package com.thomsonreuters.codes.codesbench.quality.tests.publishing.publishing.publishapprovetextandnodnodesbyvolume;

import com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.publishing.toolbox.GridContextMenuElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.publishing.toolbox.ToolbarElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.EDGE;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.hierarchy.HierarchyDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.publishing.PublishingDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.TargetDataMockingNew;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.datapod.HierarchyDatapodConfiguration;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.datapod.HierarchyDatapodObject;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.Arrays;
import java.util.List;

public class PublishApproveTextAndNodNodesByVolumeDisplayTests extends TestService
{
    Connection uatConnection;
    String contentSetIowa = ContentSets.IOWA_DEVELOPMENT.getCode();
    HierarchyDatapodObject datapodObject;

    @AfterEach
    public void closeConnection()
    {
        PublishingDatabaseUtils.checkAndAddPublishApproverForContentSet(user().getUsername(), user().getFirstname(), user().getLastname(), contentSetIowa, uatConnection);
        BaseDatabaseUtils.disconnect(uatConnection);
    }

    /**
     * STORY: HALCYONST-7919 <br>
     * SUMMARY: This test verifies that a user who is not on the publish approvers list and does not have SWAT access should not see the Next button on
     * the Publish Approve-Text and NOD nodes By Volume Toolbox page <br>
     * USER: Legal <br>
     */
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void nextButtonAccessForNonSwatUserAndNotPublishApproverTest()
    {
        uatConnection = BaseDatabaseUtils.connectToDatabaseUAT();
        PublishingDatabaseUtils.checkAndRemovePublishApproverForContentSet(user().getUsername(), user().getFirstname(), user().getLastname(), contentSetIowa, uatConnection);

        homePage().goToHomePage();
        loginPage().logIn();

        boolean toolboxWindowOpened = publishingMenu().goToPublishApproveTextAndNodNodesByVolume();
        Assertions.assertTrue(toolboxWindowOpened, "Publish Approve-Text and NOD nodes By Volume Toolbox window opened");

        boolean nextButtonIsDisplayed = toolbarPage().isElementDisplayed(ToolbarElements.NEXT);
        Assertions.assertFalse(nextButtonIsDisplayed, "Next button is displayed on toolbox for a non swat user page when it should not");
    }

    /**
     * STORY/BUG - Halcyonst-14535 <br>
     * SUMMARY - This test selects multiple volumes then clicks cancel and verifies they don't appear in the Publish Approve-Text and NOD nodes By Volume grid <br>
     * USER - LEGAL <br>
     */
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void multipleVolumeSelectionCancelTest()
    {
        uatConnection = BaseDatabaseUtils.connectToDatabaseUAT();

        String[] contentUuid = {"I1D7B2A301AFD11DAB310FB76B2E4F553", "I1EEC51F01AF311DAB310FB76B2E4F553", "I463476F01B0911DAB311FB76B2E4F553"};
        String[] value = {HierarchyDatabaseUtils.getNodeValue(contentUuid[0], contentSetIowa, uatConnection),
                HierarchyDatabaseUtils.getNodeValue(contentUuid[1], contentSetIowa, uatConnection),
                HierarchyDatabaseUtils.getNodeValue(contentUuid[2], contentSetIowa, uatConnection)};
        List<String> volumes = Arrays.asList(HierarchyDatabaseUtils.getNodeVolumeWithContentUuid(contentUuid[0], contentSetIowa, uatConnection),
                HierarchyDatabaseUtils.getNodeVolumeWithContentUuid(contentUuid[1], contentSetIowa, uatConnection),
                HierarchyDatabaseUtils.getNodeVolumeWithContentUuid(contentUuid[2], contentSetIowa, uatConnection));

        PublishingDatabaseUtils.setPublishingNodeToNotPublish(contentUuid[0], contentSetIowa, uatConnection);
        PublishingDatabaseUtils.setPublishingNodeToNotPublish(contentUuid[1], contentSetIowa, uatConnection);
        PublishingDatabaseUtils.setPublishingNodeToNotPublish(contentUuid[2], contentSetIowa, uatConnection);

        homePage().goToHomePage();
        loginPage().logIn();

        boolean publishingPageLoaded = publishingMenu().goToPublishApproveTextAndNodNodesByVolume();
        Assertions.assertTrue(publishingPageLoaded, "Publish Approve-Text and NOD nodes By Volume Toolbox window opened");

        toolbarPage().clickVolumeSelection();
        volumeSelectionPage().clickCheckBoxForVolume(volumes.get(0));
        volumeSelectionPage().clickCheckBoxForVolume(volumes.get(1));
        volumeSelectionPage().clickCheckBoxForVolume(volumes.get(2));
        volumeSelectionPage().clickAdd();
        boolean validateVolumesAdded = volumeSelectionPage().doVolumesAppearInSelectedVolumes(volumes);
        Assertions.assertTrue(validateVolumesAdded, "All volumes should be added to selected volumes column but were not");
        volumeSelectionPage().clickCancel();

        boolean isGridEmpty = gridPage().isNodeHierarchyColumnValueInGrid("= " + value[0]) &&
                gridPage().isNodeHierarchyColumnValueInGrid("= " + value[1]) &&
                gridPage().isNodeHierarchyColumnValueInGrid("= " + value[2]);
        Assertions.assertFalse(isGridEmpty,"The grid is empty after clicking cancel");
    }

    /**
     * STORY/BUG - Halcyonst-14535 <br>
     * SUMMARY - This test selects multiple volumes and verifies they appear in the Publish Approve-Text and NOD nodes By Volume grid <br>
     * USER - LEGAL <br>
     */
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void multipleVolumeSelectionTest()
    {
        uatConnection = BaseDatabaseUtils.connectToDatabaseUAT();

        String[] contentUuid = {"I1D7B2A301AFD11DAB310FB76B2E4F553", "I1EEC51F01AF311DAB310FB76B2E4F553", "I463476F01B0911DAB311FB76B2E4F553"};
        String[] value = {HierarchyDatabaseUtils.getNodeValue(contentUuid[0], contentSetIowa, uatConnection),
                HierarchyDatabaseUtils.getNodeValue(contentUuid[1], contentSetIowa, uatConnection),
                HierarchyDatabaseUtils.getNodeValue(contentUuid[2], contentSetIowa, uatConnection)};
        List<String> volumes = Arrays.asList(HierarchyDatabaseUtils.getNodeVolumeWithContentUuid(contentUuid[0], contentSetIowa, uatConnection),
                HierarchyDatabaseUtils.getNodeVolumeWithContentUuid(contentUuid[1], contentSetIowa, uatConnection),
                HierarchyDatabaseUtils.getNodeVolumeWithContentUuid(contentUuid[2], contentSetIowa, uatConnection));
        List<String> volumesWithoutLast = Arrays.asList(volumes.get(0), volumes.get(1));

        PublishingDatabaseUtils.setPublishingNodeToNotPublish(contentUuid[0], contentSetIowa, uatConnection);
        PublishingDatabaseUtils.setPublishingNodeToNotPublish(contentUuid[1], contentSetIowa, uatConnection);
        PublishingDatabaseUtils.setPublishingNodeToNotPublish(contentUuid[2], contentSetIowa, uatConnection);

        homePage().goToHomePage();
        loginPage().logIn();

        boolean publishingPageLoaded = publishingMenu().goToPublishApproveTextAndNodNodesByVolume();
        Assertions.assertTrue(publishingPageLoaded, "Publish Approve-Text and NOD nodes By Volume Toolbox window opened");

        toolbarPage().clickVolumeSelection();
        volumeSelectionPage().clickCheckBoxForVolume(volumes.get(0));
        volumeSelectionPage().clickCheckBoxForVolume(volumes.get(1));
        volumeSelectionPage().clickCheckBoxForVolume(volumes.get(2));
        volumeSelectionPage().clickAdd();
        volumeSelectionPage().clickCheckBoxForVolumeSelectedVolumes(volumes.get(2));
        volumeSelectionPage().clickRemove();

        boolean nodeMovedToAvailableVolumes = volumeSelectionPage().doVolumesAppearInSelectedVolumes(volumesWithoutLast);
        Assertions.assertTrue(nodeMovedToAvailableVolumes,"The removed volume should appear in the available volumes column after clicking cancel");

        volumeSelectionPage().clickConfirm();

        gridHeaderPage().openMenuForValueColumn();
        gridHeaderFiltersPage().setMultipleFilterValues(value);
        boolean isVolume1NodeInGrid = gridPage().isNodeHierarchyColumnValueInGrid("= " + value[0]);
        boolean isVolume2NodeInGrid = gridPage().isNodeHierarchyColumnValueInGrid("= " + value[1]);
        boolean isVolume3NodeInGrid = gridPage().isNodeHierarchyColumnValueInGrid("= " + value[2]);

        toolbarPage().clickVolumeSelection();
        volumeSelectionPage().clickCheckBoxForVolume(volumes.get(0));
        volumeSelectionPage().clickCheckBoxForVolume(volumes.get(1));
        volumeSelectionPage().clickCheckBoxForVolume(volumes.get(2));
        volumeSelectionPage().clickAdd();
        volumeSelectionPage().clickConfirm();

        gridHeaderPage().openMenuForValueColumn();
        gridHeaderFiltersPage().setMultipleFilterValues(value);
        boolean isVolume1NodeInGridAfterUpdate = gridPage().isNodeHierarchyColumnValueInGrid("= " + value[0]);
        boolean isVolume2NodeInGridAfterUpdate = gridPage().isNodeHierarchyColumnValueInGrid("= " + value[1]);
        boolean isVolume3NodeInGridAfterUpdate = gridPage().isNodeHierarchyColumnValueInGrid("= " + value[2]);

        Assertions.assertAll
        (
            ()-> Assertions.assertTrue(isVolume1NodeInGrid, "node From volume 1 should be in the grid"),
            ()-> Assertions.assertTrue(isVolume2NodeInGrid, "node From volume 2 should be in the grid"),
            ()-> Assertions.assertFalse(isVolume3NodeInGrid, "node From volume 3 should not be in the grid"),
            ()-> Assertions.assertTrue(isVolume1NodeInGridAfterUpdate, "node From volume 1 should be in the grid"),
            ()-> Assertions.assertTrue(isVolume2NodeInGridAfterUpdate, "node From volume 2 should be in the grid"),
            ()-> Assertions.assertTrue(isVolume3NodeInGridAfterUpdate, "node From volume 3 should be in the grid")
        );
    }

    /**
     * STORY: HALCYONST-6019, HALCYONST-6356, HALCYONST-6364, HALCYONST-6523, HALCYONST-6565, HALCYONST-8075 <br>
     * SUMMARY: Verifies that after finding nodes with the various flags in a publishing UI, the nodes should display with the correct flag
     * in hierarchy. Also verifies that after right clicking a node with a flag, the node should have the Approved Status context menu option disabled. <br>
     * USER: LEGAL <br>
     */
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void flagDisplayTest()
    {
        HierarchyDatapodConfiguration.getConfig().setSectionCount(4);
        datapodObject = TargetDataMockingNew.Iowa.Small.insert();

        String errorFlagContentUuid =  datapodObject.getSections().get(0).getContentUUID();
        String warningFlagContentUuid = datapodObject.getSections().get(1).getContentUUID();
        String infoFlagContentUuid = datapodObject.getSections().get(2).getContentUUID();
        String greenCheckFlagContentUuid = datapodObject.getSections().get(3).getContentUUID();

        List<String> flagColumnExpectedFilterValues = Arrays.asList("RED_FLAG", "YELLOW_FLAG", "BLUE_FLAG");

        uatConnection = BaseDatabaseUtils.connectToDatabaseUAT();

        PublishingDatabaseUtils.checkAndSetContentSetToBeEnabledForPublishing(contentSetIowa, uatConnection);

        // error flag node mockup
        PublishingDatabaseUtils.setPublishingNodeToNotPublish(errorFlagContentUuid, contentSetIowa, uatConnection);
        HierarchyDatabaseUtils.setNodeToErrorValidationFlag(errorFlagContentUuid, contentSetIowa, uatConnection);
        String errorNodeValue = HierarchyDatabaseUtils.getNodeValue(errorFlagContentUuid, contentSetIowa, uatConnection);

        // warning flag node mockup
        PublishingDatabaseUtils.setPublishingNodeToNotPublish(warningFlagContentUuid, contentSetIowa, uatConnection);
        HierarchyDatabaseUtils.setNodeToWarningValidationFlag(warningFlagContentUuid, contentSetIowa, uatConnection);
        String warningNodeValue = HierarchyDatabaseUtils.getNodeValue(warningFlagContentUuid, contentSetIowa, uatConnection);

        // info flag node mockup
        PublishingDatabaseUtils.setPublishingNodeToNotPublish(infoFlagContentUuid, contentSetIowa, uatConnection);
        HierarchyDatabaseUtils.setNodeToInfoValidationFlag(infoFlagContentUuid, contentSetIowa, uatConnection);
        String infoNodeValue = HierarchyDatabaseUtils.getNodeValue(infoFlagContentUuid, contentSetIowa, uatConnection);

        // green check flag mockup
        PublishingDatabaseUtils.setPublishingNodeToNotPublish(greenCheckFlagContentUuid, contentSetIowa, uatConnection);
        HierarchyDatabaseUtils.setNodeToGreenCheckValidationFlag(greenCheckFlagContentUuid, contentSetIowa, uatConnection);
        String greenCheckNodeValue = HierarchyDatabaseUtils.getNodeValue(greenCheckFlagContentUuid, contentSetIowa, uatConnection);

        homePage().goToHomePage();
        loginPage().logIn();

        boolean publishingPageLoaded = publishingMenu().goToPublishApproveTextAndNodNodesByVolume();
        Assertions.assertTrue(publishingPageLoaded, "Publish Approve-Text and NOD nodes By Volume Toolbox window opened");

        toolbarPage().clickVolumeSelection();
        volumeSelectionPage().clickCheckBoxForVolume("9999");
        volumeSelectionPage().clickAdd();
        volumeSelectionPage().clickConfirm();

        gridHeaderPage().openMenuForFlagColumn();
        gridHeaderFiltersPage().setMultipleFilterValues(flagColumnExpectedFilterValues.get(0), flagColumnExpectedFilterValues.get(1), flagColumnExpectedFilterValues.get(2));
        gridHeaderPage().openMenuForNodeHierarchyColumn();
        gridHeaderFiltersPage().setMultipleFilterValues(errorNodeValue, warningNodeValue, infoNodeValue);

        gridPage().rightClickByNodeTargetValue(errorNodeValue);
        boolean approvedStatusIsDisabledForNodeWithErrorValidationFlag = gridContextMenu().isElementDisabled(GridContextMenuElements.APPROVED_STATUS_XPATH);
        gridContextMenu().findDocumentInHierarchy();

        boolean broughtToErrorNodeAfterFindDocumentInHierarchy = siblingMetadataPage().getSelectedNodeValue().equals(errorNodeValue);
        Assertions.assertTrue(broughtToErrorNodeAfterFindDocumentInHierarchy, "Brought to Error node after Find Document in Hierarchy");

        boolean selectedNodeHasErrorValidationFlagInHierarchy = siblingMetadataPage().doesSelectedNodeHaveErrorValidationFlag();

        hierarchyNavigatePage().closeCurrentWindowIgnoreDialogue();
        mainHeaderPage().switchToPublishApproveTextAndNodNodesByVolumeWindow();

        gridPage().rightClickByNodeTargetValue(warningNodeValue);
        boolean approvedStatusIsDisabledForNodeWithWarningValidationFlag = gridContextMenu().isElementDisabled(GridContextMenuElements.APPROVED_STATUS_XPATH);
        gridContextMenu().findDocumentInHierarchy();

        boolean broughtToWarningNodeAfterFindDocumentInHierarchy = siblingMetadataPage().getSelectedNodeValue().equals(warningNodeValue);
        Assertions.assertTrue(broughtToWarningNodeAfterFindDocumentInHierarchy, "Brought to Warning node after Find Document in Hierarchy");

        boolean selectedNodeHasWarningValidationFlagInHierarchy = siblingMetadataPage().doesSelectedNodeHaveWarningValidationFlag();

        hierarchyNavigatePage().closeCurrentWindowIgnoreDialogue();
        mainHeaderPage().switchToPublishApproveTextAndNodNodesByVolumeWindow();

        gridPage().rightClickByNodeTargetValue(infoNodeValue);
        gridContextMenu().findDocumentInHierarchy();

        boolean broughtToInfoNodeAfterFindDocumentInHierarchy = siblingMetadataPage().getSelectedNodeValue().equals(infoNodeValue);
        Assertions.assertTrue(broughtToInfoNodeAfterFindDocumentInHierarchy, "Brought to Info node after Find Document in Hierarchy");

        boolean selectedNodeHasInfoValidationFlagInHierarchy = siblingMetadataPage().doesSelectedNodeHaveInfoValidationFlag();

        hierarchyNavigatePage().closeCurrentWindowIgnoreDialogue();
        mainHeaderPage().switchToPublishApproveTextAndNodNodesByVolumeWindow();

        toolbarPage().clickClearFiltersAndSorts();

        gridHeaderPage().openMenuForNodeHierarchyColumn();
        gridHeaderFiltersPage().setFilterValue(greenCheckNodeValue);

        gridPage().rightClickByNodeTargetValue(greenCheckNodeValue);
        gridContextMenu().findDocumentInHierarchy();

        boolean broughtToGreenCheckNodeAfterFindDocumentInHierarchy = siblingMetadataPage().getSelectedNodeValue().equals(greenCheckNodeValue);
        Assertions.assertTrue(broughtToGreenCheckNodeAfterFindDocumentInHierarchy, "Brought to Green check node after Find Document in Hierarchy");

        boolean selectedNodeHasGreenCheckValidationFlagInHierarchy = siblingMetadataPage().doesSelectedNodeHaveGreenCheckValidationFlag();

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(selectedNodeHasErrorValidationFlagInHierarchy, "The node with an Error validation flag in the publishing UI does not appear to have an Error flag in Hierarchy and should"),
            () -> Assertions.assertTrue(approvedStatusIsDisabledForNodeWithErrorValidationFlag, "Approved Status is not disabled for the node with an error flag and should be"),
            () -> Assertions.assertTrue(selectedNodeHasWarningValidationFlagInHierarchy, "The node with a Warning validation flag in the publishing UI does not appear to have a Warning flag in Hierarchy and should"),
            () -> Assertions.assertTrue(approvedStatusIsDisabledForNodeWithWarningValidationFlag, "Approved Status is not disabled for the node with a warning flag and should be"),
            () -> Assertions.assertTrue(selectedNodeHasInfoValidationFlagInHierarchy, "The node with an Info validation flag in the publishing UI does not appear to have an Info flag in Hierarchy and should"),
            () -> Assertions.assertTrue(selectedNodeHasGreenCheckValidationFlagInHierarchy, "The node with no flag in the publishing UI does not appear to have a Green check flag in Hierarchy and should")
        );
    }

    @AfterEach
    public void cleanUp()
    {
        if (datapodObject != null)
        {
            datapodObject.delete();
        }
        if(uatConnection != null)
        {
            BaseDatabaseUtils.disconnect(uatConnection);
        }
    }
}

