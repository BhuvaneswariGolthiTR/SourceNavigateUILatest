package com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.popups;

public class SourceNavigateAngularToastPageElements
{
    public static final String TOAST_BODY = "//div[@class='toast-body']";
    public static final String TOAST_BODY_CLOSE = "//div[@class='modal-content']//button[@aria-label='Close']";
    public static final String TOAST_BODY_TEXT = "//div[@class='modal-content']//span[text()='%s']";

    // ---------- Toast messages ----------

    public static final String ADD_EXISTING_FRAGMENT = "Fragment '%s' already exists in the Draft Index Entry";
    public static final String DISPLAYING_MULTIPLE_AND_DUPLICATE_RENDITIONS = "Displaying all results by Rendition UUID column filter, regardless of other filters.";
}
