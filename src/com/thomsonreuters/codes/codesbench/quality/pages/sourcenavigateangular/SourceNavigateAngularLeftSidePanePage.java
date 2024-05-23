package com.thomsonreuters.codes.codesbench.quality.pages.sourcenavigateangular;

import com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.SourceNavigateAngularLeftSidePaneElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.SourceNavigateAngularPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import java.time.Duration;

import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.SourceNavigateAngularLeftSidePaneElements.*;
import static java.lang.String.format;

@Component
public class SourceNavigateAngularLeftSidePanePage extends BasePage
{
    private final WebDriver driver;

    @Autowired
    public SourceNavigateAngularLeftSidePanePage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, SourceNavigateAngularLeftSidePaneElements.class);
    }

    public void accessColumnsInterface()
    {
        click(columnsLeftPaneButton);
    }

    public void uncheckAllColumns()
    {
        // TODO verify this method works correctly when corresponding tests are implemented
        String checkboxStatus = getElementsAttribute(SELECT_ALL_COLUMNS_CHECKBOX_STATUS, "class");
        if (checkboxStatus.contains("indeterminate"))
        {
            new Actions(driver).doubleClick(toggleSelectAllColumns).build().perform();
        }
        else if (checkboxStatus.contains("checked"))
        {
            new Actions(driver).click(toggleSelectAllColumns).build().perform();
        }
    }

    public void filterForColumnAndSelect(String columnName)
    {
        accessColumnsInterface();
        click(COLUMN_SEARCH_FIELD);
        sendKeys(columnName);
        new Actions(driver)
                .pause(Duration.ofSeconds(1))
                .sendKeys(Keys.ENTER)
                .click(driver.findElement(By.xpath(COLUMN_SEARCH_FIELD)))
                .sendKeys(Keys.END)
                .build()
                .perform();
        for (int i = 0; i < columnName.length(); i++)
        {
            sendKeys(Keys.BACK_SPACE);
        }
        click(columnsLeftPaneButton);
    }

    public void clickFindButtonOnLeftPane()
    {
        click(findLeftPaneButton);
    }

    public void setFindValue(String fieldName, String uuid)
    {
        click(format(FIND_TEXT_FIELD_PATTERN, fieldName));
        sendKeys(uuid);
    }

    public void selectUserFromDropdown(String username)
    {
        click(assignedToUserDropdown);
        sendKeys(username);
        click(String.format(ASSIGN_USER_OPTION_DROPDOWN, username));
    }

    public void clickFindButton()
    {
        click(SourceNavigateAngularLeftSidePaneElements.findButton);
        waitForElementGone(SourceNavigateAngularPageElements.LOADING_PLATE);
    }
    
    public void setFilterSizeValue(String fieldName, String uuid)
    {
        click(fieldName);
        sendKeys(uuid);
    }

    public void accessColumnsInterface(String tabName)
    {
        switch (tabName)
        {
            case "SECTION":
                click(SECTION_COLUMNS);
                break;
            case "SECTIONGROUP":
                click(SECTION_GROUP_COLUMNS);
                break;
            case "DELTA":
                click(DELTA_COLUMNS);
                break;
            case "DELTAGROUP":
                click(DELTA_GROUP_COLUMNS);
                break;
            case "LINEAGE":
                click(LINEAGE_COLUMNS);
                break;
            default:
                click(columnsLeftPaneButton);
                break;
        }
    }

    public void clickColumnSearch(String tabName)
    {
        switch (tabName)
        {
            case "SECTION":
                click(String.format(COLUMN_SEARCH_COMMON, 8));
                break;
            case "SECTIONGROUP":
                click(String.format(COLUMN_SEARCH_COMMON, 6));
                break;
            case "DELTA":
                click(String.format(COLUMN_SEARCH_COMMON, 12));
                break;
            case "DELTAGROUP":
                click(String.format(COLUMN_SEARCH_COMMON, 10));
                break;
            case "LINEAGE":
                click(String.format(COLUMN_SEARCH_COMMON, 2));
                break;
            case "RENDITION":
                click(String.format(COLUMN_SEARCH_COMMON, 4));
                break;
            default:
                logger.information("No tab name specified");
                break;

        }
    }

    public String getColumnSearchFieldUnderSpecifiedTab(String tabName)
    {
        switch (tabName)
        {
            case "SECTION":
                return (String.format(COLUMN_SEARCH_COMMON, 8));
            case "SECTIONGROUP":
                return (String.format(COLUMN_SEARCH_COMMON, 6));
            case "DELTA":
                return (String.format(COLUMN_SEARCH_COMMON, 12));
            case "DELTAGROUP":
                return (String.format(COLUMN_SEARCH_COMMON, 10));
            case "LINEAGE":
                return (String.format(COLUMN_SEARCH_COMMON, 2));
            case "RENDITION":
                return (String.format(COLUMN_SEARCH_COMMON, 4));
            default:
                logger.information("No tab name specified");
                return "";
        }
    }

    public void filterForColumnAndSelectUnderSpecificTab(String tabName, String columnName)
    {
        accessColumnsInterface(tabName);
        clickColumnSearch(tabName);
        sendKeys(columnName);
        new Actions(driver)
                .pause(Duration.ofSeconds(1))
                .sendKeys(Keys.ENTER)
                .click(driver.findElement(By.xpath(getColumnSearchFieldUnderSpecifiedTab(tabName))))
                .sendKeys(Keys.END)
                .build()
                .perform();
        for (int i = 0; i < columnName.length(); i++)
        {
            sendKeys(Keys.BACK_SPACE);
        }
        accessColumnsInterface(tabName);
    }
}
