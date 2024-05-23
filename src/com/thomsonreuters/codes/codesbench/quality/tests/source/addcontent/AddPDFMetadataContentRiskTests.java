package com.thomsonreuters.codes.codesbench.quality.tests.source.addcontent;

import com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.popups.AddShellPageElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.RISK;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class AddPDFMetadataContentRiskTests extends TestService
{
    /**
     * STORY: JETS-6885, JETS-9055, JETS-12507, JETS-9467, JETS-8654, JETS-12506<br>
     * SUMMARY: Verifies all contentTypes in the add pdf metadata page<br>
     * USER:  RISK<br>
     */
    @Test
	@IE_EDGE_MODE
    @RISK
    @LOG
    public void addPDFMetadataContentTest()
    {
        final List<String> contentTypes = Arrays.asList("",
                "Enacted Legislation", "Pending Legislation", "EU Directives and Regulations", "Guidance and Interpretation",
                "Discussion and Consultation Documents", "Comments and Feedback", "SRO / Exchange Materials",
                "Policy Documents", "Public Notices", "Announcements and News Releases", "Speeches",
                "Enforcements", "International Sanctions", "Pending Regulations");

        List<String> orgValues = Arrays.asList("Australian Prudential Regulation Authority", "Australian Securities and Investments Commission", "Australian Transactions Reports and Analysis Centre (AUSTRAC)",
                "Autorité des marchés financiers (AMF)", "BaFin (German Federal Financial Supervisory Authority)", "Bahrain Stock Exchange", "Bats BYX Exchange",
                "Bats BZX Exchange", "Bats EDGA Exchange", "Bats EDGX Exchange", "BOX Options Exchange (BOX)", "CBOE Futures Exchange (CFE)", "Central Bank of Bahrain",
                "Chicago Board Options Exchange (CBOE)", "Commission de Surveillance du Secteur Financier (Luxembourg) (CSSF)", "Council of the European Union",
                "Department for Business Innovation & Skills", "Dubai Financial Services Authority (DFSA)", "Dubai Gold and Commodities Exchange",
                "Dubai International Financial Centre Courts", "Dubai Mercantile Exchange (DME)", "Emirates Securities and Commodities Authority (ESCA)",
                "European Banking Authority", "European Commission", "European Insurance and Occupational Pensions Authority", "European Parliament",
                "European Securities and Markets Authority", "European Systemic Risk Board", "Federal Authorities of the Swiss Confederation", "Federal Deposit Insurance Corporation",
                "Federal Financial Institutions Examination Council", "Federal Reserve", "Financial Conduct Authority", "Financial Industry Regulatory Authority",
                "Financial Market Authority (Finanzmarktaufsicht)", "Financial Reporting Council", "German Ministry of Finance", "Government departments/ministries",
                "Guernsey Financial Services Commission", "HM Revenue & Customs", "HM Treasury", "Home Office", "Hong Kong Exchange", "Hong Kong Monetary Authority",
                "Hong Kong Office of the Commissioner of Insurance", "Hong Kong Securities and Futures Commission", "House of Commons", "House of Commons Treasury Committee",
                "ICE Futures Europe", "ICE US", "Independent Commission on Banking", "International Securities Exchange (ISE)", "Investment Industry Regulatory Organization of Canada (IIROC)",
                "Isle of Man Financial Services Authority", "Jersey Financial Services Commission", "Joint Money Laundering Steering Group", "Legislative Council of Hong Kong",
                "Lloyd's", "London Metal Exchange", "London Stock Exchange", "MarketAxess SEF Corporation", "Ministry of Finance (Liechtenstein)",
                "Ministry of Finance (Luxembourg)", "Ministry of Justice", "Monetary Authority of Singapore", "Municipal Securities Rulemaking Board (MSRB)",
                "Mutual Fund Dealers Association of Canada (MFDA)", "NASDAQ Dubai (formerly DIFX)", "NASDAQ OMX BX", "NASDAQ Stock Market LLC", "National Futures Association (NFA)",
                "New York Stock Exchange (NYSE)", "NYSE Arca", "NYSE MKT", "Office of Fair Trading", "Office of the Comptroller of the Currency", "Office of Thrift Supervision",
                "OneChicago Exchange (OC)", "Ontario Securities Commission", "Options Clearing Corporation (OCC)", "Parliament of Australia", "Prudential Regulation Authority",
                "Qatar Financial Centre", "Reserve Bank of Australia", "Securities and Exchange Commission", "Singapore Exchange", "Six Swiss Exchange", "TeraExchange",
                "The Takeover Panel", "Toronto Stock Exchange", "Tradition SEF", "TSX Venture Exchange", "UK Parliament", "Upper Tribunal (Tax and Chancery Chamber)", "US Congress");

        List<String> DOC_TYPES = Arrays.asList(
                "",
                "Act",
                "Consultation Paper",
                "Disciplinary Action",
                "Election Notice",
                "Equity Regulatory Alert",
                "Information Memo",
                "Information Notice",
                "Law",
                "NASDAQ Issuer Alert",
                "Notice",
                "Options Regulatory Alert",
                "Order",
                "Ordinance",
                "Price List",
                "Regulation",
                "Regulatory Bulletin",
                "Rule Filing",
                "Trade Reporting Notice");

        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        clamshellPage().openSideBarCreate();
        renditionTabCreateClamshellPage().clickAddPDFMetadata(true, false);

        boolean contentTypesVerified = addShellPage().getAllContentTypes().containsAll(contentTypes);
        List<String> values = addShellPage().getAllOrganisations();
        values.remove(0);
        values.remove(0);

        boolean organisationsListVerified = orgValues.containsAll(values);

        List<String> fileTypesFalseSupported = addShellPage().verifyAddPDFMetadataUnsupportedFileTypes();
        boolean fileTypesUnsupportedCorrect = fileTypesFalseSupported.isEmpty();

        List<String> fileTypesSupported = addShellPage().verifyAddPDFMetadataSupportedFileTypes();
        boolean fileTypesSupportedCorrect = fileTypesSupported.isEmpty();

        boolean docTypeDropdownExist = addShellPage().doesElementExist(AddShellPageElements.DOCUMENT_TYPE_DROPDOWN);
        List<String> docType = addShellPage().getAllDocTypes();
        boolean docTypeVerified = docType.containsAll(DOC_TYPES);

        boolean alertCorrect = addShellPage().clickCancel();
        Assertions.assertAll
        (
            () -> Assertions.assertTrue(contentTypesVerified, "All content sets are present"),
            () -> Assertions.assertTrue(organisationsListVerified, "Organisations are correct in the shell page"),
            () -> Assertions.assertTrue(fileTypesUnsupportedCorrect, "File type(s) did not trigger a file type unsupported message: " + fileTypesFalseSupported.toString()),
            () -> Assertions.assertTrue(fileTypesSupportedCorrect, "File type(s) triggered a file type unsupported message: " + fileTypesSupported.toString()),
            () -> Assertions.assertTrue(docTypeDropdownExist, "Document type dropdown menu exists"),
            () -> Assertions.assertTrue(docTypeVerified, "Document type drop down contains expected values"),
            () -> Assertions.assertTrue(alertCorrect, "The cancel alert was correct")
        );
    }
}
