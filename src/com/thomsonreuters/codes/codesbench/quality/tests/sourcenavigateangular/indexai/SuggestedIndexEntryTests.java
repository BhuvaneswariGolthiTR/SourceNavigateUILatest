package com.thomsonreuters.codes.codesbench.quality.tests.sourcenavigateangular.indexai;

import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.EDGE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.SourceNavigateAngularPageElements.*;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.SourceNavigateAngularViewManagerPageElements.*;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.indexai.IndexingFragmentsPageElements.*;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.indexai.IndexingFragmentsPageElements.SECONDARY_FRAGMENT;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.indexai.IndexingPageElements.*;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.indexai.IndexingSuggestedIndexEntriesPageElements.*;
import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Feature 725254: CWB Index UI Redesign [HALCYONST-17476]
 * Bug 724855: Indexing UI: Suggested Index Entries Lightbulb Not Appearing
 */
public class SuggestedIndexEntryTests extends IndexingUiBase
{
    private List<String> suggestedIndexEntriesList;

    private List<String> aiIndexEntryCompletedBy;

    @BeforeEach
    public void accessIndexingUi(TestInfo testInfo)
    {
        if (!sourceNavigateAngularPage().getActiveViewName(RENDITIONS_ACTIVE_VIEW_NAME).contains(INDEX_AI_TEST_VIEW_DROPDOWN))
        {
            sourceNavigateAngularViewManagerPage().applyExistingView(INDEX_AI_TEST_VIEW_DROPDOWN);
        }

        switch (testInfo.getDisplayName().split(" -- ")[1]) {
            case "no suggestions":
                findSingleApvRenditionForIndexing(UUID_NO_SUGGESTIONS);
                break;
            case "backup AI model and no primary fragments":
                findSingleApvRenditionForIndexing(UUID_BACKUP_AI_NO_PRIMARY);
                break;
            case "backup AI model and primary fragments exist":
                findSingleApvRenditionForIndexing(UUID_BACKUP_AI_PRIMARY_EXIST);
                break;
            case "double dash":
                findSingleApvRenditionForIndexing(UUID_DOUBLE_DASH);
                break;
            default:
                findSingleApvRenditionForIndexing(UUID_HAS_SUGGESTIONS);
        }
        aiIndexEntryCompletedBy = sourceNavigateAngularPage().getCellValue(RENDITIONS_GRID_CONTAINER, COLUMN_CELL_VALUE_OF_RENDITION_ROW_PATTERN, "aiIndexEntryCompletedBy");

        accessIndexingUiViaContextMenu();
    }

    @Test
    @DisplayName("Indexing UI Suggested Index Entry Box: layout -- no suggestions")
    @EDGE
    @LEGAL
    @LOG
    public void verifySuggestedIndexEntriesLayoutNoSuggestedEntriesTest()
    {
        assertThat(aiIndexEntryCompletedBy)
                .as("The \"AI Index Entry Completed By\" should be empty for this Rendition")
                .containsOnly("");

        indexingPage().click(SUGGESTED_INDEX_ENTRIES_BOX);
        assertThat(indexingSuggestedIndexEntriesPage().getElementsText(NO_SUGGESTED_INDEX_ENTRIES_PLACEHOLDER))
                .as("There should be a message that there are no eligible fragments for use")
                .contains("Suggested fragments are all ineligible for use as a heading");

        assertThatLightBulbIsNotPresent();
    }

    @Test
    @DisplayName("Indexing UI Suggested Index Entry box: layout -- suggestions exist")
    @EDGE
    @LEGAL
    @LOG
    public void verifySuggestedIndexEntriesLayoutSuggestedEntriesTest()
    {
        assertThatSuggestionsAreMadeBySpecifiedAiModel(aiIndexEntryCompletedBy, "Primary AI Model");

        List<String> primaryFragments = indexingFragmentsPage().getListOfFragments(PRIMARY_FRAGMENT);
        assertThatSuggestedFragmentsExist(primaryFragments, PRIMARY, PRIMARY_FRAGMENT);
        primaryFragments.forEach(s -> assertThatPrimarySuggestionsBackgroundColorIsGrey(format(PRIMARY_FRAGMENT_TEXT, s)));

        List<String> secondaryFragments = indexingFragmentsPage().getListOfFragments(SECONDARY_FRAGMENT);
        assertThatSuggestedFragmentsExist(secondaryFragments, SECONDARY, SECONDARY_FRAGMENT);

        assertThatEachSuggestedIndexEntryContainsOnlyOfSuggestedFragments(primaryFragments);
        assertThatEachSuggestedIndexEntryDoesNotContainSecondaryFragments(secondaryFragments);

        assertThatEachSuggestedIndexEntryHasCheckbox();
        assertThatEachSuggestedIndexEntryContainsDashes();

        assertThatLightBulbIsNotPresent();
    }

    @Test
    @DisplayName("Indexing UI Suggested Index Entry box: layout -- backup AI model and no primary fragments")
    @EDGE
    @LEGAL
    @LOG
    public void verifySuggestedIndexEntriesLayoutBackupModelNoPrimaryFragmentsTest()
    {
        assertThatSuggestionsAreMadeBySpecifiedAiModel(aiIndexEntryCompletedBy, "Backup AI Model");
        DateAndTimeUtils.takeNap(DateAndTimeUtils.THREE_SECONDS);
        assertThat(indexingFragmentsPage().doesElementExist(FRAGMENTS_NO_SUGGESTIONS))
                .as("The primary fragments should not be present for this Rendition")
                .isTrue();
        assertThatPrimarySuggestionsBackgroundColorIsGrey(FRAGMENTS_NO_SUGGESTIONS_BACKGROUND);

        List<String> secondaryFragments = indexingFragmentsPage().getListOfFragments(SECONDARY_FRAGMENT);
        assertThatSuggestedFragmentsExist(secondaryFragments, SECONDARY, SECONDARY_FRAGMENT);

        assertThatEachSuggestedIndexEntryContainsOnlyOfSuggestedFragments(secondaryFragments);
        assertThatOrangeLightBulbIsPresent();

        indexingPage().hoverElement(LIGHT_BULB);
        assertThatElementHasTooltip(LIGHT_BULB, LIGHT_BULB_TOOLTIP_WINDOW);
        assertThatHoveredLightBulbHasExpectedTooltip();

        assertThatEachSuggestedIndexEntryHasCheckbox();
        assertThatEachSuggestedIndexEntryContainsDashes();
    }

    @Test
    @DisplayName("Indexing UI Suggested Index Entry box: layout -- backup AI model and primary fragments exist")
    @EDGE
    @LEGAL
    @LOG
    public void verifySuggestedIndexEntriesLayoutBackupModelPrimaryFragmentsExistTest()
    {
        assertThatSuggestionsAreMadeBySpecifiedAiModel(aiIndexEntryCompletedBy, "Backup AI Model");

        List<String> primaryFragments = indexingFragmentsPage().getListOfFragments(PRIMARY_FRAGMENT);
        assertThatSuggestedFragmentsExist(primaryFragments, PRIMARY, PRIMARY_FRAGMENT);
        primaryFragments.forEach(s -> assertThatPrimarySuggestionsBackgroundColorIsGrey(format(PRIMARY_FRAGMENT_TEXT, s)));

        List<String> secondaryFragments = indexingFragmentsPage().getListOfFragments(SECONDARY_FRAGMENT);
        assertThatSuggestedFragmentsExist(secondaryFragments, SECONDARY, SECONDARY_FRAGMENT);

        List<String> suggestionsList = mergePrimarySecondaryFragmentsListsWithoutRepeats(primaryFragments, secondaryFragments);
        assertThatEachSuggestedIndexEntryContainsOnlyOfSuggestedFragments(suggestionsList);

        assertThatOrangeLightBulbIsPresent();

        indexingPage().hoverElement(LIGHT_BULB);
        assertThatElementHasTooltip(LIGHT_BULB, LIGHT_BULB_TOOLTIP_WINDOW);
        assertThatHoveredLightBulbHasExpectedTooltip();

        assertThatEachSuggestedIndexEntryHasCheckbox();
        assertThatEachSuggestedIndexEntryContainsDashes();
    }

    //Bug 724987: Indexing: there is no separating dash between the fragments in the 'Draft Index Entry' box
    @Test
    @DisplayName("Indexing UI Suggested Index Entry Box: separators between entries -- suggestions exist")
    @EDGE
    @LEGAL
    @LOG
    public void verifyIndexEntriesDisplayedWithDashesTest()
    {
        assertThatDraftIndexEntryBoxIsEmpty();

        suggestedIndexEntriesList = indexingSuggestedIndexEntriesPage().getListOfSuggestedEntries();
        assertThatEachSuggestedIndexEntryHasCheckbox();
        assertThatEachSuggestedIndexEntryContainsDashes();
        suggestedIndexEntriesList.forEach(this::assertThatSuggestedIndexEntryCheckboxIsNotSelected);

        String firstSuggestedIndexEntryName = suggestedIndexEntriesList.get(0);

        indexingSuggestedIndexEntriesPage().clickSuggestedEntry(firstSuggestedIndexEntryName);
        assertThatSuggestedIndexEntryIsSelected(firstSuggestedIndexEntryName);
        assertThatSelectedItemsTextIsBold(format(SELECTED_SUGGESTED_ENTRY, firstSuggestedIndexEntryName));
        assertThatSuggestedIndexEntryCheckboxIsSelected(firstSuggestedIndexEntryName);

        indexingSuggestedIndexEntriesPage().editSuggestedIndexEntry();

        List<String> suggestedWords = indexingSuggestedIndexEntriesPage()
                .splitSuggestedIndexEntryName(firstSuggestedIndexEntryName);
        assertThatDraftIndexEntryBoxContainsAllExpectedEntryElements(suggestedWords);

        indexingDraftIndexEntryPage().saveDraftIndexEntries();
        assertThatFragmentTokensArePresentInFinalIndexEntriesBoxInSpecifiedOrder(
                indexingFinalIndexEntriesPage()
                .getExpectedIndexEntry(suggestedWords)
        );

        assertThatDashIsPresentBetweenDraftIndexEntries(suggestedWords);

        indexingDraftIndexEntryPage().saveDraftIndexEntries();
        assertThatLastAddedFinalIndexEntryContainedOfTokensInDraftIndexEntryBox();

        String lastSuggestedIndexEntryName = suggestedIndexEntriesList.get(suggestedIndexEntriesList.size() - 1);

        indexingSuggestedIndexEntriesPage().clickSuggestedEntry(lastSuggestedIndexEntryName);
        assertThatSuggestedIndexEntryIsSelected(lastSuggestedIndexEntryName);
        assertThatSelectedItemsTextIsBold(format(SELECTED_SUGGESTED_ENTRY, lastSuggestedIndexEntryName));
        assertThatSuggestedIndexEntryCheckboxIsSelected(lastSuggestedIndexEntryName);

        indexingSuggestedIndexEntriesPage().saveSuggestedIndexEntry();

        assertThatLastAddedFinalIndexEntryIsEqualToSubmittedSuggestedIndexEntry(lastSuggestedIndexEntryName);
    }

    @Test
    @DisplayName("Indexing UI Suggested Index Entry Box: checkboxes -- suggestions exist")
    @EDGE
    @LEGAL
    @LOG
    public void verifyCheckboxesSuggestedIndexEntriesFunctionalityTest()
    {
        suggestedIndexEntriesList = indexingSuggestedIndexEntriesPage().getListOfSuggestedEntries();
        int count = suggestedIndexEntriesList.size();

        assertThatEachSuggestedIndexEntryHasCheckbox();
        assertThatEachSuggestedIndexEntryContainsDashes();
        suggestedIndexEntriesList.forEach(this::assertThatSuggestedIndexEntryCheckboxIsNotSelected);

        for (int i = 0; i < count; i++)
        {
            String listItem = suggestedIndexEntriesList.get(i);
            indexingSuggestedIndexEntriesPage().clickSuggestedEntry(listItem);
            assertThatSuggestedIndexEntryIsSelected(listItem);
            assertThatSelectedItemsTextIsBold(format(SELECTED_SUGGESTED_ENTRY, listItem));
            assertThatSuggestedIndexEntryCheckboxIsSelected(listItem);

            indexingSuggestedIndexEntriesPage().clickSuggestedEntry(listItem);
            assertThatSuggestedIndexEntryIsSelected(listItem);
            assertThatSelectedItemsTextIsBold(format(SELECTED_SUGGESTED_ENTRY, listItem));
            assertThatSuggestedIndexEntryCheckboxIsNotSelected(listItem);

            indexingSuggestedIndexEntriesPage().pressEnterOnSuggestedIndexEntry();
            assertThatSuggestedIndexEntryIsSelected(listItem);
            assertThatSelectedItemsTextIsBold(format(SELECTED_SUGGESTED_ENTRY, listItem));
            assertThatSuggestedIndexEntryCheckboxIsSelected(listItem);

            indexingSuggestedIndexEntriesPage().pressEnterOnSuggestedIndexEntry();
            assertThatSuggestedIndexEntryCheckboxIsNotSelected(listItem);
            assertThatSuggestedIndexEntryIsSelected(listItem);
            assertThatSelectedItemsTextIsBold(format(SELECTED_SUGGESTED_ENTRY, listItem));

            indexingSuggestedIndexEntriesPage().pressArrowDownOnSuggestedIndexEntry();
        }

        while (count > 0)
        {
            assertThatSuggestedIndexEntryCheckboxIsNotSelected(suggestedIndexEntriesList.get(count - 1));
            assertThatSuggestedIndexEntryIsSelected(suggestedIndexEntriesList.get(count - 1));
            assertThatSelectedItemsTextIsBold(format(SELECTED_SUGGESTED_ENTRY, suggestedIndexEntriesList.get(count - 1)));
            count--;
            indexingSuggestedIndexEntriesPage().pressArrowUpOnSuggestedIndexEntry();
        }

        indexingSuggestedIndexEntriesPage().pressEnterOnSuggestedIndexEntry();
        assertThatSuggestedIndexEntryCheckboxIsSelected(getFirstItemFromList(suggestedIndexEntriesList));
        assertThatSuggestedIndexEntryIsSelected(getFirstItemFromList(suggestedIndexEntriesList));
        assertThatSelectedItemsTextIsBold(format(SELECTED_SUGGESTED_ENTRY, getFirstItemFromList(suggestedIndexEntriesList)));

        indexingPage().switchToNextFocusableElement();
        indexingPage().pressEnterToActivateButton();

        assertThatDraftIndexEntryBoxContainsAllExpectedEntryElements(
                indexingSuggestedIndexEntriesPage()
                .splitSuggestedIndexEntryName(getFirstItemFromList(suggestedIndexEntriesList))
        );

        indexingDraftIndexEntryPage().saveDraftIndexEntries();
        assertThatLastAddedFinalIndexEntryContainedOfTokensInDraftIndexEntryBox();

        indexingSuggestedIndexEntriesPage().clickSuggestedEntry(getLastItemFromList(suggestedIndexEntriesList));
        assertThatSuggestedIndexEntryIsSelected(getLastItemFromList(suggestedIndexEntriesList));
        assertThatSelectedItemsTextIsBold(format(SELECTED_SUGGESTED_ENTRY, getLastItemFromList(suggestedIndexEntriesList)));
        assertThatSuggestedIndexEntryCheckboxIsSelected(getLastItemFromList(suggestedIndexEntriesList));
        assertThatSuggestedIndexEntryCheckboxIsSelected(getFirstItemFromList(suggestedIndexEntriesList));

        indexingSuggestedIndexEntriesPage().clickSuggestedEntry(getFirstItemFromList(suggestedIndexEntriesList));
        indexingSuggestedIndexEntriesPage().editSuggestedIndexEntry();
        assertThatDraftIndexEntryBoxContainsAllExpectedEntryElements(
                indexingSuggestedIndexEntriesPage()
                        .splitSuggestedIndexEntryName(getLastItemFromList(suggestedIndexEntriesList))
        );

        indexingSuggestedIndexEntriesPage().saveSuggestedIndexEntry();
        assertThatLastAddedFinalIndexEntryContainedOfTokensInDraftIndexEntryBox();
    }

    //User Story 725046: Indexing: Remove pop-up messages when pressing some action buttons and add the disable function for these buttons
    @Test
    @DisplayName("Indexing UI Suggested Index Entry Box: buttons -- suggestions exist")
    @EDGE
    @LEGAL
    @LOG
    public void verifyDisabledEnabledButtonsSuggestedIndexEntriesTest()
    {
        suggestedIndexEntriesList = indexingSuggestedIndexEntriesPage().getListOfSuggestedEntries();
        String firstSuggestedIndexEntry = getFirstItemFromList(suggestedIndexEntriesList);
        String lastSuggestedIndexEntry = getLastItemFromList(suggestedIndexEntriesList);

        List<String> buttons = List.of(EDIT_SUGGESTED_INDEX_ENTRY_BUTTON, SAVE_SUGGESTED_INDEX_ENTRY_BUTTON);

        assertThatEachSuggestedIndexEntryHasCheckbox();
        assertThatEachSuggestedIndexEntryContainsDashes();
        suggestedIndexEntriesList.forEach(this::assertThatSuggestedIndexEntryCheckboxIsNotSelected);

        assertThatAllButtonsAreDisabled(buttons);

        indexingSuggestedIndexEntriesPage().clickSuggestedEntry(firstSuggestedIndexEntry);
        assertThatSuggestedIndexEntryCheckboxIsSelected(firstSuggestedIndexEntry);

        assertThatAllButtonsAreEnabled(buttons);

        indexingSuggestedIndexEntriesPage().editSuggestedIndexEntry();
        assertThatDraftIndexEntryBoxContainsAllExpectedEntryElements(
                indexingSuggestedIndexEntriesPage().splitSuggestedIndexEntryName(firstSuggestedIndexEntry)
        );

        indexingSuggestedIndexEntriesPage().saveSuggestedIndexEntry();
        assertThatLastAddedFinalIndexEntryIsEqualToSubmittedSuggestedIndexEntry(firstSuggestedIndexEntry);

        indexingSuggestedIndexEntriesPage().clickSuggestedEntry(lastSuggestedIndexEntry);
        assertThatSuggestedIndexEntryCheckboxIsSelected(firstSuggestedIndexEntry);
        assertThatSuggestedIndexEntryCheckboxIsSelected(lastSuggestedIndexEntry);

        assertThatButtonIsDisabled(EDIT_SUGGESTED_INDEX_ENTRY_BUTTON);
        assertThatButtonIsEnabled(SAVE_SUGGESTED_INDEX_ENTRY_BUTTON);

        indexingPage().hoverElement(EDIT_SUGGESTED_INDEX_ENTRY_BUTTON);
        assertThatElementDoesNotHaveTooltip();

        indexingPage().hoverElement(SAVE_SUGGESTED_INDEX_ENTRY_BUTTON);
        assertThatElementHasTooltip(SAVE_SUGGESTED_INDEX_ENTRY_BUTTON, BUTTON_TOOLTIP_WINDOW);
        assertThatButtonTooltipIsAsExpected(SAVE_SUGGESTED_INDEX_ENTRY_BUTTON);

        indexingSuggestedIndexEntriesPage().clickSuggestedEntry(firstSuggestedIndexEntry);
        indexingSuggestedIndexEntriesPage().clickSuggestedEntry(lastSuggestedIndexEntry);

        assertThatAllButtonsAreDisabled(buttons);
    }

    //Bug 736957: Indexing: getting an error when trying to save suggested entries with double dashes instead
    @Test
    @DisplayName("Indexing UI Suggested Index Entry Box: saving suggested entries with double dashes -- double dash")
    @EDGE
    @LEGAL
    @LOG
    public void verifySavingSuggestedIndexEntriesWithDoubleDashesTest()
    {
        indexingPage().click(FINAL_INDEX_ENTRIES_BOX);
        if (indexingFinalIndexEntriesPage().getNumberOfFinalEntries() != 0)
        {
            indexingFinalIndexEntriesPage().clearFinalIndexEntryBox();
        }
        assertThatFinalIndexEntriesBoxIsEmpty();

        suggestedIndexEntriesList = indexingSuggestedIndexEntriesPage().getListOfSuggestedEntries();
        String firstSuggestedIndexEntry = getFirstItemFromList(suggestedIndexEntriesList);

        assertThat(indexingSuggestedIndexEntriesPage().splitSuggestedIndexEntryName(firstSuggestedIndexEntry))
                .as("The suggested index entry picked for selection should have double dash")
                .anySatisfy(s -> assertThat(s).contains("--"));

        indexingSuggestedIndexEntriesPage().clickSuggestedEntry(firstSuggestedIndexEntry);
        assertThatSuggestedIndexEntryCheckboxIsSelected(firstSuggestedIndexEntry);

        indexingSuggestedIndexEntriesPage().saveSuggestedIndexEntry();
        assertThatLastAddedFinalIndexEntryIsEqualToSubmittedSuggestedIndexEntry(firstSuggestedIndexEntry);

        indexingFinalIndexEntriesPage().saveExitFinalIndexEntry();
        assertThatIndexingUiIsClosed();
        assertThatToastMessageDoesNotKickOff();

        accessIndexingUiViaContextMenu();
        assertThatLastAddedFinalIndexEntryIsEqualToSubmittedSuggestedIndexEntry(firstSuggestedIndexEntry);
        indexingFinalIndexEntriesPage().clearFinalIndexEntryBox();

        indexingSuggestedIndexEntriesPage().clickSuggestedEntry(firstSuggestedIndexEntry);
        assertThatSuggestedIndexEntryCheckboxIsSelected(firstSuggestedIndexEntry);

        indexingSuggestedIndexEntriesPage().editSuggestedIndexEntry();
        assertThatDraftIndexEntryBoxContainsAllExpectedEntryElements(indexingSuggestedIndexEntriesPage().splitSuggestedIndexEntryName(firstSuggestedIndexEntry));
        indexingDraftIndexEntryPage().saveDraftIndexEntries();
        assertThatLastAddedFinalIndexEntryContainedOfTokensInDraftIndexEntryBox();

        indexingFinalIndexEntriesPage().saveExitFinalIndexEntry();
        assertThatIndexingUiIsClosed();
        assertThatToastMessageDoesNotKickOff();

        accessIndexingUiViaContextMenu();
        assertThatLastAddedFinalIndexEntryIsEqualToSubmittedSuggestedIndexEntry(firstSuggestedIndexEntry);
        indexingFinalIndexEntriesPage().clearFinalIndexEntryBox();
        assertThatFinalIndexEntriesBoxIsEmpty();
        indexingFinalIndexEntriesPage().saveExitFinalIndexEntry();
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);

        accessIndexingUiViaContextMenu();
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        assertThatFinalIndexEntriesBoxIsEmpty();
    }

    // ---------- Assistive Methods ----------

    private List<String> mergePrimarySecondaryFragmentsListsWithoutRepeats(List<String> primaryFragments, List<String> secondaryFragments)
    {
        List<String> suggestionsList = new ArrayList<>(primaryFragments);
        for (String fragment : secondaryFragments)
        {
            if (!suggestionsList.contains(fragment))
            {
                suggestionsList.add(fragment);
            }
        }
        return suggestionsList;
    }
}
