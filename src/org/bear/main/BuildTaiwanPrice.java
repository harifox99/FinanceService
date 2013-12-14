package org.bear.main;

import org.bear.datainput.GetSFIContent;
import org.bear.datainput.ImportPriceSFI;
import org.bear.util.newRevenue.GetSFIPrice;
import org.bear.util.newRevenue.GetTwseIndividualIndex;
import org.bear.util.newRevenue.GretaiIndividualIndex;
/**
 * 價格資訊與週轉率
 * @author edward
 *
 */
public class BuildTaiwanPrice {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String year = "2013";
		String preMonth = "10";
		String month = "11";
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
		sfi.insertBatchList(year, month, null, null, getContent);
	}
}
