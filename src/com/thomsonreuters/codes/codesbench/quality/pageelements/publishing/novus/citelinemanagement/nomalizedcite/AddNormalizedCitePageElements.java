package com.thomsonreuters.codes.codesbench.quality.pageelements.publishing.novus.citelinemanagement.nomalizedcite;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class AddNormalizedCitePageElements extends AddOrEditNormalizedCitePageElements
{
    public static final String NORMALIZED_CITE_EDIT = "//div[@class='cdk-overlay-pane']//h1[contains(text(),'Normalized Cite Edit')]";
    public static final String NORMALIZED_CITE_ADD = "//div[@class='cdk-overlay-pane']//h1[contains(text(),'Normalized Cite Add')]";

    public static final String ADD_WINDOW_TITLE_XPATH = "//mat-dialog-container//h1[text()='Normalized Cite Add']";

    @FindBy(how = How.ID, using = "createNormCiteButton")
    public static WebElement create;

    @FindBy(how = How.ID, using = "createAnotherNormCiteButton")
    public static WebElement createAnother;
}
