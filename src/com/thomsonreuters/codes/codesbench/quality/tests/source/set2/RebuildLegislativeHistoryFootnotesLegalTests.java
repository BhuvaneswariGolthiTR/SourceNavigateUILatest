package com.thomsonreuters.codes.codesbench.quality.tests.source.set2;

import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.autoIT.AutoITUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RebuildLegislativeHistoryFootnotesLegalTests extends TestService
{
    /**
     * STORY: NA <br>
     * SUMMARY: checks if a bill track with a bill text error syncs correctly.<br>
     * USER:  LEGAL<br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void rebuildLHFRenditionExistingLHFsLegalTest()
    {
        String contentSet = "USCA(Development)";
        String year = "2016";
        String renditionStatus = "APV";
        String docType = "HR";
        String docNumber = "890";

        String expectedMessage = "Rebuild completed successfully";
        String expectedError = "Unable to get property 'nodeIds' of undefined or null reference";

        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterContentSet(contentSet);
        sourceNavigateFiltersAndSortsPage().setFilterYear(year);
        sourceNavigateFiltersAndSortsPage().setFilterRenditionStatus(renditionStatus);
        sourceNavigateFiltersAndSortsPage().setFilterDocType(docType);
        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(docNumber);
        sourceNavigateFooterToolsPage().refreshTheGrid();

        sourceNavigateGridPage().rightClickFirstRendition();
        renditionContextMenu().editRendition();
        editorPage().switchDirectlyToTextFrame();
        int footnoteCountBefore = editorTextPage().getFootnoteCount();

        editorToolbarPage().clickRebuild();

        boolean checkMessageRebuild = editorMessagePage().checkMessage(expectedMessage);
        boolean checkErrorInMessagePane = editorMessagePage().checkMessage(expectedError);

        int footnoteCountAfter = editorTextPage().getFootnoteCount();
        editorPage().closeDocumentAndDiscardChanges();

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(checkMessageRebuild, "Rebuild completed successfully"),
            () -> Assertions.assertFalse(checkErrorInMessagePane, "Error message appears in messagePane"),
            () -> Assertions.assertTrue(footnoteCountBefore == footnoteCountAfter, "Footnote quantity is equal")
        );
    }

    /**
     * STORY: NA <br>
     * SUMMARY: checks if a bill track with a bill text error syncs correctly.<br>
     * USER: LEGAL <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void rebuildLHFSectionLHFsLegalTest()
    {
        String contentSet = "USCA(Development)";
        String year = "2016";
        String renditionStatus = "APV";
        String docType = "HR";
        String docNumber = "3601";

        String expectedMessage = "Rebuild completed successfully";
        String expectedError = "Unable to get property 'nodeIds' of undefined or null reference";

        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterContentSet(contentSet);
        sourceNavigateFiltersAndSortsPage().setFilterYear(year);
        sourceNavigateFiltersAndSortsPage().setFilterRenditionStatus(renditionStatus);
        sourceNavigateFiltersAndSortsPage().setFilterDocType(docType);
        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(docNumber);
        sourceNavigateFooterToolsPage().refreshTheGrid();

        sourceNavigateGridPage().clickFirstRendition();
        sourceNavigateTabsPage().goToSectionTab();
        sourceNavigateGridPage().rightClickFirstRendition();
        renditionContextMenu().editSection();
        int footnoteCountBefore = editorTextPage().getFootnoteCount();

        editorToolbarPage().clickRebuild();
        AutoITUtils.verifyAlertTextAndAccept(true, "*Warning* This Rendition has existing Section Groups and/or Delta Groups.  Running Rebuild may remove Section Group and/or Delta Group assignments for Sections and Deltas in this Editing session. *Warning*.");

        boolean checkMessageRebuild = editorMessagePage().checkMessage(expectedMessage);
        boolean checkErrorInMessagePane = editorMessagePage().checkMessage(expectedError);

        int footnoteCountAfter = editorTextPage().getFootnoteCount();
        editorPage().closeDocumentAndDiscardChanges();

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(checkMessageRebuild, "Rebuild completed successfully"),
            () -> Assertions.assertFalse(checkErrorInMessagePane, "Error message appears in messagePane"),
            () -> Assertions.assertTrue(footnoteCountBefore == footnoteCountAfter, "Footnote quantity is equal")
        );
    }

    /**
     * STORY: NA <br>
     * SUMMARY: checks if a bill track with a bill text error syncs correctly.<br>
     * USER:  LEGAL<br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void rebuildLHFSectionGroupLHFsLegalTest()
    {
        String test1 = "Test1";
        String unassigned = "Unassigned";

        String contentSet = "USCA(Development)";
        String year = "2016";
        String renditionStatus = "APV";
        String docType = "HR";
        String docNumber = "3082";

        String expectedMessage = "Rebuild completed successfully";
        String expectedError = "Unable to get property 'nodeIds' of undefined or null reference";

        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterContentSet(contentSet);
        sourceNavigateFiltersAndSortsPage().setFilterYear(year);
        sourceNavigateFiltersAndSortsPage().setFilterRenditionStatus(renditionStatus);
        sourceNavigateFiltersAndSortsPage().setFilterDocType(docType);
        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(docNumber);
        sourceNavigateFooterToolsPage().refreshTheGrid();

        sourceNavigateGridPage().rightClickFirstRendition();
        renditionContextMenu().modifySectionOrDeltaGrouping("Section");

        if(sectionGroupingGroupGridPage().doesGroupExist(test1))
        {
            sectionGroupingGroupGridPage().clickGroup(unassigned);
            sectionGroupingGroupGridPage().clickGroup(test1);
            sectionGroupingFooterPage().clickGroupingRemove();
            sectionGroupingFooterPage().clickGroupingYes();
            sectionGroupingFooterPage().clickGroupingApply();
        }
        sectionGroupingGroupGridPage().clickGroup(unassigned);
        sectionGroupingGroupGridPage().sendEnterToGroup();
        sectionGroupingGroupGridPage().addNewGroup(test1);

        sectionGroupingSectionGridPage().clickXSectionsInARange(1,2);
        sectionGroupingSectionGridPage().rightClickHighlightedSection(1);
        sectionGroupingSectionGridPage().openMoveToGroupContextMenu(test1);

        sectionGroupingFooterPage().clickGroupingApply();
        sectionGroupingGroupGridPage().closeCurrentWindowIgnoreDialogue();

        sourcePage().switchToSourceNavigatePage();
        sourceNavigateTabsPage().switchToGroupingTab("Section");

        sourceNavigateGridPage().rightClickRenditionByGroupName(test1);
        renditionContextMenu().openEditorByGrouping("Section");

        Integer footnoteCountBefore = editorTextPage().getFootnoteCount();

        editorToolbarPage().clickRebuild();

        boolean checkMessageRebuild = editorMessagePage().checkMessagePaneForText(expectedMessage);
        boolean checkNoErrorInMessagePane = !editorMessagePage().checkMessagePaneForText(expectedError);

        Integer footnoteCountAfter = editorTextPage().getFootnoteCount();
        boolean footnotesCountNotChanged = footnoteCountBefore.equals(footnoteCountAfter);
        editorPage().closeDocumentAndDiscardChanges();
        editorPage().waitForEditorToClose();

        sourcePage().switchToSectionNavigatePage();
        sourcePage().goToRenditionTab();
        sourceNavigateGridPage().rightClickFirstRendition();
        renditionContextMenu().modifySectionGrouping();

        if(sectionGroupingGroupGridPage().doesGroupExist(test1))
        {
            sectionGroupingGroupGridPage().clickGroup(unassigned);
            sectionGroupingGroupGridPage().clickGroup(test1);
            sectionGroupingFooterPage().clickGroupingRemove();
            sectionGroupingFooterPage().clickGroupingYes();
            sectionGroupingFooterPage().clickGroupingApply();
        }
        sectionGroupingFooterPage().clickGroupingApply();
        sectionGroupingGroupGridPage().closeCurrentWindowIgnoreDialogue();

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(checkMessageRebuild, "Rebuild completed successfully"),
            () -> Assertions.assertTrue(checkNoErrorInMessagePane, "Error message appears in messagePane"),
            () -> Assertions.assertTrue(footnotesCountNotChanged, "Footnote quantity is equal")
        );
    }

    /**
     * STORY: NA <br>
     * SUMMARY: checks if a bill track with a bill text error syncs correctly.<br>
     * USER:  LEGAL<br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void rebuildLHFRenditionNonexistingLHFsLegalTest()
    {
        String contentSet = "USCA(Development)";
        String year = "2016";
        String renditionStatus = "APV";
        String docType = "S";
        String docNumber = "719";

        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterContentSet(contentSet);
        sourceNavigateFiltersAndSortsPage().setFilterYear(year);
        sourceNavigateFiltersAndSortsPage().setFilterRenditionStatus(renditionStatus);
        sourceNavigateFiltersAndSortsPage().setFilterDocType(docType);
        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(docNumber);
        sourceNavigateFooterToolsPage().refreshTheGrid();
        sourceNavigateGridPage().unlockFirstRendition();

        sourceNavigateGridPage().rightClickFirstRendition();
        renditionContextMenu().editRendition();
        int footnoteNumber = editorTextPage().getFootnoteCount();
        boolean deleteFootnote = editorTextPage().deleteFootnote(footnoteNumber);

        boolean footnoteCountBefore = (editorTextPage().getFootnoteCount() == 0);
        editorToolbarPage().clickRebuild();

        boolean footnoteCountAfter = (editorTextPage().getFootnoteCount() == 9);
        editorPage().closeDocumentAndDiscardChanges();

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(deleteFootnote, "Footnotes are deleted"),
            () -> Assertions.assertTrue(footnoteCountBefore, "Footnote quantity is 0"),
            () -> Assertions.assertTrue(footnoteCountAfter, "Footnote quantity is correct")
        );
    }

    /**
     * STORY: NA <br>
     * SUMMARY: checks if a bill track with a bill text error syncs correctly.<br>
     * USER:  LEGAL<br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void setEffectiveDateRebuildSectionGroupAndRenditionAndCheckInTest()
    {
        String groupName = "Test1";
        String unassigned = "Unassigned";
        String message = "Rebuild completed successfully";

        String contentSet = "Iowa (Development)";
        String year = "2016";
        String renditionStatus = "APV";
        String docNumber = "2257";
        String currentDate = DateAndTimeUtils.getCurrentDateMMddyyyy();

        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterContentSet(contentSet);
        sourceNavigateFiltersAndSortsPage().setFilterYear(year);
        sourceNavigateFiltersAndSortsPage().setFilterRenditionStatus(renditionStatus);
        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(docNumber);
        sourceNavigateFooterToolsPage().refreshTheGrid();

        sourceNavigateGridPage().rightClickFirstRendition();
        renditionContextMenu().editEffectiveDate();
        effectiveDatePage().enterTheInnerFrame();

        effectiveDatePage().setEffectiveDate(currentDate);
        effectiveDatePage().clickSave();

        sourcePage().switchToSourceNavigatePage();
        sourceNavigateGridPage().onlyRightClickFirstItem();
        renditionContextMenu().modifySectionGrouping();

        if(sectionGroupingGroupGridPage().doesGroupExist(groupName))
        {
            sectionGroupingGroupGridPage().clickGroup(unassigned);
            sectionGroupingGroupGridPage().clickGroup(groupName);
            sectionGroupingFooterPage().clickGroupingRemove();
            sectionGroupingFooterPage().clickGroupingYes();
            sectionGroupingFooterPage().clickGroupingApply();
        }
        sectionGroupingGroupGridPage().clickGroup(unassigned);
        sectionGroupingGroupGridPage().sendEnterToGroup();
        sectionGroupingGroupGridPage().addNewGroup(groupName);

        sectionGroupingSectionGridPage().clickXSectionsInARange(1,2);
        sectionGroupingSectionGridPage().rightClickHighlightedSection(1);
        sectionGroupingSectionGridPage().openMoveToGroupContextMenu(groupName);
        sectionGroupingFooterPage().clickGroupingApply();
        sectionGroupingGroupGridPage().closeCurrentWindowIgnoreDialogue();

        sourcePage().switchToSourceNavigatePage();
        sourceNavigateTabsPage().switchToGroupingTab("Section");
        sourceNavigateGridPage().rightClickItemBySectionGroupName(groupName);
        renditionContextMenu().openEditorByGrouping("Section");
        editorPage().switchDirectlyToTextFrame();

        editorTextPage().scrollToView(EditorTextPageElements.DELTA_AMEND_SUBSECTION);
        editorTextPage().rightClickDeltaAmend("Subsection");
        editorPage().breakOutOfFrame();
        editorTextContextMenu().insertTextSiblingMiscSCP3();

        editorPage().switchDirectlyToTextFrame();
        editorTextPage().clickInsertTextHint();
        editorTextPage().sendKeys(groupName);
        editorToolbarPage().clickRebuild();
        boolean checkMessageRebuild = editorMessagePage().checkMessage(message);
        editorPage().closeDocumentAndDiscardChanges();

        sourcePage().switchToSectionNavigatePage();
        sourcePage().goToRenditionTab();
        sourceNavigateGridPage().rightClickFirstRendition();
        renditionContextMenu().modifySectionGrouping();
        if(sectionGroupingGroupGridPage().doesGroupExist(groupName))
        {
            sectionGroupingGroupGridPage().clickGroup(unassigned);
            sectionGroupingGroupGridPage().clickGroup(groupName);
            sectionGroupingFooterPage().clickGroupingRemove();
            sectionGroupingFooterPage().clickGroupingYes();
            sectionGroupingFooterPage().clickGroupingApply();
        }
        sectionGroupingFooterPage().clickGroupingApply();

        Assertions.assertTrue(checkMessageRebuild, "Rebuild completed successfully");
    }
}
