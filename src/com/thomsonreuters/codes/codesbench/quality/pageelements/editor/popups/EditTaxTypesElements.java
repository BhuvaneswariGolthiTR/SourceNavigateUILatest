package com.thomsonreuters.codes.codesbench.quality.pageelements.editor.popups;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import java.util.List;

public class EditTaxTypesElements {

    public static final String PAGE_TITLE_NEW = "Select new tax types for '%s' element";
    public static final String PAGE_TITLE_EDIT = "Edit tax types for '%s' element";

    @FindBy(how = How.XPATH, using = "//select[contains(@id,'selectedList')]/option")
    public static List<WebElement> selectedTaxTypeAdds;

    @FindBy(how = How.XPATH, using = "//input[contains(@id,'removeAllButton')]")
    public static WebElement moveAllToAvailable;

    @FindBy(how = How.XPATH, using = "//input[contains(@id,'addOneButton')]")
    public static WebElement moveToSelected;

    public static final String SELECTED_TAX_TYPE_ADDS = "//select[contains(@id,'selectedList')]/option";
    public static final String SELECTED_TAX_TYPE_BY_NAME = "//select[contains(@id,'selectedList')]/option[@value='%s']";
    public static final String AVAILABLE_TAX_TYPE_BY_NAME = "//select[contains(@id,'availablelist')]/option[@value='%s']";
    public static final String AVAILABLE_TAX_TYPE_BY_NAME_ID = "availablelist";

}
