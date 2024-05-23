package com.thomsonreuters.codes.codesbench.quality.tests.tools;

import java.util.Arrays;
import java.util.List;

import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.thomsonreuters.codes.codesbench.quality.pageelements.CommonPageElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.regex.RegexUtils;

@TestMethodOrder(OrderAnnotation.class)
public class KeyCiteResearchReferencesTests extends TestService
{
	private static final List<String> SELECTED_ANALYTICAL_TITLES = Arrays.asList
	(
		"Administered Laws",
		"Administration of Decedents Estates--Wests Texas Forms",
		"Administrative Law and Practice"
	);
	private static final List<String> SELECTED_VOLUMES = Arrays.asList
	(
		"0010",
		"0030",
		"0035"
	);
	private static final String CONTENT_SET = "Iowa (Development)";

	/**
	 * STORY/BUG - N/A <br>
	 * SUMMARY - Goes to Key Cite Research References, selects analytical titles and verifies they are selected. Then sets
	 * them as default and goes to the next page. Adds volumes and verifies they are moved over. Clicks
	 * submit. To anticipate the inconsistency in the time it takes for workflows to finish, simply verifies
	 * workflow ID is valid and the a workflow is kicked off.  <br>
	 * USER - Legal <br>
	 */
	@Test
	@Order(1)
	@IE_EDGE_MODE
	@LEGAL
	@LOG
	public void kcrrSelectTitlesAndVolumesWorkflowKicksOffLegalTest()
	{
		homePage().goToHomePage();
		loginPage().logIn();

		boolean keyCiteExtractWindowAppeared = toolsMenu().goToKeyCiteExtract();
		Assertions.assertTrue(keyCiteExtractWindowAppeared, "Key Cite extract was opened.");

		keyCiteResearchReferencesFiltersPage().setContentSetLegacyId(CONTENT_SET);
		keyCiteResearchReferencesFiltersPage().checkKeyRules();
		keyCiteResearchReferencesFiltersPage().checkAmericanLawReportAnnotations();
		keyCiteResearchReferencesFiltersPage().checkTreatiesDocuments();
		keyCiteResearchReferencesFiltersPage().checkLegalEncyclopediaDocuments();
		keyCiteResearchReferencesFiltersPage().checkOtherSecondarySources();
		keyCiteResearchReferencesFiltersPage().uncheckDefaultAnalyticalTitles();
		keyCiteResearchReferencesPage().clickEmptySelectedAnalyticalTitles();
		keyCiteResearchReferencesPage().selectAvailableAnalyticalTitles(SELECTED_ANALYTICAL_TITLES);
		keyCiteResearchReferencesPage().clickAddOneAnalyticalTitleButton();
		boolean correctAnalyticalTitlesAreSelected = keyCiteResearchReferencesPage().verifySelectedAnalyticalTitlesMatch(SELECTED_ANALYTICAL_TITLES);
		Assertions.assertTrue(correctAnalyticalTitlesAreSelected, "The desired analytical titles were selected.");

		keyCiteResearchReferencesPage().clickSaveDefaultTitlesButton();
		boolean isDefaultAnalyticalTitlesCheckboxChecked = keyCiteResearchReferencesFiltersPage().isDefaultAnalyticalTitlesCheckboxChecked();
		boolean isAvailableAnalyticalTitlesSelectDisabled = keyCiteResearchReferencesPage().isAvailableAnalyticalTitlesSelectDisabled();
		boolean isSelectedAnalyticalTitlesSelectDisabled = keyCiteResearchReferencesPage().isSelectedAnalyticalTitlesSelectDisabled();

		Assertions.assertAll
		(
			() -> Assertions.assertTrue(isDefaultAnalyticalTitlesCheckboxChecked, "Default analytical titles checkbox should be checked."),
			() -> Assertions.assertTrue(isAvailableAnalyticalTitlesSelectDisabled, "Available analytical titles select should be disabled."),
			() -> Assertions.assertTrue(isSelectedAnalyticalTitlesSelectDisabled, "Selected analytical titles select should be disabled.")
		);

		keyCiteResearchReferencesFiltersPage().setContentSetLegacyId(CONTENT_SET);
		keyCiteResearchReferencesFiltersPage().checkKeyRules();
		keyCiteResearchReferencesFiltersPage().checkAmericanLawReportAnnotations();
		keyCiteResearchReferencesFiltersPage().checkTreatiesDocuments();
		keyCiteResearchReferencesFiltersPage().checkLegalEncyclopediaDocuments();
		keyCiteResearchReferencesFiltersPage().checkOtherSecondarySources();
		keyCiteResearchReferencesPage().selectAvailableVolumes(SELECTED_VOLUMES);
		keyCiteResearchReferencesPage().clickAddOneVolumeButton();
		boolean correctVolumesAreSelected = keyCiteResearchReferencesPage().verifySelectedVolumesMatch(SELECTED_VOLUMES);
		Assertions.assertTrue(correctVolumesAreSelected, "The desired volumes were selected.");

		keyCiteResearchReferencesPage().clickSubmitButton(true);

		DateAndTimeUtils.takeNap(DateAndTimeUtils.FIVE_SECONDS);

		boolean workflowKicksOff = keyCiteResearchReferencesPage().doesWorkflowKickOff();
		Assertions.assertTrue(workflowKicksOff, "Workflow was kicked off successfully.");

		String workflowId = keyCiteResearchReferencesPage().getWorkflowId();
		boolean validWorkflowId = RegexUtils.matchesRegex(workflowId, CommonPageElements.VALID_WORKFLOW_ID_REGEX);
		Assertions.assertTrue(validWorkflowId, "A valid workflow id was generated.");

		homePage().switchToPage();
		toolsMenu().goToWorkflowReportingSystem();
		workflowSearchPage().setWorkflowID(workflowId);
		workflowSearchPage().clickFilterButton();
		boolean workflowAppears = workflowId.equals(workflowSearchPage().getWorkflowIdOfFirstWorkflow());
		Assertions.assertTrue(workflowAppears, "The workflow finished. id: " + workflowId);
	}

	/**
	 * STORY/BUG - N/A <br>
	 * SUMMARY - Goes to Key Cite Research References and verifies the default analytical titles are the same as the previous
	 * test set. Then goes to the next page, adds volumes and verifies they are moved over. Then runs
	 * workflow and verifies the correct message appears. To anticipate the inconsistency in the time
	 * it takes for workflows to finish, simply verifies workflow ID is valid and that the workflow is
	 * kicked off. <br>
	 * USER - Legal <br>
	 */
	@Test
	@Order(2)
	@IE_EDGE_MODE
	@LEGAL
	@LOG
	public void kcrrDefaultTitlesAndVolumesWorkflowKicksOffLegalTest()
	{
		homePage().goToHomePage();
		loginPage().logIn();

		boolean keyCiteExtractWindowAppeared = toolsMenu().goToKeyCiteExtract();
		Assertions.assertTrue(keyCiteExtractWindowAppeared, "Key Cite extract opened.");

		keyCiteResearchReferencesFiltersPage().setContentSetLegacyId(CONTENT_SET);
		keyCiteResearchReferencesFiltersPage().checkKeyRules();
		keyCiteResearchReferencesFiltersPage().checkAmericanLawReportAnnotations();
		keyCiteResearchReferencesFiltersPage().checkTreatiesDocuments();
		keyCiteResearchReferencesFiltersPage().checkLegalEncyclopediaDocuments();
		keyCiteResearchReferencesFiltersPage().checkOtherSecondarySources();
		keyCiteResearchReferencesFiltersPage().checkDefaultAnalyticalTitles();
		boolean selectedAnalyticalTitlesAppear = keyCiteResearchReferencesPage().verifySelectedAnalyticalTitlesMatch(SELECTED_ANALYTICAL_TITLES);
		Assertions.assertTrue(selectedAnalyticalTitlesAppear, "The correct analytical titles were selected.");

		keyCiteResearchReferencesPage().selectAvailableVolumes(SELECTED_VOLUMES);
		keyCiteResearchReferencesPage().clickAddOneVolumeButton();
		boolean selectedVolumesAppear = keyCiteResearchReferencesPage().verifySelectedVolumesMatch(SELECTED_VOLUMES);
		Assertions.assertTrue(selectedVolumesAppear, "The correct volumes were selected.");

		keyCiteResearchReferencesPage().clickSubmitButton(true);
		boolean workflowKicksOff = keyCiteResearchReferencesPage().doesWorkflowKickOff();
		Assertions.assertTrue(workflowKicksOff, "Workflow was kicked off successfully.");

		String workflowId = keyCiteResearchReferencesPage().getWorkflowId();
		boolean validWorkflowId = RegexUtils.matchesRegex(workflowId, CommonPageElements.VALID_WORKFLOW_ID_REGEX);
		Assertions.assertTrue(validWorkflowId, "A valid workflow id was generated.");

		homePage().switchToPage();
		toolsMenu().goToWorkflowReportingSystem();
		workflowSearchPage().setWorkflowID(workflowId);
		workflowSearchPage().clickFilterButton();
		boolean workflowAppears = workflowId.equals(workflowSearchPage().getWorkflowIdOfFirstWorkflow());
		Assertions.assertTrue(workflowAppears, "The workflow finished. id: " + workflowId);
	}

	/**
	 * STORY/BUG - N/A <br>
	 * SUMMARY - Goes to Key Cite Research References, empties selected analytical titles. Adds analytical titles and verifies
	 * they were moved. Then goes to the next page. Tries to start workflow with no volumes selected and
	 * checks for correct error. <br>
	 * USER - Legal <br>
	 */
	@Test
	@Order(3)
	@IE_EDGE_MODE
	@LEGAL
	@LOG
	public void kcrrWorkflowSubmissionVolumeErrorLegalTest()
	{
		homePage().goToHomePage();
		loginPage().logIn();

		boolean keyCiteExtractWindowAppeared = toolsMenu().goToKeyCiteExtract();
		Assertions.assertTrue(keyCiteExtractWindowAppeared, "Key Cite extract opened.");

		keyCiteResearchReferencesFiltersPage().setContentSetLegacyId(CONTENT_SET);
		keyCiteResearchReferencesFiltersPage().checkKeyRules();
		keyCiteResearchReferencesFiltersPage().checkAmericanLawReportAnnotations();
		keyCiteResearchReferencesFiltersPage().checkTreatiesDocuments();
		keyCiteResearchReferencesFiltersPage().checkLegalEncyclopediaDocuments();
		keyCiteResearchReferencesFiltersPage().checkOtherSecondarySources();
		keyCiteResearchReferencesFiltersPage().uncheckDefaultAnalyticalTitles();
		keyCiteResearchReferencesPage().clickEmptySelectedAnalyticalTitles();
		keyCiteResearchReferencesPage().selectAvailableAnalyticalTitles(SELECTED_ANALYTICAL_TITLES);
		keyCiteResearchReferencesPage().clickAddOneAnalyticalTitleButton();
		boolean selectedAnalyticalTitlesAppear = keyCiteResearchReferencesPage().verifySelectedAnalyticalTitlesMatch(SELECTED_ANALYTICAL_TITLES);
		Assertions.assertTrue(selectedAnalyticalTitlesAppear, "The desired analytical titles were selected.");

		keyCiteResearchReferencesPage().clickSaveDefaultTitlesButton();
		boolean isDefaultAnalyticalTitlesCheckboxChecked = keyCiteResearchReferencesFiltersPage().isDefaultAnalyticalTitlesCheckboxChecked();
		boolean isAvailableAnalyticalTitlesSelectDisabled = keyCiteResearchReferencesPage().isAvailableAnalyticalTitlesSelectDisabled();
		boolean isSelectedAnalyticalTitlesSelectDisabled = keyCiteResearchReferencesPage().isSelectedAnalyticalTitlesSelectDisabled();

		Assertions.assertAll
		(
			() -> Assertions.assertTrue(isDefaultAnalyticalTitlesCheckboxChecked, "Default analytical titles checkbox should be checked."),
			() -> Assertions.assertTrue(isAvailableAnalyticalTitlesSelectDisabled, "Available analytical titles select should be disabled."),
			() -> Assertions.assertTrue(isSelectedAnalyticalTitlesSelectDisabled, "Selected analytical titles select should be disabled.")
		);

		keyCiteResearchReferencesFiltersPage().setContentSetLegacyId(CONTENT_SET);
		keyCiteResearchReferencesFiltersPage().checkKeyRules();
		keyCiteResearchReferencesFiltersPage().checkAmericanLawReportAnnotations();
		keyCiteResearchReferencesFiltersPage().checkTreatiesDocuments();
		keyCiteResearchReferencesFiltersPage().checkLegalEncyclopediaDocuments();
		keyCiteResearchReferencesFiltersPage().checkOtherSecondarySources();
		keyCiteResearchReferencesPage().clickSubmitButton(false);
		boolean workflowErrorAppears = keyCiteResearchReferencesPage().doesWorkflowErrorAppearsForNotSelectingVolume();
		Assertions.assertTrue(workflowErrorAppears, "The workflow error appeared when the user doesn't select a volume.");
	}

	/**
	 * STORY/BUG - N/A <br>
	 * SUMMARY - Goes to Key Cite Research References and empties selected analytical titles. Goes to the next page and selects
	 * volumes. Tries to start a workflow and verifies the correct error appears. <br>
	 * USER - Legal <br>
	 */
	@Test
	@Order(4)
	@IE_EDGE_MODE
	@LEGAL
	@LOG
	public void kcrrWorkflowSubmissionAnalyticalTitleErrorLegalTest()
	{
		homePage().goToHomePage();
		loginPage().logIn();

		boolean keyCiteExtractWindowAppeared = toolsMenu().goToKeyCiteExtract();
		Assertions.assertTrue(keyCiteExtractWindowAppeared, "Key Cite extract opened.");

		keyCiteResearchReferencesFiltersPage().setContentSetLegacyId(CONTENT_SET);
		keyCiteResearchReferencesFiltersPage().checkKeyRules();
		keyCiteResearchReferencesFiltersPage().checkAmericanLawReportAnnotations();
		keyCiteResearchReferencesFiltersPage().checkTreatiesDocuments();
		keyCiteResearchReferencesFiltersPage().checkLegalEncyclopediaDocuments();
		keyCiteResearchReferencesFiltersPage().checkOtherSecondarySources();
		keyCiteResearchReferencesFiltersPage().uncheckDefaultAnalyticalTitles();
		keyCiteResearchReferencesPage().clickEmptySelectedAnalyticalTitles();
		keyCiteResearchReferencesPage().clickSaveDefaultTitlesButton();
		keyCiteResearchReferencesFiltersPage().setContentSetLegacyId(CONTENT_SET);
		keyCiteResearchReferencesFiltersPage().checkKeyRules();
		keyCiteResearchReferencesFiltersPage().checkAmericanLawReportAnnotations();
		keyCiteResearchReferencesFiltersPage().checkTreatiesDocuments();
		keyCiteResearchReferencesFiltersPage().checkLegalEncyclopediaDocuments();
		keyCiteResearchReferencesFiltersPage().checkOtherSecondarySources();
		keyCiteResearchReferencesPage().selectAvailableVolumes(SELECTED_VOLUMES);
		keyCiteResearchReferencesPage().clickAddOneVolumeButton();
		boolean selectedVolumesAppear = keyCiteResearchReferencesPage().verifySelectedVolumesMatch(SELECTED_VOLUMES);
		Assertions.assertTrue(selectedVolumesAppear, "The correct volumes are selected.");

		keyCiteResearchReferencesPage().clickSubmitButton(false);
		boolean workflowErrorAppears = keyCiteResearchReferencesPage().doesWorkflowErrorAppearForNotSelectingAnalyticalTitle();
		Assertions.assertTrue(workflowErrorAppears, "The workflow error appeared when the user doesn't select an Analytical Title.");
	}
}
