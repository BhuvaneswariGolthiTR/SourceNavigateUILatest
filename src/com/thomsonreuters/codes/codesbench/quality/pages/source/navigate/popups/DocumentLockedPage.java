package com.thomsonreuters.codes.codesbench.quality.pages.source.navigate.popups;


import com.thomsonreuters.codes.codesbench.quality.pageelements.CommonPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.popups.DocumentLockedPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class DocumentLockedPage extends BasePage
{
    private WebDriver driver;

    @Autowired
    public DocumentLockedPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, DocumentLockedPageElements.class);
    }

    public boolean switchToDocumentLockedPage()
    {
        boolean pageAppeared = switchToWindow(DocumentLockedPageElements.PAGE_TITLE);
        enterTheInnerFrame();
        return pageAppeared;
    }

    public void clickCancel()
    {
        click(CommonPageElements.CANCEL_BUTTON);
    }
}
