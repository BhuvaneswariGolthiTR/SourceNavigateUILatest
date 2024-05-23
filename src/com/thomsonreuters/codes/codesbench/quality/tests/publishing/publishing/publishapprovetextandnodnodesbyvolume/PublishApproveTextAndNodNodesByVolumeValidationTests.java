package com.thomsonreuters.codes.codesbench.quality.tests.publishing.publishing.publishapprovetextandnodnodesbyvolume;

import com.thomsonreuters.codes.codesbench.quality.pageelements.publishing.toolbox.ToolbarElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.EDGE;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.hierarchy.HierarchyDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.publishing.PublishingDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.TargetDataMockingNew;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.datapod.HierarchyDatapodConfiguration;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.datapod.HierarchyDatapodObject;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class PublishApproveTextAndNodNodesByVolumeValidationTests extends TestService
{
    Connection uatConnection;
    String contentSetIowa = ContentSets.IOWA_DEVELOPMENT.getCode();
    HierarchyDatapodObject datapodObject;

    /**
     * STORY/BUG - HALCYONST-7156, HALCYONST-7874 <br>
     * SUMMARY - This test verifies when any one node within a range is selected and a user chooses Ready status or Approved status,
     * then all nodes within the range should be added to the Ready status or Approved status selection whether the user has them selected or not. <br>
     * USER - LEGAL <br>
     */
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void rangeRepealValidationTest()
    {
        String[] nodeValues = {"347.1", "347.2", "347.3", "347.4", "347.5", "347.6"};
        String nodeUuid = "I181C93C014F511DA8AC5CD53670E6B4E";
        String keyword = "= ";

        //add approver from approved list via database
        String contentSetId = ContentSets.IOWA_DEVELOPMENT.getCode();
        uatConnection = BaseDatabaseUtils.connectToDatabaseUAT();
        PublishingDatabaseUtils.checkAndAddPublishApproverForContentSet(user().getUsername(), user().getFirstname(), user().getLastname(), contentSetId, uatConnection);
        PublishingDatabaseUtils.setPublishingNodeToNotPublish(HierarchyDatabaseUtils.getContentUuidWithNodeUuid(nodeUuid, uatConnection), contentSetIowa, uatConnection);
        String nodeVolume = HierarchyDatabaseUtils.getNodeVolumeWithNodeUuid(nodeUuid, contentSetIowa, uatConnection);

        homePage().goToHomePage();
        loginPage().logIn();

        //Check that selected node is in a range,has a publishing status of 'Not Published', and set to 'Repeal'
        hierarchyMenu().goToNavigate();
        hierarchySearchPage().searchNodeUuid(nodeUuid);
        boolean nodeIsStartOfRange = siblingMetadataPage().isSelectedNodeTheStartOfARange();
        boolean nodeStatusIsSetToRepeal = siblingMetadataPage().isSelectedRowStatusRepeal();
        boolean publishStatusSetToNotPublish = siblingMetadataPage().isSelectedNodeStatusNotPublished();

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(nodeIsStartOfRange,"The node is not the starting node of the range when it should be"),
            () -> Assertions.assertTrue(nodeStatusIsSetToRepeal,"The node's status is not set to Repeal when it should be"),
            () -> Assertions.assertTrue(publishStatusSetToNotPublish,"The node's publishing status is not set to Not Published when it should be")
        );

        boolean toolboxWindowOpened = publishingMenu().goToPublishApproveTextAndNodNodesByVolume();
        Assertions.assertTrue(toolboxWindowOpened, "Publish Approve-Text and NOD nodes by volume Toolbox window was not opened");

        toolbarPage().clickVolumeSelection();
        volumeSelectionPage().clickCheckBoxForVolume(nodeVolume);
        volumeSelectionPage().clickAdd();
        volumeSelectionPage().clickConfirm();

        gridHeaderPage().openMenuForStatusColumn();
        gridHeaderFiltersPage().setFilterValue("Repeal");
        gridHeaderPage().openMenuForValueColumn();
        gridHeaderFiltersPage().setFilterValue(nodeValues[0]);

        //Right click node in range and click next
        gridPage().rightClickByNodeTargetValue(nodeValues[0]);
        gridContextMenu().approvedStatus();
        toolbarPage().clickNext();

        //Check that warning message appears and the rest of range nodes appears in grid
        boolean warningRMessageAppears = toolbarPage().isElementDisplayed(ToolbarElements.R_WARNING_XPATH);

        List<String> validationOfNodeIsSetToRList = new ArrayList<>();
        for(int i = 0; i < nodeValues.length; i++)
        {
            gridPage().selectByNodeTargetValue(nodeValues[i]);
            String validationText = gridPage().getValidationOfSelectedNode();
            validationOfNodeIsSetToRList.add(validationText);
        }

        boolean allMatchR = validationOfNodeIsSetToRList.stream().allMatch(validationItem -> validationItem.equals("R"));

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(warningRMessageAppears,"The warning message with title: 'Warning R' is not displayed when it should"),
            () -> Assertions.assertTrue(allMatchR,"all nodes validation are set to 'r'")
        );
    }

    /**
     * STORY/BUG - HALCYONST-9108 <br>
     * SUMMARY - This test verifies a prior version node of a selected node has a status of "Not Published" and should be identified as an expected node
     * and automatically be included on the submission page as part of the prior version validation with a version warning <br>
     * USER - LEGAL <br>
     */
    @Disabled
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void validationForPriorVersionsTest()
    {
        uatConnection = BaseDatabaseUtils.connectToDatabaseUAT();

        HierarchyDatapodConfiguration.getConfig().setNumberOfPastChapterVersions(1);
        datapodObject = TargetDataMockingNew.Iowa.Small.insert();
        String nodeUuid = datapodObject.getSections().get(0).getNodeUUID();
        String nodeValue = HierarchyDatabaseUtils.getNodeValue(HierarchyDatabaseUtils.getContentUuidWithNodeUuid(nodeUuid, uatConnection), contentSetIowa, uatConnection);

        //Check that the legal user has the necessary permissions
        PublishingDatabaseUtils.checkAndAddPublishApproverForContentSet(user().getUsername(), user().getFirstname(), user().getLastname(), contentSetIowa, uatConnection);
        String nodeVolume = HierarchyDatabaseUtils.getNodeVolumeWithNodeUuid(nodeUuid, contentSetIowa, uatConnection);

        homePage().goToHomePage();
        loginPage().logIn();

        boolean toolboxWindowAppeared = publishingMenu().goToPublishApproveTextAndNodNodesByVolume();
        Assertions.assertTrue(toolboxWindowAppeared, "Publish Approve-Text and NOD nodes by volume window did not appear and should have");

        toolbarPage().clickVolumeSelection();
        volumeSelectionPage().clickCheckBoxForVolume(nodeVolume);
        volumeSelectionPage().clickAdd();
        volumeSelectionPage().clickConfirm();

        gridHeaderPage().openMenuForValueColumn();
        gridHeaderFiltersPage().setFilterValue(nodeValue);

        gridPage().rightClickByNodeTargetValue(nodeValue);
        gridContextMenu().approvedStatus();
        toolbarPage().clickNext();

        //Check original node appears has "V." symbol and warning message appears
        boolean warningVMessageAppears = toolbarPage().isElementDisplayed(ToolbarElements.V_WARNING_XPATH);
        boolean originalNodeWasAdded = gridPaginationPage().getTotalNumberOfSelectableRows()>=2;
        boolean newNodeIsHighlightedGreen = gridPage().isBackgroundGreenForDeselectedLastRow();

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(warningVMessageAppears,"A warning message with the title 'Warning V' does not appear when it should"),
            () -> Assertions.assertTrue(originalNodeWasAdded,"The original node was not added to the submission page when it should have been"),
            () -> Assertions.assertTrue(newNodeIsHighlightedGreen,"The new node is not highlighted green when it should have")
        );
    }

    /**
     * STORY/BUG - HALCYONST-6100, HALCYONST-7874 <br>
     * SUMMARY - This test verifies that a not published section node that has a not published parent node when selecting approve status in the Publish Approve-Text and NOD nodes by volume UI
     * the parent node is also added with a parent warning message and that the validation of the parent node is set to 'P'. <br>
     * USER - LEGAL <br>
     */
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void parentageValidationTest()
    {
        uatConnection = BaseDatabaseUtils.connectToDatabaseUAT();

        HierarchyDatapodConfiguration.getConfig().setSectionCount(1);
        datapodObject = TargetDataMockingNew.Iowa.Small.insert();
        String sectionNodeUuid = datapodObject.getSections().get(0).getNodeUUID();
        String chapterNodeUuid = datapodObject.getAllNodes().get(2).getNodeUUID();

        String sectionNodeValue = HierarchyDatabaseUtils.getNodeValueByNodeUUID(sectionNodeUuid, contentSetIowa, uatConnection);
        String chapterNodeValue = HierarchyDatabaseUtils.getNodeValueByNodeUUID(chapterNodeUuid, contentSetIowa, uatConnection);

        homePage().goToHomePage();
        loginPage().logIn();

        boolean toolboxWindowOpened = publishingMenu().goToPublishApproveTextAndNodNodesByVolume();
        Assertions.assertTrue(toolboxWindowOpened, "Publish Approve-Text and NOD nodes by volume Toolbox window was not opened");

        toolbarPage().clickVolumeSelection();
        volumeSelectionPage().clickCheckBoxForVolume("9999");
        volumeSelectionPage().clickAdd();
        volumeSelectionPage().clickConfirm();

        gridPage().rightClickByNodeTargetValue(sectionNodeValue);
        gridContextMenu().approvedStatus();
        toolbarPage().clickNext();

        gridPage().selectByNodeTargetValue(chapterNodeValue);
        String validationText = gridPage().getValidationOfSelectedNode();
        boolean pValidationAppears = validationText.equals("P");
        boolean pWarningMessageAppears = toolbarPage().isElementDisplayed(ToolbarElements.P_WARNING_MESSAGE_XPATH);

        Assertions.assertAll
                (
                        () -> Assertions.assertTrue(pValidationAppears,"The selected chapter node should have a 'P' symbol in the validation column but does not"),
                        () -> Assertions.assertTrue(pWarningMessageAppears,"The 'Warning: P' message does not appear at the top of the Publishing UI when it should")
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
