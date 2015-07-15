package org.bear.util.distribution;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.bear.dao.ThreeBigExchangeDao;
import org.bear.parser.TwseThreeBigExchangeParser;
import org.bear.util.HttpUtil;
/**
 * 設定URL，擷取三大法人買賣超資料
 * @author edward
 *
 */
public class GetTwseThreeBigExchange 
{
	ThreeBigExchangeDao dao;	
	String date;
	int stockBranch;
	String exchanger;
	String url;
	public ThreeBigExchangeDao getDao() {
		return dao;
	}
	public void setDao(ThreeBigExchangeDao dao) {
		this.dao = dao;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}			
	public int getStockBranch() {
		return stockBranch;
	}
	public void setStockBranch(int stockBranch) {
		this.stockBranch = stockBranch;
	}
	public String getExchanger() {
		return exchanger;
	}
	public void setExchanger(String exchanger) {
		this.exchanger = exchanger;
	}	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public void getContent()
	{
		TwseThreeBigExchangeParser parser = new TwseThreeBigExchangeParser();
		parser.setDao(dao);
		List<NameValuePair> paramList = new ArrayList<NameValuePair>();
		paramList.add(new BasicNameValuePair("qdate", date));
		paramList.add(new BasicNameValuePair("sorting", "by_issue"));
		String responseString = HttpUtil.send(url, paramList, 1, "big5");
		//System.out.println(responseString);
		parser.setResponseString(responseString);
		parser.setDate(date);
		parser.setExchanger(exchanger);
		parser.setDao(dao);
		parser.setStockBranch(stockBranch);
		parser.parse(0);	
	}
}
