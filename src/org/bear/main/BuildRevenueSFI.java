package org.bear.main;
import org.bear.datainput.GetSFIContent;
import org.bear.datainput.ImportPriceSFI;
import org.bear.util.newRevenue.GetSFIGrateiRevenue;
import org.bear.util.newRevenue.GetSFIPrice;
import org.bear.util.newRevenue.GetSFITwseRevenue;
import org.bear.util.newRevenue.GetTwseIndividualIndex;
import org.bear.util.newRevenue.GretaiIndividualIndex;

public class BuildRevenueSFI {

	/**
	 * @param args
	 */
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String year = "2013";
		String preMonth = "7";
		String month = "8";
		GetSFIContent getContent;
		ImportPriceSFI sfi = new ImportPriceSFI();
		/* 上市公司價格資訊 */
		getContent = new GetSFIPrice();
		sfi.insertBatchList(year, preMonth, year, month, getContent);
		/* 證交所平均價與週轉率 */
		getContent = new GetTwseIndividualIndex();
		sfi.insertBatchList(year, null, null, null, getContent);
		/* 櫃臺月成交資訊與週轉率 */
		getContent = new GretaiIndividualIndex(); 		
		sfi.insertBatchList(year, null, null, null, getContent);
		/* 上市營收資訊 */
		getContent = new GetSFITwseRevenue();
		sfi.insertBatchList(year, month, year, month, getContent);
		/* 上櫃營收資訊 */
		getContent = new GetSFIGrateiRevenue();
		sfi.insertBatchList(year, month, year, month, getContent);
	}
}
