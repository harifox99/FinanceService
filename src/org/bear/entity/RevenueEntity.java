package org.bear.entity;
import java.util.*;
/**
 * 每月營業收入與股價關係圖
 * @author edward
 *
 */
public class RevenueEntity 
{
	String openIndex;
	String highIndex;
	String lowIndex;
	String closeIndex;
	String stockID;
	String averageIndex;
	int revenue;
	int accumulation;
	int lastAccumulation;
	int lastRevenue;
	Date yearMonth;
	String turnoverRatio;
	String revenueMoM;
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
	public int getRevenue() {
		return revenue;
	}
	public void setRevenue(int revenue) {
		this.revenue = revenue;
	}
	public int getAccumulation() {
		return accumulation;
	}
	public void setAccumulation(int accumulation) {
		this.accumulation = accumulation;
	}
	public int getLastAccumulation() {
		return lastAccumulation;
	}
	public void setLastAccumulation(int lastAccumulation) {
		this.lastAccumulation = lastAccumulation;
	}
	public int getLastRevenue() {
		return lastRevenue;
	}
	public void setLastRevenue(int lastRevenue) {
		this.lastRevenue = lastRevenue;
	}
	public String getStockID() {
		return stockID;
	}
	public void setStockID(String stockID) {
		this.stockID = stockID;
	}
	public String getTurnoverRatio() {
		return turnoverRatio;
	}
	public void setTurnoverRatio(String turnoverRatio) {
		this.turnoverRatio = turnoverRatio;
	}
	public String getRevenueMoM() {
		return revenueMoM;
	}
	public void setRevenueMoM(String revenueMoM) {
		this.revenueMoM = revenueMoM;
	}
	public Date getYearMonth() {
		return yearMonth;
	}
	public void setYearMonth(Date yearMonth) {
		this.yearMonth = yearMonth;
	}
	public String getAverageIndex() {
		return averageIndex;
	}
	public void setAverageIndex(String averageIndex) {
		this.averageIndex = averageIndex;
	}
}
