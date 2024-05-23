package com.thomsonreuters.codes.codesbench.quality.pages.nod.reports;

import javax.annotation.PostConstruct;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thomsonreuters.codes.codesbench.quality.pageelements.CommonPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.nod.reports.DetailReportPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;

@Component
public class ReportsDetailPage extends BasePage
{
	WebDriver driver;
	
	@Autowired
	public ReportsDetailPage(WebDriver driver) 
	{
		super(driver);
		this.driver = driver;
	}
	
	@PostConstruct
	public void init()
	{
		PageFactory.initElements(driver, DetailReportPageElements.class);
	}
	
	public void insertDate(String date)  
    {
    	sendKeysToElement(DetailReportPageElements.dateField, date);
    }
	
	public void insertContentSet(String contentSet)
	{
		clearAndSendKeysToElement(DetailReportPageElements.contentSetInputField, contentSet);
	}
	
	public void clickOk()
	{
		click(CommonPageElements.OK_BUTTON);
	}
	
	public boolean switchToDetailReportPage()
	{
		return switchToWindow(DetailReportPageElements.DETAIL_REPORT_PAGE_TITLE);
	}
}
