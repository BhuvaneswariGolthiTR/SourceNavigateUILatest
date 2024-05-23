package com.thomsonreuters.codes.codesbench.quality.pageelements.nodAngular.adminOpinions;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class EditAdminOpinionsPageElementsAngular
{
	public static final String HEADING = "Edit Admin. Opinion %s";
	public static final String URL_MODIFIER = "adminOpinions";
	public static final String PAGE_TAG = "//body/app-root/app-admin-opinion-edit-classify";

	@FindBy(how = How.XPATH, using = "//button[text() = 'Update']")
	public static WebElement updateButton;

	@FindBy(how = How.XPATH, using = "//button[text() = 'Cancel']")
	public static WebElement cancelButton;

}
