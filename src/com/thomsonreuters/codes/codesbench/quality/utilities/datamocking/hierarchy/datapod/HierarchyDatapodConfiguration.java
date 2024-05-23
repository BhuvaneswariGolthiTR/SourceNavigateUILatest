package com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.datapod;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.hierarchy.HierarchyDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.datapod.nodes.NodeType;
import org.junit.jupiter.api.Assertions;

import java.sql.Connection;
import java.util.ArrayList;

public class HierarchyDatapodConfiguration
{
    private int bluelineCount;

    private int ruleCount;

    private int sectionCount;

    private int chapterCount;

    private int subtitleCount;

    private int gradeCount;

    private int glossaryCount;

    private String topNodeTypeString;

    private String sectionType;

    private String chapterType;

    private int sectionWipCount;

    private int partCount;

    private HierarchyDatapodContentType contentType;

    private static HierarchyDatapodConfiguration config;

    private boolean insertInPubNav;

    private int numberOfPastSectionVersions;

    private int numberOfFutureSectionVersions;

    private int numberOfPastChapterVersions;

    private int numberOfFutureChapterVersions;

    /**
     * Returns the configuration, or creates it if it doesn't exist yet.
     *
     * @return The configuration
     */
    public static HierarchyDatapodConfiguration getConfig()
    {
        if (config == null)
        {
            config = new HierarchyDatapodConfiguration();
        }
        return config;
    }

    /**
     * Loads a default Risk configuration, instead of having to manually edit the entire thing.
     */
    public void loadDefaultRiskConfig()
    {
        this.topNodeTypeString = "GRADE";
        this.partCount = 1;
        this.chapterCount = 1;
        this.sectionCount = 1;
        this.bluelineCount = 0;
        this.subtitleCount = 0;
        this.gradeCount = 0;
        this.sectionType = "EQUALS";
        this.sectionWipCount = 1;
        this.contentType = HierarchyDatapodContentType.RISK;
        this.ruleCount = 0;
        this.glossaryCount = 0;
        this.insertInPubNav = false;
        this.numberOfFutureSectionVersions = 0;
        this.numberOfPastSectionVersions = 0;
        this.numberOfFutureChapterVersions = 0;
        this.numberOfPastChapterVersions = 0;
    }

    /**
     * Loads a default FedRegs configuration, instead of having to manually edit the entire thing.
     */
    public void loadDefaultFedRegsConfig()
    {
        this.topNodeTypeString = "TITLE";
        this.partCount = 1;
        this.chapterCount = 1;
        this.sectionCount = 1;
        this.bluelineCount = 0;
        this.subtitleCount = 0;
        this.gradeCount = 0;
        this.sectionType = "SECTION";
        this.sectionWipCount = 1;
        this.contentType = HierarchyDatapodContentType.FED_REGS;
        this.ruleCount = 0;
        this.insertInPubNav = false;
        this.numberOfFutureSectionVersions = 0;
        this.numberOfPastSectionVersions = 0;
        this.numberOfFutureChapterVersions = 0;
        this.numberOfPastChapterVersions = 0;
    }

    /**
     * Loads a default German configuration, instead of having to manually edit the entire thing.
     */
    public void loadDefaultGermanConfig()
    {
        this.topNodeTypeString = "GRADE";
        this.partCount = 0;
        this.chapterCount = 0;
        this.sectionCount = 1;
        this.bluelineCount = 0;
        this.subtitleCount = 0;
        this.gradeCount = 0;
        this.sectionType = "EQUALS";
        this.sectionWipCount = 1;
        this.contentType = HierarchyDatapodContentType.GERMAN;
        this.ruleCount = 0;
        this.numberOfFutureSectionVersions = 0;
        this.numberOfPastSectionVersions = 0;
        this.numberOfFutureChapterVersions = 0;
        this.numberOfPastChapterVersions = 0;
    }

    /**
     * Resets the current configuration back to the defaults
     */
    public static void resetConfig()
    {
        if (config == null)
        {
            TestService.logger.warning("You can't reset a HierarchyDatapodConfiguration until you have gotten a HierarchyDatapodConfiguration with HierarchyDatapodConfiguration.getConfig()");
            return;
        }
        config.bluelineCount = 0;
        config.sectionCount = 1;
        config.chapterCount = 1;
        config.subtitleCount = 1;
        config.gradeCount = 0;
        config.topNodeTypeString = "TITLE";
        config.sectionType = "EQUALS";
        config.sectionWipCount = 1;
        config.partCount = 0;
        config.contentType = HierarchyDatapodContentType.LEGAL;
        config.ruleCount = 0;
        config.glossaryCount = 0;
        config.insertInPubNav = false;
        config.numberOfFutureSectionVersions = 0;
        config.numberOfPastSectionVersions = 0;
        config.numberOfFutureChapterVersions = 0;
        config.numberOfPastChapterVersions = 0;
    }

    /**
     * Resets the current configuration back to the defaults
     * This allows for both HierarchyDatapodConfiguration.resetConfig() and HierarchyDatapodConfiguration.getConfig().reset()
     */
    public void reset()
    {
        resetConfig();
    }

    /**
     * Private constructor; sets the default values
     */
    private HierarchyDatapodConfiguration()
    {
        this.bluelineCount = 0;
        this.sectionCount = 1;
        this.chapterCount = 1;
        this.subtitleCount = 1;
        this.gradeCount = 0;
        this.topNodeTypeString = "TITLE";
        this.sectionType = "EQUALS";
        this.sectionWipCount = 1;
        this.partCount = 0;
        this.contentType = HierarchyDatapodContentType.LEGAL;
        this.ruleCount = 0;
        this.glossaryCount = 0;
        this.insertInPubNav = false;
        this.numberOfFutureSectionVersions = 0;
        this.numberOfPastSectionVersions = 0;
        this.numberOfFutureChapterVersions = 0;
        this.numberOfPastChapterVersions = 0;
    }

    /**
     * Get the amount of Bluelines that will be inserted per Section
     *
     * @return The amount of Bluelines that will be inserted per Section
     */
    public int getBluelineCount()
    {
        return bluelineCount;
    }

    /**
     * Sets the amount of Bluelines that will be inserted per Section
     *
     * @param bluelineCount The amount of Bluelines that will be inserted per Section
     */
    public void setBluelineCount(int bluelineCount)
    {
        this.bluelineCount = bluelineCount;
    }

    /**
     * Sets the amount of glossarys that will be inserted per Section
     *
     * @param glossaryCount The amount of glossarys that will be inserted per Section
     * @deprecated Glossary Nodes are "=" Section Nodes
     */
    public void setGlossaryCount(int glossaryCount)
    {
        this.glossaryCount = glossaryCount;
    }

    /**
     * Get the amount of Sections that will be inserted per Chapter
     *
     * @return The amount of Sections that will be inserted per Chapter
     */
    public int getSectionCount()
    {
        return sectionCount;
    }

    /**
     * Sets the amount of Sections that will be inserted per Chapter
     *
     * @param sectionCount The amount of Sections that will be inserted per Chapter
     */
    public void setSectionCount(int sectionCount)
    {
        this.sectionCount = sectionCount;
    }

    /**
     * Get the amount of Rules that will be inserted per Chapter
     *
     * @return The amount of Rules that will be inserted per Chapter
     */
    public int getRuleCount()
    {
        return ruleCount;
    }

    /**
     * Sets the amount of Rules that will be inserted per Chapter
     *
     * @param ruleCount The amount of Rules that will be inserted per Chapter
     */
    public void setRuleCount(int ruleCount)
    {
        this.ruleCount = ruleCount;
    }

    /**
     * Get the amount of Sections that will be inserted per Chapter
     *
     * @return The amount of Sections that will be inserted per Chapter
     */
    public int getChapterCount()
    {
        return chapterCount;
    }

    /**
     * Sets the amount of Chapters that will be inserted per Subtitle
     *
     * @param chapterCount The amount of Chapters that will be inserted per Subtitle
     */
    public void setChapterCount(int chapterCount)
    {
        this.chapterCount = chapterCount;
    }

    /**
     * Get the amount of Subtitles that will be inserted below the top node
     *
     * @return The amount of Subtitles that will be inserted below the top node
     */
    public int getSubtitleCount()
    {
        return subtitleCount;
    }

    /**
     * Sets the amount of Subtitles that will be inserted below the top node
     *
     * @param subtitleCount The amount of Subtitles that will be inserted below the top node
     */
    public void setSubtitleCount(int subtitleCount)
    {
        this.subtitleCount = subtitleCount;
    }

    /**
     * Get the amount of Grades defined by the configuration. These will be inserted below a Chapter -- this does not affect top node, even if it is a Grade!
     *
     * @return The amount of Grades defined by the configuration.
     */
    public int getGradeCount()
    {
        return gradeCount;
    }

    /**
     * Get the amount of Glossary's defined by the configuration. This will be inserted under a chapter
     *
     * @return The amount of Glossary's defined by the configuration.
     * @deprecated Glossary Nodes are "=" Section Nodes
     */
    public int getGlossaryCount()
    {
        return glossaryCount;
    }

    /**
     * Sets the amount of Grades defined by the configuration. These will be inserted below a Chapter -- this does not affect top node, even if it is a Grade!
     *
     * @param gradeCount The amount of Grades that defined by the configuration.
     */
    public void setGradeCount(int gradeCount)
    {
        this.gradeCount = gradeCount;
    }

    /**
     * Get the NodeType of the top node
     *
     * @return The NodeType of the top node
     */
    public NodeType getTopNodeType()
    {
        return NodeType.valueOf(topNodeTypeString);
    }

    /**
     * Sets the Node Type of the top node as a String (instead of a NodeType object). Fails if not TITLE or GRADE (case sensitive)
     *
     * @param topNodeTypeString The String representing a NodeType
     */
    public void setTopNodeTypeWithString(String topNodeTypeString)
    {
        if (!topNodeTypeString.equals("TITLE") && !topNodeTypeString.equals("GRADE") && !topNodeTypeString.equals("SECTION") && !topNodeTypeString.equals("GLOSSARY"))
        {
            Assertions.fail("Top node must be TITLE or GRADE or SECTION");
        }
        this.topNodeTypeString = topNodeTypeString;
    }

    /**
     * Get a String representing which type of Section will be inserted. EQUALS or SECTION, corresponding to the = sign, and the § sign.
     *
     * @return A String representing which type of Section will be inserted
     */
    public String getSectionType()
    {
        return sectionType;
    }

    /**
     * Sets the type of Section to be used. Fails if not EQUALS or SECTION (case sensitive) which represent the = sign and § sign.
     *
     * @param sectionType The type of Section to be used
     */
    public void setSectionType(String sectionType)
    {
        if (!sectionType.equals("EQUALS") && !sectionType.equals("SECTION"))
        {
            Assertions.fail("Section type must be EQUALS or SECTION");
        }
        this.sectionType = sectionType;
    }

    /**
     * Get a String representing which type of Chapter will be inserted. EQUALS or Chapter, corresponding to the = sign, and the § sign.
     *
     * @return A String representing which type of Chapter will be inserted
     */
    public String getChapterType()
    {
        return chapterType;
    }

    /**
     * Sets the type of Chapter to be used. Fails if not EQUALS or Chapter (case sensitive) which represent the = sign and § sign.
     *
     * @param chapterType The type of Section to be used
     */
    public void setChapterType(String chapterType)
    {
        if (!sectionType.equals("EQUALS") && !sectionType.equals("CHAPTER"))
        {
            Assertions.fail("Section type must be EQUALS or SECTION");
        }
        this.sectionType = chapterType;
    }

    /**
     * Gets the amount of WIP Versions sections should have in the datapod.
     *
     * @return The amount of WIP Versions sections should have in the datapod.
     */
    public int getSectionWipCount()
    {
        return sectionWipCount;
    }

    /**
     * Sets the amount of WIP Versions sections should have in the datapod.
     *
     * @param sectionWipCount The amount of WIP Versions sections should have in the datapod.
     */
    public void setSectionWipCount(int sectionWipCount)
    {
        this.sectionWipCount = sectionWipCount;
    }

    /**
     * Gets the amount of Parts defined by the configuration.
     *
     * @return The amount of Parts defined by the configuration.
     */
    public int getPartCount()
    {
        return partCount;
    }

    /**
     * Sets the amount of Parts defined by the configuration
     *
     * @param partCount The amount of Parts to be defined by the configuration
     */
    public void setPartCount(int partCount)
    {
        this.partCount = partCount;
    }

    /**
     * Get a String representing which type of Section content will be inserted.
     *
     * @return A String representing which type of Section content will be inserted
     */
    public String getContentTypeAsString()
    {
        return contentType.name();
    }

    /**
     * Get the HierarchyDatapodContentType enum represeting which type of Section content will be inserted.
     *
     * @return A HierarchyDatapodContentType enum representing which type of Section content will be inserted
     */
    public HierarchyDatapodContentType getContentType()
    {
        return contentType;
    }

    /**
     * Sets the type of content to be used in sections. Fails if not one of the allowed options (see HierarchyDatapodContentType)
     *
     * @param contentTypeString The type of Section to be used
     * @deprecated Please use setContentType(HierarchyDatapodContentType) instead of setContentType(String)
     */
    @Deprecated
    public void setContentType(String contentTypeString)
    {
        try
        {
            HierarchyDatapodContentType contentType = HierarchyDatapodContentType.valueOf(contentTypeString);
            setContentType(contentType);
        }
        catch (IllegalArgumentException e)
        {
            Assertions.fail("You can only set the content type to those which exist in HierarchyDatapodContentType! (Not '" + contentTypeString + "'");
        }
    }

    /**
     * Sets the type of content to be used in sections.
     *
     * @param contentType The type of Section to be used
     */
    public void setContentType(HierarchyDatapodContentType contentType)
    {
        this.contentType = contentType;
    }

    /**
     * Sets whether or not the datapod should insert into Publishing Navigate
     *
     * @param insertInPubNav whether or not the datapod should insert into Publishing Navigate
     */
    public void setInsertInPubNav(boolean insertInPubNav)
    {
        this.insertInPubNav = insertInPubNav;
    }

    /**
     * Get whether or not the datapod should insert into Publishing Navigate
     *
     * @return whether or not the datapod should insert into Publishing Navigate
     */
    public boolean getInsertInPubNav()
    {
        return insertInPubNav;
    }

    /**
     * Set the number of future historical versions sections
     *
     */
    public void setNumberOfFutureSectionVersions(int numberOfFutureSectionVersions)
    {
        this.numberOfFutureSectionVersions = numberOfFutureSectionVersions;
    }

    /**
     * Get the number of future historical versions sections
     *
     */
    public int getNumberOfFutureSectionVersions()
    {
        return this.numberOfFutureSectionVersions;
    }

    /**
     * Set the number of past historical versions sections
     *
     */
    public void setNumberOfPastSectionVersions(int numberOfPastSectionVersions)
    {
        this.numberOfPastSectionVersions = numberOfPastSectionVersions;
    }

    /**
     * Get the number of past historical versions sections
     *
     */
    public int getNumberOfPastSectionVersions()
    {

        return this.numberOfPastSectionVersions;
    }

    /**
     * Set the number of future historical versions chapters
     *
     */
    public void setNumberOfFutureChapterVersions(int numberOfFutureChapterVersions)
    {
        this.numberOfFutureChapterVersions = numberOfFutureChapterVersions;
    }

    /**
     * Get the number of future historical versions chapters
     *
     */
    public int getNumberOfFutureChapterVersions()
    {
        return this.numberOfFutureChapterVersions;
    }

    /**
     * Set the number of past historical versions chapters
     *
     */
    public void setNumberOfPastChapterVersions(int numberOfPastChapterVersions)
    {
        this.numberOfPastChapterVersions = numberOfPastChapterVersions;
    }

    /**
     * Get the number of past historical versions chapters
     *
     */
    public int getNumberOfPastChapterVersions()
    {
        return this.numberOfPastChapterVersions;
    }

    public void setRange (ArrayList<String> sectionsNodeUUIDS, Connection connection)
    {
        int headHID = 0;
        String headNodeOfRangeUUID = "";
        for(int i =0; i<sectionsNodeUUIDS.size(); i++)
        {
            if(i ==0)
            {
               headHID = HierarchyDatabaseUtils.getHIDWithNodeUUID(sectionsNodeUUIDS.get(i),connection);
               headNodeOfRangeUUID = sectionsNodeUUIDS.get(i);
               HierarchyDatabaseUtils.updateInitRangeNodeUUID(connection,sectionsNodeUUIDS.get(i),headNodeOfRangeUUID);
               HierarchyDatabaseUtils.updateLegacyInitRangeHID(connection,sectionsNodeUUIDS.get(i),headHID);
            }
            else
            {
                HierarchyDatabaseUtils.updateInitRangeNodeUUID(connection, sectionsNodeUUIDS.get(i),headNodeOfRangeUUID);
                HierarchyDatabaseUtils.updateLegacyInitRangeHID(connection, sectionsNodeUUIDS.get(i),headHID);
            }
        }
    }
}
