package com.thomsonreuters.codes.codesbench.quality.tests.source.set2;

import com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.popups.DocumentLockedPageElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.source.SourceDatabaseUtils;
import org.junit.jupiter.api.*;

import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;

import java.sql.Connection;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.source.reports.LockReportPageElements.lockReportPaginationDropDown;

public class LockReportTests extends TestService
{
    String contentSet = "Iowa (Development)";
    String dropDownValue = "50";

    /**
     * STORY: N/A <br>
     * SUMMARY: Tests that you can create a lock as a prod user and transfer it as a legal user
     *          The next two tests go together and must run in succession
     *          HALCYONST-1108  <br>
     * USER: LEGAL<br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void transferAndUnlockDocLockedByProdUserTest()
    {
        String userName = user().getUsername().toUpperCase();
        String prodName = "TLE TCBE-BOT";
        String docName = "2018 1RG HF 633";

        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterValidation("None");
        sourceNavigateFiltersAndSortsPage().setFilterDeleted("Not Deleted");
        sourceNavigateFiltersAndSortsPage().setFilterContentSet(contentSet);
        sourceNavigateFiltersAndSortsPage().setFilterYear("2018");
        sourceNavigateFiltersAndSortsPage().setFilterSession("1RG");
        sourceNavigateFiltersAndSortsPage().setFilterRenditionStatus("APV");
        sourceNavigateFiltersAndSortsPage().setFilterDocType("HF");
        sourceNavigateFiltersAndSortsPage().setFilterDocNumber("633");
        sourceNavigateFooterToolsPage().refreshTheGrid();
        //If the rendition is already locked unlock it
        sourceNavigateGridPage().unlockFirstRendition();

        String prodUser = prodLegalUser().getUsername();
        String renditionUUID = sourceNavigateGridPage().getUUID();
        Connection connnection = BaseDatabaseUtils.connectToDatabaseUAT();
        SourceDatabaseUtils.createLock(connnection, renditionUUID, prodUser);

        sourceNavigateFooterToolsPage().refreshTheGrid();
        boolean firstRenditionLocked = sourceNavigateGridPage().isFirstRenditionLocked();
        Assertions.assertTrue(firstRenditionLocked, "The first rendition was locked through the database");

        sourceMenu().goToSourceC2012ReportsLockReport();

        lockReportPage().setContentSetFilter(contentSet);
        lockReportPage().refreshLockReportGrid();

        //Check to see if the transfer lock is enabled
        boolean transferLockEnabled = lockReportPage().transferLockDocument(contentSet, docName, prodName);
        Assertions.assertTrue(transferLockEnabled, "Transfer lock is enabled");
        lockReportPage().selectDropdownOptionByValue(lockReportPaginationDropDown, dropDownValue);

        //Check that the document was transferred to  the legal user successfully
        boolean documentTransferred = (!lockReportPage().lockedDocumentExists(contentSet, docName, prodName) &&
                (lockReportPage().lockedDocumentExists(contentSet, docName, userName)));
        Assertions.assertTrue(documentTransferred, "Locked document has been transferred");

        //Check that the option to unlock the document is available
        boolean unlockEnabled = lockReportPage().unlockDocument(contentSet, docName, userName);
        Assertions.assertTrue(unlockEnabled, "Unlock document is enabled");
    }

    /**
     * STORY: N/A <br>
     * SUMMARY: Tests that you can create a lock as a prod user and force unlock it as a legal user
     *          This and the previous test go together and must run in succession
     *          HALCYONST-1110 <br>
     * USER: PROD <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void forceUnlockDocLockedByProdUserTest()
    {
        String prodName = "TLE TCBE-BOT";
        String docName = "2018 1RG HF 633";

        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterValidation("None");
        sourceNavigateFiltersAndSortsPage().setFilterDeleted("Not Deleted");
        sourceNavigateFiltersAndSortsPage().setFilterContentSet(contentSet);
        sourceNavigateFiltersAndSortsPage().setFilterYear("2018");
        sourceNavigateFiltersAndSortsPage().setFilterSession("1RG");
        sourceNavigateFiltersAndSortsPage().setFilterRenditionStatus("APV");
        sourceNavigateFiltersAndSortsPage().setFilterDocType("HF");
        sourceNavigateFiltersAndSortsPage().setFilterDocNumber("633");
        sourceNavigateFooterToolsPage().refreshTheGrid();
        //If the rendition is already locked unlock it
        sourceNavigateGridPage().unlockFirstRendition();

        String prodUser = prodLegalUser().getUsername();
        String renditionUUID = sourceNavigateGridPage().getUUID();
        Connection connnection = BaseDatabaseUtils.connectToDatabaseUAT();
        SourceDatabaseUtils.createLock(connnection, renditionUUID, prodUser);

        sourceNavigateFooterToolsPage().refreshTheGrid();
        boolean firstRenditionLocked = sourceNavigateGridPage().isFirstRenditionLocked();
        Assertions.assertTrue(firstRenditionLocked, "The first rendition was locked through the database");

        sourceMenu().goToSourceC2012ReportsLockReport();

        lockReportPage().setContentSetFilter(contentSet);
        lockReportPage().refreshLockReportGrid();

        //Check if the option for Force Unlock is enabled
        boolean forceUnlockEnabled = lockReportPage().forceUnlockDocument(contentSet, docName, prodName);
        boolean forceUnlockAlertAppears = lockReportPage().checkAlertTextMatchesGivenText("Unlocking this document will cause all changes made by the listed user to be lost.  Proceed with unlock?");
        lockReportPage().waitForGridRefresh();
        lockReportPage().waitForPageLoaded();
        //Make sure that the document is no longer in the locked report
        boolean lockedDocumentNoLongerAppears = !lockReportPage().lockedDocumentExists(contentSet, docName, prodName);

        //Unlocks the rendition if something in the test went wrong
        sourceNavigateGridPage().unlockRenditionWithUuid(renditionUUID);

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(forceUnlockEnabled, "Force Unlock is enabled"),
            () -> Assertions.assertTrue(forceUnlockAlertAppears, "Force Unlock alert appeared"),
            () -> Assertions.assertTrue(lockedDocumentNoLongerAppears, "Locked document is not in the grid.")
        );
    }

    /**
     * STORY: HALCYONST-1109 <br>
     * SUMMARY: Tests that you can create a lock as a legal user and unlock it yourself <br>
     * USER: LEGAL <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void unlockDocLockedByTestUserTest()
    {
        String userName = user().getFirstname() + " " + user().getLastname();
        String docName = "2017 1RG H 1";

        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        //Filter for the document
        sourceNavigateFiltersAndSortsPage().setFilterContentSet(contentSet);
        sourceNavigateFiltersAndSortsPage().setFilterYear("2017");
        sourceNavigateFiltersAndSortsPage().setFilterRenditionStatus("DRA");
        sourceNavigateFiltersAndSortsPage().setFilterDocNumber("1");
        sourceNavigateFooterToolsPage().refreshTheGrid();
        //Unlock the rendition if it is already locked
        sourceNavigateGridPage().unlockFirstRendition();

        //Check that the first rendition is not locked
        boolean renditionIsLocked = sourceNavigateGridPage().isFirstRenditionLocked();
        Assertions.assertFalse(renditionIsLocked, "Rendition is locked and shouldn't be");

        //Create a lock on the first rendition
        String legalUser = legalUser().getUsername();
        String renditionUUID = sourceNavigateGridPage().getUUID();
        Connection connnection = BaseDatabaseUtils.connectToDatabaseUAT();
        SourceDatabaseUtils.createLock(connnection, renditionUUID, legalUser);
        sourceNavigateFooterToolsPage().refreshTheGrid();
        boolean lockAdded = sourceNavigateGridPage().isFirstRenditionLocked();
        Assertions.assertTrue(lockAdded, "First rendition isn't locked and should be");

        //Filter and unlock the locked rendition
        sourceMenu().goToSourceC2012ReportsLockReport();
        lockReportPage().setContentSetFilter(contentSet);
        boolean unlockEnabled = lockReportPage().unlockDocument(contentSet, docName, userName);
        //Make sure that the locked document is no longer in the report page
        boolean lockedDocumentNoLongerAppears = !lockReportPage().lockedDocumentExists(contentSet, docName, userName);

        //Go back and check that the lock is no longer on the document
        lockReportPage().closeCurrentWindowIgnoreDialogue();
        sourcePage().switchToSourceNavigatePage();
        sourceNavigateFooterToolsPage().refreshTheGrid();

        //Check that the first rendition isn't locked anymore and if it is unlock it
        boolean lockDisappeared = sourceNavigateGridPage().isFirstRenditionLocked();
        sourceNavigateGridPage().unlockFirstRendition();

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(unlockEnabled, "Unlock is not enabled"),
            () -> Assertions.assertTrue(lockedDocumentNoLongerAppears, "Locked document does not appear in the lock report"),
            () -> Assertions.assertFalse(lockDisappeared, "Lock is not on the first rendition")
        );
    }

    /**
     * STORY: N/A <br>
     * SUMMARY: Tests that you can view content of a locked node and that the node's content will be read-only
     *          HALCYONST-1109  <br>
     * USER: LEGAL <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    @Disabled
    public void viewContentOfLockedDocTest()
    {
        String docName = "2017 1RG H 1";
        String userName = user().getFirstname() + " " + user().getLastname();

        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        //Filter for the rendition
        sourceNavigateFiltersAndSortsPage().setFilterContentSet(contentSet);
        sourceNavigateFiltersAndSortsPage().setFilterYear("2017");
        sourceNavigateFiltersAndSortsPage().setFilterRenditionStatus("DRA");
        sourceNavigateFiltersAndSortsPage().setFilterDocNumber("1");
        sourceNavigateFooterToolsPage().refreshTheGrid();
        //Unlock the rendition if it is already unlocked
        sourceNavigateGridPage().unlockFirstRendition();

        //Check that the first rendition is not already locked
        boolean renditionIsAlreadyLocked = sourceNavigateGridPage().isFirstRenditionLocked();
        Assertions.assertFalse(renditionIsAlreadyLocked, "Rendition is already locked");

        //Create a lock on the first rendition
        String legalUser = legalUser().getUsername();
        String renditionUUID = sourceNavigateGridPage().getUUID();
        Connection connnection = BaseDatabaseUtils.connectToDatabaseUAT();
        SourceDatabaseUtils.createLock(connnection, renditionUUID, legalUser);
        sourceNavigateFooterToolsPage().refreshTheGrid();

        //Open view content on the locked rendition
        sourceMenu().goToSourceC2012ReportsLockReport();
        lockReportPage().setContentSetFilter(contentSet);
        lockReportPage().viewContent(contentSet, docName, userName);

        editorPage().switchToEditor();
        boolean editorIsReadOnly = editorPage().checkEditorIsReadOnly();

        //Unlock the document
        editorPage().closeDocumentWithNoChanges();
        lockReportPage().switchToLockReportPage();
        lockReportPage().forceUnlockDocument(contentSet, docName, userName);

        Assertions.assertTrue(editorIsReadOnly, "Editor is in read only mode");
    }

    /**
     * STORY: N/A <br>
     * SUMMARY: Tests that you can create a lock in the section tab and not be able to edit the doc in the rendition tab
     *          HALCYONST-1109 <br>
     * USER: LEGAL<br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void lockedBySelfErrorTest()
    {
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        //Filter for the document
        sourceNavigateFiltersAndSortsPage().setFilterContentSet(contentSet);
        sourceNavigateFiltersAndSortsPage().setFilterYear("2017");
        sourceNavigateFiltersAndSortsPage().setFilterDocType("SF");
        sourceNavigateFiltersAndSortsPage().setFilterDocNumber("408");
        sourceNavigateFooterToolsPage().refreshTheGrid();
        //Unlock the rendition if it already locked
        sourceNavigateGridPage().unlockFirstRendition();

        //Create a lock on the first rendition through the section tab
        sourceNavigateGridPage().clickFirstRendition();
        sourceNavigateTabsPage().goToSectionTab();
        sourceNavigateGridPage().rightClickFirstSection();
        sectionContextMenu().openSectionEditor();
        editorPage().switchToEditor();
        editorPage().closeCurrentWindowIgnoreDialogue();

        //Try to edit the rendition with a lock
        sourcePage().switchToSectionNavigatePage();
        sourceNavigateTabsPage().goToRenditionTab();
        sourceNavigateGridPage().rightClickFirstRendition();
        renditionContextMenu().editRenditionOldWithLockedDoc();

        //Make sure that the document locked page shows up and contains three buttons
        boolean docLockedWindowAppeared = documentLockedPage().switchToDocumentLockedPage();
        Assertions.assertTrue(docLockedWindowAppeared, "The document locked window did appear");
        boolean buttonsPresent = documentLockedPage().doesElementExist(DocumentLockedPageElements.LOCKED_RENDITION_ALERT_VIEW_DOC_BUTTON)
                && documentLockedPage().doesElementExist(DocumentLockedPageElements.LOCKED_RENDITION_ALERT_VIEW_LOCK_BUTTON)
                && documentLockedPage().doesElementExist(DocumentLockedPageElements.LOCKED_RENDITION_ALERT_CANCEL_BUTTON);
        Assertions.assertTrue(buttonsPresent, "The three buttons of the document locked page did appear");
        documentLockedPage().breakOutOfFrame();
        documentLockedPage().closeCurrentWindowIgnoreDialogue();

        //Go back to the reports page and unlock the document
        sourcePage().switchToSourceNavigatePage();
        sourceMenu().goToSourceC2012ReportsLockReport();
        lockReportPage().setContentSetFilter(contentSet);
        lockReportPage().unlockDocument(contentSet, "2017 1RG SF 408", user().getFirstname() + " " + user().getLastname());
    }
}
