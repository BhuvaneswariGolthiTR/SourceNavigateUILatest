package com.thomsonreuters.codes.codesbench.quality.tests.source.set1;

import com.thomsonreuters.codes.codesbench.quality.pageelements.source.SourceNavigatePageElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.CommonDataMocking;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.source.SourceDataMockingNew;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.source.datapod.SourceDatapodObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.RISK;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class LineageTabDisabledRiskTests extends TestService
{
    List<SourceDatapodObject> datapodObjects = new ArrayList<>();
    Connection connection;

    /**
     * STORY: N/A <br>
     * SUMMARY: Verifies 'Lineage Tab' option id disabled for risk user <br>
     * USER: Risk <br>
     */
    @Test
	@IE_EDGE_MODE
    @RISK
    @LOG
    public void multipleRenditionLineageRiskTest()
    {
        int renditionCount = 3;
        connection = CommonDataMocking.connectToDatabase(environmentTag);
        for(int i = 0; i < renditionCount; i++)
        {
            datapodObjects.add(SourceDataMockingNew.Iowa.Small.APV.insert());
        }

        String docNumbers =datapodObjects.get(0).getRenditions().get(0).getDocNumber() + ", " + datapodObjects.get(1).getRenditions().get(0).getDocNumber() + ", " + datapodObjects.get(2).getRenditions().get(0).getDocNumber();

        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();
        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(docNumbers);
        sourceNavigateFooterToolsPage().refreshTheGrid();

        sourceNavigateGridPage().clickFirstXRenditions(renditionCount);
        boolean lineageTabDisabled = sourceNavigateGridPage().isElementDisabled(SourceNavigatePageElements.LINEAGE_TAB_XPATH);
        Assertions.assertTrue(lineageTabDisabled, "Lineage tab was disabled");
    }

    @AfterEach
    public void cleanUp()
    {
        if(datapodObjects != null)
        {
            for (SourceDatapodObject datapodObject : datapodObjects)
            {
                datapodObject.delete();
            }
        }
    }
}
