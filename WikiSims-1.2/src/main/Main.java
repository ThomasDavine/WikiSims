package main;

public class Main {

	public static void main(String[] args) {
		
		double start = System.currentTimeMillis();
		DataManager data = new DataManager(
				"Crime",
				"Products",
				"/home/black/Documents/categories/");
		
		System.out.println(DataManager.getAnaphora());
		System.out.println(DataManager.getAntecedent());
		
		data.getData();

		
		
		double cosine = data.getSimValue();
		double jaccard = data.getJaccard();
		System.out.println(cosine);
		System.out.println(jaccard);
		
		double end = System.currentTimeMillis();
		double total = end - start;
		
		
		System.out.println(String.format("total time: %s", total));
	}
}

