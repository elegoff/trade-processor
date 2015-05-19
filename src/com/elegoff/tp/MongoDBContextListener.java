package com.elegoff.tp;

import java.net.UnknownHostException;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.log4j.Logger;

import com.mongodb.MongoClient;


@WebListener
public class MongoDBContextListener implements ServletContextListener
{

    /**
     * Logging object
     */
    private final static org.apache.log4j.Logger LOG = Logger.getLogger(MongoDBContextListener.class);

    @Override
    public void contextDestroyed(ServletContextEvent sce)
    {
        MongoClient mongo = (MongoClient) sce.getServletContext().getAttribute("MONGO_CLIENT");
        mongo.close();
        LOG.info("MongoClient closed successfully");
    }

    @Override
    public void contextInitialized(ServletContextEvent sce)
    {

try
        {

	    String host = System.getenv("OPENSHIFT_MONGODB_DB_HOST");
        String sport = System.getenv("OPENSHIFT_MONGODB_DB_PORT");
        String db = System.getenv("OPENSHIFT_APP_NAME");
        if(db == null)
            db = "mydb";
        String user = System.getenv("OPENSHIFT_MONGODB_DB_USERNAME");
        String password = System.getenv("OPENSHIFT_MONGODB_DB_PASSWORD");
        int port = Integer.decode(sport);
	
            ServletContext ctx = sce.getServletContext();
            MongoClient mongo = new MongoClient(host, port);
            LOG.info("MongoClient initialized successfully");
            sce.getServletContext().setAttribute("MONGO_CLIENT", mongo);
        }
        catch (UnknownHostException e)
        {
            throw new RuntimeException("MongoClient init failed");
        }


	
 	
	    
    }

}
