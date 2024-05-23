package com.thomsonreuters.codes.codesbench.quality.utilities.files;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CsvFileUtils
{
    public static List<String> getContent(String filePath)
    {
        Scanner scan = null;
        List<String> csvContent = new ArrayList<>();
        try
        {
            scan = new Scanner(new File(filePath));
            while(scan.hasNextLine())
            {
                csvContent.add(scan.nextLine());
            }
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        finally
        {
            if(scan != null)
            {
                scan.close();
            }
        }

        return csvContent;
    }
}
