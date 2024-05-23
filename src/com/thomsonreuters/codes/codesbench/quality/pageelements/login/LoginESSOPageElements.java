package com.thomsonreuters.codes.codesbench.quality.pageelements.login;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class LoginESSOPageElements
{
	public static final String LOGIN_ESSO_PAGE_TITLE = "Enterprise Single Sign-On";
	
	@FindBy(how = How.XPATH, using = "//input[@name='subject']")
	public static WebElement usernameTextbox;

	@FindBy(how = How.XPATH, using = "//div[@id='postButton']/a")
	public static WebElement nextButton;
	
	@FindBy(how = How.XPATH, using = "//input[@id='password']")
	public static WebElement passwordTextbox;

	@FindBy(how = How.XPATH, using = "//span[@id='signOnButtonSpan']/a")
	public static WebElement signOnButton;
}
