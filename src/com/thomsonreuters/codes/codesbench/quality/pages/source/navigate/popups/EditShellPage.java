package com.thomsonreuters.codes.codesbench.quality.pages.source.navigate.popups;

import com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.popups.EditShellPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import com.thomsonreuters.codes.codesbench.quality.utilities.autoIT.AutoITUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class EditShellPage extends BasePage
{
    private WebDriver driver;

    @Autowired
    public EditShellPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, EditShellPageElements.class);
    }

    public void setTextBoxSelectedFile(String filename)
    {
        click(EditShellPageElements.selectedFileTextBox);
        AutoITUtils.handleChooseFileToUploadWindowWithAutoIT(filename);
        waitForPageLoaded();
    }

    public void clickSave() {
        click(EditShellPageElements.saveButton);
    }

    public String getDocumentNumber()
    {
        return EditShellPageElements.documentNumber.getAttribute("value");
    }

    public void deleteAllFiles()
    {
        while(doesElementExist(EditShellPageElements.DELETE_FILE_BUTTON))
        {
            click(EditShellPageElements.DELETE_FILE_BUTTON);
            DateAndTimeUtils.takeNap(100);
        }
    }

    public void typeNewFileNumber(String newFileNumber)
    {
        DateAndTimeUtils.takeNap(1000);
        getElement(EditShellPageElements.NEW_FILE).clear();
        getElement(EditShellPageElements.NEW_FILE).sendKeys(newFileNumber);
    }

    public void clickAddNewFileNumber()
    {
        click(EditShellPageElements.addFileNumber);
    }
}
