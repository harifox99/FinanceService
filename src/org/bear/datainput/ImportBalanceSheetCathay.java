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
public class ImportBalanceSheetCathay extends ImportStockIDData
{
	public void insertBatchList()
	{
		try
		{		
			int idleTime = 0;
			
			for (int j = 0; j < wrapperList.size(); j++)
			{
				int expectedNum = FinancialReport.expectedNum;
				String[] years = {"2014"};
				String[] seasons = {"01", "02"};				
				String stockID = wrapperList.get(j).getStockID();
				/*
				if (!(stockID.equals("2314") || stockID.equals("2321") || stockID.equals("2364") || stockID.equals("2454") ||
					 stockID.equals("2603") || stockID.equals("2880") || stockID.equals("2882") || stockID.equals("2883") ||
					 stockID.equals("2885") || stockID.equals("2886") || stockID.equals("2887") || stockID.equals("2888") ||
					 stockID.equals("2889") || stockID.equals("2890") || stockID.equals("2891") || stockID.equals("2892") ||		
					 stockID.equals("3443") || stockID.equals("3625") || stockID.equals("3646") || stockID.equals("3702") ||
					 stockID.equals("4103") || stockID.equals("4138") || stockID.equals("4139") || stockID.equals("4160") ||
					 stockID.equals("4207") || stockID.equals("4304") || stockID.equals("4503") || stockID.equals("4506") ||	
					 stockID.equals("4529") || stockID.equals("4609") || stockID.equals("4707") || stockID.equals("4720") ||
					 stockID.equals("5014") || stockID.equals("5820") || stockID.equals("5880")				 
						))
					continue;
					*/
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
				Thread.sleep(3000);		
				idleTime++;
			}		
			/*
			for (int j = 1389; j < wrapperList.size(); j++)
			{
				int expectedNum = 1;
				String[] years = {"2013"};
				String[] seasons = {"00"};
				String stockID = wrapperList.get(j).getStockID();
				System.out.println("股票代碼：" + stockID + " " + idleTime + ". ");				
				//年資料
				GetURLCathayBalanceSheet urlContent = new GetURLCathayBalanceSheet(stockID, true);
				BalanceSheetParserCathay balanceSheetYear = new BalanceSheetParserCathay(urlContent.getContent(), stockID, true, years, seasons, expectedNum, true);
				balanceSheetYear.parse(2);
				Thread.sleep(2000);		
				idleTime++;
			}*/
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}
}
