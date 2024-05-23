package com.thomsonreuters.codes.codesbench.quality.tests.publishing.novus.citelinemanagement.citelinereferences;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.EDGE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.CITELINE;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.citelinemanagement.CitelineManagementDataMocking;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

public class CitelineReferencesDeleteTests extends TestService
{
    Connection uatConnection;
    String contentSetUK = "UK Legislative";
    int contentSetUKLegislativeCode = Integer.parseInt(ContentSets.UK_LEGISLATIVE.getCode());
    String contentSetUKLegislativeName = "RB.UKLG";

    @AfterEach
    public void closeConnection()
    {
        BaseDatabaseUtils.disconnect(uatConnection);
    }

    /**
     * STORY: Jets - 21840/21843/21842/21844 <br>
     * SUMMARY: This test verifies the deleting a cite line removes the cite line from the grid <br>
     * USER: CITELINE <br>
     */
    @Test
    @EDGE
    @CITELINE
    @LOG
    public void deleteCiteLineReferencesFunctionalityTest()
    {
        uatConnection = BaseDatabaseUtils.connectToDatabaseUAT();
        String ct1 = DateAndTimeUtils.getCurrentTimeHHmmss();
        String value = "Delete Test";
        CitelineManagementDataMocking.insertCiteline(uatConnection, contentSetUKLegislativeCode, ct1, value, value, value, contentSetUKLegislativeName);

        homePage().goToHomePage();
        loginPage().logIn();
        homePage().setMyContentSet(contentSetUK);

        boolean citeLineManagementPageLoaded = publishingMenu().goToPublishingNovusCitelineManagement();
        Assertions.assertTrue(citeLineManagementPageLoaded, "Cite Line Management Page Appeared");

        citeLineReferencesPage().openFilterMenuForCt1();
        gridHeaderFiltersPage().setFilterValue(ct1);
        String rowID = citeLineReferencesPage().getRowID(ct1);

        boolean deleteAlertAppears = citeLineReferencesPage().clickDeleteAndVerifyAlertAppears(rowID);
        Assertions.assertTrue(deleteAlertAppears, "the alert for deleting the cite did not appear when it should have");

        deleteCitelineReferencesPage().clickYes();
        boolean isCt1InGridAfterDelete = citeLineReferencesPage().isCT1InGrid(ct1);
        Assertions.assertFalse(isCt1InGridAfterDelete, "CT1 should no longer be in the grid after we deleted it");
    }

    /**
     * STORY: Jets - 21840/21843/21842/21844 <br>
     * SUMMARY: This test verifies deleting a cite line and clicking no on the message does not delete the cite line <br>
     * USER: CITELINE <br>
     */
    @Test
    @EDGE
    @CITELINE
    @LOG
    public void deleteCiteLineReferencesClickNoFunctionalityTest()
    {
        uatConnection = BaseDatabaseUtils.connectToDatabaseUAT();
        String ct1 = DateAndTimeUtils.getCurrentTimeHHmmss();
        String value = "Test";
        CitelineManagementDataMocking.insertCiteline(uatConnection, contentSetUKLegislativeCode, ct1, value, value, value, contentSetUKLegislativeName);

        homePage().goToHomePage();
        loginPage().logIn();
        homePage().setMyContentSet(contentSetUK);

        boolean citeLineManagementPageLoaded = publishingMenu().goToPublishingNovusCitelineManagement();
        Assertions.assertTrue(citeLineManagementPageLoaded, "Cite Line Management Page Appeared");

        citeLineReferencesPage().openFilterMenuForCt1();
        gridHeaderFiltersPage().setFilterValue(ct1);
        String rowID = citeLineReferencesPage().getRowID(ct1);

        boolean deleteAlertAppears = citeLineReferencesPage().clickDeleteAndVerifyAlertAppears(rowID);
        Assertions.assertTrue(deleteAlertAppears, "the alert for deleting the cite did not appear when it should have");

        deleteCitelineReferencesPage().clickNo();
        boolean ct1AppearsInGridStill = citeLineReferencesPage().verifySelectedCt1("0", ct1);
        Assertions.assertTrue(ct1AppearsInGridStill, "after clicking no ct1 should still be in the grid");
    }
}
