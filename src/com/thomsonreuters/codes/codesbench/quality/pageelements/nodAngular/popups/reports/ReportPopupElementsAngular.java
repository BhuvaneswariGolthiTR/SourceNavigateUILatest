package com.thomsonreuters.codes.codesbench.quality.pageelements.nodAngular.popups.reports;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class ReportPopupElementsAngular
{
    @FindBy(how = How.ID, using = "datepicker-dropdown-input")
    public static WebElement dateInputField;

    @FindBy(how = How.XPATH, using = "//button[contains(@class,'btn btn-outline-secondary')]")
    public static WebElement dateSelectButton;

    @FindBy(how = How.XPATH, using = "//button[contains(@class,'btn btn-primary')]")
    public static WebElement searchBButton;

    //table
    public static final String ROW_BY_INDEX = "//div[@ref='eCenterContainer']/div[@role ='row' and @row-id='%s']";
    public static final String CELL_BY_ROW_COLUMN_INDEX = ROW_BY_INDEX + "/div[@aria-colindex='%s']";


}
