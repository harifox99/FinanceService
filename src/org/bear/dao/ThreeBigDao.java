package org.bear.dao;

import java.util.Date;
import java.util.List;
import org.bear.entity.ThreeBigEntity;

public interface ThreeBigDao {
	public void insert(ThreeBigEntity entity); 
	public List<ThreeBigEntity> latest(String stockID, int duration);
	public int update(String indexName, String indexValue, String date, String stockID);
	public long query(String stockID, Date date, String columnName, int addDay);
}
