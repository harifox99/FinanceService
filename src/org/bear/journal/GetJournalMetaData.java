package org.bear.journal;

import java.util.List;

import org.bear.dao.CommonMetaDataDao;
import org.bear.journal.wrapper.CommonJournalMetaWrapper;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class GetJournalMetaData 
{
	public List<CommonJournalMetaWrapper> getAppoachList() {
		// TODO Auto-generated method stub
		List <CommonJournalMetaWrapper> list;
		ApplicationContext context = new ClassPathXmlApplicationContext("config.xml");
		CommonMetaDataDao dao = (CommonMetaDataDao)context.getBean("basicCommonMetaDataDao");	
		list = dao.getAppoachList();		
		return list;
	}

	/* (non-Javadoc)
	 * @see org.bear.dao.CommonMetaDataDao#getReasonList()
	 */
	public List<CommonJournalMetaWrapper> getReasonList() {
		// TODO Auto-generated method stub
		List <CommonJournalMetaWrapper> list;
		ApplicationContext context = new ClassPathXmlApplicationContext("config.xml");
		CommonMetaDataDao dao = (CommonMetaDataDao)context.getBean("basicCommonMetaDataDao");	
		list = dao.getReasonList();		
		return list;
	}
}
