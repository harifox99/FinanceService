/**
 * 
 */
package org.bear.datainput;

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
				int expectedNum = 7;
				String[] seasons = {"01", "02", "03", "04"};
				String[] years = {"2012", "2013"};
				String stockID = wrapperList.get(j).getStockID();
				System.out.println("股票代碼：" + stockID + " " + idleTime + ". ");
				//季資料
				GetURLCathayIncomeStatement urlContent = new GetURLCathayIncomeStatement(stockID, false);
				IncomeStatementParserCathay incomeStatementYear = new IncomeStatementParserCathay(urlContent.getContent(), stockID, false, years, seasons, expectedNum, true);
				incomeStatementYear.parse(2);
				Thread.sleep(2000);	
				idleTime++;
			}
			//for (int j = 0; j < wrapperList.size(); j++)
			/*
			for (int j = 585; j < wrapperList.size(); j++)
			{
				int expectedNum = 8;
				String[] years = {"2005","2006","2007","2008","2009","2010","2011","2012"};
				String[] seasons = {"00"};
				String stockID = wrapperList.get(j).getStockID();
				System.out.println("股票代碼：" + stockID + " " + idleTime + ". ");
				//年資料
				GetURLCathayIncomeStatement urlContent = new GetURLCathayIncomeStatement(stockID, true);
				IncomeStatementParserCathay incomeStatementYear = new IncomeStatementParserCathay(urlContent.getContent(), stockID, true, years, seasons, expectedNum, true);
				incomeStatementYear.parse(2);
				Thread.sleep(5000);	
				idleTime++;
			}*/
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}
}
