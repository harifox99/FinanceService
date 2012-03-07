package org.bear.entity;

public class AmericanMacroPantherEntity extends AmericanMacroEntity {
	/**
	 * 3個月殖利率
	 */
	String tb3ms;
	/**
	 * 90天商業本票
	 */
	String cfp3m;
	/**
	 * 零售銷售
	 */
	String rsafs;
	/**
	 * 非農就業人口
	 */
	String payems;
	public String getPayems() {
		return payems;
	}
	public void setPayems(String payems) {
		this.payems = payems;
	}
	public String getRsafs() {
		return rsafs;
	}
	public void setRsafs(String rsafs) {
		this.rsafs = rsafs;
	}
	public String getTb3ms() {
		return tb3ms;
	}
	public void setTb3ms(String tb3ms) {
		this.tb3ms = tb3ms;
	}
	public String getCfp3m() {
		return cfp3m;
	}
	public void setCfp3m(String cfp3m) {
		this.cfp3m = cfp3m;
	}

}
