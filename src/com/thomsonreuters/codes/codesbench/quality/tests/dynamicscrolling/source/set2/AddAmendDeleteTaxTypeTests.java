package com.thomsonreuters.codes.codesbench.quality.tests.dynamicscrolling.source.set2;

import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.popups.EditTaxTypesElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.tools.TaxTypeAssignmentDatabaseUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.sql.Connection;

import static com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import static com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;

public class AddAmendDeleteTaxTypeTests extends TestService {

    /**
     * STORY/BUG - HALCYONST-9781 <br>
     * SUMMARY -  Add/Amend/Delete Tax Type Wrapper for body/section/delta without saving changes<br>
     * USER - LEGAL <br>
     * CONTENT SET - Iowa (Development), PREP, LAW <br>
     */
    @ParameterizedTest(name = "Test: {displayName}; Index: {index}; Arguments: {arguments}")
    @ValueSource(strings = {"source.body","source.section", "delta"})
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void addAmendDeleteTaxTypeWrapperForBodySectionDeltaWithoutSavingChangesTest(String node)
    {
        String uuid = "IBCE0FAC01F0111E98D18B00554385E22";
        String taxTypeAdd = "TTRT1";
        String taxTypeAmend = "TTRT2";

        //preconditions
        deleteTaxTypeAssignmentForUAT(uuid);

        //Sign in as the legal user. Go to source -> C2012 Navigate -> Rendition tab. Open DS
        sourcePage().goToSourcePageWithRenditionUuids(uuid);
        loginPage().logIn();
        sourceNavigateGridPage().firstRenditionEditContent();

        //Add tax type
        editorPage().switchToEditorTextFrame();
        editorPage().scrollToElement(String.format(EditorTextPageElements.ENGLISH_LABEL,node));
        editorPage().rightClick(String.format(EditorTextPageElements.ENGLISH_LABEL,node));
        editorTextPage().breakOutOfFrame();
        editorTextContextMenuPage().taxTypeAdd();
        Assertions.assertTrue(editorPage().switchToWindow(String.format(EditTaxTypesElements.PAGE_TITLE_NEW,node)),
                "Select a new Tax Type window DOESN't appear");

        editTaxTypesPage().selectAndMoveAllSelectedTaxTagAdd();
        editTaxTypesPage().selectAndMoveAvailableTaxTagAddByName(taxTypeAdd);
        editTaxTypesPage().clickOk();
        Assertions.assertFalse(editorPage().doesWindowExistByTitle(String.format(EditTaxTypesElements.PAGE_TITLE_NEW,node)),
                "Select a new  Tax Type window HAVEN'T gone");

        //English Label for Tax Type Add appears for selected body/section/delta
        editorPage().switchToEditor();
        editorPage().switchToEditorTextFrame();
        String taxTypeAddValueAdd = editorTextPage().getTaxTypeAddValue(node);

        //Amend tax type
        editorPage().rightClick(String.format(EditorTextPageElements.HIGHLIGHTED_TAX_TYPE_ADD_ENGLISH_LABEL,node));
        editorTextPage().breakOutOfFrame();
        editorTextContextMenuPage().amend();
        Assertions.assertTrue(editorPage().switchToWindow(String.format(EditTaxTypesElements.PAGE_TITLE_EDIT,node)),
                "Edit Tax Type window DOESN't appear");

        editTaxTypesPage().selectAndMoveAllSelectedTaxTagAdd();
        editTaxTypesPage().selectAndMoveAvailableTaxTagAddByName(taxTypeAmend);
        editTaxTypesPage().clickOk();
        Assertions.assertFalse(editorPage().doesWindowExistByTitle(String.format(EditTaxTypesElements.PAGE_TITLE_EDIT,node)),
                "Edit Tax Type window HAVEN'T gone");

        //English Label for Tax Type Add appears for selected body/section/delta
        editorPage().switchToEditor();
        editorPage().switchToEditorTextFrame();
        String taxTypeAddValueAmend = editorTextPage().getTaxTypeAddValue(node);

        //Delete tax type
        editorPage().rightClick(String.format(EditorTextPageElements.HIGHLIGHTED_TAX_TYPE_ADD_ENGLISH_LABEL,node));
        editorTextPage().breakOutOfFrame();
        editorTextContextMenuPage().delete();

        //English Label for Tax Type Add appears for selected body/section/delta
        editorPage().switchToEditorTextFrame();
        boolean taxTypeAddValueDelete = editorTextPage().doesElementExist(String.format(EditorTextPageElements.TAX_TYPE_ADD_ENGLISH_LABEL,node));

        //Close document and Discard Changes
        editorPage().switchToEditor();
        editorPage().closeDocumentAndDiscardChanges();

        Assertions.assertAll("insertTaxTypeWrapperWithoutSavingChanges",
                () -> Assertions.assertEquals(taxTypeAdd,taxTypeAddValueAdd,
                        "Tax Type Add value after adding is WRONG"),
                () -> Assertions.assertEquals(taxTypeAmend,taxTypeAddValueAmend,
                        "Tax Type Add value after amending is WRONG"),
                () -> Assertions.assertFalse(taxTypeAddValueDelete,
                        "Tax Type Add value after deleting is WRONG")
        );
    }

    /**
     * STORY/BUG - HALCYONST-9781 <br>
     * SUMMARY -  Add Tax Type for body, section and delta with check-in. And verify it in Source->C2012 Navigate<br>
     * USER - LEGAL <br>
     * CONTENT SET - Iowa (Development), non PREP, LAW <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void addTaxTypeForBodySectionDeltaWithSavingChangesTest()
    {
        String uuid = "I00971001EB1811E4B2F7E0548898E78A";
        String taxTypeForBody = "TTRT1";
        String taxTypeForSection = "TTRT2";
        String taxTypeForDelta = "TTRT3";
        String body = "source.body";
        String section = "source.section";
        String delta = "delta";

        //preconditions
        deleteTaxTypeAssignmentForUAT(uuid);

        //Sign in as the legal user. Go to source -> C2012 Navigate -> Rendition tab. Open DS
        sourcePage().goToSourcePageWithRenditionUuids(uuid);
        loginPage().logIn();

        //open DS
        sourceNavigateGridPage().firstRenditionEditContent();

        //Add tax type for Body
        addTaxType(body, taxTypeForBody);

        //Add tax type for Section
        addTaxType(section, taxTypeForSection);

        //Add tax type for Delta
        addTaxType(delta, taxTypeForDelta);

        //Close document and Save Changes
        editorPage().switchToEditor();
        editorPage().closeAndCheckInChanges();

        //Go to source->C2012 navigate ->Rendition tab
        sourcePage().switchToSourceNavigatePage();
        sourcePage().waitForGridRefresh();
        sourcePage().goToRenditionTab();
        sourcePage().publicViewAllColsWithCdpAutoIntegration();
        String taxTypeAddRenditionValue = sourceNavigateGridPage().getFirstItemTaxTypeAddValue();

        //Go to Section tab
        sourceNavigateGridPage().clickFirstItem();
        sourcePage().goToSectionTab();
        sourcePage().publicViewAllCols();
        String taxTypeAddSectionValue = sourceNavigateGridPage().getFirstItemTaxTypeAddValue();

        //Go to Delta tab
        sourceNavigateGridPage().clickFirstItem();
        sourcePage().goToDeltaTab();
        sourcePage().publicViewAllCols();
        String taxTypeAddDeltaValue = sourceNavigateGridPage().getFirstItemTaxTypeAddValue();

        Assertions.assertAll("insertTaxTypeWrapperWithSavingChangesTest",
                () -> Assertions.assertEquals(taxTypeForBody,taxTypeAddRenditionValue,
                        "Tax Type Add value in Rendition Tab is WRONG"),
                () -> Assertions.assertEquals(taxTypeForSection,taxTypeAddSectionValue,
                        "Tax Type Add value in Section Tab is WRONG"),
                () -> Assertions.assertEquals(taxTypeForDelta,taxTypeAddDeltaValue,
                        "Tax Type Add value in Delta Tab is WRONG")
        );
    }

    /**
     * STORY/BUG - HALCYONST-9781 <br>
     * SUMMARY -  Add/Amend/Delete  Tax Type Wrapper for body, section and delta with check-in. And verify it in Source->C2012 Navigate<br>
     * USER - LEGAL <br>
     * CONTENT SET - Iowa (Development), PREP, LAW <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void addAmendDeleteTaxTypeForBodyWithSavingChangesTest()
    {
        String uuid = "IBCE0FAC01F0111E98D18B00554385E22";
        String taxType1 = "TTRT1";
        String taxType2 = "TTRT2";
        String body = "source.body";

        //preconditions
        deleteTaxTypeAssignmentForUAT(uuid);

        //Sign in as the legal user. Go to source -> C2012 Navigate -> Rendition tab. Open DS
        sourcePage().goToSourcePageWithRenditionUuids(uuid);
        loginPage().logIn();

        //open DS
        sourceNavigateGridPage().firstRenditionEditContent();

        //Add tax type for Body
        addTaxType(body, taxType1);

        //Close document and Save Changes
        editorPage().switchToEditor();
        editorPage().closeAndCheckInChanges();

        //Go to source->C2012 navigate ->Rendition tab
        sourcePage().switchToSourceNavigatePage();
        sourcePage().waitForGridRefresh();
        sourcePage().goToRenditionTab();
        sourcePage().publicViewAllColsWithCdpAutoIntegration();
        String taxTypeAddValue = sourceNavigateGridPage().getFirstItemTaxTypeAddValue();

        //open DS
        sourceNavigateGridPage().firstRenditionEditContent();

        //Amend tax type
        editorPage().switchToEditorTextFrame();
        editorPage().rightClick(String.format(EditorTextPageElements.TAX_TYPE_ADD_ENGLISH_LABEL,body));
        editorTextPage().breakOutOfFrame();
        editorTextContextMenuPage().amend();
        Assertions.assertTrue(editorPage().switchToWindow(String.format(EditTaxTypesElements.PAGE_TITLE_EDIT,body)),
                "Edit Tax Type window DOESN't appear");

        editTaxTypesPage().selectAndMoveAllSelectedTaxTagAdd();
        editTaxTypesPage().selectAndMoveAvailableTaxTagAddByName(taxType2);
        editTaxTypesPage().clickOk();
        Assertions.assertFalse(editorPage().doesWindowExistByTitle(String.format(EditTaxTypesElements.PAGE_TITLE_EDIT,body)),
                "Edit Tax Type window HAVEN'T gone");

        //Close document and Save Changes
        editorPage().switchToEditor();
        editorPage().closeAndCheckInChanges();

        //Go to source->C2012 navigate ->Rendition tab
        sourcePage().switchToSourceNavigatePage();
        sourcePage().waitForGridRefresh();
        sourcePage().goToRenditionTab();
        String taxTypeAmendValue = sourceNavigateGridPage().getFirstItemTaxTypeAddValue();

        //open DS
        sourceNavigateGridPage().firstRenditionEditContent();

        //Delete tax type
        editorPage().switchToEditorTextFrame();
        editorPage().rightClick(String.format(EditorTextPageElements.TAX_TYPE_ADD_ENGLISH_LABEL,body));
        editorTextPage().breakOutOfFrame();
        editorTextContextMenuPage().delete();

        //Close document and Save Changes
        editorPage().switchToEditor();
        editorPage().closeAndCheckInChanges();

        //Go to source->C2012 navigate ->Rendition tab
        sourcePage().switchToSourceNavigatePage();
        sourcePage().waitForGridRefresh();
        sourcePage().goToRenditionTab();
        String taxTypeDeleteValue = sourceNavigateGridPage().getFirstItemTaxTypeAddValue();

        Assertions.assertAll("insertTaxTypeWrapperWithSavingChangesTest",
                () -> Assertions.assertEquals(taxType1,taxTypeAddValue,
                        "Tax Type Add value in Rendition Tab is WRONG"),
                () -> Assertions.assertEquals(taxType2,taxTypeAmendValue,
                        "Tax Type Amend value in Rendition Tab is WRONG"),
                () -> Assertions.assertEquals("",taxTypeDeleteValue,
                        "Tax Type Delete value in Rendition Tab is WRONG")
        );
    }

    private void addTaxType(String node, String taxType)
    {
        editorPage().switchToEditor();
        editorPage().switchToEditorTextFrame();
        editorPage().scrollToElement(String.format(EditorTextPageElements.ENGLISH_LABEL,node));
        editorPage().rightClick(String.format(EditorTextPageElements.ENGLISH_LABEL,node));
        editorTextPage().breakOutOfFrame();
        editorTextContextMenuPage().taxTypeAdd();
        Assertions.assertTrue(editorPage().switchToWindow(String.format(EditTaxTypesElements.PAGE_TITLE_NEW,node)),
                "Edit Tax Type window DOESN't appear");

        editTaxTypesPage().selectAndMoveAllSelectedTaxTagAdd();
        editTaxTypesPage().selectAndMoveAvailableTaxTagAddByName(taxType);
        editTaxTypesPage().clickOk();
        Assertions.assertFalse(editorPage().doesWindowExistByTitle(String.format(EditTaxTypesElements.PAGE_TITLE_NEW,node)),
                "Edit Tax Type window HAVEN'T gone");
    }

    public void deleteTaxTypeAssignmentForUAT(String renditionUUID)
    {
        Connection uatConnection = BaseDatabaseUtils.connectToDatabaseUAT();
        TaxTypeAssignmentDatabaseUtils.deleteTaxTypeAssignmentFromRenditionAndItsSectionsAndDeltas(uatConnection, renditionUUID);
        BaseDatabaseUtils.commit(uatConnection);
        BaseDatabaseUtils.disconnect(uatConnection);
    }
}
