package org.bear.entity;

public class AmericanMacroPantherEntity extends AmericanMacroEntity {
	/**
	 * 3個月殖利率
	 */
	String tb3ms;
	/**
	 * 90天商業本票
	 */
	String cpf3m;
	/**
	 * 零售銷售
	 */
	String rsafs;
	/**
	 * 非農就業人口
	 */
	String payems;
	/**
	 * 商業存貨
	 */
	String businv;
	/**
	 * 高收益債利差
	 */
	String bamlh0a0hym2;
	/**
	 * 建築許可YoY
	 */
	String permitnsaYoy;
	/**
	 * VIX恐慌指數
	 */
	String vixHigh;
	String vixLow;
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

	public String getBusinv() {
		return businv;
	}
	public void setBusinv(String businv) {
		this.businv = businv;
	}
	public String getCpf3m() {
		return cpf3m;
	}
	public void setCpf3m(String cpf3m) {
		this.cpf3m = cpf3m;
	}
	public String getBamlh0a0hym2() {
		return bamlh0a0hym2;
	}
	public void setBamlh0a0hym2(String bamlh0a0hym2) {
		this.bamlh0a0hym2 = bamlh0a0hym2;
	}
	public String getPermitnsaYoy() {
		return permitnsaYoy;
	}
	public void setPermitnsaYoy(String permitnsaYoy) {
		this.permitnsaYoy = permitnsaYoy;
	}
	public String getVixHigh() {
		return vixHigh;
	}
	public void setVixHigh(String vixHigh) {
		this.vixHigh = vixHigh;
	}
	public String getVixLow() {
		return vixLow;
	}
	public void setVixLow(String vixLow) {
		this.vixLow = vixLow;
	}
}
