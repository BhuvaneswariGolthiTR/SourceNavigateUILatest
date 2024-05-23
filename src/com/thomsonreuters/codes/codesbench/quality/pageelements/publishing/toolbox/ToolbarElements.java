package com.thomsonreuters.codes.codesbench.quality.pageelements.publishing.toolbox;

public class ToolbarElements
{
        public static final String TOOLBAR = "//div[@class='main-toolbar']";
        public static final String NEXT = TOOLBAR + "//div[@class='confirmation-buttons']//button[contains(@class, 'mat-raised-button mat-button-base mat-primary')]//span[contains(text(), 'Next')]";
        public static final String SUBMIT = TOOLBAR + "//span[text()=' Submit ']";
        public static final String BACK = TOOLBAR + "//span[text()=' Back ']";
        public static final String PROGRESS_BAR = TOOLBAR + "//mat-progress-bar";
        public static final String RELOAD = TOOLBAR + "//button[@aria-label='Reload']";
        public static final String LOAD_COMPLETE_FOR_LAST_30_DAYS_BUTTON = TOOLBAR + "//button[@aria-label='Load 30']/span/mat-icon";
        public static final String PUBLISH_NOW_RADIO_BUTTON_XPATH = TOOLBAR + "//div[contains(@class,'publish-checkbox')]//div[contains(text(), 'Publish Now')]/../div[@class='mat-radio-container']";
        public static final String SCHEDULED_PUBLISH_RADIO_BUTTON_XPATH = TOOLBAR + "//div[contains(@class,'publish-checkbox')]//div[contains(text(), 'Scheduled Publish') and contains(text(), 'Central')]/../div[@class='mat-radio-container']";
        public static final String CLEAR_FILTERS = TOOLBAR + "//button[@aria-label='Clear Filters']";
        public static final String REFRESH = TOOLBAR + "//button[@aria-label='Refresh']";
        public static final String LOAD_COMPLETE_FOR_PAST_30_DAYS = TOOLBAR + "//button[@aria-label='Load 30']";
        public static final String V_WARNING_XPATH = "//div[text()='Warning: V']";
        public static final String R_WARNING_XPATH = "//div[text()='Warning: R']";
        public static final String P_WARNING_MESSAGE_XPATH = "//div[text()='Warning: P']";
        public static final String HIDE_FULL_VOLS_CHECKBOX = "//span[@class='mat-checkbox-label' and contains(text(),'Hide Full Vols')]/../div[@class='mat-checkbox-inner-container']/input";
        public static final String HIDE_FULL_VOLS_SAR_CHECKBOX = "//span[@class='mat-checkbox-label' and contains(text(),'Hide Full Vols-SAR')]/../div[@class='mat-checkbox-inner-container']/input";
        public static final String HIDE_COM_LAW_TRACKING_CHECKBOX = "//span[@class='mat-checkbox-label' and contains(text(),'Hide COM Law Tracking')]/../div[@class='mat-checkbox-inner-container']/input";
        public static final String HIDE_FULL_VOLS_RECOMP_CHECKBOX = "//span[@class='mat-checkbox-label' and contains(text(),'Hide Full Vols-Recomp')]/../div[@class='mat-checkbox-inner-container']/input";
        public static final String HIDE_FULL_VOLS_COMPARE_CHECKBOX = "//span[@class='mat-checkbox-label' and contains(text(),'Hide Full Vols-Compare')]/../div[@class='mat-checkbox-inner-container']/input";
        public static final String VOLUME_SELECTION = "//div[@class='volume-selection-container']//span[text()=' Volume Selection ']";
}
