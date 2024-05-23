package com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class SourceNavigateTabsPageElements
{
    public static final String TABBED_NAVIGATION = "//div[@id='tabbedNavigation']";

    @FindBy(how = How.XPATH, using = TABBED_NAVIGATION + "/span[contains(@id,'tab') and text() = ' Delta ']")
    public static WebElement deltaTab;

    @FindBy(how = How.XPATH, using = TABBED_NAVIGATION + "/span[contains(@id,'tab') and text() = ' Rendition ']")
    public static WebElement renditionTab;

    @FindBy(how = How.XPATH, using = TABBED_NAVIGATION + "/span[contains(@id,'tab') and text() = ' Section ']")
    public static WebElement sectionTab;

    @FindBy(how = How.XPATH, using = TABBED_NAVIGATION + "/span[contains(@id,'tab') and text() = ' Section Group ']")
    public static WebElement sectionGroupTab;

    @FindBy(how = How.XPATH, using = TABBED_NAVIGATION + "/span[contains(@id,'tab') and text() = ' Delta Group ']")
    public static WebElement deltaGroupTab;

    @FindBy(how = How.XPATH, using = TABBED_NAVIGATION + "/span[contains(@id,'tab') and text() = ' Lineage ']")
    public static WebElement lineageTab;

    public static final String SELECTED_SOURCE_NAVIGATE_TAB = TABBED_NAVIGATION + "/span[contains(@id, 'tab') and text() = ' %s ' and contains(@class, 'Current')]";

    public static final String GROUPING_TAB_OF_GIVEN_LEVEL = TABBED_NAVIGATION + "/span[contains(@id,'tab') and text() = ' %s Group ']";
}
