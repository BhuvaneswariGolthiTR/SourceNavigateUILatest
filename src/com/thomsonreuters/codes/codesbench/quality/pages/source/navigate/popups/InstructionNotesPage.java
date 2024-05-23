package com.thomsonreuters.codes.codesbench.quality.pages.source.navigate.popups;

import javax.annotation.PostConstruct;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.popups.InstructionNotesPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;

@Component
public class InstructionNotesPage extends BasePage
{
	private WebDriver driver;

	@Autowired
	public InstructionNotesPage(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
	}

	@PostConstruct
	public void init()
	{
		PageFactory.initElements(driver, InstructionNotesPageElements.class);
	}

	public boolean switchToInstructionNotesWindow()
	{
		boolean windowAppears = switchToWindow(InstructionNotesPageElements.INSTRUCTION_NOTES_PAGE_TITLE);
		waitForPageLoaded();
		enterTheInnerFrame();
		return windowAppears;
	}

	public void clickCancel()
	{
		click(InstructionNotesPageElements.cancelButton);
		waitForWindowGoneByTitle(InstructionNotesPageElements.INSTRUCTION_NOTES_PAGE_TITLE);
	}

	public void clickSave()
	{
		click(InstructionNotesPageElements.saveButton);
		waitForWindowGoneByTitle(InstructionNotesPageElements.INSTRUCTION_NOTES_PAGE_TITLE);
	}

	public void clickOk()
	{
		click(InstructionNotesPageElements.okButton);
		waitForWindowGoneByTitle(InstructionNotesPageElements.INSTRUCTION_NOTES_PAGE_TITLE);
	}

	public void addDeltaNotes(String text)
	{
		sendTextToTextbox(InstructionNotesPageElements.instructionNotesDeltaLevel, text);
	}

	public void addRenditionNotes(String text)
	{
		sendTextToTextbox(InstructionNotesPageElements.instructionNotesRenditionLevel, text);
	}

	public void clearRenditionNotes()
	{
		click(InstructionNotesPageElements.instructionNotesRenditionLevel);
		clear(InstructionNotesPageElements.instructionNotesRenditionLevel);
	}

	public void addSourceLevelInstruction(String text)
	{
		sendTextToTextbox(InstructionNotesPageElements.instructionNotesSourceLevel, text);
	}

	public void addSectionLevelInstruction(String text)
	{
		sendTextToTextbox(InstructionNotesPageElements.instructionNotesSectionLevel, text);
	}

	public String getInstructionNoteHeader() {
		return getElementsText(InstructionNotesPageElements.instructionNotesHeader);
	}

	public boolean isRenditionNotesReadOnly()
	{
		return isElementReadOnly(InstructionNotesPageElements.INSTRUCTION_NOTES_RENDITION_LEVEL);

	}

	public boolean isSectionNotesReadOnly()
	{
		return isElementReadOnly(InstructionNotesPageElements.INSTRUCTION_NOTES_SECTION_LEVEL);
	}

	public boolean getSourceLevelInstructionNotesReadOnly()
	{
		return getElementsAttribute(InstructionNotesPageElements.instructionNotesSourceLevel, "readonly").equals("true");
	}

	public boolean getSectionLevelInstructionNotesReadOnly()
	{
		return getElementsAttribute(InstructionNotesPageElements.instructionNotesSectionLevel, "readonly").equals("true");
	}

	public boolean getDeltaLevelInstructionNotesReadOnly()
	{
		return getElementsAttribute(InstructionNotesPageElements.instructionNotesDeltaLevel, "readonly").equals("true");
	}
}
