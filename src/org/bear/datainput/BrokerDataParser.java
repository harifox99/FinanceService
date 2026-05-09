package org.bear.datainput;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;
import java.util.Map.Entry;

import org.bear.dao.DealerBranchDao;
import org.bear.entity.DealerBranchEntity;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Semi-Auto Gen by Gemini，券商分點資料
 */
public class BrokerDataParser
{
    public void convertData() 
    {
	ApplicationContext context = new ClassPathXmlApplicationContext("config.xml");
	DealerBranchDao dao = (DealerBranchDao)context.getBean("dealerBranchDao");
	// 設定輸入檔案路徑
	String inputFilePath = "data/broker.txt";
	try
	{
	    // 讀取整個檔案內容 (由於含有特殊符號，建議使用 UTF-8 讀取)
	    String content = new String(Files.readAllBytes(Paths.get(inputFilePath)), StandardCharsets.UTF_8);

	    // 1. 清理頭尾的單引號
	    content = content.trim();
	    if (content.startsWith("'") && content.endsWith("'"))
	    {
		content = content.substring(1, content.length() - 1);
	    }

	    // 使用 LinkedHashSet 來儲存結果，既能「保持原本的排序」，又能「過濾重複的項目」
	    Map<String, String> resultSet = new HashMap<>();

	    // 2. 依照分號 ';' 將資料切分為不同的券商群組
	    String[] brokerGroups = content.split(";");

	    for (String group : brokerGroups)
	    {
		if (group.trim().isEmpty())
		    continue;

		// 3. 在群組內，依照驚嘆號 '!' 切分為各個分公司
		String[] branches = group.split("!");
		if (branches.length == 0)
		    continue;

		// 4. 取得該群組的「主代碼 (Group Code)」
		// 群組的第一筆資料 (例如 "1030,土銀")，逗號前面的即為主代碼
		String[] firstBranchParts = branches[0].split(",");
		String groupCode = firstBranchParts[0].trim().replaceAll("'", "");

		// 5. 處理該群組內的所有分公司
		for (String branch : branches)
		{
		    String[] parts = branch.split(",");
		    if (parts.length >= 2)
		    {
			String code = parts[0].trim().replaceAll("'", "");
			String name = parts[1].trim().replaceAll("'", "");;

			// 組合出您要的格式：群組代碼-原始代碼,名稱
			String formattedLine = groupCode + "-" + code + "," + name;
			System.out.println(formattedLine);			
			// 加入 Set 中 (自動忽略重複項，如連續兩次的 1030-1030,土銀)
			resultSet.put(groupCode + "-" + code, name);
		    }
		}
	    }
	    
	    // 6. 將結果輸出到控制台 (或可自行改為寫入新檔案)
	    System.out.println("--- 轉換結果 ---");
	    for (Entry<String, String> entry : resultSet.entrySet()) 
	    {
		System.out.println(entry.getKey() + "," + entry.getValue());		
		String name = entry.getValue();
		String[] code = entry.getKey().split("-");
		DealerBranchEntity entity = new DealerBranchEntity();
	        entity.setCode(code[1]);
	        entity.setName(name);
	        entity.setGroupCode(code[0]);
	        dao.insert(entity);
	    }
	} 
	catch (NoSuchFileException e)
	{
	    System.err.println("找不到檔案，請確認 broker.txt 是否放在正確路徑。");
	} 
	catch (IOException e)
	{
	    System.err.println("讀取檔案時發生錯誤: " + e.getMessage());
	}
    }
    public static void main(String[] args)
    {
	BrokerDataParser parser = new BrokerDataParser();
	parser.convertData();
    }
}