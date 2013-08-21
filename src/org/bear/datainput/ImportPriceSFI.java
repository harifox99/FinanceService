package org.bear.datainput;

import org.bear.util.newRevenue.GetTwseIndividualIndex;
import org.bear.util.newRevenue.GretaiIndividualIndex;

public class ImportPriceSFI extends ImportStockIDData
{
	public void insertBatchList(String startYear, String startMonth, String endYear, String endMonth, GetSFIContent sfi)
	{
		//去證券期貨發展基金會抓營收資料
		try
		{		
			int idleTime = 0;
			for (int j = 0; j < wrapperList.size(); j++)
			{
				String stockID = wrapperList.get(j).getStockID();				
				int stockBranch = wrapperList.get(j).getStockBranch();
				System.out.println("股票代碼：" + stockID + " " + idleTime + ". ");	
				idleTime++;
				if (sfi instanceof GetTwseIndividualIndex && stockBranch == 2)
					continue;
				else if (sfi instanceof GretaiIndividualIndex && stockBranch == 1)
					continue;
				else
					sfi.getContent(stockID, startYear, startMonth, endYear, endMonth);
				Thread.sleep(5000);
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}
}
