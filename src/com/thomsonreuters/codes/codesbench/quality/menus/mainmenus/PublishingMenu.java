package com.thomsonreuters.codes.codesbench.quality.menus.mainmenus;

import com.thomsonreuters.codes.codesbench.quality.menuelements.mainmenuelements.PublishingMenuElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.publishing.novus.*;
import com.thomsonreuters.codes.codesbench.quality.pageelements.publishing.novus.citelinemanagement.CiteLineManagementsCommonPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.publishing.print.RunPubtagRefreshByVOLSPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.publishing.print.ScriptsForContentSetPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.publishing.print.SelectUploadTypePageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.publishing.print.pubextracts.PUBAnnotatedPamphletPocketPartManagementPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.publishing.print.pubextracts.PubExtractsCustomPubExtractManagementPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.publishing.print.pubextracts.PubRecompExtractPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.publishing.print.pubextracts.PubTOCExtractPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.publishing.print.wipextracts.WIPRecompExtractPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.publishing.print.wipextracts.WIPTOCExtractPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.publishing.print.wipextracts.custom.CustomPubExtractManagementPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.publishing.reports.CT1ReportPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.publishing.reports.KnexPubIdReportPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.publishing.toolbox.MainHeaderElements;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class PublishingMenu extends Menu
{
	private WebDriver driver;

	@Autowired
	public PublishingMenu(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
	}

	@PostConstruct
	public void init()
	{
		PageFactory.initElements(driver, PublishingMenuElements.class);
	}

	// Publishing -> Print -> DLU Upload
	public boolean goToPublishingPrintDLUUpload()
	{
		openMenu(PublishingMenuElements.publishingMenu);
		openSubMenu(PublishingMenuElements.print);
		sendEnterToElement(PublishingMenuElements.printDLUUpload);
		return switchToWindow(SelectUploadTypePageElements.SELECT_UPLOAD_TYPE_PAGE_TITLE);
	}

	// Publishing -> Print -> WIP Extracts -> Custom
	public boolean goToPublishingPrintWipExtractsCustom()
	{
		openMenu(PublishingMenuElements.publishingMenu);
		openSubMenu(PublishingMenuElements.print);
		openSubMenu(PublishingMenuElements.printWIPExtracts);
		sendEnterToElement(PublishingMenuElements.printWIPExtractsCustom);
		return switchToWindow(CustomPubExtractManagementPageElements.WIP_EXTRACTS_CUSTOM_PUB_EXTRACT_MANAGEMENT_PAGE_TITLE);
	}

	// Publishing -> Print -> WIP Extracts -> Recomp
	public boolean goToPublishingPrintWipExtractsRecomp()
	{
		openMenu(PublishingMenuElements.publishingMenu);
		openSubMenu(PublishingMenuElements.print);
		openSubMenu(PublishingMenuElements.printWIPExtracts);
		sendEnterToElement(PublishingMenuElements.printWIPExtractsRecomp);
		return switchToWindow(WIPRecompExtractPageElements.WIP_RECOMP_EXTRACT_PAGE_TITLE);
	}

	// Publishing -> Print -> WIP Extracts -> TOC
	public boolean goToPublishingPrintWipExtractsTOC()
	{
		openMenu(PublishingMenuElements.publishingMenu);
		openSubMenu(PublishingMenuElements.print);
		openSubMenu(PublishingMenuElements.printWIPExtracts);
		sendEnterToElement(PublishingMenuElements.printWIPExtractsTOC);
		return switchToWindow(WIPTOCExtractPageElements.WIP_TOC_EXTRACT_PAGE_TITLE);
	}

	// Publishing -> Print -> PUB Extracts -> AnP/PP Management
	public boolean goToPublishingPrintPubExtractsAnPPPManagement()
	{
		openMenu(PublishingMenuElements.publishingMenu);
		openSubMenu(PublishingMenuElements.print);
		openSubMenu(PublishingMenuElements.printPUBExtracts);
		sendEnterToElement(PublishingMenuElements.printPUBExtractsAnpPPManagement);
		return switchToWindow(PUBAnnotatedPamphletPocketPartManagementPageElements.PUB_ANNOTATED_PAMPHLET_POCKET_PART_MANAGEMENT_PAGE_TITLE);
	}

	// Publishing -> Print -> PUB Extracts -> AnP/PP General Table
	// TODO: Implement

	// Publishing -> Print -> PUB Extracts -> Custom
	public boolean goToPublishingPrintPubExtractsCustom()
	{
		openMenu(PublishingMenuElements.publishingMenu);
		openSubMenu(PublishingMenuElements.print);
		openSubMenu(PublishingMenuElements.printPUBExtracts);
		sendEnterToElement(PublishingMenuElements.printPUBExtractsCustom);
		return switchToWindow(PubExtractsCustomPubExtractManagementPageElements.PUB_EXTRACTS_CUSTOM_PUB_EXTRACT_MANAGEMENT);
	}

	// Publishing -> Print -> PUB Extracts -> Recomp
	public boolean goToPublishingPrintPubExtractsRecomp()
	{
		openMenu(PublishingMenuElements.publishingMenu);
		openSubMenu(PublishingMenuElements.print);
		openSubMenu(PublishingMenuElements.printPUBExtracts);
		sendEnterToElement(PublishingMenuElements.printPUBExtractsRecomp);
		return switchToWindow(PubRecompExtractPageElements.PUB_RECOMP_EXTRACT_PAGE_TITLE);
	}

	// Publishing -> Print -> PUB Extracts -> TOC
	public boolean goToPublishingPrintPubExtractsTOC()
	{
		openMenu(PublishingMenuElements.publishingMenu);
		openSubMenu(PublishingMenuElements.print);
		openSubMenu(PublishingMenuElements.printPUBExtracts);
		sendEnterToElement(PublishingMenuElements.printPUBExtractsTOC);
		return switchToWindow(PubTOCExtractPageElements.PUB_TOC_EXTRACT_PAGE_TITLE);
	}

	// Publishing -> Print -> PUB Extracts -> E-book Novus Missing Heading Reports
	// TODO: Implement

	// Publishing -> Print -> Run Pubtag Refresh by VOLS
	public boolean goToPublishingPrintRunPubtagRefreshByVOLS()
	{
		openMenu(PublishingMenuElements.publishingMenu);
		openSubMenu(PublishingMenuElements.print);
		sendEnterToElement(PublishingMenuElements.printRunPUBTagRefreshByVols);
		return switchToWindow(RunPubtagRefreshByVOLSPageElements.PUBTAG_REFRESH_PAGE_TITLE);
	}

	// Publishing -> Print -> View Scripts for Content Set
	public boolean goToPublishingPrintViewScriptsForContentSet()
	{
		openMenu(PublishingMenuElements.publishingMenu);
		openSubMenu(PublishingMenuElements.print);
		sendEnterToElement(PublishingMenuElements.printViewScriptsForContentSet);
		return switchToWindow(ScriptsForContentSetPageElements.SCRIPTS_FOR_CONTENT_SET_PAGE_TITLE);
	}

	// Publishing -> Novus -> Ranking Preferences -> View/Edit Data
	public boolean goToPublishingNovusRankingPreferencesViewEditData()
	{
		openMenu(PublishingMenuElements.publishingMenu);
		openSubMenu(PublishingMenuElements.novus);
		openSubMenu(PublishingMenuElements.novusRankingPreferences);
		sendEnterToElement(PublishingMenuElements.novusRankingPreferencesViewEditData);
		return switchToWindow(RankingPreferencesPageElements.RANKING_PREFERENCES_PAGE_TITLE);
	}

	// Publishing -> Novus -> Ranking Preferences -> View Deleted Rows
	public boolean goToPublishingNovusRankingPreferencesViewDeletedRows()
	{
		openMenu(PublishingMenuElements.publishingMenu);
		openSubMenu(PublishingMenuElements.novus);
		openSubMenu(PublishingMenuElements.novusRankingPreferences);
		sendEnterToElement(PublishingMenuElements.novusRankingPreferencesViewDeletedRows);
		return switchToWindow(RankingPreferencesPageElements.RANKING_PREFERENCES_PAGE_TITLE);
	}

	// Publishing -> Novus -> XFeature Preferences -> View/Edit Data
	public boolean goToPublishingNovusXFeaturePreferencesViewEditData()
	{
		openMenu(PublishingMenuElements.publishingMenu);
		openSubMenu(PublishingMenuElements.novus);
		openSubMenu(PublishingMenuElements.novusXFeaturePreferences);
		sendEnterToElement(PublishingMenuElements.novusXFeaturePreferencesViewEditData);
		return switchToWindow(XFeaturePreferencesPageElements.XFEATURE_PREFERENCES_PAGE_TITLE);
	}

	// Publishing -> Novus -> XFeature Preferences -> View Deleted Rows
	public boolean goToPublishingNovusXFeaturePreferencesViewDeletedRows()
	{
		openMenu(PublishingMenuElements.publishingMenu);
		openSubMenu(PublishingMenuElements.novus);
		openSubMenu(PublishingMenuElements.novusXFeaturePreferences);
		sendEnterToElement(PublishingMenuElements.novusXFeaturePreferencesViewDeletedRows);
		return switchToWindow(XFeaturePreferencesPageElements.XFEATURE_PREFERENCES_PAGE_TITLE);
	}

	// Publishing -> Novus -> Copyright Message Codes -> View/Edit Data
	public boolean goToPublishingNovusCopyrightMessageCodesViewEditData()
	{
		openMenu(PublishingMenuElements.publishingMenu);
		openSubMenu(PublishingMenuElements.novus);
		openSubMenu(PublishingMenuElements.novusCopyrightMessageCodes);
		sendEnterToElement(PublishingMenuElements.novusCopyrightMessageCodesViewEditData);
		return switchToWindow(CopyrightMessageCodesPageElements.COPYRIGHT_MESSAGE_CODES_PAGE_TITLE);
	}

	// Publishing -> Novus -> Copyright Message Codes -> View Deleted Rows
	public boolean goToPublishingNovusCopyrightMessageCodesViewDeletedRows()
	{
		openMenu(PublishingMenuElements.publishingMenu);
		openSubMenu(PublishingMenuElements.novus);
		openSubMenu(PublishingMenuElements.novusCopyrightMessageCodes);
		sendEnterToElement(PublishingMenuElements.novusCopyrightMessageCodesViewDeletedRows);
		return switchToWindow(CopyrightMessageCodesPageElements.COPYRIGHT_MESSAGE_CODES_PAGE_TITLE);
	}

	// Publishing -> Novus -> Currency Message Codes -> View/Edit Data
	public boolean goToPublishingNovusCurrencyMessageCodesViewEditData()
	{
		openMenu(PublishingMenuElements.publishingMenu);
		openSubMenu(PublishingMenuElements.novus);
		openSubMenu(PublishingMenuElements.novusCurrencyMessageCodes);
		sendEnterToElement(PublishingMenuElements.novusCurrencyMessageCodesViewEditData);
		return switchToWindow(CurrencyMessageCodesPageElements.CURRENCY_MESSAGES_PAGE_TITLE);
	}

	// Publishing -> Novus -> Currency Message Codes -> View Deleted Rows
	public boolean goToPublishingNovusCurrencyMessageCodesViewDeletedRows()
	{
		openMenu(PublishingMenuElements.publishingMenu);
		openSubMenu(PublishingMenuElements.novus);
		openSubMenu(PublishingMenuElements.novusCurrencyMessageCodes);
		sendEnterToElement(PublishingMenuElements.novusCurrencyMessageCodesViewDeletedRows);
		return switchToWindow(CurrencyMessageCodesPageElements.CURRENCY_MESSAGES_PAGE_TITLE);
	}

	// Publishing -> Novus -> Message Code Volumes -> Add New Volumes
	public boolean goToPublishingNovusMessageCodeVolumesAddNewVolumes()
	{
		openMenu(PublishingMenuElements.publishingMenu);
		openSubMenu(PublishingMenuElements.novus);
		openSubMenu(PublishingMenuElements.novusMessageCodeVolumes);
		sendEnterToElement(PublishingMenuElements.novusMessageCodeVolumesAddNewVolumes);
		return switchToWindow(AddNewVolumesPageElements.ADD_NEW_VOLUMES_PAGE_TITLE);
	}

	// Publishing -> Novus -> Message Code Volumes -> Delete Volumes
	public boolean goToPublishingNovusMessageCodeVolumesDeleteVolumes()
	{
		openMenu(PublishingMenuElements.publishingMenu);
		openSubMenu(PublishingMenuElements.novus);
		openSubMenu(PublishingMenuElements.novusMessageCodeVolumes);
		sendEnterToElement(PublishingMenuElements.novusMessageCodeVolumesDeleteVolumes);
		return switchToWindow(DeleteVolumesPageElements.DELETE_VOLUMES_PAGE_TITLE);
	}

	// Publishing -> Novus -> Citeline Mangament
	public boolean goToPublishingNovusCitelineManagement()
	{
		openMenu(PublishingMenuElements.publishingMenu);
		openSubMenu(PublishingMenuElements.novus);
		sendEnterToElement(PublishingMenuElements.NOVUS_CITLINE_MANAGAMENT);
		if (waitForWindowByUrl(CiteLineManagementsCommonPageElements.PAGE_TITLE_URL))
		{
			maximizeCurrentWindow();
			return true;
		}
		else
		{
			return false;
		}
	}

	// Publishing -> Reports -> CT1 Report
	public boolean goToPublishingReportsCT1Report() 
	{
		openMenu(PublishingMenuElements.publishingMenu);
		openSubMenu(PublishingMenuElements.reports);
		sendEnterToElement(PublishingMenuElements.reportsCT1Report);
		return switchToWindow(CT1ReportPageElements.CT1_REPORT_PAGE_TITLE);
	}

	// Publishing -> Reports -> PubID Report
	public boolean goToPublishingReportsPubIdReport() 
	{
		openMenu(PublishingMenuElements.publishingMenu);
		openSubMenu(PublishingMenuElements.reports);
		sendEnterToElement(PublishingMenuElements.reportsPubIdReport);
		return switchToWindow(KnexPubIdReportPageElements.KNEX_PUBID_REPORT_PAGE_TITLE);
	}

	// Publishing -> Novus -> Feature Source Heading Message Code
	public boolean goToPublishingNovusFeatureSourceHeadingMessageCode()
	{
		openMenu(PublishingMenuElements.publishingMenu);
		openSubMenu(PublishingMenuElements.novus);
		sendEnterToElement(PublishingMenuElements.novusFeatureSourceHeadingMessageCode);
		return switchToWindow(FeatureSourceHeadingMessageCodesPageElements.FEATURE_SOURCE_HEADING_MESSAGE_CODES_PAGE_TITLE);
	}

	// Publishing -> Codesbench E-book Scripting Report
	// TODO: Implement

	public boolean isPublishingNovusSubmenuItemAvailable(String submenuItemToCheck) 
	{
		openMenu(PublishingMenuElements.publishingMenu);
		openSubMenu(PublishingMenuElements.novus);
		return isElementDisplayed(submenuItemToCheck);
	}
 
    public boolean isPublishingMenuAvailable()
    {
    	return isElementDisplayed(PublishingMenuElements.PUBLISHING_MENU_XPATH);
    }

    // Publishing -> Publishing -> Publish Ready-Text nodes only
    public boolean goToPublishReadyTextNodesOnly()
    {
    	openMenu(PublishingMenuElements.publishingMenu);
		openSubMenu(PublishingMenuElements.publishing);
		sendEnterToElement(PublishingMenuElements.publishReadyTextNodesOnly);
		if (waitForWindowByUrl(MainHeaderElements.PUBLISH_READY_TEXT_NODES_ONLY_URL))
		{
			maximizeCurrentWindow();
			//Need to call waitForGridLoaded in GridPage for tests that verify grid functionality
			return true;
		}
		else
		{
			return false;
		}
    }

	// Publishing -> Publishing -> Publish Approve-Text nodes only
	public boolean goToPublishingPublishApproveTextNodesOnly()
	{
		openMenu(PublishingMenuElements.publishingMenu);
		openSubMenu(PublishingMenuElements.publishing);
		sendEnterToElement(PublishingMenuElements.publishApproveTextNodesOnly);
		if (waitForWindowByUrl(MainHeaderElements.PUBLISH_APPROVE_TEXT_NODES_ONLY_URL))
		{
			maximizeCurrentWindow();
			//Need to call waitForGridLoaded in GridPage for tests that verify grid functionality
			return true;
		}
		else
		{
			return false;
		}
	}

	// Publishing -> Publishing -> Publish Approve-NOD nodes only
	public boolean goToPublishApproveNodNodesOnly()
	{
		openMenu(PublishingMenuElements.publishingMenu);
		openSubMenu(PublishingMenuElements.publishing);
		sendEnterToElement(PublishingMenuElements.publishApproveNodNodesOnly);
		if(waitForWindowByUrl(MainHeaderElements.PUBLISH_APPROVE_NOD_NODES_ONLY_URL))
		{
			maximizeCurrentWindow();
			//Need to call waitForGridLoaded in GridPage for tests that verify grid functionality
			return true;
		}
		else
		{
			return false;
		}
	}

	// Publishing -> Publishing -> Publish Approve-Text and NOD nodes by volume
	public boolean goToPublishApproveTextAndNodNodesByVolume()
	{
		openMenu(PublishingMenuElements.publishingMenu);
		openSubMenu(PublishingMenuElements.publishing);
		sendEnterToElement(PublishingMenuElements.publishApproveTextAndNodNodesByVolume);
		if (waitForWindowByUrl(MainHeaderElements.PUBLISH_APPROVE_TEXT_AND_NOD_NODES_BY_VOLUME_URL))
		{
			maximizeCurrentWindow();
			//Need to call waitForGridLoaded in GridPage for tests that verify grid functionality
			return true;
		}
		else
		{
			return false;
		}
	}

	// Publishing -> Publishing Status Reports -> Pub Navigate Evaluation
	public boolean goToPublishingStatusReportsPubNavigateEvaluation()
	{
		openMenu(PublishingMenuElements.publishingMenu);
		openSubMenu(PublishingMenuElements.publishingStatusReports);
		sendEnterToElement(PublishingMenuElements.publishingStatusReportsPubNavigateEvaluation);
		if(waitForWindowByUrl(MainHeaderElements.PUB_NAVIGATE_EVALUATION_URL))
		{
			maximizeCurrentWindow();
			//Need to call waitForGridLoaded in GridPage for tests that verify grid functionality
			return true;
		}
		else
		{
			return false;
		}
	}

	// Publishing -> Publishing Status Reports -> WIP to PUB upload issues
	public boolean goToPublishingStatusReportsWipToPubUploadIssues()
	{
		openMenu(PublishingMenuElements.publishingMenu);
		openSubMenu(PublishingMenuElements.publishingStatusReports);
		sendEnterToElement(PublishingMenuElements.publishingStatusReportsWipToPubUploadIssues);
		if(waitForWindowByUrl(MainHeaderElements.WIP_TO_PUB_UPLOAD_ISSUES_URL))
		{
			maximizeCurrentWindow();
			//Need to call waitForGridLoaded in GridPage for tests that verify grid functionality
			return true;
		}
		else
		{
			return false;
		}
	}

	// Publishing -> Publishing Status Reports -> ERROR Statuses
	public boolean goToPublishingStatusReportsErrorStatuses()
	{
		openMenu(PublishingMenuElements.publishingMenu);
		openSubMenu(PublishingMenuElements.publishingStatusReports);
		sendEnterToElement(PublishingMenuElements.publishingStatusReportsErrorStatuses);
		if(waitForWindowByUrl(MainHeaderElements.ERROR_STATUSES_URL))
		{
			maximizeCurrentWindow();
			//Need to call waitForGridLoaded in GridPage for tests that verify grid functionality
			return true;
		}
		else
		{
			return false;
		}
	}

	// Publishing -> Publishing Status Reports -> Nod Only - Pub Navigate Evaluation
	public boolean goToPublishingStatusReportsNodOnlyPubNavigateEvaluation()
	{
		openMenu(PublishingMenuElements.publishingMenu);
		openSubMenu(PublishingMenuElements.publishingStatusReports);
		sendEnterToElement(PublishingMenuElements.publishingStatusReportsNodOnlyPubNavigateEvaluation);
		if(waitForWindowByUrl(MainHeaderElements.NOD_ONLY_PUB_NAVIGATE_EVALUATION_URL))
		{
			maximizeCurrentWindow();
			//Need to call waitForGridLoaded in GridPage for tests that verify grid functionality
			return true;
		}
		else
		{
			return false;
		}
	}

	// Publishing -> Publishing Status Reports -> Nod Only - Wip to Pub Upload Issues
	public boolean goToPublishingStatusReportsNodOnlyWipToPubUploadIssues()
	{
		openMenu(PublishingMenuElements.publishingMenu);
		openSubMenu(PublishingMenuElements.publishingStatusReports);
		sendEnterToElement(PublishingMenuElements.publishingStatusReportsNodOnlyWipToPubUploadIssues);
		if(waitForWindowByUrl(MainHeaderElements.NOD_ONLY_WIP_to_PUB_UPLOAD_ISSUES_URL))
		{
			maximizeCurrentWindow();
			//Need to call waitForGridLoaded in GridPage for tests that verify grid functionality
			return true;
		}
		else
		{
			return false;
		}
	}

	// Publishing -> Publishing Status Reports -> Pub Navigate Evaluation - Error Status
	public boolean goToPublishingStatusReportsNodOnlyErrorStatus()
	{
		openMenu(PublishingMenuElements.publishingMenu);
		openSubMenu(PublishingMenuElements.publishingStatusReports);
		sendEnterToElement(PublishingMenuElements.publishingStatusReportsNodOnlyErrorStatuses);
		if(waitForWindowByUrl(MainHeaderElements.NOD_ONLY_ERROR_STATUS_URL))
		{
			maximizeCurrentWindow();
			//Need to call waitForGridLoaded in GridPage for tests that verify grid functionality
			return true;
		}
		else
		{
			return false;
		}
	}

	// Publishing -> Publishing Status Reports -> Westlaw Load Complete
	public boolean goToPublishingStatusReportsLoadCompleteWlReview()
	{
		openMenu(PublishingMenuElements.publishingMenu);
		openSubMenu(PublishingMenuElements.publishingStatusReports);
		sendEnterToElement(PublishingMenuElements.publishingStatusReportsWestlawLoadComplete);
		if(waitForWindowByUrl(MainHeaderElements.WESTLAW_LOAD_CONPLETE_URL))
		{
			maximizeCurrentWindow();
			return true;
		}
		else
		{
			return false;
		}
	}

	public boolean isDluUploadSubMenuOptionDisplayed()
	{
		openMenu(PublishingMenuElements.publishingMenu);
		openSubMenu(PublishingMenuElements.print);
		return isElementDisplayed(PublishingMenuElements.DLU_UPLOAD_XPATH);
	}

	public void openMenu()
	{
		sendKeyToElement(PublishingMenuElements.publishingMenu, Keys.ARROW_DOWN);
	}


}
