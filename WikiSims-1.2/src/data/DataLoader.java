package data;

import java.io.File;
import java.util.HashMap;
import tokenizer.Eng_StopwrdTokenizer;
import static main.DataManager.directory;


public class DataLoader extends Thread{
	
	private File file;
	
	private String fileName;
        
        private String mention;
	
	//private static final String fileDir = "/home/tommaso/Desktop/";
	
	public HashMap<String, Integer> frequencyVector;
	
	public DataLoader(String fileName){
                setFileName(fileName);
                setFile();
                setMention(fileName);
                //setFrequencyVector();
	}
	
	
	@Override
	public void run(){
		if(newEntity()!=true){
			frequencyVector = getLocalData();
		}else{
			frequencyVector = getRemoteData();
			storeData(frequencyVector);
		}
	}
	
	private void storeData(HashMap<String, Integer> data) {
		DataStore ds = new DataStore(fileName, data);
		ds.store();
	}

	private HashMap<String,Integer> getRemoteData() {
		return new Remote(mention).getFrequencyVector();
	}

	public HashMap<String,Integer> getFrequencyVector(){
		return frequencyVector;
	}
	
	/** Check if a data file is already stored */
	public boolean newEntity(){
		if(!file.exists()){
			System.out.println(file.getAbsolutePath());
			return true;
		}else{
			return false;
		}
	}
	
	/** Getter and Setter methods */
	File getFile() {
		return file;
	}

	void setFile() {
		this.file = new File(directory+"/"+fileName);
	}

	String getFileName() {
		return fileName;
	}

	void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	/** Read the serialized data file */
	private HashMap<String, Integer> getLocalData(){
		return new DataStore(fileName).getData();
	}
        
        private static String preprocess(String mention){
		String line = "";
		for(String token : new Eng_StopwrdTokenizer(mention.split(",")[0]).getTokens()){
			line += token+" ";
		}
		return line;
	}

    private void setMention(String mention) {
        this.mention = preprocess(mention);
    }
	
	
}
