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
		new BuildThreeBigExchange(date, 1, "外資");
	}
	/**
	 * 
	 * @param date Exchange Date
	 * @param stockBranch 1:上市/2:上櫃
	 * @param exchanger 三大法人種類 
	 */
	public BuildThreeBigExchange(String date, int stockBranch, String exchanger)
	{
		ApplicationContext context = new ClassPathXmlApplicationContext("config.xml");
		ThreeBigExchangeDao threeBigExchangeDao = (ThreeBigExchangeDao)context.getBean("threeBigExchangeDao");
		GetThreeBigExchange getThreeBigExchange = new GetThreeBigExchange();
		getThreeBigExchange.setDao(threeBigExchangeDao);
		getThreeBigExchange.setExchanger(exchanger);
		getThreeBigExchange.setStockBranch(stockBranch);
		getThreeBigExchange.getContent(date);
	}
	

}
