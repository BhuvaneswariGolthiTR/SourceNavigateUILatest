package com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.popups.topicalHeadingHighlight;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class TopicalHeadingHighlightPageElements
{
    public static final String TOPICAL_HEADING_HIGHLIGHT_PAGE_TITLE = "Topical Heading/Highlight";
    public static final String UNSELECTED_PHRASES_LIST_OPTION = "//select[@id='pageForm:list1']/option[@value='%s']";
    public static final String SELECTED_PHRASES_LIST_OPTION = "//select[@id='pageForm:list2']/option[@value='%s']";
    public static final String INDEX_ENTRY_LIST_OPTION = "//select[@id='pageForm:indexEntryList']/option[@value='%s']";
    public static final String SELECTED_PHRASES_LIST_ITEM = "//select[@id='pageForm:list2']/option";
    public static final String UNSELECTED_PHRASES_LIST = "//select[@id='pageForm:list1']";
    public static final String CREATE_TOPICAL_HEADING_BUTTON = "//input[@value='Create Topical Heading']";
    public static final String TOPICAL_HEADING_TEXT_XPATH = "//span[@id='pageForm:outTopicalHeading' and text()='%s']";
    public static final String TOPICAL_HEADING_TEXT = "//span[@id='pageForm:outTopicalHeading']";

    @FindBy(how = How.XPATH, using = "//fieldset[@id='topicalHeading']//input[@value='Create Topical Heading']")
    public static WebElement createTopicalHeadingButton;

    @FindBy(how = How.ID, using = "pageForm:upbutton")
    public static WebElement upButton;

    @FindBy(how = How.ID, using = "pageForm:downButton")
    public static WebElement downButton;

    @FindBy(how = How.ID, using = "pageForm:show")
    public static WebElement userAddedButton;

    @FindBy(how = How.XPATH, using = "//input[@type='button' and @value='Create Index Entry']")
    public static WebElement createIndexEntryButton;

    @FindBy(how = How.XPATH, using = "//input[@type='submit' and @value='Delete Phrase']")
    public static WebElement deletePhraseButton;

    @FindBy(how = How.ID, using = "pageForm:deleteIndexButton")
    public static WebElement deleteIndexEntryButton;

    @FindBy(how = How.ID, using = "pageForm:addContentButton")
    public static WebElement okButton;

    @FindBy(how = How.ID, using = "pageForm:cancelButton")
    public static WebElement cancelButton;

    @FindBy(how = How.XPATH, using = "//div[@id='tabbedNavigation']/span[contains(text() = 'Topical Heading')]")
    public static WebElement topicalHeadingTab;

    @FindBy(how = How.XPATH, using = "//div[@id='tabbedNavigation']/span[contains(text(),'Index Entry')]")
    public static WebElement indexEntryTab;
}
