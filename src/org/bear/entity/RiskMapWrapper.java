/**
 * 
 */
package org.bear.entity;

/**
 * @author edward
 *
 */
public class RiskMapWrapper extends BasicEntity 
{
	double roe;
	double nav;
	double reinvestmentRate;
	/**
	 * 股價
	 */
	double averagePrice;
	double maxPrice;
	double minPrice;
	/**
	 * 股價淨值比
	 */
	double pbr;
	double maxPbr;
	double minPbr;
	/**
	 * 外部股東權益報酬率
	 */
	double kn;
	double maxKn;
	double minKn;
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
	public double getAveragePrice() {
		return averagePrice;
	}
	public void setAveragePrice(double averagePrice) {
		this.averagePrice = averagePrice;
	}
	public double getMaxPrice() {
		return maxPrice;
	}
	public void setMaxPrice(double maxPrice) {
		this.maxPrice = maxPrice;
	}
	public double getMinPrice() {
		return minPrice;
	}
	public void setMinPrice(double minPrice) {
		this.minPrice = minPrice;
	}
	public double getPbr() {
		return pbr;
	}
	public void setPbr(double pbr) {
		this.pbr = pbr;
	}
	public double getMaxPbr() {
		return maxPbr;
	}
	public void setMaxPbr(double maxPbr) {
		this.maxPbr = maxPbr;
	}
	public double getMinPbr() {
		return minPbr;
	}
	public void setMinPbr(double minPbr) {
		this.minPbr = minPbr;
	}
	public double getKn() {
		return kn;
	}
	public void setKn(double kn) {
		this.kn = kn;
	}
	public double getMaxKn() {
		return maxKn;
	}
	public void setMaxKn(double maxKn) {
		this.maxKn = maxKn;
	}
	public double getMinKn() {
		return minKn;
	}
	public void setMinKn(double minKn) {
		this.minKn = minKn;
	}
	public double getReinvestmentRate() {
		return reinvestmentRate;
	}
	public void setReinvestmentRate(double reinvestmentRate) {
		this.reinvestmentRate = reinvestmentRate;
	}
}
