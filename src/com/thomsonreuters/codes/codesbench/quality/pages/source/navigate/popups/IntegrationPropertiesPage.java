package com.thomsonreuters.codes.codesbench.quality.pages.source.navigate.popups;

import com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.popups.IntegrationPropertiesPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class IntegrationPropertiesPage extends BasePage
{
    private WebDriver driver;

    @Autowired
    public IntegrationPropertiesPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, IntegrationPropertiesPageElements.class);
    }

    public boolean switchToIntegrationPropertiesPage()
    {
        boolean windowAppears = switchToWindow(IntegrationPropertiesPageElements.PAGE_TITLE);
        waitForPageLoaded();
        enterTheInnerFrame();
        return windowAppears;
    }

    public void clickSave()
    {
        sendEnterToElement(IntegrationPropertiesPageElements.saveButton);
        waitForWindowGoneByTitle(IntegrationPropertiesPageElements.PAGE_TITLE);
    }

    public void setEffectiveDate(String date)
    {
        clearAndSendKeysToElement(IntegrationPropertiesPageElements.effectiveDateTextBox, date);
    }

    public void setEffectiveDateProvision(String text)
    {
        clearAndSendKeysToElement(IntegrationPropertiesPageElements.effectiveDateProvision, text);
    }

    public void setEffectiveComments(String text)
    {
        clearAndSendKeysToElement(IntegrationPropertiesPageElements.effectiveComments, text);
    }

    public void setQueryNote(String text)
    {
        sendTextToTextbox(IntegrationPropertiesPageElements.queryNote, text);
    }

    public void setQueryDate(String date)
    {
        clearAndSendKeysToElement(IntegrationPropertiesPageElements.queryDateTextbox, date);
    }

    public void checkAddAsDateQueryCheckbox()
    {
        checkCheckbox(IntegrationPropertiesPageElements.addAsDateQueryCheckbox);
    }

    public void checkDefaultCheckbox()
    {
        checkCheckbox(IntegrationPropertiesPageElements.integrationPropertiesDefaultCheckbox);
    }

    public void setInstructionNote(String text)
    {
        clearAndSendKeysToElement(IntegrationPropertiesPageElements.instructionNote, text);
    }

    public void setMiscellaneous(String text)
    {
        clearAndSendKeysToElement(IntegrationPropertiesPageElements.miscellaneous, text);
    }

    public void clickCancel()
    {
        IntegrationPropertiesPageElements.cancelButton.click();
        waitForWindowGoneByTitle(IntegrationPropertiesPageElements.PAGE_TITLE);
        waitForGridRefresh();
    }

    public String getEffectiveDateProvision()
    {
        return getElementsAttribute(IntegrationPropertiesPageElements.integrationPropertiesEffectiveDateProvision, "value");
    }

    public String getEffectiveDate()
    {
        return getElementsAttribute(IntegrationPropertiesPageElements.effectiveDateTextBox, "value");
    }

    public String getQueryDate()
    {
        return getElementsAttribute(IntegrationPropertiesPageElements.integrationPropertiesQueryDateTextBox, "value");
    }

    public String getEffectiveComments()
    {
        return getElementsAttribute(IntegrationPropertiesPageElements.integrationPropertiesEffectiveComments, "value");
    }

    public String getQueryNote()
    {
        return getElementsAttribute(IntegrationPropertiesPageElements.integrationPropertiesQueryNote, "value");
    }

    public String getInstructionNotes()
    {
        return getElementsAttribute(IntegrationPropertiesPageElements.integrationPropertiesInstructionNotes, "value");
    }

    public String getMiscellaneous()
    {
        return getElementsAttribute(IntegrationPropertiesPageElements.integrationPropertiesMiscellaneous, "value");
    }
}
