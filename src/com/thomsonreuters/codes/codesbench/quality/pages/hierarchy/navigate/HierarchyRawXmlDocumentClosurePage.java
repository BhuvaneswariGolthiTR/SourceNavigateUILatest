package com.thomsonreuters.codes.codesbench.quality.pages.hierarchy.navigate;

import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.HierarchyPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.HierarchyRawXmlDocumentClosurePageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.abstractPageElements.RawXmlEditorPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import com.thomsonreuters.codes.codesbench.quality.pages.abstractPages.AbstractRawXmlDocumentClosurePage;
import com.thomsonreuters.codes.codesbench.quality.utilities.autoIT.AutoITUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class HierarchyRawXmlDocumentClosurePage extends AbstractRawXmlDocumentClosurePage
{
    private WebDriver driver;

    @Autowired
    public HierarchyRawXmlDocumentClosurePage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, HierarchyRawXmlDocumentClosurePageElements.class);
    }

    public void clickCheckInButton()
    {
        click(HierarchyRawXmlDocumentClosurePageElements.checkInButton);
        AutoITUtils.clickSaveOnIEPopupRawXmlEditorWithAutoIT();
        switchToWindow(HierarchyPageElements.PAGE_TITLE);
        waitForWindowGoneByTitle(RawXmlEditorPageElements.RAW_XML_EDITOR_TITLE_PAGE);
    }

    public void clickQuickLoadRadioButton()
    {
        click(HierarchyRawXmlDocumentClosurePageElements.quickLoadRadioButton);
    }
}