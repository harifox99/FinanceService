package org.bear.main;
import org.bear.datainput.GetSFIContent;
import org.bear.datainput.ImportPriceSFI;
import org.bear.util.StringUtil;
import org.bear.util.newRevenue.*;

public class BuildRevenueSFI {

	/**
	 * @param args
	 */
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String startYear = "2020";
		String startMonth = "05";
		String endYear = "2020";
		String endMonth = "05";
		GetSFIContent getContent;
		ImportPriceSFI sfi = new ImportPriceSFI();
		/* 上市/上櫃營收資訊 (公開資訊觀測站)，僅用startYear and startMonth */
		getContent = new GetMopsRevenue();
		sfi.insertBatchList(startYear, StringUtil.addZeroMonth(startMonth), endYear, endMonth, getContent);
		/* 上市營收資訊 (證券期貨發展基金會) */
		//getContent = new GetSFITwseRevenue();
		//sfi.insertBatchList(startYear, startMonth, endYear, endMonth, getContent);
		/* 上櫃營收資訊 (證券期貨發展基金會) */
		//getContent = new GetSFIGrateiRevenue();
		//sfi.insertBatchList(startYear, startMonth, endYear, endMonth, getContent);
	}
}
