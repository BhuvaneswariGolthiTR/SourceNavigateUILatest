package com.thomsonreuters.codes.codesbench.quality.utilities;

import com.thomsonreuters.codes.codesbench.quality.menus.clamshell.source.AbstractReportsClamshellPage;
import com.thomsonreuters.codes.codesbench.quality.menus.clamshell.source.create.LineageTabCreateClamshellPage;
import com.thomsonreuters.codes.codesbench.quality.menus.clamshell.source.create.RenditionTabCreateClamshellPage;
import com.thomsonreuters.codes.codesbench.quality.menus.clamshell.source.edit.DeltaTabEditClamshellPage;
import com.thomsonreuters.codes.codesbench.quality.menus.clamshell.source.edit.LineageTabEditClamshellPage;
import com.thomsonreuters.codes.codesbench.quality.menus.clamshell.source.edit.RenditionTabEditClamshellPage;
import com.thomsonreuters.codes.codesbench.quality.menus.clamshell.source.edit.SectionTabEditClamshellPage;
import com.thomsonreuters.codes.codesbench.quality.menus.clamshell.source.modify.DeltaTabModifyClamshellPage;
import com.thomsonreuters.codes.codesbench.quality.menus.clamshell.source.modify.LineageTabModifyClamshellPage;
import com.thomsonreuters.codes.codesbench.quality.menus.clamshell.source.modify.RenditionTabModifyClamshellPage;
import com.thomsonreuters.codes.codesbench.quality.menus.clamshell.source.modify.SectionTabModifyClamshellPage;
import com.thomsonreuters.codes.codesbench.quality.menus.clamshell.source.reports.DeltaTabReportsClamshellPage;
import com.thomsonreuters.codes.codesbench.quality.menus.clamshell.source.reports.LineageTabReportsClamshellPage;
import com.thomsonreuters.codes.codesbench.quality.menus.clamshell.source.reports.RenditionTabReportsClamshellPage;
import com.thomsonreuters.codes.codesbench.quality.menus.clamshell.source.reports.SectionTabReportsClamshellPage;
import com.thomsonreuters.codes.codesbench.quality.menus.clamshell.source.sync.LineageTabSyncClamshellPage;
import com.thomsonreuters.codes.codesbench.quality.menus.clamshell.source.sync.RenditionTabSyncClamshellPage;
import com.thomsonreuters.codes.codesbench.quality.menus.clamshell.source.track.DeltaTabTrackClamshellPage;
import com.thomsonreuters.codes.codesbench.quality.menus.clamshell.source.track.LineageTabTrackClamshellPage;
import com.thomsonreuters.codes.codesbench.quality.menus.clamshell.source.track.RenditionTabTrackClamshellPage;
import com.thomsonreuters.codes.codesbench.quality.menus.clamshell.source.track.SectionTabTrackClamshellPage;
import com.thomsonreuters.codes.codesbench.quality.menus.clamshell.source.validate.LineageTabValidateClamshellPage;
import com.thomsonreuters.codes.codesbench.quality.menus.clamshell.source.validate.RenditionTabValidateClamshellPage;
import com.thomsonreuters.codes.codesbench.quality.menus.clamshell.source.view.DeltaTabViewClamshellPage;
import com.thomsonreuters.codes.codesbench.quality.menus.clamshell.source.view.LineageTabViewClamshellPage;
import com.thomsonreuters.codes.codesbench.quality.menus.clamshell.source.view.RenditionTabViewClamshellPage;
import com.thomsonreuters.codes.codesbench.quality.menus.clamshell.source.view.SectionTabViewClamshellPage;
import com.thomsonreuters.codes.codesbench.quality.menus.contextmenus.activity.ActivityUserLogContextMenu;
import com.thomsonreuters.codes.codesbench.quality.menus.contextmenus.audits.ReportCentralContextMenu;
import com.thomsonreuters.codes.codesbench.quality.menus.contextmenus.audits.SetLawTrackingContextMenu;
import com.thomsonreuters.codes.codesbench.quality.menus.contextmenus.editor.EditorQueryContextMenu;
import com.thomsonreuters.codes.codesbench.quality.menus.contextmenus.editor.EditorTextContextMenu;
import com.thomsonreuters.codes.codesbench.quality.menus.contextmenus.editor.EditorTreeContextMenu;
import com.thomsonreuters.codes.codesbench.quality.menus.contextmenus.editor.popups.TargetLocatorContextMenu;
import com.thomsonreuters.codes.codesbench.quality.menus.contextmenus.hierarchy.navigate.*;
import com.thomsonreuters.codes.codesbench.quality.menus.contextmenus.hierarchy.navigate.popups.OnlineProductAssignmentsContextMenu;
import com.thomsonreuters.codes.codesbench.quality.menus.contextmenus.hierarchy.navigate.popups.TaxTypeAssignmentsContextMenu;
import com.thomsonreuters.codes.codesbench.quality.menus.contextmenus.hierarchy.reports.LockedNodesReportContextMenu;
import com.thomsonreuters.codes.codesbench.quality.menus.contextmenus.nod.AdministrativeOpinionsContextMenu;
import com.thomsonreuters.codes.codesbench.quality.menus.contextmenus.nod.EditOpinionContextMenu;
import com.thomsonreuters.codes.codesbench.quality.menus.contextmenus.nod.HeadnotesContextMenu;
import com.thomsonreuters.codes.codesbench.quality.menus.contextmenus.nod.SubscribedCasesContextMenu;
import com.thomsonreuters.codes.codesbench.quality.menus.contextmenus.publishing.novus.CiteLineManagementContextMenu;
import com.thomsonreuters.codes.codesbench.quality.menus.contextmenus.publishing.toolbox.GridContextMenu;
import com.thomsonreuters.codes.codesbench.quality.menus.contextmenus.source.BtsWebUiContextMenu;
import com.thomsonreuters.codes.codesbench.quality.menus.contextmenus.source.navigate.*;
import com.thomsonreuters.codes.codesbench.quality.menus.contextmenus.tools.*;
import com.thomsonreuters.codes.codesbench.quality.menus.contextmenus.source.reports.LockReportContextMenu;
import com.thomsonreuters.codes.codesbench.quality.menus.contextmenus.tools.OnlineProductMaintenanceContextMenu;
import com.thomsonreuters.codes.codesbench.quality.menus.contextmenus.tools.QueryNoteReportContextMenu;
import com.thomsonreuters.codes.codesbench.quality.menus.contextmenus.tools.SpellcheckManagerContextMenu;
import com.thomsonreuters.codes.codesbench.quality.menus.contextmenus.tools.StocknoteManagerContextMenu;
import com.thomsonreuters.codes.codesbench.quality.menus.contextmenus.tools.ocextract.PublicationFilesContextMenu;
import com.thomsonreuters.codes.codesbench.quality.menus.contextmenus.tools.ocextract.PublicationsContextMenu;
import com.thomsonreuters.codes.codesbench.quality.menus.contextmenus.tools.statefeed.FileManagementContextMenu;
import com.thomsonreuters.codes.codesbench.quality.menus.mainmenus.ActivityMenu;
import com.thomsonreuters.codes.codesbench.quality.menus.mainmenus.AuditsMenu;
import com.thomsonreuters.codes.codesbench.quality.menus.mainmenus.HierarchyMenu;
import com.thomsonreuters.codes.codesbench.quality.menus.mainmenus.HomeMenu;
import com.thomsonreuters.codes.codesbench.quality.menus.mainmenus.NodMenu;
import com.thomsonreuters.codes.codesbench.quality.menus.mainmenus.PublishingMenu;
import com.thomsonreuters.codes.codesbench.quality.menus.mainmenus.SourceMenu;
import com.thomsonreuters.codes.codesbench.quality.menus.mainmenus.ToolsMenu;
import com.thomsonreuters.codes.codesbench.quality.pageelements.publishing.novus.citelinemanagement.nomalizedcite.NormalizedCiteReferencesPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.editor.popups.InsertTablePage;
import com.thomsonreuters.codes.codesbench.quality.pages.hierarchy.navigate.popups.*;
import com.thomsonreuters.codes.codesbench.quality.pages.hierarchy.reports.HierarchyDateSearchMultipleDocumentsPage;
import com.thomsonreuters.codes.codesbench.quality.pages.hierarchy.reports.LockedNodesReportPage;
import com.thomsonreuters.codes.codesbench.quality.pages.hierarchy.reports.ScriptListingReportPage;
import com.thomsonreuters.codes.codesbench.quality.pages.hierarchy.navigate.*;
import com.thomsonreuters.codes.codesbench.quality.pages.login.LoginESSOPage;
import com.thomsonreuters.codes.codesbench.quality.pages.login.PingIdPasscodePage;
import com.thomsonreuters.codes.codesbench.quality.pages.nodAngular.popups.courts.AddCourtRoutingPopupAngular;
import com.thomsonreuters.codes.codesbench.quality.pages.nodAngular.popups.reports.*;
import com.thomsonreuters.codes.codesbench.quality.pages.nodAngular.popups.tools.*;
import com.thomsonreuters.codes.codesbench.quality.pages.nortextractor.NortExtractorPage;
import com.thomsonreuters.codes.codesbench.quality.pages.popups.RawXmlDocumentClosurePage;
import com.thomsonreuters.codes.codesbench.quality.pages.popups.RawXmlEditorPage;
import com.thomsonreuters.codes.codesbench.quality.pages.publishing.novus.citelinemanagementpage.CiteLineManagementPage;
import com.thomsonreuters.codes.codesbench.quality.pages.publishing.novus.citelinemanagementpage.normalizedcitepage.AddNewNormalizedCitePage;
import com.thomsonreuters.codes.codesbench.quality.pages.publishing.novus.citelinemanagementpage.citelinereferencespage.AddNewCiteLineReferencesPage;
import com.thomsonreuters.codes.codesbench.quality.pages.publishing.novus.citelinemanagementpage.citelinereferencespage.CiteLineReferencesPage;
import com.thomsonreuters.codes.codesbench.quality.pages.publishing.novus.citelinemanagementpage.citelinereferencespage.DeleteCitelineReferencePage;
import com.thomsonreuters.codes.codesbench.quality.pages.publishing.novus.citelinemanagementpage.citelinereferencespage.EditCiteLineReferencesPage;
import com.thomsonreuters.codes.codesbench.quality.pages.publishing.novus.citelinemanagementpage.normalizedcitepage.AddOrEditNormalizedCitePage;
import com.thomsonreuters.codes.codesbench.quality.pages.publishing.novus.citelinemanagementpage.normalizedcitepage.EditNormalizedCitePage;
import com.thomsonreuters.codes.codesbench.quality.pages.publishing.novus.citelinemanagementpage.normalizedcitepage.NormalizedCitePage;
import com.thomsonreuters.codes.codesbench.quality.pages.publishing.print.RunPubtagRefreshByVolsPage;
import com.thomsonreuters.codes.codesbench.quality.pages.publishing.print.wipextracts.custom.CustomPubExtractManagementPage;
import com.thomsonreuters.codes.codesbench.quality.pages.publishing.print.wipextracts.custom.CustomPubExtractOptionsPage;
import com.thomsonreuters.codes.codesbench.quality.pages.publishing.print.wipextracts.custom.CustomPubExtractSavePage;
import com.thomsonreuters.codes.codesbench.quality.pages.publishing.print.wipextracts.custom.CustomPubExtractTreeViewPage;
import com.thomsonreuters.codes.codesbench.quality.pages.sourcenavigateangular.*;
import com.thomsonreuters.codes.codesbench.quality.pages.sourcenavigateangular.indexai.*;
import com.thomsonreuters.codes.codesbench.quality.pages.sourcenavigateangular.popups.SourceNavigateAngularPopUpPage;
import com.thomsonreuters.codes.codesbench.quality.pages.sourcenavigateangular.popups.SourceNavigateAngularToastPage;
import com.thomsonreuters.codes.codesbench.quality.pages.tools.RedliningComparePage;
import com.thomsonreuters.codes.codesbench.quality.pages.tools.ocextract.PublicationFilesPage;
import com.thomsonreuters.codes.codesbench.quality.pages.tools.ocextract.PublicationFilesTablePage;
import com.thomsonreuters.codes.codesbench.quality.pages.tools.ocextract.PublicationsTablePage;
import com.thomsonreuters.codes.codesbench.quality.pages.tools.ocextract.popups.ToastNotificationPopup;
import com.thomsonreuters.codes.codesbench.quality.pages.tools.ocextract.popups.UploadFilePopup;
import com.thomsonreuters.codes.codesbench.quality.pages.tools.onlineproductmaintancepage.OnlineProductMaintenancePage;
import com.thomsonreuters.codes.codesbench.quality.pages.tools.onlineproductmaintancepage.popups.CreateNewOnlineProductPage;
import com.thomsonreuters.codes.codesbench.quality.pages.tools.onlineproductmaintancepage.popups.DeleteOnlineProductPage;
import com.thomsonreuters.codes.codesbench.quality.pages.tools.onlineproductmaintancepage.popups.EditNamesAndAssignmentsPage;
import com.thomsonreuters.codes.codesbench.quality.pages.tools.onlineproductmaintancepage.popups.ViewAssignmentStatusPage;
import com.thomsonreuters.codes.codesbench.quality.pages.login.NodLoginPage;
import com.thomsonreuters.codes.codesbench.quality.pages.nod.tools.InitiateNodReportPage;
import com.thomsonreuters.codes.codesbench.quality.pages.nodAngular.*;
import com.thomsonreuters.codes.codesbench.quality.pages.nodAngular.headnotesPageFragments.*;
import com.thomsonreuters.codes.codesbench.quality.pages.nodAngular.CasesTablePageAngular;
import com.thomsonreuters.codes.codesbench.quality.pages.tableAngular.popups.FilterPopUp;
import com.thomsonreuters.codes.codesbench.quality.pages.nodAngular.popups.*;
import com.thomsonreuters.codes.codesbench.quality.pages.source.popups.EditTaxTypesPage;
import com.thomsonreuters.codes.codesbench.quality.pages.source.navigate.popups.InsertIntoHierarchyWizardNavigatePage;
import com.thomsonreuters.codes.codesbench.quality.pages.editor.EditorHomePage;
import com.thomsonreuters.codes.codesbench.quality.pageelements.nod.NodRawXmlEditorPage;
import com.thomsonreuters.codes.codesbench.quality.pages.abstractPages.AbstractPropertiesPage;
import com.thomsonreuters.codes.codesbench.quality.pages.activity.EffectiveDatePage;
import com.thomsonreuters.codes.codesbench.quality.pages.source.popups.*;
import com.thomsonreuters.codes.codesbench.quality.pages.nodAngular.adminOpinions.AdminOpinionsContextMenuAngular;
import com.thomsonreuters.codes.codesbench.quality.pages.nodAngular.adminOpinions.DeleteAdminOpinionsPageAngular;
import com.thomsonreuters.codes.codesbench.quality.pages.nodAngular.adminOpinions.EditAdminOpinionsPageAngular;
import com.thomsonreuters.codes.codesbench.quality.pages.source.DateSearchFilterPage;
import com.thomsonreuters.codes.codesbench.quality.pages.source.SharedDeltaReportPage;
import com.thomsonreuters.codes.codesbench.quality.pages.source.StageCheckReportPage;
import com.thomsonreuters.codes.codesbench.quality.pages.source.navigate.popups.StageCheckReportsPage;
import com.thomsonreuters.codes.codesbench.quality.pages.source.navigate.popups.DeltaEffectiveDatePage;
import com.thomsonreuters.codes.codesbench.quality.pages.source.navigate.MismatchedReportFiltersPage;
import com.thomsonreuters.codes.codesbench.quality.pages.source.navigate.*;
import com.thomsonreuters.codes.codesbench.quality.pages.nodAngular.popups.HeadnoteDetailsPopupAngular;
import com.thomsonreuters.codes.codesbench.quality.pages.source.navigate.popups.SyncPage;
import com.thomsonreuters.codes.codesbench.quality.pages.source.EditPDFMetadataPage;
import com.thomsonreuters.codes.codesbench.quality.pages.source.navigate.popups.DeleteConfirmationPopupPage;
import com.thomsonreuters.codes.codesbench.quality.pages.source.navigate.popups.*;
import com.thomsonreuters.codes.codesbench.quality.pages.source.navigate.popups.ViewBaselinesNavigatePage;
import com.thomsonreuters.codes.codesbench.quality.pages.source.navigate.popups.topicalHeadingHighlight.MarkIndexEntryPage;
import com.thomsonreuters.codes.codesbench.quality.pages.source.navigate.popups.topicalHeadingHighlight.TopicalHeadingHighlightPage;
import com.thomsonreuters.codes.codesbench.quality.pages.source.navigate.popups.topicalHeadingHighlight.UserPhrasePage;
import com.thomsonreuters.codes.codesbench.quality.pages.source.reports.LockReportPage;
import com.thomsonreuters.codes.codesbench.quality.pages.activity.ActivityUserLogPage;
import com.thomsonreuters.codes.codesbench.quality.pages.audits.AuditByDocumentPage;
import com.thomsonreuters.codes.codesbench.quality.pages.audits.AuditBySourcePage;
import com.thomsonreuters.codes.codesbench.quality.pages.audits.ConfirmAuditRequestPage;
import com.thomsonreuters.codes.codesbench.quality.pages.audits.reportcentral.ReportCentralFiltersPage;
import com.thomsonreuters.codes.codesbench.quality.pages.audits.reportcentral.ReportCentralGridPage;
import com.thomsonreuters.codes.codesbench.quality.pages.audits.reportcentral.ReportCentralPage;
import com.thomsonreuters.codes.codesbench.quality.pages.editor.*;
import com.thomsonreuters.codes.codesbench.quality.pages.editor.popups.*;
import com.thomsonreuters.codes.codesbench.quality.pages.hierarchy.GlossaryTermPage;
import com.thomsonreuters.codes.codesbench.quality.pages.hierarchy.InsertNewNodesPage;
import com.thomsonreuters.codes.codesbench.quality.pages.hierarchy.RelatedRuleBooksPage;
import com.thomsonreuters.codes.codesbench.quality.pages.hierarchy.PreviousWipVersionsPage;
import com.thomsonreuters.codes.codesbench.quality.pages.hierarchy.publishing.BulkPublishPage;
import com.thomsonreuters.codes.codesbench.quality.pages.hierarchy.publishing.UndeletePage;
import com.thomsonreuters.codes.codesbench.quality.pages.hierarchy.pubnavigate.HierarchyPubNavigatePage;
import com.thomsonreuters.codes.codesbench.quality.pages.hierarchy.pubnavigate.HierarchyPubNavigateSearchPage;
import com.thomsonreuters.codes.codesbench.quality.pages.home.ContentPreferencesPage;
import com.thomsonreuters.codes.codesbench.quality.pages.home.ContentSetPublishingConfigurationPage;
import com.thomsonreuters.codes.codesbench.quality.pages.home.HomePage;
import com.thomsonreuters.codes.codesbench.quality.pages.home.navigation.ActivityNavigationSharedContentSetPage;
import com.thomsonreuters.codes.codesbench.quality.pages.home.userpreferences.ContentSetsPage;
import com.thomsonreuters.codes.codesbench.quality.pages.home.userpreferences.PropertiesPage;
import com.thomsonreuters.codes.codesbench.quality.pages.home.userpreferences.SecurityPage;
import com.thomsonreuters.codes.codesbench.quality.pages.login.LoginPage;
import com.thomsonreuters.codes.codesbench.quality.pages.modal.SpellcheckReportModal;
import com.thomsonreuters.codes.codesbench.quality.pages.nod.CourtsPage;
import com.thomsonreuters.codes.codesbench.quality.pages.nod.FindPage;
import com.thomsonreuters.codes.codesbench.quality.pages.nod.ListOfCasesPage;
import com.thomsonreuters.codes.codesbench.quality.pages.nod.administrativeopinions.*;
import com.thomsonreuters.codes.codesbench.quality.pages.nod.blueline.BluelineAnalysisPage;
import com.thomsonreuters.codes.codesbench.quality.pages.nod.blueline.DeleteBluelinePage;
import com.thomsonreuters.codes.codesbench.quality.pages.nod.blueline.EditBluelinePage;
import com.thomsonreuters.codes.codesbench.quality.pages.nod.blueline.InsertBluelinePage;
import com.thomsonreuters.codes.codesbench.quality.pages.nod.headnotes.*;
import com.thomsonreuters.codes.codesbench.quality.pages.nod.reports.BrowseReportsPage;
import com.thomsonreuters.codes.codesbench.quality.pages.nod.reports.NoTeamReportPage;
import com.thomsonreuters.codes.codesbench.quality.pages.nod.reports.ReportsDetailPage;
import com.thomsonreuters.codes.codesbench.quality.pages.nod.reports.ReportsSummaryPage;
import com.thomsonreuters.codes.codesbench.quality.pages.nod.subscribedcases.*;
import com.thomsonreuters.codes.codesbench.quality.pages.publishing.toolbox.*;
import com.thomsonreuters.codes.codesbench.quality.pages.source.bts.*;
import com.thomsonreuters.codes.codesbench.quality.pages.source.navigate.deltagrouping.*;
import com.thomsonreuters.codes.codesbench.quality.pages.source.navigate.sectiongrouping.*;
import com.thomsonreuters.codes.codesbench.quality.pages.table.TableTestingPage;
import com.thomsonreuters.codes.codesbench.quality.pages.tools.effectivedatemetricsreport.EffectiveDateMetricsReportPage;
import com.thomsonreuters.codes.codesbench.quality.pages.tools.keyciteextract.KeyCiteResearchReferencesFiltersPage;
import com.thomsonreuters.codes.codesbench.quality.pages.tools.keyciteextract.KeyCiteResearchReferencesPage;
import com.thomsonreuters.codes.codesbench.quality.pages.tools.onlineproductmaintancepage.OnlineProductMaintenanceGridPage;
import com.thomsonreuters.codes.codesbench.quality.pages.tools.onlineproductmaintancepage.popups.*;
import com.thomsonreuters.codes.codesbench.quality.pages.tools.querynotereport.*;
import com.thomsonreuters.codes.codesbench.quality.pages.tools.querynotereportangular.*;
import com.thomsonreuters.codes.codesbench.quality.pages.tools.scriptmaintenance.copy.CopyScriptForVersionPage;
import com.thomsonreuters.codes.codesbench.quality.pages.tools.scriptmaintenance.copy.CopyScriptPage;
import com.thomsonreuters.codes.codesbench.quality.pages.tools.scriptmaintenance.create.CreateScriptPage;
import com.thomsonreuters.codes.codesbench.quality.pages.tools.scriptmaintenance.create.CreateScriptVersion998Page;
import com.thomsonreuters.codes.codesbench.quality.pages.tools.scriptmaintenance.create.CreateScriptVersionPage;
import com.thomsonreuters.codes.codesbench.quality.pages.tools.scriptmaintenance.delete.DeleteScriptConfirmationPage;
import com.thomsonreuters.codes.codesbench.quality.pages.tools.scriptmaintenance.edit.EditScriptRulesConfirmationPage;
import com.thomsonreuters.codes.codesbench.quality.pages.tools.scriptmaintenance.edit.EditScriptRulesPage;
import com.thomsonreuters.codes.codesbench.quality.pages.tools.scriptmaintenance.page.ScriptMaintenanceClamshellPage;
import com.thomsonreuters.codes.codesbench.quality.pages.tools.scriptmaintenance.page.ScriptMaintenanceErrorPage;
import com.thomsonreuters.codes.codesbench.quality.pages.tools.scriptmaintenance.page.ScriptMaintenanceGridPage;
import com.thomsonreuters.codes.codesbench.quality.pages.tools.scriptmaintenance.page.ScriptMaintenanceMetadataPage;
import com.thomsonreuters.codes.codesbench.quality.pages.tools.scriptmaintenance.view.ViewDescriptionContentSetsPage;
import com.thomsonreuters.codes.codesbench.quality.pages.tools.scriptmaintenance.view.ViewPrintListOfScriptPage;
import com.thomsonreuters.codes.codesbench.quality.pages.tools.scriptmaintenance.view.ViewPrintScriptRulesPage;
import com.thomsonreuters.codes.codesbench.quality.pages.tools.searchandreplace.CopyTableToAnotherContentSetPage;
import com.thomsonreuters.codes.codesbench.quality.pages.tools.searchandreplace.SearchAndReplacePage;
import com.thomsonreuters.codes.codesbench.quality.pages.tools.searchandreplace.SearchAndReplaceReportPage;
import com.thomsonreuters.codes.codesbench.quality.pages.tools.spellcheckmanager.SpellcheckManagerFiltersPage;
import com.thomsonreuters.codes.codesbench.quality.pages.tools.spellcheckmanager.SpellcheckManagerGridPage;
import com.thomsonreuters.codes.codesbench.quality.pages.tools.spellcheckmanager.SpellcheckManagerPage;
import com.thomsonreuters.codes.codesbench.quality.pages.tools.statefeed.ExtractedDataPage;
import com.thomsonreuters.codes.codesbench.quality.pages.tools.statefeed.FileManagementPage;
import com.thomsonreuters.codes.codesbench.quality.pages.tools.statefeed.StateFeedBasePage;
import com.thomsonreuters.codes.codesbench.quality.pages.tools.stocknotemanager.*;
import com.thomsonreuters.codes.codesbench.quality.pages.tools.workflowreportingsystem.popups.YourWorkflowHasBeenCreatedPage;
import com.thomsonreuters.codes.codesbench.quality.pages.tools.workflowreportingsystem.workflowdetails.WorkflowDetailsPage;
import com.thomsonreuters.codes.codesbench.quality.pages.tools.workflowreportingsystem.workflowdetails.WorkflowPropertiesPage;
import com.thomsonreuters.codes.codesbench.quality.pages.tools.workflowreportingsystem.workflowsearch.WorkflowSearchPage;
import com.thomsonreuters.codes.codesbench.quality.pages.tools.ocextract.PublicationsPage;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.popname.PopNameDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.urls.Urls;
import com.thomsonreuters.codes.codesbench.quality.utilities.users.*;

/**
 * A class where new Page Services are to be added to become accessible from tests
 * Need to register new Services beans in spring.xml
 */
public class TestService extends TestSetupEdge
{
	/*
	 * services
	 */
	protected LoginPage loginPage()
	{
		return annotationConfigApplicationContext.getBean(LoginPage.class);
	}

	protected LoginESSOPage loginESSOPage()
	{
		return annotationConfigApplicationContext.getBean(LoginESSOPage.class);
	}

	protected PingIdPasscodePage pingIdPasscodePage()
	{
		return annotationConfigApplicationContext.getBean(PingIdPasscodePage.class);
	}

	protected NodLoginPage nodLoginPage()
	{
		return annotationConfigApplicationContext.getBean(NodLoginPage.class);
	}

	protected HomePage homePage()
	{
		return annotationConfigApplicationContext.getBean(HomePage.class);
	}

	protected SourceMenu sourceMenu()
	{
		return annotationConfigApplicationContext.getBean(SourceMenu.class);
	}

	protected HomeMenu homeMenu()
	{
		return annotationConfigApplicationContext.getBean(HomeMenu.class);
	}

	protected NodMenu nodMenu()
	{
		return annotationConfigApplicationContext.getBean(NodMenu.class);
	}

	protected PublishingMenu publishingMenu()
	{
		return annotationConfigApplicationContext.getBean(PublishingMenu.class);
	}

	protected BtsWebUiPage btsWebUiPage()
	{
		return annotationConfigApplicationContext.getBean(BtsWebUiPage.class);
	}

	protected GenerateLegislativeServiceTablesPage generateLegislativeServiceTablesPage()
	{
		return annotationConfigApplicationContext.getBean(GenerateLegislativeServiceTablesPage.class);
	}

	protected GeneratePocketPartTablesPage generatePocketPartTablesPage()
	{
		return annotationConfigApplicationContext.getBean(GeneratePocketPartTablesPage.class);
	}

	protected ListPocketPartTablesPage listPocketPartTablesPage()
	{
		return annotationConfigApplicationContext.getBean(ListPocketPartTablesPage.class);
	}

	protected ListLegislativeServiceTablesPage listLegislativeServiceTablesPage()
	{
		return annotationConfigApplicationContext.getBean(ListLegislativeServiceTablesPage.class);
	}

	protected ListPocketPartTablesPage listPocketPartsTablePageElements()
	{
		return annotationConfigApplicationContext.getBean(ListPocketPartTablesPage.class);
	}

	protected AuditsMenu auditsMenu()
	{
		return annotationConfigApplicationContext.getBean(AuditsMenu.class);
	}

	protected AuditBySourcePage auditBySourcePage()
	{
		return annotationConfigApplicationContext.getBean(AuditBySourcePage.class);
	}

	protected DateSearchFilterPage dateSearchFilterPage()
	{
		return annotationConfigApplicationContext.getBean(DateSearchFilterPage.class);
	}

	protected ConfirmAuditRequestPage confirmAuditRequestPage()
	{
		return annotationConfigApplicationContext.getBean(ConfirmAuditRequestPage.class);
	}
	protected WorkflowSearchPage workflowSearchPage()
	{
		return annotationConfigApplicationContext.getBean(WorkflowSearchPage.class);
	}

	protected HeadnotesContextMenu headnotesContextMenu()
	{
		return annotationConfigApplicationContext.getBean(HeadnotesContextMenu.class);
	}

	protected EditPDFMetadataPage editPDFMetadataPage() {return annotationConfigApplicationContext.getBean(EditPDFMetadataPage.class);}

	protected GridContextMenu gridContextMenu()
	{
		return annotationConfigApplicationContext.getBean(GridContextMenu.class);
	}

	protected HierarchyPubNavigatePage hierarchyPubNavigatePage()
	{
		return annotationConfigApplicationContext.getBean(HierarchyPubNavigatePage.class);
	}

	protected HierarchyPubNavigateSearchPage hierarchyPubNavigateSearchPage()
	{
		return annotationConfigApplicationContext.getBean(HierarchyPubNavigateSearchPage.class);
	}

	protected BulkPublishPage bulkPublishPage()
	{
		return annotationConfigApplicationContext.getBean(BulkPublishPage.class);
	}

	protected ContentEditorialSystemPage contentEditorialSystemPage()
	{
		return annotationConfigApplicationContext.getBean(ContentEditorialSystemPage.class);
	}

	protected ConfirmActionPage confirmActionPage()
	{
		return annotationConfigApplicationContext.getBean(ConfirmActionPage.class);
	}

	protected ContentSetsPage userContentSetPreferencesPage()
	{
		return annotationConfigApplicationContext.getBean(ContentSetsPage.class);
	}

	protected ContentPreferencesPage contentPreferencesPage()
	{
		return annotationConfigApplicationContext.getBean(ContentPreferencesPage.class);
	}

	protected EditorHomePage dynamicScrollingHomePage()
	{
		return annotationConfigApplicationContext.getBean(EditorHomePage.class);
	}

	protected ReportCentralPage reportCentralPage()
	{
		return annotationConfigApplicationContext.getBean(ReportCentralPage.class);
	}

	protected EditorPage editorPage()
	{
		return annotationConfigApplicationContext.getBean(EditorPage.class);
	}

	protected InsertSpecialCharacterPage insertSpecialCharacterPage()
	{
		return annotationConfigApplicationContext.getBean(InsertSpecialCharacterPage.class);
	}

	protected DocumentPreviewPage documentPreviewPage()
	{
		return annotationConfigApplicationContext.getBean(DocumentPreviewPage.class);
	}

	protected EditorToolbarPage editorToolbarPage()
	{
		return annotationConfigApplicationContext.getBean(EditorToolbarPage.class);
	}

	protected EditorTreePage editorTreePage()
	{
		return annotationConfigApplicationContext.getBean(EditorTreePage.class);
	}

	protected EditAttributesPage editAttributesPage()
	{
		return annotationConfigApplicationContext.getBean(EditAttributesPage.class);
	}

	protected  InsertTablePage insertTablePage()
	{
		return annotationConfigApplicationContext.getBean(InsertTablePage.class);
	}

	protected EditHvsInformationPage editHvsInformationPage()
	{
		return annotationConfigApplicationContext.getBean(EditHvsInformationPage.class);
	}

	protected AdvancedSearchAndReplacePage advancedSearchAndReplacePage()
	{
		return annotationConfigApplicationContext.getBean(AdvancedSearchAndReplacePage.class);
	}

	protected InsertDeltaPage insertDeltaPage()
	{
		return annotationConfigApplicationContext.getBean(InsertDeltaPage.class);
	}

	protected AdvancedSearchAndReplaceScanPage advancedSearchAndReplaceScanPage()
	{
		return annotationConfigApplicationContext.getBean(AdvancedSearchAndReplaceScanPage.class);
	}

	protected FindAndReplacePage findAndReplacePage()
	{
		return annotationConfigApplicationContext.getBean(FindAndReplacePage.class);
	}

	protected FindInChunksPage findInChunksPage()
	{
		return annotationConfigApplicationContext.getBean(FindInChunksPage.class);
	}

	protected AdvancedSearchAndReplaceSearchAndReplacePage advancedSearchAndReplaceSearchAndReplacePage()
	{
		return annotationConfigApplicationContext.getBean(AdvancedSearchAndReplaceSearchAndReplacePage.class);
	}

	protected AddPubtagPage addPubtagPage() { return annotationConfigApplicationContext.getBean(AddPubtagPage.class); }

	protected SearchAndReplaceReportPage searchAndReplaceReportPage()
	{
		return annotationConfigApplicationContext.getBean(SearchAndReplaceReportPage.class);
	}

	protected SpellcheckPage spellcheckPage()
	{
		return annotationConfigApplicationContext.getBean(SpellcheckPage.class);
	}

	protected DeletePubtagPage deletePubtagPage()
	{
		return annotationConfigApplicationContext.getBean(DeletePubtagPage.class);
	}

	protected RemovePubtagPage removePubtagPage()
	{
		return annotationConfigApplicationContext.getBean(RemovePubtagPage.class);
	}

	protected SelectPubtagsPage selectPubtagsPage()
	{
		return annotationConfigApplicationContext.getBean(SelectPubtagsPage.class);
	}

	protected SelectDatePage selectDatePage()
	{
		return annotationConfigApplicationContext.getBean(SelectDatePage.class);
	}

	protected InsertFootnotePage insertFootnotePage()
	{
		return annotationConfigApplicationContext.getBean(InsertFootnotePage.class);
	}

	protected InsertImagePage insertImagePage()
	{
		return annotationConfigApplicationContext.getBean(InsertImagePage.class);
	}

	protected InsertGlossaryReferencePage insertGlossaryReferencePage()
	{
		return annotationConfigApplicationContext.getBean(InsertGlossaryReferencePage.class);
	}

	protected InsertWestMarkupPage insertWestMarkupPage()
	{
		return annotationConfigApplicationContext.getBean(InsertWestMarkupPage.class);
	}

	protected InsertSourceCiteReferencePage insertSourceCiteReferencePage()
	{
		return annotationConfigApplicationContext.getBean(InsertSourceCiteReferencePage.class);
	}

	protected PendingRenditionNavigatePage pendingRenditionNavigatePage()
	{
		return annotationConfigApplicationContext.getBean(PendingRenditionNavigatePage.class);
	}

	protected EditorTextPage editorTextPage()
	{
		return annotationConfigApplicationContext.getBean(EditorTextPage.class);
	}

	protected EditorMessagePage editorMessagePage()
	{
		return annotationConfigApplicationContext.getBean(EditorMessagePage.class);
	}

	protected DocumentClosurePage editorClosurePage()
	{
		return annotationConfigApplicationContext.getBean(DocumentClosurePage.class);
	}

	protected EditorTextContextMenu editorTextContextMenu()
	{
		return annotationConfigApplicationContext.getBean(EditorTextContextMenu.class);
	}

	protected EditorTreeContextMenu editorTreeContextMenu()
	{
		return annotationConfigApplicationContext.getBean(EditorTreeContextMenu.class);
	}

	protected EditorPreferencesPage editorPreferencesPage()
	{
		return annotationConfigApplicationContext.getBean(EditorPreferencesPage.class);
	}

	protected CreditPhraseEditorPage creditPhraseEditorPage()
	{
		return annotationConfigApplicationContext.getBean(CreditPhraseEditorPage.class);
	}

	protected ChangeSourceTagPage changeSourceTagPage()
	{
		return annotationConfigApplicationContext.getBean(ChangeSourceTagPage.class);
	}

	protected CreateQueryNotePage queryNotePage()
	{
		return annotationConfigApplicationContext.getBean(CreateQueryNotePage.class);
	}

	protected HierarchyNavigatePage hierarchyNavigatePage()
	{
		return annotationConfigApplicationContext.getBean(HierarchyNavigatePage.class);
	}

	protected InsertIntoHierarchyWizardNavigatePage insertIntoHierarchyWizardPage()
	{
		return annotationConfigApplicationContext.getBean(InsertIntoHierarchyWizardNavigatePage.class);
	}

	protected RedliningComparePage redliningComparePage(){return annotationConfigApplicationContext.getBean(RedliningComparePage.class);}


	protected SearchAndReplacePage searchAndReplacePage()
	{
		return annotationConfigApplicationContext.getBean(SearchAndReplacePage.class);
	}

	protected CopyTableToAnotherContentSetPage copyTableToAnotherContentSetPage()
	{
		return annotationConfigApplicationContext.getBean(CopyTableToAnotherContentSetPage.class);
	}

	protected GlossaryTermPage glossaryTermPage()
	{
		return annotationConfigApplicationContext.getBean(GlossaryTermPage.class);
	}

	protected HierarchyInputAsSiblingPage hierarchyInputAsSiblingPage()
	{
		return annotationConfigApplicationContext.getBean(HierarchyInputAsSiblingPage.class);
	}

	protected InsertNewNodesPage insertNewNodesPage()
	{
		return annotationConfigApplicationContext.getBean(InsertNewNodesPage.class);
	}

	protected PreviousWipVersionsPage previousWipVersionsPage()
	{
		return annotationConfigApplicationContext.getBean(PreviousWipVersionsPage.class);
	}

	protected ViewBaselinesNavigatePage viewBaselinesNavigatePage()
	{
		return annotationConfigApplicationContext.getBean(ViewBaselinesNavigatePage.class);
	}

	protected SetLawTrackingPage setLawTrackingPage()
	{
		return annotationConfigApplicationContext.getBean(SetLawTrackingPage.class);
	}

	protected SetLawTrackingContextMenu setLawTrackingContextMenu()
	{
		return annotationConfigApplicationContext.getBean(SetLawTrackingContextMenu.class);
	}

	protected ReportCentralContextMenu reportCentralContextMenu()
	{
		return annotationConfigApplicationContext.getBean(ReportCentralContextMenu.class);
	}

	protected SourceNavigatePage sourcePage()
	{
		return annotationConfigApplicationContext.getBean(SourceNavigatePage.class);
	}

	protected DocumentSearchFilterPage documentSearchFilterPage()
	{
		return annotationConfigApplicationContext.getBean(DocumentSearchFilterPage.class);
	}

	protected GroupingPropertiesPage groupingPropertiesPage()
	{
		return annotationConfigApplicationContext.getBean(GroupingPropertiesPage.class);
	}

	protected AssignUserPage assignUserPage()
	{
		return annotationConfigApplicationContext.getBean(AssignUserPage.class);
	}

	protected TableTestingPage tableTestingPage()
	{
		return annotationConfigApplicationContext.getBean(TableTestingPage.class);
	}

	protected ActivityMenu activityMenu()
	{
		return annotationConfigApplicationContext.getBean(ActivityMenu.class);
	}

	protected EditorTextContextMenu editorTextContextMenuPage()
	{
		return annotationConfigApplicationContext.getBean(EditorTextContextMenu.class);
	}

	protected HierarchyMenu hierarchyMenu()
	{
		return annotationConfigApplicationContext.getBean(HierarchyMenu.class);
	}

	protected ToolsMenu toolsMenu()
	{
		return annotationConfigApplicationContext.getBean(ToolsMenu.class);
	}

	protected SiblingMetadataPage siblingMetadataPage()
	{
		return annotationConfigApplicationContext.getBean(SiblingMetadataPage.class);
	}

	protected InputDocumentContentPage inputDocumentContentPage() { return annotationConfigApplicationContext.getBean(InputDocumentContentPage.class); }

	protected SourceRawXmlEditorPage sourceRawXmlEditorPage()
	{
		return annotationConfigApplicationContext.getBean(SourceRawXmlEditorPage.class);
	}

	protected NodRawXmlEditorPage nodRawXmlEditorPage()
	{
		return annotationConfigApplicationContext.getBean(NodRawXmlEditorPage.class);
	}

	protected HierarchyRawXmlEditorPage hierarchyRawXmlEditorPage()
	{
		return annotationConfigApplicationContext.getBean(HierarchyRawXmlEditorPage.class);
	}

	protected HierarchyRawXmlDocumentClosurePage hierarchyRawXmlDocumentClosurePage()
	{
		return annotationConfigApplicationContext.getBean(HierarchyRawXmlDocumentClosurePage.class);
	}

	protected SourceRawXmlDocumentClosurePage sourceRawXmlDocumentClosurePage()
	{
		return annotationConfigApplicationContext.getBean(SourceRawXmlDocumentClosurePage.class);
	}

	protected RawXmlEditorPage rawXmlEditorPage()
	{
		return annotationConfigApplicationContext.getBean(RawXmlEditorPage.class);
	}

	protected RawXmlDocumentClosurePage rawXmlDocumentClosurePage()
	{
		return annotationConfigApplicationContext.getBean(RawXmlDocumentClosurePage.class);
	}

	protected CreateBookmarkPage createBookmarkPage()
	{
		return annotationConfigApplicationContext.getBean(CreateBookmarkPage.class);
	}

	protected HierarchyTreePage hierarchyTreePage()
	{
		return annotationConfigApplicationContext.getBean(HierarchyTreePage.class);
	}

	protected TreeContextMenu hierarchyTreeContextMenu()
	{
		return annotationConfigApplicationContext.getBean(TreeContextMenu.class);
	}

	protected SiblingMetadataContextMenu siblingMetadataContextMenu()
	{
		return annotationConfigApplicationContext.getBean(SiblingMetadataContextMenu.class);
	}

	protected RelocateBinAsChildSiblingPage relocateBinAsChildSiblingPage()
	{
		return annotationConfigApplicationContext.getBean(RelocateBinAsChildSiblingPage.class);
	}

	protected AdministrativeOpinionsPage administrativeOpinionsPage()
	{
		return annotationConfigApplicationContext.getBean(AdministrativeOpinionsPage.class);
	}

	protected AdministrativeOpinionsPageAngular administrativeOpinionsPageAngular()
	{
		return annotationConfigApplicationContext.getBean(AdministrativeOpinionsPageAngular.class);
	}

	protected AdministrativeOpinionsTablePageAngular administrativeOpinionsTablePageAngular()
	{
		return annotationConfigApplicationContext.getBean(AdministrativeOpinionsTablePageAngular.class);
	}

	protected EditAdminPageAngular editAdminPageAngular()
	{
		return annotationConfigApplicationContext.getBean(EditAdminPageAngular.class);
	}

	protected EditAdminOpinionsPageAngular editAdminOpinionsPageAngular()
	{
		return annotationConfigApplicationContext.getBean(EditAdminOpinionsPageAngular.class);
	}

	protected InsertOpinionPopupAngular insertOpinionPopupAngular()
	{
		return annotationConfigApplicationContext.getBean(InsertOpinionPopupAngular.class);
	}

	protected DeleteAdminOpinionsPageAngular deleteAdminOpinionsPageAngular()
	{
		return annotationConfigApplicationContext.getBean(DeleteAdminOpinionsPageAngular.class);
	}

	protected HomePageAngular homePageAngular()
	{
		return annotationConfigApplicationContext.getBean(HomePageAngular.class);
	}

	protected SubscribedCasesPageAngular subscribedCasesPageAngular()
	{
		return annotationConfigApplicationContext.getBean(SubscribedCasesPageAngular.class);
	}

	protected CasesPageAngular casesPageAngular()
	{
		return annotationConfigApplicationContext.getBean(CasesPageAngular.class);
	}

	protected CourtsPageAngular courtsPageAngular()
	{
		return annotationConfigApplicationContext.getBean(CourtsPageAngular.class);
	}

	protected AddCourtRoutingPopupAngular addCourtRoutingPopupAngular()
	{
		return annotationConfigApplicationContext.getBean(AddCourtRoutingPopupAngular.class);
	}

	protected ImportCourtTeamRoutingPopupAngular importCourtTeamRoutingPopupAngular()
	{
		return annotationConfigApplicationContext.getBean(ImportCourtTeamRoutingPopupAngular.class);
	}

	protected DetailReportPopupAngular detailReportPopupAngular()
	{
		return annotationConfigApplicationContext.getBean(DetailReportPopupAngular.class);
	}

	protected NoTeamReportPopupAngular noTeamReportPopupAngular()
	{
		return annotationConfigApplicationContext.getBean(NoTeamReportPopupAngular.class);
	}

	protected SummaryReportPopupAngular summaryReportPopupAngular()
	{
		return annotationConfigApplicationContext.getBean(SummaryReportPopupAngular.class);
	}

	protected AutoMergeReportPopupAngular autoMergeReportPopupAngular()
	{
		return annotationConfigApplicationContext.getBean(AutoMergeReportPopupAngular.class);
	}

	protected DateSelectorPopupAngular dateSelectorPopupAngular()
	{
		return annotationConfigApplicationContext.getBean(DateSelectorPopupAngular.class);
	}

	protected InitiateNODBatchMergePopupAngular initiateNODBatchMergePopupAngular()
	{
		return annotationConfigApplicationContext.getBean(InitiateNODBatchMergePopupAngular.class);
	}

	protected ToolsGridPopupAngular toolsGridPopupAngular()
	{
		return annotationConfigApplicationContext.getBean(ToolsGridPopupAngular.class);
	}

	protected ToolsFooterPopupAngular toolsFooterPopupAngular()
	{
		return annotationConfigApplicationContext.getBean(ToolsFooterPopupAngular.class);
	}

	protected InitiateNODUnmergedReportPopupAngular initiateNODUnmergedReportPopupAngular()
	{
		return annotationConfigApplicationContext.getBean(InitiateNODUnmergedReportPopupAngular.class);
	}

	protected InitiateNODUpdatePopupAngular initiateNODUpdatePopupAngular()
	{
		return annotationConfigApplicationContext.getBean(InitiateNODUpdatePopupAngular.class);
	}

	protected InitiateXUSSCUpdatePopupAngular initiateXUSSCUpdatePopupAngular()
	{
		return annotationConfigApplicationContext.getBean(InitiateXUSSCUpdatePopupAngular.class);
	}

	protected InitiateNODBatchReorderPopupsAngular initiateNODBatchReorderPopupsAngular()
	{
		return annotationConfigApplicationContext.getBean(InitiateNODBatchReorderPopupsAngular.class);
	}

	protected ImportLtcNovusLoadFilePopupsAngular importLtcNovusLoadFilePopupsAngular()
	{
		return annotationConfigApplicationContext.getBean(ImportLtcNovusLoadFilePopupsAngular.class);
	}

	protected InitiateNODDataValidationPopupAngular initiateNODDataValidationPopupAngular()
	{
		return annotationConfigApplicationContext.getBean(InitiateNODDataValidationPopupAngular.class);
	}

	protected HeadnotesPageAngular headnotesPageAngular()
	{
		return annotationConfigApplicationContext.getBean(HeadnotesPageAngular.class);
	}

	protected UsCaseInformationFragmentAngular usCaseInformationFragmentAngular()
	{
		return annotationConfigApplicationContext.getBean(UsCaseInformationFragmentAngular.class);
	}

	protected CanadaCaseInformationFragmentAngular canadaCaseInformationFragmentAngular()
	{
		return annotationConfigApplicationContext.getBean(CanadaCaseInformationFragmentAngular.class);
	}

	protected HeadnotesClassificationFragmentAngular headnotesClassificationFragmentAngular()
	{
		return annotationConfigApplicationContext.getBean(HeadnotesClassificationFragmentAngular.class);
	}

	protected SynopsisFragmentAngular synopsisFragmentAngular()
	{
		return annotationConfigApplicationContext.getBean(SynopsisFragmentAngular.class);
	}

	protected HierarchyTreeFragmentAngular hierarchyTreeFragmentAngular()
	{
		return annotationConfigApplicationContext.getBean(HierarchyTreeFragmentAngular.class);
	}

	protected HeadnotesContextMenuAngular headnotesContextMenuAngular()
	{
		return annotationConfigApplicationContext.getBean(HeadnotesContextMenuAngular.class);
	}

	protected SubscribedCasesContextMenuAngular subscribedCasesContextMenuAngular()
	{
		return annotationConfigApplicationContext.getBean(SubscribedCasesContextMenuAngular.class);
	}

	protected TitleBarRibbonAngular titleBarRibbonAngular()
	{
		return annotationConfigApplicationContext.getBean(TitleBarRibbonAngular.class);
	}

	protected CasesTablePageAngular casesTablePage()
	{
		return annotationConfigApplicationContext.getBean(CasesTablePageAngular.class);
	}

	protected SubscribedCasesTablePageAngular subscribedCasesTablePage()
	{
		return annotationConfigApplicationContext.getBean(SubscribedCasesTablePageAngular.class);
	}

	protected CourtsTablePageAngular courtsTablePageAngular()
	{
		return annotationConfigApplicationContext.getBean(CourtsTablePageAngular.class);
	}

	protected NotesPopupAngular notesPopupAngular() { return annotationConfigApplicationContext.getBean(NotesPopupAngular.class); }

	protected NotificationPopupAngular notificationPopupAngular()
	{
		return annotationConfigApplicationContext.getBean(NotificationPopupAngular.class);
	}

	protected UploadFilePopup uploadFilePopup()
	{
		return annotationConfigApplicationContext.getBean(UploadFilePopup.class);
	}

	protected ToastNotificationPopup toastNotificationPopup()
	{
		return annotationConfigApplicationContext.getBean(ToastNotificationPopup.class);
	}

	protected UpdateMetadataPopupAngular updateMetadataPopupAngular()
	{
		return annotationConfigApplicationContext.getBean(UpdateMetadataPopupAngular.class);
	}

	protected FilterPopUp filterPopUp()
	{
		return annotationConfigApplicationContext.getBean(FilterPopUp.class);
	}

	protected TitleBarRibbonAngular nodClassifyRibbonAngular()
	{
		return annotationConfigApplicationContext.getBean(TitleBarRibbonAngular.class);
	}

	protected DeleteOpinionPage deleteOpinionPage()
	{
		return annotationConfigApplicationContext.getBean(DeleteOpinionPage.class);
	}

	protected EditOpinionsPage editOpinionPage()
	{
		return annotationConfigApplicationContext.getBean(EditOpinionsPage.class);
	}

	protected InsertOpinionPage insertOpinionPage()
	{
		return annotationConfigApplicationContext.getBean(InsertOpinionPage.class);
	}

	protected AdministrativeOpinionsGridPage administrativeOpinionsGridPage()
	{
		return annotationConfigApplicationContext.getBean(AdministrativeOpinionsGridPage.class);
	}

	protected AdministrativeOpinionsContextMenu administrativeOpinionsContextMenu()
	{
		return annotationConfigApplicationContext.getBean(AdministrativeOpinionsContextMenu.class);
	}

	protected AdminOpinionsContextMenuAngular adminOpinionsContextMenuAngular()
	{
		return annotationConfigApplicationContext.getBean(AdminOpinionsContextMenuAngular.class);
	}

	protected HeadnotesDetailsPage headnotesDetailsPage()
	{
		return annotationConfigApplicationContext.getBean(HeadnotesDetailsPage.class);
	}

	protected BluelineAnalysisPage bluelineAnalysisPage()
	{
		return annotationConfigApplicationContext.getBean(BluelineAnalysisPage.class);
	}

	protected DeleteBluelinePage deleteBluelinePage()
	{
		return annotationConfigApplicationContext.getBean(DeleteBluelinePage.class);
	}

	protected EditBluelinePage editBluelinePage()
	{
		return annotationConfigApplicationContext.getBean(EditBluelinePage.class);
	}

	protected InsertBluelinePage insertBluelinePage()
	{
		return annotationConfigApplicationContext.getBean(InsertBluelinePage.class);
	}

	protected ListOfCasesPage listOfCasesPage()
	{
		return annotationConfigApplicationContext.getBean(ListOfCasesPage.class);
	}

	protected CourtsPage courtsPage()
	{
		return annotationConfigApplicationContext.getBean(CourtsPage.class);
	}
	protected PropertiesPage propertiesPage()
	{
		return annotationConfigApplicationContext.getBean(PropertiesPage.class);
	}

	protected BrowseReportsPage browseReportsPage()
	{
		return annotationConfigApplicationContext.getBean(BrowseReportsPage.class);
	}

	protected NoTeamReportPage noTeamReportsPage()
	{
		return annotationConfigApplicationContext.getBean(NoTeamReportPage.class);
	}

	protected ReportsDetailPage reportsDetailPage()
	{
		return annotationConfigApplicationContext.getBean(ReportsDetailPage.class);
	}

	protected ReportsSummaryPage reportsSummaryPage()
	{
		return annotationConfigApplicationContext.getBean(ReportsSummaryPage.class);
	}

	protected HeadnotesTablePage headnotesTablePage()
	{
		return annotationConfigApplicationContext.getBean(HeadnotesTablePage.class);
	}

	protected HeadnotesTreePage headnotesTreePage()
	{
		return annotationConfigApplicationContext.getBean(HeadnotesTreePage.class);
	}

	protected FindPage findPage()
	{
		return annotationConfigApplicationContext.getBean(FindPage.class);
	}

	protected EditOpinionContextMenu editOpinionContextMenu()
	{
		return annotationConfigApplicationContext.getBean(EditOpinionContextMenu.class);
	}

	protected HeadnotesPage headnotesPage()
	{
		return annotationConfigApplicationContext.getBean(HeadnotesPage.class);
	}

	protected BluelineAnalysisPopupAngular bluelineAnalysisPopupAngular()
	{
		return annotationConfigApplicationContext.getBean(BluelineAnalysisPopupAngular.class);
	}

	protected InsertBluelinePopupAngular insertBluelinePopupAngular()
	{
		return annotationConfigApplicationContext.getBean(InsertBluelinePopupAngular.class);
	}

	protected HeadnoteDetailsPopupAngular headnoteDetailsPopupAngular()
	{
		return annotationConfigApplicationContext.getBean(HeadnoteDetailsPopupAngular.class);
	}

	protected CalendarWidgetAngular calendarWidgetAngular()
	{
		return annotationConfigApplicationContext.getBean(CalendarWidgetAngular.class);
	}

	protected HeadnotesBenchmarksFragmentAngular headnotesBenchmarksFragmentAngular()
	{
		return annotationConfigApplicationContext.getBean(HeadnotesBenchmarksFragmentAngular.class);
	}

	protected KeywordFoundPopupAngular keywordFoundPopupAngular()
	{
		return annotationConfigApplicationContext.getBean(KeywordFoundPopupAngular.class);
	}

	protected HeadnotesSearchPage headnotesSearchPage()
	{
		return annotationConfigApplicationContext.getBean(HeadnotesSearchPage.class);
	}

	protected SubscribedCasesPage subscribedCasesPage()
	{
		return annotationConfigApplicationContext.getBean(SubscribedCasesPage.class);
	}

	protected SubscribedCasesGridPage subscribedCasesGridPage()
	{
		return annotationConfigApplicationContext.getBean(SubscribedCasesGridPage.class);
	}

	protected SubscribedCasesContextMenu subscribedCasesContextMenu()
	{
		return annotationConfigApplicationContext.getBean(SubscribedCasesContextMenu.class);
	}

	protected SubscribedCasesPropertiesPage subscribedCasesPropertiesPage()
	{
		return annotationConfigApplicationContext.getBean(SubscribedCasesPropertiesPage.class);
	}

	protected NotesPage notesPage()
	{
		return annotationConfigApplicationContext.getBean(NotesPage.class);
	}

	protected ViewManagementPage viewManagementPage()
	{
		return annotationConfigApplicationContext.getBean(ViewManagementPage.class);
	}
	protected DeleteConfirmationPopupPage deleteConfirmationPopupPage()
	{
		return annotationConfigApplicationContext.getBean(DeleteConfirmationPopupPage.class);
	}

	protected AddShellPage addShellPage()
	{
		return annotationConfigApplicationContext.getBean(AddShellPage.class);
	}

	protected EditShellPage editShellPage()
	{
			return annotationConfigApplicationContext.getBean(EditShellPage.class);
	}

	protected ViewWizardPage viewWizardPage()
	{
		return annotationConfigApplicationContext.getBean(ViewWizardPage.class);
	}

	protected SpellcheckManagerPage spellcheckManagerPage()
	{
		return annotationConfigApplicationContext.getBean(SpellcheckManagerPage.class);
	}

	protected SpellcheckManagerFiltersPage spellcheckMangerFiltersPage()
	{
		return annotationConfigApplicationContext.getBean(SpellcheckManagerFiltersPage.class);
	}

	protected SpellcheckManagerFiltersPage spellcheckManagerFiltersPage()
	{
		return annotationConfigApplicationContext.getBean(SpellcheckManagerFiltersPage.class);
	}

	protected SpellcheckManagerGridPage spellcheckManagerGridPage()
	{
		return annotationConfigApplicationContext.getBean(SpellcheckManagerGridPage.class);
	}

	protected SpellcheckManagerContextMenu spellcheckManagerContextMenu()
	{
		return annotationConfigApplicationContext.getBean(SpellcheckManagerContextMenu.class);
	}

	protected StocknoteManagerPage stocknoteManagerPage()
	{
		return annotationConfigApplicationContext.getBean(StocknoteManagerPage.class);
	}

	protected StocknoteManagerFiltersPage stocknoteManagerFiltersPage()
	{
		return annotationConfigApplicationContext.getBean(StocknoteManagerFiltersPage.class);
	}

	protected StocknoteManagerGridPage stocknoteManagerGridPage()
	{
		return annotationConfigApplicationContext.getBean(StocknoteManagerGridPage.class);
	}

	protected StocknotePropertiesPage stocknotePropertiesPage()
	{
		return annotationConfigApplicationContext.getBean(StocknotePropertiesPage.class);
	}

	protected StocknoteHotKeysPage stocknoteHotKeysPage()
	{
		return annotationConfigApplicationContext.getBean(StocknoteHotKeysPage.class);
	}

	protected StocknoteSearchAndReplacePage stocknoteSearchAndReplacePage()
	{
		return annotationConfigApplicationContext.getBean(StocknoteSearchAndReplacePage.class);
	}

	protected StocknoteManagerContextMenu stocknoteManagerContextMenu()
	{
		return annotationConfigApplicationContext.getBean(StocknoteManagerContextMenu.class);
	}

	protected StocknoteTableManagementPage stocknoteTableManagementPage()
	{
		return annotationConfigApplicationContext.getBean(StocknoteTableManagementPage.class);
	}

	protected SectionContextMenu sectionContextMenu()
	{
		return annotationConfigApplicationContext.getBean(SectionContextMenu.class);
	}

	protected SectionGroupContextMenu sectionGroupContextMenu()
	{
		return annotationConfigApplicationContext.getBean(SectionGroupContextMenu.class);
	}

	protected DeltaGroupContextMenu deltaGroupContextMenu()
	{
		return annotationConfigApplicationContext.getBean(DeltaGroupContextMenu.class);
	}

	protected PopNameDatabaseUtils popNameDatabaseUtils()
	{
		return annotationConfigApplicationContext.getBean(PopNameDatabaseUtils.class);
	}

	protected ActivityUserLogPage activityUserLogPage()
	{
		return annotationConfigApplicationContext.getBean(ActivityUserLogPage.class);
	}

	protected EffectiveDatePage effectiveDatePage()
	{
		return annotationConfigApplicationContext.getBean(EffectiveDatePage.class);
	}

	protected ActivityUserLogContextMenu activityUserLogContextMenu()
	{
		return annotationConfigApplicationContext.getBean(ActivityUserLogContextMenu.class);
	}

	protected com.thomsonreuters.codes.codesbench.quality.pages.hierarchy.publishing.DeletePage deletePage()
	{
		return annotationConfigApplicationContext.getBean(com.thomsonreuters.codes.codesbench.quality.pages.hierarchy.publishing.DeletePage.class);
	}

	protected BillGapCountGridPage billGapCountGridPage()
	{
		return annotationConfigApplicationContext.getBean(BillGapCountGridPage.class);
	}

	protected BillGapCountContentSetPage billGapCountContentSetPage()
	{
		return annotationConfigApplicationContext.getBean(BillGapCountContentSetPage.class);
	}
	protected UndeletePage undeletePage()
	{
		return annotationConfigApplicationContext.getBean(UndeletePage.class);
	}

	/*
	 * QUERY NOTE REPORT
	 */
	protected QueryNoteReportPage queryNoteReportPage()
	{
		return annotationConfigApplicationContext.getBean(QueryNoteReportPage.class);
	}

	protected QueryNoteReportFiltersPage queryNoteReportFiltersPage()
	{
		return annotationConfigApplicationContext.getBean(QueryNoteReportFiltersPage.class);
	}

	protected QueryNoteReportEditPage queryNoteReportEditPage()
	{
		return annotationConfigApplicationContext.getBean(QueryNoteReportEditPage.class);
	}

	protected QueryNoteReportGridPage queryNoteReportGridPage()
	{
		return annotationConfigApplicationContext.getBean(QueryNoteReportGridPage.class);
	}

	protected QueryNoteReportContextMenu queryNoteReportContextMenu()
	{
		return annotationConfigApplicationContext.getBean(QueryNoteReportContextMenu.class);
	}

	protected DeleteQueryNotePage deleteQueryNotePage()
	{
		return annotationConfigApplicationContext.getBean(DeleteQueryNotePage.class);
	}

	protected QueryNoteReportResolvePage queryNoteResolvePage()
	{
		return annotationConfigApplicationContext.getBean(QueryNoteReportResolvePage.class);
	}

	protected QueryNoteReportDeletePage queryNoteDeletePage()
	{
		return annotationConfigApplicationContext.getBean(QueryNoteReportDeletePage.class);
	}

	/*
	 * QUERY NOTE REPORT ANGULAR
	 */
	protected QueryNoteReportAngularTablePage queryNoteReportAngularTablePage()
	{
		return annotationConfigApplicationContext.getBean(QueryNoteReportAngularTablePage.class);
	}

	protected QueryNoteReportAngularContextMenu queryNoteReportAngularContextMenu()
	{
		return annotationConfigApplicationContext.getBean(QueryNoteReportAngularContextMenu.class);
	}

	protected QueryNoteReportAngularEditDeleteResolveCommonPage queryNoteReportAngularEditDeleteResolveCommonPage()
	{
		return annotationConfigApplicationContext.getBean(QueryNoteReportAngularEditDeleteResolveCommonPage.class);
	}

	protected QueryNoteReportAngularEditPage queryNoteReportAngularEditPage()
	{
		return annotationConfigApplicationContext.getBean(QueryNoteReportAngularEditPage.class);
	}

	protected QueryNoteReportAngularResolvePage queryNoteReportAngularResolvePage()
	{
		return annotationConfigApplicationContext.getBean(QueryNoteReportAngularResolvePage.class);
	}

	protected QueryNoteReportAngularDeletePage queryNoteReportAngularDeletePage()
	{
		return annotationConfigApplicationContext.getBean(QueryNoteReportAngularDeletePage.class);
	}

	protected QueryNoteReportAngularExportPage queryNoteReportAngularExportPage()
	{
		return annotationConfigApplicationContext.getBean(QueryNoteReportAngularExportPage.class);
	}

	protected QueryNoteReportAngularEditActionDatesPage queryNoteReportAngularEditActionDatesPage()
	{
		return annotationConfigApplicationContext.getBean(QueryNoteReportAngularEditActionDatesPage.class);
	}

	protected HierarchySearchPage hierarchySearchPage()
	{
		return annotationConfigApplicationContext.getBean(HierarchySearchPage.class);
	}

	protected ContentSetPublishingConfigurationPage contentSetPublishingConfigurationPage()
	{
		return annotationConfigApplicationContext.getBean(ContentSetPublishingConfigurationPage.class);
	}

	protected SecurityPage userSecuritySettingsPage()
	{
		return annotationConfigApplicationContext.getBean(SecurityPage.class);
	}

	protected FooterToolsPage sourceNavigateFooterToolsPage()
	{
		return annotationConfigApplicationContext.getBean(FooterToolsPage.class);
	}

	protected SourceNavigateContextMenu sourceNavigateContextMenu()
	{
		return annotationConfigApplicationContext.getBean(SourceNavigateContextMenu.class);
	}

	protected ViewBaselinesContextMenu viewBaselinesContextMenu()
	{
		return annotationConfigApplicationContext.getBean(ViewBaselinesContextMenu.class);
	}

	protected RenditionContextMenu renditionContextMenu()
	{
		return annotationConfigApplicationContext.getBean(RenditionContextMenu.class);
	}

	protected SectionGroupingPage sectionGroupingPage()
	{
		return  annotationConfigApplicationContext.getBean(SectionGroupingPage.class);
	}

	protected DeltaGroupingPage deltaGroupingPage()
	{
		return  annotationConfigApplicationContext.getBean(DeltaGroupingPage.class);
	}

	protected SpellcheckReportModal spellcheckReportModal()
	{
		return annotationConfigApplicationContext.getBean(SpellcheckReportModal.class);
	}

	protected WorkflowPropertiesPage workflowPropertiesPage()
	{
		return annotationConfigApplicationContext.getBean(WorkflowPropertiesPage.class);
	}

	protected WorkflowDetailsPage workflowDetailsPage()
	{
		return annotationConfigApplicationContext.getBean(WorkflowDetailsPage.class);
	}

	protected SourceNavigateTabsPage sourceNavigateTabsPage()
	{
		return annotationConfigApplicationContext.getBean(SourceNavigateTabsPage.class);
	}

	protected SourceNavigateAngularTabsPage sourceNavigateAngularTabsPage()
	{
		return annotationConfigApplicationContext.getBean(SourceNavigateAngularTabsPage.class);
	}

	protected FiltersAndSortsPage sourceNavigateFiltersAndSortsPage()
	{
		return annotationConfigApplicationContext.getBean(FiltersAndSortsPage.class);
	}

	protected DeltaEffectiveDatePage deltaEffectiveDatePage()
	{
		return annotationConfigApplicationContext.getBean(DeltaEffectiveDatePage.class);
	}

	protected SyncPage syncPage()
	{
		return annotationConfigApplicationContext.getBean(SyncPage.class);
	}

	protected UpdateYearOrSessionPage updateYearOrSessionPage()
	{
		return annotationConfigApplicationContext.getBean(UpdateYearOrSessionPage.class);
	}

	protected StageCheckReportPage stageCheckReportPage()
	{
		return annotationConfigApplicationContext.getBean(StageCheckReportPage.class);
	}

	protected MismatchedReportGridPage mismatchedReportGridPage()
	{
		return annotationConfigApplicationContext.getBean(MismatchedReportGridPage.class);
	}

	protected MismatchedReportFiltersPage mismatchedReportFiltersPage()
	{
		return annotationConfigApplicationContext.getBean(MismatchedReportFiltersPage.class);
	}

	protected MismatchedReportFooterPage mismatchedReportFooterPage()
	{
		return annotationConfigApplicationContext.getBean(MismatchedReportFooterPage.class);
	}

	protected SectionGroupingFiltersAndSortsPage sectionGroupingFiltersAndSortsPage()
	{
		return annotationConfigApplicationContext.getBean(SectionGroupingFiltersAndSortsPage.class);
	}

	protected SectionGroupingGroupGridPage sectionGroupingGroupGridPage()
	{
		return annotationConfigApplicationContext.getBean(SectionGroupingGroupGridPage.class);
	}

	protected SectionGroupingFooterPage sectionGroupingFooterPage()
	{
		return annotationConfigApplicationContext.getBean(SectionGroupingFooterPage.class);
	}

	protected SectionGroupingNewGroupPage sectionGroupingNewGroupPage()
	{
		return annotationConfigApplicationContext.getBean(SectionGroupingNewGroupPage.class);
	}

	protected SectionGroupingSectionGridPage sectionGroupingSectionGridPage()
	{
		return annotationConfigApplicationContext.getBean(SectionGroupingSectionGridPage.class);
	}

	protected SectionGroupingToolsPage sectionGroupingToolsPage()
	{
		return annotationConfigApplicationContext.getBean(SectionGroupingToolsPage.class);
	}

	protected DeltaGroupingFiltersAndSortsPage deltaGroupingFiltersAndSortsPage()
	{
		return annotationConfigApplicationContext.getBean(DeltaGroupingFiltersAndSortsPage.class);
	}

	protected DeltaGroupingGroupGridPage deltaGroupingGroupGridPage()
	{
		return annotationConfigApplicationContext.getBean(DeltaGroupingGroupGridPage.class);
	}

	protected DeltaGroupingNewGroupPage deltaGroupingNewGroupPage()
	{
		return annotationConfigApplicationContext.getBean(DeltaGroupingNewGroupPage.class);
	}

	protected DeltaGroupingDeltaGridPage deltaGroupingDeltaGridPage()
	{
		return annotationConfigApplicationContext.getBean(DeltaGroupingDeltaGridPage.class);
	}

	protected DeltaGroupingToolsPage deltaGroupingToolsPage()
	{
		return annotationConfigApplicationContext.getBean(DeltaGroupingToolsPage.class);
	}

	protected YourWorkflowHasBeenCreatedPage yourWorkflowHasBeenCreatedPage()
	{
		return annotationConfigApplicationContext.getBean(YourWorkflowHasBeenCreatedPage.class);
	}

	protected SourceNavigateGridPage sourceNavigateGridPage()
	{
		return annotationConfigApplicationContext.getBean(SourceNavigateGridPage.class);
	}

	protected TopicalHeadingHighlightPage topicalHeadingHighlightPage()
	{
		return annotationConfigApplicationContext.getBean(TopicalHeadingHighlightPage.class);
	}

	protected UserPhrasePage userPhrasePage()
	{
		return annotationConfigApplicationContext.getBean(UserPhrasePage.class);
	}

	protected MarkIndexEntryPage markIndexEntryPage()
	{
		return annotationConfigApplicationContext.getBean(MarkIndexEntryPage.class);
	}

	protected ClassNumberWizardPage classNumberWizardPage()
	{
		return annotationConfigApplicationContext.getBean(ClassNumberWizardPage.class);
	}

	protected ApprovalDateWizardPage approvalDateWizardPage()
	{
		return annotationConfigApplicationContext.getBean(ApprovalDateWizardPage.class);
	}

	protected WestIdWizardPage westIdWizardPage()
	{
		return annotationConfigApplicationContext.getBean(WestIdWizardPage.class);
	}

	protected RenditionPropertiesPage renditionPropertiesPage()
	{
		return annotationConfigApplicationContext.getBean(RenditionPropertiesPage.class);
	}

	protected CreatePreparationDocumentPage createPreparationDocumentPage()
	{
		return annotationConfigApplicationContext.getBean(CreatePreparationDocumentPage.class);
	}

	protected SendToVendorPage sendToVendorPage()
	{
		return annotationConfigApplicationContext.getBean(SendToVendorPage.class);
	}

	protected SharedDeltaReportPage sharedDeltaReportPage()
	{
		return annotationConfigApplicationContext.getBean(SharedDeltaReportPage.class);
	}

	protected IndexReportPage indexReportPage()
	{
		return annotationConfigApplicationContext.getBean(IndexReportPage.class);
	}

	protected IndexReportSortOrderPage indexReportSortOrderPage()
	{
		return annotationConfigApplicationContext.getBean(IndexReportSortOrderPage.class);
	}

	protected ReportPage reportPage()
	{
		return annotationConfigApplicationContext.getBean(ReportPage.class);
	}

	protected EffectiveDateMetricsReportPage effectiveDateMetricsReportPage()
	{
		return annotationConfigApplicationContext.getBean(EffectiveDateMetricsReportPage.class);
	}

	protected ReportCentralFiltersPage reportCentralFiltersPage() {
		return annotationConfigApplicationContext.getBean(ReportCentralFiltersPage.class);
	}

	protected ReportCentralGridPage reportCentralGridPage() {
		return annotationConfigApplicationContext.getBean(ReportCentralGridPage.class);
	}

	protected KeyCiteResearchReferencesPage keyCiteResearchReferencesPage()
	{
		return annotationConfigApplicationContext.getBean(KeyCiteResearchReferencesPage.class);
	}

	protected KeyCiteResearchReferencesFiltersPage keyCiteResearchReferencesFiltersPage()
	{
		return annotationConfigApplicationContext.getBean(KeyCiteResearchReferencesFiltersPage.class);
	}

	protected LineageContextMenu lineageContextMenu()
	{
		return annotationConfigApplicationContext.getBean(LineageContextMenu.class);
	}

	protected ValidationFlagsReportPopupPage validationFlagsReportPopupPage()
	{
		return annotationConfigApplicationContext.getBean(ValidationFlagsReportPopupPage.class);
	}

	protected MainHeaderPage mainHeaderPage()
	{
		return annotationConfigApplicationContext.getBean(MainHeaderPage.class);
	}

	protected com.thomsonreuters.codes.codesbench.quality.pages.publishing.toolbox.GridPage gridPage()
	{
		return annotationConfigApplicationContext.getBean(com.thomsonreuters.codes.codesbench.quality.pages.publishing.toolbox.GridPage.class);
	}

	protected GridHeaderPage gridHeaderPage()
	{
		return annotationConfigApplicationContext.getBean(GridHeaderPage.class);
	}

	protected GridHeaderFiltersPage gridHeaderFiltersPage()
	{
		return annotationConfigApplicationContext.getBean(GridHeaderFiltersPage.class);
	}

	protected GridPaginationPage gridPaginationPage()
	{
		return annotationConfigApplicationContext.getBean(GridPaginationPage.class);
	}

	protected ToolbarPage toolbarPage()
	{
		return annotationConfigApplicationContext.getBean(ToolbarPage.class);
	}

	protected VolumeSelectionPage volumeSelectionPage()
	{
		return annotationConfigApplicationContext.getBean(VolumeSelectionPage.class);
	}

	protected ActivityNavigationSharedContentSetPage activityNavigationLegalSharedContentSetPage()
	{
		return annotationConfigApplicationContext.getBean(ActivityNavigationSharedContentSetPage.class);
	}

	protected CreateNewBtsRecordPage createNewBtsRecordPage()
	{
		return annotationConfigApplicationContext.getBean(CreateNewBtsRecordPage.class);
	}

	protected FindRecordPage findRecordPage()
	{
		return annotationConfigApplicationContext.getBean(FindRecordPage.class);
	}

	protected BtsWebUiContextMenu btsWebUiContextMenu()
	{
		return annotationConfigApplicationContext.getBean(BtsWebUiContextMenu.class);
	}

	protected DeleteRecordsPage deleteRecordsPage()
	{
		return annotationConfigApplicationContext.getBean(DeleteRecordsPage.class);
	}

	protected RelatedRuleBooksPage relatedRuleBooksPage()
	{
		return annotationConfigApplicationContext.getBean(RelatedRuleBooksPage.class);
	}

	protected ParentageGraphPage parentageGraphPage()
	{
		return annotationConfigApplicationContext.getBean(ParentageGraphPage.class);
	}

	protected AuditByDocumentPage auditByDocumentPage()
	{
		return annotationConfigApplicationContext.getBean(AuditByDocumentPage.class);
	}

	protected DeltaContextMenu deltaContextMenu()
	{
		return annotationConfigApplicationContext.getBean(DeltaContextMenu.class);
	}

	protected ClamshellPage clamshellPage()
	{
		return annotationConfigApplicationContext.getBean(ClamshellPage.class);
	}

	protected ManualDataEntryPage manualDataEntryPage()
	{
		return annotationConfigApplicationContext.getBean(ManualDataEntryPage.class);
	}

	protected CleanPage cleanPage()
	{
		return annotationConfigApplicationContext.getBean(CleanPage.class);
	}

	protected ReleasedToWestlawPage releasedToWestlawPage()
	{
		return annotationConfigApplicationContext.getBean(ReleasedToWestlawPage.class);
	}

	protected RandomBillReportContentSelectPage randomBillReportContentSelectPage()
	{
		return annotationConfigApplicationContext.getBean(RandomBillReportContentSelectPage.class);
	}

	protected RandomBillReportGridFooterPage randomBillReportGridFooterPage()
	{
		return annotationConfigApplicationContext.getBean(RandomBillReportGridFooterPage.class);
	}

	protected HierarchySetLawTrackingPage hierarchySetLawTrackingPage(){return annotationConfigApplicationContext.getBean(HierarchySetLawTrackingPage.class);}

	protected CopyPage copyPage(){return annotationConfigApplicationContext.getBean(CopyPage.class);}

	protected MovePage movePage()
	{
		return annotationConfigApplicationContext.getBean(MovePage.class);
	}

	protected DeepCopyPage deepCopyPage(){return annotationConfigApplicationContext.getBean(DeepCopyPage.class);}

	protected ClassifyDescendantsWorkflowPage classifyDescendantsWorkflowPage(){return annotationConfigApplicationContext.getBean(ClassifyDescendantsWorkflowPage.class);}

	protected OnlineProductMaintenancePage onlineProductMaintenancePage()
	{
		return annotationConfigApplicationContext.getBean(OnlineProductMaintenancePage.class);
	}

	protected OnlineProductMaintenanceGridPage onlineProductMaintenanceGridPage()
	{
		return annotationConfigApplicationContext.getBean(OnlineProductMaintenanceGridPage.class);
	}

	protected OnlineProductMaintenanceContextMenu onlineProductMaintenanceContextMenu()
	{
		return annotationConfigApplicationContext.getBean(OnlineProductMaintenanceContextMenu.class);
	}

	protected CreateNewOnlineProductPage createNewOnlineProductPage()
	{
		return annotationConfigApplicationContext.getBean(CreateNewOnlineProductPage.class);
	}

	protected EditNamesAndAssignmentsPage editNamesAndAssignmentsPage()
	{
		return annotationConfigApplicationContext.getBean(EditNamesAndAssignmentsPage.class);
	}

	protected DeleteOnlineProductPage deleteOnlineProductPage()
	{
		return annotationConfigApplicationContext.getBean(DeleteOnlineProductPage.class);
	}

	protected PreviousWipVersionsContextMenu previousWipVersionsContextMenu()
	{
		return annotationConfigApplicationContext.getBean(PreviousWipVersionsContextMenu.class);
	}

	protected ViewAssignmentStatusPage viewAssignmentStatusPage()
	{
		return annotationConfigApplicationContext.getBean(ViewAssignmentStatusPage.class);
	}

	protected OnlineProductAssignmentsPage onlineProductAssignmentsPage()
	{
		return annotationConfigApplicationContext.getBean(OnlineProductAssignmentsPage.class);
	}

	protected TaxTypeAssignmentsPage taxTypeAssignmentsPage()
	{
		return annotationConfigApplicationContext.getBean(TaxTypeAssignmentsPage.class);
	}

	protected ManageOnlineProductAssignmentsPage manageOnlineProductAssignmentsPage()
	{
		return annotationConfigApplicationContext.getBean(ManageOnlineProductAssignmentsPage.class);
	}

	protected ManageTaxTypeAssignmentsPage manageTaxTypeAssignmentsPage()
	{
		return annotationConfigApplicationContext.getBean(ManageTaxTypeAssignmentsPage.class);
	}

	protected OnlineProductAssignmentsContextMenu onlineProductAssignmentsContextMenu()
	{
		return annotationConfigApplicationContext.getBean(OnlineProductAssignmentsContextMenu.class);
	}

	protected TaxTypeAssignmentsContextMenu taxTypeAssignmentsContextMenu()
	{
		return annotationConfigApplicationContext.getBean(TaxTypeAssignmentsContextMenu.class);
	}

	protected OnlineProductAssignmentReportPage onlineProductAssignmentReportPage()
	{
		return annotationConfigApplicationContext.getBean(OnlineProductAssignmentReportPage.class);
	}

	protected ViewSelectedNodesBinPage viewSelectedNodesBinPage()
	{
		return annotationConfigApplicationContext.getBean(ViewSelectedNodesBinPage.class);
	}

	protected DispositionDerivationPage dispositionDerivationPage()
	{
		return annotationConfigApplicationContext.getBean(DispositionDerivationPage.class);
	}

	protected DispositionDerivationContextMenu dispositionDerivationContextMenu()
	{
		return annotationConfigApplicationContext.getBean(DispositionDerivationContextMenu.class);
	}

	protected DelinkPreviousNodePage delinkPreviousNodePage()
	{
		return annotationConfigApplicationContext.getBean(DelinkPreviousNodePage.class);
	}

	protected TreeContextMenu treeContextMenu()
	{
		return annotationConfigApplicationContext.getBean(TreeContextMenu.class);
	}

	protected FindCitePage findCitePage()
	{
		return annotationConfigApplicationContext.getBean(FindCitePage.class);
	}

	protected HierarchyFindPage hierarchyFindPage()
	{
		return annotationConfigApplicationContext.getBean(HierarchyFindPage.class);
	}

	protected FindTemplatesPage findTemplatesPage()
	{
		return annotationConfigApplicationContext.getBean(FindTemplatesPage.class);
	}

	protected AddToRangePage addToRangePage()
	{
		return annotationConfigApplicationContext.getBean(AddToRangePage.class);
	}

	protected RemoveFromRangePage removeFromRangePage()
	{
		return annotationConfigApplicationContext.getBean(RemoveFromRangePage.class);
	}

	protected RepealPage repealPage()
	{
		return annotationConfigApplicationContext.getBean(RepealPage.class);
	}

	protected ReservePage reservePage()
	{
		return annotationConfigApplicationContext.getBean(ReservePage.class);
	}

	protected ReusePage reusePage()
	{
		return annotationConfigApplicationContext.getBean(ReusePage.class);
	}

	protected ChangeVolumeNumberDescendantsPage changeVolumeNumberDescendantsPage()
	{
		return annotationConfigApplicationContext.getBean(ChangeVolumeNumberDescendantsPage.class);
	}

	protected DeepClonePage deepClonePage()
	{
		return annotationConfigApplicationContext.getBean(DeepClonePage.class);
	}

	protected UpdateAlternativeCitePage updateAlternativeCitePage()
	{
		return annotationConfigApplicationContext.getBean(UpdateAlternativeCitePage.class);
	}

	protected AddAssignedScriptsPage addAssignedScriptsPage()
	{
		return annotationConfigApplicationContext.getBean(AddAssignedScriptsPage.class);
	}

	protected AddHighAssignedScriptsPage addHighAssignedScriptsPage()
	{
		return annotationConfigApplicationContext.getBean(AddHighAssignedScriptsPage.class);
	}

	protected RemoveAssignedScriptsPage removeAssignedScriptsPage()
	{
		return annotationConfigApplicationContext.getBean(RemoveAssignedScriptsPage.class);
	}

	protected ScriptListingReportPage scriptListingReportPage()
	{
		return annotationConfigApplicationContext.getBean(ScriptListingReportPage.class);
	}

	protected LineageTabViewClamshellPage lineageTabViewClamshellPage()
	{
		return annotationConfigApplicationContext.getBean(LineageTabViewClamshellPage.class);
	}

	protected LineageTabEditClamshellPage lineageTabEditClamshellPage()
	{
		return annotationConfigApplicationContext.getBean(LineageTabEditClamshellPage.class);
	}

	protected LineageTabModifyClamshellPage lineageTabModifyClamshellPage()
	{
		return annotationConfigApplicationContext.getBean(LineageTabModifyClamshellPage.class);
	}

	protected LineageTabCreateClamshellPage lineageTabCreateClamshellPage()
	{
		return annotationConfigApplicationContext.getBean(LineageTabCreateClamshellPage.class);
	}

	protected LineageTabValidateClamshellPage lineageTabValidateClamshellPage()
	{
		return annotationConfigApplicationContext.getBean(LineageTabValidateClamshellPage.class);
	}

	protected LineageTabTrackClamshellPage lineageTabTrackClamshellPage()
	{
		return annotationConfigApplicationContext.getBean(LineageTabTrackClamshellPage.class);
	}

	protected LineageTabReportsClamshellPage lineageTabReportClamshellPage()
	{
		return annotationConfigApplicationContext.getBean(LineageTabReportsClamshellPage.class);
	}

	protected LineageTabSyncClamshellPage lineageTabSyncClamshellPage()
	{
		return annotationConfigApplicationContext.getBean(LineageTabSyncClamshellPage.class);
	}

	protected DeltaTabViewClamshellPage deltaTabViewClamshellPage()
	{
		return annotationConfigApplicationContext.getBean(DeltaTabViewClamshellPage.class);
	}

	protected DeltaTabEditClamshellPage deltaTabEditClamshellPage()
	{
		return annotationConfigApplicationContext.getBean(DeltaTabEditClamshellPage.class);
	}

	protected DeltaTabModifyClamshellPage deltaTabModifyClamshellPage()
	{
		return annotationConfigApplicationContext.getBean(DeltaTabModifyClamshellPage.class);
	}

	protected DeltaTabTrackClamshellPage deltaTabTrackClamshellPage()
	{
		return annotationConfigApplicationContext.getBean(DeltaTabTrackClamshellPage.class);
	}

	protected DeltaTabReportsClamshellPage deltaTabReportClamshellPage()
	{
		return annotationConfigApplicationContext.getBean(DeltaTabReportsClamshellPage.class);
	}

	protected AbstractReportsClamshellPage abstractReportsClamshellPage()
	{
		return annotationConfigApplicationContext.getBean(AbstractReportsClamshellPage.class);
	}

	protected AbstractPropertiesPage abstractPropertiesPage()
	{
		return annotationConfigApplicationContext.getBean(AbstractPropertiesPage.class);
	}

	protected DifficultyLevelPage difficultyLevelPage()
	{
		return annotationConfigApplicationContext.getBean(DifficultyLevelPage.class);
	}

	protected DocumentLockedPage documentLockedPage()
	{
		return annotationConfigApplicationContext.getBean(DocumentLockedPage.class);
	}

	protected RenditionTabViewClamshellPage renditionTabViewClamshellPage()
	{
		return annotationConfigApplicationContext.getBean(RenditionTabViewClamshellPage.class);
	}

	protected RenditionTabEditClamshellPage renditionTabEditClamshellPage()
	{
		return annotationConfigApplicationContext.getBean(RenditionTabEditClamshellPage.class);
	}

	protected RenditionTabModifyClamshellPage renditionTabModifyClamshellPage()
	{
		return annotationConfigApplicationContext.getBean(RenditionTabModifyClamshellPage.class);
	}

	protected RenditionTabCreateClamshellPage renditionTabCreateClamshellPage()
	{
		return annotationConfigApplicationContext.getBean(RenditionTabCreateClamshellPage.class);
	}

	protected RenditionTabValidateClamshellPage renditionTabValidateClamshellPage()
	{
		return annotationConfigApplicationContext.getBean(RenditionTabValidateClamshellPage.class);
	}

	protected RenditionTabTrackClamshellPage renditionTabTrackClamshellPage()
	{
		return annotationConfigApplicationContext.getBean(RenditionTabTrackClamshellPage.class);
	}

	protected RenditionTabReportsClamshellPage renditionTabReportClamshellPage()
	{
		return annotationConfigApplicationContext.getBean(RenditionTabReportsClamshellPage.class);
	}

	protected RenditionTabSyncClamshellPage renditionTabSyncClamshellPage()
	{
		return annotationConfigApplicationContext.getBean(RenditionTabSyncClamshellPage.class);
	}

	protected SectionTabViewClamshellPage sectionTabViewClamshellPage()
	{
		return annotationConfigApplicationContext.getBean(SectionTabViewClamshellPage.class);
	}

	protected SectionTabEditClamshellPage sectionTabEditClamshellPage()
	{
		return annotationConfigApplicationContext.getBean(SectionTabEditClamshellPage.class);
	}

	protected SectionTabModifyClamshellPage sectionTabModifyClamshellPage()
	{
		return annotationConfigApplicationContext.getBean(SectionTabModifyClamshellPage.class);
	}

	protected SectionTabTrackClamshellPage sectionTabTrackClamshellPage()
	{
		return annotationConfigApplicationContext.getBean(SectionTabTrackClamshellPage.class);
	}

	protected SectionTabReportsClamshellPage sectionTabReportClamshellPage()
	{
		return annotationConfigApplicationContext.getBean(SectionTabReportsClamshellPage.class);
	}

	protected InsertTargetCiteReferencePage insertTargetCiteReferencePage()
	{
		return annotationConfigApplicationContext.getBean(InsertTargetCiteReferencePage.class);
	}

	protected TargetLocatorPage targetLocatorPage()
	{
		return annotationConfigApplicationContext.getBean(TargetLocatorPage.class);
	}

	protected TargetLocatorContextMenu targetLocatorContextMenu()
	{
		return annotationConfigApplicationContext.getBean(TargetLocatorContextMenu.class);
	}

	protected ApplyLinkMarkupPage applyLinkMarkupPage()
	{
		return annotationConfigApplicationContext.getBean(ApplyLinkMarkupPage.class);
	}

	protected BrowseLinkMarkupPage browseLinkMarkupPage()
	{
		return annotationConfigApplicationContext.getBean(BrowseLinkMarkupPage.class);
	}

	protected UpdateMetadataPage updateMetadataPage()
	{
		return annotationConfigApplicationContext.getBean(UpdateMetadataPage.class);
	}

	protected ListOfC2012RenditionsPage listOfC2012RenditionsPage()
	{
		return annotationConfigApplicationContext.getBean(ListOfC2012RenditionsPage.class);
	}

	protected CreateWipVersionPage createWipVersionPage()
	{
		return annotationConfigApplicationContext.getBean(CreateWipVersionPage.class);
	}

	protected EditorQueryContextMenu editorQueryContextMenu()
	{
		return annotationConfigApplicationContext.getBean(EditorQueryContextMenu.class);
	}

	protected TaxTypeAddPage taxTypeAddPage()
	{
		return annotationConfigApplicationContext.getBean(TaxTypeAddPage.class);
	}

	protected EditTaxTypesPage editTaxTypesPage()
	{
		return annotationConfigApplicationContext.getBean(EditTaxTypesPage.class);
	}

	protected DeleteWithPromoteChildrenPage deleteWithPromoteChildrenPage()
	{
		return annotationConfigApplicationContext.getBean(DeleteWithPromoteChildrenPage.class);
	}

	protected LockedNodesReportPage lockedNodesReportPage()
	{
		return annotationConfigApplicationContext.getBean(LockedNodesReportPage.class);
	}

	protected LockedNodesReportContextMenu lockedNodesReportContextMenu()
	{
		return annotationConfigApplicationContext.getBean(LockedNodesReportContextMenu.class);
	}

	protected LockReportPage lockReportPage()
	{
		return annotationConfigApplicationContext.getBean(LockReportPage.class);
	}

	protected LockReportContextMenu lockReportContextMenu()
	{
		return annotationConfigApplicationContext.getBean(LockReportContextMenu.class);
	}

	protected ParentageGraphContextMenu parentageGraphContextMenu()
	{
		return annotationConfigApplicationContext.getBean(ParentageGraphContextMenu.class);
	}

	protected HierarchyPreviewDocumentPage hierarchyPreviewDocumentPage()
	{
		return annotationConfigApplicationContext.getBean(HierarchyPreviewDocumentPage.class);
	}

	protected ReorderChildrenPage reorderChildrenPage()
	{
		return annotationConfigApplicationContext.getBean(ReorderChildrenPage.class);
	}

	protected InputAsChildPage inputAsChildPage()
	{
		return annotationConfigApplicationContext.getBean(InputAsChildPage.class);
	}

	protected VolumeInformationPage volumeInformationPage()
	{
		return annotationConfigApplicationContext.getBean(VolumeInformationPage.class);
	}

	protected ViewMetadataPage viewMetadataPage()
	{
		return annotationConfigApplicationContext.getBean(ViewMetadataPage.class);
	}

	protected InitiateNodReportPage initiateNodReportPage()
	{
		return annotationConfigApplicationContext.getBean(InitiateNodReportPage.class);
	}

	protected InstructionNotesPage instructionsNotesPage()
	{
		return annotationConfigApplicationContext.getBean(InstructionNotesPage.class);
	}

	protected DeltaPropertiesPage deltaPropertiesPage()
	{
		return annotationConfigApplicationContext.getBean(DeltaPropertiesPage.class);
	}

	protected SectionEffectiveDatePage sectionEffectiveDatePage()
	{
		return annotationConfigApplicationContext.getBean(SectionEffectiveDatePage.class);
	}

	protected IntegrationPropertiesPage integrationPropertiesPage()
	{
		return annotationConfigApplicationContext.getBean(IntegrationPropertiesPage.class);
	}

	protected IntegrationResultsPage integrationResultsPage()
	{
		return annotationConfigApplicationContext.getBean(IntegrationResultsPage.class);
	}

	protected ChangeEndDateDescendantsPage changeEndDateDescendantsPage() { return annotationConfigApplicationContext.getBean(ChangeEndDateDescendantsPage.class); }

	protected AuditCorrectionsPage auditCorrectionsPage()
	{
		return annotationConfigApplicationContext.getBean(AuditCorrectionsPage.class);
	}

	protected SectionPropertiesPage sectionPropertiesPage() {
		return annotationConfigApplicationContext.getBean(SectionPropertiesPage.class);
	}

	protected RunPubtagRefreshByVolsPage runPubtagRefreshByVolsPage()
	{
		return annotationConfigApplicationContext.getBean(RunPubtagRefreshByVolsPage.class);
	}

	protected AddAssignedScriptsContextMenu addAssignedScriptsContextMenu()
	{
		return annotationConfigApplicationContext.getBean(AddAssignedScriptsContextMenu.class);
	}

	protected AddHightAssignedScriptsContextMenu addHightAssignedScriptsContextMenu()
	{
		return annotationConfigApplicationContext.getBean(AddHightAssignedScriptsContextMenu.class);
	}

	protected RemoveAssignedScriptsContextMenu removeAssignedScriptsContextMenu()
	{
		return annotationConfigApplicationContext.getBean(RemoveAssignedScriptsContextMenu.class);
	}

	protected EnterDateCriteriaForGridFilterPage enterDateCriteriaForGridFilterPage()
	{
		return annotationConfigApplicationContext.getBean(EnterDateCriteriaForGridFilterPage.class);
	}

	protected ScriptMaintenanceClamshellPage scriptMaintenanceClamshellPage()
	{
		return annotationConfigApplicationContext.getBean(ScriptMaintenanceClamshellPage.class);
	}

	protected CreateScriptPage createScriptPage()
	{
		return annotationConfigApplicationContext.getBean(CreateScriptPage.class);
	}

	protected ScriptMaintenanceGridPage scriptMaintenanceGridPage()
	{
		return annotationConfigApplicationContext.getBean(ScriptMaintenanceGridPage.class);
	}

	protected EditScriptRulesPage editScriptRulesPage()
	{
		return annotationConfigApplicationContext.getBean(EditScriptRulesPage.class);
	}

	protected EditScriptRulesConfirmationPage editScriptRulesConfirmationPage()
	{
		return annotationConfigApplicationContext.getBean(EditScriptRulesConfirmationPage.class);
	}

	protected CreateScriptVersionPage createScriptVersionPage()
	{
		return annotationConfigApplicationContext.getBean(CreateScriptVersionPage.class);
	}

	protected CreateScriptVersion998Page createScriptVersion998Page()
	{
		return annotationConfigApplicationContext.getBean(CreateScriptVersion998Page.class);
	}

	protected ViewDescriptionContentSetsPage viewDescriptionContentSetsPage()
	{
		return annotationConfigApplicationContext.getBean(ViewDescriptionContentSetsPage.class);
	}

	protected ViewPrintScriptRulesPage viewPrintScriptRulesPage()
	{
		return annotationConfigApplicationContext.getBean(ViewPrintScriptRulesPage.class);
	}

	protected ViewPrintListOfScriptPage viewPrintListOfScriptPage()
	{
		return annotationConfigApplicationContext.getBean(ViewPrintListOfScriptPage.class);
	}

	protected DeleteScriptConfirmationPage deleteScriptConfirmationPage()
	{
		return annotationConfigApplicationContext.getBean(DeleteScriptConfirmationPage.class);
	}

	protected CopyScriptPage copyScriptPage()
	{
		return annotationConfigApplicationContext.getBean(CopyScriptPage.class);
	}

	protected XmlExtractPage xmlExtractSourcePage()
	{
		return annotationConfigApplicationContext.getBean(XmlExtractPage.class);
	}

	protected xmlExtractHierarchyPage xmlExtractHierarchyPage()
	{
		return annotationConfigApplicationContext.getBean(xmlExtractHierarchyPage.class);
	}

	protected XmlExtractStateFeedPage xmlExtractStateFeedPage()
	{
		return annotationConfigApplicationContext.getBean(XmlExtractStateFeedPage.class);
	}

	protected CopyScriptForVersionPage copyScriptForVersionPage()
	{
		return annotationConfigApplicationContext.getBean(CopyScriptForVersionPage.class);
	}

	protected ScriptMaintenanceMetadataPage scriptMaintenanceMetadataPage()
	{
		return annotationConfigApplicationContext.getBean(ScriptMaintenanceMetadataPage.class);
	}

	protected ScriptMaintenanceErrorPage scriptMaintenanceErrorPage()
	{
		return annotationConfigApplicationContext.getBean(ScriptMaintenanceErrorPage.class);
	}

	protected ScriptMaintenanceContextMenu scriptMaintenanceContextMenu()
	{
		return annotationConfigApplicationContext.getBean(ScriptMaintenanceContextMenu.class);
	}

	protected UpdateCodeNameIDPage updateCodeNameIDPage(){return annotationConfigApplicationContext.getBean(UpdateCodeNameIDPage.class);}

	protected StageCheckReportsPage stageCheckReportsPage()
	{
		return annotationConfigApplicationContext.getBean(StageCheckReportsPage.class);
	}

	protected ValidationReportPage validationReportPage()
	{
		return annotationConfigApplicationContext.getBean(ValidationReportPage.class);
	}

	protected TabularWLPreviewPage tabularWLPreviewPage()
	{
		return annotationConfigApplicationContext.getBean(TabularWLPreviewPage.class);
	}

	protected TabularPrintPreviewPage tabularPrintPreviewPage()
	{
		return annotationConfigApplicationContext.getBean(TabularPrintPreviewPage.class);
	}

	protected PrintPreviewPage printPreviewPage()
	{
		return annotationConfigApplicationContext.getBean(PrintPreviewPage.class);
	}

	protected PublicationsPage publicationsPage()
	{
		return annotationConfigApplicationContext.getBean(PublicationsPage.class);
	}

	protected PublicationsTablePage publicationsTablePage()
	{
		return annotationConfigApplicationContext.getBean(PublicationsTablePage.class);
	}

	protected PublicationFilesPage publicationFilesPage()
	{
		return annotationConfigApplicationContext.getBean(PublicationFilesPage.class);
	}

	protected PublicationFilesTablePage publicationFilesTablePage()
	{
		return annotationConfigApplicationContext.getBean(PublicationFilesTablePage.class);
	}

	protected PublicationsContextMenu publicationsContextMenu()
	{
		return annotationConfigApplicationContext.getBean(PublicationsContextMenu.class);
	}

	protected TableEditorPage tableEditorPage()
	{
		return annotationConfigApplicationContext.getBean(TableEditorPage.class);
	}

	protected EditQueryNotePage editQueryNotePage()
	{
		return annotationConfigApplicationContext.getBean(EditQueryNotePage.class);
	}

	protected PublicationFilesContextMenu publicationFilesContextMenu()
	{
		return annotationConfigApplicationContext.getBean(PublicationFilesContextMenu.class);
	}
	protected UpdateMetadataMultiplePage updateMetadataMultiplePage() { return annotationConfigApplicationContext.getBean(UpdateMetadataMultiplePage.class); }

	protected  StatPageReorderPage statPageReorderPage()
	{
		return annotationConfigApplicationContext.getBean(StatPageReorderPage.class);
	}

	protected  CreateNewVolumePage createNewVolumePage()
	{
		return annotationConfigApplicationContext.getBean(CreateNewVolumePage.class);
	}

	protected NormalizedCiteReferencesPageElements normalizedCiteReferencesPageElements()
	{
		return annotationConfigApplicationContext.getBean(NormalizedCiteReferencesPageElements.class);
	}

	protected CiteLineManagementPage citeLineManagementPage()
	{
		return annotationConfigApplicationContext.getBean(CiteLineManagementPage.class);
	}

	protected AddNewNormalizedCitePage addNewNormalizedCitePage()
	{
		return annotationConfigApplicationContext.getBean(AddNewNormalizedCitePage.class);
	}

	protected NormalizedCitePage normalizedCitePage()
	{
		return annotationConfigApplicationContext.getBean(NormalizedCitePage.class);
	}

	protected CiteLineReferencesPage citeLineReferencesPage()
	{
		return annotationConfigApplicationContext.getBean(CiteLineReferencesPage.class);
	}

	protected AddNewCiteLineReferencesPage addNewCiteLineReferencesPage()
	{
		return annotationConfigApplicationContext.getBean(AddNewCiteLineReferencesPage.class);
	}

	protected CustomPubExtractManagementPage customPubExtractManagementPage()
	{
		return annotationConfigApplicationContext.getBean(CustomPubExtractManagementPage.class);
	}

	protected CustomPubExtractOptionsPage customPubExtractOptionsPage()
	{
		return annotationConfigApplicationContext.getBean(CustomPubExtractOptionsPage.class);
	}
	protected CustomPubExtractTreeViewPage customPubExtractTreeViewPage()
	{
		return annotationConfigApplicationContext.getBean(CustomPubExtractTreeViewPage.class);
	}

	protected CustomPubExtractSavePage customPubExtractSavePage()
	{
		return annotationConfigApplicationContext.getBean(CustomPubExtractSavePage.class);
	}

	protected AddOrEditNormalizedCitePage addOrEditNormalizedCitePage()
	{
		return annotationConfigApplicationContext.getBean(AddOrEditNormalizedCitePage.class);
	}

	protected EditNormalizedCitePage editNormalizedCitePage()
	{
		return annotationConfigApplicationContext.getBean(EditNormalizedCitePage.class);
	}

	protected HierarchyDateSearchMultipleDocumentsPage hierarchyDateSearchMultipleDocumentsPage()
	{
		return annotationConfigApplicationContext.getBean(HierarchyDateSearchMultipleDocumentsPage.class);
	}

	protected StateFeedBasePage stateFeedPage()
	{
		return annotationConfigApplicationContext.getBean(StateFeedBasePage.class);
	}

	protected ExtractedDataPage extractedDataPage()
	{
		return annotationConfigApplicationContext.getBean(ExtractedDataPage.class);
	}

	protected FileManagementPage fileManagementPage()
	{
		return annotationConfigApplicationContext.getBean(FileManagementPage.class);
	}

	protected SourceNavigateAngularPage sourceNavigateAngularPage()
	{
		return annotationConfigApplicationContext.getBean(SourceNavigateAngularPage.class);
	}

	protected SourceNavigateAngularLeftSidePanePage sourceNavigateAngularLeftSidePanePage()
	{
		return annotationConfigApplicationContext.getBean(SourceNavigateAngularLeftSidePanePage.class);
	}

	protected SourceNavigateAngularViewManagerPage sourceNavigateAngularViewManagerPage()
	{
		return annotationConfigApplicationContext.getBean(SourceNavigateAngularViewManagerPage.class);
	}

	protected SourceNavigateAngularFiltersAndSortsPage sourceNavigateAngularFiltersAndSortsPage()
	{
		return annotationConfigApplicationContext.getBean(SourceNavigateAngularFiltersAndSortsPage.class);
	}

	protected SourceNavigateAngularPopUpPage sourceNavigateAngularPopUpPage()
	{
		return annotationConfigApplicationContext.getBean(SourceNavigateAngularPopUpPage.class);
	}

	protected SourceNavigateAngularToastPage sourceNavigateAngularToastPage()
	{
		return annotationConfigApplicationContext.getBean(SourceNavigateAngularToastPage.class);
	}

	protected IndexingPage indexingPage()
	{
		return annotationConfigApplicationContext.getBean(IndexingPage.class);
	}

	protected IndexingSessionLawBoxPage indexingSessionLawBoxPage()
	{
		return annotationConfigApplicationContext.getBean(IndexingSessionLawBoxPage.class);
	}

	protected IndexingFragmentsPage indexingFragmentsPage()
	{
		return annotationConfigApplicationContext.getBean(IndexingFragmentsPage.class);
	}

	protected IndexingSuggestedIndexEntriesPage indexingSuggestedIndexEntriesPage()
	{
		return annotationConfigApplicationContext.getBean(IndexingSuggestedIndexEntriesPage.class);
	}

	protected IndexingDraftIndexEntryPage indexingDraftIndexEntryPage()
	{
		return annotationConfigApplicationContext.getBean(IndexingDraftIndexEntryPage.class);
	}

	protected IndexingFinalIndexEntriesPage indexingFinalIndexEntriesPage()
	{
		return annotationConfigApplicationContext.getBean(IndexingFinalIndexEntriesPage.class);
	}

	protected CiteLineManagementContextMenu citeLineManagementContextMenu()
	{
		return annotationConfigApplicationContext.getBean(CiteLineManagementContextMenu.class);
	}

	protected DeleteCitelineReferencePage deleteCitelineReferencesPage()
	{
		return annotationConfigApplicationContext.getBean(DeleteCitelineReferencePage.class);
	}

	protected EditCiteLineReferencesPage editCiteLineReferencesPage()
	{
		return annotationConfigApplicationContext.getBean(EditCiteLineReferencesPage.class);
	}

	protected NortExtractorPage nortExtractorPage()
	{
		return annotationConfigApplicationContext.getBean(NortExtractorPage.class);
	}

	protected FileManagementContextMenu fileManagementContextMenu()
	{
		return annotationConfigApplicationContext.getBean(FileManagementContextMenu.class);
	}

	protected SourceNavigateAngularDeltaGroupPage sourceNavigateAngularDeltaGroupPage()
	{
		return annotationConfigApplicationContext.getBean(SourceNavigateAngularDeltaGroupPage.class);
	}

	protected SourceNavigateAngularDeltaPage sourceNavigateAngularDeltaPage()
	{
		return annotationConfigApplicationContext.getBean(SourceNavigateAngularDeltaPage.class);
	}

	protected SourceNavigateAngularLineagePage sourceNavigateAngularLineagePage()
	{
		return annotationConfigApplicationContext.getBean(SourceNavigateAngularLineagePage.class);
	}

	protected SourceNavigateAngularRenditionPage sourceNavigateAngularRenditionPage()
	{
		return annotationConfigApplicationContext.getBean(SourceNavigateAngularRenditionPage.class);
	}

	protected SourceNavigateAngularSectionGroupPage sourceNavigateAngularSectionGroupPage()
	{
		return annotationConfigApplicationContext.getBean(SourceNavigateAngularSectionGroupPage.class);
	}

	protected SourceNavigateAngularSectionPage sourceNavigateAngularSectionPage()
	{
		return annotationConfigApplicationContext.getBean(SourceNavigateAngularSectionPage.class);
	}

	protected SourceNavigateAngularLockReportPage sourceNavigateAngularLockReportPage()
	{
		return annotationConfigApplicationContext.getBean(SourceNavigateAngularLockReportPage.class);
	}

	/*
	 * users
	 */
	protected User user()
	{
		switch (userTag)
		{
			case "risk":
				return annotationConfigApplicationContext.getBean(RiskUser.class);
			case "legal":
				return annotationConfigApplicationContext.getBean(LegalUser.class);
			case "carswell":
				return annotationConfigApplicationContext.getBean(CarswellUser.class);
			case "citeline":
				return annotationConfigApplicationContext.getBean(CitelineUser.class);
			case "prodlegal":
				return annotationConfigApplicationContext.getBean(ProdLegalUser.class);
			default:
				return null;
		}
	}

	protected User specificUser(String user)
	{
		switch (user)
		{
			case "risk":
				return annotationConfigApplicationContext.getBean(RiskUser.class);
			case "legal":
				return annotationConfigApplicationContext.getBean(LegalUser.class);
			case "carswell":
				return annotationConfigApplicationContext.getBean(CarswellUser.class);
			case "citeline":
				return annotationConfigApplicationContext.getBean(CitelineUser.class);
			case "prodlegal":
				return annotationConfigApplicationContext.getBean(ProdLegalUser.class);
			default:
				return null;
		}
	}

	protected RiskUser riskUser()
	{
		return annotationConfigApplicationContext.getBean(RiskUser.class);
	}

	protected LegalUser legalUser()
	{
		return annotationConfigApplicationContext.getBean(LegalUser.class);
	}

	protected CarswellUser carswellUser()
	{
		return annotationConfigApplicationContext.getBean(CarswellUser.class);
	}

	protected CitelineUser citelineUser() { return annotationConfigApplicationContext.getBean(CitelineUser.class); }

	protected ProdLegalUser prodLegalUser()
	{
		return annotationConfigApplicationContext.getBean(ProdLegalUser.class);
	}

	protected String pingIdUnlockPIN()
	{
		return annotationConfigApplicationContext.getBean(PingIdUnlockPIN.class).getPingIdUnlockPIN();
	}



	/*
	 * urls
	 */
	protected Urls urls()
	{
		return annotationConfigApplicationContext.getBean(Urls.class);
	}
}