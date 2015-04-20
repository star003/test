import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class picProcessor {
	
	///////////////////////////////////////////////////////////////////////////////////
	
	static ArrayList<String> getPokaz(Document doc,String prm){
		int i=0;
		ArrayList<String> x = new ArrayList<String>();
		Elements tm = doc.select(prm);
		for(Element v:tm) {
			if (i==13) {
				break;
			}
			x.add(v.text());
			i++;
		}
		return x;
	}//static ArrayList<String> getPokaz(Document doc,String prm)
	
	///////////////////////////////////////////////////////////////////////////////////
	
	static ArrayList<String> getImg(Document doc,String prm){
		ArrayList<String> x = new ArrayList<String>();
		Elements cloudness = doc.select(prm);
		Elements tm = cloudness.select("img");
		for(Element v:tm) {
			x.add((v.attr("src")));
		}
		return x;
	}//static ArrayList<String> getImg(Document doc,String prm)
	
	///////////////////////////////////////////////////////////////////////////////////
	
	static ArrayList<ArrayList<String>> grabGismeteo() throws IOException{
		ArrayList<ArrayList<String>> x = new ArrayList<ArrayList<String>>();
		Document doc  = Jsoup.connect("http://www.gismeteo.ru/city/legacy/4298/").get();
		x.add(getPokaz(doc,"th.df,th.current"));
		x.add(getImg(doc,"tr.cloudness"));
		x.add(getImg(doc,"tr.persp"));
		x.add(getPokaz(doc,"span.value.m_temp.c"));
		x.add(getPokaz(doc,"span.value.m_press.torr"));
		x.add(getPokaz(doc,"dt.wicon"));
		x.add(getPokaz(doc,"span.value.m_wind.ms"));
		x.add(getPokaz(doc,"span.value.m_temp.c"));
		return x;
	}//static ArrayList<ArrayList<String>> grabGismeteo() throws IOException
	
	///////////////////////////////////////////////////////////////////////////////////
	
	public static void main(String[] args) throws IOException {
		ArrayList<ArrayList<String>> x =grabGismeteo();
		for(ArrayList<String> a:x){
			for(String h:a){
				System.out.print(h);
				System.out.print("	");
			}
			System.out.println("");
		}
		//System.out.println(m_tempO.get(0));
	} //public static void main(String[] args) throws IOException

}//public class picProcessor
