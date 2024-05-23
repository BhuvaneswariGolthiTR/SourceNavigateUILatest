package com.thomsonreuters.codes.codesbench.quality.pageelements.nodAngular.headnotesPageFragmentsElements;

import com.thomsonreuters.codes.codesbench.quality.pageelements.nodAngular.HeadnotesPageElementsAngular;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class UsCaseInformationFragmentElementsAngular
{
    public static final String CASE_INFORMATION_COLUMN_VALUES = HeadnotesPageElementsAngular.CASE_INFORMATION_COLUMN_VALUES;
    public static final String CASE_SERIAL =  CASE_INFORMATION_COLUMN_VALUES + "[2]";
//    Loaded Date	Case Serial #	Westlaw #	Court	R/U	Reloaded	Reporter Cite	Reporter #	Signed Out By	Completed Date

    @FindBy(how = How.XPATH, using = CASE_INFORMATION_COLUMN_VALUES + "[1]")
    public static WebElement loadedDate;

    @FindBy(how = How.XPATH, using = CASE_INFORMATION_COLUMN_VALUES + "[2]")
    public static WebElement caseSerial;

    @FindBy(how = How.XPATH, using = CASE_INFORMATION_COLUMN_VALUES + "[3]")
    public static WebElement westlaw;

    @FindBy(how = How.XPATH, using = CASE_INFORMATION_COLUMN_VALUES + "[4]")
    public static WebElement court;

    @FindBy(how = How.XPATH, using = CASE_INFORMATION_COLUMN_VALUES + "[5]")
    public static WebElement ruReloaded;

    @FindBy(how = How.XPATH, using = CASE_INFORMATION_COLUMN_VALUES + "[6]")
    public static WebElement reloaded;

    @FindBy(how = How.XPATH, using = CASE_INFORMATION_COLUMN_VALUES + "[7]")
    public static WebElement reporterCite;

    @FindBy(how = How.XPATH, using = CASE_INFORMATION_COLUMN_VALUES + "[8]")
    public static WebElement reporter;

    @FindBy(how = How.XPATH, using = CASE_INFORMATION_COLUMN_VALUES + "[9]")
    public static WebElement signedOutBy;

    @FindBy(how = How.XPATH, using = CASE_INFORMATION_COLUMN_VALUES + "[10]")
    public static WebElement completedDate;

}
