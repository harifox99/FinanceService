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
 * ミ基籔–る犁Μ戈
 */
public class ImportTwseRevenue extends ParseFile
{
	HashMap <String, String> mapLastCloseIndex;
	public ImportTwseRevenue()
	{
		mapLastCloseIndex = new HashMap <String, String>();
	}	
	public ArrayList <RevenueEntity> getRevenueEntity(String indexFile, String revenueFile)
	{
		//秨絃计
		HashMap <String, String> mapOpenIndex = new HashMap <String, String>();
		//程蔼计
		HashMap <String, String> mapHighIndex = new HashMap <String, String>();
		//程计
		HashMap <String, String> mapLowIndex = new HashMap <String, String>();
		//Μ絃计
		HashMap <String, String> mapCloseIndex = new HashMap <String, String>();
		//キА计
		HashMap <String, String> mapAverageIndex = new HashMap <String, String>();
		//秅锣瞯
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
					//System.out.println("Index: " + stockID);
					/*
					System.out.println("Row Index: " + this.intRowIndex++);
					if (intRowIndex > 695)
					{
						System.out.println("");
					}*/
					mapOpenIndex.put(stockID, mapLastCloseIndex.get(stockID));
					mapHighIndex.put(stockID, bufferData[7].trim());
					mapLowIndex.put(stockID, bufferData[9].trim());
					mapCloseIndex.put(stockID, bufferData[6].trim());
					mapTurnoverRatio.put(stockID, bufferData[11].trim());
					mapAverageIndex.put(stockID, bufferData[4].trim());
					mapLastCloseIndex.put(stockID, bufferData[6].trim());
				}
			}
			reader = new BufferedReader(new FileReader(revenueFile));
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
			String strDate = revenueFile.substring(17, 21) + "-" + revenueFile.substring(21, 23);
			Date date = dateFormat.parse(strDate);
			while((readData = reader.readLine()) != null)
			{		
				bufferData = readData.split(",");
				String stockID = bufferData[0].trim();
				if (new RegEx("^[\\d]{4}", stockID).isMatch() == true)
				{
					stockID = stockID.substring(0, 4);
					if (mapOpenIndex.get(stockID) != null)
						list.add(getRevenueEntity(mapOpenIndex, mapHighIndex, mapLowIndex, mapCloseIndex, bufferData, date, mapTurnoverRatio, stockID, mapAverageIndex));
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
			HashMap <String, String> mapTurnoverRatio, String stockID, HashMap <String, String> mapAverageIndex)
	{
		RevenueEntity entity = new RevenueEntity();
		//System.out.println("Revenue: " + bufferData[0]);
		entity.setAccumulation(Integer.parseInt(bufferData[3].trim()));
		entity.setCloseIndex(mapCloseIndex.get(stockID));
		entity.setHighIndex(mapHighIndex.get(stockID));
		entity.setLastAccumulation(Integer.parseInt(bufferData[5].trim()));
		entity.setLastRevenue(Integer.parseInt(bufferData[4].trim()));
		entity.setLowIndex(mapLowIndex.get(stockID));
		entity.setOpenIndex(mapOpenIndex.get(stockID));
		entity.setRevenue(Integer.parseInt(bufferData[2].trim()));
		entity.setAverageIndex(mapAverageIndex.get(stockID));
		entity.setYearMonth(yearMonth);
		entity.setStockID(stockID);
		entity.setTurnoverRatio(mapTurnoverRatio.get(stockID));
		//entity.setRevenueMoM(bufferData[4].trim());
		return entity;
	}
}

