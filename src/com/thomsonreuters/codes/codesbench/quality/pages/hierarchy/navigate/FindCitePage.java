package com.thomsonreuters.codes.codesbench.quality.pages.hierarchy.navigate;

import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.FindCitePageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.HierarchyTreePageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class FindCitePage extends BasePage
{
    private WebDriver driver;

    @Autowired
    public FindCitePage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, FindCitePageElements.class);
    }

    public void setKeyword(String keyword)
    {
        selectDropdownOption(FindCitePageElements.findCiteFirstKeywordDropdown,keyword);
    }

    public void setValue(String value)
    {
        sendKeysToElement(FindCitePageElements.findCiteFirstValueInput,value);
    }

    public void clickFind()
    {
        click(FindCitePageElements.findCiteFindButton);
    }
}