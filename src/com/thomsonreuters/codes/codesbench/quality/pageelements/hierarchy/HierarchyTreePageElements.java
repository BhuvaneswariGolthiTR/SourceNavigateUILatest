package com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class HierarchyTreePageElements
{
    public static final String NAV_TREE_NODE_WITH_VALUE_GIVEN = "//div[@id='treeDiv1']//div[@class='ygtvitem']//td[contains(text(),'%s')]";
    public static final String SELECTED_TREE_NODE_WITH_VALUE_GIVEN = "//div[@id='treeDiv1']//div[@class='ygtvitem']//table[contains(@class,'highlight1')]//td[contains(text(),'%s')]";
    public static final String CALENDAR_MODAL_CELL = "//div[contains(@class,'calcontainer')]//td[contains(@class,'calcell')]/a[text()='%s']";
    public static final String DELETED_NODE_WITH_GIVEN_VALUE_XPATH = "//div[@id='treeDiv1']//div[@class='ygtvitem']//td[contains(text(),'%s') and contains(@class,'deleted')]";
    public static final String EXPAND_BUTTON_NEXT_TO_GIVEN_VALUE = "//td[contains(text(), '%s')]/..//td//a[@class='ygtvspacer']";
    public static final String COLLAPSED_SELECTED_NODE_TEXT = "//table[contains(@class,'highlight1')]//td[last()]";

    @FindBy(how = How.ID, using = "treeFieldsForm:hideDeleted")
    public static WebElement hideDeletedCheckbox;

    @FindBy(how = How.XPATH, using = "//div[@id='treeDiv']//table[contains(@class,'highlight1')]")
    public static WebElement selectedNode;

    @FindBy(how = How.XPATH, using = "//table[contains(@class,'highlight0')]")
    public static WebElement highlightedNodeAboveSelectedNode;

    @FindBy(how = How.XPATH, using = "//table[contains(@class,'highlight1')]//td[contains(text(),'%s')]")
    public static WebElement selectedNodeWithNameValueGiven;

    @FindBy(how = How.XPATH, using = "//td[contains(@class,'ygtvcontent ygtvfocus')]")
    public static WebElement selectedSectionNameValue;

    @FindBy(how = How.XPATH, using = "//input[contains(@id,'treeFieldsForm:navTreeDate')]")
    public static WebElement date;

    @FindBy(how = How.XPATH, using = "//div[@id='treeDiv1']//div[@class='ygtvitem']//td[contains(text(),'%s')]" + "//preceding-sibling::td/a")
    public static WebElement nodeWithValuePluButton;

    @FindBy(how = How.ID, using = "showCalendar")
    public static WebElement calendarButton;

    @FindBy(how = How.XPATH, using = "//div[@id='container']/div[contains(text(),'Choose a Date')]")
    public static WebElement calendarModal;

    @FindBy(how = How.XPATH, using = "//td[contains(@class,'ygtvcontent ygtvfocus')]")
    public static WebElement navTreeSelectedSectionNameValue;

    @FindBy(how = How.XPATH, using = "//table[contains(@class,'highlight1')]//td[contains(@class, 'ygtvln')]")
    public static WebElement navTreeSelectedNodeWithoutChildren;

    public static final String NAV_TREE_SELECTED_NODE_WITHOUT_CHILDREN = "//table[contains(@class,'highlight1')]//td[contains(@class, 'ygtvln')]";
}
