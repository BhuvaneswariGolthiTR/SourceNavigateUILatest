package com.thomsonreuters.codes.codesbench.quality.utilities.database.publishing;

public class PublishingDatabaseConstants
{
    /*Select Record (Debugging Purposes)*/
    public static final String SELECT_PUBLISHING_NODE_QUERY = "select * from toc_content_publishing_status where content_uuid = ? and js_content_set_id = ?";

    /*Reset Publishing Values (Run before mocking up data to ensure a clean state)*/
    public static final String RESET_PUBLISHING_NODE_QUERY = "update toc_content_publishing_status" +
            " set publish_status_id = 0, publish_ready_user = null, publish_ready_date = null, publish_approved_user = null, publish_approved_date = null, publish_hold_user = null, publish_hold_date = null, publishing_date = null, codesbench_failure_date = null, ltc_failure_date = null, loaded_to_westlaw_date = null, pub_complete_date = null, workflow_id = null, error_message = null" +
            " where content_uuid = ?" +
            " and js_content_set_id = ?";
    public static final String SET_NODE_TO_APPROVE_QUERY = "update toc_content_publishing_status set publish_status_id = 2 where content_uuid = ? and js_content_set_id = ?";
    /*Not Published - Node appears in the Publish Ready Selection UI*/
    public static final String SET_NODE_TO_NOT_PUBLISH_QUERY = "update toc_content_publishing_status set publish_status_id = 0 where content_uuid = ? and js_content_set_id = ?";

    /*Publish Ready - Node appears in the Publish Approve Selection UI*/
    public static final String SET_NODE_TO_READY_QUERY = "update toc_content_publishing_status set publish_status_id = 1 where content_uuid = ? and js_content_set_id = ?";

    public static final String SET_NODE_TO_PUBLISH_COMPLETE_QUERY = "update toc_content_publishing_status set publish_status_id = 3 where content_uuid = ? and js_content_set_id = ?";

    public static final String SET_NODE_TO_WESTLAW_LOADED_QUERY = "update toc_content_publishing_status set publish_status_id = 8 where content_uuid = ? and js_content_set_id = ?";

    /*CodesBench Failure - Node appears in the Issues after Pub Complete UI*/
    public static final String SET_NODE_TO_CODESBENCH_FAILURE_QUERY = "update toc_content_publishing_status set publish_status_id = 5, codesbench_failure_date = CURRENT_TIMESTAMP, error_message = 'CodesBench Failure Test' where content_uuid = ? and js_content_set_id = ?";

    /*Hold node - Node appears in the Pub Evaluation UI*/
    public static final String SET_PUB_NODE_TO_HOLD_STATUS = "update toc_content_publishing_status set publish_status_id = 3 where content_uuid = ? and js_content_set_id = ?";

    /*LTC Failure - Node appears in the Issues after Pub Complete UI*/
    public static final String SET_NODE_TO_LTC_FAILURE_QUERY = "update toc_content_publishing_status set publish_status_id = 6, ltc_failure_date = CURRENT_TIMESTAMP, error_message = 'LTC Failure Test' where content_uuid = ? and js_content_set_id = ?";

    /*LTC Failure - Node appears in the Issues after Pub Complete UI*/
    public static final String SET_NODE_TO_PUBLISHING_QUERY = "update toc_content_publishing_status set publish_status_id = 4, publishing_date = CURRENT_TIMESTAMP, error_message = 'Publishing Test' where content_uuid = ? and js_content_set_id = ?";

    /*Law tracking status column will no longer be populated with certain law tracking statuses */
    public static final String REMOVE_NODE_LAW_TRACKING_STATUS_FROM_PUBLISHING_UI = "delete from toc_node_audit where content_uuid = ? and js_content_set_id = ? and wip_version = (select max(wip_version) from toc_node_audit where content_uuid = ? and js_content_set_id = ?)";

    /*Update Loaded To Westlaw date*/
    public static final String UPDATE_LOADED_TO_WESTLAW_DATE_ON_PUBLISHING_UI = "update toc_content_publishing_status set loaded_to_westlaw_date = ? where content_uuid = ? and js_content_set_id = ?";

    /*Check if content set is enabled for publishing */
    public static final String IS_CONTENT_SET_ENABLED_FOR_PUBLISHING = "select property_value from js_content_set_properties where property_name = 'Is publishing enabled for particular content set' and js_content_set_id = ?";

    /*Update content set to be enabled for publishing*/
    public static final String UPDATE_CONTENT_SET_TO_BE_ENABLED_FOR_PUBLISHING = "update js_content_set_properties set property_value = 'true' where property_name = 'Is publishing enabled for particular content set' and js_content_set_id = ?";

    /*Update content set to be disabled for publishing*/
    public static final String UPDATE_CONTENT_SET_TO_BE_DISABLED_FOR_PUBLISHING = "update js_content_set_properties set property_value = 'false' where property_name = 'Is publishing enabled for particular content set' and js_content_set_id = ?";

    public static final String GET_LIST_OF_PUBLISH_APPROVERS_FOR_CONTENT_SET_QUERY = "select property_value from js_content_set_properties where property_name = 'List of possible publishing approvals' and js_content_set_id = ?";
    public static final String UPDATE_LIST_OF_PUBLISH_APPROVERS_FOR_CONTENT_SET_QUERY = "update js_content_set_properties set property_value = ? where property_name = 'List of possible publishing approvals' and js_content_set_id = ?";
    public static final String PUBLISH_APPROVER_SKELETON = "<approval><userName>%s</userName><userId>%s</userId></approval>";
    public static final String APPROVALS_CLOSING_TAG = "</approvals>";

    /*Insert Node into table*/
    public static final String INSERT_NODE_INTO_TOC_CONTENT_PUBLISHING_STATUS_TABLE = "insert into Toc_content_publishing_Status(Content_uuid, wip_version_num, Publish_status_id, Publish_Status_uuid) Values (?, ?, ?, ?)";

    /*Delete Node from table*/
    public static final String SET_NODE_TO_ERROR_PUBLISHING_STATUS = "delete from toc_content_publishing_status where content_uuid = ? and js_content_set_id = ?";

    public static final String GET_PUBLISHING_STATUS = "Select publish_status_id FROM toc_content_publishing_status where content_uuid = ? and js_content_set_id = ? and wip_version_num =?";

    public static final String SELECT_FROM_PUB_LEGACY_NORM_TBL_WITHOUT_CITATION_PREFIX = "select * from PUB_LEGACY_NORM_TBL WHERE Content_set_name = ? and POSITION = ?";
    public static final String SELECT_POSITION_FROM_PUB_LEGACY_NORM_TBL = "Select position from pub_legacy_norm_tbl where Content_set_name = ? AND CITATION_PREFIX=?";

}
