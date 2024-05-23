package com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.source.set2;

import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.popups.SelectDateElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import org.apache.logging.log4j.util.Strings;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.HIGHLIGHTED_DELTA;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.SPAN_BY_TEXT;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.FOLLOWING_SIBLING;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.ANCESTOR;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.DELTA_BY_NUMBER_SPAN;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.DATE_BY_TEXT;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.HINT_BY_TEXT;

public class DeferredItalicsAndDateQueriesTests extends TestService
{
    private static final String ACTUAL_DATE_ASSERTION_MESSAGE = "Actual date doesn't match expected";
    private static final String DATE_QUERY_ASSERTION_MESSAGE = "[%s] and [%s] weren't added";
    private static final String PART_START_SUBSECTION_ITALIC = "Part Start Subsection Italic";
    private static final String SECTION_NUMBER_TEN_DOT_ZERO_ONE = "10.01";
    private static final String SECTION_NUMBER_ONE = "1";
    private static final String DATE_QUERY = "Date Query";
    private static final String ACTION_DATE = "Action Date";
    private static final String ITALIC_NOTE = "Italic Note";
    private static final String HINT_TEXT = "insert effective date";
    private static final String SPLIT_REGEX = "effective until ";
    private static final String PART = "part";
    private static final String DOUBLE_SLASH = "//";
    private static final String FIRST_SPAN = "span[1]";
    private static final String ACTION_DATE_FROM_DATE_QUERY_XPATH = String.format(HIGHLIGHTED_DELTA + DOUBLE_SLASH + PART
            + SPAN_BY_TEXT + "/.." + SPAN_BY_TEXT + FOLLOWING_SIBLING, PART_START_SUBSECTION_ITALIC, ACTION_DATE, FIRST_SPAN);
    private static final String ACTION_DATE_FROM_ITALIC_NOTE_TEXT_XPATH = String.format(
            HIGHLIGHTED_DELTA + SPAN_BY_TEXT + FOLLOWING_SIBLING, ITALIC_NOTE, "paratext");
    private static final String PART_START_SUBSECTION_ITALIC_AND_DATE_QUERY_XPATH = String.format(SPAN_BY_TEXT + ANCESTOR
                    + SPAN_BY_TEXT + ANCESTOR, DATE_QUERY, PART, PART_START_SUBSECTION_ITALIC, HIGHLIGHTED_DELTA.replace(DOUBLE_SLASH, Strings.EMPTY));

    /**
     * STORY/BUG - HALCYONST-12705 <br>
     * SUMMARY - Deferred Italics and Date Queries (Source) <br>
     * USER - LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void effectiveDateOnTheRenditionAndNotTheDeltaSourceLegalTest()
    {
        String renditionUUID = "ICDE0C430566411EB91E9B124C222C68E";

        sourcePage().goToSourcePageWithRenditionUuids(renditionUUID);
        loginPage().logIn();
        sourceNavigateGridPage().clickFirstRendition();
        sourceNavigateTabsPage().goToSectionTab();
        sourceNavigateFiltersAndSortsPage().setSiblingOrderFilter(SECTION_NUMBER_TEN_DOT_ZERO_ONE);
        sourceNavigateFooterToolsPage().refreshTheGrid();
        sourceNavigateGridPage().clickFirstSection();

        String expectedEffectiveDate = sourceNavigateGridPage().getEffectiveDateOfFirstItem();

        sourceNavigateGridPage().firstSectionEditContent();
        editorPage().switchDirectlyToTextFrame();
        editorTextPage().scrollToView(String.format(DELTA_BY_NUMBER_SPAN, 3));
        editorTextPage().rightClick(String.format(DELTA_BY_NUMBER_SPAN, 3));
        editorTextPage().breakOutOfFrame();
        editorTextContextMenu().insertItalicNoteWideCentered();
        editorPage().switchToEditorTextFrame();

        boolean arePartStartSubsectionItalicAndDateQueryAdded = editorTextPage().doesElementExist(PART_START_SUBSECTION_ITALIC_AND_DATE_QUERY_XPATH);

        String actionDateFromDateQuery = editorTextPage().getElementsText(ACTION_DATE_FROM_DATE_QUERY_XPATH);
        String actionDateFromItalicNoteText = editorTextPage().getElementsText(ACTION_DATE_FROM_ITALIC_NOTE_TEXT_XPATH).split(SPLIT_REGEX)[1];
        String parsedActionDateFromItalicNoteText = DateAndTimeUtils.formatLocalDateMMddyyy(
                DateAndTimeUtils.getLocalDateFromStringDate(actionDateFromItalicNoteText));

        editorPage().closeDocumentAndDiscardChanges();

        Assertions.assertAll(
                () -> Assertions.assertTrue(arePartStartSubsectionItalicAndDateQueryAdded,
                        String.format(DATE_QUERY_ASSERTION_MESSAGE, PART_START_SUBSECTION_ITALIC, DATE_QUERY)),
                () -> Assertions.assertEquals(expectedEffectiveDate, actionDateFromDateQuery, ACTUAL_DATE_ASSERTION_MESSAGE),
                () -> Assertions.assertEquals(expectedEffectiveDate, parsedActionDateFromItalicNoteText, ACTUAL_DATE_ASSERTION_MESSAGE)
        );
    }

    /**
     * STORY/BUG - HALCYONST-12705 <br>
     * SUMMARY - Deferred Italics and Date Queries (Source) <br>
     * USER - LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void effectiveDateOnTheRenditionAndDifferentEffectiveDateOnTheDeltaSourceLegalTest()
    {
        String renditionUUID = "ICDE0C430566411EB91E9B124C222C68E";

        sourcePage().goToSourcePageWithRenditionUuids(renditionUUID);
        loginPage().logIn();
        sourceNavigateGridPage().clickFirstRendition();
        sourceNavigateTabsPage().goToSectionTab();
        sourceNavigateFiltersAndSortsPage().setSiblingOrderFilter(SECTION_NUMBER_TEN_DOT_ZERO_ONE);
        sourceNavigateFooterToolsPage().refreshTheGrid();
        sourceNavigateGridPage().clickFirstSection();

        sourceNavigateGridPage().firstSectionEditContent();
        editorPage().switchDirectlyToTextFrame();
        editorTextPage().scrollToView(String.format(DELTA_BY_NUMBER_SPAN, 3));
        editorTextPage().rightClick(String.format(DELTA_BY_NUMBER_SPAN, 3));
        editorTextPage().breakOutOfFrame();
        editorTextContextMenu().effectiveDate();
        editorPage().switchToWindow(SelectDateElements.PAGE_TITLE);
        editorPage().enterTheInnerFrame();
        selectDatePage().setDate(DateAndTimeUtils.getCurrentDateMMddyyyy());
        selectDatePage().clickOK();
        editorPage().switchDirectlyToTextFrame();

        editorTextPage().rightClick(String.format(DELTA_BY_NUMBER_SPAN, 3));
        editorTextPage().breakOutOfFrame();
        editorTextContextMenu().insertItalicNoteWideCentered();
        editorPage().switchToEditorTextFrame();

        String expectedEffectiveDate = editorTextPage().getElementsText(String.format(DATE_BY_TEXT, DateAndTimeUtils.getCurrentDateMMddyyyy()));

        boolean arePartStartSubsectionItalicAndDateQueryAdded = editorTextPage().doesElementExist(PART_START_SUBSECTION_ITALIC_AND_DATE_QUERY_XPATH);

        String actionDateFromDateQuery = editorTextPage().getElementsText(ACTION_DATE_FROM_DATE_QUERY_XPATH);
        String actionDateFromItalicNoteText = editorTextPage().getElementsText(ACTION_DATE_FROM_ITALIC_NOTE_TEXT_XPATH).split(SPLIT_REGEX)[1];
        String parsedActionDateFromItalicNoteText = DateAndTimeUtils.formatLocalDateMMddyyy(
                DateAndTimeUtils.getLocalDateFromStringDate(actionDateFromItalicNoteText));

        editorPage().closeDocumentAndDiscardChanges();

        Assertions.assertAll(
                () -> Assertions.assertTrue(arePartStartSubsectionItalicAndDateQueryAdded,
                        String.format(DATE_QUERY_ASSERTION_MESSAGE, PART_START_SUBSECTION_ITALIC, DATE_QUERY)),
                () -> Assertions.assertEquals(expectedEffectiveDate, actionDateFromDateQuery, ACTUAL_DATE_ASSERTION_MESSAGE),
                () -> Assertions.assertEquals(expectedEffectiveDate, parsedActionDateFromItalicNoteText, ACTUAL_DATE_ASSERTION_MESSAGE)
        );
    }

    /**
     * STORY/BUG - HALCYONST-12705 <br>
     * SUMMARY - Deferred Italics and Date Queries (Source) <br>
     * USER - LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void effectiveDateOnTheDeltaAndNotTheRenditionSourceLegalTest()
    {
        String renditionUUID = "I9A9EB75025C511EBA678DBC1FB7144B9";

        sourcePage().goToSourcePageWithRenditionUuids(renditionUUID);
        loginPage().logIn();
        sourceNavigateGridPage().clickFirstRendition();
        sourceNavigateTabsPage().goToSectionTab();
        sourceNavigateFiltersAndSortsPage().setSiblingOrderFilter(SECTION_NUMBER_ONE);
        sourceNavigateFooterToolsPage().refreshTheGrid();
        sourceNavigateGridPage().clickFirstSection();

        sourceNavigateGridPage().firstSectionEditContent();
        editorPage().switchDirectlyToTextFrame();
        editorTextPage().scrollToView(String.format(DELTA_BY_NUMBER_SPAN, 1));
        editorTextPage().rightClick(String.format(DELTA_BY_NUMBER_SPAN, 1));
        editorTextPage().breakOutOfFrame();
        editorTextContextMenu().effectiveDate();
        editorPage().switchToWindow(SelectDateElements.PAGE_TITLE);
        editorPage().enterTheInnerFrame();
        selectDatePage().setDate(DateAndTimeUtils.getCurrentDateMMddyyyy());
        selectDatePage().clickOK();
        editorPage().switchDirectlyToTextFrame();
        editorTextPage().scrollToView(String.format(DELTA_BY_NUMBER_SPAN, 1));
        editorTextPage().rightClick(String.format(DELTA_BY_NUMBER_SPAN, 1));
        editorTextPage().breakOutOfFrame();
        editorTextContextMenu().insertItalicNoteWideCentered();
        editorPage().switchToEditorTextFrame();

        String expectedEffectiveDate = editorTextPage().getElementsText(String.format(DATE_BY_TEXT, DateAndTimeUtils.getCurrentDateMMddyyyy()));

        boolean arePartStartSubsectionItalicAndDateQueryAdded = editorTextPage().doesElementExist(PART_START_SUBSECTION_ITALIC_AND_DATE_QUERY_XPATH);

        String actionDateFromDateQuery = editorTextPage().getElementsText(ACTION_DATE_FROM_DATE_QUERY_XPATH);
        String actionDateFromItalicNoteText = editorTextPage().getElementsText(ACTION_DATE_FROM_ITALIC_NOTE_TEXT_XPATH).split(SPLIT_REGEX)[1];
        String parsedActionDateFromItalicNoteText = DateAndTimeUtils.formatLocalDateMMddyyy(
                DateAndTimeUtils.getLocalDateFromStringDate(actionDateFromItalicNoteText));

        editorPage().closeDocumentAndDiscardChanges();

        Assertions.assertAll(
                () -> Assertions.assertTrue(arePartStartSubsectionItalicAndDateQueryAdded,
                        String.format(DATE_QUERY_ASSERTION_MESSAGE, PART_START_SUBSECTION_ITALIC, DATE_QUERY)),
                () -> Assertions.assertEquals(expectedEffectiveDate, actionDateFromDateQuery, ACTUAL_DATE_ASSERTION_MESSAGE),
                () -> Assertions.assertEquals(expectedEffectiveDate, parsedActionDateFromItalicNoteText, ACTUAL_DATE_ASSERTION_MESSAGE)
        );
    }

    /**
     * STORY/BUG - HALCYONST-12705 <br>
     * SUMMARY - Deferred Italics and Date Queries (Source) <br>
     * USER - LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void noEffectiveDatesOnRenditionOrDeltaSourceLegalTest()
    {
        String renditionUUID = "I9A9EB75025C511EBA678DBC1FB7144B9";

        sourcePage().goToSourcePageWithRenditionUuids(renditionUUID);
        loginPage().logIn();
        sourceNavigateGridPage().clickFirstRendition();
        sourceNavigateTabsPage().goToSectionTab();
        sourceNavigateFiltersAndSortsPage().setSiblingOrderFilter(SECTION_NUMBER_ONE);
        sourceNavigateFooterToolsPage().refreshTheGrid();
        sourceNavigateGridPage().clickFirstSection();

        sourceNavigateGridPage().firstSectionEditContent();
        editorPage().switchDirectlyToTextFrame();
        editorTextPage().scrollToView(String.format(DELTA_BY_NUMBER_SPAN, 1));
        editorTextPage().rightClick(String.format(DELTA_BY_NUMBER_SPAN, 1));
        editorTextPage().breakOutOfFrame();
        editorTextContextMenu().insertItalicNoteWideCentered();
        editorPage().switchToEditorTextFrame();

        boolean arePartStartSubsectionItalicAndDateQueryAdded = editorTextPage().doesElementExist(PART_START_SUBSECTION_ITALIC_AND_DATE_QUERY_XPATH);
        boolean doesActionDateExistInDateQuery = editorTextPage().doesElementExist(ACTION_DATE_FROM_DATE_QUERY_XPATH);
        boolean doesHintToInsertEffectiveDateExist = editorTextPage().doesElementExist(String.format(
                HINT_BY_TEXT + ANCESTOR + SPAN_BY_TEXT, HINT_TEXT, PART, PART_START_SUBSECTION_ITALIC));

        String dateQueryXpath = String.format(SPAN_BY_TEXT + ANCESTOR + SPAN_BY_TEXT, PART_START_SUBSECTION_ITALIC, PART, DATE_QUERY);

        editorTextPage().click(dateQueryXpath);
        editorTextPage().rightClick(dateQueryXpath);
        editorTextPage().breakOutOfFrame();
        editorQueryContextMenu().edit();
        editorPage().enterTheInnerFrame();

        editQueryNotePage().setDate(DateAndTimeUtils.getCurrentDateMMddyyyy());
        editQueryNotePage().clickSave();
        editorPage().switchDirectlyToTextFrame();

        String actionDateFromDateQuery = editorTextPage().getElementsText(String.format(
                SPAN_BY_TEXT + ANCESTOR + SPAN_BY_TEXT + FOLLOWING_SIBLING, PART_START_SUBSECTION_ITALIC, PART, ACTION_DATE, FIRST_SPAN));

        editorPage().closeDocumentAndDiscardChanges();

        Assertions.assertAll(
                () -> Assertions.assertTrue(arePartStartSubsectionItalicAndDateQueryAdded,
                        String.format(DATE_QUERY_ASSERTION_MESSAGE, PART_START_SUBSECTION_ITALIC, DATE_QUERY)),
                () -> Assertions.assertFalse(doesActionDateExistInDateQuery,
                        String.format("[%s] doesn't exist in [%s]", ACTION_DATE, DATE_QUERY)),
                () -> Assertions.assertTrue(doesHintToInsertEffectiveDateExist,
                        String.format("Hint with [%s] text doesn't exist", HINT_TEXT)),
                () -> Assertions.assertEquals(DateAndTimeUtils.getCurrentDateMMddyyyy(), actionDateFromDateQuery, ACTUAL_DATE_ASSERTION_MESSAGE)
        );
    }
}
