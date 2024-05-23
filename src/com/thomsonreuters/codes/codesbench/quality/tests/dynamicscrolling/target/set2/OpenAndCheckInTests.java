package com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.target.set2;

import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorMessagePageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorToolbarPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTreePageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.popups.DocumentClosurePageElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.FIRST_TEXT_PARAGRAPH;
import static com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import static com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;

public class OpenAndCheckInTests extends TestService
{


    /*Open and check-in changes no new version
     *  Open the document
     *  Scroll to chunk 2 or 3
     *  Insert simple text into a paragraph here
     *  Verify: Modified by tags appear
     *  Close and check-in changes
     *  Re-open the document
     *  Scroll to chunk 2 or 3
     *  Verify: The change is there
     */
    /**
     * STORY/BUG - migration <br>
     * SUMMARY -  Open and check-in changes no new version<br>
     * USER - Legal <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void checkInNoNewVersionTargetLegalTest()
    {
        String uuid = "I2A8B5B9014F511DA8AC5CD53670E6B4E";
        String phrase = "TEST" + DateAndTimeUtils.getCurrentDateAndTimeyyyyMMddHHmmss();
        String modifiedByExpected = editorTextPage().getModifiedByTag(this.user());

        hierarchyNavigatePage().goToHierarchyPage(ContentSets.IOWA_DEVELOPMENT.getCode());
        loginPage().logIn();
        hierarchyNavigatePage().handleCasIssueWithHierarchyPageOpenedAsStartPageIfOccurred(ContentSets.IOWA_DEVELOPMENT);

        // open DS editor
        hierarchySearchPage().searchNodeUuid(uuid);
        siblingMetadataPage().selectedSiblingMetadataEditContent();
        editorPage().switchToEditor();
        editorPage().closeEditorWithDiscardingChangesIfAppeared();

        hierarchyNavigatePage().switchToHierarchyEditPage();
        siblingMetadataPage().selectedSiblingMetadataEditContent();
        int targetChunk = 2;

        // scroll to chunk
        editorPage().scrollToChunk(targetChunk);

        String firstPara = String.format(EditorTextPageElements.TEXT_PARAGRAPH_FROM_CHUNK,
                targetChunk - 1, 1);
        String firstParaSpan = firstPara + EditorTextPageElements.SPAN;

        String secondPara = String.format(EditorTextPageElements.TEXT_PARAGRAPH_FROM_CHUNK,
                targetChunk - 1, 2);
        String secondParaSpan = secondPara + EditorTextPageElements.SPAN;
        String secondParaParatext = secondPara + EditorTextPageElements.PARATEXT;

        editorTextPage().scrollToView(firstParaSpan);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        editorTextPage().rightClick(firstParaSpan);
        editorPage().breakOutOfFrame();
        editorTextContextMenu().insertNewParagraphAltI();
        editorTextPage().switchToEditorTextArea();
        editorTextPage().waitForElementExists(secondParaParatext + "[text()='']");

        // insert a word
        editorTextPage().insertPhraseUnderGivenLabelWithHome(phrase, secondParaSpan);

        // check modified by
        String modifiedByActual = editorTextPage().getTheFirstParaModifiedByTagInTheChunk(targetChunk);

        //check-in
        editorPage().closeAndCheckInChanges();
        editorPage().switchToEditor();

        //open DS editor
        hierarchyNavigatePage().switchToHierarchyEditPage();
        siblingMetadataPage().selectedSiblingMetadataEditContent();

        //verify changes exist
        editorPage().scrollToChunk(targetChunk);
        String paraTextAfterCheckIn = editorTextPage().getElementsText(secondParaParatext);

        //put initial text back and close DS editor
        editorTextPage().click(secondParaSpan);
        editorTextPage().rightClick(secondParaSpan);
        editorTextPage().breakOutOfFrame();
        editorTextContextMenu().delete();

        editorPage().closeAndCheckInChanges();

        Assertions.assertAll
                (
                        () -> Assertions.assertEquals(modifiedByActual, modifiedByExpected,
                                String.format("Modified By tag should contain right name and date. " +
                                        "Expected: %s. Actual: %s", modifiedByExpected, modifiedByActual)),
                        () -> Assertions.assertEquals( phrase, paraTextAfterCheckIn,
                                "Changes HAVE NOT BEEN SAVED after check-in")
                );
    }

    /*Open and check-in changes new version
	 * Open the document
	 * Scroll to chunk 2 or 3
	 * Insert simple text into a paragraph here
	 * Verify: Modified by tags appear
	 * Close and check-in changes
	 * Set a future start effective date here (this will create a new version in the hierarchy)
	 * Go to Hierarchy Navigate and search for the node uuid
	 * Verify: Pull the value from this node and verify 2 of these appear in the grid
	 * Grab the node uuid from the second one
	 * Open this document in dynamic scrolling
	 * Scroll to chunk 2 or 3
	 * Verify: The change is there
	 * Delete the document that was created
	 */
    /**
     * STORY/BUG - migration <br>
     * SUMMARY -  Open and check-in changes with new version<br>
     * USER - Legal <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void checkInNewVersionTargetLegalTest()
    {
        String uuid = "IE5B9E260158111DA8AC5CD53670E6B4E";
        String phrase = "TEST" + DateAndTimeUtils.getCurrentDateAndTimeyyyyMMddHHmmss();
        String modifiedByExpected = editorTextPage().getModifiedByTag(this.user());

        hierarchyNavigatePage().goToHierarchyPage(ContentSets.IOWA_DEVELOPMENT.getCode());
        loginPage().logIn();
        hierarchyNavigatePage().handleCasIssueWithHierarchyPageOpenedAsStartPageIfOccurred(ContentSets.IOWA_DEVELOPMENT);

//         open DS editor
        hierarchySearchPage().searchNodeUuid(uuid);

        if(dispositionDerivationPage().areMultipleNodesShown())
        {
            dispositionDerivationPage().rightClickSelectNodeByNumber("1");
            dispositionDerivationContextMenu().updateMetadata();
            updateMetadataPage().clearEffectiveEndDate();
            updateMetadataPage().clickQuickLoadOk();
            hierarchyNavigatePage().switchToHierarchyEditPage();
        }
        while(dispositionDerivationPage().areMultipleNodesShown())
        {
            dispositionDerivationPage().rightClickSelectNodeByNumber("2");
            dispositionDerivationContextMenu().deleteFunctionsDelete();
            deletePage().clickQuickLoad();
            deletePage().clickSubmit();
            hierarchyNavigatePage().switchToHierarchyEditPage();
            hierarchySearchPage().searchNodeUuid(uuid);
        }
        siblingMetadataPage().selectedSiblingMetadataEditContent();
        editorPage().switchToEditor();
        editorPage().closeEditorWithDiscardingChangesIfAppeared();

        hierarchyNavigatePage().switchToHierarchyEditPage();
        siblingMetadataPage().selectedSiblingMetadataEditContent();
        int targetChunk = 1;

        // scroll to chunk
        editorPage().scrollToChunk(targetChunk);

        String firstPara = String.format(EditorTextPageElements.TEXT_PARAGRAPH_FROM_CHUNK,
                targetChunk - 1, 1);
        String firstParaSpan = firstPara + EditorTextPageElements.SPAN;

        // insert a word
        String originalText = editorTextPage().getTheFirstParaTextInTheChunk(targetChunk);
        editorTextPage().insertPhraseUnderGivenLabelWithHome(phrase, firstParaSpan);
        String paraTextAfterChange = editorTextPage().getTheFirstParaTextInTheChunk(targetChunk);

        //check-in
        editorPage().closeAndCheckInChangesWithGivenDate(DateAndTimeUtils.getCurrentDatePlusOneMonthMMddyyyy());
        editorPage().switchToEditor();

        //open DS editor
        hierarchyNavigatePage().switchToHierarchyEditPage();
        dispositionDerivationPage().rightClickSelectNodeByNumber("2");
        dispositionDerivationContextMenu().editContent();

        //verify changes exist
        editorPage().scrollToChunk(targetChunk);
        String paraTextAfterCheckIn = editorTextPage().getTheFirstParaTextInTheChunk(targetChunk);

        // check modified by
        String modifiedByActual = editorTextPage().getTheFirstParaModifiedByTagInTheChunk(targetChunk);

        //close DS editor without changes
        editorPage().closeDocumentWithNoChanges();
        editorPage().switchToEditor();

        //delete new version
        hierarchyNavigatePage().switchToHierarchyEditPage();
        dispositionDerivationPage().rightClickSelectNodeByNumber("1");
        dispositionDerivationContextMenu().updateMetadata();
        updateMetadataPage().clearEffectiveEndDate();
        updateMetadataPage().clickQuickLoadOk();
        hierarchyNavigatePage().switchToHierarchyEditPage();
        dispositionDerivationPage().rightClickSelectNodeByNumber("2");
        dispositionDerivationContextMenu().deleteFunctionsDelete();
        deletePage().clickQuickLoad();
        deletePage().clickSubmit();

        Assertions.assertAll
                (
                        () -> Assertions.assertEquals( phrase + " " + originalText, paraTextAfterChange,
                                "The para HAS NOT BEEN changed"),
                        () -> Assertions.assertEquals(modifiedByActual,modifiedByExpected,
                                String.format("Modified By tag should contain right name and date. " +
                                        "Expected: %s. Actual: %s", modifiedByExpected, modifiedByActual)),
                        () -> Assertions.assertEquals( phrase + " " + originalText, paraTextAfterCheckIn,
                                "Changes HAVE NOT BEEN SAVED after check-in")
                );
    }

	/*Open and discard changes
	 * 1. Open the document
	 * 2. Verify: The document contains content in the tree
	 * 3. Verify: The document contains content in the editor content area
	 * 4. Verify: The document contains information in the message pane
	 * 5. Scroll to chunk 2 or 3
	 * 6. Insert simple text into a paragraph here
	 * 6.1. Verify: Modified by tags appear
	 * 7. Close and discard changes
	 * 8. Re-open the document
	 * 9. Scroll to chunk 2 or 3
	 * 10. Verify: The change is not there
	 */
    /**
     * STORY/BUG - migration <br>
     * SUMMARY -  Open and discard changes<br>
     * USER - Legal <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void openAndDiscardChangesTargetLegalTest()
    {
        String uuid = "I21F74DB0E6B711DA94DBD412285A4678";
        String phrase = "TEST" + DateAndTimeUtils.getCurrentDateAndTimeyyyyMMddHHmmss();
        String modifiedByExpected = editorTextPage().getModifiedByTag(this.user());

        hierarchyNavigatePage().goToHierarchyPage(ContentSets.IOWA_DEVELOPMENT.getCode());
        loginPage().logIn();
        hierarchyNavigatePage().handleCasIssueWithHierarchyPageOpenedAsStartPageIfOccurred(ContentSets.IOWA_DEVELOPMENT);

        // open DS editor
        hierarchySearchPage().searchNodeUuid(uuid);
        siblingMetadataPage().selectedSiblingMetadataEditContent();
        editorPage().switchToEditor();
        editorPage().closeEditorWithDiscardingChangesIfAppeared();

        hierarchyNavigatePage().switchToHierarchyEditPage();
        siblingMetadataPage().selectedSiblingMetadataEditContent();
        int targetChunk = 1;

        // check tree, textarea, message pane
        boolean treeContentIsPresented = editorPage().doesElementExist(EditorTreePageElements.TREE_NODE_LINK, 10);
        boolean messagePaneContentIsPresented = editorPage().doesElementExist(EditorMessagePageElements.INFO_SPAN, 10);

        // scroll
        editorPage().scrollToChunk(targetChunk);

        String firstPara = String.format(EditorTextPageElements.TEXT_PARAGRAPH_FROM_CHUNK,
                targetChunk - 1, 1);
        String firstParaSpan = firstPara + EditorTextPageElements.SPAN;

        boolean textAreaContentIsPresented = editorPage().doesElementExist(EditorTextPageElements.CHUNKS, 10);

        // insert a word
        String originalText = editorTextPage().getTheFirstParaTextInTheChunk(targetChunk);
        editorTextPage().insertPhraseUnderGivenLabelWithHome(phrase, firstParaSpan);
        String paraTextAfterChange = editorTextPage().getTheFirstParaTextInTheChunk(targetChunk);

        // check modified by
        String modifiedByActual = editorTextPage().getTheFirstParaModifiedByTagInTheChunk(targetChunk);

        //close and discard changes
        editorPage().closeDocumentAndDiscardChanges();
        editorPage().switchToEditor();

        //open DS editor
        hierarchyNavigatePage().switchToHierarchyEditPage();
        siblingMetadataPage().selectedSiblingMetadataEditContent();

        //verify changes exist
        editorPage().scrollToChunk(targetChunk);
        String paraTextAfterDiscardChanges = editorTextPage().getTheFirstParaTextInTheChunk(targetChunk);

        //discard changes and clos DS
        editorPage().closeDocumentWithNoChanges();

        Assertions.assertAll
                (
                        () -> Assertions.assertTrue(treeContentIsPresented,
                                "Tree should present some content"),
                        () -> Assertions.assertTrue(messagePaneContentIsPresented,
                                "Message Pane should present some content"),
                        () -> Assertions.assertTrue(textAreaContentIsPresented,
                                "Text Area should present some content"),
                        () -> Assertions.assertEquals( phrase + " " + originalText, paraTextAfterChange,
                                "The para HAS NOT BEEN changed"),
                        () -> Assertions.assertEquals(modifiedByActual,modifiedByExpected,
                                String.format("Modified By tag should contain right name and date. " +
                                        "Expected: %s. Actual: %s", modifiedByExpected, modifiedByActual)),
                        () -> Assertions.assertEquals( originalText, paraTextAfterDiscardChanges,
                                "Changes HAVE BEEN SAVED after discard changes")
                );
    }

	/*Open and save changes
	 * Open the document
	 * Scroll to chunk 2 or 3
	 * Insert simple text into a paragraph here
	 * Verify: Modified by tags appear
	 * Click the Save button in the toolbar
	 * Verify: The content change saves (verify message appears in message pane)
	 * Close the current window via the upper right close button
	 * Re-open the document
	 * Scroll to chunk 2 or 3
	 * Verify: The change is there
	 * Close and discard changes
	 */
    /**
     * STORY/BUG - migration <br>
     * SUMMARY -  Open and save changes<br>
     * USER - Legal <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void openAndSaveChangesTargetLegalTest()
    {
        String uuid = "I21F74DB0E6B711DA94DBD412285A4678";
        String phrase = "TEST" + DateAndTimeUtils.getCurrentDateAndTimeyyyyMMddHHmmss();
        String modifiedByExpected = editorTextPage().getModifiedByTag(this.user());

        hierarchyNavigatePage().goToHierarchyPage(ContentSets.IOWA_DEVELOPMENT.getCode());
        loginPage().logIn();
        hierarchyNavigatePage().handleCasIssueWithHierarchyPageOpenedAsStartPageIfOccurred(ContentSets.IOWA_DEVELOPMENT);

        // open DS editor
        hierarchySearchPage().searchNodeUuid(uuid);
        siblingMetadataPage().selectedSiblingMetadataEditContent();
        editorPage().switchToEditor();
        editorPage().closeEditorWithDiscardingChangesIfAppeared();

        hierarchyNavigatePage().switchToHierarchyEditPage();
        siblingMetadataPage().selectedSiblingMetadataEditContent();
        int targetChunk = 1;

        // scroll to chunck
        editorPage().scrollToChunk(targetChunk);

        String firstPara = String.format(EditorTextPageElements.TEXT_PARAGRAPH_FROM_CHUNK,
                targetChunk - 1, 1);
        String firstParaSpan = firstPara + EditorTextPageElements.SPAN;

        // insert a word
        String originalText = editorTextPage().getTheFirstParaTextInTheChunk(targetChunk);
        editorTextPage().insertPhraseUnderGivenLabelWithHome(phrase, firstParaSpan);
        String paraTextAfterChange = editorTextPage().getTheFirstParaTextInTheChunk(targetChunk);

        // check modified by
        String modifiedByActual = editorTextPage().getTheFirstParaModifiedByTagInTheChunk(targetChunk);

        // init save
        editorPage().switchToEditor();
        editorToolbarPage().clickSave();

        // reopen editor and check phrase inserted
        editorPage().switchToEditor();
        editorPage().closeCurrentWindowIgnoreDialogue();
        hierarchyNavigatePage().switchToHierarchyEditPage();
        siblingMetadataPage().selectedSiblingMetadataEditContent();
        editorPage().switchToEditor();
        editorPage().scrollToChunk(targetChunk);
        String paraTextAfterDiscardChanges = editorTextPage().getTheFirstParaTextInTheChunk(targetChunk);

        //discard changes and clos DS
        editorPage().closeDocumentAndDiscardChanges();

        Assertions.assertAll
                (
                        () -> Assertions.assertEquals( phrase + " " + originalText, paraTextAfterChange,
                                "The para HAS NOT BEEN changed"),
                        () -> Assertions.assertEquals(modifiedByActual,modifiedByExpected,
                                String.format("Modified By tag should contain right name and date. " +
                                        "Expected: %s. Actual: %s", modifiedByExpected, modifiedByActual)),
                        () -> Assertions.assertEquals( phrase + " " + originalText, paraTextAfterDiscardChanges,
                                "Changes HAVE NOT BEEN SAVED after discard changes")
                );
    }

    /*Open and save changes
     * Open the document
     * Select a paragraph
     * Perform copy and paste operation
     * Click on close doc from toolbar
     * Verify:
     * 1.Assign Effective Date field is blank
     * 2.Westlaw Date Suppress radio button is defaulted to OFF
     * 3.Set Law Tracking Current Value field is blank
     * 4.Full Vols, Full Vols-Compare, Full Vols-Recomp, and Quick Load are defaulted to not selected
     * Check-in the changes
     * Re-open the same doc
     * Select a paragraph
     * Perform cut-and-paste operation
     * Verify:
     * 1.Assign Effective Date field is blank
     * 2.Westlaw Date Suppress radio button is defaulted to OFF
     * 3.Set Law Tracking Current Value field is Quick Load
     * 4.Quick Load is still selected
     * Close and discard changes
     */
    /**
     * STORY/BUG - HALCYONST-12675 <br>
     * SUMMARY - Test case verifies default state for
     * -- Assign Effective Date as blank,
     * -- Westlaw Date Suppress is defaulted to OFF,
     * -- Set Law Tracking Current Value is blank,
     * -- Full Vols, Full Vols-Compare, Full Vols-Recomp, and
     * -- Quick Load fields are defaulted to not selected in Document Closure page<br>
     * USER - Legal <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void verifyLawTrackingInfo()
    {
        String uuid = "I7AE1A0B014EE11DA8AC5CD53670E6B4E";

        hierarchyNavigatePage().goToHierarchyPage(ContentSets.IOWA_DEVELOPMENT.getCode());
        loginPage().logIn();
        hierarchySearchPage().searchNodeUuid(uuid);
        siblingMetadataPage().selectedSiblingMetadataEditContent();
        editorPage().switchToEditor();
        editorPage().switchToEditorTextFrame();

        //Copy text paragraph and paste it, verify document closure page elements during check-in
        editorTextPage().rightClick(FIRST_TEXT_PARAGRAPH);
        editorTextPage().breakOutOfFrame();
        editorTextContextMenu().copyCtrlC();
        editorPage().switchToEditorTextFrame();
        editorTextPage().rightClick(FIRST_TEXT_PARAGRAPH);
        editorTextPage().breakOutOfFrame();
        editorTextContextMenu().pasteSiblingCtrlV();
        editorPage().switchToEditor();
        editorToolbarPage().clickToolbarClose();

        boolean effectiveDate = editorClosurePage().checkIfEffectiveDateFieldIsEmpty();
        Assertions.assertTrue(effectiveDate, "Assign Effective date field must be empty");

        boolean westlawDateSuppressIsOff = editorClosurePage().checkWestlawDateSuppressIsOff();
        Assertions.assertTrue(westlawDateSuppressIsOff, "Westlaw Date Suppress OFF radio button must be selected");

        boolean lawtrackingTextAreaIsEmpty = editorClosurePage().checkLawTrackingFieldIsEmpty();
        Assertions.assertTrue(lawtrackingTextAreaIsEmpty, "Law Tracking field must be empty");

        boolean fullVolRadioButton = editorClosurePage().checkFullVolIsNotSelected();
        Assertions.assertTrue(fullVolRadioButton,"The Radio button Full Vols must not be selected");

        boolean fullVolRecompRadioButton = editorClosurePage().checkFullVolRecompIsNotSelected();
        Assertions.assertTrue(fullVolRecompRadioButton,"The Radio button Full Vols-Recomp must not be selected");

        boolean fullVolCompareRadioButton = editorClosurePage().checkFullVolCompareIsNotSelected();
        Assertions.assertTrue(fullVolCompareRadioButton,"The Radio button Full Vols-Compare must not be selected");

        boolean quickLoadRadioButtonIsNotSelected = editorClosurePage().checkQuickLoadIsNotSelected();
        Assertions.assertTrue(quickLoadRadioButtonIsNotSelected,"The Radio button Quick Load must not be selected");

        //Quickload and checkin changes
        editorClosurePage().selectQuickLoad();
        editorClosurePage().clickCheckInChanges();

        //Re-open the same document
        siblingMetadataPage().selectedSiblingMetadataEditContent();
        editorPage().switchToEditor();
        editorPage().switchToEditorTextFrame();

        //Cut text paragraph and paste it, verify document closure page elements during closing the document
        editorTextPage().rightClick(FIRST_TEXT_PARAGRAPH);
        editorTextPage().breakOutOfFrame();
        editorTextContextMenu().cutCtrlX();
        editorPage().switchToEditorTextFrame();
        editorTextPage().rightClick(FIRST_TEXT_PARAGRAPH);
        editorTextPage().breakOutOfFrame();
        editorTextContextMenu().pasteSiblingCtrlV();
        editorToolbarPage().clickToolbarClose();

        boolean effectiveDateDuringDiscard = editorClosurePage().checkIfEffectiveDateFieldIsEmpty();
        Assertions.assertTrue(effectiveDateDuringDiscard, "Assign Effective date field must be empty");

        boolean westlawDateSuppressIsOffDuringDiscard = editorClosurePage().checkWestlawDateSuppressIsOff();
        Assertions.assertTrue(westlawDateSuppressIsOffDuringDiscard, "Westlaw Date Suppress OFF radio button must be selected");

        boolean lawtrackingTextAreaHasQuickLoad = editorClosurePage().checkLawTrackingFieldHasRequiredLoad("Quick Load");
        Assertions.assertTrue(lawtrackingTextAreaHasQuickLoad, "Law Tracking field must be Quick Load");

        boolean quickLoadRadioButtonIsSelected = editorClosurePage().checkQuickLoadIsSelected();
        Assertions.assertTrue(quickLoadRadioButtonIsSelected,"The Radio button Quick Load must be selected");

        // Discard the changes as the Document Closure page is already open
        editorClosurePage().clickDiscardChanges();
    }

}
