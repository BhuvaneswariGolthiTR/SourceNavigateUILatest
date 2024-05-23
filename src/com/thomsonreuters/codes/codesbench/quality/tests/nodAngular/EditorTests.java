package com.thomsonreuters.codes.codesbench.quality.tests.nodAngular;

import com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.hierarchy.navigate.PreviousWipVersionsContextMenuElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.popups.DocumentClosurePageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.popups.EditHVSInformationPageElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.EDGE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.Keys;

import java.util.stream.Stream;

import static com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.PreviousWipVersionsPageElements.closeButton;
import static com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.*;

public class EditorTests extends TestService
{

    /**
     * HALCYONST-9454
     * 1. Open Subscribed cases page for Iowa (Development)
     * 2. VERIFY: Subscribed Cases page is opened
     * 3. Click on the blue hyper link of any of the cases
     * 4. VERIFY: Headnotes page is opened
     * 5. Locate a section level document
     * 6. Right click a Section level document
     * 7. Select Edit Content from the context menu
     * 8. VERIFY: The Section level document will harness the Edit content options that we currently have and
     * the document will open in the Dynamic Scrolling editor.
     * <p>
     * 1. Open Subscribed cases page for Iowa (Development)
     * 2. VERIFY: Subscribed Cases page is opened
     * 3. Click on the blue hyper link of any of the cases
     * 4. VERIFY: Headnotes page is opened
     * 5. Locate a section level document
     * 6. Right click a Grade level document
     * 7. Select Edit Content from the context menu
     * 8. VERIFY: The Section level document will harness the Edit content options that we currently have and
     * the document will open in the Dynamic Scrolling editor.
     */

    private static Stream<Arguments> provideDataForEditContentTest()
    {
        return Stream.of(
                Arguments.of("123.3", "= 123.3 Definitions", "T. IV PUBLIC HEALTH [Chs. 123-158]"),
                Arguments.of("159.1", "= 159.1 Definitions", "T. V AGRICULTURE [Chs. 159-215A]")
        );
    }
    @ParameterizedTest(name = "Test: {displayName}; Index: {index}; Arguments: {arguments}")
    @MethodSource("provideDataForEditContentTest")
    @EDGE
    @LEGAL
    @LOG
    public void editContentTest(String sectionSearchString, String bluelineName, String sectionName)
    {
        String caseSerial = "2045-085248";
        //1. Open Subscribed cases page for IOWA (Development)
        subscribedCasesPageAngular().openNodSubscribedCasesPage(ContentSets.IOWA_DEVELOPMENT);
        // clean storage
        casesTablePage().cleanLocalStorage();
        loginPage().logIn();
        //2. VERIFY: Subscribed Cases page is opened
        boolean subscribedCasesPageIsOpened = subscribedCasesPageAngular().isPageOpened();
        Assertions.assertTrue(subscribedCasesPageIsOpened, "NOD Subscribed Cases page didn't open");
        //3. Click on the blue hyper link of any of the cases
        casesTablePage().openHeadnotesPageByCaseSerial(caseSerial);
        //4. VERIFY: The Classification/Headnote page is opened
        boolean headnotesPageIsOpened = headnotesPageAngular().isPageOpened();
        Assertions.assertTrue(headnotesPageIsOpened, "Headnotes page didn't open");
        //5. Locate a section level document
        hierarchyTreeFragmentAngular().quickFind(sectionSearchString);
        hierarchyTreeFragmentAngular().waitForElementScrolledTo(bluelineName);
        hierarchyTreeFragmentAngular().scrollToNodeByText(sectionName);
        //6. Right click a Section level document
        DateAndTimeUtils.takeNap(DateAndTimeUtils.THREE_SECONDS);
        hierarchyTreeFragmentAngular().openContextMenuForNodeWithText(sectionName);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        //7. Select Edit Content from the context menu
        headnotesContextMenuAngular().selectEditContent();
        DateAndTimeUtils.takeNap(DateAndTimeUtils.FIVE_SECONDS);
        //Sree: Test may fail after this point; In that case we need to ensure that DS Editor is opened
        boolean isEditorOpened = hierarchyTreeFragmentAngular().isWindowHandlesAsExpected(2);
        editorPage().closeCurrentWindowIgnoreDialogue();

        Assertions.assertAll
                (
                        () -> Assertions.assertTrue(isEditorOpened, "DS Editor didn't open")
                );
    }

    /**
     * HALCYONST-9445
     * 1. Open Subscribed cases page for Iowa (Development)
     * 2. VERIFY: Subscribed Cases page is opened
     * 3. Click on the blue hyper link of any of the cases
     * 4. VERIFY: Headnotes page is opened
     * 5. Locate a section level document
     * 6. Right click a Section level document
     * 7. Select View Content from the context menu
     * 8. VERIFY: Document is opened in DS Editor in view mode
     * <p>
     * 1. Open Subscribed cases page for Iowa (Development)
     * 2. VERIFY: Subscribed Cases page is opened
     * 3. Click on the blue hyper link of any of the cases
     * 4. VERIFY: Headnotes page is opened
     * 5. Locate a section level document
     * 6. Right click a Grade level document
     * 7. Select View Content from the context menu
     * 8. VERIFY: Document is opened in DS Editor in view mode
     */
    @ParameterizedTest(name = "Test: {displayName}; Index: {index}; Arguments: {arguments}")
    @MethodSource("provideDataForEditContentTest")
    @EDGE
    @LEGAL
    @LOG
    public void viewContentTest(String sectionSearchString, String bluelineName, String sectionName)
    {
        String caseSerial = "2045-085248";
        //1. Open Subscribed cases page for IOWA (Development)
        subscribedCasesPageAngular().openNodSubscribedCasesPage(ContentSets.IOWA_DEVELOPMENT);
        // clean storage
        casesTablePage().cleanLocalStorage();
        loginPage().logIn();
        //2. VERIFY: Subscribed Cases page is opened
        boolean subscribedCasesPageIsOpened = subscribedCasesPageAngular().isPageOpened();
        Assertions.assertTrue(subscribedCasesPageIsOpened, "NOD Subscribed Cases page didn't open");
        //3. Click on the blue hyper link of any of the cases
        casesTablePage().openHeadnotesPageByCaseSerial(caseSerial);
        //4. VERIFY: The Classification/Headnote page is opened
        boolean headnotesPageIsOpened = headnotesPageAngular().isPageOpened();
        Assertions.assertTrue(headnotesPageIsOpened, "Headnotes page didn't open");
        //5. Locate a section level document
        hierarchyTreeFragmentAngular().quickFind(sectionSearchString);
        hierarchyTreeFragmentAngular().waitForElementScrolledTo(bluelineName);
        hierarchyTreeFragmentAngular().scrollToNodeByText(sectionName);
        //6. Right click a Section level document
        DateAndTimeUtils.takeNap(DateAndTimeUtils.THREE_SECONDS);
        hierarchyTreeFragmentAngular().openContextMenuForNodeWithText(sectionName);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        //7. Select View Content from the context menu
        headnotesContextMenuAngular().selectViewContent();
        DateAndTimeUtils.takeNap(DateAndTimeUtils.FIVE_SECONDS);
        //Sree: Test may fail after this point; In that case we need to ensure that DS Editor is opened
        boolean isEditorOpened = hierarchyTreeFragmentAngular().isWindowHandlesAsExpected(2);

        Assertions.assertAll
                (
                        () -> Assertions.assertTrue(isEditorOpened, "DS Editor didn't open")
                );
    }

    /**
     * HALCYONST-9445
     * 1. Open Subscribed cases page for Iowa (Development)
     * 2. VERIFY: Subscribed Cases page is opened
     * 3. Click on the blue hyper link of any of the cases
     * 4. VERIFY: Headnotes page is opened
     * 5. Locate a section level document
     * 6. Right click a Section level document
     * 7. Select View Content Raw Xml from the context menu
     * 8. VERIFY: Document is opened in Raw Xml Editor in view mode
     * <p>
     * 1. Open Subscribed cases page for Iowa (Development)
     * 2. VERIFY: Subscribed Cases page is opened
     * 3. Click on the blue hyper link of any of the cases
     * 4. VERIFY: Headnotes page is opened
     * 5. Locate a section level document
     * 6. Right click a Grade level document
     * 7. Select View Content Raw Xml from the context menu
     * 8. VERIFY: Document is opened in Raw Xml Editor in view mode
     */
    @ParameterizedTest(name = "Test: {displayName}; Index: {index}; Arguments: {arguments}")
    @MethodSource("provideDataForEditContentTest")
    @EDGE
    @LEGAL
    @LOG
    public void viewContentRawXmlTest(String sectionSearchString, String bluelineName, String sectionName)
    {
        String caseSerial = "2045-085248";
        //1. Open Subscribed cases page for IOWA (Development)
        subscribedCasesPageAngular().openNodSubscribedCasesPage(ContentSets.IOWA_DEVELOPMENT);
        // clean storage
        casesTablePage().cleanLocalStorage();
        loginPage().logIn();
        //2. VERIFY: Subscribed Cases page is opened
        boolean subscribedCasesPageIsOpened = subscribedCasesPageAngular().isPageOpened();
        Assertions.assertTrue(subscribedCasesPageIsOpened, "NOD Subscribed Cases page didn't open");
        //3. Click on the blue hyper link of any of the cases
        casesTablePage().openHeadnotesPageByCaseSerial(caseSerial);
        //4. VERIFY: The Classification/Headnote page is opened
        boolean headnotesPageIsOpened = headnotesPageAngular().isPageOpened();
        Assertions.assertTrue(headnotesPageIsOpened, "Headnotes page didn't open");
        //5. Locate a section level document
        hierarchyTreeFragmentAngular().quickFind(sectionSearchString);
        hierarchyTreeFragmentAngular().waitForElementScrolledTo(bluelineName);
        hierarchyTreeFragmentAngular().scrollToNodeByText(sectionName);
        //6. Right click a Section level document
        hierarchyTreeFragmentAngular().openContextMenuForNodeWithText(sectionName);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        //7. Select Edit Content from the context menu
        headnotesContextMenuAngular().selectViewContentRawXml();
        DateAndTimeUtils.takeNap(DateAndTimeUtils.FIVE_SECONDS);
        boolean isEditorOpened = headnotesContextMenuAngular().isWindowHandlesAsExpected(2);

        Assertions.assertAll
                (
                        () -> Assertions.assertTrue(isEditorOpened, "Raw Xml Editor didn't open")
                );
    }

    /**
     * HALCYONST-9460
     * 1. Open Subscribed cases page for Iowa (Development)
     * 2. VERIFY: Subscribed Cases page is opened
     * 3. Click on the blue hyper link of any of the cases
     * 4. VERIFY: Headnotes page is opened
     * 5. Locate a section level document
     * 6. Right click a Section level document
     * 7. Select Find in Hierarchy from the context menu
     * 8. VERIFY: The Hierarchy Edit appears in a separate IE window
     * 9. VERIFY: The selected Section level document is located and highlighted in the Hierarchy Edit window
     */

    private static Stream<Arguments> provideDataForFindInHierarchyTest()
    {
        return Stream.of(
                Arguments.of("123.2", "= 123.3 Definitions", "= 123.2 General prohibition", "(1)"),
                Arguments.of("159.1", "= 159.1 Definitions", "T. V AGRICULTURE [Chs. 159-215A]", "(4)")
        );
    }

    @ParameterizedTest(name = "Test: {displayName}; Index: {index}; Arguments: {arguments}")
    @MethodSource("provideDataForFindInHierarchyTest")
    @EDGE
    @LEGAL
    @LOG
    public void findInHierarchyTest(String sectionSearchString, String bluelineName, String sectionName, String numberOfChildrenNodes)
    {
        String caseSerial = "2045-085248";
        String expectedNodeValue = sectionName + numberOfChildrenNodes;
        //1. Open Subscribed cases page for IOWA (Development)
        subscribedCasesPageAngular().openNodSubscribedCasesPage(ContentSets.IOWA_DEVELOPMENT);
        // clean storage
        casesTablePage().cleanLocalStorage();
        loginPage().logIn();
        //2. VERIFY: Subscribed Cases page is opened
        boolean subscribedCasesPageIsOpened = subscribedCasesPageAngular().isPageOpened();
        Assertions.assertTrue(subscribedCasesPageIsOpened, "NOD Subscribed Cases page didn't open");
        //3. Click on the blue hyper link of any of the cases
        casesTablePage().openHeadnotesPageByCaseSerial(caseSerial);
        //4. VERIFY: The Classification/Headnote page is opened
        boolean headnotesPageIsOpened = headnotesPageAngular().isPageOpened();
        Assertions.assertTrue(headnotesPageIsOpened, "Headnotes page didn't open");
        //5. Locate a section level document
        hierarchyTreeFragmentAngular().quickFind(sectionSearchString);
        hierarchyTreeFragmentAngular().waitForElementScrolledTo(bluelineName);
        hierarchyTreeFragmentAngular().scrollToNodeByText(sectionName);
        //6. Right click a Section level document
        hierarchyTreeFragmentAngular().openContextMenuForNodeWithText(sectionName);
        //7. Select Edit Content from the context menu
        headnotesContextMenuAngular().selectFindInHierarchy();
        hierarchyNavigatePage().switchToHierarchyEditPage();
        hierarchyNavigatePage().waitForPageLoaded();
        hierarchyNavigatePage().maximizeCurrentWindow();
        //8. VERIFY: The Hierarchy Edit appears in a separate IE window
        boolean isPageOpened = hierarchyNavigatePage().checkIfPageIsOpened();
        //9. VERIFY: The selected Section level document is located and highlighted in the Hierarchy Edit window
        String selectedNodeValue = hierarchyTreePage().getSelectedCollapsedTreeNodeValue();
        boolean isExpectedNodeSelected = selectedNodeValue.equals(expectedNodeValue);

        Assertions.assertAll
                (
                        () -> Assertions.assertTrue(isPageOpened, "Raw Xml Editor didn't open"),
                        () -> Assertions.assertTrue(isExpectedNodeSelected, String.format("Expected node is not selected" +
                                "\nExpected: %s, \nActual: %s", expectedNodeValue, selectedNodeValue))
                );
    }

    /**
     * HALCYONST-9087
     * 1. Log onto the Canada Ontario (Development) content set
     * 2. Go to Hierarchy -> Navigate, and find node with UUID: ID79D01902D1B11EBA719E384E2D2BED9
     * 3. VERIFY: This is Blueline document
     * 4. Right click this node and select Edit Content from the context menu
     * 5. Locate GNP record (This is a  Note of Decision Paragraph)
     * 6. Place cursor in the text of the GNP record
     * 7. Type in "testing"
     * 8. Click the green close button on the toolbar
     * 9. Document closure UI will come up
     * 10. VERIFY: That the new radio button "Canada" is located under the Quickload option
     * 11. Select the Canada radio button, click the Check In button
     * 12. Right click the node again and select Edit Content from the context menu
     * 13. VERIFY: Changes are checked in
     */
    @Test
    @IE_EDGE_MODE
    @CARSWELL
    @LOG
    public void findInHierarchyCheckInChangesTest()
    {
        String nodeUuid = "ID79D01902D1B11EBA719E384E2D2BED9";
        String expectedKeyword = "BLUE LINE";
        String phrase = "testing" + (int) (Math.random() * 1000);

        //1. Log onto the Iowa (Development) content set
        //2. Go to Hierarchy -> Navigate, and find node with UUID: ID79D01902D1B11EBA719E384E2D2BED9
        hierarchyNavigatePage().goToHierarchyPage(ContentSets.CANADA_ONTARIO_DEVELOPMENT.getCode());
        loginPage().logIn();
        hierarchySearchPage().searchNodeUuid(nodeUuid);
        //3. VERIFY: This is Blueline document
        String actualKeyword = siblingMetadataPage().getSelectedGridRowKeyword();
        //4. Right click this node and select Edit Content from the context menu
        siblingMetadataPage().selectedSiblingMetadataEditContent();
        editorPage().switchToEditor();
        //5. Locate GNP record (This is a  Note of Decision Paragraph)
        //6. Place cursor in the text of the GNP record
        //7. Type in "testing"
        editorTextPage().switchToEditorTextArea();
        editorTextPage().click(EditorTextPageElements.NDP_TEXT);
        editorTextPage().sendKeys(Keys.F7);
        //editorTextPage().sendKeysToElement(EditorTextPageElements.NDP_TEXT, phrase);
        editorTextPage().sendKeys(phrase);

        //8. Click the green close button on the toolbar
        editorPage().pressCloseDocumentButton();
        //9. Document closure UI will come up
        editorPage().switchToWindow(DocumentClosurePageElements.PAGE_TITLE);
        //10. VERIFY: That the new radio button "Canada" is located under the Quickload option
        editorClosurePage().waitForElement(DocumentClosurePageElements.canadaRadioButton);
        boolean isCanadaRadioButtonExist = DocumentClosurePageElements.canadaRadioButton.isDisplayed();
        //11. Select the Canada radio button, click the Check In button
        editorClosurePage().click(DocumentClosurePageElements.checkInButton);
        editorPage().closeSpellcheckWindow();
        //12. Right click the node again and select Edit Content from the context menu
        hierarchyNavigatePage().switchToHierarchyEditPage();
        siblingMetadataPage().selectedSiblingMetadataEditContent();
        editorPage().switchToEditor();
        //13. VERIFY: Changes are checked in
        editorTextPage().switchToEditorTextArea();
        String newText = editorTextPage().getElementsText(EditorTextPageElements.NDP_TEXT);
        //14. Click the green close button on the toolbar
        editorPage().closeDocumentWithNoChanges();

        Assertions.assertAll
                (
                        () -> Assertions.assertEquals(expectedKeyword, actualKeyword, String.format("Expected Blueline document, but is '%s'", actualKeyword)),
                        () -> Assertions.assertTrue(isCanadaRadioButtonExist, "The new radio button 'Canada' is NOT displayed"),
                        () -> Assertions.assertEquals(phrase, newText, "Changes did NOT save")
                );

    }

    /**
     * HALCYONST-9087
     * 1. Log onto the Canada Ontario (Development) content set
     * 2. Go to Hierarchy -> Navigate, and find node with UUID: ID79D01902D1B11EBA719E384E2D2BED9
     * 3. VERIFY: This is Blueline document
     * 4. Right click and select View/Modify Previous WIP Version
     * 5. VERIFY: Previous WIP Versions window appears, and the latest WIP version has the Law Tracking status "Canada"
     * 6. Right click 0 WIP version and select Restore WIP Content
     */
    @Test
    @IE_EDGE_MODE
    @CARSWELL
    @LOG
    public void vipVersionTest()
    {
        String nodeUuid = "ID79D01902D1B11EBA719E384E2D2BED9";
        String expectedKeyword = "BLUE LINE";
        String expectedLawTrackingStatus = "Canada";

        //1. Log onto the Iowa (Development) content set
        //2. Go to Hierarchy -> Navigate, and find node with UUID: ID79D01902D1B11EBA719E384E2D2BED9
        hierarchyNavigatePage().goToHierarchyPage(ContentSets.CANADA_ONTARIO_DEVELOPMENT.getCode());
        loginPage().logIn();
        hierarchySearchPage().searchNodeUuid(nodeUuid);
        //3. VERIFY: This is Blueline document
        String actualKeyword = siblingMetadataPage().getSelectedGridRowKeyword();
        //4. Right click this node and select Edit Content from the context menu
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().viewModifyPreviousWIPVersion();
        //16. VERIFY: Previous WIP Versions window appears, and the latest WIP version has the Law Tracking status "Canada"
        //17. Right click 0 WIP version and select Resore WIP Content
        String actualLawTrackingStatus = previousWipVersionsPage().getLawTrackinStatusByIndex("1");

        Assertions.assertAll
                (
                        () -> Assertions.assertEquals(expectedKeyword, actualKeyword, String.format("Expected Blueline document, but is '%s'", actualKeyword)),
                        () -> Assertions.assertEquals(expectedLawTrackingStatus, actualLawTrackingStatus, "the latest WIP version has the Law Tracking status is NOT 'Canada'")
                );

    }

    /**
     * HALCYONST-9087
     * 1. Log onto the Canada Ontario (Development) content set
     * 2. Go to Hierarchy -> Navigate, and find node with UUID: ID79D01902D1B11EBA719E384E2D2BED9
     * 3.VERIFY: This is Blueline document
     * 4. Right click the node and select Edit Content from the context menu
     * 5. Locate GNP record
     * 6. Place cursor in the text of the GNP record
     * 7. Type in "testing"
     * 8. Click the green close button on the toolbar
     * 9. Document closure UI will come up
     * 10. VERIFY: That the new radio button "Canada" is located under the Quickload option
     * 11. Select the Canada radio button, then click the Cancel button
     * 12. Right click the node again and select Edit Content from the context menu
     * 13. VERIFY: Changes were not checked in
     */
    @Test
    @IE_EDGE_MODE
    @CARSWELL
    @LOG
    public void findInHierarchyDiscardChangesTest()
    {
        String nodeUuid = "IE4928320340511EB8351CBC688A29B57";
        String expectedKeyword = "BLUE LINE";
        String phrase = "testing" + (int) (Math.random() * 1000);

        //1. Log onto the Iowa (Development) content set
        //2. Go to Hierarchy -> Navigate, and find node with UUID: ID79D01902D1B11EBA719E384E2D2BED9
        hierarchyNavigatePage().goToHierarchyPage(ContentSets.CANADA_ONTARIO_DEVELOPMENT.getCode());
        loginPage().logIn();
        hierarchySearchPage().searchNodeUuid(nodeUuid);
        //3. VERIFY: This is Blueline document
        String actualKeyword = siblingMetadataPage().getSelectedGridRowKeyword();
        //4. Right click this node and select Edit Content from the context menu
        siblingMetadataPage().selectedSiblingMetadataEditContent();
        editorPage().switchToEditor();
        //5. Locate GNP record (This is a  Note of Decision Paragraph)
        //6. Place cursor in the text of the GNP record
        //7. Type in "testing"
        editorTextPage().switchToEditorTextArea();
        String expectedText = editorTextPage().getElementsText(EditorTextPageElements.NDP_TEXT);
        editorTextPage().click(EditorTextPageElements.NDP_TEXT);
        editorTextPage().sendKeys(Keys.F7);
        editorTextPage().sendKeys(phrase);
        //editorTextPage().sendTextToTextbox(EditorTextPageElements.NDP_TEXT, phrase);
        //8. Click the green close button on the toolbar
        editorPage().pressCloseDocumentButton();
        //9. Document closure UI will come up
        editorPage().switchToWindow(DocumentClosurePageElements.PAGE_TITLE);
        //10. VERIFY: That the new radio button "Canada" is located under the Quickload option
        editorClosurePage().waitForElement(DocumentClosurePageElements.canadaRadioButton);
        boolean isCanadaRadioButtonExist = DocumentClosurePageElements.canadaRadioButton.isDisplayed();
        //11. Select the Canada radio button, then click the Cancel button
        editorClosurePage().click(DocumentClosurePageElements.discardButton);
        editorClosurePage().acceptAlertNoFail();
        //12. Right click the node again and select Edit Content from the context menu
        hierarchyNavigatePage().switchToHierarchyEditPage();
        siblingMetadataPage().selectedSiblingMetadataEditContent();
        editorPage().switchToEditor();
        //13. VERIFY: Changes are checked in
        editorTextPage().switchToEditorTextArea();
        String actualText = editorTextPage().getElementsText(EditorTextPageElements.NDP_TEXT);
        //14. Click the green close button on the toolbar
        editorPage().closeDocumentWithNoChanges();

        Assertions.assertAll
                (
                        () -> Assertions.assertEquals(expectedKeyword, actualKeyword, String.format("Expected Blueline document, but is '%s'", actualKeyword)),
                        () -> Assertions.assertTrue(isCanadaRadioButtonExist, "The new radio button 'Canada' is NOT displayed"),
                        () -> Assertions.assertEquals(expectedText, actualText, "Changes did NOT save")
                );

    }

    /**
     * HALCYONST-8395
     * 1. Log onto Canada Ontario (Development) content set
     * 2. Go to Hierarchy -> Navigate, and find node with HID: 487018902
     * 3. VERIFY: The document is found
     * 4. Expand this node until a Blueline is found, right click on it and select the Edit Content context menu option.
     * 5. VERIFY: DS Editor is opened.
     * 6. VERIFY: "Refresh Pubtags"  button is grayed-out (disabled)
     */
    @Test
    @IE_EDGE_MODE
    @CARSWELL
    @LOG
    public void pubtagsDisabledCanadianContentSetsTest()
    {
        String nodeUuid = "IE4928320340511EB8351CBC688A29B57";
        String expectedKeyword = "BLUE LINE";

        //1. Log onto the Iowa (Development) content set
        //2. Go to Hierarchy -> Navigate, and find node with UUID: ID79D01902D1B11EBA719E384E2D2BED9
        hierarchyNavigatePage().goToHierarchyPage(ContentSets.CANADA_ONTARIO_DEVELOPMENT.getCode());
        loginPage().logIn();
        hierarchySearchPage().searchNodeUuid(nodeUuid);
        //3. VERIFY: This is Blueline document
        String actualKeyword = siblingMetadataPage().getSelectedGridRowKeyword();
        //4. Right click this node and select Edit Content from the context menu
        siblingMetadataPage().selectedSiblingMetadataEditContent();
        editorPage().switchToEditor();
        //5. VERIFY: "Refresh Pubtags"  button is grayed-out (disabled)
        boolean pubtagRefreshButtonDisabled = editorToolbarPage().isPubtagRefreshButtonEnabled();
        //6. Click the green close button on the toolbar
        editorPage().closeDocumentWithNoChanges();

        Assertions.assertAll
                (
                        () -> Assertions.assertEquals(expectedKeyword, actualKeyword, String.format("Expected Blueline document, but is '%s'", actualKeyword)),
                        () -> Assertions.assertTrue(pubtagRefreshButtonDisabled, "'Pubtag Refresh' toolbar button should be disabled")
                );

    }

    /**
     * HALCYONST-8395
     * 1. Log onto Texas (Development) content set
     * 2. Go to Hierarchy -> Navigate, and find node with HID: 6751892
     * 4.VERIFY: The document is found.
     * 5. Expand this node until a Blueline is found, right click on it and select the Edit Content from the context menu
     * 6. VERIFY: The content is opened in the Dynamic Scrolling editor.
     * 7. VERIFY: The Refresh Pubtags button is enabled
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void pubtagsDisabledUSContentSetsTest()
    {
        String nodeUuid = "I1FE5A5E01B8011DC868395DC84A0FC63";
        String expectedKeyword = "BLUE LINE";

        //1. Log onto the Iowa (Development) content set
        //2. Go to Hierarchy -> Navigate, and find node with UUID: ID79D01902D1B11EBA719E384E2D2BED9
        hierarchyNavigatePage().goToHierarchyPage(ContentSets.IOWA_DEVELOPMENT.getCode());
        loginPage().logIn();
        hierarchySearchPage().searchNodeUuid(nodeUuid);
        //3. VERIFY: This is Blueline document
        String actualKeyword = siblingMetadataPage().getSelectedGridRowKeyword();
        //4. Right click this node and select Edit Content from the context menu
        siblingMetadataPage().selectedSiblingMetadataEditContent();
        editorPage().switchToEditor();
        //5. VERIFY: The Refresh Pubtags button is enabled
        boolean pubtagRefreshButtonDisabled = editorToolbarPage().isPubtagRefreshButtonEnabled();
        //6. Click the green close button on the toolbar
        editorPage().closeDocumentWithNoChanges();

        Assertions.assertAll
                (
                        () -> Assertions.assertEquals(expectedKeyword, actualKeyword, String.format("Expected Blueline document, but is '%s'", actualKeyword)),
                        () -> Assertions.assertFalse(pubtagRefreshButtonDisabled, "'Pubtag Refresh' toolbar button should be disabled")
                );

    }

    /**
     * HALCYONST-8401
     * 1. Log onto Canada Ontario (Development) content set
     * 2. Go to Hierarchy -> Navigate, and find node with UUID: ID79D01902D1B11EBA719E384E2D2BED9
     * 4. Right click on this document and select the Edit Content context menu option
     * 5. Select HVS metadata, right click on the serial number
     * 6. VERIFY: HVS UI comes up
     * 7. Change CCDB number to 1234567
     * 8. Change NOD type to "J"
     * 9. Change headnote humber to 123456
     * 10. Change Neutral citation to 2020 SKCA 123
     * 11. Select the Save button
     * 12. VERIFY: UI has closed and serial number 1234567890 is now displayed in the HVS information on the NOD record
     */
    @Test
    @IE_EDGE_MODE
    @CARSWELL
    @LOG
    public void updateHVSCanadianContentSetsTest() throws InterruptedException
    {
        String nodeUuid = "IE4928320340511EB8351CBC688A29B57";
        String expectedKeyword = "BLUE LINE";
        String newCCDB = "1234567";
        String newNodType = "J";
        String newHeadnoteNumber = "123456";
        String newNeutralCitation = "2020 SKCA 123";

        //1. Log onto Canada Ontario (Development) content set
        //2. Go to Hierarchy -> Navigate, and find node with UUID: ID79D01902D1B11EBA719E384E2D2BED9
        hierarchyNavigatePage().goToHierarchyPage(ContentSets.CANADA_ONTARIO_DEVELOPMENT.getCode());
        loginPage().logIn();
        hierarchySearchPage().searchNodeUuid(nodeUuid);
        //3. VERIFY: This is Blueline document
        String actualKeyword = siblingMetadataPage().getSelectedGridRowKeyword();
        //4. Right click this node and select Edit Content from the context menu
        siblingMetadataPage().selectedSiblingMetadataEditContent();
        editorPage().switchToEditor();
        //5. Select HVS metadata, right click on the serial number
        editorTextPage().switchToEditorTextArea();
        editorTextPage().rightClick(EditorTextPageElements.hvsSerialNumber);
        boolean editHvsInformationWindowAppeared = editHvsInformationPage().switchToWindow(EditHVSInformationPageElements.PAGE_TITLE);
        //6. VERIFY: HVS UI comes up
        Assertions.assertTrue(editHvsInformationWindowAppeared, "Edit HVSwindow should appear");
        //7. Change CCDB number to 1234567
        editHvsInformationPage().setCCDB(newCCDB);
        //8. Change NOD type to "J"
        editHvsInformationPage().selectNodType(newNodType);
        //9. Change headnote humber to 123456
        editHvsInformationPage().setHeadnoteNumber(newHeadnoteNumber);
        //10. Change Neutral citation to 2020 SKCA 123
        editHvsInformationPage().setNeutralCitation(newNeutralCitation);
        //11. Select the Save button
        editHvsInformationPage().clickSaveButton();
        editorPage().switchToEditor();
        editorTextPage().switchToEditorTextArea();
        //12. VERIFY: UI has closed and serial number 1234567890 is now displayed in the HVS information on the NOD record
        String actualCCDB = editorPage().getHvsCcdb();
        String actualNodType = editorPage().getHvsNodType();
        String actualHeadnoteNumber = editorPage().getHvsHeadnoteNumber();
        String actualNeutralCitation = editorPage().getHvsNeutralCitation();
        editorPage().closeDocumentAndDiscardChanges();

        Assertions.assertAll
                (
                        () -> Assertions.assertEquals(expectedKeyword, actualKeyword, String.format("Expected Blueline document, but is '%s'", actualKeyword)),
                        () -> Assertions.assertEquals(newCCDB, actualCCDB, String.format("CBDD is '%s' instead of '%s'", actualCCDB, newCCDB)),
                        () -> Assertions.assertEquals(newNodType, actualNodType, String.format("NOD type is '%s' instead of '%s'", actualNodType, newNodType)),
                        () -> Assertions.assertEquals(newHeadnoteNumber, actualHeadnoteNumber, String.format("Headnote number is '%s' instead of '%s'", actualHeadnoteNumber, newHeadnoteNumber)),
                        () -> Assertions.assertEquals(newNeutralCitation, actualNeutralCitation, String.format("Neutral citation is '%s' instead of '%s'", actualNeutralCitation, newNeutralCitation))
                );

    }

    /**
     * HALCYONST-10806
     * 1. Log onto Canada Ontario (Development) content set
     * 2. Go to Hierarchy -> Navigate, and find node with UUID: ID79D01902D1B11EBA719E384E2D2BED9
     * 3. Right click the Section document and select Edit Raw Xml from the context menu
     * 4. Document will open in the Raw XML editor
     * 5. Make a change to the first text record by adding in your name.
     * 6. Select the check-in box at the lower left of the page
     * 7. VERIFY: The XML document is checked in and WIP version is created
     * 8. Right click the Section document and select View/Modify Previous WIP Version from the context menu
     * 9. Right click the 0 WIP version and selection Restore WIP Content from the context menu
     */
    @Test
    @IE_EDGE_MODE
    @CARSWELL
    @LOG
    public void editRawXmlCanadaTest()
    {
        String nodeUuid = "IE4928320340511EB8351CBC688A29B57";
        String xml = "<nod.body>" +
                "  <codes.head ID=\"ID79D4FB02D1B11EBA719E384E2D2BED9\">" +
                "    <metadata.block owner=\"ID79D4FB02D1B11EBA719E384E2D2BED9\">" +
                "      <md.mnem>bl</md.mnem>" +
                "      <md.pub.tag.info>" +
                "        <md.pub.tag>WL</md.pub.tag>" +
                "      </md.pub.tag.info>" +
                "      <md.source.tag>MV</md.source.tag>" +
                "      <modified.by>autoClassifyUser 11/22/2020</modified.by>" +
                "    </metadata.block>" +
                "    <head.info>" +
                "      <label.designator>1</label.designator>.&emsp;" +
                "      <headtext>In general</headtext>" +
                "    </head.info>" +
                "  </codes.head>" +
                "  <nod.note>" +
                "    <para ID=\"ID7AD55402D1B11EBA719E384E2D2BED9\">" +
                "      <metadata.block owner=\"ID7AD55402D1B11EBA719E384E2D2BED9\">" +
                "        <md.mnem>gnp</md.mnem>" +
                "        <md.nod.info>" +
                "          <md.nod.type>U</md.nod.type>" +
                "          <md.serial.no>2049267876</md.serial.no>" +
                "          <md.ccdb>1892003</md.ccdb>" +
                "        </md.nod.info>" +
                "        <md.headnote.no>176</md.headnote.no>" +
                "        <md.neutral courtline=\"7094\">2019 ONSC 2626</md.neutral>" +
                "        <md.source.tag>21</md.source.tag>" +
                "      </metadata.block>" +
                "      <paratext>TEST" +
                "      </paratext>" +
                "    </para>" +
                "  </nod.note>" +
                "</nod.body>"
                ;
        String expectedText = "TEST";

        //1. Log onto Canada Ontario (Development) content set
        //2. Go to Hierarchy -> Navigate, and find node with UUID: ID79D01902D1B11EBA719E384E2D2BED9
        hierarchyNavigatePage().goToHierarchyPage(ContentSets.CANADA_ONTARIO_DEVELOPMENT.getCode());
        loginPage().logIn();
        hierarchySearchPage().searchNodeUuid(nodeUuid);
        //3. Right click the Section document and select Edit Raw Xml from the context menu
        siblingMetadataPage().editRawXmlSelectedSiblingMetadata();
        //4. Document will open in the Raw XML editor
        rawXmlEditorPage().switchToRawXmlEditorPage();
        //5. Make a change to the first text record by adding in your name.
        rawXmlEditorPage().sendTextToEditor(xml);
        //6. Select the check-in box at the lower left of the page
        rawXmlEditorPage().clickValidate();
        rawXmlEditorPage().clickSave();
        //rawXmlDocumentClosurePage().clickCanada();
        rawXmlDocumentClosurePage().clickQuickLoad();
        rawXmlDocumentClosurePage().clickCheckInButton();
        //7. VERIFY: The XML document is checked in and WIP version is created
        //8. Right click the Section document and select View/Modify Previous WIP Version from the context menu
        //9. Right click the 0 WIP version and selection Restore WIP Content from the context menu
        hierarchyNavigatePage().switchToHierarchyEditPage();
        hierarchyNavigatePage().waitForPageLoaded();
        siblingMetadataPage().selectedSiblingMetadataEditContent();
        editorPage().switchToEditor();
        //5. Locate GNP record (This is a  Note of Decision Paragraph)
        //6. Place cursor in the text of the GNP record
        //7. Type in "testing"
        editorTextPage().switchToEditorTextArea();
        String actualText = editorTextPage().getElementsText(EditorTextPageElements.NDP_TEXT);
        editorPage().closeDocumentWithNoChanges();
        hierarchyNavigatePage().switchToHierarchyEditPage();
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().viewModifyPreviousWIPVersion();
        previousWipVersionsPage().rightClickWipVersionByIndex("2");
        previousWipVersionsPage().sendEnterToElement(PreviousWipVersionsContextMenuElements.RESTORE_WIP_CONTENT);
        confirmActionPage().switchToConfirmActionPage();
        confirmActionPage().clickOK();

        Assertions.assertAll
                (
                        () -> Assertions.assertEquals(expectedText, actualText, "Changes were NOT saved. ")
                );

    }


}
