package org.bear.entity;
/**
 * 根據資料庫資料與package org.bear.converter，計算累計營收、YoY and MoM，將計算後的資料儲存在此Wrapper
 * @author edward
 *
 */
public class RevenueIncreaseWrapper 
{
	double yoyRevenue;
	double momRevenue;
	double accumulation;
	double turnoverRatio;
	String openIndex;
	String highIndex;
	String lowIndex;
	String closeIndex;
	String yearMonth;
	
	public String getOpenIndex() {
		return openIndex;
	}
	public void setOpenIndex(String openIndex) {
		this.openIndex = openIndex;
	}
	public String getHighIndex() {
		return highIndex;
	}
	public void setHighIndex(String highIndex) {
		this.highIndex = highIndex;
	}
	public String getLowIndex() {
		return lowIndex;
	}
	public void setLowIndex(String lowIndex) {
		this.lowIndex = lowIndex;
	}
	public String getCloseIndex() {
		return closeIndex;
	}
	public void setCloseIndex(String closeIndex) {
		this.closeIndex = closeIndex;
	}
	public double getYoyRevenue() {
		return yoyRevenue;
	}
	public void setYoyRevenue(double yoyRevenue) {
		this.yoyRevenue = yoyRevenue;
	}
	public double getMomRevenue() {
		return momRevenue;
	}
	public void setMomRevenue(double momRevenue) {
		this.momRevenue = momRevenue;
	}
	public double getAccumulation() {
		return accumulation;
	}
	public void setAccumulation(double accumulation) {
		this.accumulation = accumulation;
	}
	public String getYearMonth() {
		return yearMonth;
	}
	public void setYearMonth(String yearMonth) {
		this.yearMonth = yearMonth;
	}
	public double getTurnoverRatio() {
		return turnoverRatio;
	}
	public void setTurnoverRatio(double turnoverRatio) {
		this.turnoverRatio = turnoverRatio;
	}
}
