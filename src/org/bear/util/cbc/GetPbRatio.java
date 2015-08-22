package org.bear.util.cbc;

import org.apache.http.message.BasicNameValuePair;
import org.bear.parser.taiwanMacro.ComplicatedParser;
import org.bear.util.HttpUtil;
/**
 * 台股股價淨值比
 * @author edward
 *
 */
public class GetPbRatio extends GetStatDbData
{
	public void getContent(int startDateValue, int endDateValue)
	{		
		url = "http://statdb.dgbas.gov.tw/pxweb/Dialog/Saveshow.asp";
		//初始值
		paramList.add(new BasicNameValuePair("Valdavarden1", String.valueOf(endDateValue-startDateValue+1)));
		paramList.add(new BasicNameValuePair("Valdavarden2", "2"));
		paramList.add(new BasicNameValuePair("Valdavarden3", "1"));
		//日期
		for (int i = startDateValue; i <= endDateValue; i++)
		{
			paramList.add(new BasicNameValuePair("values1", String.valueOf(i)));
		}
		paramList.add(new BasicNameValuePair("values2", "1"));
		paramList.add(new BasicNameValuePair("values2", "3"));
		paramList.add(new BasicNameValuePair("values3", "1"));
		paramList.add(new BasicNameValuePair("matrix", "SF0401A1M"));
		paramList.add(new BasicNameValuePair("root", "../pxfile/Securities/"));
		paramList.add(new BasicNameValuePair("classdir", "../pxfile/Securities/"));
		paramList.add(new BasicNameValuePair("noofvar", "3"));
		paramList.add(new BasicNameValuePair("elim", "NNN"));
		paramList.add(new BasicNameValuePair("lang", "9"));
		paramList.add(new BasicNameValuePair("hasAggregno", "0"));	
		paramList.add(new BasicNameValuePair("pxkonv", "asp1"));
		String responseString = HttpUtil.send(url, paramList, 1, "big5");
		ComplicatedParser parser = new ComplicatedParser();
		parser.setResponseString(responseString);
		parser.setTableName("PbRatio");
		parser.setDao(dao);
		parser.parse(1);
	}
}
