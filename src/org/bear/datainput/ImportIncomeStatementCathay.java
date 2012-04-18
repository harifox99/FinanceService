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
			//String[] seasons = {"04"};
			String year[] = {"2011"};
			for (int i = 0; i < year.length; i++)
			{			
				int idleTime = 0;
				for (int j = 0; j < wrapperList.size(); j++)
				{
					String stockID = wrapperList.get(j).getStockID();
					System.out.println("股票代碼：" + stockID + " " + idleTime + ". ");
					//年資料
					GetURLCathayIncomeStatement urlContent = new GetURLCathayIncomeStatement(stockID, true);
					IncomeStatementParserCathay incomeStatementYear = new IncomeStatementParserCathay(urlContent.getContent(), stockID, true, year[i], "00");
					incomeStatementYear.parse(2);
					//季資料
					/*
					GetURLCathayIncomeStatement urlContent = new GetURLCathayIncomeStatement(stockID, false);
					IncomeStatementParserCathay incomeStatementSeason = new IncomeStatementParserCathay(urlContent.getContent(), stockID, false, "2011", "04");
					incomeStatementSeason.parse(2);*/
					Thread.sleep(10000);	
					idleTime++;
				}
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}
}
