package com.thomsonreuters.codes.codesbench.quality.utilities.xml;

import org.custommonkey.xmlunit.Diff;
import org.custommonkey.xmlunit.Difference;
import org.custommonkey.xmlunit.DifferenceConstants;
import org.custommonkey.xmlunit.DifferenceListener;
import org.w3c.dom.Node;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class DifferenceListenerOverriderUtility implements DifferenceListener
{
    List<String> ignoreDifferencesList;

    public DifferenceListenerOverriderUtility()
    {
        ignoreDifferencesList = getIgnoreDifferencesList();
    }

    private List<String> getIgnoreDifferencesList()
    {
        File ignoreDifferencesList = new File("commonFiles\\TestFiles\\ignoreDifferencesList.txt");
        List<String> customIgnoredDifferencesList = new ArrayList<>();
        try
        {
            customIgnoredDifferencesList = Files.readAllLines(ignoreDifferencesList.toPath(), Charset.defaultCharset());
        }
        catch(IOException e)
        {
            System.out.println("Unable to load the custom ignored differences list.  This will cause tests to throw false negative results.");
            e.printStackTrace();
        }
        return customIgnoredDifferencesList;
    }

    private boolean containsAndNotNull(String s)
    {
        if(s == null)
        {
            return false;
        }
        return ignoreDifferencesList.contains(s);
    }

    @Override
    public int differenceFound(Difference difference)
    {
        /*
         * This is used for catching inner node with specific attributes. There are cases where documents
         * will have a changing id (such as a table id) that will be different for each run and this is an
         * acceptable change.
         */
        switch (difference.getId())
        {
            case DifferenceConstants.ATTR_VALUE_ID:
                if (containsAndNotNull(difference.getControlNodeDetail().getNode().getNodeName()))
                {
                    return DifferenceListener.RETURN_IGNORE_DIFFERENCE_NODES_IDENTICAL;
                }

                /*
                 * This portion will catch most problematic XML nodes in the files. Examples such as:
                 * md.publisheddatetime, md.loaddate. & sent.to.nims.date will be caught here
                 */
            case DifferenceConstants.TEXT_VALUE_ID:
                String s = "";
                try
                {
                    s = difference.getControlNodeDetail().getNode().getNodeName();
                }
                catch (NullPointerException e) { }
                if (containsAndNotNull(s))
                {
                    return DifferenceListener.RETURN_IGNORE_DIFFERENCE_NODES_IDENTICAL;
                }
                try
                {
                    s = difference.getControlNodeDetail().getNode().getParentNode().getNodeName();
                }
                catch (NullPointerException e) { }
                if (containsAndNotNull(s))
                {
                    return DifferenceListener.RETURN_IGNORE_DIFFERENCE_NODES_IDENTICAL;
                }
                try
                {
                    s = difference.getControlNodeDetail().getNode().getParentNode().getParentNode().getNodeName();
                }
                catch (NullPointerException e) { }
                if (containsAndNotNull(s))
                {
                    return DifferenceListener.RETURN_IGNORE_DIFFERENCE_NODES_IDENTICAL;
                }
                try
                {
                    s = difference.getControlNodeDetail().getNode().getParentNode().getAttributes().getNamedItem("name").getNodeValue();
                }
                catch (NullPointerException e) { }
                if(containsAndNotNull(s))
                {
                    return DifferenceListener.RETURN_IGNORE_DIFFERENCE_NODES_IDENTICAL;
                }

                /*
                 *  If the difference node is of this Id, it means that the Node is a compilation of a numerous other XML nodes.
                 *  There was no simple and easy way to get and compare these inner nodes, so instead, the String compilation
                 *  is taken and passed through a new Diff object using the existing IgnoreNamedElementsDifferenceListener.
                 *  Thus, all of these inner nodes will be checked, and can still be ignored by the already defined blacklist
                 */
            case DifferenceConstants.CDATA_VALUE_ID:
                String controlText = difference.getControlNodeDetail().getValue();
                String testText = difference.getTestNodeDetail().getValue();
                s = "";
                try
                {
                    s = difference.getControlNodeDetail().getNode().getParentNode().getParentNode().getNodeName();
                }
                catch (NullPointerException e) { }
                if (containsAndNotNull(s))
                {
                    return DifferenceListener.RETURN_IGNORE_DIFFERENCE_NODES_IDENTICAL;
                }
                if(performXMLDiffCompare(controlText, testText, this))
                {
                    return DifferenceListener.RETURN_IGNORE_DIFFERENCE_NODES_IDENTICAL;
                }

            default:
                return DifferenceListener.RETURN_ACCEPT_DIFFERENCE;
        }
    }

    private boolean performXMLDiffCompare(String goldText, String testText, DifferenceListenerOverriderUtility differenceListenerOverriderUtility)
    {
        try
        {
            Diff xmlDiff = new Diff(goldText, testText);
            if (differenceListenerOverriderUtility != null)
            {
                xmlDiff.overrideDifferenceListener(differenceListenerOverriderUtility);
            }
            return xmlDiff.similar();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void skippedComparison(Node arg0, Node arg1) { }
}
