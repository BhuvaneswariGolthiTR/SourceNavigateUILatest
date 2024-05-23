package com.thomsonreuters.codes.codesbench.quality.pageelements.tools.ocextract.popups;

public class NotificationPopupElements
{
    public static final String NOTIFICATION_POPUP = "//div[contains(@id,'cdk-overlay')]";
    public static final String NOTIFICATION_TEXT = NOTIFICATION_POPUP + "//span[@class='Toast-message ng-star-inserted']";
}
