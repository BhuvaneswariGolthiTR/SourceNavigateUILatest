package com.thomsonreuters.codes.codesbench.quality.tests.hierarchy;

import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.ParentageGraphPageElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.hierarchy.HierarchyDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.CommonDataMocking;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.TargetDataMockingNew;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.datapod.HierarchyDatapodConfiguration;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.datapod.HierarchyDatapodObject;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class FindFunctionsTests extends TestService
{

    List<HierarchyDatapodObject> datapodObjects = new ArrayList<>();

    /**
     * STORY/BUG - HALCYONST-737 <br>
     * SUMMARY - This test verifies that after searching for a node by the 'Find Cite' tab,
     * that the given node appears in each pane of the page <br>
     * USER - LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void findCiteSectionLegalTest()
    {
        datapodObjects.add(TargetDataMockingNew.Iowa.Small.insert());
        String nodeUUID = datapodObjects.get(0).getSections().get(0).getNodeUUID();
        Connection connection = CommonDataMocking.connectToDatabase(environmentTag);
        String value = HierarchyDatabaseUtils.getNodeValueByNodeUUID(nodeUUID, ContentSets.IOWA_DEVELOPMENT.getCode(), connection);
        BaseDatabaseUtils.disconnect(connection);
        String keyword = "=";

        homePage().goToHomePage();
        loginPage().logIn();
        hierarchyMenu().goToNavigate();

        //Search value through 'Find Cite' tab
        hierarchySearchPage().clickFindCiteTab();
        findCitePage().setKeyword(keyword);
        findCitePage().setValue(value);
        findCitePage().clickFind();

        hierarchyNavigatePage().switchToHierarchyEditPage();
        hierarchyNavigatePage().waitForHierarchyEditPageToLoad(value);

        //Check that the node appears in each pane of the page
        boolean metadataNodeAppears = siblingMetadataPage().isNodeDisplayedWithValue(value);
        boolean navTreeNodeAppears = hierarchyTreePage().isSelectedTreeNodeDisplayedByValue(value);
        boolean parentageGraphNodeAppears = parentageGraphPage().isElementDisplayed(ParentageGraphPageElements.PARENTAGE_GRAPH_IMAGE);
        boolean dispDerivNodeAppears = dispositionDerivationPage().isNodeValueDisplayed(value);

        Assertions.assertAll
        (
                () -> Assertions.assertTrue(metadataNodeAppears,"The node with the given value doesn't appear in the sibling metadata pane when it should"),
                () -> Assertions.assertTrue(navTreeNodeAppears,"The node with the given value doesn't appear in the navigation tree pane when it should"),
                () -> Assertions.assertTrue(parentageGraphNodeAppears,"The node with the given value doesn't appear in the parentage graph pane when it should"),
                () -> Assertions.assertTrue(dispDerivNodeAppears,"The node with the given value doesn't appear in the disposition/derivation pane when it should")
        );
    }

    /**
     * STORY/BUG - HALCYONST-738 <br>
     * SUMMARY - This test verifies that after searching for a node, with multiple results, by the 'Find Cite' tab,
     * that the given node appears in each pane of the page <br>
     * USER - LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void findCiteMultipleResultsLegalTest()
    {
        // We need two titles with the same value
        datapodObjects.add(TargetDataMockingNew.Iowa.Small.insert());
        datapodObjects.add(TargetDataMockingNew.Iowa.Small.insert());
        String value = "XCIX";
        String keyword = "TITLE";

        homePage().goToHomePage();
        loginPage().logIn();
        hierarchyMenu().goToNavigate();

        //Search value through 'Find Cite' tab
        hierarchySearchPage().clickFindCiteTab();
        findCitePage().setKeyword(keyword);
        findCitePage().setValue(value);
        findCitePage().clickFind();

        //Choose first node from grid
        hierarchyFindPage().clickFirstNodeUuid();
        hierarchyNavigatePage().waitForHierarchyEditPageToLoad(value);

        //Check that the node appears in each pane of the page
        boolean metadataNodeAppears = siblingMetadataPage().isNodeDisplayedWithValue(value);
        boolean navTreeNodeAppears = hierarchyTreePage().isSelectedTreeNodeDisplayedByValue(value);
        boolean parentageGraphNodeAppears = parentageGraphPage().isElementDisplayed(ParentageGraphPageElements.PARENTAGE_GRAPH_IMAGE);
        boolean dispDerivNodeAppears = dispositionDerivationPage().isNodeValueDisplayed(value);

        Assertions.assertAll
        (
                () -> Assertions.assertTrue(metadataNodeAppears,"The node with the given value doesn't appear in the sibling metadata pane when it should"),
                () -> Assertions.assertTrue(navTreeNodeAppears,"The node with the given value doesn't appear in the navigation tree pane when it should"),
                () -> Assertions.assertTrue(parentageGraphNodeAppears,"The node with the given value doesn't appear in the parentage graph pane when it should"),
                () -> Assertions.assertTrue(dispDerivNodeAppears,"The node with the given value doesn't appear in the disposition/derivation pane when it should")
        );
    }

    /**
     * STORY/BUG - N/A <br>
     * SUMMARY - This test verifies that after searching for a specific node by the 'Find Cite' tab,
     * that the given node appears in each pane of the page <br>
     * USER - LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void findCiteGradeLegalTest()
    {
        HierarchyDatapodConfiguration.getConfig().setTopNodeTypeWithString("GRADE");
        // TODO: Figure out why this needs a child to delete properly
        HierarchyDatapodConfiguration.getConfig().setSectionCount(1);
        HierarchyDatapodConfiguration.getConfig().setChapterCount(0);
        HierarchyDatapodConfiguration.getConfig().setSubtitleCount(0);
        datapodObjects.add(TargetDataMockingNew.Iowa.Small.insert());
        String nodeUUID = datapodObjects.get(0).getAllNodes().get(0).getNodeUUID();
        Connection connection = CommonDataMocking.connectToDatabase(environmentTag);
        String value = HierarchyDatabaseUtils.getNodeValueByNodeUUID(nodeUUID, ContentSets.IOWA_DEVELOPMENT.getCode(), connection);
        BaseDatabaseUtils.disconnect(connection);
        String keyword = "@";

        homePage().goToHomePage();
        loginPage().logIn();
        hierarchyMenu().goToNavigate();

        //Search value through 'Find Cite' tab
        hierarchySearchPage().clickFindCiteTab();
        findCitePage().setKeyword(keyword);
        findCitePage().setValue(value);
        findCitePage().clickFind();
        hierarchyNavigatePage().switchToHierarchyEditPage();
        hierarchyNavigatePage().waitForHierarchyEditPageToLoad(value);

        //Check that the node appears in each pane of the page
        boolean metadataNodeAppears = siblingMetadataPage().isNodeDisplayedWithValue(value);
        boolean navTreeNodeAppears = hierarchyTreePage().isSelectedTreeNodeDisplayedByValue(value);
        boolean parentageGraphNodeAppears = parentageGraphPage().isElementDisplayed(ParentageGraphPageElements.PARENTAGE_GRAPH_IMAGE);
        boolean dispDerivNodeAppears = dispositionDerivationPage().isNodeValueDisplayed(value);

        Assertions.assertAll
        (
                () -> Assertions.assertTrue(metadataNodeAppears,"The node with the given value doesn't appear in the sibling metadata pane when it should"),
                () -> Assertions.assertTrue(navTreeNodeAppears,"The node with the given value doesn't appear in the navigation tree pane when it should"),
                () -> Assertions.assertTrue(parentageGraphNodeAppears,"The node with the given value doesn't appear in the parentage graph pane when it should"),
                () -> Assertions.assertTrue(dispDerivNodeAppears,"The node with the given value doesn't appear in the disposition/derivation pane when it should")
        );
    }

    /**
     * STORY/BUG - HALCYONST-774 <br>
     * SUMMARY - This test verifies that the correct number of nodes is shown
     * in the Find page after searching criteria that fits several nodes <br>
     * USER - LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void quickSearchBeginsWithXLegalTest()
    {
        HierarchyDatapodConfiguration.getConfig().setTopNodeTypeWithString("GRADE");
        // TODO: Figure out why this needs a child to delete properly
        HierarchyDatapodConfiguration.getConfig().setSectionCount(1);
        HierarchyDatapodConfiguration.getConfig().setChapterCount(0);
        HierarchyDatapodConfiguration.getConfig().setSubtitleCount(0);
        for(int i = 0; i < 5; i++)
        {
            datapodObjects.add(TargetDataMockingNew.Iowa.Small.insert());
        }
        String keyword = "@";
        String value = "IABCDEF";
        String numberOfNodes = "5";

        homePage().goToHomePage();
        loginPage().logIn();
        hierarchyMenu().goToNavigate();

        hierarchySearchPage().clickQuickSearchBeginsWithCheckBox();
        hierarchySearchPage().quickSearch(keyword,value);

        boolean numberOfNodesFoundIsExpected = hierarchyFindPage().numberOfNodesFoundEqualsGivenValue(numberOfNodes);
        Assertions.assertTrue(numberOfNodesFoundIsExpected,"The number of nodes listed in the find page should be equal to " + numberOfNodes);
    }

    /**
     * STORY/BUG - N/A <br>
     * SUMMARY - This test verifies the searched node appears in each pane of the Hierarchy Edit page
     * after clicking the Find button or clicking Enter after inputting the search criteria <br>
     * USER - LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void quickSearchEnterKeysLegalTest()
    {
        HierarchyDatapodConfiguration.getConfig().setSectionCount(2);
        datapodObjects.add(TargetDataMockingNew.Iowa.Small.insert());
        String nodeUUID1 = datapodObjects.get(0).getSections().get(0).getNodeUUID();
        String nodeUUID2 = datapodObjects.get(0).getSections().get(1).getNodeUUID();
        Connection connection = CommonDataMocking.connectToDatabase(environmentTag);
        String value = HierarchyDatabaseUtils.getNodeValueByNodeUUID(nodeUUID1, ContentSets.IOWA_DEVELOPMENT.getCode(), connection);
        String value2 = HierarchyDatabaseUtils.getNodeValueByNodeUUID(nodeUUID2, ContentSets.IOWA_DEVELOPMENT.getCode(), connection);
        BaseDatabaseUtils.disconnect(connection);

        homePage().goToHomePage();
        loginPage().logIn();
        hierarchyMenu().goToNavigate();

        //Enter value under Quick Search tab then click find button
        hierarchySearchPage().quickSearch("=",value);
        hierarchyNavigatePage().waitForHierarchyEditPageToLoad(value);

        //Check that the node appears in each pane of the page
        boolean clickMetadataNodeAppears = siblingMetadataPage().isNodeDisplayedWithValue(value);
        boolean clickNavTreeNodeAppears = hierarchyTreePage().isSelectedTreeNodeDisplayedByValue(value);
        boolean clickParentageGraphNodeAppears = parentageGraphPage().isElementDisplayed(ParentageGraphPageElements.PARENTAGE_GRAPH_IMAGE);
        boolean clickDispDerivNodeAppears = dispositionDerivationPage().isNodeValueDisplayed(value);

        //Enter value under Quick Search tab then click enter
        hierarchySearchPage().quickSearchClickEnter("=",value2);
        hierarchyNavigatePage().waitForHierarchyEditPageToLoad(value2);

        //Check that the node appears in each pane of the page
        boolean enterMetadataNodeAppears = siblingMetadataPage().isNodeDisplayedWithValue(value2);
        boolean enterNavTreeNodeAppears = hierarchyTreePage().isSelectedTreeNodeDisplayedByValue(value2);
        boolean enterParentageGraphNodeAppears = parentageGraphPage().isElementDisplayed(ParentageGraphPageElements.PARENTAGE_GRAPH_IMAGE);
        boolean enterDispDerivNodeAppears = dispositionDerivationPage().isNodeValueDisplayed(value2);

        Assertions.assertAll
        (
                () -> Assertions.assertTrue(clickMetadataNodeAppears,"The node with the given value doesn't appear in the sibling metadata pane when it should"),
                () -> Assertions.assertTrue(clickNavTreeNodeAppears,"The node with the given value doesn't appear in the navigation tree pane when it should"),
                () -> Assertions.assertTrue(clickParentageGraphNodeAppears,"The node with the given value doesn't appear in the parentage graph pane when it should"),
                () -> Assertions.assertTrue(clickDispDerivNodeAppears,"The node with the given value doesn't appear in the disposition/derivation pane when it should"),
                () -> Assertions.assertTrue(enterMetadataNodeAppears,"The node with the given value doesn't appear in the sibling metadata pane when it should"),
                () -> Assertions.assertTrue(enterNavTreeNodeAppears,"The node with the given value doesn't appear in the navigation tree pane when it should"),
                () -> Assertions.assertTrue(enterParentageGraphNodeAppears,"The node with the given value doesn't appear in the parentage graph pane when it should"),
                () -> Assertions.assertTrue(enterDispDerivNodeAppears,"The node with the given value doesn't appear in the disposition/derivation pane when it should")
        );
    }

    /**
     * STORY/BUG - N/A <br>
     * SUMMARY - This test verifies that clicking the Prev Result button shows the correct list of node UUIDS
     * after searching for criteria that fits multiple nodes <br>
     * USER - LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void quickSearchPrevResultsLegalTest()
    {
        datapodObjects.add(TargetDataMockingNew.Iowa.Small.insert());
        datapodObjects.add(TargetDataMockingNew.Iowa.Small.insert());
        String value = "XCIX";
        String keyword = "TITLE";

        homePage().goToHomePage();
        loginPage().logIn();
        hierarchyMenu().goToNavigate();

        //Search with given criteria and get list of nodes it falls under
        hierarchySearchPage().quickSearch(keyword,value);
        List<String> searchResults = hierarchyFindPage().listOfAllNodeUuidsShown();
        hierarchyFindPage().clickFirstNodeUuid();

        //Click Prev Result and check that list of nodes is same as before
        hierarchySearchPage().clickPrevResultButton();
        List<String> searchResults2 = hierarchyFindPage().listOfAllNodeUuidsShown();
        boolean searchResultsAreEqual = searchResults.equals(searchResults2);
        Assertions.assertTrue(searchResultsAreEqual,"The search results were not equal when they should be");
    }

    /**
     * STORY/BUG - HALCYONST-773 <br>
     * SUMMARY - This test verifies that after searching for a node through the Find Templates tab,
     * that the node appears in each pane of the Hierarchy Edit page <br>
     * USER - LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void findTemplateLegalTest()
    {
        datapodObjects.add(TargetDataMockingNew.Iowa.Small.insert());
        String nodeUUID = datapodObjects.get(0).getSections().get(0).getNodeUUID();
        Connection connection = CommonDataMocking.connectToDatabase(environmentTag);
        String value = HierarchyDatabaseUtils.getNodeValueByNodeUUID(nodeUUID, ContentSets.IOWA_DEVELOPMENT.getCode(), connection);
        BaseDatabaseUtils.disconnect(connection);

        //Set content set
        homePage().goToHomePage();
        loginPage().logIn();
        hierarchyMenu().goToNavigate();

        //Search for node through Find Templates tab
        hierarchySearchPage().clickFindTemplatesTab();
        findTemplatesPage().enterNodeValue(value);
        findTemplatesPage().clickGoButton();
        hierarchyNavigatePage().waitForHierarchyEditPageToLoad(value);

        //Check that the node appears in each pane of the page
        boolean metadataNodeAppears = siblingMetadataPage().isNodeDisplayedWithValue(value);
        boolean navTreeNodeAppears = hierarchyTreePage().isSelectedTreeNodeDisplayedByValue(value);
        boolean parentageGraphNodeAppears = parentageGraphPage().isElementDisplayed(ParentageGraphPageElements.PARENTAGE_GRAPH_IMAGE);
        boolean dispDerivNodeAppears = dispositionDerivationPage().isNodeValueDisplayed(value);

        Assertions.assertAll
        (
                () -> Assertions.assertTrue(metadataNodeAppears,"The node with the given value doesn't appear in the sibling metadata pane when it should"),
                () -> Assertions.assertTrue(navTreeNodeAppears,"The node with the given value doesn't appear in the navigation tree pane when it should"),
                () -> Assertions.assertTrue(parentageGraphNodeAppears,"The node with the given value doesn't appear in the parentage graph pane when it should"),
                () -> Assertions.assertTrue(dispDerivNodeAppears,"The node with the given value doesn't appear in the disposition/derivation pane when it should")
        );
    }

    /**
     * STORY/BUG - N/A <br>
     * SUMMARY - This test verifies that after searching for a node through the Find Content tab,
     * that the node appears in each pane of the Hierarchy Edit page <br>
     * USER - LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void findContentUUIDLegalTest()
    {
        datapodObjects.add(TargetDataMockingNew.Iowa.Small.insert());
        String uuid = datapodObjects.get(0).getSections().get(0).getContentUUID();
        Connection connection = CommonDataMocking.connectToDatabase(environmentTag);
        String value = HierarchyDatabaseUtils.getNodeValue(uuid, ContentSets.IOWA_DEVELOPMENT.getCode(), connection);
        BaseDatabaseUtils.disconnect(connection);

        homePage().goToHomePage();
        loginPage().logIn();
        hierarchyMenu().goToNavigate();

        hierarchySearchPage().searchContentUuid(uuid);
        hierarchyNavigatePage().waitForHierarchyEditPageToLoad(value);

        //Check that the node appears in each pane of the page
        boolean metadataNodeAppears = siblingMetadataPage().isNodeDisplayedWithValue(value);
        boolean navTreeNodeAppears = hierarchyTreePage().isSelectedTreeNodeDisplayedByValue(value);
        boolean parentageGraphNodeAppears = parentageGraphPage().isElementDisplayed(ParentageGraphPageElements.PARENTAGE_GRAPH_IMAGE);
        boolean dispDerivNodeAppears = dispositionDerivationPage().isNodeValueDisplayed(value);

        Assertions.assertAll
        (
                () -> Assertions.assertTrue(metadataNodeAppears,"The node with the given value doesn't appear in the sibling metadata pane when it should"),
                () -> Assertions.assertTrue(navTreeNodeAppears,"The node with the given value doesn't appear in the navigation tree pane when it should"),
                () -> Assertions.assertTrue(parentageGraphNodeAppears,"The node with the given value doesn't appear in the parentage graph pane when it should"),
                () -> Assertions.assertTrue(dispDerivNodeAppears,"The node with the given value doesn't appear in the disposition/derivation pane when it should")
        );
    }

    /**
     * STORY/BUG - N/A <br>
     * SUMMARY - This test verifies that after searching for a node through the Find HID tab,
     * that the node appears in each pane of the Hierarchy Edit page <br>
     * USER - LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void findHIDLegalTest()
    {
        datapodObjects.add(TargetDataMockingNew.Iowa.Small.insert());
        String nodeUUID = datapodObjects.get(0).getSections().get(0).getNodeUUID();
        Connection connection = CommonDataMocking.connectToDatabase(environmentTag);
        int hid = HierarchyDatabaseUtils.getHIDWithNodeUUID(nodeUUID, connection);
        String headingId = String.valueOf(hid);
        String value = HierarchyDatabaseUtils.getNodeValueByNodeUUID(nodeUUID, ContentSets.IOWA_DEVELOPMENT.getCode(), connection);
        BaseDatabaseUtils.disconnect(connection);

        homePage().goToHomePage();
        loginPage().logIn();
        hierarchyMenu().goToNavigate();

        hierarchySearchPage().searchHidUnderFindHidTab(headingId);
        hierarchyNavigatePage().waitForHierarchyEditPageToLoad(value);

        //Check that the node appears in each pane of the page
        boolean metadataNodeAppears = siblingMetadataPage().isNodeDisplayedWithValue(value);
        boolean navTreeNodeAppears = hierarchyTreePage().isSelectedTreeNodeDisplayedByValue(value);
        boolean parentageGraphNodeAppears = parentageGraphPage().isElementDisplayed(ParentageGraphPageElements.PARENTAGE_GRAPH_IMAGE);
        boolean dispDerivNodeAppears = dispositionDerivationPage().isNodeValueDisplayed(value);

        Assertions.assertAll
        (
                () -> Assertions.assertTrue(metadataNodeAppears,"The node with the given value doesn't appear in the sibling metadata pane when it should"),
                () -> Assertions.assertTrue(navTreeNodeAppears,"The node with the given value doesn't appear in the navigation tree pane when it should"),
                () -> Assertions.assertTrue(parentageGraphNodeAppears,"The node with the given value doesn't appear in the parentage graph pane when it should"),
                () -> Assertions.assertTrue(dispDerivNodeAppears,"The node with the given value doesn't appear in the disposition/derivation pane when it should")
        );
    }

    /**
     * STORY/BUG - N/A <br>
     * SUMMARY - This test verifies that after searching for a node through the Find Node tab,
     * that the node appears in each pane of the Hierarchy Edit page <br>
     * USER - LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void findNodeUUIDLegalTest()
    {
        HierarchyDatapodConfiguration.getConfig().setTopNodeTypeWithString("GRADE");
        // TODO: Find out why this will not delete without a child
        HierarchyDatapodConfiguration.getConfig().setSectionCount(1);
        HierarchyDatapodConfiguration.getConfig().setChapterCount(0);
        HierarchyDatapodConfiguration.getConfig().setSubtitleCount(0);
        datapodObjects.add(TargetDataMockingNew.Iowa.Small.insert());
        String uuid = datapodObjects.get(0).getAllNodes().get(0).getNodeUUID();
        Connection connection = CommonDataMocking.connectToDatabase(environmentTag);
        String value = HierarchyDatabaseUtils.getNodeValueByNodeUUID(uuid, ContentSets.IOWA_DEVELOPMENT.getCode(), connection);
        BaseDatabaseUtils.disconnect(connection);

        homePage().goToHomePage();
        loginPage().logIn();
        hierarchyMenu().goToNavigate();

        hierarchySearchPage().searchNodeUuid(uuid);
        hierarchyNavigatePage().waitForHierarchyEditPageToLoad(value);

        //Check that the node appears in each pane of the page
        boolean metadataNodeAppears = siblingMetadataPage().isNodeDisplayedWithValue(value);
        boolean navTreeNodeAppears = hierarchyTreePage().isSelectedTreeNodeDisplayedByValue(value);
        boolean parentageGraphNodeAppears = parentageGraphPage().isElementDisplayed(ParentageGraphPageElements.PARENTAGE_GRAPH_IMAGE);
        boolean dispDerivNodeAppears = dispositionDerivationPage().isNodeValueDisplayed(value);

        Assertions.assertAll
        (
                () -> Assertions.assertTrue(metadataNodeAppears,"The node with the given value doesn't appear in the sibling metadata pane when it should"),
                () -> Assertions.assertTrue(navTreeNodeAppears,"The node with the given value doesn't appear in the navigation tree pane when it should"),
                () -> Assertions.assertTrue(parentageGraphNodeAppears,"The node with the given value doesn't appear in the parentage graph pane when it should"),
                () -> Assertions.assertTrue(dispDerivNodeAppears,"The node with the given value doesn't appear in the disposition/derivation pane when it should")
        );
    }

    /**
     * STORY/BUG - N/A <br>
     * SUMMARY - This test verifies that after searching for a node through the Find Vols tab,
     * that the node appears in each pane of the Hierarchy Edit page <br>
     * USER - LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void selectedVolumeLegalTest()
    {
        datapodObjects.add(TargetDataMockingNew.Iowa.Small.insert());
        String volNumber = "9999";
        String value = "XCIX";

        homePage().goToHomePage();
        loginPage().logIn();
        hierarchyMenu().goToNavigate();

        hierarchySearchPage().setVolNumberUnderFindVolsTab(volNumber);
        hierarchyNavigatePage().waitForHierarchyEditPageToLoad(value);

        //Check that the node appears in each pane of the page
        boolean metadataNodeAppears = siblingMetadataPage().isNodeDisplayedWithValue(value);
        boolean navTreeNodeAppears = hierarchyTreePage().isSelectedTreeNodeDisplayedByValue(value);
        boolean parentageGraphNodeAppears = parentageGraphPage().isElementDisplayed(ParentageGraphPageElements.PARENTAGE_GRAPH_IMAGE);
        boolean dispDerivNodeAppears = dispositionDerivationPage().isNodeValueDisplayed(value);

        Assertions.assertAll
        (
                () -> Assertions.assertTrue(metadataNodeAppears,"The node with the given value doesn't appear in the sibling metadata pane when it should"),
                () -> Assertions.assertTrue(navTreeNodeAppears,"The node with the given value doesn't appear in the navigation tree pane when it should"),
                () -> Assertions.assertTrue(parentageGraphNodeAppears,"The node with the given value doesn't appear in the parentage graph pane when it should"),
                () -> Assertions.assertTrue(dispDerivNodeAppears,"The node with the given value doesn't appear in the disposition/derivation pane when it should")
        );
    }

    @AfterEach
    public void cleanUp()
    {
        for(HierarchyDatapodObject datapodObject : datapodObjects)
        {
            datapodObject.delete();
        }
        datapodObjects.clear();
    }
}
