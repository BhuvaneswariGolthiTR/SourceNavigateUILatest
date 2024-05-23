package com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.source.set1;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.Keys;

import static com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.editor.EditorTextContextMenuElements.INSERT_DELTA_INSTRUCTION;
import static com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.editor.EditorTextContextMenuElements.INSERT_SECTION_INSTRUCTION;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.CORNERPIECE_TEXT;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.DELTA_ADD_NOTE_SPAN;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.DELTA_INSTRUCTION_TEXT;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.LOADED_CHUNK;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.PARA;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.PARATEXT;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.SECTION_INSTRUCTION_TEXT;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.SOURCE_SECTION;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.SOURCE_SECTION_SPAN;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.SUBSECTION_UNDER_NUMBER;

import static org.assertj.core.api.Assertions.assertThat;

public class TextSelectionAndHighlightingSourceTests extends TestService
{
    private static final String RENDITION_UUID_FOR_TEXT_PARAGRAPH = "I9D676EA0A22211E9A152EBC8559F631B";
    private static final String RENDITION_UUID_FOR_DELTA_INSTRUCTION_NOTES = "IE86210A2E87511EA852AC4B447F6A479";
    private static final String RENDITION_UUID_FOR_SOURCE_INSTRUCTION_NOTES = "I6BB114508B1611EA9A129329B53BCCCE";
    private static final String HIGHLIGHTED_BACKGROUND_COLOR = "rgba(49, 106, 197, 1)";
    private static final String PARA_XPATH = LOADED_CHUNK + SUBSECTION_UNDER_NUMBER + PARA;
    private static final String PARA_TEXT = String.format(PARA_XPATH + PARATEXT, 0, 1);
    private static final String ADDED_MATERIAL = String.format(PARA_XPATH + "//added.material", 0, 1);
    private static final String DELETED_MATERIAL = String.format(PARA_XPATH + "//deleted.material", 0, 1);
    private static final String PARA_TEXT_IN_SOURCE_SECTION = SOURCE_SECTION + PARA + PARATEXT;

    @BeforeEach
    public void openDocument()
    {
        homePage().goToHomePage();
        loginPage().logIn();
    }

    @AfterEach
    public void closeDocument()
    {
        editorPage().closeEditorWithDiscardingChangesIfAppeared();
    }

    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void textParagraphSelectionTest()
    {
        //Edit documents with DS editor
        openSourceRenditionInDsEditor(RENDITION_UUID_FOR_TEXT_PARAGRAPH);

        //Locate a text paragraph
        //Place cursor in the record and click around within the record multiple times
        editorTextPage().click(PARA_TEXT);
        editorTextPage().click(ADDED_MATERIAL);
        editorTextPage().click(DELETED_MATERIAL);

        //Verify that the entire record is not highlighted
        assertThat(editorTextPage().getBackgroundColor(PARA_TEXT))
                .as("The entire record should not be highlighted")
                .isNotEqualTo(HIGHLIGHTED_BACKGROUND_COLOR);
    }

    static Object[][] instructionTypesAndOtherLinkedElements()
    {
        return new Object[][]
                {
                        {RENDITION_UUID_FOR_DELTA_INSTRUCTION_NOTES, DELTA_ADD_NOTE_SPAN, INSERT_DELTA_INSTRUCTION,
                                "Delta", DELTA_INSTRUCTION_TEXT, CORNERPIECE_TEXT},
                        {RENDITION_UUID_FOR_SOURCE_INSTRUCTION_NOTES, SOURCE_SECTION_SPAN, INSERT_SECTION_INSTRUCTION,
                                "Section", SECTION_INSTRUCTION_TEXT, PARA_TEXT_IN_SOURCE_SECTION}
                };
    }

    @ParameterizedTest(name = "Test: {displayName}; Index: {index}; Arguments: {arguments}")
    @MethodSource("instructionTypesAndOtherLinkedElements")
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void instructionSelectionTest(
            String renditionUUID, String noteEnglishLabelSpanXpath, String instruction,
            String testPhrase, String instructionTextXpath, String anotherTextFieldXpath)
    {
        //Edit documents with DS editor
        openSourceRenditionInDsEditor(renditionUUID);

        //In the document, locate the given element (Delta/Section)
        //Right click its English label and select "Insert Delta/Section Instruction - Template"
        editorTextPage().rightClick(noteEnglishLabelSpanXpath);
        editorTextPage().breakOutOfFrame();
        editorTextContextMenu().insertGivenInstructionTemplateNote(instruction);

        //Start typing in the text field from here
        editorTextPage().sendKeys(testPhrase);
        editorTextPage().switchToEditorTextArea();

        //Verify that it's possible to type in these text fields at anytime.
        assertThatGivenElementContainsText(instructionTextXpath, testPhrase);

        //Select a text paragraph elsewhere in the document, then come
        //back into this instruction note text and start typing
        editorTextPage().click(anotherTextFieldXpath);
        editorTextPage().click(instructionTextXpath);
        editorTextPage().sendKeys(Keys.END);
        editorTextPage().sendKeys(testPhrase);

        //Verify that it's possible to type in the text field.
        assertThatGivenElementContainsText(instructionTextXpath, testPhrase + testPhrase);
    }

//  ------------- Assertions: -------------
    private void assertThatGivenElementContainsText(String xpath, String text)
    {
        assertThat(editorTextPage().getElementsText(xpath))
                .as("The given text field should contain the text")
                .contains(text);
    }

//  ------------- Assistive methods: -------------

    private void openSourceRenditionInDsEditor(String renditionUUID)
    {
        sourcePage().goToSourcePageWithRenditionUuids(renditionUUID);
        sourceNavigateGridPage().firstRenditionEditContent();
        editorPage().switchDirectlyToTextFrame();
    }
}