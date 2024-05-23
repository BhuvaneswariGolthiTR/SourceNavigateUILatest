package com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.audits;

import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;

public class AuditsDataMockingConstants {
    public static String newHeadText = "<head.info><headtext>QED Small Section</headtext></head.info>";

    public static String pathToContentComparePicture = "commonFiles\\TestFiles\\AuditRedesignFiles\\Images\\ContentCompareSectionWithAllMarkupsAndSpecialCharacters.png";

    public static String pathToCtabsContentComparePicture = "commonFiles\\TestFiles\\AuditRedesignFiles\\Images\\CtabInContentCompareSection.png";

    public static String textForHeaderTests = "<section type=\"Live\" legacy.identifier=\"992122421\" ID=\"IE586C730805A11ECA6EEDA0C9C0572FA\"><cornerpiece " +
            "type=\"cp\" ID=\"I60A068B4805A11ECB5C9ECCC254832C7\"><metadata.block owner=\"I60A068B4805A11ECB5C9ECCC254832C7\"><md.mnem>cp</md.mnem>" +
            "<md.pub.tag.info><md.pub.tag>AN</md.pub.tag><md.pub.tag>WL</md.pub.tag></md.pub.tag.info><md.source.tag>10</md.source.tag><modified.by>TLE TCBA-BOT "+ DateAndTimeUtils.getCurrentDateMMddyyyy()+ "</modified.by>" +
            "</metadata.block><cornerpiece.text>&sect;&ensp;999.990</cornerpiece.text></cornerpiece><codes.head ID=\"I0D3B6C89805B11ECB5C9ECCC254832C7\">" +
            "<metadata.block owner=\"I0D3B6C89805B11ECB5C9ECCC254832C7\"><md.mnem>snl</md.mnem><md.pub.tag.info><md.pub.tag>AN</md.pub.tag><md.pub.tag>WL</md.pub.tag>" +
            "</md.pub.tag.info><md.source.tag>10</md.source.tag><md.head.info><md.level>15</md.level></md.head.info><modified.by>TLE TCBA-BOT "+DateAndTimeUtils.getCurrentDateMMddyyyy()+"</modified.by>" +
            "</metadata.block><head.info><label.designator>999.990</label.designator>.&emsp;<headtext>QED Small Section</headtext></head.info></codes.head>" +
            "<body><para ID=\"I0D3B6C64805B11ECB5C9ECCC254832C7\"><metadata.block owner=\"I0D3B6C64805B11ECB5C9ECCC254832C7\"><md.mnem>smp</md.mnem>" +
            "<md.pub.tag.info><md.pub.tag>AN</md.pub.tag><md.pub.tag>WL</md.pub.tag></md.pub.tag.info><md.source.tag>10</md.source.tag><modified.by>TLE TCBA-BOT "+ DateAndTimeUtils.getCurrentDateMMddyyyy() +
            "</modified.by></metadata.block><paratext>This document is only for use by the QED Testing Team. <bold>" +
            "<ital>IT IS NOT LEGAL CONTENT OR LEGAL ADVICE!</ital></bold> THIS TEXT WAS ADDED FROM AUDIT DATA MOCKING</paratext></para></body><placeholder " +
            "ID=\"I45D2878331BF11D99E6BEE4CC5FFF826\"><metadata.block owner=\"I45D2878331BF11D99E6BEE4CC5FFF826\"><md.mnem>eot</md.mnem><md.pub.tag.info>" +
            "<md.pub.tag>AN</md.pub.tag><md.pub.tag>WL</md.pub.tag></md.pub.tag.info><md.source.tag>06</md.source.tag><modified.by>TLE TCBA-BOT " + DateAndTimeUtils.getCurrentDateMMddyyyy()+"</modified.by>" +
            "</metadata.block><placeholder.text>&emsp;</placeholder.text></placeholder></section>\n";

    public static String textForDTDDataErrors = "<section ID=\"IE586C730805A11ECA6EEDA0C9C0572FA\" legacy.identifier=\"992122421\" type=\"Live\"><cornerpiece ID=\"I60A068B4805A11ECB5C9ECCC254832C7\" type=\"cp\">" +
            "<metadata.block owner=\"I60A068B4805A11ECB5C9ECCC254832C7\"><md.mnem>cp</md.mnem><md.pub.tag.info><md.pub.tag>AN</md.pub.tag><md.pub.tag>WL</md.pub.tag></md.pub.tag.info><md.source.tag>10</md.source.tag>" +
            "<modified.by>TLE TCBA-BOT 08/29/2022</modified.by></metadata.block><cornerpiece.text>&sect;&ensp;999.990</cornerpiece.text></cornerpiece>\n<codes.head ID=\"I0D3B6C89805B11ECB5C9ECCC254832C7\"><metadata.block owner=\"I0D3B6C89805B11ECB5C9ECCC254832C7\">" +
            "<md.mnem>snl</md.mnem><md.pub.tag.info><md.pub.tag>AN</md.pub.tag><md.pub.tag>WL</md.pub.tag></md.pub.tag.info><md.source.tag>10</md.source.tag><md.head.info>" +
            "<md.level>15</md.level></md.head.info><modified.by>TLE TCBA-BOT 08/29/2022</modified.by></metadata.block><head.info><label.designator>999.990</label.designator>.&emsp;<headtext>QED Small Section</headtext>" +
            "</head.info></codes.head><body><para ID=\"I0D3B6C64805B11ECB5C9ECCC254832C7\"><metadata.block owner=\"I0D3B6C64805B11ECB5C9ECCC254832C7\"><md.mnem>smp</md.mnem>" +
            "<md.pub.tag.info><md.pub.tag>AN</md.pub.tag><md.pub.tag>WL</md.pub.tag></md.pub.tag.info><md.source.tag>10</md.source.tag>" +
            "<modified.by>TLE TCBA-BOT 08/29/2022</modified.by></metadata.block><paratext>This document is only for use by the QED Testing Team.<bold><ital>IT IS NOT LEGAL CONTENT OR LEGAL ADVICE!</ital>" +
            "</bold> THIS TEXT WAS ADDED FROM AUDIT DATA MOCKING</paratext></para><figure ID=\"IDE1C54F727D111EDBF0DC0AAB1A013B2\"><para ID=\"IDE1C54F627D111EDBF0DC0AAB1A013B2\"><metadata.block owner=\"IDE1C54F627D111EDBF0DC0AAB1A013B2\">" +
            "<md.mnem>img</md.mnem><md.pub.tag.info><md.pub.tag>AN</md.pub.tag><md.pub.tag>WL</md.pub.tag></md.pub.tag.info><md.source.tag>10</md.source.tag><modified.by>TLE TCBA-BOT 08/29/2022</modified.by>" +
            "</metadata.block><paratext>&emsp;</paratext></para><para ID=\"IE4F5691127D111EDBF0DC0AAB1A013B2\"><metadata.block owner=\"IE4F5691127D111EDBF0DC0AAB1A013B2\"><md.mnem>smp</md.mnem>" +
            "<md.pub.tag.info><md.pub.tag>AN</md.pub.tag><md.pub.tag>WL</md.pub.tag></md.pub.tag.info><md.source.tag>10</md.source.tag><modified.by>TLE TCBA-BOT 08/29/2022</modified.by>" +
            "</metadata.block><paratext>This document is only for use by the QED Testing Team.<bold><ital>IT IS NOT LEGAL CONTENT OR LEGAL ADVICE!</ital></bold> THIS TEXT WAS ADDED FROM AUDIT DATA MOCKING</paratext>" +
            "</para><image.block><image ID=\"IDE1C54F927D111EDBF0DC0AAB1A013B2\" imageID=\"Idcf9f41027d111ed8e83f919e7db420e\" ident=\"path\" modified=\"true\"/></image.block></figure>" +
            "</body><placeholder ID=\"I45D2878331BF11D99E6BEE4CC5FFF826\"><metadata.block owner=\"I45D2878331BF11D99E6BEE4CC5FFF826\"><md.mnem>eot</md.mnem><md.pub.tag.info><md.pub.tag>AN</md.pub.tag>" +
            "<md.pub.tag>WL</md.pub.tag></md.pub.tag.info><md.source.tag>06</md.source.tag><modified.by>TLE TCBA-BOT 08/29/2022</modified.by></metadata.block><placeholder.text>&emsp;</placeholder.text></placeholder></section>";

    public static String textForNODReportTest = "<section ID=\"IE586C730805A11ECA6EEDA0C9C0572FA\" legacy.identifier=\"992024285\" type=\"Live\">\n" +
            "  <cornerpiece ID=\"I60A068B4805A11ECB5C9ECCC254832C7\" type=\"cp\">\n" +
            "    <metadata.block owner=\"I60A068B4805A11ECB5C9ECCC254832C7\">\n" +
            "      <md.mnem>cp</md.mnem>\n" +
            "      <md.pub.tag.info>\n" +
            "        <md.pub.tag>AN</md.pub.tag>\n" +
            "        <md.pub.tag>WL</md.pub.tag>\n" +
            "      </md.pub.tag.info>\n" +
            "      <md.source.tag>21</md.source.tag>\n" +
            "    </metadata.block>\n" +
            "    <cornerpiece.text>&sect;&ensp;SHARE</cornerpiece.text>\n" +
            "  </cornerpiece>\n" +
            "  <placeholder ID=\"I0B37E5F0A0DE11EDA7F9E5173363F9BE\">\n" +
            "    <metadata.block owner=\"I0B37E5F0A0DE11EDA7F9E5173363F9BE\">\n" +
            "      <md.mnem>ce</md.mnem>\n" +
            "      <md.pub.tag.info>\n" +
            "        <md.pub.tag>NOPUB</md.pub.tag>\n" +
            "      </md.pub.tag.info>\n" +
            "      <md.source.tag>21</md.source.tag>\n" +
            "    </metadata.block>\n" +
            "    <placeholder.text>Formerly cited as IA ST &sect;&ensp;IABCDEFC47DC8F559554A1D5998DA00011.990</placeholder.text>\n" +
            "  </placeholder>\n" +
            "  <codes.head ID=\"I0D3B6C89805B11ECB5C9ECCC254832C7\">\n" +
            "    <metadata.block owner=\"I0D3B6C89805B11ECB5C9ECCC254832C7\">\n" +
            "      <md.mnem>snl</md.mnem>\n" +
            "      <md.pub.tag.info>\n" +
            "        <md.pub.tag>AN</md.pub.tag>\n" +
            "        <md.pub.tag>WL</md.pub.tag>\n" +
            "      </md.pub.tag.info>\n" +
            "      <md.source.tag>21</md.source.tag>\n" +
            "      <md.head.info>\n" +
            "        <md.level>15</md.level>\n" +
            "      </md.head.info>\n" +
            "    </metadata.block>\n" +
            "    <head.info>\n" +
            "      <label.designator>SHARE</label.designator>.&emsp;\n" +
            "      <headtext>QED Small Section</headtext>\n" +
            "    </head.info>\n" +
            "  </codes.head>\n" +
            "  <body>\n" +
            "    <para ID=\"I0D3B6C64805B11ECB5C9ECCC254832C7\">\n" +
            "      <metadata.block owner=\"I0D3B6C64805B11ECB5C9ECCC254832C7\">\n" +
            "        <md.mnem>smp</md.mnem>\n" +
            "        <md.pub.tag.info>\n" +
            "          <md.pub.tag>AN</md.pub.tag>\n" +
            "          <md.pub.tag>WL</md.pub.tag>\n" +
            "        </md.pub.tag.info>\n" +
            "        <md.source.tag>21</md.source.tag>\n" +
            "        <modified.by>TLE TCBA-BOT 01/28/2022</modified.by>\n" +
            "      </metadata.block>\n" +
            "      <paratext>This document is only for use by the QED Testing Team.AddingText  \n" +
            "        <bold>\n" +
            "          <ital>IT IS NOT LEGAL CONTENT OR LEGAL ADVICE!</ital>\n" +
            "        </bold>\n" +
            "      </paratext>\n" +
            "    </para>\n" +
            "  </body>\n" +
            "  <placeholder ID=\"I45D2878331BF11D99E6BEE4CC5FFF826\">\n" +
            "    <metadata.block owner=\"I45D2878331BF11D99E6BEE4CC5FFF826\">\n" +
            "      <md.mnem>eot</md.mnem>\n" +
            "      <md.pub.tag.info>\n" +
            "        <md.pub.tag>AN</md.pub.tag>\n" +
            "        <md.pub.tag>WL</md.pub.tag>\n" +
            "      </md.pub.tag.info>\n" +
            "      <md.source.tag>21</md.source.tag>\n" +
            "    </metadata.block>\n" +
            "    <placeholder.text>&emsp;</placeholder.text>\n" +
            "  </placeholder>\n" +
            "</section>";
    public static String pathToCleanValidationFlagInNodeSection = "commonFiles\\TestFiles\\AuditRedesignFiles\\Images\\CleanValidationFlagInNodeSection";

    public static String pathToErrorValidationFlagInNodeSection = "commonFiles\\TestFiles\\AuditRedesignFiles\\Images\\ErrorValidationFlagInNodeSection";

    public static String pathToWarningValidationFlagInNodeSection = "commonFiles\\TestFiles\\AuditRedesignFiles\\Images\\WarningValidationFlagInNodeSection";

    public static String pathToDeletedValidationFlagInNodeSection = "commonFiles\\TestFiles\\AuditRedesignFiles\\Images\\DeletedValidationFlagInNodeSection";

    public static String pathToInfoValidationFlagInNodeSection = "commonFiles\\TestFiles\\AuditRedesignFiles\\Images\\InfoValidationFlagInNodeSection";

    public static String pathToCleanValidationFlagInSummary = "commonFiles\\TestFiles\\AuditRedesignFiles\\Images\\CleanValidationFlagInSummary";

    public static String pathToErrorValidationFlagInSummary = "commonFiles\\TestFiles\\AuditRedesignFiles\\Images\\ErrorValidationFlagInSummary";

    public static String pathToWarningValidationFlagInSummary = "commonFiles\\TestFiles\\AuditRedesignFiles\\Images\\WarningValidationFlagInSummary";

    public static String pathToDeletedValidationFlagInSummary = "commonFiles\\TestFiles\\AuditRedesignFiles\\Images\\DeletedValidationFlagInSummary";

    public static String pathToInfoValidationFlagInSummary = "commonFiles\\TestFiles\\AuditRedesignFiles\\Images\\InfoValidationFlagInSummary";

    public static String pathToDataValidationErrorFlagInNodeSection = "commonFiles\\TestFiles\\AuditRedesignFiles\\Images\\DataValidationErrorInNodeSection";

    public static String pathToDTDValidationErrorFlagInNodeSection = "commonFiles\\TestFiles\\AuditRedesignFiles\\Images\\DTDErrorValidationFlagInNodeSection";


    public static String textForContentCompareTests= "<section type=\"Live\" legacy.identifier=\"992122421\" ID=\"IE586C730805A11ECA6EEDA0C9C0572FA\"> " +
            "<cornerpiece type=\"cp\" ID=\"I60A068B4805A11ECB5C9ECCC254832C7\"> <metadata.block owner=\"I60A068B4805A11ECB5C9ECCC254832C7\"> <md.mnem>cp</md.mnem> " +
            "<md.pub.tag.info> <md.pub.tag>AN</md.pub.tag> <md.pub.tag>WL</md.pub.tag> </md.pub.tag.info> <md.source.tag>10</md.source.tag> <modified.by>TLE TCBA-BOT" +
            " 10/07/2022</modified.by> </metadata.block> <cornerpiece.text>&sect;&ensp;999.990</cornerpiece.text> </cornerpiece> <codes.head ID=\"I0D3B6C89805B11ECB5C9ECCC254832C7\">" +
            " <metadata.block owner=\"I0D3B6C89805B11ECB5C9ECCC254832C7\"> <md.mnem>snl</md.mnem> <md.pub.tag.info> <md.pub.tag>AN</md.pub.tag> <md.pub.tag>WL</md.pub.tag>" +
            " </md.pub.tag.info> <md.source.tag>10</md.source.tag> <md.head.info> <md.level>15</md.level> </md.head.info> <modified.by>TLE TCBA-BOT 10/07/2022</modified.by>" +
            " </metadata.block> <head.info> <label.designator>999.990</label.designator>.&emsp; <headtext>QED Small Section</headtext> </head.info> </codes.head> <body>" +
            " <para ID=\"I0D3B6C64805B11ECB5C9ECCC254832C7\"> <metadata.block owner=\"I0D3B6C64805B11ECB5C9ECCC254832C7\"> <md.mnem>smp</md.mnem> <md.pub.tag.info>" +
            " <md.pub.tag>AN</md.pub.tag> <md.pub.tag>WL</md.pub.tag> </md.pub.tag.info> <md.source.tag>10</md.source.tag> <modified.by>TLE TCBA-BOT 10/07/2022</modified.by>" +
            " </metadata.block> <paratext>This document is only for use by the QED Testing Team. <bold> <ital>IT IS NOT LEGAL CONTENT OR LEGAL ADVICE!</ital> </bold> " +
            "THIS TEXT WAS ADDED FROM AUDIT DATA MOCKING </paratext> </para> <para ID=\"ID5F2A8E8466E11ED8A01C26D38300A7C\"> <metadata.block owner=\"ID5F2A8E8466E11ED8A01C26D38300A7C\">" +
            " <md.mnem>smp</md.mnem> <md.pub.tag.info> <md.pub.tag>AN</md.pub.tag> <md.pub.tag>WL</md.pub.tag> </md.pub.tag.info> <md.source.tag>10</md.source.tag> " +
            "<modified.by>TLE TCBA-BOT 10/07/2022</modified.by> </metadata.block> <paratext> <added.material>This </added.material> <quote style=\"block\">text</quote> " +
            "<bold>is </bold> <uc>used </uc> <wip.flag legacy.markup=\"centh\">for </wip.flag> <leader> <leaderchar>the </leaderchar> </leader> <charfill numchar=\"3\">content " +
            "</charfill> <cite.query w-ref-type=\"LQ\" w-normalized-cite=\"TEST\" w-pub-number=\"1000256\" format=\"cent_r\" manual-edit=\"true\">compare </cite.query> tests. " +
            "<conditional>There </conditional> <crosshatch>is </crosshatch> <deleted.material>going </deleted.material> <dbluscore>to </dbluscore>be <endline quadding=\"c\"/>a lot" +
            " <endline quadding=\"j\"/>of <endline quadding=\"l\"/>mark <endline quadding=\"r\"/> <typo.format>ups </typo.format> <fraction> <numerator>here </numerator> " +
            "<denominator>denom</denominator> </fraction> <wip.flag legacy.markup=\"ceqxfrq\"/>so I <ital>need </ital> <vetoed.text mandated.display=\"strikethrough\"> " +
            "<deleted.material style=\"percent_op\">a </deleted.material> </vetoed.text> <conditional product.type=\"leg.service\" action=\"suppress\">lot </conditional> " +
            "<set.line.indent/> <pasteup>of </pasteup> <conditional product.type=\"westlaw\" action=\"include\"> <wip.flag legacy.markup=\"centdol\">text " +
            "</wip.flag> </conditional> <wip.flag legacy.markup=\"cent8\">here</wip.flag>. <conditional product.type=\"print\" action=\"suppress\">Hmmm</conditional>," +
            " <justifying.space>what </justifying.space> <target>should </target>I <csc>talk </csc> <wip.flag legacy.markup=\"cent1\">about</wip.flag>?" +
            " <deleted.material style=\"percent_x\">I&rsquo;m </deleted.material> <sub style=\"percent_l\">not </sub> <super style=\"percent_g\">really " +
            "</super>sure. I <underscore>wonder </underscore> <url>who </url>is <marginal.markup>going </marginal.markup> <starpage.anchor>to </starpage.anchor> " +
            "<label.designator>be </label.designator> <conditional product.type=\"pocket.part\" action=\"include\">the </conditional> <vendor.extract>next </vendor.extract> " +
            "<vetoed.text>person </vetoed.text> <target target.format=\"cent_v\">to </target> <conditional product.type=\"westlaw.legacy\" action=\"include\">" +
            " <wip.flag legacy.markup=\"centdol\">read </wip.flag> </conditional> <conditional product.type=\"westlaw\" action=\"suppress\">this </conditional>? Or " +
            "<wip.flag legacy.markup=\"centdol\">maybe </wip.flag>I <wordphrase/>am <wordphrase/>the only one &mdash;ever &ensp;to readd this entire thing. Never know for sure. " +
            "</paratext> </para> <xampex.table ID=\"I3B50513271B211ED9604CC19A571CE41\"> <para ID=\"I3B50513071B211ED9604CC19A571CE41\"> " +
            "<metadata.block owner=\"I3B50513071B211ED9604CC19A571CE41\"> <md.mnem>stbl</md.mnem> <md.pub.tag.info> <md.pub.tag>AN</md.pub.tag> <md.pub.tag>WL</md.pub.tag> " +
            "</md.pub.tag.info> <md.source.tag>10</md.source.tag> <modified.by>TLE TCBA-BOT 12/01/2022</modified.by> </metadata.block> <paratext>2</paratext> </para> " +
            "<para ID=\"I3B50512871B211ED9604CC19A571CE41\"> <metadata.block owner=\"I3B50512871B211ED9604CC19A571CE41\"> <md.mnem>dtblw</md.mnem> <md.pub.tag.info> " +
            "<md.pub.tag>AN</md.pub.tag> <md.pub.tag>WL</md.pub.tag> </md.pub.tag.info> <md.source.tag>10</md.source.tag> <modified.by>TLE TCBA-BOT 12/01/2022</modified.by> " +
            "</metadata.block> <paratext> </paratext> </para> <para ID=\"I3B50512071B211ED9604CC19A571CE41\"> <metadata.block owner=\"I3B50512071B211ED9604CC19A571CE41\"> " +
            "<md.mnem>h</md.mnem> <md.pub.tag.info> <md.pub.tag>AN</md.pub.tag> <md.pub.tag>WL</md.pub.tag> </md.pub.tag.info> <md.source.tag>10</md.source.tag> " +
            "<modified.by>TLE TCBA-BOT 12/01/2022</modified.by> </metadata.block> <paratext>tests <ctab/>tests </paratext> </para> <para ID=\"I3B50511771B211ED9604CC19A571CE41\"> " +
            "<metadata.block owner=\"I3B50511771B211ED9604CC19A571CE41\"> <md.mnem>b</md.mnem> <md.pub.tag.info> <md.pub.tag>AN</md.pub.tag> <md.pub.tag>WL</md.pub.tag> " +
            "</md.pub.tag.info> <md.source.tag>10</md.source.tag> <modified.by>TLE TCBA-BOT 12/01/2022</modified.by> </metadata.block> <paratext>tests <ctab/>tests </paratext> " +
            "</para> <para ID=\"I3B50510E71B211ED9604CC19A571CE41\"> <metadata.block owner=\"I3B50510E71B211ED9604CC19A571CE41\"> <md.mnem>wsoc</md.mnem> <md.pub.tag.info> " +
            "<md.pub.tag>AN</md.pub.tag> </md.pub.tag.info> <md.source.tag>10</md.source.tag> <modified.by>TLE TCBA-BOT 12/01/2022</modified.by> </metadata.block> " +
            "<paratext>&emsp;</paratext> </para> <para ID=\"I3B50510571B211ED9604CC19A571CE41\"> <metadata.block owner=\"I3B50510571B211ED9604CC19A571CE41\"> <md.mnem>etbl</md.mnem>" +
            " <md.pub.tag.info> <md.pub.tag>AN</md.pub.tag> <md.pub.tag>WL</md.pub.tag> </md.pub.tag.info> <md.source.tag>10</md.source.tag> " +
            "<modified.by>TLE TCBA-BOT 12/01/2022</modified.by> </metadata.block> <paratext>&emsp;</paratext> </para> </xampex.table> </body> " +
            "<placeholder ID=\"I45D2878331BF11D99E6BEE4CC5FFF826\"> <metadata.block owner=\"I45D2878331BF11D99E6BEE4CC5FFF826\"> <md.mnem>eot</md.mnem> <md.pub.tag.info>" +
            " <md.pub.tag>AN</md.pub.tag> <md.pub.tag>WL</md.pub.tag> </md.pub.tag.info> <md.source.tag>21</md.source.tag> <modified.by>TLE TCBA-BOT 10/07/2022</modified.by>" +
            " </metadata.block> <placeholder.text>&emsp;</placeholder.text> </placeholder> </section>";

    public static String textForMarkupsTest= "<section ID=\"IE586C730805A11ECA6EEDA0C9C0572FA\" legacy.identifier=\"992122421\" type=\"Live\"> <cornerpiece ID=\"I60A068B4805A11ECB5C9ECCC254832C7\" type=\"cp\"> <metadata.block owner=\"I60A068B4805A11ECB5C9ECCC254832C7\"> <md.mnem>cp</md.mnem> <md.pub.tag.info> <md.pub.tag>AN</md.pub.tag> <md.pub.tag>WL</md.pub.tag> </md.pub.tag.info> <md.source.tag>10</md.source.tag> <modified.by>TLE TCBA-BOT 10/07/2022</modified.by> </metadata.block> <cornerpiece.text>&sect;&ensp;999.990</cornerpiece.text> </cornerpiece> <codes.head ID=\"I0D3B6C89805B11ECB5C9ECCC254832C7\"> <metadata.block owner=\"I0D3B6C89805B11ECB5C9ECCC254832C7\"> <md.mnem>snl</md.mnem> <md.pub.tag.info> <md.pub.tag>AN</md.pub.tag> <md.pub.tag>WL</md.pub.tag> </md.pub.tag.info> <md.source.tag>10</md.source.tag> <md.head.info> <md.level>15</md.level> </md.head.info> <modified.by>TLE TCBA-BOT 10/07/2022</modified.by> </metadata.block> <head.info> <label.designator>999.990</label.designator>.&emsp; <headtext>QED Small Section</headtext> </head.info> </codes.head> <body> <para ID=\"I0D3B6C64805B11ECB5C9ECCC254832C7\"> <metadata.block owner=\"I0D3B6C64805B11ECB5C9ECCC254832C7\"> <md.mnem>smp</md.mnem> <md.pub.tag.info> <md.pub.tag>AN</md.pub.tag> <md.pub.tag>WL</md.pub.tag> </md.pub.tag.info> <md.source.tag>10</md.source.tag> <modified.by>TLE TCBA-BOT 10/07/2022</modified.by> </metadata.block> <paratext>This document is only for use by the QED Testing Team. <bold> <ital>IT IS NOT LEGAL CONTENT OR LEGAL ADVICE!</ital> </bold> THIS TEXT WAS ADDED FROM AUDIT DATA MOCKING </paratext> </para> <para ID=\"ID5F2A8E8466E11ED8A01C26D38300A7C\"> <metadata.block owner=\"ID5F2A8E8466E11ED8A01C26D38300A7C\"> <md.mnem>smp</md.mnem> <md.pub.tag.info> <md.pub.tag>AN</md.pub.tag> <md.pub.tag>WL</md.pub.tag> </md.pub.tag.info> <md.source.tag>10</md.source.tag> <modified.by>TLE TCBA-BOT 10/07/2022</modified.by> </metadata.block> <paratext> <added.material>This </added.material> <quote style=\"block\">text</quote> <bold>is </bold> <uc>used </uc> <wip.flag legacy.markup=\"centh\">for </wip.flag> <leader> <leaderchar>the </leaderchar> </leader> <charfill numchar=\"3\">content </charfill> <cite.query manual-edit=\"true\" format=\"cent_r\" w-pub-number=\"1000256\" w-normalized-cite=\"TEST\" w-ref-type=\"LQ\">compare </cite.query> tests. <conditional>There </conditional> <crosshatch>is </crosshatch> <deleted.material>going </deleted.material> <dbluscore>to </dbluscore>be <endline quadding=\"c\"/>a lot <endline quadding=\"j\"/>of <endline quadding=\"l\"/>mark <endline quadding=\"r\"/> <typo.format>ups </typo.format> <fraction> <numerator>here </numerator> <denominator>denom</denominator> </fraction> <wip.flag legacy.markup=\"ceqxfrq\"/>so I <ital>need </ital> <vetoed.text mandated.display=\"strikethrough\"> <deleted.material style=\"percent_op\">a </deleted.material> </vetoed.text> <conditional action=\"suppress\" product.type=\"leg.service\">lot </conditional> <set.line.indent/> <pasteup>of </pasteup> <conditional action=\"include\" product.type=\"westlaw\"> <wip.flag legacy.markup=\"centdol\">text </wip.flag> </conditional> <wip.flag legacy.markup=\"cent8\">here</wip.flag>. <conditional action=\"suppress\" product.type=\"print\">Hmmm</conditional>, <justifying.space>what </justifying.space> <target>should </target>I <csc>talk </csc> <wip.flag legacy.markup=\"cent1\">about</wip.flag>? <deleted.material style=\"percent_x\">I&rsquo;m </deleted.material> <sub style=\"percent_l\">not </sub> <super style=\"percent_g\">really </super>sure. I <underscore>wonder </underscore> <url>who </url>is <marginal.markup>going </marginal.markup> <starpage.anchor>to </starpage.anchor> <label.designator>be </label.designator> <conditional action=\"include\" product.type=\"pocket.part\">the </conditional> <vendor.extract>next </vendor.extract> <vetoed.text>person </vetoed.text> <target target.format=\"cent_v\">to </target> <conditional action=\"include\" product.type=\"westlaw.legacy\"> <wip.flag legacy.markup=\"centdol\">read </wip.flag> </conditional> <conditional action=\"suppress\" product.type=\"westlaw\">this </conditional>? Or <wip.flag legacy.markup=\"centdol\">maybe </wip.flag>I <wordphrase/>am <wordphrase/>the only one &mdash;ever &ensp;to readd this entire thing. Never know for sure. </paratext> </para> </body> <placeholder ID=\"I45D2878331BF11D99E6BEE4CC5FFF826\"> <metadata.block owner=\"I45D2878331BF11D99E6BEE4CC5FFF826\"> <md.mnem>eot</md.mnem> <md.pub.tag.info> <md.pub.tag>AN</md.pub.tag> <md.pub.tag>WL</md.pub.tag> </md.pub.tag.info> <md.source.tag>21</md.source.tag> <modified.by>TLE TCBA-BOT 10/07/2022</modified.by> </metadata.block> <placeholder.text>&emsp;</placeholder.text> </placeholder> </section>";

    public static String textForCtabTest = "<section type=\"Live\" legacy.identifier=\"992122421\" ID=\"IE586C730805A11ECA6EEDA0C9C0572FA\"> <cornerpiece type=\"cp\" ID=\"I60A068B4805A11ECB5C9ECCC254832C7\"> <metadata.block owner=\"I60A068B4805A11ECB5C9ECCC254832C7\"> <md.mnem>cp</md.mnem> <md.pub.tag.info> <md.pub.tag>AN</md.pub.tag> <md.pub.tag>WL</md.pub.tag> </md.pub.tag.info> <md.source.tag>10</md.source.tag> <modified.by>TLE TCBA-BOT 10/07/2022</modified.by> </metadata.block> <cornerpiece.text>&sect;&ensp;999.990</cornerpiece.text> </cornerpiece> <codes.head ID=\"I0D3B6C89805B11ECB5C9ECCC254832C7\"> <metadata.block owner=\"I0D3B6C89805B11ECB5C9ECCC254832C7\"> <md.mnem>snl</md.mnem> <md.pub.tag.info> <md.pub.tag>AN</md.pub.tag> <md.pub.tag>WL</md.pub.tag> </md.pub.tag.info> <md.source.tag>10</md.source.tag> <md.head.info> <md.level>15</md.level> </md.head.info> <modified.by>TLE TCBA-BOT 10/07/2022</modified.by> </metadata.block> <head.info> <label.designator>999.990</label.designator>.&emsp; <headtext>QED Small Section</headtext> </head.info> </codes.head> <body> <para ID=\"I0D3B6C64805B11ECB5C9ECCC254832C7\"> <metadata.block owner=\"I0D3B6C64805B11ECB5C9ECCC254832C7\"> <md.mnem>smp</md.mnem> <md.pub.tag.info> <md.pub.tag>AN</md.pub.tag> <md.pub.tag>WL</md.pub.tag> </md.pub.tag.info> <md.source.tag>10</md.source.tag> <modified.by>TLE TCBA-BOT 10/07/2022</modified.by> </metadata.block> <paratext>This document is only for use by the QED Testing Team. <bold> <ital>IT IS NOT LEGAL CONTENT OR LEGAL ADVICE!</ital> </bold> THIS TEXT WAS ADDED FROM AUDIT DATA MOCKING </paratext> </para> <xampex.table ID=\"IC38576027A4C11ED80B68DD7790E9E0C\"> <para ID=\"IC38576007A4C11ED80B68DD7790E9E0C\"> <metadata.block owner=\"IC38576007A4C11ED80B68DD7790E9E0C\"> <md.mnem>stbl</md.mnem> <md.pub.tag.info> <md.pub.tag>AN</md.pub.tag> <md.pub.tag>WL</md.pub.tag> </md.pub.tag.info> <md.source.tag>10</md.source.tag> <md.sn.info> <md.sn.query.table ID=\"IC3A66AC07A4C11ED80B68DD7790E9E0C\">A child element of xampex.table was modified in the source editor by a user not assigned to the Tabular User role</md.sn.query.table> </md.sn.info> <modified.by>TLE TCBA-BOT 12/12/2022</modified.by> </metadata.block> <paratext>3</paratext> </para> <para ID=\"IC38575F87A4C11ED80B68DD7790E9E0C\"> <metadata.block owner=\"IC38575F87A4C11ED80B68DD7790E9E0C\"> <md.mnem>dtblw</md.mnem> <md.pub.tag.info> <md.pub.tag>AN</md.pub.tag> <md.pub.tag>WL</md.pub.tag> </md.pub.tag.info> <md.source.tag>10</md.source.tag> <md.sn.info> <md.sn.query.table ID=\"IC3B22A907A4C11ED80B68DD7790E9E0C\">A child element of xampex.table was modified in the source editor by a user not assigned to the Tabular User role</md.sn.query.table> </md.sn.info> <modified.by>TLE TCBA-BOT 12/12/2022</modified.by> </metadata.block> <paratext/> </para> <para ID=\"IC38575F07A4C11ED80B68DD7790E9E0C\"> <metadata.block owner=\"IC38575F07A4C11ED80B68DD7790E9E0C\"> <md.mnem>h</md.mnem> <md.pub.tag.info> <md.pub.tag>AN</md.pub.tag> <md.pub.tag>WL</md.pub.tag> </md.pub.tag.info> <md.source.tag>10</md.source.tag> <md.sn.info> <md.sn.query.table ID=\"IC3BDC3507A4C11ED80B68DD7790E9E0C\">A child element of xampex.table was modified in the source editor by a user not assigned to the Tabular User role</md.sn.query.table> <md.sn.query.table ID=\"IC78EC8807A4C11ED80B68DD7790E9E0C\">A child element of xampex.table was modified in the source editor by a user not assigned to the Tabular User role</md.sn.query.table> </md.sn.info> <modified.by>TLE TCBA-BOT 12/12/2022</modified.by> </metadata.block> <paratext>testing <ctab/>testing <ctab/>testing </paratext> </para> <para ID=\"IC38575E77A4C11ED80B68DD7790E9E0C\"> <metadata.block owner=\"IC38575E77A4C11ED80B68DD7790E9E0C\"> <md.mnem>b</md.mnem> <md.pub.tag.info> <md.pub.tag>AN</md.pub.tag> <md.pub.tag>WL</md.pub.tag> </md.pub.tag.info> <md.source.tag>10</md.source.tag> <md.sn.info> <md.sn.query.table ID=\"IC3C95C107A4C11ED80B68DD7790E9E0C\">A child element of xampex.table was modified in the source editor by a user not assigned to the Tabular User role</md.sn.query.table> <md.sn.query.table ID=\"ICCF588E07A4C11ED80B68DD7790E9E0C\">A child element of xampex.table was modified in the source editor by a user not assigned to the Tabular User role</md.sn.query.table> </md.sn.info> <modified.by>TLE TCBA-BOT 12/12/2022</modified.by> </metadata.block> <paratext>testing <ctab/>testing <ctab/>testing </paratext> </para> <para ID=\"IC38575DE7A4C11ED80B68DD7790E9E0C\"> <metadata.block owner=\"IC38575DE7A4C11ED80B68DD7790E9E0C\"> <md.mnem>wsoc</md.mnem> <md.pub.tag.info> <md.pub.tag>AN</md.pub.tag> </md.pub.tag.info> <md.source.tag>10</md.source.tag> <md.sn.info> <md.sn.query.table ID=\"IC3D4F4D07A4C11ED80B68DD7790E9E0C\">A child element of xampex.table was modified in the source editor by a user not assigned to the Tabular User role</md.sn.query.table> </md.sn.info> <modified.by>TLE TCBA-BOT 12/12/2022</modified.by> </metadata.block> <paratext>&emsp;</paratext> </para> <para ID=\"IC38575D57A4C11ED80B68DD7790E9E0C\"> <metadata.block owner=\"IC38575D57A4C11ED80B68DD7790E9E0C\"> <md.mnem>etbl</md.mnem> <md.pub.tag.info> <md.pub.tag>AN</md.pub.tag> <md.pub.tag>WL</md.pub.tag> </md.pub.tag.info> <md.source.tag>10</md.source.tag> <md.sn.info> <md.sn.query.table ID=\"IC3E150E07A4C11ED80B68DD7790E9E0C\">A child element of xampex.table was modified in the source editor by a user not assigned to the Tabular User role</md.sn.query.table> </md.sn.info> <modified.by>TLE TCBA-BOT 12/12/2022</modified.by> </metadata.block> <paratext>&emsp;</paratext> </para> </xampex.table> </body> <placeholder ID=\"I45D2878331BF11D99E6BEE4CC5FFF826\"> <metadata.block owner=\"I45D2878331BF11D99E6BEE4CC5FFF826\"> <md.mnem>eot</md.mnem> <md.pub.tag.info> <md.pub.tag>AN</md.pub.tag> <md.pub.tag>WL</md.pub.tag> </md.pub.tag.info> <md.source.tag>21</md.source.tag> <modified.by>TLE TCBA-BOT 10/07/2022</modified.by> </metadata.block> <placeholder.text>&emsp;</placeholder.text> </placeholder> </section>";
    public static String textForContentCompareMiddleColumnTest = "<section legacy.identifier=\"001391565\" type=\"Live\" ID=\"I08EEDB601B7411DC868395DC84A0FC63\"> " +
            "<cornerpiece type=\"cp\" ID=\"I08EEDB611B7411DC868395DC84A0FC63\"> <metadata.block owner=\"I08EEDB611B7411DC868395DC84A0FC63\"> <md.mnem>cp</md.mnem> " +
            "<md.text.id>01006760813</md.text.id> <md.pub.tag.info> <md.pub.tag>AN</md.pub.tag> <md.pub.tag>WL</md.pub.tag> </md.pub.tag.info> <md.source.tag>MV-RP" +
            "</md.source.tag> </metadata.block> <cornerpiece.text>&sect;&ensp;IABCDEF44727A7E4C76296A08606A00011.990</cornerpiece.text> </cornerpiece> " +
            "<codes.head ID=\"I08EF50901B7411DC868395DC84A0FC63\"> <metadata.block owner=\"I08EF50901B7411DC868395DC84A0FC63\"> <md.mnem>snl</md.mnem> " +
            "<md.text.id>01006760814</md.text.id> <md.pub.tag.info> <md.pub.tag>AN</md.pub.tag> <md.pub.tag>WL</md.pub.tag> </md.pub.tag.info> <md.source.tag>10</md.source.tag>" +
            " <md.head.info> <md.level>15</md.level> </md.head.info> <modified.by>TLE TCBA-BOT 03/03/2022</modified.by> </metadata.block> <head.info> <label.designator>" +
            "IABCDEF44727A7E4C76296A08606A00011.990</label.designator>.&emsp; <headtext>QED Medium Section</headtext> </head.info> </codes.head> <body> <ed.note type=\"KITN\"> " +
            "<para ID=\"I08EF50911B7411DC868395DC84A0FC63\"> <metadata.block owner=\"I08EF50911B7411DC868395DC84A0FC63\"> <md.mnem>wisc</md.mnem> <md.text.id>00000000000</md.text.id> " +
            "<md.pub.tag.info> <md.pub.tag>AN</md.pub.tag> <md.pub.tag>WL</md.pub.tag> </md.pub.tag.info> <md.source.tag>10</md.source.tag> <modified.by>TLE TCBA-BOT 03/03/2022" +
            "</modified.by> </metadata.block> <paratext>[Text subject to final changes by the Scale and Performance Team]</paratext> </para> " +
            "<para ID=\"I7F1DE2609AF611ECB82D9E666E4B62D4\"> <metadata.block owner=\"I7F1DE2609AF611ECB82D9E666E4B62D4\"> <md.mnem>wisc</md.mnem> <md.text.id>00000000000" +
            "</md.text.id> <md.pub.tag.info> <md.pub.tag>AN</md.pub.tag> <md.pub.tag>WL</md.pub.tag> </md.pub.tag.info> <md.source.tag>10</md.source.tag> " +
            "<modified.by>TLE TCBA-BOT 03/03/2022</modified.by> </metadata.block> <paratext>[This document is only for use by the QED Testing Team -- it is not legal content " +
            "or legal advice]</paratext> </para> </ed.note> <subsection ID=\"IB58E292056DC11EDBDD690D66C2990F4\"> <metadata.block owner=\"IB58E292056DC11EDBDD690D66C2990F4\"> " +
            "<md.head.info> <md.label.designator>1</md.label.designator> </md.head.info> </metadata.block> <para ID=\"I08EF50921B7411DC868395DC84A0FC63\"> " +
            "<metadata.block owner=\"I08EF50921B7411DC868395DC84A0FC63\"> <md.mnem>smp</md.mnem> <md.text.id>01006760815E</md.text.id> <md.pub.tag.info> <md.pub.tag>AN</md.pub.tag> " +
            "<md.pub.tag>WL</md.pub.tag> </md.pub.tag.info> <md.source.tag>10</md.source.tag> <modified.by>TLE TCBA-BOT 03/03/2022</modified.by> </metadata.block> " +
            "<paratext>1.&emsp;The QED Testing team shall do the following:</paratext> </para> <subsection ID=\"IB58E292156DC11EDBDD690D66C2990F4\"> " +
            "<metadata.block owner=\"IB58E292156DC11EDBDD690D66C2990F4\"> <md.head.info> <md.label.designator>a</md.label.designator> </md.head.info> </metadata.block>" +
            " <para ID=\"I08EF50941B7411DC868395DC84A0FC63\"> <metadata.block owner=\"I08EF50941B7411DC868395DC84A0FC63\"> <md.mnem>smp</md.mnem> <md.text.id>01006760817E" +
            "</md.text.id> <md.pub.tag.info> <md.pub.tag>AN</md.pub.tag> <md.pub.tag>WL</md.pub.tag> </md.pub.tag.info> <md.source.tag>10</md.source.tag> " +
            "<modified.by>TLE TCBA-BOT 10/28/2022</modified.by> </metadata.block> <paratext>a.&emsp;Write clear, readable tests in order to ensure CodesBench is working properly." +
            "</paratext> </para> </subsection> <subsection ID=\"IB58E292256DC11EDBDD690D66C2990F4\"> <metadata.block owner=\"IB58E292256DC11EDBDD690D66C2990F4\"> <md.head.info> " +
            "<md.label.designator>b</md.label.designator> </md.head.info> </metadata.block> <para ID=\"I08EF50951B7411DC868395DC84A0FC63\"> " +
            "<metadata.block owner=\"I08EF50951B7411DC868395DC84A0FC63\"> <md.mnem>smp</md.mnem> <md.text.id>00000000000</md.text.id> <md.pub.tag.info> " +
            "<md.pub.tag>AN</md.pub.tag> <md.pub.tag>WL</md.pub.tag> </md.pub.tag.info> <md.source.tag>10</md.source.tag> <modified.by>TLE TCBA-BOT 10/28/2022</modified.by> " +
            "</metadata.block> <paratext>b.&emsp;Ensure the tests are written in such a way that they will continue to work for years to come, with little to no maintenance." +
            "</paratext> </para> </subsection> </subsection> <subsection ID=\"IB58E292356DC11EDBDD690D66C2990F4\"> <metadata.block owner=\"IB58E292356DC11EDBDD690D66C2990F4\"> " +
            "<md.head.info> <md.label.designator>2</md.label.designator> </md.head.info> </metadata.block> <para ID=\"I08EF77A11B7411DC868395DC84A0FC63\"> " +
            "<metadata.block owner=\"I08EF77A11B7411DC868395DC84A0FC63\"> <md.mnem>smp</md.mnem> <md.text.id>01006760820E</md.text.id> <md.pub.tag.info> " +
            "<md.pub.tag>AN</md.pub.tag> <md.pub.tag>WL</md.pub.tag> </md.pub.tag.info> <md.source.tag>10</md.source.tag> <modified.by>TLE TCBA-BOT 03/03/2022</modified.by>" +
            " </metadata.block> <paratext>2.&emsp;The QED Team, subject to approval from their Team Lead, may do all of the following:</paratext> </para> " +
            "<subsection ID=\"IB58E292456DC11EDBDD690D66C2990F4\"> <metadata.block owner=\"IB58E292456DC11EDBDD690D66C2990F4\"> <md.head.info> " +
            "<md.label.designator>a</md.label.designator> </md.head.info> </metadata.block> <para ID=\"I4AA4788656DC11ED89D2E32A49CAB806\"> " +
            "<metadata.block owner=\"I4AA4788656DC11ED89D2E32A49CAB806\"> <md.mnem>smp</md.mnem> <md.text.id>01006760821E</md.text.id> <md.pub.tag.info> " +
            "<md.pub.tag>AN</md.pub.tag> <md.pub.tag>WL</md.pub.tag> </md.pub.tag.info> <md.source.tag>10</md.source.tag> <modified.by>TLE TCBA-BOT 10/28/2022" +
            "</modified.by> </metadata.block> <paratext>a.&emsp;This is section should be added</paratext> </para> </subsection> " +
            "<subsection ID=\"IB58E292556DC11EDBDD690D66C2990F4\"> <metadata.block owner=\"IB58E292556DC11EDBDD690D66C2990F4\"> <md.head.info>" +
            " <md.label.designator>b</md.label.designator> </md.head.info> </metadata.block> <para ID=\"I08EF77A21B7411DC868395DC84A0FC63\"> " +
            "<metadata.block owner=\"I08EF77A21B7411DC868395DC84A0FC63\"> <md.mnem>smp</md.mnem> <md.text.id>01006760821E</md.text.id> <md.pub.tag.info> " +
            "<md.pub.tag>AN</md.pub.tag> <md.pub.tag>WL</md.pub.tag> </md.pub.tag.info> <md.source.tag>10</md.source.tag> <modified.by>TLE TCBA-BOT 10/28/2022" +
            "</modified.by> </metadata.block> <paratext>b.&emsp;Go on vacation without inviting other members of the team (even if their teammates really want to go too)." +
            "</paratext> </para> </subsection> <subsection ID=\"IB58E292656DC11EDBDD690D66C2990F4\"> <metadata.block owner=\"IB58E292656DC11EDBDD690D66C2990F4\"> <md.head.info> " +
            "<md.label.designator>c</md.label.designator> </md.head.info> </metadata.block> <para ID=\"I08EF77A31B7411DC868395DC84A0FC63\"> " +
            "<metadata.block owner=\"I08EF77A31B7411DC868395DC84A0FC63\"> <md.mnem>smp</md.mnem> <md.text.id>01006760822E</md.text.id> <md.pub.tag.info> " +
            "<md.pub.tag>AN</md.pub.tag> <md.pub.tag>WL</md.pub.tag> </md.pub.tag.info> <md.source.tag>10</md.source.tag> <modified.by>TLE TCBA-BOT 10/28/2022</modified.by> " +
            "</metadata.block> <paratext>c.&emsp;Leave work early in order to take an exam, recooperate from illness, ensure mental stability, or any other reason at the" +
            " discretion of the Team Lead.</paratext> </para> </subsection> <subsection ID=\"IB58E292756DC11EDBDD690D66C2990F4\"> <metadata.block " +
            "owner=\"IB58E292756DC11EDBDD690D66C2990F4\"> <md.head.info> <md.label.designator>d</md.label.designator> </md.head.info> </metadata.block> " +
            "<para ID=\"I08EF77A41B7411DC868395DC84A0FC63\"> <metadata.block owner=\"I08EF77A41B7411DC868395DC84A0FC63\"> <md.mnem>smp</md.mnem> " +
            "<md.text.id>01006760823E</md.text.id> <md.pub.tag.info> <md.pub.tag>AN</md.pub.tag> <md.pub.tag>WL</md.pub.tag> </md.pub.tag.info> <md.source.tag>10" +
            "</md.source.tag> <modified.by>TLE TCBA-BOT 10/28/2022</modified.by> </metadata.block> <paratext>d.&emsp;Go away shortly for a lunch break. &ensp;Note " +
            "that this would probably be okay without asking your Team Lead, but you should at least tell them. Also this is just content for a Testing Document... " +
            "no need to take it seriously.</paratext> </para> </subsection> <subsection ID=\"IB58E292856DC11EDBDD690D66C2990F4\"> " +
            "<metadata.block owner=\"IB58E292856DC11EDBDD690D66C2990F4\"> <md.head.info> <md.label.designator>e</md.label.designator> </md.head.info> </metadata.block>" +
            "<para ID=\"I08EF77A51B7411DC868395DC84A0FC63\"> <metadata.block owner=\"I08EF77A51B7411DC868395DC84A0FC63\"> <md.mnem>smp</md.mnem>" +
            " <md.text.id>01006760824E</md.text.id> <md.pub.tag.info> <md.pub.tag>AN</md.pub.tag> <md.pub.tag>WL</md.pub.tag> </md.pub.tag.info> <md.source.tag>10</md.source.tag>" +
            " <modified.by>TLE TCBA-BOT 10/28/2022</modified.by> </metadata.block> <paratext>e.&emsp;Make a snowman in the parking lot if, and only if, it is " +
            "snowing and the snow is perfect for making a snowman.</paratext> </para> </subsection> <subsection ID=\"IB58E292956DC11EDBDD690D66C2990F4\"> " +
            "<metadata.block owner=\"IB58E292956DC11EDBDD690D66C2990F4\"> <md.head.info> <md.label.designator>f</md.label.designator> </md.head.info> </metadata.block> " +
            "<para ID=\"I08EF77A61B7411DC868395DC84A0FC63\"> <metadata.block owner=\"I08EF77A61B7411DC868395DC84A0FC63\"> <md.mnem>smp</md.mnem> <md.text.id>01006760825E" +
            "</md.text.id> <md.pub.tag.info> <md.pub.tag>AN</md.pub.tag> <md.pub.tag>WL</md.pub.tag> </md.pub.tag.info> <md.source.tag>10</md.source.tag> <modified.by>" +
            "TLE TCBA-BOT 10/28/2022</modified.by> </metadata.block> <paratext>f.&emsp;Send emails directly to CodesBench developers for clarification on how the different " +
            "parts of CodesBench work.</paratext> </para> </subsection> </subsection> <subsection ID=\"IB58E292A56DC11EDBDD690D66C2990F4\"> " +
            "<metadata.block owner=\"IB58E292A56DC11EDBDD690D66C2990F4\"> <md.head.info> <md.label.designator>3</md.label.designator> </md.head.info> </metadata.block>" +
            " <para ID=\"I08EF77A71B7411DC868395DC84A0FC63\"> <metadata.block owner=\"I08EF77A71B7411DC868395DC84A0FC63\"> <md.mnem>smp</md.mnem> <md.text.id>01006760826E" +
            "</md.text.id> <md.pub.tag.info> <md.pub.tag>AN</md.pub.tag> <md.pub.tag>WL</md.pub.tag> </md.pub.tag.info> <md.source.tag>10</md.source.tag> <modified.by>TLE " +
            "TCBA-BOT 03/03/2022</modified.by> </metadata.block> <paratext>3.&emsp;The QED Testing team shall keep their progress recorded in some way, most often in Excel " +
            "Spreadsheets.</paratext> </para> <para ID=\"IF11D98AA9AFB11ECB6FFE51D3DA29192\"> <metadata.block owner=\"IF11D98AA9AFB11ECB6FFE51D3DA29192\"> <md.mnem>smp</md.mnem> " +
            "<md.text.id>01006760826E</md.text.id> <md.pub.tag.info> <md.pub.tag>AN</md.pub.tag> <md.pub.tag>WL</md.pub.tag> </md.pub.tag.info> <md.source.tag>10</md.source.tag>" +
            " <modified.by>TLE TCBA-BOT 03/03/2022</modified.by> </metadata.block> <paratext>At this point, we need more text to make this document MEDIUM, thus, we will be inserting" +
            " a portion of the Public-Domain book, <ital>Frankenstein</ital>, by Mary Wollstonecraft (Godwin) Shelley/ </paratext> </para> " +
            "<para ID=\"IF11D989B9AFB11ECB6FFE51D3DA29192\"> <metadata.block owner=\"IF11D989B9AFB11ECB6FFE51D3DA29192\"> <md.mnem>smp</md.mnem> " +
            "<md.text.id>01006760826E</md.text.id> <md.pub.tag.info> <md.pub.tag>AN</md.pub.tag> <md.pub.tag>WL</md.pub.tag> </md.pub.tag.info> <md.source.tag>10" +
            "</md.source.tag> <modified.by>TLE TCBA-BOT 03/03/2022</modified.by> </metadata.block> <paratext> <bold>Letter 1:</bold> To Mrs. Saville, England. " +
            "St. Petersburgh, Dec. 11th, 17-. You will rejoice to hear that no disaster has accompanied the commencement of an enterprise which you have regarded with such evil " +
            "forebodings. I arrived here yesterday, and my first task is to assure my dear sister of my welfare and increasing confidence in the success of my undertaking. </paratext>" +
            " </para> <para ID=\"IF11D988D9AFB11ECB6FFE51D3DA29192\"> <metadata.block owner=\"IF11D988D9AFB11ECB6FFE51D3DA29192\"> <md.mnem>smp</md.mnem> <md.text.id>01006760826E" +
            "</md.text.id> <md.pub.tag.info> <md.pub.tag>AN</md.pub.tag> <md.pub.tag>WL</md.pub.tag> </md.pub.tag.info> <md.source.tag>10</md.source.tag> <modified.by>TLE TCBA-BOT " +
            "03/03/2022</modified.by> </metadata.block> <paratext>I am already far north of London, and as I walk in the streets of Petersburgh, I feel a cold northern breeze play " +
            "upon my cheeks, which braces my nerves and fills me with delight. Do you understand this feeling? This breeze, which has travelled from the regions towards which I am " +
            "advancing, gives me a foretaste of those icy climes. Inspirited by this wind of promise, my daydreams become more fervent and vivid. I try in vain to be persuaded that " +
            "the pole is the seat of frost and desolation; it ever presents itself to my imagination as the region of beauty and delight. There, Margaret, the sun is for ever visible, " +
            "its broad disk just skirting the horizon and diffusing a perpetual splendour. Therefore with your leave, my sister, I will put some trust in preceding navigators there " +
            "snow and frost are banished; and, sailing over a calm sea, we may be wafted to a land surpassing in wonders and in beauty every region hitherto discovered on the " +
            "habitable globe. Its productions and features may be without example, as the phenomena of the heavenly bodies undoubtedly are in those undiscovered solitudes. What " +
            "may not be expected in a country of eternal light? I may there discover the wondrous power which attracts the needle and may regulate a thousand celestial observations " +
            "that require only this voyage to render their seeming eccentricities consistent for ever. I shall satiate my ardent curiosity with the sight of a part of the world" +
            " never before visited, and may tread a land never before imprinted by the foot of man. These are my enticements, and they are sufficient to conquer all fear of danger" +
            " or death and to induce me to commence this laborious voyage with the joy a child feels when he embarks in a little boat, with his holiday mates, on an expedition of " +
            "discovery up his native river. But supposing all these conjectures to be false, you cannot contest the inestimable benefit which I shall confer on all mankind, to the" +
            " last generation, by discovering a passage near the pole to those countries, to reach which at present so many months are requisite; or by ascertaining the secret of " +
            "the magnet, which, if at all possible, can only be effected by an undertaking such as mine.</paratext> </para> <para ID=\"IF11D98809AFB11ECB6FFE51D3DA29192\"> " +
            "<metadata.block owner=\"IF11D98809AFB11ECB6FFE51D3DA29192\"> <md.mnem>smp</md.mnem> <md.text.id>01006760826E</md.text.id> <md.pub.tag.info> <md.pub.tag>AN</md.pub.tag>" +
            " <md.pub.tag>WL</md.pub.tag> </md.pub.tag.info> <md.source.tag>10</md.source.tag> <modified.by>TLE TCBA-BOT 03/03/2022</modified.by> </metadata.block> " +
            "<paratext>These reflections have dispelled the agitation with which I began my letter, and I feel my heart glow with an enthusiasm which elevates me to heaven, " +
            "for nothing contributes so much to tranquillise the mind as a steady purpose a point on which the soul may fix its intellectual eye. This expedition has been the " +
            "favourite dream of my early years. I have read with ardour the accounts of the various voyages which have been made in the prospect of arriving at the North Pacific" +
            " Ocean through the seas which surround the pole. You may remember that a history of all the voyages made for purposes of discovery composed the whole of our good Uncle " +
            "Thomas&rsquo; library. My education was neglected, yet I was passionately fond of reading. These volumes were my study day and night, and my familiarity with them incr" +
            "eased that regret which I had felt, as a child, on learning that my father&rsquo;s dying injunction had forbidden my uncle to allow me to embark in a seafaring life." +
            "</paratext> </para> <para ID=\"IF11D98739AFB11ECB6FFE51D3DA29192\"> <metadata.block owner=\"IF11D98739AFB11ECB6FFE51D3DA29192\"> <md.mnem>smp</md.mnem> <md.text.id>" +
            "01006760826E</md.text.id> <md.pub.tag.info> <md.pub.tag>AN</md.pub.tag> <md.pub.tag>WL</md.pub.tag> </md.pub.tag.info> <md.source.tag>10</md.source.tag> <modified.b" +
            "y>TLE TCBA-BOT 03/03/2022</modified.by> </metadata.block> <paratext>These visions faded when I perused, for the first time, those poets whose effusions entranced my" +
            " soul and lifted it to heaven. I also became a poet and for one year lived in a paradise of my own creation; I imagined that I also might obtain a niche in the temp" +
            "le where the names of Homer and Shakespeare are consecrated. You are well acquainted with my failure and how heavily I bore the disappointment. But just at that tim" +
            "e I inherited the fortune of my cousin, and my thoughts were turned into the channel of their earlier bent.</paratext> </para> <para ID=\"IF11D98669AFB11ECB6FFE51D3" +
            "DA29192\"> <metadata.block owner=\"IF11D98669AFB11ECB6FFE51D3DA29192\"> <md.mnem>smp</md.mnem> <md.text.id>01006760826E</md.text.id> <md.pub.tag.info> <md.pub.tag>A" +
            "N</md.pub.tag> <md.pub.tag>WL</md.pub.tag> </md.pub.tag.info> <md.source.tag>10</md.source.tag> <modified.by>TLE TCBA-BOT 03/03/2022</modified.by> </metadata.block>" +
            " <paratext>Six years have passed since I resolved on my present undertaking. I can, even now, remember the hour from which I dedicated myself to this great enterpri" +
            "se. I commenced by inuring my body to hardship. I accompanied the whale-fishers on several expeditions to the North Sea; I voluntarily endured cold, famine, thirst," +
            " and want of sleep; I often worked harder than the common sailors during the day and devoted my nights to the study of mathematics, the theory of medicine, and thos" +
            "e branches of physical science from which a naval adventurer might derive the greatest practical advantage. Twice I actually hired myself as an under-mate in a Gree" +
            "nland whaler, and acquitted myself to admiration. I must own I felt a little proud when my captain offered me the second dignity in the vessel and entreated me to r" +
            "emain with the greatest earnestness, so valuable did he consider my services.</paratext> </para> <para ID=\"IF11D98599AFB11ECB6FFE51D3DA29192\"> " +
            "<metadata.block owner=\"IF11D98599AFB11ECB6FFE51D3DA29192\"> <md.mnem>smp</md.mnem> <md.text.id>01006760826E</md.text.id> <md.pub.tag.info> <md.pub.tag>" +
            "AN</md.pub.tag> <md.pub.tag>WL</md.pub.tag> </md.pub.tag.info> <md.source.tag>10</md.source.tag> <modified.by>TLE TCBA-BOT 03/03/2022</modified.by> " +
            "</metadata.block> <paratext>And now, dear Margaret, do I not deserve to accomplish some great purpose? My life might have been passed in ease and luxury, " +
            "but I preferred glory to every enticement that wealth placed in my path. Oh, that some encouraging voice would answer in the affirmative! My courage and my " +
            "resolution is firm; but my hopes fluctuate, and my spirits are often depressed. I am about to proceed on a long and difficult voyage, the emergencies of which " +
            "will demand all my fortitude: I am required not only to raise the spirits of others, but sometimes to sustain my own, when theirs are failing.</paratext> </para>" +
            " <para ID=\"IF11D984C9AFB11ECB6FFE51D3DA29192\"> <metadata.block owner=\"IF11D984C9AFB11ECB6FFE51D3DA29192\"> <md.mnem>smp</md.mnem> <md.text.id>01006760826E" +
            "</md.text.id> <md.pub.tag.info> <md.pub.tag>AN</md.pub.tag> <md.pub.tag>WL</md.pub.tag> </md.pub.tag.info> <md.source.tag>10</md.source.tag> <modified.by>TLE TCBA-BOT" +
            " 03/03/2022</modified.by> </metadata.block> <paratext>This is the most favourable period for travelling in Russia. They fly quickly over the snow in their sledges; " +
            "the motion is pleasant, and, in my opinion, far more agreeable than that of an English stagecoach. The cold is not excessive, if you are wrapped in furs&mdash;a dress " +
            "which I have already adopted, for there is a great difference between walking the deck and remaining seated motionless for hours, when no exercise prevents the blood " +
            "from actually freezing in your veins. I have no ambition to lose my life on the post-road between St. Petersburgh and Archangel.</paratext> </para> " +
            "<para ID=\"IF11D983F9AFB11ECB6FFE51D3DA29192\"> <metadata.block owner=\"IF11D983F9AFB11ECB6FFE51D3DA29192\"> <md.mnem>smp</md.mnem> <md.text.id>01006760826E</md.text.id>" +
            " <md.pub.tag.info> <md.pub.tag>AN</md.pub.tag> <md.pub.tag>WL</md.pub.tag> </md.pub.tag.info> <md.source.tag>10</md.source.tag> " +
            "<modified.by>TLE TCBA-BOT 03/03/2022</modified.by> </metadata.block> <paratext>I shall depart for the latter town in a fortnight or three weeks; " +
            "and my intention is to hire a ship there, which can easily be done by paying the insurance for the owner, and to engage as many sailors as I think nec" +
            "essary among those who are accustomed to the whale-fishing. I do not intend to sail until the month of June; and when shall I return? Ah, dear sister, " +
            "how can I answer this question? If I succeed, many, many months, perhaps years, will pass before you and I may meet. If I fail, you will see me again s" +
            "oon, or never.</paratext> </para> <para ID=\"IF11D98319AFB11ECB6FFE51D3DA29192\"> <metadata.block owner=\"IF11D98319AFB11ECB6FFE51D3DA29192\"> <md.mnem" +
            ">smp</md.mnem> <md.text.id>01006760826E</md.text.id> <md.pub.tag.info> <md.pub.tag>AN</md.pub.tag> <md.pub.tag>WL</md.pub.tag> </md.pub.tag.info> <md.s" +
            "ource.tag>10</md.source.tag> <modified.by>TLE TCBA-BOT 03/03/2022</modified.by> </metadata.block> <paratext>Farewell, my dear, excellent Margaret. " +
            " shower down blessings on you, and save me, that I may again and again testify my gratitude for all your love and kindness. -- Your affectionate brother" +
            ", R. Walton</paratext> </para> </subsection> </body> <placeholder ID=\"I08F544001B7411DC868395DC84A0FC63\"> <metadata.block owner=\"I08F544001B7411DC868" +
            "395DC84A0FC63\"> <md.mnem>eot</md.mnem> <md.text.id>01006760828</md.text.id> <md.pub.tag.info> <md.pub.tag>AN</md.pub.tag> <md.pub.tag>WL</md.pub.tag> <" +
            "/md.pub.tag.info> <md.source.tag>IN</md.source.tag> </metadata.block> <placeholder.text>&emsp;</placeholder.text> </placeholder> <annotations> <hist.not" +
            "e.block> <codes.head ID=\"I08F5B9301B7411DC868395DC84A0FC63\"> <metadata.block owner=\"I08F5B9301B7411DC868395DC84A0FC63\"> <md.mnem>xhsw</md.mnem> " +
            "<md.text.id>01006760829</md.text.id> <md.pub.tag.info> <md.pub.tag>AN</md.pub.tag> <md.pub.tag>WL</md.pub.tag> </md.pub.tag.info> <md.source.tag>01" +
            "</md.source.tag> </metadata.block> <head.info> <headtext>&emsp;</headtext> </head.info> </codes.head> <hist.note.body> <codes.head ID=\"I08F5B9311B74" +
            "11DC868395DC84A0FC63\"> <metadata.block owner=\"I08F5B9311B7411DC868395DC84A0FC63\"> <md.mnem>gnpc</md.mnem> <md.text.id>01006760832</md.text.id> <md" +
            ".pub.tag.info> <md.pub.tag>AN</md.pub.tag> <md.pub.tag>WL</md.pub.tag> </md.pub.tag.info> <md.source.tag>01</md.source.tag> </metadata.block> <head.i" +
            "nfo> <headtext>2001 Legislation</headtext> </head.info> </codes.head> <hist.note type=\"Amendment\"> <para ID=\"I08F5B9321B7411DC868395DC84A0FC63\"> " +
            "<metadata.block owner=\"I08F5B9321B7411DC868395DC84A0FC63\"> <md.mnem>gnp</md.mnem> <md.text.id>01007317726</md.text.id> <md.pub.tag.info> <md.pub.ta" +
            "g>AN</md.pub.tag> <md.pub.tag>WL</md.pub.tag> </md.pub.tag.info> <md.source.tag>01</md.source.tag> </metadata.block> <paratext>Acts 2001 (79 G.A.) 2n" +
            "d Ex.Sess, ch. 2, &sect;&ensp;1,, in subsec. 1, deleted par. c and relettered pars. d and e as pars. c and d. The deleted par. c had read:</paratext>" +
            " </para> <para ID=\"I08F5B9331B7411DC868395DC84A0FC63\"> <metadata.block owner=\"I08F5B9331B7411DC868395DC84A0FC63\"> <md.mnem>gnp</md.mnem> <md.text" +
            ".id>01006760834</md.text.id> <md.pub.tag.info> <md.pub.tag>AN</md.pub.tag> <md.pub.tag>WL</md.pub.tag> </md.pub.tag.info> <md.source.tag>01</md.sourc" +
            "e.tag> </metadata.block> <paratext>&ldquo;c.&emsp;Receive the five-year capital project priority plan for all state agencies, pursuant to section 8.6" +
            ", subsection 14.&rdquo;</paratext> </para> </hist.note> <hist.note type=\"Law.Note\"> <para ID=\"I08F5B9341B7411DC868395DC84A0FC63\"> <metadata.block" +
            " owner=\"I08F5B9341B7411DC868395DC84A0FC63\"> <md.mnem>gnpq</md.mnem> <md.text.id>01006870815</md.text.id> <md.pub.tag.info> <md.pub.tag>AN</md.pub.t" +
            "ag> <md.pub.tag>WL</md.pub.tag> </md.pub.tag.info> <md.source.tag>01</md.source.tag> </metadata.block> <paratext>Acts 2001 (79 G.A.) 2nd Ex.Sess., ch" +
            ". 2, &sect;&ensp;13, provides:</paratext> </para> <para ID=\"I08F5E0401B7411DC868395DC84A0FC63\"> <metadata.block owner=\"I08F5E0401B7411DC868395DC84" +
            "A0FC63\"> <md.mnem>gnpq</md.mnem> <md.text.id>01006870816</md.text.id> <md.pub.tag.info> <md.pub.tag>AN</md.pub.tag> <md.pub.tag>WL</md.pub.tag> </md" +
            ".pub.tag.info> <md.source.tag>01</md.source.tag> </metadata.block> <paratext>&ldquo;Effective date&mdash;applicability. This Act, being deemed of imm" +
            "ediate importance, takes effect upon enactment. &ensp;Sections 1 through 5, 7, 8, 10, and 11 are first applicable to the budget and appropriations ma" +
            "de for the fiscal year beginning July 1, 2002, and ending June 30, 2003.&rdquo;</paratext> </para> </hist.note> </hist.note.body> <hist.note.body> <c" +
            "odes.head ID=\"I08F5E0411B7411DC868395DC84A0FC63\"> <metadata.block owner=\"I08F5E0411B7411DC868395DC84A0FC63\"> <md.mnem>gnpc</md.mnem> <md.text.id>" +
            "01006760835</md.text.id> <md.pub.tag.info> <md.pub.tag>AN</md.pub.tag> <md.pub.tag>WL</md.pub.tag> </md.pub.tag.info> <md.source.tag>03</md.source.ta" +
            "g> </metadata.block> <head.info> <headtext>2003 Legislation</headtext> </head.info> </codes.head> <hist.note type=\"Amendment\"> <para ID=\"I08F5E042" +
            "1B7411DC868395DC84A0FC63\"> <metadata.block owner=\"I08F5E0421B7411DC868395DC84A0FC63\"> <md.mnem>gnp</md.mnem> <md.text.id>01007317727</md.text.id> " +
            "<md.pub.tag.info> <md.pub.tag>AN</md.pub.tag> <md.pub.tag>WL</md.pub.tag> </md.pub.tag.info> <md.source.tag>03</md.source.tag> </metadata.block> <par" +
            "atext>Acts 2003 (80 G.A.) ch. 145, &sect;&ensp;105, in subsec. 1, par. c, substituted &ldquo;8A.321, subsection 10&rdquo; for &ldquo;18.12, subsectio" +
            "n 14&rdquo;.</paratext> </para> </hist.note> </hist.note.body> <hist.note.body> <codes.head ID=\"I08F5E0431B7411DC868395DC84A0FC63\"> <metadata.block" +
            " owner=\"I08F5E0431B7411DC868395DC84A0FC63\"> <md.mnem>gnpc</md.mnem> <md.text.id>01007220115</md.text.id> <md.pub.tag.info> <md.pub.tag>AN</md.pub.t" +
            "ag> <md.pub.tag>WL</md.pub.tag> </md.pub.tag.info> <md.source.tag>06</md.source.tag> </metadata.block> <head.info> <headtext>2006 Legislation</headte" +
            "xt> </head.info> </codes.head> <hist.note type=\"Revisor\"> <para ID=\"I08F5E0441B7411DC868395DC84A0FC63\"> <metadata.block owner=\"I08F5E0441B7411DC" +
            "868395DC84A0FC63\"> <md.mnem>gnp</md.mnem> <md.text.id>01007220116</md.text.id> <md.pub.tag.info> <md.pub.tag>AN</md.pub.tag> <md.pub.tag>WL</md.pub." +
            "tag> </md.pub.tag.info> <md.source.tag>06</md.source.tag> </metadata.block> <paratext>The Code Editor for Code 2007, in subsec. 1, par. c, substitute" +
            "d &ldquo;subsection 11&rdquo; for &ldquo;subsection 10&rdquo;.</paratext> </para> </hist.note> </hist.note.body> <hist.note.body> <codes.head ID=\"I0" +
            "8F5E0451B7411DC868395DC84A0FC63\"> <metadata.block owner=\"I08F5E0451B7411DC868395DC84A0FC63\"> <md.mnem>gnpc</md.mnem> <md.text.id>00000000000</md.t" +
            "ext.id> <md.pub.tag.info> <md.pub.tag>AN</md.pub.tag> <md.pub.tag>WL</md.pub.tag> </md.pub.tag.info> <md.source.tag>07</md.source.tag> </metadata.blo" +
            "ck> <head.info> <headtext>2007 Legislation</headtext> </head.info> </codes.head> <hist.note type=\"Amendment\"> <para ID=\"I08F5E0461B7411DC868395DC8" +
            "4A0FC63\"> <metadata.block owner=\"I08F5E0461B7411DC868395DC84A0FC63\"> <md.mnem>gnp</md.mnem> <md.text.id>00000000000</md.text.id> <md.pub.tag.info>" +
            " <md.pub.tag>AN</md.pub.tag> <md.pub.tag>WL</md.pub.tag> </md.pub.tag.info> <md.source.tag>07</md.source.tag> </metadata.block> <paratext>Acts 2007 (" +
            "82 G.A.) H.F. 849, &sect;&ensp;1, in subsec. 1, in par. c, deleted from the end of the paragraph, &ldquo;, pursuant to section 8A.321 subsection 11.&" +
            "rdquo;</paratext> </para> </hist.note> </hist.note.body> <hist.note.body> <hist.note type=\"Amendment\"> <para ID=\"I08F5E0471B7411DC868395DC84A0FC63" +
            "\"> <metadata.block owner=\"I08F5E0471B7411DC868395DC84A0FC63\"> <md.mnem>gnp</md.mnem> <md.text.id>01006760837</md.text.id> <md.pub.tag.info> <md.pu" +
            "b.tag>AN</md.pub.tag> <md.pub.tag>WL</md.pub.tag> </md.pub.tag.info> <md.source.tag>MV-90</md.source.tag> </metadata.block> <paratext>The 1990 amendm" +
            "ent by ch. 1168 made statutory corrections of a noncontroversial and nonsubstantive nature in subsec. 2, par. a.</paratext> </para> </hist.note> <his" +
            "t.note type=\"Amendment\"> <para ID=\"I08F5E0481B7411DC868395DC84A0FC63\"> <metadata.block owner=\"I08F5E0481B7411DC868395DC84A0FC63\"> <md.mnem>gnp<" +
            "/md.mnem> <md.text.id>01006760838</md.text.id> <md.pub.tag.info> <md.pub.tag>AN</md.pub.tag> <md.pub.tag>WL</md.pub.tag> </md.pub.tag.info> <md.sourc" +
            "e.tag>MV-91</md.source.tag> </metadata.block> <paratext>The 1991 amendment, in subsec. 1, par. d, substituted &ldquo;semiannual status reports&rdquo;" +
            " for &ldquo;quarterly status reports&rdquo;.</paratext> </para> </hist.note> <hist.note type=\"Amendment\"> <para ID=\"I08F5E0491B7411DC868395DC84A0F" +
            "C63\"> <metadata.block owner=\"I08F5E0491B7411DC868395DC84A0FC63\"> <md.mnem>gnp</md.mnem> <md.text.id>01006760839</md.text.id> <md.pub.tag.info> <md" +
            ".pub.tag>AN</md.pub.tag> <md.pub.tag>WL</md.pub.tag> </md.pub.tag.info> <md.source.tag>MV-95</md.source.tag> </metadata.block> <paratext>The 1995 ame" +
            "ndment, in par. d of subsec. 1, substituted &ldquo;annual&rdquo; for &ldquo;semiannual&rdquo;.</paratext> </para> </hist.note> </hist.note.body> </hi" +
            "st.note.block> <library.reference.block> <codes.head ID=\"I08F766E01B7411DC868395DC84A0FC63\"> <metadata.block owner=\"I08F766E01B7411DC868395DC84A0F" +
            "C63\"> <md.mnem>xlr</md.mnem> <md.text.id>01006760840</md.text.id> <md.pub.tag.info> <md.pub.tag>AN</md.pub.tag> <md.pub.tag>WL</md.pub.tag> </md.pub" +
            ".tag.info> <md.source.tag>MV-01</md.source.tag> </metadata.block> <head.info> <headtext>&emsp;</headtext> </head.info> </codes.head> <library.referen" +
            "ce.body> <library.reference type=\"Key.Number\" ID=\"I08F766E11B7411DC868395DC84A0FC63\" category=\"Key.Number\"> <metadata.block owner=\"I08F766E11B" +
            "7411DC868395DC84A0FC63\"> <md.mnem>key</md.mnem> <md.text.id>01006760841</md.text.id> <md.pub.tag.info> <md.pub.tag>AN</md.pub.tag> <md.pub.tag>WL</m" +
            "d.pub.tag> </md.pub.tag.info> <md.source.tag>MV-01</md.source.tag> </metadata.block> <reference.text> <ref.cite>States &TLRkey;34.</ref.cite> </refer" +
            "ence.text> </library.reference> <library.reference type=\"WL.Topic.Number\" ID=\"I08F78DF01B7411DC868395DC84A0FC63\" category=\"Key.Number\"> <metada" +
            "ta.block owner=\"I08F78DF01B7411DC868395DC84A0FC63\"> <md.mnem>wlt</md.mnem> <md.text.id>01006760842</md.text.id> <md.pub.tag.info> <md.pub.tag>AN</m" +
            "d.pub.tag> <md.pub.tag>WL</md.pub.tag> </md.pub.tag.info> <md.source.tag>MV-01</md.source.tag> </metadata.block> <reference.text> <ref.cite>Westlaw T" +
            "opic No. 360.</ref.cite> </reference.text> </library.reference> <library.reference type=\"CJS\" ID=\"I08F78DF11B7411DC868395DC84A0FC63\" category=\"E" +
            "ncyclopedias\"> <metadata.block owner=\"I08F78DF11B7411DC868395DC84A0FC63\"> <md.mnem>cjs</md.mnem> <md.text.id>01006760843</md.text.id> <md.pub.tag." +
            "info> <md.pub.tag>AN</md.pub.tag> <md.pub.tag>WL</md.pub.tag> </md.pub.tag.info> <md.source.tag>MV-01</md.source.tag> </metadata.block> <reference.te" +
            "xt> <ref.cite>C.J.S. States &sect;&sect;&ensp;55 to 58.</ref.cite> </reference.text> </library.reference> </library.reference.body> </library.referen" +
            "ce.block> </annotations> </section>";

    public static String textForFootnoteDeleteTest = "<section ID=\"IE586C730805A11ECA6EEDA0C9C0572FA\" legacy.identifier=\"992122421\" type=\"Live\"> <cornerpiece" +
            " ID=\"I60A068B4805A11ECB5C9ECCC254832C7\" type=\"cp\"> <metadata.block owner=\"I60A068B4805A11ECB5C9ECCC254832C7\"> <md.mnem>cp</md.mnem> <md.pub.tag." +
            "info> <md.pub.tag>AN</md.pub.tag> <md.pub.tag>WL</md.pub.tag> </md.pub.tag.info> <md.source.tag>10</md.source.tag> <modified.by>TLE TCBA-BOT 12/08/202" +
            "2</modified.by> </metadata.block> <cornerpiece.text>&sect;&ensp;999.990</cornerpiece.text> </cornerpiece> <codes.head ID=\"I0D3B6C89805B11ECB5C9ECCC25" +
            "4832C7\"> <metadata.block owner=\"I0D3B6C89805B11ECB5C9ECCC254832C7\"> <md.mnem>snl</md.mnem> <md.pub.tag.info> <md.pub.tag>AN</md.pub.tag> <md.pub.ta" +
            "g>WL</md.pub.tag> </md.pub.tag.info> <md.source.tag>10</md.source.tag> <md.head.info> <md.level>15</md.level> </md.head.info> <modified.by>TLE TCBA-BO" +
            "T 12/08/2022</modified.by> </metadata.block> <head.info> <label.designator>999.990</label.designator>.&emsp; <headtext>QED Small Section</headtext> </" +
            "head.info> </codes.head> <body> <para ID=\"I0D3B6C64805B11ECB5C9ECCC254832C7\"> <metadata.block owner=\"I0D3B6C64805B11ECB5C9ECCC254832C7\"> <md.mnem>" +
            "smp</md.mnem> <md.pub.tag.info> <md.pub.tag>AN</md.pub.tag> <md.pub.tag>WL</md.pub.tag> </md.pub.tag.info> <md.source.tag>10</md.source.tag> <modified" +
            ".by>TLE TCBA-BOT 12/08/2022</modified.by> </metadata.block> <paratext>This document is only for use by the QED Testing <footnote.reference refid=\"I18" +
            "E07E81772E11ED9CE6D090568E0E2F\"> <super style=\"percent_r\">TestRef</super> </footnote.reference>Team. <bold> <ital>IT IS NOT LEGAL CONTENT OR LEGAL " +
            "ADVICE!</ital> </bold> THIS TEXT WAS ADDED FROM AUDIT DATA MOCKING </paratext> </para> <footnote ID=\"I18E07E81772E11ED9CE6D090568E0E2F\"> <para ID=\"" +
            "IEB957EFE772D11ED9CE6D090568E0E2F\"> <metadata.block owner=\"IEB957EFE772D11ED9CE6D090568E0E2F\"> <md.mnem>fp</md.mnem> <md.pub.tag.info> <md.pub.tag>" +
            "AN</md.pub.tag> <md.pub.tag>WL</md.pub.tag> </md.pub.tag.info> <md.source.tag>10</md.source.tag> <modified.by>TLE TCBA-BOT 12/08/2022</modified.by> </" +
            "metadata.block> <paratext> <super style=\"percent_r\">TestRef</super>&thinsp;This is a test footnote </paratext> </para> </footnote> </body> <placehol" +
            "der ID=\"I45D2878331BF11D99E6BEE4CC5FFF826\"> <metadata.block owner=\"I45D2878331BF11D99E6BEE4CC5FFF826\"> <md.mnem>eot</md.mnem> <md.pub.tag.info> <m" +
            "d.pub.tag>AN</md.pub.tag> <md.pub.tag>WL</md.pub.tag> </md.pub.tag.info> <md.source.tag>06</md.source.tag> <modified.by>TLE TCBA-BOT 12/08/2022</modif" +
            "ied.by> </metadata.block> <placeholder.text>&emsp;</placeholder.text> </placeholder> </section>";
}
