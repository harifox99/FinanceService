package org.bear.parser;

import org.bear.entity.BasicEntity;
import net.htmlparser.jericho.Element;
/**
 * @author edward
 * 我只是Interface
 */
public interface Parser 
{
	/**
	 * 通常從網站傳回來的Table不只一個，所以根據Index來決定你要Parse第幾個Table
	 * @param tableIndex 
	 */
	public void parse(int tableIndex);
	/**
	 * Element儲存Parse後的Table資料，此Element是一個特殊格式，需要用method getTableContent一個一個擷取
	 * @param element
	 */
	public void getTableContent(Element element);
	/**
	 * 擷取Element裡面的Table資料
	 * @param entity 現金流量Entity
	 * @param rowData 某一筆row（ 網站的每一年度或季度資料是以Column方式儲存，但是HTML的格式是以Row方式建立）
	 */
	public void setYearAndSeason(BasicEntity entity, String rowData);
	/**
	 * 
	 * @param rowData 用此陣列儲存現金流量表某筆Row資料
	 */
	public void setStockData(String rowData[]);
}
