package org.bear.datainput;

import java.io.BufferedReader;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import org.bear.entity.RevenueEntity;
import org.bear.util.*;
/**
 * @author edward
 * 建立個股股價與每月營收資料（用來建立當月最新營收資料）
 */
public class ImportRevenue extends ParseFile
{
	/**
	 * 舊的營收擷取程式
	 * @param indexFile
	 * @param revenueFile
	 * @return
	 */
	@Deprecated
	public ArrayList <RevenueEntity> getRevenueEntity(String indexFile, String revenueFile)
	{
		//開盤指數
		HashMap <String, String> mapOpenIndex = new HashMap <String, String>();
		//最高指數
		HashMap <String, String> mapHighIndex = new HashMap <String, String>();
		//最低指數
		HashMap <String, String> mapLowIndex = new HashMap <String, String>();
		//收盤指數
		HashMap <String, String> mapCloseIndex = new HashMap <String, String>();
		//週轉率
		HashMap <String, String> mapTurnoverRatio = new HashMap <String, String>();	
		//因為證交所沒有當月開盤價，故使用上個月收盤價當本月開盤價
		HashMap <String, String> mapLastCloseIndex = new HashMap <String, String>();
		ArrayList <RevenueEntity> list = new ArrayList <RevenueEntity>();
		intRowIndex = 0;
		try
		{				
			int fileNum = 0;
			reader = new BufferedReader(new FileReader(indexFile));		
			while((readData = reader.readLine()) != null && fileNum++ < 1000)
			{							
				bufferData = readData.split(",");	
				String stockID = bufferData[0].trim();				
				if (new RegEx("^[1-9][0-9]{3,3} ", stockID).isMatch() == true && bufferData.length == 12)
				{
					stockID = stockID.substring(0, 4);
					mapOpenIndex.put(stockID, mapLastCloseIndex.get(stockID));
					mapHighIndex.put(stockID, bufferData[7].trim());
					mapLowIndex.put(stockID, bufferData[9].trim());
					mapCloseIndex.put(stockID, bufferData[6].trim());
					mapTurnoverRatio.put(stockID, bufferData[11].trim());
					mapLastCloseIndex.put(stockID, bufferData[6].trim());
				}
			}
			reader = new BufferedReader(new FileReader(revenueFile));
			//把檔案名稱當作資料庫日期欄位
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
			String strDate = revenueFile.substring(18, 22) + "-" + revenueFile.substring(22, 24);
			Date date = dateFormat.parse(strDate);
			while((readData = reader.readLine()) != null)
			{		
				bufferData = readData.split(",");
				String stockID = bufferData[0].trim();
				if (new RegEx("^[\\d]{4}", stockID).isMatch() == true)
				{
					if (mapOpenIndex.get(stockID) != null)
						list.add(getRevenueEntity(mapOpenIndex, mapHighIndex, mapLowIndex, mapCloseIndex, bufferData, date, mapTurnoverRatio));
				}
			}			
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		return list;
	}
	/**
	 * 新的營收擷取程式，股價資料從證交所，營收資料從網站
	 * @param filePath
	 * @param fileList
	 */
	public void getRevenueEntity(String filePath, String[] fileList, String gretaiPath, String[] gretaiList)
	{
		//開盤指數
		HashMap <String, String> mapOpenIndex = new HashMap <String, String>();
		//最高指數
		HashMap <String, String> mapHighIndex = new HashMap <String, String>();
		//最低指數
		HashMap <String, String> mapLowIndex = new HashMap <String, String>();
		//收盤指數
		HashMap <String, String> mapCloseIndex = new HashMap <String, String>();
		//週轉率
		HashMap <String, String> mapTurnoverRatio = new HashMap <String, String>();	
		//因為證交所沒有當月開盤價，故使用上個月收盤價當本月開盤價
		HashMap <String, String> mapLastCloseIndex = new HashMap <String, String>();
		//ArrayList <RevenueEntity> list = new ArrayList <RevenueEntity>();
		intRowIndex = 0;
		try
		{		
			for (int i = 0; i < fileList.length; i++)
			{
				//System.out.println("Main index: " + i);
				int fileNum = 0;
				reader = new BufferedReader(new FileReader(filePath + fileList[i]));		
				//從檔案擷取股價與週轉率，我是上市部分
				while((readData = reader.readLine()) != null && fileNum++ < 1000)
				{												
					bufferData = readData.split(",");	
					if (bufferData.length == 0)
						continue;
					String stockID = bufferData[0].trim();
					String year = fileList[i].substring(0,4);
					String month = fileList[i].substring(4,6);
					if (new RegEx("^[1-9][0-9]{3,3} ", stockID).isMatch() == true && bufferData.length == 12)
					{
						stockID = stockID.substring(0, 4);
						//if (stockID.equals("1582"))
							//System.out.println("stockID: " + stockID);
						String key = year + "-" + month + "-" + stockID;
						mapOpenIndex.put(key, mapLastCloseIndex.get(key));
						mapHighIndex.put(key, bufferData[7].trim());
						mapLowIndex.put(key, bufferData[9].trim());
						mapCloseIndex.put(key, bufferData[6].trim());
						mapTurnoverRatio.put(key, bufferData[11].trim());
						mapLastCloseIndex.put(this.computeOpenIndex(year, month) + "-" + stockID, bufferData[6].trim());
					}
				}
			}
			
			for (int i = 0; i < gretaiList.length; i++)
			{		
				//從檔案擷取股價與週轉率，我是上櫃部分
				int fileNum = 0;
				reader = new BufferedReader(new FileReader(gretaiPath + gretaiList[i]));	
				while((readData = reader.readLine()) != null && fileNum++ < 1000)
				{												
					bufferData = readData.split(",");	
					if (bufferData.length == 0)
						continue;
					String stockID = bufferData[0].trim();
					String year = gretaiList[i].substring(0,4);
					String month = gretaiList[i].substring(4,6);
					if (new RegEx("^[1-9][0-9]{3,3}", stockID).isMatch() == true && bufferData.length == 14)
					{
						stockID = stockID.substring(0, 4);
						//if (stockID.equals("1582"))
							//System.out.println("stockID: " + stockID);
						String key = year + "-" + month + "-" + stockID;
						mapOpenIndex.put(key, mapLastCloseIndex.get(key));
						mapHighIndex.put(key, this.convertNonNumeric(bufferData[6].trim()));
						mapLowIndex.put(key, this.convertNonNumeric(bufferData[7].trim()));
						mapCloseIndex.put(key, this.convertNonNumeric(bufferData[9].trim()));
						mapTurnoverRatio.put(key, this.convertNonNumeric(bufferData[12].trim()));
						mapLastCloseIndex.put(this.computeOpenIndex(year, month) + "-" + stockID, this.convertNonNumeric(bufferData[9].trim()));
					}
				}
			}
			/*
			String value = mapOpenIndex.get("2012-09-2412");
			System.out.println(value);
		    value = mapHighIndex.get("2012-09-2412");
			System.out.println(value);
			value = mapLowIndex.get("2012-09-2412");
			System.out.println(value);
			value = mapCloseIndex.get("2012-09-2412");
			System.out.println(value);*/

			//從蕃薯藤網站擷取營業收入			
			ImportRevenueYam importRevenueYam = new ImportRevenueYam();
			importRevenueYam.setMapCloseIndex(mapCloseIndex);
			importRevenueYam.setMapHighIndex(mapHighIndex);
			importRevenueYam.setMapLowIndex(mapLowIndex);
			importRevenueYam.setMapOpenIndex(mapOpenIndex);
			importRevenueYam.setMapTurnoverRatio(mapTurnoverRatio);
			importRevenueYam.insertBatchList();	
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}
	private RevenueEntity getRevenueEntity(HashMap <String, String> mapOpenIndex, HashMap <String, String> mapHighIndex,
			HashMap <String, String> mapLowIndex, HashMap <String, String> mapCloseIndex, String[] bufferData, Date yearMonth,
			HashMap <String, String> mapTurnoverRatio)
	{
		RevenueEntity entity = new RevenueEntity();
		String stockID = bufferData[0].trim();
		entity.setAccumulation(Long.parseLong(bufferData[7].trim()));
		entity.setCloseIndex(mapCloseIndex.get(stockID));
		entity.setHighIndex(mapHighIndex.get(stockID));
		entity.setLastAccumulation(Long.parseLong(bufferData[8].trim()));
		entity.setLastRevenue(Integer.parseInt(bufferData[5].trim()));
		entity.setLowIndex(mapLowIndex.get(stockID));
		entity.setOpenIndex(mapOpenIndex.get(stockID));
		entity.setRevenue(Integer.parseInt(bufferData[2].trim()));		
		entity.setYearMonth(yearMonth);
		entity.setStockID(stockID);
		entity.setTurnoverRatio(mapTurnoverRatio.get(stockID));
		//entity.setRevenueMoM(bufferData[4].trim());
		return entity;
	}	
	/**
	 * 將這個月的收盤價當作下個月的開盤價
	 * @param year
	 * @param month
	 * @return
	 */
	private String computeOpenIndex(String year, String month)
	{
		int intMonth = Integer.parseInt(month);
		String key;
		int intYear = Integer.parseInt(year);
		if (intMonth == 12)
		{
			intYear++;
			intMonth = 1;
		}
		else
		{
			intMonth++;
		}
		if (intMonth > 9)
			key = String.valueOf(intYear) + "-" + String.valueOf(intMonth);
		else
			key = String.valueOf(intYear) + "-0" + String.valueOf(intMonth);
		return key;
	}
	private String convertNonNumeric(String number)
	{
		try
		{
			Double.parseDouble(number);
			return number;
		}
		catch (NumberFormatException ex)
		{
			return null;
		}
	}
}
