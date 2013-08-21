package org.bear.util.newRevenue;

import java.util.*;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.bear.datainput.GetSFIContent;
import org.bear.parser.sfi.GretaiParser;
import org.bear.util.HttpUtil;

public class GretaiIndividualIndex implements GetSFIContent {

	@Override
	public void getContent(String stockID, String startYear, String startMonth,
			String endYear, String endMonth) 
	{
		GretaiParser parser = new GretaiParser();
		String url = "http://www.otc.org.tw/ch/stock/statistics/monthly/result_st44.php";
		List<NameValuePair> paramList = new ArrayList<NameValuePair>();
		paramList.add(new BasicNameValuePair("timestamp", "1376901040511"));
		paramList.add(new BasicNameValuePair("ajax", "true"));
		paramList.add(new BasicNameValuePair("yy", startYear));
		paramList.add(new BasicNameValuePair("input_stock_code", stockID));
		String responseString = HttpUtil.send(url, paramList, 1, "big5");
		parser.setResponseString(responseString);
		parser.setStockID(stockID);
		parser.parse(2);
		//System.out.println(responseString);	
	}
	public static void main(String args[])
    {
    	//GetSFIPrice revenue = new GetSFIPrice();
		GretaiIndividualIndex revenue = new GretaiIndividualIndex();
    	revenue.getContent("1256", "2013", null, null, null);
    }

}
