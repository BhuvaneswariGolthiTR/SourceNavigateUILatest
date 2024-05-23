package com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.source.set1;

import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

public class SplitJoinTextParagraphTests extends TestService
{
    private static final String PARAGRAPH = String.format(EditorTextPageElements.SUBSECTION_PARA_TEXT_BY_SECTION_AND_SUBSECTION_NUM, 1, 3);
    private static final String PARA_TEXT_CONTENT_ASSERTION_MESSAGE = "Para text content doesn't contain content from splitted paragraph [%s]";
    private static final String JOINED_PARA_TEXT_CONTENT_ASSERTION_MESSAGE = "Joined para text content doesn't contain content from splitted paragraph [%s]";
    private static final String PARAGRAPHS_NUMBER_ASSERTION_MESSAGE = "Expected value of paragraphs number doesn't match actual";
    private static final String MODIFIED_BY_TAG_ASSERTION_MESSAGE = "Expected modified by tag doesn't match actual";
    private static final String UUID = "I604F93F0F66311E3B148A5B04553906B";

    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void splitJoinTextParagraphByHotkeysSourceLegalTest()
    {
        sourcePage().goToSourcePageWithRenditionUuids(UUID);
        loginPage().logIn();
        sourceNavigateGridPage().firstRenditionEditContent();
        editorPage().switchToEditorTextFrame();

        String paraTextContent = editorTextPage().getElementsText(PARAGRAPH);
        editorPage().splitParagraphViaHotkey(PARAGRAPH);
        List<String> splittedParaTextContent = editorTextPage().getElementsTextList(PARAGRAPH);

        editorPage().selectMultipleTextParagraphs(4, 5);
        editorPage().joinParagraphsVia("Hotkey", PARAGRAPH);
        List<String> joinedParaTextContent = editorTextPage().getElementsTextList(PARAGRAPH);

        String actualModifiedByTag = editorPage().getElementsText(editorTextPage().getModifiedByXpath(user()));

        editorPage().closeDocumentAndDiscardChanges();

        Assertions.assertAll(
                () -> Assertions.assertEquals(2, splittedParaTextContent.size(), PARAGRAPHS_NUMBER_ASSERTION_MESSAGE),
                () -> splittedParaTextContent.forEach(textContent -> Assertions.assertTrue(paraTextContent.contains(textContent.trim()),
                        String.format(PARA_TEXT_CONTENT_ASSERTION_MESSAGE, textContent))),
                () -> Assertions.assertEquals(1, joinedParaTextContent.size(), PARAGRAPHS_NUMBER_ASSERTION_MESSAGE),
                () -> splittedParaTextContent.forEach(textContent -> Assertions.assertTrue(joinedParaTextContent.get(0).contains(textContent.trim()),
                        String.format(JOINED_PARA_TEXT_CONTENT_ASSERTION_MESSAGE, textContent))),
                () -> Assertions.assertEquals(editorTextPage().getModifiedByTag(user()), actualModifiedByTag, MODIFIED_BY_TAG_ASSERTION_MESSAGE)
        );
    }

    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void splitJoinTextParagraphByContextMenuSourceLegalTest()
    {
        sourcePage().goToSourcePageWithRenditionUuids(UUID);
        loginPage().logIn();
        sourceNavigateGridPage().firstRenditionEditContent();
        editorPage().switchToEditorTextFrame();

        String paraTextContent = editorTextPage().getElementsText(PARAGRAPH);

        editorPage().splitParagraphViaContextMenu(PARAGRAPH);
        List<String> splittedParaTextContent = editorTextPage().getElementsTextList(PARAGRAPH);

        editorPage().selectMultipleTextParagraphs(4, 5);
        editorPage().joinParagraphsVia("ContextMenu", PARAGRAPH);
        List<String> joinedParaTextContent = editorTextPage().getElementsTextList(PARAGRAPH);

        String actualModifiedByTag = editorPage().getElementsText(editorTextPage().getModifiedByXpath(user()));

        editorPage().closeDocumentAndDiscardChanges();

        Assertions.assertAll(
                () -> Assertions.assertEquals(2, splittedParaTextContent.size(), PARAGRAPHS_NUMBER_ASSERTION_MESSAGE),
                () -> splittedParaTextContent.forEach(textContent -> Assertions.assertTrue(paraTextContent.contains(textContent.trim()),
                        String.format(PARA_TEXT_CONTENT_ASSERTION_MESSAGE, textContent))),
                () -> Assertions.assertEquals(1, joinedParaTextContent.size(), PARAGRAPHS_NUMBER_ASSERTION_MESSAGE),
                () -> splittedParaTextContent.forEach(textContent -> Assertions.assertTrue(joinedParaTextContent.get(0).contains(textContent.trim()),
                        String.format(JOINED_PARA_TEXT_CONTENT_ASSERTION_MESSAGE, textContent))),
                () -> Assertions.assertEquals(editorTextPage().getModifiedByTag(user()), actualModifiedByTag, MODIFIED_BY_TAG_ASSERTION_MESSAGE)
        );
    }
}
