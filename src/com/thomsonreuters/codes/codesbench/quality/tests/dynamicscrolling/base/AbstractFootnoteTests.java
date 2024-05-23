package com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.base;

import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.CLASS_HIGHLIGHTED_POSTFIX;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.FOLLOWING_SIBLING;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.FOOTNOTE_REFERENCE;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.TEXT_PARAGRAPH_SPAN;
import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;

public abstract class AbstractFootnoteTests extends TestService
{
    protected static final String FP_REFERENCE_TYPE = "FP";
    protected static final String FN_REFERENCE_TYPE = "FN";
    protected static final String FOOTNOTE_REFERENCE_VALUE_1 = String.valueOf(1);
    protected static final String FOOTNOTE_REFERENCE_VALUE_2 = String.valueOf(2);
    protected static final String FOOTNOTE_REFERENCE_VALUE_3 = String.valueOf(3);
    protected static final String FOLLOWING_SIBLING_PARATEXT = format(FOLLOWING_SIBLING, "paratext");
    protected static final String MNEMONIC_ASSERTION_MESSAGE = "There should be only one [%s] mnemonic";
    protected static final String EXPECTED_ERROR_MESSAGE =
            format("Footnote for reference[%s] already exists", FOOTNOTE_REFERENCE_VALUE_1);
    protected static final int ZERO_CHUNK = 0;
    protected static final int FIRST_CHUNK = 1;

    @BeforeEach
    public void login()
    {
        homePage().goToHomePage();
        loginPage().logIn();
    }

    @AfterEach
    public void closeDocument()
    {
        editorPage().closeDocumentAndDiscardChanges();
    }

    /*
     * Insert multiple footnotes and change mnemonic
     * 1. open document
     * 2. scroll to chunk 2 or 3
     * 3. Insert and highlight text
     * 4. Insert a footnote (alt + f)
     * 5. Set the footnote reference type to FP
     * 6. Set the footnote type to footnote
     * 7. Set the footnote reference to 1
     * 8. Click Save
     * 9. VERIFY: We are brought to the selected, inserted footnote and the footnote appears under the Credit Line
     * 10. VERIFY: The 'footnote: end text' markup contains the reference set
     * 11. VERIFY: The footnote contains a hint for the user to 'Insert footnote text here'
     * 11.1. Set the footnote text
     * 12. Scroll back to the place you inserted the footnote
     * 13. VERIFY: 'footnote: end text' markup exists where you inserted the footnote and contains the reference set
     * 14. Follow steps 3 - 13 and use a different reference
     * 15. Change the first footnote's mnemonic by right clicking 'FP' and selecting and submitting a different footnote in the dropdown
     * 16. VERIFY: The mnemonic of the second footnote didn't change
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void insertMultipleFootnotesAndChangeMnemonicLegalTest()
    {
        // insert first footnote
        insertFootnoteWithReference(FOOTNOTE_REFERENCE_VALUE_1, true, true);
        // insert second footnote
        insertFootnoteWithReference(FOOTNOTE_REFERENCE_VALUE_2, true, true);

        int fpCountBefore = editorTextPage().getNumberOfFootnoteMnemonics(FP_REFERENCE_TYPE);
        int fnCountBefore = editorTextPage().getNumberOfFootnoteMnemonics(FN_REFERENCE_TYPE);

        // change mnemonic
        editorTextPage().waitForElementExists(EditorTextPageElements.mnemonics.FP.xpath());
        editorTextPage().rightClickMnemonic(EditorTextPageElements.mnemonics.FP.xpath());
        contentEditorialSystemPage().setMnemonic(FN_REFERENCE_TYPE.toLowerCase());
        contentEditorialSystemPage().clickSave();
        editorPage().switchDirectlyToTextFrame();

        // assert that mnemonic is changed
        assertThat(fpCountBefore - editorTextPage().getNumberOfFootnoteMnemonics(FP_REFERENCE_TYPE))
                .as(String.format(MNEMONIC_ASSERTION_MESSAGE, FP_REFERENCE_TYPE))
                .isEqualTo(1);
        assertThat(editorTextPage().getNumberOfFootnoteMnemonics(FN_REFERENCE_TYPE) - fnCountBefore)
                .as(String.format(MNEMONIC_ASSERTION_MESSAGE, FN_REFERENCE_TYPE))
                .isEqualTo(1);
    }

    /*
     * Insert footnote hotkeys and context menu
     * 1. open document
     * 2. scroll to chunk 2 or 3
     * 3. Insert and highlight text
     * 4. Insert a footnote (alt + f)
     * 5. Set the footnote reference type to FP
     * 6. Set the footnote type to footnote
     * 7. Set the footnote reference to 1
     * 8. Click Save
     * 9. VERIFY: We are brought to the selected, inserted footnote and the footnote appears under the Credit Line
     * 10. VERIFY: The 'footnote: end text' markup contains the reference set
     * 11. VERIFY: The footnote contains a hint for the user to 'Insert footnote text here'
     * 11.1. Set the footnote text
     * 12. Scroll back to the place you inserted the footnote
     * 13. VERIFY: 'footnote: end text' markup exists where you inserted the footnote and contains the reference set
     * 13.1. VERIFY: No changes to the footnote text were made
     * 14. Follow steps 3 - 13, but use the context menu option Insert -> Footnote, and use a different reference
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void insertFootnotesWithHotkeyAndContextMenuLegalTest()
    {
        // insert first footnote with context menu
        insertFootnoteWithReference(FOOTNOTE_REFERENCE_VALUE_1, true, true);
        // assert that first footnote is inserted successfully
        assertThatFootnoteInsertedAmountOfTimes(FOOTNOTE_REFERENCE_VALUE_1, 1);

        // insert second footnote with hotkey
        insertFootnoteWithReference(FOOTNOTE_REFERENCE_VALUE_2, true, true);
        // assert that second footnote is inserted successfully
        assertThatFootnoteInsertedAmountOfTimes(FOOTNOTE_REFERENCE_VALUE_2, 1);
    }

    /*
     * http://ent.jira.int.thomsonreuters.com/browse/HALCYONST-2240
     *
     * setup (follow this when a step in the AC instructs you)
     * Place cursor in a text paragraph
     * Hit Alt + f to insert footnote
     * Set
     * 		Footnote Reference Type: FP
     * 		Footnote Type: footnote
     * 		Footnote Reference: 1
     * 		Check Create a new Footnote Reference
     * 		Check Create a new Footnote
     * 		Click Save
     * Expected: A new Footnote is inserted after the Credit.
     * Footnote markup is inserted where your cursor was placed.
     */

    /*
     * 1
     * Follow setup
     * Place your cursor in another spot in the text paragraph
     * Hit Alt + f to insert footnote
     * Set
     * 		Footnote Reference Type: FP
     * 		Footnote Type: footnote
     * 		Footnote Reference: 1
     * 		Check Create a new Footnote Reference
     * 		Check Create a new Footnote
     * 		Click Save
     * Expected (results pulled from normal editor):
     * 		A new Footnote is not inserted after the Credit.
     * 		Footnote markup is not inserted where your cursor was placed.
     * 		The editor message pane shows error: �Footnote for reference[1] already exists�
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void createNewFootnoteWithNewReferenceCausesErrorLegalTest()
    {
        // insert first footnote
        insertFootnoteWithReference(FOOTNOTE_REFERENCE_VALUE_1, true, true);
        // assert that first footnote is inserted successfully
        assertThatFootnoteInsertedAmountOfTimes(FOOTNOTE_REFERENCE_VALUE_1, 1);

        // insert second footnote
        insertFootnoteWithReference(FOOTNOTE_REFERENCE_VALUE_1, true, true);
        // assert that second footnote is not inserted
        assertThatFootnoteInsertedAmountOfTimes(FOOTNOTE_REFERENCE_VALUE_1, 1);
        assertThatExpectedErrorIsAppearedInTheEditorMessagePane();
    }

    /*
     * 2 Insert multiple footnotes with the same reference
     * 1. open document
     * 2. scroll to chunk 2 or 3
     * 3. Insert and highlight text
     * 4. Insert a footnote (alt + f)
     * 5. Set the footnote reference type to FP
     * 6. Set the footnote type to footnote
     * 7. Set the footnote reference to 1
     * 8. Click Save
     * 9. VERIFY: We are brought to the selected, inserted footnote and the footnote appears under the Credit Line
     * 10. VERIFY: The 'footnote: end text' markup contains the reference set
     * 11. VERIFY: The footnote contains a hint for the user to 'Insert footnote text here'
     * 12. Scroll back to the place you inserted the footnote
     * 13. VERIFY: 'footnote: end text' markup exists where you inserted the footnote and contains the reference set
     * 14. Follow steps 3 - 13 and use the same reference
     * 15. VERIFY: only one footnote is inserted in the document, but we have 2 markups where we inserted the footnotes
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void createNoNewFootnoteWithNewReferenceNoErrorLegalTest()
    {
        // insert first footnote
        insertFootnoteWithReference(FOOTNOTE_REFERENCE_VALUE_1, true, true);
        // assert that first footnote is inserted successfully
        assertThatFootnoteInsertedAmountOfTimes(FOOTNOTE_REFERENCE_VALUE_1, 1);

        // insert second footnote
        insertFootnoteWithReference(FOOTNOTE_REFERENCE_VALUE_1, false, true);
        // assert that second footnote is not inserted to the footnote bottom of text block, but inserted in text
        assertThatFootnoteInsertedAmountOfTimes(FOOTNOTE_REFERENCE_VALUE_1, 2);
    }

    /*
     * 3
     * Follow setup
     * Place your cursor in another spot in the text paragraph
     * Hit Alt + f to insert footnote
     * Set
     * 		Footnote Reference Type: FP
     * 		Footnote Type: footnote
     * 		Footnote Reference: 1
     * 		Uncheck Create a new Footnote Reference
     * 		Check Create a new Footnote
     * 		Click Save
     * Expected (results pulled from normal editor):
     * 		A new Footnote is not inserted after the Credit.
     * 		Footnote markup is not inserted where your cursor was placed.
     * 		The editor message pane shows error: �Footnote for reference[1] already exists�
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void createNewFootnoteWithoutNewReferenceCausesErrorLegalTest()
    {
        // insert first footnote
        insertFootnoteWithReference(FOOTNOTE_REFERENCE_VALUE_1, true, true);
        // assert that first footnote is inserted successfully
        assertThatFootnoteInsertedAmountOfTimes(FOOTNOTE_REFERENCE_VALUE_1, 1);

        // insert second footnote
        insertFootnoteWithReference(FOOTNOTE_REFERENCE_VALUE_1, true, false);
        // assert that second footnote is not inserted
        assertThatFootnoteInsertedAmountOfTimes(FOOTNOTE_REFERENCE_VALUE_1, 1);
        assertThatExpectedErrorIsAppearedInTheEditorMessagePane();
    }

    /*
     * 4
     * Follow setup
     * Place your cursor in another spot in the text paragraph
     * Hit Alt + f to insert footnote
     * Set
     * 		Footnote Reference Type: FP
     * 		Footnote Type: footnote
     * 		Footnote Reference: 1
     * 		Uncheck Create a new Footnote Reference
     * 		Uncheck Create a new Footnote
     * 		Click Save
     * Expected (results pulled from normal editor):
     *		A new Footnote is not inserted after the Credit.
     * 		Footnote markup is not inserted where your cursor was placed.
     * 		The editor message pane shows error: �Footnote for reference[1] already exists�
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void createNoNewFootnoteWithoutNewReferenceCausesErrorLegalTest()
    {
        // insert first footnote
        insertFootnoteWithReference(FOOTNOTE_REFERENCE_VALUE_1, true, true);
        // assert that first footnote is inserted successfully
        assertThatFootnoteInsertedAmountOfTimes(FOOTNOTE_REFERENCE_VALUE_1, 1);

        // insert second footnote
        insertFootnoteWithReference(FOOTNOTE_REFERENCE_VALUE_1, false, false);
        // assert that second footnote is not inserted
        assertThatFootnoteInsertedAmountOfTimes(FOOTNOTE_REFERENCE_VALUE_1, 1);
        assertThatExpectedErrorIsAppearedInTheEditorMessagePane();
    }

    /**
     * STORY/BUG - HALCYONST-13019 <br>
     * SUMMARY - Find Footnotes/References warning <br>
     * USER - LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void findFootnotesReferencesWarningLegalTest()
    {
        //Insert footnote in the first text paragraph
        insertFootnoteWithReference(FOOTNOTE_REFERENCE_VALUE_1, true, true);
        //Assert that footnote is inserted successfully
        assertThatFootnoteInsertedAmountOfTimes(FOOTNOTE_REFERENCE_VALUE_1, 1);

        editorTextPage().click(TEXT_PARAGRAPH_SPAN + FOLLOWING_SIBLING_PARATEXT);

        //Assert that Footnote is NOT highlighted before finding
        long scrollPositionBeforeFinding = editorTextPage().getScrollPosition();
        assertThat(editorTextPage().doesElementExist("//footnote" + CLASS_HIGHLIGHTED_POSTFIX))
                .as("The Footnote should NOT be highlighted before finding")
                .isFalse();

        editorTextPage().rightClick(
                TEXT_PARAGRAPH_SPAN + FOLLOWING_SIBLING_PARATEXT + FOOTNOTE_REFERENCE + "/super/img");
        editorTextPage().breakOutOfFrame();
        editorTextContextMenu().findFootnotesReferences();
        editorPage().switchDirectlyToTextFrame();

        //Assert that Footnote is highlighted after finding
        assertThat(editorTextPage().doesElementExist("//footnote" + CLASS_HIGHLIGHTED_POSTFIX))
                .as("The Footnote should be highlighted after finding")
                .isTrue();
        //Assert that scroll position is changed after finding
        assertThat(editorTextPage().getScrollPosition())
                .as("Scroll position after finding should be changed")
                .isNotEqualTo(scrollPositionBeforeFinding);
    }

//  ------------- Assistive methods: -------------

    protected void insertFootnoteWithReference(String reference,
                                               boolean createNewFootnote, boolean createNewFootnoteReference)
    {
        String phrase = format("Autotest%s", System.currentTimeMillis());
        editorTextPage().insertPhraseUnderParaInChunk(
                phrase, 1, FIRST_CHUNK);
        editorTextPage().highlightHelper(phrase);
        editorPage().openInsertFootnoteDialog();

        insertFootnotePage().setFootnoteReferenceType("footnote.reference");
        insertFootnotePage().setFootnoteReference(reference);
        if (!createNewFootnote)
        {
            insertFootnotePage().uncheckNewFootnoteCheckbox();
        }
        if (!createNewFootnoteReference)
        {
            insertFootnotePage().uncheckNewFootnoteReferenceCheckbox();
        }
        insertFootnotePage().clickSaveFootnote();
        editorPage().switchDirectlyToTextFrame();
    }

    protected void assertThatFootnoteInsertedAmountOfTimes(String footnoteReferenceValue, int times)
    {
        assertThat(editorTextPage().getNumberOfFootnotesWithReference(footnoteReferenceValue))
                .as(format("Footnote with reference value %s should be present in footnote bottom of text block 1 time",
                        footnoteReferenceValue))
                .isEqualTo(1);
        editorPage().switchToEditorAndScrollToChunk(FIRST_CHUNK);
        assertThat(editorTextPage().getNumberOfFootnoteReferences(footnoteReferenceValue))
                .as(format("Footnote reference with value %s should be present in text %d time(s)",
                        footnoteReferenceValue, times))
                .isEqualTo(times);
    }

    protected void assertThatExpectedErrorIsAppearedInTheEditorMessagePane()
    {
        editorPage().switchToEditor();
        assertThat(editorMessagePage().checkMessage(EXPECTED_ERROR_MESSAGE))
                .as("The [%s] error message should be appeared in the editor message pane", EXPECTED_ERROR_MESSAGE)
                .isTrue();
    }
}
