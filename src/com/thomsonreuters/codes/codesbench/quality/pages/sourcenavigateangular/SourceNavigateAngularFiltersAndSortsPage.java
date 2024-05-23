package com.thomsonreuters.codes.codesbench.quality.pages.sourcenavigateangular;

import com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.SourceNavigateAngularFiltersAndSortsPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.SourceNavigateAngularFiltersAndSortsPageElements.*;
import static java.lang.String.format;

@Component
public class SourceNavigateAngularFiltersAndSortsPage extends BasePage
{
    private WebDriver driver;

    @Autowired
    public SourceNavigateAngularFiltersAndSortsPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, SourceNavigateAngularFiltersAndSortsPageElements.class);
    }

    public void openMenuColumnHeader(String columnName)
    {
        new Actions(driver)
                .moveToElement(driver.findElement(By.xpath(format(COLUMN_FILTER_BUTTON_PATTERN, columnName))))
                .click(driver.findElement(By.xpath(format(COLUMN_FILTER_BUTTON_PATTERN, columnName))))
                .build()
                .perform();
    }
    public void openMenuColumnHeaderForTabs(String filterbutton, String columnName)
    {
        new Actions(driver)
                .moveToElement(driver.findElement(By.xpath(format(filterbutton, columnName))))
                .click(driver.findElement(By.xpath(format(filterbutton, columnName))))
                .build()
                .perform();
    }
    public void openMenuColumnHeaderForElement(WebElement elementToClick)
    {
        Actions builder = new Actions(driver);
        builder.moveToElement(elementToClick).click(elementToClick);
        builder.perform();
    }

    public void openFilterTabColumnHeader()
    {
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        //click(COLUMN_MENU_FILTERS);
    }

    public void clickCheckbox(String checkboxName)
    {
        click(format(FILTER_CHECKBOX_PATTERN, checkboxName));
    }

    public void doubleClickCheckbox(String checkboxName)
    {
        new Actions(driver)
                .doubleClick(driver.findElement(By.xpath(format(FILTER_CHECKBOX_PATTERN, checkboxName))))
                .build()
                .perform();
    }
    public void setRenditionStatus(String fieldName, String documentName)
    {
        click(fieldName);
        sendKeys(documentName);
    }

}
