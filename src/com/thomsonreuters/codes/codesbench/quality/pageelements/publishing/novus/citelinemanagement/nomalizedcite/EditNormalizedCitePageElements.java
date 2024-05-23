package com.thomsonreuters.codes.codesbench.quality.pageelements.publishing.novus.citelinemanagement.nomalizedcite;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class EditNormalizedCitePageElements extends AddOrEditNormalizedCitePageElements
{
    public static final String EDIT_WINDOW_TITLE_XPATH = "//mat-dialog-container//h1[text()='Normalized Cite Edit']";

    @FindBy(how = How.ID, using = "updateNormCiteButton")
    public static WebElement updateValues;
}
