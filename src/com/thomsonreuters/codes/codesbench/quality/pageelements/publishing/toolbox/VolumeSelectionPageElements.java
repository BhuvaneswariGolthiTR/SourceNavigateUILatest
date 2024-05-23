package com.thomsonreuters.codes.codesbench.quality.pageelements.publishing.toolbox;

public class VolumeSelectionPageElements
{
    public static final String VOLUME_SELECTION_XPATH = "//toolbox-volume-selection";

    public static final String AVAILABLE_VOLUMES_TITLE = VOLUME_SELECTION_XPATH + "//p[contains(text(),'Available Volumes')]";
    public static final String SELECTED_VOLUMES_TITLE = VOLUME_SELECTION_XPATH + "//p[contains(text(),'Selected Volumes')]";

    public static final String CHECK_BOX_FOR_SPECIFIC_VOLUME_UNDER_AVAILABLE_VOLUME = VOLUME_SELECTION_XPATH + "//div[contains(@class,'col-sm transferlist')][1]//div[contains(text(),'%s')]/preceding-sibling::mat-pseudo-checkbox";

    public static final String CHECK_BOX_FOR_SPECIFIC_VOLUME_UNDER_SELECTED_VOLUME = VOLUME_SELECTION_XPATH + "//div[contains(@class,'col-sm transferlist')][2]//div[contains(text(),'%s')]/preceding-sibling::mat-pseudo-checkbox";

    public static final String ADD_BUTTON = VOLUME_SELECTION_XPATH + "//button/span[contains(text(),'Add')]";
    public static final String REMOVE_BUTTON = VOLUME_SELECTION_XPATH + "//button/span[contains(text(),'Remove')]";
    public static final String CONFIRM_BUTTON = "//div[@class='confirm-btn-container']//span[contains(text(),'Confirm')]";
    public static final String CANCEL_BUTTON = "//div[@class='confirm-btn-container']//span[contains(text(),'Cancel')]";
    public static final String AVAILABLE_VOLUMES_COUNT = VOLUME_SELECTION_XPATH + "//P[contains(text(), 'Available Volumes')]/descendant::span";
    public static final String SELECTED_VOLUMES_COUNT = VOLUME_SELECTION_XPATH + "//P[contains(text(), 'Selected Volumes')]/descendant::span";

    public static final String SELECTED_VOLUMES_XPATH = VOLUME_SELECTION_XPATH + "//P[contains(text(), 'Selected Volumes')]/../..//div[@class = 'mat-list-text']";
}

