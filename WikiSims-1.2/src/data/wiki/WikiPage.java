package data.wiki;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WikiPage {
	
	private Document doc;
	
	private String text;

	private Elements links;
	
	public WikiPage(String html){
		this.doc = Jsoup.parse(html);
		setText();
		setLinks();
	}

	public org.jsoup.nodes.Document getDoc() {
		return this.doc;
	}
	
	private void setText(){
		this.text = "";
		Elements paragraphs = doc.select("p");
		for(Element par : paragraphs){
			this.text += par.text().replaceAll("\"", "");
		}
	}
	
	public String text(){
		return this.text;
	}
	
	private void setLinks(){
		Elements node = doc.select("li");
		links = node.select("a[href]");
	}
	
	public Elements links(){
		return this.links;
	}
	
	public Elements search(){
		Elements search = new Elements();
		for(Element link : links){
			if(link.attr("href").contains("wiki")){
				search.add(link);
			}
		}
		return search;
	}
	
	public String suggestion(){
		try{
			return doc.select("div[class = searchdidyoumean]").first().select("em").text();
		}catch(NullPointerException e){
			return null;
		}
	}
}
