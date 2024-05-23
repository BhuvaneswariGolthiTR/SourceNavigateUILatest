package com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.target.set2;

import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.popups.InsertSpecialCharacterPageElements;
import com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.base.AbstractSpecialCharacterLegalTests;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.RISK;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.BODY_TAG;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.CLASS_HIGHLIGHTED_POSTFIX;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.CORNERPIECE;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.CORNERPIECE_TEXT;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.EMBEDDED_HTML;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.FOLLOWING_SIBLING;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.PARA;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.PARA_SPAN;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.TEXT_PARAGRAPH_LABEL_UNDER_NUMBER;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.popups.TableEditorPageElements.FIRST_INPUT_FIELD;
import static com.thomsonreuters.codes.codesbench.quality.utilities.enums.SpecialCharacters.AMP;
import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;

public class SpecialCharacterLegalTests extends AbstractSpecialCharacterLegalTests
{
	private static final String NODE_UUID = "I2A8B5B9014F511DA8AC5CD53670E6B4E";

	@BeforeEach
	public void openNodeInDsEditor(TestInfo testInfo)
	{
		if (!(testInfo.getDisplayName().equals("unableToInsertSpecialCharactersFromTableEditorOutsideCellTest()")))
		{
			openNodeInDsEditor(ContentSets.IOWA_DEVELOPMENT, NODE_UUID);
		}
	}

	/**
	 * STORY/BUG - HALCYONST-11161 <br>
	 * SUMMARY - Replacing special characters updates Modified by tags (Target) <br>
	 * USER - LEGAL <br>
	 */
	@Test
	@IE_EDGE_MODE
	@LEGAL
	@LOG
	public void replacingSpecialCharactersUpdatesModifiedByTagsTargetLegalTest()
	{
		KEYS_AND_SPECIAL_CHARACTERS_MAP.keySet().forEach(key ->
		{
			//Assert that 'Modified by' tag does not exist before replacing special character
			assertThatModifiedByTagDoesNotExistInElement(CORNERPIECE);

			//Replace special character
			replaceSpecialCharacterInElementWithKey(CORNERPIECE_TEXT, key);

			//Assert that correct special character inserted after replacing
			assertThatExpectedSpecialCharacterIsAppearedInTheElement(
					KEYS_AND_SPECIAL_CHARACTERS_MAP.get(key), CORNERPIECE_TEXT);
			//Assert that 'Modified by' tag exists after replacing special character
			assertThatModifiedByTagExistsInElement(CORNERPIECE);

			undoChanges();
		});
	}

	@Test
	@IE_EDGE_MODE
	@LEGAL
	@LOG
	public void copyTextNextToSpecialCharacterTargetLegalTest()
	{
		String firstParagraph = format(TEXT_PARAGRAPH_LABEL_UNDER_NUMBER, 1) + format(FOLLOWING_SIBLING, "paratext");

		//1. Place cursor in a text paragraph
		placeCursorAtTheBeginningOfTextParagraph(1);

		//2. Click the insert special character button in the toolbar
		//3. Select 'amp' from the insert special character window
		editorPage().switchToEditor();
		editorToolbarPage().clickInsertSpecialCharacter();
		insertSpecialCharacterPage().sendTextInXmlEntity(AMP.getHtml());
		insertSpecialCharacterPage().clickInsert();
		editorPage().switchDirectlyToTextFrame();

		assertThatExpectedSpecialCharacterIsAppearedInTheElement(AMP, firstParagraph);

		//4. Select and copy some text from a different text paragraph
		placeCursorAtTheBeginningOfTextParagraph(2);
		editorTextPage().highlightHelper(30);
		editorTextPage().sendKeys(Keys.chord(Keys.CONTROL, "c"));

		//5. Go back to the paragraph you inserted the 'amp' special character into
		//6. Place cursor right before or right after the special character and paste the text here
		placeCursorAtTheBeginningOfTextParagraph(1);
		editorTextPage().sendKeys(Keys.chord(Keys.CONTROL, "v"));

		//Assert that the 'amp' special character is still appeared in the paragraph after text pasting
		assertThatExpectedSpecialCharacterIsAppearedInTheElement(AMP, firstParagraph);

		//Assert that the pasted text does not take on the green, bolded font that 'amp' has
		WebElement ampSpan = editorTextPage().getElement(firstParagraph + format("/span[@entity='%s']", AMP.getEntity()));
		assertThat(ampSpan.getCssValue("font-weight"))
				.as("The 'amp' should have bolded font with font-weight value equal to 700")
				.isEqualTo("700");
		assertThat(ampSpan.getCssValue("color"))
				.as("The 'amp' should have green font color")
				.isEqualTo("rgba(0, 128, 0, 1)");
		assertThat(editorTextPage().getElementsText(ampSpan))
				.as("The pasted text should not be placed in 'amp' element")
				.isEqualTo(AMP.getEntity());
	}

	@Test
	@IE_EDGE_MODE
	@RISK
	@LOG
	public void unableToInsertSpecialCharactersFromTableEditorOutsideCellTest()
	{
		String crownDependenciesUuid = "IA79E62A329A011E6A46DB8CA3AB007EB";

		//Search for node: "IA79E62A329A011E6A46DB8CA3AB007EB"
		//	This node has an existing table to work with. If it does not, you can insert one by clicking on the
		//	'Text Paragraph' English label, and then hit Alt+t to insert a table
		openNodeInDsEditor(ContentSets.CROWN_DEPENDENCIES, crownDependenciesUuid);

		//Insert an 'Embedded HTML'
		editorTextPage().click(PARA_SPAN);
		editorTextPage().waitForElement(PARA + CLASS_HIGHLIGHTED_POSTFIX);
		editorTextPage().addTableUsingAltT();

		//Hit Alt+Shift+t to edit this table
		editorTextPage().click(String.format("/%s/span", EMBEDDED_HTML));
		editorTextPage().editTableViaShiftAltT();

		//Assert that the 'Table Editor' window is appeared
		assertThat(tableEditorPage().switchToTableEditorPage())
				.as("'Table Editor' window should be appeared")
				.isTrue();
		tableEditorPage().enterTheInnerFrame();

		//Click outside one of the existing cells in the table
		//Click the 'Insert Special Character' button
		tableEditorPage().click(BODY_TAG);
		tableEditorPage().clickInsertSpecialCharacter();

		//Assert that the 'Insert Special Character' window is not appeared
		assertThat(tableEditorPage().doesWindowExistByTitle(InsertSpecialCharacterPageElements.PAGE_TITLE))
				.as("'Insert Special Character' window should not be appeared")
				.isFalse();

		//Click inside one of the existing cells in the table
		//Click the 'Insert Special Character' button
		tableEditorPage().switchToTableEditorPage();
		tableEditorPage().enterTheInnerFrame();
		tableEditorPage().click(FIRST_INPUT_FIELD);
		tableEditorPage().clickInsertSpecialCharacter();

		//Assert that the 'Insert Special Character' window is appeared
		assertThat(insertSpecialCharacterPage().switchToInsertSpecialCharacterWindow())
				.as("'Insert Special Character' window should be appeared")
				.isTrue();

		//Close 'Insert Special Character' window
		insertSpecialCharacterPage().closeCurrentWindowIgnoreDialogue();

		//Click 'Cancel' in the 'Table Editor' window
		tableEditorPage().switchToTableEditorPage();
		tableEditorPage().enterTheInnerFrame();
		tableEditorPage().clickCancel();
	}

//  ------------- Assistive methods: -------------

	private void placeCursorAtTheBeginningOfTextParagraph(int paragraphNumber)
	{
		editorTextPage().click(format(TEXT_PARAGRAPH_LABEL_UNDER_NUMBER, paragraphNumber));
		editorTextPage().waitForElement(PARA + CLASS_HIGHLIGHTED_POSTFIX);
		editorTextPage().sendKeys(Keys.ARROW_DOWN);
		editorTextPage().waitForElementGone(PARA + CLASS_HIGHLIGHTED_POSTFIX);
		editorTextPage().sendKeys(Keys.HOME);
	}

	private void openNodeInDsEditor(ContentSets contentSet, String nodeUuid)
	{
		hierarchyNavigatePage().goToHierarchyPage(contentSet.getCode());
		hierarchySearchPage().searchNodeUuid(nodeUuid);
		siblingMetadataPage().selectedSiblingMetadataEditContent();
		editorPage().switchDirectlyToTextFrame();
	}
}
