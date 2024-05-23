package com.thomsonreuters.codes.codesbench.quality.pageelements.publishing.toolbox;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class GridHeaderFiltersElements
{
        //Old xpath doesn't work in Edge
        //public static final String SELECT_ALL_CHECKBOX_CHECKED = "//div[contains(@class,'ag-menu')]//div[@ref='eSelectAll']/span[@class='ag-icon ag-icon-checkbox-checked']";
        //new xpath works in edge
        public static final String SELECT_ALL_CHECKBOX_CHECKED = "//div[contains(@class,'ag-menu')]//div[contains(text(),'Select All')]/..//input[contains(@class,'ag-input-field-input ag-checkbox-input')]";
        public static final String SELECT_ALL_CHECKBOX = "//div[contains(@class,'ag-menu')]//div[contains(text(),'Select All')]/..//input[contains(@class,'ag-input-field-input ag-checkbox-input')]";
        //Old xpath doesn't work in Edge
        //public static final String VALUE_CHECKBOX = "//div[contains(@class,'ag-menu')]//span[text()='%s']/../div[@class='ag-filter-checkbox']/span[contains(@class, 'ag-icon-checkbox-unchecked')]";
        //new xpath works in edge
        public static final String VALUE_CHECKBOX = "//div[contains(text(),'%s')]/..//input[contains(@class, 'ag-input-field-input ag-checkbox-input')]";
        public static final String FILTER_ICON_XPATH = "//span[contains(@class,'ag-tab')]//span[contains(@class,'ag-icon-filter')]/..";
        public static final String EMPTY_FILTER_LIST_XPATH = "//div[contains(@class,'ag-menu')]//div[@id='richList']//div[@style='height: 0px;']";
        public static final String FILTER_VALUES_DIV_XPATH = "//div[contains(@class,'ag-virtual-list-item')]/div[@class='ag-set-filter-item']//div[contains(@class,'ag-input-field-label')]";
        public static final String DROPDOWN = "//div[contains(@class,'ag-filter-select')]";
        public static final String DROPDOWN_OPTION = "//div[contains(@class, 'ag-select-list')]//span[contains(text(), '%s')]";

        @FindBy(how = How.XPATH, using = "//div[contains(@class,'ag-menu')]//span[contains(@class,'ag-icon-filter')]")
        public static WebElement filterIcon;

        @FindBy(how = How.XPATH, using = "//div[contains(@class,'ag-menu')]//span[contains(@class,'ag-icon-menu')]")
        public static WebElement columnIcon;

        @FindBy(how = How.XPATH, using =  "//div[contains(@class,'ag-menu')]//input[@class='ag-input-field-input ag-text-field-input']")
        public static WebElement inputField;

        @FindBy(how = How.XPATH, using = "//div[contains(@class, 'ag-filter-body-wrapper')]//div[contains(@class, 'ag-filter-date-from')]//input")
        public static WebElement fromDateField;

        @FindBy(how = How.XPATH, using =  "//div[contains(@class, 'ag-filter-body-wrapper')]//div[contains(@class, 'ag-filter-date-to')]//input")
        public static WebElement toDateField;

        @FindBy(how = How.XPATH, using = "//div[contains(@class, 'ag-filter-body-wrapper')]//input[contains(@class, 'ng-valid')]")
        public static WebElement dateInputField;

        @FindBy(how = How.XPATH, using = "//mat-datepicker-toggle/button[contains(@class, 'mat-icon-button')]")
        public static WebElement calendarFilterButton;

        @FindBy(how = How.XPATH, using = "//tbody[contains(@class, mat-calendar-body')]//div[contains(@class, 'mat-calendar-body-today')]")
        public static WebElement todaysDateCalendarButton;

        @FindBy(how = How.XPATH, using = "//div[contains(@class, 'ag-menu')]//div[contains(@class,'ag-filter-apply-panel')]/button[contains(text(), 'Clear Filter')]")
        public static WebElement clearFilterButton;
}
