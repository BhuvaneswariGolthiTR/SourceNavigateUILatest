package com.thomsonreuters.codes.codesbench.quality.pages.source.navigate;

import com.thomsonreuters.codes.codesbench.quality.pageelements.abstractPageElements.RawXmlEditorPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.abstractPages.AbstractRawXmlEditorPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class SourceRawXmlEditorPage extends AbstractRawXmlEditorPage
{
    private WebDriver driver;

    @Autowired
    public SourceRawXmlEditorPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, RawXmlEditorPageElements.class);
    }

    public void clickSave()
    {
        click(RawXmlEditorPageElements.saveButton);
        sourceRawXmlDocumentClosurePage().switchToDocumentClosureWindow();
    }

    public void clickClose()
    {
        super.clickClose();
        sourcePage().switchToSourceNavigatePage();
        waitForGridRefresh();
    }
}
