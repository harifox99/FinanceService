package org.bear.main;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Date;
import java.util.List;
import org.bear.dao.RevenueDao;
import org.bear.datainput.ImportRevenue;
import org.bear.datainput.ImportTwseRevenue;
import org.bear.entity.LongTermRevenueWrapper;
import org.bear.entity.RevenueEntity;
import org.bear.entity.RevenueIncreaseWrapper;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BuildRevenueData {

	/**
	 * @param args
	 */
	RevenueDao dao;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//new BuildRevenueData().insertBatchList();
		new BuildRevenueData().insertNewRevenue();
	}
	public BuildRevenueData()
	{
		ApplicationContext context = new ClassPathXmlApplicationContext("config.xml");
		dao = (RevenueDao)context.getBean("revenueDao");
	}
	public void insertBatchList()
	{
		List <RevenueEntity> list;
		File file = new File("data/revenueTwse");
		String revenueList[] = file.list();
		file = new File("data/index");
		String indexList[] = file.list();
		ImportTwseRevenue importRevenue = new ImportTwseRevenue();
		for (int i = 0; i < revenueList.length; i++)
		{
			System.out.println("Main index: " + i);
			list = importRevenue.getRevenueEntity("data/index/" + indexList[i], "data/revenueTwse/" + revenueList[i]);
			//第一筆不存，因為只使用上個月收盤價當本月開盤價
			if (i == 0)
				continue;
			dao.insertBatch(list);
		}
	}
	@SuppressWarnings("deprecation")
	public void insertNewRevenue()
	{
		List <RevenueEntity> list;
		File file = new File("data/revenueMonth");
		FilenameFilter filter = new FilenameFilter() 
		{ 
            public boolean accept(File file, String name) 
            {
            	return (name.indexOf(".csv") != -1);
            }
		};
		String revenueList[] = file.list(filter);
		/*************************/
		file = new File("data/indexMonth");
		/*
		filter = new FilenameFilter() 
		{ 
            public boolean accept(File file, String name) 
            {
            	return (name.indexOf("*.csv") != -1);
            }
		};*/
		String indexList[] = file.list(filter);
		/*
		for (int i = 0; i < indexList.length; i++) 
		{
		    System.out.println(indexList[i]);
	    }*/
		ImportRevenue importRevenue = new ImportRevenue();
		for (int i = 0; i < revenueList.length; i++)
		{
			System.out.println("Main index: " + i);
			list = importRevenue.getRevenueEntity("data/indexMonth/" + indexList[i], "data/revenueMonth/" + revenueList[i]);
						
			if (i == 0)
				continue;
			dao.insertBatch(list);
		}
	}
	public List<RevenueIncreaseWrapper> findListByDate(String stockID, Date startTime, Date endTime)
	{
		List <RevenueIncreaseWrapper> list;
		list = dao.findAllRevenueIncrease(stockID, startTime, endTime);		
		return list;
	}
	public List<LongTermRevenueWrapper> findLongTermByDate(String stockID, Date startTime, Date endTime)
	{
		List <LongTermRevenueWrapper> list;
		list = dao.findLongTermByDate(stockID, startTime, endTime);
		for (int i = 0; i < 12; i++)
			list.remove(0);
		return list;
	}
}
