package com.elegoff.tp.dao;

import java.util.ArrayList;
import java.util.List;

import com.elegoff.tp.bean.Trade;
import com.elegoff.tp.bean.TradeConverter;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.DB;

//DAO class for different MongoDB CRUD operations

public class TradeDAO
{

    private final DBCollection col;

    public TradeDAO(MongoClient mongo)
    {
	String dbname = System.getenv("OPENSHIFT_APP_NAME");
	DB db =  mongo.getDB(dbname);
	
	String user = System.getenv("OPENSHIFT_MONGODB_DB_USERNAME");
        String password = System.getenv("OPENSHIFT_MONGODB_DB_PASSWORD");

	if(db.authenticate(user, password.toCharArray()) == false) {
            throw new RuntimeException("Failed to authenticate against db: "+db);
        }
        col = db.getCollection("Trades");
        
    }

    public Trade createTrade(Trade t)
    {
        DBObject doc = TradeConverter.toDBObject(t);
        col.insert(doc);
        return t;
    }

    public void updateTrade(Trade t)
    {
        DBObject query = BasicDBObjectBuilder.start().get();
        col.update(query, TradeConverter.toDBObject(t));
    }

    public List<Trade> readAllTrade()
    {
        List<Trade> data = new ArrayList<Trade>();
        DBCursor cursor = col.find();
        while (cursor.hasNext())
        {
            DBObject doc = cursor.next();
            Trade t = TradeConverter.toTrade(doc);
            data.add(t);
        }
        return data;
    }

    public void deleteTrade(Trade t)
    {
        DBObject query = BasicDBObjectBuilder.start().get();
        col.remove(query);
    }

    public Trade readTrade(Trade t)
    {
        DBObject query = BasicDBObjectBuilder.start().get();
        DBObject data = col.findOne(query);
        return TradeConverter.toTrade(data);
    }

}
