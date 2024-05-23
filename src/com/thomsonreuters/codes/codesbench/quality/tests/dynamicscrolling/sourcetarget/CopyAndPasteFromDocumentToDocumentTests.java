package com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.sourcetarget;

import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorToolbarPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.popups.ViewBaselinesPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.base.AbstractSourceTargetTests;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.robot.RobotUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.util.Map;

import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.*;
import static com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.base.AbstractSourceTargetTests.DocumentInfo.EFFECTIVE_DATE;
import static com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.base.AbstractSourceTargetTests.DocumentInfo.PREP_DOCUMENT_INFO;
import static com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets.IOWA_DEVELOPMENT;
import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;

public class CopyAndPasteFromDocumentToDocumentTests extends AbstractSourceTargetTests
{
    private static final String IOWA_TARGET_NODE_1 = "I0BA39CC0158011DA8AC5CD53670E6B4E";
    private static final String IOWA_TARGET_NODE_2 = "I0BA830A0158011DA8AC5CD53670E6B4E";
    private static final String IOWA_SOURCE_RENDITION_1 = "I5CD15FF0445011EB89548152343E66A9";
    private static final String IOWA_SOURCE_RENDITION_2 = "IED670DA0630D11EAA261BA88BE11CA40";
    private static final String MERGED_TO = HIGHLIGHTED_PARA + MERGED_TO_TAG;
    private static final String TEXT_PARAGRAPH_SPAN_2 = format("(%s)[2]", TEXT_PARAGRAPH_SPAN);

    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void copyTextParagraphBlockFromSourceDocAndPasteToSourceDoc()
    {
        //1. Go to Source Navigate
        //2. Filter for Source Doc 1 and edit it via the DS editor
        sourcePage().goToSourcePageWithRenditionUuids(IOWA_SOURCE_RENDITION_1);
        sourceNavigateGridPage().firstRenditionEditContent();

        //3. Filter for Source Doc 2 and edit it via the DS editor
        pendingRenditionNavigatePage().switchToPendingRenditionNavigatePage();
        Map<DocumentInfo, String> documentInfo =
                openSourceRenditionWithSpecifiedUuidAndReturnDocumentInfoAsMap(IOWA_SOURCE_RENDITION_2);

        //4. Copy a Text Paragraph from Source Doc 1
        copySecondTextParagraph();

        //5. Paste the Text Paragraph to Source Doc 2 as a sibling to another Text Paragraph
        pasteTextParagraphAsSiblingToTheFirstTextParagraph();

        //Assert that the Text Paragraph from Source Doc 1 does not contain a merged to tag telling the user
        // the Text Paragraph was merged to Source Doc 2
        // NOTE: This Text Paragraph may contain a pre-existing merged to tag, but it should not reference Source Doc 2
        assertThatParagraphDoesNotContainMergedToTag(documentInfo);

        //Assert that the Text Paragraph from Source Doc 1 does not contain a modified by tag with the user’s name and current date
        assertThatParagraphDoesNotContainModifiedByTagWithUserNameAndCurrentDate();

        //Assert that the pasted Text Paragraph in Source Doc 2 does not contain a merged from tag telling the user
        // the Text Paragraph was merged from Source Doc 1
        switchToSecondDocument();
        assertThatParagraphDoesNotContainMergedFromTag();

        //Assert that the pasted Text Paragraph in Source Doc 2 does contain a modified by tag with
        // the user’s name and current date
        assertThatParagraphContainsModifiedByTagWithUserNameAndCurrentDate();

        //Assert that there are no errors in the message pane
        assertThatThereAreNoErrorsInBothMessagePanes();

        //6. Close and check-in changes for Source Doc 2
        switchToSecondDocument();
        editorPage().closeAndCheckInChanges();

        //7. Reopening this document should show the baggage tags
        reopenCurrentSourceRendition();

        //Assert that the pasted Text Paragraph in Source Doc 2 does contain a modified by tag with
        // the user’s name and current date
        switchToSecondDocumentAndSelect(TEXT_PARAGRAPH_SPAN_2);
        assertThatParagraphContainsModifiedByTagWithUserNameAndCurrentDate();
        editorPage().closeDocumentWithNoChanges();

        //8. Restore original baseline for Source Doc 2
        restoreOriginalBaseline();
    }

    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void copyTextParagraphBlockFromSourceDocAndPasteToTargetDoc()
    {
        //1. Go to Source Navigate
        //2. Filter for Source Doc 1 and edit it via the DS editor
        Map<DocumentInfo, String> documentInfo =
                openSourceRenditionWithSpecifiedUuidAndReturnDocumentInfoAsMap(IOWA_SOURCE_RENDITION_1);

        //restoreOriginalBaseline();

        //3. Filter for Target Doc 1 and edit it via the DS editor
        String nodeValue = openTheFirstTargetNodeInDsEditorAndReturnNodeValue();

        //editorPage().closeAllOpenedDocumentsWithDiscardingChangesIfAppeared();

        //4. Automatic Credit Generation may want to run after step 6 if you have it turned on in the editor preferences
        // You can dismiss the alert asking if you want to "Run user assisted credit generation now?" after step 6
        // or turn it off in step 4
        turnOffAutomaticCreditGeneration();

        //5. Copy a Text Paragraph from Source Doc 1
        copySecondTextParagraph();

        //6. Paste the Text Paragraph to Target Doc 1 as a sibling to another Text Paragraph
        pasteTextParagraphAsSiblingToTheFirstTextParagraph();

        //Assert that the TextParagraph from Source Doc 1 is followed by a merged to tag telling the user
        // the Text Paragraph was merged to Target Doc 1
        // NOTE: This Text Paragraph may contain a pre-existing merged to tag referencing a different Target Doc, and that is ok
        switchToFirstDocument();
        editorTextPage().scrollToView(TEXT_PARAGRAPH_SPAN_2);
        editorTextPage().click(TEXT_PARAGRAPH_SPAN_2);
        assertThatParagraphContainsMergedToTag(nodeValue);

        //Assert that the Text Paragraph from Source Doc 1 does contain a modified by tag with the user's name and current date
        assertThatParagraphContainsModifiedByTagWithUserNameAndCurrentDate();

        //Assert that the pasted Text Paragraph in Target Doc 1 is followed by a merged from tag telling the user
        // the Text Paragraph was merged from Source Doc 1
        switchToSecondDocument();
        assertThatParagraphContainsMergedFromTag(documentInfo);

        //Assert that the pasted Text Paragraph in Target Doc 1 does contain a modified by tag with the user's name and current date
        assertThatParagraphContainsModifiedByTagWithUserNameAndCurrentDate();

        //Assert that there are no errors in the message pane
        assertThatThereAreNoErrorsInBothMessagePanes();

        //7. Close and check-in changes for Source Doc 1
        switchToFirstDocument();
        editorPage().closeAndCheckInChanges();

        //8. Reopening this document should show the baggage tags (HALCYONST-15442)
        reopenTheFirstSourceRendition();

        //Assert that the TextParagraph from Source Doc 1 is followed by a merged to tag telling the user
        // the Text Paragraph was merged to Target Doc 1
        // NOTE: This Text Paragraph may contain a pre-existing merged to tag referencing a different Target Doc, and that is ok
        switchToSecondDocumentAndSelect(TEXT_PARAGRAPH_SPAN_2);
        assertThatParagraphContainsMergedToTag(nodeValue);

        //Assert that the Text Paragraph from Source Doc 1 does contain a modified by tag with the user's name and current date
        assertThatParagraphContainsModifiedByTagWithUserNameAndCurrentDate();
        editorPage().closeDocumentWithNoChanges();
        //9. Restore original baseline for Source Doc 1
        restoreOriginalBaseline();

        //10. Close and check-in changes for Target Doc 1
        switchToSecondDocument();
        editorPage().waitForPageLoaded();
        editorPage().closeAndCheckInChanges();

        //11. Reopening this document should show the baggage tags (HALCYONST-15442)
        pendingRenditionNavigatePage().switchToPendingRenditionNavigatePage();
        reopenTargetNodeWithUuid(IOWA_TARGET_NODE_1);

        //Assert that the pasted Text Paragraph in Target Doc 1 is followed by a merged from tag telling the user
        // the Text Paragraph was merged from Source Doc 1
        editorPage().switchToTheOpenedDocument(4);
        editorPage().switchToEditorTextFrameByIndex(1);
        editorTextPage().scrollToView(TEXT_PARAGRAPH_SPAN_2);
        editorTextPage().click(TEXT_PARAGRAPH_SPAN_2);
        assertThatParagraphContainsMergedFromTag(documentInfo);

        //Assert that the pasted Text Paragraph in Target Doc 1 does contain a modified by tag with the user's name and current date
        assertThatParagraphContainsModifiedByTagWithUserNameAndCurrentDate();
        editorPage().closeDocumentWithNoChanges();

        //12. Restore original WIP version for Target Doc 1
        restoreOriginalWipVersion();
    }

    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void copyTextFromSourceDocAndPasteToSourceDoc()
    {
        //1. Go to Source Navigate
        //2. Filter for Source Doc 1 and edit it via the DS editor
        sourcePage().goToSourcePageWithRenditionUuids(IOWA_SOURCE_RENDITION_1);
        sourceNavigateGridPage().firstRenditionEditContent();

        //3. Filter for Source Doc 2 and edit it via the DS editor
        pendingRenditionNavigatePage().switchToPendingRenditionNavigatePage();
        Map<DocumentInfo, String> documentInfo =
                openSourceRenditionWithSpecifiedUuidAndReturnDocumentInfoAsMap(IOWA_SOURCE_RENDITION_2);

        //4. Copy text from Text Paragraph from Source Doc 1
        copyTextFromTheSecondTextParagraph();

        //5. Paste text to the Text Paragraph to Source Doc 2
        pasteTextInTheFirstTextParagraph();

        //Assert that the Text Paragraph from Source Doc 1 does not contain a merged to tag telling the user
        // the Text Paragraph was merged to Source Doc 2
        // NOTE: This Text Paragraph may contain a pre-existing merged to tag, but it should not reference Source Doc 2
        assertThatParagraphDoesNotContainMergedToTag(documentInfo);

        //Assert that the Text Paragraph does not contain a modified by tag with the user’s name and current date
        assertThatParagraphDoesNotContainModifiedByTagWithUserNameAndCurrentDate();

        //Assert that the Text Paragraph with pasted text in Source Doc 2 does not contain a merged from tag telling the user
        // the Text Paragraph was merged from Source Doc 1
        switchToSecondDocument();
        editorTextPage().click(format(TEXT_PARAGRAPH_SPAN_UNDER_NUMBER, 1));

        assertThatParagraphDoesNotContainMergedFromTag();

        //Assert that the Text Paragraph with pasted text in Source Doc 2 does contain a modified by tag with
        // the user’s name and current date
        assertThatParagraphContainsModifiedByTagWithUserNameAndCurrentDate();

        //Assert that there are no errors in the message pane
        assertThatThereAreNoErrorsInBothMessagePanes();

        //6. Close and check-in changes for Source Doc 2
        switchToSecondDocument();
        editorPage().closeAndCheckInChangesAndHandleSpellcheckWindowIfAppeared();

        //7. Reopening this document should show the baggage tags
        reopenCurrentSourceRendition();

        //Assert that the Text Paragraph with pasted text in Source Doc 2 does contain a modified by tag with
        // the user’s name and current date
        switchToSecondDocumentAndSelect(format(TEXT_PARAGRAPH_SPAN_UNDER_NUMBER, 1));
        assertThatParagraphContainsModifiedByTagWithUserNameAndCurrentDate();
        editorPage().closeDocumentWithNoChanges();

        //8. Restore original baseline for Source Doc 2
        restoreOriginalBaseline();
    }

    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void copyTextFromSourceDocAndPasteToTargetDoc()
    {
        //1. Go to Source Navigate
        //2. Filter for Source Doc 1 and edit it via the DS editor
        Map<DocumentInfo, String> documentInfo =
                openSourceRenditionWithSpecifiedUuidAndReturnDocumentInfoAsMap(IOWA_SOURCE_RENDITION_1);

        //3. Filter for Target Doc 1 and edit it via the DS editor
        String nodeValue = openTheFirstTargetNodeInDsEditorAndReturnNodeValue();

        //4. Automatic Credit Generation may want to run after step 6 if you have it turned on in the editor preferences
        // You can dismiss the alert asking if you want to "Run user assisted credit generation now?" after step 6
        // or turn it off in step 4
        turnOffAutomaticCreditGeneration();

        //5. Copy text from Text Paragraph from Source Doc 1
        copyTextFromTheSecondTextParagraph();

        //6. Paste text to Text Paragraph to Target Doc 1
        pasteTextInTheFirstTextParagraph();

        //Assert that the TextParagraph from Source Doc 1 is followed by a merged to tag telling the user
        // the Text Paragraph was merged to Target Doc 1
        // NOTE: This Text Paragraph may contain a pre-existing merged to tag referencing a different Target Doc, and that is ok
        switchToFirstDocument();
        editorTextPage().scrollToView(TEXT_PARAGRAPH_SPAN_2);
        editorTextPage().click(TEXT_PARAGRAPH_SPAN_2);

        assertThatParagraphContainsMergedToTag(nodeValue);

        //Assert that the Text Paragraph from Source Doc 1 does contain a modified by tag with the user's name and current date
        assertThatParagraphContainsModifiedByTagWithUserNameAndCurrentDate();

        //Assert that the Text Paragraph with pasted text in Target Doc 1 is followed by a merged from tag telling the user
        // the Text Paragraph was merged from Source Doc 1
        switchToSecondDocument();
        editorTextPage().scrollToView(TEXT_PARAGRAPH_SPAN);
        editorTextPage().click(TEXT_PARAGRAPH_SPAN);
        assertThatParagraphContainsMergedFromTag(documentInfo);

        //Assert that the Text Paragraph with pasted text in Target Doc 1 does contain a modified by tag with the user's name and current date
        assertThatParagraphContainsModifiedByTagWithUserNameAndCurrentDate();

        //Assert that there are no errors in the message pane
        assertThatThereAreNoErrorsInBothMessagePanes();

        //7. Close and check-in changes for Source Doc 1
        switchToFirstDocument();
        editorPage().closeAndCheckInChangesAndHandleSpellcheckWindowIfAppeared();

        //8. Reopening this document should show the baggage tags (HALCYONST-15442)
        reopenTheFirstSourceRendition();

        //Assert that the TextParagraph from Source Doc 1 is followed by a merged to tag telling the user
        // the Text Paragraph was merged to Target Doc 1
        // NOTE: This Text Paragraph may contain a pre-existing merged to tag referencing a different Target Doc, and that is ok
        switchToSecondDocumentAndSelect(TEXT_PARAGRAPH_SPAN);
        assertThatParagraphContainsMergedToTag(nodeValue);

        //Assert that the Text Paragraph from Source Doc 1 does contain a modified by tag with the user's name and current date
        assertThatParagraphContainsModifiedByTagWithUserNameAndCurrentDate();
        editorPage().closeDocumentWithNoChanges();

        //9. Restore original baseline for Source Doc 1
        restoreOriginalBaseline();

        //10. Close and check-in changes for Target Doc 1
        switchToSecondDocument();
        editorPage().waitForPageLoaded();
        editorPage().closeAndCheckInChangesAndHandleSpellcheckWindowIfAppeared();

        //11. Reopening this document should show the baggage tags (HALCYONST-15442)
        pendingRenditionNavigatePage().switchToPendingRenditionNavigatePage();
        reopenTargetNodeWithUuid(IOWA_TARGET_NODE_1);

        //Assert that the pasted Text Paragraph in Target Doc 1 is followed by a merged from tag telling the user
        // the Text Paragraph was merged from Source Doc 1
        editorPage().switchToTheOpenedDocument(4);
        editorPage().switchToEditorTextFrameByIndex(1);
        editorTextPage().scrollToView(TEXT_PARAGRAPH_SPAN);
        editorTextPage().click(TEXT_PARAGRAPH_SPAN);
        assertThatParagraphContainsMergedFromTag(documentInfo);

        //Assert that the pasted Text Paragraph in Target Doc 1 does contain a modified by tag with the user's name and current date
        assertThatParagraphContainsModifiedByTagWithUserNameAndCurrentDate();
        editorPage().closeDocumentWithNoChanges();

        //12. Restore original WIP version for Target Doc 1
        restoreOriginalWipVersion();
    }

    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void copyTextParagraphBlockFromTargetDocAndPasteToTargetDoc()
    {
        //1. Go to Hierarchy Navigate
        //2. Filter for Target Doc 1 and edit it via the DS editor
        hierarchyNavigatePage().goToHierarchyPage(IOWA_DEVELOPMENT.getCode());
        openTargetNodeInDsEditorWithUuid(IOWA_TARGET_NODE_1);

        //3. Filter for Target Doc 2 and edit it via the DS editor
        hierarchyNavigatePage().switchToHierarchyEditPage();
        openTargetNodeInDsEditorWithUuid(IOWA_TARGET_NODE_2);

        //4. Copy a Text Paragraph from Target Doc 1
        copySecondTextParagraph();

        //5. Paste the Text Paragraph to Target Doc 2 as a sibling to another Text Paragraph
        pasteTextParagraphAsSiblingToTheFirstTextParagraph();

        //Assert that the Text Paragraph from Target Doc 1 does not contain a merged to tag telling the user
        // the Text Paragraph was merged to Target Doc 2
        assertThatParagraphDoesNotContainMergedToTag();

        //Assert that the Text Paragraph from Target Doc 1 does not contain a modified by tag with the user’s name and current date
        assertThatParagraphDoesNotContainModifiedByTagWithUserNameAndCurrentDate();

        //Assert that the pasted Text Paragraph in Target Doc 2 does not contain a merged from tag telling the user
        // the Text Paragraph was merged from Target Doc 1
        switchToSecondDocument();
        assertThatParagraphDoesNotContainMergedFromTag();

        //Assert that the pasted Text Paragraph in Target Doc 2 does contain a modified by tag with
        // the user’s name and current date
        assertThatParagraphContainsModifiedByTagWithUserNameAndCurrentDate();

        //Assert that there are no errors in the message pane
        assertThatThereAreNoErrorsInBothMessagePanes();

        //6. Close and check-in changes for Target Doc 2
        switchToSecondDocument();
        editorPage().closeAndCheckInChanges();

        //7. Reopening this document should show the baggage tags
        hierarchyNavigatePage().switchToHierarchyEditPage();
        reopenTargetNodeWithUuid(IOWA_TARGET_NODE_2);

        //Assert that the pasted Text Paragraph in Target Doc 2 does contain a modified by tag with
        // the user’s name and current date
        switchToSecondDocumentAndSelect(TEXT_PARAGRAPH_SPAN_2);
        assertThatParagraphContainsModifiedByTagWithUserNameAndCurrentDate();
        editorPage().closeDocumentWithNoChanges();

        //8. Restore original WIP version for Target Doc 2
        restoreOriginalWipVersion();
    }

    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void copyTextParagraphBlockFromTargetDocAndPasteToSourceDoc()
    {
        //1. Go to Hierarchy Navigate
        //2. Filter for Target Doc 1 and edit it via the DS editor
        hierarchyNavigatePage().goToHierarchyPage(IOWA_DEVELOPMENT.getCode());
        openTargetNodeInDsEditorWithUuid(IOWA_TARGET_NODE_1);

        //3. Filter for Source Doc 2 and edit it via the DS editor
        openTheSecondSourceRenditionInDsEditor();

        //4. Copy a Text Paragraph from Target Doc 1
        copySecondTextParagraph();

        //5. Paste the Text Paragraph to Source Doc 2 as a sibling to another Text Paragraph
        pasteTextParagraphAsSiblingToTheFirstTextParagraph();

        //Assert that the Text Paragraph from Target Doc 1 does not contain a merged to tag telling the user
        // the Text Paragraph was merged to Source Doc 2
        assertThatParagraphDoesNotContainMergedToTag();

        //Assert that the Text Paragraph from Target Doc 1 does not contain a modified by tag with the user’s name and current date
        assertThatParagraphDoesNotContainModifiedByTagWithUserNameAndCurrentDate();

        //Assert that the pasted Text Paragraph in Source Doc 2 does not contain a merged from tag telling the user
        // the Text Paragraph was merged from Target Doc 1
        switchToSecondDocument();
        assertThatParagraphDoesNotContainMergedFromTag();

        //Assert that the pasted Text Paragraph in Source Doc 2 does contain a modified by tag with
        // the user’s name and current date
        assertThatParagraphContainsModifiedByTagWithUserNameAndCurrentDate();

        //Assert that there are no errors in the message pane
        assertThatThereAreNoErrorsInBothMessagePanes();

        //6. Close and check-in changes for Source Doc 2
        switchToSecondDocument();
        editorPage().closeAndCheckInChanges();

        //7. Reopening this document should show the baggage tags
        reopenCurrentSourceRendition();

        //Assert that the pasted Text Paragraph in Source Doc 2 does contain a modified by tag with
        // the user’s name and current date
        switchToSecondDocumentAndSelect(TEXT_PARAGRAPH_SPAN_2);
        assertThatParagraphContainsModifiedByTagWithUserNameAndCurrentDate();
        editorPage().closeDocumentWithNoChanges();

        //8. Restore original baseline for Source Doc 2
        restoreOriginalBaseline();
    }

    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void copyTextFromTargetDocAndPasteToTargetDoc()
    {
        //1. Go to Hierarchy Navigate
        //2. Filter for Target Doc 1 and edit it via the DS editor
        hierarchyNavigatePage().goToHierarchyPage(IOWA_DEVELOPMENT.getCode());
        openTargetNodeInDsEditorWithUuid(IOWA_TARGET_NODE_1);

        //3. Filter for Target Doc 2 and edit it via the DS editor
        hierarchyNavigatePage().switchToHierarchyEditPage();
        openTargetNodeInDsEditorWithUuid(IOWA_TARGET_NODE_2);

        //4. Copy text from Text Paragraph from Target Doc 1
        copyTextFromTheSecondTextParagraph();

        //5. Paste text to the Text Paragraph to Target Doc 2
        pasteTextInTheFirstTextParagraph();

        //Assert that the Text Paragraph from Target Doc 1 does not contain a merged to tag telling the user
        // the Text Paragraph was merged to Target Doc 2
        assertThatParagraphDoesNotContainMergedToTag();

        //Assert that the Text Paragraph from Target Doc 1 does not contain a modified by tag with the user’s name and current date
        assertThatParagraphDoesNotContainModifiedByTagWithUserNameAndCurrentDate();

        //Assert that the Text Paragraph with pasted text in Target Doc 2 does not contain a merged from tag telling the user
        // the Text Paragraph was merged from Target Doc 1
        // switchToSecondDocumentAndSelect(TEXT_PARAGRAPH_SPAN);
        editorPage().switchToEditor();
        editorPage().switchToTheOpenedDocument(2);
        editorPage().switchToEditorTextFrameByIndex(3);
        editorTextPage().scrollToView(TEXT_PARAGRAPH_SPAN);
        editorTextPage().click(TEXT_PARAGRAPH_SPAN);
        assertThatParagraphDoesNotContainMergedFromTag();

        //Assert that the Text Paragraph with pasted text in Target Doc 2 does contain a modified by tag with
        // the user’s name and current date
        assertThatParagraphContainsModifiedByTagWithUserNameAndCurrentDate();

        //Assert that there are no errors in the message pane
        assertThatThereAreNoErrorsInBothMessagePanes();

        //6. Close and check-in changes for Target Doc 2
        switchToSecondDocument();
        editorPage().closeAndCheckInChangesAndHandleSpellcheckWindowIfAppeared();

        //7. Reopening this document should show the baggage tags
        hierarchyNavigatePage().switchToHierarchyEditPage();
        reopenTargetNodeWithUuid(IOWA_TARGET_NODE_2);

        //Assert that the Text Paragraph with pasted text in Target Doc 2 does contain a modified by tag with
        // the user’s name and current date
        switchToSecondDocumentAndSelect(TEXT_PARAGRAPH_SPAN);
        assertThatParagraphContainsModifiedByTagWithUserNameAndCurrentDate();
        editorPage().closeDocumentWithNoChanges();

        //8. Restore original WIP version for Target Doc 2
        restoreOriginalWipVersion();
    }

    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void copyTextFromTargetDocAndPasteToSourceDoc()
    {
        //1. Go to Hierarchy Navigate
        //2. Filter for Target Doc 1 and edit it via the DS editor
        hierarchyNavigatePage().goToHierarchyPage(IOWA_DEVELOPMENT.getCode());
        openTargetNodeInDsEditorWithUuid(IOWA_TARGET_NODE_1);

        //3. Filter for Source Doc 2 and edit it via the DS editor
        openTheSecondSourceRenditionInDsEditor();

        //4. Copy text from Text Paragraph from Target Doc 1
        copyTextFromTheSecondTextParagraph();

        //5. Paste text to the Text Paragraph to Source Doc 2
        pasteTextInTheFirstTextParagraph();

        //Assert that the Text Paragraph from Target Doc 1 does not contain a merged to tag telling the user
        // the Text Paragraph was merged to Source Doc 2
        assertThatParagraphDoesNotContainMergedToTag();

        //Assert that the Text Paragraph from Target Doc 1 does not contain a modified by tag with the user’s name and current date
        assertThatParagraphDoesNotContainModifiedByTagWithUserNameAndCurrentDate();

        //Assert that the pasted Text Paragraph in Source Doc 2 does not contain a merged from tag telling the user
        // the Text Paragraph was merged from Target Doc 1
        editorPage().switchToEditor();
        editorPage().switchToTheOpenedDocument(2);
        editorPage().switchToEditorTextFrameByIndex(3);
        editorTextPage().scrollToView(TEXT_PARAGRAPH_SPAN);
        editorTextPage().click(TEXT_PARAGRAPH_SPAN);
        assertThatParagraphDoesNotContainMergedFromTag();

        //Assert that the pasted Text Paragraph in Source Doc 2 does contain a modified by tag with
        // the user’s name and current date
        assertThatParagraphContainsModifiedByTagWithUserNameAndCurrentDate();

        //Assert that there are no errors in the message pane
        assertThatThereAreNoErrorsInBothMessagePanes();

        //6. Close and check-in changes for Source Doc 2
        switchToSecondDocument();
        editorPage().closeAndCheckInChangesAndHandleSpellcheckWindowIfAppeared();

        //7. Reopening this document should show the baggage tags
        reopenCurrentSourceRendition();

        //Assert that the pasted Text Paragraph in Source Doc 2 does contain a modified by tag with
        // the user’s name and current date
        switchToSecondDocumentAndSelect(TEXT_PARAGRAPH_SPAN);
        assertThatParagraphContainsModifiedByTagWithUserNameAndCurrentDate();
        editorPage().closeDocumentWithNoChanges();

        //8. Restore original baseline for Source Doc 2
        restoreOriginalBaseline();
    }

//  ------------- Assistive methods: -------------

    private void copySecondTextParagraph()
    {
        switchToFirstDocument();
        //selectparagraphforcopy(TEXT_PARAGRAPH_SPAN_2);
        openContextMenuOnElement(TEXT_PARAGRAPH_SPAN_2);
        editorTextContextMenu().copyCtrlC();
    }

    private void pasteTextParagraphAsSiblingToTheFirstTextParagraph()
    {
        switchToSecondDocument();
        //selectparagraphforpaste(TEXT_PARAGRAPH_SPAN);
        openContextMenuOnElement(TEXT_PARAGRAPH_SPAN);
        editorTextContextMenu().waitForPageLoaded();
        editorTextContextMenu().pasteSiblingCtrlV();
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);

    }

    private void copyTextFromTheSecondTextParagraph()
    {
        switchToFirstDocument();
        editorTextPage().placeCursorBehindTheTextInTextParagraphUsingRobot(TEXT_PARAGRAPH_SPAN_2);
        editorTextPage().highlightHelperUsingRobot(30);
        editorTextPage().waitForPageLoaded();
        RobotUtils.ctrlCUsingRobot();
        editorTextPage().waitForPageLoaded();
    }

    private void pasteTextInTheFirstTextParagraph()
    {
        switchToSecondDocument();
        editorTextPage().placeCursorBehindTheTextInTextParagraphUsingRobot(TEXT_PARAGRAPH_SPAN);
        editorTextPage().waitForPageLoaded();
        RobotUtils.ctrlVUsingRobot();
    }

    private void placeCursorBehindTheTextInTextParagraph(String xPath)
    {
        editorTextPage().click(xPath);
        editorTextPage().waitForElement(HIGHLIGHTED_PARA);
        editorTextPage().sendKeys(Keys.ARROW_DOWN);
        editorTextPage().waitForElementGone(HIGHLIGHTED_PARA);
        editorTextPage().sendKeys(Keys.HOME);
    }

    private String openTheFirstTargetNodeInDsEditorAndReturnNodeValue()
    {
        pendingRenditionNavigatePage().switchToPendingRenditionNavigatePage();
        hierarchyNavigatePage().goToHierarchyPage(IOWA_DEVELOPMENT.getCode());
        hierarchySearchPage().searchNodeUuid(IOWA_TARGET_NODE_1);
        hierarchyNavigatePage().switchToHierarchyEditPage();
        String nodeValue = siblingMetadataPage().getSelectedNodeValue();
        siblingMetadataPage().selectedSiblingMetadataEditContent();
        switchToSecondDocument();
        return nodeValue;
    }

    private void openTargetNodeInDsEditorWithUuid(String nodeUuid)
    {
        hierarchySearchPage().searchNodeUuid(nodeUuid);
        hierarchyNavigatePage().switchToHierarchyEditPage();
        siblingMetadataPage().selectedSiblingMetadataEditContent();
    }

    private void openTheSecondSourceRenditionInDsEditor()
    {
        hierarchyNavigatePage().switchToHierarchyEditPage();
        sourcePage().goToSourcePageWithRenditionUuids(IOWA_SOURCE_RENDITION_2);
        sourceNavigateGridPage().firstRenditionEditContent();
    }

    private void turnOffAutomaticCreditGeneration()
    {
        editorPage().switchToEditor();
        editorToolbarPage().clickConfigureEditorSessionPreferences();
        editorPreferencesPage().clickAutomaticCreditGenerationNoRadioButton();
        editorPreferencesPage().clickSaveButton();
    }

    private void assertThatParagraphContainsMergedToTag(String nodeValue)
    {
        editorTextPage().click(TEXT_PARAGRAPH_SPAN_2);
        assertThat(editorTextPage().getElementsTextList(MERGED_TO + "//md.reference.doc"))
                .as(format("The Text Paragraph should be followed by a 'Merged to' tag telling the user" +
                        "the Text Paragraph was merged to Target Doc with value [%s]", nodeValue))
                .anyMatch(mergedTo -> mergedTo.contains(nodeValue));
    }

    private void assertThatParagraphDoesNotContainMergedToTag(Map<DocumentInfo, String> documentInfo)
    {
        switchToFirstDocument();
        editorTextPage().click(TEXT_PARAGRAPH_SPAN_2);
        if (editorTextPage().doesElementExist(MERGED_TO))
        {
            editorTextPage().getElementsTextList(MERGED_TO + "//md.reference.doc")
                    .forEach(mergedTo -> assertThat(mergedTo)
                            .as(format("[%s] should not contain [%s] and [%s]",
                                    mergedTo, documentInfo.get(PREP_DOCUMENT_INFO), documentInfo.get(EFFECTIVE_DATE)))
                            .doesNotContain(documentInfo.get(PREP_DOCUMENT_INFO))
                            .doesNotContain(documentInfo.get(EFFECTIVE_DATE)));
        }
    }

    private void assertThatParagraphDoesNotContainMergedToTag()
    {
        switchToFirstDocument();
        editorTextPage().click(TEXT_PARAGRAPH_SPAN_2);
        assertThat(editorTextPage().doesElementExist(MERGED_TO))
                .as("'Merged to' tag should not exist")
                .isFalse();
    }

    private void assertThatParagraphContainsModifiedByTagWithUserNameAndCurrentDate()
    {
        assertThat(editorTextPage().doesElementExist(HIGHLIGHTED_PARA + editorTextPage().getModifiedByXpath(user())))
                .as("'Modified by' tag with the user's name and current date should exist")
                .isTrue();
    }

    private void assertThatParagraphDoesNotContainModifiedByTagWithUserNameAndCurrentDate()
    {
        assertThat(editorTextPage().doesElementExist(HIGHLIGHTED_PARA + editorTextPage().getModifiedByXpath(user())))
                .as("'Modified by' tag with the user's name and current date should not exist")
                .isFalse();
    }

    private void assertThatParagraphContainsMergedFromTag(Map<DocumentInfo, String> documentInfo)
    {
        assertThat(editorTextPage().getElementsText(HIGHLIGHTED_PARA + MERGED_FROM_TAG))
                .as(format("The pasted Text Paragraph should be followed by a 'Merged from tag' with [%s] and [%s]",
                        documentInfo.get(PREP_DOCUMENT_INFO), documentInfo.get(EFFECTIVE_DATE)))
                .contains(documentInfo.get(PREP_DOCUMENT_INFO))
                .contains(documentInfo.get(EFFECTIVE_DATE));
    }

    private void assertThatParagraphDoesNotContainMergedFromTag()
    {
        assertThat(editorTextPage().doesElementExist(HIGHLIGHTED_PARA + MERGED_FROM_TAG))
                .as("'Merged from' tag should not exist")
                .isFalse();
    }

    private void assertThatThereAreNoErrorsInBothMessagePanes()
    {
        editorPage().switchToEditor();
        assertThat(editorMessagePage().checkForErrorInBothMessagePanes())
                .as("There should be no errors in the message panes")
                .isFalse();
    }

    private void restoreOriginalBaseline()
    {
        pendingRenditionNavigatePage().switchToPendingRenditionNavigatePage();
        sourceNavigateGridPage().rightClickFirstRendition();
        sourceNavigateGridPage().breakOutOfFrame();
        sourceNavigateContextMenu().viewRenditionBaselines();
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        viewBaselinesNavigatePage().switchToViewBaselinesPage();
        viewBaselinesNavigatePage().rightClickOriginalBaseline();
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        viewBaselinesContextMenu().restoreBaseline();
        viewBaselinesNavigatePage().breakOutOfFrame();
        viewBaselinesNavigatePage().closeWindow();
    }

    private void restoreOriginalWipVersion()
    {
        hierarchyNavigatePage().switchToHierarchyEditPage();
        siblingMetadataPage().rightClickSelectedNode();
        siblingMetadataPage().breakOutOfFrame();
        siblingMetadataContextMenu().viewModifyPreviousWIPVersion();
        previousWipVersionsPage().restoreOriginalWIPVersion();
        setLawTrackingPage().clickQuickLoadTrackingButton();
        setLawTrackingPage().clickOkButton();
    }

    private void reopenTheFirstSourceRendition()
    {
        hierarchyNavigatePage().switchToHierarchyEditPage();
        sourcePage().goToSourcePageWithRenditionUuids(IOWA_SOURCE_RENDITION_1);
        sourceNavigateGridPage().firstRenditionEditContent();
    }

    private void reopenCurrentSourceRendition()
    {
        pendingRenditionNavigatePage().switchToPendingRenditionNavigatePage();
        sourceNavigateGridPage().firstRenditionEditContent();
    }

    private void reopenTargetNodeWithUuid(String nodeUuid)
    {
        hierarchyNavigatePage().goToHierarchyPage(IOWA_DEVELOPMENT.getCode());
        hierarchySearchPage().searchNodeUuid(nodeUuid);
        hierarchyNavigatePage().switchToHierarchyEditPage();
        siblingMetadataPage().selectedSiblingMetadataEditContent();
    }

    private void switchToSecondDocumentAndSelect(String xPath)
    {
        editorPage().switchToTheOpenedDocument(3);
        editorPage().switchToEditorTextFrameByIndex(3);
        editorTextPage().scrollToView(xPath);
        editorTextPage().click(xPath);
    }

    private void switchToFirstDocumentAndSelect(String xPath)
    {
        switchToFirstDocument();
        editorTextPage().click(xPath);
    }
}
