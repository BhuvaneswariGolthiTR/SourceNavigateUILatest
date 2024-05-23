package com.thomsonreuters.codes.codesbench.quality.utilities.database.audits;

public class AuditsDatabaseConstants
{
    public static String LEGACY_TEXT_PREPARED_STATEMENT = "SELECT LEGACY_TEXT FROM TOC_NODE WHERE NODE_UUID = ?";
    public static String VOLUME_NUM_STR_PREPARES_STATEMENT = "SELECT VOLUME_NUM_STR FROM TOC_NODE WHERE NODE_UUID = ?";
    public static String LEGIS_START_EFFECTIVE_DATE_PREPARED_STATEMENT = "SELECT LEGIS_START_EFFECTIVE_DATE FROM TOC_NODE WHERE NODE_UUID = ?";

    public static String CODE_NAME_PREPARED_STATEMENT = "SELECT CODE_NAME FROM CODES WHERE CODE_ID = (SELECT CODE_ID FROM TOC_NODE WHERE NODE_UUID = ?)";

    public static final String INSERT_INTO_AUDIT_REPORT = "insert into audit_report(audit_report_uuid, created_by, created_date, name, workflow_status, js_content_set_id, user_id) values (?, ?, current_timestamp, ?, ?, ?, ?)";
    public static final String INSERT_INTO_AUDIT_REPORT_DATA = "insert into audit_report_data(audit_report_data_uuid, audit_report_uuid, type_id, test_data) values (?, ?, ?, ?)";

    public static final String DELETE_FROM_AUDIT_REPORT = "delete from audit_report where audit_report_uuid = ?";
    public static final String DELETE_FROM_AUDIT_REPORT_DATA = "delete from audit_report_data where audit_report_uuid = ?";

}
