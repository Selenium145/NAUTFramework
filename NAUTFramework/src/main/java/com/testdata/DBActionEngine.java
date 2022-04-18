package com.testdata;

public interface DBActionEngine {
	
	public String getData(String columnName);
	public void setData(String columnName, String cellValue);

}
