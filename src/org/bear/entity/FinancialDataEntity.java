/**
 * 
 */
package org.bear.entity;

/**
 * @author edward
 *
 */
public class FinancialDataEntity extends BasicEntity 
{
	double nav;
	double cashDiv;
	public double getNav() {
		return nav;
	}

	public void setNav(double nav) {
		this.nav = nav;
	}

	public double getCashDiv() {
		return cashDiv;
	}

	public void setCashDiv(double cashDiv) {
		this.cashDiv = cashDiv;
	}
}
