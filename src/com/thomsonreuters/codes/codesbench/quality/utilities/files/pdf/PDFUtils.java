package com.thomsonreuters.codes.codesbench.quality.utilities.files.pdf;

import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.files.FileUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.files.pdf.elements.PDFElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.files.pdf.extensions.PDFFontStripperByArea;
import com.thomsonreuters.codes.codesbench.quality.utilities.landingStrips.LandingStripsFTPClient;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.interactive.action.PDActionGoTo;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotation;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.destination.PDNamedDestination;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.destination.PDPageDestination;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.PDDocumentOutline;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.PDOutlineItem;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static com.thomsonreuters.codes.codesbench.quality.utilities.TestSetupEdge.logger;
import static com.thomsonreuters.codes.codesbench.quality.utilities.files.pdf.elements.PDFElements.*;


/**
 * This class stores various utilities to use in the pdf. I recommend printing out the page numbers in the error messages for debugging purposes
 */
public class PDFUtils
{
	private static int currentPageNumber;

	private static PDDocument PDF;
	private static PDPage currentPDPage;

	public static String getPdfContent(File file)
	{
		String content = "";
		Assertions.assertNotNull(file,"File is null when it should not be");
		try
		{
			PDDocument pdfReport = PDDocument.load(file);
			PDFTextStripper pdfStripper = new PDFTextStripper();
			content = pdfStripper.getText(pdfReport);
			pdfReport.close();
		}
		catch(IOException e)
		{
			logger.warning("Error getting pdf content");
			e.printStackTrace();
		}
		return content;
	}

//----------------------------------------------------------------------------------------------------------------//
//-----------------------------------------------NAVIGATION-METHODS-----------------------------------------------//
//----------------------------------------------------------------------------------------------------------------//

	public static void createPDDocument(File file)
	{
		try
		{
			PDF = PDDocument.load(file);
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}

	}

	//TODO clean this up to work with other methods
	//Open pdf separately and use go to page makes it more dynamic
	//This can probably be taken out eventually because most tests will test page by page and the pdf already gets loaded in the previous method
	//The tests can just use the createpddocument method to load the pdf once and use the switch to page function after that
	//Going to say this is deprecated
	public static void openPDFAtPage(int pageToOpen, File file)
	{
		currentPageNumber = pageToOpen - 1;

		try
		{
			PDF = PDDocument.load(file);
			currentPDPage = PDF.getPage(currentPageNumber);
		}
		catch (IOException e)
		{
			e.printStackTrace();
			Assertions.fail(String.format("Failed to open PDF at page %s", pageToOpen));
		}
	}

	public static void switchToPage(int pageToSwitchTo)
	{
		if (pageToSwitchTo >= 1 && pageToSwitchTo <= getNumberOfPages())
		{
			currentPageNumber = pageToSwitchTo - 1;
			currentPDPage = PDF.getPage(currentPageNumber);
		}
		else
		{
			Assertions.fail("You're attempting to access a page that is out of bounds");
		}
	}

	public static List<PDAnnotation> getPageAnnotations(PDPage page)
	{
		try
		{
			return page.getAnnotations();
		}
		catch (IOException e)
		{
			Assertions.fail("Unable to get PDF Elements such as links, checkboxes, etc." + e.getMessage());
		}
		return null;
	}

	//Is probably deprecated
	public static void switchToNextPage()
	{
		switchToPage(currentPageNumber + 2);
	}

	public static void closePDF()
	{
		if (PDF != null)
		{
			try
			{
				PDF.close();
			}
			catch (IOException e)
			{
				Assertions.fail("Failed to close PDF");
			}
		}
	}
	public static File savePDFWithName(String auditReportUuid, String name) {
		String target = String.format(AUDIT_LANDING_STRIP_PDF, auditReportUuid);
		String location = String.format(AUDIT_SAVING_LOCATION_PDF, name);
		File file = null;
		try {
			//File file = null;
			file = new File(location);
			FileWriter writer = new FileWriter(file);
			writer.write("This is a file forAuditsRegressionTest.");
			writer.close();
			DateAndTimeUtils.takeNap(DateAndTimeUtils.FIFTEEN_SECONDS);
			DateAndTimeUtils.takeNap(DateAndTimeUtils.FIFTEEN_SECONDS);
			DateAndTimeUtils.takeNap(DateAndTimeUtils.FIFTEEN_SECONDS);
			LandingStripsFTPClient client = new LandingStripsFTPClient();
			client.connectToServer();
			file = client.download(target, location);
			client.disconnectFromServer();
			FileUtils.waitForFileToExist(location, DateAndTimeUtils.FIVE_SECONDS);

		} catch (IOException e) {
			e.printStackTrace();
		}
		return file;
	}

	//These methods are helper methods that most tests will use to switch to different pages or get text from a page
	public static int getNumberOfPages()
	{
		return PDF.getNumberOfPages();
	}

	public static int getCurrentPageNumber()
	{
		return currentPageNumber + 1;
	}

	public static PDPage getCurrentPDPage() { return currentPDPage; }

	public static PDDocument getCurrentPDDocument() { return PDF; }

	//indexOfNode =0 returns page number of summary
	public static int getPageNumberOfNode(int indexOfNode)
	{
		PDDocumentOutline PDFOutline = PDF.getDocumentCatalog().getDocumentOutline();
		int count =0;
		PDOutlineItem currItem = PDFOutline.getFirstChild();
		PDOutlineItem nextItem = null;
		while(true)
		{
			if(count == indexOfNode)
			{
				try
				{
					if (currItem.getDestination() instanceof PDPageDestination)
					{
						PDPageDestination pd = (PDPageDestination) currItem.getDestination();
						return pd.retrievePageNumber() + 1;
					}
					else if (currItem.getDestination() instanceof PDNamedDestination)
					{
						PDPageDestination pd = PDF.getDocumentCatalog().findNamedDestinationPage((PDNamedDestination) currItem.getDestination());
						if (pd != null)
						{
							return pd.retrievePageNumber() + 1;
						}
					}

					if (currItem.getAction() instanceof PDActionGoTo)
					{
						PDActionGoTo gta = (PDActionGoTo) currItem.getAction();
						if (gta.getDestination() instanceof PDPageDestination)
						{
							PDPageDestination pd = (PDPageDestination) gta.getDestination();
							return pd.retrievePageNumber() + 1;
						}
						else if (gta.getDestination() instanceof PDNamedDestination)
						{
							PDPageDestination pd = PDF.getDocumentCatalog().findNamedDestinationPage((PDNamedDestination) gta.getDestination());
							if (pd != null)
							{
								return pd.retrievePageNumber() + 1;
							}
						}
					}
				} catch (Exception e)
				{
					closePDF();
					Assertions.fail("Could not find the Page number for node " + indexOfNode);
				}
			}
			else if (count != indexOfNode)
			{
				nextItem = currItem.getNextSibling();
				if (nextItem == null)
				{
					break;
				}
				currItem = nextItem;
				count++;
			}

		}
		return -1;
	}

	public static int getNumberOfBookmarks()
	{
		PDDocumentOutline PDFOutline = PDF.getDocumentCatalog().getDocumentOutline();
		return PDFOutline.getOpenCount();

	}
	public static PDDocumentOutline getPDFOutline()
	{
		return PDF.getDocumentCatalog().getDocumentOutline();
	}

	public static PDPageDestination getPageDestination(PDNamedDestination outlineItem)
	{
		try
		{
			return PDF.getDocumentCatalog().findNamedDestinationPage((PDNamedDestination) outlineItem);
		}
		catch(Exception e)
		{
			closePDF();
			Assertions.fail("Could not get Page Destination");
		}
		return null;
	}

//----------------------------------------------------------------------------------------------------------------//
//--------------------------------------------------TEXT-METHODS--------------------------------------------------//
//----------------------------------------------------------------------------------------------------------------//

	//This just gets every piece of text from all the pages
	//May be helpful but we are testing specific parts of a page so it can probably be taken out
	public static String getAllTextFromCurrentPage()
	{
		String strippedText = "";
		try
		{
			PDFTextStripper textStripper = new PDFTextStripper();
			textStripper.setStartPage(currentPageNumber + 1);
			textStripper.setEndPage(currentPageNumber + 1);
			strippedText = textStripper.getText(PDF);
		}
		catch (IOException e)
		{
			closePDF();
			Assertions.fail("Failed to get text from page");
		}
		return strippedText;
	}

	//Gets the text from the area specified
	//This will be used in a lot of tests
	//All rectangle objects are stored in the PDFElements class
	public static String getTextFromArea(Rectangle2D rec)
	{
		try
		{
			PDFTextStripperByArea areaStripper = new PDFTextStripperByArea();

			areaStripper.addRegion("Region", rec);
			areaStripper.extractRegions(currentPDPage);
			//System.out.println(areaStripper.getTextForRegion("Region"));
			return areaStripper.getTextForRegion("Region");
		}
		catch (IOException e)
		{
			e.printStackTrace();

			closePDF();
			Assertions.fail("Failed to strip text from PDF, Page: "+currentPageNumber);
			return null;
		}
	}


//----------------------------------------------------------------------------------------------------------------//
//--------------------------------------------------FONT-METHODS--------------------------------------------------//
//----------------------------------------------------------------------------------------------------------------//

	//Returns a String containing the font data for the given String
	public static String getFontData(Rectangle2D rec, String stringToSearch)
	{
		try
		{
			PDFFontStripperByArea areaStripper = new PDFFontStripperByArea();

			areaStripper.addRegion("Region", rec);
			areaStripper.setStringToSearch(stringToSearch);
			areaStripper.extractRegions(currentPDPage);
			return areaStripper.getTextForRegion("Region");
		}
		catch (IOException e)
		{
			closePDF();
			Assertions.fail(String.format("Failed to get font data of text '%s'.", stringToSearch));
			return null;
		}
	}





	public static String formatTextArea(String expected)
	{
		return expected.replace("¶","\r\n");
	}

	public static String failureMessage(String expectedText, String actualText)
	{
		return String.format("\n\nExpected text and actual text pulled from PDF do not match!\n\nEXPECTED:\n%s\n\nACTUAL:\n%s\n", expectedText, actualText);
	}



	//TODO: Maybe add method parameter for using a specific file instead of a hard coded one
	/**
	 * This method uses the java native rectangle object
	 * This method will not actually be used in the real tests
	 * It is just for drawing a visual rectangle to validate the tests are grabbing the correct area of a page
	 * @param rec
	 */
	public static void drawRec(Rectangle2D rec)
	{
		float recX = (float) rec.getX();
		float recY = (float) (-rec.getY() + (PDFElements.PAGE_HEIGHT - rec.getHeight()));
		float recWidth = (float) rec.getWidth();
		float recHeight = (float) rec.getHeight();

		try
		{
			PDPageContentStream contentStream = new PDPageContentStream(PDF, currentPDPage, PDPageContentStream.AppendMode.APPEND, false);
			contentStream.setStrokingColor(Color.RED);
			contentStream.addRect(recX, recY, recWidth, recHeight);
			contentStream.stroke();
			contentStream.close();

			//File markedFile = new File("./commonFiles/TestFiles/AuditRedesignFiles/TempFiles/MARKEDFILE.pdf");
			File markedFile = new File("./commonFiles/TestFiles/AuditRedesignFiles/TempFiles/HierarchyContextOnly.pdf");
			PDF.save(markedFile);

		}
		catch (IOException e)
		{
			closePDF();
			Assertions.fail("Failed to draw rec");
		}
	}
	//TODO: Maybe add method parameter for using a specific file instead of a hard coded one
	/**
	 * This draw rec method used the rectangle object given by the apache pdfbox library
	 * This method will not actually be used in the real tests
	 * It is just for drawing a visual rectangle to validate the tests are grabbing the correct area of a page
	 * @param rec
	 */
	public static void drawRec(PDRectangle rec)
	{
		float recX = rec.getLowerLeftX();
		float recY = rec.getLowerLeftY();
		float recWidth = rec.getWidth();
		float recHeight = rec.getHeight();

		try
		{
			PDPageContentStream contentStream = new PDPageContentStream(PDF, currentPDPage, PDPageContentStream.AppendMode.APPEND, false);
			contentStream.setStrokingColor(Color.RED);
			contentStream.addRect(recX, recY, recWidth, recHeight);
			contentStream.stroke();
			contentStream.close();

			//File markedFile = new File("./commonFiles/TestFiles/AuditRedesignFiles/TempFiles/MARKEDFILE.pdf");
			File markedFile = new File("./commonFiles/TestFiles/AuditRedesignFiles/TempFiles/NODReportOnly.pdf");
			PDF.save(markedFile);

		}
		catch (IOException e)
		{
			closePDF();
			Assertions.fail("Failed to draw rec");
		}
	}
	public static String getRequestByFromAuditPDF(String leftCornerpieceText)
	{
		return leftCornerpieceText.split("\\r\\n")[0];
	}

	public static String getAuditIdentifierFromAuditPDF(String leftCornerpieceText)
	{
		return leftCornerpieceText.split("\\r\\n")[1];
	}

	public static String getDateAndTimeFromAuditPDF(String leftCornerpieceText)
	{
		return leftCornerpieceText.split("\\r\\n")[2];
	}

	public static String getPageTextFromAuditPDF(String rightCornerpieceText)
	{
		return rightCornerpieceText.split("\\r\\n")[1];
	}

	public static String getParagraphFormatFromPDF(String rightCornerpieceText)
	{
		return rightCornerpieceText.split("\\r\\n")[0];
	}

	public static String getNodeTitleFromPDF(String centerHeadingText)
	{
		return centerHeadingText.split("\\r\\n")[0];
	}

	public static String getCurrentEffectiveDateFromPDF(String centerHeadingText)
	{
		return centerHeadingText.split("\\r\\n")[1];
	}

	public static String getVolumeNumberTextFromPDF(String centerHeadingText)
	{
		return centerHeadingText.split("\\r\\n")[2];
	}

	public static String getNodeUUIDTextFromPDF(String centerHeadingText)
	{
		return centerHeadingText.split("\\r\\n")[3];
	}

	public static String getAuditIdentifier(String summaryHeaderText)
	{
		return summaryHeaderText.split("\\r\\n")[0];
	}

	public static String getAuditType(String summaryHeaderText)
	{
		return summaryHeaderText.split("\\r\\n")[1];
	}
	public static String getJurisdictionInfo(String summaryHeaderText)
	{
		return summaryHeaderText.split("\\r\\n")[2];
	}
	public static String getCompareInfo(String summaryHeaderText)
	{
		return summaryHeaderText.split("\\r\\n")[3];
	}
	public static String getSortByInfo(String summaryHeaderText)
	{
		return summaryHeaderText.split("\\r\\n")[4];
	}
	public static String getModifiedFrom(String summaryHeaderText)
	{
		return summaryHeaderText.split("\\r\\n")[5];
	}

	public static PDPageDestination getNextPageDestination(PDOutlineItem outlineItem, int indexOfOutlineItem)
	{
		PDPageDestination nextPageDestination = null;

		try
		{
			if(indexOfOutlineItem < outlineItem.getOpenCount())
			{
				nextPageDestination = PDFUtils.getPageDestination((PDNamedDestination) outlineItem.getDestination());
			}
		}
		catch (IOException e)
		{
			closePDF();
			Assertions.fail("Next Destination not found");
		}
		return nextPageDestination;
	}
	public static void saveFileWithOnlyOnePageFromPDDocument(int pageToBeSavedAsPDF)
	{
		PDPage contentComparePage = PDF.getPage(pageToBeSavedAsPDF);
		PDDocument onePagePDDocument = new PDDocument();
		onePagePDDocument.addPage(contentComparePage);
		try
		{
			onePagePDDocument.save(String.format(AUDIT_SINGLE_PAGE_LOCATION_PDF,pageToBeSavedAsPDF));
		}
		catch (IOException e)
		{
			e.printStackTrace();
			Assert.fail("Failed to save one page of audit to location");
		}
	}
}
