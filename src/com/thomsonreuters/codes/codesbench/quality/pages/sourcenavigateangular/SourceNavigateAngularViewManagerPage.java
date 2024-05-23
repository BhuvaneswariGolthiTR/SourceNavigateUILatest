package com.thomsonreuters.codes.codesbench.quality.pages.sourcenavigateangular;

import com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.SourceNavigateAngularViewManagerPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.SourceNavigateAngularPageElements.*;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.SourceNavigateAngularViewManagerPageElements.*;
import static java.lang.String.format;

@Component
public class SourceNavigateAngularViewManagerPage extends BasePage
{
    private final WebDriver driver;

    @Autowired
    public SourceNavigateAngularViewManagerPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, SourceNavigateAngularViewManagerPageElements.class);
    }

    public void applyExistingView(String viewName)
    {
        openViewDropdown();
        selectViewFromDropdown(viewName);
        clickApplyViewButton();
        waitForElementExists(format(SPAN_CONTAINS_TEXT, viewName));
    }

    private void openViewDropdown()
    {
        click(viewDropdown);
    }

    private void selectViewFromDropdown(String viewName)
    {
        sendKeys(viewName);
        if (!viewName.equalsIgnoreCase(DEFAULT_VIEW_DROPDOWN))
        {
            click(format(VIEW_DROPDOWN_ITEM, viewName));
        }
        else
        {
            sendKeys(Keys.ENTER);
        }
    }

    private void clickApplyViewButton()
    {
        click(applyViewButton);
    }

    public void deleteView(String viewName)
    {
        openViewDropdown();
        click(String.format(Click_Created_View, viewName));
        sourceNavigateAngularFiltersAndSortsPage().click(Confirm_Button);
    }
}
