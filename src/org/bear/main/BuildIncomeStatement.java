/**
 * 
 */
package org.bear.main;
import org.bear.datainput.ImportIncomeStatementCathay;

/**
 * @author edward
 *
 */
public class BuildIncomeStatement {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BuildIncomeStatement buildIncomeStatement = new BuildIncomeStatement();
		buildIncomeStatement.insertBatch();
	}
	public void insertBatch()
	{
		ImportIncomeStatementCathay importIncomeStatement = new ImportIncomeStatementCathay();
		importIncomeStatement.insertBatchList();
	}
}
