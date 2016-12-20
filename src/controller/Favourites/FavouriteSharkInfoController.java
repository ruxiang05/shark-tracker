package controller.Favourites;

import api.jaws.Location;
import controller.GeoController;
import controller.Sharks.MoreInfoScreenController;
import controller.Sharks.SharkController;
import view.widgets.FavouriteSharkInfo;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Controller for the favouritesSharkInfoPanel
 *
 * this controller constructs a new info panel with the necessary controls and
 * handles events that occur on it
 * Created by Adam on 22/03/2016.
 */
public class FavouriteSharkInfoController implements ActionListener{

    //the info panel
    private FavouriteSharkInfo view;
    //the parent controller
    private FavouritesScreenController controller;
    //the name of the shark being represented
    private String sharkName;

    /**
     * Constructor for the favouritesSharkInfoController takes the name and distance
     * of the shark as well as the calling controller
     * @param sharkName - the name of the shark being represented by this info controller
     * @param distance - the distance of the shark from kings
     * @param parentController - the parent controller of this class
     */
    public FavouriteSharkInfoController(String sharkName, double distance, FavouritesScreenController parentController) {
        //gets shark location, checks if it's a sharknado and constructs an info
        //panel from these details
        Location sharkLocation = SharkController.getLastLocationOfShark(sharkName);
        view = new FavouriteSharkInfo(sharkName, Math.round(distance) + "KM", getSharknadoDetails(sharkLocation), this);

        //sets the sharkname of that the controller is representing
        this.sharkName = sharkName;

        //sets the parent controller, and uses it to add the new panel to the parent view
        controller = parentController;
        controller.addFavouriteSharkView(view);
    }


    /**
     * Handles button clicks for the FavouriteSharkInfo Panel, this includes
     * clicks of the unfollow button and clicks of the "More Info" button
     * @param e - the event indicating which button has been clicked
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        //if the source is a button
        if(e.getSource() instanceof JButton){
            //cast to button
            JButton clickedButton = (JButton)e.getSource();
            //select action based on button text
            switch (clickedButton.getText()){
                case "Following":
                    //unfollow shark
                    FavouritesController.removeFavouriteShark(
                            FavouritesController.getCurrentUser(),
                            sharkName
                    );
                    controller.refreshView();
                    break;
                case "More Info":
                    //show more info on shark
                    new MoreInfoScreenController(sharkName);
                    break;
            }
        }
    }

    /**
     * Method for checking with the google elevation api to see if the shark
     * is a sharknado and if so will return the string "Sharknado" else returns
     * empty string
     * @param sharkLocation - the location of the shark to determine
     * @return - String indicating if the shark is a sharknado
     */
    private String getSharknadoDetails(Location sharkLocation){
        if(GeoController.isSharknado(sharkLocation.getLatitude(), sharkLocation.getLongitude())){
            return "Sharknado";
        } else {
            return "";
        }
    }
}
