package com.testdata;

public class DBManager {
	
	public static DBActionEngine dbAction;
	
	public static void InitializeDB(String dbType){
		
		switch (dbType) {
		case "Excel":
			dbAction = new ExcelHandler();
			break;

		default:
			break;
		}
	}
	
	public static String getData(String colName) {
		return dbAction.getData(colName);
	}
	
	public static void setData(String colName,  String cellValue) {
		dbAction.setData(colName, cellValue);
	}

}
