package com.thomsonreuters.codes.codesbench.quality.tests.publishing.novus.citelinemanagement.normalizedcite;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.EDGE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.CITELINE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.citelinemanagement.CitelineManagementDataMocking;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

public class NormalizedCiteDeleteTests extends TestService
{
    Connection connection;
    String citationPrefix;
    private static final String contentSetName = "RB.UKLG";

    @BeforeEach
    public void mockUpCitation()
    {
        connection = BaseDatabaseUtils.connectToDatabaseUAT();
        //name for citation prefix/condensed prefix
        citationPrefix = "test" + DateAndTimeUtils.getCurrentDateAndTimeyyyyMMddHHmm();
        //Create cite through database to modify
        CitelineManagementDataMocking.insertNormalizedCite(connection,  0, citationPrefix, citationPrefix+"condensed", Integer.parseInt(ContentSets.UK_LEGISLATIVE.getCode()), contentSetName);
    }

    @AfterEach
    public void closeConnection()
    {
        CitelineManagementDataMocking.deleteNormalizedCite(connection, citationPrefix, contentSetName);
        BaseDatabaseUtils.disconnect(connection);
    }

    /**
     * STORY: JETS-21847/21848/21849 <br>
     * SUMMARY: Verifies cite can be deleted from Normalized Cite page. <br>
     * USER: Citeline <br>
     */
    @Test
    @EDGE
    @CITELINE
    @LOG
    public void deleteCiteFromNormalizedCitePageTest()
    {
        //navigate to page
        homePage().goToHomePage();
        loginPage().logIn();
        homePage().setMyContentSet(ContentSets.UK_LEGISLATIVE.getName());
        publishingMenu().goToPublishingNovusCitelineManagement();
        normalizedCitePage().goToNormalizedCiteTab();

        //filter for created cite, verify it was created
        normalizedCitePage().openFilterMenuForCitationPrefix();
        gridHeaderFiltersPage().setFilterValue(citationPrefix);
        boolean wasCiteCreated = normalizedCitePage().isCitationPrefixInGrid(citationPrefix);
        Assertions.assertTrue(wasCiteCreated, "Citation should be found in the grid");

        //Grab rowID of new citation
        String rowID = normalizedCitePage().getRowID(citationPrefix);

        //Verifies cite is not deleted from Normalized Cite page when delete is cancelled
        boolean deleteAlertAppeared1 = normalizedCitePage().clickDeleteAndVerifyAlertAppears(rowID);
        Assertions.assertTrue(deleteAlertAppeared1, "The delete alert appeared 1");
        normalizedCitePage().clickNo();
        boolean wasCiteNotDeleted = normalizedCitePage().isCitationPrefixInGrid(citationPrefix);
        Assertions.assertTrue(wasCiteNotDeleted, "Citation should not be deleted from the grid");

        //Verifies cite can be deleted from Normalized Cite page
        boolean deleteAlertAppeared2 = normalizedCitePage().clickDeleteAndVerifyAlertAppears(rowID);
        Assertions.assertTrue(deleteAlertAppeared2, "The delete alert appeared 2");
        normalizedCitePage().clickYes();
        boolean wasCiteDeleted = normalizedCitePage().isCitationPrefixInGrid(citationPrefix);
        Assertions.assertFalse(wasCiteDeleted, "Citation should be deleted from the grid");
    }
}
