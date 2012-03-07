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
			for (int j = 0; j < wrapperList.size(); j++)
			{
				String stockID = wrapperList.get(j).getStockID();
				System.out.println("股票代碼：" + stockID + " " + idleTime + ". ");
				
				//if (!stockID.startsWith("3380"))
				if (j < 514)
					continue;
				//年資料
				GetURLCathayBalanceSheet urlContent = new GetURLCathayBalanceSheet(stockID, true);
				BalanceSheetParserCathay balanceSheetYear = new BalanceSheetParserCathay(urlContent.getContent(), stockID, true);
				balanceSheetYear.parse(2);
				//季資料
				urlContent = new GetURLCathayBalanceSheet(stockID, false);
				BalanceSheetParserCathay balanceSheetSeason = new BalanceSheetParserCathay(urlContent.getContent(), stockID, false);
				balanceSheetSeason.parse(2);
				Thread.sleep(10000);		
				/*
				if (idleTime++ > 10)
				{
					break;
				}*/
				idleTime++;
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}
}
