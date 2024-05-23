package com.thomsonreuters.codes.codesbench.quality.pageelements.editor.popups;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class TableEditorPageElements
{
    public static final String PAGE_TITLE = "Table Editor";
    public static final String FIRST_INPUT_FIELD = "//tbody[1]/tr/td[1]";

    public static final String SECOND_INPUT_FIELD= "//tbody[1]/tr/td[2]";
    public static final String INSERT_SPECIAL_CHARACTER_BUTTON = "//button[text()='Insert Special Character']";

    @FindBy(how = How.XPATH, using = "//button[text()='Save']")
    public static WebElement saveButton;

    @FindBy(how = How.XPATH, using = "//button[text()='Cancel']")
    public static WebElement cancelButton;

    @FindBy(how = How.XPATH, using = FIRST_INPUT_FIELD)
    public static WebElement firstInputField;

    @FindBy(how = How.XPATH, using = SECOND_INPUT_FIELD)
    public static WebElement secondInputField;
    @FindBy(how = How.XPATH, using = "//button[text()='Markup ']")
    public static WebElement markupButton;

    public enum MarkupDropdownOptions
    {
        OTHER_LINKS(7),
        SOURCE_LINKS(8);

        private final int index;

        MarkupDropdownOptions(int index)
        {
            this.index = index;
        }

        public int getIndex()
        {
            return index;
        }
    }
}
