package com.thomsonreuters.codes.codesbench.quality.tests.sourcenavigateangular.general;

import com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.SourceNavigateAngularPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.SourceNavigateAngularTabsPageElements;
import com.thomsonreuters.codes.codesbench.quality.tests.sourcenavigateangular.assertions.SourceNavigateAngularAssertions;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.EDGE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.CommonDataMocking;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.source.SourceDataMockingNew;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.source.datapod.SourceDatapodObject;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import java.sql.Connection;

import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.SourceNavigateAngularContextMenuItemsPageElements.*;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.SourceNavigateAngularFiltersAndSortsPageElements.*;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.SourceNavigateAngularPageElements.*;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.SourceNavigateAngularRenditionPageElements.submit;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.SourceNavigateAngularSectionPageElements.*;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.SourceNavigateAngularTabsPageElements.RENDITION_TAB;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.SourceNavigateAngularTabsPageElements.SECTION_TAB;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.popups.SourceNavigateAngularPopUpPageElements.CONFIRM_BUTTON;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.popups.SourceNavigateAngularPopUpPageElements.SOURCE_NAV_ASSIGN_USER_CANCEL;
import static com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils.disconnect;
import static java.lang.String.format;

public class SNGeneralSectionPageTests extends SourceNavigateAngularAssertions {
    SourceDatapodObject datapodObject;
    Connection connection;
    String renditionUuid;

    @BeforeEach
    public void mockData(TestInfo testInfo) {
        if (!(testInfo.getDisplayName().equals("verifySecetionTabClearFilterTest"))&&
                !(testInfo.getDisplayName().equals("assignUserandDatetothedocuments()"))) {
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
    /*
      User Story 722130: [HALCYONST-15806] Source Navigation: Clear filter
        */
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void verifySectionTabClearFilterTest() {

        String rendition_uuid = "I0002B5509AE311E6A7AC9C82917CBFA7";
        sourceNavigateAngularLeftSidePanePage().clickFindButtonOnLeftPane();
        sourceNavigateAngularLeftSidePanePage().setFindValue("Rendition UUID", rendition_uuid);
        sourceNavigateAngularLeftSidePanePage().clickFindButton();
        sourceNavigateAngularPage().click(SourceNavigateAngularPageElements.FIRST_RENDITION_ROW);

        sourceNavigateAngularPage().click(SECTION_TAB);
        String totalNumberOfSections = sourceNavigateAngularSectionPage().getTotalNumberOfSections();
        sourceNavigateAngularFiltersAndSortsPage().openMenuColumnHeaderForTabs(COLUMN_FILTER_BUTTON_PATTERN_SECTION_TAB,RENDITION_STATUS);
        sourceNavigateAngularFiltersAndSortsPage().openFilterTabColumnHeader();
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        sourceNavigateAngularFiltersAndSortsPage().clickCheckbox(SELECT_ALL);
        sourceNavigateAngularFiltersAndSortsPage().clickCheckbox(BLANKS);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);

        //Verify current section numbers less than to the total section numbers
        assertThatFilteredRenditionsNumberLessThanTotalRenditions(sourceNavigateAngularSectionPage().getTotalNumberOfSections(), totalNumberOfSections);

        //Clearing Filter
        sourceNavigateAngularSectionPage().isElementDisplayed(CLEAR_FILTERS_SECTION_TAB);
        sourceNavigateAngularSectionPage().clickSectionTabClearFiltersButton();
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);

        //verify current section numbers is equal to the total section numbers
        assertThatTotalRenditionsNumberMatchingToExpected(sourceNavigateAngularSectionPage().getTotalNumberOfSections(), totalNumberOfSections);
        sourceNavigateAngularTabsPage().click(RENDITION_TAB);
    }
    /**
     * Access Source Navigate UI app > Click Renditions >Click Sections tab Enable the  Assigned User  and  Assigned Date  columns via the  Columns  UI on the left-side pane for all the tabs
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
     * -	If click on Calendar  button, todays date is displayed.
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
    public void assignUserAndDateOftheDocuments()

    {
        String rendition_uuid = "I010E8F200FA511E5BCA8C9C17001CA39";
        String userName1 = "TLE TCBA-BOT";
        String userName2 = "TLE TCBB-BOT";

        sourceNavigateAngularLeftSidePanePage().clickFindButtonOnLeftPane();
        sourceNavigateAngularLeftSidePanePage().setFindValue("Rendition UUID", rendition_uuid);
        sourceNavigateAngularLeftSidePanePage().clickFindButton();

        sourceNavigateAngularPage().click(SourceNavigateAngularPageElements.FIRST_RENDITION_ROW);
        sourceNavigateAngularPage().click(SECTION_TAB);
        sourceNavigateAngularLeftSidePanePage().filterForColumnAndSelectUnderSpecificTab("SECTION", "Assigned User");
        sourceNavigateAngularLeftSidePanePage().filterForColumnAndSelectUnderSpecificTab("SECTION", "Assigned Date");
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        sourceNavigateAngularPage().rightClick((format(SECTION_ROW,2)));


        //Right click Modify--> AssignUser
        sourceNavigateAngularPage().clickContextSubMenuItem(MODIFY, ASSIGN_USER);
        sourceNavigateAngularPage().waitForPageLoaded();

        sourceNavigateAngularPage().click(ASSIGN_USER_DROP_DOWN);
        sourceNavigateAngularPage().click(BLANK_USER);
        sourceNavigateAngularPage().clear(ASSIGNED_TO_DATE);
        sourceNavigateAngularPage().click(Submit_Button);
        if (sourceNavigateAngularPage().doesElementExist(CONFIRM_BUTTON))
        {
            sourceNavigateAngularPage().click(CONFIRM_BUTTON);
        }

        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        sourceNavigateAngularPage().rightClick((format(SECTION_ROW,2)));
        sourceNavigateAngularPage().clickContextSubMenuItem(MODIFY, ASSIGN_USER);
        sourceNavigateAngularPage().waitForPageLoaded();

        sourceNavigateAngularPage().clear(ASSIGN_USER_USER);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        sourceNavigateAngularPage().sendKeysToElement(ASSIGN_USER_USER,userName1);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.ONE_SECOND);
        sourceNavigateAngularPage().click(format(SPAN_CONTAINS_TEXT, userName1));

        sourceNavigateAngularPage().sendKeysToElement(ASSIGNED_TO_DATE, DateAndTimeUtils.getCurrentDateMMddyyyyNoDelimeters());
        sourceNavigateAngularPage().click(Submit_Button);
        if (sourceNavigateAngularPage().doesElementExist(CONFIRM_BUTTON))
        {
            sourceNavigateAngularPage().click(CONFIRM_BUTTON);
        }
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        sourceNavigateAngularFiltersAndSortsPage().openMenuColumnHeaderForTabs(COLUMN_FILTER_SECTIONNUMBER_PATTERN_SECTION_TAB,SECTION_NUMBER);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        sourceNavigateAngularFiltersAndSortsPage().openFilterTabColumnHeader();
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);

        sourceNavigateAngularFiltersAndSortsPage().sendKeysToElement(SECTION_NUMBER_FILTER_VALUE,"3");
        sourceNavigateAngularPage().scrollPageHorizontallyShiftEnd((format(SECTION_ROW,0)));
        assertThatRenditionAssignedDateAndUser(FIRST_SECTION_ASSIGNED_DATE,FIRST_SECTION_ASSIGNED_USER,userName1);

        sourceNavigateAngularPage().click((format(SECTION_ROW,0)));
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        sourceNavigateAngularPage().rightClick((format(SECTION_ROW,0)));
        sourceNavigateAngularPage().clickContextSubMenuItem(MODIFY, ASSIGN_USER);
        sourceNavigateAngularPage().waitForPageLoaded();

        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        sourceNavigateAngularPage().clear(ASSIGN_USER_USER);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        sourceNavigateAngularPage().sendKeysToElement(ASSIGN_USER_USER,userName2.substring(1,userName2.length()-4));
        DateAndTimeUtils.takeNap(DateAndTimeUtils.ONE_SECOND);
        sourceNavigateAngularPage().click(format(SPAN_CONTAINS_TEXT, userName2));
        sourceNavigateAngularPage().click(Submit_Button);

        assertThatMessage(ASSIGNED_USER_MESSAGE,"One or more of the selected documents is already assigned to a user. Continue?");
        sourceNavigateAngularPage().click(SOURCE_NAV_ASSIGN_USER_CANCEL);

        sourceNavigateAngularPage().click(Submit_Button);
        sourceNavigateAngularPage().click(CONFIRM_BUTTON);

        sourceNavigateAngularPage().scrollPageHorizontallyShiftEnd((format(SECTION_ROW,0)));
        assertThatRenditionAssignedDateAndUser(FIRST_SECTION_ASSIGNED_DATE,FIRST_SECTION_ASSIGNED_USER,userName2);

        sourceNavigateAngularTabsPage().click(RENDITION_TAB);
    }

    /**
     * Create/Edit/Delete Source Instructions for Section Level Documents
     */

    @Test
    @EDGE
    @LEGAL
    @LOG
    public void verifyCreate_Delete_SourceLevel_InstructionTest() {
        String text = "QEDTESTING";
        String text2 = "AUTOMATIONTESTING";
        String renditionUuid = "I0072218042BB11EC83838BEC591E7AC1"; // This must be a PREP Doc uuid
        sourceNavigateAngularLeftSidePanePage().clickFindButtonOnLeftPane();
        sourceNavigateAngularLeftSidePanePage().setFindValue("Rendition UUID", renditionUuid);
        sourceNavigateAngularLeftSidePanePage().clickFindButton();
        sourceNavigateAngularPage().waitForElementExists(format(TOTAL_RENDITIONS_NUMBER, "1"));
        sourceNavigateAngularPage().click(format(RENDITION_ROW_PATTERN, 0));
        sourceNavigateAngularTabsPage().click(SourceNavigateAngularTabsPageElements.SECTION_TAB);
        sourceNavigateAngularTabsPage().waitForPageLoaded();

        //Add Instruction Note column from left panel options
        sourceNavigateAngularLeftSidePanePage().filterForColumnAndSelectUnderSpecificTab("SECTION", "Notes");
        sourceNavigateAngularLeftSidePanePage().filterForColumnAndSelectUnderSpecificTab("SECTION", "Instruction Note");
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        sourceNavigateAngularSectionPage().isElementEnabled(NOTES_VALUE_OF_SECTION);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        assertThatMessage(NOTES_VALUE_OF_SECTION, "Add");
        sourceNavigateAngularSectionPage().click(NOTES_VALUE_OF_SECTION);
        sourceNavigateAngularPage().waitForPageLoaded();

        //Adding Instruction note
        sourceNavigateAngularSectionPage().addNotes(sectionLevelinstructions, text);
        sourceNavigateAngularRenditionPage().click(submit);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        assertThatMessage(SECTION_ROW_INSTRUCTION_NOTE, text);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        assertNotesValues(NOTES_VALUE_OF_SECTION, "§");
        sourceNavigateAngularPage().click(format(SECTION_ROW, 0));
        sourceNavigateAngularSectionPage().rightClickSection(String.format(SECTION_ROW, "0"));

        sourceNavigateAngularPage().clickContextSubMenuItem(EDIT, SECTION_NOTES);
        sourceNavigateAngularSectionPage().replaceNotesText(sectionLevelinstructions, text2);
        sourceNavigateAngularRenditionPage().click(submit);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        assertThatMessage(SECTION_ROW_INSTRUCTION_NOTE, text2);

        sourceNavigateAngularSectionPage().click(NOTES_VALUE_OF_SECTION);
        sourceNavigateAngularRenditionPage().clearRenditionNotes(sectionLevelinstructions);
        sourceNavigateAngularRenditionPage().click(submit);
        sourceNavigateAngularRenditionPage().waitForPageLoaded();
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        assertThatMessage(NOTES_VALUE_OF_SECTION, "Add");

        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        sourceNavigateAngularTabsPage().click(RENDITION_TAB);
    }
}
