package com.thomsonreuters.codes.codesbench.quality.utilities.database.tools.ScriptMaintenance;

import java.util.ArrayList;
import java.util.List;

public class ScriptMaintenanceDatabaseConstants
{
    //Hardcoded Strings
    public static final String SCRIPT_NAME = "A00001";
    public static final String VERSION_DESCRIPTION = "A00002";
    public static final String PUB_TAG = "A00003";
    public static final int SCRIPT_ID = 10000001;
    public static final int SCRIPT_NUM = 0000;
    //Delete
    public static final String DELETE_FROM_SCRIPT_PUBTAG_STATEMENT_STRING = "DELETE FROM SCRIPT_PUBTAG WHERE SCRIPT_LOCATION_ID = 1 AND SCRIPT_NUM = ? AND PUBTAG = ?";
    //Deep Delete Queries
    public static final String DEEP_DELETE_PUB_TOC_NODE_SCRIPT_ASSIGNMENT = "DELETE FROM PUB_TOC_NODE_SCRIPT_ASSIGNMENT WHERE SCRIPT_ID IN (SELECT SCRIPT_ID FROM SCRIPT WHERE SCRIPT_NUM = ? AND LONG_NAME = ? AND CREATED_BY = ? AND SCRIPT_LOCATION_ID = 1)";
    public static final String DEEP_DELETE_TOC_NODE_SCRIPT_ASSIGNMENT = "DELETE FROM TOC_NODE_SCRIPT_ASSIGNMENT WHERE SCRIPT_ID IN (SELECT SCRIPT_ID FROM SCRIPT WHERE SCRIPT_NUM = ? AND LONG_NAME = ? AND CREATED_BY = ? AND SCRIPT_LOCATION_ID = 1)";
    public static final String DEEP_DELETE_QUERY_NOTE = "DELETE FROM QUERY_NOTE WHERE SCRIPT_ID IN (SELECT SCRIPT_ID FROM SCRIPT WHERE SCRIPT_NUM = ? AND LONG_NAME = ? AND CREATED_BY = ? AND SCRIPT_LOCATION_ID = 1)";
    public static final String DEEP_DELETE_SCRIPT_ATTRIBUTES = "DELETE FROM SCRIPT_ATTRIBUTES WHERE SCRIPT_ID IN (SELECT SCRIPT_ID FROM SCRIPT WHERE SCRIPT_NUM = ? AND LONG_NAME = ? AND CREATED_BY = ? AND SCRIPT_LOCATION_ID = 1)";
    public static final String DEEP_DELETE_SCRIPT_COMMAND_END = "delete from script_command_end where script_command_uuid in (select script_command_uuid from script_command where script_id in (select script_id from script where script_num = ? and long_name = ? and created_by = ? and script_location_id = 1))";
    public static final String DEEP_DELETE_SCRIPT_COMMAND = "DELETE FROM SCRIPT_COMMAND WHERE SCRIPT_ID IN (SELECT SCRIPT_ID FROM SCRIPT WHERE SCRIPT_NUM = ? AND LONG_NAME = ? AND CREATED_BY = ? AND SCRIPT_LOCATION_ID = 1)";
    public static final String DEEP_DELETE_SCRIPT_DEPENDENCY_DEPENDANT = "DELETE FROM SCRIPT_DEPENDENCY WHERE DEPENDANT_ON_SCRIPT_ID IN (SELECT SCRIPT_ID FROM SCRIPT WHERE SCRIPT_NUM = ? AND LONG_NAME = ? AND CREATED_BY = ? AND SCRIPT_LOCATION_ID = 1)";
    public static final String DEEP_DELETE_SCRIPT_DEPENDENCY = "DELETE FROM SCRIPT_DEPENDENCY WHERE SCRIPT_ID IN (SELECT SCRIPT_ID FROM SCRIPT WHERE SCRIPT_NUM = ? AND LONG_NAME = ? AND CREATED_BY = ? AND SCRIPT_LOCATION_ID = 1)";
    public static final String DEEP_DELETE_SCRIPT_VOLUME_STATUS = "DELETE FROM SCRIPT_VOLUME_STATUS WHERE SCRIPT_ID IN (SELECT SCRIPT_ID FROM SCRIPT WHERE SCRIPT_NUM = ? AND LONG_NAME = ? AND CREATED_BY = ? AND SCRIPT_LOCATION_ID = 1)";
    public static final String DEEP_DELETE_SCRIPT_VALID_CONTENT_SET = "DELETE FROM SCRIPT_VALID_CONTENT_SET WHERE SCRIPT_ID IN (SELECT SCRIPT_ID FROM SCRIPT WHERE SCRIPT_NUM = ? AND LONG_NAME = ? AND CREATED_BY = ? AND SCRIPT_LOCATION_ID = 1)";
    public static final String DEEP_DELETE_SCRIPT = "DELETE FROM SCRIPT WHERE SCRIPT_NUM = ? AND LONG_NAME = ? AND CREATED_BY = ? AND SCRIPT_LOCATION_ID = 1";

    //Inserts
    public static final String INSERT_SCRIPTID_WITH_CONTENT_SET_INTO_JS_VALID_CONTENT_SETS = "insert into SCRIPT_VALID_CONTENT_SET (SCRIPT_ID, JS_CONTENT_SET_ID) values (?, ?)";
    public static final String INSERT_SCRIPT_FOR_001 = "insert into SCRIPT (SCRIPT_NUM,LONG_NAME,CREATED_BY,SCRIPT_LOCATION_ID,SCRIPT_ID,SCRIPT_VERSION,SCRIPT_STATUS_ID,PRODUCT_TYPE_ID,CREATED_DATE, IS_DELETED, SCRIPT_UUID, LONG_VER_DESC, update_num) values ( ?, ?, ?,1,?, 001,9,1,to_char(sysdate), 0, ?, ?, 0)";
    public static final String INSERT_SCRIPT_FOR_999 = "insert into SCRIPT (SCRIPT_NUM,LONG_NAME,CREATED_BY,SCRIPT_LOCATION_ID,SCRIPT_ID,SCRIPT_VERSION,SCRIPT_STATUS_ID,PRODUCT_TYPE_ID,CREATED_DATE, IS_DELETED, SCRIPT_UUID, LONG_VER_DESC, update_num) values ( ?, ?, ?,1,?, 999,9,1,to_char(sysdate), 0, ?, 'MANUALLY ADDED PUBTAG VERSION', 0)";
    public static final String INSERT_SCRIPT_NUM_INTO_SCRIPT_PUBTAG = "insert into script_pubtag (SCRIPT_LOCATION_ID, SCRIPT_NUM, PUBTAG) values (1, ?, ?)";
    public static final String INSERT_SCRIPT_COMMAND_SELECT_RANGE = "insert into SCRIPT_COMMAND (script_id, command_sequence, script_action_id, start_mnemonic, end_include_flag, created_by, created_date, script_command_uuid) values (?,?,?,?,?,?,sysdate,?)";
    public static final String INSERT_SCRIPT_COMMAND_END_SELECT_RANGE = "insert into SCRIPT_COMMAND_END (end_mnemonic, script_command_end_uuid, script_command_uuid) values (?,?,?)";

    public static final String INSERT_SCRIPT_INTO_TOC_NODE_ASSIGNMENT = "insert into toc_node_script_assignment (node_uuid, script_id, script_range_indicator, cs_id) values(?,?,?,?)";
    //Select
    public static final String SELECT_SCRIPT_NUM_STATEMENT_STRING = "SELECT SCRIPT_NUM FROM SCRIPT WHERE LONG_NAME = ? AND CREATED_BY = ?";
    public static final String SELECT_999_COUNT = "select COUNT(*) from script where long_name = ? and created_by = ? and script_version = 999";
    public static final String SELECT_SCRIPTS_FROM_UI_LIST = "select pubtag.pubtag, script.long_name, script.long_ver_desc, script.script_id, status.status from script_status status, script script, script_pubtag pubtag, script_valid_content_set svcs where svcs.script_id = script.script_id and pubtag.script_num = script.script_num and status.script_status_id = script.script_status_id and svcs.js_content_set_id = ? and (script.script_version <> 999) and script.is_deleted = 0 and (script.script_status_id = 9 or script.script_status_id = 1 or script.script_status_id = 2) and pubtag.script_location_id = 1 order by case when pubtag.pubtag = 'AN' then pubtag.pubtag end asc, case when pubtag.pubtag = 'WL' then pubtag.pubtag end asc, pubtag.pubtag asc, script_id asc";
    public static final String SELECT_CONTENT_SETS_GIVEN_SCRIPT_ID = "select * from SCRIPT_VALID_CONTENT_SET where script_id = ?";

    public static final String DOES_SCRIPT_EXIST = "select * from script where script_id = ?";

    public static List<String> deepDeleteStatementStrings = new ArrayList<>();

    static
    {
        deepDeleteStatementStrings.add("DELETE FROM PUB_TOC_NODE_SCRIPT_ASSIGNMENT WHERE SCRIPT_ID IN (SELECT SCRIPT_ID FROM SCRIPT WHERE SCRIPT_NUM = ? AND LONG_NAME = ? AND CREATED_BY = ? AND SCRIPT_LOCATION_ID = 1)");
        deepDeleteStatementStrings.add("DELETE FROM TOC_NODE_SCRIPT_ASSIGNMENT WHERE SCRIPT_ID IN (SELECT SCRIPT_ID FROM SCRIPT WHERE SCRIPT_NUM = ? AND LONG_NAME = ? AND CREATED_BY = ? AND SCRIPT_LOCATION_ID = 1)");
        deepDeleteStatementStrings.add("DELETE FROM QUERY_NOTE WHERE SCRIPT_ID IN (SELECT SCRIPT_ID FROM SCRIPT WHERE SCRIPT_NUM = ? AND LONG_NAME = ? AND CREATED_BY = ? AND SCRIPT_LOCATION_ID = 1)");
        deepDeleteStatementStrings.add("DELETE FROM SCRIPT_ATTRIBUTES WHERE SCRIPT_ID IN (SELECT SCRIPT_ID FROM SCRIPT WHERE SCRIPT_NUM = ? AND LONG_NAME = ? AND CREATED_BY = ? AND SCRIPT_LOCATION_ID = 1)");

        deepDeleteStatementStrings.add("delete from script_command_end where script_command_uuid in (select script_command_uuid from script_command where script_id in (select script_id from script where script_num = ? and long_name = ? and created_by = ? and script_location_id = 1))");

        deepDeleteStatementStrings.add("DELETE FROM SCRIPT_COMMAND WHERE SCRIPT_ID IN (SELECT SCRIPT_ID FROM SCRIPT WHERE SCRIPT_NUM = ? AND LONG_NAME = ? AND CREATED_BY = ? AND SCRIPT_LOCATION_ID = 1)");
        deepDeleteStatementStrings.add("DELETE FROM SCRIPT_DEPENDENCY WHERE DEPENDANT_ON_SCRIPT_ID IN (SELECT SCRIPT_ID FROM SCRIPT WHERE SCRIPT_NUM = ? AND LONG_NAME = ? AND CREATED_BY = ? AND SCRIPT_LOCATION_ID = 1)");
        deepDeleteStatementStrings.add("DELETE FROM SCRIPT_DEPENDENCY WHERE SCRIPT_ID IN (SELECT SCRIPT_ID FROM SCRIPT WHERE SCRIPT_NUM = ? AND LONG_NAME = ? AND CREATED_BY = ? AND SCRIPT_LOCATION_ID = 1)");
        deepDeleteStatementStrings.add("DELETE FROM SCRIPT_VOLUME_STATUS WHERE SCRIPT_ID IN (SELECT SCRIPT_ID FROM SCRIPT WHERE SCRIPT_NUM = ? AND LONG_NAME = ? AND CREATED_BY = ? AND SCRIPT_LOCATION_ID = 1)");
        deepDeleteStatementStrings.add("DELETE FROM SCRIPT_VALID_CONTENT_SET WHERE SCRIPT_ID IN (SELECT SCRIPT_ID FROM SCRIPT WHERE SCRIPT_NUM = ? AND LONG_NAME = ? AND CREATED_BY = ? AND SCRIPT_LOCATION_ID = 1)");
        deepDeleteStatementStrings.add("DELETE FROM SCRIPT WHERE SCRIPT_NUM = ? AND LONG_NAME = ? AND CREATED_BY = ? AND SCRIPT_LOCATION_ID = 1");
    }

}
