package com.thomsonreuters.codes.codesbench.quality.tests.hierarchy;

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
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.RISK;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.Arrays;
import java.util.List;

public class LockedNodesReportGRCTests extends TestService
{
    HierarchyDatapodObject datapodObject;

    /**
     * STORY/BUG - JETS-11150 / JETS-13258 <br>
     * SUMMARY - This test verifies that the context menu options are correct in the Locked Nodes Report page
     * for a node locked by a risk user and for a node not locked by a risk user <br>
     * USER - RISK <br>
     */
    @Test
    @IE_EDGE_MODE
    @RISK
    @LOG
    public void lockedNodesReportUnlockGRCTest()
    {
        String lockUUID = CommonDataMocking.generateUUID();
        String contentSetId = ContentSets.CROWN_DEPENDENCIES.getCode();
        String todaysDate = DateAndTimeUtils.getCurentDateDDDashMMMDashYY();

        List<String> sameUserLockContextMenu = Arrays.asList("View Content", "Edit Content", "Unlock", "View in Hierarchy");
        List<String> differentUserLockContextMenu = Arrays.asList("View Content", "Force Unlock", "Transfer Lock", "View in Hierarchy");
        String riskUser = user().getUsername();
        String prodLegalUser = prodLegalUser().getUsername();

        HierarchyDatapodConfiguration.getConfig().loadDefaultRiskConfig();
        datapodObject = TargetDataMockingNew.CrownDependencies.Small.insert();
        String nodeUUID = datapodObject.getSections().get(0).getNodeUUID();
        String contentUUID = datapodObject.getSections().get(0).getContentUUID();

        Connection connection = CommonDataMocking.connectToDatabase(environmentTag);
        String nodeValue = HierarchyDatabaseUtils.getNodeValue(contentUUID, contentSetId, connection);
        String nodeVolume = HierarchyDatabaseUtils.getNodeVolumeWithContentUuid(contentUUID, contentSetId, connection);
        HierarchyDatabaseUtils.insertNodeIntoTocNodeLockTable(lockUUID, nodeValue, 1, riskUser, todaysDate, 2, nodeVolume, nodeUUID, connection);
        HierarchyDatabaseUtils.insertNodeIntoLockedTocNodeTable(nodeUUID, lockUUID, connection);
        BaseDatabaseUtils.disconnect(connection);

        homePage().goToHomePage();
        loginPage().logIn();

        //Check that context menu options are correct for a risk user locked node
        hierarchyMenu().goToLockedNodesReport();
        lockedNodesReportPage().selectNodeByGivenUuid(nodeUUID);
        lockedNodesReportPage().rightClickSelectedNode();
        boolean allContextMenuOptionsAppear = lockedNodesReportContextMenu().areGivenContextMenuOptionsDisplayed(sameUserLockContextMenu);

        //Unlock the node locked by risk user
        lockedNodesReportContextMenu().clickUnlock();
        boolean expectedAlertAppeared = AutoITUtils.
                verifyAlertTextAndAccept(true,"Unlocking this document will cause all changes made by you to be lost.  Proceed with unlock?");
        Assertions.assertTrue(expectedAlertAppeared,"The expected alert didn't appear or wasn't accepted");

        lockedNodesReportPage().switchToLockedNodesReportPage();
        boolean lockedNodeWasLocked = lockedNodesReportPage().isNodeDisplayedByGivenUuid(nodeUUID);
        Assertions.assertFalse(lockedNodeWasLocked,"The risk user locked node does appear when it should not");

        //Check that the context menu options are correct for a different user locked node
        //We create a lock in the table with the prod legal user account
        connection = CommonDataMocking.connectToDatabase(environmentTag);
        HierarchyDatabaseUtils.insertNodeIntoTocNodeLockTable(lockUUID, nodeValue, 1, prodLegalUser, todaysDate, 2, nodeVolume, nodeUUID, connection);
        HierarchyDatabaseUtils.insertNodeIntoLockedTocNodeTable(nodeUUID, lockUUID, connection);
        BaseDatabaseUtils.disconnect(connection);

        lockedNodesReportPage().switchToLockedNodesReportPage();
        lockedNodesReportPage().clickRefresh();

        lockedNodesReportPage().selectNodeByGivenUuid(nodeUUID);
        lockedNodesReportPage().rightClickSelectedNode();
        boolean allContextMenuOptionsAppear2 = lockedNodesReportContextMenu().areGivenContextMenuOptionsDisplayed(differentUserLockContextMenu);
        lockedNodesReportContextMenu().clickForceUnlock();
        boolean expectedAlertAppearedOtherUser = AutoITUtils.
                verifyAlertTextAndAccept(true,"Unlocking this document will cause all changes made by the listed user to be lost.  Proceed with unlock?");
        Assertions.assertTrue(expectedAlertAppearedOtherUser,"The expected alert didn't appear or wasn't accepted for a lock by another user");

        lockedNodesReportPage().switchToLockedNodesReportPage();
        boolean lockedNodeWasLocked2 = lockedNodesReportPage().isNodeDisplayedByGivenUuid(nodeUUID);
        Assertions.assertFalse(lockedNodeWasLocked2,"The other user locked node does appear when it should not");

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(allContextMenuOptionsAppear,"One or more context menu options were missing, or some appeared when they shouldn't have"),
            () -> Assertions.assertTrue(allContextMenuOptionsAppear2,"One or more context menu options were missing, or no locked nodes from other users appeared")
        );
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