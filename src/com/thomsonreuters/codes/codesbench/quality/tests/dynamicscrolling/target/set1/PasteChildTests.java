package com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.target.set1;

import com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.base.AbstractPasteChildTests;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.BODY_PARA;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.SPAN_BY_TEXT;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.TEXT_PARAGRAPH_SPAN;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTreePageElements.TREE_NODE_WITH_TEXT_LINK;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTreePageElements.treeElements.PARA_SMP;
import static com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets.IOWA_DEVELOPMENT;
import static org.assertj.core.api.Assertions.assertThat;

public class PasteChildTests extends AbstractPasteChildTests
{
    private static final String UUID = "ID8A0AED0157F11DA8AC5CD53670E6B4E";

    @BeforeEach
    public void openTargetNodeInDsEditor()
    {
        hierarchyNavigatePage().goToHierarchyPage(IOWA_DEVELOPMENT.getCode());
        hierarchySearchPage().searchNodeUuid(UUID);
        siblingMetadataPage().selectedSiblingMetadataEditContent();
        editorPage().switchToEditorTextFrame();
    }

    /**
     * STORY/BUG - HALCYONST-4678 <br>
     * SUMMARY - Paste child (Target) <br>
     * USER - LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void pasteChildTargetLegalTest()
    {
        int childParagraphsCountBeforePastingInEditor = getChildParagraphsCountFromEditor();
        editorTextPage().click(TEXT_PARAGRAPH_SPAN);
        editorPage().switchToEditor();
        int childParagraphsCountBeforePastingInTheTree = getChildParagraphsCountFromTree();

        //Copy paragraph
        copy(TEXT_PARAGRAPH_SPAN);
        //Paste paragraph as child
        pasteAsChild(String.format(SPAN_BY_TEXT, "Text"));

        //Assert that the editor tree is updated
        assertThat(getChildParagraphsCountFromTree())
                .as("Editor tree should be updated and the pasted paragraph should be added in the tree")
                .isEqualTo(childParagraphsCountBeforePastingInTheTree + 1);
        //Assert that paragraph is pasted as child
        editorPage().switchDirectlyToTextFrame();
        assertThat(getChildParagraphsCountFromEditor())
                .as("The paragraph should be pasted as child")
                .isEqualTo(childParagraphsCountBeforePastingInEditor + 1);
        assertThatThereAreNoErrorsInTheEditorMessagePane();
    }

//  ------------- Assistive methods: -------------

    private int getChildParagraphsCountFromEditor()
    {
        return countElements(BODY_PARA);
    }

    private int getChildParagraphsCountFromTree()
    {
        return countElements(String.format(TREE_NODE_WITH_TEXT_LINK, PARA_SMP.getNodeName()));
    }
}
