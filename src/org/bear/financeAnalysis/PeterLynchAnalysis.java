/**
 * 
 */
package org.bear.financeAnalysis;

import java.util.List;

import org.bear.dao.FinancialDataDao;
import org.bear.dao.IncomeStatementDao;
import org.bear.entity.FinancialDataEntity;
import org.bear.entity.IncomeStatementEntity;
import org.bear.entity.PeterLynchWrapper;
import org.bear.parser.BasicDataParserCathay;
import org.bear.util.GetURLCathayBasicData;
import org.bear.util.StringUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author edward
 *
 */
public class PeterLynchAnalysis 
{
	PeterLynchWrapper wrapper;
	final String initYear = "2002";
	public PeterLynchAnalysis()
	{
		
	}
	public PeterLynchWrapper getPeterLynchAnalysis(String stockID)
	{
		int intYearLength = 8;
		wrapper = new PeterLynchWrapper();
		ApplicationContext context = new ClassPathXmlApplicationContext("config.xml");
		//損益表
		IncomeStatementDao incomeStatementDao = (IncomeStatementDao)context.getBean("basicIncomeStatementDao");;
		List <IncomeStatementEntity> incomeStatementList;
		incomeStatementDao = (IncomeStatementDao)context.getBean("basicIncomeStatementDao");
		incomeStatementList = incomeStatementDao.findDataByYear(stockID, initYear);
		//計算過去N年稅前盈餘成長率
		double preTaxIncome = 0;
		double totalGrowthRate = 0;
		for (int i = incomeStatementList.size() - intYearLength; i < incomeStatementList.size(); i++)
		{
			double earningsGrowthRate = 0;
			
			if (i == incomeStatementList.size() - intYearLength)
			{
				preTaxIncome = incomeStatementList.get(i).getPreTaxIncome();				
			}
			else
			{
				earningsGrowthRate = incomeStatementList.get(i).getPreTaxIncome() / preTaxIncome - 1;
				totalGrowthRate = totalGrowthRate + getFinalGrowthRate(earningsGrowthRate);
				preTaxIncome = incomeStatementList.get(i).getPreTaxIncome();
			}
		}
		/*
		totalGrowthRate = Math.pow((double)20160/65022, 0.2);
		totalGrowthRate = Math.pow(5.72/1.79, 0.25);
		totalGrowthRate = Math.pow(1.79/5.72, 0.25);
		totalGrowthRate = Math.pow(5.72/1.79, 0.2);*/
		totalGrowthRate = totalGrowthRate/(intYearLength - 1);
		wrapper.setEarningsGrowthRate(StringUtil.setPointLength(totalGrowthRate * 100));
		//計算過去N年平均現金股息
		//其他財務資料
		FinancialDataDao financialDao = (FinancialDataDao)context.getBean("basicFinancialDataDao");
		List <FinancialDataEntity> financialList;
		financialList = financialDao.findDataByYear(stockID, initYear);
		double cashDiv = 0;
		for (int i = financialList.size() - (intYearLength - 1); i < financialList.size(); i++)
		{
			cashDiv = cashDiv + financialList.get(i).getCashDiv();
		}
		cashDiv = cashDiv / (intYearLength - 1);
		wrapper.setCashDiv(StringUtil.setPointLength(cashDiv));
		//計算P/E Ratio, P/B Ratio, 股價
		GetURLCathayBasicData urlContent = new GetURLCathayBasicData(stockID);
		BasicDataParserCathay parser = new BasicDataParserCathay(urlContent.getContent(), stockID);
		parser.parse(2);
		//P/E Ratio
		wrapper.setPer(parser.getPer());
		//P/B Ratio
		double ratioNumber;
		ratioNumber = StringUtil.setPointLength(parser.getPrice() / parser.getNav());
		wrapper.setPbr(ratioNumber);
		//Price
		ratioNumber = StringUtil.setPointLength(parser.getPrice());
		wrapper.setPrice(ratioNumber);
		//PeterLynch
		ratioNumber = StringUtil.setPointLength( (totalGrowthRate + (cashDiv/parser.getPrice())) * 100 / parser.getPer() );
		wrapper.setPeterLynchResult(ratioNumber);
		//Graham
		ratioNumber = StringUtil.setPointLength(wrapper.getPer() * wrapper.getPbr());
		wrapper.setGrahamResult(ratioNumber);
		return wrapper;
	}
	private double getFinalGrowthRate(double earningsGrowthRate)
	{
		//設定最大盈餘成長率為50%，若超過50%以50%計
		double maxGrowthRate = 0.5;
		if (earningsGrowthRate < maxGrowthRate)
		{
			return earningsGrowthRate;
		}
		else
		{
			return maxGrowthRate;
		}
	}
}
