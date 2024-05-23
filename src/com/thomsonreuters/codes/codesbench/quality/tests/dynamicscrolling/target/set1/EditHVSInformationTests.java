package com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.target.set1;

import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.popups.EditHVSInformationPageElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class EditHVSInformationTests extends TestService
{

	@Test
	@IE_EDGE_MODE
	@LEGAL
	@LOG
    public void editHVSTargetLegalTest() throws InterruptedException
    {
 		String nodeUuid = "IAFE1D8D01B8011DC9EFD99CA72A5E33C";
 		int targetChunkNumber = 1;
 		
 		String newSerialNumber = "1965118191";
 		String newReporterVolume = "133";
 		String newReporterNumber = "595";
 		String newReporterPage = "687";
		String phrase = "TEST" + DateAndTimeUtils.getCurrentDateAndTimeyyyyMMddHHmmss();
		String wordPhraseXpath = EditorTextPageElements.NOD_PARATEXT_WORDPHRASE;

 		// open DS editor
		homePage().goToHomePage();
		loginPage().logIn();
		hierarchyNavigatePage().goToHierarchyPage(ContentSets.IOWA_DEVELOPMENT.getCode());
		hierarchySearchPage().searchNodeUuid(nodeUuid);
		siblingMetadataPage().selectedSiblingMetadataEditContent();
		
		// scroll to chunk
		editorPage().scrollToChunk(targetChunkNumber);
		
		// remember current info
    	String initialSerialNumber = editorPage().getHvsSerialNumber();
    	String initialReporterVolume = editorPage().getHvsReporterVolume();
    	String initialReporterNumber = editorPage().getHvsReporterNumber();
    	String initialReporterPage = editorPage().getHvsReporterPage();
    	String initialHeadnoteNumber = editorPage().getHvsHeadnoteNumber();

		// add word&phrase markup, verify that the markup is added for the selected text
		editorTextPage().insertNoteDecisionParagraphdAndHighlight(phrase);
		editorToolbarPage().clickWordsAndPhrases();
		editorTextPage().switchToEditorTextArea();
		DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
		boolean phraseGotWrappedAsWordPhrase = editorTextPage().doesElementExist(wordPhraseXpath) && editorTextPage().getElementsText(wordPhraseXpath).contains(phrase);

		//open HVS info
		editorTextPage().rightClick(EditorTextPageElements.hvsSerialNumber);
		boolean editHvsInformationWindowAppeared = editHvsInformationPage().switchToWindow(EditHVSInformationPageElements.PAGE_TITLE);
		Assertions.assertTrue(editHvsInformationWindowAppeared, "Edit HVSwindow should appear");
    	
    	// check fields
    	boolean initialSerialNumberMatches = editHvsInformationPage().getSerialNumberInput().equals(initialSerialNumber);
    	boolean initialReporterVolumeMatches = editHvsInformationPage().getReporterVolumeInput().equals(initialReporterVolume);
    	boolean initialReporterNumberMatches = editHvsInformationPage().getReporterNumberInput().equals(initialReporterNumber);
    	boolean initialReporterPageMatches = editHvsInformationPage().getReporterPageInput().equals(initialReporterPage);
    	boolean initialHeadnoteNumberMatches = editHvsInformationPage().getHeadnoteNumberInput().equals(initialHeadnoteNumber);
    	
    	//enter a different serial number and verify that the values change appropriately for that serial number
    	//Serial Number: 1965118191
    	//Headnote Number: 1 // We don't necessarily care about the Headnote Number, because Case Serial Numbers can have multiple Headnote Numbers
    	//Reporter Volume: 133
    	//Reporter Number: 595
    	//Reporter Page: 687
    	editHvsInformationPage().suggestNewSerialNumberForHVSInformation(newSerialNumber);

    	// check fields got populated with expected info
    	boolean serialNumberUpdatedInHVS = editHvsInformationPage().getSerialNumberInput().equals(newSerialNumber);
    	boolean reporterVolumeUpdatedInHVS = editHvsInformationPage().getReporterVolumeInput().equals(newReporterVolume);
    	boolean reporterNumberUpdatedInHVS = editHvsInformationPage().getReporterNumberInput().equals(newReporterNumber);
    	boolean reporterPageUpdatedInHVS = editHvsInformationPage().getReporterPageInput().equals(newReporterPage);
    	
    	// save and check that editor's content reflects changes made
    	editHvsInformationPage().clickSaveButton();
    	editorPage().switchDirectlyToTextFrame();

		//validations
    	boolean serialNumberUpdatedInEditor = editorPage().getHvsSerialNumber().equals(newSerialNumber);
    	boolean reporterVolumeUpdatedInEditor = editorPage().getHvsReporterVolume().equals(newReporterVolume);
    	boolean reporterNumberUpdatedInEditor = editorPage().getHvsReporterNumber().equals(newReporterNumber);
    	boolean reporterPageUpdatedInEditor = editorPage().getHvsReporterPage().equals(newReporterPage);

    	// close
    	editorPage().closeDocumentAndDiscardChanges();
    	
    	// asserts
    	Assertions.assertAll
		(
	    	() -> Assertions.assertTrue(initialSerialNumberMatches, "The serial number from the Edit HVS Info window matches the editor"),
	    	() -> Assertions.assertTrue(initialHeadnoteNumberMatches, "The headnote number from the Edit HVS Info window matches the editor"),
	    	() -> Assertions.assertTrue(initialReporterVolumeMatches, "The reporter volume number from the Edit HVS Info window matches the editor"),
	    	() -> Assertions.assertTrue(initialReporterNumberMatches, "The reporter number from the Edit HVS Info window matches the editor"),
	    	() -> Assertions.assertTrue(initialReporterPageMatches, "The reporter page from the Edit HVS Info window matches the editor"),
			() -> Assertions.assertTrue(phraseGotWrappedAsWordPhrase, "Added phrase should get wrapped with Wordphrase markup tag"),
	    	() -> Assertions.assertTrue(serialNumberUpdatedInHVS, "The serial number updated appropriately after swapping the value"),
	    	() -> Assertions.assertTrue(reporterVolumeUpdatedInHVS, "The reporter volume number updated appropriately after swapping the serial number"),
	    	() -> Assertions.assertTrue(reporterNumberUpdatedInHVS, "The reporter number updated appropriately after swapping the serial number"),
	    	() -> Assertions.assertTrue(reporterPageUpdatedInHVS, "The reporter page updated appropriately after swapping the serial number"),
	    	() -> Assertions.assertTrue(serialNumberUpdatedInEditor, "The serial number in editor updated appropriately after swapping the value"),
	    	() -> Assertions.assertTrue(reporterVolumeUpdatedInEditor, "The reporter volume number in editor updated appropriately after swapping the serial number"),
	    	() -> Assertions.assertTrue(reporterNumberUpdatedInEditor, "The reporter number in editor updated appropriately after swapping the serial number"),
	    	() -> Assertions.assertTrue(reporterPageUpdatedInEditor, "The reporter page in editor updated appropriately after swapping the serial number")
		);
    }
}

