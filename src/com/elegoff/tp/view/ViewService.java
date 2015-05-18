package com.elegoff.tp.view;

import java.util.List;

import org.apache.log4j.Logger;

import com.elegoff.tp.bean.Processed;
import com.elegoff.tp.dao.ProcessedDAO;
import com.mongodb.MongoClient;

public class ViewService
{

    /**
     * Logging object
     */
    private final static org.apache.log4j.Logger LOG = Logger.getLogger(ViewService.class);

    private final MongoClient mongo;

    public ViewService(MongoClient mongoc)
    {
        mongo = mongoc;
    }

    public List<Processed> run()

    {

        ProcessedDAO processedDAO = new ProcessedDAO(mongo);

        return processedDAO.readAllProcessed();

    }

}
