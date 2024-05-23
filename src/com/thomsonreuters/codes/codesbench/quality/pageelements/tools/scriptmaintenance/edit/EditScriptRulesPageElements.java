package com.thomsonreuters.codes.codesbench.quality.pageelements.tools.scriptmaintenance.edit;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class EditScriptRulesPageElements
{
    private static final String PAGE_TITLE = "Edit Script Rules";
    private static final String DIALOG_CONTAINER_XPATH = "//mat-dialog-container/toolbox-script-edit-rules-dialog-form";
    public static String PAGE_XPATH = String.format("%s/h1[@class='mat-dialog-title' and contains(text(),%s)]", DIALOG_CONTAINER_XPATH, PAGE_TITLE);

    //script label
    @FindBy(how = How.XPATH, using = DIALOG_CONTAINER_XPATH + "//toolbox-script-edit-rules-form//p[@class='script-rules-p']")
    public static WebElement scriptLabel;
    //pubtag label
    @FindBy(how = How.XPATH, using = DIALOG_CONTAINER_XPATH + "//toolbox-script-edit-rules-form")
    public static WebElement existingPubTag;
    //script action selection
    @FindBy(how = How.XPATH, using = DIALOG_CONTAINER_XPATH + "//select[@formControlName='scriptAction']")
    public static WebElement scriptAction;
    //pubtag text box
    @FindBy(how = How.XPATH, using = DIALOG_CONTAINER_XPATH + "//input[@formControlName='pubtag']")
    public static WebElement pubtagInput;
    //start mnemonic text box
    @FindBy(how = How.XPATH, using = DIALOG_CONTAINER_XPATH + "//input[@formControlName='mnemonicStart']")
    public static WebElement startMnemonic;
    //end mnemonic text box
    @FindBy(how = How.XPATH, using = DIALOG_CONTAINER_XPATH + "//input[@formControlName='mnemonicEnd']")
    public static WebElement endMnemonic;
    //start phrase text box
    @FindBy(how = How.XPATH, using = DIALOG_CONTAINER_XPATH + "//input[@formControlName='phraseStart']")
    public static WebElement startPhrase;
    //end phrase text box
    @FindBy(how = How.XPATH, using = DIALOG_CONTAINER_XPATH + "//input[@formControlName='phraseEnd']")
    public static WebElement endPhrase;
    //start phrase position selection
    @FindBy(how = How.XPATH, using = DIALOG_CONTAINER_XPATH + "//select[@formControlName='phraseStartPosition']")
    public static WebElement startPhrasePosition;
    //end phrase position selection
    @FindBy(how = How.XPATH, using = DIALOG_CONTAINER_XPATH + "//select[@formControlName='phraseEndPosition']")
    public static WebElement endPhrasePosition;

    //assign pubtag to end mnemonic phrase check box
    public static final String ASSIGN_PUBTAG_TO_END_MNEMONIC_OR_PHRASE_CHECKBOX = DIALOG_CONTAINER_XPATH + "//input[@id='mat-checkbox-2-input']";

    //add at end button
    public static final String ADD_AT_END_BUTTON = "//div[@class='rule-actions']//button[span[contains(text(),'Add At End')]]";
    //add before button
    public static final String ADD_BEFORE_BUTTON = "//div[@class='rule-actions']//button[span[contains(text(),'Add Before')]]";
    //add after button
    public static final String ADD_AFTER_BUTTON = "//div[@class='rule-actions']//button[span[contains(text(),'Add After')]]";
    //update selected rule button
    public static final String UPDATE_SELECTED_RULE_BUTTON = "//div[@class='rule-actions']//button[span[contains(text(),'Update Selected Rule')]]";

    //cancel button
    public static final String CANCEL_BUTTON = "//div[@class='mat-dialog-actions']//button[span[contains(text(),'Cancel')]]";
    //save button
    public static final String SAVE_BUTTON = "//div[@class='mat-dialog-actions']//button[span[contains(text(),'Save')]]";

    //get script rule text with index
    public static final String GET_SCRIPT_RULE_TEXT_WITH_INDEX = "//div[@class='script-rules-list-container']//tr[%s]//td[3]";
    //script rule with text
    public static final String SCRIPT_RULE_GIVEN_TEXT = "//div[@class='script-rules-list-container']//td[contains(text(),'%s')]";

    //edit script rules button
    public static final String EDIT_SCRIPT_LIST_MENU_BUTTON_WITH_INDEX = "//div[@class='script-rules-list-container']//tr[%s]//button";
    //edit script list select button
    public static final String EDIT_SCRIPT_LIST_SELECT_BUTTON_WITH_INDEX = "//div[@class='script-rules-list-container']//tr[%s]//mat-checkbox";
    //move up
    public static final String MOVE_UP = "//button[span[contains(text(),'Move Up')]]";
    //move down
    public static final String MOVE_DOWN = "//button[span[contains(text(),'Move Down')]]";
    //add indent
    public static final String ADD_INDENT = "//button[span[contains(text(),'Add Indent')]]";
    //remove indent
    public static final String REMOVE_INDENT = "//button[span[contains(text(),'Remove Indent')]]";
    //select delete rule
    public static final String DELETE_RULE = "//button[span[contains(text(),'Delete Rule')]]";
    //delete rule and children
    public static final String DELETE_RULE_AND_CHILDREN = "//button[contains(text(),'Delete Rule and Children')]";
    //delete rule only
    public static final String DELETE_RULE_ONLY = "//button[contains(text(),'Delete Rule Only')]";

}
