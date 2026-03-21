package org.bear.entity;

import java.util.Date;

public class ThreeBigEntity {
	String stockID;
	Date yearMonth;
	long quantity;
	long supervisor;
	long manager;
	long strongStockHolder;
	public String getStockID() {
		return stockID;
	}
	public void setStockID(String stockID) {
		this.stockID = stockID;
	}
	public Date getYearMonth() {
		return yearMonth;
	}
	public void setYearMonth(Date yearMonth) {
		this.yearMonth = yearMonth;
	}
	public long getQuantity() {
		return quantity;
	}
	public void setQuantity(long quantity) {
		this.quantity = quantity;
	}
	public long getSupervisor() {
		return supervisor;
	}
	public void setSupervisor(long supervisor) {
		this.supervisor = supervisor;
	}
	public long getManager() {
		return manager;
	}
	public void setManager(long manager) {
		this.manager = manager;
	}
	public long getStrongStockHolder() {
		return strongStockHolder;
	}
	public void setStrongStockHolder(long strongStockHolder) {
		this.strongStockHolder = strongStockHolder;
	}
	
}
