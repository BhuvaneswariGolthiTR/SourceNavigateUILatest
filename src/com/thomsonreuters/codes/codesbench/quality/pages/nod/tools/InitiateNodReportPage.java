package com.thomsonreuters.codes.codesbench.quality.pages.nod.tools;

import javax.annotation.PostConstruct;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thomsonreuters.codes.codesbench.quality.pageelements.nod.tools.InitiateNodReportPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;

@Component
public class InitiateNodReportPage extends BasePage
{
	WebDriver driver;
	
	@Autowired
	public InitiateNodReportPage(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
	}

	@PostConstruct
	public void init()
	{
		PageFactory.initElements(driver, InitiateNodReportPageElements.class);
	}
	
	public void setFileName(String fileName)
	{
		clearAndSendKeysToElement(InitiateNodReportPageElements.fileNameTextbox, fileName);
	}
	
	public void selectGivenVolumeFromDropdown(String volume)
	{
		selectDropdownOption(InitiateNodReportPageElements.volumeSelector, volume);
	}
	
	public void moveSelectedVolumeRight()
	{
		click(InitiateNodReportPageElements.moveSelectedVolumeToTheRight);
	}
	
	public void clickCreateWorkflowButton()
	{
		click(InitiateNodReportPageElements.createWorkflowButton);
	}

	public void clickCreateCwbWorkflowButton()
	{
		click(InitiateNodReportPageElements.createCwbWorkflowButton);
	}
}
