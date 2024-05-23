package com.thomsonreuters.codes.codesbench.quality.tests.publishing.novus.citelinemanagement.citelinereferences;

import com.thomsonreuters.codes.codesbench.quality.pageelements.publishing.novus.citelinemanagement.CiteLineManagementsCommonPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.publishing.novus.citelinemanagement.citelinereferences.AddOrEditCiteLineReferencesPageElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.EDGE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.CITELINE;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.citelinemanagement.CitelineManagementDataMocking;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

public class CitelineReferencesEditTests extends TestService
{
    Connection uatConnection;
    String contentSetUK = "UK Legislative";
    int contentSetUKLegislativeCode = Integer.parseInt(ContentSets.UK_LEGISLATIVE.getCode());
    String contentSetUKLegislativeName = "RB.UKLG";

    @AfterEach
    public void closeConnection()
    {
        BaseDatabaseUtils.disconnect(uatConnection);
    }

    /**
     * STORY: Jets - 21840/21843/21842/21844 <br>
     * SUMMARY: This test verifies the columns appear in the 'Cite Line Edit' modal <br>
     * USER: CITELINE <br>
     */
    @Test
    @EDGE
    @CITELINE
    @LOG
    public void editCiteLineReferencesDisplayTest()
    {
        homePage().goToHomePage();
        loginPage().logIn();
        homePage().setMyContentSet(contentSetUK);

        boolean citelineManagementPageLoaded = publishingMenu().goToPublishingNovusCitelineManagement();
        Assertions.assertTrue(citelineManagementPageLoaded, "Cite Line Management Page Appeared");

        //click edit for mocked record
        boolean editPageAppears = citeLineReferencesPage().clickEdit("1");
        Assertions.assertTrue(editPageAppears, "edit page should appear after clicking edit on a cite");

        //verify all fields appear
        boolean ct1FieldAppears = editCiteLineReferencesPage().isElementDisplayed(AddOrEditCiteLineReferencesPageElements.CT1_INPUT_XPATH);
        boolean originalFirstLineCiteAppears = editCiteLineReferencesPage().isElementDisplayed(AddOrEditCiteLineReferencesPageElements.ORIGINAL_FIRST_LINE_INPUT_XPATH);
        boolean firstLineCiteFieldAppears = editCiteLineReferencesPage().isElementDisplayed(AddOrEditCiteLineReferencesPageElements.FIRST_LINE_INPUT_XPATH);
        boolean secondLineCitePreFieldAppears = editCiteLineReferencesPage().isElementDisplayed(AddOrEditCiteLineReferencesPageElements.SECOND_LINE_PRE_INPUT_XPATH);
        boolean secondLineCiteAppFieldAppears = editCiteLineReferencesPage().isElementDisplayed(AddOrEditCiteLineReferencesPageElements.SECOND_LINE_APP_INPUT_XPATH);
        boolean expandedCiteLinePreFieldAppears = editCiteLineReferencesPage().isElementDisplayed(AddOrEditCiteLineReferencesPageElements.EXPANDED_LINE_PRE_INPUT_XPATH);
        boolean expandedCiteLineAppFieldAppears = editCiteLineReferencesPage().isElementDisplayed(AddOrEditCiteLineReferencesPageElements.EXPANDED_LINE_APP_INPUT_XPATH);
        boolean formerCiteFieldAppears = editCiteLineReferencesPage().isElementDisplayed(AddOrEditCiteLineReferencesPageElements.FORMER_CITE_INPUT_XPATH);
        boolean commentsFieldAppears = editCiteLineReferencesPage().isElementDisplayed(AddOrEditCiteLineReferencesPageElements.COMMENT_INPUT_XPATH);
        boolean updateValuesFieldAppears = editCiteLineReferencesPage().isElementDisplayed(CiteLineManagementsCommonPageElements.UPDATE_VALUES_BUTTON_XPATH);
        boolean cancelValuesFieldAppears = editCiteLineReferencesPage().isElementDisplayed(CiteLineManagementsCommonPageElements.CANCEL_BUTTON_XPATH);

        Assertions.assertAll
        (
            ()-> Assertions.assertTrue(ct1FieldAppears, "CT1 should appear on the edit page"),
            () -> Assertions.assertTrue(originalFirstLineCiteAppears, "originalFirstLineCite should appear on the edit page"),
            () -> Assertions.assertTrue(firstLineCiteFieldAppears, "firstLineCite should appear on the edit page"),
            () -> Assertions.assertTrue(secondLineCitePreFieldAppears, "secondLineCitePre should appear on the edit page"),
            () -> Assertions.assertTrue(secondLineCiteAppFieldAppears, "secondLineCiteApp should appear on the edit page"),
            () -> Assertions.assertTrue(expandedCiteLinePreFieldAppears, "ExpandedCiteLinePre should appear on the edit page"),
            () -> Assertions.assertTrue(expandedCiteLineAppFieldAppears, "ExpandedCiteLineApp should appear on the edit page"),
            () -> Assertions.assertTrue(formerCiteFieldAppears, "formerCite should appear on the edit page"),
            () -> Assertions.assertTrue(commentsFieldAppears, "Comments should appear on the edit page"),
            () -> Assertions.assertTrue(updateValuesFieldAppears, "updateValues should appear on the edit page"),
            () -> Assertions.assertTrue(cancelValuesFieldAppears, "Cancel should appear on the edit page")
        );
    }

    /**
     * STORY: Jets - 21840/21843/21842/21844 <br>
     * SUMMARY: This test verifies an alert appears for a non-numeric CT1 entry in the Edit Cite Line modal <br>
     * USER: CITELINE <br>
     */
    @Test
    @EDGE
    @CITELINE
    @LOG
    public void alertAppearsForNonNumericCT1Test()
    {
        String nonNumericInput = "editTest";

        homePage().goToHomePage();
        loginPage().logIn();
        homePage().setMyContentSet(contentSetUK);

        boolean citelineManagementPageLoaded = publishingMenu().goToPublishingNovusCitelineManagement();
        Assertions.assertTrue(citelineManagementPageLoaded, "Cite Line Management Page Appeared");

        boolean editPageAppears = citeLineReferencesPage().clickEdit("1");
        Assertions.assertTrue(editPageAppears, "edit page should appear after clicking edit on a cite");

        editCiteLineReferencesPage().setCt1Field(nonNumericInput, true);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        boolean isUpdateValuesDisabledAfterEntry = editCiteLineReferencesPage().isElementDisabled(CiteLineManagementsCommonPageElements.updateValues);
        boolean nonNumericAlertAppears = editCiteLineReferencesPage().isElementDisplayed(String.format(AddOrEditCiteLineReferencesPageElements.CT1_ERROR, AddOrEditCiteLineReferencesPageElements.NON_NUMERIC_CT1_ERROR));

        Assertions.assertAll
        (
            ()-> Assertions.assertTrue(isUpdateValuesDisabledAfterEntry, "Update Values button should be disabled with non-numeric text in ct1 value"),
            ()-> Assertions.assertTrue(nonNumericAlertAppears, "CT1 must be only numbers alert appears")
        );
    }

    /**
     * STORY: Jets - 21840/21843/21842/21844 <br>
     * SUMMARY: This test verifies you cannot update a cite line if CT1, Original First Line Cite, or First Line Cite are empty <br>
     * USER: CITELINE <br>
     */
    @Test
    @EDGE
    @CITELINE
    @LOG
    public void isUpdateValuesDisabledForEmptyCt1OriginalFirstLineCiteAndFirstLineCiteTest()
    {
        uatConnection = BaseDatabaseUtils.connectToDatabaseUAT();
        String ct1 = DateAndTimeUtils.getCurrentTimeHHmmss();
        String value = "editTest";

        CitelineManagementDataMocking.insertCiteline(uatConnection, contentSetUKLegislativeCode, ct1, value, value, value, contentSetUKLegislativeName);

        try
        {
            homePage().goToHomePage();
            loginPage().logIn();
            homePage().setMyContentSet(contentSetUK);

            boolean citelineManagementPageLoaded = publishingMenu().goToPublishingNovusCitelineManagement();
            Assertions.assertTrue(citelineManagementPageLoaded, "Cite Line Management Page Appeared");

            //click edit for mocked record
            citeLineReferencesPage().openFilterMenuForCt1();
            gridHeaderFiltersPage().setMultipleFilterValues(ct1);
            String rowId = citeLineReferencesPage().getRowID(ct1);

            boolean editPageAppears = citeLineReferencesPage().clickEdit(rowId);
            Assertions.assertTrue(editPageAppears, "edit page should appear after clicking edit on a cite");

            //clear ct1, original and first line and verify buttons and messages appear
            editCiteLineReferencesPage().clearCt1Field(ct1);
            editCiteLineReferencesPage().clearOriginalFirstLineCite(value);
            editCiteLineReferencesPage().clearFirstLineCite(value);

            boolean isUpdateValuesDisabledAfterClear = editCiteLineReferencesPage().isElementDisabled(CiteLineManagementsCommonPageElements.updateValues);
            boolean ct1Required = editCiteLineReferencesPage().isElementDisplayed(String.format(AddOrEditCiteLineReferencesPageElements.CT1_ERROR, AddOrEditCiteLineReferencesPageElements.CT1_IS_REQUIRED) + CiteLineManagementsCommonPageElements.REQUIRED);
            boolean orgFirstLineCiteRequired = editCiteLineReferencesPage().isElementDisplayed(String.format(AddOrEditCiteLineReferencesPageElements.ORG_FIRST_LINE_CITE_ERROR, AddOrEditCiteLineReferencesPageElements.ORG_FIRST_LINE_CITE_REQUIRED) + CiteLineManagementsCommonPageElements.REQUIRED);
            boolean firstLineCiteRequired = editCiteLineReferencesPage().isElementDisplayed(String.format(AddOrEditCiteLineReferencesPageElements.FIRST_LINE_CITE_ERROR, AddOrEditCiteLineReferencesPageElements.FIRST_LINE_CITE_REQUIRED) + CiteLineManagementsCommonPageElements.REQUIRED);

            Assertions.assertAll
            (
                ()-> Assertions.assertTrue(isUpdateValuesDisabledAfterClear, "update value should be disabled after clear"),
                () -> Assertions.assertTrue(ct1Required, "CT1 is a required field error did not appear as it should"),
                () -> Assertions.assertTrue(orgFirstLineCiteRequired, "Original First Line Cite is a required field error did not appear as it should"),
                () -> Assertions.assertTrue(firstLineCiteRequired, "First Line Cite is a required field error did not appear as it should")
            );
        }
        finally
        {
            CitelineManagementDataMocking.deleteFromPubStableTable(uatConnection, ct1);
        }
    }

    /**
     * STORY: Jets - 21840/21843/21842/21844 <br>
     * SUMMARY: This test verifies values get updated when editing a cite line <br>
     * USER: CITELINE <br>
     */
    @Test
    @EDGE
    @CITELINE
    @LOG
    public void editAndUpdateCiteLineReferencesFunctionalityTest()
    {
        uatConnection = BaseDatabaseUtils.connectToDatabaseUAT();
        String ct1 = DateAndTimeUtils.getCurrentTimeHHmmss();
        String ct1_2 = DateAndTimeUtils.getCurrentTimeHHmmss();
        String value = "Test";

        CitelineManagementDataMocking.insertCiteLine(uatConnection, contentSetUKLegislativeCode, ct1, value, value, value, value, value, value, value, value, value, value, contentSetUKLegislativeName);

        try
        {
            homePage().goToHomePage();
            loginPage().logIn();
            homePage().setMyContentSet(contentSetUK);

            boolean citelineManagementPageLoaded = publishingMenu().goToPublishingNovusCitelineManagement();
            Assertions.assertTrue(citelineManagementPageLoaded, "Cite Line Management Page Appeared");

            //click edit for mocked record
            citeLineReferencesPage().openFilterMenuForCt1();
            gridHeaderFiltersPage().setMultipleFilterValues(ct1);
            String rowId = citeLineReferencesPage().getRowID(ct1);

            boolean editPageAppears = citeLineReferencesPage().clickEdit(rowId);
            Assertions.assertTrue(editPageAppears, "edit page should appear after clicking edit on a cite");

            editCiteLineReferencesPage().setNewCiteFields(ct1,"Test og first","Test first", "Test second pre", "Test second app",
                    "Test expanded pre", "Test expanded app", "Test former", "Test comment");
            boolean editModalClosed = editCiteLineReferencesPage().clickUpdateValues();
            Assertions.assertTrue(editModalClosed, "The edit cite line modal did not close when it should have");

            citeLineReferencesPage().openFilterMenuForCt1();
            gridHeaderFiltersPage().setFilterValue(ct1_2);
            boolean isCt1InGrid = citeLineReferencesPage().isCT1InGrid(ct1_2);
            Assertions.assertTrue(isCt1InGrid, "The new cite line did not appear in the grid when it should");
            boolean originalFirstLineCiteColumnUpdated = citeLineReferencesPage().getOriginalFirstLineCite(ct1).equals("Test og first");
            boolean firstLineCiteColumnUpdated = citeLineReferencesPage().getFirstLineCite(ct1).equals("Test first");
            boolean secondLineCitePreColumnUpdated = citeLineReferencesPage().getSecondLineCitePre(ct1).equals("Test second pre");
            boolean secondLineCiteAppColumnUpdated = citeLineReferencesPage().getSecondLineCiteApp(ct1).equals("Test second app");
            boolean expandedCitePreColumnUpdated = citeLineReferencesPage().getExpandedCitePre(ct1).equals("Test expanded pre");
            boolean expandedCiteAppColumnUpdated = citeLineReferencesPage().getExpandedCiteApp(ct1).equals("Test expanded app");
            boolean formerCiteColumnUpdated = citeLineReferencesPage().getFormerCite(ct1).equals("Test former");
            boolean modifiedByColumnUpdated = citeLineReferencesPage().getModifiedBy(ct1).equals(citelineUser().getPublishingToolboxUsername());
            boolean modifiedDateColumnUpdated = citeLineReferencesPage().getModifiedDate(ct1).equals(DateAndTimeUtils.getCurentDateMDYYYY());
            boolean commentColumnUpdated = citeLineReferencesPage().getComment(ct1).equals("Test comment");

            Assertions.assertAll
            (
                () -> Assertions.assertTrue(originalFirstLineCiteColumnUpdated, "The original first line cite in the grid did not update when it should have"),
                () -> Assertions.assertTrue(firstLineCiteColumnUpdated, "The first line cite in the grid did not update when it should have"),
                () -> Assertions.assertTrue(secondLineCitePreColumnUpdated, "The second line cite pre in the grid did not update when it should have"),
                () -> Assertions.assertTrue(secondLineCiteAppColumnUpdated, "The second line cite app in the grid did not update when it should have"),
                () -> Assertions.assertTrue(expandedCitePreColumnUpdated, "The expanded cite pre in the grid did not update when it should have"),
                () -> Assertions.assertTrue(expandedCiteAppColumnUpdated, "The expanded cite app in the grid did not update when it should have"),
                () -> Assertions.assertTrue(formerCiteColumnUpdated, "The former cite in the grid did not update when it should have"),
                () -> Assertions.assertTrue(modifiedByColumnUpdated, "The modified by user in the grid did not update when it should have"),
                () -> Assertions.assertTrue(modifiedDateColumnUpdated, "Date is the grid is in the incorrect format, it should be MM/dd/yyyy"),
                () -> Assertions.assertTrue(commentColumnUpdated, "Comment column in the grid did not update when it should have")
            );
        }
        finally
        {
            CitelineManagementDataMocking.deleteFromPubStableTable(uatConnection, ct1);
            CitelineManagementDataMocking.deleteFromPubStableTable(uatConnection, ct1_2);
        }
    }

    /**
     * STORY: Jets - 21840/21843/21842/21844 <br>
     * SUMMARY: This test verifies the values of a cite line do not get updated when clicking cancel on the edit cite line modal <br>
     * USER: CITELINE <br>
     */
    @Test
    @EDGE
    @CITELINE
    @LOG
    public void editAndCancelCiteLineReferencesFunctionalityTest()
    {
        uatConnection = BaseDatabaseUtils.connectToDatabaseUAT();
        String ct1 = DateAndTimeUtils.getCurrentTimeHHmmss();
        String ct1_2 = DateAndTimeUtils.getCurrentTimeHHmmss();
        String originalValue = "Test";
        String updatedValue = "Test2";

        CitelineManagementDataMocking.insertCiteline(uatConnection, contentSetUKLegislativeCode, ct1, originalValue, originalValue, originalValue, contentSetUKLegislativeName);

        try
        {
            homePage().goToHomePage();
            loginPage().logIn();
            homePage().setMyContentSet(contentSetUK);

            boolean citelineManagementPageLoaded = publishingMenu().goToPublishingNovusCitelineManagement();
            Assertions.assertTrue(citelineManagementPageLoaded, "Cite Line Management Page Appeared");

            citeLineReferencesPage().openFilterMenuForCt1();
            gridHeaderFiltersPage().setMultipleFilterValues(ct1);
            String rowId = citeLineReferencesPage().getRowID(ct1);

            boolean editPageAppears = citeLineReferencesPage().clickEdit(rowId);
            Assertions.assertTrue(editPageAppears, "edit page should appear after clicking edit on a cite");

            editCiteLineReferencesPage().setCt1Field(ct1_2, false);
            editCiteLineReferencesPage().setOriginalFirstLineCite(updatedValue);
            editCiteLineReferencesPage().setFirstLineCite(updatedValue);
            editCiteLineReferencesPage().setSecondLineCitePre(updatedValue);
            boolean editModalClosed = editCiteLineReferencesPage().clickCancel();
            Assertions.assertTrue(editModalClosed, "The edit cite line modal did not close when it should have");

            boolean originalFirstLineCiteColumnUpdated = citeLineReferencesPage().getOriginalFirstLineCite(ct1).equals(originalValue);
            boolean firstLineCiteColumnUpdated = citeLineReferencesPage().getFirstLineCite(ct1).equals(originalValue);
            boolean secondLineCitePreColumnUpdated = citeLineReferencesPage().getSecondLineCitePre(ct1).equals(originalValue);

            Assertions.assertAll
            (
                () -> Assertions.assertTrue(originalFirstLineCiteColumnUpdated, "The original first line cite in the grid is different than expected when it should be the same"),
                () -> Assertions.assertTrue(firstLineCiteColumnUpdated, "The first line cite in the grid is different than expected when it should be the same"),
                () -> Assertions.assertTrue(secondLineCitePreColumnUpdated, "The second line cite pre in the grid is different than expected when it should be the same")
            );
        }
        finally
        {
            CitelineManagementDataMocking.deleteFromPubStableTable(uatConnection, ct1);
            CitelineManagementDataMocking.deleteFromPubStableTable(uatConnection, ct1_2);
        }
    }

    /**
     * STORY: HALCYONST-14864 <br>
     * SUMMARY: For Cite Line References page, verifies that the edit comment field has a 100-character limit<br>
     * USER: Citeline <br>
     */
    @Test
    @EDGE
    @CITELINE
    @LOG
    public void citelineReferencesCommentsLengthAlertEditTest()
    {
        //string with more than 100 characters
        String tooManyCharacters = "too many chars: Hi I need to generate 100 characters to make sure that the testing for this functionality works as!!";
        //string with 100 characters
        String enoughCharacters = "Test comment";
        String ct1 = DateAndTimeUtils.getCurrentTimeHHmmss();
        String value = "editTest";
        uatConnection = BaseDatabaseUtils.connectToDatabaseUAT();
        CitelineManagementDataMocking.insertCiteline(uatConnection, contentSetUKLegislativeCode, ct1, value, value, value, contentSetUKLegislativeName);

        try
        {
            homePage().goToHomePage();
            loginPage().logIn();
            homePage().setMyContentSet(ContentSets.UK_LEGISLATIVE.getName());
            publishingMenu().goToPublishingNovusCitelineManagement();
            citeLineReferencesPage().goToCiteLineReferencesTab();

            //Edit cite, check cite 100 character comment limit alert
            citeLineReferencesPage().openFilterMenuForCt1();
            gridHeaderFiltersPage().setFilterValue(ct1);
            citeLineReferencesPage().clickEdit(citeLineReferencesPage().getRowID(ct1));
            addNewCiteLineReferencesPage().setComments(tooManyCharacters);
            boolean editCommentsTooManyCharacters = editCiteLineReferencesPage().commentsHasTooManyCharactersError();
            addNewCiteLineReferencesPage().setComments(enoughCharacters);
            boolean editCommentsCorrectNumCharacters = editCiteLineReferencesPage().commentsHasTooManyCharactersError();

            Assertions.assertAll
            (
                () -> Assertions.assertTrue(editCommentsTooManyCharacters, "100 character limit error should have been displayed"),
                () -> Assertions.assertFalse(editCommentsCorrectNumCharacters, "100 character limit error should not have been displayed")
            );
        }
        finally
        {
            CitelineManagementDataMocking.deleteFromPubStableTable(uatConnection, ct1);
        }
    }
}
