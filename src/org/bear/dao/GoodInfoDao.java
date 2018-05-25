package org.bear.dao;

import java.util.List;

import org.bear.entity.GoodInfoEntity;

public interface GoodInfoDao 
{
	public void insertBatch(List<GoodInfoEntity> entity);
	public int update(String indexName, String indexValue, String date, String stockId);
}
