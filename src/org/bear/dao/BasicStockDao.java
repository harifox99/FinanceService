package org.bear.dao;

import java.util.List;
import org.bear.entity.BasicStockWrapper;

public interface BasicStockDao 
{
	public void insertBatch(List<BasicStockWrapper> entity);
	public List<BasicStockWrapper> findAllData();
}
