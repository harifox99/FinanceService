package org.bear.main;

import org.bear.datainput.GetSFIContent;
import org.bear.datainput.ImportPriceSFI;
import org.bear.util.newRevenue.GetHinetStockPrice;
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
		
		String preYear = "2015";
		String preMonth = "4";
		String year = "2015";
		String month = "4";
		GetSFIContent getContent;
		ImportPriceSFI sfi = new ImportPriceSFI();
		/* 上市公司價格資訊...證券期貨發展基金會自2015/1/1，不再提供載客服務 */
		//getContent = new GetSFITwsePrice();
		//sfi.insertBatchList(preYear, preMonth, year, month, getContent);
		getContent = new GetHinetStockPrice();
		sfi.insertBatchList(preYear, preMonth, year, month, getContent);
		/* 證交所平均價與週轉率, sleep time可以調成1000ms */
		getContent = new GetTwseIndividualIndex();
		sfi.insertBatchList(year, null, null, null, getContent);
		/* 櫃臺月成交資訊與週轉率 */
		getContent = new GretaiIndividualIndex(); 		
		sfi.insertBatchList(year, preMonth, null, month, getContent);
	}
}
