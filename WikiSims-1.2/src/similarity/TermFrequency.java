package similarity;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.collections.Bag;
import org.apache.commons.collections.bag.HashBag;


/** Class to build the Term -Frequency vector
 *  here implemented as a HashMap<type,frequency>
 * 
 * @author tommaso
 *
 */


public class TermFrequency {
	
	private ArrayList<String> tokens = new ArrayList<String>();
	private HashMap<String,Integer> frequencyVector = new HashMap<String,Integer>();

	public TermFrequency(ArrayList<String> tokens) {
		this.tokens=tokens;
		setFrequencyVector();
	}
	
	private void setFrequencyVector(){
		
		//store tokens in a Bag in order to use the bag method getCount()
		Bag bag = getWordFrequencies(tokens);
		for(Object str : bag.uniqueSet().toArray()){
			frequencyVector.put((String) str, bag.getCount(str));
		}
	}
	
	public HashMap<String,Integer> getFrequencyVector(){
		return frequencyVector;
	}
	
	private Bag getWordFrequencies(ArrayList<String> tokens) {
		Bag wordBag = new HashBag();
		for(String token : tokens){
			wordBag.add(token);
		}
		return wordBag;
	}
}
