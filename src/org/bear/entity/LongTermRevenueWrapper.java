package org.bear.entity;
/**
 * @author edward
 * 長期營收趨勢變動圖
 */
public class LongTermRevenueWrapper {
	int twelveAverage;
	int threeAverage;
	int difference;
	String openIndex;
	String highIndex;
	String lowIndex;
	String closeIndex;
	String yearMonth;
	public int getTwelveAverage() {
		return twelveAverage;
	}
	public void setTwelveAverage(int twelveAverage) {
		this.twelveAverage = twelveAverage;
	}
	public int getThreeAverage() {
		return threeAverage;
	}
	public void setThreeAverage(int threeAverage) {
		this.threeAverage = threeAverage;
	}
	public int getDifference() {
		return difference;
	}
	public void setDifference(int difference) {
		this.difference = difference;
	}
	public String getYearMonth() {
		return yearMonth;
	}
	public void setYearMonth(String yearMonth) {
		this.yearMonth = yearMonth;
	}
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
}
