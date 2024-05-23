package com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.source.queries;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.source.SourceDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.CommonDataMocking;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.DataMocking;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.source.SourceDataMockingInterface;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.source.SourceDataMockingNew;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.source.SourceDocument;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.source.datapod.SourceDatapodObject;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import org.junit.jupiter.api.Assertions;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;

import static com.thomsonreuters.codes.codesbench.quality.utilities.TestSetupEdge.logger;
import static com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils.commit;

public class SourceDataMockingQueries
{
    private static String renditionUuid = "";

    /**
     * @deprecated Deprecated in favor of SourceDataMockingNew
     */
    public static class Iowa
    {
        static String contentSetId = "106";

        /**
         * @deprecated Deprecated in favor of SourceDataMockingNew
         */
        public static class PREP
        {
            static int renditionStatus = 302;

            /*
        1 chunk of data, a few paragraphs, nothing else
            */
            /**
             * @deprecated Deprecated in favor of SourceDataMockingNew
             */
            public static class Small extends CommonDataMocking implements SourceDataMockingInterface
            {
                public String insertReturnRenditionUuid(String environment)
                {
                    SourceDataMockingQueries rendition = new SourceDataMockingQueries();
                    long docNumber = SourceDataMockingNew.generateDocNumber();
                    Connection connection = CommonDataMocking.connectToDatabase(environment);

                    String lineageUuid = rendition.insertSrcLineage(connection, docNumber, Iowa.contentSetId);
                    renditionUuid = rendition.insertSrcRendition(connection, lineageUuid, PREP.renditionStatus, docNumber, 2020, 6);
                    rendition.insertRendProcProps(connection, renditionUuid);

                    String sectionUuid = rendition.insertSection(connection, renditionUuid);
                    rendition.insertSectionProcProps(connection, sectionUuid);

                    String deltaUuid = rendition.insertDelta(connection, sectionUuid, renditionUuid);
                    rendition.insertDeltaProcProps(connection, deltaUuid);

                    rendition.insertInitialBaseline(connection, renditionUuid, deltaUuid);

                    return renditionUuid;
                }

                @Override
                public SourceDocument insertReturnRenditionObject(String environment)
                {
                    return null;
                }

                @Override
                public ArrayList<String> insertReturnRenditionUuidList(String environment, int numOfRenditions)
                {
                    return null;
                }

                public boolean delete(String environment)
                {
                    SourceDataMockingQueries rendition = new SourceDataMockingQueries();
                    Connection connection = CommonDataMocking.connectToDatabase(environment);

                    String lineageUuid = SourceDatabaseUtils.getLineageUuidFromRenditionUuid(connection, renditionUuid);
                    rendition.deleteRenditionWarningFlag(connection, renditionUuid);
                    rendition.deleteSourceFrontParagraphs(connection, renditionUuid);
                    rendition.deleteRenditionBaselines(connection, renditionUuid);
                    rendition.deleteDeltaParagraph(connection, renditionUuid);
                    rendition.deleteDeltaProcProps(connection, renditionUuid);
                    rendition.deleteTargetLocation(connection, renditionUuid);
                    rendition.deleteDeltasAndSectionParagraphs(connection, renditionUuid);
                    rendition.deleteSectionProcProps(connection, renditionUuid);
                    rendition.deleteSection(connection, renditionUuid);
                    rendition.deleteRendProcProps(connection, renditionUuid);
                    rendition.deleteSrcRendition(connection, renditionUuid);
                    rendition.deleteSrcLineage(connection, lineageUuid);
                    commit(connection);
                    return true;
                }

                public boolean deleteBasedOnRenditionUuid(String environment, String rendUuid)
                {
                    SourceDataMockingQueries rendition = new SourceDataMockingQueries();
                    Connection connection = CommonDataMocking.connectToDatabase(environment);

                    rendition.deleteRenditionWarningFlag(connection, rendUuid);
                    rendition.deleteSourceFrontParagraphs(connection, renditionUuid);
                    rendition.deleteRenditionBaselines(connection, rendUuid);
                    rendition.deleteDeltaParagraph(connection, rendUuid);
                    rendition.deleteDeltaProcProps(connection, rendUuid);
                    rendition.deleteTargetLocation(connection, rendUuid);
                    rendition.deleteDeltasAndSectionParagraphs(connection, rendUuid);
                    rendition.deleteSectionProcProps(connection, rendUuid);
                    rendition.deleteSection(connection, rendUuid);
                    rendition.deleteRendProcProps(connection, rendUuid);
                    rendition.deleteSrcRendition(connection, rendUuid);
                    commit(connection);
                    return true;
                }
            }

            /*
                5 chunks of data, a bunch of paragraphs
            */
            public static class Medium extends CommonDataMocking
            {
                public String insert(String environment)
                {
                    return null;
                }

                public boolean delete(String environment)
                {
                    return false;
                }
            }

            /*
                10 chunks of data, a bunch of paragraphs
            */
            public static class Large extends CommonDataMocking
            {
                public String insert(String environment)
                {
                    return null;
                }

                public boolean delete(String environment)
                {
                    return false;
                }
            }
        }

        /**
         * @deprecated Deprecated in favor of SourceDataMockingNew
         */
        public static class APV
        {
            static int renditionStatus = 303;

            /**
             * @deprecated Deprecated in favor of SourceDataMockingNew
             */
            public static class Small extends CommonDataMocking implements SourceDataMockingInterface
            {
                public String insertReturnRenditionUuid(String environment)
                {
                    SourceDataMockingQueries rendition = new SourceDataMockingQueries();
                    long docNumber = SourceDataMockingNew.generateDocNumber();
                    Connection connection = CommonDataMocking.connectToDatabase(environment);

                    String lineageUuid = rendition.insertSrcLineage(connection, docNumber, Iowa.contentSetId);
                    renditionUuid = rendition.insertSrcRendition(connection, lineageUuid, APV.renditionStatus, docNumber, 2020, 6);
                    rendition.insertRendProcProps(connection, renditionUuid);

                    String sectionUuid = rendition.insertSection(connection, renditionUuid);
                    rendition.insertSectionProcProps(connection, sectionUuid);

                    String deltaUuid = rendition.insertDelta(connection, sectionUuid, renditionUuid);
                    rendition.insertDeltaProcProps(connection, deltaUuid);
                    rendition.insertDeltaTargetLocation(connection, deltaUuid);

                    rendition.insertInitialBaseline(connection, renditionUuid, deltaUuid);
                    rendition.insertBaseline(connection, renditionUuid, "c268906");

                    return renditionUuid;
                }

                @Override
                public SourceDocument insertReturnRenditionObject(String environment)
                {
                    SourceDocument rendition = new SourceDocument(environment);
                    rendition.renditionStatus = renditionStatus;
                    Connection connection = CommonDataMocking.connectToDatabase(environment);

                    rendition.lineageUuid = rendition.insertSrcLineage(connection, rendition.docNumber, Iowa.contentSetId);
                    rendition.renditionUuid = rendition.insertSrcRendition(connection, rendition.lineageUuid, APV.renditionStatus, rendition.docNumber, 2020, 6);
                    rendition.insertRendProcProps(connection, rendition.renditionUuid);

                    rendition.sectionUuid = rendition.insertSection(connection, rendition.renditionUuid);
                    rendition.insertSectionProcProps(connection, rendition.sectionUuid);

                    rendition.deltaUuid = rendition.insertDelta(connection, rendition.sectionUuid, rendition.renditionUuid);
                    rendition.insertDeltaProcProps(connection, rendition.deltaUuid);
                    rendition.insertDeltaTargetLocation(connection, rendition.deltaUuid);

                    rendition.insertInitialBaseline(connection, rendition.renditionUuid, rendition.deltaUuid);

                    return rendition;
                }

                @Override
                public ArrayList<String> insertReturnRenditionUuidList(String environment, int numOfRenditions)
                {
                    return null;
                }

                public boolean delete(String environment)
                {
                    SourceDataMockingQueries rendition = new SourceDataMockingQueries();
                    Connection connection = CommonDataMocking.connectToDatabase(environment);

                    String lineageUuid = SourceDatabaseUtils.getLineageUuidFromRenditionUuid(connection, renditionUuid);
                    rendition.deleteRenditionWarningFlag(connection, renditionUuid);
                    rendition.deleteSourceFrontParagraphs(connection, renditionUuid);
                    rendition.deleteRenditionBaselines(connection, renditionUuid);
                    rendition.deleteDeltaParagraph(connection, renditionUuid);
                    rendition.deleteDeltaProcProps(connection, renditionUuid);
                    rendition.deleteTargetLocation(connection, renditionUuid);
                    rendition.deleteDeltasAndSectionParagraphs(connection, renditionUuid);
                    rendition.deleteSectionProcProps(connection, renditionUuid);
                    rendition.deleteSection(connection, renditionUuid);
                    rendition.deleteRendProcProps(connection, renditionUuid);
                    rendition.deleteSrcRendition(connection, renditionUuid);
                    rendition.deleteSrcLineage(connection, lineageUuid);
                    commit(connection);
                    return true;
                }

                public boolean deleteBasedOnRenditionUuid(String environment, String rendUuid)
                {
                    SourceDataMockingQueries rendition = new SourceDataMockingQueries();
                    Connection connection = CommonDataMocking.connectToDatabase(environment);

                    rendition.deleteRenditionWarningFlag(connection, rendUuid);
                    rendition.deleteSourceFrontParagraphs(connection, rendUuid);
                    rendition.deleteRenditionBaselines(connection, rendUuid);
                    rendition.deleteDeltaParagraph(connection, rendUuid);
                    rendition.deleteDeltaProcProps(connection, rendUuid);
                    rendition.deleteTargetLocation(connection, rendUuid);
                    rendition.deleteDeltasAndSectionParagraphs(connection, rendUuid);
                    rendition.deleteSectionProcProps(connection, rendUuid);
                    rendition.deleteSection(connection, rendUuid);
                    rendition.deleteRendProcProps(connection, rendUuid);
                    rendition.deleteSrcRendition(connection, rendUuid);
                    commit(connection);
                    return true;
                }
            }

            /*
                5 chunks of data, a bunch of paragraphs
            */
            /**
             * @deprecated Deprecated in favor of SourceDataMockingNew
             */
            public static class Medium extends CommonDataMocking
            {
                public String insert(String environment)
                {
                    return null;
                }

                public boolean delete(String environment)
                {
                    return false;
                }
            }

            /*
                10 chunks of data, a bunch of paragraphs
            */
            /**
             * @deprecated Deprecated in favor of SourceDataMockingNew
             */
            public static class Large extends CommonDataMocking
            {
                public String insert(String environment)
                {
                    return null;
                }

                public boolean delete(String environment)
                {
                    return false;
                }
            }
        }
    }

    public String insertSrcLineage(Connection connection, Long docNumber, String contentSetId)
    {
        String lineageUuid = CommonDataMocking.generateUUID();

        try
        {
            logger.information("Inserting lineage");
            PreparedStatement insertLineageStatement = connection.prepareStatement(SourceDataMockingConstants.INSERT_INTO_SRC_LINEAGE);
            insertLineageStatement.setString(1, lineageUuid);
            insertLineageStatement.setInt(2, Integer.parseInt(contentSetId));
            insertLineageStatement.setLong(3, docNumber);
            insertLineageStatement.setString(4, CommonDataMocking.generateUUID());
            insertLineageStatement.executeUpdate();
            commit(connection);
            if (insertLineageStatement != null) {
                insertLineageStatement.close();
            }
        }
        catch (SQLException e)
        {
            Assertions.fail(String.format("Inserting data into the lineage grid failed: %s", e.toString()));
        }

        return lineageUuid;
    }

    public String insertSrcRendition(Connection connection, String lineageUuid, int renditionStatus, Long docNumber, int year, int contentTypeId)
    {
        String renditionUuid = CommonDataMocking.generateUUID();

        try
        {
            logger.information("Inserting rendition " + renditionUuid);
            PreparedStatement insertRenditionStatement = connection.prepareStatement(SourceDataMockingConstants.INSERT_INTO_SRC_RENDITION);
            insertRenditionStatement.setString(1, renditionUuid);
            insertRenditionStatement.setString(2, lineageUuid);
            insertRenditionStatement.setInt(3, year);
            insertRenditionStatement.setInt(4, renditionStatus);
            insertRenditionStatement.setString(5, CommonDataMocking.generateUUID());
            insertRenditionStatement.setInt(6, Integer.parseInt(DateAndTimeUtils.getCurrentDateAndTimeyyyyMMddHHmmss().substring(4, 13)));
            insertRenditionStatement.setString(7, CommonDataMocking.generateUUID());
            insertRenditionStatement.setInt(8, contentTypeId);
            insertRenditionStatement.setLong(9, docNumber);
            insertRenditionStatement.executeUpdate();
            commit(connection);
        }
        catch (SQLException e)
        {
            Assertions.fail(String.format("Inserting data into the rendition grid failed: %s", e.toString()));
        }

        return renditionUuid;
    }

    public String insertRendProcProps(Connection connection, String renditionUuid)
    {
        String procPropsUuid = CommonDataMocking.generateUUID();
        try
        {
            logger.information("Inserting into CWB_REND_PROC_PROPS");
            PreparedStatement insertRendProcPropsStatement = connection.prepareStatement(SourceDataMockingConstants.INSERT_INTO_SRC_REND_PROC_PROPS);
            insertRendProcPropsStatement.setString(1, procPropsUuid);
            insertRendProcPropsStatement.setString(2, renditionUuid);
            insertRendProcPropsStatement.setString(3, "c277649");
            insertRendProcPropsStatement.setInt(4, 30000);
            insertRendProcPropsStatement.executeUpdate();
            commit(connection);
        }
        catch (SQLException e)
        {
            Assertions.fail(String.format("Inserting data into the rendition proc props table failed: %s", e.toString()));
        }

        return procPropsUuid;
    }

    public String insertSection(Connection connection, String renditionUuid)
    {
        String sectionUuid = CommonDataMocking.generateUUID();
        int siblingOrder = getMaxSiblingOrder(connection, renditionUuid, "CWB_SRC_RENDITION_CHILD", "CWB_SRC_RENDITION_UUID");

        try
        {
            logger.information("Inserting into CWB_SRC_RENDITION_CHILD");
            PreparedStatement insertSectionStatement = connection.prepareStatement(SourceDataMockingConstants.INSERT_INTO_SRC_RENDITION_CHILD);
            insertSectionStatement.setString(1, sectionUuid);
            insertSectionStatement.setString(2, renditionUuid);
            insertSectionStatement.setInt(3, siblingOrder);
            insertSectionStatement.setString(4, "Sec. 1");
            insertSectionStatement.setString(5, "Sec. 1");
            insertSectionStatement.executeUpdate();
            commit(connection);

            String paraId = CommonDataMocking.generateUUID();
            String codeHeadId = CommonDataMocking.generateUUID();
            String cornerpieceId = CommonDataMocking.generateUUID();
            insertSectionParagraph(connection, sectionUuid, "<para ID=\"" + paraId + "\"><metadata.block owner=\"" + paraId + "\"><md.mnem>scp3</md.mnem><md.exclude.tag>NX</md.exclude.tag></metadata.block><paratext>Sec. 1</paratext></para>");
            insertSectionParagraph(connection, sectionUuid, "<codes.head ID=\"" + codeHeadId + "\"><metadata.block owner=\"" + codeHeadId + "\"><md.mnem>snl</md.mnem></metadata.block><head.info><label.name>SECTION</label.name> <label.designator>1</label.designator>.&emsp;<headtext>SHORT TITLE.</headtext></head.info></codes.head>");
            insertSectionParagraph(connection, sectionUuid, "<cornerpiece ID=\"" + cornerpieceId + "\" type=\"cp.code.identifier\"><metadata.block owner=\"" + cornerpieceId + "\"><md.mnem>cpl</md.mnem><md.exclude.tag>NX</md.exclude.tag></metadata.block><cornerpiece.text>18 USCA</cornerpiece.text></cornerpiece>");
        }
        catch (SQLException e)
        {
            Assertions.fail(String.format("Inserting data into the CWB_SRC_RENDITION_CHILD table failed: %s", e.toString()));
        }
        return sectionUuid;
    }

    public String insertSectionProcProps(Connection connection, String sectionUuid)
    {
        String sectionProcPropsUuid = CommonDataMocking.generateUUID();

        try
        {
            logger.information("Inserting into CWB_SECTION_PROC_PROPS");
            PreparedStatement insertSectionProcPropsStatement = connection.prepareStatement(SourceDataMockingConstants.INSERT_INTO_SECTION_PROC_PROPS);
            insertSectionProcPropsStatement.setString(1, sectionProcPropsUuid);
            insertSectionProcPropsStatement.setString(2, sectionUuid);
            insertSectionProcPropsStatement.executeUpdate();
            commit(connection);
        }
        catch (SQLException e)
        {
            Assertions.fail(String.format("Inserting data into the CWB_SECTION_PROC_PROPS table failed: %s", e.toString()));
        }

        return sectionProcPropsUuid;
    }

    public String insertSectionParagraph(Connection connection, String sectionUuid, String text)
    {
        String sectionChildUuid = CommonDataMocking.generateUUID();
        int siblingOrder = getMaxSiblingOrder(connection, sectionUuid, "CWB_SECTION_CHILD", "CWB_SECTION_UUID");

        try
        {
            PreparedStatement insertSectionParagraph = connection.prepareStatement(SourceDataMockingConstants.INSERT_INTO_SECTION_CHILD_PARAGRAPH);
            insertSectionParagraph.setString(1, sectionChildUuid);
            insertSectionParagraph.setString(2, sectionUuid);
            insertSectionParagraph.setInt(3, siblingOrder);
            insertSectionParagraph.setString(4, text);
            insertSectionParagraph.executeUpdate();
            commit(connection);
        }
        catch (SQLException e)
        {
            Assertions.fail(String.format("Inserting data into the CWB_SECTION_CHILD table failed: %s", e.toString()));
        }
        return sectionChildUuid;
    }

    public String insertDelta(Connection connection, String sectionUuid, String renditionUuid)
    {
        String deltaUuid = CommonDataMocking.generateUUID();
        int siblingOrder = getMaxSiblingOrder(connection, sectionUuid, "CWB_SECTION_CHILD", "CWB_SECTION_UUID");

        try
        {
            logger.information("Inserting delta into CWB_SECTION_CHILD");
            PreparedStatement insertDeltaStatement = connection.prepareStatement(SourceDataMockingConstants.INSERT_INTO_SECTION_CHILD_DELTA);
            insertDeltaStatement.setString(1, deltaUuid);
            insertDeltaStatement.setString(2, sectionUuid);
            insertDeltaStatement.setInt(3, siblingOrder);
            insertDeltaStatement.executeUpdate();
            commit(connection);

            updateDeltaCount(connection, renditionUuid, "CWB_SRC_REND_PROC_PROPS", "CWB_SRC_RENDITION_UUID");
            updateDeltaCount(connection, sectionUuid, "CWB_SRC_RENDITION_CHILD", "CWB_SRC_RENDITION_CHILDUUID");
            updateDeltaCount(connection, sectionUuid, "CWB_SECTION_PROC_PROPS", "CWB_SECTION_UUID");

            String cornerpieceId = CommonDataMocking.generateUUID();
            String paragraphId = CommonDataMocking.generateUUID();
            String codeHeadId = CommonDataMocking.generateUUID();
            String paraId = CommonDataMocking.generateUUID();
            insertDeltaParagraph(connection, deltaUuid, "<cornerpiece ID=\"" + cornerpieceId + "\" type=\"cp.note\"><metadata.block owner=\"" + cornerpieceId + "\"><md.mnem>cpn</md.mnem><md.exclude.tag>NX</md.exclude.tag></metadata.block><cornerpiece.text>18 &sect;&ensp;1 </cornerpiece.text></cornerpiece>");
            insertDeltaParagraph(connection, deltaUuid, "<para ID=\"" + paragraphId + "\"><metadata.block owner=\"" + paragraphId + "\"><md.mnem>smp</md.mnem></metadata.block><paratext>&ldquo;This Act may be cited as the &lsquo;Foreign Evidence Request Efficiency Act of 2009&rsquo;.</paratext></para>");
            insertDeltaParagraph(connection, deltaUuid, "<codes.head ID=\"" + codeHeadId + "\"><metadata.block owner=\"" + codeHeadId + "\"><md.mnem>shfn</md.mnem><modified.by>TLE TCBA-BOT 09/29/2021</modified.by></metadata.block><head.info><label.name>SEC.</label.name> <label.designator>2</label.designator>.&emsp;<headtext>Improvements To The Title 18</headtext></head.info></codes.head>");
            insertDeltaParagraph(connection, deltaUuid, "<para ID=\"" + paraId + "\"><metadata.block owner=\"" + paraId + "\"><md.mnem>smp</md.mnem></metadata.block><paratext>&ldquo;Title 18 of the United States Code is amended&rdquo;&mdash;</paratext></para>");
        }
        catch (SQLException e)
        {
            Assertions.fail(String.format("Inserting data into the CWB_SECTION_CHILD table failed: %s", e.toString()));
        }
        return deltaUuid;
    }

    public String insertDeltaProcProps(Connection connection, String deltaUuid)
    {
        String deltaProcPropsUuid = CommonDataMocking.generateUUID();
        try
        {
            logger.information("Inserting into CWB_DELTA_PROC_PROPS");
            PreparedStatement insertDeltaProcPropsStatement = connection.prepareStatement(SourceDataMockingConstants.INSERT_INTO_DELTA_PROC_PROPS);
            insertDeltaProcPropsStatement.setString(1, deltaProcPropsUuid);
            insertDeltaProcPropsStatement.setString(2, deltaUuid);
            insertDeltaProcPropsStatement.executeUpdate();
            commit(connection);
        }
        catch (SQLException e)
        {
            Assertions.fail(String.format("Inserting data into the CWB_DELTA_PROC_PROPS table failed: %s", e.toString()));
        }

        return deltaProcPropsUuid;
    }

    public String insertDeltaParagraph(Connection connection, String deltaUuid, String text)
    {
        String deltaChildUuid = CommonDataMocking.generateUUID();
        int siblingOrder = getMaxSiblingOrder(connection, deltaUuid, "CWB_DELTA_CHILD", "CWB_DELTA_UUID");
        try
        {
            PreparedStatement insertDeltaChildStatement = connection.prepareStatement(SourceDataMockingConstants.INSERT_INTO_DELTA_CHILD);
            insertDeltaChildStatement.setString(1, deltaChildUuid);
            insertDeltaChildStatement.setString(2, deltaUuid);
            insertDeltaChildStatement.setInt(3, siblingOrder);
            insertDeltaChildStatement.setString(4, text);
            insertDeltaChildStatement.executeUpdate();
            commit(connection);
        }
        catch (SQLException e)
        {
            Assertions.fail(String.format("Inserting data into the CWB_DELTA_CHILD table failed: %s", e.toString()));
        }
        return deltaChildUuid;
    }

    public String insertDeltaTargetLocation(Connection connection, String deltaUuid)
    {
        // Austin TODO: Work with Target Data Mocking to actually connect to a Target Location.
        String targetLocationUuid = CommonDataMocking.generateUUID();

        try
        {
            logger.information("Inserting into CWB_TARGET_LOCATION");
            PreparedStatement insertTargetLocationStatement = connection.prepareStatement(SourceDataMockingConstants.INSERT_INTO_TARGET_LOCATION);
            insertTargetLocationStatement.setString(1, targetLocationUuid);
            insertTargetLocationStatement.setString(2, deltaUuid);
            insertTargetLocationStatement.executeUpdate();
            commit(connection);
        }
        catch (SQLException e)
        {
            Assertions.fail(String.format("Inserting data into the CWB_TARGET_LOCATION table failed: %s", e.toString()));
        }
        return targetLocationUuid;
    }

    public String insertInitialBaseline(Connection connection, String renditionUuid, String deltaUuid)
    {
        String baselineUuid = DataMocking.generateUUID();
        String docNumber = SourceDatabaseUtils.getDocNumberFromRenditionUuid(connection, renditionUuid);
        ArrayList<String> uuids = new ArrayList<>(Arrays.asList(
                CommonDataMocking.generateUUID(), CommonDataMocking.generateUUID(), CommonDataMocking.generateUUID(), CommonDataMocking.generateUUID(),
                CommonDataMocking.generateUUID(), CommonDataMocking.generateUUID(), CommonDataMocking.generateUUID(), CommonDataMocking.generateUUID(),
                CommonDataMocking.generateUUID(), CommonDataMocking.generateUUID(), CommonDataMocking.generateUUID(), CommonDataMocking.generateUUID(),
                CommonDataMocking.generateUUID(), CommonDataMocking.generateUUID(), CommonDataMocking.generateUUID(), CommonDataMocking.generateUUID(),
                CommonDataMocking.generateUUID(), CommonDataMocking.generateUUID(), CommonDataMocking.generateUUID(), CommonDataMocking.generateUUID(),
                CommonDataMocking.generateUUID(), CommonDataMocking.generateUUID(), CommonDataMocking.generateUUID(), CommonDataMocking.generateUUID(),
                CommonDataMocking.generateUUID()));
        String lineageUuid = SourceDatabaseUtils.getLineageUuidFromRenditionUuid(connection, renditionUuid);
        String targetLocationUuid = SourceDatabaseUtils.getTargetLocationUuidFromDeltaUuid(connection, deltaUuid);

        // TODO source front/end paragraphs are causing data validation issues

        String schHeadingText = "<para ID=\"" + uuids.get(4) + "\"><metadata.block owner=\"" + uuids.get(4) + "\"><md.mnem>sch</md.mnem></metadata.block><paratext>H.F. " + docNumber + "</paratext></para>";
        //insertSourceFrontParagraph(connection, renditionUuid, "UnparsedParagraphImpl", "FRONT", "", "", 0, 0, 0, 0, schHeadingText);
        String scpHeadingText = "<para ID=\"" + uuids.get(5) + "\"><metadata.block owner=\"" + uuids.get(5) + "\"><md.mnem>scp</md.mnem></metadata.block><paratext>West's No. 194</paratext></para>";
        //insertSourceFrontParagraph(connection, renditionUuid, "UnparsedParagraphImpl", "FRONT", "", "", 0, 0, 0, 0, scpHeadingText);
        String dtypeMetadataBlock = "<metadata.block owner=\"" + uuids.get(6) + "\"><md.mnem>dtype</md.mnem></metadata.block>";
        String dtypeText = "R1, S1HB";
        //insertSourceFrontParagraph(connection, renditionUuid, "SessionInfoParagraphImpl", "FRONT", dtypeMetadataBlock, "", 0, 0, 0, 1, dtypeText);
        String hg1MetadataBlock = "<metadata.block owner=\"" + uuids.get(7) + "\"><md.mnem>hg1</md.mnem></metadata.block>";
        String hg1Text = "Ch. 2 (H.F. " + docNumber + ")";
        //insertSourceFrontParagraph(connection, renditionUuid, "BillInfoParagraphImpl", "FRONT", hg1MetadataBlock, "", 44, Integer.parseInt(docNumber), 0, 0, hg1Text);
        String hg2MetadataBlock = "<metadata.block owner=\"" + uuids.get(8) + "\"><md.mnem>hg2</md.mnem></metadata.block>";
        String hg2Text = "West's No. 194";
        //insertSourceFrontParagraph(connection, renditionUuid, "WestIdParagraphImpl", "FRONT", hg2MetadataBlock, "", 0, 0, 194, 0, hg2Text);
        String toaText = "<para ID=\"" + uuids.get(9) + "\"><metadata.block owner=\"" + uuids.get(9) + "\"><md.mnem>toa</md.mnem></metadata.block><paratext>AN ACT RELATING TO THE EMPLOYMENT SECURITY ADMINISTRATIVE CONTRIBUTION SURCHARGE, AND PROVIDING AN EFFECTIVE DATE.</paratext></para>";
        //insertSourceFrontParagraph(connection, renditionUuid,"UnparsedParagraphImpl", "FRONT", "", "", 0, 0, 0, 0, toaText);
        String beitText = "<para ID=\"" + uuids.get(10) + "\"><metadata.block owner=\"" + uuids.get(10) + "\"><md.mnem>beit</md.mnem></metadata.block><paratext>BE IT ENACTED BY THE GENERAL ASSEMBLY OF THE STATE OF IOWA:</paratext></para>";
        //insertSourceFrontParagraph(connection, renditionUuid, "UnparsedParagraphImpl", "FRONT", "", "", 0, 0, 0, 0, beitText);
        String apvMetadataBlock = "<metadata.block owner=\"" + uuids.get(20) + "\"><md.mnem>apv</md.mnem></metadata.block>";
        //insertSourceFrontParagraph(connection, renditionUuid, "ApprovalDateParagraphImpl", "END", apvMetadataBlock, "Approved June 26, 2020.", 0, 0, 0, 0, "");
        String edatText = "<para ID=\"" + uuids.get(21) + "\"><metadata.block owner=\"" + uuids.get(21) + "\"><md.mnem>edat</md.mnem></metadata.block><paratext>&emsp;</paratext></para>";
        //insertSourceFrontParagraph(connection, renditionUuid, "UnparsedParagraphImpl", "END", "", "", 0, 0, 0, 0, edatText);

        try
        {
            logger.information("Inserting into CWB_SRC_RENDITION_BASELINE");
            PreparedStatement insertBaselineStatement = connection.prepareStatement(SourceDataMockingConstants.INSERT_ORIGINAL_INTO_SRC_RENDITION_BASELINE);
            insertBaselineStatement.setString(1, baselineUuid);
            insertBaselineStatement.setString(2, renditionUuid);
            insertBaselineStatement.setString(3, "<source.rendition artifact-type=\"bill\" ID=\"" + renditionUuid + "\" official.status=\"APV\" " +
                    "year=\"2020\"><source.lineage.properties ID=\"" + lineageUuid + "\"><jurisdiction ID=\"" + uuids.get(0) + "\"><metadata.block " +
                    "owner=\"" + uuids.get(0) + "\"></metadata.block><text>IA</text></jurisdiction>" +
                    "<class.name ID=\"" + uuids.get(1) + "\"<metadata.block owner=\"" + uuids.get(1) + "\"/><text>CHAPTER</text></class.name>" +
                    "<class.number ID=\"" + uuids.get(2) + "\"<metadata.block owner=\"" + uuids.get(2) + "\"<text>2</text></class.number></source.lineage.properties>" +
                    "<source.front ID=\"" + uuids.get(3) + "\" version=\"1\" docid=\"" + uuids.get(3) + "\">" +
                    schHeadingText + scpHeadingText +
                    "<session ID=\"" + uuids.get(6) + "\" type=\"2RG\">" + dtypeMetadataBlock + "<text>" + dtypeText + "</text></session>" +
                    "<bill.info ID=\"" + uuids.get(7) + "\" type=\"HF\" num=\"" + docNumber + "\">" + hg1MetadataBlock + "<text>" + hg1Text + "</text></bill.info>" +
                    "<west.id ID=\"" + uuids.get(8) + "\">" + hg2MetadataBlock + "<text>" + hg2Text + "</text></west.id>" +
                    toaText + beitText +
                    "</source.front>" +
                    "<source.body ID=\"" + uuids.get(11) + "\"><source.section ID=\"" + uuids.get(11) + "\" version=\"1\" docid=\"" + uuids.get(12) + "\" " +
                    "num=\"Sec. 1\"><para ID=\"" + uuids.get(13) + "\"><metadata.block owner=\"" + uuids.get(13) + "\"><md.mnem>scp3</md.mnem><md.exclude.tag>NX</md.exclude.tag></metadata.block>" +
                    "<paratext>Sec. 1</paratext></para><codes.head ID=\"" + uuids.get(14) + "\"><metadata.block owner=\"" + uuids.get(14) + "\">" +
                    "<md.mnem>snl</md.mnem></metadata.block><head.info><label.name>SECTION</label.name><label.designator>1</label.designator>.&emsp;<headtext>SHORT TITLE.</headtext></head.info></codes.head>" +
                    "<cornerpiece ID=\"" + uuids.get(15) + "\" type=\"cp.code.identifier\"><metadata.block owner=\"" + uuids.get(15) + "\"><md.mnem>cpl</md.mnem><md.exclude.tag>NX</md.exclude.tag>" +
                    "</metadata.block><cornerpiece.text>18 USCA</cornerpiece.text></cornerpiece><delta ID=\"" + deltaUuid + "\" action=\"add\" level=\"note.classified\">" +
                    "<target.location ID=\"" + targetLocationUuid + "\"><target.location.code>18 USCA</target.location.code><target.location.section>18 &sect; 1</target.location.section></target.location>" +
                    "<cornerpiece ID=\"" + uuids.get(16) + "\" type=\"cp.note\"><metadata.block owner=\"" + uuids.get(16) + "\"><md.mnem>cpn</md.mnem><md.exclude.tag>NX</md.exclude.tag></metadata.block>" +
                    "<cornerpiece.text>18 &sect;&ensp;1 </cornerpiece.text></cornerpiece><para ID=\"" + uuids.get(17) + "\"><metadata.block owner=\"" + uuids.get(17) + "\">" +
                    "<md.mnem>smp</md.mnem></metadata.block><paratext>&ldquo;This Act may be cited as the &lsquo;Foreign Evidence Request Efficiency Act of 2009&rsquo;.</paratext></para>" +
                    "<codes.head ID=\"" + uuids.get(18) + "\"><metadata.block owner=\"" + uuids.get(18) + "\"><md.mnem>shfn</md.mnem></metadata.block><head.info><label.name>SEC.</label.name> <label.designator>2</label.designator>" +
                    ".&emsp;<headtext>Improvements To Title 18</headtext></head.info></codes.head><para ID=\"" + uuids.get(19) + "\"><metadata.block owner=\"" + uuids.get(19) + "\"><md.mnem>smp</md.mnem></metadata.block>" +
                    "<paratext>&ldquo;Title 18 of the United States Code is amended&rdquo;&mdash;</paratext></para></delta></source.section></source.body>" +
                    "<source.end ID=\"" + uuids.get(23) + "\" version=\"1\" docid=\"" + uuids.get(24) + "\">" +
                    "<approval.date ID=\"" + uuids.get(22) + "\">" + apvMetadataBlock + "<text>Approved June 26, 2020.</text></approval.date>" +
                    edatText + "</source.end>" +
                    "</source.rendition>");
            insertBaselineStatement.setInt(4, 0);
            insertBaselineStatement.executeUpdate();
            commit(connection);
        }
        catch (SQLException e)
        {
            Assertions.fail(String.format("Inserting into CWB_SRC_RENDITION_BASELINE failed: %s", e.toString()));
        }
        return baselineUuid;
    }

    public String insertBaseline(Connection connection, String renditionUuid, String username)
    {
        String baselineUuid = CommonDataMocking.generateUUID();
        int siblingOrder = getMaxSiblingOrder(connection, renditionUuid, "CWB_SRC_RENDITION_BASELINE", "CWB_SRC_RENDITION_UUID");
        try
        {
            PreparedStatement insertBaselineStatement = connection.prepareStatement(SourceDataMockingConstants.INSERT_INTO_SRC_RENDITION_BASELINE);
            insertBaselineStatement.setString(1, baselineUuid);
            insertBaselineStatement.setString(2, renditionUuid);
            insertBaselineStatement.setString(3, username);
            insertBaselineStatement.setInt(4, siblingOrder);
            insertBaselineStatement.executeUpdate();
            commit(connection);
        }
        catch (SQLException e)
        {
            Assertions.fail(String.format("Inserting a baseline failed: %s", e.toString()));
        }
        return baselineUuid;
    }

    private void insertSourceFrontParagraph(Connection connection, String renditionUuid, String paragraphType, String paragraphLocation, String metadataBlock,
            String contentDate, int docType, int docNumber, int westID, int sessionID, String text)
    {
        String paraUuid = CommonDataMocking.generateUUID();
        int siblingOrder = getMaxSiblingOrder(connection, renditionUuid, "CWB_FRONT_END_PARA", "CWB_SOURCE_RENDITION_UUID");

        try
        {
            logger.information(String.format("Inserting source front paragraph %s", paragraphType));
            PreparedStatement insertSourceFrontParagraph = connection.prepareStatement(SourceDataMockingConstants.INSERT_INTO_SOURCE_FRONT);
            insertSourceFrontParagraph.setString(1, paraUuid);
            insertSourceFrontParagraph.setString(2, renditionUuid);
            insertSourceFrontParagraph.setString(3, paragraphType);
            insertSourceFrontParagraph.setString(4, paragraphLocation);
            insertSourceFrontParagraph.setInt(5, siblingOrder);
            insertSourceFrontParagraph.setString(6, metadataBlock);
            insertSourceFrontParagraph.setString(7, contentDate);
            setIntOrNull(insertSourceFrontParagraph, 8, docType);
            setIntOrNull(insertSourceFrontParagraph, 9, docNumber);
            setIntOrNull(insertSourceFrontParagraph, 10, westID);
            setIntOrNull(insertSourceFrontParagraph, 11, sessionID);
            insertSourceFrontParagraph.setString(12, text);
            insertSourceFrontParagraph.executeUpdate();
        }
        catch (SQLException e)
        {
            Assertions.fail(String.format("Inserting a source front paragraph failed: %s", e.toString()));
        }
    }

    private void setIntOrNull(PreparedStatement statement, int column, int value)
    {
        try
        {
            if (value != 0)
            {
                statement.setInt(column, value);
            }
            else
            {
                statement.setNull(column, Types.INTEGER);
            }
        }
        catch (SQLException e)
        {
            Assertions.fail(String.format("Setting the value failed: %s", e.toString()));
        }

    }

    public void deleteSourceFrontParagraphs(Connection connection, String renditionUuid)
    {
        try
        {
            logger.information("Deleting from CWB_FRONT_END_PARA " + renditionUuid);
            PreparedStatement deleteFrontEndParaStatement = connection.prepareStatement(SourceDataMockingConstants.DELETE_FROM_CWB_FRONT_END_PARA);
            deleteFrontEndParaStatement.setString(1, renditionUuid);
            deleteFrontEndParaStatement.executeUpdate();
            deleteFrontEndParaStatement.close();
        }
        catch (SQLException e)
        {
            Assertions.fail(String.format("Deleting from CWB_FRONT_END_PARA failed: %s", e.toString()));
        }
    }

    public void deleteRenditionErrorFlag(Connection connection, String renditionUuid)
    {
        try
        {
            logger.information("Deleting from CWB_SRC_REND_ERROR_FLAG " + renditionUuid);
            PreparedStatement deleteDeltaChildStatement = connection.prepareStatement(SourceDataMockingConstants.DELETE_FROM_SRC_REND_ERROR_FLAG);
            deleteDeltaChildStatement.setString(1, renditionUuid);
            deleteDeltaChildStatement.executeUpdate();
            deleteDeltaChildStatement.close();
        }
        catch (SQLException e)
        {
            Assertions.fail(String.format("Deleting from CWB_SRC_REND_ERROR_FLAG failed: %s", e.toString()));
        }
    }

    public void deleteRenditionWarningFlag(Connection connection, String renditionUuid)
    {
        try
        {
            logger.information("Deleting from CWB_SRC_REND_WARNING_FLAG " + renditionUuid);
            PreparedStatement deleteDeltaChildStatement = connection.prepareStatement(SourceDataMockingConstants.DELETE_FROM_SRC_REND_WARNING_FLAG);
            deleteDeltaChildStatement.setString(1, renditionUuid);
            deleteDeltaChildStatement.executeUpdate();
            deleteDeltaChildStatement.close();
        }
        catch (SQLException e)
        {
            Assertions.fail(String.format("Deleting from CWB_SRC_REND_WARNING_FLAG failed: %s", e.toString()));
        }
    }

    public void deleteRenditionBaselines(Connection connection, String renditionUuid)
    {
        try
        {
            logger.information("Deleting from CWB_SRC_RENDITION_BASELINE");
            PreparedStatement deleteDeltaChildStatement = connection.prepareStatement(SourceDataMockingConstants.DELETE_FROM_SRC_RENDITION_BASELINE);
            deleteDeltaChildStatement.setString(1, renditionUuid);
            deleteDeltaChildStatement.executeUpdate();
            deleteDeltaChildStatement.close();
        }
        catch (SQLException e)
        {
            Assertions.fail(String.format("Deleting from CWB_SRC_RENDITION_BASELINE failed: %s", e.toString()));
        }
    }

    public void deleteDeltaParagraph(Connection connection, String renditionUuid)
    {
        try
        {
            logger.information("Deleting from CWB_DELTA_CHILD");
            PreparedStatement deleteDeltaChildStatement = connection.prepareStatement(SourceDataMockingConstants.DELETE_FROM_DELTA_CHILD);
            deleteDeltaChildStatement.setString(1, renditionUuid);
            deleteDeltaChildStatement.executeUpdate();
            deleteDeltaChildStatement.close();
        }
        catch (SQLException e)
        {
            Assertions.fail(String.format("Deleting from CWB_DELTA_CHILD failed: %s", e.toString()));
        }
    }

    public void deleteDeltaProcProps(Connection connection, String renditionUuid)
    {
        try
        {
            logger.information("Deleting from CWB_DELTA_PROC_PROPS");
            PreparedStatement deleteDeltaProcPropsStatement = connection.prepareStatement(SourceDataMockingConstants.DELETE_FROM_DELTA_PROC_PROPS);
            deleteDeltaProcPropsStatement.setString(1, renditionUuid);
            deleteDeltaProcPropsStatement.executeUpdate();
            deleteDeltaProcPropsStatement.close();
        }
        catch (SQLException e)
        {
            Assertions.fail(String.format("Deleting from CWB_DELTA_PROC_PROPS failed: %s", e.toString()));
        }
    }

    public void deleteTargetLocation(Connection connection, String renditionUuid)
    {
        try
        {
            logger.information("Deleting from CWB_TARGET_LOCATION");
            PreparedStatement deleteTargetLocWarnFlagStatement = connection.prepareStatement(SourceDataMockingConstants.DELETE_FROM_TARGET_LOC_WARN_FLAG);
            deleteTargetLocWarnFlagStatement.setString(1, renditionUuid);
            deleteTargetLocWarnFlagStatement.executeUpdate();
            deleteTargetLocWarnFlagStatement.close();

            PreparedStatement deleteTargetLocErrStatement = connection.prepareStatement(SourceDataMockingConstants.DELETE_FROM_TARGET_LOC_ERR_FLAG);
            deleteTargetLocErrStatement.setString(1, renditionUuid);
            deleteTargetLocErrStatement.executeUpdate();
            deleteTargetLocErrStatement.close();

            PreparedStatement deleteTargetLocCitelocErrStatement = connection.prepareStatement(SourceDataMockingConstants.DELETE_FROM_TARGET_LOC_CITELOC_ERR);
            deleteTargetLocCitelocErrStatement.setString(1, renditionUuid);
            deleteTargetLocCitelocErrStatement.executeUpdate();
            deleteTargetLocCitelocErrStatement.close();

            PreparedStatement deleteTargetLocationStatement = connection.prepareStatement(SourceDataMockingConstants.DELETE_FROM_TARGET_LOCATION);
            deleteTargetLocationStatement.setString(1, renditionUuid);
            deleteTargetLocationStatement.executeUpdate();
            deleteTargetLocationStatement.close();
        }
        catch (SQLException e)
        {
            Assertions.fail(String.format("Deleting from CWB_TARGET_LOCATION failed: %s", e.toString()));
        }
    }

    public void deleteDeltasAndSectionParagraphs(Connection connection, String renditionUuid)
    {
        try
        {
            logger.information("Deleting from CWB_SECTION_CHILD");
            PreparedStatement deleteSrcRenditionChildStatement = connection.prepareStatement(SourceDataMockingConstants.DELETE_FROM_SECTION_CHILD);
            deleteSrcRenditionChildStatement.setString(1, renditionUuid);
            deleteSrcRenditionChildStatement.executeUpdate();
            deleteSrcRenditionChildStatement.close();
        }
        catch (SQLException e)
        {
            Assertions.fail(String.format("Deleting from CWB_SECTION_CHILD failed: %s", e.toString()));
        }
    }

    public void deleteSectionProcProps(Connection connection, String renditionUuid)
    {
        try
        {
            logger.information("Deleting from CWB_SECTION_GROUP_PROC_PROPS");
            PreparedStatement deleteSectionProcPropsStatement = connection.prepareStatement(SourceDataMockingConstants.DELETE_FROM_SECTION_PROC_PROPS);
            deleteSectionProcPropsStatement.setString(1, renditionUuid);
            deleteSectionProcPropsStatement.executeUpdate();
            deleteSectionProcPropsStatement.close();
        }
        catch (SQLException e)
        {
            Assertions.fail(String.format("Deleting from CWB_SECTION_GROUP_PROC_PROPS failed: %s", e.toString()));
        }
    }

    public void deleteSection(Connection connection, String renditionUuid)
    {
        try
        {
            logger.information("Deleting from CWB_SRC_RENDITION_CHILD");
            PreparedStatement deleteSectionWarnFlagStatement = connection.prepareStatement(SourceDataMockingConstants.DELETE_FROM_SECTION_WARN_FLAG);
            deleteSectionWarnFlagStatement.setString(1, renditionUuid);
            deleteSectionWarnFlagStatement.executeUpdate();
            deleteSectionWarnFlagStatement.close();

            PreparedStatement deleteSrcRenditionChildStatement = connection.prepareStatement(SourceDataMockingConstants.DELETE_FROM_SRC_RENDITION_CHILD);
            deleteSrcRenditionChildStatement.setString(1, renditionUuid);
            deleteSrcRenditionChildStatement.executeUpdate();
            deleteSectionWarnFlagStatement.close();
        }
        catch (SQLException e)
        {
            Assertions.fail(String.format("Deleting from CWB_SRC_RENDITION_CHILD failed: %s", e.toString()));
        }
    }

    public void deleteRendProcProps(Connection connection, String renditionUuid)
    {
        try
        {
            logger.information("Deleting from CWB_SRC_REND_PROC_PROPS");
            PreparedStatement deleteRendProcPropsStatement = connection.prepareStatement(SourceDataMockingConstants.DELETE_FROM_SRC_REND_PROC_PROPS);
            deleteRendProcPropsStatement.setString(1, renditionUuid);
            deleteRendProcPropsStatement.executeUpdate();
            deleteRendProcPropsStatement.close();
        }
        catch (SQLException e)
        {
            Assertions.fail(String.format("Deleting from CWB_SRC_REND_PROC_PROPS failed: %s", e.toString()));
        }
    }

    public void deleteSrcRendition(Connection connection, String renditionUuid)
    {
        try
        {
            logger.information("Deleting from CWB_SRC_RENDITION");
            PreparedStatement deleteSrcRenditionStatement = connection.prepareStatement(SourceDataMockingConstants.DELETE_FROM_SRC_RENDITION);
            deleteSrcRenditionStatement.setString(1, renditionUuid);
            deleteSrcRenditionStatement.executeUpdate();
            deleteSrcRenditionStatement.close();
        }
        catch (SQLException e)
        {
            Assertions.fail(String.format("Deleting from CWB_SRC_RENDITION failed: %s", e.toString()));
        }
    }

    public boolean doesLineageExist(Connection connection, String lineageUuid)
    {
        boolean exists = false;
        try
        {
            logger.information("Retrieving from CWB_SRC_LINEAGE");
            PreparedStatement getLineageStatement = connection.prepareStatement(SourceDataMockingConstants.SELECT_LINEAGE_WITH_UUID);
            getLineageStatement.setString(1, lineageUuid);
            ResultSet result = getLineageStatement.executeQuery();
            if(result.next())
            {
                exists = true;
            }
            result.close();
            getLineageStatement.close();
        }
        catch (SQLException e)
        {
            Assertions.fail(String.format("Selecting from CWB_SRC_LINEAGE failed: %s", e.toString()));
        }
        return exists;
    }

    public void deleteSrcLineage(Connection connection, String lineageUuid)
    {
        try
        {
            logger.information("Deleting from CWB_SRC_LINEAGE. Lineage UUID: '" + lineageUuid + "'");
            PreparedStatement deleteSrcLineageStatement = connection.prepareStatement(SourceDataMockingConstants.DELETE_FROM_SRC_LINEAGE);
            deleteSrcLineageStatement.setString(1, lineageUuid);
            deleteSrcLineageStatement.executeUpdate();
            deleteSrcLineageStatement.close();
        }
        catch (SQLException e)
        {
            TestService.logger.warning(String.format("Deleting from CWB_SRC_LINEAGE failed: %s", e.toString()));
            logger.warning("This may be an error, or it may be that multiple mocked renditions exist within this lineage. Make sure this lineage is deleted later in this test! '" + lineageUuid + "'");
        }
    }

    private void updateDeltaCount(Connection connection, String uuid, String tableName, String uuidName)
    {
        try
        {
            //Increase current delta count by 1
            int deltaCount = Integer.parseInt(SourceDatabaseUtils.getMaxDeltaCount(connection, uuid, tableName, uuidName)) + 1;

            String query = SourceDataMockingConstants.UPDATE_DELTA_COUNT.replace("$tableName", tableName).replace("$uuid", uuidName);
            PreparedStatement getDeltaCountStatement = connection.prepareStatement(query);
            getDeltaCountStatement.setInt(1, deltaCount);
            getDeltaCountStatement.setString(2, uuid);
            getDeltaCountStatement.executeUpdate();
            getDeltaCountStatement.close();
        }
        catch (SQLException e)
        {
            Assertions.fail(String.format("Failed to update delta count for section %s: %s", uuid, e.toString()));
        }
    }

    private int getMaxSiblingOrder(Connection connection, String uuid, String tableName, String uuidName)
    {
        int siblingOrder = 0;

        if (SourceDatabaseUtils.getMaxSiblingOrder(connection, tableName, uuid, uuidName) != null)
        {
            //Increment the sibling order by 1 from what is currently the max
            siblingOrder = Integer.parseInt(SourceDatabaseUtils.getMaxSiblingOrder(connection, tableName, uuid, uuidName)) + 1;
        }

        return siblingOrder;
    }

    public void deleteRendition(Connection connection, String renditionUUID)
    {
        String lineageUuid = SourceDatabaseUtils.getLineageUuidFromRenditionUuid(connection, renditionUUID);
        SourceDatabaseUtils.deleteLocks(connection, renditionUUID);
        deleteRenditionErrorFlag(connection, renditionUUID);
        deleteRenditionWarningFlag(connection, renditionUUID);
        deleteSourceFrontParagraphs(connection, renditionUUID);
        deleteRenditionBaselines(connection, renditionUUID);
        deleteDeltaParagraph(connection, renditionUUID);
        deleteDeltaProcProps(connection, renditionUUID);
        deleteTargetLocation(connection, renditionUUID);
        deleteDeltasAndSectionParagraphs(connection, renditionUUID);
        deleteSectionProcProps(connection, renditionUUID);
        deleteSection(connection, renditionUUID);
        deleteRendProcProps(connection, renditionUUID);
        deleteManualLoadProps(connection, renditionUUID);
        deleteAiEffectiveDateOutput(connection, renditionUUID);
        deleteSrcRendition(connection, renditionUUID);
        if(doesLineageExist(connection, lineageUuid))
        {
            deleteSrcLineage(connection, lineageUuid);
        }
        commit(connection);
    }

    public void giveRenditionError(Connection connection, SourceDatapodObject datapodObject)
    {
        String flagUuid = CommonDataMocking.generateUUID();

        try
        {
            PreparedStatement insertTargetLocationStatement = connection.prepareStatement(SourceDataMockingConstants.INSERT_INTO_REND_ERROR_FLAG);
            insertTargetLocationStatement.setString(1, flagUuid);
            insertTargetLocationStatement.setString(2, datapodObject.getRenditions().get(0).getRenditionUUID());
            insertTargetLocationStatement.executeUpdate();
            insertTargetLocationStatement.close();

            PreparedStatement updateRendProcProcsStatement = connection.prepareStatement(SourceDataMockingConstants.UPDATE_REND_PROC_PROPS_FLAG_ERROR);
            updateRendProcProcsStatement.setString(1, datapodObject.getRenditions().get(0).getRenditionUUID());
            updateRendProcProcsStatement.executeUpdate();
            updateRendProcProcsStatement.close();

            PreparedStatement updateTargetLocationStatement = connection.prepareStatement(SourceDataMockingConstants.UPDATE_TARGET_LOCATION_FOR_ERROR);
            updateTargetLocationStatement.setString(1, datapodObject.getDeltas().get(0).getDeltaUUID());
            updateTargetLocationStatement.executeUpdate();
            updateRendProcProcsStatement.close();
            commit(connection);

        }
        catch (SQLException e)
        {
            Assertions.fail(String.format("Giving the rendition an error flag failed: %s", e.toString()));
        }
    }

    public void deleteManualLoadProps(Connection connection, String renditionUuid)
    {
        try
        {
            PreparedStatement deleteManualLoadPropsStatement = connection.prepareStatement(SourceDataMockingConstants.DELETE_FROM_SRC_MANUAL_LOAD_PROPS);
            deleteManualLoadPropsStatement.setString(1, renditionUuid);
            deleteManualLoadPropsStatement.executeUpdate();
            deleteManualLoadPropsStatement.close();
            commit(connection);
        }
        catch (SQLException e)
        {
            Assertions.fail(String.format("Deleting from CWB_SRC_MANUAL_LOAD_PROPS failed: %s", e.toString()));
        }
    }

    public void deleteAiEffectiveDateOutput(Connection connection, String renditionUuid)
    {
        try
        {
            PreparedStatement deleteManualLoadPropsStatement = connection.prepareStatement(SourceDataMockingConstants.DELETE_FROM_AI_EFFECTIVE_DATE);
            deleteManualLoadPropsStatement.setString(1, renditionUuid);
            deleteManualLoadPropsStatement.setString(2, renditionUuid);
            deleteManualLoadPropsStatement.executeUpdate();
            deleteManualLoadPropsStatement.close();
            commit(connection);
        }
        catch (SQLException e)
        {
            Assertions.fail(String.format("Deleting from CWB_AI_EFFECTIVE_DATE_OUTPUT failed: %s", e.toString()));
        }
    }
}

