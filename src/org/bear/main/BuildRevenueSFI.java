package org.bear.main;
import org.bear.datainput.GetSFIContent;
import org.bear.datainput.ImportPriceSFI;
import org.bear.util.newRevenue.GetSFIGrateiRevenue;
import org.bear.util.newRevenue.GetSFITwseRevenue;

public class BuildRevenueSFI {

	/**
	 * @param args
	 */
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String startYear = "2014";
		String startMonth = "9";
		String endYear = "2015";
		String endMonth = "4";
		GetSFIContent getContent;
		ImportPriceSFI sfi = new ImportPriceSFI();
		/* 上市營收資訊 */
		getContent = new GetSFITwseRevenue();
		sfi.insertBatchList(startYear, startMonth, endYear, endMonth, getContent);
		/* 上櫃營收資訊 */
		getContent = new GetSFIGrateiRevenue();
		sfi.insertBatchList(startYear, startMonth, endYear, endMonth, getContent);
	}
}
