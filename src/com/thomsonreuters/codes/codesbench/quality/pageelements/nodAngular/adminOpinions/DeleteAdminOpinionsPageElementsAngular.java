package com.thomsonreuters.codes.codesbench.quality.pageelements.nodAngular.adminOpinions;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class DeleteAdminOpinionsPageElementsAngular
{
	public static final String HEADING = "Delete Opinion";
	public static final String URL_MODIFIER = "adminOpinions";
	public static final String PAGE_TAG = "//body/app-root/app-admin-opinion-delete-modal";

	@FindBy(how = How.XPATH, using = "//button[text() = 'Delete Opinion']")
	public static WebElement deleteOpinionButton;

	@FindBy(how = How.XPATH, using = "//button[text() = 'Cancel']")
	public static WebElement cancelButton;

}
