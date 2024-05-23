package com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.source.set3;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.autoIT.AutoITUtils;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.LOADED_CHUNK;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.popups.StatPageReorderPageElements.PAGE_TITLE;
import static java.lang.String.format;
import static java.lang.String.valueOf;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * STORY/BUG - HALCYONST-11967 <br>
 * SUMMARY - STAT Pages (Source) <br>
 * USER - LEGAL <br>
 */
public class StatPagesTests extends TestService
{
    private static final String UUID = "ICBDDF351192F11EB873AE934EDADDF57";
    private static final String DESCENDANT_STAR_PAGE_ANCHOR = "/descendant::starpage.anchor";
    private static final String MARKUP_NOT_SELECTED_ERROR_MESSAGE =
            "Please select the \"starpage.anchor\" markup to start reordering from";
    private static final String STAT_PAGE_REORDER_CANCELLED_BY_EDITOR_ERROR_MESSAGE =
            "StatPage reorder has been cancelled by the Editor";
    private static final String STAT_PAGE_REORDER_CANCELLED_BY_USER_WARN_MESSAGE =
            "StatPage reorder has been cancelled by the user";
    private static final String LAST_INSTANCE_IN_THE_DOCUMENT_ERROR_MESSAGE =
            "Current selected StatePage instance is the last instance in the document";
    private static final int TOTAL_DOCUMENT_STAT_PAGES_NUMBER = 31;
    private static final int INITIAL_STAT_PAGE_POSITION = 1;
    private static final int SECOND_CHUNK = 2;

    private int reorderStartingNumber;
    private ReorderFrom reorderStartingPosition;

    @BeforeEach
    public void loginAndOpenSourceRenditionInDsEditor()
    {
        sourcePage().goToSourcePageWithRenditionUuids(UUID);
        loginPage().logIn();
        sourceNavigateGridPage().firstRenditionEditContent();
        editorPage().switchDirectlyToTextFrame();
    }

    @AfterEach
    public void closeSourceRendition()
    {
        editorPage().closeEditorWithDiscardingChangesIfAppeared();
    }

    /**
     * STORY/BUG - HALCYONST-11911 <br>
     * SUMMARY - STAT Pages should not be duplicated after Rebuild (Source) <br>
     * USER - LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void reorderFromTopAndRebuildSourceLegalTest()
    {
        // Do NOT select any STAT page markup (USPL page break markup).
        // Open STAT page reorder dialogue,
        // set values to "Reorder from" (TOP STAT page) and "First reordered STAT page number" (1) fields,
        // start reordering:
        setupTheValuesAndStartReorderingFromPositionAndNumberingFrom(ReorderFrom.TOP, 1);

        List<String> currentStatPagesListValues = getCurrentStatPagesListValues();
        int usplPageBreakMarkupsNumberBeforeRebuild = currentStatPagesListValues.size();

        // Assert that USPL page break markups through the document contain STAT.nnn where nnn = 1,2,3...xxx
        assertThatUsplPageBreakMarkupsThroughTheDocumentContainCorrectValues(currentStatPagesListValues);
        softAssertThatMessagePaneContainsAllMessagesOfSuccessfulStatPagesReordering();

        //Rebuild and accept alert
        editorToolbarPage().clickRebuild();
        AutoITUtils.verifyAlertTextAndAccept(true,
                "*Warning* This Rendition has existing Section Groups and/or Delta Groups.  " +
                        "Running Rebuild will remove all Section Group and/or Delta Group assignments. *Warning*.");

        // Assert that USPL page break markups number after rebuild is the same as before rebuild
        editorPage().switchDirectlyToTextFrame();
        assertThat(getCurrentStatPagesListValues().size())
                .as("USPL page break markups number after rebuild should be the same as before rebuild")
                .isEqualTo(usplPageBreakMarkupsNumberBeforeRebuild);
    }

    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void reorderFromCurrentSourceLegalTest()
    {
        // Do NOT select any STAT page markup (USPL page break markup).
        // Open STAT page reorder dialogue,
        // set values to "Reorder from" (CURRENT STAT page) and "First reordered STAT page number" (5) fields,
        // start reordering:
        setupTheValuesAndStartReorderingFromPositionAndNumberingFrom(ReorderFrom.CURRENT, 5);
        softAssertThatMessagePaneContainsMessages(
                MARKUP_NOT_SELECTED_ERROR_MESSAGE,
                STAT_PAGE_REORDER_CANCELLED_BY_EDITOR_ERROR_MESSAGE);

        // Select INITIAL_STAT_PAGE_POSITION STAT page markup (USPL page break markup).
        // Open STAT page reorder dialogue,
        // set values to "Reorder from" (CURRENT STAT page) and "First reordered STAT page number" (5) fields,
        // start reordering:
        selectInitialStatPagePositionPageBreakMarkup();
        setupTheValuesAndStartReorderingFromPositionAndNumberingFrom(ReorderFrom.CURRENT, 5);
        //Assert that USPL page break markups through the document contain STAT.nnn where nnn = 5,6,7...xxx
        assertThatUsplPageBreakMarkupsThroughTheDocumentContainCorrectValues(getCurrentStatPagesListValues());
        softAssertThatMessagePaneContainsAllMessagesOfSuccessfulStatPagesReordering();
    }

    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void reorderFromNextSourceLegalTest()
    {
        // Do NOT select any STAT page markup (USPL page break markup).
        // Open STAT page reorder dialogue,
        // set values to "Reorder from" (NEXT STAT page) and "First reordered STAT page number" (7) fields,
        // start reordering:
        setupTheValuesAndStartReorderingFromPositionAndNumberingFrom(ReorderFrom.NEXT, 7);
        softAssertThatMessagePaneContainsMessages(
                MARKUP_NOT_SELECTED_ERROR_MESSAGE,
                STAT_PAGE_REORDER_CANCELLED_BY_EDITOR_ERROR_MESSAGE);

        // Select INITIAL_STAT_PAGE_POSITION STAT page markup (USPL page break markup).
        // Open STAT page reorder dialogue,
        // set values to "Reorder from" (NEXT STAT page) and "First reordered STAT page number" (7) fields,
        // start reordering:
        selectInitialStatPagePositionPageBreakMarkup();
        setupTheValuesAndStartReorderingFromPositionAndNumberingFrom(ReorderFrom.NEXT, 7);
        List<String> currentStatPagesListValues = getCurrentStatPagesListValues();
        // Assert that the first non reordered USPL page break markup is removed from list
        assertThat(currentStatPagesListValues.remove("STAT."))
                .as("Current document STAT pages list should contain non reordered STAT page")
                .isTrue();
        // Assert that USPL page break markups through the document contain STAT.nnn where nnn = 7,8,9...xxx
        assertThatUsplPageBreakMarkupsThroughTheDocumentContainCorrectValues(currentStatPagesListValues);
        softAssertThatMessagePaneContainsAllMessagesOfSuccessfulStatPagesReordering();
    }

    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void lastUsplPageBreakReorderFromNextSourceLegalTest()
    {
        // Scroll to last chunk and select last page break markup
        editorPage().switchToEditor();
        int lastChunk = editorPage().getChunkAmount();
        editorPage().scrollToChunk(lastChunk);
        editorTextPage()
                .getElements(format(LOADED_CHUNK, lastChunk - 1) + DESCENDANT_STAR_PAGE_ANCHOR + "/img")
                .get(1)
                .click();

        // Open STAT page reorder dialogue,
        // set values to "Reorder from" (NEXT STAT page) and "First reordered STAT page number" (1) fields,
        // start reordering:
        setupTheValuesAndStartReorderingFromPositionAndNumberingFrom(ReorderFrom.NEXT, 1);
        softAssertThatMessagePaneContainsMessages(
                LAST_INSTANCE_IN_THE_DOCUMENT_ERROR_MESSAGE,
                STAT_PAGE_REORDER_CANCELLED_BY_EDITOR_ERROR_MESSAGE);
    }

    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void cancelReorderSourceLegalTest()
    {
        // Open STAT page reorder dialogue,
        // set any value to "First reordered STAT page number" (ex.: "777") field,
        // cancel reordering:
        openStatPageReorderWindow();
        statPageReorderPage().setFirstReorderedStatPageNumber("777");
        statPageReorderPage().clickCancelButton();
        softAssertThatMessagePaneContainsMessages(STAT_PAGE_REORDER_CANCELLED_BY_USER_WARN_MESSAGE);
    }

//  ------------- Assistive methods: -------------

    private void setupTheValuesAndStartReorderingFromPositionAndNumberingFrom(
            ReorderFrom reorderStartingPosition, int reorderStartingNumber)
    {
        this.reorderStartingPosition = reorderStartingPosition;
        this.reorderStartingNumber = reorderStartingNumber;

        openStatPageReorderWindow();
        statPageReorderPage().setFirstReorderedStatPageNumber(valueOf(reorderStartingNumber));
        switch (reorderStartingPosition) {
            case TOP:
                statPageReorderPage().clickStartReorderFromTopRadioButton();
                break;
            case CURRENT:
                statPageReorderPage().clickStartReorderFromCurrentStatPageRadioButton();
                break;
            case NEXT:
                statPageReorderPage().clickStartReorderFromNextStatPageRadioButton();
                break;
        }
        statPageReorderPage().clickStartReorderButton();
    }

    private void softAssertThatMessagePaneContainsAllMessagesOfSuccessfulStatPagesReordering()
    {
        int reorderedStatPagesNumber = 0;
        int position = 0;
        int sequence = 0;
        switch (reorderStartingPosition)
        {
            case TOP:
                reorderedStatPagesNumber = TOTAL_DOCUMENT_STAT_PAGES_NUMBER;
                position = 1;
                sequence = TOTAL_DOCUMENT_STAT_PAGES_NUMBER - 1 + reorderStartingNumber;
                break;
            case CURRENT:
                reorderedStatPagesNumber = TOTAL_DOCUMENT_STAT_PAGES_NUMBER - INITIAL_STAT_PAGE_POSITION + 1;
                position = INITIAL_STAT_PAGE_POSITION;
                sequence = TOTAL_DOCUMENT_STAT_PAGES_NUMBER - INITIAL_STAT_PAGE_POSITION + reorderStartingNumber;
                break;
            case NEXT:
                reorderedStatPagesNumber = TOTAL_DOCUMENT_STAT_PAGES_NUMBER - INITIAL_STAT_PAGE_POSITION;
                position = INITIAL_STAT_PAGE_POSITION + 1;
                sequence = TOTAL_DOCUMENT_STAT_PAGES_NUMBER - INITIAL_STAT_PAGE_POSITION - 1 + reorderStartingNumber;
                break;
        }
        softAssertThatMessagePaneContainsMessages(
                format("Total number of STAT.page instances in document are: %d and the number of STAT.page instances reordered are: %d",
                        TOTAL_DOCUMENT_STAT_PAGES_NUMBER, reorderedStatPagesNumber),
                format("Starting from the \"%s\" position with first STAT.page instance reordered being at position \"%d\"",
                        reorderStartingPosition.name().toLowerCase(), position),
                format("With the start value being '%d' and the last STAT.page instance following the sequence is '%d'",
                        reorderStartingNumber, sequence)
        );
    }

    private List<String> getCurrentStatPagesListValues()
    {
        editorPage().switchToEditor();
        int chunkAmount = editorPage().getChunkAmount();

        List<String> currentStatPagesListValues = new ArrayList<>();
        for (int i = 1; i <= chunkAmount; i++)
        {
            editorPage().switchToEditorAndScrollToChunk(i);
            currentStatPagesListValues.addAll(editorTextPage()
                    .getElementsTextList(format(LOADED_CHUNK, i - 1) + DESCENDANT_STAR_PAGE_ANCHOR));
        }
        return currentStatPagesListValues;
    }

    private void assertThatUsplPageBreakMarkupsThroughTheDocumentContainCorrectValues(List<String> currentStatPagesListValues)
    {
        for (int i = 1; i <= currentStatPagesListValues.size(); i++) {
            String pageBreakMarkupValue = format("STAT.%d", i - 1 + reorderStartingNumber);
            assertThat(currentStatPagesListValues.get(i - 1).equals(pageBreakMarkupValue))
                    .as(format("USPL page break markups through the document should contain %s", pageBreakMarkupValue))
                    .isTrue();
        }
    }

    private void openStatPageReorderWindow()
    {
        editorPage().switchToEditor();
        editorToolbarPage().clickInsertStatPages();
        statPageReorderPage().switchToStatPageReorderWindow();
        assertThat(statPageReorderPage().getTitle())
                .as(format("Page with title \"%s\" should be appeared", PAGE_TITLE))
                .isEqualTo(PAGE_TITLE);
    }

    private void softAssertThatMessagePaneContainsMessages(String... messages)
    {
        SoftAssertions softAssertions = new SoftAssertions();
        editorPage().switchToEditor();
        Arrays.stream(messages).forEach(message -> softAssertions.assertThat(editorMessagePage().checkMessage(message))
                .as(format("The %s message should be appeared in the editor message pane", message))
                .isTrue());
        softAssertions.assertAll();
    }

    private void selectInitialStatPagePositionPageBreakMarkup()
    {
        // INITIAL_STAT_PAGE_POSITION = 1 and page break markup with this position is in the first chunk
        editorPage().switchToEditorAndScrollToChunk(SECOND_CHUNK);
        editorTextPage().click(format(LOADED_CHUNK, SECOND_CHUNK - 1) + DESCENDANT_STAR_PAGE_ANCHOR + "/img");
    }

    enum ReorderFrom
    {
        TOP,
        CURRENT,
        NEXT
    }
}
