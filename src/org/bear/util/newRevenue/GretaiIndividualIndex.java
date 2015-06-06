package org.bear.util.newRevenue;

import java.util.*;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.bear.datainput.GetSFIContent;
import org.bear.parser.sfi.GretaiParser;
import org.bear.util.HttpUtil;
/**
 * 櫃臺個股月成交資訊與週轉率
 * @author edward
 *
 */
public class GretaiIndividualIndex implements GetSFIContent {

	@Override
	public void getContent(String stockID, String stockName, String startYear, String startMonth,
			   			   String endYear, String endMonth)
	{
		GretaiParser parser = new GretaiParser();		
		String url = "http://www.tpex.org.tw/web/stock/statistics/monthly/print_st44.php?l=zh-tw";
		List<NameValuePair> paramList = new ArrayList<NameValuePair>();
		//paramList.add(new BasicNameValuePair("timestamp", "1376901040511"));
		//paramList.add(new BasicNameValuePair("ajax", "true"));
		paramList.add(new BasicNameValuePair("yy", startYear));		
		paramList.add(new BasicNameValuePair("stk_no", stockID));
		String responseString = HttpUtil.send(url, paramList, 1, "UTF-8");
		parser.setResponseString(responseString);
		parser.setStockID(stockID);
		parser.setYear(startYear);
		parser.setStartMonth(startMonth);
		parser.setEndMonth(endMonth);
		parser.parse(0);
		//System.out.println(responseString);	
	}
	public static void main(String args[])
    {
    	//GetSFIPrice revenue = new GetSFIPrice();
		GretaiIndividualIndex revenue = new GretaiIndividualIndex();
    	revenue.getContent("1256", null, "2013", "12", null, null);
    }

}
