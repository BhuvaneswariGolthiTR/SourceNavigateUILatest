package com.thomsonreuters.codes.codesbench.quality.pages.nod.headnotes;

import javax.annotation.PostConstruct;

import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import com.thomsonreuters.codes.codesbench.quality.pages.table.TableTestingPage;

@Component
public class HeadnotesTablePage extends BasePage
{
	WebDriver driver;

	@Autowired
	public HeadnotesTablePage(WebDriver driver) 
	{
		super(driver);
		this.driver = driver;
	}
	
	@PostConstruct
	public void init()
	{
		
	}
	
	public boolean isCompletedByCorrect()
	{
		return (user().getFirstname() + " " + user().getLastname()).equals( tableTestingPage().getCellValue(TableTestingPage.SubscribedCasesColumns.COMPLETED_BY, 0) );
	}
	
	public boolean isCompletedDateCorrect(String currentDate)
	{
		return currentDate.equals( tableTestingPage().getCellValue(TableTestingPage.SubscribedCasesColumns.COMPLETED_DATE, 0) );
	}
}
