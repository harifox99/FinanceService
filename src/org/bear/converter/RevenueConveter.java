package org.bear.converter;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import org.bear.entity.RevenueEntity;
import org.bear.entity.RevenueIncreaseWrapper;
/**
 * @author edward
 * 根據資料庫資料，計算累計營收，YoY and MoM
 */
public class RevenueConveter 
{
	public RevenueIncreaseWrapper converter(RevenueEntity entity, HashMap <String, Integer>mapLastMonthRevenue)
	{
		NumberFormat formatter = new DecimalFormat(".##");
		RevenueIncreaseWrapper wrapper = new RevenueIncreaseWrapper();
		int intLastMonthRevenue = 0;
		//YoY Revenue
		double yoyRevenue = (double)entity.getRevenue()/entity.getLastRevenue();
		yoyRevenue -= 1;
		yoyRevenue *= 100;
		String strRevenue = formatter.format(yoyRevenue);
		wrapper.setYoyRevenue(Double.parseDouble(strRevenue));		
		//Accumulation;
		double accumulation = (double)entity.getAccumulation()/entity.getLastAccumulation();
		accumulation -= 1;
		accumulation *= 100;
		String strAccumulation = formatter.format(accumulation);
		wrapper.setAccumulation(Double.parseDouble(strAccumulation));
		//MoM Revenue
		String stockID = entity.getStockID();
		if (mapLastMonthRevenue.get(stockID) == null)
		{
			mapLastMonthRevenue.put(stockID, entity.getRevenue());
			return null;
		}
		else
		{
			intLastMonthRevenue = mapLastMonthRevenue.get(stockID); 
			double momRevenue = (double)entity.getRevenue()/intLastMonthRevenue;
			momRevenue -= 1;
			momRevenue *= 100;
			String strMomREvenue = formatter.format(momRevenue);
			wrapper.setMomRevenue(Double.parseDouble(strMomREvenue));
			//End MoM
			wrapper.setOpenIndex(entity.getOpenIndex());
			wrapper.setHighIndex(entity.getHighIndex());
			wrapper.setLowIndex(entity.getLowIndex());
			wrapper.setCloseIndex(entity.getCloseIndex());
			wrapper.setTurnoverRatio(Double.parseDouble(entity.getTurnoverRatio()));
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM"); 
			wrapper.setYearMonth(dateFormat.format(entity.getYearMonth()));
			mapLastMonthRevenue.put(stockID, entity.getRevenue());
			return wrapper;
		}
	}
}
