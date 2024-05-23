package com.thomsonreuters.codes.codesbench.quality.pageelements.editor.popups;

public class DocumentPreviewPageElements
{
    //Title
    public static final String PAGE_TITLE = "Document Preview";

    //Buttons
    public static final String REFRESH_BUTTON = "//button[@id='b_refresh']";
    public static final String CLOSE_BUTTON = "//button[@id='b_close']";
    public static final String PHRASE_WITH_BOLD_MARKUP = "//b[text()='%s']";
    public static final String PHRASE_WITH_ADDED_MARKUP = "//u[@class='highlighted' and text()='%s']";
    public static final String PHRASE_WITH_STRIKED_MARKUP = "//s[text()='%s']";
    public static final String PHRASE_WITH_UNDERLINE_MARKUP = "//u[not(@class) and text()='%s']";
    public static final String PHRASE_WITH_SUPERSCRIPT_MARKUP = "//sup[text()='%s']";
    public static final String PHRASE_WITH_SUBSCRIPT_MARKUP = "//sub[text()='%s']";
    public static final String PARATEXT = "//div[@class='paratext']";
    public static final String PARA = "//div[@class='para']";
    public static final String PLACEHOLDER = "//div[@class='placeholder']";
    public static final String CODES_HEAD = "//div[@class='codes.head']";
}
