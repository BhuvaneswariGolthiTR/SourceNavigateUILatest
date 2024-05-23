package com.thomsonreuters.codes.codesbench.quality.tests.tools.scriptmaintenance.select;

import com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.tools.ScriptMaintenanceContextMenuElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.home.HomePageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.scriptmaintenance.page.ScriptMaintenanceClamshellPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.scriptmaintenance.page.ScriptMaintenanceMetadataPageElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.EDGE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.*;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.*;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.tools.ScriptMaintenance.ScriptMaintenanceDatabaseConstants;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.tools.ScriptMaintenance.ScriptMaintenanceDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.util.List;

import static com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets.IOWA_DEVELOPMENT;

public class SelectScriptLegalTests extends TestService
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
     * STORY/BUGS - HALCYONST-5786, HALCYONST-5774 <br>
     * SUMMARY - Selects a script and views the metadata below the grid in a single content set <br>
     * USER - Legal <br>
     */
    @Test
	@EDGE
    @LEGAL
    @LOG
    public void selectAndViewScriptMetadataBelowGridLegalTest()
    {
        homePage().goToHomePage();
        loginPage().logIn();

        boolean scriptMaintenancePageAppears = toolsMenu().goToScriptMaintenance();
        Assertions.assertTrue(scriptMaintenancePageAppears, "Script Maintenance page appeared");

        boolean homePageStillExists = scriptMaintenanceGridPage().doesWindowExistByTitle(HomePageElements.HOME_PAGE_TITLE);
        Assertions.assertTrue(homePageStillExists, "Home page still appears after going to Script Maintenance");

        scriptMaintenanceGridPage().switchToScriptPage();
        boolean scriptMetadataBlockAppears = scriptMaintenanceMetadataPage().isElementDisplayed(ScriptMaintenanceMetadataPageElements.SCRIPT_METADATA_BLOCK);
        Assertions.assertFalse(scriptMetadataBlockAppears, "Script metadata block appeared");

        boolean scriptAppearsInGrid = scriptMaintenanceGridPage().scriptExists(ScriptMaintenanceDatabaseConstants.PUB_TAG, ScriptMaintenanceDatabaseConstants.SCRIPT_NAME, ScriptMaintenanceDatabaseConstants.VERSION_DESCRIPTION, "Incomplete");
        Assertions.assertTrue(scriptAppearsInGrid, "Created script appears in grid");

        scriptMaintenanceGridPage().selectScript(ScriptMaintenanceDatabaseConstants.PUB_TAG, ScriptMaintenanceDatabaseConstants.SCRIPT_NAME, ScriptMaintenanceDatabaseConstants.VERSION_DESCRIPTION, "Incomplete");
        scriptMaintenanceMetadataPage().waitForElement(ScriptMaintenanceMetadataPageElements.SCRIPT_METADATA_BLOCK);
        boolean scriptMetadataBlockAppearsAfterSelect = scriptMaintenanceMetadataPage().isElementDisplayed(ScriptMaintenanceMetadataPageElements.SCRIPT_METADATA_BLOCK);
        Assertions.assertTrue(scriptMetadataBlockAppearsAfterSelect, "Script metadata block appeared");

        List<String> selectedContentSets = scriptMaintenanceMetadataPage().getSelectedContentSets();
        boolean contentSetsMatch = selectedContentSets.contains("Iowa (Development)") && selectedContentSets.size() == 1;
        Assertions.assertTrue(contentSetsMatch, "Content Sets match");
        DateAndTimeUtils.takeNap(DateAndTimeUtils.FOUR_SECONDS);
        boolean isSaveDisabled = scriptMaintenanceGridPage().isElementDisabled(ScriptMaintenanceMetadataPageElements.SAVE_BUTTON);
        Assertions.assertTrue(isSaveDisabled, "The save button is disabled prior to making changes");

        scriptMaintenanceMetadataPage().setScriptName(ScriptMaintenanceDatabaseConstants.SCRIPT_NAME + " 2");
        scriptMaintenanceMetadataPage().setVersionDescription(ScriptMaintenanceDatabaseConstants.VERSION_DESCRIPTION + " 2");
        DateAndTimeUtils.takeNap(DateAndTimeUtils.FOUR_SECONDS);
        boolean isSaveEnabled = scriptMaintenanceGridPage().isElementEnabled(ScriptMaintenanceMetadataPageElements.SAVE_BUTTON);
        Assertions.assertTrue(isSaveEnabled, "The save button is enabled after making changes");
    }

    /**
     * STORY/BUGS - HALCYONST-5848 <br>
     * SUMMARY - Checks disabled clamshell menu options prior to selecting a script, and toggles and checks clamshell menus <br>
     * USER - Legal <br>
     */
    @Test
	@EDGE
    @LEGAL
    @LOG
    public void disabledClamshellMenuOptionsAndTogglingClamshellMenuLegalTest()
    {

        homePage().goToHomePage();
        loginPage().logIn();

        boolean scriptMaintenancePageAppears = toolsMenu().goToScriptMaintenance();
        Assertions.assertTrue(scriptMaintenancePageAppears, "Script Maintenance page appeared");

        scriptMaintenanceClamshellPage().expandEdit();
        boolean editScriptRulesIsDisabled = scriptMaintenanceClamshellPage().isElementDisabled(ScriptMaintenanceClamshellPageElements.EDIT_SCRIPT_RULES_SPAN);
        scriptMaintenanceClamshellPage().expandView();
        boolean editScriptRulesIsDisplayed = scriptMaintenanceClamshellPage().isElementDisplayed(ScriptMaintenanceClamshellPageElements.EDIT_SCRIPT_RULES_SPAN);
        boolean viewDescriptionContentSetsIsDisabled = scriptMaintenanceClamshellPage().isElementDisabled(ScriptMaintenanceClamshellPageElements.VIEW_DESCRIPTION_CONTENT_SETS_SPAN);
        boolean viewPrintScriptRulesIsDisabled = scriptMaintenanceClamshellPage().isElementDisabled(ScriptMaintenanceClamshellPageElements.VIEW_PRINT_SCRIPT_RULES_SPAN);
        boolean viewPrintListOfScriptIsEnabled = scriptMaintenanceClamshellPage().isElementEnabled(ScriptMaintenanceClamshellPageElements.VIEW_PRINT_LIST_OF_SCRIPT_BUTTON);
        scriptMaintenanceClamshellPage().expandCreate();
        boolean viewDescriptionContentSetsIsDisplayed = scriptMaintenanceClamshellPage().isElementDisplayed(ScriptMaintenanceClamshellPageElements.VIEW_DESCRIPTION_CONTENT_SETS_SPAN);
        boolean viewPrintScriptRulesIsDisplayed = scriptMaintenanceClamshellPage().isElementDisplayed(ScriptMaintenanceClamshellPageElements.VIEW_PRINT_SCRIPT_RULES_SPAN);
        boolean viewPrintListOfScriptIsDisplayed = scriptMaintenanceClamshellPage().isElementDisplayed(ScriptMaintenanceClamshellPageElements.VIEW_PRINT_LIST_OF_SCRIPT_BUTTON);
        boolean createScriptIsDisabled = scriptMaintenanceClamshellPage().isElementDisabled(ScriptMaintenanceClamshellPageElements.CREATE_SCRIPT_BUTTON);
        boolean createScriptVersionIsDisabled = scriptMaintenanceClamshellPage().isElementDisabled(ScriptMaintenanceClamshellPageElements.CREATE_SCRIPT_VERSION_SPAN);
        boolean createScriptVersion998IsDisabled = scriptMaintenanceClamshellPage().isElementDisabled(ScriptMaintenanceClamshellPageElements.CREATE_SCRIPT_VERSION_998_SPAN);
        scriptMaintenanceClamshellPage().expandCopy();
        boolean createScriptIsDisplayed = scriptMaintenanceClamshellPage().isElementDisplayed(ScriptMaintenanceClamshellPageElements.CREATE_SCRIPT_SPAN);
        boolean createScriptVersionIsDisplayed = scriptMaintenanceClamshellPage().isElementDisplayed(ScriptMaintenanceClamshellPageElements.CREATE_SCRIPT_VERSION_SPAN);
        boolean createScriptVersion998IsDisplayed = scriptMaintenanceClamshellPage().isElementDisplayed(ScriptMaintenanceClamshellPageElements.CREATE_SCRIPT_VERSION_998_SPAN);
        boolean copyScriptIsDisabled = scriptMaintenanceClamshellPage().isElementDisabled(ScriptMaintenanceClamshellPageElements.COPY_SCRIPT_SPAN);
        boolean copyScriptForVersionIsDisabled = scriptMaintenanceClamshellPage().isElementDisabled(ScriptMaintenanceClamshellPageElements.COPY_SCRIPT_FOR_VERSION_SPAN);
        scriptMaintenanceClamshellPage().expandDelete();
        boolean copyScriptIsDisplayed = scriptMaintenanceClamshellPage().isElementDisplayed(ScriptMaintenanceClamshellPageElements.COPY_SCRIPT_SPAN);
        boolean copyScriptForVersionIsDisplayed = scriptMaintenanceClamshellPage().isElementDisplayed(ScriptMaintenanceClamshellPageElements.COPY_SCRIPT_FOR_VERSION_SPAN);
        boolean deleteScriptIsDisabled = scriptMaintenanceClamshellPage().isElementDisabled(ScriptMaintenanceClamshellPageElements.DELETE_SCRIPT_SPAN);
        scriptMaintenanceClamshellPage().expandMisc();
        boolean deleteScriptIsDisplayed = scriptMaintenanceClamshellPage().isElementDisplayed(ScriptMaintenanceClamshellPageElements.DELETE_SCRIPT_SPAN);
        boolean changeDescriptionContentSetIsDisabled = scriptMaintenanceClamshellPage().isElementDisabled(ScriptMaintenanceClamshellPageElements.CHANGE_DESCRIPTION_CONTENT_SETS_SPAN);

        boolean scriptAppearsInGrid = scriptMaintenanceGridPage().scriptExists(ScriptMaintenanceDatabaseConstants.PUB_TAG, ScriptMaintenanceDatabaseConstants.SCRIPT_NAME, ScriptMaintenanceDatabaseConstants.VERSION_DESCRIPTION, "Incomplete");
        Assertions.assertTrue(scriptAppearsInGrid, "Created script appears in grid");

        scriptMaintenanceGridPage().selectScript(ScriptMaintenanceDatabaseConstants.PUB_TAG, ScriptMaintenanceDatabaseConstants.SCRIPT_NAME, ScriptMaintenanceDatabaseConstants.VERSION_DESCRIPTION, "Incomplete");
        scriptMaintenanceClamshellPage().expandEdit();
        boolean editScriptRulesIsDisabledAfterSelect = scriptMaintenanceClamshellPage().isElementDisabled(ScriptMaintenanceClamshellPageElements.EDIT_SCRIPT_RULES_BUTTON);
        scriptMaintenanceClamshellPage().expandView();
        boolean editScriptRulesIsDisplayedAfterSelect = scriptMaintenanceClamshellPage().isElementDisplayed(ScriptMaintenanceClamshellPageElements.EDIT_SCRIPT_RULES_BUTTON);
        boolean viewDescriptionContentSetsIsDisabledAfterSelect = scriptMaintenanceClamshellPage().isElementDisabled(ScriptMaintenanceClamshellPageElements.VIEW_DESCRIPTION_CONTENT_SETS_BUTTON);
        boolean viewPrintScriptRulesIsDisabledAfterSelect = scriptMaintenanceClamshellPage().isElementDisabled(ScriptMaintenanceClamshellPageElements.VIEW_PRINT_SCRIPT_RULES_BUTTON);
        boolean viewPrintListOfScriptIsDisabledAfterSelect = scriptMaintenanceClamshellPage().isElementEnabled(ScriptMaintenanceClamshellPageElements.VIEW_PRINT_LIST_OF_SCRIPT_BUTTON);
        scriptMaintenanceClamshellPage().expandCreate();
        boolean viewDescriptionContentSetsIsDisplayedAfterSelect = scriptMaintenanceClamshellPage().isElementDisplayed(ScriptMaintenanceClamshellPageElements.VIEW_DESCRIPTION_CONTENT_SETS_BUTTON);
        boolean viewPrintScriptRulesIsDisplayedAfterSelect = scriptMaintenanceClamshellPage().isElementDisplayed(ScriptMaintenanceClamshellPageElements.VIEW_PRINT_SCRIPT_RULES_BUTTON);
        boolean viewPrintListOfScriptIsDisplayedAfterSelect = scriptMaintenanceClamshellPage().isElementDisplayed(ScriptMaintenanceClamshellPageElements.VIEW_PRINT_LIST_OF_SCRIPT_BUTTON);
        boolean createScriptIsDisabledAfterSelect = scriptMaintenanceClamshellPage().isElementDisabled(ScriptMaintenanceClamshellPageElements.CREATE_SCRIPT_BUTTON);
        boolean createScriptVersionIsDisabledAfterSelect = scriptMaintenanceClamshellPage().isElementDisabled(ScriptMaintenanceClamshellPageElements.CREATE_SCRIPT_VERSION_BUTTON);
        boolean createScriptVersion998IsDisabledAfterSelect = scriptMaintenanceClamshellPage().isElementDisabled(ScriptMaintenanceClamshellPageElements.CREATE_SCRIPT_VERSION_998_BUTTON);
        scriptMaintenanceClamshellPage().expandCopy();
        boolean createScriptIsDisplayedAfterSelect = scriptMaintenanceClamshellPage().isElementDisplayed(ScriptMaintenanceClamshellPageElements.CREATE_SCRIPT_BUTTON);
        boolean createScriptVersionIsDisplayedAfterSelect = scriptMaintenanceClamshellPage().isElementDisplayed(ScriptMaintenanceClamshellPageElements.CREATE_SCRIPT_VERSION_BUTTON);
        boolean createScriptVersion998IsDisplayedAfterSelect = scriptMaintenanceClamshellPage().isElementDisplayed(ScriptMaintenanceClamshellPageElements.CREATE_SCRIPT_VERSION_998_BUTTON);
        boolean copyScriptIsDisabledAfterSelect = scriptMaintenanceClamshellPage().isElementDisabled(ScriptMaintenanceClamshellPageElements.COPY_SCRIPT_BUTTON);
        boolean copyScriptForVersionIsDisabledAfterSelect = scriptMaintenanceClamshellPage().isElementDisabled(ScriptMaintenanceClamshellPageElements.COPY_SCRIPT_FOR_VERSION_BUTTON);
        scriptMaintenanceClamshellPage().expandDelete();
        boolean copyScriptIsDisplayedAfterSelect = scriptMaintenanceClamshellPage().isElementDisplayed(ScriptMaintenanceClamshellPageElements.COPY_SCRIPT_BUTTON);
        boolean copyScriptForVersionIsDisplayedAfterSelect = scriptMaintenanceClamshellPage().isElementDisplayed(ScriptMaintenanceClamshellPageElements.COPY_SCRIPT_FOR_VERSION_BUTTON);
        boolean deleteScriptIsDisabledAfterSelect = scriptMaintenanceClamshellPage().isElementDisabled(ScriptMaintenanceClamshellPageElements.DELETE_SCRIPT_BUTTON);
        scriptMaintenanceClamshellPage().expandMisc();
        boolean deleteScriptIsDisplayedAfterSelect = scriptMaintenanceClamshellPage().isElementDisplayed(ScriptMaintenanceClamshellPageElements.DELETE_SCRIPT_BUTTON);
        boolean changeDescriptionContentSetIsDisabledAfterSelect = scriptMaintenanceClamshellPage().isElementDisabled(ScriptMaintenanceClamshellPageElements.CHANGE_DESCRIPTION_CONTENT_SETS_SPAN);

        Assertions.assertAll
        (
            //expand edit
            () -> Assertions.assertTrue(editScriptRulesIsDisabled, "Edit script rules is disabled before selection"),

            //expand view
            () -> Assertions.assertFalse(editScriptRulesIsDisplayed, "Edit script rules is displayed after expanding view before selection"),
            () -> Assertions.assertTrue(viewDescriptionContentSetsIsDisabled, "View description content sets is disabled before selection"),
            () -> Assertions.assertTrue(viewPrintScriptRulesIsDisabled, "View print script rules is disabled before selection"),
            () -> Assertions.assertTrue(viewPrintListOfScriptIsEnabled, "View print list of script is disabled before selection"),

            //expand create
            () -> Assertions.assertFalse(viewDescriptionContentSetsIsDisplayed, "View description content sets is displayed after expanding create before selection"),
            () -> Assertions.assertFalse(viewPrintScriptRulesIsDisplayed, "View print script rules is displayed after expanding create before selection"),
            () -> Assertions.assertFalse(viewPrintListOfScriptIsDisplayed, "View print list of script is displayed after expanding create before selection"),
            () -> Assertions.assertFalse(createScriptIsDisabled, "Create script is disabled before selection"),
            () -> Assertions.assertTrue(createScriptVersionIsDisabled, "Create script version is disabled before selection"),
            () -> Assertions.assertTrue(createScriptVersion998IsDisabled, "Create script version 998 is disabled before selection"),

            //expand copy
            () -> Assertions.assertFalse(createScriptIsDisplayed, "Create script is displayed after expanding copy before selection"),
            () -> Assertions.assertFalse(createScriptVersionIsDisplayed, "Create script version is displayed after expanding copy before selection"),
            () -> Assertions.assertFalse(createScriptVersion998IsDisplayed, "Create script version 998 is displayed after expanding copy before selection"),
            () -> Assertions.assertTrue(copyScriptIsDisabled, "Copy script is disabled before selection"),
            () -> Assertions.assertTrue(copyScriptForVersionIsDisabled, "Copy script for version is disabled before selection"),

            //expand delete
            () -> Assertions.assertFalse(copyScriptIsDisplayed, "Copy script is displayed after expanding delete before selection"),
            () -> Assertions.assertFalse(copyScriptForVersionIsDisplayed, "Copy script for version is displayed after expanding delete before selection"),
            () -> Assertions.assertTrue(deleteScriptIsDisabled, "Delete script is disabled before selection"),

            //expand misc
            () -> Assertions.assertFalse(deleteScriptIsDisplayed, "Delete script is displayed after expanding misc before selection"),
            () -> Assertions.assertTrue(changeDescriptionContentSetIsDisabled, "Change description content set is disabled before selection"),

            //create and select script
            //expand edit
            () -> Assertions.assertFalse(editScriptRulesIsDisabledAfterSelect, "Edit script rules is disabled after selection"),

            //expand view
            () -> Assertions.assertFalse(editScriptRulesIsDisplayedAfterSelect, "Edit script rules is displayed after expanding view after selection"),
            () -> Assertions.assertFalse(viewDescriptionContentSetsIsDisabledAfterSelect, "View description content sets is disabled after selection"),
            () -> Assertions.assertFalse(viewPrintScriptRulesIsDisabledAfterSelect, "View print script rules is disabled after selection"),
            () -> Assertions.assertTrue(viewPrintListOfScriptIsDisabledAfterSelect, "View print list of script is disabled after selection"),

            //expand create
            () -> Assertions.assertFalse(viewDescriptionContentSetsIsDisplayedAfterSelect, "View description content sets is displayed after expanding create after selection"),
            () -> Assertions.assertFalse(viewPrintScriptRulesIsDisplayedAfterSelect, "View print script rules is displayed after expanding create after selection"),
            () -> Assertions.assertFalse(viewPrintListOfScriptIsDisplayedAfterSelect, "View print list of script is displayed after expanding create after selection"),
            () -> Assertions.assertFalse(createScriptIsDisabledAfterSelect, "Create script is disabled after selection"),
            () -> Assertions.assertFalse(createScriptVersionIsDisabledAfterSelect, "Create script version is disabled after selection"),
            () -> Assertions.assertFalse(createScriptVersion998IsDisabledAfterSelect, "Create script version 998 is disabled after selection"),

            //expand copy
            () -> Assertions.assertFalse(createScriptIsDisplayedAfterSelect, "Create script is displayed after expanding copy after selection"),
            () -> Assertions.assertFalse(createScriptVersionIsDisplayedAfterSelect, "Create script version is displayed after expanding copy after selection"),
            () -> Assertions.assertFalse(createScriptVersion998IsDisplayedAfterSelect, "Create script version 998 is displayed after expanding copy after selection"),
            () -> Assertions.assertFalse(copyScriptIsDisabledAfterSelect, "Copy script is disabled after selection"),
            () -> Assertions.assertFalse(copyScriptForVersionIsDisabledAfterSelect, "Copy script for version is disabled after selection"),

            //expand delete
            () -> Assertions.assertFalse(copyScriptIsDisplayedAfterSelect, "Copy script is displayed after expanding delete after selection"),
            () -> Assertions.assertFalse(copyScriptForVersionIsDisplayedAfterSelect, "Copy script for version is displayed after expanding delete after selection"),
            () -> Assertions.assertFalse(deleteScriptIsDisabledAfterSelect, "Delete script is disabled after selection"),

            //expand misc
            () -> Assertions.assertFalse(deleteScriptIsDisplayedAfterSelect, "Delete script is displayed after expanding misc after selection"),
            () -> Assertions.assertTrue(changeDescriptionContentSetIsDisabledAfterSelect, "Change description content set is disabled after selection")
        );
    }

    /**
     * STORY/BUGS - HALCYONST-2324 <br>
     * SUMMARY - Checks disabled context menu options <br>
     * USER - Legal <br>
     */
    @Test
	@EDGE
    @LEGAL
    @LOG
    public void contextMenuOptionsLegalTest()
    {
        homePage().goToHomePage();
        loginPage().logIn();

        boolean scriptMaintenancePageAppears = toolsMenu().goToScriptMaintenance();
        Assertions.assertTrue(scriptMaintenancePageAppears, "Script Maintenance page appeared");

        boolean scriptAppearsInGrid = scriptMaintenanceGridPage().scriptExists(ScriptMaintenanceDatabaseConstants.PUB_TAG, ScriptMaintenanceDatabaseConstants.SCRIPT_NAME, ScriptMaintenanceDatabaseConstants.VERSION_DESCRIPTION, "Incomplete");
        Assertions.assertTrue(scriptAppearsInGrid, "Created script appears in grid");

        scriptMaintenanceGridPage().selectScript(ScriptMaintenanceDatabaseConstants.PUB_TAG, ScriptMaintenanceDatabaseConstants.SCRIPT_NAME, ScriptMaintenanceDatabaseConstants.VERSION_DESCRIPTION, "Incomplete");
        scriptMaintenanceGridPage().rightClickScript(ScriptMaintenanceDatabaseConstants.PUB_TAG, ScriptMaintenanceDatabaseConstants.SCRIPT_NAME, ScriptMaintenanceDatabaseConstants.VERSION_DESCRIPTION, "Incomplete");
        boolean copyIsEnabled = scriptMaintenanceContextMenu().isElementDisabled(ScriptMaintenanceContextMenuElements.copy);
        boolean copyWithHeadersIsEnabled = scriptMaintenanceContextMenu().isElementDisabled(ScriptMaintenanceContextMenuElements.copyWithHeaders);
        boolean pasteIsEnabled = scriptMaintenanceContextMenu().isElementDisabled(ScriptMaintenanceContextMenuElements.paste);
        scriptMaintenanceContextMenu().expandExport();
        boolean exportCsvExportIsEnabled = scriptMaintenanceContextMenu().isElementDisabled(ScriptMaintenanceContextMenuElements.exportCsvExport);
        boolean exportExcelXlsxExport = scriptMaintenanceContextMenu().isElementDisabled(ScriptMaintenanceContextMenuElements.exportExcelXlsxExport);

        Assertions.assertAll
        (
            () -> Assertions.assertFalse(copyIsEnabled, "copy is enabled"),
            () -> Assertions.assertFalse(copyWithHeadersIsEnabled, "copy with headers is enabled"),
            () -> Assertions.assertTrue(pasteIsEnabled, "paste is disabled"),

            () -> Assertions.assertFalse(exportCsvExportIsEnabled, "csv export is enabled"),
            () -> Assertions.assertFalse(exportExcelXlsxExport, "excel export .xlsx is enabled")
        );
    }

    /**
     * STORY/BUGS - HALCYONST-2324 <br>
     * SUMMARY - Checks the content set displayed in the top left corner and compares it to the content set displayed in the top right of the Home Page <br>
     * USER - Legal <br>
     */
    @Test
	@EDGE
    @LEGAL
    @LOG
    public void checkContentSetLegalTest()
    {
        homePage().goToHomePage();
        loginPage().logIn();

        String contentSetFromHomePage = homePage().getCurrentContentSetFromUpperRight();
        boolean scriptMaintenancePageAppears = toolsMenu().goToScriptMaintenance();
        Assertions.assertTrue(scriptMaintenancePageAppears, "Script Maintenance page appeared");

        String contentSetFromScript = scriptMaintenanceGridPage().getContentSetFromTopLeftCorner();
        Assertions.assertEquals(contentSetFromHomePage, contentSetFromScript, "The Content Set taken from the script UI doesn't match what is on the homepage");
    }
}
