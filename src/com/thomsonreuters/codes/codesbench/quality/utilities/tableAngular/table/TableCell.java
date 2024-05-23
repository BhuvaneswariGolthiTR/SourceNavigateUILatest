package com.thomsonreuters.codes.codesbench.quality.utilities.tableAngular.table;

import org.openqa.selenium.WebElement;

public interface TableCell
{
    String getColumnId();

    Integer getRowNumber();

    WebElement getWebElement();

    String getCellText();

    void click();

    void rightClick();

    void doubleClick();
}
