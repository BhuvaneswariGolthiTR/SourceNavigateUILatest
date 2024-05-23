package com.thomsonreuters.codes.codesbench.quality.tests.nodAngular;

import com.thomsonreuters.codes.codesbench.quality.pageelements.nodAngular.HeadnotesPageElementsAngular;
import com.thomsonreuters.codes.codesbench.quality.pageelements.nodAngular.headnotesPageFragmentsElements.HeadnotesClassificationFragmentElementsAngular;
import com.thomsonreuters.codes.codesbench.quality.pageelements.nodAngular.headnotesPageFragmentsElements.HierarchyTreeFragmentElementsAngular;
import com.thomsonreuters.codes.codesbench.quality.pageelements.tableAngular.popups.FilterPopupElementsAngular;
import com.thomsonreuters.codes.codesbench.quality.pageelements.nodAngular.popups.HeadnoteDetailsPopupElementsAngular;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.nodangular.NodAngularDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.Courts;
import com.thomsonreuters.codes.codesbench.quality.utilities.tableAngular.table.enums.FilterOperator;
import com.thomsonreuters.codes.codesbench.quality.utilities.tableAngular.table.enums.TableColumns;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.CARSWELL;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.sql.Connection;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.*;

public class HeadnotesTests extends TestService
{
    Connection connection;
    String courtUuid = Courts.IOWA.getCourtUuid();
    String contentSet = ContentSets.IOWA_DEVELOPMENT.getCode();
    private static final String EXPECTED_ROOT_ELEMENTS_TEXT = "@ IOWA CODE ANNOTATED IOWA CODE ANNOTATED";
    private static final String GRADE_LEVEL_TITLE = "T. I STATE SOVEREIGNTY AND MANAGEMENT [Chs. 1-3";

    @AfterEach
    public void cleanUp()
    {
        if (connection != null)
        {
            BaseDatabaseUtils.disconnect(connection);
        }
    }

    /**
     * HALCYONST-9404 + HALCYONST-9433 + HALCYONST-9384 - NOD Headnotes page for US content set Section level Context Menu
     * 1. Open Subscribed Cases page for IOWA (Development)
     * 2. VERIFY: Subscribed Cases page is opened
     * 3. Click on the blue hyper link of any of the cases
     * 4. VERIFY: Headnotes page is opened
     * 5. VERIFY: On the left side of the page appears a hierarchy tree for the content set that you signed into
     * 6. Select a Section level document
     * 7. Right click on this document
     * 8. VERIFY: That the following context menu generates and is in this order: Insert Blueline, Update Metadata,
     * View Content, View Content Raw Xml, Edit Content, Edit Content Raw Xml, Find in Hierarchy
     * <p>
     * HALCYONST-9404 + HALCYONST-9433 + HALCYONST-9384 - NOD Headnotes page for US content set Grade level Context Menu
     * 1. Open Subscribed Cases page for IOWA (Development)
     * 2. VERIFY: Subscribed Cases page is opened
     * 3. Click on the blue hyper link of any of the cases
     * 4. VERIFY: Headnotes page is opened
     * 5. VERIFY: On the left side of the page appears a hierarchy tree for the content set that you signed into
     * 6. Select a Grade level document
     * 7. Right click on this document
     * 8. VERIFY: That the following context menu generates and is in this order: Update Metadata, View Content,
     * View Content Raw Xml, Edit Content, Edit Content Raw Xml, Find in Hierarchy
     * <p>
     * HALCYONST-9404 + HALCYONST-9433 + HALCYONST-9384 - NOD Headnotes page for US content set Grade level Context Menu
     * 1. Open Subscribed Cases page for IOWA (Development)
     * 2. VERIFY: Subscribed Cases page is opened
     * 3. Click on the blue hyper link of any of the cases
     * 4. VERIFY: Headnotes page is opened
     * 5. VERIFY: On the left side of the page appears a hierarchy tree for the content set that you signed into
     * 7.  Select a BL level document
     * 8.  Right click on this document
     * 9. VERIFY: That the following context menu generates and is in this order: Blueline Analysis View, Insert Blueline,
     * Edit Blueline, Delete Blueline, Update Metadata, View Content, View Content Raw Xml, Edit Content, Edit Content Raw Xml, Find in Hierarchy
     * <p>
     * HALCYONST-9404 + HALCYONST-9433 + HALCYONST-9384 - NOD Headnotes page for US content set BL ANALYSIS level Context Menu
     * 1. Open Subscribed Cases page for IOWA (Development)
     * 2. VERIFY: Subscribed Cases page is opened
     * 3. Click on the blue hyper link of any of the cases
     * 4. VERIFY: Headnotes page is opened
     * 5. VERIFY: On the left side of the page appears a hierarchy tree for the content set that you signed into
     * 6. Select a BL ANALYSIS level document
     * 7. Right click on this document
     * 8. VERIFY: That the following context menu generates and is in this order:
     * All Blueline Analysis View, Blueline Analysis View, Insert Blueline, Update Metadata, View Content,
     * View Content Raw Xml, Edit Content, Edit Content Raw Xml, Find in Hierarchy
     */

    private static Stream<Arguments> provideDataForContextMenu()
    {
        return Stream.of(
                Arguments.of("123.3", "= 123.3 Definitions", Arrays.asList("Insert Blueline", "Update Metadata", "View Content",
                        "View Content Raw Xml", "Edit Content", "Edit Content Raw Xml", "Find In Hierarchy")),
                Arguments.of("123.3", "D. I GENERAL PROVISIONS RELATING TO ALCOHOLIC BEVERAGES", Arrays.asList("Update Metadata", "View Content",
                        "View Content Raw Xml", "Edit Content", "Edit Content Raw Xml", "Find In Hierarchy")),
                Arguments.of("123.3 3", "BL 3 Spirits", Arrays.asList("Blueline Analysis View", "Insert Blueline", "Edit Blueline",
                        "Delete Blueline", "Update Metadata", "View Content", "View Content Raw Xml", "Edit Content",
                        "Edit Content Raw Xml", "Find In Hierarchy")),
                Arguments.of("123.3 1", "BL ANALYSI ", Arrays.asList("All Blueline Analysis View", "Blueline Analysis View", "Insert Blueline",
                        "Update Metadata", "View Content", "View Content Raw Xml", "Edit Content",
                        "Edit Content Raw Xml", "Find In Hierarchy"))
        );
    }
    @ParameterizedTest(name = "Test: {displayName}; Index: {index}; Arguments: {arguments}")
    @MethodSource("provideDataForContextMenu")
    @EDGE
    @LEGAL
    @LOG
    public void contextMenuInUSTest(String searchString, String nodeText, List<String> expectedContexMenuOptions)
    {
        //1. Open Subscribed Cases page for IOWA (Development)
        subscribedCasesPageAngular().openNodSubscribedCasesPage(ContentSets.IOWA_DEVELOPMENT);
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
        //5. VERIFY: On the left side of the page appears a hierarchy tree for the content set that you signed into
        boolean isNavigationTreeVisible = hierarchyTreeFragmentAngular().isHierarchyTreeDisplayed();
        boolean isContentSetOpened = headnotesPageAngular().isElementDisplayed(String.format
                (HierarchyTreeFragmentElementsAngular.SPAN_WITH_TEXT_CONTAINS, EXPECTED_ROOT_ELEMENTS_TEXT));
        //6. Select a Section/Grade/etc level document
        hierarchyTreeFragmentAngular().quickFind(searchString);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        hierarchyTreeFragmentAngular().scrollToSeeParentLevelNode(nodeText);
        //7. Right click on this document
        hierarchyTreeFragmentAngular().openContextMenuForNodeWithText(nodeText);
        //8. VERIFY: That the following context menu generates and is in this order: Insert Blueline, Update Metadata,
        // View Content, View Content Raw Xml, Edit Content, Edit Content Raw Xml, Find in Hierarchy
        List<String> contextMenuOptionsTexts = headnotesContextMenuAngular().getContextMenuOptionsTexts();

        Assertions.assertAll
                (
                        () -> Assertions.assertTrue(isNavigationTreeVisible, "Navigation tree is not shown"),
                        () -> Assertions.assertTrue(isContentSetOpened, String.format("Root element of the content set is not found in navigation tree. " +
                                "Root element: \"%s\"", EXPECTED_ROOT_ELEMENTS_TEXT)),
                        () -> Assertions.assertEquals(contextMenuOptionsTexts, expectedContexMenuOptions, String.format("Context menu options are not expected. \n" +
                                "Expected: %s \n Actual: %s", expectedContexMenuOptions, contextMenuOptionsTexts))
                );
    }

    /**
     * HALCYONST-9436
     * HALCYONST-9721
     * 1. Open Subscribed Cases page for IOWA (Development)
     * 2. VERIFY: Subscribed Cases page is opened
     * 3. Click on the blue hyper link of any of the cases
     * 4. VERIFY: The Classification/Headnote page is opened
     * 5. VERIFY: On the left side of the page appears a hierarchy tree for the content set that you signed into
     * 6. Select BL Analysis node
     * 7. Select Blueline Analsys View from the context menu
     * 8. VERIFY: The Blueline Analysis pop-up opens with a list of the selected NOD's Bluelines and hyper links to these Bluelines.
     * 9. Select Blueline 1
     * 10. The Pop-up will close
     * 11. VERIFY: That Blueline 1 is selected
     */

    private static Stream<Arguments> provideDataForBluelineAnalysis()
    {
        return Stream.of(
                Arguments.of("123.3 1", "BL ANALYSI ", "All Blueline Analysis View")
        );
    }

    @ParameterizedTest(name = "Test: {displayName}; Index: {index}; Arguments: {arguments}")
    @MethodSource("provideDataForBluelineAnalysis")
    @EDGE
    @LEGAL
    @LOG
    public void bluelineAnalysisViewTest(String searchString, String nodeText, String contextMenuOption)
    {
        String title = "Test" + DateAndTimeUtils.getCurrentDateAndTimeyyyyMMddHHmm();
        String caseSerial = "1234-987689";
        connection = BaseDatabaseUtils.connectToDatabaseUAT();
        NodAngularDatabaseUtils.insertCase(title, caseSerial, courtUuid, connection);
        String caseUuid = NodAngularDatabaseUtils.getCaseUuid(caseSerial, connection);
        NodAngularDatabaseUtils.subscribeToCase(caseUuid, contentSet, connection);

        try
        {
            //1. Open Subscribed Cases page for IOWA (Development) and open mocked up case
            subscribedCasesPageAngular().openNodSubscribedCasesPage(ContentSets.IOWA_DEVELOPMENT);
            loginPage().logIn();
            Assertions.assertTrue(subscribedCasesPageAngular().isPageOpened(), "NOD Cases page didn't open");
            subscribedCasesTablePage().openHeadnotesPageByCaseSerial(caseSerial);
            Assertions.assertTrue(headnotesPageAngular().isPageOpened(), "Headnotes page didn't open");

            //5. VERIFY: On the left side of the page appears a hierarchy tree for the content set that you signed into
            boolean isNavigationTreeVisible = hierarchyTreeFragmentAngular().isHierarchyTreeDisplayed();
            boolean isContentSetOpened = headnotesPageAngular().isElementDisplayed(String.format(HierarchyTreeFragmentElementsAngular.SPAN_WITH_TEXT, EXPECTED_ROOT_ELEMENTS_TEXT));

            //6. Select BL Analysis node
            hierarchyTreeFragmentAngular().quickFind(searchString);
            DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
            hierarchyTreeFragmentAngular().waitForElementScrolledTo(nodeText);
            List<String> fullBluelineNamesInTree = hierarchyTreeFragmentAngular().getAllBluelinesNamesInBluelineAnalysisNode(nodeText);
            HashMap<String, String> bluelinesAndNumbers = hierarchyTreeFragmentAngular().convertFullBluelinesNamesToNameToNumberHashMap(fullBluelineNamesInTree);
            //7. Right click on this document
            hierarchyTreeFragmentAngular().openContextMenuForNodeWithText(nodeText);
            headnotesContextMenuAngular().selectOptionByText(contextMenuOption);
            //VERIFY: Blueline Analysis Popup is opened
            boolean isBluelineAnalysisPopupOpened = bluelineAnalysisPopupAngular().isPageOpened();
            String headerText = bluelineAnalysisPopupAngular().getHeaderText();
            //VERIFY: Blueline Analysis has all expected bluelines with correct numbers
            HashMap<String, String> bluelineAnalysisNamesAndNumbers = bluelineAnalysisPopupAngular().getAllBluelinesAsNameToNumberHashMap();
            boolean areBluelinesInPopupSameAsInHierarchyTree = bluelineAnalysisNamesAndNumbers.equals(bluelinesAndNumbers);
            String bluelineToSelect = bluelineAnalysisPopupAngular().selectRandomBlueline(bluelineAnalysisNamesAndNumbers);
            String fullBluelineNameInTree = fullBluelineNamesInTree.stream().filter(element -> element.contains(bluelineToSelect)).collect(Collectors.toList()).get(0);
            bluelineAnalysisPopupAngular().clickOnBluelineWithName(bluelineToSelect);
            hierarchyTreeFragmentAngular().waitForGridRefresh();
            String treeElementClass = hierarchyTreeFragmentAngular().getNodeClassByName(fullBluelineNameInTree);
            boolean isTreeElementSelected = treeElementClass.contains("selected");

            Assertions.assertAll
                          (
                              () -> Assertions.assertTrue(isNavigationTreeVisible, "Navigation tree is not shown"),
                              () -> Assertions.assertTrue(isContentSetOpened, String.format("Root element of the content set is not found in navigation tree. " + "Root element: \"%s\"", EXPECTED_ROOT_ELEMENTS_TEXT)),
                              () -> Assertions.assertTrue(isBluelineAnalysisPopupOpened, "Blueline Analysis Popup didn't open."),
                              () -> Assertions.assertEquals(headerText, contextMenuOption.replace(" View", ""), "Popup header is not expected."),
                              () -> Assertions.assertTrue(areBluelinesInPopupSameAsInHierarchyTree, String.format("Blueline Analysis Popup has different data on bluelines, " + "than in Hierarchy tree. \n Tree: %s, \n Popup: %s", bluelineAnalysisNamesAndNumbers, bluelinesAndNumbers)),
                              () -> Assertions.assertTrue(isTreeElementSelected, "Blueline %s is not selected in tree")
                          );
        }
        finally
        {
            NodAngularDatabaseUtils.deleteCase(caseUuid, contentSet, connection);
        }
    }

    /**
     * HALCYONST-9524
     * 1. Open Subscribed Cases page for IOWA (Development)
     * 2. VERIFY: Subscribed Cases page is opened
     * 3. Find a case with at least 3 headnotes (HN column)
     * 4. Click on the blue hyper link for that case
     * 5. VERIFY: The Classification/Headnote page is opened
     * 6. VERIFY: There are "HN", "RL", "Headnote Text" columns in classification area
     * 7. Click on any headnote number hyper link in classification area (HN column)
     * 8. VERIFY: "Headnote Detail" pop up appears.
     * 9. VERIFY: The pop up title is "Headnote Detail|{case-serial}|{case title}", where {case-serial} is case serial number like 1234-567890 and {case title} is case title like Thomson v. USA
     * 10. VERIFY: "Headnote", "Topic & Key Number", "Content Set", "Classification" and "Full Text" are present with respective data filled.
     * 11. VERIFY: Navigation buttons in the bottom of pop up window are present: "<< <<", "<<", headnotes numbers ("1", "2", "3", etc), ">>", ">> >>"
     */
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void headnoteDetailsLayoutTest()
    {
        String caseTitle = "Test" + DateAndTimeUtils.getCurrentDateAndTimeyyyyMMddHHmm();
        String caseSerial = "1234-987666";
        String headnoteText = "Test headnote";
        int numberOfHeadnotes = 3;

        //Mock up case and 3 headnotes
        connection = BaseDatabaseUtils.connectToDatabaseUAT();
        NodAngularDatabaseUtils.insertCase(caseTitle, caseSerial, courtUuid, connection);
        String caseUuid = NodAngularDatabaseUtils.getCaseUuid(caseSerial, connection);
        NodAngularDatabaseUtils.subscribeToCase(caseUuid, contentSet, connection);
        String subscribedCaseUuid = NodAngularDatabaseUtils.getSubscribedCaseUuid(caseUuid, contentSet, connection);
        NodAngularDatabaseUtils.insertMultipleHeadnotesAndSubscribe(numberOfHeadnotes, caseUuid, subscribedCaseUuid, headnoteText, connection);
        try
        {
            //1. Open Subscribed cases page for IOWA (Development) and open mocked case with 3 headnotes
            subscribedCasesPageAngular().openNodSubscribedCasesPage(ContentSets.IOWA_DEVELOPMENT);
            loginPage().logIn();
            Assertions.assertTrue(subscribedCasesPageAngular().isPageOpened(), "NOD Subscribed Cases page didn't open");subscribedCasesTablePage().openHeadnotesPageByCaseSerial(caseSerial);
            Assertions.assertTrue(headnotesPageAngular().isPageOpened(), "Headnotes page didn't open");

            //7. VERIFY: There are "HN", "RL", "Headnote Text" columns in classification area with expected data
            boolean classificationColumnsHeadersAreShown = headnotesClassificationFragmentAngular().isClassificationAreaShown();
            int numberOfHeadnotesOnHeadnotesPage = headnotesClassificationFragmentAngular().getNumberOfHeadnotesInTable();
            boolean allColumnsExpectedToHaveTextHaveIt = headnotesClassificationFragmentAngular().doAllRowsHaveTextInColumnsHnRlHeadnotesText();

            // Open first headnote link and verify the pop-up header is "Headnote Detail|{case-serial}|{case title}"
            headnotesClassificationFragmentAngular().clickHeadnoteLinkByRowNumber(1);
            Assertions.assertTrue(headnoteDetailsPopupAngular().isPageOpened(), "Headnote details popup doesn't open when it should");
            String expectedHeader = "Headnote Detail | " + caseSerial + " | " + caseTitle;
            String actualHeader = headnotesDetailsPage().getHeader();

            //11. VERIFY: "Headnote", "Topic & Key Number", "Content Set", "Classification" and "Full Text" are present with respective data filled.
            boolean headnoteNumberIsShown = headnoteDetailsPopupAngular().isHeadnotesNumberShownAndExpected(numberOfHeadnotesOnHeadnotesPage);
            boolean topicKeyNumberIsShown = headnoteDetailsPopupAngular().isTopicAndKeyNumberShown();
            boolean contentSetIsShown = headnoteDetailsPopupAngular().isContentSetShownAndExpected(ContentSets.IOWA_DEVELOPMENT);
            boolean classificationIsShown = headnoteDetailsPopupAngular().isElementDisplayed(HeadnoteDetailsPopupElementsAngular.CLASSIFICATION);
            boolean fullTextIsShown = headnoteDetailsPopupAngular().isFullTextShown();

            //12. VERIFY: Navigation buttons in the bottom of pop up window are present: "<< <<", "<<", headnotes numbers ("1", "2", "3", etc), ">>", ">> >>"
            boolean navigationArrowsButtonsAreShown = headnoteDetailsPopupAngular().areNavigationArrowButtonsShown();
            List<String> expectedPageNumbers = headnoteDetailsPopupAngular().generateExpectedPageNumbersUsingTotalNumberOfHeadnotes(numberOfHeadnotesOnHeadnotesPage);
            List<String> pageNumbers = headnoteDetailsPopupAngular().getNavigationPageButtons();

            Assertions.assertAll
                          (
                              () -> Assertions.assertTrue(classificationColumnsHeadersAreShown, "Column headers doesn't exist in classification area"),
                              () -> Assertions.assertEquals(numberOfHeadnotes, numberOfHeadnotesOnHeadnotesPage, "Number of headnotes on headnotes page is different from number of headnotes shown on Subscribed cases page"),
                              () -> Assertions.assertTrue(allColumnsExpectedToHaveTextHaveIt, "Not all columns contain text for headnotes"),
                              () -> Assertions.assertEquals(expectedHeader, actualHeader, "Headnote Details header was not expected"),
                              () -> Assertions.assertTrue(headnoteNumberIsShown, "Headnote field is not shown in Headnote Details popup"),
                              () -> Assertions.assertTrue(topicKeyNumberIsShown, "Topic and key number field is not shown in Headnote Details popup"),
                              () -> Assertions.assertTrue(contentSetIsShown, "Content set field is not shown in Headnote Details popup"),
                              () -> Assertions.assertTrue(classificationIsShown, "Classification field is not shown in Headnote Details popup"),
                              () -> Assertions.assertTrue(fullTextIsShown, "Full text field is not shown in Headnote Details popup"),
                              () -> Assertions.assertTrue(navigationArrowsButtonsAreShown, "Navigation arrows are not shown in the popup"),
                              () -> Assertions.assertEquals(expectedPageNumbers, pageNumbers, "Page numbers are not shown correctly")
                          );
        }
        //Cleanup, delete case and all its child dependencies
        finally
        {
            NodAngularDatabaseUtils.deleteCase(caseUuid, contentSet, connection);
        }
    }

    /**
     * HALCYONST-9524
     * 1. Open Subscribed Cases page for IOWA (Development)
     * 2. VERIFY: Subscribed Cases page is opened
     * 3. Find given case serial
     * 4. Click on the blue hyper link for that case
     * 5. VERIFY: The Classification/Headnote page is opened
     * 6. VERIFY: That the classification information is populated to show how many of headnotes where classified, ignored and that have had no activity
     * 7. Click the Ignore button, VERIFY: That the "HN IGNORED*" entry will increase by 1 and "HN NOT REVIEWED" entry will increase by 1
     * 8. Click the Unignore button, VERIFY: That the "HN IGNORED*" entry will decrease by 1 and "HN NOT REVIEWED" entry will decrease by 1
     * 9. Click the Classify button, VERIFY: That the HN Classified entry will increase by 1
     * 10. Click the trashcan icon next to the blue hyper link created under the "CLASSIFICATION" column to remove the classification
     */
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void headnoteBenchmarksTest()
    {
        // This test should use mocking
        String caseSerial = "2056-754159";
        String searchString = "123.1 1";
        String alertText = "Are you sure you want to un-classify this headnote?";
        List<Integer> expectedBenchmarks = Arrays.asList(0, 17, 16, 0, 0, 17);
        //1. Open Subscribed cases page for IOWA (Development)``
        subscribedCasesPageAngular().openNodSubscribedCasesPage(ContentSets.IOWA_DEVELOPMENT);
        loginPage().logIn();
        //2. VERIFY: Subscribed Cases page is opened
        boolean subscribedCasesPageIsOpened = subscribedCasesPageAngular().isPageOpened();
        Assertions.assertTrue(subscribedCasesPageIsOpened, "NOD Subscribed Cases page didn't open");
        //3. Find cases with at least 3 headnotes
        //4. Find given case serial
        //5. Click on the blue hyper link for that case
        subscribedCasesTablePage().openHeadnotesPageByCaseSerial(caseSerial);
        //6. VERIFY: The Classification/Headnote page is opened
        boolean headnotesPageIsOpened = headnotesPageAngular().isPageOpened();
        Assertions.assertTrue(headnotesPageIsOpened, "Headnotes page didn't open");
        //7. VERIFY: That the classification information is populated to show how many of headnotes where classified, ignored and that have had no activity
        int benchmarkClassifications = headnotesBenchmarksFragmentAngular().getBenchmarksClassifications();
        int benchmarkHeadnotes = headnotesBenchmarksFragmentAngular().getBenchmarksHeadnotes();
        int benchmarkAiIgnoredHN = headnotesBenchmarksFragmentAngular().getBenchmarksAiIgnoredHN();
        int benchmarkHNClassified = headnotesBenchmarksFragmentAngular().getBenchmarksHNClassified();
        int benchmarkHNIgnored = headnotesBenchmarksFragmentAngular().getBenchmarksHNIgnored();
        int benchmarkHNNotReviewed = headnotesBenchmarksFragmentAngular().getBenchmarksHNNotReviewed();
        List<Integer> benchmarksAsOnPage = Arrays.asList(benchmarkClassifications, benchmarkHeadnotes, benchmarkAiIgnoredHN, benchmarkHNClassified, benchmarkHNIgnored, benchmarkHNNotReviewed);
        boolean areBenchmarksExpected = benchmarksAsOnPage.equals(expectedBenchmarks);
        //Let's check if benchmarks are actually correct
        //8. Click the Ignore button, VERIFY: That the "HN IGNORED*" entry will increase by 1
        boolean isIgnoreButtonPresent = headnotesPageAngular().doesElementExist(String.format(
                HeadnotesClassificationFragmentElementsAngular.IGNORE_BUTTON_BY_ROW, 2));
        Assertions.assertTrue(isIgnoreButtonPresent, "Ignore button is not available for 2nd headnote. It seems test data is corrupted");
        headnotesClassificationFragmentAngular().clickIgnoreForHeadnoteByRowNumber(2);
        headnotesBenchmarksFragmentAngular().waitForGridRefresh();
        int benchmarkHNIgnoredAfterIgnore = headnotesBenchmarksFragmentAngular().getBenchmarksHNIgnored();
        int benchmarkHNNotReviewedAfterIgnore = headnotesBenchmarksFragmentAngular().getBenchmarksHNNotReviewed();
        //classify button should disappear
        boolean didClassifyButtonDisappear = !headnotesPageAngular().doesElementExist(String.format(
                HeadnotesClassificationFragmentElementsAngular.CLASSIFY_BUTTON_BY_ROW, 2));
        //9. Click Unignore button, VERIFY: That the "HN IGNORED*" entry will increase by 1
        headnotesClassificationFragmentAngular().clickUnignoreForHeadnoteByRowNumber(2);
        headnotesBenchmarksFragmentAngular().waitForGridRefresh();
        int benchmarkHNIgnoredAfterUnignore = headnotesBenchmarksFragmentAngular().getBenchmarksHNIgnored();
        int benchmarkHNNotReviewedAfterUnignore = headnotesBenchmarksFragmentAngular().getBenchmarksHNNotReviewed();
        boolean didClassifyButtonAppear = headnotesPageAngular().doesElementExist(String.format(HeadnotesClassificationFragmentElementsAngular.CLASSIFY_BUTTON_BY_ROW, 2));
        //10. Click Classify button, VERIFY: Benchmarks change accordingly
        boolean isClassifyButtonPresent = headnotesPageAngular().doesElementExist(String.format(HeadnotesClassificationFragmentElementsAngular.CLASSIFY_BUTTON_BY_ROW, 2));
        Assertions.assertTrue(isClassifyButtonPresent, "Classify button is not available for 2nd headnote. It seems test data is corrupted");
        boolean isClassificationBlockAlreadyPresent = headnotesPageAngular().doesElementExist(String.format(
                HeadnotesClassificationFragmentElementsAngular.CLASSIFICATION_BLOCK_BY_ROW, 2) +
                HeadnotesClassificationFragmentElementsAngular.CLASSIFICATION_INNER_TABLE);
        Assertions.assertFalse(isClassificationBlockAlreadyPresent, "2nd headnote is already classified. It seems test data is corrupted");
        hierarchyTreeFragmentAngular().quickFind(searchString);
        headnotesClassificationFragmentAngular().clickClassifyForHeadnoteByRowNumber(2);
        try
        {
            headnotesPageAngular().waitForVisibleElement(String.format(
                    HeadnotesClassificationFragmentElementsAngular.CLASSIFICATION_BLOCK_BY_ROW, 2) +
                    HeadnotesClassificationFragmentElementsAngular.CLASSIFICATION_INNER_TABLE, 10);
        } catch (NoSuchElementException e)
        {
            Assertions.fail("No info on headnote being classified appeared after classification");
        }
        int benchmarkClassificationsAfterClassified = headnotesBenchmarksFragmentAngular().getBenchmarksClassifications();
        int benchmarkHNNotReviewedAfterClassified = headnotesBenchmarksFragmentAngular().getBenchmarksHNNotReviewed();
        int benchmarkHNClassifiedAfterClassified = headnotesBenchmarksFragmentAngular().getBenchmarksHNClassified();
        boolean didIgnoreButtonDisappear = !headnotesPageAngular().doesElementExist(String.format(
                HeadnotesClassificationFragmentElementsAngular.IGNORE_BUTTON_BY_ROW, 2));
        //11. Click the trashcan icon next to the blue hyper link created under the "CLASSIFICATION" column to remove the classification
        //VERIFY: Benchmarks change accordingly
        headnotesClassificationFragmentAngular().clickDeleteClassification(2, 1);
        boolean isAlertTextExpected = headnotesPageAngular().checkAlertTextMatchesGivenText(alertText);
        boolean didIgnoreButtonReappear = headnotesPageAngular().waitForElementExists(String.format(HeadnotesClassificationFragmentElementsAngular.IGNORE_BUTTON_BY_ROW, 2), 20);
        Assertions.assertTrue(didIgnoreButtonReappear, "Ignore button didn't reappear after classification was deleted");
        int benchmarkClassificationsAfterDeleted = headnotesBenchmarksFragmentAngular().getBenchmarksClassifications();
        int benchmarkHNNotReviewedAfterDeleted = headnotesBenchmarksFragmentAngular().getBenchmarksHNNotReviewed();
        int benchmarkHNClassifiedAfterDeleted = headnotesBenchmarksFragmentAngular().getBenchmarksHNClassified();

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(areBenchmarksExpected, "Benchmarks are not expected. The test data is corrupted."),
            () -> Assertions.assertEquals(benchmarkHNIgnoredAfterIgnore, benchmarkHNIgnored + 1, "HN ignored benchmark is not correct after headnote was ignored"),
            () -> Assertions.assertEquals(benchmarkHNNotReviewedAfterIgnore, benchmarkHNNotReviewed - 1, "HN Not Reviewed benchmark is not correct after " + "headnote was ignored"),
            () -> Assertions.assertTrue(didClassifyButtonDisappear, "Classify button didn't disappear after the headnote was ignored"),
            () -> Assertions.assertEquals(benchmarkHNIgnoredAfterUnignore, benchmarkHNIgnored, "HN ignored benchmark is not correct after " + "headnote was unignored"),
            () -> Assertions.assertEquals(benchmarkHNNotReviewedAfterUnignore, benchmarkHNNotReviewed, "HN Not Reviewed benchmark is not correct after " + "headnote was unignored"),
            () -> Assertions.assertTrue(didClassifyButtonAppear, "Classify button didn't reappear after the headnote was unignored"),
            () -> Assertions.assertEquals(benchmarkClassificationsAfterClassified, benchmarkClassifications + 1, "Classifications benchmark is not correct after " + "headnote was classified"),
            () -> Assertions.assertEquals(benchmarkHNNotReviewedAfterClassified, benchmarkHNNotReviewed - 1, "HN Not Reviewed benchmark is not correct after " + "headnote was classified"),
            () -> Assertions.assertEquals(benchmarkHNClassifiedAfterClassified,benchmarkHNClassified + 1, "HN Classified benchmark is not correct after " + "headnote was classified"),
            () -> Assertions.assertTrue(didIgnoreButtonDisappear, "Ignore button didn't disappear after the headnote was classified"),
            () -> Assertions.assertTrue(isAlertTextExpected, String.format("Alert text is not expected\n Expected: %s", alertText)),
            () -> Assertions.assertEquals(benchmarkClassificationsAfterDeleted,benchmarkClassifications, "Classifications benchmark is not correct after " + "classification was deleted"),
            () -> Assertions.assertEquals(benchmarkHNNotReviewedAfterDeleted, benchmarkHNNotReviewed, "HN Not Reviewed benchmark is not correct after " + "classification was deleted"),
            () -> Assertions.assertEquals(benchmarkHNClassifiedAfterDeleted, benchmarkHNClassified, "HN Classified benchmark is not correct after " + "classification was deleted")
        );
    }

    /**
     * HALCYONST-9470
     * 1. Open Subscribed Cases page for IOWA (Development)
     * 2. VERIFY: Subscribed Cases page is opened
     * 3. Find given case serial
     * 4. Click on the blue hyper link for that case
     * 5. VERIFY: The Classification/Headnote page is opened
     * 6. Click on the first headnote number hyper link in classification area (HN column)
     * 7. VERIFY: "Headnote Detail" pop up appears.
     * 8. Click each of the navigation buttons and VERIFY: Their functions are performed correctly
     * 9. VERIFY: "Save" button is disabled
     * 10. Change "Full text" field text to some testing value.
     * 11. VERIFY: "Save" button is enabled now
     * 12. Save changes
     * 13. VERIFY: Pop up closed and "Headnote updated!" message appeared in the left bottom side of the page.
     * 14. VERIFY: Headnote text has changed in classification pane
     */
    @ParameterizedTest(name = "Test: {displayName}; Index: {index}; Arguments: {arguments}")
    @CsvSource
            ({
                    "4",
                    "1"
            })
    @EDGE
    @LEGAL
    @LOG
    public void headnoteDetailsNavigateAndSaveTest(String headnoteNumber)
    {
        String caseTitle = "Test" + DateAndTimeUtils.getCurrentDateAndTimeyyyyMMddHHmm();
        String caseSerial = "1234-987655";
        String headnoteText = "Test text";
        String headnoteTextUpdated = "Updated text";
        int numberOfHeadnotes = 5;
        int headnoteNumberInt = Integer.parseInt(headnoteNumber);
        String expectedHeadnoteUpdatedAlertText = "Headnote updated!";

        //Mock up case with headnotes
        connection = BaseDatabaseUtils.connectToDatabaseUAT();
        NodAngularDatabaseUtils.insertCase(caseTitle, caseSerial, courtUuid, connection);
        String caseUuid = NodAngularDatabaseUtils.getCaseUuid(caseSerial, connection);
        NodAngularDatabaseUtils.subscribeToCase(caseUuid, contentSet, connection);
        String subscribedCaseUuid = NodAngularDatabaseUtils.getSubscribedCaseUuid(caseUuid, contentSet, connection);
        NodAngularDatabaseUtils.insertMultipleHeadnotesAndSubscribe(numberOfHeadnotes, caseUuid, subscribedCaseUuid, headnoteText, connection);

        try
        {
            //1. Open Subscribed cases page for IOWA (Development) and open case by serial number
            subscribedCasesPageAngular().openNodSubscribedCasesPage(ContentSets.IOWA_DEVELOPMENT);
            loginPage().logIn();
            Assertions.assertTrue(subscribedCasesPageAngular().isPageOpened(), "NOD Subscribed Cases page didn't open");
            subscribedCasesTablePage().openHeadnotesPageByCaseSerial(caseSerial);
            Assertions.assertTrue(headnotesPageAngular().isPageOpened(), "Headnotes page didn't open");

            //Click on given headnote number link
            headnotesClassificationFragmentAngular().clickHeadnoteLinkByRowNumber(headnoteNumberInt);
            Assertions.assertTrue(headnoteDetailsPopupAngular().isPageOpened(), "Headnote details popup doen't seem to open");

            //8. Click each of the navigation buttons and VERIFY: Their functions are performed correctly
            //click "next page" and verify next blueline is shown
            headnoteDetailsPopupAngular().clickGoToNextHeadnote();
            String nextPageHeadnoteNumber = headnoteDetailsPopupAngular().getHeadnoteNumber();
            String expectedNextHeadnoteNumber = String.format("%s of %s", headnoteNumberInt + 1, numberOfHeadnotes);
            boolean isNextHeadnoteNumberCorrect = nextPageHeadnoteNumber.equals(expectedNextHeadnoteNumber);

            //click "last page" and verify the last blueline is shown
            headnoteDetailsPopupAngular().clickGoToLastHeadnote();
            String lastPageHeadnoteNumber = headnoteDetailsPopupAngular().getHeadnoteNumber();
            String expectedLastHeadnoteNumber = String.format("%s of %s", numberOfHeadnotes, numberOfHeadnotes);
            boolean isLastHeadnoteNumberCorrect = lastPageHeadnoteNumber.equals(expectedLastHeadnoteNumber);

            //click "first page" and verify the first blueline is shown
            headnoteDetailsPopupAngular().clickGoToFirstHeadnote();
            String firstPageHeadnoteNumber = headnoteDetailsPopupAngular().getHeadnoteNumber();
            String expectedFirstHeadnoteNumber = String.format("1 of %s", numberOfHeadnotes);
            boolean isFirstHeadnoteNumberCorrect = firstPageHeadnoteNumber.equals(expectedFirstHeadnoteNumber);

            //click a page by number and verify blueline is shown
            headnoteDetailsPopupAngular().clickGoToHeadnoteByNumber(4);
            String headnoteNumberByPage = headnoteDetailsPopupAngular().getHeadnoteNumber();
            String expectedHeadnoteByPage = String.format("4 of %s", numberOfHeadnotes);
            boolean isSelectedHeadnoteNumberCorrect = headnoteNumberByPage.equals(expectedHeadnoteByPage);

            //Verify save button is disabled by default, then update the text in the pane, verify save button is now enabled
            boolean isSaveButtonDisabled = !headnoteDetailsPopupAngular().isSaveButtonEnabled();
            headnoteDetailsPopupAngular().sendTextToHeadnoteTextArea(headnoteTextUpdated);
            boolean isSaveButtonEnabled = headnoteDetailsPopupAngular().isSaveButtonEnabled();

            // Save and verify changes are saved, handle expected alerts "Headnote updated!" on bottom left
            headnoteDetailsPopupAngular().clickSave();
            boolean isNotificationShown = notificationPopupAngular().waitForNotification();
            String notificationText = notificationPopupAngular().getNotificationText();
            boolean isHeadnoteUpdatedAlertCorrect = expectedHeadnoteUpdatedAlertText.equals(notificationText);
            boolean didHeadnoteTextUpdateAfterSaving = headnotesClassificationFragmentAngular().getHeadnoteTextByRowNumber(4).equals(headnoteTextUpdated);

            Assertions.assertAll
                          (
                              () -> Assertions.assertTrue(isNextHeadnoteNumberCorrect, "Expected headnote didn't open when next headnote button is clicked"),
                              () -> Assertions.assertTrue(isLastHeadnoteNumberCorrect, "Expected headnote didn't open when the last headnote button is clicked"),
                              () -> Assertions.assertTrue(isFirstHeadnoteNumberCorrect, "Expected headnote didn't open when the first headnote button is clicked"),
                              () -> Assertions.assertTrue(isSelectedHeadnoteNumberCorrect, "Expected headnote didn't open when page number button is clicked"),
                              () -> Assertions.assertTrue(isSaveButtonDisabled, "Save button is not disabled by default"),
                              () -> Assertions.assertTrue(isSaveButtonEnabled, "Save button is not enabled after text edit"),
                              () -> Assertions.assertTrue(isNotificationShown, "Notification didn't show"),
                              () -> Assertions.assertTrue(isHeadnoteUpdatedAlertCorrect, "Notification text is not expected"),
                              () -> Assertions.assertTrue(didHeadnoteTextUpdateAfterSaving, "Headnote text was not updated when it should have been")
                          );
        }
        finally
        {
            NodAngularDatabaseUtils.deleteCase(caseUuid, contentSet, connection);
        }
    }

    /**
     * HALCYONST-9530
     * HALCYONST-9518
     * 1. Open Subscribed Cases page for IOWA (Development)
     * 2. VERIFY: Subscribed Cases page is opened
     * 3. Set a filter for Title column as "Contains" and type part of your case serial
     * 4. Click on the blue hyper link for your case case
     * 5. VERIFY: The Classification/Headnote page is opened
     * 6. VERIFY: All case information is correct (equals to what is stated on Subscribed Cases page)
     * 7. VERIFY: That the buttons under the title bar are selectable and are in this order Previous Case, Next Case,
     * Unignore All, Ignore All, Completed By and Date, Sign Out Case page
     * 8. Click Next Case
     * 9. VERIFY: next case is opened
     * 10. VERIFY: All case information is correct (equals to what is stated on Subscribed Cases page)
     * 11. Click Previous Case
     * 12. VERIFY: previous case is opened
     * 13. VERIFY: All case information is correct (equals to what is stated on Subscribed Cases page)
     */
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void headnoteUsCaseInformationTest()
    {
        String caseSerial = "2050-673955";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
        //1. Open Subscribed cases page for IOWA (Development)``
        subscribedCasesPageAngular().openNodSubscribedCasesPage(ContentSets.IOWA_DEVELOPMENT);
        loginPage().logIn();
        //2. VERIFY: Subscribed Cases page is opened
        boolean subscribedCasesPageIsOpened = subscribedCasesPageAngular().isPageOpened();
        Assertions.assertTrue(subscribedCasesPageIsOpened, "NOD Subscribed Cases page didn't open");
        //3. Set a filter for Title column as "Contains" and type part of your case serial
        subscribedCasesTablePage().openFilterOptionsForColumn(TableColumns.CASE_SERIAL_SUBSCRIBED_CASES);
        filterPopUp().selectFilterFromDropdown(FilterPopupElementsAngular.FIRST_FILTER_CONDITION, FilterOperator.CONTAINS);
        filterPopUp().typeTextToFilterField(FilterPopupElementsAngular.FIRST_INPUT_FIELD_FROM, caseSerial.substring(0, 6));
        casesTablePage().waitForTableToReload();
        //get all values you'll need if the future:
        List<List<String>> casesInformation = new ArrayList<>();
        List<String> caseTitles = new ArrayList<>();
        for (int i = 0; i <= 2; i++)
        {
            List<String> fullCasesInfo = subscribedCasesTablePage().getAllCellValuesAsTextByRowNumber(i);
            caseTitles.add(fullCasesInfo.get(10));
            List<String> caseInfoExpected = usCaseInformationFragmentAngular().sortSubscribedCasesCaseInformationToUSHeadnotesPageOrder(fullCasesInfo);
            casesInformation.add(caseInfoExpected);
        }
        //4. Click on the blue hyper link for that case
        subscribedCasesTablePage().clickCellTextByRowAndColumn(1, TableColumns.CASE_SERIAL_SUBSCRIBED_CASES);
        //5. VERIFY: The Classification/Headnote page is opened
        boolean headnotesPageIsOpened = headnotesPageAngular().isPageOpened();
        Assertions.assertTrue(headnotesPageIsOpened, "Headnotes page didn't open");
        //6. VERIFY: Case information is correct
        List<String> caseInfoActual = usCaseInformationFragmentAngular().getCaseInformationWithoutReporter();
        String caseTitleActual = headnotesPageAngular().getCaseTitle();
        boolean isCaseTitleCorrect = caseTitles.get(1).equals(caseTitleActual);
        boolean isCaseInfoForOursCaseCorrect = casesInformation.get(1).subList(1, 9)
                .equals(caseInfoActual.subList(1, 9)) && LocalDate.parse(casesInformation.get(1).get(0), formatter)
                .isEqual(LocalDate.parse(caseInfoActual.get(0), formatter));
        //7. VERIFY: That the buttons under the title bar are clickable and are in this order Previous Case, Next Case,
        //  Ignore All, Completed By and Date, Sign Out Case page and Unignore All button is disabled;
        boolean isPreviousCaseButtonDisabled = headnotesPageAngular().isPreviousCaseButtonDisabled();
        boolean isNextCaseButtonDisabled = headnotesPageAngular().isNextCaseButtonDisabled();
        boolean isUnignoreAllButtonDisabled = headnotesPageAngular().isUnignoreAllButtonDisabled();
        boolean isIgnoreAllButtonDisabled = headnotesPageAngular().isIgnoreAllButtonDisabled();
        boolean isCompletedByAndDateButtonDisabled = headnotesPageAngular().isCompletedByAndDateButtonDisabled();
        boolean isSignOutCaseButtonDisabled = headnotesPageAngular().isSignOutCaseButtonDisabled();
        //8. Click Previous Case Button and verify case information is correct
        headnotesPageAngular().clickPreviousCaseButton();
        usCaseInformationFragmentAngular().waitUntilCaseSerialChangesToFollowing(casesInformation.get(0).get(1));
        List<String> previousCaseInfoActual = usCaseInformationFragmentAngular().getCaseInformationWithoutReporter();
        String previousCaseTitleActual = headnotesPageAngular().getCaseTitle();
        boolean isPreviousCaseTitleCorrect = caseTitles.get(0).equals(previousCaseTitleActual);
        boolean isPreviousCaseInfoForOursCaseCorrect = casesInformation.get(0).subList(1, 9)
                .equals(previousCaseInfoActual.subList(1, 9)) && LocalDate.parse(casesInformation.get(0).get(0), formatter)
                .isEqual(LocalDate.parse(previousCaseInfoActual.get(0), formatter));
        //8. Click Next Case button twice and verify case information is correct
        headnotesPageAngular().clickNextCaseButton();
        usCaseInformationFragmentAngular().waitUntilCaseSerialChangesToFollowing(casesInformation.get(1).get(1));
        headnotesPageAngular().clickNextCaseButton();
        usCaseInformationFragmentAngular().waitUntilCaseSerialChangesToFollowing(casesInformation.get(2).get(1));
        List<String> nextCaseInfoActual = usCaseInformationFragmentAngular().getCaseInformationWithoutReporter();
        String nextCaseTitleActual = headnotesPageAngular().getCaseTitle();
        boolean isNextCaseTitleCorrect = caseTitles.get(2).equals(nextCaseTitleActual);
        boolean isNextCaseInfoForOursCaseCorrect = casesInformation.get(2).subList(1, 9)
                .equals(nextCaseInfoActual.subList(1, 9)) && LocalDate.parse(casesInformation.get(2).get(0), formatter)
                .isEqual(LocalDate.parse(nextCaseInfoActual.get(0), formatter));

        Assertions.assertAll
                (
                        () -> Assertions.assertTrue(isCaseTitleCorrect, String.format("The case title for case # %s is not correct. \nActual: %s, \nExpected: %s",
                                caseSerial, caseTitleActual, caseTitles.get(1))),
                        () -> Assertions.assertTrue(isCaseInfoForOursCaseCorrect, String.format("The case information for case # %s is not correct. \nActual: %s, \nExpected: %s",
                                caseSerial, caseInfoActual, casesInformation.get(1))),
                        () -> Assertions.assertFalse(isPreviousCaseButtonDisabled, "Previous Case button is disabled"),
                        () -> Assertions.assertFalse(isNextCaseButtonDisabled, "Next Case button is disabled"),
                        () -> Assertions.assertTrue(isUnignoreAllButtonDisabled, "Unignore All button is enabled"),
                        () -> Assertions.assertFalse(isIgnoreAllButtonDisabled, "Ignore All button is disabled"),
                        () -> Assertions.assertFalse(isCompletedByAndDateButtonDisabled, "Completed By and Date button is disabled"),
                        () -> Assertions.assertFalse(isSignOutCaseButtonDisabled, "Sign Out button is disabled"),
                        () -> Assertions.assertTrue(isPreviousCaseTitleCorrect, String.format("The case title for previous case # %s is not correct. \nActual: %s, \nExpected: %s",
                                casesInformation.get(0).get(1), previousCaseTitleActual, caseTitles.get(0))),
                        () -> Assertions.assertTrue(isPreviousCaseInfoForOursCaseCorrect, String.format("The case information for previous case # %s is not correct. \nActual: %s, \nExpected: %s",
                                casesInformation.get(0).get(1), previousCaseInfoActual, casesInformation.get(0))),
                        () -> Assertions.assertTrue(isNextCaseTitleCorrect, String.format("The case title for the next case # %s is not correct. \nActual: %s, \nExpected: %s",
                                casesInformation.get(2).get(1), nextCaseTitleActual, caseTitles.get(2))),
                        () -> Assertions.assertTrue(isNextCaseInfoForOursCaseCorrect, String.format("The case information for the next case # %s is not correct. \nActual: %s, \nExpected: %s",
                                casesInformation.get(2).get(1), nextCaseInfoActual, casesInformation.get(2)))
                );
    }

    /**
     * HALCYONST-9530
     * HALCYONST-9518
     * 1. Open Subscribed Cases page for IOWA (Development)
     * 2. VERIFY: Subscribed Cases page is opened
     * 3. Set a filter for Title column as "Contains" and type your case serial
     * 4. Click on the blue hyper link for your case case
     * 5. VERIFY: The Classification/Headnote page is opened
     * 6. VERIFY: That the buttons Previous Case, Next Case and Unignore All are disabled, while
     * //  Ignore All, Completed By and Date, Sign Out Case buttons are enabled;
     * 7. Click Ignore all button
     * 8. VERIFY: All headnotes are ignored
     * 9. Click Unignore All button
     * 10. VERIFY: All headnotes are unignored
     */
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void headnoteCaseInformationIgnoreAllAndUnignoreAllTest()
    {
        String caseSerial = "2050-616471";
        String notificationMessage = "Headnotes updated";
        //1. Open Subscribed cases page for IOWA (Development)``
        subscribedCasesPageAngular().openNodSubscribedCasesPage(ContentSets.IOWA_DEVELOPMENT);
        loginPage().logIn();
        //2. VERIFY: Subscribed Cases page is opened
        boolean subscribedCasesPageIsOpened = subscribedCasesPageAngular().isPageOpened();
        Assertions.assertTrue(subscribedCasesPageIsOpened, "NOD Subscribed Cases page didn't open");
        //3. Set a filter for Title column as "Contains" and type part of your case serial
        casesTablePage().filterByCaseSerialWaitForTableReload(caseSerial);
        int numberOfHeadnotes = Integer.parseInt(subscribedCasesTablePage().getCellTextByRowAndColumn(0,TableColumns.HN));
        //4. Click on the blue hyper link for that case
        subscribedCasesTablePage().clickCellTextByRowAndColumn(0, TableColumns.CASE_SERIAL_SUBSCRIBED_CASES);
        //5. VERIFY: The Classification/Headnote page is opened
        boolean headnotesPageIsOpened = headnotesPageAngular().isPageOpened();
        Assertions.assertTrue(headnotesPageIsOpened, "Headnotes page didn't open");
        //6. VERIFY: That the buttons Previous Case, Next Case and Unignore All are disabled, while
        //  Ignore All, Completed By and Date, Sign Out Case buttons are enabled;
        boolean isPreviousCaseButtonDisabled = headnotesPageAngular().isPreviousCaseButtonDisabled();
        boolean isNextCaseButtonDisabled = headnotesPageAngular().isNextCaseButtonDisabled();
        boolean isUnignoreAllButtonDisabled = headnotesPageAngular().isUnignoreAllButtonDisabled();
        boolean isIgnoreAllButtonEnabled = !headnotesPageAngular().isIgnoreAllButtonDisabled();
        Assertions.assertTrue(isIgnoreAllButtonEnabled, "Ignore All button is disabled. Test data is corrupted");
        boolean isCompletedByAndDateButtonEnabled = !headnotesPageAngular().isCompletedByAndDateButtonDisabled();
        boolean isSignOutCaseButtonEnabled = !headnotesPageAngular().isSignOutCaseButtonDisabled();
        //7. Click Ignore all button
        headnotesPageAngular().clickIgnoreAll();
        notificationPopupAngular().waitForNotification();
        String firstNotificationText = notificationPopupAngular().getNotificationText();
        notificationPopupAngular().closeNotification();
        //8. VERIFY: All headnotes are ignored
        boolean areAllHeadnotesIgnored = !headnotesClassificationFragmentAngular().areAllHeadnotesUnignored(numberOfHeadnotes);
        int benchmarkHNIgnoredAfterIgnore = headnotesBenchmarksFragmentAngular().getBenchmarksHNIgnored();
        int benchmarkHNNotReviewedAfterIgnore = headnotesBenchmarksFragmentAngular().getBenchmarksHNNotReviewed();
        //9. Click Unignore All button
        headnotesPageAngular().clickUnignoreAll();
        notificationPopupAngular().waitForNotification();
        //10. VERIFY: All headnotes are unignored
        headnotesClassificationFragmentAngular().refreshPage();
        boolean areAllHeadnotesUnignored = headnotesClassificationFragmentAngular().areAllHeadnotesUnignored(numberOfHeadnotes);
        int benchmarkHNIgnoredAfterUnignore = headnotesBenchmarksFragmentAngular().getBenchmarksHNIgnored();
        int benchmarkHNNotReviewedAfterUnignore = headnotesBenchmarksFragmentAngular().getBenchmarksHNNotReviewed();

        Assertions.assertAll
                (
                        () -> Assertions.assertTrue(isPreviousCaseButtonDisabled, "Previous Case button is disabled"),
                        () -> Assertions.assertTrue(isNextCaseButtonDisabled, "Next Case button is disabled"),
                        () -> Assertions.assertTrue(isUnignoreAllButtonDisabled, "Unignore All button is enabled"),
                        () -> Assertions.assertTrue(isCompletedByAndDateButtonEnabled, "Completed By and Date button is disabled"),
                        () -> Assertions.assertTrue(isSignOutCaseButtonEnabled, "Sign Out button is disabled"),
                        () -> Assertions.assertEquals(notificationMessage, firstNotificationText, "Notification text is not expected"),
                        () -> Assertions.assertEquals(numberOfHeadnotes, benchmarkHNIgnoredAfterIgnore, "The number of ignored headnotes in " +
                                "benchmark is not correct"),
                        () -> Assertions.assertEquals(0, benchmarkHNNotReviewedAfterIgnore, "The number of Not Reviewed headnotes " +
                                "in benchmark is not correct"),
                        () -> Assertions.assertTrue(areAllHeadnotesIgnored, "Not all headnotes are ignored"),
//                        () -> Assertions.assertEquals(notificationMessage, secondNotificationText, "Second notification text is not expected"),
                        () -> Assertions.assertTrue(areAllHeadnotesUnignored, "Not all headnotes are unignored"),
                        () -> Assertions.assertEquals(0, benchmarkHNIgnoredAfterUnignore, "The number of ignored headnotes " +
                                "in benchmark is not correct"),
                        () -> Assertions.assertEquals(numberOfHeadnotes, benchmarkHNNotReviewedAfterUnignore, "The number of Not Reviewed " +
                                "headnotes in benchmark is not correct")
                );
    }

    /**
     * HALCYONST-9530
     * HALCYONST-9518
     * HALCYONST-14757
     * 1. Open Subscribed Cases page for IOWA (Development)
     * 2. VERIFY: Subscribed Cases page is opened
     * 3. Set a filter for Title column as "Contains" and type your case serial
     * 4. Click on the blue hyper link for your case case
     * 5. VERIFY: The Classification/Headnote page is opened
     * 6. Click Completed By And Date button
     * 7. VERIFY: Error message is shown
     * 8. Click Ignore All button
     * 9. Verify notification message is shown
     * 10. VERIFY: Completed Date field is still empty
     * 11. Click Completed By And Date button
     * 12. VERIFY: notification message is shown
     * 13. VERIFY: Subscribed Cases page is opened
     * 14. VERIFY: Completed date on Subscribed Cases page is the same as on Headnotes page
     */
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void headnoteCaseInformationCompletedByAndDateTest()
    {
        String caseSerial = "2050-616472";
        String expectedNotificationMessage = "Headnotes updated";
        String expectedCompletionNotificationText = "Completed name and date updated";
        String expectedErrorMessage = "Error: All headnotes must have either been classified or ignored before the case can be marked as completed.";
        //1. Open Subscribed cases page for IOWA (Development)``
        subscribedCasesPageAngular().openNodSubscribedCasesPage(ContentSets.IOWA_DEVELOPMENT);
        loginPage().logIn();
        //2. VERIFY: Subscribed Cases page is opened
        boolean subscribedCasesPageIsOpened = subscribedCasesPageAngular().isPageOpened();
        Assertions.assertTrue(subscribedCasesPageIsOpened, "NOD Subscribed Cases page didn't open");
        //3. Set a filter for Title column as "Contains" and type part of your case serial
        subscribedCasesTablePage().filterByCaseSerialWaitForTableReload(caseSerial);
        int numberOfHeadnotes = Integer.parseInt(subscribedCasesTablePage().getCellTextByRowAndColumn(0,TableColumns.HN));
        //4. Click on the blue hyper link for that case
        subscribedCasesTablePage().clickCellTextByRowAndColumn(0,TableColumns.CASE_SERIAL_SUBSCRIBED_CASES);
        //5. VERIFY: The Classification/Headnote page is opened
        boolean headnotesPageIsOpened = headnotesPageAngular().isPageOpened();
        Assertions.assertTrue(headnotesPageIsOpened, "Headnotes page didn't open");
        //6. Click Completed By And Date button
        //First verify data is not corrupted
        boolean areAllHeadnotesUnignored = headnotesClassificationFragmentAngular().areAllHeadnotesUnignored(numberOfHeadnotes);
        if (!areAllHeadnotesUnignored)
        {
            headnotesPageAngular().clickUnignoreAll();
            notificationPopupAngular().closeNotification();
            headnotesBenchmarksFragmentAngular().waitForGridRefresh();
            boolean areAllHeadnotesUnignoredAgain = headnotesClassificationFragmentAngular().areAllHeadnotesUnignored(numberOfHeadnotes);
            Assertions.assertTrue(areAllHeadnotesUnignoredAgain, "Not all headnotes are unignored. Test data is corrupted");
        }
        headnotesPageAngular().clickCompletedByAndDate();
        //7. VERIFY: Error message is shown
        boolean isErrorMessageShown = notificationPopupAngular().waitForNotification();
        String errorText = notificationPopupAngular().getNotificationText();
        boolean isErrorMessageTextExpected = errorText.equals(expectedErrorMessage);
        notificationPopupAngular().closeNotification();
        DateAndTimeUtils.takeNap(DateAndTimeUtils.FIVE_SECONDS);
        //8. Click Ignore All button
        headnotesPageAngular().clickIgnoreAll();
        //9. Verify notification message is shown
        boolean isNotificationShownAfterIgnore = notificationPopupAngular().waitForNotification();
        String notificationTextAfterIgnore = notificationPopupAngular().getNotificationText();
        boolean isNotificationTextCorrectAfterIgnore = notificationTextAfterIgnore.equalsIgnoreCase(expectedNotificationMessage);
        notificationPopupAngular().closeNotification();
        DateAndTimeUtils.takeNap(DateAndTimeUtils.FIVE_SECONDS);
        //10. VERIFY: Completed Date field is still empty
        String completedDate = usCaseInformationFragmentAngular().getCaseInformationCompletedDate();
        boolean isCompletedDateEmpty = completedDate.isEmpty();
        //11. Click Completed By And Date button
        headnotesPageAngular().clickCompletedByAndDate();
        //12. VERIFY: notification message is shown
        boolean isNotificationShownAfterCompletion = notificationPopupAngular().waitForNotification();
        String notificationTextAfterCompletion = notificationPopupAngular().getNotificationText();
        boolean isNotificationTextCorrectAfterCompletion = notificationTextAfterCompletion.equals(expectedCompletionNotificationText);
        notificationPopupAngular().closeNotification();
        //13. VERIFY: Subscribed Cases page is opened
        boolean subscribedCasesPageIsOpenedAgain = subscribedCasesPageAngular().isPageOpened();
        Assertions.assertTrue(subscribedCasesPageIsOpenedAgain, "NOD Subscribed Cases page didn't open");
        //14. VERIFY: Completed date on Subscribed Cases page is the same as on Headnotes page
        LocalDate currentDate = LocalDate.now();
        String formattedCurrentDate = currentDate.format(DateTimeFormatter.ofPattern("M/d/yyyy"));
        String completedDateSubscribedCases = subscribedCasesTablePage().getCellTextByRowAndColumn(0,TableColumns.COMPLETED_DATE);
        String formattedCurrentDateForSubscribedCases = currentDate.format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        subscribedCasesPageAngular().scrollToRight("500");
        String completedBySubscribedCases = subscribedCasesTablePage().getCellTextByRowAndColumn(0,TableColumns.COMPLETED_BY);
        String expectedCompletedBy = user().getFirstname() + " " + user().getLastname();
        subscribedCasesTablePage().clickCellTextByRowAndColumn(0,TableColumns.CASE_SERIAL_SUBSCRIBED_CASES);
        headnotesPageIsOpened = headnotesPageAngular().isPageOpened();
        Assertions.assertTrue(headnotesPageIsOpened, "Headnotes page didn't open");
        completedDate = usCaseInformationFragmentAngular().getCaseInformationCompletedDate();
        boolean isCompletedDateCorrectAgain = completedDate.equals(formattedCurrentDate);
        String finalCompletedDate = completedDate;

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(isErrorMessageShown, "The error message wasn't shown"),
            () -> Assertions.assertTrue(isErrorMessageTextExpected, String.format("Error message text is not expected" + " \nActual: %s; \nExpected: %s", errorText, expectedErrorMessage)),
            () -> Assertions.assertTrue(isNotificationShownAfterIgnore, "Notification wasn't shown after headnotes were ignored"),
            () -> Assertions.assertTrue(isNotificationTextCorrectAfterIgnore, String.format("The notification text after headnotes were ignored" + " is not expected \nActual: %s; \nExpected: %s", notificationTextAfterIgnore, expectedNotificationMessage)),
            () -> Assertions.assertTrue(isCompletedDateEmpty, "Completed date field is empty"),
            () -> Assertions.assertTrue(isNotificationShownAfterCompletion, "Notification wasn't shown after Complete button was clicked"),
            () -> Assertions.assertTrue(isNotificationTextCorrectAfterCompletion, String.format("The notification text after Complete button was clicked" + " is not expected \nActual: %s; \nExpected: %s", notificationTextAfterCompletion, expectedCompletionNotificationText)),
            () -> Assertions.assertTrue(isCompletedDateCorrectAgain, String.format("Completed date is not correct after Complete button was clicked" + " \nActual: %s; \nExpected: %s", finalCompletedDate, formattedCurrentDate)),
            () -> Assertions.assertEquals(formattedCurrentDateForSubscribedCases, completedDateSubscribedCases, String.format("Completed date is not correct on Subscribed" + " Cases page \nActual: %s; \nExpected: %s", completedDateSubscribedCases, formattedCurrentDateForSubscribedCases)),
            () -> Assertions.assertEquals(completedBySubscribedCases, expectedCompletedBy, String.format("Completed By field is not correct on Subscribed" + " Cases page \nActual: %s; \nExpected: %s", completedBySubscribedCases, expectedCompletedBy))
        );
    }

    /**
     * HALCYONST-9530
     * HALCYONST-9518
     * 1. Open Subscribed Cases page for IOWA (Development)
     * 2. VERIFY: Subscribed Cases page is opened
     * 3. Set a filter for Title column as "Contains" and type your case serial
     * 4. Verify Sign out by column for our case is empty
     * 5. Click on the blue hyper link for your case case
     * 6. VERIFY: The Classification/Headnote page is opened
     * 7. Click the Sign Out Case button
     * 8. Verify notification message is shown
     * 9. VERIFY: User's initials are filled in the Sign Out by column.
     * 10. Go back to Subscribed Cases page
     * 11. VERIFY: User's initials are filled in the Sign Out by column on Subscribed Cases page are the same as on Headnotes page
     * 12. Click on the blue hyper link for your case
     * 13. Click the Clear Sign Out button
     * 14. VERIFY: notification message is shown
     * 15. VERIFY: Sign out column is now empty
     */

    @Test
    @EDGE
    @LEGAL
    @LOG
    public void headnoteCaseSignOutTest()
    {
        String expectedSignOutNotificationMessage = "Sign out by updated";
        String expectedClearNotificationText = "Clear sign out by completed";
        String caseTitle = "Test" + DateAndTimeUtils.getCurrentDateAndTimeyyyyMMddHHmm();
        String caseSerial = "1234-987666";

        //Mock case with a headnote that contains an enabled and disabled link
        connection = BaseDatabaseUtils.connectToDatabaseUAT();
        NodAngularDatabaseUtils.insertCase(caseTitle, caseSerial, courtUuid, connection);
        String caseUuid = NodAngularDatabaseUtils.getCaseUuid(caseSerial, connection);
        NodAngularDatabaseUtils.subscribeToCase(caseUuid, contentSet, connection);

        try
        {
            //1. Open Subscribed cases page for IOWA (Development), filter for the given case by serial number
            subscribedCasesPageAngular().openNodSubscribedCasesPage(ContentSets.IOWA_DEVELOPMENT);
            loginPage().logIn();
            Assertions.assertTrue(subscribedCasesPageAngular().isPageOpened(), "NOD Subscribed Cases page didn't open");
            subscribedCasesTablePage().filterByCaseSerialWaitForTableReload(caseSerial);

            //4. Verify Sign out by column for our case is empty
            String signOutByColumnValueInTheBeginning = subscribedCasesTablePage().getCellTextByRowAndColumn(0, TableColumns.SIGNED_OUT_BY);
            boolean isSignOutByEmpty = signOutByColumnValueInTheBeginning.equals("");
            Assertions.assertTrue(isSignOutByEmpty, "Test data is corrupted. The case has already been sign out");

            //5. Click on the blue hyper link for that case, and sign it out, verify the correct notiication is shown
            subscribedCasesTablePage().clickCellTextByRowAndColumn(0, TableColumns.CASE_SERIAL_CASES);
            Assertions.assertTrue(headnotesPageAngular().isPageOpened(), "Headnotes page didn't open");
            headnotesPageAngular().click(HeadnotesPageElementsAngular.SIGN_OUT_CASE_BUTTON);
            //8. Verify notification message is shown
            boolean isNotificationShownAfterSignOut = notificationPopupAngular().waitForNotification();
            String notificationTextAfterSignOut = notificationPopupAngular().getNotificationText();
            boolean isNotificationTextCorrectAfterSignOut = notificationTextAfterSignOut.equalsIgnoreCase(expectedSignOutNotificationMessage);
            notificationPopupAngular().closeNotification();
            DateAndTimeUtils.takeNap(DateAndTimeUtils.FIVE_SECONDS);

            //9. VERIFY: User's initials are filled in the Sign Out by column.
            String signOutByActual = usCaseInformationFragmentAngular().getCaseInformationSignOutBy();
            String signOutByExpected = user().getSubscribedCasesInitials();
            boolean areInitialsExpectedAfterSignOut = signOutByActual.equals(signOutByExpected);

            //10. Go back to Subscribed Cases page and verify user's initials are filled in the Sign Out by column on Subscribed Cases page are the same as on Headnotes page
            titleBarRibbonAngular().clickSubscribedCases();
            boolean subscribedCasesPageIsOpenedAgain = subscribedCasesPageAngular().isPageOpened();
            Assertions.assertTrue(subscribedCasesPageIsOpenedAgain, "NOD Subscribed Cases page didn't open");
            String signOutBySubscribedCases = subscribedCasesTablePage().getCellTextByRowAndColumn(0, TableColumns.SIGNED_OUT_BY);
            boolean areInitialsExpectedSubscribedCasesPage = signOutBySubscribedCases.equals(signOutByExpected);

            //12. Click on the blue hyper link for your case and  click the 'Clear Sign Out' button, verify the correct notification is shown
            subscribedCasesTablePage().clickCellTextByRowAndColumn(0, TableColumns.CASE_SERIAL_CASES);
            Assertions.assertTrue(headnotesPageAngular().isPageOpened(), "Headnotes page didn't open");
            headnotesPageAngular().clickClearSignOutCase();
            boolean isNotificationShownAfterClear = notificationPopupAngular().waitForNotification();
            String notificationTextAfterClear = notificationPopupAngular().getNotificationText();
            boolean isNotificationTextCorrectAfterClear = notificationTextAfterClear.equalsIgnoreCase(expectedClearNotificationText);
            notificationPopupAngular().closeNotification();
            DateAndTimeUtils.takeNap(DateAndTimeUtils.FIVE_SECONDS);

            //15. VERIFY: Sign out column is now empty
            String signOutByActualAfterClear = usCaseInformationFragmentAngular().getCaseInformationSignOutBy();
            boolean areInitialsExpectedAfterClear = signOutByActualAfterClear.equals("");

            Assertions.assertAll
                          (
                              () -> Assertions.assertTrue(isNotificationShownAfterSignOut, "The notification wasn't shown after sign out"),
                              () -> Assertions.assertTrue(isNotificationTextCorrectAfterSignOut, String.format("Notification text after sign out is not expected \nActual: %s; \nExpected: %s", notificationTextAfterSignOut, expectedSignOutNotificationMessage)),
                              () -> Assertions.assertTrue(areInitialsExpectedAfterSignOut, String.format("User initials in sign out by column is not expected \nActual: %s; \nExpected: %s", signOutByActual, signOutByExpected)),
                              () -> Assertions.assertTrue(areInitialsExpectedSubscribedCasesPage, String.format("User initials in sign out by column on Subscribed Cases page is not expected \nActual: %s; \nExpected: %s", signOutBySubscribedCases, signOutByExpected)),
                              () -> Assertions.assertTrue(isNotificationShownAfterClear, "The notification wasn't shown after sign out was cleared"),
                              () -> Assertions.assertTrue(isNotificationTextCorrectAfterClear, String.format("Notification text after sign out was cleared is not expected \nActual: %s; \nExpected: %s", notificationTextAfterClear, expectedClearNotificationText)),
                              () -> Assertions.assertTrue(areInitialsExpectedAfterClear, String.format("User initials in sign out by column on Subscribed Cases page is not expected \nActual: %s; \nExpected: %s", signOutBySubscribedCases, "none - this field should be empty"))
                          );
        }
        finally
        {
            NodAngularDatabaseUtils.deleteCase(caseUuid, contentSet, connection);
        }
    }

    /**
     * HALCYONST-9521
     * HALCYONST-9515
     * 1. Open Subscribed Cases page for Canada Ontario (Development)
     * 2. VERIFY: Subscribed Cases page is opened
     * 3. Set a filter for Title column as "Contains" and type part of your case serial
     * 4. Click on the blue hyper link for your case case
     * 5. VERIFY: The Classification/Headnote page is opened
     * 6. VERIFY: All case information is correct (equals to what is stated on Subscribed Cases page)
     * 7. VERIFY: That the buttons under the title bar are selectable and are in this order Previous Case, Next Case,
     * Unignore All, Ignore All, Completed By and Date, Sign Out Case page
     * 8. Click Next Case
     * 9. VERIFY: next case is opened
     * 10. VERIFY: All case information is correct (equals to what is stated on Subscribed Cases page)
     * 11. Click Previous Case
     * 12. VERIFY: previous case is opened
     * 13. VERIFY: All case information is correct (equals to what is stated on Subscribed Cases page)
     */
    @Test
    @EDGE
    @CARSWELL
    @LOG
    public void canadaHeadnoteCaseInformationTest()
    {
        String caseSerial = "1881138";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
        //1. Open Subscribed cases page for IOWA (Development)``
        subscribedCasesPageAngular().openNodSubscribedCasesPage(ContentSets.CANADA_ONTARIO_DEVELOPMENT);
        loginPage().logIn();
        //2. VERIFY: Subscribed Cases page is opened
        boolean subscribedCasesPageIsOpened = subscribedCasesPageAngular().isPageOpened();
        Assertions.assertTrue(subscribedCasesPageIsOpened, "NOD Subscribed Cases page didn't open");
        //3. Set a filter for Title column as "Contains" and type part of your case serial
        subscribedCasesTablePage().openFilterOptionsForColumn(TableColumns.CCDB);
        filterPopUp().selectFilterFromDropdown(FilterPopupElementsAngular.FIRST_FILTER_CONDITION, FilterOperator.CONTAINS);
        filterPopUp().typeTextToFilterField(FilterPopupElementsAngular.FIRST_INPUT_FIELD_FROM, caseSerial.substring(0, 2));
        //get cell to check if table is reloaded
        subscribedCasesTablePage().waitForTableToReload();
        //get all values you'll need if the future:
        List<List<String>> casesInformation = new ArrayList<>();
        List<String> caseTitles = new ArrayList<>();
        for (int i = 2; i <= 4; i++)
        {
            List<String> fullCasesInfo = casesTablePage().getAllCellValuesAsTextByRowNumber(i);
            caseTitles.add(fullCasesInfo.get(10));
            List<String> caseInfoExpected = canadaCaseInformationFragmentAngular()
                    .sortSubscribedCasesCaseInformationToCanadianHeadnotesPageOrder(fullCasesInfo);
            casesInformation.add(caseInfoExpected);
        }
        //4. Click on the blue hyper link for that case
        subscribedCasesTablePage().clickCellTextByRowAndColumn(3, TableColumns.CCDB);
        //5. VERIFY: The Classification/Headnote page is opened
        boolean headnotesPageIsOpened = headnotesPageAngular().isPageOpened();
        Assertions.assertTrue(headnotesPageIsOpened, "Headnotes page didn't open");
        //6. VERIFY: Case information is correct
        List<String> caseInfoActual = canadaCaseInformationFragmentAngular().getCaseInformationWithoutReporter();
        String caseTitleActual = headnotesPageAngular().getCaseTitle();
        boolean isCaseTitleCorrect = caseTitles.get(1).equals(caseTitleActual);
        boolean isCaseInfoForOursCaseCorrect = casesInformation.get(1).subList(1, 9)
                .equals(caseInfoActual.subList(1, 9)) && LocalDate.parse(casesInformation.get(1).get(0), formatter)
                .isEqual(LocalDate.parse(caseInfoActual.get(0), formatter));
        //7. VERIFY: That the buttons under the title bar are clickable and are in this order Previous Case, Next Case,
        //  Ignore All, Completed By and Date, Sign Out Case page and Unignore All button is disabled;
        boolean isPreviousCaseButtonDisabled = headnotesPageAngular().isPreviousCaseButtonDisabled();
        boolean isNextCaseButtonDisabled = headnotesPageAngular().isNextCaseButtonDisabled();
        boolean isUnignoreAllButtonDisabled = headnotesPageAngular().isUnignoreAllButtonDisabled();
        boolean isIgnoreAllButtonDisabled = headnotesPageAngular().isIgnoreAllButtonDisabled();
        boolean isCompletedByAndDateButtonDisabled = headnotesPageAngular().isCompletedByAndDateButtonDisabled();
        boolean isSignOutCaseButtonDisabled = headnotesPageAngular().isSignOutCaseButtonDisabled();
        //8. Click Previous Case Button and verify case information is correct
        headnotesPageAngular().clickPreviousCaseButton();
        canadaCaseInformationFragmentAngular().waitUntilCCDBChangesToFollowing(casesInformation.get(0).get(1));
        List<String> previousCaseInfoActual = canadaCaseInformationFragmentAngular().getCaseInformationWithoutReporter();
        String previousCaseTitleActual = headnotesPageAngular().getCaseTitle();
        boolean isPreviousCaseTitleCorrect = caseTitles.get(0).equals(previousCaseTitleActual);
        boolean isPreviousCaseInfoForOursCaseCorrect = casesInformation.get(0).subList(1, 9)
                .equals(previousCaseInfoActual.subList(1, 9)) && LocalDate.parse(casesInformation.get(0).get(0), formatter)
                .isEqual(LocalDate.parse(previousCaseInfoActual.get(0), formatter));
        //8. Click Next Case button twice and verify case information is correct
        headnotesPageAngular().clickNextCaseButton();
        canadaCaseInformationFragmentAngular().waitUntilCCDBChangesToFollowing(casesInformation.get(1).get(1));
        headnotesPageAngular().clickNextCaseButton();
        canadaCaseInformationFragmentAngular().waitUntilCCDBChangesToFollowing(casesInformation.get(2).get(1));
        List<String> nextCaseInfoActual = canadaCaseInformationFragmentAngular().getCaseInformationWithoutReporter();
        String nextCaseTitleActual = headnotesPageAngular().getCaseTitle();
        boolean isNextCaseTitleCorrect = caseTitles.get(2).equals(nextCaseTitleActual);
        boolean isNextCaseInfoForOursCaseCorrect = casesInformation.get(2).subList(1, 9)
                .equals(nextCaseInfoActual.subList(1, 9)) && LocalDate.parse(casesInformation.get(2).get(0), formatter)
                .isEqual(LocalDate.parse(nextCaseInfoActual.get(0), formatter));

        Assertions.assertAll
                (
                        () -> Assertions.assertTrue(isCaseTitleCorrect, String.format("The case title for case # %s is not correct. \nActual: %s, \nExpected: %s",
                                caseSerial, caseTitleActual, caseTitles.get(1))),
                        () -> Assertions.assertTrue(isCaseInfoForOursCaseCorrect, String.format("The case information for case # %s is not correct. \nActual: %s, \nExpected: %s",
                                caseSerial, caseInfoActual, casesInformation.get(1))),
                        () -> Assertions.assertFalse(isPreviousCaseButtonDisabled, "Previous Case button is disabled"),
                        () -> Assertions.assertFalse(isNextCaseButtonDisabled, "Next Case button is disabled"),
                        () -> Assertions.assertTrue(isUnignoreAllButtonDisabled, "Unignore All button is enabled"),
                        () -> Assertions.assertFalse(isIgnoreAllButtonDisabled, "Ignore All button is disabled"),
                        () -> Assertions.assertFalse(isCompletedByAndDateButtonDisabled, "Completed By and Date button is disabled"),
                        () -> Assertions.assertFalse(isSignOutCaseButtonDisabled, "Sign Out button is disabled"),
                        () -> Assertions.assertTrue(isPreviousCaseTitleCorrect, String.format("The case title for previous case # %s is not correct. \nActual: %s, \nExpected: %s",
                                casesInformation.get(0).get(1), previousCaseTitleActual, caseTitles.get(0))),
                        () -> Assertions.assertTrue(isPreviousCaseInfoForOursCaseCorrect, String.format("The case information for previous case # %s is not correct. \nActual: %s, \nExpected: %s",
                                casesInformation.get(0).get(1), previousCaseInfoActual, casesInformation.get(0))),
                        () -> Assertions.assertTrue(isNextCaseTitleCorrect, String.format("The case title for the next case # %s is not correct. \nActual: %s, \nExpected: %s",
                                casesInformation.get(2).get(1), nextCaseTitleActual, caseTitles.get(2))),
                        () -> Assertions.assertTrue(isNextCaseInfoForOursCaseCorrect, String.format("The case information for the next case # %s is not correct. \nActual: %s, \nExpected: %s",
                                casesInformation.get(2).get(1), nextCaseInfoActual, casesInformation.get(2)))
                );
    }

    /**
     * HALCYONST-9527
     * 1. Log onto the Iowa (Development) content set
     * 2. Go to NOD -> Subscribed Cases - angular
     * 3. VERIFY: Subscribed Cases page is opened
     * 4. Click on the blue hyper link of any of the cases
     * 5. VERIFY: The Classification/Headnote page is opened
     * 6. Click the "+" sign to expand each of these panes: "Synopsis Background", "Synopsis Holdings" and "Notes"
     * 7. VERIFY: These are expanded
     * 8. Click the "-" sign to collapse each of these panes
     * 9. VERIFY: These are collapsed
     */
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void headnoteSynopsisButtonsExpandCollapseTest()
    {
        String caseSerial = "2050-616470";
        String expectedBackground = "African-American-owned television-network operator brought action against cable television conglomerate, alleging that conglomerate systematically disfavored African-American-owned media companies in violation of \u00A7 1981 after conglomerate refused to carry operator's channels. The United States District Court for the Central District of California, Terry J. Hatter, Jr., Senior District Judge, 2016 WL 11652073, dismissed and entered final judgment for conglomerate. Operator appealed. The United States Court of Appeals for the Ninth Circuit, 743 Fed.Appx. 106, reversed. Certiorari was granted.";
        String expectedHoldings = "The Supreme Court, Justice Gorsuch, held that operator had the burden over the life of its \u00A7 1981 lawsuit of showing that operator's race was but-for cause of its injury, abrogating National Association of African American-Owned Media v. Charter Communications, Inc., 915 F.3d 617.";
        //1. Open Subscribed cases page for IOWA (Development)``
        subscribedCasesPageAngular().openNodSubscribedCasesPage(ContentSets.IOWA_DEVELOPMENT);
        loginPage().logIn();
        //2. VERIFY: Subscribed Cases page is opened
        boolean subscribedCasesPageIsOpened = subscribedCasesPageAngular().isPageOpened();
        Assertions.assertTrue(subscribedCasesPageIsOpened, "NOD Subscribed Cases page didn't open");
        //3. Set a filter for Title column as "Contains" and type part of your case serial
        //4. Click on the blue hyper link for that case
        subscribedCasesTablePage().openHeadnotesPageByCaseSerial(caseSerial);
        //5. VERIFY: The Classification/Headnote page is opened
        boolean headnotesPageIsOpened = headnotesPageAngular().isPageOpened();
        Assertions.assertTrue(headnotesPageIsOpened, "Headnotes page didn't open");
        //6. Click the "+" sign to expand each of these panes: "Synopsis Background", "Synopsis Holdings" and "Notes"
        synopsisFragmentAngular().expandSynopsisBackground();
        synopsisFragmentAngular().expandSynopsisHoldings();
        synopsisFragmentAngular().expandNotes();
        //7. VERIFY: These are expanded
        boolean isSynopsisBackgroundContentShown = synopsisFragmentAngular().waitForSynopsisBackgroundContentToShow();
        boolean isSynopsisHoldingsContentShown = synopsisFragmentAngular().waitForSynopsisHoldingsContentToShow();
        boolean isNotesContentShown = synopsisFragmentAngular().waitForNotesContentToShow();
        String synopsisBackgroundText = synopsisFragmentAngular().getSynopsisBackgroundContent();
        boolean isSynopsisBackgroundTextExpected = synopsisBackgroundText.contains(expectedBackground);
        String synopsisHoldingsText = synopsisFragmentAngular().getSynopsisHoldingsContent();
        boolean isSynopsisHoldingsTextExpected = synopsisHoldingsText.contains(expectedHoldings);
        //8. Click the "-" sign to collapse each of these panes
        synopsisFragmentAngular().collapseSynopsisBackground();
        synopsisFragmentAngular().collapseSynopsisHoldings();
        synopsisFragmentAngular().collapseNotes();
        //9. VERIFY: These are collapsed
        boolean isSynopsisBackgroundCollapsed = !synopsisFragmentAngular().checkIfSynopsisBackgroundContentIsShown();
        boolean isSynopsisHoldingsCollapsed = !synopsisFragmentAngular().checkIfSynopsisHoldingsContentIsShown();
        boolean areNotesCollapsed = !synopsisFragmentAngular().checkIfNotesContentIsShown();

        Assertions.assertAll
                (
                        () -> Assertions.assertTrue(isSynopsisBackgroundContentShown, "Synopsis background did not expand"),
                        () -> Assertions.assertTrue(isSynopsisHoldingsContentShown, "Synopsis holdings did not expand"),
                        () -> Assertions.assertTrue(isNotesContentShown, "Notes did not expand"),
                        () -> Assertions.assertTrue(isSynopsisBackgroundTextExpected, String.format("Synopsis background text was" +
                                " not expected \nActual: %s, \nExpected fragment: %s", synopsisBackgroundText, expectedBackground)),
                        () -> Assertions.assertTrue(isSynopsisHoldingsTextExpected, String.format("Synopsis holdings text was" +
                                " not expected \nActual: %s, \nExpected fragment: %s", synopsisHoldingsText, expectedHoldings)),
                        () -> Assertions.assertTrue(isSynopsisBackgroundCollapsed, "Synopsis background did not collapse"),
                        () -> Assertions.assertTrue(isSynopsisHoldingsCollapsed, "Synopsis holdings did not collapse"),
                        () -> Assertions.assertTrue(areNotesCollapsed, "Notes did not collapse")
                );
    }

    /**
     * ADO-725234: For USCA, the Synopsis Background and Synopsis Holdings should be expanded by default
     */
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void headnoteSynopsisBackgroundAndSynopsisHoldingsExpandedUSCATest()
    {
        String caseSerial = "2053-890878";
        String expectedBackground = "Class of 8,185 consumers with alerts in their credit files maintained by credit reporting agency";
        String expectedHoldings = "under Article III, only those plaintiffs who have been concretely harmed by a defendant's statutory violation may sue that private defendant over that violation in federal court";
        //1. Open Subscribed cases page for USCA(Development)`
        subscribedCasesPageAngular().openNodSubscribedCasesPage(ContentSets.USCA_DEVELOPMENT);
        loginPage().logIn();
        //2. VERIFY: Subscribed Cases page is opened
        boolean subscribedCasesPageIsOpened = subscribedCasesPageAngular().isPageOpened();
        Assertions.assertTrue(subscribedCasesPageIsOpened, "NOD Subscribed Cases page didn't open");
        //3. Set a filter for Title column as "Contains" and type part of your case serial
        //4. Click on the blue hyper link for that case
        subscribedCasesTablePage().openHeadnotesPageByCaseSerial(caseSerial);
        //5. VERIFY: The Classification/Headnote page is opened
        boolean headnotesPageIsOpened = headnotesPageAngular().isPageOpened();
        Assertions.assertTrue(headnotesPageIsOpened, "Headnotes page didn't open");
        //7. VERIFY: These are expanded
        boolean isSynopsisBackgroundContentShown = synopsisFragmentAngular().waitForSynopsisBackgroundContentToShow();
        boolean isSynopsisHoldingsContentShown = synopsisFragmentAngular().waitForSynopsisHoldingsContentToShow();
        String synopsisBackgroundText = synopsisFragmentAngular().getSynopsisBackgroundContent();
        boolean isSynopsisBackgroundTextExpected = synopsisBackgroundText.contains(expectedBackground);
        String synopsisHoldingsText = synopsisFragmentAngular().getSynopsisHoldingsContent();
        boolean isSynopsisHoldingsTextExpected = synopsisHoldingsText.contains(expectedHoldings);

        Assertions.assertAll
        (
                () -> Assertions.assertTrue(isSynopsisBackgroundContentShown, "Synopsis background did not expand"),
                () -> Assertions.assertTrue(isSynopsisHoldingsContentShown, "Synopsis holdings did not expand"),
                () -> Assertions.assertTrue(isSynopsisBackgroundTextExpected, String.format("Synopsis background text was" +
                        " not expected \nActual: %s, \nExpected fragment: %s", synopsisBackgroundText, expectedBackground)),
                () -> Assertions.assertTrue(isSynopsisHoldingsTextExpected, String.format("Synopsis holdings text was" +
                        " not expected \nActual: %s, \nExpected fragment: %s", synopsisHoldingsText, expectedHoldings))
        );
    }

    /**
     * HALCYONST-9527
     * 1. Open Subscribed cases page for IOWA (Development)``
     * 2. VERIFY: Subscribed Cases page is opened
     * 3. Set a filter for Title column as "Contains" and type your case serial
     * 4. Click on the blue hyper link of your case with empty note
     * 5. VERIFY: The Classification/Headnote page is opened
     * 6. Expand Notes pane above headnotes classification
     * 7. VERIFY: Notes text area is empty and the Save button is disabled
     * 8. Type in some text into the text field
     * 9. VERIFY: The Save button is now enabled
     * 10. Click the Save buttonn
     * 11. VERIFY: "Case Note update" message appeared and the Save button is disabled again
     * 12. Open Subscribed Cases page and find the same case
     * 13. VERIFY: "Edit note" is now in place of "Create note" in Notes column
     * 14. Click on "Edit note" icon
     * 15. VERIFY: The text you entered on step 8 is shown.
     * 16. Delete all text from the note, leaving the note blank
     * 17. Click the Save button
     * 18. VERIFY: The "Case Note updated" message appears
     * 19. VERIFY: The "Edit Note" icon is replaced with the "Create Note" icon appears
     */
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void headnoteSynopsisNotesTest()
    {
        String caseSerial = "2050-616470";
        String noteText = "Headnote Synopsis Note Test Text 123";

        //1. Open Subscribed cases page for IOWA (Development)
        subscribedCasesPageAngular().openNodSubscribedCasesPage(ContentSets.IOWA_DEVELOPMENT);
        loginPage().logIn();
        //2. VERIFY: Subscribed Cases page is opened
        boolean subscribedCasesPageIsOpened = subscribedCasesPageAngular().isPageOpened();
        Assertions.assertTrue(subscribedCasesPageIsOpened, "NOD Subscribed Cases page didn't open");
        //3. Set a filter for Title column as "Contains" and type part of your case serial
        subscribedCasesTablePage().filterByCaseSerialWaitForTableReload(caseSerial);
        //Verify there "create note" and in case it's not - clear the note;
        if (!subscribedCasesTablePage().isTextCreateNote(0, TableColumns.NOTES))
        {
            subscribedCasesTablePage().clickCellTextByRowAndColumn(0, TableColumns.NOTES);
            notesPopupAngular().waitForPopup();
            notesPopupAngular().clearTextArea();
            notesPopupAngular().clickSaveButton();
            notificationPopupAngular().waitForNotification();
            notificationPopupAngular().closeNotification();
        }

        //4. Click on the blue hyper link of your case with empty note
        subscribedCasesTablePage().clickCellTextByRowAndColumn(0, TableColumns.CASE_SERIAL_SUBSCRIBED_CASES);
        //5. VERIFY: The Classification/Headnote page is opened
        boolean headnotesPageIsOpened = headnotesPageAngular().isPageOpened();
        Assertions.assertTrue(headnotesPageIsOpened, "Headnotes page didn't open");
        //6. Expand Notes pane above headnotes classification
        synopsisFragmentAngular().expandNotes();
        boolean notesContentIsShown = synopsisFragmentAngular().waitForNotesContentToShow();
        //7. VERIFY: Save button is disabled
        boolean isSaveButtonDisabledByDefault = !synopsisFragmentAngular().isNotesSaveButtonEnabled();
        //8. Type in some text into the text field
        synopsisFragmentAngular().typeTextIntoNotesTextArea(noteText);
        //9. VERIFY: The Save button is now enabled
        boolean isSaveButtonEnabledAfterTextEntry = synopsisFragmentAngular().isNotesSaveButtonEnabled();
        //10. Click the Save button
        synopsisFragmentAngular().clickNotesSaveButton();
        //11. VERIFY: "Case Note update" message appeared and the Save button is disabled again
        boolean isNotificationShownAfterTextSaved = notificationPopupAngular().waitForNotification();
        notificationPopupAngular().closeNotification();
        //12. Open Subscribed Cases page and find the same case
        subscribedCasesPageAngular().openNodSubscribedCasesPage(ContentSets.IOWA_DEVELOPMENT);
        subscribedCasesTablePage().filterByCaseSerialWaitForTableReload(caseSerial);
        //13. VERIFY: "Edit note" is now in place of "Create note" in Notes column
        boolean didIconChangedToEditNote = subscribedCasesTablePage().isTextEditNote(0, TableColumns.NOTES);
        //14. Click on "Edit note" icon
        subscribedCasesTablePage().clickCellTextByRowAndColumn(0, TableColumns.NOTES);
        notesPopupAngular().waitForPopup();
        //15. VERIFY: The text you entered on step 8 is shown.
        String noteTextInPopup = notesPopupAngular().getNoteText();
        boolean isTextInPopupExpected = noteTextInPopup.equals(noteText);
        //16. Delete all text from the note, leaving the note blank
        notesPopupAngular().clearTextArea();
        //17. Click the Save button
        notesPopupAngular().clickSaveButton();
        //18. VERIFY: The "Case Note updated" message appears
        boolean isNotificationShownAfterTextDeleted = notificationPopupAngular().waitForNotification();
        notificationPopupAngular().closeNotification();
        //19. VERIFY: The "Edit Note" icon is replaced with the "Create Note" icon appears
        boolean isIconCreateNote = subscribedCasesTablePage().isTextCreateNote(0, TableColumns.NOTES);

        Assertions.assertAll
                (
                        () -> Assertions.assertTrue(notesContentIsShown, "Notes content was not shown"),
                        () -> Assertions.assertTrue(isSaveButtonDisabledByDefault, "Notes save button is not disabled by default"),
                        () -> Assertions.assertTrue(isSaveButtonEnabledAfterTextEntry, "Notes save button had not been enabled after text was entered"),
                        () -> Assertions.assertTrue(isNotificationShownAfterTextSaved, "Notification had not been not shown after text was entered"),
                        () -> Assertions.assertTrue(didIconChangedToEditNote, "Icon didn't change to Edit note"),
                        () -> Assertions.assertTrue(isTextInPopupExpected, String.format("Text in note popup is not expected. " +
                                "\nExpected: %s, \nActual: %s", noteText, noteTextInPopup)),
                        () -> Assertions.assertTrue(isNotificationShownAfterTextDeleted, "Notification had not been not shown after text was deleted"),
                        () -> Assertions.assertTrue(isIconCreateNote, "Icon in Notes column didn't change to Create icon")
                );
    }

    /**
     * HALCYONST-9527
     * 1. Open Subscribed cases page for IOWA (Development)``
     * 2. VERIFY: Subscribed Cases page is opened
     * 3. Set a filter for Title column as "Contains" and type your case serial
     * 4. Click on the blue hyper link of your case with empty note
     * 5. VERIFY: The NOD classification headnote page is opened
     * 6. VERIFY: On the left side of the page appears a hierarchy tree for the content set that you signed into
     * 7. Locate a node and expand it few levels down
     * 8. VERIFY: Node can be expanded
     * 9. Get down to section node and expand it
     * 10. VERIFY: XND and BL ANALYSI nodes are automatically expanded
     * 11. Collapse all nodes up to the highest ancestor
     * 12. VERIFY: all nodes are collapsed
     */

    @Test
    @EDGE
    @LEGAL
    @LOG
    public void headnoteHierarchyTreeTest()
    {
        String caseSerial = "2045-639990";
        String gradeLevelSubTitle = "Subt. 1 SOVEREIGNTY [Chs. 1-1D]";
        String chapter = "Ch. 1 SOVEREIGNTY AND JURISDICTION OF THE STATE";
        String section = "= 1.3 Concurrent jurisdiction";
        String xnd = "XND ";
        String bluelineAnalysis = "BL ANALYSI ";
        //1. Open Subscribed cases page for IOWA (Development)
        subscribedCasesPageAngular().openNodSubscribedCasesPage(ContentSets.IOWA_DEVELOPMENT);
        loginPage().logIn();
        //2. VERIFY: Subscribed Cases page is opened
        boolean subscribedCasesPageIsOpened = subscribedCasesPageAngular().isPageOpened();
        Assertions.assertTrue(subscribedCasesPageIsOpened, "NOD Subscribed Cases page didn't open");
        //3. Set a filter for Title column as "Contains" and type part of your case serial
        //4. Click on the blue hyper link of your case with empty note
        casesTablePage().openHeadnotesPageByCaseSerial(caseSerial);
        //5. VERIFY: The Classification/Headnote page is opened
        boolean headnotesPageIsOpened = headnotesPageAngular().isPageOpened();
        Assertions.assertTrue(headnotesPageIsOpened, "Headnotes page didn't open");
        //6. VERIFY: On the left side of the page appears a hierarchy tree for the content set that you signed into
        boolean isHierarchyTreeShown = hierarchyTreeFragmentAngular().isHierarchyTreeDisplayed();
        boolean isContentSetOpened = headnotesPageAngular().isElementDisplayed(String.format
                (HierarchyTreeFragmentElementsAngular.SPAN_WITH_TEXT, EXPECTED_ROOT_ELEMENTS_TEXT));
        //7. Locate a node and expand it few levels down
        //8. VERIFY: Node can be expanded
        //9. Get down to section node and expand it
        //10. VERIFY: XND and BL ANALYSI nodes are automatically expanded
        boolean gradeLevelTitleIsCollapsedByDefault = hierarchyTreeFragmentAngular().isNodeCollapsed(GRADE_LEVEL_TITLE);

        hierarchyTreeFragmentAngular().expandNodeWithText(GRADE_LEVEL_TITLE);
        boolean gradeLevelTitleIsExpanded = hierarchyTreeFragmentAngular().isNodeExpanded(GRADE_LEVEL_TITLE);

        hierarchyTreeFragmentAngular().expandNodeWithText(gradeLevelSubTitle);
        boolean gradeLevelSubTitleIsExpanded = hierarchyTreeFragmentAngular().isNodeExpanded(gradeLevelSubTitle);

        hierarchyTreeFragmentAngular().expandNodeWithText(chapter);
        boolean chapterIsExpanded = hierarchyTreeFragmentAngular().isNodeExpanded(chapter);

        hierarchyTreeFragmentAngular().expandNodeWithText(section);
        boolean sectionIsExpanded = hierarchyTreeFragmentAngular().isNodeExpanded(section);
        boolean xndIsExpanded = hierarchyTreeFragmentAngular().isNodeExpanded(xnd);
        boolean bluelineAnalysisIsExpanded = hierarchyTreeFragmentAngular().isNodeExpanded(bluelineAnalysis);
        //11. Collapse all nodes up to the highest ancestor
        //12. VERIFY: all nodes are collapsed
        hierarchyTreeFragmentAngular().collapseNodeWithText(section);
        boolean sectionIsCollapsed = hierarchyTreeFragmentAngular().isNodeCollapsed(section);

        hierarchyTreeFragmentAngular().collapseNodeWithText(chapter);
        boolean chapterIscollapsed = hierarchyTreeFragmentAngular().isNodeCollapsed(chapter);

        hierarchyTreeFragmentAngular().collapseNodeWithText(gradeLevelSubTitle);
        boolean gradeLevelSubTitleIscollapsed = hierarchyTreeFragmentAngular().isNodeCollapsed(gradeLevelSubTitle);

        hierarchyTreeFragmentAngular().collapseNodeWithText(GRADE_LEVEL_TITLE);
        boolean gradeLevelTitleIscollapsed = hierarchyTreeFragmentAngular().isNodeCollapsed(GRADE_LEVEL_TITLE);

        Assertions.assertAll
                (
                        () -> Assertions.assertTrue(isHierarchyTreeShown, "Notes content was not shown"),
                        () -> Assertions.assertTrue(isContentSetOpened, String.format("Root element of the content set is not found in navigation tree. " +
                                "Root element: \"%s\"", EXPECTED_ROOT_ELEMENTS_TEXT)),
                        () -> Assertions.assertTrue(gradeLevelTitleIsCollapsedByDefault, "Highest grade level node is not collapsed by default"),
                        () -> Assertions.assertTrue(gradeLevelTitleIsExpanded, "Highest grade level  Title node is not expanded"),
                        () -> Assertions.assertTrue(gradeLevelSubTitleIsExpanded, "Grade level  SubTitle node is not expanded"),
                        () -> Assertions.assertTrue(chapterIsExpanded, "Chapter node is not expanded"),
                        () -> Assertions.assertTrue(sectionIsExpanded, "Section node is not expanded"),
                        () -> Assertions.assertTrue(xndIsExpanded, "XND node is not expanded"),
                        () -> Assertions.assertTrue(bluelineAnalysisIsExpanded, "Blueline Analysis node is not expanded"),
                        () -> Assertions.assertTrue(sectionIsCollapsed, "Section node is not collapsed"),
                        () -> Assertions.assertTrue(chapterIscollapsed, "Chapter node is not collapsed"),
                        () -> Assertions.assertTrue(gradeLevelSubTitleIscollapsed, "Grade level  SubTitle node is not collapsed"),
                        () -> Assertions.assertTrue(gradeLevelTitleIscollapsed, "Highest grade level  Title node is not collapsed")
                );
    }

    /**
     * HALCYONST-9527
     * 1. Open Subscribed cases page for IOWA (Development)
     * 2. VERIFY: Subscribed Cases page is opened
     * 3. Set a filter for Title column as "Contains" and type your case serial
     * 4. Click on the blue hyper link of your case with empty note
     * 5. VERIFY: The NOD classification headnote page is opened
     * 6. VERIFY: On the left side of the page appears a hierarchy tree for the content set that you signed into
     * 7. Locate a node and expand it few levels down until you reach repealed document
     * 8. VERIFY: Node can be expanded
     * 9. VERIFY: Repealed document is highlighted in redish pink
     */
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void headnoteHierarchyTreeRepealedDocumentTest()
    {
        String caseSerial = "2045-639990";
        String gradeLevelSubTitle = "Subt. 2 LEGISLATIVE BRANCH [Chs. 2-5]";
        String chapter = "Ch. 2 GENERAL ASSEMBLY";
        String gradeAt = "@ LEGISLATIVE FISCAL B LEGISLATIVE FISCAL BUREAU";
        String section = "= 2.48 Repealed by Acts 2003 (80 G.A.) ch. 35, \u00A7 47, eff. April 14, 2003";
        //1. Open Subscribed cases page for IOWA (Development)
        subscribedCasesPageAngular().openNodSubscribedCasesPage(ContentSets.IOWA_DEVELOPMENT);
        loginPage().logIn();
        //2. VERIFY: Subscribed Cases page is opened
        boolean subscribedCasesPageIsOpened = subscribedCasesPageAngular().isPageOpened();
        Assertions.assertTrue(subscribedCasesPageIsOpened, "NOD Subscribed Cases page didn't open");
        //3. Set a filter for Title column as "Contains" and type part of your case serial
        //4. Click on the blue hyper link of your case with empty note
        subscribedCasesTablePage().openHeadnotesPageByCaseSerial(caseSerial);
        //5. VERIFY: The Classification/Headnote page is opened
        boolean headnotesPageIsOpened = headnotesPageAngular().isPageOpened();
        Assertions.assertTrue(headnotesPageIsOpened, "Headnotes page didn't open");
        //6. VERIFY: On the left side of the page appears a hierarchy tree for the content set that you signed into
        boolean isHierarchyTreeShown = hierarchyTreeFragmentAngular().isHierarchyTreeDisplayed();
        boolean isContentSetOpened = headnotesPageAngular().isElementDisplayed(String.format
                (HierarchyTreeFragmentElementsAngular.SPAN_WITH_TEXT, EXPECTED_ROOT_ELEMENTS_TEXT));
        //7. Locate a node and expand it few levels down
        //8. VERIFY: Node can be expanded
        //9. Get down to section node and expand it
        //10. VERIFY: XND and BL ANALYSI nodes are automatically expanded
        boolean gradeLevelTitleIsCollapsedByDefault = hierarchyTreeFragmentAngular().isNodeCollapsed(GRADE_LEVEL_TITLE);
        hierarchyTreeFragmentAngular().expandNodeWithText(GRADE_LEVEL_TITLE);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        hierarchyTreeFragmentAngular().expandNodeWithText(gradeLevelSubTitle);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.ONE_SECOND);
        hierarchyTreeFragmentAngular().expandNodeWithText(chapter);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.ONE_SECOND);
        hierarchyTreeFragmentAngular().expandNodeWithText(gradeAt);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.ONE_SECOND);
        boolean isNodeRepealed = hierarchyTreeFragmentAngular().isNodeRepealed(section);
        Assertions.assertTrue(isNodeRepealed, "The section node is not repealed. Test data is corrupted");

        boolean isNodeHighlightedInRedishPink = hierarchyTreeFragmentAngular().isNodeRedishPink(section);

        Assertions.assertAll
                (
                        () -> Assertions.assertTrue(isHierarchyTreeShown, "Notes content was not shown"),
                        () -> Assertions.assertTrue(isContentSetOpened, String.format("Root element of the content set is not found in navigation tree. " +
                                "Root element: \"%s\"", EXPECTED_ROOT_ELEMENTS_TEXT)),
                        () -> Assertions.assertTrue(gradeLevelTitleIsCollapsedByDefault, "Highest grade level node is not collapsed by default"),
                        () -> Assertions.assertTrue(isNodeHighlightedInRedishPink, "Repealed node is not highlighted with redish pink")
                );
    }

    /**
     * HALCYONST-9410
     * 1. Open Subscribed cases page for IOWA (Development)
     * 2. VERIFY: Subscribed Cases page is opened
     * 3. Set a filter for Title column as "Contains" and type your case serial
     * 4. Click on the blue hyper link of your case with empty note
     * 5. VERIFY: The NOD classification headnote page is opened
     * 6. VERIFY: The Classify button and the Ignore button are present, and the Classify button is on top of the Ignore button
     * 7. Locate and select a Blueline document from the tree on the right hand side
     * 8. Click the Classify button for the first Headnote
     * 9. VERIFY: The Headnote that you just classified has a blue hyper link to the Blueline document in the Classification column
     * 10. Click the trashcan icon next to the blue hyper link that appears for the classified Headnote
     * 11. VERIFY: The Headnote is no longer classified, the blue hyper link dissappears, the Classify button and Ignore button are present
     */
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void headnoteClassificationTest()
    {
        String caseSerial = "2045-085248";
        String searchString = "123.3 3";
        String nodeText = "BL 3 Spirits";
        String otherNodeText = "BL 6 Person";
        String expectedClassificationText = "T. IV; = 123.3; BL 3";
        String alertText = "Are you sure you want to un-classify this headnote?";
        int headnotesNumber = 2;

        //1. Open Subscribed cases page for IOWA (Development), open given case by serial number
        subscribedCasesPageAngular().openNodSubscribedCasesPage(ContentSets.IOWA_DEVELOPMENT);
        loginPage().logIn();
        Assertions.assertTrue(subscribedCasesPageAngular().isPageOpened(), "NOD Subscribed Cases page didn't open");
        subscribedCasesTablePage().openHeadnotesPageByCaseSerial(caseSerial);
        Assertions.assertTrue(headnotesPageAngular().isPageOpened(), "Headnotes page didn't open");

        // Make sure headnotes in the case start as unignored and unclassified (clean data)
        for (int i = 1; i <= headnotesNumber; i++)
        {
            if (headnotesClassificationFragmentAngular().isHeadnoteClassified(i))
            {
                headnotesClassificationFragmentAngular().clickDeleteClassification(i, 1);
                headnotesPage().acceptAlert();
            }
        }
        if (!headnotesClassificationFragmentAngular().areAllHeadnotesUnignored(headnotesNumber))
        {
            headnotesPage().clickUnignoreAllHeadnotesButton();
        }

        //7. Locate and select a Blueline document from the tree on the right hand side
        hierarchyTreeFragmentAngular().quickFind(searchString);
        hierarchyTreeFragmentAngular().waitForElementScrolledTo(nodeText);

        //Classify the first headnote, verify the classification link works
        headnotesClassificationFragmentAngular().clickClassifyForHeadnoteByRowNumber(1);
        boolean isFirstHeadnoteClassified = headnotesClassificationFragmentAngular().isHeadnoteClassified(1);
        hierarchyTreeFragmentAngular().clickNode(otherNodeText);
        hierarchyTreeFragmentAngular().waitForElementScrolledTo(otherNodeText);
        boolean isOtherNodeSelected = hierarchyTreeFragmentAngular().isNodeSelected(otherNodeText);
        headnotesClassificationFragmentAngular().clickClassificationText(1, 1);
        hierarchyTreeFragmentAngular().waitForElementScrolledTo(nodeText);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.THREE_SECONDS);
        boolean isNodeSelected = hierarchyTreeFragmentAngular().isNodeSelected(nodeText);
        String classificationText = headnotesClassificationFragmentAngular().getClassificationText(1, 1);
        boolean classificationTextIsExpected = classificationText.equals(expectedClassificationText);

        // Remove the classification, verify the link disappears and the classify and ignore buttons are again present
        headnotesClassificationFragmentAngular().clickDeleteClassification(1, 1);
        boolean isAlertTextExpected = headnotesPageAngular().checkAlertTextMatchesGivenText(alertText);
        headnotesPageAngular().waitForGridRefresh();
        headnotesPageAngular().waitForPageLoaded();
        boolean isFirstHeadnoteUnClassified = !headnotesClassificationFragmentAngular().isHeadnoteClassified(1);
        boolean areAllHeadnotesUnignoredAgain = headnotesClassificationFragmentAngular().areAllHeadnotesUnignored(2);
        boolean areAllHeadnotesUnclassifiedAgain = headnotesClassificationFragmentAngular().areAllHeadnotesUnclassified(2);

        Assertions.assertAll
                      (
                          () -> Assertions.assertTrue(isFirstHeadnoteClassified, "First headnote wasn't classified"),
                          () -> Assertions.assertTrue(isOtherNodeSelected, String.format("Node %s is not selected", otherNodeText)),
                          () -> Assertions.assertTrue(isNodeSelected, String.format("Node %s is not selected", nodeText)),
                          () -> Assertions.assertTrue(classificationTextIsExpected, String.format("Classification text is not expected. \nExpected: %s," + "\nActual: %s", expectedClassificationText, classificationText)),
                          () -> Assertions.assertTrue(isAlertTextExpected, String.format("Alert text is not expected. \nExpected: %s.", alertText)),
                          () -> Assertions.assertTrue(isFirstHeadnoteUnClassified, "First headnote wasn't classified"),
                          () -> Assertions.assertTrue(areAllHeadnotesUnignoredAgain, "All headnotes are not unignored in the end of the test"),
                          () -> Assertions.assertTrue(areAllHeadnotesUnclassifiedAgain, "All headnotes are not unclassified in the end of the test")
                      );
    }

    /**
     * HALCYONST-9410
     * 1. Open Subscribed cases page for IOWA (Development)
     * 2. VERIFY: Subscribed Cases page is opened
     * 3. Set a filter for Title column as "Contains" and type your case serial
     * 4. Click on the blue hyper link of your case with empty note
     * 5. VERIFY: The NOD classification headnote page is opened
     * 6. VERIFY: The Classify button and the Ignore button are present, and the Classify button is on top of the Ignore button
     * 7. Click the Ignore button
     * 8. VERIFY: The headnote is now ignored, and the Unignore button appeared
     * 9. Click the Unignore button
     * 10. VERIFY: Ignore & Classify button appeared again
     */
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void headnoteIgnoreTest()
    {
        String caseSerial = "2051-399609";
        //1. Open Subscribed cases page for IOWA (Development)
        subscribedCasesPageAngular().openNodSubscribedCasesPage(ContentSets.IOWA_DEVELOPMENT);
        loginPage().logIn();
        //2. VERIFY: Subscribed Cases page is opened
        boolean subscribedCasesPageIsOpened = subscribedCasesPageAngular().isPageOpened();
        Assertions.assertTrue(subscribedCasesPageIsOpened, "NOD Subscribed Cases page didn't open");
        //3. Set a filter for Title column as "Contains" and type part of your case serial
        //4. Click on the blue hyper link of your case with empty note
        subscribedCasesTablePage().openHeadnotesPageByCaseSerial(caseSerial);
        //5. VERIFY: The Classification/Headnote page is opened
        boolean headnotesPageIsOpened = headnotesPageAngular().isPageOpened();
        Assertions.assertTrue(headnotesPageIsOpened, "Headnotes page didn't open");
        //6. VERIFY: The Classify button and the Ignore button are present, and the Classify button is on top of the Ignore button
        boolean areAllHeadnotesUnignored = headnotesClassificationFragmentAngular().areAllHeadnotesUnignored(2);
        boolean areAllHeadnotesUnclassified = headnotesClassificationFragmentAngular().areAllHeadnotesUnclassified(2);
        Assertions.assertTrue(areAllHeadnotesUnignored, "Some headnotes are ignored. Test data is corrupted.");
        Assertions.assertTrue(areAllHeadnotesUnclassified, "Some headnotes are classified. Test data is corrupted.");
        //7. Click the Ignore button
        headnotesClassificationFragmentAngular().clickIgnoreForHeadnoteByRowNumber(1);
        //8. VERIFY: The headnote is now ignored, and the Unignore button appeared
        boolean isHeadnoteIgnored = headnotesClassificationFragmentAngular().isHeadnoteIgnored(1);
        //9. Click the Unignore button
        headnotesClassificationFragmentAngular().clickUnignoreForHeadnoteByRowNumber(1);
        //10. VERIFY: Ignore & Classify button appeared again
        boolean isHeadnoteUnignored = headnotesClassificationFragmentAngular().isHeadnoteUnignored(1);

        Assertions.assertAll
                (
                        () -> Assertions.assertTrue(isHeadnoteIgnored, "Headnote is not ignored"),
                        () -> Assertions.assertTrue(isHeadnoteUnignored, "Headnote is not unignored")
                );
    }


    /**
     * HALCYONST-10777
     * 1. Open Subscribed cases page for Canada Ontario (Development)
     * 2. VERIFY: Subscribed Cases page is opened
     * 3. Find a case with at least 3 headnotes (HN column)
     * 4. Click on the blue hyper link (CCDB) for that case
     * 5. VERIFY: The Classification/Headnote page is opened
     * 6. VERIFY: There are "HN", "RL", "Headnote Text" columns in classification area
     * 7. Click on any headnote number hyper link in classification area (HN column)
     * 8. VERIFY: "Headnote Detail" pop up appears.
     * 9. VERIFY: The pop up title is "Headnote Detail|{ccdb}|{case title}"
     * 10. VERIFY: "Headnote", "Topic & Key Number", "Content Set", "Classification" and "Full Text" are present with respective data filled.
     * 11. VERIFY: Navigation buttons in the bottom of pop up window are present: "<< <<", "<<", headnotes numbers ("1", "2", "3", etc), ">>", ">> >>"
     */

    @Test
    @EDGE
    @CARSWELL
    @LOG
    public void headnoteDetailsLayoutCanadaTest()
    {
        String ccdbNumber = "1881021";
        String caseTitle = "Brampton (City) v. Khela";
        int numberOfHeadnotesForCase = 10;
        //1. Open Subscribed cases page for Canada Ontario (Development)
        subscribedCasesPageAngular().openNodSubscribedCasesPage(ContentSets.CANADA_ONTARIO_DEVELOPMENT);
        loginPage().logIn();
        //2. VERIFY: Subscribed Cases page is opened
        boolean subscribedCasesPageIsOpened = subscribedCasesPageAngular().isPageOpened();
        Assertions.assertTrue(subscribedCasesPageIsOpened, "NOD Subscribed Cases page didn't open");
        //3. Find a case with at least 3 headnotes (HN column)
        //4. Click on the blue hyper link (CCDB) for that case
        subscribedCasesTablePage().openHeadnotesPageByCCDB(ccdbNumber);
        //5. VERIFY: The Classification/Headnote page is opened
        boolean headnotesPageIsOpened = headnotesPageAngular().isPageOpened();
        Assertions.assertTrue(headnotesPageIsOpened, "Headnotes page didn't open");
        //6. VERIFY: There are "HN", "RL", "Headnote Text" columns in classification area
        boolean classificationColumnsHeadersAreShown = headnotesClassificationFragmentAngular().isClassificationAreaShown();
        int numberOfHeadnotesOnHeadnotesPage = headnotesClassificationFragmentAngular().getNumberOfHeadnotesInTable();
        boolean isNumberOfHednotesOnHeadnotesPageSameAsOnSubscribedCasesPage = numberOfHeadnotesOnHeadnotesPage == numberOfHeadnotesForCase;
        boolean allColumnsExpectedToHaveTextHaveIt = headnotesClassificationFragmentAngular().doAllRowsHaveTextInColumnsHnRlHeadnotesText();
        //7. Click on any headnote number hyper link in classification area (HN column)
        headnotesClassificationFragmentAngular().clickHeadnoteLinkByRowNumber(1);
        //8. VERIFY: "Headnote Detail" pop up appears.
        boolean headnoteDetailsPopupIsOpened = headnoteDetailsPopupAngular().isPageOpened();
        //9. VERIFY: The pop up title is "Headnote Detail|{ccdb}|{case title}"
        String expectedHeader = "Headnote Detail | " + ccdbNumber + " | " + caseTitle;
        String actualHeader = headnotesDetailsPage().getHeader();
        boolean headerIsCorrect = actualHeader.equals(expectedHeader);
        //10. VERIFY: "Headnote", "Topic & Key Number", "Content Set", "Classification" and "Full Text" are present with respective data filled.
        boolean headnoteNumberIsShown = headnoteDetailsPopupAngular().isHeadnotesNumberShownAndExpected(numberOfHeadnotesOnHeadnotesPage);
        boolean digestsAreShown = headnoteDetailsPopupAngular().isDigestsFieldShown();
        boolean contentSetIsShown = headnoteDetailsPopupAngular().isContentSetShownAndExpected(ContentSets.CANADA_ONTARIO_DEVELOPMENT);
        boolean classificationIsShown = headnoteDetailsPopupAngular().isElementDisplayed(HeadnoteDetailsPopupElementsAngular.CLASSIFICATION);
        boolean fullTextIsShown = headnoteDetailsPopupAngular().isFullTextShown();
        //11. VERIFY: Navigation buttons in the bottom of pop up window are present: "<< <<", "<<", headnotes numbers ("1", "2", "3", etc), ">>", ">> >>"
        boolean navigationArrowsButtonsAreShown = headnoteDetailsPopupAngular().areNavigationArrowButtonsShown();
        List<String> expectedPageNumbers = headnoteDetailsPopupAngular().generateExpectedPageNumbersUsingTotalNumberOfHeadnotes(numberOfHeadnotesOnHeadnotesPage);
        List<String> pageNumbers = headnoteDetailsPopupAngular().getNavigationPageButtons();
        boolean pageNumbersAreShownCorrectly = pageNumbers.equals(expectedPageNumbers);

        Assertions.assertAll
                (
                        () -> Assertions.assertTrue(classificationColumnsHeadersAreShown, "Column headers doesn't exist in classification area"),
                        () -> Assertions.assertTrue(isNumberOfHednotesOnHeadnotesPageSameAsOnSubscribedCasesPage, String.format(
                                "Number of headnotes on headnotes page is differnet from number of headnotes shown on Subscribed cases page. Expected: %s, \nActual: %s",
                                numberOfHeadnotesForCase, numberOfHeadnotesOnHeadnotesPage)),
                        () -> Assertions.assertTrue(allColumnsExpectedToHaveTextHaveIt, "Not all columns contain text for headnotes"),
                        () -> Assertions.assertTrue(headerIsCorrect, String.format("Headnote Details header was not expected. Expected: %s, Actual %s",
                                expectedHeader, actualHeader)),
                        () -> Assertions.assertTrue(headnoteDetailsPopupIsOpened, "Headnote details popup doen't seem to open"),
                        () -> Assertions.assertTrue(headnoteNumberIsShown, "Headnote field is not shown in Headnote Details popup"),
                        () -> Assertions.assertTrue(digestsAreShown, "Digests field is not shown in Headnote Details popup"),
                        () -> Assertions.assertTrue(contentSetIsShown, "Content set field is not shown in Headnote Details popup"),
                        () -> Assertions.assertTrue(classificationIsShown, "Classification field is not shown in Headnote Details popup"),
                        () -> Assertions.assertTrue(fullTextIsShown, "Full text field is not shown in Headnote Details popup"),
                        () -> Assertions.assertTrue(navigationArrowsButtonsAreShown, "Navigation arrows are not shown in the popup"),
                        () -> Assertions.assertTrue(pageNumbersAreShownCorrectly, String.format("Page numbers are not shown correctly. Expected: %s, \nActual: %s",
                                expectedPageNumbers, pageNumbers))
                );
    }

    /**
     * HALCYONST-10777
     * 1. Open Subscribed cases page for Canada Ontario (Development)
     * 2. VERIFY: Subscribed Cases page is opened
     * 3. Find given ccdb
     * 4. Click on the blue hyper link for that case
     * 5. VERIFY: The Classification/Headnote page is opened
     * 6. Click on the first headnote number hyper link in classification area (HN column)
     * 7. VERIFY: "Headnote Detail" pop up appears.
     * 8. Click each of the navigation buttons and VERIFY: Their functions are performed correctly
     * 9. VERIFY: "Save" button is disabled
     * 10. Change "Full text" field text to some testing value.
     * 11. VERIFY: "Save" button is enabled now
     * 12. Save changes
     * 13. VERIFY: Pop up closed and "Headnote updated!" message appeared in the left bottom side of the page.
     * 14. VERIFY: Headnote text has changed in classification pane
     */
    @ParameterizedTest(name = "Test: {displayName}; Index: {index}; Arguments: {arguments}")
    @CsvSource
            ({
                    "4",
                    "1"
            })
    @EDGE
    @CARSWELL
    @LOG
    public void headnoteDetailsNavigateAndSaveCanadaTest(String headnoteNumber)
    {
        String contentSet = ContentSets.CANADA_ONTARIO_DEVELOPMENT.getCode();
        String caseTitle = "Test" + DateAndTimeUtils.getCurrentDateAndTimeyyyyMMddHHmm();
        String caseSerial = "1234-987655";
        String headnoteText = "Test text";
        String headnoteTextUpdated = "Updated text";
        int numberOfHeadnotes = 5;
        int headnoteNumberInt = Integer.parseInt(headnoteNumber);
        String expectedHeadnoteUpdatedAlertText = "Headnote updated!";
        String ccdbNumber = "9876543";

        //Mock up case with headnotes
        connection = BaseDatabaseUtils.connectToDatabaseUAT();
        NodAngularDatabaseUtils.insertCase(caseTitle, caseSerial, courtUuid, connection);
        String caseUuid = NodAngularDatabaseUtils.getCaseUuid(caseSerial, connection);
        NodAngularDatabaseUtils.setCcdbNumber(caseUuid, ccdbNumber, connection);
        NodAngularDatabaseUtils.subscribeToCase(caseUuid, contentSet, connection);
        String subscribedCaseUuid = NodAngularDatabaseUtils.getSubscribedCaseUuid(caseUuid, contentSet, connection);
        NodAngularDatabaseUtils.insertMultipleHeadnotesAndSubscribe(numberOfHeadnotes, caseUuid, subscribedCaseUuid, headnoteText, connection);

        try
        {
            //1. Open Subscribed cases page for Canada Ontario (Development) and open headnote page by given ccdb number
            subscribedCasesPageAngular().openNodSubscribedCasesPage(ContentSets.CANADA_ONTARIO_DEVELOPMENT);
            loginPage().logIn();
            Assertions.assertTrue(subscribedCasesPageAngular().isPageOpened(), "NOD Subscribed Cases page didn't open");
            subscribedCasesTablePage().openHeadnotesPageByCCDB(ccdbNumber);
            Assertions.assertTrue(headnotesPageAngular().isPageOpened(), "Headnotes page didn't open");

            //Click on given headnote number link
            headnotesClassificationFragmentAngular().clickHeadnoteLinkByRowNumber(headnoteNumberInt);
            Assertions.assertTrue(headnoteDetailsPopupAngular().isPageOpened(), "Headnote details popup doen't seem to open");

            //8. Click each of the navigation buttons and VERIFY: Their functions are performed correctly
            //click "next page" and verify next blueline is shown
            headnoteDetailsPopupAngular().clickGoToNextHeadnote();
            String nextPageHeadnoteNumber = headnoteDetailsPopupAngular().getHeadnoteNumber();
            String expectedNextHeadnoteNumber = String.format("%s of %s", headnoteNumberInt + 1, numberOfHeadnotes);
            boolean isNextHeadnoteNumberCorrect = nextPageHeadnoteNumber.equals(expectedNextHeadnoteNumber);

            //click "last page" and verify the last blueline is shown
            headnoteDetailsPopupAngular().clickGoToLastHeadnote();
            String lastPageHeadnoteNumber = headnoteDetailsPopupAngular().getHeadnoteNumber();
            String expectedLastHeadnoteNumber = String.format("%s of %s", numberOfHeadnotes, numberOfHeadnotes);
            boolean isLastHeadnoteNumberCorrect = lastPageHeadnoteNumber.equals(expectedLastHeadnoteNumber);

            //click "first page" and verify the first blueline is shown
            headnoteDetailsPopupAngular().clickGoToFirstHeadnote();
            String firstPageHeadnoteNumber = headnoteDetailsPopupAngular().getHeadnoteNumber();
            String expectedFirstHeadnoteNumber = String.format("1 of %s", numberOfHeadnotes);
            boolean isFirstHeadnoteNumberCorrect = firstPageHeadnoteNumber.equals(expectedFirstHeadnoteNumber);

            //click a page by number and verify blueline is shown
            headnoteDetailsPopupAngular().clickGoToHeadnoteByNumber(4);
            String headnoteNumberByPage = headnoteDetailsPopupAngular().getHeadnoteNumber();
            String expectedHeadnoteByPage = String.format("4 of %s", numberOfHeadnotes);
            boolean isSelectedHeadnoteNumberCorrect = headnoteNumberByPage.equals(expectedHeadnoteByPage);

            //Verify save button is disabled by default, then update the text in the pane, verify save button is now enabled
            boolean isSaveButtonDisabled = !headnoteDetailsPopupAngular().isSaveButtonEnabled();
            headnoteDetailsPopupAngular().sendTextToHeadnoteTextArea(headnoteTextUpdated);
            boolean isSaveButtonEnabled = headnoteDetailsPopupAngular().isSaveButtonEnabled();

            // Save and verify changes are saved, handle expected alerts "Headnote updated!" on bottom left
            headnoteDetailsPopupAngular().clickSave();
            boolean isNotificationShown = notificationPopupAngular().waitForNotification();
            String notificationText = notificationPopupAngular().getNotificationText();
            boolean isHeadnoteUpdatedAlertCorrect = expectedHeadnoteUpdatedAlertText.equals(notificationText);
            boolean didHeadnoteTextUpdateAfterSaving = headnotesClassificationFragmentAngular().getHeadnoteTextByRowNumber(4).equals(headnoteTextUpdated);


            Assertions.assertAll
              (
                  () -> Assertions.assertTrue(isNextHeadnoteNumberCorrect, "Expected headnote didn't open when next headnote button is clicked"),
                  () -> Assertions.assertTrue(isLastHeadnoteNumberCorrect, "Expected headnote didn't open when the last headnote button is clicked"),
                  () -> Assertions.assertTrue(isFirstHeadnoteNumberCorrect, "Expected headnote didn't open when the first headnote button is clicked"),
                  () -> Assertions.assertTrue(isSelectedHeadnoteNumberCorrect, "Expected headnote didn't open when page number button is clicked"),
                  () -> Assertions.assertTrue(isSaveButtonDisabled, "Save button is not disabled by default"),
                  () -> Assertions.assertTrue(isSaveButtonEnabled, "Save button is not enabled after text edit"),
                  () -> Assertions.assertTrue(isNotificationShown, "Notification didn't show"),
                  () -> Assertions.assertTrue(isHeadnoteUpdatedAlertCorrect, String.format("Notification text is not expected. Expected: %s \n Actual: %s", expectedHeadnoteUpdatedAlertText, notificationText)),
                  () -> Assertions.assertTrue(didHeadnoteTextUpdateAfterSaving, "Headnote text was not updated when it should have been")
              );
        }
        finally
        {
            NodAngularDatabaseUtils.deleteCase(caseUuid, contentSet, connection);
        }
    }

    /**
     * HALCYONST-9408
     * 1. Open Subscribed cases page for Iowa (Development)
     * 2. VERIFY: Subscribed Cases page is opened
     * 3. Click on the blue hyper link of any of the cases
     * 4. VERIFY: Headnotes page is opened
     * 5. VERIFY: The Search box, the Quick Find button, the Keyword Find button, and the Template Find button are present
     * 6. In Search box type "372.13 196"
     * 7. Click the Quick Find button
     * 8. VERIFY: Section 372.13, Blueline 196 is selected
     * 9. Remove search box contents and type "R 1.101"
     * 10. Click the Quick Find button
     * 11. VERIFY: Court rule 1.101 is selected now
     */
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void quickFindTest()
    {
        String caseSerial = "2045-085248";
        String firstSearchString = "372.13 196";
        String firstNodeText = "BL 196 Policemen, compensation";
        String secondSearchString = "R. 1.101";
        String secondNodeText = "R. 1.101 Applicability; statutes affected";

        //1. Open Subscribed cases page for IOWA (Development) and open case by serial number
        subscribedCasesPageAngular().openNodSubscribedCasesPage(ContentSets.IOWA_DEVELOPMENT);
        loginPage().logIn();
        Assertions.assertTrue(subscribedCasesPageAngular().isPageOpened(), "NOD Subscribed Cases page didn't open");
        casesTablePage().openHeadnotesPageByCaseSerial(caseSerial);
        Assertions.assertTrue(headnotesPageAngular().isPageOpened(), "Headnotes page didn't open");

        //5. VERIFY: The Search box, the Quick Find button, the Keyword Find button, and the Template Find button are present
        boolean isQuickFindButtonPresent = hierarchyTreeFragmentAngular().isQuickFindButtonPresent();
        boolean isKeywordFindButtonPresent = hierarchyTreeFragmentAngular().isKeywordFindButtonPresent();
        boolean isTemplateFindButtonPresent = hierarchyTreeFragmentAngular().isTemplateFindButtonPresent();
        //6. In Search box type "372.13 196"
        //7. Click the Quick Find button
        hierarchyTreeFragmentAngular().quickFind(firstSearchString);
        hierarchyTreeFragmentAngular().waitForElementScrolledTo(firstNodeText);
        //8. VERIFY: Section 372.13, Blueline 196 is selected
        boolean isFirstNodeSelected = hierarchyTreeFragmentAngular().isNodeSelected(firstNodeText);
        //9. Remove search box contents and type "R 1.101"
        //10. Click the Quick Find button
        hierarchyTreeFragmentAngular().clearQuickFindInput();
        hierarchyTreeFragmentAngular().quickFind(secondSearchString);
        hierarchyTreeFragmentAngular().waitForElementScrolledTo(secondNodeText);
        //11. VERIFY: Court rule 1.101 is selected now
        boolean isSecondNodeSelected = hierarchyTreeFragmentAngular().isNodeSelected(secondNodeText);

        Assertions.assertAll
          (
              () -> Assertions.assertTrue(isQuickFindButtonPresent, "Quick find button is not present on the page"),
              () -> Assertions.assertTrue(isKeywordFindButtonPresent, "Keyword find button is not present on the page"),
              () -> Assertions.assertTrue(isTemplateFindButtonPresent, "Template find button is not present on the page"),
              () -> Assertions.assertTrue(isFirstNodeSelected, String.format("Node %s is not selected after quick find", firstNodeText)),
              () -> Assertions.assertTrue(isSecondNodeSelected, String.format("Node %s is not selected after quick find", secondNodeText))
          );
    }

    /**
     * HALCYONST-9408
     * 1. Open Subscribed cases page for Iowa (Development)
     * 2. VERIFY: Subscribed Cases page is opened
     * 3. Click on the blue hyper link of any of the cases
     * 4. VERIFY: Headnotes page is opened
     * 5. VERIFY: The Search box, the Quick Find button, the Keyword Find button, and the Template Find button are present
     * 6. Click the Keyword Find button
     * 7. VERIFY: The Keyword Find pop-up is shown.
     * 8. VERIFY: In the first dropdown "=" is defaulted, in the second dropdown "blue line" is defaulted.
     * 9. Fill first input field with "372.13" and the second with "196"
     * 10. Click the Search button
     * 11. VERIFY: Section 372.13, Blueline 196 is selected
     */
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void keywordFindTest()
    {
        String caseSerial = "2045-085248";
        String expectedFirstDropdownValue = "=";
        String expectedSecondDropdownValue = "BLUE LINE";
        String firstSearchString = "372.13";
        String secondSearchString = "196";
        String nodeText = "BL 196 Policemen, compensation";
        //1. Open Subscribed cases page for IOWA (Development)
        subscribedCasesPageAngular().openNodSubscribedCasesPage(ContentSets.IOWA_DEVELOPMENT);
        loginPage().logIn();
        //2. VERIFY: Subscribed Cases page is opened
        boolean subscribedCasesPageIsOpened = subscribedCasesPageAngular().isPageOpened();
        Assertions.assertTrue(subscribedCasesPageIsOpened, "NOD Subscribed Cases page didn't open");
        //3. Click on the blue hyper link of any of the cases
        subscribedCasesTablePage().openHeadnotesPageByCaseSerial(caseSerial);
        //4. VERIFY: The Classification/Headnote page is opened
        boolean headnotesPageIsOpened = headnotesPageAngular().isPageOpened();
        Assertions.assertTrue(headnotesPageIsOpened, "Headnotes page didn't open");
        //5. VERIFY: The Search box, the Quick Find button, the Keyword Find button, and the Template Find button are present
        boolean isQuickFindButtonPresent = hierarchyTreeFragmentAngular().isQuickFindButtonPresent();
        boolean isKeywordFindButtonPresent = hierarchyTreeFragmentAngular().isKeywordFindButtonPresent();
        boolean isTemplateFindButtonPresent = hierarchyTreeFragmentAngular().isTemplateFindButtonPresent();
        //6. Click the Keyword Find button
        hierarchyTreeFragmentAngular().clickKeywordFind();
        //7. VERIFY: The Keyword Find pop-up is shown. In the first dropdown "=" is defaulted, in the second dropdown "blue line" is defaulted.
        boolean isKeywordFindPopupOpened = keywordFoundPopupAngular().isPageOpened();
        Assertions.assertTrue(isKeywordFindPopupOpened, "Keyword Find popup didn't open");
        //8. VERIFY: In the first dropdown "=" is defaulted, in the second dropdown "blue line" is defaulted.
        String actualFirstDropdownValue = keywordFoundPopupAngular().getFirstSelectOptionText();
        boolean firstDropdownValueIsExpected = expectedFirstDropdownValue.equals(actualFirstDropdownValue);
        String actualSecondDropdownValue = keywordFoundPopupAngular().getSecondSelectOptionText();
        boolean secondDropdownValueIsExpected = expectedSecondDropdownValue.equals(actualSecondDropdownValue);
        //9. Fill first input field with "372.13" and the second with "196"
        keywordFoundPopupAngular().typeToFirstInput(firstSearchString);
        keywordFoundPopupAngular().typeToSecondInput(secondSearchString);
        //10. Click the Search button
        keywordFoundPopupAngular().clickSearchButton();
        //11. VERIFY: Section 372.13, Blueline 196 is selected
        boolean isFirstNodeSelected = hierarchyTreeFragmentAngular().isNodeSelected(nodeText);

        Assertions.assertAll
                      (
                          () -> Assertions.assertTrue(isQuickFindButtonPresent, "Quick find button is not present on the page"),
                          () -> Assertions.assertTrue(isKeywordFindButtonPresent, "Keyword find button is not present on the page"),
                          () -> Assertions.assertTrue(isTemplateFindButtonPresent, "Template find button is not present on the page"),
                          () -> Assertions.assertTrue(firstDropdownValueIsExpected, String.format("First keyword find dropdown value is not expected. \n" +
                                                                                                      "Expected: %s, \nActual: %s", expectedFirstDropdownValue, actualFirstDropdownValue)),
                          () -> Assertions.assertTrue(secondDropdownValueIsExpected, String.format("Second keyword find dropdown value is not expected. \n" +
                                                                                                       "Expected: %s, \nActual: %s", expectedSecondDropdownValue, actualSecondDropdownValue)),
                          () -> Assertions.assertTrue(isFirstNodeSelected, String.format("Node %s is not selected after quick find", nodeText))
                      );
    }


    /**
     * HALCYONST-14499
     */
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void backgroundColorTest()
    {
        String caseSerial = "2045-085248";
        String expectedColor = "rgba(243, 242, 240, 1)";
        //1. Open Subscribed cases page for IOWA (Development)
        subscribedCasesPageAngular().openNodSubscribedCasesPage(ContentSets.IOWA_DEVELOPMENT);
        loginPage().logIn();
        //2. VERIFY: Subscribed Cases page is opened
        boolean subscribedCasesPageIsOpened = subscribedCasesPageAngular().isPageOpened();
        Assertions.assertTrue(subscribedCasesPageIsOpened, "NOD Subscribed Cases page didn't open");
        //3. Click on the blue hyper link of any of the cases
        subscribedCasesPageAngular().inputTextInSearchByCaseSerialField(caseSerial);
        subscribedCasesTablePage().clickOnHyperLinkOnFirstRow();
        //4. VERIFY: The Classification/Headnote page is opened
        Assertions.assertTrue(headnotesPageAngular().isPageOpened(), "Headnotes page didn't open");
        String actualColor = headnotesPageAngular().getBackGroundColor();
        Assertions.assertEquals(expectedColor, actualColor);
    }

    /**
     * HALCYONST-14130
     */
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void selectedHeadnoteTextTest()
    {
        String caseSerial = "2045-085248";
        int rowNumber =1;

        subscribedCasesPageAngular().openNodSubscribedCasesPage(ContentSets.IOWA_DEVELOPMENT);
        loginPage().logIn();
        Assertions.assertTrue(subscribedCasesPageAngular().isPageOpened(), "NOD Subscribed Cases page didn't open");

        subscribedCasesPageAngular().inputTextInSearchByCaseSerialField(caseSerial);
        subscribedCasesTablePage().clickOnHyperLinkOnFirstRow();
        Assertions.assertTrue(headnotesPageAngular().isPageOpened(), "Headnotes page didn't open");

        String headnoteTextToCheck = headnotesClassificationFragmentAngular().getHeadnoteTextByRowNumber(rowNumber);

        headnotesClassificationFragmentAngular().clickHeadnoteLinkByRowNumber(rowNumber);
        Assertions.assertTrue(headnoteDetailsPopupAngular().isPageOpened());
        String headnoteText = headnoteDetailsPopupAngular().getTextFromHeadnoteTextArea();
        headnoteDetailsPopupAngular().clickCancel();

        Assertions.assertEquals(headnoteTextToCheck, headnoteText, String.format("Headnote text does not match.  From blueline classify headnote pages: %s.  From headnote page: %s", headnoteTextToCheck, headnoteText));
    }

    /**
     * HALCYONST-14581
     */
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void topicAndKeyInformationTest()
    {
        String caseSerial = "2045-085248";
        int rowNumber = 1;

        subscribedCasesPageAngular().openNodSubscribedCasesPage(ContentSets.IOWA_DEVELOPMENT);
        loginPage().logIn();
        Assertions.assertTrue(subscribedCasesPageAngular().isPageOpened(), "NOD Subscribed Cases page didn't open");

        subscribedCasesPageAngular().inputTextInSearchByCaseSerialField(caseSerial);
        subscribedCasesTablePage().clickOnHyperLinkOnFirstRow();
        Assertions.assertTrue(headnotesPageAngular().isPageOpened(), "Headnotes page didn't open");

        String headnoteTopicAndKeyNumberTextHeadnotePage = headnotesClassificationFragmentAngular().getHeadnoteTopicAndKeyNumberTextByRowNumber(rowNumber).replace(":", "");

        headnotesClassificationFragmentAngular().clickHeadnoteLinkByRowNumber(rowNumber);
        Assertions.assertTrue(headnoteDetailsPopupAngular().isPageOpened());
        String headnoteTopicAndKeyNumberTextHeadnoteDetail = headnoteDetailsPopupAngular().getHeadnoteTopicAndKeyNumber();
        headnoteDetailsPopupAngular().clickCancel();

        Assertions.assertEquals(headnoteTopicAndKeyNumberTextHeadnotePage, headnoteTopicAndKeyNumberTextHeadnoteDetail);
    }

    /**
     * STORY/BUG - N/A <br>
     * SUMMARY - Tests that the Classify button is disabled if we select a node type other than a BL <br>
     * USER - LEGAL <br>
     */
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void classifyButtonIsDisabledForNonBLNodesTest()
    {
        String caseSerial = "2045-085248";

        String searchString = "123.3";
        String nodeText = "= 123.3 Definitions";

        subscribedCasesPageAngular().openNodSubscribedCasesPage(ContentSets.IOWA_DEVELOPMENT);
        loginPage().logIn();
        boolean subscribedCasesPageIsOpened = subscribedCasesPageAngular().isPageOpened();
        Assertions.assertTrue(subscribedCasesPageIsOpened, "NOD Subscribed Cases page didn't open");

        subscribedCasesTablePage().openHeadnotesPageByCaseSerial(caseSerial);
        boolean headnotesPageIsOpened = headnotesPageAngular().isPageOpened();
        Assertions.assertTrue(headnotesPageIsOpened, "Headnotes page didn't open");

        //at the time of writing this test there were only 2 headnotes for this case, and both weren't classified or ignored
        boolean areAllHeadnotesUnignored = headnotesClassificationFragmentAngular().areAllHeadnotesUnignored(1);
        boolean areAllHeadnotesUnclassified = headnotesClassificationFragmentAngular().areAllHeadnotesUnclassified(1);
        Assertions.assertTrue(areAllHeadnotesUnignored, "Some headnotes are ignored. Test data is corrupted.");
        Assertions.assertTrue(areAllHeadnotesUnclassified, "Some headnotes are classified. Test data is corrupted.");

        hierarchyTreeFragmentAngular().quickFind(searchString);
        hierarchyTreeFragmentAngular().waitForElementScrolledTo(nodeText);

        int numberOfHeadnotes = headnotesClassificationFragmentAngular().getNumberOfHeadnotesInTable();
        for(int i = 1; i <= numberOfHeadnotes; i++)
        {
            logger.information("Checking Classify button is disabled on Headnote: " + i);
            Assertions.assertTrue(headnotesClassificationFragmentAngular().isElementDisabled(String.format(HeadnotesClassificationFragmentElementsAngular.CLASSIFY_BUTTON_BY_ROW,i)), "Headnote: " + i + " classify button disabled");
        }
    }

    /**
     * STORY/BUG - N/A <br>
     * SUMMARY - Tests that the cites in the Headnotes page link appropriately in a given jurisdiction <br>
     * USER - LEGAL <br>
     */
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void headnotesCitesLinkedAppropriatelyTest()
    {
        String caseTitle = "Test" + DateAndTimeUtils.getCurrentDateAndTimeyyyyMMddHHmm();
        String caseSerial = "1234-987677";
        String headnoteText = "HNote " + caseTitle;
        String headnoteLinkEnabled = " <cite.query w-normalized-cite=\"IAR32:1.3\" w-ref-type=\"LQ\">Test Link Enabled</cite.query>";
        String headnoteLinkDisabled = " <cite.query w-normalized-cite=\"USFRBPR9011\" w-ref-type=\"LQ\">Test Link Disabled</cite.query>";
        int headnoteNumber = 1;
        String disabledCiteString = "Test Link Disabled";
        String enabledCiteString = "Test Link Enabled";
        String enabledCiteStringNodeText = "R. 32:1.3 Diligence";

        //Mock case with a headnote that contains an enabled and disabled link
        connection = BaseDatabaseUtils.connectToDatabaseUAT();
        NodAngularDatabaseUtils.insertCase(caseTitle, caseSerial, courtUuid, connection);
        String caseUuid = NodAngularDatabaseUtils.getCaseUuid(caseSerial, connection);
        NodAngularDatabaseUtils.subscribeToCase(caseUuid, contentSet, connection);
        String subscribedCaseUuid = NodAngularDatabaseUtils.getSubscribedCaseUuid(caseUuid, contentSet, connection);
        NodAngularDatabaseUtils.insertHeadnote(caseUuid, headnoteText + headnoteLinkEnabled + headnoteLinkDisabled, connection);
        String headnoteUuid = NodAngularDatabaseUtils.getHeadnoteUuid(caseUuid, headnoteNumber, connection);
        NodAngularDatabaseUtils.subscribeToHeadnote(subscribedCaseUuid, headnoteUuid, connection);

        try
        {
            //login and navigate to subscribed cases page
            subscribedCasesPageAngular().openNodSubscribedCasesPage(ContentSets.IOWA_DEVELOPMENT);
            loginPage().logIn();
            Assertions.assertTrue(subscribedCasesPageAngular().isPageOpened(), "NOD Subscribed Cases page didn't open");

            //open mocked up case headnotes page
            subscribedCasesTablePage().openHeadnotesPageByCaseSerial(caseSerial);
            Assertions.assertTrue(headnotesPageAngular().isPageOpened(), "Headnotes page didn't open");

            //check the enabled cite string is clickable and brings us to the appropriate node
            headnotesClassificationFragmentAngular().click(String.format(HeadnotesClassificationFragmentElementsAngular.HEADNOTE_TEXT_CITE, enabledCiteString));
            hierarchyTreeFragmentAngular().waitForElementScrolledTo(enabledCiteStringNodeText);
            boolean isNodeSelected = hierarchyTreeFragmentAngular().isNodeSelected(enabledCiteStringNodeText);
            Assertions.assertTrue(isNodeSelected, "Node is selected after clicking on clickable cite link in headnote");

            //check the disabled cite string is not clickable and doesn't bring us to the appropriate node.
            // Just checking to make sure the previous selection is still set.
            headnotesClassificationFragmentAngular().click(String.format(HeadnotesClassificationFragmentElementsAngular.HEADNOTE_TEXT_CITE, disabledCiteString));
            hierarchyTreeFragmentAngular().waitForElementScrolledTo(enabledCiteStringNodeText);
            boolean isNodeSelected2 = hierarchyTreeFragmentAngular().isNodeSelected(enabledCiteStringNodeText);
            Assertions.assertTrue(isNodeSelected2, "Node is selected after clicking on clickable cite link in headnote");
        }
        finally
        {
            NodAngularDatabaseUtils.deleteCase(caseUuid, contentSet, connection);
        }
    }
}
