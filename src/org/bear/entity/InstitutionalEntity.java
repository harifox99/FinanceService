package org.bear.entity;

import java.util.List;
/**
 * 儲存法人每日買賣超佔股本比資料
 * @author edward
 *
 */
public class InstitutionalEntity {
	/**
	 * 股票代碼
	 */
	String stockID;
	/**
	 * 交易資訊
	 */
	List<Double> info;
	/**
	 * 註解
	 */
	String comment;
	/**
	 * 連續買賣超天數
	 */
	String consecutiveDays;
	/**
	 * 名稱
	 */
	String name;
	public String getStockID() {
		return stockID;
	}
	public void setStockID(String stockID) {
		this.stockID = stockID;
	}
	public List<Double> getInfo() {
		return info;
	}
	public void setInfo(List<Double> info) {
		this.info = info;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getConsecutiveDays() {
		return consecutiveDays;
	}
	public void setConsecutiveDays(String consecutiveDays) {
		this.consecutiveDays = consecutiveDays;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}	
}
