package com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.source.set1;

import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.regex.RegexUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class CopyHotkeysLegalTests extends TestService
{
        String uuid = "I907FF6F1FCBE11E69D2EF907E3E4B606";
        @Test
        @IE_EDGE_MODE
        @LEGAL
        @LOG
        public void copyRawXmlThroughCtrlShiftCTest()
        {
                // any rendition with a text paragraph and with >=2 chunks
                //String uuid = "IC94B896118B811E8820588494B6E0205"; // for dev

                sourcePage().goToSourcePageWithRenditionUuids(uuid);
                loginPage().logIn();
                sourceNavigateGridPage().firstRenditionEditContent();
                editorPage().switchToEditorTextFrame();
                String rawXml = editorTextPage().copyXmlThroughCtrlShiftC(
                        String.format(EditorTextPageElements.TEXT_PARAGRAPH_WITH_NUMBER_PARA, 2) + "/span");
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
        @LEGAL
        @LOG
        public void copyTextThroughShiftAltCTest()
        {
                // any rendition with a text paragraph and with >=2 chunks
                //String uuid = "IC94B896118B811E8820588494B6E0205"; // for dev

                sourcePage().goToSourcePageWithRenditionUuids(uuid);
                loginPage().logIn();
                sourceNavigateGridPage().firstRenditionEditContent();

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
