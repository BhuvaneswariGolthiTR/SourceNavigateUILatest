package com.thomsonreuters.codes.codesbench.quality.tests.sourcenavigateangular.indexai;

import com.thomsonreuters.codes.codesbench.quality.tests.sourcenavigateangular.SourceNavigateAngularBase;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import org.junit.jupiter.api.AfterEach;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.SourceNavigateAngularContextMenuItemsPageElements.EDIT;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.SourceNavigateAngularContextMenuItemsPageElements.EDIT_INDEX_ENTRY_FEATURES;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.SourceNavigateAngularPageElements.COLUMN_CELL_VALUE_OF_RENDITION_ROW_PATTERN;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.SourceNavigateAngularPageElements.TOTAL_RENDITIONS_NUMBER;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.indexai.IndexingDraftIndexEntryPageElements.*;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.indexai.IndexingFinalIndexEntriesPageElements.*;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.indexai.IndexingFragmentsPageElements.*;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.indexai.IndexingPageElements.*;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.indexai.IndexingSuggestedIndexEntriesPageElements.*;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.popups.SourceNavigateAngularPopUpPageElements.*;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.popups.SourceNavigateAngularToastPageElements.TOAST_BODY;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.popups.SourceNavigateAngularToastPageElements.TOAST_BODY_TEXT;
import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;

public class IndexingUiBase extends SourceNavigateAngularBase
{
    // UAT
    static final String UUID_NO_SUGGESTIONS = "I007FB101043E11E59C07D85BC3F6E2E9";
    // DEV
    //static final String UUID_NO_SUGGESTIONS = "I000DE311166911EC8D32C80764E138CE";

    // UAT
    static final String UUID_HAS_SUGGESTIONS = "I6749E9304AB011ED8A01EECA81CA88E5";
    // DEV
    //static final String UUID_HAS_SUGGESTIONS = "I9535D3000D7A11E28BD6FE299650F7D5";

    //UAT
    static final String UUID_BACKUP_AI_NO_PRIMARY = "I35992FC14C5B11ED8A01EECA81CA88E5";
    //UAT
    static final String UUID_BACKUP_AI_PRIMARY_EXIST = "IA23B5B604AD211ED8A01EECA81CA88E5";
    //UAT
    static final String UUID_DOUBLE_DASH = "I28EB49D16AA511EDAD1C84AFCAF616F3";

    @AfterEach
    public void closeIndexingUi()
    {
        sourceNavigateAngularPopUpPage().closeUi(INDEXING);
    }

    void findSingleApvRenditionForIndexing(String uuid)
    {
        sourceNavigateAngularLeftSidePanePage().clickFindButtonOnLeftPane();
        sourceNavigateAngularLeftSidePanePage().setFindValue("Rendition UUID", uuid);
        sourceNavigateAngularLeftSidePanePage().clickFindButton();
        sourceNavigateAngularPage().waitForElementExists(format(TOTAL_RENDITIONS_NUMBER, "1"));
    }

    void accessIndexingUiViaContextMenu()
    {
        sourceNavigateAngularPage().openRenditionsContextMenu();
        sourceNavigateAngularPage().clickContextSubMenuItem(EDIT, EDIT_INDEX_ENTRY_FEATURES);
        sourceNavigateAngularPage().waitForElementExists(format(UI_TITLE_PATTERN, INDEXING));
    }

    void accessIndexingUiViaContextMenu(int numberOfRenditions)
    {
        sourceNavigateAngularPage().openRenditionsContextMenu(numberOfRenditions);
        sourceNavigateAngularPage().clickContextSubMenuItem(EDIT, EDIT_INDEX_ENTRY_FEATURES);
        sourceNavigateAngularPage().waitForElementExists(format(UI_TITLE_PATTERN, INDEXING));
    }

    // ---------- Assertions ----------
    // ---------- Indexing UI general ----------

    void assertThatIndexingWindowAppears()
    {
        assertThat(indexingPage().getElementsText(HEADER))
                .as("The UI header should be %s", INDEXING)
                .isEqualTo(INDEXING);
        assertThat(indexingPage().getBackgroundColorHex(UI_HEADING))
                .as("The UI heading color should be light-blue")
                .isEqualTo(LIGHT_BLUE);
    }

    void assertThatIndexingUiIsClosed()
    {
        assertThat(indexingPage().doesElementExist(format(UI_TITLE_PATTERN, INDEXING)))
                .as("The UI should be closed after clicking \"Save & Exit\" button")
                .isFalse();
    }

    void assertThatOutlineIsOfSpecifiedColor(String boxName, String outlineColor, String colorName)
    {
        assertThat(indexingPage().getOutlineBorderColor(boxName, "outline-color"))
                .as("The outline color should be %s", colorName)
                .isEqualTo(outlineColor);
    }

    void assertThatOutlineColorIsNotBlue(String boxName)
    {
        assertThat(indexingPage().getOutlineBorderColor(boxName, "outline-color"))
                .as("THe outline color should not be blue")
                .containsAnyOf(WHITE, BLACK);
    }

    void assertThatElementHasTooltip(String item, String ngbTooltipXpath)
    {
        assertThat(indexingPage().getElementsAttribute(item, "aria-describedby"))
                .as("The element should have a tooltip")
                .isEqualTo(indexingPage().getElementsAttribute(ngbTooltipXpath, "id"));
    }

    void assertThatElementDoesNotHaveTooltip()
    {
        assertThat(indexingPage().doesElementExist(BUTTON_TOOLTIP_WINDOW))
                .as("The button should NOT have a tooltip")
                .isFalse();
    }

    void assertThatButtonTooltipIsAsExpected(String item)
    {
        assertThat(indexingPage().getElementsAttribute(item, "ngbtooltip"))
                .as("The tooltip is not as expected")
                .isEqualTo(indexingPage().getElementsText(BUTTON_TOOLTIP_WINDOW));
    }

    void assertThatToastMessageKicksOff(String message)
    {
        assertThat(sourceNavigateAngularToastPage().getToastPopupText())
                .as("The toast message should be kicked-off")
                .isEqualTo(message);
    }

    void assertThatToastPopupText(String message)
    {
        assertThat(sourceNavigateAngularToastPage().doesElementExist(String.format(TOAST_BODY_TEXT, message)))
                .as("The toast message should be kicked-off")
                .isTrue();
    }

    void assertThatToastMessageDoesNotKickOff()
    {
        assertThat(sourceNavigateAngularToastPage().doesElementExist(TOAST_BODY))
                .as("The toast message should NOT be kicked-off")
                .isFalse();
    }

    void assertThatSelectedItemsTextIsBold(String xpath)
    {
        assertThat(indexingFragmentsPage().getFragmentFontWeight(xpath))
                .as("The selected item should be bold")
                .isEqualTo("700");
    }

    void assertThatItemsTextIsNotBold(String xpath)
    {
        assertThat(indexingFragmentsPage().getFragmentFontWeight(xpath))
                .as("The item should NOT be bold")
                .isEqualTo("400");
    }

    void assertThatButtonIsDisabled(String buttonXpath)
    {
        assertThat(indexingPage().getElementsAttribute(buttonXpath, "disabled"))
                .as("The button should be disabled")
                .isEqualTo("true");
        assertThat(indexingPage().getBackgroundColorHex(buttonXpath))
                .as("The background color of the disabled button should be silver")
                .isEqualTo(SILVER);
    }

    void assertThatButtonIsEnabled(String buttonXpath)
    {
        assertThat(indexingPage().getElementsAttribute(buttonXpath, "disabled"))
                .as("The button should be enabled")
                .isNull();
        assertThat(indexingPage().getBackgroundColorHex(buttonXpath))
                .as("The background color of the enabled button should be blue")
                .containsAnyOf(INDIGO, DARK_BLUE);
    }

    void assertThatAllButtonsAreEnabled(List<String> buttons)
    {
        buttons.forEach(
                s -> {
                    assertThatButtonIsEnabled(s);
                    indexingPage().hoverElement(s);
                    assertThatElementHasTooltip(s, BUTTON_TOOLTIP_WINDOW);
                    assertThatButtonTooltipIsAsExpected(s);
                }
        );
    }

    void assertThatAllButtonsAreDisabled(List<String> buttons)
    {
        buttons.forEach(
                s -> {
                    assertThatButtonIsDisabled(s);
                    indexingPage().hoverElement(s);
                    assertThatElementDoesNotHaveTooltip();
                }
        );
    }

    // ---------- Session Law Box ----------

    void assertThatSeeMoreLinkIsPresent()
     {
         assertThat(indexingSessionLawBoxPage().isSeeMoreHyperlinkPresent())
                 .as("See More hyperlink should be displayed inside the Session Law box")
                 .isTrue();
     }

     // ---------- Fragments Box ----------

    void assertThatSelectedFragmentContainsTypedCharCombination(String chars)
    {
        assertThat(indexingFragmentsPage().getElementsText(SELECTED_FRAGMENT))
                .as("The selected fragment should contain %s", chars)
                .contains(chars);
    }

    void assertThatFragmentBeforeSelectedDoesNotContainCharCombination(String chars)
    {
        assertThat(indexingFragmentsPage().getPreviousFragment())
                .as("The previous fragment should not contain %s", chars)
                .doesNotContainIgnoringCase(chars);
    }

    void assertThatHoveredSelectedFragmentIsUnderlined(String xpath)
    {
        assertThat(indexingFragmentsPage().getFragmentTextDecoration(xpath))
                .as("The hovered fragment should be underlined")
                .isEqualTo("underline");
    }

    void assertThatFragmentIsNotUnderlined(String xpath)
    {
        assertThat(indexingFragmentsPage().getFragmentTextDecoration(xpath))
                .as("The fragment should NOT be underlined")
                .isEqualTo("none");
    }

    void assertThatSuggestedFragmentsInAlphabeticalOrder(List<String> suggestedFragments)
    {
        assertThat(suggestedFragments.stream().sorted().collect(Collectors.toList()))
                .as("The fragments should be in alphabetical order")
                .isEqualTo(suggestedFragments);
    }

    void assertThatSuggestedFragmentsExist(List<String> primaryFragments, String suggestion, String suggestedFragment)
    {
        assertThat(primaryFragments.size())
                .as("There are %s fragments should exist", suggestion)
                .isGreaterThan(0)
                .isEqualTo(indexingFragmentsPage().getNumberOfFragments(suggestedFragment));
    }

    void assertThatPrimarySuggestionsBackgroundColorIsGrey(String fragments)
    {
        assertThat(indexingFragmentsPage().getBackgroundColorHex(fragments))
                .as("The background of the top part of the Fragments box should be grey")
                .isEqualTo(GREY);
    }

    // ---------- Suggested Index Entries Box ----------

    void assertThatEachSuggestedIndexEntryContainsDashes()
    {
        indexingSuggestedIndexEntriesPage().getTextOfSuggestedEntries()
                .forEach(
                        s -> {
                            assertThat(s)
                                    .as("The Suggested Index Entry should contain dashes")
                                    .contains(DASH);
                            long numberOfDashes = s.chars().filter(v -> v == DASH.trim().charAt(0)).count();
                            long numberOfFragments = indexingSuggestedIndexEntriesPage().splitSuggestedIndexEntryName(s).size();
                            assertThat(numberOfFragments - numberOfDashes)
                                    .as("The number of dashes should be one less than the number of fragments")
                                    .isOne();
                        }
                );
    }

    void assertThatEachSuggestedIndexEntryContainsOnlyOfSuggestedFragments(List<String> suggestionsList)
    {
        indexingSuggestedIndexEntriesPage().getTextOfSuggestedEntries()
                .forEach(
                        s ->
                            indexingSuggestedIndexEntriesPage().splitSuggestedIndexEntryName(s)
                                    .forEach(
                                            p -> assertThat(suggestionsList)
                                                    .as("The Suggested Index Entry should consist of only the suggested fragments")
                                                    .anySatisfy(value -> assertThat(value).matches(p)))
                );
    }

    void assertThatEachSuggestedIndexEntryDoesNotContainSecondaryFragments(List<String> suggestionsList)
    {
        indexingSuggestedIndexEntriesPage().getTextOfSuggestedEntries()
                .forEach(
                        s -> indexingSuggestedIndexEntriesPage().splitSuggestedIndexEntryName(s)
                                .forEach(
                                        p -> assertThat(suggestionsList)
                                                .as("The Suggested Index Entry created by the Primary AI model should consist of only the primary fragments")
                                                .doesNotContain(p)
                                )
                );
    }

    void assertThatOrangeLightBulbIsPresent()
    {
        assertThat(indexingPage().doesElementExist(LIGHT_BULB))
                .as("The light bulb should be present for this Rendition")
                .isTrue();
        assertThat(indexingPage().getElementsColorHex(LIGHT_BULB))
                .as("The light bulb color should be orange")
                .isEqualTo(ORANGE);
    }

    void assertThatLightBulbIsNotPresent()
    {
        assertThat(indexingPage().doesElementExist(LIGHT_BULB))
                .as("The light bulb should NOT be present for this Rendition")
                .isFalse();
    }

    void assertThatHoveredLightBulbHasExpectedTooltip()
    {
        assertThat(indexingPage().getElementsText(LIGHT_BULB_TOOLTIP_WINDOW))
                .as("The tooltip is not as expected")
                .isEqualTo("Suggested references supplied by backup AI model may be less reliable than average.");
    }

    void assertThatSuggestionsAreMadeBySpecifiedAiModel(List<String> completedByList, String completedByValue)
    {
        assertThat(completedByList)
                .as("The entries should be created by %s for this Rendition", completedByValue)
                .containsExactly(completedByValue);
    }

    void assertThatEachSuggestedIndexEntryHasCheckbox()
    {
        List<String> entriesList = indexingSuggestedIndexEntriesPage().getListOfSuggestedEntries();
        for (int i = 1; i <= indexingSuggestedIndexEntriesPage().getNumberOfSuggestedEntries(); i++)
        {
            assertThat(indexingSuggestedIndexEntriesPage().doesElementExist(format(SUGGESTED_ENTRY_CHECKBOX, entriesList.get(i - 1))))
                    .as("The checkbox should exist for each of the suggested index entry")
                    .isTrue();
        }
    }

    void assertThatSuggestedIndexEntryIsSelected(String suggestedIndexEntryName)
    {
        assertThat(indexingSuggestedIndexEntriesPage().getElementsAttribute(format(SUGGESTED_ENTRY_TEXT, suggestedIndexEntryName), "class"))
                .as("The suggested index entry should be selected")
                .contains("selected-entry");
    }

    void assertThatSuggestedIndexEntryCheckboxIsSelected(String suggestedIndexEntryName)
    {
        assertThat(indexingSuggestedIndexEntriesPage().isCheckboxSelected(suggestedIndexEntryName))
                .as("The checkbox of the entry should be selected")
                .isTrue();
    }

    void assertThatSuggestedIndexEntryCheckboxIsNotSelected(String suggestedIndexEntryName)
    {
        assertThat(indexingSuggestedIndexEntriesPage().isCheckboxSelected(suggestedIndexEntryName))
                .as("The checkbox of the entry should be selected")
                .isFalse();
    }

    // ---------- Draft Index Entry Box ----------

    void assertThatExpectedTokensArePresentInDraftIndexEntryBox(String... tokens) {
        assertThat(indexingDraftIndexEntryPage().getListOfAllTextDraftIndexEntries())
                .as("Not all expected tokens are present in the Draft Index Entry box")
                .containsExactly(tokens);
    }

    void assertThatOnlyOneDraftIndexEntryIsPresent(String draftIndexEntryName)
    {
        assertThat(indexingDraftIndexEntryPage().getNumberOfDraftIndexEntries())
                .as("Only one draft index entry should exist in the Draft Index Entry box")
                .isOne();
        assertThat(indexingDraftIndexEntryPage().getTextOfSpecificDraftIndexEntry(1))
                .as("The draft index entry should be equal to", draftIndexEntryName)
                .isEqualTo(draftIndexEntryName);
    }

    void assertThatDraftIndexEntryBorderIsOfSpecifiedColor(String entryText, String colorName, String borderColor)
    {
        assertThat(indexingPage().getOutlineBorderColor(format(READONLY_DRAFT_INDEX_ENTRY_INPUT, entryText), "border-color"))
                .as("The draft entry's border color should be %s", colorName)
                .isEqualTo(borderColor);
    }

    void assertThatDraftIndexEntryBoxIsEmpty()
    {
        editorTextPage().waitForElement(EMPTY_DRAFT_INDEX_ENTRY_BOX);
        assertThat(indexingDraftIndexEntryPage().doesElementExist(EMPTY_DRAFT_INDEX_ENTRY_BOX))
                .as("The Draft Index Entry box should be empty")
                .isTrue();
    }

    void assertThatEntryIsPresentInDraftIndexEntryBox(List<String> entriesList, String entry, int index)
    {
        assertThat(indexingDraftIndexEntryPage().doesElementExist(format(INPUT_DRAFT_INDEX_ENTRY_BY_TEXT, entriesList.get(index))))
                .as("The \"%s\" entry should be present in the Draft Index Entry box", entry)
                .isTrue();

    }

    void assertThatEntryIsPresentInDraftIndexEntryBox(String entry)
    {
        assertThat(indexingDraftIndexEntryPage().doesElementExist(format(INPUT_DRAFT_INDEX_ENTRY_BY_TEXT, entry)))
                .as("The \"%s\" entry should be present in the Draft Index Entry box", entry)
                .isTrue();
    }

    void assertThatOnlySelectedDraftIndexEntriesAreDeleted(int totalNumberOfEntries, List<String> itemsList)
    {
        for (int i = 1; i < totalNumberOfEntries; i++)
        {
            assertThat(indexingDraftIndexEntryPage().doesElementExist(format(DATA_ENTRY_FIELD_TEXT, i, itemsList.get(i - 1))))
                    .as("The \"%s\" entry should NOT be present in the Draft Index Entry box", itemsList.get(i - 1))
                    .isFalse();
        }
    }

    void assertThatEmptyDataEntryFieldIsPresentInDraftIndexEntryBox(int numberOfEntry)
    {
        assertThat(indexingDraftIndexEntryPage().doesElementExist(format(DATA_ENTRY_FIELD_TEXT, numberOfEntry, "")))
                .as("The empty data entry field should be present in the Draft Index Entry box")
                .isTrue();
        assertThatDraftIndexEntryIsInEditMode("");
    }

    void assertThatEmptyDataEntryFieldIsNotPresentInDraftIndexEntryBox(int numberOfEntry)
    {
        assertThat(indexingDraftIndexEntryPage().doesElementExist(format(DATA_ENTRY_FIELD_TEXT, numberOfEntry, "")))
                .as("Only one empty data entry field should NOT be present in the Draft Index Entry box")
                .isFalse();
    }

    void assertThatSpellcheckUiKicksOff()
    {
        assertThat(sourceNavigateAngularPopUpPage().doesElementExist(format(UI_HEADING_SECOND_LEVEL, INDEXING, SPELLCHECK)))
                .as("%s UI should kicks-off", SPELLCHECK)
                .isTrue();
        assertThat(sourceNavigateAngularPopUpPage().getElementsText(SPELLCHECK_INFO))
                .as("The info message should exist inside the Spellcheck UI")
                .contains("The following words do not exist in the dictionary. Please confirm spelling.");
    }

    void assertThatSpellcheckUiClosed()
    {
        assertThat(sourceNavigateAngularPopUpPage().doesElementExist(format(UI_HEADING_SECOND_LEVEL, INDEXING, SPELLCHECK)))
                .as("%s UI should kicks-off", SPELLCHECK)
                .isFalse();
    }

    void assertThatItemInSpellcheckUiIsEqualToEnteredDraftIndexEntry(String spellcheckEntry)
    {
        assertThat(sourceNavigateAngularPopUpPage().getElementsText(SPELLCHECK_ITEM))
                .as("The item for spellcheck should be equal to the \"%s\"", spellcheckEntry)
                .isEqualTo(spellcheckEntry);
    }

    void assertThatDraftIndexEntryIsInEditMode(String entryName)
    {
        assertThat(indexingDraftIndexEntryPage().doesElementExist(format(READONLY_DRAFT_INDEX_ENTRY_SPAN, entryName)))
                .as("The entry should remain in the Edit mode")
                .isFalse();
    }

    void assertThatElementAfterSingleOrLastDraftIndexEntryIsNotPresent()
    {
        assertThat(indexingDraftIndexEntryPage().getAfterPseudoElement(format(DRAFT_ENTRY_SELECTOR_PATTERN, indexingDraftIndexEntryPage().getNumberOfDraftIndexEntries())))
                .as("The dash should not be added to the single or last draft index entry")
                .isEqualTo("none");
    }

    void assertThatDraftIndexEntryBoxContainsAllExpectedEntryElements(List<String> list)
    {
        for (int i = 1; i <= indexingDraftIndexEntryPage().getNumberOfDraftIndexEntries(); i++)
        {
            assertThat(indexingDraftIndexEntryPage().doesElementExist(format(DATA_ENTRY_FIELD_TEXT, i, list.get(i - 1))))
                    .as("The Draft Index Entry box should contain every fragment of the selected entry")
                    .isTrue();
            assertThatDraftIndexEntryBorderIsOfSpecifiedColor(list.get(i - 1), "black", BLACK);

            indexingDraftIndexEntryPage().clickDraftEntryByNumber(i);
            assertThatDraftIndexEntryBorderIsOfSpecifiedColor(list.get(i - 1), "orange", ORANGE);

            indexingDraftIndexEntryPage().clickDraftEntryByNumber(i);
            assertThatDraftIndexEntryBorderIsOfSpecifiedColor(list.get(i - 1), "black", BLACK);
        }
    }

    void assertThatDashIsPresentBetweenDraftIndexEntries(List<String> list)
    {
        list
                .forEach(
                        s -> {
                            if ((list.indexOf(s) + 1) != list.size())
                            {
                                assertThat(indexingDraftIndexEntryPage().getAfterPseudoElement(format(DRAFT_ENTRY_SELECTOR_PATTERN, list.indexOf(s) + 1)))
                                        .as("The dash should be added between draft index entries")
                                        .isEqualTo("\"-\"");
                            }
                            else
                            {
                                assertThatElementAfterSingleOrLastDraftIndexEntryIsNotPresent();
                            }
                        }
                );
    }

    void assertThatOnlyPlusButtonIsEnabled(List<String> buttons)
    {
        buttons.forEach(
                s -> {
                    if (!s.contains("Add"))
                    {
                        assertThatButtonIsDisabled(s);
                        indexingPage().hoverElement(s);
                        assertThatElementDoesNotHaveTooltip();
                    }
                    else
                    {
                        assertThatButtonIsEnabled(s);
                        indexingPage().hoverElement(s);
                        assertThatElementHasTooltip(s, BUTTON_TOOLTIP_WINDOW);
                        assertThatButtonTooltipIsAsExpected(s);
                    }

                }
        );
    }

    void assertThatOnlyReorderButtonIsDisabled(List<String> buttons)
    {
        buttons.forEach(
                s -> {
                    if (s.contains("Swap"))
                    {
                        assertThatButtonIsDisabled(s);
                        indexingPage().hoverElement(s);
                        assertThatElementDoesNotHaveTooltip();
                    }
                    else
                    {
                        assertThatButtonIsEnabled(s);
                        indexingPage().hoverElement(s);
                        assertThatElementHasTooltip(s, BUTTON_TOOLTIP_WINDOW);
                        assertThatButtonTooltipIsAsExpected(s);
                    }

                }
        );
    }

    void assertThatNumberOfDraftIndexEntryTokensIsEqualToNumberOfAddedFragments(int totalNumberOfEntries)
    {
        assertThat(indexingDraftIndexEntryPage().getNumberOfDraftIndexEntries())
                .as("The number of added draft index entry tokens should be equal to %s", totalNumberOfEntries)
                .isEqualTo(totalNumberOfEntries);
    }

    // ---------- Final Index Entries Box ----------

    void assertThatFinalIndexEntriesBoxIsEmpty()
    {
        assertThat(indexingFinalIndexEntriesPage().doesElementExist(EMPTY_FINAL_INDEX_ENTRIES_BOX))
                .as("The Final Index Entries box should be empty")
                .isTrue();
    }

    void assertThatFinalIndexEntryIsHighlighted(String entry)
    {
        assertThat(indexingFinalIndexEntriesPage().getBackgroundColorHex(format(FINAL_INDEX_ENTRIES_PATTERN, entry)))
                .as("The entry should be selected")
                .isEqualTo(GREY);
    }

    void assertThatFragmentTokensArePresentInFinalIndexEntriesBoxInSpecifiedOrder(String entry)
    {
        assertThat(indexingFinalIndexEntriesPage().doesElementExist(format(FINAL_INDEX_ENTRIES_PATTERN, entry)))
                .as("All draft index entry tokens should be present in the Final Index Entries box in the exact order")
                .isTrue();
    }

    void assertThatNumberOfFinalIndexEntriesIsEqualToExpected(int numberOfEntries)
    {
        assertThat(indexingFinalIndexEntriesPage().getNumberOfFinalEntries())
                .as("There should be %s final index entries", numberOfEntries)
                .isEqualTo(numberOfEntries);
    }

    void assertThatOnlyOneFinalIndexEntryIsPresent()
    {
        assertThat(indexingFinalIndexEntriesPage().getNumberOfFinalEntries())
                .as("There should be only one final index entry")
                .isOne();
    }

    void assertThatLastAddedFinalIndexEntryContainedOfTokensInDraftIndexEntryBox()
    {
        assertThat(indexingFinalIndexEntriesPage().getTextOfFinalEntries().get(indexingFinalIndexEntriesPage().getNumberOfFinalEntries() - 1))
                .as("The last final index entry contained of the draft fragments and dashes should exist")
                .isEqualTo(indexingFinalIndexEntriesPage().getExpectedIndexEntry(indexingDraftIndexEntryPage().getListOfAllTextDraftIndexEntries()));
    }

    void assertThatLastAddedFinalIndexEntryIsEqualToSubmittedSuggestedIndexEntry(String entry)
    {
        assertThat(indexingFinalIndexEntriesPage().getTextOfFinalEntries().get(indexingFinalIndexEntriesPage().getNumberOfFinalEntries() - 1))
                .as("The last final index entry equal to the corresponding suggested index entry should exist")
                .isEqualTo(entry);
    }

    void assertThatFinalIndexEntryIsNotHighlighted(List<String> list, int numberOfEntry)
    {
        assertThat(indexingFinalIndexEntriesPage().getBackgroundColorHex(format(FINAL_INDEX_ENTRIES_PATTERN, list.get(numberOfEntry))))
                .as("The entry should not be highlighted")
                .isNotEqualTo(GREY)
                .isEqualTo(LIGHT_GREY);
    }

    void assertThatFinalIndexEntriesBoxContainsExpectedFinalIndexEntries(String... finalIndexEntryItems)
    {
        assertThat(indexingFinalIndexEntriesPage().getTextOfFinalEntries())
                .as("The Final Index Entries are not as expected")
                .containsExactly(finalIndexEntryItems);
    }

    void assertThatOnlySaveExitButtonIsEnabled(List<String> buttons)
    {
        buttons.forEach(
                s -> {
                    if (!s.contains("Exit"))
                    {
                        assertThatButtonIsDisabled(s);
                    }
                    else
                    {
                        assertThatButtonIsEnabled(s);
                    }
                    indexingPage().hoverElement(s);
                    assertThatElementDoesNotHaveTooltip();

                }
        );
    }

    void assertThatButtonsExceptSaveExitHaveTooltips(List<String> buttons)
    {
        buttons.forEach(
                s -> {
                    if (!s.contains("Exit"))
                    {
                        indexingPage().hoverElement(s);
                        assertThatElementHasTooltip(s, BUTTON_TOOLTIP_WINDOW);
                        assertThatButtonTooltipIsAsExpected(s);
                    }
                    assertThatButtonIsEnabled(s);
                }
        );
    }

    void assertThatSpecifiedFinalIndexEntryIsNotPresent(String finalIndexEntry)
    {
        assertThat(indexingFinalIndexEntriesPage().doesElementExist(format(FINAL_INDEX_ENTRIES_PATTERN, finalIndexEntry)))
                .as("The entry should not be present in the Final Index Entries box")
                .isFalse();
    }

    void assertThatSaveNextButtonIsPresentForSeveralSelectedRenditions()
    {
        assertThat(indexingPage().doesElementExist(SAVE_NEXT_FINAL_INDEX_ENTRIES_BUTTON))
                .as("The \"Save & Next\" button should be present if 2 or more renditions are selected")
                .isTrue();
    }

    void assertThatSaveNextButtonIsNotPresentForLastSelectedRendition()
    {
        assertThat(indexingPage().doesElementExist(SAVE_NEXT_FINAL_INDEX_ENTRIES_BUTTON))
                .as("The \"Save & Next\" button should NOT be present for the last of multi-selected renditions")
                .isFalse();
    }

    void assertThatIndexEntryCompletedColumnsArePopulatedForRendition(int numberOfRenditions)
    {
        assertThat(sourceNavigateAngularPage().getCellValue(numberOfRenditions, COLUMN_CELL_VALUE_OF_RENDITION_ROW_PATTERN, "indexEntryCompletedDate"))
                .as("The entries should be completed on %s for this Rendition", DateAndTimeUtils.getCurrentDateMMddyyyy())
                .containsAll(Collections.singleton(DateAndTimeUtils.getCurrentDateMMddyyyy()));
        assertThat(sourceNavigateAngularPage().getCellValue(numberOfRenditions, COLUMN_CELL_VALUE_OF_RENDITION_ROW_PATTERN, "indexEntryCompletedBy"))
                .as("The entries should be completed by TLE TCBA-BOT for this Rendition")
                .containsAll(Collections.singleton("TLE TCBA-BOT"));
    }

    // ---------- Validation ----------

    void assertThatFinalIndexEntryDoesNotHaveValidationDotOfSpecificColor(String finalIndexEntry, String dotColor) {
        assertThat(indexingFinalIndexEntriesPage().doesElementExist(format(VALIDATION_DOT_STYLE_COLOR, finalIndexEntry, dotColor)))
                .as("The final index entry should NOT have the validation dot of this color")
                .isFalse();
    }

    void assertThatFinalIndexEntryHasSpecifiedValidationDot(String finalIndexEntry, String message, String dotColor) {
        assertThat(indexingFinalIndexEntriesPage().getValidationDotsList(finalIndexEntry))
                .as(message)
                .anySatisfy(
                        s -> assertThat(s.getAttribute("style"))
                                .contains(dotColor)
                );
    }

    void assertThatFinalIndexEntryHasValidationDots(String finalIndexEntry) {
        editorTextPage().waitForElement(format(VALIDATION_DOT_STYLE, finalIndexEntry));
        assertThat(indexingFinalIndexEntriesPage().doesElementExist(format(VALIDATION_DOT_STYLE, finalIndexEntry)))
                .as("The final index entry should have validation dots")
                .isTrue();
    }

    void assertThatYellowDotMessageContainsOnlyHeadingFragment(String finalIndexEntryName)
    {
        assertThat(indexingFinalIndexEntriesPage().getElementsTextList(format(TOOLTIP_VALIDATION_MESSAGE_FRAGMENTS, YELLOW_DOT)))
                .as("The message for the yellow dot should contain only the first fragment of the final index entry")
                .containsOnly(getFirstItemFromList(indexingFinalIndexEntriesPage().splitFinalIndexEntryName(finalIndexEntryName)));
    }

    void assertThatBlueDotMessageContainsOnlyGenerallyFragment()
    {
        assertThat(indexingFinalIndexEntriesPage().getElementsTextList(format(TOOLTIP_VALIDATION_MESSAGE_FRAGMENTS, BLUE_DOT)))
                .as("The message for the blue dot should contain only \"generally\" fragment")
                .containsOnly(GENERALLY);
    }

    void assertThatPurpleDotMessageContainsOnlyHeadingFragment(List<String> list)
    {
        assertThat(indexingFinalIndexEntriesPage().getElementsTextList(format(TOOLTIP_VALIDATION_MESSAGE_FRAGMENTS, PURPLE_DOT)))
                .as("The message for the purple dot should contain only heading ineligible fragment")
                .containsOnly(getFirstItemFromList(list));
    }

    void assertThatPinkDotMessageContainsConflictingFragments(String finalIndexEntryName)
    {
        assertThat(indexingFinalIndexEntriesPage().getElementsTextList(format(TOOLTIP_VALIDATION_MESSAGE_FRAGMENTS, PINK_DOT)))
                .as("The message for the pink dot should contain conflicting fragment from the Final Index Entry")
                .containsAnyElementsOf(indexingFinalIndexEntriesPage().splitFinalIndexEntryName(finalIndexEntryName));
    }

    void assertThatOrangeDotMessageContainsFragmentsIncorrectOrder(String finalIndexEntryName)
    {
        assertThat(indexingFinalIndexEntriesPage().getElementsTextList(format(TOOLTIP_VALIDATION_MESSAGE_FRAGMENTS, ORANGE_DOT)))
                .as("The message for the orange dot should contain fragments in the incorrect oder from the Final Index Entry")
                .containsAnyElementsOf(indexingFinalIndexEntriesPage().splitFinalIndexEntryName(finalIndexEntryName));
    }

    void assertThatTooltipMessageMatchesDotColor(String dotColor, String expectedMessage)
    {
        assertThat(indexingFinalIndexEntriesPage()
                .getElementsText(format(TOOLTIP_VALIDATION_MESSAGE, dotColor)))
                .as("The message does not match the dot color")
                .contains(expectedMessage);
    }

    void assertThatThreeOrLessValidationDotsAreDisplayed(String finalIndexEntry)
    {
        assertThat(indexingFinalIndexEntriesPage().getValidationDotsList(finalIndexEntry).size())
                .as("Three or less dots should be displayed")
                .isLessThanOrEqualTo(3);
    }

    void assertThatNumberOfTooltipsIsThreeOrLess()
    {
        assertThat(indexingFinalIndexEntriesPage().getElements(TOOLTIP_VALIDATION_SPAN).size())
                .as("The number of tooltip messages should be equal or less than 3")
                .isLessThanOrEqualTo(3);
    }

    void assertThatNumberOfTooltipsIsMoreThanThree()
    {
        assertThat(indexingFinalIndexEntriesPage().getElements(TOOLTIP_VALIDATION_SPAN).size())
                .as("The number of tooltip messages should be more than 3")
                .isGreaterThan(3);
    }

    // ---------- Assistive Methods ----------

    String getRandomListItem(List<String> list)
    {
        return list.get(new Random().nextInt(list.size()));
    }

    String getFirstItemFromList(List<String> list)
    {
        return list.get(0);
    }

    int getLastItemIndexFromList(List<String> list)
    {
        return list.size() - 1;
    }

    String getLastItemFromList(List<String> list)
    {
        return list.get(getLastItemIndexFromList(list));
    }


}
