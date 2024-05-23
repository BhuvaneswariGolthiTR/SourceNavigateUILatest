package com.thomsonreuters.codes.codesbench.quality.utilities.datamocking;

public interface DataMockingInterface
{
    /*
    NOTE:
    - We have database utilities created in the database package to get
    information, manipulate, and delete specific nodes.

    - This interface, and further data mocking in general, is solely
    interested in inserting data, and returning the node uuid to work with nodes in the UI and database package.

    - Example usage:
    DataMocking.Target.Iowa.SmallIowa.insert("uat", "106");
    DataMocking.Target.Iowa.SmallIowa.delete("uat", "106");
     */

    /**
     * Method signature to insert data into the database.
     * @param environment: The environment to insert the data into
     * @param contentSetId: The contentSetId of the content set to insert the data into
     * @return String nodeUUID for the node inserted in the database
     */
    String insert(String environment, String contentSetId);

    /**
     * Method signature to delete data from the database.
     * @param environment: The environment to delete data from
     * @param contentSetId: The contentSetId of the content set to delete data from
     * @return boolean result of deletion.  true if deletion worked, and false otherwise
     */
    boolean delete(String environment, String contentSetId);
}
