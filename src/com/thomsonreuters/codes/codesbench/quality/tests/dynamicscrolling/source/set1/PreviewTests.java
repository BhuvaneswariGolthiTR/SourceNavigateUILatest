package com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.source.set1;

import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.popups.DocumentPreviewPageElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.stream.Collectors;

import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.popups.InsertWestMarkupPageElements.*;

public class PreviewTests extends TestService {
    /* Preview Text Paragraph Content and Refresh
     * NOTE: Maybe just copy a test over to here that inserts a lot of the simple markup like bold, italics, underline, etc.
     *
     *
     *  1. Select and right click the text paragraph english label after inserting the text with markup
     *  2. Select Preview
     *  VERIFY: Preview appears
     *  VERIFY: The text content of the text paragraph appears in the Preview window.  Will have to save the text into a variable
     *  before opening preview
     *  VERIFY: Preview is read-only
     *  VERIFY: The different markups appear correctly
     *
     *  3. closePreview()
     *  VERIFY: Preview window no longer appears
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void previewMarkupSourceLegalTest()
    {
        String[] terms = {"boldText", "addedText", "strikethroughText", "underlineText", "superscriptText", "subscriptText"};
       // String[] markups = {BOLD, ADDED, STRIKETHROUGH, UNDERSCORE, SUPERSCRIPT, SUBSCRIPT};
        String[] markups = {"bold", "added.material",  "strikethrough", "underscore", "super","sub"};
        String uuid = "I9E9FDC90EB8611E582679EDDE172CEE8";
        //String uuid = "IABE9C160EC6A11E587CF8FA76E8F4335"; //for dev
        int targetChunkNumber = 2;
        String phrase = "Etobanany";
        // open DS editor
        sourcePage().goToSourcePageWithRenditionUuids(uuid);
        loginPage().logIn();
        sourceNavigateGridPage().firstRenditionEditContent();
        editorPage().switchToEditor();
        // scroll to chunk
        editorPage().scrollToChunk(targetChunkNumber);
        // find target paragraph
        String textParagraphXpath = String.format(EditorTextPageElements.SOURCE_BODY +
                EditorTextPageElements.LAW_BLOCK_SPAN, 6);
        String textParagraphParatextXpath = String.format(EditorTextPageElements.SOURCE_BODY +
                EditorTextPageElements.LAW_BLOCK_PARATEXT, 6);
        String text = editorPage().getElementsText(textParagraphParatextXpath);
        editorTextPage().insertPhrasesWithRespectiveMarkup(textParagraphXpath, terms, markups, 1);
        // without something, that will refresh the content, like loading next chunk or just save point
        // preview will not show last changes
        editorToolbarPage().clickSave();
        editorPage().switchToEditorTextFrame();
        editorPage().click(textParagraphXpath);
        editorTextContextMenu().preview(textParagraphXpath);
        String previewText = editorPage().getElementsText(EditorTextPageElements.DIV_PARATEXT);
        boolean textSame = previewText.contains(text);
        WebElement divParatext = editorPage().getElement(EditorTextPageElements.DIV_PARATEXT);
        divParatext.click();
        editorPage().sendKeysWithException(divParatext, phrase);
        boolean previewIsReadOnly = !editorPage().getSource().contains(phrase);
        // verify that each term has its respective markup
        boolean boldTextAppeared = editorPage()
                .doesElementExist(String.format(DocumentPreviewPageElements.PHRASE_WITH_BOLD_MARKUP, "boldText"));
        boolean addedTextAppeared =editorPage()
                .doesElementExist(String.format(DocumentPreviewPageElements.PHRASE_WITH_ADDED_MARKUP, "addedText"));
        boolean strikeThroughTextAppeared = editorPage()
                .doesElementExist(String.format(DocumentPreviewPageElements.PHRASE_WITH_STRIKED_MARKUP, "strikethroughText"));
        boolean underlineTextAppeared = editorPage()
                .doesElementExist(String.format(DocumentPreviewPageElements.PHRASE_WITH_UNDERLINE_MARKUP, "underlineText"));
        boolean superscriptTextAppeared = editorPage()
                .doesElementExist(String.format(DocumentPreviewPageElements.PHRASE_WITH_SUPERSCRIPT_MARKUP, "superscriptText"));
        boolean subscriptTextAppeared = editorPage()
                .doesElementExist(String.format(DocumentPreviewPageElements.PHRASE_WITH_SUBSCRIPT_MARKUP, "subscriptText"));
        // close
        documentPreviewPage().closePreview();
        editorPage().closeDocumentAndDiscardChanges();
        // assertions
        Assertions.assertAll(
                () -> Assertions.assertTrue(textSame, String.format("Text in Preview should contain text from paragragh " +
                        "in editor's textframe. Expected: %s, actual: %s", text, previewText)),
                () -> Assertions.assertTrue(previewIsReadOnly, "Preview should be read-only"),
                () -> Assertions.assertTrue(boldTextAppeared, "Bold text should appear in the preview"),
                () -> Assertions.assertTrue(addedTextAppeared, "Added text should appear in the preview"),
                () -> Assertions.assertTrue(strikeThroughTextAppeared, "Strikethrough text should appear in the preview"),
                () -> Assertions.assertTrue(underlineTextAppeared, "Underline text should appear in the preview"),
                () -> Assertions.assertTrue(superscriptTextAppeared, "Superscript text should appear in the preview"),
                () -> Assertions.assertTrue(subscriptTextAppeared, "Subscript text should appear in the preview")
        );
    }

    /* Preview Table
     *
     * 1. Insert a table
     * 2. Select and right click the table english label
     * 3. Select Preview
     * VERIFY: Preview appears
     * VERIFY: The text content of the table appears in the Preview window.  Will have to save the text into a variable
     * before opening preview
     * VERIFY: Preview is read-only
     *
     * 4. closePreview()
     * VERIFY: Preview window no longer appears
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void previewTableSourceLegalTest()
    {
        String uuid = "I6F3B00102F4611E29538B629ABD39661";
        //String uuid = "I49AB9821671611E29883F66BF9B3C8F0"; // for dev
        int targetChunkNumber = 2;
        // open DS editor
        sourcePage().goToSourcePageWithRenditionUuids(uuid);
        loginPage().logIn();
        sourceNavigateGridPage().firstRenditionEditContent();
        editorPage().switchToEditor();
        // scroll to chunk
        editorPage().scrollToChunk(targetChunkNumber);
        // add table
        editorTextContextMenu().insertChildTableTemplate("//source.section[@full-display-name='Source Section']/span");
        // without something, that will refresh the content, like loading next chunk or just save point
        // preview will not show last changes
        editorToolbarPage().clickSave();
        editorPage().switchToEditorTextFrame();
        editorPage().click("//source.section[@full-display-name='Source Section']/span");
        editorTextContextMenu().preview("//source.section[@full-display-name='Source Section']/span");
        // check table content
        List<String> text = editorPage().getElements(EditorTextPageElements.DIV_PARATEXT)
                .stream()
                .limit(4)
                .map(WebElement::getText)
                .collect(Collectors.toList());
        boolean textIsAsExpected = text.get(0).equals("3")
                && text.get(1).equals("")
                && text.get(2).equals("Insert text")
                && text.get(3).equals("Insert text");
        documentPreviewPage().closePreview();
        editorPage().closeDocumentAndDiscardChanges();
        // assertions
        Assertions.assertAll(
                () -> Assertions.assertTrue(textIsAsExpected, String.format("Text in Preview should contain text " +
                        "from paragragh in editor's textframe. Expected: \"3\", \" \", \"Insert text\", \"Insert text\"." +
                        " Actual: \"%s\", \"%s\", \"%s\",\"%s\"", text.get(0), text.get(1), text.get(2), text.get (3)))
        );
    }

    /* Preview editor tree content
     * 1. Open document
     * 2. Expand the body element in the tree
     * 3. Select a subsection or text paragraph
     * VERIFY: You are brought to the subsection or text paragraph in the editor
     * VERIFY: The element appears selected in both the tree and content area
     * 4. Right click and select Preview
     * VERIFY: The text content of this element appears in the Preview window
     * VERIFY: Preview is read only
     * 5. closePreview()
     * VERIFY: Preview window no longer appears
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void previewTreeNodeSourceLegalTest()
    {
        String uuid = "I9E9FDC90EB8611E582679EDDE172CEE8";
        //String uuid = "IABE9C160EC6A11E587CF8FA76E8F4335"; // for dev
        // open DS editor
        sourcePage().goToSourcePageWithRenditionUuids(uuid);
        loginPage().logIn();
        sourceNavigateGridPage().firstRenditionEditContent();
        editorPage().switchToEditor();
        // expand tree
        editorTreePage().expandEditorsTreeAndClickNode("source.body", "pending.law.block", "para \"SMP52\"");
        // check text frame
        editorPage().switchToEditorTextFrame();
        boolean textParagraphGotHighlighted =  editorPage().doesElementExist(EditorTextPageElements.HIGHLIGHTED_PARA);
        String textFromTextframe =  editorPage().getElementsText(EditorTextPageElements.HIGHLIGHTED_PARA + EditorTextPageElements.PARATEXT);
        // check preview content
        editorPage().switchToEditor();
        editorTreePage().previewTreeNode("para \"SMP52\"");
        String textFromPreview =  editorPage().getElementsText(EditorTextPageElements.DIV_PARATEXT);
        boolean textsAreEqual = textFromTextframe.equals(textFromPreview);
        // close
        documentPreviewPage().closePreview();
        editorPage().closeDocumentWithNoChanges();
        // assertions
        Assertions.assertAll(
                () -> Assertions.assertTrue(textParagraphGotHighlighted, "Text paragragh in editor's textframe got highlighted after clicking in the Tree"),
                () -> Assertions.assertTrue(textsAreEqual, "Text in Preview should be equal to text in editor's textframe")
        );
    }
}
