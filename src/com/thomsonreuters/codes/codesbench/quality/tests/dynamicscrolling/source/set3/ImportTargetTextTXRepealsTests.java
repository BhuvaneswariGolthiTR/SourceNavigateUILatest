package com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.source.set3;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorPageElements.COMMAND_IN_PROGRESS;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.*;
import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * STORY/BUG - HALCYONST-12694 <br>
 * SUMMARY - Import target text TX repeals (Source) <br>
 * USER - LEGAL <br>
 */
public class ImportTargetTextTXRepealsTests extends TestService
{
    private static final String COMMON_EDITOR = "Common Editor";
    private static final String DELETED_MATERIAL = "//deleted.material";
    private SoftAssertions softAssertions;

    @BeforeEach
    public void login()
    {
        homePage().goToHomePage();
        loginPage().logIn();
    }

    @AfterEach
    public void closeDocument()
    {
        editorPage().closeDocumentAndDiscardChanges();
    }

    /*
    1. Access Texas (Development) - this enhancement is for Texas content set only
    2. Open source rendition with uuid: I993C1A1025C511EBA678DBC1FB7144B9 in DS editor
    3. Document will open, locate the 'Delta Repeal Section' that is cite located and does not contain any Part Texts, e.g., 551.053
    4. To make sure that this delta in the source document is cite located, switch to Delta tab and check Target Node column –
        if you click the link, target node can be opened
    5. Right click on 'Delta Repeal Section' English label
        VERIFY: the 'Import Target Text TX Repeals' is under the 'Import Target Text TX' context menu option
    6. Select 'Import Target Text TX Repeals' from the context menu
        VERIFY: full text of the cite located live section from the hierarchy is imported into the Source document
        VERIFY: all paragraphs are combined into a single paragraph
        VERIFY: deleted markup is added around each imported line
        VERIFY: end left markup is added after the end delete markup, immediately following the deleted markup on each imported line
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void importTargetTextTXRepealsOnDeltaRepealSectionSourceLegalTest()
    {
        softAssertions = new SoftAssertions();

        String uuid = "I993C1A1025C511EBA678DBC1FB7144B9";
        String targetLocationSectionNumber = "551.053";
        String highlightedParatext = PARA + CLASS_HIGHLIGHTED_POSTFIX + PARATEXT;

        openSourceRenditionInDsEditor(uuid);

        openContextMenuOnElement(TARGET_LOCATION_SECTION + format(CONTAINS_TEXT_POSTFIX, targetLocationSectionNumber)
                + format(ANCESTOR, "delta/span"));

        //Assert that the 'Import Target Text TX Repeals' is under the 'Import Target Text TX' context menu option
        softAssertThatImportTargetTextTXRepealsOptionIsPlacedCorrectly();

        importTargetTextTXRepeals();

        assertThatDeltaIsCiteLocated(5, targetLocationSectionNumber);

        openSelectedDeltaTargetNode();

        List<String> textListThatShouldBeImported = editorTextPage().getElementsTextList(
                SECTION_NAMELINE + HEAD_INFO + " | " + BODY_TAG + SUBSECTION + PARA + PARATEXT);

        closeTargetNode();

        softAssertThatFullTextOfCiteLocatedLiveSectionFromHierarchyIsImportedIntoSourceDocument(
                textListThatShouldBeImported, highlightedParatext);
        softAssertThatAllParagraphsAreCombinedIntoSingleParagraph(highlightedParatext);
        softAssertThatDeletedMarkupIsAddedAroundEachImportedLine(highlightedParatext, textListThatShouldBeImported);
        softAssertThatEndLeftMarkupIsAddedAfterEndDeleteMarkupOnEachImportedLine(highlightedParatext,
                textListThatShouldBeImported);

        softAssertions.assertAll();
    }

    /*
    1. Access Texas (Development) - this enhancement is for Texas content set only
    2. Open source rendition with uuid: I20DB18804C4711EAA224F7A255AA9973 in DS editor
    3. Document will open, locate source section 4
    4. Right click on 'Delta Amend Subsection' wrapper
    5. To make sure that this delta in the source document is cite located, switch to Delta tab and check Target Node column –
        if you click the link, target node can be opened
    6. Right click on 'Delta Amend Subsection' English label
        VERIFY: the 'Import Target Text TX Repeals' is under the 'Import Target Text TX' context menu option
    7. Select 'Import Target Text TX Repeals' from the context menu
        VERIFY: full text of the cite located live section from the hierarchy is imported into the Source document
        VERIFY: all paragraphs are combined into a single paragraph
        VERIFY: deleted markup is added around each imported paragraph
        VERIFY: end left markup is added after the each line, immediately following the deleted markup on each imported paragraph
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void importTargetTextTXRepealsOnDeltaAmendSubsectionSourceLegalTest()
    {
        softAssertions = new SoftAssertions();

        String uuid = "I20DB18804C4711EAA224F7A255AA9973";
        String modifiedParatext = format(MODIFIED_BY_MNEMONIC_WITH_TEXT,
                format("Imported by %s", editorTextPage().getUserNameAndCurrentData(user())))
                + format(ANCESTOR, "para/paratext");

        openSourceRenditionInDsEditor(uuid);

        openContextMenuOnElement(SOURCE_SECTION + "[@num='4']" + DELTA_AMEND_SUBSECTION_LABEL);

        //Assert that the 'Import Target Text TX Repeals' is under the 'Import Target Text TX' context menu option
        softAssertThatImportTargetTextTXRepealsOptionIsPlacedCorrectly();

        importTargetTextTXRepeals();

        assertThatDeltaIsCiteLocated(4, "261.307");

        openSelectedDeltaTargetNode();

        List<String> textListThatShouldBeImported = editorTextPage().getElementsTextList(
                BODY_TAG + SUBSECTION + "[1]" + PARA + PARATEXT);

        closeTargetNode();

        softAssertThatFullTextOfCiteLocatedLiveSectionFromHierarchyIsImportedIntoSourceDocument(
                textListThatShouldBeImported, modifiedParatext);
        softAssertThatAllParagraphsAreCombinedIntoSingleParagraph(modifiedParatext);
        softAssertThatDeletedMarkupIsAddedAroundEachImportedLine(modifiedParatext, textListThatShouldBeImported);
        softAssertThatEndLeftMarkupIsAddedAfterEndDeleteMarkupOnEachImportedLine(modifiedParatext,
                textListThatShouldBeImported);

        softAssertions.assertAll();
    }

//  ------------- Assistive methods: -------------

    private void openSourceRenditionInDsEditor(String uuid)
    {
        sourcePage().goToSourcePageWithRenditionUuids(uuid);
        sourceNavigateGridPage().firstRenditionEditContent();
        editorPage().switchToEditorAndScrollToChunk(2);
    }

    private void openContextMenuOnElement(String xPath)
    {
        editorTextPage().click(xPath);
        editorTextPage().rightClick(xPath);
        editorTextPage().breakOutOfFrame();
    }

    private void softAssertThatImportTargetTextTXRepealsOptionIsPlacedCorrectly()
    {
        List<String> allAvailableContextMenuOptionsList = editorTextContextMenu().getAllAvailableContextMenuOptions();
        int index = allAvailableContextMenuOptionsList.indexOf("Import Target Text TX Repeals");
        assertThat(index)
                .as("The 'Import Target Text TX Repeals' option should present in context menu")
                .isNotEqualTo(-1);
        softAssertions.assertThat(allAvailableContextMenuOptionsList.get(index - 1))
                .as("The 'Import Target Text TX Repeals' should be under the 'Import Target Text TX'" +
                        " context menu option")
                .isEqualTo("Import Target Text TX");
    }

    private void importTargetTextTXRepeals()
    {
        editorTextContextMenu().importTargetTextTXRepeals();
        editorPage().switchDirectlyToTextFrame();
    }

    private void assertThatDeltaIsCiteLocated(int rowIndex, String targetLocationSectionNumber)
    {
        pendingRenditionNavigatePage().switchToPendingRenditionNavigatePage();
        sourceNavigateGridPage().clickFirstRendition();
        sourceNavigateTabsPage().goToDeltaTab();
        sourceNavigateGridPage().clickRowByIndex(rowIndex);

        assertThat(sourceNavigateGridPage().getSelectedDeltaTargetNode())
                .as(format("The delta with %s target location section number should be cite located",
                        targetLocationSectionNumber))
                .contains(targetLocationSectionNumber);
    }

    private void openSelectedDeltaTargetNode()
    {
        sourceNavigateGridPage().rightClick("//tr" + format(CLASS_CONTAINS_POSTFIX, "selected")
                + "/td" + format(CLASS_CONTAINS_POSTFIX, "targetNode") + "/div/a");
        sourceNavigateGridPage().breakOutOfFrame();
        deltaContextMenu().goToEditTargetContent();
        editorPage().switchToWindow(COMMON_EDITOR, true);
        editorPage().waitForPageLoaded();
        editorPage().waitForElementGone(COMMAND_IN_PROGRESS);
        editorPage().switchToEditorTextFrame();
    }

    private void closeTargetNode()
    {
        editorPage().closeDocumentWithNoChanges();
        editorPage().switchToWindow(COMMON_EDITOR);
        editorPage().closeCurrentWindowIgnoreDialogue();
        editorPage().switchDirectlyToTextFrame();
    }

    private void softAssertThatFullTextOfCiteLocatedLiveSectionFromHierarchyIsImportedIntoSourceDocument(
            List<String> textListThatShouldBeImported, String importedTextXpath)
    {
        textListThatShouldBeImported.forEach(textThatShouldBeImported ->
                softAssertions.assertThat(editorTextPage().getElementsText(importedTextXpath))
                        .as("The full text of the cite located section from the hierarchy" +
                                " should be imported into the source document")
                        .contains(textThatShouldBeImported));
    }

    private void softAssertThatAllParagraphsAreCombinedIntoSingleParagraph(String importedTextXpath)
    {
        softAssertions.assertThat(editorTextPage().countElements(importedTextXpath))
                .as("All paragraphs should be combined into a single paragraph")
                .isEqualTo(1);
    }

    private void softAssertThatDeletedMarkupIsAddedAroundEachImportedLine(String importedTextXpath,
                                                                          List<String> textListThatShouldBeImported)
    {
        softAssertions.assertThat(editorTextPage().countElements(importedTextXpath + DELETED_MATERIAL))
                .as("The deleted markup should be added around each imported line")
                .isEqualTo(textListThatShouldBeImported.size());
    }

    private void softAssertThatEndLeftMarkupIsAddedAfterEndDeleteMarkupOnEachImportedLine(String importedTextXpath,
                                List<String> textListThatShouldBeImported)
    {
        softAssertions.assertThat(editorTextPage()
                .countElements(importedTextXpath + DELETED_MATERIAL + format(FOLLOWING_SIBLING, "endline")))
                .as("The end left markup should be added after the end delete markup," +
                        " immediately following the deleted markup on each imported line")
                .isEqualTo(textListThatShouldBeImported.size());
    }
}
