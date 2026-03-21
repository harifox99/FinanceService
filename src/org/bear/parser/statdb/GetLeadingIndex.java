/**
 * 
 */
package org.bear.parser.statdb;

import org.bear.parser.taiwanMacro.NdcParser;
import org.bear.util.HttpUtil;
import org.bear.util.cbc.NewGetStatDbData;

/**
 * @author edward
 * 統計資訊網->景氣指標統計->景氣領先指標/景氣領先指標不含趨勢/景氣燈號
 */
public class GetLeadingIndex extends NewGetStatDbData 
{
	public GetLeadingIndex()
	{
		super();
	}
	public void getContent(int startDateValue, int endDateValue)
	{
		this.startDateValue = startDateValue;
		this.endDateValue = endDateValue;
		this.url = "http://statdb.dgbas.gov.tw/pxweb/Dialog/Saveshow.asp";
		this.valdavarden2 = "3";
		this.valdavarden3 = "1";
		this.values2 = new String[]{"1", "2", "5"};
		this.values3 = new String[]{"1"};
		this.matrix = "ES0101A2M";
		this.root = "../PXfile/EconomicStatistics/";
		this.classdir = "../PXfile/EconomicStatistics/";
		this.setParameter();
		String responseString = HttpUtil.send(url, paramList, 1, "big5");
		//System.out.println(responseString);
		NdcParser ndcParser = new NdcParser();
		ndcParser.setResponseString(responseString);
		ndcParser.setDao(dao);
		ndcParser.parse(1);
	}
}
