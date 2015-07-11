package org.bear.main;

import org.bear.datainput.GetSFIContent;
import org.bear.datainput.ImportPriceSFI;
import org.bear.util.newRevenue.GetHinetStockPrice;
/**
 * 價格資訊與週轉率
 * @author edward
 *
 */
public class BuildTaiwanPrice {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String startYear = "2015";
		String startMonth = "5";
		String year = "2015";
		String month = "5";
		GetSFIContent getContent;
		ImportPriceSFI sfi = new ImportPriceSFI();
		/* 上市公司價格資訊...證券期貨發展基金會自2015/1/1，不再提供載客服務 */
		//getContent = new GetSFITwsePrice();
		//sfi.insertBatchList(preYear, preMonth, year, month, getContent);
		getContent = new GetHinetStockPrice();
		sfi.insertBatchList(startYear, startMonth, year, month, getContent);
		/* 證交所平均價與週轉率, sleep time可以調成1000ms */
		//getContent = new GetTwseIndividualIndex();
		//sfi.insertBatchList(startYear, null, null, null, getContent);
		/* 櫃臺月成交資訊與週轉率 */
		//getContent = new GretaiIndividualIndex(); 		
		//sfi.insertBatchList(startYear, startMonth, null, month, getContent);
	}
}
