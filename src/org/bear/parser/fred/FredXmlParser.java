package org.bear.parser.fred;

import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
/**
 * 解出FRED的XML資料
 * @author edward
 *
 */
public class FredXmlParser 
{
    public HashMap<String, String> parse(String url, String key, String value)
    {
    	HashMap<String, String> map = new HashMap<String, String>();
    	try
    	{
	        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	        factory.setNamespaceAware(true);
	        DocumentBuilder builder = factory.newDocumentBuilder();
	        Document doc = builder.parse(url);
	
	        XPathFactory xpathFactory = XPathFactory.newInstance();
	        XPath xpath = xpathFactory.newXPath();
	        XPathExpression expr = xpath.compile("//observation");
	        Object result = expr.evaluate(doc, XPathConstants.NODESET);
	
	        NodeList nodes = (NodeList) result;
	        for (int i = 0; i < nodes.getLength(); i++)
	        {
	            Node currentItem = nodes.item(i);
	            map.put(currentItem.getAttributes().getNamedItem(key).getNodeValue(),
	            currentItem.getAttributes().getNamedItem(value).getNodeValue());
	            //String value = currentItem.getTextContent();
	        }            
        }
	    catch (Exception ex)
	    {
	    	ex.printStackTrace();
	    }
	    return map;
    }
}    