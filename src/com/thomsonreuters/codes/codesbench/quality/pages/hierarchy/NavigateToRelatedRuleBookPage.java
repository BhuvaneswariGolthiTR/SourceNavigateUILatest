package com.thomsonreuters.codes.codesbench.quality.pages.hierarchy;

import javax.annotation.PostConstruct;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.HierarchyPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;

@Component
public class NavigateToRelatedRuleBookPage extends BasePage
{
    private WebDriver driver;

    @Autowired
    public NavigateToRelatedRuleBookPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, HierarchyPageElements.class);
    }

    public void openRelatedRulebook(String contentSet)
    {
        try
        {
            click(contentSet);
        }
        catch (Exception e)
        {
            closeCurrentWindowIgnoreDialogue();
        }

    }

}

