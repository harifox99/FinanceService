/**
 * 
 */
package org.bear.util;

import java.util.ArrayList;
import java.util.List;

import org.bear.entity.BalanceSheetEntity;
import org.bear.entity.IncomeStatementEntity;
import org.bear.entity.ROEAnalysisWrapper;

/**
 * @author edward
 *
 */
public class CalculateROEAnalysis 
{
	List <BalanceSheetEntity> balanceSheetList;
	List <IncomeStatementEntity> incomeStatementList;
	List <ROEAnalysisWrapper> roeAnalysisList;
	ROEAnalysisWrapper wrapper;
	boolean isYear;
	public CalculateROEAnalysis(List <BalanceSheetEntity> balanceSheetList, List <IncomeStatementEntity> incomeStatementList, boolean isYear)
	{
		this.balanceSheetList = balanceSheetList;
		this.incomeStatementList = incomeStatementList;
		this.isYear = isYear;
		roeAnalysisList = new ArrayList<ROEAnalysisWrapper>();		
	}
	public List <ROEAnalysisWrapper> getROEAnalysis()
	{
		//資產總額
		int lastAsset = 0; 
		//股東權益
		int lastEquity = 0;
		double ratioNumber;
		for (int i = 0; i < balanceSheetList.size(); i++)
		{
			wrapper = new ROEAnalysisWrapper();
			//純益率
			ratioNumber = (double) incomeStatementList.get(i).getNetIncome() / incomeStatementList.get(i).getOperatingRevenue() * 100;
			ratioNumber = StringUtil.setPointLength(ratioNumber);
			wrapper.setNetProfitMargin(ratioNumber);
			if (i == 0)
			{
				lastAsset = balanceSheetList.get(i).getTotalAssets();
				lastEquity = balanceSheetList.get(i).getStockholdersEquity();
			}
			else
			{
				//總資產週轉率
				ratioNumber = (double) incomeStatementList.get(i).getOperatingRevenue() / ((lastAsset+balanceSheetList.get(i).getTotalAssets())/2) * 100;
				ratioNumber = StringUtil.setPointLength(ratioNumber);
				wrapper.setTotalAssetTurnover(ratioNumber);
				//權益乘數
				ratioNumber = (double) ((lastAsset+balanceSheetList.get(i).getTotalAssets())/2) / ((lastEquity+balanceSheetList.get(i).getStockholdersEquity())/2);
				ratioNumber = StringUtil.setPointLength(ratioNumber);
				wrapper.setEquityMultiplier(ratioNumber);
				//ROE
				ratioNumber = (double) incomeStatementList.get(i).getNetIncome() / ((lastEquity+balanceSheetList.get(i).getStockholdersEquity())/2) * 100;
				ratioNumber = StringUtil.setPointLength(ratioNumber);
				wrapper.setRoe(ratioNumber);
				if (isYear)
					wrapper.setYear(balanceSheetList.get(i).getYear());
				else
					wrapper.setYear(balanceSheetList.get(i).getYear() + "-" + balanceSheetList.get(i).getSeasons());
				roeAnalysisList.add(wrapper);
				/***************************/
				lastAsset = balanceSheetList.get(i).getTotalAssets();
				lastEquity = balanceSheetList.get(i).getStockholdersEquity();
			}
		}
		return roeAnalysisList;
	}
}
