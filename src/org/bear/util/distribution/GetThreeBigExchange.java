package org.bear.util.distribution;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.bear.dao.ThreeBigExchangeDao;
import org.bear.parser.ThreeBigExchangeParser;
import org.bear.util.HttpUtil;
/**
 * 擷取三大法人籌碼資料
 * @author edward
 *
 */
public class GetThreeBigExchange 
{
	ThreeBigExchangeDao dao;	
	String date;
	int stockBranch;
	String exchanger;
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
	public void getContent(String date)
	{
		ThreeBigExchangeParser parser = new ThreeBigExchangeParser();
		parser.setDao(dao);
		String url = "http://www.twse.com.tw/ch/trading/fund/TWT38U/TWT38U.php";
		List<NameValuePair> paramList = new ArrayList<NameValuePair>();
		paramList.add(new BasicNameValuePair("qdate", date));
		paramList.add(new BasicNameValuePair("sorting", "by_issue"));
		String responseString = HttpUtil.send(url, paramList, 1, "big5");
		System.out.println(responseString);
		parser.setResponseString(responseString);
		parser.setDate(date);
		parser.setExchanger(exchanger);
		parser.setDao(dao);
		parser.setStockBranch(stockBranch);
		parser.parse(0);	
	}
}
