package com.thomsonreuters.codes.codesbench.quality.tests.source.set1;

import com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.source.navigate.RenditionContextMenuElements;
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
import org.junit.jupiter.api.Test;

import java.sql.Connection;

public class EditLockedRenditionTests extends TestService
{
    SourceDatapodObject datapodObject;
    Connection connection;

    /**
     * STORY: PNR_20000 <br>
     * SUMMARY: Test to verify that a locked document cannot be edited <br>
     * USER: Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void editLockedRenditionTest()
    {
        datapodObject = SourceDataMockingNew.Iowa.Small.APV.insert();
        connection = CommonDataMocking.connectToDatabase(environmentTag);
        SourceDatabaseUtils.createLock(connection, datapodObject.getRenditions().get(0).getRenditionUUID(), riskUser().getUsername());

        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(String.valueOf(datapodObject.getRenditions().get(0).getDocNumber()));
        sourceNavigateFooterToolsPage().refreshTheGrid();

        sourceNavigateGridPage().rightClickFirstRendition();
        renditionContextMenu().edit();

        boolean isDisabled = renditionContextMenu().isElementDisabled(RenditionContextMenuElements.editRendition);
        Assertions.assertTrue(isDisabled, "The Rendition was disabled");
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
