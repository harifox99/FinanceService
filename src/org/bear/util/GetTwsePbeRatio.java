package org.bear.util;

import java.util.HashMap;
import org.bear.parser.TwsePbeParser;
/**
 * √“•Ê©“PE Ratio and PB Ratio
 * @author edward
 *
 */
public class GetTwsePbeRatio {
	HashMap<String, Double> hashPer;
	HashMap<String, Double> hashPbr;
	GetURLContent content;
	//String url = "http://www.twse.com.tw/ch/trading/exchange/BWIBBU/BWIBBU_d.php";
	String url = "http://www.twse.com.tw/exchangeReport/BWIBBU_d?response=html&selectType=ALL&date=";
	String date;
	public String getDate() {
		return date;
	}
	
	public void setDate(String date) {
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
		TwsePbeParser parser = new TwsePbeParser();
		parser.setUrl(url + date);
		parser.getConnection();
		parser.parse(0);
		/*
		List<NameValuePair> paramList = new ArrayList<NameValuePair>();
		paramList.add(new BasicNameValuePair("qdate", date));
		paramList.add(new BasicNameValuePair("select2", "ALL"));
		paramList.add(new BasicNameValuePair("Sort_kind", "STKNO"));
		paramList.add(new BasicNameValuePair("download", ""));
		String responseString = HttpUtil.send(url, paramList, 1, "UTF-8");
		//System.out.println(responseString);
		parser.setResponseString(responseString);
		parser.parse(0);*/
		hashPer = parser.getHashPer();
		hashPbr = parser.getHashPbr();
	}
	public static void main(String args[])
	{
		GetTwsePbeRatio ratio = new GetTwsePbeRatio();
		ratio.setDate("106/04/18");
		ratio.getContent();
	}
}
