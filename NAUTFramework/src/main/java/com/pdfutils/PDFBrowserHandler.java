package com.pdfutils;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class PDFBrowserHandler {

	/**
	 * Below method is used to read the text from PDF file and then validate if the
	 * text is present or not.
	 * @param pdfFileName
	 * @return
	 */
	@SuppressWarnings("finally")
	public static String readPdfContent(String pdfFileName) {
		WebDriver driver;
		String getURL = null;
		URL pdfUrl = null;
		InputStream in = null;
		BufferedInputStream bf = null;
		PDDocument doc = null;
		int numberOfPages;
		String content = null;
		String expectedResults = "The pdf995 suite of products - Pdf995, PdfEdit995, and Signature995";

		// Initialize Browser
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		String currentDirectory = System.getProperty("user.dir");

		// Specify the path of the pdf file to open in browser
		driver.get(currentDirectory + "\\PDFFile\\" + pdfFileName);
		driver.manage().window().maximize();
		getURL = driver.getCurrentUrl();
		
		try {
			
			pdfUrl = new URL(getURL);
			in = pdfUrl.openStream();
			bf = new BufferedInputStream(in);
			doc = PDDocument.load(bf);
			numberOfPages = getPageCount(doc);
			System.out.println("The total number of pages " + numberOfPages);

			// This class will take a pdf document and strip out all of the text and ignore
			// the formatting and such.
			PDFTextStripper stripper = new PDFTextStripper();

			// This will set the first page to be extracted by this class.
			stripper.setStartPage(1);

			// This will set the last page to be extracted by this class.
			stripper.setEndPage(1);

			content = stripper.getText(doc);
			System.out.println(content);

			// Comparing the text from PDF file
			if(content.contains(expectedResults)) {
				System.out.println("Match Found In PDF File");
			}
			else {
				System.out.println("Content Not Found In PDF File");
			}
		
			doc.close();
			driver.quit();

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
		readPdfContent("SamplePDF.pdf");
	}
}
