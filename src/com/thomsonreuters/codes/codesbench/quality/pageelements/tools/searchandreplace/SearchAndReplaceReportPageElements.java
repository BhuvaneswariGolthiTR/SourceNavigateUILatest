package com.thomsonreuters.codes.codesbench.quality.pageelements.tools.searchandreplace;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class SearchAndReplaceReportPageElements
{
    public static final String SEARCH_AND_REPLACE_REPORT_PAGE_URL = "searchAndReplaceWeb/genericModalPopup";
    public static final String NODE_UUID_XPATH = "//tr[@class='document-info']//table[@class='document-info-table']/tbody/tr/td/a[contains(text(), '%s')]";
    public static final String BEFORE_CHANGE_HIGHLIGHTED_TEXT_XPATH = "//tr[contains(@class, 'difference')]/td[1]/span[contains(text(), '%s')]";
    public static final String AFTER_CHANGE_HIGHLIGHTED_TEXT_XPATH = "//tr[contains(@class, 'difference')]/td[2]/span[contains(text(), '%s')]";

    @FindBy(how = How.XPATH, using = "//input[@value='Commit']")
    public static WebElement commitButton;
}
