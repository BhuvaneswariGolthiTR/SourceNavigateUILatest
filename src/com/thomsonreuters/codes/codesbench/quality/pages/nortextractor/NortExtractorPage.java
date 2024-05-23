package com.thomsonreuters.codes.codesbench.quality.pages.nortextractor;

import com.thomsonreuters.codes.codesbench.quality.pageelements.nortextractor.NortExtractorPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.files.FileUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import java.util.List;

@Component
public class NortExtractorPage extends BasePage
{
    private WebDriver driver;

    @Autowired
    public NortExtractorPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, NortExtractorPageElements.class);
    }

    public void goToNortExtractor()
    {
        openPageWithUrl(NortExtractorPageElements.PAGE_URL, NortExtractorPageElements.PAGE_TITLE);
    }

    public void clickTargetConversionRadioButton()
    {
        click(NortExtractorPageElements.TARGET_CONVESION_RADIO_BUTTON_XPATH);
    }

    public void clickStatuteHistoryIdsRadioButton()
    {
        click(NortExtractorPageElements.STATUTE_HISTORY_IDS_RADIO_BUTTON_XPATH);
    }

    public void clickSourceConversionTaxTypeRadioButton()
    {
        click(NortExtractorPageElements.SOURCE_CONVERSION_TAX_TYPE_RADIO_BUTTON_XPATH);
        waitForPageLoaded();
    }

    public void clickSourceConversionAmendmentNotesRadioButton()
    {
        click(NortExtractorPageElements.SOURCE_CONVERSION_AMENDMENT_NOTES_RADIO_BUTTON_XPATH);
        waitForPageLoaded();
    }

    public void clickSubmit()
    {
        click(NortExtractorPageElements.submitButton);
    }

    public void setIdList(String text)
    {
        sendTextToTextbox(NortExtractorPageElements.ID_LIST_INPUT_XPATH, text);
    }

    public void setEnvironmentDropdown(String environment)
    {
        if(isElementDisplayed(NortExtractorPageElements.AMENDMENT_NOTES_ENVIRONMENT_DROPDOWN_XPATH))
        {
            selectDropdownOption(NortExtractorPageElements.AMENDMENT_NOTES_ENVIRONMENT_DROPDOWN_XPATH, environment);
        }
        else
        {
            selectDropdownOption(NortExtractorPageElements.TAX_TYPE_ENVIRONMENT_DROPDOWN_XPATH, environment);
        }
    }

    public void setContentSetDropdown(String contentSet)
    {
        if(isElementDisplayed(NortExtractorPageElements.AMENDMENT_NOTES_ENVIRONMENT_DROPDOWN_XPATH))
        {
            selectDropdownOption(NortExtractorPageElements.AMENDMENT_NOTES_CONTENT_SET_DROPDOWN_XPATH, contentSet);
        }
        else
        {
            selectDropdownOption(NortExtractorPageElements.TAX_TYPE_CONTENT_SET_DROPDOWN_XPATH, contentSet);
        }
    }

    public void clickContentSetDropdown()
    {
        if(isElementDisplayed(NortExtractorPageElements.TAX_TYPE_CONTENT_SET_DROPDOWN_XPATH))
        {
            click(NortExtractorPageElements.TAX_TYPE_CONTENT_SET_DROPDOWN_XPATH);
        }
        else
        {
            click(NortExtractorPageElements.AMENDMENT_NOTES_CONTENT_SET_DROPDOWN_XPATH);
        }
    }

    public void setDocName(String docName)
    {
        sendTextToTextbox(NortExtractorPageElements.DOC_NAME_INPUT_XPATH, docName);
    }

    public void checkAttorneyTopicGapCheckbox()
    {
        checkCheckbox(NortExtractorPageElements.ATTORNEY_TOPIC_GAP_CHECKBOX_XPATH);
        waitForPageLoaded();
    }

    public void checkRepealDocGapCheckbox()
    {
        checkCheckbox(NortExtractorPageElements.REPEAL_DOC_GAP_CHECKBOX_XPATH);
        waitForPageLoaded();
    }

    public void checkNewCodeGapCheckbox()
    {
        checkCheckbox(NortExtractorPageElements.NEW_CODE_GAP_CHECKBOX_XPATH);
        waitForPageLoaded();
    }

    public void setAttorneyTopicGapDocName(String docName)
    {
        sendTextToTextbox(NortExtractorPageElements.ATTORNEY_TOPIC_GAP_DOC_NAME_INPUT_XPATH, docName);
    }

    public void setRepealDocGapDocName(String docName)
    {
        sendTextToTextbox(NortExtractorPageElements.REPEAL_DOC_GAP_DOC_NAME_INPUT_XPATH, docName);
    }

    public void setNewCodeGapGapDocName(String docName)
    {
        sendTextToTextbox(NortExtractorPageElements.ATTORNEY_TOPIC_GAP_DOC_NAME_INPUT_XPATH, docName);
    }

    public void clickReset()
    {
        click(NortExtractorPageElements.resetButton);
    }

    public void clickBack()
    {
        click(NortExtractorPageElements.backButton);
        waitForPageLoaded();
        waitForElementExists(NortExtractorPageElements.ID_LIST_INPUT_XPATH);
    }

    public void clickCheckWorkflowLink()
    {
        click(NortExtractorPageElements.CHECK_WORKFLOWS_URL_XPATH);
        waitForWindowByTitle(workflowDetailsPage().getTitle());
        waitForPageLoaded();
    }

    public void clickConvertedFilesLink(String fileName)
    {
        click(NortExtractorPageElements.CONVERTED_FILES_URL_XPATH);
        FileUtils.waitForFileToExist(FileUtils.DOWNLOAD_FOLDER_DIR + fileName, 5000);
    }

    public String getConvertedFileName()
    {
        String filePath = getElementsText(NortExtractorPageElements.CONVERTED_FILES_URL_XPATH);
        return filePath.substring(filePath.lastIndexOf("/") + 1);
    }

    public void attorneyTopicGapChooseFile(String fileName)
    {
        sendKeysToElement(NortExtractorPageElements.ATTORNEY_TOPIC_GAP_CHOOSE_FILE_XPATH, FileUtils.DOWNLOAD_FOLDER_DIR + fileName);
        int loopCount = 0;
        while((Long) ((JavascriptExecutor) driver).executeScript("return document.getElementById('attorney-topic-gap-file').files.length") < 1 && loopCount < 5)
        {
            DateAndTimeUtils.takeNap(DateAndTimeUtils.ONE_SECOND);
            loopCount++;
        }
    }

    public List<String> getAllContentSets()
    {
        return getSelectOptionsText(getElement(NortExtractorPageElements.TAX_TYPE_CONTENT_SET_DROPDOWN_XPATH));
    }
}
