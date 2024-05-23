package com.thomsonreuters.codes.codesbench.quality.tests.nod;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.thomsonreuters.codes.codesbench.quality.utilities.autoIT.AutoITUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.thomsonreuters.codes.codesbench.quality.pages.table.TableTestingPage.SubscribedCasesColumns;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;

public class HeadnotesTests extends TestService 
{
	/**
	 * STORY/BUG - HALCYONST-1569 <br>
	 * SUMMARY - Tests general aspects and features of NOD Headnotes such as the quickfind, ignore/unignore, and bluelines<br>
	 * USER - Legal <br>
	 */
	@Test
	@LEGAL
	@LOG
	public void nodHeadnotesTest()
	{
		homePage().goToHomePage();
		loginPage().logIn();

		boolean subscribedCasesWindowAppeared = nodMenu().goToSubscribedCases();
		Assertions.assertTrue(subscribedCasesWindowAppeared, "Subscribed Cases Window did not appear");

		String firstCaseSerialNumber = subscribedCasesGridPage().getCaseSerialNumberFromSubscribedCasesByGivenRow(1);
		boolean headnotesWindowAppeared = subscribedCasesGridPage().clickCaseSerialNumberLink(firstCaseSerialNumber);
		Assertions.assertTrue(headnotesWindowAppeared, "Headnotes window did not appear.");

		headnotesPage().clickUnignoreAllHeadnotesButton();
		headnotesPage().ignoreHeadnoteByGivenRow(1);
		boolean headnoteWasIgnored = headnotesPage().isHeadnoteInGivenRowIgnored(1);
		headnotesPage().unignoreHeadnoteByGivenRow(1);
		boolean headnoteWasUnignored = headnotesPage().isHeadnoteInGivenRowUnignored(1);

		headnotesSearchPage().setQuickFindField("a I s 999999");
		headnotesSearchPage().clickQuickFindButton();
		Assertions.assertTrue(AutoITUtils.verifyAlertTextAndAccept(true, "0 records found"), "Alert should appear saying no records found");
		Assertions.assertFalse(headnotesPage().doesHighlightedItemExist(), "No item should be highlighted");

		/*
		 * The value below should result in Constitution Article I Section 1 BL 1,
		 * so we would expected to see that BL 1 highlighted as a result
		 */
		headnotesSearchPage().setQuickFindField("a I s 1 1");
		headnotesSearchPage().clickQuickFindButton();
		boolean treeControlGoesToBlueLine = headnotesTreePage().isNodeHighlightedInTree("BL 1");
		Assertions.assertTrue(treeControlGoesToBlueLine, "The searched for blueline appeared selected in the tree");

		boolean selectedBlueLineFieldPopulatesWithFoundBlueLine = headnotesTreePage().selectedBluelineIsFound();
		Assertions.assertTrue(selectedBlueLineFieldPopulatesWithFoundBlueLine, "The searched for blueline populated the Selected Blueline field");

		boolean findPageAppeared = headnotesSearchPage().clickKeywordFind();
		Assertions.assertTrue(findPageAppeared, "Find Page did not appear for multiple results");
		findPage().setFirstKeywordDropdown("SECTION");
		findPage().setFirstKeywordValue("2");
		findPage().setSecondKeywordDropdown("");
		
		//ENDED HERE ON CODE REVIEW
		findPage().clickSearch();
		findPage().clickFirstFindResult();
		headnotesWindowAppeared = headnotesPage().switchToHeadnotesPage();
		Assertions.assertTrue(headnotesWindowAppeared, "Headnotes Page did not appear");

		boolean treeControlGoesToSection = headnotesTreePage().isNodeHighlightedInTree("2");

		headnotesSearchPage().setQuickFindField("321A.1");
		headnotesSearchPage().clickQuickFindButton();
		boolean treeControlGoesToNode = headnotesTreePage().isNodeHighlightedInTree("321A.1");

		headnotesSearchPage().setQuickFindField("633.264 308");
		headnotesSearchPage().clickQuickFindButton();
		boolean treeControlGoesToNewBlueLine = headnotesTreePage().isNodeHighlightedInTree("BL 308");
		headnotesTreePage().rightClickCurrentlyHighlightedNode();
		headnotesContextMenu().bluelineAnalysisView();

		headnotesWindowAppeared = bluelineAnalysisPage().clickBluelineWithGivenText("In general");
		Assertions.assertTrue(headnotesWindowAppeared, "Headnotes window did not appear when it should have.");

		boolean treeControlGoesToNewNewBlueLine = headnotesTreePage().isNodeHighlightedInTree("In general");

		boolean headnoteDetailsWindowAppeared = headnotesTreePage().clickHeadnoteNumber(1);
		Assertions.assertTrue(headnoteDetailsWindowAppeared, "Headnotes Details window did not appear");

		headnotesWindowAppeared = headnotesDetailsPage().clickCancel();
		Assertions.assertTrue(headnotesWindowAppeared, "Headnotes window did not appear.");
		headnotesPage().ignoreHeadnoteByGivenRow(1);
		boolean headnoteWasIgnored2 = headnotesPage().isHeadnoteInGivenRowIgnored(1);
		boolean completedDateIsEmpty = headnotesPage().isCompletedDateFieldEmpty();
		headnotesPage().clickIgnoreAllHeadnotesButton();
		boolean completedDateIsCurrentDate = headnotesPage().compareCompletedDateToCurrentDate();

		headnotesPage().unignoreHeadnoteByGivenRow(1);
		boolean headnoteWasUnignored2 = headnotesPage().isHeadnoteInGivenRowIgnored(1);
		boolean completedDateIsEmpty2 = headnotesPage().isCompletedDateFieldEmpty();
		headnotesPage().ignoreHeadnoteByGivenRow(1);
		boolean headnoteWasIgnored3 = headnotesPage().isHeadnoteInGivenRowIgnored(1);
		boolean completedDateIsEmpty3 = headnotesPage().isCompletedDateFieldEmpty();
		headnotesPage().clickCompletedByAndDateButton();
		boolean subscribedCasesWindowAppeared2 = subscribedCasesPage().switchToSubscribedCasesPage();
		Assertions.assertTrue(subscribedCasesWindowAppeared2);

		subscribedCasesPage().findCase(firstCaseSerialNumber);
		headnoteDetailsWindowAppeared = subscribedCasesGridPage().clickCaseSerialNumberLink(firstCaseSerialNumber);
		Assertions.assertTrue(headnoteDetailsWindowAppeared, "Headnotes window did not appear.");
		headnotesPage().clickUnignoreAllHeadnotesButton();
		boolean completedDateIsEmpty4 = headnotesPage().isCompletedDateFieldEmpty();

		Assertions.assertAll
		(		
				() -> Assertions.assertTrue(subscribedCasesWindowAppeared, "The Subscribed Cases window appeared"),
				() -> Assertions.assertTrue(headnoteWasIgnored, "Ignoring the headnote was a success"),
				() -> Assertions.assertTrue(headnoteWasUnignored, "Unignoring the headnote was a success"),
				() -> Assertions.assertTrue(treeControlGoesToSection, "The searched for section appeared selected in the tree"),
				() -> Assertions.assertTrue(treeControlGoesToNode, "The searched for node appeared selected in the tree"),
				() -> Assertions.assertTrue(treeControlGoesToNewBlueLine, "The searched for blueline appeared selected in the tree"),
				() -> Assertions.assertTrue(treeControlGoesToNewNewBlueLine, "The searched for blueline appeared selected in the tree"),
				() -> Assertions.assertTrue(headnoteWasIgnored2, "Ignoring the headnote a second time was a success"),
				() -> Assertions.assertTrue(completedDateIsEmpty, "The Completed Date field was empty after ignoring the headnote"),
				() -> Assertions.assertTrue(completedDateIsCurrentDate, "The Completed Date field was the current date after ignoring all headnotes"),
				() -> Assertions.assertTrue(headnoteWasUnignored2, "Unignoring the headnote a second time was a success"),
				() -> Assertions.assertTrue(completedDateIsEmpty2, "The Completed Date field was empty after unignoring the headnote"),
				() -> Assertions.assertTrue(headnoteWasIgnored3, "Ignoring the headnote a third time was a success"),
				() -> Assertions.assertTrue(completedDateIsEmpty3, "The Completed Date field was empty after ignoring the headnote"),
				() -> Assertions.assertTrue(subscribedCasesWindowAppeared2, "The Subscribed Cases window appeared a second time"),
				() -> Assertions.assertTrue(completedDateIsEmpty4, "The Completed Date field was empty after unignoring all headnotes")
		);
	}

	/**
	 * STORY/BUGS - HALCYONST-1469 &lt;br&gt;
	 * SUMMARY -  Tests that specific buttons do and do not appear in the subscribed cases page&lt;br&gt;
	 * USER -  Legal &lt;br&gt;
	 */
	@Test
	@LEGAL
	@LOG
	public void headnotesButtonsTest()
	{
		homePage().goToHomePage();
		loginPage().logIn();

		boolean subscribedCasesWindowAppeared = nodMenu().goToSubscribedCases();
		Assertions.assertTrue(subscribedCasesWindowAppeared, "Subscribed Cases Window did not appear");

		String firstCaseSerialNumber = subscribedCasesGridPage().getCaseSerialNumberFromSubscribedCasesByGivenRow(1);
		boolean headnotesWindowAppeared = subscribedCasesGridPage().clickCaseSerialNumberLink(firstCaseSerialNumber);
		Assertions.assertTrue(headnotesWindowAppeared, "Headnotes window did not appear");

		//Verify: We don�t see a �Refresh Reporter�, �Completed By� buttons
		boolean refreshReporterButtonIsNotExist = headnotesPage().doRefreshReporterButtonsExist();
		boolean completedByButtonIsNotExist = headnotesPage().doCompletedByButtonsExist();

		//Verify: We see, pretty much in this order, �Previous Case�, �Next Case�, �Unignore All�, �Ignore All�, �Completed By and Date�, and �Sign Out Case� buttons
		boolean previousCaseButtonExists = headnotesPage().doesPreviousCaseButtonExist();
		boolean nextCaseButtonExists = headnotesPage().doesNextCaseButtonExist();
		boolean unignoreAllButtonExists = headnotesPage().doesUnignoreAllButtonExist();
		boolean ignoreAllButtonExists = headnotesPage().doesIgnoreAllButtonExist();
		boolean completedByAndDateButtonExists = headnotesPage().doesCompletedByAndDateButtonExist();
		boolean signOutCaseButtonExists = headnotesPage().doesSignOutCaseButtonExist();

		Assertions.assertAll
		(
				() -> Assertions.assertTrue(subscribedCasesWindowAppeared, "The Subscribed Cases window should appeare"),
				() -> Assertions.assertFalse(refreshReporterButtonIsNotExist, "Refresh Reporter button should not exist "),
				() -> Assertions.assertFalse(completedByButtonIsNotExist, "Completed By button should not exist "),
				() -> Assertions.assertTrue(previousCaseButtonExists, "Previous Case button should be displayed"),
				() -> Assertions.assertTrue(nextCaseButtonExists, "Next Case button should be displayed"),
				() -> Assertions.assertTrue(unignoreAllButtonExists, "Unignore All button should be displayed"),
				() -> Assertions.assertTrue(ignoreAllButtonExists, "Ignore All button should be displayed"),
				() -> Assertions.assertTrue(completedByAndDateButtonExists, "Completed By and Date button should be displayed"),
				() -> Assertions.assertTrue(signOutCaseButtonExists, "Sign Out Case button should be displayed")
		);
	}

	/**
	 * STORY/BUGS - HALCYONST-1470 &lt;br&gt;
	 * SUMMARY -  Tests to see if the Previous Case and Next Case buttons are disabled when there are no cases before or after the current case&lt;br&gt;
	 * USER - legal &lt;br&gt;
	 */
	@Test
	@LEGAL
	@LOG
	public void headnotesNextAndPreviousCasesTest() 
	{
		homePage().goToHomePage();
		loginPage().logIn();

		boolean subscribedCasesWindowAppeared = nodMenu().goToSubscribedCases();
		Assertions.assertTrue(subscribedCasesWindowAppeared, "Subscribed Cases Window did not appear");

		List<String> caseSerialNumbers = tableTestingPage().getColumnValues(SubscribedCasesColumns.CASE_SERIAL_NUMBER);
		boolean headnotesWindowAppeared = subscribedCasesGridPage().clickCaseSerialNumberLink(caseSerialNumbers.get(0));
		Assertions.assertTrue(headnotesWindowAppeared, "Headnotes window did not appear");

		boolean previousCaseButtonIsDisabled = headnotesPage().isPreviousCaseButtonDisabled();

		headnotesPage().clickNextCaseButton();

		boolean secondCaseOpened = headnotesPage().isCaseOpen(caseSerialNumbers.get(1));
		boolean previousCaseButtonIsEnabled = !headnotesPage().isPreviousCaseButtonDisabled();

		headnotesPage().clickPreviousCaseButton();

		boolean firstCaseOpened = headnotesPage().isCaseOpen(caseSerialNumbers.get(0));

		headnotesPage().clickSubscribedCasesBreadcrumb();
		headnotesWindowAppeared = subscribedCasesGridPage().clickCaseSerialNumberLink(caseSerialNumbers.get(caseSerialNumbers.size()-1));
		Assertions.assertTrue(headnotesWindowAppeared, "Headnotes window did not appear");
		boolean nextCaseButtonIsDisabled = headnotesPage().isNextCaseButtonDisabled(); 

		headnotesPage().clickPreviousCaseButton();
		boolean preLastCaseOpened = headnotesPage().isCaseOpen(caseSerialNumbers.get(caseSerialNumbers.size()-2));
		headnotesPage().clickNextCaseButton();
		boolean lastCaseOpened = headnotesPage().isCaseOpen(caseSerialNumbers.get(caseSerialNumbers.size()-1));

		Assertions.assertAll
		(
				() -> Assertions.assertTrue(subscribedCasesWindowAppeared, "The Subscribed Cases window should have appeared"),
				() -> Assertions.assertTrue(previousCaseButtonIsDisabled, "'Previous Case' button should be disabled"),
				() -> Assertions.assertTrue(secondCaseOpened, "The second case should be opened"),
				() -> Assertions.assertTrue(previousCaseButtonIsEnabled, "'Previous Case' button should be enabled"),
				() -> Assertions.assertTrue(firstCaseOpened, "The first case should be opened"),
				() -> Assertions.assertTrue(nextCaseButtonIsDisabled, "'Next Case' button should be disabled"),
				() -> Assertions.assertTrue(preLastCaseOpened, "pre-Last case should be opened"),
				() -> Assertions.assertTrue(lastCaseOpened, "last case should be opened")
		);
	}

	/**
	 * STORY/BUGS - HALCYONST-1471 &lt;br&gt;
	 * SUMMARY -  Tests the sign-out and completed by feature of the the headnotes page&lt;br&gt;
	 * USER -  Legal &lt;br&gt;
	 */
	@Test
	@LEGAL
	@LOG
	public void headnotesSignOutCaseAndCompletedByAndDateTest()
	{	
		homePage().goToHomePage();
		loginPage().logIn();

		boolean subscribedCasesWindowAppeared = nodMenu().goToSubscribedCases();
		Assertions.assertTrue(subscribedCasesWindowAppeared, "Subscribed Cases Window did not appear");

		//Grab the first case serial number and open it
		String firstCaseSerialNumber = subscribedCasesGridPage().getCaseSerialNumberFromSubscribedCasesByGivenRow(1);
		boolean headnotesWindowAppeared = subscribedCasesGridPage().clickCaseSerialNumberLink(firstCaseSerialNumber);
		Assertions.assertTrue(headnotesWindowAppeared, "Headnotes window did not appear");

		//Make sure that the Signed Out By field is currently empty
		DateAndTimeUtils.takeNap(DateAndTimeUtils.TEN_SECONDS);
		headnotesPage().clearCaseSignOut();
		boolean signedOutByIsEmpty = "".equals(headnotesPage().getSignedOutByText());
		Assertions.assertTrue(signedOutByIsEmpty, "Signed Out By field should be empty");

		//Make sure all headnotes on the page are not classified or Ignored
		headnotesPage().clickUnignoreAllHeadnotesButton();
		headnotesPage().removeClassificationAllHeadnotes();
		headnotesPage().clickSignOutCaseButton();

		//Verify: Signed Out By field is updated to the legal user�s initials �GG�
		boolean isSignedOutByUser = user().getSubscribedCasesInitials().equals(headnotesPage().getSignedOutByText());
		Assertions.assertTrue(isSignedOutByUser, "Signed Out By field should be current users intials");

		//Completed By and Date (with out ignored cases)
		headnotesPage().clickCompletedByAndDateButton();
		boolean alertExist = AutoITUtils.verifyAlertTextAndAccept(true, "All headnotes must have either been classified or ignored before the case can be marked as completed.","Alert should appear saying no records found");

		//Ignore All and check
		headnotesPage().clickIgnoreAllHeadnotesButton();
		boolean allHeadnotesAreIgnored = headnotesPage().AreAllHeadnotesIgnored();

		//Completed By and Date (with ignored headnotes)
		headnotesPage().clickCompletedByAndDateButton();
		subscribedCasesWindowAppeared = subscribedCasesPage().switchToSubscribedCasesPage();
		Assertions.assertTrue(subscribedCasesWindowAppeared, "Subscribed cases window did not appear");
		subscribedCasesPage().filterForSerialNumber(firstCaseSerialNumber);

		//Verify: In the subscribed cases grid, our case serial number from earlier shows it was Completed By the legal user and the Completed Date is the current date
		String currentDate = DateAndTimeUtils.getCurrentDateMMddyyyy();
		boolean completedByIsCorrect = headnotesTablePage().isCompletedByCorrect();
		boolean completedDateIsCorrect = headnotesTablePage().isCompletedDateCorrect(currentDate);

		//Open the case serial number from earlier and verify: The Completed Date shows the current date and all headnotes still have an Unignore button under the Classification column
		subscribedCasesGridPage().clickCaseSerialNumberLink(firstCaseSerialNumber);
		boolean completedDateIsCorrect2 = currentDate.equals( headnotesPage().getCompletedDateText());
		boolean allHeadnotesAreStillIgnored = headnotesPage().AreAllHeadnotesIgnored();

		//Click Unignore All and Verify: The Completed Date is now empty and all headnotes no longer have an Unignore button under the Classification column.
		headnotesPage().clickUnignoreAllHeadnotesButton();
		boolean completedDateIsNotEmpty = "".equals( headnotesPage().getCompletedDateText());
		boolean allHeadnotesAreNotIgnored = headnotesPage().AreAllHeadnotesNotIgnored();

		//Go back to the Subscribed Cases window
		//Verify: In the subscribed cases grid, our case serial number from earlier no longer shows it was Completed By the legal user and the Completed Date is empty
		headnotesPage().clickSubscribedCasesBreadcrumb();
		boolean completedByIsEmpty = "".equals( tableTestingPage().getCellValue(SubscribedCasesColumns.COMPLETED_BY, 0) );
		boolean completedDateIsEmpty = "".equals( tableTestingPage().getCellValue(SubscribedCasesColumns.COMPLETED_DATE, 0) );

		Assertions.assertAll
		(
				() -> Assertions.assertTrue(alertExist, "Should appears the allert telling the user 'All headnotes must have either been classified or ignored before the case can be marked as compelted'"),
				() -> Assertions.assertTrue(allHeadnotesAreIgnored, "All headnotes should have an Unignore button under the Classification column"),
				() -> Assertions.assertTrue(completedByIsCorrect, "The Case should be completed by current user"),
				() -> Assertions.assertTrue(completedDateIsCorrect, "Completed Date should be the current date"),
				() -> Assertions.assertTrue(completedDateIsCorrect2, "Completed Date in Headnote page should be the current date"),
				() -> Assertions.assertTrue(allHeadnotesAreStillIgnored, "All headnotes should have an Unignore button under the Classification column"),
				() -> Assertions.assertTrue(completedDateIsNotEmpty, "The Completed Date should be empty"),
				() -> Assertions.assertTrue(allHeadnotesAreNotIgnored, "All headnotes should shouldn't be ignored"),
				() -> Assertions.assertTrue(completedByIsEmpty, "The Case's field 'Completed by' should be empty"),
				() -> Assertions.assertTrue(completedDateIsEmpty, "The Case's field 'Completed Date' should be empty")
		);
	}

	/**
	 * STORY/BUGS - HALCYONST-1474 &lt;br&gt;
	 * SUMMARY -  Tests that fields that features in the Subscribed Cases page that are expandable and collapsable work as intended.&lt;br&gt;
	 * USER - Legal &lt;br&gt;
	 *
	 */
	@Test
	@LEGAL
	@LOG
	public void expandableAndCollapsibleFieldsInHeadnotesTest()
	{
		homePage().goToHomePage();
		loginPage().logIn();

		boolean subscribedCasesWindowAppeared = nodMenu().goToSubscribedCases();
		Assertions.assertTrue(subscribedCasesWindowAppeared, "Subscribed Cases Window did not appear");

		//Take the first subscribed case number and open that subscribed case
		String firstCaseSerialNumber = subscribedCasesGridPage().getCaseSerialNumberFromSubscribedCasesByGivenRow(1);
		boolean headnotesWindowAppeared = subscribedCasesGridPage().clickCaseSerialNumberLink(firstCaseSerialNumber);
		Assertions.assertTrue(headnotesWindowAppeared, "The Headnotes page did not appear as expected");

		//Verify: the �Synopsis Background�, �Synopsis Holdings�, and �Notes� sections have expandable and collapsible buttons
		boolean synopsisBackgroundCollapsButtonIsExist = headnotesPage().doesSynopsisBackgroundCollapsibleExist();
		boolean synopsisHoldingsCollapsButtonIsExist = headnotesPage().doesSynopsisHoldingCollapsibleExist();
		boolean notesCollapsButtonIsExist = headnotesPage().doesNotesCollapsibleExist();

		//Verify: the sections are collapsed by default
		boolean synopsisBackgroundIsCollapsed1 = headnotesPage().isSynopsisBackgroundCollapsed();
		boolean synopsisHoldingsIsCollapsed1 = headnotesPage().isSynopsisHoldingsCollapsed();
		boolean notesIsCollapsed1 = headnotesPage().doesNotesCollapsibleExist();

		//Open each one of these and pull the text from the field that opens below
		String synopsisBackgroundText1 = headnotesPage().getSynopsisBackgroundCollapsibleBodyText();
		String synopsisHoldingsText1 = headnotesPage().getSynopsisHoldingsCollapsibleBodyText();
		String notesText1 = headnotesPage().getNotesCollapsibleBodyText();

		//reopen the subscribed case
		headnotesPage().clickSubscribedCasesBreadcrumb();
		subscribedCasesGridPage().clickCaseSerialNumberLink(firstCaseSerialNumber);

		//Verify: the sections are all collapsed by default
		boolean synopsisBackgroundIsCollapsed2 = headnotesPage().isSynopsisBackgroundCollapsed();
		boolean synopsisHoldingsIsCollapsed2 = headnotesPage().isSynopsisHoldingsCollapsed();
		boolean notesIsCollapsed2 = headnotesPage().isNotesCollapsed();

		//Verify: the text pulled from each field before matches the text now
		String synopsisBackgroundText2 = headnotesPage().getSynopsisBackgroundCollapsibleBodyText();
		String synopsisHoldingsText2 = headnotesPage().getSynopsisHoldingsCollapsibleBodyText();
		String notesText2 = headnotesPage().getNotesCollapsibleBodyText();
		boolean synopsisBackgroundTextIsMatches = synopsisBackgroundText1.equals(synopsisBackgroundText2);
		boolean synopsisHoldingsTextIsMatches = synopsisHoldingsText1.equals(synopsisHoldingsText2);
		boolean notesTextIsMatches = notesText1.equals(notesText2);

		Assertions.assertAll
		(
				() -> Assertions.assertTrue(subscribedCasesWindowAppeared, "The Subscribed Cases window should appeare"),
				() -> Assertions.assertTrue(synopsisBackgroundCollapsButtonIsExist, "'Synopsis Background' section should have expandable and collapsible button"),
				() -> Assertions.assertTrue(synopsisHoldingsCollapsButtonIsExist, "'Synopsis Holdings' section should have expandable and collapsible button"),
				() -> Assertions.assertTrue(notesCollapsButtonIsExist, "'Notes' section should have expandable and collapsible button"),

				() -> Assertions.assertTrue(synopsisBackgroundIsCollapsed1, "'Synopsis Background' section should be collapsed by default"),
				() -> Assertions.assertTrue(synopsisHoldingsIsCollapsed1, "'Synopsis Holdings' section should be collapsed by default"),
				() -> Assertions.assertTrue(notesIsCollapsed1, "'Notes' section should be collapsed by default"),

				() -> Assertions.assertTrue(synopsisBackgroundIsCollapsed2, "'Synopsis Background' section should be collapsed by default"),
				() -> Assertions.assertTrue(synopsisHoldingsIsCollapsed2, "'Synopsis Holdings' section should be collapsed by default"),
				() -> Assertions.assertTrue(notesIsCollapsed2, "'Notes' section should be collapsed by default"),

				() -> Assertions.assertTrue(synopsisBackgroundTextIsMatches, "'Synopsis Background': text pulled from this field before should matches the text now"),
				() -> Assertions.assertTrue(synopsisHoldingsTextIsMatches, "'Synopsis Holdings': text pulled from this field before should matches the text now"),
				() -> Assertions.assertTrue(notesTextIsMatches, "'Notes': text pulled from this field before should matches the text now")
		);
	}

	/**
	 * STORY/BUGS - HALCYONST-1473 &lt;br&gt;
	 * SUMMARY -  Checks that a repealed document is highlighted correctly in the headnotes page&lt;br&gt;
	 * USER -  Legal &lt;br&gt;
	 */
	@Test
	@LEGAL
	@LOG
	public void headnotesRepealedDocumentsTest()
	{
		String headingId = "1390810";
		String uuid = "I26D2C49014EE11DA8AC5CD53670E6B4E";

		homePage().goToHomePage();
		loginPage().logIn();

		hierarchyMenu().goToNavigate();
		hierarchySearchPage().searchNodeUuid(uuid);

		String keyword = siblingMetadataPage().getSelectedGridRowKeyword();
		String value = siblingMetadataPage().getSelectedGridRowValue();

		siblingMetadataPage().rightClickSelectedSiblingMetadata();
		siblingMetadataContextMenu().updateMetadata();
		updateMetadataPage().setStatusTo("Repeal");
		updateMetadataPage().clickQuickLoadOk();

		boolean statusIsRepeal = siblingMetadataPage().isSelectedRowStatusRepeal();
		boolean subscribedCasesWindowAppeared = nodMenu().goToSubscribedCases();
		Assertions.assertTrue(subscribedCasesWindowAppeared, "Subscribed Cases Window did not appear");

		String firstCaseSerialNumber = subscribedCasesGridPage().getCaseSerialNumberFromSubscribedCasesByGivenRow(1);
		boolean headnotesPageAppeared = subscribedCasesGridPage().clickCaseSerialNumberLink(firstCaseSerialNumber);
		Assertions.assertTrue(headnotesPageAppeared, "Headnotes window did not appear");

		headnotesSearchPage().clickKeywordFind();
		boolean findPageAppeared = findPage().switchToFindPage();
		Assertions.assertTrue(findPageAppeared);
		findPage().setFirstKeywordDropdown(keyword);
		findPage().setFirstKeywordValue(value);
		findPage().setSecondKeywordDropdown("");
		findPage().clickSearch();

		headnotesPage().clickFindResultByHID(headingId);
		headnotesPage().switchToHeadnotesWindow();
		headnotesPage().waitForPageLoaded();
		boolean nodeIsHighlightedRed = headnotesPage().repealedNodeIsHighlightedRed(value);
		Assertions.assertTrue(nodeIsHighlightedRed, "Node is not repealed");

		headnotesPage().closeCurrentWindowIgnoreDialogue();
		boolean hierarchyEditWindowAppeared = hierarchyNavigatePage().switchToHierarchyEditPage();
		Assertions.assertTrue(hierarchyEditWindowAppeared, "The Hierarchy Edit Window did not appear");

		siblingMetadataPage().rightClickSelectedSiblingMetadata();
		siblingMetadataContextMenu().updateMetadata();
		updateMetadataPage().setStatusTo("Live");
		updateMetadataPage().clickQuickLoadOk();
		boolean statusIsLive = siblingMetadataPage().isSelectedRowStatusLive();

		nodMenu().goToSubscribedCases();
		headnotesPageAppeared = subscribedCasesGridPage().clickCaseSerialNumberLink(firstCaseSerialNumber);
		Assertions.assertTrue(headnotesPageAppeared, "Headnotes window did not appear");

		headnotesSearchPage().clickKeywordFind();
		findPageAppeared = findPage().switchToFindPage();
		Assertions.assertTrue(findPageAppeared);
		findPage().setFirstKeywordDropdown(keyword);
		findPage().setFirstKeywordValue(value);
		findPage().setSecondKeywordDropdown("");
		findPage().clickSearch();

		headnotesPage().clickFindResultByHID(headingId);
		headnotesPageAppeared = headnotesPage().switchToHeadnotesWindow();
		Assertions.assertTrue(headnotesPageAppeared, "Headnotes window did not appear");

		boolean nodeIsNOTHighlightedRed = headnotesPage().repealedNodeIsNotHighlightedRed(value);

		Assertions.assertAll
		(
				() -> Assertions.assertTrue(statusIsRepeal, "The node should have a status of Repeal"),
				() -> Assertions.assertTrue(subscribedCasesWindowAppeared, "The Subscribed Cases window should appeare"),
				() -> Assertions.assertTrue(nodeIsHighlightedRed, "The node should be highlighted red"),
				() -> Assertions.assertTrue(hierarchyEditWindowAppeared, "Hierarchy Edit Window should appeare after switch"),
				() -> Assertions.assertTrue(statusIsLive, "The node should has a status of Live"),
				() -> Assertions.assertTrue(nodeIsNOTHighlightedRed, "The node should not be highlighted red")
		);
	}

	@Test // HALCYONST-1472
	@LEGAL
	@LOG
	public void nodSubscribedCasesSearchResultsCompareTest()
	{
		String quickSearchQuery = "a II s 6";
		List<String> expectedColumns = new ArrayList<>(Arrays.asList("Node UUID","Keyword","Value","HID","Start Date","End Date","Cite"));

		homePage().goToHomePage();
		loginPage().logIn();

		// go to Subscribed Cases
		boolean subscribedCasesWindowAppeared = nodMenu().goToSubscribedCases();
		Assertions.assertTrue(subscribedCasesWindowAppeared, "Subscribed Cases Window did not appear");

		// open first Case and perform Quick search
		String caseSerialNumber = subscribedCasesGridPage().getCaseSerialNumberFromSubscribedCasesByGivenRow(1);
		boolean headnotesPageAppeared = subscribedCasesGridPage().clickCaseSerialNumberLink(caseSerialNumber);
		Assertions.assertTrue(headnotesPageAppeared);

		// check columns and grab results
		headnotesSearchPage().setQuickFindField(quickSearchQuery);
		headnotesSearchPage().clickQuickFindButton();
		boolean findWindowAppeared = findPage().switchToFindPage();
		Assertions.assertTrue(findWindowAppeared, "Find window did not appear");

		List<String> actualColumns = findPage().getActualColumns();
		boolean columns = actualColumns.equals(expectedColumns);
		Assertions.assertTrue(columns, "Find window should display expected columns");

		List<String> quickSearchResults = findPage().getQuickSearchResults();
		quickSearchResults.replaceAll(item -> item.replace("�", "&sect;")); //Is corrupted pre merge

		// close find window
		findPage().breakOutOfFrame();
		findPage().clickWindowCloseButton();

		// perform keyword search and grab results to compare with quick find results
		headnotesPageAppeared = headnotesPage().switchToHeadnotesPage();
		Assertions.assertTrue(headnotesPageAppeared, "The headnotes window did not appear");

		headnotesSearchPage().clickKeywordFind();
		findWindowAppeared = findPage().switchToFindPage();
		Assertions.assertTrue(findWindowAppeared, "The find window did not appear");
		findPage().setFirstKeywordDropdown("ARTICLE");
		findPage().setFirstKeywordValue("II");
		findPage().setSecondKeywordDropdown("SECTION");
		findPage().setSecondKeywordValue("6");
		findPage().clickSearch();

		findWindowAppeared = findPage().switchToFindPage();
		Assertions.assertTrue(findWindowAppeared, "Find window did not appear");

		List<String> keywordSearchResults = findPage().getKeywordSearchResults();
		boolean results = quickSearchResults.equals(keywordSearchResults);
		Assertions.assertTrue(results, "Quick Search and Keyword Search provided same results");
	}


	@Test
	@LEGAL
	@LOG
	public void classifyBluelineCancelAndOk()
	{
		String quickSearchQuery = "123.2 1";

		homePage().goToHomePage();
		loginPage().logIn();

		//Go to Subscribed cases page
		boolean subscribedCasesWindowAppeared = nodMenu().goToSubscribedCases();
		Assertions.assertTrue(subscribedCasesWindowAppeared, "Subscribed Cases Window did not appear");

		// open first Case and go to headnotes page
		String caseSerialNumber = subscribedCasesGridPage().getCaseSerialNumberFromSubscribedCasesByGivenRow(1);
		boolean headnotesPageAppeared = subscribedCasesGridPage().clickCaseSerialNumberLink(caseSerialNumber);
		Assertions.assertTrue(headnotesPageAppeared, "Headnotes page did not appear");

		//quickfind node
		headnotesSearchPage().setQuickFindField(quickSearchQuery);
		headnotesSearchPage().clickQuickFindButton();
		headnotesPage().waitForPageLoaded();
		headnotesPageAppeared = headnotesPage().switchToHeadnotesPage();
		Assertions.assertTrue(headnotesPageAppeared, "Headnotes page did not appear");

		//Check correct node was found
		boolean isCorrectNodeHighlighted = headnotesTreePage().isNodeHighlightedInTree(" BL 1");
		Assertions.assertTrue(isCorrectNodeHighlighted, "The incorrect node is highlighted");

		//Classify headnote
		headnotesPage().classifyHeadnoteByGivenRow(1);
		boolean isFirstHeadnoteClassified = headnotesPage().isHeadnoteInGivenRowClassified(1);
		Assertions.assertTrue(isFirstHeadnoteClassified, "The first headnote is not classified");
		boolean doesIgnoreButtonAppearForFirstHeadnote = headnotesPage().doesIgnoreButtonExistForHeadnoteInGivenRow(1);
		Assertions.assertFalse(doesIgnoreButtonAppearForFirstHeadnote, "Ignore appears after classification of headnotes");

		//click cancel after attempting to remove classification
		headnotesPage().unclassifyFirstHeadnote();
		AutoITUtils.verifyAlertTextAndCancel(true, " Are you sure you want to un-classify this headnote?");
		headnotesPage().switchToHeadnotesPage();

		//Check to see if classifications appear correctly after cancel
		isFirstHeadnoteClassified = headnotesPage().isHeadnoteInGivenRowClassified(1);
		Assertions.assertTrue(isFirstHeadnoteClassified, "First Headnote is not classified after clicking cancel");
		doesIgnoreButtonAppearForFirstHeadnote = headnotesPage().doesIgnoreButtonExistForHeadnoteInGivenRow(1);
		Assertions.assertFalse(doesIgnoreButtonAppearForFirstHeadnote, "Ignore button appeares on classified headnote");

		//Unclassify Headnote
		headnotesPage().unclassifyFirstHeadnote();
		AutoITUtils.verifyAlertTextAndAccept(true, " Are you sure you want to un-classify this headnote?");
		headnotesPage().waitForGridRefresh();
		headnotesPage().waitForPageLoaded();
		AutoITUtils.verifyAlertTextAndAccept(true, "The headnote couldn't be unclassified because NOD couldn't be deleted. It either doesn't exist or has changed.");
		headnotesPage().waitForGridRefresh();
		headnotesPage().waitForPageLoaded();

		isFirstHeadnoteClassified = headnotesPage().isHeadnoteInGivenRowClassified(1);
		Assertions.assertFalse(isFirstHeadnoteClassified, "First Headnote is still classified after removal");
		doesIgnoreButtonAppearForFirstHeadnote = headnotesPage().doesIgnoreButtonExistForHeadnoteInGivenRow(1);
		Assertions.assertTrue(doesIgnoreButtonAppearForFirstHeadnote, "Ignore button does not appear after unclassification");
	}


	@Test
	@LEGAL
	@LOG
	public void classifySectionAttempt()
	{
		String quickSearchQuery = "123.2";

		homePage().goToHomePage();
		loginPage().logIn();

		//Go to Subscribed cases page
		nodMenu().goToSubscribedCases();
		boolean subscribedCasesWindowAppeared = subscribedCasesPage().switchToSubscribedCasesPage();
		Assertions.assertTrue(subscribedCasesWindowAppeared, "Subscribed Cases Window did not appear");

		// open first Case and go to headnotes page
		String caseSerialNumber = subscribedCasesGridPage().getCaseSerialNumberFromSubscribedCasesByGivenRow(1);
		boolean headnotesPageAppeared = subscribedCasesGridPage().clickCaseSerialNumberLink(caseSerialNumber);
		Assertions.assertTrue(headnotesPageAppeared, "Headnotes page did not appear");

		//quickfind node
		headnotesSearchPage().setQuickFindField(quickSearchQuery);
		headnotesSearchPage().clickQuickFindButton();
		headnotesPage().waitForPageLoaded();
		headnotesPageAppeared = headnotesPage().switchToHeadnotesPage();
		Assertions.assertTrue(headnotesPageAppeared, "Headnotes page did not appear");

		//Check correct node was found
		boolean isCorrectNodeHighlighted = headnotesTreePage().isNodeHighlightedInTree("123.2");
		Assertions.assertTrue(isCorrectNodeHighlighted, "The incorrect node is highlighted");

		//Classify headnote
		headnotesPage().classifyHeadnoteByGivenRow(1);
		AutoITUtils.verifyAlertTextAndAccept(true, "");

		boolean didHeadnoteGetClassified = headnotesPage().isHeadnoteInGivenRowClassified(1);
		Assertions.assertFalse(didHeadnoteGetClassified, "Headnote was classified when it should not be");
	}


	@Test
	@LEGAL
	@LOG
	public void bluelineClassificationLinkBringUsersToCorrectBlueline()
	{
		String quickSearchQuery = "123.2 ";

		homePage().goToHomePage();
		loginPage().logIn();

		//Go to Subscribed cases page
		nodMenu().goToSubscribedCases();
		boolean subscribedCasesWindowAppeared = subscribedCasesPage().switchToSubscribedCasesPage();
		Assertions.assertTrue(subscribedCasesWindowAppeared, "Subscribed Cases Window did not appear");

		// open first Case and go to headnotes page
		String caseSerialNumber = subscribedCasesGridPage().getCaseSerialNumberFromSubscribedCasesByGivenRow(1);
		boolean headnotesPageAppeared = subscribedCasesGridPage().clickCaseSerialNumberLink(caseSerialNumber);
		Assertions.assertTrue(headnotesPageAppeared, "Headnotes page did not appear");

		//quickfind first node
		headnotesSearchPage().setQuickFindField(quickSearchQuery + "1");
		headnotesSearchPage().clickQuickFindButton();
		headnotesPage().waitForPageLoaded();
		headnotesPageAppeared = headnotesPage().switchToHeadnotesPage();
		Assertions.assertTrue(headnotesPageAppeared, "Headnotes page did not appear");

		//Check correct node was found
		boolean isCorrectNodeHighlighted = headnotesTreePage().isNodeHighlightedInTree(" BL 1");
		Assertions.assertTrue(isCorrectNodeHighlighted, "The incorrect node is highlighted");

		//Classify headnote
		headnotesPage().classifyHeadnoteByGivenRow(1);
		boolean isFirstHeadnoteClassified = headnotesPage().isHeadnoteInGivenRowClassified(1);
		Assertions.assertTrue(isFirstHeadnoteClassified, "The first headnote is not classified");
		boolean doesIgnoreButtonAppearForFirstHeadnote = headnotesPage().doesIgnoreButtonExistForHeadnoteInGivenRow(1);
		Assertions.assertFalse(doesIgnoreButtonAppearForFirstHeadnote, "Ignore appears after classification of headnotes");

		//Check parent section and sections parent

		//quickfind node
		headnotesSearchPage().setQuickFindField(quickSearchQuery + "2");
		headnotesSearchPage().clickQuickFindButton();
		headnotesPage().waitForPageLoaded();
		headnotesPageAppeared = headnotesPage().switchToHeadnotesPage();
		Assertions.assertTrue(headnotesPageAppeared, "Headnotes page did not appear");

		//Check correct node was found
		isCorrectNodeHighlighted = headnotesTreePage().isNodeHighlightedInTree(" BL 2");
		Assertions.assertTrue(isCorrectNodeHighlighted, "The incorrect node is highlighted");

		//Classify Second Headnote
		headnotesPage().classifyHeadnoteByGivenRow(1);
		boolean isSecondHeadnoteClassified = headnotesPage().isHeadnoteInGivenRowClassified(1);
		Assertions.assertTrue(isSecondHeadnoteClassified, "The first headnote is not classified");
		boolean doesIgnoreButtonAppearForSecondHeadnote = headnotesPage().doesIgnoreButtonExistForHeadnoteInGivenRow(1);
		Assertions.assertFalse(doesIgnoreButtonAppearForSecondHeadnote, "Ignore appears after classification of headnotes");

		//click the subscribed cases breadcrumb and re-enter the headnotes page
		subscribedCasesWindowAppeared = headnotesPage().clickSubscribedCasesBreadcrumb();
		Assertions.assertTrue(subscribedCasesWindowAppeared, "The Subscribed Cases page did not appear");
		headnotesPageAppeared = subscribedCasesGridPage().clickCaseSerialNumberLink(caseSerialNumber);
		Assertions.assertTrue(headnotesPageAppeared, "Headnotes page did not appear");

		//Find first blueline from quickfind field
		headnotesSearchPage().setQuickFindField(quickSearchQuery + "1");
		headnotesSearchPage().clickQuickFindButton();
		headnotesPage().waitForPageLoaded();
		headnotesPageAppeared = headnotesPage().switchToHeadnotesPage();
		Assertions.assertTrue(headnotesPageAppeared, "Headnotes page did not appear");

		//Click links and verify it brings us to correct blueline

		//Unclassify Headnote
		headnotesPage().unclassifyFirstHeadnote();
		AutoITUtils.verifyAlertTextAndAccept(true, " Are you sure you want to un-classify this headnote?");
		headnotesPage().waitForClassificationRemoval();
		headnotesPage().switchToHeadnotesPage();

		headnotesPage().unclassifyFirstHeadnote();
		AutoITUtils.verifyAlertTextAndAccept(true, " Are you sure you want to un-classify this headnote?");
		headnotesPage().waitForClassificationRemoval();
		headnotesPage().switchToHeadnotesPage();

		//Make sure each headnote is unclassified and the ignore buttons appear
		//NOTE: THIS IS NOT IN A ASSERT ALL BECAUSE THEY ARE USED EARLIER. This can be fixed by using a different variable name.
		isFirstHeadnoteClassified = headnotesPage().isHeadnoteInGivenRowClassified(1);
		Assertions.assertFalse(isFirstHeadnoteClassified, "the first headnote is still classified");
		doesIgnoreButtonAppearForFirstHeadnote = headnotesPage().doesIgnoreButtonExistForHeadnoteInGivenRow(1);
		Assertions.assertTrue(doesIgnoreButtonAppearForFirstHeadnote, "The ignore button did not appear for the first headnote");
		isSecondHeadnoteClassified = headnotesPage().isHeadnoteInGivenRowClassified(1);
		Assertions.assertFalse(isSecondHeadnoteClassified, "The second headnote is still classified");
		doesIgnoreButtonAppearForSecondHeadnote = headnotesPage().doesIgnoreButtonExistForHeadnoteInGivenRow(1);
		Assertions.assertTrue(doesIgnoreButtonAppearForSecondHeadnote, "The ignore button did not appear for the second headnote");
	}

	@Test
	@LEGAL
	@LOG
	public void multipleBluelineClassifications()
	{
		String quickSearchQuery = "123.2 ";

		homePage().goToHomePage();
		loginPage().logIn();

		//Go to Subscribed cases page
		boolean subscribedCasesWindowAppeared = nodMenu().goToSubscribedCases();
		Assertions.assertTrue(subscribedCasesWindowAppeared, "Subscribed Cases Window did not appear");

		// open first Case and go to headnotes page
		String caseSerialNumber = subscribedCasesGridPage().getCaseSerialNumberFromSubscribedCasesByGivenRow(1);
		boolean headnotesPageAppeared = subscribedCasesGridPage().clickCaseSerialNumberLink(caseSerialNumber);
		Assertions.assertTrue(headnotesPageAppeared, "Headnotes page did not appear");

		//quickfind first node
		headnotesSearchPage().setQuickFindField(quickSearchQuery + "1");
		headnotesSearchPage().clickQuickFindButton();

		//Check correct node was found
		boolean isCorrectNodeHighlighted = headnotesTreePage().isNodeHighlightedInTree(" BL 1");
		Assertions.assertTrue(isCorrectNodeHighlighted, "The incorrect node is highlighted");

		//Classify headnote
		headnotesPage().classifyHeadnoteByGivenRow(1);
		boolean isFirstHeadnoteClassifiedWithBL1 = headnotesPage().doesClassificationExistForGivenRow("BL 1", 1);
		Assertions.assertTrue(isFirstHeadnoteClassifiedWithBL1, "The first headnote is not classified");
		boolean doesIgnoreButtonAppearForFirstHeadnote = headnotesPage().doesIgnoreButtonExistForHeadnoteInGivenRow(1);
		Assertions.assertFalse(doesIgnoreButtonAppearForFirstHeadnote, "Ignore appears after classification of headnotes");

		//quickfind second node
		headnotesSearchPage().setQuickFindField(quickSearchQuery + "2");
		headnotesSearchPage().clickQuickFindButton();

		//Check correct node was found
		isCorrectNodeHighlighted = headnotesTreePage().isNodeHighlightedInTree(" BL 2");
		Assertions.assertTrue(isCorrectNodeHighlighted, "The incorrect node is highlighted");

		//Classify headnote 1 again while second headnote is selected
		headnotesPage().classifyHeadnoteByGivenRow(1);
		boolean isFirstHeadnoteClassifiedWithBL2 = headnotesPage().doesClassificationExistForGivenRow("BL 2", 1);
		Assertions.assertTrue(isFirstHeadnoteClassifiedWithBL2, "The first headnote is not classified");
		doesIgnoreButtonAppearForFirstHeadnote = headnotesPage().doesIgnoreButtonExistForHeadnoteInGivenRow(1); //Ignore button should still not exist
		Assertions.assertFalse(doesIgnoreButtonAppearForFirstHeadnote, "Ignore appears after classification of headnotes");

		//Unclassify Headnote
		headnotesPage().unclassifyFirstHeadnote();//unclassify specific blueline
		AutoITUtils.verifyAlertTextAndAccept(true, " Are you sure you want to un-classify this headnote?");
		headnotesPage().waitForGridRefresh();
		headnotesPage().waitForPageLoaded();
		AutoITUtils.verifyAlertTextAndAccept(true, "The headnote couldn't be unclassified because NOD couldn't be deleted. It either doesn't exist or has changed.");
		headnotesPage().waitForGridRefresh();
		headnotesPage().waitForPageLoaded();
		isFirstHeadnoteClassifiedWithBL1 = headnotesPage().doesClassificationExistForGivenRow("BL 1", 1);
		Assertions.assertFalse(isFirstHeadnoteClassifiedWithBL1, "The first headnote is not classified");
		doesIgnoreButtonAppearForFirstHeadnote = headnotesPage().doesIgnoreButtonExistForHeadnoteInGivenRow(1);
		Assertions.assertFalse(doesIgnoreButtonAppearForFirstHeadnote, "Ignore appears after classification of headnotes");

		headnotesPage().unclassifyFirstHeadnote();
		AutoITUtils.verifyAlertTextAndAccept(true, " Are you sure you want to un-classify this headnote?");
		headnotesPage().waitForGridRefresh();
		headnotesPage().waitForPageLoaded();
		AutoITUtils.verifyAlertTextAndAccept(true, "The headnote couldn't be unclassified because NOD couldn't be deleted. It either doesn't exist or has changed.");
		headnotesPage().waitForGridRefresh();
		headnotesPage().waitForPageLoaded();
		isFirstHeadnoteClassifiedWithBL2 = headnotesPage().doesClassificationExistForGivenRow("BL 2", 1);
		Assertions.assertFalse(isFirstHeadnoteClassifiedWithBL2, "The first headnote is not classified");
		doesIgnoreButtonAppearForFirstHeadnote = headnotesPage().doesIgnoreButtonExistForHeadnoteInGivenRow(1); //Ignore button should exist
		Assertions.assertTrue(doesIgnoreButtonAppearForFirstHeadnote, "Ignore appears after classification of headnotes");
	}
}
