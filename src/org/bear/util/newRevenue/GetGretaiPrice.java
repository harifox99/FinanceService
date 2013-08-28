package org.bear.util.newRevenue;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.bear.datainput.GetSFIContent;
import org.bear.entity.GretaiPriceEntity;
import org.bear.parser.sfi.GretaiPriceParser;
import org.bear.util.HttpUtil;

public class GetGretaiPrice implements GetSFIContent 
{
	GretaiPriceEntity entity;
	public GretaiPriceEntity getEntity() {
		return entity;
	}
	public void setEntity(GretaiPriceEntity entity) {
		this.entity = entity;
	}
	@Override
	public void getContent(String stockID, String startYear, String startMonth,
			String endYear, String endMonth) {
		GretaiPriceParser parser = new GretaiPriceParser();
		String url = "http://www.otc.org.tw/ch/stock/aftertrading/daily_trading_info/result_st43.php?";
		List<NameValuePair> paramList = new ArrayList<NameValuePair>();
		paramList.add(new BasicNameValuePair("timestamp", "1377322695093"));
		paramList.add(new BasicNameValuePair("ajax", "true"));
		paramList.add(new BasicNameValuePair("yy", startYear));
		paramList.add(new BasicNameValuePair("mm", startMonth));
		paramList.add(new BasicNameValuePair("input_stock_code", stockID));
		String responseString = HttpUtil.send(url, paramList, 1, "big5");
		parser.setResponseString(responseString);
		parser.parse(2);
		entity = parser.getEntity();
		//System.out.println(responseString);	
	}
	public static void main(String args[])
	{
		GetGretaiPrice price = new GetGretaiPrice();
		price.getContent("1336", "2013", "7", null, null);
	}

}
