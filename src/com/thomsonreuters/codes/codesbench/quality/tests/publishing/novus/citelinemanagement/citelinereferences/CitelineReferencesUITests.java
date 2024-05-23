package com.thomsonreuters.codes.codesbench.quality.tests.publishing.novus.citelinemanagement.citelinereferences;

import com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.publishing.novus.CitelineManagementContextMenuElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.publishing.novus.citelinemanagement.citelinereferences.CiteLineReferencesPageElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.EDGE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.CITELINE;
import com.thomsonreuters.codes.codesbench.quality.utilities.clipboard.ClipboardUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.citelinemanagement.CitelineManagementDataMocking;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

public class CitelineReferencesUITests extends TestService
{
    Connection uatConnection;
    String contentSetUKLegislative = ContentSets.UK_LEGISLATIVE.getName();
    int contentSetUKLegislativeCode = Integer.parseInt(ContentSets.UK_LEGISLATIVE.getCode());
    String contentSetUKLegislativeName = "RB.UKLG";

    @AfterEach
    public void closeConnection()
    {
        BaseDatabaseUtils.disconnect(uatConnection);
    }

    /**
     * STORY: Jets - 21840/21843/21842/21844 <br>
     * SUMMARY: This test verifies the Citeline Reference grid contains all columns and is sorted ascending by CT1 value <br>
     * USER: CITELINE <br>
     */
    @Test
    @EDGE
    @CITELINE
    @LOG
    public void citeLineReferencesPageDisplayTest()
    {
        String ct1_1 = "001";
        String ct1_2 = "002";
        String ct1_3 = "003";
        String testValue = "navTest";

        uatConnection = BaseDatabaseUtils.connectToDatabaseUAT();
        CitelineManagementDataMocking.insertCiteline(uatConnection, contentSetUKLegislativeCode, ct1_1, testValue, testValue, testValue, contentSetUKLegislativeName);
        CitelineManagementDataMocking.insertCiteline(uatConnection, contentSetUKLegislativeCode, ct1_2, testValue, testValue, testValue, contentSetUKLegislativeName);
        CitelineManagementDataMocking.insertCiteline(uatConnection, contentSetUKLegislativeCode, ct1_3, testValue, testValue, testValue, contentSetUKLegislativeName);

        try
        {
            homePage().goToHomePage();
            loginPage().logIn();
            homePage().setMyContentSet(contentSetUKLegislative);

            boolean citelineManagementPageLoaded = publishingMenu().goToPublishingNovusCitelineManagement();
            Assertions.assertTrue(citelineManagementPageLoaded, "Cite Line Management Page Appeared");
            boolean isNewReferencesButtonDisplayed = citeLineReferencesPage().isElementDisplayed(CiteLineReferencesPageElements.ADD_NEW_CITELINE_REFERENCES);

            //verify the grid columns are in order
            boolean isCT1InOrder = citeLineReferencesPage().isElementDisplayed(CiteLineReferencesPageElements.CT1_COLUMN_HEADER);
            Assertions.assertTrue(isCT1InOrder, "The ct1 header is on the page");

            //verify all grid headers are there and in order
            String isOriginalFirstLineCiteInOrder = citeLineReferencesPage().getColumnHeaderNextSibling(CiteLineReferencesPageElements.ct1Header);
            String isFirstLineCiteInOrder = citeLineReferencesPage().getColumnHeaderNextSibling(CiteLineReferencesPageElements.originalFirstLineCiteHeader);
            String isSecondLineCitePreInOrder = citeLineReferencesPage().getColumnHeaderNextSibling(CiteLineReferencesPageElements.firstLineCiteHeader);
            String isSecondLineCiteAppInOrder = citeLineReferencesPage().getColumnHeaderNextSibling(CiteLineReferencesPageElements.secondLineCitePreHeader);
            String isExpandedCitePreInOrder = citeLineReferencesPage().getColumnHeaderNextSibling(CiteLineReferencesPageElements.secondLineCiteAppHeader);
            String isExpandedCiteAppInOrder = citeLineReferencesPage().getColumnHeaderNextSibling(CiteLineReferencesPageElements.expandedCitePre);
            String isFormerCiteInOrder = citeLineReferencesPage().getColumnHeaderNextSibling(CiteLineReferencesPageElements.expandedCiteAppHeader);

            citeLineReferencesPage().openFilterMenuForOriginalFirstLineCite();
            gridHeaderFiltersPage().setMultipleFilterValues(testValue);
            String rowID1 = citeLineReferencesPage().getRowID(ct1_1);
            String rowID2 = citeLineReferencesPage().getRowID(ct1_2);
            String rowID3 = citeLineReferencesPage().getRowID(ct1_3);

            //verify Actions edit and delete appear
            boolean editAppears1 = citeLineReferencesPage().isElementDisplayed(String.format(CiteLineReferencesPageElements.EDIT, rowID1));
            boolean deleteAppears1 = citeLineReferencesPage().isElementDisplayed(String.format(CiteLineReferencesPageElements.DELETE, rowID1));
            boolean editAppears2 = citeLineReferencesPage().isElementDisplayed(String.format(CiteLineReferencesPageElements.EDIT, rowID2));
            boolean deleteAppears2 = citeLineReferencesPage().isElementDisplayed(String.format(CiteLineReferencesPageElements.DELETE, rowID2));
            boolean editAppears3 = citeLineReferencesPage().isElementDisplayed(String.format(CiteLineReferencesPageElements.EDIT, rowID3));
            boolean deleteAppears3 = citeLineReferencesPage().isElementDisplayed(String.format(CiteLineReferencesPageElements.DELETE, rowID3));

            //verify that contents of ct1 are in the proper ascending order
            boolean ct1InCorrectOrder = citeLineReferencesPage().verifySelectedCt1("0", ct1_1);
            boolean ct12InCorrectOrder = citeLineReferencesPage().verifySelectedCt1("1", ct1_2);
            boolean ct13InCorrectOrder = citeLineReferencesPage().verifySelectedCt1("2", ct1_3);

            Assertions.assertAll
                    (
                            () -> Assertions.assertTrue(isNewReferencesButtonDisplayed, "The Add New Cite References button is expected to be on the page"),

                            () -> Assertions.assertEquals(isOriginalFirstLineCiteInOrder, "originalFirstLineCite", "Original First Line should be after Ct1"),
                            () -> Assertions.assertEquals(isFirstLineCiteInOrder, "firstLineCite", "First Line Cite should be after original First Line Cite"),
                            () -> Assertions.assertEquals(isSecondLineCitePreInOrder, "secondLineCitePre", "Second Line Cite Pre should be after first line cite"),
                            () -> Assertions.assertEquals(isSecondLineCiteAppInOrder, "secondLineCiteApp", "Second Line Cite App should be after Second Line Cite Pre"),
                            () -> Assertions.assertEquals(isExpandedCitePreInOrder,"expandedCitePre", "Expanded Cite Pre should be after Second Line App"),
                            () -> Assertions.assertEquals(isExpandedCiteAppInOrder,"expandedCiteApp", "Expanded Cite App Should be after Expanded Cite Pre"),
                            () -> Assertions.assertEquals(isFormerCiteInOrder, "formerCite", "Former Cite should be after Expanded Cite App"),

                            () -> Assertions.assertTrue(editAppears1, "The Edit button Appears in the grid for ct1 num 1"),
                            () -> Assertions.assertTrue(deleteAppears1, "The Delete button Appears in the grid for ct1 num 1"),
                            () -> Assertions.assertTrue(editAppears2, "The Edit button Appears in the grid for ct1 num 2"),
                            () -> Assertions.assertTrue(deleteAppears2, "The Delete button Appears in the grid for ct1 num 2"),
                            () -> Assertions.assertTrue(editAppears3, "The Edit button Appears in the grid for ct1 num 3"),
                            () -> Assertions.assertTrue(deleteAppears3, "The Delete button Appears in the grid for ct1 num 3"),

                            () -> Assertions.assertTrue(ct1InCorrectOrder, "ct1 is not in the correct order"),
                            () -> Assertions.assertTrue(ct12InCorrectOrder, "the second ct1 should be after ct1 #1"),
                            () -> Assertions.assertTrue(ct13InCorrectOrder, "the third ct1 should be after ct1 #2")
                    );
        }
        finally
        {
            CitelineManagementDataMocking.deleteFromPubStableTable(uatConnection, ct1_1);
            CitelineManagementDataMocking.deleteFromPubStableTable(uatConnection, ct1_2);
            CitelineManagementDataMocking.deleteFromPubStableTable(uatConnection, ct1_3);
        }
    }

    /**
     * STORY: Jets - 21840/21843/21842/21844 <br>
     * SUMMARY: This test verifies all the context menu items are displayed on a right click <br>
     * USER: CITELINE <br>
     */
    @Test
    @EDGE
    @CITELINE
    @LOG
    public void citeLineReferencesContextMenuDisplayTest()
    {
        String ct1 = "001";
        String testValue = "navTest";

        uatConnection = BaseDatabaseUtils.connectToDatabaseUAT();
        CitelineManagementDataMocking.insertCiteline(uatConnection, contentSetUKLegislativeCode, ct1, testValue, testValue, testValue, contentSetUKLegislativeName);

        try
        {
            homePage().goToHomePage();
            loginPage().logIn();
            homePage().setMyContentSet(contentSetUKLegislative);

            boolean citelineManagementPageLoaded = publishingMenu().goToPublishingNovusCitelineManagement();
            Assertions.assertTrue(citelineManagementPageLoaded, "Cite Line Management Page Appeared");

            citeLineReferencesPage().openFilterMenuForOriginalFirstLineCite();
            gridHeaderFiltersPage().setMultipleFilterValues(testValue);

            //right click and verify context Menu
            citeLineReferencesPage().rightClickElementByCt1(ct1);
            boolean copyMenuOptionAppears = citeLineReferencesPage().isElementDisplayed(CitelineManagementContextMenuElements.COPY);
            boolean copyWithHeadersMenuOptionAppears = citeLineReferencesPage().isElementDisplayed(CitelineManagementContextMenuElements.COPY_WITH_HEADERS);
            boolean pasteMenuOptionAppears = citeLineReferencesPage().isElementDisplayed(CitelineManagementContextMenuElements.PASTE);
            boolean pasteMenuOptionAppearsDisabled = !citeLineReferencesPage().isElementDisabled(CitelineManagementContextMenuElements.PASTE);
            boolean exportMenuOptionAppears = citeLineReferencesPage().isElementDisplayed(CitelineManagementContextMenuElements.EXPORT);

            Assertions.assertAll
                    (
                            () -> Assertions.assertTrue(copyMenuOptionAppears, "Copy Context menu appeared"),
                            () -> Assertions.assertTrue(copyWithHeadersMenuOptionAppears, "Copy with header Context menu appeared"),
                            () -> Assertions.assertTrue(pasteMenuOptionAppears, "Paste Context menu appeared"),
                            () -> Assertions.assertTrue(pasteMenuOptionAppearsDisabled, "Paste Context menu is disabled"),
                            () -> Assertions.assertTrue(exportMenuOptionAppears, "Export Context menu appeared")
                    );
        }
        finally
        {
            CitelineManagementDataMocking.deleteFromPubStableTable(uatConnection, ct1);
        }
    }

    /**
     * STORY: Jets - 21840/21843/21842/21844 <br>
     * SUMMARY: This test verifies the Citeline Reference copy context menu function copies the correct CT1 <br>
     * USER: CITELINE <br>
     */
    @Test
    @EDGE
    @CITELINE
    @LOG
    public void citeLineReferencesCopyTest()
    {
        String ct1 = "001";
        String testValue = "navTest";

        uatConnection = BaseDatabaseUtils.connectToDatabaseUAT();
        CitelineManagementDataMocking.insertCiteline(uatConnection, contentSetUKLegislativeCode, ct1, testValue, testValue, testValue, contentSetUKLegislativeName);

        try
        {
            homePage().goToHomePage();
            loginPage().logIn();
            homePage().setMyContentSet(contentSetUKLegislative);

            boolean citelineManagementPageLoaded = publishingMenu().goToPublishingNovusCitelineManagement();
            Assertions.assertTrue(citelineManagementPageLoaded, "Cite Line Management Page Appeared");

            citeLineReferencesPage().openFilterMenuForOriginalFirstLineCite();
            gridHeaderFiltersPage().setMultipleFilterValues(testValue);

            //Verifying context menu copy function works
            citeLineReferencesPage().rightClickElementByCt1(ct1);
            citeLineManagementContextMenu().copy();
            String copiedText = ClipboardUtils.getSystemClipboard();
            boolean copyFunctionalityWorks = ct1.equals(copiedText);
            Assertions.assertTrue(copyFunctionalityWorks, String.format("the copied text was: %s but should be: %s", copiedText, ct1));
        }
        finally
        {
            CitelineManagementDataMocking.deleteFromPubStableTable(uatConnection, ct1);
        }
    }

    /**
     * STORY: Jets - 21840/21843/21842/21844 <br>
     * SUMMARY: This test verifies the Citeline Reference copy with headers context menu function copies the correct CT1 <br>
     * USER: CITELINE <br>
     */
    @Test
    @EDGE
    @CITELINE
    @LOG
    public void citeLineReferencesCopyWithHeadersTest()
    {
        String ct1 = "001";
        String testValue = "navTest";

        uatConnection = BaseDatabaseUtils.connectToDatabaseUAT();
        CitelineManagementDataMocking.insertCiteline(uatConnection, contentSetUKLegislativeCode, ct1, testValue, testValue, testValue, contentSetUKLegislativeName);

        try
        {
            homePage().goToHomePage();
            loginPage().logIn();
            homePage().setMyContentSet(contentSetUKLegislative);

            boolean citelineManagementPageLoaded = publishingMenu().goToPublishingNovusCitelineManagement();
            Assertions.assertTrue(citelineManagementPageLoaded, "Cite Line Management Page Appeared");

            citeLineReferencesPage().openFilterMenuForOriginalFirstLineCite();
            gridHeaderFiltersPage().setMultipleFilterValues(testValue);

            //Verifying the context menu copy with headers function works
            citeLineReferencesPage().rightClickElementByCt1(ct1);
            citeLineManagementContextMenu().copyWithHeaders();
            String copiedTextWithHeaders = ClipboardUtils.getSystemClipboard();
            boolean copyWithHeadersFunctionalityWorks = String.format("CT1\n%s", ct1).equals(copiedTextWithHeaders);
            Assertions.assertTrue(copyWithHeadersFunctionalityWorks, String.format("the copied with headers text was '%s' but should be 'CT1\n%s'", copiedTextWithHeaders, ct1));
        }
        finally
        {
            CitelineManagementDataMocking.deleteFromPubStableTable(uatConnection, ct1);
        }
    }
}
