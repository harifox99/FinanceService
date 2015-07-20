package org.bear.financeAnalysis;

import java.util.*;

import org.bear.dao.BasicStockDao;
import org.bear.dao.JdbcRevenueDao;
import org.bear.entity.BasicStockWrapper;
import org.bear.entity.RedRabbitWrapper;
import org.bear.entity.RevenueEntity;
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
	
}
