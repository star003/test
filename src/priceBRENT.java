

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/////////////////////////////////////////////////////////////////////////////////////////////////////////
//	описание:
//		получение данных о цене на нефть

public class priceBRENT {
	static ArrayList<String> item = new ArrayList<String>();
	static ArrayList<String> itemVal = new ArrayList<String>();
	final String url = "http://www.finam.ru/";
	static String indicator;
	static String indicatorValue;
	static String currTime;

	/////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	priceBRENT() throws IOException {
		Document doc  = Jsoup.connect(url).get();
		Elements metaElements = doc.select("span.usd.sm.pl05");
		for (Element x:metaElements) {
			itemVal.add(x.text());
		}
		Elements metaElementsName = doc.select("td.fst-col");
		
		for (Element x:metaElementsName) {
			item.add(x.text());
		}
		currTime = currTime();
	}//priceBRENT() throws IOException
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////	
	
	priceBRENT(String indicator) throws IOException ,SocketTimeoutException {
		priceBRENT.indicatorValue = indicator;
		Document doc  = Jsoup.connect(url).get();
		Elements metaElements = doc.select("span.usd.sm.pl05");
		for (Element x:metaElements) {
			itemVal.add(x.text());
		}
		int i = 0;
		Elements metaElementsName = doc.select("td.fst-col");
		for (Element x:metaElementsName) {
			if (x.text().indexOf(indicator)>0){
				priceBRENT.indicatorValue = itemVal.get(i);
			}
			item.add(x.text());
			i++;
		}
		currTime = currTime();
	}//priceBRENT(String indicator) throws IOException
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public static String currTime() {
		Calendar currentTime = Calendar.getInstance();
	    return String.valueOf(currentTime.get(1))+"-"
	    		+ (currentTime.get(2) + 1 >= 10 ? String.valueOf(currentTime.get(2) + 1) : "0"+String.valueOf(currentTime.get(2) + 1)) +"-"
	    		+(currentTime.get(5) >=10 ? String.valueOf(currentTime.get(5))  : "0"+String.valueOf(currentTime.get(5)))+" "
	    		+((currentTime.get(11)) >=10 ? String.valueOf(currentTime.get(11))  : "0"+String.valueOf(currentTime.get(11)))+":"
	    		+((currentTime.get(12)) >=10 ? String.valueOf(currentTime.get(12))  : "0"+String.valueOf(currentTime.get(12)))+":"
	    		+((currentTime.get(13)) >=10 ? String.valueOf(currentTime.get(13))  : "0"+String.valueOf(currentTime.get(13)));
	    				
	}//public static String currTime()
	
	

	public static String getVal() {
		return indicatorValue;
	}//public static String getVal()

	/////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public static String investing ()  {
		Document doc;
		try {
			doc = Jsoup.connect("http://www.investing.com/commodities/brent-oil")
					.userAgent("Mozilla")
					.get();
		
			Elements metaElements = doc.select("span.arial_26.pid-8833-last");
			for (Element x:metaElements) {
				return x.text();
			}
		
		} catch (IOException e) {
			return "";
		}
		return "";
	}//public static String investing () throws IOException
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public static String tinkoff() throws IOException {
		Document doc  = Jsoup.connect("http://www.londonstockexchange.com/exchange/prices-and-markets/stocks/summary/company-summary.html?fourWayKey=US87238U2033USUSDIOBE")
				.userAgent("Mozilla")
				.get();
		Elements metaElements = doc.select("tr.odd");
				
		return metaElements.first().text().split(" ")[0];
	}//public static String tinkoff()
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public static String usd() throws IOException {
		Document doc  = Jsoup.connect("http://www.finam.ru")
				.userAgent("Mozilla")
				.get();
		Elements metaElements = doc.select("a.dark.no");
		return metaElements.first().text();
	}//public static String usd() throws IOException
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public static String gis() throws IOException {
		String rez=" ";
		String rz = "\r\n";
		Document doc  = Jsoup.connect("http://www.gismeteo.ru/city/daily/4298/").get();
		Elements a1 = doc.select("div.section.higher");
		//**город
		rez= rez.concat((a1.select("h2.typeM").first().text())+rz);
		//**явления
		rez=rez.concat(a1.select("dl.cloudness").first().text()+rz);
		//**температура
		Elements a2 =a1.select("div.temp");
		rez=rez.concat(a2.select("dd.value.m_temp.c").first().text().replaceAll("[^\\d-+]", "")+rz);
		//**ветер
		Elements a3 =a1.select("div.wicon.wind");
		rez=rez.concat(a3.select("dl[title]").attr("title")+rz);
		//**скорость ветра
		rez=rez.concat(a3.select("dd.value.m_wind.ms").first().text().replaceAll("[^\\d]", "")+rz);
		
		//**давление
		Elements a4 =a1.select("div.wicon.barp");
		rez=rez.concat(a4.select("dd.value.m_press.torr").first().text().replaceAll("[^\\d]", "")+rz);
		
		//**влажность
		rez=rez.concat(a1.select("div.wicon.hum").first().text().replaceAll("[^\\d]", "")+rz);
		return rez;

	}
	
	///////////////////////////////////////////////////////////////////////////////////
	//	описание
	//		колбания курса за день
	static ArrayList<String> getUsdDay() throws IOException {
		ArrayList<String> x = new ArrayList<String>();
		Document doc  = Jsoup.connect("http://www.micex.ru/issrpc/marketdata/currency/selt/daily/short/result_2014_03_20.xml?boardid=CETS&secid=USD000UTSTOM").get();
		Elements a1 = doc.select("row");
		
		x.add(a1.attr("LAST"));
		x.add(a1.attr("OPEN"));
		x.add(a1.attr("VOLTODAY"));
		x.add(a1.attr("UPDATETIME"));
		
		return x;
	}//static ArrayList<String> getUsdDay()
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////
	//	по доллару с финам
	/*
	 * 	0 Последняя сделка 53,5840
		1 Изменение 0,2490 (0,47%)
		2 Сегодня, max 54,1680
		3 Сегодня, min 53,4620
		4 Цена открытия 53,6760
		5 Пред. закрытие 53,3350
		6 Объём торгов 2 023 013 000
	 */
	public static ArrayList<String> usdFinam() throws IOException {
		ArrayList<String> x = new ArrayList<String>();
		Document doc  = Jsoup.connect("http://www.finam.ru/profile/mosbirzha-valyutnyj-rynok/usdrubtom-usd-rub/").get();
		Elements a1 = doc.select("td.value");
		int i = 0;
		for (Element d:a1) {
			if(i>5 & i<13) {
				x.add(d.text());
			}	
			i++;
		}	
		return x;
	}//public static String gis() throws IOException
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////
	/*
	 * 	0 Последняя сделка 53,5840
		1 Изменение 0,2490 (0,47%)
		2 Сегодня, max 54,1680
		3 Сегодня, min 53,4620
		4 Цена открытия 53,6760
		5 Пред. закрытие 53,3350
		6 Объём торгов 2 023 013 000
	 */
	public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException {
		int i = 0;
		for (String a:usdFinam()) {
			System.out.print(i);
			System.out.print(" ");
			System.out.println(a);
			i++;
		}
		//System.out.println(priceBRENT.gis ());
	}//public static void main(String[] args) throws IOException


}//public class priceBRENT
