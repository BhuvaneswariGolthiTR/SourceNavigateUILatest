package com.thomsonreuters.codes.codesbench.quality.pageelements.source.bts;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class GeneratePocketPartTablesPageElements
{
	public static final String POCKET_PARTS_SUCCESS_MESSAGE = "//span[@class='messages']//li[contains(text(),'Successfully generated table')]";
	public static final String GENERATE_POCKET_PART_TABLES_TITLE_PAGE = "Generate Pocket Part Tables";
	public static final String TEXT_EDIT_BOX_XPATH = "//textarea[@name='pageForm:editingarea']";
	
	@FindBy(how = How.XPATH, using= "//input[@name='pageForm:cancelViewButton']")
	public static WebElement pocketPartsTableViewCancelButton; 
	
	@FindBy(how = How.XPATH, using = "//input[@name='pageForm:generate']")
	public static WebElement generateButton;
	
	@FindBy(how = How.XPATH, using = "//input[@name='pageForm:legislativeYear']")
	public static WebElement legislativeYear;
	
	@FindBy(how = How.XPATH, using = "//input[@name='pageForm:viewButton']")
	public static WebElement viewEditButton;
	
	@FindBy(how = How.LINK_TEXT, using = "List")
	public static WebElement listTab;
	
	@FindBy(how = How.ID, using ="pageForm:legislativeYear")
	public static WebElement legislativeYearField;
	
	@FindBy(how = How.LINK_TEXT, using ="Generate")
	public static WebElement generateTab;
}
