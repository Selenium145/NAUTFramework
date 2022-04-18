package com.testdata;

import java.util.HashMap;
import java.util.Map;

import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;
import com.framework.utils.GlobalVariables;

public class ExcelHandler implements DBActionEngine{

	private static Map<String, Map<String, String>> TestData = null;
	
	/*
	 * Method to return data table as Map
	 * 
	 * @author 10675365
	 * 
	 */
	private static Map<String, Map<String, String>> getTestDataInMap(String testDataFile, String sheetName)
			throws Exception {

		Map<String, Map<String, String>> TestDataInMap = new HashMap<String, Map<String, String>>();
		
		String PrimaryKey = null;
		String query = null;
		query = String.format("SELECT * FROM %s WHERE Run='Yes'", sheetName);
		Fillo fillo = new Fillo();
		Connection conn = null;
		Recordset recordset = null;

		try {
			conn = fillo.getConnection(testDataFile);
			recordset = conn.executeQuery(query);
			while (recordset.next()) {
				Map<String, String> DataRowInMap = new HashMap<String, String>();
				for (String field : recordset.getFieldNames()) {
					DataRowInMap.put(field, recordset.getField(field));
					if (field.equals("FeatureName")) {
						PrimaryKey = recordset.getField(field);
					}					
				}
				TestDataInMap.put(PrimaryKey, DataRowInMap);				
			}			
		} catch (FilloException e) {
			e.printStackTrace();
			throw new Exception("Test data file not exist . . . ");
		}
		conn.close();
		return TestDataInMap;
	}
	
	/*
	 * Method to return singleton data instance
	 * 
	 * @author 10675365
	 * 
	 */
	public static Map<String, Map<String, String>> getDataInstance() {		
		return TestData;
	}

	/*
	 * Method to return data cell value
	 * 
	 * @author 10675365
	 * 
	 */
	@Override
	public String getData(String columnName) {
		Map<String, String> dataRow = null;
		String dataCell = null;
		
		dataRow = ExcelHandler.getDataInstance().get(GlobalVariables.currentFeature.get());
		dataCell = dataRow.get(columnName);

		return dataCell;
	}

	/*
	 * Method to update data cell value
	 * 
	 * @author 10675365
	 * 
	 */
	@Override
	public void setData(String columnName, String cellValue) {

		String strQuery = null;
		Connection connection = null;
		Fillo fillo = new Fillo();

		try {
			connection = fillo.getConnection("src/test/resources/TestData/StandardDataTable.xlsx");
			strQuery = String.format("Update %s Set %s=%s where FeatureName=%s",
					GlobalVariables.configProp.getProperty("environment"), columnName, "'"+cellValue+"'", "'"+GlobalVariables.currentFeature.get()+"'");
			connection.executeUpdate(strQuery);
			connection.close();
		} catch (FilloException e) {
			e.printStackTrace();
		}

	}

	/*
	 * Method to return initialize DB data
	 * 
	 * @author 10675365
	 * 
	 */	
	public ExcelHandler() {
		if (TestData == null)
			try {
				TestData = ExcelHandler.getTestDataInMap("src/test/resources/TestData/StandardDataTable.xlsx",
						GlobalVariables.configProp.getProperty("environment"));	
				GlobalVariables.featureList = TestData.keySet();				
			} catch (Exception e) {
				e.printStackTrace();
			}		
	}

}
