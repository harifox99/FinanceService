package org.bear.main;

import java.util.Date;
import java.util.List;
import org.bear.dao.PMIndexDao;
import org.bear.datainput.ImportPMIndex;
import org.bear.entity.PMIndexEntity;
import org.bear.util.ParseFile;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BuildPMIndex extends ParseFile {

	/**
	 * @param args
	 */
	List <PMIndexEntity> list;
	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
		new BuildPMIndex().insertBatch();
		//new BuildMacroEconomicData().findAllList();
	}
	public void insertBatch()
	{
		ApplicationContext context = new ClassPathXmlApplicationContext("config.xml");
		PMIndexDao dao = (PMIndexDao)context.getBean("pmIndexDao");
		list = new ImportPMIndex().getPMIndexList();
		dao.insertBatch(list);
	}
	public List<PMIndexEntity> findAllList()
	{
		ApplicationContext context = new ClassPathXmlApplicationContext("config.xml");
		PMIndexDao dao = (PMIndexDao)context.getBean("pmIndexDao");	
		list = dao.findAll();		
		return list;
	}
	public List<PMIndexEntity> findListByDate(Date startTime, Date endTime)
	{
		ApplicationContext context = new ClassPathXmlApplicationContext("config.xml");
		PMIndexDao dao = (PMIndexDao)context.getBean("pmIndexDao");	
		list = dao.findByDate(startTime, endTime);		
		return list;
	}

}
