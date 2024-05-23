package com.thomsonreuters.codes.codesbench.quality.pages.editor;

import com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.editor.EditorTextContextMenuElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.CommonPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.popups.ContentEditorialSystemPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.popups.InsertTablePageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import com.thomsonreuters.codes.codesbench.quality.utilities.autoIT.AutoITUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.clipboard.ClipboardUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.SpecialCharacters;
import com.thomsonreuters.codes.codesbench.quality.utilities.robot.RobotUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.users.User;
import org.apache.commons.collections4.ListUtils;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.awt.event.KeyEvent;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorPageElements.firstTextParagraph;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.HIGHLIGHTED_PARA;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.TEXT_PARAGRAPH_WITH_NUMBER_PARA;

@Component
public class EditorTextPage extends BasePage
{
	private WebDriver driver;

	@Autowired
	public EditorTextPage(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
	}

	@PostConstruct
	public void init()
	{
		PageFactory.initElements(driver, EditorTextPageElements.class);
	}

	public void insertPhraseIntoFirstTextParagraph(String phrase)
	{
		insertPhraseUnderGivenLabel(phrase, EditorTextPageElements.PARA_SPAN);
	}

	public void insertPhraseUnderGivenLabel(String phrase, String xpath)
	{
		WebElement textParagraph = returnExistingElement(xpath);
		click(textParagraph);
		Actions insertText = new Actions(driver);
		insertText.sendKeys(Keys.ARROW_DOWN);
		insertText.sendKeys(Keys.HOME);
		insertText.sendKeys(phrase);
		insertText.build().perform();
	}

	public void insertNoteDecisionParagraphdAndHighlight(String phrase)
	{
		editorTextPage().click(EditorTextPageElements.NDP_TEXT);
		editorTextPage().sendKeys(Keys.HOME);
		editorTextPage().sendKeys(phrase);
		editorTextPage().highlightHelper(phrase);
		editorTextPage().breakOutOfFrame();
	}

	public void insertPhraseIntoTextParagraphByNumber(String phrase, String number)
	{
		WebElement textParagraph = getElement(String.format(EditorTextPageElements.TEXT_PARAGRAPH_WITH_NUMBER, number));
		click(textParagraph);
		Actions insertText = new Actions(driver);
		insertText.sendKeys(Keys.ARROW_DOWN);
		insertText.sendKeys(Keys.HOME);
		insertText.sendKeys(phrase);
		insertText.build().perform();
	}

	public void switchToEditorTextArea()
	{
		driver.switchTo().frame(driver.findElements(By.xpath("//iframe")).get(1));
	}

	public void sendHotKeyToCornerpieceText(String hotKey)
	{
		click(EditorTextPageElements.CORNERPIECE_TEXT);
		switch(hotKey)
		{
			case "Alt-Z":
				altZUsingAction();
				break;
			case "Shift-Alt-Z":
				click(EditorTextPageElements.CORNERPIECE_TEXT);
				shiftAltZUsingAction();
				break;
		}
	}

	public boolean isPlaceholderDisplayed()
	{
		return EditorTextPageElements.placeholder.isDisplayed();
	}

	public void rightClickFirstParagraph()
	{
		waitForElementToBeClickable(EditorTextPageElements.firstTextParagraph);
		click(EditorTextPageElements.firstTextParagraph);
		rightClick(EditorTextPageElements.firstTextParagraph);
		breakOutOfFrame();
	}

	public boolean containsResolvedQueryNote()
	{
		return doesElementExist(EditorTextPageElements.RESOLVED_QUERY_NOTE_XPATH);
	}

	public boolean checkForExistanceOfPubtag()
	{
		return isElementDisplayed(EditorTextPageElements.ANY_PUBTAG_IN_METADATA_BLOCK);
	}

	public boolean checkForExistanceOfSourceTag()
	{
		return isElementDisplayed(EditorTextPageElements.SOURCE_TAG);
	}

	public int countCornerpieceElements()
	{
		return countElements(EditorTextPageElements.SECTION + EditorTextPageElements.CORNERPIECE);
	}

	public int countPlaceholderElements()
	{
		return countElements(EditorTextPageElements.SECTION + EditorTextPageElements.PLACEHOLDER);
	}

	public void clickCornerpiece()
	{
		click(EditorTextPageElements.CORNERPIECE_SPAN);
		waitForEditorElementSelected(EditorTextPageElements.CORNERPIECE_SPAN);
	}

	public void rightClickCornerpiece()
	{
		scrollToView(EditorTextPageElements.CORNERPIECE_SPAN);
		rightClick(EditorTextPageElements.CORNERPIECE_SPAN);
	}

	public void insertSpaceAndRemoveSpaceInEndOfTextBlock()
	{
		click(EditorTextPageElements.END_OF_TEXT_BLOCK_XPATH);
		sendKeys(Keys.HOME);
		sendKeys(" ");
		sendKeys(Keys.BACK_SPACE);
	}

	public String getModifiedByXpath(User user)
	{
		return String.format(
			EditorTextPageElements.MODIFIED_BY_MNEMONIC_WITH_TEXT,
			user.getEditorModifiedByUsername() + DateAndTimeUtils.getCurrentDateMMddyyyy());
	}

	public String getImportedModifiedByXpath(User user)
	{
		return String.format(
				EditorTextPageElements.IMPORTED_MODIFIED_BY_MNEMONIC_WITH_TEXT,
				user.getEditorModifiedByUsername() + DateAndTimeUtils.getCurrentDateMMddyyyy());
	}

	public String getModifiedByTag(User user)
	{
		String modifiedByTagData = user.getEditorModifiedByUsername() + DateAndTimeUtils.getCurrentDateMMddyyyy();
		return String.format("Modified by%s", modifiedByTagData);
	}

	public String getUserNameAndCurrentData(User user)
	{
		return user.getEditorModifiedByUsername() + DateAndTimeUtils.getCurrentDateMMddyyyy();
	}

	public int countTextParagraphElements()
	{
		return countElements(EditorTextPageElements.TEXT_PARAGRAPH_LABEL);
	}

	public int countOfHighLightedParagraphs()
	{
		return countElements(HIGHLIGHTED_PARA);
	}

	public String getHighlightedParaText()
	{
		return getElementsText(HIGHLIGHTED_PARA + EditorTextPageElements.PARATEXT);
	}

	public String getHighlightedCornerpieceText()
	{
		return getElementsText(EditorTextPageElements.HIGHLIGHTED_CORNERPIECE_TEXT);
	}

	public void clickOnGivenParagrpaphsText(WebElement givenParagraph)
	{
		givenParagraph.findElement(By.xpath(EditorTextPageElements.CHILD_PARATEXT)).click();
	}

	public void typeTextIntoGivenParagraph(String element, String phrase) {
		DateAndTimeUtils.takeNap(DateAndTimeUtils.THREE_SECONDS);
		Actions action = new Actions(driver);
		action.sendKeys(Keys.HOME);
		action.sendKeys(phrase).build().perform();
	}
	public void typeTextIntoGivenParagraphassibiling(String element, String phrase) {
		click(element);
		DateAndTimeUtils.takeNap(DateAndTimeUtils.THREE_SECONDS);
		Actions action = new Actions(driver);
		action.sendKeys(phrase).build().perform();
	}
	public boolean sectionXExists(int numX)
	{
		return doesElementExist(String.format(EditorTextPageElements.SECTION_BY_NUMBER,numX));
	}
	public boolean sectionXHasTreatment(int numX)
	{
		return doesElementExist(String.format(EditorTextPageElements.SECTION_BY_NUMBER,numX) + EditorTextPageElements.SECTION_HAS_TREATMENT);
	}
	public boolean sectionXHasReference(int numX)
	{
		return doesElementExist(String.format(EditorTextPageElements.SECTION_BY_NUMBER,numX) + EditorTextPageElements.SECTION_HAS_REFERENCE);
	}
	public WebElement getValidCite(int section, int cite)
	{
		WebElement elementExists = getElement(String.format(EditorTextPageElements.SECTION_INTRO_PARA_TEXT_XPATH
				+ EditorTextPageElements.SECTION_HAS_REFERENCE + "[%s]//img",section,cite));
		return elementExists;
	}
	public boolean doesValidCiteExist(int section, int cite) {
		boolean elementExists = doesElementExist(String.format(EditorTextPageElements.SECTION_INTRO_PARA_TEXT_XPATH
				+ EditorTextPageElements.SECTION_HAS_REFERENCE + "[%s]//img", section, cite));
		return elementExists;
	}
	public WebElement getValidTreatment(int section, int treatment)
	{
		WebElement elementExists = getElement(String.format(EditorTextPageElements.SECTION_INTRO_PARA_TEXT_XPATH
				+ EditorTextPageElements.SECTION_HAS_TREATMENT + "[%s]//img",section,treatment));
		return elementExists;
	}
	public boolean doesValidTreatmentExist(int section, int treatment)
	{
		boolean elementExists = doesElementExist(String.format(EditorTextPageElements.SECTION_INTRO_PARA_TEXT_XPATH
				+ EditorTextPageElements.SECTION_HAS_TREATMENT + "[%s]//img",section,treatment));
		return elementExists;
	}
	public boolean checkCreditSectionForSectionNumbers(String sectionToCheck, String sectionNumbers)
	{
		return getElement(String.format(EditorTextPageElements.CREDIT_SECTION, sectionToCheck)).getText().contains(sectionNumbers);
	}
	public boolean checkCreditSectionForFlag(String sectionToCheck, String sectionFlag)
	{
		return getElement(String.format(EditorTextPageElements.TEXT_MERGE_FLAG, sectionToCheck)).getText().contains(sectionFlag);
	}
	public void typeTextIntoGivenParagraph(WebElement givenParagraph, String phrase) throws InterruptedException {
		clickOnGivenParagrpaphsText(givenParagraph);
		Thread.sleep(3000);
		Actions action = new Actions(driver);
		action.sendKeys(phrase).build().perform();
	}

	public void insertCopiedText(String element)
	{
		editorPage().click(element);
		Actions pasteAction = new Actions(driver);
		pasteAction.keyDown(Keys.CONTROL).sendKeys("v").build().perform();
	}

	public String copyXmlThroughCtrlShiftC(String xpath)
	{
		click(xpath);
		getElement(xpath);
		new Actions(driver).keyDown(Keys.CONTROL).keyDown(Keys.SHIFT).sendKeys("c").build().perform();
		return ClipboardUtils.getSystemClipboard();
	}

	public String copyTextThroughShiftAltC(String xpath)
	{
		click(xpath);
		getElement(xpath);
		new Actions(driver).keyDown(Keys.ALT).keyDown(Keys.SHIFT).sendKeys("c").build().perform();
		String text = Objects.requireNonNull(ClipboardUtils.getSystemClipboard()).replace("\n", "");
		return SpecialCharacters.convertHtmlCodesToCharacters(text);
	}

	public void cutContent()
	{
		new Actions(driver).keyDown(Keys.CONTROL).sendKeys("x").build().perform();
	}

	public void pasteContent()
	{
		new Actions(driver).keyDown(Keys.CONTROL).sendKeys("v").build().perform();
	}

	public void copyContent()
	{
		new Actions(driver).keyDown(Keys.CONTROL).sendKeys("c").build().perform();
	}

	public void insertPhrasesWithRespectiveMarkup(String targetSpan, String[] phrases, String[] markups, int index)
	{
		for (int i = 0; i < phrases.length; i++)
		{
			// insert and highlight the term
			insertPhraseUnderGivenLabelWithHome(phrases[i], targetSpan);
			highlightHelper(phrases[i]);
			editorToolbarPage().clickInsertMarkup();
			// add the markup associated to the term inserted
			insertWestMarkupPage().selectMarkupToBeInserted(markups[i]);
			insertWestMarkupPage().clickSave();
			editorPage().switchToEditor();
			editorPage().switchToEditorTextFrameByIndex(index);
		}
	}

	/*
	 * NOTE: This only selects the first line of text in the paragraph. This also
	 * returns the full text of the paragraph
	 */
	public String selectParagraphText()
	{
		String text = getElementsText("//para//paratext");
		click("//para//paratext");
		Actions act = new Actions(driver);
		act.sendKeys(Keys.HOME).keyDown(Keys.SHIFT).sendKeys(Keys.END).build().perform();
		return text;
	}

	public void replaceCornerpieceText(String replaceWith)
	{
		click("//cornerpiece//cornerpiece.text");
		AutoITUtils.sendHotKey("a", Keys.CONTROL);
		AutoITUtils.sendHotKey(Keys.DELETE);
		editorTextPage().sendKeys(replaceWith);
	}

	public void insertPhraseAndHighlight(String phrase)
	{
		insertPhraseUnderGivenLabelWithHome(phrase, EditorTextPageElements.PARA_SPAN);
		highlightHelper(phrase);
	}

	public void insertPhraseInFirstTextParagraphOfChunkAndHighlight(String phrase, int chunkNumber)
	{
		insertPhraseUnderGivenLabelWithHome(phrase,
						    String.format(EditorTextPageElements.LOADED_CHUNK, chunkNumber - 1)
						    + EditorTextPageElements.FIRST_TEXT_PARAGRAPH);
		highlightHelper(phrase);
	}

	public void insertPhraseUnderGivenLabelWithHome(String phrase, String xpath)
	{
		insertPhraseWithKeys(phrase, xpath, Keys.HOME);
	}

	public void insertPhraseUnderGivenLabelWithEnd(String phrase, String xpath)
	{
		insertPhraseWithKeys(phrase, xpath, Keys.END);
	}

	private void insertPhraseWithKeys(String phrase, String xpath, Keys keys)
	{
		WebElement textLabel = getElement(xpath);
		scrollToView(textLabel);
		click(textLabel);
		DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
		Actions englishLabel = new Actions(driver);
		englishLabel.sendKeys(keys.ARROW_DOWN).sendKeys(keys).build().perform();
		//We change the two lines below to actions object above to bypass the selenium focusable error
		//textLabel.sendKeys(Keys.ARROW_DOWN);
		//textLabel.sendKeys(keys);
		// insert the phrase as well as a space at the end of it and then move the
		// cursor left one
		Actions cursor = new Actions(driver);
		sendKeys(phrase);
		cursor.sendKeys(Keys.SPACE).sendKeys(Keys.LEFT);
		Action action = cursor.build();
		action.perform();
	}

	public void insertPhraseUnderParaInChunk(String phrase, int paraNumber, int chunkNumber)
	{
		editorPage().switchToEditorAndScrollToChunk(chunkNumber);
		insertPhraseUnderGivenLabelWithHome(phrase, String.format(EditorTextPageElements.TEXT_PARAGRAPH_FROM_CHUNK,
				chunkNumber-1, paraNumber) + EditorTextPageElements.SPAN);
	}

	public void insertPhrase(String phrase)
	{
		insertPhraseUnderGivenLabelWithHome(phrase, EditorTextPageElements.PARA_SPAN);
	}

	public void insertPhraseAtEndOfNODParagraph(String phrase)
	{
		WebElement nodPara = getElement(EditorTextPageElements.NOD_PARA_XPATH);
		nodPara.click();
		Actions insertText = new Actions(driver);
		insertText.keyDown(Keys.CONTROL);
		insertText.sendKeys(Keys.ARROW_DOWN).pause(Duration.ofSeconds(2)).build().perform();
		insertText.keyUp(Keys.CONTROL);
		insertText.sendKeys(Keys.ARROW_LEFT);
		insertText.sendKeys(phrase + " ");
		insertText.build().perform();
	}


	public void insertPhraseIntoFirstTextParagraphWithSpace(String phrase)
	{
	    click(firstTextParagraph);
		Actions insertText = new Actions(driver);
		insertText.sendKeys(Keys.ARROW_DOWN);
		insertText.sendKeys(Keys.HOME);
		insertText.sendKeys(phrase + " ");
		insertText.build().perform();
	}

//	public void insertSpaceAndRemoveSpace()
//	{
//		click(firstTextParagraph);
//		firstTextParagraph.sendKeys(Keys.ARROW_DOWN);
//		firstTextParagraph.sendKeys(Keys.HOME);
//		firstTextParagraph.sendKeys(" ");
//		firstTextParagraph.sendKeys(Keys.BACK_SPACE);
//	}

	public void insertSpaceAndRemoveSpace()
	{
		click(firstTextParagraph);
		sendKeys(Keys.ARROW_DOWN);
		sendKeys(Keys.HOME);
		sendKeys(" ");
		sendKeys(Keys.BACK_SPACE);
	}

	public void insertPhraseInTextParagraphWithGivenNumber(String phrase, int textParagraphNumber)
	{
		WebElement textParagraph = getElement(String.format(TEXT_PARAGRAPH_WITH_NUMBER_PARA, textParagraphNumber));
		click(textParagraph);
		sendKeys(Keys.ARROW_DOWN);
		sendKeys(Keys.HOME);
		sendKeys(phrase + " ");
	}

	public void insertPhraseInSubsectionTextParagraphWithGivenNumber(String phrase, int paragraphNumber)
	{
		WebElement textParagraph = getElement(String.format(EditorTextPageElements.SUBSECTION_PARA_UNDER_NUMBER, paragraphNumber));
		click(textParagraph);
//		sendKeys(Keys.ARROW_DOWN);
//		sendKeys(Keys.HOME);
//		sendKeys(phrase + " ");
		Actions insertText = new Actions(driver);
		insertText.sendKeys(Keys.ARROW_DOWN);
		insertText.sendKeys(Keys.HOME);
		insertText.sendKeys(phrase + " ");
		insertText.build().perform();
	}

	public void insertPhraseInTheEndOfLine(String phrase, String xpath) {
		WebElement label = getElement(xpath);
		label.click();
		editorTextPage().sendKeys(Keys.END);
		editorTextPage().sendKeys(Keys.SPACE);
		editorTextPage().sendKeys(phrase + " ");
	}

	/**
	 * This method performs highlighting of specified phrase from right to left
	 *
	 * @param phrase phrase which should be highlighted
	 */
	public void highlightHelper(String phrase)
	{
		Actions highlighter = new Actions(driver);
		highlighter.keyDown(Keys.SHIFT);

		// After a phrase usually a space is inserted
		for (int i = 0; i < phrase.length(); i++)
		{
			highlighter.sendKeys(Keys.LEFT);
		}

		highlighter.keyUp(Keys.SHIFT);
		Action action = highlighter.build();
		action.perform();
	}

	/**
	 * This method performs highlighting with specified length from left to right
	 *
	 * @param length how many characters should be highlighted
	 */
	public void highlightHelper(int length)
	{
		Actions highlighter = new Actions(driver);
		highlighter.keyDown(Keys.SHIFT);

		for (int i = 0; i < length; i++)
		{
			highlighter.sendKeys(Keys.RIGHT);
		}

		highlighter.keyUp(Keys.SHIFT);
		Action action = highlighter.build();
		action.perform();
	}

	public void highlightHelperUsingRobot(int length)
	{
		try
		{
			RobotUtils.getRobot().keyPress(KeyEvent.VK_SHIFT);
			for (int i = 0; i < length; i++)
			{
				RobotUtils.getRobot().keyPress(KeyEvent.VK_RIGHT);
				RobotUtils.getRobot().keyRelease(KeyEvent.VK_RIGHT);
			}
		}
		finally
		{
			RobotUtils.getRobot().keyRelease(KeyEvent.VK_SHIFT);
			RobotUtils.getRobot().keyRelease(KeyEvent.VK_RIGHT);
		}
	}

	public void createTable(String columnAmount)
	{
		switchToWindow("Insert Table");
		enterTheInnerFrame();
		getElement(CommonPageElements.VALUE_INPUT).sendKeys(columnAmount);
		sendEnterToElement(CommonPageElements.SAVE_BUTTON);
		editorPage().switchDirectlyToTextFrame();
	}

	public void deletePhraseInTheBeginningOfTextParagraph(String phrase, String labelXpath)
	{
		WebElement textLabel = getElement(labelXpath);
		click(textLabel);
		DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
		Actions cursor= new Actions(driver);
		cursor.sendKeys(Keys.ARROW_DOWN).pause(Duration.ofSeconds(2)).sendKeys(Keys.HOME).pause(Duration.ofSeconds(2)).build().perform();

		for (int i = 0; i < (phrase.length()); i++)
		{
			cursor.sendKeys(Keys.DELETE).build().perform();
		}
		//cursor.sendKeys(Keys.BACK_SPACE);
	}

	public void addTextToGivenCiteReference(String citeReference,String addedText)
	{
		click(String.format(EditorTextPageElements.CITE_REFERENCE_TEXT,citeReference));
		sendKeys(addedText);
	}

	public void removeTextFromGivenCiteReference(String citeReference,String removedText)
	{
		click(String.format(EditorTextPageElements.CITE_REFERENCE_TEXT,citeReference));
		if(removedText.length()%2==0)
		{
			for (int i = 0; i < removedText.length()/2; i++) {
					sendKeys(Keys.BACK_SPACE);
			}
			for (int i = removedText.length()/2; i < removedText.length(); i++) {
				sendKeys(Keys.DELETE);
			}
		}
	}

	public void deleteFirstCharacterOfFirstTextParagraph()
	{
		click(EditorTextPageElements.FIRST_TEXT_PARAGRAPH);
		DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
		editorTextPage().sendKeys(Keys.ARROW_DOWN);
		DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
		editorTextPage().sendKeys(Keys.HOME);
		DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
		editorTextPage().sendKeys(Keys.ARROW_RIGHT);
		DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
		editorTextPage().sendKeys(Keys.DELETE);
	}

	public String getRefTypeOfGivenCiteReference(String citeReferenceText)
	{
		return getElement(String.format(EditorTextPageElements.CITE_REFERENCE_TEXT,citeReferenceText)).getAttribute("w-ref-type");
	}

	public String getManualEditAttributeOfGivenCiteReference(String citeReferenceText)
	{
		return getElement(String.format(EditorTextPageElements.CITE_REFERENCE_TEXT,citeReferenceText)).getAttribute("manual-edit");
	}

	public String getPinpointPageAttributeOfGivenCiteReference(String citeReferenceText)
	{
		return getElement(String.format(EditorTextPageElements.CITE_REFERENCE_TEXT,citeReferenceText)).getAttribute("w-pinpoint-page");
	}

	public void deleteAllTextFromFirstTextParagraph()
	{
		EditorTextPageElements.paraText.clear();
	}

	public String getCornerPieceText()
	{
		return getElementsText(EditorTextPageElements.CORNERPIECE_TEXT);
	}

	public void addTextInsideValueLabel(String textToInsert)
	{
		click(EditorTextPageElements.LABEL_DESIGNATOR);
		sendKeys(textToInsert);
	}

	public boolean isGivenTextInsideValueLabel(String textToCheck)
	{
		return getElement(EditorTextPageElements.LABEL_DESIGNATOR).getText().contains(textToCheck);
	}

	public void insertAndRemoveSpaceInVolumeNumberText()
	{
		click(EditorTextPageElements.VOLUME_NUMBER_SPAN);
		sendKeys(Keys.HOME);
		sendKeys(" ");
		sendKeys(Keys.BACK_SPACE);
	}

	public void addTextToSelectedParagraph(String textToInsert)
	{
		sendKeys(textToInsert);
	}

	public boolean doesTextBlockHaveTextParagraphAndImage()
	{
		return doesElementExist(EditorTextPageElements.TEXT_PARAGRAPH_IMAGE);
	}

	public boolean doesFirstParagraphWithParaTextContainGivenText(String textToCheck)
	{
		return getElement(EditorTextPageElements.PARATEXT).getText().contains(textToCheck);
	}

	public boolean doesFirstSubsectionParagraphWithParaTextContainGivenText(String textToCheck, int paragaphNumber)
	{
		return getElement(String.format(EditorTextPageElements.SUBSECTION_PARA_UNDER_NUMBER, paragaphNumber)).getText().contains(textToCheck);
	}

	public boolean doesFirstTextParagraphContainGivenText(String textToCheck)
	{
		return getElement(EditorTextPageElements.TEXT_PARAGRAPH_PARA).getText().contains(textToCheck);
	}

	public boolean doesFirstParagraphWithAnalysisTextContainGivenText(String textToCheck)
	{
		return getElement(EditorTextPageElements.ANALYSIS_TEXT).getText().contains(textToCheck);
	}

	public void insertTextToAnalysisParagraph(String textToInsert)
	{
		click(EditorTextPageElements.ANALYSIS_TEXT);
		sendKeys(textToInsert);
	}

	public void insertTextInsideHeadingTextLabel(String textToInsert)
	{
		click(EditorTextPageElements.HEADTEXT);
		sendKeys(textToInsert);
	}

	public boolean doesHeadingTextContainGivenText(String textToCheck)
	{
		return getElement(EditorTextPageElements.HEADTEXT).getText().contains(textToCheck);
	}

	public void insertTextIntoFirstTextParagraphWithoutSpaces(String textToInsert)
	{
		click(EditorTextPageElements.FIRST_TEXT_PARAGRAPH);
		editorTextPage().sendKeys(Keys.ARROW_DOWN);
		editorTextPage().sendKeys(Keys.HOME);
		editorTextPage().sendKeys(textToInsert);
	}


	public boolean rightClickMnemonic(String xpath)
	{
		scrollToView(xpath);
		click(xpath);
		rightClick(xpath);
		boolean windowAppeared = switchToWindow(ContentEditorialSystemPageElements.PAGE_TITLE);
		enterTheInnerFrame();
		return windowAppeared;
	}

	public void addTableViaAltT()
	{
		DateAndTimeUtils.takeNap(DateAndTimeUtils.THREE_SECONDS);
		new Actions(driver).keyDown(Keys.ALT).sendKeys("t").build().perform();
		switchToWindow(InsertTablePageElements.PAGE_TITLE);
		enterTheInnerFrame();
	}

	public void rightClickFirstTextParagraphInTheChunk(int targetChunkNumber)
	{
		scrollToView(String.format("//div[@id='loaded_chunk_%d']//para/span[contains(text(), 'Text Paragraph')]", targetChunkNumber-1));
		rightClick(String.format("//div[@id='loaded_chunk_%d']//para/span[contains(text(), 'Text Paragraph')]", targetChunkNumber-1));
	}

	public void rightClickFirstCornerpieceInTheChunk(int targetChunkNumber)
	{
		scrollToView(String.format("//div[@id='loaded_chunk_%d']//cornerpiece/span[contains(text(), 'Cornerpiece')]", targetChunkNumber-1));
		rightClick(String.format("//div[@id='loaded_chunk_%d']//cornerpiece/span[contains(text(), 'Cornerpiece')]", targetChunkNumber-1));
	}

	public void rightClickFirstSourceNoteParagraphInTheChunk(int targetChunkNumber)
	{
		rightClick(String.format("//div[@id='loaded_chunk_%d']//para/span[contains(text(),'Source Note Paragraph')]", targetChunkNumber-1));
	}

	public void rightClickFirstSubsectionInTheChunk(int targetChunkNumber)
	{
		waitForElementExists(
				String.format("//div[@id='loaded_chunk_%d']//subsection/span[contains(text(),'Subsection')]", targetChunkNumber-1));
		rightClick(String.format("//div[@id='loaded_chunk_%d']//subsection/span[contains(text(),'Subsection')]", targetChunkNumber-1));
	}

	public String getFirstTextParagraphTextInTheChunk(int targetChunkNumber)
	{
		return getElementsText(String.format("//div[@id='loaded_chunk_%d']//para[span[contains(text(), 'Text Paragraph')]]/paratext", targetChunkNumber-1));
	}

	public String getFirstCornerpieceTextInTheChunk(int targetChunkNumber)
	{
		return getElementsText(String.format("//div[@id='loaded_chunk_%d']//cornerpiece[span[contains(text(), 'Cornerpiece')]]/cornerpiece.text", targetChunkNumber-1));
	}

	public String getTheFirstParaTextInTheChunk(int targetChunkNumber)
	{
		return getElementsText(String.format("//div[@id='loaded_chunk_%d']//para/paratext", targetChunkNumber-1));
	}

	public String getTheFirstParaModifiedByTagInTheChunk(int targetChunkNumber)
	{
		return getElementsText(String.format("//div[@id='loaded_chunk_%d']//para/metadata.block/modified.by", targetChunkNumber-1));
	}

	public String getFirstSourceNoteParagraphInTheChunk(int targetChunkNumber)
	{
		return getElementsText(String.format("//div[@id='loaded_chunk_%d']//para[span[contains(text(),'Source Note Paragraph')]]/paratext/cite.query", targetChunkNumber-1));
	}

	public String getSecondSubsectionLabelInTheChunk(int targetChunkNumber)
	{
		return getElementsText(String.format("//div[@id='loaded_chunk_%d']//body/subsection[2]//md.label.designator", targetChunkNumber-1));
	}

	public int countSubsectionInTheChunk(int targetChunkNumber)
	{
		return countElements(String.format("//div[@id='loaded_chunk_%d']//subsection/span[contains(text(),'Subsection')]", targetChunkNumber-1));
	}

	public String getTextFromTextParagraphStartingWithTextGiven(String textGiven)
	{
		return getElementsText(String.format(EditorTextPageElements.TEXT_PARAGRAPH_STARTING_WITH_TEXT_GIVEN,textGiven));
	}

	public void clickDeltaAmendLabel(String level)
	{
		click(String.format(EditorTextPageElements.DELTA_AMEND_LABEL,level));
	}

	public void rightClickDeltaAmendLabel(String level)
	{
		rightClick(String.format(EditorTextPageElements.DELTA_AMEND_LABEL,level));
	}

	public String getTargetLocationSection()
	{
		return getElementsText(EditorTextPageElements.TARGET_LOCATION_SECTION);
	}

	public String getTargetLocationSubsection()
	{
		return getElementsText(EditorTextPageElements.TARGET_LOCATION_SUBSECTION);
	}

	public int countAnnotationsNumber()
	{
		return countElements("//annotations/span[contains(text(),'Annotations')]");
	}

	public int countTextBody()
	{
		return countElements("//span[contains(text(),'Text')]");
	}

	public String getTaxTypeAddValue(String node)
	{
		return getElementsText(String.format(EditorTextPageElements.HIGHLIGHTED_TAX_TYPE_ADD_ENGLISH_LABEL_VALUE,node));
	}

	public List<String> getPubtagsListOfTextParagraphFromChunk(int chunk, int paragraph)
	{
		return getElements(String.format(EditorTextPageElements.TEXT_PARAGRAPH_FROM_CHUNK, chunk - 1, paragraph)
				+ EditorTextPageElements.ANY_PUBTAG_IN_METADATA_BLOCK).stream().map(WebElement::getText)
				.collect(Collectors.toList());
	}

	public boolean doesParagraphContainAnyOfGivenPubtags(int chunk, int paragraph, List<String> pubtags)
	{
		for (String pubtag : pubtags)
		{
			if(doesElementExist(
					String.format(EditorTextPageElements.TEXT_PARAGRAPH_FROM_CHUNK, chunk - 1, paragraph)
							+ "//md.pub.tag.info"
							+ String.format(EditorTextPageElements.PUBTAG_AS_CONDITION_XPATH_PART, pubtag)))
				return true;
		}
		return false;
	}

	public List<String> grabMnemonicsFromChunks(int firstChunk, int lastChunk)
	{
		List<String> mnemonicsList = new ArrayList<>();
		List<String> mnemonicsListOfCurrentChunk;

		for (int currentChunkNumber = firstChunk-1; currentChunkNumber <= lastChunk-1; currentChunkNumber++)
		{
			editorPage().scrollToChunk(currentChunkNumber + 1);
			mnemonicsListOfCurrentChunk = getElements(String
					.format(EditorTextPageElements.LOADED_CHUNK, currentChunkNumber) + EditorTextPageElements.MD_MNEM_MNEMONIC)
					.stream().map(WebElement::getText).collect(Collectors.toList());

			mnemonicsList = ListUtils.union(mnemonicsList, mnemonicsListOfCurrentChunk);

			editorPage().breakOutOfFrame();
		}
		return mnemonicsList;
	}

	public void deleteMainTextBlock()
	{
		click(EditorTextPageElements.TEXT_LABEL);
		getElement(EditorTextPageElements.TEXT_LABEL).sendKeys(Keys.DELETE);
	}

	public void deleteNoteOfDecisionByUserName(String userName)
	{
		click(String.format(EditorTextPageElements.NDP_BY_USER,userName));
		getElement(String.format(EditorTextPageElements.NDP_BY_USER,userName)).sendKeys(Keys.DELETE);
	}

	public boolean checkVisibilityOfNodeWithGivenEnglishLabel(String elementsXpath, boolean visibilityExpected)
	{
		// collect elements for a check
		Stream<WebElement> elementsStream = getElements(elementsXpath).stream()
				.skip(1) // the English label is always displayed, so we skip it
				.filter(element -> !element.getTagName().equals("mergeremove")); // an exclusion found

		// check visibility
		return elementsStream
				.allMatch(element -> visibilityExpected == element.isDisplayed());
	}

	public void rightClickQuery()
	{
		scrollToView(EditorTextPageElements.QUERY_NOTE_OF_ANY_TYPE);
		click(EditorTextPageElements.QUERY_NOTE_BLOCK);
	    waitForEditorElementSelected(EditorTextPageElements.QUERY_NOTE_OF_ANY_TYPE);
		rightClick(EditorTextPageElements.QUERY_NOTE_OF_ANY_TYPE);
	}

	public boolean checkQueryContent(String queryType, String[] queryContent, String elementXpath)
	{
		// gathering things to check -- different action date case for each query type
		ArrayList<String> expectedQueryContent = new ArrayList<>();
		expectedQueryContent.add("Query Type:");
		expectedQueryContent.add(queryType + " Query");
		switch (queryType)
		{
			case "Date":
				expectedQueryContent.add("Action Date:");
				expectedQueryContent.add(queryContent[1]);
				break;
			case "Revisor":
				expectedQueryContent.add("Action Date:");
				int len = queryContent[1].length();
				expectedQueryContent.add(queryContent[1].substring(len - 4, len));
				break;
		}
		expectedQueryContent.add("Query Text:");
		expectedQueryContent.add(queryContent[0]);

		// comparing
		List<String> actualQueryContent = driver.findElements(By.xpath(elementXpath + "/span")).stream()
				.map(WebElement::getText).collect(Collectors.toList()).subList(0, expectedQueryContent.size());

		boolean queryDisplaysExpectedContent = true;
		for (int number = 0; number < expectedQueryContent.size(); number++)
		{
			queryDisplaysExpectedContent &= actualQueryContent.get(number).equals(expectedQueryContent.get(number));
		}

		return queryDisplaysExpectedContent;
	}

	public List<String> getPubtagsListOfTextParagraph(int textParagraphNumber)
	{
		return getElements(String.format(EditorTextPageElements.TEXT_PARAGRAPH_WITH_NUMBER_PARA, textParagraphNumber)
				+ EditorTextPageElements.ANY_PUBTAG_IN_METADATA_BLOCK).stream().map(WebElement::getText).collect(Collectors.toList());
	}

	public List<String> getPubtagsList()
	{
		return getElements(EditorTextPageElements.ANY_PUBTAG_IN_METADATA_BLOCK).stream().map(WebElement::getText).collect(Collectors.toList());
	}

	public void rightClickFirstTextParagraphText()
	{
		rightClick(EditorTextPageElements.TEXT_PARAGRAPH_PARATEXT);
		breakOutOfFrame();
	}

	public void openMarkupSubMenu()
	{
		getElement(EditorTextContextMenuElements.MARKUP).sendKeys(Keys.ARROW_RIGHT);
	}

	public void insertPhraseAtFootnotesHintWithReference(String reference, String phrase)
	{
		waitForElementExists(String.format(EditorTextPageElements.FOOTNOTE_WITH_GIVEN_REFERENCE, reference));
		String footnoteId = getElementsId(String.format(EditorTextPageElements.FOOTNOTE_WITH_GIVEN_REFERENCE, reference));
		waitForElementExists(String.format(EditorTextPageElements.FOOTNOTE_BY_ID_HINT, footnoteId));
		click(String.format(EditorTextPageElements.FOOTNOTE_BY_ID_HINT, footnoteId));
		waitForElementExists(String.format(EditorTextPageElements.FOOTNOTE_BY_ID_NOT_HIGHLIGHTED, footnoteId));
		getElement(String.format(EditorTextPageElements.FOOTNOTE_BY_ID_NOT_HIGHLIGHTED, footnoteId)
				+ EditorTextPageElements.HINT).sendKeys(phrase);
		waitForElementExists(String.format(EditorTextPageElements.FOOTNOTE_BY_ID_NOT_HIGHLIGHTED, footnoteId)
				+ String.format(EditorTextPageElements.PARATEXT_WITH_TEXT, phrase));
	}

	public String checkFootnoteReferenceTextUnderNumber(int number)
	{
		return getElementsText(String.format(EditorTextPageElements.FOOTNOTE_REFERENCE_IN_TEXT_UNDER_NUMBER,number));
	}

	public String getFootnoteTextUnderNumber(int number)
	{
		return getElementsText(String.format(EditorTextPageElements.FOOTNOTE_TEXT, number));
	}

	public int getNumberOfFootnoteMnemonics(String mnemonic)
	{
		return getElements(String.format(EditorTextPageElements.FOOTNOTE_MNEMONIC, mnemonic)).size();
	}

	public int getNumberOfFootnotesWithReference(String reference)
	{
		return getElements(String.format(EditorTextPageElements.FOOTNOTE_WITH_GIVEN_REFERENCE, reference)).size();
	}

	public int getNumberOfFootnoteReferences(String reference)
	{
		return getElements(String.format(EditorTextPageElements.FOOTNOTE_REFERENCE_IN_TEXT, reference)).size();
	}

	public boolean doesFootnoteWithGivenReferenceAndHintUnderCreditExist(String reference)
	{
		return doesElementExist(EditorTextPageElements.FOOTNOTE_UNDER_CREDIT) &&
				doesElementExist(String.format(EditorTextPageElements.FOOTNOTE_PARAGRAPH_WITH_GIVEN_REFERENCE_AND_HINT,
						reference));
	}

	public boolean doesFootnoteWithGivenReferenceAndHintExist(String reference)
	{
		return doesElementExist(String.format(EditorTextPageElements.FOOTNOTE_PARAGRAPH_WITH_GIVEN_REFERENCE_AND_HINT, reference));
	}

	public void addTextInsideHeadingTextLabel(String textToInsert)
	{
		click(EditorTextPageElements.HEADING_TEXT_LABEL);
		sendKeys(textToInsert);
	}

	public boolean isGivenTextInsideHeadingTextLabel(String textToCheck)
	{
		return getElement(EditorTextPageElements.HEADING_TEXT_LABEL).getText().contains(textToCheck);
	}

	public void sendTextToBillTrackBlock(String text)
	{
		click(EditorTextPageElements.BILL_TRACK_BLOCK_TRACK_STAGE_TEXT_XPATH);
		click(EditorTextPageElements.BILL_TRACK_BLOCK_TRACK_STAGE_TEXT_XPATH);
		sendKeysToElement(EditorTextPageElements.BILL_TRACK_BLOCK_TRACK_STAGE_TEXT_XPATH,text);
	}

	public String getTextFromBillTrackModified()
	{
		return getElement(EditorTextPageElements.BILL_TRACK_BLOCK_TRACK_STAGE_MODIFIIED_XPATH).getText();
	}

	public String getTextFromBillTrackText()
	{
		return getElement(EditorTextPageElements.BILL_TRACK_BLOCK_TRACK_STAGE_TEXT_XPATH).getText();
	}

	public void clickSourceSectionByNumber(int num)
	{
		click(String.format(EditorTextPageElements.SECTION_SOURCE_SECTION_BYNUM, num));
	}

	public void rightClickSourceSectionByNumber(int num)
	{
		rightClick(String.format(EditorTextPageElements.SECTION_SOURCE_SECTION_BYNUM, num));
	}

	public String copyParagraphText()
	{
		editorPage().scrollToElement(EditorTextPageElements.PARATEXT);
		String copiedData = selectParagraphText();
		Actions copyAction = new Actions(driver);
		copyAction.sendKeys(Keys.chord(Keys.CONTROL, "c")).perform();
		return copiedData;
	}

	public void clickFirstLineSummaryTextBox()
	{
		click(EditorTextPageElements.FIRST_SUMMARY_LINE_TEXTBOX_XPATH);
	}

	public void rightClickFirstLineSummaryTextBox()
	{
		rightClick(EditorTextPageElements.FIRST_SUMMARY_LINE_TEXTBOX_XPATH);
	}

	public boolean doesSecondSummaryLineExist()
	{
		return doesElementExist(EditorTextPageElements.SECOND_SUMMARY_LINE_FROM_SPLIT_XPATH);
	}

	public void clickFirstTrackingStage()
	{
		click(EditorTextPageElements.FIRST_TRACKING_STAGE_TEXTBOX_XPATH);
	}

	public void rightCLickFirstTrackingStage()
	{
		rightClick(EditorTextPageElements.FIRST_TRACKING_STAGE_TEXTBOX_XPATH);
	}

	public boolean doesSecondTrackingStageExist()
	{
		return doesElementExist(EditorTextPageElements.SECOND_TRACKING_STAGE_FROM_SPLIT_XPATH);
	}

	public int getNumberOfSourceSections()
	{
		return getElements(EditorTextPageElements.SECTION_SOURCE_SECTION).size();
	}

	public void clickSectionIntroParagraphXText(int num)
	{
		click(String.format(EditorTextPageElements.SECTION_INTRO_PARA_TEXT_XPATH,num));
	}

	public void clickSectionIntroParagraphXSpan(int num)
	{
		click(String.format(EditorTextPageElements.SECTION_INTRO_PARA_SPAN_XPATH,num));
	}

	public void sendKeysToIntroParagraphX(int num, String text)
	{
		sendKeysToElement(String.format(EditorTextPageElements.SECTION_INTRO_PARA_SPAN_XPATH,num),text);
	}

	public boolean sectionIntroParagraphXContains(int num, String text)
	{
		return getElementsText(String.format(EditorTextPageElements.SECTION_INTRO_PARA_TEXT_XPATH,num)).contains(text);
	}

	public void sendDeleteToSectionIntroParagraphX(int num)
	{
		getElement(String.format(EditorTextPageElements.SECTION_INTRO_PARA_SPAN_XPATH,num)).sendKeys(Keys.DELETE);
	}

	public int getNumberOfParagraphs()
	{
		return getElements(EditorTextPageElements.TEXT_PARAGRAPH_PARATEXT).size();
	}

	public List<String> getListOfParagraphText()
	{
		return getElementsTextList(EditorTextPageElements.TEXT_PARAGRAPH_PARATEXT);
	}

	public void clickInsertTextHint()
	{
		click(EditorTextPageElements.INSERT_TEXT_HINT);
	}

	public void insertPhraseToTargetLocationSubsection(String phrase)
	{
		click(EditorTextPageElements.TARGET_LOCATION_SUBSECTION);
		Actions cursor = new Actions(driver);
		cursor.sendKeys(Keys.ARROW_RIGHT).sendKeys(Keys.SPACE).sendKeys(phrase).build().perform();
	}

	public void insertPhraseToTextsResearchReference(String phrase)
	{
		click(EditorTextPageElements.REFERENCE_LABEL);
		sendKeys(Keys.ARROW_RIGHT);
		sendKeys(Keys.SPACE);
		sendKeys(phrase);
	}

	public boolean checkEditorInfoAboutTextMerge(String docNumber, String testPhrase)
	{
		editorPage().switchDirectlyToTextFrame();
		boolean editorInfoAboutTextMerge = (isElementDisplayed(String.format(EditorTextPageElements.REFERENCE_TEXT_WITH_THE_PHRASE_GIVEN, testPhrase)))
				&& (isElementDisplayed(String.format(EditorTextPageElements.REFERENCE_DOC_NUMBER, docNumber)))
				&& (isElementDisplayed(String.format(EditorTextPageElements.REFERENCE_MODIFIED_BY, DateAndTimeUtils.getCurrentDateMMddyyyy())));
		return editorInfoAboutTextMerge;
	}

	public boolean checkEffectiveDateInEditor(String level)
	{
		switchToEditorTextArea();

		List<String> checksList = getElements(String.format("//span/following-sibling::effective.date.block/date")).
				stream().map(WebElement::getText).collect(Collectors.toList());
		String expectedValue = DateAndTimeUtils.getCurrentDateMMddyyyy();

		Predicate<String> expectedContentOfTag = actualContentOfTag -> actualContentOfTag.equals(expectedValue);

		return checksList.stream().allMatch(expectedContentOfTag);
	}

	public int numberOfSectionByGrouping(String level)
	{
		int deltasExist = 0;
		switchToEditorTextArea();
		if(level == "Section")
		{
			deltasExist = getElements(EditorTextPageElements.SECTION_IN_EDITOR).size();
		}
		else if(level == "Delta")
		{
			deltasExist = getElements(EditorTextPageElements.DELTA_IN_EDITOR).size();
		}
		return deltasExist;
	}

	public int countInstancesOfTermInParagraphs(String term)
	{
		int termCount = 0;
		int startIndex;
		int termLength = term.length();
		boolean errored = false;
		String paragraphText = "";
		List<WebElement> paras = getElements("//para//paratext");
		for (WebElement para : paras)
		{
			paragraphText += para.getText() + " ";
		}
		while(errored != true)
		{
			startIndex = paragraphText.indexOf(term);
			if(startIndex != -1)
			{
				paragraphText = paragraphText.substring(startIndex + termLength).trim();
				termCount++;
			}
			else
			{
				errored = true;
			}
		}
		return termCount;
	}

	public int getFootnoteCount()
	{
		editorPage().switchToEditor();
		switchToEditorTextArea();
		return getElements(EditorTextPageElements.LEGISLATIVE_HISTORY_FOOTNOTE).size();
	}

	public boolean deleteFootnote(int footnoteNumber)
	{
		editorPage().switchDirectlyToTextFrame();
		WebElement textLabel = getElement(EditorTextPageElements.LEGISLATIVE_HISTORY_FOOTNOTE + "/paratext");
		textLabel.click();
		Actions cursor = new Actions(driver);
		cursor.sendKeys(Keys.HOME);
		for (int i = 0; i <= footnoteNumber; i++)
		{
			cursor.sendKeys(Keys.F7);
		}
		cursor.sendKeys(Keys.DELETE);
		Action action = cursor.build();
		action.perform();
		return !doesElementExist(EditorTextPageElements.LEGISLATIVE_HISTORY_FOOTNOTE);
	}

	public void rightClickDeltaAmend(String type)
	{
		rightClick(String.format(EditorTextPageElements.DELTA_AMEND_LABEL,type));
	}

	public void insertWordInPart(String part, String word)
	{
		switchToEditorTextArea();
		WebElement textLabel = getElement("//" + part + "/para[1]");
		textLabel.click();
		textLabel.sendKeys(Keys.HOME);
		textLabel.sendKeys(Keys.DOWN);
		Actions cursor = new Actions(driver);
		cursor.sendKeys(word);
		Action action = cursor.build();
		action.perform();
	}

	public void moveToElementAndSendKeys(String xPath, String keysToSend)
	{
		new Actions(driver).moveToElement(getElement(xPath)).click().sendKeys(keysToSend).build().perform();
	}

	public void moveToElementWithOffsetAndRightClick(String xPath, int xOffset, int yOffset)
	{
		new Actions(driver).moveToElement(getElement(xPath), xOffset, yOffset).contextClick().build().perform();
	}

	public List<String> getPubTagsBySubsectionAndParaNumber(int subsectionNumber, int paraNumber)
	{
		List<String> pubTagsList = new ArrayList<>();
		editorTextPage().getElements(EditorTextPageElements.BODY_TAG
				+ String.format("/subsection[%d]/para[%d]", subsectionNumber, paraNumber)
				+ EditorTextPageElements.ANY_PUBTAG_IN_METADATA_BLOCK).forEach(
				element -> pubTagsList.add(element.getText()));
		return pubTagsList;
	}

	public String getHistoricalNoteParaTextContent()
	{
		return getElementsText(EditorTextPageElements.HISTORICAL_NOTE_PARATEXT);
	}

	//This method replaces all hints from the last to the first element because in another case when started from
	//the first element not all hints replace correctly
	public void replaceAllHintsWithText(String textToReplace)
	{
		List<WebElement> hints = getElements(EditorTextPageElements.HINT);
		for (int i = hints.size() - 1; i >= 0; i--)
		{
			hints.get(i).click();
			//hints.get(i).sendKeys(textToReplace);
			sendKeys(textToReplace);
		}
	}

	public Long getScrollPosition()
	{
		return (Long) ((JavascriptExecutor) driver)
				.executeScript("return document.documentElement.scrollTop;");
	}

	public String getSelectedText()
	{
		return (String) ((JavascriptExecutor) driver)
				.executeScript("return document.selection.createRange().text");
	}

	/**
	 * Waits for the Editor element to be selected, essentially having a background-color of rgba(49, 106, 197, 1) <br>
	 * HEX #316AC5 shows up in the DOM, however, with additional testing by logging the value of background-color, it displays as rgba(49, 106, 197, 1) <br>
	 *
	 * This must be used after any kind of action that selects an element in the Editor for stability. <br>
	 *
	 * @param elementXpath - The element XPATH to be checked.
	 */
	public void waitForEditorElementSelected(String elementXpath)
	{
		waitForEditorElementSelectedHelper(getElement(elementXpath));
	}

	/**
	 * Waits for the Editor element to be selected, essentially having a background-color of rgba(49, 106, 197, 1) <br>
	 * HEX #316AC5 shows up in the DOM, however, with additional testing by logging the value of background-color, it displays as rgba(49, 106, 197, 1) <br>
	 *
	 * This must be used after any kind of action that selects an element in the Editor for stability. <br>
	 *
	 * @param element - The element to be checked.
	 */
	public void waitForEditorElementSelected(WebElement element)
	{
		waitForEditorElementSelectedHelper(element);
	}

	private void waitForEditorElementSelectedHelper(WebElement element)
	{
		// CSS BACKGROUND VALUE IS: background-color #316AC5 OR rgba(49, 106, 197, 1)
		// Will loop for maximum of 10 seconds as anything over that should definitely be a bug in the UI
		int counter = 0;
		while(element.getCssValue("background-color").equals("rgba(49, 106, 197, 1)") && counter < 20)
		{
			//logger.information("Element's background color is: " + element.getCssValue("background-color"));
			DateAndTimeUtils.takeNap(DateAndTimeUtils.HALF_SECOND);
			counter++;
		}
		if(counter >= 20)
		{
			logger.severe("Editor element did not appear selected.  This may mean a refinement to the xpath is necessary, or the element we are checking does not support this method.");
		}
	}

	public void ctrlCUsingAction()
	{
		new Actions(driver).keyDown(Keys.CONTROL).sendKeys("c").keyUp(Keys.CONTROL).build().perform();
	}

	public void ctrlAUsingAction()
	{
		new Actions(driver).keyDown(Keys.CONTROL).sendKeys("a").keyUp(Keys.CONTROL).build().perform();
	}

	public void ctrlAAndPasteUsingAction(String text)
	{
		new Actions(driver).keyDown(Keys.CONTROL).sendKeys("a").keyUp(Keys.CONTROL).sendKeys(text).build().perform();
	}

	public void ctrlSUsingAction()
	{
		new Actions(driver).keyDown(Keys.CONTROL).sendKeys("s").keyUp(Keys.CONTROL).build().perform();
	}

	public void ctrlZUsingAction()
	{
		new Actions(driver).keyDown(Keys.CONTROL).sendKeys("z").keyUp(Keys.CONTROL).build().perform();
	}

	public void ctrlYUsingAction()
	{
		new Actions(driver).keyDown(Keys.CONTROL).sendKeys("y").keyUp(Keys.CONTROL).build().perform();
	}

	public void altAUsingAction()
	{
		new Actions(driver).keyDown(Keys.ALT).sendKeys("a").keyUp(Keys.ALT).build().perform();
	}

	public void ctrlVUsingAction()
	{
		new Actions(driver).keyDown(Keys.CONTROL).sendKeys("v").keyUp(Keys.CONTROL).build().perform();
	}

	public void ctrlLUsingAction()
	{
		new Actions(driver).keyDown(Keys.CONTROL).sendKeys("l").keyUp(Keys.CONTROL).build().perform();
	}

	public void ctrlXUsingAction()
	{
		new Actions(driver).keyDown(Keys.CONTROL).sendKeys("x").keyUp(Keys.CONTROL).build().perform();
	}

	public void ctrlRUsingAction()
	{
		new Actions(driver).keyDown(Keys.CONTROL).sendKeys("r").keyUp(Keys.CONTROL).build().perform();
	}

	public void ctrlTUsingAction()
	{
		new Actions(driver).keyDown(Keys.CONTROL).sendKeys("t").keyUp(Keys.CONTROL).build().perform();
	}

	public void ctrlWUsingAction()
	{
		new Actions(driver).keyDown(Keys.CONTROL).sendKeys("w").keyUp(Keys.CONTROL).build().perform();
	}

	public void highlightHelperUsingRobot(String phrase)
	{
		DateAndTimeUtils.takeNap(DateAndTimeUtils.THREE_SECONDS);
		RobotUtils.getRobot().keyPress(KeyEvent.VK_SHIFT);
		try
		{
			for (int i = 0; i < phrase.length(); i++)
			{
				RobotUtils.getRobot().keyPress(KeyEvent.VK_LEFT);
				RobotUtils.getRobot().keyRelease(KeyEvent.VK_LEFT);
			}
		}
		finally
		{
			RobotUtils.getRobot().keyRelease(KeyEvent.VK_SHIFT);
		}
		DateAndTimeUtils.takeNap(DateAndTimeUtils.THREE_SECONDS);
	}

	public  void addTableUsingAltT()
	{
		try
		{
			RobotUtils.getRobot().keyPress(KeyEvent.VK_ALT);
			RobotUtils.getRobot().keyPress(KeyEvent.VK_T);
		}
		finally
		{
			RobotUtils.getRobot().keyRelease(KeyEvent.VK_ALT);
			RobotUtils.getRobot().keyRelease(KeyEvent.VK_T);
		}
	}

	public void editTableViaShiftAltT()
	{
		try
		{
			RobotUtils.getRobot().keyPress(KeyEvent.VK_SHIFT);
			RobotUtils.getRobot().keyPress(KeyEvent.VK_ALT);
			RobotUtils.getRobot().keyPress(KeyEvent.VK_T);
		}
		finally
		{
			RobotUtils.getRobot().keyRelease(KeyEvent.VK_SHIFT);
			RobotUtils.getRobot().keyRelease(KeyEvent.VK_ALT);
			RobotUtils.getRobot().keyRelease(KeyEvent.VK_T);
		}
	}

	public void selectTable(String htmlText, boolean additionalHighlighting)
	{
		int numberOfChar = htmlText.length();
		for (int i = 0; i < numberOfChar; i++)
		{
			DateAndTimeUtils.takeNap(DateAndTimeUtils.ONE_SECOND);
			try
			{
				RobotUtils.getRobot().keyPress(KeyEvent.VK_RIGHT);
			}
			finally
			{
				RobotUtils.getRobot().keyRelease(KeyEvent.VK_RIGHT);
			}
		}
		DateAndTimeUtils.takeNap(DateAndTimeUtils.ONE_SECOND);

		try
		{
			RobotUtils.getRobot().keyPress(KeyEvent.VK_SHIFT);
			RobotUtils.getRobot().keyPress(KeyEvent.VK_END);
		}
		finally
		{
			RobotUtils.getRobot().keyRelease(KeyEvent.VK_END);
			RobotUtils.getRobot().keyRelease(KeyEvent.VK_SHIFT);
		}

		int numberOfLines = 3;
		for (int i = 0; i < numberOfLines - 1; i++)
		{
			try
			{
				RobotUtils.getRobot().keyPress(KeyEvent.VK_SHIFT);
				RobotUtils.getRobot().keyPress(KeyEvent.VK_DOWN);
			}
			finally
			{
				RobotUtils.getRobot().keyRelease(KeyEvent.VK_DOWN);
				RobotUtils.getRobot().keyRelease(KeyEvent.VK_SHIFT);
			}
		}

		if (additionalHighlighting)
		{
			//9 here refers to the left out chars to be highlighted in the embedded html after SHIFT+DOWN(3times)
			for (int i = 0; i < 9; i++)
			{
				try
				{
					RobotUtils.getRobot().keyPress(KeyEvent.VK_SHIFT);
					RobotUtils.getRobot().keyPress(KeyEvent.VK_RIGHT);
				}
				finally
				{
					RobotUtils.getRobot().keyRelease(KeyEvent.VK_SHIFT);
					RobotUtils.getRobot().keyRelease(KeyEvent.VK_RIGHT);
				}
			}
		}
		DateAndTimeUtils.takeNap(DateAndTimeUtils.THREE_SECONDS);
	}

	public void deleteUsingAction()
	{
		new Actions(driver).keyDown(Keys.DELETE).keyUp(Keys.DELETE).build().perform();
	}

	public void shiftF10UsingAction()
	{
		new Actions(driver).keyDown(Keys.SHIFT).keyDown(Keys.F10).keyUp(Keys.F10).keyUp(Keys.SHIFT).build().perform();
	}

	public void altZUsingAction()
	{
		new Actions(driver).keyDown(Keys.ALT).sendKeys("z").keyUp(Keys.ALT).build().perform();
	}

	public void shiftAltZUsingAction()
	{
		new Actions(driver).keyDown(Keys.SHIFT).keyDown(Keys.ALT).sendKeys("z").keyUp(Keys.ALT).keyUp(Keys.SHIFT).build().perform();
	}

	public void placeCursorBehindTheTextInTextParagraphUsingRobot(String xPath)
	{
		editorTextPage().click(xPath);
		editorTextPage().waitForElement(HIGHLIGHTED_PARA);
		try
		{
			RobotUtils.getRobot().keyPress(KeyEvent.VK_DOWN);
			RobotUtils.getRobot().keyRelease(KeyEvent.VK_DOWN);
			DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
			RobotUtils.getRobot().keyPress(KeyEvent.VK_HOME);
			RobotUtils.getRobot().keyRelease(KeyEvent.VK_HOME);
		}
		finally
		{
			RobotUtils.getRobot().keyRelease(KeyEvent.VK_DOWN);
			RobotUtils.getRobot().keyRelease(KeyEvent.VK_HOME);
		}
	}

	public void holdFocusAndRightClickOnElement(String xPath)
	{
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("document.getElementById('" + xPath + "').focus();");
		WebElement element = driver.switchTo().activeElement();
		rightClick(element);
	}

	public void placeCursorAtTheBeginningOfTextParagraph(String xpath)
	{
		editorPage().click(editorTextPage().getElement(xpath));
		editorTextPage().waitForEditorElementSelected(editorTextPage().getElement(xpath));
		editorTextPage().sendKeys(Keys.HOME);
		editorTextPage().sendKeys(Keys.ARROW_DOWN);
		editorTextPage().sendKeys(Keys.ARROW_RIGHT);
	}

	public void insertSpaceAndRemoveSpaceRunningHeadBlock()
	{
		click(EditorTextPageElements.RUNNING_HEAD_EVEN_TEXT);
		sendKeys(Keys.HOME);
		sendKeys(" ");
		sendKeys(Keys.BACK_SPACE);
	}
}

