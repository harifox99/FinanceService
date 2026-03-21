package org.bear.parser.fred;

import java.util.HashMap;

public interface UrlString {
	public void setUrlString(String series_id, String observation_start, HashMap<String, String> parameterMap);
}
