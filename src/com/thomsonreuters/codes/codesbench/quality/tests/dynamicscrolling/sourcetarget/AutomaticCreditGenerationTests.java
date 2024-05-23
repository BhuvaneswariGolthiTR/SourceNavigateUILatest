package com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.sourcetarget;

import com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.base.AbstractSourceTargetTests;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Map;

import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.*;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.mnemonics.SMP;
import static com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.base.AbstractSourceTargetTests.DocumentInfo.EFFECTIVE_DATE;
import static com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.base.AbstractSourceTargetTests.DocumentInfo.PREP_DOCUMENT_INFO;
import static com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.base.AbstractSourceTargetTests.RadioButton.NO;
import static com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.base.AbstractSourceTargetTests.RadioButton.YES;
import static com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets.IOWA_DEVELOPMENT;
import static com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets.TEXAS_DEVELOPMENT;
import static java.lang.String.format;

public class AutomaticCreditGenerationTests extends AbstractSourceTargetTests
{
    private static final String NOPUB_PUBTAG = "NOPUB";

    public static Object[][] contentSetsRadioButtonsAndUuids()
    {
        return new Object[][]
                {
                        //todo wait for mnemonics merged etc
                        {IOWA_DEVELOPMENT, YES, IOWA_TARGET_NODE_UUID, IOWA_SOURCE_RENDITION_UUID},
                        {IOWA_DEVELOPMENT, NO, IOWA_TARGET_NODE_UUID, IOWA_SOURCE_RENDITION_UUID},
                        {TEXAS_DEVELOPMENT, YES, TEXAS_TARGET_NODE_UUID, TEXAS_SOURCE_RENDITION_UUID},
                        //TODO scroll to delta 7
                        {TEXAS_DEVELOPMENT, NO, TEXAS_TARGET_NODE_UUID, TEXAS_SOURCE_RENDITION_UUID}
                };
    }

    @ParameterizedTest(name = "Test: {displayName}; Index: {index}; Arguments: {argumentsWithNames}")
    @MethodSource("contentSetsRadioButtonsAndUuids")
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void automaticCreditGenerationTargetLegalTest(
            ContentSets contentSet, RadioButton automaticCreditGenerationRadioButtonValue,
            String nodeUuid, String renditionUuid)
    {
        softAssertions = new SoftAssertions();

        openTargetNodeWithSpecifiedContentSetAndUuidInDsEditor(contentSet, nodeUuid);

        //Select specified automatic credit generation radio button
        String defaultSourceTag = selectSpecifiedAutomaticCreditGenerationRadioButtonAndReturnDefaultSourceTagValue(
                automaticCreditGenerationRadioButtonValue);

        //Open source rendition with specified uuid in DS editor
        hierarchyNavigatePage().switchToHierarchyEditPage();
        Map<DocumentInfo, String> documentInfo = openSourceRenditionWithSpecifiedUuidAndReturnDocumentInfoAsMap(renditionUuid);
        String prepDocumentInfo = documentInfo.get(PREP_DOCUMENT_INFO);
        String effectiveDate = documentInfo.get(EFFECTIVE_DATE);

        //Copy paragraph with added, deleted markups and footnote reference from source rendition
        switchToSecondDocument();
        selectParagraphInSourceRendition(renditionUuid);
        String deletedMaterialSource = editorTextPage().getElementsText(DELETED_MARKUP);
        String addedMaterialSource = editorTextPage().getElementsText(ADDED_MARKUP);
        openContextMenuOnTextParagraphInSourceRendition(renditionUuid);
        editorTextContextMenu().copyCtrlC();

        //Paste paragraph with added, deleted markups and footnote reference to target node
        switchToFirstDocument();
        openContextMenuOnElement(TEXT_PARAGRAPH_SPAN);
        editorTextContextMenu().pasteSiblingCtrlV();

        assertAutomaticCreditGenerationAlert(automaticCreditGenerationRadioButtonValue);

        switchToFirstDocument();
        editorTextPage().click(PARA + "[2]/span");

        //Assert that pasted paragraph has a Merged from tag specifying the PREP document and effective date info
        softAssertThatPastedContentHasAMergedFromTagSpecifyingThePrepDocAndEffectiveDateInfo(prepDocumentInfo, effectiveDate);

        //Assert that pasted paragraph has a Modified by tag with user's name and current date
        softAssertThatPastedContentHasAModifiedByTagWithUsersNameAndCurrentDate();

        //Assert that pasted paragraph has an SMP mnemonic
        softAssertions.assertThat(editorTextPage().getElementsText(SMP.xpath()))
                .as(format("Pasted paragraph should have an %s mnemonic", SMP.name()))
                .isEqualTo(SMP.name());

        //Assert that pasted paragraph has an NOPUB pubtag
        softAssertions.assertThat(editorTextPage().getElementsText(HIGHLIGHTED_PARA + ANY_PUBTAG_IN_METADATA_BLOCK))
                .as(format("Pasted paragraph should have an %s pubtag", NOPUB_PUBTAG))
                .isEqualTo(NOPUB_PUBTAG);

        //Assert that pasted paragraph has default source tag
        softAssertions.assertThat(editorTextPage().getElementsText(HIGHLIGHTED_PARA + SOURCE_TAG))
                .as(format("Pasted paragraph should have %s default source tag", defaultSourceTag))
                .isEqualTo(defaultSourceTag);

        softAssertDeletedMarkupAccordingToContentSet(contentSet, deletedMaterialSource);
        softAssertAddedMarkupAccordingToContentSet(contentSet, addedMaterialSource);

        softAssertCreditLineModifiedByTextMergeTag(automaticCreditGenerationRadioButtonValue);
        softAssertCreditLineMergedFromTag(automaticCreditGenerationRadioButtonValue, prepDocumentInfo, effectiveDate);
        softAssertCreditLineTextChildAmendmentWithEffectiveDateInfo(automaticCreditGenerationRadioButtonValue, effectiveDate);

        softAssertThatTheExpectedFootnoteMessageIsAppearedInTheEditorMessagePane(prepDocumentInfo);
        softAssertThatThereAreNoErrorsInBothMessagePanes();

        softAssertions.assertAll();
    }

//  ------------- Assistive methods: -------------

    private void selectParagraphInSourceRendition(String renditionUuid)
    {
        if (renditionUuid.equals(IOWA_SOURCE_RENDITION_UUID))
        {
            editorTextPage().click(TEXT_PARAGRAPH_SPAN);
        }
        if (renditionUuid.equals(TEXAS_SOURCE_RENDITION_UUID))
        {
            editorTextPage().click(format("(%s)[2]", DELTA) + TEXT_PARAGRAPH_SPAN);
        }
    }

    private void openContextMenuOnTextParagraphInSourceRendition(String renditionUuid)
    {
        if (renditionUuid.equals(IOWA_SOURCE_RENDITION_UUID))
        {
            openContextMenuOnElement(TEXT_PARAGRAPH_SPAN);
        }
        if (renditionUuid.equals(TEXAS_SOURCE_RENDITION_UUID))
        {
            openContextMenuOnElement(format("(%s)[2]", DELTA) + TEXT_PARAGRAPH_SPAN);
        }
    }
}
