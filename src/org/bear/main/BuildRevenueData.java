package org.bear.main;

import java.io.File;
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
			if (i == 0)
				continue;
			dao.insertBatch(list);
		}
	}
	public void insertNewRevenue()
	{
		List <RevenueEntity> list;
		File file = new File("data/revenueMonth");
		String revenueList[] = file.list();
		file = new File("data/indexMonth");
		String indexList[] = file.list();
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
