package org.bear.util.cbc;
import org.apache.http.message.BasicNameValuePair;
import org.bear.parser.taiwanMacro.SimpleParser;
import org.bear.util.HttpUtil;
/**
 * 新增[出口/外銷訂單年增率]
 * @author edward
 *
 */
public class GetExportOrder extends GetStatDbData
{
	public void getContent(int startDateValue, int endDateValue)
	{		
		url = "http://statdb.dgbas.gov.tw/pxweb/Dialog/Saveshow.asp";
		paramList.add(new BasicNameValuePair("var1", "期間"));
		paramList.add(new BasicNameValuePair("var2", "計價"));
		paramList.add(new BasicNameValuePair("var3", "指標"));
		paramList.add(new BasicNameValuePair("var4", "種類"));
		//初始值
		paramList.add(new BasicNameValuePair("Valdavarden1", String.valueOf(endDateValue-startDateValue+1)));
		paramList.add(new BasicNameValuePair("Valdavarden2", "1"));
		paramList.add(new BasicNameValuePair("Valdavarden3", "1"));
		paramList.add(new BasicNameValuePair("Valdavarden4", "1"));
		//日期
		for (int i = startDateValue; i <= endDateValue; i++)
		{
			paramList.add(new BasicNameValuePair("values1", String.valueOf(i)));
		}
		paramList.add(new BasicNameValuePair("values2", "2"));
		paramList.add(new BasicNameValuePair("values3", "1"));
		paramList.add(new BasicNameValuePair("values4", "2"));
		paramList.add(new BasicNameValuePair("matrix", "ES0105A1M"));
		paramList.add(new BasicNameValuePair("root", "../PXfile/EconomicStatistics/"));
		paramList.add(new BasicNameValuePair("classdir", "../PXfile/EconomicStatistics/"));
		paramList.add(new BasicNameValuePair("noofvar", "4"));
		paramList.add(new BasicNameValuePair("elim", "NNN"));
		paramList.add(new BasicNameValuePair("lang", "9"));
		paramList.add(new BasicNameValuePair("hasAggregno", "0"));	
		paramList.add(new BasicNameValuePair("pxkonv", "asp1"));
		String responseString = HttpUtil.send(url, paramList, 1, "big5");
		//System.out.println(responseString);
		SimpleParser parser = new SimpleParser();
		parser.setTableName("ExportOrder");
		parser.setDao(dao);
		parser.setResponseString(responseString);
		parser.parse(1);
	}
}
