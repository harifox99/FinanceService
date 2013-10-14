package org.bear.financeAnalysis;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bear.dao.BasicStockDao;
import org.bear.dao.IncomeStatementDao;
import org.bear.dao.RevenueDao;
import org.bear.entity.BasicStockWrapper;
import org.bear.entity.IncomeStatementEntity;
import org.bear.entity.PeterLynchWrapper;
import org.bear.entity.RevenueEntity;
import org.bear.parser.BasicDataParserCathay;
import org.bear.util.GetURLCathayBasicData;
import org.bear.util.ReverseUtil;
import org.bear.util.StringUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class PerfectAnalysis 
{	
	//最多只看半年成長
	final int maxMonth = 6;
	//最多只看過去4季
	final int maxSeasons = 4;
	//可以繼續掃瞄的股票代碼
	List<List<Double>> legalStockIdList = new ArrayList<List<Double>>();
	//Column Name
	List<String> columnNameList = new ArrayList<String>(); 
	/**
	 * 
	 * @param yoyGrowMonth 期望的YoY連續成長月份
	 * @param profitRatioGrowSeason 期望的利益率連續成長季數
	 * @param expectedGrossProfitRatio 期望的當季毛利率 
	 * @param operatingProfitRatio 期望的當季營業利益率
	 * @param expectedPe 期望PE Ratio
	 * @return
	 */
	public List<List<String>> analysis(int yoyGrowMonth, int profitRatioGrowSeason, 
			int expectedGrossProfitRatio, int operatingProfitRatio, int expectedPe)
	{		
		ApplicationContext context = new ClassPathXmlApplicationContext("config.xml");
		BasicStockDao basicStockDao = (BasicStockDao)context.getBean("basicStockDao");
		RevenueDao revenueDao = (RevenueDao)context.getBean("revenueDao");
		IncomeStatementDao incomeStatementDao = (IncomeStatementDao)context.getBean("basicIncomeStatementDao");
		//股票列表
		List<BasicStockWrapper> stockList = basicStockDao.findAllData();
		//最終結果
		List<List<String>> perfectList = new ArrayList<List<String>>();
		//暫時的計算結果
		List<List<String>> calculateList;		
		//掃瞄符合期望的YoY
		try
		{			
			columnNameList.add("股票代碼");
			calculateList = new ArrayList<List<String>>();
			for (int i = 0; i < stockList.size(); i++)
			{
				String stockID = stockList.get(i).getStockID();
				System.out.println("stockID: " + stockID);
				List<RevenueEntity> revenueList = revenueDao.findByLatestSize(maxMonth+1, stockList.get(i).getStockID());
				//Set Column Name
				if (i == 0)
				{
					for (int j = revenueList.size()-1; j >= 0; j--)
					{
						this.addColumnName(revenueList.get(j).getYearMonth(), "營收");
					}
				}
				List<String> yoyList = this.checkYoy(revenueList, yoyGrowMonth);
				if (yoyList != null)
				{					
					yoyList = ReverseUtil.reverse(yoyList);					
					yoyList.add(0, stockID);
					//把經過第一關檢驗的股票代碼先暫存起來，第二關就不用掃瞄所有股票了
					perfectList.add(yoyList);				
				}
			}		
			calculateList = new ArrayList<List<String>>();
			for (int i = 0; i < perfectList.size(); i++)
			{				
				String stockID = perfectList.get(i).get(0);
				System.out.println("stockID: " + stockID);
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
				List<String> rateList = this.checkProfitRatio(entity, profitRatioGrowSeason, 1);
				if (rateList != null)
				{
					rateList = ReverseUtil.reverse(rateList);			
					//把營業利益率直接附在營收YoY後面
					perfectList.get(i).addAll(rateList);
					//所有符合期望的資料暫存在calculateList
					calculateList.add(perfectList.get(i));
				}
			}
			//把所有符合期望的資料calculateList重新塞回perfectList，並以perfectList內的資料作進一步篩選
			perfectList = calculateList;
			calculateList = new ArrayList<List<String>>();
			for (int i = 0; i < perfectList.size(); i++)
			{
				String stockID = perfectList.get(i).get(0);
				System.out.println("stockID: " + stockID);
				List<IncomeStatementEntity> entity = incomeStatementDao.findDataByLatest(maxSeasons+1, stockID);
				//Set Column Name
				if (i == 0)
				{
					for (int j = entity.size()-1; j >= 0; j--)
					{
						this.addColumnName(entity.get(j).getYear() + "-" + entity.get(j).getSeasons(), "稅前淨利率");
					}
				}
				//稅前淨利率
				List<String> rateList = this.checkProfitRatio(entity, profitRatioGrowSeason, 2);
				if (rateList != null)
				{
					rateList = ReverseUtil.reverse(rateList);		
					//把稅前淨利率直接附在營業利益率後面
					perfectList.get(i).addAll(rateList);
					//所有符合期望的資料暫存在calculateList
					calculateList.add(perfectList.get(i));
				}
			}
			//把所有符合期望的資料calculateList重新塞回perfectList，並以perfectList內的資料作進一步篩選
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
			//期望本益比
			for (int i = 0; i < perfectList.size(); i++)
			{
				String stockID = perfectList.get(i).get(0);		
				PeterLynchWrapper wrapper = this.checkPeRatio(expectedPe, stockID);		
				if (i == 0)
				{
					columnNameList.add("本益比");
					columnNameList.add("股價淨值比");
					columnNameList.add("股價");
				}
				if (wrapper != null)
				{
					//把PE, PB Ratio直接附在營業利益率後面
					perfectList.get(i).add(String.valueOf(wrapper.getPer()));
					perfectList.get(i).add(String.valueOf(wrapper.getPbr()));
					perfectList.get(i).add(String.valueOf(wrapper.getPrice()));
					//所有符合期望的資料暫存在calculateList
					calculateList.add(perfectList.get(i));
				}
			}
			perfectList = calculateList;
			calculateList = new ArrayList<List<String>>();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		perfectList.add(0, columnNameList);
		return perfectList;
	}
	/**
	 * YoY連N季成長
	 * @param revenue
	 * @param expectedRate
	 * @return
	 */
	private List<String> checkYoy(List<RevenueEntity> revenue, int expectedRate)
	{
		List<String> yoyList = new ArrayList<String>();
		for (int i = 0; i < maxMonth; i++)
		{
			//YoY Revenue
			if (revenue.get(i).getRevenue() == 0 || revenue.get(i).getLastRevenue() == 0 ||
				revenue.get(i+1).getRevenue() == 0 || revenue.get(i+1).getLastRevenue() == 0)
				return null;
			double thisMonthYoy = (double)revenue.get(i).getRevenue()/revenue.get(i).getLastRevenue();
			double lastMonthYoy = (double)revenue.get(i+1).getRevenue()/revenue.get(i+1).getLastRevenue();
			if (i < expectedRate && thisMonthYoy < lastMonthYoy)
				return null;					
			else
			{
				thisMonthYoy -= 1;
				thisMonthYoy *= 100;
				NumberFormat formatter = new DecimalFormat(".##");
				String strRevenue = formatter.format(thisMonthYoy);
				yoyList.add(strRevenue);	
			}
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
	 * @param expectedRate 期望益利率
	 * @param type, 0 for 毛利率, 1 for 營業利益率, 2 for 稅前淨利率
	 * @return
	 */
	private List<String> checkProfitRatio(List<IncomeStatementEntity> entity, int expectedRate, int type)
	{
		//稅前淨利率		
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
	 * 
	 * @param entity
	 * @param expectedRatio 期望益利率
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
	private PeterLynchWrapper checkPeRatio(int expectedPe, String stockID)
	{
		/* 計算P/E Ratio, P/B Ratio, 股價 */
		GetURLCathayBasicData urlContent = new GetURLCathayBasicData(stockID);
		BasicDataParserCathay parser = new BasicDataParserCathay(urlContent.getContent(), stockID);
		parser.parse(2);
		PeterLynchWrapper wrapper = new PeterLynchWrapper();
		/* P/E Ratio */
		wrapper.setPer(parser.getPer());
		/* P/B Ratio */
		double ratioNumber;
		ratioNumber = StringUtil.setPointLength(parser.getPrice() / parser.getNav());
		wrapper.setPbr(ratioNumber);
		//Price
		ratioNumber = StringUtil.setPointLength(parser.getPrice());
		wrapper.setPrice(ratioNumber);
		if (expectedPe > wrapper.getPer())
			return wrapper;
		else
			return null;
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
}
