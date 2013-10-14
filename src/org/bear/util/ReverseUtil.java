package org.bear.util;
import java.util.ArrayList;
import java.util.List;
public class ReverseUtil {
	public static <T> List<T> reverse(List<T> objectList)
	{
		List <T> reverseList = new ArrayList<T>();
		for (int i = objectList.size()-1; i >= 0; i--)
		{
			reverseList.add(objectList.get(i));
		}
		return reverseList;
	}
	
}
