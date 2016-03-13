package org.bear.parser;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.bear.dao.MacroEconomicDao;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;
/**
 * 國發會，領先指標不含趨勢
 * @author edward
 *
 */
public class NdcParser 
{
	String url = "http://ws.ndc.gov.tw/001/administrator/10/relfile/5781/6392/%E6%96%B0%E8%81%9E%E7%A8%BF%E9%99%84%E4%BB%B6%E6%95%B8%E5%88%97.xml";
	MacroEconomicDao dao;
	public MacroEconomicDao getDao() 
	{
		return dao;
	}
	public void setDao(MacroEconomicDao dao) 
	{
		this.dao = dao;
	}
	public void parse()
	{
		try
    	{
	        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	        factory.setNamespaceAware(true);
	        DocumentBuilder builder = factory.newDocumentBuilder();
	        Document doc = builder.parse(url);
	        NodeList listOfRows = doc.getElementsByTagName("Row");	
	        //總共有5個表，我只要第一個，對不起，這是爛招
	        for(int i = 1; i < listOfRows.getLength()/5; i++) 
	        {
	        	Node rowNode = listOfRows.item(i);
	        	if(rowNode.getNodeType() == Node.ELEMENT_NODE) 
	        	{
	                Element firstElement = (Element)rowNode;                              
	                //-------Cell	               
	                NodeList listOfCells = firstElement.getElementsByTagName("Cell");
	                String date = null;
	                for (int j = 0; j < listOfCells.getLength(); j++)
	                {	                		                	
		                Element firstCellElement = (Element)listOfCells.item(j);
		                //-------Data
		                NodeList listOfDatas = firstCellElement.getElementsByTagName("Data");
		                /************
		                 * 0 = 日期
		                 * 1 = 領先指標
		                 * 2 = 領先指標不含趨勢
		                 * 7 = 景氣對策分數
		                 * 8 = 景氣對策信號
		                 */
		                for (int k = 0; k < listOfDatas.getLength(); k++)
		                {		                			                
		                	Element firstDateElement = (Element)listOfDatas.item(k);
		                	NodeList textFNList = firstDateElement.getChildNodes();
		                	String data = ((Node)textFNList.item(0)).getNodeValue().trim();
		                	System.out.println("Data : " + ((Node)textFNList.item(0)).getNodeValue().trim());
		                	if (j == 0 && data.length() >= 10)
		                		date = data.substring(0, 10);
		                	if (j == 2 && date != null)		                		
		                		dao.update("NonTrendIndex", data, date);
		                	
		                }
	                }
	            }
	        }
    	}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}
	public static void main(String args[])
	{
		NdcParser parser = new NdcParser();
		parser.parse();
	}
}
