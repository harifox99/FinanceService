package org.bear.util;
/**
 * FRED API的基礎設定
 * @author edward
 *
 */
public class GetFredContentBase {
	public final String api_key = "ed82409405be66fda627007e4c5d77b1";
	public final String limit = "1000";
	public final String observation_init = "2016-01-01";
	String file_type = "txt";
	protected String urlStringHeader = "https://api.stlouisfed.org/fred/series/observations?";
	protected String urlString = "";
	String observation_start;
	String observation_end;

	public String getObservation_start() {
		return observation_start;
	}
	public void setObservation_start(String observation_start) {
		this.observation_start = observation_start;
	}
	public String getObservation_end() {
		return observation_end;
	}
	public void setObservation_end(String observation_end) {
		this.observation_end = observation_end;
	}
	public String getFile_type() {
		return file_type;
	}
	public void setFile_type(String file_type) {
		this.file_type = file_type;
	}

}
