package org.bear.dao;
import java.util.List;
import org.bear.journal.wrapper.CommonJournalMetaWrapper;
public interface CommonMetaDataDao {
	public List<CommonJournalMetaWrapper> getReasonList();
	public List<CommonJournalMetaWrapper> getAppoachList();
}
