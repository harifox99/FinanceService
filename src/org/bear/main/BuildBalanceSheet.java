package org.bear.main;

import org.bear.datainput.ImportBalanceSheetCathay;


public class BuildBalanceSheet {

	/**
	 * 下載資產負債表
	 * @param args
	 */
	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
		BuildBalanceSheet buildBalanceSheet = new BuildBalanceSheet();
		buildBalanceSheet.insertBatch();
	}
	public void insertBatch()
	{
		ImportBalanceSheetCathay importBalanceSheet = new ImportBalanceSheetCathay();
		importBalanceSheet.insertBatchList();
	}
}
