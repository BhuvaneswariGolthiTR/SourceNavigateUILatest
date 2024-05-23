package com.thomsonreuters.codes.codesbench.quality.pageelements.publishing.novus.citelinemanagement.citelinereferences;

import com.thomsonreuters.codes.codesbench.quality.pageelements.publishing.novus.citelinemanagement.CiteLineManagementsCommonPageElements;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class CiteLineReferencesPageElements extends CiteLineManagementsCommonPageElements
{
    public static final String COLUMN_HEADER = "//div[contains(@class,'ag-header-row')]";

    public static final String CT1_COLUMN_HEADER = COLUMN_HEADER + "//div[contains(@class,'ag-header-cell') and @col-id='ct1Num']";
    public static final String CT1_MENU_BUTTON = CT1_COLUMN_HEADER + "//span[contains(@class,'ag-icon-menu')]";
    public static final String ORIGINAL_FIRST_LINE_CITE_MENU_BUTTON = COLUMN_HEADER + "//div[contains(@class,'ag-header-cell') and @col-id='originalFirstLineCite']//span[contains(@class,'ag-icon-menu')]";

    public static final String CT1_COLUMN_BY_ROW_INDEX = "//div[@row-index='%s']//div[@col-id='ct1Num']";
    public static final String CT1 = "//div[@class='ag-center-cols-clipper']//div[@col-id='ct1Num' and text()='%s']";

    public static final String LAST_CT1_IN_GRID = "//div[@class='ag-center-cols-clipper']//div[contains(@class,'ag-row-last')]";
    public static final String ADD_NEW_CITELINE_REFERENCES = "//toolbox-cite-line//mat-tab-body//button[contains(text(),'Add New Cite Line References')]";
    public static final String DELETE_ALERT_TEXT = "//toolbox-delete-citeline-ref//p[text()='Are you sure you want to delete this CT1 entry? Doing so may affect the publishing of any nodes assigned this CT1 value.']";
    public static final String CT1_DIV_PARENT_XPATH =  "//div[contains(@class,'ag-center-cols-container')]//div[@col-id='ct1Num' and text()='%s']/..";
    public static final String COMMENTS = CT1 + "/following-sibling::div[@col-id='comments']";
    public static final String DELETE_BUTTON = "//div[@row-id='%s']//button[@title='Delete']";

    public static final String CT1_FIELD = "//div[@class='ag-center-cols-clipper']//div[@col-id='ct1Num']";
    public static final String ORIGINAL_FIRST_LINE_CITE = CT1 + "/following-sibling::div[@col-id='originalFirstLineCite']";
    public static final String FIRST_LINE_CITE = CT1 + "/following-sibling::div[@col-id='firstLineCite']";
    public static final String SECOND_LINE_CITE_PRE = CT1 + "/following-sibling::div[@col-id='secondLineCitePre']";
    public static final String SECOND_LINE_CITE_APP = CT1 + "/following-sibling::div[@col-id='secondLineCiteApp']";
    public static final String EXPANDED_CITE_PRE = CT1 + "/following-sibling::div[@col-id='expandedCitePre']";
    public static final String EXPANDED_CITE_APP = CT1 + "/following-sibling::div[@col-id='expandedCiteApp']";
    public static final String FORMER_CITE = CT1 + "/following-sibling::div[@col-id='formerCite']";
    public static final String MODIFIED_BY = CT1 + "/following-sibling::div[@col-id='modifiedBy']";
    public static final String MODIFIED_DATE = CT1 + "/following-sibling::div[@col-id='modifiedDate']";

    @FindBy(how = How.ID, using = "addNewCitelineRef")
    public static WebElement addNewCitelineReferences;

    @FindBy(how = How.XPATH, using = COLUMN_HEADER + "//div[@col-id='ct1Num']")
    public static WebElement ct1Header;

    @FindBy(how = How.XPATH, using ="//div[@class='ag-center-cols-clipper']//div[@col-id='ct1Num' and text()='%s']")
    public static WebElement ct1;

    @FindBy(how = How.XPATH, using = COLUMN_HEADER + "//div[contains(@class,'ag-header-cell') and @col-id='originalFirstLineCite']")
    public static WebElement originalFirstLineCiteHeader;

    @FindBy(how = How.XPATH, using = COLUMN_HEADER + "//div[contains(@class,'ag-header-cell') and @col-id='firstLineCite']")
    public static WebElement firstLineCiteHeader;

    @FindBy(how = How.XPATH, using = COLUMN_HEADER + "//div[contains(@class,'ag-header-cell') and @col-id='secondLineCitePre']")
    public static WebElement secondLineCitePreHeader;

    @FindBy(how = How.XPATH, using = COLUMN_HEADER + "//div[contains(@class,'ag-header-cell') and @col-id='secondLineCiteApp']")
    public static WebElement secondLineCiteAppHeader;

    @FindBy(how = How.XPATH, using = COLUMN_HEADER + "//div[contains(@class,'ag-header-cell') and @col-id='expandedCitePre']")
    public static WebElement expandedCitePre;

    @FindBy(how = How.XPATH, using = COLUMN_HEADER + "//div[contains(@class,'ag-header-cell') and @col-id='expandedCiteApp']")
    public static WebElement expandedCiteAppHeader;

    @FindBy(how = How.XPATH, using = COLUMN_HEADER + "//div[contains(@class,'ag-header-cell') and @col-id='formerCite']")
    public static WebElement formerCiteHeader;
}
