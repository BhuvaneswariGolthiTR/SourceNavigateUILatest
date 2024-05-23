package com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.source.navigate;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class SectionContextMenuElements
{
	public static final String CONTEXT_MENU = "//div[@id='contextMenu']";
	public static final String CONTEXT_MENU_VIEW = CONTEXT_MENU + "//div[@id='view']";

	public static final String EDIT = "//div[@id='contextMenu']//a[text()='Edit']";
	public static final String EDIT_SECTION = "//div[@id='contextMenu']//a[text()='Section']";

	public static final String EDIT_SECTIONS = "//div[@id='contextMenu']//a[text()='Sections']";
	public static final String EDIT_SECTION_NOTES = "//div[@id='contextMenu']//a[text()='Section Notes']";
	public static final String EDIT_EFFECTIVE_DATE = "//div[@id='contextMenu']//a[text()='Effective Date']";
	public static final String EDIT_INTEGRATION_PROPERTIES = "//div[@id='contextMenu']//a[text()='Integration Properties']";
	public static final String EDIT_DIFFICULTY_LEVEL = "//div[@id='contextMenu']//a[text()='Difficulty Level']";

	public static final String VIEW = "//div[@id='contextMenu']//a[text()='View']";
	public static final String VIEW_SECTION = CONTEXT_MENU_VIEW + "//a[text()='Section']";
	public static final String VIEW_SECTION_NOTES = CONTEXT_MENU_VIEW + "//a[text()='Section Notes']";

	public static final String MODIFY = "//div[@id='contextMenu']//a[text()='Modify']";
	public static final String MODIFY_ASSIGN_USER = "//div[@id='contextMenu']//a[text()='Assign User']";

	public static final String TRACK = "//div[@id='contextMenu']//a[text()='Track']";
	public static final String TRACK_IMAGES_SENT_OUT = "//div[@id='contextMenu']//a[text()='Images Sent Out']";
	public static final String TRACK_IMAGES_COMPLETED = "//div[@id='contextMenu']//a[text()='Images Completed']";
	public static final String TRACK_TABULAR_REQUESTED = "//div[@id='contextMenu']//a[text()='Tabular Requested']";
	public static final String TRACK_TABULAR_STARTED = "//div[@id='contextMenu']//a[text()='Tabular Started']";
	public static final String TRACK_TABULAR_COMPLETED = "//div[@id='contextMenu']//a[text()='Tabular Completed']";

	public static final String VALIDATE = "//div[@id='contextMenu']//a[text()='Validate']";
	public static final String VALIDATE_CHECK_VALIDATIONS = "//div[@id='contextMenu']//div[@id='validate']//a[text()='Check Validations']";

	@FindBy(how = How.XPATH, using = CONTEXT_MENU + "//a[text()='View']")
	public static WebElement view;

	@FindBy(how = How.XPATH, using = CONTEXT_MENU + "//a[text()='Edit']")
	public static WebElement edit;

	@FindBy(how = How.XPATH, using = CONTEXT_MENU + "//a[text()='Modify']")
	public static WebElement modify;

	@FindBy(how = How.XPATH, using = CONTEXT_MENU + "//a[text()='Track']")
	public static WebElement track;

	@FindBy(how = How.XPATH, using = CONTEXT_MENU_VIEW + "//a[text()='Section Notes']")
	public static WebElement viewSectionNotes;
	
	@FindBy(how = How.XPATH, using = CONTEXT_MENU_VIEW + "//a[text()='Section' or text()='Sections']")
	public static WebElement viewSection;
	
	@FindBy(how = How.XPATH, using = CONTEXT_MENU + "//a[text()='Validate']")
	public static WebElement validate;

	@FindBy(how = How.XPATH, using = CONTEXT_MENU + "//a[text()='Section Properties']")
	public static WebElement sectionProperties;

	@FindBy(how = How.XPATH, using = "//div/span[contains(@id,'view') and text()='View Section(s)']")
	public static WebElement viewSections;
}

