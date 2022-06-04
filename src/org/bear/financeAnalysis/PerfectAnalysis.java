package org.bear.financeAnalysis;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.bear.dao.BasicStockDao;
import org.bear.dao.CashFlowsDao;
import org.bear.dao.IncomeStatementDao;
import org.bear.dao.RevenueDao;
import org.bear.entity.BasicStockWrapper;
import org.bear.entity.CashFlowsEntity;
import org.bear.entity.IncomeStatementEntity;
import org.bear.entity.PeterLynchWrapper;
import org.bear.entity.RevenueEntity;
import org.bear.parser.TpexPriceParser;
import org.bear.parser.TwsePriceParser;
import org.bear.util.GetTpexPbeRatio;
import org.bear.util.GetTwsePbeRatio;
import org.bear.util.ReverseUtil;
import org.bear.util.StringUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;

public class PerfectAnalysis 
{	
	//最多只看過去4個月成長
	final int maxMonth = 4;
	//最多只看過去2季
	final int maxSeasons = 2;
	//可以繼續掃瞄的股票代碼
	List<List<Double>> legalStockIdList = new ArrayList<List<Double>>();
	//Column Name
	List<String> columnNameList = new ArrayList<String>(); 
	/**
	 * 
	 * @param yoyTotalMonth 總月份數 (YoY)
	 * @param yoyGrowMonth 期望的月份數 (YoY)
	 * @param demandOperatingProfit 期望的連續營業利益年增率上升季數
	 * @param demandGrossProfit 期望的毛利率上升季數
	 * @param demandOperatingProfitRatio 期望的連續營業利益率年增率上升季數
	 * @param demandEps 期望的連續EPS年增率上升季數
	 * @param expectedGrossProfitRatio 期望的毛利率
	 * @param operatingProfitRatio 期望的營業利益率
	 * @param expectedPe 期望的PE
	 * @param isMinusRevenueGrowth 過濾營收零成長標的
	 * @param isMinusProfitGrowth 過濾毛利/營業利益/稅前淨利成長標的 
	 * (這個值如果為true，則最新一季的毛利/營業利益/稅前淨利一定要為正數，反之則不需要符合此條件)
	 * @param isFreeCashFlow 過去8季自由現金流總和 
	 * @param isOperatingCashFlow 過去8季營運現金流，至少5季 > 0
	 * @param isNonOperating 業外收入佔稅前淨利比，過去5年在+-20%之內
	 * @param isComparePrice 過濾近期漲幅已達(%)
	 * @param priceRate 漲幅比例
	 * @param peDate 證交所本益比/股價 (日期)
	 * @param compareDate 某個時間股價 (通常是6個月)
	 * @param isMergeChineseYear 一二月營收是否合併計算
	 * @param isSpecificDate 篩選日期
	 * @param specificYear 篩選年
	 * @param specificMonth 篩選月
	 * @return
	 */
	public List<List<String>> analysis(int yoyTotalMonth, int yoyGrowMonth, int demandOperatingProfit,
			int demandGrossProfit, int demandOperatingProfitRatio, int demandEps,
			int expectedGrossProfitRatio, int operatingProfitRatio, int expectedPe, 
			boolean isMinusRevenueGrowth, boolean isMinusProfitGrowth, 
			boolean isFreeCashFlow, boolean isOperatingCashFlow, boolean isNonOperating,
			boolean isComparePrice, int priceRate, String peDate, String compareDate, boolean isMergeChineseYear,
			boolean isSpecificDate, String specificYear, String specificMonth)
	{		
		ApplicationContext context = new ClassPathXmlApplicationContext("config.xml");
		BasicStockDao basicStockDao = (BasicStockDao)context.getBean("basicStockDao");
		RevenueDao revenueDao = (RevenueDao)context.getBean("revenueDao");
		IncomeStatementDao incomeStatementDao = (IncomeStatementDao)context.getBean("basicIncomeStatementDao");
		CashFlowsDao cashFlowsDao = (CashFlowsDao)context.getBean("basicCashFlowsDao");
		//股票列表
		List<BasicStockWrapper> stockList = basicStockDao.findAllData();
		//最終結果
		List<List<String>> perfectList = new ArrayList<List<String>>();
		//暫時的計算結果
		List<List<String>> calculateList;		
		//掃瞄符合期望的YoY
		try
		{			
			//Get PE/PB Ratio
			//String date = "106/02/10";
			GetTwsePbeRatio twseRatio = new GetTwsePbeRatio();
			GetTpexPbeRatio tpexRatio = new GetTpexPbeRatio();
			twseRatio.setDate(peDate);
			twseRatio.getContent();
			tpexRatio.setDate(peDate);
			tpexRatio.getContent();
			HashMap<String, Double> hashPer = twseRatio.getHashPer();
			HashMap<String, Double> hashPbr = twseRatio.getHashPbr();
			hashPer.putAll(tpexRatio.getHashPer());
			hashPbr.putAll(tpexRatio.getHashPbr());
			columnNameList.add("股票代碼");
			columnNameList.add("股票名稱");
			calculateList = new ArrayList<List<String>>();
			boolean isSetColumnName = false;
			for (int i = 0; i < stockList.size(); i++)
			{
				String stockID = stockList.get(i).getStockID();
				String stockName = stockList.get(i).getStockName();
				//if (!stockID.equals("1539"))
					//continue;
				//System.out.println("stockID: " + stockID);
				List<RevenueEntity> revenueList;
				if (isMergeChineseYear)
				{
					if (isSpecificDate)
					{
						revenueList = revenueDao.findBySpecificDate(stockList.get(i).getStockID(), specificYear, specificMonth);
						if (revenueList.size() > 0)
							revenueList = revenueDao.findByLatestMergeSize(maxMonth+1, stockList.get(i).getStockID());
						else
							continue;
					}
					else
						revenueList = revenueDao.findByLatestMergeSize(maxMonth+1, stockList.get(i).getStockID());
				}
				else
				{
					if (isSpecificDate)
					{
						revenueList = revenueDao.findBySpecificDate(stockList.get(i).getStockID(), specificYear, specificMonth);
						if (revenueList.size() > 0)
							revenueList = revenueDao.findByLatestSize(maxMonth+1, stockList.get(i).getStockID());
						else
							continue;
					}
					else
						revenueList = revenueDao.findByLatestSize(maxMonth+1, stockList.get(i).getStockID());
				}
				//Set Column Name
				if (isSetColumnName == false)
				{
					for (int j = revenueList.size()-1; j >= 0; j--)
					{
						this.addColumnName(revenueList.get(j).getYearMonth(), "營收");
					}
					isSetColumnName = true;
				}
				List<String> yoyList = this.checkYoy(revenueList, yoyTotalMonth, yoyGrowMonth);
				if (yoyList != null)
				{					
					yoyList = ReverseUtil.reverse(yoyList);					
					yoyList.add(0, stockID);
					yoyList.add(1, stockName);
					//把經過第一關檢驗的股票代碼先暫存起來，第二關就不用掃瞄所有股票了
					perfectList.add(yoyList);				
				}
			}		
			calculateList = new ArrayList<List<String>>();
			//三個月營收平均
			columnNameList.add("三個月平均營收");
			for (int i = 0; i < perfectList.size(); i++)
			{
				double average = Double.parseDouble(perfectList.get(i).get(4)) + 
				Double.parseDouble(perfectList.get(i).get(5)) + Double.parseDouble(perfectList.get(i).get(6));
				average = average/3;
				average = StringUtil.setPointLength(average);
				perfectList.get(i).add(String.valueOf(average));
			}
			//毛利率季增數
			for (int i = 0; i < perfectList.size(); i++)
			{				
				String stockID = perfectList.get(i).get(0);
				//System.out.println("stockID: " + stockID);
				List<IncomeStatementEntity> entity = incomeStatementDao.findDataByLatest(maxSeasons+1, stockID);
				//Set Column Name
				if (i == 0)
				{
					for (int j = entity.size()-1; j >= 0; j--)
					{
						this.addColumnName(entity.get(j).getYear() + "-" + entity.get(j).getSeasons(), "毛利率");
					}
				}
				//毛利率
				List<String> rateList = this.checkProfitRatio(entity, demandGrossProfit, 0);
				if (rateList != null)
				{
					rateList = ReverseUtil.reverse(rateList);			
					//把毛利率直接附在營收YoY後面
					perfectList.get(i).addAll(rateList);
					//所有符合期望的資料暫存在calculateList
					calculateList.add(perfectList.get(i));
				}
			}
			//把所有符合期望的資料calculateList重新塞回perfectList，並以perfectList內的資料作進一步篩選
			perfectList = calculateList;
			calculateList = new ArrayList<List<String>>();
			
			//營業利益率年增數
			for (int i = 0; i < perfectList.size(); i++)
			{
				String stockID = perfectList.get(i).get(0);
				//System.out.println("stockID: " + stockID);
				List<IncomeStatementEntity> entity = incomeStatementDao.findDataByLatest(maxSeasons+1, stockID);
				//Set Column Name
				if (i == 0)
				{
					for (int j = entity.size()-1; j >= 0; j--)
					{
						this.addColumnName(entity.get(j).getYear() + "-" + entity.get(j).getSeasons(), "營業利益率");
					}
				}
				//營業利益率
				List<String> rateList = this.checkProfitRatioYoy(entity, demandOperatingProfitRatio, 1, incomeStatementDao);
				if (rateList != null)
				{
					rateList = ReverseUtil.reverse(rateList);		
					//把營業利益率直接附在毛利率後面
					perfectList.get(i).addAll(rateList);
					//所有符合期望的資料暫存在calculateList
					calculateList.add(perfectList.get(i));
				}
			}
			//把所有符合期望的資料calculateList重新塞回perfectList，並以perfectList內的資料作進一步篩選
			perfectList = calculateList;
			calculateList = new ArrayList<List<String>>();
						
			//期望EPS
			for (int i = 0; i < perfectList.size(); i++)
			{
				String stockID = perfectList.get(i).get(0);
				//System.out.println("stockID: " + stockID);
				List<IncomeStatementEntity> entity = incomeStatementDao.findDataByLatest(demandEps, stockID);
				if (i == 0)
				{
					for (int j = entity.size()-1; j >= 0; j--)
					{
						this.addColumnName(entity.get(j).getYear() + "-" + entity.get(j).getSeasons(), "EPS");
					}
				}
				//計算期望EPS
				List<String> rateList = this.checkEpsGrowth(entity, 
						incomeStatementDao, demandEps);
				if (rateList != null)
				{
					rateList = ReverseUtil.reverse(rateList);		
					//把EPS直接附在稅前淨利率後面
					perfectList.get(i).addAll(rateList);
					//所有符合期望的資料暫存在calculateList
					calculateList.add(perfectList.get(i));
				}
			}						
			perfectList = calculateList;
			calculateList = new ArrayList<List<String>>();
			
			//期望毛利率
			for (int i = 0; i < perfectList.size(); i++)
			{
				String stockID = perfectList.get(i).get(0);				
				List<IncomeStatementEntity> entity = incomeStatementDao.findDataByLatest(1, stockID);			
				if (this.latestRate(entity, expectedGrossProfitRatio, 0))
				{
					//所有符合期望的資料暫存在calculateList
					calculateList.add(perfectList.get(i));
				}				
			}
			//把所有符合期望的資料calculateList重新塞回perfectList，並以perfectList內的資料作進一步篩選
			perfectList = calculateList;
			calculateList = new ArrayList<List<String>>();
			
			//期望營業利益率
			for (int i = 0; i < perfectList.size(); i++)
			{
				String stockID = perfectList.get(i).get(0);				
				List<IncomeStatementEntity> entity = incomeStatementDao.findDataByLatest(1, stockID);			
				if (this.latestRate(entity, operatingProfitRatio, 1))
				{
					//所有符合期望的資料暫存在calculateList
					calculateList.add(perfectList.get(i));
				}				
			}
			perfectList = calculateList;
			calculateList = new ArrayList<List<String>>();
			
			//營業利益年增數
			for (int i = 0; i < perfectList.size(); i++)
			{
				String stockID = perfectList.get(i).get(0);
				//System.out.println("stockID: " + stockID);
				List<IncomeStatementEntity> entity = incomeStatementDao.findDataByLatest(maxSeasons+1, stockID);
				//營業利益
				if (checkProfitYoy(entity, demandOperatingProfit, 1, incomeStatementDao, isMinusProfitGrowth))
				{
					calculateList.add(perfectList.get(i));
				}
			}
			//把所有符合期望的資料calculateList重新塞回perfectList，並以perfectList內的資料作進一步篩選
			perfectList = calculateList;
			calculateList = new ArrayList<List<String>>();
			
			//至少要有N期營收是正的
			if (isMinusRevenueGrowth == true)
			{
				for (int i = 0; i < perfectList.size(); i++)
				{
					if (this.checkPlusRevenue(perfectList.get(i), yoyTotalMonth+1, yoyTotalMonth-1))
					{
						calculateList.add(perfectList.get(i));
					}
				}
				//把所有符合期望的資料calculateList重新塞回perfectList，並以perfectList內的資料作進一步篩選
				perfectList = calculateList;
				calculateList = new ArrayList<List<String>>();
			}
			
			//期望本益比
			for (int i = 0; i < perfectList.size(); i++)
			{
				String stockID = perfectList.get(i).get(0);		
				PeterLynchWrapper wrapper = this.checkPeRatio(expectedPe, stockID, hashPer, hashPbr);		
				if (i == 0)
				{
					columnNameList.add("本益比");
					columnNameList.add("股價淨值比");
					//columnNameList.add("股價");
				}
				if (wrapper != null)
				{
					//把PE, PB Ratio直接附在營業利益率後面
					perfectList.get(i).add(String.valueOf(wrapper.getPer()));
					perfectList.get(i).add(String.valueOf(wrapper.getPbr()));
					//perfectList.get(i).add(String.valueOf(wrapper.getPrice()));
					//所有符合期望的資料暫存在calculateList
					calculateList.add(perfectList.get(i));
				}
				//Thread.sleep(1000);
			}
			perfectList = calculateList;
			calculateList = new ArrayList<List<String>>();
			//過濾營建業
			for (int i = 0; i < perfectList.size(); i++)
			{
				String stockID = perfectList.get(i).get(0);
				BasicStockWrapper entity = basicStockDao.findBasicData(stockID);
				if (entity.getStockType() != 14)
				{
					calculateList.add(perfectList.get(i));
				}
			}
			perfectList = calculateList;
			calculateList = new ArrayList<List<String>>();
			//isFreeCashFlow 過去8季自由現金流總和 
			if (isFreeCashFlow == true)
			{
				int num = 8;
				for (int i = 0; i < perfectList.size(); i++)
				{
					String stockID = perfectList.get(i).get(0);
					List <CashFlowsEntity> wrapperList = cashFlowsDao.findLatest(stockID, num);
					if (this.checkFreeCashFlow(wrapperList, num))
						calculateList.add(perfectList.get(i));
				}
				perfectList = calculateList;
				calculateList = new ArrayList<List<String>>();
			}
			
			//isOperatingCashFlow 過去8季營運現金流，至少5季 > 0
			if (isOperatingCashFlow == true)
			{
				int num = 8;
				for (int i = 0; i < perfectList.size(); i++)
				{
					String stockID = perfectList.get(i).get(0);
					List <CashFlowsEntity> wrapperList = cashFlowsDao.findLatest(stockID, num);
					if (this.checkOperatingCashFlow(wrapperList, num, 5))
						calculateList.add(perfectList.get(i));
				}
				perfectList = calculateList;
				calculateList = new ArrayList<List<String>>();
			}
			
			//isNonOperating 業外收入佔稅前淨利比，過去5年在+-20%之內
			if (isNonOperating == true)
			{
				int num = 5;
				for (int i = 0; i < perfectList.size(); i++)
				{
					String stockID = perfectList.get(i).get(0);
					List <IncomeStatementEntity> wrapperList = incomeStatementDao.findDataByLatestYear(num, stockID);
					if (this.checkNonOperating(wrapperList, num))
						calculateList.add(perfectList.get(i));
				}				
				perfectList = calculateList;
				calculateList = new ArrayList<List<String>>();
			}
			
			//檢視近期漲幅，上市
			if (isComparePrice == true)
			{
				//計算最新股價
				String url = "https://www.twse.com.tw/exchangeReport/MI_INDEX?response=html&type=ALLBUT0999&date=";
				TwsePriceParser parser = new TwsePriceParser();
				//民國轉西元
				String[] dateArray = peDate.split("/");
				String year = StringUtil.convertYear(dateArray[0]);
				parser.setUrl(url + year + dateArray[1] + dateArray[2]);
				parser.getConnection();
				parser.parse(8);
				HashMap<String, Double> hashPrice = parser.getHashPrice();
				//計算某個日子股價 (通常是半年)
				parser = new TwsePriceParser();
				parser.setUrl(url + compareDate.replace("/", ""));
				parser.getConnection();
				parser.parse(8);
				HashMap<String, Double> previousPrice = parser.getHashPrice();
				columnNameList.add(compareDate.replace("/", "") + "\r\n" + "股價");
				columnNameList.add(year + dateArray[1] + dateArray[2] + "\r\n" + "最新股價");				
				//計算股價上漲幅度
				for (int i = 0; i < perfectList.size(); i++)
				{
					String stockID = perfectList.get(i).get(0);
					try
					{
						double rate = (double)hashPrice.get(stockID)/previousPrice.get(stockID) * 100 - 100;
						if (rate < priceRate)
						{							
							perfectList.get(i).add(String.valueOf(previousPrice.get(stockID)));
							perfectList.get(i).add(String.valueOf(hashPrice.get(stockID)));							
							calculateList.add(perfectList.get(i));
						}
					}
					catch (NullPointerException ex)
					{
						System.out.println("TWSE Stock price null: " + stockID);
						calculateList.add(perfectList.get(i));
					}
				}
				perfectList = calculateList;
				calculateList = new ArrayList<List<String>>();
			}
			
			//檢視近期漲幅，上櫃
			if (isComparePrice == true)
			{
				//計算最新股價
				String url = "https://www.tpex.org.tw/web/stock/aftertrading/otc_quotes_no1430/stk_wn1430_print.php?l=zh-tw&se=EW&s=0,asc,0&d=";
				TpexPriceParser parser = new TpexPriceParser();
				parser.setUrl(url + peDate);
				parser.getConnection();
				parser.parse(0);
				HashMap<String, Double> hashPrice = parser.getHashPrice();
				//計算某個日子股價 (通常是半年)
				parser = new TpexPriceParser();
				//西元轉民國
				String[] dateArray = compareDate.split("/");
				String year = StringUtil.convertChineseYear(dateArray[0]);
				parser.setUrl(url + year + "/" + dateArray[1] + "/" + dateArray[2]);
				parser.getConnection();
				parser.parse(0);
				HashMap<String, Double> previousPrice = parser.getHashPrice();	
				//計算股價上漲幅度
				for (int i = 0; i < perfectList.size(); i++)
				{
					String stockID = perfectList.get(i).get(0);
					try
					{
						double rate = (double)hashPrice.get(stockID)/previousPrice.get(stockID) * 100 - 100;
						if (rate < priceRate)
						{							
							perfectList.get(i).add(String.valueOf(previousPrice.get(stockID)));
							perfectList.get(i).add(String.valueOf(hashPrice.get(stockID)));							
							calculateList.add(perfectList.get(i));
						}
					}
					catch (NullPointerException ex)
					{
						System.out.println("TPEX Stock price null: " + stockID);
						calculateList.add(perfectList.get(i));
					}
				}
				perfectList = calculateList;
				calculateList = new ArrayList<List<String>>();
			}			
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		perfectList.add(0, columnNameList);
		return perfectList;
	}
	/**
	 * YoY月成長，期望M個月份有N個月份YoY上升
	 * @param revenue
	 * @param totalMonth 總月份M
	 * @param expectedMonth 期望月份N
	 * @return
	 */
	private List<String> checkYoy(List<RevenueEntity> revenue, int totalMonth, int expectedMonth)
	{
		List<String> yoyList = new ArrayList<String>();
		int difference = totalMonth - expectedMonth;
		for (int i = 0; i < maxMonth; i++)
		{
			//YoY Revenue
			if (revenue.get(i).getRevenue() == 0 || revenue.get(i).getLastRevenue() == 0 ||
				revenue.get(i+1).getRevenue() == 0 || revenue.get(i+1).getLastRevenue() == 0)
				return null;
			double thisMonthYoy = (double)revenue.get(i).getRevenue()/revenue.get(i).getLastRevenue();
			double lastMonthYoy = (double)revenue.get(i+1).getRevenue()/revenue.get(i+1).getLastRevenue();
			//如果本月營收 (thisMonthYoy < lastMonthYoy) 衰退
			if (i < totalMonth && thisMonthYoy < lastMonthYoy)
			{
				//期望M個月份有N個月份YoY上升
				//期望totalMonth個月份有expectedMonth個月份YoY上升
				//衰退幅度超越臨界值 (difference-- <= 0)
				if (difference-- <= 0)
					return null;
				else
				{
					thisMonthYoy -= 1;
					thisMonthYoy *= 100;
					NumberFormat formatter = new DecimalFormat(".##");
					String strRevenue = formatter.format(thisMonthYoy);
					yoyList.add(strRevenue);	
				}
			}		
			else
			{
				thisMonthYoy -= 1;
				thisMonthYoy *= 100;
				NumberFormat formatter = new DecimalFormat(".##");
				String strRevenue = formatter.format(thisMonthYoy);
				yoyList.add(strRevenue);	
			}
			//最舊的一個月的資料
			if (i == maxMonth-1)
			{
				lastMonthYoy -= 1;
				lastMonthYoy *= 100;
				NumberFormat formatter = new DecimalFormat(".##");
				String strRevenue = formatter.format(lastMonthYoy);
				yoyList.add(strRevenue);					
			}		
		}	
		
		return yoyList;
	}
	/**
	 * 
	 * @param entity
	 * @param expectedRate 期望利益率連續季增率
	 * @param type, 0 for 毛利率, 1 for 營業利益率, 2 for 稅前淨利率
	 * @return
	 */
	private List<String> checkProfitRatio(List<IncomeStatementEntity> entity, int expectedRate, int type)
	{	
		List<String> rateList = new ArrayList<String>();
		for (int i = 0; i < maxSeasons; i++)
		{
			double thisSeason = 0;
			double lastSeason = 0;
			switch (type) 
			{			
				//毛利率
				case 0:
					thisSeason = (double)entity.get(i).getGrossProfit() / entity.get(i).getOperatingRevenue();
					lastSeason = (double)entity.get(i+1).getGrossProfit() / entity.get(i+1).getOperatingRevenue();
					break;
				//營業利益率	
				case 1:	
					thisSeason = (double)entity.get(i).getOperatingIncome() / entity.get(i).getOperatingRevenue();
					lastSeason = (double)entity.get(i+1).getOperatingIncome() / entity.get(i+1).getOperatingRevenue();
					break;
				//稅前淨利率
				case 2:
					thisSeason = (double)entity.get(i).getPreTaxIncome() / entity.get(i).getOperatingRevenue();
					lastSeason = (double)entity.get(i+1).getPreTaxIncome() / entity.get(i+1).getOperatingRevenue();
					break;
			}
					
			if (i < expectedRate && thisSeason < lastSeason)
				return null;	
			else
			{
				thisSeason *= 100;
				NumberFormat formatter = new DecimalFormat(".##");
				String strRevenue = formatter.format(thisSeason);
				rateList.add(strRevenue);	
			}		
			if (i == maxSeasons-1)
			{
				lastSeason *= 100;
				NumberFormat formatter = new DecimalFormat(".##");
				String strRevenue = formatter.format(lastSeason);
				rateList.add(strRevenue);	
			}	
		}
		return rateList;
	}
	/**
	 * 計算期望的利益率是否達成最低要求
	 * @param entity
	 * @param expectedRatio 期望利益率
	 * @param type, 0 for 毛利率, 1 for 營業利益率, 2 for 稅前淨利率
	 * @return
	 */
	private boolean latestRate(List<IncomeStatementEntity> entity, int expectedRatio, int type)
	{
		for (int i = 0; i < entity.size(); i++)
		{
			double thisSeason = 0;
			switch (type) 
			{			
				//毛利率
				case 0:
					thisSeason = (double)entity.get(i).getGrossProfit() / entity.get(i).getOperatingRevenue();					
					break;
				//營業利益率	
				case 1:	
					thisSeason = (double)entity.get(i).getOperatingIncome() / entity.get(i).getOperatingRevenue();
					break;
				//稅前淨利率
				case 2:
					thisSeason = (double)entity.get(i).getPreTaxIncome() / entity.get(i).getOperatingRevenue();
					break;
			}
			//最新一季利益率
			if (i == 0)
			{
				if (thisSeason * 100 < expectedRatio)
				{
					return false;
				}
			}			
		}
		return true;
	}
	private PeterLynchWrapper checkPeRatio(int expectedPe, String stockID, 
			HashMap<String, Double> hashPer, HashMap<String, Double> hashPbr)
	{
		/* 計算P/E Ratio, P/B Ratio, 股價 */
		//GetURLCathayBasicData urlContent = new GetURLCathayBasicData(stockID);
		//BasicDataParserCathay parser = new BasicDataParserCathay(urlContent.getContent(), stockID);
		//parser.parse(2);
		System.out.println("stockID: " + stockID);
		try
		{
			PeterLynchWrapper wrapper = new PeterLynchWrapper();
			/* P/E Ratio */		
			wrapper.setPer(hashPer.get(stockID));
			/* P/B Ratio */
			wrapper.setPbr(hashPbr.get(stockID));
			//Price
			//ratioNumber = StringUtil.setPointLength(parser.getPrice());
			//wrapper.setPrice(ratioNumber);
			if (expectedPe > wrapper.getPer())
				return wrapper;
			else
				return null;
		}
		catch (NullPointerException ex)
		{
			return null;
		}
	}
	private void addColumnName(Date yearMonth, String comment)
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM"); 
		String dateString = dateFormat.format(yearMonth);
		columnNameList.add(dateString + comment);
	}
	private void addColumnName(String dateString, String comment)
	{
		columnNameList.add(dateString + "\n" + comment);
	}
	/**
	 * 
	 * @param entity
	 * @param incomeStatementDao
	 * @param demandEps 期望EPS年增率連續成長季數
	 * @return
	 */
	private List<String> checkEpsGrowth(List<IncomeStatementEntity> entity, 
			IncomeStatementDao incomeStatementDao, int demandEps)
	{
		List<String> rateList = new ArrayList<String>();
		for (int i = 0; i < demandEps; i++)
		{
			String stockID = entity.get(i).getStockID();
			String year = entity.get(i).getYear();
			String seasons = entity.get(i).getSeasons();
			//本季EPS
			double thisYearEps = entity.get(i).getEps();
			//擷取去年本季EPS
			int intYear = Integer.parseInt(year);
			year = String.valueOf(--intYear);
			System.out.println("stockID: " + stockID);
			IncomeStatementEntity lastEntity;
			try
			{
				lastEntity = incomeStatementDao.findSingleDataBySeason(stockID, year, seasons);
			}
			catch (EmptyResultDataAccessException ex)
			{
				System.out.println(stockID + "資料不足，無法計算！");
				return null;				
			}
			double lastYearEps = lastEntity.getEps();
			if (thisYearEps < lastYearEps && i < demandEps)
				return null;
			else
				rateList.add(String.valueOf(thisYearEps));				
		
		}
		return rateList;
	}
	/**
	 * 計算本季的利益率是否超越去年同期
	 * @param entity
	 * @param expectedRate, 期望利益率年增率連續成長季數
	 * @param type, 0 for 毛利率, 1 for 營業利益率, 2 for 稅前淨利率
	 * @param incomeStatementDao
	 * @return
	 */
	private List<String> checkProfitRatioYoy(List<IncomeStatementEntity> entity, 
			int expectedRate, int type, IncomeStatementDao incomeStatementDao)
	{	
		List<String> rateList = new ArrayList<String>();
		for (int i = 0; i < maxSeasons; i++)
		{
			double thisSeason = 0;
			double lastSeason = 0;
			String stockID = entity.get(i).getStockID();
			String year = entity.get(i).getYear();
			String seasons = entity.get(i).getSeasons();
			//擷取去年本季利益率用
			int intYear = Integer.parseInt(year);
			year = String.valueOf(--intYear);
			System.out.println("stockID: " + stockID);
			IncomeStatementEntity lastEntity;
			try
			{
				lastEntity = incomeStatementDao.findSingleDataBySeason(stockID, year, seasons);
			}
			catch (EmptyResultDataAccessException ex)
			{
				System.out.println(stockID + "資料不足，無法計算！");
				return null;				
			}
			switch (type) 
			{						
				//毛利率
				case 0:
					thisSeason = (double)entity.get(i).getGrossProfit() / entity.get(i).getOperatingRevenue();
					lastSeason = (double)lastEntity.getGrossProfit() / lastEntity.getOperatingRevenue();
					break;
				//營業利益率	
				case 1:	
					thisSeason = (double)entity.get(i).getOperatingIncome() / entity.get(i).getOperatingRevenue();
					lastSeason = (double)lastEntity.getOperatingIncome() / lastEntity.getOperatingRevenue();
					break;
				//稅前淨利率
				case 2:
					thisSeason = (double)entity.get(i).getPreTaxIncome() / entity.get(i).getOperatingRevenue();
					lastSeason = (double)lastEntity.getPreTaxIncome() / lastEntity.getOperatingRevenue();
					break;
			}
					
			if (i < expectedRate && thisSeason < lastSeason)
				return null;	
			else
			{
				thisSeason *= 100;
				NumberFormat formatter = new DecimalFormat(".##");
				String strRevenue = formatter.format(thisSeason);
				rateList.add(strRevenue);	
			}		
			if (i == maxSeasons-1)
			{
				lastSeason *= 100;
				NumberFormat formatter = new DecimalFormat(".##");
				String strRevenue = formatter.format(lastSeason);
				rateList.add(strRevenue);	
			}	
		}
		return rateList;
	}
	private boolean checkProfitYoy(List<IncomeStatementEntity> entity, 
			int expectedRate, int type, IncomeStatementDao incomeStatementDao,
			boolean isMinusProfitGrowth)
	{	
		double thisSeason = 0;
		double lastSeason = 0;
		String stockID = entity.get(0).getStockID();
		String year = entity.get(0).getYear();
		String seasons = entity.get(0).getSeasons();
		for (int i = 0; i < expectedRate; i++)
		{						
			//擷取去年本季利益率用
			int intYear = Integer.parseInt(year);
			year = String.valueOf(--intYear);
			System.out.println("stockID: " + stockID);
			IncomeStatementEntity lastEntity;
			try
			{
				lastEntity = incomeStatementDao.findSingleDataBySeason(stockID, year, seasons);
			}
			catch (EmptyResultDataAccessException ex)
			{
				System.out.println(stockID + "資料不足，無法計算！");
				return false;				
			}
			switch (type) 
			{						
				//毛利
				case 0:
					if (thisSeason == 0)
					{
						thisSeason = (double)entity.get(i).getGrossProfit();
						//最新一季的資料要  > 0
						if (isMinusProfitGrowth == true && thisSeason < 0)
							return false;
					}
					lastSeason = (double)lastEntity.getGrossProfit();
					break;
				//營業利益
				case 1:	
					if (thisSeason == 0)
					{
						thisSeason = (double)entity.get(i).getOperatingIncome();
						//最新一季的資料要  > 0
						if (isMinusProfitGrowth == true && thisSeason < 0)
							return false;
					}
					lastSeason = (double)lastEntity.getOperatingIncome();
					break;
				//稅前淨利
				case 2:
					if (thisSeason == 0)
					{
						thisSeason = (double)entity.get(i).getPreTaxIncome();
						//最新一季的資料要  > 0
						if (isMinusProfitGrowth == true && thisSeason < 0)
							return false;
					}
					lastSeason = (double)lastEntity.getPreTaxIncome();
					break;
			}
					
			if (thisSeason < lastSeason)
				return false;	
			else
				thisSeason = lastSeason;
		}
		return true;
	}
	/**
	 * 營收年增率至少要有expectedNum期為正
	 * @param revenue
	 * @param totalNum 
	 * @param expectNum
	 * @return
	 */
	private boolean checkPlusRevenue(List<String> revenue, int totalNum, int expectNum)
	{
		int counter = 0;
		//過去totalNum個月要有expectNum個月，其營收YoY大於0 
		for (int i = 2; i < totalNum+2; i++)
		{
			if (Double.parseDouble(revenue.get(i)) > 0)
				counter++;
		}
		if (counter >= expectNum)
			return true;
		counter = 0;
		//最後expectNum-1個月，其營收YoY都要大於0
		for (int i = totalNum-expectNum+3; i < totalNum+2; i++)
		{
			if (Double.parseDouble(revenue.get(i)) > 0)
				counter++;
		}
		if (counter >= expectNum-1)
			return true;
		else
			return false;
	}
	/**
	 * 過去8季自由現金流總和 > 0, 資料不足 return true
	 * @param wrapperList
	 * @return
	 */
	private boolean checkFreeCashFlow(List<CashFlowsEntity> wrapperList, int num)
	{
		if (wrapperList == null || wrapperList.size() < num)
			return true;
		int freeCashFlow = 0;
		for (int i = 0; i < wrapperList.size(); i++)
		{
			freeCashFlow = freeCashFlow + wrapperList.get(i).getFreeCashFlow();
		}
		if (freeCashFlow > 0)
			return true;
		else
			return false;
	}
	/**
	 * 去8季營運現金流，至少5季 > 0, 資料不足 return true
	 * @param wrapperList
	 * @param totalNum
	 * @param ExceptedNum
	 * @return
	 */
	private boolean checkOperatingCashFlow(List<CashFlowsEntity> wrapperList, int totalNum, int ExceptedNum)
	{
		if (wrapperList == null || wrapperList.size() < totalNum)
			return true;
		int operatingCashFlowPositiveNum = 0;
		for (int i = 0; i < wrapperList.size(); i++)
		{
			if (wrapperList.get(i).getOperatingActivity() > 0)
				operatingCashFlowPositiveNum++;
		}
		if (operatingCashFlowPositiveNum >= ExceptedNum)
			return true;
		else
			return false;
	}
	/**
	 * 業外收入佔稅前淨利比，過去5年在+-20%之內, 資料不足 return true
	 * @param wrapperList
	 * @param num
	 * @return
	 */
	private boolean checkNonOperating(List<IncomeStatementEntity> wrapperList, int num)	
	{
		if (wrapperList == null || wrapperList.size() < num)
			return true;
		double ratioNumber = 0;
		for (int i = 0; i < wrapperList.size(); i++)
		{
			double number = (double) (wrapperList.get(i).getNonOperatingRevenue() - wrapperList.get(i).getNonOperatingExpense()) * 100 / wrapperList.get(i).getPreTaxIncome();
			ratioNumber = ratioNumber + number;
		}
		ratioNumber = ratioNumber/num;
		if (ratioNumber > -20 && ratioNumber < 20)
			return true;
		else
			return false;
	}
}
