package com.thomsonreuters.codes.codesbench.quality.utilities.tableAngular.table;

import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LinkCellImpl extends BasePage implements TableCell
{
    private final String columnId;
    private final Integer rowNumber;
    private final WebElement cell;

    LinkCellImpl(WebElement cell, Integer rowNumber, String columnId, WebDriver driver)
    {
        super(driver);
        this.cell = cell;
        this.rowNumber = rowNumber;
        this.columnId = columnId;
    }

    public static TableCell createCell(WebElement cell, Integer rowNumber, String columnId, WebDriver driver)
    {
        return new LinkCellImpl(cell, rowNumber, columnId, driver);
    }

    public String getColumnId()
    {
        return columnId;
    }

    public Integer getRowNumber()
    {
        return rowNumber;
    }

    public WebElement getWebElement()
    {
        return getElement(cell, "./a");
    }

    public String getCellText()
    {
        return getElement(cell, "./a").getText();
    }

    public void click()
    {
        getWebElement().click();
    }

    public void rightClick()
    {
        rightClick(getWebElement());
    }

    public void doubleClick() {
        doubleClick(getWebElement());
    }
}
