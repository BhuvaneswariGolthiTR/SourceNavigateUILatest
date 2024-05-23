package com.thomsonreuters.codes.codesbench.quality.pages.hierarchy.navigate;

import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.FindTemplatesPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.HierarchyPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class FindTemplatesPage extends BasePage
{
    private WebDriver driver;

    @Autowired
    public FindTemplatesPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, FindTemplatesPageElements.class);
    }

    public void enterNodeValue(String searchValue)
    {
        sendKeysToElement(FindTemplatesPageElements.searchField,searchValue);
    }

    public void clickGoButton()
    {
        waitForElement(FindTemplatesPageElements.goButton);
        sendEnterToElement(FindTemplatesPageElements.goButton);
        waitForWindowGoneByTitle(FindTemplatesPageElements.FIND_TEMPLATES_PAGE_TITLE);
        switchToWindow(HierarchyPageElements.PAGE_TITLE);
    }
}
