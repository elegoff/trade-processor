package com.elegoff.tp.consumer;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.elegoff.tp.exception.TradeException;
import com.elegoff.tp.processing.ProcessService;
import com.mongodb.MongoClient;

@WebServlet("/trade")
public class ConsumerRoute extends HttpServlet

{

    /**
    *
    */
    private static final long serialVersionUID = 151247333386230387L;

    private ExecutorService executor;

    /**
     * Logging object
     */
    private final static org.apache.log4j.Logger LOG = Logger.getLogger(ConsumerRoute.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
    {
        res.setContentType("application/json");

        StringBuilder sb = new StringBuilder();
        String s;
        while ((s = req.getReader().readLine()) != null)
        {
            sb.append(s);
        }
        final MongoClient mongo = (MongoClient) req.getServletContext().getAttribute("MONGO_CLIENT");
        final ConsumerService service = new ConsumerService(sb.toString(), mongo);

        try
        {

            final ConsumerDto dto = service.run();

            if (dto.isSuccess())
            {

                executor.execute(new Runnable()
                {
                    @Override
                    public void run()
                    {

                        service.insertTrade(dto.getTrade());

                    }
                });

                executor.execute(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        try
                        {
                            new ProcessService(dto.getTrade(), mongo).run();
                        }
                        catch (TradeException e)
                        {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                });
            }

            res.getOutputStream().print(dto.getJsonResult());
            res.getOutputStream().flush();
        }
        catch (TradeException ex)
        {
            ex.printStackTrace();

            ConsumerDto dto = new ConsumerDto();
            dto.setSuccess(false);
            dto.setDescription(ex.getMessage());
            res.getOutputStream().print(dto.getJsonResult());
            res.getOutputStream().flush();
        }
    }

    @Override
    public void init(ServletConfig config) throws ServletException
    {
        executor = Executors.newFixedThreadPool(10); // Max 10 threads.
    }

    @Override
    public void destroy()
    {
        executor.shutdownNow(); // Returns list of undone tasks, for the case that.
    }
}
