package com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.source.set2;

import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements;
import com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.base.AbstractMnemonicsTests;
import org.junit.jupiter.api.BeforeEach;

import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.SUBSECTION_SPAN_UNDER_NUMBER;

public class MnemonicsTests extends AbstractMnemonicsTests
{
    private static final String SOURCE_RENDITION_UUID = "I0CFB7490179811E2A2CCBB59A0353111";

    @BeforeEach
    public void beforeEachTestMethod()
    {
        //Open source rendition in DS editor
        sourcePage().goToSourcePageWithRenditionUuids(SOURCE_RENDITION_UUID);
        sourceNavigateGridPage().firstRenditionEditContent();
        //Select multiple Text Paragraphs on the chunk boundary
        editorPage().scrollToChunk(3);
        //selectSubsectionElementsUnder(7);
        editorTextPage().click(String.format(SUBSECTION_SPAN_UNDER_NUMBER, 7));
        editorPage().click(EditorTextPageElements.TEXT_PARAGRAPH_SPAN);
    }
}
