/**
 * 
 */
package org.bear.entity;

/**
 * @author edward
 *
 */
public class CashConversionCycleWrapper extends BasicEntity 
{
	/**
	 * 現金轉換循環
	 */
	double ccc;
	/**
	 * 平均存貨天數
	 */
	double inventoryDays;
	/**
	 * 平均應收帳款收現天數
	 */
	double averageCollctionPeriod;
	/**
	 * 平均應付帳款付款天數
	 */
	double averageAccountsPayableDays;
	/**
	 * 存貨週轉率
	 */
	double inventoryTurnover;
	/**
	 * 應收帳款週轉率
	 */
	double receivableTurnover;
	/**
	 * 應付帳款週轉率
	 */
	double accountsPayableTurnover;
	public double getCcc() {
		return ccc;
	}
	public void setCcc(double ccc) {
		this.ccc = ccc;
	}
	public double getInventoryDays() {
		return inventoryDays;
	}
	public void setInventoryDays(double inventoryDays) {
		this.inventoryDays = inventoryDays;
	}
	public double getAverageCollctionPeriod() {
		return averageCollctionPeriod;
	}
	public void setAverageCollctionPeriod(double averageCollctionPeriod) {
		this.averageCollctionPeriod = averageCollctionPeriod;
	}
	public double getAverageAccountsPayableDays() {
		return averageAccountsPayableDays;
	}
	public void setAverageAccountsPayableDays(double averageAccountsPayableDays) {
		this.averageAccountsPayableDays = averageAccountsPayableDays;
	}
	public double getInventoryTurnover() {
		return inventoryTurnover;
	}
	public void setInventoryTurnover(double inventoryTurnover) {
		this.inventoryTurnover = inventoryTurnover;
	}
	public double getReceivableTurnover() {
		return receivableTurnover;
	}
	public void setReceivableTurnover(double receivableTurnover) {
		this.receivableTurnover = receivableTurnover;
	}
	public double getAccountsPayableTurnover() {
		return accountsPayableTurnover;
	}
	public void setAccountsPayableTurnover(double accountsPayableTurnover) {
		this.accountsPayableTurnover = accountsPayableTurnover;
	}
}
