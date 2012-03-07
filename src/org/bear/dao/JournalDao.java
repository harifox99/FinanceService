/**
 * 
 */
package org.bear.dao;

import java.util.List;
import org.bear.journal.wrapper.JournalEntity;

/**
 * @author edward
 * 儲存與查詢日誌資料
 */
public interface JournalDao 
{
	public List<JournalEntity> findJournalList(String status);
	public void insert(JournalEntity entity);
	public JournalEntity findJournal(String transactionID, int serialNo);
	public void closeTransaction(String transactionID, int serialNo);
}
