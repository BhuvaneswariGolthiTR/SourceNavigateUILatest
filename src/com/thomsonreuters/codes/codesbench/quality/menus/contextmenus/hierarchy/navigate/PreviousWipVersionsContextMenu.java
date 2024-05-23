package com.thomsonreuters.codes.codesbench.quality.menus.contextmenus.hierarchy.navigate;

import com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.hierarchy.navigate.PreviousWipVersionsContextMenuElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.popups.SetLawTrackingPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class PreviousWipVersionsContextMenu extends BasePage
{
    private WebDriver driver;

    @Autowired
    public PreviousWipVersionsContextMenu(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, PreviousWipVersionsContextMenuElements.class);
    }

    public void createHistoricalVersion()
    {
        click(PreviousWipVersionsContextMenuElements.createHistoricalVersion);
        switchToWindow(SetLawTrackingPageElements.PAGE_TITLE);
        enterTheInnerFrame();
    }

    public void viewContent()
    {
        click(PreviousWipVersionsContextMenuElements.viewContent);
    }
}