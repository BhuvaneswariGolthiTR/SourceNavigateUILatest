package com.thomsonreuters.codes.codesbench.quality.tests.navigation.risk.risk;

import com.thomsonreuters.codes.codesbench.quality.menuelements.mainmenuelements.AuditsMenuElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.RISK;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AuditsNavigationRiskContentSetTests extends TestService
{
	/**
     * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that the Audit By Document page loads after selecting the 'Audit by Document' menu option in the Audits menu.<br>
	 * USER - RISK <br>
     */
	@Test
	@IE_EDGE_MODE
	@RISK
	@LOG
    public void navigateToAuditByDocumentRiskTest() 
    {
		homePage().goToHomePage();
		loginPage().logIn();
		
    	boolean auditByDocumentPageLoaded = auditsMenu().goToAuditByDocument();
        Assertions.assertTrue(auditByDocumentPageLoaded, "The Audit By Document page did not load when it should have");
    }
	
    /**
     * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that the Audit By Source page loads after selecting the 'Audit by Source' menu option in the Audits menu.<br>
	 * USER - RISK <br>
     */
    @Test
	@IE_EDGE_MODE
	@RISK
	@LOG
    public void navigateToAuditBySourceRiskTest() 
    {
    	homePage().goToHomePage();
		loginPage().logIn();
    	
    	boolean auditBySourcePageLoaded = auditsMenu().goToAuditBySource();
        Assertions.assertTrue(auditBySourcePageLoaded, "The Audit By Source page did not load when it should have");
    }

    /**
     * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that the Report Central page opens after selecting the 'Report Central' menu option in the Audits menu.<br>
	 * USER - RISK <br>
     */
    @Test
	@IE_EDGE_MODE
	@RISK
	@LOG
    public void navigateToReportCentralRiskTest() 
    {
    	homePage().goToHomePage();
		loginPage().logIn();
    	
		boolean reportCentralPageOpened = auditsMenu().goToReportCentral();
		Assertions.assertTrue(reportCentralPageOpened, "The Report Central page did not open when it should have");
    }
    
    /**
     * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that the 'Twirl - Inverted' and 'Twirl - Publication' menu options do not appear under the Audits menu.<br>
	 * USER - RISK <br>
     */
    @Test
	@IE_EDGE_MODE
	@RISK
	@LOG
    public void negativeNavigateToAuditsRiskTest()
    {
    	homePage().goToHomePage();
		loginPage().logIn();
		
		auditsMenu().openMenu();

    	boolean twirlInvertedItemIsPresent = auditsMenu().isElementDisplayed(AuditsMenuElements.TWIRL_INVERTED);
    	boolean twirlPublicationItemIsPresent = auditsMenu().isElementDisplayed(AuditsMenuElements.TWIRL_PUBLICATION);

    	Assertions.assertAll
		(
				() -> Assertions.assertFalse(twirlPublicationItemIsPresent, "The 'Twirl-Publication' is a submenu option in the Audits menu when it should not be"),
				() -> Assertions.assertFalse(twirlInvertedItemIsPresent, "The 'Twirl-Inverted' is a submenu option in the Audits menu when it should not be")
		);
    }
}
