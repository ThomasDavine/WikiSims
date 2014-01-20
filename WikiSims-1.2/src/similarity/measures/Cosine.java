package similarity.measures;


import java.util.Arrays;
import java.util.HashMap;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;

import data.Data;
import data.DataStore;

import similarity.Similarity;

public class Cosine extends Similarity {
	
	HashMap<String,Integer> antFrequencyVector = new HashMap<String,Integer>();
	HashMap<String,Integer> anaFrequencyVector = new HashMap<String,Integer>();
	HashMap<String, Integer> idfVector = new HashMap<String, Integer>();
	
	final static String[] regex = new String[]{"reasons","message","may","displayed"};
	
	boolean emptyPage=false;
	double cosine = 0;
	
	public Cosine(Data data){
		this.antFrequencyVector=data.getAntecedentFrequencyVector();
		this.anaFrequencyVector=data.getAnaphoraFrequencyVector();
		this.idfVector= new DataStore("/idf/idf").getData();
		checkEmptyPage();
	}

	private void checkEmptyPage() {
		if(
		antFrequencyVector.keySet().containsAll(Arrays.asList(regex))|
		anaFrequencyVector.keySet().containsAll(Arrays.asList(regex))){
			emptyPage=true;
		}
	}


	public double getCosine() {
		
		if(emptyPage){return 0;
		}else{
		
		SortedSet<String> wordSet_ana = new TreeSet<String>();
			wordSet_ana.addAll(anaFrequencyVector.keySet());
			
		SortedSet<String> wordSet_ant = new TreeSet<String>();
			wordSet_ant.addAll(antFrequencyVector.keySet());
			
		wordSet_ant.retainAll(wordSet_ana);
		
		double[] vector_ana = new double[wordSet_ant.size()];
		double[] vector_ant = new double[wordSet_ant.size()];
		double[] vector_idf = new double[wordSet_ant.size()];
		double[] vector_idfByAna = new double[wordSet_ant.size()];
		double[] vector_idfByAnt = new double[wordSet_ant.size()];
		
		for(int i=0; i<wordSet_ant.size(); i++){
			vector_ana[i] = anaFrequencyVector.get(wordSet_ant.toArray()[i]);
			vector_ant[i] = antFrequencyVector.get(wordSet_ant.toArray()[i]);
			vector_idf[i] = (1.0/(idfVector.get(wordSet_ant.toArray()[i])));
			vector_idfByAna[i] = vector_ana[i]*vector_idf[i];
			vector_idfByAnt[i] = vector_ant[i]*vector_idf[i];
		}
		
		RealMatrix matrix = MatrixUtils.createRealMatrix(wordSet_ant.size(), 2);
			matrix.setColumn(0, vector_idfByAna);
			matrix.setColumn(1, vector_idfByAnt);
		
		RealMatrix cosine = cosine(matrix);
		
		return cosine.getEntry(0,1);
		}
	}
	
	private static RealMatrix cosine(RealMatrix matrix) {

		int n = matrix.getColumnDimension();
		double[] db = new double[n];
		RealMatrix cosine = MatrixUtils.createRealMatrix(n, n);

		for (int i = 0; i < n; i++) {
			db[i] = matrix.getColumnVector(i).getNorm();
		}

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				cosine.setEntry(i, j, matrix.getColumnVector(i).cosine(
						matrix.getColumnVector(j)));
			}
		}

		return cosine;
	}
}
