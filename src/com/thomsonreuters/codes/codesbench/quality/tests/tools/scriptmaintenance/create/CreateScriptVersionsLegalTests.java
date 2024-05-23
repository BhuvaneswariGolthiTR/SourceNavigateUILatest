package com.thomsonreuters.codes.codesbench.quality.tests.tools.scriptmaintenance.create;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.EDGE;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.tools.ScriptMaintenance.ScriptMaintenanceDatabaseConstants;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.tools.ScriptMaintenance.ScriptMaintenanceDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.regex.RegexUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;

import java.sql.Connection;
import java.util.List;

import static com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.*;
import static com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets.IOWA_DEVELOPMENT;
import static com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets.USCA_DEVELOPMENT;

public class CreateScriptVersionsLegalTests extends TestService
{
    private String userName = "";

    @AfterEach
    public void deepDeleteScriptMaintenanceTestingTags()
    {
        Connection uatConnection = BaseDatabaseUtils.connectToDatabaseUAT();
        ScriptMaintenanceDatabaseUtils.deepDeleteScriptMaintenanceTestingTags(uatConnection, ScriptMaintenanceDatabaseConstants.SCRIPT_NAME, userName, ScriptMaintenanceDatabaseConstants.PUB_TAG);
        BaseDatabaseUtils.commit(uatConnection);
        BaseDatabaseUtils.disconnect(uatConnection);
    }

    @BeforeEach
    public void createScriptForTests()
    {
        userName = user().getUsername().contains("c") ? "u" + user().getUsername() : user().getUsername();
        Connection uatConnection = BaseDatabaseUtils.connectToDatabaseUAT();
        ScriptMaintenanceDatabaseUtils.insertScript(uatConnection, ScriptMaintenanceDatabaseConstants.SCRIPT_NAME, userName, ScriptMaintenanceDatabaseConstants.SCRIPT_NUM, ScriptMaintenanceDatabaseConstants.PUB_TAG, ScriptMaintenanceDatabaseConstants.VERSION_DESCRIPTION, ScriptMaintenanceDatabaseConstants.SCRIPT_ID, IOWA_DEVELOPMENT.getCode());
        BaseDatabaseUtils.disconnect(uatConnection);
    }

    /**
     * STORY/BUGS - HALCYONST-3787, HALCYONST-5175 <br>
     * SUMMARY - Creates a script in a single content set <br>
     * USER - Legal <br>
     */
    @Test
	@EDGE
    @LEGAL
    @LOG
    public void createScriptVersionSingleContentSetLegalTest()
    {
        userName = user().getUsername().contains("c") ? "u" + user().getUsername() : user().getUsername();

        homePage().goToHomePage();
        loginPage().logIn();

        boolean scriptMaintenancePageAppears = toolsMenu().goToScriptMaintenance();
        Assertions.assertTrue(scriptMaintenancePageAppears, "Script Maintenance page appeared");

        boolean scriptAppearsInGrid = scriptMaintenanceGridPage().scriptExists(ScriptMaintenanceDatabaseConstants.PUB_TAG, ScriptMaintenanceDatabaseConstants.SCRIPT_NAME, ScriptMaintenanceDatabaseConstants.VERSION_DESCRIPTION, "Incomplete");
        Assertions.assertTrue(scriptAppearsInGrid, "Created script appears in grid");

        scriptMaintenanceGridPage().selectScript(ScriptMaintenanceDatabaseConstants.PUB_TAG, ScriptMaintenanceDatabaseConstants.SCRIPT_NAME, ScriptMaintenanceDatabaseConstants.VERSION_DESCRIPTION, "Incomplete");
        boolean createScriptVersionWindowAppeared = scriptMaintenanceClamshellPage().goToCreateScriptVersion();
        Assertions.assertTrue(createScriptVersionWindowAppeared, "Create Script Version page appeared");

//        boolean scriptNameFieldIsDisabled = createScriptVersionPage().isElementDisabled(CreateScriptVersionPageElements.scriptName); TODO: When issue HALCYONST-15514 is solved uncomment out these checks
//        Assertions.assertTrue(scriptNameFieldIsDisabled, "Script Name field is disabled");
//
//        boolean versionDescriptionFieldIsDisabled = createScriptVersionPage().isElementDisabled(CreateScriptVersionPageElements.versionDescription);
//        Assertions.assertFalse(versionDescriptionFieldIsDisabled, "Version Description field is disabled");
//
//        boolean pubTagFieldIsDisabled = createScriptVersionPage().isElementDisabled(CreateScriptVersionPageElements.pubTag);
//        Assertions.assertTrue(pubTagFieldIsDisabled, "Pub Tag field is disabled");

        List<String> selectedContentSets = createScriptVersionPage().getSelectedContentSets();
        boolean contentSetsMatch = selectedContentSets.contains("Iowa (Development)") && selectedContentSets.size() == 1;
        Assertions.assertTrue(contentSetsMatch, "Content Sets match");

        createScriptVersionPage().setVersionDescription(ScriptMaintenanceDatabaseConstants.VERSION_DESCRIPTION + "Version");
        DateAndTimeUtils.takeNap(DateAndTimeUtils.FOUR_SECONDS);
        createScriptVersionPage().clickSubmit();

        boolean versionScriptAppearsInGrid = scriptMaintenanceGridPage().scriptExists(ScriptMaintenanceDatabaseConstants.PUB_TAG, ScriptMaintenanceDatabaseConstants.SCRIPT_NAME, ScriptMaintenanceDatabaseConstants.VERSION_DESCRIPTION + "Version", "Incomplete");
        Assertions.assertTrue(versionScriptAppearsInGrid, "Created script version appears in grid");

        String scriptID = scriptMaintenanceGridPage().getScriptIdOfScript(ScriptMaintenanceDatabaseConstants.PUB_TAG, ScriptMaintenanceDatabaseConstants.SCRIPT_NAME, ScriptMaintenanceDatabaseConstants.VERSION_DESCRIPTION + "Version", "Incomplete");
        boolean iowaScriptIdIsCorrectFormat = RegexUtils.matchesRegex(scriptID, "1-\\d{4}-002");
        Assertions.assertTrue(iowaScriptIdIsCorrectFormat, "Script Id is the correct format");

        Connection uatConnection = BaseDatabaseUtils.connectToDatabaseUAT();
        int script999count = ScriptMaintenanceDatabaseUtils.getScript999Count(uatConnection, ScriptMaintenanceDatabaseConstants.SCRIPT_NAME, userName);
        BaseDatabaseUtils.disconnect(uatConnection);
        Assertions.assertEquals(1, script999count, "There is a Script 999 version within the database for the new Script");
    }

    /**
     * STORY/BUGS - HALCYONST-3787, HALCYONST-5175 <br>
     * SUMMARY - Creates a script in multiple content sets <br>
     * USER - Legal <br>
     */
    @Test
	@EDGE
    @LEGAL
    @LOG
    public void createScriptVersionMultipleContentSetsLegalTest()
    {
        userName = user().getUsername().contains("c") ? "u" + user().getUsername() : user().getUsername();

        Connection uatConnection = BaseDatabaseUtils.connectToDatabaseUAT();
        ScriptMaintenanceDatabaseUtils.insertSelectRangeScriptRuleIntoGivenScript(uatConnection, ScriptMaintenanceDatabaseConstants.SCRIPT_ID, userName, "startM","endM", 1,3); //Look in the SCRIPT_ACTION table in the db for other action IDs
        ScriptMaintenanceDatabaseUtils.insertScriptIntoOtherContentSets(uatConnection, ScriptMaintenanceDatabaseConstants.SCRIPT_ID,USCA_DEVELOPMENT.getCode());
        BaseDatabaseUtils.disconnect(uatConnection);

        homePage().goToHomePage();
        loginPage().logIn();

        boolean scriptMaintenancePageAppears = toolsMenu().goToScriptMaintenance();
        Assertions.assertTrue(scriptMaintenancePageAppears, "Script Maintenance page appeared");

        boolean iowaScriptAppearsInGrid = scriptMaintenanceGridPage().scriptExists(ScriptMaintenanceDatabaseConstants.PUB_TAG, ScriptMaintenanceDatabaseConstants.SCRIPT_NAME, ScriptMaintenanceDatabaseConstants.VERSION_DESCRIPTION, "Incomplete");
        Assertions.assertTrue(iowaScriptAppearsInGrid, "Iowa created script appears in grid");

        scriptMaintenanceGridPage().selectScript(ScriptMaintenanceDatabaseConstants.PUB_TAG, ScriptMaintenanceDatabaseConstants.SCRIPT_NAME, ScriptMaintenanceDatabaseConstants.VERSION_DESCRIPTION, "Incomplete");
        boolean createScriptVersionWindowAppeared = scriptMaintenanceClamshellPage().goToCreateScriptVersion();
        Assertions.assertTrue(createScriptVersionWindowAppeared, "Create Script Version page appeared");

//        boolean scriptNameFieldIsDisabled = createScriptVersionPage().isElementDisabled(CreateScriptVersionPageElements.scriptName); TODO: When issue HALCYONST-15514 is solved uncomment out these checks
//        Assertions.assertTrue(scriptNameFieldIsDisabled, "Script Name field is disabled");
//
//        boolean versionDescriptionFieldIsDisabled = createScriptVersionPage().isElementDisabled(CreateScriptVersionPageElements.versionDescription);
//        Assertions.assertFalse(versionDescriptionFieldIsDisabled, "Version Description field is disabled");
//
//        boolean pubTagFieldIsDisabled = createScriptVersionPage().isElementDisabled(CreateScriptVersionPageElements.pubTag);
//        Assertions.assertTrue(pubTagFieldIsDisabled, "Pub Tag field is disabled");

        List<String> selectedContentSets = createScriptVersionPage().getSelectedContentSets();
        boolean contentSetsMatch = selectedContentSets.contains("Iowa (Development)") && selectedContentSets.contains("USCA(Development)") && selectedContentSets.size() == 2;
        Assertions.assertTrue(contentSetsMatch, "Content Sets match");

        createScriptVersionPage().setVersionDescription(ScriptMaintenanceDatabaseConstants.VERSION_DESCRIPTION + "Version");
        DateAndTimeUtils.takeNap(DateAndTimeUtils.FOUR_SECONDS);
        createScriptVersionPage().clickSubmit();
        boolean versionScriptAppearsInGrid = scriptMaintenanceGridPage().scriptExists(ScriptMaintenanceDatabaseConstants.PUB_TAG, ScriptMaintenanceDatabaseConstants.SCRIPT_NAME, ScriptMaintenanceDatabaseConstants.VERSION_DESCRIPTION + "Version", "Incomplete");
        Assertions.assertTrue(versionScriptAppearsInGrid, "Created script version appears in grid");

        String scriptID = scriptMaintenanceGridPage().getScriptIdOfScript(ScriptMaintenanceDatabaseConstants.PUB_TAG, ScriptMaintenanceDatabaseConstants.SCRIPT_NAME, ScriptMaintenanceDatabaseConstants.VERSION_DESCRIPTION + "Version", "Incomplete");
        boolean iowaScriptIdIsCorrectFormat = RegexUtils.matchesRegex(scriptID, "1-\\d{4}-002");
        Assertions.assertTrue(iowaScriptIdIsCorrectFormat, "Script Id is the correct format");

        scriptMaintenanceGridPage().closeCurrentWindowIgnoreDialogue();
        homePage().switchToPage();
        homePage().setMyContentSet("USCA(Development)");
        toolsMenu().goToScriptMaintenance();
        boolean uscaScriptAppearsInGrid = scriptMaintenanceGridPage().scriptExists(ScriptMaintenanceDatabaseConstants.PUB_TAG, ScriptMaintenanceDatabaseConstants.SCRIPT_NAME, ScriptMaintenanceDatabaseConstants.VERSION_DESCRIPTION, "Incomplete");
        Assertions.assertTrue(uscaScriptAppearsInGrid, "USCA created script appears in grid");

        boolean uscaVersionScriptAppearsInGrid = scriptMaintenanceGridPage().scriptExists(ScriptMaintenanceDatabaseConstants.PUB_TAG, ScriptMaintenanceDatabaseConstants.SCRIPT_NAME, ScriptMaintenanceDatabaseConstants.VERSION_DESCRIPTION + "Version", "Incomplete");
        Assertions.assertTrue(uscaVersionScriptAppearsInGrid, "USCA Created script version appears in grid");
    }

    /**
     * STORY/BUGS - HALCYONST-3787 <br>
     * SUMMARY - Cancels the creation of a script in a single content set <br>
     * USER - Legal <br>
     */
    @Test
	@EDGE
    @LEGAL
    @LOG
    public void cancelCreateScriptVersionLegalTest()
    {
        userName = user().getUsername().contains("c") ? "u" + user().getUsername() : user().getUsername();

        Connection uatConnection = BaseDatabaseUtils.connectToDatabaseUAT();
        ScriptMaintenanceDatabaseUtils.insertSelectRangeScriptRuleIntoGivenScript(uatConnection, ScriptMaintenanceDatabaseConstants.SCRIPT_ID, userName, "startM","endM", 1,3); //Look in the SCRIPT_ACTION table in the db for other action IDs
        ScriptMaintenanceDatabaseUtils.insertScriptIntoOtherContentSets(uatConnection, ScriptMaintenanceDatabaseConstants.SCRIPT_ID,USCA_DEVELOPMENT.getCode());
        BaseDatabaseUtils.disconnect(uatConnection);

        homePage().goToHomePage();
        loginPage().logIn();

        boolean scriptMaintenancePageAppears = toolsMenu().goToScriptMaintenance();
        Assertions.assertTrue(scriptMaintenancePageAppears, "Script Maintenance page appeared");

        boolean scriptAppearsInGrid = scriptMaintenanceGridPage().scriptExists(ScriptMaintenanceDatabaseConstants.PUB_TAG, ScriptMaintenanceDatabaseConstants.SCRIPT_NAME, ScriptMaintenanceDatabaseConstants.VERSION_DESCRIPTION, "Incomplete");
        Assertions.assertTrue(scriptAppearsInGrid, "Created script appears in grid");

        scriptMaintenanceGridPage().selectScript(ScriptMaintenanceDatabaseConstants.PUB_TAG, ScriptMaintenanceDatabaseConstants.SCRIPT_NAME, ScriptMaintenanceDatabaseConstants.VERSION_DESCRIPTION, "Incomplete");
        boolean createScriptVersionWindowAppeared = scriptMaintenanceClamshellPage().goToCreateScriptVersion();
        Assertions.assertTrue(createScriptVersionWindowAppeared, "Create Script Version page appeared");

//        boolean scriptNameFieldIsDisabled = createScriptVersionPage().isElementDisabled(CreateScriptVersionPageElements.scriptName); TODO: When issue HALCYONST-15514 is solved uncomment out these checks
//        Assertions.assertTrue(scriptNameFieldIsDisabled, "Script Name field is disabled");
//
//        boolean versionDescriptionFieldIsDisabled = createScriptVersionPage().isElementDisabled(CreateScriptVersionPageElements.versionDescription);
//        Assertions.assertFalse(versionDescriptionFieldIsDisabled, "Version Description field is disabled");
//
//        boolean pubTagFieldIsDisabled = createScriptVersionPage().isElementDisabled(CreateScriptVersionPageElements.pubTag);
//        Assertions.assertTrue(pubTagFieldIsDisabled, "Pub Tag field is disabled");

        List<String> selectedContentSets = createScriptVersionPage().getSelectedContentSets();
        boolean contentSetsMatch = selectedContentSets.contains("Iowa (Development)");
        Assertions.assertTrue(contentSetsMatch, "Content Sets match");

        createScriptVersionPage().setVersionDescription(ScriptMaintenanceDatabaseConstants.VERSION_DESCRIPTION + "Version");
        createScriptVersionPage().clickCancel();
        boolean versionScriptAppearsInGrid = scriptMaintenanceGridPage().scriptExists(ScriptMaintenanceDatabaseConstants.PUB_TAG, ScriptMaintenanceDatabaseConstants.SCRIPT_NAME, ScriptMaintenanceDatabaseConstants.VERSION_DESCRIPTION + "Version", "Incomplete");
        Assertions.assertFalse(versionScriptAppearsInGrid, "Created script version appears in grid");
    }

    /**
     * STORY/BUGS - HALCYONST-3787, HALCYONST-5175 <br>
     * SUMMARY - Creates a duplicate script in a single content set <br>
     * USER - Legal <br>
     */
    @Test
	@EDGE
    @LEGAL
    @LOG
    public void createDuplicateScriptVersionsLegalTest()
    {
        userName = user().getUsername().contains("c") ? "u" + user().getUsername() : user().getUsername();

        homePage().goToHomePage();
        loginPage().logIn();

        boolean scriptMaintenancePageAppears = toolsMenu().goToScriptMaintenance();
        Assertions.assertTrue(scriptMaintenancePageAppears, "Script Maintenance page appeared");

        boolean scriptAppearsInGrid = scriptMaintenanceGridPage().scriptExists(ScriptMaintenanceDatabaseConstants.PUB_TAG, ScriptMaintenanceDatabaseConstants.SCRIPT_NAME, ScriptMaintenanceDatabaseConstants.VERSION_DESCRIPTION, "Incomplete");
        Assertions.assertTrue(scriptAppearsInGrid, "Created script appears in grid");

        scriptMaintenanceGridPage().selectScript(ScriptMaintenanceDatabaseConstants.PUB_TAG, ScriptMaintenanceDatabaseConstants.SCRIPT_NAME, ScriptMaintenanceDatabaseConstants.VERSION_DESCRIPTION, "Incomplete");
        boolean createScriptVersionWindowAppeared = scriptMaintenanceClamshellPage().goToCreateScriptVersion();
        Assertions.assertTrue(createScriptVersionWindowAppeared, "Create Script Version page appeared");

//        boolean scriptNameFieldIsDisabled = createScriptVersionPage().isElementDisabled(CreateScriptVersionPageElements.scriptName); TODO: When issue HALCYONST-15514 is solved uncomment out these checks
//        Assertions.assertTrue(scriptNameFieldIsDisabled, "Script Name field is disabled");
//
//        boolean versionDescriptionFieldIsDisabled = createScriptVersionPage().isElementDisabled(CreateScriptVersionPageElements.versionDescription);
//        Assertions.assertFalse(versionDescriptionFieldIsDisabled, "Version Description field is disabled");
//
//        boolean pubTagFieldIsDisabled = createScriptVersionPage().isElementDisabled(CreateScriptVersionPageElements.pubTag);
//        Assertions.assertTrue(pubTagFieldIsDisabled, "Pub Tag field is disabled");

        List<String> selectedContentSets = createScriptVersionPage().getSelectedContentSets();
        boolean contentSetsMatch = selectedContentSets.contains("Iowa (Development)");
        Assertions.assertTrue(contentSetsMatch, "Content Sets match");

        createScriptVersionPage().setVersionDescription(ScriptMaintenanceDatabaseConstants.VERSION_DESCRIPTION + "Version");
        DateAndTimeUtils.takeNap(DateAndTimeUtils.FOUR_SECONDS);
        createScriptVersionPage().clickSubmit();
        boolean versionScriptAppearsInGrid = scriptMaintenanceGridPage().scriptExists(ScriptMaintenanceDatabaseConstants.PUB_TAG, ScriptMaintenanceDatabaseConstants.SCRIPT_NAME, ScriptMaintenanceDatabaseConstants.VERSION_DESCRIPTION + "Version", "Incomplete");
        Assertions.assertTrue(versionScriptAppearsInGrid, "Created script version appears in grid");

        String scriptID = scriptMaintenanceGridPage().getScriptIdOfScript(ScriptMaintenanceDatabaseConstants.PUB_TAG, ScriptMaintenanceDatabaseConstants.SCRIPT_NAME, ScriptMaintenanceDatabaseConstants.VERSION_DESCRIPTION + "Version", "Incomplete");
        boolean iowaScriptIdIsCorrectFormat = RegexUtils.matchesRegex(scriptID, "1-\\d{4}-002");
        Assertions.assertTrue(iowaScriptIdIsCorrectFormat, "Script Id is the correct format");

        scriptMaintenanceGridPage().selectScript(ScriptMaintenanceDatabaseConstants.PUB_TAG, ScriptMaintenanceDatabaseConstants.SCRIPT_NAME, ScriptMaintenanceDatabaseConstants.VERSION_DESCRIPTION + "Version", "Incomplete");
        boolean createScriptVersionWindowAppeared2 = scriptMaintenanceClamshellPage().goToCreateScriptVersion();
        Assertions.assertTrue(createScriptVersionWindowAppeared2, "Create Script Version page appeared 2");

//        boolean scriptNameFieldIsDisabled2 = createScriptVersionPage().isElementDisabled(CreateScriptVersionPageElements.scriptName); TODO: When issue HALCYONST-15514 is solved uncomment out these checks
//        Assertions.assertTrue(scriptNameFieldIsDisabled2, "Script Name field is disabled 2");
//
//        boolean versionDescriptionFieldIsDisabled2 = createScriptVersionPage().isElementDisabled(CreateScriptVersionPageElements.versionDescription);
//        Assertions.assertFalse(versionDescriptionFieldIsDisabled2, "Version Description field is disabled 2");
//
//        boolean pubTagFieldIsDisabled2 = createScriptVersionPage().isElementDisabled(CreateScriptVersionPageElements.pubTag);
//        Assertions.assertTrue(pubTagFieldIsDisabled2, "Pub Tag field is disabled 2");

        List<String> selectedContentSets2 = createScriptVersionPage().getSelectedContentSets();
        boolean contentSetsMatch2 = selectedContentSets2.contains("Iowa (Development)") && selectedContentSets2.size() == 1;
        Assertions.assertTrue(contentSetsMatch2, "Content Sets match 2");

        createScriptVersionPage().setVersionDescription(ScriptMaintenanceDatabaseConstants.VERSION_DESCRIPTION + "Version2");
        DateAndTimeUtils.takeNap(DateAndTimeUtils.FOUR_SECONDS);
        createScriptVersionPage().clickSubmit();
        versionScriptAppearsInGrid = scriptMaintenanceGridPage().scriptExists(ScriptMaintenanceDatabaseConstants.PUB_TAG, ScriptMaintenanceDatabaseConstants.SCRIPT_NAME, ScriptMaintenanceDatabaseConstants.VERSION_DESCRIPTION + "Version", "Incomplete");
        Assertions.assertTrue(versionScriptAppearsInGrid, "Created script version appears in grid");

        scriptID = scriptMaintenanceGridPage().getScriptIdOfScript(ScriptMaintenanceDatabaseConstants.PUB_TAG, ScriptMaintenanceDatabaseConstants.SCRIPT_NAME, ScriptMaintenanceDatabaseConstants.VERSION_DESCRIPTION + "Version", "Incomplete");
        iowaScriptIdIsCorrectFormat = RegexUtils.matchesRegex(scriptID, "1-\\d{4}-002");
        Assertions.assertTrue(iowaScriptIdIsCorrectFormat, "Script Id is the correct format");

        boolean versionScriptAppearsInGrid2 = scriptMaintenanceGridPage().scriptExists(ScriptMaintenanceDatabaseConstants.PUB_TAG, ScriptMaintenanceDatabaseConstants.SCRIPT_NAME, ScriptMaintenanceDatabaseConstants.VERSION_DESCRIPTION + "Version2", "Incomplete");
        Assertions.assertTrue(versionScriptAppearsInGrid2, "Created script version appears in grid 2");
    }

    /**
     * STORY/BUGS - HALCYONST-3787, HALCYONST-5175 <br>
     * SUMMARY - Creates a Version of a Script that has rules <br>
     * USER - Legal <br>
     */
    @Test
	@EDGE
    @LEGAL
    @LOG
    public void createScriptVersionWithRulesLegalTest()
    {
        userName = user().getUsername().contains("c") ? "u" + user().getUsername() : user().getUsername();

        Connection uatConnection = BaseDatabaseUtils.connectToDatabaseUAT();
        ScriptMaintenanceDatabaseUtils.insertSelectRangeScriptRuleIntoGivenScript(uatConnection, ScriptMaintenanceDatabaseConstants.SCRIPT_ID, userName, "startM","endM", 1,3); //Look in the SCRIPT_ACTION table in the db for other action IDs
        BaseDatabaseUtils.disconnect(uatConnection);

        homePage().goToHomePage();
        loginPage().logIn();

        boolean scriptMaintenancePageAppears = toolsMenu().goToScriptMaintenance();
        Assertions.assertTrue(scriptMaintenancePageAppears, "Script Maintenance page appeared");

        boolean scriptAppearsInGrid = scriptMaintenanceGridPage().scriptExists(ScriptMaintenanceDatabaseConstants.PUB_TAG, ScriptMaintenanceDatabaseConstants.SCRIPT_NAME, ScriptMaintenanceDatabaseConstants.VERSION_DESCRIPTION, "Incomplete");
        Assertions.assertTrue(scriptAppearsInGrid, "Created script appears in grid");

        scriptMaintenanceGridPage().selectScript(ScriptMaintenanceDatabaseConstants.PUB_TAG, ScriptMaintenanceDatabaseConstants.SCRIPT_NAME, ScriptMaintenanceDatabaseConstants.VERSION_DESCRIPTION, "Incomplete");
        boolean createScriptVersionWindowAppeared = scriptMaintenanceClamshellPage().goToCreateScriptVersion();
        Assertions.assertTrue(createScriptVersionWindowAppeared, "Create Script Version page appeared");

        createScriptVersionPage().setVersionDescription(ScriptMaintenanceDatabaseConstants.VERSION_DESCRIPTION + "Version");
        DateAndTimeUtils.takeNap(DateAndTimeUtils.FOUR_SECONDS);
        createScriptVersionPage().clickSubmit();
        boolean versionScriptAppearsInGrid = scriptMaintenanceGridPage().scriptExists(ScriptMaintenanceDatabaseConstants.PUB_TAG, ScriptMaintenanceDatabaseConstants.SCRIPT_NAME, ScriptMaintenanceDatabaseConstants.VERSION_DESCRIPTION + "Version", "Incomplete");
        Assertions.assertTrue(versionScriptAppearsInGrid, "Created script version appears in grid");

        String scriptID = scriptMaintenanceGridPage().getScriptIdOfScript(ScriptMaintenanceDatabaseConstants.PUB_TAG, ScriptMaintenanceDatabaseConstants.SCRIPT_NAME, ScriptMaintenanceDatabaseConstants.VERSION_DESCRIPTION + "Version", "Incomplete");
        boolean iowaScriptIdIsCorrectFormat = RegexUtils.matchesRegex(scriptID, "1-\\d{4}-002");
        Assertions.assertTrue(iowaScriptIdIsCorrectFormat, "Script Id is the correct format");

        scriptMaintenanceGridPage().selectScript(ScriptMaintenanceDatabaseConstants.PUB_TAG, ScriptMaintenanceDatabaseConstants.SCRIPT_NAME, ScriptMaintenanceDatabaseConstants.VERSION_DESCRIPTION + "Version", "Incomplete");
        boolean editScriptRulesPageAppeared = scriptMaintenanceClamshellPage().goToEditScriptRules();
        Assertions.assertTrue(editScriptRulesPageAppeared, "Edit Script Version page appeared");

        editScriptRulesConfirmationPage().clickOK();
        boolean doesScriptHaveRules = editScriptRulesPage().doesScriptRuleExistGivenText("1");
        Assertions.assertFalse(doesScriptHaveRules, "The new Script Version does have rules");
    }

}
