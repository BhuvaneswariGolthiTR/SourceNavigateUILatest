package com.thomsonreuters.codes.codesbench.quality.tests.nodAngular;

import com.thomsonreuters.codes.codesbench.quality.pageelements.nodAngular.popups.InsertBluelinePopupElementsAngular;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.EDGE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class BluelineTests extends TestService
{

    /**
     * HALCYONST-9442
     * HALCYONST-11224
     * 1. Open Subscribed cases page for Iowa (Development)
     * 2. VERIFY: Subscribed Cases page is opened
     * 3. Click on the blue hyper link of any of the cases
     * 4. VERIFY: Headnotes page is opened
     * 5. Right click on a BL ANALYSI node and select Insert Blueline from the context menu
     * 6. VERIFY: Insert Blueline pop up appeared
     * 7. VERIFY: A blue bar will run across the top of the pop up stating "Insert Blueline - Step 1 of 2"
     * 8. VERIFY: Breadcrumbs information is correct
     * 9. Type "1" in Blueline Number field and click Next
     * 10. VERIFY: Error message is shown stating "The Blueline number specified is not valid. A Blueline already exists with this number under the analysis node"
     * 11. Delete the incorrent blueline number
     * 12. Type "333" in Bluline Number field and click Next
     * 13. VERIFY: Warning message is shown stating "Gap Warning: The last Blueline number before this one was: {number} . Press 'Next' button again to ignore this warning."
     * 14. Click NEXT
     * 15. VERIFY: The second page of the pop up is shown
     * 16. Click "Cancel"
     * 17. VERIFY: The pop up is closed and no Bluelines are inserted
     */
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void namingNewBluelineTest()
    {
        String caseSerial = "2045-085248";
        String searchString = "1C.1";
        String bluelineName = "BL 1 Construction and application";
        String blAnalysis = "BL ANALYSI ";
        String expectedBreadcrumbs = "@ IOWA CODE ANNOTATED; T. I; Subt. 1; Ch. 1C; = 1C.1; NOD CONTAI 1C.1; XND; BL ANALYSI";
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
        //5.  Right click on a BL ANALYSI node and select Insert Blueline from the context menu
        hierarchyTreeFragmentAngular().quickFind(searchString);
        hierarchyTreeFragmentAngular().waitForElementScrolledTo(bluelineName);
        hierarchyTreeFragmentAngular().scrollToSeeParentLevelNode(blAnalysis);
        //we'll need this later
        int lastBluelineNumber = hierarchyTreeFragmentAngular().getLastBluelineNumberInBLAnalysisNode();

        hierarchyTreeFragmentAngular().openContextMenuForNodeWithText(blAnalysis);
        headnotesContextMenuAngular().selectInsertBlueline();
        //6. VERIFY: Insert Blueline pop up appeared
        boolean isInsertBluelinePopupOpened = insertBluelinePopupAngular().isPageOpened();
        Assertions.assertTrue(isInsertBluelinePopupOpened, "Insert blueline popup didn't open");
        //7. VERIFY: A blue bar will run across the top of the pop up stating "Insert Blueline - Step 1 of 2"
        String expectedHeaderFirstStep = insertBluelinePopupAngular().getExpectedHeaderForStep(1);
        String actualHeaderFirstStep = insertBluelinePopupAngular().getHeaderText();
        boolean firstStepHeaderIsExpected = expectedHeaderFirstStep.equals(actualHeaderFirstStep);
        //8. VERIFY: Breadcrumbs information is correct
        String actualBreadcrumbs = insertBluelinePopupAngular().getBreadcrumbs();
        boolean breadcrumbsAreExpected = actualBreadcrumbs.equals(expectedBreadcrumbs);
        //9. Type "1" in Blueline Number field and click Next
        insertBluelinePopupAngular().typeToBluelineNumberInput(1);
        insertBluelinePopupAngular().clickNext();
        insertBluelinePopupAngular().waitForElement(String.format(InsertBluelinePopupElementsAngular.ERROR_MESSAGE_BY_TEXT, InsertBluelinePopupElementsAngular.ERROR_BLUELINE_NUMBER_ALREADY_EXISTS));
        //10. VERIFY: Error message is shown stating "The Blueline number specified is not valid.
        // A Blueline already exists with this number under the analysis node"
        boolean isErrorMessageShown = insertBluelinePopupAngular().isErrorMessageShown();
        String actualErrorMessageText = insertBluelinePopupAngular().getAlertMessageText();
        String expectedErrorMessage = insertBluelinePopupAngular().getExpectedErrorMessageBlueilineNumberAlreadyExists();
        boolean isErrorMessageExpected = actualErrorMessageText.equals(expectedErrorMessage);
        //11. Delete the incorrect blueline number
        insertBluelinePopupAngular().clearBluelineNumberInput();
        //12. Type "333" in Blueline Number field and click Next
        insertBluelinePopupAngular().typeToBluelineNumberInput(333);
        insertBluelinePopupAngular().clickNext();

        String errorMessageGap = String.format(InsertBluelinePopupElementsAngular.GAP_WARNING, lastBluelineNumber);
        insertBluelinePopupAngular().doesElementExist(String.format(InsertBluelinePopupElementsAngular.ERROR_MESSAGE_BY_TEXT, errorMessageGap));
        //13. VERIFY: Warning message is shown stating "Gap Warning: The last Blueline number before this one was: {number} .
        // Press 'Next' button again to ignore this warning."
        boolean isWarningMessageShown = insertBluelinePopupAngular().isErrorMessageShown();
        String expectedWarningMessage = insertBluelinePopupAngular().getExpectedGapWarning(lastBluelineNumber);
        String actualWarningMessageText = insertBluelinePopupAngular().getAlertMessageText();
        boolean isWarningMessageExpected = actualWarningMessageText.equals(expectedWarningMessage);
        //14. Click NEXT
        insertBluelinePopupAngular().clickNext();
        //15. VERIFY: The second page of the pop up is shown
        String expectedHeaderSecondStep = insertBluelinePopupAngular().getExpectedHeaderForStep(2);
        String actualHeaderSecondStep = insertBluelinePopupAngular().getHeaderText();
        boolean secondStepHeaderIsExpected = expectedHeaderSecondStep.equals(actualHeaderSecondStep);
        //16. Click "Cancel"
        insertBluelinePopupAngular().clickCancel();
        //17. VERIFY: The pop up is closed and no Bluelines are inserted
        boolean isPageClosed = !insertBluelinePopupAngular().isPageOpened();
        int lastBluelineNumberAfterTest = hierarchyTreeFragmentAngular().getLastBluelineNumberInBLAnalysisNode();
        boolean didLastBluelineNumberChange = lastBluelineNumberAfterTest == lastBluelineNumber;

        Assertions.assertAll
                (
                        () -> Assertions.assertTrue(firstStepHeaderIsExpected, String.format(
                                "Expected header didn't appear. \nExpected: %s, \nActual: %s",
                                expectedHeaderFirstStep, actualHeaderFirstStep)),
                        () -> Assertions.assertTrue(breadcrumbsAreExpected, String.format(
                                "Breadcrunbs info is unexpected. \nExpected: %s, \nActual: %s",
                                expectedBreadcrumbs, actualBreadcrumbs)),
                        () -> Assertions.assertTrue(isErrorMessageShown, "Error message wasn't shown"),
                        () -> Assertions.assertTrue(isErrorMessageExpected, String.format(
                                "Error text is unexpected. \nExpected: %s, \nActual: %s",
                                expectedErrorMessage, actualErrorMessageText)),
                        () -> Assertions.assertTrue(isWarningMessageShown, "Error message wasn't shown"),
                        () -> Assertions.assertTrue(isWarningMessageExpected, String.format(
                                "Error text is unexpected. \nExpected: %s, \nActual: %s",
                                expectedWarningMessage, actualWarningMessageText)),
                        () -> Assertions.assertTrue(secondStepHeaderIsExpected, String.format(
                                "Expected header didn't appear. \nExpected: %s, \nActual: %s",
                                expectedHeaderFirstStep, actualHeaderFirstStep)),
                        () -> Assertions.assertTrue(isPageClosed, "Insert Blueline popup didn't disappear"),
                        () -> Assertions.assertTrue(didLastBluelineNumberChange, String.format(
                                "Last blueline number has changed. \nExpected: %s, \nActual: %s",
                                lastBluelineNumber, lastBluelineNumberAfterTest))
                );
    }

    /**
     * HALCYONST-9442
     * HALCYONST-11224
     * 1. Open Subscribed cases page for Iowa (Development)
     * 2. VERIFY: Subscribed Cases page is opened
     * 3. Click on the blue hyper link of any of the cases
     * 4. VERIFY: Headnotes page is opened
     * 5. Right click on a BL ANALYSI node and select Insert Blueline from the context menu
     * 6. VERIFY: Insert Blueline pop up appeared
     * 7. VERIFY: A blue bar will run across the top of the pop up stating "Insert Blueline - Step 1 of 2"
     * 8. Type next Blueline number in Blueline Number field and click Next
     * 9. VERIFY: The second page of the pop up is shown
     * 10. Select "Flush" type
     * 11. Type "Test" into Text filed
     * 12. VERIFY: The dropdown is shown and "Test" option is there.
     * 13. Click on "Test" option.
     * 14. VERIFY: The Flush Analysis (NAL) is now populated with the same value as Text field
     * 15. Type "test" into "Flush Analysis (NAL) - Index Also" field and press enter
     * 16. VERIFY: "test" tag is added to the field.
     * 17. Click "Insert"
     * 18. VERIFY: new Blueline is inserted with correct number and text.
     */
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void insertNewFlushBlueline()
    {
        String caseSerial = "2045-261610";
        String searchString = "1D.1 1";
        String bluelineName = "BL 1 Validity";
        String blAnalysis = "BL ANALYSI ";
        String textToType = "Test";
        //1. Open Subscribed cases page for IOWA (Development)
        subscribedCasesPageAngular().openNodSubscribedCasesPage(ContentSets.IOWA_DEVELOPMENT);
        // clean storage
        loginPage().logIn();
        //2. VERIFY: Subscribed Cases page is opened
        boolean subscribedCasesPageIsOpened = subscribedCasesPageAngular().isPageOpened();
        Assertions.assertTrue(subscribedCasesPageIsOpened, "NOD Subscribed Cases page didn't open");
        //3. Click on the blue hyper link of any of the cases
        subscribedCasesTablePage().openHeadnotesPageByCaseSerial(caseSerial);
        //4. VERIFY: The Classification/Headnote page is opened
        boolean headnotesPageIsOpened = headnotesPageAngular().isPageOpened();
        Assertions.assertTrue(headnotesPageIsOpened, "Headnotes page didn't open");
        //5.  Right click on a BL ANALYSI node and select Insert Blueline from the context menu
        hierarchyTreeFragmentAngular().quickFind(searchString);
        hierarchyTreeFragmentAngular().waitForElementScrolledTo(bluelineName);
        hierarchyTreeFragmentAngular().scrollToSeeParentLevelNode(blAnalysis);
        //we'll need this later
        int lastBluelineNumber = hierarchyTreeFragmentAngular().getLastBluelineNumberInBLAnalysisNode();
        int newBluelineNumber = lastBluelineNumber + 1;
        hierarchyTreeFragmentAngular().openContextMenuForNodeWithText(blAnalysis);
        headnotesContextMenuAngular().selectInsertBlueline();
        //6. VERIFY: Insert Blueline pop up appeared
        boolean isInsertBluelinePopupOpened = insertBluelinePopupAngular().isPageOpened();
        Assertions.assertTrue(isInsertBluelinePopupOpened, "Insert blueline popup didn't open");
        //7. VERIFY: A blue bar will run across the top of the pop up stating "Insert Blueline - Step 1 of 2"
        String expectedHeaderFirstStep = insertBluelinePopupAngular().getExpectedHeaderForStep(1);
        String actualHeaderFirstStep = insertBluelinePopupAngular().getHeaderText();
        boolean firstStepHeaderIsExpected = expectedHeaderFirstStep.equals(actualHeaderFirstStep);
        //8. Type next Blueline number in Blueline Number field and click Next
        insertBluelinePopupAngular().typeToBluelineNumberInput(newBluelineNumber);
        insertBluelinePopupAngular().clickNext();
        //9. VERIFY: The second page of the pop up is shown
        String expectedHeaderSecondStep = insertBluelinePopupAngular().getExpectedHeaderForStep(2);
        boolean secondStepHeaderIsExpected = insertBluelinePopupAngular().waitHeaderWithText(expectedHeaderSecondStep);
        //10. Select "Flush" type
        insertBluelinePopupAngular().selectFlushType();
        //11. Type "Test" into Text field
        insertBluelinePopupAngular().typeToTextField(textToType);
        //12. VERIFY: The dropdown is shown and "Test" option is there.
        boolean isDropdownShown = insertBluelinePopupAngular().textFieldDropdownIsShown();
        //13. Click on "Test" option.
        insertBluelinePopupAngular().clickTextInputDropdownOptionByText(textToType);
        //14. VERIFY: The Flush Analysis (NAL) is now populated with the same value as Text field
        String flushAnalysisText = insertBluelinePopupAngular().getFlushAnalysisFieldText();
        boolean flushAnalysisTextIsExpected = flushAnalysisText.equals(textToType);
        //15. Type "test" into "Flush Analysis (NAL) - Index Also" field and press enter
        insertBluelinePopupAngular().typeTagsToFlushAnalysisIndexAlsoField(textToType);
        //16. VERIFY: "test" tag is added to the field.
        boolean isTestTagPresent = insertBluelinePopupAngular().isTagPresentInFlushAnalysisIndexAlso(textToType);
        //17. Click "Insert"
        insertBluelinePopupAngular().clickInsert();
        //18. VERIFY: new Blueline is inserted with correct number and text
        headnotesPageAngular().refreshPage();
        // remove this page update if driver version is changed or Chrome driver is used

        hierarchyTreeFragmentAngular().quickFind(searchString);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.THREE_SECONDS);
        hierarchyTreeFragmentAngular().scrollToSeeParentLevelNode(blAnalysis);
        List<String> bluelines = hierarchyTreeFragmentAngular().getAllBluelinesNamesInBluelineAnalysisNode(blAnalysis);
        String expectedBluelineName = String.format("BL %s %s", newBluelineNumber, textToType);
        boolean isNewBluelinePresentInTree = bluelines.contains(expectedBluelineName);
        //data clean up:
        hierarchyTreeFragmentAngular().openContextMenuForNodeWithText(expectedBluelineName);
        headnotesContextMenuAngular().selectDeleteBlueline();
        insertBluelinePopupAngular().clickDelete();
        insertBluelinePopupAngular().waitPopupClosed();

        Assertions.assertAll
                (
                        () -> Assertions.assertTrue(firstStepHeaderIsExpected, String.format(
                                "Expected header didn't appear. \nExpected: '%s', \nActual: '%s'",
                                expectedHeaderFirstStep, actualHeaderFirstStep)),
                        () -> Assertions.assertTrue(secondStepHeaderIsExpected, String.format(
                                "Expected header didn't appear. \nExpected: '%s'",
                                expectedHeaderSecondStep)),
                        () -> Assertions.assertTrue(isDropdownShown,
                                "Expected text input field dropdown didn't appear."),
                        () -> Assertions.assertTrue(flushAnalysisTextIsExpected, String.format(
                                "Flush Analysis text is not expected. \nExpected: '%s', \nActual: '%s'",
                                textToType, flushAnalysisText)),
                        () -> Assertions.assertTrue(isTestTagPresent, String.format(
                                "Expected tag '%s' doesn't exist in Flush Analysis - Index Also",
                                textToType)),
                        () -> Assertions.assertTrue(isNewBluelinePresentInTree, String.format(
                                "New blueline is not found in the tree \nExpected blueline name: '%s'",
                                expectedBluelineName))
                );
    }

    /**
     * HALCYONST-9442
     * HALCYONST-11224
     * 1. Open Subscribed cases page for Iowa (Development)
     * 2. VERIFY: Subscribed Cases page is opened
     * 3. Click on the blue hyper link of any of the cases
     * 4. VERIFY: Headnotes page is opened
     * 5. Right click on a BL ANALYSI node and select Insert Blueline from the context menu
     * 6. VERIFY: Insert Blueline pop up appeared
     * 7. VERIFY: A blue bar will run across the top of the pop up stating "Insert Blueline - Step 1 of 2"
     * 8. Type next Blueline number in Blueline Number field and click Next
     * 9. VERIFY: The second page of the pop up is shown
     * 10. Select "Start of indent" type
     * 11. Type "Test" into Text field
     * 12. VERIFY: The dropdown is shown and "Test" option is there.
     * 13. Click on "Test" option.
     * 14. VERIFY: The Flush Analysis (NAL) is now populated with the same value as Text field
     * 15. Type "Notice" into second text field
     * 16. VERIFY: The dropdown is shown and "Notice" option is there.
     * 17. Click on "Notice" option.
     * 18. VERIFY: The Indent Analysis (NAL2) is now populated with the same value as second text field
     * 19. Click "Insert"
     * 20. VERIFY: new Blueline is inserted with correct number and text.
     */
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void insertNewStartOfIndentBlueline()
    {
        String caseSerial = "2045-261610";
        String searchString = "1D.1 1";
        String bluelineName = "BL 1 Validity";
        String blAnalysis = "BL ANALYSI ";
        String textToType = "Test";
        String secondTextToType = "Notice";
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
        //5.  Right click on a BL ANALYSI node and select Insert Blueline from the context menu
        hierarchyTreeFragmentAngular().quickFind(searchString);
        hierarchyTreeFragmentAngular().waitForElementScrolledTo(bluelineName);
        hierarchyTreeFragmentAngular().scrollToSeeParentLevelNode(blAnalysis);
        //we'll need this later
        int lastBluelineNumber = hierarchyTreeFragmentAngular().getLastBluelineNumberInBLAnalysisNode();
        int newBluelineNumber = lastBluelineNumber + 1;
        hierarchyTreeFragmentAngular().openContextMenuForNodeWithText(blAnalysis);
        headnotesContextMenuAngular().selectInsertBlueline();
        //6. VERIFY: Insert Blueline pop up appeared
        boolean isInsertBluelinePopupOpened = insertBluelinePopupAngular().isPageOpened();
        Assertions.assertTrue(isInsertBluelinePopupOpened, "Insert blueline popup didn't open");
        //7. VERIFY: A blue bar will run across the top of the pop up stating "Insert Blueline - Step 1 of 2"
        String expectedHeaderFirstStep = insertBluelinePopupAngular().getExpectedHeaderForStep(1);
        String actualHeaderFirstStep = insertBluelinePopupAngular().getHeaderText();
        boolean firstStepHeaderIsExpected = expectedHeaderFirstStep.equals(actualHeaderFirstStep);
        //8. Type next Blueline number in Blueline Number field and click Next
        insertBluelinePopupAngular().typeToBluelineNumberInput(newBluelineNumber);
        insertBluelinePopupAngular().clickNext();
        //9. VERIFY: The second page of the pop up is shown
        String expectedHeaderSecondStep = insertBluelinePopupAngular().getExpectedHeaderForStep(2);
        boolean secondStepHeaderIsExpected = insertBluelinePopupAngular().waitHeaderWithText(expectedHeaderSecondStep);
        //10. Select "Flush" type
        insertBluelinePopupAngular().selectStartOfIndentType();
        //11. Type "Test" into Text field
        insertBluelinePopupAngular().typeToTextField(textToType);
        //12. VERIFY: The dropdown is shown and "Test" option is there.
        boolean isDropdownShown = insertBluelinePopupAngular().textFieldDropdownIsShown();
        //13. Click on "Test" option.
        insertBluelinePopupAngular().clickTextInputDropdownOptionByText(textToType);
        //14. VERIFY: The Flush Analysis (NAL) is now populated with the same value as Text field
        String flushAnalysisText = insertBluelinePopupAngular().getFlushAnalysisFieldText();
        boolean flushAnalysisTextIsExpected = flushAnalysisText.equals(textToType);
        //15. Type "Notice" into second text field
        insertBluelinePopupAngular().typeToSecondTextField(secondTextToType);
        //16. VERIFY: The dropdown is shown and "Notice" option is there.
        boolean isSecondDropdownShown = insertBluelinePopupAngular().textFieldDropdownIsShown();
        //17. Click on "Notice" option.
        insertBluelinePopupAngular().clickTextInputDropdownOptionByText(secondTextToType);
        //18. VERIFY: The Indent Analysis (NAL2) is now populated with the same value as second text field
        String indentAnalysisText = insertBluelinePopupAngular().getIndentAnalysisFieldText();
        boolean indentAnalysisTextIsExpected = flushAnalysisText.equals(textToType);
        //19. Click "Insert"
        insertBluelinePopupAngular().clickInsert();
        //20. VERIFY: new Blueline is inserted with correct number and text
        headnotesPageAngular().refreshPage();
        // remove this page update if driver version is changed or Chrome driver is used

        hierarchyTreeFragmentAngular().quickFind(searchString);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.ONE_SECOND);
        hierarchyTreeFragmentAngular().scrollToSeeParentLevelNode(blAnalysis);
        List<String> bluelines = hierarchyTreeFragmentAngular().getAllBluelinesNamesInBluelineAnalysisNode(blAnalysis);
        String expectedBluelineName = String.format("BL %s %s-%s", newBluelineNumber, textToType, secondTextToType);
        boolean isNewBluelinePresentInTree = bluelines.contains(expectedBluelineName);
        //data clean up:
        hierarchyTreeFragmentAngular().openContextMenuForNodeWithText(expectedBluelineName);
        headnotesContextMenuAngular().selectDeleteBlueline();
        insertBluelinePopupAngular().clickDelete();
        insertBluelinePopupAngular().waitPopupClosed();

        Assertions.assertAll
                (
                        () -> Assertions.assertTrue(firstStepHeaderIsExpected, String.format(
                                "Expected header didn't appear. \nExpected: '%s', \nActual: '%s'",
                                expectedHeaderFirstStep, actualHeaderFirstStep)),
                        () -> Assertions.assertTrue(secondStepHeaderIsExpected, String.format(
                                "Expected header didn't appear. \nExpected: '%s'",
                                expectedHeaderSecondStep)),
                        () -> Assertions.assertTrue(isDropdownShown,
                                "Expected text input field dropdown didn't appear."),
                        () -> Assertions.assertTrue(flushAnalysisTextIsExpected, String.format(
                                "Flush Analysis text is not expected. \nExpected: '%s', \nActual: '%s'",
                                textToType, flushAnalysisText)),
                        () -> Assertions.assertTrue(isSecondDropdownShown,
                                "Expected second text input field dropdown didn't appear."),
                        () -> Assertions.assertTrue(indentAnalysisTextIsExpected, String.format(
                                "Flush Analysis text is not expected. \nExpected: '%s', \nActual: '%s'",
                                secondTextToType, indentAnalysisText)),
                        () -> Assertions.assertTrue(isNewBluelinePresentInTree, String.format(
                                "New blueline is not found in the tree \nExpected blueline name: '%s'",
                                expectedBluelineName))
                );
    }

    /**
     * HALCYONST-9729
     * 1. Open Subscribed cases page for Iowa (Development)
     * 2. VERIFY: Subscribed Cases page is opened
     * 3. Click on the blue hyper link of any of the cases
     * 4. VERIFY: Headnotes page is opened
     * 5. Right click on a BL ANALYSI node and select Insert Blueline from the context menu
     * 6. VERIFY: Insert Blueline pop up appeared
     * 7. VERIFY: A blue bar will run across the top of the pop up stating "Insert Blueline - Step 1 of 2"
     * 8. Insert new Blueline with number "33333" and any content
     * 9. VERIFY: Blueline number "33333" is created under BL ANALYSI node
     * 10. Right click on newly created Blueline and select "Delete Blueline" option from context menu
     * 11. In pop up confirm deletion
     * 12.VERIFY: Blueline is deleted. It is no longer shown on headnotes\classify page.
     * 13. Refresh the page. VERIFY: deleted Blueline is not shown anymore.
     * 14. Right click on BL ANALYSI node and select "Insert Blueline" option from context menu
     * 15. Insert new Blueline with any suitable number and any content
     * 16. VERIFY: A new Blueline is created. It is shown on headnotes\classify page
     * 17. Refresh the page.
     * 18. VERIFY: Newly inserted Blueline with respective number is shown
     */
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void deleteBluelineAndInsertNewBlueline()
    {
        String caseSerial = "2045-261610";
        String searchString = "1D.1 1";
        String bluelineName = "BL 1 Validity";
        String blAnalysis = "BL ANALYSI ";
        String textToType = "Test";
        int bluelineToDeleteNumber = 5555;
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
        //5.  Right click on a BL ANALYSI node and select Insert Blueline from the context menu
        hierarchyTreeFragmentAngular().quickFind(searchString);
        hierarchyTreeFragmentAngular().waitForElementScrolledTo(bluelineName);
        hierarchyTreeFragmentAngular().scrollToSeeParentLevelNode(blAnalysis);
        //we'll need this later
        int lastBluelineNumber = hierarchyTreeFragmentAngular().getLastBluelineNumberInBLAnalysisNode();
        int newBluelineNumber = lastBluelineNumber + 1;
        hierarchyTreeFragmentAngular().openContextMenuForNodeWithText(blAnalysis);
        headnotesContextMenuAngular().selectInsertBlueline();
        //6. VERIFY: Insert Blueline pop up appeared
        boolean isInsertBluelinePopupOpened = insertBluelinePopupAngular().isPageOpened();
        Assertions.assertTrue(isInsertBluelinePopupOpened, "Insert blueline popup didn't open");
        //7. VERIFY: A blue bar will run across the top of the pop up stating "Insert Blueline - Step 1 of 2"
        String expectedHeaderFirstStep = insertBluelinePopupAngular().getExpectedHeaderForStep(1);
        String actualHeaderFirstStep = insertBluelinePopupAngular().getHeaderText();
        boolean firstStepHeaderIsExpected = expectedHeaderFirstStep.equals(actualHeaderFirstStep);
        //8. Insert new Blueline with number "3333333333" and any content
        insertBluelinePopupAngular().typeToBluelineNumberInput(bluelineToDeleteNumber);
        insertBluelinePopupAngular().clickNext();
        insertBluelinePopupAngular().clickNext();
        insertBluelinePopupAngular().waitForSecondStepHeaderToAppear();
        insertBluelinePopupAngular().selectFlushType();
        insertBluelinePopupAngular().typeToTextField(textToType);
        insertBluelinePopupAngular().clickTextInputDropdownOptionByText(textToType);
        insertBluelinePopupAngular().clickInsert();
        //9. VERIFY: Blueline number "33333" is created under BL ANALYSI node
        headnotesPageAngular().refreshPage();
        // remove this page update if driver version is changed or Chrome driver is used
        hierarchyTreeFragmentAngular().quickFind(searchString);
        hierarchyTreeFragmentAngular().scrollToSeeParentLevelNode(blAnalysis);
        List<String> bluelines = hierarchyTreeFragmentAngular().getAllBluelinesNamesInBluelineAnalysisNode(blAnalysis);
        String expectedBluelineName = String.format("BL %s %s", bluelineToDeleteNumber, textToType);
        boolean isNewBluelinePresentInTree = bluelines.contains(expectedBluelineName);
        //10. Right click on newly created Blueline and select "Delete Blueline" option from context menu
        hierarchyTreeFragmentAngular().openContextMenuForNodeWithText(expectedBluelineName);
        headnotesContextMenuAngular().selectDeleteBlueline();
        //11. In pop up confirm deletion
        insertBluelinePopupAngular().clickDelete();
        insertBluelinePopupAngular().waitPopupClosed();
        //12.VERIFY: Blueline is deleted. It is no longer shown on headnotes\classify page.
        bluelines = hierarchyTreeFragmentAngular().getAllBluelinesNamesInBluelineAnalysisNode(blAnalysis);
        boolean isNewBluelineDeletedFromTree = !bluelines.contains(expectedBluelineName);
        //13. Refresh the page. VERIFY: deleted Blueline is not shown anymore.
        headnotesPageAngular().refreshPage();
        //14. Right click on BL ANALYSI node and select "Insert Blueline" option from context menu
        hierarchyTreeFragmentAngular().quickFind(searchString);
        hierarchyTreeFragmentAngular().waitForElementScrolledTo(bluelineName);
        hierarchyTreeFragmentAngular().scrollToSeeParentLevelNode(blAnalysis);
        //15. Insert new Blueline with any suitable number and any content
        hierarchyTreeFragmentAngular().openContextMenuForNodeWithText(blAnalysis);
        headnotesContextMenuAngular().selectInsertBlueline();
        insertBluelinePopupAngular().typeToBluelineNumberInput(newBluelineNumber);
        insertBluelinePopupAngular().clickNext();
        insertBluelinePopupAngular().waitForSecondStepHeaderToAppear();
        insertBluelinePopupAngular().selectFlushType();
        insertBluelinePopupAngular().typeToTextField(textToType);
        insertBluelinePopupAngular().clickTextInputDropdownOptionByText(textToType);
        insertBluelinePopupAngular().clickInsert();
        //16. VERIFY: A new Blueline is created.
        headnotesPageAngular().refreshPage();
        // remove this page update if driver version is changed or Chrome driver is used

        hierarchyTreeFragmentAngular().quickFind(searchString);
        hierarchyTreeFragmentAngular().waitForElementScrolledTo(bluelineName);
        hierarchyTreeFragmentAngular().scrollToSeeParentLevelNode(blAnalysis);
        bluelines = hierarchyTreeFragmentAngular().getAllBluelinesNamesInBluelineAnalysisNode(blAnalysis);
        String expectedSecondBluelineName = String.format("BL %s %s", newBluelineNumber, textToType);
        boolean isSecondBluelinePresentInTree = bluelines.contains(expectedSecondBluelineName);
        boolean isOldBluelineDeletedFromTree = !bluelines.contains(expectedBluelineName);
        //17. Refresh the page.
        headnotesPageAngular().refreshPage();
        //18. VERIFY: Newly inserted Blueline with respective number is shown
        hierarchyTreeFragmentAngular().quickFind(searchString);
        hierarchyTreeFragmentAngular().scrollToSeeParentLevelNode(blAnalysis);
        bluelines = hierarchyTreeFragmentAngular().getAllBluelinesNamesInBluelineAnalysisNode(blAnalysis);
        boolean isSecondBluelinePresentInTreeAfterRefresh = bluelines.contains(expectedSecondBluelineName);
        boolean isOldBluelineStillDeletedFromTreeAfterRefresh = !bluelines.contains(expectedBluelineName);
        //data clean up:
        hierarchyTreeFragmentAngular().openContextMenuForNodeWithText(expectedSecondBluelineName);
        headnotesContextMenuAngular().selectDeleteBlueline();
        insertBluelinePopupAngular().clickDelete();
        insertBluelinePopupAngular().waitPopupClosed();

        Assertions.assertAll
                (
                        () -> Assertions.assertTrue(firstStepHeaderIsExpected, String.format(
                                "Expected header didn't appear. \nExpected: '%s', \nActual: '%s'",
                                expectedHeaderFirstStep, actualHeaderFirstStep)),
                        () -> Assertions.assertTrue(isNewBluelinePresentInTree, String.format(
                                "New blueline '%s' is not found in the tree",
                                expectedBluelineName)),
                        () -> Assertions.assertTrue(isNewBluelineDeletedFromTree, String.format(
                                "Blueline '%s' is present in the tree after deletion",
                                expectedBluelineName)),
                        () -> Assertions.assertTrue(isSecondBluelinePresentInTree, String.format(
                                "Newly created blueline '%s' is not found in the tree",
                                expectedSecondBluelineName)),
                        () -> Assertions.assertTrue(isOldBluelineDeletedFromTree, String.format(
                                "Old deleted blueline '%s' is present in the tree again",
                                expectedBluelineName)),
                        () -> Assertions.assertTrue(isSecondBluelinePresentInTreeAfterRefresh, String.format(
                                "Newly created blueline '%s' is not found in the tree after page refresh",
                                expectedSecondBluelineName)),
                        () -> Assertions.assertTrue(isOldBluelineStillDeletedFromTreeAfterRefresh, String.format(
                                "Old deleted blueline '%s' is present in the tree again after page refresh",
                                expectedSecondBluelineName))
                );
    }

    /**
     * HALCYONST-9735
     * 1. Open Subscribed cases page for Iowa (Development)
     * 2. VERIFY: Subscribed Cases page is opened
     * 3. Click on the blue hyper link of any of the cases
     * 4. VERIFY: Headnotes page is opened
     * 5. Locate BL ANALYSI node
     * 6. Insert new blueline here, populate every field with "Test"
     * 7. Right click newly created blueline and select "Edit Blueline" option from context menu
     * 8. VERIFY: Edit Blueline pop up is shown.
     * 9. VERIFY: Breadcrumbs information is correct
     * 10. Select "Flush" type
     * 11. Type "SubstituteText" into Text field
     * 12. VERIFY: The Flush Analysis (NAL) is now populated with the same value as Text field
     * 13. Type "SubstituteText" into "Flush Analysis (NAL) - Index Also" field and press enter
     * 14 VERIFY: "test" tag is added to the field.
     * 15. Click "Cancel"
     * 16. VERIFY: The pop up is closed, no changes were made
     */
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void editBluelineDiscardChanges()
    {
        String caseSerial = "2045-261610";
        String searchString = "1D.1 1";
        String bluelineName = "BL 1 Validity";
        String blAnalysis = "BL ANALYSI ";
        String textToTypeWhenInsert = "Test";
        String textToTypeWhenEdit = "SubstituteText";
        String expectedHeader = "Edit Blueline";
        String unformattedBreadcrumbs = "@ IOWA CODE ANNOTATED; T. I; Subt. 1; Ch. 1D; = 1D.1; NOD CONTAI 1D.1; XND; BL ANALYSI; BL %s";
        //1. Open Subscribed cases page for IOWA (Development)
        subscribedCasesPageAngular().openNodSubscribedCasesPage(ContentSets.IOWA_DEVELOPMENT);
        loginPage().logIn();
        //2. VERIFY: Subscribed Cases page is opened
        Assertions.assertTrue(subscribedCasesPageAngular().isPageOpened(), "NOD Subscribed Cases page didn't open");
        //3. Click on the blue hyper link of any of the cases
        subscribedCasesTablePage().openHeadnotesPageByCaseSerial(caseSerial);
        //4. VERIFY: The Classification/Headnote page is opened
        Assertions.assertTrue(headnotesPageAngular().isPageOpened(), "Headnotes page didn't open");
        //5. Locate BL ANALYSI node
        hierarchyTreeFragmentAngular().quickFind(searchString);
        hierarchyTreeFragmentAngular().waitForElementScrolledTo(bluelineName);
        hierarchyTreeFragmentAngular().scrollToSeeParentLevelNode(blAnalysis);
        int newBluelineNumber = hierarchyTreeFragmentAngular().getLastBluelineNumberInBLAnalysisNode() +1;
        String expectedBreadcrumbs = String.format(unformattedBreadcrumbs, newBluelineNumber);
        //6. Insert new blueline here, populate every field with "Expert Testimony"
        hierarchyTreeFragmentAngular().openContextMenuForNodeWithText(blAnalysis);
        headnotesContextMenuAngular().selectInsertBlueline();
        insertBluelinePopupAngular().typeToBluelineNumberInput(newBluelineNumber);
        insertBluelinePopupAngular().clickNext();
        insertBluelinePopupAngular().waitForSecondStepHeaderToAppear();
        insertBluelinePopupAngular().selectFlushType();
        insertBluelinePopupAngular().typeToTextField(textToTypeWhenInsert);
        insertBluelinePopupAngular().clickTextInputDropdownOptionByText(textToTypeWhenInsert);
        insertBluelinePopupAngular().typeTagsToFlushAnalysisIndexAlsoField(textToTypeWhenInsert);
        insertBluelinePopupAngular().clickInsert();
        //16. VERIFY: A new Blueline is created.
        headnotesPageAngular().refreshPage();
        // remove this page update if driver version is changed or Chrome driver is used
        hierarchyTreeFragmentAngular().quickFind(searchString);
        hierarchyTreeFragmentAngular().scrollToSeeParentLevelNode(blAnalysis);
        List<String> bluelines = hierarchyTreeFragmentAngular().getAllBluelinesNamesInBluelineAnalysisNode(blAnalysis);
        String expectedBluelineName = String.format("BL %s %s", newBluelineNumber, textToTypeWhenInsert);
        boolean isNewBluelinePresentInTree = bluelines.contains(expectedBluelineName);
        Assertions.assertTrue(isNewBluelinePresentInTree, String.format("Newly created blueline '%s' is not found in the tree", expectedBluelineName));

        //6. Right click it and select "Edit Blueline" option from context menu
        hierarchyTreeFragmentAngular().openContextMenuForNodeWithText(expectedBluelineName);
        headnotesContextMenuAngular().selectEditBlueline();
        //7. VERIFY: Edit Blueline pop up is shown.
        Assertions.assertTrue(insertBluelinePopupAngular().isPageOpened(), "Insert blueline popup didn't open");
        String actualHeader = insertBluelinePopupAngular().getHeaderText();
        //8. VERIFY: Breadcrumbs information is correct
        String actualBreadcrumbs = insertBluelinePopupAngular().getBreadcrumbs();
        //9. Select "Flusyh" type
        insertBluelinePopupAngular().selectFlushType();
        //10. Type "SubstituteText" into Text field
        insertBluelinePopupAngular().clearTextField();
        insertBluelinePopupAngular().typeToTextField(textToTypeWhenEdit);
        //11. VERIFY: The Flush Analysis (NAL) is now populated with the same value as Text field
        String flushAnalysisText = insertBluelinePopupAngular().getFlushAnalysisFieldText();
        //12. Type "test" into "Flush Analysis (NAL) - Index Also" field and press enter
        insertBluelinePopupAngular().removeTagFromFlushAnalysisIndexAlso(textToTypeWhenInsert);
        insertBluelinePopupAngular().typeTagsToFlushAnalysisIndexAlsoField(textToTypeWhenEdit);
        //13. VERIFY: "test" tag is added to the field.
        boolean isTestTagPresent = insertBluelinePopupAngular().isTagPresentInFlushAnalysisIndexAlso(textToTypeWhenEdit);
        //14. Click "Cancel"
        insertBluelinePopupAngular().clickCancel();
        //15.  The pop up is closed, VERIFY: no changes were made
        hierarchyTreeFragmentAngular().clickQuickFind();
        String lastBluelineNameAfterChanges = hierarchyTreeFragmentAngular().getLastBluelineNameInBLAnalysisNode();
        hierarchyTreeFragmentAngular().openContextMenuForNodeWithText(lastBluelineNameAfterChanges);
        headnotesContextMenuAngular().selectEditBlueline();
        String textFromTextField = insertBluelinePopupAngular().getTextFromTextField();
        boolean textHasNotChanged = !textFromTextField.equals(textToTypeWhenEdit);
        String flushAnalysisTextAfterChanges = insertBluelinePopupAngular().getTextFromTextField();
        boolean flushAnalysisTextHasNotChanged = !flushAnalysisTextAfterChanges.equals(textToTypeWhenEdit);
        boolean isTagNotPresent = !insertBluelinePopupAngular().isTagPresentInFlushAnalysisIndexAlso(textToTypeWhenEdit);
        insertBluelinePopupAngular().clickCancel();
        //data clean up:
        hierarchyTreeFragmentAngular().openContextMenuForNodeWithText(lastBluelineNameAfterChanges);
        headnotesContextMenuAngular().selectDeleteBlueline();
        insertBluelinePopupAngular().clickDelete();
        insertBluelinePopupAngular().waitPopupClosed();

        Assertions.assertAll
                (
                        () -> Assertions.assertEquals(expectedHeader,actualHeader, "Expected header didn't appear."),
                        () -> Assertions.assertEquals(expectedBreadcrumbs, actualBreadcrumbs, "Breadcrunbs info is unexpected."),
                        () -> Assertions.assertEquals(textToTypeWhenEdit, flushAnalysisText, "Flush Analysis text is not expected."),
                        () -> Assertions.assertTrue(isTestTagPresent, String.format("Expected tag '%s' doesn't exist in Flush Analysis - Index Also", textToTypeWhenEdit)),
                        () -> Assertions.assertEquals(expectedBluelineName,lastBluelineNameAfterChanges, "The BL name is not expected'"),
                        () -> Assertions.assertTrue(textHasNotChanged, String.format("The text field contains '%s'", textToTypeWhenEdit)),
                        () -> Assertions.assertTrue(flushAnalysisTextHasNotChanged, String.format("The Flush Analysis field contains '%s'", textToTypeWhenEdit)),
                        () -> Assertions.assertTrue(isTagNotPresent, String.format("The Flush Analysis (NAL) - Index Also field contains '%s'", textToTypeWhenEdit))
                );
    }

    /**
     * HALCYONST-9735
     * 1. Open Subscribed cases page for Iowa (Development)
     * 2. VERIFY: Subscribed Cases page is opened
     * 3. Click on the blue hyper link of any of the cases
     * 4. VERIFY: Headnotes page is opened
     * 5. Locate BL ANALYSI node
     * 6. Insert new blueline here, populate every field with "Expert Testimony"
     * 7. Right click it and select "Edit Blueline" option from context menu
     * 8. VERIFY: Edit Blueline pop up is shown.
     * 9. VERIFY: Breadcrumbs information is correct
     * 10. Select "Flush" type
     * 11. Type "Test" into Text filed
     * 12. VERIFY: The dropdown is shown and "Test" option is there.
     * 13. Click on "Test" option.
     * 14. VERIFY: The Flush Analysis (NAL) is now populated with the same value as Text field
     * 15. Type "test" into "Flush Analysis (NAL) - Index Also" field and press enter
     * 16. VERIFY: "test" tag is added to the field.
     * 17. Click "Save"
     * 18. VERIFY: pop up is closed and all chages are saved.
     */
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void editBluelineSaveChanges()
    {
        String caseSerial = "2045-261610";
        String searchString = "1D.1 1";
        String bluelineName = "BL 1 Validity";
        String blAnalysis = "BL ANALYSI ";
        String textToTypeWhenInsert = "Expert testimony";
        String textToTypeWhenEdit = "Edit Text";
        String expectedHeader = "Edit Blueline";
        String unformattedBreadcrumbs = "@ IOWA CODE ANNOTATED; T. I; Subt. 1; Ch. 1D; = 1D.1; NOD CONTAI 1D.1; XND; BL ANALYSI; BL %s";
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
        //5. Locate BL ANALYSI node
        hierarchyTreeFragmentAngular().quickFind(searchString);
        hierarchyTreeFragmentAngular().waitForElementScrolledTo(bluelineName);
        hierarchyTreeFragmentAngular().scrollToSeeParentLevelNode(blAnalysis);
        int newBluelineNumber = hierarchyTreeFragmentAngular().getLastBluelineNumberInBLAnalysisNode() +1;
        String expectedBreadcrumbs = String.format(unformattedBreadcrumbs, newBluelineNumber);
        //6. Insert new blueline here, populate every field with "Expert Testimony"
        hierarchyTreeFragmentAngular().openContextMenuForNodeWithText(blAnalysis);
        headnotesContextMenuAngular().selectInsertBlueline();
        insertBluelinePopupAngular().typeToBluelineNumberInput(newBluelineNumber);
        insertBluelinePopupAngular().clickNext();
        insertBluelinePopupAngular().waitForSecondStepHeaderToAppear();
        insertBluelinePopupAngular().selectFlushType();
        insertBluelinePopupAngular().typeToTextField(textToTypeWhenInsert);
        insertBluelinePopupAngular().clickTextInputDropdownOptionByText(textToTypeWhenInsert);
        insertBluelinePopupAngular().typeTagsToFlushAnalysisIndexAlsoField(textToTypeWhenInsert);
        insertBluelinePopupAngular().clickInsert();
        //16. VERIFY: A new Blueline is created.
        headnotesPageAngular().refreshPage();
        // remove this page update if driver version is changed or Chrome driver is used

        hierarchyTreeFragmentAngular().quickFind(searchString);
        hierarchyTreeFragmentAngular().waitForElementScrolledTo(bluelineName);
        hierarchyTreeFragmentAngular().scrollToSeeParentLevelNode(blAnalysis);
        List<String> bluelines = hierarchyTreeFragmentAngular().getAllBluelinesNamesInBluelineAnalysisNode(blAnalysis);
        String expectedBluelineName = String.format("BL %s %s", newBluelineNumber, textToTypeWhenInsert);
        boolean isNewBluelinePresentInTree = bluelines.contains(expectedBluelineName);
        //7. Right click it and select "Edit Blueline" option from context menu
        String newBluelineName = String.format("BL %s %s", newBluelineNumber, textToTypeWhenInsert);
        hierarchyTreeFragmentAngular().openContextMenuForNodeWithText(newBluelineName);
        headnotesContextMenuAngular().selectEditBlueline();
        //7. VERIFY: Edit Blueline pop up is shown.
        boolean isInsertBluelinePopupOpened = insertBluelinePopupAngular().isPageOpened();
        Assertions.assertTrue(isInsertBluelinePopupOpened, "Insert blueline popup didn't open");
        String actualHeader = insertBluelinePopupAngular().getHeaderText();
        boolean isHeaderExpected = actualHeader.equals(expectedHeader);
        //8. VERIFY: Breadcrumbs information is correct
        String actualBreadcrumbs = insertBluelinePopupAngular().getBreadcrumbs();
        boolean breadcrumbsAreExpected = actualBreadcrumbs.equals(expectedBreadcrumbs);
        //9. Select "Flush" type
        insertBluelinePopupAngular().selectFlushType();
        //10. Type "EditText" into Text field
        insertBluelinePopupAngular().clearTextField();
        insertBluelinePopupAngular().typeToTextField(textToTypeWhenEdit);
        //11. VERIFY: The Flush Analysis (NAL) is now populated with the same value as Text field
        String flushAnalysisText = insertBluelinePopupAngular().getFlushAnalysisFieldText();
        boolean flushAnalysisTextIsExpected = flushAnalysisText.equals(textToTypeWhenEdit);
        //12. Type "test" into "Flush Analysis (NAL) - Index Also" field and press enter
        String previousTagText = insertBluelinePopupAngular().getTagTextInFlushAnalysisIndexAlso();
        insertBluelinePopupAngular().removeTagFromFlushAnalysisIndexAlso(previousTagText);
        insertBluelinePopupAngular().typeTagsToFlushAnalysisIndexAlsoField(textToTypeWhenEdit);
        //13. VERIFY: "Edit Text" tag is added to the field.
        boolean isTestTagPresent = insertBluelinePopupAngular().isTagPresentInFlushAnalysisIndexAlso(textToTypeWhenEdit);
        //14. Click "Save"
        insertBluelinePopupAngular().clickSave();
        //15. Wait for popup to close, VERIFY: all changes saved
        insertBluelinePopupAngular().waitPopupClosed();
        headnotesPageAngular().refreshPage();
        // remove this page update if driver version is changed or Chrome driver is used
        hierarchyTreeFragmentAngular().quickFind(searchString);
        hierarchyTreeFragmentAngular().waitForElementScrolledTo(bluelineName);
        String newBluelineNameAfterChange = String.format("BL %s %s", newBluelineNumber, textToTypeWhenEdit);
        String lastBluelineNameAfterChanges = hierarchyTreeFragmentAngular().getLastBluelineNameInBLAnalysisNode();
        boolean bluelineNameHasChanged = lastBluelineNameAfterChanges.equals(newBluelineNameAfterChange);
        hierarchyTreeFragmentAngular().openContextMenuForNodeWithText(newBluelineNameAfterChange);
        headnotesContextMenuAngular().selectEditBlueline();
        String textFromTextField = insertBluelinePopupAngular().getTextFromTextField();
        boolean textHasChanged = textFromTextField.equals(textToTypeWhenEdit);
        String flushAnalysisTextAfterChanges = insertBluelinePopupAngular().getTextFromTextField();
        boolean flushAnalysisTextNotChanged = flushAnalysisTextAfterChanges.equals(textToTypeWhenEdit);
        boolean isTagPresent = insertBluelinePopupAngular().isTagPresentInFlushAnalysisIndexAlso(textToTypeWhenEdit);
        insertBluelinePopupAngular().clickCancel();
        //data clean up:
        hierarchyTreeFragmentAngular().openContextMenuForNodeWithText(lastBluelineNameAfterChanges);
        headnotesContextMenuAngular().selectDeleteBlueline();
        insertBluelinePopupAngular().clickDelete();
        insertBluelinePopupAngular().waitPopupClosed();

        Assertions.assertAll
                (
                        () -> Assertions.assertTrue(isNewBluelinePresentInTree, String.format(
                                "Newly created blueline '%s' is not found in the tree",
                                expectedBluelineName)),
                        () -> Assertions.assertTrue(isHeaderExpected, String.format(
                                "Expected header didn't appear. \nExpected: '%s', \nActual: '%s'",
                                expectedHeader, actualHeader)),
                        () -> Assertions.assertTrue(breadcrumbsAreExpected, String.format(
                                "Breadcrunbs info is unexpected. \nExpected: %s, \nActual: %s",
                                expectedBreadcrumbs, actualBreadcrumbs)),
                        () -> Assertions.assertTrue(flushAnalysisTextIsExpected, String.format(
                                "Flush Analysis text is not expected. \nExpected: '%s', \nActual: '%s'",
                                textToTypeWhenEdit, flushAnalysisText)),
                        () -> Assertions.assertTrue(isTestTagPresent, String.format(
                                "Expected tag '%s' doesn't exist in Flush Analysis - Index Also",
                                textToTypeWhenEdit)),
                        () -> Assertions.assertTrue(bluelineNameHasChanged, String.format(
                                "The expected BL name '%s' was not found. Found: '%s'",
                                newBluelineNameAfterChange, lastBluelineNameAfterChanges)),
                        () -> Assertions.assertTrue(textHasChanged, String.format(
                                "The text field doesn't contain '%s'",
                                textToTypeWhenEdit)),
                        () -> Assertions.assertTrue(flushAnalysisTextNotChanged, String.format(
                                "The Flush Analysis field doesn't contain '%s'",
                                textToTypeWhenEdit)),
                        () -> Assertions.assertTrue(isTagPresent, String.format(
                                "The Flush Analysis (NAL) - Index Also field doesn't '%s'",
                                textToTypeWhenEdit))
                );
    }

    /**
     * HALCYONST-14127
     */
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void clearQuickFindFieldTest()
    {
        String caseSerial = "2045-085248";
        String searchString = "Test";
        //1. Open Subscribed cases page for IOWA (Development)
        subscribedCasesPageAngular().openNodSubscribedCasesPage(ContentSets.IOWA_DEVELOPMENT);
        loginPage().logIn();
        //2. VERIFY: Subscribed Cases page is opened
        Assertions.assertTrue(subscribedCasesPageAngular().isPageOpened(), "NOD Subscribed Cases page didn't open");
        //3. Click on the blue hyper link of any of the cases
        subscribedCasesTablePage().openHeadnotesPageByCaseSerial(caseSerial);
        //4. VERIFY: The Classification/Headnote page is opened
        Assertions.assertTrue(headnotesPageAngular().isPageOpened(), "Headnotes page didn't open");
        //5.  Right click on a BL ANALYSI node and select Insert Blueline from the context menu
        hierarchyTreeFragmentAngular().putTextInQuickFindField(searchString);
        hierarchyTreeFragmentAngular().clickClearQuickFind();
        String actualText = hierarchyTreeFragmentAngular().getTextInQuickFindField();
        Assertions.assertEquals("", actualText, "QuickFind field is NOT clear.");
    }

    /**
     * HALCYONST-14124
     */
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void bluelineHighlightedTest()
    {
        String caseSerial = "2045-085248";
        String searchString = "1D.1 1";
        String bluelineName = "BL 1 Validity";
        String blAnalysis = "BL ANALYSI ";
        //1. Open Subscribed cases page for IOWA (Development)
        subscribedCasesPageAngular().openNodSubscribedCasesPage(ContentSets.IOWA_DEVELOPMENT);
        loginPage().logIn();
        //2. VERIFY: Subscribed Cases page is opened
        Assertions.assertTrue(subscribedCasesPageAngular().isPageOpened(), "NOD Subscribed Cases page didn't open");
        //3. Click on the blue hyper link of any of the cases
        subscribedCasesTablePage().openHeadnotesPageByCaseSerial(caseSerial);
        //4. VERIFY: The Classification/Headnote page is opened
        Assertions.assertTrue(headnotesPageAngular().isPageOpened(), "Headnotes page didn't open");
        //5.  Right click on a BL ANALYSI node and select Insert Blueline from the context menu
        hierarchyTreeFragmentAngular().quickFind(searchString);
        hierarchyTreeFragmentAngular().waitForElementScrolledTo(bluelineName);
        hierarchyTreeFragmentAngular().scrollToSeeParentLevelNode(blAnalysis);
        String actualColor = hierarchyTreeFragmentAngular().getHighlightedNodeText();
        Assertions.assertEquals(bluelineName, actualColor, "The selected node is NOT highlighted.");
    }

    /**
     * HALCYONST-10011
        */
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void expandXndElementAutomaticallyTest()
    {
        String caseSerial = "2045-085248";
        String searchString = "1D.1";
        String nodeName = "= 1D.1 Standard time and daylight saving time";
        String bluelineName = "BL 1 Validity";
        //1. Open Subscribed cases page for IOWA (Development)
        subscribedCasesPageAngular().openNodSubscribedCasesPage(ContentSets.IOWA_DEVELOPMENT);
        loginPage().logIn();
        //2. VERIFY: Subscribed Cases page is opened
        Assertions.assertTrue(subscribedCasesPageAngular().isPageOpened(), "NOD Subscribed Cases page didn't open");
        //3. Click on the blue hyper link of any of the cases
        subscribedCasesTablePage().openHeadnotesPageByCaseSerial(caseSerial);
        //4. VERIFY: The Classification/Headnote page is opened
        Assertions.assertTrue(headnotesPageAngular().isPageOpened(), "Headnotes page didn't open");
        //5.  Right click on a BL ANALYSIS node and select Insert Blueline from the context menu
        hierarchyTreeFragmentAngular().quickFind(searchString);
        hierarchyTreeFragmentAngular().waitForElementScrolledTo(nodeName);
        Assertions.assertTrue(hierarchyTreeFragmentAngular().isNodeTreeDisplayed(bluelineName));
    }
}
