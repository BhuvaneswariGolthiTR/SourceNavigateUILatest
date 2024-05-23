package com.thomsonreuters.codes.codesbench.quality.pages.source.navigate.popups.topicalHeadingHighlight;

import com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.popups.topicalHeadingHighlight.MarkIndexEntryPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class MarkIndexEntryPage extends BasePage
{
    private WebDriver driver;

    @Autowired
    public MarkIndexEntryPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, MarkIndexEntryPageElements.class);
    }

    public void clickNo()
    {
        sendEnterToElement(MarkIndexEntryPageElements.noButton);
        sourcePage().switchToSourceNavigatePage();
    }

    public void clickCancel()
    {
        sendEnterToElement(MarkIndexEntryPageElements.cancelButton);
        topicalHeadingHighlightPage().switchToTopicalHeadingHighlightWindow();
    }
}
