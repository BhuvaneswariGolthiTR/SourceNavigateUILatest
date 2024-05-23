package com.thomsonreuters.codes.codesbench.quality.pages.abstractPages;

import com.thomsonreuters.codes.codesbench.quality.pageelements.abstractPageElements.RawXmlEditorPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import com.thomsonreuters.codes.codesbench.quality.utilities.autoIT.AutoITUtils;
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
public class AbstractRawXmlEditorPage extends BasePage
{
    private WebDriver driver;

    @Autowired
    public AbstractRawXmlEditorPage(WebDriver driver)
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
        return checkWindowIsPresented(RawXmlEditorPageElements.RAW_XML_EDITOR_TITLE_PAGE);
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
        waitForWindowGoneByTitle(RawXmlEditorPageElements.RAW_XML_EDITOR_TITLE_PAGE);
    }

    public void copyPasteXmlEditorText()
    {
        StringSelection editorContent = new StringSelection(getTextFromEditor());
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(editorContent, editorContent);
        click(RawXmlEditorPageElements.textArea);
        AutoITUtils.sendHotKey("a", Keys.CONTROL);
        AutoITUtils.sendHotKey(Keys.DELETE);
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
        boolean windowAppears = switchToWindow(RawXmlEditorPageElements.RAW_XML_EDITOR_TITLE_PAGE);
        waitForPageLoaded();
        return windowAppears;
    }

    public boolean isEditorTextReadOnly()
    {
        return isElementReadOnly(RawXmlEditorPageElements.BODY_TAG);
    }
}