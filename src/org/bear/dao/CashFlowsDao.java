package org.bear.dao;
import java.util.List;
import org.bear.entity.CashFlowsEntity;

public interface CashFlowsDao 
{
	public void insertBatch(List<CashFlowsEntity> entity);
	public List<CashFlowsEntity> findDataBySeason(String stockID, String year, String seasons);
	public void insert(CashFlowsEntity cashFlowsEntity);
	public void insertWithCheck(CashFlowsEntity cashFlowsEntity);
	public List<CashFlowsEntity> findDataByYear(String stockID, String year);
	public List<CashFlowsEntity> findLatest(String stockID, int num);
}
