package com.thomsonreuters.codes.codesbench.quality.pageelements.login;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class LoginPageElements
{
	public static final String LOGIN_PAGE_TITLE = "Single Sign On (codesSSO)";
	
	public static final String INCORRECT_LOGIN_PASSWORD_MESSAGE =
			"//div[@id='status' and contains(text(),'The credentials you provided cannot be determined to be authentic.')]";
	
	@FindBy(how = How.ID, using = "username")
	public static WebElement usernameTextbox;
	
	@FindBy(how = How.ID, using = "password")
	public static WebElement passwordTextbox;

	@FindBy(how = How.NAME, using = "submit")
	public static WebElement loginButton;
}
