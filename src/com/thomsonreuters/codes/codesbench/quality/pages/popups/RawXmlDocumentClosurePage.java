package com.thomsonreuters.codes.codesbench.quality.pages.popups;

import com.thomsonreuters.codes.codesbench.quality.pageelements.popups.RawXmlDocumentClosurePageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.popups.RawXmlEditorPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class RawXmlDocumentClosurePage extends BasePage
{
    private WebDriver driver;

    @Autowired
    public RawXmlDocumentClosurePage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, RawXmlDocumentClosurePageElements.class);
    }

    public void clickCheckInButton()
    {
        RawXmlDocumentClosurePageElements.checkInButton.click();
        try {
            //switchToWindow(RawXmlEditorPageElements.PAGE_TITLE);
            waitForWindowGoneByTitle(RawXmlEditorPageElements.PAGE_TITLE);
        }
        catch (Exception ex){
            waitForWindowGoneByTitle(RawXmlEditorPageElements.PAGE_TITLE);
        }
    }

    public void clickCanada()
    {
        doubleClick(RawXmlDocumentClosurePageElements.CANADA_RADIO);
    }

    public void clickQuickLoad()
    {
        doubleClick(RawXmlDocumentClosurePageElements.quickloadRadioButton);
    }
}