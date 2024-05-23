package com.thomsonreuters.codes.codesbench.quality.pageelements.editor.popups;

public class SpellcheckPageElements
{
    public static final String PAGE_TITLE = "Spell Check";

    //Internal Buttons in Editor
    public static final String REPLACE_BUTTON = "//button[@id='b_replace']";
    public static final String REPLACE_WITH_TEXTBOX = "//input[@id='v_replacement']";
    public static final String COMMIT_CHANGES_AND_CLOSE_BUTTON = "//button[@id='b_ok']";
    public static final String DISCARD_CHANGES_AND_CLOSE_BUTTON = "//button[@id='b_cancel']";

    public static final String WORDS_FOUND_BY_SPELLCHECK = 	"//span[contains(@class, 'HA-spellcheck-error')]";
}
