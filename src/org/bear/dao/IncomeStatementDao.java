/**
 * 
 */
package org.bear.dao;
import java.util.List;

import org.bear.entity.IncomeStatementEntity;

/**
 * @author edward
 *
 */
public interface IncomeStatementDao {
	public void insertBatch(List<IncomeStatementEntity> entity);
	public List<IncomeStatementEntity> findDataBySeason(String stockID, String year, String seasons);
	public List<IncomeStatementEntity> findDataByYear(String stockID, String year);
	public List<IncomeStatementEntity> findDataByYear(String stockID);
	public void insert(IncomeStatementEntity balanceSheetEntity);
}
