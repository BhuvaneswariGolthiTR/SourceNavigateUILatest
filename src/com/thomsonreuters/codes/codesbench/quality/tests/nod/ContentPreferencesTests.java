package com.thomsonreuters.codes.codesbench.quality.tests.nod;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.thomsonreuters.codes.codesbench.quality.pageelements.home.userpreferences.UserPropertiesPageElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;

public class ContentPreferencesTests extends TestService
{
	/**
	 * STORY/BUGS - NA &lt;br&gt;
	 * SUMMARY -  Checks source tags and settings in different parts of the preferences page&lt;br&gt;
	 * USER -  Legal &lt;br&gt;
	 */
	@Test
	@LEGAL
	@LOG
    public void preferencesTest()
    {
		homePage().goToHomePage();
		loginPage().logIn();
		
		boolean landedOnContentPrefs = homeMenu().goToContentPreferences();
		Assertions.assertTrue(landedOnContentPrefs, "The content preferences page did not appear");
		
		//change page elements to content prefe
        boolean nodSourceTagsFoundOnContentPrefs = contentPreferencesPage().sourceTagsFound();
        boolean nodGridSettingsFoundOnContentPrefs = contentPreferencesPage().gridSettingsFound();
		contentPreferencesPage().clickCancel();

        boolean userPreferencesPropertiesPageAppeared = homeMenu().goToUserPreferencesProperties();
        Assertions.assertTrue(userPreferencesPropertiesPageAppeared, "The user preferences properties page did not appear");

        boolean nodSourceTagsFoundOnProperties = propertiesPage().sourceTagsFound();
        boolean nodMergeFoundOnProperties = propertiesPage().mergeFound();
        boolean nodGridSettingsFoundOnProperties = propertiesPage().gridSettingsFound();
        propertiesPage().clickCancel();
        
        Assertions.assertAll
		(
			() -> Assertions.assertTrue(nodSourceTagsFoundOnContentPrefs, "NOD Source Tag Settings did not appear on the Content Preferences page."),
			() -> Assertions.assertTrue(nodGridSettingsFoundOnContentPrefs, "NOD Filter Unreported Cases under the NOD Grid Settings failed to appear on the Content Preferences page."),
			() -> Assertions.assertTrue(nodSourceTagsFoundOnProperties, "NOD Source Tag Settings failed to appear on Properties page."),
			() -> Assertions.assertTrue(nodMergeFoundOnProperties, "NOD Merge Settings failed to appear on Properties page."),
			() -> Assertions.assertTrue(nodGridSettingsFoundOnProperties, "NOD Filter Unreported Cases under the NOD Grid Settings failed to appear on the Properties page.")
		);
    }

	/**
	 * STORY/BUG - HALCYONST-1841 <br>
	 * SUMMARY - A test to verify that other values are not changed in NOD upload settings when
	 * changing the upload time values. <br>
	 * USER - Legal <br>
	 */
	@Test
	@LEGAL
	@LOG
	public void nodUploadSettingsTests()
	{ 
		String dailyTimeToChangeTo = "12:00 PM";
		homePage().goToHomePage();
		loginPage().logIn();
		homeMenu().goToContentPreferences();

		contentPreferencesPage().clickDailyUploadRadioButton();
		String dailyTime = contentPreferencesPage().getDailyTime();

		contentPreferencesPage().setDailyTime(dailyTimeToChangeTo);
		contentPreferencesPage().clickSave();
		String newDailyTime = contentPreferencesPage().getDailyTime();
		Assertions.assertTrue(newDailyTime.equals(dailyTimeToChangeTo), "The daily time was not changed");

		contentPreferencesPage().clickDailyUploadRadioButton();
		String dailyTimeAfterNoUpload = contentPreferencesPage().getDailyTime();
		Assertions.assertTrue(dailyTimeAfterNoUpload.equals(newDailyTime), "Daily time changed when it should not have.");
		contentPreferencesPage().clickDailyUploadRadioButton();

		contentPreferencesPage().setDailyTime(dailyTime);
		contentPreferencesPage().clickSave();
		String resetDailyTime = contentPreferencesPage().getDailyTime();
		Assertions.assertTrue(resetDailyTime.equals(dailyTime), "Daily time was not reset");
	 }
}
