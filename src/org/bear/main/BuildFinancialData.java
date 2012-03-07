/**
 * 
 */
package org.bear.main;

import org.bear.datainput.ImportFinancialDataCathay;

/**
 * @author edward
 *
 */
public class BuildFinancialData {

	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
		BuildFinancialData buildFinancialData = new BuildFinancialData();
		buildFinancialData.insertBatch();
	}
	public void insertBatch()
	{
		ImportFinancialDataCathay importFinancialCathay = new ImportFinancialDataCathay();
		importFinancialCathay.insertBatchList();
	}

}
