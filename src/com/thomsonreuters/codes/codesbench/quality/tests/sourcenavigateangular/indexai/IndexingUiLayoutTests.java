package com.thomsonreuters.codes.codesbench.quality.tests.sourcenavigateangular.indexai;

import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.EDGE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.indexai.IndexingFragmentsPageElements.SELECTED_FRAGMENT_CONTAINS_TEXT;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.indexai.IndexingPageElements.*;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.popups.SourceNavigateAngularPopUpPageElements.*;
import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Feature 725254: CWB Index UI Redesign [HALCYONST-17476]
 */
public class IndexingUiLayoutTests extends IndexingUiBase
{
    @BeforeEach
    public void accessIndexingUi()
    {
        findSingleApvRenditionForIndexing(UUID_HAS_SUGGESTIONS);
        accessIndexingUiViaContextMenu();
    }

    @Test
    @DisplayName("Indexing UI Layout Redesign: Boxes")
    @EDGE
    @LEGAL
    @LOG
    public void verifyAllBoxesAreDisplayedAndOutlineColorOfActiveBoxesIsBlueTest()
    {
        assertThatIndexingWindowAppears();

        List<String> tabsNames = Arrays
                .asList(
                        "Session Law",
                        "Fragments",
                        "Suggested Index Entries",
                        "Draft Index Entry",
                        "Final Index Entries"
                );
        tabsNames
                .forEach(
                        s -> assertThat(indexingPage().isBoxDisplayed(s))
                                .as("'%s' box should be displayed", s)
                                .isTrue()
                );

        List<String> indexingBoxes = Arrays
                .asList(
                        SESSION_LAW_BOX,
                        FRAGMENTS_BOX,
                        SUGGESTED_INDEX_ENTRIES_BOX,
                        DRAFT_INDEX_ENTRY_BOX,
                        FINAL_INDEX_ENTRIES_BOX
                );
        indexingBoxes
                .forEach(s -> {
                    assertThatOutlineIsOfSpecifiedColor(s, BLACK, "grey unless the box is selected");
                    indexingPage().click(s);
                    assertThatOutlineIsOfSpecifiedColor(s, BLUE, "blue after clicking");
                    indexingPage().click(format(UI_TITLE_PATTERN, INDEXING));
                    assertThatOutlineIsOfSpecifiedColor(s, BLACK, "grey unless the box is selected");
                }
                );

    }

    @Test
    @DisplayName("Indexing UI Layout Redesign: Keyboard Navigation")
    @EDGE
    @LEGAL
    @LOG
    public void verifyAllEnabledItemsAreNavigatedByTabTest()
    {
        List<String> indexingItems = Arrays.asList(
                PRINT_PREVIEW_BUTTON,
                OPEN_IN_EDITOR_BUTTON,
                SESSION_LAW_BOX,
                FRAGMENTS_BOX,
                SUGGESTED_INDEX_ENTRIES_BOX,
                DRAFT_INDEX_ENTRY_BOX,
                PLUS_DRAFT_INDEX_ENTRY_BUTTON,
                FINAL_INDEX_ENTRIES_BOX,
                SAVE_EXIT_FINAL_INDEX_ENTRIES_BUTTON
                );

        indexingPage().switchToNextFocusableElement();
        indexingItems
                .forEach(
                        s -> {
                            assertThatOutlineIsOfSpecifiedColor(s, BLUE, "blue after pressing Tab");
                            if (s.contains(BUTTON) && !s.contains("Exit"))
                            {
                                assertThatElementHasTooltip(s, BUTTON_TOOLTIP_WINDOW);
                                assertThatButtonTooltipIsAsExpected(s);
                            }
                            indexingPage().switchToNextFocusableElement();
                            assertThatOutlineColorIsNotBlue(s);
                        }
                );
    }

    @Test
    @DisplayName("Indexing UI Layout Redesign: disabled buttons")
    @EDGE
    @LEGAL
    @LOG
    public void verifySpecifiedButtonsAreDisabledTest()
    {
        List<String> indexingItems = Arrays.asList(
                EDIT_SUGGESTED_INDEX_ENTRY_BUTTON,
                SAVE_SUGGESTED_INDEX_ENTRY_BUTTON,
                SWAP_DRAFT_INDEX_ENTRY_BUTTON,
                REMOVE_DRAFT_INDEX_ENTRY_BUTTON,
                SAVE_DRAFT_INDEX_ENTRY_BUTTON,
                EDIT_FINAL_INDEX_ENTRIES_BUTTON,
                REMOVE_FINAL_INDEX_ENTRIES_BUTTON
        );

        indexingItems
                .forEach(
                        s -> {
                            assertThatButtonIsDisabled(s);
                            indexingPage().hoverElement(s);
                            assertThatElementDoesNotHaveTooltip();
                        }
                );
    }

    //Bug 724888: Indexing: Tab key control does not work if the 'Fragment' box is active with an item selected
    @Test
    @DisplayName("Indexing UI Layout Redesign: Tab when the fragment is selected")
    @EDGE
    @LEGAL
    @LOG
    public void verifyTabWorksIfFragmentIsSelectedTest()
    {
        indexingPage().click(FRAGMENTS_BOX);

        String fragmentName = indexingFragmentsPage().getRandomFragmentName();

        indexingFragmentsPage().typeTextToLocateFragment(fragmentName);
        indexingFragmentsPage().clickFragmentOnce(fragmentName);
        assertThatSelectedFragmentContainsTypedCharCombination(fragmentName);
        assertThatSelectedItemsTextIsBold(format(SELECTED_FRAGMENT_CONTAINS_TEXT, fragmentName));
        assertThatFragmentBeforeSelectedDoesNotContainCharCombination(fragmentName);

        indexingPage().switchToNextFocusableElement();
        assertThatOutlineColorIsNotBlue(FRAGMENTS_BOX);
        assertThatOutlineIsOfSpecifiedColor(SUGGESTED_INDEX_ENTRIES_BOX, BLUE, "blue after pressing Tab");

    }
}
