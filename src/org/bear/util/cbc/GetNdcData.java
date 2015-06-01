package org.bear.util.cbc;
import org.apache.http.message.BasicNameValuePair;
import org.bear.parser.taiwanMacro.NdcParser;
import org.bear.util.HttpUtil;
/**
 * 中華民國統計資訊網擷取總經資料
 * @author edward
 *
 */
public class GetNdcData extends GetStatDbData 
{
	public void getContent(int startDateValue, int endDateValue)
	{		
		url = "http://statdb.dgbas.gov.tw/pxweb/Dialog/Saveshow.asp";
		//初始值
		paramList.add(new BasicNameValuePair("Valdavarden1", String.valueOf(endDateValue-startDateValue+1)));
		paramList.add(new BasicNameValuePair("Valdavarden2", "3"));
		paramList.add(new BasicNameValuePair("Valdavarden3", "2"));
		//日期
		for (int i = startDateValue; i <= endDateValue; i++)
		{
			paramList.add(new BasicNameValuePair("values1", String.valueOf(i)));
		}
		//指標
		paramList.add(new BasicNameValuePair("values2", "1"));
		paramList.add(new BasicNameValuePair("values2", "2"));
		paramList.add(new BasicNameValuePair("values2", "7"));
		//平均&期底
		paramList.add(new BasicNameValuePair("values3", "1"));
		paramList.add(new BasicNameValuePair("values3", "2"));
		/************************************/
		paramList.add(new BasicNameValuePair("matrix", "ES0101A1M"));
		//paramList.add(new BasicNameValuePair("varparm", "ma=ES0101A1M&amp;ti=%B4%BA%AE%F0%BB%E2%A5%FD%AB%FC%BC%D0%2D%A4%EB&amp;path=%2E%2E%2FPXfile%2FEconomicStatistics%2F&amp;xu=&amp;yp=&amp;lang=9"));
		paramList.add(new BasicNameValuePair("hasAggregno", "0"));	
		//paramList.add(new BasicNameValuePair("ti", "景氣領先指標-月"));
		paramList.add(new BasicNameValuePair("pxkonv", "asp1"));		
		paramList.add(new BasicNameValuePair("root", "../PXfile/EconomicStatistics/"));
		paramList.add(new BasicNameValuePair("classdir", "../PXfile/EconomicStatistics/"));
		paramList.add(new BasicNameValuePair("noofvar", "3"));
		paramList.add(new BasicNameValuePair("elim", "NNN"));
		//paramList.add(new BasicNameValuePair("numberstub", "1"));
		paramList.add(new BasicNameValuePair("lang", "9"));
		//paramList.add(new BasicNameValuePair("stubceller", "0"));
		//paramList.add(new BasicNameValuePair("headceller", "0"));		
		String responseString = HttpUtil.send(url, paramList, 1, "big5");
		System.out.println(responseString);
		NdcParser ndcParser = new NdcParser();
		ndcParser.setResponseString(responseString);
		ndcParser.setDao(dao);
		ndcParser.parse(1);
		
	}
}
