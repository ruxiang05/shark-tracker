package controller.Favourites;

import api.jaws.Location;
import controller.GeoController;
import controller.OpenScreenController;
import controller.Sharks.SharkController;
import view.FavouritesScreen;
import view.widgets.FavouriteSharkInfo;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.TreeMap;

/**
 * Class for controlling the FavouritesScreen, holding all the control logic
 * for the favourites screen and managing events such as button clicks and
 * navigational requests on the favourite screen
 *
 * Created by Adam on 17/03/2016.
 */
public class FavouritesScreenController implements ActionListener {
    //the favouritescreen which the controller handles
    private FavouritesScreen view;

    /**
     * This method constructs a new favourites screen controller which in turn
     * constructs a new favourites screen and adds all the favourites for the
     * current user to the favourites screen
     */
    public FavouritesScreenController() {
        view = new FavouritesScreen(this);
        addFavouriteSharks();
    }

    /**
     * Method for handling the various button clicks that occur on the favourites
     * screen, these are only navigational buttons
     * @param e - the action event that has occured
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        //if source is a button
        if(e.getSource() instanceof JButton){
            //cast to button
            JButton clickedButton = (JButton)e.getSource();
            //switch depending on the text on the button
            switch (clickedButton.getText()){
                //go back if the back button is clicked to the menu screen
                case "Back":
                    view.setVisible(false);
                    new OpenScreenController();
                    view.dispose();
                    break;
                //create a new map screen controller if the map button is clicked
                case "Map":
                    new FavouritesMapScreenController(FavouritesController.getCurrentUsersFavouriteSharks());
                    break;
            }
        }
    }

    /**
     * Internal method for adding the current users favourite sharks to the view
     */
    private void addFavouriteSharks(){
        //get every shark, indexed by their distance from king's
        TreeMap<Double, ArrayList<String>> sharksByDistance =
                getSharksByDistance(FavouritesController.getCurrentUsersFavouriteSharks());
        //A tree map is already sorted by it's index so just add them to the view
        for(double distance : sharksByDistance.keySet()){
            ArrayList<String> names = sharksByDistance.get(distance);
            for(String name : names){
                new FavouriteSharkInfoController(name, distance, this);
            }

        }
    }

    /**
     * Internal method for getting all the sharks in a treemap (a map sorted by
     * index) indexed by distance. This results in each shark being added to
     * the view by their distance from King's (i.e. their index)
     * @param sharks - The favourite sharks to add to the treemap
     * @return - a Treemap of favourite sharks indexed and sorted by their distance from kings
     */
    private TreeMap<Double, ArrayList<String>> getSharksByDistance(ArrayList<String> sharks){
        TreeMap<Double, ArrayList<String>> sharksByDistance = new TreeMap<>();
        for(String sharkName : sharks){
            Location sharkLocation = SharkController.getLastLocationOfShark(sharkName);
            double distance = GeoController.getDistanceFromKclKm(sharkLocation.getLatitude(), sharkLocation.getLongitude());

            ArrayList<String> sharksNames = sharksByDistance.getOrDefault(distance, new ArrayList<>());
            sharksNames.add(sharkName);
            sharksByDistance.putIfAbsent(distance, sharksNames);
        }
        return sharksByDistance;
    }

    /**
     * Adds a FavouriteSharkInfo panel to the view
     * @param sharkInfo - the favouriteSharkInfo panel to add
     * @see FavouriteSharkInfo
     */
    protected void addFavouriteSharkView(FavouriteSharkInfo sharkInfo){
        view.addFavouriteShark(sharkInfo);
    }

    /**
     * Method for refreshing the favourites view, primarily used by favouriteSharkInfoController
     * to refresh favourite view after a shark has been unfavourited
     */
    public void refreshView(){
        view.clearAllSharks();
        addFavouriteSharks();
    }
}
