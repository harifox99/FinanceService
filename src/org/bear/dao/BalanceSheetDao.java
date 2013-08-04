/**
 * 
 */
package org.bear.dao;
import java.util.List;
import org.bear.entity.BalanceSheetEntity;

/**
 * @author edward
 *
 */
public interface BalanceSheetDao 
{
	public void insertBatch(List<BalanceSheetEntity> entity);
	public List<BalanceSheetEntity> findDataBySeason(String stockID, String year, String seasons);
	public List<BalanceSheetEntity> findDataByYear(String stockID, String year);
	public List<BalanceSheetEntity> findDataByYear(String stockID);
	public void insert(BalanceSheetEntity balanceSheetEntity);
}
