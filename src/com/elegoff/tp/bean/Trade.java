package com.elegoff.tp.bean;

import org.apache.log4j.Logger;

/**
 * TODO Class description
 */
public class Trade
{
    /*{"userId": "134256", "currencyFrom": "EUR", "currencyTo": "GBP", "amountSell": 1000,
     * "amountBuy": 747.10, "rate": 0.7471, "timePlaced" : "24-JAN-15 10:27:44", "originatingCountry" : "FR"}
     */

    /**
     * Logging object
     */
    private final static org.apache.log4j.Logger LOG = Logger.getLogger(Trade.class);

    private int userId;

    private String currencyFrom, currencyTo;

    private double amountSell, amountBuy;

    private double rate;

    private String timePlaced;

    private String originatingCountry;

    public int getUserId()
    {
        return userId;
    }

    public void setUserId(int userId)
    {
        this.userId = userId;
    }

    public String getCurrencyFrom()
    {
        return currencyFrom;
    }

    public void setCurrencyFrom(String currencyFrom)
    {
        this.currencyFrom = currencyFrom;
    }

    public String getCurrencyTo()
    {
        return currencyTo;
    }

    public void setCurrencyTo(String currencyTo)
    {
        this.currencyTo = currencyTo;
    }

    public double getAmountSell()
    {
        return amountSell;
    }

    public void setAmountSell(double amountSell)
    {
        this.amountSell = amountSell;
    }

    public double getAmountBuy()
    {
        return amountBuy;
    }

    public void setAmountBuy(double amountBuy)
    {
        this.amountBuy = amountBuy;
    }

    public double getRate()
    {
        return rate;
    }

    public void setRate(double rate)
    {
        this.rate = rate;
    }

    public String getTimePlaced()
    {
        return timePlaced;
    }

    public void setTimePlaced(String timePlaced)
    {
        this.timePlaced = timePlaced;
    }

    public String getOriginatingCountry()
    {
        return originatingCountry;
    }

    public void setOriginatingCountry(String originatingCountry)
    {
        this.originatingCountry = originatingCountry;
    }

    public boolean validate()
    {

        if (originatingCountry == null || originatingCountry.isEmpty())

        {
            LOG.error("wrong originatingCountry " + originatingCountry);
            return false;
        }

        if (currencyFrom == null || currencyFrom.isEmpty())
        {
            LOG.error("wrong currencyFrom " + currencyFrom);
            return false;
        }

        if (currencyTo == null || currencyTo.isEmpty())
        {
            LOG.error("wrong currencyTo " + currencyTo);
            return false;
        }

        if (timePlaced == null || timePlaced.isEmpty())
        {
            LOG.error("wrong timePlaced " + timePlaced);
            return false;
        }

        if (amountSell == 0)
        {
            LOG.error("wrong amountSell " + amountSell);
            return false;
        }

        if (amountBuy == 0)
        {
            LOG.error("wrong amountBuy " + amountBuy);
            return false;
        }

        if (rate <= 0)
        {
            LOG.error("wrong rate " + rate);
            return false;
        }

        return true;
    }

}
