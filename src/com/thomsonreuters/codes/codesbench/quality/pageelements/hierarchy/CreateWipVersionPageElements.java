package com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class CreateWipVersionPageElements
{
    public static final String CREATE_WIP_VERSION_PAGE_TITLE = "Create WIP Version";
    public static final String PLEASE_SET_LAW_TRACKING_ERROR_MESSAGE_XPATH = "//ul[@id='errors']//span[contains(text(),'Please set Law Tracking')]";
    public static final String SUBMIT_BUTTON_XPATH = "//input[@id='pageForm:ok']";
    public static final String CANCEL_BUTTON_XPATH = "//input[@id='pageForm:cancelButton']";
    public static final String C2012_LAW_TRACKING_BUTTON_XPATH = "//input[@id='pageForm:C2012SetLawTracking']";
    public static final String FULL_VOLS_BUTTON_XPATH = "//input[@id='pageForm:setLawTrackingFull']";
    public static final String FULL_VOLS_COMPARE_BUTTON_XPATH = "//input[@id='pageForm:setLawTrackingFullCompare']";
    public static final String FULL_VOLS_RECOMP_BUTTON_XPATH = "//input[@id='pageForm:setLawTrackingFullRecomp']";
    public static final String QUICK_LOAD_BUTTON_XPATH = "//input[@id='pageForm:setLawTrackingQuick']";

    @FindBy(how = How.XPATH, using = QUICK_LOAD_BUTTON_XPATH)
    public static WebElement quickLoadButton;

    @FindBy(how = How.ID, using = "pageForm:ok")
    public static WebElement submitButton;
}