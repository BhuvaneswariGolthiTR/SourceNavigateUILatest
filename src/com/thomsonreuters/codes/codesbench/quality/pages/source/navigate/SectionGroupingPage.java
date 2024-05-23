package com.thomsonreuters.codes.codesbench.quality.pages.source.navigate;

import com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.SectionGroupingPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class SectionGroupingPage extends BasePage
{
    private WebDriver driver;

    @Autowired
    public SectionGroupingPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, SectionGroupingPageElements.class);
    }

    public void waitForSectionGridRefresh()
    {
        waitForElementGone(SectionGroupingPageElements.GRID_WAIT);
    }

    public void switchToSectionGroupingPage()
    {
        switchToWindow(SectionGroupingPageElements.SECTION_GROUPING_PAGE_TITLE);
    }
}