package com.thomsonreuters.codes.codesbench.quality.pages.source.navigate.popups;

import com.thomsonreuters.codes.codesbench.quality.pageelements.abstractPageElements.PropertiesPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.abstractPages.AbstractPropertiesPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class DeltaPropertiesPage extends AbstractPropertiesPage
{
    WebDriver driver;

    @Autowired
    public DeltaPropertiesPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, PropertiesPageElements.class);
    }

    public boolean switchToDeltaPropertiesWindow()
    {
        boolean windowAppears = switchToWindow(PropertiesPageElements.DELTA_PROPERTIES_PAGE_TITLE);
        waitForPageLoaded();
        enterTheInnerFrame();
        return windowAppears;
    }

    public void clickCancel()
    {
        super.clickCancel();
        waitForWindowGoneByTitle(PropertiesPageElements.DELTA_PROPERTIES_PAGE_TITLE);
        sourcePage().switchToDeltaNavigatePage();
        waitForPageLoaded();
    }

    public void clickSave()
    {
        super.clickSave();
        waitForWindowGoneByTitle(PropertiesPageElements.DELTA_PROPERTIES_PAGE_TITLE);
        sourcePage().switchToDeltaNavigatePage();
        waitForPageLoaded();
    }
}
