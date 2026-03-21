/**
 * 
 */
package org.bear.util;

import java.util.ArrayList;
import java.util.List;

import org.bear.dao.BalanceSheetDao;
import org.bear.dao.FinancialDataDao;
import org.bear.dao.IncomeStatementDao;
import org.bear.dao.RevenueDao;
import org.bear.entity.*;
import org.bear.parser.BasicDataParserCathay;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author edward
 *
 */
public class CalculateRiskMap 
{
	List <BalanceSheetEntity> balanceSheetList;
	List <IncomeStatementEntity> incomeStatementList;
	List <RiskMapWrapper> riskMapWrapperList;
	List <RevenueEntity> revenueEntityList;
	RiskMapWrapper wrapper;
	public List<RiskMapWrapper> getRiskMap(String stockID, String year)
	{
		List <RiskMapWrapper> riskMapList = null;
		try
		{
			ApplicationContext context = new ClassPathXmlApplicationContext("config.xml");
			//戈玻璽杜
			BalanceSheetDao balanceSheetDao = (BalanceSheetDao)context.getBean("basicBalanceSheetDao");
			List <BalanceSheetEntity> balanceSheetList;
			//穕痲
			IncomeStatementDao incomeStatementDao = (IncomeStatementDao)context.getBean("basicIncomeStatementDao");;
			List <IncomeStatementEntity> incomeStatementList;
			//Μ絃基戈癟
			RevenueDao revenueDao = (RevenueDao)context.getBean("revenueDao");
			List <RevenueEntity> revenueEntityList;
			//ㄤ癩叭戈惠璶琌NAV
			FinancialDataDao financialDao = (FinancialDataDao)context.getBean("basicFinancialDataDao");
			List <FinancialDataEntity> financialList;
			/***********************************************/
			riskMapList = new ArrayList<RiskMapWrapper>();
			balanceSheetList = balanceSheetDao.findDataByYear(stockID, year);
			incomeStatementList = incomeStatementDao.findDataByYear(stockID, year);
			financialList = financialDao.findDataByYear(stockID, year);
			/**********************************************/
			//狥舦痲
			int lastEquity = 0;
			double ratioNumber = 0;
			//キАΜ絃基
			//double averagePrice = 0;
			//程蔼基
			double maxPrice = 0;
			//程基
			double minPrice = 2000;
			//ROE
			double roe = 0;
			//衡瞯
			CalculateReinvestmentRate reinvestmentRate = new CalculateReinvestmentRate();
			reinvestmentRate.setStockID(stockID);
			reinvestmentRate.setYear("2002");
			reinvestmentRate.setSeasons("01");
			List <Double> reinvestmentRateList = reinvestmentRate.getReinvestmentRateList();
			String currentYear;
			//狦⊿Τ犁Μ戈癟...ぃ笵и糶ぐ或...犁Μ戈羆
			int misCount = 0;
			GetURLCathayBasicData urlContent = new GetURLCathayBasicData(stockID);
			BasicDataParserCathay parser = new BasicDataParserCathay(urlContent.getContent(), stockID);
			parser.parse(2);	
			for (int i = 0; i < balanceSheetList.size(); i++)
			{
				wrapper = new RiskMapWrapper();
				if (i == 0)
				{
					lastEquity = balanceSheetList.get(i).getStockholdersEquity();
				}
				else
				{
					//maxPrice = 0;
					//minPrice = 2000;
					//averagePrice = 0;
					currentYear = balanceSheetList.get(i).getYear();
					//ROE
					ratioNumber = (double) incomeStatementList.get(i).getNetIncome() / ((lastEquity+balanceSheetList.get(i).getStockholdersEquity())/2) * 100;
					ratioNumber = StringUtil.setPointLength(ratioNumber);
					wrapper.setRoe(ratioNumber);
					//NAV
					wrapper.setNav(StringUtil.setPointLength(financialList.get(i).getNav()));				
					revenueEntityList = revenueDao.findAllData(stockID, currentYear);
					//犁Μ戈Τ┮铬筁硂щ戈繧瓜
					if (revenueEntityList.size() == 0)
					{
						misCount++;
						continue;
					}
					/*
					for (int j = 0; j < revenueEntityList.size(); j++)
					{
						//キАΜ絃基
						averagePrice = averagePrice + Double.parseDouble(revenueEntityList.get(j).getAverageIndex());
						//程蔼基
						if (Double.parseDouble(revenueEntityList.get(j).getHighIndex()) > maxPrice)
							maxPrice = Double.parseDouble(revenueEntityList.get(j).getHighIndex());
						//程基
						if (minPrice > Double.parseDouble(revenueEntityList.get(j).getLowIndex()))
							minPrice = Double.parseDouble(revenueEntityList.get(j).getLowIndex());	
					}*/
									
					maxPrice = parser.getMaxPrice();
					minPrice = parser.getMinPrice();
					wrapper.setAveragePrice(parser.getPrice());
					wrapper.setMaxPrice(parser.getMaxPrice());
					wrapper.setMinPrice(parser.getMinPrice());
					//基瞓ゑ
				    double pbr = parser.getPrice()/wrapper.getNav();
				    pbr = StringUtil.setPointLength(pbr);
				    wrapper.setPbr(pbr);
				    //程蔼基瞓ゑ
				    pbr = maxPrice/wrapper.getNav();
				    pbr = StringUtil.setPointLength(pbr);
				    wrapper.setMaxPbr(pbr);
				    //程基瞓ゑ
				    pbr = minPrice/wrapper.getNav();
				    pbr = StringUtil.setPointLength(pbr);
				    wrapper.setMinPbr(pbr);
				    //场ROE
				    roe = wrapper.getRoe()/wrapper.getPbr();
				    roe = StringUtil.setPointLength(roe);
				    wrapper.setKn(roe);
				    //Max场ROE
				    roe = wrapper.getRoe()/wrapper.getMinPbr();
				    roe = StringUtil.setPointLength(roe);
				    wrapper.setMaxKn(roe);
				    //Min场ROE
				    roe = wrapper.getRoe()/wrapper.getMaxPbr();
				    roe = StringUtil.setPointLength(roe);
				    wrapper.setMinKn(roe);
					/**************************/
				    wrapper.setYear(balanceSheetList.get(i).getYear());
				    //干洱瞯痷璚
				    wrapper.setReinvestmentRate(reinvestmentRateList.get(reinvestmentRateList.size()-i+misCount));
					riskMapList.add(wrapper);
					lastEquity = balanceSheetList.get(i).getStockholdersEquity();
				}
			}
			wrapper = new RiskMapWrapper();
			//璸衡﹗ROE
			balanceSheetList = balanceSheetDao.findDataBySeason(stockID, "2015", "01");
			incomeStatementList = incomeStatementDao.findDataBySeason(stockID, "2015", "01");
			//キА狥舦痲
			int averageEquity = 0;
			//祙瞓
			int netIncome = 0;
			for (int i = balanceSheetList.size() - 1; i >= balanceSheetList.size() - 4; i--)
			{
				if (i == balanceSheetList.size() - 1)
				{
					//璸衡程穝
					year = balanceSheetList.get(i).getYear();
				}
				netIncome += incomeStatementList.get(i).getNetIncome();
				averageEquity += balanceSheetList.get(i).getStockholdersEquity();
			}
			averageEquity = averageEquity/4;
			wrapper.setYear("筁﹗キА");
			//筁﹗ROE
			ratioNumber = (double)netIncome/averageEquity*100;
			ratioNumber = StringUtil.setPointLength(ratioNumber);
			wrapper.setRoe(ratioNumber);
			//–瞓
			//GetURLCathayBasicData urlContent = new GetURLCathayBasicData(stockID);
			//BasicDataParserCathay parser = new BasicDataParserCathay(urlContent.getContent(), stockID);
			//parser.parse(2);
			wrapper.setNav(parser.getNav());
			//程蔼基
			wrapper.setMaxPrice(parser.getMaxPrice());
			//程基
			wrapper.setMinPrice(parser.getMinPrice());
			//Μ絃基
			wrapper.setAveragePrice(parser.getPrice());
			//
			wrapper.setYear(year);
			//基瞓ゑ
			ratioNumber = (double)parser.getPrice()/parser.getNav();
			ratioNumber = StringUtil.setPointLength(ratioNumber);
			wrapper.setPbr(StringUtil.setPointLength(ratioNumber));
			//程蔼基瞓ゑ
			wrapper.setMaxPbr(StringUtil.setPointLength(parser.getMaxPrice()/parser.getNav()));
			//程基瞓ゑ
			wrapper.setMinPbr(StringUtil.setPointLength(parser.getMinPrice()/parser.getNav()));
			//场ROE
			wrapper.setKn(StringUtil.setPointLength(wrapper.getRoe()/wrapper.getPbr()));
			//Max场ROE
			wrapper.setMaxKn(StringUtil.setPointLength(wrapper.getRoe()/wrapper.getMinPbr()));
			//Min场ROE
			wrapper.setMinKn(StringUtil.setPointLength(wrapper.getRoe()/wrapper.getMaxPbr()));
			//瞯
			wrapper.setReinvestmentRate(reinvestmentRateList.get(0));
			riskMapList.add(wrapper);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		return riskMapList;
	}
	
}
