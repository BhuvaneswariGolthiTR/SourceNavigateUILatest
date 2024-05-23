package com.thomsonreuters.codes.codesbench.quality.tests.source.set1;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CreditCumulatorTests extends TestService
{
    /**
     * STORY: HALCYONST-1013 <br>
     * SUMMARY: Modifies the cumulate credits and checks it in the editor <br>
     * USER:  LEGAL <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void creditCumulatorTest()
    {
        String deleted = "Not Deleted";
        String contentSet = "USCA(Development)";
        String year = "2016";
        String renditionStatus = "PREP";
        String docNumber = "1213";

        String workflowType = "CwbCreditCumulator";
        String description = "Credit Cumulator";
        String userID = user().getUsername();

        String firstDeltaSectionToCheck = "Sec. 2(a)(1)";
        String firstDeltaSectionNumber = "Sec. 2(a)(1), 2(a)(2)";
        String firstDeltaFlagSection = "Sec. 2(a)(2)";
        String secondDeltaSectionToCheck = "Sec. 2(c)(1)";
        String secondDeltaSectionNumber = "Sec. 2(c)(1), 2(c)(2)";
        String thirdDeltaFlagSection = "Sec. 2(c)(2)";
        String flag = "NOCR";

        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDeleted(deleted);
        sourceNavigateFiltersAndSortsPage().setFilterContentSet(contentSet);
        sourceNavigateFiltersAndSortsPage().setFilterYear(year);
        sourceNavigateFiltersAndSortsPage().setFilterRenditionStatus(renditionStatus);
        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(docNumber);
        sourceNavigateFooterToolsPage().refreshTheGrid();

        sourceNavigateGridPage().clickFirstRendition();
        sourceNavigateTabsPage().goToDeltaTab();

        sourceNavigateFooterToolsPage().clickSelectAllOnPage();
        sourceNavigateGridPage().rightClickRenditions();
        deltaContextMenu().goToEditEffectiveDate();
        effectiveDatePage().setCurrentDateInEffectiveDateCalender();
        effectiveDatePage().clickSave();

        sourcePage().switchToDeltaNavigatePage();
        sourceNavigateTabsPage().goToRenditionTab();
        sourceNavigateGridPage().rightClickRenditions();
        renditionContextMenu().modifyCumulateCredits();

        sourcePage().switchToSourceNavigatePage();
        boolean workflowReportingWindowAppeared =  toolsMenu().goToWorkflowReportingSystem();
        Assertions.assertTrue(workflowReportingWindowAppeared,"The workflow reporting window did appear.");
        boolean workflowCompleted = workflowSearchPage().filterWorkflowAndVerifyStatus(workflowType, "", description, userID.toUpperCase());
        String workflowID = workflowSearchPage().getWorkflowIdOfFirstWorkflow();

        workflowSearchPage().closeWorkflowSearchPage();
        sourcePage().switchToSourceNavigatePage();

        sourceNavigateGridPage().firstRenditionEditContent();
        editorPage().switchDirectlyToTextFrame();

        boolean firstDeltaAmendSubsectionInfo = editorTextPage().checkCreditSectionForSectionNumbers(firstDeltaSectionToCheck,firstDeltaSectionNumber);
        boolean firstDeltaAddSubsectionInfo = editorTextPage().checkCreditSectionForFlag(firstDeltaFlagSection,flag);
        boolean secondDeltaAmendSubsectionInfo = editorTextPage().checkCreditSectionForSectionNumbers(secondDeltaSectionToCheck,secondDeltaSectionNumber);
        boolean thirdDeltaAmendSubsectionInfo = editorTextPage().checkCreditSectionForFlag(thirdDeltaFlagSection,flag);

        editorPage().closeDocumentWithNoChanges();

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(workflowCompleted, String.format("Workflow %s was finished", workflowID)),
            () -> Assertions.assertTrue(firstDeltaAmendSubsectionInfo, "The First Delta Amend Subsection does contain the expected section number"),
            () -> Assertions.assertTrue(firstDeltaAddSubsectionInfo, "The First Delta add subsection does contain the expected NOCR flag"),
            () -> Assertions.assertTrue(secondDeltaAmendSubsectionInfo,"The Second Delta Amend Subsection contains the expected section number"),
            () -> Assertions.assertTrue(thirdDeltaAmendSubsectionInfo, "The Third Delta Amend Subsection contains the expected NOCR flag")
        );
    }
}