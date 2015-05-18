package com.elegoff.tp;

import com.elegoff.tp.exception.TradeException;

/**
 * Parent class for trading services (anticipating further extensions)
 */
public abstract class AbstractService
{
    public abstract AbstractDto run() throws TradeException;

}
