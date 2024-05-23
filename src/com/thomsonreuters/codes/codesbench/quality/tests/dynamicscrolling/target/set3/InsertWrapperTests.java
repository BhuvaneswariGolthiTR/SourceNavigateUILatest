package com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.target.set3;

import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import static com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import static com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;

public class InsertWrapperTests extends TestService
{

    /* Insert Subsection Wrapper
     * 1. Open document
     * 2. Select and right click a text paragraph
     * 3. Go to Insert Wrapper -> Subsection
     * VERIFY: A subsection parent is inserted above the text paragraph
     */
    /**
     * STORY/BUG - migration <br>
     * SUMMARY -  Insert Subsection Wrapper<br>
     * USER - LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void textParagraphSubsectionWrapperTargetLegalTest()
    {
        String uuid = "I09CFC99014F011DA8AC5CD53670E6B4E";
        int chunkNumber = 1;

        // open DS editor
        homePage().goToHomePage();
        loginPage().logIn();
        hierarchyNavigatePage().goToHierarchyPage(ContentSets.IOWA_DEVELOPMENT.getCode());
        hierarchySearchPage().searchNodeUuidHandlingIncorrectStartDate(uuid);
        siblingMetadataPage().selectedSiblingMetadataEditContent();

        // make a change to toggle warning
        editorToolbarPage().clickConfigureEditorSessionPreferences();
        editorPreferencesPage().clickShowSubsectionLabelDesignatorsYesRadioButton();
        editorPreferencesPage().clickSaveButton();
        editorPage().switchToEditor();

        //scroll to chunk
        editorPage().scrollToChunk(2);

        // count subsection number in text and in tree
        editorPage().switchToEditor();
        editorTreePage().rightClickTreeRootForTargetNodes();
        editorTreeContextMenu().expandSixLevels();
        int subsectionNumberInTreeBeforeGeneration = editorTreePage().countSubsectionNumberInTree();
        editorPage().switchToEditor();
        editorPage().scrollToChunk(chunkNumber);
        int subsectionNumberBeforeGeneration = editorTextPage().countSubsectionInTheChunk(chunkNumber);

        //get text from Text Paragraph
        String paraText = editorTextPage().getFirstTextParagraphTextInTheChunk(chunkNumber);

        // insert Wrapper for Text Paragraph
        editorTextPage().rightClickFirstTextParagraphInTheChunk(chunkNumber);
        editorTextPage().breakOutOfFrame();
        editorTextContextMenuPage().insertWrapperSubsection();

        //get text from highlighted Text Paragraph
        editorPage().switchToEditorTextFrame();
        String highlightedParaText = editorTextPage().getHighlightedParaText();

        // count subsection number in text and in tree
        int subsectionNumberAfterGeneration = editorTextPage().countSubsectionInTheChunk(chunkNumber);
        editorPage().switchToEditor();
        int subsectionNumberInTreeAfterGeneration = editorTreePage().countSubsectionNumberInTree();

        //close DS editor
        editorPage().closeDocumentAndDiscardChanges();

        Assertions.assertAll
                (
                        () -> Assertions.assertEquals(subsectionNumberBeforeGeneration, subsectionNumberAfterGeneration-1, "New subsection WAS NOT generated in text"),
                        () -> Assertions.assertEquals(subsectionNumberInTreeBeforeGeneration, subsectionNumberInTreeAfterGeneration-1, "New subsection WAS NOT generated in tree"),
                        () -> Assertions.assertEquals(paraText, highlightedParaText, "Subsection element should wrap selected text paragraph")
                );

    }


    /* Insert Annotations Wrapper
     * 1. open document
     * 2. Select and right click the Cornerpiece
     * 3. Go to Insert Wrapper -> Annotations
     * VERIFY: An Annotations element. Check the document itself and the tree
     */
    /**
     * STORY/BUG - migration <br>
     * SUMMARY -  Insert Annotations Wrapper<br>
     * USER - LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void cornerpieceAnnotationsWrapperTargetLegalTest()
    {
        String uuid = "I09CFC99014F011DA8AC5CD53670E6B4E";
        int chunkNumber = 1;

        // open DS editor
        homePage().goToHomePage();
        loginPage().logIn();
        hierarchyNavigatePage().goToHierarchyPage(ContentSets.IOWA_DEVELOPMENT.getCode());
        hierarchySearchPage().searchNodeUuidHandlingIncorrectStartDate(uuid);
        siblingMetadataPage().selectedSiblingMetadataEditContent();

        //scroll to chunk
        editorPage().switchToEditor();
        editorPage().scrollToChunk(2);

        // count subsection number in text and in tree
        editorPage().switchToEditor();
        editorPage().waitForPageLoaded();
        editorTreePage().expandEditorsTreeAndClickNode("annotations");
        editorTreePage().sendKeys(Keys.DELETE);
        editorTreePage().rightClickTreeRootForTargetNodes();
        editorTreeContextMenu().expandSixLevels();
        int annotationNumberInTreeBeforeGeneration = editorTreePage().countAnnotationsNumberInTree();
        editorPage().switchToEditor();
        editorPage().scrollToChunk(chunkNumber);
        int annotationNumberBeforeGeneration = editorTextPage().countAnnotationsNumber();

        //get text from Cornerpiece
        String cornerpieceText = editorTextPage().getFirstCornerpieceTextInTheChunk(chunkNumber);

        // insert Wrapper for Cornerpiece
        editorTextPage().rightClickFirstCornerpieceInTheChunk(chunkNumber);
        editorTextPage().breakOutOfFrame();
        editorTextContextMenuPage().insertWrapperAnnotations();

        // count Cornerpiece number in text and in tree
        editorPage().switchToEditorTextFrame();
        String highlightedCornerpieceText = editorTextPage().getHighlightedCornerpieceText();
        editorTextPage().click(EditorTextPageElements.ANNOTATIONS_SPAN);
        int annotationNumberAfterGeneration = editorTextPage().countAnnotationsNumber();
        editorPage().switchToEditor();
        int annotationNumberInTreeAfterGeneration = editorTreePage().countAnnotationsNumberInTree();

        //close DS editor
        editorPage().closeDocumentAndDiscardChanges();

        Assertions.assertAll
                (
                        () -> Assertions.assertEquals(annotationNumberBeforeGeneration, annotationNumberAfterGeneration-1, "New annotation WAS NOT generated in text"),
                        () -> Assertions.assertEquals(annotationNumberInTreeBeforeGeneration, annotationNumberInTreeAfterGeneration-1, "New annotation WAS NOT generated in tree"),
                        () -> Assertions.assertEquals(cornerpieceText, highlightedCornerpieceText, "Annotations element should wrap selected cornerpiece")

                );

    }

    /* Insert Text Wrapper
     * 1. open document
     * 2. Select and right click the Cornerpiece
     * 3. Go to Insert Wrapper -> Text (body)
     * VERIFY: A Text element is inserted. Check the document itself and the tree
     */
    /**
     * STORY/BUG - migration <br>
     * SUMMARY -  Insert Text (body) Wrapper<br>
     * USER - LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void cornerpieceTextBodyWrapperTargetLegalTest()
    {
        String uuid = "I09CFC99014F011DA8AC5CD53670E6B4E";
        int chunkNumber = 1;

        // open DS editor
        homePage().goToHomePage();
        loginPage().logIn();
        hierarchyNavigatePage().goToHierarchyPage(ContentSets.IOWA_DEVELOPMENT.getCode());
        hierarchySearchPage().searchNodeUuidHandlingIncorrectStartDate(uuid);
        siblingMetadataPage().selectedSiblingMetadataEditContent();

        //scroll to chunk
        editorPage().scrollToChunk(2);

        // count subsection number in text and in tree
        editorPage().switchToEditor();
        editorTreePage().rightClickTreeRootForTargetNodes();
        editorTreeContextMenu().expandSixLevels();
        int bodyNumberInTreeBeforeGeneration = editorTreePage().countBodyNumberInTree();
        editorPage().switchToEditor();
        editorPage().scrollToChunk(chunkNumber);
        editorTextPage().clickCornerpiece();
        int textNumberBeforeGeneration = editorTextPage().countTextBody();

        //get text from Cornerpiece
        String cornerpieceText = editorTextPage().getFirstCornerpieceTextInTheChunk(chunkNumber);

        // insert Wrapper for Cornerpiece
        editorTextPage().rightClickFirstCornerpieceInTheChunk(chunkNumber);
        editorTextPage().breakOutOfFrame();
        editorTextContextMenuPage().insertWrapperTextBody();

        // count Cornerpiece number in text and in tree
        editorPage().switchToEditorTextFrame();
        int textNumberAfterGeneration = editorTextPage().countTextBody();
        String highlightedCornerpieceText = editorTextPage().getHighlightedCornerpieceText();
        editorPage().switchToEditor();
        int bodyNumberInTreeAfterGeneration = editorTreePage().countBodyNumberInTree();

        //close DS editor
        editorPage().closeDocumentAndDiscardChanges();

        Assertions.assertAll
                (
                        () -> Assertions.assertEquals(textNumberBeforeGeneration, textNumberAfterGeneration-1, "New text (body) WAS NOT generated in text"),
                        () -> Assertions.assertEquals(bodyNumberInTreeBeforeGeneration, bodyNumberInTreeAfterGeneration-1, "New text (body) WAS NOT generated in tree"),
                        () -> Assertions.assertEquals(cornerpieceText, highlightedCornerpieceText, "Annotations element should wrap selected cornerpiece")

                );
    }

}
