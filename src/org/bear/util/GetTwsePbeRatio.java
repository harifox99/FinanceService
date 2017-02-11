package org.bear.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
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
	String url = "http://www.twse.com.tw/ch/trading/exchange/BWIBBU/BWIBBU_d.php";
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
		List<NameValuePair> paramList = new ArrayList<NameValuePair>();
		paramList.add(new BasicNameValuePair("input_date", date));
		paramList.add(new BasicNameValuePair("select2", "ALL"));
		paramList.add(new BasicNameValuePair("order", "STKNO"));
		String responseString = HttpUtil.send(url, paramList, 1, "Big5");
		//System.out.println(responseString);
		parser.setResponseString(responseString);
		parser.parse(8);
		hashPer = parser.getHashPer();
		hashPbr = parser.getHashPbr();
	}
	public static void main(String args[])
	{
		GetTwsePbeRatio ratio = new GetTwsePbeRatio();
		ratio.setDate("106/02/10");
		ratio.getContent();
	}
}
