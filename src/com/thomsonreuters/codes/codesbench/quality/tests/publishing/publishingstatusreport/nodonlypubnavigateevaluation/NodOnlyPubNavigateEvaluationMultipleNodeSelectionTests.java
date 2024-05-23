package com.thomsonreuters.codes.codesbench.quality.tests.publishing.publishingstatusreport.nodonlypubnavigateevaluation;

import com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.publishing.toolbox.GridContextMenuElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.publishing.toolbox.GridElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.CITELINE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.EDGE;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.hierarchy.HierarchyDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.publishing.PublishingDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

public class NodOnlyPubNavigateEvaluationMultipleNodeSelectionTests extends TestService
{
    Connection connection;
    String contentSetIowa = ContentSets.IOWA_DEVELOPMENT.getCode();

    /**
     * STORY: Nod-only Pub Navigate Evaluation Multiple node selection Across parents positive test  <br>
     * SUMMARY: This test verifies that when multi selecting nodes that span across different parents, if the nodes within the selection are
     * not locked and do not have error flags, the first two context menu options(Select for updated status and remove updated status) on the publishing UIs should be enabled <br>
     * USER: Legal <br>
     * */
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void nodOnlyMultipleNodeSelectionAcrossParentsPositiveTest()
    {
        String childNodeOfParent1ContentUuid = "IEF8E0B601B8B11DC9EFD99CA72A5E33C";
        String childNodeOfParent2ContentUuid = "ID72ACCB01B8C11DCB9F3C3D5E8630D0B";
        String code = "TITLE VI";
        String targetValue1 = "4.5";
        String targetValue2 = "29";
        String volume = "0135";
        connection = BaseDatabaseUtils.connectToDatabaseUAT();

        // section node mock up
        PublishingDatabaseUtils.setPublishingNodeToPublishComplete(childNodeOfParent1ContentUuid, contentSetIowa, connection);
        String childNode1OfParentValue = HierarchyDatabaseUtils.getNodeValue(childNodeOfParent1ContentUuid, contentSetIowa, connection);
        PublishingDatabaseUtils.setPublishingNodeToPublishComplete(childNodeOfParent2ContentUuid, contentSetIowa, connection);
        String childNode2OfParentValue = HierarchyDatabaseUtils.getNodeValue(childNodeOfParent2ContentUuid, contentSetIowa, connection);

        homePage().goToHomePage();
        loginPage().logIn();

        boolean toolboxWindowAppeared = publishingMenu().goToPublishingStatusReportsNodOnlyPubNavigateEvaluation();
        Assertions.assertTrue(toolboxWindowAppeared, "NOD-only Pub Navigate Evaluation window did not appear and should have");
        gridPage().waitForGridLoaded();

        gridHeaderPage().openMenuForCodeColumn();
        gridHeaderFiltersPage().setFilterValue(code);
        gridHeaderPage().openMenuForTargetValueColumn();
        gridHeaderFiltersPage().setMultipleFilterValues(targetValue1, targetValue2);
        gridHeaderPage().openMenuForVolColumn();
        gridHeaderFiltersPage().setFilterValue(volume);

        gridPage().selectAllnodesBetweenTwoNodeTargetValueOnlyDiv(childNode1OfParentValue, childNode2OfParentValue);
        boolean isNode1Selected = gridPage().isElementSelected(String.format(GridElements.NODE_TARGET_VALUE_ONLY_DIV_XPATH + "/..", targetValue1));
        boolean isNode2Selected = gridPage().isElementSelected(String.format(GridElements.NODE_TARGET_VALUE_ONLY_DIV_XPATH+ "/..", targetValue2));
        Assertions.assertTrue(isNode1Selected, "Node 1 should be selected");
        Assertions.assertTrue(isNode2Selected, "Node 2 should be selected");

        gridPage().rightClickMultipleSelectedNodes();

        boolean selectForUpdatedStatusContextMenuOptionIsEnabled = gridContextMenu().isElementEnabled(GridContextMenuElements.SELECT_FOR_UPDATED_STATUS_XPATH);
        boolean removeSelectionContextMenuOptionIsEnabled = gridContextMenu().isElementEnabled(GridContextMenuElements.REMOVE_SELECTION_XPATH);

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(selectForUpdatedStatusContextMenuOptionIsEnabled, "On the Pub Navigate Evaluation UI, the Select For Updated Status context menu option is disabled and should be enabled"),
            () -> Assertions.assertTrue(removeSelectionContextMenuOptionIsEnabled, "On the Pub Navigate Evaluation UI, the Remove Selection context menu option is disabled and should be enabled")
        );
    }

    /**
     * STORY/BUG - NOD-Only Pub navigate Evaluation Mass Selection Functionality Test  <br>
     * SUMMARY - This test verifies that all the NOD-only sub context menu options appear under the 'Mass Selection' in Publishing UI<br>
     * USER - CITELINE <br>
     */
    @Test
    @EDGE
    @CITELINE
    @LOG
    public void nodOnlyMassSelectionMenuOptionsTest()
    {
        String nodeUuid = "I03C317A01BB511DC868395DC84A0FC63";
        String contentUuid = "I03CF25901BB511DC868395DC84A0FC63";
        String keyword = "BL ";
        connection = BaseDatabaseUtils.connectToDatabaseUAT();

        //Initialize nodes to a publishing status of 'Not Complete' to prevent errors when changing to 'Pub Complete'
        PublishingDatabaseUtils.setPublishingNodeToNotPublish(contentUuid,contentSetIowa, connection);

        //Grab name of node
        String nodeValue = HierarchyDatabaseUtils.getNodeValue(contentUuid, contentSetIowa, connection);

        homePage().goToHomePage();
        loginPage().logIn();

        //Set node to 'Pub Complete' status
        PublishingDatabaseUtils.setPublishingNodeToPublishComplete(contentUuid,contentSetIowa, connection);

        //Filter for the nodes we are checking
        publishingMenu().goToPublishingStatusReportsNodOnlyPubNavigateEvaluation();
        gridPage().waitForGridLoaded();
        gridHeaderPage().openMenuForNodeUuidColumn();
        gridHeaderFiltersPage().setMultipleFilterValues(nodeUuid);

        //verify each menu option is visible
        gridPage().rightClickByNodeTargetValueOnlyDiv(nodeValue);
        gridContextMenu().click(GridContextMenuElements.MASS_SELECTION_XPATH);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.THREE_SECONDS);
        boolean  doesSelectedForUpdatedAppear = gridContextMenu().isElementDisplayed(GridContextMenuElements.SELECT_FOR_UPDATED_STATUS_XPATH);
        boolean  doesRemoveSelectedAppear = gridContextMenu().isElementDisplayed(GridContextMenuElements.REMOVE_SELECTION_XPATH);
        gridContextMenu().click(GridContextMenuElements.MASS_SELECTION_SELECT_FOR_UPDATED_STATUS_XPATH);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.THREE_SECONDS);
        boolean  doesSelectForUpdatedAllDocsSameVolumeAppear = gridContextMenu().isElementDisplayed(GridContextMenuElements.MASS_SELECTION_All_DOCS_SAME_VOLUME_XPATH);
        boolean  doesSelectForUpdatedAllDocsSameCodeAppear = gridContextMenu().isElementDisplayed(GridContextMenuElements.MASS_SELECTION_ALL_DOCS_SAME_CODE_XPATH);
        gridContextMenu().click(GridContextMenuElements.MASS_SELECTION_REMOVE_SELECTION_XPATH);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.THREE_SECONDS);
        boolean  doesRemoveSelectionAllDocsSameVolumeAppear = gridContextMenu().isElementDisplayed(GridContextMenuElements.MASS_SELECTION_All_DOCS_SAME_VOLUME_XPATH);
        boolean  doesRemoveSelectionAllDocsSameCodeAppear = gridContextMenu().isElementDisplayed(GridContextMenuElements.MASS_SELECTION_ALL_DOCS_SAME_CODE_XPATH);

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(doesSelectedForUpdatedAppear, "Mass selection > Selected For Updated Status menu option appears"),
            () -> Assertions.assertTrue(doesSelectForUpdatedAllDocsSameVolumeAppear, "Mass selection > Selected For Updated Status > All Document in Same Volume menu option appears"),
            () -> Assertions.assertTrue(doesSelectForUpdatedAllDocsSameCodeAppear, "Mass selection > Selected For Updated Status > All Documents with Same Code menu option appears"),
            () -> Assertions.assertTrue(doesRemoveSelectedAppear, "Mass selection > Remove Selection menu option appears"),
            () -> Assertions.assertTrue(doesRemoveSelectionAllDocsSameVolumeAppear, "Mass selection > Remove Selection > All Document in Same Volume menu option appears"),
            () -> Assertions.assertTrue(doesRemoveSelectionAllDocsSameCodeAppear, "Mass selection > Remove Selection > All Documents with Same Code menu option appears")
        );
    }

    /**
     * STORY/BUG - NOD-Only Pub navigate Evaluation Mass Selection Functionality Test  <br>
     * SUMMARY - This test verifies that all the NOD-only sub context menu options under the 'Mass Selection' option are working correctly in each main Publishing UI<br>
     * USER - CITELINE <br>
     */
    @Test
    @EDGE
    @CITELINE
    @LOG
    public void nodOnlyMassSelectionFunctionalityTest()
    {
        String[] nodeUuids = {"I03C317A01BB511DC868395DC84A0FC63","I03F76E101BB511DC868395DC84A0FC63","IA97EAC201DA611DC8ADFB7755B0ACD62"};
        String[] contentUuids = {"I03CF25901BB511DC868395DC84A0FC63", "I040418401BB511DC868395DC84A0FC63", "IA98BF2901DA611DC8ADFB7755B0ACD62"};
        String keyword = "BL ";

        //Initialize nodes to a publishing status of 'Not Complete' to prevent errors when changing to 'Pub Complete'
        connection = BaseDatabaseUtils.connectToDatabaseUAT();

        PublishingDatabaseUtils.setPublishingNodeToNotPublish(contentUuids[0],contentSetIowa, connection);
        PublishingDatabaseUtils.setPublishingNodeToNotPublish(contentUuids[1],contentSetIowa, connection);
        PublishingDatabaseUtils.setPublishingNodeToNotPublish(contentUuids[2],contentSetIowa, connection);

        //Grab names of each node
        String nodeValue1 = HierarchyDatabaseUtils.getNodeValue(contentUuids[0], contentSetIowa, connection);
        String nodeValue2 = HierarchyDatabaseUtils.getNodeValue(contentUuids[1], contentSetIowa, connection);
        String nodeValue3 = HierarchyDatabaseUtils.getNodeValue(contentUuids[2], contentSetIowa, connection);

        //Set nodes to 'Pub Complete' status
        PublishingDatabaseUtils.setPublishingNodeToPublishComplete(contentUuids[0],contentSetIowa, connection);
        PublishingDatabaseUtils.setPublishingNodeToPublishComplete(contentUuids[1],contentSetIowa, connection);
        PublishingDatabaseUtils.setPublishingNodeToPublishComplete(contentUuids[2],contentSetIowa, connection);

        homePage().goToHomePage();
        loginPage().logIn();

        //Filter for the nodes we are checking
        publishingMenu().goToPublishingStatusReportsNodOnlyPubNavigateEvaluation();
        gridPage().waitForGridLoaded();
        mainHeaderPage().switchToPublishingStatusReportsPubNavigateEvaluation();
        gridHeaderPage().openMenuForNodeUuidColumn();
        gridHeaderFiltersPage().setMultipleFilterValues(nodeUuids);

        //Verify nodes in same volume highlighted green when Mass selection > Selected For Updated Status > All Document in Same Volume

//        gridPage().selectByNodeTargetValue(nodeValue1);
        gridPage().rightClickByNodeTargetValueOnlyDiv(nodeValue1);
        gridContextMenu().massSelectionSelectForUpdatedStatusAllDocumentsInSameVolume();
        boolean node1IsHighlightedGreenForSameVolume1 = gridPage().isBackgroundGreenForSelectedRow();
        gridPage().selectByNodeTargetValueOnlyDiv(nodeValue2);
        boolean node2IsHighlightedGreenForSameVolume1 = gridPage().isBackgroundGreenForSelectedRow();
        gridPage().selectByNodeTargetValueOnlyDiv(nodeValue3);
        boolean node3IsNotHighlightedGreenForSameVolume1 = gridPage().isBackgroundGreenForSelectedRow();

        //Verify nodes in same volume no longer highlighted green when Mass selection > Remove Selection > All Document in Same Volume
        gridPage().rightClickByNodeTargetValueOnlyDiv(nodeValue1);
        gridContextMenu().massSelectionRemoveSelectionAllDocumentsInSameVolume();
        boolean node1IsNotHighlightedGreenForSameVolume2 = gridPage().isBackgroundGreenForSelectedRow();
        gridPage().selectByNodeTargetValueOnlyDiv(nodeValue2);
        boolean node2IsNotHighlightedGreenForSameVolume2 = gridPage().isBackgroundGreenForSelectedRow();
        gridPage().selectByNodeTargetValueOnlyDiv(nodeValue3);
        boolean node3IsNotHighlightedGreenForSameVolume2 = gridPage().isBackgroundGreenForSelectedRow();

        //Verify nodes with same code are highlighted green when Mass selection > Selected For Updated Status > All Documents with Same Code
        gridPage().rightClickByNodeTargetValueOnlyDiv(nodeValue1);
        gridContextMenu().massSelectionSelectForUpdatedStatusAllDocumentsWithSameCode();
        boolean node1IsHighlightedGreenForSameCode3 = gridPage().isBackgroundGreenForSelectedRow();
        gridPage().selectByNodeTargetValueOnlyDiv(nodeValue2);
        boolean node2IsHighlightedGreenForSameCode3 = gridPage().isBackgroundGreenForSelectedRow();
        gridPage().selectByNodeTargetValueOnlyDiv(nodeValue3);
        boolean node3IsNotHighlightedGreenForSameCode3 = gridPage().isBackgroundGreenForSelectedRow();

        //Verify nodes with same code no longer highlighted green when Mass selection > Remove Selection > All Documents with Same Code
        gridPage().rightClickByNodeTargetValueOnlyDiv(nodeValue1);
        gridContextMenu().massSelectionRemoveSelectionAllDocumentsWithSameCode();
        boolean node1IsNotHighlightedGreenForSameCode4 = gridPage().isBackgroundGreenForSelectedRow();
        gridPage().selectByNodeTargetValueOnlyDiv(nodeValue2);
        boolean node2IsNotHighlightedGreenForSameCode4 = gridPage().isBackgroundGreenForSelectedRow();
        gridPage().selectByNodeTargetValueOnlyDiv(nodeValue3);
        boolean node3IsNotHighlightedGreenForSameCode4 = gridPage().isBackgroundGreenForSelectedRow();

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(node1IsHighlightedGreenForSameVolume1,"node 1 is highlighted green after after Mass selection > Selected For Updated Status > All Document in Same Volume"),
            () -> Assertions.assertTrue(node2IsHighlightedGreenForSameVolume1,"node 2 is highlighted green after Mass selection > Selected For Updated Status > All Document in Same Volume"),
            () -> Assertions.assertFalse(node3IsNotHighlightedGreenForSameVolume1,"node 3 is not highlighted green after Mass selection > Selected For Updated Status > All Document in Same Volume"),
            () -> Assertions.assertFalse(node1IsNotHighlightedGreenForSameVolume2,"node 1 is not highlighted green after Mass selection > Remove Selection > All Document in Same Volume"),
            () -> Assertions.assertFalse(node2IsNotHighlightedGreenForSameVolume2,"node 2 is not highlighted green after Mass selection > Remove Selection > All Document in Same Volume"),
            () -> Assertions.assertFalse(node3IsNotHighlightedGreenForSameVolume2,"node 3 is not highlighted green after Mass selection > Remove Selection > All Document in Same Volume"),
            () -> Assertions.assertTrue(node1IsHighlightedGreenForSameCode3,"node 1 is highlighted green after Mass selection > Selected For Updated Status > All Documents with Same Code"),
            () -> Assertions.assertTrue(node2IsHighlightedGreenForSameCode3,"node 2 is highlighted green after Mass selection > Selected For Updated Status > All Documents with Same Code"),
            () -> Assertions.assertFalse(node3IsNotHighlightedGreenForSameCode3,"node 3 is not highlighted green after Mass selection > Selected For Updated Status > All Documents with Same Code"),
            () -> Assertions.assertFalse(node1IsNotHighlightedGreenForSameCode4,"node 1 is not highlighted green after Mass selection > Remove Selection > All Documents with Same Code"),
            () -> Assertions.assertFalse(node2IsNotHighlightedGreenForSameCode4,"node 2 is not highlighted green after Mass selection > Remove Selection > All Documents with Same Code"),
            () -> Assertions.assertFalse(node3IsNotHighlightedGreenForSameCode4,"node 3 is not highlighted green after Mass selection > Remove Selection > All Documents with Same Code")
        );
    }

    @AfterEach
    public void closeConnection()
    {
        BaseDatabaseUtils.disconnect(connection);
    }
}
