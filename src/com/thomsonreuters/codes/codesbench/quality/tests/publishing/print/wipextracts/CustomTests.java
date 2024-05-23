package com.thomsonreuters.codes.codesbench.quality.tests.publishing.print.wipextracts;

import com.thomsonreuters.codes.codesbench.quality.pageelements.publishing.print.wipextracts.custom.CustomPubExtractTreeViewPageElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

public class CustomTests extends TestService
{

    /**
     * STORY/BUG - HALCYONST-11068 <br>
     * SUMMARY - This test verifies that custom pub extract page correctly displays selected nodes, and we can move nodes back and forth between the lists <br>
     * USER - LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void customPubExtractUiCorrectlyDisplaysSelectedNodesTest()
    {
        String fileName = "QED test " + DateAndTimeUtils.getCurrentDateMMddyyyy();
        String values[] = {"Ch. 84", "Ch. 84A", "Ch. 84B", "Ch. 85"};

        homePage().goToHomePage();
        loginPage().logIn();

        boolean customPubExtractManagementPageLoaded = publishingMenu().goToPublishingPrintWipExtractsCustom();
        Assertions.assertTrue(customPubExtractManagementPageLoaded, "The Custom WIP Extract (Management) page did not load when it should have");

        boolean customPubExtractOptionsPageLoaded = customPubExtractManagementPage().clickNew();
        Assertions.assertTrue(customPubExtractOptionsPageLoaded, "Custom Pub Extract Options page didn't load when it should");

        customPubExtractOptionsPage().setExtractFileName(fileName);
        boolean customPubExtractTreeViewPageLoaded = customPubExtractOptionsPage().clickNext();
        Assertions.assertTrue(customPubExtractTreeViewPageLoaded, "Custom Pub Extract Tree View page didn't load when it should");

        customPubExtractTreeViewPage().clickExpandButtonNextToValueInTreeViewPane("SET IAS");
        customPubExtractTreeViewPage().clickExpandButtonNextToValueInTreeViewPane("T. III PUBLIC SERVICES AND REGULATION");
        customPubExtractTreeViewPage().clickExpandButtonNextToValueInTreeViewPane("Subt. 2 EMPLOYMENT SERVICES");
        customPubExtractTreeViewPage().selectMultipleValuesInTreeViewPaneValues(values);

        customPubExtractTreeViewPage().clickValueInSelectedRecordsPane("Selected Records");
        customPubExtractTreeViewPage().clickRightMultipleArrowButton();

        //verify records moved over to selected view properly
        boolean isValue0InSelectedRecordsGrid = customPubExtractTreeViewPage().isElementDisplayed(String.format(CustomPubExtractTreeViewPageElements.SELECTED_RECORDS_PANE_VALUE, values[0]));
        boolean isValue1InSelectedRecordsGrid = customPubExtractTreeViewPage().isElementDisplayed(String.format(CustomPubExtractTreeViewPageElements.SELECTED_RECORDS_PANE_VALUE, values[1]));
        boolean isValue2InSelectedRecordsGrid = customPubExtractTreeViewPage().isElementDisplayed(String.format(CustomPubExtractTreeViewPageElements.SELECTED_RECORDS_PANE_VALUE, values[2]));
        boolean isValue3InSelectedRecordsGrid = customPubExtractTreeViewPage().isElementDisplayed(String.format(CustomPubExtractTreeViewPageElements.SELECTED_RECORDS_PANE_VALUE, values[3]));

        Assertions.assertAll
        (
            () ->  Assertions.assertTrue(isValue0InSelectedRecordsGrid, values[0] + " is supposed to be seen in Selected Records Pane but was not"),
            () ->  Assertions.assertTrue(isValue1InSelectedRecordsGrid, values[1] + " is supposed to be seen in Selected Records Pane but was not"),
            () ->  Assertions.assertTrue(isValue2InSelectedRecordsGrid, values[2] + " is supposed to be seen in Selected Records Pane but was not"),
            () ->  Assertions.assertTrue(isValue3InSelectedRecordsGrid, values[3] + " is supposed to be seen in Selected Records Pane but was not")
        );

       //move 84B and 85 off of selected record view
        customPubExtractTreeViewPage().selectMultipleValuesInSelectedRecordsPaneValues(values[2], values[3]);
        customPubExtractTreeViewPage().clickLeftArrowButton();

        boolean isValue2NoLongerInSelectedRecordsGrid = customPubExtractTreeViewPage().isElementDisplayed(String.format(CustomPubExtractTreeViewPageElements.SELECTED_RECORDS_PANE_VALUE, values[2]));
        boolean isValue3NoLongerInSelectedRecordsGrid = customPubExtractTreeViewPage().isElementDisplayed(String.format(CustomPubExtractTreeViewPageElements.SELECTED_RECORDS_PANE_VALUE, values[3]));

        Assertions.assertAll
        (
            () ->  Assertions.assertFalse(isValue2NoLongerInSelectedRecordsGrid, values[2] + " is Supposed to not be seen in Selected Records Pane but was"),
            () ->  Assertions.assertFalse(isValue3NoLongerInSelectedRecordsGrid, values[3] + " is Supposed to not be seen in Selected Records Pane but was")
        );

        //move 84B and 85 back into selected records view
        customPubExtractTreeViewPage().selectMultipleValuesInTreeViewPaneValues(values[2], values[3]);

        customPubExtractTreeViewPage().clickValueInSelectedRecordsPane("Selected Records");
        customPubExtractTreeViewPage().clickRightMultipleArrowButton();

        boolean isValue2IsBackInSelectedRecordsGrid = customPubExtractTreeViewPage().isElementDisplayed(String.format(CustomPubExtractTreeViewPageElements.SELECTED_RECORDS_PANE_VALUE, values[2]));
        boolean isValue3IsBackInSelectedRecordsGrid = customPubExtractTreeViewPage().isElementDisplayed(String.format(CustomPubExtractTreeViewPageElements.SELECTED_RECORDS_PANE_VALUE, values[3]));

        Assertions.assertAll
        (
            () ->  Assertions.assertTrue(isValue2IsBackInSelectedRecordsGrid, values[2] + " is Supposed to be seen in Selected Records Pane again but was not"),
            () -> Assertions.assertTrue(isValue3IsBackInSelectedRecordsGrid, values[3] + " is Supposed to be seen in Selected Records Pane again but was not")
        );
    }
}
