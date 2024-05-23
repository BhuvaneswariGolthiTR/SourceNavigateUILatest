package com.thomsonreuters.codes.codesbench.quality.utilities.files.pdf.extensions;

import org.apache.pdfbox.text.PDFTextStripperByArea;
import org.apache.pdfbox.text.TextPosition;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//This extension is necessary to allow us to appropriately get font data info from a PDF

public class PDFFontStripperByArea extends PDFTextStripperByArea
{

    String stringToSearch = "";

    public PDFFontStripperByArea() throws IOException
    {
        super();
    }

    public void setStringToSearch(String string)
    {
        stringToSearch = string;
    }

    @Override
    protected void writeString(String string, List<TextPosition> textPositions) throws IOException
    {
        StringBuilder builder = new StringBuilder();
        String lastFont = "";

        //Loop through each character two create the words and get the data for each word
        for (TextPosition position : textPositions)
        {
            String font = position.getFont().getName();
            int fontSize = (int)position.getFontSizeInPt();

            //Two 'if' statements work together to get each word's data separately
            if(position.toString().equals(" "))
            {
                lastFont = "";
                continue;
            }
            if (font != null && !font.equals(lastFont))
            {
                String finds = "[" + font + ", " + fontSize + "]";
                builder.append(finds);
                lastFont = font;
            }
            builder.append(position.getUnicode());
        }

        //Only return the font name and font size of the string we're looking for
        String stringToMatchFrom = builder.toString().trim();
        Matcher matcher = Pattern.compile("\\[\\w+.\\w+,\\d+]" + stringToSearch).matcher(stringToMatchFrom);
        if(matcher.find())
        {
            writeString(matcher.group(0));
        }
    }
}
