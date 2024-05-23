package com.thomsonreuters.codes.codesbench.quality.pageelements.home.userpreferences;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class UserPropertiesPageElements
{
	public static final String USER_PROPERTIES_PAGE_TITLE = "User Properties";
	public static final String NOD_MERGE_SETTINGS = "//legend[text()='NOD Merge Settings']";
	public static final String SIGN_OFF = "//a[text()='Sign Off']";
	public static final String NOD_UNREPORTED_FILTER = "//legend[text()='NOD Grid Display Settings']/../table//span[text()='Display Unreported Subscribed Cases:']";

	public static final String NOD_SOURCE_TAG_SETTINGS = "//legend[text()='NOD Source Tag Settings']";

	@FindBy(how = How.XPATH, using = NOD_SOURCE_TAG_SETTINGS)
	public static WebElement nodSourceTagSettings;

	@FindBy(how = How.XPATH, using = "//legend[text()='NOD Grid Display Settings']")
	public static WebElement nodGridDisplaySettings;

	@FindBy(how = How.XPATH, using = "//div[@id='breadcrumbArea']//a[text()='Home']")
	public static WebElement breadcrumbHome;
	
	@FindBy(how = How.XPATH, using = "//select[@id='pageForm:resultListSettingsSelection']")
	public static WebElement defaultRowsPerPageDropDown;
	
	@FindBy(how = How.ID, using = "pageForm:saveButton")
	public static WebElement saveButton;
	
	@FindBy(how = How.ID, using = "pageForm:cancelButton")
	public static WebElement cancelButton;
}
