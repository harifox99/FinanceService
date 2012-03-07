package org.bear.datainput;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import org.bear.entity.BasicStockWrapper;
import org.bear.util.ParseFile;
import org.bear.util.RegEx;
/**
 * @author edward
 * 儲存上市公司名稱、代碼、股票種類
 */
public class ImportBasicStock extends ParseFile 
{
	public ImportBasicStock(){};
	int stockType;
	public ArrayList <BasicStockWrapper> getBasicStockList()
	{
		ArrayList <BasicStockWrapper> list = new ArrayList <BasicStockWrapper>();
		try
		{			
			reader = new BufferedReader(new FileReader("data/StockID.csv"));
			while((readData = reader.readLine()) != null)
			{
				if (this.intRowIndex++ == 0)
					continue;
				else
				{
					System.out.println(this.intRowIndex);
					bufferData = readData.split(",");
					String stockID = bufferData[0].trim();				
					if (new RegEx("^[1-9][0-9]{3,3}", stockID).isMatch() == true)
						list.add(this.getBasicStockWrapper(bufferData));
					else if (new RegEx("^[0-9]{1,2}", stockID).isMatch() == true)
						stockType = Integer.parseInt(stockID);
				}			
			}
		}
		catch (IOException ex)
		{
			ex.printStackTrace();
		}
		return list;
	}
	private BasicStockWrapper getBasicStockWrapper(String[] bufferData)
	{		
		BasicStockWrapper wrapper = new BasicStockWrapper();
		wrapper.setStockID(bufferData[0].trim());
		wrapper.setStockName(bufferData[1].trim());
		wrapper.setStockBranch(1);
		wrapper.setStockType(stockType);
		return wrapper;
	}
}
