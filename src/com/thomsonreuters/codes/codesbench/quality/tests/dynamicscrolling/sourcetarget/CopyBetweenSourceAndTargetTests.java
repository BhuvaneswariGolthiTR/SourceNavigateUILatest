package com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.sourcetarget;

import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTreePageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.editor.EditorTextPage;
import com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.base.AbstractSourceTargetTests;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.MarkupValue;
import com.thomsonreuters.codes.codesbench.quality.utilities.robot.RobotUtils;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.Alert;
import org.openqa.selenium.Keys;

import java.awt.event.KeyEvent;
import java.util.Map;

import static com.thomsonreuters.codes.codesbench.quality.menuelements.mainmenuelements.SourceMenuElements.source;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.*;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.DELTA;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.popups.InsertWestMarkupPageElements.ADDED;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.popups.InsertWestMarkupPageElements.DELETED;
import static com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.base.AbstractSourceTargetTests.DocumentInfo.EFFECTIVE_DATE;
import static com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.base.AbstractSourceTargetTests.DocumentInfo.PREP_DOCUMENT_INFO;
import static com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.base.AbstractSourceTargetTests.RadioButton.YES;
import static com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets.IOWA_DEVELOPMENT;
import static com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets.TEXAS_DEVELOPMENT;
import static java.lang.String.format;
import static org.openqa.selenium.Keys.CONTROL;
import static org.openqa.selenium.Keys.HOME;
import static org.openqa.selenium.Keys.SPACE;
import static org.openqa.selenium.Keys.chord;

public class CopyBetweenSourceAndTargetTests extends AbstractSourceTargetTests
{
    private static final String PARENT_METADATA_BLOCK = format(PARENT, "metadata.block");
    private static final String FOLLOWING_SIBLING_PARATEXT = format(FOLLOWING_SIBLING, "paratext");
    protected static final String FOOTNOTE_REFERENCE_VALUE_1 = String.valueOf(1);

    public static Object[][] contentSetsAndUuids()
    {
        return new Object[][]
                {
                        {IOWA_DEVELOPMENT, IOWA_TARGET_NODE_UUID, IOWA_SOURCE_RENDITION_UUID},
                        {TEXAS_DEVELOPMENT, TEXAS_TARGET_NODE_UUID, TEXAS_SOURCE_RENDITION_UUID}
                };
    }

    /**
     * STORY/BUG - HALCYONST-13868/HALCYONST-16086 <br>
     * SUMMARY - Copy and paste of text with markup from source to target (Target) <br>
     * USER - LEGAL <br>
     */
    @ParameterizedTest(name = "Test: {displayName}; Index: {index}; Arguments: {argumentsWithNames}")
    @MethodSource("contentSetsAndUuids")
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void copyAndPasteOfTextWithMarkupFromSourceToTargetTargetLegalTest(ContentSets contentSet,
                                                                              String nodeUuid, String renditionUuid)
    {
        softAssertions = new SoftAssertions();

        openTargetNodeWithSpecifiedContentSetAndUuidInDsEditor(contentSet, nodeUuid);

        //Select automatic credit generation 'YES' radio button
        selectSpecifiedAutomaticCreditGenerationRadioButtonAndReturnDefaultSourceTagValue(YES);

        //Open source rendition with specified uuid in DS editor
        hierarchyNavigatePage().switchToHierarchyEditPage();
        Map<DocumentInfo, String> documentInfo = openSourceRenditionWithSpecifiedUuidAndReturnDocumentInfoAsMap(renditionUuid);
        String prepDocumentInfo = documentInfo.get(PREP_DOCUMENT_INFO);
        String effectiveDate = documentInfo.get(EFFECTIVE_DATE);

        //Copy text from paragraph with added, deleted markups and footnote reference from source rendition
        switchToSecondDocument();

        //Below lines are commented because the text paragraph already contains the markups which is required.
        String[] phrases = {"added text", "deleted text"};
        String[] markups = {"added.material",  "deleted.material"};
        addMarkupsAndFootnoteReferenceAndSelectTextFromParagraphInSourceRendition(phrases, markups);
        String paratextXpath = editorTextPage().getModifiedByXpath(user()) + PARENT_METADATA_BLOCK + FOLLOWING_SIBLING_PARATEXT;
        String deletedMaterialSource = editorTextPage().getElementsText(paratextXpath + DELETED_MATERIAL);
        String addedMaterialSource = editorTextPage().getElementsText(paratextXpath + ADDED_MATERIAL);

        RobotUtils.ctrlCUsingRobot();

        //Paste text with added, deleted markups and footnote reference in paragraph to target node
        switchToFirstDocument();
        editorTextPage().click(TEXT_PARAGRAPH_SPAN + FOLLOWING_SIBLING_PARATEXT);
        editorTextPage().sendKeys(HOME);
        RobotUtils.ctrlVUsingRobot();

        assertAutomaticCreditGenerationAlert(YES);

        switchToFirstDocument();
        editorTextPage().click(TEXT_PARAGRAPH_SPAN);

        //Assert that paragraph with pasted text has a Merged from tag specifying the PREP document and effective date info
        softAssertThatPastedContentHasAMergedFromTagSpecifyingThePrepDocAndEffectiveDateInfo(prepDocumentInfo, effectiveDate);

        //Assert that paragraph with pasted text has a Modified by tag with user's name and current date
        softAssertThatPastedContentHasAModifiedByTagWithUsersNameAndCurrentDate();

        softAssertDeletedMarkupAccordingToContentSet(contentSet, deletedMaterialSource);
        softAssertAddedMarkupAccordingToContentSet(contentSet, addedMaterialSource);

        softAssertCreditLineModifiedByTextMergeTag(YES);
        softAssertCreditLineMergedFromTag(YES, prepDocumentInfo, effectiveDate);
        softAssertCreditLineTextChildAmendmentWithEffectiveDateInfo(YES, effectiveDate);

        softAssertThatTheExpectedFootnoteMessageIsAppearedInTheEditorMessagePane(prepDocumentInfo);
        softAssertThatThereAreNoErrorsInBothMessagePanes();

        softAssertions.assertAll();
    }

//  ------------- Assistive methods: -------------

    private void addMarkupsAndFootnoteReferenceAndSelectTextFromParagraphInSourceRendition(String[] phrases, String[] markups)
    {
        editorTextPage().insertPhrasesWithRespectiveMarkup(TEXT_PARAGRAPH_SPAN, phrases, markups, 3);
        editorTextPage().sendKeys(HOME);
        editorTextPage().sendKeys(SPACE);
        editorTextPage().sendKeys(HOME);
        editorPage().openInsertFootnoteDialog();
        insertFootnotePage().setFootnoteReferenceType("footnote.reference");
        insertFootnotePage().setFootnoteReference("1");
        insertFootnotePage().uncheckNewFootnoteCheckbox();
        insertFootnotePage().clickSaveFootnote();
        editorPage().switchToEditor();
        editorPage().switchToEditorTextFrameByIndex(3);
        editorTextPage().sendKeys(HOME);
        int length = 0;
        for(String phrase : phrases)
        {
            length += phrase.length();
        }
        editorTextPage().highlightHelper(length + markups.length * 2 + phrases.length - 1 + 5);
    }
}
