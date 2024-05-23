package com.thomsonreuters.codes.codesbench.quality.tests.publishing.publishing.publishapprovetextandnodnodesbyvolume;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;

import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.EDGE;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.hierarchy.HierarchyDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.publishing.PublishingDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

public class PublishApproveTextAndNodNodesByVolumeDateFilteringTests extends TestService
{
    Connection uatConnection;
    String contentSetIowa = ContentSets.IOWA_DEVELOPMENT.getCode();

    @AfterEach
    public void closeConnection()
    {
        BaseDatabaseUtils.disconnect(uatConnection);
    }

    /**
     * STORY: HALCYONST-12924 <br>
     * SUMMARY: This test verifies the date filtering correctly reflects in the Publish Approve-Text and NOD nodes by volume Toolbox grid <br>
     * USER: Legal <br>
     */
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void publishingUiDateFilteringTest()
    {
        uatConnection = BaseDatabaseUtils.connectToDatabaseUAT();

        String contentUuid1 = "I7FDEA5C00E2C11DCB2009220F1CF0138";
        //get the original values for clean up of node 1
        String nodeValue1 = HierarchyDatabaseUtils.getNodeValue(contentUuid1, contentSetIowa, uatConnection);
        String nodeVolume1 = HierarchyDatabaseUtils.getNodeVolumeWithContentUuid(contentUuid1, contentSetIowa, uatConnection);

        String originalStartDate1 = "31-Jul-07";
        String originalEndDate1 =  "";
        String originalModifiedDate1 = "";

        String contentUuid2 = "I157099001B0811DAB311FB76B2E4F553";
        //get the original values for clean up of node 2
        String nodeValue2 = HierarchyDatabaseUtils.getNodeValue(contentUuid2, contentSetIowa, uatConnection);
        String nodeVolume2 = HierarchyDatabaseUtils.getNodeVolumeWithContentUuid(contentUuid2, contentSetIowa, uatConnection);

        String originalStartDate2 = "31-Dec-05";
        String originalEndDate2 = "";
        String originalModifiedDate2 = "";

        String contentUuid3 = "I7A7356A0FD6E11DA92F6AA1B87662755";
        //get the original values for clean up of node 3
        String nodeValue3 =  HierarchyDatabaseUtils.getNodeValue(contentUuid3, contentSetIowa, uatConnection);
        String nodeVolume3 = HierarchyDatabaseUtils.getNodeVolumeWithContentUuid(contentUuid3, contentSetIowa, uatConnection);

        String originalStartDate3 = "01-Jul-06";
        String originalEndDate3 = "";
        String originalModifiedDate3 = "";

        try
        {
            homePage().goToHomePage();
            loginPage().logIn();

            //set to our values to make sure test always stays the same
            //mock up for node 1 -
            PublishingDatabaseUtils.setPublishingNodeToNotPublish(contentUuid1, contentSetIowa, uatConnection);
            HierarchyDatabaseUtils.updateLegisStartEffectiveDate(contentUuid1, "11-Sep-01", uatConnection);
            HierarchyDatabaseUtils.updateLegisEndEffectiveDate(contentUuid1, "21-Dec-12", uatConnection);
            HierarchyDatabaseUtils.updateModifiedDate(contentUuid1, "21-Dec-12", uatConnection);

            //mock up for node 2-
            PublishingDatabaseUtils.setPublishingNodeToNotPublish(contentUuid2, contentSetIowa, uatConnection);
            HierarchyDatabaseUtils.updateLegisStartEffectiveDate(contentUuid2, "20-Jan-20", uatConnection);
            HierarchyDatabaseUtils.updateLegisEndEffectiveDate(contentUuid2, "25-May-20", uatConnection);
            HierarchyDatabaseUtils.updateModifiedDate(contentUuid2, "25-May-20", uatConnection);

            //mock up for node 3-
            PublishingDatabaseUtils.setPublishingNodeToNotPublish(contentUuid3, contentSetIowa, uatConnection);
            HierarchyDatabaseUtils.updateLegisStartEffectiveDate(contentUuid3, "25-May-08", uatConnection);
            HierarchyDatabaseUtils.updateLegisEndEffectiveDate(contentUuid3, "2-Jan-21", uatConnection);
            HierarchyDatabaseUtils.updateModifiedDate(contentUuid3, "2-Jan-21", uatConnection);

            //go to publishing -> publishing -> 1 Step Approval Approve
            boolean PublishApproveTextAndNodNodesByVolumePageLoaded = publishingMenu().goToPublishApproveTextAndNodNodesByVolume();
            Assertions.assertTrue(PublishApproveTextAndNodNodesByVolumePageLoaded, "the Publish Approve-Text and NOD nodes by volume page loaded");

            toolbarPage().clickVolumeSelection();
            volumeSelectionPage().clickCheckBoxForVolume(nodeVolume1);
            volumeSelectionPage().clickCheckBoxForVolume(nodeVolume2);
            volumeSelectionPage().clickCheckBoxForVolume(nodeVolume3);
            volumeSelectionPage().clickAdd();
            volumeSelectionPage().clickConfirm();

            //do all other filtering to get to next part
            gridHeaderPage().openMenuForValueColumn();
            gridHeaderFiltersPage().setMultipleFilterValues(nodeValue1, nodeValue2, nodeValue3);

            //Start Date -> Equals
            gridHeaderPage().openMenuForStartDate();
            gridHeaderFiltersPage().setDateFilterOption("Equals");
            gridHeaderFiltersPage().setFilterDateValue("09/11/2001");
            mainHeaderPage().switchToPublishingToolboxWindow();

            boolean nodeValue1IsInGridAfterEquals = gridPage().isNodeHierarchyColumnValueInGrid("= " + nodeValue1);
            boolean nodeValue2IsNotInGridAfterEquals = !gridPage().isNodeHierarchyColumnValueInGrid("= " + nodeValue2);
            boolean nodeValue3IsNotInGridAfterEquals = !gridPage().isNodeHierarchyColumnValueInGrid("= " + nodeValue3);

            toolbarPage().clickClearFiltersAndSorts();
            gridHeaderPage().openMenuForValueColumn();
            gridHeaderFiltersPage().setMultipleFilterValues(nodeValue1, nodeValue2, nodeValue3);

            //End Date -> Not Equals
            gridHeaderPage().openMenuForEndDate();
            gridHeaderFiltersPage().setDateFilterOption("Not equal");
            gridHeaderFiltersPage().setFilterDateValue("1/2/2021");
            mainHeaderPage().switchToPublishingToolboxWindow();

            boolean nodeValue1IsInGridAfterNotEquals = gridPage().isNodeHierarchyColumnValueInGrid("= " + nodeValue1);
            boolean nodeValue2IsNotInGridAfterNotEquals = gridPage().isNodeHierarchyColumnValueInGrid("= " + nodeValue2);
            boolean nodeValue3IsNotInGridAfterNotEquals = !gridPage().isNodeHierarchyColumnValueInGrid("= " + nodeValue3);

            toolbarPage().clickClearFiltersAndSorts();
            gridHeaderPage().openMenuForValueColumn();
            gridHeaderFiltersPage().setMultipleFilterValues(nodeValue1, nodeValue2, nodeValue3);

            //Start Date -> After
            gridHeaderPage().openMenuForStartDate();
            gridHeaderFiltersPage().setDateFilterOption("After");
            gridHeaderFiltersPage().setFilterDateValue("05/25/2008");
            mainHeaderPage().switchToPublishingToolboxWindow();

            boolean nodeValue1IsInGridAfterAfter = !gridPage().isNodeHierarchyColumnValueInGrid("= " + nodeValue1);
            boolean nodeValue2IsNotInGridAfterAfter = gridPage().isNodeHierarchyColumnValueInGrid("= " + nodeValue2);
            boolean nodeValue3IsNotInGridAfterAfter = !gridPage().isNodeHierarchyColumnValueInGrid("= " + nodeValue3);

            toolbarPage().clickClearFiltersAndSorts();
            gridHeaderPage().openMenuForValueColumn();
            gridHeaderFiltersPage().setMultipleFilterValues(nodeValue1, nodeValue2, nodeValue3);

            //End Date -> before
            gridHeaderPage().openMenuForEndDate();
            gridHeaderFiltersPage().setDateFilterOption("Before");
            gridHeaderFiltersPage().setFilterDateValue("01/02/2021");
            mainHeaderPage().switchToPublishingToolboxWindow();

            boolean nodeValue1IsInGridAfterBefore = gridPage().isNodeHierarchyColumnValueInGrid("= " + nodeValue1);
            boolean nodeValue2IsNotInGridAfterBefore = gridPage().isNodeHierarchyColumnValueInGrid("= " + nodeValue2);
            boolean nodeValue3IsNotInGridAfterBefore = !gridPage().isNodeHierarchyColumnValueInGrid("= " + nodeValue3);

            toolbarPage().clickClearFiltersAndSorts();
            gridHeaderPage().openMenuForValueColumn();
            gridHeaderFiltersPage().setMultipleFilterValues(nodeValue1, nodeValue2, nodeValue3);

            //Modified -> in between //there is a bug with the in between stuff
            gridHeaderPage().openMenuForModifiedDateColumn();
            gridHeaderFiltersPage().setDateFilterOption("In between");
            gridHeaderFiltersPage().setInBetweenInputDateFields("05/23/2020", "05/28/2020");
            mainHeaderPage().switchToPublishingToolboxWindow();

            boolean nodeValue1IsInGridAfterBetween = !gridPage().isNodeHierarchyColumnValueInGrid("= " + nodeValue1);
            boolean nodeValue2IsNotInGridAfterBetween = gridPage().isNodeHierarchyColumnValueInGrid("= " + nodeValue2);
            boolean nodeValue3IsNotInGridAfterBetween = !gridPage().isNodeHierarchyColumnValueInGrid("= " + nodeValue3);

            Assertions.assertAll
            (
                () -> Assertions.assertTrue(nodeValue1IsInGridAfterEquals, "node 1 should be in the grid After setting dropdown to Equals"),
                () -> Assertions.assertTrue(nodeValue2IsNotInGridAfterEquals, "node 2 should not be in the grid After setting dropdown to Equals"),
                () -> Assertions.assertTrue(nodeValue3IsNotInGridAfterEquals, "node 3 should not be in the grid After setting dropdown to Equals"),

                () -> Assertions.assertTrue(nodeValue1IsInGridAfterNotEquals, "node 1 should not be in the grid After setting dropdown to Not Equals"),
                () -> Assertions.assertTrue(nodeValue2IsNotInGridAfterNotEquals, "node 2 should be in the grid After setting dropdown to Not Equals"),
                () -> Assertions.assertTrue(nodeValue3IsNotInGridAfterNotEquals, "node 3 should be in the grid After setting dropdown to Not Equals"),

                () -> Assertions.assertTrue(nodeValue1IsInGridAfterAfter, "node 1 should be in the grid After setting dropdown to After"),
                () -> Assertions.assertTrue(nodeValue2IsNotInGridAfterAfter, "node 2 should not be in the grid After setting dropdown to After"),
                () -> Assertions.assertTrue(nodeValue3IsNotInGridAfterAfter, "node 3 should be in the grid After setting dropdown to After"),

                () -> Assertions.assertTrue(nodeValue1IsInGridAfterBefore, "node 1 should not be in the grid After setting dropdown to Before"),
                () -> Assertions.assertTrue(nodeValue2IsNotInGridAfterBefore, "node 2 should not be in the grid After setting dropdown to Before"),
                () -> Assertions.assertTrue(nodeValue3IsNotInGridAfterBefore, "node 3 should not be in the grid After setting dropdown to Before"),

                () -> Assertions.assertTrue(nodeValue1IsInGridAfterBetween, "node 1 should be in the grid After setting dropdown to Between"),
                () -> Assertions.assertTrue(nodeValue2IsNotInGridAfterBetween, "node 2 should not be in the grid After setting dropdown to Between"),
                () -> Assertions.assertTrue(nodeValue3IsNotInGridAfterBetween, "node 3 should not be in the grid After setting dropdown to Between")
            );
        }
        finally
        {
            HierarchyDatabaseUtils.updateLegisStartEffectiveDate(contentUuid1, originalStartDate1, uatConnection);
            HierarchyDatabaseUtils.updateLegisEndEffectiveDate(contentUuid1, originalEndDate1, uatConnection);
            HierarchyDatabaseUtils.updateModifiedDate(contentUuid1, originalModifiedDate1, uatConnection);

            HierarchyDatabaseUtils.updateLegisStartEffectiveDate(contentUuid2, originalStartDate2, uatConnection);
            HierarchyDatabaseUtils.updateLegisEndEffectiveDate(contentUuid2, originalEndDate2, uatConnection);
            HierarchyDatabaseUtils.updateModifiedDate(contentUuid2, originalModifiedDate2, uatConnection);

            HierarchyDatabaseUtils.updateLegisStartEffectiveDate(contentUuid3, originalStartDate3, uatConnection);
            HierarchyDatabaseUtils.updateLegisEndEffectiveDate(contentUuid3, originalEndDate3, uatConnection);
            HierarchyDatabaseUtils.updateModifiedDate(contentUuid3, originalModifiedDate3, uatConnection);
        }
    }
}
