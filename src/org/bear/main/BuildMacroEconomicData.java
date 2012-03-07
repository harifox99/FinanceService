package org.bear.main;
import java.util.Date;
import java.util.List;

import org.bear.dao.*;
import org.bear.datainput.ImportMacroEconomic;
import org.bear.entity.MacroEconomicEntity;
import org.bear.util.ParseFile;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BuildMacroEconomicData extends ParseFile
{
	/**
	 * @param args
	 */
	List <MacroEconomicEntity> list;
	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
		new BuildMacroEconomicData().insertBatch();
		//new BuildMacroEconomicData().findAllList();
	}
	public void insertBatch()
	{
		ApplicationContext context = new ClassPathXmlApplicationContext("config.xml");
		MacroEconomicDao dao = (MacroEconomicDao)context.getBean("macroEconomicDao");
		list = new ImportMacroEconomic().getMacroEconomicList();
		dao.insertBatch(list);
	}
	public List<MacroEconomicEntity> findAllList()
	{
		ApplicationContext context = new ClassPathXmlApplicationContext("config.xml");
		MacroEconomicDao dao = (MacroEconomicDao)context.getBean("macroEconomicDao");	
		list = dao.findAll();		
		return list;
	}
	public List<MacroEconomicEntity> findListByDate(Date startTime, Date endTime)
	{
		ApplicationContext context = new ClassPathXmlApplicationContext("config.xml");
		MacroEconomicDao dao = (MacroEconomicDao)context.getBean("macroEconomicDao");	
		list = dao.findByDate(startTime, endTime);		
		return list;
	}
}
