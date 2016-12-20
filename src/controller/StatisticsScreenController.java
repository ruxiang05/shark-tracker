package controller;

import controller.Sharks.SharkController;
import model.SearchResult;
import model.SearchResults;
import org.jfree.data.general.DefaultPieDataset;
import view.StatisticsScreen;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.HashMap;
import java.util.HashSet;

/**
 * The class encapsulate the construction and management of the statistics view
 * in the search screen
 *
 * It automatically calculates the data for the statistics view, for the tracking
 * range given to the constructor and displays the view.
 *
 * Created by Adam on 19/03/2016.
 */
public class StatisticsScreenController implements WindowListener {

    //the view of the statistics screen
    private StatisticsScreen view;
    //the search results for the tracking range
    private SearchResults model;


    /**
     * Constructs a new statistics screen controller for the given tracking range
     * @param trackingRange - the tracking range to get statistics for
     */
    public StatisticsScreenController(String trackingRange) {
        //creates a new statistics screen with this controller as the controller
        view = new StatisticsScreen(trackingRange, this);
        //gets the data from the sharkController
        model = SharkController.getSharksByTimePeriod(trackingRange);
        //remove duplicate sharks
        model = removeDuplicateSharks(model);
        //internal method for creating gender chart
        createGenderChart();
        //internal method for creating Tag location chart
        createTagLocationChart();
        //internal method for creating Stage of Life chart
        createStageOfLifeChart();
    }

    /**
     * Method for removing duplicate shark data from the model, so that the pie
     * charts are shown correctly
     * @param initial - the initial data set
     * @return - the data set with duplicates removed
     */
    private SearchResults removeDuplicateSharks(SearchResults initial){
        HashSet<String> sharkNames = new HashSet<>();
        SearchResults finalResults = new SearchResults();
        for(SearchResult sr : initial){
            if(!sharkNames.contains(sr.getShark().getName())){
                finalResults.add(sr);
                sharkNames.add(sr.getShark().getName());
            }
        }
        return finalResults;
    }

    /**
     * Method for handling the window closing (i.e. when close button is clicked)
     * this method catches the window close and reopens the search view
     * @param e - the window close event
     */
    @Override
    public void windowClosing(WindowEvent e) {
        new SearchScreenController();
    }

    /**
     * Creates a new chart with a breakdown of sharks by gender
     */
    private void createGenderChart(){
        //creates a new piechart data set
        DefaultPieDataset genderPieDataset = new DefaultPieDataset();
        //creates a new hashmap that maps gender names, to number in that gender
        HashMap<String, Integer> genderBreakdown = new HashMap<>();
        //oops through each search result
        for(SearchResult sr : model){
            //if the gender is not already in the hashmap
            if(!genderBreakdown.containsKey(sr.getShark().getGender())){
                //put in the hashmap
                genderBreakdown.put(sr.getShark().getGender(), 0);
            }
            //replace the value, in the hashmap with the value plus one
            genderBreakdown.replace(sr.getShark().getGender(), genderBreakdown.get(sr.getShark().getGender()) + 1);
        }
        //for each gender value in the hashmap, set the value in the data set
        for(String s : genderBreakdown.keySet()){
            genderPieDataset.setValue(s, genderBreakdown.get(s));
        }
        //tell the view to add the piechart with given data set
        view.addPieChart(genderPieDataset, "Gender");
    }

    /**
     * Creates a piechart with sharks broken down by tag location
     */
    private void createTagLocationChart(){
        //Same as above
        //create new dataset
        DefaultPieDataset tagLocationPieDataset = new DefaultPieDataset();
        //create hashmap of locations, to number in that location
        HashMap<String, Integer> tagLocationBreakdown = new HashMap<>();
        //for each search result in the model
        for(SearchResult sr : model){
            //if the category isn't added to the map, add it
            if(!tagLocationBreakdown.containsKey(sr.getShark().getTagLocation())){
                tagLocationBreakdown.put(sr.getShark().getTagLocation(), 0);
            }
            //increase value in the map for category by one
            tagLocationBreakdown.replace(sr.getShark().getTagLocation(), tagLocationBreakdown.get(sr.getShark().getTagLocation()) + 1);
        }
        //set value in the dataset, based on hashmap
        for(String s : tagLocationBreakdown.keySet()){
            tagLocationPieDataset.setValue(s, tagLocationBreakdown.get(s));
        }
        //add piechart to the view
        view.addPieChart(tagLocationPieDataset, "Tag Location");
    }

    /**
     * Method for adding the stage of life piechart to the statistics screen
     */
    private void createStageOfLifeChart(){
        //Same as above two
        //Creates a new PieChart dataset
        DefaultPieDataset stageOfLifePieDataset = new DefaultPieDataset();
        //Creates a hashmap that maps piechart categories to values
        HashMap<String, Integer> stageOfLifeBreakdown = new HashMap<>();
        //for each search result
        for(SearchResult sr : model){
            //if the stage of life category doesn't exist yet, add it
            if(!stageOfLifeBreakdown.containsKey(sr.getShark().getStageOfLife())){
                stageOfLifeBreakdown.put(sr.getShark().getStageOfLife(), 0);
            }
            //replace the value in the hashmap with value plus one
            stageOfLifeBreakdown.replace(sr.getShark().getStageOfLife(), stageOfLifeBreakdown.get(sr.getShark().getStageOfLife()) + 1);
        }
        //add each value in hashmap to the piechart data set
        for(String s : stageOfLifeBreakdown.keySet()){
            stageOfLifePieDataset.setValue(s, stageOfLifeBreakdown.get(s));
        }
        //add piechart to the view
        view.addPieChart(stageOfLifePieDataset, "Stage Of Life");
    }

    /**
     * These methods serve no purpose but have to be implemented due to the interface
     */

    @Override
    public void windowOpened(WindowEvent e) {
        //do nothing
    }

    @Override
    public void windowClosed(WindowEvent e) {
        //do nothing
    }

    @Override
    public void windowIconified(WindowEvent e) {
        //do nothing
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
        //do nothing
    }

    @Override
    public void windowActivated(WindowEvent e) {
        //do nothing
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
        //do nothing
    }
}
