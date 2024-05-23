package com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class FindCitePageElements
{
    public static final String FIND_CITE_PAGE_TITLE = "Find Cite";

    @FindBy(how = How.ID, using = "pageForm:kwd1")
    public static WebElement findCiteFirstKeywordDropdown;

    @FindBy(how = How.ID, using = "pageForm:value1")
    public static WebElement findCiteFirstValueInput;

    @FindBy(how = How.ID, using = "pageForm:updateButton")
    public static WebElement findCiteFindButton;
}
