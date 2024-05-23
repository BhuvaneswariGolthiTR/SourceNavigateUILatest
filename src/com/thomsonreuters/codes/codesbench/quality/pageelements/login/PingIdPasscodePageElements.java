package com.thomsonreuters.codes.codesbench.quality.pageelements.login;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class PingIdPasscodePageElements
{
	public static final String PINGID_PASSCODE_PAGE_TITLE = "PingID";

	public static final String PINGID_DESKTOP_AUTHENTICATION_MESSAGE = "//div[contains(text(),'Authenticating with Desktop Windows')]";

	public static final String PINGID_AUTHENTICATED_MESSAGE = "//div[contains(text(),'Authenticated')]";
	
	public static final String PINGID_INVALID_PASSCODE_MESSAGE =
			"//div[@class='error-message show' and contains(text(),'Invalid passcode')]";
	
	@FindBy(how = How.CLASS_NAME, using = "passcode-input")
	public static WebElement passcodeInput;

	@FindBy(how = How.XPATH, using = "//input[@value='Sign On']")
	public static WebElement signOnButton;
}
