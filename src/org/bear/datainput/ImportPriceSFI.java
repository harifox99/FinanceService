package org.bear.datainput;

import org.bear.util.newRevenue.GetHinetStockPrice;
import org.bear.util.newRevenue.GetMopsRevenue;
import org.bear.util.newRevenue.GetSFIGrateiRevenue;
import org.bear.util.newRevenue.GetSFITwseRevenue;
import org.bear.util.newRevenue.GetTwseIndividualIndex;
import org.bear.util.newRevenue.GretaiIndividualIndex;

public class ImportPriceSFI extends ImportStockID
{
	public void insertBatchList(String startYear, String startMonth, String endYear, String endMonth, GetSFIContent sfi)
	{
		//去證券期貨發展基金會抓營收資料
		try
		{		
			int sleepTime = 1000;
			int idleTime = 0;
			/*
			String[] stockid = {"1598","1776","2069","2236","2239","2633","2739","2748",
                                "3321","3346","3413","3708","4190","4438","4545",
                                "4551","4552","4555","4557","4763","4943","4968","5258",
                                "6442","6443","6451","6452","6456","6464","6477","6525","6531",
                                "8222","8341","8442","8463","8464","8466","8467","8473","8488"};
            */
			for (int j = 0; j < wrapperList.size(); j++)
			{
				String stockID = wrapperList.get(j).getStockID();
				String stockName = wrapperList.get(j).getStockName();
				/*
				if (!stockID.equals("8934"))
				{
					continue;
				}*/
				System.out.print(j);

				int stockBranch = wrapperList.get(j).getStockBranch();
				System.out.println("股票代碼：" + stockID + " " + idleTime + ". ");	
				idleTime++;
				if (sfi instanceof GetTwseIndividualIndex && stockBranch == 2)
					continue;
				else if (sfi instanceof GretaiIndividualIndex && stockBranch == 1)
					continue;
				else if (sfi instanceof GetHinetStockPrice)
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
