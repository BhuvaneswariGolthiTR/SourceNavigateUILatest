package com.thomsonreuters.codes.codesbench.quality.tests.hierarchy;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.CommonDataMocking;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.TargetDataMockingNew;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.datapod.HierarchyDatapodConfiguration;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.datapod.HierarchyDatapodObject;

import org.junit.jupiter.api.AfterEach;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.hierarchy.HierarchyDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

public class DispDerivGraphTests extends TestService
{

    HierarchyDatapodObject datapodObject;

    /**
     * STORY/BUG - N/A <br>
     * SUMMARY - This test verifies that the several context menu options are able to be accessed
     * by going through the disposition/derivation pane and right clicking on the node row shown in the grid <br>
     * USER - LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void dispDerivGraph_FunctionalityTest()
    {
        HierarchyDatapodConfiguration.getConfig().setBluelineCount(1);
        datapodObject = TargetDataMockingNew.Iowa.Small.insert();
        String contentSetIowa = ContentSets.IOWA_DEVELOPMENT.getCode();
        Connection connection = CommonDataMocking.connectToDatabase(environmentTag);
        String nodeUUID = datapodObject.getSections().get(0).getNodeUUID();
        String value = HierarchyDatabaseUtils.getNodeValueByNodeUUID(nodeUUID, contentSetIowa, connection);
        BaseDatabaseUtils.disconnect(connection);

        homePage().goToHomePage();
        loginPage().logIn();
        hierarchyMenu().goToNavigate();

        hierarchySearchPage().searchNodeUuid(nodeUUID);
        dispositionDerivationPage().clickExpandButton();

        //Check that Common Editor page was shown
        dispositionDerivationPage().rightClickNodeByValue(value);
        boolean editorPageAppeared = dispositionDerivationContextMenu().editContent();
        Assertions.assertTrue(editorPageAppeared, "The Common Editor page did not appear when it should have");

        editorPage().closeDocumentWithNoChanges();
        hierarchyNavigatePage().switchToHierarchyEditPage();

        //Check that Common Editor page was shown
        dispositionDerivationPage().rightClickNodeByValue(value);
        boolean viewEditorPageAppeared = dispositionDerivationContextMenu().viewContent();
        Assertions.assertTrue(viewEditorPageAppeared, "The Common Editor page did not appear when it should have");

        editorPage().closeDocumentWithNoChanges();
        hierarchyNavigatePage().switchToHierarchyEditPage();

        //Check that page refreshed
        dispositionDerivationPage().rightClickNodeByValue(value);
        dispositionDerivationContextMenu().refreshSelection();

        //Check that Update Metadata page was shown
        dispositionDerivationPage().rightClickNodeByValue(value);
        boolean updateMetadataPageAppeared = dispositionDerivationContextMenu().updateMetadata();
        Assertions.assertTrue(updateMetadataPageAppeared, "The Update Metadata page did not appear when it should have");

        updateMetadataPage().clickCancel();

        //Check that Previous WIP Versions page was shown
        dispositionDerivationPage().rightClickNodeByValue(value);
        boolean previousWipVersionPageAppeared = dispositionDerivationContextMenu().viewModifyPreviousWipVersion();
        Assertions.assertTrue(previousWipVersionPageAppeared, "The Previous WIP Versions page did not appear when it should have");

        previousWipVersionsPage().clickClose();

        //Check that Set Law Tracking page was shown
        dispositionDerivationPage().rightClickNodeByValue(value);
        boolean setLawTrackingPageAppearedAfter = dispositionDerivationContextMenu().cloneAfter();
        Assertions.assertTrue(setLawTrackingPageAppearedAfter, "The Set Law Tracking page did not appear when it should have");

        hierarchySetLawTrackingPage().clickCancel();

        //Check that Set Law Tracking page was shown
        dispositionDerivationPage().rightClickNodeByValue(value);
        boolean setLawTrackingPageAppearedBefore = dispositionDerivationContextMenu().cloneBefore();
        Assertions.assertTrue(setLawTrackingPageAppearedBefore, "The Set Law Tracking page did not appear when it should have");

        hierarchySetLawTrackingPage().clickCancel();

        //Check that Delete page was shown
        dispositionDerivationPage().rightClickNodeByValue(value);
        boolean deletePageAppeared = dispositionDerivationContextMenu().deleteFunctionsDelete();
        Assertions.assertTrue(deletePageAppeared, "The Delete page did not appear when it should have");

        deletePage().clickCancel();

        //Check that Raw XML Editor page was shown
        dispositionDerivationPage().rightClickNodeByValue(value);
        boolean editRawXmlPageAppeared = dispositionDerivationContextMenu().editRawXml();
        Assertions.assertTrue(editRawXmlPageAppeared, "The Raw XML Editor page did not appear when it should have");

        hierarchyRawXmlEditorPage().clickClose();
        hierarchyNavigatePage().switchToHierarchyEditPage();

        //Check that Raw XML Editor page was shown
        dispositionDerivationPage().rightClickNodeByValue(value);
        boolean viewRawXmlPageAppeared = dispositionDerivationContextMenu().viewRawXml();
        Assertions.assertTrue(viewRawXmlPageAppeared, "The Raw XML Editor page did not appear when it should have");

        hierarchyRawXmlEditorPage().clickClose();
        hierarchyNavigatePage().switchToHierarchyEditPage();
    }

    /**
     * STORY/BUG - N/A <br>
     * SUMMARY - This test verifies that the several context menu options are able to be accessed
     * by going through the disposition/derivation pane and right clicking on the node image shown below the grid <br>
     * USER - LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void dispDerivGraph_BottomDocumentTest()
    {
        datapodObject = TargetDataMockingNew.Iowa.Small.insert();
        String contentSetIowa = ContentSets.IOWA_DEVELOPMENT.getCode();
        Connection connection = CommonDataMocking.connectToDatabase(environmentTag);
        String nodeUUID = datapodObject.getSections().get(0).getNodeUUID();
        BaseDatabaseUtils.disconnect(connection);

        homePage().goToHomePage();
        loginPage().logIn();
        hierarchyMenu().goToNavigate();

        hierarchySearchPage().searchNodeUuid(nodeUUID);
        dispositionDerivationPage().clickExpandButton();

        //Check that Common Editor page was shown
        dispositionDerivationPage().rightClickSelectedNodeImage();
        boolean editorPageAppeared = dispositionDerivationContextMenu().editContentGraph();
        Assertions.assertTrue(editorPageAppeared, "The Common Editor page did not appear when it should have");

        editorPage().closeDocumentWithNoChanges();
        editorPage().waitForEditorToClose();
        hierarchyNavigatePage().switchToHierarchyEditPage();

        //Check that Common Editor page was shown
        dispositionDerivationPage().rightClickSelectedNodeImage();
        boolean viewEditorPageAppeared = dispositionDerivationContextMenu().viewContentGraph();
        Assertions.assertTrue(viewEditorPageAppeared, "The Common Editor page did not appear when it should have");

        editorPage().closeDocumentWithNoChanges();
        editorPage().waitForEditorToClose();
        hierarchyNavigatePage().switchToHierarchyEditPage();

        //Check that Update Metadata page was shown
        dispositionDerivationPage().rightClickSelectedNodeImage();
        boolean updateMetadataPageAppeared = dispositionDerivationContextMenu().updateMetadataGraph();
        Assertions.assertTrue(updateMetadataPageAppeared, "The Update Metadata page did not appear when it should have");

        updateMetadataPage().clickCancel();

        //Check that Previous WIP Versions page was shown
        dispositionDerivationPage().rightClickSelectedNodeImage();
        boolean previousWipVersionPageAppeared = dispositionDerivationContextMenu().viewModifyPreviousWipVersion();
        Assertions.assertTrue(previousWipVersionPageAppeared, "The Previous WIP Versions page did not appear when it should have");

        previousWipVersionsPage().clickClose();
        hierarchyNavigatePage().switchToHierarchyEditPage();

        //Check that Set Law Tracking page was shown
        dispositionDerivationPage().rightClickSelectedNodeImage();
        boolean setLawTrackingPageAppearedAfter = dispositionDerivationContextMenu().cloneAfterGraph();
        Assertions.assertTrue(setLawTrackingPageAppearedAfter, "The Set Law Tracking page did not appear when it should have");

        hierarchySetLawTrackingPage().clickCancel();
        hierarchyNavigatePage().switchToHierarchyEditPage();

        //Check that Set Law Tracking page was shown
        dispositionDerivationPage().rightClickSelectedNodeImage();
        boolean setLawTrackingPageAppearedBefore = dispositionDerivationContextMenu().cloneBeforeGraph();
        Assertions.assertTrue(setLawTrackingPageAppearedBefore, "The Set Law Tracking page did not appear when it should have");

        hierarchySetLawTrackingPage().clickCancel();
        hierarchyNavigatePage().switchToHierarchyEditPage();

        //Check that Delete page was shown
        dispositionDerivationPage().rightClickSelectedNodeImage();
        boolean deletePageAppeared = dispositionDerivationContextMenu().deleteFunctionsDeleteGraph();
        Assertions.assertTrue(deletePageAppeared, "The Delete page did not appear when it should have");

        deletePage().clickCancel();
        hierarchyNavigatePage().switchToHierarchyEditPage();

        //Check that Raw XML Editor page was shown
        dispositionDerivationPage().rightClickSelectedNodeImage();
        boolean editRawXmlPageAppeared = dispositionDerivationContextMenu().editRawXml();
        Assertions.assertTrue(editRawXmlPageAppeared, "The Raw XML Editor page did not appear when it should have");

        hierarchyRawXmlEditorPage().closeCurrentWindowIgnoreDialogue();
        hierarchyNavigatePage().switchToHierarchyEditPage();

        //Check that Raw XML Editor page was shown
        dispositionDerivationPage().rightClickSelectedNodeImage();
        boolean viewRawXmlPageAppeared = dispositionDerivationContextMenu().viewRawXml();
        Assertions.assertTrue(viewRawXmlPageAppeared, "The Raw XML Editor page did not appear when it should have");

        hierarchyRawXmlEditorPage().closeCurrentWindowIgnoreDialogue();
    }

    /**
     * STORY/BUG - HALCYONST-11485 <br>
     * SUMMARY - This test verifies that we can set the end date in the disp/derv pane and verify that after the change is applied
     * the change is visible in both the disp/derv and sibling metadata grid. <br>
     * USER - LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void dispDervSuccessfulChangeDateTest()
    {
        datapodObject = TargetDataMockingNew.Iowa.Small.insert();
        String nodeUuid = datapodObject.getSections().get(0).getNodeUUID();
        String todaysDate = DateAndTimeUtils.getCurrentDateMMddyyyy();
        Connection connection = CommonDataMocking.connectToDatabase(environmentTag);
        String contentSetIowa = ContentSets.IOWA_DEVELOPMENT.getCode();
        String nodeUUID = datapodObject.getSections().get(0).getNodeUUID();
        String value = HierarchyDatabaseUtils.getNodeValueByNodeUUID(nodeUUID, contentSetIowa, connection);

        homePage().goToHomePage();
        loginPage().logIn();
        hierarchyMenu().goToNavigate();

        hierarchySearchPage().searchNodeUuid(nodeUuid);

        // Check that update metadata page is shown
        dispositionDerivationPage().rightClickNodeByValue(value);
        boolean updateMetadataPageAppears = dispositionDerivationContextMenu().updateMetadata();
        Assertions.assertTrue(updateMetadataPageAppears, "The update Metadata should have appeared but didn't");

        updateMetadataPage().enterEffectiveEndDate(todaysDate);
        updateMetadataPage().clickQuickLoadOk();
        hierarchyNavigatePage().switchToHierarchyEditPage();

        String siblingMetadataEndDate = siblingMetadataPage().getEndDateOfSelectedNode();
        String dispDervEndDate = dispositionDerivationPage().getEndDateOfSelectedNode();

        Assertions.assertAll
          (
              () -> Assertions.assertEquals(todaysDate, siblingMetadataEndDate, "After setting effective end date the Sibling metadata grid should have updated"),
              () -> Assertions.assertEquals(DateAndTimeUtils.getCurentDateMDYYYY(), dispDervEndDate, "After setting effective end date the disp/Derv should have updated")
          );
    }

    @AfterEach
    public void cleanUp()
    {
        if (datapodObject != null)
        {
            datapodObject.delete();
        }
    }
}