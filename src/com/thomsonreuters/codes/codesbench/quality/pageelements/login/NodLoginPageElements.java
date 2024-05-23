package com.thomsonreuters.codes.codesbench.quality.pageelements.login;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class NodLoginPageElements
{
	public static final String PAGE_TITLE = "Enterprise Single Sign-On";
	
	public static final String INCORRECT_LOGIN_PASSWORD_MESSAGE =
			"//div[@class='ping-error' and contains(text(),'We didn't recognize the username or password you entered. Please try again.')]";
	
	@FindBy(how = How.ID, using = "username")
	public static WebElement usernameTextbox;
	
	@FindBy(how = How.ID, using = "password")
	public static WebElement passwordTextbox;

	@FindBy(how = How.ID, using = "signOnButtonSpan")
	public static WebElement loginButton;
}
