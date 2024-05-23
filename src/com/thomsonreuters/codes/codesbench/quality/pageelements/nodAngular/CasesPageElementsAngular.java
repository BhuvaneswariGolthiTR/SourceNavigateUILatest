package com.thomsonreuters.codes.codesbench.quality.pageelements.nodAngular;

public class CasesPageElementsAngular {

    public static final String HEADING = "Cases Page";
    public static final String PAGE_TITLE = "NodClassifyUi";
    public static final String URL_MODIFIER = "cases";
    public static final String PAGE_TAG = "//body/app-root/app-cases";
    public static final String GRID = "//ag-grid-angular";
    public static final String CONTENT_SET_TEAM_MESSAGE = PAGE_TAG +
            "/div[@class='content-set-bar']/following-sibling::h6[contains(text(), 'Currently viewing cases for:')]";
    public static final String GO_BUTTON = "//button[text() = 'Go']";
    public static final String CONTENT_SET_TEAM_INPUT = "//input[contains(@class, 'bui-menu-item')]";


}

