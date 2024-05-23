package com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.source.set1;

import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import static com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;

public class ConfigureEditorSessionPreferencesTests extends TestService
{

    /* Display Label Designator Metadata Yes and No
     * NOTE: There is an existing bug that is being worked on
     * but the test can still be implemented and it will just fail until the bug is fixed
     * 1. Open document that contains subsections
     * 2. Click the Configure Editor Session Preferences button
     * VERIFY: Editor preferences window appears
     * 3. Select Yes for Label Designator Metadata Display for Subsections
     * 4. Click Save
     * VERIFY: Editor preferences window no longer appears
     * VERIFY: Label designator metadata (the value markup you see after subsections) displays in both the tree and editor content area
     * 5. Click the Configure Editor Session Preferences button
     * VERIFY: Editor preferences window appears
     * 6. Select No for Label Designator Metadata Display for Subsections
     * 4. Click Save
     * VERIFY: Editor preferences window no longer appears
     * VERIFY: Label designator metadata (the value markup you see after subsections) does not display in both the tree and content area
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void displayLabelDesignatorMetadataTest()
    {
        String uuid = "I9E7B6241F51611E5AF0B8B9371769F64";
        int chunkNumber = 3;

        // open DS editor
        sourcePage().goToSourcePageWithRenditionUuids(uuid);
        loginPage().logIn();
        sourceNavigateGridPage().firstRenditionEditContent();

        //chunk 3 contains subsections necessary for this test
        editorPage().scrollToChunk(chunkNumber);

        //set label designator to yes
        editorPage().breakOutOfFrame();
        editorToolbarPage().clickConfigureEditorSessionPreferences();
        editorPreferencesPage().clickShowSubsectionLabelDesignatorsYesRadioButton();
        editorPreferencesPage().clickSaveButton();

        //verify subsection labels appear
        editorPage().switchDirectlyToTextFrame();
        boolean subsectionLabelsDisplayed = editorTextPage().isElementDisplayed(EditorTextPageElements.SUBSECTION_LABEL_DESIGNATOR_RIGHT_ARROW)
                && editorTextPage().isElementDisplayed(EditorTextPageElements.SUBSECTION_LABEL_DESIGNATOR_LEFT_ARROW)
                && !editorTextPage().getElement(EditorTextPageElements.SUBSECTION + EditorTextPageElements.METADATA_LABEL_DESIGNATOR).getText().isEmpty();

        //set label designator to no
        editorPage().breakOutOfFrame();
        editorToolbarPage().clickConfigureEditorSessionPreferences();
        editorPreferencesPage().clickShowSubsectionLabelDesignatorsNoRadioButton();
        editorPreferencesPage().clickSaveButton();

        //verify subsection labels no longer appear
        editorPage().switchDirectlyToTextFrame();
        boolean subsectionLabelsNotDisplayed = editorTextPage().isElementDisplayed(EditorTextPageElements.SUBSECTION_LABEL_DESIGNATOR_RIGHT_ARROW)
                && editorTextPage().isElementDisplayed(EditorTextPageElements.SUBSECTION_LABEL_DESIGNATOR_LEFT_ARROW)
                && editorTextPage().isElementDisplayed(EditorTextPageElements.SUBSECTION + EditorTextPageElements.METADATA_LABEL_DESIGNATOR );

        // close editor
        editorPage().closeDocumentWithNoChanges();

        Assertions.assertAll
                (
                        () -> Assertions.assertTrue(subsectionLabelsDisplayed, "Subsection labels were displayed correctly"),
                        () -> Assertions.assertFalse(subsectionLabelsNotDisplayed, "Subsection labels were not displayed correctly")
                );
    }

}
