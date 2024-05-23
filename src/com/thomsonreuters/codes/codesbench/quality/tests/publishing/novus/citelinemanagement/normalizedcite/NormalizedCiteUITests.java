package com.thomsonreuters.codes.codesbench.quality.tests.publishing.novus.citelinemanagement.normalizedcite;

import com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.publishing.novus.CitelineManagementContextMenuElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.publishing.novus.citelinemanagement.nomalizedcite.AddNormalizedCitePageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.publishing.novus.citelinemanagement.nomalizedcite.NormalizedCiteReferencesPageElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.CITELINE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.EDGE;
import com.thomsonreuters.codes.codesbench.quality.utilities.clipboard.ClipboardUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.citelinemanagement.CitelineManagementDataMocking;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

public class NormalizedCiteUITests extends TestService
{
    Connection connection;
    private static final String contentSetName = "RB.UKLG";

    @AfterEach
    public void closeConnection()
    {
        BaseDatabaseUtils.disconnect(connection);
    }

    /**
     * STORY: Jets - 21847 <br>
     * SUMMARY: This test verifies that the Normalized Cite page and its elements appear as expected, and are
     *          in the correct order if applicable  <br>
     * USER: CITELINE <br>
     */
    @Test
    @EDGE
    @CITELINE
    @LOG
    public void normalizedCitePageNavigationTest()
    {
        String citationPrefix1 = "Test001";
        String citationPrefix2 = "Test002";
        String citationPrefix3 = "Test003";
        String condensedPrefix = "navTest";

        connection = BaseDatabaseUtils.connectToDatabaseUAT();
        CitelineManagementDataMocking.insertNormalizedCite(connection, 0, citationPrefix1, condensedPrefix, Integer.parseInt(ContentSets.UK_LEGISLATIVE.getCode()), contentSetName);
        CitelineManagementDataMocking.insertNormalizedCite(connection, 1, citationPrefix2, condensedPrefix, Integer.parseInt(ContentSets.UK_LEGISLATIVE.getCode()), contentSetName);
        CitelineManagementDataMocking.insertNormalizedCite(connection, 2, citationPrefix3, condensedPrefix, Integer.parseInt(ContentSets.UK_LEGISLATIVE.getCode()), contentSetName);

        try
        {
            homePage().goToHomePage();
            loginPage().logIn();
            homePage().setMyContentSet(ContentSets.UK_LEGISLATIVE.getName());
            boolean citelineManagementPageLoaded = publishingMenu().goToPublishingNovusCitelineManagement();
            Assertions.assertTrue(citelineManagementPageLoaded, "Cite Line Management Page Appeared");
            citeLineReferencesPage().goToNormalizedCiteTab();
            boolean isNewCiteButtonDisplayed = normalizedCitePage().isElementDisplayed(AddNormalizedCitePageElements.ADD_NEW_NORMALIZED_CITE_BUTTON);

            //verify all grid headers are there and in order
            boolean isCitationPrefixInOrder = normalizedCitePage().isElementDisplayed(NormalizedCiteReferencesPageElements.CITATION_PREFIX_COLUMN);
            Assertions.assertTrue(isCitationPrefixInOrder, "The citation prefix header is on the page");
            String isCondensedPrefixInOrder = normalizedCitePage().getColumnHeaderNextSibling(NormalizedCiteReferencesPageElements.citationPrefixHeader);
            String isModifiedByInOrder = normalizedCitePage().getColumnHeaderNextSibling(NormalizedCiteReferencesPageElements.condensedPrefixHeader);
            String isModifiedDateInOrder = normalizedCitePage().getColumnHeaderNextSibling(NormalizedCiteReferencesPageElements.modifiedByHeader);
            String isCommentsInOrder = normalizedCitePage().getColumnHeaderNextSibling(NormalizedCiteReferencesPageElements.modifiedDateHeader);

            //filter for citations and grab the row ids
            normalizedCitePage().openFilterMenuForCondensedPrefix();
            gridHeaderFiltersPage().setFilterValue(condensedPrefix);
            String rowID1 = normalizedCitePage().getRowID(citationPrefix1);
            String rowID2 = normalizedCitePage().getRowID(citationPrefix2);
            String rowID3 = normalizedCitePage().getRowID(citationPrefix3);

            //verify Actions edit and delete appear
            boolean editAppears1 = normalizedCitePage().isElementDisplayed(String.format(NormalizedCiteReferencesPageElements.EDIT, rowID1));
            boolean deleteAppears1 = normalizedCitePage().isElementDisplayed(String.format(NormalizedCiteReferencesPageElements.DELETE, rowID1));
            boolean editAppears2 = normalizedCitePage().isElementDisplayed(String.format(NormalizedCiteReferencesPageElements.EDIT, rowID2));
            boolean deleteAppears2 = normalizedCitePage().isElementDisplayed(String.format(NormalizedCiteReferencesPageElements.DELETE, rowID2));
            boolean editAppears3 = normalizedCitePage().isElementDisplayed(String.format(NormalizedCiteReferencesPageElements.EDIT, rowID3));
            boolean deleteAppears3 = normalizedCitePage().isElementDisplayed(String.format(NormalizedCiteReferencesPageElements.DELETE, rowID3));

            //right click and verify context Menu
            normalizedCitePage().rightClickElementByRowId(rowID1);
            boolean copyMenuOptionAppears = normalizedCitePage().isElementDisplayed(CitelineManagementContextMenuElements.COPY);
            boolean copyWithHeadersMenuOptionAppears = normalizedCitePage().isElementDisplayed(CitelineManagementContextMenuElements.COPY_WITH_HEADERS);
            boolean pasteMenuOptionAppears = normalizedCitePage().isElementDisplayed(CitelineManagementContextMenuElements.PASTE);
            boolean pasteMenuOptionAppearsDisabled = normalizedCitePage().isElementDisabled(CitelineManagementContextMenuElements.PASTE + "/..");
            boolean exportMenuOptionAppears = normalizedCitePage().isElementDisplayed(CitelineManagementContextMenuElements.EXPORT);

            //verify that contents of citation prefix are in the proper descending order
            boolean citationPrefix1InCorrectOrder = normalizedCitePage().verifySelectedNormalizedCiteCitationPrefixByRowIndex(citationPrefix1, "2");
            boolean citationPrefix2InCorrectOrder = normalizedCitePage().verifySelectedNormalizedCiteCitationPrefixByRowIndex(citationPrefix2, "1");
            boolean citationPrefix3InCorrectOrder = normalizedCitePage().verifySelectedNormalizedCiteCitationPrefixByRowIndex(citationPrefix3, "0");

            //Verifying context menu copy function works
            normalizedCitePage().rightClickElementByRowId(rowID1);
            normalizedCitePage().click(CitelineManagementContextMenuElements.COPY);
            String copiedText = ClipboardUtils.getSystemClipboard();
            boolean copyFunctionalityWorks = copiedText.equals(citationPrefix1);

            //Verifying the context menu copy with headers function works
            normalizedCitePage().rightClickElementByRowId(rowID1);
            normalizedCitePage().click(CitelineManagementContextMenuElements.COPY_WITH_HEADERS);
            String copiedTextWithHeaders = ClipboardUtils.getSystemClipboard();
            boolean copyWithHeadersFunctionalityWorks = copiedTextWithHeaders.equals("Citation Prefix\n" + citationPrefix1);

            Assertions.assertAll
            (
                () -> Assertions.assertTrue(isNewCiteButtonDisplayed, "The Add New Normalized Cite button is expected to be on the page"),

                () -> Assertions.assertEquals(isCondensedPrefixInOrder, "condensedPrefix", "Condensed Prefix should be after Citation Prefix"),
                () -> Assertions.assertEquals(isModifiedByInOrder, "modifiedBy", "Modified By should be after Condensed Prefix"),
                () -> Assertions.assertEquals(isModifiedDateInOrder, "modifiedDate", "Modified Date should be after Modified By"),
                () -> Assertions.assertEquals(isCommentsInOrder,"comments", "Comments should be after Modified Date"),

                () -> Assertions.assertTrue(editAppears1, "The Edit button Appears in the grid for citationPrefix1"),
                () -> Assertions.assertTrue(deleteAppears1, "The Delete button Appears in the grid for citationPrefix1"),
                () -> Assertions.assertTrue(editAppears2, "The Edit button Appears in the grid for ct1 citationPrefix2"),
                () -> Assertions.assertTrue(deleteAppears2, "The Delete button Appears in the grid for citationPrefix2"),
                () -> Assertions.assertTrue(editAppears3, "The Edit button Appears in the grid for citationPrefix3"),
                () -> Assertions.assertTrue(deleteAppears3, "The Delete button Appears in the grid for citationPrefix3"),

                () -> Assertions.assertTrue(copyMenuOptionAppears, "Copy Context menu appeared"),
                () -> Assertions.assertTrue(copyWithHeadersMenuOptionAppears, "Copy with header Context menu appeared"),
                () -> Assertions.assertTrue(pasteMenuOptionAppears, "Paste Context menu appeared"),
                () -> Assertions.assertTrue(pasteMenuOptionAppearsDisabled, "Paste Context menu is disabled"),
                () -> Assertions.assertTrue(exportMenuOptionAppears, "Export Context menu appeared"),

                () -> Assertions.assertTrue(citationPrefix1InCorrectOrder, "citationPrefix1 is in the correct order"),
                () -> Assertions.assertTrue(citationPrefix2InCorrectOrder, "citationPrefix2 should be after citationPrefix1"),
                () -> Assertions.assertTrue(citationPrefix3InCorrectOrder, "citationPrefix1 should be after citationPrefix2"),

                () -> Assertions.assertTrue(copyFunctionalityWorks, "the copied text should be: \"001\""),
                () -> Assertions.assertTrue(copyWithHeadersFunctionalityWorks, "the copied with headers text should contain: \"001\" and \"CT1\"")
            );
        }
        finally
        {
            CitelineManagementDataMocking.deleteNormalizedCite(connection, citationPrefix1, contentSetName);
            CitelineManagementDataMocking.deleteNormalizedCite(connection, citationPrefix2, contentSetName);
            CitelineManagementDataMocking.deleteNormalizedCite(connection, citationPrefix3, contentSetName);
        }
    }
}
