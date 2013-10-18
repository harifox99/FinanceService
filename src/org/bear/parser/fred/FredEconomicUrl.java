package org.bear.parser.fred;
import java.util.HashMap;
import org.bear.util.GetFredContentBase;
/**
 * 舱Θ耝FRED羆竒戈URL
 * @author edward
 *
 */
public class FredEconomicUrl extends GetFredContentBase
{
	public static final String proxy = "10.160.3.88";
	public static final int port = 8080;
	public HashMap<String, String> getContent(String key, String value)
	{
		/* Proxy
		InetSocketAddress inetSocketAddressnew = new InetSocketAddress(proxy, port);
		Proxy proxy = new Proxy(Proxy.Type.HTTP, inetSocketAddressnew); 
		Source source = null;*/
		HashMap<String, String> map = null;
		try
		{
			//source = new Source(new URL(urlString).openConnection(proxy));
			//String content = new String(source.toString().getBytes("UTF-8"));
			FredXmlParser parser = new FredXmlParser();
			map = parser.parse(urlString, key, value);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return map;
	}
	public void setUrlString(String series_id, String observation_start, HashMap<String, String> parameterMap) {
		// TODO Auto-generated method stub
		//INDPRO and INDPROYoY常ノINDPRO硂把计耝戈
		if (series_id.contains("INDPRO"))
			series_id = "INDPRO";
		//PERMITNSA and PERMITNSAYoY常ノPERMITNSA硂把计耝戈
		if (series_id.contains("PERMITNSA"))
			series_id = "PERMITNSA";
		this.urlString = this.urlStringHeader + "series_id=" + series_id + "&api_key=" + this.api_key +
			"&observation_start=" + observation_start +	
			"&units=" + parameterMap.get("units") + 
			"&frequency=" + parameterMap.get("frequency");	
		if (parameterMap.get("aggregation_method") != null)
			this.urlString = this.urlString + "&aggregation_method=" + parameterMap.get("aggregation_method");
		System.out.println(urlString);
	}
}
