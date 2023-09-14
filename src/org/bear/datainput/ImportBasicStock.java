package org.bear.datainput;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
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
	Map<String, Integer> stockMap;
	public ArrayList <BasicStockWrapper> getBasicStockList(Map<String, Integer> stockMap)
	{
		this.stockMap = stockMap;
		ArrayList <BasicStockWrapper> list = new ArrayList <BasicStockWrapper>();
		try
		{			
			reader = new BufferedReader(new FileReader("data/twse2023Q2.csv"));
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
					stockID = stockID.replace("\"","");
					if (new RegEx("^[1-9][0-9]{3,3}", stockID).isMatch() == true)
					{
						System.out.println("stockID:" + stockID);
						list.add(this.getBasicStockWrapper(bufferData));
					}
					/*
					else if (new RegEx("^[0-9]{1,2}", stockID).isMatch() == true)
					{
						System.out.println("stockType:" + stockID);
						stockType = Integer.parseInt(stockID);
					}*/
				}			
			}
			System.out.println("上市End:");			
			//上櫃公司
			reader = new BufferedReader(new FileReader("data/tpex2023Q2.csv"));
			while((readData = reader.readLine()) != null)
			{
				if (this.intRowIndex++ == 0)
					continue;				
				else
				{
					bufferData = readData.split(",");
					String stockID = bufferData[0].trim();	
					stockID = stockID.replace("\"","");
					if (new RegEx("^[1-9][0-9]{3,3}", stockID).isMatch() == true)
					{
						System.out.println("stockID:" + stockID);
						list.add(this.getBasicGretaiWrapper(bufferData));
					}
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
		wrapper.setStockID(bufferData[0].trim().replace("\"",""));
		wrapper.setStockName(bufferData[1].trim().replace("\"",""));
		wrapper.setStockBranch(1);
		String stockName = bufferData[3].trim().replace("\"","");
		int stockType = stockMap.get(stockName);
		wrapper.setStockType(stockType);
		wrapper.setEnabled(1);
		wrapper.setCapital(0);
		return wrapper;
	}
	//處理上櫃資訊
	private BasicStockWrapper getBasicGretaiWrapper(String[] bufferData)
	{		
		BasicStockWrapper wrapper = new BasicStockWrapper();
		wrapper.setStockID(bufferData[0].trim().replace("\"",""));
		wrapper.setStockName(bufferData[1].trim().replace("\"",""));
		//1.上市；2上櫃
		wrapper.setStockBranch(2);
		String stockName = bufferData[3].trim().replace("\"","");
		int stockType = stockMap.get(stockName);
		wrapper.setStockType(stockType);
		wrapper.setEnabled(1);
		wrapper.setCapital(0);
		return wrapper;
	}
}
