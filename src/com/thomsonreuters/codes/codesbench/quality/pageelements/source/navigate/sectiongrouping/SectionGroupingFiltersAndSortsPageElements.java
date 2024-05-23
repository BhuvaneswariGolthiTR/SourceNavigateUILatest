package com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.sectiongrouping;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class SectionGroupingFiltersAndSortsPageElements
{
    @FindBy(how = How.XPATH, using = "//div[@id='yui-dt9-th-sectionNumberFilter-liner']//input")
    public static WebElement sectionNumberFilter;

    @FindBy(how = How.XPATH, using = "//a[@href='yui-dt9-href-sectionNumber']")
    public static WebElement sectionNumberSort;

    @FindBy(how = How.XPATH, using = "//div[@id='yui-dt9-th-subSectionNumberFilter-liner']/input")
    public static WebElement subSectionNumberFilter;

    @FindBy(how = How.XPATH, using = "//a[@href='yui-dt9-href-subSectionNumber']")
    public static WebElement subSectionNumberSort;

    @FindBy(how = How.XPATH, using = "//div[@id='yui-dt9-th-effectiveDateFilter-liner']/input")
    public static WebElement effectiveDateFilter;

    @FindBy(how = How.XPATH, using = "//div[@id='yui-dt9-th-deltaCountFilter-liner']/input")
    public static WebElement deltaCountFilter;

    @FindBy(how = How.XPATH, using = "//a[@href='yui-dt9-href-deltaCount']")
    public static WebElement deltaCountSort;

    @FindBy(how = How.XPATH, using = "//div[@id='yui-dt9-th-groupNameFilter-liner']/input")
    public static WebElement groupNameFilter;

    @FindBy(how = How.XPATH, using = "//div[@id='yui-dt9-th-tagalongFilter-liner']/input")
    public static WebElement tagalongFilter;

    @FindBy(how = How.XPATH, using = "//div[@id='yui-dt9-th-isCpdDateProvidedFilter-liner']/input")
    public static WebElement cpdDateFilter;

    public static final String SECTION_DELTA_GROUPING_TOOLBAR_BUTTONS = "//div[@id='toolbarContainer']//ul[@class='first-of-type']//li[%s]//a";
    public static final String SECTION_DELTA_GROUPING_BOTTOM_BUTTONS = "//div[@id='bottom']/div//span[%s]/span/button";

}
