package com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.source;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.CommonDataMocking;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.source.datapod.SourceDatapodConfiguration;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.source.datapod.SourceDatapodObject;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.source.datapod.SourceDatapodType;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.source.datapod.SrcContentType;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.source.datapod.sourceobjects.*;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.source.queries.SourceDataMockingQueries;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets;

import java.sql.Connection;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class SourceDataMockingNew extends SourceDataMockingQueries
{

    private static Connection connection;

    private static Connection getConnection()
    {
        if (connection == null)
        {
            connection = CommonDataMocking.connectToDatabase(TestService.environmentTag);
        }
        return connection;
    }

    private static final int PREP_STATUS = 302;
    private static final int APV_STATUS = 303;
    private static final int APVRS_STATUS = 342 ;

    public static class Iowa
    {
        public static class Small
        {

            public static class PREP
            {
                public static SourceDatapodObject insert()
                {
                    return GenericInsertions.genericInsertSmallDatapod(PREP_STATUS, ContentSets.IOWA_DEVELOPMENT.getCode());
                }
            }

            public static class APV
            {
                public static SourceDatapodObject insert()
                {
                    return GenericInsertions.genericInsertSmallDatapod(APV_STATUS, ContentSets.IOWA_DEVELOPMENT.getCode());
                }
            }

            public static class APVRS
            {
                public static SourceDatapodObject insert()
                {
                    return GenericInsertions.genericInsertSmallDatapod(APVRS_STATUS, ContentSets.IOWA_DEVELOPMENT.getCode());
                }
            }
        }
    }

    public static class CrownDependencies
    {
        public static class Small
        {

            public static class PREP
            {
                public static SourceDatapodObject insert()
                {
                    return GenericInsertions.genericInsertSmallDatapod(PREP_STATUS, ContentSets.CROWN_DEPENDENCIES.getCode());
                }
            }

            public static class APV
            {
                public static SourceDatapodObject insert()
                {
                    return GenericInsertions.genericInsertSmallDatapod(APV_STATUS, ContentSets.CROWN_DEPENDENCIES.getCode());
                }
            }

            public static class APVRS
            {
                public static SourceDatapodObject insert()
                {
                    return GenericInsertions.genericInsertSmallDatapod(APVRS_STATUS, ContentSets.CROWN_DEPENDENCIES.getCode());
                }
            }
        }
    }

    public static class USCA
    {
        public static class Small
        {

            public static class PREP
            {
                public static SourceDatapodObject insert()
                {
                    return GenericInsertions.genericInsertSmallDatapod(PREP_STATUS, ContentSets.USCA_DEVELOPMENT.getCode());
                }
            }

            public static class APV
            {
                public static SourceDatapodObject insert()
                {
                    return GenericInsertions.genericInsertSmallDatapod(APV_STATUS, ContentSets.USCA_DEVELOPMENT.getCode());
                }
            }

            public static class APVRS
            {
                public static SourceDatapodObject insert()
                {
                    return GenericInsertions.genericInsertSmallDatapod(APVRS_STATUS, ContentSets.USCA_DEVELOPMENT.getCode());
                }
            }
        }
    }

    private static class GenericInsertions
    {
        private static SourceDatapodObject genericInsertSmallDatapod(int renditionStatus, String contentSetId)
        {
            SourceDataMockingNew sourceMocker = new SourceDataMockingNew();
            Connection connection = getConnection();
            SourceDatapodConfiguration config = SourceDatapodConfiguration.getConfig();

            SourceDatapodObject datapodObject = new SourceDatapodObject();
            if(SourceDatapodConfiguration.getConfig().getType() == SourceDatapodType.LEGAL)
            {
                LineageObject lineageObject = Lineage.insert(contentSetId);
                datapodObject.addSourceObject(lineageObject);

                RenditionObject renditionObject = Rendition.insert(lineageObject, renditionStatus, config.getYear(), SrcContentType.enumToDatabaseId(config.getContentType()));
                datapodObject.addSourceObject(renditionObject);
                String renditionUUID = renditionObject.getRenditionUUID();

                SectionObject sectionObject = Section.insert(renditionObject);
                datapodObject.addSourceObject(sectionObject);

                DeltaObject deltaObject = Delta.insert(renditionObject, sectionObject, renditionStatus);
                datapodObject.addSourceObject(deltaObject);
                String deltaUuid = deltaObject.getDeltaUUID();

                sourceMocker.insertInitialBaseline(connection, renditionUUID, deltaUuid);
                if(renditionStatus == APV_STATUS)
                {
                    // May need to dynamically get username in the future
                    sourceMocker.insertBaseline(connection, renditionUUID, "c268906");
                }
            }

            return datapodObject;
        }
    }

    public static int generateDocNumber()
    {
        int high = Integer.MAX_VALUE;
        int low = 1_000_000;
        SplittableRandom r = new SplittableRandom();
        return r.nextInt(high - low) + low;
    }

    private static class Lineage
    {
        private static LineageObject insert(String contentSetId)
        {
            SourceDataMockingNew sourceMocker = new SourceDataMockingNew();
            long docNumber = generateDocNumber();
            String lineageUUID = sourceMocker.insertSrcLineage(connection, docNumber, contentSetId);
            return new LineageObject(lineageUUID, docNumber);
        }
    }

    private static class Rendition
    {
        private static RenditionObject insert(LineageObject lineageObject, int renditionStatus, int year, int contentTypeId)
        {
            SourceDataMockingNew sourceMocker = new SourceDataMockingNew();
            String renditionUUID = sourceMocker.insertSrcRendition(getConnection(), lineageObject.getLineageUUID(), renditionStatus, lineageObject.getDocNumber(), year, contentTypeId);
            sourceMocker.insertRendProcProps(getConnection(), renditionUUID);
            return new RenditionObject(renditionUUID, lineageObject.getDocNumber());
        }
    }

    private static class Section
    {
        private static SectionObject insert(RenditionObject renditionObject)
        {
            SourceDataMockingNew sourceMocker = new SourceDataMockingNew();
            String renditionUUID = renditionObject.getRenditionUUID();
            String sectionUUID = sourceMocker.insertSection(getConnection(), renditionUUID);
            sourceMocker.insertSectionProcProps(getConnection(), sectionUUID);
            return new SectionObject(sectionUUID);
        }
    }

    private static class Delta
    {
        private static DeltaObject insert(RenditionObject renditionObject, SectionObject sectionObject, int renditionStatus)
        {
            SourceDataMockingNew sourceMocker = new SourceDataMockingNew();
            String renditionUUID = renditionObject.getRenditionUUID();
            String sectionUUID = sectionObject.getSectionUUID();
            String deltaUUID = sourceMocker.insertDelta(getConnection(), sectionUUID, renditionUUID);
            sourceMocker.insertDeltaProcProps(getConnection(), deltaUUID);
            if(renditionStatus == APV_STATUS)
            {
                sourceMocker.insertDeltaTargetLocation(connection, deltaUUID);
            }
            return new DeltaObject(deltaUUID);
        }
    }
}
