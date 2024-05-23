package com.thomsonreuters.codes.codesbench.quality.pageelements.nodAngular;

public class HomePageElementsAngular {

    public static final String PAGE_TITLE = "NodClassifyUi";
    public static final String URL_MODIFIER = "";
    public static final String PAGE_TAG = "//body/app-root/app-home";
    public static final String NAVIGATION_CONTAINER = PAGE_TAG + "//div[@class = 'navigation-container']";
    public static final String NAVIGATION_LIST = "//ul[@class = 'navigation-list']";
    public static final String LIST_BULLETPOINT = "//li[@class = 'underscore']";
    public static final String CONTENT_SET_PLACEHOLDER = "//form[text()=' %s ']";

    //LINKS
    public static final String LINKS = NAVIGATION_CONTAINER + "//*[normalize-space() = 'Links']/parent::div" + NAVIGATION_LIST;
    public static final String SUBSCRIBED_CASES = LINKS + LIST_BULLETPOINT + "//a[normalize-space()='Subscribed Cases']";
    public static final String CASES = LINKS + LIST_BULLETPOINT + "//a[normalize-space()='Cases']";
    public static final String COURTS = LINKS + LIST_BULLETPOINT + "//a[normalize-space()='Courts']";
    public static final String ADMINISTRATIVE_OPINIONS = LINKS + LIST_BULLETPOINT + "//a[normalize-space() = 'Administrative Opinions']";

    //REPORTS
    public static final String REPORTS = NAVIGATION_CONTAINER + "//*[normalize-space() = 'Reports']/parent::div" + NAVIGATION_LIST;
    public static final String NO_TEAM = REPORTS + "//a[normalize-space() = 'No Team']";
    public static final String SUMMARY = REPORTS + "//a[normalize-space() = 'Summary']";
    public static final String DETAIL = REPORTS + "//a[normalize-space() = 'Detail']";
    public static final String AUTO_MERGE = REPORTS + "//a[normalize-space() = 'Auto Merge']";

    //TOOLS
    public static final String TOOLS = NAVIGATION_CONTAINER + "//*[normalize-space() = 'Tools']/parent::div" + NAVIGATION_LIST;
    public static final String IMPORT_COURT_TEAM_ROUTING = TOOLS+LIST_BULLETPOINT+"//a[normalize-space()='Import Court Team Routing']";
    public static final String INITIATE_NOD_BATCH_MERGE = TOOLS+LIST_BULLETPOINT+"//a[normalize-space()='Initiate NOD Batch Merge']";
    public static final String INITIATE_NOD_UNMERGED_REPORT = TOOLS+LIST_BULLETPOINT+"//a[normalize-space()='Initiate NOD Unmerged Report']";
    public static final String INITIATE_NOD_UPDATE = TOOLS+LIST_BULLETPOINT+"//a[normalize-space()='Initiate NOD Update']";
    public static final String INITIATE_NOD_DATA_VALIDATION = TOOLS+LIST_BULLETPOINT+"//a[normalize-space()='Initiate NOD Data Validation']";
    public static final String INITIATE_XUSSC_UPDATE = TOOLS+LIST_BULLETPOINT+"//a[normalize-space()='Initiate XUSSC Update']";
    public static final String INITIATE_NOD_BATCH_REORDER = TOOLS+LIST_BULLETPOINT+"//a[normalize-space()='Initiate NOD Batch Reorder']";
    public static final String IMPORT_LTC_NOVUS_LOAD = TOOLS+LIST_BULLETPOINT+"//a[normalize-space()='Import LTC/Novus Load']";

    //MESSAGE
    public static final String MESSAGE = "//div[@class='toast-body']";
    public static final String MESSAGE_CLOSE_BUTTON = MESSAGE + "//button[contains(@class,'close')]";

}

