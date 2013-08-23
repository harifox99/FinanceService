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
			/* for (int j = 0; j < wrapperList.size(); j++)
			{
				String[] seasons = {"01", "02", "03", "04"};
				String[] years = {"2012"};
				String stockID = wrapperList.get(j).getStockID();
				System.out.println("股票代碼：" + stockID + " " + idleTime + ". ");
				//季資料
				GetURLCathayIncomeStatement urlContent = new GetURLCathayIncomeStatement(stockID, false);
				IncomeStatementParserCathay incomeStatementYear = new IncomeStatementParserCathay(urlContent.getContent(), stockID, false, years, seasons);
				incomeStatementYear.parse(2);
				Thread.sleep(5000);	
				idleTime++;
			}*/
			
			//for (int j = 0; j < wrapperList.size(); j++)
			for (int j = 0; j < 1; j++)
			{
				String[] seasons = {"00"};
				String[] years = {"2012"};
				String stockID = wrapperList.get(j).getStockID();
				System.out.println("股票代碼：" + stockID + " " + idleTime + ". ");
				//年資料
				GetURLCathayIncomeStatement urlContent = new GetURLCathayIncomeStatement(stockID, true);
				IncomeStatementParserCathay incomeStatementSeason = new IncomeStatementParserCathay(urlContent.getContent(), stockID, true, years, seasons);
				incomeStatementSeason.parse(2);
				Thread.sleep(10000);	
				idleTime++;
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}
}
