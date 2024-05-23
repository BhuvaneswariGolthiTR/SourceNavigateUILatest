package com.thomsonreuters.codes.codesbench.quality.pages.nod.reports;

import javax.annotation.PostConstruct;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thomsonreuters.codes.codesbench.quality.pageelements.CommonPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.nod.reports.SummaryReportPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;

@Component
public class ReportsSummaryPage extends BasePage
{
	WebDriver driver;
	
	@Autowired
	public ReportsSummaryPage(WebDriver driver) 
	{
		super(driver);
		this.driver = driver;
	}
	
	@PostConstruct
	public void init()
	{
		PageFactory.initElements(driver, SummaryReportPageElements.class);
	}
	
	public boolean switchToSummaryReportsPage()
	{
		return switchToWindow(SummaryReportPageElements.SUMMARY_REPORT_PAGE_TITLE);
	}
	
	public void insertDate(String date)  
    {
    	sendKeysToElement(SummaryReportPageElements.dateField, date);
    }
	
	public void insertContentSet(String contentSet)
	{
		clearAndSendKeysToElement(SummaryReportPageElements.contentSetInputField, contentSet);
	}
	
	public void clickOk()
	{
		click(CommonPageElements.OK_BUTTON);
	}
}
