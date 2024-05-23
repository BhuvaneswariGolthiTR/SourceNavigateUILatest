package com.thomsonreuters.codes.codesbench.quality.pages.tools;

import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.RedliningComparePageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import com.thomsonreuters.codes.codesbench.quality.utilities.autoIT.AutoITUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class RedliningComparePage extends BasePage {
    private WebDriver driver;

    @Autowired
    public RedliningComparePage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, RedliningComparePageElements.class);
    }

    public void selectAndAddSingleVolume(String volumeNumber)
    {
        click(String.format(RedliningComparePageElements.VOLUME, volumeNumber));
        click(RedliningComparePageElements.addOneVolumeButton);
        waitForPageLoaded();
    }

    public void clickNext()
    {
        click(RedliningComparePageElements.nextButton);
        waitForPageLoaded();
    }

    public void clickCancel()
    {
        click(RedliningComparePageElements.cancelButton);
        AutoITUtils.verifyAlertTextAndAccept(true, "Are you sure you want to cancel compare process?");
        waitForWindowGoneByTitle(RedliningComparePageElements.REDLINING_COMPARE_PAGE_TITLE);
    }

    /**
     * This method is used to see whether or not a specific document exists in the redlining report page by checking for the node uuid
     * @param nodeUUID
     * @return returns the boolean value of the expected document check
     */
    public boolean isExpectedNodeUUIDPresent(String nodeUUID)
    {
        return doesElementExist(String.format(RedliningComparePageElements.UNSELECTED_DOCUMENT_PANE_DOCUMENT, nodeUUID));
    }
}
