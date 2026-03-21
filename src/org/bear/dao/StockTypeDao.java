package org.bear.dao;

import java.util.List;

import org.bear.entity.StockType;
import org.bear.entity.StockTypeName;

public interface StockTypeDao {
	public List<Integer> findAllData();
	public List<StockType> findFullData();
	public List<StockTypeName> getStockTypeName();
}
