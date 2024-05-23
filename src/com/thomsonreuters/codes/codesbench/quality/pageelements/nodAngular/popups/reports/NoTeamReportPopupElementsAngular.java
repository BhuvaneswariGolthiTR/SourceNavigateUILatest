package com.thomsonreuters.codes.codesbench.quality.pageelements.nodAngular.popups.reports;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class NoTeamReportPopupElementsAngular extends ReportPopupElementsAngular {

    public static final String PAGE_TITLE = "//div/h5[contains(text(), 'No Team Report')]";

    //headers
    @FindBy(how = How.XPATH, using = "//span[contains(text(),'Title')]")
    public static WebElement title;

    @FindBy(how = How.XPATH, using = "//span[contains(text(),'Case serial')]")
    public static WebElement caseSerial;

    @FindBy(how = How.XPATH, using = "//span[contains(text(),'Westlaw')]")
    public static WebElement westlaw;

    @FindBy(how = How.XPATH, using = "//span[contains(text(),'Court')]")
    public static WebElement court;

    @FindBy(how = How.XPATH, using = "//span[contains(text(),'R/U')]")
    public static WebElement ru;

    @FindBy(how = How.XPATH, using = "//span[contains(text(),'Reloaded')]")
    public static WebElement reloaded;

    @FindBy(how = How.XPATH, using = "//span[contains(text(),'New')]")
    public static WebElement new_report;

    @FindBy(how = How.XPATH, using = "//span[contains(text(),'Update')]")
    public static WebElement update;


}
