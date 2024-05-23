package com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.source.set1;

import com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.source.navigate.RenditionContextMenuElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.popups.ViewBaselinesPageElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.*;
import static com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.*;

public class DynamicScrollingAccessTests extends TestService
{
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void dynamicScrollingAccessTest()
    {
        String uuid = "I907FF6F1FCBE11E69D2EF907E3E4B606";
        sourcePage().goToSourcePageWithRenditionUuids(uuid);
        loginPage().logIn();
        sourceNavigateGridPage().rightClickFirstRendition();
        renditionContextMenu().edit();
        boolean editRenditionDynamicScrolling = RenditionContextMenuElements.rendition.isDisplayed();
        boolean editRenditionDynamicScrollingDebug = RenditionContextMenuElements.renditionDebug.isDisplayed();
        boolean editSourceFrontDynamicScrolling = RenditionContextMenuElements.sourceFront.isDisplayed();
        boolean editSourceFrontDynamicScrollingDebug = RenditionContextMenuElements.sourceFrontDebug.isDisplayed();
        boolean editSourceEndDynamicScrolling = RenditionContextMenuElements.sourceEnd.isDisplayed();
        boolean editSourceEndDynamicScrollingDebug = RenditionContextMenuElements.sourceEndDebug.isDisplayed();
        renditionContextMenu().openViewSubMenu();
        boolean viewRenditionDynamicScrolling = RenditionContextMenuElements.rendition.isDisplayed();
        boolean viewRenditionDynamicScrollingDebug = RenditionContextMenuElements.renditionDebug.isDisplayed();
        boolean viewSourceFrontDynamicScrolling = RenditionContextMenuElements.sourceFront.isDisplayed();
        boolean viewSourceFrontDynamicScrollingDebug = RenditionContextMenuElements.sourceFrontDebug.isDisplayed();
        boolean viewSourceEndDynamicScrolling = RenditionContextMenuElements.sourceEnd.isDisplayed();
        boolean viewSourceEndDynamicScrollingDebug = RenditionContextMenuElements.sourceEndDebug.isDisplayed();
        renditionContextMenu().click(RenditionContextMenuElements.viewRenditionBaselines);
        viewBaselinesNavigatePage().switchToInnerIFrameByName("dialogContentId");
        viewBaselinesNavigatePage().rightClickWipVersionByIndex("1");
        boolean viewBaselineDynamicScrolling= ViewBaselinesPageElements.viewBaselineDynamicScrolling.isDisplayed();
        viewBaselinesNavigatePage().breakOutOfFrame();
        viewBaselinesNavigatePage().closeWindow();

        Assertions.assertAll
                (
                        () -> Assertions.assertTrue(editRenditionDynamicScrolling, "Edit -> Rendition Dynamic Scrolling isn't displayed"),
                        () -> Assertions.assertTrue(editRenditionDynamicScrollingDebug, "Edit -> Rendition Dynamic Scrolling Debug isn't displayed"),
                        () -> Assertions.assertTrue(editSourceFrontDynamicScrolling, "Edit -> Source Front Dynamic Scrolling isn't displayed"),
                        () -> Assertions.assertTrue(editSourceFrontDynamicScrollingDebug, "Edit -> Source Front Dynamic Scrolling Debug isn't displayed"),
                        () -> Assertions.assertTrue(editSourceEndDynamicScrolling, "Edit -> Source End Dynamic Scrolling isn't displayed"),
                        () -> Assertions.assertTrue(editSourceEndDynamicScrollingDebug, "Edit -> Source End Dynamic Scrolling Debug isn't displayed"),
                        () -> Assertions.assertTrue(viewRenditionDynamicScrolling, "View -> Rendition Dynamic Scrolling isn't displayed"),
                        () -> Assertions.assertTrue(viewRenditionDynamicScrollingDebug, "View -> Rendition Dynamic Scrolling Debug isn't displayed"),
                        () -> Assertions.assertTrue(viewSourceFrontDynamicScrolling, "View -> Source Front Dynamic Scrolling isn't displayed"),
                        () -> Assertions.assertTrue(viewSourceFrontDynamicScrollingDebug, "View -> Source Front Dynamic Scrolling Debug isn't displayed"),
                        () -> Assertions.assertTrue(viewSourceEndDynamicScrolling, "View -> Source End Dynamic Scrolling isn't displayed"),
                        () -> Assertions.assertTrue(viewSourceEndDynamicScrollingDebug, "View -> Source End Dynamic Scrolling Debug isn't displayed"),
                        () -> Assertions.assertTrue(viewBaselineDynamicScrolling, "View Baseline Dynamic Scrolling isn't displayed")
                );


    }
}
