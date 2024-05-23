package com.thomsonreuters.codes.codesbench.quality.tests.sourcenavigateangular.indexai;

import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.EDGE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.indexai.IndexingFinalIndexEntriesPageElements.*;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.indexai.IndexingFragmentsPageElements.*;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.indexai.IndexingPageElements.DRAFT_INDEX_ENTRY_BOX;
import static java.lang.String.format;

/**
 * Feature 725254: CWB Index UI Redesign [HALCYONST-17476]
 */
public class ValidationTests extends IndexingUiBase
{
    private String finalIndexEntry;
    @BeforeEach
    public void accessIndexingUi()
    {
        findSingleApvRenditionForIndexing(UUID_HAS_SUGGESTIONS);
        accessIndexingUiViaContextMenu();
        assertThatDraftIndexEntryBoxIsEmpty();
        assertThatFinalIndexEntriesBoxIsEmpty();
    }

    @Test
    @DisplayName("Indexing UI Validation: single-fragment final index entry")
    @EDGE
    @LEGAL
    @LOG
    public void verifyValidationDotsSingleWordFinalIndexEntryTest()
    {
        String singleFragment = indexingFragmentsPage().getRandomFragmentName();

        indexingDraftIndexEntryPage().enterDraftIndexEntryAndMoveToFinalIndexEntries(singleFragment);

        assertThatOnlyOneFinalIndexEntryIsPresent();
        assertThatLastAddedFinalIndexEntryContainedOfTokensInDraftIndexEntryBox();
        assertThatFinalIndexEntriesBoxContainsExpectedFinalIndexEntries(singleFragment);
        assertThatFinalIndexEntryHasValidationDots(singleFragment);

        finalIndexEntry = indexingFinalIndexEntriesPage().getFirstFinalIndexEntryText();

        assertThatFinalIndexEntryHasSpecifiedValidationDot(
                finalIndexEntry,
                "The single-fragment final index entry should have a blue validation dot",
                BLUE_DOT
                );

        indexingFinalIndexEntriesPage().hoverOverValidationDots(finalIndexEntry);
        assertThatElementHasTooltip(format(VALIDATION_DOT, finalIndexEntry), VALIDATION_HOVER_TOOLTIP);
        assertThatTooltipMessageMatchesDotColor(BLUE_DOT, BLUE_DOT_MESSAGE);
        assertThatBlueDotMessageContainsOnlyGenerallyFragment();

        indexingFinalIndexEntriesPage().clickSpecificFinalIndexEntry(finalIndexEntry);
        assertThatFinalIndexEntryIsHighlighted(finalIndexEntry);

        indexingFinalIndexEntriesPage().editFinalIndexEntry();
        assertThatFinalIndexEntriesBoxIsEmpty();
        assertThatOnlyOneDraftIndexEntryIsPresent(singleFragment);

        indexingDraftIndexEntryPage().enterDraftIndexEntryAndMoveToFinalIndexEntries(GENERALLY);

        finalIndexEntry = indexingFinalIndexEntriesPage().getFirstFinalIndexEntryText();

        assertThatLastAddedFinalIndexEntryContainedOfTokensInDraftIndexEntryBox();
        assertThatFinalIndexEntryDoesNotHaveValidationDotOfSpecificColor(finalIndexEntry, BLUE_DOT);
    }

    @Test
    @DisplayName("Indexing UI Validation: heading ineligibility final index entry")
    @EDGE
    @LEGAL
    @LOG
    public void verifyValidationDotsHeadingIneligibilityFinalIndexEntryTest()
    {
        List<String> headingIneligibilityFinalIndexEntry = Arrays.asList(
                BALLOTS, ABSENTEE_VOTING, MILITARY_FORCES
        );

        headingIneligibilityFinalIndexEntry
                .forEach(
                        s -> {
                            indexingDraftIndexEntryPage().enterDraftIndexEntryWithoutMovingToFinalIndexEntries(s);
                            assertThatEntryIsPresentInDraftIndexEntryBox(s);
                        }
                );

        indexingDraftIndexEntryPage().saveDraftIndexEntries();
        assertThatOnlyOneFinalIndexEntryIsPresent();
        assertThatLastAddedFinalIndexEntryContainedOfTokensInDraftIndexEntryBox();

        finalIndexEntry = indexingFinalIndexEntriesPage().getFirstFinalIndexEntryText();

        assertThatOnlyOneFinalIndexEntryIsPresent();
        assertThatFinalIndexEntriesBoxContainsExpectedFinalIndexEntries(finalIndexEntry);
        assertThatFinalIndexEntryHasValidationDots(finalIndexEntry);

        assertThatFinalIndexEntryHasSpecifiedValidationDot(
                finalIndexEntry,
                "The final index entry with ineligible heading should have a purple validation dot",
                PURPLE_DOT
        );

        indexingFinalIndexEntriesPage().hoverOverValidationDots(finalIndexEntry);
        assertThatElementHasTooltip(format(VALIDATION_DOT, finalIndexEntry), VALIDATION_HOVER_TOOLTIP);

        assertThatTooltipMessageMatchesDotColor(PURPLE_DOT, PURPLE_DOT_MESSAGE);
        assertThatPurpleDotMessageContainsOnlyHeadingFragment(headingIneligibilityFinalIndexEntry);

        indexingFinalIndexEntriesPage().clickSpecificFinalIndexEntry(finalIndexEntry);
        assertThatFinalIndexEntryIsHighlighted(finalIndexEntry);

        indexingFinalIndexEntriesPage().editFinalIndexEntry();
        assertThatFinalIndexEntriesBoxIsEmpty();
        assertThatDraftIndexEntryBoxContainsAllExpectedEntryElements(headingIneligibilityFinalIndexEntry);

        indexingDraftIndexEntryPage().reorderDraftIndexEntries();
        indexingDraftIndexEntryPage().saveDraftIndexEntries();

        finalIndexEntry = indexingFinalIndexEntriesPage().getFirstFinalIndexEntryText();

        assertThatOnlyOneFinalIndexEntryIsPresent();
        assertThatLastAddedFinalIndexEntryContainedOfTokensInDraftIndexEntryBox();
        assertThatFinalIndexEntryDoesNotHaveValidationDotOfSpecificColor(finalIndexEntry, PURPLE_DOT);
    }

    @Test
    @DisplayName("Indexing UI Validation: fragment conflict final index entry")
    @EDGE
    @LEGAL
    @LOG
    public void verifyValidationDotsFragmentConflictFinalIndexEntryTest()
    {
        List<String> fragmentConflictFinalIndexEntry = Arrays.asList(
                MILITARY_FORCES, BALLOTS, ELECTIONS, ABSENTEE_VOTING
        );

        fragmentConflictFinalIndexEntry
                .forEach(
                        s -> {
                            indexingDraftIndexEntryPage().enterDraftIndexEntryWithoutMovingToFinalIndexEntries(s);
                            assertThatEntryIsPresentInDraftIndexEntryBox(s);
                        }
                );

        indexingDraftIndexEntryPage().saveDraftIndexEntries();
        assertThatOnlyOneFinalIndexEntryIsPresent();
        assertThatLastAddedFinalIndexEntryContainedOfTokensInDraftIndexEntryBox();

        finalIndexEntry = indexingFinalIndexEntriesPage().getFirstFinalIndexEntryText();

        assertThatOnlyOneFinalIndexEntryIsPresent();
        assertThatFinalIndexEntriesBoxContainsExpectedFinalIndexEntries(finalIndexEntry);
        assertThatFinalIndexEntryHasValidationDots(finalIndexEntry);

        assertThatFinalIndexEntryHasSpecifiedValidationDot(
                finalIndexEntry,
                "The final index entry with ineligible heading should have a purple validation dot",
                PINK_DOT
        );

        indexingFinalIndexEntriesPage().hoverOverValidationDots(finalIndexEntry);
        assertThatElementHasTooltip(format(VALIDATION_DOT, finalIndexEntry), VALIDATION_HOVER_TOOLTIP);

        assertThatTooltipMessageMatchesDotColor(PINK_DOT, PINK_DOT_MESSAGE);
        assertThatPinkDotMessageContainsConflictingFragments(finalIndexEntry);

        indexingFinalIndexEntriesPage().clickSpecificFinalIndexEntry(finalIndexEntry);
        assertThatFinalIndexEntryIsHighlighted(finalIndexEntry);

        indexingFinalIndexEntriesPage().editFinalIndexEntry();
        assertThatFinalIndexEntriesBoxIsEmpty();
        assertThatDraftIndexEntryBoxContainsAllExpectedEntryElements(fragmentConflictFinalIndexEntry);

        indexingDraftIndexEntryPage().clickDraftEntryByNumber(indexingDraftIndexEntryPage().getNumberOfDraftIndexEntries());
        indexingDraftIndexEntryPage().deleteDraftIndexEntries();
        indexingDraftIndexEntryPage().saveDraftIndexEntries();

        finalIndexEntry = indexingFinalIndexEntriesPage().getFirstFinalIndexEntryText();

        assertThatLastAddedFinalIndexEntryContainedOfTokensInDraftIndexEntryBox();
        assertThatOnlyOneFinalIndexEntryIsPresent();
        assertThatFinalIndexEntryDoesNotHaveValidationDotOfSpecificColor(finalIndexEntry, PINK_DOT);
    }

    @Test
    @DisplayName("Indexing UI Validation: fragment order final index entry")
    @EDGE
    @LEGAL
    @LOG
    public void verifyValidationDotsFragmentOrderFinalIndexEntryTest()
    {
        List<String> fragmentOrderFinalIndexEntry = Arrays.asList(
                ABSENTEE_VOTING, BALLOTS, MILITARY_FORCES
        );

        fragmentOrderFinalIndexEntry
                .forEach(
                        s -> {
                            indexingDraftIndexEntryPage().enterDraftIndexEntryWithoutMovingToFinalIndexEntries(s);
                            assertThatEntryIsPresentInDraftIndexEntryBox(s);
                        }
                );

        indexingDraftIndexEntryPage().saveDraftIndexEntries();
        assertThatOnlyOneFinalIndexEntryIsPresent();
        assertThatLastAddedFinalIndexEntryContainedOfTokensInDraftIndexEntryBox();

        finalIndexEntry = indexingFinalIndexEntriesPage().getFirstFinalIndexEntryText();

        assertThatOnlyOneFinalIndexEntryIsPresent();
        assertThatFinalIndexEntriesBoxContainsExpectedFinalIndexEntries(finalIndexEntry);
        assertThatFinalIndexEntryHasValidationDots(finalIndexEntry);

        assertThatFinalIndexEntryHasSpecifiedValidationDot(
                finalIndexEntry,
                "The final index entry with incorrect fragment order should have an orange validation dot",
                ORANGE_DOT
        );

        indexingFinalIndexEntriesPage().hoverOverValidationDots(finalIndexEntry);
        assertThatElementHasTooltip(format(VALIDATION_DOT, finalIndexEntry), VALIDATION_HOVER_TOOLTIP);

        assertThatTooltipMessageMatchesDotColor(ORANGE_DOT, ORANGE_DOT_MESSAGE);
        assertThatOrangeDotMessageContainsFragmentsIncorrectOrder(finalIndexEntry);

        indexingFinalIndexEntriesPage().clickSpecificFinalIndexEntry(finalIndexEntry);
        assertThatFinalIndexEntryIsHighlighted(finalIndexEntry);

        indexingFinalIndexEntriesPage().editFinalIndexEntry();
        assertThatFinalIndexEntriesBoxIsEmpty();
        assertThatDraftIndexEntryBoxContainsAllExpectedEntryElements(fragmentOrderFinalIndexEntry);

        indexingDraftIndexEntryPage().deleteDraftIndexEntries();
        assertThatDraftIndexEntryBoxIsEmpty();

        List<String> updatedFinalIndexEntry = Arrays.asList(
                ABSENTEE_VOTING, MILITARY_FORCES, BALLOTS
        );

        updatedFinalIndexEntry
                .forEach(
                        s -> {
                            indexingDraftIndexEntryPage().enterDraftIndexEntryWithoutMovingToFinalIndexEntries(s);
                            assertThatEntryIsPresentInDraftIndexEntryBox(s);
                        }
                );

        indexingDraftIndexEntryPage().saveDraftIndexEntries();

        finalIndexEntry = indexingFinalIndexEntriesPage().getFirstFinalIndexEntryText();

        assertThatOnlyOneFinalIndexEntryIsPresent();
        assertThatLastAddedFinalIndexEntryContainedOfTokensInDraftIndexEntryBox();
        assertThatFinalIndexEntryDoesNotHaveValidationDotOfSpecificColor(finalIndexEntry, ORANGE_DOT);
    }

    @Test
    @DisplayName("Indexing UI Validation: multiple references final index entry")
    @EDGE
    @LEGAL
    @LOG
    public void verifyValidationDotsMultipleReferencesFinalIndexEntryTest()
    {
        List<String> firstFinalIndexEntryFragments = Arrays.asList(ABSENTEE_VOTING, MILITARY_FORCES, BALLOTS);
        List<String> secondFinalIndexEntryFragments = Arrays.asList(ABSENTEE_VOTING, BALLOTS);
        List<String> thirdFinalIndexEntryFragments = Arrays.asList(ELECTIONS, MILITARY_FORCES, BALLOTS);
        List<List<String>> fragmentsFinalIndexEntriesList = List.of(
                firstFinalIndexEntryFragments, secondFinalIndexEntryFragments
        );

        fragmentsFinalIndexEntriesList
                .forEach(
                        p -> {
                            p.forEach(
                                    s -> {
                                        indexingDraftIndexEntryPage().enterDraftIndexEntryWithoutMovingToFinalIndexEntries(s);
                                        assertThatEntryIsPresentInDraftIndexEntryBox(s);
                                    }
                            );

                            indexingDraftIndexEntryPage().saveDraftIndexEntries();
                            assertThatLastAddedFinalIndexEntryContainedOfTokensInDraftIndexEntryBox();
                            if (indexingFinalIndexEntriesPage().getNumberOfFinalEntries() == 1)
                            {
                                assertThatFinalIndexEntryDoesNotHaveValidationDotOfSpecificColor(getFirstItemFromList(p), YELLOW_DOT);
                            }
                            indexingDraftIndexEntryPage().deleteDraftIndexEntries();
                        }
                );

        indexingFinalIndexEntriesPage().getTextOfFinalEntries()
                .forEach(
                        s -> {
                            assertThatFinalIndexEntryHasValidationDots(s);
                            indexingFinalIndexEntriesPage().hoverOverValidationDots(s);
                            assertThatElementHasTooltip(format(VALIDATION_DOT, s), VALIDATION_HOVER_TOOLTIP);
                            assertThatFinalIndexEntryHasSpecifiedValidationDot(
                                    s,
                                    "The final index entry with already existing heading fragment should have a yellow validation dot",
                                    YELLOW_DOT
                            );
                            assertThatTooltipMessageMatchesDotColor(YELLOW_DOT, YELLOW_DOT_MESSAGE);
                            assertThatYellowDotMessageContainsOnlyHeadingFragment(s);
                        }
                );

        indexingPage().click(DRAFT_INDEX_ENTRY_BOX);

        thirdFinalIndexEntryFragments
                .forEach(
                            s -> {
                                indexingDraftIndexEntryPage().enterDraftIndexEntryWithoutMovingToFinalIndexEntries(s);
                                assertThatEntryIsPresentInDraftIndexEntryBox(s);
                            }
                );

        indexingDraftIndexEntryPage().saveDraftIndexEntries();
        assertThatLastAddedFinalIndexEntryContainedOfTokensInDraftIndexEntryBox();
        assertThatFinalIndexEntryDoesNotHaveValidationDotOfSpecificColor(getLastItemFromList(indexingFinalIndexEntriesPage().getTextOfFinalEntries()), YELLOW_DOT);

        indexingFinalIndexEntriesPage().clickSpecificFinalIndexEntry(getLastItemFromList(indexingFinalIndexEntriesPage().getTextOfFinalEntries()));
        indexingFinalIndexEntriesPage().deleteFinalIndexEntry();

        indexingFinalIndexEntriesPage().getTextOfFinalEntries()
                .forEach(
                        s -> assertThatFinalIndexEntryHasSpecifiedValidationDot(
                                    s,
                                    "The final index entry with already existing heading fragment should have a yellow validation dot",
                                    YELLOW_DOT
                            )
                );

        indexingFinalIndexEntriesPage().clickSpecificFinalIndexEntry(indexingFinalIndexEntriesPage().getFirstFinalIndexEntryText());
        indexingFinalIndexEntriesPage().editFinalIndexEntry();

        assertThatOnlyOneFinalIndexEntryIsPresent();
        assertThatFinalIndexEntryDoesNotHaveValidationDotOfSpecificColor(getFirstItemFromList(indexingFinalIndexEntriesPage().getTextOfFinalEntries()), YELLOW_DOT);

        indexingDraftIndexEntryPage().reorderDraftIndexEntries();
        indexingDraftIndexEntryPage().saveDraftIndexEntries();

        indexingFinalIndexEntriesPage().getTextOfFinalEntries()
                .forEach(s -> assertThatFinalIndexEntryDoesNotHaveValidationDotOfSpecificColor(s, YELLOW_DOT));
    }

    @Test
    @DisplayName("Indexing UI Validation: 3+ validation issues")
    @EDGE
    @LEGAL
    @LOG
    public void verifyValidationDotsMoreThanThreeIssuesFinalIndexEntryTest()
    {
        List<String> issuedFinalIndexEntry = Arrays.asList(
                BALLOTS, MILITARY_FORCES, ELECTIONS, ABSENTEE_VOTING
        );

        issuedFinalIndexEntry
                .forEach(
                        s -> {
                            indexingDraftIndexEntryPage().enterDraftIndexEntryWithoutMovingToFinalIndexEntries(s);
                            assertThatEntryIsPresentInDraftIndexEntryBox(s);
                        }
                );

        indexingDraftIndexEntryPage().saveDraftIndexEntries();
        assertThatOnlyOneFinalIndexEntryIsPresent();
        assertThatLastAddedFinalIndexEntryContainedOfTokensInDraftIndexEntryBox();

        finalIndexEntry = indexingFinalIndexEntriesPage().getFirstFinalIndexEntryText();

        assertThatOnlyOneFinalIndexEntryIsPresent();
        assertThatFinalIndexEntriesBoxContainsExpectedFinalIndexEntries(finalIndexEntry);
        assertThatThreeOrLessValidationDotsAreDisplayed(finalIndexEntry);

        indexingFinalIndexEntriesPage().hoverOverValidationDots(finalIndexEntry);
        assertThatElementHasTooltip(format(VALIDATION_DOT, finalIndexEntry), VALIDATION_HOVER_TOOLTIP);

        assertThatNumberOfTooltipsIsThreeOrLess();

        indexingPage().click(DRAFT_INDEX_ENTRY_BOX);
        indexingDraftIndexEntryPage().deleteDraftIndexEntries();
        indexingDraftIndexEntryPage().enterDraftIndexEntryAndMoveToFinalIndexEntries(BALLOTS);
        assertThatFinalIndexEntriesBoxContainsExpectedFinalIndexEntries(finalIndexEntry, BALLOTS);
        assertThatFinalIndexEntryHasValidationDots(BALLOTS);

        assertThatThreeOrLessValidationDotsAreDisplayed(finalIndexEntry);

        indexingFinalIndexEntriesPage().hoverOverValidationDots(finalIndexEntry);
        assertThatElementHasTooltip(format(VALIDATION_DOT, finalIndexEntry), VALIDATION_HOVER_TOOLTIP);

        assertThatNumberOfTooltipsIsMoreThanThree();
    }
}
