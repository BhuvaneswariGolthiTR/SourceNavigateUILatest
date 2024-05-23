package com.thomsonreuters.codes.codesbench.quality.tests.nodAngular.tools;

import com.thomsonreuters.codes.codesbench.quality.pageelements.nodAngular.popups.tools.*;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.EDGE;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.*;
import static com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.*;

public class InitiateNODBatchReorderTest extends TestService
{

    /**
     * HALCYONST-10608
     * 1. Log onto Iowa (Development) content set
     * 2. Go to NOD -> Subscribed Cases - angular,
     * then click "Home" in the header on the top of the page,
     * and then click "Initiate NOD Batch Reorder" from the Tools section
     * 3. VERIFY: Initiate NOD Batch Reorder window opened
     * 4. VERIFY the following elemnts are present:
     * a) "Initiate NOD Data Batch Reorder" heading
     * b) Current user field with the name of the current user
     * c) Source tag field
     * d) File name field
     * e) 2 two panes with select checkboxes and volume titles
     * f) Add button
     * g) Remove buton
     * h) Total number of non-selected volumes and selected volumes
     * i) "Create Workflow" button is disabled
     * k) "Cancel" button is enabled
     * 5. Click "Cancel"
     * 6. VERIFY: "Initiate NOD Batch Reorder" window closed
     */
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void initiateNODBatchReorderLayoutTest()
    {
        String expectedStatus = "Initiate NOD Data Batch Reorder";
        String expectedCurrentUserName = user().getNodEditorUsername();

        // 1. Open NodClassifyUI home page
        homePageAngular().openNodHomePage(ContentSets.IOWA_DEVELOPMENT);
        loginPage().logIn();
        //2. Go to NOD -> -> Subscribed Cases - angular,
        homePageAngular().clickSubscribedCases();
        //then click "Home" in the header on the top of the page
        titleBarRibbonAngular().clickHome();
        //and then click "Initiate NOD Batch Reorder" from the Tools section
        homePageAngular().clickInitiateNODBatchReorder();
        // VERIFY: Initiate NOD Update window opened
        Assertions.assertTrue(initiateNODBatchReorderPopupsAngular().doesElementExist(InitiateNODBatchReorderPopupsElementsAngular.PAGE_TITLE), "Initiate NOD Batch Reorder window DID NOT open");
        //4. VERIFY the following elements are present:
        String actualStatus = initiateNODBatchReorderPopupsAngular().getStatus();
        String actualCurrentUserName = initiateNODBatchReorderPopupsAngular().getCurrentUserName();
        boolean isAddButtonDisplayed = toolsGridPopupAngular().isAddButtonDisplayed();
        boolean isRemoveButtonDisplayed = toolsGridPopupAngular().isRemoveButtonDisplayed();
        String numberNonSelectedVolume = toolsGridPopupAngular().getNumberNonSelectedVolume();
        String numberSelectedVolume = toolsGridPopupAngular().getNumberSelectedVolume();
        boolean isCreateWorkflowButtonEnabled = toolsFooterPopupAngular().isCreateWorkflowButtonEnabled();
        //Click "Cancel"
        toolsFooterPopupAngular().clickCancel();
        homePageAngular().waitForPageLoaded();

        Assertions.assertAll(
                () -> Assertions.assertEquals(expectedStatus, actualStatus, String.format("Status is '%s' instead of '%s'",actualStatus,expectedStatus)),
                () -> Assertions.assertEquals(expectedCurrentUserName, actualCurrentUserName, String.format("Current user name is '%s' instead of '%s'",actualCurrentUserName,expectedCurrentUserName)),
                () -> Assertions.assertTrue(isAddButtonDisplayed, "Add button IS NOT displayed"),
                () -> Assertions.assertTrue(isRemoveButtonDisplayed, "Remove button IS NOT displayed"),
                () -> Assertions.assertTrue(Integer.parseInt(numberNonSelectedVolume)>=0, String.format("Number non selected volumes is '%s'", numberNonSelectedVolume)),
                () -> Assertions.assertTrue(Integer.parseInt(numberSelectedVolume)>=0, String.format("Number selected volumes is '%s'", numberSelectedVolume)),
                () -> Assertions.assertFalse(isCreateWorkflowButtonEnabled, "Create Workflow button is ENABLED"),
                () -> Assertions.assertFalse(homePageAngular().doesElementExist(InitiateNODBatchReorderPopupsElementsAngular.PAGE_TITLE), "Initiate NOD Batch Reorder window DID NOT close")
        );
    }

    /**
     * HALCYONST-10608
     * 1. Log onto the Iowa (development) content set
     * 2. Go to NOD -> Subscribed Cases - angular, then click "Home" in the header on the top of the page, and then click "Initiate NOD Batch Reorder" from the Tools section
     * 3. VERIFY: Initiate NOD Batch Reorder window opened
     * 4. In filter field above the non-selected volumes column type "constitution"
     * 6. Click the checkbox near "Title" in non-selected column to select all filtered content
     * 7. Click "Add >" button
     * 8. VERIFY: The volumes that were checked previously were moved to the Selected Volumes column now
     * 9. VERIFY: Total number on non-selected volumes is decremented by the amount of previously checked volumes and total number of selected volumes is incremented by  the amount of previously checked volumes
     * 10. VERIFY: the filter is cleared automatically
     * 11. Select volumes 0030, 0050, and 0330 from Non-Selected Volumes column
     * 12. Click "Add >" button
     * 13. VERIFY: Volumes 0030, 0050 and 0330 are in Selected Volumes column now
     * 14. VERIFY: Total number on non-selected volumes is decremented by 3 and total number of selected volumes is incremented by 3
     * 15. Select volume 0050 in Selected Volumes column
     * 16. Click "Remove >" button
     * 17. VERIFY: Volume 0050 is removed from Selected Volumes column and is in Non-Selected Volumes column now
     * 18. VERIFY: total number on selected volumes is decremented by 1 and total number of non-selected volumes is incremented by 1
     * 19. In filter field above the non-selected volumes column type "03"
     * 20. VERIFY: Only volumes containing the pattern "03" should occur, such as volumes 0030, 0330, and any others where a "03" is present
     * 21. Click the checkbox near "Title" in the selected column to select all filtered content
     * 22. Click "< Remove" button
     * 23. VERIFY: Only volumes containing the pattern "03" were removed from Selected Volumes column and are in Non-Selected Volumes column now
     * 24. VERIFY: Total number on selected volumes is decremented by the number of volumes containing the pattern "03" and total number of non-selected volumes is incremented by the number of volumes containing the pattern "03"
     */
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void initiateNODBatchReorderFilterSelectAllTest()
    {
        String firstFilterListPhrase = "constitution";
        String secondFilterListPhrase = "03";
        String thirdFilterListPhrase = "oli";
        String firstVolume = "0030";
        String secondVolume = "0050";
        String thirdVolume = "0040";
        String fourthVolume = "0070";
        String fifthVolume = "0080";

        //1. Open NodClassifyUI home page
        homePageAngular().openNodHomePage(ContentSets.IOWA_DEVELOPMENT);
        loginPage().logIn();
        //2. click "Initiate NOD Batch Reorder" from the Tools section
        homePageAngular().clickInitiateNODBatchReorder();
        // VERIFY: Initiate NOD Update window opened
        Assertions.assertTrue(initiateNODBatchReorderPopupsAngular().doesElementExist(InitiateNODBatchReorderPopupsElementsAngular.PAGE_TITLE), "Initiate NOD Batch Reorder window DID NOT open");
        //5. In filter field above the non-selected volumes column type "constitution"
        int nonSelectedVolumesNumberBeforeFirstFiltering = Integer.parseInt(toolsGridPopupAngular().getNumberNonSelectedVolume());
        toolsGridPopupAngular().setNonSelectedFilterList(firstFilterListPhrase);
        int nonSelectedVolumesNumberAfterFirstFiltering = Integer.parseInt(toolsGridPopupAngular().getNumberNonSelectedVolume());
        //7. Click the checkbox near "Title" in non-selected column to select all filtered content
        toolsGridPopupAngular().checkNonSelectedTitleCheckbox();
        //8. Click "Add >" button
        //9. VERIFY: The volumes that were checked previously were moved to the Selected Volumes column now
        //10. VERIFY: Total number on non-selected volumes is decremented by the amount of previously checked volumes
        // and total number of selected volumes is incremented by  the amount of previously checked volumes
        int selectedVolumesNumberBeforeFirstAdding = Integer.parseInt(toolsGridPopupAngular().getNumberSelectedVolume());
        toolsGridPopupAngular().clickAdd();
        int selectedVolumesNumberAfterFirstAdding = Integer.parseInt(toolsGridPopupAngular().getNumberSelectedVolume());
        int nonSelectedVolumesNumberAfterFirstAdding = Integer.parseInt(toolsGridPopupAngular().getNumberNonSelectedVolume());
        //11. VERIFY: The filter is cleared automatically
        String filterListAfterAdding = toolsGridPopupAngular().getNonSelectedFilterList();
        //12. Select volumes 0030, 0050, and 0330 from Non-Selected Volumes column
        //13. Click "Add >" button
        toolsGridPopupAngular().setNonSelectedFilterList(firstVolume);
        toolsGridPopupAngular().checkNonSelectedTitleCheckbox();
        toolsGridPopupAngular().clickAdd();
        toolsGridPopupAngular().setNonSelectedFilterList(secondVolume);
        toolsGridPopupAngular().checkNonSelectedTitleCheckbox();
        toolsGridPopupAngular().clickAdd();
        toolsGridPopupAngular().setNonSelectedFilterList(thirdVolume);
        toolsGridPopupAngular().checkNonSelectedTitleCheckbox();
        toolsGridPopupAngular().clickAdd();
        toolsGridPopupAngular().setNonSelectedFilterList(fourthVolume);
        toolsGridPopupAngular().checkNonSelectedTitleCheckbox();
        toolsGridPopupAngular().clickAdd();
        toolsGridPopupAngular().setNonSelectedFilterList(fifthVolume);
        toolsGridPopupAngular().checkNonSelectedTitleCheckbox();
        toolsGridPopupAngular().clickAdd();
        //14. VERIFY: Volumes 0030, 0050 and 0040 are in Selected Volumes column now
        boolean firstSelectedVolume =  toolsGridPopupAngular().isElementExistInSelectedListByPartOfName(firstVolume);
        boolean secondSelectedVolume = toolsGridPopupAngular().isElementExistInSelectedListByPartOfName(secondVolume);
        boolean thirdSelectedVolume = toolsGridPopupAngular().isElementExistInSelectedListByPartOfName(thirdVolume);
        //15. VERIFY: Total number on non-selected volumes is decremented by 3 and total number of selected volumes is incremented by 3
        int selectedVolumesNumberAfterSecondAdding = Integer.parseInt(toolsGridPopupAngular().getNumberSelectedVolume());
        int nonSelectedVolumesNumberAfterSecondAdding = Integer.parseInt(toolsGridPopupAngular().getNumberNonSelectedVolume());
        //16. Select volume 0050 in Selected Volumes column
        //17. Click "Remove >" button
        toolsGridPopupAngular().setSelectedFilterList(secondVolume);
        toolsGridPopupAngular().checkSelectedTitleCheckbox();
        toolsGridPopupAngular().clickRemove();
        //18. VERIFY: Volume 0050 is removed from Selected Volumes column and is in Non-Selected Volumes column now
        boolean isRemovedNodeInNonSelectedList = toolsGridPopupAngular().isElementExistInNonSelectedListByPartOfName(secondVolume);
        boolean isRemovedNodeInSelectedList = toolsGridPopupAngular().isElementExistInSelectedListByPartOfName(secondVolume);
        //19. VERIFY: Total number on selected volumes is decremented by 1 and total number of non-selected volumes is incremented by 1
        int selectedVolumesNumberAfterRemoving = Integer.parseInt(toolsGridPopupAngular().getNumberSelectedVolume());
        int nonSelectedVolumesNumberAfterRemoving = Integer.parseInt(toolsGridPopupAngular().getNumberNonSelectedVolume());
        //20. In filter field above the selected volumes column type "03"
        toolsGridPopupAngular().setSelectedFilterList(secondFilterListPhrase);
        int selectedVolumesNumberAfterSecondFiltering = Integer.parseInt(toolsGridPopupAngular().getNumberSelectedVolume());
        //21. VERIFY: Only volumes containing the pattern "03" should occur, such as volumes 0030, 0330, and any others where a "03" is present
        boolean isOnlyVolumesContainingPatternExistInSelectedList = toolsGridPopupAngular().isOnlyVolumesContainingPatternExistInSelectedList(secondFilterListPhrase);
        //22. Click the checkbox near "Title" in the selected column to select all filtered content
        toolsGridPopupAngular().checkSelectedTitleCheckbox();
        //23. Click "< Remove" button
        toolsGridPopupAngular().clickRemove();
        //24. VERIFY: Only volumes containing the pattern "03" were removed from Selected Volumes column and are in Non-Selected Volumes column now
        //25. VERIFY: Total number on selected volumes is decremented by the number of volumes containing the pattern "03" and total number of non-selected volumes is incremented by the number of volumes containing the pattern "03"
        //26. VERIFY: the filter is cleared automatically
        int selectedVolumesNumberAfterSecondRemoving = Integer.parseInt(toolsGridPopupAngular().getNumberSelectedVolume());
        int nonSelectedVolumesNumberAfterSecondRemoving = Integer.parseInt(toolsGridPopupAngular().getNumberNonSelectedVolume());
        String filterListAfterRemoving = toolsGridPopupAngular().getSelectedFilterList();
        //27. In filter field above the selected volumes column type "art"
        //28. VERIFY: Only volumes containing the pattern "art" should occur, such as volumes containing "part", "arts", or simply "art"
        toolsGridPopupAngular().setSelectedFilterList(thirdFilterListPhrase);
        boolean isOnlyVolumesContainingPatternExistInSelectedListAfterThirdFiltering = toolsGridPopupAngular().isOnlyVolumesContainingPatternExistInSelectedList(thirdFilterListPhrase);
        //29. Click on "X" button in filter field to remove filtering
        toolsGridPopupAngular().clearSelectedFilterList();
        //30. VERIFY: Filter is removed, filtered volumes remain in the selected volumes column
        String filterListAfterX = toolsGridPopupAngular().getSelectedFilterList();
        //Click "Cancel"
        toolsFooterPopupAngular().clickCancel();
        homePageAngular().waitForPageLoaded();

        Assertions.assertAll(
                () -> Assertions.assertEquals("",filterListAfterAdding, String.format("The filter is NOT cleared automatically after adding and has value '%s'",filterListAfterAdding)),
                () -> Assertions.assertEquals(nonSelectedVolumesNumberBeforeFirstFiltering-nonSelectedVolumesNumberAfterFirstFiltering, nonSelectedVolumesNumberAfterFirstAdding,"Total number on non-selected volumes is NOT decremented by the amount of previously checked volumes"),
                () -> Assertions.assertEquals(selectedVolumesNumberBeforeFirstAdding+selectedVolumesNumberAfterFirstAdding, nonSelectedVolumesNumberAfterFirstFiltering,"Total number of selected volumes is NOT incremented by the amount of previously checked volumes"),
                () -> Assertions.assertTrue(firstSelectedVolume, String.format("Volume with number '%s' number is NOT in Selected Volumes List",firstVolume)),
                () -> Assertions.assertTrue(secondSelectedVolume, String.format("Volume with number '%s' number is NOT in Selected Volumes List",secondVolume)),
                () -> Assertions.assertTrue(thirdSelectedVolume, String.format("Volume with number '%s' number is NOT in Selected Volumes List",thirdVolume)),
                () -> Assertions.assertEquals(nonSelectedVolumesNumberAfterFirstAdding-5, nonSelectedVolumesNumberAfterSecondAdding,"Total number on non-selected volumes is NOT decremented by 5"),
                () -> Assertions.assertEquals(selectedVolumesNumberAfterFirstAdding+5, selectedVolumesNumberAfterSecondAdding,"Total number of selected volumes is incremented by 5"),
                () -> Assertions.assertTrue(isRemovedNodeInNonSelectedList,"Volume is NOT added to non Selected list"),
                () -> Assertions.assertFalse(isRemovedNodeInSelectedList,"Volume is NOT removed from Selected list"),
                () -> Assertions.assertEquals(selectedVolumesNumberAfterSecondAdding-1, selectedVolumesNumberAfterRemoving,"Total number on selected volumes is NOT decremented by 1"),
                () -> Assertions.assertEquals(nonSelectedVolumesNumberAfterSecondAdding+1, nonSelectedVolumesNumberAfterRemoving,"Total number of non-selected volumes is incremented by 1"),
                () -> Assertions.assertTrue(isOnlyVolumesContainingPatternExistInSelectedList, String.format("After filtering by '%s' value the selected List contains values without such pattern",secondFilterListPhrase)),
                () -> Assertions.assertEquals(selectedVolumesNumberAfterRemoving-selectedVolumesNumberAfterSecondFiltering, selectedVolumesNumberAfterSecondRemoving,"Total number on selected volumes is NOT decremented by the number of volumes containing the pattern 03"),
                () -> Assertions.assertEquals(nonSelectedVolumesNumberAfterRemoving+selectedVolumesNumberAfterSecondFiltering, nonSelectedVolumesNumberAfterSecondRemoving,"Total number of non-selected volumes is incremented by the number of volumes containing the pattern 03"),
                () -> Assertions.assertEquals("",filterListAfterRemoving, String.format("The filter is NOT cleared automatically after removing and has value '%s'",filterListAfterAdding)),
                () -> Assertions.assertTrue(isOnlyVolumesContainingPatternExistInSelectedListAfterThirdFiltering, String.format("After filtering in Selected Volume List by '%s' value the selected List contains values without such pattern",thirdFilterListPhrase)),
                () -> Assertions.assertEquals("",filterListAfterX, "The filter is NOT cleared automatically after Click on X button"),
                () -> Assertions.assertFalse(homePageAngular().doesElementExist(InitiateNODBatchMergePopupElementsAngular.PAGE_TITLE), "Initiate NOD Batch Merge window DID NOT close")
        );

    }

}
