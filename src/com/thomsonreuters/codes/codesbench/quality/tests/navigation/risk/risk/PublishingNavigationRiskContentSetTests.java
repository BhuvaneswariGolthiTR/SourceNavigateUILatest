package com.thomsonreuters.codes.codesbench.quality.tests.navigation.risk.risk;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.RISK;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PublishingNavigationRiskContentSetTests extends TestService
{
	/**
	 * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that the Select Upload Type page loads after selecting the 'DLU Upload' menu option from the Publishing -> Print submenu<br>
	 * USER - Risk <br>
	 */
	@Test
	@IE_EDGE_MODE
	@RISK
	@LOG
	public void navigateToPrintDLUUploadRiskTest()
	{
		homePage().goToHomePage();
		loginPage().logIn();

		boolean isDluUploadMenuOptionDisplayed = publishingMenu().isDluUploadSubMenuOptionDisplayed();
		Assertions.assertFalse(isDluUploadMenuOptionDisplayed, "The Select Upload Type page did not load when it should have");
	}

	/**
	 * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that the Annotated Pamphlet/Pocket Part Management page loads after selecting the 'AnP/PP Management' menu option from the Publishing -> Print -> Pub Extracts submenu<br>
	 * USER - Risk <br>
	 */
	@Test
	@IE_EDGE_MODE
	@RISK
	@LOG
	public void navigateToPrintPubExtractsAnPPPMgmtRiskTest()
	{
		homePage().goToHomePage();
		loginPage().logIn();

		boolean annotatedPamphletPocketPartManagementPageLoaded = publishingMenu().goToPublishingPrintPubExtractsAnPPPManagement();
		Assertions.assertTrue(annotatedPamphletPocketPartManagementPageLoaded, "The Annotated Pamphlet/Pocket Part Management page did not load when it should have");
	}

	/**
	 * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that the Custom Pub Extract (Management) page loads after selecting the 'Custom' menu option from the Publishing -> Print -> Pub Extracts submenu<br>
	 * USER - Risk <br>
	 */
	@Test
	@IE_EDGE_MODE
	@RISK
	@LOG
	public void navigateToPrintPubExtractsCustomRiskTest()
	{
		homePage().goToHomePage();
		loginPage().logIn();

		boolean customPubExtractManagementPageLoaded = publishingMenu().goToPublishingPrintPubExtractsCustom();
		Assertions.assertTrue(customPubExtractManagementPageLoaded, "The Custom Pub Extract (Management) page did not load when it should have");
	}

	/**
	 * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that the PUB Recomp Extract page loads after selecting the 'Recomp' menu option from the Publishing -> Print -> Pub Extracts submenu<br>
	 * USER - Risk <br>
	 */
	@Test
	@IE_EDGE_MODE
	@RISK
	@LOG
	public void navigateToPrintPubExtractsRecompRiskTest()
	{
		homePage().goToHomePage();
		loginPage().logIn();

		boolean pubRecompExtractPageLoaded = publishingMenu().goToPublishingPrintPubExtractsRecomp();
		Assertions.assertTrue(pubRecompExtractPageLoaded, "The PUB Recomp Extract page did not load when it should have");
	}

	/**
	 * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that the PUB TOC Extract page loads after selecting the 'TOC' menu option from the Publishing -> Print -> Pub Extracts submenu<br>
	 * USER - Risk <br>
	 */
	@Test
	@IE_EDGE_MODE
	@RISK
	@LOG
	public void navigateToPrintPubExtractsTOCRiskTest()
	{
		homePage().goToHomePage();
		loginPage().logIn();

		boolean pubTocExtractPageLoaded = publishingMenu().goToPublishingPrintPubExtractsTOC();
		Assertions.assertTrue(pubTocExtractPageLoaded, "The PUB TOC Extract page did not load when it should have");
	}

	/**
	 * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that the Pubtag Refresh page loads after selecting the 'Run Pubtag Refresh by VOLS' menu option in the Publishing -> Print submenu<br>
	 * USER - Risk <br>
	 */
	@Test
	@IE_EDGE_MODE
	@RISK
	@LOG
	public void navigateToPrintRunPubtagRefreshByVolsRiskTest()
	{
		homePage().goToHomePage();
		loginPage().logIn();

		boolean pubtagRefreshPageLoaded = publishingMenu().goToPublishingPrintRunPubtagRefreshByVOLS();
		Assertions.assertTrue(pubtagRefreshPageLoaded, "The Pubtag Refresh page did not load when it should have");
	}

	/**
	 * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that the Scripts For Content Set page loads after selecting the 'View Scripts For Content Set' menu option in the Publishing -> Print submenu<br>
	 * USER - Risk <br>
	 */
	@Test
	@IE_EDGE_MODE
	@RISK
	@LOG
	public void navigateToPrintViewScriptsForContentSetRiskTest()
	{
		homePage().goToHomePage();
		loginPage().logIn();

		boolean scriptsForContentSetPageLoaded = publishingMenu().goToPublishingPrintViewScriptsForContentSet();
		Assertions.assertTrue(scriptsForContentSetPageLoaded, "The Scripts For Content Set page did not load when it should have");
	}

	/**
	 * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that the Custom WIP Extract (Management) page loads after selecting the 'Custom' menu option from the Publishing -> Print -> Wip Extracts submenu<br>
	 * USER - Risk <br>
	 */
	@Test
	@IE_EDGE_MODE
	@RISK
	@LOG
	public void navigateToPrintWipExtractsCustomRiskTest()
	{
		homePage().goToHomePage();
		loginPage().logIn();

		boolean customPubExtractManagementPageLoaded = publishingMenu().goToPublishingPrintWipExtractsCustom();
		Assertions.assertTrue(customPubExtractManagementPageLoaded, "The Custom WIP Extract (Management) page did not load when it should have");
	}

	/**
	 * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that the WIP Recomp Extract page loads after selecting the 'Recomp' menu option from the Publishing -> Print -> Wip Extracts submenu<br>
	 * USER - Risk <br>
	 */
	@Test
	@IE_EDGE_MODE
	@RISK
	@LOG
	public void navigateToPrintWipExtractsRecompRiskTest()
	{
		homePage().goToHomePage();
		loginPage().logIn();

		boolean wipRecompExtractPageLoaded = publishingMenu().goToPublishingPrintWipExtractsRecomp();
		Assertions.assertTrue(wipRecompExtractPageLoaded, "The WIP Recomp Extract page did not load when it should have");
	}

	/**
	 * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that the WIP TOC Extract page loads after selecting the 'TOC' menu option from the Publishing -> Print -> Wip Extracts submenu<br>
	 * USER - Risk <br>
	 */
	@Test
	@IE_EDGE_MODE
	@RISK
	@LOG
	public void navigateToPrintWipExtractsTOCRiskTest()
	{
		homePage().goToHomePage();
		loginPage().logIn();

		boolean wipTOCExtractPageLoaded = publishingMenu().goToPublishingPrintWipExtractsTOC();
		Assertions.assertTrue(wipTOCExtractPageLoaded, "The WIP TOC Extract page did not load when it should have");
	}

	/**
	 * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that the Ct1 Report page loads after selecting the 'CT1 Report' menu option from the Publishing -> Reports submenu<br>
	 * USER - Risk <br>
	 */
	@Test
	@IE_EDGE_MODE
	@RISK
	@LOG
	public void navigateToReportsCT1ReportRiskTest()
	{
		homePage().goToHomePage();
		loginPage().logIn();

		boolean ct1ReportPageLoaded = publishingMenu().goToPublishingReportsCT1Report();
		Assertions.assertTrue(ct1ReportPageLoaded, "The Ct1 Report page did not load when it should have");
	}

	/**
	 * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that the PubId Report page loads after selecting the 'PubId Report' menu option in the Publishing -> Reports submenu<br>
	 * USER - Risk <br>
	 */
	@Test
	@IE_EDGE_MODE
	@LEGAL
	@LOG
	public void navigateToReportsPubIdReportRiskTest()
	{
		homePage().goToHomePage();
		loginPage().logIn();
		
		boolean knexPubIdReportPageLoaded = publishingMenu().goToPublishingReportsPubIdReport();
		Assertions.assertTrue(knexPubIdReportPageLoaded, "The Knex PubId Report page did not load when it should have");
	}
	
	/**
	 * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that the Ranking Preferences page loads after selecting the 'View/Edit Data' menu option from the Publishing -> Novus -> Ranking Preferences submenu<br>
	 * USER - Risk <br>
	 */
	@Test
	@IE_EDGE_MODE
	@RISK
	@LOG
	public void navigateToNovusRankingPreferencesViewEditDataRiskTest()
	{
		homePage().goToHomePage();
		loginPage().logIn();
		
		boolean rankingPreferencesPageLoaded = publishingMenu().goToPublishingNovusRankingPreferencesViewEditData();
		Assertions.assertTrue(rankingPreferencesPageLoaded, "The Ranking Preferences page did not load when it should have");
	}

	/**
	 * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that the Ranking Preferences page loads after selecting the 'View Deleted Rows' menu option from the Publishing -> Novus -> Ranking Preferences submenu<br>
	 * USER - Risk <br>
	 */
	@Test
	@IE_EDGE_MODE
	@RISK
	@LOG
	public void navigateToNovusRankingPreferencesViewDeletedRowsRiskTest()
	{
		homePage().goToHomePage();
		loginPage().logIn();

		boolean rankingPreferencesPageLoaded = publishingMenu().goToPublishingNovusRankingPreferencesViewDeletedRows();
		Assertions.assertTrue(rankingPreferencesPageLoaded, "The Ranking Preferences page did not load when it should have");
	}
	
	/**
	 * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that the XFeature Preferences page loads after selecting the 'View/Edit Data' menu option from the Publishing -> Novus -> XFeature Preferences submenu<br>
	 * USER - Risk <br>
	 */
	@Test
	@IE_EDGE_MODE
	@RISK
	@LOG
	public void navigateToNovusXFeaturePreferencesViewEditDataRiskTest()
	{
		homePage().goToHomePage();
		loginPage().logIn();
		
		boolean xFeaturePreferencesPageLoaded = publishingMenu().goToPublishingNovusXFeaturePreferencesViewEditData();
		Assertions.assertTrue(xFeaturePreferencesPageLoaded,"The XFeature Preferences page did not load when it should have");
	}

	/**
	 * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that the XFeature Preferences page loads after selecting the 'View Deleted Rows' menu option from the Publishing -> Novus -> XFeature Preferences submenu<br>
	 * USER - Risk <br>
	 */
	@Test
	@IE_EDGE_MODE
	@RISK
	@LOG
	public void navigateToNovusXFeaturePreferencesViewDeletedRowsRiskTest()
	{
		homePage().goToHomePage();
		loginPage().logIn();

		boolean xFeaturePreferencesPageLoaded = publishingMenu().goToPublishingNovusXFeaturePreferencesViewDeletedRows();
		Assertions.assertTrue(xFeaturePreferencesPageLoaded,"The XFeature Preferences page did not load when it should have");
	}
	
	/**
	 * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that the Copyright Message Codes page loads after selecting the 'View/Edit Data' menu option from the Publishing -> Novus -> Copyright Message Codes submenu<br>
	 * USER - Risk <br>
	 */
	@Test
	@IE_EDGE_MODE
	@RISK
	@LOG
	public void navigateToNovusCopyrightMessageCodesViewEditDataRiskTest()
	{
		homePage().goToHomePage();
		loginPage().logIn();
		
		boolean copyrightMessageCodesPageLoaded = publishingMenu().goToPublishingNovusCopyrightMessageCodesViewEditData();
		Assertions.assertTrue(copyrightMessageCodesPageLoaded, "The Copyright Message Codes page did not load when it should have");
	}

	/**
	 * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that the Copyright Message Codes page loads after selecting the 'View Deleted Rows' menu option from the Publishing -> Novus -> Copyright Message Codes submenu<br>
	 * USER - Risk <br>
	 */
	@Test
	@IE_EDGE_MODE
	@RISK
	@LOG
	public void navigateToNovusCopyrightMessageCodesViewDeletedRowsRiskTest()
	{
		homePage().goToHomePage();
		loginPage().logIn();

		boolean copyrightMessageCodesPageLoaded = publishingMenu().goToPublishingNovusCopyrightMessageCodesViewDeletedRows();
		Assertions.assertTrue(copyrightMessageCodesPageLoaded, "The Copyright Message Codes page did not load when it should have");
	}
	
	/**
	 * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that the Currency Message Codes page loads after selecting the 'View/Edit Data' menu option from the Publishing -> Novus -> Currency Message Codes submenu<br>
	 * USER - RISK <br>
	 */
	@Test
	@IE_EDGE_MODE
	@RISK
	@LOG
	public void navigateToNovusCurrencyMessageCodesViewEditDataRiskTest()
	{
		homePage().goToHomePage();
		loginPage().logIn();
		
		boolean currencyMessageCodesPageLoaded = publishingMenu().goToPublishingNovusCurrencyMessageCodesViewEditData();
		Assertions.assertTrue(currencyMessageCodesPageLoaded, "The Currency Message Codes page did not load when it should have");
	}

	/**
	 * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that the Currency Message Codes page loads after selecting the 'View Deleted Rows' menu option from the Publishing -> Novus -> Currency Message Codes submenu<br>
	 * USER - RISK <br>
	 */
	@Test
	@IE_EDGE_MODE
	@RISK
	@LOG
	public void navigateToNovusCurrencyMessageCodesViewDeletedRowsRiskTest()
	{
		homePage().goToHomePage();
		loginPage().logIn();

		boolean currencyMessageCodesPageLoaded = publishingMenu().goToPublishingNovusCurrencyMessageCodesViewDeletedRows();
		Assertions.assertTrue(currencyMessageCodesPageLoaded, "The Currency Message Codes page did not load when it should have");
	}

	/**
	 * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that the Add New Volumes page loads after selecting the 'Add New Volumes' menu option from the Publishing -> Novus -> Message Code Volumes submenu<br>
	 * USER - RISK <br>
	 */
	@Test
	@IE_EDGE_MODE
	@RISK
	@LOG
	public void navigateToNovusMessageCodeVolumesAddNewVolumesRiskTest()
	{
		homePage().goToHomePage();
		loginPage().logIn();

		boolean addNewVolumesPageLoaded = publishingMenu().goToPublishingNovusMessageCodeVolumesAddNewVolumes();
		Assertions.assertTrue(addNewVolumesPageLoaded, "The Add New Volumes page did not load when it should have");
	}

	/**
	 * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that the Delete Volumes page loads after selecting the 'Delete Volumes' menu option from the Publishing -> Novus -> Message Code Volumes submenu<br>
	 * USER - RISK <br>
	 */
	@Test
	@IE_EDGE_MODE
	@RISK
	@LOG
	public void navigateToNovusMessageCodeVolumesDeleteVolumesRiskTest()
	{
		homePage().goToHomePage();
		loginPage().logIn();

		boolean deleteVolumesPageLoaded = publishingMenu().goToPublishingNovusMessageCodeVolumesDeleteVolumes();
		Assertions.assertTrue(deleteVolumesPageLoaded, "The Delete Volumes page did not load when it should have");
	}
	
	/**
	 * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that the Feature Source Heading Message Codes page after selecting the 'Feature Source Heading Message Codes' menu option from the Publishing -> Novus submenu<br>
	 * USER - Risk <br>
	 */
	@Test
	@IE_EDGE_MODE
	@RISK
	@LOG
	public void navigateToNovusFeatureSourceHeadingMessageCodesRiskTest()
	{
		homePage().goToHomePage();
		loginPage().logIn();
		
		boolean featureSourceHeadingMessageCodesPageLoaded = publishingMenu().goToPublishingNovusFeatureSourceHeadingMessageCode();
		Assertions.assertTrue(featureSourceHeadingMessageCodesPageLoaded,"The Feature Source Heading Message Codes page did not load when it should have");
	}

	/**
	 * STORY/BUG - HALCYONST - 12146 <br>
	 * SUMMARY - This test verifies that a Risk CT1 user can load the citeline management page <br>
	 * USER - Risk <br>
	 */
	@Test
	@IE_EDGE_MODE
	@RISK
	@LOG
	public void navigateToCitelineManagementRiskCT1RiskTest()
	{
		homePage().goToHomePage();
		loginPage().logIn();

		boolean doesUserPreferenceSecurityLoad = homeMenu().goToUserPreferencesSecurity();
		Assertions.assertTrue(doesUserPreferenceSecurityLoad, "Error loading user preferences security page");

		boolean doesUserHaveRiskCT1UserAccess = userSecuritySettingsPage().doesUserHaveRiskCT1UserAccess();
		Assertions.assertTrue(doesUserHaveRiskCT1UserAccess, "The user does not have Risk CT1 user access");

		boolean didCitelineManagementPageLoad = publishingMenu().goToPublishingNovusCitelineManagement();
		Assertions.assertTrue(didCitelineManagementPageLoad, "The citeline management page didn't load when it should have");
	}
}
