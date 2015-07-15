package org.bear.main;
import org.bear.dao.ThreeBigExchangeDao;
import org.bear.util.distribution.GetThreeBigExchange;
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
	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
		String date = "104/07/14";
		String url;
		//上市，外資
		url = "http://www.twse.com.tw/ch/trading/fund/TWT38U/TWT38U.php";
		new BuildThreeBigExchange(date, 1, "外資", url);
		//上市，投信
		url = "http://www.twse.com.tw/ch/trading/fund/TWT44U/TWT44U.php";
		new BuildThreeBigExchange(date, 1, "投信", url);		
	}
	/**
	 * 
	 * @param date Exchange Date
	 * @param stockBranch 1:上市/2:上櫃
	 * @param exchanger 三大法人種類 
	 */
	public BuildThreeBigExchange(String date, int stockBranch, String exchanger, String url)
	{
		ApplicationContext context = new ClassPathXmlApplicationContext("config.xml");
		ThreeBigExchangeDao threeBigExchangeDao = (ThreeBigExchangeDao)context.getBean("threeBigExchangeDao");
		GetThreeBigExchange getThreeBigExchange = new GetThreeBigExchange();
		getThreeBigExchange.setDao(threeBigExchangeDao);
		getThreeBigExchange.setExchanger(exchanger);
		getThreeBigExchange.setStockBranch(stockBranch);
		getThreeBigExchange.setUrl(url);
		getThreeBigExchange.getContent(date);
	}
	

}
