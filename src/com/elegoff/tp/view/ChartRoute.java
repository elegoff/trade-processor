//////////////////////////////////////////////////////////////////////////////
//
//  Copyright  1999 - 2014 VASCO DATA SECURITY
//  All rights reserved. http://www.vasco.com
//
//////////////////////////////////////////////////////////////////////////////
package com.elegoff.tp.view;

import java.awt.Font;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.entity.StandardEntityCollection;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import com.elegoff.tp.bean.Processed;

/**
 * TODO Class description
 */
@WebServlet("/chart")
public class ChartRoute extends HttpServlet
{

    /**
     *
     */
    private static final long serialVersionUID = 158317218154060492L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
    {

        String srow = req.getParameter("count");
        String col = req.getParameter("column");

        int row = Integer.parseInt(srow);

        @SuppressWarnings("unchecked")
        List<Processed> ps = (List<Processed>) req.getSession().getAttribute("data");
        Processed p = ps.get(row);
        Map<String, Double> data = null;
        JFreeChart chart = null;
        if (col.equalsIgnoreCase("day"))
        {
            data = p.getVolumeByDay();
            chart = this.getChartByDay(p.getCurrencyPair(), data);
        }
        else
        {
            data = p.getVolumeByCountry();
            chart = this.getChartByCountry(p.getCurrencyPair(), data);
        }

        if (chart != null)
        {
            int width = 500;
            int height = 340;
            final ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection());
            res.setContentType("image/png");
            OutputStream out = res.getOutputStream();
            ChartUtilities.writeChartAsPNG(out, chart, width, height, info);
        }
    }

    private JFreeChart getChartByCountry(String title, Map<String, Double> data)
    {

        DefaultPieDataset dataset = new DefaultPieDataset();
        for (String key : data.keySet())
        {
            dataset.setValue(key, data.get(key));
        }

        JFreeChart chart = ChartFactory.createPieChart(title, dataset, true, true, false);
        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setLabelFont(new Font("SansSerif", Font.PLAIN, 12));
        plot.setNoDataMessage("No data available");
        plot.setCircular(false);
        plot.setLabelGap(0.02);

        return chart;
    }

    private JFreeChart getChartByDay(String title, Map<String, Double> data)
    {
        final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (String day : data.keySet())
        {
            dataset.addValue(data.get(day), "Day", day);
        }

        JFreeChart chart = ChartFactory.createBarChart(title, "Time", "Total Amount", dataset,
                PlotOrientation.VERTICAL, true, true, false);

        return chart;

    }
}
