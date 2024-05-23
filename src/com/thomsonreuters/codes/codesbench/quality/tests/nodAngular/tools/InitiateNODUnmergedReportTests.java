package com.thomsonreuters.codes.codesbench.quality.tests.nodAngular.tools;

import com.thomsonreuters.codes.codesbench.quality.pageelements.nodAngular.popups.tools.InitiateNODBatchMergePopupElementsAngular;
import com.thomsonreuters.codes.codesbench.quality.pageelements.nodAngular.popups.tools.InitiateNODUnmergedReportPopupElementsAngular;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.EDGE;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.*;
import static com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.*;

public class InitiateNODUnmergedReportTests extends TestService
{
    /**
     * HALCYONST-10593
     * 1. Log onto the Iowa (development) content set
     * 2. Go to NOD -> Subscribed Cases - angular,
     * then click "Home" in the header on the top of the page,
     * and then click "Initiate NOD Unmerged Report" from the Tools section
     * 3. VERIFY: Initiate NOD Unmerged Report window opened
     * 4. VERIFY the following elemnts are present:
     * a) "Generates a report listing all unmerged headnotes" heading
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
     * 6. VERIFY: Initiate NOD Unmerged Report window closed
     */
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void initiateNODUnmergedReportLayoutTest()
    {
        String expectedStatus = "Generates a report listing all unmerged headnotes.";
        String expectedCurrentUserName = user().getNodEditorUsername();

        // 1. Open NodClassifyUI home page
        homePageAngular().openNodHomePage(ContentSets.IOWA_DEVELOPMENT);
        loginPage().logIn();
        //2. Go to NOD -> -> Subscribed Cases - angular,
        homePageAngular().clickSubscribedCases();
        //then click "Home" in the header on the top of the page
        titleBarRibbonAngular().clickHome();
        //and then click "Initiate NOD Unmerged Report" from the Tools section
        homePageAngular().clickInitiateNODUnmergedReport();
        // VERIFY: Initiate NOD Batch Merge window opened
        Assertions.assertTrue(initiateNODUnmergedReportPopupAngular().doesElementExist(InitiateNODUnmergedReportPopupElementsAngular.PAGE_TITLE), "Initiate NOD Unmerged Report window DID NOT open");
        //4. VERIFY the following elements are present:
        String actualStatus = initiateNODUnmergedReportPopupAngular().getStatus();
        String actualCurrentUserName = initiateNODUnmergedReportPopupAngular().getCurrentUserName();
        boolean isSourceTagDisplayed = initiateNODUnmergedReportPopupAngular().isSourceTagDisplayed();
        boolean isFileNameDisplayed = initiateNODUnmergedReportPopupAngular().isFileNameDisplayed();
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
                () -> Assertions.assertTrue(isSourceTagDisplayed, "Source Tag field IS NOT displayed"),
                () -> Assertions.assertTrue(isFileNameDisplayed, "File Name field IS NOT displayed"),
                () -> Assertions.assertTrue(isAddButtonDisplayed, "Add button IS NOT displayed"),
                () -> Assertions.assertTrue(isRemoveButtonDisplayed, "Remove button IS NOT displayed"),
                () -> Assertions.assertTrue(Integer.parseInt(numberNonSelectedVolume)>=0, String.format("Number non selected volumes is '%s'", numberNonSelectedVolume)),
                () -> Assertions.assertTrue(Integer.parseInt(numberSelectedVolume)>=0, String.format("Number selected volumes is '%s'", numberSelectedVolume)),
                () -> Assertions.assertFalse(isCreateWorkflowButtonEnabled, "Create Workflow button is ENABLED"),
                () -> Assertions.assertFalse(homePageAngular().doesElementExist(InitiateNODUnmergedReportPopupElementsAngular.PAGE_TITLE), "Initiate NOD Unmerged Report window DID NOT close")
        );
    }

    /**
     * HALCYONST-10593
     * 1. Log onto the Iowa (development) content set
     * 2. Go to NOD -> Subscribed Cases - angular, then click "Home" in the header on the top of the page, and then click "Initiate NOD Unmerged Report" from the Tools section
     * 3. VERIFY: Initiate NOD Unmerged Report window opened
     * 4. Click in "File name" field and type there something like TEST_001
     * 5. In filter field above the non-selected volumes column type "constitution"
     * 7. Click the checkbox near "Title" in non-selected column to select all filtered content
     * 8. Click "Add >" button
     * 9. VERIFY: VERIFY: The volumes that were checked previously were moved to the Selected Volumes column now
     * 10. VERIFY: Total number on non-selected volumes is decremented by the amount of previously checked volumes and total number of selected volumes is incremented by  the amount of previously checked volumes
     * 11. VERIFY: the filter is cleared automatically
     * 12. Select volumes 0030, 0050, and 0330 from Non-Selected Volumes column
     * 13. Click "Add >" button
     * 14. VERIFY: Volumes 0030, 0050 and 0330 are in Selected Volumes column now
     * 15. VERIFY: Total number on non-selected volumes is decremented by 3 and total number of selected volumes is incremented by 3
     * 16. Select volume 0050 in Selected Volumes column
     * 17. Click "Remove >" button
     * 18. VERIFY: Volume 0050 is removed from Selected Volumes column and is in Non-Selected Volumes column now
     * 19. VERIFY: total number on selected volumes is decremented by 1 and total number of non-selected volumes is incremented by 1
     * 20. In filter field above the non-selected volumes column type "003"
     * 21. VERIFY: Only volumes containing the pattern "03" should occur, such as volumes 0030, 0330, and any others where a "03" is present
     * 22. Click in checkbox near title of selected column to select all filtered content
     * 23. Click "< Remove" button
     * 24. VERIFY: VERIFY: Only volumes containing the pattern "03" were removed from Selected Volumes column and are in Non-Selected Volumes column now
     * 25. VERIFY: Total number on selected volumes is decremented by the number of volumes containing the pattern "03" and total number of non-selected volumes is incremented by the number of volumes containing the pattern "03"
     * 26. VERIFY: the filter is cleared automatically
     * 27. In filter field above the selected volumes column type "assoc"
     * 28. VERIFY: Only volume 0330 is shown
     * 29. Click on "X" button in filter field to remove filtering
     * 30. VERIFY: Filter is removed, filtered volumes remain in the selected volumes column
     */
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void initiateNODUnmergedReportFilterSelectAllTest()
    {
        String fileName = "TEST_001";
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
        //2. click "Initiate NOD Unmerged Report" from the Tools section
        homePageAngular().clickInitiateNODUnmergedReport();
        //3. VERIFY: Initiate NOD Unmerged Report window opened
        Assertions.assertTrue(initiateNODUnmergedReportPopupAngular().doesElementExist(InitiateNODUnmergedReportPopupElementsAngular.PAGE_TITLE), "Initiate NOD Unmerged Report window DID NOT open");
        //4. Click in "File name" field and type there something like TEST_001
        initiateNODUnmergedReportPopupAngular().setFileName(fileName);
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

    /**
     * HALCYONST-10593
     * 1. Log onto the Iowa (development) content set
     * 2. Go to NOD -> Subscribed Cases - angular, then click "Home" in the header on the top of the page, and then click "Initiate NOD Unmerged Report" from the Tools section
     * 3. VERIFY: Initiate NOD Unmerged Report window opened
     * 4. Click in "File name" field and type there something like TEST_001
     * 5. Click in "Source tag" field and type there "15"
     * 6. Select volumes 0030, 0050 and 0330 from Non-Selected Volumes column
     * 7. Click "Add >" button
     * 8. Click on "Create Workflow" button
     * 9. VERIFY: Workflow is created and hyperlink is provided
     */
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void initiateNODUnmergedReportWorkflowTest() // Test failed due to workflow in old and new NOD can't work on the same time and now it is turned of on NODAngular
    {
        String fileName = "TEST_001";
        String sourceTag = "15";
        String firstVolume = "0030";
        String secondVolume = "0050";
        String thirdVolume = "0040";
        String expectedMessage = "Workflow has been created.";

        //1. Open NodClassifyUI home page
        homePageAngular().openNodHomePage(ContentSets.IOWA_DEVELOPMENT);
        loginPage().logIn();
        //2. Go to NOD -> -> Subscribed Cases - angular,
        homePageAngular().clickSubscribedCases();
        //then click "Home" in the header on the top of the page
        titleBarRibbonAngular().clickHome();
        //and then click "Initiate NOD Unmerged Report" from the Tools section
        homePageAngular().clickInitiateNODUnmergedReport();
        // VERIFY: Initiate NOD Batch Merge window opened
        Assertions.assertTrue(initiateNODUnmergedReportPopupAngular().doesElementExist(InitiateNODUnmergedReportPopupElementsAngular.PAGE_TITLE), "Initiate NOD Unmerged Report window DID NOT open");
        //4. Click in "File name" field and type there something like TEST_001
        initiateNODUnmergedReportPopupAngular().setFileName(fileName);
        //5. Click in "Source tag" field and type there "15"
        initiateNODUnmergedReportPopupAngular().setSourceTag(sourceTag);
        //6. Select volumes 0030, 0050 and 0330 from Non-Selected Volumes column
        toolsGridPopupAngular().selectByNameFromNonSelectedList(firstVolume);
        toolsGridPopupAngular().selectByNameFromNonSelectedList(secondVolume);
        toolsGridPopupAngular().selectByNameFromNonSelectedList(thirdVolume);
        //7. Click "Add >" button
        toolsGridPopupAngular().clickAdd();
        //8. Click on "Create Workflow" button
        toolsFooterPopupAngular().createWorkflow();
        //9. VERIFY: Workflow is created and hyperlink is provided
        String actualMessage = initiateNODUnmergedReportPopupAngular().getMessage();

        Assertions.assertAll(
                () -> Assertions.assertEquals(expectedMessage, actualMessage, String.format("Message is '%s' instead of '%s'",actualMessage,expectedMessage))
        );

    }

    /**
     * HALCYONST-10593
     * 1. Log onto the Iowa (development) content set
     * 2. Go to NOD -> Subscribed Cases - angular, then click "Home" in the header on the top of the page, and then click "Initiate NOD Unmerged Report" from the Tools section
     * 3. VERIFY: Initiate NOD Unmerged Report window opened
     * 4. VERIFY: "Create Workflow" button is disabled
     * 5. Select volumes 0030, 0050 and 0330 from Non-Selected Volumes column
     * 6. Click "Add >" button
     * 7. VERIFY: "Create Workflow" button is enabled
     * 8. Remove source tag
     * 9. VERIFY: "Create Workflow" button is disabled
     * 10. Restore the source tag
     * 11. VERIFY: "Create Workflow" button is enabled
     * 12. Click on "Create Workflow" button
     * 13. VERIFY: Workflow is created and hyperlink is provided
     * 14. Verify email with report arrives to user's mail in 5 minutes
     */
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void initiateNODUnmergedReportWorkflowWithEmptyFieldTest() // Test failed due to workflow in old and new NOD can't work on the same time and now it is turned of on NODAngular
    {
        String sourceTag = "15";
        String firstVolume = "0030";
        String secondVolume = "0050";
        String thirdVolume = "0040";
        String expectedMessage = "Workflow has been created.";

        //1. Open NodClassifyUI home page
        homePageAngular().openNodHomePage(ContentSets.IOWA_DEVELOPMENT);
        loginPage().logIn();
        //2. Go to NOD -> -> Subscribed Cases - angular,
        homePageAngular().clickSubscribedCases();
        //then click "Home" in the header on the top of the page
        titleBarRibbonAngular().clickHome();
        //and then click "Initiate NOD Unmerged Report" from the Tools section
        homePageAngular().clickInitiateNODUnmergedReport();
        // VERIFY: Initiate NOD Batch Merge window opened
        Assertions.assertTrue(initiateNODUnmergedReportPopupAngular().doesElementExist(InitiateNODUnmergedReportPopupElementsAngular.PAGE_TITLE), "Initiate NOD Unmerged Report window DID NOT open");
        boolean isCreateWorkflowButtonInitiallyEnabled = toolsFooterPopupAngular().isCreateWorkflowButtonEnabled();
        //5. Select volumes 0030, 0050 and 0330 from Non-Selected Volumes column
        toolsGridPopupAngular().selectByNameFromNonSelectedList(firstVolume);
        toolsGridPopupAngular().selectByNameFromNonSelectedList(secondVolume);
        toolsGridPopupAngular().selectByNameFromNonSelectedList(thirdVolume);
        //6. Click "Add >" button
        toolsGridPopupAngular().clickAdd();
        //7. VERIFY: "Create Workflow" button is enabled
        boolean isCreateWorkflowButtonAfterVolumesAddingEnabled = toolsFooterPopupAngular().isCreateWorkflowButtonEnabled();
        //8. Remove source tag
        initiateNODUnmergedReportPopupAngular().clearSourceTag();
        //9. VERIFY: "Create Workflow" button is disabled
        boolean isCreateWorkflowButtonWithoutSourceTagEnabled = toolsFooterPopupAngular().isCreateWorkflowButtonEnabled();
        //10. Restore the source tag
        initiateNODUnmergedReportPopupAngular().setSourceTag(sourceTag);
        //11. VERIFY: "Create Workflow" button is enabled
        boolean isCreateWorkflowButtonWithSourceTagEnabled = toolsFooterPopupAngular().isCreateWorkflowButtonEnabled();
        //12. Click on "Create Workflow" button
        toolsFooterPopupAngular().createWorkflow();
        //13. VERIFY: Workflow is created and hyperlink is provided
        String actualMessage = initiateNODUnmergedReportPopupAngular().getMessage();

        Assertions.assertAll(
                () -> Assertions.assertFalse(isCreateWorkflowButtonInitiallyEnabled, "Create Workflow button initially is ENABLED"),
                () -> Assertions.assertTrue(isCreateWorkflowButtonAfterVolumesAddingEnabled, "Create Workflow button after adding volumes is DISABLED"),
                () -> Assertions.assertFalse(isCreateWorkflowButtonWithoutSourceTagEnabled, "Create Workflow button without source tag is ENABLED"),
                () -> Assertions.assertTrue(isCreateWorkflowButtonWithSourceTagEnabled, "Create Workflow button with source tag is DISABLED"),
                () -> Assertions.assertEquals(expectedMessage, actualMessage, String.format("Message is '%s' instead of '%s'",actualMessage,expectedMessage))
        );
    }

}
