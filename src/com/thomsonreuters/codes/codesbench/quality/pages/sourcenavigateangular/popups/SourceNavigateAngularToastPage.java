package com.thomsonreuters.codes.codesbench.quality.pages.sourcenavigateangular.popups;

import com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.popups.SourceNavigateAngularToastPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.popups.SourceNavigateAngularToastPageElements.*;

@Component
public class SourceNavigateAngularToastPage extends BasePage
{
    private WebDriver driver;

    @Autowired
    public SourceNavigateAngularToastPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, SourceNavigateAngularToastPageElements.class);
    }

    public void clickXButton()
    {
        click(TOAST_BODY_CLOSE);
        waitForElementGone(TOAST_BODY);
    }

    public String getToastPopupText()
    {
        waitForElementExists(TOAST_BODY);
        return getElementsText(TOAST_BODY_TEXT);
    }
}
