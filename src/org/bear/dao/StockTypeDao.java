package org.bear.dao;

import java.util.List;

import org.bear.entity.StockType;

public interface StockTypeDao {
	public List<Integer> findAllData();
	public List<StockType> findFullData();
}
