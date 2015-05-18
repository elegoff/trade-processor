package com.elegoff.tp.processing;

import com.elegoff.tp.AbstractDto;
import com.elegoff.tp.bean.Processed;

public class ProcessDto extends AbstractDto
{

    private Processed processed;

    public Processed getProcessed()
    {
        return processed;
    }

    public void setProcessed(Processed processed)
    {
        this.processed = processed;
    }

}
