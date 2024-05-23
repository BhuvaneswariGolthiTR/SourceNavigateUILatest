package com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.target.set1;

import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.*;

public class ReferenceHeadingTests extends TestService
{

    /**
     * STORY/BUG - HALCYONST-12755 <br>
     * SUMMARY -  Dynamic Scrolling: Reference Heading Updates <br>
     * USER - LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void referenceHeadingUpdateTest()
    {
        String uuid = "IB73391A096E811E993DCE73C558C2312";
        int chunkNumber = 4;

        // open DS editor
        hierarchyNavigatePage().goToHierarchyPage(ContentSets.TEXAS_DEVELOPMENT.getCode());
        loginPage().logIn();
        hierarchyNavigatePage().handleCasIssueWithHierarchyPageOpenedAsStartPageIfOccurred(ContentSets.TEXAS_DEVELOPMENT);
        hierarchySearchPage().searchNodeUuid(uuid);
        siblingMetadataPage().selectedSiblingMetadataEditContent();
        //scroll down and copy Research Reference Subheading block
        editorPage().scrollToChunk(chunkNumber);
        editorPage().scrollToElement(EditorTextPageElements.RESEARCH_REFERENCE_SUBHEADING);
        String researchReferenceText = editorTextPage().getElementsText(EditorTextPageElements.RESEARCH_REFERENCE_SUBHEADING + EditorTextPageElements.HEADTEXT);

        editorPage().click(EditorTextPageElements.RESEARCH_REFERENCE_SUBHEADING + EditorTextPageElements.SPAN);
        editorPage().rightClick(EditorTextPageElements.RESEARCH_REFERENCE_SUBHEADING + EditorTextPageElements.SPAN);

        editorPage().breakOutOfFrame();
        editorTextContextMenuPage().copyCtrlC();
        //scroll up and insert block as Historical Note Subheading sibling
        editorPage().jumpToBeginningOfDocument();
        editorPage().scrollToChunk(2);

        editorPage().click(EditorTextPageElements.HISTORICAL_NOTE_SUBHEADING + EditorTextPageElements.SPAN);

        editorPage().rightClick(EditorTextPageElements.HISTORICAL_NOTE_SUBHEADING + EditorTextPageElements.SPAN);

        editorPage().breakOutOfFrame();
        editorTextContextMenuPage().pasteSiblingCtrlV();
        editorTextPage().switchToEditorTextArea();
        String historicalSubheadingText = editorTextPage().getElementsText(EditorTextPageElements.HISTORICAL_NOTE_SUBHEADING + "[2]" + EditorTextPageElements.HEADTEXT);
        //close document
        editorPage().closeDocumentAndDiscardChanges();
        //verify the Research Reference Subheading is pasted in as a Historical Note Subheading, and the same heading text markup appears in the text
        Assertions.assertEquals(historicalSubheadingText, researchReferenceText, String.format("Text new Historical Note Subheading is '%s' instead of '%s'",historicalSubheadingText,researchReferenceText));
    }

}
