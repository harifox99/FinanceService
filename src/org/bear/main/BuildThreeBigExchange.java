package org.bear.main;
import org.bear.dao.JuristicDailyReportDao;
import org.bear.dao.ThreeBigExchangeDao;
import org.bear.parser.TaifexLotParser;
import org.bear.parser.TaifexMtxParser;
import org.bear.parser.TpexThreeBigExchangeParser;
import org.bear.parser.TwseDailyDataParser;
import org.bear.parser.TwseStockLendingParser;
import org.bear.parser.TwseThreeBigAmountParser;
import org.bear.util.StringUtil;
import org.bear.util.distribution.GetMtxTotalOi;
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
		String[] date = {"106/08/14"};
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
			url = "http://www.tse.com.tw/fund/T86?response=html";
			exchange.buildTwse(westenDate.replace("/", ""), 1, url);
			//上櫃，外資&投信
			url = "http://www.tpex.org.tw/web/stock/3insti/daily_trade/3itrade_hedge_print.php?l=en-us&se=EW&t=D&d=";
			exchange.buildTpex(westenDate, 2, url);	
			//證交所，三大法人買賣超金額
			url = "http://www.tse.com.tw/fund/BFI82U?response=html";
			exchange.buildJuristicAmountInfo(westenDate.replace("/", ""), url);
			//期交所，外資未平倉口數
			url = "http://www.taifex.com.tw/chinese/3/7_12_3_tbl.asp";
			exchange.buildTaiFexLot(westenDate, url, "", new TaifexLotParser(), 1);
			//期交所前十大法人未沖銷部位
			url = "http://www.taifex.com.tw/chinese/3/7_8.asp";
			exchange.buildTopTen(westenDate, url);
			//期交所外資/自營商未平倉餘額
			url = "http://www.taifex.com.tw/chinese/3/7_12_5.asp";
			exchange.buildOption(westenDate, url);
			//大盤指數&成交量
			url = "http://www.tse.com.tw/exchangeReport/FMTQIK?response=html";
			exchange.buildIndex(westenDate.replace("/", ""), url);	
			System.out.println(url);
			//大盤指數&成交量
			//url = "http://www.twse.com.tw/en/trading/exchange/FMTQIK/genpage/Report";
			//url = url + westenYear + dateArray[1] + "/" + westenYear + dateArray[1] + "_F3_1_2.php?STK_NO=&myear=" + 
			//westenYear + "&mmon=" + dateArray[1];			
			//exchange.buildVolumn(westenDate, url);
			//外資借券
			url = "http://www.twse.com.tw/exchangeReport/TWT93U?response=html";
			exchange.buildStockLending(westenDate.replace("/", ""), url);
			//小台三大法人未平倉口數
			url = "http://www.taifex.com.tw/chinese/3/7_12_3.asp";
			exchange.buildTaiFexLot(westenDate, url, "MXF", new TaifexMtxParser(), 3);
			//小台未平倉餘額
			url = "http://www.taifex.com.tw/chinese/3/3_1_1.asp";
			exchange.buildMtxOi(westenDate, url);
			System.out.println(url);
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
		parser.setUrl(url + date);
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
}
