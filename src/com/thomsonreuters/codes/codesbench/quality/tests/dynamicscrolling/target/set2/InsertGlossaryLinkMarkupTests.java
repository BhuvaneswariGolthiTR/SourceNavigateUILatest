package com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.target.set2;

import com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.editor.EditorTextContextMenuElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.popups.InsertGlossaryReferencePageElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.RISK;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class InsertGlossaryLinkMarkupTests extends TestService
{
    /*Insert Glossary Link for existing glossary node
     * This might be more of a risk user test than anything.  I'm not sure if legal users use this at all
     * 1. Risk user credentials
     * 2. FCA Handbook Content Set - contentSetId = 9169
     * 2.1 Glossary type nodes can be found: SET RB.FCA -> FCA Handbook -> Glossary -> Definitions (look under any children there)
     * ##########
     * 3. Open the document
     * 3.1. Scroll to chunk 2 or 3
     * 4. Insert and highlight a word that is a glossary node - talk with Jesse for the details
     * 5. Right click and go to Markup -> Insert glossary link
     * 6. This word should be wrapped in glossary link markup and we should not see a warning in the editor message pane
     *
     * Please see existing tests for additional verifications that need to be made
     */
    @Test
    @IE_EDGE_MODE
    @RISK
    @LOG
    public void insertGlossaryLinkForExistingGlossaryNodeTargetRiskTest()
    {
        String uuid = "I21740DF8FC9511E7A9C880000BA47767";
        hierarchyNavigatePage().goToHierarchyPage(ContentSets.FCA_HANDBOOK.getCode());
        loginPage().logIn();
        hierarchyNavigatePage().handleCasIssueWithHierarchyPageOpenedAsStartPageIfOccurred(ContentSets.FCA_HANDBOOK);
        hierarchySearchPage().searchNodeUuid(uuid);
        siblingMetadataPage().selectedSiblingMetadataEditContent();
        int targetChunk = 1;

        String firstParaSpan = String.format(EditorTextPageElements.TEXT_PARAGRAPH_FROM_CHUNK,
                targetChunk - 1, 1) + EditorTextPageElements.SPAN;
        String secondParaParatext = String.format(EditorTextPageElements.TEXT_PARAGRAPH_FROM_CHUNK,
                targetChunk - 1, 2) + EditorTextPageElements.PARATEXT;

        // add a phrase and mark it up
        String phrase = "accident";
        editorPage().scrollToChunk(targetChunk);

        editorTextPage().rightClick(firstParaSpan);
        editorTextPage().breakOutOfFrame();
        editorTextContextMenu().insertNewParagraphAltI();
        editorTextPage().switchToEditorTextArea();
        editorTextPage().waitForElementExists(secondParaParatext + "[text()='']");

        editorTextPage().insertPhraseUnderParaInChunk(phrase, 2, targetChunk);
        editorTextPage().highlightHelper(phrase);
        editorPage().rightClick(secondParaParatext);
        editorTextPage().breakOutOfFrame();
        editorTextContextMenu().openContextMenu(EditorTextContextMenuElements.MARKUP,
                EditorTextContextMenuElements.INSERT_GLOSSARY_LINK);

        // verify markup
        editorTextPage().switchToEditorTextArea();
        boolean markupAppeared = editorTextPage().doesElementExist(secondParaParatext
                + String.format(EditorTextPageElements.GLOSSARY_LINK_TEXT, phrase));

        editorPage().breakOutOfFrame();
        boolean warningLogMessage = editorMessagePage().checkMessagePaneForText(
                ": Unable to find the term '" + phrase + "' in the Glossary.");

        // close editor
        editorPage().closeDocumentAndDiscardChanges();
        Assertions.assertAll
                (
                        () -> Assertions.assertTrue(markupAppeared,
                                "Added phrase should get wrapped with markup tag"),
                        () -> Assertions.assertFalse(warningLogMessage,
                                "There should be no warning message in a log pane saying term not found")
                );
    }

    /*Insert Glossary Link for non-existing glossary node
     * This might be more of a risk user test than anything.  I'm not sure if legal users use this at all
     * 1. Risk user credentials
     * 2. Crown Dependencies or FCA content
     * ##########
     * 3. Open the document
     * 3.1. Scroll to chunk 2 or 3
     * 4. Insert a word (something that probably isn't being used as a glossary node)
     * 5. Right click and go to Markup -> Insert glossary link
     * 6. We should see a warning in the editor message pane and a UI should appear allowing us to select the node we want
     *
     * Please see existing tests for additional verifications that need to be made
     */
    @Test
    @IE_EDGE_MODE
    @RISK
    @LOG
    public void insertGlossaryLinkForNonExistingGlossaryNodeTargetRiskTest()
    {
        String uuid = "I21740DF8FC9511E7A9C880000BA47767";
        hierarchyNavigatePage().goToHierarchyPage(ContentSets.FCA_HANDBOOK.getCode());
        loginPage().logIn();
        hierarchyNavigatePage().handleCasIssueWithHierarchyPageOpenedAsStartPageIfOccurred(ContentSets.FCA_HANDBOOK);
        hierarchySearchPage().searchNodeUuid(uuid);
        siblingMetadataPage().selectedSiblingMetadataEditContent();
        int targetChunk = 1;

        String firstParaSpan = String.format(EditorTextPageElements.TEXT_PARAGRAPH_FROM_CHUNK,
                targetChunk - 1, 1) + EditorTextPageElements.SPAN;
        String secondParaParatext = String.format(EditorTextPageElements.TEXT_PARAGRAPH_FROM_CHUNK,
                targetChunk - 1, 2) + EditorTextPageElements.PARATEXT;

        // add a phrase and mark it up
        String phrase = "TEST" + DateAndTimeUtils.getCurrentDateAndTimeyyyyMMddHHmmss();
        editorPage().scrollToChunk(targetChunk);

        editorTextPage().rightClick(firstParaSpan);
        editorTextPage().breakOutOfFrame();
        editorTextContextMenu().insertNewParagraphAltI();
        editorTextPage().switchToEditorTextArea();
        editorTextPage().waitForElementExists(secondParaParatext + "[text()='']");

        editorTextPage().insertPhraseUnderParaInChunk(phrase, 2, targetChunk);
        editorTextPage().highlightHelper(phrase);
        editorPage().rightClick(secondParaParatext);
        editorTextPage().breakOutOfFrame();
        editorTextContextMenu().openContextMenu(EditorTextContextMenuElements.MARKUP,
                EditorTextContextMenuElements.INSERT_GLOSSARY_LINK);

        boolean glossaryWindowAppeared = editorPage().switchToWindow(InsertGlossaryReferencePageElements.PAGE_TITLE);
        Assertions.assertTrue(glossaryWindowAppeared, "Insert Glossary Reference window should appear");
        insertGlossaryReferencePage().enterTheInnerFrame();
        insertGlossaryReferencePage().clickCancel();
        editorPage().switchToEditor();

        // verify markup
        editorTextPage().switchToEditorTextArea();
        boolean markupNotAdded = !editorTextPage().doesElementExist(secondParaParatext
                + String.format(EditorTextPageElements.GLOSSARY_LINK_TEXT, phrase));

        editorPage().breakOutOfFrame();
        boolean warningLogMessage = editorMessagePage().checkMessagePaneForText(
                ": Unable to find the term '" + phrase + "' in the Glossary.");

        // close editor
        editorPage().closeDocumentAndDiscardChanges();
        Assertions.assertAll
                (
                        () -> Assertions.assertTrue(markupNotAdded,
                                "Added phrase should not get wrapped with markup tag"),
                        () -> Assertions.assertTrue(warningLogMessage,
                                "There should be a warning message in a log pane saying term not found")
                );
    }
}
