package com.thomsonreuters.codes.codesbench.quality.pages.nodAngular.headnotesPageFragments;

import com.thomsonreuters.codes.codesbench.quality.pageelements.nodAngular.headnotesPageFragmentsElements.HeadnotesContextMenuElementsAngular;
import com.thomsonreuters.codes.codesbench.quality.pageelements.nodAngular.headnotesPageFragmentsElements.HierarchyTreeFragmentElementsAngular;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class HierarchyTreeFragmentAngular extends BasePage
{
    private final WebDriver driver;

    @Autowired
    public HierarchyTreeFragmentAngular(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, HierarchyTreeFragmentElementsAngular.class);
    }

    public void quickFind(String textToFind)
    {
        sendKeysToElement(HierarchyTreeFragmentElementsAngular.HIERARCHY_FIND_INPUT, textToFind);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.FIVE_SECONDS);//added to fix timing issue
        clickQuickFind();
    }

    public void hierarchyFind(String textToFind)
    {
        sendKeysToElement(HierarchyTreeFragmentElementsAngular.HIERARCHY_FIND_HIERARCHY_FIND_INPUT, textToFind);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.FIVE_SECONDS);//added to fix timing issue
        clickHierarchyFind();
    }

    public void putTextInQuickFindField(String textToFind)
    {
        sendKeysToElement(HierarchyTreeFragmentElementsAngular.HIERARCHY_FIND_INPUT, textToFind);
    }

    public String getTextInQuickFindField()
    {
        return getElementsText(HierarchyTreeFragmentElementsAngular.HIERARCHY_FIND_INPUT);
    }

    public void clickClearQuickFind()
    {
        //Old code
        //sendEnterToElement(HierarchyTreeFragmentElementsAngular.HIERARCHY_FIND_CLEAR);
        //New code
        click(HierarchyTreeFragmentElementsAngular.HIERARCHY_FIND_CLEAR);
    }

    public void clearQuickFindInput()
    {
        clear(getElement(HierarchyTreeFragmentElementsAngular.HIERARCHY_FIND_INPUT));
    }

    public void clearHierarchyFindInput()
    {
        clear(getElement(HierarchyTreeFragmentElementsAngular.HIERARCHY_FIND_HIERARCHY_FIND_INPUT));
    }

    public void clickQuickFind()
    {
        click(HierarchyTreeFragmentElementsAngular.quickFindButton);
        waitForPageLoaded();
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
    }

    public void clickHierarchyFind()
    {
        click(HierarchyTreeFragmentElementsAngular.hierarchyFindButton);
        waitForPageLoaded();
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
    }

    public void clickKeywordFind()
    {
        click(HierarchyTreeFragmentElementsAngular.keywordFindButton);
    }

    public void clickTemplateFind()
    {
        click(HierarchyTreeFragmentElementsAngular.templateFindButton);
    }

    public void openContextMenuForNodeWithText(String nodeText)
    {
        click(String.format(HierarchyTreeFragmentElementsAngular.SPAN_WITH_TEXT, nodeText));
        rightClick(String.format(HierarchyTreeFragmentElementsAngular.SPAN_WITH_TEXT, nodeText));
        waitForVisibleElement(HeadnotesContextMenuElementsAngular.CONTEXT_MENU);
    }

    public void waitForElementScrolledTo(String nodeText)
    {
        waitForVisibleElement(String.format(HierarchyTreeFragmentElementsAngular.SPAN_WITH_TEXT, nodeText));
    }

    public void scrollToSeeParentLevelNode(String nodeText)
    {
        waitForVisibleElement(String.format(HierarchyTreeFragmentElementsAngular.SPAN_WITH_TEXT, nodeText));
        scrollTo(String.format(HierarchyTreeFragmentElementsAngular.PARENT_LEVEL_NODE_IN_TREE, nodeText));
    }

    public void scrollToNodeByText(String nodeText)
    {
        scrollToView(String.format(HierarchyTreeFragmentElementsAngular.SPAN_WITH_TEXT, nodeText));
    }

    public boolean isHierarchyTreeDisplayed()
    {
        waitForElement(HierarchyTreeFragmentElementsAngular.NAVIGATION_TREE);
        waitForVisibleElement(HierarchyTreeFragmentElementsAngular.NAVIGATION_TREE);
        return isElementDisplayed(HierarchyTreeFragmentElementsAngular.NAVIGATION_TREE);
    }

    public int getNumberOfBluelinesInBluelineAnalysisNode(String nodeText)
    {
        String bluelineXpath = String.format(HierarchyTreeFragmentElementsAngular.CHILD_BLUELINE_NODES_IN_TREE, nodeText);
        return Integer.parseInt(headnotesPageAngular().getElementsAttribute(bluelineXpath, "aria-setsize"));
    }

    public List<String> getAllBluelinesNamesInBluelineAnalysisNode(String nodeText)
    {
        String bluelineSpanXpath = String.format(HierarchyTreeFragmentElementsAngular.CHILD_BLUELINE_NODES_IN_TREE, nodeText) + "//span";
        List<String> bluelines = new ArrayList<>();
        WebElement blueline = getElement(bluelineSpanXpath);
        int numberOfBluelines = getNumberOfBluelinesInBluelineAnalysisNode(nodeText);
        while (numberOfBluelines > 0)
        {
            bluelines.add(blueline.getText());
            click(blueline);
            blueline = headnotesPageAngular().getElement(blueline, HierarchyTreeFragmentElementsAngular.NEIGHBOUR_BLUELINES_SPAN_IN_TREE);
            numberOfBluelines -= 1;
        }
        return bluelines;
    }

    public String getLastBluelineNameInBLAnalysisNode()
    {
        String nodeText = "BL ANALYSI ";
        String lastBluelinePosition = String.format("[%s]", getNumberOfBluelinesInBluelineAnalysisNode(nodeText));
        String lastBluelineXpath = String.format(HierarchyTreeFragmentElementsAngular.CHILD_BLUELINE_NODES_IN_TREE,
                nodeText) + lastBluelinePosition + "//span" ;
        return getElementsText(lastBluelineXpath);
    }

    public int getLastBluelineNumberInBLAnalysisNode()
    {
        String nodeText = "BL ANALYSI ";
        String lastBluelinePosition = String.format("[%s]", getNumberOfBluelinesInBluelineAnalysisNode(nodeText));
        String lastBluelineXpath = String.format(HierarchyTreeFragmentElementsAngular.CHILD_BLUELINE_NODES_IN_TREE,
                nodeText) + lastBluelinePosition + "//span" ;
        String lastBluelineNumberAsString = getElementsText(lastBluelineXpath).split(" ")[1];
        return Integer.parseInt(lastBluelineNumberAsString);
    }

    public HashMap<String, String> convertFullBluelinesNamesToNameToNumberHashMap(List<String> fullBluelineNamesInTree)
    {
        List<String> bluelineNames = removeBluelineNumbersFromNames(fullBluelineNamesInTree);
        List<String> bluelineNumbers = fullBluelineNamesInTree.stream().map(element -> element.split(" ")[1]).collect(Collectors.toList());
        HashMap<String, String> blulinesAndNumbers = new HashMap<>();
        for (int index = 0; index < bluelineNames.size(); index++)
        {
            blulinesAndNumbers.put(bluelineNames.get(index), bluelineNumbers.get(index));
        }
        return blulinesAndNumbers;
    }

    public List<String> removeBluelineNumbersFromNames(List<String> fullBluelineNamesInTree)
    {
        return fullBluelineNamesInTree.stream().map(element -> element.replaceAll("BL [0-9] ", "")).collect(Collectors.toList());
    }

    public String getNodeClassByName(String nodeName)
    {
        return getElementsAttribute(String.format(HierarchyTreeFragmentElementsAngular.TREE_NODE_BY_TEXT,
                nodeName), "class");
    }

    public void expandNodeWithText(String text)
    {
        click(String.format(HierarchyTreeFragmentElementsAngular.TREE_NODE_BY_TEXT, text) + HierarchyTreeFragmentElementsAngular.EXPAND_BUTTON);
        waitForPageLoaded();
    }

    public boolean isNodeTreeDisplayed(String text)
    {
        DateAndTimeUtils.takeNap(DateAndTimeUtils.ONE_SECOND);
        return isElementDisplayed(String.format(HierarchyTreeFragmentElementsAngular.SPAN_WITH_TEXT, text));
    }

    public void collapseNodeWithText(String text)
    {
        click(String.format(HierarchyTreeFragmentElementsAngular.TREE_NODE_BY_TEXT, text) + HierarchyTreeFragmentElementsAngular.COLLAPSE_BUTTON);
    }

    public boolean isNodeCollapsed(String nodeText)
    {
        WebElement nodeButton = getElement(String.format(HierarchyTreeFragmentElementsAngular.TREE_NODE_BY_TEXT, nodeText) +
                HierarchyTreeFragmentElementsAngular.BUTTON);
        String buttonLabel = nodeButton.getAttribute(HierarchyTreeFragmentElementsAngular.BUTTON_LABEL);
        return buttonLabel.equals(HierarchyTreeFragmentElementsAngular.EXPAND);
    }

    public boolean isNodeExpanded(String nodeText)
    {
        WebElement nodeButton = getElement(String.format(HierarchyTreeFragmentElementsAngular.TREE_NODE_BY_TEXT, nodeText) +
                HierarchyTreeFragmentElementsAngular.BUTTON);
        String buttonLabel = nodeButton.getAttribute(HierarchyTreeFragmentElementsAngular.BUTTON_LABEL);
        return buttonLabel.equals(HierarchyTreeFragmentElementsAngular.COLLAPSE);
    }

    public boolean isNodeRepealed(String nodeText)
    {
        WebElement nodesDivWithClass = getElement(String.format(HierarchyTreeFragmentElementsAngular.SPAN_WITH_TEXT, nodeText) +
                HierarchyTreeFragmentElementsAngular.PARENT_LEVEL_NODE);
        return nodesDivWithClass.getAttribute("class").contains("repealed");
    }

    public String getNodeBackgroundColor(String nodeText)
    {
        WebElement nodesDivWithColor = getElement(String.format(HierarchyTreeFragmentElementsAngular.SPAN_WITH_TEXT, nodeText) +
                HierarchyTreeFragmentElementsAngular.PARENT_LEVEL_NODE);
        return nodesDivWithColor.getCssValue("background-color");
    }

    public boolean isNodeRedishPink(String nodeText)
    {
        String redishPink = "rgba(255, 182, 193, 1)";
        return getNodeBackgroundColor(nodeText).equals(redishPink);
    }

    public void clickNode(String nodeText)
    {
        click(String.format(HierarchyTreeFragmentElementsAngular.TREE_NODE_BY_TEXT, nodeText));
    }

    public boolean isNodeSelected(String nodeText)
    {
        WebElement element = getElement(String.format(HierarchyTreeFragmentElementsAngular.TREE_NODE_BY_TEXT, nodeText));
        return element.getAttribute("class").contains("selected");
    }

    public boolean isQuickFindButtonPresent()
    {
        return doesElementExist(HierarchyTreeFragmentElementsAngular.QUICK_FIND_BUTTON);
    }

    public boolean isKeywordFindButtonPresent()
    {
        return doesElementExist(HierarchyTreeFragmentElementsAngular.KEYWORD_FIND_BUTTON);
    }

    public boolean isTemplateFindButtonPresent()
    {
        return doesElementExist(HierarchyTreeFragmentElementsAngular.TEMPLATE_FIND_BUTTON);
    }

    public void canadaExpandToBlAnalysisNode()
    {
        hierarchyTreeFragmentAngular().expandNodeWithText("Grp. English / Anglais ");
        hierarchyTreeFragmentAngular().expandNodeWithText("Grp. Act / Loi ");
        hierarchyTreeFragmentAngular().expandNodeWithText("Grp. J-N ");
        hierarchyTreeFragmentAngular().expandNodeWithText("ACT Jewish Heritage Month Act, 2012 ");
        hierarchyTreeFragmentAngular().expandNodeWithText("� 1. Jewish Heritage Month ");
    }

    public String getHighlightedNodeText()
    {
        return getElementsText(HierarchyTreeFragmentElementsAngular.HIGHLIGHTED_BLUELINES_IN_TREE);
    }
}

