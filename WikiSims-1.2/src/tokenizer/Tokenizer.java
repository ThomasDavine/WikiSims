package tokenizer;

import java.util.ArrayList;

public abstract class Tokenizer {
	
	ArrayList<String> tokens;

	protected void tokenize() {}

	public ArrayList<String> getTokens() {
		return tokens;}

}
