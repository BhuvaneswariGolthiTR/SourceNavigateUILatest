package com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.base;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.autoIT.AutoITUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.util.EnumMap;
import java.util.Map;

import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.*;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.popups.EditorPreferencesPageElements.DEFAULT_SOURCE_TAG_SELECTOR;
import static com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.base.AbstractSourceTargetTests.DocumentInfo.EFFECTIVE_DATE;
import static com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.base.AbstractSourceTargetTests.DocumentInfo.PREP_DOCUMENT_INFO;
import static com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.base.AbstractSourceTargetTests.RadioButton.NO;
import static com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.base.AbstractSourceTargetTests.RadioButton.YES;
import static com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets.IOWA_DEVELOPMENT;
import static com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets.TEXAS_DEVELOPMENT;
import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;

public abstract class AbstractSourceTargetTests extends TestService
{
    protected static final String IOWA_TARGET_NODE_UUID = "I039616E114F211DA8AC5CD53670E6B4E";
    protected static final String IOWA_SOURCE_RENDITION_UUID = "I8E9B1790988411E2B101869D1571CAFF";
    protected static final String TEXAS_TARGET_NODE_UUID = "I291B3CF06BA84EB7B935F13940DD2427";
    protected static final String TEXAS_SOURCE_RENDITION_UUID = "ID84EDB509C8B11E293F0DC65E5A4CDC3";
    private static final String AUTOMATIC_CREDIT_GENERATION_ALERT_MESSAGE = "Run user assisted credit generation now?";
    protected static final String HIGHLIGHTED_PARATEXT = PARATEXT + CLASS_HIGHLIGHTED_POSTFIX;
    protected static final String ADDED_MATERIAL = "/added.material";
    protected static final String DELETED_MATERIAL = "/deleted.material";
    protected static final String DELETED_MARKUP = HIGHLIGHTED_PARATEXT + DELETED_MATERIAL;
    protected static final String ADDED_MARKUP = HIGHLIGHTED_PARATEXT + ADDED_MATERIAL;
    private static final String MESSAGE_PANE_FOOTNOTE_MESSAGE =
            "[Manual Source Integrate]: A footnote.reference from |%s| was found in paste content. " +
                    "Storing footnote reference information for footnote synchronization. " +
                    "You should copy footnote |1| belonging to the same subsection next.";
    protected SoftAssertions softAssertions;

    @BeforeEach
    public void login()
    {
        homePage().goToHomePage();
        loginPage().logIn();
    }

    @AfterEach
    public void closeAllOpenedDocuments()
    {
        editorPage().closeAllOpenedDocumentsWithDiscardingChangesIfAppeared();
    }

    public enum RadioButton
    {
        YES,
        NO
    }

    public enum DocumentInfo
    {
        PREP_DOCUMENT_INFO,
        EFFECTIVE_DATE
    }

//  ------------- Assistive methods: -------------

    protected void openTargetNodeWithSpecifiedContentSetAndUuidInDsEditor(ContentSets contentSet, String nodeUuid)
    {
        hierarchyNavigatePage().goToHierarchyPage(contentSet.getCode());
        hierarchySearchPage().searchNodeUuid(nodeUuid);
        siblingMetadataPage().selectedSiblingMetadataEditContent();
        editorPage().switchToEditorTextFrame();
        editorPage().switchToEditor();
    }

    protected void openSourceRenditionInDsEditor(String renditionUuid)
    {
        sourcePage().goToSourcePageWithRenditionUuids(renditionUuid);
        sourceNavigateGridPage().firstRenditionEditContent();
    }

    protected Map<DocumentInfo, String> openSourceRenditionWithSpecifiedUuidAndReturnDocumentInfoAsMap(String renditionUuid)
    {
        Map<DocumentInfo, String> documentInfo = new EnumMap<>(DocumentInfo.class);
        sourcePage().goToSourcePageWithRenditionUuids(renditionUuid);
        sourceNavigateGridPage().rightClickFirstRendition();
        sourceNavigateGridPage().breakOutOfFrame();
        sourceNavigateContextMenu().openRenditionProperties();
        renditionPropertiesPage().switchToRenditionPropertiesWindow();
        documentInfo.put(PREP_DOCUMENT_INFO, renditionPropertiesPage().getPageHeader().split("\\|")[0].trim());
        documentInfo.put(EFFECTIVE_DATE, getSourceRenditionEffectiveDate());
        renditionPropertiesPage().clickCancel();
        sourceNavigateGridPage().firstRenditionEditContent();
        return documentInfo;
    }

    protected String selectSpecifiedAutomaticCreditGenerationRadioButtonAndReturnDefaultSourceTagValue(
            RadioButton automaticCreditGenerationRadioButtonValue)
    {
        editorToolbarPage().clickConfigureEditorSessionPreferences();
        if (automaticCreditGenerationRadioButtonValue == YES)
        {
            editorPreferencesPage().clickAutomaticCreditGenerationYesRadioButton();
        }
        if (automaticCreditGenerationRadioButtonValue == NO)
        {
            editorPreferencesPage().clickAutomaticCreditGenerationNoRadioButton();
        }
        String defaultSourceTag = editorPreferencesPage()
                .getElementsAttribute(DEFAULT_SOURCE_TAG_SELECTOR, "value");
        editorPreferencesPage().clickSaveButton();
        return defaultSourceTag;
    }

    protected void switchToFirstDocument()
    {
        editorPage().switchToEditor();
        editorPage().switchToTheOpenedDocument(1);
        editorPage().switchToEditorTextFrame();
    }

    protected void switchToSecondDocument()
    {
        editorPage().switchToEditor();
        editorPage().switchToTheOpenedDocument(2);
        editorPage().switchToEditorTextFrameByIndex(3);
    }

    protected void openContextMenuOnElement(String xPath)
    {
        editorTextPage().scrollToView(xPath);
        editorTextPage().rightClick(xPath);
        editorPage().breakOutOfFrame();
    }

    protected void softAssertDeletedMarkupAccordingToContentSet(ContentSets contentSet, String deletedMaterial)
    {
        if (contentSet == IOWA_DEVELOPMENT)
        {
            //Assert that the 'deleted' markup and text within the 'deleted' markup is removed
            softAssertions.assertThat(editorTextPage().doesElementExist(DELETED_MARKUP))
                    .as("The 'deleted' markup should be removed")
                    .isFalse();
        }
        if (contentSet == TEXAS_DEVELOPMENT)
        {
            //Assert that the 'deleted' markup is remained in the text
            softAssertions.assertThat(editorTextPage().doesElementExist(DELETED_MARKUP))
                    .as("The 'deleted' markup should remain")
                    .isTrue();
            softAssertions.assertThat(editorTextPage().getElementsText(DELETED_MARKUP))
                    .as("Text within the 'deleted' markup should remain")
                    .contains(deletedMaterial);
        }
    }

    protected void softAssertAddedMarkupAccordingToContentSet(ContentSets contentSet, String addedMaterial)
    {
        if (contentSet == IOWA_DEVELOPMENT)
        {
            //Assert that the 'added' markup is removed but text within the 'added' markup is remained
            softAssertions.assertThat(editorTextPage().doesElementExist(ADDED_MARKUP))
                    .as("The 'added' markup should be removed")
                    .isFalse();
            softAssertions.assertThat(editorTextPage().getElementsText(HIGHLIGHTED_PARATEXT))
                    .as("Text within the 'added' markup should remain")
                    .contains(addedMaterial);
        }
        if (contentSet == TEXAS_DEVELOPMENT)
        {
            //Assert that the 'added' markup is remained in the text
            softAssertions.assertThat(editorTextPage().doesElementExist(ADDED_MARKUP))
                    .as("The 'added' markup should remain")
                    .isTrue();
            softAssertions.assertThat(editorTextPage().getElementsText(ADDED_MARKUP))
                    .as("Text within the 'added' markup should remain")
                    .contains(addedMaterial);
        }
    }

    private String getSourceRenditionEffectiveDate()
    {
        String effectiveDate = renditionPropertiesPage().getEffectiveDate();
        if (effectiveDate.equals(""))
        {
            effectiveDate = renditionPropertiesPage().getEarliestEffectiveDate();
        }
        return effectiveDate;
    }

    protected void assertAutomaticCreditGenerationAlert(RadioButton automaticCreditGenerationRadioButtonValue)
    {
        if (automaticCreditGenerationRadioButtonValue == YES)
        {
            //Assert that expected alert is appeared
            assertThat(AutoITUtils.verifyAlertTextAndAccept(true, AUTOMATIC_CREDIT_GENERATION_ALERT_MESSAGE))
                    .as(format("The alert with message %s should be appeared", AUTOMATIC_CREDIT_GENERATION_ALERT_MESSAGE))
                    .isTrue();
        }
        if (automaticCreditGenerationRadioButtonValue == NO)
        {
            //Assert that alert is NOT appeared
            assertThat(AutoITUtils.verifyAlertTextAndAccept(false, AUTOMATIC_CREDIT_GENERATION_ALERT_MESSAGE))
                    .as(format("The alert with message %s should NOT be appeared", AUTOMATIC_CREDIT_GENERATION_ALERT_MESSAGE))
                    .isTrue();
        }
    }

    protected void softAssertCreditLineModifiedByTextMergeTag(RadioButton automaticCreditGenerationRadioButtonValue)
    {
        String creditLine = format(SPAN_BY_TEXT, "Credit Line");
        if (automaticCreditGenerationRadioButtonValue == YES)
        {
            //Assert that the 'Credit Line' has a Modified by Text Merge tag with the current date
            editorTextPage().click(creditLine);
            softAssertions.assertThat(editorTextPage().getElementsText(HIGHLIGHTED_PARA + MODIFIED_BY_MNEMONIC))
                    .as("The 'Credit Line' should have a Modified by Text Merge tag with the current date")
                    .contains(format("Text Merge %s", DateAndTimeUtils.getCurrentDateMMddyyyy()));
        }
        if (automaticCreditGenerationRadioButtonValue == NO){
            editorPage().switchToEditor();
            editorTreePage().expandEditorsTreeAndClickNode("credit");
            switchToFirstDocument();
            editorTextPage().click(creditLine);
            //Assert that the 'Credit Line' does NOT have a Modified by Text Merge tag with the current date
            softAssertions.assertThat(editorTextPage().doesElementExist(HIGHLIGHTED_PARA + MODIFIED_BY_MNEMONIC))
                    .as("The 'Credit Line' should NOT have a Modified by Text Merge tag with the current date")
                    .isFalse();
        }
    }

    protected void softAssertCreditLineMergedFromTag(RadioButton automaticCreditGenerationRadioButtonValue,
                                                     String prepDocumentInfo, String effectiveDate)
    {
        if (automaticCreditGenerationRadioButtonValue == YES)
        {
            //Assert that the 'Credit Line' has a Merged from tag that contains the PREP document and effective date info
            String mergedFrom = editorTextPage().getElementsText(HIGHLIGHTED_PARA + MERGED_FROM_TAG);
            softAssertions.assertThat(mergedFrom.contains(prepDocumentInfo) && mergedFrom.contains(effectiveDate))
                    .as(format("'Credit Line' should have a Merged from tag with %s info and %s effective date",
                            prepDocumentInfo, effectiveDate))
                    .isTrue();
        }
        if (automaticCreditGenerationRadioButtonValue == NO)
        {
            //Assert that the 'Credit Line' does NOT have a Merged from tag that contains the PREP document and effective date info
            softAssertions.assertThat(editorTextPage().doesElementExist(HIGHLIGHTED_PARA + MERGED_FROM_TAG))
                    .as(format("'Credit Line' should NOT have a Merged from tag with %s info and %s effective date",
                            prepDocumentInfo, effectiveDate))
                    .isFalse();
        }
    }

    protected void softAssertCreditLineTextChildAmendmentWithEffectiveDateInfo(
            RadioButton automaticCreditGenerationRadioButtonValue, String effectiveDate)
    {
        String formattedDate = DateAndTimeUtils.getFormattedDate(effectiveDate);
        formattedDate = formattedDate.contains("Sep.")
                ? formattedDate.replace("Sep.", "Sept.")
                : formattedDate;
        if (automaticCreditGenerationRadioButtonValue == YES)
        {
            //Assert that the 'Credit Line' text child is amended with the PREP document effective date info
            softAssertions.assertThat(editorTextPage().getElementsText(HIGHLIGHTED_PARA + PARATEXT))
                    .as(format("The 'Credit Line' text child should contain %s date", formattedDate))
                    .contains(formattedDate);
        }
        if (automaticCreditGenerationRadioButtonValue == NO)
        {
            //Assert that the 'Credit Line' text child is NOT amended with the PREP document effective date info
            softAssertions.assertThat(editorTextPage().getElementsText(HIGHLIGHTED_PARA + PARATEXT))
                    .as(format("The 'Credit Line' text child should NOT contain %s date", formattedDate))
                    .doesNotContain(formattedDate);
        }
    }

    protected void softAssertThatThereAreNoErrorsInBothMessagePanes()
    {
        softAssertions.assertThat(editorMessagePage().checkForErrorInBothMessagePanes())
                .as("There should be no errors in the message panes")
                .isFalse();
    }

    protected void softAssertThatPastedContentHasAMergedFromTagSpecifyingThePrepDocAndEffectiveDateInfo(
            String prepDocumentInfo, String effectiveDate)
    {
        String mergedFrom = editorTextPage().getElementsText(HIGHLIGHTED_PARA + MERGED_FROM_TAG);
        softAssertions.assertThat(mergedFrom.contains(prepDocumentInfo) && mergedFrom.contains(effectiveDate))
                .as(format("Pasted content should have a Merged from tag with %s info and %s effective date",
                        prepDocumentInfo, effectiveDate))
                .isTrue();
    }

    protected void softAssertThatPastedContentHasAModifiedByTagWithUsersNameAndCurrentDate()
    {
        softAssertions.assertThat(editorTextPage().getElementsText(HIGHLIGHTED_PARA + editorTextPage().getModifiedByXpath(user())))
                .as("Pasted content should have a Modified by tag with user's name and current date")
                .contains(user().getEditorModifiedByUsername() + DateAndTimeUtils.getCurrentDateMMddyyyy());
    }

    protected void softAssertThatTheExpectedFootnoteMessageIsAppearedInTheEditorMessagePane(String prepDocumentInfo)
    {
        String expectedMessage = format(MESSAGE_PANE_FOOTNOTE_MESSAGE, prepDocumentInfo);
        editorPage().switchToEditor();
        softAssertions.assertThat(editorMessagePage().checkMessage(expectedMessage))
                .as(format("The %s message should be appeared in the editor message pane", expectedMessage))
                .isTrue();
    }
}
