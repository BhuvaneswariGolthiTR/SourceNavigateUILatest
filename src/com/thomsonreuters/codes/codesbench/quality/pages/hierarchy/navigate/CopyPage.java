package com.thomsonreuters.codes.codesbench.quality.pages.hierarchy.navigate;

import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.CopyPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.HierarchyPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class CopyPage extends BasePage
{
    private WebDriver driver;

    @Autowired
    public CopyPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, CopyPageElements.class);
    }

    public void clickQuickLoadOk()
    {
        click(CopyPageElements.quickLoadTrackingButton);
        waitForPageLoaded();
        waitForGridRefresh();
        click(CopyPageElements.okButton);
        waitForWindowGoneByTitle(CopyPageElements.COPY_PAGE_TITLE);
        switchToWindow(HierarchyPageElements.PAGE_TITLE);
        waitForGridRefresh();
        waitForPageLoaded();
    }

    public void setVolumes(String volumes)
    {
        click(String.format(CopyPageElements.VOLUMES_SELECTION_BOX_XPATH, volumes));
    }

    public void setCreateCopyOfNODs()
    {
        click(CopyPageElements.createCopyOfNODsRadioButton);
    }
}