package com.thomsonreuters.codes.codesbench.quality.pages.login;

import com.thomsonreuters.codes.codesbench.quality.pageelements.login.LoginPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import static com.thomsonreuters.codes.codesbench.quality.pageelements.login.LoginPageElements.*;

@Component
public class NodLoginPage extends BasePage
{
    private WebDriver driver;

    @Autowired
    public NodLoginPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, LoginPageElements.class);
    }

    /** WE ARE WAITING FOR SSO LOGIN TO BE FIXED
    *
    **/
    public void logIn()
    {
        //for now just use your creds
//        sendKeysToElement(usernameTextbox, user().getUsername());
//        sendKeysToElement(passwordTextbox, user().getPassword());
//        sendEnterToElement(loginButton);
//
//        boolean credentialsInvalid = doesElementExist(INCORRECT_LOGIN_PASSWORD_MESSAGE, 2);
//        if(credentialsInvalid)
//        {
//            Assertions.fail("The credentials are expired and need to be updated.\n");
//        }
        waitForPageLoaded();
    }
}
