package com.thomsonreuters.codes.codesbench.quality.pages.source.navigate.deltagrouping;

import com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.deltagrouping.DeltaGroupingToolsPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class DeltaGroupingToolsPage extends BasePage
{
    private WebDriver driver;

    @Autowired
    public DeltaGroupingToolsPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, DeltaGroupingToolsPageElements.class);
    }


    public void clearFilters()
    {
        click(DeltaGroupingToolsPageElements.clearButton);
        click(DeltaGroupingToolsPageElements.clearFilters);
    }

    public void clearSort()
    {
        click(DeltaGroupingToolsPageElements.clearButton);
        click(DeltaGroupingToolsPageElements.clearSort);
    }


    public void clickApply()
    {
        click(DeltaGroupingToolsPageElements.applyButton);
    }

    public void clickApplyAndClose()
    {
        click(DeltaGroupingToolsPageElements.applyAndCloseButton);
    }

    public void createGroup(String groupName)
    {
        click(DeltaGroupingToolsPageElements.newGroup);
        deltaGroupingNewGroupPage().setGroupName(groupName);
        deltaGroupingNewGroupPage().clickSave();
    }

    public void removeGroup(String groupName)
    {
        if(sourceNavigateGridPage().doesDeltaGroupingGroupExist(groupName))
        {
            sourceNavigateGridPage().clickDeltaGroupingGroup(groupName);
            click(DeltaGroupingToolsPageElements.removeGroup);
            click(DeltaGroupingToolsPageElements.yesButton);
        }
    }
}
