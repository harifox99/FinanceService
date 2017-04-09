package org.bear.entity;

import java.util.List;

public class RoeYearWrapper {
	String stockID;
	String stockName;
	List<Double> roeList;
	int roeSize;
	String year;
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
	
}
