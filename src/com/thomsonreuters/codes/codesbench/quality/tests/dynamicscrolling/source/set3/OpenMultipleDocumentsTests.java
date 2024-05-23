package com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.source.set3;

import com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.base.AbstractOpenMultipleDocumentsTests;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class OpenMultipleDocumentsTests extends AbstractOpenMultipleDocumentsTests
{
    private static final String FIRST_RENDITION_UUID = "I124CF7D1619D11E28B049F1D7A89B350";
    private static final String SECOND_RENDITION_UUID = "I688E43B0714811EBA0B4D34B1DA9F369";
    private static final String THIRD_RENDITION_UUID = "IB6FA198060D011EBBE6FA8AD556CB520";
    private static final String RENDITION_WITH_SECTIONS_AND_DELTAS_UUID = "I8E9B1790988411E2B101869D1571CAFF";

    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void openMultipleSourceRenditionsLegalTest()
    {
        //Go to Pending Rendition Navigate page and open source renditions in DS editor
        int numberOfTheOpenedRenditions = sourcePage().openMultipleSourceRenditions(
                FIRST_RENDITION_UUID, SECOND_RENDITION_UUID, THIRD_RENDITION_UUID);

        //Assert that each blue bar of the opened source rendition has different metadata
        assertThatEachBlueBarOfTheOpenedDocumentHasDifferentMetadata();
        //Assert that source renditions are opened in DS editor
        assertThatDocumentsAreOpenedInDsEditor(numberOfTheOpenedRenditions);
        //Assert that list with editor links is displayed in html window
        assertThatListWithEditorLinksIsDisplayedInHtmlWindow(numberOfTheOpenedRenditions);
        //Assert that current position is not bounced anywhere after Copy and Paste Text Paragraph
        assertThatCurrentPositionIsNotBouncedAfterCopyPasteTextParagraphByIframeIndex(5);
    }

    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void openMultipleSourceSectionsLegalTest()
    {
        //Go to Pending Rendition Navigate page, click the rendition and open source sections in DS editor
        openRenditionWithSectionsAndDeltas();
        sourcePage().goToSectionTab();
        int numberOfTheOpenedSections = sourcePage().openMultipleSourceSections(3);

        //Assert that source sections are opened in DS editor
        assertThatDocumentsAreOpenedInDsEditor(numberOfTheOpenedSections);
        //Assert that list with editor links is displayed in html window
        assertThatListWithEditorLinksIsDisplayedInHtmlWindow(numberOfTheOpenedSections);
        //Assert that current position is not bounced anywhere after Copy and Paste Text Paragraph
        assertThatCurrentPositionIsNotBouncedAfterCopyPasteTextParagraphByIframeIndex(5);
    }

    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void openMultipleSourceDeltasLegalTest()
    {
        //Go to Pending Rendition Navigate page, click the rendition and open source deltas in DS editor
        openRenditionWithSectionsAndDeltas();
        sourcePage().goToDeltaTab();
        int numberOfTheOpenedDeltas = sourcePage().openMultipleSourceDeltas(3);

        //Assert that source deltas are opened in DS editor
        assertThatDocumentsAreOpenedInDsEditor(numberOfTheOpenedDeltas);
        //Assert that list with editor links is displayed in html window
        assertThatListWithEditorLinksIsDisplayedInHtmlWindow(numberOfTheOpenedDeltas);
        //Assert that current position is not bounced anywhere after Copy and Paste Text Paragraph
        assertThatCurrentPositionIsNotBouncedAfterCopyPasteTextParagraphByIframeIndex(5);
    }

//  ------------- Assistive methods: -------------

    private void openRenditionWithSectionsAndDeltas()
    {
        //Go to Pending Rendition Navigate page and click the rendition
        sourcePage().goToSourcePageWithRenditionUuids(RENDITION_WITH_SECTIONS_AND_DELTAS_UUID);
        sourceNavigateGridPage().clickFirstRendition();
    }
}
