package com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.sourcetarget;

import com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.base.AbstractSourceTargetTests;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.robot.RobotUtils;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.util.List;

import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.CLASS_HIGHLIGHTED_POSTFIX;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.CORNERPIECE;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.CORNERPIECE_SPAN;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.SPAN_BY_TEXT;
import static com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets.CALIFORNIA_DEVELOPMENT;
import static java.lang.String.format;

public class CopyMultipleTextParagraphsFromSourceToTargetTests extends AbstractSourceTargetTests
{
    private static final String CALIFORNIA_SOURCE_RENDITION_UUID = "I03B31BF1310511EC85F892513A34108A";
    private static final String CALIFORNIA_TARGET_NODE_UUID = "I58D07E003BDE11ECA56181CB7D229597";
    private static final String EXPECTED_ALERT_MSG = "Warning: The DTD for Target documents does not list para as a" +
            " child of grade.content.section after a cornerpiece before a placeholder. Would you like to continue?";
    private static final String GRADE_HEADING_BLOCK = format(SPAN_BY_TEXT, "Grade Heading Block");
    private static final int ELEMENTS_TO_HIGHLIGHT_NUMBER = 13;

    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void copyMultipleTextParagraphsFromSourceToTargetTest()
    {
        softAssertions = new SoftAssertions();

        //1. Open California Target Doc Node in DS editor
        openTargetNodeWithSpecifiedContentSetAndUuidInDsEditor(CALIFORNIA_DEVELOPMENT, CALIFORNIA_TARGET_NODE_UUID);

        //2. Open California PREP Rendition in the DS Editor
        hierarchyNavigatePage().switchToHierarchyEditPage();
        openSourceRenditionInDsEditor(CALIFORNIA_SOURCE_RENDITION_UUID);

        //3. In the PREP document, expand the source.body and select the CLPP para and then
        // select F7 13 times to block select the next 13 SMPF paras and then copy  using Ctrl-C
        switchToSecondDocument();
        editorPage().switchToEditor();
        editorTreePage().expandEditorsTreeAndClickNode("source.body", "para \"CLPP\"");
        switchToSecondDocument();
        editorTextPage().highlightFollowingSiblingsUsingRobotF7(ELEMENTS_TO_HIGHLIGHT_NUMBER);
        editorTextPage().waitForElementsCountToBe(HIGHLIGHTED_PARATEXT, ELEMENTS_TO_HIGHLIGHT_NUMBER + 1);
        List<String> copiedTextParagraphs = editorTextPage().getElementsTextList(HIGHLIGHTED_PARATEXT);
        RobotUtils.ctrlCUsingRobot();


        //4. In the target document, select the Grade Heading Block and select Insert Text (Sibling) - Grade Content Section
        // After it is inserted select the Cornerpiece English Label and select Paste using Ctrl-V
        switchToFirstDocument();
        editorTextPage().click(GRADE_HEADING_BLOCK);
        openContextMenuOnElement(GRADE_HEADING_BLOCK);
        editorTextContextMenu().insertTextSiblingGradeContentSection();
        editorPage().switchDirectlyToTextFrame();
        editorTextPage().click(CORNERPIECE_SPAN);
        editorTextPage().waitForElement(CORNERPIECE + CLASS_HIGHLIGHTED_POSTFIX);
        RobotUtils.ctrlVUsingRobot();

        //5. Click 'OK' in the appeared dialog box with message:
        // 'Warning: The DTD for Target Document does not list <…> Would you like to continue?'
        editorTextPage().waitForAlert();
        softAssertions.assertThat(editorTextPage().getAlertText())
                .as("An alert with message [%s] should be appeared", EXPECTED_ALERT_MSG)
                .isEqualTo(EXPECTED_ALERT_MSG);
        editorTextPage().acceptAlert();

        //Assert that the copied text paragraphs from the Source document are pasted to the Target document
        softAssertions.assertThat(editorTextPage().getElementsTextList(HIGHLIGHTED_PARATEXT))
                .as("Copied text paragraphs from source doc should be pasted to the target doc")
                .isEqualTo(copiedTextParagraphs);

        //Assert that there are no paste/runtime errors appearing in the message pane
        editorPage().switchToEditor();
        softAssertThatThereAreNoErrorsInBothMessagePanes();

        softAssertions.assertAll();
    }
}
