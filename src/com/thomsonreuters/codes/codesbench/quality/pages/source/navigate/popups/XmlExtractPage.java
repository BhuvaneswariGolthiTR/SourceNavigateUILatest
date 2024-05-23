package com.thomsonreuters.codes.codesbench.quality.pages.source.navigate.popups;

import com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.popups.XmlExtractPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class XmlExtractPage extends BasePage
{
    private WebDriver driver;

    @Autowired
    public XmlExtractPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, XmlExtractPageElements.class);
    }

    public boolean switchToXmlExtractWindow()
    {
        boolean windowAppears = switchToWindow(XmlExtractPageElements.XML_EXTRACT_PAGE_TITLE);
        waitForPageLoaded();
        enterTheInnerFrame();
        return windowAppears;
    }

    public void clickSingleFileOutput()
    {
        clickFileOutputButton("single");
    }

    public void clickMultiFileOutput()
    {
        clickFileOutputButton("multiple");
    }

    public void addFileName(String fileName)
    {
        //click(XmlExtractPageElements.fileNameInput);
        sendTextToTextbox(XmlExtractPageElements.fileNameInput, fileName);
    }

    public void checkRemoveAddedMaterialCheckbox()
    {
        checkCheckbox(XmlExtractPageElements.removeAddedMaterialCheckbox);
    }

    public void checkRemoveDeletedMaterialCheckbox()
    {
        checkCheckbox(XmlExtractPageElements.removeDeletedMaterialCheckbox);
    }

    public void clickOk()
    {
        sendEnterToElement(XmlExtractPageElements.okButton);
        waitForWindowGoneByTitle(XmlExtractPageElements.XML_EXTRACT_PAGE_TITLE);
    }

    public void clickCancel()
    {
        sendEnterToElement(XmlExtractPageElements.cancelButton);
        waitForWindowGoneByTitle(XmlExtractPageElements.XML_EXTRACT_PAGE_TITLE);
    }

    private void clickFileOutputButton(String value)
    {
        checkCheckbox(String.format(XmlExtractPageElements.FILE_OUTPUT_RADIO_BUTTON, value));
    }
}
