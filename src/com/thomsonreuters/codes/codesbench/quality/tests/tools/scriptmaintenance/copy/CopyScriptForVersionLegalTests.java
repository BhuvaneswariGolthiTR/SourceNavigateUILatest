package com.thomsonreuters.codes.codesbench.quality.tests.tools.scriptmaintenance.copy;

import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.scriptmaintenance.copy.CopyScriptForVersionPageElements;
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

public class CopyScriptForVersionLegalTests extends TestService
{
    private final String VERSION_DESCRIPTION_COPY = "A00022";
    private String userName = "";

    @AfterEach
    public void deepDeleteScriptMaintenanceTestingTags()
    {
        Connection uatConnection = BaseDatabaseUtils.connectToDatabaseUAT();
        ScriptMaintenanceDatabaseUtils.deepDeleteScriptMaintenanceTestingTags(uatConnection, ScriptMaintenanceDatabaseConstants.SCRIPT_NAME, userName, ScriptMaintenanceDatabaseConstants.PUB_TAG);
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
     * STORY/BUGS - HALCYONST-3799 <br>
     * SUMMARY - Copies a script in a single content set <br>
     * USER - Legal <br>
     */
    @Test
	@EDGE
    @LEGAL
    @LOG
    public void copyScriptForVersionSingleContentSetLegalTest()
    {
        homePage().goToHomePage();
        loginPage().logIn();

        boolean scriptMaintenancePageAppears = toolsMenu().goToScriptMaintenance();
        Assertions.assertTrue(scriptMaintenancePageAppears, "Script Maintenance page appeared");

        boolean scriptAppearsInGrid = scriptMaintenanceGridPage().scriptExists(ScriptMaintenanceDatabaseConstants.PUB_TAG, ScriptMaintenanceDatabaseConstants.SCRIPT_NAME, ScriptMaintenanceDatabaseConstants.VERSION_DESCRIPTION, "Incomplete");
        Assertions.assertTrue(scriptAppearsInGrid, "Created script appears in grid");
        String scriptId = scriptMaintenanceGridPage().getScriptIdOfScript(ScriptMaintenanceDatabaseConstants.PUB_TAG, ScriptMaintenanceDatabaseConstants.SCRIPT_NAME, ScriptMaintenanceDatabaseConstants.VERSION_DESCRIPTION, "Incomplete");

        scriptMaintenanceGridPage().selectScript(ScriptMaintenanceDatabaseConstants.PUB_TAG, ScriptMaintenanceDatabaseConstants.SCRIPT_NAME, ScriptMaintenanceDatabaseConstants.VERSION_DESCRIPTION, "Incomplete");
        DateAndTimeUtils.takeNap(DateAndTimeUtils.FIVE_SECONDS);
        boolean copyScriptPageAppeared = scriptMaintenanceClamshellPage().goToCopyScriptForVersion();
        Assertions.assertTrue(copyScriptPageAppeared, "Copy Script page appeared");

        boolean versionDescriptionRequiredMessageAppeared = copyScriptForVersionPage().isElementDisplayed(CopyScriptForVersionPageElements.VERSION_DESCRIPTION_IS_REQUIRED_MESSAGE);
        Assertions.assertTrue(versionDescriptionRequiredMessageAppeared, "Version description is required message appeared");

        List<String> selectedContentSets = copyScriptForVersionPage().getSelectedContentSets();
        boolean contentSetsMatch = selectedContentSets.contains("Iowa (Development)") && selectedContentSets.size() == 1;
        Assertions.assertTrue(contentSetsMatch, "Content Sets match");

        boolean submitButtonIsDisabled = copyScriptForVersionPage().isElementDisabled(CopyScriptForVersionPageElements.SUBMIT_BUTTON);
        Assertions.assertTrue(submitButtonIsDisabled, "Submit button is disabled");

        copyScriptForVersionPage().setVersionDescription(VERSION_DESCRIPTION_COPY);
        boolean copyScriptPageDisappeared = copyScriptForVersionPage().clickSubmit();
        Assertions.assertFalse(copyScriptPageDisappeared, "Copy Script page disappeared");

        boolean copyScriptAppearsInGrid = scriptMaintenanceGridPage().scriptExists(ScriptMaintenanceDatabaseConstants.PUB_TAG, ScriptMaintenanceDatabaseConstants.SCRIPT_NAME, VERSION_DESCRIPTION_COPY, "Pending");
        Assertions.assertTrue(copyScriptAppearsInGrid, "Copy script appears in grid");

        String copyScriptId = scriptMaintenanceGridPage().getScriptIdOfScript(ScriptMaintenanceDatabaseConstants.PUB_TAG, ScriptMaintenanceDatabaseConstants.SCRIPT_NAME, VERSION_DESCRIPTION_COPY, "Pending");
        boolean scriptIdIsCorrectFormat = RegexUtils.matchesRegex(copyScriptId, "1-\\d{4}-002");
        Assertions.assertTrue(scriptIdIsCorrectFormat, "Script Id is the correct format");

        boolean originalScriptAppearsInGrid = scriptMaintenanceGridPage().scriptExists(ScriptMaintenanceDatabaseConstants.PUB_TAG, ScriptMaintenanceDatabaseConstants.SCRIPT_NAME, ScriptMaintenanceDatabaseConstants.VERSION_DESCRIPTION, "Incomplete");
        Assertions.assertTrue(originalScriptAppearsInGrid, "Original script appears in grid");

        String scriptIdCopy = scriptMaintenanceGridPage().getScriptIdOfScript(ScriptMaintenanceDatabaseConstants.PUB_TAG, ScriptMaintenanceDatabaseConstants.SCRIPT_NAME, VERSION_DESCRIPTION_COPY, "Pending");
        Assertions.assertNotEquals(scriptIdCopy, scriptId, "The script IDs do not match");
    }

    /**
     * STORY/BUGS - HALCYONST-3799 <br>
     * SUMMARY - Copies a script in multiple content sets <br>
     * USER - Legal <br>
     */
    @Test
	@EDGE
    @LEGAL
    @LOG
    public void copyScriptForVersionMultipleContentSetsLegalTest()
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
        boolean copyScriptPageAppeared = scriptMaintenanceClamshellPage().goToCopyScriptForVersion();
        Assertions.assertTrue(copyScriptPageAppeared, "Copy Script page appeared");

        boolean versionDescriptionRequiredMessageAppeared = copyScriptForVersionPage().isElementDisplayed(CopyScriptForVersionPageElements.VERSION_DESCRIPTION_IS_REQUIRED_MESSAGE);
        Assertions.assertTrue(versionDescriptionRequiredMessageAppeared, "Version description is required message appeared");

        List<String> selectedContentSets = copyScriptForVersionPage().getSelectedContentSets();
        boolean contentSetsMatch = selectedContentSets.contains(IOWA_DEVELOPMENT.getName()) && selectedContentSets.contains(USCA_DEVELOPMENT.getName()) && selectedContentSets.size() == 2;
        Assertions.assertTrue(contentSetsMatch, "Content Sets match");

        boolean submitButtonIsDisabled = copyScriptForVersionPage().isElementDisabled(CopyScriptForVersionPageElements.SUBMIT_BUTTON);
        Assertions.assertTrue(submitButtonIsDisabled, "Submit button is disabled");

        copyScriptForVersionPage().setVersionDescription(VERSION_DESCRIPTION_COPY);
        boolean copyScriptPageDisappeared = copyScriptForVersionPage().clickSubmit();
        Assertions.assertFalse(copyScriptPageDisappeared, "Copy Script page disappeared");

        boolean copyScriptAppearsInGrid = scriptMaintenanceGridPage().scriptExists(ScriptMaintenanceDatabaseConstants.PUB_TAG, ScriptMaintenanceDatabaseConstants.SCRIPT_NAME, VERSION_DESCRIPTION_COPY, "Pending");
        Assertions.assertTrue(copyScriptAppearsInGrid, "Copy script appears in grid");

        boolean originalScriptAppearsInGrid = scriptMaintenanceGridPage().scriptExists(ScriptMaintenanceDatabaseConstants.PUB_TAG, ScriptMaintenanceDatabaseConstants.SCRIPT_NAME, ScriptMaintenanceDatabaseConstants.VERSION_DESCRIPTION, "Incomplete");
        Assertions.assertTrue(originalScriptAppearsInGrid, "Original script appears in grid");

        String copyScriptId = scriptMaintenanceGridPage().getScriptIdOfScript(ScriptMaintenanceDatabaseConstants.PUB_TAG, ScriptMaintenanceDatabaseConstants.SCRIPT_NAME, VERSION_DESCRIPTION_COPY, "Pending");
        boolean scriptIdIsCorrectFormat = RegexUtils.matchesRegex(copyScriptId, "1-\\d{4}-002");
        Assertions.assertTrue(scriptIdIsCorrectFormat, "Script Id is the correct format");

        String scriptIdCopy = scriptMaintenanceGridPage().getScriptIdOfScript(ScriptMaintenanceDatabaseConstants.PUB_TAG, ScriptMaintenanceDatabaseConstants.SCRIPT_NAME, VERSION_DESCRIPTION_COPY, "Pending");
        Assertions.assertNotEquals(scriptIdCopy, scriptId, "The script IDs do not match");

        scriptMaintenanceGridPage().closeCurrentWindowIgnoreDialogue();
        homePage().switchToPage();
        homePage().setMyContentSet("USCA(Development)");
        toolsMenu().goToScriptMaintenance();
        boolean uscaCopyScriptAppearsInGrid = scriptMaintenanceGridPage().scriptExists(ScriptMaintenanceDatabaseConstants.PUB_TAG, ScriptMaintenanceDatabaseConstants.SCRIPT_NAME, VERSION_DESCRIPTION_COPY, "Pending");
        Assertions.assertTrue(uscaCopyScriptAppearsInGrid, "Copy usca script appears in grid");

        boolean uscaoriginalScriptAppearsInGrid = scriptMaintenanceGridPage().scriptExists(ScriptMaintenanceDatabaseConstants.PUB_TAG, ScriptMaintenanceDatabaseConstants.SCRIPT_NAME, ScriptMaintenanceDatabaseConstants.VERSION_DESCRIPTION, "Incomplete");
        Assertions.assertTrue(uscaoriginalScriptAppearsInGrid, "Original usca script appears in grid");
    }

    /**
     * STORY/BUGS - HALCYONST-3799 <br>
     * SUMMARY - Cancels a copy of a script in a single content set <br>
     * USER - Legal <br>
     */
    @Test
	@EDGE
    @LEGAL
    @LOG
    public void cancelCopyScriptForVersionLegalTest()
    {
        homePage().goToHomePage();
        loginPage().logIn();

        boolean scriptMaintenancePageAppears = toolsMenu().goToScriptMaintenance();
        Assertions.assertTrue(scriptMaintenancePageAppears, "Script Maintenance page appeared");

        boolean scriptAppearsInGrid = scriptMaintenanceGridPage().scriptExists(ScriptMaintenanceDatabaseConstants.PUB_TAG, ScriptMaintenanceDatabaseConstants.SCRIPT_NAME, ScriptMaintenanceDatabaseConstants.VERSION_DESCRIPTION, "Incomplete");
        Assertions.assertTrue(scriptAppearsInGrid, "Created script appears in grid");

        scriptMaintenanceGridPage().selectScript(ScriptMaintenanceDatabaseConstants.PUB_TAG, ScriptMaintenanceDatabaseConstants.SCRIPT_NAME, ScriptMaintenanceDatabaseConstants.VERSION_DESCRIPTION, "Incomplete");
        boolean copyScriptPageAppeared = scriptMaintenanceClamshellPage().goToCopyScriptForVersion();
        Assertions.assertTrue(copyScriptPageAppeared, "Copy Script page appeared");

        boolean versionDescriptionRequiredMessageAppeared = copyScriptForVersionPage().isElementDisplayed(CopyScriptForVersionPageElements.VERSION_DESCRIPTION_IS_REQUIRED_MESSAGE);
        Assertions.assertTrue(versionDescriptionRequiredMessageAppeared, "Version description is required message appeared");

        List<String> selectedContentSets = copyScriptForVersionPage().getSelectedContentSets();
        boolean contentSetsMatch = selectedContentSets.contains("Iowa (Development)") && selectedContentSets.size() == 1;
        Assertions.assertTrue(contentSetsMatch, "Content Sets match");

        boolean submitButtonIsDisabled = copyScriptForVersionPage().isElementDisabled(CopyScriptForVersionPageElements.SUBMIT_BUTTON);
        Assertions.assertTrue(submitButtonIsDisabled, "Submit button is disabled");

        copyScriptForVersionPage().setVersionDescription(VERSION_DESCRIPTION_COPY);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);

        submitButtonIsDisabled = copyScriptForVersionPage().isElementDisabled(CopyScriptForVersionPageElements.SUBMIT_BUTTON);
        Assertions.assertFalse(submitButtonIsDisabled, "Submit button is disabled");

        boolean copyScriptPageDisappeared = copyScriptForVersionPage().clickCancel();
        Assertions.assertFalse(copyScriptPageDisappeared, "Copy Script page disappeared");

        boolean copyScriptAppearsInGrid = scriptMaintenanceGridPage().scriptExists(ScriptMaintenanceDatabaseConstants.PUB_TAG, ScriptMaintenanceDatabaseConstants.SCRIPT_NAME, VERSION_DESCRIPTION_COPY, "Pending");
        Assertions.assertFalse(copyScriptAppearsInGrid, "Copy script appears in grid");

        boolean originalScriptAppearsInGrid = scriptMaintenanceGridPage().scriptExists(ScriptMaintenanceDatabaseConstants.PUB_TAG, ScriptMaintenanceDatabaseConstants.SCRIPT_NAME, ScriptMaintenanceDatabaseConstants.VERSION_DESCRIPTION, "Incomplete");
        Assertions.assertTrue(originalScriptAppearsInGrid, "Original script appears in grid");
    }

    /**
     * STORY/BUGS - HALCYONST-3799 <br>
     * SUMMARY - Copies a script for a version in a single content set <br>
     * USER - Legal <br>
     */
    @Test
	@EDGE
    @LEGAL
    @LOG
    public void copyScriptVersionAndCompareRules()
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
        boolean copyScriptPageAppeared = scriptMaintenanceClamshellPage().goToCopyScriptForVersion();
        Assertions.assertTrue(copyScriptPageAppeared, "Copy Script page appeared");

        copyScriptForVersionPage().setVersionDescription(VERSION_DESCRIPTION_COPY);
        boolean copyScriptCreatedSubmitted = copyScriptForVersionPage().clickSubmit();
        Assertions.assertFalse(copyScriptCreatedSubmitted, "The Script was copied successfully");

        scriptAppearsInGrid = scriptMaintenanceGridPage().scriptExists(ScriptMaintenanceDatabaseConstants.PUB_TAG, ScriptMaintenanceDatabaseConstants.SCRIPT_NAME, VERSION_DESCRIPTION_COPY, "Pending");
        Assertions.assertTrue(scriptAppearsInGrid, "Copied script appears in grid");

        String copyScriptId = scriptMaintenanceGridPage().getScriptIdOfScript(ScriptMaintenanceDatabaseConstants.PUB_TAG, ScriptMaintenanceDatabaseConstants.SCRIPT_NAME, VERSION_DESCRIPTION_COPY, "Pending");
        boolean scriptIdIsCorrectFormat = RegexUtils.matchesRegex(copyScriptId, "1-\\d{4}-002");
        Assertions.assertTrue(scriptIdIsCorrectFormat, "Script Id is the correct format");

        scriptMaintenanceGridPage().selectScript(ScriptMaintenanceDatabaseConstants.PUB_TAG, ScriptMaintenanceDatabaseConstants.SCRIPT_NAME, VERSION_DESCRIPTION_COPY, "Pending");
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
    }
}
