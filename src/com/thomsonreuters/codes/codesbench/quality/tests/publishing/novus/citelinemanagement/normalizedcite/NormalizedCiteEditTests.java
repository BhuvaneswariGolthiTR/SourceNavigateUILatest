package com.thomsonreuters.codes.codesbench.quality.tests.publishing.novus.citelinemanagement.normalizedcite;

import com.thomsonreuters.codes.codesbench.quality.pageelements.publishing.novus.citelinemanagement.nomalizedcite.EditNormalizedCitePageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.publishing.novus.citelinemanagement.nomalizedcite.NormalizedCiteReferencesPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.publishing.toolbox.GridHeaderElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.EDGE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.CITELINE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.citelinemanagement.CitelineManagementDataMocking;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets;
import org.junit.jupiter.api.*;

import java.sql.Connection;

public class NormalizedCiteEditTests extends TestService
{
    Connection connection;
    String citationPrefix;
    private static final String contentSetName = "RB.UKLG";

    @BeforeEach
    public void mockUpCitation()
    {
        connection = BaseDatabaseUtils.connectToDatabaseUAT();
        //name for citation prefix/condensed prefix
        citationPrefix = "test" + DateAndTimeUtils.getCurrentDateAndTimeyyyyMMddHHmm();
        //Create cite through database to modify
        CitelineManagementDataMocking.insertNormalizedCite(connection,  0, citationPrefix, citationPrefix+"condensed", Integer.parseInt(ContentSets.UK_LEGISLATIVE.getCode()), contentSetName);
    }

    @AfterEach
    public void closeConnection()
    {
        CitelineManagementDataMocking.deleteNormalizedCite(connection, citationPrefix, contentSetName);
        BaseDatabaseUtils.disconnect(connection);
    }

    /**
     * STORY: HALCYONST-14864 <br>
     * SUMMARY: For Normalized Cite page, verifies that the edit comment field has a 100-character limit <br>
     * USER: Citeline <br>
     */
    @Test
    @EDGE
    @CITELINE
    @LOG
    public void normalizedCiteCommentsLengthAlertEditTest()
    {
        //string with more than 100 characters
        String tooManyCharacters = "too many chars: Hi I need to generate 100 characters to make sure that the testing for this functionality works as!!";
        //string with less than 100 characters
        String enoughCharacters = "Test comment";

        //navigate to normalized cite page
        homePage().goToHomePage();
        loginPage().logIn();
        homePage().setMyContentSet(ContentSets.UK_LEGISLATIVE.getName());
        publishingMenu().goToPublishingNovusCitelineManagement();
        citeLineReferencesPage().goToNormalizedCiteTab();

        //edit cite, check cite 100 character comment limit alert
        normalizedCitePage().openFilterMenuForCitationPrefix();
        gridHeaderFiltersPage().setFilterValue(citationPrefix);
        normalizedCitePage().clickEdit(normalizedCitePage().getRowID(citationPrefix));
        editNormalizedCitePage().setComments(tooManyCharacters);
        boolean editCommentsTooManyCharacters = editNormalizedCitePage().commentsHasTooManyCharactersError();
        editNormalizedCitePage().setComments(enoughCharacters);
        boolean editCommentsCorrectNumCharacters = editNormalizedCitePage().commentsHasTooManyCharactersError();
        Assertions.assertAll
        (
            () -> Assertions.assertTrue(editCommentsTooManyCharacters, "100 character limit error should have been displayed"),
            () -> Assertions.assertFalse(editCommentsCorrectNumCharacters, "100 character limit error should not have been displayed")
        );
    }

    /**
     * STORY: HALCYONST-14864 <br>
     * SUMMARY: For Normalized Cite page, verifies that values are not updated when cancelling a citation edit.
     * Then verifies that the values are updated correctly when successfully updating a citation edit <br>
     * USER: Citeline <br>
     */
    @Test
    @EDGE
    @CITELINE
    @LOG
    public void normalizedCiteEditCitationTest()
    {
        //name for citation prefix/condensed prefix
        String citationPrefixChange = citationPrefix + "Change";
        String condensedPrefix = citationPrefix + "condensed";
        String condensedPrefixChange = citationPrefix + "condensed change";
        String commentChange = "test comment change";

        //Navigate to citeline references page
        homePage().goToHomePage();
        loginPage().logIn();
        homePage().setMyContentSet(ContentSets.UK_LEGISLATIVE.getName());
        publishingMenu().goToPublishingNovusCitelineManagement();
        citeLineReferencesPage().goToNormalizedCiteTab();

        try
        {
            //filter for created citation
            normalizedCitePage().openFilterMenuForCitationPrefix();
            gridHeaderFiltersPage().setFilterValue(citationPrefix);

            //Edit selected citation and cancel, verify grid does not update with new values
            normalizedCitePage().clickEdit(normalizedCitePage().getRowID(citationPrefix));
            editNormalizedCitePage().setNewCiteFields(citationPrefixChange, condensedPrefixChange, commentChange);
            editNormalizedCitePage().clickCancelButton();
            String rowID = normalizedCitePage().getRowID(citationPrefix);
            boolean citationPrefixCancelled = normalizedCitePage().verifySelectedNormalizedCiteCitationPrefix(citationPrefix, rowID);
            boolean condensedPrefixCancelled = normalizedCitePage().verifySelectedNormalizedCiteCondensedPrefix(condensedPrefix, rowID);
            boolean commentCancelled = normalizedCitePage().verifySelectedNormalizedCiteComments("", rowID);
            Assertions.assertAll
            (
                () -> Assertions.assertTrue(citationPrefixCancelled, "citationPrefix should not have been updated"),
                () -> Assertions.assertTrue(condensedPrefixCancelled, "condensedPrefix should not have been updated"),
                () -> Assertions.assertTrue(commentCancelled, "Comment should not have been updated")
            );

            //Edit selected citation and submit, verify grid updates with new values
            normalizedCitePage().clickEdit(rowID);
            editNormalizedCitePage().setNewCiteFields(citationPrefixChange, condensedPrefixChange, commentChange);
            editNormalizedCitePage().clickUpdateValues();
            boolean citationPrefixUpdated = normalizedCitePage().verifySelectedNormalizedCiteCitationPrefix(citationPrefixChange, rowID);
            boolean condensedPrefixUpdated = normalizedCitePage().verifySelectedNormalizedCiteCondensedPrefix(condensedPrefixChange, rowID);
            boolean commentUpdated = normalizedCitePage().verifySelectedNormalizedCiteComments(commentChange, rowID);
            Assertions.assertAll
            (
                () -> Assertions.assertTrue(citationPrefixUpdated, "citationPrefix should have been updated"),
                () -> Assertions.assertTrue(condensedPrefixUpdated, "condensedPrefix should have been updated"),
                () -> Assertions.assertTrue(commentUpdated, "Comment should have been updated")
            );
        }
        finally
        {
            //cleanup, delete any created citations
            connection = BaseDatabaseUtils.connectToDatabaseUAT();
            CitelineManagementDataMocking.deleteNormalizedCite(connection, citationPrefixChange, contentSetName);
        }
    }

    /**
     * STORY: JETS - 21847/21848/21849 <br>
     * SUMMARY: For Citation Prefix and Condensed Prefix entries, this test verifies special characters can NOT start at the beginning of the entry<br>
     * USER: Citeline <br>
     */
    @Test
    @EDGE
    @CITELINE
    @LOG
    public void citationAndCondensedPrefixBoxInvalidSpecialCharacterEntryEditTest()
    {
        connection = BaseDatabaseUtils.connectToDatabaseUAT();
        String invalidEntry = "!@$%&";

        //navigate to normalized cite page
        homePage().goToHomePage();
        loginPage().logIn();
        homePage().setMyContentSet(ContentSets.UK_LEGISLATIVE.getName());
        boolean citelineManagementPageLoaded = publishingMenu().goToPublishingNovusCitelineManagement();
        Assertions.assertTrue(citelineManagementPageLoaded, "Citeline Management Page Appeared");
        citeLineReferencesPage().goToNormalizedCiteTab();

        try
        {
            //Edit citation we created
            normalizedCitePage().openFilterMenuForCitationPrefix();
            gridHeaderFiltersPage().setFilterValue(citationPrefix);
            String rowId = normalizedCitePage().getRowID(citationPrefix);
            normalizedCitePage().clickEdit(rowId);

            //set  values to invalid symbol entry: verify there is an error and buttons are not clickable
            editNormalizedCitePage().setCitationPrefix(invalidEntry);
            editNormalizedCitePage().setCondensedPrefix(invalidEntry);
            boolean citationPrefixErrorAppeared = editNormalizedCitePage().isElementDisplayed(String.format(EditNormalizedCitePageElements.CITATION_PREFIX_ERROR_XPATH, EditNormalizedCitePageElements.CITATION_PREFIX_CHARACTER_ERROR));
            boolean condensedPrefixErrorAppeared = editNormalizedCitePage().isElementDisplayed(String.format(EditNormalizedCitePageElements.CONDENSED_PREFIX_ERROR_XPATH, EditNormalizedCitePageElements.CONDENSED_PREFIX_CHARACTER_ERROR));
            boolean updateValuesButtonDisabled = editNormalizedCitePage().isElementDisabled(EditNormalizedCitePageElements.updateValues);

            Assertions.assertAll
            (
                () -> Assertions.assertTrue(citationPrefixErrorAppeared, "The proper error appeared when using invalid text in citation prefix"),
                () -> Assertions.assertTrue(condensedPrefixErrorAppeared, "The proper error appeared when using invalid text in condensed prefix"),
                () -> Assertions.assertTrue(updateValuesButtonDisabled, "The update values button was disabled after entering invalid text")
            );
        }
        finally
        {
            //Cleanup, remove citation we added
            CitelineManagementDataMocking.deleteNormalizedCite(connection, citationPrefix, contentSetName);
        }
    }

    /**
     * STORY: Jets - 21847/21848/21849 <br>
     * SUMMARY: Verifies that the Citation Prefix and Condensed Prefix can contain numbers, spaces, and special characters
     *      *          Also verifies that changing from a valid to an invalid entity gives the expected errors and enable/disables
     *      *          the 'create' and 'create another' buttons appropriately<br>
     * USER: CITELINE <br>
     */
    @Test
    @EDGE
    @CITELINE
    @LOG
    public void citationAndCondensedPrefixBoxValidToInvalidEntityEditTest()
    {
        connection = BaseDatabaseUtils.connectToDatabaseUAT();
        String validEntry = "TEST123 &amp;";
        String invalidEntry = "1&test;";
        String entityNotValidError = String.format(EditNormalizedCitePageElements.ENTRY_NOT_VALID_ERROR, "&test;");

        //navigate to normalized cite page
        homePage().goToHomePage();
        loginPage().logIn();
        homePage().setMyContentSet(ContentSets.UK_LEGISLATIVE.getName());
        boolean citelineManagementPageLoaded = publishingMenu().goToPublishingNovusCitelineManagement();
        Assertions.assertTrue(citelineManagementPageLoaded, "Citeline Management Page Appeared");
        citeLineReferencesPage().goToNormalizedCiteTab();

        try
        {
            //Edit citation we created
            normalizedCitePage().openFilterMenuForCitationPrefix();
            gridHeaderFiltersPage().setFilterValue(citationPrefix);
            String rowId = normalizedCitePage().getRowID(citationPrefix);
            normalizedCitePage().clickEdit(rowId);

            //Add valid entry into input fields
            addNewNormalizedCitePage().setCitationPrefix(validEntry);
            addNewNormalizedCitePage().setCondensedPrefix(validEntry);
            boolean citationPrefixErrorAppeared = editNormalizedCitePage().isElementDisplayed(String.format(EditNormalizedCitePageElements.CITATION_PREFIX_ERROR_XPATH, EditNormalizedCitePageElements.CITATION_PREFIX_CHARACTER_ERROR));
            boolean condensedPrefixErrorAppeared = editNormalizedCitePage().isElementDisplayed(String.format(EditNormalizedCitePageElements.CONDENSED_PREFIX_ERROR_XPATH, EditNormalizedCitePageElements.CONDENSED_PREFIX_CHARACTER_ERROR));
            boolean updateValuesButtonDisabled = editNormalizedCitePage().isElementDisabled(EditNormalizedCitePageElements.updateValues);

            //Change values to an invalid entity: verify there is an error and buttons are not clickable
            addNewNormalizedCitePage().setCitationPrefix(invalidEntry);
            addNewNormalizedCitePage().setCondensedPrefix(invalidEntry);
            boolean citationPrefixErrorAppeared2 = addNewNormalizedCitePage().isElementDisplayed(String.format(EditNormalizedCitePageElements.CITATION_PREFIX_ERROR_XPATH, entityNotValidError));
            boolean condensedPrefixErrorAppeared2 = addNewNormalizedCitePage().isElementDisplayed(String.format(EditNormalizedCitePageElements.CONDENSED_PREFIX_ERROR_XPATH, entityNotValidError));
            boolean updateValuesButtonDisabled2 = addNewNormalizedCitePage().isElementDisabled(EditNormalizedCitePageElements.updateValues);

            Assertions.assertAll
            (
                () -> Assertions.assertFalse(citationPrefixErrorAppeared, "With a valid text an error message did appears for citation prefix"),
                () -> Assertions.assertFalse(condensedPrefixErrorAppeared, "With a valid text no error message did appears for condensed prefix"),
                () -> Assertions.assertFalse(updateValuesButtonDisabled, "The update values button was enabled after entering valid text"),

                () -> Assertions.assertTrue(citationPrefixErrorAppeared2, "The 'Entry is not valid' error appeared when using invalid text in citation Prefix"),
                () -> Assertions.assertTrue(condensedPrefixErrorAppeared2, "The 'Entry is not valid' error appeared when using invalid text in condensed prefix'"),
                () -> Assertions.assertTrue(updateValuesButtonDisabled2, "The update values button was disabled after entering invalid text")
            );
        }
        finally
        {
            //Cleanup, remove citation we added
            CitelineManagementDataMocking.deleteNormalizedCite(connection, citationPrefix, contentSetName);
        }
    }

    /**
     * STORY: JETS-21847/21848/21849 <br>
     * SUMMARY: Citation Prefix and Condensed Prefix can not be empty<br>
     * USER: CITELINE <br>
     */
    @Test
    @EDGE
    @CITELINE
    @LOG
    public void citationAndCondensedPrefixBoxEraseEntryThrowsErrorEditTest()
    {
        connection = BaseDatabaseUtils.connectToDatabaseUAT();
        String validEntry = "TEST123";

        //navigate to normalized cite page
        homePage().goToHomePage();
        loginPage().logIn();
        homePage().setMyContentSet(ContentSets.UK_LEGISLATIVE.getName());
        boolean citelineManagementPageLoaded = publishingMenu().goToPublishingNovusCitelineManagement();
        Assertions.assertTrue(citelineManagementPageLoaded, "Citeline Management Page Appeared");
        citeLineReferencesPage().goToNormalizedCiteTab();

        try
        {
            //Edit citation we created
            normalizedCitePage().openFilterMenuForCitationPrefix();
            gridHeaderFiltersPage().setFilterValue(citationPrefix);
            String rowId = normalizedCitePage().getRowID(citationPrefix);
            normalizedCitePage().clickEdit(rowId);

            //Change values to a valid entity
            editNormalizedCitePage().setCitationPrefix(validEntry);
            editNormalizedCitePage().setCondensedPrefix(validEntry);

            //Change values to an empty field: verify there is an error and buttons are not clickable
            editNormalizedCitePage().clearCitationPrefix(validEntry);
            editNormalizedCitePage().clearCondensedPrefix(validEntry);
            DateAndTimeUtils.takeNap(DateAndTimeUtils.THREE_SECONDS);
            boolean citationPrefixErrorAppeared = editNormalizedCitePage().isElementDisplayed(String.format(EditNormalizedCitePageElements.CITATION_PREFIX_ERROR_XPATH, EditNormalizedCitePageElements.CITATION_PREFIX_IS_ERROR) + EditNormalizedCitePageElements.REQUIRED);
            boolean condensedPrefixErrorAppeared = editNormalizedCitePage().isElementDisplayed(String.format(EditNormalizedCitePageElements.CONDENSED_PREFIX_ERROR_XPATH, EditNormalizedCitePageElements.CONDENSED_PREFIX_IS_ERROR) + EditNormalizedCitePageElements.REQUIRED);
            boolean updateValuesButtonDisabled = editNormalizedCitePage().isElementDisabled(EditNormalizedCitePageElements.updateValues);

            Assertions.assertAll
            (
                () -> Assertions.assertTrue(citationPrefixErrorAppeared, "The citation field should not be allowed to be empty"),
                () -> Assertions.assertTrue(condensedPrefixErrorAppeared, "The citation field should not be allowed to be empty"),
                () -> Assertions.assertTrue(updateValuesButtonDisabled, "The update values button was disabled after having empty fields")
            );
        }
        finally
        {
            //Cleanup, remove citation we added
            CitelineManagementDataMocking.deleteNormalizedCite(connection, citationPrefix, contentSetName);
        }
    }

    /**
     * STORY: Jets - 21847/21848/21849 <br>
     * SUMMARY: Verifies that there are no duplicates allowed for the citation Prefix edit<br>
     * USER: CITELINE <br>
     */
    @Test
    @EDGE
    @CITELINE
    @LOG
    public void citationPrefixDuplicatesAreInvalidEditTest()
    {
        connection = BaseDatabaseUtils.connectToDatabaseUAT();
        String validEntry = "validEntry";
        String duplicateEntry = "dupEntry";
        String condensedPrefix = "Test";

        //Create citation to beck checked against
        CitelineManagementDataMocking.insertNormalizedCite(connection, 0, duplicateEntry, condensedPrefix, Integer.parseInt(ContentSets.UK_LEGISLATIVE.getCode()), contentSetName);
        CitelineManagementDataMocking.insertNormalizedCite(connection, 0, validEntry, condensedPrefix, Integer.parseInt(ContentSets.UK_LEGISLATIVE.getCode()), contentSetName);

        //Navigate to normalized cite page
        homePage().goToHomePage();
        loginPage().logIn();
        homePage().setMyContentSet(ContentSets.UK_LEGISLATIVE.getName());
        boolean citelineManagementPageLoaded = publishingMenu().goToPublishingNovusCitelineManagement();
        Assertions.assertTrue(citelineManagementPageLoaded, "Citeline Management Page Appeared");
        citeLineReferencesPage().goToNormalizedCiteTab();

        try
        {
            //Edit one of the created citations and set the prefix to duplicate the other citation. Verify there is an error and buttons are not enabled
            normalizedCitePage().openFilterMenuForCondensedPrefix();
            gridHeaderFiltersPage().setFilterValue(condensedPrefix);
            String rowId = normalizedCitePage().getRowID(validEntry);
            normalizedCitePage().clickEdit(rowId);
            editNormalizedCitePage().setCitationPrefix(duplicateEntry);
            editNormalizedCitePage().setCondensedPrefix(duplicateEntry);
            boolean citationPrefixInvalidEntry = editNormalizedCitePage().isElementDisplayed(String.format(EditNormalizedCitePageElements.CITATION_PREFIX_ERROR_XPATH, EditNormalizedCitePageElements.DUPLICATE_CITATION_PREFIX_ERROR));
            boolean updateButtonDisabled = editNormalizedCitePage().isElementEnabled(EditNormalizedCitePageElements.updateValues);

            Assertions.assertAll
            (
                () -> Assertions.assertTrue(citationPrefixInvalidEntry, "The duplicate entry error appeared for citation prefix "),
                () -> Assertions.assertFalse(updateButtonDisabled, "The update button was disabled after entering invalid text")
            );
        }
        finally
        {
            //Cleanup, delete the citations created
            CitelineManagementDataMocking.deleteNormalizedCite(connection, duplicateEntry, contentSetName);
            CitelineManagementDataMocking.deleteNormalizedCite(connection, validEntry, contentSetName);
        }
    }

    /**
     * STORY: Jets - 21847/21848/21849 <br>
     * SUMMARY: This verifies that once a cite is edited, it is updated to stay in descending order<br>
     * USER: CITELINE <br>
     */
    @Test
    @EDGE
    @CITELINE
    @LOG
    public void normalizedCiteVerifyDescendingAlphabeticOrderEditTest()
    {
        connection = BaseDatabaseUtils.connectToDatabaseUAT();
        String citationPrefixEntry = "Test122 &amp;";
        String citationPrefixEntry2 = "Test123 &amp;";
        String citationPrefixEntry2Updated = "Test225 &amp;";
        String citationPrefixEntry3 = "Test124 &amp;";
        String condensedPrefixEntry = "Test123 &amp;Condensed";

        //Create citations to be used
        CitelineManagementDataMocking.insertNormalizedCite(connection,  0, citationPrefixEntry, condensedPrefixEntry, Integer.parseInt(ContentSets.UK_LEGISLATIVE.getCode()), contentSetName);
        CitelineManagementDataMocking.insertNormalizedCite(connection,  1, citationPrefixEntry2, condensedPrefixEntry, Integer.parseInt(ContentSets.UK_LEGISLATIVE.getCode()), contentSetName);
        CitelineManagementDataMocking.insertNormalizedCite(connection,  2, citationPrefixEntry3, condensedPrefixEntry, Integer.parseInt(ContentSets.UK_LEGISLATIVE.getCode()), contentSetName);

        //navigate to normalized cite page
        homePage().goToHomePage();
        loginPage().logIn();
        homePage().setMyContentSet(ContentSets.UK_LEGISLATIVE.getName());
        boolean citelineManagementPageLoaded = publishingMenu().goToPublishingNovusCitelineManagement();
        Assertions.assertTrue(citelineManagementPageLoaded, "Citeline Management Page Appeared");
        citeLineReferencesPage().goToNormalizedCiteTab();

        try
        {
            //filter for citations, validate they appear in the grid
            normalizedCitePage().openFilterMenuForCondensedPrefix();
            gridHeaderFiltersPage().setFilterValue(condensedPrefixEntry);

            //Edit the 2nd citation and update values
            String rowId2 = normalizedCitePage().getRowID(citationPrefixEntry2);
            normalizedCitePage().clickEdit(rowId2);
            editNormalizedCitePage().setCitationPrefix(citationPrefixEntry2Updated);
            editNormalizedCitePage().clickUpdateValues();

            //verify the citations are in descending citation prefix order
            normalizedCitePage().clickFirstCitationPrefix();
            boolean entry2IsInOrder = normalizedCitePage().verifySelectedNormalizedCiteCitationPrefixByRowIndex(citationPrefixEntry2Updated, "0");
            boolean entry3IsInOrder = normalizedCitePage().verifySelectedNormalizedCiteCitationPrefixByRowIndex(citationPrefixEntry3, "1");
            boolean entry1IsInOrder = normalizedCitePage().verifySelectedNormalizedCiteCitationPrefixByRowIndex(citationPrefixEntry, "2");
            Assertions.assertAll
            (
                () -> Assertions.assertTrue(entry3IsInOrder, "entry 3 is in the expected location"),
                () -> Assertions.assertTrue(entry2IsInOrder, "entry 2 is in the expected location"),
                () -> Assertions.assertTrue(entry1IsInOrder, "entry 1 is in the expected location")
            );
        }
        finally
        {
            //cleanup, delete created citations
            CitelineManagementDataMocking.deleteNormalizedCite(connection, citationPrefixEntry, contentSetName);
            CitelineManagementDataMocking.deleteNormalizedCite(connection, citationPrefixEntry2, contentSetName);
            CitelineManagementDataMocking.deleteNormalizedCite(connection, citationPrefixEntry3, contentSetName);
            CitelineManagementDataMocking.deleteNormalizedCite(connection, citationPrefixEntry2Updated, contentSetName);
        }
    }
}
