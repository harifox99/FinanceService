package org.bear.shortTrade;
import java.net.URL;
import java.util.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
/**
 * 蘭弦
 * @author bear
 *
 */
public class Orchid 
{
	String urlString = "https://gd.myftp.org/lb/stockpickup.asp";
	Set<String> sets = new HashSet<String>();
	public void getContent()
	{		
		try
		{
			URL url = new URL(urlString);//在這邊輸入你要解析的網頁網址 
			Document xmlDoc =  Jsoup.parse(url, 3000); //使用Jsoup jar 去解析網頁
			//(要解析的文件,timeout)
			Element table = xmlDoc.select("table").get(0); //select the first table.
			Elements tr = table.select("tr"); //要解析的tag元素為tr
	        //只要最新3年
	        for (int i = 1; i < tr.size(); i++)
	        {
	        	Element td = tr.get(i);
		        Elements tdList = td.select("td");
		        String stockId = tdList.get(2).text();
		        //System.out.println(stockId);
		        sets.add(stockId);
	        }
		}
		catch (Exception ex)
		{
			ex.printStackTrace();	
		}
	}
	
	public Set<String> getSets() {
		return sets;
	}

	public void setSets(Set<String> sets) {
		this.sets = sets;
	}


	public static void main(String[] args)
	{
		Orchid orchid = new Orchid();
		orchid.getContent();
	}

}
