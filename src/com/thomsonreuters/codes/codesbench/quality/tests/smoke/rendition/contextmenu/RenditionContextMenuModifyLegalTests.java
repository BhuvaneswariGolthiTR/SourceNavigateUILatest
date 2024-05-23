package com.thomsonreuters.codes.codesbench.quality.tests.smoke.rendition.contextmenu;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.source.SourceDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.CommonDataMocking;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.source.SourceDataMockingNew;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.source.datapod.SourceDatapodObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils.disconnect;

public class RenditionContextMenuModifyLegalTests extends TestService
{
    SourceDatapodObject datapodObject;
    Connection connection;
    String renditionUuid;

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - Veto rendition and checks veto status shows in grid <br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void vetoLegalTest()
    {
        // Log in and go to Source Navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(SourceDatabaseUtils.getDocNumberFromRenditionUuid(connection, renditionUuid));
        sourceNavigateFooterToolsPage().refreshTheGrid();

        //right click first rendition and select Modify -> Veto Rendition
        sourceNavigateGridPage().rightClickFirstRendition();
        renditionContextMenu().modifyVetoRendition();
        boolean vetoStatusInGrid = sourceNavigateGridPage().getInternalEnactmentStatus().equals("VETO");
        Assertions.assertTrue(vetoStatusInGrid, "Veto status appears in grid");
    }

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - Omit rendition and checks omit status shows in grid <br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void omitLegalTest()
    {
        // Log in and go to Source Navigate
        //Filter for the document: Validation: None, Duplicates: None, Deleted: Not Deleted,
        // Content Set: Iowa(Development), Year: 2012, Rendition Status: APV, Doc Type: HF, Doc Number: 2231
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(SourceDatabaseUtils.getDocNumberFromRenditionUuid(connection, renditionUuid));
        sourceNavigateFooterToolsPage().refreshTheGrid();

        //select omit rendition option in modify context menu and check status in grid
        sourceNavigateGridPage().rightClickFirstRendition();
        renditionContextMenu().modifyOmitRendition();
        boolean omitStatusInGrid = sourceNavigateGridPage().getInternalEnactmentStatus().equals("OMIT");
        Assertions.assertTrue(omitStatusInGrid, "Omit status appears in grid");
    }

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - Run cite locate and check grid refreshes <br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void citeLocateLegalTest()
    {
        // Log in and go to Source Navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(SourceDatabaseUtils.getDocNumberFromRenditionUuid(connection, renditionUuid));
        sourceNavigateFooterToolsPage().refreshTheGrid();

        //select cite locate option in modify context menu and check grid refreshes
        sourceNavigateGridPage().rightClickFirstRendition();
        boolean gridRefreshes = renditionContextMenu().modifyCiteLocate();
        Assertions.assertTrue(gridRefreshes, "Grid refreshes after clicking Cite Locate");
    }

    @BeforeEach
    public void mockData()
    {
        datapodObject = SourceDataMockingNew.Iowa.Small.APV.insert();
        renditionUuid = datapodObject.getRenditions().get(0).getRenditionUUID();

        connection = CommonDataMocking.connectToDatabase(environmentTag);
    }

    @AfterEach
    public void deleteMockedData()
    {
        if(datapodObject != null)
        {
            datapodObject.delete();
        }
        disconnect(connection);
    }
}
