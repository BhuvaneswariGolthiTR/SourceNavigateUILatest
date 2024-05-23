package com.thomsonreuters.codes.codesbench.quality.utilities.enums;

import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements;

public enum MarkupValue
{
        ADD("Add", EditorTextPageElements.ADDED_TEXT, "added.material"),
        DELETE("Delete", EditorTextPageElements.DELETED_TEXT, "deleted.material"),
        BOLD("Bold", EditorTextPageElements.BOLD_TEXT, "bold"),
        ITALIC("Italic", EditorTextPageElements.ITALIC_TEXT, "ital"),
        CAP_CONDITIONAL("Cap conditional", EditorTextPageElements.CAP_CONDITIONAL_TEXT, "uc"),
        SMALL_CAPS("Small caps", EditorTextPageElements.SMALL_CAPS_TEXT, "csc"),
        PARAGRAPH_ID_INCLUDE("Paragraph id include", EditorTextPageElements.PARAGRAPH_ID_INCLUDE_TEXT, "cent8"),
        PARAGRAPH_ID_IGNORE("Paragraph id ignore", EditorTextPageElements.PARAGRAPH_ID_IGNORE_TEXT, "cent9"),
        WORDS_AND_PHRASES("Words & Phrases", EditorTextPageElements.ELEMENT_WITH_WORDPHRASE_REGEX, "wordphrase"),
        CHARACTER_FILL("Character fill", EditorTextPageElements.CHARACTER_FILL_TEXT, "percentw"),
        CHARACTER_GENERATE("Character generate", EditorTextPageElements.CHARACTER_GENERATE_TEXT, "charfill"),
        PRINT_SUPPRESS("Print suppress", EditorTextPageElements.PRINT_SUPPRESS_TEXT, "centk"),
        WESTLAW_SUPPRESS("Westlaw suppress", EditorTextPageElements.WL_SUPPRESS_TEXT, "centw"),
        VENDOR_INCLUDE("Vendor include", EditorTextPageElements.VENDOR_INCLUDE_TEXT, "cent3"),
        CASE_HISTORY_ID("Case history id", EditorTextPageElements.CASE_HISTORY_ID_TEXT, "centh"),
        END_LEFT("End left", EditorTextPageElements.ELEMENT_WITH_END_LEFT_REGEX, "percenttl"),
        END_CENTER("End center", EditorTextPageElements.ELEMENT_WITH_END_CENTER_REGEX, "percenttc"),
        END_RIGHT("End right", EditorTextPageElements.ELEMENT_WITH_END_RIGHT_REGEX, "percenttr"),
        END_JUSTIFY("End justify", EditorTextPageElements.ELEMENT_WITH_END_JUSTIFY_REGEX, "percenttj"),
        WESTLAW_TABLE_CODE("Westlaw Table Code", EditorTextPageElements.WTC_TEXT, "centdol"),
        CITE_REFERENCE("Cite reference", EditorTextPageElements.CITE_REFERENCE_TEXT, "cite.query"),
        QUOTE(null, EditorTextPageElements.QUOTE_TEXT, "quote"),
        CITE_TREATMENT(null, EditorTextPageElements.CITE_TREATMENT_TEXT, "citator.treatment"),
        CONDITIONAL_CASE(null, EditorTextPageElements.CONDITIONAL_CASE_TEXT, "conditional"),
        CROSSHATCH_VETOED(null, EditorTextPageElements.CROSSHATCH_VETOED_TEXT, "crosshatch");


        private String buttonTitle;
        private String textElement;
//        private String dropdownOption;
        private String dropdownOption;

        MarkupValue(String buttonTitle, String textElement, String dropdownOption)
        {
                this.buttonTitle = buttonTitle;
                this.textElement = textElement;
                this.dropdownOption = dropdownOption;
        }

        public String getButtonTitle()
        {
                return buttonTitle;
        }

        public String getTextElement()
        {
                return textElement;
        }

        public String getDropdownOption()
        {
                return  dropdownOption;
        }

}
