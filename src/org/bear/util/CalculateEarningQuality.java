/**
 * 
 */
package org.bear.util;

import java.util.ArrayList;
import java.util.List;

import org.bear.entity.BalanceSheetEntity;
import org.bear.entity.EarningQualityWrapper;
import org.bear.entity.IncomeStatementEntity;

/**
 * @author edward
 *
 */
public class CalculateEarningQuality 
{
	List <BalanceSheetEntity> balanceSheetList;
	List <IncomeStatementEntity> incomeStatementList;
	List <EarningQualityWrapper> earningQualityList;
	EarningQualityWrapper wrapper;
	boolean isYear;
	public CalculateEarningQuality(List <BalanceSheetEntity> balanceSheetList, List <IncomeStatementEntity> incomeStatementList, boolean isYear)
	{
		this.balanceSheetList = balanceSheetList;
		this.incomeStatementList = incomeStatementList;
		this.isYear = isYear;
		earningQualityList = new ArrayList<EarningQualityWrapper>();		
	}
	public List <EarningQualityWrapper> getEarningQuality()
	{
		final int wrapperLength = 6;
		double ratioNumber;
		/*
		 * first[0] = 存貨
		 * first[1] = 銷貨收入
		 * first[2] = 應收帳款
		 * first[3] = 銷貨毛利
		 * first[4] = 營業費用
		 * first[5] = 應付帳款
		 */
		int first[] = new int[wrapperLength];
		int second[] = new int[wrapperLength];
		for (int i = 0; i < balanceSheetList.size(); i++)
		{
			wrapper = new EarningQualityWrapper();
			if (i == 0)
			{
				first[0] = balanceSheetList.get(i).getInventory();
				first[1] = incomeStatementList.get(i).getOperatingRevenue();
				first[2] = balanceSheetList.get(i).getReceivable();
				first[3] = incomeStatementList.get(i).getGrossProfit();
				first[4] = incomeStatementList.get(i).getOperatingExpense();
				first[5] = balanceSheetList.get(i).getAccountsPayable();
			}
			else if (i == 1)
			{
				second[0] = balanceSheetList.get(i).getInventory();
				second[1] = incomeStatementList.get(i).getOperatingRevenue();
				second[2] = balanceSheetList.get(i).getReceivable();
				second[3] = incomeStatementList.get(i).getGrossProfit();
				second[4] = incomeStatementList.get(i).getOperatingExpense();
				second[5] = balanceSheetList.get(i).getAccountsPayable();
			}
			else
			{
				//先記銷貨收入增加率
				ratioNumber = (double) (incomeStatementList.get(i).getOperatingRevenue() - (first[1]+second[1])/2) / ((first[1] + second[1])/2);
				ratioNumber = StringUtil.setPointLength(ratioNumber, 4);
				wrapper.setOperatingRevenueRatio(ratioNumber);				
				double operatingRevenue = ratioNumber;
				//存貨
				ratioNumber = (double) (balanceSheetList.get(i).getInventory() - (first[0]+second[0])/2) / ((first[0] + second[0])/2);
				ratioNumber = StringUtil.setPointLength(ratioNumber, 4);
				wrapper.setInventoryRatio(ratioNumber);				
				wrapper.setInventoryIndex(StringUtil.setPointLength(ratioNumber - operatingRevenue, 4));
				//應收帳款
				ratioNumber = (double) (balanceSheetList.get(i).getReceivable() - (first[2]+second[2])/2) / ((first[2] + second[2])/2);
				ratioNumber = StringUtil.setPointLength(ratioNumber, 4);
				wrapper.setReceivableRatio(ratioNumber);
				wrapper.setReceivableIndex(StringUtil.setPointLength(ratioNumber - operatingRevenue, 4));
				//銷貨毛利
				ratioNumber = (double) (incomeStatementList.get(i).getGrossProfit() - (first[3]+second[3])/2) / ((first[3] + second[3])/2);
				ratioNumber = StringUtil.setPointLength(ratioNumber, 4);
				wrapper.setGrossProfitRatio(ratioNumber);
				wrapper.setGrossProfitIndex(StringUtil.setPointLength(operatingRevenue - ratioNumber, 4));
				//營業費用
				ratioNumber = (double) (incomeStatementList.get(i).getOperatingExpense() - (first[4]+second[4])/2) / ((first[4] + second[4])/2);
				ratioNumber = StringUtil.setPointLength(ratioNumber, 4);
				wrapper.setOperatingExpensesRatio(ratioNumber);
				wrapper.setOperatingExpensesIndex(StringUtil.setPointLength(ratioNumber - operatingRevenue, 4));
				//應付帳款
				ratioNumber = (double) (balanceSheetList.get(i).getAccountsPayable() - (first[5]+second[5])/2) / ((first[5] + second[5])/2);
				ratioNumber = StringUtil.setPointLength(ratioNumber, 4);
				wrapper.setAccountsPayableRatio(ratioNumber);
				wrapper.setAccountsPayableIndex(StringUtil.setPointLength(operatingRevenue - ratioNumber , 4));
				if (isYear)
					wrapper.setYear(balanceSheetList.get(i).getYear());
				else
					wrapper.setYear(balanceSheetList.get(i).getYear() + "-" + balanceSheetList.get(i).getSeasons());
				earningQualityList.add(wrapper);
				/******************************/
				first[0] = second[0];
				first[1] = second[1];
				first[2] = second[2];
				first[3] = second[3];
				first[4] = second[4];
				first[5] = second[5];
				second[0] = balanceSheetList.get(i).getInventory();
				second[1] = incomeStatementList.get(i).getOperatingRevenue();
				second[2] = balanceSheetList.get(i).getReceivable();
		        second[3] = incomeStatementList.get(i).getGrossProfit();
                second[4] = incomeStatementList.get(i).getOperatingExpense();
				second[5] = balanceSheetList.get(i).getAccountsPayable();
			}
		}
		return earningQualityList;
	}
}
