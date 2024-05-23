package com.thomsonreuters.codes.codesbench.quality.pages.sourcenavigateangular;

import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.SourceNavigateAngularLockReportPageElements.*;
import com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.SourceNavigateAngularLockReportPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import java.time.Duration;
import org.openqa.selenium.Keys;

import static java.lang.String.format;

@Component
public class SourceNavigateAngularLockReportPage extends BasePage
{
    private final WebDriver driver;

    @Autowired
    public SourceNavigateAngularLockReportPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, SourceNavigateAngularLockReportPageElements.class);
    }

     public boolean isFilterIconExistsInColumnHeading(String columnName)
    {
        return doesElementExist(format(FILTER_ICON_ON_COLUMN_PATTERN, columnName));
    }

    public void clickOnColumnHeaderFilterButton(String columnName)
    {
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        openMenuColumnHeader(columnName);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        click(COLUMN_MENU_FILTERS);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
    }

    public void clickClearFiltersButton()
    {
        click(CLEAR_FILTERS_LOCKS);
    }

    public void makePause()
    {
        new Actions(driver).pause(Duration.ofSeconds(1)).build().perform();
    }

    public void enterInputInFilterInputBox(String text)
    {
        sendKeysToElement(FILTER_INPUT_FIELD,text);
        waitForPageLoaded();
        DateAndTimeUtils.takeNap(DateAndTimeUtils.ONE_SECOND);
    }

    public boolean verifyOnlyOneLockedRenditionExists()
    {
        return (countElements(LOCKED_ROWS)==1 && doesElementExist(String.format(TOTAL_LOCKS_NUMBER, 1)));
    }

    public void rightClickFirstLockedRendition()
    {
        waitForElement(FIRST_LOCKED_RENDITION_ROW);
        click(FIRST_LOCKED_RENDITION_ROW);
        sourceNavigateAngularLockReportPage().rightClick(FIRST_LOCKED_RENDITION_ROW);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.HALF_SECOND);
        sourceNavigateAngularLockReportPage().breakOutOfFrame();
    }

    public void openMenuColumnHeader(String columnName)
    {
        new Actions(driver)
                .moveToElement(driver.findElement(By.xpath(format(COLUMN_FILTER_BUTTON_LOCK_PATTERN, columnName))))
                .click(driver.findElement(By.xpath(format(COLUMN_FILTER_BUTTON_LOCK_PATTERN, columnName))))
                .build()
                .perform();
    }

    public void clickUnlock_ForceUnlock_TransferLock(String option)
    {
        click(String.format(SourceNavigateAngularLockReportPageElements.CONTEXT_MENU_OPTION_PARENT, option));
        waitForPageLoaded();
    }

    public boolean verifyContextMenuOptionEnabled(String xpath)
    {
        return (!sourceNavigateAngularLockReportPage().getElementsAttribute(getElement(xpath),"class").contains("disabled"));
    }

    public void clickRefreshTableData()
    {
        click(SourceNavigateAngularLockReportPageElements.REFRESH_TABLE_DATA);
       // waitForPageLoaded();
    }

    public void pressEnter()
    {
        sendKeys(Keys.ENTER);
    }

    public void openFilterTabColumnHeader()
    {
        click(COLUMN_MENU_FILTERS);
    }


}
