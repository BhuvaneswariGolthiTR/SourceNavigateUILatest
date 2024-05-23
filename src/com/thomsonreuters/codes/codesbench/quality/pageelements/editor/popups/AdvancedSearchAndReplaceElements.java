package com.thomsonreuters.codes.codesbench.quality.pageelements.editor.popups;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class AdvancedSearchAndReplaceElements 
{
    public static final String PAGE_TITLE = "Single Document SAR";

    public static final String PUBTAGS_LIST_PAGE_TITLE = "Select Pubtags";

    public static final String PUBTAGS_SEARCH_TEXTAREA = "//textarea[@id='pageForm:searchPubtags']";

    public static final String MNEMONIC_TO_SEARCH = "//input[@id='pageForm:searchMnemonic']";

    public static final String MNEMONIC_TO_REPLACE_WITH = "//input[@id='pageForm:replaceMnemonic']";

    public static final String SEARCH_PHRASE_TEXTAREA = "//textarea[@id='pageForm:searchPhrase']";

    public static final String REPLACE_PHRASE_TEXTAREA = "//textarea[@id='pageForm:replacePhrase']";

    public static final String BEFORE_CHANGE = "//tr[contains(@class,'difference')]";

    public static final String AFTER_CHANGE = "//tr[contains(@class,'difference')]";

    public final static String ITEMS_WITH_ASAR_FOCUS = "//span[@class='hit' and text()='%s']";

    public static final String BEFORE_CHANGE_ITEMS_WITH_ASAR_FOCUS = BEFORE_CHANGE + ITEMS_WITH_ASAR_FOCUS;

    public static final String AFTER_CHANGE_ITEMS_WITH_ASAR_FOCUS = AFTER_CHANGE + ITEMS_WITH_ASAR_FOCUS;

    public static final String COMMIT_BUTTON = "//input[@value='Commit']";

    @FindBy(how = How.XPATH, using = "//input[@id='pageForm:SAR']")
	public static WebElement searchAndReplaceButton;
	
	@FindBy(how = How.XPATH, using = "//input[@id='pageForm:findSearchPubtags']")
	public static WebElement searchForPubtagsListButton;
	
	@FindBy(how = How.XPATH, using = "//input[@id='pageForm:findReplacePubtags']")
	public static WebElement showPubtagsListButtonForReplace;
	
	@FindBy(how = How.XPATH, using = "//textarea[@id='pageForm:searchPhrase']")
	public static WebElement searchPhraseTextArea;
	
	@FindBy(how = How.XPATH, using = "//textarea[@id='pageForm:replacePhrase']")
	public static WebElement replacePhraseTextArea;

    @FindBy(how = How.XPATH, using = "//textarea[@id='pageForm:searchPubtags']")
	public static WebElement pubtagsSearchTextArea;
    
    @FindBy(how = How.XPATH, using = "//textarea[@id='pageForm:replacePubtags']")
  	public static WebElement pubtagsReplaceTextArea;
    
    @FindBy(how = How.ID, using = "pageForm:SCAN")
	public static WebElement scanButton;

    @FindBy(how = How.XPATH, using = "//input[@id='pageForm:ok' or @id='pageForm:okButton' or @value='OK'] | //button[@id='bt_ok']")
    public static WebElement okButton;
}
