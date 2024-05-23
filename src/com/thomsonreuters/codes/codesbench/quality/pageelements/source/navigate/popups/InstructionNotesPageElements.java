package com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.popups;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class InstructionNotesPageElements
{
	public static final String INSTRUCTION_NOTES_PAGE_TITLE = "Instruction Notes";
	//the String xpaths are to check if the elements are read only
    public static final String INSTRUCTION_NOTES_SECTION_LEVEL = "//textarea[@id='pageForm:sectionLevelTextArea']";
    public static final String INSTRUCTION_NOTES_RENDITION_LEVEL = "//textarea[@id='pageForm:renditionLeveLTextArea']";

	@FindBy(how = How.ID, using = "pageHeader")
	public static WebElement instructionNotesHeader;

    @FindBy(how = How.ID, using = "pageForm:sourceLeveLTextArea")
	public static WebElement instructionNotesSourceLevel;

	@FindBy(how = How.ID, using = "pageForm:sectionLevelTextArea")
	public static WebElement instructionNotesSectionLevel;

	@FindBy(how = How.ID, using = "pageForm:deltaLevelTextArea")
	public static WebElement instructionNotesDeltaLevel;

	@FindBy(how = How.ID, using = "pageForm:renditionLeveLTextArea")
	public static WebElement instructionNotesRenditionLevel;

	@FindBy(how = How.ID, using = "pageForm:okButton")
	public static WebElement okButton;

	@FindBy(how = How.ID, using = "pageForm:cancelButton")
	public static WebElement cancelButton;

	@FindBy(how = How.ID, using = "pageForm:saveButton")
	public static WebElement saveButton;
}
