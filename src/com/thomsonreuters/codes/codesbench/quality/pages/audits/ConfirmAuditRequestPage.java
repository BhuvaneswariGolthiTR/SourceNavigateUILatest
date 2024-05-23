package com.thomsonreuters.codes.codesbench.quality.pages.audits;

import javax.annotation.PostConstruct;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thomsonreuters.codes.codesbench.quality.pageelements.CommonPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.audits.ConfirmAuditRequestPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.audits.auditsbysource.AuditBySourcePageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.audits.reportcentral.ReportCentralPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;

@Component
public class ConfirmAuditRequestPage extends BasePage
{
	private WebDriver driver;
	
	@Autowired
	public ConfirmAuditRequestPage(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
	}
	
	@PostConstruct
	public void init()
	{
		PageFactory.initElements(driver, ConfirmAuditRequestPageElements.class);
	}
	
	public boolean isNumberOfTargetDocumentsEqualToZero()
    {
    	String numberOfTargetDocs = getElementsText(ConfirmAuditRequestPageElements.numberOfTargetDocuments);
        int numOfTarDocs = -1;
        try
        {
        	numOfTarDocs = Integer.parseInt(numberOfTargetDocs);
        }
        catch(Exception e)
        {
        	Assert.fail("Grabbed the wrong td cell that we expected to find a number in.  Xpath needs to be looked into.");
        }
        return numOfTarDocs == 0;
    }
	
	public void clickCancelButton()
	{
		sendEnterToElement(CommonPageElements.CANCEL_BUTTON);
		//waitForElementGone(CommonPageElements.CANCEL_BUTTON);
		waitForWindowGoneByTitle(ConfirmAuditRequestPageElements.CONFIRM_AUDIT_REQUEST_PAGE_TITLE);
		switchToWindow(AuditBySourcePageElements.AUDIT_BY_SOURCE_PAGE_TITLE);
	}
	
	public boolean isProcessAuditButtonEnabled() 
	{
		return isElementEnabled(ConfirmAuditRequestPageElements.processAuditButton);
	}
	
	public boolean clickProcessAuditButton()
	{
		click(ConfirmAuditRequestPageElements.processAuditButton);
		boolean reportCentralWindowAppeared = switchToWindow(ReportCentralPageElements.REPORT_CENTRAL_PAGE_TITLE);
		maximizeCurrentWindow();
		return reportCentralWindowAppeared;
	}
	
	public boolean isErrorMessageDisplayed()
	{
		return isElementDisplayed(ConfirmAuditRequestPageElements.ERROR_MESSAGE);
	}
}
