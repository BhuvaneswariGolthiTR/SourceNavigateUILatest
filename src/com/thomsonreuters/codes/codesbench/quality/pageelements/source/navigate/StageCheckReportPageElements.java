package com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class StageCheckReportPageElements
{
    @FindBy(how = How.ID, using = "searchForm:year")
    public static WebElement yearDropdown;

    @FindBy(how = How.ID, using = "searchForm:legislation")
    public static WebElement legislationDropdown;

    @FindBy(how = How.ID, using = "searchForm:contentSetList:list1")
    public static WebElement nonSelectedContentSets;

    @FindBy(how = How.ID, using = "searchForm:contentSetList:move1to2")
    public static WebElement moveOneToSelectedButton;

    @FindBy(how = How.ID, using = "searchForm:searchButton")
    public static WebElement searchButton;

    @FindBy(how = How.ID, using = "contentSetLongNameFilter")
    public static WebElement contentSetFilter;

    @FindBy(how = How.XPATH, using = "//div[@id='shortNameAutoComplete']/input[@id='shortNameFilter']")
    public static WebElement sessionFilter;

    @FindBy(how = How.XPATH, using = "//div[@id='docTypeAutoComplete']/input[@id='docTypeFilter']")
    public static WebElement docTypeFilter;

    @FindBy(how = How.ID, using = "docNumberFilter")
    public static WebElement docNumberFilter;

    @FindBy(how = How.XPATH, using = "//div[@id='contentTypeShortNameAutoComplete']/input[@id='contentTypeShortNameFilter']")
    public static WebElement contentTypeFilter;

    @FindBy(how = How.XPATH, using = "//a[text()='Refresh']")
    public static WebElement refreshButton;
}
