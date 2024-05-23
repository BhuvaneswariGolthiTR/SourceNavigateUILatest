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
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

public class CitelineReferencesAddTests extends TestService
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
     * SUMMARY: This test verifies the columns appear and are in order in the 'Add New Cite Line References' modal <br>
     * USER: CITELINE <br>
     */
    @Test
    @EDGE
    @CITELINE
    @LOG
    public void addNewCiteLineReferencesDisplayTest()
    {
        homePage().goToHomePage();
        loginPage().logIn();
        homePage().setMyContentSet(contentSetUK);

        boolean citeLineManagementPageLoaded = publishingMenu().goToPublishingNovusCitelineManagement();
        Assertions.assertTrue(citeLineManagementPageLoaded, "Cite Line Management Page Appeared");

        boolean addNewCiteLineReferencesPage = citeLineReferencesPage().clickAddNewCiteLineReferences();
        Assertions.assertTrue(addNewCiteLineReferencesPage, "add new Cite references page appeared");

        boolean ct1FieldAppears = addNewCiteLineReferencesPage().isElementDisplayed(AddOrEditCiteLineReferencesPageElements.CT1_INPUT_XPATH);
        boolean originalFirstLineCiteAppears = addNewCiteLineReferencesPage().isElementDisplayed(AddOrEditCiteLineReferencesPageElements.ORIGINAL_FIRST_LINE_INPUT_XPATH);
        boolean firstLineCiteFieldAppears = addNewCiteLineReferencesPage().isElementDisplayed(AddOrEditCiteLineReferencesPageElements.FIRST_LINE_INPUT_XPATH);
        boolean secondLineCitePreFieldAppears = addNewCiteLineReferencesPage().isElementDisplayed(AddOrEditCiteLineReferencesPageElements.SECOND_LINE_PRE_INPUT_XPATH);
        boolean secondLineCiteAppFieldAppears = addNewCiteLineReferencesPage().isElementDisplayed(AddOrEditCiteLineReferencesPageElements.SECOND_LINE_APP_INPUT_XPATH);
        boolean expandedCiteLinePreFieldAppears = addNewCiteLineReferencesPage().isElementDisplayed(AddOrEditCiteLineReferencesPageElements.EXPANDED_LINE_PRE_INPUT_XPATH);
        boolean expandedCiteLineAppFieldAppears = addNewCiteLineReferencesPage().isElementDisplayed(AddOrEditCiteLineReferencesPageElements.EXPANDED_LINE_APP_INPUT_XPATH);
        boolean formerCiteFieldAppears = addNewCiteLineReferencesPage().isElementDisplayed(AddOrEditCiteLineReferencesPageElements.FORMER_CITE_INPUT_XPATH);
        boolean commentsFieldAppears = addNewCiteLineReferencesPage().isElementDisplayed(AddOrEditCiteLineReferencesPageElements.COMMENT_INPUT_XPATH);
        boolean createButtonAppears = addNewCiteLineReferencesPage().isElementDisabled(AddOrEditCiteLineReferencesPageElements.CREATE_BUTTON_XPATH);
        boolean cancelValuesButtonAppears = addNewCiteLineReferencesPage().isElementDisplayed(CiteLineManagementsCommonPageElements.CANCEL_BUTTON_XPATH);
        boolean createAnotherButtonAppears = addNewCiteLineReferencesPage().isElementDisabled(CiteLineManagementsCommonPageElements.CREATE_ANOTHER_BUTTON_XPATH);

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(ct1FieldAppears, "ct1 should appear on the add page"),
            () -> Assertions.assertTrue(originalFirstLineCiteAppears, "originalFirstLineCite should appear on the add page"),
            () -> Assertions.assertTrue(firstLineCiteFieldAppears, "firstLineCite should appear on the add page"),
            () -> Assertions.assertTrue(secondLineCitePreFieldAppears, "secondLineCitePre should appear on the add page"),
            () -> Assertions.assertTrue(secondLineCiteAppFieldAppears, "secondLineCiteApp should appear on the add page"),
            () -> Assertions.assertTrue(expandedCiteLinePreFieldAppears, "ExpandedCiteLinePre should appear on the add page"),
            () -> Assertions.assertTrue(expandedCiteLineAppFieldAppears, "ExpandedCiteLineApp should appear on the add page"),
            () -> Assertions.assertTrue(formerCiteFieldAppears, "formerCite should appear on the add page"),
            () -> Assertions.assertTrue(commentsFieldAppears, "Comments should appear on the add page"),
            () -> Assertions.assertTrue(createButtonAppears, "Create button should appear on the add page and be disabled"),
            () -> Assertions.assertTrue(cancelValuesButtonAppears, "Cancel should appear on the add page"),
            () -> Assertions.assertTrue(createAnotherButtonAppears, "Create Another should appear on the add page and be disabled")
        );
    }

    /**
     * STORY: Jets - 21840/21843/21842/21844 <br>
     * SUMMARY: This test verifies the 'create' and 'create another' button are disabled if CT1, Original First Line Cite, or First Line Cite are empty <br>
     * USER: CITELINE <br>
     */
    @Test
    @EDGE
    @CITELINE
    @LOG
    public void isCreateDisabledForEmptyCt1OriginalFirstLineCiteAndFirstLineCiteEntry()
    {
        uatConnection = BaseDatabaseUtils.connectToDatabaseUAT();
        String ct1 = DateAndTimeUtils.getCurrentTimeHHmmss();
        String lineValue = "creationTest";

        try
        {
            homePage().goToHomePage();
            loginPage().logIn();
            homePage().setMyContentSet(contentSetUK);

            boolean citeLineManagementPageLoaded = publishingMenu().goToPublishingNovusCitelineManagement();
            Assertions.assertTrue(citeLineManagementPageLoaded, "Cite Line Management Page appeared");

            boolean addNewCiteLineReferencesPage = citeLineReferencesPage().clickAddNewCiteLineReferences();
            Assertions.assertTrue(addNewCiteLineReferencesPage, "add new Cite references page appeared");

            //fill out second pre & verify buttons disabled
            addNewCiteLineReferencesPage().setSecondLineCitePre(lineValue);
            boolean isCreateDisabledWithOnlySecondPre = addNewCiteLineReferencesPage().isElementDisabled(CiteLineManagementsCommonPageElements.createButton);
            boolean isCreateAnotherDisabledWithOnlySecondPre = addNewCiteLineReferencesPage().isElementDisabled(CiteLineManagementsCommonPageElements.createAnotherButton);

            Assertions.assertAll
            (
                () -> Assertions.assertTrue(isCreateDisabledWithOnlySecondPre, "create button should be disabled when we only filed in Second pre"),
                () -> Assertions.assertTrue(isCreateAnotherDisabledWithOnlySecondPre, "create Another button should be disabled when we only filed in Second pre")
            );
        }
        finally
        {
            CitelineManagementDataMocking.deleteFromPubStableTable(uatConnection, ct1);
        }
    }

    /**
     * STORY: Jets - 21840/21843/21842/21844 <br>
     * SUMMARY: This test verifies an alert appears when attempting to create a citeline with an existing ct1 value <br>
     * USER: CITELINE <br>
     */
    @Test
    @EDGE
    @CITELINE
    @LOG
    public void alertAppearForDuplicateEntryTest()
    {
        uatConnection = BaseDatabaseUtils.connectToDatabaseUAT();
        String ct1 = DateAndTimeUtils.getCurrentTimeHHmmss();
        String value = "creationTest";
        CitelineManagementDataMocking.insertCiteline(uatConnection, contentSetUKLegislativeCode, ct1, value, value, value, contentSetUKLegislativeName);

        try
        {
            homePage().goToHomePage();
            loginPage().logIn();
            homePage().setMyContentSet(contentSetUK);

            boolean citeLineManagementPageLoaded = publishingMenu().goToPublishingNovusCitelineManagement();
            Assertions.assertTrue(citeLineManagementPageLoaded, "Cite Line Management Page Appeared");

            boolean addNewCiteLineReferencesPage = citeLineReferencesPage().clickAddNewCiteLineReferences();
            Assertions.assertTrue(addNewCiteLineReferencesPage, "add new Cite references page appeared");

            //using same data we mocked up create another with the same data
            addNewCiteLineReferencesPage().setCt1Field(ct1, true);
            addNewCiteLineReferencesPage().setOriginalFirstLineCite(value);
            addNewCiteLineReferencesPage().setFirstLineCite(value);

            boolean ct1DuplicateEntry = addNewCiteLineReferencesPage().isElementDisplayed(String.format(AddOrEditCiteLineReferencesPageElements.CT1_ERROR, AddOrEditCiteLineReferencesPageElements.DUPLICATE_CT1_ERROR));
            Assertions.assertTrue(ct1DuplicateEntry, "ct1 should have a duplicate CT1 error message");
        }
        finally
        {
            CitelineManagementDataMocking.deleteFromPubStableTable(uatConnection, ct1);
        }
    }

    /**
     * STORY: Jets - 21840/21843/21842/21844 <br>
     * SUMMARY: This test verifies an alert appears for a non-numeric CT1 value <br>
     * USER: CITELINE <br>
     */
    @Test
    @EDGE
    @CITELINE
    @LOG
    public void alertAppearsForNonNumericCt1Test()
    {
        String nonNumericCt1 = "test";

        homePage().goToHomePage();
        loginPage().logIn();
        homePage().setMyContentSet(contentSetUK);

        boolean citeLineManagementPageLoaded = publishingMenu().goToPublishingNovusCitelineManagement();
        Assertions.assertTrue(citeLineManagementPageLoaded, "Cite Line Management Page Appeared");

        boolean addNewCiteLineReferencesPage = citeLineReferencesPage().clickAddNewCiteLineReferences();
        Assertions.assertTrue(addNewCiteLineReferencesPage, "add new Cite references page appeared");

        addNewCiteLineReferencesPage().setCt1Field(nonNumericCt1, true);
        boolean ct1MustBeOnlyNumbersAlert = addNewCiteLineReferencesPage().isElementDisplayed(String.format(AddOrEditCiteLineReferencesPageElements.CT1_ERROR, AddOrEditCiteLineReferencesPageElements.NON_NUMERIC_CT1_ERROR));
        Assertions.assertTrue(ct1MustBeOnlyNumbersAlert, "An alert should appear for a non numeric CT1 value");
    }

    /**
     * STORY: Jets - 21840/21843/21842/21844 <br>
     * SUMMARY: This test verifies the 'Add New Cite Line References' modal disappears and a new cite isn't added to the grid when clicking cancel <br>
     * USER: CITELINE <br>
     */
    @Test
    @EDGE
    @CITELINE
    @LOG
    public void addAndCancelCiteLineReferencesTest()
    {
        uatConnection = BaseDatabaseUtils.connectToDatabaseUAT();
        String ct1 = DateAndTimeUtils.getCurrentTimeHHmmss();
        String lineValue = "creationTest";

        try
        {
            homePage().goToHomePage();
            loginPage().logIn();
            homePage().setMyContentSet(contentSetUK);

            boolean citeLineManagementPageLoaded = publishingMenu().goToPublishingNovusCitelineManagement();
            Assertions.assertTrue(citeLineManagementPageLoaded, "Cite Line Management Page Appeared");

            boolean addNewCiteLineReferencesPage = citeLineReferencesPage().clickAddNewCiteLineReferences();
            Assertions.assertTrue(addNewCiteLineReferencesPage, "add new Cite references page appeared");

            //fill out fields
            addNewCiteLineReferencesPage().setCt1Field(ct1, false);
            addNewCiteLineReferencesPage().setOriginalFirstLineCite(lineValue);
            addNewCiteLineReferencesPage().setFirstLineCite(lineValue);
            boolean isCreateEnabled = addNewCiteLineReferencesPage().isElementEnabled(CiteLineManagementsCommonPageElements.createButton);
            boolean isCreateAnotherEnabled = addNewCiteLineReferencesPage().isElementEnabled(CiteLineManagementsCommonPageElements.createAnotherButton);

            addNewCiteLineReferencesPage().setSecondLineCiteApp(lineValue);
            addNewCiteLineReferencesPage().setExpandedCitePre(lineValue);
            addNewCiteLineReferencesPage().setExpandedCitePre(lineValue);
            addNewCiteLineReferencesPage().setFormerCite(lineValue);

            //click cancel and verify not added to grid
            boolean didAddModalClose = addNewCiteLineReferencesPage().clickCancel();
            citeLineReferencesPage().openFilterMenuForCt1();
            boolean isEntryInGridAfterCancel = citeLineReferencesPage().isCT1InGrid(ct1);

            Assertions.assertAll
            (
                () -> Assertions.assertTrue(isCreateEnabled, "create button should be enabled with proper fields filled out"),
                () -> Assertions.assertTrue(isCreateAnotherEnabled, "create Another button should enabled with proper fields filled out"),
                () -> Assertions.assertTrue(didAddModalClose, "The add new cite line references modal should have disappeared but did not"),
                () -> Assertions.assertFalse(isEntryInGridAfterCancel, "Entry should not be in grid after clicking cancel")
            );
        }
        finally
        {
            CitelineManagementDataMocking.deleteFromPubStableTable(uatConnection, ct1);
        }
    }

    /**
     * STORY: Jets - 21840/21843/21842/21844 <br>
     * SUMMARY: This test verifies the functionality of adding a new Citline through the 'Add New Cite Line References' modal <br>
     * USER: CITELINE <br>
     */
    @Test
    @EDGE
    @CITELINE
    @LOG
    public void addCiteLineReferencesTest()
    {
        uatConnection = BaseDatabaseUtils.connectToDatabaseUAT();
        String ct1 = DateAndTimeUtils.getCurrentTimeHHmmss();

        try
        {
            homePage().goToHomePage();
            loginPage().logIn();
            homePage().setMyContentSet(contentSetUK);

            boolean citeLineManagementPageLoaded = publishingMenu().goToPublishingNovusCitelineManagement();
            Assertions.assertTrue(citeLineManagementPageLoaded, "Cite Line Management Page Appeared");

            boolean addNewCiteLineReferencesPage = citeLineReferencesPage().clickAddNewCiteLineReferences();
            Assertions.assertTrue(addNewCiteLineReferencesPage, "add new Cite references page appeared");

            //click add new cite, fill out fields and click create another & verify fields are now disabled again
            addNewCiteLineReferencesPage().setNewCiteFields(ct1,"Test og first","Test first", "Test second pre", "Test second app",
                    "Test expanded pre", "Test expanded app", "Test former", "Test comment");

            boolean addModalClosed = addNewCiteLineReferencesPage().clickCreate();
            Assertions.assertTrue(addModalClosed, "The add cite line modal should have closed");

            //verify that it was successfully created
            citeLineReferencesPage().openFilterMenuForCt1();
            gridHeaderFiltersPage().setFilterValue(ct1);
            boolean isCt1InGrid = citeLineReferencesPage().isCT1InGrid(ct1);
            Assertions.assertTrue(isCt1InGrid, "The new cite line did not appear in the grid when it should");
            boolean originalFirstLineCiteColumnIsCorrect = citeLineReferencesPage().getOriginalFirstLineCite(ct1).equals("Test og first");
            boolean firstLineCiteColumnIsCorrect = citeLineReferencesPage().getFirstLineCite(ct1).equals("Test og first");
            boolean secondLineCitePreColumnIsCorrect = citeLineReferencesPage().getSecondLineCitePre(ct1).equals("Test second pre");
            boolean secondLineCiteAppColumnIsCorrect = citeLineReferencesPage().getSecondLineCiteApp(ct1).equals("Test second app");
            boolean expandedCitePreColumnIsCorrect = citeLineReferencesPage().getExpandedCitePre(ct1).equals("Test expanded pre");
            boolean expandedCiteAppColumnIsCorrect = citeLineReferencesPage().getExpandedCiteApp(ct1).equals("Test expanded app");
            boolean formerCiteColumnIsCorrect = citeLineReferencesPage().getFormerCite(ct1).equals("Test former");
            boolean modifiedByColumnIsCorrect = citeLineReferencesPage().getModifiedBy(ct1).equals(citelineUser().getPublishingToolboxUsername());
            boolean modifiedDateColumnIsCorrect = citeLineReferencesPage().getModifiedDate(ct1).equals(DateAndTimeUtils.getCurentDateMDYYYY());
            boolean commentColumnIsCorrect = citeLineReferencesPage().getComment(ct1).equals("Test comment");

            Assertions.assertAll
            (
                () -> Assertions.assertTrue(originalFirstLineCiteColumnIsCorrect, "The original first line cite in the grid is different than expected when it should be the same"),
                () -> Assertions.assertTrue(firstLineCiteColumnIsCorrect, "The first line cite in the grid is different than expected when it should be the same"),
                () -> Assertions.assertTrue(secondLineCitePreColumnIsCorrect, "The second line cite pre in the grid is different than expected when it should be the same"),
                () -> Assertions.assertTrue(secondLineCiteAppColumnIsCorrect, "The second line cite app in the grid is different than expected when it should be the same"),
                () -> Assertions.assertTrue(expandedCitePreColumnIsCorrect, "The expanded cite pre in the grid is different than expected when it should be the same"),
                () -> Assertions.assertTrue(expandedCiteAppColumnIsCorrect, "The expanded cite app in the grid is different than expected when it should be the same"),
                () -> Assertions.assertTrue(formerCiteColumnIsCorrect, "The former cite in the grid is different than expected when it should be the same"),
                () -> Assertions.assertTrue(modifiedByColumnIsCorrect, "The modified by user in the grid is different than expected when it should be the same"),
                () -> Assertions.assertTrue(modifiedDateColumnIsCorrect, "Date is the grid is in the incorrect format, it should be MM/dd/yyyy"),
                () -> Assertions.assertTrue(commentColumnIsCorrect, "Comment column in the grid is different than the inputted comment, it should be the same")
            );
        }
        finally
        {
            CitelineManagementDataMocking.deleteFromPubStableTable(uatConnection, ct1);
        }
    }

    /**
     * STORY: Jets - 21840/21843/21842/21844 <br>
     * SUMMARY: This test verifies the functionality of creating another Citeline through the 'Add New Cite Line References' modal <br>
     * USER: CITELINE <br>
     */
    @Test
    @EDGE
    @CITELINE
    @LOG
    public void addAnotherCiteLineReferencesTest()
    {
        uatConnection = BaseDatabaseUtils.connectToDatabaseUAT();
        String ct1 = DateAndTimeUtils.getCurrentTimeHHmmss();
        String ct1_2 = DateAndTimeUtils.getCurrentTimeHHmmss();
        String inputValue = "creationTest";

        try
        {
            homePage().goToHomePage();
            loginPage().logIn();
            homePage().setMyContentSet(contentSetUK);

            boolean citeLineManagementPageLoaded = publishingMenu().goToPublishingNovusCitelineManagement();
            Assertions.assertTrue(citeLineManagementPageLoaded, "Cite Line Management Page Appeared");

            boolean addNewCiteLineReferencesPage = citeLineReferencesPage().clickAddNewCiteLineReferences();
            Assertions.assertTrue(addNewCiteLineReferencesPage, "add new Cite references page appeared");

            addNewCiteLineReferencesPage().setNewCiteFields(ct1, inputValue, inputValue, inputValue, inputValue, inputValue, inputValue, inputValue, inputValue);

            addNewCiteLineReferencesPage().clickCreateAnother();
            boolean isCiteLineAddModalOpened = addNewCiteLineReferencesPage().doesElementExist(AddOrEditCiteLineReferencesPageElements.CT1_INPUT_XPATH);
            Assertions.assertTrue(isCiteLineAddModalOpened, "The cite line add modal closed when it should not have");
            DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
            boolean isCreateDisabledAfterCreateAnother = addNewCiteLineReferencesPage().isElementDisabled(AddOrEditCiteLineReferencesPageElements.createButton);
            boolean isCreateAnotherDisabledAfterCreateAnother = addNewCiteLineReferencesPage().isElementDisabled(AddOrEditCiteLineReferencesPageElements.createAnotherButton);

            //create another cite
            ct1_2 = DateAndTimeUtils.getCurrentTimeHHmmss();
            addNewCiteLineReferencesPage().setCt1Field(ct1_2, false);
            addNewCiteLineReferencesPage().setOriginalFirstLineCite(inputValue);
            addNewCiteLineReferencesPage().setFirstLineCite(inputValue);
            boolean addModalClosed = addNewCiteLineReferencesPage().clickCreate();
            Assertions.assertTrue(addModalClosed, "The add cite line modal should have closed");

            //verify both Cite lines were successfully created
            citeLineReferencesPage().openFilterMenuForOriginalFirstLineCite();
            gridHeaderFiltersPage().setFilterValue(inputValue);
            boolean ct1Num1IsInGrid = citeLineReferencesPage().isCT1InGrid(ct1);
            boolean ct1Num2IsInGrid = citeLineReferencesPage().isCT1InGrid(ct1_2);
            boolean originalFirstLineCiteColumnIsCorrect = citeLineReferencesPage().getOriginalFirstLineCite(ct1_2).equals(inputValue);
            boolean firstLineCiteColumnIsCorrect = citeLineReferencesPage().getFirstLineCite(ct1_2).equals(inputValue);
            boolean secondLineCitePreColumnIsCorrect = citeLineReferencesPage().getSecondLineCitePre(ct1_2).equals("");
            boolean secondLineCiteAppColumnIsCorrect = citeLineReferencesPage().getSecondLineCiteApp(ct1_2).equals("");
            boolean expandedCitePreColumnIsCorrect = citeLineReferencesPage().getExpandedCitePre(ct1_2).equals("");
            boolean expandedCiteAppColumnIsCorrect = citeLineReferencesPage().getExpandedCiteApp(ct1_2).equals("");
            boolean formerCiteColumnIsCorrect = citeLineReferencesPage().getFormerCite(ct1_2).equals("");
            boolean modifiedByColumnIsCorrect = citeLineReferencesPage().getModifiedBy(ct1_2).equals(citelineUser().getPublishingToolboxUsername());
            boolean modifiedDateColumnIsCorrect = citeLineReferencesPage().getModifiedDate(ct1_2).equals(DateAndTimeUtils.getCurentDateMDYYYY());
            boolean commentColumnIsCorrect = citeLineReferencesPage().getComment(ct1_2).equals("");

            Assertions.assertAll
            (
                () -> Assertions.assertTrue(isCreateDisabledAfterCreateAnother, "create button should be disabled when we only filed in Second pre"),
                () -> Assertions.assertTrue(isCreateAnotherDisabledAfterCreateAnother, "create Another button should be disabled when we only filed in Second pre"),
                () -> Assertions.assertTrue(ct1Num1IsInGrid, "After clicking create Another the first CT1 should  be in grid"),
                () -> Assertions.assertTrue(ct1Num2IsInGrid, "After clicking create Another the second CT1 should  be in grid"),
                () -> Assertions.assertTrue(originalFirstLineCiteColumnIsCorrect, "The original first line cite in the grid is different than expected when it should be the same"),
                () -> Assertions.assertTrue(firstLineCiteColumnIsCorrect, "The first line cite in the grid is different than expected when it should be the same"),
                () -> Assertions.assertTrue(secondLineCitePreColumnIsCorrect, "The second line cite pre in the grid is different than expected when it should be the same"),
                () -> Assertions.assertTrue(secondLineCiteAppColumnIsCorrect, "The second line cite app in the grid is different than expected when it should be the same"),
                () -> Assertions.assertTrue(expandedCitePreColumnIsCorrect, "The expanded cite pre in the grid is different than expected when it should be the same"),
                () -> Assertions.assertTrue(expandedCiteAppColumnIsCorrect, "The expanded cite app in the grid is different than expected when it should be the same"),
                () -> Assertions.assertTrue(formerCiteColumnIsCorrect, "The former cite in the grid is different than expected when it should be the same"),
                () -> Assertions.assertTrue(modifiedByColumnIsCorrect, "The modified by user in the grid is different than expected when it should be the same"),
                () -> Assertions.assertTrue(modifiedDateColumnIsCorrect, "Date is the grid is in the incorrect format, it should be MM/dd/yyyy"),
                () -> Assertions.assertTrue(commentColumnIsCorrect, "Comment column in the grid is different than the inputted comment, it should be the same")
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
     * SUMMARY: For Cite Line References page, verifies that the comment field has a 100-character limit <br>
     * USER: Citeline <br>
     */
    @Test
    @EDGE
    @CITELINE
    @LOG
    public void citelineReferencesCiteCommentsLengthAlertAddTest()
    {
        //string with more than 100 characters
        String tooManyCharacters = "too many chars: Hi I need to generate 100 characters to make sure that the testing for this functionality works as!!";
        //string with 100 characters
        String enoughCharacters = "Test comment";

        //navigate to citeline references page
        homePage().goToHomePage();
        loginPage().logIn();
        publishingMenu().goToPublishingNovusCitelineManagement();
        citeLineReferencesPage().goToCiteLineReferencesTab();

        //Check cite 100 character comment limit alert
        citeLineReferencesPage().clickAddNewCiteLineReferences();
        addNewCiteLineReferencesPage().setComments(tooManyCharacters);
        boolean commentsTooManyCharacters = editCiteLineReferencesPage().commentsHasTooManyCharactersError();
        addNewCiteLineReferencesPage().setComments(enoughCharacters);
        boolean commentsCorrectNumCharacters = editCiteLineReferencesPage().commentsHasTooManyCharactersError();
        Assertions.assertAll
        (
            () -> Assertions.assertTrue(commentsTooManyCharacters, "100 character limit error should be displayed"),
            () -> Assertions.assertFalse(commentsCorrectNumCharacters, "100 character limit error should not be displayed")
        );
    }
}