package org.bear.main;
import org.bear.datainput.GetSFIContent;
import org.bear.datainput.ImportPriceSFI;
import org.bear.util.newRevenue.GetSFIPrice;
import org.bear.util.newRevenue.GetSFIRevenue;

public class BuildRevenueSFI {

	/**
	 * @param args
	 */
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//GetSFIContent getConent = new GetSFIPrice();
		GetSFIContent getConent = new GetSFIRevenue();
		ImportPriceSFI sfi = new ImportPriceSFI();
		sfi.insertBatchList("2013", "3", "2013", "3", getConent);
	}
}
