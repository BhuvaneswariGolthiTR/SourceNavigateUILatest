package com.thomsonreuters.codes.codesbench.quality.pages.editor;

import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorMessagePageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;

@Component
public class EditorMessagePage extends BasePage
{

	private WebDriver driver;

	@Autowired
	public EditorMessagePage(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
	}

	@PostConstruct
	public void init()
	{
		PageFactory.initElements(driver, EditorMessagePageElements.class);
	}

	public boolean checkLastInfoMessageSpellcheckOk()
	{
		waitForPageLoaded();
		switchToWindow(EditorPageElements.PAGE_TITLE);
		String allMessagesText = getElementsText(EditorMessagePageElements.MESSAGE_AREA);
		String lastMessageText = allMessagesText.substring(allMessagesText.lastIndexOf(':')+2);
		DateAndTimeUtils.takeNap(DateAndTimeUtils.THREE_SECONDS);
		return allMessagesText.contains("Spell checking completed with no words identified as spelled incorrectly");
	}

	public boolean checkMessagePaneForText(String string) 
	{
		return getElementsText(EditorMessagePageElements.MESSAGE_AREA).contains(string);
	}

	public String getMessage()
	{
		return getElementsText(EditorMessagePageElements.MESSAGE_AREA);
	}

	public int countMatchesTextInMessagePane(String string)
	{
		String message = getElementsText(EditorMessagePageElements.MESSAGE_AREA);
		return StringUtils.countMatches(message,string);
	}

	public String clickAffectedIds()
	{
		String ids = getElementsText(EditorMessagePageElements.IDS);
		click(EditorMessagePageElements.IDS);
		return ids;
	}

	public boolean checkForErrorInMessagePane()
	{
		return getElementsText(EditorMessagePageElements.MESSAGE_AREA).toLowerCase().contains("error");
	}

	public boolean checkForErrorInBothMessagePanes()
	{
		return getElementsTextList(EditorMessagePageElements.MESSAGE_AREA).stream().anyMatch("error"::contains);
	}

	public boolean checkForWarningInMessagePane()
	{
		return getElementsText(EditorMessagePageElements.MESSAGE_AREA).toLowerCase().contains("warning");
	}

	public boolean checkMessage(String... messages)
	{
		String messageFromEditor = getElement(EditorMessagePageElements.LOG_MESSAGE_AREA).getText();
		return Arrays.stream(messages).allMatch(messageFromEditor::contains);
	}

}
