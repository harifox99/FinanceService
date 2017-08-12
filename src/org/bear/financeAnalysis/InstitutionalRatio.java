package org.bear.financeAnalysis;

import java.util.*;

import org.bear.dao.BasicStockDao;
import org.bear.dao.JuristicDailyReportDao;
import org.bear.entity.BasicStockWrapper;
import org.bear.entity.InstitutionalEntity;
import org.bear.entity.ThreeBigExchangeEntity;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class InstitutionalRatio 
{
	int week = 10;
	int month = 30;
	ApplicationContext context = new ClassPathXmlApplicationContext("config.xml");
	JuristicDailyReportDao juristicDailyReportDao = (JuristicDailyReportDao)context.getBean("juristicDailyReportDao");
	BasicStockDao basicStockDao = (BasicStockDao)context.getBean("basicStockDao");
	List<InstitutionalEntity> listInstitutionalEntity = new ArrayList<InstitutionalEntity>();
	public void getOrder()
	{
		List<BasicStockWrapper> listStock = basicStockDao.findAllData();
		Map<String, List<Double>> mapStockRatio = new TreeMap<String, List<Double>>();	
		List<ThreeBigExchangeEntity> listInstitutional;
		for (int i = 0; i < listStock.size(); i++)
		{
			//得到該股票的法人交易資料
			listInstitutional = juristicDailyReportDao.findStockBySize(listStock.get(i).getStockID(), week, "外資");
			//
			for (int j = 0; j < listInstitutional.size(); j++)
			{
				this.computeCapitalRatio(listStock.get(i).getStockID(), listInstitutional, listStock.get(i).getCapital());
			}
			
		}
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
    	ratio.getOrder();
    }
    /**
     * 計算不同天數的投信買賣超佔股本比或是外資買賣超佔股本比
     * @param stockID
     * @param listInstitutional
     * @param capital
     */
    private void computeCapitalRatio(String stockID, List<ThreeBigExchangeEntity> listInstitutional, double capital)
    {
    	//成交量
    	double totalQuantity = 0;
    	List<String> ratioList = new ArrayList<String>(); 
    	for (int i = 0; i < listInstitutional.size(); i++)
    	{
    		totalQuantity = totalQuantity + listInstitutional.get(i).getQuantity();
    		double ratio = (double)totalQuantity/capital;
    	}
    }
}
