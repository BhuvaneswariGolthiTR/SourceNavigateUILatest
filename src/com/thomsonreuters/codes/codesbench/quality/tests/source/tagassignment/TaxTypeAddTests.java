package com.thomsonreuters.codes.codesbench.quality.tests.source.tagassignment;

import com.thomsonreuters.codes.codesbench.quality.pageelements.source.SourceNavigatePageElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.tools.TaxTypeAssignmentDatabaseUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class TaxTypeAddTests extends TestService
{
    public static final String EMPTY_STATUS = "";

    /**
     * STORY/BUG - HALCYONST-9573 <br>
     * SUMMARY - Tax Type – Source Navigate Implementation (Disabled context menu option) <br>
     * USER - Legal <br>
     * CONTENT TYPE - anything not in (LAW, RES, UNCHAPTERED, ORDER, REG), such as CRT <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void taxTypeDisabledContextMenuOptionTest()
    {
        //for DEV
//        String uuidCRT = "I030992B0E00A11E2BADBFA3EFEF961C0";
//        String uuidLAW = "I68C3F81118B911E8820588494B6E0205";

        //for UAT
        String uuidCRT = "I00D0A9F09D2311E6A7AC9C82917CBFA7";
        String uuidLAW = "I124CF7D1619D11E28B049F1D7A89B350";

        //STEP 01: Sign in as the legal user
        //STEP 02: Go to source -> c2012 navigate
        //STEP 03: Filter for: Rendition Status: PREP; Content Type: anything not in (LAW, RES, UNCHAPTERED, ORDER, REG), such as CRT
        sourcePage().goToSourcePageWithRenditionUuids(uuidCRT);
        loginPage().logIn();
        sourceNavigateFooterToolsPage().refreshTheGrid();
        //STEP 04: Right click the rendition
        sourceNavigateGridPage().rightClickFirstItem();
        //STEP 05: VERIFY Edit -> Tax Type Add is disabled
        boolean isEditTaxTypeAddDisabledForCRT = renditionContextMenu().isEditTaxTypeAddDisabled();
        //STEP 06: Filter for: Rendition Status: APV; Content Type: LAW
        sourcePage().goToSourcePageWithRenditionUuids(uuidLAW);
        sourceNavigateFooterToolsPage().refreshTheGrid();
        //STEP 07: Right click the rendition
        sourceNavigateGridPage().rightClickFirstItem();
        //STEP 08: VERIFY Edit -> Tax Type Add is disabled
        boolean isEditTaxTypeAddDisabledForLAW = renditionContextMenu().isEditTaxTypeAddDisabled();

        Assertions.assertAll("taxTypeDisabledContextMenuOptionTest",
                () -> Assertions.assertTrue(isEditTaxTypeAddDisabledForCRT,
                        "STEP 05: Edit -> Tax Type Add IS NOT disabled"),
                () -> Assertions.assertTrue(isEditTaxTypeAddDisabledForLAW,
                        "STEP 08: Edit -> Tax Type Add IS NOT disabled"));
    }


    /**
     * STORY/BUG - HALCYONST-9573 <br>
     * SUMMARY - Tax Type – Source Navigate Implementation
     * (Enabled context menu option, adding and removing tax type tags, and column check) (rendition) <br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void addingAndRemovingTaxTypeTagsRenditionLevelTest()
    {
//        for DEV
//        String uuidLaw = "I15E459C0B20811E985F68156B6DA447E";
//        for UAT
        String uuidLaw = "I51CEAFE0AC0611E6B81DE813DB1990E2";
        List<String> taxTypeTags = Arrays.asList("TTRT1", "TTRT2", "TTRT3", "TTRT4");
        String taxTypeAddExpectedValue = "TTRT1;TTRT2;TTRT3;TTRT4";

        //preconditions
        deleteTaxTypeAssignmentForUAT(uuidLaw);

        //STEP 01: Sign in as the legal user
        //STEP 02: Go to source navigate
        //STEP 03: Change your view to 'all col' in the View Management UI
        //STEP 04: Filter for:
        // DEV: Content Set: Iowa (Development); Year: 2019; Rendition Status: PREP; Content Type: LAW; Doc Number: 307
        // UAT: Content Set: Iowa (Development); Year: 2018; Rendition Status: PREP; Content Type: LAW; Doc Number: 2303
        homePage().goToHomePage();
        loginPage().logIn();
        sourcePage().goToSourcePageWithRenditionUuids(uuidLaw);
        sourcePage().publicViewAllColsWithCdpAutoIntegration();
        //STEP 05: Right click the rendition. VERIFY: Edit -> Tax Type Add is enabled
        sourceNavigateGridPage().rightClickFirstItem();
        boolean isEditTaxTypeAddEnable = renditionContextMenu().isEditTaxTypeAddDisabled();
        //Step 06: Click Tax Type Add. The windows lists the available Tax Type product tags in the given environment. No other product tags should appear in this UI
        Assertions.assertTrue(renditionContextMenu().clickTaxTypeAdd(),
                "STEP 06: Tax Type Add window DOESN'T appear");
        boolean isAvailableTaxTypeAddsEmpty = taxTypeAddPage().isAvailableTaxTypeAddsEmpty();
        //STEP 07: Select TTRT1, TTRT2, TTRT3, and TTRT4
        for (String taxTypeTag : taxTypeTags)
        {
            taxTypeAddPage().selectAndMoveAvailableTaxTagAddByName(taxTypeTag);
        }
        //STEP 08: Click Save. Tax Type Add window closes.The Tax Type Add column shows TTRT1;TTRT2;TTRT3;TTRT4
        boolean isTaxTypeAddWindowExist = taxTypeAddPage().clickSave();
        taxTypeAddPage().waitForGridRefresh();
        String taxTypeAddRenditionActualValueAfterAdding = sourceNavigateGridPage().getFirstItemTaxTypeAddValue();
        //STEP 09: Click the rendition
        sourceNavigateGridPage().clickFirstItem();
        //STEP 10: Go to the Section tab. The Tax Type Add column shows TTRT1;TTRT2;TTRT3;TTRT4 for each section in the grid
        sourcePage().goToSectionTab();
        sourcePage().publicViewAllCols();
        HashSet<String> taxTypeAddSectionActualValuesAfterAdding = new HashSet<>(sourceNavigateGridPage().getTaxTypeAddColumnValues());
        //STEP 11: Go to the delta tab. The Tax Type Add column shows TTRT1;TTRT2;TTRT3;TTRT4 for each delta in the grid
        sourcePage().goToDeltaTab();
        sourcePage().publicViewAllCols();
        HashSet<String> taxTypeAddDeltaActualValuesAfterAdding = new HashSet<>(sourceNavigateGridPage().getTaxTypeAddColumnValues());

        Assertions.assertAll("addingAndRemovingTaxTypeTagsRenditionLevelTest",
                () -> Assertions.assertFalse(isEditTaxTypeAddEnable,
                        "STEP 05: Edit -> Tax Type Add IS NOT disabled"),
                () -> Assertions.assertFalse(isAvailableTaxTypeAddsEmpty,
                        "STEP 06: The Available Tax Type Adds list IS empty"),
                () -> Assertions.assertFalse(isTaxTypeAddWindowExist,
                        "STEP 08: Tax Type Add window DIDN'T close"),
                () -> Assertions.assertEquals(taxTypeAddExpectedValue, taxTypeAddRenditionActualValueAfterAdding,
                        String.format("STEP 08: Rendition tab. The Tax Type Add column shows '%s' instead of '%s'", taxTypeAddRenditionActualValueAfterAdding, taxTypeAddExpectedValue)),
                () -> Assertions.assertEquals(1, taxTypeAddSectionActualValuesAfterAdding.size(),
                        "STEP 10: Section tab. The Tax Type Add status IS NOT the same for each row"),
                () -> Assertions.assertTrue(taxTypeAddSectionActualValuesAfterAdding.contains(taxTypeAddExpectedValue),
                        String.format("STEP 10: Section tab. The Tax Type Add column shows '%s' instead of '%s'", taxTypeAddSectionActualValuesAfterAdding.toString(), taxTypeAddExpectedValue)),
                () -> Assertions.assertEquals(1, taxTypeAddDeltaActualValuesAfterAdding.size(),
                        "STEP 11: Delta tab. The Tax Type Add status IS NOT the same for each row"),
                () -> Assertions.assertTrue(taxTypeAddDeltaActualValuesAfterAdding.contains(taxTypeAddExpectedValue),
                        String.format("STEP 11: Delta tab. The Tax Type Add column shows '%s' instead of '%s'", taxTypeAddDeltaActualValuesAfterAdding.toString(), taxTypeAddExpectedValue))
        );

    }

    /**
     * STORY/BUG - HALCYONST-9573 <br>
     * SUMMARY - Tax Type – Source Navigate Implementation
     * (Enabled context menu option, adding and removing tax type tags, and column check) (section) <br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void addingAndRemovingTaxTypeTagsSectionLevelTest()
    {
//        for DEV
//        String uuidLaw = "I15E459C0B20811E985F68156B6DA447E";
//        for UAT
        String uuidLaw = "IBCE0FAC01F0111E98D18B00554385E22";
        List<String> taxTypeTags = Arrays.asList("TTRT1", "TTRT2", "TTRT3", "TTRT4");
        String taxTypeAddExpectedValue = "TTRT1;TTRT2;TTRT3;TTRT4";

        //preconditions
        deleteTaxTypeAssignmentForUAT(uuidLaw);

        //STEP 01: Sign in as the legal user
        //STEP 02: Go to source navigate
        //STEP 03: Change your view to 'all col' in the View Management UI
        //STEP 04: Filter for:
        // DEV: Content Set: Iowa (Development); Year: 2019; Rendition Status: PREP; Content Type: LAW; Doc Number: 307
        // UAT: Content Set: Iowa (Development); Year: 2018; Rendition Status: PREP; Content Type: LAW; Doc Number: 2303
        homePage().goToHomePage();
        loginPage().logIn();
        sourcePage().goToSourcePageWithRenditionUuids(uuidLaw);
        sourcePage().publicViewAllColsWithCdpAutoIntegration();
        //STEP 05: Click Rendition
        sourceNavigateGridPage().clickFirstItem();
        //STEP 06: Go to the Section tab.
        sourcePage().goToSectionTab();
        sourcePage().publicViewAllCols();
        //STEP 07: Right click first Section
        sourceNavigateGridPage().rightClickFirstItem();
        //Step 08: Click Tax Type Add.
        Assertions.assertTrue(sectionContextMenu().editTaxTypeAdd(),
                "STEP 08: Tax Type Add window DOESN'T appear");
        boolean isAvailableTaxTypeAddsEmpty = taxTypeAddPage().isAvailableTaxTypeAddsEmpty();
        //STEP 09: Select TTRT1, TTRT2, TTRT3, and TTRT4
        for (String taxTypeTag : taxTypeTags)
        {
            taxTypeAddPage().selectAndMoveAvailableTaxTagAddByName(taxTypeTag);
        }
        //STEP 10: Click Save. Tax Type Add window closes.The Tax Type Add column shows TTRT1;TTRT2;TTRT3;TTRT4
        boolean isTaxTypeAddWindowExist = taxTypeAddPage().clickSave();
        taxTypeAddPage().waitForGridRefresh();
        String taxTypeAddSectionActualValueAfterAdding = sourceNavigateGridPage().getFirstItemTaxTypeAddValue();
        //STEP 11: Click the section
        sourceNavigateGridPage().clickFirstItem();
        //STEP 12: Go to the delta tab. The Tax Type Add column shows TTRT1;TTRT2;TTRT3;TTRT4 for each delta in the grid
        sourcePage().goToDeltaTab();
        sourcePage().publicViewAllCols();
        HashSet<String> taxTypeAddDeltaActualValuesAfterAdding = new HashSet<>(sourceNavigateGridPage().getTaxTypeAddColumnValues());
        //STEP 13: Go to the rendition tab. The Tax Type Add column doesn’t show TTRT1;TTRT2;TTRT3;TTRT4
        sourcePage().goToRenditionTab();
        String taxTypeAddRenditionActualValueAfterAdding = sourceNavigateGridPage().getFirstItemTaxTypeAddValue();

        Assertions.assertAll("addingAndRemovingTaxTypeTagsSectionLevelTest",
                () -> Assertions.assertFalse(isAvailableTaxTypeAddsEmpty,
                        "STEP 09: The Available Tax Type Adds list IS empty"),
                () -> Assertions.assertFalse(isTaxTypeAddWindowExist,
                        "STEP 10: Tax Type Add window DIDN'T close"),
                () -> Assertions.assertEquals(taxTypeAddExpectedValue, taxTypeAddSectionActualValueAfterAdding,
                        String.format("STEP 08: Section tab. The Tax Type Add column shows '%s' instead of '%s'", taxTypeAddSectionActualValueAfterAdding, taxTypeAddExpectedValue)),
                () -> Assertions.assertEquals(1, taxTypeAddDeltaActualValuesAfterAdding.size(),
                        "STEP 12: Delta tab. The Tax Type Add status IS NOT the same for each row"),
                () -> Assertions.assertTrue(taxTypeAddDeltaActualValuesAfterAdding.contains(taxTypeAddExpectedValue),
                        String.format("STEP 12: Delta tab. The Tax Type Add column shows '%s' instead of '%s'", taxTypeAddDeltaActualValuesAfterAdding.toString(), taxTypeAddExpectedValue)),
                () -> Assertions.assertEquals(EMPTY_STATUS, taxTypeAddRenditionActualValueAfterAdding,
                        String.format("STEP 13: Rendition tab. The Tax Type Add column shows '%s' instead of '%s'", taxTypeAddRenditionActualValueAfterAdding, taxTypeAddExpectedValue))
        );

    }

    /**
     * STORY/BUG - HALCYONST-9573 <br>
     * SUMMARY - Tax Type – Source Navigate Implementation
     * (Enabled context menu option, adding and removing tax type tags, and column check) (section) <br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void addingTaxTypeTagsMultipleSectionsTest()
    {
//        for UAT
        String uuidLaw = "IBCE0FAC01F0111E98D18B00554385E22";
        List<String> taxTypeTags = Arrays.asList("TTRT1", "TTRT2", "TTRT3", "TTRT4");
        String taxTypeAddExpectedValue = "TTRT1;TTRT2;TTRT3;TTRT4";

        //preconditions
        deleteTaxTypeAssignmentForUAT(uuidLaw);

        //STEP 01: Sign in as the legal user
        //STEP 02: Go to source navigate
        //STEP 03: Change your view to 'all col' in the View Management UI
        //STEP 04: Filter for:
        // UAT: Content Set: Iowa (Development); Year: 2018; Rendition Status: PREP; Content Type: LAW; Doc Number: 2303
        homePage().goToHomePage();
        loginPage().logIn();
        sourcePage().goToSourcePageWithRenditionUuids(uuidLaw);
        sourcePage().publicViewAllColsWithCdpAutoIntegration();
        //STEP 05: Click Rendition
        sourceNavigateGridPage().clickFirstItem();
        //STEP 06: Go to the Section tab.
        sourcePage().goToSectionTab();
        sourcePage().publicViewAllCols();
        //STEP 07: Right click first Section
        sourcePage().selectAllOnPage();
        sourceNavigateGridPage().rightClickFirstItem();
        //Step 08: Click Tax Type Add.
        Assertions.assertTrue(sectionContextMenu().editTaxTypeAdd(),
                "STEP 08: Tax Type Add window DOESN'T appear");
        boolean isAvailableTaxTypeAddsEmpty = taxTypeAddPage().isAvailableTaxTypeAddsEmpty();
        //STEP 09: Select TTRT1, TTRT2, TTRT3, and TTRT4
        for (String taxTypeTag : taxTypeTags)
        {
            taxTypeAddPage().selectAndMoveAvailableTaxTagAddByName(taxTypeTag);
        }
        //STEP 10: Click Save. Tax Type Add window closes.The Tax Type Add column shows TTRT1;TTRT2;TTRT3;TTRT4
        boolean isTaxTypeAddWindowExist = taxTypeAddPage().clickSave();
        taxTypeAddPage().waitForGridRefresh();
        String taxTypeAddSectionActualValueAfterAdding = sourceNavigateGridPage().getFirstItemTaxTypeAddValue();

        Assertions.assertAll("addingAndRemovingTaxTypeTagsSectionLevelTest",
                () -> Assertions.assertFalse(isAvailableTaxTypeAddsEmpty,
                        "STEP 09: The Available Tax Type Adds list IS empty"),
                () -> Assertions.assertFalse(isTaxTypeAddWindowExist,
                        "STEP 10: Tax Type Add window DIDN'T close"),
                () -> Assertions.assertEquals(taxTypeAddExpectedValue, taxTypeAddSectionActualValueAfterAdding,
                        String.format("STEP 08: Section tab. The Tax Type Add column shows '%s' instead of '%s'", taxTypeAddSectionActualValueAfterAdding, taxTypeAddExpectedValue))
        );

    }

    /**
     * STORY/BUG - HALCYONST-9573 <br>
     * SUMMARY - Tax Type – Source Navigate Implementation
     * (Enabled context menu option, adding and removing tax type tags, and column check) (delta) <br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void addingAndRemovingTaxTypeTagsDeltaLevelTest()
    {
//        for DEV
//        String uuidLaw = "I15E459C0B20811E985F68156B6DA447E";
//        for UAT
        String uuidLaw = "ID3FFD3E2E88E11EA852AC4B447F6A479";
        List<String> taxTypeTags = Arrays.asList("TTRT1", "TTRT2", "TTRT3", "TTRT4");
        String taxTypeAddExpectedValue = "TTRT1;TTRT2;TTRT3;TTRT4";

        //preconditions
        deleteTaxTypeAssignmentForUAT(uuidLaw);

        //STEP 01: Sign in as the legal user
        //STEP 02: Go to source navigate
        //STEP 03: Change your view to 'all col' in the View Management UI
        //STEP 04: Filter for:
        // DEV: Content Set: Iowa (Development); Year: 2019; Rendition Status: PREP; Content Type: LAW; Doc Number: 307
        // UAT: Content Set: Iowa (Development); Year: 2018; Rendition Status: PREP; Content Type: LAW; Doc Number: 2303
        homePage().goToHomePage();
        loginPage().logIn();
        sourcePage().goToSourcePageWithRenditionUuids(uuidLaw);
        sourcePage().publicViewAllColsWithCdpAutoIntegration();
        //STEP 05: Click Rendition
        sourceNavigateGridPage().clickFirstItem();
        //STEP 06: Go to the Section tab.
        sourcePage().goToSectionTab();
        sourcePage().publicViewAllCols();
        //STEP 07: Click first Section
        sourceNavigateGridPage().clickFirstItem();
        //STEP 08: Go to the delta tab.
        sourcePage().goToDeltaTab();
        sourcePage().publicViewAllCols();
        //Step 09: Right click the first delta.
        sourceNavigateGridPage().rightClickFirstItem();
        //Step 10: Click Tax Type Add.
        Assertions.assertTrue(sectionContextMenu().editTaxTypeAdd(),
                "STEP 10: Tax Type Add window DOESN'T appear");
        boolean isAvailableTaxTypeAddsEmpty = taxTypeAddPage().isAvailableTaxTypeAddsEmpty();
        //STEP 11: Select TTRT1, TTRT2, TTRT3, and TTRT4
        for (String taxTypeTag : taxTypeTags)
        {
            taxTypeAddPage().selectAndMoveAvailableTaxTagAddByName(taxTypeTag);
        }
        //STEP 12: Click Save. Tax Type Add window closes.The Tax Type Add column shows TTRT1;TTRT2;TTRT3;TTRT4
        boolean isTaxTypeAddWindowExist = taxTypeAddPage().clickSave();
        taxTypeAddPage().waitForGridRefresh();
        String taxTypeAddDeltaActualValueAfterAdding = sourceNavigateGridPage().getFirstItemTaxTypeAddValue();
        //STEP 13: Go to the section tab. The Tax Type Add column doesn’t show TTRT1;TTRT2;TTRT3;TTRT4
        sourcePage().goToSectionTab();
        HashSet<String> taxTypeAddSectionActualValuesAfterAdding = new HashSet<>(sourceNavigateGridPage().getTaxTypeAddColumnValues());
        //STEP 14: Go to the rendition tab. The Tax Type Add column doesn’t show TTRT1;TTRT2;TTRT3;TTRT4
        sourcePage().goToRenditionTab();
        String taxTypeAddRenditionActualValueAfterAdding = sourceNavigateGridPage().getFirstItemTaxTypeAddValue();

        Assertions.assertAll("addingAndRemovingTaxTypeTagsDeltaLevelTest",
                () -> Assertions.assertFalse(isAvailableTaxTypeAddsEmpty,
                        "STEP 10: The Available Tax Type Adds list IS empty"),
                () -> Assertions.assertFalse(isTaxTypeAddWindowExist,
                        "STEP 12: Tax Type Add window DIDN'T close"),
                () -> Assertions.assertEquals(taxTypeAddExpectedValue, taxTypeAddDeltaActualValueAfterAdding,
                        String.format("STEP 12: Delta tab. The Tax Type Add column shows '%s' instead of '%s'", taxTypeAddDeltaActualValueAfterAdding, taxTypeAddExpectedValue)),
                () -> Assertions.assertEquals(1, taxTypeAddSectionActualValuesAfterAdding.size(),
                        "STEP 13: Section tab. The Tax Type Add status IS NOT the same for each row"),
                () -> Assertions.assertTrue(taxTypeAddSectionActualValuesAfterAdding.contains(EMPTY_STATUS),
                        String.format("STEP 13: Section tab. The Tax Type Add column shows '%s' instead of '%s'", taxTypeAddSectionActualValuesAfterAdding.toString(), EMPTY_STATUS)),
                () -> Assertions.assertEquals(EMPTY_STATUS, taxTypeAddRenditionActualValueAfterAdding,
                        String.format("STEP 14: Rendition tab. The Tax Type Add column shows '%s' instead of '%s'", taxTypeAddRenditionActualValueAfterAdding, taxTypeAddExpectedValue))
        );

    }

    /**
     * STORY/BUG - HALCYONST-9573 <br>
     * SUMMARY - Tax Type – Source Navigate Implementation
     * (Enabled context menu option, adding and removing tax type tags, and column check) (delta) <br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void addingTaxTypeTagsMultipleDeltaLevelTest()
    {
//        for UAT
        String uuidLaw = "ID3FFD3E2E88E11EA852AC4B447F6A479";
        List<String> taxTypeTags = Arrays.asList("TTRT1", "TTRT2", "TTRT3", "TTRT4");
        String taxTypeAddExpectedValue = "TTRT1;TTRT2;TTRT3;TTRT4";

        //preconditions
        deleteTaxTypeAssignmentForUAT(uuidLaw);

        //STEP 01: Sign in as the legal user
        //STEP 02: Go to source navigate
        //STEP 03: Change your view to 'all col' in the View Management UI
        //STEP 04: Filter for:
        // UAT: Content Set: Iowa (Development); Year: 2018; Rendition Status: PREP; Content Type: LAW; Doc Number: 2303
        homePage().goToHomePage();
        loginPage().logIn();
        sourcePage().goToSourcePageWithRenditionUuids(uuidLaw);
        sourcePage().publicViewAllColsWithCdpAutoIntegration();
        //STEP 05: Click Rendition
        sourceNavigateGridPage().clickFirstItem();
        //STEP 06: Go to the Section tab.
        sourcePage().goToSectionTab();
        sourcePage().publicViewAllCols();
        //STEP 07: Click first Section
        sourceNavigateGridPage().clickFirstItem();
        //STEP 08: Go to the delta tab.
        sourcePage().goToDeltaTab();
        sourcePage().publicViewAllCols();
        sourcePage().selectAllOnPage();
        sourceNavigateGridPage().rightClickFirstItem();
        sourcePage().publicViewAllCols();
        //Step 09: Right click the first delta.
        sourceNavigateGridPage().rightClickFirstItem();
        //Step 10: Click Tax Type Add.
        Assertions.assertTrue(sectionContextMenu().editTaxTypeAdd(),
                "STEP 10: Tax Type Add window DOESN'T appear");
        boolean isAvailableTaxTypeAddsEmpty = taxTypeAddPage().isAvailableTaxTypeAddsEmpty();
        //STEP 11: Select TTRT1, TTRT2, TTRT3, and TTRT4
        for (String taxTypeTag : taxTypeTags)
        {
            taxTypeAddPage().selectAndMoveAvailableTaxTagAddByName(taxTypeTag);
        }
        //STEP 12: Click Save. Tax Type Add window closes.The Tax Type Add column shows TTRT1;TTRT2;TTRT3;TTRT4
        boolean isTaxTypeAddWindowExist = taxTypeAddPage().clickSave();
        taxTypeAddPage().waitForGridRefresh();
        String taxTypeAddDeltaActualValueAfterAdding = sourceNavigateGridPage().getFirstItemTaxTypeAddValue();

        Assertions.assertAll("addingAndRemovingTaxTypeTagsDeltaLevelTest",
                () -> Assertions.assertFalse(isAvailableTaxTypeAddsEmpty,
                        "STEP 10: The Available Tax Type Adds list IS empty"),
                () -> Assertions.assertFalse(isTaxTypeAddWindowExist,
                        "STEP 12: Tax Type Add window DIDN'T close"),
                () -> Assertions.assertEquals(taxTypeAddExpectedValue, taxTypeAddDeltaActualValueAfterAdding,
                        String.format("STEP 12: Delta tab. The Tax Type Add column shows '%s' instead of '%s'", taxTypeAddDeltaActualValueAfterAdding, taxTypeAddExpectedValue))
        );

    }

    /**
     * STORY/BUG - HALCYONST-11150 <br>
     * SUMMARY - Tax Type – Remove 'Tax Type Add' from the menu in SourceNavigate in SectionGroup tab
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void taxTypeEnableForSectionGroupLevelTest()
    {
        String uuidLaw = "IBCE0FAC01F0111E98D18B00554385E22";
        String group = "group";

        //STEP 01: Sign in as the legal user
        //STEP 02: Go to source navigate
        //STEP 03: Change your view to 'all col' in the View Management UI
        //STEP 04: Filter for:
        // DEV: Content Set: Iowa (Development); Year: 2019; Rendition Status: PREP; Content Type: LAW; Doc Number: 307
        // UAT: Content Set: Iowa (Development); Year: 2018; Rendition Status: PREP; Content Type: LAW; Doc Number: 2303
        homePage().goToHomePage();
        loginPage().logIn();
        sourcePage().goToSourcePageWithRenditionUuids(uuidLaw);
        sourcePage().publicViewAllColsWithCdpAutoIntegration();
        //STEP 05: Right click Rendition
        sourceNavigateGridPage().rightClickFirstItem();
        //STEP 06: Go to modify -> section grouping
        renditionContextMenu().modifySectionGrouping();
        //STEP 07: Remove group if it exists
        sectionGroupingToolsPage().removeGroup(group);
        //STEP 08: Create a new group
        sectionGroupingToolsPage().createGroup(group);
        //STEP 09: Select the first two sections and move them to the new group
        sourceNavigateGridPage().selectSectionsInRange(1, 2);
        sourceNavigateGridPage().moveSelectedSectionsToGroup(group);
        //STEP 10: Apply and close
        sectionGroupingToolsPage().clickApplyAndClose();
        sourcePage().switchToWindow(SourceNavigatePageElements.PAGE_TITLE);
        homePage().goToHomePage();
        sourcePage().goToSourcePage();
        //STEP 11: Click Rendition
        sourceNavigateGridPage().clickFirstItem();
        //STEP 12: Go to the Section Group tab.
        sourcePage().goToSectionGroupTab();
        sourcePage().publicViewAllCols();
        //STEP 13: Right click to the new section group
        sourceNavigateGridPage().rightClickItemBySectionGroupName(group);
        //STEP 14: Click Tax Type Add
        boolean isEditTaxTypeAddExist = sourceNavigateContextMenu().doesEditTaxTypeAddExist();

        Assertions.assertAll("taxTypeEnableForSectionGroupLevelTest",
                () -> Assertions.assertFalse(isEditTaxTypeAddExist,
                        "STEP 07: Edit -> Tax Type Add IS NOT disabled for multiply sections"));
    }

    /**
     * STORY/BUG - HALCYONST-11150 <br>
     * SUMMARY - Tax Type – Remove 'Tax Type Add' from the menu in SourceNavigate in DeltaGroup tab
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void taxTypeEnableFoDeltaGroupLevelTest()
    {
        String uuidLaw = "IBCE0FAC01F0111E98D18B00554385E22";
        String group = "group";

        //STEP 01: Sign in as the legal user
        //STEP 02: Go to source navigate
        //STEP 03: Change your view to 'all col' in the View Management UI
        //STEP 04: Filter for:
        // DEV: Content Set: Iowa (Development); Year: 2019; Rendition Status: PREP; Content Type: LAW; Doc Number: 307
        // UAT: Content Set: Iowa (Development); Year: 2018; Rendition Status: PREP; Content Type: LAW; Doc Number: 2303
        homePage().goToHomePage();
        loginPage().logIn();
        sourcePage().goToSourcePageWithRenditionUuids(uuidLaw);
        sourcePage().publicViewAllColsWithCdpAutoIntegration();
        //STEP 05: Right click Rendition
        sourceNavigateGridPage().rightClickFirstItem();
        //STEP 06: Go to modify -> delta grouping
        renditionContextMenu().modifyDeltaGrouping();
        //STEP 07: Remove group if it exists
        deltaGroupingToolsPage().removeGroup(group);
        //STEP 08: Create a new group
        deltaGroupingToolsPage().createGroup(group);
        //STEP 09: Select the first two delta and move them to the new group
        sourceNavigateGridPage().selectSectionsInRange(1, 2);
        sourceNavigateGridPage().moveSelectedSectionsToGroup(group);
        //STEP 10: Apply and close
        deltaGroupingToolsPage().clickApplyAndClose();
        sourcePage().switchToWindow(SourceNavigatePageElements.PAGE_TITLE);
        homePage().goToHomePage();
        sourcePage().goToSourcePage();
        //STEP 11: Click Rendition
        sourceNavigateGridPage().clickFirstItem();
        //STEP 12: Go to the Delta Group tab.
        sourcePage().goToDeltaGroupTab();
        sourcePage().publicViewAllCols();
        //STEP 13: Right click to the new section group
        sourceNavigateGridPage().clickItemByDeltaGroupName(group);
        sourceNavigateGridPage().rightClickItemByDeltaGroupName(group);
        //STEP 14: Click Tax Type Add
        boolean isEditTaxTypeAddExist = sourceNavigateContextMenu().doesEditTaxTypeAddExist();

        Assertions.assertAll("taxTypeEnableFoDeltaGroupLevelTest",
                () -> Assertions.assertFalse(isEditTaxTypeAddExist,
                        "STEP 07: Edit -> Tax Type Add IS NOT disabled for multiply sections"));

    }

    public void deleteTaxTypeAssignmentForUAT(String renditionUUID)
    {
        Connection uatConnection = BaseDatabaseUtils.connectToDatabaseUAT();
        TaxTypeAssignmentDatabaseUtils.deleteTaxTypeAssignmentFromRenditionAndItsSectionsAndDeltas(uatConnection, renditionUUID);
        BaseDatabaseUtils.commit(uatConnection);
        BaseDatabaseUtils.disconnect(uatConnection);
    }
}
