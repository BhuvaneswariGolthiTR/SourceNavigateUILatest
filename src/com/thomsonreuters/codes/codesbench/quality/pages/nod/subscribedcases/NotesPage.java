package com.thomsonreuters.codes.codesbench.quality.pages.nod.subscribedcases;

import javax.annotation.PostConstruct;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thomsonreuters.codes.codesbench.quality.pageelements.CommonPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.nod.subscribedcases.SubscribedCasesPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.nod.subscribedcases.NotesPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;

@Component
public class NotesPage extends BasePage
{
	WebDriver driver;

	@Autowired
	public NotesPage(WebDriver driver) 
	{
		super(driver);
		this.driver = driver;
	}
	
	@PostConstruct
	public void init()
	{
		PageFactory.initElements(driver, NotesPageElements.class);
		PageFactory.initElements(driver, CommonPageElements.class);
	}

	public void addNote(String text)
	{
		sendKeysToElement(NotesPageElements.noteTextArea,text);
	}
	
	public boolean doesGivenCaseHaveNote(String serialNumber)
	{
		return doesElementExist(String.format(SubscribedCasesPageElements.NOTE_IMG_OF_NOTE_WITH_GIVEN_SERIAL_NUMBER, serialNumber));
	}
	
	public void addTextToNote(String text)
	{
		sendKeysToElement(NotesPageElements.noteTextArea, text);
	}
	
	public void clearTextArea()
	{
		NotesPageElements.noteTextArea.clear();
	}
    
    public void clickCancel()
    {
    	click(CommonPageElements.CANCEL_BUTTON);
		breakOutOfFrame();
		waitForWindowGoneByTitle(NotesPageElements.NOTES_PAGE_TITLE);
    }
    
    public void clickSave()
    {
    	click(CommonPageElements.SAVE_BUTTON);
		breakOutOfFrame();
		waitForWindowGoneByTitle(NotesPageElements.NOTES_PAGE_TITLE);
    }
   
    public String getNoteText()
    {
    	return getElementsText(NotesPageElements.noteTextArea);
    }
}
