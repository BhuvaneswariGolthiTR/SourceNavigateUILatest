package com.thomsonreuters.codes.codesbench.quality.utilities.datamocking;

import org.junit.jupiter.api.Assertions;

import java.util.UUID;

public class DataMocking
{
    public static String generateUUID()
    {
        UUID randomUUUID = UUID.randomUUID();
        String middlePartialUUID = randomUUUID.toString().replace("-", "").toUpperCase().substring(11);
        String uuid = String.format("%s%s%s", "IABCDEF", middlePartialUUID, "A0001");
        Assertions.assertEquals(33, uuid.length(), "The UUID length is not 33.");
        return uuid;
    }

    public static class Target
    {
        public static class Iowa
        {
            /*
            1 chunk of data, a few paragraphs, nothing else
             */
            public static class SmallIowa implements DataMockingInterface
            {
                @Override
                public String insert(String environment, String contentSetId)
                {
                    return null;
                }

                @Override
                public boolean delete(String environment, String contentSetId)
                {
                    return false;
                }
            }
        }
    }

    public static class Source
    {
        //TODO AFTER TARGET
    }
}
