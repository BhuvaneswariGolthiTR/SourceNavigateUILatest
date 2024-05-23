package com.thomsonreuters.codes.codesbench.quality.tests.source.set1;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CompareBaselinesWordForWordTests extends TestService
{
    /**
     * STORY: PNR_14000 <br>
     * SUMMARY: Checks the baseline for the correct description and location <br>
     * USER:  Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void compareBaseLinesWordForWordTest()
    {
        String description = "DJ";
        String descriptionPostTest ="Restored from 0";
        String phraseToInsert = "asdfasdf";

        String contentSet = "Alaska (Development)";
        String docType = "HR";
        String docNumber = "1";
        String contentType = "BILL";
        String year = "2011";
        String renditionStatus = "AD";

        homePage().goToHomePage();
        loginPage().logIn();
        boolean sourceNavigateWindowAppeared = sourceMenu().goToSourceC2012Navigate();
        Assertions.assertTrue(sourceNavigateWindowAppeared,"Source Navigate Widow did appear");

        sourceNavigateFiltersAndSortsPage().setFilterContentSet(contentSet);
        sourceNavigateFiltersAndSortsPage().setFilterDocType(docType);
        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(docNumber);
        sourceNavigateFiltersAndSortsPage().setFilterContentType(contentType);
        sourceNavigateFiltersAndSortsPage().setFilterYear(year);
        sourceNavigateFiltersAndSortsPage().setFilterRenditionStatus(renditionStatus);
        sourceNavigateFooterToolsPage().refreshTheGrid();

        sourceNavigateGridPage().firstRenditionEditContent();

        editorPage().switchDirectlyToTextFrame();
        editorTextPage().insertPhraseIntoTextParagraphByNumber(phraseToInsert,"1");
        editorPage().closeAndCheckInChangesWithBaselineNotes(description);
        editorPage().waitForEditorToClose();

        sourcePage().switchToSourceNavigatePage();
        sourceNavigateFooterToolsPage().refreshTheGrid();
        sourceNavigateGridPage().rightClickFirstRendition();
        renditionContextMenu().viewRenditionBaselines();

        boolean correctDescription = viewBaselinesNavigatePage().checkBaselineTableDescriptionValue(1, description);
        boolean correctNumber = viewBaselinesNavigatePage().checkBaselineTableNumberValue(1, "Current");

        tableTestingPage().selectRows(1,2);
        viewBaselinesNavigatePage().rightClickTableNumber(1);
        boolean compareExists = viewBaselinesNavigatePage().doesCompareBaselinesExist();

        viewBaselinesNavigatePage().breakOutOfFrame();
        viewBaselinesNavigatePage().closeViewBaselinesPage();

        sourcePage().switchToSourceNavigatePage();

        viewBaselinesNavigatePage().restoreFirstRenditionToBaselineInRowWithGivenNumber("0");
        sourceNavigateGridPage().rightClickFirstRendition();
        renditionContextMenu().viewRenditionBaselines();
        boolean restoredDescription = viewBaselinesNavigatePage().checkBaselineTableDescriptionValue(1, descriptionPostTest);

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(correctDescription,"The current baseline has the correct description"),
            () -> Assertions.assertTrue(correctNumber,"The current baseline was marked as Current"),
            () -> Assertions.assertTrue(compareExists,"The compare baselines option was available"),
            () -> Assertions.assertTrue(restoredDescription, "The current baseline was marked as 'Restored from 0'")
        );
    }
}