package com.elegoff.tp.bean;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;

public class TradeConverter
{

    /*
     *  convert Trade Object to MongoDB DBObject
     */

    public static DBObject toDBObject(Trade t)
    {

        BasicDBObjectBuilder builder = BasicDBObjectBuilder.start().append("userId", t.getUserId())
                .append("currencyFrom", t.getCurrencyFrom()).append("currencyTo", t.getCurrencyTo())
                .append("amountSell", t.getAmountSell()).append("amountBuy", t.getAmountBuy())
                .append("rate", t.getRate()).append("timePlaced", t.getTimePlaced());
        builder.append("originatingCountry", t.getOriginatingCountry());

        return builder.get();
    }

    // convert DBObject Object to Trade
    //
    public static Trade toTrade(DBObject doc)
    {
        Trade t = new Trade();
        t.setUserId((int) doc.get("userId"));
        t.setCurrencyFrom((String) doc.get("currencyFrom"));
        t.setCurrencyTo((String) doc.get("currencyTo"));
        t.setAmountBuy((double) doc.get("amountBuy"));
        t.setAmountSell((double) doc.get("amountSell"));
        t.setRate((double) doc.get("rate"));
        t.setTimePlaced((String) doc.get("timePlaced"));
        t.setOriginatingCountry((String) doc.get("originatingCountry"));

        return t;

    }

}
