package com.thomsonreuters.codes.codesbench.quality.pageelements.nodAngular;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class SubscribedCasesPageElementsAngular {

    public static final String HEADING = "Subscribed Cases";
    public static final String PAGE_TITLE = "NodClassifyUi";
    public static final String URL_MODIFIER = "subscribedCases";
    public static final String PAGE_TAG = "//body/app-root/app-subscribed-cases";
    public static final String GRID = "//ag-grid-angular";

    public static final String HORIZONTAL_SCROLL = "//div[@class='ag-body-horizontal-scroll-viewport']";

    @FindBy(how = How.XPATH, using = "//button[text()='Clear Filters']")
    public static WebElement clearFilters;

    @FindBy(how = How.XPATH, using = "//button[text()='Clear Sort']")
    public static WebElement clearSort;

    @FindBy(how = How.XPATH, using = "//div[@class='search-by-box']//label[text()=' Search by Case serial ']/input")
    public static WebElement SearchByCaseSerial;

    public static final String COLUMN_HEADERS_TEXT_XPATH = "//div[@class='ag-header-row']//span[@class='ag-header-cell-text']";

}

