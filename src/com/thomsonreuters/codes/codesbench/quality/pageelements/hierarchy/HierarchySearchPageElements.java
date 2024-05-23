package com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class HierarchySearchPageElements
{
    public static final String QUICK_SEARCH_KEYWORD_OPTION = "//select[@id='topPageForm:keywordInput']/option[text()='%s']";

    // Top search tabs
    @FindBy(how = How.ID, using = "findNodeLabel")
    public static WebElement findNodeLabel;

    @FindBy(how = How.ID, using = "findContentLabel")
    public static WebElement findContentLabel;

    @FindBy(how = How.ID, using = "findHIDLabel")
    public static WebElement findHidLabel;

    @FindBy(how = How.ID, using = "findVolsLabel")
    public static WebElement findVolsLabel;

    @FindBy(how = How.ID, using = "findCiteLabel")
    public static WebElement findCiteLabel;

    @FindBy(how = How.ID, using = "findTemplatesLabel")
    public static WebElement findTemplatesLabel;

    @FindBy(how = How.ID, using = "quickSearchLabel")
    public static WebElement quickSearchLabel;

    // Quick Search
    @FindBy(how = How.ID, using = "topPageForm:keywordInput")
    public static WebElement quickSearchKeywordList;

    @FindBy(how = How.ID, using = "topPageForm:valueInput")
    public static WebElement quickSearchInput;

    @FindBy(how = How.ID, using = "topPageForm:codeInput")
    public static WebElement quickSearchCodeDropdown;

    @FindBy(how = How.ID, using = "topPageForm:beginsWith")
    public static WebElement quickSearchBeginsWithCheckbox;

    @FindBy(how = How.ID, using = "topPageForm:findQuickButton")
    public static WebElement quickSearchFindButton;
    
    // Find Node
    @FindBy(how = How.XPATH, using = "//*[@value='Prev Result']")
    public static WebElement nodeFindPrevResult;

    @FindBy(how = How.ID, using = "topPageForm:findByNodeUuidButton")
    public static WebElement findByNodeUuidButton;

    public static final String FIND_BY_NODE_UUID_BUTTON = "//*[@id='topPageForm:findByNodeUuidButton']";

    @FindBy(how = How.ID, using = "topPageForm:nodeUuidInput")
    public static WebElement nodeUuidInput;

    public static final String NODE_UUID_INPUT = "//input[@id='topPageForm:nodeUuidInput']";

    // Find Content
    @FindBy(how = How.ID, using = "topPageForm:findByContentUuidButton")
    public static WebElement contentFindResult;

    @FindBy(how = How.ID, using = "topPageForm:contentUuidInput")
    public static WebElement contentFindInput;

    // Find HID
    @FindBy(how = How.ID, using = "topPageForm:findByHeadingIdButton")
    public static WebElement hidFindResult;

    @FindBy(how = How.ID, using = "topPageForm:headingIdInput")
    public static WebElement hidFindInput;

    // Find Vols
    @FindBy(how = How.ID, using = "topPageForm:jumpToBeginOfVolume")
    public static WebElement volsFindResult;

    @FindBy(how = How.ID, using = "topPageForm:volumeInput")
    public static WebElement volsFindInput;

    // Find Cite
    @FindBy(how = How.ID, using = "pageForm:kwd1")
    public static WebElement findCiteFirstKeywordDropdown;

    @FindBy(how = How.ID, using = "pageForm:value1")
    public static WebElement findCiteFirstValueInput;

    @FindBy(how = How.ID, using = "pageForm:updateButton")
    public static WebElement findCiteFindButton;

    // Find Templates
    @FindBy(how = How.XPATH, using = "//td[contains(text(), 'ID ST')]/input[@type='text']")
    public static WebElement findTemplatesSearchField;

    @FindBy(how = How.XPATH, using = "//td[contains(text(), 'ID ST')]/input[@type='submit']")
    public static WebElement findTemplatesGoButton;

    // Search results windows items
    @FindBy(how = How.XPATH, using = "//tr[@class='yui-dt-rec yui-dt-first yui-dt-even']/td[2]/div/a")
    public static WebElement previousResultsFirstItemInGrid;

    @FindBy(how = How.XPATH, using = "//td[contains(@class,'NodeUUID')]/div/a")
    public static WebElement findResultsAllUuidsInGrid;

    @FindBy(how = How.ID, using = "pageForm:text")
    public static WebElement findCiteSearchResultSummaryInFindResultsWindow;
}
