package com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.base;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.util.List;

import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.*;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.mnemonics.SMP;
import static org.assertj.core.api.Assertions.assertThat;

public abstract class AbstractCopyAndPasteTests extends TestService
{
    private static final String ITALIC_NOTE = "[@display-name='Italic Note']";
    protected static final String PARA_XPATH = LOADED_CHUNK + SUBSECTION_UNDER_NUMBER + PARA;
    protected static final String FOLLOWING_SIBLING_PARA = "/.." + String.format(FOLLOWING_SIBLING, "para");
    protected static final int SECOND_CHUNK = 2;
    protected SoftAssertions softAssertions;

    @BeforeEach
    protected void login()
    {
        homePage().goToHomePage();
        loginPage().logIn();
    }

    @AfterEach
    protected void closeDocument()
    {
        editorPage().closeDocumentAndDiscardChanges();
    }

//  ------------- Assistive methods: -------------

    protected void openContextMenuOnElement(String xPath)
    {
        editorTextPage().scrollToView(xPath);
        editorTextPage().rightClick(xPath);
        editorTextPage().breakOutOfFrame();
    }

    protected void copyTextParagraphAndScrollToChunk(String paraXpath, int chunkNumber)
    {
        openContextMenuOnElement(paraXpath);
        editorTextContextMenu().copyCtrlC();
        editorPage().scrollToChunk(chunkNumber);
    }

    protected void pasteAsTextParagraphSmp(String paraXpath)
    {
        openContextMenuOnElement(paraXpath);
        editorTextContextMenu().pasteAsTextParagraphSmp();
        editorPage().switchToEditorTextFrame();
    }

    protected int countTextParagraphsInLoadedChunkWithNumber(int chunkNumber)
    {
        return editorTextPage().countElements(String.format(LOADED_CHUNK, chunkNumber) + FIRST_TEXT_PARAGRAPH);
    }

    protected void highlightTextParagraphsAndCopyThem(String textParaXpath, int countOfSiblingsToHighlight)
    {
        editorTextPage().click(textParaXpath);
        editorTreePage().highlightFollowingSiblings(countOfSiblingsToHighlight);
        editorTextPage().rightClick(textParaXpath);
        editorTextPage().breakOutOfFrame();
        editorTextContextMenu().copyCtrlC();
    }

    protected void assertThatSelectedContentIsCopied()
    {
        assertThat(editorMessagePage().checkMessagePaneForText("Copying to clipboard initiated...completed"))
                .as("The selected content should be copied")
                .isTrue();
    }

    protected void assertThatCopiedContentIsStillSelectedAfterCopying(int countOfSiblingsToHighlight)
    {
        editorPage().switchToEditorTextFrame();
        assertThat(editorTextPage().countElements(PARA + CLASS_HIGHLIGHTED_POSTFIX))
                .as("The selected content should be still selected after copying")
                .isEqualTo(countOfSiblingsToHighlight + 1);
    }

    protected void paste(String textParaXpath)
    {
        openContextMenuOnElement(textParaXpath);
        editorTextContextMenu().pasteSiblingCtrlV();
    }

    protected void assertThatWarningMessageIsAppearedInMessagePane()
    {
        assertThat(editorMessagePage().checkMessagePaneForText("Unable to determine insert location from block selection"))
                .as("The warning message should appear after pasting")
                .isTrue();
    }

    protected void pasteAndSwitchToEditorTextFrame(String textParaXpath)
    {
        editorTextPage().click(textParaXpath);
        paste(textParaXpath);
        editorPage().switchToEditorTextFrame();
    }

    protected void assertThatPastedContentIsHighlighted(int before, int after, int countOfSiblingsToHighlight)
    {
        assertThat(after)
                .as("The copied earlier content should be pasted")
                .isEqualTo(before + countOfSiblingsToHighlight + 1);
        assertThat(editorTextPage().countElements(PARA + CLASS_HIGHLIGHTED_POSTFIX))
                .as("The pasted content should be selected after pasting")
                .isEqualTo(after - before);
    }

    protected void softAssertThatTextParagraphIsPasted(int before, int after)
    {
        softAssertions.assertThat(after)
                .as("Text Paragraph should be pasted")
                .isEqualTo(before + 1);
    }

    protected void softAssertThatTextParagraphIsPastedAsSibling(String pastedParaXpath)
    {
        softAssertions.assertThat(editorTextPage().doesElementExist(pastedParaXpath))
                .as("Text Paragraph should be pasted as sibling")
                .isTrue();
    }

    protected void softAssertThatMnemonicTagIsAppeared(String pastedParaXpath)
    {
        softAssertions.assertThat(editorTextPage().getElementsText(pastedParaXpath + MD_MNEM_MNEMONIC))
                .as("Mnemonic tag should be appeared")
                .isEqualTo(SMP.name());
    }

    protected void softAssertThatModifiedByTagIsAdded()
    {
        softAssertions.assertThat(editorTextPage().doesElementExist(editorTextPage().getModifiedByXpath(user())))
                .as("Modified by tag should be added")
                .isTrue();
    }

    protected void softAssertThatTextParagraphsArePasted(int before, int after)
    {
        softAssertions.assertThat(after)
                .as("Text Paragraphs should be pasted")
                .isEqualTo(before + 2);
    }

    protected void softAssertThatTextParagraphsArePastedAsSiblings(String pastedParaXpath)
    {
        softAssertions.assertThat(editorTextPage().countElements(pastedParaXpath))
                .as("Text Paragraphs should be pasted as siblings")
                .isEqualTo(2);
    }

    protected void softAssertThatMnemonicTagsAreAppeared(String pastedParaXpath)
    {
        softAssertions.assertThat(editorTextPage().getElementsTextList(pastedParaXpath + MD_MNEM_MNEMONIC))
                .as("Mnemonic tags should be appeared")
                .allMatch(mnemonicTag -> mnemonicTag.equals(SMP.name()));
    }

    protected void softAssertThatModifiedTagsAreAdded()
    {
        softAssertions.assertThat(editorTextPage().countElements(editorTextPage().getModifiedByXpath(user())))
                .as("Modified by tags should be added")
                .isEqualTo(2);
    }

    protected void pasteAsFeatureTextEditorialNote(String paraXpath)
    {
        openContextMenuOnElement(paraXpath);
        editorTextContextMenu().pasteAsFeatureTextEditorialNote();
        editorPage().switchToEditorTextFrame();
    }

    protected void softAssertThatTextEditorialNoteIsInsertedAsAChildTo(String baseXpath)
    {
        softAssertions.assertThat(editorTextPage().doesElementExist(baseXpath + ED_NOTE))
                .as(String.format("Text Editorial Note should be inserted as a child to the %s", baseXpath))
                .isTrue();
    }

    protected void softAssertThatMiscellaneousTextNoteHeadingIsInsertedAsAChildToTextEditorialNote(String baseXpath)
    {
        softAssertions.assertThat(editorTextPage()
                .doesElementExist(baseXpath + ED_NOTE + CODES_HEAD + "[@display-name='Miscellaneous Text Note Heading']"))
                .as("Miscellaneous Text Note Heading should be inserted as a child to the Text Editorial Note")
                .isTrue();
    }

    protected void softAssertThatItalicNoteIsInsertedAsAChildToTextEditorialNote(String baseXpath)
    {
        softAssertions.assertThat(editorTextPage()
                .doesElementExist(baseXpath + ED_NOTE + PARA + ITALIC_NOTE))
                .as("Italic Note should be inserted as a child to the Text Editorial Note")
                .isTrue();
    }

    protected void softAssertThatTheNumberOfItalicNotesIsEqualToTheNumberOfCopiedParagraphs(String baseXpath)
    {
        softAssertions.assertThat(editorTextPage().countElements(baseXpath + ED_NOTE + PARA + ITALIC_NOTE))
                .as("The number of Italic Notes should be equal to the number of the copied paragraphs")
                .isEqualTo(2);
    }

    protected void softAssertThatItalicNoteHasTheSameTextContentAsCopiedParagraph(String baseXpath,
                                                                                  String copiedParagraphTextContent)
    {
        softAssertions.assertThat(editorTextPage()
                .getElementsText(baseXpath + ED_NOTE + PARA + ITALIC_NOTE + PARATEXT))
                .as("Italic Note should have the same text content as the copied paragraph")
                .isEqualTo(copiedParagraphTextContent);
    }

    protected void softAssertThatItalicNotesHaveTheSameTextContentAsCopiedParagraphs(
            String baseXpath,
            List<String> copiedParagraphsTextContent)
    {
        softAssertions.assertThat(editorTextPage()
                .getElementsTextList(baseXpath + ED_NOTE + PARA + ITALIC_NOTE + PARATEXT))
                .as("Italic Notes should have the same text content as the copied paragraphs")
                .isEqualTo(copiedParagraphsTextContent);
    }

    protected  void softAssertThatThereIsNoWarningMessageInMessagePane()
    {
        softAssertions.assertThat(editorMessagePage().checkForWarningInMessagePane())
                .as("There should be no warnings in the editor message pane")
                .isFalse();
    }

    protected  void softAssertThatThereIsNoErrorMessageInMessagePane()
    {
        softAssertions.assertThat(editorMessagePage().checkForErrorInMessagePane())
                .as("There should be no errors in the editor message pane")
                .isFalse();
    }

    protected String getParagraphsContent(String paraXpath)
    {
        return editorTextPage().getElementsText(paraXpath + String.format(FOLLOWING_SIBLING, "paratext"));
    }

    protected List<String> getSelectedParagraphsContentList()
    {
        return editorTextPage().getElementsTextList(HIGHLIGHTED_PARA + PARATEXT);
    }
}
