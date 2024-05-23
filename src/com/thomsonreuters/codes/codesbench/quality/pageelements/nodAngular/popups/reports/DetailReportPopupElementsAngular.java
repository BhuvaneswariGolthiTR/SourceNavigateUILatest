package com.thomsonreuters.codes.codesbench.quality.pageelements.nodAngular.popups.reports;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class DetailReportPopupElementsAngular extends ReportPopupElementsAngular
{
    public static final String PAGE_TITLE = "//div/h5[contains(text(), 'Detail Report')]";

    //headers
    @FindBy(how = How.XPATH, using = "//span[contains(text(),'Title')]")
    public static WebElement title;

    @FindBy(how = How.XPATH, using = "//span[contains(text(),'Case serial')]")
    public static WebElement caseSerial;

    @FindBy(how = How.XPATH, using = "//span[contains(text(),'Westlaw')]")
    public static WebElement westlaw;

    @FindBy(how = How.XPATH, using = "//span[contains(text(),'Court')]")
    public static WebElement court;

    @FindBy(how = How.XPATH, using = "//span[contains(text(),'Synopsis Background')]")
    public static WebElement synopsisBackground;

    public static final String INVALID_MESSAGE= "//div[@class='invalid-tooltip']";


}
