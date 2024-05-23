package com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.hierarchy.navigate;

import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.HierarchyContextMenuElements;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

//This class extends HierarchyContextMenuElements because it uses the same elements as other context menus in the Hierarchy Edit page
public class ParentageGraphContextMenuElements extends HierarchyContextMenuElements
{
    public static final String REFRESH_SELECTION = "//div[contains(@id,'ContextMenu') or contains(@id,'contextmenu')]//a[text()='Refresh Selection']";
    public static final String QUERIES = "//div[contains(@id,'ContextMenu') or contains(@id,'contextmenu')]//a[text()='Queries']";
    public static final String VIEW_ONLINE_PRODUCT_NODE_QUERIES_XPATH = "//div[contains(@id,'ContextMenu') or contains(@id,'contextmenu')]//a[text()='View Online Product Node Queries']";
    public static final String CREATE_NEW_VOLUME_XPATH = "//div[contains(@id,'ParentageGraphContextMenu')]//a[text()='Create New Volume']";
    //This element won't exist in the context menu, but needs to be checked against
    public static final String XML_EXTRACT_STATE_FEED = "//div[contains(@id,'ContextMenu') or contains(@id,'contextmenu')]//a[text()='XML Extract - State Feed']";

    @FindBy(how = How.XPATH, using = "//div[contains(@id,'ContextMenu') or contains(@id,'contextmenu')]//a[text()='Refresh Selection']")
    public static WebElement refreshSelection;
}
