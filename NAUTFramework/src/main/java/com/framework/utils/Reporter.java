package com.framework.utils;

import java.io.File;
import java.io.IOException;

import io.cucumber.java.Scenario;


public class Reporter {
	public static InheritableThreadLocal<Scenario> TScenario = new InheritableThreadLocal<Scenario>();

	private static int fileCount = 0;

	/**
	 * Added desired message to cucumber report
	 * 
	 */
	public synchronized static void addStepLog(String message) {
		//TScenario.get().write(message);
		TScenario.get().log(message);		
	}
	
	/**
	 * Add web application screenshot (Display in step)
	 * @throws IOException 
	 * 	
	 */
	public static void addScreenCapture() throws IOException {

		byte[] screenshot = CaptureScreenshot.screenCaptureByte();		
		TScenario.get().attach(screenshot, "image/png", "Error Screenshot");		
	}

	/**
	 * Add web application screenshot (current screen)
	 * 
	 */
	public synchronized static void addScreenCapture2() {

		String screenshotName = CaptureScreenshot.screenCapture(null);

		String html = "<div style=\"margin-bottom: 5px\">"
				+ "<label style=\"color: #1E90FF\">Click on image to see Screenshot !!</label>" + "<a href=\""
				+ screenshotName
				+ "\" target=\"_blank\" style=\"float:right;\"><img src=\"core-image-screenshot.png\" style=\"background-color:aliceblue;\"/></a>\r\n"
				+ "</div>";

		addStepLog(html);
	}

	/**
	 * Take Web screenshot
	 * "Click on image to see Failure Screenshot !!", "#ff1a1a"
	 */
	public synchronized static void addScreenCapture(String message, String color) {

		String screenshotName = CaptureScreenshot.screenCapture(null);

		String html = "<div style=\"margin-bottom: 5px\">"
				+ "<label style=\"color: "+color+"\">"+message+"</label>" + "<a href=\""
				+ screenshotName
				+ "\" target=\"_blank\" style=\"float:right;\"><img src=\"core-image-screenshot.png\" style=\"background-color:aliceblue;\"/></a>\r\n"
				+ "</div>";

		addStepLog(html);
	}

	/**
	 * Add web application screenshot (entire application)
	 * 
	 */
	public synchronized static void addEntireScreenCaptured() {

		String screenshotName = CaptureScreenshot.captureEntireScreen();

		String html = "<div style=\"margin-bottom: 5px\">"
				+ "<label style=\"color: #1E90FF\">Click on image to see Screenshot !!</label>" + "<a href=\""
				+ screenshotName
				+ "\" target=\"_blank\" style=\"float:right;\"><img src=\"core-image-screenshot.png\" style=\"background-color:aliceblue;\"/></a>\r\n"
				+ "</div>";

		addStepLog(html);
	}
	
	
	/**
	 * Adding Request or Response in Extent report
	 * 
	 */
	public synchronized static void addResponse(String content, String type, String message) {

		PropertyFileUtils property = new PropertyFileUtils("./src/test/resources/extent.properties");
		String reportDirPath = property.getProperty("extent.reporter.spark.scr");

		/* default file extension is .txt */
		String extension = ".txt";

		type = type.trim();
		if (type.equalsIgnoreCase("json") || type.equalsIgnoreCase(".json")) {
			extension = ".json";
		} else if (type.equalsIgnoreCase("xml") || type.equalsIgnoreCase(".xml")) {
			extension = ".xml";
		} else if (type.equalsIgnoreCase("yaml") || type.equalsIgnoreCase(".yaml") || type.equalsIgnoreCase("yml")
				|| type.equalsIgnoreCase(".yml")) {
			extension = ".yml";
		}

		String fileName = "file-" + (++fileCount) + extension;
		String filePath = reportDirPath + "/" + fileName;

		FileManager.createFile(filePath);

		FileManager.write(filePath, content);

		String html = "";

		if (message == null || message.length() == 0) {
			html = "<a href=\"" + fileName
					+ "\" target=\"_blank\" style=\"float: right\"><img src=\"core-image-hper-link.png\" style=\"background-color: aliceblue\"></a>";
		} else {
			html = "<lable><font color=#1E90FF>" + message + "</font></lable>" + "<a href=\"" + fileName
					+ "\" target=\"_blank\" style=\"float: right\"><img src=\"core-image-hper-link.png\" style=\"background-color: aliceblue\"></a>";
		}

		Reporter.addStepLog(html);
	}

	/**
	 * Cleaning Report folder
	 * 
	 */
	public static void cleanReportFolder() {

		PropertyFileUtils property = new PropertyFileUtils("./src/test/resources/extent.properties");
		String partialSparkDirPath = "./" + property.getProperty("extent.reporter.spark.out");
		
		File dirPath = new File(partialSparkDirPath);
		
		if(dirPath.exists()) {
			FileManager.deleteAllFilesInFolder(partialSparkDirPath);
		}
		

	}

//	/**
//	 * Copies all core file to Spark folder
//	 * 
//	 */
//	public static void copyCoreFiles() {
//
//		PropertyFileUtils property = new PropertyFileUtils("./src/test/resources/extent.properties");
//		String toDirPath = "./" + property.getProperty("extent.reporter.spark.out");
//
//		String fromDirPath = "./src/main/resources/core-images";
//		File fromDir = new File(fromDirPath);
//
//		File[] listFiles = fromDir.listFiles();
//
//		for (File file : listFiles) {
//			try {
//				Files.copy(file, new File(toDirPath + "/" + file.getName()));
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//	}
//
//	/**
//	 * Screen shot is normally stored in target/cucumber-html-report folder moving
//	 * it to Spark folder
//	 *
//	 */
//	public synchronized static void moveScreenshotToSparkfolder() {
//
//		int sparkScreenshotCounter = 1;
//
//		PropertyFileUtils property = new PropertyFileUtils("./src/test/resources/extent.properties");
//
//		String sparkDirPath = "./" + property.getProperty("extent.reporter.spark.out");
//
//		String screenshotDirPath = "./target/cucumber-html-report";
//
//		/*
//		 * in cucumber screenshot counter starts from 0 i.e. 'embedeed0.png' however, in
//		 * spark screenshot counter starts from 1 not 0 for e.g. 'embedded1.png'
//		 */
//
//		File dirScreenshot = new File(screenshotDirPath);
//
//		File[] listFiles = dirScreenshot.listFiles();
//
//		/* moving screen shots from cucumber-html-report to Spark folder */
//		for (int index = 0; index < listFiles.length; index++) {
//
//			if (listFiles[index].getName().startsWith("embedded")) {
//
//				listFiles[index].renameTo(new File(sparkDirPath + "/embedded" + sparkScreenshotCounter + ".png"));
//				sparkScreenshotCounter++;
//
//			}
//
//		}
//
//	}
//
//	/**
//	 * Providing relative path to Extent Report
//	 * 
//	 */
//	public static void updateRelativeScreenshotPath() {
//
//		String rootDir = System.getProperty("user.dir");
//
//		PropertyFileUtils extentConfig = new PropertyFileUtils("./src/test/resources/extent.properties");
//		String partialSparkFldPath = extentConfig.getProperty("extent.reporter.spark.out");
//
//		String sparkDirPath = rootDir + "/" + partialSparkFldPath;
//
//		String originalIndexFilePath = sparkDirPath + "/" + "index.html";
//
//		/*----*/
//		File originalFile = null;
//		File tempFile = null;
//
//		BufferedReader bufReader = null;
//		BufferedWriter bufWriter = null;
//
//		boolean isIndexFileExist = FileManager.isFileExists(originalIndexFilePath);
//
//		if (isIndexFileExist) {
//
//			try {
//
//				originalFile = new File(originalIndexFilePath);
//
//				tempFile = new File(sparkDirPath + "/index-temp.html");
//				tempFile.createNewFile();
//
//				FileReader reader = new FileReader(originalIndexFilePath);
//				bufReader = new BufferedReader(reader);
//
//				FileWriter writer = new FileWriter(tempFile);
//				bufWriter = new BufferedWriter(writer);
//
//				String line;
//				while ((line = bufReader.readLine()) != null) {
//					if (line.contains(partialSparkFldPath)) {
//						line = line.replace(partialSparkFldPath, "");
//					}
//					bufWriter.append(line);
//				}
//			} catch (Exception e) {
//				ExceptionHandler.handleException(e);
//			} finally {
//				try {
//					bufReader.close();
//					bufWriter.close();
//					originalFile.delete();
//					tempFile.renameTo(new File(sparkDirPath + "/index.html"));
//				} catch (Exception e) {
//					ExceptionHandler.handleException(e);
//				}
//			} // try block end
//
//		} else {
//			System.out.println(
//					"!!! Report NOT Generated !!! \nPlease check the command and its different parameters like tags");
//		}
//	}
//
//	/**
//	 * Report Customization Should only be used after Suite execution.
//	 * 
//	 */
//	public static void reportCustomization() {
//
//		/* coping core file to target/.../spark folder */
//		copyCoreFiles();
//
//		/*
//		 * moving screenshot from 'target/cucumber-html-report' to
//		 * 'target/cucumber-reports/Spark'
//		 */
//		moveScreenshotToSparkfolder();
//
//		/* updating relative screenshot path */
//		updateRelativeScreenshotPath();
//	}
}
