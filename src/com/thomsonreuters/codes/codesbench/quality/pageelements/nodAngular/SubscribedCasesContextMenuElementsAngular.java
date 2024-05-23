package com.thomsonreuters.codes.codesbench.quality.pageelements.nodAngular;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class SubscribedCasesContextMenuElementsAngular
{

    public static final String CONTEXT_MENU = "//div[@class = 'ag-theme-balham ag-popup']//div[@class = 'ag-menu-list']";
    public static final String CONTEXT_MENU_OPTION = CONTEXT_MENU + "/div";
    public static final String CONTEXT_MENU_OPTION_WITH_TEXT = CONTEXT_MENU + "/div/span[text()='%s']";

    @FindBy(how = How.XPATH, using = CONTEXT_MENU + "/div/span[text()='Export to Excel']")
    public static WebElement exportToExcel;

    @FindBy(how = How.XPATH, using = CONTEXT_MENU + "/div/span[text()='Export to Excel selected rows']")
    public static WebElement exportToExcelSelectedRows;
}
