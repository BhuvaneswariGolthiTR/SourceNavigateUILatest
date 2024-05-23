package com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.source.set1;

import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTreePageElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.SpecialCharacters;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class TableLegalTests extends TestService
{
        public enum InsertMethod
        {
                TOOLBAR,
                HOTKEY,
                CONTEXT_MENU
        }

        @ParameterizedTest(name = "Test: {displayName}; Index: {index}; Arguments: {arguments}")
        @EnumSource
                (
                        names = {"TOOLBAR", "HOTKEY", "CONTEXT_MENU"}
                )
        @IE_EDGE_MODE
        @LEGAL
        @LOG
        public void insertTableSourceLegalTest(InsertMethod insertMethod)
        {
                String uuid = "IBF152BE1294811E895B794E6E83E4633";
                //String uuid = "I11F538B0492411E291A5CFC49AB788FF"; // for dev

                String columnNumber = "3";
                List<String> expectedMetadataTags = Arrays.asList("STBL", "DTBLW", "H", "B", "WSOC", "ETBL");
                String insertHint = "Insert text";
                List<String> expectedPreviewText = Arrays.asList(columnNumber, "", insertHint, insertHint);

                sourcePage().goToSourcePageWithRenditionUuids(uuid);
                loginPage().logIn();
                sourceNavigateGridPage().firstRenditionEditContent();

                int chunkNumber = 4;
                editorPage().scrollToChunk(chunkNumber);

                String firstPara = String.format(EditorTextPageElements.LOADED_CHUNK, chunkNumber - 1)
                        + String.format(EditorTextPageElements.TEXT_PARAGRAPH_UNDER_NUMBER_GIVEN, 1);

                String firstParaSpan = firstPara + EditorTextPageElements.TEXT_PARAGRAPH_LABEL;
                editorTextPage().scrollToView(firstParaSpan);

                switch(insertMethod) {
                        case TOOLBAR:
                                editorTextPage().click(firstParaSpan);
                                editorPage().breakOutOfFrame();
                                editorToolbarPage().clickInsertTable();
                                break;
                        case HOTKEY:
                                editorTextPage().click(firstParaSpan);
                                editorTextPage().addTableViaAltT();
                                break;
                        case CONTEXT_MENU:
                                editorTextPage().rightClick(firstParaSpan);
                                editorPage().breakOutOfFrame();
                                editorTextContextMenu().insertTabularSiblingTableTemplate();
                                break;
                        default:
                                break;
                }

                insertTablePage().setNumberOfColumns("3");
                insertTablePage().clickSave();
                editorTextPage().switchToEditorTextArea();
                boolean tableAppeared = editorTextPage().doesElementExist(firstPara + "/following-sibling::xampex.table");
                boolean tableIsHighlighted = editorTextPage().getElementsAttribute
                        (firstPara + "/following-sibling::xampex.table", "class").contains("highlighted");

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

                List<String> actualPreviewText = documentPreviewPage().getElements(EditorTextPageElements.DIV_PARATEXT)
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

        @Test
        @IE_EDGE_MODE
        @LEGAL
        @LOG
        public void convertTextParagraphsToTableTest()
        {
                String uuid = "IBCFE9440AFBC11E3B34DCF0398D7238F";
                //String uuid = "IB76A1C60627011E29276BA532FA0D416"; // for dev

                sourcePage().goToSourcePageWithRenditionUuids(uuid);
                loginPage().logIn();
                sourceNavigateGridPage().firstRenditionEditContent();
                editorPage().switchToEditor();
                editorPage().closeEditorWithDiscardingChangesIfAppeared();

                sourcePage().switchToSourceNavigatePage();
                sourceNavigateGridPage().firstRenditionEditContent();
                editorPage().switchToEditor();

                int chunkNumber = 2;
                editorPage().scrollToChunk(chunkNumber);

                String firstPara = String.format(EditorTextPageElements.TEXT_PARAGRAPH_FROM_CHUNK,
                        chunkNumber - 1, 1);
                String firstParaHighlighted = String.format(EditorTextPageElements.HIGHLIGHTED_PARA_FROM_CHUNK,
                        chunkNumber - 1, 1);
                String firstParaSpan = firstPara + EditorTextPageElements.SPAN;

                String highlightedSibling = firstPara + "/following-sibling::para[contains(@class,'highlighted')]";

                String secondPara = String.format(EditorTextPageElements.TEXT_PARAGRAPH_FROM_CHUNK,
                        chunkNumber - 1, 2);
                String secondParaSpan = secondPara + EditorTextPageElements.SPAN;

                String thirdPara = String.format(EditorTextPageElements.TEXT_PARAGRAPH_FROM_CHUNK,
                        chunkNumber - 1, 3);
                String thirdParaSpan = thirdPara + EditorTextPageElements.SPAN;

                String fourthPara = String.format(EditorTextPageElements.TEXT_PARAGRAPH_FROM_CHUNK,
                        chunkNumber - 1, 4);
                String fourthParaSpan = fourthPara + EditorTextPageElements.SPAN;

                editorTextPage().click(firstParaSpan);
                editorTextPage().waitForElement(firstParaHighlighted);
                editorPage().insertNewParagraphViaContextMenu(firstParaSpan);
                editorPage().waitForElement(highlightedSibling);
                editorTextPage().typeTextIntoGivenParagraph(EditorTextPageElements.HIGHLIGHTED_PARA
                        + EditorTextPageElements.PARATEXT, "This is the first paragraph to convert.");

                editorTextPage().click(secondParaSpan);
                editorTextPage().waitForElement(highlightedSibling);
                editorPage().insertNewParagraphViaContextMenu(secondParaSpan);
                editorPage().waitForElement(highlightedSibling);
                editorTextPage().typeTextIntoGivenParagraph(EditorTextPageElements.HIGHLIGHTED_PARA
                        + EditorTextPageElements.PARATEXT, "This is the second paragraph to convert.");

                editorTextPage().click(thirdParaSpan);
                editorTextPage().waitForElement(highlightedSibling);
                editorPage().insertNewParagraphViaContextMenu(thirdParaSpan);
                editorPage().waitForElement(highlightedSibling);
                editorTextPage().typeTextIntoGivenParagraph(EditorTextPageElements.HIGHLIGHTED_PARA
                        + EditorTextPageElements.PARATEXT, "This is the third paragraph to convert.");

                editorTextPage().rightClick(secondParaSpan);
                editorTextPage().breakOutOfFrame();
                editorTextContextMenu().insertTabularSiblingInsertStart();
                editorTextPage().switchToEditorTextArea();

                editorTextPage().rightClick(fourthParaSpan);
                editorTextPage().breakOutOfFrame();
                editorTextContextMenu().insertTabularSiblingInsertStop();
                editorTextPage().switchToEditorTextArea();

                int numberOfTablesBeforeConverting = editorTextPage().countElements(
                        EditorTextPageElements.XAMPEX_TABLE_IN_BODY_PART);
                boolean startMarkAppeared = editorTextPage().doesElementExist(secondPara
                        + EditorTextPageElements.HINT_START_AS_PRECEDING_SIBLING);
                boolean stopMarkAppeared = editorTextPage().doesElementExist(fourthPara
                        + EditorTextPageElements.HINT_STOP_AS_FOLLOWING_SIBLING);
                int numberOfTextParasBeforeConverting = editorTextPage().countElements(firstPara
                        + "/following-sibling::para[@display-name='Text Paragraph']");
                int numberOfTabularEntriesBeforeConverting = editorTextPage().
                        countElements(EditorTextPageElements.TABULAR_ENTRIES_IN_BODY_PART);

                editorTextPage().click(EditorTextPageElements.HINT_START_TABLE_MARKER);
                editorTextPage().rightClick(EditorTextPageElements.HINT_START_TABLE_MARKER);
                editorTextPage().breakOutOfFrame();
                editorTextContextMenu().convertToTable();
                editorTextPage().switchToEditorTextArea();

                int numberOfTablesAfterConverting = editorTextPage().countElements(
                        EditorTextPageElements.XAMPEX_TABLE_IN_BODY_PART);
                boolean startMarkDisappearedAfterConverting = !editorTextPage().doesElementExist(
                        EditorTextPageElements.HINT_START_TABLE_MARKER);
                boolean stopMarkDisappearedAfterConverting = !editorTextPage().doesElementExist(
                        EditorTextPageElements.HINT_STOP_TABLE_MARKER);
                int numberOfTextParasAfterConverting = editorTextPage().countElements(firstPara
                        + "/following-sibling::para[@display-name='Text Paragraph']");
                int numberOfTabularEntriesAfterConverting = editorTextPage().countElements(
                        EditorTextPageElements.TABULAR_ENTRIES_IN_BODY_PART);

                // each Tabular entry has "B" in a metadata block, except first("STBL") and last ("ETBL")
                List<String> metadataTags = editorTextPage().getElements(EditorTextPageElements.TABLE_PARAS_MNEMONIC).
                        stream().map(WebElement::getText).collect(Collectors.toList());

                // modified by displays test user and current date
                boolean modifiedByTag = editorTextPage().getElement(EditorTextPageElements.TABLE_PARAS_MODIFIED_BY).
                        getText().equals(editorTextPage().getModifiedByTag(this.user()));

                // exit
                editorTextPage().breakOutOfFrame();

                editorPage().closeDocumentAndDiscardChanges();

                int numberOfParagraphsToConvert = 3;

                int listLength = numberOfParagraphsToConvert + 2; // additional 2 for STBL/ETBL

                List<String> expectedMetadataInfo = new ArrayList<>(Collections.nCopies(listLength, "B"));
                expectedMetadataInfo.set(0, "STBL");
                expectedMetadataInfo.set(listLength - 1, "ETBL");

                Assertions.assertAll
                (
                        () -> Assertions.assertEquals(numberOfTextParasBeforeConverting - numberOfParagraphsToConvert, numberOfTextParasAfterConverting,
                                                    "Number of text paragraphs should decrease by number of paragraphs we've converted"),
                        () -> Assertions.assertTrue(startMarkAppeared, "Start mark should appear"),
                        () -> Assertions.assertTrue(stopMarkAppeared, "Stop mark should appear"),
                        () -> Assertions.assertTrue(startMarkDisappearedAfterConverting, "Start mark should disappear after converting"),
                        () -> Assertions.assertTrue(stopMarkDisappearedAfterConverting, "Stop mark should disappear after converting"),
                        () -> Assertions.assertEquals(numberOfTablesBeforeConverting + 1, numberOfTablesAfterConverting, "Table element should appear"),
                        () -> Assertions.assertEquals(numberOfTabularEntriesBeforeConverting + numberOfParagraphsToConvert + 2, numberOfTabularEntriesAfterConverting,
                                                "Number of tabular entries should increase by correct number (number former paragraphs and 2 for Start/Stop tags)"),
                        () -> Assertions.assertEquals(metadataTags, expectedMetadataInfo, "Metadata in tabular entries should be correct"),
                        () -> Assertions.assertTrue(modifiedByTag, "ModifiedBy tag should display correct info")
                );
        }

}
