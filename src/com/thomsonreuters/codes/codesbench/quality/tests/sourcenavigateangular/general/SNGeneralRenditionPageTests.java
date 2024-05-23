package com.thomsonreuters.codes.codesbench.quality.tests.sourcenavigateangular.general;

import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorToolbarPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.nodAngular.SubscribedCasesPageElementsAngular;
import com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.popups.IntegrationResultsPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.*;
import com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.popups.SourceNavigateAngularPopUpPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.workflowreportingsystem.workflowsearch.WorkflowSearchPageElements;
import com.thomsonreuters.codes.codesbench.quality.tests.sourcenavigateangular.assertions.SourceNavigateAngularAssertions;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.EDGE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.source.SourceDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.CommonDataMocking;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.source.SourceDataMockingNew;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.source.datapod.SourceDatapodObject;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.util.Arrays;
import java.util.List;

import static com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.popups.IntegrationResultsPageElements.*;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.SourceNavigateAngularContextMenuItemsPageElements.*;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.SourceNavigateAngularDeltaPageElements.*;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.SourceNavigateAngularFiltersAndSortsPageElements.*;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.SourceNavigateAngularLeftSidePaneElements.FIND_TEXT_FIELD_PATTERN;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.SourceNavigateAngularLockReportPageElements.CONTENT_SET;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.SourceNavigateAngularLockReportPageElements.LOCK_REPORT_RENDITION_STATUS;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.SourceNavigateAngularPageElements.*;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.SourceNavigateAngularRenditionPageElements.*;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.SourceNavigateAngularSectionPageElements.FIRST_RENDITION_ROW;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.SourceNavigateAngularSectionPageElements.*;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.SourceNavigateAngularTabsPageElements.*;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.SourceNavigateAngularTabsPageElements.RENDITION_TAB;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.popups.SourceNavigateAngularPopUpPageElements.*;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.tools.workflowreportingsystem.workflowsearch.WorkflowSearchPageElements.firstWorkflowIdXpath;
import static com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils.disconnect;
import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;

public class SNGeneralRenditionPageTests extends SourceNavigateAngularAssertions {
    SourceDatapodObject datapodObject;
    Connection connection;
    String renditionUuid;

    @BeforeEach
    public void mockData(TestInfo testInfo) {
        if (!(testInfo.getDisplayName().equals("verifyEffectiveDateUpdatedinAllGrids()")) &&
                !(testInfo.getDisplayName().equals("verifyRenditionClearFilterTest()")) &&
                !(testInfo.getDisplayName().equals("setEffectiveDateOnSingleLockedDocumentforRenditionsSectionsDeltas()")) &&
                (!(testInfo.getDisplayName().equals("verifyDifficultyLevelSingleItem()"))) &&
                !(testInfo.getDisplayName().equals("verifyEdit_Instruction_NotesTest()")) &&
                !(testInfo.getDisplayName().equals("cancelChangesOfEffectiveDateforRenditionsSectionsDeltas()")) &&
                !(testInfo.getDisplayName().equals("assignUserandDatetothedocuments()")) &&
                !(testInfo.getDisplayName().equals("verifyCancelDifficultyLevel()")) &&
                !(testInfo.getDisplayName().equals("verifyValidateViewValidationForWarningFlag()")) &&
                !(testInfo.getDisplayName().equals("verifyValidateViewValidationForErrorFlag()")) &&
                !(testInfo.getDisplayName().equals("verifyValidateViewValidationCiteLocationMultipleMatches()")) &&
                !(testInfo.getDisplayName().equals("verifyValidateViewValidationUpdatedLink()"))&&
                !(testInfo.getDisplayName().equals("verifyValidateViewValidationInvalidLink()"))&&
                !(testInfo.getDisplayName().equals("verifyRunCompareReportAndRunCompareAndMarkupReport()"))&&
                !(testInfo.getDisplayName().equals("verifyRunCompareReportAndRunCompareAndMarkupReportForMultipleRenditions()"))&&
                !(testInfo.getDisplayName().equals("verifyTaxTypeAdd()")) &&
                !(testInfo.getDisplayName().equals("AccessIntegrationResultsOfRenditionsAndDeltas()")) &&
                !(testInfo.getDisplayName().equals("verifyContextMenu()")))
        {
            datapodObject = SourceDataMockingNew.Iowa.Small.APV.insert();
            renditionUuid = datapodObject.getRenditions().get(0).getRenditionUUID();
            connection = CommonDataMocking.connectToDatabase(environmentTag);
        }

    }

    @AfterEach
    public void deleteMockedData() {
        if (datapodObject != null) {
            // sourceNavigateGridPage().unlockRenditionWithUuid(renditionUuid);
            datapodObject.delete();
        }
        disconnect(connection);
    }

    /**
     * User Story 723186: [HALCYONST-17173] [CMI]Rendition: Edit - Tax Type Add
     * Test case:
     * Access Source Navigate UI app > Click 'Renditions'
     * Right click rendition -> Edit -> Tax Type Add
     * Add 2 or more tax types
     * Verify :
     *      Add, Remove, Submit, Cancel buttons and Filter list text area.
     *      If added tax types is present under selected list column and the count is increased.
     *      If the count is reduced on the available tax types column after adding.
     * Remove added tax types
     * Verify :
     *      Add, Remove, Submit, Cancel buttons and Filter list text area.
     *      If removed tax types is not present under selected list column and the count is reduced.
     *      If the count is increased on the available tax types column after removing.
     */
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void verifyTaxTypeAdd(){
        String renditionUuid = "IBCFE9440AFBC11E3B34DCF0398D7238F";
        List<String> taxTypesToAdd_1 = Arrays.asList("BO", "PERS");
        int countAddedTaxTypes_1 = taxTypesToAdd_1.size();
        List<String> taxTypesToAdd_2 = Arrays.asList("TTRT1", "CORPLI");
        int countAddedTaxTypes_2 = taxTypesToAdd_2.size();
        int totalCount = countAddedTaxTypes_1 + countAddedTaxTypes_2;
        sourceNavigateAngularPage().goToSourcePage();
        loginPage().logIn();

        sourceNavigateAngularLeftSidePanePage().filterForColumnAndSelect("Tax Type Add");
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        sourceNavigateAngularPage().waitForElementExists(SourceNavigateAngularPageElements.TAX_TYPE);
        sourceNavigateAngularLeftSidePanePage().clickFindButtonOnLeftPane();
        sourceNavigateAngularLeftSidePanePage().setFindValue("Rendition UUID", renditionUuid);
        sourceNavigateAngularLeftSidePanePage().clickFindButton();

        //add two tax type and verify the above steps in description
        sourceNavigateAngularPage().rightClick(FIRST_RENDITION_ROW);
        sourceNavigateAngularPage().clickContextSubMenuItem(EDIT, TAX_TYPE_ADD);
        assertThatTaxTypeAddWindowIsDisplayed();
        int totalAvailableTaxType = Integer.parseInt(sourceNavigateAngularPage().getElementsText(SourceNavigateAngularPageElements.AVAILABLE_TAX_TYPE_NUM));
        assertThatTaxTypeAddWindowHasAllOptions(totalAvailableTaxType);
        sourceNavigateAngularPage().selectTaxType(taxTypesToAdd_1);
        assertAddButton(true);
        assertRemoveButton(false);
        sourceNavigateAngularPage().click(SourceNavigateAngularPageElements.ADD_BUTTON);
        assertAddButton(false);
        assertRemoveButton(false);
        assertValidationsAfterSelectingTaxTypes(totalAvailableTaxType,countAddedTaxTypes_1);
        sourceNavigateAngularPage().click(SourceNavigateAngularPageElements.Submit_Button);
        assertThatMessage(TOAST_MESSAGE,"The tax types were successfully updated");
        DateAndTimeUtils.takeNap(DateAndTimeUtils.ONE_SECOND);
        assertThatTaxTypeAddWindowIsDisappeared();
        sourceNavigateAngularPage().scrollToRight("900","(//div[@class='ag-body-horizontal-scroll-viewport'])[2]");
        sourceNavigateAngularPage().waitForElementExists(SourceNavigateAngularPageElements.TAX_TYPE);
        assertValidationsAfterAddingTaxTypes(taxTypesToAdd_1);
        assertThat(sourceNavigateAngularPage().getElementsText(SourceNavigateAngularPageElements.TAX_TYPE_COULMN_ID))
                .as("The list is not in ascending order")
                .isEqualTo(taxTypesToAdd_1.get(0) + ";" + taxTypesToAdd_1.get(1));

        //add two tax type and verify the above steps in description
        sourceNavigateAngularPage().rightClick(FIRST_RENDITION_ROW);
        sourceNavigateAngularPage().clickContextSubMenuItem(EDIT, TAX_TYPE_ADD);
        assertThatTaxTypeAddWindowIsDisplayed();
        sourceNavigateAngularPage().selectTaxType(taxTypesToAdd_2);
        assertAddButton(true);
        assertRemoveButton(false);
        sourceNavigateAngularPage().click(SourceNavigateAngularPageElements.ADD_BUTTON);
        assertAddButton(false);
        assertRemoveButton(false);
        assertValidationsAfterSelectingTaxTypes(totalAvailableTaxType,totalCount);
        sourceNavigateAngularPage().click(SourceNavigateAngularPageElements.Submit_Button);
        assertThatMessage(TOAST_MESSAGE,"The tax types were successfully updated");
        DateAndTimeUtils.takeNap(DateAndTimeUtils.ONE_SECOND);
        assertThatTaxTypeAddWindowIsDisappeared();
        sourceNavigateAngularPage().waitForElementExists(SourceNavigateAngularPageElements.TAX_TYPE);
        assertValidationsAfterAddingTaxTypes(taxTypesToAdd_1);
        assertThat(sourceNavigateAngularPage().getElementsText(SourceNavigateAngularPageElements.TAX_TYPE_COULMN_ID))
                .as("The list is not in ascending order")
                .isEqualTo("BO;CORPLI;PERS;TTRT1");

        //remove first two added tax type and verify the above steps in description
        sourceNavigateAngularPage().rightClick(FIRST_RENDITION_ROW);
        sourceNavigateAngularPage().clickContextSubMenuItem(EDIT, TAX_TYPE_ADD);
        assertThatTaxTypeAddWindowIsDisplayed();
        int totalAvailableTaxTypeBeforeRemoving = Integer.parseInt(sourceNavigateAngularPage().getElementsText(SourceNavigateAngularPageElements.AVAILABLE_TAX_TYPE_NUM));
        sourceNavigateAngularPage().deSelectTaxType(taxTypesToAdd_1);
        assertRemoveButton(true);
        assertAddButton(false);
        sourceNavigateAngularPage().click(SourceNavigateAngularPageElements.REMOVE_BUTTON);
        assertRemoveButton(false);
        assertAddButton(false);
        assertValidationsAfterDeSelectingTaxTypes(totalCount,countAddedTaxTypes_1,totalAvailableTaxTypeBeforeRemoving);
        sourceNavigateAngularPage().click(SourceNavigateAngularPageElements.Submit_Button);
        assertThatMessage(TOAST_MESSAGE,"The tax types were successfully updated");
        DateAndTimeUtils.takeNap(DateAndTimeUtils.ONE_SECOND);
        assertThatTaxTypeAddWindowIsDisappeared();
        sourceNavigateAngularPage().waitForElementExists(SourceNavigateAngularPageElements.TAX_TYPE);
        assertValidationsAfterRemovingTaxTypes(taxTypesToAdd_1);

        //remove the lastly two added tax type and verify the above steps in description
        sourceNavigateAngularPage().rightClick(FIRST_RENDITION_ROW);
        sourceNavigateAngularPage().clickContextSubMenuItem(EDIT, TAX_TYPE_ADD);
        assertThatTaxTypeAddWindowIsDisplayed();
        sourceNavigateAngularPage().deSelectTaxType(taxTypesToAdd_2);
        assertRemoveButton(true);
        assertAddButton(false);
        sourceNavigateAngularPage().click(SourceNavigateAngularPageElements.REMOVE_BUTTON);
        assertRemoveButton(false);
        assertAddButton(false);
        assertValidationsAfterDeSelectingTaxTypes(totalCount,totalCount,totalAvailableTaxTypeBeforeRemoving);
        sourceNavigateAngularPage().click(SourceNavigateAngularPageElements.Submit_Button);
        assertThatMessage(TOAST_MESSAGE,"The tax types were successfully updated");
        DateAndTimeUtils.takeNap(DateAndTimeUtils.ONE_SECOND);
        assertThatTaxTypeAddWindowIsDisappeared();
        sourceNavigateAngularPage().waitForElementExists(SourceNavigateAngularPageElements.TAX_TYPE);
        assertValidationsAfterRemovingTaxTypes(taxTypesToAdd_2);
        assertThat(sourceNavigateAngularPage().getElementsText(SourceNavigateAngularPageElements.TAX_TYPE_COULMN_ID))
                .as("The tax type add column is not empty")
                .isEqualTo("");
    }

    /**
     * User Story : 722047 Validate View Vlaidations
     * Test case:
     * Access Source Navigate UI app > Click 'Renditions'
     * From the Error Flag on the column header filter it to Warning / Cite Locate Multiple Matches and Select multiple renditions
     * Right click -> validate -> view validations
     * Select Multiple rows and Right CLick
     * Verify:
     *          If Clear Warning Flag is enabled
     *          If Clear Validation Links and Validate Links are disabled
     *Click Clear Warning Flag.
     *Verify:
     *          If the Confirmation window is appeared
     * Click Confirm and
     * Verify:
     *          Selected row is removed from the table.
     */
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void verifyValidateViewValidationForWarningFlag() {
        String warningFlag = "Warning";
        List<String> getSelectedRowText;
        List<String> viewValidationPageColumnExpectedFilterValues = Arrays.asList("Content Set", "Doc Uuid", "Doc Type", "Doc Number", "Class Number",
                "West ID", "Rendition Status", "Westlaw Load", "Validations", "Section Name", "Section Number", "Sub Section Number", "Target Node",
                "Target Code", "Target Sub-Node", "Validation Description");

        sourceNavigateAngularFiltersAndSortsPage().openMenuColumnHeaderForElement(SourceNavigateAngularFiltersAndSortsPageElements.errorFlag);
        sourceNavigateAngularFiltersAndSortsPage().openFilterTabColumnHeader();
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        sourceNavigateAngularFiltersAndSortsPage().clickCheckbox(SELECT_ALL);
        sourceNavigateAngularFiltersAndSortsPage().clickCheckbox(warningFlag);

        sourceNavigateAngularPage().clickMultipleRendtions(3, 6, RENDITION_ROW_PATTERN);
        sourceNavigateAngularPage().rightClick(String.format(RENDITION_ROW_PATTERN, "3"));
        sourceNavigateAngularPage().clickContextSubMenuItem(VALIDATE, VIEW_VALIDATIONS);

        assertThatViewValidationWindowIsDisplayed();
        assertThatViewValidationColumns(viewValidationPageColumnExpectedFilterValues);

        getSelectedRowText = sourceNavigateAngularPage().addSelectedRowData(1, 3,"(//div[@row-id='%s']/div[@col-id='targetLocationNode']/span)");
        sourceNavigateAngularPage().clickMultipleRendtions(1, 3, VIEW_VALIDATION_ROW_PATTERN);
        sourceNavigateAngularPage().rightClick(String.format(VIEW_VALIDATION_ROW_PATTERN, "1"));

        assertThatViewValidationContentMenuForWarningOrCiteLocatedFlag();

        sourceNavigateAngularPage().clickClearWarningFlag();

        assertThatIfConfirmationWindowAppeared();

        sourceNavigateAngularPage().clickCancel();

        assertThatIfConfirmationWindowDisappeared();

        sourceNavigateAngularPage().rightClick(String.format(VIEW_VALIDATION_ROW_PATTERN, "1"));
        sourceNavigateAngularPage().clickClearWarningFlag();
        sourceNavigateAngularPage().clickConfirm();
        sourceNavigateAngularPage().waitForPageLoaded();

        assertThatIfConfirmationWindowDisappeared();
        aasertThatHighlightedRowsRemoved(getSelectedRowText, "//div[@row-id='%s']/div[@col-id='targetLocationNode']/span[contains(text(), '%s')");
    }

    @Test
    @EDGE
    @LEGAL
    @LOG
    public void verifyValidateViewValidationCiteLocationMultipleMatches()
    {
        String citeLocateMultipleMatches  = "Cite Locate Multiple Matches";
        List<String> getSelectedRowText;
        List<String> viewValidationPageColumnExpectedFilterValues = Arrays.asList("Content Set", "Doc Uuid", "Doc Type", "Doc Number", "Class Number",
                "West ID", "Rendition Status", "Westlaw Load", "Validations", "Section Name", "Section Number", "Sub Section Number", "Target Node",
                "Target Code", "Target Sub-Node", "Validation Description");

        sourceNavigateAngularFiltersAndSortsPage().openMenuColumnHeaderForElement(SourceNavigateAngularFiltersAndSortsPageElements.errorFlag);
        sourceNavigateAngularFiltersAndSortsPage().openFilterTabColumnHeader();
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        sourceNavigateAngularFiltersAndSortsPage().clickCheckbox(SELECT_ALL);
        sourceNavigateAngularFiltersAndSortsPage().clickCheckbox(citeLocateMultipleMatches);

        sourceNavigateAngularPage().clickMultipleRendtions(1, 3, RENDITION_ROW_PATTERN);
        sourceNavigateAngularPage().rightClick(String.format(RENDITION_ROW_PATTERN, "1"));
        sourceNavigateAngularPage().clickContextSubMenuItem(VALIDATE, VIEW_VALIDATIONS);

        assertThatViewValidationWindowIsDisplayed();
        assertThatViewValidationColumns(viewValidationPageColumnExpectedFilterValues);
        getSelectedRowText = sourceNavigateAngularPage().addSelectedRowData(2, 4, "(//div[@row-id='%s']/div[@col-id='targetLocationNode']/span)");
        sourceNavigateAngularPage().clickMultipleRendtions(2, 4, VIEW_VALIDATION_ROW_PATTERN);
        sourceNavigateAngularPage().rightClick(String.format(VIEW_VALIDATION_ROW_PATTERN, "2"));

        assertThatViewValidationContentMenuForWarningOrCiteLocatedFlag();

        sourceNavigateAngularPage().clickClearWarningFlag();

        assertThatIfConfirmationWindowAppeared();

        sourceNavigateAngularPage().clickCancel();

        assertThatIfConfirmationWindowDisappeared();

        sourceNavigateAngularPage().rightClick(String.format(VIEW_VALIDATION_ROW_PATTERN, "2"));
        sourceNavigateAngularPage().clickClearWarningFlag();
        sourceNavigateAngularPage().clickConfirm();
        sourceNavigateAngularPage().waitForPageLoaded();

        assertThatIfConfirmationWindowDisappeared();
        aasertThatHighlightedRowsRemoved(getSelectedRowText,"//div[@row-id='%s']/div[@col-id='targetLocationNode']/span[contains(text(), '%s')");
    }

    /**
     * User Story : 722047 Validate View Vlaidations
     * Test case:
     * Access Source Navigate UI app > Click 'Renditions'
     * From the Error Flag on the column header filter it to Error and Select multiple renditions
     * Right click -> validate -> view validations
     * Select Multiple rows and Right CLick
     * Verify:
     *          If Clear Warning Flag ,Clear Validation Links and Validate Links are disabled
     */
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void verifyValidateViewValidationForErrorFlag()
    {
        String error = "Error";
        List<String> viewValidationPageColumnExpectedFilterValues = Arrays.asList("Content Set", "Doc Uuid", "Doc Type", "Doc Number", "Class Number",
                "West ID", "Rendition Status", "Westlaw Load", "Validations", "Section Name", "Section Number", "Sub Section Number", "Target Node",
                "Target Code", "Target Sub-Node", "Validation Description");

        sourceNavigateAngularFiltersAndSortsPage().openMenuColumnHeaderForElement(SourceNavigateAngularFiltersAndSortsPageElements.errorFlag);
        sourceNavigateAngularFiltersAndSortsPage().openFilterTabColumnHeader();
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        sourceNavigateAngularFiltersAndSortsPage().clickCheckbox(SELECT_ALL);
        sourceNavigateAngularFiltersAndSortsPage().clickCheckbox(error);

        sourceNavigateAngularPage().clickMultipleRendtions(0, 2, RENDITION_ROW_PATTERN);
        sourceNavigateAngularPage().rightClick(String.format(RENDITION_ROW_PATTERN, "0"));
        sourceNavigateAngularPage().clickContextSubMenuItem(VALIDATE, VIEW_VALIDATIONS);

        assertThatViewValidationWindowIsDisplayed();
        assertThatViewValidationColumns(viewValidationPageColumnExpectedFilterValues);

        sourceNavigateAngularPage().clickMultipleRendtions(1, 3, VIEW_VALIDATION_ROW_PATTERN);
        sourceNavigateAngularPage().rightClick(String.format(VIEW_VALIDATION_ROW_PATTERN, "1"));

        assertThatViewValidationContentMenuForErrorFlag();
    }

    /**
     * User Story : 722047 Validate View Vlaidations
     * Test case:
     * Access Source Navigate UI app > Click 'Renditions'
     * From the Error Flag on the column header filter it to Updated Link / Invalid Link and Select multiple renditions
     * Right click -> validate -> view validations
     * Select Multiple rows and Right CLick
     * Verify:
     *          If Clear Warning Flag is disabled
     *          If Clear Validation Links and Validate Links are enabled
     *Click Clear Validation Flag.
     *Verify:
     *          If the Confirmation window is appeared
     * Click Confirm and
     * Verify:
     *          Selected row is removed from the table.
     * Select one of the rendition and Right Click -> Validate Link
     * Verify:
     *          If the workflow has started and finished
     */
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void verifyValidateViewValidationUpdatedLink()
    {
        String updatedLinks  = "Updated Link";
        List<String> getSelectedRowText;
        List<String> viewValidationPageColumnExpectedFilterValues = Arrays.asList("Content Set", "Doc Uuid", "Doc Type", "Doc Number", "Class Number",
                "West ID", "Rendition Status", "Westlaw Load", "Validations", "Section Name", "Section Number", "Sub Section Number", "Target Node",
                "Target Code", "Target Sub-Node", "Validation Description");

        sourceNavigateAngularFiltersAndSortsPage().openMenuColumnHeaderForElement(SourceNavigateAngularFiltersAndSortsPageElements.errorFlag);
        sourceNavigateAngularFiltersAndSortsPage().openFilterTabColumnHeader();
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        sourceNavigateAngularFiltersAndSortsPage().clickCheckbox(SELECT_ALL);
        sourceNavigateAngularFiltersAndSortsPage().clickCheckbox(updatedLinks);

        sourceNavigateAngularPage().clickMultipleRendtions(4, 12, RENDITION_ROW_PATTERN);
        sourceNavigateAngularPage().rightClick(String.format(RENDITION_ROW_PATTERN, "4"));
        sourceNavigateAngularPage().clickContextSubMenuItem(VALIDATE, VIEW_VALIDATIONS);

        assertThatViewValidationWindowIsDisplayed();
        assertThatViewValidationColumns(viewValidationPageColumnExpectedFilterValues);
        sourceNavigateAngularPage().scrollToRight("-1500","(//div[@class='ag-body-horizontal-scroll-viewport'])[7]");
        getSelectedRowText = sourceNavigateAngularPage().addSelectedRowData(0, 1, "(//div[@row-id='%s']//div[@col-id='documentUuid'])");
        sourceNavigateAngularPage().clickMultipleRendtions(0, 1, VIEW_VALIDATION_ROW_PATTERN);
        sourceNavigateAngularPage().rightClick(String.format(VIEW_VALIDATION_ROW_PATTERN, "1"));

        assertThatViewValidationContentMenuForUpdatedOrInvalidFlag();

        sourceNavigateAngularPage().clickClearValidationFlag();

        assertThatIfConfirmationWindowAppeared();

        sourceNavigateAngularPage().clickCancel();

        assertThatIfConfirmationWindowDisappeared();

        sourceNavigateAngularPage().rightClick(String.format(VIEW_VALIDATION_ROW_PATTERN, "1"));
        sourceNavigateAngularPage().clickClearValidationFlag();
        sourceNavigateAngularPage().clickConfirm();
        sourceNavigateAngularPage().waitForPageLoaded();

        assertThatIfConfirmationWindowDisappeared();
        aasertThatHighlightedRowsRemoved(getSelectedRowText, "//div[@row-id='%s']/div[@col-id='documentUuid' and contains(text(), '%s')]");

        String Uuid = sourceNavigateAngularPage().getElementsText("(//div[@row-id='2']/div[@col-id='documentUuid'])");
        sourceNavigateAngularPage().rightClick(String.format(VIEW_VALIDATION_ROW_PATTERN, "2"));

        sourceNavigateAngularPage().clickValidateLinks();
        sourceNavigateAngularPage().clickConfirm();
        DateAndTimeUtils.takeNap(DateAndTimeUtils.THREE_SECONDS);

        assertToastMessageForValidateLinks();

        DateAndTimeUtils.takeNap(DateAndTimeUtils.THREE_SECONDS);
        sourceNavigateAngularPage().click(VIEW_VALIDATION_CLOSE_BTN);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);

        boolean workflowSearchPageAppears = sourceNavigateAngularPage().goToWorkflowReportingSystem();

        Assertions.assertTrue(workflowSearchPageAppears, "The Workflow Search page does not appear when it should");

        boolean workflowFinished = workflowSearchPage().filterWorkflowAndVerifyStatus("CwbBermudaLinks", "", "Validate Links", "");

        String workflowId = workflowSearchPage().getWorkflowIdOfFirstWorkflow();
        String workflowUserName = workflowSearchPage().getFirstWorkflowUserName();

        Assertions.assertTrue(workflowFinished, String.format("The workflow %s does not have finished status", workflowId));

        workflowSearchPage().closeWorkflowSearchPage();
        workflowSearchPage().switchToWindow(PAGE_TITLE);
        sourceNavigateAngularLeftSidePanePage().clickFindButtonOnLeftPane();
        sourceNavigateAngularLeftSidePanePage().setFindValue("Document UUID", Uuid);
        sourceNavigateAngularLeftSidePanePage().clickFindButton();
        sourceNavigateAngularPage().click(FIRST_RENDITION_ROW);
        sourceNavigateAngularPage().rightClickRenditions();
        sourceNavigateAngularPage().clickContextSubMenuItem(VIEW, RENDITION_BASELINE);

        assertTheRenditionBaseline(workflowUserName);
    }

    @Test
    @EDGE
    @LEGAL
    @LOG
    public void verifyValidateViewValidationInvalidLink()
    {
        String invalidLink  = "Invalid Link";
        List<String> getSelectedRowText;
        List<String> viewValidationPageColumnExpectedFilterValues = Arrays.asList("Content Set", "Doc Uuid", "Doc Type", "Doc Number", "Class Number",
                "West ID", "Rendition Status", "Westlaw Load", "Validations", "Section Name", "Section Number", "Sub Section Number", "Target Node",
                "Target Code", "Target Sub-Node", "Validation Description");

        sourceNavigateAngularFiltersAndSortsPage().openMenuColumnHeaderForElement(SourceNavigateAngularFiltersAndSortsPageElements.errorFlag);
        sourceNavigateAngularFiltersAndSortsPage().openFilterTabColumnHeader();
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        sourceNavigateAngularFiltersAndSortsPage().clickCheckbox(SELECT_ALL);
        sourceNavigateAngularFiltersAndSortsPage().clickCheckbox(invalidLink);

        sourceNavigateAngularPage().clickMultipleRendtions(1, 10, RENDITION_ROW_PATTERN);
        sourceNavigateAngularPage().rightClick(String.format(RENDITION_ROW_PATTERN, "1"));
        sourceNavigateAngularPage().clickContextSubMenuItem(VALIDATE, VIEW_VALIDATIONS);

        assertThatViewValidationWindowIsDisplayed();
        assertThatViewValidationColumns(viewValidationPageColumnExpectedFilterValues);
        sourceNavigateAngularPage().scrollToRight("-1600","(//div[@class='ag-body-horizontal-scroll-viewport'])[7]");
        getSelectedRowText = sourceNavigateAngularPage().addSelectedRowData(0, 1, "(//div[@row-id='%s']/div[@col-id='documentUuid'])");
        sourceNavigateAngularPage().clickMultipleRendtions(0, 1, VIEW_VALIDATION_ROW_PATTERN);
        sourceNavigateAngularPage().rightClick(String.format(VIEW_VALIDATION_ROW_PATTERN, "1"));

        assertThatViewValidationContentMenuForUpdatedOrInvalidFlag();

        sourceNavigateAngularPage().clickClearValidationFlag();

        assertThatIfConfirmationWindowAppeared();

        sourceNavigateAngularPage().clickCancel();

        assertThatIfConfirmationWindowDisappeared();

        sourceNavigateAngularPage().rightClick(String.format(VIEW_VALIDATION_ROW_PATTERN, "1"));
        sourceNavigateAngularPage().clickClearValidationFlag();
        sourceNavigateAngularPage().clickConfirm();
        sourceNavigateAngularPage().waitForPageLoaded();

        assertThatIfConfirmationWindowDisappeared();
        aasertThatHighlightedRowsRemoved(getSelectedRowText, "(//div[@row-id='%s']/div[@col-id='documentUuid' and contains(text(), '%s')]");

        String Uuid = sourceNavigateAngularPage().getElementsText("(//div[@row-id='2']/div[@col-id='documentUuid'])");
        sourceNavigateAngularPage().rightClick(String.format(VIEW_VALIDATION_ROW_PATTERN, "2"));

        sourceNavigateAngularPage().clickValidateLinks();
        sourceNavigateAngularPage().clickConfirm();
        DateAndTimeUtils.takeNap(DateAndTimeUtils.THREE_SECONDS);

        assertToastMessageForValidateLinks();

        DateAndTimeUtils.takeNap(DateAndTimeUtils.THREE_SECONDS);
        sourceNavigateAngularPage().click(VIEW_VALIDATION_CLOSE_BTN);

        boolean workflowSearchPageAppears = sourceNavigateAngularPage().goToWorkflowReportingSystem();

        Assertions.assertTrue(workflowSearchPageAppears, "The Workflow Search page does not appear when it should");

        boolean workflowFinished = workflowSearchPage().filterWorkflowAndVerifyStatus("CwbBermudaLinks", "", "Validate Links", "");

        String workflowId = workflowSearchPage().getWorkflowIdOfFirstWorkflow();
        String workflowUserName = workflowSearchPage().getFirstWorkflowUserName();

        Assertions.assertTrue(workflowFinished, String.format("The workflow %s does not have finished status", workflowId));

        workflowSearchPage().closeWorkflowSearchPage();
        workflowSearchPage().switchToWindow(PAGE_TITLE);
        sourceNavigateAngularLeftSidePanePage().clickFindButtonOnLeftPane();
        sourceNavigateAngularLeftSidePanePage().setFindValue("Document UUID", Uuid);
        sourceNavigateAngularLeftSidePanePage().clickFindButton();
        sourceNavigateAngularPage().click(FIRST_RENDITION_ROW);
        sourceNavigateAngularPage().rightClickRenditions();
        sourceNavigateAngularPage().clickContextSubMenuItem(VIEW, RENDITION_BASELINE);

        assertTheRenditionBaseline(workflowUserName);
    }

    /*
     *   Story# Bug 722943: [HALCYONST-16810] Cant unlock own lock
     *  Test case:
     *   Access Source Navigate UI app > Click 'Renditions'
     *  Lock the rendition
     *   Right-click the Rendition-> Click Rendition Properties on the context menu-> Refresh the page -> Locate the rendition in the grid
     *       Verify that the rendition has a lock icon in the Lock column.
     *   Click Lock Report on the top of the Source Navigate UI
     *       Verify that	the Lock Report opens
     *   Locate the locked Rendition in the Lock Report (by the Lock Date and by the Locked By columns) -> Right-click the row
     *       Verify that	1)there are three unlocking options under the line on the context menu: Unlock, Force Unlock, Transfer Lock
	   *                 2)Unlock option is active
	   *                 3)Force Unlock and Transfer Lock options are disabled
     *   Click Unlock on the context menu
     *       Verify that	1)the Lock Report grid refreshes
	   *                 2)The rendition is removed from the Lock Report
     *   Click Renditions -> locate the Rendition previously selected for locking
     *       Verify that the rendition does not have any lock icon in the Lock column
     */
    @Test
    @Disabled
    @EDGE
    @LEGAL
    @LOG
    public void verifyUnableToUnlockSelfLockedDoc() {
        //Find a rendition that is NOT currently locked
        sourceNavigateAngularLeftSidePanePage().clickFindButtonOnLeftPane();
        sourceNavigateAngularLeftSidePanePage().setFindValue("Rendition UUID", renditionUuid);
        sourceNavigateAngularLeftSidePanePage().clickFindButton();
        sourceNavigateAngularPage().waitForElementExists(format(TOTAL_RENDITIONS_NUMBER, "1"));

        //Lock the rendition from UI
        sourceNavigateAngularPage().click(FIRST_RENDITION_ROW);
        sourceNavigateAngularPage().rightClickRenditions();
        sourceNavigateAngularPage().clickContextSubMenuItem(EDIT, RENDITION);
        sourceNavigateAngularPage().switchToWindow(EditorPageElements.PAGE_TITLE);
        editorPage().waitForPageLoaded();
        editorPage().waitForElementGone(EditorPageElements.COMMAND_IN_PROGRESS);
        editorPage().waitForElement(EditorToolbarPageElements.CLOSE_DOC);
        editorPage().closeCurrentWindowIgnoreDialogue();

        //Refresh the grid
        editorPage().switchToWindow(SourceNavigateAngularPageElements.PAGE_TITLE);
        sourceNavigateAngularPage().clickRefreshTableData();
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);

        //Verify the lock icon for the rendition
        assertThatRenditionDisplaysLockIcon(true);

        //Click on Lock Report
        sourceNavigateAngularTabsPage().clickLockReportTab();
        sourceNavigateAngularTabsPage().waitForPageLoaded();

        //Verify that url points to lock report page
        assertThatLockedReportUrl();

        //sourceNavigateAngularLockReportPage().openMenuColumnHeader("lockedDocument");
        sourceNavigateAngularLockReportPage().clickOnColumnHeaderFilterButton("lockedDocument");
        sourceNavigateAngularLockReportPage().enterInputInFilterInputBox("2021 2RG Chapter 2");//2020 2RG Chapter 2
        sourceNavigateAngularLockReportPage().clickOnColumnHeaderFilterButton("lockedBy");
        sourceNavigateAngularLockReportPage().enterInputInFilterInputBox("TCBA");//2020 2RG Chapter 2

        assertThatRenditionIsLocked(true);

        //Right click on the listed rendition and verify the options
        sourceNavigateAngularLockReportPage().rightClickFirstLockedRendition();
        assertThatLockedRenditionContextMenuItems();
        assertThatLockedRendtionContextMenuState();

        //Click on Unlock
        sourceNavigateAngularLockReportPage().clickUnlock_ForceUnlock_TransferLock("Unlock");

        //Assert that the grid does not show the record any more
        DateAndTimeUtils.takeNap(DateAndTimeUtils.FIVE_SECONDS);
        assertThatRenditionIsLocked(false);

        //Click on Renditions tab
        sourceNavigateAngularTabsPage().click(RENDITION_TAB);

        //Refresh the grid
        editorPage().switchToWindow(SourceNavigateAngularPageElements.PAGE_TITLE);
        sourceNavigateAngularPage().clickRefreshTableData();

        //Find the rendition and verify that is NOT currently locked
        sourceNavigateAngularLeftSidePanePage().clickFindButtonOnLeftPane();
        sourceNavigateAngularLeftSidePanePage().setFindValue("Rendition UUID", renditionUuid);
        sourceNavigateAngularLeftSidePanePage().clickFindButton();
        sourceNavigateAngularPage().waitForElementExists(format(TOTAL_RENDITIONS_NUMBER, "1"));

        //Verify the lock icon for the rendition
        assertThatRenditionDisplaysLockIcon(false);
    }

    /**
     * May 2023:
     * User Story 723620: Set backdrop of modals to static
     * Access Source Navigate UI app > Click Renditions > Right-click any Rendition > Click Create Bookmark in the context menu >
     * Click somewhere outside the modal window
     * VERIFY:The modal window is not closed
     */
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void verifyCreateBookMarkModalWindow() {
        String renditionUuid = "I00022980487611E4B310D2D0CB231E2D";

        sourceNavigateAngularPage().goToSourcePage();
        loginPage().logIn();

        sourceNavigateAngularLeftSidePanePage().clickFindButtonOnLeftPane();
        sourceNavigateAngularLeftSidePanePage().setFindValue("Rendition UUID", renditionUuid);
        sourceNavigateAngularLeftSidePanePage().clickFindButton();

        String originalWindow = driver().getWindowHandle();
        sourceNavigateAngularPage().rightClick(FIRST_RENDITION_ROW);
        sourceNavigateAngularPage().clickContextMenuItem(CREATE_BOOKMARK);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TEN_SECONDS);

        //Verify that the Create Bookmark window gets opened in a new tab with title containing Create Bookmark
        assertThatCreateBookMarkWindowIsDisplayed(originalWindow);
        sourceNavigateAngularPage().closeCurrentWindowIgnoreDialogue();

        //Switch back to original window handle to execute the after each successfully
        driver().switchTo().window(originalWindow);
    }

    /**
     * May 2023:
     * User Story Bug 724324: UIs do not close if open them and then return to the previous page
     * Access Source Navigate UI app > Click 'Renditions' > Select any not locked APV/PREP rendition > Right-click the rendition > Click Edit  Effective Date
     * VERIFY:
     * The "Effective Date" UI opens
     * Click "Go back" button in the browser OR use "Alt + Left arrow" key combination
     * VERIFY:
     * The "Effective Date" UI closes
     * The previous page is displayed
     */
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void verifyUIDoNotCloseReturnToPreviousPage() {
        //Find a rendition that is NOT currently locked
        sourceNavigateAngularLeftSidePanePage().clickFindButtonOnLeftPane();
        sourceNavigateAngularLeftSidePanePage().setFindValue("Rendition UUID", renditionUuid);
        sourceNavigateAngularLeftSidePanePage().clickFindButton();
        sourceNavigateAngularPage().waitForElementExists(format(TOTAL_RENDITIONS_NUMBER, "1"));
        sourceNavigateAngularPage().rightClickRenditions();
        sourceNavigateAngularPage().clickContextSubMenuItem(EDIT, EDIT_EFFECTIVE_DATE);
        sourceNavigateAngularPage().waitForElementExists(format(UI_TITLE_PATTERN, EFFECTIVE_DATE));
        assertThatTitleLoaded(format(UI_TITLE_PATTERN, EFFECTIVE_DATE), true);
        sourceNavigateAngularPage().clickBackButton();
        sourceNavigateAngularPage().waitForPageLoaded();
        assertThatTitleLoaded(SourceNavigateAngularPageElements.homePageLabel, true);
        assertThatTitleLoaded(format(UI_TITLE_PATTERN, EFFECTIVE_DATE), false);

        //Click on Renditions link from source navigate UI home page
        sourceNavigateAngularPage().goToSourcePage();
    }

    /*
       User Story 722130: [HALCYONST-15806] Source Navigation: Clear filter
         */
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void verifyRenditionClearFilterTest() {
        String renditionUuid = "I00022980487611E4B310D2D0CB231E2D";
        //Intial Total number of Renditions
        String totalNumberOfRenditions = sourceNavigateAngularPage().getTotalNumberOfRendtions();

        //Verify Clear Filter working as expected after cleraning Rendition status
        sourceNavigateAngularFiltersAndSortsPage().openMenuColumnHeader(SourceNavigateAngularPageElements.RENDITION_STATUS);
        sourceNavigateAngularFiltersAndSortsPage().openFilterTabColumnHeader();
        sourceNavigateFiltersAndSortsPage().waitForPageLoaded();
        sourceNavigateFiltersAndSortsPage().waitForElement(format(FILTER_CHECKBOX_PATTERN, SELECT_ALL));
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        sourceNavigateAngularFiltersAndSortsPage().clickCheckbox(SELECT_ALL);
        sourceNavigateAngularFiltersAndSortsPage().clickCheckbox(BLANKS);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);

        //After applying RendtionStatus Filter
        assertThatFilteredRenditionsNumberLessThanTotalRenditions(sourceNavigateAngularPage().getTotalNumberOfRendtions(), totalNumberOfRenditions);

        //Clearing Filter
        sourceNavigateAngularPage().isElementDisplayed(SourceNavigateAngularPageElements.CLEAR_FILTERS);
        sourceNavigateAngularPage().clickClearFiltersButton();
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        assertThatTotalRenditionsNumberMatchingToExpected(sourceNavigateAngularPage().getTotalNumberOfRendtions(), totalNumberOfRenditions);

        //Verify Clear Filter working as expected after cleraning Rendition UUID
        sourceNavigateAngularLeftSidePanePage().clickFindButtonOnLeftPane();
        sourceNavigateAngularLeftSidePanePage().setFindValue("Rendition UUID", renditionUuid);
        sourceNavigateAngularLeftSidePanePage().clickFindButton();
        assertThatFilteredRenditionsNumberLessThanTotalRenditions(sourceNavigateAngularPage().getTotalNumberOfRendtions(), totalNumberOfRenditions);
        sourceNavigateAngularPage().clickClearFiltersButton();
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        assertThatTotalRenditionsNumberMatchingToExpected(sourceNavigateAngularPage().getTotalNumberOfRendtions(), totalNumberOfRenditions);
    }

    /**
     * May 2023:
     * User Story Bug 722943: Force Unlock and Transfer Lock the document locked by another user (MANUALTEST)
     * Access Source Navigate UI app as legal user > Click ‘Renditions’ > Select any not locked APV/PREP rendition > Right-click the rendition > Click Edit  Effective Date
     * Now logout the current user and login as risk user and access lock report
     * Search using the locked doc name
     * VERIFY:
     * Unlock option is not enabled
     * Transfer Lock, Force Unlock are enabled
     * Click "Force Unlock"
     * VERIFY:
     * The rendition is not displayed in the locked report
     * Return to renditions tab and lock the rendition once again in Edit mode
     * Logout and login again as legal user
     * Access Lock Report and Search using the locked doc name
     * VERIFY:
     *      Unlock option is not enabled
     *      Transfer Lock, Force Unlock are enabled
     * Click Transfer Lock
     * VERIFY:
     * Rendition is still in the locked report
     * Right click and select Unlock option to unlock the rendition
     */
    @Test
    @Disabled
    @EDGE
    @LEGAL
    @LOG
    public void verifyForceUnlock_TransferLock_ByOtherUser() {
        //Find a rendition that is NOT currently locked
        sourceNavigateAngularLeftSidePanePage().clickFindButtonOnLeftPane();
        sourceNavigateAngularLeftSidePanePage().setFindValue("Rendition UUID", renditionUuid);
        sourceNavigateAngularLeftSidePanePage().clickFindButton();
        sourceNavigateAngularPage().waitForElementExists(format(TOTAL_RENDITIONS_NUMBER, "1"));

        //Lock the rendition from UI
        sourceNavigateAngularPage().rightClickRenditions();
        sourceNavigateAngularPage().clickContextSubMenuItem(EDIT, RENDITION);
        sourceNavigateAngularPage().switchToWindow(EditorPageElements.PAGE_TITLE);
        editorPage().waitForPageLoaded();
        editorPage().waitForElementGone(EditorPageElements.COMMAND_IN_PROGRESS);
        editorPage().waitForElement(EditorToolbarPageElements.CLOSE_DOC);
        editorPage().closeCurrentWindowIgnoreDialogue();

        //Refresh the grid
        editorPage().switchToWindow(SourceNavigateAngularPageElements.PAGE_TITLE);
        sourceNavigateAngularPage().clickRefreshTableData();

        //Login as Risk user
        String specificUserName = "risk";
        sourceNavigateAngularPage().loginAsSpecificUser(specificUserName);

        //Click on Lock Report
        sourceNavigateAngularTabsPage().clickLockReportTab();
        sourceNavigateAngularTabsPage().waitForPageLoaded();

        //Verify that url points to lock report page
        assertThatLockedReportUrl();

        // Filter the locked report using locked by legal user name
        sourceNavigateAngularLockReportPage().clickOnColumnHeaderFilterButton("lockedDocument");
        sourceNavigateAngularLockReportPage().enterInputInFilterInputBox("2021 2RG Chapter 2");
        assertThatRenditionIsLocked(true);

        //Right click on the listed rendition and verify the options and their states
        sourceNavigateAngularLockReportPage().rightClickFirstLockedRendition();
        assertThatLockedRenditionContextMenuItems();
        assertThatContextMenuOptionIsEnabled("Unlock", false);
        assertThatContextMenuOptionIsEnabled("Force Unlock", true);
        assertThatContextMenuOptionIsEnabled("Transfer lock", true);

        //Click on Force Unlock
        sourceNavigateAngularLockReportPage().clickUnlock_ForceUnlock_TransferLock("Force Unlock");
        sourceNavigateAngularLockReportPage().waitForPageLoaded();

        //Assert that the grid does not show the record any more
        DateAndTimeUtils.takeNap(DateAndTimeUtils.FIVE_SECONDS);
        assertThatRenditionIsLocked(false);

        //Click on Renditions tab
        sourceNavigateAngularTabsPage().click(RENDITION_TAB);

        //Find a rendition that is NOT currently locked
        sourceNavigateAngularLeftSidePanePage().clickFindButtonOnLeftPane();
        sourceNavigateAngularLeftSidePanePage().setFindValue("Rendition UUID", renditionUuid);
        sourceNavigateAngularLeftSidePanePage().clickFindButton();
        sourceNavigateAngularPage().waitForElementExists(format(TOTAL_RENDITIONS_NUMBER, "1"));

        //Lock the rendition from UI
        sourceNavigateAngularPage().rightClickRenditions();
        sourceNavigateAngularPage().clickContextSubMenuItem(EDIT, RENDITION);
        sourceNavigateAngularPage().switchToWindow(EditorPageElements.PAGE_TITLE);
        editorPage().waitForPageLoaded();
        editorPage().waitForElementGone(EditorPageElements.COMMAND_IN_PROGRESS);
        editorPage().waitForElement(EditorToolbarPageElements.CLOSE_DOC);
        editorPage().closeCurrentWindowIgnoreDialogue();

        //Refresh the grid
        editorPage().switchToWindow(SourceNavigateAngularPageElements.PAGE_TITLE);
        sourceNavigateAngularPage().clickRefreshTableData();

        //Login as Legal user
        specificUserName = "legal";
        sourceNavigateAngularPage().loginAsSpecificUser(specificUserName);

        //Click on Lock Report
        sourceNavigateAngularTabsPage().clickLockReportTab();
        sourceNavigateAngularTabsPage().waitForPageLoaded();

        //Verify that url points to lock report page
        assertThatLockedReportUrl();

        //Filter lock report based on locked doc name
        DateAndTimeUtils.takeNap(DateAndTimeUtils.FIVE_SECONDS);
        sourceNavigateAngularLockReportPage().clickOnColumnHeaderFilterButton("lockedDocument");
        sourceNavigateAngularLockReportPage().enterInputInFilterInputBox("2021 2RG Chapter 2");
        assertThatRenditionIsLocked(true);

        //Right click on the listed rendition and verify the options and their states
        sourceNavigateAngularLockReportPage().rightClickFirstLockedRendition();
        assertThatLockedRenditionContextMenuItems();
        assertThatContextMenuOptionIsEnabled("Unlock", false);
        assertThatContextMenuOptionIsEnabled("Force Unlock", true);
        assertThatContextMenuOptionIsEnabled("Transfer lock", true);

        //Click on Transfer lock
        sourceNavigateAngularLockReportPage().clickUnlock_ForceUnlock_TransferLock("Transfer lock");
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        assertThatRenditionIsLocked(true);

        //Right click on the listed rendition and verify the options and their states
        sourceNavigateAngularLockReportPage().rightClickFirstLockedRendition();

        //Click on Unlock
        sourceNavigateAngularLockReportPage().clickUnlock_ForceUnlock_TransferLock("Unlock");
        sourceNavigateAngularLockReportPage().waitForPageLoaded();

        //Click on Renditions tab
        sourceNavigateAngularTabsPage().click(RENDITION_TAB);
    }

    /**
     * May 2023:
     * User Story 724160: Refresh page should keep selection
     * Access Source Navigate UI app > Click ‘Renditions’ > Scroll down to load more renditions until Loading is displayed > Select one or more renditions > Click "Refresh" button on the upper right corner of the grid
     * Access Source Navigate UI app > Click ‘Renditions’ > Select any single rendition (not the first one) or multiple renditions > Click "Columns" button on the left pane > Type "Rendition UUID" into the "Search" text field > Click on the "Rendition UUID" checkbox
     * Access Source Navigate UI app > Click ‘Renditions’ > Select any APV rendition displayed on the grid > Access filters in the "Rendition Status" column > Click (Select All) checkbox to unselect all rendition statuses > Click "APV" checkbox
     * Access Source Navigate UI app > Click ‘Renditions’ > Select any rendition(s) > Go to Section tab > Select any section(s) > Go to Delta tab > Select any Delta(s) > Click Refresh grid button
     * VERIFY:
     * Grid is refreshed
     * The selection on the delta(s) is kept
     **/
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void verifyRefreshWindow() {
        String renditionUuid = "I0002B5509AE311E6A7AC9C82917CBFA7";

        sourceNavigateAngularPage().selectTwoRenditions((format(RENDITION_ROW_PATTERN, 4)), (format(RENDITION_ROW_PATTERN, 5)));
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        sourceNavigateAngularPage().clickRefreshTableData();
        assertThatBackgroundBlueForselectedRenditionRow((format(RENDITION_ROW_PATTERN, 4)));
        assertThatBackgroundBlueForselectedRenditionRow((format(RENDITION_ROW_PATTERN, 5)));

        sourceNavigateAngularLeftSidePanePage().filterForColumnAndSelect("Rendition UUID");
        assertThatBackgroundBlueForselectedRenditionRow((format(RENDITION_ROW_PATTERN, 4)));
        assertThatBackgroundBlueForselectedRenditionRow((format(RENDITION_ROW_PATTERN, 5)));

        DateAndTimeUtils.takeNap(DateAndTimeUtils.FIVE_SECONDS);
        sourceNavigateAngularPage().selectTwoRenditions((format(APV_RENDITION_ROW, 6)), (format(APV_RENDITION_ROW, 7)));
        String firstAPVDocumentNumber = sourceNavigateAngularPage().getElementsText((format(APVDOCUMENT_NUMBER, 1)));
        String secondAPVDocumentNumber = sourceNavigateAngularPage().getElementsText((format(APVDOCUMENT_NUMBER, 2)));

        sourceNavigateAngularFiltersAndSortsPage().openMenuColumnHeader(RENDITION_STATUS);
        sourceNavigateAngularFiltersAndSortsPage().openFilterTabColumnHeader();

        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        sourceNavigateAngularFiltersAndSortsPage().clickCheckbox(SELECT_ALL);

        sourceNavigateAngularFiltersAndSortsPage().clickCheckbox(APV_RENDITION_STATUS);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        assertThatRenditionDocumentNumber((format(APVDOCUMENT_NUMBER, 1)), firstAPVDocumentNumber);
        assertThatRenditionDocumentNumber((format(APVDOCUMENT_NUMBER, 2)), secondAPVDocumentNumber);

        sourceNavigateAngularLeftSidePanePage().clickFindButtonOnLeftPane();
        sourceNavigateAngularLeftSidePanePage().setFindValue("Rendition UUID", renditionUuid);
        sourceNavigateAngularLeftSidePanePage().clickFindButton();

        sourceNavigateAngularPage().click(FIRST_RENDITION_ROW);
        sourceNavigateAngularPage().click(SECTION_GROUP_TAB);
        sourceNavigateAngularPage().selectTwoRenditions((format(SECTIONGROUP_ROW, 3)), (format(SECTIONGROUP_ROW, 4)));
        assertThatBackgroundBlueForselectedRenditionRow((format(SECTIONGROUP_ROW, 3)));
        assertThatBackgroundBlueForselectedRenditionRow((format(SECTIONGROUP_ROW, 4)));

        sourceNavigateAngularPage().click(DELTA_TAB);
        sourceNavigateAngularPage().selectTwoRenditions((format(DELTA_ROW, 3)), (format(DELTA_ROW, 4)));
        assertThatBackgroundBlueForselectedRenditionRow((format(DELTA_ROW, 3)));
        assertThatBackgroundBlueForselectedRenditionRow((format(DELTA_ROW, 4)));
        sourceNavigateAngularPage().click(deltarefreshtable);

        sourceNavigateAngularPage().click(SECTION_GROUP_TAB);
        assertThatBackgroundBlueForselectedRenditionRow((format(SECTIONGROUP_ROW, 3)));
        assertThatBackgroundBlueForselectedRenditionRow((format(SECTIONGROUP_ROW, 4)));

        sourceNavigateAngularPage().click(DELTA_TAB);
        assertThatBackgroundBlueForselectedRenditionRow((format(DELTA_ROW, 3)));
        assertThatBackgroundBlueForselectedRenditionRow((format(DELTA_ROW, 4)));
        sourceNavigateAngularPage().click(RENDITION_TAB);
    }

    /**
    * Access Source Navigate UI app > Click Renditions > Select any not locked APV/PREP rendition >
    * Click Lineage/Section/Delta tabs one by one to make table grid loaded > Click Rendition tab >
    * Right-click the selected Rendition > Click Edit a Effective Date > Pick any valid date > Click Submit button >
    * Go to Lineage/Section Group/Section/Delta Group/Delta tabs one by one
    * Table grid contains updated Effective Date value for each tab
    **/
 /*   @Test
    @EDGE
    @LEGAL
    @LOG
    public void verifyEffectiveDateUpdatedinAllGrids() {
        String renditionUuid = "I0002B5509AE311E6A7AC9C82917CBFA7";

        sourceNavigateAngularLeftSidePanePage().clickFindButtonOnLeftPane();
        sourceNavigateAngularLeftSidePanePage().setFindValue("Rendition UUID", renditionUuid);
        sourceNavigateAngularLeftSidePanePage().clickFindButton();

        sourceNavigateAngularPage().rightClick(FIRST_RENDITION_ROW);
        sourceNavigateAngularPage().clickContextMenuItem(EDIT);
        sourceNavigateAngularPage().clickContextMenuItem(CONTEXT_MENU_EFFECTIVE_DATE);
        sourceNavigateAngularPage().sendTextToTextbox(EFFECTIVE_DATE_INPUT, DateAndTimeUtils.getCurrentDateMMddyyyyNoDelimeters());

        sourceNavigateAngularPage().click(SUBMIT);
        sourceNavigateAngularPage().scrollPageHorizontallyShiftEnd(FIRST_RENDITION_ROW);
        assertThatRenditionEffectiveDate(FIRST_RENDITION_EFFECTIVE_DATE);

        sourceNavigateAngularPage().click(SECTION_TAB);
        sourceNavigateAngularPage().click((format(SECTION_ROW, 0)));
        assertThatRenditionEffectiveDate(FIRST_SECTION_EFFECTIVE_DATE);

        sourceNavigateAngularPage().click(DELTA_TAB);
        sourceNavigateAngularPage().scrollPageHorizontallyShiftEnd((format(DELTA_ROW, 0)));
        sourceNavigateAngularPage().click((format(DELTA_ROW, 0)));
        assertThatRenditionEffectiveDate(FIRST_DELTA_EFFECTIVE_DATE);

        sourceNavigateAngularPage().click(RENDITION_TAB);
    }
*/
    /**
     * Bug 723900: Zero File size or zero Delta count do not save in View
     **/
    @Test
    @EDGE
    @CustomAnnotations.UserAnnotations.CITELINE
    @LOG
    public void zeroValueFileSizeSaveViewAs() {
        String view = "ZeroFileSizeView";
        String fileSize = "0";
        sourceNavigateAngularFiltersAndSortsPage().openMenuColumnHeader(SourceNavigateAngularPageElements.File_Size);
        sourceNavigateAngularFiltersAndSortsPage().waitForPageLoaded();
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        sourceNavigateAngularLeftSidePanePage().setFilterSizeValue(File_Size_Filter, fileSize);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.THREE_SECONDS);
        sourceNavigateAngularPage().click(FIRST_RENDITION_ROW);

        sourceNavigateAngularViewManagerPage().click(Save_View);
        sourceNavigateAngularLeftSidePanePage().setFilterSizeValue(View_Name, view);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.ONE_SECOND);
        //Assert that Update Difficulty Level window gets opened
        assertThatExpectedElementPresent(String.format(SourceNavigateAngularPopUpPageElements.UI_TITLE_PATTERN, "Save View"));
        sourceNavigateAngularViewManagerPage().click(Save_And_Select_New_View);
        sourceNavigateAngularViewManagerPage().click(Save_Button);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.THREE_SECONDS);

        sourceNavigateAngularPage().click(FIRST_RENDITION_ROW);
        assertThatFileSizeZero(fileSize);

        //Cleanup
        sourceNavigateAngularViewManagerPage().waitForPageLoaded();
        sourceNavigateAngularViewManagerPage().deleteView(view);
    }

    /**
     * Scenario#1: User Story 721951: [HALCYONST-15574] [CMI] Renditions - Edit - Create Difficulty Level functionality
     * Difficulty Level functionality – single item selection (MANUALTEST)
     * Scenario#4: Remove set difficulty level for Renditions, Sections, Deltas
     **/
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void verifyDifficultyLevelSingleItem() {
        String renditionUuid = "I0072218042BB11EC83838BEC591E7AC1"; // This must be a PREP Doc uuid
        String diffLevel1 = "M1";
        String diffLevel2 = "M3";
        String diffLevel3 = "E2";
        String defaultDiffLevel = "";
        final List<String> expectedDropDownOptions = Arrays.asList("", "E1", "E2", "E3", "M1", "M2", "M3");

        try {
            //Find a rendition that is NOT currently locked
            sourceNavigateAngularLeftSidePanePage().clickFindButtonOnLeftPane();
            sourceNavigateAngularLeftSidePanePage().setFindValue("Rendition UUID", renditionUuid);
            sourceNavigateAngularLeftSidePanePage().clickFindButton();
            sourceNavigateAngularPage().waitForElementExists(format(TOTAL_RENDITIONS_NUMBER, "1"));

            //Add Difficulty Level column to the view from left panel Columns options
            sourceNavigateAngularLeftSidePanePage().filterForColumnAndSelect("Difficulty Level");
            DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);

            //Right click Edit--> Difficulty Level
            sourceNavigateAngularPage().rightClickRenditions();
            sourceNavigateAngularPage().clickContextSubMenuItem(EDIT, DIFFICULTY_LEVEL);
            sourceNavigateAngularPage().waitForPageLoaded();

            //Assert that Update Difficulty Level window gets opened
            assertThatExpectedElementPresent(String.format(SourceNavigateAngularPopUpPageElements.UI_TITLE_PATTERN, "Update Difficulty Level"));

            //Switch to Update Difficulty Level window and verify the dropdown values
            assertThatDifficultyLevelWindowElements();
            assertThatDifficultyLevelDropdownOptionsPresent(expectedDropDownOptions);

            //Select M1 value from dropdown and click Submit
            sourceNavigateAngularPopUpPage().selectDropdownOption(DIFFICULTY_LEVEL_DROPDOWN, diffLevel1);
            sourceNavigateAngularPopUpPage().click(String.format(ANY_BUTTON, "Submit"));
            sourceNavigateAngularPage().waitForPageLoaded();

            //Verify that Difficulty Level column is updated with the value M1 in Renditions grid
            sourceNavigateAngularPage().scrollPageHorizontallyShiftEnd(FIRST_RENDITION_ROW);
            assertThatDifficultyLevelSelectedValueinGrid(FIRST_RENDITION_ROW + String.format(COLUMN_ID_PATTERN, "difficultyLevel"), diffLevel1);

            //Click on Section tab
            sourceNavigateAngularTabsPage().click(SourceNavigateAngularTabsPageElements.SECTION_TAB);
            sourceNavigateAngularTabsPage().waitForPageLoaded();

            //Add Difficulty column from left panel options
            sourceNavigateAngularLeftSidePanePage().filterForColumnAndSelectUnderSpecificTab("SECTION", "Difficulty Level");
            DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);

            //Right click on the first section --> Edit --> Difficulty Level
            sourceNavigateAngularSectionPage().rightClickSection(String.format(SECTION_ROW, "0"));

            //Assert that all sections show value as M1 for difficulty level
            assertThatSectionDifficultyLevelValuesAsExpected(diffLevel1, 0);

            //Right click to access Difficulty Level modal again
            sourceNavigateAngularPage().clickContextSubMenuItem(EDIT, DIFFICULTY_LEVEL);
            sourceNavigateAngularPage().waitForPageLoaded();

            //Assert that Update Difficulty Level window gets opened
            assertThatExpectedElementPresent(String.format(SourceNavigateAngularPopUpPageElements.UI_TITLE_PATTERN, "Update Difficulty Level"));

            //Assert all page elements in Difficulty Level modal
            assertThatDifficultyLevelWindowElements();
            assertThatDifficultyLevelDropdownOptionsPresent(expectedDropDownOptions);

            //Assert that the value M1 is selected in the dropdown list
            assertThatDifficultyLevelDropdownSelectedValue(diffLevel1);

            //Select M3 from the dropdown and click on Submit
            sourceNavigateAngularPopUpPage().selectDropdownOption(DIFFICULTY_LEVEL_DROPDOWN, diffLevel2);
            sourceNavigateAngularPopUpPage().click(String.format(ANY_BUTTON, "Submit"));
            sourceNavigateAngularPage().waitForPageLoaded();
            DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);

            //Verify that Difficulty Level column is updated with the value M1 in Sections grid for first row
            sourceNavigateAngularPage().scrollPageHorizontallyShiftEnd(String.format(SECTION_ROW, "0"));
            sourceNavigateAngularPage().scrollToTop();
            sourceNavigateAngularPage().click(String.format(SECTION_ROW, "0"));
            assertThatDifficultyLevelSelectedValueinGrid((String.format(SECTION_ROW, "0") + String.format(COLUMN_ID_PATTERN, "difficultyLevel")), diffLevel2);

            //Verify that all other sections except first one shows the Difficulty level value as M1
            assertThatSectionDifficultyLevelValuesAsExpected(diffLevel1, 1);

            //Click on Delta tab
            sourceNavigateAngularTabsPage().click(SourceNavigateAngularTabsPageElements.DELTA_TAB);
            sourceNavigateAngularTabsPage().waitForPageLoaded();

            //Add Difficulty column from left panel options in Delta tab
            sourceNavigateAngularLeftSidePanePage().filterForColumnAndSelectUnderSpecificTab("DELTA", "Difficulty Level");
            DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);

            //Verify that all deltas are showing Difficulty Level value as M3
            assertThatDeltaDifficultyLevelValuesAsExpected(diffLevel2, 0);

            //Right click on the first Delta --> Edit --> Difficulty Level
            sourceNavigateAngularDeltaPage().click(String.format(DELTA_ROW, "0"));
            sourceNavigateAngularDeltaPage().rightClickDelta(String.format(DELTA_ROW, "0"));
            sourceNavigateAngularPage().clickContextSubMenuItem(EDIT, DIFFICULTY_LEVEL);
            sourceNavigateAngularPage().waitForPageLoaded();

            //Assert that Update Difficulty Level window gets opened
            assertThatExpectedElementPresent(String.format(SourceNavigateAngularPopUpPageElements.UI_TITLE_PATTERN, "Update Difficulty Level"));

            //Assert all page elements in Difficulty Level modal
            assertThatDifficultyLevelWindowElements();
            assertThatDifficultyLevelDropdownOptionsPresent(expectedDropDownOptions);

            //Assert that the value M1 is selected in the dropdown list
            assertThatDifficultyLevelDropdownSelectedValue(diffLevel2);

            //Select M3 from the dropdown and click on Submit
            sourceNavigateAngularPopUpPage().selectDropdownOption(DIFFICULTY_LEVEL_DROPDOWN, diffLevel3);
            sourceNavigateAngularPopUpPage().click(String.format(ANY_BUTTON, "Submit"));
            sourceNavigateAngularPage().waitForPageLoaded();
            DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);

            //Verify that Difficulty Level column is updated with the value E2 in Delta grid for first row
            sourceNavigateAngularPage().scrollPageHorizontallyShiftEnd(String.format(DELTA_ROW, "0"));
            assertThatDifficultyLevelSelectedValueinGrid((String.format(DELTA_ROW, "0") + String.format(COLUMN_ID_PATTERN, "difficultyLevel")), diffLevel3);

            //Verify that all other sections except first one shows the Difficulty level value as M3
            assertThatDeltaDifficultyLevelValuesAsExpected(diffLevel2, 1);

            //Go back to Section tab and ensure that there is no E2
            sourceNavigateAngularTabsPage().click(SourceNavigateAngularTabsPageElements.SECTION_TAB);
            sourceNavigateAngularTabsPage().waitForPageLoaded();

            //Add Difficulty column from left panel options
            sourceNavigateAngularLeftSidePanePage().filterForColumnAndSelectUnderSpecificTab("SECTION", "Difficulty Level");
            DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
            sourceNavigateAngularPage().scrollPageHorizontallyShiftEnd(String.format(SECTION_ROW, "0"));

            //Assert that the sections still show the value based on selection made earlier
            assertThatDifficultyLevelSelectedValueinGrid((String.format(SECTION_ROW, "0") + String.format(COLUMN_ID_PATTERN, "difficultyLevel")), diffLevel2);

            //Verify that all other sections except first one shows the Difficulty level value as M1
            assertThatSectionDifficultyLevelValuesAsExpected(diffLevel1, 1);

            //Go back to Rendition tab and ensure that there is no E2
            sourceNavigateAngularTabsPage().click(RENDITION_TAB);
            sourceNavigateAngularTabsPage().waitForPageLoaded();
            sourceNavigateAngularPage().scrollPageHorizontallyShiftEnd(FIRST_RENDITION_ROW);
            assertThatDifficultyLevelSelectedValueinGrid(FIRST_RENDITION_ROW + String.format(COLUMN_ID_PATTERN, "difficultyLevel"), diffLevel1);

        } finally {
            //------------------Clean up ---------------------------------------------------//
            //Reset all the difficulty values back to null
            sourceNavigateAngularTabsPage().click(RENDITION_TAB);
            sourceNavigateAngularPage().click(FIRST_RENDITION_ROW);

            //Click on Delta tab
            sourceNavigateAngularTabsPage().click(SourceNavigateAngularTabsPageElements.DELTA_TAB);
            sourceNavigateAngularTabsPage().waitForPageLoaded();

            //Add Difficulty column from left panel options in Delta tab
            sourceNavigateAngularLeftSidePanePage().filterForColumnAndSelectUnderSpecificTab("DELTA", "Difficulty Level");
            DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
            //Right click on the first Delta --> Edit --> Difficulty Level
            sourceNavigateAngularDeltaPage().rightClickDelta(String.format(DELTA_ROW, "0"));
            sourceNavigateAngularPage().clickContextSubMenuItem(EDIT, DIFFICULTY_LEVEL);
            sourceNavigateAngularPage().waitForPageLoaded();
            //Select defaultDiffLevel from the dropdown and click on Submit
            sourceNavigateAngularPopUpPage().selectDropdownOption(DIFFICULTY_LEVEL_DROPDOWN, defaultDiffLevel);
            sourceNavigateAngularPopUpPage().click(String.format(ANY_BUTTON, "Submit"));
            sourceNavigateAngularPage().waitForPageLoaded();
            DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
            sourceNavigateAngularPage().scrollPageHorizontallyShiftEnd(String.format(DELTA_ROW, "0"));
            assertThatDifficultyLevelSelectedValueinGrid((String.format(DELTA_ROW, "0") + String.format(COLUMN_ID_PATTERN, "difficultyLevel")), defaultDiffLevel);

            //Click on Section tab
            sourceNavigateAngularTabsPage().click(SourceNavigateAngularTabsPageElements.SECTION_TAB);
            sourceNavigateAngularTabsPage().waitForPageLoaded();
            //Add Difficulty column from left panel options
            sourceNavigateAngularLeftSidePanePage().filterForColumnAndSelectUnderSpecificTab("SECTION", "Difficulty Level");
            DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
            sourceNavigateAngularSectionPage().click(String.format(SECTION_ROW, "0"));
            //Right click on the first section --> Edit --> Difficulty Level
            sourceNavigateAngularSectionPage().rightClickSection(String.format(SECTION_ROW, "0"));
            //Right click to access Difficulty Level modal again
            sourceNavigateAngularPage().clickContextSubMenuItem(EDIT, DIFFICULTY_LEVEL);
            sourceNavigateAngularPage().waitForPageLoaded();
            //Select defaultDiffLevel from the dropdown and click on Submit
            sourceNavigateAngularPopUpPage().selectDropdownOption(DIFFICULTY_LEVEL_DROPDOWN, defaultDiffLevel);
            sourceNavigateAngularPopUpPage().click(String.format(ANY_BUTTON, "Submit"));
            sourceNavigateAngularPage().waitForPageLoaded();
            DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
            sourceNavigateAngularPage().scrollPageHorizontallyShiftEnd(String.format(SECTION_ROW, "0"));
            assertThatDifficultyLevelSelectedValueinGrid((String.format(SECTION_ROW, "0") + String.format(COLUMN_ID_PATTERN, "difficultyLevel")), defaultDiffLevel);

            //Scenario#2 : Remove set difficulty level for Renditions, Sections, Deltas
            //Right click Edit--> Difficulty Level
            sourceNavigateAngularTabsPage().click(RENDITION_TAB);
            DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
            sourceNavigateAngularPage().click(FIRST_RENDITION_ROW);
            sourceNavigateAngularPage().rightClickRenditions();
            sourceNavigateAngularPage().clickContextSubMenuItem(EDIT, DIFFICULTY_LEVEL);
            sourceNavigateAngularPage().waitForPageLoaded();
            //Select defaultDiffLevel value from dropdown and click Submit
            sourceNavigateAngularPopUpPage().selectDropdownOption(DIFFICULTY_LEVEL_DROPDOWN, defaultDiffLevel);
            sourceNavigateAngularPopUpPage().click(String.format(ANY_BUTTON, "Submit"));
            sourceNavigateAngularPage().waitForPageLoaded();
            DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
            sourceNavigateAngularPage().scrollPageHorizontallyShiftEnd(FIRST_RENDITION_ROW);
            assertThatDifficultyLevelSelectedValueinGrid(FIRST_RENDITION_ROW + String.format(COLUMN_ID_PATTERN, "difficultyLevel"), defaultDiffLevel);
            DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
            sourceNavigateAngularPage().click(FIRST_RENDITION_ROW);

            //Verify that the difficulty level values are empty for Section and Delta after that has been removed under Rendition tab
            //Click on Section tab
            sourceNavigateAngularTabsPage().click(SourceNavigateAngularTabsPageElements.SECTION_TAB);
            sourceNavigateAngularTabsPage().waitForPageLoaded();
            //Add Difficulty column from left panel options
            sourceNavigateAngularLeftSidePanePage().filterForColumnAndSelectUnderSpecificTab("SECTION", "Difficulty Level");
            DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
            sourceNavigateAngularSectionPage().click(String.format(SECTION_ROW, "0"));
            sourceNavigateAngularPage().scrollPageHorizontallyShiftEnd(String.format(SECTION_ROW, "0"));
            assertThatDifficultyLevelSelectedValueinGrid((String.format(SECTION_ROW, "0") + String.format(COLUMN_ID_PATTERN, "difficultyLevel")), defaultDiffLevel);

            //Click on Delta tab
            sourceNavigateAngularTabsPage().click(SourceNavigateAngularTabsPageElements.DELTA_TAB);
            sourceNavigateAngularTabsPage().waitForPageLoaded();

            //Add Difficulty column from left panel options in Delta tab
            sourceNavigateAngularLeftSidePanePage().filterForColumnAndSelectUnderSpecificTab("DELTA", "Difficulty Level");
            DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
            sourceNavigateAngularPage().scrollPageHorizontallyShiftEnd(String.format(DELTA_ROW, "0"));
            assertThatDifficultyLevelSelectedValueinGrid((String.format(DELTA_ROW, "0") + String.format(COLUMN_ID_PATTERN, "difficultyLevel")), defaultDiffLevel);

            //Click on Renditions tab for AfterEach to get executed successfully
            sourceNavigateAngularTabsPage().click(RENDITION_TAB);
            DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        }
    }

    /**
     * User Story 721566: [HALCYONST-15035] Source Lock Report
     **/
    @Test
    @Disabled
    @EDGE
    @LEGAL
    @LOG
    public void sourceLockReportFilter() {
        String contentSet = "Iowa (Development)";
        String renditionStatus = "APV";

        sourceNavigateAngularTabsPage().clickLockReportTab();
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        sourceNavigateAngularLockReportPage().openMenuColumnHeader(CONTENT_SET);
        sourceNavigateAngularLockReportPage().openFilterTabColumnHeader();

        sourceNavigateAngularLockReportPage().enterInputInFilterInputBox(contentSet);
        sourceNavigateAngularLockReportPage().waitForPageLoaded();
        sourceNavigateAngularLockReportPage().pressEnter();
        sourceNavigateAngularLockReportPage().clickRefreshTableData();
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);

        sourceNavigateAngularLockReportPage().openMenuColumnHeader(LOCK_REPORT_RENDITION_STATUS);
        sourceNavigateAngularLockReportPage().enterInputInFilterInputBox(renditionStatus);
        sourceNavigateAngularLockReportPage().clickRefreshTableData();

        assertThatContentSetFiltered(contentSet);
        assertThatRenditionStatusFiltered(renditionStatus);

        sourceNavigateAngularLockReportPage().clickClearFiltersButton();
        sourceNavigateAngularTabsPage().click(RENDITION_TAB);

    }

    /**
     * User Story 722021: [HALCYONST-15653] [CMI] Renditions - Edit - Effective Date
     * User Story 723077:[HALCYONST-17027] [CMI]Section - Edit - Effective Date
     * User Story 723232: [HALCYONST-17225] [CMI]Delta: Effective Date
     * User Story 725284: Effective date year picker should be ranged from 1999 to current year
     **/
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void editEffectiveDate() {
        String renditionUuid = "I000A4F50744211E9AA1792A035F921BD";
        String dateValue = "09/01/2023";

        sourceNavigateAngularLeftSidePanePage().clickFindButtonOnLeftPane();
        sourceNavigateAngularLeftSidePanePage().setFindValue("Rendition UUID", renditionUuid);
        sourceNavigateAngularLeftSidePanePage().clickFindButton();
        sourceNavigateAngularLeftSidePanePage().clickFindButtonOnLeftPane();

        sourceNavigateAngularPage().rightClick(FIRST_RENDITION_ROW);
        sourceNavigateAngularPage().clickContextMenuItem(EDIT);
        sourceNavigateAngularPage().clickContextMenuItem(CONTEXT_MENU_EFFECTIVE_DATE);
        sourceNavigateAngularPage().sendTextToTextbox(EFFECTIVE_DATE_INPUT, DateAndTimeUtils.getCurrentDateMMddyyyyNoDelimeters());

        sourceNavigateAngularPage().click(SUBMIT);
        sourceNavigateAngularPage().scrollPageHorizontallyShiftEnd(FIRST_RENDITION_ROW);
        assertThatRenditionEffectiveDate(FIRST_RENDITION_EFFECTIVE_DATE);

        sourceNavigateAngularPage().click(SECTION_TAB);
        sourceNavigateAngularPage().click((format(SECTION_ROW, 0)));
        sourceNavigateAngularPage().scrollPageHorizontallyShiftEnd(String.format(SECTION_ROW, 0));
        assertThatRenditionEffectiveDate(FIRST_SECTION_EFFECTIVE_DATE);

        sourceNavigateAngularPage().click(DELTA_TAB);
        sourceNavigateAngularPage().click((format(DELTA_ROW, 1)));
        sourceNavigateAngularPage().scrollPageHorizontallyShiftEnd(String.format(DELTA_ROW, 1));
        assertThatRenditionEffectiveDate(String.format(FIRST_DELTA_EFFECTIVE_DATE, 1));

        sourceNavigateAngularPage().click(RENDITION_TAB);
        sourceNavigateAngularPage().rightClick(FIRST_RENDITION_ROW);
        sourceNavigateAngularPage().clickContextMenuItem(EDIT);
        sourceNavigateAngularPage().clickContextMenuItem(CONTEXT_MENU_EFFECTIVE_DATE);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.ONE_SECOND);
        sourceNavigateAngularPage().click(GENERAL_EFFECTIVE_DATE_TOGGLE_BUTTON);
        sourceNavigateAngularPage().click(SUBMIT);

        sourceNavigateAngularPage().click(SECTION_TAB);
        sourceNavigateAngularPage().click((format(SECTION_ROW, 0)));
        sourceNavigateAngularPage().scrollPageHorizontallyShiftEnd(String.format(SECTION_ROW, 0));
        DateAndTimeUtils.takeNap(DateAndTimeUtils.ONE_SECOND);
        assertThatGeneralEffectiveDate(FIRST_SECTION_EFFECTIVE_DATE, dateValue);

        sourceNavigateAngularPage().click(DELTA_TAB);
        sourceNavigateAngularPage().click((format(DELTA_ROW, 1)));
        sourceNavigateAngularPage().scrollPageHorizontallyShiftEnd(format(DELTA_ROW, 0));
        assertThatGeneralEffectiveDate(String.format(FIRST_DELTA_EFFECTIVE_DATE, 1), dateValue);

        sourceNavigateAngularTabsPage().click(RENDITION_TAB);
    }

    /**
     * User Story 722021: [HALCYONST-15653] [CMI] Renditions - Edit - Effective Date
     * User Story 723077:[HALCYONST-17027] [CMI]Section - Edit - Effective Date
     * User Story 723232: [HALCYONST-17225] [CMI]Delta: Effective Date
     * User Story 725284: Effective date year picker should be ranged from 1999 to current year
     **/
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void cancelChangesOfEffectiveDateforRenditionsSectionsDeltas() 
    {
        String renditionUuid = "I000574806FEC11E4B0E4FDA79C60D85B";
        String effectivedate = "02/24/2023";

        sourceNavigateAngularLeftSidePanePage().clickFindButtonOnLeftPane();
        sourceNavigateAngularLeftSidePanePage().setFindValue("Rendition UUID", renditionUuid);
        sourceNavigateAngularLeftSidePanePage().clickFindButton();
        sourceNavigateAngularLeftSidePanePage().clickFindButtonOnLeftPane();

        sourceNavigateAngularPage().rightClick(FIRST_RENDITION_ROW);
        sourceNavigateAngularPage().clickContextMenuItem(EDIT);
        sourceNavigateAngularPage().clickContextMenuItem(CONTEXT_MENU_EFFECTIVE_DATE);
        sourceNavigateAngularPage().sendTextToTextbox(EFFECTIVE_DATE_INPUT, DateAndTimeUtils.getCurrentDateMMddyyyyNoDelimeters());

        sourceNavigateAngularPage().click(CANCEL_BUTTON);
        sourceNavigateAngularPage().scrollPageHorizontallyShiftEnd(FIRST_RENDITION_ROW);
        assertThatCancelEffectiveDate(FIRST_RENDITION_EFFECTIVE_DATE, effectivedate);

        sourceNavigateAngularPage().click(SECTION_TAB);
        sourceNavigateAngularPage().click((format(SECTION_ROW, 0)));
        sourceNavigateAngularPage().rightClick((format(SECTION_ROW, 0)));
        sourceNavigateAngularPage().clickContextMenuItem(EDIT);
        sourceNavigateAngularPage().clickContextMenuItem(CONTEXT_MENU_EFFECTIVE_DATE);
        sourceNavigateAngularPage().sendTextToTextbox(EFFECTIVE_DATE_INPUT, DateAndTimeUtils.getCurrentDateMMddyyyyNoDelimeters());

        sourceNavigateAngularPage().click(CANCEL_BUTTON);
        sourceNavigateAngularPage().scrollPageHorizontallyShiftEnd(format(SECTION_ROW, 0));
        assertThatCancelEffectiveDate(FIRST_SECTION_EFFECTIVE_DATE, effectivedate);

        sourceNavigateAngularPage().click(DELTA_TAB);
        sourceNavigateAngularPage().scrollToRight("500", SubscribedCasesPageElementsAngular.HORIZONTAL_SCROLL);
        sourceNavigateAngularPage().rightClick((format(DELTA_ROW, 0)));
        sourceNavigateAngularPage().clickContextMenuItem(EDIT);
        sourceNavigateAngularPage().clickContextMenuItem(CONTEXT_MENU_EFFECTIVE_DATE);
        sourceNavigateAngularPage().sendTextToTextbox(EFFECTIVE_DATE_INPUT, DateAndTimeUtils.getCurrentDateMMddyyyyNoDelimeters());

        sourceNavigateAngularPage().click(CANCEL_BUTTON);
        sourceNavigateAngularPage().scrollPageHorizontallyShiftEnd((format(DELTA_ROW, 0)));
        assertThatCancelEffectiveDate(String.format(FIRST_DELTA_EFFECTIVE_DATE, 0), effectivedate);
        sourceNavigateAngularPage().click(RENDITION_TAB);
    }

    /**
     * User Story 722021: [HALCYONST-15653] [CMI] Renditions - Edit - Effective Date
     * User Story 723077:[HALCYONST-17027] [CMI]Section - Edit - Effective Date
     * User Story 723232: [HALCYONST-17225] [CMI]Delta: Effective Date
     * User Story 725284: Effective date year picker should be ranged from 1999 to current year
     **/
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void setEffectiveDateOnSingleLockedDocumentforRenditionsSectionsDeltas() {
        String renditionUuid = "I01E7D671FAD011E4BF4FB0BE839A745E";
        String lockMessage = "Error: Locked by: Buvaneshwar V.G. on 11/30/2023";

        sourceNavigateAngularLeftSidePanePage().clickFindButtonOnLeftPane();
        sourceNavigateAngularLeftSidePanePage().setFindValue("Rendition UUID", renditionUuid);
        sourceNavigateAngularLeftSidePanePage().clickFindButton();
        sourceNavigateAngularLeftSidePanePage().clickFindButtonOnLeftPane();

        sourceNavigateAngularPage().rightClick(FIRST_RENDITION_ROW);
        sourceNavigateAngularPage().clickContextMenuItem(EDIT);
        sourceNavigateAngularPage().clickContextMenuItem(CONTEXT_MENU_EFFECTIVE_DATE);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        assertThatMessage(String.format(ERROR_TOAST_MESSAGE,lockMessage), lockMessage);

        sourceNavigateAngularPage().click(SECTION_TAB);
        sourceNavigateAngularPage().click((format(SECTION_ROW, 0)));
        sourceNavigateAngularPage().rightClick((format(SECTION_ROW, 0)));
        sourceNavigateAngularPage().clickContextMenuItem(EDIT);
        sourceNavigateAngularPage().clickContextMenuItem(CONTEXT_MENU_EFFECTIVE_DATE);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.ONE_SECOND);
        assertThatMessage(String.format(ERROR_TOAST_MESSAGE,lockMessage), lockMessage);

        sourceNavigateAngularPage().click(DELTA_TAB);
        sourceNavigateAngularPage().scrollToRight("500", SubscribedCasesPageElementsAngular.HORIZONTAL_SCROLL);
        sourceNavigateAngularPage().rightClick((format(DELTA_ROW, 0)));
        sourceNavigateAngularPage().clickContextMenuItem(EDIT);
        sourceNavigateAngularPage().clickContextMenuItem(CONTEXT_MENU_EFFECTIVE_DATE);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.ONE_SECOND);
        assertThatMessage(String.format(ERROR_TOAST_MESSAGE,lockMessage), lockMessage);

        sourceNavigateAngularPage().click(RENDITION_TAB);
    }

    /**
     * Access Source Navigate UI app > Click ‘Renditions’ > Enable the  Assigned User  and  Assigned Date  columns via the  Columns  UI on the left-side pane for all the tabs
     *
     * VERIFY:	-	The grid refreshes
     * -	The  Assigned User  and  Assigned Date  columns are displayed in the grid
     *
     * Select one or more not locked and not deleted Rendition(s) without Assigned User and Assigned Date > Right-click the rendition(s) > Click  Modify -> Assign User
     *
     * VERIFY:-	Assign User UI window is opened.
     * -	It contains  User  drop-down list,  Assigned to date  field, the  Calendar  button and  Submit  and  Cancel  buttons.
     * -	The User  drop-down list is empty.
     * -	The Assigned to date  field is empty.
     * -	If click on Calendar  button, today’s date is displayed.
     * Click on the User  drop-down list > Select any user > Type in the correct date mm/dd/yyyy into the Assigned to date  text field > Click on the Calendar  button	The typed date is equal to the date displayed on the Calendar
     * Click on the Calendar  button again to close it > Click Submit  button	-	Assign User UI window is closed.
     * -	The grid is refreshed.
     * -	Assigned user is displayed in the Assigned User  column
     * -	Assigned date is displayed in the Assigned Date  column.
     *
     *  Right-click the Rendition > Click Modify -> Assign User 	-	Assign User UI window is opened.
     * -	Previously assigned user is displayed on the User  drop-down list
     * -	Previously assigned date is displayed on the Assigned to date  field
     * Select another user from the drop-down list > Click on Calendar  button > Select a year and a month on the Calendar > Click on any day	-	Calendar is closed.
     * -	Selected date is displayed in the Assigned to date  field.
     * Click Submit  button	Confirmation modal window appears
     * Click  Cancel  button	-	Confirmation modal window is closed.
     * -	Assign user UI window is active.
     * -	The  User  and  Assigned to date  values are the same as before clicking on  Submit  button
     * Click  Submit  button > Confirm the action	-	Confirmation modal window is closed.
     * -	Assign User UI window is closed.
     * -	The grid is refreshed.
     * -	New assigned user is displayed in the  Assigned User  column
     * -	New assigned date is displayed in the  Assigned Date  column.
     * Right-click on the Rendition > Click  Modify -> Assign User 	-	Assign User UI window is opened.
     * -	Previously assigned user is displayed on the  User  drop-down list
     * -	Previously assigned date is displayed on the  Assigned to date  field.
     * Select an empty option from the drop-down list > Clear  Assigned to date  field > Click  Submit  button > Confirm the action	-	Confirmation modal window is closed.
     * -	Assign User UI window is closed.
     * -	The grid is refreshed.
     * -	No assigned user is displayed in the  Assigned User  column
     * -	No assigned date is displayed in the  Assigned Date  column.
     */
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void assignUserAndDateOfTheDocuments()
    {
        String rendition_uuid = "I010E8F200FA511E5BCA8C9C17001CA39";
        String userName1 = "TLE TCBA-BOT";
        String userName2 = "TLE TCBB-BOT";

        sourceNavigateAngularLeftSidePanePage().clickFindButtonOnLeftPane();
        sourceNavigateAngularLeftSidePanePage().setFindValue("Rendition UUID", rendition_uuid);
        sourceNavigateAngularLeftSidePanePage().clickFindButton();

        sourceNavigateAngularLeftSidePanePage().filterForColumnAndSelect("Assigned User");
        sourceNavigateAngularLeftSidePanePage().filterForColumnAndSelect("Assigned Date");
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);

        //Right click Modify--> AssignUser
        sourceNavigateAngularPage().rightClickRenditions();
        sourceNavigateAngularPage().clickContextSubMenuItem(MODIFY, ASSIGN_USER);
        sourceNavigateAngularPage().waitForPageLoaded();

        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        sourceNavigateAngularPage().click(ASSIGN_USER_DROP_DOWN);
        sourceNavigateAngularPage().click(BLANK_USER);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);

        sourceNavigateAngularPage().clear(ASSIGNED_TO_DATE);
        sourceNavigateAngularPage().sendKeysToElement(ASSIGNED_TO_DATE, DateAndTimeUtils.getCurrentDateMMddyyyyNoDelimeters());
        sourceNavigateAngularPage().click(Submit_Button);
        assertThatMessage(ASSIGNED_USER_BLANK_MESSAGE,"User is required when setting a date.");
        sourceNavigateAngularPage().clear(ASSIGNED_TO_DATE);
        sourceNavigateAngularPage().click(Submit_Button);
        if (sourceNavigateAngularPage().doesElementExist(CONFIRM_BUTTON))
        {
            sourceNavigateAngularPage().click(CONFIRM_BUTTON);
        }
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        sourceNavigateAngularPage().rightClickRenditions();
        sourceNavigateAngularPage().clickContextSubMenuItem(MODIFY, ASSIGN_USER);
        sourceNavigateAngularPage().waitForPageLoaded();

        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        sourceNavigateAngularPage().clear(ASSIGN_USER_USER);
        sourceNavigateAngularPage().sendKeysToElement(ASSIGN_USER_USER,userName1);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        sourceNavigateAngularPage().click(format(SPAN_CONTAINS_TEXT, userName1));

        sourceNavigateAngularPage().click(Submit_Button);

        sourceNavigateAngularPage().scrollPageHorizontallyShiftEnd(FIRST_RENDITION_ROW);
        assertThatRenditionAssignedDateAndUser(FIRST_RENDITION_ASSIGNED_DATE,FIRST_RENDITION_ASSIGNED_USER,userName1);

        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        sourceNavigateAngularPage().rightClickRenditions();
        sourceNavigateAngularPage().clickContextSubMenuItem(MODIFY, ASSIGN_USER);
        sourceNavigateAngularPage().waitForPageLoaded();

        DateAndTimeUtils.takeNap(DateAndTimeUtils.FIVE_SECONDS);
        sourceNavigateAngularPage().clear(ASSIGN_USER_USER);
        sourceNavigateAngularPage().sendKeysToElement(ASSIGN_USER_USER,userName1.substring(1,userName1.length()-4));
        sourceNavigateAngularPage().click(format(SPAN_CONTAINS_TEXT, userName1));

        sourceNavigateAngularPage().clear(ASSIGNED_TO_DATE);
        sourceNavigateAngularPage().sendKeysToElement(ASSIGNED_TO_DATE, "13/3*/0000");
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        assertThatMessage(ASSIGNED_DATE_INCORRECT_MESSAGE,"The correct date format should be MM/DD/YYYY.");
        sourceNavigateAngularPage().click(CANCEL_BUTTON);

        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        sourceNavigateAngularPage().rightClickRenditions();
        sourceNavigateAngularPage().clickContextSubMenuItem(MODIFY, ASSIGN_USER);
        sourceNavigateAngularPage().waitForPageLoaded();

        sourceNavigateAngularPage().clear(ASSIGN_USER_USER);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        sourceNavigateAngularPage().sendKeysToElement(ASSIGN_USER_USER,userName2.substring(0,userName2.length()-4));
        sourceNavigateAngularPage().click(format(SPAN_CONTAINS_TEXT, userName2));
        sourceNavigateAngularPage().click(Submit_Button);

        assertThatMessage(ASSIGNED_USER_MESSAGE,"One or more of the selected documents is already assigned to a user. Continue?");
        sourceNavigateAngularPage().click(SOURCE_NAV_ASSIGN_USER_CANCEL);

        sourceNavigateAngularPage().click(Submit_Button);
        sourceNavigateAngularPage().click(CONFIRM_BUTTON);

        sourceNavigateAngularPage().scrollPageHorizontallyShiftEnd(FIRST_RENDITION_ROW);
        assertThatRenditionAssignedDateAndUser(FIRST_RENDITION_ASSIGNED_DATE,FIRST_RENDITION_ASSIGNED_USER,userName2);
    }
    
    /**
     * Scenario#1: Bug 724765: SN - Selection issues: sometimes the sections/deltas of the
     * previously selected rendition are still available in the sections/deltas tab of the newly selected rendition
     **/
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void verifySectionsDeltasRefreshesForRenditionSearch() {
        String renditionUUID2 = "I0002B5509AE311E6A7AC9C82917CBFA7";
        String renditionUUIDs = renditionUuid + "," + renditionUUID2;

        //Find a rendition that is NOT currently locked
        sourceNavigateAngularLeftSidePanePage().clickFindButtonOnLeftPane();
        sourceNavigateAngularLeftSidePanePage().setFindValue("Rendition UUID", renditionUUIDs);
        sourceNavigateAngularLeftSidePanePage().clickFindButton();
        sourceNavigateAngularPage().waitForElementExists(format(TOTAL_RENDITIONS_NUMBER, "2"));
        sourceNavigateAngularPage().click(FIRST_RENDITION_ROW);

        //Go to Sections tab and validate the Total sections count against the db count
        sourceNavigateAngularPage().click(SECTION_TAB);
        int sectionCount = SourceDatabaseUtils.getQueryKeyForRenditionUUID(connection, "getSectionsCountForRenditionUUID", renditionUUID2);
        assertThatTrue(format(TOTAL_SECTIONS, sectionCount));

        //Go to Deltas tab and validate the Total deltas count against the db count
        sourceNavigateAngularPage().click(DELTA_TAB);
        int deltaCount = SourceDatabaseUtils.getQueryKeyForRenditionUUID(connection, "getDeltasCountForRenditionUUID", renditionUUID2);
        assertThatTrue(format(TOTAL_DELTAS, deltaCount));

        //validate the Total deltas count against the db count
        //Go back to Renditions tab
        sourceNavigateAngularPage().click(RENDITION_TAB);

        //Go to the second rendition
        sourceNavigateAngularPage().click(String.format(ANY_RENDITION_ROW, 1));

        //Go to Sections tab and validate the Total sections count against the db count
        sourceNavigateAngularPage().click(SECTION_TAB);
        sectionCount = SourceDatabaseUtils.getQueryKeyForRenditionUUID(connection, "getSectionsCountForRenditionUUID", renditionUuid);
        assertThatTrue(format(TOTAL_SECTIONS, sectionCount));

        //Go to Deltas tab and validate the Total deltas count against the db count
        sourceNavigateAngularPage().click(DELTA_TAB);
        deltaCount = SourceDatabaseUtils.getQueryKeyForRenditionUUID(connection, "getDeltasCountForRenditionUUID", renditionUuid);
        assertThatTrue(format(TOTAL_DELTAS, deltaCount));

        //Go back to Renditions tab
        sourceNavigateAngularPage().click(RENDITION_TAB);
    }

    /**
     * User Story 722021: [HALCYONST-15653] [CMI] Renditions - Edit - Effective Date
     * User Story 723077:[HALCYONST-17027] [CMI]Section - Edit - Effective Date
     * User Story 723232: [HALCYONST-17225] [CMI]Delta: Effective Date
     * User Story 725284: Effective date year picker should be ranged from 1999 to current year
     **/
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void setEffectiveDateOfIncorrectFormatForRenditionsSectionsDeltas() {
        String renditionUuid = "I010DE431DBF911EC903D82C4F8A3D1DA";

        sourceNavigateAngularLeftSidePanePage().clickFindButtonOnLeftPane();
        sourceNavigateAngularLeftSidePanePage().setFindValue("Rendition UUID", renditionUuid);
        sourceNavigateAngularLeftSidePanePage().clickFindButton();

        sourceNavigateAngularPage().rightClick(FIRST_RENDITION_ROW);
        sourceNavigateAngularPage().clickContextMenuItem(EDIT);
        sourceNavigateAngularPage().clickContextMenuItem(CONTEXT_MENU_EFFECTIVE_DATE);
        sourceNavigateAngularPage().sendTextToTextbox(EFFECTIVE_DATE_INPUT, "AA/BB/YYYY");
        assertThatInCorrectDateFormat(Incorrect_Date_Format);
        sourceNavigateAngularPage().clear(EFFECTIVE_DATE_INPUT);
        sourceNavigateAngularPage().sendTextToTextbox(EFFECTIVE_DATE_INPUT, "11081996");
        assertThatCorrectDateAfter1999(Incorrect_Date_Format);
        sourceNavigateAngularPage().click(CANCEL_BUTTON);

        sourceNavigateAngularPage().click(SECTION_TAB);
        sourceNavigateAngularPage().click((format(SECTION_ROW, 0)));
        sourceNavigateAngularPage().rightClick((format(SECTION_ROW, 0)));
        sourceNavigateAngularPage().clickContextMenuItem(EDIT);
        sourceNavigateAngularPage().clickContextMenuItem(CONTEXT_MENU_EFFECTIVE_DATE);
        sourceNavigateAngularPage().sendTextToTextbox(EFFECTIVE_DATE_INPUT, "AA/BB/YYYY");
        assertThatInCorrectDateFormat(Incorrect_Date_Format);
        sourceNavigateAngularPage().clear(EFFECTIVE_DATE_INPUT);
        sourceNavigateAngularPage().sendTextToTextbox(EFFECTIVE_DATE_INPUT, "11081996");
        assertThatCorrectDateAfter1999(Incorrect_Date_Format);
        sourceNavigateAngularPage().click(CANCEL_BUTTON);

        sourceNavigateAngularPage().click(DELTA_TAB);
        sourceNavigateAngularPage().scrollToRight("500", SubscribedCasesPageElementsAngular.HORIZONTAL_SCROLL);
        sourceNavigateAngularPage().rightClick((format(DELTA_ROW, 0)));
        sourceNavigateAngularPage().clickContextMenuItem(EDIT);
        sourceNavigateAngularPage().clickContextMenuItem(CONTEXT_MENU_EFFECTIVE_DATE);
        sourceNavigateAngularPage().sendTextToTextbox(EFFECTIVE_DATE_INPUT, "AA/BB/YYYY");
        assertThatInCorrectDateFormat(Incorrect_Date_Format);
        sourceNavigateAngularPage().clear(EFFECTIVE_DATE_INPUT);
        sourceNavigateAngularPage().sendTextToTextbox(EFFECTIVE_DATE_INPUT, "11081996");
        assertThatCorrectDateAfter1999(Incorrect_Date_Format);
        sourceNavigateAngularPage().click(CANCEL_BUTTON);
        sourceNavigateAngularPage().click(RENDITION_TAB);
    }

    /**
     * User Story 722021: [HALCYONST-15653] [CMI] Renditions - Edit - Effective Date
     * User Story 723077:[HALCYONST-17027] [CMI]Section - Edit - Effective Date
     * User Story 723232: [HALCYONST-17225] [CMI]Delta: Effective Date
     * User Story 725284: Effective date year picker should be ranged from 1999 to current year
     **/
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void setEffectiveDateOnMultipleSelectedDocuments() {
        String renditionuuid_1 = "IA3812780B9D011ED9231AF49E03A2FBF";
        String renditionuuid_2 = "IB01D9380FBC911ED831E81D63AEBAA2B";
        String dateValue = "11/08/2010";

        sourceNavigateAngularLeftSidePanePage().clickFindButtonOnLeftPane();
        sourceNavigateAngularLeftSidePanePage().setFindValue("Rendition UUID", renditionuuid_1 + "," + renditionuuid_2);
        sourceNavigateAngularLeftSidePanePage().clickFindButton();
        sourceNavigateAngularLeftSidePanePage().clickFindButtonOnLeftPane();

        sourceNavigateAngularPage().selectTwoRenditions((format(RENDITION_ROW_PATTERN, 0)), (format(RENDITION_ROW_PATTERN, 1)));
        sourceNavigateAngularPage().rightClick(FIRST_RENDITION_ROW);
        sourceNavigateAngularPage().clickContextMenuItem(EDIT);
        sourceNavigateAngularPage().clickContextMenuItem(CONTEXT_MENU_EFFECTIVE_DATE);
        sourceNavigateAngularPage().sendTextToTextbox(EFFECTIVE_DATE_INPUT, DateAndTimeUtils.getCurrentDateMMddyyyyNoDelimeters());

        sourceNavigateAngularPage().click(SUBMIT);
        sourceNavigateAngularPage().scrollPageHorizontallyShiftEnd(FIRST_RENDITION_ROW);
        assertThatRenditionEffectiveDate(FIRST_RENDITION_EFFECTIVE_DATE);

        sourceNavigateAngularPage().click(SECTION_TAB);
        sourceNavigateAngularPage().selectTwoRenditions((format(SECTION_ROW, 0)), (format(SECTION_ROW, 1)));
        sourceNavigateAngularPage().rightClick((format(SECTION_ROW, 0)));
        sourceNavigateAngularPage().clickContextMenuItem(EDIT);
        sourceNavigateAngularPage().clickContextMenuItem(CONTEXT_MENU_EFFECTIVE_DATE);
        sourceNavigateAngularPage().sendTextToTextbox(EFFECTIVE_DATE_INPUT, "11082010");
        sourceNavigateAngularPage().click(SUBMIT);
        sourceNavigateAngularPage().scrollPageHorizontallyShiftEnd(format(SECTION_ROW, 0));
        assertThatSecondRenditionEffectiveDate(SECOND_SECTION_EFFECTIVE_DATE, dateValue);

        sourceNavigateAngularPage().click(DELTA_TAB);
        sourceNavigateAngularPage().selectTwoRenditions((format(DELTA_ROW, 0)), (format(DELTA_ROW, 1)));
        sourceNavigateAngularPage().rightClick((format(DELTA_ROW, 0)));
        sourceNavigateAngularPage().clickContextMenuItem(EDIT);
        sourceNavigateAngularPage().clickContextMenuItem(CONTEXT_MENU_EFFECTIVE_DATE);
        sourceNavigateAngularPage().sendTextToTextbox(EFFECTIVE_DATE_INPUT, "11082010");
        sourceNavigateAngularPage().click(SUBMIT);
        sourceNavigateAngularPage().scrollPageHorizontallyShiftEnd((format(DELTA_ROW, 0)));
        assertThatSecondRenditionEffectiveDate(SECOND_DELTA_EFFECTIVE_DATE, dateValue);
        sourceNavigateAngularPage().click(RENDITION_TAB);
    }

    /**
     * Add buttons are disabled for non-PREP Renditions/Sections/Deltas
     */
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void verifyNotes_NonPrep_Rendition() {
        String renditionStatus = "APV";
        sourceNavigateAngularFiltersAndSortsPage().openMenuColumnHeader(SourceNavigateAngularPageElements.RENDITION_STATUS);
        sourceNavigateAngularFiltersAndSortsPage().openFilterTabColumnHeader();
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        sourceNavigateAngularFiltersAndSortsPage().clickCheckbox(SELECT_ALL);
        sourceNavigateAngularFiltersAndSortsPage().setRenditionStatus(FILTER_RENDITION_STATUS, renditionStatus);
        sourceNavigateAngularFiltersAndSortsPage().clickCheckbox(renditionStatus);
        sourceNavigateAngularPage().isElementDisabled(NOTES_VALUE_OF_RENDITIONS);
        sourceNavigateAngularPage().click(FIRST_RENDITION_ROW);
        sourceNavigateAngularTabsPage().click(SourceNavigateAngularTabsPageElements.SECTION_TAB);
        sourceNavigateAngularTabsPage().waitForPageLoaded();
        //Add Notes column from left panel options
        sourceNavigateAngularLeftSidePanePage().filterForColumnAndSelectUnderSpecificTab("SECTION", "Notes");
        sourceNavigateAngularPage().isElementDisabled(NOTES_VALUE_OF_RENDITIONS);
        //Delta tab
        sourceNavigateAngularTabsPage().click(SourceNavigateAngularTabsPageElements.DELTA_TAB);
        sourceNavigateAngularPage().isElementDisabled(NOTES_VALUE_OF_RENDITIONS);
        sourceNavigateAngularTabsPage().click(RENDITION_TAB);
    }

    /**
     * Add buttons are disabled for PREP Renditions/Sections/Deltas
     **/
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void verifyNotes_Prep_Rendition() {
        String renditionStatus = "PREP";
        sourceNavigateAngularFiltersAndSortsPage().openMenuColumnHeader(SourceNavigateAngularPageElements.RENDITION_STATUS);
        sourceNavigateAngularFiltersAndSortsPage().openFilterTabColumnHeader();
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        sourceNavigateAngularFiltersAndSortsPage().clickCheckbox(SELECT_ALL);
        sourceNavigateAngularFiltersAndSortsPage().setRenditionStatus(FILTER_RENDITION_STATUS, renditionStatus);
        sourceNavigateAngularFiltersAndSortsPage().clickCheckbox(renditionStatus);
        sourceNavigateAngularPage().isElementEnabled(NOTES_VALUE_OF_RENDITIONS);
        sourceNavigateAngularPage().click(format(RENDITION_ROW_PATTERN, 3));
        sourceNavigateAngularTabsPage().click(SourceNavigateAngularTabsPageElements.SECTION_TAB);
        sourceNavigateAngularTabsPage().waitForPageLoaded();

        //Add Notes column from left panel options
        sourceNavigateAngularLeftSidePanePage().accessColumnsInterface("SECTION");
        sourceNavigateAngularLeftSidePanePage().filterForColumnAndSelectUnderSpecificTab("SECTION", "Notes");
        sourceNavigateAngularPage().isElementEnabled(NOTES_VALUE_OF_RENDITIONS);
        sourceNavigateAngularTabsPage().click(SourceNavigateAngularTabsPageElements.DELTA_TAB);
        sourceNavigateAngularPage().isElementDisabled(NOTES_VALUE_OF_RENDITIONS);
        sourceNavigateAngularTabsPage().click(RENDITION_TAB);
    }

    /**
     * Edit Instruction Notes – locked PREP Renditions/Sections/Deltas
     **/
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void verifyEdit_Instruction_NotesTest() {
        String renditionUuid = "I0072218042BB11EC83838BEC591E7AC1"; // This must be a PREP Doc uuid
        sourceNavigateAngularLeftSidePanePage().clickFindButtonOnLeftPane();
        sourceNavigateAngularLeftSidePanePage().setFindValue("Rendition UUID", renditionUuid);
        sourceNavigateAngularLeftSidePanePage().clickFindButton();
        sourceNavigateAngularPage().waitForElementExists(format(TOTAL_RENDITIONS_NUMBER, "1"));

        //Lock the rendition from UI
        sourceNavigateAngularPage().click(SourceNavigateAngularPageElements.FIRST_RENDITION_ROW);
        sourceNavigateAngularPage().rightClickRenditions();
        sourceNavigateAngularPage().clickContextSubMenuItem(EDIT, RENDITION);
        sourceNavigateAngularPage().switchToWindow(EditorPageElements.PAGE_TITLE);
        editorPage().waitForPageLoaded();
        editorPage().waitForElementGone(EditorPageElements.COMMAND_IN_PROGRESS);
        editorPage().waitForElement(EditorToolbarPageElements.CLOSE_DOC);
        editorPage().closeCurrentWindowIgnoreDialogue();

        //Refresh the grid
        editorPage().switchToWindow(SourceNavigateAngularPageElements.PAGE_TITLE);
        sourceNavigateAngularPage().clickRefreshTableData();

        //Rendtion>Edit>Rendition Notes
        sourceNavigateAngularPage().click(format(RENDITION_ROW_PATTERN, 0));
        sourceNavigateAngularPage().rightClickRenditions();
        sourceNavigateAngularPage().clickContextSubMenuItem(EDIT, RENDITION_NOTES);
        assertThatInstructionNotesWindowIsDisplayed(EDIT_RENDITION_INSTRUCTION_NOTES,"Edit Rendition Instruction Notes");
        sourceNavigateAngularRenditionPage().renditionlockedByUsername();
        boolean renditionLevelIsReadOnly = sourceNavigateAngularRenditionPage().getRenditionLevelInstructionsReadOnly();
        sourceNavigateAngularPage().click(CANCEL);

        //Section Tab
        sourceNavigateAngularPage().click(format(RENDITION_ROW_PATTERN, 0));
        sourceNavigateAngularTabsPage().click(SourceNavigateAngularTabsPageElements.SECTION_TAB);
        sourceNavigateAngularTabsPage().waitForPageLoaded();
        sourceNavigateAngularPage().click(format(SECTION_ROW, 0));
        sourceNavigateAngularSectionPage().rightClickSection(String.format(SECTION_ROW, "0"));
        sourceNavigateAngularPage().clickContextSubMenuItem(EDIT, SECTION_NOTES);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        assertThatInstructionNotesWindowIsDisplayed(EDIT_SECTION_INSTRUCTION_NOTES,"Edit Section Instruction Notes");
        sourceNavigateAngularSectionPage().sectionLockedByUsername();
        boolean sectionLevelIsReadOnly = sourceNavigateAngularSectionPage().getSectionLevelInstructionsReadOnly();
        sourceNavigateAngularPage().click(CANCEL);

        //Delta Tab
        sourceNavigateAngularTabsPage().click(SourceNavigateAngularTabsPageElements.DELTA_TAB);
        sourceNavigateAngularDeltaPage().rightClickDelta(String.format(DELTA_ROW, "0"));
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        sourceNavigateAngularPage().clickContextSubMenuItem(EDIT, DELTA_NOTES);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        assertThatInstructionNotesWindowIsDisplayed(EDIT_DELTA_INSTRUCTION_NOTES,"Edit Delta Instruction Notes");
        sourceNavigateAngularDeltaPage().deltaLockedByUsername();
        boolean deltaLevelIsReadOnly = sourceNavigateAngularDeltaPage().getDeltaLevelInstructionsReadOnly();
        Assertions.assertTrue(renditionLevelIsReadOnly && sectionLevelIsReadOnly && deltaLevelIsReadOnly, "Instruction Notes are read only");
        sourceNavigateAngularPage().click(CANCEL);
        sourceNavigateAngularTabsPage().click(RENDITION_TAB);
        //Commented by Sree as Locked report page is no longer available in the UI
        //Manual cleanup may need for this
        // Unlock UUID
        /*sourceNavigateAngularTabsPage().clickLockReportTab();
        sourceNavigateAngularTabsPage().waitForPageLoaded();
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        sourceNavigateAngularLockReportPage().clickOnColumnHeaderFilterButton("lockedDocument");
        sourceNavigateAngularLockReportPage().enterInputInFilterInputBox("2021 EM EM HLT-46-21-00004-E");
        assertThatRenditionIsLocked(true);
        sourceNavigateAngularLockReportPage().rightClickFirstLockedRendition();
        sourceNavigateAngularLockReportPage().clickUnlock_ForceUnlock_TransferLock("Unlock");
        sourceNavigateAngularLockReportPage().waitForPageLoaded();
*/
        //Click on Renditions tab
        sourceNavigateAngularTabsPage().click(RENDITION_TAB);
    }

    /**
     * User Story 722021: [HALCYONST-15653] [CMI] Renditions - Edit - Effective Date
     * User Story 723077:[HALCYONST-17027] [CMI]Section - Edit - Effective Date
     * User Story 723232: [HALCYONST-17225] [CMI]Delta: Effective Date
     * User Story 725284: Effective date year picker should be ranged from 1999 to current year
     **/
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void setEffectiveDateOnSingleDocumentAndRunCiteLocateUpdateIntegrationStatusForRenditionsSectionsAndDeltas() {
        String renditionUuid = "I00298EF0F15311E3B335C9F4DD682AE9"; //"I000FF260102A11EE8CB0DA5B0EA6931A";

        sourceNavigateAngularLeftSidePanePage().clickFindButtonOnLeftPane();
        sourceNavigateAngularLeftSidePanePage().setFindValue("Rendition UUID", renditionUuid);
        sourceNavigateAngularLeftSidePanePage().clickFindButton();

        sourceNavigateAngularPage().rightClick(FIRST_RENDITION_ROW);
        sourceNavigateAngularPage().clickContextSubMenuItem(EDIT, CONTEXT_MENU_EFFECTIVE_DATE);
        sourceNavigateAngularPage().sendTextToTextbox(EFFECTIVE_DATE_INPUT, DateAndTimeUtils.getCurrentDateMMddyyyyNoDelimeters());

        sourceNavigateAngularPage().click(RUN_CITE_LOCATE_INTEGRATION_STATUS_TOGGLE_BUTTON);
        sourceNavigateAngularPage().click(SUBMIT);
        sourceNavigateAngularPage().waitForPageLoaded();
        sourceNavigateAngularPage().goToWorkflowReportingSystem();
        assertThatVerifyWorkflowType(String.valueOf(firstWorkflowIdXpath), "CwbCiteLocate");
        workflowSearchPage().closeWorkflowSearchPage();
        workflowSearchPage().switchToWindow(PAGE_TITLE);

        sourceNavigateAngularPage().click(DELTA_TAB);
        sourceNavigateAngularPage().rightClick((format(DELTA_ROW, 0)));
        sourceNavigateAngularPage().clickContextSubMenuItem(EDIT, CONTEXT_MENU_EFFECTIVE_DATE);
        sourceNavigateAngularPage().sendTextToTextbox(EFFECTIVE_DATE_INPUT, DateAndTimeUtils.getCurrentDateMMddyyyyNoDelimeters());

        sourceNavigateAngularPage().click(RUN_CITE_LOCATE_INTEGRATION_STATUS_TOGGLE_BUTTON);
        sourceNavigateAngularPage().click(SUBMIT);
        sourceNavigateAngularPage().waitForPageLoaded();
        sourceNavigateAngularPage().goToWorkflowReportingSystem();
        assertThatVerifyWorkflowType(String.valueOf(firstWorkflowIdXpath), "CwbCiteLocate");
        workflowSearchPage().closeWorkflowSearchPage();
        workflowSearchPage().switchToWindow(PAGE_TITLE);
        sourceNavigateAngularTabsPage().click(RENDITION_TAB);
    }
  
     /**
     * Scenario#3 :
     *Access Source Navigate UI app > Click 'Renditions' > Enable "Difficulty Level" column via the "Columns" UI on the left side pane for each tab
     * Select any not locked PREP rendition without any previously set Difficulty Level > Right-click the rendition > Click "Edit -> Difficulty Level" > Click the drop-down list > Select "M1" from the drop-down list > Click "Cancel" button	-	The "Difficulty Level" UI is closed
     * -	The grid was not refreshed
     * -	The "Difficulty Level" column is empty for the selected Rendition
     *
     * Click "Refresh page" button on the upper right corner of the grid	-	The "Difficulty Level" column is empty for the selected Rendition
     * Go to Section tab	-	The "Difficulty Level" column is empty for all the Sections
     *  Right-click the first Section > Click "Edit -> Difficulty level" > Click the drop-down list > Select "M3" from the drop-down list > Click "Cancel" button	-	The "Difficulty Level" UI is closed
     * -	The grid was not refreshed
     * -	The "Difficulty Level" column is empty for all Sections
     * Click "Refresh page" button on the upper right corner of the grid	The "Difficulty Level" column is empty for all Sections
     * Go to Delta tab	The "Difficulty Level" column is empty for all the Deltas
     *  Right-click the first Delta > Click "Edit -> Difficulty level" > Click the drop-down list > Select "E2" from the drop-down list > Click "Cancel" button	-	The "Difficulty Level" UI is closed
     * -	The grid was not refreshed
     * -	The "Difficulty Level" column is empty for all Deltas
     * Click "Refresh page" button on the upper right corner of the grid	The "Difficulty Level" column is empty for all Deltas
     **/
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void verifyCancelDifficultyLevel()
    {
        String renditionUuid = "I14E18BE06B4311EDB32DE1D870F94779"; // This must be a PREP Doc uuid
        String diffLevel1 = "M1";
        String diffLevel2 = "M3";
        String diffLevel3 = "E2";
        String defaultDiffLevel = "";
        final List<String> expectedDropDownOptions = Arrays.asList("", "E1", "E2", "E3", "M1", "M2", "M3");

        //Find a rendition that is NOT currently locked
        sourceNavigateAngularLeftSidePanePage().clickFindButtonOnLeftPane();
        sourceNavigateAngularLeftSidePanePage().setFindValue("Rendition UUID", renditionUuid);
        sourceNavigateAngularLeftSidePanePage().clickFindButton();
        sourceNavigateAngularPage().waitForElementExists(format(TOTAL_RENDITIONS_NUMBER, "1"));


        //Add Difficulty Level column to the view from left panel Columns options
        sourceNavigateAngularLeftSidePanePage().accessColumnsInterface();
        sourceNavigateAngularLeftSidePanePage().filterForColumnAndSelect("Difficulty Level");
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);

        //Right click Edit--> Difficulty Level
        sourceNavigateAngularPage().rightClickRenditions();
        sourceNavigateAngularPage().clickContextSubMenuItem(EDIT, DIFFICULTY_LEVEL);
        sourceNavigateAngularPage().waitForPageLoaded();

        //Assert that Update Difficulty Level window gets opened
        assertThatExpectedElementPresent(String.format(SourceNavigateAngularPopUpPageElements.UI_TITLE_PATTERN, "Update Difficulty Level"));

        //Switch to Update Difficulty Level window and verify the dropdown values
        assertThatDifficultyLevelWindowElements();
        assertThatDifficultyLevelDropdownOptionsPresent(expectedDropDownOptions);

        //Select M1 value from dropdown and click Submit
        sourceNavigateAngularPopUpPage().selectDropdownOption(DIFFICULTY_LEVEL_DROPDOWN, diffLevel1);
        sourceNavigateAngularPopUpPage().click(String.format(ANY_BUTTON, "Cancel"));
        sourceNavigateAngularPage().waitForPageLoaded();

        //Verify that Difficulty Level column is updated with the value M1 in Renditions grid
        sourceNavigateAngularPage().scrollPageHorizontallyShiftEnd(FIRST_RENDITION_ROW);
        assertThatElementNotPresent(FIRST_RENDITION_ROW + String.format(COLUMN_ID_PATTERN, "difficultyLevel"), diffLevel1);

        //Click on Section tab
        sourceNavigateAngularTabsPage().click(SourceNavigateAngularTabsPageElements.SECTION_TAB);
        sourceNavigateAngularTabsPage().waitForPageLoaded();

        //Add Difficulty column from left panel options
        sourceNavigateAngularLeftSidePanePage().accessColumnsInterface("SECTION");
        sourceNavigateAngularLeftSidePanePage().filterForColumnAndSelectUnderSpecificTab("SECTION", "Difficulty Level");
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);

        //Right click on the first section --> Edit --> Difficulty Level
        sourceNavigateAngularSectionPage().rightClickSection(String.format(SECTION_ROW, "0"));

        //Right click to access Difficulty Level modal again
        sourceNavigateAngularPage().clickContextSubMenuItem(EDIT, DIFFICULTY_LEVEL);
        sourceNavigateAngularPage().waitForPageLoaded();

        //Assert that Update Difficulty Level window gets opened
        assertThatExpectedElementPresent(String.format(SourceNavigateAngularPopUpPageElements.UI_TITLE_PATTERN, "Update Difficulty Level"));

        //Assert all page elements in Difficulty Level modal
        assertThatDifficultyLevelWindowElements();

        //Select M3 from the dropdown and click on Submit
        sourceNavigateAngularPopUpPage().selectDropdownOption(DIFFICULTY_LEVEL_DROPDOWN, diffLevel2);
        sourceNavigateAngularPopUpPage().click(String.format(ANY_BUTTON, "Cancel"));
        sourceNavigateAngularPage().waitForPageLoaded();
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);

        //Verify that Difficulty Level column is updated with the value M1 in Sections grid for first row
        sourceNavigateAngularPage().scrollPageHorizontallyShiftEnd(String.format(SECTION_ROW, "0"));
        sourceNavigateAngularPage().scrollToTop();
        sourceNavigateAngularPage().click(String.format(SECTION_ROW, "0"));
        assertThatElementNotPresent(String.format(SECTION_ROW, "0") + String.format(COLUMN_ID_PATTERN, "difficultyLevel"), diffLevel2);

        //Click on Delta tab
        sourceNavigateAngularTabsPage().click(SourceNavigateAngularTabsPageElements.DELTA_TAB);
        sourceNavigateAngularTabsPage().waitForPageLoaded();

        //Add Difficulty column from left panel options in Delta tab
        sourceNavigateAngularLeftSidePanePage().accessColumnsInterface("DELTA");
        sourceNavigateAngularLeftSidePanePage().filterForColumnAndSelectUnderSpecificTab("DELTA", "Difficulty Level");
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);

        //Right click on the first Delta --> Edit --> Difficulty Level
        sourceNavigateAngularDeltaPage().click(String.format(DELTA_ROW, "0"));
        sourceNavigateAngularDeltaPage().rightClickDelta(String.format(DELTA_ROW, "0"));
        sourceNavigateAngularPage().clickContextSubMenuItem(EDIT, DIFFICULTY_LEVEL);
        sourceNavigateAngularPage().waitForPageLoaded();

        //Assert all page elements in Difficulty Level modal
        assertThatDifficultyLevelWindowElements();

        //Select M3 from the dropdown and click on Submit
        sourceNavigateAngularPopUpPage().selectDropdownOption(DIFFICULTY_LEVEL_DROPDOWN, diffLevel3);
        sourceNavigateAngularPopUpPage().click(String.format(ANY_BUTTON, "Cancel"));
        sourceNavigateAngularPage().waitForPageLoaded();
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);

        //Verify that Difficulty Level column is updated with the value E2 in Delta grid for first row
        sourceNavigateAngularPage().scrollPageHorizontallyShiftEnd(String.format(DELTA_ROW, "0"));
        assertThatElementNotPresent((String.format(DELTA_ROW, "0") + String.format(COLUMN_ID_PATTERN, "difficultyLevel")), diffLevel3);

        //Click on Renditions tab for AfterEach to get executed successfully
        sourceNavigateAngularTabsPage().click(RENDITION_TAB);
    }

    /**
     * Scenario#2: Difficulty Level functionality – multiple items selection (MANUALTEST)
     *
     **/
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void verifyDifficultyLevelMultipleItem()
    {
        String renditionUuid1 = "I0D62F8404A3711EDAC6C506B8DC11347"; // This must be a PREP Doc uuid
        String renditionUuid2 = "I0D95A1004A3711EDAC6C506B8DC11347"; // This must be a PREP Doc uuid
        renditionUuid = "I01BA3D30329211ED9E2D95E21AD6B885,I042A1ED012A011ED9120F3D8B757A28B";
        String diffLevel1 = "M1";
        String diffLevel2 = "M3";
        String diffLevel3 = "E2";
        String defaultDiffLevel = "";
        final List<String> expectedDropDownOptions = Arrays.asList("", "E1", "E2", "E3", "M1", "M2", "M3");

        try
        {
            //Find a rendition that is NOT currently locked
            sourceNavigateAngularLeftSidePanePage().clickFindButtonOnLeftPane();
            sourceNavigateAngularLeftSidePanePage().setFindValue("Rendition UUID","I01BA3D30329211ED9E2D95E21AD6B885,I042A1ED012A011ED9120F3D8B757A28B");
            sourceNavigateAngularLeftSidePanePage().clickFindButton();
            sourceNavigateAngularPage().waitForElementExists(format(TOTAL_RENDITIONS_NUMBER, "2"));

            //Right click Edit--> Difficulty Level
            sourceNavigateAngularPage().selectTwoRenditions((format(RENDITION_ROW_PATTERN, 0)),(format(RENDITION_ROW_PATTERN, 1)));
            sourceNavigateAngularPage().rightClick(FIRST_RENDITION_ROW);;
            sourceNavigateAngularPage().clickContextMenuItem(EDIT);
            sourceNavigateAngularPage().clickContextMenuItem(DIFFICULTY_LEVEL);
            sourceNavigateAngularPage().waitForPageLoaded();

            //Assert that Update Difficulty Level window gets opened
            assertThatExpectedElementPresent(String.format(SourceNavigateAngularPopUpPageElements.UI_TITLE_PATTERN, "Update Difficulty Level"));

            //Switch to Update Difficulty Level window and verify the dropdown values
            assertThatDifficultyLevelWindowElements();
            assertThatDifficultyLevelDropdownOptionsPresent(expectedDropDownOptions);

            //Select M1 value from dropdown and click Submit
            sourceNavigateAngularPopUpPage().selectDropdownOption(DIFFICULTY_LEVEL_DROPDOWN, diffLevel1);
            sourceNavigateAngularPopUpPage().click(String.format(ANY_BUTTON, "Submit"));
            sourceNavigateAngularPage().waitForPageLoaded();
            DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);

            //Add Difficulty Level column to the view from left panel Columns options
            sourceNavigateAngularLeftSidePanePage().clickFindButtonOnLeftPane();
            sourceNavigateAngularLeftSidePanePage().accessColumnsInterface();
            DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
            sourceNavigateAngularLeftSidePanePage().filterForColumnAndSelect("Difficulty Level");
            DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);

            //Verify that Difficulty Level column is updated with the value M1 in Renditions grid
            sourceNavigateAngularPage().scrollPageHorizontallyShiftEnd(FIRST_RENDITION_ROW);
            assertThatDifficultyLevelSelectedValueinGrid(FIRST_RENDITION_ROW + String.format(COLUMN_ID_PATTERN, "difficultyLevel"), diffLevel1);
            assertThatDifficultyLevelSelectedValueinGrid(String.format(ANY_RENDITION_ROW, 1) + String.format(COLUMN_ID_PATTERN, "difficultyLevel"), diffLevel1);

            sourceNavigateAngularPage().selectTwoRenditions((format(RENDITION_ROW_PATTERN, 0)),(format(RENDITION_ROW_PATTERN, 1)));

            //Click on Section tab
            sourceNavigateAngularTabsPage().click(SourceNavigateAngularTabsPageElements.SECTION_TAB);
            sourceNavigateAngularTabsPage().waitForPageLoaded();

            //Right click on the 2 sections --> Edit --> Difficulty Level
            sourceNavigateAngularSectionPage().selectTwoSections(String.format(SECTION_ROW, "0"), String.format(SECTION_ROW, "1"));
            sourceNavigateAngularSectionPage().rightClickSection(String.format(SECTION_ROW, "0"));

            //Right click to access Difficulty Level modal again
            sourceNavigateAngularPage().clickContextSubMenuItem(EDIT, DIFFICULTY_LEVEL);
            sourceNavigateAngularPage().waitForPageLoaded();

            //Assert that Update Difficulty Level window gets opened
            assertThatExpectedElementPresent(String.format(SourceNavigateAngularPopUpPageElements.UI_TITLE_PATTERN, "Update Difficulty Level"));

            //Assert all page elements in Difficulty Level modal
            assertThatDifficultyLevelWindowElements();
            assertThatDifficultyLevelDropdownOptionsPresent(expectedDropDownOptions);

            //Select M3 from the dropdown and click on Submit
            sourceNavigateAngularPopUpPage().selectDropdownOption(DIFFICULTY_LEVEL_DROPDOWN, diffLevel2);
            sourceNavigateAngularPopUpPage().click(String.format(ANY_BUTTON, "Submit"));
            sourceNavigateAngularPage().waitForPageLoaded();
            DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);

            //Add Difficulty column from left panel options
            sourceNavigateAngularLeftSidePanePage().accessColumnsInterface("SECTION");
            sourceNavigateAngularLeftSidePanePage().filterForColumnAndSelectUnderSpecificTab("SECTION", "Difficulty Level");
            DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);

            //Verify that Difficulty Level column is updated with the value M1 in Sections grid for first and second rows
            sourceNavigateAngularPage().scrollPageHorizontallyShiftEnd(String.format(SECTION_ROW, "0"));
            sourceNavigateAngularPage().scrollToTop();
            sourceNavigateAngularPage().click(String.format(SECTION_ROW, "0"));
            assertThatDifficultyLevelSelectedValueinGrid((String.format(SECTION_ROW, "0") + String.format(COLUMN_ID_PATTERN, "difficultyLevel")), diffLevel2);
            assertThatDifficultyLevelSelectedValueinGrid((String.format(SECTION_ROW, "1") + String.format(COLUMN_ID_PATTERN, "difficultyLevel")), diffLevel2);

            //Verify that all other sections except first one shows the Difficulty level value as M1
            sourceNavigateAngularSectionPage().selectTwoSections(String.format(SECTION_ROW, "0"), String.format(SECTION_ROW, "1"));

            //Click on Delta tab
            sourceNavigateAngularTabsPage().click(SourceNavigateAngularTabsPageElements.DELTA_TAB);
            sourceNavigateAngularTabsPage().waitForPageLoaded();

            //Right click on the first Delta --> Edit --> Difficulty Level
            sourceNavigateAngularDeltaPage().selectTwoDeltas(String.format(DELTA_ROW, "0"), String.format(DELTA_ROW, "1"));
            sourceNavigateAngularDeltaPage().rightClickDelta(String.format(DELTA_ROW, "0"));
            sourceNavigateAngularPage().clickContextSubMenuItem(EDIT, DIFFICULTY_LEVEL);
            sourceNavigateAngularPage().waitForPageLoaded();

            //Assert that Update Difficulty Level window gets opened
            assertThatExpectedElementPresent(String.format(SourceNavigateAngularPopUpPageElements.UI_TITLE_PATTERN, "Update Difficulty Level"));

            //Assert all page elements in Difficulty Level modal
            assertThatDifficultyLevelWindowElements();
            assertThatDifficultyLevelDropdownOptionsPresent(expectedDropDownOptions);

            //Select M3 from the dropdown and click on Submit
            sourceNavigateAngularPopUpPage().selectDropdownOption(DIFFICULTY_LEVEL_DROPDOWN, diffLevel3);
            sourceNavigateAngularPopUpPage().click(String.format(ANY_BUTTON, "Submit"));
            sourceNavigateAngularPage().waitForPageLoaded();
            DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);

            //Add Difficulty column from left panel options in Delta tab
            sourceNavigateAngularLeftSidePanePage().accessColumnsInterface("DELTA");
            sourceNavigateAngularLeftSidePanePage().filterForColumnAndSelectUnderSpecificTab("DELTA", "Difficulty Level");
            DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);

            //Verify that Difficulty Level column is updated with the value E2 in Delta grid for first row
            sourceNavigateAngularPage().scrollPageHorizontallyShiftEnd(String.format(DELTA_ROW, "0"));
            assertThatDifficultyLevelSelectedValueinGrid((String.format(DELTA_ROW, "0") + String.format(COLUMN_ID_PATTERN, "difficultyLevel")), diffLevel3);
            assertThatDifficultyLevelSelectedValueinGrid((String.format(DELTA_ROW, "1") + String.format(COLUMN_ID_PATTERN, "difficultyLevel")), diffLevel3);
        }
        finally
        {
            //------------------Clean up ---------------------------------------------------//
            //Go back to Rendition tab and ensure that there is no E2
            sourceNavigateAngularTabsPage().click(RENDITION_TAB);
            sourceNavigateAngularTabsPage().waitForPageLoaded();
            sourceNavigateAngularPage().click(FIRST_RENDITION_ROW);
            sourceNavigateAngularPage().rightClick(FIRST_RENDITION_ROW);
            sourceNavigateAngularPage().clickContextSubMenuItem(EDIT, DIFFICULTY_LEVEL);
            sourceNavigateAngularPage().waitForPageLoaded();
            //Select defaultDiffLevel value from dropdown and click Submit
            sourceNavigateAngularPopUpPage().selectDropdownOption(DIFFICULTY_LEVEL_DROPDOWN, defaultDiffLevel);
            sourceNavigateAngularPopUpPage().click(String.format(ANY_BUTTON, "Submit"));
            sourceNavigateAngularPage().waitForPageLoaded();
            DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);

            sourceNavigateAngularPage().click(String.format(ANY_RENDITION_ROW, 1));
            sourceNavigateAngularPage().rightClick(String.format(ANY_RENDITION_ROW, 1));
            sourceNavigateAngularPage().breakOutOfFrame();
            sourceNavigateAngularPage().clickContextSubMenuItem(EDIT, DIFFICULTY_LEVEL);
            sourceNavigateAngularPage().waitForPageLoaded();
            //Select defaultDiffLevel value from dropdown and click Submit
            sourceNavigateAngularPopUpPage().selectDropdownOption(DIFFICULTY_LEVEL_DROPDOWN, defaultDiffLevel);
            sourceNavigateAngularPopUpPage().click(String.format(ANY_BUTTON, "Submit"));
            sourceNavigateAngularPage().waitForPageLoaded();
            DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
            sourceNavigateAngularPage().scrollPageHorizontallyShiftEnd(FIRST_RENDITION_ROW);
            assertThatDifficultyLevelSelectedValueinGrid(FIRST_RENDITION_ROW + String.format(COLUMN_ID_PATTERN, "difficultyLevel"), defaultDiffLevel);
            assertThatDifficultyLevelSelectedValueinGrid(String.format(ANY_RENDITION_ROW, 1) + String.format(COLUMN_ID_PATTERN, "difficultyLevel"), defaultDiffLevel);
        }
    }


    /**
     * Access Source Navigate UI app > Click Renditions > Right-click any locked rendition > Click Modify -> Assign User	-	
     * The toast message appears: Error: Locked by: <username> on <mm/dd/yyyy> - The Assign User UI does not open
     **/
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void assignUserToLockedRendition()
    {
        String rendition_uuid = "I010E8F200FA511E5BCA8C9C17001CA39";
        String userName1 = "TLE TCBA-BOT";

        //Find a rendition that is NOT currently locked
        sourceNavigateAngularLeftSidePanePage().clickFindButtonOnLeftPane();
        sourceNavigateAngularLeftSidePanePage().setFindValue("Rendition UUID", rendition_uuid);
        sourceNavigateAngularLeftSidePanePage().clickFindButton();
        sourceNavigateAngularPage().waitForElementExists(format(TOTAL_RENDITIONS_NUMBER, "1"));

        //Lock the rendition from UI
        sourceNavigateAngularPage().click(FIRST_RENDITION_ROW);
        sourceNavigateAngularPage().rightClickRenditions();
        sourceNavigateAngularPage().clickContextSubMenuItem(EDIT, RENDITION);
        sourceNavigateAngularPage().switchToWindow(EditorPageElements.PAGE_TITLE);
        editorPage().waitForPageLoaded();
        editorPage().waitForElementGone(EditorPageElements.COMMAND_IN_PROGRESS);
        editorPage().waitForElement(EditorToolbarPageElements.CLOSE_DOC);
        editorPage().closeCurrentWindowIgnoreDialogue();

        //Refresh the grid
        editorPage().switchToWindow(SourceNavigateAngularPageElements.PAGE_TITLE);
        sourceNavigateAngularPage().clickRefreshTableData();

        sourceNavigateAngularLeftSidePanePage().filterForColumnAndSelect("Assigned User");
        sourceNavigateAngularLeftSidePanePage().filterForColumnAndSelect("Assigned Date");
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);

        //Right click Modify--> AssignUser
        sourceNavigateAngularPage().rightClickRenditions();
        sourceNavigateAngularPage().clickContextSubMenuItem(MODIFY, ASSIGN_USER);
        sourceNavigateAngularPage().waitForPageLoaded();
        DateAndTimeUtils.takeNap(DateAndTimeUtils.THREE_SECONDS);
        String message = "Error: Locked by: " + userName1 + " on";
        assertThatMessage(String.format(ERROR_TOAST_MESSAGE, message),message + " " + DateAndTimeUtils.getCurrentDateMMddyyyy());
        sourceNavigateAngularTabsPage().click(RENDITION_TAB);
    }
    
    /**
     * Create/Edit/Delete Source Instructions for Rendition Level Documents
     **/
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void verifyCreate_Delete_InstructionTest()
    {
        String text = "QEDTESTING";
        String renditionUuid = "I0072218042BB11EC83838BEC591E7AC1"; // This must be a PREP Doc uuid
        sourceNavigateAngularLeftSidePanePage().clickFindButtonOnLeftPane();
        sourceNavigateAngularLeftSidePanePage().setFindValue("Rendition UUID", renditionUuid);
        sourceNavigateAngularLeftSidePanePage().clickFindButton();
        sourceNavigateAngularPage().waitForElementExists(format(TOTAL_RENDITIONS_NUMBER, "1"));
        sourceNavigateAngularPage().isElementDisplayed(NOTES_VALUE_OF_RENDITIONS);
        assertThatMessage(NOTES_VALUE_OF_RENDITIONS, "Add");
        sourceNavigateAngularPage().click(NOTES_VALUE_OF_RENDITIONS);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        sourceNavigateAngularPage().waitForPageLoaded();
        //Adding Instruction note
        sourceNavigateAngularRenditionPage().addRenditionNotes(text);
        sourceNavigateAngularRenditionPage().click(submit);
        sourceNavigateAngularRenditionPage().waitForPageLoaded();
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);

        // Verify Instruction value
        assertThatMessage(FIRST_ROW_INSTRUCTION_NOTE, text);
        assertThatMessage(NOTES_VALUE_OF_RENDITIONS, "R");
        sourceNavigateAngularPage().click(NOTES_VALUE_OF_RENDITIONS);

        //Delete the InstructionNote
        sourceNavigateAngularRenditionPage().clearRenditionNotes(SourceNavigateAngularRenditionPageElements.renditionLevelinstructions);
        sourceNavigateAngularRenditionPage().click(submit);
        sourceNavigateAngularRenditionPage().waitForPageLoaded();
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        assertThatMessage(NOTES_VALUE_OF_RENDITIONS, "Add");
    }

  /*
    Access Source Navigate UI app > Click Renditions > Select one or more APV renditions with the single Baseline > Right-click on selected rendition(s) > Click Report -> Run Compare Report	The pop-up message Workflow was started: <Workflow ID hyperlink> shows up
    Click Workflow ID link	-	Workflow Properties page opens
    -	The workflow with given <Workflow ID> is created and finished with no errors
    Close Workflow Properties page > Go to Source Navigate Rendition tab > Go to Delta tab > Right-click one or more deltas > Click Report -> Run Compare Report	The pop-up message ‘Workflow was started: <Workflow ID hyperlink> shows up
    Click Workflow ID link	-	Workflow Properties page opens
    -	The workflow with given <Workflow ID> is created and finished with no errors
     */
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void verifyRunCompareReport() {
        String renditionStatus = "APV";
        sourceNavigateAngularFiltersAndSortsPage().openMenuColumnHeader(SourceNavigateAngularPageElements.RENDITION_STATUS);
        sourceNavigateAngularFiltersAndSortsPage().openFilterTabColumnHeader();
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        sourceNavigateAngularFiltersAndSortsPage().clickCheckbox(SELECT_ALL);
        sourceNavigateAngularFiltersAndSortsPage().setRenditionStatus(FILTER_RENDITION_STATUS, renditionStatus);
        sourceNavigateAngularFiltersAndSortsPage().clickCheckbox(renditionStatus);
        sourceNavigateAngularPage().isElementDisabled(NOTES_VALUE_OF_RENDITIONS);
        sourceNavigateAngularPage().click(FIRST_RENDITION_ROW);

        sourceNavigateAngularPage().rightClickRenditions();
        sourceNavigateAngularPage().clickContextMenuItem(REPORT);
        sourceNavigateAngularPage().clickContextMenuItem(RUN_COMPARE_REPORT);

        assertThatMessage(ASSIGNED_USER_MESSAGE," was created by something other than source input. Do you wish to continue?");
        sourceNavigateAngularPage().click(CONFIRM_BUTTON);

        assertThatMessage(TOAST_MESSAGE,"Workflow was started:");

        DateAndTimeUtils.takeNap(DateAndTimeUtils.THREE_SECONDS);

        boolean workflowSearchPageAppears = sourceNavigateAngularPage().goToWorkflowReportingSystem();
        Assertions.assertTrue(workflowSearchPageAppears, "The Workflow Search page does not appear when it should");

        boolean workflowFinished = workflowSearchPage().filterWorkflowAndVerifyStatus("CwbSourceCompare", "", "Source to Target Compare",  "");
        Assertions.assertTrue(workflowFinished, "The Workflow Search is not finished");

        sourceNavigateAngularPage().switchToWindow("SourceNavigateUi");

        //Click on Delta tab
        sourceNavigateAngularTabsPage().click(SourceNavigateAngularTabsPageElements.DELTA_TAB);
        sourceNavigateAngularTabsPage().waitForPageLoaded();

        //Right click on the first Delta --> Report --> Run Compare Report
        sourceNavigateAngularDeltaPage().click(String.format(DELTA_ROW, "0"));
        sourceNavigateAngularDeltaPage().rightClickDelta(String.format(DELTA_ROW, "0"));

        sourceNavigateAngularPage().clickContextMenuItem(REPORT);
        sourceNavigateAngularPage().clickContextMenuItem(RUN_COMPARE_REPORT);

        assertThatMessage(ASSIGNED_USER_MESSAGE," was created by something other than source input. Do you wish to continue?");
        sourceNavigateAngularPage().click(CONFIRM_BUTTON);

        assertThatMessage(TOAST_MESSAGE,"Workflow was started:");

        DateAndTimeUtils.takeNap(DateAndTimeUtils.THREE_SECONDS);

        boolean workflowSearchPageDeltaAppears = sourceNavigateAngularPage().goToWorkflowReportingSystem();
        Assertions.assertTrue(workflowSearchPageAppears, "The Workflow Search page does not appear when it should");

        boolean workflowFinishedDelta = workflowSearchPage().filterWorkflowAndVerifyStatus("CwbSourceCompare", "", "Source to Target Compare",  "");
        Assertions.assertTrue(workflowFinishedDelta, "workflow didn't finish");

        sourceNavigateAngularPage().switchToWindow("SourceNavigateUi");

        sourceNavigateAngularTabsPage().click(RENDITION_TAB);
    }
    /*
    Access Source Navigate UI app > Click ‘Renditions’ > Select one or more APV renditions with the single Baseline > Right-click on selected rendition(s) > Click Report -> Run Compare and Markup Report	The pop-up message ‘Workflow was started: <Workflow ID hyperlink>’ shows up
    Click Workflow ID link	-	Workflow Properties page opens
    -	The workflow with given <Workflow ID> is created and finished with no errors
    Close Workflow Properties page > Go to Source Navigate Rendition tab > Go to Delta tab > Right-click one or more deltas > Click Report -> Run Compare Report	The pop-up message ‘Workflow was started: <Workflow ID hyperlink>’ shows up
    Click Workflow ID link	-	Workflow Properties page opens
    -	The workflow with given <Workflow ID> is created and finished with no errors
    */
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void verifyRunCompareAndMarkupReport() {
        String renditionStatus = "APV";
        sourceNavigateAngularFiltersAndSortsPage().openMenuColumnHeader(SourceNavigateAngularPageElements.RENDITION_STATUS);
        sourceNavigateAngularFiltersAndSortsPage().openFilterTabColumnHeader();
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        sourceNavigateAngularFiltersAndSortsPage().clickCheckbox(SELECT_ALL);
        sourceNavigateAngularFiltersAndSortsPage().setRenditionStatus(FILTER_RENDITION_STATUS, renditionStatus);
        sourceNavigateAngularFiltersAndSortsPage().clickCheckbox(renditionStatus);
        sourceNavigateAngularPage().isElementDisabled(NOTES_VALUE_OF_RENDITIONS);
        sourceNavigateAngularPage().click(FIRST_RENDITION_ROW);

        sourceNavigateAngularPage().rightClickRenditions();
        sourceNavigateAngularPage().clickContextMenuItem(REPORT);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.ONE_SECOND);
        sourceNavigateAngularPage().clickContextMenuItem(RUN_COMPARE_AND_MARKUP_REPORT);

        assertThatMessage(ASSIGNED_USER_MESSAGE," was created by something other than source input. Do you wish to continue?");
        sourceNavigateAngularPage().click(CONFIRM_BUTTON);

        assertThatMessage(TOAST_MESSAGE,"Workflow was started:");

        DateAndTimeUtils.takeNap(DateAndTimeUtils.THREE_SECONDS);

        boolean workflowSearchPageAppears = sourceNavigateAngularPage().goToWorkflowReportingSystem();
        Assertions.assertTrue(workflowSearchPageAppears, "The Workflow Search page does not appear when it should");

        boolean workflowFinished = workflowSearchPage().filterWorkflowAndVerifyStatus("CwbSourceCompare", "", "Source to Target Compare",  "");
        Assertions.assertTrue(workflowFinished , "The Workflow is not finished");

        sourceNavigateAngularPage().switchToWindow("SourceNavigateUi");

        //Click on Delta tab
        sourceNavigateAngularTabsPage().click(SourceNavigateAngularTabsPageElements.DELTA_TAB);
        sourceNavigateAngularTabsPage().waitForPageLoaded();

        //Right click on the first Delta --> Report --> Run Compare Report
        sourceNavigateAngularDeltaPage().click(String.format(DELTA_ROW, "0"));
        sourceNavigateAngularDeltaPage().rightClickDelta(String.format(DELTA_ROW, "0"));

        sourceNavigateAngularPage().clickContextMenuItem(REPORT);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.ONE_SECOND);
        sourceNavigateAngularPage().clickContextMenuItem(RUN_COMPARE_AND_MARKUP_REPORT);

        assertThatMessage(ASSIGNED_USER_MESSAGE," was created by something other than source input. Do you wish to continue?");
        sourceNavigateAngularPage().click(CONFIRM_BUTTON);

        assertThatMessage(TOAST_MESSAGE,"Workflow was started:");

        DateAndTimeUtils.takeNap(DateAndTimeUtils.THREE_SECONDS);

        boolean workflowSearchPageDeltaAppears = sourceNavigateAngularPage().goToWorkflowReportingSystem();
        Assertions.assertTrue(workflowSearchPageAppears, "The Workflow Search page does not appear when it should");

        boolean workflowFinishedDelta = workflowSearchPage().filterWorkflowAndVerifyStatus("CwbSourceCompare", "", "Source to Target Compare",  "");
        Assertions.assertTrue(workflowFinishedDelta, "workflow didn't finish");

        sourceNavigateAngularPage().switchToWindow("SourceNavigateUi");

        sourceNavigateAngularTabsPage().click(RENDITION_TAB);
    }
    /*
    Access Source Navigate UI app > Click ‘Renditions’ > Select one or more locked APV renditions > Right-click on selected rendition(s) > Click Report	The Report -> Run Compare Report and Report -> Run Compare and Markup Report items are disabled and grayed out
    Go to Delta tab > Right-click any locked Delta > Click Report	The Report -> Run Compare Report and Report -> Run Compare and Markup Report items are disabled and grayed out
     */
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void verifyLockedRenditionRunCompareReport() {
        //Find a rendition that is NOT currently locked
        sourceNavigateAngularLeftSidePanePage().clickFindButtonOnLeftPane();
        sourceNavigateAngularLeftSidePanePage().setFindValue("Rendition UUID", renditionUuid);
        sourceNavigateAngularLeftSidePanePage().clickFindButton();
        sourceNavigateAngularPage().waitForElementExists(format(TOTAL_RENDITIONS_NUMBER, "1"));

        //Lock the rendition from UI
        sourceNavigateAngularPage().rightClickRenditions();
        sourceNavigateAngularPage().clickContextSubMenuItem(EDIT, RENDITION);
        sourceNavigateAngularPage().switchToWindow(EditorPageElements.PAGE_TITLE);
        editorPage().waitForPageLoaded();
        editorPage().waitForElementGone(EditorPageElements.COMMAND_IN_PROGRESS);
        editorPage().waitForElement(EditorToolbarPageElements.CLOSE_DOC);
        editorPage().closeCurrentWindowIgnoreDialogue();

        //Refresh the grid
        editorPage().switchToWindow(SourceNavigateAngularPageElements.PAGE_TITLE);
        sourceNavigateAngularPage().clickRefreshTableData();

        sourceNavigateAngularPage().click(FIRST_RENDITION_ROW);

        sourceNavigateAngularPage().rightClickRenditions();
        sourceNavigateAngularPage().clickContextMenuItem(REPORT);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        boolean runCompareAndMarkupIsDisabled =   sourceNavigateAngularPage().isElementDisabled(RUN_COMPARE_AND_MARKUP_REPORT + "/.."); // disabled property is set at the immediate parent level
        boolean runCompareReportIsDisabled= sourceNavigateAngularPage().isElementDisabled(RUN_COMPARE_REPORT + "/..");  // disabled property is set at the immediate parent level

        Assertions.assertTrue(runCompareAndMarkupIsDisabled, "Run Compare and Markup Report is enabled when it should not");
        Assertions.assertTrue(runCompareReportIsDisabled, "Run Compare Report is enabled when it should not");
    }
    /*
    Access Source Navigate UI app > Click ‘Renditions’ > Select one or more APV renditions with the multiple Baselines > Right-click on selected rendition(s) > Click Report -> Run Compare Report	The confirmation window appears: ‘Rendition <rendition UUID> was created by something other than source input. Do you wish to continue?’
    Confirm the action	The pop-up message ‘Workflow was started: <Workflow ID hyperlink>’ shows up
    Click Workflow ID link	-	Workflow Properties page opens
    -	The workflow with given <Workflow ID> is created and finished with no errors
    Close Workflow Properties page > Go to Source Navigate Rendition tab > Go to Delta tab > Right-click one or more deltas > Click Report -> Run Compare Report	The confirmation window appears: ‘Rendition <rendition UUID> was created by something other than source input. Do you wish to continue?’
    Confirm the action	The pop-up message ‘Workflow was started: <Workflow ID hyperlink>’ shows up
    Click Workflow ID link	-	Workflow Properties page opens
    -	The workflow with given <Workflow ID> is created and finished with no errors
     */
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void verifyRunCompareReportAndRunCompareReportForMultipleRenditions() {
        String renditionUuid="I00006D813FFB11E9A15ECFE772ACF6DE,I00015140DFF511EB87EF955706D208B9";
        sourceNavigateAngularLeftSidePanePage().clickFindButtonOnLeftPane();
        sourceNavigateAngularLeftSidePanePage().setFindValue("Rendition UUID", renditionUuid);
        sourceNavigateAngularLeftSidePanePage().clickFindButton();
        String renditionStatus = "APV";
        sourceNavigateAngularFiltersAndSortsPage().openMenuColumnHeader(SourceNavigateAngularPageElements.RENDITION_STATUS);
        sourceNavigateAngularFiltersAndSortsPage().openFilterTabColumnHeader();
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        sourceNavigateAngularFiltersAndSortsPage().clickCheckbox(SELECT_ALL);
        sourceNavigateAngularFiltersAndSortsPage().setRenditionStatus(FILTER_RENDITION_STATUS, renditionStatus);
        sourceNavigateAngularFiltersAndSortsPage().clickCheckbox(renditionStatus);
        sourceNavigateAngularPage().isElementDisabled(NOTES_VALUE_OF_RENDITIONS);
        sourceNavigateAngularPage().selectTwoRenditions((format(RENDITION_ROW_PATTERN, 0)),(format(RENDITION_ROW_PATTERN, 1)));

        sourceNavigateAngularPage().rightClickRenditions();
        sourceNavigateAngularPage().clickContextMenuItem(REPORT);
        sourceNavigateAngularPage().clickContextMenuItem(RUN_COMPARE_REPORT);


        assertThatMessage(ASSIGNED_USER_MESSAGE," was created by something other than source input. Do you wish to continue?");
        sourceNavigateAngularPage().click(CONFIRM_BUTTON);

        assertThatMessage(TOAST_MESSAGE,"Workflow was started:");

        DateAndTimeUtils.takeNap(DateAndTimeUtils.THREE_SECONDS);

        boolean workflowSearchPageAppears = sourceNavigateAngularPage().goToWorkflowReportingSystem();
        Assertions.assertTrue(workflowSearchPageAppears, "The Workflow Search page does not appear when it should");

        boolean workflowFinished = workflowSearchPage().filterWorkflowAndVerifyStatus("CwbSourceCompare", "", "Source to Target Compare",  "");
        Assertions.assertTrue(workflowFinished, "The Workflow is not finished");

        //Check finished workflow has correct idSet
        String workflowId = workflowSearchPage().getWorkflowIdOfFirstWorkflow();
        workflowSearchPage().openWorkflow(workflowId);
        workflowSearchPage().click(WorkflowSearchPageElements.WORKFLOW_PROPERTIES);
        assertThatCorrectRenditionUuids(WorkflowSearchPageElements.WORKFLOW_PROPERTIES_RENDITION_COUNT,renditionUuid);

        sourceNavigateAngularPage().switchToWindow("SourceNavigateUi");

        //Click on Delta tab
        sourceNavigateAngularTabsPage().click(SourceNavigateAngularTabsPageElements.DELTA_TAB);
        sourceNavigateAngularTabsPage().waitForPageLoaded();

        //Right click on the first Delta --> Report --> Run Compare Report
        sourceNavigateAngularPage().selectTwoRenditions((format(DELTA_ROW, 1)),(format(DELTA_ROW, 2)));
        sourceNavigateAngularDeltaPage().rightClickDelta(String.format(DELTA_ROW, "1"));

        sourceNavigateAngularPage().clickContextMenuItem(REPORT);
        sourceNavigateAngularPage().clickContextMenuItem(RUN_COMPARE_REPORT);

        assertThatMessage(ASSIGNED_USER_MESSAGE," was created by something other than source input. Do you wish to continue?");
        sourceNavigateAngularPage().click(CONFIRM_BUTTON);

        assertThatMessage(TOAST_MESSAGE,"Workflow was started:");

        DateAndTimeUtils.takeNap(DateAndTimeUtils.THREE_SECONDS);

        boolean workflowSearchPageDeltaAppears = sourceNavigateAngularPage().goToWorkflowReportingSystem();
        Assertions.assertTrue(workflowSearchPageDeltaAppears, "The Workflow Search page does not appear when it should");

        boolean workflowFinishedDelta = workflowSearchPage().filterWorkflowAndVerifyStatus("CwbSourceCompare", "", "Source to Target Compare",  "");
        Assertions.assertTrue(workflowFinishedDelta, "The Workflow is not finished");

        //Check finished workflow has correct idSet
        String workflowIddelta = workflowSearchPage().getWorkflowIdOfFirstWorkflow();
        workflowSearchPage().openWorkflow(workflowIddelta);
        workflowSearchPage().click(WorkflowSearchPageElements.WORKFLOW_PROPERTIES);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.THREE_SECONDS);
        assertThatTargetLocationUuidsCount(WorkflowSearchPageElements.WORKFLOW_PROPERTIES_TARGET_LOCATION_UUIDS_COUNT, 2);

        sourceNavigateAngularPage().switchToWindow("SourceNavigateUi");

        sourceNavigateAngularTabsPage().click(RENDITION_TAB);
    }
    /*
    Access Source Navigate UI app > Click ‘Renditions’ > Select one or more APV renditions with the multiple Baselines > Right-click on selected rendition(s) > Click Report -> Run Compare and Markup Report	The confirmation window appears: ‘Rendition <rendition UUID> was created by something other than source input. Do you wish to continue?’
    Confirm the action	The pop-up message ‘Workflow was started: <Workflow ID hyperlink>’ shows up
    Click Workflow ID link	-	Workflow Properties page opens
    The workflow with given <Workflow ID> is created and finished with no errors
    Close Workflow Properties page > Go to Source Navigate Rendition tab > Go to Delta tab > Right-click one or more deltas > Click Report -> Run Compare and Markup Report	The confirmation window appears: ‘Rendition <rendition UUID> was created by something other than source input. Do you wish to continue?’
    Confirm the action	The pop-up message ‘Workflow was started: <Workflow ID hyperlink>’ shows up
    Click Workflow ID link	-	Workflow Properties page opens
    The workflow with given <Workflow ID> is created and finished with no errors
     */
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void verifyRunCompareReportAndRunCompareAndMarkupReportForMultipleRenditions() {
        String renditionUuid="I00006D813FFB11E9A15ECFE772ACF6DE,I00015140DFF511EB87EF955706D208B9";
        sourceNavigateAngularLeftSidePanePage().clickFindButtonOnLeftPane();
        sourceNavigateAngularLeftSidePanePage().setFindValue("Rendition UUID", renditionUuid);
        sourceNavigateAngularLeftSidePanePage().clickFindButton();
        String renditionStatus = "APV";
        sourceNavigateAngularFiltersAndSortsPage().openMenuColumnHeader(SourceNavigateAngularPageElements.RENDITION_STATUS);
        sourceNavigateAngularFiltersAndSortsPage().openFilterTabColumnHeader();
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        sourceNavigateAngularFiltersAndSortsPage().clickCheckbox(SELECT_ALL);
        sourceNavigateAngularFiltersAndSortsPage().setRenditionStatus(FILTER_RENDITION_STATUS, renditionStatus);
        sourceNavigateAngularFiltersAndSortsPage().clickCheckbox(renditionStatus);
        sourceNavigateAngularPage().isElementDisabled(NOTES_VALUE_OF_RENDITIONS);
        sourceNavigateAngularPage().selectTwoRenditions((format(RENDITION_ROW_PATTERN, 0)),(format(RENDITION_ROW_PATTERN, 1)));

        sourceNavigateAngularPage().rightClickRenditions();
        sourceNavigateAngularPage().clickContextMenuItem(REPORT);
        sourceNavigateAngularPage().clickContextMenuItem(RUN_COMPARE_AND_MARKUP_REPORT);


        assertThatMessage(ASSIGNED_USER_MESSAGE," was created by something other than source input. Do you wish to continue?");
        sourceNavigateAngularPage().click(CONFIRM_BUTTON);

        assertThatMessage(TOAST_MESSAGE,"Workflow was started:");

        DateAndTimeUtils.takeNap(DateAndTimeUtils.THREE_SECONDS);

        boolean workflowSearchPageAppears = sourceNavigateAngularPage().goToWorkflowReportingSystem();
        Assertions.assertTrue(workflowSearchPageAppears, "The Workflow Search page does not appear when it should");

        boolean workflowFinished = workflowSearchPage().filterWorkflowAndVerifyStatus("CwbSourceCompare", "", "Source to Target Compare",  "");
        Assertions.assertTrue(workflowFinished, "The Workflow is not finished");

        //Check finished workflow has correct idSet
        String workflowId = workflowSearchPage().getWorkflowIdOfFirstWorkflow();
        workflowSearchPage().openWorkflow(workflowId);
        workflowSearchPage().click(WorkflowSearchPageElements.WORKFLOW_PROPERTIES);
        assertThatCorrectRenditionUuids(WorkflowSearchPageElements.WORKFLOW_PROPERTIES_RENDITION_COUNT,renditionUuid);
        workflowSearchPage().closeWorkflowSearchPage();

        sourceNavigateAngularPage().switchToWindow("SourceNavigateUi");

        //Click on Delta tab
        sourceNavigateAngularTabsPage().click(SourceNavigateAngularTabsPageElements.DELTA_TAB);
        sourceNavigateAngularTabsPage().waitForPageLoaded();

        //Right click on the first Delta --> Report --> Run Compare Report
        sourceNavigateAngularPage().selectTwoRenditions((format(DELTA_ROW, 1)),(format(DELTA_ROW, 2)));
        sourceNavigateAngularDeltaPage().rightClickDelta(String.format(DELTA_ROW, "1"));

        sourceNavigateAngularPage().clickContextMenuItem(REPORT);
        sourceNavigateAngularPage().clickContextMenuItem(RUN_COMPARE_AND_MARKUP_REPORT);

        assertThatMessage(ASSIGNED_USER_MESSAGE," was created by something other than source input. Do you wish to continue?");
        sourceNavigateAngularPage().click(CONFIRM_BUTTON);

        assertThatMessage(TOAST_MESSAGE,"Workflow was started:");

        DateAndTimeUtils.takeNap(DateAndTimeUtils.THREE_SECONDS);

        boolean workflowSearchPageDeltaAppears = sourceNavigateAngularPage().goToWorkflowReportingSystem();
        Assertions.assertTrue(workflowSearchPageAppears, "The Workflow Search page does not appear when it should");

        boolean workflowFinishedDelta = workflowSearchPage().filterWorkflowAndVerifyStatus("CwbSourceCompare", "", "Source to Target Compare",  "");
        Assertions.assertTrue(workflowFinishedDelta, "The Workflow is not finished");

        //Check finished workflow has correct idSet
        String workflowIddelta = workflowSearchPage().getWorkflowIdOfFirstWorkflow();
        workflowSearchPage().openWorkflow(workflowIddelta);
        workflowSearchPage().click(WorkflowSearchPageElements.WORKFLOW_PROPERTIES);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.THREE_SECONDS);
        assertThatTargetLocationUuidsCount(WorkflowSearchPageElements.WORKFLOW_PROPERTIES_TARGET_LOCATION_UUIDS_COUNT,2);

        sourceNavigateAngularPage().switchToWindow("SourceNavigateUi");

        sourceNavigateAngularTabsPage().click(RENDITION_TAB);
    }
    /**
     * View Source Instructions of single PREP Renditions/Sections/Deltas (MANUALTEST)
     **/
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void verifyView_Source_InstructionTest(){

        String renditionUuid = "I0072218042BB11EC83838BEC591E7AC1"; // This must be a PREP Doc uuid
        sourceNavigateAngularLeftSidePanePage().clickFindButtonOnLeftPane();
        sourceNavigateAngularLeftSidePanePage().setFindValue("Rendition UUID", renditionUuid);
        sourceNavigateAngularLeftSidePanePage().clickFindButton();
        sourceNavigateAngularPage().waitForElementExists(format(TOTAL_RENDITIONS_NUMBER, "1"));

        //Lock the rendition from UI
        sourceNavigateAngularPage().rightClickRenditions();
        sourceNavigateAngularPage().clickContextSubMenuItem(EDIT, RENDITION);
        sourceNavigateAngularPage().switchToWindow(EditorPageElements.PAGE_TITLE);
        editorPage().waitForPageLoaded();
        editorPage().waitForElementGone(EditorPageElements.COMMAND_IN_PROGRESS);
        editorPage().waitForElement(EditorToolbarPageElements.CLOSE_DOC);
        editorPage().closeCurrentWindowIgnoreDialogue();

        //Refresh the grid
        editorPage().switchToWindow(SourceNavigateAngularPageElements.PAGE_TITLE);
        sourceNavigateAngularPage().clickRefreshTableData();
        //view >Rendition notes
        sourceNavigateAngularPage().click(format(RENDITION_ROW_PATTERN, 0));
        sourceNavigateAngularPage().rightClickRenditions();
        sourceNavigateAngularPage().clickContextSubMenuItem(VIEW, RENDITION_NOTES);
        assertThatInstructionNotesWindowIsDisplayed(VIEW_RENDITION_INSTRUCTION_NOTES,"View Rendition Instruction Notes");
        boolean renditionLevelIsReadOnly = sourceNavigateAngularRenditionPage().getRenditionLevelInstructionsReadOnly();
        sourceNavigateAngularPage().click(CANCEL);
        //section tab
        sourceNavigateAngularPage().click(format(RENDITION_ROW_PATTERN, 0));
        sourceNavigateAngularTabsPage().click(SourceNavigateAngularTabsPageElements.SECTION_TAB);
        sourceNavigateAngularTabsPage().waitForPageLoaded();

        //Add Instruction Note column from left panel options
        sourceNavigateAngularLeftSidePanePage().filterForColumnAndSelectUnderSpecificTab("SECTION", "Instruction Note");
        sourceNavigateAngularPage().click(format(SECTION_ROW, 0));
        sourceNavigateAngularSectionPage().rightClickSection(String.format(SECTION_ROW,"0"));
        sourceNavigateAngularPage().clickContextSubMenuItem(VIEW, SECTION_NOTES);
        assertThatInstructionNotesWindowIsDisplayed(VIEW_SECTION_INSTRUCTION_NOTES,"View Section Instruction Notes");
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        boolean sectionLevelIsReadOnly = sourceNavigateAngularSectionPage().getSectionLevelInstructionsReadOnly();
        sourceNavigateAngularPage().click(CANCEL);

        //Delta Tab
        sourceNavigateAngularTabsPage().click(SourceNavigateAngularTabsPageElements.DELTA_TAB);
        sourceNavigateAngularDeltaPage().rightClickDelta(String.format(DELTA_ROW,"0"));
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        sourceNavigateAngularPage().clickContextSubMenuItem(VIEW, DELTA_NOTES);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        assertThatInstructionNotesWindowIsDisplayed(VIEW_DELTA_INSTRUCTION_NOTES,"View Delta Instruction Notes");
        boolean deltaLevelIsReadOnly = sourceNavigateAngularDeltaPage().getDeltaLevelInstructionsReadOnly();
        Assertions.assertTrue(renditionLevelIsReadOnly && sectionLevelIsReadOnly && deltaLevelIsReadOnly, "Instruction Notes are read only");
        sourceNavigateAngularPage().click(CANCEL);
        sourceNavigateAngularTabsPage().click(RENDITION_TAB);

        //Sree commented this as Lock repot is no longer available in UI
        // Unlock UUID
        /*sourceNavigateAngularTabsPage().clickLockReportTab();
        sourceNavigateAngularTabsPage().waitForPageLoaded();
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        sourceNavigateAngularLockReportPage().clickOnColumnHeaderFilterButton("lockedDocument");
        sourceNavigateAngularLockReportPage().enterInputInFilterInputBox("2021 EM EM HLT-46-21-00004-E");
        assertThatRenditionIsLocked(true);
        sourceNavigateAngularLockReportPage().rightClickFirstLockedRendition();
        sourceNavigateAngularLockReportPage().clickUnlock_ForceUnlock_TransferLock("Unlock");
        sourceNavigateAngularLockReportPage().waitForPageLoaded();*/

        //Click on Renditions tab
        sourceNavigateAngularTabsPage().click(RENDITION_TAB);
    }
    /**
     * User Story 722876: [HALCYONST-16722] [CMI]Rendition Integration Results User Story 722858: [HALCYONST-16686] [CMI]Delta Report - Integration Results User Story 722368: [HALCYONST-16098] [CMI] Renditions and Delta: Integration Results
     */
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void AccessIntegrationResultsOfRenditionsAndDeltas()
    {
        String renditionUuid = "IC77271B075DB11E289DBE4E232D95F35";
        sourceNavigateAngularLeftSidePanePage().clickFindButtonOnLeftPane();
        sourceNavigateAngularLeftSidePanePage().setFindValue("Rendition UUID", renditionUuid);
        sourceNavigateAngularLeftSidePanePage().clickFindButton();

        sourceNavigateAngularPage().click(FIRST_RENDITION_ROW);
        sourceNavigateAngularPage().rightClick(FIRST_RENDITION_ROW);
        sourceNavigateAngularPage().clickContextMenuItem(REPORT);
        sourceNavigateAngularPage().clickContextMenuItem(INTEGRATION_RESULT);
        sourceNavigateAngularPage().breakOutOfFrame();

        sourceNavigateAngularPage().switchToWindow(IntegrationResultsPageElements.INTEGRATION_RESULTS_PAGE_TITLE);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        sourceNavigateAngularPage().openMenuColumnHeaderForTabs(COLUMN_FILTER_BUTTON_INTERGRATION,INTERGRATION_SECTION_NUMBER);
        sourceNavigateAngularPage().openFilterTabColumnHeader();
        sourceNavigateAngularPage().sendTextToTextbox(FILTER_SECTION_NUMBER_INTERGRATION_RESULT,"60");
        sourceNavigateAngularPage().sendEnterToElement(FILTER_SECTION_NUMBER_INTERGRATION_RESULT);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        sourceNavigateAngularPage().doubleClick(COLUMN_FILTER_BUTTON_INTERGRATION);
        assertThatIntergrationSectionNumber(FIRST_SECTION_NUMBER,"60");
        sourceNavigateAngularPage().click(CLOSE_INTEGRATION_RESULT_PAGE);

        sourceNavigateAngularPage().click(DELTA_TAB);
        sourceNavigateAngularPage().selectTwoRenditions((format(DELTA_ROW, 1)),(format(DELTA_ROW, 2)));
        sourceNavigateAngularPage().rightClick((format(DELTA_ROW, 1)));
        sourceNavigateAngularPage().clickContextMenuItem(REPORT);
        sourceNavigateAngularPage().clickContextMenuItem(INTEGRATION_RESULT);
        sourceNavigateAngularPage().breakOutOfFrame();

        sourceNavigateAngularPage().switchToWindow(IntegrationResultsPageElements.INTEGRATION_RESULTS_PAGE_TITLE);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        sourceNavigateAngularPage().openMenuColumnHeaderForTabs(COLUMN_FILTER_BUTTON_INTERGRATION,INTERGRATION_SECTION_NUMBER);
        sourceNavigateAngularPage().openFilterTabColumnHeader();
        sourceNavigateAngularPage().sendTextToTextbox(FILTER_SECTION_NUMBER_INTERGRATION_RESULT,"2");
        sourceNavigateAngularPage().sendEnterToElement(FILTER_SECTION_NUMBER_INTERGRATION_RESULT);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        sourceNavigateAngularPage().doubleClick(COLUMN_FILTER_BUTTON_INTERGRATION);
        assertThatIntergrationSectionNumber(SECOND_SECTION_NUMBER,"2");
        sourceNavigateAngularPage().click(CLOSE_INTEGRATION_RESULT_PAGE);

        //Click on Renditions tab
        sourceNavigateAngularPage().switchToWindow("SourceNavigateUi");
        sourceNavigateAngularTabsPage().click(RENDITION_TAB);
    }
    /**
     * User Story 722876: [HALCYONST-16722] [CMI]Rendition Integration Results User Story 722858: [HALCYONST-16686] [CMI]Delta Report - Integration Results User Story 722368: [HALCYONST-16098] [CMI] Renditions and Delta: Integration Results
     */
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void IntegrationResultsOfDeltaWithWarnCompleteErrorLocationIntegrationStatuses()
    {
        String renditionUuid = "I14EE5BD0BB2C11E785F2F75CEB041DE2";
        sourceNavigateAngularLeftSidePanePage().clickFindButtonOnLeftPane();
        sourceNavigateAngularLeftSidePanePage().setFindValue("Rendition UUID", renditionUuid);
        sourceNavigateAngularLeftSidePanePage().clickFindButton();

        sourceNavigateAngularPage().click(FIRST_RENDITION_ROW);
        sourceNavigateAngularPage().click(DELTA_TAB);
        sourceNavigateAngularDeltaPage().click(String.format(DELTA_ROW, "5"));
        sourceNavigateAngularDeltaPage().rightClickDelta(String.format(DELTA_ROW, "5"));
        sourceNavigateAngularPage().clickContextMenuItem(REPORT);
        sourceNavigateAngularPage().clickContextMenuItem(INTEGRATION_RESULT);
        sourceNavigateAngularPage().click(CLOSE_INTEGRATION_RESULT_PAGE);

        sourceNavigateAngularDeltaPage().click(String.format(DELTA_ROW, "10"));
        sourceNavigateAngularDeltaPage().rightClickDelta(String.format(DELTA_ROW, "10"));
        sourceNavigateAngularPage().clickContextMenuItem(REPORT);
        sourceNavigateAngularPage().clickContextMenuItem(INTEGRATION_RESULT);
        sourceNavigateAngularPage().switchToWindow(IntegrationResultsPageElements.INTEGRATION_RESULTS_PAGE_TITLE);
        sourceNavigateAngularPage().scrollPageHorizontallyShiftEnd(PART_STATUS_SCROLL_VIEW);

        assertThatIntegrationResultPartStatus(INTEGRATION_RESULT_PART_STATUS,"COMPLETE");
        sourceNavigateAngularPage().click(CLOSE_INTEGRATION_RESULT_PAGE);

        sourceNavigateAngularDeltaPage().click(String.format(DELTA_ROW, "3"));
        sourceNavigateAngularDeltaPage().rightClickDelta(String.format(DELTA_ROW, "3"));
        sourceNavigateAngularPage().clickContextMenuItem(REPORT);
        sourceNavigateAngularPage().clickContextMenuItem(INTEGRATION_RESULT);
        sourceNavigateAngularPage().switchToWindow(IntegrationResultsPageElements.INTEGRATION_RESULTS_PAGE_TITLE);
        sourceNavigateAngularPage().scrollPageHorizontallyShiftEnd(PART_STATUS_SCROLL_VIEW);
        assertThatIntegrationResultPartStatus(INTEGRATION_RESULT_PART_STATUS,"WARN");
        sourceNavigateAngularPage().click(CLOSE_INTEGRATION_RESULT_PAGE);

        //Click on Renditions tab
        sourceNavigateAngularPage().switchToWindow("SourceNavigateUi");
        sourceNavigateAngularTabsPage().click(RENDITION_TAB);
    }

    /**
     * Verify that "Track  Clean" and the "Track  Released to Westlaw" are populated for the corresponding Sections and Deltas of the PREP Rendition(s)
     */
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void verifySpecifiedTrackingStautusColumn() 
    {
        datapodObject = SourceDataMockingNew.Iowa.Small.PREP.insert();
        renditionUuid = datapodObject.getRenditions().get(0).getRenditionUUID();
        connection = CommonDataMocking.connectToDatabase(environmentTag);
        String RENDITION_UUID = renditionUuid;
        sourceNavigateAngularPage().goToSourcePage();
        loginPage().logIn();
        DateAndTimeUtils.takeNap(DateAndTimeUtils.FIVE_SECONDS);
        sourceNavigateAngularLeftSidePanePage().clickFindButtonOnLeftPane();
        sourceNavigateAngularLeftSidePanePage().setFindValue("Rendition UUID", RENDITION_UUID);
        sourceNavigateAngularLeftSidePanePage().clickFindButton();
        sourceNavigateAngularPage().click(FIRST_RENDITION_ROW);
        sourceNavigateAngularLeftSidePanePage().filterForColumnAndSelect("Rendition Tracking Status");
        sourceNavigateAngularLeftSidePanePage().filterForColumnAndSelect("Clean");
        sourceNavigateAngularPage().rightClick(FIRST_RENDITION_ROW);
        // Track>Clean
        sourceNavigateAngularPage().clickContextMenuItem(TRACK);
        sourceNavigateAngularPage().clickContextMenuItem(CLEAN);
        sourceNavigateAngularPage().sendKeysToElement("//INPUT[@ID='statusDatePicker']", DateAndTimeUtils.getCurrentDateMMddyyyy());
        sourceNavigateAngularPage().click(Submit_Button);
        assertThatToastMessage();
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        sourceNavigateAngularPage().scrollPageHorizontallyShiftEnd(FIRST_RENDITION_ROW);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        assertThatRenditionTrackingStatus(FIRST_RENDITION_TRACKING_STATUS, "Clean");
        assertThatRenditionTrackingStatus(CLEANED_DATE, DateAndTimeUtils.getCurrentDateMMddyyyy());
    }
  
   /**
    * User Story 724356: Verify that all the context menu items are accessible if some columns are unchecked
    */
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void verifyContextMenu(){
        String apvRenditionUuid = "I005B3130FEDC11EDACE1BEF1514E33C9";
        String prepRenditionUuid = "I012B84E2944E11E99303BB1F7911C30D";
        List<String> renditionTabValidation = Arrays.asList("Edit", "View", "Modify", "Rendition Properties", "Add Content",
                "Source Copy", "Validate", "Track", "Report", "Transfer", "Create Bookmark", "Debug", "Export to Excel",
                "Export to Excel selected rows");

        List<String> sectionTabValidation = Arrays.asList("Edit", "View", "Modify", "Section Properties", "Validate", "Track", "Debug", "Export to Excel",
                "Export to Excel selected rows");

        List<String> deltaTabValidation = Arrays.asList("Edit", "View", "Modify", "Delta Properties", "Validate", "Track", "Debug", "Export to Excel",
                "Export to Excel selected rows");

        sourceNavigateAngularPage().goToSourcePage();
        loginPage().logIn();

        sourceNavigateAngularLeftSidePanePage().filterForColumnAndSelect("Error Flag");
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        sourceNavigateAngularPage().waitForElementGone(SourceNavigateAngularPageElements.ERROR_FLAG);
        sourceNavigateAngularLeftSidePanePage().clickFindButtonOnLeftPane();
        sourceNavigateAngularLeftSidePanePage().setFindValue("Rendition UUID", apvRenditionUuid);
        sourceNavigateAngularLeftSidePanePage().clickFindButton();
        sourceNavigateAngularPage().rightClick(FIRST_RENDITION_ROW);
        assertContextMenuValidationForApvDoc();

        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        sourceNavigateAngularLeftSidePanePage().clear((format(FIND_TEXT_FIELD_PATTERN, "Rendition UUID")));
        sourceNavigateAngularLeftSidePanePage().setFindValue("Rendition UUID", prepRenditionUuid);
        sourceNavigateAngularLeftSidePanePage().clickFindButton();
        sourceNavigateAngularPage().rightClick(FIRST_RENDITION_ROW);
        sourceNavigateAngularPage().clickContextMenuItem(MODIFY);
        assertContextMenuValidationForPrepDoc();

        sourceNavigateAngularPage().breakOutOfFrame();
        sourceNavigateAngularLeftSidePanePage().filterForColumnAndSelect("Error Flag");
        sourceNavigateAngularLeftSidePanePage().filterForColumnAndSelect("Multiple / Duplicates");
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        sourceNavigateAngularPage().waitForElementExists(SourceNavigateAngularPageElements.ERROR_FLAG);
        sourceNavigateAngularPage().waitForElementGone(SourceNavigateAngularPageElements.MULTIPLE_DUPLICATE_COLUMN_HEADER);

        sourceNavigateAngularPage().breakOutOfFrame();
        sourceNavigateAngularLeftSidePanePage().clickFindButtonOnLeftPane();
        sourceNavigateAngularLeftSidePanePage().clear((format(FIND_TEXT_FIELD_PATTERN, "Rendition UUID")));
        sourceNavigateAngularLeftSidePanePage().setFindValue("Rendition UUID", apvRenditionUuid);
        sourceNavigateAngularLeftSidePanePage().clickFindButton();
        sourceNavigateAngularPage().rightClick(FIRST_RENDITION_ROW);
        assertThat(sourceNavigateAngularPage().isElementDisplayed(SourceNavigateAngularContextMenuItemsPageElements.SYNC))
                .as("Sync option is not displayed for this APV document")
                .isTrue();
        /* This validation will not work in uat since there is no valid data for APV Document now.
        sourceNavigateAngularPage().refreshPage();
        sourceNavigateAngularPage().rightClick(FIRST_RENDITION_ROW);
        sourceNavigateAngularPage().clickContextMenuItem(MODIFY);
        assertThat(sourceNavigateAngularPage().isElementDisplayed(SourceNavigateAngularContextMenuItemsPageElements.MARK_AS_NON_DUPLICATE))
                .as("Mark as non duplicate option is not displayed for this APV document")
                 .isTrue();
         */
        sourceNavigateAngularPage().clickContextMenuItem(VIEW);
        assertThat(sourceNavigateAngularPage().isElementDisplayed(SourceNavigateAngularContextMenuItemsPageElements.VIEW_MULTIPLE_AND_DUPLICATE_RENDITIONS))
                .as("Multiple and duplicate renditions option is not displayed for this APV document")
                .isTrue();

        sourceNavigateAngularLeftSidePanePage().filterForColumnAndSelect("Multiple / Duplicates");
        sourceNavigateAngularLeftSidePanePage().filterForColumnAndSelect("Index Entry Completed Date");
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        sourceNavigateAngularPage().waitForElementGone(SourceNavigateAngularPageElements.INDEX_ENTRY_COMPLETED_DATE_COLUMN_HEADER);
        sourceNavigateAngularPage().waitForElementExists(SourceNavigateAngularPageElements.MULTIPLE_DUPLICATE_COLUMN_HEADER);

        sourceNavigateAngularLeftSidePanePage().clickFindButtonOnLeftPane();
        sourceNavigateAngularLeftSidePanePage().clear((format(FIND_TEXT_FIELD_PATTERN, "Rendition UUID")));
        sourceNavigateAngularLeftSidePanePage().setFindValue("Rendition UUID", apvRenditionUuid);
        sourceNavigateAngularLeftSidePanePage().clickFindButton();
        sourceNavigateAngularPage().rightClick(FIRST_RENDITION_ROW);
        sourceNavigateAngularPage().clickContextMenuItem(TRANSFER);
        assertThat(sourceNavigateAngularPage().isElementDisplayed(SourceNavigateAngularContextMenuItemsPageElements.CINDEX))
                .as("Transfer to Cindex option is not displayed for this APV document")
                .isTrue();

        sourceNavigateAngularPage().refreshPage();
        sourceNavigateAngularLeftSidePanePage().clickFindButtonOnLeftPane();
        sourceNavigateAngularLeftSidePanePage().setFindValue("Rendition UUID", prepRenditionUuid);
        sourceNavigateAngularLeftSidePanePage().clickFindButton();
        sourceNavigateAngularPage().rightClick(FIRST_RENDITION_ROW);
        assertTheContextMenuForPrepDocUnderAllTabs(renditionTabValidation);
        sourceNavigateAngularLeftSidePanePage().filterForColumnAndSelect("Lock");
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        sourceNavigateAngularPage().waitForElementGone(SourceNavigateAngularPageElements.LOCK_COLUMN_HEADER);
        sourceNavigateAngularPage().rightClick(FIRST_RENDITION_ROW);
        assertTheContextMenuForPrepDocUnderAllTabs(renditionTabValidation);

        sourceNavigateAngularPage().refreshPage();
        sourceNavigateAngularLeftSidePanePage().clickFindButtonOnLeftPane();
        sourceNavigateAngularLeftSidePanePage().setFindValue("Rendition UUID", prepRenditionUuid);
        sourceNavigateAngularLeftSidePanePage().clickFindButton();
        sourceNavigateAngularLeftSidePanePage().click(FIRST_RENDITION_ROW);
        sourceNavigateAngularPage().click(SECTION_TAB);
        sourceNavigateAngularPage().rightClick((format(SECTION_ROW,0)));
        assertTheContextMenuForPrepDocUnderAllTabs(sectionTabValidation);
        sourceNavigateAngularLeftSidePanePage().filterForColumnAndSelectUnderSpecificTab("SECTION", "Lock");
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        sourceNavigateAngularPage().waitForElementGone(SourceNavigateAngularPageElements.LOCK_COLUMN_HEADER);
        sourceNavigateAngularPage().rightClick((format(SECTION_ROW,0)));
        assertTheContextMenuForPrepDocUnderAllTabs(sectionTabValidation);

        sourceNavigateAngularPage().refreshPage();
        sourceNavigateAngularLeftSidePanePage().clickFindButtonOnLeftPane();
        sourceNavigateAngularLeftSidePanePage().setFindValue("Rendition UUID", prepRenditionUuid);
        sourceNavigateAngularLeftSidePanePage().clickFindButton();
        sourceNavigateAngularLeftSidePanePage().click(FIRST_RENDITION_ROW);
        sourceNavigateAngularPage().click(DELTA_TAB);
        sourceNavigateAngularPage().rightClick((format(DELTA_ROW,0)));
        assertTheContextMenuForPrepDocUnderAllTabs(deltaTabValidation);
        sourceNavigateAngularLeftSidePanePage().filterForColumnAndSelectUnderSpecificTab("DELTA", "Lock");
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        sourceNavigateAngularPage().waitForElementGone(SourceNavigateAngularPageElements.LOCK_COLUMN_HEADER);
        sourceNavigateAngularPage().rightClick((format(DELTA_ROW,0)));
        assertTheContextMenuForPrepDocUnderAllTabs(deltaTabValidation);

        sourceNavigateAngularTabsPage().click(RENDITION_TAB);
    }
}

