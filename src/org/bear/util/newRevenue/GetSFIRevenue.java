package org.bear.util.newRevenue;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.bear.datainput.GetSFIContent;
import org.bear.parser.sfi.RevenueParser;
import org.bear.util.HttpUtil;

public class GetSFIRevenue implements GetSFIContent
{
	public void getContent(String stockID, String startYear, String startMonth, String endYear, String endMonth)
	{
    	RevenueParser parser = new RevenueParser();
		String url = "http://webline.sfi.org.tw/B/intdb/single/sfis112b1.asp";
		List<NameValuePair> paramList = new ArrayList<NameValuePair>();
		paramList.add(new BasicNameValuePair("SCODE", stockID));
		paramList.add(new BasicNameValuePair("SYY", startYear));
		paramList.add(new BasicNameValuePair("SMM", startMonth));
		paramList.add(new BasicNameValuePair("EYY", endYear));
		paramList.add(new BasicNameValuePair("EMM", endMonth));
		paramList.add(new BasicNameValuePair("GOTOPROG", "sfis112b1.asp"));
		paramList.add(new BasicNameValuePair("CHARTYPE", "5"));
		String responseString = HttpUtil.send(url, paramList, 1, "big5");
		parser.setResponseString(responseString);
		parser.setStockID(stockID);
		parser.parse(2);
		//System.out.println(responseString);		
	}
}
