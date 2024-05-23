package com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.target.set2;

import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.popups.ChangeSourceTagPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import com.thomsonreuters.codes.codesbench.quality.pages.editor.EditorTextPage;
import com.thomsonreuters.codes.codesbench.quality.pages.editor.EditorToolbarPage;
import com.thomsonreuters.codes.codesbench.quality.pages.editor.popups.ChangeSourceTagPage;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

public class ChangeSourceTagsTests extends TestService
{
	/* Change source tag
	 * 1. Open document
	 * 2. Scroll down 2 or 3 chunks
	 * 3. Right click the source tag on a text paragraph
	 * - Source tag is the green colored tag located next to the english label
	 * VERIFY: Change Sourcetag window appears
	 * 4. Select one of the source tags from the dropdown
	 * 5. Click Ok
	 * VERIFY: Change Sourcetag window no longer appears
	 * VERIFY: The source tag selected replaced the existing source tag
	 * VERIFY: There are no errors in the editor message pane
	 */
	@Test
	@IE_EDGE_MODE
	@LEGAL
	@LOG
	public void changeSourceTagsTargetLegalTest()
	{
		String nodeUuid = "I2A8B5B9014F511DA8AC5CD53670E6B4E";
		int targetChunkNumber = 1;
		String newSourceTag = "17";
		String modifiedByTag = editorTextPage().getModifiedByTag(this.user());

		// open DS editor
		homePage().goToHomePage();
		loginPage().logIn();
		hierarchyNavigatePage().goToHierarchyPage(ContentSets.IOWA_DEVELOPMENT.getCode());
		hierarchySearchPage().searchNodeUuid(nodeUuid);
		siblingMetadataPage().selectedSiblingMetadataEditContent();
		
		// scroll to chunk
		editorPage().switchToEditorAndScrollToChunk(targetChunkNumber);
		
		// change source tag
		String existedSourcetag = editorPage().getTextOfTextParagraphSourceTagUnderNumber(1);
		editorTextPage().waitForPageLoaded();
		editorTextPage().scrollToElement(String.format(EditorTextPageElements.TEXT_PARAGRAPH_SOURCE_TAG_UNDER_NUMBER, 1));
		editorTextPage().rightClick(String.format(EditorTextPageElements.TEXT_PARAGRAPH_SOURCE_TAG_UNDER_NUMBER, 1));
		boolean changeSourceTagWindowAppeared = changeSourceTagPage().switchToWindow(ChangeSourceTagPageElements.CHANGE_SOURCETAG_PAGE_TITLE);
		Assertions.assertTrue(changeSourceTagWindowAppeared, "Change SourceTag window should appear");
		changeSourceTagPage().selectDropdownOptionUsingJavascript("selectSourceTag","17");
		changeSourceTagPage().clickOk();
		
		// verify mnemonics and other information
		editorPage().switchDirectlyToTextFrame();
		editorPage().waitForPageLoaded();
		String sourceTagAfterChange = editorPage().getTextOfTextParagraphSourceTagUnderNumber(1);
		boolean sourceTagChanged = !existedSourcetag.equals(sourceTagAfterChange) && sourceTagAfterChange.equals(newSourceTag);
		boolean modifiedByPopulatedCorrectly = editorPage().checkForModifiedByTextInTextParagraphNumber(modifiedByTag, 1);
		
		//System.out.println("pre: " + existedSourcetag + " new: " + sourceTagAfterChange);
		// close editor
		editorPage().closeDocumentAndDiscardChanges();
		
		Assertions.assertAll
		(
			() -> Assertions.assertTrue(sourceTagChanged, "Source tag should be changed"),
			() -> Assertions.assertTrue(modifiedByPopulatedCorrectly, "Modified By tag should contain right name and date")
		);
	}
}
