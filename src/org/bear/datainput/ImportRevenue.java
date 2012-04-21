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
	HashMap <String, String> mapLastCloseIndex;
	public ImportRevenue()
	{
		mapLastCloseIndex = new HashMap <String, String>();
	}	
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
}
