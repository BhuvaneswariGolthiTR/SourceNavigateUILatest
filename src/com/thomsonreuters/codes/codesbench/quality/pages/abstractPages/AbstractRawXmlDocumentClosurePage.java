package com.thomsonreuters.codes.codesbench.quality.pages.abstractPages;

import com.thomsonreuters.codes.codesbench.quality.pageelements.abstractPageElements.AbstractRawXmlDocumentClosurePageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public abstract class AbstractRawXmlDocumentClosurePage extends BasePage
{
    private WebDriver driver;

    @Autowired
    public AbstractRawXmlDocumentClosurePage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, AbstractRawXmlDocumentClosurePageElements.class);
    }

    public void switchToRawXmlEditorClosurePage()
    {
        switchToWindow(AbstractRawXmlDocumentClosurePageElements.RAW_XML_DOCUMENT_CLOSURE_PAGE_TITLE);
    }
}
