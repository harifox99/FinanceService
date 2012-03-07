package org.bear.main;

import java.util.Date;
import java.util.List;
import org.bear.dao.AmericanMacroPantherDao;
import org.bear.datainput.ImportAmericanMacroPanther;
import org.bear.entity.AmericanMacroPantherEntity;
import org.bear.util.ParseFile;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BuildAmericanMacroData extends ParseFile
{
	/**
	 * @param args
	 */
	List <AmericanMacroPantherEntity> list;
	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
		new BuildAmericanMacroData().insertBatch();
		//new BuildMacroEconomicData().findAllList();
	}
	public void insertBatch()
	{
		ApplicationContext context = new ClassPathXmlApplicationContext("config.xml");
		AmericanMacroPantherDao dao = (AmericanMacroPantherDao)context.getBean("americanMacroPantherDao");
		list = new ImportAmericanMacroPanther().getMacroEconomicList();
		dao.insertBatch(list);
	}
	public List<AmericanMacroPantherEntity> findAllList()
	{
		ApplicationContext context = new ClassPathXmlApplicationContext("config.xml");
		AmericanMacroPantherDao dao = (AmericanMacroPantherDao)context.getBean("americanMacroPantherDao");	
		list = dao.findAll();		
		return list;
	}
	public List<AmericanMacroPantherEntity> findListByDate(Date startTime, Date endTime)
	{
		ApplicationContext context = new ClassPathXmlApplicationContext("config.xml");
		AmericanMacroPantherDao dao = (AmericanMacroPantherDao)context.getBean("americanMacroPantherDao");	
		list = dao.findByDate(startTime, endTime);		
		return list;
	}

}
