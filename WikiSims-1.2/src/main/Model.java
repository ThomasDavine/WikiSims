package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Model {

	private static BufferedWriter bw;

	/**public static void main(String[] args) {
		File file = new File("/home/black/Documents/model/data.trim");
		
		try {
			String line = "";
			BufferedReader br = new BufferedReader(new FileReader(file));
			while((line = br.readLine())!=null){
				String[] split = line.split(" ->");
				DataManager data = new DataManager(
						split[0],
						split[1],
						"/home/black/Documents/prova/");
				
				data.getData();

				double cosine = data.getSimValue();
				System.out.println(cosine);
				
			}
			
			br.close();
			
			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}


	}

	*/
	
}
