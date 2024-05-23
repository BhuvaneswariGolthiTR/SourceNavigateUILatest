package com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.target.set2;


import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.popups.AdvancedSearchAndReplaceElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets;
import com.thomsonreuters.codes.codesbench.quality.utilities.lists.ListUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AdvancedSearchAndReplaceTests extends TestService
{
    /*Advanced Search and Replace text and mnemonic
     *This test could get tricky, and might just be dumped in the manual tests
     *It will need to know the data of the document before and after to ensure proper verification
     * 1. Open document (probably use one that has 3 chunks at most)
     * 2. Scroll through the document and find correlations between the text and mnemonics of the content
     * --- Example: Maybe a paragraph has a mnemonic of 'AN' and contains a word 'TEST'
     * 3. Click the Advanced Search and Replace button in the toolbar
     * 4. Set the search criteria to match the correlations you picked up before
     * --- Example: Mnemonic is set to 'AN' and phrase is set to 'TEST'
     * 4.1. Set the replace criteria to a different mnemonic and different phrase
     * 5. Run the Advanced Search and Replace
     * 6. VERIFY: This only picked up the content that had a mnemonic of 'AN' and the text of 'TEST'
     * 7. Click commit
     * 8. VERIFY: The correct replacements were made in the document
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void advancedSARTextAndPubtagsTargetLegalTest()
    {
        String uuid = "I2A8B5B9014F511DA8AC5CD53670E6B4E";
        String phrase = "Etobanany";
        String replacementPhrase = "Kartoshka";
        List<String> replacementPubtag = new ArrayList<>();
        replacementPubtag.add("ABC");
        int targetChunk = 1;

        hierarchyNavigatePage().goToHierarchyPage(ContentSets.IOWA_DEVELOPMENT.getCode());
        loginPage().logIn();
        hierarchyNavigatePage().handleCasIssueWithHierarchyPageOpenedAsStartPageIfOccurred(ContentSets.IOWA_DEVELOPMENT);
        hierarchySearchPage().searchNodeUuid(uuid);
        siblingMetadataPage().selectedSiblingMetadataEditContent();
        editorPage().scrollToChunk(targetChunk);

        String firstPara = String.format(EditorTextPageElements.TEXT_PARAGRAPH_FROM_CHUNK,
                targetChunk - 1, 1);
        String firstParaSpan = firstPara + EditorTextPageElements.SPAN;

        List<String> pubtags = editorTextPage().getPubtagsListOfTextParagraphFromChunk(1, 1);

        // add a phrase
        editorTextPage().insertPhraseUnderGivenLabelWithHome(phrase, firstParaSpan);
        editorTextPage().breakOutOfFrame();
        editorToolbarPage().clickSave();

        // go ASAR
        editorToolbarPage().clickAdvancedSearchAndReplace();

        // fill things to search for
        boolean searchPubtagsListPopulatedCorrectly = advancedSearchAndReplacePage().setSearchPubtags(pubtags);
        advancedSearchAndReplacePage().getElement(AdvancedSearchAndReplaceElements.SEARCH_PHRASE_TEXTAREA)
                .sendKeys(phrase);

        // scan
        advancedSearchAndReplacePage().clickScan();
        int matchesFound = advancedSearchAndReplaceScanPage().getNumberOfMatches();
        advancedSearchAndReplaceScanPage().clickBack();

        // fill things to replace with
        boolean replacePubtagsListPopulatedCorrectly = advancedSearchAndReplacePage().setReplacePubtags(replacementPubtag);
        advancedSearchAndReplacePage().getElement(AdvancedSearchAndReplaceElements.REPLACE_PHRASE_TEXTAREA)
                .sendKeys(replacementPhrase);

        // SAR
        advancedSearchAndReplacePage().clickSearchAndReplace();
        advancedSearchAndReplacePage().waitForElement(String.format(AdvancedSearchAndReplaceElements.BEFORE_CHANGE_ITEMS_WITH_ASAR_FOCUS,
                String.join(",", pubtags)));

        boolean pubtagsReplacementMade = advancedSearchAndReplaceSearchAndReplacePage().doesElementExist(
                String.format(AdvancedSearchAndReplaceElements.BEFORE_CHANGE_ITEMS_WITH_ASAR_FOCUS,
                        String.join(",", pubtags)))
                && advancedSearchAndReplaceSearchAndReplacePage().doesElementExist(
                        String.format(AdvancedSearchAndReplaceElements.AFTER_CHANGE_ITEMS_WITH_ASAR_FOCUS,
                                replacementPubtag.get(0)));
        boolean phraseReplacementMade = advancedSearchAndReplaceSearchAndReplacePage().doesElementExist(
                String.format(AdvancedSearchAndReplaceElements.BEFORE_CHANGE_ITEMS_WITH_ASAR_FOCUS, phrase))
                && advancedSearchAndReplaceSearchAndReplacePage().doesElementExist(
                        String.format(AdvancedSearchAndReplaceElements.AFTER_CHANGE_ITEMS_WITH_ASAR_FOCUS, replacementPhrase));

        advancedSearchAndReplaceSearchAndReplacePage().clickCommit();

        // check replacement was made
        boolean pubtagsReplacementHasBeenMadeInText =
                editorTextPage().doesParagraphContainAnyOfGivenPubtags(1, 1, replacementPubtag)
                && !editorTextPage().doesParagraphContainAnyOfGivenPubtags(1, 1, pubtags);
        boolean phraseReplacementHasBeenMadeInText =
                editorTextPage().getElementsText(firstPara).contains(replacementPhrase)
                && !editorTextPage().getElementsText(firstPara).contains(phrase);

        editorPage().closeDocumentAndDiscardChanges();

        Assertions.assertAll
                (
                        () -> Assertions.assertTrue(searchPubtagsListPopulatedCorrectly, "Search pubtags list should be filled correctly"),
                        () -> Assertions.assertEquals(1, matchesFound, "Only one match should be found with selected settings"),
                        () -> Assertions.assertTrue(replacePubtagsListPopulatedCorrectly, "Replace pubtags list should be filled correctly"),
                        () -> Assertions.assertTrue(pubtagsReplacementMade, "Pub tags replacement should be done"),
                        () -> Assertions.assertTrue(phraseReplacementMade, "Phrase replacement should be done"),
                        () -> Assertions.assertTrue(pubtagsReplacementHasBeenMadeInText, "Pubtags should get replaced with expected ones"),
                        () -> Assertions.assertTrue(phraseReplacementHasBeenMadeInText, "initial text should get replaced with expected ones")
                );
    }

    /*Advanced Search and Replace mnemonic
     *This test should be a little easier because we can grab the mnemonics of everything in the beginning
     *and then grab mnemonics after and compare
     * 1. Open document (probably use one that has 3 chunks at most)
     * 2. gather a list of the mnemonics in the document
     * 3. Click the Advanced Search and Replace button in the toolbar
     * 4. Set the search criteria to match the most popular mnemonic (or something similar)
     * 4.1. Set the replace criteria to a different mnemonic
     * 5. Run the Advanced Search and Replace
     * 6. VERIFY: This picked up all matching mnemonics in the test and wants to replace them
     * 7. Click commit
     * 8. VERIFY: The correct replacements were made in the document
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void advancedSARMnemonicTargetLegalTest()
    {
        String uuid = "I2A8B5B9014F511DA8AC5CD53670E6B4E";
        String mnemonicToReplace = "SMP";
        String replacementMnemonic = "SMPF";

        hierarchyNavigatePage().goToHierarchyPage(ContentSets.IOWA_DEVELOPMENT.getCode());
        loginPage().logIn();
        hierarchyNavigatePage().handleCasIssueWithHierarchyPageOpenedAsStartPageIfOccurred(ContentSets.IOWA_DEVELOPMENT);
        hierarchySearchPage().searchNodeUuid(uuid);
        siblingMetadataPage().selectedSiblingMetadataEditContent();

        // grab mnems
        List<String> mnemonicsBeforeASAR = editorTextPage().grabMnemonicsFromChunks(1, 3);

        List<Integer> indexesOfMnemonicsToReplace = ListUtils.grabIndexesOfNeededItems(mnemonicsBeforeASAR, mnemonicToReplace);

        // go ASAR
        editorToolbarPage().clickAdvancedSearchAndReplace();

        // fill mnems to search for
        advancedSearchAndReplacePage().setSearchMnemonic(mnemonicToReplace);

        // scan
        advancedSearchAndReplacePage().clickScan();
        int ASARNumberOfMatches = advancedSearchAndReplaceScanPage()
                .getNumberOfMatches();
        int mnemonicsSize = indexesOfMnemonicsToReplace.size();
        advancedSearchAndReplaceScanPage().clickBack();

        // fill things to replace with
        advancedSearchAndReplacePage().setReplaceMnemonic(replacementMnemonic);

        // SAR
        advancedSearchAndReplacePage().clickSearchAndReplace();

        int expectedNumberOfReplacementsBeforeChange = advancedSearchAndReplaceSearchAndReplacePage().countElements(
                String.format(AdvancedSearchAndReplaceElements.BEFORE_CHANGE_ITEMS_WITH_ASAR_FOCUS, mnemonicToReplace));

        int expectedNumberOfReplacementsAfterChange = advancedSearchAndReplaceSearchAndReplacePage().countElements(
                String.format(AdvancedSearchAndReplaceElements.AFTER_CHANGE_ITEMS_WITH_ASAR_FOCUS, replacementMnemonic));

        advancedSearchAndReplaceSearchAndReplacePage().clickCommit();

        editorTextPage().breakOutOfFrame();
        // check replacement was made
        editorPage().switchDirectlyToTextFrame();
        editorPage().scrollToTop();
        editorPage().switchToEditor();

        List<String> mnemonicsAfterASAR = editorTextPage().grabMnemonicsFromChunks(1, 3);

        // if after ASAR some elements were moved to next chunk, that is not loaded at the moment, forget about them
        indexesOfMnemonicsToReplace = indexesOfMnemonicsToReplace.stream()
                .filter(index -> index < mnemonicsAfterASAR.size()).collect(Collectors.toList());
        boolean replacementsHaveBeenDone = indexesOfMnemonicsToReplace.stream()
                .allMatch(index -> mnemonicsAfterASAR.get(index).equals(replacementMnemonic));

        editorPage().closeDocumentAndDiscardChanges();
        Assertions.assertAll
                (
                        () -> Assertions.assertEquals(mnemonicsSize, ASARNumberOfMatches,
                                "ASAR should show an expected number of matches"),
                        () -> Assertions.assertEquals(mnemonicsSize, expectedNumberOfReplacementsBeforeChange,
                                "Expected number of 'Before Change' replacements should be displayed"),
                        () -> Assertions.assertEquals(mnemonicsSize, expectedNumberOfReplacementsAfterChange,
                                "Expected number of 'After Change' replacements should be displayed"),
                        () -> Assertions.assertTrue(replacementsHaveBeenDone,
                                "Initial mnemonics got replaced with expected ones")
                );
    }

    /*Advanced Search and Replace text
     * 1. Open document
     * 2. Scroll through several of the chunks and insert a phrase into them
     * 3. Click the Advanced Search and Replace button in the toolbar
     * 4. Set the search phrase to be that of the phrase inserted
     * 4.1. Seat the replace phrase to that of a phrase you'd like to replace the text with
     * 5. Run the Advanced Search and Replace
     * 6. VERIFY: This picked up all instances of the search phrase in the test and wants to replace them
     * 7. Click commit
     * 8. VERIFY: The correct replacements were made in the document
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void advancedSARTextTargetLegalTest()
    {
        String uuid = "I1CDBA100157A11DA8AC5CD53670E6B4E";
        String phrase = "Etobanany";
        String replacementPhrase = "Kartoshka";

        hierarchyNavigatePage().goToHierarchyPage(ContentSets.IOWA_DEVELOPMENT.getCode());
        loginPage().logIn();
        hierarchyNavigatePage().handleCasIssueWithHierarchyPageOpenedAsStartPageIfOccurred(ContentSets.IOWA_DEVELOPMENT);
        hierarchySearchPage().searchNodeUuid(uuid);
        siblingMetadataPage().selectedSiblingMetadataEditContent();

        String firstParaFromFirstChunk = String.format(EditorTextPageElements.TEXT_PARAGRAPH_FROM_CHUNK, 0, 1);
        String firstParaFromFirstChunkParatext = firstParaFromFirstChunk + EditorTextPageElements.PARATEXT;
        String firstParaFromFirstChunkSpan = firstParaFromFirstChunk + EditorTextPageElements.SPAN;
        String firstParaFromFirstChunkModifiedBy = firstParaFromFirstChunk + EditorTextPageElements.MODIFIED_BY_MNEMONIC;

        String firstParaFromSecondChunk = String.format(EditorTextPageElements.TEXT_PARAGRAPH_FROM_CHUNK, 1, 1);
        String firstParaFromSecondChunkParatext = firstParaFromSecondChunk + EditorTextPageElements.PARATEXT;
        String firstParaFromSecondChunkSpan = firstParaFromSecondChunk + EditorTextPageElements.SPAN;
        String firstParaFromSecondChunkModifiedBy = firstParaFromSecondChunk + EditorTextPageElements.MODIFIED_BY_MNEMONIC;

        int addedItems = 2;
        // add phrases to 1 chunk
        editorPage().scrollToChunk(1);
        editorTextPage().insertPhraseUnderGivenLabelWithHome(phrase, firstParaFromFirstChunkSpan);

        editorPage().breakOutOfFrame();
        editorPage().scrollToChunk(2);
        editorTextPage().insertPhraseUnderGivenLabelWithHome(phrase, firstParaFromSecondChunkSpan);

        // save
        editorPage().breakOutOfFrame();
        editorToolbarPage().clickSave();

        // go ASAR
        editorToolbarPage().clickAdvancedSearchAndReplace();

        // fill phrase to search for
        advancedSearchAndReplacePage().setSearchPhrase(phrase);

        // scan
        advancedSearchAndReplacePage().clickScan();
        int expectedNumberOfMatchesFound = advancedSearchAndReplaceScanPage().getNumberOfMatches();
        advancedSearchAndReplaceScanPage().clickBack();

        // fill phrase to replace with
        advancedSearchAndReplacePage().setReplacePhrase(replacementPhrase);

        // SAR
        advancedSearchAndReplacePage().clickSearchAndReplace();
        int expectedNumberOfReplacementsBeforeChange = advancedSearchAndReplaceSearchAndReplacePage()
                .getElements(String.format(AdvancedSearchAndReplaceElements.BEFORE_CHANGE_ITEMS_WITH_ASAR_FOCUS, phrase))
                .size();
        int expectedNumberOfReplacementsAfterChange = advancedSearchAndReplaceSearchAndReplacePage()
                .getElements(String.format(AdvancedSearchAndReplaceElements.AFTER_CHANGE_ITEMS_WITH_ASAR_FOCUS,
                        replacementPhrase))
                .size();
        advancedSearchAndReplaceSearchAndReplacePage().clickCommit();

        // check replacement was made in chunk 2
        boolean replacementsHaveBeenMadeCh2 = editorTextPage()
                .getElementsText(firstParaFromSecondChunkParatext)
                .startsWith(replacementPhrase);

        //check paragraph has Modified by SAR tag with current date
        String modifiedByTag = "Modified bySAR " + DateAndTimeUtils.getCurrentDateMMddyyyy();
        String modifiedByCh2 = editorTextPage().getElementsText(firstParaFromSecondChunkModifiedBy);

        // check replacement was made in chunk 1
        editorTextPage().breakOutOfFrame();
        editorPage().scrollToChunk(1);
        boolean replacementsHaveBeenMadeCh1 = editorTextPage()
                .getElementsText(firstParaFromFirstChunkParatext)
                .startsWith(replacementPhrase);

        //check paragraph has Modified by SAR tag with current date
        String modifiedByCh1 = editorTextPage().getElementsText(firstParaFromFirstChunkModifiedBy);

        // close editor
        editorPage().closeDocumentAndDiscardChanges();

        Assertions.assertAll
                (
                        () -> Assertions.assertEquals(addedItems, expectedNumberOfMatchesFound,
                                "Expected number of matches should be found with selected settings"),
                        () -> Assertions.assertEquals(addedItems, expectedNumberOfReplacementsBeforeChange,
                                "Expected number of 'Before Change' replacements should be done"),
                        () -> Assertions.assertEquals(addedItems, expectedNumberOfReplacementsAfterChange,
                                "Expected number of 'After Change' replacements should be done"),
                        () -> Assertions.assertTrue(replacementsHaveBeenMadeCh2,
                                "Inserted phrase in chunk 2 should get replaced with expected ones"),
                        () -> Assertions.assertTrue(replacementsHaveBeenMadeCh1,
                                "Inserted phrase in chunk 1 should get replaced with expected ones"),
                        () -> Assertions.assertEquals(modifiedByTag, modifiedByCh2,
                                "Text paragraph in chunk 2 should have a Modified by SAR tag"),
                        () -> Assertions.assertEquals(modifiedByTag, modifiedByCh1,
                                "Text paragraph in chunk 1 should have a Modified by SAR tag")
                );
    }

}
