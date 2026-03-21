package org.bear.entity;

import java.util.List;

public class RoeYearWrapper {
	String stockID;
	String stockName;
	List<Double> roeList;
	int roeSize;
	String year;
	/**
	 * 短期營收
	 */
	double shortRevenue;
	/**
	 * 長期營收
	 */
	double longRevenue;
	public String getStockID() {
		return stockID;
	}
	public void setStockID(String stockID) {
		this.stockID = stockID;
	}
	public int getRoeSize() {
		return roeSize;
	}
	public void setRoeSize(int roeSize) {
		this.roeSize = roeSize;
	}
	public List<Double> getRoeList() {
		return roeList;
	}
	public void setRoeList(List<Double> roeList) {
		this.roeList = roeList;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getStockName() {
		return stockName;
	}
	public void setStockName(String stockName) {
		this.stockName = stockName;
	}
	public double getShortRevenue() {
		return shortRevenue;
	}
	public void setShortRevenue(double shortRevenue) {
		this.shortRevenue = shortRevenue;
	}
	public double getLongRevenue() {
		return longRevenue;
	}
	public void setLongRevenue(double longRevenue) {
		this.longRevenue = longRevenue;
	}
	
}
