package com.thomsonreuters.codes.codesbench.quality.pageelements.tools.spellcheckmanager;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class SpellcheckManagerFiltersPageElements
{
    @FindBy(how = How.ID, using = "pageForm:selectedState")
    public static WebElement addRemovedFilterDropdown;

    public static final String ADD_REMOVED_FILTER_DROPDOWN_ID = "pageForm:selectedState";
    
    // Word column button
    @FindBy(how = How.LINK_TEXT, using = "Word")
    public static WebElement wordColumnButton;
    
    // Word filter textbox
    @FindBy(how = How.XPATH, using = "//input[@id='pageForm:word']")
    public static WebElement wordFilterTextbox;
    
    @FindBy(how = How.XPATH, using = "//select[@id='pageForm:selectedState']/option[@value='new']")
    public static WebElement newAddRemovedFilterOption;
    
    @FindBy(how = How.XPATH, using = "//select[@id='pageForm:selectedState']/option[@value='removed']")
    public static WebElement removedAddRemovedFilterOption;
    
    @FindBy(how = How.XPATH, using = "//select[@id='pageForm:selectedState']/option[@value='']")
    public static WebElement blankAddRemovedFilterOption;
    
    @FindBy(how = How.XPATH, using = "//a[contains(@title,'Click to sort ') and text()='Word']")
    public static WebElement wordSortAscendingDescending;
    
    @FindBy(how = How.ID, using = "pageForm:selectedState")
    public static WebElement addRemovedDropdown;
}
