package controller;

import controller.Sharks.SharkController;
import controller.Sharks.SharkInfoWidgetController;
import controller.Sharks.SharkOfDayInfoPanelController;
import model.SearchOptions;
import model.SearchResult;
import model.SearchResults;
import view.SearchScreen;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.HashSet;

/**
 * Search screen controller for handling all events on the search screen and pulling
 * data for the search view from the sharksController (A boilerplate for the JawsAPI)
 *
 * This controller proactively captures a users search options as they make them, minimising
 * the controls needing be stored as private fields in the view
 *
 * Created by Adam on 17/03/2016.
 */
public class SearchScreenController implements ItemListener, ActionListener {

    //The search screen view
    SearchScreen view;
    //The currently selected options for this view
    SearchOptions options;

    /**
     * Constructs a new Search Screen controller with the default search options
     * model and the searchScreen view
     */
    public SearchScreenController() {
        view = new SearchScreen(this, SharkOfDayInfoPanelController.getSharkOfTheDayPanel());
        //The search options are constructed and set to default search
        options = new SearchOptions();
    }

    /**
     * Handles all button click actions that take place on the search screen
     * This includes clicking the Search, Back, Statistics and Follow button
     *
     * Routing all actions to the relevant screens
     *
     * @param e - the details of the event
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        //if the source is a button
        if(e.getSource() instanceof JButton){
            //cast to button
            JButton buttonClicked = (JButton)e.getSource();
            //based on the text of that button do action
            switch (buttonClicked.getText()){
                //if button is search
                case "Search":
                    //run update search results
                    updateSearchResults();
                    break;
                //if button is back
                case "Back":
                    //return user to the open screen
                    view.setVisible(false);
                    new OpenScreenController();
                    view.dispose();
                    break;
                //if button is statistics
                case "Statistics":
                    //hide search screen and open statistics screen with the selected
                    //time period
                    view.setVisible(false);
                    new StatisticsScreenController(options.getTimePeriod());
                    view.dispose();
                    break;
                case "Compatibility":
                    new CompatibilityScreenController();
                    break;
                case "Refresh":
                    SharkController.requestForceRefresh();
                    updateSearchResults();
                    break;
            }
        }
    }

    /**
     * This method loads search results based on the users search options
     * clears any existing search results and then displays the new search
     * results in the view
     *
     */
    private void updateSearchResults() {
        addSharksToView(SharkController.getSharksByTimePeriod(options.getTimePeriod()));
    }

    /**
     * This method takes a SearchResults object, filters all of the results according
     * to the Gender, Stage of life and tag locations and then renders them
     * in the search screen view
     * @param initialResults - the initial set of results for the time period selected
     */
    private void addSharksToView(SearchResults initialResults){
        //run a method to narrow search results by search options
        SearchResults finalResult = narrowSearchResults(initialResults);
        //clear the view of any existing results being shown
        view.clearSharkResults();

        if(finalResult.size() == 0){
            view.addNoResults();
            return;
        }

        //add the results to the view
        for(SearchResult sr : finalResult){
            new SharkInfoWidgetController(view, sr);
        }
    }

    /**
     * This method narrows search results according to the search options selected
     * by the user in the search view.
     * @param initialResults - the initial results to narrow
     * @return
     */
    private SearchResults narrowSearchResults(SearchResults initialResults){
        //create a new set of search results
        SearchResults finalResult = new SearchResults();
        //create a set of shark names so we only list them once
        HashSet<String> names = new HashSet<>();
        for(SearchResult sr : initialResults){
            //Match condition for gender search option
            if(sr.getShark().getGender().equals(options.getGender()) || options.getGender().equals("All")){
                //match condition
                if(sr.getShark().getStageOfLife().equals(options.getStageOfLife()) || options.getStageOfLife().equals("All")){
                    if(sr.getShark().getTagLocation().equals(options.getTagLocation()) || options.getTagLocation().equals("All")){
                        if(!names.contains(sr.getShark().getName())){
                            finalResult.add(sr);
                            names.add(sr.getShark().getName());
                        }
                    }
                }
            }
        }
        return finalResult;
    }

    /**
     * Method for handling search selection changes in the JComboBox, this method
     * is attached to each JComboBox to catch selections made on them
     * @param e - the event info for an item changed event
     */
    @Override
    public void itemStateChanged(ItemEvent e) {
        JComboBox<String> changedBox = (JComboBox<String>)e.getSource();
        String item = (String)e.getItem();
        switch (changedBox.getName()){
            case "trackingRange":
                options.setTimePeriod(item);
                break;
            case "stageOfLife":
                options.setStageOfLife(item);
                break;
            case "tagLocation":
                options.setTagLocation(item);
                break;
            case "gender":
                options.setGender(item);
                break;
        }
    }
}
