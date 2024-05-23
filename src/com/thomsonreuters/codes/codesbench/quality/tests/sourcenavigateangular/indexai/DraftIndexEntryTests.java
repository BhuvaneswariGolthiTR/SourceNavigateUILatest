package com.thomsonreuters.codes.codesbench.quality.tests.sourcenavigateangular.indexai;

import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.EDGE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.SourceNavigateAngularPageElements.TOAST_MESSAGE;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.indexai.IndexingDraftIndexEntryPageElements.*;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.indexai.IndexingFragmentsPageElements.*;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.indexai.IndexingPageElements.*;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.indexai.IndexingSuggestedIndexEntriesPageElements.*;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.popups.SourceNavigateAngularPopUpPageElements.*;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.popups.SourceNavigateAngularToastPageElements.ADD_EXISTING_FRAGMENT;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.popups.SourceNavigateAngularToastPageElements.DISPLAYING_MULTIPLE_AND_DUPLICATE_RENDITIONS;
import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Feature 725254: CWB Index UI Redesign [HALCYONST-17476]
 * Bug 724792: Unusual Behavior Using Spaces in Index Entry Components
 * Bug 724965: Indexing: there is no popup message when trying to add the same fragment which already been added to the 'Draft Index Entry' box
 * Bug 725104: Indexing: Draft Index Entry: the fragment should be displayed in the "Spellcheck" popup message with a first lowercase letter
 * Bug 724987: Indexing: there is no separating dash between the fragments in the 'Draft Index Entry' box
 * User Story 725046: Indexing: Remove pop-up messages when pressing some action buttons and add the disable function for these buttons
 */
public class DraftIndexEntryTests extends IndexingUiBase
{
    private static final String SPACES = "   ";

    private static final int FIRST_ENTRY = 0;
    private static final int SECOND_ENTRY = 1;
    private static final int THIRD_ENTRY = 2;
    private static final int FOURTH_ENTRY = 3;

    private static final int TOTAL_ENTRIES_NUMBER = 3;

    private List<String> itemsList;

    @BeforeEach
    public void accessIndexingUi()
    {
        findSingleApvRenditionForIndexing(UUID_HAS_SUGGESTIONS);
        accessIndexingUiViaContextMenu();
    }

    @Test
    @DisplayName("Indexing UI Draft Index Entry Box: adding of draft entries to the Final Index Entry box")
    @EDGE
    @LEGAL
    @LOG
    public void verifyDraftIndexEntryAddedToFinalIndexEntryTest()
    {
        itemsList = indexingFragmentsPage().makeListOfRandomFragments(TOTAL_ENTRIES_NUMBER);

        itemsList
                .forEach(
                        s -> {
                            indexingPage().click(DRAFT_INDEX_ENTRY_BOX);
                            assertThatDraftIndexEntryBoxIsEmpty();

                            indexingDraftIndexEntryPage().enterDraftIndexEntryAndMoveToFinalIndexEntries(s);
                            assertThatEntryIsPresentInDraftIndexEntryBox(indexingDraftIndexEntryPage().getTextOfSpecificDraftIndexEntry(1));
                            assertThatFragmentTokensArePresentInFinalIndexEntriesBoxInSpecifiedOrder(s);

                            indexingDraftIndexEntryPage().deleteDraftIndexEntries();
                            assertThatDraftIndexEntryBoxIsEmpty();
                        }
                );
    }

    @Test
    @DisplayName("Indexing UI Draft Index Entry Box: processing of spaces")
    @EDGE
    @LEGAL
    @LOG
    public void verifyDraftIndexEntryProcessesSpacesCorrectlyTest()
    {
        itemsList = Arrays.asList(ABATEMENT, HOLIDAYS, ENTRY_SPELLCHECK_DEDUSHKA, ABATEMENT_OF_ACTIONS_AND_PROCEEDINGS);
        String finalIndexEntry;

        assertThatDraftIndexEntryBoxIsEmpty();

        indexingDraftIndexEntryPage().enterDraftIndexEntryWithoutMovingToFinalIndexEntries(SPACES + itemsList.get(FIRST_ENTRY));
        assertThatEntryIsPresentInDraftIndexEntryBox(itemsList, ABATEMENT, FIRST_ENTRY);
        assertThatFinalIndexEntriesBoxIsEmpty();

        indexingDraftIndexEntryPage().enterDraftIndexEntryAndMoveToFinalIndexEntries(SPACES + itemsList.get(SECOND_ENTRY));
        finalIndexEntry = ABATEMENT + DASH + HOLIDAYS;
        assertThatEntryIsPresentInDraftIndexEntryBox(itemsList, HOLIDAYS, SECOND_ENTRY);
        assertThatFragmentTokensArePresentInFinalIndexEntriesBoxInSpecifiedOrder(finalIndexEntry);

        indexingDraftIndexEntryPage().enterDraftIndexEntryAndMoveToFinalIndexEntries(SPACES + itemsList.get(THIRD_ENTRY));
        assertThatSpellcheckUiKicksOff();
        assertThatItemInSpellcheckUiIsEqualToEnteredDraftIndexEntry(ENTRY_SPELLCHECK_DEDUSHKA);
        sourceNavigateAngularPopUpPage().confirmAction();
        assertThatEntryIsPresentInDraftIndexEntryBox(itemsList, ENTRY_SPELLCHECK_DEDUSHKA, THIRD_ENTRY);
        finalIndexEntry += DASH + ENTRY_SPELLCHECK_DEDUSHKA;
        assertThatFragmentTokensArePresentInFinalIndexEntriesBoxInSpecifiedOrder(finalIndexEntry);

        indexingDraftIndexEntryPage()
                .enterDraftIndexEntryAndMoveToFinalIndexEntries(itemsList.get(FOURTH_ENTRY).replaceAll("\\W", SPACES));
        assertThatEntryIsPresentInDraftIndexEntryBox(itemsList, ABATEMENT_OF_ACTIONS_AND_PROCEEDINGS, FOURTH_ENTRY);
        finalIndexEntry += DASH + ABATEMENT_OF_ACTIONS_AND_PROCEEDINGS;
        assertThatFragmentTokensArePresentInFinalIndexEntriesBoxInSpecifiedOrder(finalIndexEntry);

        indexingDraftIndexEntryPage().deleteDraftIndexEntries();
        assertThatDraftIndexEntryBoxIsEmpty();

        indexingFinalIndexEntriesPage().clickSpecificFinalIndexEntry(finalIndexEntry);
        assertThatFinalIndexEntryIsHighlighted(finalIndexEntry);
        indexingFinalIndexEntriesPage().editFinalIndexEntry();

        assertThatSpecifiedFinalIndexEntryIsNotPresent(finalIndexEntry);

        itemsList
                .forEach(
                        s -> assertThatEntryIsPresentInDraftIndexEntryBox(itemsList, s, itemsList.indexOf(s))
                );
    }

    @Test
    @DisplayName("Indexing UI Draft Index Entry Box: toast message when adding already existed draft fragment")
    @EDGE
    @LEGAL
    @LOG
    public void verifyToastMessageAppearsWhenAddExistingFragmentToDraftIndexEntryBoxTest()
    {
        indexingPage().click(FRAGMENTS_BOX);

        assertThatDraftIndexEntryBoxIsEmpty();

        addRandomFragmentsToDraftIndexEntryBox();
        assertThatNumberOfDraftIndexEntryTokensIsEqualToNumberOfAddedFragments(TOTAL_ENTRIES_NUMBER);

        String existingFragment = getRandomListItem(itemsList);
        indexingFragmentsPage().doubleClickFragment(format(ANY_FRAGMENT_TEXT, existingFragment));

        assertThat(sourceNavigateAngularPage().getElementsText(TOAST_MESSAGE).contains(ADD_EXISTING_FRAGMENT))
                .as("Error message is not as expeced")
                .isTrue();
        assertThatDraftIndexEntryBoxContainsAllExpectedEntryElements(
                indexingDraftIndexEntryPage().getListOfAllTextDraftIndexEntries()
        );
        assertThatNumberOfDraftIndexEntryTokensIsEqualToNumberOfAddedFragments(TOTAL_ENTRIES_NUMBER);
    }

    @Test
    @DisplayName("Indexing UI Draft Index Entry Box: spellcheck functionality")
    @EDGE
    @LEGAL
    @LOG
    public void verifyDraftIndexEntrySpellcheckTest()
    {
        assertThatDraftIndexEntryBoxIsEmpty();

        indexingDraftIndexEntryPage().enterDraftIndexEntryWithoutMovingToFinalIndexEntries(ENTRY_SPELLCHECK_DEDUSHKA);
        assertThatSpellcheckUiKicksOff();
        assertThatItemInSpellcheckUiIsEqualToEnteredDraftIndexEntry(ENTRY_SPELLCHECK_DEDUSHKA);

        sourceNavigateAngularPopUpPage().confirmAction();

        assertThatEntryIsPresentInDraftIndexEntryBox(ENTRY_SPELLCHECK_DEDUSHKA);
        assertThatFinalIndexEntriesBoxIsEmpty();

        indexingDraftIndexEntryPage().saveDraftIndexEntries();
        assertThatFragmentTokensArePresentInFinalIndexEntriesBoxInSpecifiedOrder(ENTRY_SPELLCHECK_DEDUSHKA);

        indexingDraftIndexEntryPage().enterDraftIndexEntryWithoutMovingToFinalIndexEntries(ENTRY_SPELLCHECK_BANANABOOM);
        assertThatSpellcheckUiKicksOff();
        assertThatItemInSpellcheckUiIsEqualToEnteredDraftIndexEntry(ENTRY_SPELLCHECK_BANANABOOM);

        sourceNavigateAngularPopUpPage().cancelAction();

        assertThatSpellcheckUiClosed();
        assertThatDraftIndexEntryIsInEditMode(ENTRY_SPELLCHECK_BANANABOOM);

        indexingPage().click(DRAFT_INDEX_ENTRY_BOX);
        indexingDraftIndexEntryPage().waitForElementExists(format(UI_HEADING_SECOND_LEVEL, INDEXING, SPELLCHECK));
        assertThatSpellcheckUiKicksOff();
        assertThatItemInSpellcheckUiIsEqualToEnteredDraftIndexEntry(ENTRY_SPELLCHECK_BANANABOOM);

        sourceNavigateAngularPopUpPage().cancelAction();

        indexingDraftIndexEntryPage().clearDraftEntryField(ENTRY_SPELLCHECK_BANANABOOM.length());
        indexingDraftIndexEntryPage().deleteDraftIndexEntries();
        assertThatDraftIndexEntryBoxIsEmpty();

        indexingDraftIndexEntryPage().enterDraftIndexEntryWithoutMovingToFinalIndexEntries(ABATEMENT_OF_ACTIONS_AND_PROCEEDINGS);
        indexingDraftIndexEntryPage().doubleClickDraftEntry(format(INPUT_DRAFT_INDEX_ENTRY_BY_TEXT, ABATEMENT_OF_ACTIONS_AND_PROCEEDINGS));
        assertThatDraftIndexEntryIsInEditMode(ABATEMENT_OF_ACTIONS_AND_PROCEEDINGS);

        indexingDraftIndexEntryPage().highlightEndingCharactersInsideDraftEntry(5);
        indexingDraftIndexEntryPage().sendKeys(ENTRY_SPELLCHECK_DEDUSHKA);
        indexingDraftIndexEntryPage().moveCursorToBeginningOfFragment();
        indexingDraftIndexEntryPage().highlightBeginningCharactersInsideDraftEntry(5);
        indexingDraftIndexEntryPage().sendKeys(ENTRY_SPELLCHECK_BANANABOOM);
        indexingDraftIndexEntryPage().sendKeys(Keys.ENTER);

        indexingDraftIndexEntryPage().getMisspelledWords()
                .forEach(
                        s -> assertThat(s)
                                .as("Only misspelled words should display in the Spellcheck UI")
                                .isNotEqualTo("of")
                                .isNotEqualTo("actions")
                                .isNotEqualTo("and")
                                .containsAnyOf(ENTRY_SPELLCHECK_DEDUSHKA, ENTRY_SPELLCHECK_BANANABOOM)
                );

        sourceNavigateAngularPopUpPage().confirmAction();
    }

    @Test
    @DisplayName("Indexing UI Draft Index Entry Box: deleting")
    @EDGE
    @LEGAL
    @LOG
    public void verifyDeletingButtonDraftIndexEntryTest()
    {
        indexingPage().click(FRAGMENTS_BOX);
        assertThatDraftIndexEntryBoxIsEmpty();

        addRandomFragmentsToDraftIndexEntryBox();
        indexingDraftIndexEntryPage().deleteDraftIndexEntries();
        assertThatDraftIndexEntryBoxIsEmpty();

        addRandomFragmentsToDraftIndexEntryBox();
        selectAllDraftIndexEntriesExceptLastOne();
        assertThat(indexingDraftIndexEntryPage().doesElementExist(
                format(SELECTED_DRAFT_INDEX_ENTRY, TOTAL_ENTRIES_NUMBER, getLastItemFromList(itemsList)))
        )
                .as("The token should NOT be selected")
                .isFalse();
        assertThatDraftIndexEntryBorderIsOfSpecifiedColor(
                getLastItemFromList(itemsList),
                BLACK_COLOR_NAME,
                BLACK
        );

        indexingDraftIndexEntryPage().deleteDraftIndexEntries();
        assertThatOnlySelectedDraftIndexEntriesAreDeleted(TOTAL_ENTRIES_NUMBER, itemsList);

        assertThatOnlyOneDraftIndexEntryIsPresent(getLastItemFromList(itemsList));
    }

    @Test
    @DisplayName("Indexing UI Draft Index Entry Box: deleting from the keyboard")
    @EDGE
    @LEGAL
    @LOG
    public void verifyDeletingFromKeyboardDraftIndexEntryTest()
    {
        List<Keys> keysList = List.of(Keys.DELETE, Keys.BACK_SPACE);

        keysList
                .forEach(
                        s -> {
                            indexingPage().click(FRAGMENTS_BOX);
                            assertThatDraftIndexEntryBoxIsEmpty();

                            addRandomFragmentsToDraftIndexEntryBox();
                            indexingPage().click(DRAFT_INDEX_ENTRY_BOX);
                            indexingDraftIndexEntryPage().sendKeys(s);
                            assertThat(indexingDraftIndexEntryPage().getNumberOfDraftIndexEntries())
                                    .as("The draft index entries should not be deleted unless they are selected")
                                    .isEqualTo(itemsList.size());

                            selectAllDraftIndexEntriesExceptLastOne();

                            indexingDraftIndexEntryPage().sendKeys(s);
                            assertThatOnlySelectedDraftIndexEntriesAreDeleted(TOTAL_ENTRIES_NUMBER, itemsList);

                            assertThatOnlyOneDraftIndexEntryIsPresent(getLastItemFromList(itemsList));

                            indexingDraftIndexEntryPage().deleteDraftIndexEntries();
                        }
                );
    }

    @Test
    @DisplayName("Indexing UI Draft Index Entry Box: separators between draft entries")
    @EDGE
    @LEGAL
    @LOG
    public void verifyDraftIndexEntriesDisplayedWithDashesTest()
    {
        assertThatDraftIndexEntryBoxIsEmpty();
        indexingPage().click(FRAGMENTS_BOX);

        String fragmentName = indexingFragmentsPage().getRandomFragmentName();
        indexingFragmentsPage().pressEnterFragment(format(ANY_FRAGMENT_TEXT, fragmentName));
        assertThatEntryIsPresentInDraftIndexEntryBox(fragmentName);
        assertThatElementAfterSingleOrLastDraftIndexEntryIsNotPresent();
        indexingDraftIndexEntryPage().deleteDraftIndexEntries();

        addRandomFragmentsToDraftIndexEntryBox();
        assertThatDashIsPresentBetweenDraftIndexEntries(itemsList);

        indexingDraftIndexEntryPage().saveDraftIndexEntries();
        assertThatLastAddedFinalIndexEntryContainedOfTokensInDraftIndexEntryBox();
    }

    @Test
    @DisplayName("Indexing UI Draft Index Entry Box: adding, selecting and unselecting fragments")
    @EDGE
    @LEGAL
    @LOG
    public void verifyAddingSelectingUnselectingFragmentsToDraftIndexEntryTest()
    {
        indexingPage().click(FRAGMENTS_BOX);
        assertThatDraftIndexEntryBoxIsEmpty();
        addRandomFragmentsToDraftIndexEntryBox();
        indexingDraftIndexEntryPage().saveDraftIndexEntries();
        assertThatFragmentTokensArePresentInFinalIndexEntriesBoxInSpecifiedOrder(indexingFinalIndexEntriesPage().getExpectedIndexEntry(itemsList));

        String firstSuggestedIndexEntryName = getFirstItemFromList(indexingSuggestedIndexEntriesPage().getListOfSuggestedEntries());

        indexingSuggestedIndexEntriesPage().clickSuggestedEntry(firstSuggestedIndexEntryName);
        assertThatSuggestedIndexEntryIsSelected(firstSuggestedIndexEntryName);
        assertThatSelectedItemsTextIsBold(format(SELECTED_SUGGESTED_ENTRY, firstSuggestedIndexEntryName));

        assertThatSuggestedIndexEntryCheckboxIsSelected(firstSuggestedIndexEntryName);

        indexingSuggestedIndexEntriesPage().editSuggestedIndexEntry();

        List<String> suggestedWords = indexingSuggestedIndexEntriesPage().splitSuggestedIndexEntryName(firstSuggestedIndexEntryName);

        assertThatDraftIndexEntryBoxContainsAllExpectedEntryElements(suggestedWords);

        indexingSuggestedIndexEntriesPage().clickSuggestedEntry(firstSuggestedIndexEntryName);
        assertThatSuggestedIndexEntryCheckboxIsNotSelected(firstSuggestedIndexEntryName);
        assertThatSelectedItemsTextIsBold(format(SELECTED_SUGGESTED_ENTRY, firstSuggestedIndexEntryName));

        String secondSuggestedIndexEntryName = indexingSuggestedIndexEntriesPage().getListOfSuggestedEntries().get(1);

        indexingSuggestedIndexEntriesPage().clickSuggestedEntry(secondSuggestedIndexEntryName);
        assertThatSuggestedIndexEntryCheckboxIsSelected(secondSuggestedIndexEntryName);
        assertThatItemsTextIsNotBold(format(SUGGESTED_ENTRY_TEXT, firstSuggestedIndexEntryName));
        assertThatSelectedItemsTextIsBold(format(SELECTED_SUGGESTED_ENTRY, secondSuggestedIndexEntryName));

        indexingSuggestedIndexEntriesPage().editSuggestedIndexEntry();

        suggestedWords = indexingSuggestedIndexEntriesPage().splitSuggestedIndexEntryName(secondSuggestedIndexEntryName);

        assertThatDraftIndexEntryBoxContainsAllExpectedEntryElements(suggestedWords);

        indexingFinalIndexEntriesPage().clickSpecificFinalIndexEntry(indexingFinalIndexEntriesPage().getExpectedIndexEntry(itemsList));
        assertThatFinalIndexEntryIsHighlighted(indexingFinalIndexEntriesPage().getExpectedIndexEntry(itemsList));
        indexingFinalIndexEntriesPage().click(EDIT_FINAL_INDEX_ENTRIES_BUTTON);
        assertThatDraftIndexEntryBoxContainsAllExpectedEntryElements(itemsList);
    }

    @Test
    @DisplayName("Indexing UI Draft Index Entry Box: reordering fragments")
    @EDGE
    @LEGAL
    @LOG
    public void verifyReorderingDraftIndexEntryTest()
    {
        List<String> suggestedIndexEntriesList = indexingSuggestedIndexEntriesPage().getListOfSuggestedEntries();
        suggestedIndexEntriesList.forEach(this::assertThatSuggestedIndexEntryCheckboxIsNotSelected);
        assertThatDraftIndexEntryBoxIsEmpty();
        assertThatButtonIsDisabled(SWAP_DRAFT_INDEX_ENTRY_BUTTON);

        indexingSuggestedIndexEntriesPage().clickSuggestedEntry(getFirstItemFromList(suggestedIndexEntriesList));
        assertThatSuggestedIndexEntryCheckboxIsSelected(getFirstItemFromList(suggestedIndexEntriesList));

        List<String> initialDraftEntriesList = indexingSuggestedIndexEntriesPage().splitSuggestedIndexEntryName(getFirstItemFromList(suggestedIndexEntriesList));
        indexingSuggestedIndexEntriesPage().editSuggestedIndexEntry();
        assertThatDraftIndexEntryBoxContainsAllExpectedEntryElements(initialDraftEntriesList);

        int draftEntriesListSize = initialDraftEntriesList.size();
        for (int i = 1; i < draftEntriesListSize; i++)
        {
            indexingDraftIndexEntryPage().reorderDraftIndexEntries();
            assertThatDraftIndexEntryBorderIsOfSpecifiedColor(
                    initialDraftEntriesList.get(i - 1),
                    ORANGE_COLOR_NAME,
                    ORANGE
            );
        }

        assertThatButtonIsDisabled(SWAP_DRAFT_INDEX_ENTRY_BUTTON);

        List<String> finalDraftIndexEntryList = new ArrayList<>(initialDraftEntriesList.subList(0, draftEntriesListSize - 1));
        finalDraftIndexEntryList
                .forEach(
                        s -> assertThatDraftIndexEntryBorderIsOfSpecifiedColor(
                                finalDraftIndexEntryList.get(finalDraftIndexEntryList.indexOf(s)),
                                ORANGE_COLOR_NAME,
                                ORANGE
                        )
                );

        finalDraftIndexEntryList.add(0, initialDraftEntriesList.get(draftEntriesListSize - 1));
        assertThatDraftIndexEntryBorderIsOfSpecifiedColor(
                getFirstItemFromList(finalDraftIndexEntryList),
                BLACK_COLOR_NAME,
                BLACK
        );

        assertThat(indexingDraftIndexEntryPage().getListOfAllTextDraftIndexEntries())
                .as("The last fragment should become the first after reordering is complete")
                .isEqualTo(finalDraftIndexEntryList);
    }

    @Test
    @DisplayName("Indexing UI Draft Index Entry Box: adding the new fragments after reordering")
    @EDGE
    @LEGAL
    @LOG
    public void verifyAddingFragmentsAfterReorderingDraftIndexEntryTest()
    {
        List<String> suggestedIndexEntriesList = indexingSuggestedIndexEntriesPage().getListOfSuggestedEntries();
        suggestedIndexEntriesList.forEach(this::assertThatSuggestedIndexEntryCheckboxIsNotSelected);
        assertThatDraftIndexEntryBoxIsEmpty();
        assertThatButtonIsDisabled(SWAP_DRAFT_INDEX_ENTRY_BUTTON);

        indexingSuggestedIndexEntriesPage().clickSuggestedEntry(getFirstItemFromList(suggestedIndexEntriesList));
        assertThatSuggestedIndexEntryCheckboxIsSelected(getFirstItemFromList(suggestedIndexEntriesList));

        List<String> initialDraftEntriesList = indexingSuggestedIndexEntriesPage().splitSuggestedIndexEntryName(getFirstItemFromList(suggestedIndexEntriesList));
        indexingSuggestedIndexEntriesPage().editSuggestedIndexEntry();
        assertThatDraftIndexEntryBoxContainsAllExpectedEntryElements(initialDraftEntriesList);

        initialDraftEntriesList
                .forEach(
                        s -> assertThatDraftIndexEntryBorderIsOfSpecifiedColor(
                                s,
                                BLACK_COLOR_NAME,
                                BLACK
                        )
                );

        int draftEntriesListSize = initialDraftEntriesList.size();
        for (int i = 1; i < draftEntriesListSize; i++)
        {
            indexingDraftIndexEntryPage().reorderDraftIndexEntries();
            assertThatDraftIndexEntryBorderIsOfSpecifiedColor(
                    initialDraftEntriesList.get(i - 1),
                    ORANGE_COLOR_NAME,
                    ORANGE
            );
        }

        assertThatButtonIsDisabled(SWAP_DRAFT_INDEX_ENTRY_BUTTON);

        indexingDraftIndexEntryPage().addEmptyDraftIndexEntry();
        assertThatEmptyDataEntryFieldIsPresentInDraftIndexEntryBox(draftEntriesListSize + 1);

        String fragment;
        do
        {
            fragment = indexingFragmentsPage().getRandomFragmentName();
        }
        while (initialDraftEntriesList.contains(fragment));

        indexingDraftIndexEntryPage().sendKeys(fragment);
        indexingDraftIndexEntryPage().saveDraftIndexEntries();

        assertThatLastAddedFinalIndexEntryContainedOfTokensInDraftIndexEntryBox();
    }

    @Test
    @DisplayName("Indexing UI Draft Index Entry Box: buttons")
    @EDGE
    @LEGAL
    @LOG
    public void verifyDisabledEnabledButtonsDraftIndexEntryTest()
    {
        List<String> buttons = List.of(
                SWAP_DRAFT_INDEX_ENTRY_BUTTON,
                PLUS_DRAFT_INDEX_ENTRY_BUTTON,
                REMOVE_DRAFT_INDEX_ENTRY_BUTTON,
                SAVE_DRAFT_INDEX_ENTRY_BUTTON
        );

        assertThatOnlyPlusButtonIsEnabled(buttons);

        String firstFragmentName = indexingFragmentsPage().getRandomFragmentName();
        indexingFragmentsPage().doubleClickFragment(format(ANY_FRAGMENT_TEXT, firstFragmentName));
        assertThatOnlyOneDraftIndexEntryIsPresent(firstFragmentName);
        assertThatEntryIsPresentInDraftIndexEntryBox(firstFragmentName);

        assertThatOnlyReorderButtonIsDisabled(buttons);

        String secondFragmentName = indexingFragmentsPage().getRandomFragmentName();
        indexingFragmentsPage().doubleClickFragment(format(ANY_FRAGMENT_TEXT, secondFragmentName));
        assertThatExpectedTokensArePresentInDraftIndexEntryBox(firstFragmentName, secondFragmentName);

        assertThatAllButtonsAreEnabled(buttons);

        indexingDraftIndexEntryPage().reorderDraftIndexEntries();
        assertThatExpectedTokensArePresentInDraftIndexEntryBox(secondFragmentName, firstFragmentName);
        assertThatDraftIndexEntryBorderIsOfSpecifiedColor(
                firstFragmentName,
                ORANGE_COLOR_NAME,
                ORANGE
        );

        assertThatOnlyReorderButtonIsDisabled(buttons);

        String thirdFragmentName = indexingFragmentsPage().getRandomFragmentName();
        indexingDraftIndexEntryPage().enterDraftIndexEntryWithoutMovingToFinalIndexEntries(thirdFragmentName);

        assertThatAllButtonsAreEnabled(buttons);

        indexingDraftIndexEntryPage().saveDraftIndexEntries();
        assertThatLastAddedFinalIndexEntryContainedOfTokensInDraftIndexEntryBox();

        indexingDraftIndexEntryPage().deleteDraftIndexEntries();
        assertThatExpectedTokensArePresentInDraftIndexEntryBox(secondFragmentName, thirdFragmentName);

        indexingDraftIndexEntryPage().deleteDraftIndexEntries();
        assertThatDraftIndexEntryBoxIsEmpty();
        assertThatOnlyPlusButtonIsEnabled(buttons);
    }

    // ---------- Assistive Methods ----------

    private void addRandomFragmentsToDraftIndexEntryBox()
    {
        itemsList = indexingFragmentsPage().makeListOfRandomFragments(TOTAL_ENTRIES_NUMBER);

        itemsList
                .forEach(
                        s -> {
                            indexingFragmentsPage().doubleClickFragment(format(FRAGMENT_TEXT, s));

                            assertThatSelectedItemsTextIsBold(format(SELECTED_FRAGMENT_CONTAINS_TEXT, s));
                            assertThatHoveredSelectedFragmentIsUnderlined(format(ANY_FRAGMENT_TEXT, s));
                            assertThatEntryIsPresentInDraftIndexEntryBox(s);
                        }
                );
    }

    private void selectAllDraftIndexEntriesExceptLastOne()
    {
        for (int i = 1; i < TOTAL_ENTRIES_NUMBER; i++)
        {
            indexingDraftIndexEntryPage().clickDraftEntryByNumber(i);
            assertThat(indexingDraftIndexEntryPage().doesElementExist(format(SELECTED_DRAFT_INDEX_ENTRY, i, itemsList.get(i - 1))))
                    .as("The entry should be selected")
                    .isTrue();
            assertThatDraftIndexEntryBorderIsOfSpecifiedColor(itemsList.get(i - 1), ORANGE_COLOR_NAME, ORANGE);
        }
    }
}
