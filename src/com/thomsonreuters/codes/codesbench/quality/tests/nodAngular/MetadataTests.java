package com.thomsonreuters.codes.codesbench.quality.tests.nodAngular;

import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.EDGE;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.tableAngular.table.enums.TableColumns;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Random;
import java.util.stream.Stream;

public class MetadataTests extends TestService
{

    /**
     * HALCYONST-9801
     * HALCYONST-9807
     * 1. Open Subscribed cases page for Iowa (Development)
     * 2. VERIFY: Subscribed Cases page is opened
     * 3. Click on the blue hyper link of any of the cases
     * 4. VERIFY: Headnotes page is opened
     * 5. Select a document, right click it
     * 6. VERIFY: Update metadata option is present
     * 7. Select Update Metadata
     * 8. VERIFY: Update Metadata pop up windows is shown.
     * 9. Set law tracking for the node (any of your choice)
     * 10. Change\set effective end date.
     * 11. Click update (accepting sane messages)
     * 12. Reopen Metadata for the node again
     * 13. VERIFY: Metadata has all the changes applied in steps 9-10
     *
     *
     *
     * 2 last cases will fail due to https://jira.thomsonreuters.com/browse/HALCYONST-13193
     */

    private static Stream<Arguments> provideDataForUpdateMetadata()
    {
        return Stream.of(
                Arguments.of("123.3", "= 123.3 Definitions"),
                Arguments.of("123.3", "D. I GENERAL PROVISIONS RELATING TO ALCOHOLIC BEVERAGES"),
                Arguments.of("123.3 3", "BL 3 Spirits"),
                Arguments.of("123.3 1", "BL ANALYSI ")
        );
    }
    @ParameterizedTest(name = "Test: {displayName}; Index: {index}; Arguments: {arguments}")
    @MethodSource("provideDataForUpdateMetadata")
    @EDGE
    @LEGAL
    @LOG
    public void updateMetadataTest(String searchString, String nodeText)
    {
        ContentSets contentSet = ContentSets.IOWA_DEVELOPMENT;
        String startDate = "02/12/2019";
        String endDate = "03/10/2023";
        String nodWindowName = "NodClassifyUi";
        //1. Open Subscribed Cases page for IOWA (Development)
        subscribedCasesPageAngular().openNodSubscribedCasesPage(contentSet);
        loginPage().logIn();
        //2. VERIFY: Subscribed Cases page is opened
        boolean subscribedCasesPageIsOpened = subscribedCasesPageAngular().isPageOpened();
        Assertions.assertTrue(subscribedCasesPageIsOpened, "NOD Cases page didn't open");
        //3. Click on the blue hyper link of any of the cases
        Random random = new Random();
        int randomRow = random.nextInt(20);
        subscribedCasesTablePage().clickCellTextByRowAndColumn(randomRow, TableColumns.CASE_SERIAL_SUBSCRIBED_CASES);
        //4. VERIFY: The Classification/Headnote page is opened
        boolean headnotesPageIsOpened = headnotesPageAngular().isPageOpened();
        Assertions.assertTrue(headnotesPageIsOpened, "Headnotes page didn't open");
        //5. Select a document, right click it
        hierarchyTreeFragmentAngular().quickFind(searchString);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        hierarchyTreeFragmentAngular().scrollToSeeParentLevelNode(nodeText);
        //6. VERIFY: Update metadata option is present
        hierarchyTreeFragmentAngular().openContextMenuForNodeWithText(nodeText);
        boolean updateMetadataIsPresent = headnotesContextMenuAngular().isUpdateMetadataPresent();
        //7. Select Update Metadata
        headnotesContextMenuAngular().selectUpdateMetadata();
        //8. VERIFY: Update Metadata pop up windows is shown.
        boolean isUpdateMetadataPageOpened = updateMetadataPopupAngular().isPageOpened();
        //9. Set law tracking for the node (any of your choice)
        updateMetadataPage().clickSetLawTracking();
        String expectedWindowTitle = listOfC2012RenditionsPage().getExpectedWindowName();
        boolean isLawTrackingPopupOpened = updateMetadataPage().switchToWindow(expectedWindowTitle);
        listOfC2012RenditionsPage().enterTheInnerFrame();
        String expectedLawTrackingText = listOfC2012RenditionsPage().composeLawTrackingTextForLineByNumber(2);
        listOfC2012RenditionsPage().setLawTrackingToTableRowByNumber(2);
        updateMetadataPage().switchToWindow(nodWindowName);
        updateMetadataPage().enterTheInnerFrame();
        String actualLawTrackingText = updateMetadataPage().getLawTrackingText();
        boolean isLawTrackingTextExpected = expectedLawTrackingText.equals(actualLawTrackingText);
        //10. Change\set effective end date.
        updateMetadataPage().clearAndEnterStartDate(startDate);
        updateMetadataPage().clearEffectiveEndDate();
        updateMetadataPage().enterEffectiveEndDate(endDate);
        //11. Click update (accepting sane messages)
        updateMetadataPage().clickUpdate();
        //12. Reopen Metadata for the node again
        hierarchyTreeFragmentAngular().switchToWindow(nodWindowName);
        hierarchyTreeFragmentAngular().clickQuickFind();
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        hierarchyTreeFragmentAngular().scrollToSeeParentLevelNode(nodeText);
        hierarchyTreeFragmentAngular().openContextMenuForNodeWithText(nodeText);
        headnotesContextMenuAngular().selectUpdateMetadata();
        //13. VERIFY: Metadata has all the changes applied in steps 9-10
        hierarchyTreeFragmentAngular().enterTheInnerFrame();
        String actualStartDate = updateMetadataPage().getEffectiveStartDate();
        String actualEndDate = updateMetadataPage().getEffectiveEndDate();
        boolean isStartDateExpected = actualStartDate.equals(startDate);
        boolean isEndDateExpected = actualEndDate.equals(endDate);
        String actualLawTrackingTextAfterReopen = updateMetadataPage().getLawTrackingText();
        boolean isLawTrackingTextExpectedAfterReopen = expectedLawTrackingText.equals(actualLawTrackingTextAfterReopen);
        //Set law tracking to some default value
        updateMetadataPage().clearEffectiveStartDate();
        updateMetadataPage().clearEffectiveEndDate();
        updateMetadataPage().clickSetLawTracking();
        updateMetadataPage().switchToWindow(expectedWindowTitle);
        listOfC2012RenditionsPage().enterTheInnerFrame();
        listOfC2012RenditionsPage().setLawTrackingToTableRowByNumber(4);
        updateMetadataPage().switchToWindow("NodClassifyUi");
        updateMetadataPage().enterTheInnerFrame();
        updateMetadataPage().clickUpdate();

        Assertions.assertAll
                (
                        () -> Assertions.assertTrue(updateMetadataIsPresent,
                                "Update metadata option is not present in context menu"),
                        () -> Assertions.assertTrue(isUpdateMetadataPageOpened,
                                "Update metadata popup didn't appear"),
                        () -> Assertions.assertTrue(isLawTrackingPopupOpened,
                                "Law tracking popup didn't open"),
                        () -> Assertions.assertTrue(isLawTrackingTextExpected, String.format(
                                "Law tracking text is unexpected" +
                                "\nExpected: '%s' \n Actual: '%s'", expectedLawTrackingText, actualLawTrackingText)),
                        () -> Assertions.assertTrue(isStartDateExpected, String.format(
                                "Start date is not unexpected" +
                                "\nExpected: '%s' '\n Actual: '%s'", startDate, actualStartDate)),
                        () -> Assertions.assertTrue(isEndDateExpected, String.format(
                                "End date is not unexpected" +
                                "\nExpected: '%s' \n Actual: '%s'", endDate, actualEndDate)),
                        () -> Assertions.assertTrue(isLawTrackingTextExpectedAfterReopen, String.format(
                                "Law tracking text is not unexpected" +
                                "\nExpected: '%s' \n Actual: '%s'", expectedLawTrackingText, actualLawTrackingTextAfterReopen))
                );
    }
}
