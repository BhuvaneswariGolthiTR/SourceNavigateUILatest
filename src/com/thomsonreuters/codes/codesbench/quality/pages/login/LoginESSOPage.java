package com.thomsonreuters.codes.codesbench.quality.pages.login;

import com.thomsonreuters.codes.codesbench.quality.pageelements.login.LoginESSOPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class LoginESSOPage extends BasePage
{
	private WebDriver driver;

	@Autowired
    public LoginESSOPage(WebDriver driver)
    {
    	super(driver);
        this.driver = driver;
    }

	@PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, LoginESSOPageElements.class);
    }

    
    public void logInESSOWithByPass()
    {
        String currentUrlWithByPassParameter = getUrl() + "&bypassmfacaptcha=true";
        driver.get(currentUrlWithByPassParameter);
        clearAndSendKeysToElement(LoginESSOPageElements.usernameTextbox, user().getUsername());
        click(LoginESSOPageElements.nextButton);
        waitForElement(LoginESSOPageElements.passwordTextbox);
        clearAndSendKeysToElement(LoginESSOPageElements.passwordTextbox, user().getPassword());
        click(LoginESSOPageElements.signOnButton);
        waitForPageLoaded();
    }
}
