package com.elegoff.tp.consumer;

import org.apache.log4j.Logger;

import com.elegoff.tp.BaseResponse;

public class ConsumerResponse extends BaseResponse
{
    /**
     * Logging object
     */
    private final static org.apache.log4j.Logger LOG = Logger.getLogger(ConsumerResponse.class);

    private final ConsumerDto _dto;

    public ConsumerResponse(ConsumerDto dto)
    {
        super();
        _dto = dto;
    }

}
