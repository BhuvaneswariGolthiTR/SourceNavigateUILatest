package com.thomsonreuters.codes.codesbench.quality.pageelements.nodAngular.popups;

public class NotificationPopupElementsAngular
{
	public static final String NOTIFICATION_POPUP = "//bento-toast-container";
	public static final String TOAST_BODY = NOTIFICATION_POPUP + "//div[@class='toast-body']";
	public static final String TEXT = TOAST_BODY + "/span";
	public static final String CLOSE_BUTTON = TOAST_BODY + "/button[@class='close']";
}
