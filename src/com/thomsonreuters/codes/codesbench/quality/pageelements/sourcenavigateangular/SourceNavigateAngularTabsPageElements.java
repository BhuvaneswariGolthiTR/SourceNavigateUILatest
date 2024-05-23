package com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular;

public class SourceNavigateAngularTabsPageElements
{
    public static final String LINEAGE_TAB_NAME = "Lineage";
    public static final String SECTION_GROUP_TAB_NAME = "Section Group";
    public static final String SECTION_TAB_NAME = "Section";
    public static final String DELTA_GROUP_TAB_NAME = "Delta Group";
    public static final String DELTA_TAB_NAME = "Delta";

    public static final String RENDITION_TAB = "//span[text()='Rendition' or text()=' Rendition ']";
    public static final String LINEAGE_TAB = "//span[text()=' Lineage ']";
    public static final String SECTION_GROUP_TAB = "//span[text()=' Section Group ']";
    public static final String SECTION_TAB = "//span[text()=' Section ']";
    public static final String DELTA_GROUP_TAB = "//span[text()=' Delta Group ']";
    public static final String DELTA_TAB = "//span[text()=' Delta ']";

    public static final String BENTO_WIZARD_STEPS = "//div[@class='bento-wizard-steps']/div[@class='steps']";
    public static final String TAB_XPATH = BENTO_WIZARD_STEPS +
            "//span[contains(@class, 'wizard-title') and contains(text(),' %s ')]/parent::button";
    public static final String SOURCE_TAB_NAME = "(//span[@class='smart-badge-host']/span[text()='Source'])[1]";
    public static final String LOCK_REPORT_TAB_NAME = "//a[text()='Lock Report']";
    public static final String ANY_TAB_NAME = "//a[text()='%s']";
}
