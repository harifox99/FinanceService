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
	/**
	 * 排名
	 */
	int rank;
	/**
	 * 外資持股比率
	 */
	double foreignerRatio;
	/**
	 * 投信持股比例
	 */
	double mutualFund;
	/**
	 * Day K
	 */
	String day_k;
	/**
	 * Day D
	 */
	String day_d;
	/**
	 * Week K
	 */
	String week_k;
	/**
	 * Week D
	 */
	String week_d;
	/**
	 * Day KD Gold Cross
	 */
	String day_kd_20;
	/**
	 * Week KD Gold Cross
	 */
	String week_kd_20;
	/**
	 * 類股
	 */
	String stockTypeName;
	
	public String getStockTypeName() {
		return stockTypeName;
	}
	public void setStockTypeName(String stockTypeName) {
		this.stockTypeName = stockTypeName;
	}
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
	public int getRank() {
		return rank;
	}
	public void setRank(int rank) {
		this.rank = rank;
	}
	public double getForeignerRatio() {
		return foreignerRatio;
	}
	public void setForeignerRatio(double foreignerRatio) {
		this.foreignerRatio = foreignerRatio;
	}
	public double getMutualFund() {
		return mutualFund;
	}
	public void setMutualFund(double mutualFund) {
		this.mutualFund = mutualFund;
	}
	public String getDay_k() {
		return day_k;
	}
	public void setDay_k(String day_k) {
		this.day_k = day_k;
	}
	public String getDay_d() {
		return day_d;
	}
	public void setDay_d(String day_d) {
		this.day_d = day_d;
	}
	public String getWeek_k() {
		return week_k;
	}
	public void setWeek_k(String week_k) {
		this.week_k = week_k;
	}
	public String getWeek_d() {
		return week_d;
	}
	public void setWeek_d(String week_d) {
		this.week_d = week_d;
	}
	public String getDay_kd_20() {
		return day_kd_20;
	}
	public void setDay_kd_20(String day_kd_20) {
		this.day_kd_20 = day_kd_20;
	}
	public String getWeek_kd_20() {
		return week_kd_20;
	}
	public void setWeek_kd_20(String week_kd_20) {
		this.week_kd_20 = week_kd_20;
	}
}
