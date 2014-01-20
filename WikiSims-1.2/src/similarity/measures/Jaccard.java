package similarity.measures;

import java.util.Arrays;
import java.util.HashMap;
import java.util.SortedSet;
import java.util.TreeSet;

import data.Data;


import similarity.Similarity;

public class Jaccard extends Similarity{
	
	HashMap<String,Integer> antFrequencyVector = new HashMap<String,Integer>();
	HashMap<String,Integer> anaFrequencyVector = new HashMap<String,Integer>();
	
	final static String[] regex = new String[]{"reasons","message","may","displayed"};
	
	boolean emptyPage=false;
	double cosine = 0;
	
	public Jaccard(Data data){
		this.antFrequencyVector=data.getAntecedentFrequencyVector();
		this.anaFrequencyVector=data.getAnaphoraFrequencyVector();
		checkEmptyPage();
	}

	private void checkEmptyPage() {
		if(
		antFrequencyVector.keySet().containsAll(Arrays.asList(regex))|
		anaFrequencyVector.keySet().containsAll(Arrays.asList(regex))){
			emptyPage=true;
		}
	}

	public double getJaccard() {
		
		if(emptyPage){return 0;
		}else{
			SortedSet<String> wordSet_ana = new TreeSet<String>();
			wordSet_ana.addAll(anaFrequencyVector.keySet());
			
			SortedSet<String> wordSet_ant = new TreeSet<String>();
			wordSet_ant.addAll(antFrequencyVector.keySet());
			
			wordSet_ant.retainAll(wordSet_ana);
			
			double numerator = wordSet_ant.size();
			double denominator = Math.min(anaFrequencyVector.size(), antFrequencyVector.size());
			
			double jaccard = numerator / denominator;
		
			return jaccard;
		}
	}
}
