package connection;

import java.io.IOException;
import java.io.InputStream;

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


public class HTTPConnection {
	
	private HttpGet httpget;
	private SchemeRegistry schemeRegistry;
	private String htmlPage;
	private HttpClient httpClient;
	private BasicHttpContext context;
	private ClientConnectionManager cm;
	
	
	
	
	public HTTPConnection(String url){
		this.schemeRegistry = new SchemeRegistry();
	    schemeRegistry.register(
	      new Scheme("http", 80, PlainSocketFactory.getSocketFactory()));

	    this.cm = new ThreadSafeClientConnManager(schemeRegistry);
	    this.httpClient = new DefaultHttpClient(cm);
	    this.context = new BasicHttpContext();
		this.httpget= new HttpGet(url);
		try {
			connect(url);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void connect(String url) throws ClientProtocolException, IOException{
		 
	        try {
	            
	            HttpResponse response = httpClient.execute(httpget, context);
	            HttpEntity entity = response.getEntity();
	            if (entity != null) {
	              InputStream instream = entity.getContent();
	              try {
	                  setHtmlPage(EntityUtils.toString(entity));
	              }finally{
	            	 instream.close(); 
	              }
	        }
	            EntityUtils.consume(entity);
	      } catch (Exception ex) {
	      ex.printStackTrace();
	      this.httpget.abort();
	    }
	}

	public String getHtmlPage() {
		return htmlPage;
	}

	private void setHtmlPage(String htmlPage) {
		this.htmlPage = htmlPage;
	}
}
