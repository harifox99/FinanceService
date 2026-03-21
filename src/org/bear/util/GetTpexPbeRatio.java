package org.bear.util;

import java.util.HashMap;

import org.bear.parser.TpexPbeParser;
/** 
 * Âd¶RPE Ratio and PB Ratio
 * @author edward
 *
 */
public class GetTpexPbeRatio {
	HashMap<String, Double> hashPer;
	HashMap<String, Double> hashPbr;
	GetURLContent content;
	String date;
	//String url = "https://www.tpex.org.tw/web/stock/aftertrading/peratio_analysis/pera_print.php?l=zh-tw&d=";
	String url = "https://www.tpex.org.tw/www/zh-tw/afterTrading/peQryDate?&cate=&id=&response=html&date=" + date;
	public String getDate() {
		return date;
	}
	public void setDate(String date) 
	{
		this.date = date;
	}

	public HashMap<String, Double> getHashPer() {
		return hashPer;
	}

	public HashMap<String, Double> getHashPbr() {
		return hashPbr;
	}
	
	public void getContent()
	{
		TpexPbeParser parser = new TpexPbeParser();
		parser.setUrl(url + date);
		parser.getConnection();
		parser.parse();
		hashPer = parser.getHashPer();
		hashPbr = parser.getHashPbr();
	}
	public static void main(String args[])
	{
		GetTpexPbeRatio ratio = new GetTpexPbeRatio();
		ratio.setDate("2024/11/08");
		ratio.getContent();
	}
}
