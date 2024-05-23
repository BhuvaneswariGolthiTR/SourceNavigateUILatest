package com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.target.set3;

import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ValidationTests extends TestService
{
	/* 
	 * 1. Open the document
	 * 2. Scroll to chunk 2 or 3
	 * 3. Click the validate button
	 * 4. Verify: The validation services kick off and complete successfully
	 * 5. Delete the Cornerpiece element from the content (This will produce a validation error)
	 * 6. Scroll to chunk 2 or 3
	 * 7. Click the validate button
	 * 8. Verify: (If this document is valid in the first place...) The validation services kick off and fail validation,
	 * 		we should see "Data error id[925]: This document is missing a cornerpiece"
	 * 9. Hit Undo and Validate again
	 * Verify: we should see no Validation errors
	 */
	@Test
	@IE_EDGE_MODE
	@LEGAL
	@LOG
	public void dynamicScrollingValidationTargetLegalTests()  
	{
		String contentSetNumber = ContentSets.IOWA_DEVELOPMENT.getCode();
		String nodeUuid = "I61494D20216C11DBADB8A540E47389CD";
		
		String errorMessage = ": Data error id[925]: This document is missing a Cornerpiece.";
		
		// open DS editor
		hierarchyNavigatePage().goToHierarchyPage(contentSetNumber);
		loginPage().logIn();
		hierarchyNavigatePage().handleCasIssueWithHierarchyPageOpenedAsStartPageIfOccurred(ContentSets.IOWA_DEVELOPMENT);
		hierarchySearchPage().searchNodeUuid(nodeUuid);
		siblingMetadataPage().selectedSiblingMetadataEditContent();
 		editorPage().switchToEditor();
		
		// scroll to chunk
 		editorPage().scrollToChunk(1);
		
		// validate before we've deleted a cornerpiece
 		editorToolbarPage().clickValidate();
		boolean errorAppearedBeforeDeleting = editorMessagePage().checkMessagePaneForText(errorMessage);
		
		// delete a cornerpiece and validate -- alert and log error expected
		editorPage().switchToEditorTextFrame();
		editorPage().deleteTextElement(EditorTextPageElements.CORNERPIECE_SPAN);
		boolean validationAlertAppeared = editorToolbarPage().clickValidate();
		
		editorPage().switchToEditor();
    	boolean errorAppearedAfterDeleting = editorMessagePage().checkMessagePaneForText(errorMessage);
		
		// undo and then validate again
		editorToolbarPage().clickSave();
		editorToolbarPage().clickUndo();
		boolean alertAppearedAfterUndoingDeleting = editorToolbarPage().clickValidate();

		// close editor
		editorPage().closeDocumentAndDiscardChanges();
		
		Assertions.assertAll
		(
				() -> Assertions.assertFalse(errorAppearedBeforeDeleting, "Validation error should not appear before cornerpiece deleting"),
				() -> Assertions.assertTrue(validationAlertAppeared, "Alert about validation error should appear"),
				() -> Assertions.assertTrue(errorAppearedAfterDeleting, "Validation error should appear after cornerpiece deleting"),
				() -> Assertions.assertFalse(alertAppearedAfterUndoingDeleting, "No alert should appear after validating after undoing")
				
		);
	}
}
