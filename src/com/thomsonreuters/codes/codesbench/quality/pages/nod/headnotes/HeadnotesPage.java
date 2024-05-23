package com.thomsonreuters.codes.codesbench.quality.pages.nod.headnotes;

import javax.annotation.PostConstruct;

import org.openqa.selenium.JavascriptException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thomsonreuters.codes.codesbench.quality.pageelements.CommonPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.nod.subscribedcases.SubscribedCasesPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.nod.headnotes.HeadnotesPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;

@Component
public class HeadnotesPage extends BasePage
{
	WebDriver driver;
	
	@Autowired
	public HeadnotesPage(WebDriver driver) 
	{
		super(driver);
		this.driver = driver;
	}
	
	@PostConstruct
	public void init()
	{
		PageFactory.initElements(driver, HeadnotesPageElements.class);
	}

	public void ignoreHeadnoteByGivenRow(int row)  
    {
    	click(String.format(HeadnotesPageElements.IGNORE_HEADNOTE_IN_A_ROW_WITH_GIVEN_NUMBER, row));
    	waitForGridRefresh();
    	waitForPageLoaded();
    }
    
    public void clickIgnoreAllHeadnotesButton()  
    {
    	click(HeadnotesPageElements.ignoreAllButton);
    	waitForGridRefresh();
    	waitForPageLoaded();
    }
    
    public boolean isHeadnoteInGivenRowIgnored(int row)  
    {
    	return doesElementExist(String.format(HeadnotesPageElements.UNIGNORE_HEADNOTE_IN_A_ROW_WITH_GIVEN_NUMBER, row));
    }
    
    public void unignoreHeadnoteByGivenRow(int row)  
    {
    	click(String.format(HeadnotesPageElements.UNIGNORE_HEADNOTE_IN_A_ROW_WITH_GIVEN_NUMBER, row));
    	waitForGridRefresh();
    	waitForPageLoaded();
    }
    
    public void classifyHeadnoteByGivenRow(int row)  
    {
    	click(String.format(HeadnotesPageElements.CLASSIFY_HEADNOTE_IN_A_ROW_WITH_GIVEN_NUMBER, row));
    	waitForGridRefresh();
    	waitForPageLoaded();
    }
    
    public boolean isHeadnoteInGivenRowClassified(int row)
    {
    	return !doesElementExist(String.format(HeadnotesPageElements.IGNORE_HEADNOTE_IN_A_ROW_WITH_GIVEN_NUMBER, row));
    }
    
    public void unclassifyFirstHeadnote()
    {
    	click(HeadnotesPageElements.firstClassificationDeletionId);
    }
    
    public void waitForClassificationRemoval() 
    {
    	waitForElementGone(HeadnotesPageElements.FIRST_CLASSIFICATION_DELETION_ID);
    }
    
    public boolean doesIgnoreButtonExistForHeadnoteInGivenRow(int row)
    {
    	return doesElementExist(String.format(HeadnotesPageElements.IGNORE_HEADNOTE_IN_A_ROW_WITH_GIVEN_NUMBER, row));
    }
    
    public void clickUnignoreAllHeadnotesButton()  
    {
    	click(HeadnotesPageElements.unignoreAllButton);
    	waitForGridRefresh();
    	waitForPageLoaded();
    }
    
    public boolean isHeadnoteInGivenRowUnignored(int row)  
    {
    	return doesElementExist(String.format(HeadnotesPageElements.IGNORE_HEADNOTE_IN_A_ROW_WITH_GIVEN_NUMBER, row));
    }
    
    public boolean doesPreviousCaseButtonExist() 
    {
    	return isElementDisplayed(HeadnotesPageElements.PREVIOUS_CASE_BUTTON);
    }
    
    public boolean doesNextCaseButtonExist()
    {
    	return isElementDisplayed(HeadnotesPageElements.NEXT_CASE_BUTTON);
    }
    
    public boolean doesUnignoreAllButtonExist()
    {
    	return isElementDisplayed(HeadnotesPageElements.UNIGNORE_ALL_BUTTON_XPATH);
    }
    
    public boolean doesIgnoreAllButtonExist()
    {
    	return isElementDisplayed(HeadnotesPageElements.IGNORE_ALL_BUTTON_XPATH);
    }
    
    public boolean doesCompletedByAndDateButtonExist()
    {
    	return isElementDisplayed(HeadnotesPageElements.COMPLETED_BY_AND_DATE_BUTTON);
    }
    
    public boolean doesSignOutCaseButtonExist()
    {
    	return isElementDisplayed(HeadnotesPageElements.SIGN_OUT_CASE_BUTTON);
    }
    
    public boolean doRefreshReporterButtonsExist()
    {
    	return isElementDisplayed(HeadnotesPageElements.REFRESH_REPORTER_BUTTON);
    }
    
    public boolean doCompletedByButtonsExist()
    {
    	return isElementDisplayed(HeadnotesPageElements.COMPLETED_BY_BUTTON);
    }
    
    public void clickCancel()  
    {
    	click(CommonPageElements.CANCEL_BUTTON);
    }	

    public void clickCompletedByAndDateButton()  
    {
    	click(HeadnotesPageElements.completedByAndDateField);
    }
    
    public boolean switchToHeadnotesPage()
    {
    	return switchToWindow(HeadnotesPageElements.HEADNOTES_PAGE_TITLE);
    }
    
    public boolean isCompletedDateFieldEmpty()
    {
    	return getElementsText(HeadnotesPageElements.completedDateField).equals("");
    }
     
    public boolean compareCompletedDateToCurrentDate()
    {
    	return getElementsText(HeadnotesPageElements.completedDateField).equals(DateAndTimeUtils.getCurrentDateMMddyyyy());
    }
    
    public boolean doesSynopsisBackgroundCollapsibleExist()
    {
    	return isElementDisplayed(HeadnotesPageElements.SYNOPSIS_BACKGROUND_COLLAPSIBLE_DIV);
    }
    
    public boolean isSynopsisBackgroundCollapsed()
    {
    	return "collapsed".equals( HeadnotesPageElements.synopsisBackgroundCollapsibleBodyDiv.getAttribute("class") );
    }
    
    public String getSynopsisBackgroundCollapsibleBodyText()
    {
    	click(HeadnotesPageElements.synopsisHoldingCollapsibleDiv);
    	return getElementsText(HeadnotesPageElements.synopsisBackgroundCollapsibleBodyDiv);
    }
    
    public boolean doesSynopsisHoldingCollapsibleExist()
    {
    	return isElementDisplayed(HeadnotesPageElements.SYNOPSIS_HOLDING_COLLAPSIBLE_DIV);
    }
    
    public boolean isSynopsisHoldingsCollapsed()
    {
    	return "collapsed".equals( HeadnotesPageElements.synopsisHoldingsCollapsibleBodyDiv.getAttribute("class") );
    }
    
    public String getSynopsisHoldingsCollapsibleBodyText()
    {
    	click(HeadnotesPageElements.synopsisHoldingCollapsibleDiv);
    	return getElementsText(HeadnotesPageElements.synopsisHoldingsCollapsibleBodyDiv);
    }
    
    public boolean doesNotesCollapsibleExist()
    {
    	return isElementDisplayed(HeadnotesPageElements.NOTES_COLLAPSIBLE_DIV);
    }
    
    public boolean isNotesCollapsed()
    {
    	return "collapsed".equals( HeadnotesPageElements.notesCollapsibleBodyDiv.getAttribute("class") );
    }
    
    public String getNotesCollapsibleBodyText()
    {
    	click(HeadnotesPageElements.notesCollapsibleDiv);
    	return getElementsText(HeadnotesPageElements.notesCollapsibleBodyDiv);
    }
    
    public boolean clickSubscribedCasesBreadcrumb()
    {
    	click(HeadnotesPageElements.subscribedCasesBreadcrumb);
    	return switchToWindow(SubscribedCasesPageElements.SUBSCRIBED_CASES_PAGE_TITLE);
    }
    
    public void clickFindResultByHID(String headingId)  
    {
    	click(String.format(HeadnotesPageElements.findResultUuidByHid, headingId));
    }
    
    public boolean repealedNodeIsHighlightedRed(String text)
    {
    	return isElementDisplayed(String.format(HeadnotesPageElements.repealedWithGivenText, text));
    }
    
    public boolean repealedNodeIsNotHighlightedRed(String text)
    {
    	return !isElementDisplayed(String.format(HeadnotesPageElements.repealedWithGivenText, text));
    }
    
    public boolean switchToHeadnotesWindow()
    {
    	return switchToWindow(HeadnotesPageElements.HEADNOTES_PAGE_TITLE);
    }
    
    public void clickSignOutCaseButton()
    {
    	click(HeadnotesPageElements.signOutCaseButton);
    }
    
    public void clearCaseSignOut()  
    {
    	if (!getSignedOutByText().equals(""))
    	{
    		click(HeadnotesPageElements.clearSignOutCaseButton);
    		acceptAlert();
            waitForPageLoaded();
    	}
    }
    
    public boolean doesHighlightedItemExist()
    {
    	return isElementDisplayed(HeadnotesPageElements.HIGHLIGHTED_ITEM_IN_SEARCH_RESULT);
    }
    
    public String getCaseINFO(final String field)
    {
    	return getElementsText(String.format(HeadnotesPageElements.CASE_INFO_FIELD, field, field));
    }
    
    public void removeClassificationAllHeadnotes()  
    {
    	while (doesElementExist(String.format(HeadnotesPageElements.UNCLASSIFY_HEADNOTE_IN_A_ROW_WITH_GIVEN_NUMBER, "*")) )
    	{
    		click( String.format(HeadnotesPageElements.UNCLASSIFY_HEADNOTE_IN_A_ROW_WITH_GIVEN_NUMBER, "*") );
    		acceptAlert();
    	}
    }
    
    public boolean AreAllHeadnotesIgnored()
    {
    	return getCountOfHeadnotes() == getElements(HeadnotesPageElements.UNIGNORE_BUTTON_XPATH).size();
    }
    
    public boolean AreAllHeadnotesNotIgnored()
    {
    	return getCountOfHeadnotes() == getElements(HeadnotesPageElements.IGNORE_BUTTON_XPATH).size();
    }
    
    public int getCountOfHeadnotes()
    {
    	return getElements(HeadnotesPageElements.HEADNOTES_TABLE_ROWS).size();
    }
    
    public boolean isPreviousCaseButtonDisabled()
	{
		return isElementDisabled(HeadnotesPageElements.previousCaseButton);
	}
	
	public boolean isNextCaseButtonDisabled()
	{
		return isElementDisabled(HeadnotesPageElements.nextCaseButton);
	}
	
	public void clickNextCaseButton()
	{
		click(HeadnotesPageElements.nextCaseButton);
		waitForGridRefresh();
		waitForPageLoaded();
	}
	
	public void clickPreviousCaseButton()
	{
		click(HeadnotesPageElements.previousCaseButton);
		waitForGridRefresh();
		waitForPageLoaded();
	}
	
	public boolean isCaseOpen(String caseSerialNumber)
	{
		return getElementsText(HeadnotesPageElements.caseNumberField).equals(caseSerialNumber);
	}
	
	public void removeNoteFromHeadnotes()  
    {
		HeadnotesPageElements.editNoteTextarea.clear();
    }
	
	public boolean wasNoteCreated()
	{
		return isElementDisplayed(HeadnotesPageElements.NOTES_COLLAPSIBLE_DIV)
        & isElementDisplayed(HeadnotesPageElements.EDIT_NOTE_BUTTON);
	}
	
	public void editNoteWithGivenText(String text)
	{
        sendKeysToElement(HeadnotesPageElements.editNoteTextarea, text);
	}
    
    public boolean clickSave()
    {
    	click(CommonPageElements.SAVE_BUTTON);
    	return switchToWindow(HeadnotesPageElements.HEADNOTES_PAGE_TITLE);
    }
    
    public void clickEdit()
    {
    	click(HeadnotesPageElements.editNoteButton);
    	waitForPageLoaded();
    }
    
    public void clickHeadnotesNoteCollapsible()
    {
    	click(HeadnotesPageElements.notesCollapsibleDiv);
    }
    
    public String getNoteText()
    {
    	click(HeadnotesPageElements.notesCollapsibleDiv);
    	return getElementsText(HeadnotesPageElements.editNoteTextarea);
    }

    public boolean doesClassificationExistForGivenRow(String bluelineText, int row)
    {
        return doesElementExist(String.format(HeadnotesPageElements.BLUELINE_CLASSIFICATION_XPATH, row, bluelineText));
    }

    public String getSignedOutByText()
    {
        try
        {
            return getElement(HeadnotesPageElements.SIGNED_OUT_BY_XPATH).getText();
        }
        catch(JavascriptException e)
        {
            //There's a JavascriptException unable to get element text that is thrown when the text is empty.
            return "";
        }
    }

    public String getCompletedDateText()
    {
        try
        {
            return getElement(HeadnotesPageElements.COMPLETED_DATE_XPATH).getText();
        }
        catch(JavascriptException e)
        {
            //There's a JavascriptException unable to get element text that is thrown when the text is empty.
            return "";
        }
    }
}
