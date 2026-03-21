package org.bear.util.distribution;
import org.bear.dao.ThreeBigExchangeDao;
import org.bear.parser.TwseThreeBigExchangeParser;
/**
 * 設定URL，擷取三大法人買賣超個股資料
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
		this.url = url + "&date=" + date + "&selectType=ALLBUT0999&response=html";
	}
	
	public void getContent()
	{
		TwseThreeBigExchangeParser parser = new TwseThreeBigExchangeParser();
		parser.setDao(dao);
		parser.setDate(date);
		parser.setUrl(url);
		parser.getConnection();
		parser.parse(0);
	}
}
