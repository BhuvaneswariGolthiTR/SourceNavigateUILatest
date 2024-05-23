package com.thomsonreuters.codes.codesbench.quality.tests.publishing.novus.citelinemanagement.normalizedcite;

import com.thomsonreuters.codes.codesbench.quality.pageelements.publishing.novus.citelinemanagement.nomalizedcite.AddNormalizedCitePageElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.EDGE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.CITELINE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.citelinemanagement.CitelineManagementDataMocking;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

public class NormalizedCiteAddTests extends TestService
{
    Connection connection;
    String contentSetName = "RB.UKLG";

    /**
     * STORY: HALCYONST-14864 <br>
     * SUMMARY: For Normalized Cite page, verifies that the comment field has a 100-character limit <br>
     * USER: Citeline <br>
     */
    @Test
    @EDGE
    @CITELINE
    @LOG
    public void normalizedCiteCommentsLengthAlertAddTest()
    {
        //string with more than 100 characters
        String tooManyCharacters = "too many chars: Hi I need to generate 100 characters to make sure that the testing for this functionality works as!!";
        //string with less than 100 characters
        String enoughCharacters = "Test comment";

        //navigate to normalized cite page
        homePage().goToHomePage();
        loginPage().logIn();
        publishingMenu().goToPublishingNovusCitelineManagement();
        citeLineReferencesPage().goToNormalizedCiteTab();

        //Check cite 100 character comment limit alert
        normalizedCitePage().clickAddNewNormalizedCite();
        addNewNormalizedCitePage().setComments(tooManyCharacters);
        boolean commentsTooManyCharacters = addNewNormalizedCitePage().commentsHasTooManyCharactersError();
        addNewNormalizedCitePage().setComments(enoughCharacters);
        boolean commentsCorrectNumCharacters = addNewNormalizedCitePage().commentsHasTooManyCharactersError();
        Assertions.assertAll
        (
            () -> Assertions.assertTrue(commentsTooManyCharacters, "100 character limit error should be displayed"),
            () -> Assertions.assertFalse(commentsCorrectNumCharacters, "100 character limit error should not be displayed")
        );
    }

    /**
     * STORY: HALCYONST-14864 <br>
     * SUMMARY: For Normalized Cite page, creates a citation with given fields, then verifies the citation shows
     * in the grid. Also verifies that the correct inputted values are shown in each column<br>
     * USER: Citeline <br>
     */
    @Test
    @EDGE
    @CITELINE
    @LOG
    public void normalizedCiteCreateCitationTest()
    {
        connection = BaseDatabaseUtils.connectToDatabaseUAT();
        //name for citation prefix/condensed prefix
        String citationPrefixTest = "test" + DateAndTimeUtils.getCurrentDateAndTimeyyyyMMddHHmm();

        //navigate to citeline references page
        homePage().goToHomePage();
        loginPage().logIn();
        homePage().setMyContentSet("UK Legislative");
        publishingMenu().goToPublishingNovusCitelineManagement();
        citeLineReferencesPage().goToNormalizedCiteTab();

        try
        {
            //open the create citation window, fill in the fields
            normalizedCitePage().clickAddNewNormalizedCite();
            addNewNormalizedCitePage().setNewCiteFields(citationPrefixTest, citationPrefixTest + "condensed", "Test comment");

            //Create citation and verify it appears in grid
            addNewNormalizedCitePage().clickCreate();
            normalizedCitePage().openFilterMenuForCitationPrefix();
            gridHeaderFiltersPage().setFilterValue(citationPrefixTest);
            boolean wasCiteCreated = normalizedCitePage().isCitationPrefixInGrid(citationPrefixTest);
            Assertions.assertTrue(wasCiteCreated, "Citation should appear in the grid");

            //Verify the citation column contents are what we inputted
            String rowID = normalizedCitePage().getRowID(citationPrefixTest);
            boolean citationPrefixColumnIsCorrect = normalizedCitePage().verifySelectedNormalizedCiteCitationPrefix(citationPrefixTest, rowID);
            boolean condensedPrefixColumnIsCorrect = normalizedCitePage().verifySelectedNormalizedCiteCondensedPrefix(citationPrefixTest + "condensed", rowID);
            boolean modifiedByColumnIsCorrect = normalizedCitePage().verifySelectedNormalizedCiteModifiedBy(citelineUser().getPublishingToolboxUsername(), rowID);
            boolean modifiedDateColumnIsCorrect = normalizedCitePage().verifySelectedNormalizedCiteModifiedDate(DateAndTimeUtils.getCurentDateMDYYYY(), rowID);
            boolean commentColumnIsCorrect = normalizedCitePage().verifySelectedNormalizedCiteComments("Test comment", rowID);
            Assertions.assertAll
            (
                () -> Assertions.assertTrue(citationPrefixColumnIsCorrect, "The citationPrefix in the grid is different than expected when it should be the same"),
                () -> Assertions.assertTrue(condensedPrefixColumnIsCorrect, "The condensedPrefix in the grid is different than expected when it should be the same"),
                () -> Assertions.assertTrue(modifiedByColumnIsCorrect, "The modified by user in the grid is different than expected when it should be the same"),
                () -> Assertions.assertTrue(modifiedDateColumnIsCorrect, "Date is the grid is in the incorrect format, it should be MM/dd/yyyy"),
                () -> Assertions.assertTrue(commentColumnIsCorrect, "Comment column in the grid is different than the inputted comment, it should be the same")
            );
        }
        finally
        {
            //Cleanup - Delete the created citation
            CitelineManagementDataMocking.deleteNormalizedCite(connection, citationPrefixTest,contentSetName );
        }
    }

    /**
     * STORY: JETS - 21847/21848/21849 <br>
     * SUMMARY: Verifies that Citation Prefix and Condensed Prefix can only contain special characters when they are NOT
     *          at the beginning of the entry<br>
     * USER: Citeline <br>
     */
    @Test
    @EDGE
    @CITELINE
    @LOG
    public void citationAndCondensedPrefixBoxInvalidSpecialCharacterEntryAddTest()
    {
        String invalidEntry = "!@$%&";

        //navigate to normalized cite page
        homePage().goToHomePage();
        loginPage().logIn();
        homePage().setMyContentSet(ContentSets.UK_LEGISLATIVE.getName());
        boolean citelineManagementPageLoaded = publishingMenu().goToPublishingNovusCitelineManagement();
        Assertions.assertTrue(citelineManagementPageLoaded, "Citeline Management Page Appeared");
        citeLineReferencesPage().goToNormalizedCiteTab();

        //click add new normalized cite button
        boolean normalizedCiteWindowAppeared = normalizedCitePage().clickAddNewNormalizedCite();
        Assertions.assertTrue(normalizedCiteWindowAppeared, "The normalized cite popup/page did not appear");

        //Set values to an invalid beginning special character entry, verify there is an error and buttons are not enabled
        addNewNormalizedCitePage().setCitationPrefix(invalidEntry);
        addNewNormalizedCitePage().setCondensedPrefix(invalidEntry);
        boolean citationPrefixErrorAppeared = addNewNormalizedCitePage().isElementDisplayed(String.format(AddNormalizedCitePageElements.CITATION_PREFIX_ERROR_XPATH, AddNormalizedCitePageElements.CITATION_PREFIX_CHARACTER_ERROR));
        boolean condensedPrefixErrorAppeared = addNewNormalizedCitePage().isElementDisplayed(String.format(AddNormalizedCitePageElements.CONDENSED_PREFIX_ERROR_XPATH, AddNormalizedCitePageElements.CONDENSED_PREFIX_CHARACTER_ERROR));
        boolean createButtonDisabled = addNewNormalizedCitePage().isElementDisabled(AddNormalizedCitePageElements.create);
        boolean createAnotherButtonDisabled = addNewNormalizedCitePage().isElementDisabled(AddNormalizedCitePageElements.createAnother);

        Assertions.assertAll
        (
            ()-> Assertions.assertTrue(citationPrefixErrorAppeared, "The proper error appeared when using invalid text in citation prefix"),
            ()-> Assertions.assertTrue(condensedPrefixErrorAppeared, "The proper error appeared when using invalid text in condensed prefix"),
            ()-> Assertions.assertTrue(createButtonDisabled,"The create button was disabled after entering invalid text"),
            ()-> Assertions.assertTrue(createAnotherButtonDisabled, "The create another button was disabled after entering invalid text")
        );
    }

    /**
     * STORY: Jets - 21847/21848/21849 <br>
     * SUMMARY: Verifies that the Citation Prefix and Condensed Prefix can contain numbers, spaces, and special characters
     *          Also verifies that changing from a valid to an invalid entity gives the expected errors and enable/disables
     *          the 'create' and 'create another' buttons appropriately<br>
     * USER: CITELINE <br>
     */
    @Test
    @EDGE
    @CITELINE
    @LOG
    public void citationAndCondensedPrefixBoxValidToInvalidEntityAddTest()
    {
        String entityNotValidError = String.format(AddNormalizedCitePageElements.ENTRY_NOT_VALID_ERROR, "&test;");
        String validEntry = "TEST123 &amp;";
        String invalidEntry = "1&test;";

        //navigate to normalized cite page
        homePage().goToHomePage();
        loginPage().logIn();
        homePage().setMyContentSet(ContentSets.UK_LEGISLATIVE.getName());
        boolean citelineManagementPageLoaded = publishingMenu().goToPublishingNovusCitelineManagement();
        Assertions.assertTrue(citelineManagementPageLoaded, "Cite Line Management Page Appeared");
        citeLineReferencesPage().goToNormalizedCiteTab();

        //click add new normalized cite button
        boolean normalizedCiteWindowAppeared = normalizedCitePage().clickAddNewNormalizedCite();
        Assertions.assertTrue(normalizedCiteWindowAppeared, "The normalized cite popup/page did not appear");

        //Set values to a valid entry, verify no error and buttons enabled
        addNewNormalizedCitePage().setCitationPrefix(validEntry);
        addNewNormalizedCitePage().setCondensedPrefix(validEntry);
        boolean citationPrefixValidEntry = addNewNormalizedCitePage().isElementDisplayed(String.format(AddNormalizedCitePageElements.CITATION_PREFIX_ERROR_XPATH, AddNormalizedCitePageElements.CITATION_PREFIX_CHARACTER_ERROR));
        boolean condensedPrefixValidEntry = addNewNormalizedCitePage().isElementDisplayed(String.format(AddNormalizedCitePageElements.CONDENSED_PREFIX_ERROR_XPATH, AddNormalizedCitePageElements.CONDENSED_PREFIX_CHARACTER_ERROR));
        boolean createButtonEnabled = addNewNormalizedCitePage().isElementClickable(AddNormalizedCitePageElements.create);
        boolean createAnotherButtonEnabled = addNewNormalizedCitePage().isElementClickable(AddNormalizedCitePageElements.createAnother);

        //Change values to an invalid entry, verify there is an error and buttons are not enabled
        addNewNormalizedCitePage().setCitationPrefix(invalidEntry);
        addNewNormalizedCitePage().setCondensedPrefix(invalidEntry);
        boolean citationPrefixErrorAppeared2 = addNewNormalizedCitePage().isElementDisplayed(String.format(AddNormalizedCitePageElements.CITATION_PREFIX_ERROR_XPATH, entityNotValidError));
        boolean condensedPrefixErrorAppeared2 = addNewNormalizedCitePage().isElementDisplayed(String.format(AddNormalizedCitePageElements.CONDENSED_PREFIX_ERROR_XPATH, entityNotValidError));
        boolean createButtonDisabled2 = addNewNormalizedCitePage().isElementDisabled(AddNormalizedCitePageElements.create);
        boolean createAnotherButtonDisabled2 = addNewNormalizedCitePage().isElementDisabled(AddNormalizedCitePageElements.createAnother);

        Assertions.assertAll
        (
            ()-> Assertions.assertFalse(citationPrefixValidEntry,"With a valid text an error message did appears for Citation prefix"),
            ()-> Assertions.assertFalse(condensedPrefixValidEntry, "With a valid text no error message did appears for Condensed Prefix"),
            ()-> Assertions.assertTrue(createButtonEnabled,"The create button was enabled after entering valid text"),
            ()-> Assertions.assertTrue(createAnotherButtonEnabled, "the create another button was enabled after entering valid text"),

            ()-> Assertions.assertTrue(citationPrefixErrorAppeared2, "The 'Entry is not valid' error appeared when using invalid text in citation Prefix"),
            ()-> Assertions.assertTrue(condensedPrefixErrorAppeared2, "The 'Entry is not valid' error appeared when using invalid text in condensed prefix'"),
            ()-> Assertions.assertTrue(createButtonDisabled2,"The create button was disabled after entering invalid text"),
            ()-> Assertions.assertTrue(createAnotherButtonDisabled2, "The create another button was disabled after entering invalid text")
        );
    }

    /**
     * STORY: JETS-21847/21848/21849 <br>
     * SUMMARY: This test verifies that the Citation Prefix and Condensed Prefix can not be empty when creating a citation<br>
     * USER: CITELINE <br>
     */
    @Test
    @EDGE
    @CITELINE
    @LOG
    public void citationAndCondensedPrefixBoxEraseEntryThrowsErrorAddTest()
    {
        String validEntry = "TEST123";

        //navigate to normalized cite page
        homePage().goToHomePage();
        loginPage().logIn();
        homePage().setMyContentSet(ContentSets.UK_LEGISLATIVE.getName());
        boolean citelineManagementPageLoaded = publishingMenu().goToPublishingNovusCitelineManagement();
        Assertions.assertTrue(citelineManagementPageLoaded, "Cite Line Management Page Appeared");
        citeLineReferencesPage().goToNormalizedCiteTab();

        //click add new normalized cite button
        boolean normalizedCiteWindowAppeared = normalizedCitePage().clickAddNewNormalizedCite();
        Assertions.assertTrue(normalizedCiteWindowAppeared, "The normalized cite popup/page did not appear");

        //Set values to a valid entry
        addNewNormalizedCitePage().setCitationPrefix(validEntry);
        addNewNormalizedCitePage().setCondensedPrefix(validEntry);

        //Change values to an empty field: verify there is an error and buttons are not clickable
        addNewNormalizedCitePage().clearCitationPrefix(validEntry);
        addNewNormalizedCitePage().clearCondensedPrefix(validEntry);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.THREE_SECONDS);
        boolean citationPrefixErrorAppeared = addNewNormalizedCitePage().isElementDisplayed(String.format(AddNormalizedCitePageElements.CITATION_PREFIX_ERROR_XPATH, AddNormalizedCitePageElements.CITATION_PREFIX_IS_ERROR)  + AddNormalizedCitePageElements.REQUIRED);
        boolean condensedPrefixErrorAppeared = addNewNormalizedCitePage().isElementDisplayed(String.format(AddNormalizedCitePageElements.CONDENSED_PREFIX_ERROR_XPATH, AddNormalizedCitePageElements.CONDENSED_PREFIX_IS_ERROR)  + AddNormalizedCitePageElements.REQUIRED);
        boolean createButtonDisabled = addNewNormalizedCitePage().isElementDisabled(AddNormalizedCitePageElements.create);
        boolean createAnotherButtonDisabled = normalizedCitePage().isElementDisabled(AddNormalizedCitePageElements.createAnother);
        Assertions.assertAll
        (
            () -> Assertions.assertTrue(citationPrefixErrorAppeared, "The citation prefix field should not be allowed to be empty"),
            () -> Assertions.assertTrue(condensedPrefixErrorAppeared, "The condensed prefix field should not be allowed to be empty"),
            () -> Assertions.assertTrue(createButtonDisabled, "The create button was disabled after having empty fields"),
            () -> Assertions.assertTrue(createAnotherButtonDisabled, "the create another button disabled after having empty fields")
        );
    }

    /**
     * STORY: Jets - 21847/21848/21849 <br>
     * SUMMARY: Verifies that there are no duplicates allowed for the citation prefix when creating a citation<br>
     * USER: CITELINE <br>
     */
    @Test
    @EDGE
    @CITELINE
    @LOG
    public void citationPrefixDuplicatesAreInvalidAddTest()
    {
        connection = BaseDatabaseUtils.connectToDatabaseUAT();
        String validEntry = "validEntry";
        String duplicateEntry = "dupEntry";
        String condensedPrefix = "Test";

        //Create citation to beck checked against
        CitelineManagementDataMocking.insertNormalizedCite(connection, 0, duplicateEntry, condensedPrefix, Integer.parseInt(ContentSets.UK_LEGISLATIVE.getCode()), contentSetName);

        //navigate to normalized cite page
        homePage().goToHomePage();
        loginPage().logIn();
        homePage().setMyContentSet(ContentSets.UK_LEGISLATIVE.getName());
        boolean citelineManagementPageLoaded = publishingMenu().goToPublishingNovusCitelineManagement();
        Assertions.assertTrue(citelineManagementPageLoaded, "Citeline Management Page Appeared");
        citeLineReferencesPage().goToNormalizedCiteTab();

        try
        {
            //Click Add New Normalized Cite and enter the duplicate entry. Verify there is an error and buttons are not enabled
            boolean normalizedCiteWindowAppeared = normalizedCitePage().clickAddNewNormalizedCite();
            Assertions.assertTrue(normalizedCiteWindowAppeared, "the normalized cite popup/page did not appear");
            addNewNormalizedCitePage().setCitationPrefix(duplicateEntry);
            addNewNormalizedCitePage().setCondensedPrefix(condensedPrefix);
            boolean citationPrefixInvalidEntry = addNewNormalizedCitePage().isElementDisplayed(String.format(AddNormalizedCitePageElements.CITATION_PREFIX_ERROR_XPATH, AddNormalizedCitePageElements.DUPLICATE_CITATION_PREFIX_ERROR));
            boolean createButtonDisabled = addNewNormalizedCitePage().isElementEnabled(AddNormalizedCitePageElements.create);
            boolean createAnotherButtonDisabled = addNewNormalizedCitePage().isElementEnabled(AddNormalizedCitePageElements.createAnother);

            //Change values to a valid entry, verify no error and buttons enabled
            addNewNormalizedCitePage().setCitationPrefix(validEntry);
            addNewNormalizedCitePage().setCondensedPrefix(validEntry);
            boolean citationPrefixValidEntry = addNewNormalizedCitePage().isElementDisplayed(String.format(AddNormalizedCitePageElements.CITATION_PREFIX_ERROR_XPATH, AddNormalizedCitePageElements.DUPLICATE_CITATION_PREFIX_ERROR));
            boolean createButtonEnabled = addNewNormalizedCitePage().isElementEnabled(AddNormalizedCitePageElements.create);
            boolean createAnotherButtonEnabled = addNewNormalizedCitePage().isElementEnabled(AddNormalizedCitePageElements.createAnother);

            Assertions.assertAll
            (
                () -> Assertions.assertTrue(citationPrefixInvalidEntry, "The duplicate entry error appeared for citation prefix "),
                () -> Assertions.assertFalse(createButtonDisabled, "The create button was disabled after entering invalid text"),
                () -> Assertions.assertFalse(createAnotherButtonDisabled, "The create another button was disabled after entering invalid text"),

                () -> Assertions.assertFalse(citationPrefixValidEntry, "The duplicate entry error went away with a valid entry"),
                () -> Assertions.assertTrue(createButtonEnabled, "The create button was enabled after entering valid text"),
                () -> Assertions.assertTrue(createAnotherButtonEnabled, "the create another button was enabled after entering valid text")
            );
        }
        finally
        {
            //cleanup, delete the citation created
            CitelineManagementDataMocking.deleteNormalizedCite(connection, duplicateEntry, contentSetName);
        }
    }

    /**
     * STORY: Jets - 21847/21848/21849 <br>
     * SUMMARY: Multiple normalized cites show up in descending alphabetical order after creating them using the Create Another button<br>
     * USER: CITELINE <br>
     */
    @Test
    @EDGE
    @CITELINE
    @LOG
    public void normalizedCiteVerifyDescendingAlphabeticOrderCreateAnotherTest()
    {
        String citationPrefix1 = "TEST1 multiAlphaTest";
        String citationPrefix2 = "TEST2 multiAlphaTest";
        String citationPrefix3 = "TEST3 multiAlphaTest";
        String condensedPrefix = "CreateAnotherAlphaTest";

        //navigate to normalized cite page
        homePage().goToHomePage();
        loginPage().logIn();
        homePage().setMyContentSet(ContentSets.UK_LEGISLATIVE.getName());
        publishingMenu().goToPublishingNovusCitelineManagement();
        citeLineReferencesPage().goToNormalizedCiteTab();

        try
        {
            //click add new Normalized Cite, create 3 citations utilizing the 'create another' and 'create' buttons, verify
            //  fields are cleared when clicking create another
            normalizedCitePage().clickAddNewNormalizedCite();
            addNewNormalizedCitePage().setCitationPrefix(citationPrefix1);
            addNewNormalizedCitePage().setCondensedPrefix(condensedPrefix);
            addNewNormalizedCitePage().clickCreateAnother();

            boolean citePrefixCleared1 = addNewNormalizedCitePage().getElementsText(AddNormalizedCitePageElements.citationPrefixInput).equals("");
            boolean condensedPrefixCleared1 = addNewNormalizedCitePage().getElementsText(AddNormalizedCitePageElements.citationPrefixInput).equals("");
            addNewNormalizedCitePage().setCitationPrefix(citationPrefix2);
            addNewNormalizedCitePage().setCondensedPrefix(condensedPrefix);
            addNewNormalizedCitePage().clickCreateAnother();

            boolean citePrefixCleared2 = addNewNormalizedCitePage().getElementsText(AddNormalizedCitePageElements.citationPrefixInput).equals("");
            boolean condensedPrefixCleared2 = addNewNormalizedCitePage().getElementsText(AddNormalizedCitePageElements.citationPrefixInput).equals("");
            addNewNormalizedCitePage().setCitationPrefix(citationPrefix3);
            addNewNormalizedCitePage().setCondensedPrefix(condensedPrefix);
            addNewNormalizedCitePage().clickCreateAnother();
            addNewNormalizedCitePage().clickCancelButton(); //click cancel to test cancel functinoality

            //filter for citations, verify the citations are in descending citation prefix order
            normalizedCitePage().openFilterMenuForCondensedPrefix();
            gridHeaderFiltersPage().setFilterValue(condensedPrefix);
            normalizedCitePage().clickFirstCitationPrefix();
            boolean entry3IsInOrder = normalizedCitePage().verifySelectedNormalizedCiteCitationPrefixByRowIndex(citationPrefix3, "0");
            boolean entry2IsInOrder = normalizedCitePage().verifySelectedNormalizedCiteCitationPrefixByRowIndex(citationPrefix2, "1");
            boolean entry1IsInOrder = normalizedCitePage().verifySelectedNormalizedCiteCitationPrefixByRowIndex(citationPrefix1, "2");
            Assertions.assertAll
            (
                () -> Assertions.assertTrue(citePrefixCleared1, "On create another, the citation prefix field should be cleared"),
                () -> Assertions.assertTrue(condensedPrefixCleared1, "On create another, the condensed prefix field should be cleared"),
                () -> Assertions.assertTrue(citePrefixCleared2, "On create another, the citation prefix field should be cleared"),
                () -> Assertions.assertTrue(condensedPrefixCleared2, "On create another, the condensed prefix field should be cleared"),
                () -> Assertions.assertTrue(entry3IsInOrder, "entry 3 is in the expected location"),
                () -> Assertions.assertTrue(entry2IsInOrder, "entry 2 is in the expected location"),
                () -> Assertions.assertTrue(entry1IsInOrder, "entry 1 is in the expected location")
            );
        }
        finally
        {
            //cleanup, delete the citations created
            connection = BaseDatabaseUtils.connectToDatabaseUAT();
            CitelineManagementDataMocking.deleteNormalizedCite(connection, citationPrefix1, contentSetName);
            CitelineManagementDataMocking.deleteNormalizedCite(connection, citationPrefix2, contentSetName);
            CitelineManagementDataMocking.deleteNormalizedCite(connection, citationPrefix3, contentSetName);
        }
    }

    /**
     * STORY: Jets - 21847/21848/21849 <br>
     * SUMMARY: Once a cite is created we want to make sure it is in descending order<br>
     * USER: CITELINE <br>
     */
    @Test
    @EDGE
    @CITELINE
    @LOG
    public void normalizedCiteVerifyDescendingAlphabeticOrderCreateTest()
    {
        connection = BaseDatabaseUtils.connectToDatabaseUAT();
        String citationPrefix1 = "Test122 &amp;";
        String citationPrefix2 = "Test123 &amp;";
        String citationPrefix3 = "Test124 &amp;";
        String condensedPrefix = "testing Alphabetical Order";

        //Create citations to be used
        CitelineManagementDataMocking.insertNormalizedCite(connection,  0, citationPrefix1, condensedPrefix, Integer.parseInt(ContentSets.UK_LEGISLATIVE.getCode()), contentSetName);
        CitelineManagementDataMocking.insertNormalizedCite(connection,  1, citationPrefix3, condensedPrefix, Integer.parseInt(ContentSets.UK_LEGISLATIVE.getCode()), contentSetName);

        //navigate to normalized cite page
        homePage().goToHomePage();
        loginPage().logIn();
        homePage().setMyContentSet(ContentSets.UK_LEGISLATIVE.getName());
        boolean citelineManagementPageLoaded = publishingMenu().goToPublishingNovusCitelineManagement();
        Assertions.assertTrue(citelineManagementPageLoaded, "Citeline Management Page Appeared");
        citeLineReferencesPage().goToNormalizedCiteTab();

        try
        {
            //Create citation  to check grid reordering
            boolean normalizedCiteWindowAppeared = normalizedCitePage().clickAddNewNormalizedCite();
            Assertions.assertTrue(normalizedCiteWindowAppeared, "The normalized cite popup/page did not appear");
            addNewNormalizedCitePage().setCitationPrefix(citationPrefix2);
            addNewNormalizedCitePage().setCondensedPrefix(condensedPrefix);
            addNewNormalizedCitePage().clickCreate();

            //filter for citations, verify the citations are in descending citation prefix order
            normalizedCitePage().openFilterMenuForCondensedPrefix();
            gridHeaderFiltersPage().setFilterValue(condensedPrefix);
            normalizedCitePage().clickFirstCitationPrefix();
            boolean entry3IsInOrder = normalizedCitePage().verifySelectedNormalizedCiteCitationPrefixByRowIndex(citationPrefix3, "0");
            boolean entry2IsInOrder = normalizedCitePage().verifySelectedNormalizedCiteCitationPrefixByRowIndex(citationPrefix2, "1");
            boolean entry1IsInOrder = normalizedCitePage().verifySelectedNormalizedCiteCitationPrefixByRowIndex(citationPrefix1, "2");
            Assertions.assertAll
            (
                () -> Assertions.assertTrue(entry3IsInOrder, "entry 3 is in the expected location"),
                () -> Assertions.assertTrue(entry2IsInOrder, "entry 2 is in the expected location"),
                () -> Assertions.assertTrue(entry1IsInOrder, "entry 1 is in the expected location")
            );
        }
        finally
        {
            //cleanup, delete the citations created
            CitelineManagementDataMocking.deleteNormalizedCite(connection, citationPrefix1, contentSetName);
            CitelineManagementDataMocking.deleteNormalizedCite(connection, citationPrefix2, contentSetName);
            CitelineManagementDataMocking.deleteNormalizedCite(connection, citationPrefix3, contentSetName);
        }
    }

    @AfterEach
    public void cleanUp()
    {
        if (connection != null)
        {
            BaseDatabaseUtils.disconnect(connection);
        }
    }
}