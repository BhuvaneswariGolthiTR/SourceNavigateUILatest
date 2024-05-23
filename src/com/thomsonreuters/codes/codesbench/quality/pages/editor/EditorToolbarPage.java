package com.thomsonreuters.codes.codesbench.quality.pages.editor;

import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorToolbarPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.popups.*;
import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.HierarchyPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.stocknotemanager.StocknoteManagerPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class EditorToolbarPage extends BasePage
{
	private WebDriver driver;
	
	@Autowired
	public EditorToolbarPage(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
	}
	
	@PostConstruct
	public void init()
    {
        PageFactory.initElements(driver, EditorToolbarPageElements.class);
    }

	public void clickToolbarButton(WebElement toolbarButton)
	{
		breakOutOfFrame();
		switchToWindow(EditorPageElements.PAGE_TITLE);
		click(toolbarButton);
	}
	
	public void clickSave()
	{
		clickToolbarButton(EditorToolbarPageElements.toolbarSaveButton);
	}

	public void clickInsertMarkup()
	{
		clickToolbarButton(EditorToolbarPageElements.toolbarInsertMarkupButton);
		switchToWindow(InsertWestMarkupPageElements.WINDOW_TITLE);
		enterTheInnerFrame();
	}
	
	public void clickUndo()
	{
		clickToolbarButton(EditorToolbarPageElements.toolbarUndoButton);
	}
	
	public void clickRedo()
	{
		clickToolbarButton(EditorToolbarPageElements.toolbarRedoButton);
	}

	public void clickAddMarkup() {clickToolbarButton(EditorToolbarPageElements.toolbarAddMarkupButton);}
	
	public void clickAdvancedSearchAndReplace()
	{
		clickToolbarButton(EditorToolbarPageElements.toolbarAdvancedSearchAndReplaceButton);
		waitForPageLoaded();
		Assertions.assertTrue(switchToWindow(AdvancedSearchAndReplaceElements.PAGE_TITLE));
		enterTheInnerFrame();
	}

	public void clickConfirmLinkMarkup()
	{
		clickToolbarButton(EditorToolbarPageElements.toolbarConfirmLinkMarkupButton);
	}
	
	public boolean clickValidate()
	{
		clickToolbarButton(EditorToolbarPageElements.toolbarValidateButton);
		return acceptAlertNoFail();
	}

	public boolean clickSubsectionValidation()
	{
		clickToolbarButton(EditorToolbarPageElements.toolbarSubsectionValidationButton);
		return acceptAlertNoFail();
	}

	public boolean clickSubsectionGeneration()
	{
		clickToolbarButton(EditorToolbarPageElements.toolbarSubsectionGenerationButton);
		return acceptAlertNoFail();
	}

	public void clickRebuild()
	{
		clickToolbarButton(EditorToolbarPageElements.toolbarRebuildButton);
	}

	public void clickToolbarCheckSpelling()
	{
		clickToolbarButton(EditorToolbarPageElements.toolbarCheckSpellingButton);
	}
	
	public void clickToolbarClose()
	{
		editorPage().switchToEditor();
		waitForElementExists(EditorToolbarPageElements.CLOSE_DOC);
		click(EditorToolbarPageElements.CLOSE_DOC);
		DateAndTimeUtils.takeNap(DateAndTimeUtils.FIVE_SECONDS);
		switchToWindow(DocumentClosurePageElements.PAGE_TITLE);
		waitForElementToBeClickable(DocumentClosurePageElements.DOCUMENT_NAME);
	}
	
	public boolean isToolbarCloseDocButtonDisabled()
	{
		return isElementDisabled(EditorToolbarPageElements.toolbarCloseDocButton);
	}

	public boolean isToolbarUndoButtonDisabled()
	{
		return isElementDisabled(EditorToolbarPageElements.toolbarUndoButton);
	}
	
	public void clickToolbarCloseNoChanges()
	{
		clickToolbarButton(EditorToolbarPageElements.toolbarCloseDocButton);
		switchToWindow(HierarchyPageElements.PAGE_TITLE);
	}

	public boolean isPubtagRefreshButtonEnabled()
	{
		String classAttr = EditorToolbarPageElements.toolbarPubtagRefreshButton.getAttribute("class");
		if ((classAttr != null) && classAttr.contains("disabled"))
		{
			return true;
		}

		String disabledAttr = EditorToolbarPageElements.toolbarPubtagRefreshButton.getAttribute("disabled");
		return (disabledAttr != null) && (disabledAttr.contains("true") || disabledAttr.contains("disabled") || disabledAttr.contains("Disabled"));
	}

	public void clickConfigureEditorSessionPreferences()
	{
		click(EditorToolbarPageElements.toolbarConfigureEditorSessionPreferencesButton);
		switchToWindow(EditorPreferencesPageElements.PAGE_TITLE);
	}

	public void clickCheckSpelling()
	{
		breakOutOfFrame();
		click(EditorToolbarPageElements.toolbarCheckSpellingButton);
	}

	public void clickWordsAndPhrases()
	{
		click(EditorToolbarPageElements.toolbarWordsAndPhrasesMarkupButton);
	}

	public void clickPubtagRefreshButton()
	{
		click(EditorToolbarPageElements.toolbarPubtagRefreshButton);
		waitForPageLoaded();
	}

	public void clickInsertTable()
	{
		click(EditorToolbarPageElements.toolbarInsertTableButton);
		switchToWindow(InsertTablePageElements.PAGE_TITLE);
		enterTheInnerFrame();
	}

    public void clickBold()
        {
        	click(EditorToolbarPageElements.toolbarBoldMarkupButton);
        }

	public void clickItalic()
	{
		click(EditorToolbarPageElements.toolbarItalicMarkupButton);
	}

	public void clickDelete()
	{
		clickToolbarButton(EditorToolbarPageElements.toolbarDeleteMarkupButton);
	}

	public void clickRemoveAddDelete()
	{
		clickToolbarButton(EditorToolbarPageElements.toolbarRemoveAddDeleteButton);
	}

	public void clickStocknoteManager()
	{
		clickToolbarButton(EditorToolbarPageElements.toolbarStocknoteManagerButton);
		switchToWindow(StocknoteManagerPageElements.PAGE_TITLE);
		enterTheInnerFrame();
	}

	public void clickInsertSpecialCharacter()
	{
		click(EditorToolbarPageElements.toolbarInsertSpecialCharacterButton);
		switchToWindow(InsertSpecialCharacterPageElements.PAGE_TITLE);
	}

	public void clickCut()
	{
		click(EditorToolbarPageElements.toolbarCutButton);
	}

	public void clickPaste()
	{
		click(EditorToolbarPageElements.toolbarPasteButton);
	}

	public void clickCopy()
	{
		click(EditorToolbarPageElements.toolbarCopyButton);
	}

	public void clickPromote()
	{
		clickToolbarButton(EditorToolbarPageElements.toolbarPromoteButton);
	}

	public void clickDemote()
	{
		clickToolbarButton(EditorToolbarPageElements.toolbarDemoteButton);
	}

	public void clickInsertStatPages()
	{
		clickToolbarButton(EditorToolbarPageElements.toolbarInsertStatPagesButton);
	}

	public void clickFootnoteReorder()
	{
		clickToolbarButton(EditorToolbarPageElements.toolbarFootnoteReorderButton);
	}
}
