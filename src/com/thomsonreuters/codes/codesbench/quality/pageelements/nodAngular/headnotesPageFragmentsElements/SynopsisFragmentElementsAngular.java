package com.thomsonreuters.codes.codesbench.quality.pageelements.nodAngular.headnotesPageFragmentsElements;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class SynopsisFragmentElementsAngular {

    public static final String SYNOPSIS_BOX = "//ngb-accordion";
    public static final String SYNOPSIS_BACKGROUND_BUTTON = SYNOPSIS_BOX + "//button[text() = ' Synopsis Background']";
    public static final String SYNOPSIS_HOLDINGS_BUTTON = SYNOPSIS_BOX + "//button[text() = ' Synopsis Holdings']";
    public static final String NOTES_BUTTON = SYNOPSIS_BOX + "//button[text() = ' Notes']";
    public static final String SYNOPSIS_BACKGROUND_CARD = SYNOPSIS_BACKGROUND_BUTTON + "/ancestor::div[@class='card']";
    public static final String SYNOPSIS_HOLDINGS_CARD = SYNOPSIS_HOLDINGS_BUTTON + "/ancestor::div[@class='card']";
    public static final String NOTES_CARD = NOTES_BUTTON + "/ancestor::div[@class='card']";
    public static final String SYNOPSIS_BACKGROUND_CONTENT = SYNOPSIS_BACKGROUND_CARD + "//div[@class='card-body']/span";
    public static final String SYNOPSIS_HOLDINGS_CONTENT = SYNOPSIS_HOLDINGS_CARD + "//div[@class='card-body']/span";
    public static final String NOTES_CONTENT = NOTES_CARD + "//div[@class='card-body']/app-case-note-editor";
    public static final String NOTES_TEXTAREA = NOTES_CARD + "//app-case-note-editor//textarea";
    public static final String NOTES_SAVE_BUTTON = NOTES_CARD + "//app-case-note-editor//button[text()='Save']";

    public static final String EXPANDED_CARD_CLASS = "btn btn-link";
    public static final String COLLAPSED_CARD_CLASS = "btn btn-link collapsed";

    @FindBy(how = How.XPATH, using = SYNOPSIS_BACKGROUND_BUTTON)
    public static WebElement synopsisBackground;

    @FindBy(how = How.XPATH, using = SYNOPSIS_HOLDINGS_BUTTON)
    public static WebElement synopsisHoldings;

    @FindBy(how = How.XPATH, using = NOTES_BUTTON)
    public static WebElement notes;



}
