package com.thomsonreuters.codes.codesbench.quality.pages.source.navigate.sectiongrouping;

import com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.sectiongrouping.SectionGroupingNewGroupPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class SectionGroupingNewGroupPage extends BasePage
{
    private WebDriver driver;

    @Autowired
    public SectionGroupingNewGroupPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, SectionGroupingNewGroupPageElements.class);
    }

    public void setGroupName(String groupName)
    {
        sendKeysToElement(SectionGroupingNewGroupPageElements.newGroupTitle, groupName);
    }

    public void clickSave()
    {
        click(SectionGroupingNewGroupPageElements.saveButton);
    }
}