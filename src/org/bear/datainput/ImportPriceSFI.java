package org.bear.datainput;

import org.bear.util.newRevenue.GetSFIPrice;
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
			int idleTime = 0;
			for (int j = 0; j < wrapperList.size(); j++)
			{
				String stockID = wrapperList.get(j).getStockID();			
				if (!stockID.equals("1338") )
					continue;
				int stockBranch = wrapperList.get(j).getStockBranch();
				System.out.println("股票代碼：" + stockID + " " + idleTime + ". ");	
				idleTime++;
				if (sfi instanceof GetTwseIndividualIndex && stockBranch == 2)
					continue;
				else if (sfi instanceof GretaiIndividualIndex && stockBranch == 1)
					continue;
				else if (sfi instanceof GetSFIPrice && stockBranch == 1)
					sfi.getContent(stockID, startYear, startMonth, endYear, endMonth);
				else if (sfi instanceof GretaiIndividualIndex && stockBranch == 2)
					sfi.getContent(stockID, startYear, startMonth, endYear, endMonth);
				else if (sfi instanceof GetTwseIndividualIndex && stockBranch == 1)
					sfi.getContent(stockID, startYear, startMonth, endYear, endMonth);
				else if (sfi instanceof GetSFITwseRevenue && stockBranch == 1)
					sfi.getContent(stockID, startYear, startMonth, endYear, endMonth);
				else
					continue;
				Thread.sleep(3000);
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}
}
