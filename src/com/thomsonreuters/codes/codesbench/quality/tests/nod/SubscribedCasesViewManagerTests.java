package com.thomsonreuters.codes.codesbench.quality.tests.nod;

import java.util.ArrayList;
import java.util.List;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestSetupEdge;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;

public class SubscribedCasesViewManagerTests extends TestService
{
	String viewName = "TEST_VIEW";

	/**
	 * STORY/BUGS - HALCYONST-1572 &lt;br&gt;
	 * SUMMARY -  checks the functionalit of the create, selection, and deletion functions of the view management window&lt;br&gt;
	 * USER -  Legal &lt;br&gt;
	 */
	@Test
	@LEGAL
	@LOG
	public void createSelectDeleteViewLegalTest()
	{	
		homePage().goToHomePage();
		loginPage().logIn();

		// Go to NOD -> Subscribed Cases
		boolean subscribedCasesWindowAppeared = nodMenu().goToSubscribedCases();
		Assertions.assertTrue(subscribedCasesWindowAppeared, "Subscribed Cases Window did not appear");

		// Open View Management
		boolean viewManagementIsOpened = subscribedCasesPage().openViewManagement();
		Assertions.assertTrue(viewManagementIsOpened, "View Management window is opened");
		boolean viewWizardPageAppeared = viewManagementPage().openViewCreationWizard();
		Assertions.assertTrue(viewWizardPageAppeared, "The view wizard page did not appear");

		viewWizardPage().addFilterToView("Reloaded", "N");
		viewWizardPage().clickNext();
		viewWizardPage().addSortToView("Case Serial #", "Ascending");
		viewWizardPage().clickNext();
		viewWizardPage().clickAddColumnsButton();
		viewWizardPage().clickNext();
		viewWizardPage().setViewname(viewName);
		viewWizardPage().setViewVisibilityToPublic();
		viewWizardPage().saveView();

		//select a view
		boolean viewManagementWindowAppeared = subscribedCasesPage().switchToViewManagementWindow();
		Assertions.assertTrue(viewManagementWindowAppeared, "The view management window appeared");
		viewManagementPage().selectView(viewName);

		//check the view in grid
		subscribedCasesWindowAppeared = subscribedCasesPage().switchToSubscribedCasesPage();
		Assertions.assertTrue(subscribedCasesWindowAppeared, "The subscribed cases window did not appear");
		boolean isView = subscribedCasesPage().compareExpectedView(viewName);

		//delete a view
		viewManagementWindowAppeared = subscribedCasesPage().openViewManagement();
		Assertions.assertTrue(viewManagementWindowAppeared, "The view management window did not appear");
		viewManagementPage().deleteView(viewName);
		viewManagementPage().closeViewManagementWindow();

		//check for view name in grid
		subscribedCasesWindowAppeared = subscribedCasesPage().switchToSubscribedCasesPage();
		Assertions.assertTrue(subscribedCasesWindowAppeared, "The Subscribed cases window did not appear");
		boolean isViewDefault = subscribedCasesPage().compareExpectedView("(none)");

		Assertions.assertAll
		(
				() -> Assertions.assertTrue(isView, "New view appears in Grid"),
				() -> Assertions.assertTrue(isViewDefault, "Default view appears in Grid")
		);
	}

	/**
	 * STORY/BUGS - HALCYONST-1577 &lt;br&gt;
	 * SUMMARY -  checks the functionality of the edit function the view management window&lt;br&gt;
	 * USER -  Legal &lt;br&gt;
	 */
	@Test
	@LEGAL
	@LOG
	public void editViewLegalTest()
	{
		// Sign in as Legal user
		homePage().goToHomePage();
		loginPage().logIn();

		// Go to NOD -> Subscribed Cases
		boolean subscribedCasesWindowAppeared = nodMenu().goToSubscribedCases();
		Assertions.assertTrue(subscribedCasesWindowAppeared, "Subscribed Cases Window did not appear");

		// Open View Management
		boolean viewManagementPageAppeared = subscribedCasesPage().openViewManagement();
		Assertions.assertTrue(viewManagementPageAppeared, "The View management window did not appear");
		boolean viewWizardPageAppeared = viewManagementPage().openViewCreationWizard();
		Assertions.assertTrue(viewWizardPageAppeared, "The view wizard page did not appear");

		viewWizardPage().addFilterToView("Reloaded", "N");
		viewWizardPage().clickNext();
		viewWizardPage().addSortToView("Case Serial #", "Ascending");
		viewWizardPage().clickNext();
		viewWizardPage().clickAddColumnsButton();
		viewWizardPage().clickNext();
		viewWizardPage().setViewname(viewName);
		viewWizardPage().setViewVisibilityToPublic();
		viewWizardPage().saveView();

		// edit view
		viewManagementPageAppeared = subscribedCasesPage().switchToViewManagementWindow();
		Assertions.assertTrue(viewManagementPageAppeared, "The view management window appeared");
		viewManagementPage().clickView(viewName);
		viewWizardPageAppeared = viewManagementPage().clickEditView();
		Assertions.assertTrue(viewWizardPageAppeared, "The view wizard did not appear");
		
		// change filter
		viewWizardPage().addFilterToView("Reloaded", "Y");

		// cancel
		viewManagementPage().closeViewManagementWindow();

		// edit again
		viewManagementPageAppeared = subscribedCasesPage().openViewManagement();
		Assertions.assertTrue(viewManagementPageAppeared, "The View management window did not appear");
		viewManagementPage().clickView(viewName);
		viewWizardPageAppeared = viewManagementPage().clickEditView();
		Assertions.assertTrue(viewWizardPageAppeared, "The view wizard did not appear");
		
		//no weird stuff like Data Error and empty grid
		boolean noDataErrorMessage = !viewManagementPage().didDataErrorMessageAppear();
		Assertions.assertTrue(noDataErrorMessage, "No Data error message should be presented");
		boolean filterIsDisplayed = viewManagementPage().isFilterDisplayed();
		Assertions.assertTrue(filterIsDisplayed, "Filter should be displayed");

		// cancel and delete
		viewManagementPage().clickClose();
		viewManagementPage().acceptAlert();
		viewManagementPageAppeared = subscribedCasesPage().switchToViewManagementWindow();
		Assertions.assertTrue(viewManagementPageAppeared, "The view management page did not appear");
		viewManagementPage().deleteView(viewName);
		viewManagementPage().closeViewManagementWindow();
	}

	/**
	 * STORY/BUGS - HALCYONST-1576 &lt;br&gt;
	 * SUMMARY -  checks the functionality excel export feature of the view management window&lt;br&gt;
	 * USER -  Legal &lt;br&gt;
	 */
	@Test
	@LEGAL
	@LOG
	public void excelExportWithCustomViewLegalTest()
	{
		String dateToFilter = "04/06/2006";

		// Sign in as Legal user
		homePage().goToHomePage();
		loginPage().logIn();

		// Go to NOD -> Subscribed Cases
		boolean subscribedCasesWindowAppeared = nodMenu().goToSubscribedCases();
		Assertions.assertTrue(subscribedCasesWindowAppeared, "Subscribed Cases Window did not appear");

		// Open View Management
		boolean viewManagmentAppeared = subscribedCasesPage().openViewManagement();
		Assertions.assertTrue(viewManagmentAppeared, "The view management window did not appear");

		viewManagementPage().deleteView(viewName);
		//boolean deleteViewAlertAppeared = AutoITUtils.verifyAlertTextAndAccept(isAlertExpected, "Are you sure you would like to delete the selected view?");
		//Assertions.assertTrue(deleteViewAlertAppeared, "The view deletion alert did not appear.");
		viewManagementPage().openViewCreationWizard();

		// add first filter for Loaded date
		viewWizardPage().addDateFilterWithGivenValue("Loaded Date", dateToFilter);

		// add second filter 
		viewWizardPage().addFilterToView("R/U", "R");
		viewWizardPage().clickNext();
		
		// add sort
		viewWizardPage().addSortToView("Loaded Date", "Ascending");
		viewWizardPage().clickNext();
		
		//Move columns
		viewWizardPage().clickAddColumnsButton();
		viewWizardPage().clickNext();
		
		// finish creating
		viewWizardPage().setViewname(viewName);
		viewWizardPage().setViewVisibilityToPublic();
		viewWizardPage().saveView();
		
		// select view
		viewManagmentAppeared = subscribedCasesPage().switchToViewManagementWindow();
		Assertions.assertTrue(viewManagmentAppeared, "The view management window appeared");
		viewManagementPage().selectView(viewName);
		//check the view in grid
		subscribedCasesWindowAppeared = subscribedCasesPage().switchToSubscribedCasesPage();
		Assertions.assertTrue(subscribedCasesWindowAppeared, "The subscribed cases page did not appear");

		// check view matching
		List<String> ruGridColumn = viewManagementPage().getRuGridColumn();
		List<String> loadedDateGridColumn = viewManagementPage().getLoadedDateGridColumn();
		boolean ruColumnMatchView = ruGridColumn.stream().allMatch(ru -> ru.equals("R"));
		boolean loadedDateColumnMatchView = loadedDateGridColumn.stream().allMatch(ld -> ld.equals(dateToFilter));
		Assertions.assertTrue(ruColumnMatchView && loadedDateColumnMatchView, "Grid should match view selected");

		// grab content from grid
		ArrayList<String> gridContents = subscribedCasesPage().createListForSubscribedCasesGridContents();

		// grab content of excel
		subscribedCasesPage().downdloadExcelFileForCurrentGridView();
		ArrayList<String> infoGrabbedFromExcel = subscribedCasesPage().grabContentsFromSubscribedCasesGridExcel();

		boolean excelMatchesGrid = gridContents.equals(infoGrabbedFromExcel);
		Assertions.assertTrue(excelMatchesGrid, "Excel file content equals grid with view selected");

		// delete view
		subscribedCasesWindowAppeared = subscribedCasesPage().switchToSubscribedCasesPage();
		Assertions.assertTrue(subscribedCasesWindowAppeared, "The subscribed cases window did not appear");
		viewManagmentAppeared = subscribedCasesPage().openViewManagement();
		Assertions.assertTrue(viewManagmentAppeared, "The view management window did not appear");
		viewManagementPage().deleteView(viewName);
		viewManagementPage().closeViewManagementWindow();       
	}

	/**
	 * STORY/BUGS - HALCYONST-1559 <br>
	 * SUMMARY -  checks the functionality of the Set as Default in the view management window <br>
	 * USER -  Legal <br>;
	 */
	@Test
	@LEGAL
	@LOG
	public void viewManagerSetAsDefaultAndRemove()
	{
		String viewName = "Automated_Default_View" + DateAndTimeUtils.getCurrentDateMMddyyyy();

		// Sign in as Legal user
		homePage().goToHomePage();
		loginPage().logIn();

		//Go through the nod subscribed cases page to the view wizard page to create a new view
		boolean subscribedCasesWindowAppeared = nodMenu().goToSubscribedCases();
		Assertions.assertTrue(subscribedCasesWindowAppeared, "The subscribed cases window did not appear");
		boolean viewManagementWindowAppeared = subscribedCasesPage().openViewManagement();
		Assertions.assertTrue(viewManagementWindowAppeared, "The view management window did not appear");
		boolean viewWizardPageAppeared = viewManagementPage().openViewCreationWizard();
		Assertions.assertTrue(viewWizardPageAppeared, "The view wizard page did not appear");

		//creating new view
		viewWizardPage().addFilterToView("R/U", "R");
		viewWizardPage().addFilterToView("Reloaded", "Y");
		viewWizardPage().clickNext();
		viewWizardPage().addSortToView("R/U", "Ascending");
		viewWizardPage().clickNext();
		viewWizardPage().clickAddColumnsButton();
		viewWizardPage().clickNext();
		viewWizardPage().setViewname(viewName);
		viewWizardPage().setViewVisibilityToPublic();
		viewWizardPage().saveView();

		//setting newly created view to default
		viewManagementPage().setViewAsDefault(viewName);

		boolean isExpectedViewSetAsDefault = viewManagementPage().isViewDefault(viewName);
		Assertions.assertTrue(isExpectedViewSetAsDefault, "The Expected view was NOT set as default");

		//closing IE and reopening to verify new view stays as default
		TestSetupEdge.closeBrowser();
		TestSetupEdge.openBrowser();
		homePage().goToHomePage();
		loginPage().logIn();
		subscribedCasesWindowAppeared = nodMenu().goToSubscribedCases();
		Assertions.assertTrue(subscribedCasesWindowAppeared, "The subscribed cases window did not appear");
		String newDefaultView = subscribedCasesPage().getCurrentViewName();
		boolean isNewViewSetAsDefault = newDefaultView.equals("Current view: " + viewName);
		Assertions.assertTrue(isNewViewSetAsDefault, "The newly created view did not stay set as default");

		//Deleting view for cleanup
		viewManagementWindowAppeared = subscribedCasesPage().openViewManagement();
		Assertions.assertTrue(viewManagementWindowAppeared, "The view management window did not appear");
		viewManagementPage().deleteView(viewName);
		viewManagementPage().closeViewManagementWindow();
		subscribedCasesWindowAppeared = subscribedCasesPage().switchToSubscribedCasesPage();
		Assertions.assertTrue(subscribedCasesWindowAppeared, "The subscribed cases window did not appear after closing view management window");

		//Validating that the default view is reset to (none)
		String viewAfterDeletion = subscribedCasesPage().getCurrentViewName();
		boolean currentViewIsResetToNone = viewAfterDeletion.equals("Current view: (none)");
		Assertions.assertEquals("Current view: (none)", viewAfterDeletion, "The current view is not (none)");
	}
}
