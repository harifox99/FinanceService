/**
 * 
 */
package org.bear.entity;

/**
 * @author edward
 *
 */
public class BuffettAnalysisWrapper extends BasicEntity 
{
	/**
	 * ROE
	 */
	double roe;
	/**
	 * NAV
	 */
	double nav;
	/**
	 * 基
	 */
	double price;
	/**
	 * 基瞓ゑ
	 */
	double pbr;
	/**
	 * 緇щ戈瞯
	 */
	double reinvestmentRate;
	/**
	 * 瞶基
	 */
	double reasonablePrice;
	/**
	 * 基娩悔
	 */
	double upperBound;
	/**
	 * 基娩悔
	 */
	double lowerBound;
	public double getRoe() {
		return roe;
	}
	public void setRoe(double roe) {
		this.roe = roe;
	}
	public double getNav() {
		return nav;
	}
	public void setNav(double nav) {
		this.nav = nav;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public double getReinvestmentRate() {
		return reinvestmentRate;
	}
	public void setReinvestmentRate(double reinvestmentRate) {
		this.reinvestmentRate = reinvestmentRate;
	}
	public double getReasonablePrice() {
		return reasonablePrice;
	}
	public void setReasonablePrice(double reasonablePrice) {
		this.reasonablePrice = reasonablePrice;
	}
	public double getUpperBound() {
		return upperBound;
	}
	public void setUpperBound(double upperBound) {
		this.upperBound = upperBound;
	}
	public double getLowerBound() {
		return lowerBound;
	}
	public void setLowerBound(double lowerBound) {
		this.lowerBound = lowerBound;
	}
	public double getPbr() {
		return pbr;
	}
	public void setPbr(double pbr) {
		this.pbr = pbr;
	}
}
