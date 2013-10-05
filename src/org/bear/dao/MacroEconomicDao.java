package org.bear.dao;
import org.bear.entity.*;
import java.util.List;
import java.util.Date;
public interface MacroEconomicDao 
{
	public void insertBatch(List <MacroEconomicEntity> entity);
	public List <MacroEconomicEntity> findAll();
	public List <MacroEconomicEntity> findByDate(Date startTime, Date endTime);
	public int update(String indexName, String indexValue, String date, String split);
}
