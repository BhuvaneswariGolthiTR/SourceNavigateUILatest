package com.thomsonreuters.codes.codesbench.quality.tests.hierarchy;

import com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.hierarchy.navigate.DispositionDerivationContextMenuElements;
import com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.hierarchy.navigate.ParentageGraphContextMenuElements;
import com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.hierarchy.navigate.SiblingMetadataContextMenuElements;
import com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.hierarchy.navigate.TreeContextMenuElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.CreateNewVolumePageElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.autoIT.AutoITUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.hierarchy.HierarchyDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.sql.Connection;

public class CreateNewVolumeTests extends TestService
{
    Connection uatConnection;
    String contentSetIowa = ContentSets.IOWA_DEVELOPMENT.getCode();

    @AfterEach
    public void closeConnection()
    {
        BaseDatabaseUtils.disconnect(uatConnection);
    }

    /**
     * STORY/BUG - HALCYONST-6437/39466/5329 <br>
     * SUMMARY - When creating a new volume the volume number has to be a unique number, it can not have any non-numeric characters.
     * Upon trying to submit anything that is not a unique numeric number specific errors that correspond to what was entered are expected to appear<br>
     * USER - LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void createNewVolumeVolumeNumberNeedsToBeUniqueAndNumericTest()
    {
        String nodeUuid = "I37E7D35014EF11DA8AC5CD53670E6B4E";
        uatConnection = BaseDatabaseUtils.connectToDatabaseUAT();
        String volumeNumber = HierarchyDatabaseUtils.getNodeVolumeWithNodeUuid(nodeUuid, contentSetIowa, uatConnection);

        homePage().goToHomePage();
        loginPage().logIn();
        hierarchyMenu().goToNavigate();

        hierarchySearchPage().searchNodeUuid(nodeUuid);
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        boolean createNewVolumePageAppeared = siblingMetadataContextMenu().createNewVolume();

        //volume fields tests
        createNewVolumePage().setVolumeNumber(volumeNumber);
        createNewVolumePage().setTitle("duplicate");
        createNewVolumePage().clickSubmit();
        boolean duplicateEntryAlertAppeared = AutoITUtils.verifyAlertTextAndAccept(true, "This volume already exists");

        createNewVolumePage().setVolumeNumber("aaaa");
        createNewVolumePage().setTitle("non numeric");
        createNewVolumePage().clickSubmit();
        boolean nonNumericAlertAppeared = AutoITUtils.verifyAlertTextAndAccept(true, "The Volume number needs to be 4 digits");

        createNewVolumePage().setVolumeNumber("");
        createNewVolumePage().setTitle("");
        createNewVolumePage().clickSubmit();
        boolean emptyFieldsAlertAppeared = AutoITUtils.verifyAlertTextContainsAndAccept(true, "The title and volume fields both need to be populated.");

        //Halcyonst-5329 title limit coverage
        createNewVolumePage().setVolumeNumber("3646");
        createNewVolumePage().setTitle("This text is more than 30 characters, whoops");
        createNewVolumePage().clickSubmit();
        boolean titleTooLongAlertAppeared = AutoITUtils.verifyAlertTextContainsAndAccept(true, "Volume Title should be less than 30 characters.");

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(createNewVolumePageAppeared, "The create new volume page should appear"),
            () -> Assertions.assertTrue(duplicateEntryAlertAppeared, "There can not be duplicate volumes an alert should have appeared"),
            () -> Assertions.assertTrue(nonNumericAlertAppeared, "There can not be non numeric values a volumes an alert should have appeared"),
            () -> Assertions.assertTrue(emptyFieldsAlertAppeared, "Volume and title fields can not be empty an alert should have appeared"),
            () -> Assertions.assertTrue(titleTooLongAlertAppeared, "Title field should be less than 30 characters alert should have appeared")
        );
    }

    /**
     * STORY/BUG - HALCYONST-6437/3946 <br>
     * SUMMARY - The menu options for create new volume should appear in the Tree Page, the sibling metadata page, but not int the
     *           Parentage Page and the Disp/Derv View panes<br>
     * USER - LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void createNewVolumeMenuOptionAppearsAsExpectedTest()
    {
        String nodeUuid = "I37E7D35014EF11DA8AC5CD53670E6B4E";

        homePage().goToHomePage();
        loginPage().logIn();
        hierarchyMenu().goToNavigate();
        
        hierarchySearchPage().searchNodeUuid(nodeUuid);
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        boolean menuOptionAppearedSiblingMetadataGrid = siblingMetadataContextMenu().isElementDisplayed(SiblingMetadataContextMenuElements.CREATE_NEW_VOLUME_XPATH);

        hierarchyTreePage().rightClickSelectedNavigationTreeNode();
        boolean menuOptionAppearedInTreePage = hierarchyTreePage().isElementDisplayed(TreeContextMenuElements.CREATE_NEW_VOLUME_XPATH);

        parentageGraphPage().rightClickSelectedParentageGraphNodeImage();
        boolean menuOptionAppearedInParentagePage = parentageGraphPage().isElementDisplayed(ParentageGraphContextMenuElements.CREATE_NEW_VOLUME_XPATH);

        dispositionDerivationPage().rightClickSelectedNode();
        boolean menuOptionAppearedInDispDervGraph = dispositionDerivationPage().isElementDisplayed(DispositionDerivationContextMenuElements.CREATE_NEW_VOLUME_XPATH);

        Assertions.assertAll
        (
            ()-> Assertions.assertTrue(menuOptionAppearedSiblingMetadataGrid, "the create New volume option should appear in sibling metadata context menu"),
            ()-> Assertions.assertTrue(menuOptionAppearedInTreePage, "the create New volume option should appear in tree page"),
            ()-> Assertions.assertFalse(menuOptionAppearedInParentagePage, "the create New volume option should not appear in parentage page"),
            ()-> Assertions.assertFalse(menuOptionAppearedInDispDervGraph, "the create New volume option should not appear in the disp/derv page")
        );
    }

    /**
     * STORY/BUG -Halcyonst-6437/3946 <br>
     * SUMMARY - This test is a negative test of Create New Volume using a valid volume number. It  validates that after clicking
     * cancel the volume number and title fields do not contain the values that where inputted prior to clicking cancel.<br>
     * USER - LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void createNewVolumeNegativeTest()
    {
        String nodeUuid = "I37E7D35014EF11DA8AC5CD53670E6B4E";
        String newVolumeNumber = "9998";
        String newVolumeTitle = "Testing";

        homePage().goToHomePage();
        loginPage().logIn();
        hierarchyMenu().goToNavigate();

        hierarchySearchPage().searchNodeUuid(nodeUuid);
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        boolean createNewVolumePageAppeared = siblingMetadataContextMenu().createNewVolume();

        createNewVolumePage().setVolumeNumber(newVolumeNumber);
        createNewVolumePage().setTitle(newVolumeTitle);
        createNewVolumePage().clickCancel();
        boolean changesWereNotSavedAlert = AutoITUtils.verifyAlertTextAndCancel(true, "Changes weren't saved. Continue?");

        //verify fields are still filled in with the same information
        String currentVolumeNumber = createNewVolumePage().getVolumeNumberFromInputField();
        String currentVolumeTitle = createNewVolumePage().getVolumeTitleFromInputField();
        createNewVolumePage().clickCancel();
        boolean changesWereNotSavedAlert2 = AutoITUtils.verifyAlertTextAndCancel(true, "Changes weren't saved. Continue?");

        //verify window closes
        boolean windowClosed = siblingMetadataPage().isElementDisplayed(CreateNewVolumePageElements.PAGE_TITLE);

        //verify volume number was not updated
        uatConnection = BaseDatabaseUtils.connectToDatabaseUAT();
        String volumeNumber = HierarchyDatabaseUtils.getNodeVolumeWithNodeUuid(nodeUuid, contentSetIowa, uatConnection);
        boolean volumeNumberWasUpdated = volumeNumber.equals(newVolumeNumber);

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(createNewVolumePageAppeared, "The Create New Volume page should appear"),
            () -> Assertions.assertTrue(changesWereNotSavedAlert, "When clicking cancel we expect an alert to appear"),
            () -> Assertions.assertEquals(newVolumeNumber, currentVolumeNumber, "The volume number did not stay the same after we hit cancel on the alert"),
            () -> Assertions.assertEquals(newVolumeTitle, currentVolumeTitle, "The volume title did not stay the same after we hit cancel on the alert"),
            () -> Assertions.assertTrue(changesWereNotSavedAlert2, "When clicking cancel we expect an alert to appear"),
            () -> Assertions.assertFalse(windowClosed, "The Create New Volume page was supposed to close"),
            () -> Assertions.assertFalse(volumeNumberWasUpdated, "The volume number should not update.")
        );
    }

    /**
     * STORY/BUG -Halcyonst-6437/3946 <br>
     * SUMMARY - This test creates a new volume and verifies that new volume can be set on the
     * view volume info page. <br>
     * USER - LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void createNewVolumePositiveTest()
    {
        String nodeUuid = "I37E7D35014EF11DA8AC5CD53670E6B4E";
        String volumeNumber = "9998";
        String volumeTitle = "Test New Volume";
        Connection connection = BaseDatabaseUtils.connectToDatabaseUAT();

        try
        {
            homePage().goToHomePage();
            loginPage().logIn();
            hierarchyMenu().goToNavigate();

            hierarchySearchPage().searchNodeUuid(nodeUuid);
            siblingMetadataPage().rightClickSelectedSiblingMetadata();
            boolean createNewVolumePageAppeared = siblingMetadataContextMenu().createNewVolume();

            createNewVolumePage().setVolumeNumber(volumeNumber);
            createNewVolumePage().setTitle(volumeTitle);
            createNewVolumePage().clickSubmit();
            boolean newVolumeSuccessfullyCreatedAlert = AutoITUtils.verifyAlertTextAndAccept(true, "New volume was created");

            String volumeTitleFromDatabase = HierarchyDatabaseUtils.getVolumeTitleWithVolumeNumber(volumeNumber, contentSetIowa, connection);
            boolean isVolumeTitleEqualToExpected = volumeTitle.equals(volumeTitleFromDatabase);

            Assertions.assertAll
            (
                () -> Assertions.assertTrue(createNewVolumePageAppeared, "The Create New Volume page should appear"),
                () -> Assertions.assertTrue(newVolumeSuccessfullyCreatedAlert, "When clicking create we expect and alert to appear telling us volume was created"),
                () -> Assertions.assertTrue(isVolumeTitleEqualToExpected, "The Volume Title should change after changing the Volume Number ")
            );
        }
        finally
        {
           HierarchyDatabaseUtils.deleteVolumeFromDatabase(connection, volumeTitle, volumeNumber, ContentSets.IOWA_DEVELOPMENT.getCode());
        }
    }
}
