package data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Search {
	
	String title;
	
	String wikiURL;
	
	Elements links;
	
	String search;

	public Search(String title) {
		this.title=title;
		setWikiURL(title);
		connect();
	}
	

	private void connect() {
		try{
		SchemeRegistry schemeRegistry = new SchemeRegistry();
		schemeRegistry.register(new Scheme("http", 80, PlainSocketFactory
				.getSocketFactory()));

		ClientConnectionManager ccm = new ThreadSafeClientConnManager(
				schemeRegistry);
		HttpClient httpClient = new DefaultHttpClient(ccm);
		HttpGet httpget = new HttpGet(wikiURL);
		HttpResponse response = httpClient.execute(httpget, new BasicHttpContext());
		HttpEntity entity = response.getEntity();
			
	    if (entity != null) {
	    	InputStream instream = entity.getContent();
	        InputStreamReader isr = new InputStreamReader(instream);
	        BufferedReader br = new BufferedReader(isr);
	          
	        org.jsoup.nodes.Document doc = Jsoup.parse(EntityUtils.toString(entity));
	        Elements li = doc.select("li");
        	this.links = li.select("a[href]");
        	this.search = getLink(links);
	        	
	        br.close();
	        isr.close();
	        instream.close();
	        
	    }
	    
	    EntityUtils.consume(entity);
			
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
     
		
	}

	private void setWikiURL(String title) {
		String[] tokens = title.split(" ");
		String query = "";
		for(String token : tokens){
			query += token + "+";
		}
				
		this.wikiURL = String.format("http://en.wikipedia.org/w/index.php?search=%s", query.substring(0, query.length()-1));
	}
	
	private String getLink(Elements links){
		String redirect = "";
		for(Element link : links){
			if (link.attr("href").startsWith("/wiki/")){
				redirect += link.attr("href").replaceFirst("/wiki/","");
				System.out.println(String.format(
	        			"Trying to solve ambiguity: redirect %s to %s", title,
	        			redirect));
				break;
			}
		}
		return redirect;
	}
	
	public String getLink(){
		return search;
	}
}

