package org.bear.financeAnalysis;

import java.util.*;

import org.bear.dao.BasicStockDao;
import org.bear.dao.JdbcRevenueDao;
import org.bear.dao.StockDistributionDao;
import org.bear.entity.BasicStockWrapper;
import org.bear.entity.RedRabbitStockWrapper;
import org.bear.entity.RedRabbitWrapper;
import org.bear.entity.RevenueEntity;
import org.bear.entity.StockDistributionEntity;
import org.bear.util.GetTpexPbeRatio;
import org.bear.util.GetTwsePbeRatio;
import org.bear.util.StringUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class RedRabbitRevenueAnalysis 
{
	BasicStockDao basicStockDao;
	final int oneYear = 12;
	final int sixYear = 72+1;
	final int threeMonth = 3;
	JdbcRevenueDao jdbcRevenueDao;
	//紅兔指標至少要14個月才能計算
	final int minMonth = 14;
	public List<RedRabbitWrapper> getRedRabbit()
	{
		List<RedRabbitWrapper> redRabitWrapper = new ArrayList<RedRabbitWrapper>();
		String debug = "";
		try
		{
			ApplicationContext context = new ClassPathXmlApplicationContext("config.xml");
			basicStockDao = (BasicStockDao)context.getBean("basicStockDao");
			jdbcRevenueDao = (JdbcRevenueDao)context.getBean("revenueDao");					
			List<BasicStockWrapper> stockIdList = basicStockDao.findAllData();		
			for (int i = 0; i < stockIdList.size(); i++)
			{
				RedRabbitWrapper wrapper = new RedRabbitWrapper();	
				String stockID = stockIdList.get(i).getStockID();
				String stockName = stockIdList.get(i).getStockName();
				/*
				if (!stockID.equals("1314") && !stockID.equals("1201") && !stockID.equals("1203") &&
					!stockID.equals("1213") && !stockID.equals("1215") && !stockID.equals("1216") &&
					!stockID.equals("1217") && !stockID.equals("1218") && !stockID.equals("1227") &&
					!stockID.equals("1231") && !stockID.equals("1234") && !stockID.equals("1234") )
					continue;*/
				debug = stockID;
				int currentRevenue = 0;
				//紅兔指標至少要14個月才能計算
				List<RevenueEntity> entityList = jdbcRevenueDao.findByLatestSize(minMonth, stockID);
				if (entityList.size() < minMonth)
					continue;
				//創近一年新高
				entityList = jdbcRevenueDao.findByLatestSize(oneYear, stockID);
				for (int j = 0; j < entityList.size(); j++)
				{
					if (j == 0)
					{
						currentRevenue = entityList.get(j).getRevenue();
						wrapper.setOneYearHigh(1);
					}
					else 
					{
						if (currentRevenue < entityList.get(j).getRevenue())										
						{
							wrapper.setOneYearHigh(0);
							break;										
						}
					}
				}	
				
				//本月營收創六年以來新高
			    entityList = jdbcRevenueDao.findByLatestSize(sixYear-1, stockID);
				for (int j = 0; j < entityList.size(); j++)
				{
					wrapper.setMonthSize(entityList.size());
					if (j == 0)
					{
						currentRevenue = entityList.get(j).getRevenue();
						wrapper.setSixYearHigh(1);
					}
					else
					{
						if (currentRevenue < entityList.get(j).getRevenue())
						{
							wrapper.setSixYearHigh(0);
							break;
						}						
					}
				}
					
				//創六年同期新高(2)&次高(1)
				//創六年同期新低(2)&次低(1)
				entityList = jdbcRevenueDao.findByLatestSize(sixYear, stockID);		
				List<Integer> sortedRevenue = new ArrayList<Integer>();
				for (int j = 0; j < entityList.size(); j++)
				{
					//RevenueEntity entity;
					if (j == 0)
					{
						currentRevenue = entityList.get(j).getRevenue();
						wrapper.setSixYearHighYoy(0);
					    //entity = entityList.get(j);
					    sortedRevenue.add(currentRevenue);
					}
					else if (j%12 == 0)
					{
						sortedRevenue.add(entityList.get(j).getRevenue());
					}
				}
				//將List排序後得到分數，最高得2分，次高得一分，否則0分
				//Collections.sort(sortedRevenue);
				Collections.sort(sortedRevenue, Collections.reverseOrder());
				int index = 0;
				for (Integer revenue : sortedRevenue) 
				{
				    if (currentRevenue == revenue && index == 0)
				    {
				    	wrapper.setSixYearHighYoy(2);
				    	break;
				    }
				    else if (currentRevenue == revenue && index == 1)
				    {
				    	wrapper.setSixYearHighYoy(1);
				    	break;
				    }
				    else if (currentRevenue == revenue && index == sortedRevenue.size()-2)
				    {
				    	wrapper.setSixYearHighYoy(-1);
				    	break;
				    }
				    else if (currentRevenue == revenue && index == sortedRevenue.size()-1)
				    {
				    	wrapper.setSixYearHighYoy(-2);
				    	break;
				    }
				    index++;	
				}
			
				//上個月創六年內同期新高(2)&次高(1)
				//上個月創六年內同期新低(2)&次低(1)
				sortedRevenue = new ArrayList<Integer>();
				for (int j = 1; j < entityList.size(); j++)
				{
					//RevenueEntity entity;
					if (j == 1)
					{
						currentRevenue = entityList.get(j).getRevenue();
						wrapper.setSixYearHighYoyLastMonth(0);
					    //entity = entityList.get(j);
					    sortedRevenue.add(currentRevenue);
					}
					else if (j%12 == 1)
					{
						sortedRevenue.add(entityList.get(j).getRevenue());
					}
				}
				//將List排序後得到分數，最高得2分，次高得一分，否則0分
				//Collections.sort(sortedRevenue);
				Collections.sort(sortedRevenue, Collections.reverseOrder());
				index = 0;
				for (Integer revenue : sortedRevenue) 
				{
				    if (currentRevenue == revenue && index == 0)
				    {
				    	wrapper.setSixYearHighYoyLastMonth(2);
				    	break;
				    }
				    else if (currentRevenue == revenue && index == 1)
				    {
				    	wrapper.setSixYearHighYoyLastMonth(1);
				    	break;
				    }
				    else if (currentRevenue == revenue && index == sortedRevenue.size()-2)
				    {
				    	wrapper.setSixYearHighYoyLastMonth(-1);
				    	break;
				    }
				    else if (currentRevenue == revenue && index == sortedRevenue.size()-1)
				    {
				    	wrapper.setSixYearHighYoyLastMonth(-2);
				    	break;
				    }
				    index++;	
				}
				//過去一年累計營收創下六年新高	
				//過去一年累計營收YoY創下六年新高
				List<Long> sortedAccumulationRevenue = new ArrayList<Long>();
				long oneYearAccumulation = 0;
				
				for (int j = 0; j < entityList.size() - 1; j++)
				{				
					oneYearAccumulation += entityList.get(j).getRevenue();
					if (j%12 == 11)
					{
						sortedAccumulationRevenue.add(oneYearAccumulation);
						oneYearAccumulation = 0;
					}					
				}
				List<Double> yoyList = new ArrayList<Double>();
				for (int j = 0; j < sortedAccumulationRevenue.size()-1; j++)
				{					
					double yoy = (double)sortedAccumulationRevenue.get(j)/sortedAccumulationRevenue.get(j+1);
					yoyList.add(yoy);
				}
				index = 0;
				//過去一年累計營收YoY創下六年新高
				double currentYoy = 0;
				for (Double yoy : yoyList) 
				{
					if (index == 0)
					{
						currentYoy = yoy;
						wrapper.setSixYearAccumulationYoyHigh(1);
					}
					else
					{
						if (currentYoy < yoy)
						{
							wrapper.setSixYearAccumulationYoyHigh(0);
							break;
						}						
					}
					index++;
				}
				//過去一年累計營收創下六年新高
				index = 0;
				long currentAccumulation = 0;
				for (Long accumulation: sortedAccumulationRevenue)
				{
					if (index == 0)
					{
						currentAccumulation = accumulation;
						wrapper.setSixYearAccumulationHigh(1);
					}
					else
					{
						if (currentAccumulation < accumulation)
						{
							wrapper.setSixYearAccumulationHigh(0);
							break;
						}						
					}
					index++;
				}
				/** 連三月單月營收數字 **/
				//連三月絕對營收成長
				entityList = jdbcRevenueDao.findByLatestSize(threeMonth, stockID);
				for (int j = 0; j < entityList.size(); j++)
				{
					if (j == 0)
					{
						currentRevenue = entityList.get(j).getRevenue();
						wrapper.setConsecutive3MRevenueGrow(1);
					}
					else
					{
						if (currentRevenue < entityList.get(j).getRevenue())
						{
							wrapper.setConsecutive3MRevenueGrow(0);
							break;
						}						
					}
				}		
				//連三月單月YoY成長
				for (int j = 0; j < entityList.size(); j++)
				{					
					if (j == 0)
					{			
						currentYoy = (double)entityList.get(j).getRevenue()/entityList.get(j).getLastRevenue();
						wrapper.setConsecutive3MYoyGrow(1);
					}
					else if (currentYoy < (double)entityList.get(j).getRevenue()/entityList.get(j).getLastRevenue())
					{
						wrapper.setConsecutive3MYoyGrow(0);
						break;
					}	
					else
					{
						currentYoy = (double)entityList.get(j).getRevenue()/entityList.get(j).getLastRevenue();
					}
				}
				//連三月累計YoY成長
				for (int j = 0; j < entityList.size(); j++)
				{
					if (j == 0)
					{
						currentYoy = (double)entityList.get(j).getAccumulation()/entityList.get(j).getLastAccumulation();
						wrapper.setConsecutive3MAccumuYoyGrow(1);
					}
					else if (currentYoy < (double)entityList.get(j).getAccumulation()/entityList.get(j).getLastAccumulation())
					{
						wrapper.setConsecutive3MAccumuYoyGrow(0);
						break;
					}						
					else
					{
						currentYoy = (double)entityList.get(j).getRevenue()/entityList.get(j).getLastRevenue();
					}
				}		
				//本月MoM>去年同期MoM
				entityList = jdbcRevenueDao.findByLatestSize(14, stockID);
				/* 這個月/上個月 */
				double currentMom = (double)entityList.get(0).getRevenue()/entityList.get(1).getRevenue();
				/* 去年同一個月/去年上一個月 */
				double lastMom = (double)entityList.get(oneYear).getRevenue()/entityList.get(oneYear+1).getRevenue();
				if (currentMom > lastMom)
					wrapper.setMomGrow(1);
				else
					wrapper.setMomGrow(0);			
				wrapper.setStockID(stockID);	
				wrapper.setStockName(stockName);
				redRabitWrapper.add(wrapper);
			}
			
		}
		catch (Exception ex)
		{
			System.out.println("debug id: " + debug);
			ex.printStackTrace();
		}
		return redRabitWrapper;
	}
	/**
	 * 紅兔營收+籌碼選股法
	 * @param revenueCheck 是否以營收篩選
	 * @param totalMonth 過去M個月
	 * @param selectedMonth 有N個月的營收符合條件
	 * @param retailCheck 是否以散戶賣超篩選
	 * @param totalRetail 過去M個月
	 * @param selectedRetail 有N個月散戶賣超
	 * @param majorShareholderCheck 是否以大股東買超篩選 
	 * @param totalMajorShareholder 過去M個月
	 * @param selectedMajorShareholder 有N個月大股東買超
	 * @param peDate 證交所本益比(日期)
	 * @return
	 */
	public List<RedRabbitStockWrapper> getAdvancedRedRabbit(boolean revenueCheck, int totalMonth, int selectedMonth,
			boolean retailCheck, int totalRetail, int selectedRetail,
			boolean majorShareholderCheck, int totalMajorShareholder, 
			int selectedMajorShareholder, String peDate)
	{
		ApplicationContext context = new ClassPathXmlApplicationContext("config.xml");
		basicStockDao = (BasicStockDao)context.getBean("basicStockDao");
		jdbcRevenueDao = (JdbcRevenueDao)context.getBean("revenueDao");
		StockDistributionDao stockDistributionDao = (StockDistributionDao)context.getBean("stockDistributionDao");
		List<RedRabbitStockWrapper> wrapperList = new ArrayList<RedRabbitStockWrapper>();
		//原始資料
		List<BasicStockWrapper> stockIdList = basicStockDao.findAllData();	
		//符合條件的資料
		List<BasicStockWrapper> conditionalList;
		//暫時的計算結果
		List<BasicStockWrapper> calculateList = new ArrayList<BasicStockWrapper>();
		conditionalList = stockIdList;
		try
		{
			//營收創下歷年同期新高
			if (revenueCheck == true)
			{
				for (int i = 0; i < conditionalList.size(); i++)
				{
					String stockID = conditionalList.get(i).getStockID();
					List<RevenueEntity> entityList = jdbcRevenueDao.findByLatestSize(sixYear, stockID);
					//實際成長的月份數
					int growMonth = 0;									
					for (int j = 0; j < totalMonth; j++)
					{
						int revenue = entityList.get(j).getRevenue();
						int lastRevenue = 0;
						String date = entityList.get(j).getYearMonth().toString().substring(5, 10);
						String lastDate = "";
						//連續比較5年的同月分資料
						for (int k = 1; k < 6; k++)
						{
							//資料不足5年，無法計算，結束迴圈
							if (entityList.size() <= j+k*oneYear)
								break;
							lastRevenue = entityList.get(j+k*oneYear).getRevenue();
							if (revenue == 0 || lastRevenue == 0)
								break;
							lastDate = entityList.get(j+k*oneYear).getYearMonth().toString().substring(5, 10);					
							//月分資料不合 (假設目前的日子是2016-03-01，理論上去年同期是03-01，如果月份資料不是03-01，則終止程式)
							if (!date.equals(lastDate))
							{
								System.out.println("stockID: " + stockID);
								System.exit(0);
							}
							if (revenue < lastRevenue)
								break;
							if (k == 5)
								growMonth++;
						}					
					}
					if (growMonth >= selectedMonth)
					{
						calculateList.add(conditionalList.get(i));
					}
				}				
				conditionalList = calculateList;
				calculateList = new ArrayList<BasicStockWrapper>();
			}
			//散戶賣超
			if (retailCheck == true)
			{
				for (int i = 0; i < conditionalList.size(); i++)
				{
					String stockID = conditionalList.get(i).getStockID();					
					//System.out.println("stockID: " + stockID);
					//if (!stockID.equals("1110"))
						//continue;
					List <StockDistributionEntity> distributionList = stockDistributionDao.latest(stockID, totalRetail);
					//實際成長的月份數
					int growMonth = 0;
					for (int j = 0; j < distributionList.size()-1; j++)
					{
						long retailInvestor = 
						distributionList.get(j).getD1() - distributionList.get(j+1).getD1() +
						distributionList.get(j).getD1000() - distributionList.get(j+1).getD1000() +
						distributionList.get(j).getD5000() - distributionList.get(j+1).getD5000() +
						distributionList.get(j).getD10000() - distributionList.get(j+1).getD10000() +
						distributionList.get(j).getD15000() - distributionList.get(j+1).getD15000() +
						distributionList.get(j).getD20000() - distributionList.get(j+1).getD20000() +
						distributionList.get(j).getD30000() - distributionList.get(j+1).getD30000() +
						distributionList.get(j).getD40000() - distributionList.get(j+1).getD40000() +
						distributionList.get(j).getD50000() - distributionList.get(j+1).getD50000();
						if (retailInvestor < 0)
							growMonth++;
					}
					if (growMonth >= selectedRetail)
					{
						calculateList.add(conditionalList.get(i));
					}
				}
				conditionalList = calculateList;
				calculateList = new ArrayList<BasicStockWrapper>();
			}
			//大股東買超
			if (majorShareholderCheck == true)
			{
				for (int i = 0; i < conditionalList.size(); i++)
				{
					String stockID = conditionalList.get(i).getStockID();
					List <StockDistributionEntity> distributionList = stockDistributionDao.latest(stockID, totalMajorShareholder);
					//實際成長的月份數
					int growMonth = 0;
					for (int j = 0; j < distributionList.size()-1; j++)
					{
						long majorShareHolder =
						distributionList.get(j).getD800000() - distributionList.get(j+1).getD800000() + 
						distributionList.get(j).getD1000000() - distributionList.get(j+1).getD1000000();
						if (majorShareHolder > 0)
							growMonth++;
					}
					if (growMonth >= selectedMajorShareholder)
					{
						calculateList.add(conditionalList.get(i));
					}
				}
				conditionalList = calculateList;
			}
			/****************
			 * 重組資料		
			 */
			//PE Ratio
			//String date = "106/02/10";
			GetTwsePbeRatio twseRatio = new GetTwsePbeRatio();
			GetTpexPbeRatio tpexRatio = new GetTpexPbeRatio();
			twseRatio.setDate(peDate);
			twseRatio.getContent();
			tpexRatio.setDate(peDate);
			tpexRatio.getContent();
			HashMap<String, Double> hashPer = twseRatio.getHashPer();
			//HashMap<String, Double> hashPbr = twseRatio.getHashPbr();
			hashPer.putAll(tpexRatio.getHashPer());
			//hashPbr.putAll(tpexRatio.getHashPbr());
			for (int i = 0; i < conditionalList.size(); i++)
			{
				String stockID = conditionalList.get(i).getStockID();
				String stockName = conditionalList.get(i).getStockName();
				RedRabbitStockWrapper wrapper = new RedRabbitStockWrapper();
				wrapper.setStockID(stockID);
				wrapper.setStockName(stockName);
				List<RevenueEntity> entityList = jdbcRevenueDao.findByLatestSize(sixYear, stockID);
				//if (i == conditionalList.size() - 1)
					//System.out.println("stockID: " + stockID);
				for (int j = 0; j < 6; j++)
				{
					double yoy;
					yoy = (double)entityList.get(j).getRevenue()/entityList.get(j).getLastRevenue()-1;
					yoy = yoy*100;
					yoy = StringUtil.setPointLength(yoy, 2);					
					switch (j)
					{
						case 0: 					
							wrapper.setLatestMonth(yoy);
							break;
						case 1:	
							wrapper.setLastMonth(yoy);
							break;
						case 2:
							wrapper.setBeforeLastMonth(yoy);
							break;
						case 3:
							wrapper.setThreeMonthAgo(yoy);
							break;
						case 4:
							wrapper.setFourMonthAgo(yoy);
							break;
						case 5:
							wrapper.setFiveMonthAgo(yoy);
							break;
					}
				}
				//Add PE Ratio
				//GetURLCathayBasicData urlContent = new GetURLCathayBasicData(stockID);
				//BasicDataParserCathay parser = new BasicDataParserCathay(urlContent.getContent(), stockID);
				//parser.parse(2);				
				wrapper.setPe(hashPer.get(stockID));
				wrapperList.add(wrapper);							
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		return wrapperList;
	}
	
}
