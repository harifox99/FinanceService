package org.bear.main;

import org.bear.dao.JuristicDailyReportDao;
import org.bear.dao.ThreeBigExchangeDao;
import org.bear.parser.TpexThreeBigExchangeParser;
import org.bear.parser.TwseThreeBigAmountParser;
import org.bear.util.StringUtil;
import org.bear.util.distribution.GetTaifexLot;
import org.bear.util.distribution.GetTaifexOption;
import org.bear.util.distribution.GetTaifexTopTen;
import org.bear.util.distribution.GetTwseThreeBigExchange;
import org.bear.util.distribution.TwseDailyIndex;
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
	JuristicDailyReportDao juristicDailyReportDao = (JuristicDailyReportDao)context.getBean("juristicDailyReportDao");
	public static void main(String[] args) 
	{
		//String[] date = {"104/07/01", "104/07/02", "104/07/03"};
		//String[] date = {"104/07/06", "104/07/07", "104/07/08", "104/07/09"};
		//String[] date = {"104/07/13", "104/07/14", "104/07/15", "104/07/16", "104/07/17"};
		//String[] date = {"104/07/20", "104/07/21", "104/07/22", "104/07/23", "104/07/24"};
		//String[] date = {"104/07/27", "104/07/28", "104/07/29", "104/07/30", "104/07/31"};
		//String[] date = {"104/08/03", "104/08/04", "104/08/05", "104/08/06", "104/08/07"};
		//String[] date = {"104/08/10", "104/08/11", "104/08/12", "104/08/13", "104/08/14"};
		String[] date = {"104/08/17", "104/08/18", "104/08/19", "104/08/20", "104/08/21"};
		// TODO Auto-generated method stub
		for (int i = 0; i < date.length; i++)
		{
			//把民國轉換成西元
			String[] dateArray = date[i].split("/");
			String westenDate = StringUtil.convertYear(dateArray[0]);
			westenDate = westenDate + "/" + dateArray[1] + "/" + dateArray[2];
			String url;		
			BuildThreeBigExchange exchange = new BuildThreeBigExchange();	
			//上市，外資
			url = "http://www.twse.com.tw/ch/trading/fund/TWT38U/TWT38U.php";
			exchange.buildTwse(date[i], 1, "外資", url);			
			//上市，投信
			url = "http://www.twse.com.tw/ch/trading/fund/TWT44U/TWT44U.php";
			exchange.buildTwse(date[i], 1, "投信", url);	
			//上櫃，外資，買超
			url = "http://www.tpex.org.tw/web/stock/3insti/qfii_trading/forgtr_print.php?l=zh-tw&t=D&type=buy&d=" +
			date[i] + "&s=0,asc,1";
			exchange.buildTpex(date[i], 2, "外資", url);	
			//上櫃，外資，賣超
			url = "http://www.tpex.org.tw/web/stock/3insti/qfii_trading/forgtr_print.php?l=zh-tw&t=D&type=sell&d=" +
			date[i] + "&s=0,asc,1";		
			exchange.buildTpex(date[i], 2, "外資", url);	
			//上櫃，投信，買超
			url = "http://www.tpex.org.tw/web/stock/3insti/sitc_trading/sitctr_print.php?l=zh-tw&t=D&type=buy&d=" + date[i];
			exchange.buildTpex(date[i], 2, "投信", url);	
			//上櫃，投信，賣超
			url = "http://www.tpex.org.tw/web/stock/3insti/sitc_trading/sitctr_print.php?l=zh-tw&t=D&type=sell&d=" + date[i];
			exchange.buildTpex(date[i], 2, "投信", url);	
			//證交所，三大法人買賣超金額
			url = "http://www.twse.com.tw/ch/trading/fund/BFI82U/BFI82U_print.php?begin_date=" + westenDate.replace("/", "") + 
				  "&end_date=" + westenDate.replace("/", "") + "&report_type=day&language=ch";
			exchange.buildJuristicAmountInfo(westenDate, url);
			//期交所，外資未平倉口數
			url = "http://www.taifex.com.tw/chinese/3/7_12_3_tbl.asp";
			exchange.buildTaiFexLot(westenDate, url);
			//期交所前十大法人未沖銷部位
			url = "http://www.taifex.com.tw/chinese/3/7_8.asp";
			exchange.buildTopTen(westenDate, url);
			//期交所外資/自營商未平倉餘額
			url = "http://www.taifex.com.tw/chinese/3/7_12_5.asp";
			exchange.buildOption(westenDate, url);
			//大盤指數
			url = "http://www.twse.com.tw/ch/trading/exchange/MI_INDEX/MI_INDEX.php";
			exchange.buildIndex(date[i], url);
		}
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
	public void buildJuristicAmountInfo(String date, String url)
	{
		//證交所三大法人買賣超金額
		TwseThreeBigAmountParser parser = new TwseThreeBigAmountParser();
		parser.setDao(juristicDailyReportDao);
		parser.setUrl(url);
		parser.setDate(date);
		parser.getConnection();
		parser.parse();
	}
	public void buildTaiFexLot(String date, String url)
	{
		//期交所外資未平倉口數
		GetTaifexLot getTaiFexForeignerLot = new GetTaifexLot();
		getTaiFexForeignerLot.setDao(juristicDailyReportDao);
		getTaiFexForeignerLot.setUrl(url);
		getTaiFexForeignerLot.setDate(date);
		getTaiFexForeignerLot.getContent();
	}
	public void buildTopTen(String date, String url)
	{
		//期交所前十大法人未沖銷部位
		GetTaifexTopTen getTaifexTopTen = new GetTaifexTopTen();
		getTaifexTopTen.setDao(juristicDailyReportDao);
		getTaifexTopTen.setUrl(url);
		getTaifexTopTen.setDate(date);
		getTaifexTopTen.getContent();
		
	}
	public void buildOption(String date, String url)
	{
		//台指選擇權
		GetTaifexOption getTaifexOption = new GetTaifexOption();
		getTaifexOption.setDao(juristicDailyReportDao);
		getTaifexOption.setUrl(url);
		getTaifexOption.setDate(date);
		getTaifexOption.getContent();
	}
	public void buildIndex(String date, String url)
	{
		//上市指數
		TwseDailyIndex twseDailyIndex = new TwseDailyIndex();
		twseDailyIndex.setDao(juristicDailyReportDao);
		twseDailyIndex.setUrl(url);
		twseDailyIndex.setDate(date);
		twseDailyIndex.getContent();
		
	}
}
