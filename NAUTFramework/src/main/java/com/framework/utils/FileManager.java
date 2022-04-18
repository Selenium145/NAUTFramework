package com.framework.utils;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import org.apache.logging.log4j.Logger;
import javax.imageio.ImageIO;

/*
 * Class to support create, edit & delete file system
 * 
 * @author 10675365
 * 
 */

public class FileManager {

	private static Logger log = LoggerHelper.getLogger(FileManager.class);

	public static void deleteAllFilesInFolder(String folderPath) {
		String status = "FAIL";
		try {
			File folder = new File(folderPath);

			/* checking if given path if of directory and folder exists or not */
			if (folder.exists() && folder.isDirectory()) {
				File[] listFiles = folder.listFiles();

				/* checking if file with in the folder exists or not */
				if (listFiles.length > 0) {
					for (File file : listFiles) {
						file.delete();
					}
				}

			}

			status = "PASS";
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		} finally {
			Report.printOperation("Delete All Files");
			Report.printStatus(status);
		}
	}

	/**
	 * public util to delete file
	 * 
	 */
	public static void deleteFile(String filePath) {
		String status = "FAIL";
		try {
			File file = new File(filePath);
			file.delete();
			status = "PASS";
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		} finally {
			Report.printOperation("Delete File");
			Report.printStatus(status);
		}
	}

	/**
	 * public util to get absolute download filepath specified in config.properties
	 * 
	 * @return absolutepath
	 */
	public static String downloadFolderFilePath() {
		PropertyFileUtils properties = new PropertyFileUtils(System.getProperty("user.dir") + "/config.properties");
		File folder = new File(properties.getProperty("downloadFolder"));
		if (!folder.exists()) {
			folder.mkdir();
		}
		return folder.getAbsolutePath();
	}

	/**
	 * public util to get absolute download filepath user specified
	 * 
	 * @return string folderpath
	 */
	public static String downloadFolderFilePath(String folderPath) {
		File folder = new File(folderPath);
		if (!folder.exists()) {
			folder.mkdir();
		}
		return folder.getAbsolutePath();
	}

	/**
	 * public utils to check if file exists or not
	 * 
	 */
	public static boolean isFileExists(String filePath) {
		return new File(filePath).exists();
	}

	/**
	 * public file util to check if file exist or not
	 * 
	 */
	public static float getFileSize(String filePath) {
		return new File(filePath).length();

	}

	/**
	 * write to file
	 * 
	 */
	public static void write(String filePath, String writestr) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
			writer.write(writestr);
			writer.close();

		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		}
	}

	/**
	 * Append character string in existing file
	 * 
	 */
	public static void append(String filePath, String writestr) {
		try {
			Files.write(Paths.get(filePath), writestr.getBytes(), StandardOpenOption.APPEND);
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		}
	}

	/**
	 * Read file and convert it to string
	 * 
	 */
	public static String readFile(String filePath) {
		StringBuffer content = new StringBuffer();
		BufferedReader bufferedReader = null;
		if (isFileExists(filePath)) {
			try {
				bufferedReader = new BufferedReader(new FileReader(filePath));
				String line = null;

				while ((line = bufferedReader.readLine()) != null) {
					content.append(line).append("/n");
				}
			} catch (Exception e) {
				ExceptionHandler.handleException(e);
				e.printStackTrace();
			} finally {
				try {
					bufferedReader.close();
				} catch (Exception e) {
					ExceptionHandler.handleException(e);
					e.printStackTrace();
				}
			}
		}
		return content.toString();
	}

	/**
	 * creating a new file in a given format
	 * 
	 */
	public static boolean createFile(String filePath) {
		File file = new File(filePath);
		try {
			return file.createNewFile();
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		}
		return false;
	}

	/**
	 * Search file if exists in specific directory
	 * 
	 */
	public static String searchFile(String dirPath, String fileName) {

		File dir = new File(dirPath);

		File[] listFiles = dir.listFiles();

		String filePath = null;

		for (File file : listFiles) {

			if (file.isDirectory()) {

				filePath = searchFile(file.getAbsolutePath(), fileName);

				if (filePath != null) {
					break;
				}

			} else {
				if (file.getName().equals(fileName)) {

					filePath = file.getAbsolutePath();
					break;
				}
			}
		}

		return filePath;
	}

	/**
	 * Code from String to Base64
	 * 
	 */
	public static String base64Encode(String value) {
		return Base64.getEncoder().encodeToString(value.getBytes());
	}

	/**
	 * Decode from Base64 to String
	 * 
	 */
	public static String base64Decode(String encodedValue) {
		byte[] decodedBytes = Base64.getDecoder().decode(encodedValue);
		return new String(decodedBytes);
	}

	/**
	 * Copy a file from source to destination
	 * 
	 */
	public static void copy(String src, String dest) {
		try {
			com.google.common.io.Files.copy(new File(src), new File(dest));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Convert BufferedImage to byte[]
	 * 
	 */
	public static byte[] bufferImageToByte(BufferedImage image) {
		ByteArrayOutputStream baos = null;
		byte[] imageInByte = null;
		try {

			baos = new ByteArrayOutputStream();
			ImageIO.write(image, "jpg", baos);
			baos.flush();
			imageInByte = baos.toByteArray();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				baos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return imageInByte;

	}

	/**
	 * Upload file using auto it
	 * 
	 */
	public static void fileUploadUsingAutoIt(String absoluteFilePath) throws IOException {
		/*
		 * wrapping filePath into double quote to over come space in between directory
		 * or file name
		 */
		File file = new File(absoluteFilePath);
		File parentFile = file.getParentFile();
		String parent = parentFile.getAbsolutePath();

		/* adding '\' at the end of parent dir path */
		parent = "\"" + parent + "\\" + "\"";
		log.info("Upload file Parent path: " + parent);

		String fileName = file.getName();
		fileName = "\"" + fileName + "\"";
		log.info("Upload file name: " + fileName);

		List<String> cmds = Arrays.asList("cmd.exe", "/C", "start", "./src/main/resources/exes/FileUpload(x64).exe",
				parent, fileName);

		ProcessBuilder builder = new ProcessBuilder(cmds);
		Process start = builder.start();		
		start.destroyForcibly();

	}

}
