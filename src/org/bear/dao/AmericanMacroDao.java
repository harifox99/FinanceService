package org.bear.dao;

import java.util.Date;
import java.util.List;

import org.bear.entity.AmericanMacroEntity;

public interface AmericanMacroDao
{
	public void insertBatch(List <AmericanMacroEntity>  entity);
	public List <AmericanMacroEntity> findAll();
	public List <AmericanMacroEntity> findByDate(Date startTime, Date endTime);
}
