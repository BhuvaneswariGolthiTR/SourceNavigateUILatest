package com.thomsonreuters.codes.codesbench.quality.pages.source.navigate;

import com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.*;
import com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.DeltaGroupingPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.DeltaGroupingPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.DeltaGroupingPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.List;

@Component
public class DeltaGroupingPage extends BasePage
{
    private WebDriver driver;

    @Autowired
    public DeltaGroupingPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, DeltaGroupingPageElements.class);
    }

    public void waitForDeltaGridRefresh()
    {
        waitForElementGone(DeltaGroupingPageElements.GRID_WAIT);
    }
}
