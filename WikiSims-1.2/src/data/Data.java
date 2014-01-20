package data;

import java.util.HashMap;

public class Data {
	
	String anaphora;
	String antecedent;
	
	private static HashMap<String,Integer> antFreqVector = new HashMap<String,Integer>();
	private static HashMap<String,Integer> anaFreqVector = new HashMap<String,Integer>();
	
	public Data(String antecedent, String anaphora){
		this.antecedent=antecedent;
		this.anaphora=anaphora;
		load();
	}
	
	public void load(){
		
		DataLoader ana = new DataLoader(antecedent);
		ana.start();
		
		DataLoader ant = new DataLoader(anaphora);
		ant.start();
		
		try {
			ana.join();
			ant.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		anaFreqVector = ana.getFrequencyVector();
		antFreqVector = ant.getFrequencyVector();
		
	}
	
	public HashMap<String,Integer> getAnaphoraFrequencyVector(){
		return anaFreqVector;
	}
	
	public HashMap<String,Integer> getAntecedentFrequencyVector(){
		return antFreqVector;
	}
}
