package org.bear.main;
import java.util.HashMap;

import org.bear.dao.JuristicDailyReportDao;
import org.bear.dao.ThreeBigExchangeDao;
import org.bear.kd.GoodInfoRequest;
import org.bear.parser.EtfParser;
import org.bear.parser.RankingParser;
import org.bear.parser.TaifexLotParser;
import org.bear.parser.TaifexMtxParser;
import org.bear.parser.TpexThreeBigExchangeParser;
import org.bear.parser.TwseDailyDataParser;
import org.bear.parser.TwseStockLendingParser;
import org.bear.parser.TwseThreeBigAmountParser;
import org.bear.util.StringUtil;
import org.bear.util.distribution.GetMtxTotalOi;
import org.bear.util.distribution.GetPutCallPower;
import org.bear.util.distribution.GetTaifexLot;
import org.bear.util.distribution.GetTaifexOption;
import org.bear.util.distribution.GetTaifexTopTen;
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
	JuristicDailyReportDao juristicDailyReportDao = (JuristicDailyReportDao)context.getBean("juristicDailyReportDao");
	public static void main(String[] args)
	{
		/*
		String[] date = {"106/02/03", "106/02/03",
		         "106/02/06", "106/02/07", "106/02/08", "106/02/09", "106/02/10",
		         "106/02/13", "106/02/14", "106/02/15", "106/02/16", "106/02/17",
		         "106/02/20", "106/02/21", "106/02/22", "106/02/23", "106/02/24",
		         "106/03/01", "106/03/02", "106/03/03", "106/03/06", "106/03/07",
		         "106/03/01", "106/03/02", "106/03/03", 
		         "106/03/06", "106/03/07", "106/03/08", "106/03/09", "106/03/10",
		         "106/03/13", "106/03/14", "106/03/15", "106/03/16", "106/03/17",
		         "106/03/20", "106/03/21", "106/03/22", "106/03/23", "106/03/24",
		         "106/03/27", "106/03/28", "106/03/29", "106/03/30", "106/03/31",
		         };
		String[] date = {"106/04/05", "106/04/06", "106/04/07",
		         "106/04/10", "106/04/11", "106/04/12", "106/04/13", "106/04/14",
		         "106/04/17", "106/04/18", "106/04/19", "106/04/20", "106/04/21",
		         "106/04/24", "106/04/25", "106/04/26", "106/04/27", "106/04/28",
		         "106/05/02", "106/05/03", "106/05/04", "106/05/05",
		         "106/05/08", "106/05/09", "106/05/10", "106/05/11", "106/05/12",
		         "106/05/15", "106/05/16", "106/05/17", "106/05/18", "106/05/19",
		         "106/05/22", "106/05/23", "106/05/24", "106/05/25", "106/05/26", "106/05/31",
		         "106/06/01", "106/06/02", "106/06/03", 
		         "106/06/05", "106/06/06", "106/06/07", "106/06/08", "106/06/09", 
				 };
		String[] date = {"106/06/12", "106/06/13", "106/06/14", "106/06/15", "106/06/16", 
                "106/06/19", "106/06/20", "106/06/21", "106/06/22", "106/06/23",
                "106/06/26", "106/06/27", "106/06/28", "106/06/29", "106/06/30",
                "106/07/03", "106/07/04", "106/07/05", "106/07/06", "106/07/07",
                "106/07/10", "106/07/11", "106/07/12", "106/07/13", "106/07/14",
                "106/07/17", "106/07/18", "106/07/19", "106/07/20", "106/07/21",
                "106/07/24", "106/07/25", "106/07/26", "106/07/27", "106/07/28", "106/07/31",
                "106/08/01", "106/08/02", "106/08/03", "106/08/04",
                "106/08/07", "106/08/08", "106/08/09", "106/08/10", "106/08/11",
                "106/08/14", "106/08/15", "106/08/16", "106/08/17", "106/08/18",
                "106/08/21", "106/08/22", "106/08/23", "106/08/24", "106/08/25",
                "106/08/28", "106/08/29", "106/08/30", "106/08/31"
                };*/
		/*
		String[] date = {"106/09/01", "106/09/04", "106/09/05", "106/09/06", "106/09/07", "106/09/08",
		         "106/09/11", "106/09/12", "106/09/13", "106/09/14", "106/09/15",
		         "106/09/18", "106/09/19", "106/09/20", "106/09/21", "106/09/22",
		         "106/09/25", "106/09/26", "106/09/27", "106/09/28", "106/09/29", "106/09/30",
		         "106/10/02", "106/10/03", "106/10/05", "106/10/06",
		         "106/10/11", "106/10/12", "106/10/13", 
		         "106/10/16", "106/10/17", "106/10/18", "106/10/19", "106/10/20",
		         "106/10/23", "106/10/24", "106/10/25", "106/10/26", "106/10/27",
		         "106/10/30", "106/10/31",
		         "106/11/01", "106/11/02", "106/11/03", 
		         "106/11/06", "106/11/07", "106/11/08", "106/11/09", "106/11/10",
		         "106/11/13", "106/11/14", "106/11/15", "106/11/16", "106/11/17",
				 "106/11/20", "106/11/21", "106/11/22", "106/11/23", "106/11/24",
				 "106/11/27", "106/11/28", "106/11/29", "106/11/30",
				 "106/12/01", "106/12/04", "106/12/05", "106/12/06", "106/12/07", "106/12/08",				 
		};*/
		/*
 		String[] date = {"106/11/01", "106/11/02", "106/11/03", 
		                 "106/11/06", "106/11/07", "106/11/08", "106/11/09", "106/11/10",
		                 "106/11/13", "106/11/14", "106/11/15", "106/11/16", "106/11/17",
				         "106/11/20", "106/11/21", "106/11/22", "106/11/23", "106/11/24",
				         "106/11/27", "106/11/28", "106/11/29", "106/11/30",
				         "106/12/01", "106/12/04", "106/12/05", "106/12/06", "106/12/07", "106/12/08",
				         "106/12/11", "106/12/12", "106/12/13", "106/12/14", "106/12/15",
				         "106/12/18", "106/12/19", "106/12/20", "106/12/21", "106/12/22",
				         "106/12/25", "106/12/26", "106/12/27", "106/12/28", "106/12/29",
				         "107/01/02", "107/01/03", "107/01/04", "107/01/05"
				         };*/
		String[] date = {"108/11/25"};
		BuildThreeBigExchange trader = new BuildThreeBigExchange();
		trader.update(date);
		
	}
	public void update(String[] date)
	{
		for (int i = 0; i < date.length; i++)
		{
			//把民國轉換成西元
			String[] dateArray = date[i].split("/");
			String westenDate = StringUtil.convertYear(dateArray[0]);
			//String westenYear = westenDate;
			westenDate = westenDate + "/" + dateArray[1] + "/" + dateArray[2];
			String url;
			BuildThreeBigExchange exchange = new BuildThreeBigExchange();
			//上市，外資&投信			
			url = "https://www.twse.com.tw/fund/T86?response=html";
			exchange.buildTwse(westenDate.replace("/", ""), 1, url);
			//上櫃，外資&投信
			//url = "http://www.tpex.org.tw/web/stock/3insti/daily_trade/3itrade_hedge_print.php?l=en-us&se=EW&t=D&d=";
			url = "https://www.tpex.org.tw/web/stock/3insti/daily_trade/3itrade_hedge_result.php?l=en-us&se=EW&t=D&d=";
			exchange.buildTpex(westenDate, 2, url);	
			//證交所，三大法人買賣超金額
			url = "https://www.twse.com.tw/fund/BFI82U?response=html";
			exchange.buildJuristicAmountInfo(westenDate.replace("/", ""), url);
			//期交所，外資未平倉口數
			url = "https://www.taifex.com.tw/cht/3/futContractsDate";
			exchange.buildTaiFexLot(westenDate, url, "", new TaifexLotParser(), 3);
			//期交所前十大法人未沖銷部位
			url = "https://www.taifex.com.tw/cht/3/largeTraderFutQry";
			exchange.buildTopTen(westenDate, url);
			//期交所外資/自營商選擇權未平倉餘額
			url = "https://www.taifex.com.tw/cht/3/callsAndPutsDate";
			exchange.buildOption(westenDate, url);
			//大盤指數&成交量
			url = "https://www.twse.com.tw/exchangeReport/FMTQIK?response=html";
			exchange.buildIndex(westenDate.replace("/", ""), url);	
			System.out.println(url);
			//大盤指數&成交量
			//url = "http://www.twse.com.tw/en/trading/exchange/FMTQIK/genpage/Report";
			//url = url + westenYear + dateArray[1] + "/" + westenYear + dateArray[1] + "_F3_1_2.php?STK_NO=&myear=" + 
			//westenYear + "&mmon=" + dateArray[1];			
			//exchange.buildVolumn(westenDate, url);
			//外資借券
			url = "https://www.twse.com.tw/exchangeReport/TWT93U?response=html";
			exchange.buildStockLending(westenDate.replace("/", ""), url);
			//小台三大法人未平倉口數
			url = "https://www.taifex.com.tw/cht/3/futContractsDate";
			exchange.buildTaiFexLot(westenDate, url, "MXF", new TaifexMtxParser(), 3);
			//小台未平倉餘額
			url = "https://www.taifex.com.tw/cht/3/futDailyMarketReport";
			exchange.buildMtxOi(westenDate, url);
			System.out.println(url);
			//00632R
			url = "https://www.twse.com.tw/fund/MI_QFIIS?response=html&selectType=0099P&date=" + westenDate.replace("/", "");
			this.setT50R(url, westenDate);
			//Put Call Power
			url = "https://www.taifex.com.tw/cht/3/optDailyMarketReport";
			this.setPutCallPower(westenDate, url);
			/*			
			//證交所投信買超排名
			url = "http://www.twse.com.tw/fund/TWT44U?response=html&date=";
			this.getRank(westenDate.replace("/", ""), url, "投信", westenDate);
			//證交所外資買超排名
			url = "http://www.twse.com.tw/fund/TWT38U?response=html&date=";
			this.getRank(westenDate.replace("/", ""), url, "外資", westenDate);
			//櫃買投信買超排名
			url = "http://www.tpex.org.tw/web/stock/3insti/sitc_trading/sitctr_print.php?l=zh-tw&t=D&type=buy&d=";
			this.getRank(date[i], url, "投信", westenDate);
			//櫃買外資買超排名
			url = "http://www.tpex.org.tw/web/stock/3insti/qfii_trading/forgtr_print.php?l=zh-tw&t=D&type=buy&d=";
			this.getRank(date[i], url, "外資", westenDate);
			/* 兩大排名, 20180107
			 * 根本不用去網頁擷取排名，資料庫都有資料，用order by就好，我他媽在耍什麼白癡 
			 */
			juristicDailyReportDao.updateRank("兩大", westenDate.replace("/", ""));
			juristicDailyReportDao.updateRank("外資", westenDate.replace("/", ""));
			juristicDailyReportDao.updateRank("投信", westenDate.replace("/", ""));
			//KD指標
			GoodInfoRequest request = new GoodInfoRequest();
			request.conn(true, westenDate);
			request.conn(false, westenDate);
			System.out.println(westenDate + " End!");
		}
	}
	/**
	 * 
	 * @param date Exchange Date
	 * @param exchanger 三大法人種類 
	 */
	public void buildTwse(String date, int stockBranch, String url)
	{		
		GetTwseThreeBigExchange getThreeBigExchange = new GetTwseThreeBigExchange();
		getThreeBigExchange.setDao(threeBigExchangeDao);
		getThreeBigExchange.setStockBranch(stockBranch);
		getThreeBigExchange.setDate(date);
		getThreeBigExchange.setUrl(url);
		getThreeBigExchange.getContent();
	}
	public void buildTpex(String date, int stockBranch, String url)
	{
		TpexThreeBigExchangeParser parser = new TpexThreeBigExchangeParser();
		parser.setDao(threeBigExchangeDao);
		parser.setStockBranch(stockBranch);
		parser.setUrl(url + date + "&o=htm");
		parser.setDate(date);
		parser.getConnection();
		parser.parse();
	}
	public void buildJuristicAmountInfo(String date, String url)
	{
		//證交所三大法人買賣超金額
		TwseThreeBigAmountParser parser = new TwseThreeBigAmountParser();
		parser.setDao(juristicDailyReportDao);
		parser.setUrl(url + "&dayDate=" + date);
		parser.setDate(date);
		parser.getConnection();
		parser.parseHttpGet(0);
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
		TwseDailyDataParser parser = new TwseDailyDataParser();
		parser.setDao(juristicDailyReportDao);
		parser.setUrl(url + "&date=" + date);
		parser.setDate(date);
		parser.getConnection();
		parser.parseHttpGet(0);
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
		TwseStockLendingParser parser = new TwseStockLendingParser();
		parser.setDao(juristicDailyReportDao);
		parser.setUrl(url + "&date=" + date);
		parser.setDate(date);
		parser.getConnection();
		parser.parseHttpGet(0);
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
	//00632R
	public void setT50R(String url, String date)
	{
		HashMap<String, Boolean> stockNo = new HashMap<String, Boolean>();
		stockNo.put("00632R", true);
		HashMap<Integer, Boolean> stockColumn = new HashMap<Integer, Boolean>();
		stockColumn.put(5, true);
		EtfParser parser = new EtfParser();
		parser.setDao(juristicDailyReportDao);
		parser.setUrl(url);
		parser.setDate(date);
		parser.setStockColumn(stockColumn);
		parser.setStockNo(stockNo);
		parser.getConnection();
		parser.parseHttpGet(0);
	}
	//Put Call Power
	public void setPutCallPower(String date, String url)
	{
		GetPutCallPower power = new GetPutCallPower();
		power.setDao(juristicDailyReportDao);
		power.setDate(date);
		power.setUrl(url);
		power.getContent();
	}
	public void getRank(String date, String url, String buyer, String sqlDate)
	{
		RankingParser parser = new RankingParser();
		parser.setThreeBigExchangeDao(threeBigExchangeDao);
		parser.setUrl(url + date);
		parser.setDate(date);
		parser.setBuyer(buyer);
		parser.setSqlDate(sqlDate);
		parser.getConnection();		
		parser.parseHttpGet(0);
	}
}
