package com.thomsonreuters.codes.codesbench.quality.pages.source;

import javax.annotation.PostConstruct;

import com.thomsonreuters.codes.codesbench.quality.pageelements.CommonPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.source.EditPDFMetadataPageElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.autoIT.AutoITUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;

import java.util.*;

@Component
public class EditPDFMetadataPage extends BasePage
{
    private WebDriver driver;

    @Autowired
    public EditPDFMetadataPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init() {PageFactory.initElements(driver, EditPDFMetadataPage.class);}

    public void setTextBoxSelectedFile(String inputFile)
    {
        WebElement elementToClick = getElement(EditPDFMetadataPageElements.SELECTED_FILE_TEXT_BOX);
        Actions builder = new Actions(driver);
        builder.moveToElement(elementToClick).click(elementToClick);
        builder.perform();
        AutoITUtils.handleChooseFileToUploadWindowWithAutoIT(inputFile);
        waitForPageLoaded();
    }

    public void clickSaveButton()
    {
        getElement(CommonPageElements.SAVE_BUTTON).click();
        AutoITUtils.verifyAlertTextAndAccept(true, "PDF/Metadata has been Updated.");
    }

    public List<String> verifyEditPDFMetadataUnsupportedFileTypes()
    {
        final List<String> unsupportedFileTypes = Arrays.asList("EDTGrcImageTypes.png",
                "bmpDocument.bmp","tifDocument.tif", "gifDocument.gif", "jpgDocument.jpg", "EDTGrcImageTypes.pdf", "docDocument.doc",
                "docxDocument.docx", "xlsDocument.xls", "xlsxDocument.xlsx", "xlsmDocument.xlsm");
        return verifyAddEditPDFMetadataFileTypes(unsupportedFileTypes, false, EditPDFMetadataPageElements.EDIT_FILENAME_MESSAGE);
    }

    public List<String> verifyEditPDFMetadataSupportedFileTypes()
    {
        final List<String> supportedFileTypes = Arrays.asList("ex_valid_2.txt");
        return verifyAddEditPDFMetadataFileTypes(supportedFileTypes, true, EditPDFMetadataPageElements.EDIT_FILENAME_MESSAGE);
    }

    private List<String> verifyAddEditPDFMetadataFileTypes(List<String> fileTypes, boolean supported, String xpathForMessage)
    {
        Map<String, Boolean> fileMap = new HashMap<>();
        List<String> fileTypesThatFailed = new ArrayList<>();
        for (String fileType : fileTypes)
        {
            WebElement elementToClick = getElement(EditPDFMetadataPageElements.SELECTED_FILE_TEXT_BOX);
            Actions builder = new Actions(driver);
            builder.moveToElement(elementToClick).click(elementToClick).perform();
            AutoITUtils.handleChooseFileToUploadWindowWithAutoIT(fileType);
            waitForPageLoaded();
            DateAndTimeUtils.takeNap(DateAndTimeUtils.ONE_SECOND);
            boolean fileTypeErrorMessageAppeared = isElementDisplayed(xpathForMessage);
            fileMap.put(fileType, fileTypeErrorMessageAppeared);
        }
        for (String key : fileMap.keySet())
        {
            if(fileMap.get(key).booleanValue() == supported)
            {
                fileTypesThatFailed.add(key);
            }
        }
        return fileTypesThatFailed;
    }

}
