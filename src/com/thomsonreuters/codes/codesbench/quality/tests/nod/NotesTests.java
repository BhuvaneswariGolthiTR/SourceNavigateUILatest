package com.thomsonreuters.codes.codesbench.quality.tests.nod;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;

public class NotesTests extends TestService
{
	String text1 = "Text1";
	String text2 = "Text2";
	/**
	 * STORY/BUGS - HALCYONST-1350 &lt;br&gt;
	 * SUMMARY -  Tests the notes function of subscribed cases by adding and deleting test text,&lt;br&gt;
	 * USER -  Legal &lt;br&gt;
	 */
	@Test
	@LEGAL
	@LOG
    public void addEditDeleteNoteFromSubscribedCasesTest()
    {
		homePage().goToHomePage();
		loginPage().logIn();
		
		boolean subscribedCasesPageAppeared = nodMenu().goToSubscribedCases();
		Assertions.assertTrue(subscribedCasesPageAppeared, "The Subscribed Cases page did not appear");
		
		// click add note link for remembered serial numnodMenuber case
		String serialNumber = subscribedCasesGridPage().getCaseSerialNumberOfCaseWithoutNote();
		boolean noteWindowAppeared = subscribedCasesGridPage().clickAddNoteLinkToCaseWithGivenCaseSerialNumber(serialNumber);
		Assertions.assertTrue(noteWindowAppeared, "Note window should be presented");
		
		// type text but hit cancel to check no note appeared
		notesPage().addNote(text1);
		notesPage().clickCancel();
		boolean noNoteAddedAfterCancellation = subscribedCasesGridPage().doesGivenCaseHaveAddNoteLink(serialNumber);
		Assertions.assertTrue(noNoteAddedAfterCancellation, "No note should appear after cancellation");
		
		// check that no text in textarea left after cancellation
		noteWindowAppeared = subscribedCasesGridPage().clickAddNoteLinkForCaseWithGivenCaseSerialNumber(serialNumber);
		Assertions.assertTrue(noteWindowAppeared, "Note window did not appear");

		boolean noTextLeftInTextareaAfterCancellation = notesPage().getNoteText().equals("");
		Assertions.assertTrue(noTextLeftInTextareaAfterCancellation, "No text should be left in the textarea after cancellation");
		
		// add text and save
		notesPage().addTextToNote(text1);
		notesPage().clickSave();
		boolean noteAdded = subscribedCasesGridPage().doesGivenCaseHaveNote(serialNumber);
		Assertions.assertTrue(noteAdded, "Note should appear");
		
		// check that textarea contains added text note
		subscribedCasesGridPage().clickNoteLinkOfCaseWithGivenCaseSerialNumber(serialNumber);
		boolean textIsBeingPresented = notesPage().getNoteText().equals(text1);
		Assertions.assertTrue(textIsBeingPresented, "Note text should be presented in the textarea");
		
		// add extra text but hit cancel
		notesPage().addNote(" " + text2);
		notesPage().clickCancel();
		
		// check that no extra text presented after cancellation
		noteWindowAppeared = subscribedCasesGridPage().clickNoteLinkOfCaseWithGivenCaseSerialNumber(serialNumber);
		Assertions.assertTrue(noteWindowAppeared, "Note window did not appear");

		boolean extraTextIsNotPresented = !notesPage().getNoteText().equals(text1 + " " + text2);
		Assertions.assertTrue(extraTextIsNotPresented, "Extra text should not be presented after cancellation");
		
		// add second part of text again and save
		notesPage().addTextToNote(" " + text2);
		notesPage().clickSave();

		subscribedCasesPage().closeCurrentWindowIgnoreDialogue();
		boolean homePageAppeared = homePage().switchToPage();
		Assertions.assertTrue(homePageAppeared, "The home page did not appear.");
		
		// text in textarea contains both text parts
		subscribedCasesPageAppeared = nodMenu().goToSubscribedCases();
		Assertions.assertTrue(subscribedCasesPageAppeared, "The subscribed cases page did not appear");

		noteWindowAppeared = subscribedCasesGridPage().clickNoteLinkOfCaseWithGivenCaseSerialNumber(serialNumber);
		Assertions.assertTrue(noteWindowAppeared, "Note window did not appear");

		boolean textareaPresentsBothParts = notesPage().getNoteText().equals(text1 + " " + text2);
		Assertions.assertTrue(textareaPresentsBothParts, "Extra text is presented");
		
		// clear textarea
		notesPage().clearTextArea();
		notesPage().clickSave();
		
		// note image disappeared
		boolean noNotePresentedAfterClearingTextarea = !subscribedCasesGridPage().doesGivenCaseHaveNote(serialNumber);
		Assertions.assertTrue(noNotePresentedAfterClearingTextarea, "Note should disappear");
    }
	
	/**
	 * STORY/BUGS - HALCYONST-1351&lt;br&gt;
	 * SUMMARY -  Adds a note to subscribed cases and then opens the headnote of the case to test note functionality inside headnotes.&lt;br&gt;
	 * USER -  Legal &lt;br&gt;
	 */
	@Test
	@LEGAL
	@LOG
    public void addEditDeleteNoteFromHeadnotesTest()
    {
		homePage().goToHomePage();
		loginPage().logIn();
		
		boolean windowAppeared = nodMenu().goToSubscribedCases();
		Assertions.assertTrue(windowAppeared, "Subscribed Cases Window did not appear");

		String serialNumber = subscribedCasesGridPage().getCaseSerialNumberOfCaseWithoutNote();
		boolean noteWindowAppeared = subscribedCasesGridPage().clickAddNoteLinkToCaseWithGivenCaseSerialNumber(serialNumber);
		Assertions.assertTrue(noteWindowAppeared, "Note window did not appear");

		notesPage().addNote(text1);
		notesPage().clickSave();
		boolean noteAdded = notesPage().doesGivenCaseHaveNote(serialNumber);
		Assertions.assertTrue(noteAdded, "Note should appear but did not");

		boolean headnotesWindowAppeared = subscribedCasesGridPage().clickCaseSerialNumberLink(serialNumber);
        Assertions.assertTrue(headnotesWindowAppeared, "Headnotes window should appear");

        boolean notesSectionDisplayed = headnotesPage().wasNoteCreated();
        Assertions.assertTrue(notesSectionDisplayed, "Note section should be presented");

        boolean editableTextarea = headnotesPage().getNoteText().equals(text1);
        Assertions.assertTrue(editableTextarea, "Editable area should display added note text");

        headnotesPage().clickEdit();
        headnotesPage().editNoteWithGivenText(" " + text2);
        headnotesPage().clickSave();

        boolean extraText = headnotesPage().getNoteText().equals(text1 + " " + text2);
        Assertions.assertTrue(extraText, "Extra text should be displayed in headnotes window");

        boolean subscribedCasesPageAppear = headnotesPage().clickSubscribedCasesBreadcrumb();
        Assertions.assertTrue(subscribedCasesPageAppear, "The subscribed cases page did not appear");

        noteWindowAppeared = subscribedCasesGridPage().clickNoteLinkOfCaseWithGivenCaseSerialNumber(serialNumber);
		Assertions.assertTrue(noteWindowAppeared, "Note window did not appear");

        boolean extraTextPresented = notesPage().getNoteText().equals(text1 + " " + text2);
		Assertions.assertTrue(extraTextPresented, "Extra text should be displayed in subscribed cases window");
		notesPage().clickCancel();

		headnotesWindowAppeared = subscribedCasesGridPage().clickCaseSerialNumberLink(serialNumber);
		Assertions.assertTrue(headnotesWindowAppeared, "Headnotes did not appear as expected");

		headnotesPage().clickEdit();
        headnotesPage().removeNoteFromHeadnotes();
        headnotesWindowAppeared = headnotesPage().clickSave();
        Assertions.assertTrue(headnotesWindowAppeared, "Headnotes page did not appear");

        headnotesPage().clickHeadnotesNoteCollapsible();
        boolean noteRemovedInHeadnotes = headnotesPage().getNoteText().equals("");
        Assertions.assertTrue(noteRemovedInHeadnotes, "Note should disappear in headnotes window");

        subscribedCasesPageAppear = headnotesPage().clickSubscribedCasesBreadcrumb();
        Assertions.assertTrue(subscribedCasesPageAppear, "Subscribed cases page did not appear as expected");

        subscribedCasesGridPage().waitForElementGoneForGivenCaseSerialNumber(serialNumber); 
        boolean noNotePresentedAfterClearingTextarea = !notesPage().doesGivenCaseHaveNote(serialNumber);
        Assertions.assertTrue(noNotePresentedAfterClearingTextarea, "Note should disappear in subscribed cases");
    }
}
