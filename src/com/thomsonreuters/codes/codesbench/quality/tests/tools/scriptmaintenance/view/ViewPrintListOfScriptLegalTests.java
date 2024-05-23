package com.thomsonreuters.codes.codesbench.quality.tests.tools.scriptmaintenance.view;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.EDGE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.*;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.*;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.tools.ScriptMaintenance.ScriptMaintenanceDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.lists.ListUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.List;

public class ViewPrintListOfScriptLegalTests extends TestService
{

    /**
     * STORY/BUGS - HALCYONST 11835 <br>
     * SUMMARY -  Logs into the legal user and opens Script Maintenance (New) and opens the View clamshell menu. Then it clicks on View Print List Of Scripts and takes the
     * list of scripts presented and saves it into a String list, then the test queries the database and compares the strings to what they should be (It should the same list
     * of scripts on the main page)<br>
     * USER - Legal <br>
     */
    @Test
	@EDGE
    @LEGAL
    @LOG
    public void viewPrintListOfScriptsContentMatchMainPageGridLegalTest()
    {
        homePage().goToHomePage();
        loginPage().logIn();

        boolean scriptMaintenancePageAppears = toolsMenu().goToScriptMaintenance();
        Assertions.assertTrue(scriptMaintenancePageAppears, "Script Maintenance page appeared");

        boolean viewDescriptionContentSetsWindowAppeared = scriptMaintenanceClamshellPage().goToViewPrintListOfScript();
        Assertions.assertTrue(viewDescriptionContentSetsWindowAppeared, "It successfully went to print list of scripts");

        List<String> listOfScripts =  viewPrintListOfScriptPage().getListOfScripts();
        Connection uatConnection = BaseDatabaseUtils.connectToDatabaseUAT();
        List<String> doesListOfScriptsInUIMatchDatabaseScripts = ScriptMaintenanceDatabaseUtils.getListOfScriptsFromAContentSet(uatConnection, 106);
        BaseDatabaseUtils.disconnect(uatConnection); //Add a 999 check
        boolean areTheUIAndDBListsEqual = ListUtils.areListsEqual(doesListOfScriptsInUIMatchDatabaseScripts,listOfScripts);
        Assertions.assertTrue(areTheUIAndDBListsEqual, "List of Scripts from the UI matches what is in the Database");
    }
}
