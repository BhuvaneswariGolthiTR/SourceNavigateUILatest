package com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.source;

import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.CommonDataMocking;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.source.queries.SourceDataMockingConstants;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.source.queries.SourceDataMockingQueries;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import org.junit.jupiter.api.Assertions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils.commit;

/**
 * @deprecated Deprecated in favor of SourceDataMockingNew
 */
public class SourceDocument extends SourceDataMockingQueries
{
    public String lineageUuid;

    public String renditionUuid;
    public String sectionUuid;
    public String deltaUuid;

    public String year;
    public int renditionStatus;
    public int contentType;
    public long docNumber;
    public int deleted;

    Connection connection;


    public SourceDocument(String environment)
    {
        year = DateAndTimeUtils.getCurrentYearyyyy();
        deleted = 0;
        docNumber = Long.parseLong(DateAndTimeUtils.getCurrentDateAndTimeyyyyMMddHHmmss().substring(4));
        contentType = 22;
        connection = CommonDataMocking.connectToDatabase(environment);
    }

    public String getRenditionUuid()
    {
        return renditionUuid;
    }

    public void giveRenditionError()
    {
        String flagUuid = CommonDataMocking.generateUUID();

        try
        {
            PreparedStatement insertTargetLocationStatement = connection.prepareStatement(SourceDataMockingConstants.INSERT_INTO_REND_ERROR_FLAG);
            insertTargetLocationStatement.setString(1, flagUuid);
            insertTargetLocationStatement.setString(2, renditionUuid);
            insertTargetLocationStatement.executeUpdate();

            PreparedStatement updateRendProcProcsStatement = connection.prepareStatement(SourceDataMockingConstants.UPDATE_REND_PROC_PROPS_FLAG_ERROR);
            updateRendProcProcsStatement.setString(1, renditionUuid);
            updateRendProcProcsStatement.executeUpdate();

            PreparedStatement updateTargetLocationStatement = connection.prepareStatement(SourceDataMockingConstants.UPDATE_TARGET_LOCATION_FOR_ERROR);
            updateTargetLocationStatement.setString(1, deltaUuid);
            updateTargetLocationStatement.executeUpdate();
            commit(connection);

        }
        catch (SQLException e)
        {
            Assertions.fail(String.format("Giving the rendition an error flag failed: %s", e.toString()));
        }
    }
}
