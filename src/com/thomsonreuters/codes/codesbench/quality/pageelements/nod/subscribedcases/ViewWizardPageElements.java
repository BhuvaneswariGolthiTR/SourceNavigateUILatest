package com.thomsonreuters.codes.codesbench.quality.pageelements.nod.subscribedcases;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class ViewWizardPageElements 
{
	public static final String VIEW_WIZARD_PAGE_TITLE = "View Wizard";
	
	public static final String VALUE_OPTION = "//select//option[@value='%s']";

	public static final String FILTER_SELECT = "(//td[contains(@headers, 'Column')]//select[@class='yui-dt-dropdown'])[last()]";
	
	public static final String OPTION_COLUMN = "(//td[contains(@headers, 'Column')]//select[@class='yui-dt-dropdown']//option[text()='%s'])[last()]";
	
	public static final String SAVE_BUTTON = "//div[contains(@id, 'yui-dropdownceditor') and not(contains(@style, 'display:'))]//button[contains(text(),'Save')]";

	public static final String VIEW_WIZARD_PANE = "//div[contains(@style, 'bold') and text()='%s']";

	public static final String VIEW_WIZARD_NON_SELECTED_COLUMNS_LIST = "//div[@id='selectionOfColumns']//select[@class='switchlist']//option[@value='%s']";

	public static final String VIEW_WIZARD_SELECTED_COLUMNS_LIST_FIRST_ELEMENT = "//div[@id= 'selectionOfColumns']//select[contains(@name, 'selectedColumns')]/option";

	@FindBy(how = How.ID, using = "next-button")
	public static WebElement nextButton;

	@FindBy(how = How.XPATH, using = "//button[contains(text(), 'Back')]")
	public static WebElement backButton;
	
	@FindBy(how = How.XPATH, using = "//button[contains(text(),'Add Sort')]")
	public static WebElement addSortButton;
	
	@FindBy(how = How.XPATH, using = "(//td[contains(@headers, 'Column')]//select[@class='yui-dt-dropdown'])[last()]")
	public static WebElement filterSelect;
	
	@FindBy(how = How.XPATH, using = "//td[contains(@headers, 'Order')]//select[@class='yui-dt-dropdown']")
	public static WebElement sortSelect;
	
	//@FindBy(how = How.XPATH, using = "//button[contains(text(),'Add Filter')]")
	@FindBy(how = How.ID, using = "addrow-button")
	public static WebElement addFilterButton;
	
	@FindBy(how = How.XPATH, using = "(//td[contains(@headers, 'Selection')])[last()]")
	public static WebElement optionSelection;
	
	@FindBy(how = How.XPATH, using = "//input[@class='switchlistButton' and @value='>>']")
	public static WebElement addColumnsToSelectedColumnsTableButton;

	@FindBy(how = How.XPATH, using = "//input[@class='switchlistButton' and @value='<<']")
	public static WebElement removeColumnsToSelectedColumnsTableButton;

	@FindBy(how = How.XPATH, using = "//input[@name='pageForm:viewVisibilityRadio' and @value='Public']")
	public static WebElement viewVisibilityPublic;
	
	@FindBy(how = How.ID, using = "pageForm:saveCommand")
	public static WebElement saveViewButton;
	
	@FindBy(how = How.XPATH, using = "//button[@title='Show Calendar']")
	public static WebElement openCalendarInFilter;
	
	@FindBy(how = How.ID, using = "showSingleDateCal")
	public static WebElement openCalender;
	
	@FindBy(how = How.XPATH, using = "//div[@id='pageFooter']//input[@id='submitButton']")
	public static WebElement submitButton;
	
	@FindBy(how = How.ID, using = "pageForm:saveName")
	public static WebElement viewNameInput;
}
