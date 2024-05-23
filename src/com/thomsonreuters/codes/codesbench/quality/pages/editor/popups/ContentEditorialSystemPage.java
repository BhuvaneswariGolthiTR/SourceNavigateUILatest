package com.thomsonreuters.codes.codesbench.quality.pages.editor.popups;

import com.thomsonreuters.codes.codesbench.quality.pageelements.CommonPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.popups.ContentEditorialSystemPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.navigate.popups.OnlineProductAssignmentsPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import javax.annotation.PostConstruct;

@Component
public class ContentEditorialSystemPage extends BasePage
{
	private WebDriver driver;
	
	@Autowired
	public ContentEditorialSystemPage(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
	}
	
	@PostConstruct
	public void init()
    {
        PageFactory.initElements(driver, ContentEditorialSystemPageElements.class);
    }
	
	public void setMnemonic(String mnemonic)
	{
		click(ContentEditorialSystemPageElements.MNEMONIC_DROPDOWN_BUTTON);
		click(String.format(ContentEditorialSystemPageElements.MNEMONIC_HINT_EXPECTED, mnemonic));
	}
	
	public void clickSave()
	{
		click(CommonPageElements.SAVE_BUTTON);
		waitForWindowGoneByTitle(OnlineProductAssignmentsPageElements.PAGE_TITLE);
		editorPage().switchToEditor();
		editorTextPage().switchToEditorTextArea();
	}

	public boolean areMnemonicsInDropdown(String[] mnemonics)
	{
		click(ContentEditorialSystemPageElements.MNEMONIC_DROPDOWN_BUTTON);
		waitForElement(ContentEditorialSystemPageElements.MNEMONIC_HINT_LIST);
		return Arrays
			.stream(mnemonics).allMatch(mnemonic -> doesElementExist(String.format(ContentEditorialSystemPageElements.MNEMONIC_HINT_EXPECTED, mnemonic)));
	}

	public void selectMnemonic(String mnemonic)
	{
		sendTextToTextbox(ContentEditorialSystemPageElements.MNEMONIC_INPUT_SELECT, mnemonic);
		waitForElement(String.format(ContentEditorialSystemPageElements.MNEMONIC_HIGHLIGHTED_HINT, mnemonic));
		sendEnterToElement(ContentEditorialSystemPageElements.MNEMONIC_INPUT_SELECT);
	}

	public boolean englishLabelTextEquals(String text)
	{
		return checkFieldValueIsExpectedOne(getElement(ContentEditorialSystemPageElements.ENGLISH_LABEL), text);
	}

	public void clickCancel()
	{
		click(CommonPageElements.CANCEL_BUTTON);
		waitForWindowGoneByTitle(OnlineProductAssignmentsPageElements.PAGE_TITLE);
		editorPage().switchToEditor();
		editorTextPage().switchToEditorTextArea();
	}

	public void selectStockNote(ContentEditorialSystemPageElements.StockNote stockNote)
	{
		sendKeysToElement(ContentEditorialSystemPageElements.STOCKNOTE_INPUT, stockNote.getName());
	}

	public void switchToContentEditorialSystemWindow()
	{
		switchToWindow(ContentEditorialSystemPageElements.PAGE_TITLE);
		enterTheInnerFrame();
	}
}
