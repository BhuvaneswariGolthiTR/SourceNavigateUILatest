package com.thomsonreuters.codes.codesbench.quality.tests.sourcenavigateangular.indexai;

import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.EDGE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.SourceNavigateAngularContextMenuItemsPageElements.*;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.SourceNavigateAngularPageElements.*;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.SourceNavigateAngularViewManagerPageElements.INDEX_AI_TEST_VIEW_DROPDOWN;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.indexai.IndexingFragmentsPageElements.*;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.indexai.IndexingPageElements.*;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.popups.SourceNavigateAngularPopUpPageElements.INDEXING;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.popups.SourceNavigateAngularPopUpPageElements.UI_TITLE_PATTERN;
import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;

public class IndexingOfMultiSelectedRenditionsTests extends IndexingUiBase
{
    private static final int NUMBER_OF_RENDITIONS = 3;

    @BeforeEach
    public void accessIndexingUi()
    {
        if (!sourceNavigateAngularPage().getActiveViewName(RENDITIONS_ACTIVE_VIEW_NAME).contains(INDEX_AI_TEST_VIEW_DROPDOWN))
        {
            sourceNavigateAngularViewManagerPage().applyExistingView(INDEX_AI_TEST_VIEW_DROPDOWN);
        }
        if(!testName.equals("verifySuggestedFragmentsAreDifferentForEverySelectedRenditionTest") || !testName.equals("verifyAddSuggestedIndexEntriesToFinalIndexEntriesForMultiSelectedRenditionsTest"))
        {
            accessIndexingUiViaContextMenu(NUMBER_OF_RENDITIONS);
        }
    }

    @Test
    @DisplayName("Indexing UI Multi-selected Renditions: saving changes and updating the columns info for the multi-selected Renditions")
    @EDGE
    @LEGAL
    @LOG
    public void verifyUpdatingColumnsForMultiSelectedRenditionsTest()
    {
        assertThatSaveNextButtonIsPresentForSeveralSelectedRenditions();
        assertThatButtonIsEnabled(SAVE_NEXT_FINAL_INDEX_ENTRIES_BUTTON);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.ONE_SECOND);

        for (int i = 1; i < NUMBER_OF_RENDITIONS; i++)
        {
            indexingPage().click(FINAL_INDEX_ENTRIES_BOX);
            clearNotEmptyFinalIndexEntriesBox();
            assertThatFinalIndexEntriesBoxIsEmpty();
            indexingPage().click(FINAL_INDEX_ENTRIES_BOX);
            DateAndTimeUtils.takeNap(DateAndTimeUtils.ONE_SECOND);
            indexingFinalIndexEntriesPage().saveNextFinalIndexEntry();
        }

        assertThatFinalIndexEntriesBoxIsEmpty();
        indexingPage().click(FINAL_INDEX_ENTRIES_BOX);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.ONE_SECOND);
        assertThatSaveNextButtonIsNotPresentForLastSelectedRendition();
        indexingFinalIndexEntriesPage().saveExitFinalIndexEntry();
        assertThatIndexingUiIsClosed();
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);

        for (int i = 0; i < NUMBER_OF_RENDITIONS; i++)
        {
            assertThatIndexEntryCompletedColumnsArePopulatedForRendition(NUMBER_OF_RENDITIONS);
        }

        sourceNavigateAngularPage().rightClickRenditions();
        sourceNavigateAngularPage().clickContextSubMenuItem(EDIT, EDIT_INDEX_ENTRY_FEATURES);
        sourceNavigateAngularPage().waitForElementExists(format(UI_TITLE_PATTERN, INDEXING));

        for (int i = 1; i < NUMBER_OF_RENDITIONS; i++)
        {
            DateAndTimeUtils.takeNap(DateAndTimeUtils.ONE_SECOND);
            indexingPage().click(FINAL_INDEX_ENTRIES_BOX);
            if (indexingFinalIndexEntriesPage().getNumberOfFinalEntries() != 0)
            {
                indexingFinalIndexEntriesPage().getTextOfFinalEntries()
                        .forEach(
                                s -> {
                                    indexingFinalIndexEntriesPage().clickSpecificFinalIndexEntry(s);
                                    indexingFinalIndexEntriesPage().deleteFinalIndexEntry();
                                }
                        );
            }
            assertThatFinalIndexEntriesBoxIsEmpty();
            indexingFinalIndexEntriesPage().saveNextFinalIndexEntry();
        }
    }

    @Test
    @DisplayName("Indexing UI Multi-selected Renditions: adding the suggested index entry to the Final Index Entries box for multi-selected renditions")
    @EDGE
    @LEGAL
    @LOG
    public void verifyAddSuggestedIndexEntriesToFinalIndexEntriesForMultiSelectedRenditionsTest()
    {
        assertThatSaveNextButtonIsPresentForSeveralSelectedRenditions();
        assertThatButtonIsEnabled(SAVE_NEXT_FINAL_INDEX_ENTRIES_BUTTON);

        indexingPage().click(FINAL_INDEX_ENTRIES_BOX);
        clearNotEmptyFinalIndexEntriesBox();
        assertThatFinalIndexEntriesBoxIsEmpty();

        indexingSuggestedIndexEntriesPage()
                .clickSuggestedEntry(
                        getFirstItemFromList(indexingSuggestedIndexEntriesPage().getTextOfSuggestedEntries())
                );
        indexingSuggestedIndexEntriesPage().saveSuggestedIndexEntry();
        assertThatLastAddedFinalIndexEntryIsEqualToSubmittedSuggestedIndexEntry(
                getFirstItemFromList(indexingSuggestedIndexEntriesPage().getTextOfSuggestedEntries())
        );

        indexingFinalIndexEntriesPage().saveNextFinalIndexEntry();

        indexingPage().click(DRAFT_INDEX_ENTRY_BOX);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.ONE_SECOND);
        indexingPage().click(FINAL_INDEX_ENTRIES_BOX);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.ONE_SECOND);
        clearNotEmptyFinalIndexEntriesBox();
        assertThatFinalIndexEntriesBoxIsEmpty();

        List<String> suggestedIndexEntriesList = indexingSuggestedIndexEntriesPage().getListOfSuggestedEntries();
        indexingSuggestedIndexEntriesPage().clickSuggestedEntry(getLastItemFromList(suggestedIndexEntriesList));
        DateAndTimeUtils.takeNap(DateAndTimeUtils.ONE_SECOND);
        indexingSuggestedIndexEntriesPage().saveSuggestedIndexEntry();

        assertThatOnlyOneFinalIndexEntryIsPresent();
        assertThatLastAddedFinalIndexEntryIsEqualToSubmittedSuggestedIndexEntry(getLastItemFromList(suggestedIndexEntriesList));
    }

    @Test
    @DisplayName("Indexing UI Multi-selected Renditions: suggested fragments for the multi-selected renditions")
    @EDGE
    @LEGAL
    @LOG
    public void verifySuggestedFragmentsAreDifferentForEverySelectedRenditionTest()
    {
        String rendition_uuid1 = "I00803831090A11EE831E81D63AEBAA2B";
        String rendition_uuid2 = "I1AEB38E1EE9E11EDA5EBD840D887AFD4";
        sourceNavigateAngularLeftSidePanePage().clickFindButtonOnLeftPane();
        sourceNavigateAngularLeftSidePanePage().setFindValue("Rendition UUID", rendition_uuid1 +"," + rendition_uuid2);
        sourceNavigateAngularLeftSidePanePage().clickFindButton();

        accessIndexingUiViaContextMenu(2);

        assertThatSaveNextButtonIsPresentForSeveralSelectedRenditions();
        assertThatButtonIsEnabled(SAVE_NEXT_FINAL_INDEX_ENTRIES_BUTTON);

        indexingPage().click(FRAGMENTS_BOX);
        List<String> primaryFragmentsFirstRendition = indexingFragmentsPage().getListOfFragments(PRIMARY_FRAGMENT);
        List<String> secondaryFragmentsFirstRendition = indexingFragmentsPage().getListOfFragments(SECONDARY_FRAGMENT);
        indexingFinalIndexEntriesPage().saveNextFinalIndexEntry();

        indexingPage().click(DRAFT_INDEX_ENTRY_BOX);
        indexingPage().click(FRAGMENTS_BOX);
        List<String> primaryFragmentsSecondRendition = indexingFragmentsPage().getListOfFragments(PRIMARY_FRAGMENT);
        List<String> secondaryFragmentsSecondRendition = indexingFragmentsPage().getListOfFragments(SECONDARY_FRAGMENT);
        List<String> suggestedFragmentsList = new ArrayList<>(primaryFragmentsSecondRendition);
        suggestedFragmentsList.addAll(secondaryFragmentsSecondRendition);

        assertThat(primaryFragmentsFirstRendition)
                .as("The primary fragments of the selected renditions should be different")
                .isNotEqualTo(primaryFragmentsSecondRendition);

        assertThat(secondaryFragmentsFirstRendition)
                .as("The secondary fragments of the selected renditions should be different")
                .isNotEqualTo(secondaryFragmentsSecondRendition);

        indexingSuggestedIndexEntriesPage().getTextOfSuggestedEntries()
                .forEach(
                        s -> indexingSuggestedIndexEntriesPage().splitSuggestedIndexEntryName(s)
                                    .forEach(
                                            p -> assertThat(suggestedFragmentsList)
                                                        .contains(p)
                                    )

                );
    }

    @Test
    @DisplayName("Indexing UI Multi-selected Renditions: saving changes for the multi-selected renditions")
    @EDGE
    @LEGAL
    @LOG
    public void verifySavingChangesForMultiSelectedRenditionsTest()
    {
        String firstFragment = indexingFragmentsPage().getRandomFragmentName();
        String secondFragment = indexingFragmentsPage().getRandomFragmentName();
        List<String> expectedFinalIndexEntriesList = Arrays.asList(firstFragment, firstFragment + DASH + secondFragment);

        assertThatSaveNextButtonIsPresentForSeveralSelectedRenditions();
        assertThatButtonIsEnabled(SAVE_NEXT_FINAL_INDEX_ENTRIES_BUTTON);

        for (int i = 1; i < NUMBER_OF_RENDITIONS; i++)
        {
            clickFinalIndexEntriesBoxMultiSelectedRenditions();
            clearNotEmptyFinalIndexEntriesBox();
            assertThatFinalIndexEntriesBoxIsEmpty();

            addFinalIndexEntryUsingRandomFragments(firstFragment);
            addFinalIndexEntryUsingRandomFragments(secondFragment);

            indexingFinalIndexEntriesPage().saveNextFinalIndexEntry();
        }

        clickFinalIndexEntriesBoxMultiSelectedRenditions();
        clearNotEmptyFinalIndexEntriesBox();
        assertThatFinalIndexEntriesBoxIsEmpty();

        addFinalIndexEntryUsingRandomFragments(firstFragment);
        addFinalIndexEntryUsingRandomFragments(secondFragment);

        assertThatSaveNextButtonIsNotPresentForLastSelectedRendition();
        indexingFinalIndexEntriesPage().saveExitFinalIndexEntry();
        assertThatIndexingUiIsClosed();

        for (int i = 0; i < NUMBER_OF_RENDITIONS; i++)
        {
            assertThatIndexEntryCompletedColumnsArePopulatedForRendition(NUMBER_OF_RENDITIONS);
        }

        sourceNavigateAngularPage().rightClickRenditions();
        sourceNavigateAngularPage().clickContextSubMenuItem(EDIT, EDIT_INDEX_ENTRY_FEATURES);
        sourceNavigateAngularPage().waitForElementExists(format(UI_TITLE_PATTERN, INDEXING));

        for (int i = 1; i < NUMBER_OF_RENDITIONS; i++)
        {
            clickFinalIndexEntriesBoxMultiSelectedRenditions();
            assertThat(indexingFinalIndexEntriesPage().getTextOfFinalEntries())
                    .as("The changes are not saved")
                    .isEqualTo(expectedFinalIndexEntriesList);

            clearNotEmptyFinalIndexEntriesBox();
            assertThatFinalIndexEntriesBoxIsEmpty();

            indexingFinalIndexEntriesPage().saveNextFinalIndexEntry();
        }

        clickFinalIndexEntriesBoxMultiSelectedRenditions();
        assertThat(indexingFinalIndexEntriesPage().getTextOfFinalEntries())
                .as("The changes are not saved")
                .isEqualTo(expectedFinalIndexEntriesList);

        clearNotEmptyFinalIndexEntriesBox();
        assertThatFinalIndexEntriesBoxIsEmpty();

        assertThatSaveNextButtonIsNotPresentForLastSelectedRendition();
        indexingFinalIndexEntriesPage().saveExitFinalIndexEntry();

        sourceNavigateAngularPage().rightClickRenditions();
        sourceNavigateAngularPage().clickContextSubMenuItem(EDIT, EDIT_INDEX_ENTRY_FEATURES);
        sourceNavigateAngularPage().waitForElementExists(format(UI_TITLE_PATTERN, INDEXING));
    }

    // ---------- Assistive Methods ----------

    private void clickFinalIndexEntriesBoxMultiSelectedRenditions()
    {
        indexingPage().click(DRAFT_INDEX_ENTRY_BOX);
        indexingFragmentsPage().makePause();
        indexingPage().click(FINAL_INDEX_ENTRIES_BOX);
    }

    private void clearNotEmptyFinalIndexEntriesBox()
    {
        if (indexingFinalIndexEntriesPage().getNumberOfFinalEntries() != 0)
        {
            indexingFinalIndexEntriesPage().clearFinalIndexEntryBox();
        }
    }

    private void addFinalIndexEntryUsingRandomFragments(String fragment)
    {
        indexingPage().click(FRAGMENTS_BOX);
        indexingFragmentsPage().pressEnterFragment(format(ANY_FRAGMENT_TEXT, fragment));
        indexingPage().click(DRAFT_INDEX_ENTRY_BOX);
        assertThatEntryIsPresentInDraftIndexEntryBox(fragment);
        indexingDraftIndexEntryPage().saveDraftIndexEntries();
        indexingPage().click(FINAL_INDEX_ENTRIES_BOX);
        assertThatLastAddedFinalIndexEntryContainedOfTokensInDraftIndexEntryBox();
    }
}
