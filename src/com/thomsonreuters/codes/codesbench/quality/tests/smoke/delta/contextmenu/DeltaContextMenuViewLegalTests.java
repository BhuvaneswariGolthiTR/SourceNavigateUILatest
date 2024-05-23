package com.thomsonreuters.codes.codesbench.quality.tests.smoke.delta.contextmenu;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.source.SourceDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.CommonDataMocking;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.source.SourceDataMockingNew;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.source.datapod.SourceDatapodObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils.disconnect;

public class DeltaContextMenuViewLegalTests extends TestService
{
    SourceDatapodObject datapodObject;
    Connection connection;
    String renditionUuid;

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - Checks Editor is read only when using View -> Delta(s) in context menu <br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void viewLegalTest()
    {
        // Log in and go to Source Navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(SourceDatabaseUtils.getDocNumberFromRenditionUuid(connection, renditionUuid));
        sourceNavigateFooterToolsPage().refreshTheGrid();

        //go to delta tab for first rendition and click deltas under view context menu
        sourceNavigateGridPage().clickFirstRendition();
        sourcePage().goToDeltaTab();
        sourceNavigateGridPage().firstDeltaViewContent();

        //check editor is read only
        boolean editorIsReadOnly = editorPage().checkEditorIsReadOnly();
        editorPage().closeDocumentWithNoChanges();
        Assertions.assertTrue(editorIsReadOnly, "Common Editor is read only");
    }

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - Checks Editor is read only when using View -> Notes in context menu <br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void viewNotesLegalTest()
    {
        // Log in and go to Source Navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(SourceDatabaseUtils.getDocNumberFromRenditionUuid(connection, renditionUuid));
        sourceNavigateFooterToolsPage().refreshTheGrid();

        //go to delta tab for first rendition and click Notes under View context menu
        sourceNavigateGridPage().clickFirstRendition();
        sourcePage().goToDeltaTab();
        sourceNavigateGridPage().rightClickFirstDelta();
        deltaContextMenu().goToViewDeltaNotes();

        //check Instruction Notes is read only
        boolean sourceLevelIsReadOnly = instructionsNotesPage().getSourceLevelInstructionNotesReadOnly();
        boolean sectionLevelIsReadOnly = instructionsNotesPage().getSectionLevelInstructionNotesReadOnly();
        boolean deltaLevelIsReadOnly = instructionsNotesPage().getDeltaLevelInstructionNotesReadOnly();
        instructionsNotesPage().clickOK();
        Assertions.assertTrue(sourceLevelIsReadOnly && sectionLevelIsReadOnly && deltaLevelIsReadOnly, "Instruction Notes are read only");
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
            sourceNavigateGridPage().unlockRenditionWithUuid(renditionUuid);
            datapodObject.delete();
        }
        disconnect(connection);
    }
}
