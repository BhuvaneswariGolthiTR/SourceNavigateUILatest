package com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.source.set3;

import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorMessagePageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.popups.EditAttributesPageElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import org.apache.logging.log4j.util.Strings;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.util.Arrays;
import java.util.List;

import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorToolbarPageElements.toolbarValidateButton;

public class ValidationTests extends TestService
{
    private static final String UUID = "ICEA353B0953F11EAAF27EF97C9375716";
    private static final String EXPECTED_ALERT_MESSAGE = "Document is not valid.  Please review errors in the Message Pane.";
    private static final List<String> EXPECTED_MESSAGE_PANE_ERRORS = Arrays.asList(
            "Data error id[18]: DELTA_WITHOUT_ACTION",
            "CLICK HERE to find the error in the document",
            "Location: Delta Note is missing an insert or add attribute. Please add an insert or add attribute to Target location");
    private static final String TARGET_LOCATION_ASSERTION_MESSAGE = "Target Location Add doesn't exist";
    private static final String DELTA_FEATURE_ASSERTION_MESSAGE = "Delta Feature doesn't exist";
    private static final String HISTORICAL_NOTE_BLOCK_ASSERTION_MESSAGE = "Historical Note Block doesn't exist";
    private static final String EXPECTED_ALERT_MESSAGE_ASSERTION_MESSAGE = String.format("[%s] doesn't present", EXPECTED_ALERT_MESSAGE);
    private static final String EXPECTED_ERRORS_ABSENCE_ASSERTION_MESSAGE = String.format("[%s] are present", EXPECTED_MESSAGE_PANE_ERRORS);
    private static final String EXPECTED_ERRORS_PRESENCE_ASSERTION_MESSAGE = String.format("[%s] didn't present", EXPECTED_MESSAGE_PANE_ERRORS);

    /*
     * 1. Open the document
     * 2. Scroll to chunk 2 or 3
     * 3. Click the validate button
     * 4. Verify: The validation services kick off and complete successfully
     * 5. Delete the Cornerpiece element from the content (This will produce a validation error)
     * 6. Scroll to chunk 2 or 3
     * 7. Click the validate button
     * 8. Verify: (If this document is valid in the first place...) The validation services kick off and fail validation,
     * 		we should see "Data error id[925]: This document is missing a cornerpiece"
     * 9. Hit Undo and Validate again
     * Verify: we should see no Validation errors
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void validationSourceLegalTests()
    {
        String uuid = "I7B470DD0DB4511E780FBFC60A07B88FB";
        String errorMessage = ": DTD error id[9]: The content of element type \"source.rendition\" must match ";

        sourcePage().goToSourcePageWithRenditionUuids(uuid);
        loginPage().logIn();
        sourceNavigateGridPage().firstRenditionEditContent();

        // validate before we've deleted a SOURCE FRONT
        editorToolbarPage().clickValidate();
        boolean errorAppearedBeforeDeleting = editorMessagePage().checkMessagePaneForText(errorMessage);

        // delete a SOURCE FRONT and validate -- alert and log error expected
        editorPage().switchToEditorTextFrame();
        editorTextPage().click(EditorTextPageElements.SOURCE_FRONT_SPAN);
        editorTextPage().sendKeys(Keys.DELETE);

        editorTextPage().breakOutOfFrame();
        editorToolbarPage().click(toolbarValidateButton);
        boolean validationAlertAppeared = editorPage().checkAlert();

        boolean errorAppearedAfterDeleting = editorMessagePage().checkMessagePaneForText(errorMessage);

        // undo and then validate again
        editorToolbarPage().clickUndo();
        editorToolbarPage().click(toolbarValidateButton);

        boolean alertAppearedAfterUndoingDeleting = editorPage().checkAlert();

        // close editor
        editorPage().closeDocumentAndDiscardChanges();
        Assertions.assertAll(
                () -> Assertions.assertFalse(errorAppearedBeforeDeleting,
                        "Validation error should not appear before SOURCE FRONT deleting"),
                () -> Assertions.assertTrue(validationAlertAppeared,
                        "Alert about validation error should appear"),
                () -> Assertions.assertTrue(errorAppearedAfterDeleting,
                        "Validation error should appear after SOURCE FRONT deleting"),
                () -> Assertions.assertFalse(alertAppearedAfterUndoingDeleting,
                        "No alert should appear after validating after undoing")
        );
    }

    /**
     * STORY/BUG - HALCYONST-14152 <br>
     * SUMMARY - Validation error on Delta Note (Source) <br>
     * USER - LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void deleteTargetLocationAddAndDoNotReplaceSourceLegalTest()
    {
        sourcePage().goToSourcePageWithRenditionUuids(UUID);
        loginPage().logIn();
        sourceNavigateGridPage().firstRenditionEditContent();
        editorPage().switchToEditorTextFrame();

        String sourceSectionSpan = EditorTextPageElements.SOURCE_BODY + EditorTextPageElements.SOURCE_SECTION_SPAN;
        editorTextPage().click(sourceSectionSpan);
        editorTextPage().rightClick(sourceSectionSpan);
        editorTextPage().breakOutOfFrame();
        editorTextContextMenu().insertDeltaWithDerivationFeature();
        editorPage().switchToEditorTextFrame();

        String commonXPath = EditorTextPageElements.SOURCE_BODY + EditorTextPageElements.SOURCE_SECTION + "%s" + EditorTextPageElements.SPAN;
        String targetLocationXPath = EditorTextPageElements.TARGET_LOCATION + "[1]";
        boolean isTargetLocationAddAdded = editorTextPage().getElement(String.format(commonXPath, targetLocationXPath)).isDisplayed();
        boolean isDeltaFeatureAdded = editorTextPage().getElement(String.format(commonXPath, EditorTextPageElements.DELTA_FEATURE)).isDisplayed();
        boolean isHistoricalNoteBlockAdded = editorTextPage().getElement(String.format(commonXPath, EditorTextPageElements.HISTORICAL_NOTE_BLOCK)).isDisplayed();

        editorToolbarPage().clickToolbarButton(toolbarValidateButton);

        String logMessageBeforeDeletionTargetLocationAdd = editorMessagePage().getElementsText(EditorMessagePageElements.LOG_MESSAGE_AREA);
        boolean areExpectedErrorsAbsent = !EXPECTED_MESSAGE_PANE_ERRORS.stream().allMatch(logMessageBeforeDeletionTargetLocationAdd::contains);

        editorPage().switchToEditorTextFrame();
        editorTextPage().click(String.format(commonXPath, targetLocationXPath));
        editorTextPage().rightClick(String.format(commonXPath, targetLocationXPath));
        editorTextPage().breakOutOfFrame();
        editorTextContextMenu().delete();

        editorToolbarPage().clickToolbarButton(toolbarValidateButton);

        boolean isExpectedAlertMessagePresent = editorPage().checkAlertTextMatchesGivenText(EXPECTED_ALERT_MESSAGE);

        String logMessageAfterDeletionTargetLocationAdd = editorMessagePage().getElementsText(EditorMessagePageElements.LOG_MESSAGE_AREA);
        boolean areExpectedErrorsPresent = EXPECTED_MESSAGE_PANE_ERRORS.stream().allMatch(logMessageAfterDeletionTargetLocationAdd::contains);

        editorPage().closeDocumentAndDiscardChanges();

        Assertions.assertAll(
                () -> Assertions.assertTrue(isTargetLocationAddAdded, TARGET_LOCATION_ASSERTION_MESSAGE),
                () -> Assertions.assertTrue(isDeltaFeatureAdded, DELTA_FEATURE_ASSERTION_MESSAGE),
                () -> Assertions.assertTrue(isHistoricalNoteBlockAdded, HISTORICAL_NOTE_BLOCK_ASSERTION_MESSAGE),
                () -> Assertions.assertTrue(areExpectedErrorsAbsent, EXPECTED_ERRORS_ABSENCE_ASSERTION_MESSAGE),
                () -> Assertions.assertTrue(isExpectedAlertMessagePresent, EXPECTED_ALERT_MESSAGE_ASSERTION_MESSAGE),
                () -> Assertions.assertTrue(areExpectedErrorsPresent, EXPECTED_ERRORS_PRESENCE_ASSERTION_MESSAGE)
        );
    }

    /**
     * STORY/BUG - HALCYONST-14152 <br>
     * SUMMARY - Validation error on Delta Note (Source) <br>
     * USER - LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void deleteTargetLocationAddAndReplaceWithNormalTargetLocationSourceLegalTest()
    {
        sourcePage().goToSourcePageWithRenditionUuids(UUID);
        loginPage().logIn();
        sourceNavigateGridPage().firstRenditionEditContent();
        editorPage().switchToEditorTextFrame();

        String sourceSectionSpan = EditorTextPageElements.SOURCE_BODY + EditorTextPageElements.SOURCE_SECTION_SPAN;
        editorTextPage().click(sourceSectionSpan);
        editorTextPage().rightClick(sourceSectionSpan);
        editorTextPage().breakOutOfFrame();
        editorTextContextMenu().insertDeltaWithDerivationFeature();
        editorPage().switchToEditorTextFrame();

        String commonXPath = EditorTextPageElements.SOURCE_BODY + EditorTextPageElements.SOURCE_SECTION + "%s" + EditorTextPageElements.SPAN;
        String targetLocationXPath = EditorTextPageElements.TARGET_LOCATION + "[1]";
        boolean isTargetLocationAddAdded = editorTextPage().getElement(String.format(commonXPath, targetLocationXPath)).isDisplayed();
        boolean isDeltaFeatureAdded = editorTextPage().getElement(String.format(commonXPath, EditorTextPageElements.DELTA_FEATURE)).isDisplayed();
        boolean isHistoricalNoteBlockAdded = editorTextPage().getElement(String.format(commonXPath, EditorTextPageElements.HISTORICAL_NOTE_BLOCK)).isDisplayed();

        String deltaTargetLocation = String.format(commonXPath, "/delta[2]" + EditorTextPageElements.TARGET_LOCATION);
        editorTextPage().click(deltaTargetLocation);
        editorTextPage().rightClick(deltaTargetLocation);
        editorTextPage().breakOutOfFrame();
        editorTextContextMenu().copyCtrlC();
        editorPage().switchToEditorTextFrame();

        String targetLocation = String.format(commonXPath, targetLocationXPath);
        editorTextPage().click(targetLocation);
        editorTextPage().rightClick(targetLocation);
        editorTextPage().breakOutOfFrame();
        editorTextContextMenu().pasteCtrlV();
        editorPage().switchToEditorTextFrame();

        editorTextPage().click(targetLocation);
        editorTextPage().rightClick(targetLocation);
        editorTextPage().breakOutOfFrame();
        editorTextContextMenu().delete();
        editorPage().switchToEditorTextFrame();

        editorToolbarPage().clickToolbarButton(toolbarValidateButton);

        boolean isExpectedAlertMessagePresent = editorToolbarPage().checkAlertTextMatchesGivenText(EXPECTED_ALERT_MESSAGE);

        String logMessageBeforeAddingAttributes = editorMessagePage().getElementsText(EditorMessagePageElements.LOG_MESSAGE_AREA);
        boolean areExpectedErrorsPresent = EXPECTED_MESSAGE_PANE_ERRORS.stream().allMatch(logMessageBeforeAddingAttributes::contains);

        editorPage().switchToEditorTextFrame();
        editorTextPage().rightClick(String.format(commonXPath, targetLocationXPath));
        editorTextPage().breakOutOfFrame();
        editorTextContextMenu().editAttributes();

        boolean isEditAttributesWindowAppear = driver().getTitle().equals(EditAttributesPageElements.ATTRIBUTE_EDITOR_TITLE);

        editAttributesPage().setFirstInput("add");
        editAttributesPage().clickSave();

        boolean isEditAttributesWindowDisappear = !driver().getTitle().equals(EditAttributesPageElements.ATTRIBUTE_EDITOR_TITLE);

        editorPage().switchToEditorTextFrame();
        boolean isTargetLocationUpdate = editorTextPage().getElementsText(String.format(commonXPath, targetLocationXPath))
                .equals("Target Location Add");

        editorToolbarPage().clickToolbarButton(toolbarValidateButton);

        String logMessageAfterAddingAttributes = editorMessagePage().getElementsText(EditorMessagePageElements.LOG_MESSAGE_AREA)
                .replace(logMessageBeforeAddingAttributes, Strings.EMPTY);
        boolean areExpectedErrorsAbsent = !EXPECTED_MESSAGE_PANE_ERRORS.stream().allMatch(logMessageAfterAddingAttributes::contains);

        editorPage().closeDocumentAndDiscardChanges();

        Assertions.assertAll(
                () -> Assertions.assertTrue(isTargetLocationAddAdded, TARGET_LOCATION_ASSERTION_MESSAGE),
                () -> Assertions.assertTrue(isDeltaFeatureAdded, DELTA_FEATURE_ASSERTION_MESSAGE),
                () -> Assertions.assertTrue(isHistoricalNoteBlockAdded, HISTORICAL_NOTE_BLOCK_ASSERTION_MESSAGE),
                () -> Assertions.assertTrue(isExpectedAlertMessagePresent, EXPECTED_ALERT_MESSAGE_ASSERTION_MESSAGE),
                () -> Assertions.assertTrue(areExpectedErrorsPresent, EXPECTED_ERRORS_PRESENCE_ASSERTION_MESSAGE),
                () -> Assertions.assertTrue(isEditAttributesWindowAppear, "Edit Attributes window didn't appear"),
                () -> Assertions.assertTrue(isEditAttributesWindowDisappear, "Edit Attributes window didn't disappear"),
                () -> Assertions.assertTrue(isTargetLocationUpdate, "Target Location doesn't update"),
                () -> Assertions.assertTrue(areExpectedErrorsAbsent, EXPECTED_ERRORS_ABSENCE_ASSERTION_MESSAGE)
        );
    }
}
