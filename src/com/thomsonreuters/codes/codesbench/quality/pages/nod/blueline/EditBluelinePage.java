package com.thomsonreuters.codes.codesbench.quality.pages.nod.blueline;

import javax.annotation.PostConstruct;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thomsonreuters.codes.codesbench.quality.pageelements.CommonPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.nod.blueline.EditBluelinePageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.nod.blueline.InsertBluelinePageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;

@Component
public class EditBluelinePage extends BasePage
{
	WebDriver driver;

	@Autowired
	public EditBluelinePage(WebDriver driver) 
	{
		super(driver);
		this.driver = driver;
	}
	
	@PostConstruct
	public void init()
	{
		PageFactory.initElements(driver, InsertBluelinePageElements.class);
	}
	
	public boolean didAdditionalFlusAnalysisTextFieldAppear()
	{
		click(InsertBluelinePageElements.bluelineIndexAlsoButton);
		return isElementDisplayed(InsertBluelinePageElements.BLUELINE_ADDITIONAL_FLUSH_ANALYSIS_TEXT_FIELD);
	}
	
	public void setSecondFlushAnalysisTextAndClickOk(String text)  
	{
		clearAndSendKeysToElement(InsertBluelinePageElements.bluelineAdditonalFlushAnalysisTextFields, text);
	}
	
	public boolean checkFlushAnalysisTextFieldWithGivenText(String text)  
    {
    	enterTheInnerFrame();
    	click(InsertBluelinePageElements.bluelineFlushAnalysisTextField);
    	return checkFieldValueIsExpectedOne(InsertBluelinePageElements.bluelineFlushAnalysisTextField, text);
    }

	public void clickOK()
    {
    	sendEnterToElement(CommonPageElements.OK_BUTTON);
    	waitForElementGone(CommonPageElements.PROCESSING_SPINNER);
    }
	
	public boolean switchToEditBluelineWidow()
	{
		return switchToWindow(EditBluelinePageElements.EDIT_BLUELINE_PAGE_TITLE);
	}
	
	public boolean isBluelineTypeFlush()
	{
		enterTheInnerFrame();
		return isElementSelected(InsertBluelinePageElements.bluelineTypeFlush);
	}
	
	public boolean isTextFieldValueSameAsExpected(String text)  
	{
		return checkFieldValueIsExpectedOne(InsertBluelinePageElements.bluelineTextField, text);
	}
	
	public boolean isFlushAnalysisFieldValueSameAsExpected(String text)  
	{
		return checkFieldValueIsExpectedOne(InsertBluelinePageElements.bluelineFlushAnalysisTextField, text);
	}

	public boolean isAdditionalFlushAnalysisFieldValueSameAsExpected(String text)  
	{
		return checkFieldValueIsExpectedOne(InsertBluelinePageElements.bluelineAdditonalFlushAnalysisTextFields, text);
	}
	
	public boolean checkFieldValueIsExpectedOne(WebElement element, String expectedValue)
	{
		boolean checkValueIsAsExpected;
		try
		{
			checkValueIsAsExpected = element.getAttribute("value").equals(expectedValue);
		}
		catch (Exception e)
		{
			checkValueIsAsExpected = element.getText().contains(expectedValue);
		}
		return checkValueIsAsExpected;
	}
	
	public void updateBluelineTextFieldValues(String text)
	{
		sendKeysToElement(InsertBluelinePageElements.bluelineTextField, text);
		click(InsertBluelinePageElements.bluelineFlushAnalysisTextField);
	}
	
    public void addAdditionalFlushAnalysisText(String text)
    {
    	clearAndSendKeysToElement(InsertBluelinePageElements.bluelineAdditonalFlushAnalysisTextFields, text);
    }
}
