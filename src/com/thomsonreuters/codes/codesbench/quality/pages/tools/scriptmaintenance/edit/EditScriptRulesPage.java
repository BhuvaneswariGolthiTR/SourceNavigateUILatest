package com.thomsonreuters.codes.codesbench.quality.pages.tools.scriptmaintenance.edit;

import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.scriptmaintenance.edit.EditScriptRulesPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.scriptmaintenance.page.ScriptMaintenancePageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class EditScriptRulesPage extends BasePage {

    private WebDriver driver;

    @Autowired
    public EditScriptRulesPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, EditScriptRulesPageElements.class);
    }


    public void setScriptAction(String scriptAction)
    {
        selectDropdownOption(EditScriptRulesPageElements.scriptAction, scriptAction);
        waitForPageLoaded();
        waitForProgressBarGone();
    }

    public void setPubtag(String pubtag)
    {
        sendKeysToElement(EditScriptRulesPageElements.pubtagInput, pubtag);
        waitForPageLoaded();
        waitForProgressBarGone();
    }

    public void setStartMnemonic(String startMnemonic)
    {
        clearAndSendKeysToElement(EditScriptRulesPageElements.startMnemonic, startMnemonic);
        waitForPageLoaded();
        waitForProgressBarGone();
    }

    public void setEndMnemonic(String endMnemonic)
    {
        clearAndSendKeysToElement(EditScriptRulesPageElements.endMnemonic, endMnemonic);
        waitForPageLoaded();
        waitForProgressBarGone();
    }

    public void setStartPhrase(String startPhrase)
    {
        sendKeysToElement(EditScriptRulesPageElements.startPhrase, startPhrase);
        waitForPageLoaded();
        waitForProgressBarGone();
    }

    public void setEndPhrase(String endPhrase)
    {
        sendKeysToElement(EditScriptRulesPageElements.endPhrase, endPhrase);
        waitForPageLoaded();
        waitForProgressBarGone();
    }

    public void setStartPhrasePosition(String startPhrasePosition)
    {
        sendKeysToElement(EditScriptRulesPageElements.startPhrasePosition, startPhrasePosition);
        waitForPageLoaded();
        waitForProgressBarGone();
    }

    public void setEndPhrasePosition(String endPhrasePosition)
    {
        sendKeysToElement(EditScriptRulesPageElements.endPhrasePosition, endPhrasePosition);
        waitForPageLoaded();
        waitForProgressBarGone();
    }

    public boolean doesScriptRuleExistGivenText(String text)
    {
        return doesElementExist(String.format(EditScriptRulesPageElements.SCRIPT_RULE_GIVEN_TEXT,text));
    }

    public String getScriptRuleText(String index)
    {
        return getElementsText(String.format(EditScriptRulesPageElements.GET_SCRIPT_RULE_TEXT_WITH_INDEX,index));
    }


    public void clickAddBeforeButton()
    {
        click(EditScriptRulesPageElements.ADD_BEFORE_BUTTON);
        waitForPageLoaded();
        waitForProgressBarGone();
    }

    public void clickAddAfterButton()
    {
        click(EditScriptRulesPageElements.ADD_AFTER_BUTTON);
        waitForPageLoaded();
        waitForProgressBarGone();
    }

    public void clickAddAtEndButton()
    {
        click(EditScriptRulesPageElements.ADD_AT_END_BUTTON);
        waitForPageLoaded();
        waitForProgressBarGone();
    }

    public void clickUpdateSelectedRuleButton()
    {
         click(EditScriptRulesPageElements.UPDATE_SELECTED_RULE_BUTTON);
         waitForPageLoaded();
        waitForProgressBarGone();
    }

    public void clickSave()
    {
        click(EditScriptRulesPageElements.SAVE_BUTTON);
        waitForProgressBarGone();
        waitForPageLoaded();
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TEN_SECONDS);
    }

    public void clickCancel()
    {
        click(EditScriptRulesPageElements.CANCEL_BUTTON);
        waitForProgressBarGone();
        waitForPageLoaded();
    }

    private void waitForProgressBarGone()
    {
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        int counter = 0;
        while(isElementDisplayed(ScriptMaintenancePageElements.PROGRESS_BAR) && counter < 30)
        {
            DateAndTimeUtils.takeNap(DateAndTimeUtils.ONE_SECOND);
            counter++;
        }
        Assertions.assertTrue(counter <= 30, "Progress bar disappeared in 30 seconds");
    }

    public void selectScriptRuleGivenIndex(String index)
    {
        click(String.format(EditScriptRulesPageElements.EDIT_SCRIPT_LIST_SELECT_BUTTON_WITH_INDEX,index));
        waitForProgressBarGone();
        waitForPageLoaded();
    }

    public void openMenuOfAScriptRuleGivenIndex(String index)
    {
        click(String.format(EditScriptRulesPageElements.EDIT_SCRIPT_LIST_MENU_BUTTON_WITH_INDEX,index));
        waitForProgressBarGone();
        waitForPageLoaded();
    }

    public void moveUpAtIndex(String index)
    {
        click(String.format(EditScriptRulesPageElements.EDIT_SCRIPT_LIST_MENU_BUTTON_WITH_INDEX,index));
        click(EditScriptRulesPageElements.MOVE_UP);
        waitForProgressBarGone();
    }

    public void moveDownAtIndex(String index)
    {
        click(String.format(EditScriptRulesPageElements.EDIT_SCRIPT_LIST_MENU_BUTTON_WITH_INDEX,index));
        click(EditScriptRulesPageElements.MOVE_DOWN);
        waitForProgressBarGone();
    }

    public void deleteScriptRuleOnlyAtIndex(String index)
    {
        click(String.format(EditScriptRulesPageElements.EDIT_SCRIPT_LIST_MENU_BUTTON_WITH_INDEX,index));
        click(String.format(EditScriptRulesPageElements.DELETE_RULE));
        click(String.format(EditScriptRulesPageElements.DELETE_RULE_ONLY));
        waitForPageLoaded();
        waitForProgressBarGone();
    }

    public void deleteScriptRuleAndChildrenAtIndex(String index)
    {
        click(String.format(EditScriptRulesPageElements.EDIT_SCRIPT_LIST_MENU_BUTTON_WITH_INDEX,index));
        click(String.format(EditScriptRulesPageElements.DELETE_RULE));
        click(String.format(EditScriptRulesPageElements.DELETE_RULE_AND_CHILDREN));
        waitForPageLoaded();
        waitForProgressBarGone();
    }

    public void addIndentAtIndex(String index)
    {
        click(String.format(EditScriptRulesPageElements.EDIT_SCRIPT_LIST_MENU_BUTTON_WITH_INDEX,index));
        click(EditScriptRulesPageElements.ADD_INDENT);
        waitForPageLoaded();
        waitForProgressBarGone();
    }

    public void removeIndentAtIndex(String index)
    {
        click(String.format(EditScriptRulesPageElements.EDIT_SCRIPT_LIST_MENU_BUTTON_WITH_INDEX,index));
        click(EditScriptRulesPageElements.REMOVE_INDENT);
        waitForPageLoaded();
        waitForProgressBarGone();
    }

}
