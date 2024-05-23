package com.thomsonreuters.codes.codesbench.quality.pageelements.source.bts;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class GenerateLegislativeServiceTablesPageElements
{
	public static final String TABLE_SUCCESFULLY_GENERATED_TEXT = "//li[text()='Successfully generated table ']"; 
	public static final String GENERATE_LEGISLATIVE_SERVICE_TABLES_PAGE_TITLE = "Generate Legislative Service Tables";
	public static final String GENERIC_RANGE_PLUS_BUTTON = "//span[@title='Add Range']";
	public static final String GENERIC_MINUS_RANGE_BUTTON = "//span[@title='Remove Range']"; 
	public static final String ADD_RANGE_BUTTON = "//table[@id='rangetable']/tbody//tr[%s]//span[@title='Add Range']";
	public static final String ADD_RANGE_FROM_TEXTFIELD = "//table[@id='rangetable']/tbody//tr[%s]//input[@rangetype='from']";
	public static final String ADD_RANGE_TO_TEXTFIELD = "//table[@id='rangetable']/tbody//tr[%s]//input[@rangetype='to']";
	
	@FindBy(how = How.ID, using = "pageForm:generateNonUSButton")
	public static WebElement generateButton;
	
	@FindBy(how = How.ID, using = "pageForm:viewPdfButton")
	public static WebElement viewPdgButton;
	
	@FindBy(how = How.ID, using = "pageForm:cancelButton")
	public static WebElement cancelButton;
	
	@FindBy(how = How.ID, using = "pageForm:tableNumber2")
	public static WebElement tableNumberDropDown;
	
	@FindBy(how = How.XPATH, using = "//input[@name='pageForm:pampletNumber']")
	public static WebElement pamphletNumberField;
	
	@FindBy(how = How.ID, using = "pageForm:pampletNumber")
	public static WebElement genLegServTableYearDropDown;
	
	@FindBy(how = How.XPATH, using ="//input[contains(@id,'pageForm:legislativeYear')]")
	public static WebElement legislativeYearField;
	
	@FindBy(how = How.LINK_TEXT, using = "List")
	public static WebElement legislativeTableListTab;
	
	@FindBy(how = How.LINK_TEXT, using = "Last")
	public static WebElement lastButton;
	
	@FindBy(how = How.XPATH, using = "//div[p[text()='Generating Tables...']]")
	public static WebElement tableWaitingForGeneratingTablesTextElement;

	public static final String TABLE_WAITING_FOR_GENERATING_TABLES_TEXT_ELEMENT = "//div[p[text()='Generating Tables...']]";
}
