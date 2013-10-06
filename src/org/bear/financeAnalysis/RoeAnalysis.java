package org.bear.financeAnalysis;

import java.util.ArrayList;
import java.util.List;

import org.bear.dao.BalanceSheetDao;
import org.bear.dao.BasicStockDao;
import org.bear.dao.IncomeStatementDao;
import org.bear.entity.BuffettAnalysisWrapper;
import org.bear.entity.RoeYearWrapper;
import org.bear.entity.BalanceSheetEntity;
import org.bear.entity.BasicStockWrapper;
import org.bear.entity.IncomeStatementEntity;
import org.bear.util.StringUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class RoeAnalysis 
{
	ApplicationContext context = new ClassPathXmlApplicationContext("config.xml");
	BasicStockDao dao = (BasicStockDao)context.getBean("basicStockDao");
	FinanceUtil financeUtil;
	//資產負債表
	BalanceSheetDao balanceSheetDao = (BalanceSheetDao)context.getBean("basicBalanceSheetDao");
	List <BalanceSheetEntity> balanceSheetList;
	//損益表
	IncomeStatementDao incomeStatementDao = (IncomeStatementDao)context.getBean("basicIncomeStatementDao");
	List <IncomeStatementEntity> incomeStatementList;
	/**
	 * 用此程式來篩選，過去N個年度，期望至少有M年，符合期望的ROE，例如：
	 * 希望過去10年，有8年，其期望的ROE > 15%，則
	 * totalYear = 10, demandYear = 8, demandRoe = 15;
	 * **********************************************
	 * 注意，這裡ROE的算法和[ROE細部分析]的方法略有不同
	 * @param totalYear 查詢過去N年度的ROE
	 * @param demandYear 期望有幾個年度符合需求
	 * @param demandRoe 期望ROE
	 * @return
	 */
	public List<RoeYearWrapper> getDemandROEList(int totalYear, int demandYear, int demandRoe, String discountRate)
	{
		List<BasicStockWrapper> stockList = dao.findAllData();
		List<RoeYearWrapper> roeYearList = new ArrayList<RoeYearWrapper>();
		try
		{
			for (int i = 0; i < stockList.size(); i++)
			{
				String stockID = "";
				stockID = stockList.get(i).getStockID();					
				//if (!stockID.equals("1733"))
					//continue;
				balanceSheetList = balanceSheetDao.findDataByYear(stockID);
				incomeStatementList = incomeStatementDao.findDataByYear(stockID);
				if (incomeStatementList.size() != balanceSheetList.size())
					continue;
				if (incomeStatementList.size() < totalYear)
					continue;
				RoeYearWrapper wrapper = new RoeYearWrapper();
				wrapper.setRoeSize(totalYear);
				List<Double> roeList = new ArrayList<Double>();
				for (int j = incomeStatementList.size() - totalYear; j < incomeStatementList.size(); j++)
				{
					if (j == incomeStatementList.size() - totalYear)
					{
						wrapper.setStockID(stockID);
						wrapper.setYear(incomeStatementList.get(j).getYear());
					}
					double roe = (double)incomeStatementList.get(j).getNetIncome() / balanceSheetList.get(j).getStockholdersEquity();				
					roeList.add(StringUtil.setPointLength(roe*100));
				}			
				wrapper.setRoeList(roeList);
				if (this.checkExpectedRoe(roeList, demandRoe, demandYear))
					roeYearList.add(wrapper);
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		if (!discountRate.equals(""))
			roeYearList = this.getExpectedRate(roeYearList, Integer.parseInt(discountRate));
		return roeYearList;
	}
	private boolean checkExpectedRoe(List<Double> roeList, int demandRoe, int demandYear)
	{
		int expectedRoeCount = 0;
		for (int i = 0; i < roeList.size(); i++)
		{
			if (roeList.get(i) >= demandRoe)
			{
				expectedRoeCount++;
			}
		}
		if (expectedRoeCount >= demandYear)
			return true;
		else
			return false;
	}
	/**
	 * 可用折現率（合理股價）過濾
	 * @param stockList
	 * @param expectedRate
	 */
	private List<RoeYearWrapper> getExpectedRate (List<RoeYearWrapper> stockList, double expectedRate)
	{
		BuffettAnalysis buffettAnalysis = new BuffettAnalysis();
		expectedRate = expectedRate/100;
		List<RoeYearWrapper> roeYearList = new ArrayList<RoeYearWrapper>();
		for (int i = 0; i < stockList.size(); i++)
		{
			//System.out.println("StockID: " + stockList.get(i).getStockID());
			BuffettAnalysisWrapper wrapper = buffettAnalysis.getBuffettAnalysis(stockList.get(i).getStockID());		
			double expectedPrice = buffettAnalysis.expectedRate(expectedRate, wrapper.getReasonablePrice());
			expectedPrice = StringUtil.setPointLength(expectedPrice);
			if (wrapper.getPrice() < expectedPrice)
				roeYearList.add(stockList.get(i));
		}
		return roeYearList;
	}
}
