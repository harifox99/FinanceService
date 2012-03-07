/**
 * 
 */
package org.bear.entity;

/**
 * @author edward
 *
 */
public class CashFlowsWrapper extends CashFlowsEntity 
{
	/**
	 * 營運活動現金流量與會計盈餘落差
	 */
	int difference;
	/**
	 * 營運活動之現金佔稅後純益比率
	 */
	double cashNetRatio;
	/**
	 * 現金流量比率
	 */
	double cashFlowToCurrentDebt;
	public int getDifference() {
		return difference;
	}

	public void setDifference(int difference) {
		this.difference = difference;
	}

	public double getCashNetRatio() {
		return cashNetRatio;
	}

	public void setCashNetRatio(double cashNetRatio) {
		this.cashNetRatio = cashNetRatio;
	}

	public double getCashFlowToCurrentDebt() {
		return cashFlowToCurrentDebt;
	}

	public void setCashFlowToCurrentDebt(double cashFlowToCurrentDebt) {
		this.cashFlowToCurrentDebt = cashFlowToCurrentDebt;
	}
}
