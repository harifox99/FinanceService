package org.bear.main;
import org.bear.dao.ThreeBigExchangeDao;
import org.bear.parser.TpexThreeBigExchangeParser;
import org.bear.util.distribution.GetTwseThreeBigExchange;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
/**
 * 擷取三大法人買賣超資料
 * @author edward
 *
 */
public class BuildThreeBigExchange {

	/**
	 * @param args
	 */
	ApplicationContext context = new ClassPathXmlApplicationContext("config.xml");
	ThreeBigExchangeDao threeBigExchangeDao = (ThreeBigExchangeDao)context.getBean("threeBigExchangeDao");
	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
		String date = "104/07/15";
		String url;
		BuildThreeBigExchange exchange = new BuildThreeBigExchange();
		//上市，外資
		url = "http://www.twse.com.tw/ch/trading/fund/TWT38U/TWT38U.php";
		exchange.buildTwse(date, 1, "外資", url);
		//上市，投信
		url = "http://www.twse.com.tw/ch/trading/fund/TWT44U/TWT44U.php";
		exchange.buildTwse(date, 1, "投信", url);		
		//上櫃，外資，買超
		url = "http://www.tpex.org.tw/web/stock/3insti/qfii_trading/forgtr_print.php?l=zh-tw&t=D&type=buy&d=" +
		date + "&s=0,asc,1";
		exchange.buildTpex(date, 2, "外資", url);	
		//上櫃，外資，賣超
		url = "http://www.tpex.org.tw/web/stock/3insti/qfii_trading/forgtr_print.php?l=zh-tw&t=D&type=sell&d=" +
		date + "&s=0,asc,1";		
		exchange.buildTpex(date, 2, "外資", url);	
		//上櫃，投信，買超
		url = "http://www.tpex.org.tw/web/stock/3insti/sitc_trading/sitctr_print.php?l=zh-tw&t=D&type=buy&d=" + date;
		exchange.buildTpex(date, 2, "投信", url);	
		//上櫃，投信，賣超
		url = "http://www.tpex.org.tw/web/stock/3insti/sitc_trading/sitctr_print.php?l=zh-tw&t=D&type=sell&d=" + date;
		exchange.buildTpex(date, 2, "投信", url);	
		
	}
	/**
	 * 
	 * @param date Exchange Date
	 * @param stockBranch 1:上市/2:上櫃
	 * @param exchanger 三大法人種類 
	 */
	public void buildTwse(String date, int stockBranch, String exchanger, String url)
	{		
		GetTwseThreeBigExchange getThreeBigExchange = new GetTwseThreeBigExchange();
		getThreeBigExchange.setDao(threeBigExchangeDao);
		getThreeBigExchange.setExchanger(exchanger);
		getThreeBigExchange.setStockBranch(stockBranch);
		getThreeBigExchange.setUrl(url);
		getThreeBigExchange.setDate(date);
		getThreeBigExchange.getContent();
	}
	public void buildTpex(String date, int stockBranch, String exchanger, String url)
	{
		TpexThreeBigExchangeParser parser = new TpexThreeBigExchangeParser();
		parser.setDao(threeBigExchangeDao);
		parser.setExchanger(exchanger);
		parser.setStockBranch(stockBranch);
		parser.setUrl(url);
		parser.setDate(date);
		parser.getConnection();
		parser.parse();
	}

}
