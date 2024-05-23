
package com.thomsonreuters.codes.codesbench.quality.tests.navigation.risk.shared;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestSetupEdge;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.RISK;
import com.thomsonreuters.codes.codesbench.quality.utilities.autoIT.AutoITUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.home.HomeDatabaseUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

public class ContentSetNavigationTests extends TestService
{
    /**
	 * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that the 'Change Default Content Set' functionality in the Content Set Preferences page correctly updates the default content set in the Home page<br>
	 * USER - Risk <br>
	 */
    @Test
	@IE_EDGE_MODE
	@RISK
	@LOG
    public void changeDefaultContentSetTestRiskSharedTest() 
    {
		String iowaContentSet = "Iowa (Development)";

    	homePage().goToHomePage();
		loginPage().logIn();
		
        String initialContentSet = homePage().getCurrentContentSetFromHomepageDropdown();
        
        homeMenu().goToUserPreferencesContentSets();
        userContentSetPreferencesPage().setDefaultTargetContentSet(iowaContentSet);

		TestSetupEdge.closeBrowser();
		TestSetupEdge.openBrowser();
		homePage().goToHomePage();
		loginPage().logIn();

        String finalContentSet = homePage().getCurrentContentSetFromHomepageDropdown();
        boolean contentSetChanged = !initialContentSet.equalsIgnoreCase(finalContentSet);
        Assertions.assertTrue(contentSetChanged, "The Default content set was not updated to the new content set when it should have been");
    }
   
    /**
	 * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that the content set in the top right corner of the Home page gets updated to the new content set
	 * after selecting the new content set in the Related Rule Books page<br>
	 * USER - Risk <br>
	 */
    @Test
	@IE_EDGE_MODE
	@RISK
	@LOG
    public void chooseCorrectContentSetAndVerifyRiskSharedTest() 
    {
        String CODE_OF_FED_REGS_CONTENT_SET = "Code of Fed Regs (Development)";
        String NYSE_CONTENT_SET = "NYSE MKT Rule Book (Development)";

    	homePage().goToHomePage();
		loginPage().logIn();
		
		homePage().setMyContentSet(CODE_OF_FED_REGS_CONTENT_SET);
		hierarchyMenu().goToHierarchyRelatedRuleBooks();
		relatedRuleBooksPage().clickOnGivenRulebook(NYSE_CONTENT_SET);

		//Can't include full alert message because AutoIt will cut off the 'ok' in the content set name that appears in the alert due to the appearance of an 'OK' button
    	boolean confirmationRuleBookAlertAppeared = AutoITUtils.verifyAlertTextContainsAndAccept(true,"Are you sure you want to change your current content set to");
        Assertions.assertTrue(confirmationRuleBookAlertAppeared, "Alert did not appear after clicking on a content set when it should have");

        hierarchyNavigatePage().switchToHierarchyEditPage();
        homeMenu().goToMyHomePage();
        
        String contentSetFromTopRightCornerOfHomePage = homePage().getCurrentContentSetFromUpperRight();
        boolean contentSetInTopRightCornerWasUpdatedCorrectly = contentSetFromTopRightCornerOfHomePage.contains(NYSE_CONTENT_SET);

        Assertions.assertTrue(contentSetInTopRightCornerWasUpdatedCorrectly, "The content set in the top right corner was not updated when it should have");
    }

    /**
	 * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that choosing the existing content set in the Related Rule Books page will not change anything<br>
	 * USER - Risk <br>
	 */
    @Test
	@IE_EDGE_MODE
	@RISK
	@LOG
    public void chooseCurrentContentSetRiskSharedTest() 
    {
    	String CODE_OF_FED_REGS_CONTENT_SET = "Code of Fed Regs (Development)";

    	homePage().goToHomePage();
		loginPage().logIn();
		
		homePage().setMyContentSet(CODE_OF_FED_REGS_CONTENT_SET);
    	hierarchyMenu().goToHierarchyRelatedRuleBooks();
    	relatedRuleBooksPage().clickOnGivenRulebook(CODE_OF_FED_REGS_CONTENT_SET);

		//This is different to the actual text due to the 'OK and 'Cancel' button text
        boolean selectDifferentRuleBookAlertAppeared = AutoITUtils.verifyAlertTextAndAccept(true, "Please select different Rule Book or  selection");
        Assertions.assertTrue(selectDifferentRuleBookAlertAppeared, "Alert to prompt the user to choose a different content set did not appear when it should have");

		homePage().switchToPage();

        String contentSetFromUpperRight = homePage().getCurrentContentSetFromUpperRight();
		boolean contentSetDidNotUpdate = contentSetFromUpperRight.contains(CODE_OF_FED_REGS_CONTENT_SET);

        Assertions.assertTrue(contentSetDidNotUpdate, "The content set changed when it should not have");
    }
    
    /**
	 * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that choosing an option that isn't even a content set in the Related Rule Books page does not change the content set in the Home page<br>
	 * USER - Risk <br>
	 */
    @Test
	@IE_EDGE_MODE
	@RISK
	@LOG
    public void chooseNotAContentSetRiskSharedTest() 
    {
    	String CODE_OF_FED_REGS_CONTENT_SET = "Code of Fed Regs (Development)";
    	String GROUP_RULEBOOK = "Groups";
    	
    	homePage().goToHomePage();
		loginPage().logIn();
		
		homePage().setMyContentSet(CODE_OF_FED_REGS_CONTENT_SET);
    	hierarchyMenu().goToHierarchyRelatedRuleBooks();
    	relatedRuleBooksPage().clickOnGivenRulebook(GROUP_RULEBOOK);

		//This is different to the actual text due to the 'OK' button text
        boolean selectDifferentRuleBookAlertAppeared = AutoITUtils.verifyAlertTextAndAccept(true, "Please select Rule Book");
        Assertions.assertTrue(selectDifferentRuleBookAlertAppeared, "Alert prompting the user to select a different rule book did not appear when it should have");

		homePage().switchToPage();

		String contentSetFromUpperRight = homePage().getCurrentContentSetFromUpperRight();
		boolean contentSetDidNotUpdate = contentSetFromUpperRight.contains(CODE_OF_FED_REGS_CONTENT_SET);

        Assertions.assertTrue(contentSetDidNotUpdate, "The content set changed when it should not have");
    }

    @AfterEach
	public void setDefaultContentSet()
	{
		Connection connection = BaseDatabaseUtils.connectToDatabaseUAT();
		HomeDatabaseUtils.setDefaultTargetContentSet(connection, "RB.CDRb", user().getUsername());
		BaseDatabaseUtils.disconnect(connection);
	}
}
