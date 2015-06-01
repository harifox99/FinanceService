package org.bear.util.cbc;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.bear.dao.MacroEconomicDao;
/**
 * 中華民國統計資訊網Parser
 * @author edward
 *
 */
public class GetStatDbData
{
	String url;
	List<NameValuePair> paramList = new ArrayList<NameValuePair>();
	MacroEconomicDao dao;
	public void setData(String key, String value)
	{
		paramList.add(new BasicNameValuePair(key, value));
	}	
	public void setDao(MacroEconomicDao dao) {
		this.dao = dao;
	}
	public GetStatDbData()
	{
		paramList.add(new BasicNameValuePair("strList", "L"));
		paramList.add(new BasicNameValuePair("var1", "期間"));
		paramList.add(new BasicNameValuePair("var2", "指標"));
		paramList.add(new BasicNameValuePair("var3", "種類"));
	}
}
