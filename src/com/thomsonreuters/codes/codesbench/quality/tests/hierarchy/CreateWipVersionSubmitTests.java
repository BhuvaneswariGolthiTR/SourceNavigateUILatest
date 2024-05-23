package com.thomsonreuters.codes.codesbench.quality.tests.hierarchy;

import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.CreateWipVersionPageElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.autoIT.AutoITUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.hierarchy.HierarchyDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.publishing.PublishingDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.CommonDataMocking;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.TargetDataMockingNew;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.datapod.HierarchyDatapodConfiguration;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.datapod.HierarchyDatapodObject;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;

import java.sql.Connection;

public class CreateWipVersionSubmitTests extends TestService
{
    Connection uatConnection;

    HierarchyDatapodObject datapodObject;

    /**
     * STORY: HALCYONST-6957 <br>
     * SUMMARY: Verifies that after creating a new wip version for a node, the node is updated to Not Published status <br>
     * USER: LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void historicalVersionSetNotPublishedTest()
    {
        HierarchyDatapodConfiguration.getConfig().setSectionWipCount(3);
        datapodObject = TargetDataMockingNew.Iowa.Small.insert();
        String contentUuid = datapodObject.getSections().get(0).getContentUUID();
        String nodeUuid = datapodObject.getSections().get(0).getNodeUUID();

        String wipIndexToCreateHistoricalVersion = "2";

        String yesterdaysDate = DateAndTimeUtils.getYesterdayDateMMddyyyy();
        String contentSetIowa = ContentSets.IOWA_DEVELOPMENT.getCode();
        uatConnection = BaseDatabaseUtils.connectToDatabaseUAT();

        homePage().goToHomePage();
        loginPage().logIn();

        hierarchyNavigatePage().goToHierarchyPage(contentSetIowa);

        hierarchySearchPage().searchNodeUuid(nodeUuid);

        if(!siblingMetadataPage().isSelectedNodeNotPublished())
        {
            siblingMetadataPage().rightClickSelectedSiblingMetadata();
            siblingMetadataContextMenu().setNotPublishedPublishingStatus();
        }

        PublishingDatabaseUtils.setPublishingNodeToWestlawLoaded(contentUuid, contentSetIowa, uatConnection);
        String nodeValue = HierarchyDatabaseUtils.getNodeValue(contentUuid, contentSetIowa, uatConnection);

        hierarchyNavigatePage().switchToHierarchyEditPage();
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().refreshSelectedNode();

        //Converts the initial date string to be in the correct format for SQL
        String initialStartDate = DateAndTimeUtils.convertDateToFormat(HierarchyDatabaseUtils.getLegisEffectiveStartDate(nodeUuid, uatConnection), "MM/dd/yyyy");
        String updatedDate = DateAndTimeUtils.convertDateToFormat(HierarchyDatabaseUtils.getLegisEffectiveStartDate(nodeUuid, uatConnection), "dd-MMM-yy");
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().createWipVersion(false);

        createWipVersionPage().clickQuickLoadButton();
        createWipVersionPage().clickSubmitButton();

        // Stabilization to make sure the node is selected before the check
        siblingMetadataPage().selectNodesByValue(nodeValue);
        boolean isNodeNotPublishAfterCreatingWipVersion = siblingMetadataPage().isSelectedNodeStatusNotPublished();

        siblingMetadataPage().selectNodesByValue(nodeValue);
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().createWipVersion(false);

        createWipVersionPage().clickQuickLoadButton();
        createWipVersionPage().clickSubmitButton();

        siblingMetadataPage().selectNodesByValue(nodeValue);
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().setReadyPublishingStatus();

        boolean isNodeReadyStatus = siblingMetadataPage().isSelectedNodeReadyStatus();
        Assertions.assertTrue(isNodeReadyStatus);

        siblingMetadataPage().selectNodesByValue(nodeValue);
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().viewModifyPreviousWIPVersion();

        previousWipVersionsPage().rightClickWipVersionByIndex(wipIndexToCreateHistoricalVersion);
        previousWipVersionsContextMenu().createHistoricalVersion();

        setLawTrackingPage().clickQuickLoadTrackingButton();
        setLawTrackingPage().clickOkButton();
        setLawTrackingPage().switchToPreviousWipVersionsPage();

        previousWipVersionsPage().clickCloseButton();

        siblingMetadataPage().selectNodesByValue(nodeValue);
        boolean isListOfSelectedNodesNotPublish = siblingMetadataPage().isSelectedNodeStatusNotPublished();

        hierarchyTreePage().setDateOfTree(yesterdaysDate);

        siblingMetadataPage().selectNodeByValueAndStartDate(nodeValue, initialStartDate);

        if(!siblingMetadataPage().isSelectedNodeDeleted())
        {
            HierarchyDatabaseUtils.deepDeleteNode(siblingMetadataPage().getSelectedNodeUuid(), uatConnection);
        }

        HierarchyDatabaseUtils.updateLegisStartEffectiveDate(contentUuid, updatedDate, uatConnection);
        BaseDatabaseUtils.disconnect(uatConnection);

        Assertions.assertAll
                (
                        () -> Assertions.assertTrue(isNodeNotPublishAfterCreatingWipVersion, "The node should be not published after creating a wip version"),
                        () -> Assertions.assertTrue(isListOfSelectedNodesNotPublish, "The nodes created by historical version should be not published")
                );
    }

    /**
     * STORY - CODESITEMS-28 <br>
     * SUMMARY - This test verifies that creating a wip version is successful and the new version can then be viewed in the View/Modify Previous Wip Versions page<br>
     * USER - LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void createWipVersionTest()
    {
        datapodObject = TargetDataMockingNew.Iowa.Small.insert();
        String nodeUuid = datapodObject.getSections().get(0).getNodeUUID();
        String legalUsername = user().getUsername();
        String currentDate = DateAndTimeUtils.getCurrentDateMMddyyyy();

        homePage().goToHomePage();
        loginPage().logIn();

        hierarchyMenu().goToNavigate();

        hierarchySearchPage().searchNodeUuid(nodeUuid);
        String nodeValue = siblingMetadataPage().getSelectedNodeValue();
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().createWipVersion(false);

        boolean c2012LawTrackingButtonAppears = createWipVersionPage().isElementDisplayed(CreateWipVersionPageElements.C2012_LAW_TRACKING_BUTTON_XPATH);
        boolean fullVolsLawTrackingButtonAppears = createWipVersionPage().isElementDisplayed(CreateWipVersionPageElements.FULL_VOLS_BUTTON_XPATH);
        boolean fullVolsCompareLawTrackingButtonAppears = createWipVersionPage().isElementDisplayed(CreateWipVersionPageElements.FULL_VOLS_COMPARE_BUTTON_XPATH);
        boolean fullVolsRecompLawTrackingButtonAppears = createWipVersionPage().isElementDisplayed(CreateWipVersionPageElements.FULL_VOLS_RECOMP_BUTTON_XPATH);
        boolean submitButtonAppears = createWipVersionPage().isElementDisplayed(CreateWipVersionPageElements.SUBMIT_BUTTON_XPATH);
        boolean cancelButtonAppears = createWipVersionPage().isElementDisplayed(CreateWipVersionPageElements.CANCEL_BUTTON_XPATH);

        createWipVersionPage().clickQuickLoadButton();
        createWipVersionPage().clickSubmitButton();
        hierarchyNavigatePage().waitForHierarchyEditPageToLoad(nodeValue);

        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        boolean previousWipVersionsPageOpens = siblingMetadataContextMenu().viewModifyPreviousWIPVersion();

        previousWipVersionsPage().clickMostRecentWipVersion();
        String mostRecentWipVersionText = previousWipVersionsPage().getWipVersionOfSelectedWip();
        String mostRecentWipCreatedByUser = previousWipVersionsPage().getSelectedWipVersionsCreatedByText();
        String mostRecentWipCreatedDate = previousWipVersionsPage().getSelectedWipVersionsCreatedDate();
        String mostRecentWipLawTracking = previousWipVersionsPage().getSelectedWipVersionsLawTrackingText();
        previousWipVersionsPage().clickClose();

        boolean wipVersionContainsCreateWipVersion = mostRecentWipVersionText.contains("Create WIP Version");
        boolean wipVersionCreatedByNameIsLegalUser = mostRecentWipCreatedByUser.equals(legalUsername);
        boolean wipVersionCreatedDateEqualsCurrentDate = mostRecentWipCreatedDate.equals(currentDate);
        boolean wipVersionLawTrackingIsSetToQuickLoad = mostRecentWipLawTracking.equals("Quick Load");

        Connection uatHierarchyConnection = BaseDatabaseUtils.connectToDatabaseUAT();
        HierarchyDatabaseUtils.deleteLatestWipVersion(HierarchyDatabaseUtils.getContentUuidWithNodeUuid(nodeUuid, uatHierarchyConnection),uatHierarchyConnection);
        HierarchyDatabaseUtils.updateWipVersionToLatest(HierarchyDatabaseUtils.getContentUuidWithNodeUuid(nodeUuid, uatHierarchyConnection),uatHierarchyConnection);
        BaseDatabaseUtils.disconnect(uatHierarchyConnection);

        Assertions.assertAll
                (
                        () -> Assertions.assertTrue(c2012LawTrackingButtonAppears,"The C2012 Law Tracking button is displayed"),
                        () -> Assertions.assertTrue(fullVolsLawTrackingButtonAppears,"The Full Vols Law Tracking button is displayed"),
                        () -> Assertions.assertTrue(fullVolsCompareLawTrackingButtonAppears,"The Full Vols Compare Law Tracking button is displayed"),
                        () -> Assertions.assertTrue(fullVolsRecompLawTrackingButtonAppears,"The Full Vols Recomp Law Tracking button is displayed"),
                        () -> Assertions.assertTrue(c2012LawTrackingButtonAppears,"The C2012 Law Tracking button is displayed"),
                        () -> Assertions.assertTrue(submitButtonAppears,"The Submit button is displayed"),
                        () -> Assertions.assertTrue(cancelButtonAppears,"The Cancel button is displayed"),
                        () -> Assertions.assertTrue(previousWipVersionsPageOpens,"The Previous WIP Version page opens"),
                        () -> Assertions.assertTrue(wipVersionContainsCreateWipVersion,"The latest WIP version says 'Create WIP Version' in the WIP Version column"),
                        () -> Assertions.assertTrue(wipVersionCreatedByNameIsLegalUser,"The latest WIP version has a 'Created by' name of a legal username"),
                        () -> Assertions.assertTrue(wipVersionCreatedDateEqualsCurrentDate,"The latest WIP version has a 'Created date' of today"),
                        () -> Assertions.assertTrue(wipVersionLawTrackingIsSetToQuickLoad,"The latest WIP version has a 'Law tracking' status of 'Quick Load'")
                );
    }

    /**
     * STORY - CODESITEMS-28 <br>
     * SUMMARY - This test verifies that creating a wip version is unsuccessful after clicking the Submit button before a law tracking button and when clicking Cancel<br>
     * USER - LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void createWipVersionCancelAndSubmitWithoutLawTrackingTest()
    {
        datapodObject = TargetDataMockingNew.Iowa.Small.insert();
        String nodeUuid = datapodObject.getSections().get(0).getNodeUUID();
        String legalUsername = user().getUsername();
        String currentDate = DateAndTimeUtils.getCurrentDateMMddyyyy();

        homePage().goToHomePage();
        loginPage().logIn();

        hierarchyMenu().goToNavigate();

        hierarchySearchPage().searchNodeUuid(nodeUuid);
        String nodeValue = siblingMetadataPage().getSelectedNodeValue();
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().createWipVersion(false);

        boolean c2012LawTrackingButtonAppears = createWipVersionPage().isElementDisplayed(CreateWipVersionPageElements.C2012_LAW_TRACKING_BUTTON_XPATH);
        boolean fullVolsLawTrackingButtonAppears = createWipVersionPage().isElementDisplayed(CreateWipVersionPageElements.FULL_VOLS_BUTTON_XPATH);
        boolean fullVolsCompareLawTrackingButtonAppears = createWipVersionPage().isElementDisplayed(CreateWipVersionPageElements.FULL_VOLS_COMPARE_BUTTON_XPATH);
        boolean fullVolsRecompLawTrackingButtonAppears = createWipVersionPage().isElementDisplayed(CreateWipVersionPageElements.FULL_VOLS_RECOMP_BUTTON_XPATH);
        boolean submitButtonAppears = createWipVersionPage().isElementDisplayed(CreateWipVersionPageElements.SUBMIT_BUTTON_XPATH);
        boolean cancelButtonAppears = createWipVersionPage().isElementDisplayed(CreateWipVersionPageElements.CANCEL_BUTTON_XPATH);

        createWipVersionPage().clickSubmitButtonWithoutLawTrackingSelected();
        boolean errorMessageAppears = createWipVersionPage().isElementDisplayed(CreateWipVersionPageElements.PLEASE_SET_LAW_TRACKING_ERROR_MESSAGE_XPATH);

        createWipVersionPage().clickQuickLoadButton();
        createWipVersionPage().clickCancelButton();
        hierarchyNavigatePage().waitForHierarchyEditPageToLoad(nodeValue);

        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        boolean previousWipVersionsPageOpens = siblingMetadataContextMenu().viewModifyPreviousWIPVersion();

        previousWipVersionsPage().clickMostRecentWipVersion();
        String mostRecentWipVersionText = previousWipVersionsPage().getWipVersionOfSelectedWip();
        String mostRecentWipCreatedByUser = previousWipVersionsPage().getSelectedWipVersionsCreatedByText();
        String mostRecentWipCreatedDate = previousWipVersionsPage().getSelectedWipVersionsCreatedDate();
        String mostRecentWipLawTracking = previousWipVersionsPage().getSelectedWipVersionsLawTrackingText();
        previousWipVersionsPage().clickClose();

        boolean wipVersionContainsCreateWipVersion = mostRecentWipVersionText.contains("Create WIP Version");
        boolean wipVersionCreatedByNameIsLegalUser = mostRecentWipCreatedByUser.equals(legalUsername);
        boolean wipVersionCreatedDateEqualsCurrentDate = mostRecentWipCreatedDate.equals(currentDate);
        boolean wipVersionLawTrackingIsSetToQuickLoad = mostRecentWipLawTracking.equals("Quick Load");

        boolean latestWipVersionIsFromCreateWipVersion = wipVersionContainsCreateWipVersion && wipVersionCreatedByNameIsLegalUser && wipVersionCreatedDateEqualsCurrentDate && wipVersionLawTrackingIsSetToQuickLoad;

        Assertions.assertAll
                (
                        () -> Assertions.assertTrue(c2012LawTrackingButtonAppears,"The C2012 Law Tracking button is displayed"),
                        () -> Assertions.assertTrue(fullVolsLawTrackingButtonAppears,"The Full Vols Law Tracking button is displayed"),
                        () -> Assertions.assertTrue(fullVolsCompareLawTrackingButtonAppears,"The Full Vols Compare Law Tracking button is displayed"),
                        () -> Assertions.assertTrue(fullVolsRecompLawTrackingButtonAppears,"The Full Vols Recomp Law Tracking button is displayed"),
                        () -> Assertions.assertTrue(c2012LawTrackingButtonAppears,"The C2012 Law Tracking button is displayed"),
                        () -> Assertions.assertTrue(submitButtonAppears,"The Submit button is displayed"),
                        () -> Assertions.assertTrue(cancelButtonAppears,"The Cancel button is displayed"),
                        () -> Assertions.assertTrue(errorMessageAppears,"The error message is displayed after clicking Submit before selecting a law tracking status"),
                        () -> Assertions.assertTrue(previousWipVersionsPageOpens,"The Previous WIP Version page opens"),
                        () -> Assertions.assertFalse(latestWipVersionIsFromCreateWipVersion,"The latest WIP version should not be a from a Create WIP Version from today by a legal user with a Quick Load law tracking status")
                );
    }

    /**
     * STORY - CODESITEMS-28 <br>
     * SUMMARY - This test verifies that creating a wip version for 3 nodes at once is successful and the new version can then be viewed in each of the node's View/Modify Previous Wip Versions page<br>
     * USER - LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void createMultipleWipVersionTest()
    {
        HierarchyDatapodConfiguration.getConfig().setSectionCount(3);
        datapodObject = TargetDataMockingNew.Iowa.Small.insert();
        String nodeUuid1 = datapodObject.getSections().get(0).getNodeUUID();
        String nodeUuid2 = datapodObject.getSections().get(0).getNodeUUID();
        String nodeUuid3 = datapodObject.getSections().get(0).getNodeUUID();

        uatConnection = CommonDataMocking.connectToDatabase(environmentTag);
        String nodeValue1 = HierarchyDatabaseUtils.getNodeValueByNodeUUID(nodeUuid1, ContentSets.IOWA_DEVELOPMENT.getCode(), uatConnection);
        String nodeValue2 = HierarchyDatabaseUtils.getNodeValueByNodeUUID(nodeUuid2, ContentSets.IOWA_DEVELOPMENT.getCode(), uatConnection);
        String nodeValue3 = HierarchyDatabaseUtils.getNodeValueByNodeUUID(nodeUuid3, ContentSets.IOWA_DEVELOPMENT.getCode(), uatConnection);

        String[] nodeValues = {nodeValue1, nodeValue2, nodeValue3};
        String legalUsername = user().getUsername();
        String currentDate = DateAndTimeUtils.getCurrentDateMMddyyyy();

        homePage().goToHomePage();
        loginPage().logIn();

        hierarchyMenu().goToNavigate();

        hierarchySearchPage().searchNodeUuid(nodeUuid1);
        siblingMetadataPage().selectNodes(nodeValues);
        siblingMetadataPage().rightClickMultipleSelectedSiblingMetadata();
        siblingMetadataContextMenu().createWipVersion(false);

        boolean c2012LawTrackingButtonAppears = createWipVersionPage().isElementDisplayed(CreateWipVersionPageElements.C2012_LAW_TRACKING_BUTTON_XPATH);
        boolean fullVolsLawTrackingButtonAppears = createWipVersionPage().isElementDisplayed(CreateWipVersionPageElements.FULL_VOLS_BUTTON_XPATH);
        boolean fullVolsCompareLawTrackingButtonAppears = createWipVersionPage().isElementDisplayed(CreateWipVersionPageElements.FULL_VOLS_COMPARE_BUTTON_XPATH);
        boolean fullVolsRecompLawTrackingButtonAppears = createWipVersionPage().isElementDisplayed(CreateWipVersionPageElements.FULL_VOLS_RECOMP_BUTTON_XPATH);
        boolean submitButtonAppears = createWipVersionPage().isElementDisplayed(CreateWipVersionPageElements.SUBMIT_BUTTON_XPATH);
        boolean cancelButtonAppears = createWipVersionPage().isElementDisplayed(CreateWipVersionPageElements.CANCEL_BUTTON_XPATH);

        createWipVersionPage().clickQuickLoadButton();
        createWipVersionPage().clickSubmitButton();
        hierarchyNavigatePage().waitForHierarchyEditPageToLoad(nodeValues[0]);

        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        boolean previousWipVersionsPageOpens = siblingMetadataContextMenu().viewModifyPreviousWIPVersion();

        previousWipVersionsPage().clickMostRecentWipVersion();
        String mostRecentWipVersionText = previousWipVersionsPage().getWipVersionOfSelectedWip();
        String mostRecentWipCreatedByUser = previousWipVersionsPage().getSelectedWipVersionsCreatedByText();
        String mostRecentWipCreatedDate = previousWipVersionsPage().getSelectedWipVersionsCreatedDate();
        String mostRecentWipLawTracking = previousWipVersionsPage().getSelectedWipVersionsLawTrackingText();
        previousWipVersionsPage().clickClose();

        boolean wipVersionContainsCreateWipVersion = mostRecentWipVersionText.contains("Create WIP Version");
        boolean wipVersionCreatedByNameIsLegalUser = mostRecentWipCreatedByUser.equals(legalUsername);
        boolean wipVersionCreatedDateEqualsCurrentDate = mostRecentWipCreatedDate.equals(currentDate);
        boolean wipVersionLawTrackingIsSetToQuickLoad = mostRecentWipLawTracking.equals("Quick Load");

        siblingMetadataPage().selectNodes(nodeValues[1]);
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        boolean previousWipVersionsPageOpens2 = siblingMetadataContextMenu().viewModifyPreviousWIPVersion();

        previousWipVersionsPage().clickMostRecentWipVersion();
        String mostRecentWipVersionText2 = previousWipVersionsPage().getWipVersionOfSelectedWip();
        String mostRecentWipCreatedByUser2 = previousWipVersionsPage().getSelectedWipVersionsCreatedByText();
        String mostRecentWipCreatedDate2 = previousWipVersionsPage().getSelectedWipVersionsCreatedDate();
        String mostRecentWipLawTracking2 = previousWipVersionsPage().getSelectedWipVersionsLawTrackingText();
        previousWipVersionsPage().clickClose();

        boolean wipVersionContainsCreateWipVersion2 = mostRecentWipVersionText2.contains("Create WIP Version");
        boolean wipVersionCreatedByNameIsLegalUser2 = mostRecentWipCreatedByUser2.equals(legalUsername);
        boolean wipVersionCreatedDateEqualsCurrentDate2 = mostRecentWipCreatedDate2.equals(currentDate);
        boolean wipVersionLawTrackingIsSetToQuickLoad2 = mostRecentWipLawTracking2.equals("Quick Load");

        siblingMetadataPage().selectNodes(nodeValues[2]);
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        boolean previousWipVersionsPageOpens3 = siblingMetadataContextMenu().viewModifyPreviousWIPVersion();

        previousWipVersionsPage().clickMostRecentWipVersion();
        String mostRecentWipVersionText3 = previousWipVersionsPage().getWipVersionOfSelectedWip();
        String mostRecentWipCreatedByUser3 = previousWipVersionsPage().getSelectedWipVersionsCreatedByText();
        String mostRecentWipCreatedDate3 = previousWipVersionsPage().getSelectedWipVersionsCreatedDate();
        String mostRecentWipLawTracking3 = previousWipVersionsPage().getSelectedWipVersionsLawTrackingText();
        previousWipVersionsPage().clickClose();

        boolean wipVersionContainsCreateWipVersion3 = mostRecentWipVersionText3.contains("Create WIP Version");
        boolean wipVersionCreatedByNameIsLegalUser3 = mostRecentWipCreatedByUser3.equals(legalUsername);
        boolean wipVersionCreatedDateEqualsCurrentDate3 = mostRecentWipCreatedDate3.equals(currentDate);
        boolean wipVersionLawTrackingIsSetToQuickLoad3 = mostRecentWipLawTracking3.equals("Quick Load");

        Assertions.assertAll
                (
                        () -> Assertions.assertTrue(c2012LawTrackingButtonAppears, "The C2012 Law Tracking button is displayed"),
                        () -> Assertions.assertTrue(fullVolsLawTrackingButtonAppears, "The Full Vols Law Tracking button is displayed"),
                        () -> Assertions.assertTrue(fullVolsCompareLawTrackingButtonAppears, "The Full Vols Compare Law Tracking button is displayed"),
                        () -> Assertions.assertTrue(fullVolsRecompLawTrackingButtonAppears, "The Full Vols Recomp Law Tracking button is displayed"),
                        () -> Assertions.assertTrue(c2012LawTrackingButtonAppears, "The C2012 Law Tracking button is displayed"),
                        () -> Assertions.assertTrue(submitButtonAppears, "The Submit button is displayed"),
                        () -> Assertions.assertTrue(cancelButtonAppears, "The Cancel button is displayed"),
                        () -> Assertions.assertTrue(previousWipVersionsPageOpens, "The Previous WIP Version page opens for the first node"),
                        () -> Assertions.assertTrue(wipVersionContainsCreateWipVersion, "The latest WIP version says 'Create WIP Version' in the WIP Version column for the first node"),
                        () -> Assertions.assertTrue(wipVersionCreatedByNameIsLegalUser, "The latest WIP version has a 'Created by' name of a legal username for the first node"),
                        () -> Assertions.assertTrue(wipVersionCreatedDateEqualsCurrentDate, "The latest WIP version has a 'Created date' of today for the first node"),
                        () -> Assertions.assertTrue(wipVersionLawTrackingIsSetToQuickLoad, "The latest WIP version has a 'Law tracking' status of 'Quick Load' for the first node"),
                        () -> Assertions.assertTrue(previousWipVersionsPageOpens2, "The Previous WIP Version page opens for the second node"),
                        () -> Assertions.assertTrue(wipVersionContainsCreateWipVersion2, "The latest WIP version says 'Create WIP Version' in the WIP Version column for the second node"),
                        () -> Assertions.assertTrue(wipVersionCreatedByNameIsLegalUser2, "The latest WIP version has a 'Created by' name of a legal username for the second node"),
                        () -> Assertions.assertTrue(wipVersionCreatedDateEqualsCurrentDate2, "The latest WIP version has a 'Created date' of today for the second node"),
                        () -> Assertions.assertTrue(wipVersionLawTrackingIsSetToQuickLoad2, "The latest WIP version has a 'Law tracking' status of 'Quick Load' for the second node"),
                        () -> Assertions.assertTrue(previousWipVersionsPageOpens3, "The Previous WIP Version page opens for the third node"),
                        () -> Assertions.assertTrue(wipVersionContainsCreateWipVersion3, "The latest WIP version says 'Create WIP Version' in the WIP Version column for the third node"),
                        () -> Assertions.assertTrue(wipVersionCreatedByNameIsLegalUser3, "The latest WIP version has a 'Created by' name of a legal username for the third node"),
                        () -> Assertions.assertTrue(wipVersionCreatedDateEqualsCurrentDate3, "The latest WIP version has a 'Created date' of today for the third node"),
                        () -> Assertions.assertTrue(wipVersionLawTrackingIsSetToQuickLoad3, "The latest WIP version has a 'Law tracking' status of 'Quick Load' for the third node")
                );
    }

    /**
     * STORY - HALCYONST - 13241 <br>
     * SUMMARY - This test verifies that only one error message appears after clicking the Submit button multiple times before selecting any law tracking button<br>
     * USER - LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void createWipVersionSubmitWithoutLawTrackingSingleErrorMessageTest()
    {
        datapodObject = TargetDataMockingNew.Iowa.Small.insert();
        String nodeUuid = datapodObject.getSections().get(0).getNodeUUID();

        homePage().goToHomePage();
        loginPage().logIn();

        hierarchyMenu().goToNavigate();

        hierarchySearchPage().searchNodeUuid(nodeUuid);
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().createWipVersion(false);

        createWipVersionPage().clickSubmitButtonWithoutLawTrackingSelected();
        boolean errorMessageAppears = createWipVersionPage().isElementDisplayed(CreateWipVersionPageElements.PLEASE_SET_LAW_TRACKING_ERROR_MESSAGE_XPATH);
        Assertions.assertTrue(errorMessageAppears,"An error message should appear after clicking the Submit button once");

        createWipVersionPage().clickSubmitButtonWithoutLawTrackingSelected();
        createWipVersionPage().clickSubmitButtonWithoutLawTrackingSelected();

        boolean onlyOneErrorMessageStillAppears = createWipVersionPage().isOnlyOneErrorMessageDisplayed();
        Assertions.assertTrue(onlyOneErrorMessageStillAppears,"There should only be one error message when clicking the Submit button multiple times");
    }

    /**
     * STORY - HALCYONST - 13247 <br>
     * SUMMARY - This test verifies that that an alert appears after trying to Create WIP Version on a SET node (the first node in hierarchy tree)<br>
     * USER - LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void createWipVersionOnSetNodeTest()
    {
        homePage().goToHomePage();
        loginPage().logIn();

        hierarchyMenu().goToNavigate();
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().createWipVersion(true);

        boolean expectedAlertAppeared = AutoITUtils.verifyAlertTextAndAccept(true,"Unable to perform Create WIP Version on this document");
        Assertions.assertTrue(expectedAlertAppeared, "The expected alert did not appear.");
    }

    /**
     * STORY - CODESITEMS-28, HALCYONST-14079 <br>
     * SUMMARY - This test verifies that creating a wip version is unsuccessful after clicking the Submit button before a law tracking button and that it is successful after setting a law tracking status<br>
     * USER - LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void createWipVersionSubmitWithoutLawTrackingAndSubmitWithLawTrackingStatusLegalTest()
    {
        datapodObject = TargetDataMockingNew.Iowa.Small.insert();
        String nodeUuid = datapodObject.getSections().get(0).getNodeUUID();
        String legalUsername = user().getUsername();
        String currentDate = DateAndTimeUtils.getCurrentDateMMddyyyy();

        homePage().goToHomePage();
        loginPage().logIn();

        hierarchyMenu().goToNavigate();

        hierarchySearchPage().searchNodeUuid(nodeUuid);
        String nodeValue = siblingMetadataPage().getSelectedNodeValue();
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().createWipVersion(false);

        createWipVersionPage().clickSubmitButtonWithoutLawTrackingSelected();
        boolean errorMessageAppears = createWipVersionPage().isElementDisplayed(CreateWipVersionPageElements.PLEASE_SET_LAW_TRACKING_ERROR_MESSAGE_XPATH);

        createWipVersionPage().clickQuickLoadButton();
        createWipVersionPage().clickSubmitButton();
        hierarchyNavigatePage().waitForHierarchyEditPageToLoad(nodeValue);

        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        boolean previousWipVersionsPageOpens = siblingMetadataContextMenu().viewModifyPreviousWIPVersion();

        previousWipVersionsPage().clickMostRecentWipVersion();
        String mostRecentWipVersionText = previousWipVersionsPage().getWipVersionOfSelectedWip();
        String mostRecentWipCreatedByUser = previousWipVersionsPage().getSelectedWipVersionsCreatedByText();
        String mostRecentWipCreatedDate = previousWipVersionsPage().getSelectedWipVersionsCreatedDate();
        String mostRecentWipLawTracking = previousWipVersionsPage().getSelectedWipVersionsLawTrackingText();
        previousWipVersionsPage().clickClose();

        boolean wipVersionContainsCreateWipVersion = mostRecentWipVersionText.contains("Create WIP Version");
        boolean wipVersionCreatedByNameIsLegalUser = mostRecentWipCreatedByUser.equals(legalUsername);
        boolean wipVersionCreatedDateEqualsCurrentDate = mostRecentWipCreatedDate.equals(currentDate);
        boolean wipVersionLawTrackingIsSetToQuickLoad = mostRecentWipLawTracking.equals("Quick Load");

        boolean latestWipVersionIsFromCreateWipVersion = wipVersionContainsCreateWipVersion && wipVersionCreatedByNameIsLegalUser && wipVersionCreatedDateEqualsCurrentDate && wipVersionLawTrackingIsSetToQuickLoad;
        Assertions.assertAll
        (
                () -> Assertions.assertTrue(errorMessageAppears,"The error message is displayed after clicking Submit before selecting a law tracking status"),
                () -> Assertions.assertTrue(previousWipVersionsPageOpens,"The Previous WIP Version page opens"),
                () -> Assertions.assertTrue(latestWipVersionIsFromCreateWipVersion,"The latest WIP version should be a from a Create WIP Version from today by a legal user with a Quick Load law tracking status")
        );
    }

    @AfterEach
    public void cleanUp()
    {
        if(datapodObject != null)
        {
            datapodObject.delete();
        }
        BaseDatabaseUtils.disconnect(uatConnection);
    }
}