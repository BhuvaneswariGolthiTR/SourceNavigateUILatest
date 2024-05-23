package com.thomsonreuters.codes.codesbench.quality.pages.hierarchy.navigate;

import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.HierarchyPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.UpdateAlternativeCitePageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class UpdateAlternativeCitePage extends BasePage
{
    private WebDriver driver;

    @Autowired
    public UpdateAlternativeCitePage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, UpdateAlternativeCitePageElements.class);
    }

    public void setDefaultAlternateCiteKeyword(String newKeyword)
    {
        selectDropdownOption(UpdateAlternativeCitePageElements.citeKeywordDropdown,newKeyword);
    }

    public void setDefaultAlternateCiteValue(String newValue)
    {
       clearAndSendKeysToElement(UpdateAlternativeCitePageElements.defaultCiteValueTextBox,newValue);
    }

    public void clickUpdate()
    {
        UpdateAlternativeCitePageElements.updateButton.click();
        waitForWindowGoneByTitle(UpdateAlternativeCitePageElements.UPDATE_ALTERNATIVE_CITE_PAGE_TITLE);
        switchToWindow(HierarchyPageElements.PAGE_TITLE);
    }
}
