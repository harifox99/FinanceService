/**
 * 
 */
package org.bear.util;

import java.util.ArrayList;
import java.util.List;

import org.bear.entity.BalanceSheetEntity;
import org.bear.entity.CashConversionCycleWrapper;
import org.bear.entity.IncomeStatementEntity;

/**
 * @author edward
 *
 */
public class CalculateCCC 
{
	List <BalanceSheetEntity> balanceSheetList;
	List <IncomeStatementEntity> incomeStatementList;
	List <CashConversionCycleWrapper> cccList;
	CashConversionCycleWrapper wrapper;
	boolean isYear;
	public CalculateCCC(List <BalanceSheetEntity> balanceSheetList, List <IncomeStatementEntity> incomeStatementList, boolean isYear)
	{
		this.balanceSheetList = balanceSheetList;
		this.incomeStatementList = incomeStatementList;
		this.isYear = isYear;
		cccList = new ArrayList<CashConversionCycleWrapper>();		
	}
	public List <CashConversionCycleWrapper> getCCC()
	{
		//平均存貨
		int lastInventory = 0; 
		//平均應收帳款
		int lastReceivable = 0;
		//平均應付帳款
		int lastAccountsPayable = 0;
		double ratioNumber;
		for (int i = 0; i < balanceSheetList.size(); i++)
		{
			wrapper = new CashConversionCycleWrapper();
			if (i == 0)
			{
				lastInventory = balanceSheetList.get(i).getInventory();
				lastReceivable = balanceSheetList.get(i).getReceivable();
				lastAccountsPayable = balanceSheetList.get(i).getAccountsPayable();
			}
			else
			{
				//存貨週轉率
				ratioNumber = (double) incomeStatementList.get(i).getOperatingCost() / ((balanceSheetList.get(i).getInventory()+lastInventory)/2);
				ratioNumber = StringUtil.setPointLength(ratioNumber);
				wrapper.setInventoryTurnover(ratioNumber);
				//平均存貨天數
				if (isYear)
					ratioNumber = (double) 365/ratioNumber;
				else
					ratioNumber = (double) 90/ratioNumber;
				ratioNumber = StringUtil.setPointLength(ratioNumber);
				wrapper.setInventoryDays(ratioNumber);
				//應收帳款週轉率
				ratioNumber = (double) incomeStatementList.get(i).getOperatingRevenue() / ((balanceSheetList.get(i).getReceivable()+lastReceivable)/2);				
				ratioNumber = StringUtil.setPointLength(ratioNumber);
				wrapper.setReceivableTurnover(ratioNumber);
				//平均應收帳款收現天數
				if (isYear)
					ratioNumber = (double) 365/ratioNumber;
				else
					ratioNumber = (double) 90/ratioNumber;
				ratioNumber = StringUtil.setPointLength(ratioNumber);
				wrapper.setAverageCollctionPeriod(ratioNumber);
				//應付帳款週轉率
				ratioNumber = (double) incomeStatementList.get(i).getOperatingCost() / ((balanceSheetList.get(i).getAccountsPayable()+lastAccountsPayable)/2);				
				ratioNumber = StringUtil.setPointLength(ratioNumber);
				wrapper.setAccountsPayableTurnover(ratioNumber);
				//平均應付帳款付款天數
				if (isYear)
					ratioNumber = (double) 365/ratioNumber;
				else
					ratioNumber = (double) 90/ratioNumber;
				ratioNumber = StringUtil.setPointLength(ratioNumber);
				wrapper.setAverageAccountsPayableDays(ratioNumber);
				//現金轉換循環
				ratioNumber = wrapper.getInventoryDays() + wrapper.getAverageCollctionPeriod() - wrapper.getAverageAccountsPayableDays();
				wrapper.setCcc(StringUtil.setPointLength(ratioNumber));
				if (isYear)
					wrapper.setYear(balanceSheetList.get(i).getYear());
				else
					wrapper.setYear(balanceSheetList.get(i).getYear() + "-" + balanceSheetList.get(i).getSeasons());
				cccList.add(wrapper);
				lastInventory = balanceSheetList.get(i).getInventory();
				lastReceivable = balanceSheetList.get(i).getReceivable();
				lastAccountsPayable = balanceSheetList.get(i).getAccountsPayable();
			}
		}
		return cccList;
	}
}
