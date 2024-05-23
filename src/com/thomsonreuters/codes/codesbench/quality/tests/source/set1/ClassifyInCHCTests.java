package com.thomsonreuters.codes.codesbench.quality.tests.source.set1;

import com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.source.navigate.RenditionContextMenuElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.source.SourceDataMockingNew;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.source.datapod.SourceDatapodObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ClassifyInCHCTests extends TestService
{
    SourceDatapodObject  datapodObject;

    /**
     * STORY:  NA <br>
     * SUMMARY: Test to verify that a Legal user can filter for APVRS documents and Classify in CHC is disabled <br>
     * USER:  LEGAL <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void classifyInCHCAPVRSLegalTest()
    {
        datapodObject = SourceDataMockingNew.Iowa.Small.APVRS.insert();

        homePage().goToHomePage();
        loginPage().logIn();
        boolean sourceNavigateAppeared = sourceMenu().goToSourceC2012Navigate();
        Assertions.assertTrue(sourceNavigateAppeared, "Source Navigate Window appeared.");

        //If this test fails, please look at the javadoc comment for what to filter for or filter for an Iowa (Development) APRVS document
        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(String.valueOf(datapodObject.getRenditions().get(0).getDocNumber()));
        sourceNavigateFooterToolsPage().refreshTheGrid();

        sourceNavigateGridPage().rightClickFirstRendition();
        boolean classifyInCHCDisabled = renditionContextMenu().isElementDisabled(RenditionContextMenuElements.classifyInCHC);
        Assertions.assertTrue(classifyInCHCDisabled, "'Classify in CHC' was disabled.");
    }

    @AfterEach
    public void cleanUp()
    {
        if(datapodObject != null)
        {
            datapodObject.delete();
        }
    }
}
