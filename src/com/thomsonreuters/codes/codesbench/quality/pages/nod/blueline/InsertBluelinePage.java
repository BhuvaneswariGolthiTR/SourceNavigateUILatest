package com.thomsonreuters.codes.codesbench.quality.pages.nod.blueline;

import javax.annotation.PostConstruct;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thomsonreuters.codes.codesbench.quality.pageelements.CommonPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.HierarchyPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.nod.blueline.InsertBluelinePageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.nod.headnotes.HeadnotesPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;

@Component
public class InsertBluelinePage extends BasePage
{
	WebDriver driver;

	@Autowired
	public InsertBluelinePage(WebDriver driver) 
	{
		super(driver);
		this.driver = driver;
	}

	@PostConstruct
	public void init()
	{
		PageFactory.initElements(driver, InsertBluelinePageElements.class);
		PageFactory.initElements(driver, HierarchyPageElements.class);
	}
	
	public boolean checkForSpellingErrorMessage()
	{
		waitForPageLoaded();
		return doesElementExist(InsertBluelinePageElements.SPELLCHECK_ERRORS_MESSAGE);
	}

	public void setBlueLineNumber(String number)  
	{
		click(InsertBluelinePageElements.bluelineNumberInputField);
		clearAndSendKeysToElement(InsertBluelinePageElements.bluelineNumberInputField, number);
	}

	public void selectBlueLineTypeFlush()  
	{
		click(InsertBluelinePageElements.bluelineTypeFlush);
	}

	public void setBlueLineText(String text)
	{
		clearAndSendKeysToElement(InsertBluelinePageElements.bluelineTextField, text);
	}
	
	public boolean didSpellcheckErrorMessageAppear()
	{
		return doesElementExist(InsertBluelinePageElements.SPELLCHECK_ERRORS_MESSAGE);
	}
	
	public boolean clickFinish()  
    {
		click(InsertBluelinePageElements.finishButton);
    	waitForElementGone(CommonPageElements.PROCESSING_SPINNER);
    	return switchToWindow(HeadnotesPageElements.HEADNOTES_PAGE_TITLE);
    }
	
	public void selectBlueLineTypeIndent()  
    {
		waitForPageLoaded(); //Needed to prevent stale element exceptions
		click(InsertBluelinePageElements.bluelineTypeIndent);
    }
	
	public boolean isIndentTypeEnabled()
	{
		return isElementDisplayed(InsertBluelinePageElements.BLUELINE_TYPE_INDENT);
	}
	
	public void selectBlueLineTypeStartOfIndent()  
    {
    	click(InsertBluelinePageElements.bluelineTypeStartOfIndent);
    }
	
	public boolean dropdownsAppeared()
	{
		return isElementDisplayed(InsertBluelinePageElements.BLUELINE_START_OF_INDENT_FIRST_TEXT_FIELD_DROPDOWN_BUTTON)
		& isElementDisplayed(InsertBluelinePageElements.BLUELINE_START_OF_INDENT_SECOND_TEXT_FIELD_DROPDOWN_BUTTON);
	}
	
	public void refreshFlushAnalysisFields()
	{
		click(InsertBluelinePageElements.bluelineStartOfIndentSecondTextField);
	}
	
	public boolean checkIfFirstFlushFieldValueIsAsExpected(String text)  
	{
		return checkFieldValueIsExpectedOne(InsertBluelinePageElements.bluelineStartOfIndentFirstFlushAnalysisTextField, text);
	}
	
	public boolean checkIfSecondFlushFieldValueIsAsExpected(String text)  
	{
		return checkFieldValueIsExpectedOne(InsertBluelinePageElements.bluelineStartOfIndentSecondFlushAnalysisTextField, text);
	}
	
	public boolean areIndentTextsAndDropdownDisplayed(String text)  
	{
		return isElementDisplayed(InsertBluelinePageElements.BLUELINE_INDENT_TEXT_FIELD)
		& isElementDisplayed(InsertBluelinePageElements.BLUELINE_INDENT_TEXT_FIELD_DROPDOWN_BUTTON)
		& checkFieldValueIsExpectedOne(InsertBluelinePageElements.bluelineIndentSecondTextField, text.toLowerCase());
	}
	
	public boolean areindentFlushAnalysisFieldsDisplayed(String text)  
	{
		return checkFieldValueIsExpectedOne(InsertBluelinePageElements.bluelineIndentFirstFlushAnalysis, "")
		& checkFieldValueIsExpectedOne(InsertBluelinePageElements.bluelineIndentSecondFlushAnalysis, text.toLowerCase());
	}
	
	public boolean isIndentIndentAnalysisDisplayed()
	{
		return isElementDisplayed(InsertBluelinePageElements.BLUELINE_INDENT_ANALYSIS);
	}
	
	public void updateInsertBluelineIndentTextField(String text)  
	{
		clearAndSendKeysToElement(InsertBluelinePageElements.bluelineIndentTextField, text);
		click(InsertBluelinePageElements.bluelineIndentTextField); // to refresh flush analysis fields
	}
	
	public boolean checkFieldValueOfIndentFirstFlushAnalysis(String text)  
	{
		return	checkFieldValueIsExpectedOne(InsertBluelinePageElements
			.bluelineIndentFirstFlushAnalysis, text);
	}	
	
	public boolean checkFieldValueOfIndentTextField(String text)  
	{
		return checkFieldValueIsExpectedOne(InsertBluelinePageElements
				.bluelineIndentTextField, text);
	}
	
	public void setFirstIndentTextField(String text)
	{
		clearAndSendKeysToElement(InsertBluelinePageElements.bluelineStartOfIndentFirstTextField, text);
	}
	
	public void setSecondIndentTextField(String text)
	{
		clearAndSendKeysToElement(InsertBluelinePageElements.bluelineStartOfIndentSecondTextField, text);
	}
	
	public boolean isIndentParentValueSameAsExpected(String expectedText)
	{
		return InsertBluelinePageElements.bluelineIndentParentBlValue.getText().equals(expectedText);
	}

	public void clickOk()
	{
		click(CommonPageElements.OK_BUTTON);
	}
}
