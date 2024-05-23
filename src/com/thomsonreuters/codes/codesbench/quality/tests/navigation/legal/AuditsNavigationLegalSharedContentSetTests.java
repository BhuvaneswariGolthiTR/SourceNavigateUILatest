package com.thomsonreuters.codes.codesbench.quality.tests.navigation.legal;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AuditsNavigationLegalSharedContentSetTests extends TestService
{
	/**
     *  STORY/BUG - n/a <br>
	 * SUMMARY - Verifies that the 'Audit By Document' option of the Audit menu is functional.<br>
	 * USER - Legal <br>
     */
	@Test
	@IE_EDGE_MODE
	@LEGAL
	@LOG
	public void navigateToAuditByDocumentLegalTest() 
	{
		homePage().goToHomePage();
		loginPage().logIn();
			
	    boolean auditByDocumentPageReached = auditsMenu().goToAuditByDocument();
	    Assertions.assertTrue(auditByDocumentPageReached, "'Audit by Document' Page did not appear when it should have");
	}

	/**
     *  STORY/BUG - n/a <br>
	 * SUMMARY - Tests to see if the 'Audit by Source' option in the Audit menu is functional. <br>
	 * USER - Legal <br>
     */
	@Test
	@IE_EDGE_MODE
	@LEGAL
	@LOG
	public void navigateToAuditBySourceLegalTest() 
	{
		homePage().goToHomePage();
		loginPage().logIn();
	    	
	    boolean auditBySourcePageReached = auditsMenu().goToAuditBySource();
	    Assertions.assertTrue(auditBySourcePageReached, "'Audit by Source' page did not appear when it should have");
	}

	/**
     *  STORY/BUG - n/a <br>
	 * SUMMARY - Test to verify that the 'Report Central' option in the Audit submenu is functional.  <br>
	 * USER - Legal <br>
     */
	@Test
	@IE_EDGE_MODE
	@LEGAL
	@LOG
	public void navigateToReportCentralLegalTest() 
	{
		homePage().goToHomePage();
		loginPage().logIn();
    	
		boolean auditByReportCentralPageOpened = auditsMenu().goToReportCentral();
		Assertions.assertTrue(auditByReportCentralPageOpened, "'Report Central' page did not appear when it should have");
    }
	
	/**
     *  STORY/BUG - n/a <br>
	 * SUMMARY - Test to verify that the 'Twirl - Inverted' option in the Audit submenu is functional.<br>
	 * USER - Legal <br>
     */
	@Test
	@IE_EDGE_MODE
	@LEGAL
	@LOG
	public void navigateToTwirlInvertedLegalTest() 
    {
		homePage().goToHomePage();
		loginPage().logIn();
    	
    	boolean TwirlReportPageOpened = auditsMenu().goToTwirlInverted();
    	Assertions.assertTrue(TwirlReportPageOpened, "Twirl Report page did not open when it should have");
    }
	
	/**
     *  STORY/BUG - n/a <br>
	 * SUMMARY -  Test to verify that the twirl publication option in the Audit submenu is functional. <br>
	 * USER - Legal <br>
     */
	@Test
	@IE_EDGE_MODE
	@LEGAL
	@LOG
	public void navigateToTwirlPublicationLegalTest() 
	{
		homePage().goToHomePage();
		loginPage().logIn();
	    	
	    boolean TwirlReportPageOpened = auditsMenu().goToTwirlPublication();
	    Assertions.assertTrue(TwirlReportPageOpened, "Twirl Report page did not open when it should have");
	}
}
