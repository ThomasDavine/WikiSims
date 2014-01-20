package data.wiki;

import java.io.File;
import org.apache.commons.io.filefilter.SizeFileFilter;



public class WikiDB extends File {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public WikiDB(String path){
		super(path);
	}

	@Override
	public boolean isDirectory(){
		return true;
	}
	
	@Override
	public boolean isFile(){
		return false;
	}
	
	public int numDoc() {
		return list().length;
	}

	public String getDbId() {
		return getName();
	}

	public String[] empty(){
		return list(new SizeFileFilter(1024,false));
	}
	
	public File[] emptyFiles() {
		String[] fileName = empty();
		File[] fileList = new File[fileName.length];
		for(int i=0; i<fileName.length; i++){
			fileList[i] = new File(fileName[0]);};
		return fileList;
	}
	
}
