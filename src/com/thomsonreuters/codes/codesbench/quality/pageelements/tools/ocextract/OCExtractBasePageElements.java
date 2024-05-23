package com.thomsonreuters.codes.codesbench.quality.pageelements.tools.ocextract;

public class OCExtractBasePageElements
{
    public static final String PAGE_TAG = "//body/toolbox-app-root/toolbox-o-connors-feature";
    public static final String PAGE_TITLE = "Toolbox";
    public static final String BACK_TO_HOME_DIV = "//div[@id='backToHome']";
    public static final String PAGE_FIRST_HEADER_LINE = BACK_TO_HOME_DIV + "/div[@class='toolbox-header-brand-first-line'][text()='CODESBENCH']";
    public static final String PAGE_SECOND_HEADER_LINE = BACK_TO_HOME_DIV + "/div[@class='toolbox-header-brand-second-line'][text()='Publishing Toolbox']";
    public static final String PAGE_SECTION_HEADER = "//toolbox-o-connors-feature/div[@class='navbar section-header']/h2[text()=\"O'Connors Processing\"]";
    public static final String TAB_PANEL_DIV = "//div[@role='tabpanel']";
    public static final String TABLE_NAME_XPATH = TAB_PANEL_DIV + "//div[@class='tb-tile left-part']/span[text()='%s']";
    public static final String TAB_PANEL_VISIBILITY_XPATH = "//div[@id='cdk-step-content-0-%s' and contains(@style, 'visibility: visible')]";
    public static final String PUBLICATION_FILES_BUTTON = "//mat-step-header[@id='cdk-step-label-0-1']/div[text()='Publication Files']";
}
