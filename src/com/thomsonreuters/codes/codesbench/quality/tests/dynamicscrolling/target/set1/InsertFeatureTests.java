package com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.target.set1;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.autoIT.AutoITUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.*;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.popups.ContentEditorialSystemPageElements.PAGE_TITLE;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.popups.ContentEditorialSystemPageElements.StockNote.INSERTED_MATERIAL;

public class InsertFeatureTests extends TestService
{
    private static final String SOURCE_TAG_ASSERTION_MESSAGE = "Source tag doesn't add to the %s";
    private static final String MODIFIED_BY_INFO_ASSERTION_MESSAGE = "Modified by info doesn't add to the %s";
    private static final String EXPECTED_ALERT_MESSAGE = "Document validation failed, continue with check-in?";

    /**
     * STORY/BUG - HALCYONST-12986 <br>
     * SUMMARY - Insertion of features using popups add modified.by information (Target) <br>
     * USER - LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void insertionOfFeaturesUsingPopupsAddModifiedByInfoTargetLegalTest() {
        String uuid = "I9043F155AFE347839DF5034F4650C132";
        String sessionDefaultTextSourceTag = "17-A1";
        String historicalNoteParagraph = "Historical Note Paragraph";
        String historicalNoteBody = "Historical Note Body";
        String yearHeading = "Year Heading";
        String codesHeadAncestor = "codes.head";
        String metaBlockAncestor = "metadata.block";
        String paraAncestor = "para";

        hierarchyNavigatePage().goToHierarchyPage(ContentSets.USCA_DEVELOPMENT.getCode());
        loginPage().logIn();
        hierarchyNavigatePage().handleCasIssueWithHierarchyPageOpenedAsStartPageIfOccurred(ContentSets.USCA_DEVELOPMENT);
        hierarchySearchPage().searchNodeUuid(uuid);
        siblingMetadataPage().selectedSiblingMetadataEditContent();

        editorToolbarPage().clickConfigureEditorSessionPreferences();
        editorPreferencesPage().selectSessionDefaultTextSourceTagDropdownOption(sessionDefaultTextSourceTag);
        editorPreferencesPage().clickSaveButton();
        editorPage().switchDirectlyToTextFrame();

        editorTextPage().rightClick(String.format(SPAN_BY_TEXT, "Text Paragraph"));
        editorTextPage().breakOutOfFrame();
        editorTextContextMenu().insertAmendmentPopup();

        editorPage().switchToWindow(PAGE_TITLE);

        boolean isContentEditorialSystemWindowAppeared = driver().getTitle().equals(PAGE_TITLE);

        contentEditorialSystemPage().enterTheInnerFrame();
        contentEditorialSystemPage().selectStockNote(INSERTED_MATERIAL);
        contentEditorialSystemPage().clickSave();
        editorPage().switchDirectlyToTextFrame();

        boolean isHistoricalNoteBodyInsertedAndHighlightedInTheAnnotations = editorTextPage().doesElementExist(String.format(
                HISTORICAL_NOTE_BODY + SPAN_BY_TEXT + ANCESTOR, historicalNoteBody, "annotations"));

        String sourceTagYearHeading = String.format(SOURCE_TAG_BY_TEXT + ANCESTOR + SPAN_BY_TEXT,
                sessionDefaultTextSourceTag, codesHeadAncestor, yearHeading);
        String sourceTagHistoricalNoteParagraph = String.format(SOURCE_TAG_BY_TEXT + ANCESTOR + SPAN_BY_TEXT,
                sessionDefaultTextSourceTag, paraAncestor, historicalNoteParagraph);

        boolean isSourceTagAddedToTheYearHeading = editorTextPage().doesElementExist(sourceTagYearHeading);
        boolean isSourceTagAddedToTheHistoricalNoteParagraph = editorTextPage().doesElementExist(sourceTagHistoricalNoteParagraph);

        String modifiedByYearHeading = editorTextPage().getModifiedByXpath(user()) + String.format(
                ANCESTOR + SPAN_BY_TEXT, codesHeadAncestor, yearHeading);
        String modifiedByHistoricalNoteParagraph = editorTextPage().getModifiedByXpath(user()) + String.format(
                ANCESTOR + SPAN_BY_TEXT, paraAncestor, historicalNoteParagraph);

        boolean isModifiedByAddedToTheYearHeading = editorTextPage().doesElementExist(modifiedByYearHeading);
        boolean isModifiedByAddedToTheHistoricalNoteParagraph = editorTextPage().doesElementExist(modifiedByHistoricalNoteParagraph);

        editorPage().closeAndCheckInChanges();
        AutoITUtils.verifyAlertTextContainsAndAccept(true, EXPECTED_ALERT_MESSAGE);

        editorPage().switchToEditor();
        hierarchyNavigatePage().switchToHierarchyEditPage();
        siblingMetadataPage().selectedSiblingMetadataEditContent();
        editorPage().switchToEditorTextFrame();

        boolean isSourceTagStillAddedToTheYearHeading = editorTextPage().doesElementExist(sourceTagYearHeading);
        boolean isSourceTagStillAddedToTheHistoricalNoteParagraph = editorTextPage().doesElementExist(sourceTagHistoricalNoteParagraph);

        boolean isModifiedByStillAddedToTheYearHeading = editorTextPage().doesElementExist(modifiedByYearHeading);
        boolean isModifiedByStillAddedToTheHistoricalNoteParagraph = editorTextPage().doesElementExist(modifiedByHistoricalNoteParagraph);

        editorTextPage().scrollToView(editorTextPage().getModifiedByXpath(user()) + String.format(
                ANCESTOR + SPAN_BY_TEXT + "/../../span", codesHeadAncestor, yearHeading));
        editorTextPage().rightClick(editorTextPage().getModifiedByXpath(user()) + String.format(
                ANCESTOR + SPAN_BY_TEXT + "/../../span", codesHeadAncestor, yearHeading));
        editorTextPage().breakOutOfFrame();
        editorTextContextMenu().delete();

        editorPage().closeAndCheckInChanges();

        Assertions.assertAll(
                () -> Assertions.assertTrue(isContentEditorialSystemWindowAppeared,
                        String.format("%s window doesn't appear", PAGE_TITLE)),
                () -> Assertions.assertTrue(isHistoricalNoteBodyInsertedAndHighlightedInTheAnnotations,
                        String.format("%s doesn't insert and highlight in the annotations", historicalNoteBody)),
                () -> Assertions.assertTrue(isSourceTagAddedToTheYearHeading,
                        String.format(SOURCE_TAG_ASSERTION_MESSAGE, yearHeading)),
                () -> Assertions.assertTrue(isSourceTagAddedToTheHistoricalNoteParagraph,
                        String.format(SOURCE_TAG_ASSERTION_MESSAGE, historicalNoteParagraph)),
                () -> Assertions.assertTrue(isModifiedByAddedToTheYearHeading,
                        String.format(MODIFIED_BY_INFO_ASSERTION_MESSAGE, yearHeading)),
                () -> Assertions.assertTrue(isModifiedByAddedToTheHistoricalNoteParagraph,
                        String.format(MODIFIED_BY_INFO_ASSERTION_MESSAGE, historicalNoteParagraph)),
                () -> Assertions.assertTrue(isSourceTagStillAddedToTheYearHeading,
                        String.format(SOURCE_TAG_ASSERTION_MESSAGE, yearHeading)),
                () -> Assertions.assertTrue(isSourceTagStillAddedToTheHistoricalNoteParagraph,
                        String.format(SOURCE_TAG_ASSERTION_MESSAGE, historicalNoteParagraph)),
                () -> Assertions.assertTrue(isModifiedByStillAddedToTheYearHeading,
                        String.format(MODIFIED_BY_INFO_ASSERTION_MESSAGE, yearHeading)),
                () -> Assertions.assertTrue(isModifiedByStillAddedToTheHistoricalNoteParagraph,
                        String.format(MODIFIED_BY_INFO_ASSERTION_MESSAGE, historicalNoteParagraph))
        );
    }
}
