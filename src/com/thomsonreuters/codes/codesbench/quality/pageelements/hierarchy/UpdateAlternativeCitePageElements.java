package com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class UpdateAlternativeCitePageElements
{
    public static final String UPDATE_ALTERNATIVE_CITE_PAGE_TITLE = "Update Alternative Cite";

    @FindBy(how = How.ID, using = "pageForm:defCodeName")
    public static WebElement citeKeywordDropdown;

    @FindBy(how = How.ID, using = "pageForm:defvalue")
    public static WebElement defaultCiteValueTextBox;

    @FindBy(how = How.ID, using = "pageForm:updateButton")
    public static WebElement updateButton;
}
