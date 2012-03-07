/**
 * 
 */
package org.bear.entity;

/**
 * @author edward
 *
 */
public class EarningQualityWrapper extends BasicEntity 
{
	/**
	 * 存貨增加率
	 */
	double inventoryRatio;
	/**
	 * 銷貨收入增加率
	 */
	double operatingRevenueRatio;
	/**
	 * 存貨指標
	 */
	double inventoryIndex;
	/**
	 * 應收帳款增加率
	 */
	double receivableRatio;
	/**
	 * 應收帳款指標
	 */
	double receivableIndex;
	/**
	 * 銷貨毛利增加率
	 */
	double grossProfitRatio;
	/**
	 * 銷貨毛利指標
	 */
	double grossProfitIndex;
	/**
	 * 營業費用增加率
	 */
	double operatingExpensesRatio;
	/**
	 * 營業費用指標
	 */
	double operatingExpensesIndex;
	/**
	 * 應付帳款增加率
	 */
	double accountsPayableRatio;
	/**
	 * 應付帳款指標
	 */
	double accountsPayableIndex;
	public double getInventoryRatio() {
		return inventoryRatio;
	}
	public void setInventoryRatio(double inventoryRatio) {
		this.inventoryRatio = inventoryRatio;
	}
	public double getOperatingRevenueRatio() {
		return operatingRevenueRatio;
	}
	public void setOperatingRevenueRatio(double operatingRevenueRatio) {
		this.operatingRevenueRatio = operatingRevenueRatio;
	}
	public double getInventoryIndex() {
		return inventoryIndex;
	}
	public void setInventoryIndex(double inventoryIndex) {
		this.inventoryIndex = inventoryIndex;
	}
	public double getReceivableRatio() {
		return receivableRatio;
	}
	public void setReceivableRatio(double receivableRatio) {
		this.receivableRatio = receivableRatio;
	}
	public double getReceivableIndex() {
		return receivableIndex;
	}
	public void setReceivableIndex(double receivableIndex) {
		this.receivableIndex = receivableIndex;
	}
	public double getGrossProfitRatio() {
		return grossProfitRatio;
	}
	public void setGrossProfitRatio(double grossProfitRatio) {
		this.grossProfitRatio = grossProfitRatio;
	}
	public double getGrossProfitIndex() {
		return grossProfitIndex;
	}
	public void setGrossProfitIndex(double grossProfitIndex) {
		this.grossProfitIndex = grossProfitIndex;
	}
	public double getOperatingExpensesRatio() {
		return operatingExpensesRatio;
	}
	public void setOperatingExpensesRatio(double operatingExpensesRatio) {
		this.operatingExpensesRatio = operatingExpensesRatio;
	}
	public double getOperatingExpensesIndex() {
		return operatingExpensesIndex;
	}
	public void setOperatingExpensesIndex(double operatingExpensesIndex) {
		this.operatingExpensesIndex = operatingExpensesIndex;
	}
	public double getAccountsPayableRatio() {
		return accountsPayableRatio;
	}
	public void setAccountsPayableRatio(double accountsPayableRatio) {
		this.accountsPayableRatio = accountsPayableRatio;
	}
	public double getAccountsPayableIndex() {
		return accountsPayableIndex;
	}
	public void setAccountsPayableIndex(double accountsPayableIndex) {
		this.accountsPayableIndex = accountsPayableIndex;
	}

}
