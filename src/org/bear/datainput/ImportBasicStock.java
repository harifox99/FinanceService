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
			reader = new BufferedReader(new FileReader("data/2018T2-04.csv"));
			//上市公司
			while((readData = reader.readLine()) != null)
			{
				if (this.intRowIndex++ == 0)
					continue;
				else
				{
					//System.out.println(this.intRowIndex);
					bufferData = readData.split(",");
					String stockID = bufferData[0].trim();	
					if (new RegEx("^[1-9][0-9]{3,3}", stockID).isMatch() == true)
					{
						System.out.println("stockID:" + stockID);
						list.add(this.getBasicStockWrapper(bufferData));
					}
					else if (new RegEx("^[0-9]{1,2}", stockID).isMatch() == true)
					{
						System.out.println("stockType:" + stockID);
						stockType = Integer.parseInt(stockID);
					}
				}			
			}
			System.out.println("上市End:");
			
			//上櫃公司
			reader = new BufferedReader(new FileReader("data/Gretai2018.csv"));
			while((readData = reader.readLine()) != null)
			{
				if (readData.equals(",,,,"))
					break;
				else if (this.intRowIndex++ == 0)
					continue;				
				else
				{
					System.out.println(this.intRowIndex);
					bufferData = readData.split(",");
					String stockID = bufferData[0].trim();				
					if (new RegEx("^[1-9][0-9]{3,3}", stockID).isMatch() == true)
						list.add(this.getBasicGretaiWrapper(bufferData));
				}			
			}
			System.out.println("上櫃End:");
			
		}
		catch (IOException ex)
		{
			ex.printStackTrace();
		}
		return list;
	}
	//處理上市資訊
	private BasicStockWrapper getBasicStockWrapper(String[] bufferData)
	{		
		BasicStockWrapper wrapper = new BasicStockWrapper();
		wrapper.setStockID(bufferData[0].trim());
		wrapper.setStockName(bufferData[1].trim());
		wrapper.setStockBranch(1);
		wrapper.setStockType(stockType);
		wrapper.setEnabled(1);
		return wrapper;
	}
	//處理上櫃資訊
	private BasicStockWrapper getBasicGretaiWrapper(String[] bufferData)
	{		
		BasicStockWrapper wrapper = new BasicStockWrapper();
		wrapper.setStockID(bufferData[0].trim());
		wrapper.setStockName(bufferData[1].trim());
		//1.上市；2上櫃
		wrapper.setStockBranch(2);
		//目前無法解析上櫃種類，一律以32代替
		wrapper.setStockType(32);
		wrapper.setEnabled(1);
		return wrapper;
	}
}
