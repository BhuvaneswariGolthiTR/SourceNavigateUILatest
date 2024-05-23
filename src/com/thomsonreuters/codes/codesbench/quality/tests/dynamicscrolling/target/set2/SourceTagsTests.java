package com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.target.set2;

import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.popups.ChangeSourceTagPageElements;
import com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.base.AbstractTagsTests;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.SOURCE_TAG;
import static org.assertj.core.api.Assertions.assertThat;

public class SourceTagsTests extends AbstractTagsTests
{
    private static final String HIGHLIGHTED_SOURCE_TAG = HIGHLIGHTED_PARA + SOURCE_TAG;
    private static final String SOURCE_TAG_VALUE = "17";

    @BeforeEach
    public void openTargetNodeInDsEditor()
    {
        openTargetNodeInDsEditorAndSwitchToEditorTextFrame();
    }

    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void changingSourceTagsAcrossChunksTargetLegalTest()
    {
        //Select multiple Text Paragraphs on the chunk boundary
        selectSubsectionElementsUnder(10);

        //Open Change Source tag window, select Source tag and click OK
        editorTextPage().rightClick(HIGHLIGHTED_SOURCE_TAG);
        editorTextPage().breakOutOfFrame();
        changeSourceTagPage().switchToChangeSourceTagWindow();
        changeSourceTagPage().selectDropdownOptionUsingJavascript(ChangeSourceTagPageElements.SOURCE_TAG_DROPDOWN_ID, SOURCE_TAG_VALUE);
        changeSourceTagPage().clickOk();
        editorPage().switchDirectlyToTextFrame();

        //Assert that the count of changed Source tags is the same as the count of selected paragraphs
        int selectedParagraphs = countHighlightedParagraphs();
        List<String> changedSourceTags = editorTextPage().getElementsTextList(HIGHLIGHTED_SOURCE_TAG);
        assertThat(changedSourceTags.size())
                .as("The count of changed Source tags should be the same as the count of selected paragraphs")
                .isEqualTo(selectedParagraphs);

        //Assert that all changed Source tags have specified Source tag value
        assertThat(changedSourceTags)
                .as(String.format("All selected Source tags should have [%s] value", SOURCE_TAG_VALUE))
                .allMatch(sourceTag -> sourceTag.equals(SOURCE_TAG_VALUE));

        softAssertThatAllChangesMadeSuccessfully();
    }
}
