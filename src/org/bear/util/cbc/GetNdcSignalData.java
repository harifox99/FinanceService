package org.bear.util.cbc;
import org.bear.parser.taiwanMacro.SimpleParser;
import org.bear.util.HttpUtil;
/**
 * 景氣燈號從統計資訊網
 * @author edward
 *
 */
public class GetNdcSignalData extends NewGetStatDbData 
{
	public GetNdcSignalData()
	{
		super();
	}
	public void getContent(int startDateValue, int endDateValue)
	{
		this.startDateValue = startDateValue;
		this.endDateValue = endDateValue;
		this.url = "http://statdb.dgbas.gov.tw/pxweb/Dialog/Saveshow.asp";
		this.valdavarden2 = "1";
		this.valdavarden3 = "1";
		this.values2 = new String[]{"1"};
		this.values3 = new String[]{"1"};
		this.matrix = "ES0103A1M";
		this.root = "../PXfile/EconomicStatistics/";
		this.classdir = "../PXfile/EconomicStatistics/";
		this.setParameter();
		String responseString = HttpUtil.send(url, paramList, 1, "big5");
		//System.out.println(responseString);
		SimpleParser ndcSignalParser = new SimpleParser();
		ndcSignalParser.setResponseString(responseString);
		ndcSignalParser.setDao(dao);
		ndcSignalParser.setTableName("LightSignal");
		ndcSignalParser.parse(1);
	}
}
