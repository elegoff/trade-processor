package com.elegoff.tp.dao;

import java.util.ArrayList;
import java.util.List;

import com.elegoff.tp.bean.Processed;
import com.elegoff.tp.bean.ProcessedConverter;
import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

//DAO class for different MongoDB CRUD operations

public class ProcessedDAO
{

    private final DBCollection col;

    public ProcessedDAO(MongoClient mongo)
    {
        col = mongo.getDB("cf").getCollection("Processed");
    }

    public Processed createProcessed(Processed p)
    {
        DBObject doc = ProcessedConverter.toDBObject(p);
        col.insert(doc);
        return p;
    }

    public void updateProcessed(Processed p)
    {
        BasicDBObject newDocument = new BasicDBObject();
        newDocument.put("currencyPair", p.getCurrencyPair());
        newDocument.put("minRate", p.getMinRate());
        newDocument.put("maxRate", p.getMaxRate());
        newDocument.put("count", p.getCount());
        newDocument.put("volumeByDay", p.getVolumeByDay());
        newDocument.put("volumeByCountry", p.getVolumeByCountry());

        BasicDBObject searchQuery = new BasicDBObject().append("currencyPair", p.getCurrencyPair());

        col.update(searchQuery, newDocument);
    }

    public List<Processed> readAllProcessed()
    {
        List<Processed> data = new ArrayList<Processed>();
        DBCursor cursor = col.find();
        cursor.sort(new BasicDBObject("count", -1).append("volumeByDay", 1));
        while (cursor.hasNext())
        {
            DBObject doc = cursor.next();
            Processed p = ProcessedConverter.toProcessed(doc);
            data.add(p);
        }
        return data;
    }

    public void deleteProcessed(Processed p)
    {
        DBObject query = BasicDBObjectBuilder.start().get();
        col.remove(query);
    }

    public Processed readProcessed(String currencyPair)
    {

        Processed p = null;
        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put("currencyPair", currencyPair);
        DBCursor cursor = col.find(whereQuery);
        while (cursor.hasNext())
        {
            DBObject data = cursor.next();
            p = ProcessedConverter.toProcessed(data);

        }
        return p;
    }

}
