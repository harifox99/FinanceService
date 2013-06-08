package org.bear.dao;

import java.util.Date;
import java.util.List;

import org.bear.entity.AmericanMacroPantherEntity;

public interface AmericanMacroPantherDao
{
	public void insertBatch(List <AmericanMacroPantherEntity>  entity);
	public List <AmericanMacroPantherEntity> findAll();
	public List <AmericanMacroPantherEntity> findByDate(Date startTime, Date endTime);
	public int update(String indexName, String indexValue, String date);
}
