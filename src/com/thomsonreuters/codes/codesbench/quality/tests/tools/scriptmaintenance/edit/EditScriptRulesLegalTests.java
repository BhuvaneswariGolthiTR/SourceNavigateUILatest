package com.thomsonreuters.codes.codesbench.quality.tests.tools.scriptmaintenance.edit;

import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.scriptmaintenance.edit.EditScriptRulesPageElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.EDGE;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.tools.ScriptMaintenance.ScriptMaintenanceDatabaseConstants;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.tools.ScriptMaintenance.ScriptMaintenanceDatabaseUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.*;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.sql.Connection;

import static com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets.IOWA_DEVELOPMENT;

public class EditScriptRulesLegalTests extends TestService
{
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
     * STORY/BUGS: HALCYONST-3807 <br>
     * SUMMARY: Opens Edit Script Rules in Script Maintenance (NEW) and goes through each of the Script Actions to see
     * if the fields are disabled/enabled as they should be <br>
     * USER - Legal <br>
     */
    @ParameterizedTest(name = "Test: {displayName}; Index: {index}; Arguments: {arguments}")
    @CsvSource
    (
    value =
        {
            "Select Range,False,True,True,True,True,True,True,True",
            "Select Pubtag,True,False,False,False,False,False,False,False",
            "Select Mnemonic,False,True,False,True,False,True,False,False",
            "Select Group Hierarchy,False,True,False,True,False,True,False,False",
            "Select Group,False,True,False,True,False,True,False,False",
            "Drop Range,False,True,True,True,True,True,True,True",
            "Drop Pubtag,True,False,False,False,False,False,False,False",
            "Drop Mnemonic,False,True,False,True,False,True,False,False",
            "Drop Group,False,True,False,True,False,True,False,False",
            "Drop Document,False,True,False,True,False,True,False,False"
        }
    )
    @EDGE
    @LEGAL
    @LOG
    public void isElementDisabledOrEnabledDependingOnScriptActionLegalTest(String scriptAction, boolean pubtag, boolean startMnemonic,
                                                                  boolean endMnemonic, boolean startPhrase, boolean endPhrase,
                                                                  boolean startPhrasePosition, boolean endPhrasePosition, boolean assignPubtagToEndMnemonicOrPhrase)
    {
        String scriptLabelData = "1-0000-001 A00001 A00002";

        homePage().goToHomePage();
        loginPage().logIn();

        boolean scriptMaintenancePageAppears = toolsMenu().goToScriptMaintenance();
        Assertions.assertTrue(scriptMaintenancePageAppears, "Script Maintenance page appeared");

        boolean scriptAppearsInGrid = scriptMaintenanceGridPage().scriptExists(ScriptMaintenanceDatabaseConstants.PUB_TAG, ScriptMaintenanceDatabaseConstants.SCRIPT_NAME, ScriptMaintenanceDatabaseConstants.VERSION_DESCRIPTION, "Incomplete");
        Assertions.assertTrue(scriptAppearsInGrid, "Created script appears in grid");

        scriptMaintenanceGridPage().selectScript(ScriptMaintenanceDatabaseConstants.PUB_TAG, ScriptMaintenanceDatabaseConstants.SCRIPT_NAME, ScriptMaintenanceDatabaseConstants.VERSION_DESCRIPTION, "Incomplete");
        boolean editScriptRulesPageAppeared = scriptMaintenanceClamshellPage().goToEditScriptRules();
        Assertions.assertTrue(editScriptRulesPageAppeared, "Edit Script Rules page appeared");

        editScriptRulesConfirmationPage().clickOK();
        editScriptRulesPage().setScriptAction(scriptAction);
        boolean isScriptInfoAtTopCorrect = editScriptRulesPage().getElementsText(EditScriptRulesPageElements.scriptLabel).contains(scriptLabelData);
        boolean isPubTagCorrect = editScriptRulesPage().getElementsText(EditScriptRulesPageElements.existingPubTag).contains(ScriptMaintenanceDatabaseConstants.PUB_TAG);
        boolean pubtagAvailability = editScriptRulesPage().isElementEnabled(EditScriptRulesPageElements.pubtagInput);
        boolean startMnemonicAvailability = editScriptRulesPage().isElementEnabled(EditScriptRulesPageElements.startMnemonic);
        boolean endMnemonicAvailability = editScriptRulesPage().isElementEnabled(EditScriptRulesPageElements.endMnemonic);
        boolean startPhraseAvailability = editScriptRulesPage().isElementEnabled(EditScriptRulesPageElements.startPhrase);
        boolean endPhraseAvailability = editScriptRulesPage().isElementEnabled(EditScriptRulesPageElements.endPhrase);
        boolean startPhrasePositionAvailability = editScriptRulesPage().isElementEnabled(EditScriptRulesPageElements.startPhrasePosition);
        boolean endPhrasePositionAvailability = editScriptRulesPage().isElementEnabled(EditScriptRulesPageElements.endPhrasePosition);
        boolean assignPubtagToEndMnemonicOrPhraseAvailability = editScriptRulesPage().isElementEnabled(EditScriptRulesPageElements.ASSIGN_PUBTAG_TO_END_MNEMONIC_OR_PHRASE_CHECKBOX);

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(isScriptInfoAtTopCorrect,"The Script label at the top of the window matched what was inserted into the database"),
            () -> Assertions.assertTrue(isPubTagCorrect,"The Pubtag label at the top of the window matched what was inserted into the database"),
            () -> Assertions.assertEquals(pubtag,pubtagAvailability,"Pubtag was enabled/disabled as expected"),
            () -> Assertions.assertEquals(startMnemonic,startMnemonicAvailability,"Start Mnemonic was enabled/disabled as expected"),
            () -> Assertions.assertEquals(endMnemonic,endMnemonicAvailability,"End Mnemonic was enabled/disabled as expected"),
            () -> Assertions.assertEquals(startPhrase,startPhraseAvailability,"Start Phrase was enabled/disabled as expected"),
            () -> Assertions.assertEquals(endPhrase,endPhraseAvailability,"End Phrase was enabled/disabled as expected"),
            () -> Assertions.assertEquals(startPhrasePosition,startPhrasePositionAvailability,"Start Phrase Position was enabled/disabled as expected"),
            () -> Assertions.assertEquals(endPhrasePosition,endPhrasePositionAvailability,"End Phrase Position was enabled/disabled as expected"),
            () -> Assertions.assertEquals(assignPubtagToEndMnemonicOrPhrase,assignPubtagToEndMnemonicOrPhraseAvailability,"Assign Pubtag to End Mnemonic/Phrase was enabled/disabled as expected")
        );
    }

    /**
     * STORY/BUGS - HALCYONST-11850, HALCYONST-3807 <br>
     * SUMMARY - Opens the Edit Script Rules Page and checks to see if the Add At End, Add Before, Add After, and Update Selected Rule buttons are Enabled/Disabled
     * Depending on what rules are present. <br>
     * USER - Legal <br>
     */
    @Test
	@EDGE
    @LEGAL
    @LOG
    public void isEditScriptPlacementButtonEnabledOrDisabledLegalTest()
    {
        homePage().goToHomePage();
        loginPage().logIn();

        boolean scriptMaintenancePageAppears = toolsMenu().goToScriptMaintenance();
        Assertions.assertTrue(scriptMaintenancePageAppears, "Script Maintenance page appeared");

        boolean scriptAppearsInGrid = scriptMaintenanceGridPage().scriptExists(ScriptMaintenanceDatabaseConstants.PUB_TAG, ScriptMaintenanceDatabaseConstants.SCRIPT_NAME, ScriptMaintenanceDatabaseConstants.VERSION_DESCRIPTION, "Incomplete");
        Assertions.assertTrue(scriptAppearsInGrid, "Created script appears in grid");

        scriptMaintenanceGridPage().selectScript(ScriptMaintenanceDatabaseConstants.PUB_TAG, ScriptMaintenanceDatabaseConstants.SCRIPT_NAME, ScriptMaintenanceDatabaseConstants.VERSION_DESCRIPTION, "Incomplete");
        boolean editScriptRulesPageAppeared = scriptMaintenanceClamshellPage().goToEditScriptRules();
        Assertions.assertTrue(editScriptRulesPageAppeared, "Edit Script Rules page appeared");

        editScriptRulesConfirmationPage().clickOK();
        boolean isAddAtEndEnabledWithNoRulesOrData = editScriptRulesPage().isElementEnabled(EditScriptRulesPageElements.ADD_AT_END_BUTTON);
        boolean isAddBeforeEnabledWithNoRulesOrData = editScriptRulesPage().isElementEnabled(EditScriptRulesPageElements.ADD_BEFORE_BUTTON);
        boolean isAddAfterEnabledWithNoRulesOrData = editScriptRulesPage().isElementEnabled(EditScriptRulesPageElements.ADD_AFTER_BUTTON);
        boolean isUpdateSelectedRuleWithNoRulesOrData = editScriptRulesPage().isElementEnabled(EditScriptRulesPageElements.UPDATE_SELECTED_RULE_BUTTON);
        editScriptRulesPage().setScriptAction("Select Range");
        editScriptRulesPage().setStartMnemonic("Starti");
        editScriptRulesPage().setEndMnemonic("Ending");
        boolean isAddAtEndEnabledWithDataAndNoRules = editScriptRulesPage().isElementEnabled(EditScriptRulesPageElements.ADD_AT_END_BUTTON);
        Assertions.assertTrue(isAddAtEndEnabledWithDataAndNoRules, "Add At End is Enabled with no script rules in the grid and data filled out.");

        boolean isAddBeforeEnabledWithDataNoRules = editScriptRulesPage().isElementEnabled(EditScriptRulesPageElements.ADD_BEFORE_BUTTON);
        boolean isAddAfterEnabledWithDataNoRules = editScriptRulesPage().isElementEnabled(EditScriptRulesPageElements.ADD_AFTER_BUTTON);
        boolean isUpdateSelectedRuleEnabledWithDataNoRules = editScriptRulesPage().isElementEnabled(EditScriptRulesPageElements.UPDATE_SELECTED_RULE_BUTTON);
        editScriptRulesPage().clickAddAtEndButton();

        boolean isAddAtEndEnabledWithRulesAndData = editScriptRulesPage().isElementEnabled(EditScriptRulesPageElements.ADD_AT_END_BUTTON);
        boolean isAddBeforeEnabledWithRulesAndData = editScriptRulesPage().isElementEnabled(EditScriptRulesPageElements.ADD_BEFORE_BUTTON);
        boolean isAddAfterEnabledWithRulesAndData = editScriptRulesPage().isElementEnabled(EditScriptRulesPageElements.ADD_AFTER_BUTTON);
        boolean isUpdateSelectedRulesEnabledWithRulesAndData = editScriptRulesPage().isElementEnabled(EditScriptRulesPageElements.UPDATE_SELECTED_RULE_BUTTON);
        editScriptRulesPage().setScriptAction("Drop Pubtag"); //Just need something to clear the text and switching to a script action
        boolean isAddAtEndEnabledWithRulesNoData = editScriptRulesPage().isElementEnabled(EditScriptRulesPageElements.ADD_AT_END_BUTTON);
        boolean isAddBeforeEnabledWithRulesNoData = editScriptRulesPage().isElementEnabled(EditScriptRulesPageElements.ADD_BEFORE_BUTTON);
        boolean isAddAfterEnabledWithRulesNoData = editScriptRulesPage().isElementEnabled(EditScriptRulesPageElements.ADD_AFTER_BUTTON);
        boolean isUpdateSelectedRulesEnabledWithRulesNoData = editScriptRulesPage().isElementEnabled(EditScriptRulesPageElements.UPDATE_SELECTED_RULE_BUTTON);

        Assertions.assertAll(
            () -> Assertions.assertFalse(isAddAtEndEnabledWithNoRulesOrData, "Add at End is disabled with no scripts in the grid and no data filled out."),
            () -> Assertions.assertFalse(isAddBeforeEnabledWithNoRulesOrData, "Add Before is disabled with no scripts in the grid and no data filled out."),
            () -> Assertions.assertFalse(isAddAfterEnabledWithNoRulesOrData, "Add After is disabled with no scripts in the grid and no data filled out."),
            () -> Assertions.assertFalse(isUpdateSelectedRuleWithNoRulesOrData, "Update Selected Rule is disabled with no scripts in the grid and no data filled out."),

            () -> Assertions.assertTrue(isAddBeforeEnabledWithDataNoRules, "Add Before is disabled with no scripts in the grid and with data filled out."),
            () -> Assertions.assertTrue(isAddAfterEnabledWithDataNoRules, "Add After is disabled with no scripts in the grid and with data filled out."),
            () -> Assertions.assertTrue(isUpdateSelectedRuleEnabledWithDataNoRules, "Update Selected Rule is disabled with no scripts in the grid and with data filled out."),

            () -> Assertions.assertTrue(isAddAtEndEnabledWithRulesAndData, "Add at End is enabled with scripts in the grid and data filled out."),
            () -> Assertions.assertTrue(isAddBeforeEnabledWithRulesAndData, "Add Before is enabled with scripts in the grid and data filled out."),
            () -> Assertions.assertTrue(isAddAfterEnabledWithRulesAndData, "Add After is enabled with scripts in the grid and data filled out."),
            () -> Assertions.assertTrue(isUpdateSelectedRulesEnabledWithRulesAndData,"Update Selected Rules is enabled with scripts in the grid and data filled out."),

            () -> Assertions.assertFalse(isAddAtEndEnabledWithRulesNoData, "Add at End is disabled with scripts in the grid and no data filled out."),
            () -> Assertions.assertFalse(isAddBeforeEnabledWithRulesNoData, "Add Before is disabled with scripts in the grid and no data filled out."),
            () -> Assertions.assertFalse(isAddAfterEnabledWithRulesNoData, "Add After is disabled with scripts in the grid and no data filled out."),
            () -> Assertions.assertFalse(isUpdateSelectedRulesEnabledWithRulesNoData, "Update Selected Rule is disabled with scripts in the grid and no data filled out.")
        );
    }

    /**
     * STORY/BUGS - HALCYONST-11850, HALCYONST 5758, HALCYONST-3807 <br>
     * SUMMARY - This test goes to edit script rules and makes a "Select Range" rule, and clicks add at end. It then closes and reopens the page to ensure the rule was added.
     * Then it makes two more rules to ensure that the new rules are added at the end no matter if the current script selected isn't at the end<br>
     * USER - Legal <br>
     */
    @Test
	@EDGE
    @LEGAL
    @LOG
    public void addAtEndAndSaveLegalTest()
    {
        homePage().goToHomePage();
        loginPage().logIn();

        boolean scriptMaintenancePageAppears = toolsMenu().goToScriptMaintenance();
        Assertions.assertTrue(scriptMaintenancePageAppears, "Script Maintenance page appeared");

        boolean scriptAppearsInGrid = scriptMaintenanceGridPage().scriptExists(ScriptMaintenanceDatabaseConstants.PUB_TAG, ScriptMaintenanceDatabaseConstants.SCRIPT_NAME, ScriptMaintenanceDatabaseConstants.VERSION_DESCRIPTION, "Incomplete");
        Assertions.assertTrue(scriptAppearsInGrid, "Created script appears in grid");

        scriptMaintenanceGridPage().selectScript(ScriptMaintenanceDatabaseConstants.PUB_TAG, ScriptMaintenanceDatabaseConstants.SCRIPT_NAME, ScriptMaintenanceDatabaseConstants.VERSION_DESCRIPTION, "Incomplete");
        boolean editScriptRulesPageAppeared = scriptMaintenanceClamshellPage().goToEditScriptRules();
        Assertions.assertTrue(editScriptRulesPageAppeared, "Edit Script Rules page appeared");

        editScriptRulesConfirmationPage().clickOK();
        editScriptRulesPage().setScriptAction("Select Range");
        editScriptRulesPage().setStartMnemonic("TestSM");
        editScriptRulesPage().setEndMnemonic("TestEM");
        editScriptRulesPage().clickAddAtEndButton();
        editScriptRulesPage().clickSave();
        scriptMaintenanceGridPage().selectScript(ScriptMaintenanceDatabaseConstants.PUB_TAG, ScriptMaintenanceDatabaseConstants.SCRIPT_NAME, ScriptMaintenanceDatabaseConstants.VERSION_DESCRIPTION, "Incomplete");
        editScriptRulesPageAppeared = scriptMaintenanceClamshellPage().goToEditScriptRules();
        Assertions.assertTrue(editScriptRulesPageAppeared, "Edit Script Rules page appeared");

        editScriptRulesConfirmationPage().clickOK();
        String actualScriptRuleText = editScriptRulesPage().getScriptRuleText("1");
        Assertions.assertEquals(" Select Range TestSM to TestEM",actualScriptRuleText,"The Script Rule Text Matches what was expected");

        editScriptRulesPage().setScriptAction("Select Range");
        editScriptRulesPage().setStartMnemonic("TesSM2");
        editScriptRulesPage().setEndMnemonic("TesEM2");
        editScriptRulesPage().clickAddAtEndButton();
        editScriptRulesPage().selectScriptRuleGivenIndex("1");
        editScriptRulesPage().setScriptAction("Select Range");
        editScriptRulesPage().setStartMnemonic("TesSM3");
        editScriptRulesPage().setEndMnemonic("TesEM3");
        editScriptRulesPage().clickAddAtEndButton();
        actualScriptRuleText = editScriptRulesPage().getScriptRuleText("3");
        Assertions.assertEquals(" Select Range TesSM3 to TesEM3",actualScriptRuleText,"The Script Rule Text Matches what was expected");
    }

    /**
     * STORY/BUGS - HALCYONST-11850, HALCYONST-3807 <br>
     * SUMMARY - This test goes into edit script rules, it then adds a rule at the end so that there can be a rule already in. Then it makes a new rule and clicks add before.
     * It then checks both indexes to ensure that the rules are in the right order<br>
     * USER - Legal <br>
     */
    @Test
	@EDGE
    @LEGAL
    @LOG
    public void addBeforeAndSaveLegalTest()
    {
        homePage().goToHomePage();
        loginPage().logIn();

        boolean scriptMaintenancePageAppears = toolsMenu().goToScriptMaintenance();
        Assertions.assertTrue(scriptMaintenancePageAppears, "Script Maintenance page appeared");

        boolean scriptAppearsInGrid = scriptMaintenanceGridPage().scriptExists(ScriptMaintenanceDatabaseConstants.PUB_TAG, ScriptMaintenanceDatabaseConstants.SCRIPT_NAME, ScriptMaintenanceDatabaseConstants.VERSION_DESCRIPTION, "Incomplete");
        Assertions.assertTrue(scriptAppearsInGrid, "Created script appears in grid");

        scriptMaintenanceGridPage().selectScript(ScriptMaintenanceDatabaseConstants.PUB_TAG, ScriptMaintenanceDatabaseConstants.SCRIPT_NAME, ScriptMaintenanceDatabaseConstants.VERSION_DESCRIPTION, "Incomplete");
        boolean editScriptRulesPageAppeared = scriptMaintenanceClamshellPage().goToEditScriptRules();
        Assertions.assertTrue(editScriptRulesPageAppeared, "Edit Script Rules page appeared");

        editScriptRulesConfirmationPage().clickOK();
        editScriptRulesPage().setScriptAction("Select Range");
        editScriptRulesPage().setStartMnemonic("TesSMa");
        editScriptRulesPage().setEndMnemonic("TesEMa");
        editScriptRulesPage().clickAddAtEndButton();
        editScriptRulesPage().setScriptAction("Select Range");
        editScriptRulesPage().setStartMnemonic("TesSMb");
        editScriptRulesPage().setEndMnemonic("TesEMb");
        editScriptRulesPage().clickAddBeforeButton();
        boolean firstScriptRuleTextIsValid = editScriptRulesPage().getScriptRuleText("1").equals(" Select Range TesSMb to TesEMb");
        boolean secondScriptRuleTextIsValid = editScriptRulesPage().getScriptRuleText("2").equals(" Select Range TesSMa to TesEMa");
        Assertions.assertAll(
            () -> Assertions.assertTrue(firstScriptRuleTextIsValid, "First Script Rule has the expected information"),
            () -> Assertions.assertTrue(secondScriptRuleTextIsValid, "Second Script Rule has the expected information")
        );
    }

    /**
     * STORY/BUGS - HALCYONST-11850, HALCYONST-3807 <br>
     * SUMMARY - This test goes to edit script rules and adds a rule at the end, then it makes another rule and clicks add after. It then checks the two indexes to ensure
     * that they have the proper rules<br>
     * USER - Legal <br>
     */
    @Test
	@EDGE
    @LEGAL
    @LOG
    public void addAfterAndSaveLegalTest()
    {
        homePage().goToHomePage();
        loginPage().logIn();

        boolean scriptMaintenancePageAppears = toolsMenu().goToScriptMaintenance();
        Assertions.assertTrue(scriptMaintenancePageAppears, "Script Maintenance page appeared");

        boolean scriptAppearsInGrid = scriptMaintenanceGridPage().scriptExists(ScriptMaintenanceDatabaseConstants.PUB_TAG, ScriptMaintenanceDatabaseConstants.SCRIPT_NAME, ScriptMaintenanceDatabaseConstants.VERSION_DESCRIPTION, "Incomplete");
        Assertions.assertTrue(scriptAppearsInGrid, "Created script appears in grid");

        scriptMaintenanceGridPage().selectScript(ScriptMaintenanceDatabaseConstants.PUB_TAG, ScriptMaintenanceDatabaseConstants.SCRIPT_NAME, ScriptMaintenanceDatabaseConstants.VERSION_DESCRIPTION, "Incomplete");
        boolean editScriptRulesPageAppeared = scriptMaintenanceClamshellPage().goToEditScriptRules();
        Assertions.assertTrue(editScriptRulesPageAppeared, "Edit Script Rules page appeared");

        editScriptRulesConfirmationPage().clickOK();
        editScriptRulesPage().setScriptAction("Select Range");
        editScriptRulesPage().setStartMnemonic("TestSM");
        editScriptRulesPage().setEndMnemonic("TestEM");
        editScriptRulesPage().clickAddAtEndButton();
        editScriptRulesPage().setScriptAction("Select Range");
        editScriptRulesPage().setStartMnemonic("Test");
        editScriptRulesPage().setEndMnemonic("Test2");
        editScriptRulesPage().clickAddAfterButton();
        editScriptRulesPage().clickSave();
        scriptMaintenanceGridPage().selectScript(ScriptMaintenanceDatabaseConstants.PUB_TAG, ScriptMaintenanceDatabaseConstants.SCRIPT_NAME, ScriptMaintenanceDatabaseConstants.VERSION_DESCRIPTION, "Incomplete");
        editScriptRulesPageAppeared = scriptMaintenanceClamshellPage().goToEditScriptRules();
        Assertions.assertTrue(editScriptRulesPageAppeared, "Edit Script Rules page appeared");

        editScriptRulesConfirmationPage().clickOK();
        boolean firstScriptRuleTextIsValid = editScriptRulesPage().getScriptRuleText("1").equals(" Select Range TestSM to TestEM");
        boolean secondScriptRuleTextIsValid = editScriptRulesPage().getScriptRuleText("2").equals(" Select Range Test to Test2");
        Assertions.assertAll(
                () -> Assertions.assertTrue(firstScriptRuleTextIsValid, "First Script Rule has the expected information"),
                () -> Assertions.assertTrue(secondScriptRuleTextIsValid, "Second Script Rule has the expected information")
        );
    }

    /**
     * STORY/BUGS - HALCYONST-11850, HALCYONST-3807 <br>
     * SUMMARY - This test goes to edit script rules and adds a rule at end. Then it closes and reopens the edit script rule window and then makes a new rule to update the old one. Then
     * it clicks updated selected rule and closes and reopens the window to ensure the change worked <br>
     * USER - Legal <br>
     */
    @Test
	@EDGE
    @LEGAL
    @LOG
    public void updateSelectedRuleAndSaveLegalTest()
    {
        homePage().goToHomePage();
        loginPage().logIn();

        boolean scriptMaintenancePageAppears = toolsMenu().goToScriptMaintenance();
        Assertions.assertTrue(scriptMaintenancePageAppears, "Script Maintenance page appeared");

        boolean scriptAppearsInGrid = scriptMaintenanceGridPage().scriptExists(ScriptMaintenanceDatabaseConstants.PUB_TAG, ScriptMaintenanceDatabaseConstants.SCRIPT_NAME, ScriptMaintenanceDatabaseConstants.VERSION_DESCRIPTION, "Incomplete");
        Assertions.assertTrue(scriptAppearsInGrid, "Created script appears in grid");

        scriptMaintenanceGridPage().selectScript(ScriptMaintenanceDatabaseConstants.PUB_TAG, ScriptMaintenanceDatabaseConstants.SCRIPT_NAME, ScriptMaintenanceDatabaseConstants.VERSION_DESCRIPTION, "Incomplete");
        boolean editScriptRulesPageAppeared = scriptMaintenanceClamshellPage().goToEditScriptRules();
        Assertions.assertTrue(editScriptRulesPageAppeared, "Edit Script Rules page appeared");

        editScriptRulesConfirmationPage().clickOK();
        editScriptRulesPage().setScriptAction("Select Range");
        editScriptRulesPage().setStartMnemonic("TestSM");
        editScriptRulesPage().setEndMnemonic("TestEM");
        editScriptRulesPage().clickAddAtEndButton();
        editScriptRulesPage().clickSave();
        scriptMaintenanceGridPage().selectScript(ScriptMaintenanceDatabaseConstants.PUB_TAG, ScriptMaintenanceDatabaseConstants.SCRIPT_NAME, ScriptMaintenanceDatabaseConstants.VERSION_DESCRIPTION, "Incomplete");
        editScriptRulesPageAppeared = scriptMaintenanceClamshellPage().goToEditScriptRules();
        Assertions.assertTrue(editScriptRulesPageAppeared, "Edit Script Rules page appeared");

        editScriptRulesConfirmationPage().clickOK();
        editScriptRulesPage().setScriptAction("Select Range");
        editScriptRulesPage().setStartMnemonic("Test");
        editScriptRulesPage().setEndMnemonic("Test2");
        editScriptRulesPage().clickUpdateSelectedRuleButton();
        editScriptRulesPage().clickSave();
        scriptMaintenanceGridPage().selectScript(ScriptMaintenanceDatabaseConstants.PUB_TAG, ScriptMaintenanceDatabaseConstants.SCRIPT_NAME, ScriptMaintenanceDatabaseConstants.VERSION_DESCRIPTION, "Incomplete");
        editScriptRulesPageAppeared = scriptMaintenanceClamshellPage().goToEditScriptRules();
        Assertions.assertTrue(editScriptRulesPageAppeared, "Edit Script Rules page appeared");

        editScriptRulesConfirmationPage().clickOK();
        String actualScriptRuleText = editScriptRulesPage().getScriptRuleText("1");
        Assertions.assertEquals(" Select Range Test to Test2",actualScriptRuleText,"The Script Rule Text Matches what was expected");
    }

    /**
     * STORY/BUGS - HALCYONST-11850, HALCYONST-3807 <br>
     * SUMMARY - This test opens edit script rules and adds a new rule at end and clicks cancel. It then reopens the window to ensure the rule doesn't appear <br>
     * USER - Legal <br>
     */
    @Test
	@EDGE
    @LEGAL
    @LOG
    public void editScriptRulesCancelLegalTest()
    {
        homePage().goToHomePage();
        loginPage().logIn();

        boolean scriptMaintenancePageAppears = toolsMenu().goToScriptMaintenance();
        Assertions.assertTrue(scriptMaintenancePageAppears, "Script Maintenance page appeared");

        boolean scriptAppearsInGrid = scriptMaintenanceGridPage().scriptExists(ScriptMaintenanceDatabaseConstants.PUB_TAG, ScriptMaintenanceDatabaseConstants.SCRIPT_NAME, ScriptMaintenanceDatabaseConstants.VERSION_DESCRIPTION, "Incomplete");
        Assertions.assertTrue(scriptAppearsInGrid, "Created script appears in grid");

        scriptMaintenanceGridPage().selectScript(ScriptMaintenanceDatabaseConstants.PUB_TAG, ScriptMaintenanceDatabaseConstants.SCRIPT_NAME, ScriptMaintenanceDatabaseConstants.VERSION_DESCRIPTION, "Incomplete");
        boolean editScriptRulesPageAppeared = scriptMaintenanceClamshellPage().goToEditScriptRules();
        Assertions.assertTrue(editScriptRulesPageAppeared, "Edit Script Rules page appeared");

        editScriptRulesConfirmationPage().clickOK();
        editScriptRulesPage().setScriptAction("Select Range");
        editScriptRulesPage().setStartMnemonic("TestSM");
        editScriptRulesPage().setEndMnemonic("TestEM");
        editScriptRulesPage().clickAddAtEndButton();
        String actualScriptRuleText = editScriptRulesPage().getScriptRuleText("1");
        Assertions.assertEquals(" Select Range TestSM to TestEM",actualScriptRuleText,"The Script Rule Text Matches what was expected");

        editScriptRulesPage().clickCancel();
        scriptMaintenanceGridPage().selectScript(ScriptMaintenanceDatabaseConstants.PUB_TAG, ScriptMaintenanceDatabaseConstants.SCRIPT_NAME, ScriptMaintenanceDatabaseConstants.VERSION_DESCRIPTION, "Incomplete");
        editScriptRulesPageAppeared = scriptMaintenanceClamshellPage().goToEditScriptRules();
        Assertions.assertTrue(editScriptRulesPageAppeared, "Edit Script Rules page appeared");

        editScriptRulesConfirmationPage().clickOK();
        boolean didScriptRuleStay = editScriptRulesPage().doesScriptRuleExistGivenText(" Select Range TestSM to TestEM");
        Assertions.assertFalse(didScriptRuleStay, "The Script Rule Didn't Save after clicking Cancel");
    }

    /**
     * STORY/BUGS - HALCYONST-3807 <br>
     * SUMMARY - This inserts two script rules and opens edit script rules. It then moves the one at index 2 up and then checks to ensure that both rules are in the proper place<br>
     * USER - Legal <br>
     */
    @Test
	@EDGE
    @LEGAL
    @LOG
    public void moveScriptUpLegalTest()
    {
        Connection uatConnection = BaseDatabaseUtils.connectToDatabaseUAT();
        ScriptMaintenanceDatabaseUtils.insertSelectRangeScriptRuleIntoGivenScript(uatConnection, ScriptMaintenanceDatabaseConstants.SCRIPT_ID, userName, "GoDownSM","GoDownEM", 1,3);
        ScriptMaintenanceDatabaseUtils.insertSelectRangeScriptRuleIntoGivenScript(uatConnection, ScriptMaintenanceDatabaseConstants.SCRIPT_ID, userName, "GoUpSM","GoUpEM", 2,3);
        BaseDatabaseUtils.disconnect(uatConnection);

        homePage().goToHomePage();
        loginPage().logIn();

        boolean scriptMaintenancePageAppears = toolsMenu().goToScriptMaintenance();
        Assertions.assertTrue(scriptMaintenancePageAppears, "Script Maintenance page appeared");

        boolean scriptAppearsInGrid = scriptMaintenanceGridPage().scriptExists(ScriptMaintenanceDatabaseConstants.PUB_TAG, ScriptMaintenanceDatabaseConstants.SCRIPT_NAME, ScriptMaintenanceDatabaseConstants.VERSION_DESCRIPTION, "Incomplete");
        Assertions.assertTrue(scriptAppearsInGrid, "Created script appears in grid");

        scriptMaintenanceGridPage().selectScript(ScriptMaintenanceDatabaseConstants.PUB_TAG, ScriptMaintenanceDatabaseConstants.SCRIPT_NAME, ScriptMaintenanceDatabaseConstants.VERSION_DESCRIPTION, "Incomplete");
        boolean editScriptRulesPageAppeared = scriptMaintenanceClamshellPage().goToEditScriptRules();
        Assertions.assertTrue(editScriptRulesPageAppeared, "Edit Script Rules page appeared");

        editScriptRulesConfirmationPage().clickOK();
        editScriptRulesPage().moveUpAtIndex("2");
        boolean firstScriptRuleTextIsValid = editScriptRulesPage().getScriptRuleText("1").equals(" Select Range GoUpSM to GoUpEM");
        boolean secondScriptRuleTextIsValid = editScriptRulesPage().getScriptRuleText("2").equals(" Select Range GoDownSM to GoDownEM");
        Assertions.assertAll(
                () -> Assertions.assertTrue(firstScriptRuleTextIsValid, "First Script Rule has the expected information"),
                () -> Assertions.assertTrue(secondScriptRuleTextIsValid, "Second Script Rule has the expected information")
        );
    }

    /**
     * STORY/BUGS - HALCYONST-3807 <br>
     * SUMMARY - This inserts two script rules and opens edit script rules. It then moves the one at index 1 down and then checks to ensure that both rules are in the proper place<br>
     * USER - Legal <br>
     */
    @Test
	@EDGE
    @LEGAL
    @LOG
    public void moveScriptDownLegalTest()
    {
        Connection uatConnection = BaseDatabaseUtils.connectToDatabaseUAT();
        ScriptMaintenanceDatabaseUtils.insertSelectRangeScriptRuleIntoGivenScript(uatConnection, ScriptMaintenanceDatabaseConstants.SCRIPT_ID, userName, "GoDownSM","GoDownEM", 1,3);
        ScriptMaintenanceDatabaseUtils.insertSelectRangeScriptRuleIntoGivenScript(uatConnection, ScriptMaintenanceDatabaseConstants.SCRIPT_ID, userName, "GoUpSM","GoUpEM", 2,3);
        BaseDatabaseUtils.disconnect(uatConnection);

        homePage().goToHomePage();
        loginPage().logIn();

        boolean scriptMaintenancePageAppears = toolsMenu().goToScriptMaintenance();
        Assertions.assertTrue(scriptMaintenancePageAppears, "Script Maintenance page appeared");

        boolean scriptAppearsInGrid = scriptMaintenanceGridPage().scriptExists(ScriptMaintenanceDatabaseConstants.PUB_TAG, ScriptMaintenanceDatabaseConstants.SCRIPT_NAME, ScriptMaintenanceDatabaseConstants.VERSION_DESCRIPTION, "Incomplete");
        Assertions.assertTrue(scriptAppearsInGrid, "Created script appears in grid");

        scriptMaintenanceGridPage().selectScript(ScriptMaintenanceDatabaseConstants.PUB_TAG, ScriptMaintenanceDatabaseConstants.SCRIPT_NAME, ScriptMaintenanceDatabaseConstants.VERSION_DESCRIPTION, "Incomplete");
        boolean editScriptRulesPageAppeared = scriptMaintenanceClamshellPage().goToEditScriptRules();
        Assertions.assertTrue(editScriptRulesPageAppeared, "Edit Script Rules page appeared");

        editScriptRulesConfirmationPage().clickOK();
        editScriptRulesPage().moveDownAtIndex("1");
        boolean firstScriptRuleTextIsValid = editScriptRulesPage().getScriptRuleText("1").equals(" Select Range GoUpSM to GoUpEM");
        boolean secondScriptRuleTextIsValid = editScriptRulesPage().getScriptRuleText("2").equals(" Select Range GoDownSM to GoDownEM");
        Assertions.assertAll(
                () -> Assertions.assertTrue(firstScriptRuleTextIsValid, "First Script Rule has the expected information"),
                () -> Assertions.assertTrue(secondScriptRuleTextIsValid, "Second Script Rule has the expected information")
        );
    }

    /**
     * STORY/BUGS - HALCYONST-7406, HALCYONST-3807 <br>
     * SUMMARY - This test start with inserting two script rules. It opens the edit window and adds an indent at index 2 and checks to see if the rule is given the correct spacing.
     * It then removes the indent and checks to see if it removes the spacing<br>
     * USER - Legal <br>
     */
    @Test
	@EDGE
    @LEGAL
    @LOG
    public void addAndRemoveSingleIndentLegalTest()
    {
        Connection uatConnection = BaseDatabaseUtils.connectToDatabaseUAT();
        ScriptMaintenanceDatabaseUtils.insertSelectRangeScriptRuleIntoGivenScript(uatConnection, ScriptMaintenanceDatabaseConstants.SCRIPT_ID, userName, "TestSM","TestEM", 1,3);
        ScriptMaintenanceDatabaseUtils.insertSelectRangeScriptRuleIntoGivenScript(uatConnection, ScriptMaintenanceDatabaseConstants.SCRIPT_ID, userName, "IndentSM","IndentEM", 2,3);
        BaseDatabaseUtils.disconnect(uatConnection);

        homePage().goToHomePage();
        loginPage().logIn();

        boolean scriptMaintenancePageAppears = toolsMenu().goToScriptMaintenance();
        Assertions.assertTrue(scriptMaintenancePageAppears, "Script Maintenance page appeared");

        boolean scriptAppearsInGrid = scriptMaintenanceGridPage().scriptExists(ScriptMaintenanceDatabaseConstants.PUB_TAG, ScriptMaintenanceDatabaseConstants.SCRIPT_NAME, ScriptMaintenanceDatabaseConstants.VERSION_DESCRIPTION, "Incomplete");
        Assertions.assertTrue(scriptAppearsInGrid, "Created script appears in grid");

        scriptMaintenanceGridPage().selectScript(ScriptMaintenanceDatabaseConstants.PUB_TAG, ScriptMaintenanceDatabaseConstants.SCRIPT_NAME, ScriptMaintenanceDatabaseConstants.VERSION_DESCRIPTION, "Incomplete");
        boolean editScriptRulesPageAppeared = scriptMaintenanceClamshellPage().goToEditScriptRules();
        Assertions.assertTrue(editScriptRulesPageAppeared, "Edit Script Rules page appeared");

        editScriptRulesConfirmationPage().clickOK();
        editScriptRulesPage().addIndentAtIndex("2");

        boolean firstIndexAfterIndentAddition = editScriptRulesPage().getScriptRuleText("1").equals(" Select Range TestSM to TestEM");
        boolean secondIndexAfterIndentAddition = editScriptRulesPage().getScriptRuleText("2").equals("     Select Range IndentSM to IndentEM");
        Assertions.assertAll(
                () -> Assertions.assertTrue(firstIndexAfterIndentAddition, "First Script Rule has the expected information"),
                () -> Assertions.assertTrue(secondIndexAfterIndentAddition, "Second Script Rule has the expected information with an indent")
        );

        editScriptRulesPage().removeIndentAtIndex("2");
        boolean firstIndexAfterIndentRemoval = editScriptRulesPage().getScriptRuleText("1").equals(" Select Range TestSM to TestEM");
        boolean secondIndexAfterIndentRemoval = editScriptRulesPage().getScriptRuleText("2").equals(" Select Range IndentSM to IndentEM");
        Assertions.assertAll(
                () -> Assertions.assertTrue(firstIndexAfterIndentRemoval, "First Script Rule has the expected information"),
                () -> Assertions.assertTrue(secondIndexAfterIndentRemoval, "Second Script Rule has the expected information without an indent")
        );
    }

    /**
     * STORY/BUGS - HALCYONST-3807 <br>
     * SUMMARY - This test inserts three rules and goes into edit script rules. Then it adds an indent to the third rule and deletes the first, this is
     * to ensure that the indent will stay when a rule is deleted. It then deletes the second rule and rechecks the indent to ensure that there isn't one since it will be the
     * only rule in the script<br>
     * USER - Legal <br>
     */
    @Test
	@EDGE
    @LEGAL
    @LOG
    public void deleteScriptRuleOnlyLegalTest()
    {
        Connection uatConnection = BaseDatabaseUtils.connectToDatabaseUAT();
        ScriptMaintenanceDatabaseUtils.insertSelectRangeScriptRuleIntoGivenScript(uatConnection, ScriptMaintenanceDatabaseConstants.SCRIPT_ID, userName, "TestSM","TestEM", 1,3);
        ScriptMaintenanceDatabaseUtils.insertSelectRangeScriptRuleIntoGivenScript(uatConnection, ScriptMaintenanceDatabaseConstants.SCRIPT_ID, userName, "TestSM2","TestEM2", 2,3);
        ScriptMaintenanceDatabaseUtils.insertSelectRangeScriptRuleIntoGivenScript(uatConnection, ScriptMaintenanceDatabaseConstants.SCRIPT_ID, userName, "TestSM3","TestEM3", 3,3);
        BaseDatabaseUtils.disconnect(uatConnection);

        homePage().goToHomePage();
        loginPage().logIn();

        boolean scriptMaintenancePageAppears = toolsMenu().goToScriptMaintenance();
        Assertions.assertTrue(scriptMaintenancePageAppears, "Script Maintenance page appeared");

        boolean scriptAppearsInGrid = scriptMaintenanceGridPage().scriptExists(ScriptMaintenanceDatabaseConstants.PUB_TAG, ScriptMaintenanceDatabaseConstants.SCRIPT_NAME, ScriptMaintenanceDatabaseConstants.VERSION_DESCRIPTION, "Incomplete");
        Assertions.assertTrue(scriptAppearsInGrid, "Created script appears in grid");

        scriptMaintenanceGridPage().selectScript(ScriptMaintenanceDatabaseConstants.PUB_TAG, ScriptMaintenanceDatabaseConstants.SCRIPT_NAME, ScriptMaintenanceDatabaseConstants.VERSION_DESCRIPTION, "Incomplete");
        boolean editScriptRulesPageAppeared = scriptMaintenanceClamshellPage().goToEditScriptRules();
        Assertions.assertTrue(editScriptRulesPageAppeared, "Edit Script Rules page appeared");

        editScriptRulesConfirmationPage().clickOK();
        editScriptRulesPage().addIndentAtIndex("3");
        editScriptRulesPage().deleteScriptRuleOnlyAtIndex("1");
        boolean firstIndexAfterIndentRemoval = editScriptRulesPage().getScriptRuleText("1").equals(" Select Range TestSM2 to TestEM2");
        boolean secondIndexAfterIndentRemoval = editScriptRulesPage().getScriptRuleText("2").equals("     Select Range TestSM3 to TestEM3");
        Assertions.assertAll(
                () -> Assertions.assertTrue(firstIndexAfterIndentRemoval, "First Script Rule has the expected information"),
                () -> Assertions.assertTrue(secondIndexAfterIndentRemoval, "Second Script Rule has the expected information")
        );

        editScriptRulesPage().deleteScriptRuleOnlyAtIndex("1");
        Assertions.assertEquals(" Select Range TestSM3 to TestEM3",editScriptRulesPage().getScriptRuleText("1"), "The delete successfully removed the intended script rule and put it's only child as the root");

    }

    /**
     * STORY/BUGS - HALCYONST-3807 <br>
     * SUMMARY - This test inserts four rules and opens edit script rules. It then adds indents at the indexes 2 and 4. It then deletes the index 1 and checks that it's child was deleted as well. Then it deletes index
     * 2 (which doesn't have any children) and enures that if you delete rule and children on a childless rule it functions similarly to the regular delete<br>
     * USER - Legal <br>
     */
    @Test
	@EDGE
    @LEGAL
    @LOG
    public void deleteScriptRuleAndChildrenLegalTest()
    {
        Connection uatConnection = BaseDatabaseUtils.connectToDatabaseUAT();
        ScriptMaintenanceDatabaseUtils.insertSelectRangeScriptRuleIntoGivenScript(uatConnection, ScriptMaintenanceDatabaseConstants.SCRIPT_ID, userName, "TestSM","TestEM", 1,3);
        ScriptMaintenanceDatabaseUtils.insertSelectRangeScriptRuleIntoGivenScript(uatConnection, ScriptMaintenanceDatabaseConstants.SCRIPT_ID, userName, "TestSM2","TestEM2", 2,3);
        ScriptMaintenanceDatabaseUtils.insertSelectRangeScriptRuleIntoGivenScript(uatConnection, ScriptMaintenanceDatabaseConstants.SCRIPT_ID, userName, "TestSM3","TestEM3", 3,3);
        ScriptMaintenanceDatabaseUtils.insertSelectRangeScriptRuleIntoGivenScript(uatConnection, ScriptMaintenanceDatabaseConstants.SCRIPT_ID, userName, "TestSM4","TestEM4", 4,3);
        BaseDatabaseUtils.disconnect(uatConnection);

        homePage().goToHomePage();
        loginPage().logIn();

        boolean scriptMaintenancePageAppears = toolsMenu().goToScriptMaintenance();
        Assertions.assertTrue(scriptMaintenancePageAppears, "Script Maintenance page appeared");

        boolean scriptAppearsInGrid = scriptMaintenanceGridPage().scriptExists(ScriptMaintenanceDatabaseConstants.PUB_TAG, ScriptMaintenanceDatabaseConstants.SCRIPT_NAME, ScriptMaintenanceDatabaseConstants.VERSION_DESCRIPTION, "Incomplete");
        Assertions.assertTrue(scriptAppearsInGrid, "Created script appears in grid");

        scriptMaintenanceGridPage().selectScript(ScriptMaintenanceDatabaseConstants.PUB_TAG, ScriptMaintenanceDatabaseConstants.SCRIPT_NAME, ScriptMaintenanceDatabaseConstants.VERSION_DESCRIPTION, "Incomplete");
        boolean editScriptRulesPageAppeared = scriptMaintenanceClamshellPage().goToEditScriptRules();
        Assertions.assertTrue(editScriptRulesPageAppeared, "Edit Script Rules page appeared");

        editScriptRulesConfirmationPage().clickOK();
        editScriptRulesPage().addIndentAtIndex("4");
        editScriptRulesPage().addIndentAtIndex("2");
        boolean firstIndexAfterAddIndent = editScriptRulesPage().getScriptRuleText("1").equals(" Select Range TestSM to TestEM");
        boolean secondIndexAfterAddIndent = editScriptRulesPage().getScriptRuleText("2").equals("     Select Range TestSM2 to TestEM2");
        boolean thirdIndexAfterAddIndent = editScriptRulesPage().getScriptRuleText("3").equals(" Select Range TestSM3 to TestEM3");
        boolean fourthIndexAfterAddIndent = editScriptRulesPage().getScriptRuleText("4").equals("     Select Range TestSM4 to TestEM4");
        Assertions.assertAll(
                () -> Assertions.assertTrue(firstIndexAfterAddIndent, "First Script Rule has the expected information"),
                () -> Assertions.assertTrue(secondIndexAfterAddIndent, "Second Script Rule has the expected information"),
                () -> Assertions.assertTrue(thirdIndexAfterAddIndent, "First Script Rule has the expected information"),
                () -> Assertions.assertTrue(fourthIndexAfterAddIndent, "Second Script Rule has the expected information")
        );

        editScriptRulesPage().deleteScriptRuleAndChildrenAtIndex("1");
        boolean firstIndexAfterDelete = editScriptRulesPage().getScriptRuleText("1").equals(" Select Range TestSM3 to TestEM3");
        boolean secondIndexAfterDelete = editScriptRulesPage().getScriptRuleText("2").equals("     Select Range TestSM4 to TestEM4");
        Assertions.assertAll(
                () -> Assertions.assertTrue(firstIndexAfterDelete, "First Script Rule has the expected information"),
                () -> Assertions.assertTrue(secondIndexAfterDelete, "Second Script Rule has the expected information")
        );

        editScriptRulesPage().deleteScriptRuleAndChildrenAtIndex("2");
        Assertions.assertEquals(" Select Range TestSM3 to TestEM3",editScriptRulesPage().getScriptRuleText("1"), "The delete successfully removed the intended script rule");
    }

    /**
     * STORY/BUGS - HALCYONST-3807 <br>
     * SUMMARY - This test inserts four rules and goes to edit script rules. It then adds indents to the rules at index 2 and 4. After that it clicks move up on index three and checks to see if they all change as expected. It then
     * does move down on index 1 and checks if they have the proper information again.<br>
     * USER - Legal <br>
     */
    @Test
	@EDGE
    @LEGAL
    @LOG
    public void moveScriptUpAndDownWithChildrenLegalTest()
    {
        Connection uatConnection = BaseDatabaseUtils.connectToDatabaseUAT();
        ScriptMaintenanceDatabaseUtils.insertSelectRangeScriptRuleIntoGivenScript(uatConnection, ScriptMaintenanceDatabaseConstants.SCRIPT_ID, userName, "TestSM","TestEM", 1,3);
        ScriptMaintenanceDatabaseUtils.insertSelectRangeScriptRuleIntoGivenScript(uatConnection, ScriptMaintenanceDatabaseConstants.SCRIPT_ID, userName, "TestSM2","TestEM2", 2,3);
        ScriptMaintenanceDatabaseUtils.insertSelectRangeScriptRuleIntoGivenScript(uatConnection, ScriptMaintenanceDatabaseConstants.SCRIPT_ID, userName, "TestSM3","TestEM3", 3,3);
        ScriptMaintenanceDatabaseUtils.insertSelectRangeScriptRuleIntoGivenScript(uatConnection, ScriptMaintenanceDatabaseConstants.SCRIPT_ID, userName, "TestSM4","TestEM4", 4,3);
        BaseDatabaseUtils.disconnect(uatConnection);

        homePage().goToHomePage();
        loginPage().logIn();

        boolean scriptMaintenancePageAppears = toolsMenu().goToScriptMaintenance();
        Assertions.assertTrue(scriptMaintenancePageAppears, "Script Maintenance page appeared");

        boolean scriptAppearsInGrid = scriptMaintenanceGridPage().scriptExists(ScriptMaintenanceDatabaseConstants.PUB_TAG, ScriptMaintenanceDatabaseConstants.SCRIPT_NAME, ScriptMaintenanceDatabaseConstants.VERSION_DESCRIPTION, "Incomplete");
        Assertions.assertTrue(scriptAppearsInGrid, "Created script appears in grid");

        scriptMaintenanceGridPage().selectScript(ScriptMaintenanceDatabaseConstants.PUB_TAG, ScriptMaintenanceDatabaseConstants.SCRIPT_NAME, ScriptMaintenanceDatabaseConstants.VERSION_DESCRIPTION, "Incomplete");
        boolean editScriptRulesPageAppeared = scriptMaintenanceClamshellPage().goToEditScriptRules();
        Assertions.assertTrue(editScriptRulesPageAppeared, "Edit Script Rules page appeared");

        editScriptRulesConfirmationPage().clickOK();
        editScriptRulesPage().addIndentAtIndex("4");
        editScriptRulesPage().addIndentAtIndex("2");
        editScriptRulesPage().moveDownAtIndex("1");
        boolean firstIndexAfterMoveDown = editScriptRulesPage().getScriptRuleText("1").equals(" Select Range TestSM3 to TestEM3");
        boolean secondIndexAfterMoveDown = editScriptRulesPage().getScriptRuleText("2").equals("     Select Range TestSM4 to TestEM4");
        boolean thirdIndexAfterMoveDown = editScriptRulesPage().getScriptRuleText("3").equals(" Select Range TestSM to TestEM");
        boolean fourthIndexAfterMoveDown = editScriptRulesPage().getScriptRuleText("4").equals("     Select Range TestSM2 to TestEM2");
        Assertions.assertAll(
                () -> Assertions.assertTrue(firstIndexAfterMoveDown, "First Script Rule has the expected information"),
                () -> Assertions.assertTrue(secondIndexAfterMoveDown, "Second Script Rule has the expected information"),
                () -> Assertions.assertTrue(thirdIndexAfterMoveDown, "First Script Rule has the expected information"),
                () -> Assertions.assertTrue(fourthIndexAfterMoveDown, "Second Script Rule has the expected information")
        );

        editScriptRulesPage().moveUpAtIndex("3");
        boolean firstIndexAfterMoveUp = editScriptRulesPage().getScriptRuleText("1").equals(" Select Range TestSM to TestEM");
        boolean secondIndexAfterMoveUp = editScriptRulesPage().getScriptRuleText("2").equals("     Select Range TestSM2 to TestEM2");
        boolean thirdIndexAfterMoveUp = editScriptRulesPage().getScriptRuleText("3").equals(" Select Range TestSM3 to TestEM3");
        boolean fourthIndexAfterMoveUp = editScriptRulesPage().getScriptRuleText("4").equals("     Select Range TestSM4 to TestEM4");
        Assertions.assertAll(
                () -> Assertions.assertTrue(firstIndexAfterMoveUp, "First Script Rule has the expected information"),
                () -> Assertions.assertTrue(secondIndexAfterMoveUp, "Second Script Rule has the expected information"),
                () -> Assertions.assertTrue(thirdIndexAfterMoveUp, "First Script Rule has the expected information"),
                () -> Assertions.assertTrue(fourthIndexAfterMoveUp, "Second Script Rule has the expected information")
        );
    }

}
