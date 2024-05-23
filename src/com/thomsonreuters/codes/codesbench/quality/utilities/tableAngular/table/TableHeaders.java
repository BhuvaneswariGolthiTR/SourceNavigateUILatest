package com.thomsonreuters.codes.codesbench.quality.utilities.tableAngular.table;

import com.thomsonreuters.codes.codesbench.quality.pageelements.tableAngular.TablePageElementsAngular;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import com.thomsonreuters.codes.codesbench.quality.utilities.tableAngular.table.enums.SortingOrder;
import com.thomsonreuters.codes.codesbench.quality.utilities.tableAngular.table.enums.TableColumns;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class TableHeaders extends BasePage
{
    private final List<ColumnHeader> headers;

    private TableHeaders(WebDriver driver, List<ColumnHeader> headers)
    {
        super(driver);
        this.headers = headers;
    }

    
    public static TableHeaders createHeaders(WebDriver driver, List<ColumnHeader> headers)
    {
        return new TableHeaders(driver, headers);
    }

    
    public List<ColumnHeader> getColumnHeaders()
    {
        return headers;
    }

    
    public ColumnHeader getColumnHeaderByName(TableColumns column)
    {
        String columnHeaderName = column.getColumnHeaderName();
        ColumnHeader headerToReturn = null;
        for (ColumnHeader header : getColumnHeaders())
        {
            if (header.getColumnName().equals(columnHeaderName))
            {
                headerToReturn = header;
            }
        }
        return headerToReturn;
    }

    
    public SortingOrder sortByColumn(TableColumns column)
    {
        String columnHeaderName = column.getColumnHeaderName();
        SortingOrder sortingOrder = SortingOrder.UNSORTED;
        for (ColumnHeader header : headers)
        {
            if (header.getColumnName().equals(columnHeaderName))
            {
                click(header.getColumnNameElement());
            }
            sortingOrder = header.defineSortingOrder();
        }
        return sortingOrder;
    }

    
    public List<String> getAllTableHeadersNames()
    {
        List<String> result = new ArrayList<>();
        for (ColumnHeader header : this.headers)
        {
            result.add(header.getColumnName());
        }
        return result;
    }

    
    public void openFilterOptionsForColumn(TableColumns column)
    {
        getColumnHeaderByName(column).openHeaderControl();
        if (!filterPopUp().checkIfTabIsOpened(2))
        {
            filterPopUp().openTab(2);
        }
    }

    
    public void openShowHideOptionsForColumn(TableColumns column)
    {
        getColumnHeaderByName(column).openHeaderControl();
        if (!filterPopUp().checkIfTabIsOpened(3))
        {
            filterPopUp().openTab(3);
        }
    }

    
    public void closeFilterOptionsForColumn()
    {
        if (filterPopUp().checkIfTabIsOpened(2))
        {
            filterPopUp().closeTab(2);
        }
    }

    
    public List<ColumnHeader> findColumnsWithFiltersApplied()
    {
        List<ColumnHeader> headers = getColumnHeaders();
        return headers.stream().filter(ColumnHeader::isFiltered).collect(Collectors.toList());
    }

    
    public boolean areAnyFiltersApplied()
    {
        return findColumnsWithFiltersApplied().size() != 0;
    }

    
    public HashMap<String, Integer> getActualColumnOrder()
    {
        HashMap<String, Integer> columnsOrder = new HashMap<>();
        for (ColumnHeader header : getColumnHeaders())
        {
            Integer columnPosition = Integer.parseInt(header.getColumnNameElement().getAttribute(TablePageElementsAngular.COLUMN_POSITION));
            columnsOrder.put(header.getColumnName(), columnPosition);
        }
        return columnsOrder;
    }

    public List<String> getActualColumnOrderList()
    {
        List<String> columnsOrder = new ArrayList<>();
        for (ColumnHeader header : getColumnHeaders())
        {
            columnsOrder.add(header.getColumnName());
        }
        return columnsOrder;
    }

    public void dragColumnToColumn(TableColumns columnToDrag, TableColumns columnWhereToDrag)
    {
        ColumnHeader columnHeaderToDrag = getColumnHeaderByName(columnToDrag);
        ColumnHeader columnHeaderWhereToDrag = getColumnHeaderByName(columnWhereToDrag);
        WebElement elementToDrag = columnHeaderToDrag.getColumnNameElement();
        WebElement elementWhereToDrag = columnHeaderWhereToDrag.getColumnNameElement();
        dragAndDropElementToElement(elementToDrag, elementWhereToDrag);
    }

}
