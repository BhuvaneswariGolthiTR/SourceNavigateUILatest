package com.thomsonreuters.codes.codesbench.quality.tests.hierarchy.tagassignment;

import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.HierarchyTreePageElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.TargetDataMockingNew;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.datapod.HierarchyDatapodConfiguration;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.datapod.HierarchyDatapodObject;
import org.junit.jupiter.api.*;

import java.util.*;

import static com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import static com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import static com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets.IOWA_DEVELOPMENT;

public class InputAsSiblingAndInputAsChildWithTaxTypesTests extends TestService
{
    private static final String KEYWORD = "@";
    private static final String EMPTY = "";
    private static final int HID_LOWER_LIMIT = 1000000;
    private static final int HID_UPPER_LIMIT = 999999999;
    private static final String FIRST_PUB_TAG = "AN";
    private static final String SECOND_PUB_TAG = "WL";

    HierarchyDatapodObject datapodObject;

    @BeforeEach
    public void loginAndGoToHierarchyPage()
    {
        homePage().goToHomePage();
        loginPage().logIn();
        hierarchyNavigatePage().goToHierarchyPage(IOWA_DEVELOPMENT.getCode());

    }

    /**
     * STORY/BUG - HALCYONST-10871/HALCYONST-11123 <br>
     * SUMMARY - This test verifies that the hierarchy function 'Input as Sibling' works correctly with one and several Tax Types and Pub Tag Refresh <br>
     * USER - LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void inputAsSiblingWithOneAndSeveralTaxTypesTest()
    {
        String fileToInput = "TaxTypesXml\\OneAnsSeveralTaxTypes.xml";

        Node sibling = new Node();
        sibling.setValue("TEST_ONE_AND_SEVERAL_TAXTYPES");

        Node child1 = new Node();
        child1.setValue("Test with one Tax Type.");
        child1.setExpectedTaxType("PROP");

        Node child2 = new Node();
        child2.setValue("Test with several Tax Types.");
        child2.setExpectedTaxType("CORPLI;PERS;PSHIP");

        List<Node> nodes = new ArrayList<>();

        datapodObject = TargetDataMockingNew.Iowa.Small.insert();
        String subtitleNodeUUID = datapodObject.getSubtitles().get(0).getNodeUUID();

        hierarchySearchPage().searchNodeUuid(subtitleNodeUUID);
        String initialNodeVols = siblingMetadataPage().getSelectedNodeVols();

        //input as sibling
        inputAsSibling(fileToInput);
        checkWorkflow();

        //Check that the file was inputted as a sibling
        hierarchySearchPage().searchNodeUuid(subtitleNodeUUID);
        Assertions.assertTrue(siblingMetadataPage().isNodeDisplayedWithValue(sibling.getValue()),"The sibling metadata node should appear but does not");
        hierarchySearchPage().quickSearch(KEYWORD,sibling.getValue());
        siblingMetadataPage().waitForPageLoaded();
        getActualNodeParameters(sibling);
        sibling.setPubTags(getPubTagFromDynamicScrolling());
        nodes.add(sibling);

        //Check children
        hierarchyTreePage().clickExpandButtonNextToGivenValue(sibling.getValue());
        hierarchyTreePage().waitForElement(String.format(HierarchyTreePageElements.NAV_TREE_NODE_WITH_VALUE_GIVEN,child1.getValue()));
        Assertions.assertTrue(hierarchyTreePage().isTreeNodeDisplayedWithGivenValue(child1.getValue()),"The expected first child node DOES NOT appear");
        Assertions.assertTrue(hierarchyTreePage().isTreeNodeDisplayedWithGivenValue(child2.getValue()),"The expected second child node DOES NOT appear");

        //Check first child
        hierarchyTreePage().clickOnTreeNodeWithGivenValue(child1.getValue());
        siblingMetadataPage().waitForGridRefresh();
        getActualNodeParameters(child1);
        child1.setPubTags(getPubTagFromDynamicScrolling());
        nodes.add(child1);

        //Check second child
        hierarchyTreePage().clickOnTreeNodeWithGivenValue(child2.getValue());
        siblingMetadataPage().waitForGridRefresh();
        getActualNodeParameters(child2);
        child2.setPubTags(getPubTagFromDynamicScrolling());
        nodes.add(child2);

        for(Node node : nodes)
        {
            Assertions.assertAll(
                    () -> Assertions.assertEquals(initialNodeVols, node.getVols(), String.format("%s: The inputted sibling node has %s vols value instead of %s", node.getValue(), node.getVols(), initialNodeVols)),
                    () -> Assertions.assertTrue(node.getPublicStatus(), String.format("%s: The inputted sibling node has Published status", node.getValue())),
                    () -> Assertions.assertTrue(HID_LOWER_LIMIT <=node.getHID(), String.format("%s: The inputted sibling node HID value is %d. It is LESS than %d", node.getValue(), node.getHID(), HID_LOWER_LIMIT)),
                    () -> Assertions.assertTrue(node.getHID()<= HID_UPPER_LIMIT, String.format("%s: The inputted sibling node HID value is %d. It  is more than %d", node.getValue(), node.getHID(), HID_UPPER_LIMIT)),
                    () -> Assertions.assertEquals(node.getOnlineProduct(), EMPTY, String.format("%s: The sibling node HAS some Online Products assignment", node.getValue())),
                    () -> Assertions.assertEquals(node.getExpectedTaxType(), node.getActualTaxType(), String.format("%s: The sibling node HAS some Tax Type assignment", node.getValue()) ),
                    () -> Assertions.assertTrue(node.getPubTags().contains(FIRST_PUB_TAG), String.format("The inputted sibling node doesn't contain Pub Tag %s.", FIRST_PUB_TAG)),
                    () -> Assertions.assertTrue(node.getPubTags().contains(SECOND_PUB_TAG), String.format("The inputted sibling node doesn't contain Pub Tag %s.", SECOND_PUB_TAG))
            );
        }
    }

    /**
     * STORY/BUG - HALCYONST-10871 <br>
     * SUMMARY - This test verifies that the hierarchy function 'Input as Sibling' works correctly with Tax Types that doesn't exist<br>
     * USER - LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void inputAsSiblingWithNotExistedTaxTypeTest()
    {
        String fileToInput = "TaxTypesXml\\NoExistTaxType.xml";

        Node sibling = new Node();
        sibling.setValue("TEST_NOT_EXIST_TAXTYPE");

        Node child = new Node();
        child.setValue("Test with not exist Tax Type.");

        List<Node> nodes = new ArrayList<>();

        datapodObject = TargetDataMockingNew.Iowa.Small.insert();
        String subtitleNodeUUID = datapodObject.getSubtitles().get(0).getNodeUUID();

        //Sign in as the legal user. Go to Hierarchy -> Navigate. Quick search Node UUID: I7A35F76014EE11DA8AC5CD53670E6B4E
        hierarchySearchPage().searchNodeUuid(subtitleNodeUUID);
        String initialNodeVols = siblingMetadataPage().getSelectedNodeVols();

        //input as sibling
        inputAsSibling(fileToInput);
        checkWorkflow();

        //Check that the file was inputted as a sibling
        hierarchySearchPage().searchNodeUuid(subtitleNodeUUID);
        Assertions.assertTrue(siblingMetadataPage().isNodeDisplayedWithValue(sibling.getValue()),"The sibling metadata node should appear but does not");
        hierarchySearchPage().quickSearch(KEYWORD,sibling.getValue());
        siblingMetadataPage().waitForPageLoaded();
        getActualNodeParameters(sibling);
        nodes.add(sibling);

        //Check children
        hierarchyTreePage().clickExpandButtonNextToGivenValue(sibling.getValue());
        hierarchyTreePage().waitForElement(String.format(HierarchyTreePageElements.NAV_TREE_NODE_WITH_VALUE_GIVEN,child.getValue()));
        Assertions.assertTrue(hierarchyTreePage().isTreeNodeDisplayedWithGivenValue(child.getValue()),"The expected child node DOES NOT appear");

        //Check first child
        hierarchyTreePage().clickOnTreeNodeWithGivenValue(child.getValue());
        siblingMetadataPage().waitForGridRefresh();
        getActualNodeParameters(child);
        nodes.add(child);

        for(Node node : nodes)
        {
            Assertions.assertAll(
                    () -> Assertions.assertEquals(initialNodeVols, node.getVols(), String.format("%s: The inputted sibling node has %s vols value instead of %s", node.getValue(), node.getVols(), initialNodeVols)),
                    () -> Assertions.assertTrue(node.getPublicStatus(), String.format("%s: The inputted sibling node has Published status", node.getValue())),
                    () -> Assertions.assertTrue(HID_LOWER_LIMIT <=node.getHID(), String.format("%s: The inputted sibling node HID value is %d. It is LESS than %d", node.getValue(), node.getHID(), HID_LOWER_LIMIT)),
                    () -> Assertions.assertTrue(node.getHID()<= HID_UPPER_LIMIT, String.format("%s: The inputted sibling node HID value is %d. It  is more than %d", node.getValue(), node.getHID(), HID_UPPER_LIMIT)),
                    () -> Assertions.assertEquals(node.getOnlineProduct(), EMPTY, String.format("%s: The sibling node HAS some Online Products assignment", node.getValue())),
                    () -> Assertions.assertEquals(node.getExpectedTaxType(), node.getActualTaxType(), String.format("%s: The sibling node HAS some Tax Type assignment", node.getValue()) ));
        }
    }

    /**
     * STORY/BUG - HALCYONST-10871 <br>
     * SUMMARY - This test verifies that the hierarchy function 'Input as Sibling' works correctly with Tax Types that isn't assigned on node Content Set <br>
     * USER - LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void inputAsSiblingWithTaxTypeNotAssignedOnContentSetTest()
    {
        String fileToInput = "TaxTypesXml\\NoAssignedTaxType.xml";

        Node sibling = new Node();
        sibling.setValue("TEST_NOT_ASSIGNED_TAXTYPE");

        Node child = new Node();
        child.setValue("Test with not assigned on Content Set Tax Type.");

        List<Node> nodes = new ArrayList<>();

        datapodObject = TargetDataMockingNew.Iowa.Small.insert();
        String subtitleNodeUUID = datapodObject.getSubtitles().get(0).getNodeUUID();

        hierarchySearchPage().searchNodeUuid(subtitleNodeUUID);
        String initialNodeVols = siblingMetadataPage().getSelectedNodeVols();

        //input as sibling
        inputAsSibling(fileToInput);
        checkWorkflow();

        //Check that the file was inputted as a sibling
        hierarchySearchPage().searchNodeUuid(subtitleNodeUUID);
        Assertions.assertTrue(siblingMetadataPage().isNodeDisplayedWithValue(sibling.getValue()),"The sibling metadata node should appear but does not");
        hierarchySearchPage().quickSearch(KEYWORD,sibling.getValue());
        siblingMetadataPage().waitForPageLoaded();
        getActualNodeParameters(sibling);
        nodes.add(sibling);

        //Check children
        hierarchyTreePage().clickExpandButtonNextToGivenValue(sibling.getValue());
        hierarchyTreePage().waitForElement(String.format(HierarchyTreePageElements.NAV_TREE_NODE_WITH_VALUE_GIVEN,child.getValue()));
        Assertions.assertTrue(hierarchyTreePage().isTreeNodeDisplayedWithGivenValue(child.getValue()),"The expected child node DOES NOT appear");

        //Check first child
        hierarchyTreePage().clickOnTreeNodeWithGivenValue(child.getValue());
        siblingMetadataPage().waitForGridRefresh();
        getActualNodeParameters(child);
        nodes.add(child);

        for(Node node : nodes)
        {
            Assertions.assertAll(
                    () -> Assertions.assertEquals(initialNodeVols, node.getVols(), String.format("%s: The inputted sibling node has %s vols value instead of %s", node.getValue(), node.getVols(), initialNodeVols)),
                    () -> Assertions.assertTrue(node.getPublicStatus(), String.format("%s: The inputted sibling node has Published status", node.getValue())),
                    () -> Assertions.assertTrue(HID_LOWER_LIMIT <=node.getHID(), String.format("%s: The inputted sibling node HID value is %d. It is LESS than %d", node.getValue(), node.getHID(), HID_LOWER_LIMIT)),
                    () -> Assertions.assertTrue(node.getHID()<= HID_UPPER_LIMIT, String.format("%s: The inputted sibling node HID value is %d. It  is more than %d", node.getValue(), node.getHID(), HID_UPPER_LIMIT)),
                    () -> Assertions.assertEquals(node.getOnlineProduct(), EMPTY, String.format("%s: The sibling node HAS some Online Products assignment", node.getValue())),
                    () -> Assertions.assertEquals(node.getExpectedTaxType(), node.getActualTaxType(), String.format("%s: The sibling node HAS some Tax Type assignment", node.getValue()) ));
        }
    }

    /**
     * STORY/BUG - HALCYONST-10871 <br>
     * SUMMARY - This test verifies that the hierarchy function 'Input as Sibling' works correctly with duplicated Tax Types tag on xml <br>
     * USER - LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void inputAsSiblingWithDuplicatedTaxTypeTest()
    {
        String fileToInput = "TaxTypesXml\\DuplicatedTaxType.xml";

        Node sibling = new Node();
        sibling.setValue("TEST_DUPLICATED_TAXTYPE");

        Node child = new Node();
        child.setValue("Test with duplicated Tax Type.");
        child.setExpectedTaxType("CORPLI");

        List<Node> nodes = new ArrayList<>();

        datapodObject = TargetDataMockingNew.Iowa.Small.insert();
        String subtitleNodeUUID = datapodObject.getSubtitles().get(0).getNodeUUID();

        hierarchySearchPage().searchNodeUuid(subtitleNodeUUID);
        String initialNodeVols = siblingMetadataPage().getSelectedNodeVols();

        //input as sibling
        inputAsSibling(fileToInput);
        checkWorkflow();

        //Check that the file was inputted as a sibling
        hierarchySearchPage().searchNodeUuid(subtitleNodeUUID);
        Assertions.assertTrue(siblingMetadataPage().isNodeDisplayedWithValue(sibling.getValue()),"The sibling metadata node should appear but does not");
        hierarchySearchPage().quickSearch(KEYWORD,sibling.getValue());
        siblingMetadataPage().waitForPageLoaded();
        getActualNodeParameters(sibling);
        nodes.add(sibling);

        //Check children
        hierarchyTreePage().clickExpandButtonNextToGivenValue(sibling.getValue());
        hierarchyTreePage().waitForElement(String.format(HierarchyTreePageElements.NAV_TREE_NODE_WITH_VALUE_GIVEN,child.getValue()));
        Assertions.assertTrue(hierarchyTreePage().isTreeNodeDisplayedWithGivenValue(child.getValue()),"The expected child node DOES NOT appear");

        //Check first child
        hierarchyTreePage().clickOnTreeNodeWithGivenValue(child.getValue());
        siblingMetadataPage().waitForGridRefresh();
        getActualNodeParameters(child);
        nodes.add(child);

        for(Node node : nodes)
        {
            Assertions.assertAll(
                    () -> Assertions.assertEquals(initialNodeVols, node.getVols(), String.format("%s: The inputted sibling node has %s vols value instead of %s", node.getValue(), node.getVols(), initialNodeVols)),
                    () -> Assertions.assertTrue(node.getPublicStatus(), String.format("%s: The inputted sibling node has Published status", node.getValue())),
                    () -> Assertions.assertTrue(HID_LOWER_LIMIT <=node.getHID(), String.format("%s: The inputted sibling node HID value is %d. It is LESS than %d", node.getValue(), node.getHID(), HID_LOWER_LIMIT)),
                    () -> Assertions.assertTrue(node.getHID()<= HID_UPPER_LIMIT, String.format("%s: The inputted sibling node HID value is %d. It  is more than %d", node.getValue(), node.getHID(), HID_UPPER_LIMIT)),
                    () -> Assertions.assertEquals(node.getOnlineProduct(), EMPTY, String.format("%s: The sibling node HAS some Online Products assignment", node.getValue())),
                    () -> Assertions.assertEquals(node.getExpectedTaxType(), node.getActualTaxType(), String.format("%s: The sibling node HAS some Tax Type assignment", node.getValue()) ));
        }
    }

    /**
     * STORY/BUG - HALCYONST-10871/HALCYONST-11123 <br>
     * SUMMARY - This test verifies that the hierarchy function 'Input as Child' works correctly with one or several Tax Types and Pub Tag refresh <br>
     * USER - LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void inputAsChildWithOneAndSeveralTaxTypesTest()
    {
        String fileToInput = "TaxTypesXml\\OneAnsSeveralTaxTypes.xml";

        Node insertedChild= new Node();
        insertedChild.setValue("TEST_ONE_AND_SEVERAL_TAXTYPES");

        Node child1 = new Node();
        child1.setValue("Test with one Tax Type.");
        child1.setExpectedTaxType("PROP");

        Node child2 = new Node();
        child2.setValue("Test with several Tax Types.");
        child2.setExpectedTaxType("CORPLI;PERS;PSHIP");

        List<Node> nodes = new ArrayList<>();

        HierarchyDatapodConfiguration.getConfig().setChapterCount(0);
        HierarchyDatapodConfiguration.getConfig().setSectionCount(0);

        datapodObject = TargetDataMockingNew.Iowa.Small.insert();
        String subtitleNodeUUID = datapodObject.getSubtitles().get(0).getNodeUUID();

        //Sign in as the legal user. Go to Hierarchy -> Navigate. Quick search Node UUID: I7A35F76014EE11DA8AC5CD53670E6B4E
        hierarchySearchPage().searchNodeUuid(subtitleNodeUUID);
        String initialNodeVols = siblingMetadataPage().getSelectedNodeVols();

        //input as child
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().hierarchyFunctionsInputAsChild();
        hierarchyInputAsSiblingPage().selectFileToUpload(fileToInput);
        hierarchyInputAsSiblingPage().clickSave();
        checkWorkflow();

        //Check that the file was inputted as a insertedChild
        hierarchySearchPage().searchNodeUuid(subtitleNodeUUID);
        String initialNodeValue = "T. I STATE SOVEREIGNTY AND MANAGEMENT";
        hierarchyTreePage().clickExpandButtonNextToGivenValue(initialNodeValue);
        hierarchyTreePage().waitForElement(String.format(HierarchyTreePageElements.NAV_TREE_NODE_WITH_VALUE_GIVEN,insertedChild.getValue()));
        Assertions.assertTrue(hierarchyTreePage().isTreeNodeDisplayedWithGivenValue(insertedChild.getValue()),"The inserted Child metadata node should appear but does not");
        hierarchySearchPage().quickSearch(KEYWORD,insertedChild.getValue());
        siblingMetadataPage().waitForPageLoaded();

        //Check inserted Child node
        getActualNodeParameters(insertedChild);
        insertedChild.setPubTags(getPubTagFromDynamicScrolling());
        nodes.add(insertedChild);

        //Check children
        hierarchyTreePage().waitForElement(String.format(HierarchyTreePageElements.NAV_TREE_NODE_WITH_VALUE_GIVEN,child1.getValue()));
        Assertions.assertTrue(hierarchyTreePage().isTreeNodeDisplayedWithGivenValue(child1.getValue()),"The expected first child node DOES NOT appear");
        Assertions.assertTrue(hierarchyTreePage().isTreeNodeDisplayedWithGivenValue(child2.getValue()),"The expected second child node DOES NOT appear");

        //Check first child
        hierarchyTreePage().clickOnTreeNodeWithGivenValue(child1.getValue());
        siblingMetadataPage().waitForGridRefresh();
        getActualNodeParameters(child1);
        child1.setPubTags(getPubTagFromDynamicScrolling());
        nodes.add(child1);

        //Check second child
        hierarchyTreePage().clickOnTreeNodeWithGivenValue(child2.getValue());
        siblingMetadataPage().waitForGridRefresh();
        getActualNodeParameters(child2);
        child2.setPubTags(getPubTagFromDynamicScrolling());
        nodes.add(child2);

        for(Node node : nodes)
        {
            Assertions.assertAll(
                    () -> Assertions.assertEquals(initialNodeVols, node.getVols(), String.format("%s: The inputted child node has %s vols value instead of %s", node.getValue(), node.getVols(), initialNodeVols)),
                    () -> Assertions.assertTrue(node.getPublicStatus(), String.format("%s: The inputted child node has Published status", node.getValue())),
                    () -> Assertions.assertTrue(HID_LOWER_LIMIT <=node.getHID(), String.format("%s: The inputted child node HID value is %d. It is LESS than %d", node.getValue(), node.getHID(), HID_LOWER_LIMIT)),
                    () -> Assertions.assertTrue(node.getHID()<= HID_UPPER_LIMIT, String.format("%s: The inputted child node HID value is %d. It  is more than %d", node.getValue(), node.getHID(), HID_UPPER_LIMIT)),
                    () -> Assertions.assertEquals(node.getOnlineProduct(), EMPTY, String.format("%s: The child node HAS some Online Products assignment", node.getValue())),
                    () -> Assertions.assertEquals(node.getExpectedTaxType(), node.getActualTaxType(), String.format("%s: The child node HAS some Tax Type assignment", node.getValue())),
                    () -> Assertions.assertTrue(node.getPubTags().contains(FIRST_PUB_TAG), String.format("The inputted sibling node doesn't contain Pub Tag %s.", FIRST_PUB_TAG)),
                    () -> Assertions.assertTrue(node.getPubTags().contains(SECOND_PUB_TAG), String.format("The inputted sibling node doesn't contain Pub Tag %s.", SECOND_PUB_TAG))
            );
        }
    }

     @AfterEach
     public void deleteDatapod()
     {
         if(datapodObject != null)
         {
             datapodObject.delete();
         }
     }

    private void inputAsSibling(String file)
    {
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().hierarchyFunctionsInputAsSibling();
        hierarchyInputAsSiblingPage().selectFileToUpload(file);
        hierarchyInputAsSiblingPage().clickSave();
    }

    private void checkWorkflow()
    {
        String workflowId = yourWorkflowHasBeenCreatedPage().getWorkflowId();
        yourWorkflowHasBeenCreatedPage().closeWorkflowConfirmationPage();
        hierarchyNavigatePage().switchToHierarchyEditPage();
        toolsMenu().goToWorkflowReportingSystem();
        workflowSearchPage().setWorkflowID(workflowId);
        workflowSearchPage().clickFilterButton();
        Assertions.assertTrue(workflowSearchPage().checkFirstWorkflowFinishedFiveMinutes(),String.format("The workflow %s did not finish",workflowId));
        workflowSearchPage().closeCurrentWindowIgnoreDialogue();
        hierarchyNavigatePage().switchToHierarchyEditPage();
    }

    private String getUuidFromMetadataPage()
    {
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().updateMetadata();
        String nodeUuid = updateMetadataPage().getNodeUuid();
        updateMetadataPage().clickCancel();
        return nodeUuid;
    }

    private void getActualNodeParameters(Node node)
    {
        node.setVols(siblingMetadataPage().getSelectedNodeVols());
        node.setPublicStatus(siblingMetadataPage().isSelectedNodeStatusNotPublished());
        node.setHID(Integer.parseInt(siblingMetadataPage().getSelectedNodeHID()));
        node.setUuid(getUuidFromMetadataPage());
        node.setOnlineProduct(siblingMetadataPage().getSelectedNodeProductTag());
        node.setActualTaxType(siblingMetadataPage().getSelectedNodeTaxType());
    }

    private List<String>  getPubTagFromDynamicScrolling()
    {
        siblingMetadataPage().selectedSiblingMetadataEditContent();
        editorPage().switchToEditor();
        editorPage().switchToEditorTextFrame();
        List<String> pubTags = editorTextPage().getPubtagsList();
        editorPage().closeDocumentWithNoChanges();
        hierarchyNavigatePage().switchToHierarchyEditPage();
        return pubTags;
    }

    static class Node
    {
        String value = "";
        String expectedTaxType = "";
        String actualTaxType = "";
        String onlineProduct = "";
        String Uuid = "";
        String vols = "";
        List<String> pubTags;
        int HID = 0;
        Boolean publicStatus = false;

         public String getOnlineProduct() {
             return onlineProduct;
         }

         public String getValue() {
             return value;
         }

         public String getExpectedTaxType() {
             return expectedTaxType;
         }

         public String getUuid() {
             return Uuid;
         }

         public Boolean getPublicStatus() {
             return publicStatus;
         }

         public String getActualTaxType() {
             return actualTaxType;
         }

         public int getHID() {
             return HID;
         }

         public String getVols() {
             return vols;
         }

        public List<String> getPubTags()
        {
            return pubTags;
        }

        public void setPubTags(List<String> pubTags)
        {
            this.pubTags = pubTags;
        }

         public void setVols(String vols) {
             this.vols = vols;
         }

         public void setHID(int HID) {
             this.HID = HID;
         }

         public void setActualTaxType(String actualTaxType) {
             this.actualTaxType = actualTaxType;
         }

         public void setValue(String value) {
             this.value = value;
         }

         public void setExpectedTaxType(String expectedTaxType) {
             this.expectedTaxType = expectedTaxType;
         }

         public void setUuid(String uuid) {
             Uuid = uuid;
         }

         public void setPublicStatus(Boolean publicStatus) {
             this.publicStatus = publicStatus;
         }

         public void setOnlineProduct(String onlineProduct) {
             this.onlineProduct = onlineProduct;
         }

     }

}
