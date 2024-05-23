package com.thomsonreuters.codes.codesbench.quality.pages.editor;

import com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.editor.EditorTreeContextMenuElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTreePageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import com.thomsonreuters.codes.codesbench.quality.utilities.robot.RobotUtils;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

@Component
public class EditorTreePage extends BasePage 
{
	private WebDriver driver;
	
	@Autowired
	public EditorTreePage(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
	}
	
	@PostConstruct
	public void init()
	{
		PageFactory.initElements(driver, EditorTreePageElements.class);
	}

	public int countTreeElements(EditorTreePageElements.treeElements treeElement)
	{
		return getElements(treeElement.getXpath()).size();
	}

	public void navigateEditorTree(String... nodes)
	{
		for (String node : nodes) {
			editorTreePage().click(editorPage().returnExistingElement(node));
		}
	}

	public void expandEditorsTreeAndClickNode(final String ... nodesToExpand)
	{
		if (nodesToExpand == null || nodesToExpand.length < 1)
		{
			throw new RuntimeException("Tree Nodes array is incorrect");
		}

		String last = nodesToExpand[nodesToExpand.length - 1];

		Arrays.stream(nodesToExpand)
				.filter( item -> !item.equals(last))
				.forEach(item -> click(String.format(EditorTreePageElements.TREE_NODE_WITH_TEXT_LINK, item) + EditorTreePageElements.PRECEDING_EXPAND_NODE_LINK));

		click(String.format(EditorTreePageElements.TREE_NODE_WITH_TEXT_LINK, last));
	}

	public void rightClickTreeRootForTargetNodes()
	{
		rightClick(EditorTreePageElements.TREE_ROOT);
		editorTextPage().breakOutOfFrame();
	}

	public void previewTreeNode(String nodeName)
	{
		rightClick(String.format(EditorTreePageElements.TREE_NODE_WITH_TEXT_LINK, nodeName));
		click(EditorTreeContextMenuElements.PREVIEW);
		switchToWindow("Document Preview");
	}

	public int countSubsectionNumberInTree()
	{
		return countElements("//div[@id='navWindow2']//div[contains(@treeNodeName,'subsection')]");
	}

	public int countAnnotationsNumberInTree()
	{
		return countElements("//div[@id='navWindow2']//div[contains(@treeNodeName,'annotation')]");
	}

	public int countBodyNumberInTree()
	{
		return countElements("//div[@id='navWindow2']//div[@treeNodeName ='body']");
	}

	public String getLastTreeNodeName()
	{
		return getElementsText("//div[@id='navWindow2']//div[@class ='dTreeNode'][last()]");
	}

	public void selectCpSnlAndBodyOfNode()
	{
		try
		{
			RobotUtils.getRobot().keyPress(KeyEvent.VK_CONTROL);
			click(EditorTreePageElements.cornerpieceCP);
			click(EditorTreePageElements.codesHeadSNL);
			click(EditorTreePageElements.body);
			RobotUtils.getRobot().keyRelease(KeyEvent.VK_CONTROL);
		}
		finally
		{
			RobotUtils.getRobot().keyRelease(KeyEvent.VK_CONTROL);
		}
	}

	public void rightClickBodyTreeNode()
	{
		rightClick(EditorTreePageElements.body);
	}

	public void toggleLockEditorTree()
	{
		String unlockedXpath = String.format(EditorTreePageElements.TREE_NODE_CONTAINS_TEXT, "unlocked");
		if (doesElementExist(unlockedXpath))
		{
			rightClick(unlockedXpath);
		}
		else
		{
			rightClick(String.format(EditorTreePageElements.TREE_NODE_CONTAINS_TEXT, "locked"));
		}
		click(EditorTreeContextMenuElements.TOGGLE_LOCK);
	}

	public String getEditorTreeBackgroundColor()
	{
		return getElement(EditorTreePageElements.NAV_WINDOW).getCssValue("background-color");
	}

	public void highlightFollowingSiblings(int countOfSiblingsToHighlight)
	{
		for (int i = 0; i < countOfSiblingsToHighlight; i++)
		{
			sendKeys(Keys.F7);
		}
	}

	public void collapseEditorsTreeByNode(String nodeToCollapse)
	{
		if (nodeToCollapse == null)
		{
			throw new IllegalArgumentException("Tree Node is empty");
		}
		waitForPageLoaded();
		click(String.format(EditorTreePageElements.COLLAPSE_BUTTON_WITH_TEXT_LINK, nodeToCollapse));
	}

	public List<String> getTreeNodesValues()
	{
		List<WebElement> treeNodesList = getElements(EditorTreePageElements.TREE_NODE_LINK);
		List<String> treeNodeValuesList = new ArrayList<>();
		treeNodesList.forEach(treeNode -> treeNodeValuesList.add(treeNode.getText()));
		return treeNodeValuesList;
	}

	public boolean isNodeExpanded(String node)
	{
		return doesElementExist(String.format(EditorTreePageElements.TREE_NODE_WITH_TEXT_LINK, node)
				+ EditorTreePageElements.PRECEDING_COLLAPSE_NODE_LINK);
	}

	public String getTreeNodeBackgroundColor(String node)
	{
		return getBackgroundColor(String.format(EditorTreePageElements.TREE_NODE_WITH_TEXT_LINK, node));
	}

	public boolean isTreeNodeSelected(String node)
	{
		return "nodeSel".equals(getElement((String.format(EditorTreePageElements.TREE_NODE_WITH_TEXT_LINK, node)))
				.getAttribute("class"));
	}

	public boolean isWholeTreeCollapsed()
	{
		return getTreeNodesValues().stream().noneMatch(node -> doesElementExist(String.format(
				EditorTreePageElements.TREE_NODE_WITH_TEXT_LINK, node)
				+ EditorTreePageElements.PRECEDING_COLLAPSE_NODE_LINK));
	}

	public void selectNodesViaAction(int hotkeyCode, String... nodes)
	{
		if (nodes == null || nodes.length < 1)
		{
			throw new IllegalArgumentException("Tree Nodes array is empty");
		}
		try
		{
			RobotUtils.getRobot().keyPress(hotkeyCode);
			Arrays.stream(nodes).forEach(node -> click((String.format(EditorTreePageElements.TREE_NODE_WITH_TEXT_LINK, node))));
			RobotUtils.getRobot().keyRelease(hotkeyCode);
		}
		finally
		{
			RobotUtils.getRobot().keyRelease(hotkeyCode);
		}
	}
}
