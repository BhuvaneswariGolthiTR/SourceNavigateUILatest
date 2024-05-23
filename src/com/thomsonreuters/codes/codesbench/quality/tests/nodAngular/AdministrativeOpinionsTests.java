package com.thomsonreuters.codes.codesbench.quality.tests.nodAngular;

import com.thomsonreuters.codes.codesbench.quality.pageelements.nodAngular.headnotesPageFragmentsElements.HierarchyTreeFragmentElementsAngular;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.EDGE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.hierarchy.HierarchyDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.nodangular.NodAngularDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.CommonDataMocking;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.TargetDataMockingNew;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.datapod.HierarchyDatapodConfiguration;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.datapod.HierarchyDatapodObject;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.sql.Connection;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class AdministrativeOpinionsTests extends TestService
{
    Connection connection;
    HierarchyDatapodObject datapodObject;
    ContentSets contentSet = ContentSets.IOWA_DEVELOPMENT;
    String contentSetCode = ContentSets.IOWA_DEVELOPMENT.getCode();

    /**
     * HALCYONST-10750
     * 1. Open NodClassifyUI home page
     * 2. Select Administrative Opinions Option
     * 3. VERIFY: the Administrative Opinions page is opened
     * 4. Click Insert Opinion
     * 5. VERIFY: the Insert Opinion page is opened
     * 6. Select Type: Attorney General
     * 7. VERIFY: Content set is expected
     * 8. Type Opinion Number: 02-306
     * 9. Type Date of Opinion:  today's date
     * 10. VERIFY: Editor's name is correct
     * 11. Type Text:  "Lorem ipsum dolor sit amet"
     * 12. Type Opinion Citation:  Atty Gen. OP. 02-306
     * 13. Type WL Number: "2021 WL 123456"
     * 14. Click on Insert button
     * 15. VERIFY: Opinion appeared in table
     * 16. Open opinion you've just inserted
     * 17. VERIFY: All data is saved correctly
     */
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void createNewAdministrativeOpinionAndInsertTest()
    {
        String opinionType = "Attorney General";
        String opinionNumber = DateAndTimeUtils.getCurrentDateAndTimeyyyyMMddHHmmss();
        String text = "Lorem ipsum dolor sit amet";
        String citation = "Atty Gen. OP. 02-306";
        String wlNumber = "2021 WL 123456";
        String expectedNotification = "Opinion inserted";

        try
        {
            // Open NodClassifyUI home page and go to Admin Opinions tab
            homePageAngular().openNodHomePage(contentSet);
            loginPage().logIn();
            homePageAngular().clickAdminOpinions();
            boolean administrativeOpinionsPageIsOpened = administrativeOpinionsPageAngular().isPageOpened();
            Assertions.assertTrue(administrativeOpinionsPageIsOpened, "NOD Administrative Opinions page didn't open");


            // Insert a new opinion
            administrativeOpinionsPageAngular().clickInsertOpinion();
            // 5. VERIFY: the Insert Opinion page is opened
            insertOpinionPopupAngular().isPageOpened();
            //6. Select Type: Attorney General
            insertOpinionPopupAngular().selectType(opinionType);
            // 7. VERIFY: Content set is expected
            String actualContentSet = insertOpinionPopupAngular().getContentSet();
            boolean isContentSetExpected = actualContentSet.equals(contentSet.getName());
            // 8. Type Opinion Number: 02-306
            insertOpinionPopupAngular().typeOpinionNumber(opinionNumber);
            // 9. Type Date of Opinion:  today's date
            String currentDate = DateAndTimeUtils.getCurrentDateMMddyyyyNoDelimeters();
            insertOpinionPopupAngular().clearDateOfOpinion();
            insertOpinionPopupAngular().typeDateOfOpinion(currentDate);
            // 10. VERIFY: Editor's name is correct
            String expectedUsername = user().getNodEditorUsername();
            String actualUsername = insertOpinionPopupAngular().getEditorName();
            boolean isEditorNameExpeted = expectedUsername.equals(actualUsername);
            // 11. Type Text:  "Lorem ipsum dolor sit amet"
            insertOpinionPopupAngular().typeText(text);
            // 12. Type Opinion Citation:  Atty Gen. OP. 02-306
            insertOpinionPopupAngular().typeCitation(citation);
            // 13. Type WL Number: "2021 WL 123456"
            insertOpinionPopupAngular().typeWestlawNumber(wlNumber);
            // 14. Click on Insert button
            insertOpinionPopupAngular().clickInsert();

            // VERIFY: Opinion appeared in table and expected notification appears
            boolean isNotificationShown = notificationPopupAngular().waitForNotification();
            String actualNotificationText = notificationPopupAngular().getNotificationText();
            boolean isNotificationTextExpected = actualNotificationText.equals(expectedNotification);

            // 16. Open opinion you've just inserted and verify all of the data is correct
            administrativeOpinionsPageAngular().clickOpinionNumber(opinionNumber);
            boolean isEditOpinionPageOpened = editAdminOpinionsPageAngular().isPageOpened();
            String typeOnEditPage = editAdminOpinionsPageAngular().getType();
            boolean isTypeExpected = typeOnEditPage.equals(opinionType);
            String contentSetOnEditPage = editAdminOpinionsPageAngular().getContentSet();
            boolean isContentSetExpectedOnEditPage = contentSetOnEditPage.equals(contentSet.getName());
            String opinionNumberOnEditPage = editAdminOpinionsPageAngular().getOpinionNumber();
            boolean isOpinionNumberExpected = opinionNumberOnEditPage.equals(opinionNumber);
            String usernameOnEditPage = editAdminOpinionsPageAngular().getEditorName();
            boolean isUsernameExpectedOnEditPage = usernameOnEditPage.equals(user().getNodEditorUsername());
            String textOnEditPage = editAdminOpinionsPageAngular().getText();
            boolean isTextExpected = textOnEditPage.equals(text);
            String citationOnEditPage = editAdminOpinionsPageAngular().getCitation();
            boolean isCitationExpected = citationOnEditPage.equals(citation);
            String wlNumberOnEditPage = editAdminOpinionsPageAngular().getWestlawNumber();
            boolean isWlNumberExpected = wlNumberOnEditPage.equals(wlNumber);
            editAdminOpinionsPageAngular().clickCancel();

            Assertions.assertAll
            (
                () -> Assertions.assertTrue(isContentSetExpected, String.format("Content set is not expected. \nExpected: '%s', \nActual: '%s'", contentSet.getName(), actualContentSet)),
                () -> Assertions.assertTrue(isEditorNameExpeted, String.format("Editor name not expected. \nExpected: '%s', \nActual: '%s'", expectedUsername, actualUsername)),
                () -> Assertions.assertTrue(isNotificationShown, "Notification didn't appear"),
                () -> Assertions.assertTrue(isNotificationTextExpected, String.format("Notification text is unexpected. \nExpected: '%s', \nActual: '%s'", expectedNotification, actualNotificationText)),
                () -> Assertions.assertTrue(isEditOpinionPageOpened, "Edit Opinion Page didn't open"),
                () -> Assertions.assertTrue(isTypeExpected, String.format("Type on edit opinion page is not expected. \nExpected: '%s', \nActual: '%s'", opinionType, typeOnEditPage)),
                () -> Assertions.assertTrue(isContentSetExpectedOnEditPage, String.format("Content Set on edit opinion page is not expected. \nExpected: '%s', \nActual: '%s'", contentSet.getName(), contentSetOnEditPage)),
                () -> Assertions.assertTrue(isOpinionNumberExpected, String.format("Opinion number on edit page is not expected. \nExpected: '%s', \nActual: '%s'", opinionNumber, opinionNumberOnEditPage)),
                () -> Assertions.assertTrue(isUsernameExpectedOnEditPage, String.format("Opinion number on edit page is not expected. \nExpected: '%s', \nActual: '%s'", user().getNodEditorUsername(), usernameOnEditPage)),
                () -> Assertions.assertTrue(isTextExpected, String.format("Text on edit page is not expected. \nExpected: '%s', \nActual: '%s'", text, textOnEditPage)),
                () -> Assertions.assertTrue(isCitationExpected, String.format("Citation on edit page is not expected. \nExpected: '%s', \nActual: '%s'", citation, citationOnEditPage)),
                () -> Assertions.assertTrue(isWlNumberExpected, String.format("Westlaw number on edit page is not expected. \nExpected: '%s', \nActual: '%s'", wlNumber, wlNumberOnEditPage))
            );
        }
        finally
        {
            connection = BaseDatabaseUtils.connectToDatabaseUAT();
            String opinionUuid = NodAngularDatabaseUtils.getOpinionUuid(opinionNumber, contentSetCode, connection);
            NodAngularDatabaseUtils.deleteAdministrativeOpinion(opinionUuid, contentSetCode, connection);
        }
    }

    /**
     * HALCYONST-10747
     * 1. Open Administrative Opinions page
     * 2. VERIFY: the Administrative Opinions page is opened
     * 3. Insert new opinion
     * 4. Click on the blue hyper link under the Opinion Number Column for newly created opinion
     * 5. VERIFY: the Edit Opinion page is opened
     * 6. Change Type of Opinion to
     * 7. Change Opinion Number
     * 8. Change the Date of Opinion to today's date: 9/1/2020
     * 9. In the text field, add in "TEST" after the very last sentence
     * 10. Change Opinion citation
     * 11. Click the Update button
     * 12. VERIFY: the Administrative Opinions page is opened
     * 13. VERIFY: Expected notification shown
     * 14. Click one of the same hyper link from step 5
     * 15. VERIFY: the Edit Opinion page is opened
     * 16. VERIFY: all changes from steps 7-11 are saved
     */
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void updateAdministrativeOpinionTest()
    {
        String opinionTypeAfterEdit = "Insurance";
        String opinionDateAfterEdit = "09/01/2020";
        String opinionNumberAfterEdit = "02-320";
        String textAfterEdit = "Lorem ipsum dolor sit amet TEST";
        String citationAfterEdit = "Atty Gen. OP. 02-320";
        String expectedNotificationAfterEdit = "Opinion updated";

        //Mock up admin opinion
        connection = BaseDatabaseUtils.connectToDatabaseUAT();
        String opinionUuid = CommonDataMocking.generateUUID();
        String opinionCategoryBeforeEdit = "Attorney General";
        String textBeforeEdit = "Lorem ipsum dolor sit amet";
        String opinionNumberBeforeEdit = DateAndTimeUtils.getCurrentDateAndTimeyyyyMMddHHmmss();
        NodAngularDatabaseUtils.insertAdministrativeOpinion(opinionUuid, opinionCategoryBeforeEdit, textBeforeEdit, opinionNumberBeforeEdit, contentSetCode, connection);

        try
        {
            // Open NodClassifyUI home page and go to Admin Opinions tab
            homePageAngular().openNodHomePage(contentSet);
            loginPage().logIn();
            homePageAngular().clickAdminOpinions();
            boolean administrativeOpinionsPageIsOpened = administrativeOpinionsPageAngular().isPageOpened();
            Assertions.assertTrue(administrativeOpinionsPageIsOpened, "NOD Administrative Opinions page didn't open");

            // Click on the blue hyper link under the Opinion Number Column and verify edit page opens
            administrativeOpinionsPageAngular().clickOpinionNumber(opinionNumberBeforeEdit);
            boolean isEditOpinionPageOpened = editAdminOpinionsPageAngular().isPageOpened();
            Assertions.assertTrue(isEditOpinionPageOpened, "NOD Edit Opinion page didn't open");

            // Edit opinion data and select to update
            editAdminOpinionsPageAngular().selectType(opinionTypeAfterEdit);
            editAdminOpinionsPageAngular().clearOpinionNumber();
            editAdminOpinionsPageAngular().typeOpinionNumber(opinionNumberAfterEdit);
            editAdminOpinionsPageAngular().clearDateOfOpinion();
            editAdminOpinionsPageAngular().typeDateOfOpinion(opinionDateAfterEdit.replace("/", ""));
            editAdminOpinionsPageAngular().clearText();
            editAdminOpinionsPageAngular().typeText(textAfterEdit);
            editAdminOpinionsPageAngular().clearCitation();
            editAdminOpinionsPageAngular().typeCitation(citationAfterEdit);
            editAdminOpinionsPageAngular().clickUpdate();

            // VERIFY: the Administrative Opinions page is opened and the expected notification is shown
            boolean isAdminOpinionsPageOpened = administrativeOpinionsPageAngular().waitPageOpened();
            Assertions.assertTrue(isAdminOpinionsPageOpened,"NOD Administrative Opinions page didn't open after editing");
            boolean isNotificationShownAfterEditing = notificationPopupAngular().waitForNotification();
            String actualNotificationTextAfterEditing = notificationPopupAngular().getNotificationText();
            boolean isNotificationTextExpectedAfterEditing = actualNotificationTextAfterEditing.equals(expectedNotificationAfterEdit);

            // Click on the blue hyper link again under the Opinion Number Column and verify edit page opens
            administrativeOpinionsPageAngular().clickOpinionNumber(opinionNumberAfterEdit);
            boolean isEditOpinionPageOpenedAfterEditing = editAdminOpinionsPageAngular().isPageOpened();
            Assertions.assertTrue(isEditOpinionPageOpenedAfterEditing,"NOD Edit Opinion page didn't open after editing");

            // 16. VERIFY: all changes from steps 7-11 are saved
            String typeOnEditPage = editAdminOpinionsPageAngular().getType();
            boolean isTypeExpected = typeOnEditPage.equals(opinionTypeAfterEdit);
            String opinionDateOnEditPage = editAdminOpinionsPageAngular().getDateOfOpinion();
            boolean isDateExpected = opinionDateOnEditPage.equals(opinionDateAfterEdit);
            String opinionNumberOnEditPage = editAdminOpinionsPageAngular().getOpinionNumber();
            boolean isOpinionNumberExpected = opinionNumberOnEditPage.equals(opinionNumberAfterEdit);
            String textOnEditPage = editAdminOpinionsPageAngular().getText();
            boolean isTextExpected = textOnEditPage.equals(textAfterEdit);
            String citationOnEditPage = editAdminOpinionsPageAngular().getCitation();
            boolean isCitationExpected = citationOnEditPage.equals(citationAfterEdit);
            editAdminOpinionsPageAngular().clickCancel();

            Assertions.assertAll
            (
            () -> Assertions.assertTrue(isNotificationShownAfterEditing,"Notification didn't appear after editing"),
            () -> Assertions.assertTrue(isNotificationTextExpectedAfterEditing, String.format("Notification text is unexpected. \nExpected: '%s', \nActual: '%s'",expectedNotificationAfterEdit, actualNotificationTextAfterEditing)),
            () -> Assertions.assertTrue(isTypeExpected, String.format("Type on edit opinion page is not expected. \nExpected: '%s', \nActual: '%s'",opinionTypeAfterEdit, typeOnEditPage)),
            () -> Assertions.assertTrue(isDateExpected, String.format("Type on edit opinion page is not expected. \nExpected: '%s', \nActual: '%s'",opinionDateAfterEdit, opinionDateOnEditPage)),
            () -> Assertions.assertTrue(isOpinionNumberExpected, String.format("Opinion number on edit page is not expected. \nExpected: '%s', \nActual: '%s'",opinionNumberAfterEdit, opinionNumberOnEditPage)),
            () -> Assertions.assertTrue(isTextExpected, String.format("Text on edit page is not expected. \nExpected: '%s', \nActual: '%s'", textAfterEdit, textOnEditPage)),
            () -> Assertions.assertTrue(isCitationExpected, String.format("Citation on edit page is not expected. \nExpected: '%s', \nActual: '%s'",citationAfterEdit, citationOnEditPage))
            );
        }
        finally
        {
            NodAngularDatabaseUtils.deleteAdministrativeOpinion(opinionUuid, contentSetCode, connection);
        }
    }

    /**
     * HALCYONST-10744
     * 1. Log onto USCA (development) content set as legal user
     * 2. Go to NOD -> Administrative Opinion
     * 4. VERIFY: the Administrative Opinions page is opened
     * 5. Click one of the blue hyper links under the Opinion Number Column
     * 6. VERIFY: the Edit Opinion page is opened
     * 7. Locate a Section level document in hierarchy, and verify that the right context menu options come up:
     * Insert Blueline, Update Metadata, View Content, View Content Raw Xml, Edit Content,
     * Edit Content Raw Xml, Find in Hierarchy
     *
     * 8. Locate the Blueline Analysis document, and verify that the right context menu options come up:
     * All Blueline Analysis View, Blueline Analysis View, Insert Blueline, Update Metadata, View Content,
     * View Content Raw Xml, Edit Content, Edit Content Raw Xml, Find in Hierarchy
     *
     * 9. Locate the Blueline document, and verify that the right context menu options come up:
     * Blueline Analysis View, Insert Blueline, Edit Blueline, Delete Blueline, Update Metadata, View Content,
     * View Content Raw Xml, Edit Content, Edit Content Raw Xml, Find in Hierarchy
     */

    private static Stream<Arguments> provideDataForContentMenuTest()
    {
        return Stream.of(
                Arguments.of("123.3", "= 123.3 Definitions", Arrays.asList("Insert Blueline", "Update Metadata",
                        "View Content", "View Content Raw Xml", "Edit Content", "Edit Content Raw Xml", "Find In Hierarchy")),
                Arguments.of("123.3 3", "BL 3 Spirits", Arrays.asList("Blueline Analysis View", "Insert Blueline",
                        "Edit Blueline", "Delete Blueline", "Update Metadata", "View Content", "View Content Raw Xml",
                        "Edit Content", "Edit Content Raw Xml", "Find In Hierarchy")),
                Arguments.of("123.3 1", "BL ANALYSI ", Arrays.asList(
                        "All Blueline Analysis View", "Blueline Analysis View", "Insert Blueline", "Update Metadata",
                        "View Content", "View Content Raw Xml", "Edit Content", "Edit Content Raw Xml", "Find In Hierarchy"))
        );
    }
    @ParameterizedTest(name = "Test: {displayName}; Index: {index}; Arguments: {arguments}")
    @MethodSource("provideDataForContentMenuTest")
    @EDGE
    @LEGAL
    @LOG
    public void administrativeOpinionContextMenuTest(String searchString, String nodeText, List<String> expectedContexMenuOptions)
    {
        String expectedRootElementsText = "@ IOWA CODE ANNOTATED IOWA CODE ANNOTATED";

        //Mock up admin opinion
        connection = BaseDatabaseUtils.connectToDatabaseUAT();
        String opinionUuid = CommonDataMocking.generateUUID();
        String opinionCategory = "Attorney General";
        String text = "Lorem ipsum dolor sit amet";
        String opinionNumber = DateAndTimeUtils.getCurrentDateAndTimeyyyyMMddHHmmss();
        NodAngularDatabaseUtils.insertAdministrativeOpinion(opinionUuid, opinionCategory, text, opinionNumber, contentSetCode, connection);

        try
        {
            // Open NodClassifyUI home page and go to Admin Opinions tab
            homePageAngular().openNodHomePage(contentSet);
            loginPage().logIn();
            homePageAngular().clickAdminOpinions();
            boolean administrativeOpinionsPageIsOpened = administrativeOpinionsPageAngular().isPageOpened();
            Assertions.assertTrue(administrativeOpinionsPageIsOpened, "NOD Administrative Opinions page didn't open");

            // Click on the blue hyper link under the Opinion Number Column and verify edit page opens
            administrativeOpinionsPageAngular().clickOpinionNumber(opinionNumber);
            boolean isEditOpinionPageOpened = editAdminOpinionsPageAngular().isPageOpened();
            Assertions.assertTrue(isEditOpinionPageOpened, "NOD Edit Opinion page didn't open");

            // 6. VERIFY: On the left side of the page appears a hierarchy tree for the content set that you signed into
            boolean isNavigationTreeVisible = hierarchyTreeFragmentAngular().isHierarchyTreeDisplayed();
            boolean isContentSetOpened = editAdminOpinionsPageAngular().isElementDisplayed(String.format(HierarchyTreeFragmentElementsAngular.SPAN_WITH_TEXT, expectedRootElementsText));
            // 7. Select a Section/Grade/etc level document
            hierarchyTreeFragmentAngular().quickFind(searchString);
            hierarchyTreeFragmentAngular().scrollToSeeParentLevelNode(nodeText);
            // 8. Right click on this document
            hierarchyTreeFragmentAngular().openContextMenuForNodeWithText(nodeText);
            // 9. VERIFY: context menu contains expected options
            List<String> contextMenuOptionsTexts = headnotesContextMenuAngular().getContextMenuOptionsTexts();
            boolean contextMenuOptionsAreAsExpected = contextMenuOptionsTexts.equals(expectedContexMenuOptions);

            Assertions.assertAll
            (
                () -> Assertions.assertTrue(isNavigationTreeVisible, "Navigation tree is not shown"),
                () -> Assertions.assertTrue(isContentSetOpened, String.format("Root element of the content set is not found in navigation tree. Root element: \"%s\"",expectedRootElementsText)),
                () -> Assertions.assertTrue(contextMenuOptionsAreAsExpected, String.format("Context menu options are not expected. \nExpected: %s \n Actual: %s",expectedContexMenuOptions, contextMenuOptionsTexts))
            );
        }
        finally
        {
            NodAngularDatabaseUtils.deleteAdministrativeOpinion(opinionUuid, contentSetCode, connection);
        }
    }

    /**
     * HALCYONST-10750
     * 1. Open NodClassifyUI home page
     * 2. Select Administrative Opinions Option
     * 3. VERIFY: the Administrative Opinions page is opened
     * 4. Click Insert Opinion
     * 5. VERIFY: the Insert Opinion page is opened
     * 6. Select Type: Attorney General
     * 7. VERIFY: Content set is expected
     * 8. Type Opinion Number: 02-306
     * 9. Type Date of Opinion:  today's date
     * 10. VERIFY: Editor's name is correct
     * 11. Type Text:  "Lorem ipsum dolor sit amet"
     * 12. Type Opinion Citation:  Atty Gen. OP. 02-306
     * 13. Type WL Number: "2021 WL 123456"
     * 14. Click on Save and Edit\Classify button
     * 15. VERIFY: All data is saved correctly
     */
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void createNewAdministrativeOpinionAndEditTest()
    {
        ContentSets contentSet = ContentSets.IOWA_DEVELOPMENT;
        String opinionType = "Attorney General";
        String opinionNumber = "02-311";
        String text = "Lorem ipsum dolor sit amet";
        String citation = "Atty Gen. OP. 02-306";
        String wlNumber = "2021 WL 123456";
        String expectedNotification = "Opinion inserted";

        try
        {
            // Open NodClassifyUI home page and go to Admin Opinions tab
            homePageAngular().openNodHomePage(contentSet);
            loginPage().logIn();
            homePageAngular().clickAdminOpinions();
            boolean administrativeOpinionsPageIsOpened = administrativeOpinionsPageAngular().isPageOpened();
            Assertions.assertTrue(administrativeOpinionsPageIsOpened, "NOD Administrative Opinions page didn't open");

            // 4. Click Insert Opinion
            administrativeOpinionsPageAngular().clickInsertOpinion();
            // 5. VERIFY: the Insert Opinion page is opened
            insertOpinionPopupAngular().isPageOpened();
            // 6. Select Type: Attorney General
            insertOpinionPopupAngular().selectType(opinionType);
            // 7. VERIFY: Content set is expected
            String actualContentSet = insertOpinionPopupAngular().getContentSet();
            boolean isContentSetExpected = actualContentSet.equals(contentSet.getName());
            // 8. Type Opinion Number: 02-306
            insertOpinionPopupAngular().typeOpinionNumber(opinionNumber);
            // 9. Type Date of Opinion:  today's date
            String currentDate = DateAndTimeUtils.getCurrentDateMMddyyyyNoDelimeters();
            insertOpinionPopupAngular().clearDateOfOpinion();
            insertOpinionPopupAngular().typeDateOfOpinion(currentDate);
            // 10. VERIFY: Editor's name is correct
            String expectedUsername = user().getNodEditorUsername();
            String actualUsername = insertOpinionPopupAngular().getEditorName();
            boolean isEditorNameExpeted = expectedUsername.equals(actualUsername);
            // 11. Type Text:  "Lorem ipsum dolor sit amet"
            insertOpinionPopupAngular().typeText(text);
            // 12. Type Opinion Citation:  Atty Gen. OP. 02-306
            insertOpinionPopupAngular().typeCitation(citation);
            // 13. Type WL Number: "2021 WL 123456"
            insertOpinionPopupAngular().typeWestlawNumber(wlNumber);
            // 14. Click on Insert button
            insertOpinionPopupAngular().clickSaveAndEditClassify();
            //Verify alert appears
            boolean isNotificationShown = notificationPopupAngular().waitForNotification();
            String actualNotificationText = notificationPopupAngular().getNotificationText();
            boolean isNotificationTextExpected = actualNotificationText.equals(expectedNotification);

            // 16. VERIFY: All data is saved correctly
            boolean isEditOpinionPageOpened = editAdminOpinionsPageAngular().isPageOpened();
            String typeOnEditPage = editAdminOpinionsPageAngular().getType();
            boolean isTypeExpected = typeOnEditPage.equals(opinionType);
            String contentSetOnEditPage = editAdminOpinionsPageAngular().getContentSet();
            boolean isContentSetExpectedOnEditPage = contentSetOnEditPage.equals(contentSet.getName());
            String opinionNumberOnEditPage = editAdminOpinionsPageAngular().getOpinionNumber();
            boolean isOpinionNumberExpected = opinionNumberOnEditPage.equals(opinionNumber);
            String usernameOnEditPage = editAdminOpinionsPageAngular().getEditorName();
            boolean isUsernameExpectedOnEditPage = usernameOnEditPage.equals(user().getNodEditorUsername());
            String textOnEditPage = editAdminOpinionsPageAngular().getText();
            boolean isTextExpected = textOnEditPage.equals(text);
            String citationOnEditPage = editAdminOpinionsPageAngular().getCitation();
            boolean isCitationExpected = citationOnEditPage.equals(citation);
            String wlNumberOnEditPage = editAdminOpinionsPageAngular().getWestlawNumber();
            boolean isWlNumberExpected = wlNumberOnEditPage.equals(wlNumber);
            editAdminOpinionsPageAngular().clickCancel();

            Assertions.assertAll
            (
            () -> Assertions.assertTrue(isContentSetExpected, String.format("Content set is not expected. \nExpected: '%s', \nActual: '%s'", contentSet.getName(), actualContentSet)),
            () -> Assertions.assertTrue(isEditorNameExpeted, String.format("Editor name not expected. \nExpected: '%s', \nActual: '%s'", expectedUsername, actualUsername)),
            () -> Assertions.assertTrue(isNotificationShown, "Notification didn't appear"),
            () -> Assertions.assertTrue(isNotificationTextExpected, String.format("Notification text is unexpected. \nExpected: '%s', \nActual: '%s'", expectedNotification, actualNotificationText)),
            () -> Assertions.assertTrue(isEditOpinionPageOpened, "Edit Opinion Page didn't open"),
            () -> Assertions.assertTrue(isTypeExpected, String.format("Type on edit opinion page is not expected. \nExpected: '%s', \nActual: '%s'", opinionType, typeOnEditPage)),
            () -> Assertions.assertTrue(isContentSetExpectedOnEditPage, String.format("Content Set on edit opinion page is not expected. \nExpected: '%s', \nActual: '%s'", contentSet.getName(), contentSetOnEditPage)),
            () -> Assertions.assertTrue(isOpinionNumberExpected, String.format("Opinion number on edit page is not expected. \nExpected: '%s', \nActual: '%s'", opinionNumber, opinionNumberOnEditPage)),
            () -> Assertions.assertTrue(isUsernameExpectedOnEditPage, String.format("Opinion number on edit page is not expected. \nExpected: '%s', \nActual: '%s'", user().getNodEditorUsername(), usernameOnEditPage)),
            () -> Assertions.assertTrue(isTextExpected, String.format("Text on edit page is not expected. \nExpected: '%s', \nActual: '%s'", text, textOnEditPage)),
            () -> Assertions.assertTrue(isCitationExpected, String.format("Citation on edit page is not expected. \nExpected: '%s', \nActual: '%s'", citation, citationOnEditPage)),
            () -> Assertions.assertTrue(isWlNumberExpected, String.format("Westlaw number on edit page is not expected. \nExpected: '%s', \nActual: '%s'", wlNumber, wlNumberOnEditPage))
            );
        }
        finally
        {
            connection = BaseDatabaseUtils.connectToDatabaseUAT();
            String opinionUuid = NodAngularDatabaseUtils.getOpinionUuid(opinionNumber, contentSetCode, connection);
            NodAngularDatabaseUtils.deleteAdministrativeOpinion(opinionUuid, contentSetCode, connection);
        }
    }

    /**
     * HALCYONST-10750
     * 1. Open NodClassifyUI home page
     * 2. Select Administrative Opinions Option
     * 3. VERIFY: the Administrative Opinions page is opened
     * 4. Click Insert Opinion
     * 5. VERIFY: the Insert Opinion page is opened
     * 6. Select Type: Attorney General
     * 7. VERIFY: Content set is expected
     * 8. Type Opinion Number: 02-306
     * 9. Type Date of Opinion:  today's date
     * 10. VERIFY: Editor's name is correct
     * 11. Type Text:  "Lorem ipsum dolor sit amet"
     * 12. Type Opinion Citation:  Atty Gen. OP. 02-306
     * 13. Type WL Number: "2021 WL 123456"
     * 14. Click on "Cancel" button
     * 15. VERIFY: The opinion is not inserted
     */
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void createNewAdministrativeOpinionAndCancel()
    {
        String opinionType = "Attorney General";
        String opinionNumber = DateAndTimeUtils.getCurrentDateAndTimeyyyyMMddHHmmss();
        String text = "Lorem ipsum dolor sit amet";
        String citation = "Atty Gen. OP. 02-306";
        String wlNumber = "2021 WL 123456";

        // Open NodClassifyUI home page and go to Admin Opinions tab
        homePageAngular().openNodHomePage(contentSet);
        loginPage().logIn();
        homePageAngular().clickAdminOpinions();
        boolean administrativeOpinionsPageIsOpened = administrativeOpinionsPageAngular().isPageOpened();
        Assertions.assertTrue(administrativeOpinionsPageIsOpened, "NOD Administrative Opinions page didn't open");

        // 4. Click Insert Opinion
        administrativeOpinionsPageAngular().clickInsertOpinion();
        // 5. VERIFY: the Insert Opinion page is opened
        insertOpinionPopupAngular().isPageOpened();
        // 6. Select Type: Attorney General
        insertOpinionPopupAngular().selectType(opinionType);
        // 7. VERIFY: Content set is expected
        String actualContentSet = insertOpinionPopupAngular().getContentSet();
        boolean isContentSetExpected = actualContentSet.equals(contentSet.getName());
        // 8. Type Opinion Number: 02-306
        insertOpinionPopupAngular().typeOpinionNumber(opinionNumber);
        // 9. Type Date of Opinion:  today's date
        String currentDate = DateAndTimeUtils.getCurrentDateMMddyyyyNoDelimeters();
        insertOpinionPopupAngular().clearDateOfOpinion();
        insertOpinionPopupAngular().typeDateOfOpinion(currentDate);
        // 10. VERIFY: Editor's name is correct
        String expectedUsername = user().getNodEditorUsername();
        String actualUsername = insertOpinionPopupAngular().getEditorName();
        boolean isEditorNameExpeted = expectedUsername.equals(actualUsername);
        // 11. Type Text:  "Lorem ipsum dolor sit amet"
        insertOpinionPopupAngular().typeText(text);
        // 12. Type Opinion Citation:  Atty Gen. OP. 02-306
        insertOpinionPopupAngular().typeCitation(citation);
        // 13. Type WL Number: "2021 WL 123456"
        insertOpinionPopupAngular().typeWestlawNumber(wlNumber);
        // 14. Click on Insert button
        insertOpinionPopupAngular().clickCancel();
        boolean opinionIsNotInserted = !administrativeOpinionsPageAngular().doesOpinionWithNumberExist(opinionNumber);

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(isContentSetExpected, String.format("Content set is not expected. \nExpected: '%s', \nActual: '%s'",contentSet.getName(), actualContentSet)),
            () -> Assertions.assertTrue(isEditorNameExpeted, String.format("Editor name not expected. \nExpected: '%s', \nActual: '%s'", expectedUsername, actualUsername)),
            () -> Assertions.assertTrue(opinionIsNotInserted, String.format("Opinion '%s' exists, while it shouldn't.", wlNumber))
        );
    }

    /**
     * HALCYONST-10747
     * 1. Open Administrative Opinions page
     * 4. Click on the blue hyper link under the Opinion Number Column for some opinion
     * 5. VERIFY: the Edit Opinion page is opened
     * 6. Change Type of Opinion to
     * 7. Change Opinion Number
     * 8. Change the Date of Opinion to today's date: 9/1/2020
     * 9. In the text field, add in "TEST" after the very last sentence
     * 10. Change Opinion citation
     * 11. Click the Update button
     * 12. VERIFY: the Administrative Opinions page is opened
     * 13. Click one of the same hyper link from step 5
     * 14. VERIFY: the Edit Opinion page is opened
     * 15. VERIFY: all changes from steps 7-11 are saved
     */
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void editAdministrativeOpinionAndCancel()
    {
        String opinionTypeAfterEdit = "Insurance";
        String opinionDateAfterEdit = "09/01/2020";
        String opinionNumberAfterEdit = "02-320";
        String textAfterEdit = "Lorem ipsum dolor sit amet TEST";
        String citationAfterEdit = "Atty Gen. OP. 02-320";
        String expectedNotificationAfterEdit = "Opinion updated";

        //Mock up admin opinion
        connection = BaseDatabaseUtils.connectToDatabaseUAT();
        String opinionUuid = CommonDataMocking.generateUUID();
        String opinionCategoryBeforeEdit = "Attorney General";
        String textBeforeEdit = "Lorem ipsum dolor sit amet";
        String opinionNumberBeforeEdit = DateAndTimeUtils.getCurrentDateAndTimeyyyyMMddHHmmss();
        NodAngularDatabaseUtils.insertAdministrativeOpinion(opinionUuid, opinionCategoryBeforeEdit, textBeforeEdit, opinionNumberBeforeEdit, contentSetCode, connection);

        try
        {
            // Open NodClassifyUI home page and go to Admin Opinions tab
            homePageAngular().openNodHomePage(contentSet);
            loginPage().logIn();
            homePageAngular().clickAdminOpinions();
            boolean administrativeOpinionsPageIsOpened = administrativeOpinionsPageAngular().isPageOpened();
            Assertions.assertTrue(administrativeOpinionsPageIsOpened, "NOD Administrative Opinions page didn't open");

            // Click on the blue hyper link under the Opinion Number Column and verify edit page opens
            administrativeOpinionsPageAngular().clickOpinionNumber(opinionNumberBeforeEdit);
            boolean isEditOpinionPageOpened = editAdminOpinionsPageAngular().isPageOpened();
            Assertions.assertTrue(isEditOpinionPageOpened, "NOD Edit Opinion page didn't open");

            // Edit opinion data and select to update
            editAdminOpinionsPageAngular().selectType(opinionTypeAfterEdit);
            editAdminOpinionsPageAngular().clearOpinionNumber();
            editAdminOpinionsPageAngular().typeOpinionNumber(opinionNumberAfterEdit);
            editAdminOpinionsPageAngular().clearDateOfOpinion();
            editAdminOpinionsPageAngular().typeDateOfOpinion(opinionDateAfterEdit.replace("/", ""));
            editAdminOpinionsPageAngular().clearText();
            editAdminOpinionsPageAngular().typeText(textAfterEdit);
            editAdminOpinionsPageAngular().clearCitation();
            editAdminOpinionsPageAngular().typeCitation(citationAfterEdit);
            editAdminOpinionsPageAngular().clickCancel();

            // VERIFY: the Administrative Opinions page is opened
            boolean isAdminOpinionsPageOpened = administrativeOpinionsPageAngular().waitPageOpened();
            Assertions.assertTrue(isAdminOpinionsPageOpened,"NOD Administrative Opinions page didn't open after editing");
            administrativeOpinionsPageAngular().waitForGridRefresh();

            // Click on the blue hyper link again under the Opinion Number Column and verify edit page opens
            boolean doesOldOpinionExist = administrativeOpinionsPageAngular().doesOpinionWithNumberExist(opinionNumberBeforeEdit);
            boolean newOpinionDoesntExist = !administrativeOpinionsPageAngular().doesOpinionWithNumberExist(opinionNumberAfterEdit);
            administrativeOpinionsPageAngular().clickOpinionNumber(opinionNumberBeforeEdit);
            boolean isEditOpinionPageOpenedAfterEditing = editAdminOpinionsPageAngular().isPageOpened();
            Assertions.assertTrue(isEditOpinionPageOpenedAfterEditing,"NOD Edit Opinion page didn't open after editing");

            // 15. VERIFY: all changes from steps 7-11 are not saved
            String typeOnEditPage = editAdminOpinionsPageAngular().getType();
            boolean isTypeExpected = typeOnEditPage.equals(opinionCategoryBeforeEdit);
            String opinionDateOnEditPage = editAdminOpinionsPageAngular().getDateOfOpinion();
            boolean isDateExpected = opinionDateOnEditPage.equals("");
            String opinionNumberOnEditPage = editAdminOpinionsPageAngular().getOpinionNumber();
            boolean isOpinionNumberExpected = opinionNumberOnEditPage.equals(opinionNumberBeforeEdit);
            String textOnEditPage = editAdminOpinionsPageAngular().getText();
            boolean isTextExpected = textOnEditPage.equals(textBeforeEdit);
            String citationOnEditPage = editAdminOpinionsPageAngular().getCitation();
            boolean isCitationExpected = citationOnEditPage.equals("");
            editAdminOpinionsPageAngular().clickCancel();

            Assertions.assertAll
            (
                () -> Assertions.assertTrue(doesOldOpinionExist, String.format("Opinion '%s' doesn't exist", opinionNumberBeforeEdit)),
                () -> Assertions.assertTrue(newOpinionDoesntExist, String.format("Opinion '%s' shouldn't exist", opinionDateAfterEdit)),
                () -> Assertions.assertTrue(isTypeExpected, String.format("Type on edit opinion page is not expected. \nExpected: '%s', \nActual: '%s'", opinionCategoryBeforeEdit, typeOnEditPage)),
                () -> Assertions.assertTrue(isDateExpected, String.format("Type on edit opinion page is not expected. \nExpected: '%s', \nActual: '%s'", "", opinionDateOnEditPage)),
                () -> Assertions.assertTrue(isOpinionNumberExpected, String.format("Opinion number on edit page is not expected. \nExpected: '%s', \nActual: '%s'", opinionNumberBeforeEdit, opinionNumberOnEditPage)),
                () -> Assertions.assertTrue(isTextExpected, String.format("Text on edit page is not expected. \nExpected: '%s', \nActual: '%s'", textBeforeEdit, textOnEditPage)),
                () -> Assertions.assertTrue(isCitationExpected, String.format("Citation on edit page is not expected. \nExpected: '%s', \nActual: '%s'", "", citationOnEditPage))
            );
        }
        finally
        {
            NodAngularDatabaseUtils.deleteAdministrativeOpinion(opinionUuid, contentSetCode, connection);
        }
    }

    /**
     * HALCYONST-12971
     * 1. Log onto Iowa (development) content set as legal user
     * 2. Go to NOD -> Administrative Opinion
     * 4. VERIFY: the Administrative Opinions page is opened
     * 5. Click one of the blue hyper links under the Opinion Number Column
     * 6. VERIFY: the Edit Opinion page is opened
     * 7. Select a blueline document in the hierarchy
     * 8. Click on Classify button
     * 9. VERIFY:Opinion is now classified
     * 10. Click on Remove Classification Button (agree if popup appears)
     * 11. VERIFY: classification is removed now.
     */
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void addRemoveClassifyTest()
    {
        String searchString1 = "T. XCIX QED TESTING";
        String searchString2 = "BL 1 Test";
        String expectedBlueLineInformation = "1 Test";
        String firstAlertText = "Do you want to Un-Classify?";
        String secondAlertText = "The classification has been removed from the database. This classification must be manually removed from the text. Click 'OK' to launch the editor and open the document.";

        //Mock up BL node
        connection = BaseDatabaseUtils.connectToDatabaseUAT();
        HierarchyDatapodConfiguration.getConfig().setBluelineCount(1);
        datapodObject = TargetDataMockingNew.Iowa.Small.insert();
        String nodeUuid = datapodObject.getSections().get(0).getNodeUUID();
        String nodeValue = HierarchyDatabaseUtils.getNodeValueByNodeUUID(nodeUuid, contentSetCode, connection);
        String expectedStatute = nodeValue + "; BL 1";

        //Mock up admin opinion
        String opinionUuid = CommonDataMocking.generateUUID();
        String opinionCategory = "Attorney General";
        String text = "Lorem ipsum dolor sit amet";
        String opinionNumber = DateAndTimeUtils.getCurrentDateAndTimeyyyyMMddHHmmss();
        NodAngularDatabaseUtils.insertAdministrativeOpinion(opinionUuid, opinionCategory, text, opinionNumber, contentSetCode, connection);

        try
        {
            // Open NodClassifyUI home page and go to Admin Opinions tab
            homePageAngular().openNodHomePage(contentSet);
            loginPage().logIn();
            homePageAngular().clickAdminOpinions();
            boolean administrativeOpinionsPageIsOpened = administrativeOpinionsPageAngular().isPageOpened();
            Assertions.assertTrue(administrativeOpinionsPageIsOpened, "NOD Administrative Opinions page didn't open");

            // Click on the blue hyper link under the Opinion Number Column and verify edit page opens
            administrativeOpinionsPageAngular().clickOpinionNumber(opinionNumber);
            boolean isEditOpinionPageOpened = editAdminOpinionsPageAngular().isPageOpened();
            Assertions.assertTrue(isEditOpinionPageOpened, "NOD Edit Opinion page didn't open");

            hierarchyTreeFragmentAngular().hierarchyFind(searchString1);
            hierarchyTreeFragmentAngular().waitForElementScrolledTo(searchString1);
            hierarchyTreeFragmentAngular().expandNodeWithText(searchString1);
            DateAndTimeUtils.takeNap(DateAndTimeUtils.TEN_SECONDS);
            hierarchyTreeFragmentAngular().clearHierarchyFindInput();
            hierarchyTreeFragmentAngular().hierarchyFind(searchString2);
            hierarchyTreeFragmentAngular().waitForElementScrolledTo(searchString2);

            editAdminPageAngular().clickClassify();
            Assertions.assertTrue(editAdminPageAngular().doesTableWithClassificationInformationExist());
            String actualStatute = editAdminPageAngular().getStatuteShortCiteByRow(1);
            boolean statutesAreSame = actualStatute.contains(expectedStatute);
            String actualBlueLineInformation = editAdminPageAngular().getBlueLineInformationByRow(1);

            editAdminPageAngular().clickRemoveClassificationByRow(1);
            editAdminPageAngular().checkAlertTextMatchesGivenText(firstAlertText);
            editAdminPageAngular().checkAlertTextMatchesGivenTextAndDismiss(secondAlertText);
            administrativeOpinionsPageAngular().switchToAdministrativeOpinionsPage();
            boolean doesTableWithClassificationInformationExist = editAdminPageAngular().doesTableWithClassificationInformationExist();

            Assertions.assertAll
            (
                () -> Assertions.assertTrue(statutesAreSame, "The Statute Cite information is from another BlueLine"),
                () -> Assertions.assertEquals(expectedBlueLineInformation, actualBlueLineInformation, "The Bluelinr information is from another BlueLine"),
                () -> Assertions.assertFalse(doesTableWithClassificationInformationExist, "There is another classification on Administrative opinion")
            );
        }
        finally
        {
            NodAngularDatabaseUtils.deleteAdministrativeOpinion(opinionUuid, contentSetCode, connection);
            datapodObject.delete();
        }
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
