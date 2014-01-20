package data;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.HashMap;

import main.DataManager;


public class DataStore {
	
	static final String path = DataManager.dbURL;
	
	private File file;
	
	private HashMap<String, Integer> data = new HashMap<String,Integer>();
	
	public DataStore(String fileName){
		this.file=new File(path+fileName);
		load();
	}
	
	public DataStore(String fileName, HashMap<String,Integer> data){
		this.file=new File(path+fileName);
		this.data=data;
	}
	
	public void store(){
		
		try{
			//use buffering
			OutputStream is = new FileOutputStream(file);
			OutputStream bf = new BufferedOutputStream(is);
			ObjectOutput oi = new ObjectOutputStream(bf);
		
			//serialize the object
			oi.writeObject(data);
    	
			//close streams
			oi.close();
			bf.close();
			is.close();
    	
			//catch exceptions
    		} catch (FileNotFoundException e) {
    		e.printStackTrace();
    		} catch (IOException e) {
    		e.printStackTrace();
    	}
		
	}
	
	@SuppressWarnings("unchecked")
	private void load(){
		
		try{
			
	    	//use buffering
	    	InputStream is = new FileInputStream(file);
	    	InputStream bf = new BufferedInputStream(is);
	    	ObjectInput oi = new ObjectInputStream(bf);
	    	
	    	//deserialize the data file
	    	data = (HashMap<String,Integer>) oi.readObject();
			
	    	//close streams
	    	oi.close();
	    	bf.close();
	    	is.close();
	    	
	    	//catch exceptions
	    	} catch (FileNotFoundException e) {
	    		e.printStackTrace();
	    	} catch (IOException e) {
	    		e.printStackTrace();
	    	} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
	}
	
	public HashMap<String, Integer> getData(){
		return data;
	}
}

