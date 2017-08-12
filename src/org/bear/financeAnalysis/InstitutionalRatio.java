package org.bear.financeAnalysis;

import java.util.*;

import org.bear.dao.BasicStockDao;
import org.bear.dao.JuristicDailyReportDao;
import org.bear.entity.BasicStockWrapper;
import org.bear.entity.InstitutionalEntity;
import org.bear.entity.ThreeBigExchangeEntity;
import org.bear.util.StringUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class InstitutionalRatio 
{
	int month = 30;
	ApplicationContext context = new ClassPathXmlApplicationContext("config.xml");
	JuristicDailyReportDao juristicDailyReportDao = (JuristicDailyReportDao)context.getBean("juristicDailyReportDao");
	BasicStockDao basicStockDao = (BasicStockDao)context.getBean("basicStockDao");
	List<InstitutionalEntity> listInstitutionalEntity = new ArrayList<InstitutionalEntity>();
	Map<String, Double> mapForeigner;	 
	Map<String, Double> mapInvestment;
	/**
	 * 查詢過去
	 * @param days 查詢幾天
	 * @param accumulation 以累積幾日來排名
	 */
	public List<Map<String, List<Double>>> getOrder(int days, int accumulation)
	{
		List<BasicStockWrapper> listStock = basicStockDao.findAllData();
		//外資
		Map<String, List<Double>> foreignerStockRatio = new HashMap<String, List<Double>>();
		List<ThreeBigExchangeEntity> listForeigner;
		//投信
		Map<String, List<Double>> investmentStockRatio = new HashMap<String, List<Double>>();	
		List<ThreeBigExchangeEntity> listInvestment;
		//外資+投信
		Map<String, List<Double>> InstitutionalStockRatio = new HashMap<String, List<Double>>();
		for (int i = 0; i < listStock.size(); i++)
		{
			//得到該股票的外資交易資料
			listForeigner = juristicDailyReportDao.findStockBySize(listStock.get(i).getStockID(), days, "外資");
			for (int j = 0; j < listForeigner.size(); j++)
			{
				List<Double> ratioList = this.computeCapitalRatio(listStock.get(i).getStockID(), listForeigner, listStock.get(i).getCapital(), mapForeigner, accumulation);
				foreignerStockRatio.put(listStock.get(i).getStockID(), ratioList);
			}
			//得到該股票的投信交易資料
			listInvestment = juristicDailyReportDao.findStockBySize(listStock.get(i).getStockID(), days, "投信");
			for (int j = 0; j < listInvestment.size(); j++)
			{
				List<Double> ratioList = this.computeCapitalRatio(listStock.get(i).getStockID(), listInvestment, listStock.get(i).getCapital(), mapInvestment, accumulation);
				foreignerStockRatio.put(listStock.get(i).getStockID(), ratioList);
			}
		}
		//外資排序
		Map<String, List<Double>> mapOrderForeigner = new HashMap<String, List<Double>>();
		Iterator<String> it;
		it = mapForeigner.keySet().iterator();
		while (it.hasNext()) 
	    {
	    	String stockID = it.next();
	    	List<Double> order = foreignerStockRatio.get(stockID);
	    	mapOrderForeigner.put(stockID, order);
	    }
		//投信排序
		Map<String, List<Double>> mapOrderInvestment = new HashMap<String, List<Double>>();
		it = mapInvestment.keySet().iterator();
		while (it.hasNext()) 
	    {
	    	String stockID = it.next();
	    	List<Double> order = investmentStockRatio.get(stockID);
	    	mapOrderInvestment.put(stockID, order);
	    }	
		return null;
	}
    public static void main(String[] args) 
    {
    	/*
	    Map<String, Integer> m = new TreeMap<String, Integer>();	 
	    // put the (k,v) pair into the map
	    // TreeMap will store them in order
	    m.put("dd", 3);
	    m.put("aa", 1);
	    m.put("ee", 4);
	    m.put("ae", 6);
	    m.put("ab", 4);
	 
	    Iterator<String> it = m.keySet().iterator();
	    while (it.hasNext()) 
	    {
	    	System.out.println(it.next());
	    }*/
    	InstitutionalRatio ratio = new InstitutionalRatio();
    	ratio.getOrder(7, 2);
    }
    /**
     * 計算不同天數的投信買賣超佔股本比或是外資買賣超佔股本比
     * @param stockID 股票代碼
     * @param listInstitutional 個股的法人交易資料
     * @param capital 股本
     * @param mapOrder 用TreeMap來自動排名
     * @param days 用累積日來排名
     * @return
     */
    private List<Double> computeCapitalRatio(String stockID, List<ThreeBigExchangeEntity> listInstitutional, 
    		double capital, Map<String, Double> mapOrder, int accumulation)
    {
    	//單位換算，分母要乘以100
    	final int converter = 100;
    	//成交量
    	double totalQuantity = 0;
    	List<Double> ratioList = new ArrayList<Double>();
    	mapOrder = new TreeMap<String, Double>();
    	for (int i = 0; i < listInstitutional.size(); i++)
    	{
    		totalQuantity = totalQuantity + listInstitutional.get(i).getQuantity();
    		double ratio = (double)totalQuantity/capital*converter;
    		ratioList.add(StringUtil.setPointLength(ratio));
    		if (i == accumulation)
    		{
    			mapOrder.put(stockID, ratio);
    		}
    	}
    	return ratioList;
    }
    
}
