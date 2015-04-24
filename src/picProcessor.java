import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

////////////////////////////////////////////////////////////////////////////////////////////////////
//	описание:
//		парсим прогноз погоды с гисметео (тестовая обкатка)
//	
public class picProcessor {
	
	///////////////////////////////////////////////////////////////////////////////////
	
	static ArrayList<String> getPokaz(Document doc,String prm){
		int i=0;
		ArrayList<String> x = new ArrayList<String>();
		Elements tm = doc.select(prm);
		for(Element v:tm) {
			/*
			if (i==13) {
				break;
			}
			*/
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
		
		ArrayList<String> m1 = new ArrayList<String>();
		ArrayList<String> m2 = new ArrayList<String>();
		ArrayList<String> m = getPokaz(doc,"span.value.m_temp.c");
		for(int i = 0 ; i<m.size();i++) {
			if (i<13) {
				m1.add(m.get(i));
			}
			else if(i>=13 & i<=26) {
				m2.add(m.get(i));
			}
		}
		x.add(m1);
		x.add(getPokaz(doc,"span.value.m_press.torr"));
		x.add(getPokaz(doc,"dt.wicon"));
		x.add(getPokaz(doc,"span.value.m_wind.ms"));
		x.add(m2);
		return x;
	}//static ArrayList<ArrayList<String>> grabGismeteo() throws IOException
	
	///////////////////////////////////////////////////////////////////////////////////
	
	public static void main(String[] args) throws IOException {
		ArrayList<ArrayList<String>> x =grabGismeteo();
		int i = 0;
		/*
		for(ArrayList<String> a:x){
			for(String h:a){
				System.out.print(i);
				System.out.print(" = ");
				System.out.print(h);
				System.out.print("	");
				i++;
			}
			System.out.println("");
		}
		*/
		System.out.println(x.get(1).get(0));//обл
		System.out.println(x.get(2).get(0));//ос
		
		System.out.println(x.get(4).get(0));//тек давл
		System.out.println(x.get(5).get(0));//ветер
		System.out.println(x.get(6).get(0));//скорость
		
		
		
		//System.out.println(m_tempO.get(0));
	} //public static void main(String[] args) throws IOException

}//public class picProcessor
