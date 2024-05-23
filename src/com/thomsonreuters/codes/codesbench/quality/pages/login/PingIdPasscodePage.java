package com.thomsonreuters.codes.codesbench.quality.pages.login;

import com.thomsonreuters.codes.codesbench.quality.pageelements.login.PingIdPasscodePageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import static com.thomsonreuters.codes.codesbench.quality.pageelements.login.PingIdPasscodePageElements.*;
import static com.thomsonreuters.codes.codesbench.quality.utilities.autoIT.AutoITUtils.getPingIdDesktopPasscodeWithAutoIT;
import static java.lang.String.format;

@Component
public class PingIdPasscodePage extends BasePage
{
	private WebDriver driver;
    private static final String CHECK_PINGID_SETTINGS =
            "Please check if the Windows desktop where tests run "
                    + "is set as primary authentication device in PingID settings "
                    + "and try again.";

	@Autowired
    public PingIdPasscodePage(WebDriver driver)
    {
    	super(driver);
        this.driver = driver;
    }

	@PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, PingIdPasscodePageElements.class);
    }

    
    public void enterPingIdDesktopPasscode()
    {
        if (doesElementExist(PINGID_DESKTOP_AUTHENTICATION_MESSAGE))
        {
            String passcode = getPingIdDesktopPasscodeWithAutoIT(pingIdUnlockPIN());

            if (passcode == null)
            {
                Assertions.fail("Failed to get passcode from PingID desktop app");
            }

            passcodeInput.sendKeys(passcode);
            signOnButton.click();

            waitForPageLoaded();

            if(doesElementExist(PINGID_INVALID_PASSCODE_MESSAGE))
            {
                Assertions.fail(
                        format("User %s: Entered PingID passcode is invalid!%n%s", user().getUsername(), CHECK_PINGID_SETTINGS));
            }
        }
        else
        {
            Assertions.fail(format("User %s: %s", user().getUsername(), CHECK_PINGID_SETTINGS));
        }
    }
}
