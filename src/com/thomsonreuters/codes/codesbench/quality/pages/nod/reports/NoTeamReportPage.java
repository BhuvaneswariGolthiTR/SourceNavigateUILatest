package com.thomsonreuters.codes.codesbench.quality.pages.nod.reports;

import javax.annotation.PostConstruct;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thomsonreuters.codes.codesbench.quality.pageelements.CommonPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.nod.reports.NoTeamReportPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;

@Component
public class NoTeamReportPage extends BasePage
{
	WebDriver driver;
	
	@Autowired
	public NoTeamReportPage(WebDriver driver) 
	{
		super(driver);
		this.driver = driver;
	}

	@PostConstruct
	public void init()
	{
		PageFactory.initElements(driver, NoTeamReportPageElements.class);
	}
	
	public void insertDate(String date) 
    {
    	clearAndSendKeysToElement(NoTeamReportPageElements.dateField, date);
    }
	
	public void clickOk()
	{
		click(CommonPageElements.OK_BUTTON);
	}
}
