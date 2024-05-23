package com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.target.set3;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.HINT;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.SPAN_BY_TEXT;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTreePageElements.CORNERPIECE_CP;
import static com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets.WASHINGTON_DEVELOPMENT;
import static org.assertj.core.api.Assertions.assertThat;

public class InsertRecodifiedSetupTests extends TestService
{
    /**
     * STORY/BUG - n/a / HALCYONST-8915 and HALCYONST-9115 <br>
     * SUMMARY - Insert Text(child) - Document Setup - Recodified-setup <br>
     * USER - LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void insertRecodifiedSetupTargetLegalTest()
    {
        String uuid = "I9933A44078DF11EA9E18CEAE0ECBC689";
        String cornerpiece = String.format(SPAN_BY_TEXT, "Cornerpiece");
        String endOfText = String.format(SPAN_BY_TEXT, "End of Text");
        String annotations = String.format(SPAN_BY_TEXT, "Annotations");

        //Go to hierarchy page, login and open target document in dynamic scrolling editor
        hierarchyNavigatePage().goToHierarchyPage(WASHINGTON_DEVELOPMENT.getCode());
        loginPage().logIn();
        hierarchyNavigatePage().handleCasIssueWithHierarchyPageOpenedAsStartPageIfOccurred(WASHINGTON_DEVELOPMENT);
        hierarchySearchPage().searchNodeUuid(uuid);
        siblingMetadataPage().selectedSiblingMetadataEditContent();
        editorPage().switchToEditorTextFrame();

        //Get count of Cornerpiece, End of Text, Annotations, hint elements before Recodified - setup insertion
        int cornerpiecesBeforeInsertion = editorTextPage().countElements(cornerpiece);
        int endOfTextBeforeInsertion = editorTextPage().countElements(endOfText);
        int annotationsBeforeInsertion = editorTextPage().countElements(annotations);
        int hintsBeforeInsertion = editorTextPage().countElements(HINT);

        //Insert Text (child) -> Document Setup -> Recodified - setup
        editorTextPage().rightClick(String.format(SPAN_BY_TEXT, "Section"));
        editorTextPage().breakOutOfFrame();
        editorTextContextMenu().insertRecodifiedSetup();
        editorPage().switchDirectlyToTextFrame();

        //Get count of Cornerpiece, End of Text, Annotations elements after Recodified - setup insertion
        int cornerpiecesAfterInsertion = editorTextPage().countElements(cornerpiece);
        int endOfTextAfterInsertion = editorTextPage().countElements(endOfText);
        int annotationsAfterInsertion = editorTextPage().countElements(annotations);

        //Assert that Recodified - setup inserted
        assertThat(cornerpiecesAfterInsertion > cornerpiecesBeforeInsertion
                && endOfTextAfterInsertion > endOfTextBeforeInsertion
                && annotationsAfterInsertion > annotationsBeforeInsertion
                && editorTextPage().countElements(HINT) > hintsBeforeInsertion)
                .as("Recodified - setup isn't inserted successfully")
                .isTrue();

        //Replace all hints
        editorTextPage().replaceAllHintsWithText(String.format("%s", System.currentTimeMillis()));
        //Assert that all hints were replaced
        assertThatCountHintIsZero();

        //Select second cornerpiece in editor tree and its following siblings
        editorPage().switchToEditor();
        editorTreePage().getElements(CORNERPIECE_CP).get(1).click();
        editorTreePage().highlightFollowingSiblings(4);

        //Delete selected content
        editorTreePage().sendKeys(Keys.DELETE);
        editorPage().switchDirectlyToTextFrame();

        //Assert that selected content was deleted and Recodified-setup still appears in the content
        assertThat(editorTextPage().countElements(cornerpiece) == cornerpiecesAfterInsertion - cornerpiecesBeforeInsertion
                && editorTextPage().countElements(endOfText) == endOfTextAfterInsertion - endOfTextBeforeInsertion
                && editorTextPage().countElements(annotations) == annotationsAfterInsertion - annotationsBeforeInsertion)
                .as("Deleting of source part of the document text isn't successful")
                .isTrue();
        //Assert that inserted content into hints doesn't revert back to hints
        assertThatCountHintIsZero();

        //Click pub tag refresh button
        editorPage().switchToEditor();
        editorToolbarPage().clickPubtagRefreshButton();

        //Assert that pub tag refreshing finished without error
        assertThat(editorMessagePage().checkForErrorInMessagePane())
                .as("Message pane shouldn't contain errors after pub tag refreshing")
                .isFalse();

        editorPage().closeDocumentAndDiscardChanges();
    }

//  ------------- Assistive methods: -------------

    private void assertThatCountHintIsZero()
    {
        assertThat(editorTextPage().countElements(HINT))
                .as(String.format("Count of [%s] element isn't zero", HINT))
                .isZero();
    }
}
