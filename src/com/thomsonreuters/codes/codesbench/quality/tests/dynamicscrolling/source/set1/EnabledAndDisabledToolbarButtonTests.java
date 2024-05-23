package com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.source.set1;

import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorToolbarPageElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import static com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;

public class EnabledAndDisabledToolbarButtonTests extends TestService
{
    /*
     * verify that the following toolbar buttons are disabled upon editor content load:
     * remove add/deleted, pubtag refresh, subsection generation, subsection validation,
     * credit generation, footnote reorder, insert stat pages, advanced sar, undo, redo,
     * confirm link markup
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void enabledAndDisabledToolbarButtonSourceLegalTest()
    {
        String uuid = "I9E7B6241F51611E5AF0B8B9371769F64";

        // open DS editor
        sourcePage().goToSourcePageWithRenditionUuids(uuid);
        loginPage().logIn();
        sourceNavigateGridPage().firstRenditionEditContent();

        //Close Doc enabled
        boolean closeDocButtonDisabled = editorToolbarPage().isElementDisabled(EditorToolbarPageElements.toolbarCloseDocButton);

        //Save enabled
        boolean saveButtonDisabled = editorToolbarPage().isElementDisabled(EditorToolbarPageElements.toolbarSaveButton);

        //Validate enabled
        boolean validateButtonDisabled = editorToolbarPage().isElementDisabled(EditorToolbarPageElements.toolbarValidateButton);

        //Check Spelling enabled
        boolean checkSpellingButtonDisabled = editorToolbarPage().isElementDisabled(EditorToolbarPageElements.toolbarCheckSpellingButton);

        //Rebuild enabled
        boolean rebuildButtonDisabled = editorToolbarPage().isElementDisabled(EditorToolbarPageElements.toolbarRebuildButton);

        //Remove Add/Delete disabled
        boolean removeAddDeleteButtonDisabled = editorToolbarPage().isElementDisabled(EditorToolbarPageElements.toolbarRemoveAddDeleteButton);

        //Pubtag Refresh disabled
        boolean pubtagRefreshButtonDisabled = editorToolbarPage().isElementDisabled(EditorToolbarPageElements.toolbarPubtagRefreshButton);

        //Subsection Generation disabled
        boolean subsectionGenerationButtonDisabled = editorToolbarPage().isElementDisabled(EditorToolbarPageElements.toolbarSubsectionGenerationButton);

        //Subsection Validation disabled
        boolean subsectionValidationButtonDisabled = editorToolbarPage().isElementDisabled(EditorToolbarPageElements.toolbarSubsectionValidationButton);

        //Credit Generation disabled
        boolean creditGenerationButtonDisabled = editorToolbarPage().isElementDisabled(EditorToolbarPageElements.toolbarCreditGenerationButton);

        //Footnote Reorder disabled
        boolean footnoteReorderButtonDisabled = editorToolbarPage().isElementDisabled(EditorToolbarPageElements.toolbarFootnoteReorderButton);

        //Insert Stat Pages disabled
        boolean insertStatPagesButtonDisabled = editorToolbarPage().isElementDisabled(EditorToolbarPageElements.toolbarInsertStatPagesButton);

        //Advanced SAR disabled
        boolean advancedSarButtonDisabled = editorToolbarPage().isElementDisabled(EditorToolbarPageElements.toolbarAdvancedSearchAndReplaceButton);

        //Cut enabled
        boolean cutButtonDisabled = editorToolbarPage().isElementDisabled(EditorToolbarPageElements.toolbarCutButton);

        //Copy enabled
        boolean copyButtonDisabled = editorToolbarPage().isElementDisabled(EditorToolbarPageElements.toolbarCopyButton);

        //Paste enabled
        boolean pasteButtonDisabled = editorToolbarPage().isElementDisabled(EditorToolbarPageElements.toolbarPasteButton);

        //Undo disabled
        boolean undoButtonDisabled = editorToolbarPage().isElementDisabled(EditorToolbarPageElements.toolbarUndoButton);

        //Redo disabled
        boolean redoButtonDisabled = editorToolbarPage().isElementDisabled(EditorToolbarPageElements.toolbarRedoButton);

        //Stocknote Manager enabled
        boolean stocknoteManagerButtonDisabled = editorToolbarPage().isElementDisabled(EditorToolbarPageElements.toolbarStocknoteManagerButton);

        //Configure Editor Session Preferences enabled
        boolean configureEditorSessionPreferencesButtonDisabled = editorToolbarPage().isElementDisabled(EditorToolbarPageElements.toolbarConfigureEditorSessionPreferencesButton);

        //Insert Table enabled
        boolean insertTableButtonDisabled = editorToolbarPage().isElementDisabled(EditorToolbarPageElements.toolbarInsertTableButton);

        //Insert Special Character enabled
        boolean insertSpecialCharacterButtonDisabled = editorToolbarPage().isElementDisabled(EditorToolbarPageElements.toolbarInsertSpecialCharacterButton);

        //Insert Markup enabled
        boolean insertMarkupButtonDisabled = editorToolbarPage().isElementDisabled(EditorToolbarPageElements.toolbarInsertMarkupButton);

        //Promote enabled
        boolean promoteButtonDisabled = editorToolbarPage().isElementDisabled(EditorToolbarPageElements.toolbarPromoteButton);

        //Demote enabled
        boolean demoteButtonDisabled = editorToolbarPage().isElementDisabled(EditorToolbarPageElements.toolbarDemoteButton);

        //Print enabled
        boolean printButtonDisabled = editorToolbarPage().isElementDisabled(EditorToolbarPageElements.toolbarPrintButton);

        //Help enabled
        boolean helpButtonDisabled = editorToolbarPage().isElementDisabled(EditorToolbarPageElements.toolbarHelpButton);

        //Add enabled
        boolean addButtonDisabled = editorToolbarPage().isElementDisabled(EditorToolbarPageElements.toolbarAddMarkupButton);

        //Delete enabled
        boolean deleteButtonDisabled = editorToolbarPage().isElementDisabled(EditorToolbarPageElements.toolbarDeleteMarkupButton);

        //Bold enabled
        boolean boldButtonDisabled = editorToolbarPage().isElementDisabled(EditorToolbarPageElements.toolbarBoldMarkupButton);

        //Italic enabled
        boolean italicButtonDisabled = editorToolbarPage().isElementDisabled(EditorToolbarPageElements.toolbarItalicMarkupButton);

        //Cap conditional enabled
        boolean capConditionalButtonDisabled = editorToolbarPage().isElementDisabled(EditorToolbarPageElements.toolbarCapConditionalMarkupButton);

        //Small caps enabled
        boolean smallCapsButtonDisabled = editorToolbarPage().isElementDisabled(EditorToolbarPageElements.toolbarSmallCapsMarkupButton);

        //Paragraph id include enabled
        boolean paragraphIdIncludeButtonDisabled = editorToolbarPage().isElementDisabled(EditorToolbarPageElements.toolbarParagraphIdIncludedMarkupButton);

        //Paragraph id ignore enabled
        boolean paragraphIdIgnoreButtonDisabled = editorToolbarPage().isElementDisabled(EditorToolbarPageElements.toolbarParagraphIdIgnoreMarkupButton);

        //Words & Phrases enabled
        boolean wordsPhrasesButtonDisabled = editorToolbarPage().isElementDisabled(EditorToolbarPageElements.toolbarWordsAndPhrasesMarkupButton);

        //Character fill enabled
        boolean characterFillButtonDisabled = editorToolbarPage().isElementDisabled(EditorToolbarPageElements.toolbarCharacterFillMarkupButton);

        //Character generate enabled
        boolean characterGenerateButtonDisabled = editorToolbarPage().isElementDisabled(EditorToolbarPageElements.toolbarCharacterGenerateMarkupButton);

        //Print suppress enabled
        boolean printSuppressButtonDisabled = editorToolbarPage().isElementDisabled(EditorToolbarPageElements.toolbarPrintSuppressMarkupButton);

        //Westlaw suppress enabled
        boolean westlawSuppressButtonDisabled = editorToolbarPage().isElementDisabled(EditorToolbarPageElements.toolbarWestlawSupressMarkupButton);

        //Vendor include enabled
        boolean vendorIncludeButtonDisabled = editorToolbarPage().isElementDisabled(EditorToolbarPageElements.toolbarVendorIncludeMarkupButton);

        //Case history id enabled
        boolean caseHistoryIdButtonDisabled = editorToolbarPage().isElementDisabled(EditorToolbarPageElements.toolbarCaseHistoryIdMarkupButton);

        //End left enabled
        boolean endLeftButtonDisabled = editorToolbarPage().isElementDisabled(EditorToolbarPageElements.toolbarEndLeftMarkupButton);

        //End center enabled
        boolean endCenterButtonDisabled = editorToolbarPage().isElementDisabled(EditorToolbarPageElements.toolbarEndCenterMarkupButton);

        //End right enabled
        boolean endRightButtonDisabled = editorToolbarPage().isElementDisabled(EditorToolbarPageElements.toolbarEndRightMarkupButton);

        //End justify enabled
        boolean endJustifyButtonDisabled = editorToolbarPage().isElementDisabled(EditorToolbarPageElements.toolbarEndJustifyMarkupButton);

        //Westlaw Table Code enabled
        boolean westlawTableCodeButtonDisabled = editorToolbarPage().isElementDisabled(EditorToolbarPageElements.toolbarWestlawTableCodeMarkupButton);

        //Cite reference enabled
        boolean citeReferenceButtonDisabled = editorToolbarPage().isElementDisabled(EditorToolbarPageElements.toolbarCiteReferenceMarkupButton);

        //Confirm Link Markup disabled
        boolean confirmLinkMarkupButtonDisabled = editorToolbarPage().isElementDisabled(EditorToolbarPageElements.toolbarConfirmLinkMarkupButton);

        editorPage().switchToEditor();
        editorPage().closeDocumentWithNoChanges();

        Assertions.assertAll
                (
                        () -> Assertions.assertFalse(closeDocButtonDisabled, "Cose Doc button was disabled"),
                        () -> Assertions.assertFalse(saveButtonDisabled, "Save button was disabled"),
                        () -> Assertions.assertFalse(validateButtonDisabled, "Validate button was disabled"),
                        () -> Assertions.assertFalse(checkSpellingButtonDisabled, "Check Spelling button was disabled"),
                        () -> Assertions.assertFalse(rebuildButtonDisabled, "Rebuild button was disabled"),
                        () -> Assertions.assertTrue(removeAddDeleteButtonDisabled, "Remove Add/Delete button was disabled"),
                        () -> Assertions.assertTrue(pubtagRefreshButtonDisabled, "Pubtag Refresh button was disabled"),
                        () -> Assertions.assertTrue(subsectionGenerationButtonDisabled, "Subsection Generation button was disabled"),
                        () -> Assertions.assertTrue(subsectionValidationButtonDisabled, "Subsection Validation button was disabled"),
                        () -> Assertions.assertTrue(creditGenerationButtonDisabled, "Credit Generation button was disabled"),
                        () -> Assertions.assertTrue(footnoteReorderButtonDisabled, "Footnote Reorder button was disabled"),
                        () -> Assertions.assertTrue(insertStatPagesButtonDisabled, "Insert Stat Pages button was disabled"),
                        () -> Assertions.assertTrue(advancedSarButtonDisabled, "Advanced SAR button was disabled"),
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
                        () -> Assertions.assertTrue(confirmLinkMarkupButtonDisabled, "Confirm Link Markup button was disabled")
                );
    }
}
