package com.thomsonreuters.codes.codesbench.quality.pageelements.publishing.novus.citelinemanagement.nomalizedcite;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class AddOrEditNormalizedCitePageElements extends NormalizedCiteReferencesPageElements
{
    public static final String ENTRY_NOT_VALID_ERROR = " Entity entered is not valid: ";
    public static final String CITATION_PREFIX_IS_ERROR = "Citation Prefix is";
    public static final String CONDENSED_PREFIX_IS_ERROR = "Condensed Prefix is";
    public static final String DUPLICATE_CITATION_PREFIX_ERROR = "Citation Prefix already exists for this content set ";
    public static final String CITATION_PREFIX_CHARACTER_ERROR = "Citation Prefix must start with either a letter or a number (spaces and special characters are not allowed";
    public static final String CONDENSED_PREFIX_CHARACTER_ERROR = "Condensed Prefix must start with either a letter or a number (spaces and special characters are not allowed";

    private static final String GENERAL_CONTENT_XPATH = "//div[@class='mat-dialog-content centerContent']";
    private static final String GENERAL_MAT_ERROR = "/following-sibling::mat-form-field//mat-error[contains(text(),'%s')]";
    public static final String CITATION_PREFIX_ERROR_XPATH = GENERAL_CONTENT_XPATH + "//label[text()='Citation Prefix:']" + GENERAL_MAT_ERROR;
    public static final String CONDENSED_PREFIX_ERROR_XPATH = GENERAL_CONTENT_XPATH + "//label[text()='Condensed Prefix:']" + GENERAL_MAT_ERROR;
    public static final String COMMENTS_LENGTH_ERROR = GENERAL_CONTENT_XPATH + "//mat-error[contains(text(),'Comments max length is 100')]";

    @FindBy(how = How.ID, using = "inputCitationPrefix")
    public static WebElement citationPrefixInput;

    @FindBy(how = How.ID, using = "inputCondensedPrefix")
    public static WebElement condensedPrefixInput;

    @FindBy(how = How.ID, using = "inputComments")
    public static WebElement commentsInput;
}
