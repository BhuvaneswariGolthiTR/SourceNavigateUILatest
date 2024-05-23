package com.thomsonreuters.codes.codesbench.quality.utilities.files;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestSetupEdge;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.junit.jupiter.api.Assertions;

import java.io.File;
import java.io.IOException;

public class PDFUtils extends TestSetupEdge
{
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
}
