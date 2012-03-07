package org.bear.main;

import java.util.List;

import org.bear.dao.BasicStockDao;
import org.bear.datainput.ImportBasicStock;
import org.bear.entity.BasicStockWrapper;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BuildBasicStock {

	/**
	 * @param args
	 */
	List <BasicStockWrapper> list;
	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
		new BuildBasicStock().insertBatch();
	}
	public void insertBatch()
	{
		ApplicationContext context = new ClassPathXmlApplicationContext("config.xml");
		BasicStockDao dao = (BasicStockDao)context.getBean("basicStockDao");
		list = new ImportBasicStock().getBasicStockList();
		dao.insertBatch(list);
	}
}
