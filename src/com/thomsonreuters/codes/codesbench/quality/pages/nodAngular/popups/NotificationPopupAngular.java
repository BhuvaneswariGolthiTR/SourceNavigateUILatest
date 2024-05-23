package com.thomsonreuters.codes.codesbench.quality.pages.nodAngular.popups;

import com.thomsonreuters.codes.codesbench.quality.pageelements.nodAngular.popups.NotificationPopupElementsAngular;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class NotificationPopupAngular extends BasePage
{

    @Autowired
    public NotificationPopupAngular(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    private final WebDriver driver;

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, NotificationPopupElementsAngular.class);
    }

    public String getNotificationText()
    {
        String result = "There is no notification";
        if(doesElementExist(NotificationPopupElementsAngular.TEXT))
        {
            result=getElementsText(NotificationPopupElementsAngular.TEXT);
        }
        return result;
    }

    public void closeNotification()
    {
        if(doesElementExist(NotificationPopupElementsAngular.CLOSE_BUTTON))
        {
            sendEnterToElement(NotificationPopupElementsAngular.CLOSE_BUTTON);
            waitForElementGone(NotificationPopupElementsAngular.NOTIFICATION_POPUP);
        }
    }

    public boolean waitForNotification()
    {
        int loopCount = 0;
        while (!doesElementExist(NotificationPopupElementsAngular.TOAST_BODY) && loopCount < 7)
        {
            DateAndTimeUtils.takeNap(DateAndTimeUtils.ONE_SECOND);
            loopCount++;
        }
        return doesElementExist(NotificationPopupElementsAngular.TOAST_BODY);
    }
}
