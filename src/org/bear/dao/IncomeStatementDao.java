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
	public void insert(IncomeStatementEntity incomeStatementEntity);
	public void insertWithCheck(IncomeStatementEntity incomeStatementEntity);
	public List<IncomeStatementEntity> findDataByLatest(int size, String stockID);
	public IncomeStatementEntity findSingleDataBySeason(String stockID, String year, String seasons);
}
