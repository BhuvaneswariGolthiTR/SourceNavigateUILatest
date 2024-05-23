package com.thomsonreuters.codes.codesbench.quality.pages.hierarchy.navigate;

import com.thomsonreuters.codes.codesbench.quality.pageelements.abstractPageElements.RawXmlEditorPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.HierarchyPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.HierarchyRawXmlDocumentClosurePageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.SourceRawXmlDocumentClosurePageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.abstractPages.AbstractRawXmlEditorPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class HierarchyRawXmlEditorPage extends AbstractRawXmlEditorPage
{
    private WebDriver driver;

    @Autowired
    public HierarchyRawXmlEditorPage(WebDriver driver)
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
        switchToWindow(SourceRawXmlDocumentClosurePageElements.RAW_XML_DOCUMENT_CLOSURE_PAGE_TITLE);
    }

    public void clickClose()
    {
        super.clickClose();
        switchToWindow(HierarchyPageElements.PAGE_TITLE);
    }
}
