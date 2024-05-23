package com.thomsonreuters.codes.codesbench.quality.pages.source.navigate.popups.topicalHeadingHighlight;

import com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.popups.topicalHeadingHighlight.UserPhrasePageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class UserPhrasePage extends BasePage
{
    private WebDriver driver;

    @Autowired
    public UserPhrasePage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, UserPhrasePageElements.class);
    }

    public void clickSubmit()
    {
        sendEnterToElement(UserPhrasePageElements.submitButton);
        topicalHeadingHighlightPage().switchToTopicalHeadingHighlightWindow();
    }

    public void clickCancel()
    {
        sendEnterToElement(UserPhrasePageElements.cancelButton);
        topicalHeadingHighlightPage().switchToTopicalHeadingHighlightWindow();
    }

    public void enterPhrase(String phrase)
    {
        sendTextToTextbox(UserPhrasePageElements.userPhraseTextBox, phrase);
    }
}
