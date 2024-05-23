package com.thomsonreuters.codes.codesbench.quality.tests.tools.scriptmaintenance.view;

import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.scriptmaintenance.view.ViewPrintScriptRulesPageElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.EDGE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.*;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.*;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.tools.ScriptMaintenance.ScriptMaintenanceDatabaseConstants;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.tools.ScriptMaintenance.ScriptMaintenanceDatabaseUtils;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.util.Arrays;
import java.util.List;

import static com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets.IOWA_DEVELOPMENT;

public class ViewPrintScriptRulesLegalTests extends TestService
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
        ScriptMaintenanceDatabaseUtils.insertScript(uatConnection, ScriptMaintenanceDatabaseConstants.SCRIPT_NAME, userName, ScriptMaintenanceDatabaseConstants.SCRIPT_NUM,ScriptMaintenanceDatabaseConstants.PUB_TAG, ScriptMaintenanceDatabaseConstants.VERSION_DESCRIPTION, ScriptMaintenanceDatabaseConstants.SCRIPT_ID, IOWA_DEVELOPMENT.getCode());
        ScriptMaintenanceDatabaseUtils.insertSelectRangeScriptRuleIntoGivenScript(uatConnection, ScriptMaintenanceDatabaseConstants.SCRIPT_ID, userName, "startM","endM", 1,3);
        BaseDatabaseUtils.disconnect(uatConnection);
    }

    /**
     * STORY/BUGS - HALCYONST-5663, HALCYONST-12752, HALCYONST-3779 <br>
     * SUMMARY - Creates and views the description/content sets for a script with multiple content sets assigned <br>
     * USER - Legal <br>
     */
    @Test
	@EDGE
    @LEGAL
    @LOG
    public void viewPrintScriptRulesPrintAndCloseLegalTest()
    {
        String FORMATTED_SCRIPT_ID = "1-0000-001";

        homePage().goToHomePage();
        loginPage().logIn();

        boolean scriptMaintenancePageAppears = toolsMenu().goToScriptMaintenance();
        Assertions.assertTrue(scriptMaintenancePageAppears, "Script Maintenance page appeared");

        boolean scriptAppearsInGrid = scriptMaintenanceGridPage().scriptExists(ScriptMaintenanceDatabaseConstants.PUB_TAG, ScriptMaintenanceDatabaseConstants.SCRIPT_NAME, ScriptMaintenanceDatabaseConstants.VERSION_DESCRIPTION, "Incomplete");
        Assertions.assertTrue(scriptAppearsInGrid, "Created script appears in grid");

        scriptMaintenanceGridPage().selectScript(ScriptMaintenanceDatabaseConstants.PUB_TAG, ScriptMaintenanceDatabaseConstants.SCRIPT_NAME, ScriptMaintenanceDatabaseConstants.VERSION_DESCRIPTION, "Incomplete");
        boolean viewPrintScriptRulesWindowAppeared = scriptMaintenanceClamshellPage().goToViewPrintScriptRules();
        Assertions.assertTrue(viewPrintScriptRulesWindowAppeared, "View Print Script Rules page appeared");

        boolean scriptIdMatches = viewPrintScriptRulesPage().getScriptId().equals(FORMATTED_SCRIPT_ID);
        Assertions.assertTrue(scriptIdMatches, "Script Id matches");

        boolean scriptNameMatches = viewPrintScriptRulesPage().getScriptName().equals(ScriptMaintenanceDatabaseConstants.SCRIPT_NAME);
        Assertions.assertTrue(scriptNameMatches, "Script Name matches");

        boolean versionDescriptionMatches = viewPrintScriptRulesPage().getVersionDescription().equals(ScriptMaintenanceDatabaseConstants.VERSION_DESCRIPTION);
        Assertions.assertTrue(versionDescriptionMatches, "Version Description matches");

        boolean pubTagMatches = viewPrintScriptRulesPage().getPubTag().equals(ScriptMaintenanceDatabaseConstants.PUB_TAG);
        Assertions.assertTrue(pubTagMatches, "Pub Tag matches");

        List<String> rulesWithIndexesForScript = viewPrintScriptRulesPage().getValidContentSetsForScript();
        boolean validContentSetsForScriptMatches = rulesWithIndexesForScript.size() == 2 &&
                rulesWithIndexesForScript.containsAll(Arrays.asList("1","Select Range startM to endM")); //If more rules are added then make sure to account for the index being a separate string
        Assertions.assertTrue(validContentSetsForScriptMatches, "Script Rule(s) match what was inserted");

        boolean printIsDisplayed = viewPrintScriptRulesPage().doesElementExist(ViewPrintScriptRulesPageElements.PRINT);
        Assertions.assertTrue(printIsDisplayed, "Print button exists");

        boolean viewDescriptionContentSetsPageDisappeared = viewPrintScriptRulesPage().clickClose();
        Assertions.assertFalse(viewDescriptionContentSetsPageDisappeared, "View Print Script Rules page disappeared");
    }
}
