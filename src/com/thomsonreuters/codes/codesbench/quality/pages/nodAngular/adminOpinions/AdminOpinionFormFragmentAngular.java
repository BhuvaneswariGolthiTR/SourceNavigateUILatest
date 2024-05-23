package com.thomsonreuters.codes.codesbench.quality.pages.nodAngular.adminOpinions;

import com.thomsonreuters.codes.codesbench.quality.pageelements.nodAngular.popups.InsertOpinionPopupElementsAngular;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class AdminOpinionFormFragmentAngular extends BasePage
{
    @Autowired
    public AdminOpinionFormFragmentAngular(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    private final WebDriver driver;

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, InsertOpinionPopupElementsAngular.class);
    }

    public void selectType(String text)
    {
        selectDropdownOption(InsertOpinionPopupElementsAngular.typeDropdown, text);
    }

    public String getType()
    {
        return getSelectedDropdownOptionText(InsertOpinionPopupElementsAngular.typeDropdown);
    }

    public String getContentSet()
    {
        return getElementsText(InsertOpinionPopupElementsAngular.contentSetText);
    }

    public void typeOpinionNumber(String text)
    {
        sendKeysToElement(InsertOpinionPopupElementsAngular.opinionNumberInput, text);
    }

    public void clearOpinionNumber()
    {
        clear(InsertOpinionPopupElementsAngular.opinionNumberInput);
    }

    public String getOpinionNumber()
    {
        return getElementsAttribute(InsertOpinionPopupElementsAngular.opinionNumberInput, "value");
    }

    public void typeDateOfOpinion(String text)
    {
        sendKeysToElement(InsertOpinionPopupElementsAngular.opinionDateInput, text);
    }

    public String getDateOfOpinion()
    {
        return getElementsAttribute(InsertOpinionPopupElementsAngular.opinionDateInput, "value");
    }

    public void clearDateOfOpinion()
    {
        clear(InsertOpinionPopupElementsAngular.opinionDateInput);
    }

    public String getEditorName()
    {
        return getElementsText(InsertOpinionPopupElementsAngular.editorName);
    }

    public void typeText(String text)
    {
        sendKeysToElement(InsertOpinionPopupElementsAngular.opinionText, text);
    }

    public String getText()
    {
        return getElementsAttribute(InsertOpinionPopupElementsAngular.opinionText, "value");
    }

    public void clearText()
    {
        clear(InsertOpinionPopupElementsAngular.opinionText);
    }

    public void typeCitation(String text)
    {
        sendKeysToElement(InsertOpinionPopupElementsAngular.opinionCitation, text);
    }

    public String getCitation()
    {
        return getElementsAttribute(InsertOpinionPopupElementsAngular.opinionCitation, "value");
    }

    public void clearCitation()
    {
        clear(InsertOpinionPopupElementsAngular.opinionCitation);
    }

    public void typeWestlawNumber(String text)
    {
        sendKeysToElement(InsertOpinionPopupElementsAngular.westlawNumber, text);
    }

    public String getWestlawNumber()
    {
        return getElementsAttribute(InsertOpinionPopupElementsAngular.westlawNumber, "value");
    }

    public void clearWestlawNumber()
    {
        clear(InsertOpinionPopupElementsAngular.westlawNumber);
    }

}
