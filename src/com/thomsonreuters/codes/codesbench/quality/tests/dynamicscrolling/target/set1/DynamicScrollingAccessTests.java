package com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.target.set1;

import com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.hierarchy.navigate.PreviousWipVersionsContextMenuElements;
import com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.hierarchy.navigate.SiblingMetadataContextMenuElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.SiblingMetadataElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.TargetDataMockingNew;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.datapod.HierarchyDatapodObject;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.*;
import static com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.*;

public class DynamicScrollingAccessTests extends TestService
{

    HierarchyDatapodObject datapodObject;

    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void dynamicScrollingAccessTest()
    {
        datapodObject = TargetDataMockingNew.Iowa.Small.insert();
        String uuid = datapodObject.getSections().get(0).getNodeUUID();
        hierarchyNavigatePage().goToHierarchyPage(ContentSets.IOWA_DEVELOPMENT.getCode());
        loginPage().logIn();
        hierarchyNavigatePage().handleCasIssueWithHierarchyPageOpenedAsStartPageIfOccurred(ContentSets.IOWA_DEVELOPMENT);
        hierarchySearchPage().searchNodeUuid(uuid);
        siblingMetadataPage().click(SiblingMetadataElements.SELECTED_NODE_ROW);
        siblingMetadataPage().rightClick(SiblingMetadataElements.SELECTED_NODE_ROW);
        siblingMetadataPage().breakOutOfFrame();
        boolean editContentDynamicScrolling = SiblingMetadataContextMenuElements.editContent.isDisplayed();
        boolean editContentDynamicScrollingDebug = SiblingMetadataContextMenuElements.editContentDebug.isDisplayed();
        boolean viewContentDynamicScrolling = SiblingMetadataContextMenuElements.viewContent.isDisplayed();
        boolean viewContentDynamicScrollingDebug = SiblingMetadataContextMenuElements.viewContentDebug.isDisplayed();
        siblingMetadataContextMenu().viewModifyPreviousWIPVersion();
        previousWipVersionsPage().rightClickWipVersionByIndex("1");
        boolean viewInDynamicScrollingEditor = PreviousWipVersionsContextMenuElements.viewContent.isDisplayed();
        previousWipVersionsPage().clickClose();

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(editContentDynamicScrolling, "Edit Content Dynamic Scrolling isn't displayed"),
            () -> Assertions.assertTrue(editContentDynamicScrollingDebug, "Edit Content Dynamic Scrolling Debug isn't displayed"),
            () -> Assertions.assertTrue(viewContentDynamicScrolling, "View Content Dynamic Scrolling isn't displayed"),
            () -> Assertions.assertTrue(viewContentDynamicScrollingDebug, "View Content Dynamic Scrolling Debug isn't displayed"),
            () -> Assertions.assertTrue(viewInDynamicScrollingEditor, "View Content Dynamic Scrolling in View/Modify Previous WIP Version Window isn't displayed")
        );
    }

    @AfterEach
    public void cleanUp()
    {
        if(datapodObject != null)
        {
            datapodObject.delete();
        }
    }
}
