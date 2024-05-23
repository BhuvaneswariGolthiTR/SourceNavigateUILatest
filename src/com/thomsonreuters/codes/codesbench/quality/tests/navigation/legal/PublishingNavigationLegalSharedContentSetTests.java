package com.thomsonreuters.codes.codesbench.quality.tests.navigation.legal;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.publishing.PublishingDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

public class PublishingNavigationLegalSharedContentSetTests extends TestService
{
	/**
	 * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that the 'DLU Upload' context menu option in the Publishing -> Print submenu is not displayed for Legal users <br>
	 * USER - Legal <br>
	 */
	@Test
	@IE_EDGE_MODE
	@LEGAL
	@LOG
	public void isPublishingPrintDLUUploadContextMenuOptionDisplayedLegalTest()
	{
		homePage().goToHomePage();
		loginPage().logIn();
		
		boolean publishingPrintDLUUploadMenuOptionIsDisplayed = publishingMenu().isDluUploadSubMenuOptionDisplayed();
		Assertions.assertFalse(publishingPrintDLUUploadMenuOptionIsDisplayed, "The 'DLU Upload' context menu option in the Publishing -> Print submenu is displayed when it should not be");
	}

	/**
	 * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that the Annotated Pamphlet/Pocket Part Management page loads after selecting the 'AnP/PP Management' menu option in the Publishing -> Print -> Pub Extracts submenu.<br>
	 * USER - Legal <br>
	 */
	@Test
	@IE_EDGE_MODE
	@LEGAL
	@LOG
	public void navigateToPublishingPrintPubExtractsAnPPPMgmtLegalTest()
	{
		homePage().goToHomePage();
		loginPage().logIn();
		
		boolean annotatedPamphletPocketPartManagementPageLoaded = publishingMenu().goToPublishingPrintPubExtractsAnPPPManagement();
		Assertions.assertTrue(annotatedPamphletPocketPartManagementPageLoaded, "The Annotated Pamphlet/Pocket Part Management page did not appear but it should have");
	}

	/**
	 * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that the Custom Pub Extract (Management) page loads after selecting the 'Custom' menu option in the Publishing -> Print -> Pub Extracts submenu<br>
	 * USER - Legal <br>
	 */
	@Test
	@IE_EDGE_MODE
	@LEGAL
	@LOG
	public void navigateToPublishingPrintPubExtractsCustomLegalTest()
	{
		homePage().goToHomePage();
		loginPage().logIn();
		
		boolean customPubExtractManagementPageLoaded = publishingMenu().goToPublishingPrintPubExtractsCustom();
		Assertions.assertTrue(customPubExtractManagementPageLoaded, "The Custom Pub Extract (Management) page did not appear when it should have");
	}

	/**
	 * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that the PUB Recomp Extract page loads after selecting the 'Recomp' option in the Publishing -> Print -> Pub Extracts submenu.<br>
	 * USER - Legal <br>
	 */
	@Test
	@IE_EDGE_MODE
	@LEGAL
	@LOG
	public void navigateToPublishingPrintPubExtractsRecompLegalTest()
	{
		homePage().goToHomePage();
		loginPage().logIn();
		
		boolean pubRecompExtractPageLoaded = publishingMenu().goToPublishingPrintPubExtractsRecomp();
		Assertions.assertTrue(pubRecompExtractPageLoaded, "The PUB Recomp Extract page did not appear when it should have");
	}

	/**
	 * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that the PUB TOC Extract page loads after selecting the 'TOC' option in the Publishing -> Print -> Pub Extracts submenu. <br>
	 * USER - Legal <br>
	 */
	@Test
	@IE_EDGE_MODE
	@LEGAL
	@LOG
	public void navigateToPublishingPrintPubExtractsTOCLegalTest()
	{
		homePage().goToHomePage();
		loginPage().logIn();
		
		boolean pubTocExtractPageLoaded = publishingMenu().goToPublishingPrintPubExtractsTOC();
		Assertions.assertTrue(pubTocExtractPageLoaded, "The PUB TOC Extract page did not appear when it should have");
	}

	/**
	 * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that the Pubtag Refresh page loads after selecting the 'Run Pubtag Refresh by VOLS' option in the Publishing -> Print submenu <br>
	 * USER - Legal <br>
	 */
	@Test
	@IE_EDGE_MODE
	@LEGAL
	@LOG
	public void navigateToPublishingPrintRunPubtagRefreshByVolsLegalTest()
	{
		homePage().goToHomePage();
		loginPage().logIn();
		
		boolean pubtagRefreshPageLoaded = publishingMenu().goToPublishingPrintRunPubtagRefreshByVOLS();
		Assertions.assertTrue(pubtagRefreshPageLoaded, "The Pubtag Refresh page did not appear when it should have");
	}

	/**
	 * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that the Scripts For Content Set page loads after selecting the 'View Scripts For Content Set' option in the Publishing -> Print submenu.<br>
	 * USER - Legal <br>
	 */
	@Test
	@IE_EDGE_MODE
	@LEGAL
	@LOG
	public void navigateToPublishingPrintViewScriptsForContentSetLegalTest()
	{
		homePage().goToHomePage();
		loginPage().logIn();
		
		boolean scriptsForContentSetPageLoaded = publishingMenu().goToPublishingPrintViewScriptsForContentSet();
		Assertions.assertTrue(scriptsForContentSetPageLoaded,"The Scripts For Content Set page did not appear when it should have");
	}

	/**
	 * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that the Custom Pub Extract (Management) page loads after selecting the 'Custom' option in the Publishing -> Print -> Wip Extracts submenu.<br>
	 * USER - Legal <br>
	 */
	@Test
	@IE_EDGE_MODE
	@LEGAL
	@LOG
	public void navigateToPublishingPrintWipExtractsCustomLegalTest()
	{
		homePage().goToHomePage();
		loginPage().logIn();
		
		boolean customPubExtractManagementPageLoaded = publishingMenu().goToPublishingPrintWipExtractsCustom();
		Assertions.assertTrue(customPubExtractManagementPageLoaded, "The Custom Pub Extract (Management) page did not appear when it should have");
	}

	/**
	 * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that the WIP Recomp Extract page loads after selecting the 'Recomp' option in the Publishing -> Print -> Wip Extracts submenu.<br>
	 * USER - Legal <br>
	 */
	@Test
	@IE_EDGE_MODE
	@LEGAL
	@LOG
	public void navigateToPublishingPrintWipExtractsRecompTestLegalTest()
	{
		homePage().goToHomePage();
		loginPage().logIn();
		
		boolean wipRecompExtractPageLoaded = publishingMenu().goToPublishingPrintWipExtractsRecomp();
		Assertions.assertTrue(wipRecompExtractPageLoaded, "The WIP Recomp Extract page did not appear when it should have");
	}

	/**
	 * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that the WIP TOC Extract page loads after selecting the 'TOC' option in the Publishing -> Print -> Wip Extracts submenu.<br>
	 * USER - Legal <br>
	 */
	@Test
	@IE_EDGE_MODE
	@LEGAL
	@LOG
	public void navigateToPublishingPrintWipExtractsTOCLegalTest()
	{
		homePage().goToHomePage();
		loginPage().logIn();
		
		boolean wipTocExtractPageLoaded = publishingMenu().goToPublishingPrintWipExtractsTOC();
		Assertions.assertTrue(wipTocExtractPageLoaded, "The WIP TOC Extract page did not appear when it should have");
	}

	/**
	 * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that the Ranking Preferences page loads after selecting the 'View/Edit Data' menu option in the Publishing -> Novus -> Ranking Preferences submenu<br>
	 * USER - Legal <br>
	 */
	@Test
	@IE_EDGE_MODE
	@LEGAL
	@LOG
	public void navigateToPublishingNovusRankingPreferencesViewEditDataLegalTest()
	{
		homePage().goToHomePage();
		loginPage().logIn();
		
		boolean rankingPreferencesPageLoaded = publishingMenu().goToPublishingNovusRankingPreferencesViewEditData();
		Assertions.assertTrue(rankingPreferencesPageLoaded, "The Ranking Preferences page did not load when it should have");
	}

	/**
	 * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that the Ranking Preferences page loads after selecting the 'View Deleted Rows' menu option in the Publishing -> Novus -> Ranking Preferences submenu<br>
	 * USER - Legal <br>
	 */
	@Test
	@IE_EDGE_MODE
	@LEGAL
	@LOG
	public void navigateToPublishingNovusRankingPreferencesViewDeletedRowsLegalTest()
	{
		homePage().goToHomePage();
		loginPage().logIn();
		
		boolean rankingPreferencesPageLoaded = publishingMenu().goToPublishingNovusRankingPreferencesViewDeletedRows();
		Assertions.assertTrue(rankingPreferencesPageLoaded, "The Ranking Preferences page did not load when it should have");
	}

	/**
	 * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that the XFeature Preferences page loads after selecting the 'View/Edit Data' menu option in the Publishing -> Novus -> XFeature Preferences submenu<br>
	 * USER - Legal <br>
	 */
	@Test
	@IE_EDGE_MODE
	@LEGAL
	@LOG
	public void navigateToPublishingNovusXFeaturePreferencesViewEditDataLegalTest()
	{
		homePage().goToHomePage();
		loginPage().logIn();
		
		boolean xFeaturePreferencesPageLoaded = publishingMenu().goToPublishingNovusXFeaturePreferencesViewEditData();
		Assertions.assertTrue(xFeaturePreferencesPageLoaded,"The XFeature Preferences page did not load when it should have");
	}

	/**
	 * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that the XFeature Preferences page loads after selecting the 'View Deleted Rows' menu option in the Publishing -> Novus -> XFeature Preferences submenu<br>
	 * USER - Legal <br>
	 */
	@Test
	@IE_EDGE_MODE
	@LEGAL
	@LOG
	public void navigateToPublishingNovusXFeaturePreferencesViewDeletedRowsLegalTest()
	{
		homePage().goToHomePage();
		loginPage().logIn();
		
		boolean xFeaturePreferencesPageLoaded = publishingMenu().goToPublishingNovusXFeaturePreferencesViewDeletedRows();
		Assertions.assertTrue(xFeaturePreferencesPageLoaded, "The XFeature Preferences page did not load when it should have");
	}

	/**
	 * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that the Copyright Message Codes page loads after selecting the 'View/Edit Data' menu option from the Publishing -> Novus -> Copyright Message Codes submenu<br>
	 * USER - Legal <br>
	 */
	@Test
	@IE_EDGE_MODE
	@LEGAL
	@LOG
	public void navigateToPublishingNovusCopyrightMessageCodesViewEditDataLegalTest()
	{
		homePage().goToHomePage();
		loginPage().logIn();
		
		boolean copyrightMessageCodesPageLoaded = publishingMenu().goToPublishingNovusCopyrightMessageCodesViewEditData();
		Assertions.assertTrue(copyrightMessageCodesPageLoaded, "The Copyright Message Codes page did not load when it should have");
	}

	/**
	 * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that the Copyright Message Codes page loads after selecting the 'View Deleted Rows' menu option from the Publishing -> Novus -> Copyright Message Codes submenu<br>
	 * USER - Legal <br>
	 */
	@Test
	@IE_EDGE_MODE
	@LEGAL
	@LOG
	public void navigateToPublishingNovusCopyrightMessageCodesViewDeletedRowsLegalTest()
	{
		homePage().goToHomePage();
		loginPage().logIn();
		
		boolean copyrightMessageCodesPageLoaded = publishingMenu().goToPublishingNovusCopyrightMessageCodesViewDeletedRows();
		Assertions.assertTrue(copyrightMessageCodesPageLoaded,"The Copyright Message Codes page did not load when it should have");
	}

	/**
	 * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that the Currency Message Codes page loads after selecting the 'View/Edit Data' menu option from the Publishing -> Novus -> Currency Message Codes submenu<br>
	 * USER - Legal <br>
	 */
	@Test
	@IE_EDGE_MODE
	@LEGAL
	@LOG
	public void navigateToPublishingNovusCurrencyMessageCodesViewEditDataLegalTest()
	{
		homePage().goToHomePage();
		loginPage().logIn();
		
		boolean currencyMessageCodesPageLoaded = publishingMenu().goToPublishingNovusCurrencyMessageCodesViewEditData();
		Assertions.assertTrue(currencyMessageCodesPageLoaded, "The Currency Message Codes page did not load when it should have");
	}

	/**
	 * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that the Currency Message Codes page loads after selecting the 'View Deleted Rows' menu option from the Publishing -> Novus -> Currency Message Codes submenu<br>
	 * USER - Legal <br>
	 */
	@Test
	@IE_EDGE_MODE
	@LEGAL
	@LOG
	public void navigateToPublishingNovusCurrencyMessageCodesViewDeletedRowsLegalTest()
	{
		homePage().goToHomePage();
		loginPage().logIn();
		
		boolean currencyMessageCodesPageLoaded = publishingMenu().goToPublishingNovusCurrencyMessageCodesViewDeletedRows();
		Assertions.assertTrue(currencyMessageCodesPageLoaded, "The Currency Message Codes page did not load when it should have");
	}

	/**
	 * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that the Add New Volumes page loads after selecting the 'Add New Volumes' menu option from the Publishing -> Novus -> Message Code Volumes submenu <br>
	 * USER - Legal <br>
	 */
	@Test
	@IE_EDGE_MODE
	@LEGAL
	@LOG
	public void navigatePublishingNovusMessageCodeVolumesAddNewVolumesLegalTest()
	{
		homePage().goToHomePage();
		loginPage().logIn();
		
		boolean addNewVolumesPageLoaded = publishingMenu().goToPublishingNovusMessageCodeVolumesAddNewVolumes();
		Assertions.assertTrue(addNewVolumesPageLoaded, "The Add New Volumes page did not load when it should have");
	}

	/**
	 * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that the Delete Volumes page loads after selecting the 'Delete Volumes' menu option from the Publishing -> Novus -> Message Code Volumes submenu<br>
	 * USER - Legal <br>
	 */
	@Test
	@IE_EDGE_MODE
	@LEGAL
	@LOG
	public void navigateToPublishingNovusMessageCodeVolumesDeleteVolumesLegalTest()
	{
		homePage().goToHomePage();
		loginPage().logIn();
		
		boolean deleteVolumesPageLoaded = publishingMenu().goToPublishingNovusMessageCodeVolumesDeleteVolumes();
		Assertions.assertTrue(deleteVolumesPageLoaded, "The Delete Volumes page did not load when it should have");
	}

	/**
	 * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that the Feature Source Heading Message Codes page loads after selecting the 'Feature Source Heading Message Code' menu option in the Publishing -> Novus submenu<br>
	 * USER - Legal <br>
	 */
	@Test
	@IE_EDGE_MODE
	@LEGAL
	@LOG
	public void navigateToPublishingNovusFeatureSourceHeadingMessageCodeLegalTest()
	{
		homePage().goToHomePage();
		loginPage().logIn();
		
		boolean featureSourceHeadingMessageCodesPageLoaded = publishingMenu().goToPublishingNovusFeatureSourceHeadingMessageCode();
		Assertions.assertTrue(featureSourceHeadingMessageCodesPageLoaded,"The Feature Source Heading Message Codes page did not load when it should have");
	}

	/**
	 * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that the Ct1 Report page loads after selecting the 'CT1 Report' menu option from the Publishing -> Reports submenu<br>
	 * USER - Legal <br>
	 */
	@Test
	@IE_EDGE_MODE
	@LEGAL
	@LOG
	public void navigateToPublishingReportsCT1ReportLegalTest()
	{
		homePage().goToHomePage();
		loginPage().logIn();
		
		boolean ct1ReportPageLoaded = publishingMenu().goToPublishingReportsCT1Report();
		Assertions.assertTrue(ct1ReportPageLoaded, "The Ct1 Report page did not load when it should have");
	}

	/**
	 * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that the Knex PubId Report page loads after selecting the 'PubId Report' menu option from the Publishing -> Reports submenu<br>
	 * USER - Legal <br>
	 */
	@Test
	@IE_EDGE_MODE
	@LEGAL
	@LOG
	public void navigateToPublishingReportsPubIdReportLegalTest()
	{
		homePage().goToHomePage();
		loginPage().logIn();
		
		boolean knexPubIdReportPageLoaded = publishingMenu().goToPublishingReportsPubIdReport();
		Assertions.assertTrue(knexPubIdReportPageLoaded, "The Knex PubId Report page did not load whe it should have");
	}

	/**
	 *  STORY/BUG - HALCYONST-12146 <br>
	 * SUMMARY -  This test verifies that a Legal CT1 user can load the citeline management page <br>
	 * USER - Legal  <br>
	 */
	@Test
	@IE_EDGE_MODE
	@LEGAL
	@LOG
	public void navigateToCitelineManagementLegalCT1LegalTest()
	{
		Connection uatConnection = BaseDatabaseUtils.connectToDatabaseUAT();
		String contentSetId = ContentSets.IOWA_DEVELOPMENT.getCode();

		//If Legal user in publishing approvers list, remove
		PublishingDatabaseUtils.checkAndRemovePublishApproverForContentSet(user().getUsername(), user().getFirstname(), user().getLastname(), contentSetId, uatConnection);

		try
		{
			homePage().goToHomePage();
			loginPage().logIn();

			//verifying user has Legal CT1 access
			boolean doesUserPreferencesSecurityPageLoad = homeMenu().goToUserPreferencesSecurity();
			Assertions.assertTrue(doesUserPreferencesSecurityPageLoad, "User preferences page does not loan when it should have");
			boolean doesUserHaveLegalCT1UserAccess = userSecuritySettingsPage().doesUserHaveLegalCT1UserAccess();
			Assertions.assertTrue(doesUserHaveLegalCT1UserAccess, "User did not have Legal Ct1 Access when they should have");

			//Verifying that user has access to citeline management page
			boolean citelineManagementPageLoaded = publishingMenu().goToPublishingNovusCitelineManagement();
			Assertions.assertTrue(citelineManagementPageLoaded, "Citeline Management page does not load when it should have");
		}
		finally
		{
			PublishingDatabaseUtils.checkAndAddPublishApproverForContentSet(user().getUsername(), user().getFirstname(), user().getLastname(), contentSetId, uatConnection);
			BaseDatabaseUtils.disconnect(uatConnection);
		}
	}
}
