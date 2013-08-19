package org.bear.datainput;
public class ImportPriceSFI extends ImportStockIDData
{
	public void insertBatchList(String startYear, String startMonth, String endYear, String endMonth, GetSFIContent sfi)
	{
		//去證券期貨發展基金會抓營收資料
		try
		{		
			int idleTime = 0;
			//for (int j = 0; j < wrapperList.size(); j++)
			for (int j = 0; j < 1; j++)
			{
				String stockID = wrapperList.get(j).getStockID();				
				//int stockBranch = wrapperList.get(j).getStockBranch();
				System.out.println("股票代碼：" + stockID + " " + idleTime + ". ");				
				idleTime++;
				sfi.getContent(stockID, startYear, startMonth, endYear, endMonth);
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}
}
