package com.thomsonreuters.codes.codesbench.quality.tests.source.set1;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.source.SourceDataMockingNew;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.source.datapod.SourceDatapodObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class HighlightRenditionNavigateToLineageTabAndDeleteTests extends TestService
{

    SourceDatapodObject datapodObject;

    /**
     * STORY: N/A <br>
     * SUMMARY: highlights a rendition in source navigate and deletes/undeletes it in the lineage tab<br>
     * USER:  LEGAL <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void highlightRenditionNavigateToLineageTabAndDeleteTest()
    {
        datapodObject = SourceDataMockingNew.Iowa.Small.APV.insert();

        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(datapodObject.getRenditions().get(0).getDocNumber() + "");
        sourceNavigateFooterToolsPage().refreshTheGrid();

        sourceNavigateGridPage().clickFirstRendition();
        sourceNavigateTabsPage().goToLineageTab();

        sourceNavigateGridPage().rightClickFirstRendition();
        boolean deleteConfirmationWindowAppeared = renditionContextMenu().modifyDeleteRendition();
        Assertions.assertTrue(deleteConfirmationWindowAppeared, "The delete confirmation window appeared.");

        sourcePage().goToLinearNavigate();

        boolean firstIsDeleted = sourceNavigateGridPage().firstRenditionDeleted();
        Assertions.assertTrue(firstIsDeleted, "The selected document has been deleted.");
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
