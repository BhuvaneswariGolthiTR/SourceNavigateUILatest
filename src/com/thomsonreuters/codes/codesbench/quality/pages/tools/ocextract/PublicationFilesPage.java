package com.thomsonreuters.codes.codesbench.quality.pages.tools.ocextract;

import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.ocextract.OCExtractBasePageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.ocextract.PublicationFilesPageElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.files.FileUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Component
public class PublicationFilesPage extends OCExtractBasePage
{
    private final WebDriver driver;

    @Autowired
    public PublicationFilesPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, PublicationFilesPageElements.class);
    }

    public List<String> getExpectedColumnOrder()
    {
        return Arrays.asList("Content Set", "Requester's Name", "Pub Tag", "File Name", "Created Date", "Status");
    }

    @Override
    public boolean isPageOpened()
    {
        if (super.isPageOpened())
        {
            waitForElement(String.format(OCExtractBasePageElements.TAB_PANEL_VISIBILITY_XPATH,
                    PublicationFilesPageElements.TAB_PANEL_NUMBER));
            return isElementDisplayed(String.format(OCExtractBasePageElements.TAB_PANEL_VISIBILITY_XPATH,
                    PublicationFilesPageElements.TAB_PANEL_NUMBER));
        }
        return super.isPageOpened();
    }

    public String getPublicationYear()
    {
        return getElementsText(PublicationFilesPageElements.YEAR_FIELD);
    }

    public String getPublicationName()
    {
        return getElementsText(PublicationFilesPageElements.NAME_FIELD);
    }

    public boolean isYearFieldLabelDisplayed()
    {
        return isElementDisplayed(PublicationFilesPageElements.YEAR_FIELD_LABEL);
    }

    public boolean isPublicationNameFieldLabelDisplayed()
    {
        return isElementDisplayed(PublicationFilesPageElements.PUBLICATION_NAME_FIELD_LABEL);
    }

    public File getDownloadedFile(String fileNameRegex)
    {
        List<File> downloadedFilesList = FileUtils.getFilesByFileNameRegex(PublicationFilesPageElements.DOWNLOAD_DIR_PATH, fileNameRegex);
        if (downloadedFilesList.size() == 1)
        {
            return downloadedFilesList.get(0);
        } else if (downloadedFilesList.size() > 1)
        {
            logger.warning("More than 1 file were found");
            return null;
        }
        return null;
    }

    public boolean waitForFileToBeDownloaded(String pdfFileNameRegex)
    {
        return FileUtils.waitForFileToExistByFileNameRegex(PublicationFilesPageElements.DOWNLOAD_DIR_PATH, pdfFileNameRegex, DateAndTimeUtils.FIFTEEN_SECONDS);
    }

    public boolean isUploadFilePopupDisplayed()
    {
        return isElementDisplayed(PublicationFilesPageElements.UPLOAD_PDF_POPUP);
    }
}
