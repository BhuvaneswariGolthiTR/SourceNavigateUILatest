package com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.source.navigate;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class SectionGroupContextMenuElements {

    public static final String CONTEXT_MENU = "//div[@id='contextMenu']/div/ul/li";
    public static final String CONTEXT_MENU_EDIT = CONTEXT_MENU + "/div[@id='edit']/div/ul/li";
    public static String SECTION_GROUP = CONTEXT_MENU_EDIT + "//a[text()='Section Group']";
    public static final String GROUP_CONTEXT_MENU = "//div[@id='groupContextMenu']";

    public static final String SECTION_GROUPING_MOVE_DOWN = GROUP_CONTEXT_MENU + "//a[text()='Move Down']";
    public static final String SECTION_GROUPING_MOVE_UP = GROUP_CONTEXT_MENU + "//a[text()='Move Up']";
    public static final String SECTION_GROUPING_REMOVE_GROUP = "//div[@id='groupContextMenu' and not(contains(@class,'hidden'))]//a[text()='Remove Group']";
    public static final String SECTION_GROUPING_RENAME_GROUP = GROUP_CONTEXT_MENU + "//a[text()='Rename Group']";
    public static final String SECTION_GROUPING_MOVE_TO_GROUP = "//a[text()='Move to Group']";
    public static final String SECTION_GROUPING_MOVE_TO_GROUP_X = "//div[@id='groupSubmenu']//a[text()='%s']";

    @FindBy(how = How.XPATH, using = "//div[@id='contextMenu']" + "//a[text()='Create']")
    public static WebElement create;

    @FindBy(how = How.XPATH, using = "//a[text()='Preparation Document']")
    public static WebElement createPreparationDocument;
}
