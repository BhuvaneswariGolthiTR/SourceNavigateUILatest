package com.thomsonreuters.codes.codesbench.quality.pages.popups;

import com.thomsonreuters.codes.codesbench.quality.pageelements.popups.RawXmlEditorPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import com.thomsonreuters.codes.codesbench.quality.utilities.autoIT.AutoITUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

@Component
public class RawXmlEditorPage extends BasePage {

    private WebDriver driver;

    @Autowired
    public RawXmlEditorPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, RawXmlEditorPageElements.class);
    }

    public boolean checkIfEditorIsOpened()
    {
        return checkWindowIsPresented(RawXmlEditorPageElements.PAGE_TITLE);
    }

    public void clickSave()
    {
        sendEnterToElement(RawXmlEditorPageElements.saveButton);
        sourceRawXmlDocumentClosurePage().switchToDocumentClosureWindow();
    }

    public boolean clickValidate()
    {
        sendEnterToElement(RawXmlEditorPageElements.validateButton);
        return getElementsText(RawXmlEditorPageElements.messagePane).contains("The document validates by DTD validations.");
    }

    public void clickClose()
    {
        sendEnterToElement(RawXmlEditorPageElements.closeButton);
        waitForWindowGoneByTitle(RawXmlEditorPageElements.PAGE_TITLE);
    }

    public void copyPasteXmlEditorText()
    {
        StringSelection editorContent = new StringSelection(getTextFromEditor());
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(editorContent, editorContent);
        RawXmlEditorPageElements.textArea.clear();
        click(RawXmlEditorPageElements.textArea);
        AutoITUtils.sendHotKey("v", Keys.CONTROL);
    }

    public void sendTextToXmlEditor(String text)
    {
        RawXmlEditorPageElements.textArea.click();
        RawXmlEditorPageElements.textArea.sendKeys(Keys.END);
        RawXmlEditorPageElements.textArea.sendKeys(Keys.SPACE);
        sendKeys(text);
    }

    public String getTextFromEditor()
    {
        return getElementsText(RawXmlEditorPageElements.textArea);
    }

    public boolean isGivenTextInEditor(String givenText)
    {
        return getElementsText(RawXmlEditorPageElements.textArea).contains(givenText);
    }

    public boolean switchToRawXmlEditorPage()
    {
        boolean windowAppears = switchToWindow(RawXmlEditorPageElements.PAGE_TITLE);
        waitForPageLoaded();
        return windowAppears;
    }

    public void sendTextToEditor(String text)
    {
        JavascriptExecutor javascriptExecutor = (JavascriptExecutor)driver;
        javascriptExecutor.executeScript(String.format("arguments[0].value='%s'",text), RawXmlEditorPageElements.textArea);
        click(RawXmlEditorPageElements.textArea);
    }

}
