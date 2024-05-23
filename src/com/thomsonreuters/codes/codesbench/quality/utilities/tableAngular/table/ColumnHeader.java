package com.thomsonreuters.codes.codesbench.quality.utilities.tableAngular.table;

import com.thomsonreuters.codes.codesbench.quality.pageelements.tableAngular.TablePageElementsAngular;
import com.thomsonreuters.codes.codesbench.quality.pageelements.tableAngular.popups.FilterPopupElementsAngular;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import com.thomsonreuters.codes.codesbench.quality.utilities.tableAngular.table.enums.SortingOrder;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ColumnHeader extends BasePage
{
    private final String columnName;
    private final WebElement columnNameSpan;

    private ColumnHeader(String columnName, WebElement columnNameSpan, WebDriver driver)
    {
        super(driver);
        this.columnName = columnName;
        this.columnNameSpan = columnNameSpan;
    }

    public static ColumnHeader createHeader(String columnName, WebElement columnNameSpan, WebDriver driver)
    {
        return new ColumnHeader(columnName, columnNameSpan, driver);
    }

    public WebElement getColumnNameElement()
    {
        return columnNameSpan;
    }

    public WebElement getControlButton()
    {
        return getElement(columnNameSpan, TablePageElementsAngular.CONTROL_BUTTON_ANCESTOR);
    }

    public boolean isFiltered()
    {
        return !getElement(this.columnNameSpan, TablePageElementsAngular.FILTER_ICON)
                .getAttribute("class").contains("ag-hidden");
    }

    public void openHeaderControl()
    {
        click(getControlButton());
        waitForVisibleElement(FilterPopupElementsAngular.FILTER_POP_UP + " | " + FilterPopupElementsAngular.FILTER_POP_UP_ALTERNATE);
    }

    public String getColumnName()
    {
        return columnName;
    }

    public SortingOrder defineSortingOrder()
    {
        WebElement asc = getElement(columnNameSpan, String.format(TablePageElementsAngular.SORTING_ORDER,
                TablePageElementsAngular.ASCENDING_ORDER));
        WebElement desc = getElement(columnNameSpan, String.format(TablePageElementsAngular.SORTING_ORDER,
                TablePageElementsAngular.DESCENDING_ORDER));
        if (asc.getAttribute("class").contains("ag-hidden") && !desc.getAttribute("class").contains("ag-hidden"))
        {
            return SortingOrder.DESCENDING;
        }
        if (desc.getAttribute("class").contains("ag-hidden") && !asc.getAttribute("class").contains("ag-hidden"))
        {
            return SortingOrder.ASCENDING;
        } else
        {
            return SortingOrder.UNSORTED;
        }
    }

    public void clickToSort()
    {
        click(this.columnNameSpan);
    }
}
