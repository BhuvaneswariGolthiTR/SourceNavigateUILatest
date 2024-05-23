package com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.source.datapod;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.source.SourceDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.CommonDataMocking;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.source.SourceDataMockingNew;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.source.datapod.sourceobjects.DeltaObject;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.source.datapod.sourceobjects.LineageObject;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.source.datapod.sourceobjects.RenditionObject;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.source.datapod.sourceobjects.SourceObject;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import static com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils.commit;

public class SourceDatapodObject
{

    private List<SourceObject> sourceObjects;

    // idk what to put here
    public SourceDatapodObject()
    {
        sourceObjects = new ArrayList<>();
    }

    public void delete()
    {
        SourceDataMockingNew sourceMocker = new SourceDataMockingNew();
        Connection connection = CommonDataMocking.connectToDatabase(TestService.environmentTag);

        for(RenditionObject rendition : getRenditions())
        {
            String renditionUUID = rendition.getRenditionUUID();
            sourceMocker.deleteRendition(connection, renditionUUID);
        }

        commit(connection);
    }

    public List<LineageObject> getLineages()
    {
        List<LineageObject> lineages = new ArrayList<>();
        for(SourceObject obj : sourceObjects)
        {
            if(obj.getClass().equals(LineageObject.class))
            {
                LineageObject lineage = (LineageObject) obj;
                lineages.add(lineage);
            }
        }
        return lineages;
    }

    public List<RenditionObject> getRenditions()
    {
        List<RenditionObject> renditions = new ArrayList<>();
        for(SourceObject obj : sourceObjects)
        {
            if(obj.getClass().equals(RenditionObject.class))
            {
                RenditionObject rendition = (RenditionObject) obj;
                renditions.add(rendition);
            }
        }
        return renditions;
    }

    public List<DeltaObject> getDeltas()
    {
        List<DeltaObject> deltas = new ArrayList<>();
        for(SourceObject obj : sourceObjects)
        {
            if(obj.getClass().equals(DeltaObject.class))
            {
                DeltaObject delta = (DeltaObject) obj;
                deltas.add(delta);
            }
        }
        return deltas;
    }

    public boolean addSourceObject(SourceObject sourceObject)
    {
        return sourceObjects.add(sourceObject);
    }

    public boolean removeSourceObject(SourceObject sourceObject)
    {
        return sourceObjects.remove(sourceObject);
    }

    public List<SourceObject> getSourceObjects()
    {
        return sourceObjects;
    }

}
