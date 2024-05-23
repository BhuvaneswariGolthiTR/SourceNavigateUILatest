package com.thomsonreuters.codes.codesbench.quality.utilities.clipboard;

import org.junit.jupiter.api.Assertions;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

public class ClipboardUtils
{
        public static String getSystemClipboard()
        {
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                try
                {
                        return (String) clipboard.getData(DataFlavor.stringFlavor);
                } catch (UnsupportedFlavorException|IOException e)
                {
                        Assertions.fail("Some problem occurred while getting system clipboard", e);
                }
                return null;
        }

        public static void setSystemClipboard(String valueToSet)
        {
                StringSelection stringSelection = new StringSelection(valueToSet);
                Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, stringSelection);
        }
}