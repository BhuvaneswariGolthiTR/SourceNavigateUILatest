package com.thomsonreuters.codes.codesbench.quality.pageelements.nodAngular.headnotesPageFragmentsElements;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class HierarchyTreeFragmentElementsAngular {

    //HIERARCHY_TREE
    public static final String NAVIGATION_TREE = "//cdk-virtual-scroll-viewport";
    public static final String SPAN_WITH_TEXT = NAVIGATION_TREE + "//span[contains(text(),'%s')]";
    public static final String SPAN_WITH_TEXT_CONTAINS = NAVIGATION_TREE + "//span[contains(text(),'%s')]";
    public static final String TREE_ITEM = "div[@role='treeitem']";
    public static final String ANCESTOR = "/ancestor::%s";
    public static final String PRECEDING_SIBLING = "/preceding-sibling::%s";
    public static final String FOLLOWING_SIBLING = "/following-sibling::%s";
    public static final String EXPAND_BUTTON = "/button[@aria-label='expand']";
    public static final String COLLAPSE_BUTTON = "/button[@aria-label='collapse']";
    public static final String TREE_NODE_BY_TEXT = NAVIGATION_TREE + "//span[text()='%s']/ancestor::div[@role='treeitem']";
    public static final String BUTTON = "/button";
    public static final String BUTTON_LABEL = "aria-label";
    public static final String EXPAND = "expand";
    public static final String COLLAPSE = "collapse";
    public static final String PARENT_LEVEL_NODE = "/parent::div";


    public static final String PARENT_LEVEL_NODE_IN_TREE = NAVIGATION_TREE + "//span[text()='%s']/ancestor::div[@role='treeitem']/preceding-sibling::div[@role='treeitem']";
    public static final String CHILD_BLUELINE_NODES_IN_TREE = NAVIGATION_TREE + "//span[text()='%s']/ancestor::div[@role='treeitem']/following-sibling::div[@role='treeitem']";
    public static final String NEIGHBOUR_BLUELINES_SPAN_IN_TREE = "./ancestor::div[@role='treeitem']/following-sibling::div[@role='treeitem']//span";
    public static final String HIGHLIGHTED_BLUELINES_IN_TREE = "//div[@class='bui-tree-item selected']";

    //FIND
    public static final String HIERARCHY_FIND = "//app-hierarchy-find";
    public static final String HIERARCHY_FIND_HIERARCHY_FIND = HIERARCHY_FIND + "//div[contains(@class, 'hierarchy-find-box')]";
    public static final String HIERARCHY_FIND_INPUT = HIERARCHY_FIND + "//input";
    public static final String HIERARCHY_FIND_HIERARCHY_FIND_INPUT = HIERARCHY_FIND_HIERARCHY_FIND + "//input";
    public static final String QUICK_FIND_BUTTON = HIERARCHY_FIND + "//button[contains(text(),'Quick Find')]";
    public static final String HIERARCHY_FIND_BUTTON = HIERARCHY_FIND_HIERARCHY_FIND + "//button[contains(text(),'Hierarchy Find')]";
    public static final String KEYWORD_FIND_BUTTON = HIERARCHY_FIND + "//button[contains(text(),'Keyword Find')]";
    public static final String TEMPLATE_FIND_BUTTON = HIERARCHY_FIND + "//button[contains(text(),'Template Find')]";
    public static final String HIERARCHY_FIND_CLEAR = "//em[@id='quick-find-clear']";

    @FindBy(how = How.XPATH, using = QUICK_FIND_BUTTON)
    public static WebElement quickFindButton;

    @FindBy(how = How.XPATH, using = HIERARCHY_FIND_BUTTON)
    public static WebElement hierarchyFindButton;

    @FindBy(how = How.XPATH, using = KEYWORD_FIND_BUTTON)
    public static WebElement keywordFindButton;

    @FindBy(how = How.XPATH, using = TEMPLATE_FIND_BUTTON)
    public static WebElement templateFindButton;
}
