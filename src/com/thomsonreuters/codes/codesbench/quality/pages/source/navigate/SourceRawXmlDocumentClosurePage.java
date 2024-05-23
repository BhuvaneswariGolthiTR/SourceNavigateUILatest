package com.thomsonreuters.codes.codesbench.quality.pages.source.navigate;

import com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.SourceRawXmlDocumentClosurePageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.abstractPages.AbstractRawXmlDocumentClosurePage;
import com.thomsonreuters.codes.codesbench.quality.utilities.autoIT.AutoITUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class SourceRawXmlDocumentClosurePage extends AbstractRawXmlDocumentClosurePage
{
    private WebDriver driver;

    @Autowired
    public SourceRawXmlDocumentClosurePage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, SourceRawXmlDocumentClosurePageElements.class);
    }

    public void switchToDocumentClosureWindow()
    {
        switchToWindow(SourceRawXmlDocumentClosurePageElements.RAW_XML_DOCUMENT_CLOSURE_PAGE_TITLE);
    }

    public void clickCheckIn()
    {
        sendEnterToElement(SourceRawXmlDocumentClosurePageElements.checkInButton);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.THREE_SECONDS);
    }

    public void clickCancel()
    {
        click(SourceRawXmlDocumentClosurePageElements.cancelButton);
        waitForWindowGoneByTitle(SourceRawXmlDocumentClosurePageElements.RAW_XML_DOCUMENT_CLOSURE_PAGE_TITLE);
        sourceRawXmlEditorPage().switchToRawXmlEditorPage();
    }

}
