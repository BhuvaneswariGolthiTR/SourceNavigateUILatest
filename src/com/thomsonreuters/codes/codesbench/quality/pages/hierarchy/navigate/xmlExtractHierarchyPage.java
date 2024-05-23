package com.thomsonreuters.codes.codesbench.quality.pages.hierarchy.navigate;

import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.navigate.popups.XmlExtractPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class xmlExtractHierarchyPage extends BasePage
{
    private WebDriver driver;

    @Autowired
    public xmlExtractHierarchyPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, XmlExtractPageElements.class);
    }

    public void setFileName(String fileName)
    {
        clearAndSendKeysToElement(XmlExtractPageElements.fileNameInput, fileName);
    }

    public void clickSubmit()
    {
        click(XmlExtractPageElements.submitButton);
    }

    public void clickClose()
    {
        click(XmlExtractPageElements.close);
        waitForWindowGoneByTitle(XmlExtractPageElements.XML_EXTRACT_PAGE_TITLE);
    }
}
