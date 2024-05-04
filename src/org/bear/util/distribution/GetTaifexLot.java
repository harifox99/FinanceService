package org.bear.util.distribution;
import org.bear.dao.JuristicDailyReportDao;
import org.bear.parser.TaifexLotParser;
/**
 * 期交所外資未平倉口數Http Post URL 
 * @author edward
 *
 */
public class GetTaifexLot 
{
	JuristicDailyReportDao dao;
	String date;
	String url;
	String commodityId;
	TaifexLotParser parser;
	int tableIndex;
	HttpPostWithHeader postHeader;
	public JuristicDailyReportDao getDao() {
		return dao;
	}

	public void setDao(JuristicDailyReportDao dao) {
		this.dao = dao;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url; 
	}

	public String getCommodityId() {
		return commodityId;
	}

	public void setCommodityId(String commodityId) {
		this.commodityId = commodityId;
	}
	
	public TaifexLotParser getParser() {
		return parser;
	}

	public void setParser(TaifexLotParser parser) {
		this.parser = parser;
	}
	
	public int getTableIndex() {
		return tableIndex;
	}

	public void setTableIndex(int tableIndex) {
		this.tableIndex = tableIndex;
	}

	public void getContent()
	{
		HttpPostWithHeader postHeader = new HttpPostWithHeader();
		TaifexLotParser parser = new TaifexLotParser();	
		postHeader.getContent(url, date, dao, parser);
	}
	public static void main(String args[])
	{
		String url = "https://www.taifex.com.tw/cht/3/futContractsDate";
		GetTaifexLot getTaiFexForeignerLot = new GetTaifexLot();
		getTaiFexForeignerLot.setUrl(url);
		getTaiFexForeignerLot.setDate("2024/04/25");
		getTaiFexForeignerLot.setCommodityId("");
		getTaiFexForeignerLot.setTableIndex(2);
		getTaiFexForeignerLot.getContent();
	}
}
