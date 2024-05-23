package com.thomsonreuters.codes.codesbench.quality.pages.editor.popups;

import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.popups.TableEditorPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.robot.RobotUtils;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.awt.event.KeyEvent;

@Component
public class TableEditorPage extends BasePage
{
    private WebDriver driver;

    @Autowired
    public TableEditorPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, TableEditorPageElements.class);
    }

    public boolean switchToTableEditorPage()
    {
        return switchToWindow(TableEditorPageElements.PAGE_TITLE);
    }

    public void clickSave()
    {
        click(TableEditorPageElements.saveButton);
        waitForWindowGoneByTitle(TableEditorPageElements.PAGE_TITLE);
        editorPage().switchToEditor();
    }

    public void clickCancel()
    {
        click(TableEditorPageElements.cancelButton);
        waitForWindowGoneByTitle(TableEditorPageElements.PAGE_TITLE);
        editorPage().switchToEditor();
    }

    public void openMarkupDropdown()
    {
        click(TableEditorPageElements.markupButton);
    }

    public void openMarkupDropdownOption(TableEditorPageElements.MarkupDropdownOptions option)
    {
        for (int i = 0; i < option.getIndex(); i++)
        {
            sendKeys(Keys.ARROW_DOWN);
        }
        try
        {
            RobotUtils.getRobot().keyPress(KeyEvent.VK_CONTROL);
            RobotUtils.getRobot().keyPress(KeyEvent.VK_ENTER);
        }
        finally
        {
            RobotUtils.getRobot().keyRelease(KeyEvent.VK_CONTROL);
            RobotUtils.getRobot().keyRelease(KeyEvent.VK_ENTER);
        }
    }

    public boolean isAddedTextLinked(String attributeName, String expectedValue)
    {
        return getAddedTextAttributeValue(attributeName).equals(expectedValue);
    }

    public String getAddedTextAttributeValue(String attributeName)
    {
        return getElement(TableEditorPageElements.FIRST_INPUT_FIELD + "/u").getAttribute(attributeName);
    }

    public void enterTextToFirstInputField(String textToEnter)
    {
        clearAndSendKeysToElement(TableEditorPageElements.firstInputField, textToEnter);
    }

    public void clearAndEnterTextToTableInputFields(String textToEnter)
    {
        TableEditorPageElements.firstInputField.clear();
        TableEditorPageElements.secondInputField.clear();
        editorPage().click(TableEditorPageElements.firstInputField);
        tableEditorPage().sendKeys(textToEnter);
    }

    public void clickInsertSpecialCharacter()
    {
        sendEnterToElement(TableEditorPageElements.INSERT_SPECIAL_CHARACTER_BUTTON);
    }
}
