package com.thomsonreuters.codes.codesbench.quality.tests.hierarchy;

import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.ParentageGraphPageElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.CommonDataMocking;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.TargetDataMockingNew;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.datapod.HierarchyDatapodConfiguration;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.datapod.HierarchyDatapodObject;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.autoIT.AutoITUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.hierarchy.HierarchyDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;

import java.sql.Connection;
import java.util.Arrays;
import java.util.List;

import static com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils.disconnect;


public class LockedNodesReportLegalTests extends TestService
{

    HierarchyDatapodObject datapodObject;
    Connection connection;

    /**
     * STORY/BUG - N/A <br>
     * SUMMARY - This test verifies that the prod user and the legal user both have the correct context menu options in the Locked Nodes Report page <br>
     * USER - PRODLEGAL/LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void lockedNodesReportUnlockLegalTest()
    {
        HierarchyDatapodConfiguration.getConfig().setSectionCount(2);
        datapodObject = TargetDataMockingNew.Iowa.Small.insert();
        List<String> legalUserLockContextMenu = Arrays.asList("View Content", "Edit Content", "Unlock", "View in Hierarchy");
        List<String> prodUserLockContextMenu = Arrays.asList("View Content", "Force Unlock", "Transfer Lock", "View in Hierarchy");
        String prodNodeUUID = datapodObject.getSections().get(0).getNodeUUID();
        String prodContentUUID = datapodObject.getSections().get(0).getContentUUID();
        String legalNodeUUID = datapodObject.getSections().get(1).getNodeUUID();
        String legalContentUUID = datapodObject.getSections().get(1).getContentUUID();
        String legalUserID = user().getUsername();
        String prodUser = "u" + prodLegalUser().getUsername();
        String contentSetId = ContentSets.IOWA_DEVELOPMENT.getCode();
        String todaysDate = DateAndTimeUtils.getCurentDateDDDashMMMDashYY();

        connection = CommonDataMocking.connectToDatabase(environmentTag);
        String prodlockUUID = CommonDataMocking.generateUUID();
        String prodNodeValue = HierarchyDatabaseUtils.getNodeValue(prodContentUUID, contentSetId, connection);
        String prodNodeVolume = HierarchyDatabaseUtils.getNodeVolumeWithContentUuid(prodContentUUID, contentSetId, connection);

        String legallockUUID = CommonDataMocking.generateUUID();
        String legalNodeValue = HierarchyDatabaseUtils.getNodeValue(legalContentUUID, contentSetId, connection);
        String legalNodeVolume = HierarchyDatabaseUtils.getNodeVolumeWithContentUuid(legalContentUUID, contentSetId, connection);

        homePage().goToHomePage();
        loginPage().logIn();

        HierarchyDatabaseUtils.insertNodeIntoTocNodeLockTable(prodlockUUID, prodNodeValue, 1, prodUser, todaysDate, 2, prodNodeVolume, prodNodeUUID, connection);
        HierarchyDatabaseUtils.insertNodeIntoLockedTocNodeTable(prodNodeUUID, prodlockUUID, connection);

        HierarchyDatabaseUtils.insertNodeIntoTocNodeLockTable(legallockUUID, legalNodeValue, 1, legalUserID, todaysDate, 2, legalNodeVolume, legalNodeUUID, connection);
        HierarchyDatabaseUtils.insertNodeIntoLockedTocNodeTable(legalNodeUUID, legallockUUID, connection);

        //Check that context menu options are correct
        hierarchyMenu().goToLockedNodesReport();
        lockedNodesReportPage().selectNodeByGivenUuid(legalNodeUUID);
        lockedNodesReportPage().rightClickSelectedNode();
        boolean allContextMenuOptionsAppear = lockedNodesReportContextMenu().areGivenContextMenuOptionsDisplayed(legalUserLockContextMenu);
        Assertions.assertTrue(allContextMenuOptionsAppear,"One or more context menu options were missing, or some appeared when they shouldn't have");

        //Unlock the legal user locked node
        lockedNodesReportContextMenu().clickUnlock();
        boolean expectedAlertAppeared = AutoITUtils.
                verifyAlertTextAndAccept(true,"Unlocking this document will cause all changes made by you to be lost.  Proceed with unlock?");
        Assertions.assertTrue(expectedAlertAppeared,"The expected alert didn't appear or wasn't accepted");

        lockedNodesReportPage().switchToLockedNodesReportPage();
        lockedNodesReportPage().clickRefresh();
        boolean lockedNodeWasLocked = lockedNodesReportPage().isNodeDisplayedByGivenUuid(legalNodeUUID);
        Assertions.assertFalse(lockedNodeWasLocked,"The legal user locked node does appear when it should not");

        //Check that the context menu options are correct
        lockedNodesReportPage().selectNodeByGivenUuid(prodNodeUUID);
        lockedNodesReportPage().rightClickSelectedNode();
        boolean allContextMenuOptionsAppear2 = lockedNodesReportContextMenu().areGivenContextMenuOptionsDisplayed(prodUserLockContextMenu);
        Assertions.assertTrue(allContextMenuOptionsAppear2,"One or more context menu options were missing, or some appeared when they shouldn't have");

        //Unlock the prod user locked node
        lockedNodesReportContextMenu().clickForceUnlock();
        boolean expectedAlertAppeared2 = AutoITUtils.verifyAlertTextAndAccept(true,"Unlocking this document will cause all changes made by the listed user to be lost.  Proceed with unlock?");
        Assertions.assertTrue(expectedAlertAppeared2,"The expected alert didn't appear or wasn't accepted");

        lockedNodesReportPage().switchToLockedNodesReportPage();
        lockedNodesReportPage().clickRefresh();
        boolean lockedNodeWasLocked2 = lockedNodesReportPage().isNodeDisplayedByGivenUuid(prodNodeUUID);
        Assertions.assertFalse(lockedNodeWasLocked2,"The prod user locked node does appear when it should not");
    }

    /**
     * STORY/BUG - N/A <br>
     * SUMMARY - This test verifies after locking a node, opening the Locked Nodes Report page, and selecting 'View in Hierarchy' in the context menu,
     * that the node appears in each pane of the Hierarchy Edit page <br>
     * USER - LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void lockedNodesReportViewInHierarchyLegalTest()
    {
        datapodObject = TargetDataMockingNew.Iowa.Small.insert();
        String legalNodeUUID = datapodObject.getSections().get(0).getNodeUUID();
        String legalContentUUID = datapodObject.getSections().get(0).getContentUUID();
        Connection connection = CommonDataMocking.connectToDatabase(environmentTag);
        String legalUserID = user().getUsername();
        String contentSetId = ContentSets.IOWA_DEVELOPMENT.getCode();
        String todaysDate = DateAndTimeUtils.getCurentDateDDDashMMMDashYY();
        String legallockUUID = CommonDataMocking.generateUUID();
        String legalNodeValue = HierarchyDatabaseUtils.getNodeValue(legalContentUUID, contentSetId, connection);
        String legalNodeVolume = HierarchyDatabaseUtils.getNodeVolumeWithContentUuid(legalContentUUID, contentSetId, connection);

        homePage().goToHomePage();
        loginPage().logIn();

        HierarchyDatabaseUtils.insertNodeIntoTocNodeLockTable(legallockUUID, legalNodeValue, 1, legalUserID, todaysDate, 2, legalNodeVolume, legalNodeUUID, connection);
        HierarchyDatabaseUtils.insertNodeIntoLockedTocNodeTable(legalNodeUUID, legallockUUID, connection);

        hierarchyMenu().goToLockedNodesReport();

        lockedNodesReportPage().selectNodeByGivenUuid(legalNodeUUID);
        lockedNodesReportPage().rightClickSelectedNode();
        lockedNodesReportContextMenu().clickViewInHierarchy();

        //Check that the node appears in each pane
        boolean metadataNodeAppears = siblingMetadataPage().isNodeDisplayedWithValue(legalNodeValue);
        boolean navTreeNodeAppears = hierarchyTreePage().isSelectedTreeNodeDisplayedByValue(legalNodeValue);
        boolean parentageGraphNodeAppears = parentageGraphPage().isElementDisplayed(ParentageGraphPageElements.PARENTAGE_GRAPH_IMAGE);
        boolean dispDerivNodeAppears = dispositionDerivationPage().isNodeValueDisplayed(legalNodeValue);

        //Unlock node nd check if successful
        hierarchyNavigatePage().closeCurrentWindowIgnoreDialogue();
        lockedNodesReportPage().switchToLockedNodesReportPage();
        lockedNodesReportPage().selectNodeByGivenUuid(legalNodeUUID);
        lockedNodesReportPage().rightClickSelectedNode();
        lockedNodesReportContextMenu().clickUnlock();
        boolean expectedAlertAppeared = AutoITUtils.
                verifyAlertTextAndAccept(true,"Unlocking this document will cause all changes made by you to be lost.  Proceed with unlock?");
        Assertions.assertTrue(expectedAlertAppeared,"The expected alert didn't appear or wasn't accepted");

        lockedNodesReportPage().switchToLockedNodesReportPage();
        lockedNodesReportPage().clickRefresh();
        boolean lockedNodeWasLocked = lockedNodesReportPage().isNodeDisplayedByGivenUuid(legalNodeUUID);

        Assertions.assertAll
                (
                        () -> Assertions.assertFalse(lockedNodeWasLocked,"The legal user locked node does appear when it should not"),
                        () -> Assertions.assertTrue(metadataNodeAppears,"The node with the given value doesn't appear in the sibling metadata pane when it should"),
                        () -> Assertions.assertTrue(navTreeNodeAppears,"The node with the given value doesn't appear in the navigation tree pane when it should"),
                        () -> Assertions.assertTrue(parentageGraphNodeAppears,"The node with the given value doesn't appear in the parentage graph pane when it should"),
                        () -> Assertions.assertTrue(dispDerivNodeAppears,"The node with the given value doesn't appear in the disposition/derivation pane when it should")
                );
    }

    @AfterEach
    public void cleanUp()
    {
        if(datapodObject != null)
        {
            datapodObject.delete();
        }
        if(connection != null)
        {
            disconnect(connection);
        }
    }
}
