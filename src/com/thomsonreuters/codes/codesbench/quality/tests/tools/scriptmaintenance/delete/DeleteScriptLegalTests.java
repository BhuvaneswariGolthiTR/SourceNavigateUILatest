package com.thomsonreuters.codes.codesbench.quality.tests.tools.scriptmaintenance.delete;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.EDGE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.*;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.*;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.tools.ScriptMaintenance.ScriptMaintenanceDatabaseConstants;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.tools.ScriptMaintenance.ScriptMaintenanceDatabaseUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets.IOWA_DEVELOPMENT;

public class DeleteScriptLegalTests extends TestService
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
     * STORY/BUGS - HALCYONST-3811 <br>
     * SUMMARY - Cancels the deletion of a script <br>
     * USER - Legal <br>
     */
    @Test
	@EDGE
    @LEGAL
    @LOG
    public void cancelDeleteScriptLegalTest()
    {
        homePage().goToHomePage();
        loginPage().logIn();

        boolean scriptMaintenancePageAppears = toolsMenu().goToScriptMaintenance();
        Assertions.assertTrue(scriptMaintenancePageAppears, "Script Maintenance page appeared");

        boolean scriptAppearsInGrid = scriptMaintenanceGridPage().scriptExists(ScriptMaintenanceDatabaseConstants.PUB_TAG, ScriptMaintenanceDatabaseConstants.SCRIPT_NAME, ScriptMaintenanceDatabaseConstants.VERSION_DESCRIPTION, "Incomplete");
        Assertions.assertTrue(scriptAppearsInGrid, "Created script appears in grid");

        scriptMaintenanceGridPage().selectScript(ScriptMaintenanceDatabaseConstants.PUB_TAG, ScriptMaintenanceDatabaseConstants.SCRIPT_NAME, ScriptMaintenanceDatabaseConstants.VERSION_DESCRIPTION, "Incomplete");
        boolean deleteScriptConfirmationWindowAppeared = scriptMaintenanceClamshellPage().goToDeleteScript();
        Assertions.assertTrue(deleteScriptConfirmationWindowAppeared, "Delete Script Confirmation window appeared");

        boolean deleteScriptConfirmationQuestionAppeared = deleteScriptConfirmationPage().getConfirmationQuestion().equals("Are you sure you want to delete this script?");
        Assertions.assertTrue(deleteScriptConfirmationQuestionAppeared, "Delete Script Confirmation question appeared");

        boolean deleteScriptConfirmationPageDisappeared = deleteScriptConfirmationPage().clickCancel();
        Assertions.assertFalse(deleteScriptConfirmationPageDisappeared, "Delete Script Confirmation page disappeared");

        boolean scriptAppearsInGridAfterCancel = scriptMaintenanceGridPage().scriptExists(ScriptMaintenanceDatabaseConstants.PUB_TAG, ScriptMaintenanceDatabaseConstants.SCRIPT_NAME, ScriptMaintenanceDatabaseConstants.VERSION_DESCRIPTION, "Incomplete");
        Assertions.assertTrue(scriptAppearsInGridAfterCancel, "Created script appears in grid after clicking cancel");
    }

    /**
     * STORY/BUGS - HALCYONST-3811, HALCYONST-5754, HALCYONST-11847, HALCYONST-13133 <br>
     * SUMMARY - Deletes a script then checks to see if it is viewable after clicking the hide delete checkbox <br>
     * USER - Legal <br>
     */
    @Test
	@EDGE
    @LEGAL
    @LOG
    public void deleteScriptLegalTest()
    {
        userName = user().getUsername().contains("c") ? "u" + user().getUsername() : user().getUsername();

        homePage().goToHomePage();
        loginPage().logIn();

        boolean scriptMaintenancePageAppears = toolsMenu().goToScriptMaintenance();
        Assertions.assertTrue(scriptMaintenancePageAppears, "Script Maintenance page appeared");

        boolean scriptAppearsInGrid = scriptMaintenanceGridPage().scriptExists(ScriptMaintenanceDatabaseConstants.PUB_TAG, ScriptMaintenanceDatabaseConstants.SCRIPT_NAME, ScriptMaintenanceDatabaseConstants.VERSION_DESCRIPTION, "Incomplete");
        Assertions.assertTrue(scriptAppearsInGrid, "Created script appears in grid");

        scriptMaintenanceGridPage().selectScript(ScriptMaintenanceDatabaseConstants.PUB_TAG, ScriptMaintenanceDatabaseConstants.SCRIPT_NAME, ScriptMaintenanceDatabaseConstants.VERSION_DESCRIPTION, "Incomplete");
        boolean deleteScriptConfirmationWindowAppeared = scriptMaintenanceClamshellPage().goToDeleteScript();
        Assertions.assertTrue(deleteScriptConfirmationWindowAppeared, "Delete Script Confirmation window appeared");

        boolean deleteScriptConfirmationQuestionAppeared = deleteScriptConfirmationPage().getConfirmationQuestion().equals("Are you sure you want to delete this script?");
        Assertions.assertTrue(deleteScriptConfirmationQuestionAppeared, "Delete Script Confirmation question appeared");

        boolean deleteScriptConfirmationPageDisappeared = deleteScriptConfirmationPage().clickSubmit();
        Assertions.assertFalse(deleteScriptConfirmationPageDisappeared, "Delete Script Confirmation page disappeared");

        boolean scriptAppearsInGridAfterSubmit = scriptMaintenanceGridPage().scriptExists(ScriptMaintenanceDatabaseConstants.PUB_TAG, ScriptMaintenanceDatabaseConstants.SCRIPT_NAME, ScriptMaintenanceDatabaseConstants.VERSION_DESCRIPTION, "Incomplete");
        Assertions.assertFalse(scriptAppearsInGridAfterSubmit, "Created script appears in grid after clicking submit");

        scriptMaintenanceGridPage().clickHideDeletedCheckbox();
        scriptAppearsInGridAfterSubmit = scriptMaintenanceGridPage().scriptExists(ScriptMaintenanceDatabaseConstants.PUB_TAG, ScriptMaintenanceDatabaseConstants.SCRIPT_NAME, ScriptMaintenanceDatabaseConstants.VERSION_DESCRIPTION, "Delete Pending");
        Assertions.assertTrue(scriptAppearsInGridAfterSubmit, "Created script appears in grid after clicking submit");
    }
}
