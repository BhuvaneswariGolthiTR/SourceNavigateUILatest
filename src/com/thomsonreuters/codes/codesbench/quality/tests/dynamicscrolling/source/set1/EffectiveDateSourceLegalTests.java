package com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.source.set1;

import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTreePageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.popups.SelectDateElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import static com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;

public class EffectiveDateSourceLegalTests extends TestService
{
    /* Insert, Amend, Delete Effective Date
     * NOTE: I believe there is are source package tests for effective date related
     * stuff that also needs to occur on the source grid itself.
     *
     * 1. Open document
     * 2. Scroll down to the source body wrapper (or select is from the tree)
     * 3. Select it if not already selected
     * 4. Right click the source body and select Effective Date
     * VERIFY: Select Date window appears
     * 5. Set the current date
     * 6. Click OK
     * VERIFY: Select Date window no longer appears
     * VERIFY: Effective Date label is inserted as a child to the Body element
     * VERIFY: The Effective date is the date set
     * VERIFY: This element also appears in the tree
     * - these should be selected too
     *
     * 7. Right click the Effective Date element and select Amend
     * VERIFY: Select Date window appears
     * 8. Set a different date
     * 9. Click OK
     * VERIFY: Select Date window no longer appears
     * VERIFY: The Effective date is the new date set
     *
     * 10. Right click the Effective Date element and select Delete
     * VERIFY: The Effective Date element no longer appears
     * VERIFY: This element no longer appears in the tree
     */

    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void effectiveDateSourceLegalTest()
    {
        String uuid = "I9E7B6241F51611E5AF0B8B9371769F64";
        int chunkNumber = 3;
        String currentDate = DateAndTimeUtils.getCurrentDateMMddyyyy();
        String dateToEnter = "01/15/2018";

        // open DS editor
        sourcePage().goToSourcePageWithRenditionUuids(uuid);
        loginPage().logIn();
        sourceNavigateGridPage().firstRenditionEditContent();

        // scroll to chunk
        editorPage().scrollToChunk(chunkNumber);

        //add effective date
        editorTextPage().click(String.format(EditorTextPageElements.SOURCE_SECTION_LABEL_UNDER_NUMBER_GIVEN, 2));
        editorTextPage().rightClick(String.format(EditorTextPageElements.SOURCE_SECTION_LABEL_UNDER_NUMBER_GIVEN, 2));
        editorTextPage().breakOutOfFrame();
        editorTextContextMenu().effectiveDate();
        editorPage().switchToWindow(SelectDateElements.PAGE_TITLE);
        selectDatePage().enterTheInnerFrame();
        selectDatePage().setDate(currentDate);
        selectDatePage().clickOK();
        selectDatePage().waitForWindowGoneByTitle(SelectDateElements.PAGE_TITLE);
        editorPage().switchToEditor();

        //check effective date
        editorTextPage().switchToEditorTextArea();
        boolean effectiveDateContentAppearsInEditor = editorTextPage().doesElementExist(EditorTextPageElements.SOURCE_SECTION_EFFECTIVE_DATE_ENGLISH_LABEL
                + String.format(EditorTextPageElements.EFFECTIVE_DATE_UNDER_ENGLISH_LABEL, currentDate));
        editorTextPage().breakOutOfFrame();
        boolean effectiveDateContentAppearsInTree = editorTreePage().doesElementExist(EditorTreePageElements.EDITOR_TREE_EFFECTIVE_DATE);

        //amend effective dste
        editorTextPage().switchToEditorTextArea();
        editorTextPage().click(EditorTextPageElements.SOURCE_SECTION_EFFECTIVE_DATE_ENGLISH_LABEL);
        editorTextPage().rightClick(EditorTextPageElements.SOURCE_SECTION_EFFECTIVE_DATE_ENGLISH_LABEL);
        editorTextPage().breakOutOfFrame();
        editorTextContextMenuPage().amend();
        editorPage().switchToWindow(SelectDateElements.PAGE_TITLE);
        selectDatePage().enterTheInnerFrame();
        selectDatePage().setDate(dateToEnter);
        selectDatePage().clickOK();
        selectDatePage().waitForWindowGoneByTitle(SelectDateElements.PAGE_TITLE);
        editorPage().switchToEditor();

        //check amend effective date
        editorTextPage().switchToEditorTextArea();
        boolean effectiveDateContentAppearsInEditorAfterAmend = editorTextPage().doesElementExist(EditorTextPageElements.SOURCE_SECTION_EFFECTIVE_DATE_ENGLISH_LABEL
                + String.format(EditorTextPageElements.EFFECTIVE_DATE_UNDER_ENGLISH_LABEL, dateToEnter));
        editorTextPage().breakOutOfFrame();
        boolean effectiveDateContentAppearsInTreeAfterAmend = editorTreePage().doesElementExist(EditorTreePageElements.EDITOR_TREE_EFFECTIVE_DATE);

        //delete effective date
        editorTextPage().switchToEditorTextArea();
        editorTextPage().click(EditorTextPageElements.SOURCE_SECTION_EFFECTIVE_DATE_ENGLISH_LABEL);
        editorTextPage().rightClick(EditorTextPageElements.SOURCE_SECTION_EFFECTIVE_DATE_ENGLISH_LABEL);
        editorTextPage().breakOutOfFrame();
        editorTextContextMenuPage().delete();

        //check delete effective date
        editorTextPage().switchToEditorTextArea();
        boolean effectiveDateContentAppearsInEditorAfterDelete = editorTextPage().doesElementExist(EditorTextPageElements.SOURCE_SECTION_EFFECTIVE_DATE_ENGLISH_LABEL
                + String.format(EditorTextPageElements.EFFECTIVE_DATE_UNDER_ENGLISH_LABEL, dateToEnter));
        editorTextPage().breakOutOfFrame();
        boolean effectiveDateContentAppearsInTreeAfterDelete = editorTreePage().doesElementExist(EditorTreePageElements.EDITOR_TREE_EFFECTIVE_DATE);

        //Close document and Discard Changes
        editorPage().switchToEditor();
        editorPage().closeDocumentAndDiscardChanges();

        Assertions.assertAll
                (
                        () -> Assertions.assertTrue(effectiveDateContentAppearsInEditor, "Effective Date content appeared in the editor content"),
                        () -> Assertions.assertTrue(effectiveDateContentAppearsInTree, "Effective Date content appeared in the editor tree"),
                        () -> Assertions.assertTrue(effectiveDateContentAppearsInEditorAfterAmend, "Effective Date content appeared in the editor content after amend"),
                        () -> Assertions.assertTrue(effectiveDateContentAppearsInTreeAfterAmend, "Effective Date content appeared in the editor tree after amend"),
                        () -> Assertions.assertFalse(effectiveDateContentAppearsInEditorAfterDelete, "Effective Date content appeared in the editor content after delete"),
                        () -> Assertions.assertFalse(effectiveDateContentAppearsInTreeAfterDelete, "Effective Date content appeared in the editor tree after delete")
                );
    }
}
