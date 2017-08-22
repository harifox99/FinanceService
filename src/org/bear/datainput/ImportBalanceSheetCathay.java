/**
 * 
 */
package org.bear.datainput;
import org.bear.constant.FinancialReport;
import org.bear.parser.BalanceSheetParserCathay;
import org.bear.util.GetURLCathayBalanceSheet;

/**
 * @author edward
 * 去國泰網站抓資產負債表
 */
public class ImportBalanceSheetCathay extends ImportStockID
{
	public void insertBatchList()
	{
		try
		{		
			int idleTime = 0;
			for (int j = 0; j < wrapperList.size(); j++)
			{
				int expectedNum = FinancialReport.expectedNum;
				String[] years = {"2017"};
				String[] seasons = {"01", "02"};				
				String stockID = wrapperList.get(j).getStockID();
				//if (!stockID.equals("8925"))
					//continue;
				System.out.println("股票代碼：" + stockID + " " + idleTime + ". ");				
				//季資料
				GetURLCathayBalanceSheet urlContent = new GetURLCathayBalanceSheet(stockID, false);
				BalanceSheetParserCathay balanceSheetSeason = new BalanceSheetParserCathay(urlContent.getContent(), stockID, false, years, seasons, expectedNum, true);
				try
				{					
					balanceSheetSeason.parse(2);
				}
				catch (NullPointerException ex)
				{
					System.out.println("NullPointerException!");
					idleTime--;
					j--;
				}
				Thread.sleep(FinancialReport.sleepTime);		
				idleTime++;
			}
			/*
			for (int j = 0; j < wrapperList.size(); j++)
			{
				int expectedNum = 2;
				String[] years = {"2015", "2016"};
				String[] seasons = {"00"};
				String stockID = wrapperList.get(j).getStockID();
				System.out.println("股票代碼：" + stockID + " " + idleTime + ". ");				
				//年資料
				GetURLCathayBalanceSheet urlContent = new GetURLCathayBalanceSheet(stockID, true);
				BalanceSheetParserCathay balanceSheetYear = new BalanceSheetParserCathay(urlContent.getContent(), stockID, true, years, seasons, expectedNum, true);
				balanceSheetYear.parse(2);
				Thread.sleep(FinancialReport.sleepTime);		
				idleTime++;
			}*/
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}
}
