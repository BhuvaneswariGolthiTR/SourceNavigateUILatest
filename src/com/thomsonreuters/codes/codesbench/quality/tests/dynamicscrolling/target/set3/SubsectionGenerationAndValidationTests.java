package com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.target.set3;

import com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.editor.EditorTextContextMenuElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorToolbarPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.popups.InsertSpecialCharacterPageElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.autoIT.AutoITUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.RISK;
import com.thomsonreuters.codes.codesbench.quality.utilities.switchEnums.ParagraphInsertMethods;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.Keys;

import java.util.List;
import java.util.stream.Stream;

import static com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.*;
import static com.thomsonreuters.codes.codesbench.quality.utilities.enums.SpecialCharacters.EMSP;


public class SubsectionGenerationAndValidationTests extends TestService
{
    /*Subsection Validation
     *
     * 1. Open document
     * 2. Initially, we should have clean subsections
     * 3. Run subsection validation
     * VERIFY: Validation occurs and completes without errors
     * 4. Change the subsection value for one of the subsections to force a validation error
     * 5. Run subsection validation
     * VERIFY: We should see a subsection validataion error in the editor message pane
     * VERIFY: It will more than likely be a misnumbered subsection error
     * VERIFY: Clicking the link brings us to the effected subsection(s)
     */
    /**
     * STORY/BUG - migration <br>
     * SUMMARY -  Change the subsection value for one of the subsections to force a validation error<br>
     * USER - RISK <br>
     */
    @Test
    @IE_EDGE_MODE
    @RISK
    @LOG
    public void subsectionValidationTargetTest()
    {
//        String uuid = "I1CEC761040B011E793F600FF10C05B07"; for dev
        String uuid = "I471322C0213111E7A1D8180373BE7E39";
        int chunkNumber = 1;
        String validMessage = ": The documents subsections are valid.";
        String expectedErrorMessage = "error: Document has 1 subsection error(s).\n" +
                "error: Error: This document is invalid because at least one subsection identifier has been updated. " +
                "Either run Subsection Generation to complete the update, " +
                "or manually restore the subsection identifier if update was unintended.\n" +
                "error: Affected IDs:";
        String messageFromWebPage = "Subsection validation failed, continue with check-in?";
        String checkInCancellationMessage = "document check-in cancelled.";

        // open DS editor
        hierarchyNavigatePage().goToHierarchyPage(ContentSets.REG_GUIDANCE_SUMMARY_US.getCode());
        loginPage().logIn();
        hierarchyNavigatePage().handleCasIssueWithHierarchyPageOpenedAsStartPageIfOccurred(ContentSets.REG_GUIDANCE_SUMMARY_US);
        hierarchySearchPage().searchNodeUuid(uuid);
        siblingMetadataPage().selectedSiblingMetadataEditContent();
        editorPage().switchToEditor();
        editorPage().closeEditorWithDiscardingChangesIfAppeared();

        hierarchyNavigatePage().switchToHierarchyEditPage();
        siblingMetadataPage().selectedSiblingMetadataEditContent();
        editorPage().switchToEditor();
        editorPage().scrollToChunk(chunkNumber);

        // validate - no warnings expected
        editorToolbarPage().clickSubsectionValidation();
        boolean noWarningsInitially = editorMessagePage().checkMessagePaneForText(validMessage);

        // make a change to toggle warning
        editorToolbarPage().clickConfigureEditorSessionPreferences();
        editorPreferencesPage().clickShowSubsectionLabelDesignatorsYesRadioButton();
        editorPreferencesPage().clickSaveButton();
        editorPage().switchToEditor();
        editorTextPage().switchToEditorTextArea();

        //change subsection value
        editorPage().click(EditorTextPageElements.METADATA_DESIGNATOR_LABEL);
        editorPage().sendKeys(Keys.DELETE);
        String wrongLabel = editorPage().getElementsText(EditorTextPageElements.METADATA_DESIGNATOR_LABEL);
        editorToolbarPage().clickSave();

        // validate - an alert and warning message expected
        boolean alertAppeared = editorToolbarPage().clickSubsectionValidation();
        boolean warningMessagesAppeared = editorMessagePage().checkMessagePaneForText(expectedErrorMessage);

        // click on link in error -- spoiled subsection should be highlighted
        editorMessagePage().clickAffectedIds();
        editorTextPage().switchToEditorTextArea();
        boolean correctSectionHighlighted = editorPage().doesElementExist(
                String.format(EditorTextPageElements.HIGHLIGHTED_SUBSECTION_BY_LABEL,wrongLabel));

        // check in and verify error alert appear again
        editorPage().closeAndCheckInChanges();
        AutoITUtils.verifyAlertTextAndCancel(true, messageFromWebPage);
        editorPage().switchToEditor();
        int secondValidationErrorAppeared = editorMessagePage().countMatchesTextInMessagePane(expectedErrorMessage);
        boolean checkInCancellationMessageAppeared = editorMessagePage().checkMessagePaneForText(checkInCancellationMessage);

        //close DS editor
        editorPage().closeDocumentAndDiscardChanges();

        Assertions.assertAll
                (
                        () -> Assertions.assertTrue(noWarningsInitially, "No warnings should appear after initial validation"),
                        () -> Assertions.assertTrue(alertAppeared, "Alert should appear after second validation"),
                        () -> Assertions.assertTrue(warningMessagesAppeared, "Warning should appear after second validation"),
                        () -> Assertions.assertTrue(correctSectionHighlighted, "Have not brought to correct subsection"),
                        () -> Assertions.assertEquals(secondValidationErrorAppeared,2, "Second error link appeared in message pane and contains the same uuid"),
                        () -> Assertions.assertTrue(checkInCancellationMessageAppeared, "Check-In cancellation message should appear")
                );
    }

    /* Subsection Generation
     * JETS-14482
     * 1. Open document
     * 2. Copy a Source Note Paragraph from a Subsection parent
     * 3. Select a different Subsection label
     * 4. Paste the Source Note Paragraph - This will result in the Source Note Paragraph being pasted in as a sibling to the subsection
     * 5. Run Subsection Generation
     * VERIFY: A new Subsection parent is generated for the Source Note Paragraph, and the SNP is now a child to the Subsection wrapper
     * VERIFY: The md.label.designator value matches the the text of the SNP - apparently it doesn't matter if it is wrapped in cite.query markup or not
     * 6. Close and discard changes
     */
    /**
     * STORY/BUG - migration <br>
     * SUMMARY -  Subsection Generation<br>
     * USER - RISK <br>
     */
    @Test
    @IE_EDGE_MODE
    @RISK
    @LOG
    public void subsectionGenerationTargetRiskTest()
    {
//        String uuid = "I1CEC761040B011E793F600FF10C05B07"; for dev
        String uuid = "I471322C0213111E7A1D8180373BE7E39";
        int chunkNumber = 1;
        int nextChunk = chunkNumber+2;

        // open DS editor
        hierarchyNavigatePage().goToHierarchyPage(ContentSets.REG_GUIDANCE_SUMMARY_US.getCode());
        loginPage().logIn();
        hierarchyNavigatePage().handleCasIssueWithHierarchyPageOpenedAsStartPageIfOccurred(ContentSets.REG_GUIDANCE_SUMMARY_US);
        hierarchySearchPage().searchNodeUuid(uuid);
        siblingMetadataPage().selectedSiblingMetadataEditContent();

        // make a change to toggle warning
        editorToolbarPage().clickConfigureEditorSessionPreferences();
        editorPreferencesPage().clickShowSubsectionLabelDesignatorsYesRadioButton();
        editorPreferencesPage().clickSaveButton();
        editorPage().switchToEditor();

        //select first Source Note Paragraph in chunk and copy it
        editorPage().scrollToChunk(chunkNumber);
        String noteParagraphText = editorTextPage().getFirstSourceNoteParagraphInTheChunk(chunkNumber);
        editorPage().jumpToBeginningOfDocument();
        editorTextPage().rightClickFirstSourceNoteParagraphInTheChunk(chunkNumber);
        editorTextPage().breakOutOfFrame();
        editorTextContextMenuPage().copyCtrlC();

        //select second Subsection in chunk
        editorPage().scrollToChunk(nextChunk);
        editorTextPage().rightClickFirstSubsectionInTheChunk(nextChunk);

        //paste the Source Note Paragraph
        editorTextPage().breakOutOfFrame();
        editorTextContextMenuPage().pasteSiblingCtrlV();

        //count subsection numbers
        editorPage().switchToEditorTextFrame();
        int subsectionNumberBeforeGeneration = editorTextPage().countSubsectionInTheChunk(nextChunk);

        //run subsection generation
        editorToolbarPage().clickSubsectionGeneration();

        //count subsection numbers
        editorPage().switchToEditorTextFrame();
        int subsectionNumberAfterGeneration = editorTextPage().countSubsectionInTheChunk(nextChunk);

        //The label value matches the the text of the SNP
        String newSubsectionLabelValue = editorTextPage().getSecondSubsectionLabelInTheChunk(nextChunk);

        //close DS editor
        editorPage().closeDocumentAndDiscardChanges();

        Assertions.assertAll
                (
                        () -> Assertions.assertEquals(subsectionNumberBeforeGeneration, subsectionNumberAfterGeneration-1, "New subsection WAS NOT generated"),
                        () -> Assertions.assertEquals(noteParagraphText, newSubsectionLabelValue, "The label value DOES NOT match the the text of the SNP")
                );

    }

    private static Stream<Arguments> provideData()
    {
        return Stream.of(
                Arguments.of("Error: This document is invalid because at least one of the subsections has an identifier that does not match the style of the previous subsection identifier(s).",
                        "Error: This document is invalid because at least one subsection identifier has been updated. Either run Subsection Generation to complete the update, or manually restore the subsection identifier if update was unintended.",
                        "a.",
                        " Some text.",
                        ": a instead 1"),
                Arguments.of("Error: This document is invalid because at least one subsection does not have an identifier greater than the value of the previous subsection identifier.",
                        "Error: This document is invalid because at least one subsection either does not have an identifier or does not have the required fix space.",
                        " ",
                        " Some text.",
                        ": empty paragraph identifier"),
                Arguments.of("Error: This document is invalid because there is a subsection identifier present without text following it.",
                        " ",
                        "1.",
                        "",
                        ": empty paragraph")
        );
    }
    /**
     * STORY/BUG - HALCYONST-11668 <br>
     * SUMMARY -  Changes to Subsection Validation messages (Target) <br>
     * USER - LEGAL <br>
     */
    @ParameterizedTest(name = "Test: {displayName}; Index: {index}; Arguments: {arguments}")
    @MethodSource("provideData")
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void subsectionValidationWithWrongSubsectionParagraphTest(String expectedFirstErrorMessage,
                                                                     String expectedSecondErrorMessage, String paragraphIdentifier,
                                                                     String paragraphText, String testName)
    {
        String uuid = "ID9254820157F11DA8AC5CD53670E6B4E";
        int chunkNumber = 1;
        String validMessage = ": The documents subsections are valid.";

        // open DS editor
        homePage().goToHomePage();
        loginPage().logIn();
        hierarchyNavigatePage().goToHierarchyPage(ContentSets.IOWA_DEVELOPMENT.getCode());
        hierarchySearchPage().searchNodeUuid(uuid);
        siblingMetadataPage().selectedSiblingMetadataEditContent();
        editorPage().scrollToChunk(chunkNumber);

        // validate - no warnings expected
        editorToolbarPage().clickSubsectionValidation();
        boolean noWarningsInitially = editorMessagePage().checkMessagePaneForText(validMessage);

        //change subsection value
        editorTextPage().switchToEditorTextArea();
        editorTextPage().scrollToView(EditorTextPageElements.SUBSECTION+EditorTextPageElements.TEXT_PARAGRAPH_LABEL);
        editorPage().insertNewParagraphVia(ParagraphInsertMethods.CONTEXT_MENU, EditorTextPageElements.SUBSECTION+EditorTextPageElements.TEXT_PARAGRAPH_LABEL);
        editorTextPage().sendKeys(paragraphIdentifier);
        editorPage().breakOutOfFrame();
        editorToolbarPage().clickInsertSpecialCharacter();
        insertSpecialCharacterPage().sendTextInXmlEntity(EMSP.getHtml());
        insertSpecialCharacterPage().clickInsert();
        editorPage().waitForWindowGoneByTitle(InsertSpecialCharacterPageElements.PAGE_TITLE);
        editorPage().switchDirectlyToTextFrame();
        editorTextPage().sendKeys(paragraphText);
        editorTextPage().scrollToView(EditorTextPageElements.SUBSECTION+EditorTextPageElements.TEXT_PARAGRAPH_LABEL);
        editorTextPage().rightClick(EditorTextPageElements.SUBSECTION+EditorTextPageElements.TEXT_PARAGRAPH_LABEL);
        editorPage().breakOutOfFrame();
        editorTextContextMenu().delete();

        // validate - an alert and warning message expected
        boolean alertAppeared = editorToolbarPage().clickSubsectionValidation();
        editorPage().waitForPageLoaded();
        String errorMessage = editorMessagePage().getMessage();
        boolean firstWarningMessagesAppeared = errorMessage.contains(expectedFirstErrorMessage);
        boolean secondWarningMessagesAppeared = errorMessage.contains(expectedSecondErrorMessage);

        //close DS editor
        editorPage().closeDocumentAndDiscardChanges();

        Assertions.assertAll
                (
                        () -> Assertions.assertTrue(noWarningsInitially, "No warnings should appear after initial validation"),
                        () -> Assertions.assertTrue(alertAppeared, "Alert should appear after second validation"),
                        () -> Assertions.assertTrue(firstWarningMessagesAppeared, String.format("Message from message pane '%s' doesn't contain next warning: '%s'", errorMessage,expectedFirstErrorMessage)),
                        () -> Assertions.assertTrue(secondWarningMessagesAppeared, String.format("Message from message pane '%s' doesn't contain next warning: '%s'", errorMessage,expectedSecondErrorMessage))
                );
    }

    /**
     * STORY/BUG - HALCYONST-11668 <br>
     * SUMMARY -  Changes to Subsection Validation messages (Target) <br>
     * USER - LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void subsectionValidationWithTwoParagraphsWithSameIdentifiersTest()
    {
        String uuid = "ID9254820157F11DA8AC5CD53670E6B4E";
        int chunkNumber = 1;
        String validMessage = ": The documents subsections are valid.";
        String expectedErrorMessage = "Error: This document is invalid because at least one paragraph has more identifiers and fixed spaces in it than subsection wrappers.";

        // open DS editor
        homePage().goToHomePage();
        loginPage().logIn();
        hierarchyNavigatePage().goToHierarchyPage(ContentSets.IOWA_DEVELOPMENT.getCode());
        hierarchySearchPage().searchNodeUuid(uuid);
        siblingMetadataPage().selectedSiblingMetadataEditContent();
        editorPage().scrollToChunk(chunkNumber);

        // validate - no warnings expected
        editorToolbarPage().clickSubsectionValidation();
        boolean noWarningsInitially = editorMessagePage().checkMessagePaneForText(validMessage);

        //change subsection value
        editorTextPage().switchToEditorTextArea();
        editorTextPage().scrollToView(EditorTextPageElements.SUBSECTION+EditorTextPageElements.TEXT_PARAGRAPH_LABEL);
        editorPage().insertNewParagraphVia(ParagraphInsertMethods.CONTEXT_MENU, EditorTextPageElements.SUBSECTION+EditorTextPageElements.TEXT_PARAGRAPH_LABEL);

        editorTextPage().sendKeys("1. ");
        editorPage().switchToEditor();
        editorToolbarPage().clickInsertSpecialCharacter();
        insertSpecialCharacterPage().sendTextInXmlEntity(EMSP.getHtml());
        insertSpecialCharacterPage().clickInsert();
        editorPage().waitForWindowGoneByTitle(InsertSpecialCharacterPageElements.PAGE_TITLE);
        editorPage().switchDirectlyToTextFrame();
        editorTextPage().sendKeys(" Some text");

        // validate - an alert and warning message expected
        boolean alertAppeared = editorToolbarPage().clickSubsectionValidation();
        editorPage().waitForPageLoaded();
        String errorMessage = editorMessagePage().getMessage();
        boolean warningMessagesAppeared = errorMessage.contains(expectedErrorMessage);

        //close DS editor
        editorPage().closeDocumentAndDiscardChanges();

        Assertions.assertAll
                (
                        () -> Assertions.assertTrue(noWarningsInitially, "No warnings should appear after initial validation"),
                        () -> Assertions.assertTrue(alertAppeared, "Alert should appear after second validation"),
                        () -> Assertions.assertTrue(warningMessagesAppeared, String.format("Message from message pane '%s' doesn't contain next warning: '%s'", errorMessage,expectedErrorMessage))
                );
    }

    /**
     * STORY/BUG - HALCYONST-11668 <br>
     * SUMMARY -  Changes to Subsection Validation messages (Target) <br>
     * USER - LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void subsectionValidationWithTwoSectionsWithSameIdentifiersTest()
    {
        String uuid = "ID9254820157F11DA8AC5CD53670E6B4E";
        int chunkNumber = 1;
        String validMessage = ": The documents subsections are valid.";
        String expectedErrorMessage = "Warning: This document is invalid because the subsection has the same identifier as the immediately preceding sibling subsection.";

        // open DS editor
        homePage().goToHomePage();
        loginPage().logIn();
        hierarchyNavigatePage().goToHierarchyPage(ContentSets.IOWA_DEVELOPMENT.getCode());
        hierarchySearchPage().searchNodeUuid(uuid);
        siblingMetadataPage().selectedSiblingMetadataEditContent();
        editorPage().scrollToChunk(chunkNumber);

        // validate - no warnings expected
        editorToolbarPage().clickSubsectionValidation();
        boolean noWarningsInitially = editorMessagePage().checkMessagePaneForText(validMessage);

        //change subsection value
        editorTextPage().switchToEditorTextArea();
        editorTextPage().scrollToView(EditorTextPageElements.SUBSECTION+EditorTextPageElements.SPAN);
        editorTextPage().rightClick(EditorTextPageElements.SUBSECTION+EditorTextPageElements.SPAN);
        editorPage().breakOutOfFrame();
        editorTextContextMenu().copyCtrlC();
        editorTextPage().switchToEditorTextArea();
        editorTextPage().scrollToView(EditorTextPageElements.SUBSECTION+EditorTextPageElements.SPAN);
        editorTextPage().rightClick(EditorTextPageElements.SUBSECTION+EditorTextPageElements.SPAN);
        editorPage().breakOutOfFrame();
        editorTextContextMenu().pasteSiblingCtrlV();
        editorPage().waitForPageLoaded();

        // validate - an alert and warning message expected
        editorToolbarPage().clickSubsectionValidation();
        editorPage().waitForPageLoaded();
        String errorMessage = editorMessagePage().getMessage();
        boolean warningMessagesAppeared = errorMessage.contains(expectedErrorMessage);

        //close DS editor
        editorPage().closeDocumentAndDiscardChanges();

        Assertions.assertAll
                (
                        () -> Assertions.assertTrue(noWarningsInitially, "No warnings should appear after initial validation"),
                        () -> Assertions.assertTrue(warningMessagesAppeared, String.format("Message from message pane '%s' doesn't contain next warning: '%s'", errorMessage,expectedErrorMessage))
                );
    }

    /**
     * STORY/BUG - HALCYONST-11668 <br>
     * SUMMARY -  Changes to Subsection Validation messages (Target) <br>
     * USER - LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void subsectionValidationTwoSectionsWithSameIdentifiersTest()
    {
        String uuid = "ID9254820157F11DA8AC5CD53670E6B4E";
        int chunkNumber = 1;
        String validMessage = ": The documents subsections are valid.";
        String expectedErrorMessage = "Error: This document is invalid because at least one subsection does not have an identifier greater than the value of the previous subsection identifier.";
        String expectedWarningMessage = "Warning: This document is invalid because there are at least two of the same subsection identifiers being used at a sibling level - these multiples are not in a row.";

        // open DS editor
        homePage().goToHomePage();
        loginPage().logIn();
        hierarchyNavigatePage().goToHierarchyPage(ContentSets.IOWA_DEVELOPMENT.getCode());
        hierarchySearchPage().searchNodeUuid(uuid);
        siblingMetadataPage().selectedSiblingMetadataEditContent();
        editorPage().scrollToChunk(chunkNumber);

        // validate - no warnings expected
        editorToolbarPage().clickSubsectionValidation();
        boolean noWarningsInitially = editorMessagePage().checkMessagePaneForText(validMessage);

        //change subsection value
        editorTextPage().switchToEditorTextArea();
        editorTextPage().scrollToView(EditorTextPageElements.SUBSECTION+EditorTextPageElements.SPAN);
        editorTextPage().rightClick(EditorTextPageElements.SUBSECTION+EditorTextPageElements.SPAN);
        editorPage().breakOutOfFrame();
        editorTextContextMenu().copyCtrlC();
        editorTextPage().switchToEditorTextArea();
        editorTextPage().scrollToView(EditorTextPageElements.SUBSECTION+"[2]"+EditorTextPageElements.SPAN);
        editorTextPage().rightClick(EditorTextPageElements.SUBSECTION+"[2]"+EditorTextPageElements.SPAN);
        editorPage().breakOutOfFrame();
        editorTextContextMenu().pasteSiblingCtrlV();
        editorPage().waitForPageLoaded();

        // validate - an alert and warning message expected
        boolean alertAppeared = editorToolbarPage().clickSubsectionValidation();
        editorPage().waitForPageLoaded();
        String errorMessage = editorMessagePage().getMessage();
        boolean warningMessagesAppeared = errorMessage.contains(expectedWarningMessage);
        boolean errorMessagesAppeared = errorMessage.contains(expectedErrorMessage);

        //close DS editor
        editorPage().closeDocumentAndDiscardChanges();

        Assertions.assertAll
                (
                        () -> Assertions.assertTrue(noWarningsInitially, "No warnings should appear after initial validation"),
                        () -> Assertions.assertTrue(alertAppeared, "Alert should appear after second validation"),
                        () -> Assertions.assertTrue(warningMessagesAppeared, String.format("Message from message pane '%s' doesn't contain next warning: '%s'", errorMessage,expectedWarningMessage)),
                        () -> Assertions.assertTrue(errorMessagesAppeared, String.format("Message from message pane '%s' doesn't contain next error: '%s'", errorMessage,expectedErrorMessage))
                );
    }

    /**
     * STORY/BUG - HALCYONST-11668 <br>
     * SUMMARY -  Changes to Subsection Validation messages (Target) <br>
     * USER - LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void subsectionValidationSectionsWithoutIdentifierTest()
    {
        String uuid = "ID9254820157F11DA8AC5CD53670E6B4E";
        int chunkNumber = 1;
        String validMessage = ": The documents subsections are valid.";
        String expectedFirstErrorMessage = "Error: This document is invalid because of a missing subsection identifier. Either run Subsection Generation to complete the update or manually add the missing designator.";
        String expectedSecondErrorMessage = "Error: This document is invalid because at least one subsection identifier has been updated. Either run Subsection Generation to complete the update, or manually restore the subsection identifier if update was unintended.";

        // open DS editor
        homePage().goToHomePage();
        loginPage().logIn();
        hierarchyNavigatePage().goToHierarchyPage(ContentSets.IOWA_DEVELOPMENT.getCode());
        hierarchySearchPage().searchNodeUuid(uuid);
        siblingMetadataPage().selectedSiblingMetadataEditContent();
        editorPage().scrollToChunk(chunkNumber);

        // validate - no warnings expected
        editorToolbarPage().clickSubsectionValidation();
        boolean noWarningsInitially = editorMessagePage().checkMessagePaneForText(validMessage);

        //change subsection value
        editorTextPage().switchToEditorTextArea();
        editorTextPage().click(EditorTextPageElements.SUBSECTION+EditorTextPageElements.METADATA_LABEL_DESIGNATOR);
        editorTextPage().sendKeys(Keys.DELETE);

        // validate - an alert and warning message expected
        boolean alertAppeared = editorToolbarPage().clickSubsectionValidation();
        editorPage().waitForPageLoaded();
        String errorMessage = editorMessagePage().getMessage();
        boolean firstErrorMessagesAppeared = errorMessage.contains(expectedFirstErrorMessage);
        boolean secondErrorMessagesAppeared = errorMessage.contains(expectedSecondErrorMessage);

        //close DS editor
        editorPage().closeDocumentAndDiscardChanges();

        Assertions.assertAll
                (
                        () -> Assertions.assertTrue(noWarningsInitially, "No warnings should appear after initial validation"),
                        () -> Assertions.assertTrue(alertAppeared, "Alert should appear after second validation"),
                        () -> Assertions.assertTrue(firstErrorMessagesAppeared, String.format("Message from message pane '%s' doesn't contain next error: '%s'", errorMessage,expectedFirstErrorMessage)),
                        () -> Assertions.assertTrue(secondErrorMessagesAppeared, String.format("Message from message pane '%s' doesn't contain next error: '%s'", errorMessage,expectedSecondErrorMessage))
                );
    }

    /**
     * STORY/BUG - HALCYONST-8721 <br>
     * SUMMARY - Subsection generate error after paragraph insert and pubtag refresh (Target) <br>
     * USER - LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void subsectionGenerateErrorAfterParagraphInsertAndPubtagRefresh()
    {
        String uuid = "IBAF42040F45B11E489848997F2A2FD26";
        String errorMessage = "Unable to get property 'chunkNumber' of undefined or null reference";

        hierarchyNavigatePage().goToHierarchyPage(ContentSets.USCA_DEVELOPMENT.getCode());
        loginPage().logIn();
        hierarchyNavigatePage().handleCasIssueWithHierarchyPageOpenedAsStartPageIfOccurred(ContentSets.USCA_DEVELOPMENT);
        hierarchySearchPage().searchNodeUuid(uuid);
        siblingMetadataPage().selectedSiblingMetadataEditContent();
        editorPage().switchToEditor();
        editorPage().switchToEditorTextFrame();

        String textSubheadingXpath = EditorTextPageElements.BODY_TAG + String.format(EditorTextPageElements.SUBSECTION_UNDER_NUMBER, 1)
                + EditorTextPageElements.PARA_SPAN;

        editorTextPage().scrollToView(textSubheadingXpath);
        editorTextPage().rightClick(textSubheadingXpath);
        editorTextPage().breakOutOfFrame();
        editorTextPage().openContextMenu(EditorTextContextMenuElements.COPY_CTRL_C);
        editorPage().switchToEditorTextFrame();

        editorTextPage().scrollToView(textSubheadingXpath);
        editorTextPage().rightClick(textSubheadingXpath);
        editorTextPage().breakOutOfFrame();
        editorTextPage().openContextMenu(EditorTextContextMenuElements.PASTE_SIBLING_CTRL_V);
        editorPage().switchToEditorTextFrame();

        editorTextPage().moveToElementAndSendKeys(EditorTextPageElements.BODY_TAG
                + String.format(EditorTextPageElements.SUBSECTION_UNDER_NUMBER, 1) + EditorTextPageElements.PARA + "[2]"
                + EditorTextPageElements.PARATEXT + String.format(EditorTextPageElements.BOLD_TEXT, "(a)"), "a");

        editorPage().switchToEditor();
        editorToolbarPage().clickPubtagRefreshButton();
        editorPage().switchToEditorTextFrame();

        List<String> actualPubTags = editorTextPage().getPubTagsBySubsectionAndParaNumber(1, 1);

        List<String> expectedPubTags = editorTextPage().getPubTagsBySubsectionAndParaNumber(1, 2);

        String subsectionParaXpath = EditorTextPageElements.BODY_TAG + String.format(EditorTextPageElements.SUBSECTION_UNDER_NUMBER, 1)
                + EditorTextPageElements.PARA;

        int paraSizeInFirstSubsectionBeforeGeneration = editorTextPage().getElements(subsectionParaXpath).size();

        editorPage().switchToEditor();
        editorToolbarPage().clickSubsectionGeneration();
        editorPage().switchToEditorTextFrame();

        int paraSizeInFirstSubsectionAfterGeneration = editorTextPage().getElements(subsectionParaXpath).size();
        int newlyGeneratedSubsectionSize = editorTextPage().getElements(editorTextPage().getModifiedByXpath(user())).size();

        editorPage().switchToEditor();
        boolean doesMessagePaneContainErrorMessage = editorMessagePage().checkMessagePaneForText(errorMessage);

        editorPage().closeDocumentAndDiscardChanges();

        Assertions.assertAll(
                () -> Assertions.assertEquals(expectedPubTags, actualPubTags, "Expected pub tags aren't the same as actual"),
                () -> Assertions.assertNotEquals(paraSizeInFirstSubsectionBeforeGeneration, paraSizeInFirstSubsectionAfterGeneration,
                        "The number of para in first subsection before generation and after it should be not equal"),
                () -> Assertions.assertEquals(1, newlyGeneratedSubsectionSize,
                        "The number of newly generated subsection should be 1"),
                () -> Assertions.assertFalse(doesMessagePaneContainErrorMessage,
                        String.format("Message pane should not contain error message: %s", errorMessage))
        );
    }
}
