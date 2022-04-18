package com.pdfutils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.text.PDFTextStripper;

public class PDFLineValidation {
	
	
	/**
	 * Below method is to comparing two PDF files.
	 * It is used to read the text from PDF file and then validate if the text is present or not.
	 * @param actualPDFFile
	 * @param expectedPDFFile
	 */
	public static void comparePDF(String actualPDFFile, String expectedPDFFile) {
		
		String currentDirectory = System.getProperty("user.dir");
		File pdftest1= new File(currentDirectory+"\\PDFFile\\"+actualPDFFile);
		File pdftest2= new File(currentDirectory+"\\PDFFile\\"+expectedPDFFile);
		
		int getfilename1 = actualPDFFile.lastIndexOf(".");
		String filename1 = actualPDFFile.substring(0, getfilename1);
		int getfilename2 = expectedPDFFile.lastIndexOf(".");
		String filename2 = expectedPDFFile.substring(0, getfilename2);
				
		String message =null;
		try {
		//Creating the object for the files
		PDDocument pdf1 = PDDocument.load(pdftest1);
	    PDDocument pdf2 = PDDocument.load(pdftest2);
	    PDPageTree pdf1pages = pdf1.getDocumentCatalog().getPages();    	 
	    PDPageTree pdf2pages = pdf2.getDocumentCatalog().getPages();
	    
	    PDFTextStripper pdfStripper = new PDFTextStripper();	    
        int issueNum = 1;
        int PageNum=1;
		for (@SuppressWarnings("unused") PDPage pdPage : pdf1pages) {
			int lineNum = 1;
			pdfStripper.setStartPage(PageNum);
			pdfStripper.setEndPage(PageNum);
			String pdf1PageText = pdfStripper.getText(pdf1);
			String pdf2PageText = pdfStripper.getText(pdf2);
			createFileAndAddContent(pdf1PageText, filename1);
			createFileAndAddContent(pdf2PageText, filename2);

			BufferedReader br1 = new BufferedReader(new FileReader(filename1 + ".txt"));
			BufferedReader br2 = new BufferedReader(new FileReader(filename2 + ".txt"));

			String line1 = br1.readLine();

			String line2 = br2.readLine();
			
			while (line1 != null || line2 != null) {

				if (line1.equals(line2)) {
//					 System.out.println("Pass "+lineNum+":"+ " Expected - "+line1+ " Actual - " +line2);
				} else {
					System.out.println("PageNum " + PageNum+" lineNum " + lineNum + " Issue " + issueNum + ":" + " Expected - " + line1
							+ " Actual - " + line2);
					issueNum++;
				}
				line1 = br1.readLine();
				line2 = br2.readLine();
				lineNum++;
			}
			br1.close();br2.close();
			PageNum++;
		}
	    if (pdf1pages.getCount() != pdf2pages.getCount())
        {
            message = "Number of pages in the files ("+actualPDFFile+" , "+expectedPDFFile+") do not match. actualPDFFile has "+pdf1pages.getCount()+" no pages, while expectedPDFFile has "+pdf2pages.getCount()+" no of pages";
            
        }
	    else
	    {
	    	message ="Number of pages in the files ("+actualPDFFile+" , "+expectedPDFFile+") is matched";
	    	System.out.println(message);
	    }}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
		
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
			//System.out.println("File created: " + myObj.getName());
			FileWriter myWriter = new FileWriter(myObj.getName());
			myWriter.write(textLine);
			myWriter.close();
			//System.out.println("Successfully wrote to the file.");
		} catch (IOException e) {
			//System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args) {	
		comparePDF("accelq-test1.pdf", "accelq-test2.pdf");
	}

}
