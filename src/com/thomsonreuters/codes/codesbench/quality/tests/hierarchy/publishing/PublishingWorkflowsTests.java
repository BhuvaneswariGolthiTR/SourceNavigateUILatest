package com.thomsonreuters.codes.codesbench.quality.tests.hierarchy.publishing;

import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.CommonDataMocking;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.TargetDataMockingNew;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.datapod.HierarchyDatapodConfiguration;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.datapod.HierarchyDatapodObject;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets;
import com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.hierarchy.navigate.SiblingMetadataContextMenuElements;
import com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.hierarchy.navigate.TreeContextMenuElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.PRODLEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.CITELINE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.RISK;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.publishing.PublishingDatabaseUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

public class PublishingWorkflowsTests extends TestService
{
    String contentSetCodeOfFedRegsText = ContentSets.CODE_OF_FED_REGS_DEVELOPMENT.getName();
    String contentSetIowa = ContentSets.IOWA_DEVELOPMENT.getCode();
    String contentSetCodeOfFedRegs = ContentSets.CODE_OF_FED_REGS_DEVELOPMENT.getCode();
    String contentSetCrownDependencies = ContentSets.CROWN_DEPENDENCIES.getCode();
    Connection connection;

    HierarchyDatapodObject datapodObject;

    /**
     * STORY: HALCYONST-6023 <br>
     * SUMMARY: Verifies that the DLU Upload menu option and Publishing Workflows context menu option are
     * not displayed for users without SWAT access in a content set that is enabled for publishing <br>
     * USER: PROD <br>
     */
    @Test
    @IE_EDGE_MODE
    @PRODLEGAL
    @LOG
    public void nonSwatAccessEnabledContentSetTest()
    {
        datapodObject = TargetDataMockingNew.Iowa.Small.insert();
        String nodeUuid = datapodObject.getSections().get(0).getNodeUUID();

        connection = CommonDataMocking.connectToDatabase(environmentTag);
        PublishingDatabaseUtils.checkAndSetContentSetToBeEnabledForPublishing(contentSetIowa, connection);
        PublishingDatabaseUtils.checkAndRemovePublishApproverForContentSet(user().getUsername(), user().getFirstname(), user().getLastname(), contentSetIowa, connection);

        homePage().goToHomePage();
        loginPage().logIn();

        homeMenu().goToUserPreferencesSecurity();

        // Verify the user does not have swat access
        boolean userHasSwatAccess = userSecuritySettingsPage().doesUserHaveSwatAccess();
        Assertions.assertFalse(userHasSwatAccess, "User does have SWAT access and should not");

        // Verify DLU Upload does not appear as a sub context menu option
        boolean dluUploadIsDisplayed = publishingMenu().isDluUploadSubMenuOptionDisplayed();

        hierarchyMenu().goToNavigate();
        hierarchySearchPage().searchNodeUuid(nodeUuid);
        siblingMetadataPage().rightClickSelectedSiblingMetadata();

        // Verify Publishing Workflows does not appear as a context menu option in the sibling metadata pane
        boolean publishingWorkflowsIsDisplayedOnSiblingMetadataContextMenu = siblingMetadataContextMenu().isElementDisplayed(SiblingMetadataContextMenuElements.PUBLISHING_WORKFLOWS_XPATH);

        hierarchyTreePage().rightClickSelectedNavigationTreeNode();

        //verify Publishing Workflows does not appear as a context menu option in the tree
        boolean publishingWorkflowsIsDisplayedOnTreeContextMenu = hierarchyTreeContextMenu().isElementDisplayed(TreeContextMenuElements.PUBLISHING_WORKFLOWS_XPATH);

        Assertions.assertAll
        (
            () -> Assertions.assertFalse(dluUploadIsDisplayed, "DLU Upload appears as a menu option and should not"),
            () -> Assertions.assertFalse(publishingWorkflowsIsDisplayedOnSiblingMetadataContextMenu, "Publishing Workflows appears as a context menu option on the sibling metadata pane and should not"),
            () -> Assertions.assertFalse(publishingWorkflowsIsDisplayedOnTreeContextMenu, "Publishing Workflows appears as a context menu option on the tree and should not")
        );
    }

    /**
     * STORY: HALCYONST-6023 <br>
     * SUMMARY: Verifies that the DLU Upload menu option and Publishing Workflows context menu option
     * are displayed for users without SWAT access in a content set that is disabled for publishing <br>
     * USER: PROD <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void nonSwatAccessDisabledContentSetTest()
    {
        HierarchyDatapodConfiguration.getConfig().loadDefaultFedRegsConfig();
        datapodObject = TargetDataMockingNew.CodeOfFedRegs.Small.insert();
        String nodeUuid = datapodObject.getSections().get(0).getNodeUUID();

        connection = CommonDataMocking.connectToDatabase(environmentTag);
        boolean contentSetIsEnabledForPublishing = PublishingDatabaseUtils.isContentSetEnabledForPublishing(contentSetCodeOfFedRegs, connection);
        Assertions.assertFalse(contentSetIsEnabledForPublishing, "Content set is enabled for publishing and should not be.");

        homePage().goToHomePage();
        loginPage().logIn();

        homePage().setMyContentSet(contentSetCodeOfFedRegsText);

        //verify DLU Upload does appear as a sub context menu option
        boolean dluUploadIsDisplayed = publishingMenu().isDluUploadSubMenuOptionDisplayed();

        hierarchyMenu().goToNavigate();
        hierarchySearchPage().searchNodeUuid(nodeUuid);
        siblingMetadataPage().rightClickSelectedSiblingMetadata();

        //verify Publishing Workflows does not appear as a context menu option in the sibling metadata pane
        boolean publishingWorkflowsIsDisplayedOnSiblingMetadataContextMenu = siblingMetadataContextMenu().isElementDisplayed(SiblingMetadataContextMenuElements.PUBLISHING_WORKFLOWS_XPATH);

        siblingMetadataContextMenu().expandPublishingWorkflows();
        boolean bulkPublishByHierarchyIsDisplayedOnSiblingMetadataContextMenu = siblingMetadataContextMenu().isElementDisplayed(SiblingMetadataContextMenuElements.BULK_PUBLISH_BY_HIERARCHY_XPATH);
        boolean bulkPublishByVolumeIsDisplayedOnSiblingMetadataContextMenu = siblingMetadataContextMenu().isElementDisplayed(SiblingMetadataContextMenuElements.BULK_PUBLISH_BY_VOLUME_XPATH);

        hierarchyTreePage().rightClickSelectedNavigationTreeNode();

        //verify Publishing Workflows does not appear as a context menu option in the tree
        boolean publishingWorkflowsIsDisplayedOnTreeContextMenu = hierarchyTreeContextMenu().isElementDisplayed(TreeContextMenuElements.PUBLISHING_WORKFLOWS_XPATH);

        hierarchyTreeContextMenu().expandPublishingWorkflows();
        boolean bulkPublishByHierarchyIsDisplayedOnTreeContextMenu = hierarchyTreeContextMenu().isElementDisplayed(TreeContextMenuElements.BULK_PUBLISH_BY_HIERARCHY);
        boolean bulkPublishByVolumeIsDisplayedOnTreeContextMenu = hierarchyTreeContextMenu().isElementDisplayed(TreeContextMenuElements.BULK_PUBLISH_BY_VOLUME);

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(dluUploadIsDisplayed, "DLU Upload does not appear as a menu option and should"),
            () -> Assertions.assertTrue(publishingWorkflowsIsDisplayedOnSiblingMetadataContextMenu, "Publishing Workflows appears as a context menu option and should not"),
            () -> Assertions.assertTrue(bulkPublishByHierarchyIsDisplayedOnSiblingMetadataContextMenu, "Bulk Publish By Hierarchy does not appear as a subcontext menu option on the sibling metadata pane and should"),
            () -> Assertions.assertTrue(bulkPublishByVolumeIsDisplayedOnSiblingMetadataContextMenu, "Bulk Publish By Hierarchy does not appear as a subcontext menu option on the sibling metadata pane and should"),
            () -> Assertions.assertTrue(publishingWorkflowsIsDisplayedOnTreeContextMenu, "Publishing Workflows appears as a context menu option on the tree and should not"),
            () -> Assertions.assertTrue(bulkPublishByHierarchyIsDisplayedOnTreeContextMenu, "Bulk Publish By Hierarchy does not appear as a subcontext menu option on the tree and should"),
            () -> Assertions.assertTrue(bulkPublishByVolumeIsDisplayedOnTreeContextMenu, "Bulk Publish By Hierarchy does not appear as a subcontext menu option on the tree and should")
        );
    }

    /**
     * STORY: HALCYONST-6023 <br>
     * SUMMARY: Verifies that the DLU Upload menu option and Publishing Workflows context menu option and its submenus
     * are displayed for users with SWAT access in a content set that is enabled for publishing <br>
     * USER: CITELINE <br>
     */
    @Test
    @IE_EDGE_MODE
    @CITELINE
    @LOG
    public void swatAccessEnabledContentSetTest()
    {
        datapodObject = TargetDataMockingNew.Iowa.Small.insert();
        String nodeUuid = datapodObject.getSections().get(0).getNodeUUID();

        homePage().goToHomePage();
        loginPage().logIn();

        connection = CommonDataMocking.connectToDatabase(environmentTag);
        PublishingDatabaseUtils.checkAndSetContentSetToBeEnabledForPublishing(contentSetIowa, connection);

        homePage().goToHomePage();
        homeMenu().goToUserPreferencesSecurity();

        //verify the user does have swat access
        boolean userHasSwatAccess = userSecuritySettingsPage().doesUserHaveSwatAccess();
        Assertions.assertTrue(userHasSwatAccess, "User does not have SWAT access and should");

        //verify DLU Upload does appear as a sub context menu option
        boolean dluUploadIsDisplayed = publishingMenu().isDluUploadSubMenuOptionDisplayed();

        hierarchyMenu().goToNavigate();
        hierarchySearchPage().searchNodeUuid(nodeUuid);
        siblingMetadataPage().rightClickSelectedSiblingMetadata();

        //verify the context menu option and its sub menu options appear in the sibling metadata pane
        boolean publishingWorkflowsIsDisplayedOnSiblingMetadataContextMenu = siblingMetadataContextMenu().isElementDisplayed(SiblingMetadataContextMenuElements.PUBLISHING_WORKFLOWS_XPATH);

        siblingMetadataContextMenu().expandPublishingWorkflows();
        boolean bulkPublishByHierarchyIsDisplayedOnSiblingMetadataContextMenu = siblingMetadataContextMenu().isElementDisplayed(SiblingMetadataContextMenuElements.BULK_PUBLISH_BY_HIERARCHY_XPATH);
        boolean bulkPublishByVolumeIsDisplayedOnSiblingMetadataContextMenu = siblingMetadataContextMenu().isElementDisplayed(SiblingMetadataContextMenuElements.BULK_PUBLISH_BY_VOLUME_XPATH);

        hierarchyTreePage().rightClickSelectedNavigationTreeNode();

        //verify the context menu option and its submenu options do appear in the tree
        boolean publishingWorkflowsIsDisplayedOnTreeContextMenu = hierarchyTreeContextMenu().isElementDisplayed(TreeContextMenuElements.PUBLISHING_WORKFLOWS_XPATH);

        hierarchyTreeContextMenu().expandPublishingWorkflows();
        boolean bulkPublishByHierarchyIsDisplayedOnTreeContextMenu = hierarchyTreeContextMenu().isElementDisplayed(TreeContextMenuElements.BULK_PUBLISH_BY_HIERARCHY);
        boolean bulkPublishByVolumeIsDisplayedOnTreeContextMenu = hierarchyTreeContextMenu().isElementDisplayed(TreeContextMenuElements.BULK_PUBLISH_BY_VOLUME);

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(dluUploadIsDisplayed, "DLU Upload does not appear as a context menu option and should"),
            () -> Assertions.assertTrue(publishingWorkflowsIsDisplayedOnSiblingMetadataContextMenu, "Publishing Workflows does not appear as a context menu option and should"),
            () -> Assertions.assertTrue(bulkPublishByHierarchyIsDisplayedOnSiblingMetadataContextMenu, "Bulk Publish By Hierarchy does not appear as a subcontext menu option on the sibling metadata pane and should"),
            () -> Assertions.assertTrue(bulkPublishByVolumeIsDisplayedOnSiblingMetadataContextMenu, "Bulk Publish By Hierarchy does not appear as a subcontext menu option on the sibling metadata pane and should"),
            () -> Assertions.assertTrue(publishingWorkflowsIsDisplayedOnTreeContextMenu, "Publishing Workflows is not a context menu option in the Tree pane and should be"),
            () -> Assertions.assertTrue(bulkPublishByHierarchyIsDisplayedOnTreeContextMenu, "Bulk Publish By Hierarchy does not appear as a subcontext menu option on the tree and should"),
            () -> Assertions.assertTrue(bulkPublishByVolumeIsDisplayedOnTreeContextMenu, "Bulk Publish By Hierarchy does not appear as a subcontext menu option on the tree and should")
        );
    }

    /**
     * STORY: HALCYONST-6023 <br>
     * SUMMARY: Verifies that the DLU Upload menu option and Publishing Workflows context menu option and its submenus
     * are displayed for users with SWAT access in a content set that is disabled for publishing <br>
     * USER: CITELINE <br>
     */
    @Test
    @IE_EDGE_MODE
    @CITELINE
    @LOG
    public void swatAccessDisabledContentSetTest()
    {
        HierarchyDatapodConfiguration.getConfig().loadDefaultFedRegsConfig();
        datapodObject = TargetDataMockingNew.CodeOfFedRegs.Small.insert();
        String nodeUuid = datapodObject.getSections().get(0).getNodeUUID();

        connection = CommonDataMocking.connectToDatabase(environmentTag);
        boolean contentSetIsEnabledForPublishing = PublishingDatabaseUtils.isContentSetEnabledForPublishing(contentSetCodeOfFedRegs, connection);
        Assertions.assertFalse(contentSetIsEnabledForPublishing, "Content set is enabled for publishing and should not be.");

        homePage().goToHomePage();
        loginPage().logIn();

        homePage().setMyContentSet(contentSetCodeOfFedRegsText);

        hierarchyMenu().goToNavigate();
        hierarchySearchPage().searchNodeUuid(nodeUuid);
        siblingMetadataPage().rightClickSelectedSiblingMetadata();

        //verify the context menu option and its sub menu options appear in the sibling metadata pane
        boolean publishingWorkflowsIsDisplayedOnSiblingMetadataContextMenu = siblingMetadataContextMenu().isElementDisplayed(SiblingMetadataContextMenuElements.PUBLISHING_WORKFLOWS_XPATH);

        siblingMetadataContextMenu().expandPublishingWorkflows();
        boolean bulkPublishByHierarchyIsDisplayedOnSiblingMetadataContextMenu = siblingMetadataContextMenu().isElementDisplayed(SiblingMetadataContextMenuElements.BULK_PUBLISH_BY_HIERARCHY_XPATH);
        boolean bulkPublishByVolumeIsDisplayedOnSiblingMetadataContextMenu = siblingMetadataContextMenu().isElementDisplayed(SiblingMetadataContextMenuElements.BULK_PUBLISH_BY_VOLUME_XPATH);

        hierarchyTreePage().rightClickSelectedNavigationTreeNode();

        //verify the context menu option and its submenu options do appear in the tree
        boolean publishingWorkflowsIsDisplayedOnTreeContextMenu = hierarchyTreeContextMenu().isElementDisplayed(TreeContextMenuElements.PUBLISHING_WORKFLOWS_XPATH);

        hierarchyTreeContextMenu().expandPublishingWorkflows();
        boolean bulkPublishByHierarchyIsDisplayedOnTreeContextMenu = hierarchyTreeContextMenu().isElementDisplayed(TreeContextMenuElements.BULK_PUBLISH_BY_HIERARCHY);
        boolean bulkPublishByVolumeIsDisplayedOnTreeContextMenu = hierarchyTreeContextMenu().isElementDisplayed(TreeContextMenuElements.BULK_PUBLISH_BY_VOLUME);

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(publishingWorkflowsIsDisplayedOnSiblingMetadataContextMenu, "Publishing Workflows does not appear as a context menu option and should"),
            () -> Assertions.assertTrue(bulkPublishByHierarchyIsDisplayedOnSiblingMetadataContextMenu, "Bulk Publish By Hierarchy does not appear as a subcontext menu option on the sibling metadata pane and should"),
            () -> Assertions.assertTrue(bulkPublishByVolumeIsDisplayedOnSiblingMetadataContextMenu, "Bulk Publish By Hierarchy does not appear as a subcontext menu option on the sibling metadata pane and should"),
            () -> Assertions.assertTrue(publishingWorkflowsIsDisplayedOnTreeContextMenu, "Publishing Workflows does not appear as a context menu option in the Tree pane and should"),
            () -> Assertions.assertTrue(bulkPublishByHierarchyIsDisplayedOnTreeContextMenu, "Bulk Publish By Hierarchy does not appear as a subcontext menu option on the tree and should"),
            () -> Assertions.assertTrue(bulkPublishByVolumeIsDisplayedOnTreeContextMenu, "Bulk Publish By Hierarchy does not appear as a subcontext menu option on the tree and should")
        );
    }

    /**
     * STORY: HALCYONST-6023 <br>
     * SUMMARY: Verifies that the DLU Upload menu option and Publishing Workflows context menu option and its submenus
     * are displayed for the risk user in a content set that is disabled for publishing <br>
     * USER: RISK <br>
     */
    @Test
    @IE_EDGE_MODE
    @RISK
    @LOG
    public void riskUserDisabledContentSetTest()
    {
        HierarchyDatapodConfiguration.getConfig().loadDefaultRiskConfig();
        datapodObject = TargetDataMockingNew.CrownDependencies.Small.insert();
        String nodeUuid = datapodObject.getSections().get(0).getNodeUUID();
        String contentSet = "Crown Dependencies";

        connection = CommonDataMocking.connectToDatabase(environmentTag);
        boolean contentSetIsEnabledForPublishing = PublishingDatabaseUtils.isContentSetEnabledForPublishing(contentSetCrownDependencies, connection);
        Assertions.assertFalse(contentSetIsEnabledForPublishing, "Content set is enabled for publishing and should not be.");

        homePage().goToHomePage();
        loginPage().logIn();

        homePage().setMyContentSet(contentSet);

        hierarchyMenu().goToNavigate();
        hierarchySearchPage().searchNodeUuid(nodeUuid);
        siblingMetadataPage().rightClickSelectedSiblingMetadata();

        siblingMetadataContextMenu().expandPublishingWorkflows();
        boolean bulkPublishByHierarchyIsDisplayedOnSiblingMetadataContextMenu = siblingMetadataContextMenu().isElementDisplayed(SiblingMetadataContextMenuElements.BULK_PUBLISH_BY_HIERARCHY_XPATH);
        boolean bulkPublishRulebookByHierarchyIsDisplayedOnSiblingMetadataContextMenu = siblingMetadataContextMenu().isElementDisplayed(SiblingMetadataContextMenuElements.BULK_PUBLISH_RULEBOOK_BY_HIERARCHY_XPATH);
        boolean bulkPublishByVolumeIsDisplayedOnSiblingMetadataContextMenu = siblingMetadataContextMenu().isElementDisplayed(SiblingMetadataContextMenuElements.BULK_PUBLISH_BY_VOLUME_XPATH);

        hierarchyTreePage().rightClickSelectedNavigationTreeNode();

        hierarchyTreeContextMenu().expandPublishingWorkflows();
        boolean bulkPublishByHierarchyIsDisplayedOnTreeContextMenu = hierarchyTreeContextMenu().isElementDisplayed(TreeContextMenuElements.BULK_PUBLISH_BY_HIERARCHY);
        boolean bulkPublishRulebookByHierarchyIsDisplayedOnTreeContextMenu = hierarchyTreeContextMenu().isElementDisplayed(TreeContextMenuElements.BULK_PUBLISH_RULEBOOK_BY_HIERARCHY);
        boolean bulkPublishByVolumeIsDisplayedOnTreeContextMenu = hierarchyTreeContextMenu().isElementDisplayed(TreeContextMenuElements.BULK_PUBLISH_BY_VOLUME);

        Assertions.assertAll
        (
            () -> Assertions.assertFalse(bulkPublishByHierarchyIsDisplayedOnSiblingMetadataContextMenu, "Bulk Publish By Hierarchy does appear as a subcontext menu option on the sibling metadata pane and should not"),
            () -> Assertions.assertTrue(bulkPublishRulebookByHierarchyIsDisplayedOnSiblingMetadataContextMenu, "Bulk Publish Rulebook By Hierarchy does not appear as a subcontext menu option on the sibling metadata pane and should"),
            () -> Assertions.assertFalse(bulkPublishByVolumeIsDisplayedOnSiblingMetadataContextMenu, "Bulk Publish By Hierarchy does appear as a subcontext menu option on the sibling metadata pane and should not"),
            () -> Assertions.assertFalse(bulkPublishByHierarchyIsDisplayedOnTreeContextMenu, "Bulk Publish By Hierarchy does appear as a subcontext menu option on the tree and should not"),
            () -> Assertions.assertTrue(bulkPublishRulebookByHierarchyIsDisplayedOnTreeContextMenu, "Bulk Publish Rulebook By Hierarchy does not appear as a subcontext menu option on the tree and should"),
            () -> Assertions.assertFalse(bulkPublishByVolumeIsDisplayedOnTreeContextMenu, "Bulk Publish By Hierarchy does appear as a subcontext menu option on the tree and should not")
        );
    }

    /**
     * STORY: HALCYONST-8918 <br>
     * SUMMARY: Verifies that the Citeline user has both SWAT and Paratech access. Once verified, navigates to pub navigate, right
     * clicks a section node, and assures that Publishing Workflows and its submenus are displayed as options in the context
     * menu on the sibling metadata pane and the hierarchy tree <br>
     * USER: CITELINE <br>
     */
    @Test
    @IE_EDGE_MODE
    @CITELINE
    @LOG
    public void publishingEnabledSWATParatechUserTest()
    {
        HierarchyDatapodConfiguration.getConfig().setInsertInPubNav(true);
        datapodObject = TargetDataMockingNew.Iowa.Small.insert();
        String nodeUuid = datapodObject.getSections().get(0).getNodeUUID();

        connection = CommonDataMocking.connectToDatabase(environmentTag);
        PublishingDatabaseUtils.checkAndSetContentSetToBeEnabledForPublishing(contentSetIowa, connection);

        homePage().goToHomePage();
        loginPage().logIn();

        homeMenu().goToUserPreferencesSecurity();

        //verify the user has swat access
        boolean userHasSwatAccess = userSecuritySettingsPage().doesUserHaveSwatAccess();
        Assertions.assertTrue(userHasSwatAccess, "User does not have SWAT access and should");

        //verify the user has paratech access
        boolean userHasParatechAccess = userSecuritySettingsPage().doesUserHaveParatechAccess();
        Assertions.assertTrue(userHasParatechAccess, "User does not have Paratech access and should");

        hierarchyMenu().goToPubNavigate();
        hierarchySearchPage().searchNodeUuid(nodeUuid);
        siblingMetadataPage().rightClickSelectedSiblingMetadata();

        //verify the context menu option and its submenu options do appear in the sibling metadata pane
        boolean publishingWorkflowsIsDisplayedOnSiblingMetadataContextMenu = siblingMetadataContextMenu().isElementDisplayed(SiblingMetadataContextMenuElements.PUBLISHING_WORKFLOWS_XPATH);

        siblingMetadataContextMenu().expandPublishingWorkflowsPublishingDisabled();
        boolean outputRepublishRulebookByHierarchyIsDisplayedOnSiblingMetadataContextMenu = siblingMetadataContextMenu().isElementDisplayed(SiblingMetadataContextMenuElements.OUTPUT_REPUBLISH_RULEBOOK_BY_HIERARCHY_XPATH);
        boolean outputRepublishByHierarchyIsDisplayedOnSiblingMetadataContextMenu = siblingMetadataContextMenu().isElementDisplayed(SiblingMetadataContextMenuElements.OUTPUT_REPUBLISH_BY_HIERARCHY_XPATH);
        boolean outputRepublishByVolumeIsDisplayedOnSiblingMetadataContextMenu = siblingMetadataContextMenu().isElementDisplayed(SiblingMetadataContextMenuElements.OUTPUT_REPUBLISH_BY_VOLUME_XPATH);
        boolean westMateTocIsDisplayedOnSiblingMetadataContextMenu = siblingMetadataContextMenu().isElementDisplayed(SiblingMetadataContextMenuElements.WESTMATE_TOC_XPATH);
        boolean westMateHighTocIsDisplayedOnSiblingMetadataContextMenu = siblingMetadataContextMenu().isElementDisplayed(SiblingMetadataContextMenuElements.WESTMATE_HIGH_TOC_XPATH);

        hierarchyTreePage().rightClickSelectedNavigationTreeNode();

        //verify the context menu option and its submenu options do appear in the tree
        boolean publishingWorkflowsIsDisplayedOnTreeContextMenu = hierarchyTreeContextMenu().isElementDisplayed(TreeContextMenuElements.PUBLISHING_WORKFLOWS_XPATH);

        hierarchyTreeContextMenu().expandPublishingWorkflowsPublishingDisabled();
        boolean outputRepublishRulebookByHierarchyIsDisplayedOnTreeContextMenu = hierarchyTreeContextMenu().isElementDisplayed(TreeContextMenuElements.OUTPUT_REPUBLISH_RULEBOOK_BY_HIERARCHY_XPATH);
        boolean outputRepublishByHierarchyIsDisplayedOnTreeContextMenu = hierarchyTreeContextMenu().isElementDisplayed(TreeContextMenuElements.OUTPUT_REPUBLISH_BY_HIERARCHY_XPATH);
        boolean outputRepublishByVolumeIsDisplayedOnTreeContextMenu = hierarchyTreeContextMenu().isElementDisplayed(TreeContextMenuElements.OUTPUT_REPUBLISH_BY_VOLUME_XPATH);
        boolean westMateTocIsDisplayedOnTreeContextMenu = hierarchyTreeContextMenu().isElementDisplayed(TreeContextMenuElements.WESTMATE_TOC_XPATH);
        boolean westMateHighTocIsDisplayedOnTreeContextMenu = hierarchyTreeContextMenu().isElementDisplayed(TreeContextMenuElements.WESTMATE_HIGH_TOC_XPATH);

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(publishingWorkflowsIsDisplayedOnSiblingMetadataContextMenu, "Publishing Workflows is not a context menu option in the Sibling Metadata pane and should be"),
            () -> Assertions.assertTrue(outputRepublishRulebookByHierarchyIsDisplayedOnSiblingMetadataContextMenu, "Output Republish Rulebook By Hierarchy is not a sub context menu option in the Sibling Metadata pane and should be"),
            () -> Assertions.assertTrue(outputRepublishByHierarchyIsDisplayedOnSiblingMetadataContextMenu, "Output Republish By Hierarchy is not a sub context menu option in the Sibling Metadata pane and should be"),
            () -> Assertions.assertTrue(outputRepublishByVolumeIsDisplayedOnSiblingMetadataContextMenu, "Output Republish Rulebook By Volume is not a sub context menu option in the Sibling Metadata pane and should be"),
            () -> Assertions.assertTrue(westMateTocIsDisplayedOnSiblingMetadataContextMenu, "WestMate TOC is not a sub context menu option in the Sibling Metadata pane and should be"),
            () -> Assertions.assertTrue(westMateHighTocIsDisplayedOnSiblingMetadataContextMenu, "WestMate High TOC is not a sub context menu option in the Sibling Metadata pane and should be"),
            () -> Assertions.assertTrue(publishingWorkflowsIsDisplayedOnTreeContextMenu, "Publishing Workflows is not a context menu option in the Tree pane and should be"),
            () -> Assertions.assertTrue(outputRepublishRulebookByHierarchyIsDisplayedOnTreeContextMenu, "Output Republish Rulebook By Hierarchy is not a sub context menu option in the Tree pane and should be"),
            () -> Assertions.assertTrue(outputRepublishByHierarchyIsDisplayedOnTreeContextMenu, "Output Republish By Hierarchy is not a sub context menu option in the Tree pane and should be"),
            () -> Assertions.assertTrue(outputRepublishByVolumeIsDisplayedOnTreeContextMenu, "Output Republish Rulebook By Volume is not a sub context menu option in the Tree pane and should be"),
            () -> Assertions.assertTrue(westMateTocIsDisplayedOnTreeContextMenu, "WestMate TOC is not a sub context menu option in the Tree pane and should be"),
            () -> Assertions.assertTrue(westMateHighTocIsDisplayedOnTreeContextMenu, "WestMate High TOC is not a sub context menu option in the Tree pane and should be")
        );
    }

    /**
     * STORY: HALCYONST-8918 <br>
     * SUMMARY: Verifies that the Prod User does not have SWAT or Paratech access. Once verified, navigates to Pub Navigate, right
     * clicks a section node, and assures that Publishing Workflows and its submenus are not displayed as options in the context
     * menu on the sibling metadata grid and the hierarchy tree <br>
     * USER: PROD <br>
     */
    @Test
    @IE_EDGE_MODE
    @PRODLEGAL
    @LOG
    public void publishingEnabledNonSwatNonParatechNotPublishApproverTest()
    {
        HierarchyDatapodConfiguration.getConfig().setInsertInPubNav(true);
        datapodObject = TargetDataMockingNew.Iowa.Small.insert();
        String nodeUuid = datapodObject.getSections().get(0).getNodeUUID();

        connection = CommonDataMocking.connectToDatabase(environmentTag);
        PublishingDatabaseUtils.checkAndSetContentSetToBeEnabledForPublishing(contentSetIowa, connection);
        PublishingDatabaseUtils.checkAndRemovePublishApproverForContentSet(user().getUsername(), user().getFirstname(), user().getLastname(), contentSetIowa, connection);

        homePage().goToHomePage();
        loginPage().logIn();

        homeMenu().goToUserPreferencesSecurity();

        //verify the user does not have swat access
        boolean userHasSwatAccess = userSecuritySettingsPage().doesUserHaveSwatAccess();
        Assertions.assertFalse(userHasSwatAccess, "User does have SWAT access and should not");

        //verify the user does not have paratech access
        boolean userHasParatechAccess = userSecuritySettingsPage().doesUserHaveParatechAccess();
        Assertions.assertFalse(userHasParatechAccess, "User does have Paratech access and should not");

        hierarchyMenu().goToPubNavigate();
        hierarchySearchPage().searchNodeUuid(nodeUuid);
        siblingMetadataPage().rightClickSelectedSiblingMetadata();

        //verify the context menu option and its submenu options do not appear in the sibling metadata pane
        boolean publishingWorkflowsIsDisplayedOnSiblingMetadataContextMenu = siblingMetadataContextMenu().isElementDisplayed(SiblingMetadataContextMenuElements.PUBLISHING_WORKFLOWS_XPATH);

        hierarchyTreePage().rightClickSelectedNavigationTreeNode();

        //verify the context menu option and its submenu options do not appear in the tree
        boolean publishingWorkflowsIsDisplayedOnTreeContextMenu = hierarchyTreeContextMenu().isElementDisplayed(TreeContextMenuElements.PUBLISHING_WORKFLOWS_XPATH);

        Assertions.assertAll
        (
            () -> Assertions.assertFalse(publishingWorkflowsIsDisplayedOnSiblingMetadataContextMenu, "Publishing Workflows is not a context menu option in the Sibling Metadata pane and should be"),
            () -> Assertions.assertFalse(publishingWorkflowsIsDisplayedOnTreeContextMenu, "Publishing Workflows is not a context menu option in the Tree pane and should be")
        );
    }

    /**
     * STORY: HALCYONST-8918 <br>
     * SUMMARY: Verifies that the Prod User does not have SWAT or Paratech access. Once verified, navigates to Content Preferences
     * and verifies that the user is on the list of Publish Approvers. Finally, navigates to Pub Navigate, right clicks a section node,
     * and assures that Publishing Workflows and its submenus are not displayed as options in the context menu on the sibling metadata grid
     * and the hierarchy tree <br>
     * USER: PROD <br>
     */
    @Test
    @IE_EDGE_MODE
    @PRODLEGAL
    @LOG
    public void publishingEnabledNonSwatNonParatechPublishApproverTest()
    {
        HierarchyDatapodConfiguration.getConfig().setInsertInPubNav(true);
        datapodObject = TargetDataMockingNew.Iowa.Small.insert();
        String nodeUuid = datapodObject.getSections().get(0).getNodeUUID();

        connection = CommonDataMocking.connectToDatabase(environmentTag);
        PublishingDatabaseUtils.checkAndSetContentSetToBeEnabledForPublishing(contentSetIowa, connection);
        PublishingDatabaseUtils.checkAndAddPublishApproverForContentSet(user().getUsername(), user().getFirstname(), user().getLastname(), contentSetIowa, connection);

        homePage().goToHomePage();
        loginPage().logIn();

        homeMenu().goToUserPreferencesSecurity();

        //verify the user does not have swat access
        boolean userHasSwatAccess = userSecuritySettingsPage().doesUserHaveSwatAccess();
        Assertions.assertFalse(userHasSwatAccess, "User does not have SWAT access and should");

        //verify the user does not have paratech access
        boolean userHasParatechAccess = userSecuritySettingsPage().doesUserHaveParatechAccess();
        Assertions.assertFalse(userHasParatechAccess, "User does not have Paratech access and should");

        hierarchyMenu().goToPubNavigate();
        hierarchySearchPage().searchNodeUuid(nodeUuid);
        siblingMetadataPage().rightClickSelectedSiblingMetadata();

        //verify the context menu option and its submenu options do appear in the sibling metadata pane
        boolean publishingWorkflowsIsDisplayedOnSiblingMetadataContextMenu = siblingMetadataContextMenu().isElementDisplayed(SiblingMetadataContextMenuElements.PUBLISHING_WORKFLOWS_XPATH);

        siblingMetadataContextMenu().expandPublishingWorkflowsPublishingDisabled();
        boolean outputRepublishRulebookByHierarchyIsDisplayedOnSiblingMetadataContextMenu = siblingMetadataContextMenu().isElementDisplayed(SiblingMetadataContextMenuElements.OUTPUT_REPUBLISH_RULEBOOK_BY_HIERARCHY_XPATH);
        boolean outputRepublishByHierarchyIsDisplayedOnSiblingMetadataContextMenu = siblingMetadataContextMenu().isElementDisplayed(SiblingMetadataContextMenuElements.OUTPUT_REPUBLISH_BY_HIERARCHY_XPATH);
        boolean outputRepublishByVolumeIsDisplayedOnSiblingMetadataContextMenu = siblingMetadataContextMenu().isElementDisplayed(SiblingMetadataContextMenuElements.OUTPUT_REPUBLISH_BY_VOLUME_XPATH);
        boolean westMateTocIsDisplayedOnSiblingMetadataContextMenu = siblingMetadataContextMenu().isElementDisplayed(SiblingMetadataContextMenuElements.WESTMATE_TOC_XPATH);
        boolean westMateHighTocIsDisplayedOnSiblingMetadataContextMenu = siblingMetadataContextMenu().isElementDisplayed(SiblingMetadataContextMenuElements.WESTMATE_HIGH_TOC_XPATH);

        hierarchyTreePage().rightClickSelectedNavigationTreeNode();

        //verify the context menu option and its submenu options do appear in the tree
        boolean publishingWorkflowsIsDisplayedOnTreeContextMenu = hierarchyTreeContextMenu().isElementDisplayed(TreeContextMenuElements.PUBLISHING_WORKFLOWS_XPATH);

        hierarchyTreeContextMenu().expandPublishingWorkflowsPublishingDisabled();

        boolean outputRepublishRulebookByHierarchyIsDisplayedOnTreeContextMenu = hierarchyTreeContextMenu().isElementDisplayed(TreeContextMenuElements.OUTPUT_REPUBLISH_RULEBOOK_BY_HIERARCHY_XPATH);
        boolean outputRepublishByHierarchyIsDisplayedOnTreeContextMenu = hierarchyTreeContextMenu().isElementDisplayed(TreeContextMenuElements.OUTPUT_REPUBLISH_BY_HIERARCHY_XPATH);
        boolean outputRepublishByVolumeIsDisplayedOnTreeContextMenu = hierarchyTreeContextMenu().isElementDisplayed(TreeContextMenuElements.OUTPUT_REPUBLISH_BY_VOLUME_XPATH);
        boolean westMateTocIsDisplayedOnTreeContextMenu = hierarchyTreeContextMenu().isElementDisplayed(TreeContextMenuElements.WESTMATE_TOC_XPATH);
        boolean westMateHighTocIsDisplayedOnTreeContextMenu = hierarchyTreeContextMenu().isElementDisplayed(TreeContextMenuElements.WESTMATE_HIGH_TOC_XPATH);

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(publishingWorkflowsIsDisplayedOnSiblingMetadataContextMenu, "Publishing Workflows is not a context menu option in the Sibling Metadata pane and should be"),
            () -> Assertions.assertTrue(outputRepublishRulebookByHierarchyIsDisplayedOnSiblingMetadataContextMenu, "Output Republish Rulebook By Hierarchy is not a sub context menu option in the Sibling Metadata pane and should be"),
            () -> Assertions.assertTrue(outputRepublishByHierarchyIsDisplayedOnSiblingMetadataContextMenu, "Output Republish By Hierarchy is not a sub context menu option in the Sibling Metadata pane and should be"),
            () -> Assertions.assertTrue(outputRepublishByVolumeIsDisplayedOnSiblingMetadataContextMenu, "Output Republish Rulebook By Volume is not a sub context menu option in the Sibling Metadata pane and should be"),
            () -> Assertions.assertTrue(westMateTocIsDisplayedOnSiblingMetadataContextMenu, "WestMate TOC is not a sub context menu option in the Sibling Metadata pane and should be"),
            () -> Assertions.assertTrue(westMateHighTocIsDisplayedOnSiblingMetadataContextMenu, "WestMate High TOC is not a sub context menu option in the Sibling Metadata pane and should be"),
            () -> Assertions.assertTrue(publishingWorkflowsIsDisplayedOnTreeContextMenu, "Publishing Workflows is not a context menu option in the Tree pane and should be"),
            () -> Assertions.assertTrue(outputRepublishRulebookByHierarchyIsDisplayedOnTreeContextMenu, "Output Republish Rulebook By Hierarchy is not a sub context menu option in the Tree pane and should be"),
            () -> Assertions.assertTrue(outputRepublishByHierarchyIsDisplayedOnTreeContextMenu, "Output Republish By Hierarchy is not a sub context menu option in the Tree pane and should be"),
            () -> Assertions.assertTrue(outputRepublishByVolumeIsDisplayedOnTreeContextMenu, "Output Republish Rulebook By Volume is not a sub context menu option in the Tree pane and should be"),
            () -> Assertions.assertTrue(westMateTocIsDisplayedOnTreeContextMenu, "WestMate TOC is not a sub context menu option in the Tree pane and should be"),
            () -> Assertions.assertTrue(westMateHighTocIsDisplayedOnTreeContextMenu, "WestMate High TOC is not a sub context menu option in the Tree pane and should be")
        );
    }

    /**
     * STORY: HALCYONST-8918 <br>
     * SUMMARY: Verifies that the Citeline user has both SWAT and Paratech access. Once verified, navigates to pub navigate, right
     * clicks a section node, and assures that Publishing Workflows and its submenus are displayed as options in the context
     * menu on the sibling metadata grid and the hierarchy tree <br>
     * USER: CITELINE <br>
     */
    @Test
    @IE_EDGE_MODE
    @CITELINE
    @LOG
    public void publishingDisabledSWATParatechUserTest()
    {
        HierarchyDatapodConfiguration.getConfig().loadDefaultFedRegsConfig();
        HierarchyDatapodConfiguration.getConfig().setInsertInPubNav(true);
        datapodObject = TargetDataMockingNew.CodeOfFedRegs.Small.insert();
        String nodeUuid = datapodObject.getSections().get(0).getNodeUUID();

        connection = CommonDataMocking.connectToDatabase(environmentTag);
        boolean contentSetIsEnabledForPublishing = PublishingDatabaseUtils.isContentSetEnabledForPublishing(contentSetCodeOfFedRegs, connection);
        Assertions.assertFalse(contentSetIsEnabledForPublishing, "Content set is enabled for publishing and should not be.");

        homePage().goToHomePage();
        loginPage().logIn();

        homePage().setMyContentSet(contentSetCodeOfFedRegsText);

        homeMenu().goToUserPreferencesSecurity();

        //verify the user has swat access
        boolean userHasSwatAccess = userSecuritySettingsPage().doesUserHaveSwatAccess();
        Assertions.assertTrue(userHasSwatAccess, "User does not have SWAT access and should");

        //verify the user has paratech access
        boolean userHasParatechAccess = userSecuritySettingsPage().doesUserHaveParatechAccess();
        Assertions.assertTrue(userHasParatechAccess, "User does not have Paratech access and should");

        hierarchyMenu().goToPubNavigate();
        hierarchySearchPage().searchNodeUuid(nodeUuid);
        siblingMetadataPage().rightClickSelectedSiblingMetadata();

        //verify the context menu option and its submenu options appear in the sibling metadata pane
        boolean publishingWorkflowsIsDisplayedOnSiblingMetadataContextMenu = siblingMetadataContextMenu().isElementDisplayed(SiblingMetadataContextMenuElements.PUBLISHING_WORKFLOWS_XPATH);

        siblingMetadataContextMenu().expandPublishingWorkflowsPublishingDisabled();
        boolean outputRepublishRulebookByHierarchyIsDisplayedOnSiblingMetadataContextMenu = siblingMetadataContextMenu().isElementDisplayed(SiblingMetadataContextMenuElements.OUTPUT_REPUBLISH_RULEBOOK_BY_HIERARCHY_XPATH);
        boolean outputRepublishByHierarchyIsDisplayedOnSiblingMetadataContextMenu = siblingMetadataContextMenu().isElementDisplayed(SiblingMetadataContextMenuElements.OUTPUT_REPUBLISH_BY_HIERARCHY_XPATH);
        boolean outputRepublishByVolumeIsDisplayedOnSiblingMetadataContextMenu = siblingMetadataContextMenu().isElementDisplayed(SiblingMetadataContextMenuElements.OUTPUT_REPUBLISH_BY_VOLUME_XPATH);
        boolean westMateTocIsDisplayedOnSiblingMetadataContextMenu = siblingMetadataContextMenu().isElementDisplayed(SiblingMetadataContextMenuElements.WESTMATE_TOC_XPATH);
        boolean westMateHighTocIsDisplayedOnSiblingMetadataContextMenu = siblingMetadataContextMenu().isElementDisplayed(SiblingMetadataContextMenuElements.WESTMATE_HIGH_TOC_XPATH);

        hierarchyTreePage().rightClickSelectedNavigationTreeNode();

        //verify the context menu option and its submenu options appear in the tree
        boolean publishingWorkflowsIsDisplayedOnTreeContextMenu = hierarchyTreeContextMenu().isElementDisplayed(TreeContextMenuElements.PUBLISHING_WORKFLOWS_XPATH);

        hierarchyTreeContextMenu().expandPublishingWorkflowsPublishingDisabled();
        boolean outputRepublishRulebookByHierarchyIsDisplayedOnTreeContextMenu = hierarchyTreeContextMenu().isElementDisplayed(TreeContextMenuElements.OUTPUT_REPUBLISH_RULEBOOK_BY_HIERARCHY_XPATH);
        boolean outputRepublishByHierarchyIsDisplayedOnTreeContextMenu = hierarchyTreeContextMenu().isElementDisplayed(TreeContextMenuElements.OUTPUT_REPUBLISH_BY_HIERARCHY_XPATH);
        boolean outputRepublishByVolumeIsDisplayedOnTreeContextMenu = hierarchyTreeContextMenu().isElementDisplayed(TreeContextMenuElements.OUTPUT_REPUBLISH_BY_VOLUME_XPATH);
        boolean westMateTocIsDisplayedOnTreeContextMenu = hierarchyTreeContextMenu().isElementDisplayed(TreeContextMenuElements.WESTMATE_TOC_XPATH);
        boolean westMateHighTocIsDisplayedOnTreeContextMenu = hierarchyTreeContextMenu().isElementDisplayed(TreeContextMenuElements.WESTMATE_HIGH_TOC_XPATH);

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(publishingWorkflowsIsDisplayedOnSiblingMetadataContextMenu, "Publishing Workflows is not a context menu option in the Sibling Metadata pane and should be"),
            () -> Assertions.assertTrue(outputRepublishRulebookByHierarchyIsDisplayedOnSiblingMetadataContextMenu, "Output Republish Rulebook By Hierarchy is not a sub context menu option in the Sibling Metadata pane and should be"),
            () -> Assertions.assertTrue(outputRepublishByHierarchyIsDisplayedOnSiblingMetadataContextMenu, "Output Republish By Hierarchy is not a sub context menu option in the Sibling Metadata pane and should be"),
            () -> Assertions.assertTrue(outputRepublishByVolumeIsDisplayedOnSiblingMetadataContextMenu, "Output Republish Rulebook By Volume is not a sub context menu option in the Sibling Metadata pane and should be"),
            () -> Assertions.assertTrue(westMateTocIsDisplayedOnSiblingMetadataContextMenu, "WestMate TOC is not a sub context menu option in the Sibling Metadata pane and should be"),
            () -> Assertions.assertTrue(westMateHighTocIsDisplayedOnSiblingMetadataContextMenu, "WestMate High TOC is not a sub context menu option in the Sibling Metadata pane and should be"),
            () -> Assertions.assertTrue(publishingWorkflowsIsDisplayedOnTreeContextMenu, "Publishing Workflows is not a context menu option in the Tree pane and should be"),
            () -> Assertions.assertTrue(outputRepublishRulebookByHierarchyIsDisplayedOnTreeContextMenu, "Output Republish Rulebook By Hierarchy is not a sub context menu option in the Tree pane and should be"),
            () -> Assertions.assertTrue(outputRepublishByHierarchyIsDisplayedOnTreeContextMenu, "Output Republish By Hierarchy is not a sub context menu option in the Tree pane and should be"),
            () -> Assertions.assertTrue(outputRepublishByVolumeIsDisplayedOnTreeContextMenu, "Output Republish Rulebook By Volume is not a sub context menu option in the Tree pane and should be"),
            () -> Assertions.assertTrue(westMateTocIsDisplayedOnTreeContextMenu, "WestMate TOC is not a sub context menu option in the Tree pane and should be"),
            () -> Assertions.assertTrue(westMateHighTocIsDisplayedOnTreeContextMenu, "WestMate High TOC is not a sub context menu option in the Tree pane and should be")
        );
    }

    /**
     * STORY: HALCYONST-8918 <br>
     * SUMMARY: Verifies that the Legal User does not have SWAT or Paratech access. Once verified, navigates to Pub Navigate, right
     * clicks a section node, and assures that Publishing Workflows and its submenus are displayed as options in the context
     * menu on the sibling metadata grid and the hierarchy tree <br>
     * USER: LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void publishingDisabledNonSWATNonParatechUserTest()
    {
        HierarchyDatapodConfiguration.getConfig().loadDefaultFedRegsConfig();
        HierarchyDatapodConfiguration.getConfig().setInsertInPubNav(true);
        datapodObject = TargetDataMockingNew.CodeOfFedRegs.Small.insert();
        String nodeUuid = datapodObject.getSections().get(0).getNodeUUID();

        connection = CommonDataMocking.connectToDatabase(environmentTag);
        boolean contentSetIsEnabledForPublishing = PublishingDatabaseUtils.checkAndSetContentSetToBeEnabledForPublishing(contentSetCodeOfFedRegs, connection);
        Assertions.assertFalse(contentSetIsEnabledForPublishing, "Content set is enabled for publishing and should not be.");

        homePage().goToHomePage();
        loginPage().logIn();

        homePage().setMyContentSet(contentSetCodeOfFedRegsText);

        homeMenu().goToUserPreferencesSecurity();

        //verify the user does not have swat access
        boolean userHasSwatAccess = userSecuritySettingsPage().doesUserHaveSwatAccess();
        Assertions.assertFalse(userHasSwatAccess, "User does not have SWAT access and should");

        //verify the user does not have paratech access
        boolean userHasParatechAccess = userSecuritySettingsPage().doesUserHaveParatechAccess();
        Assertions.assertFalse(userHasParatechAccess, "User does not have Paratech access and should");

        hierarchyMenu().goToPubNavigate();
        hierarchySearchPage().searchNodeUuid(nodeUuid);
        siblingMetadataPage().rightClickSelectedSiblingMetadata();

        //verify the context menu option and its submenu options appear in the sibling metadata pane
        boolean publishingWorkflowsIsDisplayedOnSiblingMetadataContextMenu = siblingMetadataContextMenu().isElementDisplayed(SiblingMetadataContextMenuElements.PUBLISHING_WORKFLOWS_XPATH);

        siblingMetadataContextMenu().expandPublishingWorkflowsPublishingDisabled();
        boolean outputRepublishRulebookByHierarchyIsDisplayedOnSiblingMetadataContextMenu = siblingMetadataContextMenu().isElementDisplayed(SiblingMetadataContextMenuElements.OUTPUT_REPUBLISH_RULEBOOK_BY_HIERARCHY_XPATH);
        boolean outputRepublishByHierarchyIsDisplayedOnSiblingMetadataContextMenu = siblingMetadataContextMenu().isElementDisplayed(SiblingMetadataContextMenuElements.OUTPUT_REPUBLISH_BY_HIERARCHY_XPATH);
        boolean outputRepublishByVolumeIsDisplayedOnSiblingMetadataContextMenu = siblingMetadataContextMenu().isElementDisplayed(SiblingMetadataContextMenuElements.OUTPUT_REPUBLISH_BY_VOLUME_XPATH);
        boolean westMateTocIsDisplayedOnSiblingMetadataContextMenu = siblingMetadataContextMenu().isElementDisplayed(SiblingMetadataContextMenuElements.WESTMATE_TOC_XPATH);
        boolean westMateHighTocIsDisplayedOnSiblingMetadataContextMenu = siblingMetadataContextMenu().isElementDisplayed(SiblingMetadataContextMenuElements.WESTMATE_HIGH_TOC_XPATH);

        hierarchyTreePage().rightClickSelectedNavigationTreeNode();

        //verify the context menu option and its submenu options appear in the tree
        boolean publishingWorkflowsIsDisplayedOnTreeContextMenu = hierarchyTreeContextMenu().isElementDisplayed(TreeContextMenuElements.PUBLISHING_WORKFLOWS_XPATH);

        hierarchyTreeContextMenu().expandPublishingWorkflowsPublishingDisabled();
        boolean outputRepublishRulebookByHierarchyIsDisplayedOnTreeContextMenu = hierarchyTreeContextMenu().isElementDisplayed(TreeContextMenuElements.OUTPUT_REPUBLISH_RULEBOOK_BY_HIERARCHY_XPATH);
        boolean outputRepublishByHierarchyIsDisplayedOnTreeContextMenu = hierarchyTreeContextMenu().isElementDisplayed(TreeContextMenuElements.OUTPUT_REPUBLISH_BY_HIERARCHY_XPATH);
        boolean outputRepublishByVolumeIsDisplayedOnTreeContextMenu = hierarchyTreeContextMenu().isElementDisplayed(TreeContextMenuElements.OUTPUT_REPUBLISH_BY_VOLUME_XPATH);
        boolean westMateTocIsDisplayedOnTreeContextMenu = hierarchyTreeContextMenu().isElementDisplayed(TreeContextMenuElements.WESTMATE_TOC_XPATH);
        boolean westMateHighTocIsDisplayedOnTreeContextMenu = hierarchyTreeContextMenu().isElementDisplayed(TreeContextMenuElements.WESTMATE_HIGH_TOC_XPATH);

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(publishingWorkflowsIsDisplayedOnSiblingMetadataContextMenu, "Publishing Workflows is not a context menu option in the Sibling Metadata pane and should be"),
            () -> Assertions.assertTrue(outputRepublishRulebookByHierarchyIsDisplayedOnSiblingMetadataContextMenu, "Output Republish Rulebook By Hierarchy is not a sub context menu option in the Sibling Metadata pane and should be"),
            () -> Assertions.assertTrue(outputRepublishByHierarchyIsDisplayedOnSiblingMetadataContextMenu, "Output Republish By Hierarchy is not a sub context menu option in the Sibling Metadata pane and should be"),
            () -> Assertions.assertTrue(outputRepublishByVolumeIsDisplayedOnSiblingMetadataContextMenu, "Output Republish Rulebook By Volume is not a sub context menu option in the Sibling Metadata pane and should be"),
            () -> Assertions.assertTrue(westMateTocIsDisplayedOnSiblingMetadataContextMenu, "WestMate TOC is not a sub context menu option in the Sibling Metadata pane and should be"),
            () -> Assertions.assertTrue(westMateHighTocIsDisplayedOnSiblingMetadataContextMenu, "WestMate High TOC is not a sub context menu option in the Sibling Metadata pane and should be"),
            () -> Assertions.assertTrue(publishingWorkflowsIsDisplayedOnTreeContextMenu, "Publishing Workflows is not a context menu option in the Tree pane and should be"),
            () -> Assertions.assertTrue(outputRepublishRulebookByHierarchyIsDisplayedOnTreeContextMenu, "Output Republish Rulebook By Hierarchy is not a sub context menu option in the Tree pane and should be"),
            () -> Assertions.assertTrue(outputRepublishByHierarchyIsDisplayedOnTreeContextMenu, "Output Republish By Hierarchy is not a sub context menu option in the Tree pane and should be"),
            () -> Assertions.assertTrue(outputRepublishByVolumeIsDisplayedOnTreeContextMenu, "Output Republish Rulebook By Volume is not a sub context menu option in the Tree pane and should be"),
            () -> Assertions.assertTrue(westMateTocIsDisplayedOnTreeContextMenu, "WestMate TOC is not a sub context menu option in the Tree pane and should be"),
            () -> Assertions.assertTrue(westMateHighTocIsDisplayedOnTreeContextMenu, "WestMate High TOC is not a sub context menu option in the Tree pane and should be")
        );
    }

    /**
     * STORY: HALCYONST-8918 <br>
     * SUMMARY: Verifies the Legal User has either Westlaw Publisher or Jetstream Editor Access. then verifies that the Legal User is not on
     * the list of Publish Approvers. Finally, navigates to Pub Navigate, right clicks a section node, and assures that Publishing
     * Workflows and its submenus are not displayed as options in the context menu on the sibling metadata grid and the hierarchy tree <br>
     * USER: LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void publishingEnabledWestlawPublisherJetstreamUserNotPublishApproverTest()
    {
        HierarchyDatapodConfiguration.getConfig().setInsertInPubNav(true);
        datapodObject = TargetDataMockingNew.Iowa.Small.insert();
        String nodeUuid = datapodObject.getSections().get(0).getNodeUUID();

        connection = CommonDataMocking.connectToDatabase(environmentTag);
        PublishingDatabaseUtils.checkAndSetContentSetToBeEnabledForPublishing(contentSetIowa, connection);
        PublishingDatabaseUtils.checkAndRemovePublishApproverForContentSet(user().getUsername(), user().getFirstname(), user().getLastname(), contentSetIowa, connection);
        homePage().goToHomePage();
        loginPage().logIn();

        homeMenu().goToUserPreferencesSecurity();

        //verify the legal user has either westlaw publisher access or jetstream editor access
        boolean userHasWestlawPublisherOrJetstreamEditorAccess = userSecuritySettingsPage().doesUserHaveWestlawPublisherAccess() || userSecuritySettingsPage().doesUserHaveJetstreamEditorSwatUserAccess();
        Assertions.assertTrue(userHasWestlawPublisherOrJetstreamEditorAccess, "User does not have Westlaw Publisher or Jetstream Editor access and should have at least one");

        hierarchyMenu().goToPubNavigate();
        hierarchySearchPage().searchNodeUuid(nodeUuid);
        siblingMetadataPage().rightClickSelectedSiblingMetadata();

        //verify the context menu option and its submenu options do not appear in the sibling metadata pane
        boolean publishingWorkflowsIsDisplayedOnSiblingMetadataContextMenu = siblingMetadataContextMenu().isElementDisplayed(SiblingMetadataContextMenuElements.PUBLISHING_WORKFLOWS_XPATH);

        hierarchyTreePage().rightClickSelectedNavigationTreeNode();

        //verify the context menu option and its submenu options do not appear in the tree
        boolean publishingWorkflowsIsDisplayedOnTreeContextMenu = hierarchyTreeContextMenu().isElementDisplayed(TreeContextMenuElements.PUBLISHING_WORKFLOWS_XPATH);

        Assertions.assertAll
        (
            () -> Assertions.assertFalse(publishingWorkflowsIsDisplayedOnSiblingMetadataContextMenu, "Publishing Workflows is not a context menu option in the Sibling Metadata pane and should be"),
            () -> Assertions.assertFalse(publishingWorkflowsIsDisplayedOnTreeContextMenu, "Publishing Workflows is not a context menu option in the Tree pane and should be")
        );
    }

    /**
     * STORY: HALCYONST-8918 <br>
     * SUMMARY: Verifies the Legal User has either Westlaw Publisher or Jetstream Editor Access. then verifies that the Legal User is on
     * the list of Publish Approvers. Finally, navigates to Pub Navigate, right clicks a section node, and assures that Publishing
     * Workflows and its submenus are displayed as options in the context menu on the sibling metadata grid and the hierarchy tree <br>
     * USER: LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void publishingEnabledWestlawPublisherJetstreamUserPublisherApproverTest()
    {
        HierarchyDatapodConfiguration.getConfig().setInsertInPubNav(true);
        datapodObject = TargetDataMockingNew.Iowa.Small.insert();
        String nodeUuid = datapodObject.getSections().get(0).getNodeUUID();

        connection = CommonDataMocking.connectToDatabase(environmentTag);
        PublishingDatabaseUtils.checkAndSetContentSetToBeEnabledForPublishing(contentSetIowa, connection);
        PublishingDatabaseUtils.checkAndAddPublishApproverForContentSet(user().getUsername(), user().getFirstname(), user().getLastname(), contentSetIowa, connection);

        homePage().goToHomePage();
        loginPage().logIn();

        homeMenu().goToUserPreferencesSecurity();

        //verify the legal user has either westlaw publisher access or jetstream editor access
        boolean userHasWestlawPublisherOrJetstreamEditorAccess = userSecuritySettingsPage().doesUserHaveWestlawPublisherAccess() || userSecuritySettingsPage().doesUserHaveJetstreamEditorSwatUserAccess();
        Assertions.assertTrue(userHasWestlawPublisherOrJetstreamEditorAccess, "User does not have Westlaw Publisher or Jetstream Editor access and should have at least one");

        hierarchyMenu().goToPubNavigate();
        hierarchySearchPage().searchNodeUuid(nodeUuid);
        siblingMetadataPage().rightClickSelectedSiblingMetadata();

        //verify the context menu option and its submenu options appear in the sibling metadata pane
        boolean publishingWorkflowsIsDisplayedOnSiblingMetadataContextMenu = siblingMetadataContextMenu().isElementDisplayed(SiblingMetadataContextMenuElements.PUBLISHING_WORKFLOWS_XPATH);

        siblingMetadataContextMenu().expandPublishingWorkflowsPublishingDisabled();
        boolean outputRepublishRulebookByHierarchyIsDisplayedOnSiblingMetadataContextMenu = siblingMetadataContextMenu().isElementDisplayed(SiblingMetadataContextMenuElements.OUTPUT_REPUBLISH_RULEBOOK_BY_HIERARCHY_XPATH);
        boolean outputRepublishByHierarchyIsDisplayedOnSiblingMetadataContextMenu = siblingMetadataContextMenu().isElementDisplayed(SiblingMetadataContextMenuElements.OUTPUT_REPUBLISH_BY_HIERARCHY_XPATH);
        boolean outputRepublishByVolumeIsDisplayedOnSiblingMetadataContextMenu = siblingMetadataContextMenu().isElementDisplayed(SiblingMetadataContextMenuElements.OUTPUT_REPUBLISH_BY_VOLUME_XPATH);
        boolean westMateTocIsDisplayedOnSiblingMetadataContextMenu = siblingMetadataContextMenu().isElementDisplayed(SiblingMetadataContextMenuElements.WESTMATE_TOC_XPATH);
        boolean westMateHighTocIsDisplayedOnSiblingMetadataContextMenu = siblingMetadataContextMenu().isElementDisplayed(SiblingMetadataContextMenuElements.WESTMATE_HIGH_TOC_XPATH);

        hierarchyTreePage().rightClickSelectedNavigationTreeNode();

        // Verify the context menu option and its submenu options appear in the tree
        boolean publishingWorkflowsIsDisplayedOnTreeContextMenu = hierarchyTreeContextMenu().isElementDisplayed(TreeContextMenuElements.PUBLISHING_WORKFLOWS_XPATH);

        hierarchyTreeContextMenu().expandPublishingWorkflowsPublishingDisabled();
        boolean outputRepublishRulebookByHierarchyIsDisplayedOnTreeContextMenu = hierarchyTreeContextMenu().isElementDisplayed(TreeContextMenuElements.OUTPUT_REPUBLISH_RULEBOOK_BY_HIERARCHY_XPATH);
        boolean outputRepublishByHierarchyIsDisplayedOnTreeContextMenu = hierarchyTreeContextMenu().isElementDisplayed(TreeContextMenuElements.OUTPUT_REPUBLISH_BY_HIERARCHY_XPATH);
        boolean outputRepublishByVolumeIsDisplayedOnTreeContextMenu = hierarchyTreeContextMenu().isElementDisplayed(TreeContextMenuElements.OUTPUT_REPUBLISH_BY_VOLUME_XPATH);
        boolean westMateTocIsDisplayedOnTreeContextMenu = hierarchyTreeContextMenu().isElementDisplayed(TreeContextMenuElements.WESTMATE_TOC_XPATH);
        boolean westMateHighTocIsDisplayedOnTreeContextMenu = hierarchyTreeContextMenu().isElementDisplayed(TreeContextMenuElements.WESTMATE_HIGH_TOC_XPATH);

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(publishingWorkflowsIsDisplayedOnSiblingMetadataContextMenu, "Publishing Workflows is not a context menu option in the Sibling Metadata pane and should be"),
            () -> Assertions.assertTrue(outputRepublishRulebookByHierarchyIsDisplayedOnSiblingMetadataContextMenu, "Output Republish Rulebook By Hierarchy is not a sub context menu option in the Sibling Metadata pane and should be"),
            () -> Assertions.assertTrue(outputRepublishByHierarchyIsDisplayedOnSiblingMetadataContextMenu, "Output Republish By Hierarchy is not a sub context menu option in the Sibling Metadata pane and should be"),
            () -> Assertions.assertTrue(outputRepublishByVolumeIsDisplayedOnSiblingMetadataContextMenu, "Output Republish Rulebook By Volume is not a sub context menu option in the Sibling Metadata pane and should be"),
            () -> Assertions.assertTrue(westMateTocIsDisplayedOnSiblingMetadataContextMenu, "WestMate TOC is not a sub context menu option in the Sibling Metadata pane and should be"),
            () -> Assertions.assertTrue(westMateHighTocIsDisplayedOnSiblingMetadataContextMenu, "WestMate High TOC is not a sub context menu option in the Sibling Metadata pane and should be"),
            () -> Assertions.assertTrue(publishingWorkflowsIsDisplayedOnTreeContextMenu, "Publishing Workflows is not a context menu option in the Tree pane and should be"),
            () -> Assertions.assertTrue(outputRepublishRulebookByHierarchyIsDisplayedOnTreeContextMenu, "Output Republish Rulebook By Hierarchy is not a sub context menu option in the Tree pane and should be"),
            () -> Assertions.assertTrue(outputRepublishByHierarchyIsDisplayedOnTreeContextMenu, "Output Republish By Hierarchy is not a sub context menu option in the Tree pane and should be"),
            () -> Assertions.assertTrue(outputRepublishByVolumeIsDisplayedOnTreeContextMenu, "Output Republish Rulebook By Volume is not a sub context menu option in the Tree pane and should be"),
            () -> Assertions.assertTrue(westMateTocIsDisplayedOnTreeContextMenu, "WestMate TOC is not a sub context menu option in the Tree pane and should be"),
            () -> Assertions.assertTrue(westMateHighTocIsDisplayedOnTreeContextMenu, "WestMate High TOC is not a sub context menu option in the Tree pane and should be")
        );
    }

    @AfterEach
    public void cleanUp()
    {
        if(connection != null)
        {
            BaseDatabaseUtils.disconnect(connection);
        }
        if(datapodObject != null)
        {
            datapodObject.delete();
        }
    }
}
