package data;

import java.util.ArrayList;
import java.util.HashMap;

import similarity.TermFrequency;
import tokenizer.Eng_StopwrdTokenizer;
import tokenizer.Tokenizer;

public class Remote {
	
	private String fileName;
	
	private HashMap<String, Integer> frequencyVector =  new HashMap<String,Integer>();
	
	Remote(String fileName){
		setFileName(fileName);
		setFrequencyVector();
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileName() {
		return fileName;
	}

	private void setFrequencyVector() {
		
		RemoteConnection conn = new RemoteConnection(fileName);
		Tokenizer tok = new Eng_StopwrdTokenizer(conn.text());
		
		ArrayList<String> tokens = tok.getTokens();
		int num = Math.min(50, tokens.size());
		ArrayList<String> tokensTrim = new ArrayList<String>();
				tokensTrim.addAll(tokens.subList(0, num));
		
		TermFrequency tf = new TermFrequency(tokensTrim);
		frequencyVector = tf.getFrequencyVector();
		
		
		
		
	}
	
	public HashMap<String, Integer> getFrequencyVector(){
		return frequencyVector;
	}
}
