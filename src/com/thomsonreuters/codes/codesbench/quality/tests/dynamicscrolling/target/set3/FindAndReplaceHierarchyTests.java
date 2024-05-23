package com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.target.set3;

import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.popups.FindInChunksPageElements;
import com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.base.AbstractFindAndReplaceTests;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.autoIT.AutoITUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.Markup;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.openqa.selenium.Keys;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.*;
import static org.assertj.core.api.Assertions.assertThat;

public class FindAndReplaceHierarchyTests extends AbstractFindAndReplaceTests
{
	private static final String NODE_UUID = "I09CFC99014F011DA8AC5CD53670E6B4E";

	@BeforeEach
	protected void openDocument(TestInfo testInfo)
	{
		if (!testInfo.getTags().contains("SkipBeforeEachTestMethod"))
		{
			homePage().goToHomePage();
			loginPage().logIn();
			hierarchyNavigatePage().goToHierarchyPage(ContentSets.IOWA_DEVELOPMENT.getCode());
			hierarchySearchPage().searchNodeUuidHandlingIncorrectStartDate(NODE_UUID);
			siblingMetadataPage().selectedSiblingMetadataEditContent();
			editorPage().switchToEditor();
			editorPage().closeEditorWithDiscardingChangesIfAppeared();
			hierarchyNavigatePage().switchToHierarchyEditPage();
			siblingMetadataPage().selectedSiblingMetadataEditContent();
			editorPage().switchToEditor();
		}
	}

	@AfterEach
	protected void closeDocument()
	{
		editorPage().closeEditorWithDiscardingChangesIfAppeared();
	}

	@Test
	@IE_EDGE_MODE
	@LEGAL
	@LOG
	@Tag("SkipBeforeEachTestMethod")
	public void findMetadataTest()
	{
		String iowaNodeUuidWithMetadata = "I313A36001B7E11DC938E99E1B8D9B2DF";

		//1. Sign on as a legal user
		//2. Go to hierarchy navigate
		hierarchyNavigatePage().goToHierarchyPage(ContentSets.IOWA_DEVELOPMENT.getCode());
		loginPage().logIn();
		hierarchyNavigatePage().handleCasIssueWithHierarchyPageOpenedAsStartPageIfOccurred(ContentSets.IOWA_DEVELOPMENT);

		//3. Search for Node UUID (Iowa): I313A36001B7E11DC938E99E1B8D9B2DF
		hierarchySearchPage().searchNodeUuid(iowaNodeUuidWithMetadata);

		//4. Right click the selected node and go to Edit Content
		siblingMetadataPage().selectedSiblingMetadataEditContent();
		editorPage().switchDirectlyToTextFrame();

		//5. Grab the 'Ser. No.', 'Vol. No.', 'Rept. No.', 'Pg. No., 'HN No.'
		List<String> hvsMetadata = new ArrayList<>();
		Arrays.asList(HVS_SERIAL_NUMBER, HVS_REPORTER_VOLUME, HVS_REPORTER_NUMBER, HVS_REPORTER_PAGE, HVS_HEADNOTE_NUMBER)
				.forEach(hvsTag -> hvsMetadata.add(editorTextPage().getElementsText(hvsTag)
						.replace(editorTextPage().getElementsText(hvsTag + "/span"), "").trim()));

		hvsMetadata.forEach(grabbedHvsMetadataValue -> {
			//6. Open the find functionality by pressing Ctrl + Alt + F
			editorPage().openFindInChunksDialog();

			//7. For each number grabbed from step 5 enter the number into the Find text field and press Find Next
			findInChunksPage().setFindTerm(grabbedHvsMetadataValue);
			findInChunksPage().clickFindButton();

			assertThatThereIsNoUnexpectedAlertAppeared(grabbedHvsMetadataValue);
			assertThatCorrectValueIsHighlighted(grabbedHvsMetadataValue);
		});

		//8. Grab 'Topic Heading' and 'Note of Decision Paragraph' metadata tags
		List<String> elementsMetadata = new ArrayList<>();
		Arrays.asList(CODES_HEAD, NOD_NOTE).forEach(element -> {
			elementsMetadata.add(editorTextPage().getElementsText(element + MD_MNEM_MNEMONIC));
			elementsMetadata.addAll(editorTextPage().getElementsTextList(element + ANY_PUBTAG_IN_METADATA_BLOCK));
			elementsMetadata.add(editorTextPage().getElementsText(element + SOURCE_TAG));
		});

		elementsMetadata.forEach(grabbedElementsMetadataValue -> {
			//9. Open the find functionality by pressing Ctrl + Alt + F
			editorPage().openFindInChunksDialog();

			//10. For each value grabbed from step 8 enter the value into the Find text field and press Find Next
			findInChunksPage().setFindTerm(grabbedElementsMetadataValue);
			findInChunksPage().clickFindButton();

			assertThatThereIsNoUnexpectedAlertAppeared(grabbedElementsMetadataValue);
			assertThatCorrectValueIsHighlighted(grabbedElementsMetadataValue);
		});

		Map<String, Markup> phrasesWithMarkups = new HashMap<>();
		phrasesWithMarkups.put("Added", Markup.ADD);
		phrasesWithMarkups.put("Deleted", Markup.DELETE);
		phrasesWithMarkups.put("Words and phrases", Markup.WORDS_AND_PHRASES);

		//11. Insert phrases and apply markups
		editorTextPage().click(NOD_NOTE + PARA + SPAN);
		phrasesWithMarkups.keySet().forEach(phraseAsKey -> {
			editorTextPage().click(NOD_NOTE + PARA + SPAN);
			editorTextPage().waitForElement(PARA + CLASS_HIGHLIGHTED_POSTFIX);
			editorTextPage().sendKeys(Keys.ARROW_DOWN);
			editorTextPage().sendKeys(Keys.ARROW_DOWN);
			editorTextPage().waitForElementGone(PARA + CLASS_HIGHLIGHTED_POSTFIX);
			editorTextPage().sendKeys(Keys.HOME);
			editorTextPage().sendKeys(phraseAsKey);
			editorTextPage().highlightHelper(phraseAsKey);
			editorPage().switchToEditor();
			editorToolbarPage().clickInsertMarkup();
			insertWestMarkupPage().selectMarkupToBeInserted(phrasesWithMarkups.get(phraseAsKey).getDropdownOption());
			insertWestMarkupPage().clickSave();
			editorPage().switchToEditor();
			editorPage().switchToEditorTextFrame();
		});

		phrasesWithMarkups.keySet().forEach(phraseAsKey -> {
			//12. Open the find functionality by pressing Ctrl + Alt + F
			editorPage().openFindInChunksDialog();

			//13. For each phrase with applied markup enter the markup into the Find text field and press Find Next
			findInChunksPage().setFindTerm(phraseAsKey);
			editorTextPage().highlightHelper(phraseAsKey);
			findInChunksPage().clickAddMarkupButton();
			insertWestMarkupPage().selectMarkupToBeInserted(phrasesWithMarkups.get(phraseAsKey).getDropdownOption());
			insertWestMarkupPage().clickSave();
			insertWestMarkupPage().switchToWindow(FindInChunksPageElements.PAGE_TITLE);
			findInChunksPage().clickFindButton();

			assertThatThereIsNoUnexpectedAlertAppeared(phraseAsKey);
			assertThatCorrectValueIsHighlighted(phraseAsKey);
		});

		editorPage().switchToWindow(FindInChunksPageElements.PAGE_TITLE);
		findInChunksPage().closeCurrentWindowIgnoreDialogue();
	}

	// ---------- Assertions ----------

	private void assertThatThereIsNoUnexpectedAlertAppeared(String grabbedValue)
	{
		assertThat(AutoITUtils.verifyAlertTextAndCancel(false, String.format(COULDNT_FIND_S_MESSAGE, grabbedValue)))
				.as(PHRASE_S_WASNT_FOUND, grabbedValue)
				.isTrue();
	}

	private void assertThatCorrectValueIsHighlighted(String grabbedValue)
	{
		editorPage().switchDirectlyToTextFrame();
		assertThat(editorTextPage().getSelectedText())
				.as("%s should be selected after clicking find button", grabbedValue)
				.isEqualTo(grabbedValue);
	}
}
