package org.bear.entity;
/**
 * @author edward
 * 報表基本資料Entity
 */
public class BasicEntity {
	public String stockID;
	public String year;
	public String seasons;
	public String getStockID() {
		return stockID;
	}
	public void setStockID(String stockID) {
		this.stockID = stockID;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getSeasons() {
		return seasons;
	}
	public void setSeasons(String seasons) {
		this.seasons = seasons;
	}
}
