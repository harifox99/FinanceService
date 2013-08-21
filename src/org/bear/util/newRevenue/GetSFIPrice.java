package org.bear.util.newRevenue;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.bear.datainput.GetSFIContent;
import org.bear.parser.sfi.PriceParser;
import org.bear.util.HttpUtil;

public class GetSFIPrice implements GetSFIContent 
{
    public void getContent(String stockID, String startYear, String startMonth, String endYear, String endMonth)
	{
    	PriceParser parser = new PriceParser();
		String url = "http://webline.sfi.org.tw/B/intdb/single/sfis507b.asp";
		List<NameValuePair> paramList = new ArrayList<NameValuePair>();
		paramList.add(new BasicNameValuePair("SCODE", stockID));
		paramList.add(new BasicNameValuePair("SYY", startYear));
		paramList.add(new BasicNameValuePair("SMM", startMonth));
		paramList.add(new BasicNameValuePair("EYY", endYear));
		paramList.add(new BasicNameValuePair("EMM", endMonth));
		String responseString = HttpUtil.send(url, paramList, 1, "big5");
		parser.setResponseString(responseString);
		parser.setStockID(stockID);
		parser.parse(2);
		//System.out.println(responseString);		
	}
    public static void main(String args[])
    {
    	//GetSFIPrice revenue = new GetSFIPrice();
    	GetSFIRevenue revenue = new GetSFIRevenue();
    	revenue.getContent("1101", "2013", "3", "2013", "6");
    }
}
