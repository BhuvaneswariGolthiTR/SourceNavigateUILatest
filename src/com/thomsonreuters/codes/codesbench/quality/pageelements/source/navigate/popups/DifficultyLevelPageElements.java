package com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.popups;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class DifficultyLevelPageElements
{
	public static final String 	DIFFICULTY_LEVEL_PAGE_TITTLE = "Difficulty Level";

	@FindBy(how = How.ID, using = "pageForm:difficultyLevelid")
	public static WebElement difficultyLevel;

	@FindBy(how = How.ID, using = "pageForm:saveButton")
	public static WebElement saveButton;
}
