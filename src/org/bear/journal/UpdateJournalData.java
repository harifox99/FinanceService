package org.bear.journal;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.bear.dao.JournalDao;
import org.bear.journal.wrapper.JournalEntity;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.DataAccessException;

public class UpdateJournalData 
{
	/**
	 * Àx¦s¤@µ§¤é»x
	 * @param entity
	 * @return
	 */
	public String insertJournalData(JournalEntity entity)
	{
		ApplicationContext context = new ClassPathXmlApplicationContext("config.xml");
		JournalDao journalDao = (JournalDao)context.getBean("journalDao");
		SimpleDateFormat format =  new SimpleDateFormat("yyyyMMddhhmmss");        
		java.util.Date today = new Date();        
		entity.setTransactionID(String.valueOf(format.format(today)));
		try
		{			
			journalDao.insert(entity);
			return "Successful!";
		}
		catch (DataAccessException ex)
		{
			ex.printStackTrace();
			return "Insert fail!";
		}
	}
	public JournalEntity findJournal(String transactionID, int serialNo) 
	{
		JournalEntity entity = null;
		ApplicationContext context = new ClassPathXmlApplicationContext("config.xml");
		JournalDao journalDao = (JournalDao)context.getBean("journalDao");
		try
		{			
			entity = journalDao.findJournal(transactionID, serialNo);		
		}
		catch (DataAccessException ex)
		{
			ex.printStackTrace();			
		}
		return entity;
	}	
	public List<JournalEntity> findJournalList(String status)
	{
		List<JournalEntity> entityList = null;
		ApplicationContext context = new ClassPathXmlApplicationContext("config.xml");
		JournalDao journalDao = (JournalDao)context.getBean("journalDao");
		try
		{			
			entityList = journalDao.findJournalList(status);
		}
		catch (DataAccessException ex)
		{
			ex.printStackTrace();			
		}
		return entityList;
	}
	public String closeJournalData(String transactionID, int serialNo)
	{
		ApplicationContext context = new ClassPathXmlApplicationContext("config.xml");
		JournalDao journalDao = (JournalDao)context.getBean("journalDao");
		try
		{			
			journalDao.closeTransaction(transactionID, serialNo);		
			return "Successful!";
		}
		catch (DataAccessException ex)
		{
			ex.printStackTrace();	
			return "Update fail!";
		}
	}
}
