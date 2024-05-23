package com.thomsonreuters.codes.codesbench.quality.tests.source.set1;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class LineageTabDisabledTests extends TestService
{

    /**
     * STORY: N/A <br>
     * SUMMARY: checks if the lineage Tab is disabled from the section tab <br>
     * USER: LEGAL <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void integrateTextOfSubsectionDeltaTest()
    {
        String contentSet = "Colorado (Development)";
        String year = "2011";
        String docType = "S";
        String docNumber = "2";
        String contentType = "BILL";

        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterContentSet(contentSet);
        sourceNavigateFiltersAndSortsPage().setFilterYear(year);
        sourceNavigateFiltersAndSortsPage().setFilterDocType(docType);
        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(docNumber);
        sourceNavigateFiltersAndSortsPage().setFilterContentType(contentType);
        sourceNavigateFooterToolsPage().refreshTheGrid();

        sourceNavigateGridPage().clickFirstRendition();

        sourceNavigateTabsPage().goToSectionTab();
        
        sourceNavigateGridPage().clickFirstRendition();
        boolean lineageTabDisabled = sourceNavigateTabsPage().lineageTabDisabled();
        Assertions.assertTrue(lineageTabDisabled, "Verifying that the Lineage tab is disabled");
    }
}
