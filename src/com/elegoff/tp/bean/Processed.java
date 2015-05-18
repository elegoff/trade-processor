package com.elegoff.tp.bean;

import java.util.Map;

/**
 * TODO Class description
 */
public class Processed
{

    private String currencyPair; // e.g EUR->USD

    private Map<String, Double> volumeByDay; //aggregate amounts for a given day

    private Map<String, Double> volumeByCountry; //aggregate amounts for a given country

    private double minRate; // lowest rate observed for this currency pair

    private double maxRate; // highest rate observed for this currency pair

    private double count; // number of placed records for this currency pair

    public String getCurrencyPair()
    {
        return currencyPair;
    }

    public void setCurrencyPair(String currencyPair)
    {
        this.currencyPair = currencyPair;
    }

    public Map<String, Double> getVolumeByDay()
    {
        return volumeByDay;
    }

    public void setVolumeByDay(Map<String, Double> volumeByDay)
    {
        this.volumeByDay = volumeByDay;
    }

    public Map<String, Double> getVolumeByCountry()
    {
        return volumeByCountry;
    }

    public void setVolumeByCountry(Map<String, Double> volumeByCountry)
    {
        this.volumeByCountry = volumeByCountry;
    }

    public double getMinRate()
    {
        return minRate;
    }

    public void setMinRate(double minRate)
    {
        this.minRate = minRate;
    }

    public double getMaxRate()
    {
        return maxRate;
    }

    public void setMaxRate(double maxRate)
    {
        this.maxRate = maxRate;
    }

    public double getCount()
    {
        return count;
    }

    public void setCount(double count)
    {
        this.count = count;
    }

}
