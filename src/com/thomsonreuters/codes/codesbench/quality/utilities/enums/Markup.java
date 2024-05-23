package com.thomsonreuters.codes.codesbench.quality.utilities.enums;

import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.popups.InsertWestMarkupPageElements;

public enum Markup
{
        ADD("Add", EditorTextPageElements.ADDED_TEXT, "added (centa)"),
        DELETE("Delete", EditorTextPageElements.DELETED_TEXT, "deleted (centd)"),
        BOLD("Bold", EditorTextPageElements.BOLD_TEXT, "bold (pcth)"),
        ITALIC("Italic", EditorTextPageElements.ITALIC_TEXT, "italic (pcti)"),
        CAP_CONDITIONAL("Cap conditional", EditorTextPageElements.CAP_CONDITIONAL_TEXT, "cap conditional (uc)"),
        SMALL_CAPS("Small caps", EditorTextPageElements.SMALL_CAPS_TEXT, "small caps (csc)"),
        PARAGRAPH_ID_INCLUDE("Paragraph id include", EditorTextPageElements.PARAGRAPH_ID_INCLUDE_TEXT, "paragraph id include (cent8)"),
        PARAGRAPH_ID_IGNORE("Paragraph id ignore", EditorTextPageElements.PARAGRAPH_ID_IGNORE_TEXT, "paragraph id ignore (cent9)"),
        WORDS_AND_PHRASES("Words & Phrases", EditorTextPageElements.ELEMENT_WITH_WORDPHRASE_REGEX, "wordphrase"),
        CHARACTER_FILL("Character fill", EditorTextPageElements.CHARACTER_FILL_TEXT, "character fill (pctw)"),
        CHARACTER_GENERATE("Character generate", EditorTextPageElements.CHARACTER_GENERATE_TEXT, "character generate (charfill/pcty)"),
        PRINT_SUPPRESS("Print suppress", EditorTextPageElements.PRINT_SUPPRESS_TEXT, "print suppress (centk)"),
        WESTLAW_SUPPRESS("Westlaw suppress", EditorTextPageElements.WL_SUPPRESS_TEXT, "WL suppress (centw)"),
        VENDOR_INCLUDE("Vendor include", EditorTextPageElements.VENDOR_INCLUDE_TEXT, "vendor include (cent3)"),
        CASE_HISTORY_ID("Case history id", EditorTextPageElements.CASE_HISTORY_ID_TEXT, "case history id (centh)"),
        END_LEFT("End left", EditorTextPageElements.ELEMENT_WITH_END_LEFT_REGEX, "end left (pcttl)"),
        END_CENTER("End center", EditorTextPageElements.ELEMENT_WITH_END_CENTER_REGEX, "end center (pcttc)"),
        END_RIGHT("End right", EditorTextPageElements.ELEMENT_WITH_END_RIGHT_REGEX, "end right (pcttr)"),
        END_JUSTIFY("End justify", EditorTextPageElements.ELEMENT_WITH_END_JUSTIFY_REGEX, "end justify (pcttj)"),
        WESTLAW_TABLE_CODE("Westlaw Table Code", EditorTextPageElements.WTC_TEXT, "WTC (cent$)"),
        CITE_REFERENCE("Cite reference", EditorTextPageElements.CITE_REFERENCE_TEXT, "cite reference (centy)"),
        QUOTE(null, EditorTextPageElements.QUOTE_TEXT, "block quote"),
        CITE_TREATMENT(null, EditorTextPageElements.CITE_TREATMENT_TEXT, "cite treatment"),
        CONDITIONAL_CASE(null, EditorTextPageElements.CONDITIONAL_CASE_TEXT, "conditional case (cent&)"),
        CROSSHATCH_VETOED(null, EditorTextPageElements.CROSSHATCH_VETOED_TEXT, "crosshatch vetoed");


        private String buttonTitle;
        private String textElement;
        private String dropdownOption;

        Markup(String buttonTitle, String textElement, String dropdownOption)
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
                return String.format(InsertWestMarkupPageElements.MARKUP_TO_BE_INSERTED, dropdownOption);
        }
}
