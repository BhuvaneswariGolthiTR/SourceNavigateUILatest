package com.thomsonreuters.codes.codesbench.quality.utilities.lists;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestSetupEdge;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ListUtils extends TestSetupEdge
{
	public static boolean areListsEqual2D(List<List<String>> list1, List<List<String>> list2)
	{
		if (list1.size() != list2.size())
		{
			logger.information("The given lists are not the same length.");
			return false;
		}
		
		Iterator<List<String>> list1It = list1.iterator();
		Iterator<List<String>> list2It = list2.iterator();
		
		while (list1It.hasNext())
		{
			List<String> list1Temp = list1It.next();
			List<String> list2Temp = list2It.next();
			
			if (list1Temp.size() != list2Temp.size())
			{
				logger.information("The given lists are not the same length at each index.");
				return false;
			}

			Iterator<String> list1It2 = list1Temp.iterator();
			Iterator<String> list2It2 = list2Temp.iterator();
			
			while (list1It2.hasNext())
			{
				String one = list1It2.next();
				String two = list2It2.next();
				
				if (!one.equals(two))
				{
					logger.information(String.format("Elements do not match in the lists. Expected: %s, Actual: %s", one, two));
					return false;
				}
			}
		}
		
		return true;
	}
	
	public static boolean areListsEqual(List<String> list1, List<String> list2)
	{
		if(list1.size() == list2.size())
		{
			for (int i = 0; i < list1.size(); i++)
			{
				
				if(!list1.get(i).equals(list2.get(i)))
				{
					return false;
				}
			}
		}
		else
		{
			logger.information(String.format("The lists are not the same size. List1: %d, List2: %d", list1.size(), list2.size()));
			return false;
		}
		
		return true;
	}
	
	public static boolean areListsEqualIgnoreOrder(List<String> list1, List<String> list2)
	{
		return list1.stream().allMatch(title -> list2.contains(title)) && list2.stream().allMatch(title -> list1.contains(title));
	}

	public static List<Integer> grabIndexesOfNeededItems(List<String> items, String item)
	{
		List<Integer> indexes = new ArrayList<>();
		for (int index = 0; index < items.size(); index++)
		{
			if (items.get(index).equals(item))
			{
				indexes.add(index);
			}
		}
		return indexes;
	}

	public static boolean areListsEqualContains(List<String> list1, List<String> list2)
	{
		if(list1.size() == list2.size())
		{
			for (int i = 0; i < list1.size(); i++)
			{

				if(!list1.get(i).contains(list2.get(i)))
				{
					return false;
				}
			}
		}
		else
		{
			logger.information(String.format("The lists are not the same size. List1: %d, List2: %d", list1.size(), list2.size()));
			return false;
		}

		return true;
	}

	/**
	 *  Checks that all values in listOfValues match a value in expectedValues ignoring list size
	 *  Ex: listOfValues = {1, 2, 2}  expectedValues = {1, 2} returns true
	 *  	listOfValues = {1, 2, 3}  expectedValues = {1, 2} returns false
	 * @param listOfValues list to check
	 * @param expectedValues list to check against
	 * @return boolean true/false
	 */
	public static boolean areListEqualIgnoreSize(List<String> listOfValues, List<String> expectedValues)
	{
		return expectedValues.containsAll(listOfValues);
	}
}
