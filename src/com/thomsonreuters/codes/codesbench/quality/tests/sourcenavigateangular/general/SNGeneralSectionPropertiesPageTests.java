package com.thomsonreuters.codes.codesbench.quality.tests.sourcenavigateangular.general;

import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorToolbarPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.SourceNavigateAngularPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.SourceNavigateAngularSectionPageElements;
import com.thomsonreuters.codes.codesbench.quality.tests.sourcenavigateangular.assertions.SourceNavigateAngularAssertions;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.EDGE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.CommonDataMocking;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.source.SourceDataMockingNew;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.source.datapod.SourceDatapodConfiguration;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.source.datapod.SourceDatapodObject;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.source.datapod.SrcContentType;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.SourceNavigateAngularContextMenuItemsPageElements.*;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.SourceNavigateAngularDeltaPageElements.*;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.SourceNavigateAngularLeftSidePaneElements.FIND_TEXT_FIELD_PATTERN;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.SourceNavigateAngularPageElements.*;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.SourceNavigateAngularRenditionPageElements.INTEGRATION_CLOSE_ICON;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.SourceNavigateAngularSectionPageElements.*;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.SourceNavigateAngularSectionPageElements.FIRST_RENDITION_ROW;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.SourceNavigateAngularTabsPageElements.RENDITION_TAB;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.SourceNavigateAngularTabsPageElements.*;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.popups.SourceNavigateAngularPopUpPageElements.*;
import static com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils.disconnect;
import static java.lang.String.format;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class SNGeneralSectionPropertiesPageTests extends SourceNavigateAngularAssertions {
    SourceDatapodObject datapodAPVObject;
    SourceDatapodObject datapodPREPObject;
    Connection connection;
    static String renditionAPVUuid;
    static String renditionPREPUuid;
    static List<SourceDatapodObject> datapodObjects = new ArrayList<>();

    @BeforeEach
    public void mockData(TestInfo testInfo) {
        if (!(testInfo.getDisplayName().equals("verifySectionGroupTabClearFilterTest()"))) {
            datapodAPVObject = SourceDataMockingNew.Iowa.Small.APV.insert();
            renditionAPVUuid = datapodAPVObject.getRenditions().get(0).getRenditionUUID();
            datapodPREPObject = SourceDataMockingNew.Iowa.Small.PREP.insert();
            renditionPREPUuid = datapodPREPObject.getRenditions().get(0).getRenditionUUID();
            connection = CommonDataMocking.connectToDatabase(environmentTag);
        }
    }

    @AfterEach
    public void deleteMockedData() {
        if (datapodAPVObject != null) {
            sourceNavigateGridPage().unlockRenditionWithUuid(renditionAPVUuid);
            datapodAPVObject.delete();
        }
        if (datapodPREPObject != null) {
            sourceNavigateGridPage().unlockRenditionWithUuid(renditionPREPUuid);
            datapodPREPObject.delete();
        }
        disconnect(connection);
    }

    /**
     * Test Case ID:723101_TC1
     * Description:Section Properties: verify the box user interface
     */
    @ParameterizedTest(name = "Test: {displayName}; Index: {index}; Arguments: {arguments}")
    @MethodSource("provideRenditionUUID")
    @EDGE
    @LEGAL
    @LOG
    public void verifySectionPropertiesBoxUserInterface(String uuid) {

        //Storing APV and PREP Rendition UUIDs
        if (uuid.equals("APV"))
            uuid = renditionAPVUuid;
        else if (uuid.equals("PREP"))
            uuid = renditionPREPUuid;

        ArrayList<String> tabs = new ArrayList<>(Arrays.asList("Section properties", "Proposed/Approved Tracking Information",
                "PREP Tracking Information", "System Properties"));

        //Finding the Renditions with Rendition UUID with APV and PREP
        sourceNavigateAngularLeftSidePanePage().clickFindButtonOnLeftPane();
        sourceNavigateAngularLeftSidePanePage().setFindValue("Rendition UUID", uuid);
        sourceNavigateAngularLeftSidePanePage().clickFindButton();
        DateAndTimeUtils.takeNap(DateAndTimeUtils.THREE_SECONDS);
        sourceNavigateAngularPage().rightClickRenditions();

        //Section tab clicking and selecting first row
        sourceNavigateAngularTabsPage().click(SECTION_TAB);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        sourceNavigateAngularPage().rightClick((format(SECTION_ROW, 0)));
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        sourceNavigateAngularPage().clickContextMenuItem(SECTION_PROPERTIES);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);

        //Verifying Display of Header and Buttons
        assertThatDisplayOfHeadersAndButtons();
        DateAndTimeUtils.takeNap(DateAndTimeUtils.FOUR_SECONDS);

        //Verifying Display of 4 tabs 1.Section Properties 2.Proposed/Approved Tracking Information

        for (String tab : tabs)
            assertThatDisplayOfSectionPropertiesTabs(tab);

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
     * Test Case ID:723101_TC2
     * Description:Section Properties: Section properties tab: verify the tab UI content and functionality
     */
    @ParameterizedTest(name = "Test: {displayName}; Index: {index}; Arguments: {arguments}")
    @MethodSource("provideRenditionUUID")
    @EDGE
    @LEGAL
    @LOG
    public void verifySectionPropertiesTabUIContentAndFunctionality(String uuid) {
        //Storing APV and PREP Rendition UUIDs
        if (uuid.equals("APV"))
            uuid = renditionAPVUuid;
        else if (uuid.equals("PREP"))
            uuid = renditionPREPUuid;

        //Finding the Renditions with Rendition UUID with APV and PREP
        sourceNavigateAngularLeftSidePanePage().clickFindButtonOnLeftPane();
        sourceNavigateAngularLeftSidePanePage().setFindValue("Rendition UUID", uuid);
        sourceNavigateAngularLeftSidePanePage().clickFindButton();
        DateAndTimeUtils.takeNap(DateAndTimeUtils.THREE_SECONDS);
        sourceNavigateAngularPage().rightClickRenditions();

        //Section tab clicking and selecting first row
        sourceNavigateAngularTabsPage().click(SECTION_TAB);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        sourceNavigateAngularPage().rightClick((format(SECTION_ROW, 0)));
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        sourceNavigateAngularPage().clickContextMenuItem(SECTION_PROPERTIES);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);

        //Verifying fields in Section Properties Tab
        assertThatFieldsViewMode("Section Number ", "Section Delta Count ");
        assertThatTextFieldsAndCalendarOptions("Effective Date", "Assigned Date");
        assertThatDisplayOfDropdowns("Assigned User", "Difficulty Level");
        assertThatDisplayOfTextArea("Section Instructions ");

        String currentDate = DateAndTimeUtils.getCurrentDateMMddyyyyNoDelimeters();
        sourceNavigateAngularPage().click(format(CALENDAR_OPTION, "Effective Date"));
        sourceNavigateAngularPage().sendTextToTextbox(format(LABEL_TEXT_FIELD, "Effective Date"), currentDate);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);

        sourceNavigateAngularPage().click(format(CALENDAR_OPTION, "Assigned Date"));
        sourceNavigateAngularPage().sendTextToTextbox(format(LABEL_TEXT_FIELD, "Assigned Date"), currentDate);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);

        // Selection User:TLE TCBA-BOT from Assigned USer Dropdown
        sourceNavigateAngularPage().click(format(LABEL_TEXT_FIELD, "Assigned User"));
        sourceNavigateAngularDeltaPage().sendKeys("TLE TCBA-BOT");
        sourceNavigateAngularLockReportPage().pressEnter();
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);

        // Selecting Difficulty level
        sourceNavigateAngularDeltaPage().click(SECTION_PROPERTIES_DIFFICULTY_LEVEL_DROPDOWN);
        sourceNavigateAngularDeltaPage().click(String.format(DIFFICULTY_LEVEL_DROPDOWN_VALUE, "E1"));
        sourceNavigateAngularPage().waitForPageLoaded();

        // Entering text in Section Instructions Test area
        sourceNavigateAngularPage().sendTextToTextbox(format(SECTION_INSTRUCTIONS, "Section Instructions "), "This is testing data for Section Instructions");
        DateAndTimeUtils.takeNap(DateAndTimeUtils.FOUR_SECONDS);

        sourceNavigateAngularPage().click(SUBMIT);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.FOUR_SECONDS);

        sourceNavigateAngularPage().rightClick((format(SECTION_ROW, 0)));
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        sourceNavigateAngularPage().clickContextMenuItem(SECTION_PROPERTIES);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);

        //Closing Section Properties Pop Up
        sourceNavigateAngularPage().clickCancel();
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        assertThatDisplayOfPopUpElements("Confirmation", "Are you sure you want to cancel?", SOURCE_NAV_ASSIGN_USER_CANCEL, CONFIRM_BUTTON, CLOSE_UI_BUTTON);

        sourceNavigateAngularPage().click(SOURCE_NAV_ASSIGN_USER_CANCEL);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        assertThatPopUpMessageDisappearedOrNot("Are you sure you want to cancel?");

        sourceNavigateAngularPage().click(CANCEL_BUTTON);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        assertThatDisplayOfPopUpElements("Confirmation", "Are you sure you want to cancel?", CANCEL_BUTTON, CONFIRM_BUTTON, CLOSE_UI_BUTTON);
        sourceNavigateAngularPage().click(CONFIRM_BUTTON);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);

        sourceNavigateAngularPage().rightClick((format(SECTION_ROW, 0)));
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        sourceNavigateAngularPage().clickContextMenuItem(SECTION_PROPERTIES);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);

        sourceNavigateAngularPage().click(CANCEL_BUTTON);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        assertThatDisplayOfPopUpElements("Confirmation", "Are you sure you want to cancel?", CANCEL_BUTTON, CONFIRM_BUTTON, CLOSE_UI_BUTTON);

        sourceNavigateAngularPage().click(format(CLOSE_UI_BUTTON, "Confirmation"));
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        assertThatPopUpMessageDisappearedOrNot("Are you sure you want to cancel?");

        String headerText = sourceNavigateAngularDeltaPage().getElementsText(HEADER);
        sourceNavigateAngularPage().click(format(CLOSE_UI_BUTTON, headerText));
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        assertThatDisplayOfPopUpElements("Confirmation", "Are you sure you want to cancel?", CANCEL_BUTTON, CONFIRM_BUTTON, CLOSE_UI_BUTTON);
        sourceNavigateAngularPage().click(SOURCE_NAV_ASSIGN_USER_CANCEL);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);

        sourceNavigateAngularPage().click(format(CLOSE_UI_BUTTON, headerText));
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        assertThatDisplayOfPopUpElements("Confirmation", "Are you sure you want to cancel?", CANCEL_BUTTON, CONFIRM_BUTTON, CLOSE_UI_BUTTON);

        sourceNavigateAngularPage().click(format(CLOSE_UI_BUTTON, "Confirmation"));
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        assertThatPopUpMessageDisappearedOrNot("Are you sure you want to cancel?");

        sourceNavigateAngularPage().click(format(CLOSE_UI_BUTTON, headerText));
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        assertThatDisplayOfPopUpElements("Confirmation", "Are you sure you want to cancel?", CANCEL_BUTTON, CONFIRM_BUTTON, CLOSE_UI_BUTTON);

        sourceNavigateAngularPage().click(CONFIRM_BUTTON);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        assertThatPopUpMessageDisappearedOrNot("Are you sure you want to cancel?");

        //Navigating to Rendition Tab
        sourceNavigateAngularTabsPage().click(RENDITION_TAB);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
    }

    /**
     * Test Case ID:723101_TC3
     * Section Properties: Section properties tab: verify the changes cannot be saved when rendition is locked
     */
    @ParameterizedTest(name = "Test: {displayName}; Index: {index}; Arguments: {arguments}")
    @MethodSource("provideRenditionUUID")
    @EDGE
    @LEGAL
    @LOG
    public void verifyChangesAreNotSavedWhenRenditionLocked(String uuid) {

        //Storing APV and PREP Rendition UUIDs
        if (uuid.equals("APV"))
            uuid = renditionAPVUuid;
        else if (uuid.equals("PREP"))
            uuid = renditionPREPUuid;

        //Finding the Renditions with Rendition UUID with APV and PREP
        sourceNavigateAngularLeftSidePanePage().clickFindButtonOnLeftPane();
        sourceNavigateAngularLeftSidePanePage().setFindValue("Rendition UUID", uuid);
        sourceNavigateAngularLeftSidePanePage().clickFindButton();
        DateAndTimeUtils.takeNap(DateAndTimeUtils.THREE_SECONDS);
        sourceNavigateAngularPage().rightClickRenditions();

        boolean lockedStatus = sourceNavigateAngularRenditionPage().verifyLockIconStateOfRendition();
        if (lockedStatus) {
            logger.information("Rendition is already locked.");
            assertThatRenditionDisplaysLockIcon(true);
        } else {
            logger.information("Rendition is not locked. Attempting to lock...");
            try {
                //Lock the rendition from UI
                sourceNavigateAngularPage().clickContextMenuItem(EDIT);
                DateAndTimeUtils.takeNap(DateAndTimeUtils.FIVE_SECONDS);
                sourceNavigateAngularPage().clickContextMenuItem(RENDITION_CONTENT_MENU);
                DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
                sourceNavigateAngularPage().switchToWindow("Iowa (Development) UAT - Dynamic Scrolling Editor");
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
            } catch (Exception e) {
                logger.information("Failed to lock rendition: " + e.getMessage());
            }

            //Section tab clicking and selecting first row
            sourceNavigateAngularTabsPage().click(SECTION_TAB);
            DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
            sourceNavigateAngularPage().rightClick((format(SECTION_ROW, 0)));
            DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
            sourceNavigateAngularPage().clickContextMenuItem(SECTION_PROPERTIES);
            DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);

            String lockedRenditionText = sourceNavigateAngularDeltaPage().getElementsText(LOCKED_ALERT_MESSAGE);
            logger.information("text displayed is" + lockedRenditionText);

            //Select Assigned User value from dropdown
            sourceNavigateAngularPage().click(format(LABEL_TEXT_FIELD, "Assigned User"));
            sourceNavigateAngularDeltaPage().sendKeys("TLE TCBA-BOT");
            sourceNavigateAngularLockReportPage().pressEnter();
            DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);

            //Click on Submit Button  and verify assigned user and difficulty level dropdown is saved
            assertThatSectionPropertiesSubmitButtonIsDisabled();
            DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);

            // Selecting Difficulty level
            sourceNavigateAngularDeltaPage().click(SECTION_PROPERTIES_DIFFICULTY_LEVEL_DROPDOWN);
            sourceNavigateAngularDeltaPage().click(String.format(DIFFICULTY_LEVEL_DROPDOWN_VALUE, "E1"));
            sourceNavigateAngularPage().waitForPageLoaded();

            //Click on Submit Button  and verify assigned user and difficulty level dropdown is saved
            assertThatSectionPropertiesSubmitButtonIsDisabled();
            DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);

            sourceNavigateAngularPage().click(CANCEL_BUTTON);
            DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);

            sourceNavigateAngularPage().rightClick((format(SECTION_ROW, 0)));
            DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
            sourceNavigateAngularPage().clickContextMenuItem(SECTION_PROPERTIES);
            DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
            assertThatSectionPropertiesSubmitButtonIsDisabled();

            sourceNavigateAngularPage().click(CANCEL_BUTTON);
            DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);

            //Navigating to Rendition Tab
            sourceNavigateAngularTabsPage().click(RENDITION_TAB);
        }
    }

    /**
     * Test Case ID:723101_TC4
     * Description:Section Properties: Proposed/Approved Tracking Information tab: verify the tab UI content and functionality
     */
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void verifyProposedApprovedTrackingInformationContentAndFunctionality() {

        String loggedUserName = "TLE TCBA-BOT";

        String[] attributeLabelNames = {"Images Sent Out", "Images Completed", "Tabular Requested", "Tabular Started", "Tabular Completed"};
        String currentDate = DateAndTimeUtils.getCurrentDateMMddyyyyNoDelimeters();

        //Finding the Renditions with Rendition UUID with APV and PREP
        sourceNavigateAngularLeftSidePanePage().clickFindButtonOnLeftPane();
            sourceNavigateAngularLeftSidePanePage().setFindValue("Rendition UUID", renditionAPVUuid);
            sourceNavigateAngularLeftSidePanePage().clickFindButton();
            DateAndTimeUtils.takeNap(DateAndTimeUtils.THREE_SECONDS);
            sourceNavigateAngularPage().rightClickRenditions();

            //Section tab clicking and selecting first row
            sourceNavigateAngularTabsPage().click(SECTION_TAB);
            DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
            sourceNavigateAngularPage().rightClick((format(SECTION_ROW, 0)));
            DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
            sourceNavigateAngularPage().clickContextMenuItem(SECTION_PROPERTIES);
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
            DateAndTimeUtils.takeNap(DateAndTimeUtils.THREE_SECONDS);
            sourceNavigateAngularPage().waitForPageLoaded();

            sourceNavigateAngularPage().rightClick((format(SECTION_ROW, 0)));
            DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
            sourceNavigateAngularPage().clickContextMenuItem(SECTION_PROPERTIES);
            DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
            sourceNavigateAngularPage().click(format(ANY_TAB_NAME, "Proposed/Approved Tracking Information"));

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

            //Closing Section Properties Pop Up
            DateAndTimeUtils.takeNap(DateAndTimeUtils.ONE_SECOND);
            sourceNavigateAngularPage().clickCancel();
            DateAndTimeUtils.takeNap(DateAndTimeUtils.ONE_SECOND);
            sourceNavigateAngularPage().clickConfirm();

            sourceNavigateAngularPage().rightClick((format(SECTION_ROW, 0)));
            DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
            sourceNavigateAngularPage().clickContextMenuItem(SECTION_PROPERTIES);
            DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
            sourceNavigateAngularPage().click(format(ANY_TAB_NAME, "PREP Tracking Information"));

            assertThatPREPTrackingInputFieldsAreReadOnly();
            DateAndTimeUtils.takeNap(DateAndTimeUtils.FOUR_SECONDS);

            sourceNavigateAngularPage().click(SUBMIT);
            DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);

            //clickOn Section Properties left hand column tab and add Fields
            for (String columnName : attributeLabelNames)
                sourceNavigateAngularLeftSidePanePage().filterForColumnAndSelectUnderSpecificTab("SECTION", columnName);

            DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);

            sourceNavigateAngularPage().scrollToRight(format(SECTION_ROW, 0));
            DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);

            //Navigating to Rendition Tab
            sourceNavigateAngularTabsPage().click(RENDITION_TAB);
            DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);

    }

    /**
     * Test Case ID:723101_TC5
     * Description:Section Properties: PREP Tracking Information: verify the tab UI content and functionality
     */
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void verifyPREPTrackingInformationTabContentAndFunctionality() {
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
            sourceNavigateAngularLeftSidePanePage().setFindValue("Rendition UUID", renditionPREPUuid);
            sourceNavigateAngularLeftSidePanePage().clickFindButton();
            DateAndTimeUtils.takeNap(DateAndTimeUtils.THREE_SECONDS);
            sourceNavigateAngularPage().rightClickRenditions();

            //Section tab clicking and selecting first row
            sourceNavigateAngularTabsPage().click(SECTION_TAB);
            DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
            sourceNavigateAngularPage().rightClick((format(SECTION_ROW, 0)));
            DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
            sourceNavigateAngularPage().clickContextMenuItem(SECTION_PROPERTIES);
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
                sourceNavigateAngularDeltaPage().click(format(CALENDAR_OPTION, labelName));
                sourceNavigateAngularPage().sendTextToTextbox(format(LABEL_TEXT_FIELD, labelName), currentDate);
            }

            DateAndTimeUtils.takeNap(DateAndTimeUtils.FIVE_SECONDS);

            for (String id : checkBoxLabelIDs) {
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

            sourceNavigateAngularPage().rightClick((format(SECTION_ROW, 0)));
            DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
            sourceNavigateAngularPage().clickContextMenuItem(SECTION_PROPERTIES);
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

            //Closing Section Properties Pop Up
            DateAndTimeUtils.takeNap(DateAndTimeUtils.ONE_SECOND);
            sourceNavigateAngularPage().clickCancel();
            DateAndTimeUtils.takeNap(DateAndTimeUtils.ONE_SECOND);
            sourceNavigateAngularPage().clickConfirm();

            DateAndTimeUtils.takeNap(DateAndTimeUtils.THREE_SECONDS);
            sourceNavigateAngularPage().waitForPageLoaded();

            sourceNavigateAngularPage().rightClick((format(SECTION_ROW, 0)));
            DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
            sourceNavigateAngularPage().clickContextMenuItem(SECTION_PROPERTIES);
            DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
            sourceNavigateAngularPage().click(format(ANY_TAB_NAME, "Proposed/Approved Tracking Information"));

            assertThatProposedApprovedTrackingInformationInputFieldsAreReadOnly();
            DateAndTimeUtils.takeNap(DateAndTimeUtils.FOUR_SECONDS);

            sourceNavigateAngularPage().click(SUBMIT);
            DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);

            for (String columnName : attributeLabelNames)
                sourceNavigateAngularLeftSidePanePage().filterForColumnAndSelectUnderSpecificTab("SECTION", columnName);

            DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);

            sourceNavigateAngularPage().scrollToRight(format(SECTION_ROW, 0));
            DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);

            //Navigating to Rendition Tab
            sourceNavigateAngularTabsPage().click(RENDITION_TAB);
            DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);

    }

    /**
     * Test Case ID:723101_TC6
     * Description:Section Properties: System Properties: verify the tab UI content and functionality
     */
    @ParameterizedTest(name = "Test: {displayName}; Index: {index}; Arguments: {arguments}")
    @MethodSource("provideRenditionUUID")
    @EDGE
    @LEGAL
    @LOG
    public void verifySystemPropertiesTabUIContentAndFunctionality(String renditionUUID) {
        //Storing APV and PREP Rendition UUIDs
        if (renditionUUID.equals("APV"))
            renditionUUID = renditionAPVUuid;
        else if (renditionUUID.equals("PREP"))
            renditionUUID = renditionPREPUuid;
        String[] systemPropertiesTabFields = {"Section UUID ", "Source Rendition UUID ", "Bill ID ", "Content Type ID ", "Document UUID ",
                "Legislation Type ID ", "Serial Number ", "Rendition Status ID ", "Source Lineage UUID ", "Content Set ID ", "Correlation ID ",
                "Doc Type ID ", "Jurisdiction ID ", "Session ID "};

        //Finding the Renditions with Rendition UUID with APV and PREP
        sourceNavigateAngularLeftSidePanePage().clickFindButtonOnLeftPane();
            sourceNavigateAngularLeftSidePanePage().setFindValue("Rendition UUID", renditionUUID);
            sourceNavigateAngularLeftSidePanePage().clickFindButton();
            DateAndTimeUtils.takeNap(DateAndTimeUtils.THREE_SECONDS);
            sourceNavigateAngularPage().rightClickRenditions();

            //Section tab clicking and selecting first row
            sourceNavigateAngularTabsPage().click(SECTION_TAB);
            DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
            sourceNavigateAngularPage().rightClick((format(SECTION_ROW, 0)));
            DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
            sourceNavigateAngularPage().clickContextMenuItem(SECTION_PROPERTIES);
            DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);

            sourceNavigateAngularPage().click(format(ANY_TAB_NAME, "System Properties"));
            for (String filed : systemPropertiesTabFields)
                assertThatInputFieldsViewMode(filed);
            DateAndTimeUtils.takeNap(DateAndTimeUtils.FOUR_SECONDS);

            sourceNavigateAngularPage().click(SUBMIT);
            DateAndTimeUtils.takeNap(DateAndTimeUtils.ONE_SECOND);

            //Navigating to Rendition Tab
            sourceNavigateAngularTabsPage().click(RENDITION_TAB);
            DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);

    }
    @EDGE
    @LEGAL
    @LOG
    @Test

    public void sectionEditIntegrationProperties() {
        //Finding the Renditions with Rendition UUID with APV and PREP
        SourceDatapodConfiguration.getConfig().setSrcContentType(SrcContentType.LAW);
        datapodObjects.add(SourceDataMockingNew.Iowa.Small.PREP.insert());
        SourceDatapodConfiguration.getConfig().setSrcContentType(SrcContentType.LAW);
        datapodObjects.add(SourceDataMockingNew.Iowa.Small.PREP.insert());
        SourceDatapodConfiguration.getConfig().setSrcContentType(SrcContentType.CRT);
        datapodObjects.add(SourceDataMockingNew.USCA.Small.PREP.insert());
        sourceNavigateAngularLeftSidePanePage().clickFindButtonOnLeftPane();

        String docNumberFilterExtract = datapodObjects.get(0).getRenditions().get(0).getRenditionUUID() + " , " + datapodObjects.get(1).getRenditions().get(0).getRenditionUUID();
        logger.information("rendition id" + docNumberFilterExtract);
        sectionEditIntegrationProperties(docNumberFilterExtract);
        String diffContentType = datapodObjects.get(0).getRenditions().get(0).getRenditionUUID() + " , " + datapodObjects.get(2).getRenditions().get(0).getRenditionUUID();
        //sourceNavigateAngularLeftSidePanePage().clickFindButtonOnLeftPane();
        sourceNavigateAngularLeftSidePanePage().clear(format(FIND_TEXT_FIELD_PATTERN, "Rendition UUID"));
        DateAndTimeUtils.takeNap(DateAndTimeUtils.THREE_SECONDS);
        sectionEditIntegrationProperties(diffContentType);

    }
    public static void sectionEditIntegrationProperties(String docNumberFilter) {
        sourceNavigateAngularLeftSidePanePage().setFindValue("Rendition UUID", docNumberFilter);
        sourceNavigateAngularLeftSidePanePage().clickFindButton();
        DateAndTimeUtils.takeNap(DateAndTimeUtils.THREE_SECONDS);
        sourceNavigateAngularPage().selectTwoRenditions((format(RENDITION_ROW_PATTERN, 0)), (format(RENDITION_ROW_PATTERN, 1)));
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        sourceNavigateAngularPage().rightClick(FIRST_RENDITION_ROW);
        //sourceNavigateAngularPage().rightClickRenditions();
        sourceNavigateAngularLeftSidePanePage().click(SECTION_TAB);
        sourceNavigateAngularPage().selectTwoRenditions((format(SECTION_ROW, 0)), (format(SECTION_ROW, 1)));
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        sourceNavigateAngularLeftSidePanePage().rightClick((format(SECTION_ROW, 0)));
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        sourceNavigateAngularPage().clickContextSubMenuItem(EDIT, INTEGRATION_PROPERTIES);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        assertThatIntegrationPropertiesUI(true);
        closeIntegrationPropertiesWindow();
        sourceNavigateAngularPage().click(RENDITION_TAB);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
    }

    @EDGE
    @LEGAL
    @LOG
    @Test
    public void integrationPropertiesMultiplePrepSection() {

        SourceDatapodConfiguration.getConfig().setSrcContentType(SrcContentType.LAW);
        datapodObjects.add(SourceDataMockingNew.Iowa.Small.PREP.insert());
        SourceDatapodConfiguration.getConfig().setSrcContentType(SrcContentType.BILL);
        datapodObjects.add(SourceDataMockingNew.Iowa.Small.PREP.insert());
        SourceDatapodConfiguration.getConfig().setSrcContentType(SrcContentType.CRT);
        datapodObjects.add(SourceDataMockingNew.USCA.Small.PREP.insert());
        sourceNavigateAngularLeftSidePanePage().clickFindButtonOnLeftPane();
        String docNumberFilterExtract = datapodObjects.get(0).getRenditions().get(0).getRenditionUUID() + " , " + datapodObjects.get(1).getRenditions().get(0).getRenditionUUID();
        logger.information("rendition id" + docNumberFilterExtract);
        integrationPropertiesUpdate(docNumberFilterExtract);
        sourceNavigateAngularPage().click(RENDITION_TAB);


    }
    private void lockSectionRendition() {
        //Lock the rendition from UI
        sourceNavigateAngularPage().clickContextMenuItem(EDIT);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.FIVE_SECONDS);
        sourceNavigateAngularPage().clickContextMenuItem(SECTIONS);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        sourceNavigateAngularPage().switchToWindow("Iowa (Development) UAT - Dynamic Scrolling Editor");
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TEN_SECONDS);
        editorPage().waitForPageLoaded();
        editorPage().waitForElementGone(EditorPageElements.COMMAND_IN_PROGRESS);
        //DateAndTimeUtils.takeNap(DateAndTimeUtils.TEN_SECONDS);
        editorPage().waitForElement(EditorToolbarPageElements.CLOSE_DOC);
        editorPage().closeCurrentWindowIgnoreDialogue();

        //Refresh the grid
        editorPage().switchToWindow(PAGE_TITLE);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        sourceNavigateAngularPage().clickRefreshSectionTableData();
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);

    }
    @EDGE
    @LEGAL
    @LOG
    @Test
    public void sectionTabLockingValidation() {
        /*SourceDatapodConfiguration.getConfig().setSrcContentType(SrcContentType.LAW);
        datapodObjects.add(SourceDataMockingNew.Iowa.Small.PREP.insert());
        SourceDatapodConfiguration.getConfig().setSrcContentType(SrcContentType.BILL);
        datapodObjects.add(SourceDataMockingNew.Iowa.Small.PREP.insert());
        SourceDatapodConfiguration.getConfig().setSrcContentType(SrcContentType.CRT);
        datapodObjects.add(SourceDataMockingNew.USCA.Small.PREP.insert());
        sourceNavigateAngularLeftSidePanePage().clickFindButtonOnLeftPane();
        String docNumberFilterExtract = datapodObjects.get(0).getRenditions().get(0).getRenditionUUID() + " , " + datapodObjects.get(1).getRenditions().get(0).getRenditionUUID();
        logger.information("rendition id" + docNumberFilterExtract);*/

        SourceDatapodConfiguration.getConfig().setSrcContentType(SrcContentType.LAW);
        datapodObjects.add(SourceDataMockingNew.Iowa.Small.PREP.insert());
        SourceDatapodConfiguration.getConfig().setSrcContentType(SrcContentType.BILL);
        datapodObjects.add(SourceDataMockingNew.Iowa.Small.PREP.insert());
        SourceDatapodConfiguration.getConfig().setSrcContentType(SrcContentType.CRT);
        datapodObjects.add(SourceDataMockingNew.USCA.Small.PREP.insert());
        sourceNavigateAngularLeftSidePanePage().clickFindButtonOnLeftPane();
        String docNumberFilterExtract = datapodObjects.get(0).getRenditions().get(0).getRenditionUUID() + " , " + datapodObjects.get(1).getRenditions().get(0).getRenditionUUID();
        logger.information("rendition id" + docNumberFilterExtract);
        String[] attributeLabelNames = {"Instruction Note", "Date Suppress"};
        sourceNavigateAngularLeftSidePanePage().setFindValue("Rendition UUID", docNumberFilterExtract);
        sourceNavigateAngularLeftSidePanePage().clickFindButton();
        DateAndTimeUtils.takeNap(DateAndTimeUtils.THREE_SECONDS);
        sourceNavigateAngularPage().selectTwoRenditions((format(RENDITION_ROW_PATTERN, 0)), (format(RENDITION_ROW_PATTERN, 1)));
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        sourceNavigateAngularPage().rightClick(FIRST_RENDITION_ROW);
        //sourceNavigateAngularPage().rightClickRenditions();
        sourceNavigateAngularLeftSidePanePage().click(SECTION_TAB);

      //  sourceNavigateAngularLeftSidePanePage().click(SECTION_TAB);
        sourceNavigateAngularPage().selectTwoRenditions((format(SECTION_ROW, 0)), (format(SECTION_ROW, 1)));
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        sourceNavigateAngularLeftSidePanePage().rightClick((format(SECTION_ROW, 0)));
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
  //      sourceNavigateAngularPage().clickContextSubMenuItem(EDIT, SECTIONS);
        boolean lockedStatus = sourceNavigateAngularRenditionPage().verifyLockIconStateOfRendition();
        logger.information("locked rendition status is" + lockedStatus);
        if (lockedStatus) {
            logger.information("Rendition is already locked.");
            assertThatRenditionDisplaysLockIcon(true);
        } else {
            logger.information("Rendition is not locked. Attempting to lock...");
            try {
                lockSectionRendition();
                //Verify the lock icon for the rendition
                assertThatRenditionDisplaysLockIcon(true);
            } catch (Exception e) {
                logger.information("Failed to lock rendition: " + e.getMessage());
            }
        }
        sourceNavigateAngularPage().click(RENDITION_TAB);
    }

    public static void integrationPropertiesUpdate(String docNumberFilter) {
        String[] attributeLabelNames = {"Instruction Note", "Date Suppress"};
        sourceNavigateAngularLeftSidePanePage().setFindValue("Rendition UUID", docNumberFilter);
        sourceNavigateAngularLeftSidePanePage().clickFindButton();
        DateAndTimeUtils.takeNap(DateAndTimeUtils.THREE_SECONDS);
        sourceNavigateAngularPage().selectTwoRenditions((format(RENDITION_ROW_PATTERN, 0)), (format(RENDITION_ROW_PATTERN, 1)));
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        sourceNavigateAngularPage().rightClick(FIRST_RENDITION_ROW);
        //sourceNavigateAngularPage().rightClickRenditions();
        sourceNavigateAngularLeftSidePanePage().click(SECTION_TAB);
        sourceNavigateAngularPage().selectTwoRenditions((format(SECTION_ROW, 0)), (format(SECTION_ROW, 1)));
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        sourceNavigateAngularLeftSidePanePage().rightClick((format(SECTION_ROW, 0)));
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        sourceNavigateAngularPage().clickContextSubMenuItem(EDIT, INTEGRATION_PROPERTIES);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        //sourceNavigateAngularPage().click(EFFECTIVE_CALENDER);
        sourceNavigateAngularPage().sendTextToTextbox(EFFECTIVE_DATE_INPUT, DateAndTimeUtils.getCurrentDateMMddyyyyNoDelimeters());
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        sourceNavigateAngularPage().click(format(SourceNavigateAngularSectionPageElements.SUPPRESS_DATE_ON_WESTLAW_RADIOBUTTON," Yes "));
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        sourceNavigateAngularPage().sendTextToTextbox(format(INSTRUCTION_NOTE_INPUT_FIELD, "Section Instructions "), "This is testing data for Section Instructions");
        DateAndTimeUtils.takeNap(DateAndTimeUtils.FOUR_SECONDS);
        sourceNavigateAngularPage().click(SUBMIT);
        //closeIntegrationPropertiesWindow();
        for (String columnName : attributeLabelNames) {
            sourceNavigateAngularLeftSidePanePage().filterForColumnAndSelectUnderSpecificTab("SECTION", columnName);
        }
       /* verifyIntegrationPropertiesColumnValues(INSTRUCTION_NOTE_INPUT_FIELD,"This is testing data for Section Instructions");
        verifyIntegrationPropertiesColumnValues(EFFECTIVE_DATE_INPUT,DateAndTimeUtils.getCurrentDateMMddyyyyNoDelimeters());
        verifyIntegrationPropertiesColumnValues(SUPPRESS_DATE_ON_WESTLAW_RADIOBUTTON,"Yes");*/
    }
    public static void closeIntegrationPropertiesWindow()
    {        sourceNavigateAngularPage().click(INTEGRATION_CLOSE_ICON);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
    }




    private static Stream<Arguments> provideRenditionUUID() {
        return Stream.of(
                Arguments.of("APV"),
                Arguments.of("PREP")
        );
    }
    public static void verifyIntegrationPropertiesColumnValues(String xpath, String text) {
        List<WebElement> value = null;
        String columnId = "";
        if(xpath.contains("Instruction Note")){
            columnId = "instructionNote";
        }
        else {
            columnId = "effectiveDate";
        }

        try {
            value = driver().findElements(By.xpath("//source-nav-root//div[@role='gridcell' and @col-id='" + columnId + "']"));
            if (value.isEmpty()) {
                logger.information("No elements found with column ID: " + columnId);
            } else {
                logger.information("The column value is " + value.size());
            }
        } catch (Exception e) {
            logger.information("An error occurred while trying to find elements with column ID: " + columnId);
        }
        for (int i = 0; i < value.size(); i++) {
            WebElement element=value.get(i);
            assertThat(sourceNavigateAngularPage().getElementsText(element)).
                    as("Entered column value " + text + "not displayed correctly under selected rendition Tab")
                    .isEqualTo(text);
        }

    }
}
