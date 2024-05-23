package com.thomsonreuters.codes.codesbench.quality.utilities.database.hierarchy;

public class HierarchyDatabaseConstants
{
    //Get contentUuid of a node based on it's nodeUuid
    public static final String GET_CONTENT_UUID_QUERY = "select distinct content_uuid from toc_content where content_uuid = (select content_uuid from toc_node where node_uuid = ?)";
    //Get legis_start_effective_date based on it's nodeUuid
    public static final String GET_START_DATE_QUERY = "select legis_start_effective_date from toc_node where node_uuid = ?";
    //Get legis_end_effective_date based on it's nodeUuid
    public static final String GET_END_DATE_QUERY = "select legis_end_effective_date from toc_node where node_uuid = ?";
    /*Update nodes status in hierarchy to Live Status*/
    public static final String SET_NODE_TO_LIVE_STATUS_QUERY = "update toc_node set legacy_status_code = 0 where content_uuid = ? and js_content_set_id = ?";
    /*Update nodes status in hierarchy to Reserve Status*/
    public static final String SET_NODE_TO_RESERVE_STATUS_QUERY = "update toc_node set legacy_status_code = 7 where content_uuid = ? and js_content_set_id = ?";
    /*Update nodes status in hierarchy to Repeal Status*/
    public static final String SET_NODE_TO_REPEAL_STATUS_QUERY = "update toc_node set legacy_status_code = 9 where content_uuid = ? and js_content_set_id = ?";
    /*Update nodes status in hierarchy to Transfer Status*/
    public static final String SET_NODE_TO_TRANSFER_STATUS_QUERY = "update toc_node set legacy_status_code = 8 where content_uuid = ? and js_content_set_id = ?";
    //Get nodeUuid of a node based on its contentUuid
    public static final String GET_NODE_UUID_WITH_CONTENT_UUID_QUERY = "select node_uuid from toc_node where content_uuid = ?";

    public static final String GET_HID_WITH_NODE_UUID = "select HID from toc_node where NODE_UUID = ?";

    /*Update nodes keyword in hierarchy to Blue Line*/
    public static final String SET_NODE_KEYWORD_TO_BLUELINE = "update toc_node set keyword_id = 100 where content_uuid = ? and js_content_set_id = ?";
    /*Update nodes keyword in hierarchy to BL Analysis*/
    public static final String SET_NODE_KEYWORD_TO_BL_ANALYSIS = "update toc_node set keyword_id = 102 where content_uuid = ? and js_content_set_id = ?";
    /*Update nodes keyword in hierarchy to NDRS*/
    public static final String SET_NODE_KEYWORD_TO_NDRS = "update toc_node set keyword_id = 103 where content_uuid = ? and js_content_set_id = ?";
    /*Update nodes keyword in hierarchy to ARL*/
    public static final String SET_NODE_KEYWORD_TO_ARL = "update toc_node set keyword_id = 104 where content_uuid = ? and js_content_set_id = ?";
    /* Update nodes keyword in hierarchy to SUB TITLE*/
    public static final String SET_NODE_KEYWORD_TO_SUB_TITLE = "update toc_node set keyword_id = 24 where content_uuid = ? and js_content_set_id = ?";

    /*Update nodes validation flag in hierarchy to Green check*/
    public static final String SET_NODE_TO_GREEN_CHECK_VALIDATION_FLAG_QUERY = "update toc_node set requires_verification_flag = 0 where content_uuid = ? and js_content_set_id = ?";
    public static final String SET_NODE_TO_GREEN_CHECK_VALIDATION_FLAG_QUERY_WITH_GIVEN_NODE_UUID = "update toc_node set requires_verification_flag = 0 where node_uuid = ? and js_content_set_id = ?";
    /*Update nodes validation flag in hierarchy to Error*/
    public static final String SET_NODE_TO_ERROR_VALIDATION_FLAG_QUERY = "update toc_node set requires_verification_flag = 1 where content_uuid = ? and js_content_set_id = ?";
    /*Update nodes validation flag in hierarchy to Warning*/
    public static final String SET_NODE_TO_WARNING_VALIDATION_FLAG_QUERY = "update toc_node set requires_verification_flag = 16 where content_uuid = ? and js_content_set_id = ?";
    /*Update nodes validation flag in hierarchy to Info*/
    public static final String SET_NODE_TO_INFO_VALIDATION_FLAG_QUERY = "update toc_node set requires_verification_flag = 4 where content_uuid = ? and js_content_set_id = ?";
    /*Getting nodes validation flag status*/
    public static final String GET_NODE_VALIDATION_FLAG = "select requires_verification_flag from toc_node where node_uuid = ?";

    public static final String SET_NODE_TO_DELETED_VALIDATION_FLAG = "update toc_node set is_deleted = 1 where content_uuid = ? and js_content_set_id = ?";

    /*Update nodes legis start effective date to a given date*/
    public static final String UPDATE_LEGIS_START_EFFECTIVE_DATE_QUERY = "update toc_node set LEGIS_START_EFFECTIVE_DATE = ? where Content_uuid = ?";
    /*Update nodes legis end effective date to a given date*/
    public static final String UPDATE_LEGIS_END_EFFECTIVE_DATE_QUERY = "update toc_node set LEGIS_END_EFFECTIVE_DATE = ? where Content_uuid = ?";
    public static final String UPDATE_MODIFIED_DATE_QUERY = "update toc_node set modified_date = ? where content_uuid = ?";
    /*Update nodes legis end effective date to null*/
    public static final String SET_LEGIS_END_EFFECTIVE_DATE_NULL_QUERY = "update toc_node set legis_end_effective_date = '' where node_uuid = ? and js_content_set_id = ?";
    //Update nodes volume number in hierarchy to the node given
    public static final String SET_NODE_VOLUME_TO_QUERY = "update toc_node set volume_num_str = ? where node_uuid = ? and js_content_set_id = ?";
    //Updates the nodes volume number in hierarchy to the children of the node uuid given, useful for resetting the volume number of descendants
    public static final String SET_NODE_VOLUME_TO_DESCENDANTS = "update toc_node set volume_num_str = ? where node_uuid in (select child_node_uuid from toc_node_link where parent_node_uuid = ?)";
    //Deletes node's alternative cite to reset data
    public static final String DELETE_ALTERNATIVE_CITE_QUERY = "delete from toc_node_alt_cite where node_uuid = ?";

    /*Get nodes value in hierarchy*/
    public static final String GET_NODE_VALUE_QUERY = "select val from toc_node where content_uuid = ? and js_content_set_id = ?";
    /*Get nodes value in hierarchy using node uuid*/
    public static final String GET_NODE_VALUE_BY_NODE_UUID_QUERY = "select val from toc_node where node_uuid = ? and js_content_set_id = ?";
    /*Get nodes legacy text*/
    public static final String GET_NODE_LEGACY_TEXT_QUERY = "select legacy_text from toc_node where content_uuid = ? and js_content_set_id = ?";
    /*Get nodes volume in hierarchy*/
    public static final String GET_NODE_VOLUME_QUERY_USING_CONTENT_UUID = "select volume_num_str from toc_node where content_uuid = ? and js_content_set_id = ?";
    public static final String GET_NODE_VOLUME_QUERY_USING_NODE_UUID = "select volume_num_str from toc_node where node_uuid = ? and js_content_set_id = ?";
    public static final String GET_NODE_VOLUME_TITLE_QUERY_USING_VOLUME_NUMBER = "select title from volume where volume_num_str = ? and js_content_set_id = ?";

    // Running the following two queries allows us to mock up a locked node. Simply insert a row into one of the tables is not sufficient due to database constraints.
    /*Insert node into TOC_NODE_LOCK table*/
    public static final String INSERT_NODE_INTO_TOC_NODE_LOCK_TABLE_QUERY = "insert into toc_node_lock(lock_uuid, locked_item_description, lock_type, lock_user_id, lock_date, lock_reason, volume_num_str, node_uuid) values (?, ?, ?, ?, ?, ?, ?, ?)";
    /*Insert node into LOCKED_TOC_NODES table*/
    public static final String INSERT_NODE_INTO_LOCKED_TOC_NODES_TABLE_QUERY = "insert into locked_toc_nodes(node_uuid, lock_uuid) values(?, ?)";

    // Running the following two queries allows us to clean up at the end of the test and remove the node from the tables we inserted into using the above two queries.
    /*Delete node in TOC_NODE_LOCK table*/
    public static final String DELETE_NODE_FROM_TOC_NODE_LOCK_TABLE_QUERY = "delete from toc_node_lock where node_uuid = ?";
    /*Delete node in LOCKED_TOC_NODES table*/
    public static final String DELETE_NODE_FROM_LOCKED_TOC_NODES_TABLE_QUERY = "delete from locked_toc_nodes where node_uuid = ?";

    //Set a node to deleted in hierarchy
    public static final String SET_NODE_DELETED_QUERY = "update toc_node set is_deleted = '1', modified_by = ?, modified_date = current_timestamp, update_num = update_num + 1 where node_uuid = ?";
    public static final String SET_NODE_DELETED_DISP_DERIV_TABLE_QUERY = "update toc_node_disp_deriv set is_deleted = '1', modified_by = ?, modified_date = current_timestamp where deriv_node_uuid = ? or disp_node_uuid = ?";
    //Set a node to undeleted in hierarchy
    public static final String SET_NODE_UNDELETED_QUERY = "update toc_node set is_deleted = '0', modified_by = ?, modified_date = current_timestamp, update_num = update_num + 1 where node_uuid = ?";
    public static final String SET_NODE_UNDELETED_DISP_DERIV_TABLE_QUERY = "update toc_node_disp_deriv set is_deleted = '0', modified_by = ?, modified_date = current_timestamp where deriv_node_uuid = ? or disp_node_uuid = ?";

    //Deep delete a node
    public static final String DELETE_NODE_FROM_DISP_DERIV_QUERY = "delete from toc_node_disp_deriv where disp_node_uuid = ? or deriv_node_uuid = ?";
    public static final String DELETE_NODE_FROM_PUB_DISP_DERIV_QUERY = "delete from pub_toc_node_disp_deriv where disp_node_uuid = ? or deriv_node_uuid = ?";

    public static final String DELETE_NODE_FROM_SCRIPT_ASSIGNMENT_QUERY = "delete from toc_node_script_assignment where node_uuid = ?";
    public static final String DELETE_NODE_FROM_DOC_FAMILY_LINK_QUERY = "delete from toc_node_doc_family_link where node_uuid = ?";
    public static final String DELETE_NODE_FROM_DOC_FAMILY_QUERY = "delete from toc_node_doc_family where doc_family_uuid = (select doc_family_uuid from toc_node_doc_family_link where node_uuid = ?)";
    public static final String DELETE_NODE_FROM_LINK_LAST_UPLOAD_QUERY = "delete from toc_node_link_last_upload where parent_child_uuid in (select parent_child_uuid from toc_node_link where child_node_uuid = ?)";
    public static final String DELETE_NODE_FROM_LINK_UPLOAD_HISTORY_QUERY = "delete from toc_node_link_upload_history where parent_child_uuid in (select parent_child_uuid from toc_node_link where child_node_uuid = ?)";
    public static final String DELETE_NODE_FROM_LINK_CHILD_QUERY = "delete from toc_node_link where child_node_uuid = ?";
    public static final String DELETE_NODE_FROM_LINK_PARENT_QUERY = "delete from toc_node_link where parent_node_uuid = ?";
    public static final String DELETE_NODE_FROM_RESEQUENCED_AUDIT_QUERY = "delete from resequenced_node_audit where parent_node_uuid = ?";
    public static final String DELETE_NODE_FROM_AUDIT_QUERY = "delete from toc_node_audit where node_uuid = ?";
    public static final String DELETE_NODE_FROM_ALT_CITE_QUERY = "delete from toc_node_alt_cite where node_uuid = ?";
    public static final String DELETE_NODE_FROM_AUDIT_LATEST_QUERY = "delete from toc_node_audit_latest where node_uuid = ?";
    public static final String DELETE_NODE_FROM_CHECKED_OUT_QUERY = "delete from toc_node_checked_out where node_uuid = ?";
    public static final String DELETE_NODE_FROM_EXTENDED_METADATA_QUERY = "delete from toc_node_extended_metadata where node_uuid = ?";
    public static final String DELETE_NODE_FROM_GLOSSARY_LINK_QUERY = "delete from toc_node_glossary_link where node_uuid = ?";
    public static final String DELETE_NODE_FROM_KEY_VALUE_METADATA_QUERY = "delete from toc_node_key_value_metadata where node_uuid = ?";
    public static final String DELETE_NODE_FROM_LAST_UPLOAD_QUERY = "delete from toc_node_last_upload where node_uuid = ?";
    public static final String DELETE_NODE_FROM_SRC_DOC_LINK_QUERY = "delete from toc_node_src_doc_link where toc_node_uuid = ?";
    public static final String DELETE_NODE_FROM_UPLOAD_HISTORY_QUERY = "delete from toc_node_upload_history where node_uuid = ?";
    public static final String DELETE_NODE_FROM_WIP_METADATA_QUERY = "delete from toc_node_wip_metadata where node_uuid = ?";
    public static final String DELETE_NODE_FROM_TOC_CONTENT_QUERY = "delete from toc_content where content_uuid = (select content_uuid from toc_node where node_uuid = ?)";
    public static final String DELETE_NODE_FROM_PUB_EXTENDED_METADATA_QUERY = "delete from pub_toc_node_extended_metadata where node_uuid = ?";
    public static final String DELETE_NODE_FROM_PUB_SCRIPT_ASSIGNMENT_QUERY = "delete from pub_toc_node_script_assignment where node_uuid = ?";
    public static final String DELETE_NODE_FROM_PUB_LINK_CHILD_QUERY = "delete from pub_toc_node_link where child_node_uuid = ?";
    public static final String DELETE_NODE_FROM_PUB_LINK_PARENT_QUERY = "delete from pub_toc_node_link where parent_node_uuid = ?";
    public static final String DELETE_NODE_FROM_PUB_TOC_NODE_QUERY = "delete from pub_toc_node where node_uuid = ?";
    public static final String DELETE_NODE_FROM_UPLOAD_CANDIDATE_QUERY = "delete from upload_candidate where node_uuid = ?";
    public static final String DELETE_NODE_FROM_TOC_NODE_QUERY = "delete from toc_node where node_uuid = ?";

    /*Get node values*/
    public static final String GET_NODE_CODE_NAME_QUERY = "select code_name from codes where code_id = (select code_id from toc_node where content_uuid = ?)";
    public static final String GET_NODE_CODE_ID_QUERY = "select code_id from toc_node where node_uuid = ?";
    public static final String GET_NODE_TYPE_QUERY = "select node_type_id from toc_node where node_uuid = ?";
    public static final String GET_NODE_KEYWORD_QUERY = "select keyword_id from toc_node where node_uuid = ?";
    public static final String GET_NODE_LEGACY_STATUS_CODE = "select legacy_status_code from toc_node where node_uuid = ?";
    public static final String GET_NODE_CODE_LEVEL_QUERY = "select is_code_level from toc_node where node_uuid = ?";
    public static final String GET_NODE_VERSIONED_QUERY = "select is_versioned from toc_node where node_uuid = ?";
    public static final String GET_NODE_MODIFIED_DATE = "select modified_date from toc_node where node_uuid = ?";
    public static final String GET_NODE_MODIFIED_BY = "select modified_by from toc_node where node_uuid = ?";

    //Running the following two queries allows us to insert a date query note into the database using a given node uuid and specific para uuid
    /*Insert query note into paragraph with given para uuid of node with given node uuid*/
    public static final String INSERT_DATE_QUERY_NOTE = "INSERT INTO query_note(NOTE_UUID, NOTE_TYPE, TEXT, CREATED_BY, CREATED_DATE, NODE_UUID, STATUS, ACTION_DATE, PARA_UUID, JS_CONTENT_SET_ID, TEXT_PART) values (?, 1, ?, ?, sysdate, ?, 1, sysdate, ?, ?, ?)";
    /*Mocks an automatically created query that is generated whenever a user inserts a date, contingency or revisor query note into a node*/
    public static final String INSERT_AUTOMATICALLY_CREATED_QUERY = "INSERT INTO query_note(NOTE_UUID, NOTE_TYPE, TEXT, CREATED_BY, CREATED_DATE, NODE_UUID, STATUS, ACTION_DATE, JS_CONTENT_SET_ID, TEXT_PART) values (?, 9, 'Automatically created due to DATE, CONTINGENCY, or REVISOR created query on the target node', ?, sysdate, ?, 1, sysdate, ?, 'Automatically created due to DATE, CONTINGENCY, or REVISOR created query on the target node')";

    public static final String GET_NODE_HIERARCHY_TREE_PAGE_TITLE = "select k.default_display_name, tn.val, tn.head_text from keyword k, toc_node tn where k.keyword_id = (select keyword_id from toc_node where node_uuid = ?) and tn.node_uuid = ?";
    public static final String GET_NODE_PARENT_UUID = "select parent_node_uuid from toc_node_link where child_node_uuid = ?";

    /* Update node values */
    public static final String UPDATE_NODE_CODE_ID_QUERY = "update toc_node set code_id = ? where node_uuid = ?";
    public static final String UPDATE_NODE_TYPE_QUERY = "update toc_node set node_type_id = ? where node_uuid = ?";
    public static final String UPDATE_NODE_CODE_LEVEL_QUERY = "update toc_node set is_code_level = ? where node_uuid = ?";
    public static final String UPDATE_NODE_VERSIONED_QUERY = "update toc_node set is_versioned = ? where node_uuid = ?";
    public static final String UPDATE_NODE_MODIFIED_BY_QUERY = "update toc_node set modified_by = ? where node_uuid = ?";

    /*In order to use any of the following queries that update a wip version's law tracking status, the node must exists within the toc_node_audit table */

    /*Set the law tracking status for a specific wip version of node to Full Vols */
    public static final String UPDATE_WIP_VERSION_LAW_TRACKING_STATUS_TO_FULL_VOLS_QUERY = "update toc_node_audit set material_id = 'I5327012EA95D11DA958400E08161165F' where content_uuid = ? and wip_version = ?";
    /*Set the law tracking status for a specific wip version of node to Quick Load */
    public static final String UPDATE_WIP_VERSION_LAW_TRACKING_STATUS_TO_QUICK_LOAD_QUERY = "update toc_node_audit set material_id = 'I68C977DCA95D11DA958400E08161165F' where content_uuid = ? and wip_version = ?";
    /*Set the law tracking status for a specific wip version of node to Full Vols Compare */
    public static final String UPDATE_WIP_VERSION_LAW_TRACKING_STATUS_TO_FULL_VOLS_COMPARE_QUERY = "update toc_node_audit set material_id = 'IE151E470643B11EA9B3000505683AFB9' where content_uuid = ? and wip_version = ?";
    /*Set the law tracking status for a specific wip version of node to Full Vols Recomp */
    public static final String UPDATE_WIP_VERSION_LAW_TRACKING_STATUS_TO_FULL_VOLS_RECOMP_QUERY = "update toc_node_audit set material_id = 'IE1520B80643B11EA9B3000505683AFB9' where content_uuid = ? and wip_version = ?";
    /*Set the law tracking status for a specific wip version of node to Com Law Tracking */
    public static final String UPDATE_WIP_VERSION_LAW_TRACKING_STATUS_TO_COM_LAW_TRACKING = "update toc_node_audit set material_id = 'I870CF35267B011EBBE6FA8AD556CB520' where content_uuid = ? and wip_version = ?";

    /* get the column information via the keyword, volume number and the Content set */
    public static final String GET_NODE_UUID_WITH_VALUE = "select * From TOC_NODE WHERE KEYWORD_ID = ? AND JS_CONTENT_SET_ID = ? and VOLUME_NUM_STR = ? and val = ? ";

    public static final String GET_NODE_UUID_WITH_NULL_VALUE = "select * From TOC_NODE WHERE KEYWORD_ID = ? AND JS_CONTENT_SET_ID = ? and VOLUME_NUM_STR = ? and val IS NULL";

    /*Update Delete Flag to be 1 for Deletion*/
    public static final String UPDATE_DELETE_FLAG_TO_BE_DELETED_HIERARCHY_NAVIAGTE = "update toc_node set IS_DELETED = ? where NODE_UUID = ? and js_content_set_id = ?";

    /*Update Delete Flag to be 1 for Deletion in pub Navigate*/
    public static final String UPDATE_DELETE_FLAG_TO_BE_DELETED_PUB_NAVIGATE = "update pub_toc_node set IS_DELETED = ? where NODE_UUID = ? and js_content_set_id = ?";

    /*get the latest/Max Wip Version for the selected content uuid*/
    public static final String GET_LATEST_WIP_VERSION = "select MAX(wip_version_num) FROM Toc_Content Where Content_uuid = ?";

    /* get the modified by for the selected wip version and content set*/
    public static final String GET_MODIFIED_BY = "select MODIFIED_BY FROM Toc_Content Where Content_uuid = ? AND wip_version_num = ?";

    public static final String UPDATE_LEGIS_START_EFFECTIVE_DATE = "update toc_node set LEGIS_START_EFFECTIVE_DATE = ? where Content_uuid = ?";
    public static final String UPDATE_LEGIS_END_EFFECTIVE_DATE = "update toc_node set LEGIS_END_EFFECTIVE_DATE = ? where Content_uuid = ?";
    public static final String UPDATE_Modified_DATE = "update toc_node set MODIFIED_DATE = ? where Content_uuid = ?";

    /*This query gets all children of a given node that are NOT deleted*/
    public static final String GET_NODE_CHILDREN_FOR_GIVEN_NODE = "select child_node_uuid from toc_node_link inner join toc_node on toc_node_link.child_node_uuid = toc_node.node_uuid where toc_node_link.parent_node_uuid = ? and toc_node.is_deleted = 0";

    public static final String SELECT_NODE_FROM_TOC_NODE = "select * from TOC_NODE where content_uuid = ? and js_content_set_id = ?";

    public static final String GET_CURRENT_CODE_ID_QUERY = "select code_id from toc_node where node_uuid = ?";

    public static final String GET_CURRENT_CODE_NAME = "select code_name from codes where code_id = ?";

    public static final String GET_CURRENT_CODE_NAME_AND_CODE_ID = "select toc_node.code_id, codes.code_name from toc_node inner join codes on toc_node.code_id = codes.code_id where node_uuid = ?";

    public static final String DELETE_VOLUME_FROM_VOLUME_PART = "DELETE from VOLUME_PART where VOLUME_ID = (select VOLUME_ID from VOLUME where TITLE = ? and VOLUME_NUM_STR = ? and JS_CONTENT_SET_ID = ?)";
    public static final String DELETE_VOLUME_FROM_VOLUME = "DELETE from VOLUME where VOLUME_ID = (select VOLUME_ID from VOLUME where TITLE = ? and VOLUME_NUM_STR = ? and JS_CONTENT_SET_ID = ?)";
    public static final String DELETE_CREATED_CODE_NAME_QUERY = "delete from codes where created_by = ? and code_name = ? and js_content_set_id = ?";

    public static final String GET_MAX_TOC_NODE_AUDIT_ROW = "select max(audit_id) as audit_id from toc_node_audit";

    public static final String INSERT_RECORD_INTO_TOC_NODE_AUDIT = "insert into toc_node_audit (audit_id, node_uuid, content_uuid, material_id, wip_version, job_id, created_by, created_date, hierarchy_function, node_inserted, node_metadata_modified, derived_from_changed, children_changed, script_changed, content_changed, alt_cite_changed, transaction_id, flag_changed, js_content_set_id)" +
            " values (?,?,?,?,?,?,?,current_timestamp,?,?,?,?,?,?,?,?,?,?,?)";

    public static final String INSERT_NEW_WIP_VERSION_WITH_VALUE = "insert into toc_content (content_uuid, wip_version_num, created_by, created_date, modified_date, text, display_head_text, cs_id) values(?, ?, ?, current_timestamp, current_timestamp, ?, ?, ?)";

    public static final String UNSET_LATEST_WIP_VERSION = "UPDATE TOC_CONTENT SET LATEST_FLAG = 0 WHERE CONTENT_UUID = ? AND LATEST_FLAG = 1";

    public static final String GET_KEYWORD_DEFAULT_DISPLAY_NAME = "select default_display_name from keyword where keyword_id = (select keyword_id from toc_node where node_uuid = ?)";

    public static String LEGACY_TEXT_PREPARED_STATEMENT = "SELECT LEGACY_TEXT FROM TOC_NODE WHERE NODE_UUID = ?";

    public static String VOLUME_NUM_STR_PREPARES_STATEMENT = "SELECT VOLUME_NUM_STR FROM TOC_NODE WHERE NODE_UUID = ?";

    public static String SET_NODE_VALUE_PREPARED_STATEMENT = "UPDATE TOC_NODE SET VAL = ? WHERE NODE_UUID = ?";
    public static String UPDATE_LEGACY_INIT_RANGE_HID = "update toc_node set LEGACY_INIT_RANGE_HID = ? where node_uuid = ?";

    public static String UPDATE_INIT_RANGE_NODE_UUID = "update toc_node SET INIT_RANGE_NODE_UUID = ? where node_uuid = ?";

    public static String SET_NODE_TO_DATA_ERROR_VALIDATION_FLAG_STATEMENT = "UPDATE TOC_CONTENT SET REQUIRES_VERIFICATION_FLAG = 3145728 WHERE CONTENT_UUID = ? AND LATEST_FLAG = 1";

    public static String SET_NODE_TO_DTD_ERROR_VALIDATION_FLAG_STATEMENT = "UPDATE TOC_CONTENT SET REQUIRES_VERIFICATION_FLAG = 8192 WHERE CONTENT_UUID = ? AND LATEST_FLAG = 1";
}