package com.thomsonreuters.codes.codesbench.quality.tests.nod;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;

public class SubscribedCasesBluelinesTests extends TestService
{
	/**
	 * STORY/BUGS - http://ent.jira.int.thomsonreuters.com/browse/HALCYONST-1232 bug &lt;br&gt;
	 * SUMMARY -  Checks the functionality of switching content sets in the Cases page&lt;br&gt;
	 * USER -  Legal &lt;br&gt;
	 */
    @Test
    @LEGAL
    @LOG
    public void nodSubscribedCasesBluelinesTest()
    {
    	String targetSection = "§ 16";
    	String quickSearchQuery = "a I s 16";
    	String incorrectlySpelledWord = "gobbletygook";
    	String correctlySpelledWord = "banana";
    	
    	// go to Subscribed Cases
    	homePage().goToHomePage();
		loginPage().logIn();
		
    	boolean subscribedCasesWindowAppeared = nodMenu().goToSubscribedCases();
    	Assertions.assertTrue(subscribedCasesWindowAppeared, "Subscribed Cases Window did not appear");

		// open first Case and perform Quick search
    	String caseSerialNumber = subscribedCasesGridPage().getCaseSerialNumberFromSubscribedCasesByGivenRow(1);
    	boolean headnotesWindowAppeared = subscribedCasesGridPage().clickCaseSerialNumberLink(caseSerialNumber);
    	Assertions.assertTrue(headnotesWindowAppeared, "The Headnotes window did not appear");
		headnotesSearchPage().setQuickFindField(quickSearchQuery);
		headnotesSearchPage().clickQuickFindButton();
		boolean findPageAppeared = findPage().switchToFindPage();
		Assertions.assertTrue(findPageAppeared, "The find page did not appear");
		findPage().clickFirstFindResult();
		headnotesWindowAppeared = headnotesPage().switchToHeadnotesPage();
		Assertions.assertTrue(headnotesWindowAppeared, "The Headnotes window did not appear");
		boolean treeControlGoesToSection = headnotesTreePage().isNodeHighlightedInTree(targetSection);

		//insert first BL
		headnotesTreePage().rightClickNodeWithGivenText(targetSection);
		boolean insertBluelinePageAppeared = headnotesContextMenu().insertBlueline();
		Assertions.assertTrue(insertBluelinePageAppeared, "The insert blueline page did not appear");
		insertBluelinePage().setBlueLineNumber("1");
		insertBluelinePage().clickOK();
		insertBluelinePage().selectBlueLineTypeFlush();
		insertBluelinePage().setBlueLineText(incorrectlySpelledWord);
		insertBluelinePage().clickOK();

		boolean spellCheckAppearedFirstCase = insertBluelinePage().didSpellcheckErrorMessageAppear();
		headnotesWindowAppeared = insertBluelinePage().clickFinish();
		Assertions.assertTrue(headnotesWindowAppeared, "The headnotes window did not appear");
		
		boolean treeControlGoesToInsertedBlueLineFirstCase = headnotesTreePage().isNodeHighlightedInTree(incorrectlySpelledWord);

		//insert second BL
		headnotesSearchPage().setQuickFindField(quickSearchQuery);
		headnotesSearchPage().clickQuickFindButton();
		findPageAppeared = findPage().switchToFindPage();
		Assertions.assertTrue(findPageAppeared, "The find page did not appear");
		findPage().enterTheInnerFrame();
		findPage().clickFirstFindResult();
		headnotesWindowAppeared = headnotesPage().switchToHeadnotesPage();
		Assertions.assertTrue(headnotesWindowAppeared, "The Headnotes window did not appear");

		headnotesTreePage().rightClickNodeWithGivenText(targetSection);
		insertBluelinePageAppeared = headnotesContextMenu().insertBlueline();
		Assertions.assertTrue(insertBluelinePageAppeared, "The insert blueline page did not appear");
		insertBluelinePage().enterTheInnerFrame();
		insertBluelinePage().setBlueLineNumber("2");
		insertBluelinePage().clickOK();
		insertBluelinePage().selectBlueLineTypeFlush();
		insertBluelinePage().setBlueLineText(correctlySpelledWord);
		insertBluelinePage().clickOK();
		boolean spellCheckAppearedSecondCase = insertBluelinePage().didSpellcheckErrorMessageAppear();

		headnotesWindowAppeared = headnotesPage().switchToHeadnotesPage();
		Assertions.assertTrue(headnotesWindowAppeared, "Hadnotes window did not appear when it should have");
		boolean treeControlGoesToInsertedBlueLineSecondCase = headnotesTreePage().isNodeHighlightedInTree(correctlySpelledWord);

		// delete first BL
		headnotesPage().switchToHeadnotesPage();
		headnotesTreePage().rightClickNodeWithGivenText(incorrectlySpelledWord);
		headnotesContextMenu().deleteBlueline();
		deleteBluelinePage().switchToDeleteBluelineWindow();
		deleteBluelinePage().enterTheInnerFrame();
		deleteBluelinePage().clickOk();
       
		// delete second BL
		headnotesPage().switchToHeadnotesPage();
		headnotesTreePage().rightClickNodeWithGivenText(correctlySpelledWord);
		headnotesContextMenu().deleteBlueline();
		deleteBluelinePage().switchToDeleteBluelineWindow();
		deleteBluelinePage().enterTheInnerFrame();
		deleteBluelinePage().clickOk();
		
		boolean treeControlGoesToOriginalSection = headnotesTreePage().isNodeHighlightedInTree(targetSection);

		Assertions.assertAll
		(
			() -> Assertions.assertTrue(subscribedCasesWindowAppeared, "Subscribed Cases window appeared"),
			() -> Assertions.assertTrue(treeControlGoesToSection, "The intended node searched for was highlighted"),
			() -> Assertions.assertTrue(spellCheckAppearedFirstCase, "A spell check message did not appeare after trying to insert a blue line with a spelling mistake"),
			() -> Assertions.assertTrue(treeControlGoesToInsertedBlueLineFirstCase, "The inserted blue line was highlighted"),
			() -> Assertions.assertFalse(spellCheckAppearedSecondCase, "A spell check message appeared after trying to insert a blue line with a spelling mistake"),
			() -> Assertions.assertTrue(treeControlGoesToInsertedBlueLineSecondCase, "The inserted blue line was highlighted"),
			() -> Assertions.assertTrue(treeControlGoesToOriginalSection, "The original node was highlighted after deleting the blue line")
		);
    }
    
    /**
	 * STORY/BUGS - HALCYONST-1467 (1232 / 1133) &lt;br&gt;
	 * SUMMARY -  Checks the functionality of switching content sets in the Cases page&lt;br&gt;
	 * USER -  Legal &lt;br&gt;
	 */
    @Test 
    @LEGAL
    @LOG
    public void nodSubscribedCasesBluelinesInsertFlushBluelineIndexEditDeleteTest()
    {
    	String targetSection = "§ 16";
    	String quickSearchQuery = "a I s 16";
    	String text = "Test";
    	String secondText = text + "2";
    	String additionalText = " Banana";
    	
    	homePage().goToHomePage();
		loginPage().logIn();
    	
    	// go to Subscribed Cases
    	nodMenu().goToSubscribedCases();
		boolean subscribedCasesWindowAppeared = subscribedCasesPage().switchToSubscribedCasesPage();
		Assertions.assertTrue(subscribedCasesWindowAppeared, "Subscribed Cases Window did not appear");
		
		// open first Case and perform Quick search
		String caseSerialNumber = subscribedCasesGridPage().getCaseSerialNumberFromSubscribedCasesByGivenRow(1);
		boolean headnotesWindowAppeared = subscribedCasesGridPage().clickCaseSerialNumberLink(caseSerialNumber);
		Assertions.assertTrue(headnotesWindowAppeared, "The headnotes window did not appear");
		headnotesSearchPage().setQuickFindField(quickSearchQuery);
		headnotesSearchPage().clickQuickFindButton();
		boolean findPageAppeared = findPage().switchToFindPage();
		Assertions.assertTrue(findPageAppeared, "Find Page did not appear");
		findPage().clickFirstFindResult();
		headnotesWindowAppeared = headnotesPage().switchToHeadnotesPage();
		Assertions.assertTrue(headnotesWindowAppeared, "The Headnotes window did not appear");
		boolean treeControlGoesToSection = headnotesTreePage().isNodeHighlightedInTree(targetSection);

		//insert BL
		headnotesWindowAppeared = headnotesPage().switchToHeadnotesPage();
		Assertions.assertTrue(headnotesWindowAppeared, "Could not switch to headnotes page");
		headnotesTreePage().rightClickNodeWithGivenText(targetSection);
		boolean insertBluelineWindowAppeared = headnotesContextMenu().insertBlueline();
		Assertions.assertTrue(insertBluelineWindowAppeared, "The insert blueline page did not appear");
		insertBluelinePage().setBlueLineNumber("1");
		insertBluelinePage().clickOK();
		insertBluelinePage().selectBlueLineTypeFlush();
		insertBluelinePage().setBlueLineText(text);
		insertBluelinePage().clickOK();
		headnotesWindowAppeared = headnotesPage().switchToHeadnotesPage();
		Assertions.assertTrue(headnotesWindowAppeared, "The headnotes window did not appear");
		
		boolean dropdownHintsDisplayCorrectValues =  headnotesTreePage().compareActualTextHintsToExpected(text);
		
		headnotesSearchPage().setQuickFindField(quickSearchQuery);
		headnotesSearchPage().clickQuickFindButton();
		findPageAppeared = findPage().switchToFindPage();
		Assertions.assertTrue(findPageAppeared, "Find page did not appear");
		findPage().clickFirstFindResult();
		headnotesWindowAppeared = headnotesPage().switchToHeadnotesPage();
		Assertions.assertTrue(headnotesWindowAppeared, "The Headnotes window did not appear");
		
		headnotesTreePage().editBluelineWithGivenText(text);
		boolean flushAnalysisTextContainsExpectedText = editBluelinePage().checkFlushAnalysisTextFieldWithGivenText(text);
		
		// add extra flush analysis
		boolean additionalFlushAnalysisTextFieldAppeared = editBluelinePage().didAdditionalFlusAnalysisTextFieldAppear();
		editBluelinePage().addAdditionalFlushAnalysisText(secondText);
		editBluelinePage().clickOK();

		// BL appeared
		headnotesWindowAppeared = headnotesPage().switchToHeadnotesPage();
		Assertions.assertTrue(headnotesWindowAppeared, "Headnotes window did not appear");
		boolean blueLineAppeared = headnotesTreePage().isNodeHighlightedInTree(text);
		
        // edit BL
		headnotesSearchPage().setQuickFindField(quickSearchQuery);
		headnotesSearchPage().clickQuickFindButton();
		findPageAppeared = findPage().switchToFindPage();
		Assertions.assertTrue(findPageAppeared, "The find page did not appear");
		findPage().clickFirstFindResult();
		headnotesWindowAppeared = headnotesPage().switchToHeadnotesPage();
		Assertions.assertTrue(headnotesWindowAppeared, "The Headnotes window did not appear");
		headnotesTreePage().editBluelineWithGivenText(text);
		boolean editBluelineWindowAppeared = editBluelinePage().switchToEditBluelineWidow();
		Assertions.assertTrue(editBluelineWindowAppeared, "The edit blueline window did not appear");
		boolean typeIsFlush = editBluelinePage().isBluelineTypeFlush();
		boolean textIsAsExpected = editBluelinePage().isTextFieldValueSameAsExpected(text);
		boolean flushAnalysisTextIsAsExpected = editBluelinePage().isFlushAnalysisFieldValueSameAsExpected(text);
		boolean secondFlushAnalysisTextIsAsExpected = editBluelinePage().isAdditionalFlushAnalysisFieldValueSameAsExpected(secondText);
		
		// change text
		editBluelinePage().updateBluelineTextFieldValues(additionalText);
		boolean flushAnalysisTextIsUpdated = editBluelinePage().isFlushAnalysisFieldValueSameAsExpected(text + additionalText);
		boolean secondFlushAnalysisTextIsStillTheSame = editBluelinePage().isAdditionalFlushAnalysisFieldValueSameAsExpected(secondText);
		editBluelinePage().clickOK();
		
		// check BL is updated in tree
		headnotesWindowAppeared = headnotesPage().switchToHeadnotesPage();
		Assertions.assertTrue(headnotesWindowAppeared, "The headnotes window did not appear");
		boolean blueLineChanged = headnotesTreePage().isNodeHighlightedInTree(text + additionalText);
		
		// delete BL created
		headnotesTreePage().rightClickNodeWithGivenText(text + additionalText);
		headnotesContextMenu().deleteBlueline();
		deleteBluelinePage().clickOK();
		
        // assertions
		Assertions.assertAll
		(
			() -> Assertions.assertTrue(treeControlGoesToSection, "The node searched for was highlighted"),
			() -> Assertions.assertTrue(insertBluelineWindowAppeared, "insert Blueline Window Appeared"),
			() -> Assertions.assertTrue(dropdownHintsDisplayCorrectValues, "dropdown Hints Display Correct Values"),
			() -> Assertions.assertTrue(flushAnalysisTextContainsExpectedText, "flush Analysis Text Contains Expected Text"),
			() -> Assertions.assertTrue(additionalFlushAnalysisTextFieldAppeared, "additional Flush Analysis Text Field Appeared"),
			() -> Assertions.assertTrue(blueLineAppeared, "tree Control Goes To Inserted BlueLine FirstCase"),
			() -> Assertions.assertTrue(editBluelineWindowAppeared, "edit Blueline Window Appeared"),
			() -> Assertions.assertTrue(typeIsFlush, "type Is Flush"),
			() -> Assertions.assertTrue(textIsAsExpected, "text Is As Expected"),
			() -> Assertions.assertTrue(flushAnalysisTextIsAsExpected, "flush Analysis Text Is As Expected"),
			() -> Assertions.assertTrue(secondFlushAnalysisTextIsAsExpected, "second Flush Analysis Text Is As Expected"),
			() -> Assertions.assertTrue(flushAnalysisTextIsUpdated, "flush Analysis Text Is Updated"),
			() -> Assertions.assertTrue(secondFlushAnalysisTextIsStillTheSame, "second Flush Analysis Text Is Still The Same"),
			() -> Assertions.assertTrue(blueLineChanged, "blue Line Changed")
		);
    }

    /**
	 * STORY/BUGS - HALCYONST-1468 &lt;br&gt;
	 * SUMMARY -  Checks the functionality of switching content sets in the Cases page&lt;br&gt;
	 * USER -  Legal &lt;br&gt;
	 */
    @Test
    @LEGAL
    @LOG
    public void nodSubscribedCasesInsertStartOfIndentAndIndentBluelinesAndDeleteTest()
    {
    	String targetSection = "§ 16";
    	String quickSearchQuery = "a I s 16";
    	String text = "Bananas";
    	String secondText = "Pimple";
    	String thirdText = "Avocados";
    	
    	homePage().goToHomePage();
		loginPage().logIn();
    	
    	// go to Subscribed Cases
		boolean subscribedCasesWindowAppeared = nodMenu().goToSubscribedCases();
		Assertions.assertTrue(subscribedCasesWindowAppeared, "Subscribed Cases Window did not appear");
		
		// open first Case and perform Quick search
		String caseSerialNumber = subscribedCasesGridPage().getCaseSerialNumberFromSubscribedCasesByGivenRow(1);
		boolean headnotesWindowAppeared = subscribedCasesGridPage().clickCaseSerialNumberLink(caseSerialNumber);
		Assertions.assertTrue(headnotesWindowAppeared, "The Headnotes page did not appear");
		headnotesSearchPage().setQuickFindField(quickSearchQuery);
		headnotesSearchPage().clickQuickFindButton();
		boolean findPageAppeared = findPage().switchToFindPage();
		Assertions.assertTrue(findPageAppeared, "The find page did not appear");
		findPage().clickFirstFindResult();
		headnotesWindowAppeared = headnotesPage().switchToHeadnotesPage();
		Assertions.assertTrue(headnotesWindowAppeared, "The Headnotes window did not appear");
		headnotesPage().switchToHeadnotesPage();
		boolean treeControlGoesToSection = headnotesTreePage().isNodeHighlightedInTree(targetSection);

		//insert start of indent BL
		headnotesTreePage().rightClickNodeWithGivenText(targetSection);
		boolean insertBluelineWindowAppeared = headnotesContextMenu().insertBlueline();
		Assertions.assertTrue(insertBluelineWindowAppeared, "Insert blueline window did not appear");
		insertBluelinePage().setBlueLineNumber("1");
		insertBluelinePage().clickOK();
		insertBluelinePage().selectBlueLineTypeStartOfIndent();
		
		// check ui
		boolean dropdownButtonsAppeared = insertBluelinePage().dropdownsAppeared();
		insertBluelinePage().setFirstIndentTextField(text);
		insertBluelinePage().setSecondIndentTextField(secondText);
		insertBluelinePage().refreshFlushAnalysisFields();
		boolean flushAnalysisTextIsAsExpected = insertBluelinePage().checkIfFirstFlushFieldValueIsAsExpected(text);
		boolean secondFlushAnalysisTextIsAsExpected = insertBluelinePage().checkIfSecondFlushFieldValueIsAsExpected(secondText);
		insertBluelinePage().clickOK();
		
		// BL appeared
		headnotesPage().switchToHeadnotesPage();
		boolean blueLineAppeared = headnotesTreePage().isNodeHighlightedInTree(text+"-"+secondText);
		
		// insert indent BL
		headnotesTreePage().rightClickNodeWithGivenText(targetSection);
		insertBluelineWindowAppeared = headnotesContextMenu().insertBlueline();
		Assertions.assertTrue(insertBluelineWindowAppeared, "Insert blueline window did not appear");
		insertBluelinePage().setBlueLineNumber("1.5");
		insertBluelinePage().clickOK();
	
		// check ui
		insertBluelinePage().selectBlueLineTypeIndent();
		boolean indentTypeIsEnabled = insertBluelinePage().isIndentTypeEnabled();
		boolean parentValue = insertBluelinePage().isIndentParentValueSameAsExpected(text);
		boolean indentTextsAndDropdownAreDisplayed = insertBluelinePage().areIndentTextsAndDropdownDisplayed(text);
		boolean indentFlushAnalysisFieldsAreDisplayed = insertBluelinePage().areindentFlushAnalysisFieldsDisplayed(text);
				
		boolean indentIndentAnalysisIsDisplayed = insertBluelinePage().isIndentIndentAnalysisDisplayed();
		
		// update things
		insertBluelinePage().updateInsertBluelineIndentTextField(thirdText);
		boolean indentFirstFlushAnalysisFieldsUpdated = insertBluelinePage().checkFieldValueOfIndentFirstFlushAnalysis(thirdText);
		boolean indentIndentAnalysisFieldsUpdated =	insertBluelinePage().checkFieldValueOfIndentTextField(thirdText);
		insertBluelinePage().clickOK();
		
		// bl appeared
		headnotesWindowAppeared = headnotesPage().switchToHeadnotesPage();
		Assertions.assertTrue(headnotesWindowAppeared, "The headnotes window did not appear");
		boolean secondBlueLineAppeared = headnotesTreePage().isNodeHighlightedInTree(thirdText+", "+text.toLowerCase());
		Assertions.assertTrue(secondBlueLineAppeared, "Blueline is not highlighted when it should be");
		
		// delete BLs created
		headnotesTreePage().rightClickNodeWithGivenText(thirdText+", "+text.toLowerCase());
		headnotesContextMenu().deleteBlueline();
		deleteBluelinePage().clickOK();
		headnotesWindowAppeared = headnotesPage().switchToHeadnotesPage();
		Assertions.assertTrue(headnotesWindowAppeared, "The headnotes window did not appear");
		
		headnotesTreePage().rightClickNodeWithGivenText(text+"-"+secondText);
		headnotesContextMenu().deleteBlueline();
		deleteBluelinePage().clickOK();
		
		boolean bluelinesDeleted =
				  !headnotesTreePage().isBluelineWithGivenValuesDeleted(text, secondText)
				& !headnotesTreePage().isBluelineWithGivenValuesDeleted(thirdText, text);
		
        // assertions
		Assertions.assertAll
		(
			() -> Assertions.assertTrue(treeControlGoesToSection, "The node searched for was highlighted"),
			() -> Assertions.assertTrue(dropdownButtonsAppeared, "dropdown buttons should be displayed"),
			() -> Assertions.assertTrue(flushAnalysisTextIsAsExpected, "Flush analysis text contains expected text"),
			() -> Assertions.assertTrue(secondFlushAnalysisTextIsAsExpected, "Second flush analysis Text field appeared"),
			() -> Assertions.assertTrue(blueLineAppeared, "Tree control goes to inserted blueLine"),
			() -> Assertions.assertTrue(indentTypeIsEnabled, "Indent type is not enabled"),
			() -> Assertions.assertTrue(parentValue, "Parent value should display correct value"),
			() -> Assertions.assertTrue(indentTextsAndDropdownAreDisplayed, "Text fields should be displayed correctly"),
			() -> Assertions.assertTrue(indentFlushAnalysisFieldsAreDisplayed, "Indent flush analysis text fields should be displayed correctly"),
			() -> Assertions.assertTrue(indentIndentAnalysisIsDisplayed, "Indent text fields should be displayed correctly"),
			() -> Assertions.assertTrue(indentFirstFlushAnalysisFieldsUpdated, "Flush analysis text fields should be updated"),
			() -> Assertions.assertTrue(indentIndentAnalysisFieldsUpdated, "Indent analysis text fields should be updated"),
			() -> Assertions.assertTrue(secondBlueLineAppeared, "Indent BL should appear in the tree"),
			() -> Assertions.assertTrue(bluelinesDeleted, "BLs should be removed")
        );
    }
}
