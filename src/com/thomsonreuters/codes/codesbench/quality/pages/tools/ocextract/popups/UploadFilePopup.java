package com.thomsonreuters.codes.codesbench.quality.pages.tools.ocextract.popups;

import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.ocextract.popups.UploadFilePopupElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import com.thomsonreuters.codes.codesbench.quality.utilities.autoIT.AutoITUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class UploadFilePopup extends BasePage
{

    private final WebDriver driver;

    @Autowired
    public UploadFilePopup(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, UploadFilePopupElements.class);
    }

    public void clickUpload()
    {
        waitForPageLoaded();
        click(UploadFilePopupElements.UPLOAD_BUTTON);
    }

    public void clickCancel()
    {
        waitForPageLoaded();
        click(UploadFilePopupElements.CANCEL_BUTTON);
    }

    public void clickSubmit()
    {
        waitForPageLoaded();
        click(UploadFilePopupElements.SUBMIT_BUTTON);
    }

    public void chooseFile(String fileName)
    {
        AutoITUtils.handleChooseFileToUploadWindowWithAutoIT(fileName);
    }

    public boolean isUploadCheckMarkDisplayed()
    {
        waitForElement(UploadFilePopupElements.UPLOAD_CHECK_MARK);
        return isElementDisplayed(UploadFilePopupElements.UPLOAD_CHECK_MARK);
    }

    public boolean isNoChosenFileErrorTextDisplayed()
    {
        return isElementDisplayed(UploadFilePopupElements.NO_CHOSEN_FILE_ERROR);
    }
}
