package com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.source.set3;

import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.popups.TargetLocatorPageElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.RISK;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.switchEnums.ParagraphInsertMethods;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

public class InsertTargetLinkMarkupTests extends TestService
{
    /*Insert Target Link
     * Open the document (will be easier to test as a risk user because the risk user data is setup nicely for this)
     * Scroll to chunk 2 or 3
     * Insert a simple word and highlight
     * Right click and select Markup -> Insert Target Link Markup
     * Select one of the section target nodes beneath that and right click and select target
     * Verify: The Target field will be updated with the node number and value
     * Click Save (if a doc family message appears - we need to find a different target node to select (USCA has good content))
     * Verify: cite reference markup surrounds the text you had highlighted
     * Verify: cite reference markup has manual edit = yes
     * Verify: cite reference markup has westlaw display = go
     * Verify: cite reference markup has a non empty content set
     *
     * Please see existing tests to cross reference verifications
     */
    @Test
    @IE_EDGE_MODE
    @RISK
    @LOG
    public void insertTargetLinkMarkupSourceRiskTest()
    {
        String uuid = "I5ACFF5E23C9911E6AE74A75C58FEB5C9";
        sourcePage().goToSourcePageWithRenditionUuids(uuid);
        loginPage().logIn();
        sourceNavigateGridPage().firstRenditionEditContent();
        int targetChunk = 2;

        String firstParaSpan = String.format(EditorTextPageElements.TEXT_PARAGRAPH_FROM_CHUNK,
                targetChunk - 1, 1) + EditorTextPageElements.SPAN;

        String secondPara = String.format(EditorTextPageElements.TEXT_PARAGRAPH_FROM_CHUNK,
                targetChunk - 1, 2);
        String secondParaSpan = secondPara + EditorTextPageElements.SPAN;
        String secondParaParatext = secondPara + EditorTextPageElements.PARATEXT;

        // scroll to chunk
        editorPage().scrollToChunk(targetChunk);

        // add a phrase and mark it up
        String phrase = "TEST" + DateAndTimeUtils.getCurrentDateAndTimeyyyyMMddHHmmss();

        editorTextPage().click(firstParaSpan);
        editorPage().insertNewParagraphVia(ParagraphInsertMethods.CONTEXT_MENU, firstParaSpan);
        editorTextPage().waitForElementExists(secondParaParatext + "[text()='']");

        editorTextPage().insertPhraseUnderGivenLabelWithHome(phrase, secondParaSpan);
        editorTextPage().highlightHelper(phrase);

        editorPage().rightClick(String.format(EditorTextPageElements.TEXT_PARAGRAPH_CONTAINS_TEXT_GIVEN, phrase));
        editorPage().breakOutOfFrame();
        editorTextContextMenu().markupInsertTargetLinkMarkup();
        insertTargetCiteReferencePage().clickLocateTarget();
        String hierarchyNode = String.format(TargetLocatorPageElements.HIERARCHY_TREE_NODE,
                "Isle of Man FSA AML/CFT Handbook");
        insertTargetCiteReferencePage().click(hierarchyNode);
        insertTargetCiteReferencePage().getElement(hierarchyNode).sendKeys(Keys.SHIFT, Keys.F10);
        targetLocatorPage().selectNodeForTargetLinkMarkup();
        insertTargetCiteReferencePage().clickSave();

        // verify markup
        editorPage().switchDirectlyToTextFrame();
        boolean markupAdded = editorTextPage().doesElementExist(secondPara + EditorTextPageElements.PARATEXT
                + String.format(EditorTextPageElements.MANUAL_TARGET_LINK_TEXT, phrase));

        // close editor
        editorPage().closeEditorWithDiscardingChangesIfAppeared();

        Assertions.assertAll
                (
                        () -> Assertions.assertTrue(markupAdded,
                                "Added phrase should get wrapped with Target Link markup tag")
                );
    }
}
