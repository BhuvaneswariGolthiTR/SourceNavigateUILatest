package com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.base;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.util.stream.Collectors;

import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorPageElements.EDITOR_LIST;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.SPAN_BY_TEXT;
import static org.assertj.core.api.Assertions.assertThat;

public abstract class AbstractOpenMultipleDocumentsTests extends TestService
{
    protected static final String TEXT_PARA_SPAN = String.format(SPAN_BY_TEXT, "Text Paragraph");

    @BeforeEach
    protected void login()
    {
        homePage().goToHomePage();
        loginPage().logIn();
    }

    @AfterEach
    protected void closeAllDocuments()
    {
        editorPage().closeAllOpenedDocumentsWithDiscardingChangesIfAppeared();
    }

//  ------------- Assistive methods: -------------

    protected void assertThatDocumentsAreOpenedInDsEditor(int numberOfTheOpenedDocuments)
    {
        assertThat(editorPage().getMetadataListOfTheOpenedDocuments().size())
                .as(String.format("%d documents should be opened in DS editor", numberOfTheOpenedDocuments))
                .isEqualTo(numberOfTheOpenedDocuments);
    }

    protected void assertThatEachBlueBarOfTheOpenedDocumentHasDifferentMetadata()
    {
        assertThat(editorPage().getMetadataListOfTheOpenedDocuments().stream().distinct().collect(Collectors.toList()))
                .as("Each blue bar of the opened document should have different metadata")
                .isEqualTo(editorPage().getMetadataListOfTheOpenedDocuments());
    }

    protected void assertThatListWithEditorLinksIsDisplayedInHtmlWindow(int numberOfTheOpenedDocuments)
    {
        editorPage().switchToTheOpenedDocument(1);
        assertThat(editorPage().getElements(EDITOR_LIST).size())
                .as(String.format("%d editor links should be displayed in html window", numberOfTheOpenedDocuments))
                .isEqualTo(numberOfTheOpenedDocuments);
    }

    protected void assertThatCurrentPositionIsNotBouncedAfterCopyPasteTextParagraphByIframeIndex(int iframeIndex)
    {
        //Copy Text Paragraph
        editorPage().switchToTheOpenedDocument(1);
        editorPage().switchToEditorTextFrame();
        editorPage().scrollToView(TEXT_PARA_SPAN);
        editorPage().click(TEXT_PARA_SPAN);
        editorPage().waitForPageLoaded();
        editorTextPage().rightClick(TEXT_PARA_SPAN);
        editorTextPage().waitForPageLoaded();
        editorTextPage().breakOutOfFrame();
        editorTextContextMenu().copyCtrlC();
        editorPage().switchToEditorTextFrame();
        //Get scroll bar position
        Long scrollPositionAfterCopying = editorTextPage().getScrollPosition();

        //Switch to the third document
        editorPage().switchToEditor();
        editorPage().switchToTheOpenedDocument(3);
        editorPage().switchToInnerIFrameByIndex(iframeIndex);

        //Paste previously copied Text Paragraph
        int textParagraphsBeforePasting = editorTextPage().countElements(TEXT_PARA_SPAN);
        editorTextPage().click(TEXT_PARA_SPAN);
        editorTextPage().rightClick(TEXT_PARA_SPAN);
        editorTextPage().breakOutOfFrame();
        editorTextContextMenu().pasteSiblingCtrlV();
        editorPage().switchToInnerIFrameByIndex(5);
        int textParagraphsAfterPasting = editorTextPage().countElements(TEXT_PARA_SPAN);

        //Assert that previously copied Text Paragraph is pasted
        assertThat(textParagraphsAfterPasting)
                .as("Text Paragraph should be pasted")
                .isEqualTo(textParagraphsBeforePasting + 1);

        //Return to the first document
        editorPage().switchToEditor();
        editorPage().switchToTheOpenedDocument(1);
        editorPage().switchToEditorTextFrame();
        //Get scroll bar position
        Long scrollPositionAfterSwitching = editorTextPage().getScrollPosition();

        //Assert that current position is not bounced anywhere according to scroll bar position
        assertThat(scrollPositionAfterSwitching)
                .as("Current position should not bounce anywhere")
                .isEqualTo(scrollPositionAfterCopying);
    }
}
