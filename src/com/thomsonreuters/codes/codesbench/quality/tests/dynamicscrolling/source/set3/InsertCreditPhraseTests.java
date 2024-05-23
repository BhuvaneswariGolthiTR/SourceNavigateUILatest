package com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.source.set3;

import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorMessagePageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTreePageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.popups.CreditPhraseEditorPageElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.*;
import static com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.*;

public class InsertCreditPhraseTests extends TestService
{
    /* Insert Credit Phrase Override
     * DEV
     * 1. Search for PREP doc (iowa prep 617)
     * 2. Select Source section wrapper
     * 3. Right click and go Credit Phrase
     * 4. Popup came up Credit Phrase editor
     * 5. Check first checkbox and click Save
     * 6. Credit phrase Override wrapper inserted right under the Subsection
     * 7. That CPO should contain bunch of children
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void insertCreditPhraseTest()
    {
        String uuid = "I2B6D1EE043EA11E8B306BDCAB99DF270";
        int chunkNumber = 1;

        String beginningPhrase = "testPhrase";
        String effDatePhrase = "Eff";
        String firstEffDate = "06/06/2026";
        String sectionNumber = "3";
        String none = "none";
        String creditPhraseOverride = "Credit Phrase Override";

        // open DS editor
        sourcePage().goToSourcePageWithRenditionUuids(uuid);
        loginPage().logIn();
        sourceNavigateGridPage().firstRenditionEditContent();

        // scroll to chunk
        editorPage().scrollToChunk(chunkNumber);

        // check there is no Credit Phrase Override element initially
        boolean creditPhraseExistingInitially = editorTextPage()
                .getElements(EditorTextPageElements.SOURCE_SECTION + EditorTextPageElements.CREDIT_PHRASE_OVERRIDE)
                .isEmpty();
        Assertions.assertTrue(creditPhraseExistingInitially,
                "Credit Phrase should not exist initially - remove the Credit Phrase and rerun the test");

        // add credit phrase
        editorTextPage().click(EditorTextPageElements.SOURCE_SECTION_SPAN);
        editorTextPage().rightClick(EditorTextPageElements.SOURCE_SECTION_SPAN);
        editorTextPage().breakOutOfFrame();
        editorTextContextMenu().creditPhrase();
        creditPhraseEditorPage().switchToWindow(CreditPhraseEditorPageElements.PAGE_TITLE);
        creditPhraseEditorPage().enterTheInnerFrame();
        creditPhraseEditorPage().inputPhraseInBeginningOfCredit(beginningPhrase);
        creditPhraseEditorPage().inputPhraseInSection(sectionNumber);
        boolean firstEffDatePhraseTextBox = creditPhraseEditorPage().selectPhraseFromEffectiveDatePhraseDropdown(effDatePhrase);
        creditPhraseEditorPage().inputEffectiveDate(firstEffDate);
        creditPhraseEditorPage().clickSave();
        editorPage().waitForWindowGoneByTitle(CreditPhraseEditorPageElements.PAGE_TITLE);
        editorPage().switchToEditor();

        // check inserted credit phrase in text area
        editorTextPage().switchToEditorTextArea();
        boolean creditPhraseInserted = editorTextPage()
                .getElements(EditorTextPageElements.SOURCE_SECTION + EditorTextPageElements.CREDIT_PHRASE_OVERRIDE)
                .size() == 1;

        String creditPhraseIsUnderSourceSection = editorTextPage()
                .getElement(EditorTextPageElements.FIRST_ELEMENT_UNDER_SOURCE_SECTION_LABEL)
                .getAttribute("display-name");

        // check Credit Phrase Override contents
        editorTextPage().click(EditorTextPageElements.CREDIT_PHRASE_OVERRIDE_SPAN);

        String beginningPhraseIsCorrect = editorTextPage()
                .getElementsText(EditorTextPageElements.CREDIT_PHRASE_BEGINNING_PHRASE);

        String firstEffDatePhraseIsCorrect = editorTextPage()
                .getElementsText(EditorTextPageElements.CREDIT_PHRASE_FIRST_EFF_DATE_PHRASE);

        String firstEffDateIsCorrect = editorTextPage()
                .getElementsText(EditorTextPageElements.CREDIT_PHRASE_FIRST_EFF_DATE);

        String secondEffDatePhraseIsCorrect = editorTextPage()
                .getElementsText(EditorTextPageElements.CREDIT_PHRASE_SECOND_EFF_DATE_PHRASE);

        String secondEffDateIsCorrect = editorTextPage()
                .getElementsText(EditorTextPageElements.CREDIT_PHRASE_SECOND_EFF_DATE);

        String sectionsNumberIsCorrect = editorTextPage()
                .getElementsText(EditorTextPageElements.CREDIT_PHRASE_CREDIT_SECTIONS);

        // check tree
        editorTextPage().breakOutOfFrame();

        editorTreePage().waitForElementExists(String
                .format(EditorTreePageElements.SELECTED_NODE_WITH_NAME, "credit.phrase.block"));
        boolean treeNodeAppeared =
                editorTreePage().doesElementExist(String
                        .format(EditorTreePageElements.SELECTED_NODE_WITH_NAME, "credit.phrase.block"))
                        && editorTreePage().doesElementExist(String
                        .format(EditorTreePageElements.SELECTED_NODE_WITH_NAME, "beginning.phrase"))
                        && editorTreePage().doesElementExist(String
                        .format(EditorTreePageElements.SELECTED_NODE_WITH_NAME, "effective.date.phrase"))
                        && editorTreePage().doesElementExist(String
                        .format(EditorTreePageElements.SELECTED_NODE_WITH_NAME, "effective.date"))
                        && editorTreePage().doesElementExist(String
                        .format(EditorTreePageElements.SELECTED_NODE_WITH_NAME, "second.effective.date.phrase"))
                        && editorTreePage().doesElementExist(String
                        .format(EditorTreePageElements.SELECTED_NODE_WITH_NAME, "second.effective.date"))
                        && editorTreePage().doesElementExist(String
                        .format(EditorTreePageElements.SELECTED_NODE_WITH_NAME, "credit.section"));

        // check message pane // HALCYONST-3011
        boolean warnAppeared = editorMessagePage().doesElementExist(EditorMessagePageElements.WARN_SPAN);

        // close editor
        editorPage().closeDocumentAndDiscardChanges();

        Assertions.assertAll
                (
                        () -> Assertions.assertTrue(firstEffDatePhraseTextBox,
                                "First eff date textbox should be populated with selected dropdown option"),
                        () -> Assertions.assertTrue(creditPhraseInserted, "Credit Phrase should be inserted"),
                        () -> Assertions.assertEquals(creditPhraseOverride, creditPhraseIsUnderSourceSection,
                                "Inserted Credit Phrase should appear right under the Source Section label"),
                        () -> Assertions.assertEquals("Beginning Phrase "+beginningPhrase, beginningPhraseIsCorrect, "Beginning phrase should be as expected"),
                        () -> Assertions.assertEquals("First Effective Date Phrase " + effDatePhrase, firstEffDatePhraseIsCorrect, "First Eff Date phrase should be as expected"),
                        () -> Assertions.assertEquals("First Effective Date " + firstEffDate, firstEffDateIsCorrect, "First Eff Date should be as expected"),
                        () -> Assertions.assertEquals("Second Effective Date Phrase " + none, secondEffDatePhraseIsCorrect, "Second Eff Date phrase should be as expected"),
                        () -> Assertions.assertEquals("Second Effective Date " + none, secondEffDateIsCorrect, "Second Eff Date should be as expected"),
                        () -> Assertions.assertEquals("Credit Section(s) " + sectionNumber, sectionsNumberIsCorrect, "Section number should be as expected"),
                        () -> Assertions.assertTrue(treeNodeAppeared, "Credit Phrase block node should appear in the tree"),
                        () -> Assertions.assertFalse(warnAppeared, "No wrn should appear")
                );
    }
}
