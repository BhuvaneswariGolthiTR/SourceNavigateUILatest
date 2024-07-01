package com.thomsonreuters.codes.codesbench.quality.pages.sourcenavigateangular;

import com.thomsonreuters.codes.codesbench.quality.pageelements.nod.subscribedcases.ViewWizardPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.SourceNavigateAngularPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.workflowreportingsystem.workflowsearch.WorkflowSearchPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.SourceNavigateAngularContextMenuItemsPageElements.*;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.SourceNavigateAngularFiltersAndSortsPageElements.COLUMN_MENU_FILTERS;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.SourceNavigateAngularPageElements.FIRST_RENDITION_ROW;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.SourceNavigateAngularPageElements.*;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.tools.workflowreportingsystem.workflowsearch.WorkflowSearchPageElements.WORKFLOW_REPORTING_SYSTEM;
import static java.lang.String.format;

@Component
public class SourceNavigateAngularPage extends BasePage
{
    private final WebDriver driver;

    @Autowired
    public SourceNavigateAngularPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, SourceNavigateAngularPageElements.class);
    }

    public void goToSourcePage()
    {
        openPageWithUrl(format(urls().getSourceAngularPageUrl(), environmentTag),
                SourceNavigateAngularPageElements.PAGE_TITLE);
        waitForPageLoaded();
    }

    public void loginAsSpecificUser(String specificUserName)
    {
        //Close the current session
        driver().get("http://uat.magellan3.int.westgroup.com:9248/sourceNavigateUi/logout");
        driver().manage().deleteAllCookies();

        // Login as risk user
        driver().get(format(urls().getSourceAngularPageUrl(), environmentTag));
        sourceNavigateAngularPage().goToSourcePage();

        loginPage().logInAsSpecificUser(specificUserName);
    }

    public int getTotalDocumentsNumber(WebElement totalDocumentsNumber)
    {
        return Integer.parseInt(getElementsText(totalDocumentsNumber).replaceAll("[^0-9]", ""));
    }

    public int getNumberOfRowsDisplayedInGrid(String gridContainer)
    {
        return driver.findElements(By.xpath(gridContainer)).size();
    }

    public void clickRefreshTableData()
    {
        click(SourceNavigateAngularPageElements.REFRESH_TABLE_DATA);
        waitForPageLoaded();
    }
    public void clickRefreshSectionTableData()
    {
        click(SourceNavigateAngularPageElements.File_Refresh_SectionTableData);
        waitForPageLoaded();
    }

    public boolean isFilterIconExistsInColumnHeading(String columnName)
    {
        return doesElementExist(format(FILTER_ICON_ON_COLUMN_PATTERN, columnName));
    }

    public List<String> getCellValue(String gridContainer, String columnCell, String columnName)
    {
        List<String> columnValuesList = new ArrayList<>();

        for (int i = 0; i < getNumberOfRowsDisplayedInGrid(gridContainer); i++)
        {
            columnValuesList.add(getElementsText(format(columnCell, i, columnName)));
        }
        return columnValuesList;
    }

    public List<String> getCellValue(int numberOfDocuments, String columnCell, String columnName)
    {
        List<String> columnValuesList = new ArrayList<>();

        for (int i = 0; i < numberOfDocuments; i++)
        {
            columnValuesList.add(getElementsText(format(columnCell, i, columnName)));
        }
        return columnValuesList;
    }

    public String getActiveViewName(String activeViewName)
    {
        return getElementsText(activeViewName);
    }

    public void scrollPageHorizontallyShiftEnd(String firstRow)
    {
        click(firstRow);
//        new Actions(driver)
        Actions action = new Actions(driver);
               action.keyDown(Keys.SHIFT)
                .sendKeys(Keys.END)
                .build()
                .perform();
        waitForElementGone(LOADING_PLATE);
    }

    public void scrollPageToTopCtrlHome()
    {
        new Actions(driver)
                .keyDown(Keys.CONTROL)
                .sendKeys(Keys.HOME)
                .build()
                .perform();
        waitForElementGone(LOADING_PLATE);
    }

    public void openRenditionsContextMenu()
    {
        click(FIRST_RENDITION_ROW);
        rightClickRenditions();
    }

    public void openRenditionsContextMenu(int numberOfRenditions)
    {
        Actions action = new Actions(driver).keyDown(Keys.LEFT_CONTROL);
        for (int i = 0; i < numberOfRenditions; i++)
        {
            action.click(driver.findElement(By.xpath(format(RENDITION_ROW_PATTERN, i))));
        }
        action.keyUp(Keys.LEFT_CONTROL).build().perform();

        rightClickRenditions();
    }

    public void rightClickRenditions()
    {
        rightClick(FIRST_RENDITION_ROW);
        breakOutOfFrame();
    }

    public void clickContextMenuItem(String contextMenuItem)
    {
        click(contextMenuItem);
    }

    public void clickContextSubMenuItem(String contextMenuItem, String contextSubMenuItem)
    {
        click(contextMenuItem);
        breakOutOfFrame();
        click(contextSubMenuItem);
    }

    public void clickClearFiltersButton()
    {
        click(CLEAR_FILTERS_RENDITION);
    }

    public void makePause()
    {
        new Actions(driver).pause(Duration.ofSeconds(1)).build().perform();
    }

    public String getTotalNumberOfRendtions()
    {
         return getElementsText(SourceNavigateAngularPageElements.TOTAL_RENDITIONS_NUMBERS).trim();
    }

    public void selectTwoRenditions(String rendition1,String rendition2)
    {
        Actions action = new Actions(driver);
        action.click(getElement(rendition1));
        action.pause(Duration.ofSeconds(1));
        action.keyDown(Keys.SHIFT);
        action.pause(Duration.ofSeconds(1));
        action.click(getElement(rendition2));
        action.pause(Duration.ofSeconds(1));
        action.keyUp(Keys.SHIFT).build().perform();
    }

    public void scrollToRight(String offset, String element)
    {
        ((JavascriptExecutor) driver).executeScript(String.format("arguments[0].scrollLeft += %s",offset), getElement(element));
    }

    public void openDifficultyLevel() 
    {
        rightClickRenditions();
        clickContextSubMenuItem(EDIT, DIFFICULTY_LEVEL);
    }

    public boolean goToWorkflowReportingSystem()
    {
        click(WORKFLOW_REPORTING_SYSTEM);
        boolean workflowReportingWindowAppeared = switchToWindow(WorkflowSearchPageElements.WORKFLOW_REPORTING_SYSTEM_PAGE_TITLE);
        maximizeCurrentWindow();
        waitForPageLoaded();
        return workflowReportingWindowAppeared;
    }

    public List<String> addSelectedRowData(int first, int last , String elementPath)
    {
        List <String> highlightedRowText= new ArrayList<>();
        for(int i = first ; i<= last; i++ )
        {
            highlightedRowText.add(getElementsText(driver.findElement(By.xpath(String.format(elementPath,i)))));
        }
        return highlightedRowText;
    }

    public void clickMultipleRendtions(int first, int last , String pattern)
    {
        Actions builder = new Actions(driver).keyDown(Keys.LEFT_CONTROL);

        for (int present = first; present<= last; present++)
            {
                builder.click(driver.findElement(By.xpath(format(pattern, present))));
            }
        builder.keyUp(Keys.LEFT_CONTROL).build().perform();
    }

    public void clickClearValidationFlag()
    {
        click(SourceNavigateAngularPageElements.CLEAR_VALIDATION_FLAGS);
    }

    public void clickClearWarningFlag()
    {
        click(SourceNavigateAngularPageElements.CLEAR_WARNING_FLAGS);
    }

    public void clickValidateLinks()
    {
       click(SourceNavigateAngularPageElements.VALIDATE_LINKS);
    }

    public void clickCancel()
    {
       click(SourceNavigateAngularPageElements.Cancel_Button);
    }

    public void clickConfirm()
    {
        click(SourceNavigateAngularPageElements.Confirm_Button);
    }
    
    public void openMenu(String element)
    {
        sendKeyToElement(element, Keys.ARROW_DOWN);
    }


    public void selectTaxType(List <String> taxType)
    {
        for(String select : taxType)
        {
            click(String.format(SourceNavigateAngularPageElements.ADD_TAX_TYPE, select));
        }
    }

    public void deSelectTaxType(List <String> taxType)
    {
        for(String select : taxType)
        {
            click(String.format(SourceNavigateAngularPageElements.ADD_TAX_TYPE, select));
        }
    }
    public void openMenuColumnHeaderForTabs(String filterbutton, String columnName)
    {
        new Actions(driver)
                .moveToElement(driver.findElement(By.xpath(format(filterbutton, columnName))))
                .click(driver.findElement(By.xpath(format(filterbutton, columnName))))
                .build()
                .perform();
    }

    public void openFilterTabColumnHeader()
    {
        click(COLUMN_MENU_FILTERS);
    }

    public void addSort(String optionToSort, String valueToSort)
    {
        click(ViewWizardPageElements.addSortButton);
        click(ViewWizardPageElements.filterSelect);
        click(optionToSort);
        selectDropdownOption(ViewWizardPageElements.sortSelect, valueToSort);
    }

    public void scrollToRight(String firstRow)
    {
        click(firstRow);
        Actions action = new Actions(driver);
        action.keyUp(Keys.SHIFT)
                .sendKeys(Keys.END)
                .keyUp(Keys.SHIFT)
                .build()
                .perform();
        waitForElementGone(LOADING_PLATE);
    }
}