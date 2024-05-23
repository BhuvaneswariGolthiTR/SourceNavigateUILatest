package com.thomsonreuters.codes.codesbench.quality.pages.source.navigate.sectiongrouping;

import com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.sectiongrouping.SectionGroupingSectionGridPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.SectionGroupingPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class SectionGroupingFooterPage extends BasePage
{

    private WebDriver driver;

    @Autowired
    public SectionGroupingFooterPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, SectionGroupingSectionGridPageElements.class);
    }

    public void clickGroupingRemove()
    {
        click(SectionGroupingSectionGridPageElements.SECTION_DELTA_GROUPING_REMOVE_GROUP_BUTTON);
    }

    public void clickGroupingYes()
    {
        click(SectionGroupingSectionGridPageElements.YES_BUTTON);
    }

    public void clickGroupingApply()
    {
        click(SectionGroupingSectionGridPageElements.SECTION_DELTA_GROUPING_APPLY_BUTTON);

    }

    public void clickApplyAndClose()
    {
        click(SectionGroupingSectionGridPageElements.SECTION_DELTA_GROUPING_APPLY_AND_CLOSE_BUTTON);
        waitForWindowGoneByTitle(SectionGroupingPageElements.SECTION_GROUPING_PAGE_TITLE);
    }
}
