package com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.target.set3;

import com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.base.AbstractHintTagTests;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.TargetDataMockingNew;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.datapod.HierarchyDatapodObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.SECTION_NAMELINE;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.SPAN;
import static com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets.IOWA_DEVELOPMENT;

public class HintTagTests extends AbstractHintTagTests
{
    private HierarchyDatapodObject datapodObject;

    /**
     * STORY/BUG - HALCYONST-13708 <br>
     * SUMMARY - Information placed in Hint tags save during check-in (Target) <br>
     * USER - LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void informationPlacedInHintTagsSaveDuringCheckInTargetLegalTest()
    {
        datapodObject = TargetDataMockingNew.Iowa.Small.insert();
        hierarchyNavigatePage().goToHierarchyPage(IOWA_DEVELOPMENT.getCode());
        hierarchySearchPage().searchNodeUuid(datapodObject.getSections().get(0).getNodeUUID());
        siblingMetadataPage().selectedSiblingMetadataEditContent();

        String text = String.format(TEXT, System.currentTimeMillis());

        //Insert Section Nameline
        editorPage().switchDirectlyToTextFrame();
        openContextMenuOnElement(SECTION_NAMELINE + SPAN);
        editorTextContextMenu().insertHeadingNamelineJurisdictionalNamelinesSnl();
        editorPage().switchDirectlyToTextFrame();

        //Delete previous Section Nameline
        deleteElement(SECTION_NAMELINE);
        editorPage().switchDirectlyToTextFrame();

        //Replace hints with text
        insertTextToElementHint(String.valueOf(4), SECTION_NAMELINE);
        insertTextToElementHint(text, SECTION_NAMELINE);

        //Click Pubtag refresh toolbar button
        editorPage().switchToEditor();
        editorToolbarPage().clickPubtagRefreshButton();
        editorPage().switchDirectlyToTextFrame();

        //Check-in target node and reopen it
        editorPage().closeAndCheckInChanges();
        editorPage().switchToEditorWindow();
        editorPage().closeCurrentWindowIgnoreDialogue();
        hierarchyNavigatePage().switchToHierarchyEditPage();
        siblingMetadataPage().selectedSiblingMetadataEditContent();
        editorPage().switchDirectlyToTextFrame();

        //Assert that information placed in hint tags is saved after check-in
        assertThatInformationPlacedInHintTagsIsSaved(SECTION_NAMELINE, text);

        editorPage().closeEditorWithDiscardingChangesIfAppeared();
    }

    @AfterEach
    public void cleanUp()
    {
        if(datapodObject != null)
        {
            datapodObject.delete();
        }
    }
}
