package org.bear.main;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Date;
import java.util.List;
import org.bear.dao.RevenueDao;
import org.bear.datainput.ImportRevenue;
import org.bear.entity.LongTermRevenueWrapper;
import org.bear.entity.RevenueIncreaseWrapper;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BuildRevenueYam {

	/**
	 * @param args
	 */
	RevenueDao dao;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new BuildRevenueYam().insertNewRevenue();
	}
	public BuildRevenueYam()
	{
		ApplicationContext context = new ClassPathXmlApplicationContext("config.xml");
		dao = (RevenueDao)context.getBean("revenueDao");
	}

	public void insertNewRevenue()
	{
		//上市月股價收盤資料
		File file = new File("data/indexMonth");
		FilenameFilter filter = new FilenameFilter() 
		{ 
            public boolean accept(File file, String name) 
            {
            	return (name.indexOf(".csv") != -1);
            }
		};
		String[] indexList = file.list(filter);
		//上櫃月股價收盤資料
		file = new File("data/gretaiMonth");
		filter = new FilenameFilter() 
		{ 
            public boolean accept(File file, String name) 
            {
            	return (name.indexOf(".csv") != -1);
            }
		};
		String[] gretaiList = file.list(filter);
		/*************************/
		ImportRevenue importRevenue = new ImportRevenue();
		importRevenue.getRevenueEntity("data/indexMonth/", indexList, "data/gretaiMonth/", gretaiList);
	
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

