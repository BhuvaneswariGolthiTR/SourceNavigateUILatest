package com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.target.set1;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.TargetDataMockingNew;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.datapod.HierarchyDatapodObject;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorToolbarPageElements.*;

public class EnabledAndDisabledToolbarButtonTargetLegalTest extends TestService
{

	HierarchyDatapodObject datapodObject;
	
	/*
	 * verify that the following toolbar buttons are disabled upon editor content load:
	 * rebuild, insert stat pages, undo, redo
	 */
	
	@Test
	@IE_EDGE_MODE
	@LEGAL
	@LOG
	public void enabledAndDisabledToolbarButtonTargetLegalTest()
	{
		datapodObject = TargetDataMockingNew.Iowa.Small.insert();
 		String nodeUuid = datapodObject.getSections().get(0).getNodeUUID();
 		
 		// open DS editor
		homePage().goToHomePage();
		loginPage().logIn();
		hierarchyNavigatePage().goToHierarchyPage(ContentSets.IOWA_DEVELOPMENT.getCode());
		hierarchySearchPage().searchNodeUuid(nodeUuid);
		siblingMetadataPage().selectedSiblingMetadataEditContent();
		
		//Close Doc enabled
		boolean closeDocButtonDisabled = editorToolbarPage().isElementDisabled(toolbarCloseDocButton);
		
		//Save enabled
		boolean saveButtonDisabled = editorToolbarPage().isElementDisabled(toolbarSaveButton);
		
		//Validate enabled
		boolean validateButtonDisabled = editorToolbarPage().isElementDisabled(toolbarValidateButton);
		
		//Check Spelling enabled
		boolean checkSpellingButtonDisabled = editorToolbarPage().isElementDisabled(toolbarCheckSpellingButton);
		
		//Rebuild enabled
		boolean rebuildButtonDisabled = editorToolbarPage().isElementDisabled(toolbarRebuildButton);
		
		//Remove Add/Delete disabled
		boolean removeAddDeleteButtonDisabled = editorToolbarPage().isElementDisabled(toolbarRemoveAddDeleteButton);
		
		//Pubtag Refresh disabled
		boolean pubtagRefreshButtonDisabled = editorToolbarPage().isElementDisabled(toolbarPubtagRefreshButton);
		
		//Subsection Generation disabled
		boolean subsectionGenerationButtonDisabled = editorToolbarPage().isElementDisabled(toolbarSubsectionGenerationButton);
		
		//Subsection Validation disabled
		boolean subsectionValidationButtonDisabled = editorToolbarPage().isElementDisabled(toolbarSubsectionValidationButton);
		
		//Credit Generation disabled
		boolean creditGenerationButtonDisabled = editorToolbarPage().isElementDisabled(toolbarCreditGenerationButton);
		
		//Footnote Reorder disabled
		boolean footnoteReorderButtonDisabled = editorToolbarPage().isElementDisabled(toolbarFootnoteReorderButton);
		
		//Insert Stat Pages disabled
		boolean insertStatPagesButtonDisabled = editorToolbarPage().isElementDisabled(toolbarInsertStatPagesButton);
		
		//Advanced SAR disabled
		boolean advancedSarButtonDisabled = editorToolbarPage().isElementDisabled(toolbarAdvancedSearchAndReplaceButton);
		
		//Cut enabled
		boolean cutButtonDisabled = editorToolbarPage().isElementDisabled(toolbarCutButton);
		
		//Copy enabled
		boolean copyButtonDisabled = editorToolbarPage().isElementDisabled(toolbarCopyButton);
		
		//Paste enabled
		boolean pasteButtonDisabled = editorToolbarPage().isElementDisabled(toolbarPasteButton);
		
		//Undo disabled
		boolean undoButtonDisabled = editorToolbarPage().isElementDisabled(toolbarUndoButton);
		
		//Redo disabled
		boolean redoButtonDisabled = editorToolbarPage().isElementDisabled(toolbarRedoButton);
		
		//Stocknote Manager enabled
		boolean stocknoteManagerButtonDisabled = editorToolbarPage().isElementDisabled(toolbarStocknoteManagerButton);
		
		//Configure Editor Session Preferences enabled
		boolean configureEditorSessionPreferencesButtonDisabled = editorToolbarPage().isElementDisabled(toolbarConfigureEditorSessionPreferencesButton);
		
		//Insert Table enabled
		boolean insertTableButtonDisabled = editorToolbarPage().isElementDisabled(toolbarInsertTableButton);
		
		//Insert Special Character enabled
		boolean insertSpecialCharacterButtonDisabled = editorToolbarPage().isElementDisabled(toolbarInsertSpecialCharacterButton);
		
		//Insert Markup enabled
		boolean insertMarkupButtonDisabled = editorToolbarPage().isElementDisabled(toolbarInsertMarkupButton);
		
		//Promote enabled
		boolean promoteButtonDisabled = editorToolbarPage().isElementDisabled(toolbarPromoteButton);
		
		//Demote enabled
		boolean demoteButtonDisabled = editorToolbarPage().isElementDisabled(toolbarDemoteButton);
		
		//Print enabled
		boolean printButtonDisabled = editorToolbarPage().isElementDisabled(toolbarPrintButton);
		
		//Help enabled
		boolean helpButtonDisabled = editorToolbarPage().isElementDisabled(toolbarHelpButton);
		
		//Add enabled
		boolean addButtonDisabled = editorToolbarPage().isElementDisabled(toolbarAddMarkupButton);
		
		//Delete enabled
		boolean deleteButtonDisabled = editorToolbarPage().isElementDisabled(toolbarDeleteMarkupButton);
		
		//Bold enabled
		boolean boldButtonDisabled = editorToolbarPage().isElementDisabled(toolbarBoldMarkupButton);
		
		//Italic enabled
		boolean italicButtonDisabled = editorToolbarPage().isElementDisabled(toolbarItalicMarkupButton);
		
		//Cap conditional enabled
		boolean capConditionalButtonDisabled = editorToolbarPage().isElementDisabled(toolbarCapConditionalMarkupButton);
		
		//Small caps enabled
		boolean smallCapsButtonDisabled = editorToolbarPage().isElementDisabled(toolbarSmallCapsMarkupButton);
		
		//Paragraph id include enabled
		boolean paragraphIdIncludeButtonDisabled = editorToolbarPage().isElementDisabled(toolbarParagraphIdIncludedMarkupButton);
		
		//Paragraph id ignore enabled
		boolean paragraphIdIgnoreButtonDisabled = editorToolbarPage().isElementDisabled(toolbarParagraphIdIgnoreMarkupButton);
		
		//Words & Phrases enabled
		boolean wordsPhrasesButtonDisabled = editorToolbarPage().isElementDisabled(toolbarWordsAndPhrasesMarkupButton);
		
		//Character fill enabled
		boolean characterFillButtonDisabled = editorToolbarPage().isElementDisabled(toolbarCharacterFillMarkupButton);
		
		//Character generate enabled
		boolean characterGenerateButtonDisabled = editorToolbarPage().isElementDisabled(toolbarCharacterGenerateMarkupButton);
		
		//Print suppress enabled
		boolean printSuppressButtonDisabled = editorToolbarPage().isElementDisabled(toolbarPrintSuppressMarkupButton);
		
		//Westlaw suppress enabled
		boolean westlawSuppressButtonDisabled = editorToolbarPage().isElementDisabled(toolbarWestlawSupressMarkupButton);
		
		//Vendor include enabled
		boolean vendorIncludeButtonDisabled = editorToolbarPage().isElementDisabled(toolbarVendorIncludeMarkupButton);
		
		//Case history id enabled
		boolean caseHistoryIdButtonDisabled = editorToolbarPage().isElementDisabled(toolbarCaseHistoryIdMarkupButton);
		
		//End left enabled
		boolean endLeftButtonDisabled = editorToolbarPage().isElementDisabled(toolbarEndLeftMarkupButton);
		
		//End center enabled
		boolean endCenterButtonDisabled = editorToolbarPage().isElementDisabled(toolbarEndCenterMarkupButton);
		
		//End right enabled
		boolean endRightButtonDisabled = editorToolbarPage().isElementDisabled(toolbarEndRightMarkupButton);
		
		//End justify enabled
		boolean endJustifyButtonDisabled = editorToolbarPage().isElementDisabled(toolbarEndJustifyMarkupButton);
		
		//Westlaw Table Code enabled
		boolean westlawTableCodeButtonDisabled = editorToolbarPage().isElementDisabled(toolbarWestlawTableCodeMarkupButton);
		
		//Cite reference enabled
		boolean citeReferenceButtonDisabled = editorToolbarPage().isElementDisabled(toolbarCiteReferenceMarkupButton);
		
		//Confirm Link Markup disabled
		boolean confirmLinkMarkupButtonDisabled = editorToolbarPage().isElementDisabled(toolbarConfirmLinkMarkupButton);
		
		editorPage().closeDocumentWithNoChanges();
		
		Assertions.assertAll
		(
			() -> Assertions.assertFalse(closeDocButtonDisabled, "Cose Doc button was disabled"),
			() -> Assertions.assertFalse(saveButtonDisabled, "Save button was disabled"),
			() -> Assertions.assertFalse(validateButtonDisabled, "Validate button was disabled"),
			() -> Assertions.assertFalse(checkSpellingButtonDisabled, "Check Spelling button was disabled"),
			() -> Assertions.assertTrue(rebuildButtonDisabled, "Rebuild button was disabled"),
			() -> Assertions.assertFalse(removeAddDeleteButtonDisabled, "Remove Add/Delete button was disabled"),
			() -> Assertions.assertFalse(pubtagRefreshButtonDisabled, "Pubtag Refresh button was disabled"),
			() -> Assertions.assertFalse(subsectionGenerationButtonDisabled, "Subsection Generation button was disabled"),
			() -> Assertions.assertFalse(subsectionValidationButtonDisabled, "Subsection Validation button was disabled"),
			() -> Assertions.assertFalse(creditGenerationButtonDisabled, "Credit Generation button was disabled"),
			() -> Assertions.assertFalse(footnoteReorderButtonDisabled, "Footnote Reorder button was disabled"),
			() -> Assertions.assertTrue(insertStatPagesButtonDisabled, "Insert Stat Pages button was disabled"),
			() -> Assertions.assertFalse(advancedSarButtonDisabled, "Advanced SAR button was disabled"),
			() -> Assertions.assertFalse(cutButtonDisabled, "Cut button was disabled"),
			() -> Assertions.assertFalse(copyButtonDisabled, "Copy button was disabled"),
			() -> Assertions.assertFalse(pasteButtonDisabled, "Paste button was disabled"),
			() -> Assertions.assertTrue(undoButtonDisabled, "Undo button was disabled"),
			() -> Assertions.assertTrue(redoButtonDisabled, "Redo button was disabled"),
			() -> Assertions.assertFalse(stocknoteManagerButtonDisabled, "Stocknote Manager button was disabled"),
			() -> Assertions.assertFalse(configureEditorSessionPreferencesButtonDisabled, "Configure Editor Session Preferences button was disabled"),
			() -> Assertions.assertFalse(insertTableButtonDisabled, "Insert Table button was disabled"),
			() -> Assertions.assertFalse(insertSpecialCharacterButtonDisabled, "Insert Special Character button was disabled"),
			() -> Assertions.assertFalse(insertMarkupButtonDisabled, "Insert Markup button was disabled"),
			() -> Assertions.assertFalse(promoteButtonDisabled, "Promote button was disabled"),
			() -> Assertions.assertFalse(demoteButtonDisabled, "Demote button was disabled"),
			() -> Assertions.assertFalse(printButtonDisabled, "Print button was disabled"),
			() -> Assertions.assertFalse(helpButtonDisabled, "Help button was disabled"),
			() -> Assertions.assertFalse(addButtonDisabled, "Add button was disabled"),
			() -> Assertions.assertFalse(deleteButtonDisabled, "Delete button was disabled"),
			() -> Assertions.assertFalse(boldButtonDisabled, "Bold button was disabled"),
			() -> Assertions.assertFalse(italicButtonDisabled, "Italic button was disabled"),
			() -> Assertions.assertFalse(capConditionalButtonDisabled, "Cap conditional button was disabled"),
			() -> Assertions.assertFalse(smallCapsButtonDisabled, "Small caps button was disabled"),
			() -> Assertions.assertFalse(paragraphIdIncludeButtonDisabled, "Paragraph id include button was disabled"),
			() -> Assertions.assertFalse(paragraphIdIgnoreButtonDisabled, "Paragraph id ignore button was disabled"),
			() -> Assertions.assertFalse(wordsPhrasesButtonDisabled, "Words & Phrases button was disabled"),
			() -> Assertions.assertFalse(characterFillButtonDisabled, "Character fill button was disabled"),
			() -> Assertions.assertFalse(characterGenerateButtonDisabled, "Character generate button was disabled"),
			() -> Assertions.assertFalse(printSuppressButtonDisabled, "Print suppress button was disabled"),
			() -> Assertions.assertFalse(westlawSuppressButtonDisabled, "Westlaw suppress button was disabled"),
			() -> Assertions.assertFalse(vendorIncludeButtonDisabled, "Vendor include button was disabled"),
			() -> Assertions.assertFalse(caseHistoryIdButtonDisabled, "Case history id button was disabled"),
			() -> Assertions.assertFalse(endLeftButtonDisabled, "End left button was disabled"),
			() -> Assertions.assertFalse(endCenterButtonDisabled, "End center button was disabled"),
			() -> Assertions.assertFalse(endRightButtonDisabled, "End right button was disabled"),
			() -> Assertions.assertFalse(endJustifyButtonDisabled, "End justify button was disabled"),
			() -> Assertions.assertFalse(westlawTableCodeButtonDisabled, "Westlaw Table Code button was disabled"),
			() -> Assertions.assertFalse(citeReferenceButtonDisabled, "Cite reference button was disabled"),
			() -> Assertions.assertFalse(confirmLinkMarkupButtonDisabled, "Confirm Link Markup button was disabled")
		);
	}

	@AfterEach
	public void cleanUp()
	{
		if(datapodObject != null)
		{
			datapodObject.delete();
		}
	}
}

