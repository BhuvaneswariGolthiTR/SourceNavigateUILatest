package com.thomsonreuters.codes.codesbench.quality.utilities.desktop;

import org.junit.jupiter.api.Assertions;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class DesktopUtils
{
    public static Desktop getDesktop()
    {
        return Desktop.getDesktop();
    }

    public static boolean isDesktopSupported()
    {
        return Desktop.isDesktopSupported();
    }

    public static void openFileOnMonitor(File myFile)
    {
        try
        {
            getDesktop().open(myFile);
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
            Assertions.fail("Could not find the audit pdf in the correct place.");
        }
    }
}



