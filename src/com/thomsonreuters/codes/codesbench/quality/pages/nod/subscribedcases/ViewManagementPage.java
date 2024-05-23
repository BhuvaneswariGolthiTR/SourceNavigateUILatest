package com.thomsonreuters.codes.codesbench.quality.pages.nod.subscribedcases;

import java.util.List;

import javax.annotation.PostConstruct;

import com.thomsonreuters.codes.codesbench.quality.utilities.autoIT.AutoITUtils;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thomsonreuters.codes.codesbench.quality.pageelements.CommonPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.nod.subscribedcases.ViewWizardPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.viewmanagament.ViewManagementPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import com.thomsonreuters.codes.codesbench.quality.pages.table.TableTestingPage.SubscribedCasesColumns;

@Component
public class ViewManagementPage extends BasePage
{
	WebDriver driver;

	@Autowired
	public ViewManagementPage(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
	}

	@PostConstruct
	public void init()
	{
		PageFactory.initElements(driver, ViewManagementPageElements.class);
	}
	
	public boolean clickEditView()
	{
		click(ViewManagementPageElements.editViewButton);
		boolean viewWizardPageAppeared = switchToWindow(ViewWizardPageElements.VIEW_WIZARD_PAGE_TITLE);
		switchToInnerIFrameByIndex(1);
		return viewWizardPageAppeared;
	}

	public void selectView(String viewName)
	{
		click(String.format(ViewManagementPageElements.VIEW, viewName));
		click(ViewManagementPageElements.selectViewButton);
		breakOutOfFrame();
		waitForWindowGoneByTitle(ViewManagementPageElements.VIEW_MANAGEMENT_WINDOW_TITLE);
	}

	public boolean doesViewExist(String viewName)
	{
		return doesElementExist(String.format(ViewManagementPageElements.VIEW, viewName));
	}

	public void setViewAsDefault(String viewName)   
	{
		click(String.format(ViewManagementPageElements.VIEW, viewName));
		click(ViewManagementPageElements.setViewAsDefaultButton);
		waitForPageLoaded();
	}

	public boolean isViewSelected()
	{
		waitForElement(ViewManagementPageElements.VIEW);
		return isElementSelected(ViewManagementPageElements.VIEW);
	}
	
	public void clickView(String viewName)
	{
		click(String.format(ViewManagementPageElements.VIEW, viewName));
	}

	public boolean openViewCreationWizard()  
	{
		boolean viewWizardPageAppeared = clickAddView();
		switchToInnerIFrameByIndex(1);
		return viewWizardPageAppeared;
	}

	private boolean clickAddView()
	{
		click(ViewManagementPageElements.addViewButton);
		return switchToWindow(ViewWizardPageElements.VIEW_WIZARD_PAGE_TITLE);
	}

	//Note that from source navigate a text checkmark appears:
	//<td class="yui-dt0-col-Default yui-dt-col-Default yui-dt-sortable yui-dt-resizeable" headers="yui-dt0-th-Default "><div class="yui-dt-liner">✔</div></td>
	//In Subscribed Cases an image appears as a text checkmark as well
	//This is the xpath we are currently using
	////tbody[@class='yui-dt-data']//td[contains(@class,'ViewName')]/div[contains(text(),'%s')]/../../td[contains(@class,'Default')]/div[contains(text(),'✔')]
	public boolean isViewDefault(String viewName)
	{
		return doesElementExist(String.format(ViewManagementPageElements.EXPECTED_DEFAULT_VIEW, viewName));
	}

	public void closeViewManagementWindow()
	{
		breakOutOfFrame();
		click(CommonPageElements.WINDOW_CLOSE_CROSS_BUTTON);
		waitForWindowGoneByTitle(ViewManagementPageElements.VIEW_MANAGEMENT_WINDOW_TITLE);
	}

	public void clickFilterSelect()
	{
		click(ViewWizardPageElements.filterSelect);
	}

	public void clickClose()
	{
		click(CommonPageElements.CLOSE_BUTTON);
	}

	public boolean didDataErrorMessageAppear()
	{
		return isElementDisplayed(ViewManagementPageElements.DATA_ERROR_MESSAGE);
	}

	public boolean isFilterDisplayed()
	{
		return isElementDisplayed(ViewWizardPageElements.FILTER_SELECT);
	}

	public List<String> getRuGridColumn()
	{
		return tableTestingPage().getColumnValues(SubscribedCasesColumns.RU);
	}

	public List<String> getLoadedDateGridColumn()
	{
		return tableTestingPage().getColumnValues(SubscribedCasesColumns.LOADED_DATE);
	}

	public boolean switchToViewManagementPage()
	{
		switchToWindow("Navigate");
		boolean pageAppeared = switchToWindow(ViewManagementPageElements.VIEW_MANAGEMENT_WINDOW_TITLE);
		enterTheInnerFrame();
		return pageAppeared;
	}

	public void deleteView(String viewName)
	{
		if(doesElementExist(String.format(ViewManagementPageElements.VIEW, viewName)))
		{
			click(String.format(ViewManagementPageElements.VIEW, viewName));
			click(ViewManagementPageElements.deleteViewButton);
			boolean deleteViewAlertAppeared = AutoITUtils.verifyAlertTextAndAccept(true, "Are you sure you would like to delete the selected view?");
			Assertions.assertTrue(deleteViewAlertAppeared, "The view deletion alert did not appear.");
		}
	}

	public void deleteViews(String viewName)
	{
		boolean viewsLeft = doesElementExist(String.format(ViewManagementPageElements.VIEW, viewName));
		int count = 1;
		while(viewsLeft && count < 10)
		{
			click(String.format(ViewManagementPageElements.VIEW, viewName));
			sendEnterToElement(ViewManagementPageElements.DELETE_VIEW_BUTTON);
			//For some reason AutoITUtils causes the view to not completely delete
			acceptAlert();
			sourcePage().switchToGroupingNavigatePage("");
			switchToWindow(ViewManagementPageElements.VIEW_MANAGEMENT_WINDOW_TITLE);
			enterTheInnerFrame();
			viewsLeft = doesElementExist(String.format(ViewManagementPageElements.VIEW, viewName));
			count++;
		}
	}
}
