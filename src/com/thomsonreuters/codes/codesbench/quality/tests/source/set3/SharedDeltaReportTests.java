package com.thomsonreuters.codes.codesbench.quality.tests.source.set3;

import com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.source.navigate.RenditionContextMenuElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.source.SharedDeltaReportPageElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.autoIT.AutoITUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.files.XlsFileUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SharedDeltaReportTests extends TestService
{
    /**
     * STORY: HALCYONST-1281 <br>
     * SUMMARY: Checks the Shared Deltas Report by going through the Source Menu<br>
     * USER: LEGAL <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void singleReportWithManySharedDeltasAndConsolidatedColumnsViaMainMenuTest()
    {
        String contentSet = "Iowa (Development)";
        String year = "2012";
        String renditionStatus = "PREP";
        String docType = "SF";
        String docNumber = "2332";

        String sharedDeltaContentSet = "Law";
        String testFileName = "2012 2RG 27";
        String reportName = "TEST " + DateAndTimeUtils.getCurrentDateAndTimeyyyyMMddHHmmss();
        String requesterName = user().getLastname() + ", " + user().getFirstname();

        String reportType = "Shared Deltas Report";

        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();
        sourcePage().waitForGridRefresh();

        sourceNavigateFiltersAndSortsPage().setFilterContentSet(contentSet);
        sourceNavigateFiltersAndSortsPage().setFilterYear(year);
        sourceNavigateFiltersAndSortsPage().setFilterRenditionStatus(renditionStatus);
        sourceNavigateFiltersAndSortsPage().setFilterDocType(docType);
        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(docNumber);
        sourceNavigateFooterToolsPage().refreshTheGrid();

        sourceNavigateGridPage().rightClickFirstRendition();
        renditionContextMenu().viewDeltasAffectingSameTarget();

        ArrayList<ArrayList<String>> infoFromSourceNav = sourceNavigateGridPage().getInfoFromSourceNavForReportConsolidatedColumns();
        boolean reportWindowAppeared = sourceMenu().goToSourceSharedDeltaReport();

        sharedDeltaReportPage().setFilterContentSet(sharedDeltaContentSet);
        sharedDeltaReportPage().selectMaterialFromAvailableMaterials(testFileName);
        sharedDeltaReportPage().addMaterialToTheSelectedMaterialsList();
        sharedDeltaReportPage().unCheckAllColumns();

        boolean alertTextIsCorrect = sharedDeltaReportPage().clickSubmitNoName();
        sharedDeltaReportPage().addReportName(reportName);
        sharedDeltaReportPage().clickSubmitWithName();

        sourcePage().switchToDeltaNavigatePage();
        yourWorkflowHasBeenCreatedPage().switchToYourWorkflowHasBeenCreatedPage();

        String workflowId = yourWorkflowHasBeenCreatedPage().getWorkflowId();
        yourWorkflowHasBeenCreatedPage().clickClose();
        sourcePage().breakOutOfFrame();
        toolsMenu().goToWorkflowReportingSystem();

        workflowSearchPage().setWorkflowID(workflowId);
        workflowSearchPage().clickFilterButton();
        boolean workflowFinishedInWorkflowReportingSystem = workflowSearchPage().checkFirstWorkflowFinishedFiveMinutes();
        workflowSearchPage().closeCurrentWindowIgnoreDialogue();

        sourcePage().switchToDeltaNavigatePage();
        auditsMenu().goToReportCentral();
        reportCentralFiltersPage().setReportType(reportType);
        reportCentralFiltersPage().setReportIdentifier(reportName);
        reportCentralFiltersPage().setRequestersName(requesterName);

        boolean workflowFinishedInAuditsReportCentral = reportCentralPage().reportCentralVerifyWorkflowFinishes();

        String innerLink = reportCentralGridPage().getHREFFromReportIdentifier();
        String uuid = innerLink.substring(innerLink.indexOf("/")+1, innerLink.lastIndexOf("'")-3);
        reportCentralGridPage().clickReportIdentifierLink();

        AutoITUtils.clickSaveOnIEPopupWithAutoIT();
        DateAndTimeUtils.takeNap(5000);
        String filePath = System.getProperty("user.home") + File.separator + "Downloads" + File.separator + uuid + ".xls";

        List<List<String>> infoFromXls = XlsFileUtils.getAllDataOfHSSFSheet(XlsFileUtils.openFileAsHssfWorkbook(filePath).getSheet("Sheet1"));
        Assertions.assertTrue(infoFromXls.size() == infoFromSourceNav.size()+1, "The two sets of data are the same size");

        boolean listsAreEqual = true;
        for(int i = 0; i < infoFromSourceNav.size(); i++)
        {
            Assertions.assertTrue(infoFromXls.get(i).size() == infoFromSourceNav.get(i).size(),"The two sets of data are the same size");
            for(int j = 0; j < infoFromSourceNav.get(i).size(); j++)
            {
                listsAreEqual =  infoFromSourceNav.get(i).get(j).equals(infoFromXls.get(i+1).get(j));
            }
        }
        boolean dataAreTheSame = listsAreEqual;
        Assertions.assertAll
        (
            () -> Assertions.assertTrue(reportWindowAppeared, "Report window appeared"),
            () -> Assertions.assertTrue(alertTextIsCorrect, "Alert contains the correct text"),
            () -> Assertions.assertTrue(workflowFinishedInWorkflowReportingSystem, String.format("The workflow %s in the workflow reports finished", workflowId)),
            () -> Assertions.assertTrue(workflowFinishedInAuditsReportCentral, "The workflow in the reports central finished"),
            () -> Assertions.assertTrue(dataAreTheSame, "The two sets of data contain the same information")
        );
    }

    /**
     * STORY: HALCYONST-1284 <br>
     * SUMMARY: Tests the Shared Delta Reports going through the rendition context menu.<br>
     * USER: LEGAL <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void singleReportWithManySharedDeltasAndAllColumnsViaContextMenu()
    {
        String contentSet = "Iowa (Development)";
        String year = "2012";
        String renditionStatus = "PREP";
        String docType = "SF";
        String docNumber = "2332";
        String testFileName = "2012 2RG 27";

        String workflowType = "SharedDeltaReport";
        String workflowDescription = "Deltas Affecting Same Target Report";
        String workflowUser = user().getWorkflowUsername();

        String reportName = "TEST " + DateAndTimeUtils.getCurrentDateAndTimeyyyyMMddHHmmss();
        String requesterName = user().getLastname() + ", " + user().getFirstname();

        String reportType = "Shared Deltas Report";

        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();
        sourcePage().waitForGridRefresh();

        sourceNavigateFiltersAndSortsPage().setFilterContentSet(contentSet);
        sourceNavigateFiltersAndSortsPage().setFilterYear(year);
        sourceNavigateFiltersAndSortsPage().setFilterRenditionStatus(renditionStatus);
        sourceNavigateFiltersAndSortsPage().setFilterDocType(docType);
        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(docNumber);
        sourceNavigateFooterToolsPage().refreshTheGrid();

        sourceNavigateGridPage().rightClickFirstRendition();
        renditionContextMenu().viewDeltasAffectingSameTarget();

        ArrayList<ArrayList<String>> infoFromSourceNav = sourceNavigateGridPage().getInfoFromSourceNavForReportAllColumns();

        sourceNavigateTabsPage().goToRenditionTab();
        sourceNavigateGridPage().rightClickFirstRendition();
        renditionContextMenu().openReportSharedDeltaReport();

        boolean onlyOneMaterialSelected = sharedDeltaReportPage().getNumberOfSelectedMaterials() == 1;
        boolean materialIsCorrect = sharedDeltaReportPage().getFirstSelectedMaterialsName().equals(testFileName);
        sharedDeltaReportPage().checkAllColumns();

        sharedDeltaReportPage().addReportName(reportName);
        sharedDeltaReportPage().clickSubmitWithName();

        sourcePage().switchToSourceNavigatePage();
        sourcePage().breakOutOfFrame();
        toolsMenu().goToWorkflowReportingSystem();

        boolean workflowFinishedInWorkflowReportingSystem = workflowSearchPage().filterWorkflowAndVerifyStatus
                (workflowType, "", workflowDescription, workflowUser);
        String workflowID = workflowSearchPage().getWorkflowIdOfFirstWorkflow();
        workflowSearchPage().closeCurrentWindowIgnoreDialogue();

        sourcePage().switchToSourceNavigatePage();
        auditsMenu().goToReportCentral();
        reportCentralFiltersPage().setReportType(reportType);
        reportCentralFiltersPage().setReportIdentifier(reportName);
        reportCentralFiltersPage().setRequestersName(requesterName);

        boolean workflowFinishedInAuditsReportCentral = reportCentralPage().reportCentralVerifyWorkflowFinishes();

        String innerLink = reportCentralGridPage().getHREFFromReportIdentifier();
        String uuid = innerLink.substring(innerLink.indexOf("/")+1, innerLink.lastIndexOf("'")-3);
        reportCentralGridPage().clickReportIdentifierLink();

        AutoITUtils.clickSaveOnIEPopupWithAutoIT();
        DateAndTimeUtils.takeNap(5000);
        String filePath = System.getProperty("user.home") + File.separator + "Downloads" + File.separator + uuid + ".xls";

        List<List<String>> infoFromXls = XlsFileUtils.getAllDataOfHSSFSheet(XlsFileUtils.openFileAsHssfWorkbook(filePath).getSheet("Sheet1"));
        Assertions.assertTrue(infoFromXls.size() == infoFromSourceNav.size()+1, "The two sets of data are the same size");

        boolean listsAreEqual = true;
        for(int i = 0; i < infoFromSourceNav.size(); i++)
        {
            Assertions.assertTrue(infoFromXls.get(i).size() == infoFromSourceNav.get(i).size(),"The two sets of data are the same size");
            for(int j = 0; j < infoFromSourceNav.get(i).size(); j++)
            {
                listsAreEqual =  infoFromSourceNav.get(i).get(j).equals(infoFromXls.get(i+1).get(j));
            }
        }
        boolean dataAreTheSame = listsAreEqual;
        Assertions.assertAll
        (
            () -> Assertions.assertTrue(materialIsCorrect, "The correct material is pre-loaded into the selected panel"),
            () -> Assertions.assertTrue(onlyOneMaterialSelected, "Only one material is loaded into the selected panel"),
            () -> Assertions.assertTrue(workflowFinishedInWorkflowReportingSystem, String.format("The workflow %s in the workflow reports finished", workflowID)),
            () -> Assertions.assertTrue(workflowFinishedInAuditsReportCentral, "The workflow in the reports central finished"),
            () -> Assertions.assertTrue(dataAreTheSame, "The two sets of data contain the same information")
        );
    }

    /**
     * STORY: HALCYONST-1283 <br>
     * SUMMARY: tests the Shared Deltas report with a delta with no shared deltas <br>
     * USER: LEGAL <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void singleReportWithZeroSharedDeltasViaMainMenuTest()
    {
        /*
            Halcyonst-4213 - this test will continue to fail out until this Halcyonst bug is fixed
         */
        String contentSet = "Law";
        String testFileName = "2012 2RG CHAPTER 155";
        String reportName = "TEST " + DateAndTimeUtils.getCurrentDateAndTimeyyyyMMddHHmmss();

        homePage().goToHomePage();
        loginPage().logIn();

        sourceMenu().goToSourceSharedDeltaReport();

        sharedDeltaReportPage().setFilterContentSet(contentSet);
        sharedDeltaReportPage().selectMaterialFromAvailableMaterials(testFileName);
        sharedDeltaReportPage().addMaterialToTheSelectedMaterialsList();
        sharedDeltaReportPage().addReportName(reportName);
        sharedDeltaReportPage().clickSubmitWithName();

        boolean alertTextIsCorrect = AutoITUtils.verifyAlertTextAndAccept(true, "A problem occured while trying to kick off the workflow. A workflow id was not created.");
        boolean errorMessageTextIsCorrect = sharedDeltaReportPage().doesElementExist(SharedDeltaReportPageElements.NO_DELTAS_ERROR_MESSAGE_SPAN);

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(alertTextIsCorrect, "The Alert saying that no workflow's been started should appear"),
            () -> Assertions.assertTrue(errorMessageTextIsCorrect, "There should be an error message in the window")
        );
    }

    /**
     * STORY: HALCYONST-1280 <br>
     * SUMMARY: Tests the shared delta context menu option while in a different content set<br>
     * USER:  LEGAL<br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void workflowDoesntStartIfRenditionContentSetIsNotEqualToDefaultContentSet()
    {
        String firstContentSet = "Iowa (Development)";
        String secondContentSet = "USCA(Development)";

        String validation = "None";
        String deleted = "Not Deleted";
        String renditionStatus = "PREP";

        homePage().goToHomePage();
        loginPage().logIn();

        homePage().setMyContentSet(firstContentSet);
        sourceMenu().goToSourceC2012Navigate();
        sourcePage().waitForGridRefresh();

        sourceNavigateFiltersAndSortsPage().setFilterValidation(validation);
        sourceNavigateFiltersAndSortsPage().setFilterDeleted(deleted);
        sourceNavigateFiltersAndSortsPage().setFilterContentSet(secondContentSet);
        sourceNavigateFiltersAndSortsPage().setFilterRenditionStatus(renditionStatus);
        sourceNavigateFooterToolsPage().refreshTheGrid();

        sourceNavigateGridPage().rightClickFirstRendition();
        renditionContextMenu().openReport();
        boolean isReportDisabledForIncorrectContentSet = renditionContextMenu().isElementDisabled(RenditionContextMenuElements.reportSharedDeltaReport);

        sourceNavigateFiltersAndSortsPage().setFilterContentSet(firstContentSet);
        sourceNavigateFooterToolsPage().refreshTheGrid();

        sourceNavigateGridPage().rightClickFirstRendition();
        renditionContextMenu().openReport();
        boolean isReportEnabledForCorrectContentSet = !renditionContextMenu().isElementDisabled(RenditionContextMenuElements.reportSharedDeltaReport);

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(isReportDisabledForIncorrectContentSet, "The report for the content set you're not in is disabled"),
            () -> Assertions.assertTrue(isReportEnabledForCorrectContentSet, "The report for the content set you're in is enabled")
        );
    }
}
