package com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.target.set1;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.CLASS_HIGHLIGHTED_POSTFIX;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.PARA;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.SOURCE_TAG;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.SPAN;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.TEXT_PARAGRAPH_SPAN;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.popups.EditorPreferencesPageElements.DEFAULT_SOURCE_TAG_SELECTOR;
import static com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.target.set1.AutomaticSourceTagUpdateTests.RadioButton.NO;
import static com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.target.set1.AutomaticSourceTagUpdateTests.RadioButton.YES;
import static com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets.IOWA_DEVELOPMENT;
import static org.assertj.core.api.Assertions.assertThat;

public class AutomaticSourceTagUpdateTests extends TestService
{
    private static final String SOURCE_TAG_ASSERTION_MESSAGE = "The source tag should be %s";
    private static final String HIGHLIGHTED_PARA_SOURCE_TAG = PARA + CLASS_HIGHLIGHTED_POSTFIX + SOURCE_TAG;
    private static final String SOURCE_TAG_VALUE = "17-A1";
    private static final String TARGET_NODE_UUID = "I2AC2273014EE11DA8AC5CD53670E6B4E";
    private static final String TARGET_NODE_UUID_2 = "I2AC7093014EE11DA8AC5CD53670E6B4E";
    private static final String SOURCE_RENDITION_UUID = "I51CEAFE0AC0611E6B81DE813DB1990E2";

    enum RadioButton
    {
        YES,
        NO
    }

    @BeforeEach
    public void loginAndOpenTargetNodeInDsEditor()
    {
        homePage().goToHomePage();
        loginPage().logIn();
        hierarchyNavigatePage().goToHierarchyPage(IOWA_DEVELOPMENT.getCode());
        hierarchySearchPage().searchNodeUuid(TARGET_NODE_UUID);
        siblingMetadataPage().selectedSiblingMetadataEditContent();
        editorPage().switchToEditorTextFrame();
        editorPage().switchToEditor();
    }

    @AfterEach
    public void closeAllOpenedDocuments()
    {
        editorPage().closeAllOpenedDocumentsWithDiscardingChangesIfAppeared();
    }

    public static Object[][] testData()
    {
        return new Object[][]
                {
                        {YES},
                        {NO}
                };
    }

    /**
     * STORY/BUG - n/a <br>
     * SUMMARY - Automatic Source Tag Update (Target) <br>
     * USER - LEGAL <br>
     */
    @ParameterizedTest(name = "Test: {displayName}; Index: {index}; Arguments: {argumentsWithNames}")
    @MethodSource("testData")
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void automaticSourceTagUpdateTargetLegalTest(RadioButton radioButtonValue)
    {
        //Select automatic source tag update radio button and save
        editorToolbarPage().clickConfigureEditorSessionPreferences();
        selectAutomaticSourceTagUpdateRadioButton(radioButtonValue);
        String defaultSourceTag = getDefaultSourceTag();
        editorPreferencesPage().clickSaveButton();

        //Open second target node in DS editor
        hierarchyNavigatePage().switchToHierarchyEditPage();
        hierarchySearchPage().searchNodeUuid(TARGET_NODE_UUID_2);
        hierarchyNavigatePage().switchToHierarchyEditPage();
        siblingMetadataPage().selectedSiblingMetadataEditContent();

        //Copy paragraph from one target node to another
        switchToSecondDocument();
        copyParagraph();

        //Paste paragraph from one target node to another
        switchToFirstDocument();
        pasteParagraph();
        //Assert that the correct source tag is applied
        editorPage().switchToEditorTextFrame();
        assertThatSourceTagMatchesToAutomaticSourceTagUpdateRadioButtonValue(radioButtonValue, defaultSourceTag);
    }

    @ParameterizedTest(name = "Test: {displayName}; Index: {index}; Arguments: {argumentsWithNames}")
    @MethodSource("testData")
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void retainSourceDocumentSourceTagsLegalTest(RadioButton radioButtonValue)
    {
        //Select retain source document source tags radio button and save
        editorToolbarPage().clickConfigureEditorSessionPreferences();
        selectRetainSourceDocumentSourceTagsRadioButton(radioButtonValue);
        editorPreferencesPage().clickAutomaticCreditGenerationNoRadioButton();
        String defaultSourceTag = getDefaultSourceTag();
        editorPreferencesPage().clickSaveButton();

        //Open source rendition in DS editor
        hierarchyNavigatePage().switchToHierarchyEditPage();
        sourcePage().goToSourcePageWithRenditionUuids(SOURCE_RENDITION_UUID);
        sourceNavigateGridPage().firstRenditionEditContent();

        //Insert source tag
        switchToSecondDocument();
        openContextMenuOnElement(TEXT_PARAGRAPH_SPAN);

        editorTextContextMenu().insertSourceTag();
        changeSourceTagPage().switchToChangeSourceTagWindow();
        changeSourceTagPage().selectDropdownOptionUsingJavascript("selectSourceTag", SOURCE_TAG_VALUE);
        changeSourceTagPage().clickOk();

        //Copy paragraph with source tag from source rendition
        switchToSecondDocument();
        copyParagraph();

        //Paste paragraph with source tag to target node
        switchToFirstDocument();
        pasteParagraph();
        //Assert that the correct source tag is applied within the pasted paragraph in target node
        switchToFirstDocument();
        assertThatSourceTagMatchesToRetainSourceDocumentSourceTagsRadioButtonValue(radioButtonValue, defaultSourceTag);
        //Assert that source tag still exists within the paragraph in source rendition
        switchToSecondDocument();
        assertThat(editorTextPage().getElementsText(PARA + SOURCE_TAG))
                .as(String.format(SOURCE_TAG_ASSERTION_MESSAGE, SOURCE_TAG_VALUE))
                .isEqualTo(SOURCE_TAG_VALUE);

        editorPage().switchToEditor();
        editorToolbarPage().clickUndo();
        editorToolbarPage().clickUndo();

        //Copy paragraph without source tag from source rendition
        switchToSecondDocument();
        copyParagraph();

        //Paste paragraph without source tag to target node
        switchToFirstDocument();
        pasteParagraph();
        //Assert that source tag on the pasted paragraph in target node is the same as the default source tag
        switchToFirstDocument();
        assertThatSourceTagOnThePastedParagraphIsDefault(defaultSourceTag);

        //Copy paragraph with default source tag from target node
        openContextMenuOnElement(PARA + CLASS_HIGHLIGHTED_POSTFIX + SPAN);
        editorTextContextMenu().copyCtrlC();

        //Paste paragraph with default source tag to source rendition
        switchToSecondDocument();
        pasteParagraph();
        //Assert that there is no any source tag after copying paragraph from target node and pasting it to source rendition
        switchToSecondDocument();
        assertThat(editorTextPage().doesElementExist(HIGHLIGHTED_PARA_SOURCE_TAG))
                .as("There should be no any source tag")
                .isFalse();
    }

//  ------------- Assistive methods: -------------

    private String getHighlightedParaSourceTag()
    {
        return editorTextPage().getElementsText(HIGHLIGHTED_PARA_SOURCE_TAG);
    }

    private void switchToSecondDocument()
    {
        editorPage().switchToEditor();
        editorPage().switchToTheOpenedDocument(2);
        editorPage().switchToEditorTextFrameByIndex(3);
    }

    private void switchToFirstDocument()
    {
        editorPage().switchToEditor();
        editorPage().switchToTheOpenedDocument(1);
        editorPage().switchToEditorTextFrame();
    }

    private void openContextMenuOnElement(String xPath)
    {
        editorTextPage().rightClick(xPath);
        editorTextPage().breakOutOfFrame();
    }

    private void copyParagraph()
    {
        openContextMenuOnElement(TEXT_PARAGRAPH_SPAN);
        editorTextContextMenu().copyCtrlC();
    }

    private void pasteParagraph()
    {
        openContextMenuOnElement(TEXT_PARAGRAPH_SPAN);
        editorTextContextMenu().pasteSiblingCtrlV();
    }

    private void selectAutomaticSourceTagUpdateRadioButton(RadioButton radioButtonValue)
    {
        if (radioButtonValue == YES)
        {
            editorPreferencesPage().clickAutomaticSourceTagUpdateYesRadioButton();
        }
        if (radioButtonValue == NO)
        {
            editorPreferencesPage().clickAutomaticSourceTagUpdateNoRadioButton();
        }
    }

    private void selectRetainSourceDocumentSourceTagsRadioButton(RadioButton radioButtonValue)
    {
        if (radioButtonValue == YES)
        {
            editorPreferencesPage().clickAlwaysRetainSourceDocumentSourceTagsYesRadioButton();
        }
        if (radioButtonValue == NO)
        {
            editorPreferencesPage().clickAlwaysRetainSourceDocumentSourceTagsNoRadioButton();
        }
    }

    private void assertThatSourceTagMatchesToAutomaticSourceTagUpdateRadioButtonValue(
            RadioButton radioButtonValue, String defaultSourceTag)
    {
        if (radioButtonValue == YES)
        {
            assertThatSourceTagOnThePastedParagraphIsDefault(defaultSourceTag);
        }
        if (radioButtonValue == NO)
        {
            //Assert that source tag on the pasted paragraph is not default
            assertThat(getHighlightedParaSourceTag())
                    .as(String.format("The source tag should not be %s", defaultSourceTag))
                    .isNotEqualTo(defaultSourceTag);
        }
    }

    private void assertThatSourceTagMatchesToRetainSourceDocumentSourceTagsRadioButtonValue(
            RadioButton radioButtonValue, String defaultSourceTag)
    {
        if (radioButtonValue == YES)
        {
            //Assert that the actual source tag on the pasted paragraph is the same as on the source document paragraph
            assertThat(getHighlightedParaSourceTag())
                    .as(String.format(SOURCE_TAG_ASSERTION_MESSAGE, SOURCE_TAG_VALUE))
                    .isEqualTo(SOURCE_TAG_VALUE);
        }
        if (radioButtonValue == NO)
        {
            //Assert that source tag on the pasted paragraph is the same as the default source tag
            assertThatSourceTagOnThePastedParagraphIsDefault(defaultSourceTag);
        }
    }

    private String getDefaultSourceTag()
    {
        return editorPreferencesPage().getElementsAttribute(DEFAULT_SOURCE_TAG_SELECTOR, "value");
    }

    private void assertThatSourceTagOnThePastedParagraphIsDefault(String defaultSourceTag)
    {
        assertThat(getHighlightedParaSourceTag())
                .as(String.format(SOURCE_TAG_ASSERTION_MESSAGE, defaultSourceTag))
                .isEqualTo(defaultSourceTag);
    }
}
