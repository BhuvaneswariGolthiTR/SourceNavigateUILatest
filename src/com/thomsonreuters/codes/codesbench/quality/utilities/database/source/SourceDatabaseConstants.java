package com.thomsonreuters.codes.codesbench.quality.utilities.database.source;

public class SourceDatabaseConstants
{
    public static final String GET_DOC_NUMBER_PREPARED_STATEMENT = "SELECT DOC_NUMBER FROM CWB_SRC_RENDITION WHERE CWB_SRC_RENDITION_UUID = ?";

    public static final String GET_LINEAGE_UUID_PREPARED_STATEMENT = "SELECT CWB_SRC_LINEAGE_UUID FROM CWB_SRC_RENDITION WHERE CWB_SRC_RENDITION_UUID = ?";

    public static final String GET_DOCUMENT_UUID_PREPARED_STATEMENT = "SELECT DOCUMENT_UUID FROM CWB_SRC_RENDITION WHERE CWB_SRC_RENDITION_UUID = ?";

    public static final String GET_CORRELATION_UUID_PREPARED_STATEMENT = "SELECT CORRELATION_ID FROM CWB_SRC_LINEAGE WHERE CWB_SRC_LINEAGE_UUID = ?";

    public static final String GET_TARGET_LOCATION_UUID_PREPARED_STATEMENT = "SELECT CWB_TARGET_LOCATION_UUID FROM CWB_TARGET_LOCATION WHERE CWB_DELTA_UUID = ?";

    public static final String ASSIGN_USER_PREPARED_STATEMENT = "UPDATE CWB_SRC_REND_PROC_PROPS SET ASSIGNED_TO = ?, ASSIGNED_TO_DATE = sysdate WHERE CWB_SRC_RENDITION_UUID = ? ";

    public static final String SELECT_LOCK_UUIDS_PREPARED_STATEMENT = "SELECT CWB_SRC_LOCK_UUID FROM CWB_SRC_LOCK_ASSOC WHERE CWB_SRC_RENDITION_UUID = ? ";

    public static final String DELETE_LOCK_ASSOC_PREPARED_STATEMENT = "DELETE FROM CWB_SRC_LOCK_ASSOC WHERE CWB_SRC_LOCK_UUID = ? ";

    public static final String DELETE_LOCK_PREPARED_STATEMENT = "DELETE FROM CWB_SRC_LOCK WHERE CWB_SRC_LOCK_UUID = ? ";

    public static final String CREATE_LOCK_PREPARED_STATEMENT = "INSERT INTO CWB_SRC_LOCK (CWB_SRC_LOCK_UUID, CREATED_BY, CREATED_DATE, REASON, LOCK_TYPE_ID, SUB_CLASS_DISCRIMINATOR)" +
            "\nVALUES (?, ?, sysdate, ?, ?, ?)";

    public static final String CREATE_LOCK_ASSOC_PREPARED_STATEMENT = "INSERT INTO CWB_SRC_LOCK_ASSOC (CWB_SRC_LOCK_ASSOC_UUID, CWB_SRC_LOCK_UUID, ASSOCIATION_DISCRIMINATOR, CWB_SRC_RENDITION_UUID)" +
            "\nVALUES (?, ?, ?, ?)";

    public static final String DELETE_RENDITION_PREPARED_STATEMENT = "UPDATE CWB_SRC_REND_PROC_PROPS SET DELETED_FLAG = 1, DUPLICATE_FLAG = 0, MULTIPLE_FLAG = 0, LAST_DELETED_DATE = sysdate, LAST_DELETED_BY = ? WHERE CWB_SRC_RENDITION_UUID = ?";

    public static final String UNDELETE_RENDITION_PREPARED_STATEMENT = "UPDATE CWB_SRC_REND_PROC_PROPS SET DELETED_FLAG = 0, LAST_UNDELETED_DATE = sysdate, LAST_UNDELETED_BY = ? WHERE CWB_SRC_RENDITION_UUID = ?";

    public static final String DELETE_DELTA_GROUP_ASSOC_PREPARED_STATEMENT = "DELETE FROM CWB_DELTA_GROUP_ASSOCIATION WHERE CWB_DELTA_GROUP_UUID = (SELECT CWB_DELTA_GROUP_UUID FROM CWB_DELTA_GROUP WHERE CWB_SRC_RENDITION_UUID = ? AND CWB_DELTA_GROUP_NAME = ?)";

    public static final String DELETE_DELTA_GROUP_PREPARED_STATEMENT = "DELETE FROM CWB_DELTA_GROUP WHERE CWB_SRC_RENDITION_UUID = ? AND CWB_DELTA_GROUP_NAME = ?";

    public static final String DELETE_SECTION_GROUP_ASSOC_PREPARED_STATEMENT = "DELETE FROM CWB_SECTION_GROUP_ASSOCIATION WHERE CWB_SECTION_GROUP_UUID = (SELECT CWB_SECTION_GROUP_UUID FROM CWB_SECTION_GROUP WHERE CWB_SRC_RENDITION_UUID = ? AND CWB_SECTION_GROUP_NAME = ?)";

    public static final String DELETE_SECTION_GROUP_PREPARED_STATEMENT = "DELETE FROM CWB_SECTION_GROUP WHERE CWB_SRC_RENDITION_UUID = ? AND CWB_SECTION_GROUP_NAME = ?";

    public static final String DELETE_VIEW_ASSOC_PREPARED_STATEMENT = "DELETE FROM CWB_SRC_VIEW_ASSOCIATION WHERE CWB_SRC_VIEW_UUID IN (SELECT CWB_SRC_VIEW_UUID FROM CWB_SRC_VIEW WHERE VIEW_NAME = ?)";

    public static final String DELETE_VIEW_PREPARED_STATEMENT = "DELETE FROM CWB_SRC_VIEW WHERE VIEW_NAME = ?";

    public static final String SELECT_CURRENT_BASELINE_NUMBER_PREPARED_STATEMENT = "SELECT MAX(SIBLING_ORDER) FROM CWB_SRC_RENDITION_BASELINE WHERE CWB_SRC_RENDITION_UUID = ?";

    //Allows you to get the max sibling order from any table
    public static final String SELECT_MAX_SIBLING_ORDER_PREPARED_STATEMENT = "SELECT MAX(SIBLING_ORDER) FROM $tableName WHERE $uuid = ?";

    public static final String SELECT_MAX_DELTA_COUNT_PREPARED_STATEMENT = "SELECT MAX(DELTA_COUNT) FROM $tableName WHERE $uuid = ?";

    public static final String RESTORE_BASELINE_TO_PREVIOUS_BASELINE = "INSERT INTO CWB_SRC_RENDITION_BASELINE (CWB_SRC_RENDITIONBASELINE_UUID, CWB_SRC_RENDITION_UUID, CREATION_DATE, CREATED_BY, DESCRIPTION, TEXT, SIBLING_ORDER, CREATION_PROCESS, MODIFIED_DATE)" +
            "\nVALUES (?, ?, sysdate, ?, ?, null, ?, 'BaseLine Restore', current_timestamp)";

    public static final String RESTORE_BASELINE_TO_ORIGINAL_BASELINE = "INSERT INTO CWB_SRC_RENDITION_BASELINE (CWB_SRC_RENDITIONBASELINE_UUID, CWB_SRC_RENDITION_UUID, CREATION_DATE, CREATED_BY, DESCRIPTION, TEXT, SIBLING_ORDER, CREATION_PROCESS, MODIFIED_DATE)" +
            "\nVALUES (?, ?, sysdate, ?, 'Restored from 0', null, ?, 'BaseLine Restore', current_timestamp)";

    public static final String UPDATE_PREVIOUS_BASELINE_TEXT = "UPDATE CWB_SRC_RENDITION_BASELINE SET TEXT = (SELECT TEXT FROM CWB_SRC_RENDITION_BASELINE WHERE SIBLING_ORDER = ? AND CWB_SRC_RENDITION_UUID = ?) WHERE SIBLING_ORDER = ? AND CWB_SRC_RENDITION_UUID = ?";

    public static final String GET_YEAR = "select year from cwb_src_rendition where cwb_src_rendition_uuid = ?";

    public static final String GET_SESSION = "select js_session_short_name from js_session_enum, cwb_src_rendition where js_session_enum.js_session_id = cwb_src_rendition.js_session_id and cwb_src_rendition_uuid = ?";

    public static final String GET_BILL_ID = "select bill_id from cwb_src_rendition where cwb_src_rendition_uuid = ?";
}