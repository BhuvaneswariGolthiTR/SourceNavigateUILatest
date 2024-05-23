package com.thomsonreuters.codes.codesbench.quality.pages.source.bts;

import javax.annotation.PostConstruct;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thomsonreuters.codes.codesbench.quality.pageelements.source.bts.FindRecordPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;

@Component
public class FindRecordPage extends BasePage 
{
	private WebDriver driver;
	
	@Autowired
	public FindRecordPage(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
	}
	
	@PostConstruct
	public void init()
	{
		PageFactory.initElements(driver, FindRecordPageElements.class);
	}

	public void setDocumentType(String documentType)
	{
		//Old code
		//selectDropdownOption(FindRecordPageElements.documentType, documentType);
		//New code
		selectDropdownOptionUsingJavascript("findDocType", documentType);
	}
	
	public void setDocumentNumber(String documentNumber)
	{
		sendKeysToElement(FindRecordPageElements.documentNumber, documentNumber);
	}
	
	public void clickFindNow() 
	{
		click(FindRecordPageElements.findNowButton);
    	waitForPageLoaded();
	}

	public void setLegislativeYear(String year)
	{
		//Old code
		//FindRecordPageElements.legislativeYear.clear();
		//sendKeysToElement(FindRecordPageElements.legislativeYear, year);
		//New code
		clearAndSendTextToTextbox(FindRecordPageElements.legislativeYear, year);
	}
	
	
}
