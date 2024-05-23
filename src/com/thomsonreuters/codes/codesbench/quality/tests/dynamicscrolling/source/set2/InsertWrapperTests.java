package com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.source.set2;

import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTreePageElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


public class InsertWrapperTests extends TestService
{
    /* Insert Subsection Wrapper
     * 1. Open dynamic scrolling editor on a PREP document
     * 2. Find a Text Paragraph
     * 3. Select and right click the Text Paragraph English label
     * 4. Go to Insert Wrapper -> Subsection
     * 5. Verify:
     * 5.a. The Text Paragraph is now a child of a Subsection English label
     * 5.c. The Subsection is an immediate child of the previous Text Paragraph's parent
     * 5.d. A subsection element is inserted in the tree with the same parent hierarchy seen in the content
     * 5.e. The subsection element in the tree has a para element child
     * 5.f. Theses elements are selected in both the content and tree
     *
     * We can take this test a step further and verify that we can modify the value
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void insertTextParagraphSubsectionWrapperSourceLegalTest()
    {
        String uuid = "I2B6D1EE043EA11E8B306BDCAB99DF270";
        //String uuid = "I8B1C2100BA6111E797B6DB99DFD08357"; //for dev
        int targetChunkNumber = 3;

        // open DS editor
        sourcePage().goToSourcePageWithRenditionUuids(uuid);
        loginPage().logIn();
        if (sourceNavigateGridPage().isFirstRenditionLockedByYou())
        {
            sourceNavigateGridPage().firstRenditionEditContent();
            try
            {editorPage().closeDocumentAndDiscardChanges();}
            catch (Exception e)
            {editorPage().closeDocumentWithNoChanges();}
            sourcePage().switchToSourceNavigatePage();
            sourcePage().clickRefreshButton();
            sourceNavigateGridPage().waitForGridRefresh();
        }
        sourceNavigateGridPage().firstRenditionEditContent();

        // scroll to chunk
        editorPage().scrollToChunk(targetChunkNumber);

        // find parent delta for Text Paragraph
        String targetPara = String.format(EditorTextPageElements.TEXT_PARAGRAPH_FROM_CHUNK,
                targetChunkNumber - 1, 3);
        String targetParaSpan = targetPara + EditorTextPageElements.SPAN;

        // Insert wrapper
        editorTextPage().scrollToView(targetParaSpan);
        editorTextPage().rightClick(targetParaSpan);
        editorTextPage().breakOutOfFrame();
        editorTextContextMenu().insertWrapperSubsection();
        editorPage().switchToEditorTextFrame();
        boolean subsectionWrapperAppeared = editorPage().doesElementExist(
                EditorTextPageElements.TEXT_PARAGRAPH_UNDER_HIGHLIGHTED_SUBSECTION);
        boolean subsectionParentIsTheSame = editorPage().doesElementExist(
                EditorTextPageElements.TEXT_PARAGRAPH_UNDER_HIGHLIGHTED_SUBSECTION +
                "/parent::subsection/parent::subsection");
        editorTextPage().breakOutOfFrame();
        String subsectionTreeNodeAppeared = editorTextPage().getElementsText(EditorTreePageElements.SELECTED_NODE);
        editorPage().closeDocumentAndDiscardChanges();

        Assertions.assertAll(
                () -> Assertions.assertTrue(subsectionWrapperAppeared, "Subsection wrapper didn't appear in text"),
                () -> Assertions.assertTrue(subsectionParentIsTheSame, "Wrong subsection parent"),
                () -> Assertions.assertEquals("subsection \"3 \"", subsectionTreeNodeAppeared, "Subsection didn't appear in the tree")
        );
    }
}