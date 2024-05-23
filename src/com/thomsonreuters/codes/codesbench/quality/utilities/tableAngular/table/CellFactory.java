package com.thomsonreuters.codes.codesbench.quality.utilities.tableAngular.table;

import com.thomsonreuters.codes.codesbench.quality.utilities.tableAngular.table.enums.TableColumns;
import com.thomsonreuters.codes.codesbench.quality.utilities.tableAngular.table.enums.TableType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CellFactory
{
    public static TableCell createCell(TableType tableType, WebElement cell, Integer rowNumber, String columnId, WebDriver driver)
    {
        if (tableType == TableType.SUBSCRIBED_CASES_TABLE)
        {
            if (columnId.equals(TableColumns.CASE_SERIAL_SUBSCRIBED_CASES.getColumnId()) ||
                    columnId.equals(TableColumns.CCDB.getColumnId()))
            {
                return LinkCellImpl.createCell(cell, rowNumber, columnId, driver);
            }
            if (columnId.equals(TableColumns.NOTES.getColumnId()))
            {
                return NotesButtonCellImpl.createCell(cell, rowNumber, columnId, driver);
            } else
            {
                return TextCellImpl.createCell(cell, rowNumber, columnId, driver);
            }
        }
        return TextCellImpl.createCell(cell, rowNumber, columnId, driver);
    }
}
