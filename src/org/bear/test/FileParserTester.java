package org.bear.test;

import org.bear.parser.file.FileParser;
import org.bear.util.GetURLContent;
/**
 * File Parser Test
 * @author edward
 *
 */
public class FileParserTester {
	GetURLContent content;
	FileParser fileParser;
	//String url = "https://stock-ai.com/ddl?s=twTT&r=tdrK2P3aCe";
	public FileParserTester(String url)
	{
		content = new GetURLContent(url);
		fileParser = new FileParser();
		fileParser.getResponse(content.getContent(null));
	}
	public static void main(String args[])
	{
		new FileParserTester("https://smart.tdcc.com.tw/opendata/getOD.ashx?id=1-5");
	}
}
