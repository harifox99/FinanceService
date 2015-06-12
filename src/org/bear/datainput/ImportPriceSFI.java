package org.bear.datainput;

import org.bear.util.newRevenue.GetHinetStockPrice;
import org.bear.util.newRevenue.GetMopsRevenue;
import org.bear.util.newRevenue.GetSFIGrateiRevenue;
import org.bear.util.newRevenue.GetSFITwseRevenue;
import org.bear.util.newRevenue.GetTwseIndividualIndex;
import org.bear.util.newRevenue.GretaiIndividualIndex;

public class ImportPriceSFI extends ImportStockIDData
{
	public void insertBatchList(String startYear, String startMonth, String endYear, String endMonth, GetSFIContent sfi)
	{
		//去證券期貨發展基金會抓營收資料
		try
		{		
			int sleepTime = 1000;
			int idleTime = 0;
			for (int j = 0; j < wrapperList.size(); j++)
			{
				String stockID = wrapperList.get(j).getStockID();
				String stockName = wrapperList.get(j).getStockName();
				//if (!stockID.equals("1256"))
					//continue;
				int stockBranch = wrapperList.get(j).getStockBranch();
				System.out.println("股票代碼：" + stockID + " " + idleTime + ". ");	
				idleTime++;
				if (sfi instanceof GetTwseIndividualIndex && stockBranch == 2)
					continue;
				else if (sfi instanceof GretaiIndividualIndex && stockBranch == 1)
					continue;
				else if (sfi instanceof GetHinetStockPrice && stockBranch == 1)
				{
					sfi.getContent(stockID, stockName, startYear, startMonth, endYear, endMonth);
					Thread.sleep(sleepTime);
				}
				else if (sfi instanceof GretaiIndividualIndex && stockBranch == 2)
				{
					sfi.getContent(stockID, stockName, startYear, startMonth, endYear, endMonth);
					Thread.sleep(sleepTime);
				}
				else if (sfi instanceof GetTwseIndividualIndex && stockBranch == 1)
				{
					sfi.getContent(stockID, stockName, startYear, startMonth, endYear, endMonth);
					Thread.sleep(sleepTime);
				}
				else if (sfi instanceof GetSFIGrateiRevenue && stockBranch == 2)
				{
					sfi.getContent(stockID, stockName, startYear, startMonth, endYear, endMonth);
					Thread.sleep(sleepTime);
				}
				else if (sfi instanceof GetSFITwseRevenue && stockBranch == 1)
				{
					sfi.getContent(stockID, stockName, startYear, startMonth, endYear, endMonth);
					Thread.sleep(sleepTime);
				}
				else if (sfi instanceof GetMopsRevenue)
				{
					sfi.getContent(stockID, stockName, startYear, startMonth, endYear, endMonth);
					Thread.sleep(sleepTime);
				}
				else
					continue;
				
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}
}
