package org.bear.util.cbc;
/**
 * 擷取央行的資料（主要是活期存款）
 */
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.bear.dao.MacroEconomicDao;
import org.bear.parser.taiwanMacro.DemandDepositParser;
import org.bear.util.HttpUtil;

public class GetDemandDeposit {
	MacroEconomicDao dao;
	public void setDao(MacroEconomicDao dao) {
		this.dao = dao;
	}
	public void getContent(int startDateValue, int endDateValue)
	{
		DemandDepositParser parser = new DemandDepositParser();
		parser.setDao(dao);
		String url = "http://www.pxweb.cbc.gov.tw/Dialog/Saveshow.asp";
		List<NameValuePair> paramList = new ArrayList<NameValuePair>();
		//初始值
		paramList.add(new BasicNameValuePair("strList", "L"));
		paramList.add(new BasicNameValuePair("var1", "期間"));
		paramList.add(new BasicNameValuePair("var2", "項目"));
		paramList.add(new BasicNameValuePair("var3", "種類"));
		paramList.add(new BasicNameValuePair("Valdavarden1", String.valueOf(endDateValue-startDateValue+1)));
		paramList.add(new BasicNameValuePair("Valdavarden2", "1"));
		paramList.add(new BasicNameValuePair("Valdavarden3", "1"));
		//日期
		for (int i = startDateValue; i <= endDateValue; i++)
		{
			paramList.add(new BasicNameValuePair("values1", String.valueOf(i)));
		}
		//計數
		paramList.add(new BasicNameValuePair("values2", "2"));
		//平均&期底
		paramList.add(new BasicNameValuePair("values3", "2"));
		/***************************************************/
		paramList.add(new BasicNameValuePair("matrix", "EF03M01"));
		paramList.add(new BasicNameValuePair("root", "../PXfile/EFS/"));
		paramList.add(new BasicNameValuePair("classdir", "../PXfile/EFS/"));
		paramList.add(new BasicNameValuePair("noofvar", "3"));
		paramList.add(new BasicNameValuePair("elim", "NNN"));
		paramList.add(new BasicNameValuePair("numberstub", "1"));
		paramList.add(new BasicNameValuePair("lang", "9"));
		paramList.add(new BasicNameValuePair("varparm", "ma=EF03M01&amp;ti=&amp;path=%2E%2E%2FPXfile%2FEFS%2F&amp;xu=&amp;yp=&amp;lang=9"));
		paramList.add(new BasicNameValuePair("hasAggregno", "0"));
		paramList.add(new BasicNameValuePair("pxkonv", "asp1"));
		String responseString = HttpUtil.send(url, paramList, 1, "big5");
		//System.out.println(responseString);
		parser.setResponseString(responseString);
		parser.parse(1);
	}
}
