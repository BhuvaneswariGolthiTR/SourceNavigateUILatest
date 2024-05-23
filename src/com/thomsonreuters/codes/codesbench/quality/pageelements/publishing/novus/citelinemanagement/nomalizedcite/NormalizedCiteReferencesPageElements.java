package com.thomsonreuters.codes.codesbench.quality.pageelements.publishing.novus.citelinemanagement.nomalizedcite;

import com.thomsonreuters.codes.codesbench.quality.pageelements.publishing.novus.citelinemanagement.CiteLineManagementsCommonPageElements;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class NormalizedCiteReferencesPageElements extends CiteLineManagementsCommonPageElements
{
    public static final String COLUMN_HEADER = "//div[contains(@class, 'ag-header-row')]";
    public static final String NORMALIZED_CITE_USING_CITATION_PREFIX = "//div[@class='ag-center-cols-container']//div[text()='%s']";
    public static final String DELETE_ALERT_TEXT = "//toolbox-delete-norm-cites//p[text()='Are you sure you want to delete this normalized cite?']";
    public static final String CITATION_PREFIX_COLUMN = "//div[contains(@class, 'ag-header-row')]//div[@col-id='citationPrefix']";
    public static final String CONDENSED_PREFIX_COLUMN = "//div[contains(@class, 'ag-header-row')]//div[@col-id='condensedPrefix']";
    public static final String CONDENSED_PREFIX_MENU_XPATH = CONDENSED_PREFIX_COLUMN + "//span[contains(@class,'ag-icon-menu')]";
    public static final String CITATION_PREFIX_MENU_XPATH = CITATION_PREFIX_COLUMN + "//span[contains(@class,'ag-icon-menu')]";
    public static final String CITATION_PREFIX_BY_ROW_INDEX = "//div[@row-index='%s']//div[@col-id='citationPrefix']";

    public static final String CITATION_PREFIX_BY_ROW_ID = "//div[@row-id='%s']//div[@col-id='citationPrefix']";
    public static final String CONDENSED_PREFIX_BY_ROW_ID = "//div[@row-id='%s']//div[@col-id='condensedPrefix']";
    public static final String MODIFIED_BY_BY_ROW_ID = "//div[@row-id='%s']//div[@col-id='modifiedBy']";
    public static final String MODIFIED_DATE_BY_ROW_ID = "//div[@row-id='%s']//div[@col-id='modifiedDate']";
    public static final String COMMENTS_BY_ROW_ID = "//div[@row-id='%s']//div[@col-id='comments']";

    public static final String CITATION_PREFIX_PARENT_XPATH =  "//div[contains(@class,'ag-center-cols-container')]//div[@col-id='citationPrefix' and text()='%s']/..";
    public static final String ADD_NEW_NORMALIZED_CITE_BUTTON = "//toolbox-cite-line//mat-tab-body//button[contains(text(),'Add New Normalized Cite')]";

    @FindBy(how = How.ID, using = "addNewCiteLine")
    public static WebElement addNewNormalizedCite;

    @FindBy(how = How.XPATH, using = COLUMN_HEADER + "//div[contains(@class,'ag-header-cell') and @col-id='citationPrefix']")
    public static WebElement citationPrefixHeader;

    @FindBy(how = How.XPATH, using = COLUMN_HEADER + "//div[contains(@class,'ag-header-cell') and @col-id='condensedPrefix']")
    public static WebElement condensedPrefixHeader;

    @FindBy(how = How.XPATH, using = COLUMN_HEADER + "//div[contains(@class,'ag-header-cell') and @col-id='modifiedBy']")
    public static WebElement modifiedByHeader;

    @FindBy(how = How.XPATH, using = COLUMN_HEADER + "//div[contains(@class,'ag-header-cell') and @col-id='modifiedDate']")
    public static WebElement modifiedDateHeader;
}
