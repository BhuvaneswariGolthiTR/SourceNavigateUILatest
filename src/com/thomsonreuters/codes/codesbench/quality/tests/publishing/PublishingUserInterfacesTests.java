package com.thomsonreuters.codes.codesbench.quality.tests.publishing;

import com.thomsonreuters.codes.codesbench.quality.menuelements.mainmenuelements.PublishingMenuElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.publishing.toolbox.ToolbarElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.EDGE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.hierarchy.HierarchyDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.publishing.PublishingDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.sql.Connection;

public class PublishingUserInterfacesTests extends TestService
{
    Connection uatConnection;
    String contentSetIowa = ContentSets.IOWA_DEVELOPMENT.getCode();


    /**
     * STORY: HALCYONST-9105 <br>
     * SUMMARY: One of the validations performed on the Publishing UIs is to determine if the prior version of a selected node has never been
     * published and if it hasn't, to automatically select and include the prior version on the Submission page. This test verifies
     * that deleted nodes that have never been published should never be uploaded/published. The validation should not
     * identify prior version nodes that have never been published when those prior version nodes have been deleted. <br>
     * USER: LEGAL <br>
     */
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void validationForPriorVersionsIdentifyingDeletedNodesTest()
    {
        String nodeUuid = "I2766520014EE11DA8AC5CD53670E6B4E";
        String newNodeStartDate = DateAndTimeUtils.getCurrentDateMMddyyyy();
        String yesterdaysDate = DateAndTimeUtils.getYesterdayDateMMddyyyy();
        String alertMessage = String.format("THIS IS NOT AN ERROR, just a notice that the selected node [%s] ended before your navigation filter date. Your navigation date has been reset to %s", nodeUuid, yesterdaysDate);

        uatConnection = BaseDatabaseUtils.connectToDatabaseUAT();
        PublishingDatabaseUtils.checkAndAddPublishApproverForContentSet(user().getUsername(), user().getFirstname(), user().getLastname(), contentSetIowa, uatConnection);

        homePage().goToHomePage();
        loginPage().logIn();

        hierarchyMenu().goToNavigate();
        hierarchySearchPage().searchNodeUuid(nodeUuid);

        String nodeValue = siblingMetadataPage().getSelectedNodeValue();

        siblingMetadataPage().selectedSiblingMetadataEditContent();

        editorPage().switchDirectlyToTextFrame();
        editorTextPage().insertSpaceAndRemoveSpace();
        editorTextPage().breakOutOfFrame();

        editorToolbarPage().clickToolbarClose();

        editorClosurePage().setAssignEffectiveDate(newNodeStartDate);
        editorClosurePage().selectQuickLoad();
        editorClosurePage().clickCheckInChangesAndHandleAlert(alertMessage);

        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().deleteFunctionsDelete();

        deletePage().clickQuickLoad();
        deletePage().clickSubmit();
        hierarchyNavigatePage().switchToHierarchyEditPage();

        siblingMetadataPage().selectNodeByValueAndStartDate(nodeValue, newNodeStartDate);
        siblingMetadataPage().rightClickSiblingMetadata();
        siblingMetadataContextMenu().updateMetadata();
        String newNodeUuid = updateMetadataPage().getNodeUuid();
        updateMetadataPage().clickCancel();

        publishingMenu().goToPublishingPublishApproveTextNodesOnly();
        gridPage().waitForGridLoaded();

        gridHeaderPage().openMenuForKeywordColumn();
        gridHeaderFiltersPage().setFilterValue("Sec.");
        gridHeaderPage().openMenuForValueColumn();
        gridHeaderFiltersPage().setFilterValue(nodeValue);
        gridHeaderPage().openMenuForStartDate();
        gridHeaderFiltersPage().setFilterDateValue(newNodeStartDate);

        gridPage().rightClickFirstSectionNode();
        gridContextMenu().approvedStatus();

        toolbarPage().clickNext();

        boolean vWarningDidAppear = toolbarPage().isElementDisplayed(ToolbarElements.V_WARNING_XPATH);

        //clean up steps
        //set the orginal node back to normal
        HierarchyDatabaseUtils.updateIsDeleteFlagToUndeletedHierarchyNavigate(nodeUuid, contentSetIowa, uatConnection);
        HierarchyDatabaseUtils.setEffectiveEndDateToNull(nodeUuid, contentSetIowa, uatConnection);

        //set the new node to deleted
        HierarchyDatabaseUtils.updateIsDeleteFlagToDeletedHierarchyNavigate(newNodeUuid, contentSetIowa, uatConnection);

        Assertions.assertFalse(vWarningDidAppear, "V Warning did appear. This means that the system is recognizing deleted nodes");
    }


    /**
     * STORY: HALCYONST-15401/15383 <br>
     * SUMMARY: Verifies that the Publishing > Publishing Status Reports menu options are displayed correctly <br>
     * USER: LEGAL <br>
     */
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void publishingStatusReportsMenuOptionsTest()
    {
        homePage().goToHomePage();
        loginPage().logIn();
        publishingMenu().openMenu();
        publishingMenu().openSubMenu(PublishingMenuElements.publishingStatusReports);

        WebElement pubNavigateEvaluationParent = PublishingMenuElements.publishingStatusReportsPubNavigateEvaluation.findElement(By.xpath("./.."));
        WebElement wipToPubUploadIssuesParent = PublishingMenuElements.publishingStatusReportsWipToPubUploadIssues.findElement(By.xpath("./.."));
        WebElement errorStatusesParent = PublishingMenuElements.publishingStatusReportsErrorStatuses.findElement(By.xpath("./.."));
        WebElement breakpointParent = PublishingMenuElements.publishingStatusReportsBreakpoint.findElement(By.xpath("./.."));
        WebElement nodOnlyPubNavigateEvaluationParent = PublishingMenuElements.publishingStatusReportsNodOnlyPubNavigateEvaluation.findElement(By.xpath("./.."));
        WebElement nodOnlyWipToPubUploadIssuesParent = PublishingMenuElements.publishingStatusReportsNodOnlyWipToPubUploadIssues.findElement(By.xpath("./.."));
        WebElement nodOnlyErrorStatusesParent = PublishingMenuElements.publishingStatusReportsNodOnlyErrorStatuses.findElement(By.xpath("./.."));
        WebElement breakpointParent2 = PublishingMenuElements.publishingStatusReportsSecondBreakpoint.findElement(By.xpath("./.."));
        WebElement westlawLoadCompleteParent = PublishingMenuElements.publishingStatusReportsWestlawLoadComplete.findElement(By.xpath("./.."));

        boolean isPubNavigateEvaluationOptionDisplayed = publishingMenu().getElementsPreviousSibling(wipToPubUploadIssuesParent).equals(pubNavigateEvaluationParent);
        boolean isWipToPubUploadIssuesOptionDisplayed = publishingMenu().getElementsNextSibling(pubNavigateEvaluationParent).equals(wipToPubUploadIssuesParent);
        boolean isErrorStatusesOptionDisplayed = publishingMenu().getElementsNextSibling(wipToPubUploadIssuesParent).equals(errorStatusesParent);
        boolean isBreakpointDisplayed = publishingMenu().getElementsNextSibling(errorStatusesParent).equals(breakpointParent);
        boolean isNodOnlyPubNavigateEvaluationOptionDisplayed = publishingMenu().getElementsNextSibling(breakpointParent).equals(nodOnlyPubNavigateEvaluationParent);
        boolean isNodOnlyWipToPubUploadIssuesOptionDisplayed = publishingMenu().getElementsNextSibling(nodOnlyPubNavigateEvaluationParent).equals(nodOnlyWipToPubUploadIssuesParent);
        boolean isNodOnlyErrorStatusesOptionDisplayed = publishingMenu().getElementsNextSibling(nodOnlyWipToPubUploadIssuesParent).equals(nodOnlyErrorStatusesParent);
        boolean isbreakpoint2Displayed = publishingMenu().getElementsNextSibling(nodOnlyErrorStatusesParent).equals(breakpointParent2);
        boolean isWestlawLoadCompleteDisplayed = publishingMenu().getElementsNextSibling(breakpointParent2).equals(westlawLoadCompleteParent);

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(isPubNavigateEvaluationOptionDisplayed, "Publishing > Publishing Status Reports > Pub navigate Evaluation option is displayed 1st"),
            () -> Assertions.assertTrue(isWipToPubUploadIssuesOptionDisplayed, "Publishing > Publishing Status Reports > WIP to PUB Upload Issues option is displayed 2nd"),
            () -> Assertions.assertTrue(isErrorStatusesOptionDisplayed, "Publishing > Publishing Status Reports > ERROR Statuses option is displayed 3rd"),
            () -> Assertions.assertTrue(isBreakpointDisplayed, "Publishing > Publishing Status Reports > '------------------------' is displayed 4th"),
            () -> Assertions.assertTrue(isNodOnlyPubNavigateEvaluationOptionDisplayed, "Publishing > Publishing Status Reports > NOD-only Pub navigate Evaluation option is displayed 5th"),
            () -> Assertions.assertTrue(isNodOnlyWipToPubUploadIssuesOptionDisplayed, "Publishing > Publishing Status Reports > NOD-only WIP to PUB Upload Issues option is displayed 6th"),
            () -> Assertions.assertTrue(isNodOnlyErrorStatusesOptionDisplayed, "Publishing > Publishing Status Reports > NOD-only ERROR Statuses option is displayed 7th"),
            () -> Assertions.assertTrue(isbreakpoint2Displayed, "Publishing > Publishing Status Reports > '------------------------' is displayed 7th"),
            () -> Assertions.assertTrue(isWestlawLoadCompleteDisplayed, "Publishing > Publishing Status Reports > Westlaw Load Complete is displayed 8th")
        );
    }

    @AfterEach
    public void closeConnection()
    {
        BaseDatabaseUtils.disconnect(uatConnection);
    }
}
