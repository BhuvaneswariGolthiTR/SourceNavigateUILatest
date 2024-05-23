package com.thomsonreuters.codes.codesbench.quality.pageelements.viewmanagament;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class ViewManagementPageElements
{	
	public static final String VIEW_MANAGEMENT_WINDOW_TITLE = "View Management";
	
	public static final String VIEW = "//div[@id='basic']//tbody[@class='yui-dt-data']//td[contains(@headers, 'ViewName')]//div[contains(text(), '%s')]";
	public static final String EXPECTED_DEFAULT_VIEW = "//tbody[@class='yui-dt-data']//td[contains(@class,'ViewName')]/div[contains(text(),'%s')]/../../td[contains(@class,'Default')]/div[contains(text(),'✔')]";
	//old xpath: //tbody[@class='yui-dt-data']//div[contains(text(), '%s')]/..//div/img

	@FindBy(how = How.ID, using = "addView-button")
	public static WebElement addViewButton;
	
	@FindBy(how = How.ID, using = "deleteView-button")
	public static WebElement deleteViewButton;
	
	@FindBy(how = How.ID, using = "editView-button")
	public static WebElement editViewButton;
	
	@FindBy(how = How.ID, using = "selectView-button")
	public static WebElement selectViewButton;
	
	@FindBy(how = How.ID, using = "setDefaultView-button")
	public static WebElement setViewAsDefaultButton;
	
	@FindBy(how = How.XPATH, using = "//button[@title='Show Calendar']/following-sibling::div[@class='yui-dt-button']/button[@class]")
	public static WebElement saveFilterBlueButton;
	
	@FindBy(how = How.XPATH, using = "//*[contains(text(),'Data error')]")
	public static WebElement dataErrorMessage;

	public static final String DATA_ERROR_MESSAGE = "//*[contains(text(),'Data error')]";
	
	@FindBy(how = How.ID, using = "showSingleDateCal")
	public static WebElement openCalender;

	public static final String VIEW_MANAGEMENT_BUTTON = "//img[@id='viewManagementButton']";
	public static final String ADD_VIEW_BUTTON = "//button[contains(text(),'Add View')]";
	public static final String DELETE_VIEW_BUTTON = "//button[contains(text(),'Delete View')]";
	public static final String OPEN_CALENDAR_IN_FILTER = "//button[@title='Show Calendar']";
	public static final String SAVE_FILTER_BLUE_BUTTON = OPEN_CALENDAR_IN_FILTER + "/following-sibling::div[@class='yui-dt-button']/button[@class]";
	public static final String OPEN_CALENDAR = "//button[@id='showSingleDateCal']";
	public static final String SUBMIT_BUTTON = "//div[@id='pageFooter']//input[@id='submitButton']";
}

