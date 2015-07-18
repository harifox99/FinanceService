package org.bear.financeAnalysis;
import java.util.*;

import org.bear.dao.BasicStockDao;
import org.bear.dao.JuristicDailyReportDao;
import org.bear.entity.BasicStockWrapper;
import org.bear.entity.JuristicDailyEntity;
import org.bear.entity.JuristicDailyReport;
import org.bear.entity.ThreeBigExchangeEntity;
import org.bear.entity.ThreeBigExchangeReport;
import org.bear.util.StringUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
/**
 * 三大法人交易日誌資料分析
 * @author edward
 *
 */
public class JuristicAnalysis 
{
	ApplicationContext context = new ClassPathXmlApplicationContext("config.xml");
	JuristicDailyReportDao juristicDailyReportDao = (JuristicDailyReportDao)context.getBean("juristicDailyReportDao");
	BasicStockDao basicStockDao = (BasicStockDao)context.getBean("basicStockDao");
	List<JuristicDailyEntity> list;
	List<JuristicDailyReport> reportList;
	List<ThreeBigExchangeEntity> threeBigList;
	public List<JuristicDailyReport> getJuristicReport(int size)
	{
		list = juristicDailyReportDao.findLatestData(size);
		reportList = new ArrayList<JuristicDailyReport>();
		try
		{
			for (int i = 0; i < list.size(); i++)
			{			
				JuristicDailyReport report = new JuristicDailyReport();
				JuristicDailyEntity entity = list.get(i);
				report.setAmount(entity.getAmount());
				report.setExchangeDate(entity.getExchangeDate());
				report.setNewLot(entity.getNewLot());
				report.setNewSmallLot(entity.getNewSmallLot());
				report.setTopTenLotBuyMonth(entity.getTopTenLotBuyMonth());
				report.setTopTenLotBuyTotal(entity.getTopTenLotBuyTotal());
				report.setTopTenLotSellMonth(entity.getTopTenLotSellMonth());
				report.setTopTenLotSellTotal(entity.getTopTenLotSellTotal());
				report.setTotalLot(entity.getTotalLot());
				report.setTotalSmallLot(entity.getTotalSmallLot());			
				report.setForeignerComment(this.foreignerComment(i));
				report.setTopTenMonthDiff(this.topTenMonthDiff(entity));
				report.setTopTenNextDiff(this.topTenNextDiff(entity));
				reportList.add(report);
			}			
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		
		return reportList;
	}
	private String foreignerComment(int index)
	{
		final int bigAmount = 1000000000;
		String message = "";	
		JuristicDailyEntity entity = list.get(index);
		long amount = entity.getAmount();
		int totalLot = entity.getTotalLot() + entity.getTotalSmallLot()/4;
		int newLot = entity.getNewLot() + entity.getNewSmallLot()/4;
		//買超金額在數十億以上&&多單超過萬口
		if (amount > bigAmount && totalLot > 10000)
			message = "確定作多";
		//買超金額低於數億或小幅賣超&&多單超過萬口
		else if(amount > -500000000 && amount < bigAmount && totalLot > 10000)
			message = "大盤隔日走多機率非常高";
		//同步大賣超
		else if(amount < -bigAmount && totalLot < -10000)
			message = "確定作空";
		//賣超低於數億元或小幅買超&&空單超過萬口
		else if (amount > -bigAmount && amount < 500000000 && totalLot < -10000)
			message = "留意開始做空";
		//未平倉多空單，數百至數千口，買賣超金額不大
		else if (amount > -500000000 && amount < 500000000 && totalLot > -5000 && totalLot < 5000)
			message = "中性，沒有方向";
		//每天新增1000-2000口以上未平倉多單，連續3天以上
		else if (this.pastNewLot(newLot, index) == 1 && amount > bigAmount)
			message = "偏多機率高";
		//每天新增1000-2000口以上未平倉空單，連續3天以上
		else if (this.pastNewLot(newLot, index) == -1 && amount < -bigAmount)
			message = "偏空機率高";
		//突然大增5000口以上多單，或大增5倍以上多單
		else if (newLot > 5000 || this.compareRatio(totalLot, index))
			message = "大盤隔日走多機率非常高";
		//由正轉負至少5000口，或大增5倍以上空單
		else if (totalLot < -5000 || this.compareRatio(totalLot, index))
			message = "大盤隔日走空機率非常高";
		else
			message = "無法判斷";
		return message;
	}
	/**
	 * 傳回1，表示連3天新增多單超過1500口
	 * 傳回0，表示無法判斷
	 * 傳回-1，表示連3天新增空單超過1500口
	 * @param quantity
	 * @param index
	 * @return
	 */
	private int pastNewLot(int totalLot, int index)
	{
		try
		{
			JuristicDailyEntity yesterdayEntity = list.get(index+1);
			int yesterdayNewLot = yesterdayEntity.getNewLot() + yesterdayEntity.getNewSmallLot()/4;
			JuristicDailyEntity beforeYesterdayEntity = list.get(index+2);
			int beforeYesterdayNewLot = beforeYesterdayEntity.getNewLot() + beforeYesterdayEntity.getNewSmallLot()/4;
			if (totalLot > 1500 && yesterdayNewLot > 1500 && beforeYesterdayNewLot > 1500)
				return 1;
			if (totalLot < -1500 && yesterdayNewLot < -1500 && beforeYesterdayNewLot < -1500)
				return -1;
			else 
				return 0;
		}
		catch (Exception ex)
		{
			return 0;
		}
	}
	/**
	 * 多/空單餘額大增5倍
	 * @param totalLot
	 * @param index
	 * @return
	 */
	private boolean compareRatio(int totalLot, int index)
	{
		try
		{
			JuristicDailyEntity yesterdayEntity = list.get(index+1);
			int yesterdayTotalLot = yesterdayEntity.getTotalLot() + yesterdayEntity.getTotalSmallLot()/4;
			if (totalLot/yesterdayTotalLot > 5)
				return true;
			else
				return false;
		}
		catch (Exception ex)
		{
			return false;
		}
		
	}
	/**
	 * 當月十大交易人差額
	 * @param entity
	 * @return
	 */
	private int topTenMonthDiff(JuristicDailyEntity entity)
	{
		int result = entity.getTopTenLotBuyMonth() - entity.getTopTenLotSellMonth();
		return result;
	}
	/**
	 * 次月十大交易人差額
	 * @param entity
	 * @return
	 */
	private int topTenNextDiff(JuristicDailyEntity entity)
	{
		int thisMonth = entity.getTopTenLotBuyMonth() - entity.getTopTenLotSellMonth();
		int total = entity.getTopTenLotBuyTotal() - entity.getTopTenLotSellTotal();
		return total - thisMonth;
	}
	/**
	 * 個股法人籌碼分析
	 * @param stockID
	 * @param size
	 * @return
	 */
	public List<ThreeBigExchangeReport> getThreeBigStock(String stockID, int size)
	{
		threeBigList = juristicDailyReportDao.findSingleStock(stockID ,size);
		List<ThreeBigExchangeReport> reportList = new ArrayList<ThreeBigExchangeReport>();
		for (int i = 0; i < threeBigList.size(); i++)
		{
			ThreeBigExchangeReport report = new ThreeBigExchangeReport();
			report.setExchangeDate(threeBigList.get(i).getExchangeDate());
			report.setExchanger(threeBigList.get(i).getExchanger());
			report.setQuantity(threeBigList.get(i).getQuantity());
			report.setRank(threeBigList.get(i).getRank());
			report.setStockBranch(threeBigList.get(i).getStockBranch());
			report.setStockID(threeBigList.get(i).getStockID());
			BasicStockWrapper basicStock = basicStockDao.findBasicData(stockID);
			report.setStockName(basicStock.getStockName());
			report.setCompanySize(this.getCapitalSize(basicStock.getCapital()));
			report.setCapital(StringUtil.setPointLength(basicStock.getCapital(), 2));
			reportList.add(report);
		}
		return reportList;
	}
	private String getCapitalSize(double capital)
	{
		if (capital > 100)
			return "大型股";
		else if (capital < 100 && capital > 40)
			return "中型股";
		else
			return "小型股";
	}
}
