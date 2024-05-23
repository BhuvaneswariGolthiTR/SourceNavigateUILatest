package com.thomsonreuters.codes.codesbench.quality.tests.sourcenavigateangular.general;

import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorToolbarPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.SourceNavigateAngularPageElements;
import com.thomsonreuters.codes.codesbench.quality.tests.sourcenavigateangular.assertions.SourceNavigateAngularAssertions;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.EDGE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.CommonDataMocking;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.source.SourceDataMockingNew;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.source.datapod.SourceDatapodObject;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.sql.Connection;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.SourceNavigateAngularContextMenuItemsPageElements.*;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.SourceNavigateAngularDeltaPageElements.*;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.SourceNavigateAngularLeftSidePaneElements.FIND_TEXT_FIELD_PATTERN;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.SourceNavigateAngularPageElements.*;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.SourceNavigateAngularPageElements.CALENDAR_OPTION;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.SourceNavigateAngularTabsPageElements.*;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.SourceNavigateAngularTabsPageElements.RENDITION_TAB;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.popups.SourceNavigateAngularPopUpPageElements.*;
import static com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils.disconnect;
import static java.lang.String.format;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SNGeneralDeltaPropertiesPageTests extends SourceNavigateAngularAssertions {

    static String renditionUuid;
    SourceDatapodObject datapodObject;
    Connection connection;

    @BeforeEach
    public void mockData(TestInfo testInfo) {
        if (!(testInfo.getDisplayName().equals("verifyDeltaTabClearFilterTest()")) &&
                !(testInfo.getDisplayName().equals("assignUserAndDateOftheDocuments()"))) {
            datapodObject = SourceDataMockingNew.Iowa.Small.APV.insert();
            renditionUuid = datapodObject.getRenditions().get(0).getRenditionUUID();
            logger.information("rendition uuid value is" + renditionUuid);
            connection = CommonDataMocking.connectToDatabase(environmentTag);
        }
    }

    @AfterEach
    public void deleteMockedData() {
        if (datapodObject != null) {
//             sourceNavigateGridPage().unlockRenditionWithUuid(renditionUuid);
            datapodObject.delete();
        }
        disconnect(connection);
    }


    /**
     * Description Delta Properties:Clicking on delta Tab and navigating to Delta Properties Tab
     */
    public void NavigatingAndVerifyingDeltaProperties(String renditionStatus) {
        sourceNavigateAngularLeftSidePanePage().setFindValue("Rendition UUID", renditionStatus);
        logger.information("rendition status value is" + renditionStatus);
        sourceNavigateAngularLeftSidePanePage().clickFindButton();
        sourceNavigateAngularPage().waitForElementExists(format(TOTAL_RENDITIONS_NUMBER, "1"));
        sourceNavigateAngularPage().rightClickRenditions();
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);

    }

    /**
     * Description Delta Properties:Clicking on delta Tab and navigating to Delta Properties Tab
     */
    public void NavigateToDeltaPropertiesTab() {
        //Delta tab clicking and selecting first row
        sourceNavigateAngularTabsPage().click(DELTA_TAB);
        sourceNavigateAngularPage().click((format(DELTA_ROW, 0)));
        sourceNavigateAngularPage().rightClick((format(DELTA_ROW, 0)));
        //Click on Delta Properties content Menu
        sourceNavigateAngularPage().clickContextMenuItem(DELTA_PROPERTIES);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.THREE_SECONDS);
    }

    /**
     * Description Delta Properties:Clicking on cancel Tab for both locked and unlocked uuids and navigating to renditions view
     */
    public void clickOnCancelButtonAndNavigateToRenditionsView(Boolean value) {
        if (value.equals(true)) {
            sourceNavigateAngularDeltaPage().click(CANCEL);
            DateAndTimeUtils.takeNap(DateAndTimeUtils.THREE_SECONDS);
            assertThatIfConfirmationWindowAppeared();
            sourceNavigateAngularPage().clickConfirm();
            sourceNavigateAngularPage().waitForPageLoaded();
            assertThatIfConfirmationWindowDisappeared();
        } else {
            sourceNavigateAngularDeltaPage().click(CANCEL);
        }

    }

    /**
     * Description Delta Properties:Clicking on Rendition Tab and clearing the existing uuid if any
     */
    public void clickOnRenditionTabAndClearUUID() {
        sourceNavigateAngularPage().click(RENDITION_TAB);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.THREE_SECONDS);
        sourceNavigateAngularLeftSidePanePage().clear(format(FIND_TEXT_FIELD_PATTERN, "Rendition UUID"));
        DateAndTimeUtils.takeNap(DateAndTimeUtils.THREE_SECONDS);
    }

    public Stream<Arguments> provideDataForDeltaProperties() {
        return Stream.of(
                Arguments.of(renditionUuid),
                Arguments.of("I300E7A00658F11E28B049F1D7A89B350"));
    }

    /**
     * Test Case ID:723101_TC1
     * Description Delta Properties: click on FindButton on left panel and verify the delta properties box user interface
     */
    @ParameterizedTest(name = "Test: {displayName}; Index: {index}; Arguments: {arguments}")
    @MethodSource("provideDataForDeltaProperties")
    @EDGE
    @LEGAL
    @LOG
    public void verifyDeltaPropertiesBoxUserInterface(String Uuid) {
        if (Uuid == null) {
            Uuid = renditionUuid;
        }
        sourceNavigateAngularLeftSidePanePage().clickFindButtonOnLeftPane();
        //Applying "APV" and "PREP" Filter for rendition status and clicking on first row
        NavigatingAndVerifyingDeltaProperties(Uuid);
        NavigateToDeltaPropertiesTab();
        //verifying display of header content,cancel,submit and close button of delta properties popup
        String headerText = sourceNavigateAngularDeltaPage().getElementsText(DELTA_PROPERTIES_HEADER);
        logger.information("text displayed is" + headerText);
        sourceNavigateAngularDeltaPage().doesElementExist(DELTA_CANCEL_BUTTON);
        sourceNavigateAngularDeltaPage().doesElementExist(DELTA_SUBMIT_BUTTON);
        sourceNavigateAngularDeltaPage().doesElementExist(DELTA_CLOSE_BUTTON);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.THREE_SECONDS);
        //Clicking on confirm button and verifying display of confirmation popup
        clickOnCancelButtonAndNavigateToRenditionsView(true);
        clickOnRenditionTabAndClearUUID();
    }

    public Stream<Arguments> verifyDeltaPropertiesTabUserInterface() {
        return Stream.of(
                Arguments.of(renditionUuid, Arrays.asList("Action ", "Cite Locate Status ", "Text Merge Status ", "Text Merge Flags ", "Target Section ", "Target Subsection ",
                        "Target Code ")),
                Arguments.of("I300E7A00658F11E28B049F1D7A89B350", Arrays.asList("Action ", "Cite Locate Status ", "Text Merge Status ", "Text Merge Flags ", "Target Section ", "Target Subsection ",
                        "Target Code "))
        );
    }

    /**
     * Test Case ID:723101_TC2
     * Description:Delta Properties: Delta properties tab: verify the tab user interface content
     */
    @ParameterizedTest(name = "Test: {displayName}; Index: {index}; Arguments: {arguments}")
    @MethodSource("verifyDeltaPropertiesTabUserInterface")
    @EDGE
    @LEGAL
    @LOG
    public void verifyDeltaPropertiesTabUserInterface(String Uuid, List<String> deltaPropertiesTabValidation) {
        if (Uuid == null) {
            Uuid = renditionUuid;
        }
        //Clicking on FindButton left panel
        sourceNavigateAngularLeftSidePanePage().clickFindButtonOnLeftPane();
        //Applying "APV" Filter for rendition status and clicking on first row

        NavigatingAndVerifyingDeltaProperties(Uuid);
        NavigateToDeltaPropertiesTab();
        //Verify Delta Properties Tab Columns Validation
        DateAndTimeUtils.takeNap(DateAndTimeUtils.THREE_SECONDS);
        for (String s : deltaPropertiesTabValidation) {
            assertThatDeltaPropertiesTabColumnValuesIsEnabled(s, true);
            assertThatDeltaPropertiesTabColumnValuesIsEnabled("Assigned User", false);
            assertThatDeltaPropertiesTabColumnValuesIsEnabled("Effective Date", false);
            assertThatDeltaPropertiesTabColumnValuesIsEnabled("Assigned Date", false);
        }

        //Clicking on confirm button and verifying display of confirmation popup
        clickOnCancelButtonAndNavigateToRenditionsView(true);
        clickOnRenditionTabAndClearUUID();
    }


    /**
     * Test Case ID:723101_TC3
     * Description: Delta Properties: Delta properties tab: verify the Edit and Save functionality
     */
    @ParameterizedTest(name = "Test: {displayName}; Index: {index}; Arguments: {arguments}")
    @MethodSource("provideDataForDeltaProperties")
    @EDGE
    @LEGAL
    @LOG
    public void verifyDeltaPropertiesTabEditAndSaveFunctionality(String Uuid) {
        String userName1 = "TLE TCBA-BOT";
        String diffLevel1 = "M1";
        String description = "This is testing data description";
        if (Uuid == null) {
            Uuid = renditionUuid;
        }
        //Clicking on FindButton left panel
        sourceNavigateAngularLeftSidePanePage().clickFindButtonOnLeftPane();
        //Applying "APV" Filter for rendition status and clicking on first row
        NavigatingAndVerifyingDeltaProperties(Uuid);
        NavigateToDeltaPropertiesTab();
        String headerText = sourceNavigateAngularDeltaPage().getElementsText(DELTA_PROPERTIES_HEADER);
        logger.information("text displayed is" + headerText);

        //Verify Delta Properties Tab Columns Validation
        DateAndTimeUtils.takeNap(DateAndTimeUtils.THREE_SECONDS);

        //Select Assigned User value from dropdown
        sourceNavigateAngularDeltaPage().click(ASSIGNED_USER_DROPDOWN);
        sourceNavigateAngularDeltaPage().sendKeys(userName1);
        sourceNavigateAngularLockReportPage().pressEnter();
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);

        //Select Effective Date value from calender dropdown
        sourceNavigateAngularDeltaPage().click(EFFECTIVE_CALENDER);
        sourceNavigateAngularPage().sendTextToTextbox(EFFECTIVE_DATE_INPUT, DateAndTimeUtils.getCurrentDateMMddyyyyNoDelimeters());
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);

        //Select difficult level from dropdown and click Submit
        sourceNavigateAngularDeltaPage().click(DELTA_DIFFICULTY_LEVEL_DROPDOWN);
        sourceNavigateAngularDeltaPage().click(format(DIFFICULTY_LEVEL_DROPDOWN_VALUE, diffLevel1));
        DateAndTimeUtils.takeNap(DateAndTimeUtils.THREE_SECONDS);

        //Select Assigned Date value from calender dropdown
        sourceNavigateAngularDeltaPage().click(ASSIGNED_CALENDER);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        sourceNavigateAngularPage().sendTextToTextbox(ASSIGNED_DATE_PICKER, DateAndTimeUtils.getCurrentDateMMddyyyyNoDelimeters());
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);

        //Navigate to the Delta instruction field > Add an entry to the field
        sourceNavigateAngularPage().sendTextToTextbox(DELTA_PROPERTIES_DESCRIPTION, description);
        sourceNavigateAngularPage().waitForPageLoaded();

        //Clicking on submit button and verifying display of confirmation popup
        sourceNavigateAngularDeltaPage().click(SUBMIT);
        sourceNavigateAngularPage().waitForPageLoaded();

        //clickOn DeltaProperties left hand column tab and add "Assigned User",Assigned Date" and "Difficulty Level"
        //"Effective Date","Description"
        sourceNavigateAngularLeftSidePanePage().filterForColumnAndSelectUnderSpecificTab("DELTA", "Assigned User");
        sourceNavigateAngularLeftSidePanePage().filterForColumnAndSelectUnderSpecificTab("DELTA", "Assigned Date");
        sourceNavigateAngularPage().scrollToRight((format(DELTA_ROW, 0)));
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        assertThatRenditionAssignedDateAndUser(FIRST_SECTION_ASSIGNED_DATE, FIRST_SECTION_ASSIGNED_USER, userName1);
        assertThatDeltaPropertiesValuesAsExpected("assignedTo", userName1, 0);
        sourceNavigateAngularLeftSidePanePage().filterForColumnAndSelectUnderSpecificTab("DELTA", "Difficulty Level");
        sourceNavigateAngularPage().scrollToRight((format(DELTA_ROW, 0)));
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        assertThatDeltaPropertiesValuesAsExpected("difficultyLevel", diffLevel1, 0);
        sourceNavigateAngularLeftSidePanePage().filterForColumnAndSelectUnderSpecificTab("DELTA", "Effective Date");
        sourceNavigateAngularLeftSidePanePage().filterForColumnAndSelectUnderSpecificTab("DELTA", "Delta Instructions ");
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        assertThatDeltaPropertiesValuesAsExpected("instructionNote", description, 0);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        clickOnRenditionTabAndClearUUID();
    }


    /**
     * Test Case ID:723101_TC4
     * Description: Delta Properties: Delta properties tab: verify the Cancel and Close functionality
     */

    @ParameterizedTest(name = "Test: {displayName}; Index: {index}; Arguments: {arguments}")
    @MethodSource("verifyDeltaPropertiesTabUserInterface")
    @EDGE
    @LEGAL
    @LOG
    public void verifyDeltaPropertiesTabCancelAndCloseFunctionality(String Uuid) {
        if (Uuid == null) {
            Uuid = renditionUuid;
        }
        //Clicking on FindButton left panel
        sourceNavigateAngularLeftSidePanePage().clickFindButtonOnLeftPane();

        //Applying "APV" Filter for rendition status and clicking on first row
        NavigatingAndVerifyingDeltaProperties(Uuid);
        NavigateToDeltaPropertiesTab();
        String headerText = sourceNavigateAngularDeltaPage().getElementsText(DELTA_PROPERTIES_HEADER);
        logger.information("text displayed is" + headerText);

        //Verify Delta Properties Tab Columns Validation
        DateAndTimeUtils.takeNap(DateAndTimeUtils.THREE_SECONDS);
        sourceNavigateAngularPage().clickCancel();

        //Closing Delta Properties Pop Up
        DateAndTimeUtils.takeNap(DateAndTimeUtils.ONE_SECOND);
        assertThatDisplayOfPopUpElements("Confirmation", "Are you sure you want to cancel?", SOURCE_NAV_ASSIGN_USER_CANCEL, CONFIRM_BUTTON, CLOSE_UI_BUTTON);

        sourceNavigateAngularPage().click(SOURCE_NAV_ASSIGN_USER_CANCEL);
        assertThatPopUpMessageDisappearedOrNot("Are you sure you want to cancel?");
        DateAndTimeUtils.takeNap(DateAndTimeUtils.ONE_SECOND);

        sourceNavigateAngularPage().click(CANCEL_BUTTON);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.ONE_SECOND);
        assertThatDisplayOfPopUpElements("Confirmation", "Are you sure you want to cancel?", CANCEL_BUTTON, CONFIRM_BUTTON, CLOSE_UI_BUTTON);

        sourceNavigateAngularPage().click(CONFIRM_BUTTON);

        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        sourceNavigateAngularPage().rightClick((format(DELTA_ROW, 0)));
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        sourceNavigateAngularPage().clickContextMenuItem(DELTA_PROPERTIES);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        sourceNavigateAngularPage().click(CANCEL_BUTTON);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.ONE_SECOND);
        assertThatDisplayOfPopUpElements("Confirmation", "Are you sure you want to cancel?", CANCEL_BUTTON, CONFIRM_BUTTON, CLOSE_UI_BUTTON);
        sourceNavigateAngularPage().click(format(CLOSE_UI_BUTTON, "Confirmation"));
        DateAndTimeUtils.takeNap(DateAndTimeUtils.ONE_SECOND);
        assertThatPopUpMessageDisappearedOrNot("Are you sure you want to cancel?");

        String headerText1 = sourceNavigateAngularDeltaPage().getElementsText(HEADER);
        sourceNavigateAngularPage().click(format(CLOSE_UI_BUTTON, headerText1));
        DateAndTimeUtils.takeNap(DateAndTimeUtils.ONE_SECOND);
        assertThatDisplayOfPopUpElements("Confirmation", "Are you sure you want to cancel?", CANCEL_BUTTON, CONFIRM_BUTTON, CLOSE_UI_BUTTON);
        sourceNavigateAngularPage().click(SOURCE_NAV_ASSIGN_USER_CANCEL);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);

        sourceNavigateAngularPage().click(format(CLOSE_UI_BUTTON, headerText));
        DateAndTimeUtils.takeNap(DateAndTimeUtils.ONE_SECOND);
        assertThatDisplayOfPopUpElements("Confirmation", "Are you sure you want to cancel?", CANCEL_BUTTON, CONFIRM_BUTTON, CLOSE_UI_BUTTON);
        sourceNavigateAngularPage().click(format(CLOSE_UI_BUTTON, "Confirmation"));
        DateAndTimeUtils.takeNap(DateAndTimeUtils.ONE_SECOND);
        assertThatPopUpMessageDisappearedOrNot("Are you sure you want to cancel?");

        sourceNavigateAngularPage().click(format(CLOSE_UI_BUTTON, headerText));
        DateAndTimeUtils.takeNap(DateAndTimeUtils.ONE_SECOND);
        assertThatDisplayOfPopUpElements("Confirmation", "Are you sure you want to cancel?", CANCEL_BUTTON, CONFIRM_BUTTON, CLOSE_UI_BUTTON);
        sourceNavigateAngularPage().click(CONFIRM_BUTTON);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.ONE_SECOND);
        assertThatPopUpMessageDisappearedOrNot("Are you sure you want to cancel?");

        //Navigating to Rendition Tab
        sourceNavigateAngularTabsPage().click(RENDITION_TAB);
        sourceNavigateAngularLeftSidePanePage().clear(format(FIND_TEXT_FIELD_PATTERN, "Rendition UUID"));
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
    }

    /**
     * Test Case ID:723101_TC5
     * Description: Delta Properties: Delta properties tab: verify the changes cannot be saved when delta is locked
     */

    @ParameterizedTest(name = "Test: {displayName}; Index: {index}; Arguments: {arguments}")
    @MethodSource("provideDataForDeltaProperties")
    @EDGE
    @LEGAL
    @LOG
    public void verifyDeltaPropertiesTabChangesWhenDeltaIsLocked(String Uuid) {
        String diffLevel1 = "M1";
        String userName1 = "TLE TCBA-BOT";
        if (Uuid == null) {
            Uuid = renditionUuid;
        }

        //Clicking on FindButton left panel
        sourceNavigateAngularLeftSidePanePage().clickFindButtonOnLeftPane();

        //Applying "APV" Filter for rendition status and clicking on first row
        NavigatingAndVerifyingDeltaProperties(Uuid);

        //verify the locked icon from the rendition
        boolean lockedStatus = sourceNavigateAngularRenditionPage().verifyLockIconStateOfRendition();
        logger.information("locked rendition status is" + lockedStatus);
        if (lockedStatus) {
            logger.information("Rendition is already locked.");
            assertThatRenditionDisplaysLockIcon(true);
        } else {
            logger.information("Rendition is not locked. Attempting to lock...");
            try {
                lockRendition();
                //Verify the lock icon for the rendition
                assertThatRenditionDisplaysLockIcon(true);
            } catch (Exception e) {
                logger.information("Failed to lock rendition: " + e.getMessage());
            }
        }

        //Navigate to Delta Properties Tab and verify Locked rendition header text
        NavigateToDeltaPropertiesTab();
        String lockedRenditionText = sourceNavigateAngularDeltaPage().getElementsText(DELTA_PROPERTIES_LOCKED_HEADER);
        logger.information("text displayed is" + lockedRenditionText);

        //Select Assigned User value from dropdown
        sourceNavigateAngularDeltaPage().click(ASSIGNED_USER_DROPDOWN);
        sourceNavigateAngularDeltaPage().sendKeys(userName1);
        sourceNavigateAngularLockReportPage().pressEnter();
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);

        //Click on Submit Button  and verify assigned user and difficulty level dropdown is saved
        assertThatDeltaPropertiesSubmitButton();
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);

        //Select difficult level from dropdown and click Submit
        sourceNavigateAngularDeltaPage().click(DELTA_DIFFICULTY_LEVEL_DROPDOWN);
        sourceNavigateAngularDeltaPage().click(format(DIFFICULTY_LEVEL_DROPDOWN_VALUE, diffLevel1));
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);

        //Click on Submit Button  and verify assigned user and difficulty level dropdown is saved
        assertThatDeltaPropertiesSubmitButton();
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);

        //Navigate back to delta properties tab and verify submitted data is not saved
        clickOnCancelButtonAndNavigateToRenditionsView(false);
        NavigateToDeltaPropertiesTab();
        //Click on Submit Button  and verify assigned user and difficulty level dropdown is saved
        assertThatDeltaPropertiesSubmitButton();
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);

        //Click on Cancel button and navigate back to rendition tab
        clickOnCancelButtonAndNavigateToRenditionsView(false);
        clickOnRenditionTabAndClearUUID();
    }


    /**
     * Test Case ID:723101_TC6,723101_TC7
     * Description:Delta Properties: Proposed/Approved Tracking Information tab: verify the tab UI content and functionality
     */

    @Test
    @EDGE
    @LEGAL
    @LOG
    public void verifyProposedApprovedTrackingInformationContentAndFunctionality() {
        //Storing APV Rendition UUIDs
        String renditionUUIDs = renditionUuid;
        String loggedUserName = "TLE TCBA-BOT";

        String[] attributeLabelNames = {"Images Sent Out", "Images Completed", "Tabular Requested", "Tabular Started", "Tabular Completed"};
        String currentDate = DateAndTimeUtils.getCurrentDateMMddyyyyNoDelimeters();

      logger.information("Current Date:" + currentDate + "/");
        //Finding the Renditions with Rendition UUID with APV and PREP
        sourceNavigateAngularLeftSidePanePage().clickFindButtonOnLeftPane();

        sourceNavigateAngularLeftSidePanePage().setFindValue("Rendition UUID", renditionUUIDs);
        sourceNavigateAngularLeftSidePanePage().clickFindButton();
        DateAndTimeUtils.takeNap(DateAndTimeUtils.THREE_SECONDS);
        sourceNavigateAngularPage().rightClickRenditions();

        //Section tab clicking and selecting first row
        sourceNavigateAngularTabsPage().click(DELTA_TAB);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        sourceNavigateAngularPage().rightClick((format(DELTA_ROW, 0)));
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        sourceNavigateAngularPage().clickContextMenuItem(DELTA_PROPERTIES);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);

        sourceNavigateAngularPage().click(format(ANY_TAB_NAME, "Proposed/Approved Tracking Information"));

        assertThatProposedApprovedTrackingInformationTabContainsColumns("Attribute", "Date", "Owner");

        for (String labelName : attributeLabelNames)
            assertThatTabTextFieldAndCalendarOption(labelName);
        for (String labelName : attributeLabelNames) {
            sourceNavigateAngularDeltaPage().click(format(CALENDAR_OPTION, labelName));
            sourceNavigateAngularPage().sendTextToTextbox(format(LABEL_TEXT_FIELD, labelName), currentDate);
        }

        //Closing Section Properties Pop Up
        DateAndTimeUtils.takeNap(DateAndTimeUtils.ONE_SECOND);
        sourceNavigateAngularPage().click(SUBMIT);

        sourceNavigateAngularPage().rightClick((format(DELTA_ROW, 0)));
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        sourceNavigateAngularPage().clickContextMenuItem(DELTA_PROPERTIES);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        sourceNavigateAngularPage().click(format(ANY_TAB_NAME, "Proposed/Approved Tracking Information"));

        for (String labelName : attributeLabelNames) {
            String currentDateWithDelimeter = DateAndTimeUtils.getCurrentDateMMddyyyy();
            assertThatCurrentDateSaved(labelName, currentDateWithDelimeter);
            assertThatColumnPopulatedWithOwnerData(labelName, loggedUserName);
        }

        DateAndTimeUtils.takeNap(DateAndTimeUtils.ONE_SECOND);
        sourceNavigateAngularPage().clickCancel();
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        assertThatDisplayOfPopUpElements("Confirmation", "Are you sure you want to cancel?", CANCEL_BUTTON, CONFIRM_BUTTON, CLOSE_UI_BUTTON);
        sourceNavigateAngularPage().click(SOURCE_NAV_ASSIGN_USER_CANCEL);
        //Closing Section Properties Pop Up
        DateAndTimeUtils.takeNap(DateAndTimeUtils.ONE_SECOND);
        sourceNavigateAngularPage().clickCancel();
        DateAndTimeUtils.takeNap(DateAndTimeUtils.ONE_SECOND);
        sourceNavigateAngularPage().clickConfirm();

        //Navigating to Rendition Tab
        sourceNavigateAngularTabsPage().click(RENDITION_TAB);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);

    }

    /**
     * Test Case ID:723101_TC8
     * Description:Delta Properties: PREP Tracking Information: verify the tab UI content and functionality
     */
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void verifyPREPTrackingInformationTabContentAndFunctionality() {
        String renditionUUID = "I003837725C1B11ED8810869DEE521F68";
        String[] attributeLabelNames = {"Attorney Work Started", "Attorney Work Completed", "Versioning Work Started",
                "Versioning Work Completed", "PREP Started", "PREP Completed", "Ready for Integration", "Integration Started",
                "Integration Completed", "Integration 2 Started", "Integration 2 Completed",
                "Audit Review Requested", "Audit Review Started", "Audit Review Completed", "Audit Corrections Started",
                "Audit Corrections Completed", "Corrections Review Completed", "Corrections Audit Requested",
                "Corrections Audit Started", "Corrections Audit Completed", "Clean", "Released To Westlaw"};

        String[] checkBoxLabelIDs = {"integrationQueryStarted", "integrationQueryCompleted", "correctionsAuditRequired"};
        String dropdownLabelName = "Corrections Audit Required";

        String currentDate = DateAndTimeUtils.getCurrentDateMMddyyyyNoDelimeters();
        String loggedUserName = "TLE TCBA-BOT";

        sourceNavigateAngularLeftSidePanePage().clickFindButtonOnLeftPane();

        sourceNavigateAngularLeftSidePanePage().setFindValue("Rendition UUID", renditionUUID);
        sourceNavigateAngularLeftSidePanePage().clickFindButton();
        DateAndTimeUtils.takeNap(DateAndTimeUtils.THREE_SECONDS);
        sourceNavigateAngularPage().rightClickRenditions();

        //Section tab clicking and selecting first row
        sourceNavigateAngularTabsPage().click(DELTA_TAB);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        sourceNavigateAngularPage().rightClick((format(DELTA_ROW, 0)));
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        sourceNavigateAngularPage().clickContextMenuItem(DELTA_PROPERTIES);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);

        sourceNavigateAngularPage().click(format(ANY_TAB_NAME, "PREP Tracking Information"));
        assertThatPREPTrackingFirstHalfColumns("Attribute", "Value", "Owner");
        assertThatPREPTrackingSecondHalfColumns("Attribute", "Value", "Owner");

        for (String labelName : attributeLabelNames)
            assertThatTabTextFieldAndCalendarOption(labelName);

        for (String id : checkBoxLabelIDs)
            assertThatCheckboxesEnabledOrNot(id);

        assertThatDisplayOfPREPTrackingDropdownValues(dropdownLabelName, "EAGAN", "MANILA");

        for (String labelName : attributeLabelNames) {
            sourceNavigateAngularPage().waitForElementToBeClickable(format(SourceNavigateAngularPageElements.CALENDAR_OPTION,labelName));
            sourceNavigateAngularDeltaPage().click(format(SourceNavigateAngularPageElements.CALENDAR_OPTION, labelName));
            sourceNavigateAngularPage().sendTextToTextbox(format(LABEL_TEXT_FIELD, labelName), currentDate);
        }

        DateAndTimeUtils.takeNap(DateAndTimeUtils.FIVE_SECONDS);

        for (String id : checkBoxLabelIDs) {
//                sourceNavigateAngularPage().click(format(CHECKBOX_INPUT_FIELD, id));
            sourceNavigateAngularPage().checkCheckbox(format(CHECKBOX_INPUT_FIELD, id));
            DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        }

        sourceNavigateAngularPage().click(format(PREP_TRACKING_COMBO_BOX, dropdownLabelName));
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        sourceNavigateAngularPage().click(format(COMBO_BOX_LIST, "EAGAN"));
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);

        sourceNavigateAngularPage().click(SUBMIT);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.THREE_SECONDS);
        sourceNavigateAngularPage().waitForPageLoaded();

        sourceNavigateAngularPage().rightClick((format(DELTA_ROW, 0)));
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        sourceNavigateAngularPage().clickContextMenuItem(DELTA_PROPERTIES);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);

        sourceNavigateAngularPage().click(format(ANY_TAB_NAME, "PREP Tracking Information"));
        String currentDateWithDelimeter = DateAndTimeUtils.getCurrentDateMMddyyyy();
        for (String labelName : attributeLabelNames) {
            assertThatCurrentDateSaved(labelName, currentDateWithDelimeter);
            assertThatColumnPopulatedWithOwnerData(labelName, loggedUserName);
        }

        DateAndTimeUtils.takeNap(DateAndTimeUtils.ONE_SECOND);
        sourceNavigateAngularPage().clickCancel();
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        assertThatDisplayOfPopUpElements("Confirmation", "Are you sure you want to cancel?", CANCEL_BUTTON, CONFIRM_BUTTON, CLOSE_UI_BUTTON);
        sourceNavigateAngularPage().click(SOURCE_NAV_ASSIGN_USER_CANCEL);

        //Closing Delta Properties Pop Up
        DateAndTimeUtils.takeNap(DateAndTimeUtils.ONE_SECOND);
        sourceNavigateAngularPage().clickCancel();
        DateAndTimeUtils.takeNap(DateAndTimeUtils.ONE_SECOND);
        sourceNavigateAngularPage().clickConfirm();

        DateAndTimeUtils.takeNap(DateAndTimeUtils.THREE_SECONDS);
        sourceNavigateAngularPage().waitForPageLoaded();

        sourceNavigateAngularPage().rightClick((format(DELTA_ROW, 0)));
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        sourceNavigateAngularPage().clickContextMenuItem(DELTA_PROPERTIES);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        sourceNavigateAngularPage().click(format(ANY_TAB_NAME, "Proposed/Approved Tracking Information"));

        assertThatProposedApprovedTrackingInformationInputFieldsAreReadOnly();
        DateAndTimeUtils.takeNap(DateAndTimeUtils.FOUR_SECONDS);

        sourceNavigateAngularPage().click(SUBMIT);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);

        for (String columnName : attributeLabelNames)
            sourceNavigateAngularLeftSidePanePage().filterForColumnAndSelectUnderSpecificTab("DELTA", columnName);

        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);

        sourceNavigateAngularPage().scrollToRight(format(DELTA_ROW, 0));
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);

        //Navigating to Rendition Tab
        sourceNavigateAngularTabsPage().click(RENDITION_TAB);
        sourceNavigateAngularLeftSidePanePage().clear(String.format(FIND_TEXT_FIELD_PATTERN, "Rendition UUID"));
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);

    }

    /**
     * Test Case ID:723101_TC9
     * Description:Delta Properties: verify the tab UI content and functionality
     */
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void verifySystemPropertiesTabUIContentAndFunctionality() {
        String renditionUUID = "I300E7A00658F11E28B049F1D7A89B350";
        String[] deltaPropertiesTabFields = {"Target Location UUID ", "Target Code ", "Target Node UUID ", "Target Content UUID ", "Delta UUID ",
                "Section UUID ", "Rendition UUID ", "Lineage UUID ", "Rendition Load Date "};

        //Finding the Renditions with Rendition UUID with PREP
        sourceNavigateAngularLeftSidePanePage().clickFindButtonOnLeftPane();

        sourceNavigateAngularLeftSidePanePage().setFindValue("Rendition UUID", renditionUUID);
        sourceNavigateAngularLeftSidePanePage().clickFindButton();
        DateAndTimeUtils.takeNap(DateAndTimeUtils.THREE_SECONDS);
        sourceNavigateAngularPage().rightClickRenditions();

        //Delta tab clicking and selecting first row
        sourceNavigateAngularTabsPage().click(DELTA_TAB);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        sourceNavigateAngularPage().rightClick((format(DELTA_ROW, 0)));
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        sourceNavigateAngularPage().clickContextMenuItem(DELTA_PROPERTIES);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);

        sourceNavigateAngularPage().click(format(ANY_TAB_NAME, "System Properties"));
        for (String filed : deltaPropertiesTabFields)
            assertThatDeltaPropertiesInputFieldsViewMode(filed);

        clickOnCancelButtonAndNavigateToRenditionsView(true);

        //Navigating to Rendition Tab
        sourceNavigateAngularTabsPage().click(RENDITION_TAB);
        sourceNavigateAngularLeftSidePanePage().clear(format(FIND_TEXT_FIELD_PATTERN, "Rendition UUID"));
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);

    }

    private void lockRendition() {
        //Lock the rendition from UI
        sourceNavigateAngularPage().clickContextMenuItem(EDIT);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.FIVE_SECONDS);
        sourceNavigateAngularPage().clickContextMenuItem(RENDITION_CONTENT_MENU);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        sourceNavigateAngularPage().switchToWindow(EditorPageElements.PAGE_TITLE);
        editorPage().waitForPageLoaded();
        editorPage().waitForElementGone(EditorPageElements.COMMAND_IN_PROGRESS);
        editorPage().waitForElement(EditorToolbarPageElements.CLOSE_DOC);
        editorPage().closeCurrentWindowIgnoreDialogue();

        //Refresh the grid
        editorPage().switchToWindow(PAGE_TITLE);
        sourceNavigateAngularPage().clickRefreshTableData();
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
    }


}







