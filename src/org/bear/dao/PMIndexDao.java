package org.bear.dao;

import java.util.Date;
import java.util.List;

import org.bear.entity.PMIndexEntity;

public interface PMIndexDao {
	public void insertBatch(List <PMIndexEntity>  entity);
	public List <PMIndexEntity> findAll();
	public List <PMIndexEntity> findByDate(Date startTime, Date endTime);
	public int update(String indexName, String indexValue, String date);
}
