package com.thomsonreuters.codes.codesbench.quality.tests.nod;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;

public class AdministrativeOpinionsTests extends TestService
{
	/**
	 * STORY/BUG - n/a <br>
	 * SUMMARY - Tests basic insert, updating, and deleting of opinions in the administrative opinions page.
	 * Also checks functionality for the keyword find window. <br>
	 * USER - Legal <br>
	 */
	@Test
	@LEGAL
	@LOG
	public void saveAndEditClassifyTest()
	{
		String opinionType = "Attorney General";
		String opinionNumber = "23";
		String opinionText1 = "This is my first opinion";
		String opinionText2 = "This is my second opinion";
		String opinionCitation1 = "myFirstCitation";
		String opinionCitation2 = "mySecondCitation";
		String editor = user().getFirstname() + " " + user().getLastname();
		String targetSection = "§ 2";

		String currentDate = DateAndTimeUtils.getCurrentDateMMddyyyy();
		String currentYear = DateAndTimeUtils.getCurrentYearyyyy();
		String wlNumber = currentYear + " WL " + opinionNumber;

		homePage().goToHomePage();
		loginPage().logIn();
 
		boolean administrativeOpinionsWindowAppeared = nodMenu().goToAdministrativeOpinions();
		Assertions.assertTrue(administrativeOpinionsWindowAppeared, "The administrative opinions window did not appear");

		administrativeOpinionsPage().clickInsertOpinion();

		insertOpinionPage().setOpinionType(opinionType);
		insertOpinionPage().setOpinionNumber(opinionNumber);
		insertOpinionPage().setDateOfOpinion(currentDate);
		insertOpinionPage().setOpinionText(opinionText1);
		insertOpinionPage().setOpinionCitation(opinionCitation1);
		insertOpinionPage().setOpinionWlNumber(wlNumber);

		boolean editOpinionWindowAppeared = insertOpinionPage().clickSaveAndEditClassify();
		Assertions.assertTrue(editOpinionWindowAppeared, "The edit opinion window did not appear");
		
		editOpinionPage().setOpinionText(opinionText2);
		editOpinionPage().setOpinionCitation(opinionCitation2);
		administrativeOpinionsWindowAppeared = editOpinionPage().clickUpdate();
		Assertions.assertTrue(administrativeOpinionsWindowAppeared, "The administrative opinions window did not appear");
		
		boolean firstOpinionAppearedOnGrid = administrativeOpinionsGridPage().opinionAppearedOnGrid(opinionType, opinionNumber, currentDate, editor, opinionText1, opinionCitation1, wlNumber);
		boolean editedOpinionAppearedOnGrid = administrativeOpinionsGridPage().opinionAppearedOnGrid(opinionType, opinionNumber, currentDate, editor, opinionText2, opinionCitation2, wlNumber);
		Assertions.assertFalse(firstOpinionAppearedOnGrid, "The first opinion appeared on the grid when it should not");
		Assertions.assertTrue(editedOpinionAppearedOnGrid, "The edited opinion appeared on the grid as expected");
		
		administrativeOpinionsGridPage().rightClickOpinionByOpinionNumber(opinionNumber);
		administrativeOpinionsContextMenu().editOpinion();
		boolean findWindowAppeared = editOpinionPage().clickKeywordFind();
		Assertions.assertTrue(findWindowAppeared, "The Find window did not appear after clicking Keyword Find and searching for a node");
		
		findPage().setFirstKeywordDropdown("SECTION");
		findPage().setFirstKeywordValue("2");
		findPage().setSecondKeywordDropdown("");
		findPage().setSecondKeywordValue("");
		findPage().setThirdKeywordDropdown("");
		findPage().setThirdKeywordValue("");
		findPage().clickSearch();
		
		editOpinionWindowAppeared = editOpinionPage().switchToEditOpinionPage();
		Assertions.assertTrue(editOpinionWindowAppeared, "The edit opinion window appeared");
		findWindowAppeared = findPage().switchToFindPage();
		Assertions.assertTrue(findWindowAppeared, "The Find window did not appear");

		String uuid = findPage().getFirstFindResultUuid();
		findPage().clickFirstFindResult();
		boolean editOpinionPageAppeared = editOpinionPage().switchToEditOpinionPage();
		Assertions.assertTrue(editOpinionPageAppeared, "Edit Opinion Page appeared");
		boolean treeControlGoesToSection = editOpinionPage().resultFromFindAppearsSelectedInTree(targetSection);

		Assertions.assertTrue(treeControlGoesToSection, "Clicking one of the results in Find did not bring us to the node we had interest in");
		
		editOpinionPage().rightClickHighlightedNode(targetSection);
		editOpinionContextMenu().viewContent();
		boolean uuidGrabbedFromEditorIsTheSameAsGrabbedFromSearchResults = editorPage().getUrl().contains(uuid);
		editorPage().closeDocumentWithNoChanges();

		editOpinionWindowAppeared = editOpinionPage().switchToEditOpinionPage();
		Assertions.assertTrue(editOpinionWindowAppeared, "The edit opinion window did not appear");
		administrativeOpinionsWindowAppeared = editOpinionPage().clickCancel();
		Assertions.assertTrue(administrativeOpinionsWindowAppeared, "The administrative window did not appear");
		administrativeOpinionsGridPage().rightClickOpinionByOpinionNumber(opinionNumber);
		boolean deleteWindowAppeared = administrativeOpinionsContextMenu().deleteOpinion();
		Assertions.assertTrue(deleteWindowAppeared, "Delete window did not appear");
		
		boolean opinionAppeardInDeletePage = deleteOpinionPage().opinionAppearedInDelete(opinionType, opinionNumber, currentDate, editor, opinionText2, opinionCitation2, wlNumber);
		Assertions.assertTrue(opinionAppeardInDeletePage, "The opinion did not appear in the delete page when it should have");
		
		administrativeOpinionsWindowAppeared = deleteOpinionPage().clickDelete();
		Assertions.assertTrue(administrativeOpinionsWindowAppeared, "The administrative opinions window did not appear when it should have");
		
		boolean opinionDeletedFromGrid = administrativeOpinionsGridPage().opinionAppearedOnGrid(opinionType, opinionNumber, currentDate, editor, opinionText2, opinionCitation2, wlNumber);

		Assertions.assertAll
		(
			() -> Assertions.assertTrue(uuidGrabbedFromEditorIsTheSameAsGrabbedFromSearchResults, "uuid Grabbed From Editor Is not The Same As Grabbed From Search Results"),
			() -> Assertions.assertFalse(opinionDeletedFromGrid, "The opinion appeared on the grid when it should not")
		);
	}

	/**
	 * STORY/BUG - n/a <br>
	 * SUMMARY - Tests basic insert, updating, and deleting of opinions in the administrative opinions page.
	 * Also checks copy and paste usage with hot keys when creating an opinion and checks for opinion during deletion. <br>
	 * USER - Legal <br>
	 */
	@Test
	@LEGAL
	@LOG
	public void insertOpinionTest()
	{
		String opinionType = "Attorney General";
		String opinionNumber = "1234512";
		String opinionText = "Parens: ( ), single quotes: ’ ‘, double quotes: “ ”";
		String opinionCitation = "101SCT1";
		String editor = user().getFirstname() + " " + user().getLastname();

		String currentDate = DateAndTimeUtils.getCurrentDateMMddyyyy();
		String currentYear = DateAndTimeUtils.getCurrentYearyyyy();
		String wlNumber = currentYear + " WL " + opinionNumber;

		homePage().goToHomePage();
		loginPage().logIn();

		boolean administrativeOpinionsWindowAppeared = nodMenu().goToAdministrativeOpinions();
		Assertions.assertTrue(administrativeOpinionsWindowAppeared, "The administrative opinions window did not appear");
		
		boolean insertOpinionWindow = administrativeOpinionsPage().clickInsertOpinion();
		Assertions.assertTrue(insertOpinionWindow, "Insert Opinion Window did not appear");
		
		insertOpinionPage().setOpinionType(opinionType);
		insertOpinionPage().setOpinionNumber(opinionNumber);
		insertOpinionPage().setDateOfOpinion(currentDate);
		insertOpinionPage().setOpinionTextWithCtrlV(opinionText);
		insertOpinionPage().setOpinionCitation(opinionCitation);
		insertOpinionPage().setOpinionWlNumber(wlNumber);
		administrativeOpinionsWindowAppeared = insertOpinionPage().clickInsert();
		Assertions.assertTrue(administrativeOpinionsWindowAppeared, "The administrative opinions window did not appear");
		
		boolean insertedOpinionAppearedOnGrid = administrativeOpinionsGridPage().opinionAppearedOnGrid(opinionType, opinionNumber, currentDate, editor, opinionText, opinionCitation, wlNumber);
		Assertions.assertTrue(insertedOpinionAppearedOnGrid, "The inserted opinion appeared correctly on the grid");

		boolean editOpinonWindowAppeared = administrativeOpinionsPage().clickOpinionNumber(opinionNumber);
		Assertions.assertTrue(editOpinonWindowAppeared, "Edit opinion window did not appear");
		String editedOpinionsText = editOpinionPage().getOpinionText();
		Assertions.assertEquals(editedOpinionsText, opinionText, "The edited opinions text does not match the original text");
		
		String newOpinionType = "Environmental Law";
		String newOpinionNumber = (Integer.parseInt(opinionNumber) + 1) + "";
		String newDate = "04/13/2015";
		String newOpinionText = "TEST TEST. Is this microphone on?";
		String newOpinionCitation = "102SCT1";
		String newWlNumber = currentYear + " WL " + newOpinionNumber;

		editOpinonWindowAppeared = editOpinionPage().switchToEditOpinionPage();
		Assertions.assertTrue(editOpinonWindowAppeared, "The edit opinion page appeared");
		editOpinionPage().setOpinionType(newOpinionType);
		editOpinionPage().setOpinionNumber(newOpinionNumber);
		editOpinionPage().setDateOfOpinion(newDate);
		editOpinionPage().setOpinionText(newOpinionText);
		editOpinionPage().setOpinionCitation(newOpinionCitation);
		editOpinionPage().setOpinionWlNumber(newWlNumber);
		administrativeOpinionsWindowAppeared = editOpinionPage().clickUpdate();
		Assertions.assertTrue(administrativeOpinionsWindowAppeared, "The administrative opinions window did not appear");

		boolean originalOpinionAppearedOnGridAfterUpdate = administrativeOpinionsGridPage().opinionAppearedOnGrid(opinionType, opinionNumber, currentDate, editor, opinionText, opinionCitation, wlNumber);
		boolean updatedOpinionAppearedOnGrid = administrativeOpinionsGridPage().opinionAppearedOnGrid(newOpinionType, newOpinionNumber, newDate, editor, newOpinionText, newOpinionCitation, newWlNumber);
		Assertions.assertFalse(originalOpinionAppearedOnGridAfterUpdate, "The original opinion appeared on the grid, but we expected the updated opinion to appear");
		Assertions.assertTrue(updatedOpinionAppearedOnGrid, "The updated opinion appeared correctly on the grid");

		administrativeOpinionsGridPage().rightClickOpinionByOpinionNumber(newOpinionNumber);
		boolean deleteWindowAppeared = administrativeOpinionsContextMenu().deleteOpinion();
		Assertions.assertTrue(deleteWindowAppeared, "Delete window did not appear");
		
		boolean opinionAppeardInDeletePage = deleteOpinionPage().opinionAppearedInDelete(newOpinionType, newOpinionNumber, newDate, editor, newOpinionText, newOpinionCitation, newWlNumber);
		Assertions.assertTrue(opinionAppeardInDeletePage, "The opinion did not appear in the delete page when it should have");
		
		administrativeOpinionsWindowAppeared = deleteOpinionPage().clickDelete();
		Assertions.assertTrue(administrativeOpinionsWindowAppeared, "The administrative opinions window did not appear when it should have");

		boolean updatedOpinionAppearedOnGridAfterDelete = administrativeOpinionsGridPage().opinionAppearedOnGrid(newOpinionType, newOpinionNumber, newDate, editor, newOpinionText, newOpinionCitation, newWlNumber);
		Assertions.assertFalse(updatedOpinionAppearedOnGridAfterDelete, "The updated opinion appeared on the grid after we deleted it");
	}
}
