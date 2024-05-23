package com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.target.set2;

import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTreePageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.popups.DocumentPreviewPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.popups.InsertSpecialCharacterPageElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.RISK;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.SpecialCharacters;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.openqa.selenium.WebElement;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.*;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.popups.TableEditorPageElements.FIRST_INPUT_FIELD;
import static com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import static com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import static org.assertj.core.api.Assertions.assertThat;

public class TableTests extends TestService
{
    public enum InsertMethod
    {
        TOOLBAR,
        HOTKEY,
        CONTEXT_MENU
    }

    /**
     * STORY/BUG - 754299 <br>
     * SUMMARY -  This test verifies
     *          1. three different ways(Toolbar,Hotkeys,ContextMenu) of inserting table
     *          2. default values present on generated table entries
     * <br>
     * USER - Legal <br>
     */
    @ParameterizedTest(name = "Test: {displayName}; Index: {index}; Arguments: {arguments}")
    @EnumSource
            (
                    TableTests.InsertMethod.class
            )
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void insertTableToolbarTargetLegalTest(TableTests.InsertMethod insertMethod)
    {
        String uuid = "I2A8B5B9014F511DA8AC5CD53670E6B4E";

        String columnNumber = "3";
        List<String> expectedMetadataTags = Arrays.asList("STBL", "DTBLW", "H", "B", "WSOC", "ETBL");
        String insertHint = "Insert text";
        List<String> expectedPreviewText = Arrays.asList(columnNumber, "", insertHint, insertHint);
        int chunkNumber = 2;

        hierarchyNavigatePage().goToHierarchyPage(ContentSets.IOWA_DEVELOPMENT.getCode());
        loginPage().logIn();
        hierarchySearchPage().searchNodeUuid(uuid);

        // open DS editor
        siblingMetadataPage().selectedSiblingMetadataEditContent();

        // scroll to chunk
        editorPage().scrollToChunk(chunkNumber);

        String fourthChunk = String.format(EditorTextPageElements.LOADED_CHUNK, chunkNumber - 1);
        String element = fourthChunk + String.format(EditorTextPageElements.TEXT_PARAGRAPH_UNDER_NUMBER_GIVEN, 1);

        switch(insertMethod) {
            case TOOLBAR:
                editorTextPage().scrollToElement(element + EditorTextPageElements.PARATEXT);
                editorTextPage().click(element);
                editorPage().breakOutOfFrame();
                editorToolbarPage().clickInsertTable();
                break;
            case HOTKEY:
                editorTextPage().scrollToElement(element);
                editorTextPage().click(element + EditorTextPageElements.TEXT_PARAGRAPH_LABEL);
                editorTextPage().click(element + EditorTextPageElements.TEXT_PARAGRAPH_LABEL);
                editorTextPage().addTableViaAltT();
                break;
            case CONTEXT_MENU:
                editorTextPage().scrollToView(element + EditorTextPageElements.TEXT_PARAGRAPH_LABEL);
                editorTextPage().rightClick(element + EditorTextPageElements.TEXT_PARAGRAPH_LABEL);
                editorPage().breakOutOfFrame();
                editorTextContextMenu().insertTabularSiblingTableTemplate();
                break;
            default:
                break;
        }

        insertTablePage().setNumberOfColumns("3");
        insertTablePage().clickSave();
        editorTextPage().switchToEditorTextArea();
        boolean tableAppeared = editorTextPage().doesElementExist(element + "/following-sibling::xampex.table");
        boolean tableIsHighlighted = editorTextPage().getElementsAttribute
                (element + "/following-sibling::xampex.table", "class").contains("highlighted");

        // 6 tabular entries
        int numberOfTabularEntries = editorTextPage().countElements(EditorTextPageElements.TABULAR_ENTRIES_IN_BODY_PART);

        // check mnemonics
        List<String> metadataTags = editorTextPage().getElements(EditorTextPageElements.TABLE_PARAS_MNEMONIC).
                stream().map(WebElement::getText).collect(Collectors.toList());

        // first one contains the number of columns
        boolean firstParatextContentIsOk = editorTextPage()
                .getElementsText(String.format(EditorTextPageElements.TABLE_PARAS_PARATEXT_BY_NUMBER, 1))
                .equals(columnNumber);
        boolean secondParatextContentIsOk = editorTextPage()
                .getElementsText(String.format(EditorTextPageElements.TABLE_PARAS_PARATEXT_BY_NUMBER, 2)).equals(" ");

        // third and fourth contains insert text hint
        boolean thirdParatextContentIsOk = editorTextPage()
                .getElementsText(String.format(EditorTextPageElements.TABLE_PARAS_PARATEXT_BY_NUMBER, 3)
                        + EditorTextPageElements.HINT)
                .equals(insertHint);

        boolean fourthParatextContentIsOk = editorTextPage()
                .getElementsText(String.format(EditorTextPageElements.TABLE_PARAS_PARATEXT_BY_NUMBER, 4)
                        + EditorTextPageElements.HINT)
                .equals(insertHint);

        // fifth and sixth contains the 3-space special character
        boolean fifthParatextContentIsOk = editorTextPage()
                .getElementsText(String.format(EditorTextPageElements.TABLE_PARAS_PARATEXT_BY_NUMBER, 5)
                        + "/span[@entity='emsp']").contains(SpecialCharacters.EMSP.getCharacter());

        boolean sixthParatextContentIsOk = editorTextPage()
                .getElementsText(String.format(EditorTextPageElements.TABLE_PARAS_PARATEXT_BY_NUMBER, 6)
                        + "/span[@entity='emsp']").contains(SpecialCharacters.EMSP.getCharacter());

        // preview
        editorPage().breakOutOfFrame();
        editorTreePage().rightClick(EditorTreePageElements.XAMPEX_TABLE);
        boolean previewAppeared = editorTreeContextMenu().preview();

        documentPreviewPage().waitForElementExists(DocumentPreviewPageElements.PARATEXT);
        List<String> actualPreviewText = documentPreviewPage().getElements(DocumentPreviewPageElements.PARATEXT)
                .stream().limit(4).map(WebElement::getText).collect(Collectors.toList());
        documentPreviewPage().closePreview();

        editorPage().closeDocumentAndDiscardChanges();

        Assertions.assertAll
                (
                        () -> Assertions.assertTrue(tableAppeared, "Table should appear"),
                        () -> Assertions.assertTrue(tableIsHighlighted, "Table should be highlighted"),
                        () -> Assertions.assertEquals(6, numberOfTabularEntries,
                                "There should be 6 tabular entries, but " +
                                        numberOfTabularEntries + " were found"),
                        () -> Assertions.assertEquals(metadataTags, expectedMetadataTags, "Mnemonics should be as in a list provided"),
                        () -> Assertions.assertTrue(firstParatextContentIsOk, "First table paratext should contain columns number"),
                        () -> Assertions.assertTrue(secondParatextContentIsOk, "Second table paratext should be empty"),
                        () -> Assertions.assertTrue(thirdParatextContentIsOk, "Third table paratext should contain hint"),
                        () -> Assertions.assertTrue(fourthParatextContentIsOk, "Fourth table paratext should contain hint"),
                        () -> Assertions.assertTrue(fifthParatextContentIsOk, "Fifth table paratext should contain 3-space character"),
                        () -> Assertions.assertTrue(sixthParatextContentIsOk, "Sixth table paratext should contain 3-space character"),
                        () -> Assertions.assertTrue(previewAppeared,"Preview should appear"),
                        () -> Assertions.assertEquals(expectedPreviewText, actualPreviewText, "Expected table content is displayed in Preview")
                );

    }
}
