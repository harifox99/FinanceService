package org.bear.entity;

import java.util.Date;

public class PMIndexEntity 
{
	String year;
	String date;
	Date yearMonth;
	/**
	 * S&P 500 Open
	 */
	String spOpen;
	/**
	 * S&P 500 High
	 */
	String spHigh;
	/**
	 * S&P 500 Low
	 */
	String spLow;
	/**
	 * S&P 500 Close
	 */
	String spClose;
	String newOrders;
	String production;
	String employment;
	String deliveries;
	String inventories;
	String pmi;
	String baseline;
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public Date getYearMonth() {
		return yearMonth;
	}
	public void setYearMonth(Date yearMonth) {
		this.yearMonth = yearMonth;
	}
	public String getSpOpen() {
		return spOpen;
	}
	public void setSpOpen(String spOpen) {
		this.spOpen = spOpen;
	}
	public String getSpHigh() {
		return spHigh;
	}
	public void setSpHigh(String spHigh) {
		this.spHigh = spHigh;
	}
	public String getSpLow() {
		return spLow;
	}
	public void setSpLow(String spLow) {
		this.spLow = spLow;
	}
	public String getSpClose() {
		return spClose;
	}
	public void setSpClose(String spClose) {
		this.spClose = spClose;
	}
	public String getNewOrders() {
		return newOrders;
	}
	public void setNewOrders(String newOrders) {
		this.newOrders = newOrders;
	}
	public String getProduction() {
		return production;
	}
	public void setProduction(String production) {
		this.production = production;
	}
	public String getEmployment() {
		return employment;
	}
	public void setEmployment(String employment) {
		this.employment = employment;
	}
	public String getDeliveries() {
		return deliveries;
	}
	public void setDeliveries(String deliveries) {
		this.deliveries = deliveries;
	}
	public String getInventories() {
		return inventories;
	}
	public void setInventories(String inventories) {
		this.inventories = inventories;
	}
	public String getPmi() {
		return pmi;
	}
	public void setPmi(String pmi) {
		this.pmi = pmi;
	}
	public String getBaseline() {
		return baseline;
	}
	public void setBaseline(String baseline) {
		this.baseline = baseline;
	}
	
}
