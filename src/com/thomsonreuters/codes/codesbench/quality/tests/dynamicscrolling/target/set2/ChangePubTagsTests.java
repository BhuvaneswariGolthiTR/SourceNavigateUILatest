package com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.target.set2;

import com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.editor.EditorTextContextMenuElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.popups.AddPubtagPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.popups.DeletePubtagPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.popups.RemovePubtagElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.pubtagsInFirstTextParaMetadataBlock;

public class ChangePubTagsTests extends TestService
{
	/* Change pubtag plus
	 * 1. Open document
	 * 2. scroll down 2 or 3 chunks
	 * 3. Right click the pubtag of a text paragraph here
	 * - The pubtag is the orange colored tag next to the english label
	 * 4. Select Plus Tag
	 * VERIFY: Add Pubtag window appears
	 * 5. Select one of the available pubtags and move it to the selected pubtags
	 * 6. Click Ok
	 * VERIFY: Add Pubtag window no longer appears
	 * VERIFY: The pubtag selected is added to the selected text paragraph and has a +
	 *         character next to it
	 * VERIFY: There are no errors in the editor message pane
	 */
	@Test
	@IE_EDGE_MODE
	@LEGAL
	@LOG
	public void changePubtagPlusTargetLegalTest()
	{
		String nodeUuid = "I2A8B5B9014F511DA8AC5CD53670E6B4E";
		int targetChunkNumber = 1;
		String pubtagToPlus = "ANIP";
		String modifiedByTag = editorTextPage().getModifiedByTag(this.user());

		// open DS editor
		homePage().goToHomePage();
		loginPage().logIn();
		hierarchyNavigatePage().goToHierarchyPage(ContentSets.IOWA_DEVELOPMENT.getCode());
		hierarchySearchPage().searchNodeUuid(nodeUuid);
		siblingMetadataPage().selectedSiblingMetadataEditContent();
		
		// scroll to chunk
 		editorPage().scrollToChunk(targetChunkNumber);
		
		// change source tag
		editorTextPage().scrollToView(pubtagsInFirstTextParaMetadataBlock);
		editorTextPage().rightClick(pubtagsInFirstTextParaMetadataBlock);
		editorTextPage().breakOutOfFrame();
		editorTextContextMenu().openContextMenu(EditorTextContextMenuElements.pubTagPlus);
		boolean addPubtagWindowAppeared = addPubtagPage().switchToWindow(AddPubtagPageElements.PAGE_TITLE);
		Assertions.assertTrue(addPubtagWindowAppeared, "Add Pubtag window should appear");

		addPubtagPage().selectAvailablePubtags(pubtagToPlus);
		
		// verify mnemonics and other information
		editorPage().switchToEditor();
		boolean doesMessagePaneContainError = editorMessagePage().checkForErrorInMessagePane();
		editorPage().switchToEditorTextFrame();
		
		boolean newPubTagAppeared = editorPage().checkForAddedPubtagsInEditorText(pubtagToPlus);
		
		boolean modifiedByPopulatedCorrectly = editorPage().checkForModifiedByTextInTextParagraphNumber(modifiedByTag, 1);
		
		// close editor
		editorPage().closeDocumentAndDiscardChanges();
		
		Assertions.assertAll
		(
			() -> Assertions.assertTrue(newPubTagAppeared, "New Pubtag should appear with +"),
			() -> Assertions.assertTrue(modifiedByPopulatedCorrectly, "Modified By tag should contain right name and date"),
			() -> Assertions.assertFalse(doesMessagePaneContainError, "No error message should appear")
		);
	}

	/* Change pubtag minus
	 * 1. Open document
	 * 2. scroll down 2 or 3 chunks
	 * 3. Right click the pubtag of a text paragraph here
	 * - The pubtag is the orange colored tag next to the english label
	 * 4. Select Minus Tag
	 * VERIFY: Delete Pubtag window appears
	 * 5. Select one of the available pubtags and move it to the selected pubtags
	 * 6. Click Ok
	 * VERIFY: Delete Pubtag window no longer appears
	 * VERIFY: The pubtag selected is added to the selected text paragraph and has a -
	 *         character next to it
	 * VERIFY: There are no errors in the editor message pane
	 */
	@Test
	@IE_EDGE_MODE
	@LEGAL
	@LOG
	public void changePubtagMinusTargetLegalTest()
	{
		String nodeUuid = "I2A8B5B9014F511DA8AC5CD53670E6B4E";
		int targetChunkNumber = 1;
		String pubtagToMinus = "ANIP";
		String modifiedByTag = editorTextPage().getModifiedByTag(this.user());

		// open DS editor
		homePage().goToHomePage();
		loginPage().logIn();
		hierarchyNavigatePage().goToHierarchyPage(ContentSets.IOWA_DEVELOPMENT.getCode());
		hierarchySearchPage().searchNodeUuid(nodeUuid);
		siblingMetadataPage().selectedSiblingMetadataEditContent();
				
		// scroll to chunk
 		editorPage().scrollToChunk(targetChunkNumber);
		
		// change source tag
		editorTextPage().scrollToView(pubtagsInFirstTextParaMetadataBlock);
		editorTextPage().rightClick(pubtagsInFirstTextParaMetadataBlock);
		editorTextPage().breakOutOfFrame();
		editorTextContextMenuPage().openContextMenu(EditorTextContextMenuElements.pubTagMinus);
		boolean addPubtagWindowAppeared = deletePubtagPage().switchToWindow(DeletePubtagPageElements.PAGE_TITLE);
		Assertions.assertTrue(addPubtagWindowAppeared, "Delete Pubtag window should appear");
		deletePubtagPage().selectAvailablePubtags(pubtagToMinus);
		
		// verify mnemonics and other information
		editorPage().switchToEditor();
		boolean doesMessagePaneContainError = editorMessagePage().checkForErrorInMessagePane();
		editorPage().switchToEditorTextFrame();
		
		boolean newPubTagAppeared = editorPage().checkForDeletedPubtagsInEditorText(pubtagToMinus);
		
		boolean modifiedByPopulatedCorrectly = editorPage().checkForModifiedByTextInTextParagraphNumber(modifiedByTag, 1);

		// close editor
		editorPage().closeDocumentAndDiscardChanges();
		
		Assertions.assertAll
		(
			() -> Assertions.assertTrue(newPubTagAppeared, "New Pubtag should appear with -"),
			() -> Assertions.assertTrue(modifiedByPopulatedCorrectly, "Modified By tag should contain right name and date"),
			() -> Assertions.assertFalse(doesMessagePaneContainError, "No error message should appear")
		);
	}
	
	/* Change pubtag remove
	 * 1. Open document
	 * 2. scroll down 2 or 3 chunks
	 * 3. Right click the pubtag of a text paragraph here
	 * - The pubtag is the orange colored tag next to the english label
	 * 4. Select Remove Tag
	 * VERIFY: Remove Pubtag window appears
	 * VERIFY: The available pubtag list is populated with those from the text paragraph
	 * 5. Select one of the available pubtags and move it to the selected pubtags
	 * 6. Click Ok
	 * VERIFY: Remove Pubtag window no longer appears
	 * VERIFY: The pubtag selected no longer appears on the text paragraph
	 * VERIFY: There are no errors in the editor message pane
	 */
	@Test
	@IE_EDGE_MODE
	@LEGAL
	@LOG
	public void changePubtagRemoveTargetLegalTest()
	{
		String nodeUuid = "I2A8B5B9014F511DA8AC5CD53670E6B4E";
		int targetChunk = 1;
		String modifiedByTag = editorTextPage().getModifiedByTag(this.user());

		// open DS editor
		homePage().goToHomePage();
		loginPage().logIn();
		hierarchyNavigatePage().goToHierarchyPage(ContentSets.IOWA_DEVELOPMENT.getCode());
		hierarchySearchPage().searchNodeUuid(nodeUuid);
		siblingMetadataPage().selectedSiblingMetadataEditContent();
				
		// scroll to chunk
 		editorPage().scrollToChunk(targetChunk);

		String firstPara = String.format(EditorTextPageElements.TEXT_PARAGRAPH_FROM_CHUNK,
				targetChunk - 1, 1);
		String firstParaPubtags = firstPara + EditorTextPageElements.ANY_PUBTAG_IN_METADATA_BLOCK;

 		// change source tag
		editorTextPage().waitForElementExists(firstParaPubtags);
		List<String> pubTagsListBeforeRemovingTag = editorTextPage().getTextOfElementsToList(firstParaPubtags);
		String pubtagToRemove = pubTagsListBeforeRemovingTag.get(0);

		editorTextPage().scrollToView(EditorTextPageElements.pubtagsText);
		editorTextPage().rightClick(EditorTextPageElements.pubtagsText);
		editorTextPage().breakOutOfFrame();
		editorTextContextMenuPage().openContextMenu(EditorTextContextMenuElements.pubTagRemove);
		boolean addPubtagWindowAppeared = removePubtagPage().switchToWindow(RemovePubtagElements.PAGE_TITLE);
		Assertions.assertTrue(addPubtagWindowAppeared, "Remove Pubtag window should appear");
		removePubtagPage().selectAvailablePubtags(pubtagToRemove);
		
		// verify mnemonics and other information
		editorPage().switchToEditor();
		boolean doesMessagePaneContainError = editorMessagePage().checkForErrorInMessagePane();
		editorPage().switchToEditorTextFrame();
		
		List<String> pubTagsListAfterRemovingTag = editorTextPage().getTextOfElementsToList(firstParaPubtags);
		
		boolean pubTagDisappeared = pubTagsListBeforeRemovingTag.contains(pubtagToRemove)
				&& !pubTagsListAfterRemovingTag.contains(pubtagToRemove);
		
		boolean modifiedByPopulatedCorrectly = editorPage().checkForModifiedByTextInTextParagraphNumber(modifiedByTag, 1);
		
		// close editor
		editorPage().closeDocumentAndDiscardChanges();
		Assertions.assertAll
		(
			() -> Assertions.assertTrue(pubTagDisappeared, "Pubtag should disappear"),
			() -> Assertions.assertTrue(modifiedByPopulatedCorrectly, "Modified By tag should contain right name and date"),
			() -> Assertions.assertFalse(doesMessagePaneContainError, "No error message should appear")
		);
	}
}