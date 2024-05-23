package com.thomsonreuters.codes.codesbench.quality.pages.audits.reportcentral;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.io.FileUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.thomsonreuters.codes.codesbench.quality.pageelements.audits.reportcentral.ReportCentralPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.source.SourceNavigatePageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class ReportCentralPage extends BasePage
{
	WebDriver driver;

	@Autowired
	public ReportCentralPage(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
	}

	@PostConstruct
	public void init()
	{
		PageFactory.initElements(driver, SourceNavigatePageElements.class);
		PageFactory.initElements(driver, ReportCentralPageElements.class);
	}

	public boolean switchToReportCentralPage()
	{
		return switchToWindow(ReportCentralPageElements.REPORT_CENTRAL_PAGE_TITLE);
	}

	public String readSelectedTextByIndex(String textXpath, int index)
	{
		return getElements(textXpath).get(index).getText();
	}

	public String grabContentsOfPDFReportInReportCentral()
	{
		// gathering URL
		String innerLink = ReportCentralPageElements.reportIdentifierColumnLinkXpath.getAttribute("href");
		String reportId = innerLink.substring(innerLink.indexOf("(") + 2, innerLink.lastIndexOf(")") - 4);
		StringBuilder urlParameter = new StringBuilder("http://uat.magellan3.int.westgroup.com:10080/Reports/Audits/nod/" + reportId); // TODO environment
		reportId = innerLink.substring(innerLink.indexOf("/"), innerLink.lastIndexOf(")") - 4);
		urlParameter.append(reportId + ".pdf");
		URL url = null;
		try 
		{
			url = new URL(urlParameter.toString());
		} 
		catch (MalformedURLException e) 
		{
			e.printStackTrace();
		}
		
		// creating a file
		File file = new File(System.getProperty("user.home") + File.separator + "Downloads" + File.separator + "report.pdf");
		try 
		{
			FileUtils.copyURLToFile(url, file);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		// looking into it
		PDDocument pdfReport = null;
		PDFTextStripper pdfStripper = null;
		String contents = "";
		try 
		{
			pdfReport = PDDocument.load(file);
			pdfStripper = new PDFTextStripper();
			contents = pdfStripper.getText(pdfReport);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		file.delete();
		
		return contents;
	}

	public boolean reportCentralVerifyWorkflowFinishes() 
	{
		String workFlowStatus = readSelectedTextByIndex(ReportCentralPageElements.WORKFLOW_STATUS_COLUMN_XPATH, 0);
		boolean workflowCompleted = false;
		if(!workFlowStatus.equals("Finished"))
		{
			for (int i = 0; i < 120; i++)
			{
				workFlowStatus = readSelectedTextByIndex(ReportCentralPageElements.WORKFLOW_STATUS_COLUMN_XPATH, 0);
				if(!workFlowStatus.equals("Finished"))
				{
					sendEnterToElement(ReportCentralPageElements.refreshButton);
					waitForGridRefresh();
					DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
				}
				else
				{
					workflowCompleted = true;
					break;
				}
			}
		}
		else
		{
			workflowCompleted = true;
		}
		return workflowCompleted;
	}

	public void clickRefresh()
	{
		click(ReportCentralPageElements.refreshButton);
		waitForGridRefresh();
	}

	public boolean isReportInTable(String reportIdentifier)
	{
		return doesElementExist(String.format(ReportCentralPageElements.REPORT_IDENTIFIER, reportIdentifier));
	}
}
