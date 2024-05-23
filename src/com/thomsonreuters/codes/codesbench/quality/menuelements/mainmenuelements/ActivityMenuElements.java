package com.thomsonreuters.codes.codesbench.quality.menuelements.mainmenuelements;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class ActivityMenuElements extends CommonMenuElements
{
	public static final String ACTIVITY_MENU_XPATH = "//ul[@class='first-of-type']//a[text()='Activity']";
	
	// Activity Menu
	@FindBy(how = How.LINK_TEXT, using = "Activity")
	public static WebElement activity;
	
	// Activity -> User Log
	@FindBy(how = How.LINK_TEXT, using = "User Log")
	public static WebElement userLog;
}
