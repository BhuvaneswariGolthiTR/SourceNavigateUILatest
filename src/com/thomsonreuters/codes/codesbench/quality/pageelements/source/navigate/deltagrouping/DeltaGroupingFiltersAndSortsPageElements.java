package com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.deltagrouping;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class DeltaGroupingFiltersAndSortsPageElements
{
    @FindBy(how = How.XPATH, using = "//div[@id='yui-dt9-th-sectionNumberFilter-liner']//input")
    public static WebElement sectionNumberFilter;

    @FindBy(how = How.XPATH, using = "//a[@href='yui-dt9-href-sectionNumber']")
    public static WebElement sectionNumberSort;

    @FindBy(how = How.XPATH, using = "//div[@id='yui-dt9-th-subSectionNumberFilter-liner']/input")
    public static WebElement subSectionNumberFilter;

    @FindBy(how = How.XPATH, using = "//a[@href='yui-dt9-href-subSectionNumber']")
    public static WebElement subSectionNumberSort;

    @FindBy(how = How.XPATH, using = "//div[@id='yui-dt9-th-deltaLevelFilter-liner']//input")
    public static WebElement deltaLevelFilter;

    @FindBy(how = How.XPATH, using = "//a[@href='yui-dt9-href-deltaLevel']")
    public static WebElement deltaLevelSort;

    @FindBy(how = How.XPATH, using = "//div[@id='yui-dt9-th-actionFilter-liner']//input")
    public static WebElement actionFilter;

    @FindBy(how = How.XPATH, using = "//a[@href='yui-dt9-href-action']")
    public static WebElement actionSort;

    @FindBy(how = How.XPATH, using = "//div[@id='yui-dt9-th-targetCodeFilter-liner']//input")
    public static WebElement targetCodeFilter;

    @FindBy(how = How.XPATH, using = "//a[@href='yui-dt9-href-targetCode']")
    public static WebElement targetCodeSort;

    @FindBy(how = How.XPATH, using = "//div[@id='yui-dt9-th-targetNodeFilter-liner']//input")
    public static WebElement targetNodeFilter;

    @FindBy(how = How.XPATH, using = "//a[@href='yui-dt9-href-targetNode']")
    public static WebElement targetNodeSort;

    @FindBy(how = How.XPATH, using = "//div[@id='yui-dt9-th-targetSubNodeFilter-liner']//input")
    public static WebElement targetSubNodeFilter;

    @FindBy(how = How.XPATH, using = "//a[@href='yui-dt9-href-targetSubNode']")
    public static WebElement targetSubNodeSort;

    @FindBy(how = How.XPATH, using = "//div[@id='yui-dt9-th-effectiveDateFilter-liner']//input")
    public static WebElement effectiveDateFilter;

    @FindBy(how = How.XPATH, using = "//a[@href='yui-dt9-href-effectiveDate']")
    public static WebElement effectiveDateSort;

    @FindBy(how = How.XPATH, using = "//div[@id='yui-dt9-th-groupNameFilter-liner']//input")
    public static WebElement groupNameFilter;

    @FindBy(how = How.XPATH, using = "//a[@href='yui-dt9-href-groupName']")
    public static WebElement groupNameSort;

    @FindBy(how = How.XPATH, using = "//div[@id='yui-dt9-th-isCpdDateProvidedFilter-liner']//input")
    public static WebElement cpdDateSortFilter;

    @FindBy(how = How.XPATH, using = "//a[@href='yui-dt9-href-isCpdDateProvided']")
    public static WebElement cpdDateSort;
}
