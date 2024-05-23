package com.thomsonreuters.codes.codesbench.quality.pages.source.navigate.popups;

import com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.popups.AddShellPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import com.thomsonreuters.codes.codesbench.quality.utilities.autoIT.AutoITUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.*;

@Component
public class AddShellPage extends BasePage
{
    private WebDriver driver;

    @Autowired
    public AddShellPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, AddShellPageElements.class);
    }

    public void setDropdownContentSet(String contentSet)
    {
        selectDropdownOption(AddShellPageElements.contentSetDropdown, contentSet);
    }

    public void setDropdownDocumentType(String documentType)
    {
        selectDropdownOption(AddShellPageElements.documentTypeDropdown, documentType);
    }

    public void setTextBoxOrganization(String organization)
    {
        clearAndSendKeysToElement(AddShellPageElements.organizationTextBox, organization);
    }

    public void setTextBoxDocumentNumber(String documentNumber)
    {
        clearAndSendKeysToElement(AddShellPageElements.documentNumberTextBox, documentNumber);
    }

    public void setDropdownContentType(String contentType)
    {
        selectDropdownOption(AddShellPageElements.contentTypeTextBox, contentType);
    }

    public void setTextBoxYear(String year)
    {
        clearAndSendKeysToElement(AddShellPageElements.yearTextBox, year);
    }

    public void setTextBoxDocumentTitle(String documentTitle)
    {
        clearAndSendKeysToElement(AddShellPageElements.documentTitleTextBox, documentTitle);
    }

    public void selectFile(String selectedFile)
    {
        String filePath = new File("commonFiles\\TestFiles\\" + selectedFile).getAbsolutePath();
        sendKeysToElement(AddShellPageElements.selectedFileBrowserButton, filePath);
        //click(AddShellPageElements.selectedFileBrowserButton);
        AutoITUtils.handleChooseFileToUploadWindowWithAutoIT(selectedFile);
        waitForPageLoaded();
    }

    public boolean clickSubmit()
    {
        click(AddShellPageElements.submitButton);
        return AutoITUtils.verifyAlertTextAndAccept(true, "Data has been submitted for input.");
    }

    public void typeNewFileNumber(String newFileNumber)
    {
        DateAndTimeUtils.takeNap(1000);
        getElement(AddShellPageElements.NEW_FILE).clear();
        getElement(AddShellPageElements.NEW_FILE).sendKeys(newFileNumber);
    }

    public void clickAddNewFileNumber()
    {
        click(AddShellPageElements.addFileNumber);
    }

    public List<String> getAllContentTypes()
    {
        return getElementsTextList(AddShellPageElements.ALL_CONTENT_TYPE_OPTIONS);
    }

    public List<String> getAllOrganisations()
    {
        click(AddShellPageElements.organisationDropdown);
        waitForElement(AddShellPageElements.ALL_ORGANISATION_OPTIONS);
        return getElementsTextList(AddShellPageElements.ALL_ORGANISATION_OPTIONS);
    }

    public boolean clickCancel()
    {
        click(AddShellPageElements.cancelButton);
        return AutoITUtils.verifyAlertTextAndAccept(true, "Are you sure you want to cancel?");
    }

    public List<String> getAllDocTypes()
    {
        return getElementsTextList(AddShellPageElements.DOCUMENT_TYPE_DROPDOWN + "/option");
    }

    public List<String> verifyAddPDFMetadataUnsupportedFileTypes()
    {
        final List<String> unsupportedFilesTypes = Arrays.asList("EDTGrcImageTypes.png", "bmpDocument.bmp",
                "tifDocument.tif", "gifDocument.gif", "jpgDocument.jpg");

        return verifyAddEditPDFMetadataFileTypes(unsupportedFilesTypes, false, AddShellPageElements.ADD_FILENAME_MESSAGE);
    }

    public List<String> verifyAddPDFMetadataSupportedFileTypes()
    {
        final List<String> supportedFilesTypes = Arrays.asList("EDTGrcImageTypes.pdf", "docDocument.doc",
                "docxDocument.docx", "xlsDocument.xls", "xlsxDocument.xlsx", "xlsmDocument.xlsm", "ex_valid_2.txt");

        return verifyAddEditPDFMetadataFileTypes(supportedFilesTypes, true, AddShellPageElements.ADD_FILENAME_MESSAGE);
    }

    private List<String> verifyAddEditPDFMetadataFileTypes(List<String> fileTypes, boolean supported, String xpathForMessage)
    {
        List<String> fileTypesThatFailed = new ArrayList<>();
        Map<String, Boolean> fileMap = new HashMap<>();
        for (String fileType : fileTypes)
        {
            //click(AddShellPageElements.selectedFileBrowserButton);
            String filePath = new File("commonFiles\\TestFiles\\" + fileType).getAbsolutePath();
            sendKeysToElement(AddShellPageElements.selectedFileBrowserButton, filePath);
            AutoITUtils.handleChooseFileToUploadWindowWithAutoIT(fileType);
            DateAndTimeUtils.takeNap(1000);
            boolean fileTypeErrorMessageAppeared = isElementDisplayed(xpathForMessage);
            fileMap.put(fileType,fileTypeErrorMessageAppeared);
        }
        for(String key : fileMap.keySet())
        {
            if(fileMap.get(key).booleanValue() == supported)
            {
                fileTypesThatFailed.add(key);
            }
        }
        return fileTypesThatFailed;
    }
}
