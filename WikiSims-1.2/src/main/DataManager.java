package main;

import java.io.File;

import similarity.measures.Cosine;
import similarity.measures.Jaccard;

import data.Data;
import data.wiki.WikiDB;




public class DataManager implements WikiSims {
	
	public static String anaphora;
	
	public static String antecedent;
	
	public static File directory;
	
	public static String dbURL;
	
	public static Data data;
	
	public static WikiDB wikidb;
	
	public DataManager(String anaphora, String antecedent, String path){
		
		/** Set the parameters */
		setAnaphora(anaphora);
		setAntecedent(antecedent);
		setDbURL(path);
		setDirectory(path);
		//setWikidb(path);
		setData();
	}
	
	public static String getDbURL(){
		return dbURL;
	}
	
	private static void setDbURL(String path){
		DataManager.dbURL=path;
	}
	
	public static WikiDB getWikidb() {
		return wikidb;
	}

	public static void setWikidb(String path_db) {
		DataManager.wikidb = new WikiDB(path_db);
	}

	private void setData(){
		data = new Data(antecedent, anaphora);
	}

	@Override
	public Data getData() {
		return data;
	}

	@Override
	public double getSimValue() {
		try{
			return new Cosine(data).getCosine();
		}catch(Exception e){
			return 0.0;
		}
	}
	
	
	public double getJaccard(){
		try{
			return new Jaccard(data).getJaccard();
		}catch(Exception e){
			return 0.0;
		}
	}
	
	static String getAnaphora() {
		return anaphora;
	}

	static void setAnaphora(String anaphora) {
		DataManager.anaphora = 
				anaphora;
				//preprocess(anaphora);
	}

	static String getAntecedent() {
		return antecedent;
	}

	static void setAntecedent(String antecedent) {
		DataManager.antecedent = 
				antecedent;
				//preprocess(antecedent);
	}

	static File getDirectory() {
		return directory;
	}

	static void setDirectory(String path) {
		DataManager.directory = new File(path);
	}
	
	public double similarity(){
		return 0;
	}
}
