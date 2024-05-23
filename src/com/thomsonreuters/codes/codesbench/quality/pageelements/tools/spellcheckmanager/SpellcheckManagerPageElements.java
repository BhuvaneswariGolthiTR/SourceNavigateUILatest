package com.thomsonreuters.codes.codesbench.quality.pageelements.tools.spellcheckmanager;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class SpellcheckManagerPageElements 
{
	public static final String SPELLCHECK_MANAGER_PAGE_TITLE = "Spellcheck Manager";
	public static final String SPELLCHECK_MANAGER_DIV_PAGE_TITLE = "//div[@class='title']/h3";
	public static final String PLEASE_WAIT = "//*[contains(text(), 'wait')]]";
    
    // Close button
    @FindBy(how = How.ID, using = "pageForm:cancelButton")
    public static WebElement closeButton;
    
    // New word field - NEW_WORD_FIELD
    @FindBy(how = How.ID, using = "pageForm:newWord")
    public static WebElement newWordField;
    
    // Add button - ADD_BUTTON
    @FindBy(how = How.ID, using = "addButton2")
    public static WebElement addButton;

    @FindBy(how = How.XPATH, using = "//div[@class='hd' and contains(@id,'dialog') and text()='Spellcheck Manager']")
    public static WebElement spellcheckManagerWindow;

    public static final String WORD_VALUES_IN_DICTIONARY_TABLE = "//tbody[@class='yui-dt-data']//td[contains(@headers,'word')]/div[text()='%s']";

    @FindBy(how = How.XPATH, using = "//div[@class='bd']//a")
    public static WebElement contextMenuRemove;

    public static final String PLUS_MINUS_BUTTON = "//tbody[@class='yui-dt-data']//td[.='%s']/..//td[contains(@headers,'id')]//input";

    public static final String WORD_STATUS = "//tbody[@class='yui-dt-data']//td[.='%s']/..//td[contains(@headers,'state')]/div";

    @FindBy(how = How.ID, using = "pageForm:showDeleted")
    public static WebElement showDeletedCheckbox;

    @FindBy(how = How.XPATH, using = "//a[contains(@title,'Click to sort ') and text()='Word']")
    public static WebElement wordSortAscendingDescending;

    @FindBy(how = How.XPATH, using = "//tr[%s]/td[contains(@headers,'yui-dt0-th-word ')]/div")
    public static WebElement wordInTableByRowNumber;

    public static final String ALL_WORDS_IN_DICTIONARY = "//td[@headers='yui-dt0-th-word ']/div";

    @FindBy(how = How.ID, using = "pageForm:word")
    public static WebElement wordFilterTextbox;

    @FindBy(how = How.ID, using = "pageForm:selectedState")
    public static WebElement addRemovedDropdown;

    @FindBy(how = How.ID, using = "//td[contains(@class,'yui-dt-col-state')]/div")
    public static WebElement wordsStates;
}
