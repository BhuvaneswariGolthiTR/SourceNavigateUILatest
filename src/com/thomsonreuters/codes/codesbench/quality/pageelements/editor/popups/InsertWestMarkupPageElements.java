package com.thomsonreuters.codes.codesbench.quality.pageelements.editor.popups;

public class InsertWestMarkupPageElements
{
    //Title
    public static final String WINDOW_TITLE = "Insert West Markup";

    //Elements
    public static final String SAVE_BUTTON = "//input[@id='pageForm:saveButton']";
    public static final String MARKUP_TO_BE_INSERTED_DROPDOWN = "//select[@id='pageForm:elementName']";
    public static final String CANCEL_BUTTON = "//input[@id='pageForm:cancelButton']";

    //Markups
    private static final String PREFIX = "//option[text()='";
    private static final String SUFFIX = "']";
    public static final String BOLD = PREFIX + "bold (pcth)" + SUFFIX;
    public static final String ADDED = PREFIX + "added (centa)" + SUFFIX;
    public static final String DELETED = PREFIX + "deleted (centd)" + SUFFIX;
    public static final String STRIKETHROUGH = PREFIX + "strikethrough (pctx)" + SUFFIX;
    public static final String UNDERSCORE = PREFIX + "underscore" + SUFFIX;
    public static final String SUPERSCRIPT = PREFIX + "superscript" + SUFFIX;
    public static final String SUBSCRIPT = PREFIX + "subscript" + SUFFIX;

    public static final String MARKUP_TO_BE_INSERTED = "//option[text()='%s']";
    public static final String MARKUP_TO_BE_INSERTED_SELECT_ID = "pageForm:elementName";



}






