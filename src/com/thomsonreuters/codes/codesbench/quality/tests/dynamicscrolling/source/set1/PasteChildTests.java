package com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.source.set1;

import com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.base.AbstractPasteChildTests;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.SOURCE_BODY_SOURCE_SECTION;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.SOURCE_BODY_SPAN;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.SOURCE_SECTION_SPAN;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTreePageElements.TREE_NODE_WITH_TEXT_LINK;
import static org.assertj.core.api.Assertions.assertThat;

public class PasteChildTests extends AbstractPasteChildTests
{
    private static final String UUID = "IF70889A0242211E891AAB40B71228301";

    @BeforeEach
    public void openSourceRenditionInDsEditor()
    {
        sourcePage().goToSourcePageWithRenditionUuids(UUID);
        sourceNavigateGridPage().firstRenditionEditContent();
        editorPage().switchToEditorTextFrame();
    }

    /**
     * STORY/BUG - HALCYONST-4678 <br>
     * SUMMARY - Paste child (Source) <br>
     * USER - LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void pasteChildSourceLegalTest()
    {
        editorTextPage().click(SOURCE_SECTION_SPAN);
        int childSourceSectionsCountBeforePastingInEditor = getChildSourceSectionsCountFromEditor();
        editorPage().switchToEditor();
        int childSourceSectionsCountBeforePastingInTheTree = getChildSourceSectionsCountFromTree();

        //Copy source section
        copy(SOURCE_SECTION_SPAN);
        //Paste source section as child
        pasteAsChild(SOURCE_BODY_SPAN);

        //Assert that the editor tree is updated
        assertThat(getChildSourceSectionsCountFromTree())
                .as("Editor tree should be updated and the pasted source section should be added in the tree")
                .isEqualTo(childSourceSectionsCountBeforePastingInTheTree + 1);
        //Assert that source section is pasted as child
        //editorPage().switchToEditorAndScrollToChunk(2);
        editorPage().switchDirectlyToTextFrame();
        assertThat(getChildSourceSectionsCountFromEditor())
                .as("The source section should be pasted as child")
                .isEqualTo(childSourceSectionsCountBeforePastingInEditor + 1);
        assertThatThereAreNoErrorsInTheEditorMessagePane();
    }

//  ------------- Assistive methods: -------------

    private int getChildSourceSectionsCountFromEditor()
    {
        return countElements(SOURCE_BODY_SOURCE_SECTION);
    }

    private int getChildSourceSectionsCountFromTree()
    {
        return countElements(String.format(TREE_NODE_WITH_TEXT_LINK, "source.section \"1\""));
    }
}
