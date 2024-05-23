package com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.target.set3;

import com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.editor.EditorTextContextMenuElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.popups.InsertTargetCiteReferencePageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.popups.InsertSourceCiteReferencePageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.popups.PendingRenditionNavigatePageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.popups.TargetLocatorPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.popups.DocumentPreviewPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.popups.TableEditorPageElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets;
import org.apache.logging.log4j.util.Strings;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.popups.InsertSourceCiteReferencePageElements.ReferenceType.CITE_REFERENCE;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.popups.InsertTargetCiteReferencePageElements.ReferenceType.EXTERNAL_URL;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.popups.InsertTargetCiteReferencePageElements.ReferenceType.EMAIL;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.popups.InsertTargetCiteReferencePageElements.ReferenceType.NON_MIGRATED;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.popups.InsertTargetCiteReferencePageElements.ReferenceType.TARGET;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.popups.InsertTargetCiteReferencePageElements.ReferenceType.GLOSSARY;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.popups.TableEditorPageElements.MarkupDropdownOptions.OTHER_LINKS;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.editor.popups.TableEditorPageElements.MarkupDropdownOptions.SOURCE_LINKS;
import static com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import static com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.RISK;

public class TableEditorMarkupTests extends TestService
{
    private static final String CROWN_DEPENDENCIES_UUID = "IA79E62A329A011E6A46DB8CA3AB007EB";
    private static final String FCA_HANDBOOK_UUID = "I21350929FC9511E7A9C880000BA47767";
    private static final String TEXT_TO_ENTER = "Autotest %s";
    private static final String TABLE_TITLE = "Embedded HTML";
    private static final String RISK_FORMATTED_TABLE_ASSERTION_MESSAGE = "Risk formatted table doesn't exist";
    private static final String TABLE_EDITOR_ASSERTION_MESSAGE = "Table Editor doesn't open";
    private static final String INSERT_TARGET_CITE_REFERENCE_ASSERTION_MESSAGE = "Insert Target Cite Reference window doesn't open";
    private static final String ADDED_TEXT_LINKING_ASSERTION_MESSAGE = "Added text doesn't linked";
    private static final String EXPECTED_TEXT_ASSERTION_MESSAGE = "Expected text doesn't present in editor";
    private static final String ERROR_MESSAGE_ASSERTION_MESSAGE = "Error message doesn't appear";
    private static final String TARGET_LOCATE_WINDOW_OPEN_ASSERTION_MESSAGE = "Target Locate window doesn't open";
    private static final String TARGET_LOCATE_WINDOW_CLOSE_ASSERTION_MESSAGE = "Target Locate window doesn't close";
    private static final String TARGET_FIELD_ASSERTION_MESSAGE = "Target field doesn't contain expected value";
    private static final String NORMALIZED_CITE_ATTRIBUTE = "w-normalized-cite";
    private static final String DOC_FAMILY_UUID_ATTRIBUTE = "w-docfamily-uuid";
    private static final String PINPOINT_PAGE_ATTRIBUTE = "w-pinpoint-page";

    @Test
    @IE_EDGE_MODE
    @RISK
    @LOG
    public void externalCiteReferenceLinkTargetRiskTest()
    {
        String textToEnter = String.format(TEXT_TO_ENTER, System.currentTimeMillis());
        String textToVerify = "<entry><cite.query w-ref-type=\"E1\" w-normalized-cite=\"%s\" manual-edit=\"true\">%s</cite.query></entry>";
        String link = "www.google.com";

        homePage().goToHomePage();
        loginPage().logIn();
        hierarchyNavigatePage().goToHierarchyPage(ContentSets.CROWN_DEPENDENCIES.getCode());
        hierarchySearchPage().searchNodeUuid(CROWN_DEPENDENCIES_UUID);
        siblingMetadataPage().selectedSiblingMetadataEditContent();
        editorPage().switchToEditorTextFrame();

        editorTextPage().click(EditorTextPageElements.BODY_TAG + EditorTextPageElements.PARA_SPAN);
        editorTextPage().addTableUsingAltT();

        String commonXpath = EditorTextPageElements.BODY_TAG + EditorTextPageElements.EMBEDDED_HTML + "[1]/%s";

        boolean doesRiskFormattedTableInsert = editorTextPage().getElementsText(String.format(commonXpath, "span")).equals(TABLE_TITLE);

        editorTextPage().click(String.format(commonXpath, "span"));
        editorTextPage().editTableViaShiftAltT();

        editorTextPage().switchToWindow(TableEditorPageElements.PAGE_TITLE);
        tableEditorPage().enterTheInnerFrame();

        boolean doesTableEditorOpen = driver().getTitle().equals(TableEditorPageElements.PAGE_TITLE);

        tableEditorPage().clearAndEnterTextToTableInputFields(textToEnter);
        editorTextPage().highlightHelperUsingRobot(textToEnter);

        tableEditorPage().openMarkupDropdown();
        tableEditorPage().openMarkupDropdownOption(OTHER_LINKS);

        tableEditorPage().switchToWindow(InsertTargetCiteReferencePageElements.PAGE_TITLE);
        insertTargetCiteReferencePage().enterTheInnerFrame();

        boolean doesInsertTargetCiteReferenceWindowOpen = driver().getTitle().equals(InsertTargetCiteReferencePageElements.PAGE_TITLE);

        insertTargetCiteReferencePage().selectReferenceType(EXTERNAL_URL);
        insertTargetCiteReferencePage().enterUrl(link);
        insertTargetCiteReferencePage().clickSave();

        insertTargetCiteReferencePage().switchToWindow(TableEditorPageElements.PAGE_TITLE);
        tableEditorPage().enterTheInnerFrame();

        boolean isAddedTextLinked = tableEditorPage().isAddedTextLinked(NORMALIZED_CITE_ATTRIBUTE, link);

        tableEditorPage().clickSave();
        editorPage().switchToEditorTextFrame();

        boolean doesExpectedTextPresentInEditor = editorTextPage().getElementsText(
                String.format(commonXpath, "html.text")).contains(String.format(textToVerify, link, textToEnter));

        editorPage().closeDocumentAndDiscardChanges();

        Assertions.assertAll(
                () -> Assertions.assertTrue(doesRiskFormattedTableInsert, RISK_FORMATTED_TABLE_ASSERTION_MESSAGE),
                () -> Assertions.assertTrue(doesTableEditorOpen, TABLE_EDITOR_ASSERTION_MESSAGE),
                () -> Assertions.assertTrue(doesInsertTargetCiteReferenceWindowOpen, INSERT_TARGET_CITE_REFERENCE_ASSERTION_MESSAGE),
                () -> Assertions.assertTrue(isAddedTextLinked, ADDED_TEXT_LINKING_ASSERTION_MESSAGE),
                () -> Assertions.assertTrue(doesExpectedTextPresentInEditor, EXPECTED_TEXT_ASSERTION_MESSAGE)
        );
    }

    @Test
    @IE_EDGE_MODE
    @RISK
    @LOG
    public void nonMigratedCiteReferenceLinkTargetRiskTest()
    {
        String textToEnter = String.format(TEXT_TO_ENTER, System.currentTimeMillis());
        String textToVerify = "<entry><cite.query w-ref-type=\"GO\" w-docfamily-uuid=\"%s\" manual-edit=\"true\">%s</cite.query></entry>";
        String docFamilyId = "I48BC23E1E7C211E68B28180373BE7E39";
        String invalidDocFamilyId = docFamilyId.substring(0, 13);

        homePage().goToHomePage();
        loginPage().logIn();
        hierarchyNavigatePage().goToHierarchyPage(ContentSets.CROWN_DEPENDENCIES.getCode());
        hierarchySearchPage().searchNodeUuid(CROWN_DEPENDENCIES_UUID);
        siblingMetadataPage().selectedSiblingMetadataEditContent();
        editorPage().switchToEditorTextFrame();

        editorTextPage().click(EditorTextPageElements.BODY_TAG + EditorTextPageElements.PARA_SPAN);
        editorTextPage().addTableUsingAltT();

        String commonXpath = EditorTextPageElements.BODY_TAG + EditorTextPageElements.EMBEDDED_HTML + "[1]/%s";

        boolean doesRiskFormattedTableInsert = editorTextPage().getElementsText(String.format(commonXpath, "span")).equals(TABLE_TITLE);

        editorTextPage().click(String.format(commonXpath, "span"));
        editorTextPage().editTableViaShiftAltT();

        editorTextPage().switchToWindow(TableEditorPageElements.PAGE_TITLE);
        tableEditorPage().enterTheInnerFrame();

        boolean doesTableEditorOpen = driver().getTitle().equals(TableEditorPageElements.PAGE_TITLE);

        tableEditorPage().clearAndEnterTextToTableInputFields(textToEnter);
        editorTextPage().highlightHelperUsingRobot(textToEnter);

        tableEditorPage().openMarkupDropdown();
        tableEditorPage().openMarkupDropdownOption(OTHER_LINKS);

        tableEditorPage().switchToWindow(InsertTargetCiteReferencePageElements.PAGE_TITLE);
        insertTargetCiteReferencePage().enterTheInnerFrame();

        boolean doesInsertTargetCiteReferenceWindowOpen = driver().getTitle().equals(InsertTargetCiteReferencePageElements.PAGE_TITLE);

        insertTargetCiteReferencePage().selectReferenceType(NON_MIGRATED);
        insertTargetCiteReferencePage().enterDocFamilyId(invalidDocFamilyId);
        insertTargetCiteReferencePage().clickSave();

        boolean doesErrorMessageAppear = InsertTargetCiteReferencePageElements.errorMessage.isDisplayed() &&
                InsertTargetCiteReferencePageElements.errorMessage.getText().contains(invalidDocFamilyId);

        insertTargetCiteReferencePage().click(InsertTargetCiteReferencePageElements.DOC_FAMILY_ID_INPUT);
        editorTextPage().highlightHelperUsingRobot(invalidDocFamilyId);
        insertTargetCiteReferencePage().sendKeys(Keys.DELETE);
        insertTargetCiteReferencePage().sendKeys(docFamilyId);
        insertTargetCiteReferencePage().clickSave();

        insertTargetCiteReferencePage().switchToWindow(TableEditorPageElements.PAGE_TITLE);
        tableEditorPage().enterTheInnerFrame();

        boolean isAddedTextLinked = tableEditorPage().isAddedTextLinked(DOC_FAMILY_UUID_ATTRIBUTE, docFamilyId);

        tableEditorPage().clickSave();
        editorPage().switchToEditorTextFrame();

        boolean doesExpectedTextPresentInEditor = editorTextPage().getElementsText(
                String.format(commonXpath, "html.text")).contains(String.format(textToVerify, docFamilyId, textToEnter));

        editorPage().closeDocumentAndDiscardChanges();

        Assertions.assertAll(
                () -> Assertions.assertTrue(doesRiskFormattedTableInsert, RISK_FORMATTED_TABLE_ASSERTION_MESSAGE),
                () -> Assertions.assertTrue(doesTableEditorOpen, TABLE_EDITOR_ASSERTION_MESSAGE),
                () -> Assertions.assertTrue(doesInsertTargetCiteReferenceWindowOpen, INSERT_TARGET_CITE_REFERENCE_ASSERTION_MESSAGE),
                () -> Assertions.assertTrue(doesErrorMessageAppear, ERROR_MESSAGE_ASSERTION_MESSAGE),
                () -> Assertions.assertTrue(isAddedTextLinked, ADDED_TEXT_LINKING_ASSERTION_MESSAGE),
                () -> Assertions.assertTrue(doesExpectedTextPresentInEditor, EXPECTED_TEXT_ASSERTION_MESSAGE)
        );
    }

    @Test
    @IE_EDGE_MODE
    @RISK
    @LOG
    public void sourceCiteReferenceLinkTargetRiskTest()
    {
        String textToEnter = String.format(TEXT_TO_ENTER, System.currentTimeMillis());
        String textToVerify = "<entry><cite.query w-docfamily-uuid=\"%s\" w-ref-type=\"GO\" manual-edit=\"true\">%s</cite.query></entry>";

        homePage().goToHomePage();
        loginPage().logIn();
        hierarchyNavigatePage().goToHierarchyPage(ContentSets.CROWN_DEPENDENCIES.getCode());
        hierarchySearchPage().searchNodeUuid(CROWN_DEPENDENCIES_UUID);
        siblingMetadataPage().selectedSiblingMetadataEditContent();
        editorPage().switchToEditorTextFrame();

        editorTextPage().click(EditorTextPageElements.BODY_TAG + EditorTextPageElements.PARA_SPAN);
        editorTextPage().addTableUsingAltT();

        String commonXpath = EditorTextPageElements.BODY_TAG + EditorTextPageElements.EMBEDDED_HTML + "[1]/%s";

        boolean doesRiskFormattedTableInsert = editorTextPage().getElementsText(String.format(commonXpath, "span")).equals(TABLE_TITLE);

        editorTextPage().click(String.format(commonXpath, "span"));
        editorTextPage().editTableViaShiftAltT();

        editorTextPage().switchToWindow(TableEditorPageElements.PAGE_TITLE);
        tableEditorPage().enterTheInnerFrame();

        boolean doesTableEditorOpen = driver().getTitle().equals(TableEditorPageElements.PAGE_TITLE);

        tableEditorPage().clearAndEnterTextToTableInputFields(textToEnter);
        editorTextPage().highlightHelperUsingRobot(textToEnter);

        tableEditorPage().openMarkupDropdown();
        tableEditorPage().openMarkupDropdownOption(SOURCE_LINKS);

        tableEditorPage().switchToWindow(InsertSourceCiteReferencePageElements.PAGE_TITLE);
        insertSourceCiteReferencePage().enterTheInnerFrame();

        boolean doesInsertSourceCiteReferenceWindowOpen = driver().getTitle().equals(InsertSourceCiteReferencePageElements.PAGE_TITLE);

        insertSourceCiteReferencePage().selectReferenceType(CITE_REFERENCE);
        insertSourceCiteReferencePage().selectContentSet(ContentSets.CROWN_DEPENDENCIES);
        insertSourceCiteReferencePage().clickLocateSource();

        boolean doesPendingRenditionNavigateWindowOpen = driver().getTitle().equals(PendingRenditionNavigatePageElements.PAGE_TITLE);

        pendingRenditionNavigatePage().clickRefreshLink();
        pendingRenditionNavigatePage().selectFirstRenditionForLink();
        pendingRenditionNavigatePage().switchToWindow(InsertSourceCiteReferencePageElements.PAGE_TITLE);
        insertSourceCiteReferencePage().enterTheInnerFrame();

        boolean doesPendingRenditionNavigateWindowClose = !driver().getTitle().equals(PendingRenditionNavigatePageElements.PAGE_TITLE);

        insertSourceCiteReferencePage().clickSave();
        insertSourceCiteReferencePage().switchToWindow(TableEditorPageElements.PAGE_TITLE);
        tableEditorPage().enterTheInnerFrame();

        String docFamilyId = tableEditorPage().getAddedTextAttributeValue(DOC_FAMILY_UUID_ATTRIBUTE);
        boolean isAddedTextLinked = !docFamilyId.equals(Strings.EMPTY);

        tableEditorPage().clickSave();
        editorPage().switchToEditorTextFrame();

        boolean doesExpectedTextPresentInEditor = editorTextPage().getElementsText(
                String.format(commonXpath, "html.text")).contains(String.format(textToVerify, docFamilyId, textToEnter));

        editorPage().closeDocumentAndDiscardChanges();

        Assertions.assertAll(
                () -> Assertions.assertTrue(doesRiskFormattedTableInsert, RISK_FORMATTED_TABLE_ASSERTION_MESSAGE),
                () -> Assertions.assertTrue(doesTableEditorOpen, TABLE_EDITOR_ASSERTION_MESSAGE),
                () -> Assertions.assertTrue(doesInsertSourceCiteReferenceWindowOpen, "Insert Source Cite Reference window doesn't open"),
                () -> Assertions.assertTrue(doesPendingRenditionNavigateWindowOpen, "Pending Rendition Navigate window doesn't open"),
                () -> Assertions.assertTrue(doesPendingRenditionNavigateWindowClose, "Pending Rendition Navigate window doesn't close"),
                () -> Assertions.assertTrue(isAddedTextLinked, ADDED_TEXT_LINKING_ASSERTION_MESSAGE),
                () -> Assertions.assertTrue(doesExpectedTextPresentInEditor, EXPECTED_TEXT_ASSERTION_MESSAGE)
        );
    }

    @Test
    @IE_EDGE_MODE
    @RISK
    @LOG
    public void emailCiteReferenceTargetRiskTest()
    {
        String textToEnter = String.format(TEXT_TO_ENTER, System.currentTimeMillis());
        String textToVerify = "<entry><cite.query w-ref-type=\"FE\" w-normalized-cite=\"%s\" manual-edit=\"true\">%s</cite.query></entry>";
        String email = "test@test.com";
        String invalidEmail = email.substring(0, 4);

        homePage().goToHomePage();
        loginPage().logIn();
        hierarchyNavigatePage().goToHierarchyPage(ContentSets.CROWN_DEPENDENCIES.getCode());
        hierarchySearchPage().searchNodeUuid(CROWN_DEPENDENCIES_UUID);
        siblingMetadataPage().selectedSiblingMetadataEditContent();
        editorPage().switchToEditorTextFrame();

        editorTextPage().click(EditorTextPageElements.BODY_TAG + EditorTextPageElements.PARA_SPAN);
        editorTextPage().addTableUsingAltT();

        String commonXpath = EditorTextPageElements.BODY_TAG + EditorTextPageElements.EMBEDDED_HTML + "[1]/%s";

        boolean doesRiskFormattedTableInsert = editorTextPage().getElementsText(String.format(commonXpath, "span")).equals(TABLE_TITLE);

        editorTextPage().click(String.format(commonXpath, "span"));
        editorTextPage().editTableViaShiftAltT();

        editorTextPage().switchToWindow(TableEditorPageElements.PAGE_TITLE);
        tableEditorPage().enterTheInnerFrame();

        boolean doesTableEditorOpen = driver().getTitle().equals(TableEditorPageElements.PAGE_TITLE);

        tableEditorPage().clearAndEnterTextToTableInputFields(textToEnter);
        editorTextPage().highlightHelperUsingRobot(textToEnter);

        tableEditorPage().openMarkupDropdown();
        tableEditorPage().openMarkupDropdownOption(OTHER_LINKS);

        tableEditorPage().switchToWindow(InsertTargetCiteReferencePageElements.PAGE_TITLE);
        insertTargetCiteReferencePage().enterTheInnerFrame();

        boolean doesInsertTargetCiteReferenceWindowOpen = driver().getTitle().equals(InsertTargetCiteReferencePageElements.PAGE_TITLE);

        insertTargetCiteReferencePage().selectReferenceType(EMAIL);
        insertTargetCiteReferencePage().enterEmail(invalidEmail);
        insertTargetCiteReferencePage().clickSave();

        boolean doesErrorMessageAppear = InsertTargetCiteReferencePageElements.errorMessage.isDisplayed() &&
                InsertTargetCiteReferencePageElements.errorMessage.getText().contains(invalidEmail);

        insertTargetCiteReferencePage().click(InsertTargetCiteReferencePageElements.EMAIL_INPUT);
        editorTextPage().highlightHelperUsingRobot(invalidEmail);
        insertTargetCiteReferencePage().sendKeys(Keys.DELETE);
        insertTargetCiteReferencePage().sendKeys(email);
        insertTargetCiteReferencePage().clickSave();

        insertTargetCiteReferencePage().switchToWindow(TableEditorPageElements.PAGE_TITLE);
        tableEditorPage().enterTheInnerFrame();

        boolean isAddedTextLinked = tableEditorPage().isAddedTextLinked(NORMALIZED_CITE_ATTRIBUTE, email);

        tableEditorPage().clickSave();
        editorPage().switchToEditorTextFrame();

        boolean doesExpectedTextPresentInEditor = editorTextPage().getElementsText(
                String.format(commonXpath, "html.text")).contains(String.format(textToVerify, email, textToEnter));

        editorPage().closeDocumentAndDiscardChanges();

        Assertions.assertAll(
                () -> Assertions.assertTrue(doesRiskFormattedTableInsert, RISK_FORMATTED_TABLE_ASSERTION_MESSAGE),
                () -> Assertions.assertTrue(doesTableEditorOpen, TABLE_EDITOR_ASSERTION_MESSAGE),
                () -> Assertions.assertTrue(doesInsertTargetCiteReferenceWindowOpen, INSERT_TARGET_CITE_REFERENCE_ASSERTION_MESSAGE),
                () -> Assertions.assertTrue(doesErrorMessageAppear, ERROR_MESSAGE_ASSERTION_MESSAGE),
                () -> Assertions.assertTrue(isAddedTextLinked, ADDED_TEXT_LINKING_ASSERTION_MESSAGE),
                () -> Assertions.assertTrue(doesExpectedTextPresentInEditor, EXPECTED_TEXT_ASSERTION_MESSAGE)
        );
    }

    @Test
    @IE_EDGE_MODE
    @RISK
    @LOG
    public void citeReferenceLinkTargetRiskTest()
    {
        String textToEnter = String.format(TEXT_TO_ENTER, System.currentTimeMillis());
        String textToVerify = "<entry><cite.query w-ref-type=\"GO\" w-docfamily-uuid=\"%s\" manual-edit=\"true\">%s</cite.query></entry>";
        String docFamilyId = "Ie978206d2b4b4d269eeebc4fcad9e44e";
        String reVocations = "= 5 Re-vocations";
        String expectedColor = "COLOR: blue";

        homePage().goToHomePage();
        loginPage().logIn();
        hierarchyNavigatePage().goToHierarchyPage(ContentSets.CROWN_DEPENDENCIES.getCode());
        hierarchySearchPage().searchNodeUuid(CROWN_DEPENDENCIES_UUID);
        siblingMetadataPage().selectedSiblingMetadataEditContent();
        editorPage().switchToEditorTextFrame();

        editorTextPage().click(EditorTextPageElements.BODY_TAG + EditorTextPageElements.PARA_SPAN);
        editorTextPage().addTableUsingAltT();

        String commonXpath = EditorTextPageElements.BODY_TAG + EditorTextPageElements.EMBEDDED_HTML + "[1]/%s";

        boolean doesRiskFormattedTableInsert = editorTextPage().getElementsText(String.format(commonXpath, "span")).equals(TABLE_TITLE);

        editorTextPage().click(String.format(commonXpath, "span"));
        editorTextPage().editTableViaShiftAltT();

        editorTextPage().switchToWindow(TableEditorPageElements.PAGE_TITLE);
        tableEditorPage().enterTheInnerFrame();

        boolean doesTableEditorOpen = driver().getTitle().equals(TableEditorPageElements.PAGE_TITLE);

        tableEditorPage().clearAndEnterTextToTableInputFields(textToEnter);
        editorTextPage().highlightHelperUsingRobot(textToEnter);

        tableEditorPage().openMarkupDropdown();
        tableEditorPage().openMarkupDropdownOption(OTHER_LINKS);

        tableEditorPage().switchToWindow(InsertTargetCiteReferencePageElements.PAGE_TITLE);
        insertTargetCiteReferencePage().enterTheInnerFrame();

        boolean doesInsertTargetCiteReferenceWindowOpen = driver().getTitle().equals(InsertTargetCiteReferencePageElements.PAGE_TITLE);

        insertTargetCiteReferencePage().selectReferenceType(TARGET);
        insertTargetCiteReferencePage().selectContentSet(ContentSets.CROWN_DEPENDENCIES);
        insertTargetCiteReferencePage().clickLocateTarget();

        boolean doesTargetLocateWindowOpen = driver().getTitle().equals(TargetLocatorPageElements.PAGE_TITLE);

        targetLocatorPage().scrollToElement(String.format(TargetLocatorPageElements.HIERARCHY_TREE_NODE, reVocations));
        targetLocatorPage().rightClick(String.format(TargetLocatorPageElements.HIERARCHY_TREE_NODE, reVocations));
        targetLocatorPage().selectNodeForTargetLinkMarkup();

        boolean doesTargetLocateWindowClose = !driver().getTitle().equals(TargetLocatorPageElements.PAGE_TITLE);
        boolean doesTargetFieldContainExpectedValue = insertTargetCiteReferencePage().getElement(
                InsertTargetCiteReferencePageElements.TARGET_INPUT).getAttribute("value").equals(reVocations);

        insertTargetCiteReferencePage().clickSave();
        insertTargetCiteReferencePage().switchToWindow(TableEditorPageElements.PAGE_TITLE);
        tableEditorPage().enterTheInnerFrame();

        boolean isAddedTextLinked = tableEditorPage().isAddedTextLinked(DOC_FAMILY_UUID_ATTRIBUTE, docFamilyId);

        tableEditorPage().clickSave();
        editorPage().switchToEditorTextFrame();

        boolean doesExpectedTextPresentInEditor = editorTextPage().getElementsText(
                String.format(commonXpath, "html.text")).contains(String.format(textToVerify, docFamilyId, textToEnter));

        editorTextPage().rightClick(String.format(commonXpath, "span"));
        editorTextPage().breakOutOfFrame();
        editorTextPage().openContextMenu(EditorTextContextMenuElements.PREVIEW_OPTION);

        editorTextPage().switchToWindow(DocumentPreviewPageElements.PAGE_TITLE);
        documentPreviewPage().enterTheInnerFrame();

        boolean doesDocumentPreviewWindowOpen = driver().getTitle().equals(DocumentPreviewPageElements.PAGE_TITLE);
        boolean doesAddedTextHaveBlueColor = tableEditorPage().isAddedTextLinked("style", expectedColor);

        documentPreviewPage().closePreview();
        editorPage().closeDocumentAndDiscardChanges();

        Assertions.assertAll(
                () -> Assertions.assertTrue(doesRiskFormattedTableInsert, RISK_FORMATTED_TABLE_ASSERTION_MESSAGE),
                () -> Assertions.assertTrue(doesTableEditorOpen, TABLE_EDITOR_ASSERTION_MESSAGE),
                () -> Assertions.assertTrue(doesInsertTargetCiteReferenceWindowOpen, INSERT_TARGET_CITE_REFERENCE_ASSERTION_MESSAGE),
                () -> Assertions.assertTrue(doesTargetLocateWindowOpen, TARGET_LOCATE_WINDOW_OPEN_ASSERTION_MESSAGE),
                () -> Assertions.assertTrue(doesTargetLocateWindowClose, TARGET_LOCATE_WINDOW_CLOSE_ASSERTION_MESSAGE),
                () -> Assertions.assertTrue(doesTargetFieldContainExpectedValue, TARGET_FIELD_ASSERTION_MESSAGE),
                () -> Assertions.assertTrue(isAddedTextLinked, ADDED_TEXT_LINKING_ASSERTION_MESSAGE),
                () -> Assertions.assertTrue(doesExpectedTextPresentInEditor, EXPECTED_TEXT_ASSERTION_MESSAGE),
                () -> Assertions.assertTrue(doesDocumentPreviewWindowOpen, "Document Preview window doesn't open"),
                () -> Assertions.assertTrue(doesAddedTextHaveBlueColor, "Added text doesn't have blue color")
        );
    }

    @Test
    @IE_EDGE_MODE
    @RISK
    @LOG
    public void glossaryLinkTargetRiskTest()
    {
        String textToEnter = String.format(TEXT_TO_ENTER, System.currentTimeMillis());
        String textToVerify = "<entry><cite.query w-ref-type=\"GM\" w-docfamily-uuid=\"%s\" w-pinpoint-page=\"%s\" manual-edit=\"true\">%s</cite.query></entry>";
        String accumulationUnit = "accumulation unit";
        String docFamilyId = "I5134ed9a7c964c7f8a947906eb54b786";

        hierarchyNavigatePage().goToHierarchyPage(ContentSets.FCA_HANDBOOK.getCode());
        loginPage().logIn();
        hierarchyNavigatePage().handleCasIssueWithHierarchyPageOpenedAsStartPageIfOccurred(ContentSets.FCA_HANDBOOK);
        hierarchySearchPage().searchNodeUuid(FCA_HANDBOOK_UUID);
        siblingMetadataPage().selectedSiblingMetadataEditContent();
        editorPage().switchToEditorTextFrame();

        editorTextPage().click(EditorTextPageElements.BODY_TAG + EditorTextPageElements.PARA_SPAN);
        editorTextPage().addTableUsingAltT();

        String commonXpath = EditorTextPageElements.BODY_TAG + EditorTextPageElements.EMBEDDED_HTML + "[1]/%s";

        boolean doesRiskFormattedTableInsert = editorTextPage().getElementsText(String.format(commonXpath, "span")).equals(TABLE_TITLE);

        editorTextPage().click(String.format(commonXpath, "span"));
        editorTextPage().editTableViaShiftAltT();

        editorTextPage().switchToWindow(TableEditorPageElements.PAGE_TITLE);
        tableEditorPage().enterTheInnerFrame();

        boolean doesTableEditorOpen = driver().getTitle().equals(TableEditorPageElements.PAGE_TITLE);

        tableEditorPage().clearAndEnterTextToTableInputFields(textToEnter);
        editorTextPage().highlightHelperUsingRobot(textToEnter);

        tableEditorPage().openMarkupDropdown();
        tableEditorPage().openMarkupDropdownOption(OTHER_LINKS);

        tableEditorPage().switchToWindow(InsertTargetCiteReferencePageElements.PAGE_TITLE);
        insertTargetCiteReferencePage().enterTheInnerFrame();

        boolean doesInsertTargetCiteReferenceWindowOpen = driver().getTitle().equals(InsertTargetCiteReferencePageElements.PAGE_TITLE);

        insertTargetCiteReferencePage().selectReferenceType(GLOSSARY);
        insertTargetCiteReferencePage().clickLocateTarget();

        boolean doesTargetLocateWindowOpen = driver().getTitle().equals(TargetLocatorPageElements.PAGE_TITLE);

        targetLocatorPage().rightClick(String.format(TargetLocatorPageElements.HIERARCHY_TREE_NODE, accumulationUnit));
        targetLocatorPage().selectNodeForTargetLinkMarkup();

        boolean doesTargetLocateWindowClose = !driver().getTitle().equals(TargetLocatorPageElements.PAGE_TITLE);
        boolean doesTargetFieldContainExpectedValue = insertTargetCiteReferencePage().getElement(
                InsertTargetCiteReferencePageElements.TARGET_INPUT).getAttribute("value").contains(accumulationUnit);

        insertTargetCiteReferencePage().clickSave();
        insertTargetCiteReferencePage().switchToWindow(TableEditorPageElements.PAGE_TITLE);
        tableEditorPage().enterTheInnerFrame();

        boolean isAddedTextLinked = tableEditorPage().isAddedTextLinked(PINPOINT_PAGE_ATTRIBUTE, accumulationUnit);

        tableEditorPage().clickSave();
        editorPage().switchToEditorTextFrame();

        boolean doesExpectedTextPresentInEditor = editorTextPage().getElementsText(
                String.format(commonXpath, "html.text")).contains(String.format(textToVerify, docFamilyId, accumulationUnit, textToEnter));

        editorPage().closeDocumentAndDiscardChanges();

        Assertions.assertAll(
                () -> Assertions.assertTrue(doesRiskFormattedTableInsert, RISK_FORMATTED_TABLE_ASSERTION_MESSAGE),
                () -> Assertions.assertTrue(doesTableEditorOpen, TABLE_EDITOR_ASSERTION_MESSAGE),
                () -> Assertions.assertTrue(doesInsertTargetCiteReferenceWindowOpen, INSERT_TARGET_CITE_REFERENCE_ASSERTION_MESSAGE),
                () -> Assertions.assertTrue(doesTargetLocateWindowOpen, TARGET_LOCATE_WINDOW_OPEN_ASSERTION_MESSAGE),
                () -> Assertions.assertTrue(doesTargetLocateWindowClose, TARGET_LOCATE_WINDOW_CLOSE_ASSERTION_MESSAGE),
                () -> Assertions.assertTrue(doesTargetFieldContainExpectedValue, TARGET_FIELD_ASSERTION_MESSAGE),
                () -> Assertions.assertTrue(isAddedTextLinked, ADDED_TEXT_LINKING_ASSERTION_MESSAGE),
                () -> Assertions.assertTrue(doesExpectedTextPresentInEditor, EXPECTED_TEXT_ASSERTION_MESSAGE)
        );
    }
}