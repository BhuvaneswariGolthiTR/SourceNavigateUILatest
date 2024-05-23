package com.thomsonreuters.codes.codesbench.quality.pages.source.navigate;

import com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.SourceNavigateTabsPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class SourceNavigateTabsPage extends BasePage
{
    private WebDriver driver;

    public SourceNavigateTabsPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, SourceNavigateTabsPageElements.class);
    }

    public void goToDeltaTab()
    {
        waitForElementToBeClickable(SourceNavigateTabsPageElements.deltaTab);
        click(SourceNavigateTabsPageElements.deltaTab);
    }
    public void goToRenditionTab()
    {
        click(SourceNavigateTabsPageElements.renditionTab);
    }
    public void goToSectionTab()
    {
        click(SourceNavigateTabsPageElements.sectionTab);
        waitForPageLoaded();
    }
    public void goToSectionGroupTab()
    {
        click(SourceNavigateTabsPageElements.sectionGroupTab);
        DateAndTimeUtils.takeNap(10);
        waitForPageLoaded();
        waitForGridRefresh();
    }

    public void goToDeltaGroupTab()
    {
        click(SourceNavigateTabsPageElements.deltaGroupTab);
        DateAndTimeUtils.takeNap(10);
        waitForPageLoaded();
        waitForGridRefresh();
    }
    public void goToLineageTab()
    {
        click(SourceNavigateTabsPageElements.lineageTab);
        waitForPageLoaded();
    }
    public boolean lineageTabDisabled()
    {
        return isElementDisabled(SourceNavigateTabsPageElements.lineageTab);
    }
    public boolean sectionTabDisabled()
    {
        return isElementDisabled(SourceNavigateTabsPageElements.sectionTab);
    }
    public boolean deltaTabDisabled()
    {
        return isElementDisabled(SourceNavigateTabsPageElements.deltaTab);
    }
    public void switchToGroupingTab(String level)
    {
        click(String.format(SourceNavigateTabsPageElements.GROUPING_TAB_OF_GIVEN_LEVEL,level));
    }

    public boolean goToSourceNavigateLevelTab(String sourceNavigateTab)
    {
        switch (sourceNavigateTab)
        {
            case "Lineage":
                goToLineageTab();
                break;
            case "Section":
                goToSectionTab();
                break;
            case "Section Group":
                goToSectionGroupTab();
                break;
            case "Delta":
                goToDeltaTab();
                break;
            case "Delta Group":
                goToDeltaGroupTab();
                break;
        }
        return doesElementExist(String.format(SourceNavigateTabsPageElements.GROUPING_TAB_OF_GIVEN_LEVEL, sourceNavigateTab));
    }
}
