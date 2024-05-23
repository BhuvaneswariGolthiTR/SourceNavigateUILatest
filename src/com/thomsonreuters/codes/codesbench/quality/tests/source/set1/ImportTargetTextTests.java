package com.thomsonreuters.codes.codesbench.quality.tests.source.set1;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class ImportTargetTextTests extends TestService
{
    /**
     * STORY: HALCYONST-812 <br>
     * SUMMARY: Tests importing text into a subsection amend delta.<br>
     * USER:  LEGAL <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void subsectionAmendLegalTest()
    {
        String contentSet = "Iowa (Development)";
        String renditionStatus = "PREP";
        String docNumber = "2305";

        String level = "SUBSECTION";
        String action = "AMEND";
        String sectionNumber = "10";

        String editorLevel = "Subsection";

        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterContentSet(contentSet);
        sourceNavigateFiltersAndSortsPage().setFilterRenditionStatus(renditionStatus);
        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(docNumber);
        sourceNavigateFooterToolsPage().refreshTheGrid();

        sourceNavigateGridPage().clickFirstRendition();
        sourceNavigateTabsPage().goToDeltaTab();

        sourceNavigateFiltersAndSortsPage().setFilterDeltaLevel(level);
        sourceNavigateFiltersAndSortsPage().setFilterAction(action);
        sourceNavigateFiltersAndSortsPage().setDeltaFilterSectionNumber(sectionNumber);
        sourceNavigateFooterToolsPage().refreshTheGrid();

        sourceNavigateGridPage().rightClickFirstRendition();
        renditionContextMenu().modifyCiteLocate();

        String targetNode = sourceNavigateGridPage().getDeltaTargetNodeText();
        String targetSubNode = sourceNavigateGridPage().getDeltaTargetSubNodeText();
        String targetSubNodeInParatext = sourceNavigateGridPage().getDeltaTargetSubNodeText();

        sourceNavigateGridPage().rightClickFirstRendition();
        deltaContextMenu().goToViewTargetInHierarchy();

        hierarchyNavigatePage().editSelectedSiblingMetadata();

        editorPage().switchDirectlyToTextFrame();
        String textParagraph = editorTextPage().getTextFromTextParagraphStartingWithTextGiven(targetSubNodeInParatext);

        editorPage().closeDocumentWithNoChanges();
        hierarchyNavigatePage().switchToHierarchyEditPage();
        hierarchyNavigatePage().closeCurrentWindowIgnoreDialogue();

        sourcePage().switchToDeltaNavigatePage();
        sourceNavigateGridPage().rightClickFirstDelta();
        deltaContextMenu().editDelta();

        editorPage().switchDirectlyToTextFrame();
        editorTextPage().clickDeltaAmendLabel(editorLevel);
        editorTextPage().rightClickDeltaAmendLabel(editorLevel);
        editorPage().breakOutOfFrame();
        editorTextContextMenuPage().importTargetText();
        editorPage().switchDirectlyToTextFrame();

        boolean isTargetNodeCorrect = editorTextPage().getTargetLocationSection().equals(targetNode);
        boolean isTargetSubNodeCorrect = editorTextPage().getTargetLocationSubsection().equals(targetSubNode);
        boolean isTextParagraphCorrect = editorTextPage().getTextFromTextParagraphStartingWithTextGiven(targetSubNode).equals(textParagraph);

        editorPage().closeDocumentAndDiscardChanges();

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(isTargetNodeCorrect, "Target Node did contain the correct value"),
            () -> Assertions.assertTrue(isTargetSubNodeCorrect, "Target SubNode did contain the correct value"),
            () -> Assertions.assertTrue(isTextParagraphCorrect, "Text paragraph did contain the correct value")
        );
    }

    /**
     * STORY: HALCYONST-872 <br>
     * SUMMARY: Tests importing text into a section amend delta.<br>
     * USER: LEGAL <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void sectionAmendLegalTest()
    {
        String multipleDuplicate = "None";
        String contentSet = "Iowa (Development)";
        String renditionStatus = "PREP";
        String docNumber = "324";

        String deltaLevel = "SECTION";
        String deltaAction = "AMEND";

        String editorLevel = "Section";

        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterMultipleDuplicate(multipleDuplicate);
        sourceNavigateFiltersAndSortsPage().setFilterContentSet(contentSet);
        sourceNavigateFiltersAndSortsPage().setFilterRenditionStatus(renditionStatus);
        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(docNumber);
        sourceNavigateFooterToolsPage().refreshTheGrid();
        sourceNavigateGridPage().unlockFirstRendition();

        sourceNavigateGridPage().clickFirstRendition();
        sourceNavigateTabsPage().goToDeltaTab();

        sourceNavigateFiltersAndSortsPage().setFilterDeltaLevel(deltaLevel);
        sourceNavigateFiltersAndSortsPage().setFilterAction(deltaAction);
        sourceNavigateFooterToolsPage().refreshTheGrid();

        sourceNavigateGridPage().rightClickFirstRendition();
        renditionContextMenu().modifyCiteLocate();

        String targetNode = sourceNavigateGridPage().getDeltaTargetNodeText();

        sourceNavigateGridPage().rightClickFirstRendition();
        deltaContextMenu().goToViewTargetInHierarchy();

        siblingMetadataPage().selectedSiblingMetadataEditContent();

        int numberOfParagraphs = editorTextPage().getNumberOfParagraphs();
        List<String> textParagraphsAndTheirNumberFromHierarchy = editorTextPage().getListOfParagraphText();

        editorPage().closeDocumentWithNoChanges();
        hierarchyNavigatePage().switchToHierarchyEditPage();
        hierarchyNavigatePage().closeCurrentWindowIgnoreDialogue();
        editorPage().switchToEditor();
        editorPage().closeCurrentWindowIgnoreDialogue();

        sourcePage().switchToDeltaNavigatePage();

        sourceNavigateGridPage().rightClickFirstDelta();
        deltaContextMenu().editDelta();
        editorPage().switchToEditor();
        editorPage().switchDirectlyToTextFrame();

        editorTextPage().clickDeltaAmendLabel(editorLevel);
        editorTextPage().rightClickDeltaAmendLabel(editorLevel);
        editorPage().breakOutOfFrame();
        editorTextContextMenuPage().importTargetText();
        editorPage().switchDirectlyToTextFrame();
        List<String> textParagraphAndTheirNumberFromSource = editorTextPage().getListOfParagraphText().subList(0,numberOfParagraphs);

        boolean isTargetCorrect = editorTextPage().getTargetLocationSection().equals(targetNode);
        boolean areTextParagraphsCorrect = textParagraphAndTheirNumberFromSource.equals(textParagraphsAndTheirNumberFromHierarchy);

        editorPage().closeDocumentAndDiscardChanges();

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(isTargetCorrect,"Target in Editor equals TargetNode from table"),
            () -> Assertions.assertTrue(areTextParagraphsCorrect,"Text Paragraphs equal to pulled previously text")
        );
    }
}