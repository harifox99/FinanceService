package org.bear.main;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import org.bear.dao.BasicStockDao;
import org.bear.dao.DailyPriceDao;
import org.bear.dao.JuristicDailyReportDao;
import org.bear.dao.ThreeBigExchangeDao;
import org.bear.datainput.GetDailyPrice;
import org.bear.datainput.UpdateTpexPrice;
import org.bear.kd.GoodInfoRequest;
import org.bear.parser.EtfParser;
import org.bear.parser.RankingParser;
import org.bear.parser.TaifexLotParser;
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
public class BuildTopThreeExchange {

	/**
	 * @param args
	 */
	ApplicationContext context = new ClassPathXmlApplicationContext("config.xml");
	ThreeBigExchangeDao threeBigExchangeDao = (ThreeBigExchangeDao)context.getBean("threeBigExchangeDao");
	JuristicDailyReportDao juristicDailyReportDao = (JuristicDailyReportDao)context.getBean("juristicDailyReportDao");
	DailyPriceDao dailyPriceDao = (DailyPriceDao)context.getBean("dailyPriceDao");
	BasicStockDao basicStockDao = (BasicStockDao)context.getBean("basicStockDao");
	public static void main(String[] args)
	{
		String[] date = {"114/03/10"};
		BuildTopThreeExchange trader = new BuildTopThreeExchange();
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
			//上市，外資&投信
			url = "https://www.twse.com.tw/rwd/zh/fund/T86?";
			this.buildTwse(westenDate.replace("/", ""), 1, url);
			//上櫃，外資&投信
			url = "http://www.tpex.org.tw/web/stock/3insti/daily_trade/3itrade_hedge_print.php?l=en-us&se=EW&t=D&d=";
			url = "https://www.tpex.org.tw/web/stock/3insti/daily_trade/3itrade_hedge_result.php?l=en-us&se=EW&t=D&d=";
			this.buildTpex(westenDate, 2, url);
			//證交所，三大法人買賣超金額
			url = "https://www.twse.com.tw/fund/BFI82U?response=html";
			this.buildJuristicAmountInfo(westenDate.replace("/", ""), url);
			//期交所，外資未平倉口數
			url = "https://www.taifex.com.tw/cht/3/futContractsDate";
			this.buildTaiFexLot(westenDate, url, "", new TaifexLotParser(), 0);
			//期交所前十大法人未沖銷部位
			url = "https://www.taifex.com.tw/cht/3/largeTraderFutQry";
			this.buildTopTen(westenDate, url);
			//期交所外資/自營商選擇權未平倉餘額
			url = "https://www.taifex.com.tw/cht/3/callsAndPutsDate";
			this.buildOption(westenDate, url);
			//大盤指數&成交量
			url = "https://www.twse.com.tw/exchangeReport/FMTQIK?response=html";
			this.buildIndex(westenDate.replace("/", ""), url);	
			System.out.println(url);
			//外資借券
			url = "https://www.twse.com.tw/exchangeReport/TWT93U?response=html";
			this.buildStockLending(westenDate.replace("/", ""), url);
			//小台三大法人未平倉口數
			//url = "https://www.taifex.com.tw/cht/3/futContractsDate";
			//this.buildTaiFexLot(westenDate, url, "MXF", new TaifexMtxParser(), 2);
			//小台未平倉餘額
			url = "https://www.taifex.com.tw/cht/3/futDailyMarketReport";
			this.buildMtxOi(westenDate, url);
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
			//每日成交資訊
			GetDailyPrice getDailyPrice = new GetDailyPrice();
			getDailyPrice.getContent(westenDate.replace("/", ""), "Big5", dailyPriceDao, basicStockDao);
			UpdateTpexPrice tpexPrice = new UpdateTpexPrice();
			tpexPrice.getContent(westenDate, "Big5", dailyPriceDao, basicStockDao);
			/* 
			Set <String> kdGolden = request.getKdGolden();
		    CronVcp cronVcp = new CronVcp();
		    cronVcp.goVcp(kdGolden, westenDate.replace("/", "-")); */
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
		try
		{
			GetTaifexLot getTaiFexForeignerLot = new GetTaifexLot();
			getTaiFexForeignerLot.setDao(juristicDailyReportDao);
			getTaiFexForeignerLot.setDate(date);
			getTaiFexForeignerLot.setUrl(url + "?queryType=1&goDay=&doQuery=1&dateaddcnt=&queryDate=" + 
			URLEncoder.encode(date, "UTF-8") + "&commodityId=");
			getTaiFexForeignerLot.setCommodityId(commodityId);
			getTaiFexForeignerLot.setParser(parser);
			getTaiFexForeignerLot.setTableIndex(tableIndex);
			getTaiFexForeignerLot.getContent();
		}
		catch (IOException ex)
		{
			ex.printStackTrace();
		}
	}
	public void buildTopTen(String date, String url) 
	{
		try 
		{
			//期交所前十大法人未沖銷部位
			GetTaifexTopTen getTaifexTopTen = new GetTaifexTopTen();
			getTaifexTopTen.setDao(juristicDailyReportDao);
			getTaifexTopTen.setDate(date);
			getTaifexTopTen.setUrl(url + "?datecount=&contractId2=&queryDate=" + URLEncoder.encode(date, "UTF-8") + "&contractId=all");
			getTaifexTopTen.getContent();
		} 
		catch (UnsupportedEncodingException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void buildOption(String date, String url)
	{
		try
		{
			//台指選擇權
			GetTaifexOption getTaifexOption = new GetTaifexOption();
			getTaifexOption.setDao(juristicDailyReportDao);
			getTaifexOption.setDate(date);
			getTaifexOption.setUrl(url + "?queryType=1&goDay=&doQuery=1&dateaddcnt=&queryDate=" + URLEncoder.encode(date, "UTF-8"));
			getTaifexOption.getContent();
		}
		catch (UnsupportedEncodingException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		try
		{
			GetMtxTotalOi mtx = new GetMtxTotalOi();
			mtx.setDao(juristicDailyReportDao);
			mtx.setDate(date);
			mtx.setUrl(url + "?queryType=2&marketCode=0&commodity_id=MTX&queryDate=" + URLEncoder.encode(date, "UTF-8") +
								"&MarketCode=0&commodity_idt=MTX");
			mtx.getContent();
		}
		catch (IOException ex)
		{
			ex.printStackTrace();
		}
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
		try
		{
			GetPutCallPower power = new GetPutCallPower();
			power.setDao(juristicDailyReportDao);
			power.setDate(date);
			power.setUrl(url + "?queryType=2&marketCode=0&commodity_id=TXO&queryDate=" + URLEncoder.encode(date, "UTF-8") +
								"&MarketCode=0&commodity_idt=TXO");
			power.getContent();
		}
		catch (IOException ex)
		{
			ex.printStackTrace();
		}
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
