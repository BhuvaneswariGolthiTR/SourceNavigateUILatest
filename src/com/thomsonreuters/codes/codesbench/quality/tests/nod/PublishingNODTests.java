package com.thomsonreuters.codes.codesbench.quality.tests.nod;

import com.thomsonreuters.codes.codesbench.quality.pageelements.home.ContentPreferencesPageElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.publishing.PublishingDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.List;

public class PublishingNODTests extends TestService
{
    Connection uatConnection;

    /**
     * STORY/BUG - HALCYONST-6789, HALCYONST-7880 <br>
     * SUMMARY - This test verifies that after inserting a blueline into a section node, that the publishing status of the newly created BL, BL Analysis, and XND
     * all have a publishing status of 'Not Published.' This test then verifies that the new NOD container should have a blank publishing status and the associated section node
     * should have the same publishing status as before inserting the new blueline<br>
     * USER - LEGAL <br>
     */
    @Test
    @LEGAL
    @LOG
    public void nodAndBluelinePublishingStatusTest()
    {
        String contentUuid = "I0B7A2390EBDB11DABC64E280680F6D36";
        String contentSetId = ContentSets.IOWA_DEVELOPMENT.getCode();

        uatConnection = BaseDatabaseUtils.connectToDatabaseUAT();
        PublishingDatabaseUtils.setPublishingNodeToReady(contentUuid,contentSetId,uatConnection);

        //Check that the content set has publishing enabled, and enable it if not
        PublishingDatabaseUtils.checkAndSetContentSetToBeEnabledForPublishing(contentSetId,uatConnection);

        homePage().goToHomePage();
        loginPage().logIn();

        //Navigate to a specific case
        nodMenu().goToSubscribedCases();
        String caseSerialNumber = subscribedCasesGridPage().getCaseSerialNumberFromSubscribedCasesByGivenRow(1);
        subscribedCasesGridPage().clickCaseSerialNumberLink(caseSerialNumber);

        headnotesSearchPage().clickKeywordFind();
        findPage().setFirstKeywordDropdown("CHAPTER");
        findPage().setFirstKeywordValue("422");
        findPage().setSecondKeywordDropdown("=");
        findPage().setSecondKeywordValue("422.1");
        findPage().clickSearch();

        //Check section node has a publishing status of 'Ready'
        headnotesPage().switchToHeadnotesPage();
        boolean isCorrectNodeHighlighted = headnotesTreePage().isNodeHighlightedInTree("= 422.1");
        Assertions.assertTrue(isCorrectNodeHighlighted, "The incorrect node is highlighted");
        headnotesTreePage().rightClickCurrentlyHighlightedNode();
        boolean hierarchyNavigatePageAppears = headnotesContextMenu().findInHierarchy();
        Assertions.assertTrue(hierarchyNavigatePageAppears,"The Hierarchy Navigate page should appear but does not");

        boolean isSelectedNodeSetToReadyStatus = siblingMetadataPage().isSelectedNodeReadyStatus();
        Assertions.assertTrue(isSelectedNodeSetToReadyStatus,"The selected node should have a publishing status of 'Ready' but in fact does not");

        //Insert new blueline into section node
        hierarchyNavigatePage().closeCurrentWindowIgnoreDialogue();
        headnotesPage().switchToHeadnotesPage();
        headnotesTreePage().rightClickCurrentlyHighlightedNode();
        boolean insertBluelinePageAppeared = headnotesContextMenu().insertBlueline();
        Assertions.assertTrue(insertBluelinePageAppeared, "The insert blueline page did not appear when it should");

        insertBluelinePage().setBlueLineNumber("1");
        insertBluelinePage().clickOk();
        insertBluelinePage().setBlueLineText("test");
        insertBluelinePage().clickOk();

        //Check new blueline appears in hierarchy page and has a publishing status of 'Not Published'
        headnotesPage().switchToHeadnotesPage();
        isCorrectNodeHighlighted = headnotesTreePage().isNodeHighlightedInTree("BL 1");
        Assertions.assertTrue(isCorrectNodeHighlighted, "The incorrect node is highlighted");
        headnotesTreePage().rightClickCurrentlyHighlightedNode();
        boolean hierarchyNavigatePageAppears2 = headnotesContextMenu().findInHierarchy();
        Assertions.assertTrue(hierarchyNavigatePageAppears2,"The Hierarchy Navigate page should appear but does not");

        boolean newBluelineIsSetToNotPublished = siblingMetadataPage().isSelectedNodeStatusNotPublished();
        boolean isSelectedNodeABluelineNode = siblingMetadataPage().getSelectedGridRowKeyword().equals("BLUE LINE");

        //Check new blueline analysis node has a publishing status of 'Not Published'
        hierarchyTreePage().clickOnTreeNodeWithGivenValue("BL ANALYSI(1)");
        boolean newBlAnalysisIsSetToNotPublished = siblingMetadataPage().isSelectedNodeStatusNotPublished();

        //Check new XND node has a publishing status of 'Not Published'
        hierarchyTreePage().clickOnTreeNodeWithGivenValue("XND(1)");
        boolean newXndIsSetToNotPublished = siblingMetadataPage().isSelectedNodeStatusNotPublished();

        //Check new NOD Container has a blank publishing status
        hierarchyTreePage().clickOnTreeNodeWithGivenValue("NOD CONTAI(1)");
        boolean newNodContainerHasBlankPublishingStatus = siblingMetadataPage().doesSelectedNodeHaveAPublishingStatus();

        //Check original section node still has a publishing status of 'Ready'
        hierarchySearchPage().searchContentUuid(contentUuid);
        boolean originalSectionNodeIsSetToReadyStatus = siblingMetadataPage().isSelectedNodeReadyStatus();

        //Delete Blueline for cleanup
        headnotesPage().switchToHeadnotesPage();
        headnotesTreePage().rightClickCurrentlyHighlightedNode();
        headnotesContextMenu().deleteBlueline();
        deleteBluelinePage().clickOk();

        Assertions.assertAll
        (
                () -> Assertions.assertTrue(newBluelineIsSetToNotPublished, "The new blueline should have a publishing status of 'Not Published' but it does not"),
                () -> Assertions.assertTrue(isSelectedNodeABluelineNode, "The selected node should be the newly created blueline but is not"),
                () -> Assertions.assertTrue(newBlAnalysisIsSetToNotPublished, "The new BL Analysis node should have a publishing status of 'Not Published' but it does not"),
                () -> Assertions.assertTrue(newXndIsSetToNotPublished, "The new XND should have a publishing status of 'Not Published' but it does not"),
                () -> Assertions.assertFalse(newNodContainerHasBlankPublishingStatus, "The new NOD Container should have a blank publishing status but it does not"),
                () -> Assertions.assertTrue(originalSectionNodeIsSetToReadyStatus, "The selected node should still have a publishing status of 'Ready' but in fact does not")
        );
    }

    /**
     * STORY/BUG - HALCYONST-7152 <br>
     * SUMMARY - This test verifies that in a publishing enabled content set on the Content Preferences page under the NOD Upload Settings, the correct radio buttons and checkboxes are displayed.
     * This test also verifies the user is able to move volumes to and from the 'Volume(s)' and 'Disabled volume(s)' tables either one by one or by multiple volumes at a time.<br>
     * USER - LEGAL <br>
     */
    @Test
    @LEGAL
    @LOG
    public void nodPublishingContentPreferencesTest()
    {
        String volume1 = "0010";
        String volume2 = "0030";
        String volume3 = "0035";
        String volume1FullName = volume1 + " Constitution 1989";
        String volume2FullName = volume2 + " 1.1 TO 11.END SOVEREIGNTY AND MANAGEMENT 2001";
        String volume3FullName = volume3 + " 12.1 TO 18C. END SOVEREIGNTY AND MANAGEMENT 2000";
        String contentSetId = ContentSets.IOWA_DEVELOPMENT.getCode();

        uatConnection = BaseDatabaseUtils.connectToDatabaseUAT();

        //Check that the given content set has publishing enabled
        PublishingDatabaseUtils.checkAndSetContentSetToBeEnabledForPublishing(contentSetId,uatConnection);

        homePage().goToHomePage();
        loginPage().logIn();

        //Check that the correct radio buttons and tables appear
        homeMenu().goToContentPreferences();
        boolean weeklyUploadRadioButtonAppears = contentPreferencesPage().isElementDisplayed(ContentPreferencesPageElements.WEEKLY_UPLOAD_RADIO_BUTTON_XPATH);
        boolean noUploadRadioButtonAppears = contentPreferencesPage().isElementDisplayed(ContentPreferencesPageElements.NO_UPLOAD_RADIO_BUTTON_XPATH);
        boolean volumesSwitchTableAppears = contentPreferencesPage().isElementDisplayed(ContentPreferencesPageElements.VOLUMES_SWITCH_TABLE_XPATH);
        boolean disabledVolumesSwitchTableAppears = contentPreferencesPage().isElementDisplayed(ContentPreferencesPageElements.DISABLED_VOLUMES_SWITCH_TABLE_XPATH);

        contentPreferencesPage().clickDailyUploadRadioButton();

        //Check that the tables are now disabled
        contentPreferencesPage().uncheckEnableVolumeChoosingCheckbox();
        boolean volumesSwitchTableIsDisabled = contentPreferencesPage().isElementDisabled(ContentPreferencesPageElements.VOLUMES_SWITCH_TABLE_XPATH);
        boolean disabledVolumesSwitchTableIsDisabled = contentPreferencesPage().isElementDisabled(ContentPreferencesPageElements.DISABLED_VOLUMES_SWITCH_TABLE_XPATH);

        //Check that the tables are now enabled
        contentPreferencesPage().checkEnableVolumeChoosingCheckbox();
        boolean volumesSwitchTableIsEnabled = contentPreferencesPage().isElementDisabled(ContentPreferencesPageElements.VOLUMES_SWITCH_TABLE_XPATH);
        boolean disabledVolumesSwitchTableIsEnabled = contentPreferencesPage().isElementDisabled(ContentPreferencesPageElements.DISABLED_VOLUMES_SWITCH_TABLE_XPATH);

        //select one volume and click the right arrow button to add
        contentPreferencesPage().selectVolumesFromVolumesTable(volume1);
        contentPreferencesPage().clickSingleRightArrowButton();
        List<String> listOfVolumesInVolumesTable = contentPreferencesPage().getListOfAllVolumesUnderVolumesTable();//Checking the tables this way takes more time, but is most stable
        List<String> listOfVolumesInDisabledVolumesTable = contentPreferencesPage().getListOfAllVolumesUnderDisabledVolumesTable();
        boolean volume1WasRemovedFromVolumesTable = listOfVolumesInVolumesTable.contains(volume1FullName);
        boolean volume1WasAddedToDisabledVolumesTable = listOfVolumesInDisabledVolumesTable.contains(volume1FullName);

        //select two volumes and click the right arrow button to add
        contentPreferencesPage().selectVolumesFromVolumesTable(volume2,volume3);
        contentPreferencesPage().clickSingleRightArrowButton();
        List<String> listOfVolumesInVolumesTable2 = contentPreferencesPage().getListOfAllVolumesUnderVolumesTable();
        List<String> listOfVolumesInDisabledVolumesTable2 = contentPreferencesPage().getListOfAllVolumesUnderDisabledVolumesTable();
        boolean volume2WasRemovedFromVolumesTable = listOfVolumesInVolumesTable2.contains(volume2FullName);
        boolean volume3WasRemovedFromVolumesTable = listOfVolumesInVolumesTable2.contains(volume3FullName);
        boolean volume2WasAddedToDisabledVolumesTable = listOfVolumesInDisabledVolumesTable2.contains(volume2FullName);
        boolean volume3WasAddedToDisabledVolumesTable = listOfVolumesInDisabledVolumesTable2.contains(volume3FullName);

        //select one volume and click the left arrow button to remove
        contentPreferencesPage().selectVolumesFromDisabledVolumesTable(volume1);
        contentPreferencesPage().clickSingleLeftArrowButton();
        List<String> listOfVolumesInVolumesTable3 = contentPreferencesPage().getListOfAllVolumesUnderVolumesTable();
        List<String> listOfVolumesInDisabledVolumesTable3 = contentPreferencesPage().getListOfAllVolumesUnderDisabledVolumesTable();
        boolean volume1WasAddedToVolumesTable = listOfVolumesInVolumesTable3.contains(volume1FullName);
        boolean volume1WasRemovedFromDisabledVolumesTable = listOfVolumesInDisabledVolumesTable3.contains(volume1FullName);

        //select two volumes and click the left arrow button to remove
        contentPreferencesPage().selectVolumesFromDisabledVolumesTable(volume2,volume3);
        contentPreferencesPage().clickSingleLeftArrowButton();
        List<String> listOfVolumesInVolumesTable4 = contentPreferencesPage().getListOfAllVolumesUnderVolumesTable();
        List<String> listOfVolumesInDisabledVolumesTable4 = contentPreferencesPage().getListOfAllVolumesUnderDisabledVolumesTable();
        boolean volume2WasAddedToVolumesTable = listOfVolumesInVolumesTable4.contains(volume2FullName);
        boolean volume3WasAddedToVolumesTable = listOfVolumesInVolumesTable4.contains(volume3FullName);
        boolean volume2WasRemovedFromDisabledVolumesTable = listOfVolumesInDisabledVolumesTable4.contains(volume2FullName);
        boolean volume3WasRemovedFromDisabledVolumesTable = listOfVolumesInDisabledVolumesTable4.contains(volume3FullName);

        Assertions.assertAll
        (
            () -> Assertions.assertFalse(weeklyUploadRadioButtonAppears,"The 'Weekly Upload' radio button appears"),
            () -> Assertions.assertTrue(noUploadRadioButtonAppears,"The 'No Upload' radio button should appear on the page but does not"),
            () -> Assertions.assertTrue(volumesSwitchTableAppears,"The 'Volume(s)' switch table should appear on the page but does not"),
            () -> Assertions.assertTrue(disabledVolumesSwitchTableAppears,"The 'Disabled volume(s)' switch table should appear on the page but does not"),
            () -> Assertions.assertTrue(volumesSwitchTableIsDisabled,"The 'Volume(s)' switch table should be disabled on the page but is actually enabled"),
            () -> Assertions.assertTrue(disabledVolumesSwitchTableIsDisabled,"The 'Disabled volume(s)' switch table should be disabled on the page but is actually enabled"),
            () -> Assertions.assertFalse(volumesSwitchTableIsEnabled,"The 'Volume(s)' switch table should be enabled on the page but is actually disabled"),
            () -> Assertions.assertFalse(disabledVolumesSwitchTableIsEnabled,"The 'Disabled volume(s)' switch table should be enabled on the page but is actually disabled"),
            () -> Assertions.assertFalse(volume1WasRemovedFromVolumesTable,"The first volume should be removed from the 'Volume(s)' table but it was not"),
            () -> Assertions.assertTrue(volume1WasAddedToDisabledVolumesTable,"The first volume should be added to the 'Disabled volume(s)' table but it was not"),
            () -> Assertions.assertFalse(volume2WasRemovedFromVolumesTable,"The second volume should be removed from the 'Volume(s)' table but it was not"),
            () -> Assertions.assertFalse(volume3WasRemovedFromVolumesTable,"The third volume should be removed from the 'Volume(s)' table but it was not"),
            () -> Assertions.assertTrue(volume2WasAddedToDisabledVolumesTable,"The second volume should be added to the 'Disabled volume(s)' table but it was not"),
            () -> Assertions.assertTrue(volume3WasAddedToDisabledVolumesTable,"The third volume should be added to the 'Disabled volume(s)' table but it was not"),
            () -> Assertions.assertTrue(volume1WasAddedToVolumesTable,"The first volume should be added to the 'Volume(s)' table but it was not"),
            () -> Assertions.assertFalse(volume1WasRemovedFromDisabledVolumesTable,"The first volume should be removed from the 'Disabled volume(s)' table but it was not"),
            () -> Assertions.assertTrue(volume2WasAddedToVolumesTable,"The second volume should be added to the 'Volume(s)' table but it was not"),
            () -> Assertions.assertTrue(volume3WasAddedToVolumesTable,"The third volume should be added to the 'Volume(s)' table but it was not"),
            () -> Assertions.assertFalse(volume2WasRemovedFromDisabledVolumesTable,"The second volume should be removed from the 'Disabled volume(s)' table but it was not"),
            () -> Assertions.assertFalse(volume3WasRemovedFromDisabledVolumesTable,"The third volume should be removed from the 'Disabled volume(s)' table but it was not")
        );
    }

    @AfterEach
    public void closeConnection()
    {
        BaseDatabaseUtils.disconnect(uatConnection);
    }
}
