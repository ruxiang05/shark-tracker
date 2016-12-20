package view;

import controller.StatisticsScreenController;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Adam on 19/03/2016.
 */

/**
 * creates a window that displays statistics of the sharks
 */
public class StatisticsScreen extends JFrame {
    /**
     * constructor creates window for statistics to be displayed in
     * @param trackingRange range in which data comes from
     * @param controller action listener
     */
    public StatisticsScreen(String trackingRange, StatisticsScreenController controller) {
        super("Statistics for Search - " + trackingRange);
        setLayout(new GridLayout(1,3));
        setSize(1500, 500);
        addWindowListener(controller);
        setVisible(true);
    }

    /**
     * adds a pie chart to the frame using data
     * @param pieDataset dataset
     * @param title title of chart
     */
    public void addPieChart(DefaultPieDataset pieDataset, String title){
        JFreeChart pieChart = ChartFactory.createPieChart(title, pieDataset, true, false, false);
        ChartPanel chartPanel = new ChartPanel(pieChart);
        add(chartPanel);
    }
}
