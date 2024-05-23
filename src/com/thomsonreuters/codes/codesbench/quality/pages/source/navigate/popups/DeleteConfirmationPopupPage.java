package com.thomsonreuters.codes.codesbench.quality.pages.source.navigate.popups;

import com.thomsonreuters.codes.codesbench.quality.pageelements.source.SourceNavigatePageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.DeleteConfirmationPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import com.thomsonreuters.codes.codesbench.quality.utilities.autoIT.AutoITUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class DeleteConfirmationPopupPage extends BasePage {

    private WebDriver driver;

    @Autowired
    public DeleteConfirmationPopupPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, DeleteConfirmationPageElements.class);
    }

    public boolean clickYes()
    {
        click(DeleteConfirmationPageElements.yesButton);
        AutoITUtils.verifyAlertTextContainsAndAccept(true, "The following workflow has been started for sync processing: ");
        waitForGridRefresh();
        breakOutOfFrame();
        boolean deleteConfirmed = switchToWindow(SourceNavigatePageElements.NAVIGATE_PAGE_TITLE);
        return deleteConfirmed;
    }
}
