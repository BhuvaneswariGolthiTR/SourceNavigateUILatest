package com.thomsonreuters.codes.codesbench.quality.menus.contextmenus.hierarchy.reports;

import com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.hierarchy.reports.ValidationFlagReportContextMenuElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.stereotype.Component;

@Component
public class ValidationFlagReportContextMenu extends BasePage
{
    private WebDriver driver;

    public ValidationFlagReportContextMenu (WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    public void init()
    {
        PageFactory.initElements(driver, ValidationFlagReportContextMenuElements.class);
    }

    public void clearWarningFlag()
    {
        click(ValidationFlagReportContextMenuElements.clearWarningFlag);
        waitForPageLoaded();
    }
}
