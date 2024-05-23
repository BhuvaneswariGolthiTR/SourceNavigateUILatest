package com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.target.set2;

import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.popups.AddPubtagPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.popups.DeletePubtagPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.popups.RemovePubtagElements;
import com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.base.AbstractTagsTests;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Collectors;

import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.ANY_PUBTAG_IN_METADATA_BLOCK;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.FOLLOWING_SIBLING;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.LOADED_CHUNK;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.PRECEDING_SIBLING;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.TEXT_PARAGRAPH_SPAN;
import static org.assertj.core.api.Assertions.assertThat;

public class PubTagsTests extends AbstractTagsTests
{
    private static final String HIGHLIGHTED_PUB_TAG = HIGHLIGHTED_PARA + ANY_PUBTAG_IN_METADATA_BLOCK;
    private static final String AAAA_PUB_TAG = "AAAA";
    private static final String WL_PUB_TAG = "WL";

    @BeforeEach
    public void openTargetNodeInDsEditor()
    {
        openTargetNodeInDsEditorAndSwitchToEditorTextFrame();
    }

    public static Object[][] testData()
    {
        return new Object[][]
                {
                        {AddPubtagPageElements.PAGE_TITLE, "+"},
                        {DeletePubtagPageElements.PAGE_TITLE, "-"}
                };
    }

    @ParameterizedTest(name = "Test: {displayName}; Index: {index}; Arguments: {arguments}")
    @MethodSource("testData")
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void changingPubTagsAcrossChunksPlusAndMinusTargetLegalTest(String pubTagWindowTitle, String characterToCheckWith)
    {
        //Select multiple Text Paragraphs on the chunk boundary
        selectSubsectionElementsUnder(10);

        //Open Add/Delete Pub tag window, select Pub tag and click OK
        openSpecifiedPubTagWindowAndSelectPubTag(pubTagWindowTitle);

        //Assert that the count of added Pub tags is the same as the count of selected paragraphs
        int selectedParagraphs = countHighlightedParagraphs();
        List<String> addedPubTags = editorTextPage()
                .getElementsTextList(HIGHLIGHTED_PUB_TAG)
                .stream()
                .filter(pubTag -> pubTag.contains(AAAA_PUB_TAG))
                .collect(Collectors.toList());
        assertThat(addedPubTags.size())
                .as("The count of added Pub tags should be the same as the count of selected paragraphs")
                .isEqualTo(selectedParagraphs);

        //Assert that all added Pub tags have '+'/'-' character
        assertThat(addedPubTags)
                .as(String.format("All selected [%s] Pub tags should have [%s] character", AAAA_PUB_TAG, characterToCheckWith))
                .allMatch(pubTag -> pubTag.contains(characterToCheckWith));

        softAssertThatAllChangesMadeSuccessfully();
    }

    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void removingPubTagsAcrossChunksTargetLegalTest()
    {
        //Select multiple Text Paragraphs on the chunk boundary
        selectSubsectionElementsUnder(10);
        int selectedPubTags = countHighlightedPubTags();

        //Open Remove Pub tag window, select Pub tag and click OK
        openSpecifiedPubTagWindowAndSelectPubTag(RemovePubtagElements.PAGE_TITLE);

        //Assert that the count of removed Pub tags is the same as the count of selected paragraphs
        int selectedPubTagsAfterRemoving = countHighlightedPubTags();
        int selectedParagraphs = countHighlightedParagraphs();
        assertThat(selectedPubTags - selectedPubTagsAfterRemoving)
                .as("The count of removed Pub tags should be the same as the count of selected paragraphs")
                .isEqualTo(selectedParagraphs);

        //Assert that all necessary Pub tags are removed
        List<String> allSelectedPubTags = editorTextPage().getElementsTextList(HIGHLIGHTED_PUB_TAG);
        assertThat(allSelectedPubTags)
                .as(String.format("All selected [%s] Pub tags should be removed", WL_PUB_TAG))
                .noneMatch(pubTag -> pubTag.equals(WL_PUB_TAG));

        softAssertThatAllChangesMadeSuccessfully();
    }

    /**
     * STORY/BUG - HALCYONST-4592 <br>
     * SUMMARY - Pub tag refresh and maintaining chunks (Target) <br>
     * USER - LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void pubTagRefreshAndMaintainingChunksTargetLegalTest()
    {
        int timesToPaste = 20;
        String nopubPubTag = "NOPUB";
        String mdPubTagInfo = "md.pub.tag.info";
        String modifiedByXpath = editorTextPage().getModifiedByXpath(user());
        String pubTagXpath = modifiedByXpath + String.format(PRECEDING_SIBLING, mdPubTagInfo);

        //Copy text paragraph
        openContextMenuOnTextParagraph();
        editorTextContextMenu().copyCtrlC();
        editorPage().switchDirectlyToTextFrame();

        String pubTagOfTheCopiedParagraph = editorTextPage().getElementsText(TEXT_PARAGRAPH_SPAN
                + String.format(FOLLOWING_SIBLING, "metadata.block/") + mdPubTagInfo);

        //Paste text paragraph several times
        for (int i = 0; i < timesToPaste; i++)
        {
            openContextMenuOnTextParagraph();
            editorTextContextMenu().pasteSiblingCtrlV();
            editorPage().switchDirectlyToTextFrame();
        }

        //Assert that the number of pasted paragraphs is the same as expected
        assertThat(editorTextPage().countElements(modifiedByXpath))
                .as("The number of pasted paragraphs should be: %s", timesToPaste)
                .isEqualTo(timesToPaste);
        //Assert that all pasted paragraphs have [NOPUB] pub tag
        assertThat(editorTextPage().getElementsTextList(pubTagXpath))
                .as(String.format("All pasted paragraphs should have [%s] pub tag", nopubPubTag))
                .allMatch(pubTag -> pubTag.equals(nopubPubTag));

        //Click pub tag refresh toolbar button
        editorPage().switchToEditor();
        editorToolbarPage().clickPubtagRefreshButton();
        editorPage().switchDirectlyToTextFrame();

        List<String> pubTagsAfterRefreshing = editorTextPage().getElementsTextList(String.format(LOADED_CHUNK, 0) + pubTagXpath);
        editorPage().switchToEditorAndScrollToChunk(2);
        pubTagsAfterRefreshing.addAll(editorTextPage().getElementsTextList(String.format(LOADED_CHUNK, 1) + pubTagXpath));

        //Assert that the number of pub tags after refreshing is the same as the pasted paragraphs number
        assertThat(pubTagsAfterRefreshing.size())
                .as(String.format("The number of pub tags after refreshing should be [%s]", timesToPaste))
                .isEqualTo(timesToPaste);
        //Assert that all pasted paragraphs have pub tag which is the same as pub tag of the copied paragraph after refreshing
        assertThat(pubTagsAfterRefreshing)
                .as(String.format("All pasted paragraphs should have [%s] pub tag after refreshing", pubTagOfTheCopiedParagraph))
                .allMatch(pubTag -> pubTag.equals(pubTagOfTheCopiedParagraph));
        //Assert that there are no errors in the editor message pane
        editorPage().switchToEditor();
        assertThat(editorMessagePage().checkForErrorInMessagePane())
                .as("There should be no any errors in the editor message pane")
                .isFalse();
    }

//  ------------- Assistive methods: -------------

    private void openSpecifiedPubTagWindowAndSelectPubTag(String pubTagWindowTitle)
    {
        editorTextPage().rightClick(HIGHLIGHTED_PUB_TAG);
        editorTextPage().breakOutOfFrame();
        switch (pubTagWindowTitle)
        {
            case "Add Pubtag":
                editorTextContextMenu().pubTagPlus();
                addPubtagPage().switchToAddPubTagWindow();
                addPubtagPage().selectAvailablePubtags(AAAA_PUB_TAG);
                break;
            case "Delete Pubtag":
                editorTextContextMenu().pubTagMinus();
                deletePubtagPage().switchToDeletePubTagWindow();
                deletePubtagPage().selectAvailablePubtags(AAAA_PUB_TAG);
                break;
            case "Remove Pubtag":
            default:
                editorTextContextMenu().pubTagRemove();
                removePubtagPage().switchToRemovePubTagWindow();
                removePubtagPage().selectAvailablePubtags(WL_PUB_TAG);
        }
        editorPage().switchDirectlyToTextFrame();
    }

    private int countHighlightedPubTags()
    {
        return editorTextPage().countElements(HIGHLIGHTED_PUB_TAG);
    }

    private void openContextMenuOnTextParagraph()
    {
        editorTextPage().click(TEXT_PARAGRAPH_SPAN);
        editorTextPage().rightClick(TEXT_PARAGRAPH_SPAN);
        editorTextPage().breakOutOfFrame();
    }
}
