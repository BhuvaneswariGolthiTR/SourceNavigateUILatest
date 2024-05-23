package com.thomsonreuters.codes.codesbench.quality.tests.tools;

import com.thomsonreuters.codes.codesbench.quality.pageelements.home.userpreferences.UserPropertiesPageElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.tools.StocknoteDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets;
import com.thomsonreuters.codes.codesbench.quality.utilities.files.XlsFileUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.Arrays;
import java.util.List;

public class StocknoteManagerTests extends TestService
{
	/**
	 * STORY - N/A <br>
	 * SUMMARY - Routes to the Stock Note Manager and exports the hotkeys to an excel file. That excel
	 * file is then saved using AutoIT. The test then verifies the exported hotkeys file has the correct
	 * hotkeys and they match up with the correct Stock Note name. <br>
	 * USER - Legal <br>
	 */
	@Test
	@IE_EDGE_MODE
	@LEGAL
	@LOG
	public void exportHotKeyToExcelLegalTest()
	{
		List<List<String>> stocknoteHotkeyAndNameList;
		Connection connection = BaseDatabaseUtils.connectToDatabaseUAT();
		int contentSetID = Integer.parseInt(ContentSets.IOWA_DEVELOPMENT.getCode());
		stocknoteHotkeyAndNameList = StocknoteDatabaseUtils.getHotKeysAndStocknoteNamesForContentSetID(connection, contentSetID);
		//stocknoteHotkeyAndNameList.forEach(System.out::println);
		BaseDatabaseUtils.disconnect(connection);

		String fileName;
		String approxDownloadTimeHHmmss;

		homePage().goToHomePage();
		loginPage().logIn();

		toolsMenu().goToStockNoteManager();
		approxDownloadTimeHHmmss = stocknoteManagerPage().exportHotkeysToExcel();
		stocknoteManagerPage().saveExcelFile();
		fileName = stocknoteManagerPage().getMostRecentDownloadedHotkeyFileName();
		boolean wasFileDownloadedWithinLastFiveMinutes = stocknoteManagerPage().wasFileDownloaded(fileName, approxDownloadTimeHHmmss);
		Assertions.assertTrue(wasFileDownloadedWithinLastFiveMinutes, "File was downloaded within past five minutes.");

		boolean dataMatches = XlsFileUtils.compareToExcelFile(fileName, stocknoteHotkeyAndNameList);
		Assertions.assertTrue(dataMatches, "The data from the downloaded hot key file matches the data in the UI");
	}

	/**
	 * STORY/BUG - N/A <br>
	 * SUMMARY - Goes to the Stock Note manager and tests the navigation. It tests the page buttons and
	 * verifies they work correctly. <br>
	 * USER - Legal <br>
	 */
	@Test
	@IE_EDGE_MODE
	@LEGAL
	@LOG
	public void navigationLegalTest()
	{
		homePage().goToHomePage();
		loginPage().logIn();

		toolsMenu().goToStockNoteManager();
		stocknoteManagerFiltersPage().setResultsPerPageTo50();
		int totalNumberOfPages = stocknoteManagerPage().getNumberOfPages();
		stocknoteManagerFiltersPage().goToNextPage();
		int currentPageNumber = stocknoteManagerPage().getCurrentPageNumber();
		Assertions.assertEquals(2, currentPageNumber, "The Stock Note manager should be on page two.");

		stocknoteManagerFiltersPage().goToLastPage();
		currentPageNumber = stocknoteManagerPage().getCurrentPageNumber();
		Assertions.assertEquals(totalNumberOfPages, currentPageNumber, "The Stock Note manager should be on the last page.");

		stocknoteManagerFiltersPage().goToPreviousPage();
		currentPageNumber = stocknoteManagerPage().getCurrentPageNumber();
		Assertions.assertEquals(totalNumberOfPages - 1, currentPageNumber, "The Stock Note manager should be on the second to last page.");

		stocknoteManagerFiltersPage().goToFirstPage();
		currentPageNumber = stocknoteManagerPage().getCurrentPageNumber();
		Assertions.assertEquals(1, currentPageNumber, "The Stock Note manager should be on the first page.");
	}

	/**
	 * STORY/BUG - N/A <br>
	 * SUMMARY - Verifies the sorting and filtering of the Stock Note manager. First applies a filter and
	 * verifies it is applied to the list. It also verifies the default sort is applied. Then sorts by
	 * date modified ascending and verifies the grid changes. After that, the clear sort and clear
	 * filters buttons functionality are tested. <br>
	 * USER - Legal <br>
	 */
	@Test
	@IE_EDGE_MODE
	@LEGAL
	@LOG
	public void filteringAndSortingLegalTest()
	{
		String stocknoteNameFilter = "cornerpiece";

		homePage().goToHomePage();
		loginPage().logIn();

		toolsMenu().goToStockNoteManager();
		List<String> originalStocknoteNames = stocknoteManagerGridPage().getStocknotesNames();
		stocknoteManagerPage().refresh();
		stocknoteManagerFiltersPage().setFilterStocknoteName(stocknoteNameFilter);
		stocknoteManagerPage().refresh();
		boolean stocknotesFilteredByName = stocknoteManagerGridPage().allStocknotesMatchNameFilter();
		Assertions.assertTrue(stocknotesFilteredByName, "There are the correct amount of Stock Note in the grid.");

		boolean stocknotesSortedByModifiedDateAscending = stocknoteManagerFiltersPage().stocknotesSortedByDateModifiedAscending();
		Assertions.assertFalse(stocknotesSortedByModifiedDateAscending, "The modified date column is sorted by ascending order.");

		stocknoteManagerFiltersPage().sortDateModifiedAscending();
		stocknotesSortedByModifiedDateAscending = stocknoteManagerFiltersPage().stocknotesSortedByDateModifiedAscending();
		Assertions.assertTrue(stocknotesSortedByModifiedDateAscending, "The modified date column is sorted by ascending order.");

		stocknoteManagerFiltersPage().clearSort();
		stocknotesSortedByModifiedDateAscending = stocknoteManagerFiltersPage().stocknotesSortedByDateModifiedAscending();
		stocknotesFilteredByName = stocknoteManagerGridPage().allStocknotesMatchNameFilter();
		Assertions.assertFalse(stocknotesSortedByModifiedDateAscending, "The modified date column is sorted by ascending order.");

		Assertions.assertTrue(stocknotesFilteredByName, "There are the correct amount of Stock Note in the grid with a specific name.");

		stocknoteManagerFiltersPage().clearAllFilters();
		List<String> currentStocknoteNames = stocknoteManagerGridPage().getStocknotesNames();
		Assertions.assertIterableEquals(originalStocknoteNames, currentStocknoteNames, "Original Stock Note are the same as current ones.");
	}

	/**
	 * STORY/BUG - N/A <br>
	 * SUMMARY - Goes to the Stock Note Manager and then goes to the third page. Then sets the hotkey on
	 * the first one without one and verifies it is set and it is shown in the grid. Right clicks on
	 * that Stock Note again and removes the hot key. Verifies it is removed from the grid. <br>
	 * USER - Legal <br>
	 */
	@Test
	@IE_EDGE_MODE
	@LEGAL
	@LOG
	public void assignAndRemoveAltHotKeyAssignmentLegalTest()
	{
		final String altZHotKeyString = "Alt-Z";
		final int pageNumber = 3;

		homePage().goToHomePage();
		loginPage().logIn();

		homeMenu().goToUserPreferencesProperties();

		propertiesPage().setDefaultRowsPerPageTo25();
		if(propertiesPage().isElementEnabled(UserPropertiesPageElements.saveButton)) //TODO: This is a workaround if the Default is already 25, This also however doesn't take into account if the save button just doesn't enable after actually switching
		{
			propertiesPage().clickSave();
		}
		toolsMenu().goToStockNoteManager();
		List<String> originalStocknoteNames = stocknoteManagerGridPage().getStocknotesNames();
		stocknoteManagerFiltersPage().goToNextPage();
		stocknoteManagerFiltersPage().goToNextPage();
		List<String> currentStocknoteNames = stocknoteManagerGridPage().getStocknotesNames();
		boolean currentPageContentMatchesFirstPageContent = originalStocknoteNames.containsAll(currentStocknoteNames);
		Assertions.assertFalse(currentPageContentMatchesFirstPageContent, "The current Stock Note page is not the first one.");

		int pageNumberAfterArrowClick = stocknoteManagerPage().getCurrentPageNumber();
		Assertions.assertEquals(pageNumber, pageNumberAfterArrowClick, "The grid did go to page three after clicking arrow twice.");

		stocknoteManagerGridPage().rightClickFirstStocknoteWithoutHotKey();
		stocknoteManagerContextMenu().hotKeySetup();
		stocknoteHotKeysPage().setAltAndChar("Z");
		boolean hotKeyMatches = stocknoteHotKeysPage().doesHotKeyMatch(altZHotKeyString);
		Assertions.assertTrue(hotKeyMatches, "Hot key is displayed correctly in the hot key setup window.");

		stocknoteHotKeysPage().saveAndIgnoreAlert();
		Assertions.assertEquals(pageNumber, stocknoteManagerPage().getCurrentPageNumber(), "The grid stayed on page 3 after saving the hot key.");

		Assertions.assertTrue(stocknoteManagerGridPage().isHotKeyInGrid(altZHotKeyString), "HotKey is shown on the current page.");

		stocknoteManagerGridPage().rightClickStocknoteWithHotKey(altZHotKeyString);
		stocknoteManagerContextMenu().hotKeySetup();
		stocknoteHotKeysPage().setAltAndChar(" ");
		stocknoteHotKeysPage().saveAndIgnoreAlert();
		Assertions.assertEquals(pageNumber, stocknoteManagerPage().getCurrentPageNumber(), "The grid stayed on page 3 after removing the hot key.");

		Assertions.assertFalse(stocknoteManagerGridPage().isHotKeyInGrid(altZHotKeyString), "HotKey is shown on the current page after removing it.");
	}

	/**
	 * STORY/BUG - N/A <br>
	 * SUMMARY - Goes to the Stock Note Manager and then goes to the third page. Then sets the hot key on
	 * the first one without one and verifies it is set and it is shown in the grid. Right clicks on
	 * that Stock Note again and removes the hot key. Verifies it is removed from the grid. <br>
	 * USER - Legal <br>
	 */
	@Test
	@IE_EDGE_MODE
	@LEGAL
	@LOG
	public void assignAndRemoveShiftAltHotKeyAssignmentLegalTest()
	{
		final String shiftAltZHotKeyString = "Shift-Alt-Z";
		final int pageNumber = 3;

		homePage().goToHomePage();
		loginPage().logIn();

		homeMenu().goToUserPreferencesProperties();
		propertiesPage().setDefaultRowsPerPageTo25();
		if(propertiesPage().isElementEnabled(UserPropertiesPageElements.saveButton)) //TODO: This is a workaround if the Default is already 25, This also however doesn't take into account if the save button just doesn't enable after actually switching
		{
			propertiesPage().clickSave();
		}
		toolsMenu().goToStockNoteManager();
		List<String> originalStocknoteNames = stocknoteManagerGridPage().getStocknotesNames();
		stocknoteManagerFiltersPage().goToNextPage();
		stocknoteManagerFiltersPage().goToNextPage();
		List<String> currentStocknoteNames = stocknoteManagerGridPage().getStocknotesNames();
		boolean currentPageContentMatchesFirstPageContent = originalStocknoteNames.containsAll(currentStocknoteNames);
		Assertions.assertFalse(currentPageContentMatchesFirstPageContent, "The current Stock Note page content is the same as the first pages content.");

		int pageNumberAfterArrowClick = stocknoteManagerPage().getCurrentPageNumber();
		Assertions.assertEquals(pageNumber, pageNumberAfterArrowClick, "The grid did go to page three after clicking arrow twice.");

		stocknoteManagerGridPage().rightClickFirstStocknoteWithoutHotKey();
		stocknoteManagerContextMenu().hotKeySetup();
		stocknoteHotKeysPage().setShiftAltAndChar("Z");
		boolean hotKeyMatches = stocknoteHotKeysPage().doesHotKeyMatch(shiftAltZHotKeyString);
		Assertions.assertTrue(hotKeyMatches, "Hot key is displayed correctly in the hot key setup window.");

		stocknoteHotKeysPage().saveAndIgnoreAlert();
		Assertions.assertEquals(stocknoteManagerPage().getCurrentPageNumber(), pageNumber, "The grid stayed on page 3 after saving the hot key.");

		Assertions.assertTrue(stocknoteManagerGridPage().isHotKeyInGrid(shiftAltZHotKeyString), "HotKey is shown on the current page.");

		stocknoteManagerGridPage().rightClickStocknoteWithHotKey(shiftAltZHotKeyString);
		stocknoteManagerContextMenu().hotKeySetup();
		stocknoteHotKeysPage().setShiftAltAndChar(" ");
		stocknoteHotKeysPage().saveAndIgnoreAlert();
		Assertions.assertEquals(stocknoteManagerPage().getCurrentPageNumber(), pageNumber, "The grid stayed on page 3 after removing the hot key.");

		Assertions.assertFalse(stocknoteManagerGridPage().isHotKeyInGrid(shiftAltZHotKeyString), "HotKey is shown on the current page after removing it.");
	}

	/**
	 * STORY/BUG - N/A <br>
	 * SUMMARY - Routes to the Stock Note Manager. Right clicks the first Stock Note without a hot key and
	 * opens the hot key setup. It sets Alt-Z hot key then clicks cancel. Checks that the Stock Note
	 * manager stays on the same page and the hot key is not in the grid. <br>
	 * USER - Legal <br>
	 */
	@Test
	@IE_EDGE_MODE
	@LEGAL
	@LOG
	public void cancelHotKeyAssignmentLegalTest()
	{
		final String altZHotKeyString = "Alt-Z";

		homePage().goToHomePage();
		loginPage().logIn();

		toolsMenu().goToStockNoteManager();
		List<String> originalStocknoteNames = stocknoteManagerGridPage().getStocknotesNames();
		stocknoteManagerGridPage().rightClickFirstStocknoteWithoutHotKey();
		stocknoteManagerContextMenu().hotKeySetup();
		stocknoteHotKeysPage().setAltAndChar("Z");
		boolean hotKeyMatches = stocknoteHotKeysPage().doesHotKeyMatch(altZHotKeyString);
		Assertions.assertTrue(hotKeyMatches, "Hot key is displayed correctly in the hot key setup window.");

		stocknoteHotKeysPage().cancel();
		boolean hotKeyIsInGrid = stocknoteManagerGridPage().isHotKeyInGrid(altZHotKeyString);
		List<String> currentStocknoteNames = stocknoteManagerGridPage().getStocknotesNames();
		Assertions.assertIterableEquals(originalStocknoteNames, currentStocknoteNames, "The Stock Note Manager is on the same page after canceling.");

		Assertions.assertFalse(hotKeyIsInGrid, "Alt-Z hotkey is in the grid.");
	}

	/**
	 * STORY/BUG - N/A <br>
	 * SUMMARY - Routes to Stock Note Manager and tests invalid hot key combinations including alt-ZA,
	 * alt-%, and shift-alt-%. Verifies the hot keys with % have an alert when trying to save. <br>
	 * USER - Legal <br>
	 */
	@Test
	@IE_EDGE_MODE
	@LEGAL
	@LOG
	public void invalidHotKeyCombinationLegalTest()
	{
		final String altZHotKeyString = "Alt-Z";
		final String altPercentHotKeyString = "Alt-%";
		final String shiftAltPercentHotKeyString = "Shift-Alt-%";
		final String shiftAltCHotKeyString = "Shift-Alt-C";
		final String expectedAlertText = "The provided hotkey combination is not valid.";

		homePage().goToHomePage();
		loginPage().logIn();

		toolsMenu().goToStockNoteManager();
		stocknoteManagerGridPage().rightClickFirstStocknoteWithoutHotKey();
		stocknoteManagerContextMenu().hotKeySetup();
		stocknoteHotKeysPage().setAltAndChar("ZA");
		Assertions.assertTrue(stocknoteHotKeysPage().doesHotKeyMatch(altZHotKeyString), "Hot key is displayed correctly in the hot key setup window.");

		stocknoteHotKeysPage().setAltAndChar("%");
		Assertions.assertTrue(stocknoteHotKeysPage().doesHotKeyMatch(altPercentHotKeyString), "Alt-% hot key is displayed correctly in the hot key setup window.");
		Assertions.assertTrue(stocknoteHotKeysPage().saveAndCheckAlertInvalidHotKey(expectedAlertText), "Alert text is correct when trying to save Alt-% hot key.");

		stocknoteHotKeysPage().switchToStocknoteHotKeySetupPage();
		stocknoteHotKeysPage().enterTheInnerFrame();
		stocknoteHotKeysPage().selectShiftAlt();
		Assertions.assertTrue(stocknoteHotKeysPage().doesHotKeyMatch(shiftAltPercentHotKeyString), "Shift-Alt-% hot key is displayed correctly in the hot key setup window.");
		Assertions.assertTrue(stocknoteHotKeysPage().saveAndCheckAlertInvalidHotKey(expectedAlertText), "Alert text is correct when trying to save Shift-Alt-% hot key.");

		stocknoteHotKeysPage().switchToStocknoteHotKeySetupPage();
		stocknoteHotKeysPage().enterTheInnerFrame();
		stocknoteHotKeysPage().setKey("C");
		Assertions.assertTrue(stocknoteHotKeysPage().doesHotKeyMatch(shiftAltCHotKeyString), "Shift-Alt-C hot key is displayed correctly in the hot key setup window.");
		Assertions.assertTrue(stocknoteHotKeysPage().saveAndCheckAlertInvalidHotKey(expectedAlertText), "Alert text is correct when trying to save Shift-Alt-C hot key.");

		stocknoteHotKeysPage().switchToStocknoteHotKeySetupPage();
		stocknoteHotKeysPage().enterTheInnerFrame();
		stocknoteHotKeysPage().cancel();
		Assertions.assertFalse(stocknoteManagerGridPage().isHotKeyInGrid(shiftAltCHotKeyString), "Shift-Alt-C hotkey is in the grid.");
	}

	/**
	 * STORY/BUG - N/A <br>
	 * SUMMARY - Routes to the Stock Note Manager and verifies a list of hard-coded hot keys cannot be
	 * set for the Stock Notes. <br>
	 * USER - Legal <br>
	 */
	@Test
	@IE_EDGE_MODE
	@LEGAL
	@LOG
	public void hardCodedHotKeysLegalTest()
	{
		List<String> keys = Arrays.asList("F", "I", "P", "1", "2", "3", "4", "5", "6", "7", "8", "9", "0");
		String expectedShiftAltAlertText = "Shift-Alt-T is already used for a different project.";
		String expectedAltAlertText = "This alt key function is currently in use.";

		homePage().goToHomePage();
		loginPage().logIn();

		toolsMenu().goToStockNoteManager();
		stocknoteManagerGridPage().rightClickFirstStocknoteWithoutHotKey();
		stocknoteManagerContextMenu().hotKeySetup();
		stocknoteHotKeysPage().setShiftAltAndChar("T");
		Assertions.assertTrue(stocknoteHotKeysPage().saveAndCheckAlertInvalidHotKey(expectedShiftAltAlertText), "Alert was thrown for shift-alt-T");

		stocknoteHotKeysPage().enterTheInnerFrame();
		stocknoteHotKeysPage().selectAlt();
		keys.forEach(key ->
		{
			stocknoteHotKeysPage().setKey(key);
			Assertions.assertTrue(stocknoteHotKeysPage().saveAndCheckAlertInvalidHotKey(expectedAltAlertText), "Alert was thrown for alt-" + key);
			stocknoteHotKeysPage().enterTheInnerFrame();
		});
	}

	/**
	 * STORY/BUG - N/A <br>
	 * SUMMARY - Routes to the Stock Note Manager. Goes to the third page and opens the properties of the
	 * first Stock Note. Checks the pin to top option and saves. Goes back to the first page and verifies
	 * the Stock Note is on the first page. Then edits it again and unchecks pin to top and verifies it
	 * is the first Stock Note on the third page. <br>
	 * USER - Legal <br>
	 */
	@Test
	@IE_EDGE_MODE
	@LEGAL
	@LOG
	public void pinnedToTheTopLegalTest()
	{
		homePage().goToHomePage();
		loginPage().logIn();

		homeMenu().goToUserPreferencesProperties();
		propertiesPage().setDefaultRowsPerPageTo25();
		if(propertiesPage().isElementEnabled(UserPropertiesPageElements.saveButton)) //TODO: This is a workaround if the Default is already 25, This also however doesn't take into account if the save button just doesn't enable after actually switching
		{
			propertiesPage().clickSave();
		}
		toolsMenu().goToStockNoteManager();
		stocknoteManagerFiltersPage().goToNextPage();
		stocknoteManagerFiltersPage().goToNextPage();
		String stocknoteName = stocknoteManagerGridPage().getFirstStocknoteName();
		stocknoteManagerGridPage().rightClickFirstStocknote();
		stocknoteManagerContextMenu().properties();
		stocknotePropertiesPage().checkPinToTop();
		stocknotePropertiesPage().save();
		stocknoteManagerFiltersPage().goToFirstPage();
		Assertions.assertTrue(stocknoteManagerGridPage().isStocknoteInGrid(stocknoteName), stocknoteName + " is on the first page.");

		stocknoteManagerGridPage().rightClickStocknote(stocknoteName);
		stocknoteManagerContextMenu().properties();
		stocknotePropertiesPage().uncheckPinToTop();
		stocknotePropertiesPage().save();
		Assertions.assertFalse(stocknoteManagerGridPage().isStocknoteInGrid(stocknoteName), stocknoteName + " is on the first page.");

		stocknoteManagerFiltersPage().goToNextPage();
		stocknoteManagerFiltersPage().goToNextPage();
		Assertions.assertEquals(stocknoteName, stocknoteManagerGridPage().getFirstStocknoteName(), stocknoteName + " should be the first in the grid.");

		stocknoteManagerPage().close();
		propertiesPage().setDefaultRowsPerPageTo500();
		propertiesPage().clickSave();
	}
}
