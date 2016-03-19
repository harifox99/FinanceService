package org.bear.entity;
/**
 * ¬õ¨ß¿ïªÑªkEntity
 * @author edward
 *
 */
public class RedRabbitStockWrapper extends BasicStockWrapper {
	double latestMonth;
	double lastMonth;
	double beforeLastMonth;
	double threeMonthAgo;
	double fourMonthAgo;
	double fiveMonthAgo;
	double pe;
	public double getLatestMonth() {
		return latestMonth;
	}
	public void setLatestMonth(double latestMonth) {
		this.latestMonth = latestMonth;
	}
	public double getLastMonth() {
		return lastMonth;
	}
	public void setLastMonth(double lastMonth) {
		this.lastMonth = lastMonth;
	}
	public double getBeforeLastMonth() {
		return beforeLastMonth;
	}
	public void setBeforeLastMonth(double beforeLastMonth) {
		this.beforeLastMonth = beforeLastMonth;
	}
	public double getThreeMonthAgo() {
		return threeMonthAgo;
	}
	public void setThreeMonthAgo(double threeMonthAgo) {
		this.threeMonthAgo = threeMonthAgo;
	}
	public double getFourMonthAgo() {
		return fourMonthAgo;
	}
	public void setFourMonthAgo(double fourMonthAgo) {
		this.fourMonthAgo = fourMonthAgo;
	}
	public double getFiveMonthAgo() {
		return fiveMonthAgo;
	}
	public void setFiveMonthAgo(double fiveMonthAgo) {
		this.fiveMonthAgo = fiveMonthAgo;
	}
	public double getPe() {
		return pe;
	}
	public void setPe(double pe) {
		this.pe = pe;
	}
	
}
