package com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.target.set2;

import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorToolbarPageElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

public class HotkeyNavigationLegalTests extends TestService
{
	@Test
	@IE_EDGE_MODE
	@LEGAL
	@LOG
	public void dynamicScrollingCtrlHomeCtrlEndHotkeysTargetLegalTest()
	{
		String nodeUuid = "I0929777014F011DA8AC5CD53670E6B4E"; // this doc has 4 chunks
		int targetChunkNumber = 3; // we need to scroll to the middle chunk
		
 		// open DS editor
		homePage().goToHomePage();
		loginPage().logIn();
		hierarchyNavigatePage().goToHierarchyPage(ContentSets.IOWA_DEVELOPMENT.getCode());
		hierarchySearchPage().searchNodeUuid(nodeUuid);
		siblingMetadataPage().selectedSiblingMetadataEditContent();
		
		// scroll to the middle
		editorPage().switchToEditor();
		int chunkAmount = editorPage().getChunkAmount();

		String firstChunk = String.format(EditorTextPageElements.LOADED_CHUNK, 0) + "/metadata.block";
		String lastChunk = String.format(EditorTextPageElements.LOADED_CHUNK, chunkAmount-1) + "//wlr.note//span";
		editorPage().waitForElementGone(firstChunk);
		// we should expect first and last chunks not to be loaded before hitting hotkeys
		boolean firstAndLastChunkLoaded = editorPage().checkForChunkLoadedByNumber(0) ||
										  editorPage().checkForChunkLoadedByNumber(chunkAmount-1);
				
		Assertions.assertFalse(firstAndLastChunkLoaded, "Need first and last chunks not to be loaded before hitting hotkeys. Check chunk amount and target chunk!");
		editorPage().scrollToChunk(targetChunkNumber);

		// ctrl+end to jump to the end
		editorPage().jumpToEndOfDocument();
		editorTextPage().waitForElementExists(lastChunk);
		boolean lastChunkLoaded = editorPage().checkForChunkLoadedByNumber(chunkAmount-1);
		
		// ctrl+home to jump to the beginning
		editorTextPage().sendKeys( Keys.LEFT_CONTROL, Keys.HOME);
		editorTextPage().waitForElementExists(firstChunk);
		boolean firstChunkLoaded = editorPage().checkForChunkLoadedByNumber(0);
    	
		// close editor
		editorPage().closeDocumentWithNoChanges();
		
		Assertions.assertAll
		(
			() -> Assertions.assertTrue(lastChunkLoaded, "Last chunk should be loaded after hitting Ctrl+End"),
			() -> Assertions.assertTrue(firstChunkLoaded, "First chunk should be loaded after hitting Ctrl+Home")
		);
	}
	/*
       ADO 353434: Dynamic Scrolling - Hotkey remapping for Ctrl-W and Ctrl-T
   */
	@Test
	@IE_EDGE_MODE
	@LEGAL
	@LOG
	public void ctrlWCtrlTHotkeysTargetTest()
	{
		String nodeUuid = "I0929777014F011DA8AC5CD53670E6B4E"; // this doc has 4 chunks
		// open DS editor
		homePage().goToHomePage();
		loginPage().logIn();
		hierarchyNavigatePage().goToHierarchyPage(ContentSets.IOWA_DEVELOPMENT.getCode());
		hierarchySearchPage().searchNodeUuid(nodeUuid);
		siblingMetadataPage().selectedSiblingMetadataEditContent();
		editorPage().switchToEditor();

		//To Open new window
		editorTextPage().ctrlTUsingAction();
		boolean windowOpened = editorPage().switchToWindowWithNoTitle(2);
		editorPage().switchToWindowWithNoTitle(1);

		//To close the current window
		editorTextPage().ctrlWUsingAction();
		boolean windowClosed =  editorPage().waitForElementGone(EditorToolbarPageElements.CLOSE_DOC);
		Assertions.assertAll
				(
						() -> Assertions.assertFalse(windowOpened, "There is no New tab"),
						() -> Assertions.assertTrue(windowClosed, "Current window  not closed")
				);
	}
}
