package data;

import connection.HTTPConnection;
import data.wiki.WikiPage;


public class RemoteConnection {
	
	String url;
	
	String text;
	
	public RemoteConnection(String query){
		this.url = toURL(query);
		setText();
	}
	
	private void setText() {
		this.text = "";
		int i = 0;
		while(text.length() < 500 & i<4){
			HTTPConnection conn = new HTTPConnection(url);
			WikiPage page = new WikiPage(conn.getHtmlPage());
			
			this.text = page.text();
			
			if(page.suggestion()!=null){ 
				url= toURL(page.suggestion());
			}else{
				try{
					url = "http://en.wikipedia.org"+page.search().first().attr("href");
				}catch(NullPointerException e)
					{e.printStackTrace();}
			}
			i++;
			System.err.println("searching...");
		}
		
		System.out.println(text);
	}
	
	public String text(){
		return this.text;
	}

	private String toURL(String query){
		return "http://en.wikipedia.org/wiki/Special:Search?search="+ query.replaceAll(" ", "+");
	}
}
