package com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.indexai;

import static java.lang.String.format;

public class IndexingPageElements
{
    // ---------- BOXES ----------

    public static final String BOX_NAME_XPATH = "//h3[contains(text(),'%s')]";

    public static final String SESSION_LAW = "//source-nav-session-law";
    public static final String SUGGESTED_INDEX_ENTRIES = "//source-nav-suggested-index-entries";
    public static final String DRAFT_INDEX_ENTRY = "//source-nav-draft-index-entry";
    public static final String FINAL_INDEX_ENTRIES = "//source-nav-final-index-entry";
    public static final String DRAG_SCROLL = "//drag-scroll[@class='focusable-box']";

    public static final String SESSION_LAW_BOX = SESSION_LAW + DRAG_SCROLL;
    public static final String FRAGMENTS_BOX = "//source-nav-index-entries-fragments" + DRAG_SCROLL;
    public static final String SUGGESTED_INDEX_ENTRIES_BOX = SUGGESTED_INDEX_ENTRIES + DRAG_SCROLL;
    public static final String DRAFT_INDEX_ENTRY_BOX = DRAFT_INDEX_ENTRY + "/div[@dragula='DRAFT_ENTITIES']";
    public static final String FINAL_INDEX_ENTRIES_BOX = FINAL_INDEX_ENTRIES + DRAG_SCROLL;

    // ---------- BUTTONS ----------

    public static final String BUTTON = "//button";
    public static final String BUTTON_NGBTOOLTIP = BUTTON + "[@ngbtooltip='%s']";
    public static final String BUTTON_TOOLTIP_WINDOW = BUTTON + "/following-sibling::ngb-tooltip-window";

    public static final String SEE_MORE_HYPERLINK = "//a[text()='see more']";

    public static final String PRINT_PREVIEW_BUTTON = SESSION_LAW +
            format(BUTTON_NGBTOOLTIP, "Print preview");
    public static final String OPEN_IN_EDITOR_BUTTON = SESSION_LAW +
            format(BUTTON_NGBTOOLTIP, "Open editor");

    public static final String EDIT_SUGGESTED_INDEX_ENTRY_BUTTON = SUGGESTED_INDEX_ENTRIES +
            format(BUTTON_NGBTOOLTIP, "Move to draft");
    public static final String SAVE_SUGGESTED_INDEX_ENTRY_BUTTON = SUGGESTED_INDEX_ENTRIES +
            format(BUTTON_NGBTOOLTIP, "Move to final");

    public static final String SWAP_DRAFT_INDEX_ENTRY_BUTTON = DRAFT_INDEX_ENTRY +
            format(BUTTON_NGBTOOLTIP, "Swap fragments");
    public static final String PLUS_DRAFT_INDEX_ENTRY_BUTTON = DRAFT_INDEX_ENTRY +
            format(BUTTON_NGBTOOLTIP, "Add fragment");
    public static final String REMOVE_DRAFT_INDEX_ENTRY_BUTTON = DRAFT_INDEX_ENTRY +
            format(BUTTON_NGBTOOLTIP, "Delete entry");
    public static final String SAVE_DRAFT_INDEX_ENTRY_BUTTON = DRAFT_INDEX_ENTRY +
            format(BUTTON_NGBTOOLTIP, "Move to final");

    public static final String EDIT_FINAL_INDEX_ENTRIES_BUTTON = FINAL_INDEX_ENTRIES +
            format(BUTTON_NGBTOOLTIP, "Move to draft");
    public static final String REMOVE_FINAL_INDEX_ENTRIES_BUTTON = FINAL_INDEX_ENTRIES +
            format(BUTTON_NGBTOOLTIP, "Delete entry");
    public static final String SAVE_EXIT_FINAL_INDEX_ENTRIES_BUTTON = FINAL_INDEX_ENTRIES +
            BUTTON + "[contains(text(),'Exit')]";
    public static final String SAVE_NEXT_FINAL_INDEX_ENTRIES_BUTTON = FINAL_INDEX_ENTRIES +
            BUTTON + "[contains(text(),'Next')]";

    // ---------- MISC ----------

    public static final String DASH = " - ";
    public static final String BLUE = "#5a75e0";
    public static final String LIGHT_GREY = "#f5faff";
    public static final String LIGHT_BLUE = "#d9eeff";
    public static final String BLACK = "#000000";
    public static final String BLACK_COLOR_NAME = "black";
    public static final String WHITE = "#ffffff";
    public static final String ORANGE = "#fa6400";
    public static final String ORANGE_COLOR_NAME = "orange";
    public static final String GREY = "#dedede";
    public static final String SILVER = "#f0f0f0";
    public static final String INDIGO = "#103a59";
    public static final String DARK_BLUE = "#0d77d6";


}
