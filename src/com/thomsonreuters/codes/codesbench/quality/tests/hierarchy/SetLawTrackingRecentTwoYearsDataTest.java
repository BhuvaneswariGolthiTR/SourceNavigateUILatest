package com.thomsonreuters.codes.codesbench.quality.tests.hierarchy;

import java.util.List;

import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.TargetDataMockingNew;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.datapod.HierarchyDatapodObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;

public class SetLawTrackingRecentTwoYearsDataTest extends TestService
{

    HierarchyDatapodObject datapodObject;

    /**
     * STORY/BUGS - CODESITEMS-20/CODESITEMS-7 &lt;br&gt;
     * SUMMARY -  Compares all visible years in the C2012LawTracking window to the current and previous year.
     * The year column should not show the current year minus 2 and older (Anything older than the previous two years of data). &lt;br&gt;
     * USER -  Legal &lt;br&gt;
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void setLawTrackingRecentTwoYearsDataTest()
    {
        datapodObject = TargetDataMockingNew.Iowa.Small.insert();
        String nodeUUID = datapodObject.getSections().get(0).getNodeUUID();
        String newWord = "WordInsertForChange";

        homePage().goToHomePage();
        loginPage().logIn();

        boolean hierarchyNavigatePageAppeared = hierarchyMenu().goToNavigate();
        Assertions.assertTrue(hierarchyNavigatePageAppeared, "The hierarchy navigate page did not appear");
        hierarchySearchPage().searchNodeUuid(nodeUUID);
        siblingMetadataPage().selectedSiblingMetadataEditContent();

        editorPage().switchToEditorTextFrame();
        editorTextPage().insertPhrase(newWord);
        editorPage().pressCloseDocumentButton();
        boolean editorClosureWindowAppeared = editorClosurePage().switchToEditorClosureWindow();
        Assertions.assertTrue(editorClosureWindowAppeared, "The editor closure window did not appear");

        editorClosurePage().clickC2012LawTracking();

        List<Integer> yearList = setLawTrackingPage().getYearFromAllItems();
        boolean yearDatesAreCorrect = setLawTrackingPage().compareYearListToCurrentAndPreviousYear(yearList);
        Assertions.assertTrue(yearDatesAreCorrect, "Years do not have correct date range. Date range should be current and previous year.");

        setLawTrackingPage().closeCurrentWindowIgnoreDialogue();
        editorClosureWindowAppeared = editorClosurePage().switchToEditorClosureWindow();
        Assertions.assertTrue(editorClosureWindowAppeared, "The editor closure page did not appear");
        editorClosurePage().clickDiscardChanges();
    }

    @AfterEach
    public void cleanUp()
    {
        if (datapodObject != null)
        {
            datapodObject.delete();
        }
    }
}