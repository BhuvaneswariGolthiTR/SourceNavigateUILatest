package com.thomsonreuters.codes.codesbench.quality.utilities.database.nodangular;

public class NodAngularDatabaseConstants
{
    /**
     * Get queries
     */
    public static final String GET_OPINION_UUID_FROM_NODE_ADMIN_OPINION_TABLE = "select opinion_uuid from nod_admin_opinion where opinion_number = ? and js_content_set_id = ?";
    public static final String GET_CASE_UUID_FROM_NOD_CASE_TABLE = "select case_uuid from nod_case where case_serial_number = ?";
    public static final String GET_SUBSCRIBED_CASE_UUID_FROM_NOD_SUBSCRIBED_CASE_TABLE = "select subscribed_case_uuid from nod_subscribed_case where case_uuid = ? and js_content_set_id = ?";
    public static final String GET_HEADNOTE_UUID_FROM_NOD_HEADNOTE_TABLE = "select headnote_uuid from nod_headnote where case_uuid = ? and headnote_number = ? and is_reloaded = 1";
    public static final String GET_MAX_HEADNOTE_NUMBER = "select max(headnote_number) as headnote_number from nod_headnote where case_uuid = ? and is_reloaded = 1";

    /**
     *  Set queries
     */
    public static final String SET_CCDB_NUMBER_IN_NOD_CASE_TABLE = "update nod_case set ccdb = ? where case_uuid = ?";

    /**
     * Insert queries
     */
    public static final String INSERT_ADMIN_OPINION_INTO_NOD_ADMIN_OPINION_TABLE = "insert into nod_admin_opinion(opinion_uuid, category, text, opinion_number, create_date, js_content_set_id) Values (?, ?, ?, ?, current_timestamp, ?)";
    //case serial num format - '1234-567880'
    public static final String INSERT_CASE_INTO_NOD_CASE_TABLE = "insert into nod_case(case_uuid, loaded_date, headnotes, title, subscription, is_killed," +
                                                                     " is_reloaded, case_serial_number, reporter_number, court_uuid, create_date, modify_date, spurs_load_date, is_reported, is_user_reporter, is_no_team)" +
                                                                     " Values (?, current_timestamp, 0, ?, 0, 0, 0, ?, 999, ?, sysdate, sysdate, current_timestamp, 0, 0, 0)";
    public static final String INSERT_SUBSCRIBED_CASE_INTO_NOD_SUBSCRIBED_CASE_TABLE = "insert into nod_subscribed_case(subscribed_case_uuid, case_uuid, js_content_set_id, from_content_set_id, ai_ignored_headnotes) Values (?, ?, ?, ?, 0)";
    public static final String INSERT_HEADNOTE_INTO_NOD_HEADNOTE_TABLE = "insert into nod_headnote(headnote_uuid, case_uuid, headnote_number, headnote_text, is_reloaded, is_killed) Values (?, ?, ?, ?, 1, 0)";
    public static final String INSERT_SUBSCRIBED_HEADNOTE_INTO_NOD_SUBSCRIBED_HEADNOTE_TABLE = "insert into nod_subscribed_headnote(subscribed_headnote_uuid, subscribed_case_uuid, headnote_uuid, is_ignored) Values (?, ?, ?, 0)";
    public static final String INSERT_HEADNOTE_TOPIC_KEY_INTO_NOD_TOPIC_KEY_TABLE = "insert into nod_topic_key(topic_key_uuid, headnote_uuid, topic_number, topic_name, key_number) Values (?, ?, ?, ?, ?)";

    /**
     * Delete queries
     */
    public static final String DELETE_ADMIN_OPINION_FROM_NOD_ADMIN_OPINION_TABLE = "delete from nod_admin_opinion where opinion_uuid = ? and js_content_set_id = ?";
    public static final String DELETE_CASE_FROM_NOD_CASE_TABLE = "delete from nod_case where case_uuid = ?";
    public static final String DELETE_SUBSCRIBED_CASE_FROM_NOD_CASE_TABLE = "delete from nod_subscribed_case where case_uuid = ?";
    public static final String DELETE_HEADNOTE_FROM_NOD_HEADNOTE_TABLE = "delete from nod_headnote where headnote_uuid = ?";
    public static final String DELETE_HEADNOTE_FROM_NOD_SUBSCRIBED_HEADNOTE_TABLE = "delete from nod_subscribed_headnote where headnote_uuid = ?";
    public static final String DELETE_HEADNOTE_TOPIC_KEY_FROM_NOD_TOPIC_KEY_TABLE = "delete from nod_topic_key where headnote_uuid = ?";

    public static final String DELETE_ALL_HEADNOTE_FROM_NOD_HEADNOTE_TABLE = "delete from nod_headnote where case_uuid = ?";
    public static final String DELETE_ALL_HEADNOTE_FROM_NOD_SUBSCRIBED_HEADNOTE_TABLE = "delete from nod_subscribed_headnote where subscribed_case_uuid = ?";
    public static final String DELETE_ALL_HEADNOTE_TOPIC_KEY_FROM_NOD_TOPIC_KEY_TABLE = "delete from nod_topic_key where headnote_uuid in (select headnote_uuid from nod_headnote where case_uuid = ?)";
}
