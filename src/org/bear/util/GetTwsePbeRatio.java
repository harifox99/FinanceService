package org.bear.util;

import java.util.HashMap;
import org.bear.parser.TwsePbeParser;
/**
 * 證交所PE Ratio and PB Ratio
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
		//民國轉西元
		String[] dateArray = date.split("/");
		String year = StringUtil.convertYear(dateArray[0]);
		parser.setUrl(url + year + dateArray[1] + dateArray[2]);
		parser.getConnection();
		parser.parse();
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
		ratio.setDate("20170609");
		ratio.getContent();
	}
}
