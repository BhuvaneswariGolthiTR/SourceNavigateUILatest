package com.thomsonreuters.codes.codesbench.quality.pages.login;

import com.thomsonreuters.codes.codesbench.quality.pageelements.login.LoginPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import static com.thomsonreuters.codes.codesbench.quality.pageelements.login.LoginESSOPageElements.LOGIN_ESSO_PAGE_TITLE;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.login.LoginPageElements.*;

@Component
public class LoginPage extends BasePage
{
	private WebDriver driver;

	@Autowired
    public LoginPage(WebDriver driver)
    {
    	super(driver);
        this.driver = driver;
    }

	@PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, LoginPageElements.class);
    }

    public void logIn()
    {
        //if we're on MFA authentication page (Enterprise SSO page):
        if(checkWindowIsPresented(LOGIN_ESSO_PAGE_TITLE))
        {
            loginESSOPage().logInESSOWithByPass();
        }
        //if we're on ordinary authentication page (without MFA with PingID):
        else if (checkWindowIsPresented(LOGIN_PAGE_TITLE))
        {
            setTextOfElement(user().getUsername(), usernameTextbox);
            setTextOfElement(user().getPassword(), passwordTextbox);
            sendEnterToElement(loginButton);

            if (doesElementExist(INCORRECT_LOGIN_PASSWORD_MESSAGE, 2))
            {
                Assertions.fail(String.format(
                        "User %s: The credentials entered are invalid or expired and need to be updated.", user().getUsername()));
            }
            waitForPageLoaded();
        }

    }

    public void logInAsSpecificUser(String user)
    {
        //if we're on ordinary authentication page (without MFA with PingID):
        if (checkWindowIsPresented(LOGIN_PAGE_TITLE))
        {
            setTextOfElement(specificUser(user).getUsername(), usernameTextbox);
            setTextOfElement(specificUser(user).getPassword(), passwordTextbox);
            sendEnterToElement(loginButton);

            if (doesElementExist(INCORRECT_LOGIN_PASSWORD_MESSAGE, 2))
            {
                Assertions.fail(String.format(
                        "User %s: The credentials entered are invalid or expired and need to be updated.", user().getUsername()));
            }
            waitForPageLoaded();
        }
        //if we're on MFA authentication page (Enterprise SSO page):
        else if (checkWindowIsPresented(LOGIN_ESSO_PAGE_TITLE))
        {
            loginESSOPage().logInESSOWithByPass();
        }
    }

    public String getSpecificUserTitle(String userName)
    {
        return specificUser(userName).getFirstname() + " " + specificUser(userName).getLastname();
    }
}
