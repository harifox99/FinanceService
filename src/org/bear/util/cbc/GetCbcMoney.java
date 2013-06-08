package org.bear.util.cbc;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.bear.dao.MacroEconomicDao;
import org.bear.parser.taiwanMacro.CbcParser;
import org.bear.util.HttpUtil;
/**
 * 擷取央行的資料（主要是貨幣）
 * @author edward
 *
 */
public class GetCbcMoney 
{
	MacroEconomicDao dao;
	public void setDao(MacroEconomicDao dao) {
		this.dao = dao;
	}
	public void getContent(int startDateValue, int endDateValue)
	{
		CbcParser cbcParser = new CbcParser();
		cbcParser.setDao(dao);
		String url = "http://www.pxweb.cbc.gov.tw/Dialog/Saveshow.asp";
		List<NameValuePair> paramList = new ArrayList<NameValuePair>();
		//初始值
		paramList.add(new BasicNameValuePair("strList", "L"));
		paramList.add(new BasicNameValuePair("var1", "期間"));
		paramList.add(new BasicNameValuePair("var2", "項目"));
		paramList.add(new BasicNameValuePair("var3", "資料"));
		paramList.add(new BasicNameValuePair("Valdavarden1", String.valueOf(endDateValue-startDateValue+1)));
		paramList.add(new BasicNameValuePair("Valdavarden2", "2"));
		paramList.add(new BasicNameValuePair("Valdavarden3", "2"));
		//日期
		for (int i = startDateValue; i <= endDateValue; i++)
		{
			paramList.add(new BasicNameValuePair("values1", String.valueOf(i)));
		}
		//計數
		paramList.add(new BasicNameValuePair("values2", "4"));
		paramList.add(new BasicNameValuePair("values2", "5"));
		//平均&期底
		paramList.add(new BasicNameValuePair("values3", "1"));
		paramList.add(new BasicNameValuePair("values3", "2"));
		/***************************************************/
		paramList.add(new BasicNameValuePair("var4", "種類"));
		paramList.add(new BasicNameValuePair("Valdavarden4", "1"));
		paramList.add(new BasicNameValuePair("values4", "2"));
		paramList.add(new BasicNameValuePair("matrix", "EF01M01"));
		paramList.add(new BasicNameValuePair("root", "../PXfile/EFS/"));
		paramList.add(new BasicNameValuePair("classdir", "../PXfile/EFS/"));
		paramList.add(new BasicNameValuePair("noofvar", "4"));
		paramList.add(new BasicNameValuePair("elim", "NNNN"));
		paramList.add(new BasicNameValuePair("numberstub", "1"));
		paramList.add(new BasicNameValuePair("lang", "9"));
		paramList.add(new BasicNameValuePair("varparm", "ma=EF01M01&amp;ti=&amp;path=%2E%2E%2FPXfile%2FEFS%2F&amp;xu=&amp;yp=&amp;lang=9"));
		paramList.add(new BasicNameValuePair("hasAggregno", "0"));
		paramList.add(new BasicNameValuePair("pxkonv", "asp1"));
		String responseString = HttpUtil.send(url, paramList, 1, "big5");
		//System.out.println(responseString);
		cbcParser.setResponseString(responseString);
		cbcParser.parse(1);
	}
}
