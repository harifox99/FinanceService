/**
 * 
 */
package org.bear.datainput;

import org.bear.constant.FinancialReport;
import org.bear.parser.IncomeStatementParserCathay;
import org.bear.util.GetURLCathayIncomeStatement;

/**
 * @author edward
 * 去國泰網站抓損益表
 */
public class ImportIncomeStatementCathay extends ImportStockIDData 
{
	public void insertBatchList()
	{
		try
		{							
			int idleTime = 0;
			
			for (int j = 0; j < wrapperList.size(); j++)
			{
				int expectedNum = FinancialReport.expectedNum;
				String[] seasons = {"01", "02"};
				String[] years = {"2014"};
				String stockID = wrapperList.get(j).getStockID();
				System.out.println("股票代碼：" + stockID + " " + idleTime + ". ");
				//季資料
				GetURLCathayIncomeStatement urlContent = new GetURLCathayIncomeStatement(stockID, false);
				IncomeStatementParserCathay incomeStatementYear = new IncomeStatementParserCathay(urlContent.getContent(), stockID, false, years, seasons, expectedNum, true);
				
				try
				{					
					incomeStatementYear.parse(2);
				}
				catch (NullPointerException ex)
				{
					System.out.println("NullPointerException!");
					idleTime--;
					j--;
				}
				Thread.sleep(3000);	
				idleTime++;
			}
			/*
			for (int j = 1102; j < wrapperList.size(); j++)
			{
				int expectedNum = 1;
				String[] years = {"2013"};
				String[] seasons = {"00"};
				String stockID = wrapperList.get(j).getStockID();
				System.out.println("股票代碼：" + stockID + " " + idleTime + ". ");
				//年資料
				GetURLCathayIncomeStatement urlContent = new GetURLCathayIncomeStatement(stockID, true);
				IncomeStatementParserCathay incomeStatementYear = new IncomeStatementParserCathay(urlContent.getContent(), stockID, true, years, seasons, expectedNum, true);
				incomeStatementYear.parse(2);
				Thread.sleep(3000);	
				idleTime++;
			}*/
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}
}
