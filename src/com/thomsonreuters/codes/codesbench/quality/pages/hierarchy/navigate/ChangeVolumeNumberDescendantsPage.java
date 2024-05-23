package com.thomsonreuters.codes.codesbench.quality.pages.hierarchy.navigate;

import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.ChangeVolumeNumberDescendantsPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.HierarchyPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class ChangeVolumeNumberDescendantsPage extends BasePage
{
    private WebDriver driver;

    @Autowired
    public ChangeVolumeNumberDescendantsPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, ChangeVolumeNumberDescendantsPageElements.class);
    }

    public void clickQuickLoadOk()
    {
        click(ChangeVolumeNumberDescendantsPageElements.quickLoadTrackingButton);
        waitForPageLoaded();
        click(ChangeVolumeNumberDescendantsPageElements.okButton);
        waitForWindowGoneByTitle(ChangeVolumeNumberDescendantsPageElements.CHANGE_VOLUME_NUMBER_DESCENDANTS_PAGE_TITLE);
        switchToWindow(HierarchyPageElements.PAGE_TITLE);
    }

    public void setVolumesDropdownTo(String newVolumesNumber)
    {
        selectDropdownOption(ChangeVolumeNumberDescendantsPageElements.volumeNumberDropdownMenu,newVolumesNumber);
    }
}