package com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class FooterToolsElements
{
        public static final String SELECT_BUTTON = "//span[@id='select-menu-button']";
        public static final String SELECT_ALL_ON_PAGE_DROPDOWN = "//a[text()='Select All on Page']";

        public static final String CURRENT_VIEW = "//span[contains(text(),'Current view:')]";

        public static final String REPOPULATE_VIEW_BUTTON_XPATH = "//img[@id='repopulateViewButton']";

        @FindBy(how = How.XPATH, using = "//div[@id='contentMainTabArea']/div[@id='tabbedNavigation']/input")
        public static WebElement refreshButtonTop;

        @FindBy(how = How.XPATH, using = "//button[@id='select-menu-button-button']")
        public static WebElement selectButton;

        @FindBy(how = How.XPATH, using = "//a[contains(text(),'Select All on Page')]")
        public static WebElement selectAllOnPageButton;

        public static final String REFRESH_BUTTON_BOTTOM = "//span[contains(@id,'yui-pg0-1-page-report')]//span[@class='refresh-button-container']//button";

        @FindBy(how = How.XPATH, using = "//span[contains(@id,'yui-pg0-1-page-report')]//span[@class='refresh-button-container']//button")
        public static WebElement refreshButtonBottom;

        public static final String PUBLIC_BUTTON = "//button[@id='public-view-menu-button-button']";

        @FindBy(how = How.XPATH, using = "//button[@id='public-view-menu-button-button']")
        public static WebElement publicButton;

        public static final String PRIVATE_BUTTON = "//button[@id='private-view-menu-button-button']";

        @FindBy(how = How.XPATH, using = "//button[@id='private-view-menu-button-button']")
        public static WebElement privateButton;

        public static final String JURISDICTION_BUTTON = "//button[@id='jur-view-menu-button-button']";

        @FindBy(how = How.XPATH, using = "//button[@id='jur-view-menu-button-button']")
        public static WebElement jurisdictionButton;

        public static final String JURISDICTION = "//a[text()='%s']";

        public static final String CLEAR_BUTTON = "//button[@id='clear-menu-button-button']/em";

        @FindBy(how = How.ID, using = "clear-menu-button-button")
        public static WebElement clearButton;

        @FindBy(how = How.XPATH, using = "//span[@id='clear-menu-button']/following-sibling::div//a[text()='Clear Filters']")
        public static WebElement clearFiltersFromClearButton;

        @FindBy(how = How.XPATH, using = "//span[@id='clear-menu-button']/following-sibling::div//a[text()='Clear Sort']")
        public static WebElement clearSortFromClearButton;

        @FindBy(how = How.XPATH, using = "//span[@id='clear-menu-button']/following-sibling::div//a[text()='Clear Selection']")
        public static WebElement  clearSelectionFromClearButton;

        @FindBy(how = How.XPATH, using = "//div[@class='yui-dt-paginator grid-pg-container']//span[contains(text(), 'Results')]/")
        public static WebElement results;

        public static final String RESULTS = "//div[@class='yui-dt-paginator grid-pg-container']//span[contains(text(), 'Results')]";

        @FindBy(how = How.ID, using = "findDocumentButton")
        public static WebElement findDocumentButton;

        @FindBy(how = How.ID, using = "viewManagementButton")
        public static WebElement viewManagementButton;

        @FindBy(how = How.XPATH, using = "//a[text()='Clear Selection']")
        public static WebElement clearSelectionOption;
}
