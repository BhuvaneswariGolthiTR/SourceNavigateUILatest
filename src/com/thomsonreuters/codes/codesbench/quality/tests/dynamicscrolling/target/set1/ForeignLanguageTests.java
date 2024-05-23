package com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.target.set1;

import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorToolbarPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.popups.SetLawTrackingPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.popups.SpellcheckPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.PreviousWipVersionsPageElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.*;

import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class ForeignLanguageTests extends TestService
{
    /**
     * STORY/BUG - JETS-14299 <br>
     * SUMMARY - A test to verify that spellcheck is disabled in foreign language content sets.
     * It checks this by first seeing the toolbar spell check button is disabled, and then by
     * inserting a misspelled word, checking in the document and verifying that the spell check
     * window doesn't appear. <br>
     * USER - Risk <br>
     */
    @Test
    @IE_EDGE_MODE
    @RISK
    @LOG
    public void disableSpellcheckDictionaryInForeignLanguageContentSetsTest()
    {
        String contentSet = "German Legislative";
        String nodeUuid = "I73fc8ad2521f48ef82739b48cbb5a39e";
        String misspelledWord = "Thursty Thirsday ";
        String paragraphNumber = "1";

        // Change content set and go to Hierarchy Navigate
        homePage().goToHomePage();
        loginPage().logIn();
        homePage().setMyContentSet(contentSet);
        hierarchyMenu().goToNavigate();

        // Search our node, right click -> edit content
        hierarchySearchPage().searchNodeUuid(nodeUuid);
        siblingMetadataPage().selectedSiblingMetadataEditContent();

        // Verify that the spell check button is disabled
        boolean checkSpellingButtonDisabled = editorToolbarPage().isElementDisabled(EditorToolbarPageElements.toolbarCheckSpellingButton);

        // Enter a miss spelled word into the first text paragraph
        editorPage().switchDirectlyToTextFrame();
        editorTextPage().insertPhraseIntoTextParagraphByNumber(misspelledWord, paragraphNumber);

        // Close and check in changes and verify a spell check window doesn't appear
        editorPage().closeAndCheckInChanges();
        boolean spellCheckWindowAppears = editorPage().doesWindowExistByTitle(SpellcheckPageElements.PAGE_TITLE);

        // Go back to hierarchy navigate, right click our node -> View/Modify Previous WIP Version
        hierarchyNavigatePage().switchToHierarchyEditPage();
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().viewModifyPreviousWIPVersion();

        // Restore the WIP version with index 0, set law tracking to quickload and hit ok
        previousWipVersionsPage().restoreOriginalWIPVersion();
        setLawTrackingPage().clickQuickLoadTrackingButton();
        setLawTrackingPage().clickOkButton();
        previousWipVersionsPage().switchToWindow(PreviousWipVersionsPageElements.PREVIOUS_WIP_VERSIONS_PAGE_TITLE);
        previousWipVersionsPage().waitForGridRefresh();
        previousWipVersionsPage().closeCurrentWindowIgnoreDialogue();

        Assertions.assertAll
        (
                () -> Assertions.assertTrue(checkSpellingButtonDisabled, "The Check Spelling Button should be disabled."),
                () -> Assertions.assertFalse(spellCheckWindowAppears, "The Spell Check window appeared and it should not have.")
        );
    }


    /**
     * STORY/BUG - JETS-18906 <br>
     * SUMMARY - This test checks that we can insert a new grade level node which contains a special character
     * in the keyword. <br>
     * USER - Risk <br>
     */
    @Test
    @IE_EDGE_MODE
    @RISK
    @LOG
    public void supportForSpecialCharactersInKeywordsOfNewGradeNodesTest()
    {
        String contentSet = "Luxembourg Regulatory";
        String nodeUuid = "IF5CEF1A508C111E7B66FB8CA3AB007EB";
        String depth = "0";
        String westlawFormatStatus = "Default";
        String nodeType = "SECTION";
        String keyword = "R"+'\u00e8'+"glement";
        String keywordSearch = "R"+'\u00e8'+"GLEMENT";
        String value = "Test " + DateAndTimeUtils.getCurrentDateAndTimeyyyyMMddHHmmss();
        String effectiveStartDate = DateAndTimeUtils.getCurrentDateMMddyyyy();

        // Change content set and go to Hierarchy Navigate
        homePage().goToHomePage();
        loginPage().logIn();
        homePage().setMyContentSet(contentSet);
        hierarchyMenu().goToNavigate();

        // Search node, right click -> Hierarchy Functions -> Insert In Hierarchy
        hierarchySearchPage().searchNodeUuid(nodeUuid);
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().hierarchyFunctionsInsertInHierarchy();
        System.out.println(insertNewNodesPage().getSource());

        // Insert new node with our values
        insertNewNodesPage().insertNewNodeSetDepth(depth); //TODO remove * from xpaths and update to elements
        insertNewNodesPage().setWestlawFormatStatus(westlawFormatStatus);
        insertNewNodesPage().setNodeType(nodeType);
        insertNewNodesPage().setKeyword(keyword);
        insertNewNodesPage().setValue(value);
        insertNewNodesPage().setEffectiveStartDate(effectiveStartDate);
        setLawTrackingPage().click(SetLawTrackingPageElements.quickLoadTrackingButton);
        insertNewNodesPage().clickOk();

        // Navigate to the new node and right click -> edit content
        hierarchySearchPage().quickSearch(keywordSearch, value);
        siblingMetadataPage().selectedSiblingMetadataEditContent();

        // Pull the text values of the hints (yellow highlighted text)
        editorPage().switchDirectlyToTextFrame();
        List<String> hints = editorPage().getHints();
        editorPage().closeDocumentWithNoChanges();

        // Delete the new node
        hierarchyNavigatePage().switchToHierarchyEditPage();

        if(!siblingMetadataPage().isSelectedNodeDeleted())
        {
            siblingMetadataPage().rightClickSelectedSiblingMetadata();
            siblingMetadataContextMenu().deleteFunctionsDelete();
            deletePage().clickQuickLoad();
            deletePage().clickSubmit();
        }

        boolean hasCornerpieceCiteHint = hints.contains("cornerpiece cite");

        Assertions.assertAll
        (
                () -> Assertions.assertTrue(hints.contains("cornerpiece cite"), "We expected to see a hint under the Cornerpiece English labeled with text 'cornerpiece cite'"),
                () -> Assertions.assertTrue(hints.contains("keyword text"), "We expected to see a hint under the Section Nameline English labeled with text 'keyword text'"),
                () -> Assertions.assertTrue(hints.contains("cite information"), "We expected to see a hint under the Section Nameline English labeled with text 'cite information'"),
                () -> Assertions.assertTrue(hints.contains("nameline text"), "We expected to see a hint under the Section Nameline English labeled with text 'nameline text'"),
                () -> Assertions.assertTrue(hints.contains("add related cite information"), "We expected to see a hint under the Source Note Paragraph English labeled with text 'add related cite information'"),
                () -> Assertions.assertTrue(hints.contains("add text"), "We expected to see a hint under the Text Paragraph English labeled with text 'add text'")
        );
    }
}