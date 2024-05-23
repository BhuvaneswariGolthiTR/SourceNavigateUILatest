package com.thomsonreuters.codes.codesbench.quality.tests.source.set1;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class EditSectionsFromSectionTabTests extends TestService
{
    /**
     * STORY:  NA <br>
     * SUMMARY:  Checks the number of sections in the editor when different number is selected.<br>
     * USER:  LEGAL <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void canEditMultipleSectionsFromSectionTabTest()
    {
        String contentSet = "Iowa (Development)";
        String year = "2011";
        String renditionStatus = "DRA";
        String contentType = "BILL";
        String docType = "H";
        String docNumber = "SB507";

        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterContentSet(contentSet);
        sourceNavigateFiltersAndSortsPage().setFilterYear(year);
        sourceNavigateFiltersAndSortsPage().setFilterRenditionStatus(renditionStatus);
        sourceNavigateFiltersAndSortsPage().setFilterContentType(contentType);
        sourceNavigateFiltersAndSortsPage().setFilterDocType(docType);
        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(docNumber);
        sourceNavigateFooterToolsPage().refreshTheGrid();
        sourceNavigateGridPage().unlockFirstRendition();

        sourceNavigateGridPage().clickFirstRendition();
        sourceNavigateTabsPage().goToSectionTab();

        sourceNavigateGridPage().rightClickFirstSection();
        sectionContextMenu().editSection();
        editorPage().switchDirectlyToTextFrame();
        boolean oneSectionAppeared = editorTextPage().getNumberOfSourceSections() == 1;
        editorPage().closeDocumentWithNoChanges();

        sourcePage().switchToSectionNavigatePage();
        sourceNavigateGridPage().refreshPage();
        sourceNavigateGridPage().waitUntilFirstDocBeUnlocked();
        sourceNavigateGridPage().clickFirstXRenditions(2);

        sourceNavigateGridPage().onlyRightClickFirstItem();
        sectionContextMenu().editSections();
        editorPage().switchDirectlyToTextFrame();
        boolean twoSectionsAppeared = editorTextPage().getNumberOfSourceSections() == 2;
        editorPage().closeDocumentWithNoChanges();

        sourcePage().switchToSectionNavigatePage();
        sourceNavigateGridPage().refreshPage();
        sourceNavigateGridPage().clickFirstXRenditions(3);

        sourceNavigateGridPage().onlyRightClickFirstItem();
        sectionContextMenu().editSections();
        editorPage().switchDirectlyToTextFrame();
        boolean threeSectionsAppeared = editorTextPage().getNumberOfSourceSections() == 3;
        editorPage().closeDocumentWithNoChanges();

        Assertions.assertAll
        (
                () -> Assertions.assertTrue(oneSectionAppeared, "There was only one section."),
                () -> Assertions.assertTrue(twoSectionsAppeared, "There was only two sections."),
                () -> Assertions.assertTrue(threeSectionsAppeared, "There was only three sections.")
        );
    }
    /**
     * STORY: NA <br>
     * SUMMARY: Edits multiple sections at the same time using the editor <br>
     * USER: Legal  <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void editTwoSectionsByAddingTextTest() {
        String contentSet = "Iowa (Development)";
        String year = "2011";
        String renditionStatus = "DRA";
        String contentType = "BILL";
        String docType = "H";
        String docNumber = "SB507";

        String testText = "TEST";

        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterContentSet(contentSet);
        sourceNavigateFiltersAndSortsPage().setFilterYear(year);
        sourceNavigateFiltersAndSortsPage().setFilterRenditionStatus(renditionStatus);
        sourceNavigateFiltersAndSortsPage().setFilterContentType(contentType);
        sourceNavigateFiltersAndSortsPage().setFilterDocType(docType);
        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(docNumber);
        sourceNavigateFooterToolsPage().refreshTheGrid();
        sourceNavigateGridPage().unlockFirstRendition();

        sourceNavigateGridPage().clickFirstRendition();
        sourceNavigateTabsPage().goToSectionTab();

        sourceNavigateGridPage().clickFirstXRenditions(2);
        sourceNavigateGridPage().onlyRightClickFirstItem();
        sectionContextMenu().editSections();
        editorPage().switchDirectlyToTextFrame();

        editorTextPage().clickSourceSectionByNumber(2);
        editorTextPage().rightClickSourceSectionByNumber(2);
        editorPage().breakOutOfFrame();
        editorTextContextMenu().insertTextChildIntroductoryParagraph();
        editorPage().switchDirectlyToTextFrame();
        editorTextPage().clickSectionIntroParagraphXText(2);
//        editorTextPage().sendKeysToIntroParagraphX(2,testText);
        editorPage().closeAndCheckInChanges();
        editorPage().waitForEditorToClose();

        sourcePage().switchToSectionNavigatePage();
        sourceNavigateGridPage().onlyRightClickFirstItem();
        sectionContextMenu().editSection();
        editorPage().switchDirectlyToTextFrame();
        boolean editSuccessful = editorTextPage().sectionIntroParagraphXContains(2,testText);
        editorTextPage().clickSectionIntroParagraphXSpan(2);
        editorTextPage().sendDeleteToSectionIntroParagraphX(2);
        editorPage().closeAndCheckInChanges();

        Assertions.assertTrue(editSuccessful, "The section was successfully edited");
    }
}