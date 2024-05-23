package com.thomsonreuters.codes.codesbench.quality.pages.nod;

import javax.annotation.PostConstruct;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thomsonreuters.codes.codesbench.quality.pageelements.nod.ListOfCasesPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;

@Component
public class ListOfCasesPage extends BasePage
{
	WebDriver driver;
	
	@Autowired
	public ListOfCasesPage(WebDriver driver) 
	{
		super(driver);
		this.driver = driver;
	}
	
	@PostConstruct
	public void init()
	{
		PageFactory.initElements(driver, ListOfCasesPageElements.class);
	}

	public String getCurrentlyViewingCasesFor()
	{
		return getElementsText(ListOfCasesPageElements.currentlyViewingCasesForSpan);
	}
	 
	public void setContentSetTeam(String contentSet)
	{
		selectDropdownOption(ListOfCasesPageElements.contentSetTeamDropdown, contentSet);
	}

	public void clickGoButton()
	{
		sendEnterToElement(ListOfCasesPageElements.goButton);
		waitForGridRefresh();
		waitForPageLoaded();
	}
}
