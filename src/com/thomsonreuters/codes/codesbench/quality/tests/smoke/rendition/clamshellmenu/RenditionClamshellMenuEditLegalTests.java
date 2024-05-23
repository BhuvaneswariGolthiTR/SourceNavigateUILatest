package com.thomsonreuters.codes.codesbench.quality.tests.smoke.rendition.clamshellmenu;

import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.source.SourceDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.CommonDataMocking;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.source.SourceDataMockingNew;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.source.datapod.SourceDatapodObject;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;

import java.sql.Connection;

import static com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils.disconnect;

public class RenditionClamshellMenuEditLegalTests extends TestService
{
    SourceDatapodObject datapodObject;
    Connection connection;
    String renditionUuid;

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - Verifies Difficulty Level is able to be changed using clamshell menu <br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void difficultyLevelLegalTest()
    {
        // Log in and go to Source Navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(SourceDatabaseUtils.getDocNumberFromRenditionUuid(connection, renditionUuid));
        sourceNavigateFooterToolsPage().refreshTheGrid();

        //edit difficulty level
        sourceNavigateGridPage().clickFirstRendition();
        clamshellPage().openSideBarEdit();
        renditionTabEditClamshellPage().clickDifficultyLevel(true,false);
        difficultyLevelPage().selectDifficultyLevel("E1");
        difficultyLevelPage().clickSave();
        boolean difficultyLevelAdded = sourceNavigateGridPage().getDifficultyLevelOfFirstItem().equals("E1");
        Assertions.assertTrue(difficultyLevelAdded,"Difficulty Level is added");
    }

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - Verifies Integration Properties are edited and saved using clamshell menu <br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void integrationPropertiesLegalTest()
    {
        String effectiveDateProvision = "Test1";
        String effectiveComments = "Test2";
        String queryNote = "Test3";
        String instructionNote = "Test4";
        String miscellaneous = "Test5";
        String currentDate = DateAndTimeUtils.getCurrentDateMMddyyyy();

        // Log in and go to Source Navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(SourceDatabaseUtils.getDocNumberFromRenditionUuid(connection, renditionUuid));
        sourceNavigateFooterToolsPage().refreshTheGrid();

        //click first rendition and open Integration Properties from the Edit clamshell menu
        sourceNavigateGridPage().clickFirstRendition();
        clamshellPage().openSideBarEdit();
        renditionTabEditClamshellPage().clickIntegrationProperties(true, false);
        integrationPropertiesPage().setEffectiveDate(currentDate);
        integrationPropertiesPage().checkDefaultCheckbox();
        integrationPropertiesPage().setEffectiveDateProvision(effectiveDateProvision);
        integrationPropertiesPage().setEffectiveComments(effectiveComments);
        integrationPropertiesPage().setQueryNote(queryNote);
        integrationPropertiesPage().setQueryDate(currentDate);
        integrationPropertiesPage().checkAddAsDateQueryCheckbox();
        integrationPropertiesPage().setInstructionNote(instructionNote);
        integrationPropertiesPage().setMiscellaneous(miscellaneous);
        integrationPropertiesPage().clickSave();

        //check integration properties are correct in grid
        boolean effectiveDateProvisionInGrid = sourceNavigateGridPage().getEffectiveDateProvisionOfFirstItem().equals(effectiveDateProvision);
        boolean effectiveCommentsInGrid = sourceNavigateGridPage().getEffectiveCommentsOfFirstItem().equals(effectiveComments);
        boolean instructionNoteInGrid = sourceNavigateGridPage().getInstructionNoteOfFirstItem().equals(instructionNote);
        boolean miscellaneousInGrid = sourceNavigateGridPage().getMiscellaneousOfFirstItem().equals(miscellaneous);
        boolean effectiveDate = sourceNavigateGridPage().getEffectiveDateOfFirstRendition().equals(currentDate);
        boolean queryDate = sourceNavigateGridPage().getQueryDateOfFirstItem().equals(currentDate);

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(effectiveDateProvisionInGrid, "Effective Date Provision is " + effectiveDateProvision),
            () -> Assertions.assertTrue(effectiveCommentsInGrid, "Effective Comments is " + effectiveComments),
            () -> Assertions.assertTrue(instructionNoteInGrid, "Instruction Note is " + instructionNote),
            () -> Assertions.assertTrue(miscellaneousInGrid, "Miscellaneous is " + miscellaneous),
            () -> Assertions.assertTrue(effectiveDate, "Effective Date is " + currentDate),
            () -> Assertions.assertTrue(queryDate, "Query Date is " + currentDate)
        );
    }

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - Verifies Raw XML Editor validates text properly and text can be saved using clamshell menu <br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void editXmlLegalTest()
    {
        String text = "official.status=\"APV\" year=\"2021\"";
        String test = "<!-- This is test abracadabra -->";

        // Log in and go to Source Navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(SourceDatabaseUtils.getDocNumberFromRenditionUuid(connection, renditionUuid));
        sourceNavigateFooterToolsPage().refreshTheGrid();

        //click first rendition and go to Clamshell Edit -> Rendition XML
        sourceNavigateGridPage().clickFirstRendition();
        clamshellPage().openSideBarEdit();
        renditionTabEditClamshellPage().clickRenditionXml(true, false);

        //check if given text is in editor. Enter given text into editor and check it is there. Validate text
        boolean textInXmlEditorAppears = sourceRawXmlEditorPage().isGivenTextInEditor(text);
        sourceRawXmlEditorPage().sendTextToXmlEditor(test);
        boolean newTextInEditorAppears = sourceRawXmlEditorPage().isGivenTextInEditor(test);
        boolean xmlEditorValid = sourceRawXmlEditorPage().clickValidate();

        //save and don't checkin changes and check editor window is still open and populated
        sourceRawXmlEditorPage().clickSave();
        sourceRawXmlDocumentClosurePage().clickCancel();
        boolean xmlEditorStaysOpen = sourceRawXmlEditorPage().isGivenTextInEditor(text);

        //save and checkin changes
        sourceRawXmlEditorPage().clickSave();
        sourceRawXmlDocumentClosurePage().clickCheckIn();

        //re-open XML editor and check changes were saved
        sourceNavigateGridPage().clickFirstRendition();
        clamshellPage().openSideBarEdit();
        renditionTabEditClamshellPage().clickRenditionXml(true, false);
        boolean textInXmlEditorSaved = sourceRawXmlEditorPage().isGivenTextInEditor(text);
        boolean newTextInEditorSaved = sourceRawXmlEditorPage().isGivenTextInEditor(test);

        //send invalid text to editor, validate, and close
        sourceRawXmlEditorPage().sendTextToXmlEditor("This is invalid test abracadabra -->");
        boolean xmlEditorValidated = sourceRawXmlEditorPage().clickValidate();
        sourceRawXmlEditorPage().clickClose();

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(textInXmlEditorAppears, "Raw XML Editor is populated"),
            () -> Assertions.assertTrue(newTextInEditorAppears, "User can enter valid text into XML Editor"),
            () -> Assertions.assertTrue(xmlEditorValid, "Valid text in XML Editor validated"),
            () -> Assertions.assertTrue(xmlEditorStaysOpen, "XML Editor stays open"),
            () -> Assertions.assertTrue(textInXmlEditorSaved, "Raw XML Editor is populated again"),
            () -> Assertions.assertFalse(newTextInEditorSaved, "Not saved new text did not appear in the XML editor"),
            () -> Assertions.assertFalse(xmlEditorValidated, "Invalid text in Rendition XML has not been validated")
        );
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
