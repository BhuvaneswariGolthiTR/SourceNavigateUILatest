package com.thomsonreuters.codes.codesbench.quality.pages.nod.subscribedcases;

import javax.annotation.PostConstruct;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thomsonreuters.codes.codesbench.quality.pageelements.nod.subscribedcases.SubscribedCasesPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.nod.headnotes.HeadnotesPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.nod.subscribedcases.NotesPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;

@Component
public class SubscribedCasesGridPage extends BasePage
{
	WebDriver driver;
	
	@Autowired
	public SubscribedCasesGridPage(WebDriver driver) 
	{
		super(driver);
		this.driver = driver;
	}
	
	@PostConstruct
	public void init()
	{
		PageFactory.initElements(driver, SubscribedCasesPageElements.class);
	}
	
	public String getCaseSerialNumberFromSubscribedCasesByGivenRow(int row)
    {
    	return getElementsText(String.format(SubscribedCasesPageElements.CASE_IN_A_ROW_WITH_NUMBER_GIVEN, row));
    }

	public boolean clickCaseSerialNumberLink(String number)
	{
		sendEnterToElement(String.format(SubscribedCasesPageElements.CASE_WITH_A_NUMBER_GIVEN, number));
		return switchToWindow(HeadnotesPageElements.HEADNOTES_PAGE_TITLE);
	}
	
	public void waitForElementGoneForGivenCaseSerialNumber(String serialNumber)
    {
    	waitForElementGone(String.format(SubscribedCasesPageElements.NOTE_IMG_OF_NOTE_WITH_GIVEN_SERIAL_NUMBER, serialNumber));
    }
    
    public boolean clickAddNoteLinkForCaseWithGivenCaseSerialNumber(String caseNumber)
    {
    	click(String.format(SubscribedCasesPageElements.ADD_NOTE_LINK_OF_NOTE_WITH_GIVEN_SERIAL_NUMBER, caseNumber));
    	boolean windowAppeared = switchToWindow(NotesPageElements.NOTES_PAGE_TITLE);
		enterTheInnerFrame();
		return windowAppeared;
    }
    
    public boolean clickNoteLinkOfCaseWithGivenCaseSerialNumber(String serialNumber)
    {
    	click(String.format(SubscribedCasesPageElements.NOTE_IMG_OF_NOTE_WITH_GIVEN_SERIAL_NUMBER, serialNumber));
    	boolean windowAppeared = switchToWindow(NotesPageElements.NOTES_PAGE_TITLE);
    	enterTheInnerFrame();
    	return windowAppeared;
    }
    
    public String getCaseSerialNumberOfCaseWithoutNote()
    {
    	return getElementsText(SubscribedCasesPageElements.caseWithoutNoteSerial);
    }
    
    public boolean clickAddNoteLinkToCaseWithGivenCaseSerialNumber(String serialNumber)
    {
    	click(String.format(SubscribedCasesPageElements.ADD_NOTE_LINK_OF_NOTE_WITH_GIVEN_SERIAL_NUMBER, serialNumber));
    	boolean windowAppeared = switchToWindow(NotesPageElements.NOTES_PAGE_TITLE);
    	enterTheInnerFrame();
    	return windowAppeared;
    }
    
	public boolean doesGivenCaseHaveAddNoteLink(String serialNumber)
	{
		return doesElementExist(String.format(SubscribedCasesPageElements.ADD_NOTE_LINK_OF_NOTE_WITH_GIVEN_SERIAL_NUMBER, serialNumber));
	}
	
	public boolean doesGivenCaseHaveNote(String serialNumber)
	{
		return doesElementExist(String.format(SubscribedCasesPageElements.NOTE_IMG_OF_NOTE_WITH_GIVEN_SERIAL_NUMBER, serialNumber));
	}
	public boolean verifySignedOutByPopulated(final String serialNumber, final String initials)
    {
    	return getElementsText((String.format(SubscribedCasesPageElements.CASE_WITH_NUMBER_SIGN_OUT_BY, serialNumber))).equals(initials);
    }
}
