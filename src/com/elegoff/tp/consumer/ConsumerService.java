package com.elegoff.tp.consumer;

import org.apache.log4j.Logger;

import com.elegoff.tp.AbstractService;
import com.elegoff.tp.bean.Trade;
import com.elegoff.tp.dao.TradeDAO;
import com.elegoff.tp.exception.TradeException;
import com.google.gson.Gson;
import com.mongodb.MongoClient;

public class ConsumerService extends AbstractService
{

    /**
     * Logging object
     */
    private final static org.apache.log4j.Logger LOG = Logger.getLogger(ConsumerService.class);

    private final String jsonTrade;

    private final MongoClient mongo;

    private final TradeDAO tradeDAO;

    public ConsumerService(String json, MongoClient mongoc)
    {
        jsonTrade = json;
        mongo = mongoc;
        tradeDAO = new TradeDAO(mongo);
    }

    @Override
    public ConsumerDto run() throws TradeException

    {

        ConsumerDto dto = new ConsumerDto();

        Trade trade = new Gson().fromJson(jsonTrade, Trade.class);

        if (!trade.validate())
        {
            dto.setSuccess(false);
            dto.setDescription("Failed to validate input");

        }
        else
        {

            //persist in db
            //is.insertTrade(trade);

            dto.setTrade(trade);
            dto.setSuccess(true);
            dto.setDescription("success");
        }
        return dto;
    }

    public void insertTrade(Trade t)
    {

        tradeDAO.createTrade(t);

    }
}
