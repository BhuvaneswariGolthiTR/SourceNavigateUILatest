package com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.target.set1;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class ConfigureEditorSessionPreferencesTargetLegalTests extends TestService
{
		/*Automate Source Tag Update Yes and No
	     * 1. Open document
	     * 1.5 Scroll down to a different chunk if available
	     * 1.75 Grab the Source Tag currently on a Text Paragraph (the green tag)
	     * 2. Click the Configure Editor Session Preferences button
	     * VERIFY: Editor preferences window appears
	     * 3. Select Yes for Automatic Source Tag Update
	     * 3.25 Grab the current Session default text source tag to reset the default later in the test
	     * 3.5 Select a Source Tag from the Session default text source tag dropdown that is not equal to the one currently on the Text Paragraph
	     * 4. Click Save
	     * VERIFY: Editor preferences window no longer appears
	     * 5. Insert text into a text paragraph
	     * VERIFY: The source tag updates to the one we selected in the dropdown
	     * VERIFY: modified by tag appears
	     * 6. Click the Configure Editor Session Preferences button
	     * VERIFY: Editor preferences window appears
	     * 7. Select No for Automatic Source Tag Update
	     * 7.5 Select a Source Tag from the Session default text source tag dropdown that is not equal to the one that the Text Paragraph updated to
	     * 8. Click Save
	     * VERIFY: Editor preferences window no longer appears
	     * 9. Insert text into a different text paragraph
	     * VERIFY: The source tag does not update to the one we selected
	     * VERIFY: modified by tag appears
	     *
	     * 10. Reset the automatic source tag update radio button to yes
	     * 11. Close and discard changes
	     *
	     */
	    
	    /* Session Default Source Tags
	     * Integrated this one with the automate source tag update when we first open
	     * the editor preferences to select yes and grab the source tag
	     * - Updated step 3.5 to select a different source tag (such as 17-A1)
	     */
		@Test
		@IE_EDGE_MODE
		@LEGAL
		@LOG
		public void automateSourceTagUpdateLegalTest()
		{
	 		String nodeUuid = "IDF3EB1C014F211DA8AC5CD53670E6B4E";
			String text = "Test";
			String firstTag = "17";
			String secondTag = "15";
			int targetChunkNumber = 1;
			String modifiedByTag = editorTextPage().getModifiedByTag(this.user());

			// open DS editor
			homePage().goToHomePage();
			loginPage().logIn();
			hierarchyNavigatePage().goToHierarchyPage(ContentSets.IOWA_DEVELOPMENT.getCode());
			hierarchySearchPage().searchNodeUuid(nodeUuid);
			siblingMetadataPage().selectedSiblingMetadataEditContent();
			
			//chunk 1 contains subsections necessary for this test
			editorPage().switchToEditorAndScrollToChunk(targetChunkNumber);
			
			//set label designator to yes
			editorPage().breakOutOfFrame();
			editorToolbarPage().clickConfigureEditorSessionPreferences();
			editorPreferencesPage().selectSessionDefaultTextSourceTagDropdownOption(firstTag);
			editorPreferencesPage().clickAutomaticSourceTagUpdateYesRadioButton();
			editorPreferencesPage().clickSaveButton();
			
			//Insert text into a text paragraph
			editorPage().switchToEditor();
			editorPage().switchDirectlyToTextFrame();
			editorTextPage().insertPhraseInTextParagraphWithGivenNumber(text, 1);
			editorPage().switchDirectlyToTextFrame();
			boolean modifiedByPopulatedCorrectlyInFirstTextParagraph = editorPage().checkForModifiedByTextInTextParagraphNumber(modifiedByTag, 1);
			String sourceTagOfFirstTextParagraphAfterChange = editorPage().getTextOfTextParagraphSourceTagUnderNumber(1);
			boolean firstTextParagraphSourceTagChanged = sourceTagOfFirstTextParagraphAfterChange.equals(firstTag);
			
			//set label designator to no
			editorPage().breakOutOfFrame();
			editorToolbarPage().clickConfigureEditorSessionPreferences();
			editorPreferencesPage().selectSessionDefaultTextSourceTagDropdownOption(secondTag);
			editorPreferencesPage().clickAutomaticSourceTagUpdateNoRadioButton();
			editorPreferencesPage().clickSaveButton();	

			//Insert text into the second text paragraph
			editorPage().switchToEditor();
			editorPage().switchDirectlyToTextFrame();
			editorTextPage().insertPhraseInTextParagraphWithGivenNumber(text, 2);
			editorPage().switchDirectlyToTextFrame();
			boolean modifiedByPopulatedCorrectlyInSecondTextParagraph = editorPage().checkForModifiedByTextInTextParagraphNumber(modifiedByTag, 2);
			String sourceTagOfSecondTextParagraphAfterChange = editorPage().getTextOfTextParagraphSourceTagUnderNumber(1);
			boolean secondTextParagraphSourceTagChanged = !sourceTagOfSecondTextParagraphAfterChange.equals(secondTag);

			//Reset the automatic source tag update radio button to yes
			editorPage().breakOutOfFrame();
			editorToolbarPage().clickConfigureEditorSessionPreferences();
			editorPreferencesPage().clickAutomaticSourceTagUpdateYesRadioButton();
			editorPreferencesPage().clickSaveButton();	
			editorPage().switchToEditor();
			
			// close editor
			editorPage().closeDocumentAndDiscardChanges();
			
			Assertions.assertAll
			(
				() -> Assertions.assertTrue(firstTextParagraphSourceTagChanged, "Source tag of first text paragraph should be changed"),
				() -> Assertions.assertTrue(modifiedByPopulatedCorrectlyInFirstTextParagraph, "Modified By tag in first text paragraph should contain right name and date"),
				() -> Assertions.assertTrue(secondTextParagraphSourceTagChanged, "Source tag of first text paragraph should not be changed"),
				() -> Assertions.assertTrue(modifiedByPopulatedCorrectlyInSecondTextParagraph, "Modified By tag in second text paragraph should contain right name and date")
			);
		}
	    
		 /* Display Label Designator Metadata Yes and No
	     * NOTE: There is an existing bug that is being worked on
	     * but the test can still be implemented and it will just fail until the bug is fixed
	     * 1. Open document that contains subsections
	     * 2. Click the Configure Editor Session Preferences button
	     * VERIFY: Editor preferences window appears
	     * 3. Select Yes for Label Designator Metadata Display for Subsections
	     * 4. Click Save
	     * VERIFY: Editor preferences window no longer appears
	     * VERIFY: Label designator metadata (the value markup you see after subsections) displays in both the tree and editor content area
	     * 5. Click the Configure Editor Session Preferences button
	     * VERIFY: Editor preferences window appears
	     * 6. Select No for Label Designator Metadata Display for Subsections
	     * 7. Click Save
	     * VERIFY: Editor preferences window no longer appears
	     * VERIFY: Label designator metadata (the value markup you see after subsections) does not display in both the tree and content area
	     *
	     * 8. Click the Configure Editor Session Preferences button
	     * VERIFY: Editor preferences window appears
	     * 9. Select Yes for Label Designator Metadata Display for Subsections
	     * 10. Click Save
	     * VERIFY: Editor preferences window no longer appears
	     * VERIFY: Label designator metadata (the value markup you see after subsections) displays in both the tree and editor content area
	     *
	     * 11. close and discard changes
	     */
		
		@Test
		@IE_EDGE_MODE
		@LEGAL
		@LOG
		public void displayLabelDesignatorMetadataLegalTest()
		{
	 		String nodeUuid = "IDF3EB1C014F211DA8AC5CD53670E6B4E";
	 		int targetChunkNumber = 1;
	 		
	 		// open DS editor
			homePage().goToHomePage();
			loginPage().logIn();
			hierarchyNavigatePage().goToHierarchyPage(ContentSets.IOWA_DEVELOPMENT.getCode());
			hierarchySearchPage().searchNodeUuid(nodeUuid);
			siblingMetadataPage().selectedSiblingMetadataEditContent();
			
			// chunk 1 contains subsections necessary for this test
			editorPage().switchToEditorAndScrollToChunk(targetChunkNumber);
			
			// set label designator to yes
			editorPage().breakOutOfFrame();
			editorToolbarPage().clickConfigureEditorSessionPreferences();
			editorPreferencesPage().clickShowSubsectionLabelDesignatorsYesRadioButton();
			editorPreferencesPage().clickSaveButton();
			
			// verify subsection labels appear
			editorPage().switchToEditor();
			editorPage().switchDirectlyToTextFrame();
			boolean subsectionLabelsDisplayed =// baseTestingService.isElementDisplayed(EditorTextPOM.LABEL_DESIGNATOR_RIGHT_ARROW_XPATH)
					//&& baseTestingService.isElementDisplayed(EditorTextPOM.LABEL_DESIGNATOR_LEFT_ARROW_XPATH) &&
					 !editorPage().getLabelDesignatorText().isEmpty();
			
			//set label designator to no
			editorPage().breakOutOfFrame();
			editorToolbarPage().clickConfigureEditorSessionPreferences();
			editorPreferencesPage().clickShowSubsectionLabelDesignatorsNoRadioButton();
			editorPreferencesPage().clickSaveButton();
			
			//verify subsection labels no longer appear
			editorPage().switchToEditor();
			editorPage().switchDirectlyToTextFrame();
			boolean subsectionLabelsNotDisplayed =// baseTestingService.isElementDisplayed(EditorTextPOM.LABEL_DESIGNATOR_RIGHT_ARROW_XPATH)
					//&& baseTestingService.isElementDisplayed(EditorTextPOM.LABEL_DESIGNATOR_LEFT_ARROW_XPATH)&& 
					editorPage().getLabelDesignatorText().isEmpty();
			
			//set label designator to yes
			editorPage().breakOutOfFrame();
			editorToolbarPage().clickConfigureEditorSessionPreferences();
			editorPreferencesPage().clickShowSubsectionLabelDesignatorsYesRadioButton();
			editorPreferencesPage().clickSaveButton();
					
			//verify subsection labels appear
			editorPage().switchToEditor();
			editorPage().switchDirectlyToTextFrame();
			boolean subsectionLabelsDisplayed1 =// baseTestingService.isElementDisplayed(EditorTextPOM.LABEL_DESIGNATOR_RIGHT_ARROW_XPATH)
					//&& baseTestingService.isElementDisplayed(EditorTextPOM.LABEL_DESIGNATOR_LEFT_ARROW_XPATH) &&
					 !editorPage().getLabelDesignatorText().isEmpty();
					
			// close editor
			editorPage().closeDocumentWithNoChanges();
			
			Assertions.assertAll
			(
				() -> Assertions.assertTrue(subsectionLabelsDisplayed, "Subsection labels were not displayed correctly"),
				() -> Assertions.assertTrue(subsectionLabelsNotDisplayed, "Subsection labels were not displayed and shouldn't be"),
				() -> Assertions.assertTrue(subsectionLabelsDisplayed1, "Subsection labels were not displayed correctly1")
			);
		}
	    
	     /* Display full/long English Labels Yes and No
	     * NOTE: This test is going to insert a known element that has a different long/short english label
	     * 1. Open document
	     * 2. Scroll down 2 or 3 chunks
	     * 3. Select and right click a text paragraph here
	     * 4. Go to Insert Text (sibling) -> Italic Note -> Italic Note Wide Centered (wisc)
	     * VERIFY: This is inserted into the editor
	     * VERIFY: This has an english label of Italic Note if we do not have the yes radio button selected
	     * 5. Click the Configure Editor Session Preferences button
	     * VERIFY: Editor preferences window appears
	     * 6. Select Yes for English Labels Display full/long titles
	     * 7. Click Save
	     * VERIFY: Editor preferences window no longer appears
	     * VERIFY: The Italic Note english label is now Italic Note Wide Centered
	     * 8. Click the Configure Editor Session Preferences button
	     * VERIFY: Editor preferences window appears
	     * 9. Select No for English Labels Display full/long titles
	     * 10. Click Save
	     * VERIFY: Editor preferences window no longer appears
	     * VERIFY: The Italic Note Wide Centered english label is now Italic Note
	     *
	      * 11. Click the Configure Editor Session Preferences button
	     * VERIFY: Editor preferences window appears
	     * 12. Select Yes for English Labels Display full/long titles
	     * 13. Click Save
	     * VERIFY: Editor preferences window no longer appears
	     * VERIFY: The Italic Note english label is now Italic Note Wide Centered
	     *
	      * 14. close and discard changes
	     */
		
		/*
		 * This Test is currently Disabled due to a recurring bug, that Users won't fix because they do not use the functionality
		 */
		
		@Test
		@IE_EDGE_MODE
		@LEGAL
		@LOG
		@Disabled
		public void displayFullLongEnglishLabelsLegalTest()
		{
	 		String nodeUuid = "IDF3EB1C014F211DA8AC5CD53670E6B4E";
	 		int targetChunkNumber = 1;
	 				
	 		// open DS editor
			homePage().goToHomePage();
			loginPage().logIn();
			hierarchyNavigatePage().goToHierarchyPage(ContentSets.IOWA_DEVELOPMENT.getCode());
			hierarchySearchPage().searchNodeUuid(nodeUuid);
			siblingMetadataPage().selectedSiblingMetadataEditContent();
			
			// chunk 1 contains subsections necessary for this test
			editorPage().scrollToChunk(targetChunkNumber);
			
			String currentDisplayNameOfTextParagraph = editorPage().getTextParagraphDisplayNameUnderNumber(1);
			String currentFullDisplayNameOfTextParagraph = editorPage().getTextParagraphFullDisplayNameUnderNumber(1);
			
			System.out.println(currentDisplayNameOfTextParagraph);
			System.out.println(currentFullDisplayNameOfTextParagraph);

			editorPage().breakOutOfFrame();
			editorToolbarPage().clickConfigureEditorSessionPreferences();
			editorPreferencesPage().clickFullEnglishLabelsYesRadioButton();
			editorPreferencesPage().clickSaveButton();
	
			editorPage().switchToEditorAndScrollToChunk(targetChunkNumber);
			
			boolean displayNameAppearsToggleYes = editorPage().checkForCurrentDisplayName(currentDisplayNameOfTextParagraph);
			boolean fullDisplayNameAppearsToggleYes = editorPage().checkForCurrentFullDisplayName(currentFullDisplayNameOfTextParagraph);

			editorPage().breakOutOfFrame();
			editorToolbarPage().clickConfigureEditorSessionPreferences();
			editorPreferencesPage().clickFullEnglishLabelsNoRadioButton();
			editorPreferencesPage().clickSaveButton();
			
			editorPage().switchToEditorAndScrollToChunk(targetChunkNumber);
			
			boolean displayNameAppearsToggleNo = editorPage().checkForCurrentDisplayName(currentDisplayNameOfTextParagraph);
			boolean fullDisplayNameAppearsToggleNo = editorPage().checkForCurrentFullDisplayName(currentFullDisplayNameOfTextParagraph);
			
			editorPage().closeDocumentWithNoChanges();
			
			Assertions.assertAll
			(
				() -> Assertions.assertFalse(displayNameAppearsToggleYes, "Text Paragraph's display name appears after toggling to Yes"),
				() -> Assertions.assertTrue(fullDisplayNameAppearsToggleYes, "Text Paragraph's full display name appears after toggling to Yes"),
				() -> Assertions.assertFalse(displayNameAppearsToggleNo, "Text Paragraph's display name appears after toggling to No"),
				() -> Assertions.assertTrue(fullDisplayNameAppearsToggleNo, "Text Paragraph's full display name appears after toggling to No")	
			);
		}
	}
