package org.bear.entity;
/**
 * 歷年同期營收比較圖Entity
 * @author edward
 *
 */
public class RedRabbitChartEntity 
{
	String year;
	String month;
	/**
	 * 最近半年營收
	 */
	int latestYear;
	/**
	 * 去年營收
	 */
	int lastYear;
	/**
	 * 前年營收
	 */
	int beforeLastYear;
	/**
	 * 三年前營收
	 */
	int threeYearsAgo;
	/**
	 * 四年前營收
	 */
	int fourYearsAgo;
	/**
	 * 五年前營收
	 */
	int fiveYearsAgo;
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public int getLatestYear() {
		return latestYear;
	}
	public void setLatestYear(int latestYear) {
		this.latestYear = latestYear;
	}
	public int getLastYear() {
		return lastYear;
	}
	public void setLastYear(int lastYear) {
		this.lastYear = lastYear;
	}
	public int getBeforeLastYear() {
		return beforeLastYear;
	}
	public void setBeforeLastYear(int beforeLastYear) {
		this.beforeLastYear = beforeLastYear;
	}
	public int getThreeYearsAgo() {
		return threeYearsAgo;
	}
	public void setThreeYearsAgo(int threeYearsAgo) {
		this.threeYearsAgo = threeYearsAgo;
	}
	public int getFiveYearsAgo() {
		return fiveYearsAgo;
	}
	public void setFiveYearsAgo(int fiveYearsAgo) {
		this.fiveYearsAgo = fiveYearsAgo;
	}
	public int getFourYearsAgo() {
		return fourYearsAgo;
	}
	public void setFourYearsAgo(int fourYearsAgo) {
		this.fourYearsAgo = fourYearsAgo;
	}
	
}
