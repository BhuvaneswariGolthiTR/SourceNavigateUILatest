package com.thomsonreuters.codes.codesbench.quality.utilities.maps;

import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.datapod.nodes.HierarchyNode;

import java.util.*;

public class HashMapUtilities
{

    /**
     * Takes in a HashMap with a HierarchyNode key and an Integer value, and sorts it from high to low based on the value.
     * It is possible that changing HierarchyNode to Object would allow for more use of this method elsewhere, but it has not been tested in that way.
     *
     * @param hashMap The HashMap being input
     * @return The sorted HashMap
     */
    public static HashMap<HierarchyNode, Integer> sortByValueHighToLow(HashMap<HierarchyNode, Integer> hashMap)
    {
        
        List<Map.Entry<HierarchyNode, Integer>> entryList = new LinkedList<>(hashMap.entrySet());

        entryList.sort(Map.Entry.comparingByValue());
        Collections.reverse(entryList);

        HashMap<HierarchyNode, Integer> nodeIntegerLinkedHashMap = new LinkedHashMap<>();
        for (Map.Entry<HierarchyNode, Integer> nodeIntegerEntry : entryList)
        {
            nodeIntegerLinkedHashMap.put(nodeIntegerEntry.getKey(), nodeIntegerEntry.getValue());
        }
        return nodeIntegerLinkedHashMap;
    }

    /**
     * @param hashMap The HashMap being input
     * @return The sorted HashMap in ascending order
     */
    public static HashMap<Object, Integer> sortByValueLowToHigh(HashMap<Object, Integer> hashMap)
    {

        List<Map.Entry<Object, Integer>> entryList = new LinkedList<>(hashMap.entrySet());

        entryList.sort(Map.Entry.comparingByValue());

        HashMap<Object, Integer> nodeIntegerLinkedHashMap = new LinkedHashMap<>();
        for (Map.Entry<Object, Integer> nodeIntegerEntry : entryList)
        {
            nodeIntegerLinkedHashMap.put(nodeIntegerEntry.getKey(), nodeIntegerEntry.getValue());
        }
        return nodeIntegerLinkedHashMap;
    }
}
