
package com.thomsonreuters.codes.codesbench.quality.pages.home;

import javax.annotation.PostConstruct;

import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thomsonreuters.codes.codesbench.quality.pageelements.home.ContentSetPublishingConfigurationPageElements;

@Component
public class ContentSetPublishingConfigurationPage extends BasePage
//6-30-2020: placement of this class?
{
    private WebDriver driver;

    @Autowired
    public ContentSetPublishingConfigurationPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, ContentSetPublishingConfigurationPageElements.class);
    }

    public boolean verifyContentSetHasPublishingEnabled(String contentSet)
    {
        return isElementDisplayed(String.format(ContentSetPublishingConfigurationPageElements.ENABLED_CONTENT_SET_XPATH, contentSet));
    }

    public void clickPublishingDisabledContentSet(String contentSet)
    {
        click(String.format(ContentSetPublishingConfigurationPageElements.DISABLED_CONTENT_SET_XPATH, contentSet));
    }

    public void clickPublishingEnabledContentSet(String contentSet)
    {
        click(String.format(ContentSetPublishingConfigurationPageElements.ENABLED_CONTENT_SET_XPATH, contentSet));
    }

    public void clickRightArrowButton()
    {
        click(ContentSetPublishingConfigurationPageElements.rightArrowButton);
        waitForPageLoaded();
    }

    public void clickLeftArrow()
    {
        click(ContentSetPublishingConfigurationPageElements.leftArrowButton);
        waitForPageLoaded();
    }

    public void clickSubmitButton()
    {
        click(ContentSetPublishingConfigurationPageElements.submitButton);
        waitForPageLoaded();
    }
}
