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
	/**
	 * 400張大戶
	 */
	double fourHundred;
	/**
	 * 800張大戶
	 */
	double eightHundred;
	/**
	 * 千張大戶
	 */
	double thousand;
	/**
	 * 股本
	 */
	double capital;
	/**
	 * 收盤價
	 */
	double price;
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
	public double getFourHundred() {
		return fourHundred;
	}
	public void setFourHundred(double fourHundred) {
		this.fourHundred = fourHundred;
	}
	public double getEightHundred() {
		return eightHundred;
	}
	public void setEightHundred(double eightHundred) {
		this.eightHundred = eightHundred;
	}
	public double getThousand() {
		return thousand;
	}
	public void setThousand(double thousand) {
		this.thousand = thousand;
	}
	public double getCapital() {
		return capital;
	}
	public void setCapital(double capital) {
		this.capital = capital;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}	
}
