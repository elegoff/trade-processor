package com.elegoff.tp.consumer;

import com.elegoff.tp.AbstractDto;
import com.elegoff.tp.bean.Trade;
import com.google.gson.Gson;

public class ConsumerDto extends AbstractDto
{
    private boolean success;

    private String description;

    private Trade trade;

    public Trade getTrade()
    {
        return trade;
    }

    public void setTrade(Trade trade)
    {
        this.trade = trade;
    }

    public Gson getGson()
    {
        return gson;
    }

    private final Gson gson;

    public ConsumerDto()
    {
        gson = new Gson();
    }

    public boolean isSuccess()
    {
        return success;
    }

    public String getJsonResult()
    {

        String json = gson.toJson(this);
        return json;
    }

    public void setSuccess(boolean success)
    {
        this.success = success;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

}
