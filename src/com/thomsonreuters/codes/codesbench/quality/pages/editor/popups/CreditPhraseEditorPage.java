package com.thomsonreuters.codes.codesbench.quality.pages.editor.popups;

import com.thomsonreuters.codes.codesbench.quality.pageelements.CommonPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.popups.CreditPhraseEditorPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.popups.CreditPhraseEditorPageElements.EFF_DATE_PHRASE_ID;

@Component
public class CreditPhraseEditorPage extends BasePage
{
    private WebDriver driver;

    @Autowired
    public CreditPhraseEditorPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, CreditPhraseEditorPageElements.class);
    }

    public void inputPhraseInBeginningOfCredit(String phrase)
    {
        checkCheckbox(CreditPhraseEditorPageElements.BEGINNING_PHRASE_CHECKBOX);
        isCheckboxChecked(CreditPhraseEditorPageElements.BEGINNING_PHRASE_CHECKBOX);
        sendTextToTextbox(CreditPhraseEditorPageElements.BEGINNING_PHRASE_TEXTBOX, phrase);
    }

    public void inputPhraseInSection(String phrase)
    {
       sendTextToTextbox(CreditPhraseEditorPageElements.SECTIONS, phrase);
    }

    public boolean selectPhraseFromEffectiveDatePhraseDropdown(String phrase)
    {
        click(CreditPhraseEditorPageElements.EFF_DATE_PHRASE_CHECKBOX);
        selectDropdownOptionUsingJavascript(CreditPhraseEditorPageElements.EFF_DATE_PHRASE_ID, phrase);
        JavascriptExecutor javascriptExecutor = (JavascriptExecutor)driver;
        javascriptExecutor.executeScript("arguments[0].onchange()", getElement(CreditPhraseEditorPageElements.EFF_DATE_PHRASE_SELECT));
        return checkFieldValueIsExpectedOne(getElement(CreditPhraseEditorPageElements.EFF_DATE_PHRASE_TEXTBOX), phrase);
    }

    public void inputEffectiveDate(String date)
    {
        click(CreditPhraseEditorPageElements.EFF_DATE_MANUAL_INPUT_RADIOBUTTON);
        click(CreditPhraseEditorPageElements.EFF_DATE_MANUAL_INPUT_TEXTBOX);
        sendKeysToElement(CreditPhraseEditorPageElements.EFF_DATE_MANUAL_INPUT_TEXTBOX, date);
    }

    public void clickSave()
    {
        click(CommonPageElements.SAVE_BUTTON);
    }

    public void clickCancel()
    {
        click(CommonPageElements.CANCEL_BUTTON);
    }

    public String getSectionsValue()
    {
        return getElementsAttribute(CreditPhraseEditorPageElements.SECTIONS_USCA, "value");
    }

    public String getStatPageValue()
    {
        return getElementsAttribute(CreditPhraseEditorPageElements.STAT_PAGE_USCA, "value");
    }
}
