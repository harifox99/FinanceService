/**
 * 
 */
package org.bear.datainput;
import org.bear.parser.BalanceSheetParserCathay;
import org.bear.util.GetURLCathayBalanceSheet;

/**
 * @author edward
 * 去國泰網站抓資產負債表
 */
public class ImportBalanceSheetCathay extends ImportStockIDData
{
	public void insertBatchList()
	{
		try
		{		
			int idleTime = 0;
			
			/*
			for (int j = 0; j < wrapperList.size(); j++)
			{
				String[] years = {"2012"};
				String[] seasons = {"01", "02", "03", "04"};
				String stockID = wrapperList.get(j).getStockID();
				System.out.println("股票代碼：" + stockID + " " + idleTime + ". ");				
				//季資料
				GetURLCathayBalanceSheet urlContent = new GetURLCathayBalanceSheet(stockID, false);
				BalanceSheetParserCathay balanceSheetSeason = new BalanceSheetParserCathay(urlContent.getContent(), stockID, false, years, seasons);
				balanceSheetSeason.parse(2);
				Thread.sleep(10000);		
				idleTime++;
			}*/			
			//for (int j = 780; j < wrapperList.size(); j++)
			for (int j = 0; j < wrapperList.size(); j++)
			{
				int expectedNum = 8;
				String[] years = {"2005","2006","2007","2008","2009","2010","2011","2012",};
				String[] seasons = {"00"};
				String stockID = wrapperList.get(j).getStockID();
				System.out.println("股票代碼：" + stockID + " " + idleTime + ". ");				
				//年資料
				GetURLCathayBalanceSheet urlContent = new GetURLCathayBalanceSheet(stockID, true);
				BalanceSheetParserCathay balanceSheetYear = new BalanceSheetParserCathay(urlContent.getContent(), stockID, true, years, seasons, expectedNum, true);
				balanceSheetYear.parse(2);
				Thread.sleep(5000);		
				idleTime++;
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}
}
