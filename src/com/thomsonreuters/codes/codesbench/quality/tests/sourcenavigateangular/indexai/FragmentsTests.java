package com.thomsonreuters.codes.codesbench.quality.tests.sourcenavigateangular.indexai;

import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.EDGE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.indexai.IndexingFragmentsPageElements.*;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.indexai.IndexingPageElements.*;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.popups.SourceNavigateAngularToastPageElements.ADD_EXISTING_FRAGMENT;
import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

/**
 * Feature 725254: CWB Index UI Redesign [HALCYONST-17476]
 * Bug 724994: Indexing: Fragments box: italic placeholder text should be in the middle of the field
 */
public class FragmentsTests extends IndexingUiBase
{
    private static final String FIRST_TYPE_AHEAD_TEXT = "land";
    private static final String SECOND_TYPE_AHEAD_TEXT = "supreme";
    private static final String THIRD_TYPE_AHEAD_TEXT = "acc";
    private static final String FOURTH_TYPE_AHEAD_TEXT = "credit";

    private String fragmentName;

    static Stream<Arguments> getTypeAheadSuggestionsTestData()
    {
        return Stream.of(
                arguments(PRIMARY_FRAGMENT, PRIMARY),
                arguments(SECONDARY_FRAGMENT, SECONDARY)
        );
    }

    @BeforeEach
    public void accessIndexingUi(TestInfo testInfo)
    {
        if (testInfo.getDisplayName().contains("no suggestions"))
        {
            findSingleApvRenditionForIndexing(UUID_NO_SUGGESTIONS);
        }
        else
        {
            findSingleApvRenditionForIndexing(UUID_HAS_SUGGESTIONS);
        }
        accessIndexingUiViaContextMenu();
    }

    @Test
    @DisplayName("Indexing UI Fragments Box: layout -- no suggestions")
    @EDGE
    @LEGAL
    @LOG
    public void verifyFragmentsLayoutNoSuggestedEntriesTest()
    {
        List<String> fragmentsZones = Arrays.asList(FRAGMENTS_NO_SUGGESTIONS, FRAGMENTS_NO_ADD_SUGGESTIONS);

        indexingPage().click(FRAGMENTS_BOX);
        assertThatPrimarySuggestionsBackgroundColorIsGrey(FRAGMENTS_NO_SUGGESTIONS_BACKGROUND);
        fragmentsZones
                .forEach(
                        s -> {
                            assertThat(indexingFragmentsPage().doesElementExist(s))
                                    .as("The expected text is not displayed on the top of the Fragments box")
                                    .isTrue();
                            assertThat(indexingFragmentsPage().getFontStyle(s))
                                    .as("The font style should be italic")
                                    .isEqualTo("italic");
                            assertThat(indexingFragmentsPage().getTextAlignParameter(s))
                                    .as("The text should be centered")
                                    .isEqualTo("center");
                        }
                );
    }

    @Test
    @DisplayName("Indexing UI Fragments Box: type-ahead -- no suggestions")
    @EDGE
    @LEGAL
    @LOG
    public void verifyTypeAheadWorksCorrectlyNoSuggestionsTest()
    {
        indexingPage().click(FRAGMENTS_BOX);

        List<String> charCombinations = Arrays.asList(
                FIRST_TYPE_AHEAD_TEXT,
                SECOND_TYPE_AHEAD_TEXT,
                THIRD_TYPE_AHEAD_TEXT,
                FOURTH_TYPE_AHEAD_TEXT
        );
        charCombinations
                .forEach(
                        s -> {
                            indexingFragmentsPage().makePause();
                            indexingFragmentsPage().typeTextToLocateFragment(s);

                            assertThatSelectedFragmentContainsTypedCharCombination(s);
                            assertThatSelectedItemsTextIsBold(format(SELECTED_FRAGMENT_CONTAINS_TEXT, s));
                            assertThatFragmentBeforeSelectedDoesNotContainCharCombination(s);

                            indexingFragmentsPage().makePause();
                        }
                );

        DateAndTimeUtils.takeNap(DateAndTimeUtils.ONE_SECOND);
        assertThatDraftIndexEntryBoxIsEmpty();

        indexingDraftIndexEntryPage().addDraftIndexEntryAndTypeCharacters(FIRST_TYPE_AHEAD_TEXT);

        assertThatSelectedFragmentContainsTypedCharCombination(FIRST_TYPE_AHEAD_TEXT);
        assertThatSelectedItemsTextIsBold(format(SELECTED_FRAGMENT_CONTAINS_TEXT, FIRST_TYPE_AHEAD_TEXT));
        assertThatFragmentBeforeSelectedDoesNotContainCharCombination(FIRST_TYPE_AHEAD_TEXT);

        indexingDraftIndexEntryPage().pressEnterToSaveDraftIndexEntry(FIRST_TYPE_AHEAD_TEXT);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.ONE_SECOND);
        assertThatEntryIsPresentInDraftIndexEntryBox(indexingFragmentsPage().getSelectedFragmentText());

        charCombinations
                .forEach(
                        s -> {
                            indexingDraftIndexEntryPage().addDraftIndexEntryAndTypeCharacters(s);
                            assertThatSelectedFragmentContainsTypedCharCombination(s);
                            assertThatSelectedItemsTextIsBold(format(SELECTED_FRAGMENT_CONTAINS_TEXT, s));
                            assertThatFragmentBeforeSelectedDoesNotContainCharCombination(s);
                            indexingDraftIndexEntryPage().clearDraftEntryField(s.length());
                        }
                );
    }

    @Test
    @DisplayName("Indexing UI Fragments Box: layout -- suggestions exist")
    @EDGE
    @LEGAL
    @LOG
    public void verifyFragmentsLayoutSuggestedEntriesExistTest()
    {
        indexingPage().click(FRAGMENTS_BOX);

        List<String> primaryFragments = indexingFragmentsPage().getListOfFragments(PRIMARY_FRAGMENT);
        assertThatSuggestedFragmentsExist(primaryFragments, PRIMARY, PRIMARY_FRAGMENT);
        assertThatSuggestedFragmentsInAlphabeticalOrder(primaryFragments);
        primaryFragments.forEach(s -> assertThatPrimarySuggestionsBackgroundColorIsGrey(format(PRIMARY_FRAGMENT_TEXT, s)));

        List<String> secondaryFragments = indexingFragmentsPage().getListOfFragments(SECONDARY_FRAGMENT);
        assertThatSuggestedFragmentsExist(secondaryFragments, SECONDARY, SECONDARY_FRAGMENT);
        assertThatSuggestedFragmentsInAlphabeticalOrder(secondaryFragments);

        int fragmentNumber = indexingFragmentsPage().getRandomFragmentNumber();

        indexingPage().hoverElement(format(FRAGMENT_NUMBER, fragmentNumber));
        assertThatHoveredSelectedFragmentIsUnderlined(format(FRAGMENT_NUMBER, fragmentNumber));
        assertThatItemsTextIsNotBold(format(FRAGMENT_NUMBER, fragmentNumber));

        indexingPage().hoverElement(format(PRIMARY_FRAGMENT_NUMBER, getLastItemIndexFromList(primaryFragments)));
        assertThatFragmentIsNotUnderlined(format(FRAGMENT_NUMBER, fragmentNumber));
        assertThatHoveredSelectedFragmentIsUnderlined(format(PRIMARY_FRAGMENT_NUMBER, getLastItemIndexFromList(primaryFragments)));
        assertThatItemsTextIsNotBold(format(PRIMARY_FRAGMENT_NUMBER, getLastItemIndexFromList(primaryFragments)));

        indexingFragmentsPage().clickFragmentOnce(getLastItemFromList(secondaryFragments));
        assertThatSelectedItemsTextIsBold(format(SELECTED_FRAGMENT_CONTAINS_TEXT, getLastItemFromList(secondaryFragments)));
        assertThatHoveredSelectedFragmentIsUnderlined(format(ANY_FRAGMENT_TEXT, getLastItemFromList(secondaryFragments)));

        fragmentName = indexingFragmentsPage().getRandomFragmentName();
        indexingFragmentsPage().clickFragmentOnce(fragmentName);
        assertThatFragmentIsNotUnderlined(format(ANY_FRAGMENT_TEXT, getLastItemFromList(secondaryFragments)));
        assertThatItemsTextIsNotBold(format(ANY_FRAGMENT_TEXT, getLastItemFromList(secondaryFragments)));
        assertThatSelectedItemsTextIsBold(format(SELECTED_FRAGMENT_CONTAINS_TEXT, fragmentName));
        assertThatHoveredSelectedFragmentIsUnderlined(format(SELECTED_FRAGMENT_TEXT, fragmentName));
    }

    //Bug 595971: Indexing AI Type Ahead Functionality
    @ParameterizedTest(name = "#{index} - Type Ahead -- {1} fragments")
    @MethodSource("getTypeAheadSuggestionsTestData")
    @DisplayName("Indexing UI Fragments Box: type-ahead -- suggestions exist")
    @EDGE
    @LEGAL
    @LOG
    public void verifyTypeAheadWorksCorrectlyWithSuggestionsTest(String suggestedFragments, String attribute)
    {
        indexingPage().click(FRAGMENTS_BOX);

        List<String> suggestedFragmentsList = indexingFragmentsPage().getListOfFragments(suggestedFragments);
        String fragment = getRandomListItem(suggestedFragmentsList);

        indexingFragmentsPage().typeTextToLocateFragment(fragment);
        assertThatSelectedFragmentContainsTypedCharCombination(fragment);
        assertThatSelectedItemsTextIsBold(format(SELECTED_FRAGMENT_CONTAINS_TEXT, fragment));
        assertThat(indexingFragmentsPage().getElementsAttribute(SELECTED_FRAGMENT, "class"))
                .as("\"%s\" fragment should be selected", attribute)
                .contains(attribute);
    }

    @Test
    @DisplayName("Indexing UI Fragments Box: double-clicking the fragments")
    @EDGE
    @LEGAL
    @LOG
    public void verifyDoubleClickingFragmentAddsToDraftIndexEntryTest()
    {
        indexingPage().click(FRAGMENTS_BOX);

        assertThatDraftIndexEntryBoxIsEmpty();

        fragmentName = indexingFragmentsPage().getRandomFragmentName();

        indexingFragmentsPage().doubleClickFragment(format(ANY_FRAGMENT_TEXT, fragmentName));

        assertThatSelectedItemsTextIsBold(format(SELECTED_FRAGMENT_CONTAINS_TEXT, fragmentName));
        assertThatHoveredSelectedFragmentIsUnderlined(format(ANY_FRAGMENT_TEXT, fragmentName));
        assertThatEntryIsPresentInDraftIndexEntryBox(fragmentName);

        indexingFragmentsPage().doubleClickFragment(format(ANY_FRAGMENT_TEXT, fragmentName));

        assertThatOnlyOneDraftIndexEntryIsPresent(fragmentName);
        assertThatEntryIsPresentInDraftIndexEntryBox(fragmentName);
        //assertThatToastMessageKicksOff(format(ADD_EXISTING_FRAGMENT, fragmentName));

        indexingPage().click(DRAFT_INDEX_ENTRY_BOX);

        assertThatItemsTextIsNotBold(format(ANY_FRAGMENT_TEXT, fragmentName));
        assertThatFragmentIsNotUnderlined(format(ANY_FRAGMENT_TEXT, fragmentName));
    }

    @Test
    @DisplayName("Indexing UI Fragments Box: pressing \"Enter\" on selected fragment")
    @EDGE
    @LEGAL
    @LOG
    public void verifyPressingEnterFragmentAddsToDraftIndexEntryTest()
    {
        indexingPage().click(FRAGMENTS_BOX);

        assertThatDraftIndexEntryBoxIsEmpty();

        fragmentName = indexingFragmentsPage().getRandomFragmentName();

        indexingFragmentsPage().pressEnterFragment(format(ANY_FRAGMENT_TEXT, fragmentName));

        assertThatSelectedItemsTextIsBold(format(SELECTED_FRAGMENT_CONTAINS_TEXT, fragmentName));
        assertThatHoveredSelectedFragmentIsUnderlined(format(ANY_FRAGMENT_TEXT, fragmentName));
        assertThatEntryIsPresentInDraftIndexEntryBox(fragmentName);
    }

    //Bug 724923: Indexing: Fragments: moving through the list using the up and down arrows keys gets lost when it reaches a fragment from suggestions
    @Test
    @DisplayName("Indexing UI Fragments Box: moving through the Fragments list using the up and down arrows keys")
    @EDGE
    @LEGAL
    @LOG
    public void verifyPressingUpDownArrowsAllowsToNavigateThroughFragmentsRegardlessOfSuggestedFragmentsTest()
    {
        int count = 3;
        indexingPage().click(FRAGMENTS_BOX);

        List<String> primaryFragments = indexingFragmentsPage().getListOfFragments(PRIMARY_FRAGMENT);
        assertThatSuggestedFragmentsExist(primaryFragments, PRIMARY, PRIMARY_FRAGMENT);

        String fragment = getRandomListItem(primaryFragments);
        indexingFragmentsPage().click(format(FRAGMENT_TEXT + PRECEDING_FRAGMENT_PATTERN, fragment, count));
        assertThatSelectedItemsTextIsBold(format(SELECTED_FRAGMENT_CONTAINS_TEXT, indexingFragmentsPage().getElementsText(format(ANY_FRAGMENT_TEXT + PRECEDING_FRAGMENT_PATTERN, fragment, count))));

        for (int i = 0; i < count; i++)
        {
            indexingFragmentsPage().pressArrowDownKey();
        }
        assertThatSelectedItemsTextIsBold(format(SELECTED_FRAGMENT_CONTAINS_TEXT, fragment));
        assertThat(indexingFragmentsPage().getElementsAttribute(format(SELECTED_FRAGMENT_TEXT, fragment), "class"))
                .as("The selected fragment should not be primary or secondary")
                .doesNotContainIgnoringCase(PRIMARY).doesNotContainIgnoringCase(SECONDARY);
    }
}
