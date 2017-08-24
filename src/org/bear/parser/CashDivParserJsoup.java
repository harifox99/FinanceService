package org.bear.parser;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Element;

/**
 * JSoup Parser爬每年配息
 * @author bear
 *
 */
public class CashDivParserJsoup extends ParserBase 
{
	String urlString;
	//儲存現金股息，以年份為Key，以NAV為Value
	HashMap <String, Double> mapCashDiv;
	//儲存年份
	ArrayList <String> listYear;
	//儲存配發股息
	ArrayList <Double> listCashDiv;
	public static void main(String[] args) throws Exception 
	{
        // TODO Auto-generated method stub
		String url = "http://sjmain.esunsec.com.tw/z/zc/zcc/zcc.djhtm?A=1101";
        new CashDivParserJsoup(url).parsing(7);
    }
	public CashDivParserJsoup(String urlString)
	{		
		this.urlString = urlString;
		mapCashDiv = new HashMap <String, Double>();
		listYear = new ArrayList <String>();
		listCashDiv = new ArrayList <Double>();
	}
    public void parsing(int startTrIndex)
    {
    	try
    	{
	        URL url = new URL(urlString);//在這邊輸入你要解析的網頁網址 
	        Document xmlDoc =  Jsoup.parse(url, 3000); //使用Jsoup jar 去解析網頁
	        //(要解析的文件,timeout)
	        Elements tr = xmlDoc.select("tr"); //要解析的tag元素為tr
	        //只要最新3年
	        for (int i = startTrIndex; i < startTrIndex + 3; i++)
	        {
		        Element td = tr.get(i);
		        Elements tdList = td.select("td");
		        listYear.add(tdList.get(0).text());
		        listCashDiv.add(Double.parseDouble(tdList.get(3).text()));
		        //mapCashDiv.put(tdList.get(0).text(), Double.parseDouble(tdList.get(3).text()));
	        }
    	}
    	catch (Exception ex)
    	{
    		ex.printStackTrace();
    	}
    }
    public HashMap<String, Double> getCashDivData()
	{
		for (int i = 0; i < listCashDiv.size(); i++)
		{
			mapCashDiv.put(listYear.get(i), listCashDiv.get(i));
		}
		return mapCashDiv;
	}
}
