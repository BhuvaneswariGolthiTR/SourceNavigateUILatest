package com.thomsonreuters.codes.codesbench.quality.pages.tools.ocextract.popups;

import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.ocextract.popups.NotificationPopupElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class ToastNotificationPopup extends BasePage
{

    private final WebDriver driver;

    @Autowired
    public ToastNotificationPopup(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, NotificationPopupElements.class);
    }

    public boolean waitForNotification()
    {
        return waitForElementExists(NotificationPopupElements.NOTIFICATION_POPUP, 1000);
    }

    public String getNotificationText()
    {
        return getElementsText(NotificationPopupElements.NOTIFICATION_TEXT);
    }
}
