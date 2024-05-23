package com.thomsonreuters.codes.codesbench.quality.tests.sourcenavigateangular.general;

import com.thomsonreuters.codes.codesbench.quality.tests.sourcenavigateangular.assertions.SourceNavigateAngularAssertions;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.EDGE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.SourceNavigateAngularContextMenuItemsPageElements.VALIDATE;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.SourceNavigateAngularContextMenuItemsPageElements.VIEW_VALIDATIONS;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.SourceNavigateAngularPageElements.VIEW_VALIDATION_CLOSE_BTN;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.SourceNavigateAngularPageElements.VIEW_VALIDATION_ROW_PATTERN;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.SourceNavigateAngularSectionGroupPageElements.SECTION_GROUP_NAME_DOCUMENT;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.SourceNavigateAngularTabsPageElements.RENDITION_TAB;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.SourceNavigateAngularTabsPageElements.SECTION_GROUP_TAB;
import static java.lang.String.format;

public class SNGeneralSectionGroupDeltaPageTests extends SourceNavigateAngularAssertions {

    /**
     * Test Case ID:723877_TC1
     * Description:View Validations of Section Groups
     */
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void verifySectionGroupViewValidations(){

        String rendition_uuid = "I02DBC56024E911EC85EEA1F6A1BB9BA8";
        List<String> viewValidationPageColumnExpectedFilterValues = Arrays.asList("Content Set", "Doc Uuid", "Doc Type", "Doc Number", "Class Number",
                "West ID", "Rendition Status", "Westlaw Load", "Validations", "Section Name", "Section Number", "Sub Section Number", "Target Node",
                "Target Code", "Target Sub-Node", "Validation Description");
        List<String> getSelectedRowText;

        //Locating Rendition with UUID
        sourceNavigateAngularLeftSidePanePage().clickFindButtonOnLeftPane();
        sourceNavigateAngularLeftSidePanePage().setFindValue("Rendition UUID", rendition_uuid);
        sourceNavigateAngularLeftSidePanePage().clickFindButton();
        DateAndTimeUtils.takeNap(DateAndTimeUtils.THREE_SECONDS);

        //Selecting Rendition
        sourceNavigateAngularPage().rightClickRenditions();

        //Clicking Section Group Tab
        sourceNavigateAngularTabsPage().click(SECTION_GROUP_TAB);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        sourceNavigateAngularPage().waitForElement(format(SECTION_GROUP_NAME_DOCUMENT,"Sg3"));
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);

        //Right click on Section groups and selecting 'Validate->View Validations' sub menu option
        sourceNavigateAngularSectionGroupPage().rightClick(format(SECTION_GROUP_NAME_DOCUMENT,"Sg3"));
        sourceNavigateAngularPage().waitForElement(VALIDATE);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        sourceNavigateAngularPage().clickContextSubMenuItem(VALIDATE, VIEW_VALIDATIONS);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);

        //Verifying 'View Validations UI' opened or not
        assertThatViewValidationWindowIsDisplayed();
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);

        //Validating presence of 'View Validation' UI Columns
        assertThatViewValidationColumns(viewValidationPageColumnExpectedFilterValues);
        sourceNavigateAngularPage().scrollToRight("-1600","(//div[@class='ag-body-horizontal-scroll-viewport'])[7]");
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);

        //Selecting multiple renditions in 'View Validation' UI window
        sourceNavigateAngularPage().clickMultipleRendtions(0, 1, VIEW_VALIDATION_ROW_PATTERN);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        getSelectedRowText = sourceNavigateAngularPage().addSelectedRowData(0, 1, "//div[@row-id='%s']//div[@col-id='documentUuid']");

        //Right click on selected renditions and verifying "Clear Warning Flags" option is active or not
        sourceNavigateAngularPage().rightClick(format(VIEW_VALIDATION_ROW_PATTERN, "1"));
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        assertThatClearWarningFlagsActiveOrNot();

        //Verifying confirmation window
        sourceNavigateAngularPage().clickClearWarningFlag();
        assertThatIfConfirmationWindowAppeared();
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        sourceNavigateAngularPage().clickCancel();
        assertThatIfConfirmationWindowDisappeared();
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);

        //Clicking "Clear Warning Flags" option and confirming option
        sourceNavigateAngularPage().rightClick(format(VIEW_VALIDATION_ROW_PATTERN, "1"));
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        sourceNavigateAngularPage().clickClearWarningFlag();
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        sourceNavigateAngularPage().clickConfirm();
        sourceNavigateAngularPage().waitForPageLoaded();
        assertThatIfConfirmationWindowDisappeared();
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);

        //Verify the rows are removed in 'View Validation' UI window
        aasertThatHighlightedRowsRemoved(getSelectedRowText, "//div[@row-id='%s']/div[@col-id='targetLocationNode']/span[contains(text(), '%s')");
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);

        //Closing 'View Validation' UI window
        sourceNavigateAngularPage().click(VIEW_VALIDATION_CLOSE_BTN);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        sourceNavigateAngularPage().waitForPageLoaded();

        //Clicking on Rendition Tab
        sourceNavigateAngularTabsPage().click(RENDITION_TAB);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
    }

}
