package com.pdfutils;

import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

public class PDFPasswordHandler {

	/**
	 * Below method is used to read the text from PDF file and then validate if the
	 * text is present or not.
	 * @param pdfFileName
	 * @return
	 */
	@SuppressWarnings("finally")
	public static String readPdfContent(String pdfFileName) {
		PDDocument doc = null;
		int numberOfPages;
		String content = null;
		String expectedResults = "Test Title of Page";

		String currentDirectory = System.getProperty("user.dir");
		File pdftest1 = new File(currentDirectory + "\\PDFFile\\" + pdfFileName);
		 
		try {
			doc = PDDocument.load(pdftest1, "Password@123");
			
			numberOfPages = getPageCount(doc);
			System.out.println("The total number of pages " + numberOfPages);
			// content = new PDFTextStripper().getText(doc);
			
			//This class will take a pdf document and strip out all of the text and ignore the formatting and such.
			PDFTextStripper stripper = new PDFTextStripper();
			
			//This will set the first page to be extracted by this class.
			stripper.setStartPage(1);
			
			//This will set the last page to be extracted by this class.
			stripper.setEndPage(1);
			content = stripper.getText(doc);
			System.out.println(content);
			
			//Comparing the text from PDF file
			if(content.contains(expectedResults)) {
				System.out.println("Match Found In PDF File");
			}
			else {
				System.out.println("Content Not Found In PDF File");
			}
			doc.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return content;
		}
	}

	/**
	 * Below method is used for total number of pages available in the pdf document.
	 * @param doc
	 * @return
	 */
	public static int getPageCount(PDDocument doc) {
		// get the total number of pages in the pdf document
		int pageCount = doc.getNumberOfPages();
		return pageCount;
	}

	public static void main(String[] args) {
		//readPdfContent("progress_chart - Bar Chart - V0.2.pdf");
		readPdfContent("PDFPasswordProtected.pdf");
	}
}
