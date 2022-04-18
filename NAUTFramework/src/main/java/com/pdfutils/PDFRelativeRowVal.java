package com.pdfutils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.text.PDFTextStripper;

public class PDFRelativeRowVal {

	
	/**
	 * Create new .txt file and delete the previous file if present then copy the content into the new file.
	 * @param textLine
	 * @param filename
	 */
	public static void createFileAndAddContent(String textLine, String filename) {

		try {
			File myObj = new File(filename + ".txt");
			if (myObj.exists()) {
				myObj.delete();
			} else {
				System.out.println("***No File To Delete***");
			}

			myObj.createNewFile();
			System.out.println("File created: " + myObj.getName());
			FileWriter myWriter = new FileWriter(myObj.getName());
			myWriter.write(textLine);
			myWriter.close();
			System.out.println("Successfully wrote to the file.");
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Delete .txt file from the project location 
	 * @param textLine
	 * @param filename
	 */
	public static void deleteFileFromProject(String filename) {

		try {
			File myObj = new File(filename);
			if (myObj.exists()) {
				myObj.delete();
				System.out.println("File Deleted: " + myObj.getName());
			} else {
				System.out.println("***No File To Delete***");
			}
		} catch (Exception e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}

	}
	
	/**
	 * Read text from the PDF file and store them in .txt file and then read line by line to 
	 * get specific value for the country
	 * @param actualPDFFile
	 * @param countryName
	 * @param index
	 * @return
	 */
	public static String findValueFromPDFFile(String actualPDFFile, String countryName, int index) {

		int getfilename = actualPDFFile.lastIndexOf(".");
		String filename = actualPDFFile.substring(0, getfilename);		
		List<String> filesListInDir = new ArrayList<String>();
		String currentDirectory = System.getProperty("user.dir");
		File pdftest1 = new File(currentDirectory + "\\PDFFile\\" + actualPDFFile);
		String value = null;

		try {
			// Creating the object for the files
			PDDocument pdf1 = PDDocument.load(pdftest1);

			PDPageTree pdf1pages = pdf1.getDocumentCatalog().getPages();
			System.out.println("Number of pages in the '"+actualPDFFile+"' file is "+pdf1pages.getCount());

			// This class will take a pdf document and strip out all of the text and ignore
			// the formatting and such.
			PDFTextStripper pdfStripper = new PDFTextStripper();
			String pdf1PageText = pdfStripper.getText(pdf1);

			createFileAndAddContent(pdf1PageText, filename);
			BufferedReader br = new BufferedReader(new FileReader(filename + ".txt"));
			String line = br.readLine();
			while (line != null) {
				line = br.readLine();
				// process the line     
				//System.out.println(line);
				if (line.contains(countryName)) {
					// splits the string based on whitespace
					String[] words = line.split("\\s");

					// using foreach/enhance loop to print elements of string array
					for (String w : words) {
						//System.out.println(w);
							if (w != null) {
								//Add element to the list
							filesListInDir.add(w.trim());
							}

					}
//					System.out.println("Available List: "+filesListInDir);
//					System.out.println("Size of the List: "+filesListInDir.size());
					value = filesListInDir.get(index);
//					System.out.println("Percentage of "+countryName+": "+filesListInDir.get(index));
					break;
				}
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			deleteFileFromProject(filename+ ".txt");
		}
		return value;
	}
		
		public static void main(String[] args) {
			//String output=findValueFromPDFFile("progress_chart - Bar Chart - V0.2.pdf","Africa",4);
			String output=findValueFromPDFFile("progress_chart - Bar Chart - V0.2.pdf","Australia",3);
			System.out.println("Available value is "+output);
		}
}
