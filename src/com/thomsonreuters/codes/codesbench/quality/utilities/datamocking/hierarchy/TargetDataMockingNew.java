package com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.hierarchy.HierarchyDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.CommonDataMocking;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.datapod.HierarchyDatapodConfiguration;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.datapod.HierarchyDatapodContentType;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.datapod.HierarchyDatapodObject;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.datapod.nodes.*;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.queries.TargetDataMockingConstants;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.queries.TargetNodeMockingQueries;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets;
import com.thomsonreuters.codes.codesbench.quality.utilities.numerals.RomanNumeralConversion;
import org.junit.jupiter.api.Assertions;

import java.sql.Connection;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static com.thomsonreuters.codes.codesbench.quality.utilities.TestService.logger;
import static com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils.commit;

public class TargetDataMockingNew extends TargetNodeMockingQueries
{

    private static int currentHID = 9999900;

    /**
     * Volume Num Str for the volume(s) mocked by TargetDataMockingNew.
     * Don't edit this if you are not a Scale & Performance team member and know it is definitely necessary!
     */
    private static final String VOLUME_NUM_STR = "9999";

    /**
     * Gets the Volume Num Str for the volume(s) mocked by TargetDataMockingNew.
     * @return The Volume Num Str that datapods are in
     */
    public static String getVolumeNumStr()
    {
        return VOLUME_NUM_STR;
    }

    private static final String DEFAULT_START_DATE = "2005-12-31";
    private static final String CURRENT_START_DATE = DateAndTimeUtils.getCurrentDateyyyyMMddWithHyphen();

    private static Connection connection;

    /**
     * These UUIDs are the highest level nodes in their content sets. Should these change, we will have to change them here.
     */
    private static final String IOWA_CODE_ANNOTATED_NODE_UUID = "I26831DF014EE11DA8AC5CD53670E6B4E";
    private static final String UNITED_STATES_CODE_ANNOTATED_NODE_UUID = "IDFF943C0B65511D8983DF34406B5929B";
    private static final String CROWN_DEPENDENCIES_SET_NODE_UUID = "IB3A48940BBA911E5B2649F8A995DD0A7";
    private static final String REG_GUIDANCE_SUMMARY_US_SET_NODE_UUID = "IBBEC9EC090AC11E69485FAEBCAC6A4AA";
    private static final String CODE_OF_FEDERAL_REGULATIONS_UUID = "I156AF7107C8011D9BF2BB0A94FBB0D8D";
    private static final String FCA_HANDBOOK_UUID = "I28DC0CF0244611E78356CC01025783FE";

    /**
     * Pub ID lets publishing workflows run on our mocked nodes -- if it stops working, this probably needs to be changed
     */
    private static final int pubId = 123;

    /**
     * Gets the current connection, or creates the connection.
     *
     * @return The connection
     */
    private static Connection getConnection()
    {
        if (connection == null)
        {
            connection = connectToDatabase();
        }
        return connection;
    }

    /**
     * Convert a keywordId from the database to a NodeType.
     *
     * @param keywordId KeywordId from the database
     * @return Corresponding NodeType
     */
    public static NodeType convertKeywordIdToNodeType(int keywordId)
    {
        switch (keywordId)
        {
            case 7:
                return NodeType.TITLE;
            case 24:
                return NodeType.SUBTITLE;
            case 4:
                return NodeType.CHAPTER;
            case 26:
            case 17:
                return NodeType.SECTION;
            case 110:
                return NodeType.NOD_CONTAINER;
            case 107:
                return NodeType.XND;
            case 102:
                return NodeType.BL_ANALYSIS;
            case 100:
                return NodeType.BLUELINE;
            case 25:
                return NodeType.GRADE;
            case 16:
                return NodeType.RULE;
            case 14:
                return NodeType.PART;
            case 103:
                return NodeType.NDRS;
            case 104:
                return NodeType.ARL;
            case 19:
                return NodeType.SUBCHAPTER;
            case 1:
                return NodeType.ARTICLE;
            case 10:
                return NodeType.DIVISION;
            default:
                logger.severe("You seem to have found/created a Node that is not supported in TargetDataMockingNew.convertKeywordIdToNodeType");
                logger.severe("Please add it (keyword id: " + keywordId + ")");
                return null;
        }
    }

    /**
     * Convert a NodeType to the keyword id
     *
     * @param nodeType The NodeType to change to a keyword id
     * @return Corresponding keyword id
     */
    public static int convertNodeTypeToKeywordId(NodeType nodeType)
    {
        switch (nodeType.ordinal())
        {
            case 0:
                return 7;
            case 1:
                return 24;
            case 2:
                return 4;
            case 3:
                return 26;
            case 4:
                return 110;
            case 5:
                return 107;
            case 6:
                return 102;
            case 7:
                return 100;
            case 8:
                return 25;
            case 9:
                return 16;
            case 10:
                return 14;
            case 11:
                return 103;
            case 12:
                return 104;
            case 13:
                return 19;
            case 14:
                return 1;
            case 15:
                return 10;
            case 16:
                return 26;
            default:
                logger.severe("Somehow you've thrown in a NodeType that isn't recognized by TargetDataMockingNew.convertNodeTypeToKeywordId");
                return -1;
        }
    }

    /**
     * Connects to the database that corresponds to the environment the test suite is currently running in.
     *
     * @return A connection that corresponds to the environment the test suite is currently running in.
     */
    protected static Connection connectToDatabase()
    {
        String environment = TestService.environmentTag;
        Connection connection;
        switch (environment)
        {
            case "uat":
                connection = BaseDatabaseUtils.connectToDatabaseUAT();
                logger.information("uat");
                break;
            case "dev":
                connection = BaseDatabaseUtils.connectToDatabaseDEV();
                logger.information("dev");
                break;
            default:
                connection = null;
        }

        return connection;
    }

    private static int contentSetId;

    /**
     * Gets the content set id that is currently being used for inserting datapods
     * @return The content set id that is currently being used for inserting datapods
     */
    public static int getCurrentContentSetId()
    {
        return contentSetId;
    }

    /**
     * Used to insert datapods into the Iowa (Development) content set
     */
    public static class Iowa
    {

        static int iowaContentSetId = Integer.parseInt(ContentSets.IOWA_DEVELOPMENT.getCode());

        /**
         * Used to insert datapods with a small amount of content
         */
        public static class Small
        {
            /**
             * Creates a HierarchyDatapodObject and inserts the related info into the database for Iowa (Development).
             * Pulls information from the HierarchyDatapodConfiguration.
             * You can access the configuration with HierarchyDatapodConfiguration.getConfig()
             *
             * @return The HierarchyDatapodObject as defined by HierarchyDatapodConfiguration
             */
            public static HierarchyDatapodObject insert()
            {
                return GenericInsertions.genericSmallDatapodInsert(iowaContentSetId);
            }
        }

        /**
         * Used to insert datapods with a medium amount of content
         */
        public static class Medium
        {
            /**
             * Creates a HierarchyDatapodObject and inserts the related info into the database for Iowa (Development).
             * Pulls information from the HierarchyDatapodConfiguration.
             * You can access the configuration with HierarchyDatapodConfiguration.getConfig()
             *
             * @return The HierarchyDatapodObject as defined by HierarchyDatapodConfiguration
             */
            public static HierarchyDatapodObject insert()
            {
                return GenericInsertions.genericMediumDatapodInsert(iowaContentSetId);
            }
        }

        /**
         * Used to insert datapods with a large amount of content
         */
        public static class Large
        {
            /**
             * Creates a HierarchyDatapodObject and inserts the related info into the database for Iowa (Development).
             * Pulls information from the HierarchyDatapodConfiguration.
             * You can access the configuration with HierarchyDatapodConfiguration.getConfig()
             *
             * @return The HierarchyDatapodObject as defined by HierarchyDatapodConfiguration
             */
            public static HierarchyDatapodObject insert()
            {
                return GenericInsertions.genericLargeDatapodInsert(iowaContentSetId);
            }
        }
    }

    /**
     * Used to insert datapods into the USCA (Development) content set.
     */
    public static class USCA
    {
        static int uscaContentSetId = Integer.parseInt(ContentSets.USCA_DEVELOPMENT.getCode());

        /**
         * Used to insert datapods with a small amount of content
         */
        public static class Small
        {
            /**
             * Creates a HierarchyDatapodObject and inserts the related info into the database for Iowa (Development).
             * Pulls information from the HierarchyDatapodConfiguration.
             * You can access the configuration with HierarchyDatapodConfiguration.getConfig()
             *
             * @return The HierarchyDatapodObject as defined by HierarchyDatapodConfiguration
             */
            public static HierarchyDatapodObject insert()
            {
                return GenericInsertions.genericSmallDatapodInsert(uscaContentSetId);
            }
        }

        /**
         * Used to insert datapods with a medium amount of content
         */
        public static class Medium
        {
            /**
             * Creates a HierarchyDatapodObject and inserts the related info into the database for Iowa (Development).
             * Pulls information from the HierarchyDatapodConfiguration.
             * You can access the configuration with HierarchyDatapodConfiguration.getConfig()
             *
             * @return The HierarchyDatapodObject as defined by HierarchyDatapodConfiguration
             */
            public static HierarchyDatapodObject insert()
            {
                return GenericInsertions.genericMediumDatapodInsert(uscaContentSetId);
            }
        }

        /**
         * Used to insert datapods with a large amount of content
         */
        public static class Large
        {
            /**
             * Creates a HierarchyDatapodObject and inserts the related info into the database for Iowa (Development).
             * Pulls information from the HierarchyDatapodConfiguration.
             * You can access the configuration with HierarchyDatapodConfiguration.getConfig()
             *
             * @return The HierarchyDatapodObject as defined by HierarchyDatapodConfiguration
             */
            public static HierarchyDatapodObject insert()
            {
                return GenericInsertions.genericLargeDatapodInsert(uscaContentSetId);
            }
        }
    }

    /**
     * Used to insert datapods into the Crown Dependencies content set.
     */
    public static class CrownDependencies
    {
        static int crownDependenciesContentSetId = Integer.parseInt(ContentSets.CROWN_DEPENDENCIES.getCode());

        /**
         * Used to insert datapods with a small amount of content
         */
        public static class Small
        {
            /**
             * Creates a HierarchyDatapodObject and inserts the related info into the database for Iowa (Development).
             * Pulls information from the HierarchyDatapodConfiguration.
             * You can access the configuration with HierarchyDatapodConfiguration.getConfig()
             *
             * @return The HierarchyDatapodObject as defined by HierarchyDatapodConfiguration
             */
            public static HierarchyDatapodObject insert()
            {
                return GenericInsertions.genericSmallDatapodInsert(crownDependenciesContentSetId);
            }
        }

        /**
         * Used to insert datapods with a medium amount of content
         */
        public static class Medium
        {
            /**
             * Creates a HierarchyDatapodObject and inserts the related info into the database for Iowa (Development).
             * Pulls information from the HierarchyDatapodConfiguration.
             * You can access the configuration with HierarchyDatapodConfiguration.getConfig()
             *
             * @return The HierarchyDatapodObject as defined by HierarchyDatapodConfiguration
             */
            public static HierarchyDatapodObject insert()
            {
                return GenericInsertions.genericMediumDatapodInsert(crownDependenciesContentSetId);
            }
        }

        /**
         * Used to insert datapods with a large amount of content
         */
        public static class Large
        {
            /**
             * Creates a HierarchyDatapodObject and inserts the related info into the database for Iowa (Development).
             * Pulls information from the HierarchyDatapodConfiguration.
             * You can access the configuration with HierarchyDatapodConfiguration.getConfig()
             *
             * @return The HierarchyDatapodObject as defined by HierarchyDatapodConfiguration
             */
            public static HierarchyDatapodObject insert()
            {
                return GenericInsertions.genericLargeDatapodInsert(crownDependenciesContentSetId);
            }
        }
    }

    /**
     * Used to insert datapods into the German Legislative content set.
     */
    public static class GermanLegislative
    {
        static int germanContentSetId = Integer.parseInt(ContentSets.GERMAN_LEGISLATIVE.getCode());

        public static class Small
        {
            public static HierarchyDatapodObject insert()
            {
                return GenericInsertions.genericSmallDatapodInsert(germanContentSetId);
            }
        }
    }

    public static class RegGuidanceSummaryUS
    {
        static int regGuidanceContentSetId = Integer.parseInt(ContentSets.REG_GUIDANCE_SUMMARY_US.getCode());

        /**
         * Used to insert datapods with a small amount of content
         */
        public static class Small
        {
            /**
             * Creates a HierarchyDatapodObject and inserts the related info into the database for Reg Guidance US.
             * Pulls information from the HierarchyDatapodConfiguration.
             * You can access the configuration with HierarchyDatapodConfiguration.getConfig()
             *
             * @return The HierarchyDatapodObject as defined by HierarchyDatapodConfiguration
             */
            public static HierarchyDatapodObject insert()
            {
                return GenericInsertions.genericSmallDatapodInsert(regGuidanceContentSetId);
            }

            public static class ValidCite
            {
                public static HierarchyDatapodObject insert(int orderNum)
                {
                    HierarchyDatapodObject datapodObject = GenericInsertions.genericSmallDatapodInsert(regGuidanceContentSetId);
                    String parent = datapodObject.getChapters().get(0).getNodeUUID();
                    String parentValue = HierarchyDatabaseUtils.getNodeValueByNodeUUID(parent,String.valueOf(regGuidanceContentSetId),connection);
                    HierarchyNode sectionAdded = GenericInsertions.genericInsert(26,parent,orderNum,parentValue+".991", "QED Small Section", TargetDataMockingConstants.VALID_TEXT,Date.valueOf(DEFAULT_START_DATE), null);
                    datapodObject.addSection((SectionNode) sectionAdded);
                    return datapodObject;
                }
            }

            public static class InvalidCite
            {
                public static HierarchyDatapodObject insert(int orderNum)
                {
                    HierarchyDatapodObject datapodObject = GenericInsertions.genericSmallDatapodInsert(regGuidanceContentSetId);
                    String parent = datapodObject.getChapters().get(0).getNodeUUID();
                    String parentValue = HierarchyDatabaseUtils.getNodeValueByNodeUUID(parent,String.valueOf(regGuidanceContentSetId),connection);
                    HierarchyNode sectionAdded = GenericInsertions.genericInsert(26,parent,orderNum,parentValue+".991", "QED Small Section", TargetDataMockingConstants.INVALID_TEXT,Date.valueOf(DEFAULT_START_DATE), null);
                    datapodObject.addSection((SectionNode) sectionAdded);
                    return datapodObject;
                }
            }
        }

        /**
         * Used to insert datapods with a medium amount of content
         */
        public static class Medium
        {
            public static HierarchyDatapodObject insert()
            {
                /**
                 * Creates a HierarchyDatapodObject and inserts the related info into the database for Iowa (Development).
                 * Pulls information from the HierarchyDatapodConfiguration.
                 * You can access the configuration with HierarchyDatapodConfiguration.getConfig()
                 *
                 * @return The HierarchyDatapodObject as defined by HierarchyDatapodConfiguration
                 */
                return GenericInsertions.genericMediumDatapodInsert(regGuidanceContentSetId);
            }
        }

        /**
         * Used to insert datapods with a large amount of content
         */
        public static class Large
        {
            public static HierarchyDatapodObject insert()
            {
                /**
                 * Creates a HierarchyDatapodObject and inserts the related info into the database for Iowa (Development).
                 * Pulls information from the HierarchyDatapodConfiguration.
                 * You can access the configuration with HierarchyDatapodConfiguration.getConfig()
                 *
                 * @return The HierarchyDatapodObject as defined by HierarchyDatapodConfiguration
                 */
                return GenericInsertions.genericLargeDatapodInsert(regGuidanceContentSetId);
            }
        }
    }

    public static class CodeOfFedRegs
    {
        static int fedRegsContentSetId = Integer.parseInt(ContentSets.CODE_OF_FED_REGS_DEVELOPMENT.getCode());

        /**
         * Used to insert datapods with a small amount of content
         */
        public static class Small
        {
            /**
             * Creates a HierarchyDatapodObject and inserts the related info into the database for Iowa (Development).
             * Pulls information from the HierarchyDatapodConfiguration.
             * You can access the configuration with HierarchyDatapodConfiguration.getConfig()
             *
             * @return The HierarchyDatapodObject as defined by HierarchyDatapodConfiguration
             */
            public static HierarchyDatapodObject insert()
            {
                return GenericInsertions.genericSmallDatapodInsert(fedRegsContentSetId);
            }
        }

        /**
         * Used to insert datapods with a medium amount of content
         */
        public static class Medium
        {
            public static HierarchyDatapodObject insert()
            {/**
             * Creates a HierarchyDatapodObject and inserts the related info into the database for Iowa (Development).
             * Pulls information from the HierarchyDatapodConfiguration.
             * You can access the configuration with HierarchyDatapodConfiguration.getConfig()
             *
             * @return The HierarchyDatapodObject as defined by HierarchyDatapodConfiguration
             */
                return GenericInsertions.genericMediumDatapodInsert(fedRegsContentSetId);
            }
        }

        /**
         * Used to insert datapods with a large amount of content
         */
        public static class Large
        {
            public static HierarchyDatapodObject insert()
            {/**
             * Creates a HierarchyDatapodObject and inserts the related info into the database for Iowa (Development).
             * Pulls information from the HierarchyDatapodConfiguration.
             * You can access the configuration with HierarchyDatapodConfiguration.getConfig()
             *
             * @return The HierarchyDatapodObject as defined by HierarchyDatapodConfiguration
             */
                return GenericInsertions.genericLargeDatapodInsert(fedRegsContentSetId);
            }
        }
    }

    public static class FCAHandbook
    {
        static int  FCAContentSetId = Integer.parseInt(ContentSets.FCA_HANDBOOK.getCode());

        /**
         * Used to insert datapods with a small amount of content
         */
        public static class Small
        {
            /**
             * Creates a HierarchyDatapodObject and inserts the related info into the database for FCA Handbook.
             * Pulls information from the HierarchyDatapodConfiguration.
             * You can access the configuration with HierarchyDatapodConfiguration.getConfig()
             *
             * @return The HierarchyDatapodObject as defined by HierarchyDatapodConfiguration
             */
            public static HierarchyDatapodObject insert()
            {
                return GenericInsertions.genericSmallDatapodInsert(FCAContentSetId);
            }
        }
    }

    /**
     * Move a datapod to be a child of another node.
     * @param datapodObject The datapod to move
     * @param newParentNodeUUID The node UUID to be the parent of the datapod.
     */
    public static void moveDatapodUnderNodeUUID(HierarchyDatapodObject datapodObject, String newParentNodeUUID)
    {
        TargetDataMockingNew nodeMocker = new TargetDataMockingNew();
        int nodeOrder = nodeMocker.getMaxChildNodeOrder(getConnection(), newParentNodeUUID, 100) + 100;
        nodeMocker.deleteTocNodeLink(getConnection(), datapodObject.getAllNodes().get(0).getNodeUUID());
        nodeMocker.insertTocNodeLink(getConnection(), newParentNodeUUID, datapodObject.getAllNodes().get(0).getNodeUUID(), nodeOrder);
        commit(getConnection());
    }

    /**
     * IF YOU ARE NOT SCALE AND PERFORMANCE, YOU DO NOT NEED TO GO FURTHER IN THIS CLASS
     * THIS IS COMPLETELY BACKEND DATAPOD IMPLEMENTATION THAT IS HIDDEN FROM THE PUBLIC IMPLEMENTATION ON PURPOSE
     */

    private static class GenericInsertions
    {
        static int AN = 10033001;
        static int WL = 10054001;

        private static void updateScriptIds()
        {
            if (contentSetId == Integer.parseInt(ContentSets.IOWA_DEVELOPMENT.getCode()))
            {
                AN = 10033001;
                WL = 10054001;
            }
            else if (contentSetId == Integer.parseInt(ContentSets.USCA_DEVELOPMENT.getCode()))
            {
                AN = 30002001;
                WL = 30001001;
            }
            else if (contentSetId == Integer.parseInt(ContentSets.CROWN_DEPENDENCIES.getCode()))
            {
                AN = -1;
                WL = 11059001;
            }
            else if (contentSetId == Integer.parseInt(ContentSets.GERMAN_LEGISLATIVE.getCode()))
            {
                AN = -1;
                WL = 10054001;
            }
            else if (contentSetId == Integer.valueOf(ContentSets.REG_GUIDANCE_SUMMARY_US.getCode()))
            {
                AN = -1;
                WL = 10054001;
            }
            else if(contentSetId == Integer.valueOf(ContentSets.FCA_HANDBOOK.getCode()))
            {
                AN = -1;
                WL = 11103998;
            }
        }

        private static List<HierarchyNode> insertNodPod(String sectionParentUUID, int numBluelines)
        {
            ArrayList<HierarchyNode> nodes = new ArrayList<>();
            NODContainerNode containerNode = NOD_Container.insert(sectionParentUUID);
            XNDNode xndNode = XND.insert(containerNode.getNodeUUID());
            BLAnalysisNode analysisNode = BL_ANALYSIS.insert(xndNode.getNodeUUID());
            List<BluelineNode> bluelineNodes = Blueline.insertMultiple(numBluelines, analysisNode.getNodeUUID());
            nodes.add(containerNode);
            nodes.add(xndNode);
            nodes.add(analysisNode);
            nodes.addAll(bluelineNodes);
            return nodes;
        }

        public static HierarchyDatapodObject genericSmallDatapodInsert(int contentSetId)
        {
            TargetDataMockingNew.contentSetId = contentSetId;
            Volume.insertAllIfNeeded();
            HierarchyDatapodConfiguration config = HierarchyDatapodConfiguration.getConfig();
            HierarchyNode topNode = null;
            switch (config.getTopNodeType().ordinal())
            {
                case (8):
                    topNode = Grade.insert(config.getContentTypeAsString());
                    break;
                case (3):
                    topNode = SectionWithEqualsSign.Small.insertSingleSection(config.getContentTypeAsString(), Date.valueOf(DEFAULT_START_DATE));
                    break;
                case (16):
                    logger.warning("Top node of datapod was set as a GlossaryNode. This should be done with a SectionNode!");
                    topNode = Glossary.insertSingleGlossary(config.getContentTypeAsString());
                    break;
                default:
                    topNode = Title.insert();
                    break;
            }

            TargetDataMockingNew nodeMocker = new TargetDataMockingNew();
            nodeMocker.insertPubId(getConnection(), topNode.getNodeUUID(), pubId);

            List<HierarchyNode> parents = new ArrayList<>();
            parents.add(topNode);

            List<HierarchyNode> nextNodes = new ArrayList<>();

            // Insert Subtitles
            for (HierarchyNode parent : parents)
            {
                nextNodes.addAll(Subtitle.insertMultiple(config.getSubtitleCount(), parent.getNodeUUID()));
            }

            if (!nextNodes.isEmpty())
            {
                parents.clear();
                parents.addAll(nextNodes);
                nextNodes.clear();
            }

            // Insert Parts
            for (HierarchyNode parent : parents)
            {
                nextNodes.addAll(Part.insertMultiple(config.getPartCount(), parent.getNodeUUID(), "QED Testing Part"));
            }

            if (!nextNodes.isEmpty())
            {
                parents.clear();
                parents.addAll(nextNodes);
                nextNodes.clear();
            }

            // Insert Chapters
            for (HierarchyNode parent : parents)
            {
                nextNodes.addAll(Chapter.insertMultiple(config.getChapterCount(), parent.getNodeUUID(), config.getContentTypeAsString(), Date.valueOf(DEFAULT_START_DATE)));
            }

            if (!nextNodes.isEmpty())
            {
                parents.clear();
                parents.addAll(nextNodes);
                nextNodes.clear();
            }

            // Insert Sections
            for (HierarchyNode parent : parents)
            {
                int wipVersionCount = config.getSectionWipCount();

                String sectionType = config.getSectionType();
                if(sectionType.equalsIgnoreCase("SECTION"))
                {
                    nextNodes.addAll(SectionWithSectionSign.Small.insertMultiple(config.getSectionCount(), parent.getNodeUUID(), config.getContentTypeAsString()));
                }
                else
                {
                    Date nextStartDate = Date.valueOf(DEFAULT_START_DATE);
                    if(wipVersionCount > 1)
                    {
                        nextNodes.addAll(SectionWithEqualsSign.Small.insertMultipleWithMultipleWipVersions(config.getSectionCount(), parent.getNodeUUID(), wipVersionCount, nextStartDate));
                    }
                    else
                    {
                        nextNodes.addAll(SectionWithEqualsSign.Small.insertMultiple(config.getSectionCount(), parent.getNodeUUID(), config.getContentTypeAsString(), nextStartDate));
                    }
                }
            }

            // Insert Rules -- Rules will not have children
            for (HierarchyNode parent : parents)
            {
                Rule.insertMultiple(config.getRuleCount(), parent.getNodeUUID());
            }

            //Insert Glossary
            if(config.getGlossaryCount() > 0)
            {
                logger.warning("Datapod is inserting GlossaryNodes -- this should not happen as they should be SectionNodes");
            }

            for (HierarchyNode parent: parents)
            {
                Glossary.insertMultiple(config.getGlossaryCount(), parent.getNodeUUID());
            }

            // Insert Grades -- at this level, Grades will not have children
            for (HierarchyNode parent : parents)
            {
                Grade.insertMultiple(config.getGradeCount(), parent.getNodeUUID(), config.getContentTypeAsString());
            }

            if (!nextNodes.isEmpty())
            {
                parents.clear();
                parents.addAll(nextNodes);
                nextNodes.clear();
            }

            // Insert Bluelines
            if (config.getBluelineCount() > 0)
            {
                for (HierarchyNode parent : parents)
                {
                    insertNodPod(parent.getNodeUUID(), config.getBluelineCount());
                }
            }

            HierarchyDatapodObject datapodObject = new HierarchyDatapodObject(topNode);

            if (config.getInsertInPubNav())
            {
                List<HierarchyNode> nodes = datapodObject.getAllNodes();
                for (HierarchyNode node : nodes)
                {
                    nodeMocker.insertNodeIntoPubNav(getConnection(), node);
                }
                nodeMocker.insertPubTocNodeLink(getConnection(), IOWA_CODE_ANNOTATED_NODE_UUID, topNode.getNodeUUID(), nodeMocker.getMaxChildNodeOrder(getConnection(), IOWA_CODE_ANNOTATED_NODE_UUID, 0));
                int orderNum = 0;
                for (HierarchyNode node : nodes)
                {
                    for (HierarchyNode child : node.getChildren(getConnection()))
                    {
                        nodeMocker.insertPubTocNodeLink(getConnection(), node.getNodeUUID(), child.getNodeUUID(), orderNum++);
                    }
                    if (AN != -1)
                    {
                        nodeMocker.insertPubTocNodeScriptAssignment(getConnection(), node.getNodeUUID(), AN, contentSetId);
                    }
                    nodeMocker.insertPubTocNodeScriptAssignment(getConnection(), node.getNodeUUID(), WL, contentSetId);
                }
                commit(getConnection());
            }

            return datapodObject;
        }

        public static HierarchyDatapodObject genericMediumDatapodInsert(int contentSetId)
        {
            TargetDataMockingNew.contentSetId = contentSetId;
            Volume.insertAllIfNeeded();
            HierarchyDatapodConfiguration config = HierarchyDatapodConfiguration.getConfig();
            HierarchyNode topNode = null;
            switch (config.getTopNodeType().ordinal())
            {
                case (8):
                    topNode = Grade.insert(config.getContentTypeAsString());
                    break;
                default:
                    topNode = Title.insert();
                    break;
            }

            TargetDataMockingNew nodeMocker = new TargetDataMockingNew();
            nodeMocker.insertPubId(getConnection(), topNode.getNodeUUID(), pubId);

            List<HierarchyNode> parents = new ArrayList<>();
            parents.add(topNode);

            List<HierarchyNode> nextNodes = new ArrayList<>();

            // Insert Subtitles
            for (HierarchyNode parent : parents)
            {
                nextNodes.addAll(Subtitle.insertMultiple(config.getSubtitleCount(), parent.getNodeUUID()));
            }

            if (!nextNodes.isEmpty())
            {
                parents.clear();
                parents.addAll(nextNodes);
                nextNodes.clear();
            }

            // Insert Parts
            for (HierarchyNode parent : parents)
            {
                nextNodes.addAll(Part.insertMultiple(config.getPartCount(), parent.getNodeUUID(), "QED Testing Part"));
            }

            if (!nextNodes.isEmpty())
            {
                parents.clear();
                parents.addAll(nextNodes);
                nextNodes.clear();
            }

            // Insert Chapters
            for (HierarchyNode parent : parents)
            {
                nextNodes.addAll(Chapter.insertMultiple(config.getChapterCount(), parent.getNodeUUID(), config.getContentTypeAsString(), Date.valueOf(DEFAULT_START_DATE)));
            }

            if (!nextNodes.isEmpty())
            {
                parents.clear();
                parents.addAll(nextNodes);
                nextNodes.clear();
            }

            // Insert Sections
            for (HierarchyNode parent : parents)
            {
                if (config.getSectionType().equalsIgnoreCase("EQUALS"))
                {
                    int wipVersionCount = config.getSectionWipCount();
                    if (wipVersionCount > 1)
                    {
                        nextNodes.addAll(SectionWithEqualsSign.Medium.insertMultipleWithMultipleWipVersions(config.getSectionCount(), parent.getNodeUUID(), wipVersionCount, Date.valueOf(DEFAULT_START_DATE)));
                    }
                    else
                    {
                        nextNodes.addAll(SectionWithEqualsSign.Medium.insertMultiple(config.getSectionCount(), parent.getNodeUUID(), config.getContentTypeAsString()));
                    }
                }
                else
                {
                    nextNodes.addAll(SectionWithSectionSign.Small.insertMultiple(config.getSectionCount(), parent.getNodeUUID(), config.getContentTypeAsString()));
                }
            }

            // Insert Rules -- Rules will not have children
            for (HierarchyNode parent : parents)
            {
                Rule.insertMultiple(config.getRuleCount(), parent.getNodeUUID());
            }

            // Insert Grades -- at this level, Grades will not have children
            for (HierarchyNode parent : parents)
            {
                Grade.insertMultiple(config.getGradeCount(), parent.getNodeUUID(), config.getContentTypeAsString());
            }

            if (!nextNodes.isEmpty())
            {
                parents.clear();
                parents.addAll(nextNodes);
                nextNodes.clear();
            }

            // Insert Bluelines
            if (config.getBluelineCount() > 0)
            {
                for (HierarchyNode parent : parents)
                {
                    insertNodPod(parent.getNodeUUID(), config.getBluelineCount());
                }
            }

            HierarchyDatapodObject datapodObject = new HierarchyDatapodObject(topNode);

            if (config.getInsertInPubNav())
            {
                List<HierarchyNode> nodes = datapodObject.getAllNodes();
                for (HierarchyNode node : nodes)
                {
                    nodeMocker.insertNodeIntoPubNav(getConnection(), node);
                }
                int orderNum = 0;
                nodeMocker.insertPubTocNodeLink(getConnection(), IOWA_CODE_ANNOTATED_NODE_UUID, topNode.getNodeUUID(), nodeMocker.getMaxChildNodeOrder(getConnection(), IOWA_CODE_ANNOTATED_NODE_UUID, 0));
                for (HierarchyNode node : nodes)
                {
                    for (HierarchyNode child : node.getChildren(getConnection()))
                    {
                        nodeMocker.insertPubTocNodeLink(getConnection(), node.getNodeUUID(), child.getNodeUUID(), orderNum++);
                    }
                    if (AN != -1)
                    {
                        nodeMocker.insertPubTocNodeScriptAssignment(getConnection(), node.getNodeUUID(), AN, contentSetId);
                    }
                    nodeMocker.insertPubTocNodeScriptAssignment(getConnection(), node.getNodeUUID(), WL, contentSetId);
                }
            }

            return datapodObject;
        }

        public static HierarchyDatapodObject genericLargeDatapodInsert(int contentSetId)
        {
            TargetDataMockingNew.contentSetId = contentSetId;
            Volume.insertAllIfNeeded();
            HierarchyDatapodConfiguration config = HierarchyDatapodConfiguration.getConfig();
            HierarchyNode topNode = null;
            switch (config.getTopNodeType().ordinal())
            {
                case (8):
                    topNode = Grade.insert(config.getContentTypeAsString());
                    break;
                default:
                    topNode = Title.insert();
                    break;
            }

            TargetDataMockingNew nodeMocker = new TargetDataMockingNew();
            nodeMocker.insertPubId(getConnection(), topNode.getNodeUUID(), pubId);

            List<HierarchyNode> parents = new ArrayList<>();
            parents.add(topNode);

            List<HierarchyNode> nextNodes = new ArrayList<>();

            // Insert Subtitles
            for (HierarchyNode parent : parents)
            {
                nextNodes.addAll(Subtitle.insertMultiple(config.getSubtitleCount(), parent.getNodeUUID()));
            }

            if (!nextNodes.isEmpty())
            {
                parents.clear();
                parents.addAll(nextNodes);
                nextNodes.clear();
            }

            // Insert Parts
            for (HierarchyNode parent : parents)
            {
                nextNodes.addAll(Part.insertMultiple(config.getPartCount(), parent.getNodeUUID(), "QED Testing Part"));
            }

            if (!nextNodes.isEmpty())
            {
                parents.clear();
                parents.addAll(nextNodes);
                nextNodes.clear();
            }

            // Insert Chapters
            for (HierarchyNode parent : parents)
            {
                nextNodes.addAll(Chapter.insertMultiple(config.getChapterCount(), parent.getNodeUUID(), config.getContentTypeAsString(), Date.valueOf(DEFAULT_START_DATE)));
            }

            if (!nextNodes.isEmpty())
            {
                parents.clear();
                parents.addAll(nextNodes);
                nextNodes.clear();
            }

            // Insert Sections
            for (HierarchyNode parent : parents)
            {
                if (config.getSectionType().equalsIgnoreCase("EQUALS"))
                {
                    int wipVersionCount = config.getSectionWipCount();
                    if (wipVersionCount > 1)
                    {
                        nextNodes.addAll(SectionWithEqualsSign.Large.insertMultipleWithMultipleWipVersions(config.getSectionCount(), parent.getNodeUUID(), wipVersionCount, Date.valueOf(DEFAULT_START_DATE)));
                    }
                    else
                    {
                        nextNodes.addAll(SectionWithEqualsSign.Large.insertMultiple(config.getSectionCount(), parent.getNodeUUID(), config.getContentTypeAsString()));
                    }
                }
                else
                {
                    // TODO: Update SectionWithSectionSign to Large
                    nextNodes.addAll(SectionWithSectionSign.Small.insertMultiple(config.getSectionCount(), parent.getNodeUUID(), config.getContentTypeAsString()));
                }
            }

            // Insert Rules -- Rules will not have children
            for (HierarchyNode parent : parents)
            {
                Rule.insertMultiple(config.getRuleCount(), parent.getNodeUUID());
            }

            // Insert Grades -- at this level, Grades will not have children
            for (HierarchyNode parent : parents)
            {
                Grade.insertMultiple(config.getGradeCount(), parent.getNodeUUID(), config.getContentTypeAsString());
            }

            if (!nextNodes.isEmpty())
            {
                parents.clear();
                parents.addAll(nextNodes);
                nextNodes.clear();
            }

            // Insert Bluelines
            if (config.getBluelineCount() > 0)
            {
                for (HierarchyNode parent : parents)
                {
                    insertNodPod(parent.getNodeUUID(), config.getBluelineCount());
                }
            }

            HierarchyDatapodObject datapodObject = new HierarchyDatapodObject(topNode);

            if (config.getInsertInPubNav())
            {
                List<HierarchyNode> nodes = datapodObject.getAllNodes();
                for (HierarchyNode node : nodes)
                {
                    nodeMocker.insertNodeIntoPubNav(getConnection(), node);
                }
                int orderNum = 0;
                nodeMocker.insertPubTocNodeLink(getConnection(), IOWA_CODE_ANNOTATED_NODE_UUID, topNode.getNodeUUID(), nodeMocker.getMaxChildNodeOrder(getConnection(), IOWA_CODE_ANNOTATED_NODE_UUID, 0));
                for (HierarchyNode node : nodes)
                {
                    for (HierarchyNode child : node.getChildren(getConnection()))
                    {
                        nodeMocker.insertPubTocNodeLink(getConnection(), node.getNodeUUID(), child.getNodeUUID(), orderNum++);
                    }
                    if (AN != -1)
                    {
                        nodeMocker.insertPubTocNodeScriptAssignment(getConnection(), node.getNodeUUID(), AN, contentSetId);
                    }
                    nodeMocker.insertPubTocNodeScriptAssignment(getConnection(), node.getNodeUUID(), WL, contentSetId);
                }
            }

            return datapodObject;
        }


        public static HierarchyNode genericInsert(int keywordId, String parentUUID, int orderNum, String headNum, String title, String xml, Date startDate, Date endDate)
        {
            NodeType nodeType = convertKeywordIdToNodeType(keywordId);
            Connection connection = getConnection();
            TargetDataMockingNew nodeMocker = new TargetDataMockingNew();
            if (!nodeType.equals(NodeType.NOD_CONTAINER))
            {
                String contentUUID = genericTocNodeInsert(nodeType, keywordId, nodeMocker, headNum, title, orderNum, startDate, endDate);
                String nodeUUID = HierarchyDatabaseUtils.getNodeUuidWithContentUuid(connection, contentUUID);
                int nodeOrder = orderNum;
                if (parentUUID.equals(IOWA_CODE_ANNOTATED_NODE_UUID) || parentUUID.equals(UNITED_STATES_CODE_ANNOTATED_NODE_UUID) ||
                        parentUUID.equals(CROWN_DEPENDENCIES_SET_NODE_UUID) || parentUUID.equals(REG_GUIDANCE_SUMMARY_US_SET_NODE_UUID) ||
                        parentUUID.equals(CODE_OF_FEDERAL_REGULATIONS_UUID)|| parentUUID.equals(FCA_HANDBOOK_UUID))
                {
                    nodeOrder = nodeMocker.getMaxChildNodeOrder(getConnection(), parentUUID, orderNum) + 1;
                }
                nodeMocker.insertTocNodeLink(connection, parentUUID, nodeUUID, nodeOrder);
                genericTocContentInsert(nodeType, nodeMocker, contentUUID, xml, title);
                nodeMocker.insertTocContentPublishingStatus(connection, contentUUID, 0, contentSetId);
                updateScriptIds();
                if (AN != -1)
                {
                    nodeMocker.insertTocNodeScriptAssignment(connection, nodeUUID, AN, contentSetId);
                }
                nodeMocker.insertTocNodeScriptAssignment(connection, nodeUUID, WL, contentSetId);
                commit(connection);
                if(nodeType.equals(NodeType.SECTION) && HierarchyDatapodConfiguration.getConfig().getContentType().equals(HierarchyDatapodContentType.GLOSSARY))
                {
                    nodeMocker.insertGlossaryTerm(connection, CommonDataMocking.generateUUID(), contentSetId, contentUUID, nodeMocker.getValFromNodeUUID(connection, nodeUUID)); //This is the extra glossary stuff we are testing
                }
                return createNode(nodeType, nodeUUID, contentUUID);
            }
            else
            {
                String nodeUUID = nodeMocker.insertTocNodeNODCONTAINER(connection, keywordId, contentSetId, title, ++currentHID);
                nodeMocker.insertTocNodeLink(connection, parentUUID, nodeUUID, 100);
                return createNode(nodeType, nodeUUID, null);
            }
        }

        private static HierarchyNode createNode(NodeType nodeType, String nodeUUID, String contentUUID, boolean... isHistoricalVersion)
        {
            switch (nodeType.ordinal())
            {
                case (0):
                    return new TitleNode(nodeUUID, contentUUID);
                case (1):
                    return new SubtitleNode(nodeUUID, contentUUID);
                case (2):
                    return new ChapterNode(nodeUUID, contentUUID);
                case (3):
                    return new SectionNode(nodeUUID, contentUUID, isHistoricalVersion);
                case (4):
                    return new NODContainerNode(nodeUUID, null);
                case (5):
                    return new XNDNode(nodeUUID, contentUUID);
                case (6):
                    return new BLAnalysisNode(nodeUUID, contentUUID);
                case (7):
                    return new BluelineNode(nodeUUID, contentUUID);
                case (8):
                    return new GradeNode(nodeUUID, contentUUID);
                case (9):
                    return new RuleNode(nodeUUID, contentUUID);
                case (10):
                    return new PartNode(nodeUUID, contentUUID);
                case (16):
                    return new GlossaryNode(nodeUUID, contentUUID);
                default:
                    logger.severe("Somehow we reached 'default' in TargetDataMockingNew.createNode() -- this probably means you added a NodeType but didn't edit TargetDataMockingNew to allow for it");
            }
            return null;
        }

        private static void genericTocContentInsert(NodeType nodeType, TargetDataMockingNew nodeMocker, String contentUUID, String xml, String title)
        {
            Connection connection = getConnection();
            switch (nodeType.ordinal())
            {
                case (0):
                    nodeMocker.insertTocContentTITLE(connection, contentUUID, xml);
                    break;
                case (1):
                    nodeMocker.insertTocContentSUBTITLE(connection, contentUUID, xml);
                    break;
                case (2):
                    nodeMocker.insertTocContentCHAPTER(connection, contentUUID, xml);
                    break;
                case (3):
                    nodeMocker.insertTocContentSECTION(connection, contentUUID, xml, title, 0);
                    break;
                case (4):
                    // NOD Containers do not have content
                    break;
                case (5):
                    nodeMocker.insertTocContentXND(connection, contentUUID, xml, title);
                    break;
                case (6):
                    nodeMocker.insertTocContentBLAnalysis(connection, contentUUID, xml, title);
                    break;
                case (7):
                    nodeMocker.insertTocContentBlueline(connection, contentUUID, xml, title);
                    break;
                case (8):
                    nodeMocker.insertTocContentGRADE(connection, contentUUID, xml);
                    break;
                case (9):
                    nodeMocker.insertTocContentRULE(connection, contentUUID, xml, title);
                    break;
                case (10):
                    nodeMocker.insertTocContentPART(connection, contentUUID, xml, title);
                    break;
                case (16):
                    nodeMocker.insertTocContentGLOSSARY(connection, contentUUID, xml, title,0);
                    break;
                default:
                    logger.severe("Somehow we reached 'default' in TargetDataMockingNew.genericTocContentInsert() -- this probably means you added a NodeType but didn't edit TargetDataMockingNew to allow for it");
            }
        }

        private static String genericTocNodeInsert(NodeType nodeType, int keywordId, TargetDataMockingNew nodeMocker, String headNum, String title, int orderNum, Date startDate, Date endDate)
        {
            Connection connection = getConnection();
            switch (nodeType.ordinal())
            {
                case (0):
                    return nodeMocker.insertTocNodeTITLE(connection, keywordId, contentSetId, ++currentHID, headNum);
                case (1):
                    return nodeMocker.insertTocNodeSUBTITLE(connection, keywordId, contentSetId, ++currentHID, headNum);
                case (2):
                    if(HierarchyDatapodConfiguration.getConfig().getNumberOfPastChapterVersions() > 0 || HierarchyDatapodConfiguration.getConfig().getNumberOfFutureChapterVersions() > 0)
                    {
                        return nodeMocker.insertTocNodeCHAPTERWithEndDate(connection, keywordId, contentSetId, headNum, title, orderNum, currentHID, startDate, endDate);
                    }
                    else
                    {
                        return nodeMocker.insertTocNodeCHAPTER(connection, keywordId, contentSetId, ++currentHID, headNum, title);
                    }
                case (3):
                    if(startDate == null)
                    {
                        startDate = Date.valueOf(DEFAULT_START_DATE);
                    }

                    int nodeTypeDB = HierarchyDatapodConfiguration.getConfig().getContentType() == HierarchyDatapodContentType.GLOSSARY ? 11 : 2;

                    if(HierarchyDatapodConfiguration.getConfig().getNumberOfPastSectionVersions() > 0 || HierarchyDatapodConfiguration.getConfig().getNumberOfFutureSectionVersions() > 0)
                    {
                        return nodeMocker.insertTocNodeSECTIONWithEndDate(connection, keywordId, contentSetId, headNum, title, orderNum, ++currentHID, startDate, endDate, nodeTypeDB);
                    }
                    else
                    {
                        return nodeMocker.insertTocNodeSECTION(connection, keywordId, contentSetId, headNum, title, orderNum, ++currentHID, startDate, nodeTypeDB);
                    }
                case (4):
                    // NOD Containers should never be here
                    logger.warning("genericTocNodeInsert was called for a NOD CONTAINER (this should not happen)");
                    break;
                case (5):
                    return nodeMocker.insertTocNodeXND(connection, keywordId, contentSetId, title, ++currentHID);
                case (6):
                    return nodeMocker.insertTocNodeBLAnalysis(connection, keywordId, contentSetId, title, ++currentHID);
                case (7):
                    return nodeMocker.insertTocNodeBlueline(connection, keywordId, contentSetId, title, ++currentHID, headNum, orderNum);
                case (8):
                    return nodeMocker.insertTocNodeGRADE(connection, keywordId, contentSetId, ++currentHID, title);
                case (9):
                    return nodeMocker.insertTocNodeRULE(connection, keywordId, contentSetId, headNum, title, orderNum, ++currentHID);
                case (10):
                    return nodeMocker.insertTocNodePART(connection, keywordId, contentSetId, ++currentHID, headNum, title);
                case(16):
                    return nodeMocker.insertTocNodeGLOSSARY(connection, keywordId, contentSetId, ++currentHID, title);
                default:
                    logger.severe("Somehow we reached 'default' in TargetDataMockingNew.genericTocNodeInsert() -- this probably means you added a NodeType but didn't edit TargetDataMockingNew to allow for it");
                    return null;
            }
            return null;
        }

    }

    private static class Part
    {
        static int keywordId = 14;

        public static List<PartNode> insertMultiple(int numParts, String parentNodeUUID, String title)
        {
            ArrayList<PartNode> partNodes = new ArrayList<>();

            for (int i = 0; i < numParts; i++)
            {
                String headNum = "" + (i + 1);
                int orderNum = 100 * (i + 1);
                partNodes.add(insert(parentNodeUUID, orderNum, title, headNum));
            }

            return partNodes;
        }

        public static PartNode insert(String parentNodeUUID, int orderNum, String title, String headNum)
        {
            String text = String.format(TargetDataMockingConstants.PART_TEXT_UNFORMATTED, headNum, title);
            return (PartNode) GenericInsertions.genericInsert(keywordId, parentNodeUUID, orderNum, headNum, title, text, Date.valueOf(DEFAULT_START_DATE), null);
        }
    }

    private static class Grade
    {
        static int keywordId = 25; // grade keyword in DB
        static String defaultParentNodeUUID = IOWA_CODE_ANNOTATED_NODE_UUID;

        private static void updateDefaultParentNodeUUID()
        {
            if (contentSetId == Integer.parseInt(ContentSets.IOWA_DEVELOPMENT.getCode()))
            {
                defaultParentNodeUUID = IOWA_CODE_ANNOTATED_NODE_UUID;
            }
            else if (contentSetId == Integer.parseInt(ContentSets.USCA_DEVELOPMENT.getCode()))
            {
                defaultParentNodeUUID = UNITED_STATES_CODE_ANNOTATED_NODE_UUID;
            }
            else if (contentSetId == Integer.parseInt(ContentSets.CROWN_DEPENDENCIES.getCode()))
            {
                defaultParentNodeUUID = CROWN_DEPENDENCIES_SET_NODE_UUID;
            }
            else if (contentSetId == Integer.parseInt(ContentSets.REG_GUIDANCE_SUMMARY_US.getCode()))
            {
                defaultParentNodeUUID = REG_GUIDANCE_SUMMARY_US_SET_NODE_UUID;
            }
            else if (contentSetId == Integer.parseInt(ContentSets.CODE_OF_FED_REGS_DEVELOPMENT.getCode()))
            {
                defaultParentNodeUUID = CODE_OF_FEDERAL_REGULATIONS_UUID;
            }
            else if (contentSetId == Integer.parseInt(ContentSets.GERMAN_LEGISLATIVE.getCode()))
            {
                defaultParentNodeUUID = "IF48D1800E35C11E29FD2AB4E4B792D22"; // SET RB.GERM
            }
            else if (contentSetId == Integer.parseInt(ContentSets.FCA_HANDBOOK.getCode()))
            {
                defaultParentNodeUUID = FCA_HANDBOOK_UUID;
            }
        }

        public static List<GradeNode> insertMultiple(int numGrades, String parentNodeUUID, String contentType)
        {
            ArrayList<GradeNode> gradeNodes = new ArrayList<>();

            for (int i = 0; i < numGrades; i++)
            {
                int orderNum = 100 * (i + 1);
                String title = CommonDataMocking.generateUUID() + " QED Testing Document";
                gradeNodes.add(insert(parentNodeUUID, orderNum, contentType, title));
            }

            return gradeNodes;
        }

        public static GradeNode insert(String parentNodeUUID, int orderNum, String contentTypeString, String title)
        {
            HierarchyDatapodContentType contentType = HierarchyDatapodContentType.valueOf(contentTypeString);
            if (contentType == HierarchyDatapodContentType.RISK || contentType == HierarchyDatapodContentType.BMP_IMAGE || contentType == HierarchyDatapodContentType.PDF_IMAGE)
            {
                return (GradeNode) GenericInsertions.genericInsert(keywordId, parentNodeUUID, orderNum, null, title, TargetDataMockingConstants.GRADE_RISK_TEXT, Date.valueOf(DEFAULT_START_DATE), null);
            }
            else
            {
                return (GradeNode) GenericInsertions.genericInsert(keywordId, parentNodeUUID, orderNum, null, title, TargetDataMockingConstants.GRADE_TEXT, Date.valueOf(DEFAULT_START_DATE), null);

            }
        }

        public static GradeNode insert(String contentType)
        {
            updateDefaultParentNodeUUID();
            return insert(defaultParentNodeUUID, 0, contentType, CommonDataMocking.generateUUID() + " QED Testing Document");
        }
    }

    private static class Title
    {
        static int keywordId = 7; // title keyword in DB
        static String defaultParentNodeUUID = IOWA_CODE_ANNOTATED_NODE_UUID;

        private static void updateDefaultParentNodeUUID()
        {
            if (contentSetId == Integer.parseInt(ContentSets.IOWA_DEVELOPMENT.getCode()))
            {
                defaultParentNodeUUID = IOWA_CODE_ANNOTATED_NODE_UUID;
            }
            else if (contentSetId == Integer.parseInt(ContentSets.USCA_DEVELOPMENT.getCode()))
            {
                defaultParentNodeUUID = UNITED_STATES_CODE_ANNOTATED_NODE_UUID;
            }
            else if (contentSetId == Integer.parseInt(ContentSets.CROWN_DEPENDENCIES.getCode()))
            {
                defaultParentNodeUUID = CROWN_DEPENDENCIES_SET_NODE_UUID;
            }
            else if (contentSetId == Integer.parseInt(ContentSets.REG_GUIDANCE_SUMMARY_US.getCode()))
            {
                defaultParentNodeUUID = REG_GUIDANCE_SUMMARY_US_SET_NODE_UUID;
            }
            else if (contentSetId == Integer.parseInt(ContentSets.CODE_OF_FED_REGS_DEVELOPMENT.getCode()))
            {
                defaultParentNodeUUID = CODE_OF_FEDERAL_REGULATIONS_UUID;
            }
            else if (contentSetId == Integer.parseInt(ContentSets.GERMAN_LEGISLATIVE.getCode()))
            {
                defaultParentNodeUUID = "IF48D1800E35C11E29FD2AB4E4B792D22"; // SET RB.GERM
            }
            else if (contentSetId == Integer.parseInt(ContentSets.FCA_HANDBOOK.getCode()))
            {
                defaultParentNodeUUID = FCA_HANDBOOK_UUID;
            }
        }

        public static List<TitleNode> insertMultiple(int numTitles)
        {
            ArrayList<TitleNode> titleNodes = new ArrayList<>();

            for (int i = 0; i < numTitles; i++)
            {
                titleNodes.add(insert());
            }

            return titleNodes;
        }

        public static TitleNode insert()
        {
            updateDefaultParentNodeUUID();
            if (contentSetId == Integer.parseInt(ContentSets.CODE_OF_FED_REGS_DEVELOPMENT.getCode()))
            {
                return (TitleNode) GenericInsertions.genericInsert(keywordId, Title.defaultParentNodeUUID, 100, "99", null, TargetDataMockingConstants.TITLE_TEXT_FED_REGS, Date.valueOf(DEFAULT_START_DATE), null);
            }
            else
            {
                return (TitleNode) GenericInsertions.genericInsert(keywordId, Title.defaultParentNodeUUID, 100, "XCIX", null, TargetDataMockingConstants.TITLE_TEXT, Date.valueOf(DEFAULT_START_DATE), null);
            }
        }
    }

    private static class Subtitle
    {
        static int keywordId = 24; // subtitle keyword in DB
        static String defaultParentNodeUUID = "I7A35F76014EE11DA8AC5CD53670E6B4E"; // T. I STATE SOVEREIGNTY AND MANAGEMENT

        public static List<SubtitleNode> insertMultiple(int numSubtitles, String titleParentUUID)
        {
            ArrayList<SubtitleNode> subtitleNodes = new ArrayList<>();

            for (int i = 0; i < numSubtitles; i++)
            {
                String headNum = "" + (i + 1);
                int orderNum = 100 * (i + 1);
                subtitleNodes.add(insert(titleParentUUID, orderNum, headNum));
            }

            return subtitleNodes;
        }

        public static SubtitleNode insert(String titleParentUUID, int orderNum, String headNum)
        {
            return (SubtitleNode) GenericInsertions.genericInsert(keywordId, titleParentUUID, orderNum, headNum, null, TargetDataMockingConstants.SUBTITLE_TEXT, Date.valueOf(DEFAULT_START_DATE), null);
        }

        public static SubtitleNode insert()
        {
            return insert(Subtitle.defaultParentNodeUUID, 0, "1");
        }
    }

    private static class Chapter
    {
        static int keywordId = 4; // chapter keyword in DB
        static String defaultParentNodeUUID = "I7A3E5BD014EE11DA8AC5CD53670E6B4E"; // Subt. 1 SOVEREIGNTY

        static int AN = 10033001; // AN Script number in the DB
        static int WL = 10054001; // WL Script number in the DB

        private static void updateScriptIds()
        {
            if (contentSetId == Integer.valueOf(ContentSets.IOWA_DEVELOPMENT.getCode()))
            {
                AN = 10033001;
                WL = 10054001;
            }
            else if (contentSetId == Integer.valueOf(ContentSets.USCA_DEVELOPMENT.getCode()))
            {
                AN = 30002001;
                WL = 30001001;
            }
            else if (contentSetId == Integer.valueOf(ContentSets.CROWN_DEPENDENCIES.getCode()))
            {
                AN = -1;
                WL = 11059001;
            }
            else if (contentSetId == Integer.valueOf(ContentSets.REG_GUIDANCE_SUMMARY_US.getCode()))
            {
                AN = -1;
                WL = 10054001;
            }
            else if (contentSetId == Integer.valueOf(ContentSets.FCA_HANDBOOK.getCode()))
            {
                AN= -1;
                WL= 11103998;
            }
        }

        public static List<ChapterNode> insertMultiple(int numChapters, String subtitleParentUUID, String contentTypeString, Date startDate)
        {
            ArrayList<ChapterNode> chapterNodes = new ArrayList<>();
            Date endDate = null;

            for (int i = 0; i < numChapters; i++)
            {
                long startingHeadNum = 1;
                String headNum = CommonDataMocking.generateUUID() + (startingHeadNum + i);
                int orderNum = 1000 * (i + 1);

                int historicalFutureChapterCount = HierarchyDatapodConfiguration.getConfig().getNumberOfFutureChapterVersions();
                int historicalPastChapterCount = HierarchyDatapodConfiguration.getConfig().getNumberOfPastChapterVersions();
                if(historicalFutureChapterCount > 0 || historicalPastChapterCount > 0)
                {
                    startDate = Date.valueOf(DateAndTimeUtils.getCurrentDateyyyyMMddWithHyphen());
                }
                if(HierarchyDatapodConfiguration.getConfig().getNumberOfFutureChapterVersions() > 0)
                {
                    endDate = Date.valueOf(startDate.toLocalDate().plusMonths(1));
                }

                ChapterNode node = insertWithHeadNumber(subtitleParentUUID, headNum, orderNum, contentTypeString, startDate, endDate);
                chapterNodes.add(node);

                if(historicalFutureChapterCount > 0 || historicalPastChapterCount > 0)
                {
                    List<HierarchyNode> historicalVersions = insertHistoricalVersions(historicalFutureChapterCount, historicalPastChapterCount, node, subtitleParentUUID, orderNum);
                    for(HierarchyNode hierarchyNode : historicalVersions)
                    {
                        chapterNodes.add((ChapterNode) hierarchyNode);
                    }
                }
            }
            return chapterNodes;
        }

        public static ChapterNode insertWithHeadNumber(String subtitleParentUUID, String headNum, int orderNum, String contentTypeString, Date startDate, Date endDate)
        {
            String text = null;
            HierarchyDatapodContentType contentType = HierarchyDatapodContentType.valueOf(contentTypeString);
            if (contentType == HierarchyDatapodContentType.RISK || contentType == HierarchyDatapodContentType.BMP_IMAGE || contentType == HierarchyDatapodContentType.PDF_IMAGE)
            {
                text = String.format(TargetDataMockingConstants.CHAPTER_TEXT_RISK_UNFORMATTED, headNum);
            }
            else if (contentType == HierarchyDatapodContentType.FED_REGS)
            {
                try
                {
                    headNum = RomanNumeralConversion.integerToRomanNumeral(Integer.parseInt(headNum));
                }
                catch (NumberFormatException e)
                {
                    // Removes 32-character UUID in head num in order to allow for Roman Numeral conversion
                    headNum = headNum.substring(33);
                    headNum = RomanNumeralConversion.integerToRomanNumeral(Integer.parseInt(headNum));
                }
                text = String.format(TargetDataMockingConstants.CHAPTER_TEXT_RISK_UNFORMATTED, headNum);
            }
            else
            {
                text = String.format(TargetDataMockingConstants.CHAPTER_TEXT_UNFORMATTED, "JURISDICTION OF QED", "JURISDICTION OF QED", headNum, "JURISDICTION OF QED");
            }

            return (ChapterNode) GenericInsertions.genericInsert(keywordId, subtitleParentUUID, orderNum, headNum, "JURISDICTION OF QED", text, startDate, endDate);

        }

        public static ChapterNode insert(String subtitleParentUUID, int orderNum, String contentTypeString)
        {
            return insertWithHeadNumber(subtitleParentUUID, "999", orderNum, contentTypeString, Date.valueOf(DEFAULT_START_DATE), null);
        }

        public static ChapterNode insert(String contentTypeString)
        {
            return insert(defaultParentNodeUUID, 0, contentTypeString);
        }
    }

    private static class Volume
    {
        static int AN = 10033001; // AN Script number in the DB
        static int WL = 10054001; // WL Script number in the DB

        static int publicationId = 99212;

        private static void updateScriptIdsAndPublicationId()
        {
            if (contentSetId == Integer.valueOf(ContentSets.IOWA_DEVELOPMENT.getCode()))
            {
                AN = 10033001;
                WL = 10054001;
                publicationId = 99212;
            }
            else if (contentSetId == Integer.valueOf(ContentSets.USCA_DEVELOPMENT.getCode()))
            {
                AN = 30002001;
                WL = 30001001;
                publicationId = 29178;
            }
            else if (contentSetId == Integer.valueOf(ContentSets.CROWN_DEPENDENCIES.getCode()))
            {
                AN = -1;
                WL = 11059001;
                publicationId = 139720;
            }
        }

        public static void insertAllIfNeeded()
        {
            updateScriptIdsAndPublicationId();
            TargetDataMockingNew nodeMocker = new TargetDataMockingNew();
            Connection connection = getConnection();
            if (!nodeMocker.mockedBOVExists(connection, contentSetId))
            {
                BOV.insert();
            }
            if (!nodeMocker.mockedEOVExists(connection, contentSetId))
            {
                EOV.insert();
            }
            if (!nodeMocker.mockedVolumeExists(connection, contentSetId))
            {
                insertVolume();
            }
        }

        private static void insertVolume()
        {
            TargetDataMockingNew nodeMocker = new TargetDataMockingNew();
            Connection connection = getConnection();
            String bovUUID = nodeMocker.getBOVUUID(connection, contentSetId);
            String eovUUID = nodeMocker.getEOVUUID(connection, contentSetId);
            // force a new volume id to avoid duplicate primary keys (resulting in SQLException)
            int volumeId = 999999;
            while (nodeMocker.volumeExistsWithId(connection, volumeId))
            {
                volumeId--;
            }
            nodeMocker.insertVolume(connection, volumeId, publicationId, bovUUID, eovUUID, contentSetId);
        }

        private static class BOV
        {
            public static void insert()
            {
                TargetDataMockingNew nodeMocker = new TargetDataMockingNew();
                String contentUUID = nodeMocker.insertTocNodeBOV(getConnection(), ++currentHID, contentSetId);
                String nodeUUID = HierarchyDatabaseUtils.getNodeUuidWithContentUuid(getConnection(), contentUUID);
                nodeMocker.insertTocContentBOV(getConnection(), contentUUID, TargetDataMockingConstants.BOV_TEXT, contentSetId);
                nodeMocker.insertTocContentPublishingStatus(getConnection(), contentUUID, 0, contentSetId);
                nodeMocker.insertTocNodeScriptAssignment(getConnection(), nodeUUID, AN, contentSetId);
                nodeMocker.insertTocNodeScriptAssignment(getConnection(), nodeUUID, WL, contentSetId);
                commit(getConnection());
            }
        }

        private static class EOV
        {
            public static void insert()
            {
                TargetDataMockingNew nodeMocker = new TargetDataMockingNew();
                String contentUUID = nodeMocker.insertTocNodeEOV(getConnection(), ++currentHID, contentSetId);
                String nodeUUID = HierarchyDatabaseUtils.getNodeUuidWithContentUuid(getConnection(), contentUUID);
                nodeMocker.insertTocContentEOV(getConnection(), contentUUID, TargetDataMockingConstants.EOV_TEXT, contentSetId);
                nodeMocker.insertTocContentPublishingStatus(getConnection(), contentUUID, 0, contentSetId);
                nodeMocker.insertTocNodeScriptAssignment(getConnection(), nodeUUID, AN, contentSetId);
                nodeMocker.insertTocNodeScriptAssignment(getConnection(), nodeUUID, WL, contentSetId);
                commit(getConnection());
            }
        }


    }

    private static class SectionWithEqualsSign
    {
        static int keywordId = 26; // section keyword in DB
        static int AN = 10033001; // AN Script number in the DB
        static int WL = 10054001; // WL Script number in the DB

        public static class Small
        {
            public static SectionNode insertSingleSection (String contentTypeString , Date startDate)
            {
                Title.updateDefaultParentNodeUUID();
                return insertWithHeadNumber(Title.defaultParentNodeUUID, "QED Testing" ,100, contentTypeString, startDate, null);
            }

            public static List<SectionNode> insertMultiple(int numSections, String chapterParentUUID, String contentTypeString, Date startDate)
            {
                ArrayList<SectionNode> sectionNodes = new ArrayList<>();
                Date endDate = null;

                HierarchyDatapodContentType contentType = HierarchyDatapodContentType.valueOf(contentTypeString);
                boolean isFedRegs = contentType == HierarchyDatapodContentType.FED_REGS;

                for (int i = 0; i < numSections; i++)
                {
                    TargetDataMockingNew nodeMocker = new TargetDataMockingNew();
                    String headNum;
                    if (!isFedRegs)
                    {
                        headNum = nodeMocker.getValFromNodeUUID(getConnection(), chapterParentUUID) + ".99" + i;
                    }
                    else
                    {
                        headNum = nodeMocker.getValFromNodeUUID(getConnection(), chapterParentUUID) + "." + (i + 1);
                    }
                    int orderNum = 1000 * (i + 1);
                    int historicalFutureSectionCount = HierarchyDatapodConfiguration.getConfig().getNumberOfFutureSectionVersions();
                    int historicalPastSectionCount = HierarchyDatapodConfiguration.getConfig().getNumberOfPastSectionVersions();
                    int historicalCount = historicalFutureSectionCount + historicalPastSectionCount;

                    if(historicalFutureSectionCount > 0 || historicalPastSectionCount > 0)
                    {
                        startDate = Date.valueOf(DateAndTimeUtils.getCurrentDateyyyyMMddWithHyphen());
                    }
                    if(historicalFutureSectionCount > 0)
                    {
                        endDate = Date.valueOf(startDate.toLocalDate().plusMonths(1));
                    }
                    SectionNode node = insertWithHeadNumber(chapterParentUUID, headNum, orderNum, contentTypeString, startDate, endDate);
                    sectionNodes.add(node);

                    if(historicalCount > 0)
                    {
                        List<HierarchyNode> historicalVersions = insertHistoricalVersions(historicalFutureSectionCount, historicalPastSectionCount, node, chapterParentUUID, orderNum);
                        for(HierarchyNode hierarchyNode : historicalVersions)
                        {
                            sectionNodes.add((SectionNode) hierarchyNode);
                        }
                    }
                }

                return sectionNodes;
            }

            public static List<SectionNode> insertMultipleWithMultipleWipVersions(int numSections, String chapterParentUUID, int numWipVersions, Date date)
            {
                ArrayList<SectionNode> sectionNodes = new ArrayList<>();

                for (int i = 0; i < numSections; i++)
                {
                    String headNum = "999.99" + i;
                    int orderNum = 1000 * (i + 1);
                    sectionNodes.add(insertWithMultipleWipVersions(chapterParentUUID, headNum, orderNum, numWipVersions, date));
                }

                return sectionNodes;
            }

            private static void updateScriptIds()
            {
                if (contentSetId == Integer.valueOf(ContentSets.IOWA_DEVELOPMENT.getCode()))
                {
                    AN = 10033001;
                    WL = 10054001;
                }
                else if (contentSetId == Integer.valueOf(ContentSets.USCA_DEVELOPMENT.getCode()))
                {
                    AN = 30002001;
                    WL = 30001001;
                }
                else if (contentSetId == Integer.valueOf(ContentSets.CROWN_DEPENDENCIES.getCode()))
                {
                    AN = -1;
                    WL = 11059001;
                }
                else if (contentSetId == Integer.valueOf(ContentSets.REG_GUIDANCE_SUMMARY_US.getCode()))
                {
                    AN = -1;
                    WL = 10054001;
                }
                else if (contentSetId == Integer.valueOf(ContentSets.FCA_HANDBOOK.getCode()))
                {
                    AN= -1;
                    WL= 11103998;
                }
            }

            public static SectionNode insert(String chapterParentUUID, int orderNum)
            {
                return (SectionNode) GenericInsertions.genericInsert(keywordId, chapterParentUUID, orderNum, "999.99", "QED Small Section", TargetDataMockingConstants.SMALL_TEXT, Date.valueOf(DEFAULT_START_DATE), null);
            }

            public static SectionNode insertWithHeadNumber(String chapterParentUUID, String headNumber, int orderNum, String contentTypeString, Date startDate, Date endDate)
            {
                HierarchyDatapodContentType contentType = HierarchyDatapodContentType.valueOf(contentTypeString);
                if (contentType == HierarchyDatapodContentType.RISK)
                {
                    return (SectionNode) GenericInsertions.genericInsert(keywordId, chapterParentUUID, orderNum, headNumber, "QED Small Section", String.format(TargetDataMockingConstants.SMALL_TEXT_RISK_UNFORMATTED, headNumber, headNumber), startDate, endDate);
                }
                else if (contentType == HierarchyDatapodContentType.RISK_SUBSECTIONS_REG_GUIDANCE_US)
                {
                    return (SectionNode) GenericInsertions.genericInsert(keywordId, chapterParentUUID, orderNum, headNumber, "QED Small Section", String.format(TargetDataMockingConstants.SMALL_TEXT_RISK_REG_GUIDANCE_SUBSECTIONS, headNumber, headNumber), startDate, endDate);
                }
                else if (contentType == HierarchyDatapodContentType.BMP_IMAGE)
                {
                    return (SectionNode) GenericInsertions.genericInsert(keywordId, chapterParentUUID, orderNum, headNumber, "QED Small Section", String.format(TargetDataMockingConstants.BMP_IMAGE_TEXT_UNFORMATTED, headNumber, headNumber), startDate, endDate);
                }
                else if (contentType == HierarchyDatapodContentType.PDF_IMAGE)
                {
                    return (SectionNode) GenericInsertions.genericInsert(keywordId, chapterParentUUID, orderNum, headNumber, "QED Small Section", String.format(TargetDataMockingConstants.PDF_IMAGE_TEXT_UNFORMATTED, headNumber, headNumber), startDate, endDate);
                }
                else if (contentType == HierarchyDatapodContentType.FED_REGS)
                {
                    return (SectionNode) GenericInsertions.genericInsert(keywordId, chapterParentUUID, orderNum, headNumber, "QED Small Section", String.format(TargetDataMockingConstants.SMALL_TEXT_FED_REGS_UNFORMATTED, headNumber, headNumber), startDate, endDate);
                }
                else if (contentType == HierarchyDatapodContentType.GERMAN)
                {
                    return (SectionNode) GenericInsertions.genericInsert(keywordId, chapterParentUUID, orderNum, headNumber, "QED Small Section", String.format(TargetDataMockingConstants.SMALL_TEXT_GERMAN_UNFORMATTED, headNumber, headNumber, "QED Small Section"), startDate, endDate);
                }
                else if (contentType == HierarchyDatapodContentType.GLOSSARY)
                {
                    return (SectionNode) GenericInsertions.genericInsert(keywordId, chapterParentUUID, orderNum, "", "QED Test", TargetDataMockingConstants.NEW_GLOSSARY_TEXT, startDate, endDate);
                }
                else
                {
                    return (SectionNode) GenericInsertions.genericInsert(keywordId, chapterParentUUID, orderNum, headNumber, "QED Small Section", String.format(TargetDataMockingConstants.SMALL_TEXT_UNFORMATTED, headNumber, headNumber), startDate, endDate);
                }
            }

            public static SectionNode insertWithMultipleWipVersions(String chapterParentUUID, String headNumber, int orderNum, int numWipVersions, Date date)
            {
                Connection connection = getConnection();
                TargetDataMockingNew nodeMocker = new TargetDataMockingNew();

                String contentUUID = nodeMocker.insertTocNodeSECTION(connection, keywordId, contentSetId, headNumber, "QED Small Section", orderNum, ++currentHID, date, 2);
                String nodeUUID = HierarchyDatabaseUtils.getNodeUuidWithContentUuid(connection, contentUUID);
                nodeMocker.insertTocNodeLink(connection, chapterParentUUID, nodeUUID, orderNum);

                for (int i = 1; i < numWipVersions; i++)
                {
                    nodeMocker.insertTocContentByWipVersionNum(connection, contentUUID, i - 1, 0);
                    nodeMocker.insertTocContentPublishingStatusByWipVersion(connection, contentUUID, i - 1, 0);
                }

                nodeMocker.insertTocContentSECTION(connection, contentUUID, String.format(TargetDataMockingConstants.SMALL_TEXT_UNFORMATTED, headNumber, headNumber), headNumber + " QED Small Section", numWipVersions - 1);
                nodeMocker.insertTocContentPublishingStatusWithWipVersionNum(connection, contentUUID, 0, contentSetId, numWipVersions - 1);
                nodeMocker.insertTocNodeScriptAssignment(connection, nodeUUID, AN, contentSetId);
                nodeMocker.insertTocNodeScriptAssignment(connection, nodeUUID, WL, contentSetId);
                commit(connection);
                return new SectionNode(nodeUUID, contentUUID);
            }
        }

        public static class Medium
        {

            public static List<SectionNode> insertMultiple(int numSections, String chapterParentUUID, String contentTypeString)
            {
                ArrayList<SectionNode> sectionNodes = new ArrayList<>();
                Date startDate = Date.valueOf(DateAndTimeUtils.getCurrentDateyyyyMMddWithHyphen());
                Date endDate = null;

                HierarchyDatapodContentType contentType = HierarchyDatapodContentType.valueOf(contentTypeString);
                boolean isFedRegs = contentType == HierarchyDatapodContentType.FED_REGS;

                for (int i = 0; i < numSections; i++)
                {
                    TargetDataMockingNew nodeMocker = new TargetDataMockingNew();
                    String headNum;
                    if (!isFedRegs)
                    {
                        headNum = nodeMocker.getValFromNodeUUID(getConnection(), chapterParentUUID) + ".99" + i;
                    }
                    else
                    {
                        headNum = nodeMocker.getValFromNodeUUID(getConnection(), chapterParentUUID) + "." + (i + 1);
                    }
                    int orderNum = 1000 * (i + 1);
                    int historicalFutureSectionCount = HierarchyDatapodConfiguration.getConfig().getNumberOfFutureSectionVersions();
                    int historicalPastSectionCount = HierarchyDatapodConfiguration.getConfig().getNumberOfPastSectionVersions();
                    int historicalCount = historicalFutureSectionCount + historicalPastSectionCount;

                    if(historicalFutureSectionCount > 0 || historicalPastSectionCount > 0)
                    {
                        startDate = Date.valueOf(DateAndTimeUtils.getCurrentDateyyyyMMddWithHyphen());
                    }
                    if(historicalFutureSectionCount > 0)
                    {
                        endDate = Date.valueOf(startDate.toLocalDate().plusMonths(1));
                    }
                    SectionNode node = insertWithHeadNumber(chapterParentUUID, headNum, orderNum, contentTypeString, startDate);
                    sectionNodes.add(node);

                    if(historicalCount > 0)
                    {
                        List<HierarchyNode> historicalVersions = insertHistoricalVersions(historicalFutureSectionCount, historicalPastSectionCount, node, chapterParentUUID, orderNum);
                        for(HierarchyNode hierarchyNode : historicalVersions)
                        {
                            sectionNodes.add((SectionNode) hierarchyNode);
                        }
                    }
                }

                return sectionNodes;
            }

            public static List<SectionNode> insertMultipleWithMultipleWipVersions(int numSections, String chapterParentUUID, int numWipVersions, Date date)
            {
                ArrayList<SectionNode> sectionNodes = new ArrayList<>();

                for (int i = 0; i < numSections; i++)
                {
                    String headNum = "999.99" + i;
                    int orderNum = 100 * (i + 1);
                    sectionNodes.add(insertWithMultipleWipVersions(chapterParentUUID, headNum, orderNum, numWipVersions, date));
                }

                return sectionNodes;
            }

            public static SectionNode insert(String chapterParentUUID, int orderNum)
            {
                return (SectionNode) GenericInsertions.genericInsert(keywordId, chapterParentUUID, orderNum, "999.99", "QED Medium Section", TargetDataMockingConstants.NEW_MEDIUM_TEXT, Date.valueOf(DEFAULT_START_DATE), null);
            }

            public static SectionNode insertWithHeadNumber(String chapterParentUUID, String headNumber, int orderNum, String contentTypeString, Date startDate)
            {
                HierarchyDatapodContentType contentType = HierarchyDatapodContentType.valueOf(contentTypeString);
                if (contentType == HierarchyDatapodContentType.RISK)
                {
                    // We don't have a Medium RISK content (yet?) -- for now we just run the small one
                    return (SectionNode) GenericInsertions.genericInsert(keywordId, chapterParentUUID, orderNum, headNumber, "QED Small Section", String.format(TargetDataMockingConstants.SMALL_TEXT_RISK_UNFORMATTED, headNumber, headNumber), startDate, null);
                }
                else if (contentType == HierarchyDatapodContentType.BMP_IMAGE)
                {
                    // The point of the IMAGE content types are to have images with no text, so there is no medium variant
                    return (SectionNode) GenericInsertions.genericInsert(keywordId, chapterParentUUID, orderNum, headNumber, "QED Small Section", String.format(TargetDataMockingConstants.BMP_IMAGE_TEXT_UNFORMATTED, headNumber, headNumber), startDate, null);
                }
                else if (contentType == HierarchyDatapodContentType.PDF_IMAGE)
                {
                    // The point of the IMAGE content types are to have images with no text, so there is no medium variant
                    return (SectionNode) GenericInsertions.genericInsert(keywordId, chapterParentUUID, orderNum, headNumber, "QED Small Section", String.format(TargetDataMockingConstants.PDF_IMAGE_TEXT_UNFORMATTED, headNumber, headNumber), startDate, null);
                }
                else if (contentType == HierarchyDatapodContentType.FED_REGS)
                {
                    // We don't have a Medium document for Fed Regs (yet?)
                    return (SectionNode) GenericInsertions.genericInsert(keywordId, chapterParentUUID, orderNum, headNumber, "QED Small Section", String.format(TargetDataMockingConstants.SMALL_TEXT_FED_REGS_UNFORMATTED, headNumber, headNumber), startDate, null);
                }
                else if (contentType == HierarchyDatapodContentType.GERMAN)
                {
                    return (SectionNode) GenericInsertions.genericInsert(keywordId, chapterParentUUID, orderNum, headNumber, "QED Small Section", String.format(TargetDataMockingConstants.SMALL_TEXT_GERMAN_UNFORMATTED, headNumber, headNumber, "QED Small Section"), startDate, null);
                }
                else if (contentType == HierarchyDatapodContentType.GLOSSARY)
                {
                    return (SectionNode) GenericInsertions.genericInsert(keywordId, chapterParentUUID, orderNum, "", "QED Test", TargetDataMockingConstants.NEW_GLOSSARY_TEXT, startDate, null);
                }
                else
                {
                    return (SectionNode) GenericInsertions.genericInsert(keywordId, chapterParentUUID, orderNum, headNumber, "QED Medium Section", String.format(TargetDataMockingConstants.NEW_MEDIUM_TEXT_UNFORMATTED, headNumber, headNumber), startDate, null);
                }
            }

            public static SectionNode insertWithMultipleWipVersions(String chapterParentUUID, String headNumber, int orderNum, int numWipVersions, Date date)
            {
                Connection connection = getConnection();
                TargetDataMockingNew nodeMocker = new TargetDataMockingNew();

                String contentUUID = nodeMocker.insertTocNodeSECTION(connection, keywordId, contentSetId, headNumber, "QED Medium Section", orderNum, ++currentHID, date, 2);
                String nodeUUID = HierarchyDatabaseUtils.getNodeUuidWithContentUuid(connection, contentUUID);
                nodeMocker.insertTocNodeLink(connection, chapterParentUUID, nodeUUID, orderNum);

                for (int i = 1; i < numWipVersions; i++)
                {
                    nodeMocker.insertTocContentByWipVersionNum(connection, contentUUID, i - 1, 0);
                    nodeMocker.insertTocContentPublishingStatusByWipVersion(connection, contentUUID, i - 1, 0);
                }

                nodeMocker.insertTocContentSECTION(connection, contentUUID, String.format(TargetDataMockingConstants.NEW_MEDIUM_TEXT_UNFORMATTED, headNumber, headNumber), headNumber + " QED Medium Section", numWipVersions - 1);
                nodeMocker.insertTocContentPublishingStatusWithWipVersionNum(connection, contentUUID, 0, contentSetId, numWipVersions - 1);
                nodeMocker.insertTocNodeScriptAssignment(connection, nodeUUID, AN, contentSetId);
                nodeMocker.insertTocNodeScriptAssignment(connection, nodeUUID, WL, contentSetId);
                commit(connection);
                return new SectionNode(nodeUUID, contentUUID);
            }
        }

        public static class Large
        {

            public static List<SectionNode> insertMultiple(int numSections, String chapterParentUUID, String contentTypeString)
            {
                ArrayList<SectionNode> sectionNodes = new ArrayList<>();
                Date startDate = Date.valueOf(DateAndTimeUtils.getCurrentDateyyyyMMddWithHyphen());
                Date endDate = null;


                HierarchyDatapodContentType contentType = HierarchyDatapodContentType.valueOf(contentTypeString);
                boolean isFedRegs = contentType == HierarchyDatapodContentType.FED_REGS;

                for (int i = 0; i < numSections; i++)
                {
                    TargetDataMockingNew nodeMocker = new TargetDataMockingNew();
                    String headNum;
                    if (!isFedRegs)
                    {
                        headNum = nodeMocker.getValFromNodeUUID(getConnection(), chapterParentUUID) + ".99" + i;
                    }
                    else
                    {
                        headNum = nodeMocker.getValFromNodeUUID(getConnection(), chapterParentUUID) + "." + (i + 1);
                    }
                    int orderNum = 1000 * (i + 1);
                    int historicalFutureSectionCount = HierarchyDatapodConfiguration.getConfig().getNumberOfFutureSectionVersions();
                    int historicalPastSectionCount = HierarchyDatapodConfiguration.getConfig().getNumberOfPastSectionVersions();
                    int historicalCount = historicalFutureSectionCount + historicalPastSectionCount;

                    if(historicalFutureSectionCount > 0 || historicalPastSectionCount > 0)
                    {
                        startDate = Date.valueOf(DateAndTimeUtils.getCurrentDateyyyyMMddWithHyphen());
                    }
                    if(historicalFutureSectionCount > 0)
                    {
                        endDate = Date.valueOf(startDate.toLocalDate().plusMonths(1));
                    }
                    SectionNode node = insertWithHeadNumber(chapterParentUUID, headNum, orderNum, contentTypeString, startDate);
                    sectionNodes.add(node);

                    if(historicalCount > 0)
                    {
                        List<HierarchyNode> historicalVersions = insertHistoricalVersions(historicalFutureSectionCount, historicalPastSectionCount, node, chapterParentUUID, orderNum);
                        for(HierarchyNode hierarchyNode : historicalVersions)
                        {
                            sectionNodes.add((SectionNode) hierarchyNode);
                        }
                    }
                }

                return sectionNodes;
            }

            public static List<SectionNode> insertMultipleWithMultipleWipVersions(int numSections, String chapterParentUUID, int numWipVersions, Date date)
            {
                ArrayList<SectionNode> sectionNodes = new ArrayList<>();

                for (int i = 0; i < numSections; i++)
                {
                    String headNum = "999.99" + i;
                    int orderNum = 100 * (i + 1);
                    sectionNodes.add(insertWithMultipleWipVersions(chapterParentUUID, headNum, orderNum, numWipVersions, date));
                }

                return sectionNodes;
            }

            public static SectionNode insert(String chapterParentUUID, int orderNum)
            {
                return (SectionNode) GenericInsertions.genericInsert(keywordId, chapterParentUUID, orderNum, "999.99", "QED Large Section", String.format(TargetDataMockingConstants.LARGE_TEXT_UNFORMATTED, "999.99", "999.99"), Date.valueOf(DEFAULT_START_DATE), null);
            }

            public static SectionNode insertWithHeadNumber(String chapterParentUUID, String headNumber, int orderNum, String contentTypeString, Date startDate)
            {
                HierarchyDatapodContentType contentType = HierarchyDatapodContentType.valueOf(contentTypeString);
                if (contentType == HierarchyDatapodContentType.RISK)
                {
                    // We don't have a Large RISK content (yet?) -- for now we just run the small one
                    return (SectionNode) GenericInsertions.genericInsert(keywordId, chapterParentUUID, orderNum, headNumber, "QED Small Section", String.format(TargetDataMockingConstants.SMALL_TEXT_RISK_UNFORMATTED, headNumber, headNumber), startDate, null);
                }
                else if (contentType == HierarchyDatapodContentType.BMP_IMAGE)
                {
                    // The point of the IMAGE content types are to have images with no text, so there is no large variant
                    return (SectionNode) GenericInsertions.genericInsert(keywordId, chapterParentUUID, orderNum, headNumber, "QED Small Section", String.format(TargetDataMockingConstants.BMP_IMAGE_TEXT_UNFORMATTED, headNumber, headNumber), startDate, null);
                }
                else if (contentType == HierarchyDatapodContentType.PDF_IMAGE)
                {
                    // The point of the IMAGE content types are to have images with no text, so there is no large variant
                    return (SectionNode) GenericInsertions.genericInsert(keywordId, chapterParentUUID, orderNum, headNumber, "QED Small Section", String.format(TargetDataMockingConstants.PDF_IMAGE_TEXT_UNFORMATTED, headNumber, headNumber), startDate, null);
                }
                else if (contentType == HierarchyDatapodContentType.FED_REGS)
                {
                    // We don't have a Large document for Fed Regs (yet?)
                    return (SectionNode) GenericInsertions.genericInsert(keywordId, chapterParentUUID, orderNum, headNumber, "QED Small Section", String.format(TargetDataMockingConstants.SMALL_TEXT_FED_REGS_UNFORMATTED, headNumber, headNumber), startDate, null);
                }
                else if (contentType == HierarchyDatapodContentType.GLOSSARY)
                {
                    return (SectionNode) GenericInsertions.genericInsert(keywordId, chapterParentUUID, orderNum, "", "QED Test", TargetDataMockingConstants.NEW_GLOSSARY_TEXT, startDate, null);
                }
                else
                {
                    return (SectionNode) GenericInsertions.genericInsert(keywordId, chapterParentUUID, orderNum, headNumber, "QED Large Section", String.format(TargetDataMockingConstants.LARGE_TEXT_UNFORMATTED, headNumber, headNumber), startDate, null);
                }
            }

            public static SectionNode insertWithMultipleWipVersions(String chapterParentUUID, String headNumber, int orderNum, int numWipVersions, Date date)
            {
                Connection connection = getConnection();
                TargetDataMockingNew nodeMocker = new TargetDataMockingNew();

                String contentUUID = nodeMocker.insertTocNodeSECTION(connection, keywordId, contentSetId, headNumber, "QED Medium Section", orderNum, ++currentHID, date, 2);
                String nodeUUID = HierarchyDatabaseUtils.getNodeUuidWithContentUuid(connection, contentUUID);
                nodeMocker.insertTocNodeLink(connection, chapterParentUUID, nodeUUID, orderNum);

                for (int i = 1; i < numWipVersions; i++)
                {
                    nodeMocker.insertTocContentByWipVersionNum(connection, contentUUID, i - 1, 0);
                    nodeMocker.insertTocContentPublishingStatusByWipVersion(connection, contentUUID, i - 1, 0);
                }

                nodeMocker.insertTocContentSECTION(connection, contentUUID, String.format(TargetDataMockingConstants.LARGE_TEXT_UNFORMATTED, headNumber, headNumber), headNumber + " QED Medium Section", numWipVersions - 1);
                nodeMocker.insertTocContentPublishingStatusWithWipVersionNum(connection, contentUUID, 0, contentSetId, numWipVersions - 1);
                nodeMocker.insertTocNodeScriptAssignment(connection, nodeUUID, AN, contentSetId);
                nodeMocker.insertTocNodeScriptAssignment(connection, nodeUUID, WL, contentSetId);
                commit(connection);
                return new SectionNode(nodeUUID, contentUUID);
            }
        }
    }

    private static class SectionWithSectionSign
    {
        static int keywordId = 17; // section keyword in DB
        static int AN = 10033001; // AN Script number in the DB
        static int WL = 10054001; // WL Script number in the DB

        public static class Small
        {

            public static List<SectionNode> insertMultiple(int numSections, String chapterParentUUID, String contentTypeString)
            {
                ArrayList<SectionNode> sectionNodes = new ArrayList<>();

                HierarchyDatapodContentType contentType = HierarchyDatapodContentType.valueOf(contentTypeString);
                boolean isFedRegs = contentType == HierarchyDatapodContentType.FED_REGS;

                for (int i = 0; i < numSections; i++)
                {
                    TargetDataMockingNew nodeMocker = new TargetDataMockingNew();
                    String headNum;
                    if (!isFedRegs)
                    {
                        headNum = nodeMocker.getValFromNodeUUID(getConnection(), chapterParentUUID) + ".99" + i;
                    }
                    else
                    {
                        headNum = nodeMocker.getValFromNodeUUID(getConnection(), chapterParentUUID) + "." + (i + 1);
                    }
                    int orderNum = 1000 * (i + 1);
                    SectionNode node = insertWithHeadNumber(chapterParentUUID, headNum, orderNum, contentTypeString);
                    sectionNodes.add(node);
                }

                return sectionNodes;
            }

            public static List<SectionNode> insertMultipleWithMultipleWipVersions(int numSections, String chapterParentUUID, int numWipVersions, Date date)
            {
                ArrayList<SectionNode> sectionNodes = new ArrayList<>();

                for (int i = 0; i < numSections; i++)
                {
                    String headNum = "999.99" + i;
                    int orderNum = 100 * (i + 1);
                    sectionNodes.add(insertWithMultipleWipVersions(chapterParentUUID, headNum, orderNum, numWipVersions, date));
                }

                return sectionNodes;
            }

            public static SectionNode insert(String chapterParentUUID, int orderNum)
            {
                return (SectionNode) GenericInsertions.genericInsert(keywordId, chapterParentUUID, orderNum, "999.99", "QED Small Section", TargetDataMockingConstants.SMALL_TEXT, Date.valueOf(DEFAULT_START_DATE), null);
            }

            public static SectionNode insertWithHeadNumber(String chapterParentUUID, String headNumber, int orderNum, String contentTypeString)
            {
                HierarchyDatapodContentType contentType = HierarchyDatapodContentType.valueOf(contentTypeString);
                if (contentType == HierarchyDatapodContentType.RISK)
                {
                    return (SectionNode) GenericInsertions.genericInsert(keywordId, chapterParentUUID, orderNum, headNumber, "QED Small Section", String.format(TargetDataMockingConstants.SMALL_TEXT_RISK_UNFORMATTED, headNumber, headNumber), Date.valueOf(DEFAULT_START_DATE), null);
                }
                else if (contentType == HierarchyDatapodContentType.BMP_IMAGE)
                {
                    return (SectionNode) GenericInsertions.genericInsert(keywordId, chapterParentUUID, orderNum, headNumber, "QED Small Section", String.format(TargetDataMockingConstants.BMP_IMAGE_TEXT_UNFORMATTED, headNumber, headNumber), Date.valueOf(DEFAULT_START_DATE), null);
                }
                else if (contentType == HierarchyDatapodContentType.PDF_IMAGE)
                {
                    return (SectionNode) GenericInsertions.genericInsert(keywordId, chapterParentUUID, orderNum, headNumber, "QED Small Section", String.format(TargetDataMockingConstants.PDF_IMAGE_TEXT_UNFORMATTED, headNumber, headNumber), Date.valueOf(DEFAULT_START_DATE), null);
                }
                else if (contentType == HierarchyDatapodContentType.FED_REGS)
                {
                    return (SectionNode) GenericInsertions.genericInsert(keywordId, chapterParentUUID, orderNum, headNumber, "QED Small Section", String.format(TargetDataMockingConstants.SMALL_TEXT_FED_REGS_UNFORMATTED, headNumber, headNumber), Date.valueOf(DEFAULT_START_DATE), null);
                }
                else if (contentType == HierarchyDatapodContentType.GERMAN)
                {
                    return (SectionNode) GenericInsertions.genericInsert(keywordId, chapterParentUUID, orderNum, headNumber, "QED Small Section", String.format(TargetDataMockingConstants.SMALL_TEXT_GERMAN_UNFORMATTED, headNumber, headNumber, "QED Small Section"), Date.valueOf(DEFAULT_START_DATE), null);
                }
                else
                {
                    return (SectionNode) GenericInsertions.genericInsert(keywordId, chapterParentUUID, orderNum, headNumber, "QED Small Section", String.format(TargetDataMockingConstants.SMALL_TEXT_UNFORMATTED, headNumber, headNumber), Date.valueOf(DEFAULT_START_DATE), null);
                }
            }

            public static SectionNode insertWithMultipleWipVersions(String chapterParentUUID, String headNumber, int orderNum, int numWipVersions, Date date)
            {
                Connection connection = getConnection();
                TargetDataMockingNew nodeMocker = new TargetDataMockingNew();

                String contentUUID = nodeMocker.insertTocNodeSECTION(connection, keywordId, contentSetId, headNumber, "QED Small Section", orderNum, ++currentHID, date, 2);
                String nodeUUID = HierarchyDatabaseUtils.getNodeUuidWithContentUuid(connection, contentUUID);
                nodeMocker.insertTocNodeLink(connection, chapterParentUUID, nodeUUID, orderNum);

                for (int i = 1; i < numWipVersions; i++)
                {
                    nodeMocker.insertTocContentByWipVersionNum(connection, contentUUID, i - 1, 0);
                    nodeMocker.insertTocContentPublishingStatusByWipVersion(connection, contentUUID, i - 1, 0);
                }

                nodeMocker.insertTocContentSECTION(connection, contentUUID, String.format(TargetDataMockingConstants.SMALL_TEXT_UNFORMATTED, headNumber, headNumber), headNumber + " QED Small Section", numWipVersions - 1);
                nodeMocker.insertTocContentPublishingStatusWithWipVersionNum(connection, contentUUID, 0, contentSetId, numWipVersions - 1);
                nodeMocker.insertTocNodeScriptAssignment(connection, nodeUUID, AN, contentSetId);
                nodeMocker.insertTocNodeScriptAssignment(connection, nodeUUID, WL, contentSetId);
                commit(connection);
                return new SectionNode(nodeUUID, contentUUID);
            }
        }

        public static class Medium
        {

            public static List<SectionNode> insertMultiple(int numSections, String chapterParentUUID, String contentTypeString)
            {
                ArrayList<SectionNode> sectionNodes = new ArrayList<>();

                HierarchyDatapodContentType contentType = HierarchyDatapodContentType.valueOf(contentTypeString);
                boolean isFedRegs = contentType == HierarchyDatapodContentType.FED_REGS;

                for (int i = 0; i < numSections; i++)
                {
                    TargetDataMockingNew nodeMocker = new TargetDataMockingNew();
                    String headNum;
                    if (!isFedRegs)
                    {
                        headNum = nodeMocker.getValFromNodeUUID(getConnection(), chapterParentUUID) + ".99" + i;
                    }
                    else
                    {
                        headNum = nodeMocker.getValFromNodeUUID(getConnection(), chapterParentUUID) + "." + (i + 1);
                    }
                    int orderNum = 100 * (i + 1);
                    sectionNodes.add(insertWithHeadNumber(chapterParentUUID, headNum, orderNum, contentTypeString));
                }

                return sectionNodes;
            }

            public static List<SectionNode> insertMultipleWithMultipleWipVersions(int numSections, String chapterParentUUID, int numWipVersions, Date date)
            {
                ArrayList<SectionNode> sectionNodes = new ArrayList<>();

                for (int i = 0; i < numSections; i++)
                {
                    String headNum = "999.99" + i;
                    int orderNum = 100 * (i + 1);
                    sectionNodes.add(insertWithMultipleWipVersions(chapterParentUUID, headNum, orderNum, numWipVersions, date));
                }

                return sectionNodes;
            }

            public static SectionNode insert(String chapterParentUUID, int orderNum)
            {
                return (SectionNode) GenericInsertions.genericInsert(keywordId, chapterParentUUID, orderNum, "999.99", "QED Medium Section", TargetDataMockingConstants.NEW_MEDIUM_TEXT, Date.valueOf(DEFAULT_START_DATE), null);
            }

            public static SectionNode insertWithHeadNumber(String chapterParentUUID, String headNumber, int orderNum, String contentTypeString)
            {
                HierarchyDatapodContentType contentType = HierarchyDatapodContentType.valueOf(contentTypeString);
                if (contentType == HierarchyDatapodContentType.RISK)
                {
                    // We don't have a Medium RISK content (yet?) -- for now we just run the small one
                    return (SectionNode) GenericInsertions.genericInsert(keywordId, chapterParentUUID, orderNum, headNumber, "QED Small Section", String.format(TargetDataMockingConstants.SMALL_TEXT_RISK_UNFORMATTED, headNumber, headNumber), Date.valueOf(DEFAULT_START_DATE), null);
                }
                else if (contentType == HierarchyDatapodContentType.BMP_IMAGE)
                {
                    // The point of the IMAGE content types are to have images with no text, so there is no medium variant
                    return (SectionNode) GenericInsertions.genericInsert(keywordId, chapterParentUUID, orderNum, headNumber, "QED Small Section", String.format(TargetDataMockingConstants.BMP_IMAGE_TEXT_UNFORMATTED, headNumber, headNumber), Date.valueOf(DEFAULT_START_DATE), null);
                }
                else if (contentType == HierarchyDatapodContentType.PDF_IMAGE)
                {
                    // The point of the IMAGE content types are to have images with no text, so there is no medium variant
                    return (SectionNode) GenericInsertions.genericInsert(keywordId, chapterParentUUID, orderNum, headNumber, "QED Small Section", String.format(TargetDataMockingConstants.PDF_IMAGE_TEXT_UNFORMATTED, headNumber, headNumber), Date.valueOf(DEFAULT_START_DATE), null);
                }
                else if (contentType == HierarchyDatapodContentType.FED_REGS)
                {
                    // We don't have a Medium document for Fed Regs (yet?)
                    return (SectionNode) GenericInsertions.genericInsert(keywordId, chapterParentUUID, orderNum, headNumber, "QED Small Section", String.format(TargetDataMockingConstants.SMALL_TEXT_FED_REGS_UNFORMATTED, headNumber, headNumber), Date.valueOf(DEFAULT_START_DATE), null);
                }
                else if (contentType == HierarchyDatapodContentType.GERMAN)
                {
                    return (SectionNode) GenericInsertions.genericInsert(keywordId, chapterParentUUID, orderNum, headNumber, "QED Small Section", String.format(TargetDataMockingConstants.SMALL_TEXT_GERMAN_UNFORMATTED, headNumber, headNumber, "QED Small Section"), Date.valueOf(DEFAULT_START_DATE), null);
                }
                else
                {
                    return (SectionNode) GenericInsertions.genericInsert(keywordId, chapterParentUUID, orderNum, headNumber, "QED Medium Section", String.format(TargetDataMockingConstants.NEW_MEDIUM_TEXT_UNFORMATTED, headNumber, headNumber), Date.valueOf(DEFAULT_START_DATE), null);
                }
            }

            public static SectionNode insertWithMultipleWipVersions(String chapterParentUUID, String headNumber, int orderNum, int numWipVersions, Date date)
            {
                Connection connection = getConnection();
                TargetDataMockingNew nodeMocker = new TargetDataMockingNew();

                String contentUUID = nodeMocker.insertTocNodeSECTION(connection, keywordId, contentSetId, headNumber, "QED Medium Section", orderNum, ++currentHID, date, 2);
                String nodeUUID = HierarchyDatabaseUtils.getNodeUuidWithContentUuid(connection, contentUUID);
                nodeMocker.insertTocNodeLink(connection, chapterParentUUID, nodeUUID, orderNum);

                for (int i = 1; i < numWipVersions; i++)
                {
                    nodeMocker.insertTocContentByWipVersionNum(connection, contentUUID, i - 1, 0);
                    nodeMocker.insertTocContentPublishingStatusByWipVersion(connection, contentUUID, i - 1, 0);
                }

                nodeMocker.insertTocContentSECTION(connection, contentUUID, String.format(TargetDataMockingConstants.NEW_MEDIUM_TEXT_UNFORMATTED, headNumber, headNumber), headNumber + " QED Medium Section", numWipVersions - 1);
                nodeMocker.insertTocContentPublishingStatusWithWipVersionNum(connection, contentUUID, 0, contentSetId, numWipVersions - 1);
                nodeMocker.insertTocNodeScriptAssignment(connection, nodeUUID, AN, contentSetId);
                nodeMocker.insertTocNodeScriptAssignment(connection, nodeUUID, WL, contentSetId);
                commit(connection);
                return new SectionNode(nodeUUID, contentUUID);
            }
        }
    }

    private static class Rule
    {
        static int keywordId = 16; // Rule keyword in DB
        static String defaultParentNodeUUID = "I7A44283014EE11DA8AC5CD53670E6B4E"; // Ch. 1 SOVEREIGNTY AND JURISDICTION OF THE STATE

        public static List<RuleNode> insertMultiple(int numRules, String parentUUID)
        {
            ArrayList<RuleNode> ruleNodes = new ArrayList<>();

            for (int i = 0; i < numRules; i++)
            {
                String headNum = String.valueOf(i + 1);
                int orderNum = 100 * (i + 1);
                ruleNodes.add(insert(parentUUID, orderNum, headNum));
            }

            return ruleNodes;
        }

        public static RuleNode insert(String chapterParentUUID, int orderNum, String headNum)
        {
            return (RuleNode) GenericInsertions.genericInsert(keywordId, chapterParentUUID, orderNum, headNum, "QED Rule", String.format(TargetDataMockingConstants.RULE_TEXT_UNFORMATTED, headNum, headNum, "QED Rule"), Date.valueOf(DEFAULT_START_DATE), null);
        }

    }

    private static class NOD_Container
    {
        static int keywordId = 110; // NOD CONTAINER keyword in DB
        static String defaultParentNodeUUID = "I7A48E32014EE11DA8AC5CD53670E6B4E"; // = 1.1 SOVEREIGNTY AND JURISDICTION OF THE STATE

        public static NODContainerNode insert(String sectionParentUUID)
        {
            return (NODContainerNode) GenericInsertions.genericInsert(keywordId, sectionParentUUID, 100, null, CommonDataMocking.generateUUID(), null, null, null);
        }

        public static NODContainerNode insert()
        {
            return insert(defaultParentNodeUUID);
        }
    }

    private static class XND
    {
        static int keywordId = 107; // XND keyword in DB
        static String defaultParentNodeUUID = "I18B4FB201AF311DAB310FB76B2E4F553"; // NOD Container under = 1.1

        public static XNDNode insert(String nodContainerParentUUID)
        {
            return (XNDNode) GenericInsertions.genericInsert(keywordId, nodContainerParentUUID, 100, null, " ", TargetDataMockingConstants.XND_TEXT, Date.valueOf(DEFAULT_START_DATE), null);
        }

        public static XNDNode insert()
        {
            return insert(defaultParentNodeUUID);
        }
    }

    private static class BL_ANALYSIS
    {
        static int keywordId = 102; // BL Analysis keyword in DB
        static String defaultParentNodeUUID = "I0CF1E600264911DCB07AD634E02ED2C4"; // XND under = 1.1

        public static BLAnalysisNode insert(String xndParentUUID)
        {
            // TODO: Edit the BLAnalysis text if there are multiple Bluelines
            return (BLAnalysisNode) GenericInsertions.genericInsert(keywordId, xndParentUUID, 100, null, " ", TargetDataMockingConstants.BL_ANALYSIS_TEXT, Date.valueOf(DEFAULT_START_DATE), null);
        }

        public static BLAnalysisNode insert()
        {
            return insert(defaultParentNodeUUID);
        }
    }

    private static class Blueline
    {
        static int keywordId = 100; // Blueline keyword in DB
        static String defaultParentNodeUUID = "I0DFC5120264911DCB07AD634E02ED2C4"; // BL ANALYSI under = 1.1

        public static List<BluelineNode> insertMultiple(int numSections, String analysisParentUUID)
        {
            ArrayList<BluelineNode> bluelineNodes = new ArrayList<>();

            for (int i = 0; i < numSections; i++)
            {
                int orderNum = 100 * (i + 1);
                bluelineNodes.add(insertWithHeadNum(analysisParentUUID, i + 1, orderNum));
            }

            return bluelineNodes;
        }

        public static BluelineNode insert(String analysisParentUUID, int orderNum)
        {
            return (BluelineNode) GenericInsertions.genericInsert(keywordId, analysisParentUUID, orderNum, "1", "Test", TargetDataMockingConstants.BLUELINE_TEXT, Date.valueOf(DEFAULT_START_DATE), null);
        }

        public static BluelineNode insertWithHeadNum(String analysisParentUUID, int headNum, int orderNum)
        {
            return (BluelineNode) GenericInsertions.genericInsert(keywordId, analysisParentUUID, orderNum, headNum + "", "Test", TargetDataMockingConstants.BLUELINE_TEXT, Date.valueOf(DEFAULT_START_DATE), null);
        }
    }

    /**
     * @deprecated Glossary Nodes ARE "= Section Nodes" and should be inserted as a Section with special content. Use HierarchyDatapodConfiguration.setContentType(HierarchyContentType.GLOSSARY) and insert a section instead.
     */
    private static class Glossary
    {
        static int keywordId = 26;
        static String defaultNodeUUID = "I213AD5BDFC9511E7A9C880000BA47767";

        public static SectionNode insertSingleGlossary(String contentType)
        {
            HierarchyDatapodConfiguration.getConfig().setContentType(HierarchyDatapodContentType.GLOSSARY);
            Title.updateDefaultParentNodeUUID();
            return insert(Title.defaultParentNodeUUID, 100);
        }

        public static List<SectionNode> insertMultiple(int numGlossary, String glossaryParentUUID)
        {
            ArrayList<SectionNode> glossaryNodes = new ArrayList<>();

            for (int i = 0; i < numGlossary; i++)
            {
                int orderNum = 100 * (i + 1);
                glossaryNodes.add(insert(glossaryParentUUID, orderNum));
            }

            return glossaryNodes;
        }

        public static SectionNode insert(String glossaryParentUUID, int orderNum)
        {
            HierarchyDatapodConfiguration.getConfig().setContentType(HierarchyDatapodContentType.GLOSSARY);
            return (SectionNode) GenericInsertions.genericInsert(keywordId, glossaryParentUUID, orderNum, "", "QED Test", TargetDataMockingConstants.NEW_GLOSSARY_TEXT, Date.valueOf(DEFAULT_START_DATE), null);
        }

    }

    /**
     * When future and past nodes are inserted into both chapters and sections the last
     * Chapter inserted will not have the historical sections underneath them.It appears that
     * another chapter gets the sections instead.
     * */
    private static List<HierarchyNode> insertHistoricalVersions(int futureNodes, int pastNodes, HierarchyNode nodeToUse, String parentUUID, int originalNodeOrder)
    {
        List<HierarchyNode> nodesToReturn = new ArrayList<>();
        NodeType nodeType = nodeToUse.getType();
        if(nodeType != NodeType.CHAPTER && nodeType != NodeType.SECTION)
        {
            Assertions.fail(String.format("TargetDataMockingNew.insertHistoricalVersions was called for a node that is not supported (%s).", nodeType.toString()));
        }
        else
        {
            Date endDate = null;
            Date startDate = HierarchyDatabaseUtils.getLegisEffectiveStartDate(nodeToUse.getNodeUUID(), getConnection());

            // Historical versions will have the same "head number"
            String headNum = HierarchyDatabaseUtils.getNodeValueByNodeUUID(nodeToUse.getNodeUUID(), contentSetId + "", getConnection());

            HierarchyNode originalNode = nodeToUse;
            HierarchyNode mostRecentPastNode = originalNode;
            HierarchyNode mostRecentFutureNode = originalNode;
            HierarchyNode dispNode = null;
            HierarchyNode derivNode = null;
            Date futureDate = Date.valueOf(startDate.toLocalDate().plusMonths(1));
            Date pastDate =  HierarchyDatabaseUtils.getLegisEffectiveStartDate(nodeToUse.getNodeUUID(), getConnection());

            for(int j = 0; j < futureNodes; j++)
            {
                derivNode = mostRecentFutureNode;
                startDate = Date.valueOf(futureDate.toLocalDate().plusDays(1));// switched these star and end date for future nodes
                endDate = Date.valueOf(futureDate.toLocalDate().plusMonths(1));
                futureDate = endDate;
                if (nodeType == NodeType.SECTION && j != futureNodes - 1)
                {
                    dispNode = GenericInsertions.genericInsert(SectionWithEqualsSign.keywordId, parentUUID, originalNodeOrder + j + 1, headNum, "QED Small Section", String.format(TargetDataMockingConstants.SMALL_TEXT_UNFORMATTED, headNum, headNum), startDate, endDate);
                }
                else if(nodeType == NodeType.SECTION && j == futureNodes - 1)
                {
                    dispNode = GenericInsertions.genericInsert(SectionWithEqualsSign.keywordId, parentUUID, originalNodeOrder + j + 1, headNum, "QED Small Section", String.format(TargetDataMockingConstants.SMALL_TEXT_UNFORMATTED, headNum, headNum), startDate, null);
                }
                else if(nodeType == NodeType.CHAPTER && j == futureNodes - 1)
                {
                    dispNode = GenericInsertions.genericInsert(Chapter.keywordId, parentUUID, originalNodeOrder + j + 1, headNum, "JURISDICTION OF QED", String.format(TargetDataMockingConstants.CHAPTER_TEXT_UNFORMATTED, "JURISDICTION OF QED", "JURISDICTION OF QED", headNum, "JURISDICTION OF QED"), startDate, null);
                }
                else
                {
                    dispNode = GenericInsertions.genericInsert(Chapter.keywordId, parentUUID, originalNodeOrder + j + 1, headNum, "JURISDICTION OF QED", String.format(TargetDataMockingConstants.CHAPTER_TEXT_UNFORMATTED, "JURISDICTION OF QED", "JURISDICTION OF QED", headNum, "JURISDICTION OF QED"), startDate, endDate);
                }
                mostRecentFutureNode = dispNode;

                nodesToReturn.add(dispNode);
                insertTocNodeDispDeriv(getConnection(), derivNode.getNodeUUID(), dispNode.getNodeUUID(), contentSetId);
            }

            for(int y = 0; y < pastNodes; y++)
            {
                dispNode = mostRecentPastNode;
                endDate = Date.valueOf(pastDate.toLocalDate().minusDays(1));
                startDate = Date.valueOf(pastDate.toLocalDate().minusMonths(1));
                pastDate = startDate;
                if(nodeType == NodeType.SECTION)
                {
                    derivNode = GenericInsertions.genericInsert(SectionWithEqualsSign.keywordId, parentUUID, originalNodeOrder-y-1, headNum, "QED Small Section", String.format(TargetDataMockingConstants.SMALL_TEXT_UNFORMATTED, headNum, headNum), startDate, endDate);
                }
                else
                {
                    derivNode = GenericInsertions.genericInsert(Chapter.keywordId, parentUUID, originalNodeOrder-y-1, headNum, "JURISDICTION OF QED", String.format(TargetDataMockingConstants.CHAPTER_TEXT_UNFORMATTED, "JURISDICTION OF QED", "JURISDICTION OF QED", headNum, "JURISDICTION OF QED"), startDate, endDate);
                }
                mostRecentPastNode = derivNode;

                nodesToReturn.add(derivNode);
                insertTocNodeDispDeriv(getConnection(), derivNode.getNodeUUID(), dispNode.getNodeUUID(), contentSetId);
            }
        }
        return nodesToReturn;
    }

}
