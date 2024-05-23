package com.thomsonreuters.codes.codesbench.quality.pages.source.navigate.sectiongrouping;

import com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.sectiongrouping.SectionGroupingToolsPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class SectionGroupingToolsPage extends BasePage
{
    private WebDriver driver;

    @Autowired
    public SectionGroupingToolsPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, SectionGroupingToolsPageElements.class);
    }


    public void clearFilters()
    {
        click(SectionGroupingToolsPageElements.clearButton);
        click(SectionGroupingToolsPageElements.clearFilters);
    }

    public void clearSort()
    {
        click(SectionGroupingToolsPageElements.clearButton);
        click(SectionGroupingToolsPageElements.clearSort);
    }


    public void clickApply()
    {
        click(SectionGroupingToolsPageElements.applyButton);
    }

    public void clickApplyAndClose()
    {
        click(SectionGroupingToolsPageElements.applyAndCloseButton);
    }

    public void createGroup(String groupName)
    {
        click(SectionGroupingToolsPageElements.newGroup);
        sectionGroupingNewGroupPage().setGroupName(groupName);
        sectionGroupingNewGroupPage().clickSave();
    }

    public void removeGroup(String groupName)
    {
        if(sourceNavigateGridPage().doesSectionGroupingGroupExist(groupName))
        {
            sourceNavigateGridPage().clickSectionGroupingGroup(groupName);
            click(SectionGroupingToolsPageElements.removeGroup);
            click(SectionGroupingToolsPageElements.yesButton);
        }
    }
}
