package com.thomsonreuters.codes.codesbench.quality.menuelements.mainmenuelements;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class HomeMenuElements extends CommonMenuElements
{
    public static final String GENERIC_HOME_XPATH = MAIN_NAVIGATOR_MENU_XPATH + "//div[@id='baseNavBarhome']";
    public static final String GENERIC_USER_PREFERENCES = GENERIC_HOME_XPATH + "//div[@id='homeuserPreferences']";

	@FindBy(how = How.XPATH, using = MAIN_NAVIGATOR_MENU_XPATH + "//a[text()='Home']")
	public static WebElement home;

	@FindBy(how = How.XPATH, using = GENERIC_HOME_XPATH + "//a[text()='My Home Page']")
    public static WebElement myHomePage;

    @FindBy(how = How.XPATH, using = GENERIC_USER_PREFERENCES + "//a[text()='Content Sets']")
	public static WebElement contentSets;

	@FindBy(how = How.XPATH, using = GENERIC_HOME_XPATH + "//a[text()='User Preferences']")
	public static WebElement userPreferences;
	
	@FindBy(how = How.XPATH, using = GENERIC_USER_PREFERENCES + "//a[text()='Properties']")
	public static WebElement properties;

	public static final String HOME_MENU_CONTENT_PREFERENCES_XPATH = "//ul[@class='first-of-type']//a[text()='Content Preferences']";

	@FindBy(how = How.XPATH, using = HOME_MENU_CONTENT_PREFERENCES_XPATH)
	public static WebElement contentPreferences;

	@FindBy(how = How.XPATH, using = GENERIC_USER_PREFERENCES + "//a[text()='Security']")
	public static WebElement security;

	public static final String HOME_MENU_RELATED_RULE_BOOK_PREFERENCES_XPATH = "//ul[@class='first-of-type']//a[text()='Related Rule Book Preferences']";
	
	@FindBy(how = How.XPATH, using = HOME_MENU_RELATED_RULE_BOOK_PREFERENCES_XPATH)
	public static WebElement relatedRuleBookPreferences;
	
}
