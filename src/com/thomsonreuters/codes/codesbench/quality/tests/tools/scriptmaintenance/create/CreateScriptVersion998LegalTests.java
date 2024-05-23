package com.thomsonreuters.codes.codesbench.quality.tests.tools.scriptmaintenance.create;

import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.scriptmaintenance.page.ScriptMaintenanceErrorPageElements;
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

import static com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets.IOWA_DEVELOPMENT;
import static com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets.USCA_DEVELOPMENT;

public class CreateScriptVersion998LegalTests extends TestService
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
     * STORY/BUGS - HALCYONST-12912, HALCYONST-11829, HALCYONST-3791 <br>
     * SUMMARY - Creates a script in a single content set <br>
     * USER - Legal <br>
     */
    @Test
	@EDGE
    @LEGAL
    @LOG
    public void createScriptVersion998SingleContentSetLegalTest()
    {
        userName = user().getUsername().contains("c") ? "u" + user().getUsername() : user().getUsername();

        homePage().goToHomePage();
        loginPage().logIn();

        boolean scriptMaintenancePageAppears = toolsMenu().goToScriptMaintenance();
        Assertions.assertTrue(scriptMaintenancePageAppears, "Script Maintenance page appeared");

        boolean scriptAppearsInGrid = scriptMaintenanceGridPage().scriptExists(ScriptMaintenanceDatabaseConstants.PUB_TAG, ScriptMaintenanceDatabaseConstants.SCRIPT_NAME, ScriptMaintenanceDatabaseConstants.VERSION_DESCRIPTION, "Incomplete");
        Assertions.assertTrue(scriptAppearsInGrid, "Created script appears in grid");

        scriptMaintenanceGridPage().selectScript(ScriptMaintenanceDatabaseConstants.PUB_TAG, ScriptMaintenanceDatabaseConstants.SCRIPT_NAME, ScriptMaintenanceDatabaseConstants.VERSION_DESCRIPTION, "Incomplete");
        boolean createScriptVersion998WindowAppeared = scriptMaintenanceClamshellPage().goToCreateScriptVersion998();
        Assertions.assertTrue(createScriptVersion998WindowAppeared, "Create Script Version 998 page appeared");

//        boolean scriptNameFieldIsDisabled = createScriptVersion998Page().isElementDisabled(CreateScriptVersion998PageElements.scriptName); TODO: When issue HALCYONST-15514 is solved uncomment out these checks
//        Assertions.assertTrue(scriptNameFieldIsDisabled, "Script Name field is disabled");
//
//        boolean versionDescriptionFieldIsDisabled = createScriptVersion998Page().isElementDisabled(CreateScriptVersion998PageElements.versionDescription);
//        Assertions.assertFalse(versionDescriptionFieldIsDisabled, "Version Description field is disabled");
//
//        boolean pubTagFieldIsDisabled = createScriptVersion998Page().isElementDisabled(CreateScriptVersion998PageElements.pubTag);
//        Assertions.assertTrue(pubTagFieldIsDisabled, "Pub Tag field is disabled");

        List<String> selectedContentSets = createScriptVersion998Page().getSelectedContentSets();
        boolean contentSetsMatch = selectedContentSets.contains("Iowa (Development)") && selectedContentSets.size() == 1;
        Assertions.assertTrue(contentSetsMatch, "Content Sets match");

        createScriptVersion998Page().setVersionDescription(ScriptMaintenanceDatabaseConstants.VERSION_DESCRIPTION + "Version");
        DateAndTimeUtils.takeNap(DateAndTimeUtils.FOUR_SECONDS);
        createScriptVersion998Page().clickSubmit();
        boolean versionScriptAppearsInGrid = scriptMaintenanceGridPage().scriptExists(ScriptMaintenanceDatabaseConstants.PUB_TAG, ScriptMaintenanceDatabaseConstants.SCRIPT_NAME, ScriptMaintenanceDatabaseConstants.VERSION_DESCRIPTION + "Version", "Incomplete");
        Assertions.assertTrue(versionScriptAppearsInGrid, "Created script version 998 appears in grid");

        String scriptId = scriptMaintenanceGridPage().getScriptIdOfScript(ScriptMaintenanceDatabaseConstants.PUB_TAG, ScriptMaintenanceDatabaseConstants.SCRIPT_NAME, ScriptMaintenanceDatabaseConstants.VERSION_DESCRIPTION + "Version", "Incomplete");
        boolean scriptIdIsCorrectFormat = RegexUtils.matchesRegex(scriptId, "1-\\d{4}-998");
        Assertions.assertTrue(scriptIdIsCorrectFormat, "Script Id is the correct format");
    }

    /**
     * STORY/BUGS - HALCYONST-3791 <br>
     * SUMMARY - Creates a script in multiple content sets <br>
     * USER - Legal <br>
     */
    @Test
	@EDGE
    @LEGAL
    @LOG
    public void createScriptVersion998MultipleContentSetsLegalTest()
    {
        userName = user().getUsername().contains("c") ? "u" + user().getUsername() : user().getUsername();

        Connection uatConnection = BaseDatabaseUtils.connectToDatabaseUAT();
        ScriptMaintenanceDatabaseUtils.insertScriptIntoOtherContentSets(uatConnection, ScriptMaintenanceDatabaseConstants.SCRIPT_ID,USCA_DEVELOPMENT.getCode());
        BaseDatabaseUtils.disconnect(uatConnection);

        homePage().goToHomePage();
        loginPage().logIn();

        boolean scriptMaintenancePageAppears = toolsMenu().goToScriptMaintenance();
        Assertions.assertTrue(scriptMaintenancePageAppears, "Script Maintenance page appeared");

        boolean iowaScriptAppearsInGrid = scriptMaintenanceGridPage().scriptExists(ScriptMaintenanceDatabaseConstants.PUB_TAG, ScriptMaintenanceDatabaseConstants.SCRIPT_NAME, ScriptMaintenanceDatabaseConstants.VERSION_DESCRIPTION, "Incomplete");
        Assertions.assertTrue(iowaScriptAppearsInGrid, "Iowa created script appears in grid");

        scriptMaintenanceGridPage().selectScript(ScriptMaintenanceDatabaseConstants.PUB_TAG, ScriptMaintenanceDatabaseConstants.SCRIPT_NAME, ScriptMaintenanceDatabaseConstants.VERSION_DESCRIPTION, "Incomplete");
        boolean createScriptVersion998WindowAppeared = scriptMaintenanceClamshellPage().goToCreateScriptVersion998();
        Assertions.assertTrue(createScriptVersion998WindowAppeared, "Create Script Version page appeared");

//        boolean scriptNameFieldIsDisabled = createScriptVersion998Page().isElementDisabled(CreateScriptVersion998PageElements.scriptName); TODO: When issue HALCYONST-15514 is solved uncomment out these checks
//        Assertions.assertTrue(scriptNameFieldIsDisabled, "Script Name field is disabled");
//
//        boolean versionDescriptionFieldIsDisabled = createScriptVersion998Page().isElementDisabled(CreateScriptVersion998PageElements.versionDescription);
//        Assertions.assertFalse(versionDescriptionFieldIsDisabled, "Version Description field is disabled");
//
//        boolean pubTagFieldIsDisabled = createScriptVersion998Page().isElementDisabled(CreateScriptVersion998PageElements.pubTag);
//        Assertions.assertTrue(pubTagFieldIsDisabled, "Pub Tag field is disabled");

        List<String> selectedContentSets = createScriptVersion998Page().getSelectedContentSets();
        boolean contentSetsMatch = selectedContentSets.contains("Iowa (Development)") && selectedContentSets.contains("USCA(Development)") && selectedContentSets.size() == 2;
        Assertions.assertTrue(contentSetsMatch, "Content Sets match");

        createScriptVersion998Page().setVersionDescription(ScriptMaintenanceDatabaseConstants.VERSION_DESCRIPTION + "Version");
        DateAndTimeUtils.takeNap(DateAndTimeUtils.FOUR_SECONDS);
        createScriptVersion998Page().clickSubmit();
        boolean versionScriptAppearsInGrid = scriptMaintenanceGridPage().scriptExists(ScriptMaintenanceDatabaseConstants.PUB_TAG, ScriptMaintenanceDatabaseConstants.SCRIPT_NAME, ScriptMaintenanceDatabaseConstants.VERSION_DESCRIPTION + "Version", "Incomplete");
        Assertions.assertTrue(versionScriptAppearsInGrid, "Created script version 998 appears in grid");

        String scriptId = scriptMaintenanceGridPage().getScriptIdOfScript(ScriptMaintenanceDatabaseConstants.PUB_TAG, ScriptMaintenanceDatabaseConstants.SCRIPT_NAME, ScriptMaintenanceDatabaseConstants.VERSION_DESCRIPTION + "Version", "Incomplete");
        boolean scriptIdIsCorrectFormat = RegexUtils.matchesRegex(scriptId, "1-\\d{4}-998");
        Assertions.assertTrue(scriptIdIsCorrectFormat, "Script Id is the correct format");

        scriptMaintenanceGridPage().closeCurrentWindowIgnoreDialogue();
        homePage().switchToPage();
        homePage().setMyContentSet("USCA(Development)");
        toolsMenu().goToScriptMaintenance();
        boolean uscaScriptAppearsInGrid = scriptMaintenanceGridPage().scriptExists(ScriptMaintenanceDatabaseConstants.PUB_TAG, ScriptMaintenanceDatabaseConstants.SCRIPT_NAME, ScriptMaintenanceDatabaseConstants.VERSION_DESCRIPTION, "Incomplete");
        Assertions.assertTrue(uscaScriptAppearsInGrid, "USCA created script appears in grid");

        boolean uscaVersionScriptAppearsInGrid = scriptMaintenanceGridPage().scriptExists(ScriptMaintenanceDatabaseConstants.PUB_TAG, ScriptMaintenanceDatabaseConstants.SCRIPT_NAME, ScriptMaintenanceDatabaseConstants.VERSION_DESCRIPTION + "Version", "Incomplete");
        Assertions.assertTrue(uscaVersionScriptAppearsInGrid, "USCA Created script version 998 appears in grid");

        String uscaScriptId = scriptMaintenanceGridPage().getScriptIdOfScript(ScriptMaintenanceDatabaseConstants.PUB_TAG, ScriptMaintenanceDatabaseConstants.SCRIPT_NAME, ScriptMaintenanceDatabaseConstants.VERSION_DESCRIPTION + "Version", "Incomplete");
        boolean uscaScriptIdIsCorrectFormat = RegexUtils.matchesRegex(uscaScriptId, "1-\\d{4}-998");
        Assertions.assertTrue(uscaScriptIdIsCorrectFormat, "USCA Script Id is the correct format");
    }

    /**
     * STORY/BUGS - HALCYONST-3791 <br>
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

        homePage().goToHomePage();
        loginPage().logIn();

        boolean scriptMaintenancePageAppears = toolsMenu().goToScriptMaintenance();
        Assertions.assertTrue(scriptMaintenancePageAppears, "Script Maintenance page appeared");

        boolean scriptAppearsInGrid = scriptMaintenanceGridPage().scriptExists(ScriptMaintenanceDatabaseConstants.PUB_TAG, ScriptMaintenanceDatabaseConstants.SCRIPT_NAME, ScriptMaintenanceDatabaseConstants.VERSION_DESCRIPTION, "Incomplete");
        Assertions.assertTrue(scriptAppearsInGrid, "Created script appears in grid");

        scriptMaintenanceGridPage().selectScript(ScriptMaintenanceDatabaseConstants.PUB_TAG, ScriptMaintenanceDatabaseConstants.SCRIPT_NAME, ScriptMaintenanceDatabaseConstants.VERSION_DESCRIPTION, "Incomplete");
        boolean createScriptVersion998WindowAppeared = scriptMaintenanceClamshellPage().goToCreateScriptVersion998();
        Assertions.assertTrue(createScriptVersion998WindowAppeared, "Create Script Version 998 page appeared");

//        boolean scriptNameFieldIsDisabled = createScriptVersion998Page().isElementDisabled(CreateScriptVersion998PageElements.scriptName); TODO: When issue HALCYONST-15514 is solved uncomment out these checks
//        Assertions.assertTrue(scriptNameFieldIsDisabled, "Script Name field is disabled");
//
//        boolean versionDescriptionFieldIsDisabled = createScriptVersion998Page().isElementDisabled(CreateScriptVersion998PageElements.versionDescription);
//        Assertions.assertFalse(versionDescriptionFieldIsDisabled, "Version Description field is disabled");
//
//        boolean pubTagFieldIsDisabled = createScriptVersion998Page().isElementDisabled(CreateScriptVersion998PageElements.pubTag);
//        Assertions.assertTrue(pubTagFieldIsDisabled, "Pub Tag field is disabled");

        List<String> selectedContentSets = createScriptVersion998Page().getSelectedContentSets();
        boolean contentSetsMatch = selectedContentSets.contains("Iowa (Development)") && selectedContentSets.size() == 1;
        Assertions.assertTrue(contentSetsMatch, "Content Sets match");

        createScriptVersion998Page().setVersionDescription(ScriptMaintenanceDatabaseConstants.VERSION_DESCRIPTION + "Version");
        createScriptVersion998Page().clickCancel();
        boolean versionScript998AppearsInGrid = scriptMaintenanceGridPage().scriptExists(ScriptMaintenanceDatabaseConstants.PUB_TAG, ScriptMaintenanceDatabaseConstants.SCRIPT_NAME, ScriptMaintenanceDatabaseConstants.VERSION_DESCRIPTION + "Version", "Incomplete");
        Assertions.assertFalse(versionScript998AppearsInGrid, "Created script version 998 appears in grid");
    }

    /**
     * STORY/BUGS - HALCYONST-12912, HALCYONST-11829, HALCYONST-3791 <br>
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
        boolean createScriptVersion998WindowAppeared = scriptMaintenanceClamshellPage().goToCreateScriptVersion998();
        Assertions.assertTrue(createScriptVersion998WindowAppeared, "Create Script Version page appeared");

//        boolean scriptNameFieldIsDisabled = createScriptVersion998Page().isElementDisabled(CreateScriptVersion998PageElements.scriptName); TODO: When issue HALCYONST-15514 is solved uncomment out these checks
//        Assertions.assertTrue(scriptNameFieldIsDisabled, "Script Name field is disabled");
//
//        boolean versionDescriptionFieldIsDisabled = createScriptVersion998Page().isElementDisabled(CreateScriptVersion998PageElements.versionDescription);
//        Assertions.assertFalse(versionDescriptionFieldIsDisabled, "Version Description field is disabled");
//
//        boolean pubTagFieldIsDisabled = createScriptVersion998Page().isElementDisabled(CreateScriptVersion998PageElements.pubTag);
//        Assertions.assertTrue(pubTagFieldIsDisabled, "Pub Tag field is disabled");

        List<String> selectedContentSets = createScriptVersion998Page().getSelectedContentSets();
        boolean contentSetsMatch = selectedContentSets.contains("Iowa (Development)") && selectedContentSets.size() == 1;
        Assertions.assertTrue(contentSetsMatch, "Content Sets match");

        createScriptVersion998Page().setVersionDescription(ScriptMaintenanceDatabaseConstants.VERSION_DESCRIPTION + "Version");
        DateAndTimeUtils.takeNap(DateAndTimeUtils.FOUR_SECONDS);
        createScriptVersion998Page().clickSubmit();
        boolean versionScriptAppearsInGrid = scriptMaintenanceGridPage().scriptExists(ScriptMaintenanceDatabaseConstants.PUB_TAG, ScriptMaintenanceDatabaseConstants.SCRIPT_NAME, ScriptMaintenanceDatabaseConstants.VERSION_DESCRIPTION + "Version", "Incomplete");
        Assertions.assertTrue(versionScriptAppearsInGrid, "Created script version appears in grid");

        String scriptId = scriptMaintenanceGridPage().getScriptIdOfScript(ScriptMaintenanceDatabaseConstants.PUB_TAG, ScriptMaintenanceDatabaseConstants.SCRIPT_NAME, ScriptMaintenanceDatabaseConstants.VERSION_DESCRIPTION + "Version", "Incomplete");
        boolean scriptIdIsCorrectFormat = RegexUtils.matchesRegex(scriptId, "1-\\d{4}-998");
        Assertions.assertTrue(scriptIdIsCorrectFormat, "Script Id is the correct format");

        scriptMaintenanceGridPage().selectScript(ScriptMaintenanceDatabaseConstants.PUB_TAG, ScriptMaintenanceDatabaseConstants.SCRIPT_NAME, ScriptMaintenanceDatabaseConstants.VERSION_DESCRIPTION + "Version", "Incomplete");
        boolean createScriptVersion998WindowAppeared2 = scriptMaintenanceClamshellPage().goToCreateScriptVersion998();
        Assertions.assertTrue(createScriptVersion998WindowAppeared2, "Create Script Version page appeared 2");

//        boolean scriptNameFieldIsDisabled2 = createScriptVersion998Page().isElementDisabled(CreateScriptVersion998PageElements.scriptName); TODO: When issue HALCYONST-15514 is solved uncomment out these checks
//        Assertions.assertTrue(scriptNameFieldIsDisabled2, "Script Name field is disabled 2");
//
//        boolean versionDescriptionFieldIsDisabled2 = createScriptVersion998Page().isElementDisabled(CreateScriptVersion998PageElements.versionDescription);
//        Assertions.assertFalse(versionDescriptionFieldIsDisabled2, "Version Description field is disabled 2");
//
//        boolean pubTagFieldIsDisabled2 = createScriptVersion998Page().isElementDisabled(CreateScriptVersion998PageElements.pubTag);
//        Assertions.assertTrue(pubTagFieldIsDisabled2, "Pub Tag field is disabled 2");

        List<String> selectedContentSets2 = createScriptVersion998Page().getSelectedContentSets();
        boolean contentSetsMatch2 = selectedContentSets2.contains("Iowa (Development)") && selectedContentSets2.size() == 1;
        Assertions.assertTrue(contentSetsMatch2, "Content Sets match 2");

        createScriptVersion998Page().setVersionDescription(ScriptMaintenanceDatabaseConstants.VERSION_DESCRIPTION + "Version2");
        boolean errorWindowAppeared = createScriptVersion998Page().clickConfirmErrorExpected();
        Assertions.assertTrue(errorWindowAppeared, "Error window appeared");

        boolean existing998ErrorMessageAppeared = scriptMaintenanceErrorPage().isElementDisplayed(ScriptMaintenanceErrorPageElements.EXISTING_998_MESSAGE);
        Assertions.assertTrue(existing998ErrorMessageAppeared, "Existing 998 error message appeared");

        boolean errorPageDisappeared = scriptMaintenanceErrorPage().clickOk();
        Assertions.assertFalse(errorPageDisappeared, "Error window disappeared");
    }

    /**
     * STORY/BUGS - HALCYONST-11829, HALCYONST-3791 <br>
     * SUMMARY - Creates a Version998 of a Script that has rules <br>
     * USER - Legal <br>
     */
    @Test
	@EDGE
    @LEGAL
    @LOG
    public void createScriptVersion998WithRulesLegalTest()
    {
        Connection uatConnection = BaseDatabaseUtils.connectToDatabaseUAT();
        ScriptMaintenanceDatabaseUtils.insertSelectRangeScriptRuleIntoGivenScript(uatConnection, ScriptMaintenanceDatabaseConstants.SCRIPT_ID, userName, "TestSM","TestEM", 1,3);
        BaseDatabaseUtils.disconnect(uatConnection);

        homePage().goToHomePage();
        loginPage().logIn();

        boolean scriptMaintenancePageAppears = toolsMenu().goToScriptMaintenance();
        Assertions.assertTrue(scriptMaintenancePageAppears, "Script Maintenance page appeared");

        boolean scriptAppearsInGrid = scriptMaintenanceGridPage().scriptExists(ScriptMaintenanceDatabaseConstants.PUB_TAG, ScriptMaintenanceDatabaseConstants.SCRIPT_NAME, ScriptMaintenanceDatabaseConstants.VERSION_DESCRIPTION, "Incomplete");
        Assertions.assertTrue(scriptAppearsInGrid, "Created script appears in grid");

        scriptMaintenanceGridPage().selectScript(ScriptMaintenanceDatabaseConstants.PUB_TAG, ScriptMaintenanceDatabaseConstants.SCRIPT_NAME, ScriptMaintenanceDatabaseConstants.VERSION_DESCRIPTION, "Incomplete");
        boolean createScriptVersion998WindowAppeared = scriptMaintenanceClamshellPage().goToCreateScriptVersion998();
        Assertions.assertTrue(createScriptVersion998WindowAppeared, "Create Script Version 998 page appeared");

        createScriptVersion998Page().setVersionDescription(ScriptMaintenanceDatabaseConstants.VERSION_DESCRIPTION + "Version");
        DateAndTimeUtils.takeNap(DateAndTimeUtils.FOUR_SECONDS);
        createScriptVersion998Page().clickSubmit();
        boolean versionScriptAppearsInGrid = scriptMaintenanceGridPage().scriptExists(ScriptMaintenanceDatabaseConstants.PUB_TAG, ScriptMaintenanceDatabaseConstants.SCRIPT_NAME, ScriptMaintenanceDatabaseConstants.VERSION_DESCRIPTION + "Version", "Incomplete");
        Assertions.assertTrue(versionScriptAppearsInGrid, "Created script version 998 appears in grid");

        String scriptId = scriptMaintenanceGridPage().getScriptIdOfScript(ScriptMaintenanceDatabaseConstants.PUB_TAG, ScriptMaintenanceDatabaseConstants.SCRIPT_NAME, ScriptMaintenanceDatabaseConstants.VERSION_DESCRIPTION + "Version", "Incomplete");
        boolean scriptIdIsCorrectFormat = RegexUtils.matchesRegex(scriptId, "1-\\d{4}-998");
        Assertions.assertTrue(scriptIdIsCorrectFormat, "Script Id is the correct format");

        scriptMaintenanceGridPage().selectScript(ScriptMaintenanceDatabaseConstants.PUB_TAG, ScriptMaintenanceDatabaseConstants.SCRIPT_NAME, ScriptMaintenanceDatabaseConstants.VERSION_DESCRIPTION + "Version", "Incomplete");
        boolean editScriptRulesPageAppeared = scriptMaintenanceClamshellPage().goToEditScriptRules();
        Assertions.assertTrue(editScriptRulesPageAppeared, "Edit Script Version page appeared");

        editScriptRulesConfirmationPage().clickOK();
        boolean doesScriptHaveRules = editScriptRulesPage().doesScriptRuleExistGivenText("1");
        Assertions.assertFalse(doesScriptHaveRules, "The new Script Version does have rules");
    }
}
