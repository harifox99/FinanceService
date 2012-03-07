/**
 * 
 */
package org.bear.util;

import java.util.ArrayList;
import java.util.List;

import org.bear.dao.BalanceSheetDao;
import org.bear.dao.IncomeStatementDao;
import org.bear.entity.BalanceSheetEntity;
import org.bear.entity.BasicEntity;
import org.bear.entity.IncomeStatementEntity;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author edward
 *
 */
public class CalculateReinvestmentRate extends BasicEntity
{
	List <Double> reinvestmentRateList;
	double reinvestmentRate;
	public List<Double> getReinvestmentRateList() 
	{
		try
		{
			reinvestmentRateList = new ArrayList <Double>();
			//計算盈再率需要過去四年資料
			final int arraySize = 4;
			//四年前固定資產
			int fiexdAssetOld = 0;
			//四年前長期投資
			int longTermInvestmentOld = 0;
			//前三年淨利
			int netIncomeOld = 0;
			//前四季固定資產
			int fiexdAsset = 0;
			//前四季長期投資
			int longTermInvestment = 0;
			//前四季淨利
			int netIncome = 0;	
			ApplicationContext context = new ClassPathXmlApplicationContext("config.xml");
			//資產負債表
			BalanceSheetDao balanceSheetDao = (BalanceSheetDao)context.getBean("basicBalanceSheetDao");
			List <BalanceSheetEntity> balanceSheetList;
			balanceSheetList = balanceSheetDao.findDataByYear(stockID, year);
			//損益表
			IncomeStatementDao incomeStatementDao = (IncomeStatementDao)context.getBean("basicIncomeStatementDao");;
			List <IncomeStatementEntity> incomeStatementList;
			incomeStatementList = incomeStatementDao.findDataByYear(stockID, year);
			for (int i = balanceSheetList.size() - 1; i >= arraySize; i--)
			{
				if (i == balanceSheetList.size() - 1)
				{
					//給計算最近四季盈再率使用
					fiexdAssetOld = balanceSheetList.get(i-arraySize+1).getFixedAssets();
					longTermInvestmentOld = balanceSheetList.get(i-arraySize+1).getLongTermInvestment();
					netIncomeOld = (incomeStatementList.get(i).getNetIncome() + incomeStatementList.get(i-1).getNetIncome() + incomeStatementList.get(i-2).getNetIncome());
				}
				//(當年固定資產 + 當年長期投資 - 四年前固定資產 - 四年前長期投資)/(今年加前三年淨利)               
				reinvestmentRate = (double)(balanceSheetList.get(i).getFixedAssets() + balanceSheetList.get(i).getLongTermInvestment() -
				balanceSheetList.get(i-arraySize).getFixedAssets() - balanceSheetList.get(i-arraySize).getLongTermInvestment())/
				(incomeStatementList.get(i).getNetIncome() + incomeStatementList.get(i-1).getNetIncome() + incomeStatementList.get(i-2).getNetIncome() + incomeStatementList.get(i-3).getNetIncome());
				reinvestmentRate = StringUtil.setPointLength(reinvestmentRate*100);
				reinvestmentRateList.add(reinvestmentRate);
			}
			balanceSheetList = balanceSheetDao.findDataBySeason(stockID, year, seasons);
			incomeStatementList = incomeStatementDao.findDataBySeason(stockID, year, seasons);
			for (int i = balanceSheetList.size() - 1; i >= balanceSheetList.size() - arraySize; i--)
			{           
				if (i == balanceSheetList.size() - 1)
				{
					fiexdAsset = balanceSheetList.get(i).getFixedAssets();
					longTermInvestment = balanceSheetList.get(i).getLongTermInvestment();
				}				
				netIncome += incomeStatementList.get(i).getNetIncome();
			}
			//(前四季固定資產 + 前四季長期投資 - 四年前固定資產 - 四年前長期投資)/(前四季加前三年淨利)
			reinvestmentRate = (double)(fiexdAsset + longTermInvestment - fiexdAssetOld - longTermInvestmentOld)/(netIncome + netIncomeOld);
			reinvestmentRate = StringUtil.setPointLength(reinvestmentRate*100);
			reinvestmentRateList.add(0, reinvestmentRate);
			reinvestmentRate = 0;
			reinvestmentRateList.add(reinvestmentRate);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return reinvestmentRateList;
	}

	public void setReinvestmentRateList(List<Double> reinvestmentRateList) {
		this.reinvestmentRateList = reinvestmentRateList;
	}
	
}
