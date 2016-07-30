package org.bear.main;

import org.bear.dao.JuristicDailyReportDao;
import org.bear.dao.ThreeBigExchangeDao;
import org.bear.parser.TaifexLotParser;
import org.bear.parser.TaifexMtxParser;
import org.bear.parser.TpexThreeBigExchangeParser;
import org.bear.parser.TwseDailyDataParser;
import org.bear.parser.TwseThreeBigAmountParser;
import org.bear.util.StringUtil;
import org.bear.util.distribution.GetMtxTotalOi;
import org.bear.util.distribution.GetTaifexLot;
import org.bear.util.distribution.GetTaifexOption;
import org.bear.util.distribution.GetTaifexTopTen;
import org.bear.util.distribution.GetTwseStockLending;
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
		/*
		String[] date = 
		{"104/07/27", "104/07/28", "104/07/29", "104/07/30", "104/07/31",
	 	 "104/08/03", "104/08/04", "104/08/05", "104/08/06", "104/08/07",
		 "104/08/10", "104/08/11", "104/08/12", "104/08/13", "104/08/14",
		 "104/08/17", "104/08/18", "104/08/19", "104/08/20", "104/08/21",
		 "104/08/24", "104/08/25", "104/08/26", "104/08/27", "104/08/28", "104/08/31",
		 "104/09/01", "104/09/02", "104/09/03", "104/09/04",
		 "104/09/07", "104/09/08", "104/09/09", "104/09/10", "104/09/11",
		 "104/09/14", "104/09/15", "104/09/16", "104/09/17", "104/09/18",
		 "104/09/21", "104/09/22", "104/09/23", "104/09/24", "104/09/25", "104/09/30",
		 "104/10/19", "104/10/20", "104/10/21", "104/10/22", "104/10/23",
		 "104/10/26", "104/10/27", "104/10/28", "104/10/29", "104/10/30",
		 "104/11/02", "104/11/03", "104/11/04", "104/11/05", "104/11/06",
		 "104/11/09", "104/11/10", "104/11/11", "104/11/12", "104/11/13",
		 "104/11/16", "104/11/17", "104/11/18", "104/11/19", "104/11/20",
		 "104/11/23", "104/11/24", "104/11/25", "104/11/26", "104/11/27", "104/11/30",
		 "104/12/01", "104/12/02", "104/12/03", "104/12/04"};
		
		String[] date = {"104/10/01", "104/10/02", "104/10/05", "104/10/06", "104/10/07",
						 "104/10/08", "104/10/12", "104/10/13", "104/10/14", "104/10/15", "104/10/16"};					 
		//String[] date = {"104/12/07", "104/12/08", "104/12/09", "104/12/10", "104/12/11"};
		//String[] date = {"104/12/14", "104/12/15", "104/12/16", "104/12/17", "104/12/18"};
		*/
		//String[] date = {"104/12/21", "104/12/22", "104/12/23", "104/12/24", "104/12/25"};
		//String[] date = {"104/12/28", "104/12/29", "104/12/30", "104/12/31"};
		//String[] date = {"105/01/04", "105/01/05", "105/01/06", "105/01/07", "105/01/08"};
		//String[] date = {"105/01/11", "105/01/12", "105/01/13", "105/01/14", "105/01/15"};
		//String[] date = {"105/01/18", "105/01/19", "105/01/20", "105/01/21", "105/01/22"};
		//String[] date = {"105/01/25", "105/01/26", "105/01/27", "105/01/28", "105/01/29"};
		//String[] date = {"105/02/01", "105/02/02", "105/02/03"};
		//String[] date = {"105/02/15", "105/02/16", "105/02/17", "105/02/18", "105/02/19"};
		//String[] date = {"105/02/22", "105/02/23", "105/02/24", "105/02/25", "105/02/26"};
		//String[] date = {"105/03/01", "105/03/02", "105/03/03", "105/03/04"};
		//String[] date = {"105/03/07", "105/03/08", "105/03/09", "105/03/10", "105/03/11"};
		//String[] date = {"105/03/21", "105/03/22", "105/03/23", "105/03/24", "105/03/25"};
		//String[] date = {"105/03/28", "105/03/29", "105/03/30", "105/03/31"};
		//String[] date = {"105/04/01", "105/04/06", "105/04/07", "105/04/08"};
		//String[] date = {"105/04/11", "105/04/12", "105/04/13", "105/04/14", "105/04/15"};
		//String[] date = {"105/04/18", "105/04/19", "105/04/20", "105/04/21", "105/04/22"};
		//String[] date = {"105/04/25", "105/04/26", "105/04/27", "105/04/28", "105/04/29"};
		//String[] date = {"105/05/03", "105/05/04", "105/05/05", "105/05/06"};
		//String[] date = {"105/05/09", "105/05/10", "105/05/11", "105/05/12", "105/05/13"};
		//String[] date = {"105/05/16", "105/05/17", "105/05/18", "105/05/19", "105/05/20"};
		//String[] date = {"105/05/23", "105/05/24", "105/05/25", "105/05/26", "105/05/27"};
		//String[] date = {"105/05/30", "105/05/31"};
		//String[] date = {"105/06/01", "105/06/02", "105/06/03", "105/06/04", "105/06/06", "105/06/07", "105/06/08"};
		//String[] date = {"105/06/13", "105/06/14", "105/06/15", "105/06/16", "105/06/17"};
		//String[] date = {"105/06/27", "105/06/28", "105/06/29", "105/06/30"};
		//String[] date = {"105/07/04", "105/07/05", "105/07/06", "105/07/07", "105/07/01"};
		//String[] date = {"105/07/11", "105/07/12", "105/07/13", "105/07/14", "105/07/15"};
		//String[] date = {"105/07/19", "105/07/20", "105/07/21", "105/07/22"};
		//String[] date = {"105/07/25", "105/07/26", "105/07/27", "105/07/28", "105/07/29"};
		String[] date = {"105/07/29"};
		for (int i = 0; i < date.length; i++)
		{
			//把民國轉換成西元
			String[] dateArray = date[i].split("/");
			String westenDate = StringUtil.convertYear(dateArray[0]);
			String westenYear = westenDate;
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
			exchange.buildTaiFexLot(westenDate, url, "", new TaifexLotParser(), 1);
			//期交所前十大法人未沖銷部位
			url = "http://www.taifex.com.tw/chinese/3/7_8.asp";
			exchange.buildTopTen(westenDate, url);
			//期交所外資/自營商未平倉餘額
			url = "http://www.taifex.com.tw/chinese/3/7_12_5.asp";
			exchange.buildOption(westenDate, url);
			//大盤指數
			//url = "http://www.twse.com.tw/ch/trading/exchange/MI_INDEX/MI_INDEX.php";
			//exchange.buildIndex(date[i], url);			
			//大盤指數&成交量
			url = "http://www.twse.com.tw/en/trading/exchange/FMTQIK/genpage/Report";
			url = url + westenYear + dateArray[1] + "/" + westenYear + dateArray[1] + "_F3_1_2.php?STK_NO=&myear=" + 
			westenYear + "&mmon=" + dateArray[1];
			System.out.println(url);
			exchange.buildVolumn(westenDate, url);
			//外資借券
			url = "http://www.twse.com.tw/ch/trading/exchange/TWT93U/TWT93U.php";
			exchange.buildStockLending(date[i], url);
			//小台三大法人未平倉口數
			url = "http://www.taifex.com.tw/chinese/3/7_12_3.asp";
			exchange.buildTaiFexLot(westenDate, url, "MXF", new TaifexMtxParser(), 3);
			//小台未平倉餘額
			url = "http://www.taifex.com.tw/chinese/3/3_1_1.asp";
			exchange.buildMtxOi(westenDate, url);
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
	public void buildTaiFexLot(String date, String url, String commodityId, TaifexLotParser parser, int tableIndex)
	{
		//期交所外資未平倉口數
		GetTaifexLot getTaiFexForeignerLot = new GetTaifexLot();
		getTaiFexForeignerLot.setDao(juristicDailyReportDao);
		getTaiFexForeignerLot.setUrl(url);
		getTaiFexForeignerLot.setDate(date);
		getTaiFexForeignerLot.setCommodityId(commodityId);
		getTaiFexForeignerLot.setParser(parser);
		getTaiFexForeignerLot.setTableIndex(tableIndex);
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
	//上市指數，漲跌幅，成交量
	public void buildVolumn(String date, String url)
	{
        TwseDailyDataParser parser = new TwseDailyDataParser();			
        parser.setDao(juristicDailyReportDao);
		parser.setUrl(url);
		parser.setDate(date);
		parser.getConnection();
		parser.parse();
	}
	//外資借券
	public void buildStockLending(String date, String url)
	{
		GetTwseStockLending stockLending = new GetTwseStockLending();			
		stockLending.setDao(juristicDailyReportDao);
		stockLending.setUrl(url);
		stockLending.setDate(date);
		stockLending.getContent();
	}
	//小台未平倉餘額
	public void buildMtxOi(String date, String url)
	{
		GetMtxTotalOi getMtxTotalOi = new GetMtxTotalOi();
		getMtxTotalOi.setDao(juristicDailyReportDao);
		getMtxTotalOi.setDate(date);
		getMtxTotalOi.setUrl(url);
		getMtxTotalOi.getContent();
	}
}
