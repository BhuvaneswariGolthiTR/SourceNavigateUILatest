package com.thomsonreuters.codes.codesbench.quality.tests.sourcenavigateangular.indexai;

import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.EDGE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.openqa.selenium.Keys;

import java.util.Arrays;
import java.util.List;

import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.SourceNavigateAngularPageElements.*;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.SourceNavigateAngularViewManagerPageElements.INDEX_AI_TEST_VIEW_DROPDOWN;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.indexai.IndexingDraftIndexEntryPageElements.INPUT_DRAFT_INDEX_ENTRY_BY_TEXT;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.indexai.IndexingFinalIndexEntriesPageElements.FINAL_INDEX_ENTRIES_PATTERN;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.indexai.IndexingFragmentsPageElements.*;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.indexai.IndexingPageElements.*;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.indexai.IndexingSuggestedIndexEntriesPageElements.SELECTED_SUGGESTED_ENTRY;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.popups.SourceNavigateAngularToastPageElements.*;
import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Feature 725254: CWB Index UI Redesign [HALCYONST-17476]
 */
public class FinalIndexEntriesTests extends IndexingUiBase
{
    private static final int TOTAL_ENTRIES_NUMBER = 3;

    private List<String> itemsList;

    @BeforeEach
    public void accessIndexingUi(TestInfo testInfo)
    {
        if (!sourceNavigateAngularPage().getActiveViewName(RENDITIONS_ACTIVE_VIEW_NAME).contains(INDEX_AI_TEST_VIEW_DROPDOWN))
        {
            sourceNavigateAngularViewManagerPage().applyExistingView(INDEX_AI_TEST_VIEW_DROPDOWN);
        }

        if (testInfo.getDisplayName().contains("suggestions exist"))
        {
            findSingleApvRenditionForIndexing(UUID_HAS_SUGGESTIONS);
        }
        else
        {
            findSingleApvRenditionForIndexing(UUID_NO_SUGGESTIONS);
        }

        accessIndexingUiViaContextMenu();
    }

    //Bug 724789: Blank Final Index Entries
    @Test
    @DisplayName("Indexing UI Final Index Entry Box: adding empty entries to the Final Index Entries box")
    @EDGE
    @LEGAL
    @LOG
    public void verifyNoEmptyOptionsAddedFromDraftIndexEntryToFinalIndexEntriesTest()
    {
        itemsList = indexingFragmentsPage().makeListOfRandomFragments(TOTAL_ENTRIES_NUMBER);

        assertThatDraftIndexEntryBoxIsEmpty();

        indexingDraftIndexEntryPage().addEmptyDraftIndexEntry();
        assertThatEmptyDataEntryFieldIsPresentInDraftIndexEntryBox(1);
        indexingDraftIndexEntryPage().saveDraftIndexEntries();
        assertThatDraftIndexEntryBoxIsEmpty();
        assertThatFinalIndexEntriesBoxIsEmpty();

        indexingDraftIndexEntryPage().addEmptyDraftIndexEntry();
        assertThatEmptyDataEntryFieldIsPresentInDraftIndexEntryBox(1);
        indexingDraftIndexEntryPage().addEmptyDraftIndexEntry();
        assertThatEmptyDataEntryFieldIsPresentInDraftIndexEntryBox(1);
        assertThatEmptyDataEntryFieldIsNotPresentInDraftIndexEntryBox(2);

        itemsList.forEach(
                s -> {
                    indexingDraftIndexEntryPage().enterDraftIndexEntryWithoutMovingToFinalIndexEntries(s);
                    assertThatEntryIsPresentInDraftIndexEntryBox(itemsList, s, itemsList.indexOf(s));
                });
        indexingDraftIndexEntryPage().addEmptyDraftIndexEntry();
        assertThatEmptyDataEntryFieldIsPresentInDraftIndexEntryBox(itemsList.size() + 1);
        indexingDraftIndexEntryPage().saveDraftIndexEntries();
        assertThatEmptyDataEntryFieldIsNotPresentInDraftIndexEntryBox(itemsList.size() + 1);
        itemsList.forEach(
                s -> assertThatEntryIsPresentInDraftIndexEntryBox(itemsList, s, itemsList.indexOf(s))
        );
        assertThatFragmentTokensArePresentInFinalIndexEntriesBoxInSpecifiedOrder(indexingFinalIndexEntriesPage().getExpectedIndexEntry(itemsList));
        assertThatOnlyOneFinalIndexEntryIsPresent();

        indexingDraftIndexEntryPage().addEmptyDraftIndexEntry();
        assertThatEmptyDataEntryFieldIsPresentInDraftIndexEntryBox(itemsList.size() + 1);
        indexingDraftIndexEntryPage().click(FINAL_INDEX_ENTRIES_BOX);
        assertThatEmptyDataEntryFieldIsNotPresentInDraftIndexEntryBox(itemsList.size() + 1);
    }

    //Bug 724795: Final Index Entries Trash Can Functionality
    @Test
    @DisplayName("Indexing UI Final Index Entry Box: deleting entries without selection")
    @EDGE
    @LEGAL
    @LOG
    public void verifyUnselectedFinalIndexEntriesCantBeDeletedTest()
    {
        itemsList = Arrays
                .asList(ABATEMENT, HOLIDAYS, ABATEMENT_OF_ACTIONS_AND_PROCEEDINGS);

        assertThatDraftIndexEntryBoxIsEmpty();

        itemsList
                .forEach(
                        s -> {
                            indexingDraftIndexEntryPage().enterDraftIndexEntryAndMoveToFinalIndexEntries(s);
                            assertThatEntryIsPresentInDraftIndexEntryBox(itemsList, s, itemsList.indexOf(s));
                        }
                );
        assertThatFragmentTokensArePresentInFinalIndexEntriesBoxInSpecifiedOrder(ABATEMENT);
        assertThatFragmentTokensArePresentInFinalIndexEntriesBoxInSpecifiedOrder(ABATEMENT + DASH + HOLIDAYS);
        assertThatFragmentTokensArePresentInFinalIndexEntriesBoxInSpecifiedOrder(ABATEMENT + DASH + HOLIDAYS + DASH + ABATEMENT_OF_ACTIONS_AND_PROCEEDINGS);

        assertThatButtonIsDisabled(REMOVE_FINAL_INDEX_ENTRIES_BUTTON);
        assertThatNumberOfFinalIndexEntriesIsEqualToExpected(itemsList.size());

        indexingFinalIndexEntriesPage().click(format(FINAL_INDEX_ENTRIES_PATTERN, ABATEMENT + DASH + HOLIDAYS));
        assertThatFinalIndexEntryIsHighlighted(ABATEMENT + DASH + HOLIDAYS);
        indexingFinalIndexEntriesPage().deleteFinalIndexEntry();
        assertThatToastMessageDoesNotKickOff();
        assertThatNumberOfFinalIndexEntriesIsEqualToExpected(itemsList.size() - 1);
        assertThatSpecifiedFinalIndexEntryIsNotPresent(ABATEMENT + DASH + HOLIDAYS);
    }

    //Bug 725031: Indexing: Duplicate Fragment Text
    @Test
    @DisplayName("Indexing UI Final Index Entry Box: duplicates")
    @EDGE
    @LEGAL
    @LOG
    public void verifyDuplicatesCannotBeAddedToFinalIndexEntriesBoxTest()
    {
        String firstFragmentName = indexingFragmentsPage().getRandomFragmentName();
        String secondFragmentName;
        do
        {
            secondFragmentName = indexingFragmentsPage().getRandomFragmentName();
        }
        while (secondFragmentName.equalsIgnoreCase(firstFragmentName));

        indexingPage().click(FRAGMENTS_BOX);
        assertThatDraftIndexEntryBoxIsEmpty();

        indexingFragmentsPage().doubleClickFragment(format(ANY_FRAGMENT_TEXT, firstFragmentName));
        assertThatOnlyOneDraftIndexEntryIsPresent(firstFragmentName);

        indexingDraftIndexEntryPage().addDraftIndexEntryAndTypeCharacters(firstFragmentName);
        indexingDraftIndexEntryPage().pressEnter();
        assertThatOnlyOneDraftIndexEntryIsPresent(firstFragmentName);
        assertThatToastPopupText(firstFragmentName);
        assertThatFinalIndexEntriesBoxIsEmpty();

        //Sree: Seems like this step is no more needed; However keeping it commented for now till next regression
        //sourceNavigateAngularToastPage().clickXButton();

        indexingDraftIndexEntryPage().addDraftIndexEntryAndTypeCharacters(firstFragmentName);
        indexingDraftIndexEntryPage().saveDraftIndexEntries();
        assertThatOnlyOneDraftIndexEntryIsPresent(firstFragmentName);
        assertThatToastPopupText(firstFragmentName);
        assertThatFragmentTokensArePresentInFinalIndexEntriesBoxInSpecifiedOrder(firstFragmentName);
        assertThatNumberOfFinalIndexEntriesIsEqualToExpected(indexingDraftIndexEntryPage().getNumberOfDraftIndexEntries());

        //Sree: Seems like this step is no more needed; However keeping it commented for now till next regression
        //sourceNavigateAngularToastPage().clickXButton();

        indexingFragmentsPage().doubleClickFragment(format(ANY_FRAGMENT_TEXT, secondFragmentName));
        assertThatExpectedTokensArePresentInDraftIndexEntryBox(firstFragmentName, secondFragmentName);

        indexingDraftIndexEntryPage().doubleClickDraftEntry(format(INPUT_DRAFT_INDEX_ENTRY_BY_TEXT, secondFragmentName));
        indexingDraftIndexEntryPage().clearDraftEntryField(secondFragmentName.length());
        indexingDraftIndexEntryPage().sendKeys(firstFragmentName);
        indexingDraftIndexEntryPage().saveDraftIndexEntries();
        assertThatOnlyOneDraftIndexEntryIsPresent(firstFragmentName);
        assertThatToastPopupText(firstFragmentName);
        assertThatFragmentTokensArePresentInFinalIndexEntriesBoxInSpecifiedOrder(firstFragmentName);
        assertThatNumberOfFinalIndexEntriesIsEqualToExpected(indexingDraftIndexEntryPage().getNumberOfDraftIndexEntries());

        //Sree: Seems like this step is no more needed; However keeping it commented for now till next regression
        //sourceNavigateAngularToastPage().clickXButton();

        indexingFragmentsPage().doubleClickFragment(format(ANY_FRAGMENT_TEXT, secondFragmentName));
        assertThatExpectedTokensArePresentInDraftIndexEntryBox(firstFragmentName, secondFragmentName);

        indexingDraftIndexEntryPage().doubleClickDraftEntry(format(INPUT_DRAFT_INDEX_ENTRY_BY_TEXT, secondFragmentName));
        indexingDraftIndexEntryPage().clearDraftEntryField(secondFragmentName.length());
        indexingDraftIndexEntryPage().sendKeys(firstFragmentName);
        indexingDraftIndexEntryPage().pressEnter();
        assertThatOnlyOneDraftIndexEntryIsPresent(firstFragmentName);
        assertThatToastPopupText(firstFragmentName);

        //Sree: Seems like this step is no more needed; However keeping it commented for now till next regression
        //sourceNavigateAngularToastPage().clickXButton();

        indexingDraftIndexEntryPage().addDraftIndexEntryAndTypeCharacters(firstFragmentName + " ");
        indexingDraftIndexEntryPage().saveDraftIndexEntries();
        assertThatOnlyOneDraftIndexEntryIsPresent(firstFragmentName);
        assertThatToastPopupText(firstFragmentName);
        assertThatFragmentTokensArePresentInFinalIndexEntriesBoxInSpecifiedOrder(firstFragmentName);
        assertThatNumberOfFinalIndexEntriesIsEqualToExpected(indexingDraftIndexEntryPage().getNumberOfDraftIndexEntries());

        //Sree: Seems like this step is no more needed; However keeping it commented for now till next regression
        //sourceNavigateAngularToastPage().clickXButton();

        indexingDraftIndexEntryPage().addDraftIndexEntryAndTypeCharacters(firstFragmentName + " ");
        indexingDraftIndexEntryPage().pressEnter();
        assertThatOnlyOneDraftIndexEntryIsPresent(firstFragmentName);
        assertThatToastPopupText(firstFragmentName);
    }

    @Test
    @DisplayName("Indexing UI Final Index Entry Box: actions by mouse and keyboard -- suggestions exist")
    @EDGE
    @LEGAL
    @LOG
    public void verifyActionsOnFinalIndexEntriesBoxTest()
    {
        List<String> suggestedIndexEntriesList = indexingSuggestedIndexEntriesPage().getListOfSuggestedEntries();
        int size = suggestedIndexEntriesList.size();

        assertThatFinalIndexEntriesBoxIsEmpty();

        for (String listItem : suggestedIndexEntriesList) {
            indexingSuggestedIndexEntriesPage().clickSuggestedEntry(listItem);
            assertThatSuggestedIndexEntryIsSelected(listItem);
            assertThatSelectedItemsTextIsBold(format(SELECTED_SUGGESTED_ENTRY, listItem));
            assertThatSuggestedIndexEntryCheckboxIsSelected(listItem);
        }

        indexingSuggestedIndexEntriesPage().saveSuggestedIndexEntry();
        assertThatNumberOfFinalIndexEntriesIsEqualToExpected(size);

        List<String> finalIndexEntriesList = indexingFinalIndexEntriesPage().getTextOfFinalEntries();
        assertThat(finalIndexEntriesList)
                .as("All selected suggested index entries should be present in the Final Index Entries box")
                .isEqualTo(suggestedIndexEntriesList);

        for (int i = 0; i < size; i++)
        {
            assertThatFinalIndexEntryIsNotHighlighted(finalIndexEntriesList, i);
        }

        indexingFinalIndexEntriesPage().clickSpecificFinalIndexEntry(getFirstItemFromList(finalIndexEntriesList));
        assertThatFinalIndexEntryIsHighlighted(getFirstItemFromList(finalIndexEntriesList));

        for (int i = 1; i < finalIndexEntriesList.size(); i++)
        {
            indexingFinalIndexEntriesPage().moveCursorToNextFinalIndexEntry();
            assertThatFinalIndexEntryIsHighlighted(finalIndexEntriesList.get(i));
            assertThatFinalIndexEntryIsNotHighlighted(finalIndexEntriesList, i - 1);
        }

        indexingFinalIndexEntriesPage().moveCursorToPreviousFinalIndexEntry();

        int count = 2;
        while (count > 0)
        {
            assertThatFinalIndexEntryIsHighlighted(finalIndexEntriesList.get(count));
            assertThatFinalIndexEntryIsNotHighlighted(finalIndexEntriesList, count + 1);
            count--;
            indexingFinalIndexEntriesPage().moveCursorToPreviousFinalIndexEntry();

        }

        indexingFinalIndexEntriesPage().deleteFinalIndexEntry();
        finalIndexEntriesList = indexingFinalIndexEntriesPage().getTextOfFinalEntries();
        assertThatNumberOfFinalIndexEntriesIsEqualToExpected(size - 1);
        assertThat(finalIndexEntriesList)
                .as("The first final index entry should be deleted")
                .doesNotContain(getFirstItemFromList(suggestedIndexEntriesList));

        assertThatDraftIndexEntryBoxIsEmpty();
        indexingPage().click(FINAL_INDEX_ENTRIES_BOX);
        assertThatOutlineIsOfSpecifiedColor(FINAL_INDEX_ENTRIES_BOX, BLUE, "blue after clicking");
        indexingFinalIndexEntriesPage().sendKeys(Keys.ENTER);
        assertThatFinalIndexEntryIsHighlighted(getFirstItemFromList(finalIndexEntriesList));

        List<String> finalIndexEntryToEdit = indexingFinalIndexEntriesPage().splitFinalIndexEntryName(getFirstItemFromList(finalIndexEntriesList));
        indexingFinalIndexEntriesPage().editFinalIndexEntry();
        finalIndexEntriesList = indexingFinalIndexEntriesPage().getTextOfFinalEntries();
        assertThatNumberOfFinalIndexEntriesIsEqualToExpected(size - 2);
        assertThat(finalIndexEntriesList)
                .as("There should no such final index entries")
                .doesNotContain(getFirstItemFromList(suggestedIndexEntriesList))
                .doesNotContain(suggestedIndexEntriesList.get(1));

        assertThatDraftIndexEntryBoxContainsAllExpectedEntryElements(finalIndexEntryToEdit);
    }

    //Bug 725059: Indexing: getting backend error 400 when clicking the [Save & Exit] button when the 'Final Index Entries' box is empty
    @Test
    @DisplayName("Indexing UI Final Index Entry Box: Save & Exit -- suggestions exist")
    @EDGE
    @LEGAL
    @LOG
    public void verifySaveExitButtonTest()
    {
        List<String> suggestedIndexEntriesList = indexingSuggestedIndexEntriesPage().getTextOfSuggestedEntries();
        String suggestedIndexEntryItem = getRandomListItem(suggestedIndexEntriesList);

        indexingPage().click(FINAL_INDEX_ENTRIES_BOX);
        if (indexingFinalIndexEntriesPage().getNumberOfFinalEntries() != 0)
        {
            indexingFinalIndexEntriesPage().clearFinalIndexEntryBox();
        }
        assertThatFinalIndexEntriesBoxIsEmpty();

        indexingFinalIndexEntriesPage().saveExitFinalIndexEntry();
        assertThatIndexingUiIsClosed();
        assertThatToastMessageDoesNotKickOff();

        accessIndexingUiViaContextMenu();

        indexingPage().click(FINAL_INDEX_ENTRIES_BOX);
        assertThatFinalIndexEntriesBoxIsEmpty();

        indexingSuggestedIndexEntriesPage().clickSuggestedEntry(suggestedIndexEntryItem);
        assertThatSuggestedIndexEntryCheckboxIsSelected(suggestedIndexEntryItem);

        indexingSuggestedIndexEntriesPage().saveSuggestedIndexEntry();
        assertThatFinalIndexEntriesBoxContainsExpectedFinalIndexEntries(suggestedIndexEntryItem);
        assertThatLastAddedFinalIndexEntryIsEqualToSubmittedSuggestedIndexEntry(suggestedIndexEntryItem);

        indexingFinalIndexEntriesPage().saveExitFinalIndexEntry();
        assertThatIndexingUiIsClosed();
        assertThatToastMessageDoesNotKickOff();

        accessIndexingUiViaContextMenu();
        assertThatOnlyOneFinalIndexEntryIsPresent();
        assertThatFinalIndexEntriesBoxContainsExpectedFinalIndexEntries(suggestedIndexEntryItem);

        indexingFinalIndexEntriesPage().clearFinalIndexEntryBox();

        assertThatFinalIndexEntriesBoxIsEmpty();

        indexingFinalIndexEntriesPage().saveExitFinalIndexEntry();
        assertThatIndexingUiIsClosed();
        assertThatToastMessageDoesNotKickOff();

        assertThat(sourceNavigateAngularPage().getCellValue(RENDITIONS_GRID_CONTAINER, COLUMN_CELL_VALUE_OF_RENDITION_ROW_PATTERN, "indexEntryCompletedDate"))
                .as("The entries should be completed on %s for this Rendition", DateAndTimeUtils.getCurrentDateMMddyyyy())
                .containsExactly(DateAndTimeUtils.getCurrentDateMMddyyyy());
        assertThat(sourceNavigateAngularPage().getCellValue(RENDITIONS_GRID_CONTAINER, COLUMN_CELL_VALUE_OF_RENDITION_ROW_PATTERN, "indexEntryCompletedBy"))
                .as("The entries should be completed by TLE TCBA-BOT for this Rendition")
                        .containsExactly("TLE TCBA-BOT");

        accessIndexingUiViaContextMenu();
        indexingPage().click(FINAL_INDEX_ENTRIES_BOX);

        assertThatFinalIndexEntriesBoxIsEmpty();
    }

    //User Story 725046: Indexing: Remove pop-up messages when pressing some action buttons and add the disable function for these buttons
    @Test
    @DisplayName("Indexing UI Final Index Entry Box: buttons -- suggestions exist")
    @EDGE
    @LEGAL
    @LOG
    public void verifyDisabledEnabledButtonsFinalIndexEntriesTest()
    {
        List<String> buttons = List.of(
                EDIT_FINAL_INDEX_ENTRIES_BUTTON,
                REMOVE_FINAL_INDEX_ENTRIES_BUTTON,
                SAVE_EXIT_FINAL_INDEX_ENTRIES_BUTTON
        );

        List<String> suggestedIndexEntriesList = indexingSuggestedIndexEntriesPage().getListOfSuggestedEntries();
        String firstSuggestedIndexEntry = getFirstItemFromList(suggestedIndexEntriesList);
        String lastSuggestedIndexEntry = getLastItemFromList(suggestedIndexEntriesList);

        assertThatFinalIndexEntriesBoxIsEmpty();
        assertThatOnlySaveExitButtonIsEnabled(buttons);

        indexingSuggestedIndexEntriesPage().clickSuggestedEntry(firstSuggestedIndexEntry);
        indexingSuggestedIndexEntriesPage().clickSuggestedEntry(lastSuggestedIndexEntry);
        indexingSuggestedIndexEntriesPage().saveSuggestedIndexEntry();
        assertThatFinalIndexEntriesBoxContainsExpectedFinalIndexEntries(firstSuggestedIndexEntry, lastSuggestedIndexEntry);

        List<String> finalIndexEntriesList = indexingFinalIndexEntriesPage().getTextOfFinalEntries();

        assertThatOnlySaveExitButtonIsEnabled(buttons);

        indexingFinalIndexEntriesPage().clickSpecificFinalIndexEntry(getFirstItemFromList(finalIndexEntriesList));
        assertThatFinalIndexEntryIsHighlighted(getFirstItemFromList(finalIndexEntriesList));
        assertThatButtonsExceptSaveExitHaveTooltips(buttons);

        indexingFinalIndexEntriesPage().editFinalIndexEntry();
        assertThatDraftIndexEntryBoxContainsAllExpectedEntryElements(indexingFinalIndexEntriesPage().splitFinalIndexEntryName(getFirstItemFromList(finalIndexEntriesList)));
        assertThatSpecifiedFinalIndexEntryIsNotPresent(getFirstItemFromList(finalIndexEntriesList));

        assertThatOnlySaveExitButtonIsEnabled(buttons);

        finalIndexEntriesList = indexingFinalIndexEntriesPage().getTextOfFinalEntries();

        indexingFinalIndexEntriesPage().clickSpecificFinalIndexEntry(getFirstItemFromList(finalIndexEntriesList));
        assertThatFinalIndexEntryIsHighlighted(getFirstItemFromList(finalIndexEntriesList));
        assertThatButtonsExceptSaveExitHaveTooltips(buttons);

        indexingFinalIndexEntriesPage().deleteFinalIndexEntry();
        assertThatFinalIndexEntriesBoxIsEmpty();
        assertThatOnlySaveExitButtonIsEnabled(buttons);
    }
}
