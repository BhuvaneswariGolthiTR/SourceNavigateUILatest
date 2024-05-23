package com.thomsonreuters.codes.codesbench.quality.utilities.database.audits;

import com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.CommonDataMocking;
import org.junit.jupiter.api.Assertions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AuditsDatabaseUtils extends BaseDatabaseUtils
{
    public static String insertReportIntoReportCentral(Connection connection, String createdBy, String name, int jsJurisdictionId, String userId)
    {
        String reportUuid = CommonDataMocking.generateUUID();
        String dataUuid = CommonDataMocking.generateUUID();
        String workflowStatus = "Test";

        try
        {
            logger.information("Inserting into AUDIT_REPORT...");
            PreparedStatement insertReportStatement = connection.prepareStatement(AuditsDatabaseConstants.INSERT_INTO_AUDIT_REPORT);
            insertReportStatement.setString(1, reportUuid);
            insertReportStatement.setString(2, createdBy);
            insertReportStatement.setString(3, name);
            insertReportStatement.setString(4, workflowStatus);
            insertReportStatement.setInt(5, jsJurisdictionId);
            insertReportStatement.setString(6, userId);
            insertReportStatement.executeUpdate();
            insertReportStatement.close();

            logger.information("Inserting into AUDIT_REPORT_DATA...");
            PreparedStatement insertReportStatement2 = connection.prepareStatement(AuditsDatabaseConstants.INSERT_INTO_AUDIT_REPORT_DATA);
            insertReportStatement2.setString(1, dataUuid);
            insertReportStatement2.setString(2, reportUuid);
            insertReportStatement2.setInt(3, 1);
            insertReportStatement2.setInt(4, 0);
            insertReportStatement2.executeUpdate();
            insertReportStatement2.close();

            commit(connection);
        }

        catch (SQLException e)
        {
            Assertions.fail(String.format("Inserting data into AUDIT_REPORT/AUDIT_REPORT_DATA failed: %s", e.toString()));
        }
        return reportUuid;
    }

    public static void deleteReportFromReportCentral(Connection connection, String reportUuid)
    {
        try
        {
            logger.information("Removing from AUDIT_REPORT_DATA...");
            PreparedStatement deleteReportStatement = connection.prepareStatement(AuditsDatabaseConstants.DELETE_FROM_AUDIT_REPORT_DATA);
            deleteReportStatement.setString(1, reportUuid);
            deleteReportStatement.executeUpdate();
            deleteReportStatement.close();

            logger.information("Removing from AUDIT_REPORT...");
            PreparedStatement deleteReportStatement2 = connection.prepareStatement(AuditsDatabaseConstants.DELETE_FROM_AUDIT_REPORT);
            deleteReportStatement2.setString(1, reportUuid);
            deleteReportStatement2.executeUpdate();
            deleteReportStatement2.close();

            commit(connection);
        }
        catch (SQLException e)
        {
            Assertions.fail(String.format("Deleting data from AUDIT_REPORT/AUDIT_REPORT_DATA failed: %s", e.toString()));
        }
    }
}
