package com.thomsonreuters.codes.codesbench.quality.tests.source.set3;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SectionLockingTests extends TestService
{
    /**
     * STORY: N/A <br>
     * SUMMARY: checks to make sure sections lock correctly<br>
     * USER: LEGAL <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void sectionLockingTest()
    {
        int firstLine = 1;
        int lastLine = 3;

        String contentSet = "Iowa (Development)";
        String docType = "HF";
        String contentType = "BILL";

        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterContentSet(contentSet);
        sourceNavigateFiltersAndSortsPage().setFilterContentType(contentType);
        sourceNavigateFiltersAndSortsPage().setFilterDocType(docType);
        sourceNavigateFooterToolsPage().refreshTheGrid();

        //If rendition is already locked, unlock the rendition
        sourceNavigateGridPage().unlockFirstRendition();

        sourceNavigateGridPage().clickFirstRendition();
        sourceNavigateTabsPage().goToSectionTab();

        sourceNavigateFooterToolsPage().clearSection();
        sourceNavigateGridPage().clickFirstXRenditions(3);
        sourceNavigateGridPage().onlyRightClickFirstItem();
        renditionContextMenu().editSection();
        editorPage().closeCurrentWindowIgnoreDialogue();

        sourcePage().switchToSectionNavigatePage();
        sourceNavigateFooterToolsPage().refreshTheGrid();
        boolean locked = true;
        for(int i = firstLine; i <= lastLine; i++)
        {
            locked = locked && sourceNavigateGridPage().isRenditionLocked(i);
        }
        boolean allSectionsLocked = locked;

        sourceNavigateFooterToolsPage().clearSection();
        sourceNavigateGridPage().clickFirstXRenditions(2);
        sourceNavigateGridPage().onlyRightClickFirstItem();
        renditionContextMenu().editLockedSection();

        boolean documentLockedWindowAppeared = documentLockedPage().switchToDocumentLockedPage();
        documentLockedPage().clickCancel();

        sourcePage().switchToSectionNavigatePage();

        sourceNavigateFooterToolsPage().clearSection();
        sourceNavigateGridPage().clickFirstXRenditions(3);
        sourceNavigateGridPage().onlyRightClickFirstItem();
        renditionContextMenu().editSection();
        editorPage().closeDocumentAndDiscardChanges();

        sourcePage().switchToSectionNavigatePage();
        sourceNavigateFooterToolsPage().refreshTheGrid();
        boolean unlocked = true;
        for(int i = firstLine; i <= lastLine; i++)
        {
            unlocked = unlocked && !sourceNavigateGridPage().isRenditionLocked(i);
        }
        boolean allSectionsUnlocked = unlocked;

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(allSectionsLocked, "The first three sections were locked"),
            () -> Assertions.assertTrue(documentLockedWindowAppeared, "The document locked window appears"),
            () -> Assertions.assertTrue(allSectionsUnlocked, "All sections become unlocked when the editor is closed")
        );
    }
}
