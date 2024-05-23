package com.thomsonreuters.codes.codesbench.quality.pages.publishing.toolbox;

import com.thomsonreuters.codes.codesbench.quality.pageelements.publishing.toolbox.GridHeaderFiltersElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class GridHeaderFiltersPage extends BasePage
{
        private WebDriver driver;

        @Autowired
        public GridHeaderFiltersPage(WebDriver driver)
        {
                super(driver);
                this.driver = driver;
        }

        @PostConstruct
        public void init()
        {
                PageFactory.initElements(driver, GridHeaderFiltersElements.class);
        }

        public void clickFilterIcon()
        {
                if (!isElementSelected(GridHeaderFiltersElements.FILTER_ICON_XPATH))
                {
                    click(GridHeaderFiltersElements.filterIcon);
                }
        }

        public void clickClearFilter()
        {
                click(GridHeaderFiltersElements.clearFilterButton);
        }

        public boolean isEmptyFilterListDisplayed()
        {
                return doesElementExist(GridHeaderFiltersElements.EMPTY_FILTER_LIST_XPATH);
        }

        public void clickSelectAllCheckbox()
        {
                if(!isElementSelected(GridHeaderFiltersElements.FILTER_ICON_XPATH))
                {
                    click(GridHeaderFiltersElements.filterIcon);
                }
                click(GridHeaderFiltersElements.SELECT_ALL_CHECKBOX);
        }

        public void setFilterValue(String value)
        {
            if (!isElementSelected(GridHeaderFiltersElements.FILTER_ICON_XPATH))
            {
                click(GridHeaderFiltersElements.filterIcon);
            }
            waitForElementExists(GridHeaderFiltersElements.inputField);
            clearAndSendKeysToElement(GridHeaderFiltersElements.inputField, value);
            sendEnterToElement(GridHeaderFiltersElements.inputField);
            waitForPageLoaded();
            click(GridHeaderFiltersElements.filterIcon);
            DateAndTimeUtils.takeNap(DateAndTimeUtils.FIVE_SECONDS);
        }

        public void sendTextToFilterTextBox(String text)
        {
                if(!isElementSelected(GridHeaderFiltersElements.FILTER_ICON_XPATH))
                {
                    click(GridHeaderFiltersElements.filterIcon);
                }
                waitForElementExists(GridHeaderFiltersElements.inputField);
                clearAndSendKeysToElement(GridHeaderFiltersElements.inputField, text);
        }

        @Deprecated
        public void setFilterDateToToday()
        {
                if(!isElementSelected(GridHeaderFiltersElements.FILTER_ICON_XPATH))
                {
                    click(GridHeaderFiltersElements.filterIcon);
                }
                click(GridHeaderFiltersElements.calendarFilterButton);
                click(GridHeaderFiltersElements.todaysDateCalendarButton);
        }

        public void setFilterDateValue(String dateValue)
        {
            if(!isElementSelected(GridHeaderFiltersElements.FILTER_ICON_XPATH))
            {
                click(GridHeaderFiltersElements.filterIcon);
            }
            clearAndSendKeysToElement(GridHeaderFiltersElements.dateInputField, dateValue);
            sendEnterToElement(GridHeaderFiltersElements.dateInputField);
            gridPage().waitForGridLoaded();
            click(GridHeaderFiltersElements.filterIcon);
            waitForPageLoaded();
            DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        }

        public List<String> getFilterValues()
        {
                List<WebElement> elements = driver.findElements(By.xpath(GridHeaderFiltersElements.FILTER_VALUES_DIV_XPATH));
                List<String> filterValuesList = new ArrayList<>(elements.size());
                elements.forEach(element ->
                {
                   String filterValue = getElementsText(element);
                   filterValuesList.add(filterValue);
                });

                return filterValuesList;
        }

        public void setMultipleFilterValues(String... values)
        {
            if(!isElementSelected(GridHeaderFiltersElements.FILTER_ICON_XPATH))
            {
                click(GridHeaderFiltersElements.filterIcon);
            }
            DateAndTimeUtils.takeNap(DateAndTimeUtils.THREE_SECONDS);
            if (doesElementExist(GridHeaderFiltersElements.SELECT_ALL_CHECKBOX_CHECKED))
            {
                click(getElement(GridHeaderFiltersElements.SELECT_ALL_CHECKBOX_CHECKED));
                waitForPageLoaded();
            }
            for(int i=0;i<values.length;i++)
            {
                clearAndSendKeysToElement(GridHeaderFiltersElements.inputField, values[i]);
                if(doesElementExist(String.format(GridHeaderFiltersElements.VALUE_CHECKBOX, values[i])))
                {
                    click(getElement(String.format(GridHeaderFiltersElements.VALUE_CHECKBOX, values[i])));
                }
            }
            click(GridHeaderFiltersElements.columnIcon);
            click(GridHeaderFiltersElements.columnIcon);
        }
    public void setDateFilterOption(String calendarDropdownOption)
        {
            if(!isElementSelected(GridHeaderFiltersElements.FILTER_ICON_XPATH))
            {
                click(GridHeaderFiltersElements.filterIcon);
            }

            click(GridHeaderFiltersElements.DROPDOWN);
            click(String.format(GridHeaderFiltersElements.DROPDOWN_OPTION, calendarDropdownOption));
        }

        public void setInBetweenInputDateFields(String fromInputDate, String toInputDte)
        {
            if(!isElementSelected(GridHeaderFiltersElements.FILTER_ICON_XPATH))
            {
                click(GridHeaderFiltersElements.filterIcon);
            }
            clearAndSendKeysToElement(GridHeaderFiltersElements.fromDateField, fromInputDate);
            clearAndSendKeysToElement(GridHeaderFiltersElements.toDateField, toInputDte);
            sendEnterToElement(GridHeaderFiltersElements.toDateField);
            gridPage().waitForGridLoaded();
            click(GridHeaderFiltersElements.filterIcon);
            DateAndTimeUtils.takeNap(DateAndTimeUtils.FIVE_SECONDS);
        }

}
