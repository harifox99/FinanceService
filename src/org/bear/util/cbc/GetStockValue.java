package org.bear.util.cbc;
import org.apache.http.message.BasicNameValuePair;
import org.bear.parser.taiwanMacro.SimpleParser;
import org.bear.util.HttpUtil;
/**
 * 擷取台股總市值
 * @author edward
 *
 */
public class GetStockValue extends GetStatDbData {
	public void getContent(int startDateValue, int endDateValue)
	{		
		//url = "http://statdb.dgbas.gov.tw/pxweb/Dialog/Saveshow.asp";
		url = "http://www.pxweb.cbc.gov.tw/Dialog/Saveshow.asp";
		//初始值
		paramList.add(new BasicNameValuePair("Valdavarden1", String.valueOf(endDateValue-startDateValue+1)));
		paramList.add(new BasicNameValuePair("Valdavarden2", "1"));
		paramList.add(new BasicNameValuePair("Valdavarden3", "1"));
		//日期
		for (int i = startDateValue; i <= endDateValue; i++)
		{
			paramList.add(new BasicNameValuePair("values1", String.valueOf(i)));
		}
		//總市值
		paramList.add(new BasicNameValuePair("values2", "3"));
		//原始值
		paramList.add(new BasicNameValuePair("values3", "1"));
		/************************************/
		//paramList.add(new BasicNameValuePair("matrix", "FM2902A1M"));
		paramList.add(new BasicNameValuePair("matrix", "EG27M01"));
		//paramList.add(new BasicNameValuePair("varparm", "ma=ES0101A1M&amp;ti=%B4%BA%AE%F0%BB%E2%A5%FD%AB%FC%BC%D0%2D%A4%EB&amp;path=%2E%2E%2FPXfile%2FEconomicStatistics%2F&amp;xu=&amp;yp=&amp;lang=9"));
		paramList.add(new BasicNameValuePair("hasAggregno", "0"));	
		//paramList.add(new BasicNameValuePair("ti", "景氣領先指標-月"));
		paramList.add(new BasicNameValuePair("pxkonv", "asp1"));		
		paramList.add(new BasicNameValuePair("root", "../PXfile/EFS/"));
		paramList.add(new BasicNameValuePair("classdir", "../PXfile/EFS/"));
		paramList.add(new BasicNameValuePair("noofvar", "3"));
		paramList.add(new BasicNameValuePair("elim", "NNN"));
		//paramList.add(new BasicNameValuePair("numberstub", "1"));
		paramList.add(new BasicNameValuePair("lang", "9"));
		//paramList.add(new BasicNameValuePair("stubceller", "0"));
		//paramList.add(new BasicNameValuePair("headceller", "0"));		
		String responseString = HttpUtil.send(url, paramList, 1, "big5");
		//System.out.println(responseString);
		SimpleParser stockValueParser = new SimpleParser();
		stockValueParser.setResponseString(responseString);
		stockValueParser.setDao(dao);
		stockValueParser.setTableName("StockValue");
		stockValueParser.parse(1);
	}
}
