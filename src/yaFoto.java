import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.select.Elements;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class yaFoto {
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public static void main(String[] args) throws IOException {
		for (String x : getListFoto()) {
			System.out.println(x);
		}
	}//public static void main(String[] args) throws IOException
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//	описание:
	//		вернет список фото дня с яндекс. В списке адреса фотоко размер XXXL
	static ArrayList<String> getListFoto() throws IOException{
		ArrayList<String> x = new ArrayList<String>();
		Document doc  = Jsoup.connect("http://api-fotki.yandex.ru/api/podhistory/").get();
		Elements a1 = doc.select("link[href]");
		for(Element h:a1) {
			
			String x1 = h.attr("href");
			
			if (x1.contains("img-fotki")) {
				if (x1.contains("XXXL")){
					
					x.add(x1);
					
				}	
			}	
		}	
		return x;
	}//static ArrayList<String> getListFoto() throws IOException
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
}//public class yaFoto
