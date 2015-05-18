package com.elegoff.tp.bean;

import com.google.gson.Gson;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;

public class ProcessedConverter
{

    //Sample json record
    /*{"userId": "134256", "currencyFrom": "EUR", "currencyTo": "GBP",
     * "amountSell": 1000, "amountBuy": 747.10, "rate": 0.7471, "timePlaced" : "24-JAN-15 10:27:44",
     * "originatingCountry" : "FR"}
     */

    /*
     *  convert Processed Object to MongoDB DBObject
     */
    public static DBObject toDBObject(Processed p)
    {

        String json = new Gson().toJson(p);

        DBObject dbObject = (DBObject) JSON.parse(json);

        return dbObject;
    }

    // convert DBObject Object to Processed
    //
    public static Processed toProcessed(DBObject doc)
    {
        Processed p = new Processed();

        String json = doc.toString();
        Gson gson = new Gson();
        p = gson.fromJson(json, Processed.class);

        return p;

    }

}
