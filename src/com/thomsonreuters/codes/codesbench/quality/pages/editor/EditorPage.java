
package com.thomsonreuters.codes.codesbench.quality.pages.editor;

import com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.editor.EditorTextContextMenuElements;
import com.thomsonreuters.codes.codesbench.quality.menuelements.mainmenuelements.HierarchyMenuElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.*;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.popups.*;
import com.thomsonreuters.codes.codesbench.quality.pageelements.source.SourceNavigatePageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.rest.RestUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.robot.RobotUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.switchEnums.ParagraphInsertMethods;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorPageElements.*;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements.METADATA_LABEL_DESIGNATOR;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorToolbarPageElements.toolbarInsertSpecialCharacterButton;
import static java.lang.String.format;


@Component
public class EditorPage extends BasePage
{
	private WebDriver driver;

	@Autowired
	public EditorPage(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
	}

	@PostConstruct
	public void init()
	{
		PageFactory.initElements(driver, EditorPageElements.class);
		PageFactory.initElements(driver, EditorToolbarPageElements.class);
		PageFactory.initElements(driver, EditorTextPageElements.class);
	}

	
	public boolean checkForUuidMatchInUrl(String uuid)
	{
		return getUrl().contains(uuid);
	}

	/*
	 * switching
	 */

	
	public void switchToTheOpenedDocument(int documentNumber)
	{
      /*WebElement document = getElements(EDITOR_LIST).get(documentNumber - 1);
		new Actions(driver).keyDown(Keys.ALT).sendKeys("`").build().perform();
		scrollToElement(document);
		click(document);*/

		RobotUtils.altBackQuoteUsingRobot();
		maximizeCurrentWindow();
		DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
		clickDocByFrameIndex(documentNumber + 1);
	}

	public void clickDocByFrameIndex(int docNumber)
	{
		JavascriptExecutor editorListEle  = (JavascriptExecutor) driver;
		/* Do not remove +1 from the arguments;FrameId index starts from 2 instead of 1 while calling we need to increment by 1 to acheive proper switching*/
		editorListEle.executeScript("arguments[0].click();", getElementById(EDITOR_LIST_ID + docNumber));
	}

	public void switchBetweenOpenDocuments()
	{
		new Actions(driver).keyDown(Keys.ALT).sendKeys("`").build().perform();
	}

	
	public void switchDirectlyToTextFrame()
	{
		switchToEditor();
		switchToEditorTextFrame();
	}

	
	public boolean checkIfEditorIsOpened()
	{
		return checkWindowIsPresented(EditorPageElements.PAGE_TITLE);
	}

	
	public void switchToEditor()
	{
		checkWindowIsPresented(EditorPageElements.PAGE_TITLE);
		waitForElementGone(COMMAND_IN_PROGRESS);
	}

	
	public boolean switchToEditorWindow()
	{
		return switchToWindow(EditorPageElements.PAGE_TITLE, true);
	}

	public void switchToEditorTextFrame()
	{
		switchToEditorTextFrameByIndex(1);
	}

	public void switchToEditorTextFrameByIndex(int index)
	{
		switchToInnerIFrameByIndex(index);
		waitForElementGone(EditorToolbarPageElements.CLOSE_DOC);
	}

	/*
	 * scrolling
	 */

	
	public int getChunkAmount()
	{
		waitForPageLoaded();
		waitForElement(EditorPageElements.EDITOR_IFRAME);
		waitForElement(EditorToolbarPageElements.toolbarCloseDocButton);
		JavascriptExecutor jse = (JavascriptExecutor)driver;
		return Integer.parseInt(String.valueOf(jse.executeScript("return editors.current().tw.p.ds.getNumberOfChunks()")));
	}

	
	private int getCurrentChunkNumber()
	{
		Pattern pattern = Pattern.compile("(?<=loaded_chunk_)(.*)");
		Matcher matcher;

		// async
		if (doesElementExist("//div[@id='loaded_chunk_0']", 5))
		{
			matcher = pattern.matcher(driver.findElements(By.xpath("//div[starts-with(@id,'loaded_chunk_')][*]"))
					.get(0).getAttribute("id"));
		}
		// old
		else
		{
			matcher = pattern.matcher(driver.findElements(By.xpath("//div[contains(@id,'loaded_chunk_')]"))
					.get(0).getAttribute("id"));
		}

		int currentChunkNumber = -1;

		if (matcher.find())
		{
			currentChunkNumber = Integer.parseInt(matcher.group());
		}
		else
		{
			Assertions.fail("Could not extract current chunk number!");
		}

		return currentChunkNumber;
	}

	/**
	 * targetChunkNumber starts at 1, and is then decremented in the method to
	 * account for the DOM starting counting at 0.
	 */
	
	public void scrollToChunk(int targetChunkNumber)
	{
		// compare target chunk number with total chunk amount and 1
		int chunkAmount = getChunkAmount();
		if (targetChunkNumber < 1 || targetChunkNumber > chunkAmount)
		{
			closeEditorWithDiscardingChangesIfAppeared();

			Assertions.fail("Target chunk number is more than the opened document has OR you passed a number that is less than 1.");
		}

		switchToEditorTextFrame();

		/*
		Jesse: Commenting this out as we always have this element, and it ends up decrementing the number too much
		for some tests that require to scroll to the last chunk
		 */
//		if (doesElementExist(LOADED_CHUNK))
//		{
//			--targetChunkNumber;
//		}

		// get current chunk
		int currentChunkNumber = getCurrentChunkNumber();

		// check if we are already in target chunk
		if (currentChunkNumber == targetChunkNumber)
		{
			return;
		}

		// if we need to scroll to the last chunk
		if (targetChunkNumber == chunkAmount)
		{
			targetChunkNumber = targetChunkNumber - 1;
		}

		switchToEditor();

		// scroll
		JavascriptExecutor jse = (JavascriptExecutor)driver;

		jse.executeScript(String.format(SCROLL_TO_CHUNK_JS, targetChunkNumber, targetChunkNumber));
		jse.executeScript(String.format(SCROLL_DOWN, 5));

		switchToEditorTextFrame();

	}

	public void switchToEditorAndScrollToChunk(int targetChunkNumber)
	{
		switchToEditor();
		scrollToChunk(targetChunkNumber);
	}

	public void scrollToTop()
	{
		DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("window.scrollTo(0, -document.body.scrollHeight)");
	}

	public boolean checkForChunkLoadedByNumber(int number)
	{
		return doesElementExist(String.format(EditorTextPageElements.LOADED_CHUNK_NUMBER, number));
	}

	/*
	 * closing
	 */

	public void pressCloseDocumentButton()
	{
		switchToEditor();
		waitForElementExists(EditorToolbarPageElements.CLOSE_DOC);
		click(EditorToolbarPageElements.CLOSE_DOC);
	}

	public void closeDocumentWithNoChanges()
	{
		switchToEditor();
		waitForElementExists(EditorToolbarPageElements.CLOSE_DOC);
		click(EditorToolbarPageElements.CLOSE_DOC);
	}

	public void closeDocumentWithChanges()
	{
		switchToEditor();
		waitForElementExists(EditorToolbarPageElements.CLOSE_DOC);
		click(EditorToolbarPageElements.CLOSE_DOC);
		switchToWindow(DocumentClosurePageElements.PAGE_TITLE);
	}

	public void closeDocumentAndDiscardChanges()
	{
		switchToEditor();
		waitForElementExists(EditorToolbarPageElements.CLOSE_DOC);
		sendEnterToElement(EditorToolbarPageElements.CLOSE_DOC);
		acceptAlertNoFail();
		switchToWindow(DocumentClosurePageElements.PAGE_TITLE);
		click("//*[contains(text(),'Discard')]");
		acceptAlertNoFail();
		//waitForWindowGoneByTitle(DocumentClosurePageElements.PAGE_TITLE);
	}

	public void closeEditorWithDiscardingChangesIfAppeared()
	{
		closeDocumentWithNoChanges();
		if (waitForWindowByTitle(DocumentClosurePageElements.PAGE_TITLE, false, DateAndTimeUtils.TEN_SECONDS))
		{
			waitForElement("//*[contains(text(),'Discard')]");
			switchToWindow(DocumentClosurePageElements.PAGE_TITLE);
			click("//*[contains(text(),'Discard')]");
			acceptAlert();
		}
		//waitForWindowGoneByTitle(EditorPageElements.PAGE_TITLE);
	}

	public void closeAllOpenedDocumentsWithDiscardingChangesIfAppeared()
	{
		switchToEditor();
		List<String> openedDocuments = getMetadataListOfTheOpenedDocuments();
		while(!openedDocuments.isEmpty())
		{
//			switchToTheOpenedDocument(1);
			closeOpenedDocument(1);
			closeEditorWithDiscardingChangesIfAppeared();
			switchToEditorWindow();
			openedDocuments = getMetadataListOfTheOpenedDocuments();
		}
	}

	public void closeOpenedDocument(int documentNumber)
	{
		WebElement document = getElements(EDITOR_LIST).get(documentNumber - 1);
		RobotUtils.altBackQuoteUsingRobot();
		scrollToElement(document);
		click(document);
	}
	
	public void closeAndCheckInChanges()
	{
		switchToEditor();
		waitForElementExists(EditorToolbarPageElements.CLOSE_DOC);
		click(EditorToolbarPageElements.CLOSE_DOC);
		switchToWindow(DocumentClosurePageElements.PAGE_TITLE);
		if(doesElementExist(DocumentClosurePageElements.QUICK_LOAD_RADIO))
		{
			click(DocumentClosurePageElements.QUICK_LOAD_RADIO);
		}
		sendEnterToElement(DocumentClosurePageElements.CHECK_IN_BUTTON);
		waitForElementGone(DocumentClosurePageElements.CHECK_IN_BUTTON);
//		switchToEditor();
//		waitForElementGone(toolbarCloseDocButton);
	}

	public void closeAndCheckInChangesWithBaselineNotes(String notes)
	{
		switchToEditor();
		waitForElementExists(EditorToolbarPageElements.CLOSE_DOC);
		click(EditorToolbarPageElements.CLOSE_DOC);
		switchToWindow("Document Closure");
		if(doesElementExist(DocumentClosurePageElements.QUICK_LOAD_RADIO))
		{
			click(DocumentClosurePageElements.QUICK_LOAD_RADIO);
		}
		sendKeysToElement(DocumentClosurePageElements.BASELINE_NOTES_FIELD, notes);
		sendEnterToElement(DocumentClosurePageElements.CHECK_IN_BUTTON);
	}

	public void closeEditorWindowAndReopenRenditionForEdit()
	{
		switchToEditor();
		closeCurrentWindowIgnoreDialogue();
		switchToWindow(SourceNavigatePageElements.PAGE_TITLE);
//		TODO add a switch to chose between Source and Target
		sourceNavigateGridPage().firstRenditionEditContent();
	}

	public void closeEditorDiscardChangesAndReopenRenditionForEdit()
	{
		switchToEditor();
		closeEditorWithDiscardingChangesIfAppeared();
		switchToWindow(SourceNavigatePageElements.PAGE_TITLE);
//		TODO add a switch to chose between Source and Target
		sourceNavigateGridPage().firstRenditionEditContent();
	}

	/** This method will check if there's a specific element.
	 * If so, editor will be closed discarding all the changes.
	 * Then the rendition will be reopened and the check will be repeated.
	 * If the word is still present, the method will fail with Assertion.
	 *
	*/

	public void closeAndCheckInChangesWithGivenDate(String date)
	{
		switchToEditor();
		click(EditorToolbarPageElements.CLOSE_DOC);
		switchToWindow(DocumentClosurePageElements.PAGE_TITLE);
		getElement(DocumentClosurePageElements.EFFECTIVE_DATE).sendKeys(date);
		if(doesElementExist(DocumentClosurePageElements.QUICK_LOAD_RADIO))
		{
			click(DocumentClosurePageElements.QUICK_LOAD_RADIO);
		}
		click(DocumentClosurePageElements.CHECK_IN_BUTTON);
	}

	public void closeAndCheckInChangesWithGivenDateAndHandleSpellcheckWindowIfAppeared(String date)
	{
		closeAndCheckInChangesWithGivenDate(date);
		if (switchToWindow(SpellcheckPageElements.PAGE_TITLE))
		{
			waitForPageLoaded();
			click(SpellcheckPageElements.COMMIT_CHANGES_AND_CLOSE_BUTTON);
		}
	}

	/* TODO: sort this pile
	 * misc steps
	 */
	
	public void tryToDiscardChangesAndReopenCorruptedFile(String elementXpath, int chunkNumber)
	{
		editorPage().scrollToChunk(chunkNumber);
		boolean fileIsCorrupted = editorPage().doesElementExist(elementXpath);
		if (fileIsCorrupted)
		{
			editorPage().closeEditorDiscardChangesAndReopenRenditionForEdit();
			editorPage().scrollToChunk(chunkNumber);
			boolean fileIsStillCorrupted = editorPage().editorPage().doesElementExist(elementXpath);
			if (fileIsStillCorrupted)
			{
				Assertions.fail(String.format("Element \"%s\" should not be present. " +
						"The file is corrupted and unwanted changes were checked in", elementXpath));
			} else { editorPage().switchDirectlyToTextFrame(); }
		} else { editorPage().switchDirectlyToTextFrame(); }
	}

	
	public void typeTextInFirstTextParagraph(String text)
	{
		WebElement textLabel = getElement(String.format(EditorTextPageElements.TEXT_PARAGRAPH_LABEL_UNDER_NUMBER, 1));
		click(textLabel);
		Actions cursor = new Actions(driver);
		cursor.sendKeys(Keys.ARROW_DOWN);
		cursor.sendKeys(Keys.HOME);
		//textLabel.sendKeys(Keys.ARROW_DOWN);
		//textLabel.sendKeys(Keys.HOME);
		//insert the phrase as well as a space at the end of it and then move the cursor left one
		//Actions cursor = new Actions(driver);
		sendKeys(text);
		cursor.sendKeys(Keys.SPACE).sendKeys(Keys.LEFT);
		Action action = cursor.build();
		action.perform();
	}

	public void rightClickOnFirstTextParagraphHeading()
	{
		WebElement textLabel = getElement(String.format(EditorTextPageElements.TEXT_PARAGRAPH_LABEL_UNDER_NUMBER, 1));
		rightClick(textLabel);
	}

	public boolean insertSpecialCharacterInTextPara(String characterEntity, String characterSymbol)
	{
		typeTextInFirstTextParagraph("");
		breakOutOfFrame();
		click(toolbarInsertSpecialCharacterButton);
		switchToWindow(InsertSpecialCharacterPageElements.PAGE_TITLE, false);
		sendTextToTextbox(InsertSpecialCharacterPageElements.XML_ENTITY_BOX, characterEntity);
		sendEnterToElement(InsertSpecialCharacterPageElements.INSERT_BUTTON);
		waitForWindowGoneByTitle(InsertSpecialCharacterPageElements.PAGE_TITLE);
		switchDirectlyToTextFrame();

		return getElement(EditorTextPageElements.TEXT_PARAGRAPH_PARATEXT)
				.getText().startsWith(characterSymbol)
				|| getElement(EditorTextPageElements.TEXT_PARAGRAPH_PARATEXT)
				.getAttribute("innerHTML").startsWith(characterSymbol);
	}

	public void deleteTextElement(String elementsEnglishLabelXpath)
	{
		click(elementsEnglishLabelXpath);
		editorPage().sendKeys(Keys.DELETE);
	}

	public List<String> getHints()
	{
		return super.getElementsTextList(EditorTextPageElements.HINT);
	}

	public void selectMultipleTextParagraphs(int first, int last)
	{
		try
		{
			click(String.format(EditorTextPageElements.TEXT_PARAGRAPH_LABEL_UNDER_NUMBER, first));
			RobotUtils.getRobot().keyPress(KeyEvent.VK_CONTROL);

			for (int current = (first+1); current <= last; current++)
			{
				click(String.format(EditorTextPageElements.TEXT_PARAGRAPH_LABEL_UNDER_NUMBER, current));
			}
		}
		finally
		{
			RobotUtils.getRobot().keyRelease(KeyEvent.VK_CONTROL);
		}
	}

	public void joinParagraphsVia(String methodOfJoining, String paragraphXpathToJoin)
	{
		switch(methodOfJoining)
		{
			case "ContextMenu":
				joinParagraphsViaContextMenu(paragraphXpathToJoin);
				break;
			case "Hotkey":
				RobotUtils.joinParagraphUsingRobot();
				break;
			default:
				throw new RuntimeException("Error: Invalid methodOfJoining parameter for joinParagraphsVia method!");
		}
	}

	private void joinParagraphsViaContextMenu(String paragraphXpathToJoin)
	{
		rightClick(paragraphXpathToJoin);
		breakOutOfFrame();
		openContextMenu(EditorTextContextMenuElements.JOIN_PARAGRAPHS);
		switchToEditorTextFrame();
	}

	public void splitParagraphViaHotkey(String paragraphXpathToSplit)
	{
		editorTextPage().click(paragraphXpathToSplit);
		new Actions(driver).keyDown(Keys.LEFT_SHIFT).sendKeys(Keys.ENTER).build().perform();
	}

	public void createSavepointViaHotkeys()
	{
		new Actions(driver).keyDown(Keys.CONTROL).sendKeys("s").keyUp(Keys.CONTROL).build().perform();
	}

	public void undoChangesViaHotkeys()
	{
		new Actions(driver).keyDown(Keys.CONTROL).sendKeys("z").keyUp(Keys.CONTROL).build().perform();
	}

	public void redoChangesViaHotkeys()
	{
        new Actions(driver).keyDown(Keys.CONTROL).sendKeys("y").keyUp(Keys.CONTROL).build().perform();
	}

	public void splitParagraphViaContextMenu(String paragraphXpathToSplit)
	{
		editorTextPage().scrollToView(paragraphXpathToSplit);
		rightClick(paragraphXpathToSplit);
		breakOutOfFrame();
		openContextMenu(EditorTextContextMenuElements.MODIFY, EditorTextContextMenuElements.SPLIT_PARAGRAPH);
		switchToEditorTextFrame();
	}

	public void insertNewParagraphVia(ParagraphInsertMethods methodOfInserting, String element)
	{
		switch(methodOfInserting)
		{
			case CONTEXT_MENU:
				insertNewParagraphViaContextMenu(element);
				break;
			case HOTKEY:
				insertNewParagraphViaHotkey();
				break;
			case AS_SIBLING:
				insertNewParagraphAsSibling(element);
				break;
			default:
				Assertions.fail("Invalid parameter for insertNewParagraphVia method!");
		}
	}

	public void insertNewParagraphVia(ParagraphInsertMethods methodOfInserting)
	{
		insertNewParagraphVia(methodOfInserting, EditorTextPageElements.TEXT_PARAGRAPH_LABEL);
	}

	public void insertNewParagraphViaContextMenu(String element)
	{
		rightClick(element);
		breakOutOfFrame();
		openContextMenu(EditorTextContextMenuElements.INSERT_NEW_PARAGRAPH_ALT_I);
		switchToEditorTextFrame();
	}
	
	public void insertNewParagraphViaHotkey() {
		try {
			DateAndTimeUtils.takeNap(DateAndTimeUtils.THREE_SECONDS);
			RobotUtils.getRobot().keyPress(KeyEvent.VK_ALT);
			RobotUtils.getRobot().keyPress(KeyEvent.VK_I);
		} finally {
			RobotUtils.getRobot().keyRelease(KeyEvent.VK_ALT);
			RobotUtils.getRobot().keyRelease(KeyEvent.VK_I);
		}

	}

	private void insertNewParagraphAsSibling(String element)
	{
		rightClick(element);
		breakOutOfFrame();
		openContextSubMenu(EditorTextContextMenuElements.INSERT_TEXT_AS_SIBLING_SUBMENU,
				EditorTextContextMenuElements.TEXT_PARAGRAPH_SIBLING_SUBMENU,
				EditorTextContextMenuElements.TEXT_PARAGRAPH_SMP);
		switchToEditorTextFrame();
	}

	public boolean checkImageUploadedWithREST(String url, String fileName)
	{
		HttpURLConnection connection = RestUtils
				.connectToURLWithMethod(url, "GET");

		return RestUtils.getResponseCode(connection) == 200
				&& connection.getHeaderField("Content-disposition").contains(fileName);
	}

	public String grabUrlWithAutoIT()  throws IOException
	{
		String url = "URL has not been found!";

		DateAndTimeUtils.takeNap(DateAndTimeUtils.THREE_SECONDS);
		String filePath = new File("commonFiles\\AutoITScripts\\" + "GrabUrlFromPdfWindow.exe").getAbsolutePath();
		ProcessBuilder processBuilder = new ProcessBuilder(filePath);
		processBuilder.redirectOutput();
		Process process = processBuilder.start();

		Scanner scanner = new Scanner(process.getInputStream());
		while (scanner.hasNext())
		{
			url = scanner.nextLine();
		}
		scanner.close();

		return url;
	}

	public boolean verifySpellcheckWindowDisappearsByItselfWhenNoErrorsFound()
	{
		waitForWindowGoneByTitle(SpellcheckPageElements.PAGE_TITLE);
		return !waitForWindowByTitle(SpellcheckPageElements.PAGE_TITLE, false);
	}

	public boolean checkIfElementsClassAttributeContainsHighlighted(String elementXpath)
	{
		return getElement(elementXpath).getAttribute("class").toLowerCase().contains("highlighted");
	}

	public void waitForEditorToClose()
	{
		// 3 minute counter
		int count = 0;
		switchToWindow(EditorPageElements.PAGE_TITLE);
		while(doesElementExist(EditorToolbarPageElements.CLOSE_DOC) && count < 180)
		{
			DateAndTimeUtils.takeNap(DateAndTimeUtils.ONE_SECOND);
			count++;
		}
	}

	public void openFindAndReplaceDialog()
	{
		DateAndTimeUtils.takeNap(DateAndTimeUtils.THREE_SECONDS);
		new Actions(driver).keyDown(Keys.CONTROL).sendKeys("f").keyUp(Keys.CONTROL).build().perform();
		waitForWindowByTitle(FindAndReplacePageElements.PAGE_TITLE, true, DateAndTimeUtils.THIRTY_SECONDS);
		boolean switchedToFindAndReplaceWindow = switchToWindow(FindAndReplacePageElements.PAGE_TITLE);
		Assertions.assertTrue(switchedToFindAndReplaceWindow);
	}

	public void openFindInChunksDialog()
	{
		DateAndTimeUtils.takeNap(DateAndTimeUtils.THREE_SECONDS);
		new Actions(driver).keyDown(Keys.CONTROL).keyDown(Keys.ALT).sendKeys("f").keyUp(Keys.CONTROL).build().perform();
		waitForWindowByTitle(FindInChunksPageElements.PAGE_TITLE, true, DateAndTimeUtils.THIRTY_SECONDS);
		boolean switchedToFindInChunksWindow = switchToWindow(FindInChunksPageElements.PAGE_TITLE);
		Assertions.assertTrue(switchedToFindInChunksWindow);
	}

	public void switchToFindInChunksDialog()
	{
		switchToWindow(FindInChunksPageElements.PAGE_TITLE);
	}

	public void insertGlossaryLinkViaContextMenu()
	{
		rightClick("//para//paratext");
		breakOutOfFrame();
		openContextSubMenu(EditorTextContextMenuElements.MARKUP, EditorTextContextMenuElements.INSERT_GLOSSARY_LINK_MARKUP);
	}

	public void openInsertFootnoteDialog()
	{
		new Actions(driver).keyDown(Keys.ALT).sendKeys("f").build().perform();
		boolean switchedToFindAndReplaceWindow = switchToWindow("Insert Footnote");
		enterTheInnerFrame();
		Assertions.assertTrue(switchedToFindAndReplaceWindow);
	}

	public void jumpToBeginningOfDocument()
	{
		new Actions(driver).keyDown(Keys.LEFT_CONTROL).sendKeys(Keys.HOME).build().perform();
	}

	public void jumpToEndOfDocument()
	{
		new Actions(driver).keyDown(Keys.LEFT_CONTROL).sendKeys(Keys.END).build().perform();
	}

	public boolean checkForAddedPubtagsInEditorText(String... pubtags)
	{
		boolean newPubTagAppeared = true;
		for (String pubtag : pubtags)
		{
			newPubTagAppeared &= doesElementExist(String.format(EditorTextPageElements.PUBTAGS_WITH_TEXT, pubtag+"+"));
		}
		return newPubTagAppeared;
	}

	public boolean checkForDeletedPubtagsInEditorText(String... pubtags)
	{
		boolean newPubTagAppeared = true;
		for (String pubtag : pubtags)
		{
			newPubTagAppeared &= doesElementExist(String.format(EditorTextPageElements.PUBTAGS_WITH_TEXT, pubtag+"-"));
		}
		return newPubTagAppeared;
	}

	public boolean checkForModifiedByTextInTextParagraphNumber(String modifiedByTag, int number)
	{
		return getElementsText(String.format(EditorTextPageElements.MODIFIED_BY_TEXT_UNDER_NUMBER, number)).equals(modifiedByTag);
	}

	public boolean checkForModifiedByTextWithXpathPrefix(String prefix, String modifiedByTag)
	{
		return doesElementExist(prefix + String.format(EditorTextPageElements.MODIFIED_BY_MNEMONIC_WITH_TEXT, modifiedByTag));
	}

	public boolean checkForNoPubWithXpathPrefix(String prefix)
	{
		return doesElementExist(prefix + EditorTextPageElements.NOPUB_MNEMONIC);
	}

	public boolean checkForSourceTagWithXpathPrefix(String prefix)
	{
		return doesElementExist(prefix + EditorTextPageElements.SOURCE_TAG);
	}

	public boolean checkForEditorialNoteInTree()
	{
		return doesElementExist(EditorTreePageElements.treeElements.EDITORIAL_NOTE.getXpath());
	}

	public boolean checkForParaWiscInTree()
	{
		return doesElementExist(EditorTreePageElements.treeElements.PARA_WISC.getXpath());
	}

	public String getLabelDesignatorText()
	{
		return getElementsText(METADATA_LABEL_DESIGNATOR);
	}

	public String getTextParagraphDisplayNameUnderNumber(int number)
	{
		return getElement(String.format(EditorTextPageElements.TEXT_PARAGRAPH_LABEL_UNDER_NUMBER, number)).getAttribute("display-name");
	}

	public String getTextParagraphFullDisplayNameUnderNumber(int number)
	{
		return getElement(String.format(EditorTextPageElements.TEXT_PARAGRAPH_LABEL_UNDER_NUMBER, number)).getAttribute("full-display-name");
	}

	public boolean validateSubsectionGeneration()
	{
		String consoleOutput = getElement(EditorMessagePageElements.LOG_MESSAGE_AREA).getText();
		String mostRecent = consoleOutput.substring(consoleOutput.lastIndexOf("Subsection validation initiated"));
		return mostRecent.contains("The documents subsections are valid");
	}

	public boolean checkEditorIsReadOnly()
	{
		// TODO: need to add additional verification, because Class 'read-only' does not
		// guarantee that the area is read-only
		switchToEditorTextFrame();
		return isElementReadOnly("//body");
	}

	public void closeSpellcheckWindow()
	{
		switchToWindow(SpellcheckPageElements.PAGE_TITLE);
		click(SpellcheckPageElements.COMMIT_CHANGES_AND_CLOSE_BUTTON);
		acceptAlertNoFail();
	}

	// Removes citation markup with the provided citation string.
	
	public void deleteCitationMarkup(String citation)
	{
		waitForPageLoaded();
		switchToEditorTextFrame();
		rightClick(String.format(EditorTextPageElements.CITATION_IMAGE, citation));
		breakOutOfFrame();
		getElement(EditorTextContextMenuElements.MARKUP).sendKeys(Keys.RIGHT);
		sendEnterToElement(EditorTextContextMenuElements.DELETE_MARKUP);
	}

	public void checkAdditionalChecks(String[][] additionalChecks, String... params)
	{
		for(String[] check : additionalChecks)
		{
			String format = String.format(check[1], Arrays.stream(params).toArray());
			boolean checkRes = doesElementExist(format);
			Assertions.assertEquals(checkRes, "true".equals(check[2]), check[0]);
		}
	}

	public boolean checkForPhraseInParatext(String phrase)
	{
		return doesElementExist( String.format("//para/paratext[text()[contains(.,'%s')]]", phrase));
	}

	public void closeAndCheckInChangesAndHandleSpellcheckWindowIfAppeared()
	{
		closeAndCheckInChanges();
		if (switchToWindow(SpellcheckPageElements.PAGE_TITLE))
		{
			waitForPageLoaded();
			click(SpellcheckPageElements.COMMIT_CHANGES_AND_CLOSE_BUTTON);
		}
	}

	public boolean checkForCurrentDisplayName(String displayName)
	{
		return !driver.findElements( By.xpath(String.format(EditorTextPageElements.ENGLISH_LABEL, displayName)) ).isEmpty();
	}

	public boolean checkForCurrentFullDisplayName(String fullDisplayName)
	{
		return !driver.findElements( By.xpath(String.format(EditorTextPageElements.ENGLISH_LABEL, fullDisplayName)) ).isEmpty();
	}

	public String getHvsSerialNumber()
	{
		String text = EditorTextPageElements.hvsSerialNumber.getText();
		return text.substring(9);
	}

	public String getHvsCcdb()
	{
		String text = EditorTextPageElements.hvsCcbd.getText();
		return text.substring(5);
	}

	public String getHvsNodType()
	{
		String text = EditorTextPageElements.hvsNodeType.getText();
		return text.substring(9);
	}

	public String getHvsReporterVolume()
	{
		String text = EditorTextPageElements.hvsReporterVolume.getText();
		return text.substring(9);
	}

	public String getHvsReporterNumber()
	{
		String text = EditorTextPageElements.hvsReporterNumber.getText();
		return text.substring(10);
	}

	public String getHvsReporterPage()
	{
		String text = EditorTextPageElements.hvsReporterPage.getText();
		return text.substring(8);
	}

	public String getHvsHeadnoteNumber()
	{
		String text = EditorTextPageElements.hvsHeadnoteNumber.getText();
		return text.substring(7);
	}

	public String getHvsNeutralCitation()
	{
		String text = EditorTextPageElements.hvsNeutralCitation.getText();
		return text.substring(13);
	}

	public String getTextOfTextParagraphSourceTagUnderNumber(int number)
	{
		return getElementsText(String.format(EditorTextPageElements.TEXT_PARAGRAPH_SOURCE_TAG_UNDER_NUMBER, number));
	}

	public boolean insertGlossaryLinkIsDisplayed()
	{
		rightClick("//para/paratext");
		breakOutOfFrame();
		getElement(EditorTextContextMenuElements.MARKUP).sendKeys(Keys.ARROW_RIGHT);
		return isElementDisplayed(HierarchyMenuElements.INSERT_GLOSSARY_LINK);
	}

	public void waitForEditorToCloseAndHandleAlert()
	{
		waitForWindowGoneByTitle(EditorPageElements.PAGE_TITLE);
		waitForAlert();
	}

	public void rightClickQuery()
	{
		rightClick(EditorTextPageElements.QUERY_NOTE_TYPE_DATE);
	}

	public List<String> getMetadataListOfTheOpenedDocuments()
	{
		List<String> metadataList = getElementsTextList(BLUE_BAR);
		metadataList.remove(0);
		return metadataList;
	}
	public void openInsertTargetCite()
	{
		new Actions(driver).keyDown(Keys.CONTROL).sendKeys("l").keyUp(Keys.CONTROL).build().perform();
	}

	public void copyPasteCiteQuery()
	{
		editorTextPage().ctrlCUsingAction();
		new Actions(driver).sendKeys(Keys.END).build().perform();
		editorTextPage().ctrlVUsingAction();
	}
}