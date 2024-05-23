package com.thomsonreuters.codes.codesbench.quality.pages.tableAngular;

import com.thomsonreuters.codes.codesbench.quality.pageelements.nodAngular.AdministrativeOpinionsPageElementsAngular;
import com.thomsonreuters.codes.codesbench.quality.pageelements.nodAngular.CasesPageElementsAngular;
import com.thomsonreuters.codes.codesbench.quality.pageelements.nodAngular.CourtsPageElementsAngular;
import com.thomsonreuters.codes.codesbench.quality.pageelements.nodAngular.SubscribedCasesPageElementsAngular;
import com.thomsonreuters.codes.codesbench.quality.pageelements.tableAngular.TablePageElementsAngular;
import com.thomsonreuters.codes.codesbench.quality.pageelements.tableAngular.popups.FilterPopupElementsAngular;
import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.ocextract.OCExtractBasePageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.querynotereportangular.QueryNoteReportAngularPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import com.thomsonreuters.codes.codesbench.quality.pages.tools.ocextract.PublicationFilesTablePage;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.tableAngular.table.*;
import com.thomsonreuters.codes.codesbench.quality.utilities.tableAngular.table.enums.FilterOperator;
import com.thomsonreuters.codes.codesbench.quality.utilities.tableAngular.table.enums.SortingOrder;
import com.thomsonreuters.codes.codesbench.quality.utilities.tableAngular.table.enums.TableColumns;
import com.thomsonreuters.codes.codesbench.quality.utilities.tableAngular.table.enums.TableType;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TablePageAngular extends BasePage
{
    public TablePageAngular(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    private final WebDriver driver;

    public void init()
    {
        PageFactory.initElements(driver, TablePageElementsAngular.class);
    }

    
    public void openHeadnotesPageByCCDB(String caseSerial)
    {
        filterByCCDBWaitForTableReload(caseSerial);
        Table table = parseCertainRows(0);
        TableCell cell = table.getCellByRowAndColumn(0, TableColumns.CCDB);
        cell.click();
    }

    public void filterByTableColumnWaitForTableReload(TableColumns tableColumn, String value)
    {
        TableHeaders headers = parseHeaders();
        headers.openFilterOptionsForColumn(tableColumn);
        filterPopUp().selectFilterFromDropdown(FilterPopupElementsAngular.FIRST_FILTER_CONDITION, FilterOperator.CONTAINS);
        filterPopUp().typeTextToFilterField(FilterPopupElementsAngular.FIRST_INPUT_FIELD_FROM, value);
        //waitForTableToReload();
        DateAndTimeUtils.takeNap(DateAndTimeUtils.FIVE_SECONDS);
    }

    
    public void filterByCCDBWaitForTableReload(String ccdbNumber)
    {
        filterByTableColumnWaitForTableReload(TableColumns.CCDB, ccdbNumber);
    }

    
    public boolean isTableEmpty()
    {
        return getRows().isEmpty();
    }

    
    public Table parseWholeTable()
    {
        List<WebElement> listOfRowElements = getRows();
        listOfRowElements.sort(Comparator.comparingInt(o -> Integer.parseInt(o.getAttribute("row-index"))));
        int numberOfRows = listOfRowElements.size();
        WebElement firstRow = listOfRowElements.get(0);
        List<String> columnIds = getColumnsOrderFromFirstRow(firstRow);
        List<TableCell> allCells = getAllCells(listOfRowElements, columnIds);
        return Table.createCasesTable(driver, allCells, numberOfRows);
    }

    
    public Table parseCertainRows(Integer... rows)
    {
        List<WebElement> listOfRowElements = getRows();
        List<String> rowsAsString = Stream.of(rows).map(Objects::toString).collect(Collectors.toList());
        List<WebElement> requestedRows = listOfRowElements.stream()
                .filter(row -> rowsAsString.contains(row.getAttribute(TablePageElementsAngular.ROW_INDEX)))
                .collect(Collectors.toList());
        int numberOfRows = requestedRows.size();
        WebElement firstRow = listOfRowElements.get(0);
        List<String> columnIds = getColumnsOrderFromFirstRow(firstRow);
        List<TableCell> certainCells = getAllCells(requestedRows, columnIds);
        return Table.createCasesTable(driver, certainCells, numberOfRows);
    }

    
    public Table parseOnlyRowsWithCertainDataNoHeaders(TableColumns column, String data)
    {
        List<WebElement> rows;
        switch (column)
        {
            case CASE_SERIAL_SUBSCRIBED_CASES:
                rows = getElements(String.format
                        (TablePageElementsAngular.CASESERIAL_GRIDCELL_BY_COLUMN_AND_VALUE, column.getColumnId(),
                                data) + TablePageElementsAngular.ANCESTOR_ROW);
                break;
            case NOTES:
                rows = getElements(String.format
                        (TablePageElementsAngular.NOTES_GRIDCELL_BY_COLUMN_AND_TITLE, column.getColumnId(),
                                data) + TablePageElementsAngular.ANCESTOR_ROW);
                break;
            default:
                rows = getElements(String.format
                        (TablePageElementsAngular.GRIDCELL_BY_COLUMN_AND_VALUE, column.getColumnId(),
                                data) + TablePageElementsAngular.ANCESTOR_ROW);
                break;
        }
        int numberOfRows = rows.size();
        WebElement firstRow = rows.get(0);
        List<String> columnsOrder = getColumnsOrderFromFirstRow(firstRow);
        List<TableCell> certainCells = getAllCells(rows, columnsOrder);
        return Table.createCasesTable(driver, certainCells, numberOfRows);
    }

    
    public void findRowNumberOfCellAndAddToList(String xpath, List<Integer> rowIndexes)
    {
        List<WebElement> cells = getElements(xpath);
        for (WebElement cell : cells)
        {
            rowIndexes.add(Integer.parseInt(cell.getAttribute(TablePageElementsAngular.ROW_INDEX)));
        }
    }

    
    public TableHeaders parseHeaders()
    {
        List<WebElement> listOfHeadersElements = getElements(TablePageElementsAngular.COLUMN_HEADER);
        List<ColumnHeader> listOfHeadersObjects = new ArrayList<>();
        for (WebElement header : listOfHeadersElements)
        {
            String columnName = header.getText();
            if (columnName.isEmpty()) {
                continue;
            }
            listOfHeadersObjects.add(ColumnHeader.createHeader(columnName, header, this.driver));
        }
        return TableHeaders.createHeaders(this.driver, listOfHeadersObjects);
    }

    private List<TableCell> getAllCells(List<WebElement> listOfRowElements, List<String> columnsIds)
    {
        List<TableCell> cells = new ArrayList<>();
        TableType tableType = defineTableType();
        for (WebElement row : listOfRowElements)
        {
            Integer rowNumber = Integer.parseInt(row.getAttribute(TablePageElementsAngular.ROW_INDEX));
            List<WebElement> cellsInRow = getElements(row, TablePageElementsAngular.CHILD);
            for (WebElement cell : cellsInRow)
            {
                int columnIndex = cellsInRow.indexOf(cell);
                cells.add(CellFactory.createCell(tableType, cell, rowNumber, columnsIds.get(columnIndex), this.driver));
            }
        }
        return cells;
    }

    private TableType defineTableType()
    {
        if (doesElementExist(SubscribedCasesPageElementsAngular.PAGE_TAG))
        {
            return TableType.SUBSCRIBED_CASES_TABLE;
        }
        if (doesElementExist(CourtsPageElementsAngular.PAGE_TAG))
        {
            return TableType.COURTS_TABLE;
        }
        if (doesElementExist(CasesPageElementsAngular.PAGE_TAG))
        {
            return TableType.CASES_TABLE;
        }
        if (doesElementExist(OCExtractBasePageElements.PAGE_TAG))
        {
            return TableType.TOOLBOX_O_CONNORS;
        }
        if (doesElementExist(AdministrativeOpinionsPageElementsAngular.PAGE_TAG))
        {
            return TableType.ADMINISTRATIVE_OPTIONS_TABLE;
        }
        if (doesElementExist(QueryNoteReportAngularPageElements.PAGE_TAG))
        {
            return TableType.QUERY_NOTE_REPORT_ANGULAR_TABLE;
        }
        else
        {
            throw new NotFoundException("This is not a table meant to be parsed with current implementation");
        }
    }

    private List<String> getColumnsOrderFromFirstRow(WebElement firstRow)
    {
        List<String> columnsPositionToName = new ArrayList<>();
        List<WebElement> cellsInFirstRow = new ArrayList<>();
        try
        {
            cellsInFirstRow = getElements(firstRow, TablePageElementsAngular.CHILD);
        } catch (IndexOutOfBoundsException exception)
        {
            logger.information("The table is empty");
        }
        //We parse the first row to get indexes of the columns
        for (WebElement cell : cellsInFirstRow)
        {
            String currentCellColumnId = cell.getAttribute(TablePageElementsAngular.COLUMN_ID);
            columnsPositionToName.add(currentCellColumnId);
        }
        return columnsPositionToName;
    }

    private List<WebElement> getRows()
    {
        WebElement tableElement;
        if (getClass().equals(PublicationFilesTablePage.class))
        {
            tableElement = getElement(TablePageElementsAngular.OC_PUB_FILES_TABLE);
        } else
        {
            tableElement = getElement(TablePageElementsAngular.TABLE);
        }
        return getElements(tableElement, TablePageElementsAngular.CHILD);
    }

    
    public List<String> applyNumericalFilterInRange(List<String> listOfValues, String from, String to)
    {
        int fromInt = Integer.parseInt(from);
        int toInt = Integer.parseInt(to);
        List<Integer> intList = listOfValues.stream().map(Integer::parseInt).collect(Collectors.toList());
        List<Integer> resultInt = intList.stream().filter(value -> value >= fromInt).filter(value -> value <= toInt).collect(Collectors.toList());
        return resultInt.stream().map(Object::toString).sorted().collect(Collectors.toList());
    }

    
    public List<String> applyDateFilterInRange(List<String> listOfValues, String from, String to)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate filterFromValueDate = LocalDate.parse(from, formatter);
        LocalDate filterToValueDate = LocalDate.parse(to, formatter);
        List<LocalDate> dateList = listOfValues.stream().map(value -> LocalDate.parse(value, formatter)).collect(Collectors.toList());
        List<LocalDate> equalInRangeDates = dateList.stream().filter(value -> value.isEqual(filterFromValueDate) ||
                value.isEqual(filterToValueDate)).collect(Collectors.toList());
        List<LocalDate> inRangeNotEqualDates = dateList.stream().filter(value -> value.isAfter(filterFromValueDate))
                .filter(value -> value.isBefore(filterToValueDate)).collect(Collectors.toList());
        List<LocalDate> resultDates = Stream.concat(inRangeNotEqualDates.stream(),
                equalInRangeDates.stream()).sorted().collect(Collectors.toList());
        return resultDates.stream().map(value -> value.format(formatter)).sorted().collect(Collectors.toList());
    }

    
    public List<String> applyNumericalFilter(List<String> listOfValues, FilterOperator filterOperator, String filterValue)
    {
        int filterValueInt = Integer.parseInt(filterValue);
        List<Integer> intList = listOfValues.stream().map(Integer::parseInt).collect(Collectors.toList());
        List<Integer> resultInt;
        switch (filterOperator)
        {
            case LESS_THAN:
                resultInt = intList.stream().filter(value -> value < filterValueInt).collect(Collectors.toList());
                break;
            case LESS_THAN_OR_EQUALS:
                resultInt = intList.stream().filter(value -> value <= filterValueInt).collect(Collectors.toList());
                break;
            case GREATER_THAN:
                resultInt = intList.stream().filter(value -> value > filterValueInt).collect(Collectors.toList());
                break;
            case GREATER_THAN_OR_EQUALS:
                resultInt = intList.stream().filter(value -> value >= filterValueInt).collect(Collectors.toList());
                break;
            case EQUALS:
                resultInt = intList.stream().filter(value -> value == filterValueInt).collect(Collectors.toList());
                break;
            case NOT_EQUAL:
                resultInt = intList.stream().filter(value -> value != filterValueInt).collect(Collectors.toList());
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + filterOperator);
        }
        return resultInt.stream().map(Object::toString).sorted().collect(Collectors.toList());
    }

    
    public List<String> applyDateFilter(List<String> listOfValues, FilterOperator filterOperator, String filterValue)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate filterValueDate = LocalDate.parse(filterValue, formatter);
        List<LocalDate> dateList = listOfValues.stream().map(value -> LocalDate.parse(value, formatter)).collect(Collectors.toList());
        List<LocalDate> resultDates;
        List<LocalDate> datesEqualValue = dateList.stream().filter(value -> value.isEqual(filterValueDate)).collect(Collectors.toList());
        List<LocalDate> inRangeNotEqualDates;
        switch (filterOperator)
        {
            case LESS_THAN_OR_EQUALS:
                inRangeNotEqualDates = dateList.stream().filter(value -> value.isBefore(filterValueDate)).collect(Collectors.toList());
                resultDates = Stream.concat(inRangeNotEqualDates.stream(), datesEqualValue.stream()).collect(Collectors.toList());
                break;
            case GREATER_THAN_OR_EQUALS:
                inRangeNotEqualDates = dateList.stream().filter(value -> value.isAfter(filterValueDate)).collect(Collectors.toList());
                resultDates = Stream.concat(inRangeNotEqualDates.stream(), datesEqualValue.stream()).collect(Collectors.toList());
                break;
            case LESS_THAN:
                resultDates = dateList.stream().filter(value -> value.isBefore(filterValueDate)).collect(Collectors.toList());
                break;
            case GREATER_THAN:
                resultDates = dateList.stream().filter(value -> value.isAfter(filterValueDate)).collect(Collectors.toList());
                break;
            case EQUALS:
                resultDates = datesEqualValue;
                break;
            case NOT_EQUAL:
                resultDates = dateList.stream().filter(value -> !value.isEqual(filterValueDate)).collect(Collectors.toList());
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + filterOperator);
        }
        return resultDates.stream().map(value -> value.format(formatter)).sorted().collect(Collectors.toList());
    }

    
    public List<String> applyTextFilter(List<String> listOfValues, FilterOperator filterOperator, String filterValue)
    {
        List<String> result;
        switch (filterOperator)
        {
            case STARTS_WITH:
                result = listOfValues.stream().filter(value -> value.startsWith(filterValue)).collect(Collectors.toList());
                break;
            case ENDS_WITH:
                result = listOfValues.stream().filter(value -> value.endsWith(filterValue)).collect(Collectors.toList());
                break;
            case CONTAINS:
                result = listOfValues.stream().filter(value -> value.contains(filterValue)).collect(Collectors.toList());
                break;
            case NOT_CONTAINS:
                result = listOfValues.stream().filter(value -> !value.contains(filterValue)).collect(Collectors.toList());
                break;
            case EQUALS:
                result = listOfValues.stream().filter(value -> value.equals(filterValue)).collect(Collectors.toList());
                break;
            case NOT_EQUAL:
                result = listOfValues.stream().filter(value -> !value.equals(filterValue)).collect(Collectors.toList());
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + filterOperator);
        }
        return result;
    }

    
    public List<String> applyFilterContainString(List<String> listOfValues, String filter)
    {
        return listOfValues.stream().filter(value -> value.contains(filter)).collect(Collectors.toList());
    }

    
    public boolean checkIfElementHidden(String xpath)
    {
        return getElement(xpath).getAttribute("class").contains("ag-hidden");
    }

    
    public void cleanLocalStorage()
    {
        String scriptToExecute = "localStorage.clear();";
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript(scriptToExecute);
    }

    public void waitForTableToReload()
    {
        waitForElementExists(TablePageElementsAngular.OVERLAY_PANEL_XPATH, DateAndTimeUtils.HALF_SECOND);
        waitForHiddenOverlay();
    }

    public void waitForHiddenOverlay()
    {
        waitForElement(TablePageElementsAngular.HIDDEN_OVERLAY_PANEL_XPATH);
    }

    
    public List<String> getAllCellValuesAsTextByRowNumber(Integer rowNumber)
    {
        Table table = parseWholeTable();
        List<TableCell> row = table.getAllCellsInRowByRowNumber(rowNumber);
        return row.stream().map(TableCell::getCellText).collect(Collectors.toList());
    }

    
    public void selectGivenRowsInTable(TableColumns columnToClick, int... rows)
    {
        Table table = parseWholeTable();
        Actions highlighter = new Actions(driver);
        highlighter.keyDown(Keys.SHIFT);

        for (int row : rows)
        {
            table.getCellByRowAndColumn(row, columnToClick);
        }

        highlighter.keyUp(Keys.SHIFT);
        Action action = highlighter.build();
        action.perform();
    }

    
    public void openFilterOptionsForColumn(TableColumns column)
    {
        TableHeaders headers = parseHeaders();
        headers.openFilterOptionsForColumn(column);
    }

    public boolean isColumnFiltered(TableColumns column)
    {
        TableHeaders headers = parseHeaders();
        return headers.getColumnHeaderByName(column).isFiltered();
    }

    public HashMap<String, Integer> getActualColumnOrder()
    {
        TableHeaders headers = parseHeaders();
        return headers.getActualColumnOrder();
    }

    public List<String> getActualColumnOrderListSubscribedCases()
    {
        List<WebElement> headerElements = getElements(SubscribedCasesPageElementsAngular.COLUMN_HEADERS_TEXT_XPATH);
        List<String> headerTextList = new ArrayList<>();
        for (WebElement headerElement : headerElements)
        {
            String headerTextItem = getElementsText(headerElement);
            if(!headerTextItem.equals(""))
            {
                if (!headerTextItem.equals(" "))
                {
                    headerTextList.add(headerTextItem);
                }
            }
        }

        // janky way to scroll to the right and get the rest of the headers.
        // Can't really use a Set collection as I'd like to as get elements doesn't pick up elements off screen
        // this loop will check for existing values and skip over those.
        subscribedCasesPageAngular().scrollToRight("500");
        waitForPageLoaded();

        headerElements = getElements(SubscribedCasesPageElementsAngular.COLUMN_HEADERS_TEXT_XPATH);
        for (WebElement headerElement : headerElements)
        {
            String headerTextItem = getElementsText(headerElement);
            if(!headerTextList.contains(headerTextItem))
            {
                if(!headerTextItem.equals(""))
                {
                    if(!headerTextItem.equals(" "))
                    {
                        headerTextList.add(headerTextItem);
                    }
                }
            }
        }
        return headerTextList;
    }

    public List<String> getActualColumnOrderListOcExtract()
    {
        List<WebElement> headerElements = getElements(TablePageElementsAngular.COLUMN_HEADER);
        List<String> headerTextList = new ArrayList<>();
        for (WebElement headerElement : headerElements)
        {
            String headerTextItem = getElementsText(headerElement);
            if(!headerTextItem.equals(""))
            {
                if (!headerTextItem.equals(" "))
                {
                    headerTextList.add(headerTextItem);
                }
            }
        }
        return headerTextList;
    }

    public List<String>  getAllValuesInGivenColumn(TableColumns column)
    {
       Table table = parseWholeTable();
       return table.getAllValuesInGivenColumn(column).stream().collect(Collectors.toList());
    }

    public SortingOrder getColumnValuesSorting(TableColumns column, List<String> columnValues)
    {
        Table table = parseWholeTable();
        return table.getColumnValuesSorting(column, columnValues);
    }

    public boolean isColumnUnsorted(TableColumns column){
        TableHeaders headers = parseHeaders();
        ColumnHeader columnHeader = headers.getColumnHeaderByName(column);
        return columnHeader.defineSortingOrder().equals(SortingOrder.UNSORTED);
    }

    public void clickToSort (TableColumns column){
        TableHeaders headers = parseHeaders();
        ColumnHeader columnHeader = headers.getColumnHeaderByName(column);
        columnHeader.clickToSort();
    }

    public SortingOrder defineSortingOrder(TableColumns column){
        TableHeaders headers = parseHeaders();
        ColumnHeader columnHeader = headers.getColumnHeaderByName(column);
        return columnHeader.defineSortingOrder();
    }

    public List<Integer> getRowsNumbers(){
        Table table = parseWholeTable();
        return table.getRowsNumbers();
    }

    public String getCellTextByRowAndColumn(Integer rowNumber, TableColumns column){
        Table table = parseCertainRows(rowNumber);
        TableCell cell = table.getCellByRowAndColumn(rowNumber, column);
        return cell.getCellText();
    }

    
    public void clickCellTextByRowAndColumn(Integer rowNumber, TableColumns column){
        Table table = parseCertainRows(rowNumber);
        TableCell cell = table.getCellByRowAndColumn(rowNumber, column);
        cell.click();
    }

    
    public void doubleClickCellTextByRowAndColumn(Integer rowNumber, TableColumns column)
    {
        Table table = parseCertainRows(rowNumber);
        TableCell cell = table.getCellByRowAndColumn(rowNumber, column);
        cell.doubleClick();
    }

    
    public void rightClickCellTextByRowAndColumn(Integer rowNumber, TableColumns column)
    {
        Table table = parseCertainRows(rowNumber);
        TableCell cell = table.getCellByRowAndColumn(rowNumber, column);
        cell.rightClick();
    }

    public boolean isTableRowSelected(Integer rowNumber) {
        return isElementSelected(String.format(TablePageElementsAngular.TABLE_ROW, rowNumber));
    }

    public boolean isTextCreateNote(Integer rowNumber, TableColumns column){
        Table table = parseCertainRows(rowNumber);
        NotesButtonCellImpl notesCell = (NotesButtonCellImpl) table.getCellByRowAndColumn(rowNumber, column);
        return notesCell.isTextCreateNote();
    }

    public boolean isTextEditNote(Integer rowNumber, TableColumns column){
        Table table = parseCertainRows(rowNumber);
        NotesButtonCellImpl notesCell = (NotesButtonCellImpl) table.getCellByRowAndColumn(rowNumber, column);
        return notesCell.isTextEditNote();
    }

    public boolean areValuesFromNotesColumnSorted(List<String> columnValues)
    {
        List<String> columnValuesSorted = columnValues.stream().sorted().collect(Collectors.toList());
        List<String> columnValuesSortedReverseOrder = columnValues.stream().sorted(Collections.reverseOrder()).collect(Collectors.toList());

        return columnValues.equals(columnValuesSorted) || columnValues.equals(columnValuesSortedReverseOrder);
    }

    public WebElement getColumnNameElement(TableColumns column){
        TableHeaders headers = parseHeaders();
        ColumnHeader columnHeader = headers.getColumnHeaderByName(column);
        return columnHeader.getColumnNameElement();
    }

    public boolean isFiltered(TableColumns column){
        TableHeaders headers = parseHeaders();
        ColumnHeader columnHeader = headers.getColumnHeaderByName(column);
        return columnHeader.isFiltered();
    }

    public void closeFilterOptionsForColumn(){
        TableHeaders headers = parseHeaders();
        headers.closeFilterOptionsForColumn();
    }

    public List<List<String>> getAllValuesForRowsInRange(int firstRow, int lastRow){
        Table table = parseWholeTable();
        return table.getAllValuesForRowsInRange(firstRow, lastRow);
    }

    public void openShowHideOptionsForColumn(TableColumns column){
    TableHeaders headers = parseHeaders();
    headers.openShowHideOptionsForColumn(column);
    }

    public void dragColumnToColumn(TableColumns columnToDrag, TableColumns columnWhereToDrag){
        TableHeaders headers = parseHeaders();
        headers.dragColumnToColumn(columnToDrag, columnWhereToDrag);
    }

    public void sortByColumn(TableColumns column){
        TableHeaders headers = parseHeaders();
        headers.sortByColumn(column);
    }

    public void rightClickCellTextByTextAndColumn(String cellText, TableColumns columnName)
    {
        Table table = parseWholeTable();
        TableCell cell = table.getCellByCellTextAndColumnName(cellText, columnName);
        cell.rightClick();
    }

    public void clickClearFilters()
    {
        click(TablePageElementsAngular.clearFiltersButton);
        waitForTableToReload();
    }
}
