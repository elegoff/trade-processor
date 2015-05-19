package com.elegoff.tp.processing;

import java.util.Map;
import java.util.TreeMap;

import com.elegoff.tp.AbstractService;
import com.elegoff.tp.bean.Processed;
import com.elegoff.tp.bean.Trade;
import com.elegoff.tp.dao.ProcessedDAO;
import com.elegoff.tp.exception.TradeException;
import com.mongodb.MongoClient;

/**
 * TODO Class description
 */
public class ProcessService extends AbstractService
{

    private final Trade trade;

    private final MongoClient mongo;

    public ProcessService(Trade t, MongoClient mongoc)
    {
        super();
        trade = t;
        mongo = mongoc;
    }

    @Override
    public ProcessDto run() throws TradeException
    {

        ProcessDto dto = new ProcessDto();

        Processed processed = this.doProcess(trade);
        dto.setProcessed(processed);

        return dto;
    }

    private Processed doProcess(Trade t)
    {

        //Date from trade
        double rate = t.getRate();
        String country = t.getOriginatingCountry();
        double amountBuy = t.getAmountBuy();
        String timeplaced = t.getTimePlaced();
        String day = this.dayPlaced(timeplaced);

        String currencyPair = t.getCurrencyFrom() + "->" + t.getCurrencyTo();
        //Lookup currency pair in db
        ProcessedDAO processedDAO = new ProcessedDAO(mongo);
        Processed p = processedDAO.readProcessed(currencyPair);

        //System.out.println("elg processing" + t.getCurrencyFrom() + "=>" + t.getCurrencyTo());

        if (p == null)
        {
            //first occurence of such currency pair

            p = new Processed();
            p.setCurrencyPair(currencyPair);
            p.setMaxRate(rate);
            p.setMinRate(rate);
            p.setCount(1);
            Map<String, Double> volumeByCountry = new TreeMap<String, Double>();
            Map<String, Double> volumeByDay = new TreeMap<String, Double>();
            volumeByCountry.put(country, amountBuy);
            volumeByDay.put(day, amountBuy);
            p.setVolumeByCountry(volumeByCountry);
            p.setVolumeByDay(volumeByDay);

            processedDAO.createProcessed(p);

        }
        else
        {
            if (rate > p.getMaxRate())
            {
                p.setMaxRate(rate);
            }
            if (rate < p.getMinRate())
            {
                p.setMinRate(rate);
            }
            p.setCount(p.getCount() + 1);

            Map<String, Double> volumeByCountry = p.getVolumeByCountry();
            Map<String, Double> volumeByDay = p.getVolumeByDay();

            //volume by country
            if (volumeByCountry.containsKey(country))
            {
                Double current = volumeByCountry.get(country);
                volumeByCountry.put(country, current + amountBuy);
                p.setVolumeByCountry(volumeByCountry);
            }
            else
            {
                volumeByCountry.put(country, amountBuy);
                p.setVolumeByCountry(volumeByCountry);
            }

            //volume by day
            if (volumeByDay.containsKey(day))
            {
                Double current = volumeByDay.get(day);
                volumeByDay.put(day, current + amountBuy);
                p.setVolumeByDay(volumeByDay);
            }
            else
            {
                volumeByDay.put(day, amountBuy);
                p.setVolumeByDay(volumeByDay);
            }

            processedDAO.updateProcessed(p);
        }

        return p;
    }

    private String dayPlaced(String timeplaced)
    {
        String[] parts = timeplaced.split(" ");
        return parts[0];
    }

}
