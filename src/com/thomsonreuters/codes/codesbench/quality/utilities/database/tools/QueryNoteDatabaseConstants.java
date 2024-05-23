package com.thomsonreuters.codes.codesbench.quality.utilities.database.tools;

public class QueryNoteDatabaseConstants {

    public static final String INSERT_DATE_QUERY_NOTE_PREPARED_STATEMENT_STRING = "INSERT INTO query_note(NOTE_UUID, NOTE_TYPE, CREATED_BY, CREATED_DATE, NODE_UUID, STATUS, JS_CONTENT_SET_ID) values (?, 1, ?, sysdate, ?, 1, ?)";

    public static final String DELETE_QUERY_NOTE_UUID_PREPARED_STATEMENT_STRING = "DELETE FROM query_note WHERE note_uuid = ?";

    public static final String UPDATE_QUERY_NOTE_TEXT_PREPARED_STATEMENT_STRING = "UPDATE query_note SET text_part = ?, text = ? WHERE note_uuid = ?";

    public static final String GET_NOTE_UUID_QUERY = "select note_uuid from query_note where node_uuid = ? and js_content_set_id = ?";
}

