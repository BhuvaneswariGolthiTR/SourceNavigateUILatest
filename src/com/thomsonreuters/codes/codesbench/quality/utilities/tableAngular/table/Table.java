package com.thomsonreuters.codes.codesbench.quality.utilities.tableAngular.table;

import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.tableAngular.table.enums.SortingOrder;
import com.thomsonreuters.codes.codesbench.quality.utilities.tableAngular.table.enums.TableColumns;
import org.openqa.selenium.WebDriver;

import java.util.*;
import java.util.stream.Collectors;

public class Table extends BasePage
{
    private final List<TableCell> cells;
    private final int numberOfRows;

    private Table(WebDriver driver, List<TableCell> cells, Integer numberOfRows)
    {
        super(driver);
        this.cells = cells;
        this.numberOfRows = numberOfRows;
    }

    public static Table createCasesTable(WebDriver driver, List<TableCell> cells, Integer numberOfRows)
    {
        return new Table(driver, cells, numberOfRows);
    }

    public List<TableCell> getAllCellsInRowByRowNumber(Integer number)
    {
        List<TableCell> allCellsInRow = new ArrayList<>();
        for (TableCell cell : this.cells)
        {
            if (cell.getRowNumber().equals(number))
            {
                allCellsInRow.add(cell);
            }
        }
        return allCellsInRow;
    }

    public List<Integer> getRowsNumbers()
    {
        Set<Integer> allRowNumbersSet = new HashSet<>();
        for (TableCell cell : this.cells)
        {
            allRowNumbersSet.add(cell.getRowNumber());
        }
        return allRowNumbersSet.stream().sorted().collect(Collectors.toList());
    }

    public int getNumberOfRows()
    {
        return numberOfRows;
    }

    public List<TableCell> getAllCellsInColumnByColumnName(TableColumns column)
    {
        String columnId = column.getColumnId();
        List<TableCell> allCellsInColumn = new ArrayList<>();
        for (TableCell cell : this.cells)
        {
            if (cell.getColumnId().equals(columnId))
            {
                allCellsInColumn.add(cell);
            }
        }
        return allCellsInColumn;
    }

    public TableCell getCellByCellTextAndColumnName(String cellText, TableColumns column)
    {
        String columnId = column.getColumnId();
        List<TableCell> cells = new ArrayList<>();
        for (TableCell cell : this.cells)
        {
            if (cell.getColumnId().equals(columnId) && cell.getCellText().equals(cellText))
            {
                cells.add(cell);
            }
        }
        return cells.get(0);
    }

    public TableCell getCellByRowAndColumn(Integer rowNumber, TableColumns column)
    {
        String columnId = column.getColumnId();
        List<TableCell> cellsInRow = getAllCellsInRowByRowNumber(rowNumber);
        List<TableCell> resultList = cellsInRow.stream().filter(cell -> cell.getColumnId().equals(columnId)).collect(Collectors.toList());
        return resultList.get(0);
    }

    public List<String> getAllValuesInGivenColumn(TableColumns column)
    {
        List<TableCell> cells = getAllCellsInColumnByColumnName(column);
        return cells.stream().map(TableCell::getCellText).collect(Collectors.toList());
    }

    public List<String> getAllValuesInGivenRow(int rowNumber)
    {
        List<TableCell> cells = getAllCellsInRowByRowNumber(rowNumber);
        return cells.stream().map(TableCell::getCellText).collect(Collectors.toList());
    }

    public List<List<String>> getAllValuesForRowsInRange(int firstRow, int lastRow)
    {
        logger.information("started table data parsing");
        List<List<String>> rowValues = new ArrayList<>();
        for (int i = firstRow; i <= lastRow; i++)
        {
            rowValues.add(getAllValuesInGivenRow(i));
        }
        logger.information("finished table data parsing");
        return rowValues;
    }

    private SortingOrder getNumericalColumnValuesSortingOrder(List<String> columnValues)
    {
        SortingOrder sortingOrder;
        List<Integer> columnValuesInt = columnValues.stream().map(Integer::parseInt).collect(Collectors.toList());
        List<Integer> columnValuesIntSorted = columnValues.stream().map(Integer::parseInt).sorted().collect(Collectors.toList());
        List<Integer> columnValuesIntSortedReverseOrder = columnValues.stream().map(Integer::parseInt).sorted(Collections.reverseOrder()).collect(Collectors.toList());

        if (columnValuesInt.equals(columnValuesIntSorted))
        {
            sortingOrder = SortingOrder.ASCENDING;
        } else if (columnValuesInt.equals(columnValuesIntSortedReverseOrder))
        {
            sortingOrder = SortingOrder.DESCENDING;
        } else
        {
            sortingOrder = SortingOrder.UNSORTED;
        }
        return sortingOrder;
    }

    private SortingOrder getDateColumnValuesSortingOrder(List<String> columnValues)
    {
        SortingOrder sortingOrder;
        String dateFormat = "MM/dd/yyyy";

        List<Date> columnValuesDate = columnValues.stream()
                .map(e -> DateAndTimeUtils.convertStringToDateObject(e, dateFormat))
                .collect(Collectors.toList());

        List<Date> columnValuesDateSorted = columnValues.stream()
                .map(e -> DateAndTimeUtils.convertStringToDateObject(e, dateFormat))
                .sorted().collect(Collectors.toList());

        List<Date> columnValuesDateSortedReverseOrder = columnValues.stream()
                .map(e -> DateAndTimeUtils.convertStringToDateObject(e, dateFormat))
                .sorted(Collections.reverseOrder()).collect(Collectors.toList());

        if (columnValuesDate.equals(columnValuesDateSorted))
        {
            sortingOrder = SortingOrder.ASCENDING;
        } else if (columnValuesDate.equals(columnValuesDateSortedReverseOrder))
        {
            sortingOrder = SortingOrder.DESCENDING;
        } else
        {
            sortingOrder = SortingOrder.UNSORTED;
        }
        return sortingOrder;
    }

    private SortingOrder getTextColumnValuesSortingOrder(List<String> columnValues)
    {
        SortingOrder sortingOrder;
        List<String> columnValuesSorted = columnValues.stream().sorted().collect(Collectors.toList());
        List<String> columnValuesSortedReverseOrder = columnValues.stream().sorted(Collections.reverseOrder()).collect(Collectors.toList());

        if (columnValues.equals(columnValuesSorted))
        {
            sortingOrder = SortingOrder.ASCENDING;
        } else if (columnValues.equals(columnValuesSortedReverseOrder))
        {
            sortingOrder = SortingOrder.DESCENDING;
        } else
        {
            sortingOrder = SortingOrder.UNSORTED;
        }
        return sortingOrder;
    }

    public SortingOrder getColumnValuesSorting(TableColumns column, List<String> columnValues)
    {
        SortingOrder sortingOrder;
        if (column.equals(TableColumns.HN) || column.equals(TableColumns.HEADNOTES) || column.equals(TableColumns.SUBSCRIPTIONS) || column.equals(TableColumns.COURT))
        {
            sortingOrder = getNumericalColumnValuesSortingOrder(columnValues);
        }
        else if (column.equals(TableColumns.LOADED_DATE) || column.equals(TableColumns.COMPLETED_DATE))
        {
            sortingOrder = getDateColumnValuesSortingOrder(columnValues);
        }
        else
        {
            sortingOrder = getTextColumnValuesSortingOrder(columnValues);
        }
        return sortingOrder;
    }

}
