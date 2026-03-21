/**
 * 
 */
package org.bear.dao;

import java.util.List;
import org.bear.entity.FinancialDataEntity;

/**
 * @author edward
 *
 */
public interface FinancialDataDao 
{
	public void insertBatch(List<FinancialDataEntity> entity);
	public List<FinancialDataEntity> findDataBySeason(String stockID, String year, String seasons);
	public List<FinancialDataEntity> findDataByYear(String stockID, String year);
	public void insert(FinancialDataEntity financialEntity);
	public void insertWithCheck(FinancialDataEntity financialEntity);
}
