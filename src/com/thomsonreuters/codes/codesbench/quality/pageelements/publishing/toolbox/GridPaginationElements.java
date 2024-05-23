package com.thomsonreuters.codes.codesbench.quality.pageelements.publishing.toolbox;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class GridPaginationElements
{
        @FindBy(xpath = "//div[@class='ag-status-bar-right']/toolbox-grid-counter-component/div[@class='ag-status-name-value']/span[contains(text(), 'Selectable rows')]")
        public static WebElement selectableRows;
}
