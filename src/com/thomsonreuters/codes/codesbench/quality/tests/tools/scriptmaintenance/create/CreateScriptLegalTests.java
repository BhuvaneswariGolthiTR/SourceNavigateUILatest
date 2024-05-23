package com.thomsonreuters.codes.codesbench.quality.tests.tools.scriptmaintenance.create;

import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.scriptmaintenance.create.CreateScriptPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.scriptmaintenance.page.ScriptMaintenanceErrorPageElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.EDGE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.*;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.*;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.tools.ScriptMaintenance.ScriptMaintenanceDatabaseConstants;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.tools.ScriptMaintenance.ScriptMaintenanceDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.regex.RegexUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.Arrays;
import java.util.List;

public class CreateScriptLegalTests extends TestService
{
    private String userName = "";

    @AfterEach
    public void deepDeleteScriptMaintenanceTestingTags()
    {
        Connection uatConnection = BaseDatabaseUtils.connectToDatabaseUAT();
        ScriptMaintenanceDatabaseUtils.deepDeleteScriptMaintenanceTestingTags(uatConnection, ScriptMaintenanceDatabaseConstants.SCRIPT_NAME, userName, ScriptMaintenanceDatabaseConstants.PUB_TAG);
        BaseDatabaseUtils.disconnect(uatConnection);
    }

    /**
     * STORY/BUGS - HALCYONST-14826, HALCYONST-7184, HALCYONST-3783, HALCYONST-5563 <br>
     * SUMMARY - Creates a script in a single content set <br>
     * USER - Legal <br>
     */
    @Test
	@EDGE
    @LEGAL
    @LOG
    public void createScriptSingleContentSetLegalTest()
    {
        userName = user().getUsername().contains("c") ? "u" + user().getUsername() : user().getUsername();

        homePage().goToHomePage();
        loginPage().logIn();

        boolean scriptMaintenancePageAppears = toolsMenu().goToScriptMaintenance();
        Assertions.assertTrue(scriptMaintenancePageAppears, "Script Maintenance page appeared");

        boolean createScriptWindowAppeared = scriptMaintenanceClamshellPage().goToCreateScript();
        Assertions.assertTrue(createScriptWindowAppeared, "Create Script page appeared");

        boolean saveButtonIsDisabled = createScriptPage().isElementDisabled(CreateScriptPageElements.SAVE_BUTTON);
        Assertions.assertTrue(saveButtonIsDisabled, "Save button is disabled");

        boolean scriptNameIsRequiredMessageAppears = createScriptPage().isElementDisplayed(CreateScriptPageElements.SCRIPT_NAME_IS_REQUIRED_MESSAGE);
        Assertions.assertTrue(scriptNameIsRequiredMessageAppears, "Script Name is required message appeared");

        boolean versionDescriptionIsRequiredMessageAppears = createScriptPage().isElementDisplayed(CreateScriptPageElements.VERSION_DESCRIPTION_IS_REQUIRED_MESSAGE);
        Assertions.assertTrue(versionDescriptionIsRequiredMessageAppears, "Version Description is required message appeared");

        boolean pubTagIsRequiredMessageAppears = createScriptPage().isElementDisplayed(CreateScriptPageElements.PUBTAG_IS_REQUIRED_MESSAGE);
        Assertions.assertTrue(pubTagIsRequiredMessageAppears, "Pub Tag is required message appeared");

        createScriptPage().setScriptName(ScriptMaintenanceDatabaseConstants.SCRIPT_NAME);
        scriptNameIsRequiredMessageAppears = createScriptPage().isElementDisplayed(CreateScriptPageElements.SCRIPT_NAME_IS_REQUIRED_MESSAGE);
        Assertions.assertFalse(scriptNameIsRequiredMessageAppears, "Script Name is required message appeared");

        createScriptPage().setVersionDescription(ScriptMaintenanceDatabaseConstants.VERSION_DESCRIPTION);
        versionDescriptionIsRequiredMessageAppears = createScriptPage().isElementDisplayed(CreateScriptPageElements.VERSION_DESCRIPTION_IS_REQUIRED_MESSAGE);
        Assertions.assertFalse(versionDescriptionIsRequiredMessageAppears, "Version Description is required message appeared");

        createScriptPage().setPubTag(ScriptMaintenanceDatabaseConstants.PUB_TAG);
        pubTagIsRequiredMessageAppears = createScriptPage().isElementDisplayed(CreateScriptPageElements.PUBTAG_IS_REQUIRED_MESSAGE);
        Assertions.assertFalse(pubTagIsRequiredMessageAppears, "Pub Tag is required message appeared");

        createScriptPage().setContentSets("Iowa (Development)");
        saveButtonIsDisabled = createScriptPage().isElementDisabled(CreateScriptPageElements.SAVE_BUTTON);
        Assertions.assertFalse(saveButtonIsDisabled, "Save button is disabled");

        boolean createScriptPageDisappeared = createScriptPage().clickSave();
        Assertions.assertFalse(createScriptPageDisappeared, "Create Script page disappeared");

        boolean scriptAppearsInGrid = scriptMaintenanceGridPage().scriptExists(ScriptMaintenanceDatabaseConstants.PUB_TAG, ScriptMaintenanceDatabaseConstants.SCRIPT_NAME, ScriptMaintenanceDatabaseConstants.VERSION_DESCRIPTION, "Incomplete");
        Assertions.assertTrue(scriptAppearsInGrid, "Created script appears in grid");

        String scriptId = scriptMaintenanceGridPage().getScriptIdOfScript(ScriptMaintenanceDatabaseConstants.PUB_TAG, ScriptMaintenanceDatabaseConstants.SCRIPT_NAME, ScriptMaintenanceDatabaseConstants.VERSION_DESCRIPTION, "Incomplete");
        boolean scriptIdIsCorrectFormat = RegexUtils.matchesRegex(scriptId, "1-\\d{4}-001");
        Assertions.assertTrue(scriptIdIsCorrectFormat, "Script Id is the correct format");

        Connection uatConnection = BaseDatabaseUtils.connectToDatabaseUAT();
        int script999count = ScriptMaintenanceDatabaseUtils.getScript999Count(uatConnection, ScriptMaintenanceDatabaseConstants.SCRIPT_NAME, userName);
        BaseDatabaseUtils.disconnect(uatConnection);
        Assertions.assertEquals(1, script999count, "There is a Script 999 version within the database for the new Script");

    }

    /**
     * STORY/BUGS - HALCYONST-7184, HALCYONST-3783, HALCYONST-5563 <br>
     * SUMMARY - Creates a script in multiple content sets <br>
     * USER - Legal <br>
     */
    @Test
	@EDGE
    @LEGAL
    @LOG
    public void createScriptMultipleContentSetsLegalTest()
    {
        userName = user().getUsername().contains("c") ? "u" + user().getUsername() : user().getUsername();
        homePage().goToHomePage();
        loginPage().logIn();

        boolean scriptMaintenancePageAppears = toolsMenu().goToScriptMaintenance();
        Assertions.assertTrue(scriptMaintenancePageAppears, "Script Maintenance page appeared");

        boolean createScriptWindowAppeared = scriptMaintenanceClamshellPage().goToCreateScript();
        Assertions.assertTrue(createScriptWindowAppeared, "Create Script page appeared");

        boolean saveButtonIsDisabled = createScriptPage().isElementDisabled(CreateScriptPageElements.SAVE_BUTTON);
        Assertions.assertTrue(saveButtonIsDisabled, "Save button is disabled");

        boolean scriptNameIsRequiredMessageAppears = createScriptPage().isElementDisplayed(CreateScriptPageElements.SCRIPT_NAME_IS_REQUIRED_MESSAGE);
        Assertions.assertTrue(scriptNameIsRequiredMessageAppears, "Script Name is required message appeared");

        boolean versionDescriptionIsRequiredMessageAppears = createScriptPage().isElementDisplayed(CreateScriptPageElements.VERSION_DESCRIPTION_IS_REQUIRED_MESSAGE);
        Assertions.assertTrue(versionDescriptionIsRequiredMessageAppears, "Version Description is required message appeared");

        boolean pubTagIsRequiredMessageAppears = createScriptPage().isElementDisplayed(CreateScriptPageElements.PUBTAG_IS_REQUIRED_MESSAGE);
        Assertions.assertTrue(pubTagIsRequiredMessageAppears, "Pub Tag is required message appeared");

        createScriptPage().setScriptName(ScriptMaintenanceDatabaseConstants.SCRIPT_NAME);
        scriptNameIsRequiredMessageAppears = createScriptPage().isElementDisplayed(CreateScriptPageElements.SCRIPT_NAME_IS_REQUIRED_MESSAGE);
        Assertions.assertFalse(scriptNameIsRequiredMessageAppears, "Script Name is required message appeared");

        createScriptPage().setVersionDescription(ScriptMaintenanceDatabaseConstants.VERSION_DESCRIPTION);
        versionDescriptionIsRequiredMessageAppears = createScriptPage().isElementDisplayed(CreateScriptPageElements.VERSION_DESCRIPTION_IS_REQUIRED_MESSAGE);
        Assertions.assertFalse(versionDescriptionIsRequiredMessageAppears, "Version Description is required message appeared");

        createScriptPage().setPubTag(ScriptMaintenanceDatabaseConstants.PUB_TAG);
        pubTagIsRequiredMessageAppears = createScriptPage().isElementDisplayed(CreateScriptPageElements.PUBTAG_IS_REQUIRED_MESSAGE);
        Assertions.assertFalse(pubTagIsRequiredMessageAppears, "Pub Tag is required message appeared");

        createScriptPage().setContentSets("Iowa (Development)", "USCA(Development)");
        saveButtonIsDisabled = createScriptPage().isElementDisabled(CreateScriptPageElements.SAVE_BUTTON);
        Assertions.assertFalse(saveButtonIsDisabled, "Save button is disabled");

        boolean createScriptPageDisappeared = createScriptPage().clickSave();
        Assertions.assertFalse(createScriptPageDisappeared, "Create Script page disappeared");

        boolean iowaScriptAppearsInGrid = scriptMaintenanceGridPage().scriptExists(ScriptMaintenanceDatabaseConstants.PUB_TAG, ScriptMaintenanceDatabaseConstants.SCRIPT_NAME, ScriptMaintenanceDatabaseConstants.VERSION_DESCRIPTION, "Incomplete");
        Assertions.assertTrue(iowaScriptAppearsInGrid, "Iowa created script appears in grid");

        String iowaScriptId = scriptMaintenanceGridPage().getScriptIdOfScript(ScriptMaintenanceDatabaseConstants.PUB_TAG, ScriptMaintenanceDatabaseConstants.SCRIPT_NAME, ScriptMaintenanceDatabaseConstants.VERSION_DESCRIPTION, "Incomplete");
        boolean iowaScriptIdIsCorrectFormat = RegexUtils.matchesRegex(iowaScriptId, "1-\\d{4}-001");
        Assertions.assertTrue(iowaScriptIdIsCorrectFormat, "Iowa script Id is the correct format");

        scriptMaintenanceGridPage().closeCurrentWindowIgnoreDialogue();
        homePage().switchToPage();
        homePage().setMyContentSet("USCA(Development)");
        toolsMenu().goToScriptMaintenance();
        boolean uscaScriptAppearsInGrid = scriptMaintenanceGridPage().scriptExists(ScriptMaintenanceDatabaseConstants.PUB_TAG, ScriptMaintenanceDatabaseConstants.SCRIPT_NAME, ScriptMaintenanceDatabaseConstants.VERSION_DESCRIPTION, "Incomplete");
        Assertions.assertTrue(uscaScriptAppearsInGrid, "USCA created script appears in grid");

        String uscaScriptId = scriptMaintenanceGridPage().getScriptIdOfScript(ScriptMaintenanceDatabaseConstants.PUB_TAG, ScriptMaintenanceDatabaseConstants.SCRIPT_NAME, ScriptMaintenanceDatabaseConstants.VERSION_DESCRIPTION, "Incomplete");
        boolean uscaScriptIdIsCorrectFormat = RegexUtils.matchesRegex(uscaScriptId, "1-\\d{4}-001");
        Assertions.assertTrue(uscaScriptIdIsCorrectFormat, "USCA script Id is the correct format");

        int uscaIntId = Integer.parseInt(uscaScriptId.replaceAll("-","").replaceFirst(".{3}$", "999")); //Removes all - and then changes the last three characters to 999
        Connection uatConnection = BaseDatabaseUtils.connectToDatabaseUAT();
        int script999count = ScriptMaintenanceDatabaseUtils.getScript999Count(uatConnection, ScriptMaintenanceDatabaseConstants.SCRIPT_NAME, userName);
        List<String> getRowsFromScriptContentSetTable = ScriptMaintenanceDatabaseUtils.getRowsFromScriptContentSetTable(uatConnection, uscaIntId);
        BaseDatabaseUtils.disconnect(uatConnection);

        boolean doesScriptIDHaveEntriesFor999 = getRowsFromScriptContentSetTable.size() == 2 && getRowsFromScriptContentSetTable.containsAll(Arrays.asList("100", "106"));
        Assertions.assertAll (
            () ->  Assertions.assertTrue(doesScriptIDHaveEntriesFor999, "Valid Content Sets For Script matches"),
            () -> Assertions.assertEquals(1, script999count, "There is a Script 999 version within the database for the new Script")
        );
    }

    /**
     * STORY/BUGS - HALCYONST-3783 <br>
     * SUMMARY - Cancels the creation of a script in a single content set <br>
     * USER - Legal <br>
     */
    @Test
	@EDGE
    @LEGAL
    @LOG
    public void cancelCreateScriptLegalTest()
    {
        userName = user().getUsername().contains("c") ? "u" + user().getUsername() : user().getUsername();

        homePage().goToHomePage();
        loginPage().logIn();

        boolean scriptMaintenancePageAppears = toolsMenu().goToScriptMaintenance();
        Assertions.assertTrue(scriptMaintenancePageAppears, "Script Maintenance page appeared");

        boolean createScriptWindowAppeared = scriptMaintenanceClamshellPage().goToCreateScript();
        Assertions.assertTrue(createScriptWindowAppeared, "Create Script page appeared");

        boolean saveButtonIsDisabled = createScriptPage().isElementDisabled(CreateScriptPageElements.SAVE_BUTTON);
        Assertions.assertTrue(saveButtonIsDisabled, "Save button is disabled");

        boolean scriptNameIsRequiredMessageAppears = createScriptPage().isElementDisplayed(CreateScriptPageElements.SCRIPT_NAME_IS_REQUIRED_MESSAGE);
        Assertions.assertTrue(scriptNameIsRequiredMessageAppears, "Script Name is required message appeared");

        boolean versionDescriptionIsRequiredMessageAppears = createScriptPage().isElementDisplayed(CreateScriptPageElements.VERSION_DESCRIPTION_IS_REQUIRED_MESSAGE);
        Assertions.assertTrue(versionDescriptionIsRequiredMessageAppears, "Version Description is required message appeared");

        boolean pubTagIsRequiredMessageAppears = createScriptPage().isElementDisplayed(CreateScriptPageElements.PUBTAG_IS_REQUIRED_MESSAGE);
        Assertions.assertTrue(pubTagIsRequiredMessageAppears, "Pub Tag is required message appeared");

        createScriptPage().setScriptName(ScriptMaintenanceDatabaseConstants.SCRIPT_NAME);
        scriptNameIsRequiredMessageAppears = createScriptPage().isElementDisplayed(CreateScriptPageElements.SCRIPT_NAME_IS_REQUIRED_MESSAGE);
        Assertions.assertFalse(scriptNameIsRequiredMessageAppears, "Script Name is required message appeared");

        createScriptPage().setVersionDescription(ScriptMaintenanceDatabaseConstants.VERSION_DESCRIPTION);
        versionDescriptionIsRequiredMessageAppears = createScriptPage().isElementDisplayed(CreateScriptPageElements.VERSION_DESCRIPTION_IS_REQUIRED_MESSAGE);
        Assertions.assertFalse(versionDescriptionIsRequiredMessageAppears, "Version Description is required message appeared");

        createScriptPage().setPubTag(ScriptMaintenanceDatabaseConstants.PUB_TAG);
        pubTagIsRequiredMessageAppears = createScriptPage().isElementDisplayed(CreateScriptPageElements.PUBTAG_IS_REQUIRED_MESSAGE);
        Assertions.assertFalse(pubTagIsRequiredMessageAppears, "Pub Tag is required message appeared");

        createScriptPage().setContentSets("Iowa (Development)");
        saveButtonIsDisabled = createScriptPage().isElementDisabled(CreateScriptPageElements.SAVE_BUTTON);
        Assertions.assertFalse(saveButtonIsDisabled, "Save button is disabled");

        boolean createScriptPageDisappeared = createScriptPage().clickCancel();
        Assertions.assertFalse(createScriptPageDisappeared, "Create Script page disappeared");

        boolean scriptAppearsInGrid = scriptMaintenanceGridPage().scriptExists(ScriptMaintenanceDatabaseConstants.PUB_TAG, ScriptMaintenanceDatabaseConstants.SCRIPT_NAME, ScriptMaintenanceDatabaseConstants.VERSION_DESCRIPTION, "Incomplete");
        Assertions.assertFalse(scriptAppearsInGrid, "Created script appears in grid");

        Connection uatConnection = BaseDatabaseUtils.connectToDatabaseUAT();
        int script999count = ScriptMaintenanceDatabaseUtils.getScript999Count(uatConnection, ScriptMaintenanceDatabaseConstants.SCRIPT_NAME, userName);
        BaseDatabaseUtils.disconnect(uatConnection);
        Assertions.assertEquals(0, script999count, "There is a Script 999 version within the database for the new Script");
    }

    /**
     * STORY/BUGS - HALCYONST-11410, HALCYONST-7184, HALCYONST-3783 <br>
     * SUMMARY - Attempts to creates a duplicate script in a single content set <br>
     * USER - Legal <br>
     */
    @Test
	@EDGE
    @LEGAL
    @LOG
    public void createDuplicateScriptsLegalTest()
    {
        userName = user().getUsername().contains("c") ? "u" + user().getUsername() : user().getUsername();

        homePage().goToHomePage();
        loginPage().logIn();

        boolean scriptMaintenancePageAppears = toolsMenu().goToScriptMaintenance();
        Assertions.assertTrue(scriptMaintenancePageAppears, "Script Maintenance page appeared");

        boolean createScriptWindowAppeared = scriptMaintenanceClamshellPage().goToCreateScript();
        Assertions.assertTrue(createScriptWindowAppeared, "Create Script page appeared");

        boolean saveButtonIsDisabled = createScriptPage().isElementDisabled(CreateScriptPageElements.SAVE_BUTTON);
        Assertions.assertTrue(saveButtonIsDisabled, "Save button is disabled");

        boolean scriptNameIsRequiredMessageAppears = createScriptPage().isElementDisplayed(CreateScriptPageElements.SCRIPT_NAME_IS_REQUIRED_MESSAGE);
        Assertions.assertTrue(scriptNameIsRequiredMessageAppears, "Script Name is required message appeared");

        boolean versionDescriptionIsRequiredMessageAppears = createScriptPage().isElementDisplayed(CreateScriptPageElements.VERSION_DESCRIPTION_IS_REQUIRED_MESSAGE);
        Assertions.assertTrue(versionDescriptionIsRequiredMessageAppears, "Version Description is required message appeared");

        boolean pubTagIsRequiredMessageAppears = createScriptPage().isElementDisplayed(CreateScriptPageElements.PUBTAG_IS_REQUIRED_MESSAGE);
        Assertions.assertTrue(pubTagIsRequiredMessageAppears, "Pub Tag is required message appeared");

        createScriptPage().setScriptName(ScriptMaintenanceDatabaseConstants.SCRIPT_NAME);
        scriptNameIsRequiredMessageAppears = createScriptPage().isElementDisplayed(CreateScriptPageElements.SCRIPT_NAME_IS_REQUIRED_MESSAGE);
        Assertions.assertFalse(scriptNameIsRequiredMessageAppears, "Script Name is required message appeared");

        createScriptPage().setVersionDescription(ScriptMaintenanceDatabaseConstants.VERSION_DESCRIPTION);
        versionDescriptionIsRequiredMessageAppears = createScriptPage().isElementDisplayed(CreateScriptPageElements.VERSION_DESCRIPTION_IS_REQUIRED_MESSAGE);
        Assertions.assertFalse(versionDescriptionIsRequiredMessageAppears, "Version Description is required message appeared");

        createScriptPage().setPubTag(ScriptMaintenanceDatabaseConstants.PUB_TAG);
        pubTagIsRequiredMessageAppears = createScriptPage().isElementDisplayed(CreateScriptPageElements.PUBTAG_IS_REQUIRED_MESSAGE);
        Assertions.assertFalse(pubTagIsRequiredMessageAppears, "Pub Tag is required message appeared");

        createScriptPage().setContentSets("Iowa (Development)");
        saveButtonIsDisabled = createScriptPage().isElementDisabled(CreateScriptPageElements.SAVE_BUTTON);
        Assertions.assertFalse(saveButtonIsDisabled, "Save button is disabled");

        boolean createScriptPageDisappeared = createScriptPage().clickSave();
        Assertions.assertFalse(createScriptPageDisappeared, "Create Script page disappeared");

        boolean scriptAppearsInGrid = scriptMaintenanceGridPage().scriptExists(ScriptMaintenanceDatabaseConstants.PUB_TAG, ScriptMaintenanceDatabaseConstants.SCRIPT_NAME, ScriptMaintenanceDatabaseConstants.VERSION_DESCRIPTION, "Incomplete");
        Assertions.assertTrue(scriptAppearsInGrid, "Created script appears in grid");

        String scriptId = scriptMaintenanceGridPage().getScriptIdOfScript(ScriptMaintenanceDatabaseConstants.PUB_TAG, ScriptMaintenanceDatabaseConstants.SCRIPT_NAME, ScriptMaintenanceDatabaseConstants.VERSION_DESCRIPTION, "Incomplete");
        boolean scriptIdIsCorrectFormat = RegexUtils.matchesRegex(scriptId, "1-\\d{4}-001");
        Assertions.assertTrue(scriptIdIsCorrectFormat, "Script Id is the correct format");

        scriptMaintenanceClamshellPage().goToCreateScript();
        createScriptPage().setScriptName(ScriptMaintenanceDatabaseConstants.SCRIPT_NAME);
        createScriptPage().setVersionDescription(ScriptMaintenanceDatabaseConstants.VERSION_DESCRIPTION);
        createScriptPage().setPubTag(ScriptMaintenanceDatabaseConstants.PUB_TAG);
        createScriptPage().setContentSets("Iowa (Development)");
        boolean errorWindowAppeared = createScriptPage().clickSaveErrorExpected();
        Assertions.assertTrue(errorWindowAppeared, "Error window appeared");

        boolean existingPubtagErrorMessageAppeared = scriptMaintenanceErrorPage().isElementDisplayed(ScriptMaintenanceErrorPageElements.EXISTING_PUBTAG_MESSAGE);
        Assertions.assertTrue(existingPubtagErrorMessageAppeared, "Existing Pubtag error message appeared");

        boolean errorPageDisappeared = scriptMaintenanceErrorPage().clickOk();
        Assertions.assertFalse(errorPageDisappeared, "Existing pubtag error page disappeared");

        Connection uatConnection = BaseDatabaseUtils.connectToDatabaseUAT();
        int script999count = ScriptMaintenanceDatabaseUtils.getScript999Count(uatConnection, ScriptMaintenanceDatabaseConstants.SCRIPT_NAME, userName);
        BaseDatabaseUtils.disconnect(uatConnection);
        Assertions.assertEquals(1, script999count, "There is a Script 999 version within the database for the new Script");
    }

    /**
     * STORY/BUGS - HALCYONST-3783 <br>
     * SUMMARY - Creates a script with a pubtag longer than 6 characters in a single content set <br>
     * USER - Legal <br>
     */
    @Test
	@EDGE
    @LEGAL
    @LOG
    public void createScriptWithPubTagLongerThan6CharactersLegalTest()
    {
        userName = user().getUsername().contains("c") ? "u" + user().getUsername() : user().getUsername();
        String pubTagWithMoreThanSixCharacters = "A000003";

        homePage().goToHomePage();
        loginPage().logIn();

        boolean scriptMaintenancePageAppears = toolsMenu().goToScriptMaintenance();
        Assertions.assertTrue(scriptMaintenancePageAppears, "Script Maintenance page appeared");

        boolean createScriptWindowAppeared = scriptMaintenanceClamshellPage().goToCreateScript();
        Assertions.assertTrue(createScriptWindowAppeared, "Create Script page appeared");

        createScriptPage().setScriptName(ScriptMaintenanceDatabaseConstants.SCRIPT_NAME);
        createScriptPage().setVersionDescription(ScriptMaintenanceDatabaseConstants.VERSION_DESCRIPTION);
        createScriptPage().setPubTag(pubTagWithMoreThanSixCharacters);
        boolean pubTagIsLongerThan6CharactersMessageAppears = createScriptPage().isElementDisplayed(CreateScriptPageElements.PUBTAG_IS_LONGER_THAN_6_CHARACTERS_MESSAGE);
        Assertions.assertTrue(pubTagIsLongerThan6CharactersMessageAppears, "Pub Tag is longer than 6 characters message appeared");

        createScriptPage().setContentSets("Iowa (Development)");
        boolean saveButtonIsDisabled = createScriptPage().isElementDisabled(CreateScriptPageElements.SAVE_BUTTON);
        Assertions.assertTrue(saveButtonIsDisabled, "Save button is disabled");
    }

    /**
     * STORY/BUGS - HALCYONST-12749, HALCYONST-3783 <br>
     * SUMMARY - Creates a script with a pubtag that doesn't start with a letter in a single content set <br>
     * USER - Legal <br>
     */
    @Test
	@EDGE
    @LEGAL
    @LOG
    public void createScriptWithPubTagNotStartingWithLetterLegalTest()
    {
        userName = user().getUsername().contains("c") ? "u" + user().getUsername() : user().getUsername();
        List<String> pubtagList = Arrays.asList("123456", "!234", "@234", "#234", "$234", "%234", "^234", "&234",
                "*234", "(234", ")234", "_234", "-234", "+234", "=234", "?234",
                ".234", "!234", "/234", " 234");

        homePage().goToHomePage();
        loginPage().logIn();

        boolean scriptMaintenancePageAppears = toolsMenu().goToScriptMaintenance();
        Assertions.assertTrue(scriptMaintenancePageAppears, "Script Maintenance page appeared");

        boolean createScriptWindowAppeared = scriptMaintenanceClamshellPage().goToCreateScript();
        Assertions.assertTrue(createScriptWindowAppeared, "Create Script page appeared");

        createScriptPage().setScriptName(ScriptMaintenanceDatabaseConstants.SCRIPT_NAME);
        createScriptPage().setVersionDescription(ScriptMaintenanceDatabaseConstants.VERSION_DESCRIPTION);
        createScriptPage().setContentSets("Iowa (Development)");

        for(String pubtagItem : pubtagList)
        {
            createScriptPage().setPubTag(pubtagItem);
            boolean pubTagCannotCreatePubtagStartingWithNumericsMessageAppears = createScriptPage().isElementDisplayed(CreateScriptPageElements.PUBTAG_CANNOT_START_WITH_NUMERIC_SPECIAL_SPACES_MESSAGE);
            Assertions.assertTrue(pubTagCannotCreatePubtagStartingWithNumericsMessageAppears, "Cannot create a pubtag that starts with a numeric value, special characters, or space message appeared");

            boolean saveButtonIsDisabled = createScriptPage().isElementDisabled(CreateScriptPageElements.SAVE_BUTTON);
            Assertions.assertTrue(saveButtonIsDisabled, "Save button is disabled");
        }
    }

    /**
     * STORY/BUGS - HALCYONST-7184, HALCYONST-7822, HALCYONST-3783, HALCYONST-5563<br>
     * SUMMARY - Creates a script with a lowercase pubtag in a single content set <br>
     * USER - Legal <br>
     */
    @Test
	@EDGE
    @LEGAL
    @LOG
    public void createScriptWithLowercasePubTagLegalTest()
    {
        userName = user().getUsername().contains("c") ? "u" + user().getUsername() : user().getUsername();

        homePage().goToHomePage();
        loginPage().logIn();

        boolean scriptMaintenancePageAppears = toolsMenu().goToScriptMaintenance();
        Assertions.assertTrue(scriptMaintenancePageAppears, "Script Maintenance page appeared");

        boolean createScriptWindowAppeared = scriptMaintenanceClamshellPage().goToCreateScript();
        Assertions.assertTrue(createScriptWindowAppeared, "Create Script page appeared");

        createScriptPage().setScriptName(ScriptMaintenanceDatabaseConstants.SCRIPT_NAME);
        createScriptPage().setVersionDescription(ScriptMaintenanceDatabaseConstants.VERSION_DESCRIPTION);
        createScriptPage().setPubTag(ScriptMaintenanceDatabaseConstants.PUB_TAG.toLowerCase());
        createScriptPage().setContentSets("Iowa (Development)");
        boolean createScriptPageDisappeared = createScriptPage().clickSave();
        Assertions.assertFalse(createScriptPageDisappeared, "Create Script page disappeared");

        boolean scriptAppearsInGrid = scriptMaintenanceGridPage().scriptExists(ScriptMaintenanceDatabaseConstants.PUB_TAG, ScriptMaintenanceDatabaseConstants.SCRIPT_NAME, ScriptMaintenanceDatabaseConstants.VERSION_DESCRIPTION, "Incomplete");
        Assertions.assertTrue(scriptAppearsInGrid, "Created script appears in grid");

        String iowaScriptId = scriptMaintenanceGridPage().getScriptIdOfScript(ScriptMaintenanceDatabaseConstants.PUB_TAG, ScriptMaintenanceDatabaseConstants.SCRIPT_NAME, ScriptMaintenanceDatabaseConstants.VERSION_DESCRIPTION, "Incomplete");
        boolean iowaScriptIdIsCorrectFormat = RegexUtils.matchesRegex(iowaScriptId, "1-\\d{4}-001");
        Assertions.assertTrue(iowaScriptIdIsCorrectFormat, "Iowa script Id is the correct format");

        Connection uatConnection = BaseDatabaseUtils.connectToDatabaseUAT();
        int script999count = ScriptMaintenanceDatabaseUtils.getScript999Count(uatConnection, ScriptMaintenanceDatabaseConstants.SCRIPT_NAME, userName);
        BaseDatabaseUtils.disconnect(uatConnection);
        Assertions.assertEquals(1, script999count, "There is a Script 999 version within the database for the new Script");
    }
}
