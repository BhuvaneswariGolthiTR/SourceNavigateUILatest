package com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.target.set1;

import com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.editor.EditorTextContextMenuElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.*;
import static com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets.IOWA_DEVELOPMENT;
import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;

public class ReorderingFootnotesInDifferentChunksTests extends TestService
{
    private static final String IOWA_NODE_UUID = "IF0BE8ED0157811DA8AC5CD53670E6B4E";

    private static final String TEXT_PARAGRAPH_FIRST_CHUNK = format(TEXT_PARAGRAPH_PARATEXT_UNDER_NUMBER_GIVEN, 1);
    private static final String TEXT_PARAGRAPH_SECOND_CHUNK = "//credit" +
            format(PRECEDING_SIBLING, "subsection") + PARA + PARATEXT;
    private static final String FIRST_FOOTNOTE = FOOTNOTE + "[1]";
    private static final String SECOND_FOOTNOTE = FOOTNOTE + "[2]";
    private static final String FOOTNOTE_REFERENCE_MARKUP_LABEL = FOOTNOTE_REFERENCE_SUPER + MARKUP_IMAGE_LABEL;
    private static final String SPAN_CONTAINS_TEXT_FOOTNOTE = SPAN + format(CONTAINS_TEXT_POSTFIX, "Footnote");

    @BeforeEach
    public void loginAndOpenTargetNodeInDsEditor()
    {
        homePage().goToHomePage();
        loginPage().logIn();
        hierarchyNavigatePage().goToHierarchyPage(IOWA_DEVELOPMENT.getCode());
        hierarchySearchPage().searchNodeUuid(IOWA_NODE_UUID);
        hierarchyNavigatePage().switchToHierarchyEditPage();
        siblingMetadataPage().selectedSiblingMetadataEditContent();
    }

    @AfterEach
    public void closeDocument()
    {
        editorPage().closeDocumentAndDiscardChanges();
    }

    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void reorderFootnotesFromDifferentChunksTest()
    {
        editorPage().switchDirectlyToTextFrame();

        // Highlight any text, right click and select Insert -> Footnote. Type '4' and click Save.
        insertFootnoteWithReferenceValueIntoTextParagraph(TEXT_PARAGRAPH_FIRST_CHUNK, 4);
        assertThatFootnoteAndReferenceAreInserted(TEXT_PARAGRAPH_FIRST_CHUNK, 4, FIRST_FOOTNOTE);

        // Highlight any text in another paragraph in different chunk,
        // right click and select Insert -> Footnote. Type '3' and click Save.
        scrollToSpecifiedChunk(2);
        insertFootnoteWithReferenceValueIntoTextParagraph(TEXT_PARAGRAPH_SECOND_CHUNK, 3);
        assertThatFootnoteAndReferenceAreInserted(TEXT_PARAGRAPH_SECOND_CHUNK, 3, SECOND_FOOTNOTE);

        // Stay on the place where the footnotes are located. Don’t scroll up to the paragraphs
        // Click 'Footnote Reorder' toolbar button.
        editorPage().switchToEditor();
        editorToolbarPage().clickFootnoteReorder();

        assertThat(editorMessagePage()
                .checkMessagePaneForText("info: Footnote reorder completed successfully!"))
                .as("There should be a message confirming reordering in the editor message pane")
                .isTrue();
        assertThat(editorMessagePage().checkForErrorInMessagePane())
                .as("No errors should appear in the editor message pane")
                .isFalse();
        editorPage().switchDirectlyToTextFrame();

        // VERIFY: Footnotes are renamed 4->1 and 3->2
        // VERIFY: Footnotes in the paragraphs are renamed to 1 and 2
        assertThatReferenceValueInFootnoteIsReordered(FIRST_FOOTNOTE, 1);
        assertThatReferenceValueInFootnoteIsReordered(SECOND_FOOTNOTE, 2);

        assertThatReferenceValueInTextParagraphIsReordered(TEXT_PARAGRAPH_SECOND_CHUNK, 2);
        scrollToSpecifiedChunk(1);
        assertThatReferenceValueInTextParagraphIsReordered(TEXT_PARAGRAPH_FIRST_CHUNK, 1);

        //Right click on any footnote reference from the text and click 'Find Footnote/Reference'
        // VERIFY: editor scrolls to the corresponding footnote location
        findFootnoteReferenceViaContextMenuVerifyThatElementIsHighlighted(
                TEXT_PARAGRAPH_FIRST_CHUNK + FOOTNOTE_REFERENCE_MARKUP_LABEL, FIRST_FOOTNOTE, FOOTNOTE
        );
        findFootnoteReferenceViaContextMenuVerifyThatElementIsHighlighted(
                TEXT_PARAGRAPH_SECOND_CHUNK + FOOTNOTE_REFERENCE_MARKUP_LABEL, SECOND_FOOTNOTE, FOOTNOTE
        );

        // Right click on any footnote and click 'Find Footnote/Reference'
        // VERIFY: editor scrolls to the corresponding footnote reference in the text
        findFootnoteReferenceViaContextMenuVerifyThatElementIsHighlighted(
                FIRST_FOOTNOTE + SPAN_CONTAINS_TEXT_FOOTNOTE,
                TEXT_PARAGRAPH_FIRST_CHUNK + FOOTNOTE_REFERENCE,
                FOOTNOTE_REFERENCE
        );
        scrollToSpecifiedChunk(2);
        findFootnoteReferenceViaContextMenuVerifyThatElementIsHighlighted(
                SECOND_FOOTNOTE + SPAN_CONTAINS_TEXT_FOOTNOTE,
                TEXT_PARAGRAPH_SECOND_CHUNK + FOOTNOTE_REFERENCE,
                FOOTNOTE_REFERENCE
        );
    }

    // ---------- Assistive Methods ----------

    private void assertThatFootnoteAndReferenceAreInserted(String xpathToParagraph,
                                                           int referenceValue,
                                                           String xpathToFootnote)
    {
        String xpathToReferenceValue = format("[text()='%s']", referenceValue);

        assertThat(
                editorTextPage()
                .doesElementExist(xpathToParagraph + FOOTNOTE_REFERENCE_SUPER + xpathToReferenceValue)
        )
                .as("The footnote reference should be inserted into the specified paragraph; its value should be equal to " + referenceValue)
                .isTrue();
        assertThat(
                editorTextPage()
                .doesElementExist( xpathToFootnote + PARA + PARATEXT + SUPER + xpathToReferenceValue)
        )
                .as("The footnote should be created; its value should be equal to " + referenceValue)
                .isTrue();
        assertThat(
                editorTextPage()
                .doesElementExist(xpathToFootnote + CLASS_HIGHLIGHTED_POSTFIX)
        )
                .as("The Footnote should be highlighted after inserting")
                .isTrue();
    }

    private void assertThatReferenceValueInFootnoteIsReordered(String xpathToFootnote, int numberOfFootnote)
    {
        assertThat(editorTextPage()
                .getElementsText(xpathToFootnote + PARA + PARATEXT + SUPER))
                .as("The reference value should be reordered and equal to " + numberOfFootnote)
                .isEqualTo(String.valueOf(numberOfFootnote));
    }

    private void assertThatReferenceValueInTextParagraphIsReordered(String xpathToTextParagraph, int referenceValue)
    {
        assertThat(editorTextPage()
                .getElementsText(xpathToTextParagraph + FOOTNOTE_REFERENCE_SUPER))
                .as("The reference value should be reordered and equal to " + referenceValue)
                .isEqualTo(String.valueOf(referenceValue));
    }

    private void findFootnoteReferenceViaContextMenuVerifyThatElementIsHighlighted(String xpathToStartElement,
                                                                                   String xpathToSearchedElement,
                                                                                   String elementName)
    {
        editorTextPage().click(xpathToStartElement);
        editorTextPage().rightClick(xpathToStartElement);
        editorTextPage().breakOutOfFrame();
        editorTextContextMenu().openContextMenu(EditorTextContextMenuElements.FIND_FOOTNOTES_REFERENCES);
        editorTextPage().switchToEditorTextArea();
        assertThat(
                editorTextPage()
                        .doesElementExist(xpathToSearchedElement + CLASS_HIGHLIGHTED_POSTFIX)
        )
                .as(format("The [%s] should be highlighted", elementName))
                .isTrue();
    }

    private void insertFootnoteWithReferenceValueIntoTextParagraph(String xpathToParagraph, int referenceValue)
    {
        editorTextPage().click(xpathToParagraph);
        editorPage().openInsertFootnoteDialog();
        insertFootnotePage().setFootnoteReference(String.valueOf(referenceValue));
        insertFootnotePage().clickSaveFootnote();
        editorPage().switchDirectlyToTextFrame();
    }

    private void scrollToSpecifiedChunk(int chunkNumber)
    {
        editorPage().switchToEditor();
        editorPage().scrollToChunk(chunkNumber);
        editorPage().switchDirectlyToTextFrame();
    }
}
