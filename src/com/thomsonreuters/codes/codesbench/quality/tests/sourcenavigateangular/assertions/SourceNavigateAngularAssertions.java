package com.thomsonreuters.codes.codesbench.quality.tests.sourcenavigateangular.assertions;


import com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.*;
import com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.popups.SourceNavigateAngularPopUpPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.popups.SourceNavigateAngularToastPageElements;
import com.thomsonreuters.codes.codesbench.quality.tests.sourcenavigateangular.SourceNavigateAngularBase;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.SourceNavigateAngularContextMenuItemsPageElements.*;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.SourceNavigateAngularDeltaPageElements.*;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.SourceNavigateAngularDeltaPageElements.LABEL_TEXT_FIELD;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.SourceNavigateAngularLockReportPageElements.FIRST_CONTENT_SET;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.SourceNavigateAngularLockReportPageElements.FIRST_RENDITION_STATUS;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.SourceNavigateAngularPageElements.*;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.SourceNavigateAngularPageElements.RENDITION_SYSTEM_PROPERTIES_INPUT_FIELD;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.SourceNavigateAngularSectionPageElements.*;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.SourceNavigateAngularSectionPageElements.SECTION_PROPERTIES_BOX_LABEL;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.SourceNavigateAngularTabsPageElements.ANY_TAB_NAME;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.popups.SourceNavigateAngularPopUpPageElements.CLOSE_UI_BUTTON;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.popups.SourceNavigateAngularPopUpPageElements.HEADER;
import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;

public class SourceNavigateAngularAssertions extends SourceNavigateAngularBase {
    public List<String> foundUuids;

    // ---------- Assertions ----------

    /**
     * Feature 355128: Source Navigation: Rendition tab
     * User Story 289183: [HALCYONST-16151] Source Navigate (Angular) - Find Function
     */

    public void assertThatTotalRenditionsNumberIsEqualToNumberOfDisplayedRenditions() {
        assertThat(sourceNavigateAngularPage().getTotalDocumentsNumber(totalRenditionsNumber))
                .as("Total Renditions number should be equal to the number of renditions displayed in the grid")
                .isEqualTo(sourceNavigateAngularPage().getNumberOfRowsDisplayedInGrid(RENDITIONS_GRID_CONTAINER));
    }

    public void assertThatFilterIconAppearsInColumnHeader(String columnId) {
        assertThat(sourceNavigateAngularPage().isFilterIconExistsInColumnHeading(columnId))
                .as("Filter icon should appear in the column's heading")
                .isTrue();
    }

    public void assertThatAllFoundRenditionsHaveSameId(String columnId, String uuid, String id) {
        foundUuids = sourceNavigateAngularPage().getCellValue(RENDITIONS_GRID_CONTAINER, COLUMN_CELL_VALUE_OF_RENDITION_ROW_PATTERN, columnId);

        assertThat(foundUuids.stream().allMatch(s -> s.equals(uuid)))
                .as("All displayed renditions should have the same %s", id)
                .isTrue();
    }

    public void assertThatRequiredRenditionsAreDisplayed(String columnId, String firstUuid, String secondUuid) {
        foundUuids = sourceNavigateAngularPage().getCellValue(RENDITIONS_GRID_CONTAINER, COLUMN_CELL_VALUE_OF_RENDITION_ROW_PATTERN, columnId);
        List<String> requiredUuids = new ArrayList<>();
        requiredUuids.add(firstUuid);
        requiredUuids.add(secondUuid);

        assertThat(foundUuids.containsAll(requiredUuids))
                .as("The required UUIDs should be displayed")
                .isTrue();
        assertThat(foundUuids.size())
                .as("The number of displayed renditions is not equal to the expected number")
                .isGreaterThanOrEqualTo(requiredUuids.size());
    }

    public void assertThatCreateBookMarkWindowIsDisplayed(String originalWindow) {
        //Get the focus shifted to the newly opened tab/window handle before assertion
        for (String windowHandle : driver().getWindowHandles()) {
            if (!originalWindow.contentEquals(windowHandle)) {
                driver().switchTo().window(windowHandle);
                break;
            }
        }
        //Verify the Create Bookmark title existence
        assertThat(sourceNavigateAngularPage().doesElementExist(SourceNavigateAngularPageElements.CREATE_BOOKMARK_TITLE))
                .as("Create Bookmark window doesn't appear")
                .isTrue();
    }

    public void assertThatViewValidationWindowIsDisplayed() {
        assertThat(sourceNavigateAngularPage().doesWindowExistByTitle(SourceNavigateAngularPageElements.VIEW_VALIDATIONS_TITLE))
                .as("View Validations window did not appear")
                .isTrue();
    }

    public void assertThatIfConfirmationWindowAppeared() {
        assertThat(sourceNavigateAngularPage().doesElementExist(SourceNavigateAngularPageElements.CONFIRMATION_TITLE))
                .as("Confirmation window did not appear")
                .isTrue();
    }

    public void assertThatIfConfirmationWindowDisappeared() {
        assertThat(!(sourceNavigateAngularPage().doesElementExist(SourceNavigateAngularPageElements.CONFIRMATION_TITLE)))
                .as("Confirmation window did not appear")
                .isTrue();
    }

    public void aasertThatHighlightedRowsRemoved(List<String> selectedRow, String elementPath) {
        int row = sourceNavigateAngularPage().countElements("//source-nav-view-validations/ag-grid-angular//div[@class='ag-center-cols-container']/div");
        int column = selectedRow.size();
        for (int i = 0; i < row; i++)
            for (int j = 0; j < column; j++) {
                assertThat(!sourceNavigateAngularPage()
                        .isElementDisplayed(String.format(elementPath, i, selectedRow.get(j))))
                        .as("The target node " + selectedRow.get(j) + " " + " is not removed")
                        .isTrue();
            }
    }

    public void assertThatViewValidationContentMenuForWarningOrCiteLocatedFlag() {
        assertThat(sourceNavigateAngularPage().isElementEnabled(CLEAR_WARNING_FLAGS + "/parent::div"))
                .as("Clear Warning flag is not enabled")
                .isTrue();
        assertThat((sourceNavigateAngularPage().isElementDisabled(CLEAR_VALIDATION_FLAGS + "/parent::div")))
                .as("Clear Validation flag is enabled")
                .isTrue();
        assertThat((sourceNavigateAngularPage().isElementDisabled(VALIDATE_LINKS + "/parent::div")))
                .as("Validation Links is enabled")
                .isTrue();
    }

    public void assertThatViewValidationContentMenuForErrorFlag() {
        assertThat((sourceNavigateAngularPage().isElementDisabled(CLEAR_WARNING_FLAGS + "/parent::div")))
                .as("Clear Warning flag is enabled")
                .isTrue();
        assertThat((sourceNavigateAngularPage().isElementDisabled(CLEAR_VALIDATION_FLAGS + "/parent::div")))
                .as("Clear Validation flag is enabled")
                .isTrue();
        assertThat((sourceNavigateAngularPage().isElementDisabled(VALIDATE_LINKS + "/parent::div")))
                .as("Validation Links is enabled")
                .isTrue();
    }

    public void assertThatViewValidationContentMenuForUpdatedOrInvalidFlag() {
        assertThat(sourceNavigateAngularPage().isElementDisabled(CLEAR_WARNING_FLAGS + "/parent::div"))
                .as("Clear Warning flag is not enabled")
                .isTrue();
        assertThat((sourceNavigateAngularPage().isElementEnabled(CLEAR_VALIDATION_FLAGS + "/parent::div")))
                .as("Clear Validation flag is enabled")
                .isTrue();
        assertThat((sourceNavigateAngularPage().isElementEnabled(VALIDATE_LINKS + "/parent::div")))
                .as("Validation Links is enabled")
                .isTrue();
    }

    public void assertThatViewValidationColumns(List<String> columnExpectedFilterValues) {
        for (int i = 0; i < columnExpectedFilterValues.size(); i++) {
            if (i == 6 || i == 7 || i == 10 || i == 12) {
                sourceNavigateAngularPage().scrollToRight("600", "(//div[@class='ag-body-horizontal-scroll-viewport'])[7]");
                sourceNavigateAngularPage().waitForPageLoaded();
            }
            assertThat(sourceNavigateAngularPage()
                    .isElementDisplayed(String.format(SourceNavigateAngularPageElements.COLUMN_NAMES, columnExpectedFilterValues.get(i))))
                    .as("Column" + " " + columnExpectedFilterValues.get(i) + " " + "is not present")
                    .isTrue();
        }
    }

    public void assertThatTaxTypeAddWindowIsDisplayed() {
        assertThat(sourceNavigateAngularPage().doesElementExist(SourceNavigateAngularPageElements.TAX_TYPE_ADD_TITLE))
                .as("Confirmation window did not appear")
                .isTrue();
    }

    public void assertThatTaxTypeAddWindowIsDisappeared() {
        assertThat(sourceNavigateAngularPage().doesElementExist(SourceNavigateAngularPageElements.TAX_TYPE_ADD_TITLE))
                .as("Tax type window did not close")
                .isFalse();
    }

    public void assertThatTaxTypeAddWindowHasAllOptions(int totalAvailableTaxType) {

        assertThat(sourceNavigateAngularPage().isElementDisplayed(String.format(SourceNavigateAngularPageElements.FILTER_LIST, "1")))
                .as("Filter list on left panel is not displayed")
                .isTrue();
        assertThat(sourceNavigateAngularPage().isElementDisplayed(String.format(SourceNavigateAngularPageElements.FILTER_LIST, "2")))
                .as("Filter list on right panel is not displayed")
                .isTrue();
        assertThat(sourceNavigateAngularPage().doesElementExist(SourceNavigateAngularPageElements.TAX_TYPE_ADD_TITLE))
                .as("Confirmation window did not appear")
                .isTrue();
        assertThat(sourceNavigateAngularPage().isElementDisplayed(SourceNavigateAngularPageElements.AVAILABLE_TAX_TYPE))
                .as("Available tax type title did not appear")
                .isTrue();
        assertThat(sourceNavigateAngularPage().getElements(SourceNavigateAngularPageElements.TOTAL_AVAILABLE_ROW_COUNT).size())
                .as("Available tax type count is not present under the title or the total count is not matching with the count mentioned under the title")
                .isLessThanOrEqualTo(totalAvailableTaxType);
        assertThat(sourceNavigateAngularPage().isElementDisplayed(SourceNavigateAngularPageElements.SELECTED_TAX_TYPE))
                .as("Selected tax type title did not appear")
                .isTrue();
        assertThat(sourceNavigateAngularPage().getElementsText(SourceNavigateAngularPageElements.SELECTED_TAX_TYPE_NUM))
                .as("Count is not 0 under selected tax type title")
                .isEqualTo("0");
        assertThat(sourceNavigateAngularPage().isElementEnabled(SourceNavigateAngularPageElements.Submit_Button))
                .as("Submit button is not enabled")
                .isTrue();
        assertThat(sourceNavigateAngularPage().isElementEnabled(SourceNavigateAngularPageElements.Cancel_Button))
                .as("Cancel button is not enabled")
                .isTrue();
        assertRemoveButton(false);
        assertAddButton(false);
    }

    public void assertAddButton(boolean option) {
        if (option) {
            assertThat(sourceNavigateAngularPage().isElementEnabled(SourceNavigateAngularPageElements.ADD_BUTTON))
                    .as("Add button is disabled")
                    .isTrue();
        } else {
            assertThat(sourceNavigateAngularPage().isElementDisabled(SourceNavigateAngularPageElements.ADD_BUTTON))
                    .as("Add button is enabled")
                    .isTrue();
        }
    }

    public void assertRemoveButton(boolean option) {
        if (option) {
            assertThat(sourceNavigateAngularPage().isElementEnabled(SourceNavigateAngularPageElements.REMOVE_BUTTON))
                    .as("Remove button is disabled")
                    .isTrue();
        } else {
            assertThat(sourceNavigateAngularPage().isElementDisabled(SourceNavigateAngularPageElements.REMOVE_BUTTON))
                    .as("Remove button is enabled")
                    .isTrue();
        }
    }

    public void assertValidationsAfterSelectingTaxTypes(int totalAvailableTaxType, int addedTaxTypeCount) {
        assertThat(Integer.parseInt(sourceNavigateAngularPage().getElementsText(SourceNavigateAngularPageElements.SELECTED_TAX_TYPE_NUM)))
                .as("Selected tax type count is wrong")
                .isEqualTo(addedTaxTypeCount);
        assertThat(Integer.parseInt(sourceNavigateAngularPage().getElementsText(SourceNavigateAngularPageElements.AVAILABLE_TAX_TYPE_NUM)))
                .as("Available tax type count is wrong")
                .isEqualTo(totalAvailableTaxType - addedTaxTypeCount);
    }

    public void assertValidationsAfterDeSelectingTaxTypes(int totalCount, int removedTaxTypeCount, int totalAvailableTaxType) {
        assertThat(Integer.parseInt(sourceNavigateAngularPage().getElementsText(SourceNavigateAngularPageElements.SELECTED_TAX_TYPE_NUM)))
                .as("Selected tax type count is wrong")
                .isEqualTo(totalCount - removedTaxTypeCount);
        assertThat(Integer.parseInt(sourceNavigateAngularPage().getElementsText(SourceNavigateAngularPageElements.AVAILABLE_TAX_TYPE_NUM)))
                .as("Available tax type count is wrong")
                .isEqualTo(totalAvailableTaxType + removedTaxTypeCount);
    }

    public void assertValidationsAfterAddingTaxTypes(List<String> taxTypes) {
        sourceNavigateAngularPage().clickRefreshTableData();
        for (String verify : taxTypes) {
            assertThat(sourceNavigateAngularPage().isElementDisplayed(String.format(SourceNavigateAngularPageElements.TAX_TYPE_COULMN_ID + "[contains(text(),'%s')]", verify)))
                    .as(verify + " " + "is not present under tax type add column")
                    .isTrue();
        }
    }

    public void assertValidationsAfterRemovingTaxTypes(List<String> taxTypes) {
        sourceNavigateAngularPage().clickRefreshTableData();
        for (String verify : taxTypes) {
            assertThat(sourceNavigateAngularPage().isElementDisplayed(String.format(SourceNavigateAngularPageElements.TAX_TYPE_COULMN_ID + "[contains(text(),'%s')]", verify)))
                    .as(verify + " " + "is  present under tax type add column")
                    .isFalse();
        }
    }

    public void assertContextMenuValidationForApvDoc() {
        assertThat(sourceNavigateAngularPage().isElementDisplayed(SourceNavigateAngularContextMenuItemsPageElements.CREATE_PREPARATION_DOCUMENT))
                .as("Create Prep Document option is not displayed for this APV document")
                .isTrue();
        assertThat(sourceNavigateAngularPage().isElementDisplayed(SourceNavigateAngularContextMenuItemsPageElements.SYNC))
                .as("Sync option is not displayed for this APV document")
                .isTrue();
        assertThat(sourceNavigateAngularPage().isElementDisplayed(SourceNavigateAngularContextMenuItemsPageElements.CREATE_PREPARATION_DOCUMENT_AUTO_INTEGRATE))
                .as("Create Prep Document/Auto Integrate option is not displayed for this APV document")
                .isTrue();
    }

    public void assertContextMenuValidationForPrepDoc() {
        assertThat(sourceNavigateAngularPage().isElementDisplayed(SourceNavigateAngularContextMenuItemsPageElements.INTEGRATE))
                .as("Integrate option is not displayed for this APV document")
                .isTrue();
    }

    public void assertTheContextMenuForPrepDocUnderAllTabs(List<String> contextMenuOptions) {
        for (String verify : contextMenuOptions) {
            assertThat(sourceNavigateAngularPage().isElementDisplayed(String.format(SourceNavigateAngularContextMenuItemsPageElements.CONTEXT_MENU_OPTION, verify)))
                    .as(verify + " " + "is not present under context menu")
                    .isTrue();
        }
    }

    public void assertThatRenditionDisplaysLockIcon(boolean state) {
        assertThat(sourceNavigateAngularRenditionPage().verifyLockIconState(state))
                .as("Locked Rendition should display lock icon in the grid")
                .isTrue();
    }

    public void assertThatBackgroundBlueForselectedRenditionRow(String Renditionrow) {
        assertThat(SELECTED_RENDITION_ROW_COLOR.equals(sourceNavigateAngularPage().getBackgroundColor(Renditionrow)))
                .as("Background is not blue").isTrue();

    }

    public void assertThatRenditionDocumentNumber(String APVRenditionRow, String DocumentNumber) {
        assertThat(sourceNavigateAngularPage().getElementsText(APVRenditionRow).equals(DocumentNumber))
                .as("DocumentNumber is not matched with selected rendition row")
                .isTrue();
    }

    public void assertThatRenditionEffectiveDate(String xpath) {
        assertThat(sourceNavigateAngularPage().getElementsText(xpath).equals(DateAndTimeUtils.getCurrentDateMMddyyyy()))
                .as("Assigned Date does not match")
                .isTrue();
    }

    public void assertThatRenditionAssignedDateAndUser(String FIRST_RENDITION_ASSIGNED_DATE, String FIRST_RENDITION_ASSIGNED_USER, String USER) {
        assertThat(sourceNavigateAngularPage().getElementsText(FIRST_RENDITION_ASSIGNED_DATE).equals(DateAndTimeUtils.getCurrentDateMMddyyyy()))
                .as("Assigned Date does not match")
                .isTrue();
        assertThat(sourceNavigateAngularPage().getElementsText(FIRST_RENDITION_ASSIGNED_USER).equals(USER))
                .as("Assigned User does not match")
                .isTrue();
    }

    public void assertThatTitleLoaded(String title, boolean state) {
        assertThat(sourceNavigateAngularRenditionPage().verifyPageTitle(title, state))
                .as("Previous page Source Navigate UI should be loaded")
                .isTrue();
    }

    public void assertThatRenditionIsLocked(boolean flag) {
        DateAndTimeUtils.takeNap(DateAndTimeUtils.ONE_SECOND);
        assertThat(sourceNavigateAngularLockReportPage().verifyOnlyOneLockedRenditionExists()).isEqualTo(flag);
    }

    public void assertThatLockedRenditionContextMenuItems() {
        boolean flag1 = sourceNavigateAngularLockReportPage().doesElementExist(String.format(SourceNavigateAngularLockReportPageElements.CONTEXT_MENU_OPTION, "Unlock"));
        boolean flag2 = sourceNavigateAngularLockReportPage().doesElementExist(String.format(SourceNavigateAngularLockReportPageElements.CONTEXT_MENU_OPTION, "Force Unlock"));
        boolean flag3 = sourceNavigateAngularLockReportPage().doesElementExist(String.format(SourceNavigateAngularLockReportPageElements.CONTEXT_MENU_OPTION, "Transfer lock"));
        assertThat(flag1 & flag2 & flag3)
                .as("Expected context menu options are NOT present")
                .isTrue();
    }

    public void assertThatLockedRendtionContextMenuState() {
        boolean flag1 = sourceNavigateAngularLockReportPage().getElementsAttribute(String.format(SourceNavigateAngularLockReportPageElements.CONTEXT_MENU_OPTION_PARENT, "Unlock"), "class").contains("disabled");
        boolean flag2 = sourceNavigateAngularLockReportPage().getElementsAttribute(String.format(SourceNavigateAngularLockReportPageElements.CONTEXT_MENU_OPTION_PARENT, "Force Unlock"), "class").contains("disabled");
        boolean flag3 = sourceNavigateAngularLockReportPage().getElementsAttribute(String.format(SourceNavigateAngularLockReportPageElements.CONTEXT_MENU_OPTION_PARENT, "Transfer lock"), "class").contains("disabled");
        assertThat((!flag1) & flag2 & flag3)
                .as("Lock report context menu options are NOT as expected")
                .isTrue();
    }

    public void assertThatLockedReportUrl() {
        assertThat(sourceNavigateAngularLockReportPage().getUrl().contains("/sourceNavigateUi/source-lock-report"))
                .as("Locked Report page is NOT opened")
                .isTrue();
    }

    public void assertThatTotalRenditionsNumberMatchingToExpected(String actual, String expected) {
        assertThat(actual)
                .as("The number of displayed renditions is not equal to the expected number")
                .isEqualTo(expected);
    }

    public void assertThatFilteredRenditionsNumberLessThanTotalRenditions(String actual, String expected) {
        assertThat(Integer.parseInt(actual.replaceAll("[^0-9]", "")) < Integer.parseInt(expected.replaceAll("[^0-9]", "")))
                .as("The number of displayed renditions is not equal to the expected number")
                .isTrue();
    }

    public void assertThatFileSizeZero(String FileSizeZero) {
        assertThat(sourceNavigateAngularPage().getElementsText(FIRST_FILE_SIZE_NUMBER).equals(FileSizeZero))
                .as("File Size are Zero")
                .isTrue();
    }

    public void assertThatContextMenuOptionIsEnabled(String option, boolean value) {
        assertThat(sourceNavigateAngularLockReportPage().verifyContextMenuOptionEnabled(String.format(SourceNavigateAngularLockReportPageElements.CONTEXT_MENU_OPTION_PARENT, option)))
                .isEqualTo(value);
    }

    public void assertThatExpectedElementPresent(String xpath) {
        assertThat(sourceNavigateAngularPage().doesElementExist(xpath))
                .isTrue();
    }

    public void assertThatDifficultyLevelDropdownOptionsPresent(List<String> list) {
        assertThat(sourceNavigateAngularPopUpPage().verifyDifficultyLevelDropDownAllOptions(list))
                .isTrue();
    }

    public void assertThatDifficultyLevelWindowElements() {
        boolean diffLevelDropDown = sourceNavigateAngularPopUpPage().doesElementExist(SourceNavigateAngularPopUpPageElements.DIFFICULTY_LEVEL_DROPDOWN);
        boolean diffLevelSubmitBtn = sourceNavigateAngularPopUpPage().doesElementExist(String.format(SourceNavigateAngularPopUpPageElements.ANY_BUTTON, "Submit"));
        boolean diffLevelCancelBtn = sourceNavigateAngularPopUpPage().doesElementExist(String.format(SourceNavigateAngularPopUpPageElements.ANY_BUTTON, "Cancel"));
        assertThat(diffLevelDropDown && diffLevelSubmitBtn && diffLevelCancelBtn)
                .isTrue();
    }

    public void assertThatDifficultyLevelSelectedValueinGrid(String xpath, String expectedValue) {
        assertThat(sourceNavigateAngularPage().getElementsText(xpath))
                .as("Difficulty level is not matching to " + expectedValue)
                .isEqualTo(expectedValue);
    }

    public void assertThatDifficultyLevelDropdownSelectedValue(String value) {
        assertThat(sourceNavigateAngularPopUpPage().getElementsText(sourceNavigateAngularPopUpPage().getSelectedDropdownOption(SourceNavigateAngularPopUpPageElements.DIFFICULTY_LEVEL_DROPDOWN)))
                .as("Difficulty Value " + value + " selected is retained")
                .isEqualTo(value);
    }

    public void assertThatSectionDifficultyLevelValuesAsExpected(String expDiffVal, int startIndex) {
        //Extract the total section count
        String[] arr = sourceNavigateAngularPage().getElementsText(SourceNavigateAngularSectionPageElements.TOTAL_SECTIONS_NUMBERS).split(":");
        int sectionCount = Integer.parseInt(arr[1].trim());
        if (sectionCount >= startIndex) {
            for (int index = startIndex; index < sectionCount - 1; index++) {
                sourceNavigateAngularPage().scrollToElement(String.format(SECTION_ROW, index));
                sourceNavigateAngularPage().scrollPageHorizontallyShiftEnd(String.format(SECTION_ROW, Integer.toString(index)));
                sourceNavigateAngularPage().scrollToTop();
                assertThat(sourceNavigateAngularPage().getElementsText(String.format(SECTION_ROW, index) + String.format(SourceNavigateAngularPageElements.COLUMN_ID_PATTERN, "difficultyLevel")))
                        .as("Section Difficulty level value is NOT matching")
                        .isEqualTo(expDiffVal);
            }
        }
    }

    public void assertThatDeltaDifficultyLevelValuesAsExpected(String expDiffVal, int startIndex) {
        //Extract the total section count
        String[] arr = sourceNavigateAngularPage().getElementsText(SourceNavigateAngularDeltaPageElements.TOTAL_DELTAS_NUMBERS).split(":");
        int deltaCount = Integer.parseInt(arr[1].trim());
        sourceNavigateAngularPage().scrollPageHorizontallyShiftEnd(String.format(DELTA_ROW, 0));

        if (deltaCount >= startIndex) {
            for (int index = startIndex; index < deltaCount - 1; index++) {
                assertThat(sourceNavigateAngularPage().getElementsText(String.format(DELTA_ROW, index) + String.format(SourceNavigateAngularPageElements.COLUMN_ID_PATTERN, "difficultyLevel")))
                        .as("Delta Difficulty level value is NOT matching")
                        .isEqualTo(expDiffVal);
            }
        }
    }

    public void assertThatContentSetFiltered(String contentSet) {
        assertThat(sourceNavigateAngularLockReportPage().getElementsText(String.format(FIRST_CONTENT_SET, contentSet)).equals(contentSet))
                .as("The contentset is filtered to " + contentSet)
                .isTrue();
    }

    public void assertThatRenditionStatusFiltered(String renditionStatus) {
        assertThat(sourceNavigateAngularLockReportPage().getElementsText(String.format(FIRST_RENDITION_STATUS, renditionStatus)).equals(renditionStatus))
                .as("The Rendition Status is filtered to " + renditionStatus)
                .isTrue();
    }

    public void assertThatGeneralEffectiveDate(String xpath, String dateValue) {
        assertThat(sourceNavigateAngularPage().getElementsText(xpath))
                .as("effective Date is not matched with selected rendition row")
                .isEqualTo(dateValue);
    }

    public void assertThatCancelEffectiveDate(String xpath, String effectivedate) {
        assertThat(sourceNavigateAngularPage().getElementsText(xpath))
                .as("Effective Date is not matched with selected rendition row")
                .isEqualTo(effectivedate);
    }

    public void assertThatMessage(String xpath, String errormessage) {
        assertThat(sourceNavigateAngularPage().getElementsText(xpath).contains(errormessage))
                .as("Error message is not as expeced")
                .isTrue();
    }

    public void assertNotesValues(String xpath, String errormessage) {
        assertThat(errormessage.contains(sourceNavigateAngularPage().getElementsText(xpath)))
                .as("Error message is not as expeced")
                .isTrue();
    }

    public void assertToastMessageForValidateLinks() {
        assertThat(sourceNavigateAngularPage().isElementDisplayed("//*[contains(@class, 'ng-star-inserted') and contains(text(), 'The following workflow has been started')]"))
                .as("Workflow toast message hs not appeared")
                .isTrue();
    }

    public void assertTheRenditionBaseline(String createdBy) {

        assertThat(sourceNavigateAngularPage().getElementsText("//source-nav-rendition-baselines//div[@row-id='0']//div[@col-id='baselineNumber']//span").contentEquals("Current"))
                .as("Current is not present in the Number column from the first row corresponding to the user")
                .isTrue();
        assertThat(sourceNavigateAngularPage().getElementsText("//source-nav-rendition-baselines//div[@row-id='0']//div[@col-id='createdBy']").contentEquals(createdBy))
                .as("User " + createdBy + " is not present")
                .isTrue();
        assertThat(sourceNavigateAngularPage().getElementsText("//source-nav-rendition-baselines//div[@row-id='0']//div[@col-id='description']").contentEquals("Links Updated"))
                .as("In Description, Links Updated is not updated")
                .isTrue();
        assertThat(sourceNavigateAngularPage().getElementsText("//source-nav-rendition-baselines//div[@row-id='0']//div[@col-id='process']").contentEquals("Bermuda"))
                .as("The process column does not have Bermuda")
                .isTrue();

    }

    public void assertThatTrue(String xpath) {
        assertThat(sourceNavigateAngularPage().waitForElementExists(xpath))
                .as("Expected element should be present")
                .isTrue();
    }

    public void assertThatInCorrectDateFormat(String xpath) {
        assertThat(sourceNavigateAngularPage().getElementsText(Incorrect_Date_Format).equals("The correct date format should be MM/DD/YYYY."))
                .as("After Passing the Incorrect Date")
                .isTrue();
    }

    public void assertThatCorrectRenditionUuids(String xpath, String uuids) {
        assertThat(sourceNavigateAngularPage().getElementsText(xpath).equals(uuids))
                .as("Report does not ran on correct uuids")
                .isTrue();
    }

    public void assertThatTargetLocationUuidsCount(String xpath, int expCount) {
        assertThat(sourceNavigateAngularPage().getElementsText(xpath).split(",").length == expCount)
                .as("Report does not ran on correct uuids")
                .isTrue();
    }

    public void assertThatCorrectDateAfter1999(String xpath) {
        assertThat(sourceNavigateAngularPage().getElementsText(Incorrect_Date_Format).equals("The correct date should be after 01/01/1999."))
                .as("After Passing the Incorrect Date")
                .isTrue();
    }

    public void assertThatSecondRenditionEffectiveDate(String xpath, String dateValue) {
        assertThat(sourceNavigateAngularPage().getElementsText(xpath))
                .as("Effective Date is not matched with selected rendition row")
                .isEqualTo(dateValue);
    }

    public void assertThatInstructionNotesWindowIsDisplayed(String xpath, String windowname) {
        assertThat(sourceNavigateAngularPage().switchToWindow(xpath))
                .as(windowname)
                .isTrue();
    }

    public void assertThatVerifyWorkflowType(String xpath, String workflowType) {
        assertThat(workflowSearchPage().filterWorkflowAndVerifyStatus(workflowType, "",
                "Cite Locate", user().getUsername().toUpperCase()))
                .as("Workflow %s failed to finish in time")
                .isTrue();
    }

    public void assertThatElementNotPresent(String xpath, String expectedValue) {
        assertThat(sourceNavigateAngularPage().getElementsText(xpath).equals(expectedValue))
                .as("Difficulty level is not matching to " + expectedValue)
                .isFalse();
    }

    public void assertThatIntergrationSectionNumber(String xpath, String sectionNumber) {
        assertThat(sourceNavigateAngularPage().getElementsText(xpath).equals(sectionNumber))
                .as("DocumentNumber is not matched with selected Section Number")
                .isTrue();
    }

    public void assertThatIntegrationResultPartStatus(String xpath, String Status) {
        assertThat(sourceNavigateAngularPage().getElementsText(xpath).equals(Status))
                .as("Part Status is complete for all rows")
                .isTrue();
    }

    public void assertThatToastMessage() {
        assertThat(sourceNavigateAngularPage().getElementsText(SourceNavigateAngularToastPageElements.TOAST_BODY).equals("The tracking status was successfully updated"))
                .as("After submit we should see a toast message appear")
                .isTrue();

    }

    public void assertThatRenditionTrackingStatus(String trackingstatus, String expected) {
        assertThat(sourceNavigateAngularPage().getElementsText(trackingstatus).equals(expected))
                .as(trackingstatus, expected)
                .isTrue();
    }

    public void assertThatDeltaPropertiesTabColumnValuesIsEnabled(String values, boolean value) {
        if (value) {
            assertThat(sourceNavigateAngularPage().isElementEnabled(format(SourceNavigateAngularDeltaPageElements.DELTA_PROPERTIES_VIEWMODE_ELEMENTS, values)))
                    .as("Delta Properties Column values are disabled")
                    .isTrue();

        } else {
            assertThat(sourceNavigateAngularPage().isElementDisabled(format(SourceNavigateAngularDeltaPageElements.DELTA_PROPERTIES_DROPDOWN_ELEMENTS, values))
            ).as("Delta Properties column values are enabled")
                    .isFalse();
        }

    }

    public void assertThatDeltaPropertiesValuesAsExpected(String actual, String expDiffVal, int startIndex) {
        assertThat(sourceNavigateAngularPage().getElementsText(format(DELTA_ROW, startIndex) + format(COLUMN_ID_PATTERN, actual)))
                .as("Delta Properties column value is NOT matching").isEqualTo(expDiffVal);

    }

    public void assertThatDisplayOfPopUpElements(String header, String message, String cancelButton, String confirmButton, String closeUIButton) {
        System.out.println("Message:" + message + "/");
        System.out.println("Message:" + sourceNavigateAngularPage().getElementsText(ASSIGNED_USER_MESSAGE) + "/");
        assertThat(sourceNavigateAngularPage().getElementsText(ASSIGNED_USER_MESSAGE))
                .as("Pop up message '" + message + "' is not displayed")
                .isEqualTo(message);
        assertThat(sourceNavigateAngularPage().isElementDisplayed(cancelButton))
                .as("Cancel button is not displayed")
                .isTrue();
        assertThat(sourceNavigateAngularPage().isElementDisplayed(confirmButton))
                .as("Confirm button is not displayed")
                .isTrue();
        assertThat(sourceNavigateAngularPage().isElementDisplayed(format(closeUIButton, header)))
                .as("Close UI button is not displayed")
                .isTrue();
    }

    public void assertThatPopUpMessageDisappearedOrNot(String message) {
        assertThat(sourceNavigateAngularPage().isElementDisplayed(ASSIGNED_USER_MESSAGE))
                .as("Pop up message '" + message + "' is not disappeared")
                .isFalse();
    }

    public void assertDisplayOfLabelFields(String labelName) {
        assertThat(sourceNavigateAngularPage().isElementDisplayed(format(SECTION_PROPERTIES_BOX_LABEL, labelName)))
                .as(labelName + " label field is not displayed")
                .isTrue();
    }

    public void assertThatProposedApprovedTrackingInformationTabContainsColumns(String column1, String column2, String column3) {
        assertDisplayOfLabelFields(column1);
        assertDisplayOfLabelFields(column2);
        assertDisplayOfLabelFields(column3);
    }

    public void assertThatTabTextFieldAndCalendarOption(String labelName) {
        assertThat(sourceNavigateAngularPage().getElementsAttribute(format(LABEL_TEXT_FIELD, labelName), "placeholder").equals("mm/dd/yyyy"))
                .as(labelName + " Text field is not in dd/mm/yyyy format")
                .isTrue();
        assertThat(sourceNavigateAngularPage().isElementDisplayed(format(CALENDAR_OPTION, labelName)))
                .as(labelName + " calendar option is not displayed")
                .isTrue();

    }

    public void assertThatCurrentDateSaved(String labelName, String currentDate) {
        assertThat(sourceNavigateAngularPage().getElementsAttribute(format(LABEL_TEXT_FIELD, labelName), "value"))
                .as("Current date is not saved")
                .isEqualTo(currentDate);
    }

    public void assertThatColumnPopulatedWithOwnerData(String labelName, String owner) {
        assertThat(sourceNavigateAngularPage().getElementsAttribute(format(OWNER_DATA, labelName), "value"))
                .as("Current date is not saved")
                .isEqualTo(owner);
    }

    public void assertPREPTrackingColumns(String xpath, String columnName) {
        assertThat(sourceNavigateAngularPage().isElementDisplayed(format(xpath, columnName)))
                .as(columnName + " column is not displayed")
                .isTrue();
    }

    public void assertThatPREPTrackingFirstHalfColumns(String column1, String column2, String column3) {
        assertPREPTrackingColumns(PREP_TRACKING_FIRST_HALF, column1);
        assertPREPTrackingColumns(PREP_TRACKING_FIRST_HALF, column2);
        assertPREPTrackingColumns(PREP_TRACKING_FIRST_HALF, column3);
    }

    public void assertThatPREPTrackingSecondHalfColumns(String column1, String column2, String column3) {
        assertPREPTrackingColumns(PREP_TRACKING_SECOND_HALF, column1);
        assertPREPTrackingColumns(PREP_TRACKING_SECOND_HALF, column2);
        assertPREPTrackingColumns(PREP_TRACKING_SECOND_HALF, column3);
    }

    public void assertThatDeltaPropertiesInputFieldsViewMode(String field) {
        assertThat(sourceNavigateAngularPage().getElementsAttribute(format(DELTA_PROPERTIES_INPUT_FIELD, field), "readonly"))
                .as("Input filed of '" + field + "' is not read only")
                .isEqualTo("true");
    }

    public void assertThatDeltaPropertiesSubmitButton() {
        assertThat(sourceNavigateAngularPage().getElementsAttribute(DELTA_PROPERTIES_SUBMIT_BUTTON, "disabled"))
                .as("Input filed of is not read only")
                .isEqualTo("true");
    }

    public void assertDisplayOfComboListValue(String value) {
        assertThat(sourceNavigateAngularPage().isElementDisplayed(format(COMBO_BOX_LIST, value)))
                .as(value + " is not present")
                .isTrue();
    }

    public void assertThatDisplayOfPREPTrackingDropdownValues(String dropDownLabelName, String value1, String value2) {
        assertThat(sourceNavigateAngularPage().isElementDisplayed(format(PREP_TRACKING_COMBO_BOX, dropDownLabelName)))
                .as(dropDownLabelName + " Combo box is not displayed")
                .isTrue();
        sourceNavigateAngularPage().click(format(PREP_TRACKING_COMBO_BOX, dropDownLabelName));
        DateAndTimeUtils.takeNap(DateAndTimeUtils.FIVE_SECONDS);
        assertDisplayOfComboListValue(value1);
        assertDisplayOfComboListValue(value2);
    }

    public void assertThatCheckboxesEnabledOrNot(String id) {
        assertThat(sourceNavigateAngularPage().getElementsAttribute(format(CHECKBOX_INPUT_FIELD, id), "aria-checked"))
                .isEqualTo("false");
    }

    public void assertThatDisplayOfHeadersAndButtons() {
        String headerText = sourceNavigateAngularDeltaPage().getElementsText(HEADER);
        System.out.println("Text displayed is" + headerText);

        assertThat(sourceNavigateAngularPage().isElementDisplayed(HEADER))
                .as("Header is not displayed")
                .isTrue();
        assertThat(sourceNavigateAngularPage().isElementDisplayed(Cancel_Button))
                .as("Cancel Button is not displayed")
                .isTrue();
        assertThat(sourceNavigateAngularPage().isElementDisplayed(Submit_Button))
                .as("Submit Button is not displayed")
                .isTrue();
        assertThat(sourceNavigateAngularPage().isElementDisplayed(format(CLOSE_UI_BUTTON, headerText)))
                .as("UI Close Button is not displayed")
                .isTrue();
    }

    public void assertThatDisplayOfSectionPropertiesTabs(String tabName) {
        assertThat(sourceNavigateAngularPage().isElementDisplayed(format(ANY_TAB_NAME, tabName)))
                .as(tabName + " is not displayed")
                .isTrue();
    }

    public void assertThatFieldsViewMode(String numberField, String deltaCountField) {
        assertThat(sourceNavigateAngularPage().getElementsAttribute(format(SECTION_PROPERTIES_INPUT_FIELD, numberField), "readonly"))
                .as(numberField + " is not read only")
                .isEqualTo("true");
        assertThat(sourceNavigateAngularPage().getElementsAttribute(format(SECTION_PROPERTIES_INPUT_FIELD, deltaCountField), "readonly"))
                .as(deltaCountField + " is not read only")
                .isEqualTo("true");
    }

    public void assertThatTextFieldsAndCalendarOptions(String effectiveDateField, String assignedDateField) {
        assertThat(sourceNavigateAngularPage().getElementsAttribute(format(SECTION_PROPERTIES_TEXT_FIELD, effectiveDateField), "placeholder").equals("mm/dd/yyyy"))
                .as(effectiveDateField + " Text field is not in dd/mm/yyyy format")
                .isTrue();
        assertThat(sourceNavigateAngularPage().getElementsAttribute(format(SECTION_PROPERTIES_TEXT_FIELD, assignedDateField), "placeholder").equals("mm/dd/yyyy"))
                .as(assignedDateField + " Text field is not in dd/mm/yyyy format")
                .isTrue();
        assertThat(sourceNavigateAngularPage().isElementDisplayed(format(CALENDAR_OPTION, effectiveDateField)))
                .as(effectiveDateField + " calendar option is not displayed")
                .isTrue();
        assertThat(sourceNavigateAngularPage().isElementDisplayed(format(CALENDAR_OPTION, assignedDateField)))
                .as(assignedDateField + " calendar option is not displayed")
                .isTrue();
    }

    public void assertThatDisplayOfDropdowns(String dropDown1, String dropDown2) {
        assertThat(sourceNavigateAngularPage().isElementDisplayed(format(LABEL_TEXT_FIELD, dropDown1)))
                .as(dropDown1 + " Dropdown is not displayed");
        assertThat(sourceNavigateAngularPage().isElementDisplayed(format(LABEL_TEXT_FIELD, dropDown2)))
                .as(dropDown1 + " Dropdown is not displayed");
    }

    public void assertThatDisplayOfTextArea(String labelName) {
        assertThat(sourceNavigateAngularPage().isElementDisplayed(format(SECTION_INSTRUCTIONS, labelName)))
                .as(labelName + " Text box is not displayed")
                .isTrue();
    }

    public void assertThatSectionPropertiesSubmitButtonIsDisabled() {
        assertThat(sourceNavigateAngularPage().getElementsAttribute(Submit_Button, "disabled"))
                .as("Input filed of Submit button is is not read only")
                .isEqualTo("true");
    }

    public void assertThatAllInputFieldsAreReadOnly() {
        List<WebElement> dateFields = sourceNavigateAngularPage().getElements(SECTION_PROPERTIES_INPUT_FIELDS);
        for (WebElement dateField : dateFields) {

            boolean disabled = false;
            boolean readOnly = false;
            try {
                if (dateField.getAttribute("disabled").equals("true")) {
                    disabled = true;
                }
            } catch (Exception e) {
                System.out.println("Element doesn't have disabled attribute");
            }

            try {
                if (dateField.getAttribute("readonly").equals("true")) {
                    readOnly = true;
                }
            } catch (Exception e) {
                System.out.println("Element doesn't have readonly attribute");
            }

            assertThat(disabled | readOnly)
                    .as("Field is not in view only mode")
                    .isTrue();
        }
    }

    public void assertThatPREPTrackingInputFieldsAreReadOnly() {
        assertThatAllInputFieldsAreReadOnly();
    }

    public void assertThatInputFieldsViewMode(String field) {
        assertThat(sourceNavigateAngularPage().getElementsAttribute(format(SYSTEM_PROPERTIES_INPUT_FIELD, field), "readonly"))
                .as("Input filed of '" + field + "' is not read only")
                .isEqualTo("true");
    }

    public void assertThatProposedApprovedTrackingInformationInputFieldsAreReadOnly() {
        assertThatAllInputFieldsAreReadOnly();
    }

    public void assertThatRenditionPropertiesLabels() {
        assertThat(sourceNavigateAngularPage().doesElementExist(CLASS_NUMBER, 2000))
                .as("Create Bookmark window doesn't appear")
                .isTrue();
        assertThat((sourceNavigateAngularPage().doesElementExist(CLASS_NAME)))
                .as("Class Name is not displayed")
                .isTrue();
        assertThat((sourceNavigateAngularPage().doesElementExist(CANCEL)))
                .as("Cancel button is not displayed")
                .isTrue();
        assertThat((sourceNavigateAngularPage().doesElementExist(RENDITION_TAB)))
                .as("Rendition properties is not displayed")
                .isTrue();
        assertThat((sourceNavigateAngularPage().doesElementExist(PROPOSED_TAB)))
                .as("Proposed/Approved Tracking Information is not displayed")
                .isTrue();
        assertThat((sourceNavigateAngularPage().doesElementExist(PREP_TAB)))
                .as("PREP Tracking Information is not displayed")
                .isTrue();
        assertThat((sourceNavigateAngularPage().doesElementExist(SYSTEM_PROPERTIES_TAB)))
                .as("System Properties is not displayed")
                .isTrue();
        assertThat((sourceNavigateAngularPage().doesElementExist(APPROVAL_DATE)))
                .as("Date is not displayed")
                .isTrue();
        assertThat((sourceNavigateAngularPage().doesElementExist(CLOSE_ICON)))
                .as("Close button is not displayed")
                .isTrue();
        assertThat((sourceNavigateAngularPage().doesElementExist(DOCUMENT_TYPE)))
                .as("Document Type is not displayed")
                .isTrue();
        assertThat((sourceNavigateAngularPage().doesElementExist(DOCUMENT_NUMBER)))
                .as("Document Number  is not displayed")
                .isTrue();
        assertThat((sourceNavigateAngularPage().doesElementExist(Submit_Button)))
                .as("Submit button is not displayed")
                .isTrue();
    }

    public void assertThatRenditionFieldsViewOnlyMode(List<String> labels) {
        for (String verify : labels) {
            assertThat(sourceNavigateAngularPage().getElementsAttribute(format(RENDITION_SYSTEM_PROPERTIES_INPUT_FIELD, verify), "readonly"))
                    .as("Input filed of '" + verify + "' is not read only")
                    .isEqualTo("true");
        }
    }

    public void assertThatRenditionProposedFieldsViewOnlyMode(List<String> labels) {
        for (String verify : labels) {
            assertThat(sourceNavigateAngularPage().getElementsAttribute(format("//input[@id='%s']", verify), "readonly"))
                    .as("Input filed of '" + verify + "' is not read only")
                    .isEqualTo("true");
        }
    }

    public void assertThatRenditionSystemInputFieldsViewMode(String field) {
        assertThat(sourceNavigateAngularPage().getElementsAttribute(format(RENDITION_SYSTEM_PROPERTIES_INPUT_FIELD, field), "readonly"))
                .as("Input filed of '" + field + "' is not read only")
                .isEqualTo("true");
    }
}
