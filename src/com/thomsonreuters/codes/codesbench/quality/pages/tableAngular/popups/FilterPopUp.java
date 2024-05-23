package com.thomsonreuters.codes.codesbench.quality.pages.tableAngular.popups;

import com.thomsonreuters.codes.codesbench.quality.pageelements.tableAngular.popups.FilterPopupElementsAngular;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import com.thomsonreuters.codes.codesbench.quality.utilities.tableAngular.table.enums.FilterOperator;
import com.thomsonreuters.codes.codesbench.quality.utilities.tableAngular.table.enums.JoinOperator;
import com.thomsonreuters.codes.codesbench.quality.utilities.tableAngular.table.enums.TableColumns;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class FilterPopUp extends BasePage
{

    @Autowired
    public FilterPopUp(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    private final WebDriver driver;

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, FilterPopupElementsAngular.class);
    }

    
    public void openTab(int tabNumber)
    {
        switch (tabNumber)
        {
            case (1):
                click(FilterPopupElementsAngular.FIRST_TAB);
                //waitForVisibleElement()
                break;
            case (2):
                click(FilterPopupElementsAngular.SECOND_TAB);
                //waitForVisibleElement(FilterPopupElementsAngular.FIRST_FILTER_CONDITION);
                break;
            case (3):
                click(FilterPopupElementsAngular.THIRD_TAB);
                //waitForVisibleElement
                break;
        }
    }

    
    public void closeTab(int tabNumber)
    {
        switch (tabNumber)
        {
            case (1):
                click(FilterPopupElementsAngular.FIRST_TAB);
                break;
            case (2):
                click(FilterPopupElementsAngular.SECOND_TAB);
                break;
            case (3):
                click(FilterPopupElementsAngular.THIRD_TAB);
                break;
        }
    }

    
    public boolean checkIfTabIsOpened(int tabNumber)
    {
        boolean isOpened = false;
        switch (tabNumber)
        {
            case (1):
                if (getElement(FilterPopupElementsAngular.FIRST_TAB).getAttribute("class")
                        .contains(FilterPopupElementsAngular.TAB_SELECTED))
                {
                    isOpened = true;
                }
                break;
            case (2):
                if (getElement(FilterPopupElementsAngular.SECOND_TAB).getAttribute("class")
                        .contains(FilterPopupElementsAngular.TAB_SELECTED))
                {
                    isOpened = true;
                }
                break;
            case (3):
                if (getElement(FilterPopupElementsAngular.THIRD_TAB).getAttribute("class")
                        .contains(FilterPopupElementsAngular.TAB_SELECTED))
                {
                    isOpened = true;
                }
                break;
        }
        return isOpened;
    }

    
    public void selectFilterFromDropdown(String filterDropdownXpath, FilterOperator filterToSelect)
    {
        selectDropdownOption(filterDropdownXpath, filterToSelect.getText());
    }

    
    public void typeTextToFilterField(String inputFieldXpath, String textToType)
    {
        WebElement field = getElement(inputFieldXpath);
        field.clear();
        field.sendKeys(textToType);
    }

    
    public void clearInputFilterField(String inputFieldXpath)
    {
        WebElement field = getElement(inputFieldXpath);
        click(field);
        clear(field);
    }

    
    public void selectJoinOperator(JoinOperator joinOperator)
    {
        click(joinOperator.getXpath());
    }

    
    public JoinOperator checkWhichJoinOperatorIsSelected()
    {
        JoinOperator joinOperator = JoinOperator.AND;
        WebElement and = getElement(JoinOperator.AND.getXpath());
        String checked = and.getAttribute("checked");
        if (checked == null)
        {
            joinOperator = JoinOperator.OR;
        }
        return joinOperator;
    }

    
    public void toggleColumnsVisibility(TableColumns... columns)
    {
        for (TableColumns column : columns)
        {
            click(String.format(FilterPopupElementsAngular.COLUMN_NAME, column.getColumnHeaderName()));
        }
    }

    
    public void clickOnCalendarButton(String ePanel)
    {
        sendEnterToElement(String.format(FilterPopupElementsAngular.FIRST_OPEN_CALENDAR_BUTTON_FROM, ePanel));
        waitForVisibleElement(String.format(FilterPopupElementsAngular.PICKER_WIDGET, ePanel));
    }

    public void clickApplyFilterButton()
    {
        sendEnterToElement(FilterPopupElementsAngular.APPLY_FILTER_BUTTON);
    }
}
