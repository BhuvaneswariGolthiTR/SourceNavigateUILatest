package com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.target.set3;

import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.RISK;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets;
import com.thomsonreuters.codes.codesbench.quality.utilities.regex.RegexUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CopyHotkeysGRCTests extends TestService
{
        @Test
        @IE_EDGE_MODE
        @RISK
        @LOG
        public void copyRawXmlThroughCtrlShiftCTest()
        {
                // any node with a text paragraph and with >= 2 chunks
                String uuid = "I0289882129A111E6BDEEB8CA3AB007EB";

                homePage().goToHomePage();
                loginPage().logIn();
                hierarchyNavigatePage().goToHierarchyPage(ContentSets.CROWN_DEPENDENCIES.getCode());
                hierarchySearchPage().searchNodeUuid(uuid);
                siblingMetadataPage().selectedSiblingMetadataEditContent();

                int targetChunkNumber = 2;
                editorPage().scrollToChunk(targetChunkNumber);

                String secondChunk = String.format(EditorTextPageElements.LOADED_CHUNK, 1);
                editorPage().scrollToElement(secondChunk + EditorTextPageElements.FIRST_TEXT_PARAGRAPH);

                String rawXml = editorTextPage().copyXmlThroughCtrlShiftC(
                        secondChunk + EditorTextPageElements.FIRST_TEXT_PARAGRAPH);
                boolean rawXmlHasCorrectFormat = RegexUtils.matchesRegex(
                        rawXml, EditorTextPageElements.TEXT_PARAGRAPH_XML_REGEX_FORMAT);
                editorPage().closeDocumentWithNoChanges();

                Assertions.assertAll
                        (
                                () -> Assertions.assertTrue(rawXmlHasCorrectFormat, String.format(
                                        "The content of the clipboard: %n%s%n does not match the format: %n%s%n",
                                        rawXml, EditorTextPageElements.TEXT_PARAGRAPH_XML_REGEX_FORMAT))
                        );
        }

        @Test
        @IE_EDGE_MODE
        @RISK
        @LOG
        public void copyTextThroughShiftAltCTest()
        {
                // any node with a text paragraph and with >= 2 chunks
                String uuid = "I0289882129A111E6BDEEB8CA3AB007EB";

                homePage().goToHomePage();
                loginPage().logIn();
                hierarchyNavigatePage().goToHierarchyPage(ContentSets.CROWN_DEPENDENCIES.getCode());
                hierarchySearchPage().searchNodeUuid(uuid);
                siblingMetadataPage().selectedSiblingMetadataEditContent();

                int targetChunkNumber = 2;
                editorPage().scrollToChunk(targetChunkNumber);

                String secondChunk = String.format(EditorTextPageElements.LOADED_CHUNK, 1);
                editorPage().scrollToElement(secondChunk + EditorTextPageElements.FIRST_TEXT_PARAGRAPH);

                String textFromClipboard = editorTextPage().copyTextThroughShiftAltC(
                        secondChunk + EditorTextPageElements.FIRST_TEXT_PARAGRAPH);
                String paragraphText = editorTextPage().getElementsText(
                        EditorTextPageElements.HIGHLIGHTED_PARA + EditorTextPageElements.PARATEXT);
                boolean textIsCorrect = paragraphText.equals(textFromClipboard);
                editorPage().closeDocumentWithNoChanges();

                Assertions.assertAll
                        (
                                () -> Assertions.assertTrue(textIsCorrect, String.format(
                                        "The content of the clipboard: %n%s%n does not match the paragraph text: %n%s%n",
                                        textFromClipboard, paragraphText))
                        );
        }
}
