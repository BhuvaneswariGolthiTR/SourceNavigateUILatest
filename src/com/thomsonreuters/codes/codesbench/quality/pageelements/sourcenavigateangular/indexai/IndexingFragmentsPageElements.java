package com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.indexai;

import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.indexai.IndexingPageElements.BUTTON;

public class IndexingFragmentsPageElements
{
    public static final String CONTAINS_SELECTED_FRAGMENT = "[contains(@class,'selected-fragment')]";
    public static final String SELECTED_FRAGMENT = BUTTON + CONTAINS_SELECTED_FRAGMENT;
    public static final String SELECTED_FRAGMENT_CONTAINS_TEXT = BUTTON + "[contains(@class,'selected-fragment') and contains(text(),'%s')]";
    public static final String SELECTED_FRAGMENT_TEXT = BUTTON + "[contains(@class,'selected-fragment') and text()=' %s ']";
    public static final String FRAGMENTS_BEFORE_SELECTED = BUTTON + CONTAINS_SELECTED_FRAGMENT + "/preceding-sibling::button";

    public static final String FRAGMENTS_NO_SUGGESTIONS = "//div[@class='fragments-wrap']//div[text()=' No suggestions ']";
    public static final String FRAGMENTS_NO_SUGGESTIONS_BACKGROUND = FRAGMENTS_NO_SUGGESTIONS + "/ancestor::div[contains(@class,'primary')]";
    public static final String FRAGMENTS_NO_ADD_SUGGESTIONS = FRAGMENTS_NO_SUGGESTIONS + "//following::div[text()=' No additional suggestions ']";

    public static final String PRIMARY_FRAGMENT = BUTTON + "[contains(@class,'primary-fragment')]";
    public static final String PRIMARY_FRAGMENT_TEXT = BUTTON + "[contains(@class,'primary-fragment') and text()=' %s ']";
    public static final String SECONDARY_FRAGMENT = BUTTON + "[contains(@class,'secondary-fragment')]";
    public static final String FRAGMENT_TEXT = BUTTON + "[@class='btn-fragment' and text()=' %s ']";
    public static final String ANY_FRAGMENT_TEXT = BUTTON + "[contains(@class,'fragment') and text()=' %s ']";

    public static final String PRIMARY_FRAGMENT_NUMBER = "(//button[contains(@class,'primary-fragment')])[%s]";
    public static final String FRAGMENT_NUMBER = "(//button[@class='btn-fragment'])[%s]";

    public static final String PRECEDING_FRAGMENT_PATTERN = "/preceding-sibling::button[position()=%s]";

    public static final String PRIMARY = "primary";
    public static final String SECONDARY = "secondary";

    // ---------- Fragments ----------

    public static final String ABATEMENT = "abatement";
    public static final String ABATEMENT_OF_ACTIONS_AND_PROCEEDINGS = "abatement of actions and proceedings";
    public static final String HOLIDAYS = "holidays";
    public static final String RENT = "rent";
    public static final String ENTRY_SPELLCHECK_DEDUSHKA = "dedushka";
    public static final String ENTRY_SPELLCHECK_BANANABOOM = "bananaboom";

    public static final String ABSENTEE_VOTING = "absentee voting";
    public static final String MILITARY_FORCES = "military forces";
    public static final String BALLOTS = "ballots";
    public static final String ELECTIONS = "elections";
    public static final String GENERALLY = "generally";

}
