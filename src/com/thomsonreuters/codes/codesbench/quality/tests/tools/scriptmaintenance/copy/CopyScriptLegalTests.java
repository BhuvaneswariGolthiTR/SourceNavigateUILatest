package com.thomsonreuters.codes.codesbench.quality.tests.tools.scriptmaintenance.copy;

import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.scriptmaintenance.copy.CopyScriptPageElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.EDGE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.*;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.*;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.tools.ScriptMaintenance.ScriptMaintenanceDatabaseConstants;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.tools.ScriptMaintenance.ScriptMaintenanceDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.regex.RegexUtils;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.util.List;

import static com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets.IOWA_DEVELOPMENT;
import static com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets.USCA_DEVELOPMENT;

public class CopyScriptLegalTests extends TestService
{
    private final String SCRIPT_NAME_COPY = "A00011";
    private final String VERSION_DESCRIPTION_COPY = "A00022";
    private final String PUB_TAG_COPY = "A00033";
    private String userName = "";

    @AfterEach
    public void deepDeleteScriptMaintenanceTestingTags()
    {
        Connection uatConnection = BaseDatabaseUtils.connectToDatabaseUAT();
        ScriptMaintenanceDatabaseUtils.deepDeleteScriptMaintenanceTestingTags(uatConnection, ScriptMaintenanceDatabaseConstants.SCRIPT_NAME, userName, ScriptMaintenanceDatabaseConstants.PUB_TAG);
        ScriptMaintenanceDatabaseUtils.deepDeleteScriptMaintenanceTestingTags(uatConnection, SCRIPT_NAME_COPY, userName, PUB_TAG_COPY);
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
     * STORY/BUGS - HALCYONST-11838, HALCYONST-5770, HALCYONST-3795, HALCYONST-6527, HALCYONST-5691 <br>
     * SUMMARY - Copies a script in a single content set <br>
     * USER - Legal <br>
     */
    @Test
	@EDGE
    @LEGAL
    @LOG
    public void copyScriptSingleContentSetLegalTest()
    {
        homePage().goToHomePage();
        loginPage().logIn();

        boolean scriptMaintenancePageAppears = toolsMenu().goToScriptMaintenance();
        Assertions.assertTrue(scriptMaintenancePageAppears, "Script Maintenance page appeared");

        boolean scriptAppearsInGrid = scriptMaintenanceGridPage().scriptExists(ScriptMaintenanceDatabaseConstants.PUB_TAG, ScriptMaintenanceDatabaseConstants.SCRIPT_NAME, ScriptMaintenanceDatabaseConstants.VERSION_DESCRIPTION, "Incomplete");
        Assertions.assertTrue(scriptAppearsInGrid, "Created script appears in grid");
        String scriptId = scriptMaintenanceGridPage().getScriptIdOfScript(ScriptMaintenanceDatabaseConstants.PUB_TAG, ScriptMaintenanceDatabaseConstants.SCRIPT_NAME, ScriptMaintenanceDatabaseConstants.VERSION_DESCRIPTION, "Incomplete");

        scriptMaintenanceGridPage().selectScript(ScriptMaintenanceDatabaseConstants.PUB_TAG, ScriptMaintenanceDatabaseConstants.SCRIPT_NAME, ScriptMaintenanceDatabaseConstants.VERSION_DESCRIPTION, "Incomplete");
        boolean copyScriptPageAppeared = scriptMaintenanceClamshellPage().goToCopyScript();
        Assertions.assertTrue(copyScriptPageAppeared, "Copy Script page appeared");

        boolean pubtagCannotBeTheSameAsOriginalMessageAppeared = copyScriptPage().isElementDisplayed(CopyScriptPageElements.PUBTAG_CANNOT_BE_THE_SAME_AS_ORIGINAL_MESSAGE);
        Assertions.assertTrue(pubtagCannotBeTheSameAsOriginalMessageAppeared, "Pubtag cannot be the same as original message appeared");

        List<String> selectedContentSets = copyScriptPage().getSelectedContentSets();
        boolean contentSetsMatch = selectedContentSets.contains("Iowa (Development)") && selectedContentSets.size() == 1;
        Assertions.assertTrue(contentSetsMatch, "Content Sets match");

        boolean submitButtonIsDisabled = copyScriptPage().isElementDisabled(CopyScriptPageElements.SUBMIT_BUTTON);
        Assertions.assertTrue(submitButtonIsDisabled, "Submit button is disabled");

        copyScriptPage().setPubTag(PUB_TAG_COPY);
        copyScriptPage().setScriptName(SCRIPT_NAME_COPY);
        copyScriptPage().setVersionDescription(VERSION_DESCRIPTION_COPY);

        DateAndTimeUtils.takeNap(DateAndTimeUtils.FOUR_SECONDS);

        submitButtonIsDisabled = copyScriptPage().isElementDisabled(CopyScriptPageElements.SUBMIT_BUTTON);
        Assertions.assertFalse(submitButtonIsDisabled, "Submit button is disabled");

        boolean copyScriptPageDisappeared = copyScriptPage().clickSubmit();
        Assertions.assertFalse(copyScriptPageDisappeared, "Copy Script page disappeared");

        boolean copyScriptAppearsInGrid = scriptMaintenanceGridPage().scriptExists(PUB_TAG_COPY, SCRIPT_NAME_COPY, VERSION_DESCRIPTION_COPY, "Pending");
        Assertions.assertTrue(copyScriptAppearsInGrid, "Copy script appears in grid");

        String copyScriptId = scriptMaintenanceGridPage().getScriptIdOfScript(PUB_TAG_COPY, SCRIPT_NAME_COPY, VERSION_DESCRIPTION_COPY, "Pending");
        boolean scriptIdIsCorrectFormat = RegexUtils.matchesRegex(copyScriptId, "1-\\d{4}-001");
        Assertions.assertTrue(scriptIdIsCorrectFormat, "Script Id is the correct format");

        boolean originalScriptAppearsInGrid = scriptMaintenanceGridPage().scriptExists(ScriptMaintenanceDatabaseConstants.PUB_TAG, ScriptMaintenanceDatabaseConstants.SCRIPT_NAME, ScriptMaintenanceDatabaseConstants.VERSION_DESCRIPTION, "Incomplete");
        Assertions.assertTrue(originalScriptAppearsInGrid, "Original script appears in grid");

        String scriptIdCopy = scriptMaintenanceGridPage().getScriptIdOfScript(PUB_TAG_COPY, SCRIPT_NAME_COPY, VERSION_DESCRIPTION_COPY, "Pending");
        Assertions.assertNotEquals(scriptIdCopy, scriptId, "The script IDs do not match");
    }

    /**
     * STORY/BUGS - HALCYONST-11838, HALCYONST-5770, HALCYONST-3795, HALCYONST-6527, HALCYONST-5691 <br>
     * SUMMARY - Copies a script in multiple content sets <br>
     * USER - Legal <br>
     */
    @Test
	@EDGE
    @LEGAL
    @LOG
    public void copyScriptMultipleContentSetsLegalTest()
    {
        Connection uatConnection = BaseDatabaseUtils.connectToDatabaseUAT();
        ScriptMaintenanceDatabaseUtils.insertScriptIntoOtherContentSets(uatConnection, ScriptMaintenanceDatabaseConstants.SCRIPT_ID,USCA_DEVELOPMENT.getCode());
        BaseDatabaseUtils.disconnect(uatConnection);

        homePage().goToHomePage();
        loginPage().logIn();

        boolean scriptMaintenancePageAppears = toolsMenu().goToScriptMaintenance();
        Assertions.assertTrue(scriptMaintenancePageAppears, "Script Maintenance page appeared");

        boolean scriptAppearsInGrid = scriptMaintenanceGridPage().scriptExists(ScriptMaintenanceDatabaseConstants.PUB_TAG, ScriptMaintenanceDatabaseConstants.SCRIPT_NAME, ScriptMaintenanceDatabaseConstants.VERSION_DESCRIPTION, "Incomplete");
        Assertions.assertTrue(scriptAppearsInGrid, "Created script appears in grid");
        String scriptId = scriptMaintenanceGridPage().getScriptIdOfScript(ScriptMaintenanceDatabaseConstants.PUB_TAG, ScriptMaintenanceDatabaseConstants.SCRIPT_NAME, ScriptMaintenanceDatabaseConstants.VERSION_DESCRIPTION, "Incomplete");

        scriptMaintenanceGridPage().selectScript(ScriptMaintenanceDatabaseConstants.PUB_TAG, ScriptMaintenanceDatabaseConstants.SCRIPT_NAME, ScriptMaintenanceDatabaseConstants.VERSION_DESCRIPTION, "Incomplete");
        boolean copyScriptPageAppeared = scriptMaintenanceClamshellPage().goToCopyScript();
        Assertions.assertTrue(copyScriptPageAppeared, "Copy Script page appeared");

        boolean pubtagCannotBeTheSameAsOriginalMessageAppeared = copyScriptPage().isElementDisplayed(CopyScriptPageElements.PUBTAG_CANNOT_BE_THE_SAME_AS_ORIGINAL_MESSAGE);
        Assertions.assertTrue(pubtagCannotBeTheSameAsOriginalMessageAppeared, "Pubtag cannot be the same as original message appeared");

        List<String> selectedContentSets = copyScriptPage().getSelectedContentSets();
        boolean contentSetsMatch = selectedContentSets.contains("Iowa (Development)") && selectedContentSets.contains("USCA(Development)") && selectedContentSets.size() == 2;
        Assertions.assertTrue(contentSetsMatch, "Content Sets match");

        boolean submitButtonIsDisabled = copyScriptPage().isElementDisabled(CopyScriptPageElements.SUBMIT_BUTTON);
        Assertions.assertTrue(submitButtonIsDisabled, "Submit button is disabled");

        copyScriptPage().setPubTag(PUB_TAG_COPY);
        copyScriptPage().setScriptName(SCRIPT_NAME_COPY);
        copyScriptPage().setVersionDescription(VERSION_DESCRIPTION_COPY);

        DateAndTimeUtils.takeNap(DateAndTimeUtils.FOUR_SECONDS);

        submitButtonIsDisabled = copyScriptPage().isElementDisabled(CopyScriptPageElements.SUBMIT_BUTTON);
        Assertions.assertFalse(submitButtonIsDisabled, "Submit button is disabled");

        boolean copyScriptPageDisappeared = copyScriptPage().clickSubmit();
        Assertions.assertFalse(copyScriptPageDisappeared, "Copy Script page disappeared");

        boolean copyScriptAppearsInGrid = scriptMaintenanceGridPage().scriptExists(PUB_TAG_COPY, SCRIPT_NAME_COPY, VERSION_DESCRIPTION_COPY, "Pending");
        Assertions.assertTrue(copyScriptAppearsInGrid, "Copy script appears in grid");

        String copyScriptId = scriptMaintenanceGridPage().getScriptIdOfScript(PUB_TAG_COPY, SCRIPT_NAME_COPY, VERSION_DESCRIPTION_COPY, "Pending");
        boolean scriptIdIsCorrectFormat = RegexUtils.matchesRegex(copyScriptId, "1-\\d{4}-001");
        Assertions.assertTrue(scriptIdIsCorrectFormat, "Script Id is the correct format");

        boolean originalScriptAppearsInGrid = scriptMaintenanceGridPage().scriptExists(ScriptMaintenanceDatabaseConstants.PUB_TAG, ScriptMaintenanceDatabaseConstants.SCRIPT_NAME, ScriptMaintenanceDatabaseConstants.VERSION_DESCRIPTION, "Incomplete");
        Assertions.assertTrue(originalScriptAppearsInGrid, "Original script appears in grid");

        String scriptIdCopy = scriptMaintenanceGridPage().getScriptIdOfScript(PUB_TAG_COPY, SCRIPT_NAME_COPY, VERSION_DESCRIPTION_COPY, "Pending");
        Assertions.assertNotEquals(scriptIdCopy, scriptId, "The script IDs do not match");

        scriptMaintenanceGridPage().closeCurrentWindowIgnoreDialogue();
        homePage().switchToPage();
        homePage().setMyContentSet("USCA(Development)");
        toolsMenu().goToScriptMaintenance();
        boolean uscaCopyScriptAppearsInGrid = scriptMaintenanceGridPage().scriptExists(PUB_TAG_COPY, SCRIPT_NAME_COPY, VERSION_DESCRIPTION_COPY, "Pending");
        Assertions.assertTrue(uscaCopyScriptAppearsInGrid, "Copy USCA script appears in grid");
        boolean uscaoriginalScriptAppearsInGrid = scriptMaintenanceGridPage().scriptExists(ScriptMaintenanceDatabaseConstants.PUB_TAG, ScriptMaintenanceDatabaseConstants.SCRIPT_NAME, ScriptMaintenanceDatabaseConstants.VERSION_DESCRIPTION, "Incomplete");
        Assertions.assertTrue(uscaoriginalScriptAppearsInGrid, "Original USCA script appears in grid");
    }

    /**
     * STORY/BUGS - HALCYONST-3795, HALCYONST-5691 <br>
     * SUMMARY - Cancels a copy of a script in a single content set <br>
     * USER - Legal <br>
     */
    @Test
	@EDGE
    @LEGAL
    @LOG
    public void cancelCopyScriptLegalTest()
    {
        homePage().goToHomePage();
        loginPage().logIn();

        boolean scriptMaintenancePageAppears = toolsMenu().goToScriptMaintenance();
        Assertions.assertTrue(scriptMaintenancePageAppears, "Script Maintenance page appeared");

        boolean scriptAppearsInGrid = scriptMaintenanceGridPage().scriptExists(ScriptMaintenanceDatabaseConstants.PUB_TAG, ScriptMaintenanceDatabaseConstants.SCRIPT_NAME, ScriptMaintenanceDatabaseConstants.VERSION_DESCRIPTION, "Incomplete");
        Assertions.assertTrue(scriptAppearsInGrid, "Created script appears in grid");

        scriptMaintenanceGridPage().selectScript(ScriptMaintenanceDatabaseConstants.PUB_TAG, ScriptMaintenanceDatabaseConstants.SCRIPT_NAME, ScriptMaintenanceDatabaseConstants.VERSION_DESCRIPTION, "Incomplete");
        boolean copyScriptPageAppeared = scriptMaintenanceClamshellPage().goToCopyScript();
        Assertions.assertTrue(copyScriptPageAppeared, "Copy Script page appeared");

        DateAndTimeUtils.takeNap(DateAndTimeUtils.FOUR_SECONDS);

        boolean pubtagCannotBeTheSameAsOriginalMessageAppeared = copyScriptPage().isElementDisplayed(CopyScriptPageElements.PUBTAG_CANNOT_BE_THE_SAME_AS_ORIGINAL_MESSAGE);
        Assertions.assertTrue(pubtagCannotBeTheSameAsOriginalMessageAppeared, "Pubtag cannot be the same as original message appeared");

        List<String> selectedContentSets = copyScriptPage().getSelectedContentSets();
        boolean contentSetsMatch = selectedContentSets.contains("Iowa (Development)") && selectedContentSets.size() == 1;
        Assertions.assertTrue(contentSetsMatch, "Content Sets match");

        boolean submitButtonIsDisabled = copyScriptPage().isElementDisabled(CopyScriptPageElements.SUBMIT_BUTTON);
        Assertions.assertTrue(submitButtonIsDisabled, "Submit button is disabled");

        copyScriptPage().setPubTag(PUB_TAG_COPY);
        copyScriptPage().setScriptName(SCRIPT_NAME_COPY);
        copyScriptPage().setVersionDescription(VERSION_DESCRIPTION_COPY);

        DateAndTimeUtils.takeNap(DateAndTimeUtils.FOUR_SECONDS);

        submitButtonIsDisabled = copyScriptPage().isElementDisabled(CopyScriptPageElements.SUBMIT_BUTTON);
        Assertions.assertFalse(submitButtonIsDisabled, "Submit button is disabled");

        boolean copyScriptPageDisappeared = copyScriptPage().clickCancel();
        Assertions.assertFalse(copyScriptPageDisappeared, "Copy Script page disappeared");

        boolean copyScriptAppearsInGrid = scriptMaintenanceGridPage().scriptExists(PUB_TAG_COPY, SCRIPT_NAME_COPY, VERSION_DESCRIPTION_COPY, "Pending");
        Assertions.assertFalse(copyScriptAppearsInGrid, "Copy script appears in grid");

        boolean originalScriptAppearsInGrid = scriptMaintenanceGridPage().scriptExists(ScriptMaintenanceDatabaseConstants.PUB_TAG, ScriptMaintenanceDatabaseConstants.SCRIPT_NAME, ScriptMaintenanceDatabaseConstants.VERSION_DESCRIPTION, "Incomplete");
        Assertions.assertTrue(originalScriptAppearsInGrid, "Original script appears in grid");
    }

    /**
     * STORY/BUGS - HALCYONST-11838,HALCYONST-5770,HALCYONST-3795, HALCYONST-6527, HALCYONST-5691 <br>
     * SUMMARY - Copies a script in a single content set <br>
     * USER - Legal <br>
     */
    @Test
	@EDGE
    @LEGAL
    @LOG
    public void copyScriptAndCompareRules()
    {
        Connection uatConnection = BaseDatabaseUtils.connectToDatabaseUAT();
        ScriptMaintenanceDatabaseUtils.insertSelectRangeScriptRuleIntoGivenScript(uatConnection, ScriptMaintenanceDatabaseConstants.SCRIPT_ID, userName, "CopySM","CopyEM", 1,3);
        BaseDatabaseUtils.disconnect(uatConnection);

        homePage().goToHomePage();
        loginPage().logIn();

        boolean scriptMaintenancePageAppears = toolsMenu().goToScriptMaintenance();
        Assertions.assertTrue(scriptMaintenancePageAppears, "Script Maintenance page appeared");

        boolean scriptAppearsInGrid = scriptMaintenanceGridPage().scriptExists(ScriptMaintenanceDatabaseConstants.PUB_TAG, ScriptMaintenanceDatabaseConstants.SCRIPT_NAME, ScriptMaintenanceDatabaseConstants.VERSION_DESCRIPTION, "Incomplete");
        Assertions.assertTrue(scriptAppearsInGrid, "Created script appears in grid");

        scriptMaintenanceGridPage().selectScript(ScriptMaintenanceDatabaseConstants.PUB_TAG, ScriptMaintenanceDatabaseConstants.SCRIPT_NAME, ScriptMaintenanceDatabaseConstants.VERSION_DESCRIPTION, "Incomplete");
        boolean copyScriptPageAppeared = scriptMaintenanceClamshellPage().goToCopyScript();
        Assertions.assertTrue(copyScriptPageAppeared, "Copy Script page appeared");

        copyScriptPage().setPubTag(PUB_TAG_COPY);
        copyScriptPage().setScriptName(SCRIPT_NAME_COPY);
        copyScriptPage().setVersionDescription(VERSION_DESCRIPTION_COPY);
        boolean copyScriptCreatedSubmitted = copyScriptPage().clickSubmit();
        Assertions.assertFalse(copyScriptCreatedSubmitted, "The Script was copied successfully");

        scriptAppearsInGrid = scriptMaintenanceGridPage().scriptExists(PUB_TAG_COPY, SCRIPT_NAME_COPY, VERSION_DESCRIPTION_COPY, "Pending");
        Assertions.assertTrue(scriptAppearsInGrid, "Copied script appears in grid");

        String copyScriptId = scriptMaintenanceGridPage().getScriptIdOfScript(PUB_TAG_COPY, SCRIPT_NAME_COPY, VERSION_DESCRIPTION_COPY, "Pending");
        boolean scriptIdIsCorrectFormat = RegexUtils.matchesRegex(copyScriptId, "1-\\d{4}-001");
        Assertions.assertTrue(scriptIdIsCorrectFormat, "Script Id is the correct format");

        scriptMaintenanceGridPage().selectScript(PUB_TAG_COPY, SCRIPT_NAME_COPY, VERSION_DESCRIPTION_COPY, "Pending");
        boolean editScriptRulesPageAppeared = scriptMaintenanceClamshellPage().goToEditScriptRules();
        Assertions.assertTrue(editScriptRulesPageAppeared, "Edit Script Version page appeared");

        editScriptRulesConfirmationPage().clickOK();
        String actualScriptRuleText = editScriptRulesPage().getScriptRuleText("1");
        Assertions.assertEquals(" Select Range CopySM to CopyEM",actualScriptRuleText,"The Script Rule Text Matches what was expected");

        editScriptRulesPage().setScriptAction("Select Range");
        editScriptRulesPage().setStartMnemonic("TCopyS");
        editScriptRulesPage().setEndMnemonic("TCopyE");
        editScriptRulesPage().clickUpdateSelectedRuleButton();
        actualScriptRuleText = editScriptRulesPage().getScriptRuleText("1");
        Assertions.assertEquals(" Select Range TCopyS to TCopyE",actualScriptRuleText,"The Script Rule Text Matches what was expected");

        editScriptRulesPage().clickSave();
        scriptMaintenanceGridPage().selectScript(ScriptMaintenanceDatabaseConstants.PUB_TAG, ScriptMaintenanceDatabaseConstants.SCRIPT_NAME, ScriptMaintenanceDatabaseConstants.VERSION_DESCRIPTION, "Incomplete");
        editScriptRulesPageAppeared = scriptMaintenanceClamshellPage().goToEditScriptRules();
        Assertions.assertTrue(editScriptRulesPageAppeared, "Edit Script page appeared");

        editScriptRulesConfirmationPage().clickOK();
        actualScriptRuleText = editScriptRulesPage().getScriptRuleText("1");
        Assertions.assertEquals(" Select Range CopySM to CopyEM",actualScriptRuleText,"The Script Rule Text wasn't updated when editing a Script version");

        uatConnection = BaseDatabaseUtils.connectToDatabaseUAT();
        int script999count = ScriptMaintenanceDatabaseUtils.getScript999Count(uatConnection, SCRIPT_NAME_COPY, userName);
        BaseDatabaseUtils.disconnect(uatConnection);
        Assertions.assertEquals(1, script999count, "There is a Script 999 version within the database for the new Script");
    }
}