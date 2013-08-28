package org.bear.main;
import org.bear.datainput.GetSFIContent;
import org.bear.datainput.ImportPriceSFI;
import org.bear.util.newRevenue.GetSFIPrice;
import org.bear.util.newRevenue.GetSFIRevenue;
import org.bear.util.newRevenue.GetTwseIndividualIndex;
import org.bear.util.newRevenue.GretaiIndividualIndex;

public class BuildRevenueSFI {

	/**
	 * @param args
	 */
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		GetSFIContent getContent;
		ImportPriceSFI sfi = new ImportPriceSFI();
		/* 上市公司價格資訊 */
		//getContent = new GetSFIPrice();
		//sfi.insertBatchList("2011", "12", "2013", "7", getContent);
		/* 證交所平均價與週轉率 */
		getContent = new GetTwseIndividualIndex();
		sfi.insertBatchList("2010", null, null, null, getContent);
		sfi.insertBatchList("2011", null, null, null, getContent);
		sfi.insertBatchList("2012", null, null, null, getContent);
		sfi.insertBatchList("2013", null, null, null, getContent);
		/* 櫃臺月成交資訊與週轉率 */
		getContent = new GretaiIndividualIndex(); 
		sfi.insertBatchList("2012", null, null, null, getContent);		
		sfi.insertBatchList("2013", null, null, null, getContent);
		/* 營收資訊 */
		getContent = new GetSFIRevenue();
		sfi.insertBatchList("2012", "1", "2013", "7", getContent);
	}
}
