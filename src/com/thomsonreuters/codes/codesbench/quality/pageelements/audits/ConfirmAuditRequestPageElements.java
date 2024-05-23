package com.thomsonreuters.codes.codesbench.quality.pageelements.audits;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class ConfirmAuditRequestPageElements 
{
	public static final String CONFIRM_AUDIT_REQUEST_PAGE_TITLE = "Confirm Audit Request";
	
	@FindBy(how = How.ID, using = "pageForm:processAudit")
    public static WebElement processAuditButton;
	
	@FindBy(how = How.XPATH, using = "//span[@id='pageForm:errorMessage' and contains(text(), 'An Audit workflow will not be initiated for any source document with zero corresponding target documents.')]")
    public static WebElement errorMessage;

	public static final String ERROR_MESSAGE = "//span[@id='pageForm:errorMessage' and contains(text(), 'An Audit workflow will not be initiated for any source document with zero corresponding target documents.')]";
	
	@FindBy(how = How.XPATH, using = "//table[@id='pageForm:sourceConfimationData']/tbody/tr/td[3]")
    public static WebElement numberOfTargetDocuments;
} 
