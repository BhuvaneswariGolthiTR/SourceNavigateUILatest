package com.thomsonreuters.codes.codesbench.quality.tests.hierarchy;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.autoIT.AutoITUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.TargetDataMockingNew;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.datapod.HierarchyDatapodConfiguration;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.datapod.HierarchyDatapodObject;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;

import java.util.List;

public class ChangeNavigationTreeDateInHierarchyTests extends TestService
{
    HierarchyDatapodObject datapodObject;

    /**
     * STORY/BUG - N/A <br>
     * SUMMARY - This test verifies that after editing, checking in a new node, and setting the navigation tree date to the current date, <br>
     * the page only shows the node that was just created. Then it should display the original node after setting the navigation tree date back <br>
     * USER - LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void changeNavigationTreeDateInHierarchyTests()
    {
        datapodObject = TargetDataMockingNew.Iowa.Small.insert();
        String nodeUUID = datapodObject.getSections().get(0).getNodeUUID();
        String text = "test";
        String currentDate = DateAndTimeUtils.getCurrentDateMMddyyyy();
        String yesterdaysDate = DateAndTimeUtils.getYesterdaysDateWithoutLeadingZeros();

        homePage().goToHomePage();
        loginPage().logIn();
        hierarchyMenu().goToNavigate();

        //Edit and check in changes to new node
        hierarchySearchPage().searchNodeUuid(nodeUUID);
        String nodeValue = siblingMetadataPage().getSelectedNodeValue();
        siblingMetadataPage().selectedSiblingMetadataEditContent();
        editorPage().switchDirectlyToTextFrame();
        editorPage().typeTextInFirstTextParagraph(text);
        editorPage().closeAndCheckInChangesWithGivenDate(currentDate);
        boolean expectedAlertAppeared = AutoITUtils.verifyAlertTextAndAccept(true,
                String.format("THIS IS NOT AN ERROR, just a notice that the selected node [%s] ended before your navigation filter date. Your navigation date has been reset to %s.", nodeUUID, yesterdaysDate));
        Assertions.assertTrue(expectedAlertAppeared, "The expected alert did not appear.");

        //Verify two nodes now exist with that value
        editorPage().waitForEditorToClose();
        hierarchyNavigatePage().switchToHierarchyEditPage();
        List<WebElement> nodeList = siblingMetadataPage().getListOfNodesWithGivenValue(nodeValue);
        boolean twoNodesAreDisplayed = nodeList.size() == 2;
        Assertions.assertTrue(twoNodesAreDisplayed, "2 nodes with the same value should be displayed");

        //Verify that only new node is displayed after changing Navigation tree date
        hierarchyTreePage().setNavigationTreeToCurrentDate();
        nodeList = siblingMetadataPage().getListOfNodesWithGivenValue(nodeValue);
        boolean oneNodeIsDisplayed = nodeList.size() == 1;
        Assertions.assertTrue(oneNodeIsDisplayed, "1 node should be displayed");
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