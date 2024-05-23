package com.thomsonreuters.codes.codesbench.quality.pageelements.nodAngular;

public class TitleBarRibbonElementsAngular {

    public static final String PAGE_TAG = "//body/app-root/app-header";
    public static final String NAVIGATION_LIST = PAGE_TAG + "//ul[@class = 'navigation']";
    public static final String HOME = NAVIGATION_LIST + "//a[contains(text(), 'Home')]";
    public static final String SUBSCRIBED_CASES = NAVIGATION_LIST + "//a[contains(text(), 'Subscribed Cases')]";
    public static final String CASES_PAGE = NAVIGATION_LIST + "//a[contains(text(), 'Cases Page')]";
    public static final String COURTS = NAVIGATION_LIST + "//a[contains(text(), 'Courts')]";
    public static final String ADMIN_OPINIONS = NAVIGATION_LIST + "//a[contains(text(), 'Administrative Opinions')]";

}

