package com.thomsonreuters.codes.codesbench.quality.tests.tools.ocextract;

import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.ocextract.OCExtractBasePageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.ocextract.PublicationFilesPageElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.EDGE;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.files.pdf.PDFUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;

import static com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import static com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;

public class PublicationFilesTests extends TestService
{
    /**
     * STORY/BUG - HALCYONST-13600 <br>
     * SUMMARY - Files table: layout <br>
     * USER - Legal <br>
     */
    @Test
	@EDGE
    @LEGAL
    @LOG
    public void publicationFilesTableLayoutTest()
    {
        // 1	Log onto the Iowa (Development) content set
        homePage().goToHomePage();
        loginPage().logIn();

        // 2	Go to Tools -> OC Extract
        boolean ocExtractPageOpened = toolsMenu().goToOCExtract();
        Assertions.assertTrue(ocExtractPageOpened, "The OC Extract page opened");

        // 3	VERIFY: O'Connors Processing UI window is opened
        boolean publicationsPageOpened = publicationsPage().isPageOpened();
        Assertions.assertTrue(publicationsPageOpened, "Publications page opened");

        String expectedPublicationYear = publicationsTablePage().getFirstPublicationYear();
        String expectedPublicationName = publicationsTablePage().getFirstPublicationName();

        // 4	Double-click on any publication in the "Publications" table
        publicationsTablePage().doubleClickFirstPublication();
        boolean publicationFilesPageOpened = publicationFilesPage().isPageOpened();
        Assertions.assertTrue(publicationFilesPageOpened, "Publication files page opened");

        /* 5	VERIFY the following elements are present:
            a) "Year:" field with Publication's year date
            b) "Name:" field with Publication's name
            c) "Files" table name
            d) Table with columns (in order below):
            1 - Content Set
            2 - Requester's name
            3 - Pubtag
            4 - File Name
            5 - Created Date
            6 - Status
        */
        boolean publicationYearLabelIsDisplayed = publicationFilesPage().isYearFieldLabelDisplayed();
        boolean publicationNameLabelIsDisplayed = publicationFilesPage().isPublicationNameFieldLabelDisplayed();
        String actualPublicationYear = publicationFilesPage().getPublicationYear();
        String actualPublicationName = publicationFilesPage().getPublicationName();

        boolean publicationFilesTableNameIsDisplayed = publicationFilesPage().isElementDisplayed(String
                .format(OCExtractBasePageElements.TABLE_NAME_XPATH, PublicationFilesPageElements.TABLE_NAME));
        List<String> expectedColumnOrder = publicationFilesPage().getExpectedColumnOrder();
        List<String> actualColumnOrder = publicationFilesTablePage().getActualColumnOrderListOcExtract();

        Assertions.assertAll
        (
                () -> Assertions.assertTrue(publicationYearLabelIsDisplayed, "Year field label is displayed"),
                () -> Assertions.assertTrue(publicationNameLabelIsDisplayed, "Publication name field label is displayed"),
                () -> Assertions.assertEquals(expectedPublicationYear, actualPublicationYear, "Actual publication year equals to expected one"),
                () -> Assertions.assertEquals(expectedPublicationName, actualPublicationName, "Actual publication name equals to expected one"),
                () -> Assertions.assertTrue(publicationFilesTableNameIsDisplayed, "Publication files table name is displayed"),
                () -> Assertions.assertEquals(expectedColumnOrder, actualColumnOrder, "Publication files table actual column order equals to expected one")
        );
    }

    /**
     * STORY/BUG - HALCYONST-13838 <br>
     * SUMMARY - Download and Upload PDF for 'Proof' status files <br>
     * USER - Legal <br>
     */
    @Test
	@EDGE
    @LEGAL
    @LOG
    public void proofFilePdfDownloadUploadTest()
    {
        File firstDownloadedFile = null;
        File secondDownloadedFile = null;

        try
        {
            String commonFilesPath = "commonFiles\\TestFiles\\";
            String initialPdfFileName = "I7A9B7630C9BE11EBB9FBF64AD7B118D8_initial.pdf";
            String pdfFileName = "I6FFD1BC0C9BE11EBB9FBF64AD7B118D8.pdf";
            String pubFileName = "file107";
            String pdfFileNameRegex = PublicationFilesPageElements.PUB_FILE_PDF_REGEX;
            String expectedNotificationText = "PDF uploaded";

            // Log onto the Iowa (Development) content set
            homePage().goToHomePage();
            loginPage().logIn();

            // Go to Tools -> OC Extract
            boolean ocExtractPageOpened = toolsMenu().goToOCExtract();
            Assertions.assertTrue(ocExtractPageOpened, "The OC Extract page opened");
            // VERIFY: O'Connors Processing UI window is opened
            boolean publicationsPageOpened = publicationsPage().isPageOpened();
            Assertions.assertTrue(publicationsPageOpened, "Publications page opened");

            // Double-click on any publication (which contains any Proof status file)
            publicationsTablePage().doubleClickFirstPublication();
            // VERIFY: The "Files" table is shown
            boolean publicationFilesPageOpened = publicationFilesPage().isPageOpened();
            Assertions.assertTrue(publicationFilesPageOpened, "Publication files page opened");

            // Right click on a file with status 'Proof' in the "Files" table
            publicationFilesTablePage().rightClickPubFileByName(pubFileName);
            // Click PDF -> Download
            publicationFilesContextMenu().clickPdfDownload();
            // VERIFY: PDF file is downloaded
            boolean isFirstPdfFileDownloaded = publicationFilesPage().waitForFileToBeDownloaded(pdfFileNameRegex);
            Assertions.assertTrue(isFirstPdfFileDownloaded, "First PDF file was downloaded");
            firstDownloadedFile = publicationFilesPage().getDownloadedFile(pdfFileNameRegex);
            DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
            Assertions.assertTrue(firstDownloadedFile.exists() && firstDownloadedFile.length() > 0, "First downloaded PDF file exists and has non zero size");
            boolean isFirstPdfFileDeleted = firstDownloadedFile.delete();
            firstDownloadedFile = null;

            // Right click on the same file with status 'Proof'
            publicationFilesTablePage().rightClickPubFileByName(pubFileName);
            // Click PDF -> Upload
            publicationFilesContextMenu().clickPdfUpload();

            // Click 'Upload'
            uploadFilePopup().clickUpload();
            // Choose different PDF file and click 'Open'
            uploadFilePopup().chooseFile(pdfFileName);
            // VERIFY: Check mark appears next to the 'Upload' button
            boolean uploadCheckMarkDisplayed = uploadFilePopup().isUploadCheckMarkDisplayed();
            // Click 'Submit'
            uploadFilePopup().clickSubmit();
            DateAndTimeUtils.takeNap(DateAndTimeUtils.FIVE_SECONDS);
            // VERIFY: Notification with text "PDF uploaded" appears
            boolean isNotificationPopupDisplayed = toastNotificationPopup().waitForNotification();
            String actualNotificationText = toastNotificationPopup().getNotificationText();

            // Right click on a file with status 'Proof' in the "Files" table
            publicationFilesTablePage().rightClickPubFileByName(pubFileName);
            // Click PDF -> Download
            publicationFilesContextMenu().clickPdfDownload();
            // VERIFY: PDF file is downloaded
            boolean isSecondPdfFileDownloaded = publicationFilesPage().waitForFileToBeDownloaded(pdfFileNameRegex);
            Assertions.assertTrue(isSecondPdfFileDownloaded, "Second PDF file was downloaded");
            secondDownloadedFile = publicationFilesPage().getDownloadedFile(pdfFileNameRegex);
            Assertions.assertTrue(secondDownloadedFile.exists() && secondDownloadedFile.length() > 0, "Second downloaded PDF file exists and has non zero size");

            File uploadedPdfFile = new File(commonFilesPath + pdfFileName);
            String pdfContentUploadedFile = PDFUtils.getPdfContent(uploadedPdfFile);
            String pdfContentSecondDownloadedFile = PDFUtils.getPdfContent(secondDownloadedFile);
            boolean isSecondPdfFileDeleted = secondDownloadedFile.delete();
            secondDownloadedFile = null;

            // Uploading initial pdf file (after test methods)
            publicationFilesTablePage().rightClickPubFileByName(pubFileName);
            publicationFilesContextMenu().clickPdfUpload();
            uploadFilePopup().clickUpload();
            uploadFilePopup().chooseFile(initialPdfFileName);
            uploadFilePopup().clickSubmit();
            boolean isNotificationPopupDisplayedForInitialFile = toastNotificationPopup().waitForNotification();
            String actualNotificationTextForInitialFile = toastNotificationPopup().getNotificationText();

            Assertions.assertAll
                    (
                            () -> Assertions.assertTrue(uploadCheckMarkDisplayed, "Upload check mark is displayed"),
                            () -> Assertions.assertTrue(isNotificationPopupDisplayed, "Notification Popup is displayed"),
                            () -> Assertions.assertEquals(expectedNotificationText, actualNotificationText, "Actual notification popup text equals to expected one"),
                            () -> Assertions.assertTrue(isNotificationPopupDisplayedForInitialFile, "Notification Popup is displayed"),
                            () -> Assertions.assertEquals(expectedNotificationText, actualNotificationTextForInitialFile, "Actual notification popup text equals to expected one"),
                            () -> Assertions.assertEquals(pdfContentUploadedFile, pdfContentSecondDownloadedFile, "Actual downloaded pdf file content equals to expected one"),
                            () -> Assertions.assertTrue(isFirstPdfFileDeleted, "First downloaded file was deleted"),
                            () -> Assertions.assertTrue(isSecondPdfFileDeleted, "Second downloaded file was deleted")
                    );
        }
        finally
        {
            if(firstDownloadedFile != null)
            {
                Assertions.assertTrue(firstDownloadedFile.delete(), "Unable to delete first file in finally");
            }
            if(secondDownloadedFile != null)
            {
                Assertions.assertTrue(secondDownloadedFile.delete(), "Unable to delete second file in finally");
            }
        }
    }

    /**
     * STORY/BUG - HALCYONST-13838 <br>
     * SUMMARY - Cancel button on the upload PDF dialog for 'Proof' status files <br>
     * USER - Legal <br>
     */
    @Test
	@EDGE
    @LEGAL
    @LOG
    public void proofFilePdfUploadCancelButtonTest()
    {
        String pdfFileName = "I6FFD1BC0C9BE11EBB9FBF64AD7B118D8.pdf";
        String pubFileName = "file107";
        String pdfFileNameRegex = PublicationFilesPageElements.PUB_FILE_PDF_REGEX;

        homePage().goToHomePage();
        loginPage().logIn();

        boolean ocExtractPageOpened = toolsMenu().goToOCExtract();
        Assertions.assertTrue(ocExtractPageOpened, "The OC Extract page opened");
        boolean publicationsPageOpened = publicationsPage().isPageOpened();
        Assertions.assertTrue(publicationsPageOpened, "Publications page opened");

        publicationsTablePage().doubleClickFirstPublication();
        boolean publicationFilesPageOpened = publicationFilesPage().isPageOpened();
        Assertions.assertTrue(publicationFilesPageOpened, "Publication files page opened");

        publicationFilesTablePage().rightClickPubFileByName(pubFileName);
        publicationFilesContextMenu().clickPdfUpload();
        uploadFilePopup().clickCancel();
        boolean isUploadFilePopupDisplayed = publicationFilesPage().isUploadFilePopupDisplayed();
        Assertions.assertFalse(isUploadFilePopupDisplayed, "First Upload PDF file popup is closed");
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        publicationFilesTablePage().rightClickPubFileByName(pubFileName);
        publicationFilesContextMenu().clickPdfUpload();
        uploadFilePopup().clickUpload();
        uploadFilePopup().chooseFile(pdfFileName);
        boolean uploadCheckMarkDisplayed = uploadFilePopup().isUploadCheckMarkDisplayed();
        Assertions.assertTrue(uploadCheckMarkDisplayed, "Upload check mark is displayed");
        uploadFilePopup().clickCancel();

        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        boolean isSecondUploadFilePopupDisplayed = publicationFilesPage().isUploadFilePopupDisplayed();
        Assertions.assertFalse(isSecondUploadFilePopupDisplayed, "Second Upload PDF file popup is closed");
    }

    /**
     * STORY/BUG - HALCYONST-13838 <br>
     * SUMMARY - No PDF file and zero-size PDF file uploading <br>
     * USER - Legal <br>
     */
    @Test
	@EDGE
    @LEGAL
    @LOG
    public void proofFilePdfUploadNoFileAndZeroSizeFileTest()
    {
        String zeroSizePdfFileName = "zeroSizePdfFile.pdf";
        String pubFileName = "file107";
        String expectedNotificationText = "Error: Can not proceed with an empty multipart file";

        homePage().goToHomePage();
        loginPage().logIn();

        boolean ocExtractPageOpened = toolsMenu().goToOCExtract();
        Assertions.assertTrue(ocExtractPageOpened, "The OC Extract page opened");
        boolean publicationsPageOpened = publicationsPage().isPageOpened();
        Assertions.assertTrue(publicationsPageOpened, "Publications page opened");

        publicationsTablePage().doubleClickFirstPublication();
        boolean publicationFilesPageOpened = publicationFilesPage().isPageOpened();
        Assertions.assertTrue(publicationFilesPageOpened, "Publication files page opened");

        publicationFilesTablePage().rightClickPubFileByName(pubFileName);
        publicationFilesContextMenu().clickPdfUpload();
        uploadFilePopup().clickSubmit();
        boolean isNoChosenFileErrorTextDisplayed = uploadFilePopup().isNoChosenFileErrorTextDisplayed();
        Assertions.assertTrue(isNoChosenFileErrorTextDisplayed, "Error text is displayed after submitting no chosen file");

        uploadFilePopup().clickUpload();
        uploadFilePopup().chooseFile(zeroSizePdfFileName);
        boolean uploadCheckMarkDisplayed = uploadFilePopup().isUploadCheckMarkDisplayed();
        uploadFilePopup().clickSubmit();
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        boolean isNotificationPopupDisplayed = toastNotificationPopup().waitForNotification();
        String actualNotificationText = toastNotificationPopup().getNotificationText();

        Assertions.assertAll
        (
                () -> Assertions.assertTrue(uploadCheckMarkDisplayed, "Upload check mark is displayed"),
                () -> Assertions.assertTrue(isNotificationPopupDisplayed, "Notification Popup is displayed"),
                () -> Assertions.assertEquals(expectedNotificationText, actualNotificationText, "Actual notification popup text equals to expected one")
        );
    }
}
