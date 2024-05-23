package com.thomsonreuters.codes.codesbench.quality.tests.tools.scriptmaintenance.view;

import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.scriptmaintenance.view.ViewDescriptionContentSetsPageElements;
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
import static com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets.USCA_DEVELOPMENT;

public class ViewDescriptionContentSetsLegalTests extends TestService
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
        ScriptMaintenanceDatabaseUtils.insertScriptIntoOtherContentSets(uatConnection, ScriptMaintenanceDatabaseConstants.SCRIPT_ID,USCA_DEVELOPMENT.getCode());
        BaseDatabaseUtils.disconnect(uatConnection);
    }

    /**
     * STORY/BUGS - HALCYONST-5183, HALCYONST-3775 <br>
     * SUMMARY - Creates and Views the Description/Content Sets for a Script with multiple content sets assigned, then validates that the appropriate information appears. <br>
     * USER - Legal <br>
     */
    @Test
	@EDGE
    @LEGAL
    @LOG
    public void viewDescriptionContentSetsPrintAndCloseLegalTest()
    {
        homePage().goToHomePage();
        loginPage().logIn();

        boolean scriptMaintenancePageAppears = toolsMenu().goToScriptMaintenance();
        Assertions.assertTrue(scriptMaintenancePageAppears, "Script Maintenance page appeared");

        boolean scriptAppearsInGrid = scriptMaintenanceGridPage().scriptExists(ScriptMaintenanceDatabaseConstants.PUB_TAG, ScriptMaintenanceDatabaseConstants.SCRIPT_NAME, ScriptMaintenanceDatabaseConstants.VERSION_DESCRIPTION, "Incomplete");
        Assertions.assertTrue(scriptAppearsInGrid, "Created script appears in grid");

        String scriptId = scriptMaintenanceGridPage().getScriptIdOfScript(ScriptMaintenanceDatabaseConstants.PUB_TAG, ScriptMaintenanceDatabaseConstants.SCRIPT_NAME, ScriptMaintenanceDatabaseConstants.VERSION_DESCRIPTION, "Incomplete");
        scriptMaintenanceGridPage().selectScript(ScriptMaintenanceDatabaseConstants.PUB_TAG, ScriptMaintenanceDatabaseConstants.SCRIPT_NAME, ScriptMaintenanceDatabaseConstants.VERSION_DESCRIPTION, "Incomplete");
        boolean viewDescriptionContentSetsWindowAppeared = scriptMaintenanceClamshellPage().goToViewDescriptionContentSets();
        Assertions.assertTrue(viewDescriptionContentSetsWindowAppeared, "View Description Content Sets page appeared");

        boolean scriptIdMatches = viewDescriptionContentSetsPage().getScriptId().equals(scriptId);
        Assertions.assertTrue(scriptIdMatches, "Script Id matches");

        boolean scriptNameMatches = viewDescriptionContentSetsPage().getScriptName().equals(ScriptMaintenanceDatabaseConstants.SCRIPT_NAME);
        Assertions.assertTrue(scriptNameMatches, "Script Name matches");

        boolean versionDescriptionMatches = viewDescriptionContentSetsPage().getVersionDescription().equals(ScriptMaintenanceDatabaseConstants.VERSION_DESCRIPTION);
        Assertions.assertTrue(versionDescriptionMatches, "Version Description matches");

        boolean pubTagMatches = viewDescriptionContentSetsPage().getPubTag().equals(ScriptMaintenanceDatabaseConstants.PUB_TAG);
        Assertions.assertTrue(pubTagMatches, "Pub Tag matches");

        List<String> validContentSetsForScript = viewDescriptionContentSetsPage().getValidContentSetsForScript();
        boolean validContentSetsForScriptMatches = validContentSetsForScript.size() == 2 &&
                validContentSetsForScript.containsAll(Arrays.asList("Iowa (Development)", "USCA(Development)"));
        Assertions.assertTrue(validContentSetsForScriptMatches, "Valid Content Sets For Script matches");
        boolean printIsDisplayed = viewDescriptionContentSetsPage().doesElementExist(ViewDescriptionContentSetsPageElements.PRINT);
        Assertions.assertTrue(printIsDisplayed, "Print exists");

        boolean printIsEnabled = viewDescriptionContentSetsPage().isElementEnabled(ViewDescriptionContentSetsPageElements.PRINT);
        Assertions.assertTrue(printIsEnabled, "Print is enabled");

        boolean viewDescriptionContentSetsPageDisappeared = viewDescriptionContentSetsPage().clickClose();
        Assertions.assertFalse(viewDescriptionContentSetsPageDisappeared, "View Description Content Sets page disappeared");
    }
}
