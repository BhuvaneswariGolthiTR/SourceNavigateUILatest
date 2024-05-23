package com.thomsonreuters.codes.codesbench.quality.pages.source.navigate.deltagrouping;

import com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.deltagrouping.DeltaGroupingGroupGridPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class DeltaGroupingGroupGridPage  extends BasePage
{
    private WebDriver driver;

    @Autowired
    public DeltaGroupingGroupGridPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, DeltaGroupingGroupGridPageElements.class);
    }

    public boolean doesGroupExist(String groupName)
    {
        return isElementDisplayed(String.format(DeltaGroupingGroupGridPageElements.DELTA_GROUPING_GROUP_WITH_GIVEN_NAME_XPATH, groupName));
    }

    public void clickGroup(String groupName)
    {
        doubleClick(String.format(DeltaGroupingGroupGridPageElements.DELTA_GROUPING_GROUP_WITH_GIVEN_NAME_XPATH, groupName));
    }
}
